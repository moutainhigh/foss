
/**
 * 全局变量及函数
 */
baseinfo.role.resourceCodes=new Array();
baseinfo.role.deleteResourceCodes=new Array();
baseinfo.role.tempResourceCodes = new Array(); 
baseinfo.role.tempRemoveResourceCodes = new Array();
baseinfo.role.userResourceConflictInfo = "";
baseinfo.role.validate = false;

baseinfo.role.resourceGuiCodes=new Array();
baseinfo.role.deleteGuiResourceCodes=new Array();
baseinfo.role.tempGuiResourceCodes = new Array();
baseinfo.role.userGuiResourceConflictInfo = "";
baseinfo.role.validateGui = false;
// 当前操作的角色
baseinfo.role.currRoleCode="";

baseinfo.role.currGuiRoleCode="";
/**
 * 字符串数组操作
 */
baseinfo.role.removeStr = function(p_array, str){
	if(p_array==null || p_array.length==0){
		return p_array;
	}
	
	for(var i=0,l=p_array.length;i<l;i++){
		if(p_array[i]==str){
			p_array.splice(i,1);
			return p_array;
		}
	}
	return p_array;
};


/**
 * 字符串数组操作
 */
baseinfo.role.removeGuiStr = function(p_array, str){
	if(p_array==null || p_array.length==0){
		return p_array;
	}
	
	for(var i=0,l=p_array.length;i<l;i++){
		if(p_array[i]==str){
			p_array.splice(i,1);
			return p_array;
		}
	}
	return p_array;
};

//扩展方法去掉数组指定元素
Array.prototype.indexOf = function(val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

// checked所有父结点
baseinfo.role.checkParent = function(node,checked){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			baseinfo.role.resourceCodes.push(parentNode.data.id);
			baseinfo.role.removeStr(baseinfo.role.deleteResourceCodes,parentNode.data.id);
			parentNode.set('checked',true);
		}
		baseinfo.role.checkParent(parentNode,checked);
	}
};

baseinfo.role.checkGuiParent = function(node,checked){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			baseinfo.role.resourceGuiCodes.push(parentNode.data.id);
			baseinfo.role.removeGuiStr(baseinfo.role.deleteGuiResourceCodes,parentNode.data.id);
			parentNode.set('checked',true);
		}
		baseinfo.role.checkGuiParent(parentNode,checked);
	}
};

baseinfo.role.setRoleCode = function(roleCode){
	var resourceVo=new Object();
	resourceVo.currRoleCode= roleCode;
	var params = {'resourceVo':resourceVo};
	var r_url = "setCurrRoleCode.action";
	r_url=	baseinfo.realPath(r_url) ;
	Ext.Ajax.request({
		url: r_url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				treeData= result.nodes;
				return treeData;
			}else{
			}
		}
	});
};

baseinfo.role.setGuiRoleCode = function(roleCode){
	var resourceVo=new Object();
	resourceVo.currGuiRoleCode= roleCode;
	var params = {'resourceVo':resourceVo};
	var r_url = "setCurrRoleCode.action";
	r_url=	baseinfo.realPath(r_url) ;
	Ext.Ajax.request({
		url: r_url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				treeData= result.nodes;
				return treeData;
			}else{
			}
		}
	});
};

// 重新加载树结点
baseinfo.role.reloadTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Foss_baseinfo_role_RoleTree_Id'),
		treeStore = resourceTree.getStore(),
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	baseinfo.role.resourceCodes=new Array();
	baseinfo.role.deleteResourceCodes=new Array();
};

//重新加载树结点
baseinfo.role.reloadGuiTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Foss_baseinfo_role_GuiRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	baseinfo.role.resourceGuiCodes=new Array();
	baseinfo.role.deleteGuiResourceCodes=new Array();
};

// 重新加载查看的树结点
baseinfo.role.reloadSeeTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Foss_baseinfo_role_SeeRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	baseinfo.role.resourceCodes=new Array();
	baseinfo.role.deleteResourceCodes=new Array();
};

//重新加载查看的树结点
baseinfo.role.reloadGuiSeeTreeNode = function(){
	// 重新加载树结点：
	var resourceTree=Ext.getCmp('Foss_baseinfo_role_SeeGuiRoleTree_Id'),
		treeStore = resourceTree.getStore();
		rootNode = treeStore.getRootNode()
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	// 加载树结点时，初始化已选树结点，作废的树结点
	baseinfo.role.resourceGuiCodes=new Array();
	baseinfo.role.deleteGuiResourceCodes=new Array();
};

// 查询条件面板
Ext.define('Foss.baseinfo.role.QueryRoleConditionForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_role_QueryRoleConditionForm_Id',
	layout:'column',
	frame : true,
	title: baseinfo.role.i18n('foss.baseinfo.queryCondition'),
	defaults: {
		margin:'5 10 5 10'
	},
	layout : 'column',	
	defaultType : 'textfield',
	initComponent: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
				fieldLabel:baseinfo.role.i18n('foss.baseinfo.roleName'),
				xtype : 'commonroleselector', 
				name:'name', 
				displayField : 'name',// 值
				valueField : 'name',// 值
				showContent : '{name}',// 显示表格列   
	    		labelWidth: 70,					
	    		columnWidth:.3
			},{
        		xtype : 'button',
				cls:'yellow_button',
				text: baseinfo.role.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.role.isPermission('roleindex/roleQueryButton'),
				hidden:!baseinfo.role.isPermission('roleindex/roleQueryButton'),
				columnWidth:.1,
				// 查询按钮的响应事件：
				handler: function() {
					var selectResultPanel=Ext.getCmp("Foss_baseinfo_role_QueryRoleResultGrid_Id");
					selectResultPanel.setVisible(true);						
					baseinfo.role.pagingBar.moveFirst();
				}
			}
		];
		me.callParent([cfg]);
	}
});

// 站点model
Ext.define('Foss.baseinfo.role.RoleModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'code',
        type : 'string'
    },{
        name : 'name',// 序号
        type : 'string'
    },{
        name : 'notes',// 所属站点组
        type : 'string'
    }]
});

