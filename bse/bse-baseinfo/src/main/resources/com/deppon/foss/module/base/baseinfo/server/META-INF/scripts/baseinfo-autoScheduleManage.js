/**
 * 自动调度开启和关闭信息
 * 
 * @author:yangkang
 * Build date: 2014-04-22
 * 
 */
//定义一个model
Ext.define('Foss.baseinfo.autoScheduleManage.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'deptName',  //部门名称
		type : 'string'
	}, {
		name : 'startTime', //开始时间
		type : 'string'
	}, {
		name : 'endTime',  //结束时间
		type : 'string'
	}, {
		name : 'remark',     //备注
		type : 'string'
	}, {
		name : 'modifyUser',  //修改人姓名
		type : 'string'
	},{
		name : 'modifyUserPsncode', //修改人工号
		type : 'string'
	},{
		name : 'modifyDate',     //修改时间
		type : 'date',
		convert:dateConvert
	} ]
});

//创建一个自动调度管理的store
Ext.define('Foss.baseinfo.autoScheduleManage.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.autoScheduleManage.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryautoScheduleManages.action'),
		reader : {
			type : 'json',
			root : 'vo.entityList',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_autoScheduleManage_QueryForm_Id').getForm();
			if (queryForm != null) {
				var deptName = queryForm.findField('deptName').getValue();
				Ext.apply(operation, {
					params : {
						'vo.entity.deptName':deptName //部门名称
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.autoScheduleManage.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.autoScheduleManage.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_autoScheduleManage_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[
		    {
				xtype : 'textfield',
				columnWidth : 0.3,
				name : 'deptName',
				fieldLabel : '部门名称'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/autoResetButton'),
			hidden:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/autoResetButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('deptName').setValue('');
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/autoResetButton'),
			hidden:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/autoResetButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-autoScheduleManage_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});

// 新增/修改自动调度管理信息表单
Ext.define('Foss.baseinfo.autoScheduleManage.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 300,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    //提交表单
    commitInfo : function(){
    	var me = this;
    	//获取表单
    	var basicForm = this.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	
    		//获取model实例
			var record = null;
			
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{
				record =new Foss.baseinfo.autoScheduleManage.OriginatingLineModel();
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			//设置部门名称，部门编码，开始时间，结束时间，备注
			var deptName =basicForm.findField('deptName').rawValue,
			deptCode =basicForm.findField('deptName').getValue(),
			startTime =basicForm.findField('startTime').getRawValue( ),
			endTime =basicForm.findField('endTime').getRawValue( ),
			remark =basicForm.findField('remark').getValue();
			
			record.set('deptName',deptName);
			record.set('startTime',startTime);
			record.set('endTime',endTime);
			record.set('remark',remark);
			record.set('deptCode',deptCode);
    		var jsonData = {'vo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateautoScheduleManage.action');//请求自动调度信息修改
			}else{
				url = baseinfo.realPath('addautoScheduleManage.action');//请求自动调度信息新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-autoScheduleManage_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [{
			xtype : 'dynamicorgcombselector',
			type : 'ORG',
			columnWidth : 0.6,
			name : 'deptName',
			//valueField : 'deptName',// 这个参数，只需与实体中的某个字段对应即可
			allowBlank:false,
			forceSelection : true,
			fieldLabel : baseinfo.autoScheduleManage.i18n('foss.baseinfo.orgName')
	},{
			xtype: 'timefield',
	        name: 'startTime',
	        fieldLabel: '开始时间',
	        minValue: '00:00',
	        maxValue: '23:59',
	        format: 'H:i',
	        increment: 1,
	        anchor: '100%',
	        value:'08:00',
	        allowBlank:false
		},
		{
			xtype: 'timefield',
	        name: 'endTime',
	        fieldLabel: '结束时间',
	        minValue: '00:00',
	        maxValue: '23:59',
	        increment: 1,
			format:'H:i',//24小时制	
			anchor: '100%',
			value:'18:00',
	        allowBlank:false
		},
		{
			name: 'remark',
	        fieldLabel: '备注',
	        allowBlank: true,
			maxLength:100,
			width:600,
		    xtype:'textareafield'
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.autoScheduleManage.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.autoScheduleManage.OriginatingLineModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

//修改自动调度管理信息表单
Ext.define('Foss.baseinfo.autoScheduleManage.UpdateExchForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 300,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
     type: 'table',
     columns: 1
 },
 //提交表单
 commitInfo : function(){
 	var me = this;
 	//获取表单
 	var basicForm = this.getForm();
 	if(basicForm.isValid()){//校验form是否通过校验
 	
 		//获取model实例
			var record = null;
			
			if(me.isUpdate){
				record = basicForm.getRecord();//修改
			}else{
				record =new Foss.baseinfo.autoScheduleManage.OriginatingLineModel();
			}
			
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			//更新开始时间，结束时间，备注
			var startTime =basicForm.findField('startTime').getRawValue( ),
			endTime =basicForm.findField('endTime').getRawValue( ),
			remark =basicForm.findField('remark').getValue();
			
			record.set('startTime',startTime);
			record.set('endTime',endTime);
			record.set('remark',remark);
			
 		var jsonData = {'vo':{entity:record.data}};
 		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateautoScheduleManage.action');//请求修改
			}else{
				url = baseinfo.realPath('addautoScheduleManage.action');//请求新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-autoScheduleManage_content').getOriginatingLineGrid(); 
         Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
	items : [{
		name: 'deptName',
        fieldLabel: '部门名称',
        allowBlank:false,
        readOnly:true,
        xtype : 'textfield'
	},{
		xtype: 'timefield',
        name: 'startTime',
        fieldLabel: '开始时间',
        minValue: '00:00',
        maxValue: '23:59',
        format: 'H:i',
        increment: 1,
        anchor: '100%',
        allowBlank:false
	},
	{
		xtype: 'timefield',
        name: 'endTime',
        fieldLabel: '结束时间',
        minValue: '00:00',
        maxValue: '23:59',
        increment: 1,
		format:'H:i',//24小时制	
		anchor: '100%',
        allowBlank:false
	},{
		name: 'remark',
        fieldLabel: '备注',
        allowBlank: true,
		maxLength:100,
		width:600,
	    xtype:'textareafield'
	}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.autoScheduleManage.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});
// 定义一个新增的窗口
Ext.define('Foss.baseinfo.autoScheduleManage.AddWindow', {
	extend : 'Ext.window.Window',
	title : '新增自动调度管理信息',//'新增自动调度管理信息'
	width : 700,
	height : 400,
	isUpdate:false,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){//窗口显示之前事件
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.autoScheduleManage.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-autoScheduleManage_content').getOriginatingLineGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([ cfg ]);
	}
});

// 定义一个修改的窗口
Ext.define('Foss.baseinfo.autoScheduleManage.UpdateWindow', {
	extend : 'Ext.window.Window',
	title : '修改自动调度管理信息',//'修改'
	width : 700,
	height : 400,
	isUpdate:true,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){//窗口显示之前事件
		   /* me.getAddUpdateForm().getForm().loadRecord(new Foss.baseinfo.autoScheduleManage.OriginatingLineModel(me.infoModel));//加载线路信息
		    //选择器赋值
		    me.getAddUpdateForm().getForm().findField('organizationCode').setCombValue(me.infoModel.organizationName,me.infoModel.organizationCode);
		    me.getAddUpdateForm().getForm().findField('destinationOrganizationCode').setCombValue(me.infoModel.destinationOrganizationName,me.infoModel.destinationOrganizationCode);
		    me.getAddUpdateForm().getForm().findField('orginalOrganizationCode').setCombValue(me.infoModel.orginalOrganizationName,me.infoModel.orginalOrganizationCode);
			if(!Ext.isEmpty(me.standardList)){
				me.getStartStandardGrid().getStore().loadData(me.standardList);//加载发车标准信息
			}*/
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.autoScheduleManage.UpdateExchForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	startStandardGrid : null,
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-autoScheduleManage_content').getOriginatingLineGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.getAddUpdateForm().getForm().findField('deptName').setValue(record.get('deptName'));
		this.getAddUpdateForm().getForm().findField('startTime').setRawValue(record.get('startTime'));
		this.getAddUpdateForm().getForm().findField('endTime').setRawValue(record.get('endTime'));
		this.getAddUpdateForm().getForm().findField('remark').setValue(record.get('remark'));
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm()];
		me.callParent([ cfg ]);
	}
});



// 定义一个表格列表
Ext.define('Foss.baseinfo.autoScheduleManage.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_autoScheduleManage_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.autoScheduleManage.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,  
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.autoScheduleManage.InfoPanel'
	} ],
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.autoScheduleManage.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.autoScheduleManage.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据

		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),baseinfo.autoScheduleManage.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		Ext.Msg.confirm(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),baseinfo.autoScheduleManage.i18n('foss.baseinfo.deleteWarnMsgAutoScheduling'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.autoScheduleManage.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.autoScheduleManage.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放虚拟编码的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				var url = baseinfo.realPath('deleteautoScheduleManage.action');
				var jsonData = {'vo':{'codeList':ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.autoScheduleManage.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
	//Ajax请求
	requestAjax : function(url,jsonData,successFn,failFn)
	{
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
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
			}
		});
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
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
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
                    iconCls:'deppon_icons_edit',
					tooltip : baseinfo.autoScheduleManage.i18n('foss.baseinfo.edit'),
					disabled:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/autoEditButton'),
					// 编辑事件
					handler : function(grid, rowIndex,colIndex) {
						//获取选中的数据
						var record = grid.getStore().getAt(rowIndex);
						//获得修改窗口
						var updateWindow = grid.up('grid').getUpdateWindow();
						updateWindow.resetWindow(record,'update');
						updateWindow.show();
					}
				}]
	},{
				// 字段标题
				header : '部门名称',
				// 关联model中的字段名
				dataIndex : 'deptName',
				flex : 1
			}, {
				// 字段标题
				header : '开始时间',
				// 关联model中的字段名
				dataIndex : 'startTime',
				width : 130
			}, {
				// 字段标题
				header : '结束时间',//'结束时间'
				// 关联model中的字段名
				dataIndex : 'endTime',
				width : 130
			}, {
				// 字段标题
				header : '备注',//'备注'
				// 关联model中的字段名
				dataIndex : 'remark',
				width : 150
			}, {
				// 字段标题
				header : '修改人',
				// 关联model中的字段名
				dataIndex : 'modifyUser',
				width : 80
			},{
				// 字段标题
				header : '工号',
				// 关联model中的字段名
				dataIndex : 'modifyUserPsncode',
				width : 80
			},{
				// 字段标题
				header : '修改时间',//'操作时间'
				// 关联model中的字段名
				dataIndex : 'modifyDate',
				width : 130,
				renderer:function(value){
					if(!Ext.isEmpty(value)){
						return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					}else{
						return null;
					}
				}
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.autoScheduleManage.OriginatingLineStore');
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
						text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/exchangeAddButton'),
						hidden:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/exchangeAddButton'),
						width : 80,
						handler : function() {// 新增自动调度信息
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.autoScheduleManage.i18n('foss.baseinfo.void'),
						disabled:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/exchangeVoidButton'),
						hidden:!baseinfo.autoScheduleManage.isPermission('autoScheduleManage/exchangeVoidButton'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							me.deleteInfos();
						}
					}
					]
		}], 
		me.bbar =me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([ cfg ]);
	}
	});
