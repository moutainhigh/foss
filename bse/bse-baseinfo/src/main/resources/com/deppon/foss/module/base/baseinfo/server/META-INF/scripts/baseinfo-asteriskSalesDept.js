/**
 * 星标营业部信息
 * 
 * @author:132599-foss-shenweihua
 * Build date: 2013-05-06
 * 
 */
//定义一个model
Ext.define('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'virtualCode',  //虚拟编码
		type : 'string'
	}, {
		name : 'salesDeptCode',       //营业部部门编码
		type : 'string'
	}, {
		name : 'salesDeptName', //营业部部门名称
		type : 'string'
	}, {
		name : 'asteriskCode',     //星标编码(对应数据字典)
		type : 'string'
	}, {
		name : 'notes',     //备注
		type : 'string'
	}, {
		name : 'versionNo',     //版本号
		type : 'number'
	},{
		name : 'active', //是否有效
		type : 'string'
	}, {
		name : 'createUser',     //创建人
		type : 'string'
	}, {
		name : 'createDate',     //创建时间
		type : 'date'
	}, {
		name : 'modifyUser',     //修改人
		type : 'string'
	}, {
		name : 'modifyDate',     //修改时间
		type : 'date'
	} ]
});


//创建一个星标营业部信息的store
Ext.define('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryAsteriskSalesDepts.action'),
		reader : {
			type : 'json',
			root : 'asteriskSalesDeptVo.entityList',
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
			var queryForm = Ext.getCmp('Foss_baseinfo_asteriskSalesDept_QueryForm_Id').getForm();
			if (queryForm != null) {
				var salesDeptCode = queryForm.findField('salesDeptCode').getValue();
				Ext.apply(operation, {
					params : {
						'asteriskSalesDeptVo.entity.salesDeptCode':salesDeptCode
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.asteriskSalesDept.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_asteriskSalesDept_QueryForm_Id',
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
				xtype : 'commonsaledepartmentselector' ,
				name:"salesDeptCode",
				fieldLabel:baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.salesDeptCode'),//营业部
				displayField : 'name',// 显示名称
		    	valueField : 'code',// 值
				labelWidth :120
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.reset'),//重置
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					Ext.getCmp('T_baseinfo-asteriskSalesDept_content').getOriginatingLineGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});


// 星标部门详细信息表单
Ext.define('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptForm', {
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
			name: 'salesDeptCode',
	        fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.deptCode') //部门编码
		},{
			name: 'salesDeptName',
			decimalPrecision:0,
	        fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.salesDeptName') //部门名称
		},{
			name: 'asteriskCode',
			decimalPrecision:0,
	        fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.asteriskCode') //星标编码
		},{
			name: 'notes',
			decimalPrecision:0,
	        fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.notes') //描述
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

// 新增/修改星标部门表单
Ext.define('Foss.baseinfo.asteriskSalesDept.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 220,
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
				record = Ext.create('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptModel');
			}
			//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(record);
			
    		var jsonData = {'asteriskSalesDeptVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateAsteriskSalesDept.action');//请求星标部门修改
			}else{
				url = baseinfo.realPath('addAsteriskSalesDept.action');//请求星标部门新增
			}
			var infoGrid = Ext.getCmp('T_baseinfo-asteriskSalesDept_content').getOriginatingLineGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),json.message); //提示消息
					  me.up('window').close();//关闭该window
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [ {
				xtype : 'commonsaledepartmentselector' ,
				name:"salesDeptCode",
				fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.salesDeptName'), //部门名称
				displayField : 'name',// 显示名称
		    	valueField : 'code',// 值
		    	allowBlank:false,
				labelWidth :120,
				listeners : {
		        	select:function(combo,records,eopts){
		        		var salesDeptName = records[0].get('name');//部门名称
		        		if(!Ext.isEmpty(salesDeptName)){
			        		//为员工姓名赋值
		        			combo.up('form').getForm().findField('salesDeptName').setValue(salesDeptName);
		        		}
		        	}
		        }
	    },{
	    	hidden:true,
	    	readOnly : true,
			name: 'salesDeptName',
	        fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.salesDeptName') //部门名称
		},{
			fieldLabel: baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.notes'), //描述
			name:'notes',
		    allowBlank:true,
		    maxLength:200,
		    width:400,
		    colspan : 2,
			xtype:'textareafield'
		}],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.asteriskSalesDept.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.loadRecord(new Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			}];
			me.callParent([cfg]);
		}
});

