/**
  * 综合管理 用户部门 JS功能代码：
 */

// 重新加载树结点
baseinfo.userDeptAuthority.reloadTreeNode = function(){
	// 重新加载树结点：
	var a_tree=Ext.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id');
	var a_rootNode= a_tree.getRootNode();
	a_rootNode.set('expanded', false);
	a_rootNode.set('loading', false);
	a_rootNode.set('loaded', false);
	a_rootNode.expand();
	
}

// //////////////////////////////////////////////////////////////////
// 全局变量（模块名userOrgDept） start
// //////////////////////////////////////////////////////////////////
// 选择框
Ext.define('Foss.baseinfo.userDept.SelectFrame', {
			extend : 'Ext.grid.Panel',
			sortableColumns : false,
			enableColumnHide : false,
			enableColumnMove : false,
			columns : [{
						header : '可选产品：',
						dataIndex : 'name',
						titlehidden : true
					}, {
						hidden : true,
						dataIndex : 'code'
					}],
			height : 150,
			width : 150,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.listeners = {
					scrollershow : function(scroller) {
						if (scroller && scroller.scrollEl) {
							scroller.clearManagedListeners();
							scroller.mon(scroller.scrollEl, 'scroll',
									scroller.onElScroll, scroller);
						}
					}
				};
				me.store = Ext.create('Ext.data.Store', {
							fields : ['name', 'code']
						});
				me.callParent([cfg]);
			}
		});
// 已选的
Ext.define('Foss.baseinfo.userDept.Selected', {
			extend : 'Ext.grid.Panel',
			sortableColumns : false,
			enableColumnHide : false,
			enableColumnMove : false,
			columns : [{
						header : '已选产品：',
						dataIndex : 'name',
						titlehidden : true
					},// 可选产品：
					{
						hidden : true,
						dataIndex : 'code'
					}],
			height : 150,
			width : 150,
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.listeners = {
					scrollershow : function(scroller) {
						if (scroller && scroller.scrollEl) {
							scroller.clearManagedListeners();
							scroller.mon(scroller.scrollEl, 'scroll',
									scroller.onElScroll, scroller);
						}
					}
				};
				me.store = Ext.create('Ext.data.Store', {
							fields : ['name', 'code']
						});
				me.callParent([cfg]);
			}
		});
