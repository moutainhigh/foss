/**
 * 用户授权表单
 */
Ext.define('Dpap.Authorization.AuthorizeForm',{
	extend : 'Ext.form.Panel',
	frame : true,
	title: authorization.authorize.i18n('dpap.authorization.user.userform'),
	height:80,
	defaultType: 'textfield',
	defaults: {
		margin:'5 10 5 10',
		anchor: '100%',
		labelWidth:60
	},
	layout:'column',
	collapsible: true,
	items : [{
		name : 'id',
		hidden : true
	},{
        xtype: 'combo',
        readOnly : true,
        columnWidth:0.3,
        fieldLabel: authorization.authorize.i18n('dpap.authorization.user.empCode'),
        store: Ext.create('Dpap.Authorization.EmpStore'),
		displayField: 'empCode',
		valueField: 'empCode',
		name: 'empCode.empCode',
		queryParam:'employee.empCode',
		minChars:1,
        typeAhead: false,
        hideTrigger:false,
        listConfig: {
            getInnerTpl: function() {
                 return '{empCode}&nbsp&nbsp&nbsp{empName}';
            }
        },
        pageSize: 10
	},{
		name: 'loginName',
		readOnly : true,
		columnWidth:0.3,
        fieldLabel: authorization.authorize.i18n('dpap.authorization.user.loginName'),
        xtype : 'textfield'
	},{
		name: 'status',
		fieldLabel: authorization.authorize.i18n('dpap.authorization.user.status'),
		xtype:'combo',
		columnWidth:0.3,
		readOnly : true,
	    store: Ext.create('Ext.data.Store', {
				    fields: ['name', 'value'],
				    data : [
				        {'name':authorization.authorize.i18n('dpap.authorization.user.valid'), 'value':1},
				        {'name':authorization.authorize.i18n('dpap.authorization.user.invalid'), 'value':0}
				    ]
				}),
	    queryMode: 'local',
	    displayField: 'name',
	    valueField: 'value'
	},{
        xtype: 'container',
        columnWidth:0.1,
        margin:'0 0 0 0',
        items : [{
            xtype: 'button',
			text : authorization.authorize.i18n('dpap.authorization.save'),
			width:60,
			handler : function() {
				var window = this.up('window'),
					authRoleGrid = window.getAuthRoleGrid(),
					deptChooseTree = window.getDeptChooseTree(),
					form = this.up('form').getForm();
	        	if (form.isValid()) { 
	        		var new_record = form.getRecord();
	        		var _roles = new Array();
	        		authRoleGrid.getStore().each( function(_role) {
	        			_roles.push(_role.get('id'));
	        		},authRoleGrid.getStore());
	        		var _depts = new Array();
	        		var nodes = deptChooseTree.getChecked();
					form.updateRecord(new_record);
					Ext.each(nodes, function(node) {
						var status = null;
						if(!node.isLeaf()&&(node.childNodes==null||node.childNodes.length<=0)){
							status=true;
						}
						var dept = {
								'id' : node.raw.entity.id,
								'status' : status
						};
						_depts.push(dept);
					});
					var array = {userId:new_record.get('id'),chooseDepts:_depts,chooseRoles:_roles};
		        	Ext.Ajax.request({
						url : '../authorization/saveAuthorize.action',
						jsonData:array,
						success : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'),json.message,function(){
								window.hide();
							});		
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'),json.message);
						}
					});
				}
			}        
		}]
	}]
});

/**
 * 角色选择列表
 */
Ext.define('Dpap.Authorization.RoleChooseGrid', {
	extend : 'Ext.grid.GridPanel',
	height:270,
	frame: true,
	title : authorization.authorize.i18n('dpap.authorization.authChooseRole'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [ {
		text : authorization.authorize.i18n('dpap.authorization.role.roleName'),
		width:130,
		dataIndex : 'roleName'
	},{
		text : authorization.authorize.i18n('dpap.authorization.role.roleDesc'),
		width:190,
		dataIndex : 'roleDesc'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.RoleStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findAllChooesRole.action',
				reader : {
					type : 'json',
					root : 'roleList'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var authForm = authorization.authorize.authorizeForm;
					if(authForm==null||authForm.getForm()==null||null==authForm.getForm().getRecord()){
						return;
					}
					var user = authForm.getForm().getRecord();
					if(user==null){
						return;
					}
					var userId = user.get('id');
					if(userId==null){
						return;
					}
					Ext.apply(operation,{
						params : {
								'userId' : userId
						}
					});
				}
		    }
		});
		me.callParent([cfg]);
	}
});

/**
 * 已授权角色列表
 */
