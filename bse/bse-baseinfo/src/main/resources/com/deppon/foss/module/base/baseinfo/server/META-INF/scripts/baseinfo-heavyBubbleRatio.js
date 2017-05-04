/**
 * 重泡比菜单
 * 
 * @author 218392 张永雪
 * Build data 2014-11-14
 */

/***********************定义Model*****************************/
Ext.define('Foss.baseinfo.heavyBubbleRatio.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'createDate',     //创建日期
		type : 'date'
	},{
		name : 'outfieldName',//外场名字
		type : 'string'
	},{
		name : 'outfield',//外场
		type : 'string'
	},{
		name : 'heavyBubbleParam', // 重泡比参数
		type : 'string'
	},{
		name : 'active',//是否有效
		type : 'string'
	},{
		name : 'remark',//备注
		type : 'string'
	}]
});
/***********************定义Store*****************************/
Ext.define('Foss.baseinfo.heavyBubbleRatio.Store',{
	extend : 'Ext.data.Store',
	pageSize : 10,//定义页面条数
	model : 'Foss.baseinfo.heavyBubbleRatio.Model',//绑定model
	proxy :{ //以json方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryHeavyBubbleRatio.action'),//----这里先自定义了一个类----------kongzhe---action类----
		reader : {
			type : 'json', 
			root : 'heavyBubbleRatioVo.entityList',    //-这里先自定义一个类---------kongzhe------------------------------
			totalProperty : 'totalCount', //检索数据集中记录总数的属性名称
			successProperty : 'success'  //检索'success'标识的属性名称，该标识属性的值标示指定请求是否成功（典型表示为一个布尔值，或‘true’|‘fasle’）。 ...
		}
	},
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.callParent([cfg]);
	},
	//监听器
	listeners : {
		beforeload : function (store,operation,eOpts){ //在一个新数据对象请求发送前，触发此事件
			var queryForm = Ext.getCmp('Foss_baseinfo_heavyBubbleRatio_queryForm_Id').getForm();
			if(queryForm != null){
				var outfield = queryForm.findField('outfield').getValue();
				var heavyBubbleParam = queryForm.findField('heavyBubbleParam').getValue();
				Ext.apply(operation,{
					params : {
						'heavyBubbleRatioVo.entity.outfield' : outfield,
						'heavyBubbleRatioVo.entity.heavyBubbleParam' : heavyBubbleParam
					}
				});
			}
		}
	}
});
/***********************定义点击查询结果Grid一行打开的详细信息记录************/
//定义详细信息记录面板panel
Ext.define('Foss.baseinfo.heavyBubbleRatio.InfoPanel',{
	extend : 'Ext.panel.Panel',
	title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.detailInfo'),//详细信息------yinyong-----------------
	frame : true,
	//获取详细信息记录的form
	infoForm : null,
	getInfoForm : function (){
		if(this.infoForm == null){
			this.infoForm = Ext.create('Foss.baseinfo.heavyBubbleRatio.DetailForm');
		}
		return this.infoForm;
	},
	constructor : function (config){
		Ext.apply(this,config);
		this.items = [this.getInfoForm()];
		this.callParent(arguments);
	},
	bindData : function (record){
		var me = this;
		me.getInfoForm().getForm().loadRecord(record);//将数据加载到表单上
	}
});
//定义详细信息记录form表单
Ext.define('Foss.baseinfo.heavyBubbleRatio.DetailForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	height : 160,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate : false,//是否为修改操作，默认是false
	defaultType : 'textfield',
	layout : {
		type : 'table',
		columns : 2
	},
	items : [{
		name : 'outfieldName',
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.fieldName')//外场名称------------yinyong--------------
	},{
		name : 'outfield',
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.fieldID')//外场编号----------yinyong-------------------
	},{
		name : 'heavyBubbleParam',
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleParam')//重泡比参数---------------------
	},{
		name : 'remark',
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notes'),//备注------------------yinyong-------------------
		width : 400
	}],
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
	
});
/***********************定义窗口中的增加、修改的form表单*****************************/
//修改form表单
Ext.define('Foss.baseinfo.heavyBubbleRatio.UpdateForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 290,
	collapsible : true,
	defaults : {
		margin : '25 15 10 15',
		labelWidth : 90,
		labelAlign : 'right'
	},
	isUpdate : false,//是否为修改，这里默认的值为false,用来判断到底是修改还是增加动作的,当你点击“修改”按钮的时候，isUpdate为true
	defaultType : 'textfield',
	layout :{
		type : 'table',
		columns : 2
	},
	//提交表单
	commitInfo : function (){
		var me = this;
		var basicForm = me.getForm();
		if(basicForm.isValid()){
			var record = null;//获取model实例
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{//如果是增加动作
				record = Ext.create('Foss.baseinfo.heavyBubbleRatio.Model');
			}
			//将Form表单中的数据添加到设置到model中
			basicForm.updateRecord(record);
			
			var jsonData = {'heavyBubbleRatioVo' : {entity : record.data}};
			var url = null;
			if(me.isUpdate){ //如果是修改
				url = baseinfo.realPath('updateHeavyBubbleRatio.action');//--------自己先定义的，kongzhe--------
			}else{	//如果是增加
				url = baseinfo.realPath('addHeavyBubbleRatio.action');   //--------自己先定义的，kongzhe------
			}
			var infoGrid = Ext.getCmp('T_baseinfo-heavyBubbleRatio_content').getQueryResultGrid();
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.outfield'),//外场名称
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',//--或者查询外场
		name : 'outfieldName',
		levelses : '3',
		maxLength : 20,
		allowBlank : false,
		readOnly : true
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.outfield'),//外场
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',//--或者查询外场
		name : 'outfield',
		levelses : '3',
		maxLength : 20,
		allowBlank : false,
		hidden : true
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleParam'),//重泡比参数
		name : 'heavyBubbleParam',
		regex : /^\d+\.{0,1}\d*$/, // 正则表达式在
		regexText : '只能输入数字!', // 正则表达式错误提示
		maxLength : 20,
		allowBlank : false
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notes'),//备注
		name : 'remark',
		xtype : 'textarea',
		maxLength : 20,
		layout : 'column',
		width : 300
	}],
	
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
				if(me.isUpdate){//如果是修改
					me.loadRecord(new Foss.baseinfo.heavyBubbleRatio.Model(me.up('window').infoModel));//加载model到增加、修改的表单
				}else{ //如果是重置
					me.getForm().reset();
				}
			}
		},{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});