// 按钮panel
Ext.define('Foss.baseinfo.userDept.LeftRightButtonPanel', {
	extend : 'Ext.panel.Panel',
	// height : 150,
	width : 80,
	// frame:true,
	// 左移框
	leftMoveFrame : null,
	// 右移框
	rightMoveFrame : null,

	setLeftMove : function(a_moveFrame) {
		this.leftMoveFrame = a_moveFrame;
	},
	getLeftMove : function() {
		return this.leftMoveFrame;
	},
	setRightMove : function(a_moveFrame) {
		this.rightMoveFrame = a_moveFrame;
	},
	getRightMove : function() {
		return this.rightMoveFrame;
	},
	//获取树的全部节点
	getTreeAllNode : function() {
		var me = this;
		var nodes = new Array();
		var rootNode = me.getLeftMove().getRootNode();
		me.getNode(rootNode, nodes);
		return nodes;
	},
	//把节点中的节点和子节点，依序（递归）添加至数组中
	getNode : function(node, nodes) {
		var me = this;
		nodes.push(node);
		if (node.hasChildNodes) {
			node.eachChild(function(cNode) {
						// nodes.push(cNode);
						me.getNode(cNode, nodes);
					})
				/*		
			var a_nodes =node.childNodes;
			if(a_nodes && a_nodes.length>0){
				for(var i=0,l=a_nodes.length;i<l;i++){
					nodes.push(a_nodes[i]);
				}
				
			}
			*/
		}
		return nodes;
	},
	// 右移全部
	rightMoveAll : function() {
		var me = this;
		var treeNodes = me.getTreeAllNode();
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		rightStore.removeAll();
		Ext.Array.each(treeNodes, function(item, index, allNodes) {
					if (!Ext.isEmpty(item.data.parentId)) {
						rightStore.add(item.raw);
					}
				});
	},
	// 右移
	rightMove : function() {
		var me = this;
		var rightStore = this.getRightMove().getStore();
		var checked = me.getLeftMove().getView().getChecked();
		if (checked.length < 1) {
			return;
		}
		var selections = this.getRightMove().getStore().data.items;
		var isSelected = false;
		Ext.Array.each(checked, function(item, index, allItems) {
					Ext.Array.each(selections, function(gridItem, gridIndex,
									gridItems) {
								if (item.data.id == gridItem.data.id) {
									isSelected = true;
								}
							})
					if (!isSelected) {
						rightStore.add(item.raw);
					}
				})
	},
	// 左移全部
	leftMoveAll : function() {
		var me = this;
		var rightStore = this.getRightMove().getStore();
		var leftStore = this.getLeftMove().getStore();
		rightStore.each(function(record) {
					// var moveRecord = {
					// 'name' : record.get('name'),
					// 'code' : record.get('code')
					// };
					// leftStore.addMembers(record);
				});
		rightStore.removeAll();
	},
	// 左移
	leftMove : function() {
		var me = this;
		var selections = this.getRightMove().getSelectionModel().getSelection();
		if (selections.length < 1) {
			return;
		}
		var treeNodes = me.getTreeAllNode();
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		Ext.Array.each(selections, function(gridItem, girdIndex, allRows) {
					Ext.Array.each(treeNodes, function(treeItem, treeIndex,
									allNodes) {
								if (treeItem.data.id == gridItem.data.id) {
									treeItem.data.checked = false;
									treeItem.updateInfo({
												checked : false
											});
								}
							})
				});
		this.getRightMove().getStore().remove(selections);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = {
			xtype : 'button',
			width : 60,
			disabled : false,
			height : 20,
			margin : '8 0 0 10'
		};
		me.items = [{
					text : '>>',
					margin : '50 0 0 10',
					handler : function() {
						me.rightMoveAll();
					}
				}, {
					text : '>',
					handler : function() {
						me.rightMove();
					}
				}, {
					text : '<',
					handler : function() {
						me.leftMove();
					}
				}, {
					text : '<<',
					handler : function() {
						me.leftMoveAll();
					}
				}]
		me.callParent([cfg]);
	}
});

