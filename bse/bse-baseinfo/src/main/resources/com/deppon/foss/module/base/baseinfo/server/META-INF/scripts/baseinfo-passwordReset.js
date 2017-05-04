/**
 * 密码初始化 foss-zengjunfan
 */
/**
 * Ajax 请求的方法
 */
baseinfo.requestJsonAjax =function(url, params, successFn, failFn){
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result =Ext.decode(response.responseText);
			if (result.success) {
				successFn(result);
			} else {
				failFn(result);
			}
		},
		failure : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
},
/**
 * ---------------------------model----------------
 */
Ext.define('Foss.baseinfo.passwordReset.UserEntity',{
	extend:'Ext.data.Model',
	fields:[{
		//ID
		name:'id',
		type:'string'
	},{
		//工号
		name:'empCode',
		type:'string'
	},{
		//用户名
		name:'userName',
		type:'string'
	},{
		//密码
		name:'password',
		type:'string'
	},{
		//最后登录时间
		name:'lastLogin',
		type:'date'
	},{
		//是否激活
		name:'active',
		type:'string'
	},{
		//职位
		name:'title',
		type:'String'
	},{
		//有效时间
		name:'beginDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type:'date'
	},{
		//失效时间
		name:'endDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type:'date'
	},{
		//职位姓名
		name:'empName',
		type:'string'
	},{
		name:'version',
		type:'long'
	},{
		name : 'createUser',// 发布人
		type:'string'
	}, {
		name : 'createDate',// 创建时间
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	}, {
		name : 'modifyUser',// 修改人
		type:'string'
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date'
	}]
});
/**
 * ----------------用户store------------
 */
Ext.define('Foss.baseinfo.passwordReset.UserStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.passwordReset.UserEntity',// 绑定用户MODEL
	pageSize : 20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../baseinfo/queryAllUserEntity.action',// 查询的url
		reader : {
			type : 'json',
			root : 'vo.userEntitys',// 结果集
			totalProperty : 'totalCount'// 个数
		}
	}
});
/**
 * ----------------查询表单Form-----------------
 */
Ext.define('Foss.baseinfo.passwordReset.queryUserForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.passwordReset.i18n('foss.baseinfo.queryCondition'),// 查询条件
	frame : true,
	collapsible : true,
	defaults : {
		columnWidth : .35,
		maxLength : 50,
		margin : '8 10 5 10',
	    anchor : '100%'
	},
	 height :150,
	 defaultType : 'textfield',
	//列布局
	 layout:'column',
	 constructor:function(config){
		 var me =this, cfg =Ext.apply({},config);
		 me.items=[{
			 	name:'empCode',
			 	columnWidth: 0.3,
			 	labelWidth:60,
				fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.jobNumber'),//工号
				xtype :'textfield'
		 	},{
				xtype:'container' 
			},{
				border: 1,
				xtype:'container',
				columnWidth:1, 
				defaultType:'button',
				layout:'column',
				items:[{
					  text : baseinfo.passwordReset.i18n('foss.baseinfo.reset'),//重置
					  disabled:!baseinfo.passwordReset.isPermission('passwordReset/passwordResetRestButton'),
					  hidden:!baseinfo.passwordReset.isPermission('passwordReset/passwordResetRestButton'),
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
					  text : baseinfo.passwordReset.i18n('foss.baseinfo.query'),//查询
					  disabled:!baseinfo.passwordReset.isPermission('passwordReset/passwordResetQueryButton'),
					  hidden:!baseinfo.passwordReset.isPermission('passwordReset/passwordResetQueryButton'),
					  columnWidth:.08,
					  cls:'yellow_button',  
					  handler:function() {
						  //表单校验，分页查询
						if(me.getForm().isValid()){
							me.up().getUserGrid().getPagingToolbar().moveFirst();
						}
					  }
				  	}]
				}];
		 me.callParent([cfg]);
	 }
});
/**
 * 用户信息grid
 */
