/**
 * 员工信息MODEL
 */
Ext.define('Dpap.Organization.Employee', {
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
 * 员工信息查询form表单
 */
Ext.define('Dpap.Organization.EmployeeForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 470,
	width : 690,
	defaults : {
		columnWidth : .5,
		margin : '5 10 5 10',
		lableWidth: 60,
		readOnly : true
	},
	layout : 'column',
	items : [ {
		fieldLabel : organization.i18n('dpap.organization.Department.id'),
		name : 'id',
		hidden : true
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.EMPCODE'),
		name : 'empCode',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.EMPNAME'),
		name : 'empName',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.GENDER'),
		name : 'gender',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.deptId'),
		name : 'deptId',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.POSITION'),
		name : 'position',
		xtype : 'textfield'
	}, {
		xtype : 'datefield',
		name : 'birthdate',
		fieldLabel : organization.i18n('dpap.organization.Employee.DATE'),
		format : 'Y-m-d'
	}, {
		name : 'status',
		fieldLabel : organization.i18n('dpap.organization.Employee.STATUS'),
		xtype : 'textfield'
	}, {
		xtype : 'datefield',
		name : 'inDate',
		fieldLabel : organization.i18n('dpap.organization.Employee.INDATE'),
		format : 'Y-m-d'
	}, {
		xtype : 'datefield',
		name : 'outDate',
		fieldLabel : organization.i18n('dpap.organization.Employee.OUTDATE'),
		format : 'Y-m-d'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.OTEL'),
		name : 'offerTel',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.OADDRESS'),
		name : 'offerAddress',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.OZIPCODE'),
		name : 'offerZipCode',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.OEMAIL'),
		name : 'offerEmail',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.MOBILENO'),
		name : 'mobileNumber',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.HTEL'),
		name : 'homeTel',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.HADDRESS'),
		name : 'homeAddress',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.HZIPCODE'),
		name : 'homeZipCode',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.PEMAIL'),
		name : 'personEmail',
		xtype : 'textfield'
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.WORKEXP'),
		name : 'workExp',
		xtype : 'textarea',
		columnWidth : 1
	}, {
		fieldLabel : organization.i18n('dpap.organization.Employee.REMARK'),
		name : 'remark',
		xtype : 'textarea',
		columnWidth : 1
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : organization.i18n('dpap.organization.close'),
		handler : function() {
			this.up('window').hide();
		}
	} ]
});
/**
 * 员工信息查询窗口
 */
Ext.define('Dpap.Organization.EmployeeWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	title : organization.i18n('dpap.organization.employeeMsg'),
	resizable : false,
	height : 550,
	width : 735,
	employeeForm : null,
	getEmployeeForm : function() {
		if (this.employeeForm == null) {
			this.employeeForm = Ext.create('Dpap.Organization.EmployeeForm');
		}
		return this.employeeForm;
	},
	modal : true,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getEmployeeForm() ],
		// 监听事件
		me.listeners = {
			beforehide : function(win, Opts) {
				me.getEmployeeForm().getForm().reset();
			}
		}, me.callParent([ cfg ]);
	}
});

/**
 * 定义员工信息Store
 */
Ext.define('Dpap.Organization.EmpStore', {
	extend : 'Ext.data.Store',
	pageSize : 15,
	model : 'Dpap.Organization.Employee',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../organization/findAllEmployee.action",
		reader : {
			type : 'json',
			root : 'employeeList',
			totalProperty : 'totalCount'
		}
	},
	listeners : {
		// 在pageStore加载前，向其传参
		beforeload : function(store, operation, eOpts) {
			var deptTree = Ext.getCmp('T_organization-index_content').getDeptTree(), 
				queryForm = Ext.getCmp('T_organization-index_content').getQueryForm();
			if (deptTree != null) {
				var sm = deptTree.getSelectionModel();
				if (sm.getSelection().length > 0) {
					var dept = sm.getSelection()[0];
					if (dept != null && dept.data.id != '103') {
						Ext.apply(operation, {
							params : {
								'employee.deptId.id' : dept.raw.entity.id
							}
						});
					}
				}
			}
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'deptName' : queryParams.deptName,
						'employee.empCode' : queryParams.empCode,
						'employee.empName' : queryParams.empName,
						'employee.mobileNumber' : queryParams.mobileNumber,
						'employee.position' :queryParams.position
					}
				});	
			}
		}
	}
});
/**
 * 员工信息表
 */