// 查询结果的表格：
Ext.define('Foss.baseinfo.role.QueryRoleResultGrid',{
	extend: 'Ext.grid.Panel',
	id : 'Foss_baseinfo_role_QueryRoleResultGrid_Id',
	title : baseinfo.role.i18n('foss.baseinfo.roleQuery'),
	// 宾单单建议添加
	cls:'autoHeight',
	bodyCls:'autoHeight',
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
    enableColumnHide:false,
    // 设置“如果查询的结果为空，则提示”
    emptyText: baseinfo.role.i18n('foss.baseinfo.queryResultIsNull'),
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
    frame: true,
    addRoleWindow: null,
	getAddRoleWindow: function(){
		if(this.addRoleWindow == null){
			this.addRoleWindow = Ext.create('Foss.baseinfo.role.AddRoleWindow');			
			baseinfo.role.addRoleWindow = this.addRoleWindow;
		}
		return this.addRoleWindow;
	},
    updateRoleWindow: null,
	getUpdateRoleWindow: function(){
		if(this.updateRoleWindow == null){
			this.updateRoleWindow = Ext.create('Foss.baseinfo.role.UpdateRoleWindow');			
			baseinfo.role.updateRoleWindow = this.updateRoleWindow;
		}
		return this.updateRoleWindow;
	},
    seeRoleWindow: null,
	getSeeRoleWindow: function(){
		if(this.seeRoleWindow == null){
			this.seeRoleWindow = Ext.create('Foss.baseinfo.role.SeeRoleWindow');
			baseinfo.role.seeRoleWindow = this.seeRoleWindow;
		}
		return this.seeRoleWindow;
	},
	columns : [{
            xtype:'actioncolumn',
            flex: 1,
			text: baseinfo.role.i18n('foss.baseinfo.operate'),
			align: 'center',
            items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.role.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.role.isPermission('roleindex/roleUpdateButton'),
                handler: function(gridView, rowIndex, colIndex) {
                	var grid = gridView.up('grid'),
                		window = grid.getUpdateRoleWindow();
					// 获得当前选择的行的的数据
					var rowInfo = grid.getStore().getAt(rowIndex);
					// 设置当前的角色编码：
					baseinfo.role.currRoleCode = rowInfo.data.code;
					baseinfo.role.currGuiRoleCode = rowInfo.data.code;
					Ext.getCmp('Foss_baseinfo_role_RoleTree_QueryText_Id').setValue('');
					Ext.getCmp('Foss_baseinfo_role_GuiRoleTree_QueryText_Id').setValue('');
					// 重新加载树结点：
					baseinfo.role.reloadTreeNode();
					baseinfo.role.reloadGuiTreeNode();
					// 设置角色角色详情
					var a_roleDetailForm=window.getQueryRoleDetailForm();
					a_roleDetailForm.getForm().loadRecord(rowInfo);
					//打开时先清空对象
					baseinfo.role.tempResourceCodes = [];
					baseinfo.role.tempRemoveResourceCodes = [];
					baseinfo.role.userResourceConflictInfo = "";
					baseinfo.role.validate = false;
					
					baseinfo.role.tempGuiResourceCodes = [];
					baseinfo.role.userGuiResourceConflictInfo = "";
					baseinfo.role.validateGui = false;
					// 修改时，先查出当前角色的详情，权限树，角色所包含的权限，然后展示在修改界面中，
					window.show();
                }
            },{
            	iconCls:'deppon_icons_showdetail',
                tooltip: baseinfo.role.i18n('foss.baseinfo.see'),
                disabled:!baseinfo.role.isPermission('roleindex/roleSeeButton'),
                handler: function(gridView, rowIndex, colIndex) {
                	var grid = gridView.up('grid'),
            			window = grid.getSeeRoleWindow();
					// 获得当前选择的行的的数据
					var rowInfo = grid.getStore().getAt(rowIndex);
					// 设置当前的角色编码：
					baseinfo.role.currRoleCode = rowInfo.data.code;
					baseinfo.role.currGuiRoleCode = rowInfo.data.code;
					Ext.getCmp('Foss_baseinfo_role_SeeRoleTree_QueryText_Id').setValue('');
					Ext.getCmp('Foss_baseinfo_role_SeeGuiRoleTree_QueryText_Id').setValue('');
					// 重新加载树结点：
					baseinfo.role.reloadSeeTreeNode();
					baseinfo.role.reloadGuiSeeTreeNode();
					// 设置角色角色详情
					var a_roleDetailForm=window.getQueryRoleDetailForm();
					a_roleDetailForm.getForm().loadRecord(rowInfo);
					// 修改时，先查出当前角色的详情，权限树，角色所包含的权限，然后展示在修改界面中，
					window.show();
                }
            }]
        },{
			text : baseinfo.role.i18n('foss.baseinfo.roleCode'),
			align: 'center',
			flex: 2,
			dataIndex :'code'
		},{
			text : baseinfo.role.i18n('foss.baseinfo.roleName'),
			align: 'center',
			flex: 2,
			dataIndex : 'name'
		},{
			text: baseinfo.role.i18n('foss.baseinfo.notes'),
			flex: 5,
			// 让表格可以自动换行
			xtype:'linebreakcolumn',
			dataIndex: 'notes'
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.RowModel');
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.role.RoleModel',
			pageSize: 25,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				url : baseinfo.realPath("queryRoleListByName.action"),
				// 定义一个读取器
				reader: {
					type: 'json',
					root: 'roleVo.roleEntityList',
					totalProperty : 'totalCount'
				}
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var queryForm = Ext.getCmp("Foss_baseinfo_role_QueryRoleConditionForm_Id");
					if (queryForm != null) {
						var a_name = queryForm.getForm().findField('name').getValue();
						Ext.apply(operation, {
							params : {
								'roleVo.roleEntityDetail.name' : a_name
							}
						});	
					}
				}
			}
		});
		me.tbar = [{ 
			text: '新增',
			disabled:!baseinfo.role.isPermission('roleindex/roleAddButton'),
			hidden:!baseinfo.role.isPermission('roleindex/roleAddButton'),
			handler: function(){
				var window = me.getAddRoleWindow(),
					form = window.getRoleDetailForm().getForm();
				form.reset();
				window.show();
				
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		baseinfo.role.pagingBar = me.bbar;
		me.callParent(cfg);
	}
});

