Ext.define('Dpap.Authorization.InitalPasswordForm',{
	extend : 'Ext.form.Panel',
	defaults:{
		labelWidth:60,
	},
	items : [{
			name : 'id',
			hidden : true
		},{
			name: 'empCode.empCode',
			xtype: 'combo',
			readOnly:true,
	        fieldLabel: authorization.user.i18n('dpap.authorization.employee.name'),
	        store: Ext.create('Dpap.Authorization.EmpStore'),
	        //显示的字段
			displayField: 'empName',
			//提交时的字段
			valueField: 'empCode',
			//查询依据的字段
			queryParam:'employee.empName',
			minChars:1,
	        typeAhead: false,
	        hideTrigger:false,
	        listConfig: {
	            getInnerTpl: function() {
	                 return '{empName}';
	            }
	        }
		}, {
			xtype:'textfield',
			name: 'password',
			inputType: "password",
	        fieldLabel: authorization.user.i18n('dpap.authorization.user.password')
		}, {
			xtype:'textfield',
			name: 'rePassword',
			inputType: "password",
	        fieldLabel: authorization.user.i18n('dpap.authorization.user.rePassword')
		}
	],
	buttonAlign : 'center',
	checkValidate : function(){
		var me = this;
		var empCode = me.getForm().findField("empCode.empCode").getValue();
		var password = me.getForm().findField("password").getValue();
		var repassword = me.getForm().findField("rePassword").getValue();
		if(password==null||password==''){
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.InputErrException'));
			return;
		}
		if(repassword==null||repassword==''){
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.InputErrException'));
			return;
		}
		if (password!=repassword) {
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.PasswordNotMatchException'));
			return false;
		}
		if(empCode!=null&&password!=null&&password!=''){
			return true;
		}else{
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.InputErrException'));
			return false;
		}
	},
	buttons : [{
		text : authorization.user.i18n('dpap.authorization.save'),
		width :70,
		handler : function() {
			var form = this.up('form').getForm();
    		var record = form.getRecord();
    		form.updateRecord(record);
    		var _user={
    			'id':record.get('id'),
    			'password':record.get('password')//hex_md5(record.get('password'))
    		};
			//传递的参数值
			var array = {user:_user};
			//如果表单通过验证就提交
			if (form.isValid()&&this.up('form').checkValidate()) { 
				Ext.Ajax.request({
					url : authorization.user.userEditWindow.getUserForm().userFormUrl,
					jsonData:array,
					success : function(response) {
						Ext.getCmp('T_authorization-userIndex_content').getUserGrid().getStore().load();
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),json.message,function(){
							authorization.user.initalPasswordWindow.hide();
						});
					},
					exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),json.message);
					}
				});
			}
		}
	}]
});

Ext.define('Dpap.Authorization.InitalPasswordWindow',{
	extend : 'Ext.window.Window',
	width: 250,
    height: 200,
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	title : authorization.user.i18n('dpap.authorization.user.InitalPasswordWindow'),
	initPwdForm : null,
	getInitPwdForm : function(){
		if(this.initPwdForm==null){
			this.initPwdForm = Ext.create('Dpap.Authorization.InitalPasswordForm');
		}
		return this.initPwdForm;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [me.getInitPwdForm()];
		me.callParent([cfg]);
	}
});

/**
 * 用户表单
 */