Ext.define('Dpap.Organization.EmpGrid', {
	extend : 'Ext.grid.Panel',
	title : organization.i18n('dpap.organization.girdEmployee'),
	frame : true,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	columnLines : true,
	columns : [{xtype: 'rownumberer'}, {
		header : organization.i18n('dpap.organization.Employee.EMPCODE'),
		flex: 1,
		dataIndex : 'empCode'
	}, {
		header : organization.i18n('dpap.organization.Department.deptName'),
		dataIndex : 'deptId',
		flex: 1,
		renderer: function(value, metadata, record){
	        if (value != null) {
				metadata.tdAttr = 'data-qtip="' + value.deptName + '"'; 
	            return value.deptName;
	        }
	        return null;
	    }
	}, {
		header : organization.i18n('dpap.organization.Employee.EMPNAME'),
		flex: 1,
		dataIndex : 'empName'
	}, {
		header : organization.i18n('dpap.organization.Employee.GENDER'),
		dataIndex : 'gender',
		flex: 1,
		renderer: function(value){
			if(value==true){
			   return organization.i18n('dpap.organization.male');
			}
			if(value==false){
			   return organization.i18n('dpap.organization.fmale');
			}
	    }
	}, {
		header : organization.i18n('dpap.organization.Employee.MOBILENO'),
		flex: 1,
		dataIndex : 'mobileNumber'
	}, {
		header : organization.i18n('dpap.organization.Employee.OTEL'),
		flex: 1,
		dataIndex : 'offerTel'
	}, {
		header : organization.i18n('dpap.organization.Employee.POSITION'),
		flex: 1,
		dataIndex : 'position'
	} ],
	tbar : [ { // 查看员工所有信息按钮
		xtype : 'button',
		text : organization.i18n('dpap.organization.viewAll'),
		handler : function() {
			var empGrid = this.up('grid'),
				sm = empGrid.getSelectionModel();
			if (sm.getSelection().length > 0) {
				var record = sm.getSelection()[0];
				empGrid.bindFormDataFromGrid(record);
				empGrid.getEmpWindow().show();
			} else {
				Ext.Msg.alert(organization.i18n('dpap.organization.message'), 
								organization.i18n('dpap.organization.notSelectGird'));
			}
		}
	} ],
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
	empWindow : null,
	getEmpWindow : function(){
		if(this.empWindow==null){
			this.empWindow = Ext.create('Dpap.Organization.EmployeeWindow');
		}
		return this.empWindow;
	},
	bindFormDataFromGrid : function(record){
		var empForm = this.getEmpWindow().getEmployeeForm();
		empForm.getForm().loadRecord(record);
		empForm.getForm().findField('deptId').setValue(record.get('deptId').deptName);
		empForm.getForm().findField('gender').setValue(
			record.get('gender')
				?organization.i18n('dpap.organization.male')
				:organization.i18n('dpap.organization.fmale')
		);
		empForm.getForm().findField('status').setValue(
			record.get('status')
				?organization.i18n('dpap.organization.onJob')
				:organization.i18n('dpap.organization.outJob')
		);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Organization.EmpStore');
		me.bbar = me.getPagingToolbar();
		me.listeners = {
			itemdblclick : function() {
				var sm = this.getSelectionModel();
				if (sm.getSelection().length > 0) {
					var record = sm.getSelection()[0];
					me.bindFormDataFromGrid(record);
					me.getEmpWindow().show();
				} else {
					Ext.Msg.alert(organization.i18n('dpap.organization.message'), 
							organization.i18n('dpap.organization.notSelectGird'));
				}
			}
		};
		me.callParent([cfg]);
	}
});

/*------------------------------------树的相关功能级元素-----------------------------------------------------------*/
/**
 * 定义组织架构树的store
 */
Ext.define('Dpap.Organization.DepartmentTreeStore', {
	extend : 'Ext.data.TreeStore',
	proxy : {
		type : 'ajax',
		url : '../organization/loadTree.action'
	},
	root : {
		// 把id为0改成103 得到OA组织架构树
		id : '103',
		text : organization.i18n('dpap.organization.rootAndCompanyName'),
		expanded : true
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			if(organization.seqAll==null){
				return;
			}
			Ext.apply(operation.params, {
				'seqAll' : organization.seqAll
			});
		}
	}
});

/**
 * 部门信息查询form表单
 */