//角色详情：
Ext.define('Foss.baseinfo.role.RoleDetailForm',{
	extend:'Ext.form.Panel',
	defaultType : 'textfield',
	defaults: {
		margin:'5 5 5 5',
		allowBlank: false,
		labelWidth: 80
	},
	initComponent: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',
			fieldLabel: baseinfo.role.i18n('foss.baseinfo.roleName')
		},{
			name: 'code',
			fieldLabel: baseinfo.role.i18n('foss.baseinfo.roleCode')
		},{
			name: 'notes',
			allowBlank: true,
			fieldLabel: baseinfo.role.i18n('foss.baseinfo.notes')
		}];
		me.callParent(cfg);
	}
});

//定义新增角色窗口
Ext.define('Foss.baseinfo.role.AddRoleWindow',{
	extend: 'Ext.window.Window',
	title: baseinfo.role.i18n('foss.baseinfo.addRole'),
	width: 300,
	height: 200,
	modal: true,
	closeAction:'hide',
	roleDetailForm: null,
	// 角色管理表单
	getRoleDetailForm: function(){
		if(this.roleDetailForm==null){
			this.roleDetailForm = Ext.create("Foss.baseinfo.role.RoleDetailForm");
		}
		return this.roleDetailForm;
	},
	// 定义获取按钮的一些方法：
	cancelButton : null,
	saveButton : null,
	getCancelButton:function(){
		if(this.cancelButton==null){
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: baseinfo.role.i18n('foss.baseinfo.cancel'),
				handler: function(){
					this.up('window').hide();
				}
			});
		}
		return this.cancelButton;
	},
	getSaveButton: function(){
		if(this.saveButton==null){
			this.saveButton = Ext.create('Ext.button.Button',{
				cls:'yellow_button',
				text: baseinfo.role.i18n('foss.baseinfo.save'),
				handler: function(){
					var win = this.up('window'),
						roleVo = new Object(),
						form = win.getRoleDetailForm().getForm();
					if(!form.isValid()){
						return;
					}
					roleVo.roleEntityDetail = form.getValues();
					var params = {'roleVo':roleVo};
					var r_url = "addRole.action";
					r_url=	baseinfo.realPath(r_url) ;
					Ext.Ajax.request({
						method:'POST',
						url: r_url,
						jsonData:params,
						success:function(response){
							var result = Ext.decode(response.responseText),
								queryForm = Ext.getCmp("Foss_baseinfo_role_QueryRoleConditionForm_Id"),
								queryReslutGrid = Ext.getCmp("Foss_baseinfo_role_QueryRoleResultGrid_Id");
							if(result.success){
								Ext.ux.Toast.msg(baseinfo.role.i18n('foss.baseinfo.tips'), result.message, 'ok', 1000);
								if (queryForm != null) {
									queryForm.getForm().findField('name').setValue(roleVo.roleEntityDetail.name);
								}
								queryReslutGrid.getStore().loadPage(1);
								win.hide();
							}else{
								Ext.ux.Toast.msg(baseinfo.role.i18n('foss.baseinfo.tips'), result.message, 'error', 1000);
							}
						}
					});
				}
			});
		}
		return this.saveButton;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getRoleDetailForm()
	    ];
		me.buttons = [
  			// 取消，保存按钮：
			me.getCancelButton(),'->',
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});

// 定义修改角色窗口
Ext.define('Foss.baseinfo.role.UpdateRoleWindow',{
	extend: 'Ext.window.Window',
	title: baseinfo.role.i18n('foss.baseinfo.updateRoleResource'),
	width: 700,
	height: 670,
	modal: true,
	closeAction:'hide',
	queryRoleDetailForm: null,
	// 角色详情
	getQueryRoleDetailForm: function(){
		if(this.queryRoleDetailForm==null){
			this.queryRoleDetailForm = Ext.create("Foss.baseinfo.role.QueryRoleDetailForm");
		}
		return this.queryRoleDetailForm;
	},
	roleTab: null,
	getRoleTab: function(){
		if(this.roleTab==null){
			this.roleTab = Ext.create("Foss.baseinfo.role.RoleTab");
		}
		return this.roleTab;
	},
	// 定义获取按钮的一些方法：
	cancelButton : null,
	resetButton : null,
	saveButton : null,
	getCancelButton:function(){
		if(this.cancelButton==null){
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: baseinfo.role.i18n('foss.baseinfo.cancel'),
				handler: function(){
					this.up('window').hide();
				}
			});
		}
		return this.cancelButton;
	},
	getResetButton : function(){
		if(this.resetButton==null){
			this.resetButton = Ext.create('Ext.button.Button',{
				cls:'yellow_button',
				name: 'reset',
				text: baseinfo.role.i18n('foss.baseinfo.reset'),
				handler: function() {
					// 重新加载
					baseinfo.role.reloadTreeNode();
				}
			});
		}
		return this.resetButton; 
	},
	getSaveButton : function(){
		var  me = this;
		if(me.saveButton==null){
			me.saveButton = Ext.create('Ext.button.Button',{
				cls:'yellow_button',
				text: baseinfo.role.i18n('foss.baseinfo.save'),
				handler: function() { 
					var roleVo = new Object();
					roleVo.roleEntityDetail = this.up('window').getQueryRoleDetailForm().getRecord().data;
					// 改为只取新增和作废的，保持不变的不取
					roleVo.resourceCodes = baseinfo.role.resourceCodes.join(",");
					roleVo.deleteResourceCodes = baseinfo.role.deleteResourceCodes.join(",");
					//	GUI 权限保存【新增】
					if(Ext.isEmpty(roleVo.resourceCodes)){
						roleVo.resourceCodes = baseinfo.role.resourceGuiCodes.join();
					}else{
						roleVo.resourceCodes += ',';
						roleVo.resourceCodes += baseinfo.role.resourceGuiCodes.join();
					}
					//GUI 权限保存【删除】
					if(Ext.isEmpty(roleVo.deleteResourceCodes)){
						roleVo.deleteResourceCodes = baseinfo.role.deleteGuiResourceCodes.join();
					}else{
						roleVo.deleteResourceCodes += ',';
						roleVo.deleteResourceCodes += baseinfo.role.deleteGuiResourceCodes.join();
					}
					
					// 将角色编码及包含的权限发送到后台：
					var params = {'roleVo':roleVo};
					var r_url = "updateRoleResource.action";
					r_url=	baseinfo.realPath(r_url) ;
					Ext.Ajax.request({
						method:'POST',
						url: r_url,
						jsonData:params,
						success:function(response){
							var result = Ext.decode(response.responseText);
							if(result.success){
								treeData= result.nodes;
								msgTip = baseinfo.role.i18n('foss.baseinfo.saveSuccessful');
								myMask.hide()
								me.hide();
								Ext.ux.Toast.msg(baseinfo.role.i18n('foss.baseinfo.tips'), msgTip, 'ok', 1000);
								return treeData;
							}else{
								msgTip = baseinfo.role.i18n('foss.baseinfo.saveFail');
								myMask.hide();
								Ext.ux.Toast.msg(baseinfo.role.i18n('foss.baseinfo.tips'), msgTip, 'error', 1000);
							}
						}
					});
					var myMask = new Ext.LoadMask(me, {msg: baseinfo.role.i18n('foss.baseinfo.wateMsg')});
					myMask.show();
				}
			});
		}
		return this.saveButton;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
			me.getQueryRoleDetailForm(),me.getRoleTab()
	    ];
		me.buttons = [
  			// 取消，重置，查询按钮：
			me.getCancelButton(),'->',
			me.getResetButton(),
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});

