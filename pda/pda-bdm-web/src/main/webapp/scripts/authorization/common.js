/**
 * treePanel的节点异步刷新事件
 * 
 * @param parentId
 *            父节点ID
 */
authorization.onRefreshTreeNodes = function(treeStore,parentId){
	var node = treeStore.getNodeById(parentId); 
	treeStore.load({
		node : node
	});
};

/**
 * 选中角色列表中的一行或多行移动至另一个角色列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name select
 */
authorization.select = function(grid,addStore,removeStroe) {
	var _records = grid.getSelectionModel().getSelection();
	if (_records == null||_records.length<=0) {
		return false;
	}
	Ext.each(_records, function(item) {
		addStore.insert(0,item);
	});
	removeStroe.remove(_records);
	return true;
};

/**
 * 角色列表中的所有记录移动至另一个角色列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name select
 */
authorization.allSelect = function(grid,addStore,removeStore) {
	var count = removeStore.getCount();
	if(count==null||count<=0){
		return false;
	}
	removeStore.each( function(_record) {
		addStore.insert(0,_record);
	},removeStore);
	// 从待选运单列表中移除该记录
	removeStore.removeAll();
	return true;
};

/**
 * function的Model
 */
Ext.define('Dpap.Authorization.FunctionModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'functionCode'
	}, {
		name : 'functionName'
	}, {
		name : 'uri'
	}, {
		type : 'int',
		name : 'functionLevel'
	}, {
		name : 'parentCode'
	}, {
		type : 'boolean',
		name : 'validFlag'
	}, {
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date',
		name : 'invalidDate'
	}, {
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date',
		name : 'validDate'
	}, {
		type : 'int',
		name : 'displayOrder'
	}, {
		type : 'boolean',
		name : 'check'
	}, {
		type : 'int',
		name : 'functionType'
	}, {
		name : 'functionDesc'
	}, {
		name : 'functionSeq'
	}, {
		name : 'createUser'
	}, {
		name : 'createDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	}, {
		name : 'modifyUser'
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	} ]
});
/**
 * role的Model
 */
Ext.define('Dpap.Authorization.RoleModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'roleName'
	}, {
		name : 'roleDesc'
	}, {
		type : 'int',
		name : 'status'
	}, {
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date',
		name : 'invalidDate'
	}, {
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'date',
		name : 'validDate'
	}, {
		name : 'createUser'
	}, {
		name : 'createDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	}, {
		name : 'modifyUser'
	}, {
		name : 'modifyDate',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	} ]
});

//角色store
Ext.define('Dpap.Authorization.RoleStore',{
	extend: 'Ext.data.Store',
	model : 'Dpap.Authorization.RoleModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : '../authorization/findAllRole.action',
		reader : {
			type : 'json',
			root : 'roleList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * treeNode的Model
 */
Ext.define('Dpap.Authorization.TreeNodeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'text'
	}, {
		name : 'parentId'
	}, {
		type : 'boolean',
		name : 'leaf'
	}, {
		name : 'checked'
	}, {
		name : 'entity'
	}]
});

/**
 * user的Model
 */
Ext.define('Dpap.Authorization.UserModel', {
	extend : 'Ext.data.Model',
	fields : [{
			name : 'id'
		},{
			name: 'empCode'
		},{
			name: 'loginName'
		},{
			defaultValue : '123456',
			name: 'password'
		},{
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date',
			name: 'lastLogin'
		},{
			type : 'int',
			name: 'status'
		},{
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date',
			name: 'invalidDate'
		},{
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date',
			name: 'validDate'
		},{
			name :'createUser'
		},{
			name : 'createDate',
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'number'
		},{
			name : 'modifyUser'
		},{
			name : 'modifyDate',
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'number'
		}]
});

/**
 * 定义用户数据加载器
 */
Ext.define('Dpap.Authorization.UserStore', {
	extend : 'Ext.data.Store',
	pageSize : 17,
	model : 'Dpap.Authorization.UserModel',
	autoLoad : true,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : '../authorization/findAllUser.action',
		reader : {
			type : 'json',
			root : 'userList',
			totalProperty : 'totalCount'
		}
	},
});

/**
 * 员工信息MODEL
 */