//新增、修改表单
Ext.define('Foss.baseinfo.heavyBubbleRatio.AddUpdateForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 290,
	collapsible : true,
	defaults : {
		margin : '25 15 10 15',
		labelWidth : 90,
		labelAlign : 'right'
	},
	isUpdate : false,//是否为修改，这里默认的值为false,用来判断到底是修改还是增加动作的,当你点击“修改”按钮的时候，isUpdate为true
	defaultType : 'textfield',
	layout :{
		type : 'table',
		columns : 2
	},
	//提交表单
	commitInfo : function (){
		var me = this;
		var basicForm = me.getForm();
		if(basicForm.isValid()){
			var record = null;//获取model实例
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{//如果是增加动作
				record = Ext.create('Foss.baseinfo.heavyBubbleRatio.Model');
			}
			//将Form表单中的数据添加到设置到model中
			basicForm.updateRecord(record);
			
			var jsonData = {'heavyBubbleRatioVo' : {entity : record.data}};
			var url = null;
			if(me.isUpdate){ //如果是修改
				url = baseinfo.realPath('updateHeavyBubbleRatio.action');//--------自己先定义的，kongzhe--------
			}else{	//如果是增加
				url = baseinfo.realPath('addHeavyBubbleRatio.action');   //--------自己先定义的，kongzhe------
			}
			var infoGrid = Ext.getCmp('T_baseinfo-heavyBubbleRatio_content').getQueryResultGrid();
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.outfield'),//外场
		xtype : 'dynamicorgcombselector',
		type : 'ORG',
		transferCenter:'Y',//--或者查询外场
		name : 'outfield',
		levelses : '3',
		maxLength : 20,
		allowBlank : false
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleParam'),//重泡比参数
		name : 'heavyBubbleParam',
		regex : /^\d+\.{0,1}\d*$/, // 正则表达式在
		regexText : '只能输入数字!', // 正则表达式错误提示
		maxLength : 20,
		allowBlank : false
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notes'),//备注
		name : 'remark',
		xtype : 'textarea',
		maxLength : 20,
		layout : 'column',
		width : 300
	}],
	
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
				if(me.isUpdate){//如果是修改
					me.loadRecord(new Foss.baseinfo.heavyBubbleRatio.Model(me.up('window').infoModel));//加载model到增加、修改的表单
				}else{ //如果是重置
					me.getForm().reset();
				}
			}
		},{
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});
/***********************定义新增、修改的的窗口*****************************/
//定义新增窗口
Ext.define('Foss.baseinfo.heavyBubbleRatio.AddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleRatio.AddWindow'),//新增重泡比信息
	width : 700,
	height : 400,
	isUpdate : false,//是否为修改，默认为false
	parent : null,  //父元素
	infoModel : null, //保存信息model
	modal : true, //为true，窗口掩饰它背后的一切
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners : {
		beforehide : function(me){//隐藏Window的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getPagingToolbar().moveFirst();//移动到分页的第一页，和点击first按钮效果是一样的
		},
		beforeshow : function(me){
			
		}
	},
	//获取新增窗口的form
	getAddUpdateForm : function (){
		if(this.addUpdateForm == null){
			this.addUpdateForm = Ext.create('Foss.baseinfo.heavyBubbleRatio.AddUpdateForm');//获取新增(修改)窗口的表单form
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	bindData : null,
	operationUrl : 'save',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function (){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-heavyBubbleRatio_content').getQueryResultGrid();//获取查询列表
		}
	},
	resetWindow : function (record,operationUrl){
		this.getAddUpdateForm().loadRecord(record);//重新加载
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([cfg]);
	}
});