// 定义treeStore
Ext.define('Foss.baseinfo.role.RoleTreeStore',{
  	extend: 'Ext.data.TreeStore',
  	storeId: 'Foss_baseinfo_role_RoleTreeStore_Id',
  	root: {
		text:baseinfo.role.i18n('foss.baseinfo.appSystem'),
		id : '0'//,
		//expanded: true
	},
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryResourceByParentResSelect.action"),
         reader: {
             type: 'json',
             root: 'nodes'
         }
  	},
  	// 设置action层接收父结点的标识：
  	// nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'resourceVo.resourceEntityDetail.parentRes.code':operation.params.node,
					// 当前操作的角色编码
					'resourceVo.currRoleCode': baseinfo.role.currRoleCode
				}
			});	
			
		}
	}  	
});    


//定义Gui treeStore
Ext.define('Foss.baseinfo.role.GuiRoleTreeStore',{
	extend: 'Ext.data.TreeStore',
	storeId: 'Foss_baseinfo_role_GuiRoleTreeStore_Id',
	root: {
		text:'GUI',
		id : 'GUI'//,
		//expanded: true
	},
	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryResourceByParentResSelect.action"),
      reader: {
          type: 'json',
          root: 'nodes'
      }
	},
	// 设置action层接收父结点的标识：
	// nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'resourceVo.resourceEntityDetail.parentRes.code':operation.params.node,
					// 当前操作的角色编码
					'resourceVo.currRoleCode': baseinfo.role.currRoleCode
				}
			});	
			
		}
	}  	
});  

// 定义baseinfo.role.i18n('foss.baseinfo.resource')树结构，
Ext.define('Foss.baseinfo.role.RoleTree',{
	extend:'Ext.tree.Panel',
	id: 'Foss_baseinfo_role_RoleTree_Id',
	autoScroll: true,
	animate: false,
	useArrows: true,
	frame: true,
	rootVisible: true,
	border: true,
	height : 450,
	/**
	 * 父节点选中时，选择所有子节点
	 */
	/*selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(checked){
			baseinfo.role.resourceCodes.push(a_code);
			baseinfo.role.removeStr(baseinfo.role.deleteResourceCodes, a_code);	
		}else{
			baseinfo.role.deleteResourceCodes.push(a_code);
			baseinfo.role.removeStr(baseinfo.role.resourceCodes, a_code);
		}
		Ext.Array.each(node.childNodes, function(childNode) {
			me.selectChildFunction(childNode, checked);
			childNode.set("checked", checked);	   
		});
	},*/
	/**
	 * 当所有子节点没有选中时候，取消选择父节点
	 */
	/*deSelectParentFunction : function(node) {
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
			var a_code=parentNode.data.id;
			baseinfo.role.deleteResourceCodes.push(a_code);
			baseinfo.role.removeStr(baseinfo.role.resourceCodes, a_code);
			parentNode.set("checked", false);		
		}
		this.deSelectParentFunction(parentNode);
	},*/
	/**
	*验证用户选择的权限是否冲突
	*/
	checkUserResourceConflictFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
	    var roleVo = new Object();
   		// 将角色名称发送到后台：
		roleVo.currRoleCode = baseinfo.role.currRoleCode;
		var params ={
				'roleVo':roleVo,
				'newResourceCode':a_code,
				'tempResourceCode': baseinfo.role.tempResourceCodes,
				'tempRemoveResourceCodes' : baseinfo.role.tempRemoveResourceCodes
		};
		var r_url = "checkUserAuthorityIsConflict.action";
		r_url=	baseinfo.realPath(r_url) ;
		Ext.Ajax.request({
			url : r_url,
			async: false,
			jsonData:params,
			success : function(response) {    								 
				json = Ext.decode(response.responseText);
				if(json && json.roleVo){
					baseinfo.role.validate = json.roleVo.validate;
					baseinfo.role.userResourceConflictInfo = json.roleVo.userResourceConflictInfo;
					
				}
			}
		});

		},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
			this.checkUserResourceConflictFunction(node,true);
			var validate = baseinfo.role.validate;
			if(!validate){
				var info = "修改角色权限存在互斥："+baseinfo.role.userResourceConflictInfo;
				Ext.Msg.show({
					title:'信息提示（失败）',
					msg: info,
					buttons : Ext.Msg.OK,
					icon : Ext.Msg.ERROR
				});
				node.set('checked',false);
				return;
			}
			baseinfo.role.tempResourceCodes.push(a_code);
			baseinfo.role.tempRemoveResourceCodes.remove(a_code);
			baseinfo.role.checkParent(node, true);
			//this.selectChildFunction(node, true);
			baseinfo.role.resourceCodes.push(a_code);
			baseinfo.role.removeStr(baseinfo.role.deleteResourceCodes, a_code);	
		} else {
		    //去除用户取消的编码
		    baseinfo.role.tempResourceCodes.remove(a_code);
		    baseinfo.role.tempRemoveResourceCodes.push(a_code);
			//this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			//this.deSelectParentFunction(node);
			//如果是取消树结点，先判断是否还有下级已选择的树结点，如果有，则递归作废
			var resourceNum=0;
			var resourceVo = new Object();
			resourceVo.resourceEntityDetail = new Object();
			resourceVo.resourceEntityDetail.code = a_code;
			resourceVo.currRoleCode = baseinfo.role.currRoleCode;
			// 将角色名称发送到后台：
			var params = {'resourceVo':resourceVo};
			var r_url = "queryResourceCountByRoleResource.action";
			r_url=	baseinfo.realPath(r_url) ;
			Ext.Ajax.request({
				url : r_url,
				jsonData:params,
				success : function(response) {    								 
					json = Ext.decode(response.responseText);
					if(json && json.resourceVo && json.resourceVo.resourceNum){
						resourceNum = json.resourceVo.resourceNum;
					}
				}
			});
			if(resourceNum>1){
				var tip1=baseinfo.role.i18n('foss.baseinfo.tipInfo');
				var tip2=baseinfo.role.i18n('foss.baseinfo.areYouSureToVoid');
				Ext.MessageBox.buttonText.yes = baseinfo.role.i18n('foss.baseinfo.sure');
				Ext.MessageBox.buttonText.no = baseinfo.role.i18n('foss.baseinfo.cancel');
				Ext.Msg.confirm(tip1, tip2, function(btn,text) {
					if (btn == 'yes') {
						var r_url = "deleteResourceByRoleResource.action";
						r_url=	baseinfo.realPath(r_url) ;
						Ext.Ajax.request({
							url : r_url,
							jsonData:params,
							success : function(response) {    								 
								json = Ext.decode(response.responseText);
								if(json && json.resourceVo && json.resourceVo.resourceNum){
									resourceNum = json.resourceVo.resourceNum;
									if(resourceNum>0){
										// 执行完作废操作后，提示用户
										msgTip=baseinfo.role.i18n('foss.baseinfo.voidSuccessNo');
										Ext.Msg.alert(baseinfo.role.i18n('foss.baseinfo.tips'), msgTip);			
									}				
								}
							}
						});	
					}
					// 点击之后，重新加载树结点
					node.parentNode.set('expanded', false);
					node.parentNode.set('loading', false);
					node.parentNode.set('loaded', false);
					node.parentNode.expand();
				});
			}
			baseinfo.role.deleteResourceCodes.push(a_code);
			baseinfo.role.removeStr(baseinfo.role.resourceCodes, a_code);
		}
	},