// //////////////////////////////////////////////////////////////////
// 全局变量（模块名userOrgDept） end
// //////////////////////////////////////////////////////////////////
// Ajax请求--json
baseinfo.requestJsonAjax = function(url, params, successFn, failFn) {
	Ext.Ajax.request({
				url : url,
				timeout: 600000,
				jsonData : params,
				success : function(response) {
					var result = Ext.decode(response.responseText);
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
};
// ==============================================================================================
// 用户部门详情的窗口
// ==============================================================================================
Ext.define('Foss.baseinfo.userDept.UserOrgDeptDetailWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id',
	title : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1164'),
	width : 800,
	height : 600,
	// resizable : false,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'hide',
	layout : 'column',
	getCancelButton : function(me) {
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.cancel'),
				// margin : '0 397 0 0',
				hidden : false,
				handler : function() {
					var a_win = Ext
							.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id');
					a_win.setVisible(false);
				}
			});
		}
		return this.cancelButton;
	},
	getResetButton : function(me) {
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				hidden : false,
				cls : 'yellow_button',
				name : 'reset',
				text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.reset'),
				margin : '0 10 0 0',
				handler : function() {
					if (baseinfo.userDept.currInfo.updateFormRowInfo == null) {
						return;
					}
					var a_updateForm = Ext
							.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailForm_Id');

					a_updateForm
							.loadRecord(baseinfo.userDept.currInfo.updateFormRowInfo);
					var rowInfo = baseinfo.userDept.currInfo.updateFormRowInfo;
					var userName = rowInfo.data.empCode + "("
							+ rowInfo.data.empName + ")";
					a_updateForm.getForm().findField('userName')
							.setValue(userName);
					a_updateForm.getForm().findField('operateOrgUnifieldCode')
							.setCombValue(rowInfo.data.operateOrgName,
									rowInfo.data.operateOrgUnifieldCode);

					// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
					baseinfo.userDept.enterUpdatePanel();

				}
			});
		}
		return this.resetButton;
	},
	saveUserDepts : function() {
		var wind = Ext
				.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id');
		var userCode = wind.down('form').getForm().findField('empCode')
				.getValue();
		var selectedDepts = Ext
				.getCmp('Foss_baseinfo_userDeptAuthority_selectedDepartmentsPanel_Id');
		var records = selectedDepts.getStore().data.items;
		var deptDataEntities = new Array();
		var userDept = {};
		Ext.Array.each(records, function(item, index, allItems) {
					userDept = {
						'userEntity' : {
							'empCode' : userCode
						},
						'dept' : item.data.entity,
						'subOrg' : item.data.subOrg
					};
					deptDataEntities.push(userDept);
				});

		var params = {
			'deptDataEntities' : deptDataEntities,
			'empCode' : userCode
		};
		var successFun = function(json) {
			Ext.MessageBox.show({
						title : '信息（成功）提示',
						msg : json.message,
						width : 300,
						buttons : Ext.Msg.OK,
						icon : Ext.MessageBox.INFO
					});
			wind.close();
		};
		var failureFun = function(json) {
			if (Ext.isEmpty(json)) {
				Ext.MessageBox.show({
							title : '信息（失败）提示',
							msg : '请求超时',
							width : 450,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			} else {
				Ext.MessageBox.show({
							title : '信息（失败）提示',
							msg : json.message,
							width : 450,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			}
		};
		var url = baseinfo.realPath('saveUserDepts.action');// 请求月台新增
		baseinfo.requestJsonAjax(url, params, successFun, failureFun);// 发送AJAX请求
	},
	getSaveButton : function() {
		var me = this;
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
				cls : 'yellow_button',
				text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.save'),
				disabled:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthoritySaveButton'),
				hidden:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthoritySaveButton'),
				margin : '0 10 0 0',
				handler : function() {
					me.saveUserDepts();
					var a_win = Ext
							.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id');
					a_win.setVisible(false);
				}
			});
		}
		return this.saveButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [Ext.create('Foss.baseinfo.userDept.UserOrgDeptDetailForm'),
				Ext.create('Foss.baseinfo.userDept.SelectDeptPanel')];
		// me.getResetButton(),
		me.fbar = [me.getCancelButton(), me.getSaveButton()]
		me.callParent([cfg]);
	}
})

// ==============================================================================================
// 用户部门-修改窗口
// ==============================================================================================

// 定义baseinfo.userDeptAuthority.i18n('foss.baseinfo.userDeptDetailShowView')
Ext.define('Foss.baseinfo.userDept.UserOrgDeptDetailForm', {
			extend : 'Ext.form.Panel',
			id : 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailForm_Id',
			title : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1165'),
			frame : true,
			hidden : false,
			defaultType : 'textfield',
			layout : 'column',
			align : 'stretch',
			// buttonAlign : 'center',// 按钮居中
			columnWidth : 0.98,
			defaults : {
				width : 220,
				labelWidth : 40
			},

			initComponent : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.items = [{
							hidden : true,
							name : 'empCode',
							fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1166')
						}, {
							readOnly : true,
							name : 'userName',
							fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1167'),
							margin : '0 0 0 70'
						}, {
							xtype : 'dynamicorgcombselector',
							type : 'ORG',
							name : 'operateOrgUnifieldCode',
							valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
							forceSelection : true,
							readOnly : true,
							fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1168'),
							listeners : {
								select : function(combo, records, eOpts) {
									// 当combo选择值以后
									// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
									baseinfo.userDept.enterUpdatePanel();
								}
							}
						}];
				me.callParent([cfg]);
			}
		});

baseinfo.ORG_ROOT_ID = '103';
baseinfo.userDeptAuthority.orgRootUnifiedCode='DP00002';
baseinfo.userDeptAuthority.orgRootName=baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1169');

/**
 * 定义功能树的store
 */