//查看自动调度记录的详细信息
Ext.define('Foss.baseinfo.autoScheduleManage.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.autoScheduleManage.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取自动调度详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.autoScheduleManage.DetailForm');
		}
		return this.infoForm;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [ this.getInfoForm()];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		me.getInfoForm().getForm().loadRecord(record);
	}
});
//详细自动调度信息表单
Ext.define('Foss.baseinfo.autoScheduleManage.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	height : 250,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
	items : [ {
			name: 'deptName',
	        fieldLabel: '部门名称'
		},{
			name: 'startTime',
	        fieldLabel: '开始时间'
		},{
			name: 'endTime',
	        fieldLabel: '结束时间'
		},{
			name: 'modifyUser',
	        fieldLabel: '工号'
		},{
			name: 'modifyUserPsncode',
	        fieldLabel: '修改人'
		},{
			xtype:'datefield',
			name: 'modifyDate',
	        fieldLabel: '修改时间',
			format:'Y-m-d H:i:s'
		},{
			name: 'remark',
			colspan: 2,
	        fieldLabel: '备注',
    		maxLength:100,
    		width:600,
    	    xtype:'textareafield'
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.autoScheduleManage.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.autoScheduleManage.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-autoScheduleManage').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-autoScheduleManage_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getOriginatingLineGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
	}));
});