Ext.define('Dpap.Organization.EmployeeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	}, {
		name : 'deptId',
		type : 'Object'
	}, {
		name : 'empCode',
		type : 'string'
	}, {
		name : 'empName',
		type : 'string'
	}, {
		name : 'gender',
		type : 'boolean'
	}, {
		name : 'position',
		type : 'string'
	}, {
		name : 'birthdate',
		type : 'Date',
		convert : dateConvert,
		defaultValue : new Date()
	}, {
		name : 'status',
		type : 'boolean'
	}, {
		name : 'inDate',
		type : 'Date',
		convert : dateConvert,
		defaultValue : new Date()
	}, {
		name : 'outDate',
		type : 'Date',
		convert : dateConvert,
		defaultValue : new Date()
	}, {
		name : 'offerTel',
		type : 'string'
	}, {
		name : 'offerAddress',
		type : 'string'
	}, {
		name : 'offerZipCode',
		type : 'string'
	}, {
		name : 'offerEmail',
		type : 'string'
	}, {
		name : 'mobileNumber',
		type : 'string'
	}, {
		name : 'homeTel',
		type : 'string'
	}, {
		name : 'homeAddress',
		type : 'string'
	}, {
		name : 'homeZipCode',
		type : 'string'
	}, {
		name : 'personEmail',
		type : 'string'
	}, {
		name : 'workExp',
		type : 'string'
	}, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'createUser',
		type : 'string'
	}, {
		name : 'createDate',
		type : 'Date',
		convert : dateConvert,
		defaultValue : new Date()
	}, {
		name : 'modifyUser',
		type : 'string'
	}, {
		name : 'modofyDate',
		type : 'Date',
		convert : dateConvert,
		defaultValue : new Date()
	}]
});

/**
 * 定义员工信息Store
 */
Ext.define('Dpap.Authorization.EmpStore', {
	extend : 'Ext.data.Store',
	pageSize : 15,
	model : 'Dpap.Organization.EmployeeModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../organization/findAllEmployee.action",
		reader : {
			type : 'json',
			root : 'employeeList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * department的Model
 */
Ext.define('Dpap.Organization.DepartmentModel', {
    extend: 'Ext.data.Model',
    fields : [{
			name : 'id'
		},{
			name: 'deptCode'
		},{
			name: 'deptName'
		},{
			name: 'principal'
		},{
			name: 'phone'
		},{
			name: 'fax'
		},{
			name: 'parentCode'
		},{
			name: 'companyName'
		},{
			name: 'zipCode'
		},{
			name: 'address'
		},{
			name: 'zipCode'
		},{
			type : 'boolean',
			name: 'status'
		},{
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date',
			name: 'validDate'
		},{
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date',
			name: 'invalidDate'
		},{
			type: 'int',
			name: 'displayOrder'
		},{
			type: 'int',
			name: 'deptLevel'
		},{
			type : 'boolean',
			name: 'leaf'
		},{
			name: 'deptDesc'
		},{
			name: 'deptSeq'
		},{
			name : 'createUser'
		},{
			name : 'createDate',
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date'
		},{
			name:'modifyUser'
		},{
			name : 'modifyDate',
			defaultValue : new Date(),
			convert : dateConvert,
			type : 'date'
		}]
});

/**
 * 定义部门的store
 */
Ext.define('Dpap.Authorization.DepartmentStore', {
	extend : 'Ext.data.Store',
	model : 'Dpap.Organization.DepartmentModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : '../organization/findAllDepartment.action',
		reader : {
			type : 'json',
			root : 'departments',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 定义组织架构树的store
 */
Ext.define('Dpap.Authorization.DepartmentTreeStore', {
	extend : 'Ext.data.TreeStore',
	proxy : {
		type : 'ajax',
		url : '../organization/loadTree.action'
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			if(authorization.seqAll==null){
				return;
			}
			Ext.apply(operation.params, {
				'seqAll' : authorization.seqAll
			});
		}
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 树的面板
 * @param root
 * @param deptNameQuery
 * @param queryName
 */
Ext.define('Dpap.Authorization.DepartmentTreePanle',{
	extend  : 'Ext.tree.Panel',
	frame : true,
	titleCollapse : true,
	useArrows : true,
	animate : true,
	deptNameQueryField : null,
	getDeptNameQueryField : function(deptNameQuery){
		if(this.deptNameQueryField==null){
			this.deptNameQueryField = Ext.create('Ext.form.field.Text',{
				xtype : 'textfield',
				fieldLabel : deptNameQuery,
				labelWidth : 80,
				name : 'deptNameQuery',
				width : 200
			});
		}
		return this.deptNameQueryField;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.DepartmentTreeStore',{
			root : config.root
		});
		me.tbar = [ me.getDeptNameQueryField(config.deptNameQuery),'-',{
			text : config.queryName,
			handler : function() {
				Ext.Ajax.request({
					url : "../organization/querySeq.action",
					jsonData : {'deptName' :me.getDeptNameQueryField().getValue()},
					success : function(response) {
						json = Ext.decode(response.responseText);
						authorization.seqAll = json.seqAll;
						me.store.load();
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});
