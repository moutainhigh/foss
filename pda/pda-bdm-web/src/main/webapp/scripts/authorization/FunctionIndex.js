/**
 * 定义树的右键菜单
 */
Ext.define('Dpap.Authorization.FunctionTreeRightMenu', {
	extend : 'Ext.menu.Menu',
	frame : true,
	width :  100,
	items : [ {
		text : authorization.functions.i18n('dpap.authorization.refresh'),
		handler : function() {
			var functionTree = Ext.getCmp('T_authorization-functionsIndex_content').getFunctionTree();
				record = functionTree.getSelectionModel().getSelection()[0];
			if (record.raw.entity != null) {
				var functionCode = record.raw.entity.functionCode;
				authorization.onRefreshTreeNodes(functionTree.getStore(),functionCode);
			}
		}
	} ],
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 定义功能树的store
 */
Ext.define('Dpap.Authorization.FunctionTreeStore', {
	extend : 'Ext.data.TreeStore',
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : '../authorization/loadManagerFunctionTree.action'
	},
	root : {
		id : '0',
		text : authorization.functions.i18n('dpap.authorization.Function.app'),
		expanded : true
	}
});

/**
 * 定义功能树的面板
 */
Ext.define('Dpap.Authorization.FunctionTreePanel',{
	extend  : 'Ext.tree.Panel',
	useArrows : true,
	frame: true,
	title:authorization.functions.i18n('dpap.authorization.functionTree'),
	layoutConfig : {
		// 展开折叠是否有动画效果
		animate : true
	},
	menu : null,
	getMenu : function(){
		if(this.menu==null){
			this.menu = Ext.create('Dpap.Authorization.FunctionTreeRightMenu'); 
		}
		return this.menu;
	},
	/**
	 * 刷新树的节点
	 */
	reFresh : function() {
		var selects = this.getSelectionModel().getSelection();
		if(selects.length==0){
			return;
		}
		var record = selects[0];
		if(record.raw.entity != null){
			var functionCode = record.raw.entity.functionCode;
			authorization.onRefreshTreeNodes(this.getStore(),functionCode);
		}
	},
	/**
	 * 树的节点左键单击事件
	 * 
	 * @param {}
	 *            view
	 * @param {}
	 *            _node 节点
	 */
	treeLeftKeyAction : function(node, record, item, index, e) {
		e.preventDefault();// 阻止浏览器默认行为处理事件
		var content = Ext.getCmp('T_authorization-functionsIndex_content');
			functionForm = content.getFunctionForm(),
			formDisableButton = functionForm.getDisableButton(),
			formEnableButton = functionForm.getEnableButton(),
			pageElementGrid = content.getPageElementGrid(),
			gridDisableButton = pageElementGrid.getDisableButton(),
			gridEnableButton = pageElementGrid.getEnableButton();
		if(record.data.root==true){
			formEnableButton.setDisabled(true);
			formDisableButton.setDisabled(true);
			functionForm.getForm().reset();
			return;
		}
		functionForm.loadFormDataFromTree(record);
		if (record.raw.entity.validFlag == true) {
			formEnableButton.setDisabled(true);
			formDisableButton.setDisabled(false);
		} else {
			formDisableButton.setDisabled(true);
			formEnableButton.setDisabled(false);
		}
		gridEnableButton.setDisabled(true);
		gridDisableButton.setDisabled(true);
		// 只有是模块功能的时候,才进行模块功能下的页面元素功能项的加载
		if (record.raw.entity.functionType == 3) {
			pageElementGrid.getStore().load({
				params : {
					'parentCode.functionCode' : record.raw.entity.functionCode
				}
			});
		}else{
			pageElementGrid.getStore().removeAll();
		}
	},

	/**
	 * 树节点的右键点击事件
	 * 
	 * @param node
	 * @param record
	 * @param item
	 * @param index
	 * @param e
	 */
	treeRightKeyAction : function(node, record, item, index, e) {
		e.preventDefault();// 阻止浏览器默认行为处理事件
		this.getMenu().showAt(e.getXY());
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.FunctionTreeStore');
		// 监听事件
		me.listeners = {
			itemclick : me.treeLeftKeyAction,
			itemcontextmenu : me.treeRightKeyAction
		};
		me.tbar = [{
			text : authorization.functions.i18n('dpap.authorization.expendAll'),//"全部展开",
			handler : function(){
				me.collapseAll();
				me.expandAll();
			},
			scope : this
		},'-', {
			text : authorization.functions.i18n('dpap.authorization.closeAll'),//"全部折叠",
			handler : me.collapseAll,
			scope : this
		} ,'-', {
			text : authorization.functions.i18n('dpap.authorization.refresh'),//"全部折叠",
			handler : me.reFresh,
			scope : this
		}];
		me.callParent([cfg]);
	}
});

/**
 * 定义功能表单
 */
Ext.define('Dpap.Authorization.FunctionForm', {
	extend : 'Ext.form.Panel',
	title: authorization.functions.i18n('dpap.authorization.Function.info'),
    frame:true,
    defaultType: 'textfield',
    defaults: {
    	columnWidth:.5,
    	margin:'5 10 5 10',
    	readOnly: true
    },
	layout:'column',
	items: [{
			name: 'functionName',
	        fieldLabel: authorization.functions.i18n('dpap.authorization.Function.functionName')
		},{
			name: 'uri',
	        fieldLabel: authorization.functions.i18n('dpap.authorization.Function.URI')
		},{
	        name: 'parentName',
	        fieldLabel: authorization.functions.i18n('dpap.authorization.Function.parentName')
		},{
			name: 'validFlag',
			fieldLabel: authorization.functions.i18n('dpap.authorization.Function.validFlag'),
			xtype:'combo',
		    store: Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					    data : [
					        {'name':authorization.functions.i18n('dpap.authorization.yes'),  'value':true},
					        {'name':authorization.functions.i18n('dpap.authorization.no'),  'value':false}
					    ]
					}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'value'
		},{
			name: 'check',
			fieldLabel: authorization.functions.i18n('dpap.authorization.Function.check'),
			xtype:'combo',
		    store: Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					    data : [
					        {'name':authorization.functions.i18n('dpap.authorization.yes'),  'value':true},
					        {'name':authorization.functions.i18n('dpap.authorization.no'),  'value':false}
					    ]
					}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'value'
		},{
			name: 'functionType',
			fieldLabel: authorization.functions.i18n('dpap.authorization.Function.functionType'),
			xtype:'combo',
		    store: Ext.create('Ext.data.Store', {
					    fields: ['name', 'value'],
					    data : [
					        {'name':authorization.functions.i18n('dpap.authorization.Function.functionTypeApp'), 'value':1},
					        {'name':authorization.functions.i18n('dpap.authorization.Function.functionTypeModule'), 'value':2},
					        {'name':authorization.functions.i18n('dpap.authorization.Function.functionTypeFunction'), 'value':3}
					    ]
					}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'value'
		},{
			name: 'functionDesc',
			columnWidth:1,
	        fieldLabel: authorization.functions.i18n('dpap.authorization.Function.functionDesc'),
	        xtype : 'textarea'
	}],
	disableButton : null,
	getDisableButton : function(){
		var me = this;
		if(me.disableButton == null){
			me.disableButton = Ext.create('Ext.Button', {
			    disabled:true,
				text : authorization.functions.i18n('dpap.authorization.disable'),
				hidden:!authorization.functions.isPermission('../authorization/updateFunction.action'),
				handler : me.disableFunction
			});
		}
		return this.disableButton;
	},
	/**
	 * 点击按钮，禁用权限
	 */
	disableFunction : function() {
		var flag=false;
		var functionForm = this.up('form');
		var form = functionForm.getForm();
		form.findField("validFlag").setValue(false);
		functionForm.disAndEnable(flag);
	},
	enableButton : null,
	getEnableButton : function(){
		var me = this;
		if(me.enableButton == null){
			me.enableButton = Ext.create('Ext.Button', {
				disabled:true,
				text : authorization.functions.i18n('dpap.authorization.enable'),
				hidden : !authorization.functions.isPermission('../authorization/updateFunction.action'),
				handler : me.ennableFunction
			});
		}
		return this.enableButton;
	},
	/**
	 * 点击按钮，启用权限
	 */
	ennableFunction : function() {
		var flag=true;
		var functionForm = this.up('form');
		var form = functionForm.getForm();
		form.findField("validFlag").setValue(true);
		functionForm.disAndEnable(flag);
	},
	/**
	 *禁用与启用方法
	 */
	disAndEnable : function(flag){
		var me = this,
			form = me.getForm(),
			new_record = form.getRecord();
		form.updateRecord(new_record);
		var record = {
				'id' : new_record.data.id,
				'validFlag' : new_record.data.validFlag
			},
			param = {'function':record},
			content = Ext.getCmp('T_authorization-functionsIndex_content');
			pageElementGrid = content.getPageElementGrid(),
			functionTree = Ext.getCmp('T_authorization-functionsIndex_content').getFunctionTree();
    	Ext.Ajax.request({
		  url : '../authorization/updateValidFlag.action',
		  jsonData:param,
		  success : function(response) {
			  if(new_record.data.validFlag==true){
				  me.getDisableButton().setDisabled(false);
				  me.getEnableButton().setDisabled(true);
			  }else{
				  me.getDisableButton().setDisabled(true);
				  me.getEnableButton().setDisabled(false);
			  }
			  if(new_record.data.functionType==3){
				  pageElementGrid.getStore().load({
						params : {
							'parentCode.functionCode' : new_record.data.functionCode
						}
				  });  
			  }
			  authorization.onRefreshTreeNodes(functionTree.getStore(),new_record.data.parentCode.functionCode);
			  if(flag){
				  Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
						  authorization.functions.i18n('dpap.authorization.enableSuccess'));	  
			  }else{
				  Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
						  authorization.functions.i18n('dpap.authorization.disableSuccess'));
			  }
		   },
		   exception : function(response){
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),json.message);
				form.findField("validFlag").setValue(!new_record.data.validFlag);
			}
    	});
	},
	/**
	 * 从功能树中加载数据到form表单中
	 */
	loadFormDataFromTree : function(record) {
		// 如果父节点为空，那么当前节点就是功能树的根节点，不可以做修改
		if (record.raw.entity.parentCode == null) {
			return;
		}
		var functionModel = Ext.ModelManager.create(record.raw.entity,
				'Dpap.Authorization.FunctionModel');
		this.loadRecord(functionModel);
		if (record.raw.entity.parentCode != null) {
			this.getForm().findField("parentName").setValue(record.raw.entity.parentCode.functionName);
		}
		setFormEditAble(this, false);
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.tbar = [me.getEnableButton(),'-',me.getDisableButton()];
		me.callParent([cfg]);
	}
});