Ext.define('Dpap.Authorization.UserForm',{
	extend : 'Ext.form.Panel',
	title: authorization.user.i18n('dpap.authorization.user.Form'),
	frame : true,
	height:80,
	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
		anchor: '100%',
		labelWidth:60
	},
	layout:'column',
	collapsible: true,
	userFormUrl: null,
	items :[{
		name : 'id',
		hidden : true
	},{
		name: 'empCode.empCode',
		xtype: 'combo',
		columnWidth:0.3,
        allowBlank : false,
        blankText : authorization.user.i18n('dpap.authorization.user.EmpCodeNullException'),
        fieldLabel: authorization.user.i18n('dpap.authorization.user.empCode'),
        store: Ext.create('Dpap.Authorization.EmpStore'),
        //显示的字段
		displayField: 'empCode',
		//提交时的字段
		valueField: 'empCode',
		//查询依据的字段
		queryParam:'employee.empCode',
		minChars:1,
        typeAhead: false,
        hideTrigger:false,
        listConfig: {
        	minWidth :280,
            getInnerTpl: function() {
            	 return '{empCode}&nbsp&nbsp&nbsp{empName}';
            }
        },
        listeners : {
        	select : function(field, value, options ){
        		this.up('form').getForm().findField("loginName").setValue(field.getValue());
        	}
        },
        pageSize: 10
	},{
		name: 'loginName',
		allowBlank : false,
		columnWidth:0.2,
		readOnly:true,
		blankText : authorization.user.i18n('dpap.authorization.user.LoginNameNullException'),
        fieldLabel: authorization.user.i18n('dpap.authorization.user.userName')
	},{
		name: 'status',
		allowBlank : false,
		 editable:false,
		columnWidth:0.3,
		blankText : authorization.user.i18n('dpap.authorization.UserStatusNullException'),
		fieldLabel: authorization.user.i18n('dpap.authorization.user.status'),
		xtype:'combo',
	    store: top.Ext.create('Ext.data.Store', {
				    fields: ['name', 'value'],
				    data : [
				            {'name':authorization.user.i18n('dpap.authorization.user.valid'), 'value':1},//生效
				            {'name':authorization.user.i18n('dpap.authorization.user.invalid'), 'value':0}//失效
				    ]
				}),
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'value'
	},{
        xtype: 'container',
        columnWidth:0.08,
        margin:'0 0 0 0',
        items : [{
        	xtype: 'button',
        	margin:'0 0 0 0',
			text : authorization.user.i18n('dpap.authorization.save'),
			width:70,
			handler : function() {
				var window = authorization.user.userEditWindow,
					userRoleGrid = window.getUserRoleGrid(),
					userDeptGrid = window.getUserDeptGrid(),
					form = this.up('form').getForm();
        		var new_record = form.getRecord();
        		form.updateRecord(new_record);
                var empCodeValue = form.findField("empCode.empCode").getValue();
        		var empCode = {'empCode':empCodeValue};
        		new_record.set('empCode',empCode);
        		if(null!=new_record.get('password')&&new_record.get('password')=='123456'){
        			new_record.set('password','123456');		        			
        		}
        		new_record.set('loginName',form.findField("empCode.empCode").getValue());
        		//将选中的角色构成一个数组
        		var _roles = new Array();
        		userRoleGrid.getStore().each( function(_role) {
        			_roles.push(_role.get('id'));
        		},userRoleGrid.getStore());
        		//将选中的部门构成一个数组
        		var _depts = new Array();
        		userDeptGrid.getStore().each(function(_dept) {
        			_depts.push(_dept.get('id'));
				},userDeptGrid.getStore());
				//传递的参数值
				var array = {user:new_record.data,chooesRoles:_roles,chooseDepts:_depts};
				//如果表单通过验证就提交
				if (form.isValid()&&this.up('form').checkInput()) { 
					Ext.Ajax.request({
						url : authorization.user.userEditWindow.getUserForm().userFormUrl,
						jsonData:array,
						success : function(response) {
							Ext.getCmp('T_authorization-userIndex_content').getUserGrid().getStore().load();
							var json = Ext.decode(response.responseText);
							if(json.isException){
								Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),json.message);
							}else{
								Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),json.message,function(optional){
									window.hide();
								});
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),json.message);
						}
					});
				}else{
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.InputErrException'));
				}
			}
		}]
	}],
	checkInput : function(){
		var empCode =  this.getForm().findField("empCode.empCode").getValue();
		var loginName = this.getForm().findField('loginName').value;
		var status = this.getForm().findField('status').value;
		if (loginName!=null&&loginName!=''&&status!=null&&empCode!=null&&empCode!='') {
			return true;
		}else{
			return false;
		}
	}
});

/**
 * 未分配角色选择列表
 */