Ext.define('Dpap.Authorization.AuthorizeRoleGrid', {
	extend : 'Ext.grid.GridPanel',
	height:270,
	frame: true,
	title : authorization.authorize.i18n('dpap.authorization.authRole'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : authorization.authorize.i18n('dpap.authorization.role.roleName'),
		width:130,
		dataIndex : 'roleName'
	},{
		text : authorization.authorize.i18n('dpap.authorization.role.roleDesc'),
		width:190,
		dataIndex : 'roleDesc'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('Dpap.Authorization.RoleStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/findAllAuthorizeRole.action',
				reader : {
					type : 'json',
					root : 'roleList'
				}
			},
			listeners: {
				beforeload: function(store, operation, eOpts){
					var authForm = authorization.authorize.authorizeForm;
					if(authForm==null||authForm.getForm()==null||null==authForm.getForm().getRecord()){
						return;
					}
					var user = authForm.getForm().getRecord();
					if(user==null){
						return;
					}
					var userId = user.get('id');
					if(userId==null){
						return;
					}
					Ext.apply(operation,{
						params : {
								'userId' : userId
							}
						}
					);
				}
		    }
		});
		me.callParent([cfg]);
	}
});
/**
 * 移动按钮
 */
Ext.define('Dpap.Authorization.ButtonPanel', {
	extend : 'Ext.panel.Panel',
	buttonAlign : 'center',
	height:270,
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
					authRoleGrid = window.getAuthRoleGrid();
				if(!authorization.allSelect(roleChooseGrid,authRoleGrid.getStore(),roleChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'), authorization.authorize.i18n('dpap.authorization.noRecord'));
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
					authRoleGrid = window.getAuthRoleGrid();
				if(!authorization.select(roleChooseGrid,authRoleGrid.getStore(),roleChooseGrid.getStore())){
					Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'),authorization.authorize.i18n('dpap.authorization.operationMessages'));
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
					authRoleGrid = window.getAuthRoleGrid();
				if(!authorization.select(authRoleGrid,roleChooseGrid.getStore(),authRoleGrid.getStore())){
					Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'),authorization.authorize.i18n('dpap.authorization.operationMessages'));
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
				authRoleGrid = window.getAuthRoleGrid();
				if(!authorization.allSelect(authRoleGrid,roleChooseGrid.getStore(),authRoleGrid.getStore())){
					Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'), authorization.authorize.i18n('dpap.authorization.noRecord'));
				}
			}
		} ]
	}]
});

Ext.define('Dpap.Authorization.DepartmentChooseTree',{
	extend  : 'Ext.tree.Panel',
	frame: true,
    collapsible: true,
    useArrows: true,
    multiSelect: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,  
    height:267,
    columns: [{
        xtype: 'treecolumn',
        text: authorization.authorize.i18n('dpap.organization.organization.deptName'),
		width : 400,
		dataIndex : 'text'
    },{
		text : authorization.authorize.i18n('dpap.organization.organization.principal'),
		width : 100,
		dataIndex : 'entity',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.principal;
		}
	},{
		text : authorization.authorize.i18n('dpap.organization.organization.companyName'),
		width :  300,
		dataIndex : 'entity',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.companyName;
		}
	}],
	/**
	 * 当所有子节点没有选中时候，取消选择父节点
	 */
	deSelectParentFunction : function(node) {
		if (node.data.id == this.store.root.id)
			return;
		var parentNode = node.parentNode;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				if (childNode.data.checked == true) {
					return;
				}
			}
		}
		if(parentNode.data.id != this.store.root.id){
			parentNode.set("checked", false);		
		}
		this.deSelectParentFunction(parentNode);
	},

	/**
	 * 父节点选中时，选择所有子节点
	 */
	selectChildFunction : function(node, checked) {
		var me = this;
		Ext.Array.each(node.childNodes, function(childNode) {
			childNode.set("checked", checked);
			me.selectChildFunction(childNode, checked);
		});
	},
	checkChange : function(node, checked) {
		if (checked == true) {
			this.selectChildFunction(node, true);
		} else {
			this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			this.deSelectParentFunction(node);
		}
	},
	itemExpand : function(node, state) {
		if(node.modified.checked==null){
			return;
		}
		//得到更改后的选中判断值
		var checked = !node.modified.checked;

		/* ***** 选中时将子节点全部选中****** */
		var parentNode = node;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				childNode.set("checked", checked);
				this.up('tree').selectChildFunction(childNode, checked);
			}
		}
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.DepartmentTreeStore',{
			model : 'TreeNodeModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../authorization/loadDepartmentChooesTree.action'
			},
			root : {
				//把id为0改成103  得到OA组织架构树
				id : '103',
				text : authorization.authorize.i18n('dpap.authorization.organization.root'),
				//expanded : true,
				checked:null
			},
			listeners : {
				'beforeload': function(store, operation, eOpts){
					var user = authorization.authorize.authorizeForm.getForm().getRecord();
					if(user == null){
						return;
					}
					if(undefined === user.raw){
						return;
					}
					if(operation.params==null){
						operation.params = {};
					}
					Ext.apply(operation.params,{
						'userId' : user.raw.id
					});
				}
			}
		});
		// 监听事件
		me.listeners = {
			checkchange : me.checkChange,
			itemexpand : me.itemExpand
		};
		me.callParent([cfg]);
	}
});