Ext.define('Foss.baseinfo.passwordReset.userInfoGridPanel',{
	extend:'Ext.grid.Panel',
	title:baseinfo.passwordReset.i18n('foss.baseinfo.queryGrid'),//查询结果列表
	frame: true,
	bodyCls:'autoHeight',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:baseinfo.passwordReset.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//修改
	valueUpdateWin:null,
	getValueUpdateWin:function(){
		if(this.valueUpdateWin==null){
			this.valueUpdateWin=Ext.create('Foss.baseinfo.passwordReset.valueUpdateWin');
			this.valueUpdateWin.parent = this;
		}
		return this.valueUpdateWin;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns=[{
			xtype: 'rownumberer',
			width:40,
			text :baseinfo.passwordReset.i18n('foss.baseinfo.sequence')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text:baseinfo.passwordReset.i18n('foss.baseinfo.operate'),//操作
			items:[{
				iconCls:'deppon_icons_edit',
				tooltip:baseinfo.passwordReset.i18n('foss.baseinfo.edit'),//编辑
				disabled:!baseinfo.passwordReset.isPermission('passwordReset/passwordResetEditButton'),
				width:50,
				handler:function(grid,rowIndex,colIndex){
					var record =grid.getStore().getAt(rowIndex);
					var id =record.get('id');//获取选中用户的id
					var params = {'vo':{'userEntity':{'id':id}}};
					var successFun=function(json){
						var valueUpdateWin =me.getValueUpdateWin();
						valueUpdateWin.userEntity =json.vo.userEntity;
						valueUpdateWin.show(); //显示修改窗口
					};
					var failureFun = function(json){
    					//传过来的数据为空的话，链接超时:
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes(baseinfo.passwordReset.i18n('foss.baseinfo.requestTimeout'));// 请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('queryUserEntityById.action');
    				//发送ajax请求
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);		
				}
			}]
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.jobNumber'),//用户工号
			dataIndex:'empCode',
			align:'center',
			width:100
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.userName'),//登陆账号
			dataIndex:'userName',
			align:'center',
			width:100
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.xingming'),//用户姓名
			dataIndex:'empName',
			align:'center',
			width:100
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.position'),//用户职位
			dataIndex:'title',
			align:'center',
			width:100
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.startDate'),//有效时间  
			xtype: 'datecolumn',  
			format:'Y-m-d h:i:s',
			dataIndex:'beginDate', 
			align:'center',
			width:130
		},{
			text:baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.endDate'),//失效时间
			xtype: 'datecolumn',  
			format:'Y-m-d h:i:s',
			dataIndex:'endDate',
			align:'center',
			width:130
		},{
			header: baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.active'),//是否有效, 
			dataIndex: 'active', 
			width:100,
			align: 'center',
			//渲染
			renderer : function(value) {
				return FossDataDictionary.rendererSubmitToDisplay(
						value, 'FOSS_ACTIVE');
			}
		}];
		me.store = Ext.create('Foss.baseinfo.passwordReset.UserStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				//查询事件的监听
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-passwordReset_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'vo.userEntity.empCode':
									queryForm.getForm().findField('empCode').getValue()//员工号
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
		  };
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/**
 * ------------------修改的window------------------------
 */
Ext.define('Foss.baseinfo.passwordReset.valueUpdateWin',{
	extend:'Ext.window.Window',
	title : baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.userInfo'),//修改用户信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素
	closeAction:'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :560,
	height :380,
	listeners:{
		//隐藏窗口时 清空里面的数据
		beforehide:function(me){
			me.getValueForm().getForm().reset();
		},
		//显示窗口之前，先显示数据
		beforeshow:function(me){
			//判断数据是否存在
			if(!Ext.isEmpty(me.userEntity)){
				var form =me.getValueForm().getForm();
				form.loadRecord(new Foss.baseinfo.passwordReset.UserEntity(me.userEntity));//赋值
			}
		}
	},
	valueForm:null,
	getValueForm:function(){
		if(Ext.isEmpty(this.valueForm)){
			this.valueForm =Ext.create('Foss.baseinfo.passwordReset.queryInfoForm');
		}
		return this.valueForm;
	},
	//提交数据的方法
	commitValue:function(){
		var me =this;
		if(me.getValueForm().getForm().isValid()){
			var valueModel =new Foss.baseinfo.passwordReset.UserEntity(me.userEntity);
			var form =me.getValueForm().getForm();
			//刷新数据
			form.updateRecord(valueModel);
			var params={'vo':{'userEntity':valueModel.data}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.parent.getPagingToolbar().moveFirst();
				me.close();
			};
			var failureFun =function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.passwordReset.i18n('foss.baseinfo.requestTimeout'));// 请求超时
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url =baseinfo.realPath('updateUserEntity.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getValueForm()];
		me.fbar=[{
				text : baseinfo.passwordReset.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.close();
				}
			},{
				text : baseinfo.passwordReset.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					me.getValueForm().getForm().loadRecord(new Foss.baseinfo.passwordReset.UserEntity(me.userEntity));
				}
			},{
				text : baseinfo.passwordReset.i18n('foss.baseinfo.confirm'),//确定
				cls:'yellow_button',
				margin:'0 0 0 235',
				handler :function(){
					me.commitValue();
				} 
			}];
		me.callParent([cfg]);
	}
});
/**
 * -----------------修改值的Form--------------
 */
Ext.define('Foss.baseinfo.passwordReset.queryInfoForm',{
	extend:'Ext.form.Panel',
	title : baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.pwdSeting'),//修改用户信息
	frame: true,
	height:260,
	collapsible: true,
	defaults:{
		colspan : 1,
		margin : '10 15 5 15',
		maxLength:50,
		allowBlank:false,
		labelWidth:60,
		columnWidth:0.5,
		anchor : '100%'
	},
	defaultType:'textfield',
	layout:'column',
	constructor:function(config){
		var me =this,
		cfg = Ext.apply({},config);
		me.items =[{
			name :'empCode',
			fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.jobNumber'),//工号
			readOnly:true
		},{
			name :'userName',
			fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.userName'),//登录账号
			readOnly:true
		},{
			name :'empName',
			fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.xingming'),//姓名
			readOnly:true
		},{
			name :'title',
			fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.position'),//职位
			readOnly:true,
			columnWidth:0.5
		},{
			name :'password',
			inputType:'password',
			fieldLabel:baseinfo.passwordReset.i18n('foss.baseinfo.passwordReset.pwdReset')//密码重置
		}];
		me.callParent([cfg]);
	}
});
/**
 * ---------------主页面-------------
 */
Ext.onReady(function() {
	//初始化Ext.QuickTips，以使得tip提示可用
	Ext.QuickTips.init();
	//获取现有组件的id
	if (Ext.getCmp('T_baseinfo-passwordReset_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.baseinfo.passwordReset.queryUserForm');//查询FORM
	var userGrid = Ext.create('Foss.baseinfo.passwordReset.userInfoGridPanel');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-passwordReset_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getUserGrid : function() {
			return userGrid;
		},
		items : [queryForm, userGrid],
		renderTo : 'T_baseinfo-passwordReset-body'
	});
});