Ext.define('Foss.baseinfo.userDept.DepartmentTreeStore', {
	extend : 'Ext.data.TreeStore',
	autoSync : false,
	proxy : {
		type : 'ajax',
		url : baseinfo.realPath('loadUserDeptsTree.action')
	},
	root : {
		//id : baseinfo.ORG_ROOT_ID,// 实际是根部门的CODE
		//text : '集团',// 集团
		id : baseinfo.ORG_ROOT_ID,//实际是根部门的根路径
		text : baseinfo.userDeptAuthority.orgRootName,// 集团
		expanded : false
	},
	sorters : [{
				property : 'leaf',
				direction : 'ASC',
				checked : false
			}],
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
							params : searchParams
						});
			}
			var grid = Ext
					.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityGrid_Id');
			if (!Ext.isEmpty(grid) && !Ext.isEmpty(grid.userCode)) {
				searchParams['orgAdministrativeInfoVo.userCode'] = grid.userCode;
			}
		}
	}
});
/**
 * 左侧树结构面板
 */
Ext.define('Foss.baseinfo.userDept.DepartmentTreePanel', {
			extend : 'Ext.tree.Panel',
			id : 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id',
			// autoScroll : true,
			// margin : '0 5 0 0',
			// border : false,
			// columnWidth : 0.58,
			width : 350,
			frame : true,
			oldFullPath : null,// 刷新之前展开的路径
			useArrows : true,
			// collapsible : true,
			rootVisible : true,
			// height : 250,
			viewConfig : {
				plugins : {
					ptype : 'treeviewdragdrop',
					appendOnly : true
				}
			},
			layoutConfig : {
				// 展开折叠是否有动画效果
				animate : true
			},
			oldId : null,
			// 左键单击事件
			treeLeftKeyAction : function(node, record, item, index, e) {
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.userDept.DepartmentTreeStore');	
				me.tbar =[{
						xtype: 'textfield',
						name:'orgName', //部门名称
						id: 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_QueryText_Id',
						fieldLabel:baseinfo.userDeptAuthority.i18n('foss.baseinfo.orgName'),//角色部门
						labelWidth: 100
					},{
						text:baseinfo.userDeptAuthority.i18n('foss.baseinfo.query'),//查询
						margin:'5 10 5 5',
						handler:function(){
							//获得树
							var a_tree =Ext.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id');
							//获取查询文本框的值
							var orgName =Ext.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_QueryText_Id').getValue();
							if(Ext.isEmpty(orgName)){
								baseinfo.showWoringMessage(baseinfo.userDeptAuthority.i18n('foss.baseinfo.pleaseEnterQuery'));//请输入查询信息！
							}else{
								Ext.Ajax.request({
									jsonData:{'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':{'name':orgName}}},
									url:baseinfo.realPath('queryOrgAllFullPathByOrgName.action'),
									success:function(response){
										json =Ext.decode(response.responseText);
										//获取第一个节点的路径
										var a_fullPath =json.orgAdministrativeInfoVo.fullPath;
										if(Ext.isEmpty(a_fullPath)){
											//若路径为空，则加载全部节点
											a_tree.getStore().load({'node':a_tree.getRootNode()});
										}else{
											//不为空，按第一个节点的全部路径展开
											a_tree.expandPath(a_fullPath,'id','.');
										}
									},
								});
							}
						}
					}];
//				var fun = function(node, checked, eOpts) {
//					node.eachChild(function(cNode) {
//								cNode.data.checked = checked;
//								cNode.updateInfo({
//											checked : true
//										});
//								changeChildrenState(cNode, checked, eOpts);
//							})
//				},
//				// 递归调用，改变孩子节点的选择状态
//				var changeChildrenState = function(node, checked, eOpts) {
//					if (!node.data.leaf) {
//						if (checked) {
//							// 087584-foss-lijun 不让自动加载,不然选择一个结点，会自动加载下级的多级子结点
//							// node.expand(true, fun(node, checked, eOpts));
//							node.expand(false, fun(node, checked, eOpts));
//							var i = {};
//							var b = {};
//						} else {
//							// 087584-foss-lijun 不让自动加载,不然选择一个结点，会自动加载下级的多级子结点
//							// node.collapse(true, fun(node, checked, eOpts));
//							node.collapse(false, fun(node, checked, eOpts));
//						}
//					}
//				};
				// 监听事件
				me.listeners = {
					checkchange : function(node, checked, eOpts) {
						node.addListeners
						// 下面一句，设置时，checked一个结点，会自动加载下级结点
						//changeChildrenState(node, checked, eOpts);
					},
					scrollershow : function(scroller) {
						if (scroller && scroller.scrollEl) {
							scroller.clearManagedListeners();
							scroller.mon(scroller.scrollEl, 'scroll',
									scroller.onElScroll, scroller);
						}
					},
					itemclick : me.treeLeftKeyAction
					// 单击事件
					// itemdblclick:treeDbLeftKeyAction//双击事件
				};
				me.callParent([cfg]);
			}
		});