Ext.define('Dpap.Authorization.UserRoleChooseGrid', {
	extend : 'Ext.grid.Panel',
	height:270,
	frame: true,
	title : authorization.user.i18n('dpap.authorization.authChooseRole'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : authorization.user.i18n('dpap.authorization.role.roleName'),
		width:150,
		dataIndex : 'roleName'
	}, {
		text : authorization.user.i18n('dpap.authorization.role.roleDesc'),
		width:180,
		dataIndex : 'roleDesc'
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.RoleStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findLeftRoles.action',
				reader : {
					type : 'json',
					root : 'roleList'
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 已分配角色选择列表
 */
Ext.define('Dpap.Authorization.UserRoleGrid', {
	extend : 'Ext.grid.GridPanel',
	title : authorization.user.i18n('dpap.authorization.authRoleGrid'),
	height:270,
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : authorization.user.i18n('dpap.authorization.role.roleName'),
		width:150,
		dataIndex : 'roleName'
	}, {
		text : authorization.user.i18n('dpap.authorization.role.roleDesc'),
		width:180,
		dataIndex : 'roleDesc'
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.RoleStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findAuthedRoles.action',
				reader : {
					type : 'json',
					root : 'roleList'
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 移动按钮
 */
Ext.define('Dpap.Authorization.RoleButtonPanel', {
	extend : 'Ext.panel.Panel',
	height:270,
	buttonAlign : 'center',
	layout : 'column',
	items : [{
		columnWidth : 1,
		xtype: 'container',
		style : 'padding-top:60px;border:none',
		width : '100%',
		items : [ {
			xtype : 'button',
			text : '&gt;&gt;',
			width : '100%',
			handler :  function() {
				var window = this.up('window'),
					roleChooseGrid = window.getRoleChooseGrid(),
					userRoleGrid = window.getUserRoleGrid();
				if(!authorization.allSelect(roleChooseGrid,userRoleGrid.getStore(),roleChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessages'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&nbsp;&gt;&nbsp;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					roleChooseGrid = window.getRoleChooseGrid(),
					userRoleGrid = window.getUserRoleGrid();
				if(!authorization.select(roleChooseGrid,userRoleGrid.getStore(),roleChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'), authorization.user.i18n('dpap.authorization.noRecord'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&nbsp;&lt;&nbsp;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					roleChooseGrid = window.getRoleChooseGrid(),
					userRoleGrid = window.getUserRoleGrid();
				if(!authorization.select(userRoleGrid,roleChooseGrid.getStore(),userRoleGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'), authorization.user.i18n('dpap.authorization.noRecord'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&lt;&lt;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					roleChooseGrid = window.getRoleChooseGrid(),
					userRoleGrid = window.getUserRoleGrid();
				if(!authorization.allSelect(userRoleGrid,roleChooseGrid.getStore(),userRoleGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessages'));
				}
			}
		} ]
	}]
});

/**
 * 未分配部门选择列表
 */
Ext.define('Dpap.Authorization.DeptChooseGrid', {
	extend : 'Ext.grid.GridPanel',
	title : authorization.user.i18n('dpap.authorization.authChooseUserGrid'),
	height:270,
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : authorization.user.i18n('dpap.organization.organization.deptName'),
		width:100,
		dataIndex : 'deptName'
	}, {
		text : authorization.user.i18n('dpap.organization.organization.principal'),
		width:80,
		dataIndex : 'principal'
	}, {
		text : authorization.user.i18n('dpap.organization.organization.companyName'),
		width:150,
		dataIndex : 'companyName'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.DepartmentStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findLeftDepts.action',
				reader : {
					type : 'json',
					root : 'deptList'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var userGrid = Ext.getCmp('T_authorization-userIndex_content').getUserGrid();
					var user= userGrid.getSelectionModel().getSelection()[0];
					if(user!=null){
						Ext.apply(operation,{
							params : {
								'userId' : user.get('id')
							}
						});
					}
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 已分配部门选择列表
 */
Ext.define('Dpap.Authorization.UserDeptGrid', {
	extend : 'Ext.grid.GridPanel',
	title : authorization.user.i18n('dpap.authorization.authDeptGrid'),
	height:270,
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : authorization.user.i18n('dpap.organization.organization.deptName'),
		width:100,
		dataIndex : 'deptName'
	}, {
		text : authorization.user.i18n('dpap.organization.organization.principal'),
		width:80,
		dataIndex : 'principal'
	}, {
		text : authorization.user.i18n('dpap.organization.organization.companyName'),
		width:150,
		dataIndex : 'companyName'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.DepartmentStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findAuthedDepts.action',
				reader : {
					type : 'json',
					root : 'deptList'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var userGrid = Ext.getCmp('T_authorization-userIndex_content').getUserGrid();
					var user= userGrid.getSelectionModel().getSelection()[0];
					if(user!=null){
						Ext.apply(operation,{
							params:{
								'userId' : user.get('id')
							}
						});
					}
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 移动按钮
 */
Ext.define('Dpap.Authorization.DeptButtonPanel', {
	extend : 'Ext.panel.Panel',
	buttonAlign : 'center',
	layout : 'column',
	height:270,
	items : [{
		columnWidth : 1,
		xtype: 'container',
		style : 'padding-top:60px;border:none',
		width : '100%',
		items : [ {
			xtype : 'button',
			text : '&gt;&gt;',
			width : '100%',
			handler :  function() {
				var window = this.up('window'),
					deptChooseGrid = window.getDeptChooseGrid(),
					userDeptGrid = window.getUserDeptGrid();
				if(!authorization.allSelect(deptChooseGrid,userDeptGrid.getStore(),deptChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'), authorization.user.i18n('dpap.authorization.noRecord'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&nbsp;&gt;&nbsp;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					deptChooseGrid = window.getDeptChooseGrid(),
					userDeptGrid = window.getUserDeptGrid();
				if(!authorization.select(deptChooseGrid,userDeptGrid.getStore(),deptChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessages'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&nbsp;&lt;&nbsp;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					deptChooseGrid = window.getDeptChooseGrid(),
					userDeptGrid = window.getUserDeptGrid();
				if(!authorization.select(userDeptGrid,deptChooseGrid.getStore(),userDeptGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'), authorization.user.i18n('dpap.authorization.noRecord'));
				}
			}
		} ]
	}, {
		columnWidth : 1,
		width : '100%',
		xtype: 'container',
		style : 'padding-top:10px;border:none',
		items : [ {
			xtype : 'button',
			text : '&lt;&lt;',
			width : '100%',
			handler : function() {
				var window = this.up('window'),
					deptChooseGrid = window.getDeptChooseGrid(),
					userDeptGrid = window.getUserDeptGrid();
				if(!authorization.allSelect(userDeptGrid,deptChooseGrid.getStore(),userDeptGrid.getStore())){
					Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessages'));
				}
			}
		} ]
	}]
});

/**
 * 用户编辑窗口
 */
Ext.define('Dpap.Authorization.UserEditWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : authorization.user.i18n('dpap.authorization.windowTitle'),
	height:550,
    width:900,
	userForm : null,
    getUserForm : function(){
    	if(this.userForm==null){
    		this.userForm = Ext.create('Dpap.Authorization.UserForm');
    	}
    	return this.userForm;
    },
	roleChooseGrid : null,
	getRoleChooseGrid : function(){
		if(this.roleChooseGrid==null){
			this.roleChooseGrid = Ext.create('Dpap.Authorization.UserRoleChooseGrid',{
				flex:10,
				margin : '10 0 0 5'
			});
		}
		return this.roleChooseGrid;
	},
	roleButtonPanel : null,
	getRoleButtonPanel : function(){
		if(this.roleButtonPanel==null){
			this.roleButtonPanel = Ext.create('Dpap.Authorization.RoleButtonPanel',{
				flex:1,
				margin : '0 0 0 15'
			});
		}
		return this.roleButtonPanel;
	},
	userRoleGrid : null,
	getUserRoleGrid : function(){
		if(this.userRoleGrid==null){
			this.userRoleGrid = Ext.create('Dpap.Authorization.UserRoleGrid',{
				flex:10,
				margin : '10 17 0 0'
			});
		}
		return this.userRoleGrid;
	},
	deptChooseGrid : null,
	getDeptChooseGrid : function(){
		if(this.deptChooseGrid==null){
			this.deptChooseGrid = Ext.create('Dpap.Authorization.DeptChooseGrid', {
				flex:10,
				margin : '10 0 0 5'
			});
		}
		return this.deptChooseGrid;
	},
	deptButtonPanel : null,
	getDeptButtonPanel : function(){
		if(this.deptButtonPanel==null){
			this.deptButtonPanel = Ext.create('Dpap.Authorization.DeptButtonPanel',{
				flex:1,
				margin : '0 0 0 15'
			});
		}
		return this.deptButtonPanel;
	},
	userDeptGrid : null,
	getUserDeptGrid : function(){
		if(this.userDeptGrid==null){
			this.userDeptGrid = Ext.create('Dpap.Authorization.UserDeptGrid', {
				flex:10,
				margin : '10 17 0 0'
			});
		}
		return this.userDeptGrid;
	},
    userTab : null,
    getUserTab : function(){
    	var me = this;
    	if(this.userTab==null){
    		this.userTab = Ext.create('Ext.tab.Panel',{
    			frame: true,
    			activeTab : 0,
    			items : [{
    				title : authorization.user.i18n('dpap.authorization.authTabNameRole'),
    				layout:'hbox',
    				tabConfig:{width:100},
    				items : [ me.getRoleChooseGrid(), me.getRoleButtonPanel(), me.getUserRoleGrid() ]
    			}, {
    				title : authorization.user.i18n('dpap.authorization.authTabNameDept'),
    				layout:'hbox',
    				tabConfig:{width:100},
    				items : [  me.getDeptChooseGrid(), me.getDeptButtonPanel(), me.getUserDeptGrid() ]
    			}]
    		});
    	}
    	return this.userTab;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getUserForm(), me.getUserTab()];
		me.listeners = {
	    	beforehide:function(){
	    		me.getUserTab().setActiveTab(0);
	    	}
	    };
		me.callParent([cfg]);
	}
});

/**
 * 用户查询表单
 */
Ext.define('Dpap.Authorization.QueryForm', {
	extend : 'Ext.form.Panel',
	title: authorization.user.i18n('dpap.authorization.user.findcase'),
	frame: true,
	layout:'column',
	height :130,
	collapsible: true,
	defaultType : 'textfield',
	defaults : {
		columnWidth : .45,
		margin : '8 10 5 10'
	},
	items :[{
		name: 'empCode',
		fieldLabel: authorization.user.i18n('dpap.authorization.user.empNumber'),
		xtype : 'textfield'
	},{
		name: 'empName',
		fieldLabel: authorization.user.i18n('dpap.authorization.user.empName'),
		xtype : 'textfield'
	},{
		name: 'status',
		fieldLabel: authorization.user.i18n('dpap.authorization.user.status'),
		xtype:'combo',
		store: Ext.create('Ext.data.Store', {
			fields: ['name', 'value'],
			data : [
		        {'name':authorization.user.i18n('dpap.authorization.user.valid'), 'value':1},//生效
		        {'name':authorization.user.i18n('dpap.authorization.user.invalid'), 'value':0}//失效
		    ]
		}),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value'
	},{
		border : false,
		xtype : 'container',
		margin : '3 0 0 10',
		items : [{
			xtype : 'button', 
			width :70,
			text : authorization.user.i18n('dpap.authorization.find'),
			handler : function() {
				Ext.getCmp('T_authorization-userIndex_content').
				getUserGrid().getPagingToolbar().moveFirst();
			}
		}]
	}]
});

/**
 * 用户列表
 */
Ext.define('Dpap.Authorization.UserGrid', {
	extend : 'Ext.grid.Panel',
	title : authorization.user.i18n('dpap.authorization.user.userGrid'),
	frame : true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	columns : [{
		hidden : true,
		dataIndex : 'id'
	},{xtype: 'rownumberer'},{
		text : authorization.user.i18n('dpap.authorization.user.empCode'),
		width:65,
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.empCode;
		},
		dataIndex : 'empCode'
	},{
		text : authorization.user.i18n('dpap.authorization.employee.name'),
		width:75,
		dataIndex : 'empCode',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.empName;
		}
	},{
		text : authorization.user.i18n('dpap.authorization.user.loginName'),
		width:60,
		dataIndex : 'loginName'
	},{
		text : authorization.user.i18n('dpap.authorization.user.status'),
		renderer : function(val) {
			if(val == null){
				return;
			}
			if (val == 1) {
				return authorization.user.i18n('dpap.authorization.user.valid');
			} else {
				return authorization.user.i18n('dpap.authorization.user.invalid');
			}
		},
		width:68,
		dataIndex : 'status'
	},{
		text : authorization.user.i18n('dpap.authorization.user.lastLogin'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'lastLogin'
	},{
		text : authorization.user.i18n('dpap.authorization.user.validDate'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'validDate'
	},{
		text : authorization.user.i18n('dpap.authorization.user.invalidDate'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'invalidDate'
	}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store
			});
		}
		return this.pagingToolbar;
	},
	beforeload: function(store, operation, eOpts){
		var query_user_form = Ext.getCmp("T_authorization-userIndex_content").getQueryForm(),
		deptTree = Ext.getCmp('T_authorization-userIndex_content').getDeptTree(); 
		if(query_user_form!=null){
			Ext.apply(operation,{
				params : {
					'user.empCode.empCode':query_user_form.getForm().findField("empCode").getValue(),
					'user.empCode.empName' : query_user_form.getForm().findField('empName').getValue(),
					'user.status' : query_user_form.getForm().findField('status').getValue()
				}
			});	
		}
		if (deptTree != null) {
			var sm = deptTree.getSelectionModel();
			if (sm.getSelection().length > 0) {
				var dept = sm.getSelection()[0];
				if (dept != null && dept.data.id != '103') {
					Ext.apply(operation, {
						params : {
							'deptId':dept.raw.entity.id
						}
					}
					);
				}
			}
		}
	},
	addUser : function() {
		var userEditWindow = authorization.user.userEditWindow,
			userForm = userEditWindow.getUserForm(),
			userRoleGird = userEditWindow.getUserRoleGrid(),
			userDeptGrid = userEditWindow.getUserDeptGrid(),
			roleChooseGrid = userEditWindow.getRoleChooseGrid(),
			deptChooseGrid = userEditWindow.getDeptChooseGrid();
		userForm.userFormUrl = '../authorization/saveUser.action';
		var user = Ext.create('Dpap.Authorization.UserModel');
		userForm.loadRecord(user);
		userForm.getForm().findField('empCode.empCode').setReadOnly(false);
		userForm.getForm().reset();
		userRoleGird.getStore().removeAll();
		userDeptGrid.getStore().removeAll();
		roleChooseGrid.getStore().load();
		deptChooseGrid.getStore().load();
		userEditWindow.show();
		
	},
	updateUser : function() {
		var userEditWindow = authorization.user.userEditWindow,
			grid = this.up('grid'),
			selection = grid.getSelectionModel().getSelection(),
			userForm = userEditWindow.getUserForm(),
			userRoleGird = userEditWindow.getUserRoleGrid(),
			userDeptGrid = userEditWindow.getUserDeptGrid(),
			roleChooseGrid = userEditWindow.getRoleChooseGrid(),
			deptChooseGrid = userEditWindow.getDeptChooseGrid();
		if (selection.length != 1) {
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessage'));
			return;
		} else {
			userForm.userFormUrl = '../authorization/updateUser.action';
			var record = selection[0];
			userForm.getForm().findField('empCode.empCode').setReadOnly(true);
			userForm.loadRecord(record);
    		if(record.raw.empCode.empName != null){
    			var empRecord = Ext.ModelManager.create(record.raw.empCode, 'Dpap.Organization.EmployeeModel');
    			userForm.getForm().findField("empCode.empCode").select(empRecord);
    		}
    		var user= grid.getSelectionModel().getSelection()[0];
    		//用户未分配的角色 
    		roleChooseGrid.getStore().load({
    			params : {
    				userId:user.get('id')	            				
    			}
			});
    		//用户已经分配的角色
    		userRoleGird.getStore().load({
    			params : {
    				userId:user.get('id')	            				
    			}
    		});
    		//用户未分配的部门
    		deptChooseGrid.getStore().load({
    			params : {
    				userId:user.get('id')	            				
    			}
    		});
    		//用户已经分配的部门
    		userDeptGrid.getStore().load({
    			params : {
    				userId:user.get('id')	            				
    			}
    		});
    		userEditWindow.show();
		}
	},
	initUserPassword : function() {
		var grid = this.up('grid'),
			selection = grid.getSelectionModel().getSelection(),
			userForm = authorization.user.userEditWindow.getUserForm(),
			window = authorization.user.initalPasswordWindow,
			initPwdForm = window.getInitPwdForm();
		userForm.userFormUrl = '../authorization/updatePassword.action';
		if (selection.length != 1) {
			Ext.MessageBox.alert(authorization.user.i18n('dpap.authorization.message'),authorization.user.i18n('dpap.authorization.operationMessage'));
			return;
		}
		var record = selection[0];
		initPwdForm.loadRecord(record);
		initPwdForm.getForm().findField('password').setValue(null);
		initPwdForm.getForm().findField('rePassword').setValue('');
		if(record.raw.empCode.empName != null){
			var empRecord = Ext.ModelManager.create(record.raw.empCode, 'Dpap.Organization.EmployeeModel');
			initPwdForm.getForm().findField("empCode.empCode").select(empRecord);
		}
		window.show();
	},
	userEditWindow : null,
	getUserEditWindow : function(){
		if(this.userEditWindow==null){
			this.userEditWindow = authorization.user.userEditWindow;
		}
		return this.userEditWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.UserStore',{
			listeners: {
				beforeload: me.beforeload
			}
		});
		me.bbar = me.getPagingToolbar();
		me.tbar = [{
			text : authorization.user.i18n('dpap.authorization.add'),
			hidden:!authorization.user.isPermission('../authorization/saveUser.action'),
			handler : me.addUser
		}, {
			text : authorization.user.i18n('dpap.authorization.update'),
			hidden:!authorization.user.isPermission('../authorization/updateUser.action'),
			handler : me.updateUser
		},{
			text : authorization.user.i18n('dpap.authorization.initalPassword'),
			hidden:!authorization.user.isPermission('../authorization/updatePassword.action'),
			handler : me.initUserPassword
		} ];
		me.callParent([cfg]);
	}
});

/**
 * 启动加载的页面元素及布局
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_authorization-userIndex_content')) {
		return;
	}
	var deptTree = Ext.create('Dpap.Authorization.DepartmentTreePanle', {
		height: 717,
		flex : 3,
		title : authorization.user.i18n('dpap.authorization.deptTree'),
		root : {
			// 把id为0改成103 得到OA组织架构树
			id : '103',
			text : authorization.user.i18n('dpap.authorization.organization.root'),
			expanded : true
		},
		deptNameQuery : authorization.user.i18n('dpap.organization.organization.deptName'),
		queryName : authorization.user.i18n('dpap.authorization.find'),
		listeners : {
			itemclick : function(node, record, item, index, e) {
				if (record.data.id != '103') {
					Ext.getCmp('T_authorization-userIndex_content').getUserGrid().getPagingToolbar().moveFirst();
				}
			}
		}
	});
	var queryForm = Ext.create('Dpap.Authorization.QueryForm');
	var userGrid = Ext.create('Dpap.Authorization.UserGrid');
	Ext.create('Ext.panel.Panel', {
		id : 'T_authorization-userIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'hbox',
		getQueryForm : function() {
			return queryForm;
		},
		getUserGrid : function() {
			return userGrid;
		},
		getDeptTree : function() {
			return deptTree;
		},
		items : [ deptTree, {
			xtype : 'container',
			margin : '0 0 0 15',
			flex : 7,
			items : [ queryForm, userGrid ]
		} ],
		listeners: {
			boxready: function(){
				authorization.user.userEditWindow = Ext.create('Dpap.Authorization.UserEditWindow');
				authorization.user.initalPasswordWindow = Ext.create('Dpap.Authorization.InitalPasswordWindow');
			}
		},
		renderTo : 'T_authorization-userIndex-body'
	});
});