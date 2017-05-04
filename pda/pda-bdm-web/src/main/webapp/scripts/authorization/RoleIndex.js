/**
 * 角色表单
 */
Ext.define('Dpap.Authorization.RoleForm', {
	extend : 'Ext.form.Panel',
	title: authorization.role.i18n('dpap.authorization.role.roleform'),
	frame : true,
	roleFormUrl:  '../authorization/saveRole.action',
	collapsible: true,
    defaults: {
    	margin:'5 10 5 10',
        anchor: '40%',
        labelWidth:60
    },
    items :[{
		name : 'id',
		hidden : true
	},{
		name: 'roleName',
		allowBlank : false,
		xtype : 'textfield',
		blankText : authorization.role.i18n('dpap.authorization.RoleNameNullException'),
        fieldLabel: authorization.role.i18n('dpap.authorization.role.roleName')
	},{
		name: 'roleDesc',
		height: 45,
        fieldLabel: authorization.role.i18n('dpap.authorization.role.roleDesc'),
        xtype : 'textarea'
    }],
    saveRole : function() {
    	var formView = this.up('form'),
    		form = this.up('form').getForm(),
    		window = this.up('window'),
    		gridStore = Ext.getCmp('T_authorization-roleIndex_content').getRoleGrid().getStore(),
    		tree = window.getFunctionChooseTree();
		if (form.isValid()) { 
			var new_record = form.getRecord();
			form.updateRecord(new_record);
			var _funs = new Array();
			var nodes = tree.getChecked();
			Ext.each(nodes, function(node) {
				var validFlag = false;
				if(!node.isLeaf()&&(node.childNodes==null||node.childNodes.length<=0)){
					validFlag = true;
				}
				var fun = {
						'id' : node.raw.entity.id,
						'validFlag' : validFlag
				};
				_funs.push(fun);
			});
			var array = {role:new_record.data,chooseFunctions:_funs};
	    	Ext.Ajax.request({
				  url : formView.roleFormUrl,
				  jsonData:array,
				  success : function(response) {
						var json = Ext.decode(response.responseText);
						gridStore.load();
						Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),json.message,function(optional){
							window.hide();
						});
				   },
				   exception : function(response) {
						var json = Ext.decode(response.responseText);
						Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),json.message);
				   }
			   });
	    	}
	},
	reset : function(button,even) {
		var form = this.up('form'),
			tree = this.up('window').getFunctionChooseTree(),
			grid = Ext.getCmp('T_authorization-roleIndex_content').getRoleGrid(),
			rootNode = tree.getRootNode();
		button.setDisabled(true);
		if(form.roleFormUrl=='saveRole.action'){
			var role = Ext.create('Dpap.Authorization.RoleModel');
			form.loadRecord(role);
			form.getForm().reset();
		}else if(form.roleFormUrl=='updateRole.action'){
			var selection = grid.getSelectionModel().getSelection();
			var record = selection[0];
			form.loadRecord(record);
		}
		authorization.onRefreshTreeNodes(tree.getStore(),rootNode.get("id"));
	},
	saveButton : null,
	getSaveButton : function(){
		var me = this;
		if(this.saveButton==null){
			this.saveButton = Ext.create('Ext.Button', {
				text : authorization.role.i18n('dpap.authorization.save'),
				handler : me.saveRole
			});
		}
		return this.saveButton;
	},
	resetButton : null,
	getResetButton : function(){
		var me = this;
		if(this.resetButton==null){
			this.resetButton = Ext.create('Ext.Button', {
				text : authorization.role.i18n('dpap.authorization.reset'),
				handler : me.reset
			});
		}
		return this.resetButton;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.buttons = [me.getResetButton(), me.getSaveButton()],
		me.callParent([cfg]);
	}
});

/**
 * 定义功能选择树的store
 */