authorization.authorize.authorizeForm = Ext.create('Dpap.Authorization.AuthorizeForm');

/**
 * 授权编辑窗口
 */
Ext.define('Dpap.Authorization.AuthorizeEditWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : authorization.authorize.i18n('dpap.authorization.windowTitle'),
	height:550,
    width:900,
	authorizeForm : null,
    getAuthorizeForm : function(){
    	if(this.authorizeForm==null){
    		this.authorizeForm = authorization.authorize.authorizeForm;
    	}
    	return this.authorizeForm;
    },
    deptChooseTree : null,
	getDeptChooseTree : function(){
		if(this.deptChooseTree==null){
			this.deptChooseTree = Ext.create('Dpap.Authorization.DepartmentChooseTree');
		}
		return this.deptChooseTree;
	},
	roleChooseGrid : null,
	getRoleChooseGrid : function(){
		if(this.roleChooseGrid==null){
			this.roleChooseGrid = Ext.create('Dpap.Authorization.RoleChooseGrid',{
				flex:10,
				margin : '10 0 0 5'
			});
		}
		return this.roleChooseGrid;
	},
	buttonPanel : null,
	getButtonPanel : function(){
		if(this.buttonPanel==null){
			this.buttonPanel = Ext.create('Dpap.Authorization.ButtonPanel',{
				flex:1,
				margin : '0 0 0 15'
			});
		}
		return this.buttonPanel;
	},
	authRoleGrid : null,
	getAuthRoleGrid : function(){
		if(this.authRoleGrid==null){
			this.authRoleGrid = Ext.create('Dpap.Authorization.AuthorizeRoleGrid',{
				flex:10,
				margin : '10 17 0 0'
			});
		}
		return this.authRoleGrid;
	},
    authorizeTab : null,
    getAuthorizeTab : function(){
    	var me = this;
    	if(this.authorizeTab==null){
    		this.authorizeTab = Ext.create('Ext.tab.Panel',{
    			frame: true,
    			height:360,
    			activeTab : 0,
    			items : [{
    				title : authorization.authorize.i18n('dpap.authorization.user.roleauth'),
    				layout:'hbox',
    				tabConfig:{width:100},
    				items : [ me.getRoleChooseGrid(), me.getButtonPanel(), me.getAuthRoleGrid() ]
    			}, {
    				title : authorization.authorize.i18n('dpap.authorization.user.deptDataAuth'),
    				tabConfig:{width:100},
    				items : [me.getDeptChooseTree()]
    			}]
    		});
    	}
    	return this.authorizeTab;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getAuthorizeForm(), me.getAuthorizeTab()];
		me.listeners = {
	    	beforehide:function(){
	    		me.getAuthorizeTab().setActiveTab(0);
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
	title: authorization.authorize.i18n('dpap.authorization.user.findcase'),
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
		fieldLabel: authorization.authorize.i18n('dpap.authorization.user.empNumber'),
		xtype : 'textfield'
	},{
		name: 'empName',
		fieldLabel: authorization.authorize.i18n('dpap.authorization.user.empName'),
		xtype : 'textfield'
	},{
		name: 'status',
		fieldLabel: authorization.authorize.i18n('dpap.authorization.user.status'),
		xtype:'combo',
		store: Ext.create('Ext.data.Store', {
			fields: ['name', 'value'],
			data : [
		        {'name':authorization.authorize.i18n('dpap.authorization.user.valid'), 'value':1},//生效
		        {'name':authorization.authorize.i18n('dpap.authorization.user.invalid'), 'value':0}//失效
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
			text : authorization.authorize.i18n('dpap.authorization.find'),
			handler : function() {
				Ext.getCmp('T_authorization-authorizeIndex_content').
				getAuthorizeGrid().getPagingToolbar().moveFirst();
			}
		}]
	}]
});
/**
 * 授权用户列表
 */
