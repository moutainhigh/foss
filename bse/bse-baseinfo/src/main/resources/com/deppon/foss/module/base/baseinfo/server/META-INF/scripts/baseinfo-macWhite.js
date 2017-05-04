/**
 * MAC地址白名单信息
 * 
 * @author:申威华
 * Build date: 2013-04-26
 * 
 */
//定义一个model
Ext.define('Foss.baseinfo.exchangeRate.OriginatingLineModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'macAddress',  //MAC地址
		type : 'string'
	}, {
		name : 'active', //是否有效
		type : 'string'
	},{
		name : 'createDate',     //创建人
		type : 'date'
	}, {
		name : 'userCode',     //工号
		type : 'string'
	}, {
		name : 'userName',     //姓名
		type : 'string'
	}, {
		name : 'deptName',     //部门名称
		type : 'string'
	},{
		name : 'bigZone',     //大区名称
		type : 'string'
	} ]
});

//创建一个汇率信息的store
Ext.define('Foss.baseinfo.exchangeRate.OriginatingLineStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.exchangeRate.OriginatingLineModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryMacWhites.action'),
		reader : {
			type : 'json',
			root : 'macWhiteVo.entityList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_MacWhite_QueryForm_Id').getForm();
			if (queryForm != null) {
				var macAddress = queryForm.findField('macAddress').getValue();
				var userCode = queryForm.findField('userCode').getValue();
				Ext.apply(operation, {
					params : {
						'macWhiteVo.entity.macAddress':macAddress,
						'macWhiteVo.entity.userCode':userCode
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.exchangeRate.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.macWhite.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_MacWhite_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[ {
		name : 'macAddress',
		fieldLabel : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.macAddress'),//MAC地址
		maxLength:200
	},{
		name : 'userCode',
		fieldLabel : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userCode'),//工号
		maxLength:200
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.macWhite.i18n('foss.baseinfo.reset'),//重置
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.macWhite.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-macWhite_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});


// 汇率详细信息表单
Ext.define('Foss.baseinfo.exchangeRate.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	height : 200,
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
			name: 'macAddress',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.macAddress') //MAC地址
		},{
			name: 'userCode',
			decimalPrecision:0,
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userCode') //工号
		},{
			name: 'userName',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userName') //姓名
		},{
			name: 'deptName',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.deptName') //部门
		},{
			name: 'bigZone',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.bigZone') //大区
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

// 新增/修改汇率表单
Ext.define('Foss.baseinfo.exchangeRate.AddUpdateForm', {
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
        columns: 2
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
				record = Ext.create('Foss.baseinfo.exchangeRate.OriginatingLineModel');
			}
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			
    		var jsonData = {'macWhiteVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateMacWhite.action');//请求汇率修改
			}else{
				url = baseinfo.realPath('addMacWhite.action');//请求汇率新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-macWhite_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.macWhite.i18n('foss.baseinfo.notice'),json.message);
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.macWhite.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [ {
			name: 'macAddress',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.macAddress'),//MAC地址
	     //   regex:/^[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}:[A-F\d]{2}$/,
	        regex:/([0-9a-fA-F]{2})(([/\s:-][0-9a-fA-F]{2}){5})/,
	        allowBlank:false
		},{
			xtype : 'commonemployeeselector',
			name: 'userCode',
			decimalPrecision:0,
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userCode'),//工号
	        displayField : 'empCode',// 显示名称
	    	valueField : 'empCode',// 值
	        allowBlank:false,
	        listeners : {
	        	select:function(combo,records,eopts){
	        		var empName = records[0].get('empName');//姓名
	        		if(!Ext.isEmpty(empName)){
		        		//为员工姓名赋值
	        			combo.up('form').getForm().findField('userName').setValue(empName);
	        		}
	        		var department = records[0].get('department');
	        		if(!Ext.isEmpty(department.name)){
		        		//为员工部门赋值
		        		combo.up('form').getForm().findField('deptName').setValue(department.name);
		        		combo.up('form').getForm().findField('bigZone').setValue(department.name);
	        		}
	        	}
	        }
		},{
			readOnly : true,
			name: 'userName',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userName') //姓名
	        //allowBlank:false
		},{
			readOnly : true,
			name: 'deptName',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.deptName') //部门
	        //allowBlank:false
		},{
			readOnly : true,
			name: 'bigZone',
	        fieldLabel: baseinfo.macWhite.i18n('foss.baseinfo.macWhite.bigZone') //大区
	        //allowBlank:false
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.macWhite.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.macWhite.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.exchangeRate.OriginatingLineModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.macWhite.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

// 定义一个新增的窗口
Ext.define('Foss.baseinfo.exchangeRate.AddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.addMacWhite'),//'新增MAC地址信息'
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
			this.addUpdateForm = Ext.create('Foss.baseinfo.exchangeRate.AddUpdateForm');
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
			this.infoGrid = Ext.getCmp('T_baseinfo-macWhite_content').getOriginatingLineGrid();
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
Ext.define('Foss.baseinfo.exchangeRate.UpdateWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_exchangeRate_UpdateWindow_Id',
	title : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.updateMacWhite'),//'修改MAC地址'
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
		   /* me.getAddUpdateForm().getForm().loadRecord(new Foss.baseinfo.exchangeRate.OriginatingLineModel(me.infoModel));//加载线路信息
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
			this.addUpdateForm = Ext.create('Foss.baseinfo.exchangeRate.AddUpdateForm');
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
			this.infoGrid = Ext.getCmp('T_baseinfo-macWhite_content').getOriginatingLineGrid();
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



// 定义一个表格列表
Ext.define('Foss.baseinfo.exchangeRate.OriginatingLineGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_macWhite_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.macWhite.i18n('foss.baseinfo.queryGrid'),
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
		rowBodyElement : 'Foss.baseinfo.exchangeRate.InfoPanel'
	} ],
    
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.exchangeRate.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.exchangeRate.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.macWhite.i18n('foss.baseinfo.notice'),baseinfo.macWhite.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.macWhite.i18n('foss.baseinfo.notice'),baseinfo.macWhite.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.macWhite.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.macWhite.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放虚拟编码的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				
				var url = baseinfo.realPath('deleteMacWhite.action');
				var jsonData = {'macWhiteVo':{'idList':ids}};
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
                  me.showWarningMsg(baseinfo.macWhite.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.macWhite.i18n('foss.baseinfo.notice'),json.message);}
            
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
				text : baseinfo.macWhite.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
//							icon : '../images/baseinfo/edit.png',
                            iconCls:'deppon_icons_edit',
							tooltip : baseinfo.macWhite.i18n('foss.baseinfo.edit'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								//获得修改窗口
								var updateWindow = grid.up('grid').getUpdateWindow();
								updateWindow.resetWindow(record,'update');
								updateWindow.show();
							}
						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.macWhite.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.macWhite.i18n('foss.baseinfo.notice'),
									msg:baseinfo.macWhite.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											var ids = new Array();//虚拟编码数组
											ids.push(record.data.id);
											var url = baseinfo.realPath('deleteMacWhite.action');
											var jsonData = {'macWhiteVo':{'idList':ids}};
											//调用Ajax请求
											grid.up('grid').ajaxRequest(url,jsonData);
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.macAddress'),//MAC地址
				// 关联model中的字段名
				dataIndex : 'macAddress',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userCode'),//工号
				// 关联model中的字段名
				dataIndex : 'userCode',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.userName'),//姓名
				// 关联model中的字段名
				dataIndex : 'userName',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.deptName'),//部门
				// 关联model中的字段名
				dataIndex : 'deptName',
				width : 150
			},{
				// 字段标题
				header : baseinfo.macWhite.i18n('foss.baseinfo.macWhite.bigZone'),//大区
				// 关联model中的字段名
				dataIndex : 'bigZone',
				width : 150
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.exchangeRate.OriginatingLineStore');
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
						text : baseinfo.macWhite.i18n('foss.baseinfo.add'),
						width : 80,
						handler : function() {// 作废多项选中的记录
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.macWhite.i18n('foss.baseinfo.void'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							me.deleteInfos();
						}
					}
					]
		}], 
		me.callParent([ cfg ]);
	}
	});

// 查看记录的详细信息
Ext.define('Foss.baseinfo.exchangeRate.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.macWhite.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取汇率详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.exchangeRate.DetailForm');
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
		// 绑定表格数据到表单上
		me.getInfoForm().getForm().loadRecord(record);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.exchangeRate.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.exchangeRate.OriginatingLineGrid');

	Ext.getCmp('T_baseinfo-macWhite').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-macWhite_content',
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