// 定义一个新增的窗口
Ext.define('Foss.baseinfo.asteriskSalesDept.AddWindow', {
	extend : 'Ext.window.Window',
	title : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.addAsteriskSalesDept'),//新增星标部门信息
	width : 700,
	height : 320,
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
			this.addUpdateForm = Ext.create('Foss.baseinfo.asteriskSalesDept.AddUpdateForm');
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
			this.infoGrid = Ext.getCmp('T_baseinfo-asteriskSalesDept_content').getOriginatingLineGrid();
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
Ext.define('Foss.baseinfo.asteriskSalesDept.UpdateWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_exchangeRate_UpdateWindow_Id',
	title : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.updateAsteriskSalesDept'),//修改星标部门信息
	width : 700,
	height : 320,
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
		   /* me.getAddUpdateForm().getForm().loadRecord(new Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptModel(me.infoModel));//加载线路信息
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
			this.addUpdateForm = Ext.create('Foss.baseinfo.asteriskSalesDept.AddUpdateForm');
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
			this.infoGrid = Ext.getCmp('T_baseinfo-asteriskSalesDept_content').getOriginatingLineGrid();
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
Ext.define('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_asteriskSalesDept_OriginatingLineGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 定义表格的标题
	title : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.queryGrid'),
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
			this.addWindow = Ext.create('Foss.baseinfo.asteriskSalesDept.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.asteriskSalesDept.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),baseinfo.asteriskSalesDept.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),baseinfo.asteriskSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.asteriskSalesDept.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.asteriskSalesDept.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var virtualCodes = new Array(); //定义一个存放虚拟编码的数组
				for(var i = 0 ; i<selections.length ; i++){
					virtualCodes.push(selections[i].get('virtualCode'));
				}
				
				var url = baseinfo.realPath('deleteAsteriskSalesDept.action');
				var jsonData = {'asteriskSalesDeptVo':{'asteriskDeptVirtualCodes':virtualCodes}};
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
                  me.showWarningMsg(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),json.message);
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
				text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [
//				         {
//                            iconCls:'deppon_icons_edit',
//							tooltip : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.edit'),
//							// 编辑事件
//							handler : function(grid, rowIndex,colIndex) {
//								//获取选中的数据
//								var record = grid.getStore().getAt(rowIndex);
//								//获得修改窗口
//								var updateWindow = grid.up('grid').getUpdateWindow();
//								updateWindow.resetWindow(record,'update');
//								updateWindow.show();
//							}
//						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.asteriskSalesDept.i18n('foss.baseinfo.notice'),
									msg:baseinfo.asteriskSalesDept.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											var ids = new Array();//虚拟编码数组
											ids.push(record.data.virtualCode);
											var url = baseinfo.realPath('deleteAsteriskSalesDept.action');
											var jsonData = {'asteriskSalesDeptVo':{'asteriskDeptVirtualCodes':ids}};
											//调用Ajax请求
											grid.up('grid').ajaxRequest(url,jsonData);
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.deptCode'), //部门编码
				// 关联model中的字段名
				dataIndex : 'salesDeptCode',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.salesDeptName'), //部门名称
				// 关联model中的字段名
				dataIndex : 'salesDeptName',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.asteriskCode'), //星标编码
				// 关联model中的字段名
				dataIndex : 'asteriskCode',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.asteriskSalesDept.notes'), //描述
				// 关联model中的字段名
				dataIndex : 'notes',
				flex : 1,
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptStore');
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
						text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.add'),
						width : 80,
						handler : function() {// 作废多项选中的记录
                            this.addWindow = me.getAddWindow();
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.void'),
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

//查看记录的详细信息
Ext.define('Foss.baseinfo.exchangeRate.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.asteriskSalesDept.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取汇率详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptForm');
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
	var queryForm = Ext.create('Foss.baseinfo.asteriskSalesDept.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.asteriskSalesDept.AsteriskSalesDeptGrid');

	Ext.getCmp('T_baseinfo-asteriskSalesDept').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-asteriskSalesDept_content',
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