//定义修改的窗口
Ext.define('Foss.baseinfo.heavyBubbleRatio.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleRatio.UpdateWindow'),//修改重泡比信息
	width : 700,
	height : 400,
	isUpdate : true,//是否为修改动作，默认是false
	parent : null,	//父元素
	infoModel : null, //保存信息model
	modal : true,//当为真true时，掩饰它背后一切
	closeAction : 'hidden',//后面是‘hidden’的时候，当关闭之后还以再次通过show方法再次渲染
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listener : {
		beforehide : function (me){
			me.getAddUpdateForm().getForm().reset(); //重置重置
			me.parent.getPagingToolbar().moveFirst(); //获取分页组件，然后显示到第一页
		}
	},
	//获取form
	getAddUpdateForm : function (){
		if(this.addUpdateForm == null){
			this.addUpdateForm = Ext.create('Foss.baseinfo.heavyBubbleRatio.UpdateForm');//获取修改form
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	bindData : null,
	operationUrl : 'save',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function (){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-heavyBubbleRatio_content').getQueryResultGrid();//获取查询列表
		}
	},
	resetWindow : function (record,operationUrl){
		
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([cfg]);
	}
	
});

/***********************查询结果列表grid*****************************/
Ext.define('Foss.baseinfo.heavyBubbleRatio.QueryResultGrid',{
	extend : 'Ext.grid.Panel',
	title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.queryGrid'),//查询列表,---yinyong--
	id : 'Foss_baseinfo_heavyBubbleRatio_queryResultGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	frame : true,//表格对象边框线
	columnLines : true,//增加表格线
	stripeRows : true,//实现各行换色
	collapsible : true,//可收缩展开
	animCollapse : true,//动画的效果展开或收缩
	selModel : Ext.create('Ext.selection.CheckboxModel'),//一个选择模式它将渲染一列可以选中或者反选的复选框. 默认选择模型是多选
	store : null,
	//表格行可展开的插件
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : false,//定义行展开模式（单行或多行），默认是多行展开（值为true时候）
		rowBodyElement : 'Foss.baseinfo.heavyBubbleRatio.InfoPanel'//定义行体的内容,又是一个panel,里面放form
	}],
	
	//定义新增窗口的函数
	addWindow : null,
	getAddWindow : function (){
		if (Ext.isEmpty(this.addWindow)){
			this.addWindow = Ext.create('Foss.baseinfo.heavyBubbleRatio.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	//定义一个修改窗口的函数
	updateWindow : null,
	getUpdateWindow : function (){
		if(Ext.isEmpty(this.updateWindow)){
			this.updateWindow = Ext.create('Foss.baseinfo.heavyBubbleRatio.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function (){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length < 1){//判断是否选至少中了一条                                          -----------yinyong---------------------
			me.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.deleteNoticeMsg'));//弹出提示语：请选择一条数据进行作废操作！
			return ;//没有则提示返回
		}
		
		
		Ext.Msg.confirm(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.deleteWarnMsg'),function(e){// 作废数据后不可恢复，确定要作废么？---------yinyong------
			Ext.MessageBox.buttonText.yes = baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.confirm');//确定
			Ext.MessageBox.buttonText.no = baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.cancel');//取消
			if(e == 'yes'){ //询问是否删除
				var ids = new Array();//定义一个存放虚拟编码的数组
				for(var i = 0;i< selections.length;i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('deleteHeavyBubbleRatio.action');
				var jsonData = {'heavyBubbleRatioVo' : {'idList' : ids}};
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
				me.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);
			},
			//保存失败
			exception : function (){
				var json = Ext.decode(response.responseText);
				me.showWarningMsg(baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'),json.message);
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
			header : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.sequence'),//序号
			xtype: 'rownumberer',
			width : 50,
			align : 'center'
	},{
		xtype : 'actioncolumn',//一个用于简化列定义的子类，用来在单元格里渲染一个或多个图标。 每个图标均可自定义点击处理事件。
		width : 100,
		text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.operate'),//操作
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_edit',  //引用编辑图标样式
			tooltip : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.edit'),// 设置提示框 ： 编辑
			//编辑事件
			handler : function (grid,rowIndex,colIndex){
				//获取选中的数据
				var record = grid.getStore().getAt(rowIndex);
				//获得修改窗口
				var updateWindow = grid.up('grid').getUpdateWindow();
//				//为了点击编辑的时候，外场：显示的是名字而不是code
//				var outfield = record.data.outfieldName;
//				if(!Ext.isEmpty(outfield)){
//					record.set('outfield',outfield);	
//				}
				updateWindow.resetWindow(record,'update');
				updateWindow.show();
			}
		},{
			iconCls : 'deppon_icons_delete', //引用作废图标样式
			tooltip : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.void'),//作废
			handler : function (grid,rowIndex,colIndex){
				//作废操作提示窗口
				Ext.Msg.show({
					title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notice'), //提示消息
					msg : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.deleteWarnMsg'),//作废数据后不可恢复，确定要作废么？
					buttons : Ext.Msg.YESNO,//按钮显示 YES 和 NO的配置
					icon : Ext.Msg.QUESTION,//提供QUESTION图标图片的CSS样式
					fn : function (btn){
						if (btn == 'yes'){
							var record = grid.getStore().getAt(rowIndex);//获取选中的数据
							var ids = new Array();//创建一个虚拟编码数组
							ids.push(record.data.id);
							var url = baseinfo.realPath('deleteHeavyBubbleRatio.action');
							var jsonData = {'heavyBubbleRatioVo' : {'idList' : ids}};
							//调用ajax请求
							grid.up('grid').ajaxRequest(url,jsonData);
						}
					}
				});
			}
		}]
	},{
		header : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.fieldID'),//外场编号
		dataIndex : 'outfield',//必须属性，关联到model中的field，要显示的属性
		width : 190,
		align : 'center'
	},{
		header : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.fieldName'),//外场名字
		dataIndex : 'outfieldName',//必须属性，关联到model中的字段
		width : 190,
		align : 'center'
	},{
		header : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleParam'),//重泡比参数
		dataIndex : 'heavyBubbleParam',//必须属性，关联到model中的字段
		width : 120,
		align : 'center'
	},{
		header : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.notes'),//备注
		dataIndex : 'remark',//必须属性，关联到model中的字段
		width : 319,
		align : 'center'
	}],
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.baseinfo.heavyBubbleRatio.Store');
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
				text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.add'),//增加--------yinyong---------
				width : 80,
				handler : function (){
					this.addWindow = me.getAddWindow();
					this.addWindow.show();
					
				}
			},{
				xtype : 'button',
				text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.void'),//作废
				width : 80,
				handler : function (){
					me.deleteInfos();
				}
			}]
		}],
		me.callParent([cfg]);
	}
	
});