//	itemExpand : function(node) {
//		if(node.data.checked==null||node.data.checked==node.raw.checked){
//			return;
//		}
//		//得到更改后的选中判断值
//		var checked = node.data.checked;
//
//		/* ***** 选中时将子节点全部选中****** */
//		var parentNode = node;
//		if (parentNode.hasChildNodes()) {
//			for (var i = 0; i < parentNode.childNodes.length; i++) {
//				var childNode = parentNode.childNodes[i];
//				childNode.set("checked", checked);
//				this.selectChildFunction(childNode, checked);
//			}
//		}
//	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.role.RoleTreeStore');
		me.tbar = [{
			xtype: 'textfield',
			id: 'Foss_baseinfo_role_RoleTree_QueryText_Id',
			name:'name',
			fieldLabel:baseinfo.role.i18n('foss.baseinfo.resourceName'),
			labelWidth: 70
		},{
			text: baseinfo.role.i18n('foss.baseinfo.query'),
			margin:'5 10 5 5',
			// 查询按钮的响应事件：
			handler: function() {
				// 根据权限名称查询树结构
				var a_resourceTree = Ext.getCmp("Foss_baseinfo_role_RoleTree_Id");
				var a_name= Ext.getCmp('Foss_baseinfo_role_RoleTree_QueryText_Id');
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
				// "../baseinfo/queryResourceFullPathByName.action"
				var r_url = "queryResourceFullPathByName.action";
				r_url=	baseinfo.realPath(r_url) ;
				Ext.Ajax.request({
					// 请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
					url : r_url,
					jsonData:params,
					success : function(response) {
						view = a_resourceTree.getView(),
						json = Ext.decode(response.responseText);
						var pathList =json.resourceVo.allFullPath;//获取权限的全部路径
						me.expandNodes = [];
						me.collapseAll();//开始时展开全部
						if(Ext.isEmpty(pathList)){
							baseinfo.showWoringMessage(baseinfo.role.i18n('foss.baseinfo.queryNoResult'));//查询无信息！
							return;
						}
						//循环得到的路径set集合
						for(var i=0;i<=pathList.length;i++){
							//根据路径进行展开
							me.expandPath(pathList[i],'id','/',function(success, lastNode){
								if(success){
									var nodeHtmlEl = view.getNode(lastNode),
										nodeEl = Ext.get(nodeHtmlEl);
									if(Ext.isEmpty(nodeEl)){
										me.expandNodes.push(lastNode);
										return;
									}
									var divHtmlEl = nodeEl.query('div')[0],
									    divEl = Ext.get(divHtmlEl);
									divEl.highlight("ff0000", { attr: 'color', duration: 5000 });
								}
							});
						}
					}
				});
			}
		}];
		// 监听鼠标事件
		me.listeners = {
			/*itemexpand : function(node){
				me.itemExpand(node);
			},*/
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
		me.callParent([cfg]);
	}
});