/**
 * FunctionPageElementStore
 */
Ext.define('Dpap.Authorization.FunctionPageElementStore',{
	extend: 'Ext.data.Store',
	model : 'Dpap.Authorization.FunctionModel',
	proxy : {
		url : '../authorization/findFunctionPageElementByParentCode.action',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'functionList'
		}
	}
});

/**
 * 模块功能页面元素的权限信息
 */
Ext.define('Dpap.Authorization.FunctionPageElementGridPanel', {
	extend: 'Ext.grid.Panel',
	title : authorization.functions.i18n('dpap.authorization.Function.pageElement'),
	frame: true,
	sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	selType : "rowmodel", // 选择类型设置为：行选择
	columns : [{xtype: 'rownumberer'},{
		id : 'id',
		hidden : true,
		dataIndex : 'id'
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.pageElementName'),
		width:60,
		dataIndex : 'functionName'
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.validFlag'),
		width:60,
		dataIndex : 'validFlag',
		renderer : function(value) {
			if(value == null){
				return;
			}
		    if (value == true) {
		        return authorization.functions.i18n('dpap.authorization.yes');
		    } else {
		        return authorization.functions.i18n('dpap.authorization.no');
		    }
		}
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.check'),
		width:100,
		dataIndex : 'check',
		renderer :  function(value) {
			if(value == null){
				return;
			}
		    if (value == true) {
		        return authorization.functions.i18n('dpap.authorization.yes');
		    } else {
		        return authorization.functions.i18n('dpap.authorization.no');
		    }
		}
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.displayOrder'),
		width:60,
		dataIndex : 'displayOrder'
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.pageElementURI'),
		width:210,
		dataIndex : 'uri'
			
	},{
		text : authorization.functions.i18n('dpap.authorization.Function.functionDesc'),
		flex: 1,
		dataIndex : 'functionDesc'
	} ],
	disableButton : null,
	getDisableButton : function(){
		var me = this;
		if(me.disableButton == null){
			me.disableButton = Ext.create('Ext.Button', {
				text : authorization.functions.i18n('dpap.authorization.disable'),
				disabled : true,
				hidden:!authorization.functions.isPermission('../authorization/updateFunction.action'),
				handler : function() {
					var flag = false;
					var sm = me.getSelectionModel();
					if (sm.getSelection().length == 0) {
						Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
								authorization.functions.i18n('dpap.authorization.noSelect'));
					} else {
						sm.getSelection()[0].set('validFlag',false);
						me.disAndEnable(sm.getSelection()[0], flag);
					}
		
				}
			});
		}
		return this.disableButton;
	},
	enableButton : null,
	getEnableButton : function(){
		var me = this;
		if(me.enableButton == null){
			me.enableButton = Ext.create('Ext.Button', {
				text : authorization.functions.i18n('dpap.authorization.enable'),
				disabled : true,
				hidden:!authorization.functions.isPermission('../authorization/updateFunction.action'),
				handler : function() {
					var flag = true;
					var sm = me.getSelectionModel();
					if (sm.getSelection().length == 0) {
						Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
								authorization.functions.i18n('dpap.authorization.noSelect'));
					} else {
						sm.getSelection()[0].set('validFlag',true);
						me.disAndEnable(sm.getSelection()[0], flag);
					}
				}
			});
		}
		return this.enableButton;
	},
	/**
	 * 禁用或启用方法
	 */
	disAndEnable : function(record, flag) {
		var me = this;
		var paramFun = {
			'id' : record.data.id,
			'validFlag' : record.data.validFlag
		};
		var param = {
			'function' : paramFun
		};
		Ext.Ajax.request({
			url : '../authorization/updateValidFlag.action',
			jsonData : param,
			success : function(response) {
				me.getStore().load({
					params : {
						'parentCode.functionCode' : record.data.parentCode.functionCode
					}
				});
				me.getDisableButton().setDisabled(true);
				me.getEnableButton().setDisabled(true);
				if(flag){
					  Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
							  authorization.functions.i18n('dpap.authorization.enableSuccess'));	  
				  }else{
					  Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),
							  authorization.functions.i18n('dpap.authorization.disableSuccess'));
				  }
			},
			exception : function(response){
				var json = Ext.decode(response.responseText);
				Ext.MessageBox.alert(authorization.functions.i18n('dpap.authorization.message'),json.message);
				record.set('validFlag',record.raw.validFlag);
			}
		});
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Dpap.Authorization.FunctionPageElementStore');
		me.listeners = {
			itemclick : function() {
				var sm = me.getSelectionModel();
				if (sm.selected.items[0].get('validFlag') == true) {
					me.getEnableButton().setDisabled(true);
					me.getDisableButton().setDisabled(false);
				} else {
					me.getEnableButton().setDisabled(false);
					me.getDisableButton().setDisabled(true);
				}
			}
		};
		me.tbar = [me.getEnableButton(),'-',me.getDisableButton()];
		me.callParent([cfg]);
	}
});

/**
 * @description 权限管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_authorization-functionsIndex_content')) {
		return;
	}
	var functionTree = Ext.create('Dpap.Authorization.FunctionTreePanel',{
		height: 717,
		flex : 3
	});
	var functionForm = Ext.create('Dpap.Authorization.FunctionForm');
	var pageElementGrid = Ext.create('Dpap.Authorization.FunctionPageElementGridPanel');
	Ext.create('Ext.panel.Panel', {
		id : 'T_authorization-functionsIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'hbox',
		getFunctionForm : function() {
			return functionForm;
		},
		getPageElementGrid : function() {
			return pageElementGrid;
		},
		getFunctionTree : function() {
			return functionTree;
		},
		items : [ functionTree, {
			xtype : 'container',
			margin : '0 0 0 15',
			flex : 7,
			items : [ functionForm, pageElementGrid ]
		}],
		renderTo : 'T_authorization-functionsIndex-body'
	});
});