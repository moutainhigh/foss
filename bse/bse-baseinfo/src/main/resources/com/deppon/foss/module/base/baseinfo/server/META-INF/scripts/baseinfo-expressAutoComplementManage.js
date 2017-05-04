/**
 * 快递自动补码管理
 * 
 * @author 218392 张永雪
 * Build data 2015-05-04 14:23:19
 * 
 */

/*****************************定义Model*********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.Model',{
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},{
		name : 'cityName',//城市名称
		type : 'string'
	},{
		name : 'provinceName',//省份名称
		type : 'string'
	},{
		name : 'cityCode',//城市code
		type : 'string'
	},{
		name : 'status',//状态——关闭：1，静默：2，开启：3
		type : 'string'
	},{
		name : 'notes',//备注
		type : 'string'
	},{
		name : 'jobNumber',//工号 
		type : 'string'
	},{
		name : 'operator',//操作人姓名
		type : 'string'
	},{
		name : 'operationTime',//操作时间
		type : 'date',
		convert: dateConvert,
		defaultValue:null
	}]
});

/********************************定义store****************************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.Store',{
	extend : 'Ext.data.Store',
	pageSize : 10,//定义页面条数
	model : 'Foss.baseinfo.expressAutoComplementManage.Model',//绑定Model
	proxy :{ //以json方式加载数据
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryExpressAutoComplementManage.action'),
		reader : {
			type : 'json', 
			root : 'expressAutoComplementManageVo.expressAutoComplementManageEntityList',  
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
			var queryForm = Ext.getCmp('Foss_baseinfo_expressAutoComplementManage_queryForm_Id').getForm();
			if(queryForm != null){
				var provinceCode = queryForm.findField('provinceCode').getValue();
				var cityCode = queryForm.findField('cityCode').getValue();
				var status = queryForm.findField('status').getValue();
				Ext.apply(operation,{
					params : {
						'expressAutoComplementManageVo.expressAutoComplementManageEntity.provinceCode' : provinceCode,
						'expressAutoComplementManageVo.expressAutoComplementManageEntity.cityCode' : cityCode,
						'expressAutoComplementManageVo.expressAutoComplementManageEntity.status' : status
					}
				});
			}
		} 
	}
	
});

/********************** 状态store *******************************/
Ext.define('Foss.baseinfo.expressPartSalesDept.StatusStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**************************** 定义新增窗口  **********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.AddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.AddWindowInfo'),//新增信息,这个国际化就采用这个
	width : 700,
	height : 400,
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
			this.addWindowForm = Ext.create('Foss.baseinfo.expressAutoComplementManage.AddWindowForm');//获取新增窗口的form
		}
		return this.addWindowForm;
	},
	bindData : null,
	operationUrl : 'save',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-expressAutoComplementManage_content').getQueryResultGrid();//获取查询列表
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
/**************************** 定义修改备注窗口 ****************************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.ModifyNotesWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.modifyNotesInfo'),//修改备注信息
	width : 450,
	height : 300,
	parent : null,//父元素
	infoModel : null,//保存信息model
	modal : true,//为true，窗口掩饰它背后的一切
	closeAction : 'hidden',//当标题头的关闭按钮被单击这个动作将被执行(默认为destroy)：这里设置为hide，hidden这个window通过将其的可见属性置为隐藏。这个window可以通过show方法再次渲染
	detailEntity : null,
	modifyNotesWindow : null,
	//监听器
	listeners : {
		beforehide : function(me){//隐藏Window的时候清除数据
			me.getModifyNotesWindowForm().getForm().reset();//重置
			me.parent.getPagingToolbar().moveFirst();//移动到分页的第一页，和点击first按钮效果是一样的
		},
		beforeshow : function(me){
		}
	},
	//获取修改备注窗口的Form
	getModifyNotesWindowForm : function(){
		if(Ext.isEmpty(this.modifyNotesWindowForm)){
			this.modifyNotesWindowForm = Ext.create('Foss.baseinfo.expressAutoComplementManage.ModifyNotesWindowForm');//获取修改备注窗口的form
		}
		return this.modifyNotesWindowForm;
	},
	bindData : null,
	operationUrl : 'update',
	//获取信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-expressAutoComplementManage_content').getQueryResultGrid();//获取查询列表
		}
	},
	resetWindow : function (record,operationUrl){
		this.getModifyNotesWindowForm().loadRecord(record);//重新加载
		
		//城市取值
//		var setCityCode = this.getModifyNotesWindowForm().getForm().findField('cityCode');
//		setCityCode.fieldInitValue = setCityCode.getValue();

		//状态取值
//		var setStatus = this.getModifyNotesWindowForm().getForm().findField('status');
//		setStatus.fieldInitValue = setStatus.getValue();
		
		//再重置时候，获取备注初始值,配合line402
		var setNotesField = this.getModifyNotesWindowForm().getForm().findField('notes');
		setNotesField.fieldInitValue = setNotesField.getValue();
		
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.items = [me.getModifyNotesWindowForm()];
		me.callParent([cfg]);
	}
});

/*****************************定义新增form表单*********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.AddWindowForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 300,
	collapsible : true,
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.add'),//新增
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
			record = Ext.create('Foss.baseinfo.expressAutoComplementManage.Model');
			basicForm.updateRecord(record);//将Form表单中的数据添加到设置到model中
			var jsonData = {'expressAutoComplementManageVo' : {expressAutoComplementManageEntity : record.data}};
			var url = null;
			url = baseinfo.realPath('addExpressAutoComplementManage.action');
			var infoGrid = Ext.getCmp('T_baseinfo-expressAutoComplementManage_content').getQueryResultGrid();//获取查询列表
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [{
		xtype : 'commoncityselector',
		forceSelection : true,
		fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.city'),//城市
		name : 'cityCode',//城市
		maxLength : 15,
		showContent : '{name}&nbsp;&nbsp;&nbsp;{code}',// 显示表格列
		allowBlank : false,
		columnWidth : 0.33
	},{
		xtype : 'combobox',
		name : 'status', //状态
		fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.Status'),//状态
		forceSelection:true,
		columnWidth : 0.33,
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		value : '1',
		store : Ext.create('Foss.baseinfo.expressPartSalesDept.StatusStore', {
			data : {
				'items' : [{
						'valueCode' : '1',
						'valueName' : '关闭'
					}, {
						'valueCode' : '2',
						'valueName' : '静默'
					}, {
						'valueCode' : '3',
						'valueName' : '开启'
					}
				]
			}
		})

	},{
		fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notes'),//备注
		name : 'notes',
		xtype : 'textarea',
		maxLength : 100,
		layout : 'column',
		width : 300
	}],
	//构造函数
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
				me.getForm().reset();
			}
		},{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});

/*****************************定义修改备注form表单*********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.ModifyNotesWindowForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	height : 200,
	collapsible : true,
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.modifyNotes'),//修改备注
	defaults : {
		margin : '20 15 10 1',
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
			record = basicForm.getRecord();
			basicForm.updateRecord(record);//将Form表单中的数据添加到设置到model中
			var jsonData = {'expressAutoComplementManageVo' : {expressAutoComplementManageEntity : record.data}};
			var url = null;
			url = baseinfo.realPath('updateExpressAutoComplementManage.action');
			var infoGrid = Ext.getCmp('T_baseinfo-expressAutoComplementManage_content').getQueryResultGrid();//获取查询列表
			Ext.Ajax.request({
				url : url,
				jsonData : jsonData,
				//成功
				success : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.getPagingToolbar().moveFirst(); //保存成功，将列表数据重新加载
					infoGrid.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);//引用其他的国际化“通知”
					me.up('window').close();//关闭该Window窗口
				},
				//失败
				exception : function (response){
					var json = Ext.decode(response.responseText);//解析JSON字符串
					infoGrid.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);//保存失败，显示提示信息
				}
			});
		}
	},
	items : [{
		fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notes'),//备注
		name : 'notes',
		xtype : 'textarea',
		maxLength : 100,
		layout : 'column',
		width : 350
	}],
	//构造函数
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.cancel'),//取消
			handler : function (){
				me.up().close();
			}
		},{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.reset'),//重置
			handler : function (){
				//me.getForm().reset();
				var setNotesField = me.getForm().findField('notes');
				setNotesField.setValue(setNotesField.fieldInitValue);
			}
		},{
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.save'),//保存
			cls : 'yellow_button',
			handler : function (){
				me.commitInfo();
			}
		}];
		me.callParent([cfg]);
	}
});

/**************************** 查询条件Form  **********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.QueryConditionForm',{
	extend : 'Ext.form.Panel',
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.queryCondition'),//快递自动补码查询条件
	id : 'Foss_baseinfo_expressAutoComplementManage_queryForm_Id',
	frame : true,
	collapsible : true,
	layout : {
		type : 'table',
		columns : 3
	},
	defaults : {
		labelSeparator : ':',
		margin : '12 5 5 10',
		anchor : '100%'
	},
	height : 180,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			xtype : 'container',
 			layout : 'column',
 			labelWidth : 0,
 			defaults : {
 				margin : '0 5 0 10'
 			},
 			items : [{
 				xtype : 'commonprovinceselector',
 				name : 'provinceCode',
 				forceSelection : true,
 				fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.province'),//省份
 				listeners : {
 					select : function(newValue, oldValue, eOpts) {
 						var city = this.up().query('commonCityByProvinceselector')[0];
 						if (null != city) {
 							city.parentId = oldValue[0].data.code;
 						}
 					}
 				}
 			}, {
 				fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.city'),//城市
 				forceSelection : true,
 				xtype : 'commonCityByProvinceselector',
 				name : 'cityCode',
 				queryMode : 'local',
 				listeners : {
 					expand : function(field, eOpts) {
// 						var province = field.up().query('commonprovinceselector')[0];
// 						if (null == province.getValue() || "" == province.getValue()) {
// 							Ext.MessageBox.alert('提示', '省份不能为空！');
// 							return;
// 						}
 						field.getStore().load({
 									params : {
 										"cityVo.parentId" : field.parentId
 									},
 									callback : function(records, operation,success) {
 									}
 								});
 					}
 				}
 			}]
 		},{
		xtype : 'combobox',
		name : 'status', //状态
		fieldLabel : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.Status'),//状态
		forceSelection:true,
		displayField : 'valueName',
		valueField : 'valueCode',
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		value : '',
		store : Ext.create('Foss.baseinfo.expressPartSalesDept.StatusStore', {
			data : {
				'items' : [{
						'valueCode' : '',
						'valueName' : '全部'
					}, {
						'valueCode' : '1',
						'valueName' : '关闭'
					}, {
						'valueCode' : '2',
						'valueName' : '静默'
					}, {
						'valueCode' : '3',
						'valueName' : '开启'
					}
				]
			}
		})
	}],
	constructor : function(config){
		var me = this,cfg = Ext.apply({},config);
		me.fbar = [{
			xtype : 'button',
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.reset'), //重置
			width : 70,
			margin : '0 800 0 0',
			handler : function (){
				me.getForm().reset();
			}
		},{
			xtype : 'button',
			text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageQueryButton'),
			hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageQueryButton'),
			width : 70,
			cls : 'yellow_button',
			handler : function (){
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-expressAutoComplementManage_content').getQueryResultGrid().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});

/**************************** 查询结果Grid  **********************************/
Ext.define('Foss.baseinfo.expressAutoComplementManage.QueryResultGrid',{
	extend : 'Ext.grid.Panel',
	title : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.QueryResultGrid'),//查询结果信息
	id : 'Foss_baseinfo_expressAutoComplementManage_queryResultGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	emptyText : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.temporaryRentalCarMarkTimeManagement.queryEmpty'),//查询结果为空,这个国际化就采用这个
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
			this.addWindow = Ext.create('Foss.baseinfo.expressAutoComplementManage.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	//定义修改备注窗口的函数
	modifyNotesWindow : null,
	getModifyNotesWindow : function(){
		if(Ext.isEmpty(this.modifyNotesWindow)){
			this.modifyNotesWindow = Ext.create('Foss.baseinfo.expressAutoComplementManage.ModifyNotesWindow');
			this.modifyNotesWindow.parent = this;//父元素
		}
		return this.modifyNotesWindow;
	},
	//开启操作
	openInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length < 1){//判断是否选至少中了一条 
			me.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.openNoticeMsg'));//弹出提示语：请选择一条数据进行开启操作！
			return ;//没有则提示返回
		}
		Ext.Msg.confirm(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.openWarnMsg'),function(e){//确定要开启？
			Ext.MessageBox.buttonText.yes = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.confirm');//确定
			Ext.MessageBox.buttonText.no = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.cancel');//取消
			if(e == 'yes'){ //询问是否删除
				var ids = new Array();//定义一个存放虚拟编码的数组
				for(var i = 0;i< selections.length;i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('openExpressAutoComplementManage.action');
				var jsonData = {'expressAutoComplementManageVo' : {'idList' : ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//关闭操作
	closeInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length < 1){//判断是否选至少中了一条 
			me.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.closeNoticeMsg'));//弹出提示语：请选择一条数据进行开启操作！
			return ;//没有则提示返回
		}
		Ext.Msg.confirm(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.closeWarnMsg'),function(e){//确定要关闭？
			Ext.MessageBox.buttonText.yes = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.confirm');//确定
			Ext.MessageBox.buttonText.no = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.cancel');//取消
			if(e == 'yes'){ //询问是否删除
				var ids = new Array();//定义一个存放虚拟编码的数组
				for(var i = 0;i< selections.length;i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('closeExpressAutoComplementManage.action');
				var jsonData = {'expressAutoComplementManageVo' : {'idList' : ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//静默操作
	silent : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length < 1){//判断是否选至少中了一条 
			me.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),'请至少选择一条数据进行静默操作！');//弹出提示语：请选择一条数据进行开启操作！
			return ;//返回
		}
		Ext.Msg.confirm(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),'确定要静默？',function(e){//确定要静默？
			Ext.MessageBox.buttonText.yes = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.confirm');//确定
			Ext.MessageBox.buttonText.no = baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.cancel');//取消
			if(e == 'yes'){ //询问是否
				var ids = new Array();//定义一个存放虚拟编码的数组
				for(var i = 0;i< selections.length;i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('silentExpressAutoComplementManage.action');
				var jsonData = {'expressAutoComplementManageVo' : {'idList' : ids}};
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
				me.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);
			},
			//保存失败
			exception : function (response){
				var json = Ext.decode(response.responseText);
				me.showWarningMsg(baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notice'),json.message);
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
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					plugins: 'pagesizeplugin',
					pageSize:10,
					prependButtons: true,
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
		width : 60,
		text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.operate'),//操作
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_edit',  //引用编辑图标样式
			// 设置提示框 ： 修改备注
			tooltip : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.modifyNotesInfo'),
			disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageModifyButton'),
			hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageModifyButton'),
			//编辑事件
			handler : function (grid,rowIndex,colIndex){
				//获取选中的数据
				var record = grid.getStore().getAt(rowIndex);
				grid.up('grid').getModifyNotesWindow().infoModel = record;
				//获得修改窗口
				var modifyNotesWindow = grid.up('grid').getModifyNotesWindow();
				modifyNotesWindow.resetWindow(record,'update');
				modifyNotesWindow.show();
			}
		}]
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.provinceName'),//省份名称
		dataIndex : 'provinceName',
		flex:0.8,
		xtype : 'ellipsiscolumn',//设置悬浮提示
		align : 'center'
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.cityName'),//城市名称
		dataIndex : 'cityName',
		flex:0.8,
		xtype : 'ellipsiscolumn',//设置悬浮提示
		align : 'center'
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.Status'),//状态
		dataIndex : 'status',
		flex:0.6,
		align : 'center',
		renderer:function(v){
			if('1'==v){
				return '关闭';
			}else if('2'==v){
				return '静默';
			}else if('3'==v){
				return '开启';
			}
		}
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.notes'),//备注
		dataIndex : 'notes',
		flex:1.4,
		xtype : 'ellipsiscolumn',//设置悬浮提示
		align : 'center'
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.expressAutoComplementManage.operationPeopel'),//操作人
		dataIndex : 'operator',
		flex:1,
		xtype : 'ellipsiscolumn',//设置悬浮提示
		align : 'center'
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.jobNumber'),//工号
		dataIndex : 'jobNumber',
		flex:1,
		xtype : 'ellipsiscolumn',//设置悬浮提示
		align : 'center'
	},{
		header : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.operationTime'),//操作时间
		dataIndex : 'operationTime',
		flex:1.2,
		align : 'center',
		renderer:function(v){
			if(!Ext.isEmpty(v)){
				return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
		    }else{
		    	return null;
		    }
		}
	}],
	//构造函数
	constructor : function (config){
		var me = this,cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.baseinfo.expressAutoComplementManage.Store');
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
				text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.add'),//新增
				disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageAddButton'),
				hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageAddButton'),
				width : 80,
				handler : function (){
					this.addWindow = me.getAddWindow();
					this.addWindow.show();
				}
			},{
				xtype : 'button',
				text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.open'),//开启
				disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageOpenButton'),
				hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageOpenButton'),
				width : 80,
				handler : function (){
					me.openInfos();
				}
			},{
				xtype : 'button',
				text : baseinfo.expressAutoComplementManage.i18n('foss.baseinfo.close'),//关闭
				disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageCloseButton'),
				hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageCloseButton'),
				width : 80,
				handler : function (){
					me.closeInfos();
				}
			},{
				xtype : 'button',
				text : '静默',
				disabled:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageCloseButton'),
				hidden:!baseinfo.expressAutoComplementManage.isPermission('expressAutoComplementManage/expressAutoComplementManageCloseButton'),
				width : 80,
				handler : function (){
					me.silent();
				}
			}]
		}],
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**************************** 入口onReady  **********************************/
Ext.onReady(function(){
	Ext.QuickTips.init();//设置悬浮提示
	//查询Form
	var queryForm = Ext.create('Foss.baseinfo.expressAutoComplementManage.QueryConditionForm');
	//查询结果Grid
	var queryGrid = Ext.create('Foss.baseinfo.expressAutoComplementManage.QueryResultGrid');
	
	Ext.getCmp('T_baseinfo-expressAutoComplementManage').add(Ext.create('Ext.panel.Panel',{
		id : 'T_baseinfo-expressAutoComplementManage_content',
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