//定义baseinfo.role.i18n('foss.baseinfo.resource')树结构，
Ext.define('Foss.baseinfo.role.GuiRoleTree',{
	extend:'Ext.tree.Panel',
	id: 'Foss_baseinfo_role_GuiRoleTree_Id',
	autoScroll: true,
	animate: false,
	useArrows: true,
	frame: true,
	rootVisible: true,
	border: true,
	height : 450,
	/**
	 * 父节点选中时，选择所有子节点
	 */
	/*selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(checked){
			baseinfo.role.resourceGuiCodes.push(a_code);
			baseinfo.role.removeGuiStr(baseinfo.role.resourceGuiCodes, a_code);	
		}else{
			baseinfo.role.deleteGuiResourceCodes.push(a_code);
			baseinfo.role.removeGuiStr(baseinfo.role.resourceGuiCodes, a_code);
		}
		Ext.Array.each(node.childNodes, function(childNode) {
			me.selectChildFunction(childNode, checked);
			childNode.set("checked", checked);	   
		});
	},*/
	/**
	 * 当所有子节点没有选中时候，取消选择父节点
	 */
	/*deSelectParentFunction : function(node) {
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
			var a_code=parentNode.data.id;
			baseinfo.role.deleteGuiResourceCodes.push(a_code);
			baseinfo.role.removeGuiStr(baseinfo.role.resourceGuiCodes, a_code);
			parentNode.set("checked", false);		
		}
		this.deSelectParentFunction(parentNode);
	},*/
	/**
	*验证用户选择的权限是否冲突
	*/
	checkUserResourceConflictFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
	    var roleVo = new Object();
		// 将角色名称发送到后台：
		roleVo.currRoleCode = baseinfo.role.currGuiRoleCode;
		var params = {'roleVo':roleVo,
		'newResourceCode':a_code,
		'tempResourceCode': baseinfo.role.tempGuiResourceCodes};
		var r_url = "checkUserAuthorityIsConflict.action";
		r_url =	baseinfo.realPath(r_url) ;
		Ext.Ajax.request({
			url: r_url,
			async: false,
			jsonData:params,
			success : function(response) {    								 
				json = Ext.decode(response.responseText);
				if(json && json.roleVo){
					baseinfo.role.validateGui = json.roleVo.validate;
					baseinfo.role.userGuiResourceConflictInfo = json.roleVo.userResourceConflictInfo;
					
				}
			}
		
		});
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
		    this.checkUserResourceConflictFunction(node,true);
		    var validate = baseinfo.role.validateGui;
		    if(!validate){
		    var info = "修改角色权限存在互斥："+baseinfo.role.userGuiResourceConflictInfo;
			Ext.Msg.show({
			     title:'信息提示（失败）',
			     msg: info,
			     buttons : Ext.Msg.OK,
				 icon : Ext.Msg.ERROR
			});
			
		    node.set('checked',false);
		    return;
		  }
		    baseinfo.role.tempGuiResourceCodes.push(a_code);
			baseinfo.role.checkGuiParent(node, true);
			//this.selectChildFunction(node, true);
			baseinfo.role.resourceGuiCodes.push(a_code);
			baseinfo.role.removeGuiStr(baseinfo.role.deleteGuiResourceCodes, a_code);	
		} else {
		    //去除用户取消的编码
		    baseinfo.role.tempGuiResourceCodes.remove(a_code);
			//this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			//this.deSelectParentFunction(node);
			//如果是取消树结点，先判断是否还有下级已选择的树结点，如果有，则递归作废
			var resourceNum=0;
			var resourceVo = new Object();
			resourceVo.resourceEntityDetail = new Object();
			resourceVo.resourceEntityDetail.code = a_code;
			resourceVo.currRoleCode = baseinfo.role.currGuiRoleCode;
			// 将角色名称发送到后台：
			var params = {'resourceVo':resourceVo};
			var r_url = "queryResourceCountByRoleResource.action";
			r_url=	baseinfo.realPath(r_url) ;
			Ext.Ajax.request({
				url : r_url,
				jsonData:params,
				success : function(response) {    								 
					json = Ext.decode(response.responseText);
					if(json && json.resourceVo && json.resourceVo.resourceNum){
						resourceNum = json.resourceVo.resourceNum;
					}
				}
			});
			if(resourceNum>1){
				var tip1=baseinfo.role.i18n('foss.baseinfo.tipInfo');
				var tip2=baseinfo.role.i18n('foss.baseinfo.areYouSureToVoid');
				Ext.MessageBox.buttonText.yes = baseinfo.role.i18n('foss.baseinfo.sure');
				Ext.MessageBox.buttonText.no = baseinfo.role.i18n('foss.baseinfo.cancel');
				Ext.Msg.confirm(tip1, tip2, function(btn,text) {
					if (btn == 'yes') {
						var r_url = "deleteResourceByRoleResource.action";
						r_url=	baseinfo.realPath(r_url) ;
						Ext.Ajax.request({
							url : r_url,
							jsonData:params,
							// FALSE 同步)
							success : function(response) {    								 
								json = Ext.decode(response.responseText);
								if(json && json.resourceVo && json.resourceVo.resourceNum){
									resourceNum = json.resourceVo.resourceNum;
									if(resourceNum>0){
										// 执行完作废操作后，提示用户
										msgTip=baseinfo.role.i18n('foss.baseinfo.voidSuccessNo');
										Ext.Msg.alert(baseinfo.role.i18n('foss.baseinfo.tips'), msgTip);			
									}				
								}
							}
						});	
					}
					// 点击之后，重新加载树结点
					node.parentNode.set('expanded', false);
					node.parentNode.set('loading', false);
					node.parentNode.set('loaded', false);
					node.parentNode.expand();
				});
			}
			baseinfo.role.deleteGuiResourceCodes.push(a_code);
			baseinfo.role.removeGuiStr(baseinfo.role.resourceGuiCodes, a_code);
		}
	},