Ext.define('Dpap.Authorization.AuthorizeGrid', {
	extend : 'Ext.grid.Panel',
	title : authorization.authorize.i18n('dpap.authorization.user.userGrid'),
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
		text : authorization.authorize.i18n('dpap.authorization.user.empCode'),
		width:65,
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.empCode;
		},
		dataIndex : 'empCode'
	},{
		text : authorization.authorize.i18n('dpap.authorization.employee.name'),
		width:75,
		dataIndex : 'empCode',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.empName;
		}
	},{
		text : authorization.authorize.i18n('dpap.authorization.user.loginName'),
		width:60,
		dataIndex : 'loginName'
	},{
		text : authorization.authorize.i18n('dpap.authorization.user.status'),
		renderer : function(val) {
			if(val == null){
				return;
			}
			if (val == 1) {
				return authorization.authorize.i18n('dpap.authorization.user.valid');
			} else {
				return authorization.authorize.i18n('dpap.authorization.user.invalid');
			}
		},
		width:68,
		dataIndex : 'status'
	},{
		text : authorization.authorize.i18n('dpap.authorization.user.lastLogin'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'lastLogin'
	},{
		text : authorization.authorize.i18n('dpap.authorization.user.validDate'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'validDate'
	},{
		text : authorization.authorize.i18n('dpap.authorization.user.invalidDate'),
		xtype: 'datecolumn',
		format : 'Y-m-d H:i:s',
		width:130,
		dataIndex : 'invalidDate'
	}],
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 15
			});
		}
		return this.pagingToolbar;
	},
	beforeload: function(store, operation, eOpts){
		var query_user_form = Ext.getCmp("T_authorization-authorizeIndex_content").getQueryForm(),
		deptTree = Ext.getCmp('T_authorization-authorizeIndex_content').getDeptTree(); 
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
	authEditWindow : null,
	getAuthEditWindow : function(){
		if(this.authEditWindow==null){
			this.authEditWindow = Ext.create('Dpap.Authorization.AuthorizeEditWindow');
		}
		return this.authEditWindow;
	},
	saveAuthorize: function() {
		var selection = this.getSelectionModel().getSelection(),
			authForm = this.getAuthEditWindow().getAuthorizeForm(),
			roleChooseGrid = this.getAuthEditWindow().getRoleChooseGrid(),
			authRoleGrid = this.getAuthEditWindow().getAuthRoleGrid(),
			deptChooseTree = this.getAuthEditWindow().getDeptChooseTree();
		if (selection.length != 1) {
			Ext.MessageBox.alert(authorization.authorize.i18n('dpap.authorization.message'),
					authorization.authorize.i18n('dpap.authorization.operationMessage'));
			return;
		} else {
			var record = selection[0];
			authForm.loadRecord(record);
			if(record.get('empCode') != null){
				var empRecord = Ext.ModelManager.create(record.get('empCode'), 'Dpap.Organization.EmployeeModel');
				authForm.getForm().findField("empCode.empCode").select(empRecord);
			}
			roleChooseGrid.getStore().load();
			authRoleGrid.getStore().load();
			var rootNode = deptChooseTree.getRootNode();
			//刷新
			authorization.onRefreshTreeNodes(deptChooseTree.getStore(),rootNode.get("id"));
			this.getAuthEditWindow().show();
		}
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
			text : authorization.authorize.i18n('dpap.authorization.autorize'),
			hidden:!authorization.authorize.isPermission('../authorization/saveAuthorize.action'),
			handler : function(){
				me.saveAuthorize();
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 用户授权管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_authorization-authorizeIndex_content')) {
		return;
	}
	var deptTree = Ext.create('Dpap.Authorization.DepartmentTreePanle', {
		height: 717,
		flex : 3,
		title : authorization.authorize.i18n('dpap.authorization.deptTree'),
		root : {
			// 把id为0改成103 得到OA组织架构树
			id : '103',
			text : authorization.authorize.i18n('dpap.authorization.organization.root'),
			expanded : true
		},
		deptNameQuery : authorization.authorize.i18n('dpap.organization.organization.deptName'),
		queryName : authorization.authorize.i18n('dpap.authorization.find'),
		listeners : {
			itemclick : function(node, record, item, index, e) {
				if (record.data.id != '103') {
					Ext.getCmp('T_authorization-authorizeIndex_content').getAuthorizeGrid().getPagingToolbar().moveFirst();
				}
			}
		}
	});
	var queryForm = Ext.create('Dpap.Authorization.QueryForm');
	var authorizeGrid = Ext.create('Dpap.Authorization.AuthorizeGrid');
	Ext.create('Ext.panel.Panel', {
		id : 'T_authorization-authorizeIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'hbox',
		getQueryForm : function() {
			return queryForm;
		},
		getAuthorizeGrid : function() {
			return authorizeGrid;
		},
		getDeptTree : function() {
			return deptTree;
		},
		items : [ deptTree, {
			xtype : 'container',
			margin : '0 0 0 15',
			flex : 7,
			items : [ queryForm, authorizeGrid ]
		} ],
		renderTo : 'T_authorization-authorizeIndex-body'
	});
});