// 已选部门列表
Ext.define('Foss.baseinfo.userDept.selectedDepartmentsPanel', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_userDeptAuthority_selectedDepartmentsPanel_Id',
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	store:null,
	width : 300,
	flex : 1,
	frame : true,
	columns : [{
				header : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1170'),
				dataIndex : 'text',
				width : 100,
				titlehidden : true
			},{
				header : '是否包括子部门',
				xtype: 'checkcolumn',
				dataIndex : 'subOrg',
				width : 100
			}, {
				hidden : true,
				dataIndex : 'entity.code'
			}, {
				hidden : true,
				dataIndex : 'parentId'
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel');
		me.listeners = {
			scrollershow : function(scroller) {
				if (scroller && scroller.scrollEl) {
					scroller.clearManagedListeners();
					scroller.mon(scroller.scrollEl, 'scroll',
							scroller.onElScroll, scroller);
				}
			},
			itemdblclick : function(ths, record, item, index, e, eOpts) {
				ths.store.remove(record);
				var root = Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id')
						.getRootNode();
				var findchildnode = function(node) {
					var parentNode;
					if (record.raw.parentId == node.data.id) {
						parentNode = node;
						return parentNode;
					}
					if (node.hasChildNodes()) { // 判断子节点下是否存在子节点
						var childnodes = node.childNodes;
						for (var i = 0; i < childnodes.length; i++) { // 从节点中取出子节点依次遍历
							if (record.raw.parentId == childnodes[i].data.id) {
								parentNode = childnodes[i];
								return parentNode;
							}
							findchildnode(childnodes[i]); // 如果存在子节点 递归
						}
					}
				}
				var parentNode = findchildnode(root);
				parentNode.appendChild(record);
			}
		}
		me.store = Ext
				.create('Foss.baseinfo.userDept.initDepartmentsStore');
		me.callParent([cfg]);
	}
});
//初始化用户权限部门
Ext.define('Foss.baseinfo.userDept.initDepartmentsStore', {
			extend : 'Ext.data.Store',
			fields : ['text', 'subOrg', 'entity', 'id', 'parentId'],
			proxy : {
				type : 'ajax',
				url :  baseinfo.realPath('searchUserDepts.action'),
				actionMethods : 'post',
				reader : {
					type : 'json',
					root : 'nodes'
				}
			}
		});
		
// 角色选择面板
Ext.define('Foss.baseinfo.userDept.SelectDeptPanel', {
			extend : 'Ext.panel.Panel',
			id : 'Foss_baseinfo_userDeptAuthority_SelectDeptPanel_Id',
			itemId : 'Foss_baseinfo_userDeptAuthority_SelectDeptPanel_ItemId',
			height : 355,
			columnWidth : 0.99,
			layout : 'column',
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			defaults : {
				readOnly : false
			},
			frame : true,
			initComponent : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				var selectableDeptTree = Ext
						.create('Foss.baseinfo.userDept.DepartmentTreePanel');
				var selectedDeptPanel = Ext
						.create('Foss.baseinfo.userDept.selectedDepartmentsPanel');
				var selectButtonPanel = Ext
						.create('Foss.baseinfo.userDept.LeftRightButtonPanel');
				selectButtonPanel.setRightMove(selectedDeptPanel);
				selectButtonPanel.setLeftMove(selectableDeptTree);
				me.items = [selectableDeptTree, selectButtonPanel,
						selectedDeptPanel];
				me.callParent([cfg]);
			}
		});