//	itemExpand : function(node) {
//		if(node.data.checked==null||node.data.checked==node.raw.checked){
//			return;
//		}
//		//得到更改后的选中判断值
//		var checked = node.data.checked;
//
//		/* ***** 选中时将子节点全部选中****** */
//		var parentNode = node;
//		if (parentNode.hasChildNodes()) {
//			for (var i = 0; i < parentNode.childNodes.length; i++) {
//				var childNode = parentNode.childNodes[i];
//				childNode.set("checked", checked);
//				this.selectChildFunction(childNode, checked);
//			}
//		}
//	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.role.GuiRoleTreeStore');
		me.tbar = [{
			xtype: 'textfield',
			id: 'Foss_baseinfo_role_GuiRoleTree_QueryText_Id',
			name:'name',
			fieldLabel:('GUI'+baseinfo.role.i18n('foss.baseinfo.resourceName')),
			labelWidth: 100
		},{
			text: baseinfo.role.i18n('foss.baseinfo.query'),
			margin:'5 10 5 5',
			// 查询按钮的响应事件：
			handler: function() {
				// 根据权限名称查询树结构
				var a_resourceTree = Ext.getCmp("Foss_baseinfo_role_GuiRoleTree_Id");
				var a_name= Ext.getCmp('Foss_baseinfo_role_GuiRoleTree_QueryText_Id');
				var itemId ='GUI';
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				resourceVo.resourceEntityDetail.belongSystemType=itemId;
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
				// "../baseinfo/queryResourceFullPathByName.action"
				var r_url = "queryResourceFullPathByName.action";
				r_url=	baseinfo.realPath(r_url) ;
				Ext.Ajax.request({
					// 请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
					url : r_url,
					jsonData:params,
					success : function(response) {
						view = a_resourceTree.getView(),
						json = Ext.decode(response.responseText);
						var pathList =json.resourceVo.allFullPath;//获取权限的全部路径
						me.expandNodes = [];
						me.collapseAll();//开始时关闭全部
						if(Ext.isEmpty(pathList)){
							baseinfo.showWoringMessage(baseinfo.role.i18n('foss.baseinfo.queryNoResult'));//查询无信息！
							return;
						}
						//循环得到的路径set集合
						for(var i=0;i<=pathList.length;i++){
							//根据路径进行展开
							me.expandPath(pathList[i],'id','/',function(success, lastNode){
								if(success){
									var nodeHtmlEl = view.getNode(lastNode),
										nodeEl = Ext.get(nodeHtmlEl);
									if(Ext.isEmpty(nodeEl)){
										me.expandNodes.push(lastNode);
										return;
									}
									var divHtmlEl = nodeEl.query('div')[0],
									    divEl = Ext.get(divHtmlEl);
									divEl.highlight("ff0000", { attr: 'color', duration: 5000 });
								}
							});
						}
					}
				});
			}
		}];
		// 监听鼠标事件
		me.listeners = {
			/*itemexpand : function(node){
				me.itemExpand(node);
			},*/
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
		me.callParent([cfg]);
	}
});
// 角色详情：
Ext.define('Foss.baseinfo.role.QueryRoleDetailForm',{
	extend:'Ext.form.Panel',
	defaultType : 'textfield',
	layout:'column',
	defaults: {
		margin:'5 5 5 5',
		readOnly: true,
		labelWidth: 80
	},
	initComponent: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',
			fieldLabel: baseinfo.role.i18n('foss.baseinfo.roleName'),
			columnWidth: 0.3
		},{
			name: 'notes',
			fieldLabel: baseinfo.role.i18n('foss.baseinfo.notes'),
			columnWidth: 0.5
		}];
		me.callParent(cfg);
	}
});

// 定义WEB treeStore
Ext.define('Foss.baseinfo.role.SeeRoleTreeStore',{
	extend: 'Ext.data.TreeStore',
	storeId: 'Foss_baseinfo_role_SeeRoleTreeStore_Id',
	root: {
		// 根的文本信息
		text:baseinfo.role.i18n('foss.baseinfo.appSystem'),
		// 设置根节点的ID
		id : '0',
		// 根节点是否展开
		expanded: true
	},
		// 设置一个代理，通过读取内存数据
	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryResourceByParentResContain.action"),
		reader: {
           type: 'json',
           root: 'nodes'
		}
	},
	// 设置action层接收父结点的标识：
	// 改为 listeners : { beforeload
	// nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'resourceVo.resourceEntityDetail.parentRes.code':operation.params.node,
					// 当前操作的角色编码
					'resourceVo.currRoleCode': baseinfo.role.currRoleCode
				}
			});	
			
		}
	}
});
//定义GUI treeStore
Ext.define('Foss.baseinfo.role.SeeGuiRoleTreeStore',{
	extend: 'Ext.data.TreeStore',
	storeId: 'Foss_baseinfo_role_SeeGuiRoleTreeStore_Id',
	root: {
		// 根的文本信息
		text:'GUI',
		// 设置根节点的ID
		id : 'GUI',
		// 根节点是否展开
		expanded: true
	},
		// 设置一个代理，通过读取内存数据
	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryResourceByParentResContain.action"),
		reader: {
	        type: 'json',
	        root: 'nodes'
		}
	},
	// 设置action层接收父结点的标识：
	// 改为 listeners : { beforeload
	// nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'resourceVo.resourceEntityDetail.parentRes.code':operation.params.node,
					// 当前操作的角色编码
					'resourceVo.currRoleCode': baseinfo.role.currRoleCode
				}
			});	
			
		}
	}
});