Ext.define('Dpap.Organization.DepartmentForm',{
	extend : 'Ext.form.Panel',
	layout : 'column',
	frame : true,
	defaults : {
		columnWidth : .5,
		margin : '5 10 5 10',
		lableWidth: 60,
		readOnly : true
	},
	items : [{
		fieldLabel : organization.i18n('dpap.organization.Department.id'),
		name : 'id',
		hidden : true
	},{
		name : 'deptCode',
		fieldLabel : organization.i18n('dpap.organization.Department.deptCode'),
		xtype : 'textfield'
	},{
		name : 'deptName',
		fieldLabel : organization.i18n('dpap.organization.Department.deptName'),
		xtype : 'textfield'
	},{
		xtype : 'textfield',
		fieldLabel : organization.i18n('dpap.organization.Department.principal'),
		name : 'principal'
	},{
		name : 'phone',
		fieldLabel : organization.i18n('dpap.organization.Department.phone'),
		xtype : 'textfield'
	},{
		name : 'fax',
		fieldLabel : organization.i18n('dpap.organization.Department.fax'),
		xtype : 'textfield'
	},{
		xtype : 'textfield',
		fieldLabel : organization.i18n('dpap.organization.Department.parentName'),
		name : 'parentDeptName'
	},{
		xtype : 'textfield',
		fieldLabel : organization.i18n('dpap.organization.Department.companyName'),
		name : 'companyName'
	},{
		name : 'zipCode',
		fieldLabel : organization.i18n('dpap.organization.Department.zipCode'),
		xtype : 'textfield'
	},{
		name : 'address',
		fieldLabel : organization.i18n('dpap.organization.Department.address'),
		xtype : 'textfield',
		width : window.screen.availWidth * 0.2344,
	},{
		name : 'status',
		fieldLabel : organization.i18n('dpap.organization.Department.status'),
		xtype : 'textfield'
	},{
		name : 'validDate',
		fieldLabel : organization.i18n('dpap.organization.Department.validDate'),
		xtype : 'datefield',
		format : 'Y-m-d'
	},{
		name : 'invalidDate',
		fieldLabel : organization.i18n('dpap.organization.Department.invalidDate'),
		xtype : 'datefield',
		format : 'Y-m-d'
	},{
		name : 'displayOrder',
		fieldLabel : organization.i18n('dpap.organization.Department.displayOrder'),
		xtype : 'textfield',
		hidden : true
	},{
		name : 'deptDesc',
		fieldLabel : organization.i18n('dpap.organization.Department.deptDesc'),
		xtype : 'textarea',
		columnWidth : .94
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : organization.i18n('dpap.organization.close'),
		handler: function(){
			this.up('window').hide();
		}
	}]
});

/**
 * 部门信息查询窗口
 */
Ext.define('Dpap.Organization.DepartmentWindow',{
	extend : 'Ext.window.Window',
	title : organization.i18n('dpap.organization.deptMsg'),
	modal : true,
	closeAction : 'hide',
	height : 400,
	width : 700,
	departmentForm : null,
	getDepartmentForm : function() {
		if (this.departmentForm == null) {
			this.departmentForm = Ext.create('Dpap.Organization.DepartmentForm');
		}
		return this.departmentForm;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getDepartmentForm() ],
		// 监听事件
		me.listeners = {
				beforehide : function(win, Opts){
					me.getDepartmentForm().getForm().reset();
				}
		};
		me.callParent([cfg]);
	}
});

/**
 * 树的面板
 */