// ==============================================================================================
// 下面是查询界面详细实现
// ==============================================================================================

/**
 * 用户部门界面数据模型定义
 */
/**
 * 人员界面数据模型定义
 */
Ext.define('Foss.baseinfo.userDept.EmployeeModel', {
			extend : 'Ext.data.Model',
			fields : [
					// ID
					{
				name : 'id',
				type : 'string'
			},
					// 职员姓名
					{
						name : 'empName',
						type : 'string'
					},
					// 拼音
					{
						name : 'pinyin',
						type : 'string'
					},
					// 工号
					{
						name : 'empCode',
						type : 'string'
					},
					// 性别
					{
						name : 'gender',
						type : 'string'
					},
					// 部门标杆编码
					{
						name : 'unifieldCode',
						type : 'string'
					},
					// 职位
					{
						name : 'title',
						type : 'string'
					},
					// 职等
					{
						name : 'degree',
						type : 'string'
					},
					// 出生日期
					{
						name : 'birthdate',
						type : 'date'
					},
					// 状态
					{
						name : 'status',
						type : 'string'
					},
					// 电话
					{
						name : 'phone',
						type : 'string'
					},
					// 身份证号
					{
						name : 'identityCard',
						type : 'string'
					},
					// 入职日期
					{
						name : 'entryDate',
						type : 'date'
					},
					// 离职日期
					{
						name : 'leaveDate',
						type : 'date'
					},
					// 手机号码
					{
						name : 'mobilePhone',
						type : 'string'
					},
					// 电子邮箱
					{
						name : 'email',
						type : 'string'
					},
					// 创建时间
					{
						name : 'createTime',
						type : 'date'
					},
					// 更新时间
					{
						name : 'modifyTime',
						type : 'date'
					},
					// 是否启用
					{
						name : 'active',
						type : 'string'
					},
					// 创建人
					{
						name : 'createUserCode',
						type : 'string'
					},
					// 更新人
					{
						name : 'modifyUserCode',
						type : 'string'
					}]
		});

// 查询条件面板
Ext.define('Foss.baseinfo.userDept.SelectConditionForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_userDeptAuthority_SelectConditionForm_Id',
	itemId : 'Foss_baseinfo_userDeptAuthority_SelectConditionForm_ItemId',
	layout : 'column',
	height: 170,
	frame : true,
	title : baseinfo.userDeptAuthority.i18n('foss.baseinfo.queryCondition'),
	defaults : {
		xtype : 'textfield',
		margin : '5 5 5 10'
	},
	initComponent : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		// 让数据字典可模糊查询
//	    var a_dictValue = login.dataDictionary.get('UUMS_POSITION');
		me.buttons = [{
			xtype : 'button',
			text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.reset'),
			disabled:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthorityQueryButton'),
			hidden:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthorityQueryButton'),
			width : 30,
			handler : function() {
				var userOrgDeptForm = Ext
						.getCmp("Foss_baseinfo_userDeptAuthority_SelectConditionForm_Id");
				// 将表单中的数据清空：
				userOrgDeptForm.getForm().reset();
			}
		}, '->', {
			xtype : 'button',
			cls : 'yellow_button',
			disabled:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthorityQueryButton'),
			hidden:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthorityQueryButton'),
			text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.query'),
			width : 30,
			// 查询按钮的响应事件：
			handler : function() {
				var selectResultPanel = Ext
						.getCmp("Foss_baseinfo_userDeptAuthority_SelectResultForm_Id");
				selectResultPanel.setVisible(true);
				Ext
						.getCmp("Foss_baseinfo_userDeptAuthority_userDeptAuthorityGridPagingBar_Id")
						.moveFirst();
			}
		}];
		me.items = [{
				columnWidth : 0.3,
				name : 'empCode',
				fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.jobNumber')
			},{
				columnWidth : 0.3,
				name : 'empName',
				fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.employeeName')
			},
			{
				name: 'title',
//				queryMode: 'local',
//			    displayField: 'valueName',
//			    valueField: 'valueCode',
//			    store: Ext.create('Ext.data.Store', {
//				    fields: ['valueCode','valueName'],
//				    data:a_dictValue
//			     }),
		        fieldLabel: baseinfo.userDeptAuthority.i18n('foss.baseinfo.position'),//词名称
//		        xtype : 'combo',
				columnWidth:.3
			}
			,{
				name : 'phone',
				columnWidth : 0.3,
				fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.phoneCode')
			},{
				xtype : 'dynamicorgcombselector',
				type : 'ORG',
				columnWidth : 0.3,
				name : 'unifieldCode',
				valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
				forceSelection : true,
				fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.orgName')
			},
			FossDataDictionary.getDataDictionaryCombo('EMPLOYEE_STATUS', {
					fieldLabel : baseinfo.userDeptAuthority.i18n('foss.baseinfo.status'),
					columnWidth : 0.3,
					name : 'status'
				}, [{
				'valueCode' : '',
				'valueName' : '全部'
			}])];
		me.callParent([cfg]);
	}
});