// 定义baseinfo.role.i18n('foss.baseinfo.resource')树结构，
Ext.define('Foss.baseinfo.role.SeeRoleTree',{
	id: 'Foss_baseinfo_role_SeeRoleTree_Id',
	extend:'Ext.tree.Panel',
	autoScroll: true,
	animate: false,
	frame: true,
	rootVisible: true,
	border: true,
	height : 400,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.role.SeeRoleTreeStore');
		me.tbar = [{
			xtype: 'textfield',
			name:'name',
			id: 'Foss_baseinfo_role_SeeRoleTree_QueryText_Id',
			fieldLabel:baseinfo.role.i18n('foss.baseinfo.resourceName'),
			labelWidth: 100
		},{
			text: baseinfo.role.i18n('foss.baseinfo.query'),
			margin:'5 10 5 5',
			// 查询按钮的响应事件：
			handler: function() {
				// 根据权限名称查询树结构
				var a_resourceTree = Ext.getCmp("Foss_baseinfo_role_SeeRoleTree_Id");
				var a_name= Ext.getCmp('Foss_baseinfo_role_SeeRoleTree_QueryText_Id');
				
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				// 设置当前角色编码
				resourceVo.currRoleCode = baseinfo.role.currRoleCode;
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
				
				var r_url = "gainFullPathByNameRole.action";
				r_url=	baseinfo.realPath(r_url) ;
				Ext.Ajax.request({
					// 请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
					url : r_url,
					jsonData:params,
					success : function(response) {    								 
						json = Ext.decode(response.responseText);

						var a_fullPath = json.resourceVo.fullPath;// 查询到的第一个部门的全路径
						if(Ext.isEmpty(a_fullPath)){// 如果没有查到，则展开根结点
							a_resourceTree.getStore().load({'node': a_resourceTree.getRootNode()});	
						}else{
							a_resourceTree.selectPath(a_fullPath,'id','.');// 再按第一个路径展开
						}   
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
}); 


//定义GUI树结构，
Ext.define('Foss.baseinfo.role.SeeGuiRoleTree',{
	id: 'Foss_baseinfo_role_SeeGuiRoleTree_Id',
	extend:'Ext.tree.Panel',
	autoScroll: true,
	animate: false,
	frame: true,
	rootVisible: true,
	border: true,
	height : 400,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.role.SeeGuiRoleTreeStore');
		me.tbar = [{
			xtype: 'textfield',
			name:'name',
			id: 'Foss_baseinfo_role_SeeGuiRoleTree_QueryText_Id',
			fieldLabel:('GUI'+baseinfo.role.i18n('foss.baseinfo.resourceName')),
			labelWidth: 100
		},{
			text: '查询',
			margin:'5 10 5 5',
			// 查询按钮的响应事件：
			handler: function() {
				// 根据权限名称查询树结构
				var a_resourceTree = Ext.getCmp("Foss_baseinfo_role_SeeGuiRoleTree_Id");
				var a_name= Ext.getCmp('Foss_baseinfo_role_SeeGuiRoleTree_QueryText_Id');
				
				var resourceVo = new Object();
				resourceVo.resourceEntityDetail = new Object();
				resourceVo.resourceEntityDetail.name = a_name.getValue();
				// 设置当前角色编码
				resourceVo.currRoleCode = baseinfo.role.currRoleCode;
				// 将角色名称发送到后台：
				var params = {'resourceVo':resourceVo};
				
				var r_url = "gainFullPathByNameRole.action";
				r_url=	baseinfo.realPath(r_url) ;
				Ext.Ajax.request({
					// 请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
					url : r_url,
					jsonData:params,
					success : function(response) {    								 
						json = Ext.decode(response.responseText);

						var a_fullPath = json.resourceVo.fullPath;// 查询到的第一个部门的全路径
						if(Ext.isEmpty(a_fullPath)){// 如果没有查到，则展开根结点
							a_resourceTree.getStore().load({'node': a_resourceTree.getRootNode()});	
						}else{
							a_resourceTree.selectPath(a_fullPath,'id','.');// 再按第一个路径展开
						}   
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
}); 



////////////////////////////////////////////////////////////////////////
//角色查看界面 start
////////////////////////////////////////////////////////////////////////

//角色权限TAB
Ext.define('Foss.baseinfo.role.SeeRoleTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	columnHeight: 'autoHeight', 
	webRoleTreeItem:null,
	getWebRoleTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.webRoleTreeItem)){
			me.webRoleTreeItem = Ext.create('Foss.baseinfo.role.SeeRoleTree');
		}
		return me.webRoleTreeItem;
	},
	guiRoleTreeItem:null,
	getGuiRoleTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.guiRoleTreeItem)){
			me.guiRoleTreeItem= Ext.create('Foss.baseinfo.role.SeeGuiRoleTree')
		}
		return me.guiRoleTreeItem;
	},
	listeners: {
		tabchange : function(tabPanel,newCard,oldCard,eOpts ){
			/*if(baseinfo.resource.SEARCH_TYPE_GUI == newCard.getItemId()){
				baseinfo.resource.search(null);
			}*/
		 }
	}, 
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [ 
				    {
						title : 'WEB角色权限',
						itemId:'WEB',
						tabConfig : {
							width : 120
							},
						items : [
								 me.getWebRoleTreeItem()
						       ]
					},
					{
						title : 'GUI角色权限',
						itemId:'GUI',
						tabConfig : {
							width : 120
							},
						items : [
						         me.getGuiRoleTreeItem()
							   	]
					}
				 ]
	    me.callParent([cfg]);
	}
});


//角色权限TAB
Ext.define('Foss.baseinfo.role.RoleTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	columnHeight: 'autoHeight', 
	webRoleTreeItem:null,
	getWebRoleTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.webRoleTreeItem)){
			me.webRoleTreeItem = Ext.create('Foss.baseinfo.role.RoleTree');
		}
		return me.webRoleTreeItem;
	},
	guiRoleTreeItem:null,
	getGuiRoleTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.guiRoleTreeItem)){
			me.guiRoleTreeItem= Ext.create('Foss.baseinfo.role.GuiRoleTree')
		}
		return me.guiRoleTreeItem;
	},
	listeners: {
		tabchange : function(tabPanel,newCard,oldCard,eOpts ){
			/*if(baseinfo.resource.SEARCH_TYPE_GUI == newCard.getItemId()){
				baseinfo.resource.search(null);
			}*/
		 }
	}, 
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [ 
				    {
						title : 'WEB角色权限',
						itemId:'WEB',
						tabConfig : {
							width : 120
							},
						items : [
								 me.getWebRoleTreeItem()
						       ]
					},
					{
						title : 'GUI角色权限',
						itemId:'GUI',
						tabConfig : {
							width : 120
							},
						items : [
						         me.getGuiRoleTreeItem()
							   	]
					}
				 ]
	    me.callParent([cfg]);
	}
});

//定义角色查看窗口
Ext.define('Foss.baseinfo.role.SeeRoleWindow',{
	extend: 'Ext.window.Window',
	title: baseinfo.role.i18n('foss.baseinfo.seeRoleResource'),
	width: 700,
	height: 650,
	modal: true,
	closeAction:'hide',
	seeRoleDetailPanel: null,
	// 角色详情
	getQueryRoleDetailForm: function(){
		if(this.seeRoleDetailPanel==null){
			this.seeRoleDetailPanel = Ext.create("Foss.baseinfo.role.QueryRoleDetailForm");
		}
		return this.seeRoleDetailPanel;
	},
	seeRoleTab: null,
	getSeeRoleTab: function(){
		if(this.seeRoleTab==null){
			this.seeRoleTab = Ext.create("Foss.baseinfo.role.SeeRoleTab");
		}
		return this.seeRoleTab;
	},
	// 定义获取按钮的一些方法：
	cancelButton : null,
	getCancelButton:function(){
		if(this.cancelButton==null){
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: baseinfo.role.i18n('foss.baseinfo.close'),
				handler: function(){
       		this.up('window').hide();
       	}
			});
		}
		return this.cancelButton;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.buttons = [
		  	'->',me.getCancelButton()
		  ];
		me.items = [
			me.getQueryRoleDetailForm(),
			me.getSeeRoleTab()
		];
		me.callParent([cfg]);
	}
});
// //////////////////////////////////////////////////////////////////////
// 角色查看界面 end
// //////////////////////////////////////////////////////////////////////

/**
 * 程序启动的初始化方法：
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-roleindex_content')) {
		return;
	};
	// 角色查询 主界面
	Ext.getCmp('T_baseinfo-roleindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-roleindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		items: [
			Ext.create('Foss.baseinfo.role.QueryRoleConditionForm'),
			Ext.create('Foss.baseinfo.role.QueryRoleResultGrid')
		] 
	}));
});