Ext.define('Dpap.Organization.DepartmentTreePanle',{
	extend  : 'Ext.tree.Panel',
	title : organization.i18n('dpap.organization.deptTree'),
	frame : true,
	titleCollapse : true,
	useArrows : true,
	animate : true,
	deptWindow : null,
	getDeptWindow : function() {
		if (this.deptWindow == null) {
			this.deptWindow = Ext
					.create('Dpap.Organization.DepartmentWindow');
		}
		return this.deptWindow;
	},
	treeLeftKeyAction : function(node, record, item, index, e) {
		if (record.data.id != '103') {
			Ext.getCmp('T_organization-index_content').getQueryForm()
				.getForm().findField("deptName")
				.setValue(record.raw.entity.deptName);
			Ext.getCmp('T_organization-index_content').getEmpGrid().getPagingToolbar().moveFirst();
		}
	},
	treeDbLeftKeyAction : function(node, record, item, index, e) {
		if (record.data.id != 'root') {
			this.bindFormDataFromTree(record);
			this.getDeptWindow().show();
		}
	},
	changeStatusDeptToString : function(value){
		if(value==true){
		   return value = organization.i18n('dpap.organization.work');
		}else if(value==false){
		   return value = organization.i18n('dpap.organization.unWork');
		}
	},
	bindFormDataFromTree : function(record) {
		var deptForm = this.getDeptWindow().getDepartmentForm();
		if (record.data.id != 'root') {
			record.raw.entity.invalidDate = dateConvert(record.raw.entity.invalidDate);
			record.raw.entity.validDate = dateConvert(record.raw.entity.validDate);
			record.raw.entity.status = this.changeStatusDeptToString(record.raw.entity.status);
			deptForm.getForm().setValues(record.raw.entity);
			var _parentName = record.raw.entity.parentCode.deptName;
			if (_parentName == null) {
				_parentName = organization.i18n('dpap.organization.rootAndCompanyName');
			}
			deptForm.getForm().findField("parentDeptName").setValue(_parentName);
		}
	},
	deptNameQueryField : null,
	getDeptNameQueryField : function(){
		if(this.deptNameQueryField==null){
			this.deptNameQueryField = Ext.create('Ext.form.field.Text',{
				xtype : 'textfield',
				fieldLabel : organization.i18n('dpap.organization.name'),
				labelPad : 2,
				labelWidth : 40,
				name : 'deptNameQuery',
				width : 150
			});
		}
		return this.deptNameQueryField;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Organization.DepartmentTreeStore');
		// 监听事件
		me.listeners = {
			itemclick : me.treeLeftKeyAction,
			itemdblclick : me.treeDbLeftKeyAction
		};
		me.tbar = [ me.getDeptNameQueryField(),{
			xtype : 'label',
			width : 3
		},{
			text : organization.i18n('dpap.organization.find'),
			handler : function() {
				var deptTree = Ext.getCmp('T_organization-index_content').getDeptTree();
				Ext.Ajax.request({
					url : "../organization/querySeq.action",
					jsonData : {'deptName' :me.getDeptNameQueryField().getValue()},
					success : function(response) {
						json = Ext.decode(response.responseText);
						organization.seqAll = json.seqAll;
						deptTree.store.load();
					}
				});
			}
		},{
			text : organization.i18n('dpap.organization.viewDeptAll'),
			handler : function() {
				var deptTree = Ext.getCmp('T_organization-index_content').getDeptTree(),
					sm = deptTree.getSelectionModel();
				if (sm.getSelection().length > 0) {
					var dept = sm.getSelection()[0];
					if (dept != null&& dept.data.id != 'root') {
						deptTree.bindFormDataFromTree(dept);
						deptTree.getDeptWindow().show();
					} else {
						Ext.Msg.alert(organization.i18n('dpap.organization.message'),
								organization.i18n('dpap.organization.notSelectTree'));
					}
				}
			}
		} ];
		me.callParent([cfg]);
	}
});

/**
 * 查询表单
 */
Ext.define('Dpap.Organization.queryForm', {
	extend  : 'Ext.form.Panel',
	title : organization.i18n('dpap.organization.findMsg'),
	frame : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		columnWidth : .5,
		margin : '5 10 5 10'
	},
	items : [{
		fieldLabel : organization.i18n('dpap.organization.Department.deptName'),
		name : 'deptName',
		xtype : 'textfield'
	},{
		fieldLabel : organization.i18n('dpap.organization.Employee.EMPCODE'),
		name : 'empCode',
		xtype : 'textfield'
	},{
		fieldLabel : organization.i18n('dpap.organization.Employee.EMPNAME'),
		name : 'empName',
		xtype : 'textfield'
	},{
		fieldLabel : organization.i18n('dpap.organization.Employee.POSITION'),
		name : 'position',
		xtype : 'textfield'
	},{
		fieldLabel : organization.i18n('dpap.organization.Employee.MOBILENO'),
		name : 'mobileNumber',
		xtype: 'textfield'  
	},{
		border : true,
		xtype : 'container',
		columnWidth : 1,
		defaultType : 'button',
		layout :'column',
		items:[{
			columnWidth : .1,
			xtype : 'button',
			width : 70,
			text : organization.i18n('dpap.organization.reset'),
			handler : function() {
				this.up('form').getForm().reset();
			}
		},{
			xtype : 'container',
			html :'&nbsp;',
			columnWidth:.8
		},{
			columnWidth : .1,
			width : 70,
			autoWidth : true,
			xtype : 'button',
			text : organization.i18n('dpap.organization.find'),
			handler : function() {
				Ext.getCmp('T_organization-index_content').getEmpGrid()
					.getPagingToolbar().moveFirst();
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*---------------------------------启动项-------------------------------------------*/
/**
 * 启动加载的页面元素及布局
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_organization-index_content')) {
		return;
	}
	var deptTree = Ext.create('Dpap.Organization.DepartmentTreePanle', {
		height: 717,
		flex : 3
	});
	var queryForm = Ext.create('Dpap.Organization.queryForm');
	var empGrid = Ext.create('Dpap.Organization.EmpGrid');
	Ext.create('Ext.panel.Panel', {
		id : 'T_organization-index_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'hbox',
		getQueryForm : function() {
			return queryForm;
		},
		getEmpGrid : function() {
			return empGrid;
		},
		getDeptTree : function() {
			return deptTree;
		},
		items : [ deptTree, {
			xtype : 'container',
			margin : '0 0 0 15',
			flex : 7,
			items : [ queryForm, empGrid ]
		} ],
		renderTo : 'T_organization-index-body'
	});
});