// 用户部门的类定义：
Ext.define('Foss.baseinfo.userDept.UserOrgDeptComboboxStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			proxy : {
				type : 'memory',
				reader : {
					type : 'json',
					root : 'items'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});
// 查询结果面板
Ext.define('Foss.baseinfo.userDept.SelectResultForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_baseinfo_userDeptAuthority_SelectResultForm_Id',
	itemId : 'Foss_baseinfo_userDeptAuthority_SelectResultForm_ItemId',
	layout : 'column',
	frame : true,
	hidden : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	title : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1171'),
	defaults : {
		readOnly : false,
		margin : '5 5 5 10',
		anchor : '90%'
	},
	initComponent : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [Ext
				.create('Foss.baseinfo.userDept.UserOrgDeptGrid')];
		me.callParent([cfg]);
	}
});

// 查询的显示表格：
Ext.define('Foss.baseinfo.userDept.UserOrgDeptGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityGrid_Id',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	// title : baseinfo.userDeptAuthority.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1172'),
	columnWidth : .99,
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : baseinfo.userDeptAuthority.i18n('foss.baseinfo.queryResultIsNull'),
	// 增加滚动条
	autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	userCode : null,
	selModel : Ext.create('Ext.selection.RowModel'),
	columns : [{
		xtype : 'actioncolumn',
		flex : 0.3,
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.operate'),
		align : 'left',
		items : [{
			iconCls : 'deppon_icons_edit',
			tooltip : baseinfo.userDeptAuthority.i18n('foss.baseinfo.update'),
			disabled:!baseinfo.userDeptAuthority.isPermission('userDeptAuthority/userDeptAuthorityUpdateButton'),
			handler : function(grid, rowIndex, colIndex) {
				var rowInfo = grid.getStore().getAt(rowIndex);
				var a_win = Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id');
				Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityGrid_Id').userCode = rowInfo.data.empCode;
				if (Ext.isEmpty(a_win)) {
					a_win = Ext
							.create('Foss.baseinfo.userDept.UserOrgDeptDetailWindow');
				}
				var a_updateForm = Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailForm_Id');
				a_updateForm.loadRecord(rowInfo);
				
				/*
				var tree = Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id');
				tree.store.load({
					params : {
						'orgAdministrativeInfoVo.userCode' : rowInfo.data.empCode
					},
					callback : function(records, operation, success) {
						Ext
								.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityTree_Id')
								//下面一句为true的话设置自动加载所有树结点
								.getRootNode().expandChildren(false);
					}
				});
				*/

				Ext
						.getCmp('Foss_baseinfo_userDeptAuthority_selectedDepartmentsPanel_Id').store
						.load({
							params : {
								'orgAdministrativeInfoVo.userCode' : rowInfo.data.empCode
							}
						});
				var userName = rowInfo.data.empCode + "("
						+ rowInfo.data.empName + ")";
				a_updateForm.getForm().findField('userName').setValue(userName);
				// 设置编码不可以修改
				a_updateForm.getForm().findField('userName').setReadOnly(true);
				// 设置combobox的值
				a_updateForm.getForm().findField('operateOrgUnifieldCode')
						.setCombValue(rowInfo.raw.department.name,
								rowInfo.data.unifieldCode);
				a_win.show();
				
				// 重置树型结构
				baseinfo.userDeptAuthority.reloadTreeNode();
			}
		}]
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.jobNumber'),
		align : 'center',
		flex : 1,
		dataIndex : 'empCode'
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.employeeName'),
		align : 'center',
		flex : 1,
		dataIndex : 'empName'
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.position'),
		align : 'center',
		flex : 1,
		dataIndex : 'title',
//		renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
//			//var a_value =value;
//			// a_value = a_value=='1'?baseinfo.userDeptAuthority.i18n('foss.baseinfo.holdPost'):a_value=='2'?'离职':a_value
//			var a_value = FossDataDictionary.rendererSubmitToDisplay(value,'UUMS_POSITION');
//			a_value =a_value==null?value:a_value;
//			
//			return a_value;
//		}
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.phoneCode'),
		align : 'center',
		flex : 1,
		dataIndex : 'phone'
	}, {
		hidden : true,
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.department'),
		align : 'center',
		flex : 1,
		dataIndex : 'unifieldCode'
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.orgName'),
		align : 'center',
		flex : 1,
		dataIndex : 'unifieldCodeName',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex,
				store) {
			var aimStr = value;
			if (record && record.raw && record.raw.department) {
				aimStr = record.raw.department.name;
			}
			return aimStr;
		}
	}, {
		text : baseinfo.userDeptAuthority.i18n('foss.baseinfo.status'),
		align : 'center',
		flex : 1,
		dataIndex : 'status',
		renderer : function(value, cellmeta, record, rowIndex, columnIndex,
				store) {
			var a_value = value;
				a_value = a_value == '1' ? baseinfo.userDeptAuthority.i18n('foss.baseinfo.holdPost') : a_value == '2' ? '离职' : a_value;
			return a_value;
		}
	}],

	listeners : {
		/*
		itemdblclick : function(me, record, item, index, e, eOpts) {
			// 获得当前选择的行的的数据
			// var rowInfo = me.getStore().getAt(rowIndex);

			// 设置用户部门详情
			var a_detailForm = Ext
					.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailForm_Id');
			if (record.raw && record.raw.empCode && record.raw.empName) {
				record.data.empName = record.raw.empCode + "("
						+ record.raw.name + ")";
			}
			a_detailForm.getForm().setValues(record.data);

			var a_window = Ext
					.getCmp('Foss_baseinfo_userDeptAuthority_userDeptAuthorityDetailWindow_Id');
			a_window.show();

		}*/
	},

	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store', {
			model : 'Foss.baseinfo.userDept.EmployeeModel',
			pageSize : 10,
			autoLoad : false,
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : '../baseinfo/queryEmployeeAndUserByEntity.action',
				// 定义一个读取器
				reader : {
					type : 'json',
					root : 'employeeVo.employeeList',
					totalProperty : 'totalCount'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners : {
				beforeload : function(store, operation, eOpts) {
					var queryForm = Ext.getCmp('Foss_baseinfo_userDeptAuthority_SelectConditionForm_Id');
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								// 设置请求参数
								'employeeVo.employeeDetail.empCode' : queryParams.empCode,
								'employeeVo.employeeDetail.empName' : queryParams.empName,
								'employeeVo.employeeDetail.title' : queryParams.title,
								'employeeVo.employeeDetail.unifieldCode' : queryParams.unifieldCode,
								'employeeVo.employeeDetail.status' : queryParams.status,
								'employeeVo.employeeDetail.phone' : queryParams.phone
							}
						});
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging', {
			id : 'Foss_baseinfo_userDeptAuthority_userDeptAuthorityGridPagingBar_Id',
			store : me.store
		});
		me.callParent([cfg]);
	}
});

/**
 * 程序入口方法
 */
Ext.onReady(function() {
			Ext.QuickTips.init();
			Ext.getCmp('T_baseinfo-userDeptAuthority').add(Ext.create('Ext.panel.Panel', {
				id : 'T_baseinfo-userDeptAuthority_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				items : [
					Ext.create('Foss.baseinfo.userDept.SelectConditionForm'),
					Ext.create('Foss.baseinfo.userDept.SelectResultForm')
				]
			}));
		});