Ext.define('Dpap.Authorization.FunctionChooseTreeStore', {
	extend : 'Ext.data.TreeStore',
	model : 'Dpap.Authorization.TreeNodeModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url:'../authorization/loadFunctionChooseTree.action'
	},
	root : {
		id : '0',
		text : authorization.role.i18n('dpap.authorization.Function.app'),
		//expanded : true,
		checked:null
	}
});

Ext.define('Dpap.Authorization.FunctionChooseTree',{
	extend  : 'Ext.tree.Panel',
	title : authorization.role.i18n('dpap.authorization.Function.list'),
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	height :300,
    collapsible: true,
    useArrows: true,
    multiSelect: true,
    columns: [{
        xtype: 'treecolumn',
        text: authorization.role.i18n('dpap.authorization.Function.functionName'),
        width:250,
		dataIndex : 'text'
    },{
    	text: authorization.role.i18n('dpap.authorization.Function.URI'),
		dataIndex : 'entity',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.uri;
		},
		width:250
    },{
		text : authorization.role.i18n('dpap.authorization.role.roleDesc'),
		width:230,
		dataIndex : 'entity',
		renderer : function(value){
			if(value==null){
				return '';
			}
			return value.functionDesc;
		}
	} ],
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
			var pNode = node.parentNode;
			if(pNode!=null){
				// 选择所有的父节点
				for (; pNode.data.id != this.store.root.id; pNode = pNode.parentNode) {
					if (pNode.data.checked == true)
						break;
					pNode.set("checked", true);
				}				
			}
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
		me.store = Ext.create('Dpap.Authorization.FunctionChooseTreeStore',{
			listeners : {
				'beforeload': function(store, operation, eOpts){
					var window = me.up('window');
					if(window==null){
						return;
					}
					var role = window.getRoleForm().getForm().getRecord();
					if(role == null){
						return;
					}
					if(undefined === role.raw){
						return;
					}
					if(operation.params==null){
						operation.params = {};
					}
					Ext.apply(operation.params,{
						'roleId' : role.raw.id
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


/**
 * 角色编辑窗口
 */
Ext.define('Dpap.Organization.RoleEditWindow',{
	extend : 'Ext.window.Window',
	title : authorization.role.i18n('dpap.authorization.windowTitle'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :800,
	height :590,
    roleForm : null,
    getRoleForm : function(){
    	if(this.roleForm==null){
    		this.roleForm = Ext.create('Dpap.Authorization.RoleForm');
    	}
    	return this.roleForm;
    },
    functionChooseTree : null,
    getFunctionChooseTree : function(){
    	if(this.functionChooseTree==null){
    		this.functionChooseTree = Ext.create('Dpap.Authorization.FunctionChooseTree');
    	}
    	return this.functionChooseTree;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getRoleForm(), me.getFunctionChooseTree()];
		me.callParent([cfg]);
	}
});

/**
 * 角色查询表单
 */
Ext.define('Dpap.Authorization.QueryForm', {
	extend : 'Ext.form.Panel',
	title: authorization.role.i18n('dpap.authorization.role.findcase'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	items :[{
		name: 'roleName',
        fieldLabel: authorization.role.i18n('dpap.authorization.role.roleName'),
        xtype : 'textfield'
	},{
		xtype : 'container',
		margin : '0 0 0 0',
		items : [{
			xtype : 'button', 
			width:70,
			text : authorization.role.i18n('dpap.authorization.find'),
			handler : function() {
				var grid = Ext.getCmp('T_authorization-roleIndex_content').getRoleGrid();
				grid.getPagingToolbar().moveFirst();
			}
		}]
	}]
});

/**
 * 角色列表
 */
Ext.define('Dpap.Authorization.RoleGridPanel', {
	extend: 'Ext.grid.Panel',
	title : authorization.role.i18n('dpap.authorization.role.roleGrid'),
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	columns : [{xtype: 'rownumberer'},{
		text : authorization.role.i18n('dpap.authorization.role.roleName'),
		flex: 1,
		dataIndex : 'roleName'
	},{
		text : authorization.role.i18n('dpap.authorization.role.roleDesc'),
		flex: 2,
		dataIndex : 'roleDesc'
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
	roleEditWindow : null,
	getRoleEditWindow : function(){
		if(this.roleEditWindow==null){
			this.roleEditWindow = Ext.create('Dpap.Organization.RoleEditWindow');
		}
		return this.roleEditWindow;
	},
	addRole : function() {
		var window = this.up('grid').getRoleEditWindow(),
			form = window.getRoleForm(),
			tree = window.getFunctionChooseTree(),
			role = Ext.create('Dpap.Authorization.RoleModel'),
			rootNode = tree.getRootNode();
		form.roleFormUrl = '../authorization/saveRole.action';
		form.loadRecord(role);
		form.getForm().reset();
		authorization.onRefreshTreeNodes(tree.getStore(),rootNode.get("id"));
		window.show();
	},
	updateRole : function() {
		var grid = this.up('grid'),
			window = grid.getRoleEditWindow(),
			form = window.getRoleForm(),
			tree = window.getFunctionChooseTree(),
			selection = grid.getSelectionModel().getSelection();
		if (selection.length != 1) {
			Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),authorization.role.i18n('dpap.authorization.operation_message'));
			return;
		} else {
			form.roleFormUrl = '../authorization/updateRole.action';
			var record = selection[0];
			form.loadRecord(record);
			var rootNode = tree.getRootNode();
			authorization.onRefreshTreeNodes(tree.getStore(),rootNode.get("id"));
			window.show();
		}
	},
	deleteRole: function() {
		var grid = this.up('grid'),
			selection = grid.getSelectionModel().getSelection();
		if (selection.length == 0) {
			Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),authorization.role.i18n('dpap.authorization.operation_message'));
			return;
		}
		if (selection.length>1) {
			Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),authorization.role.i18n('dpap.authorization.operation_message'));
			return;
		}
		var msg=':'+selection[0].get('roleName')+'!';
		Ext.MessageBox.confirm(authorization.role.i18n('dpap.authorization.confirm')
				,authorization.role.i18n('dpap.authorization.confirmMessage')+msg,
			function(e) {
				if (e == 'yes') {
					Ext.Ajax.request({
						url : '../authorization/deleteRole.action',
						params : {
							'roleId' : selection[0].get('id')
						},
						success : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),json.message);
							if(json.success){
								grid.getStore().load();					
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
							Ext.MessageBox.alert(authorization.role.i18n('dpap.authorization.message'),json.message);
						}
					});
				}
			});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.RoleStore',{
			autoLoad : true,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_authorization-roleIndex_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'role.roleName' : queryForm.getForm().findField('roleName').getValue()
							}
						});	
					}
				}
		    }
		});
		me.tbar = [{
			text : authorization.role.i18n('dpap.authorization.add'),
			hidden:!authorization.role.isPermission('../authorization/saveRole.action'),
			handler : me.addRole
		},'-', {
			text : authorization.role.i18n('dpap.authorization.update'),
			hidden:!authorization.role.isPermission('../authorization/updateRole.action'),
			handler : me.updateRole
		},'-', {
			text : authorization.role.i18n('dpap.authorization.delete'),
			hidden:!authorization.role.isPermission('../authorization/deleteRole.action'),
			handler : me.deleteRole
		} ];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 角色管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_authorization-roleIndex_content')) {
		return;
	}
	var queryForm = Ext.create('Dpap.Authorization.QueryForm');
	var roleGrid = Ext.create('Dpap.Authorization.RoleGridPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_authorization-roleIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getQueryForm : function() {
			return queryForm;
		},
		getRoleGrid : function() {
			return roleGrid;
		},
		items : [ queryForm, roleGrid],
		renderTo : 'T_authorization-roleIndex-body'
	});
});