/***********************查询条件表单form*****************************/
Ext.define('Foss.baseinfo.heavyBubbleRatio.QueryConditonForm',{
	extend : 'Ext.form.Panel',
	title : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleRatio'),//重泡比信息查询,
	id : 'Foss_baseinfo_heavyBubbleRatio_queryForm_Id',
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
	items : [{
		fieldLabel :  baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.outfield'), //外场
		xtype : 'dynamicorgcombselector',
		transferCenter:'Y',//--或者查询外场
       // airDispatch  : 'Y',//--空运配载
		type : 'ORG',
		name : 'outfield', //---外场--------yinyong----------
		levelses : '3',
		columnWidth:0.33
	},{
		fieldLabel : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.heavyBubbleParam'),//重泡比参数--------kongzhe,在国际化的脚本里也要写
		name : 'heavyBubbleParam',//-------kongzhe 重泡比参数
		maxLength : 20,
		columnWidth:0.33
	}],
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			xtype : 'button',
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.reset'), //------重置----yinyong
			width : 70,
			margin : '0 800 0 0',
			handler : function (){
				me.getForm().reset();
			}
		},{
			xtype : 'button',
			text : baseinfo.heavyBubbleRatio.i18n('foss.baseinfo.query'),//---------查询-----yinyong
			width : 70,
			cls : 'yellow_button',
			handler : function (){
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-heavyBubbleRatio_content').getQueryResultGrid().getPagingToolbar().moveFirst();//---zhuyi-这个方法要在查询grid列表中写--getPagingToolbar--
				}
			}
		}];
		me.callParent([cfg]);
	}
});

/***********************入口onReady*****************************/
Ext.onReady(function(){
	Ext.QuickTips.init();//设置提示信息
	//查询form
	var queryForm = Ext.create('Foss.baseinfo.heavyBubbleRatio.QueryConditonForm');
	//查询结果grid
	var queryResult = Ext.create('Foss.baseinfo.heavyBubbleRatio.QueryResultGrid');
	
	Ext.getCmp('T_baseinfo-heavyBubbleRatio').add(Ext.create('Ext.panel.Panel',{
		id:'T_baseinfo-heavyBubbleRatio_content',
		cls:'panelContentNToolbar',
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		//获得查询Form
		getQueryConditonForm : function (){
			return queryForm;
		},
		//获得查询结果grid
		getQueryResultGrid : function (){
			return queryResult;
		},
		items:[queryForm,queryResult]
	}));
});