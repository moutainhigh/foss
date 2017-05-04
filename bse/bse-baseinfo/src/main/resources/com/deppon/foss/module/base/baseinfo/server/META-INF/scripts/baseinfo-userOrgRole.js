/**
*	综合管理 用户部门角色 JS功能代码：
*/

////////////////////////////////////////////////////////////////////
// 全局变量（模块名userOrgRole） start
////////////////////////////////////////////////////////////////////

//var userOrgRole = new Object();
baseinfo.userOrgRole.rawRecord = null; //记录表单的当前值，在重置的时候使用。

// 将当前修改的用户，部门保存下来，在重置时使用
baseinfo.userOrgRole.currInfo = new Object();
baseinfo.userOrgRole.currInfo.unifiedCode = null;
baseinfo.userOrgRole.currInfo.empCode = null;
baseinfo.userOrgRole.currInfo.empName = null;
baseinfo.userOrgRole.currInfo.updateFormRowInfo = null;

/**
 * 进入update界面时，给可选角色，已选角色框设置值，设置角色权限列表的值
 */
baseinfo.userOrgRole.enterUpdatePanel = function(){
	baseinfo.userOrgRole.selectableRoleFrame.getStore().removeAll();
	baseinfo.userOrgRole.selectedRoleFrame.getStore().removeAll();
	
	baseinfo.userOrgRole.setRoleToFram(true);
	baseinfo.userOrgRole.setRoleToFram(false);
	baseinfo.userOrgRole.pagingBarRoleResource.moveFirst();
}


// 初始化角色左移框
/**
 * p_isLeft 是否是左框，true,左框，否则为右框
 */
baseinfo.userOrgRole.setRoleToFram = function(p_isLeft){
	var a_form = Ext.getCmp("Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id");
	var a_empCode = a_form.getForm().findField('empCode').getValue();
	// TODO var a_orgCode = a_form.getForm().getValue("orgUnifiedCode");
	var a_operateOrgUnifiedCode = a_form.getForm().findField('operateOrgUnifiedCode').getValue();
	var userOrgRoleVo = new Object();
	userOrgRoleVo.userOrgRoleEntity = new Object();
	userOrgRoleVo.userOrgRoleEntity.empCode = a_empCode;
	userOrgRoleVo.userOrgRoleEntity.orgUnifiedCode = a_operateOrgUnifiedCode;
	
	// "../baseinfo/queryRoleByUserOrgAll.action"
	var r_url = "queryRoleByUserOrgAll.action";
	r_url=	baseinfo.realPath(r_url) ;
	var a_url = r_url;
	// 如果是展示可分配（未分配）的角色
	if(p_isLeft){
		// "../baseinfo/queryRoleByNoAllot.action"
		var r_url = "queryRoleByNoAllot.action";
		r_url=	baseinfo.realPath(r_url) ;
		a_url = r_url;
	}
	// 将角色编码及包含的权限发送到后台：
	var params = {'userOrgRoleVo':userOrgRoleVo};
	Ext.Ajax.request({
		method:'POST',
		url:a_url,
		jsonData:params,
		async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				/* 
				*/
				if(result.userOrgRoleVo!=null && result.userOrgRoleVo.roleList != null){
	
					// 获得角色数组
					var a_roles = result.userOrgRoleVo.roleList;
					// 转化成左右移框中的数组
					var a_frameRoles = new Array();
					var l=a_roles.length;
					for(var i=0;i<l;i++){
						var a_meta = {'name':a_roles[i].name,'code':a_roles[i].code};
						a_frameRoles.push(a_meta);
					}
					// 将数组中的值设置到框中
					if(p_isLeft){
						baseinfo.userOrgRole.selectableRoleFrame.getStore().loadData(a_frameRoles);
					}else{
						baseinfo.userOrgRole.selectedRoleFrame.getStore().loadData(a_frameRoles);
					}
				
				}
				
			}else{
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
		}
	});
}

// 更新用户部门角色
baseinfo.userOrgRole.updateUserOrgRole = function(){
	// 获得选择的权限编码
	var a_roleCodes="";
	var a_roleStore = baseinfo.userOrgRole.selectedRoleFrame.getStore();
	/* 待请教，调试
	a_roleStore.each(function(record){
		var a_roleCode = record.get('code');
		a_roleCodes+=","+a_roleCode;
	});
	*/
	var a_array = a_roleStore.data.items;
	for(var i=0,l=a_array.length;i<l;i++){
		var a_roleCode = a_array[i].data.code;
		a_roleCodes+=","+a_roleCode;
	}
	
	
	if(a_roleCodes && a_roleCodes.length>0){
		a_roleCodes = a_roleCodes.substring(1,a_roleCodes.length);
	}
	

	var a_form = Ext.getCmp("Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id");

	var a_empCode = a_form.getForm().findField('empCode').getValue();
	var a_operateOrgUnifiedCode = a_form.getForm().findField('operateOrgUnifiedCode').getValue();
	
	
	if(a_operateOrgUnifiedCode == null || a_operateOrgUnifiedCode==''){
		Ext.Msg.alert(baseinfo.userOrgRole.i18n('foss.baseinfo.tips'), baseinfo.userOrgRole.i18n('foss.baseinfo.selectOperateDept'));
		return;
	}
	
	if(!a_form.getForm().isValid()){
		return;
	}
	
	var userOrgRoleVo = new Object();
	userOrgRoleVo.userOrgRoleEntity = new Object();
	userOrgRoleVo.userOrgRoleEntity.empCode = a_empCode;
	userOrgRoleVo.userOrgRoleEntity.orgUnifiedCode = a_operateOrgUnifiedCode;
	userOrgRoleVo.userOrgRoleEntity.roleCode = a_roleCodes;
	
	// "../baseinfo/updateUserOrgRole.action"
	var r_url = "updateUserOrgRole.action";
	r_url=	baseinfo.realPath(r_url) ;
	var a_url = r_url;
	
	// 将角色编码及包含的权限发送到后台：
	var params = {'userOrgRoleVo':userOrgRoleVo};
	Ext.Ajax.request({
		method:'POST',
		url: a_url,
		jsonData:params,
		async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success:function(response){
			var result = Ext.decode(response.responseText);
			var msgTip = baseinfo.userOrgRole.i18n('foss.baseinfo.saveSuccessful');
			if(result.success){
				// 数据有可能被删除，查询列表显示到第一页：
				baseinfo.userOrgRole.pagingBar.moveFirst();
				Ext.Msg.alert(baseinfo.userOrgRole.i18n('foss.baseinfo.tips'), msgTip);
			}else{
				msgTip = baseinfo.userOrgRole.i18n('foss.baseinfo.saveFail');
				Ext.Msg.alert(baseinfo.userOrgRole.i18n('foss.baseinfo.tips'), msgTip);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
		}
	});
	

	var a_win=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailWindow_Id');
	a_win.setVisible(false);
	
}


//选择框
Ext.define('Foss.baseinfo.userOrgRole.SelectFrame', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   columns: [
       { header:'可选产品：',dataIndex: 'name' ,titlehidden:true,flex:1},
       { hidden:true, dataIndex: 'code'}
   ],
   height: 150,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store = 	Ext.create('Ext.data.Store', {
					    fields:['name','code']
					});
		me.callParent([cfg]);
	}
});
//已选的
Ext.define('Foss.baseinfo.userOrgRole.Selected', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   columns: [
       { header:'已选产品：',dataIndex: 'name' ,titlehidden:true,flex:1},//可选产品：
       { hidden:true, dataIndex: 'code'}
   ],
   height: 150,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store =	Ext.create('Ext.data.Store', {
					    fields:['name','code']
					});
		me.callParent([cfg]);
	}
});
//按钮panel
Ext.define('Foss.baseinfo.userOrgRole.LeftRightButtonPanel', {
    extend:'Ext.panel.Panel',
    height: 150,
    width: 80,
   
    // 左移框
    leftMoveFrame:null,
    // 右移框
    rightMoveFrame:null,

    setLeftMove : function(a_moveFrame){
    	this.leftMoveFrame = a_moveFrame;
    },
	getLeftMove : function(){
		return this.leftMoveFrame;
	},
    setRightMove : function(a_moveFrame){
    	this.rightMoveFrame = a_moveFrame;
    },
	getRightMove : function(){
		return this.rightMoveFrame;
	},
   
   //右移全部
   rightMoveAll:function(){
	   var me = this;
	   var  rightStore = this.getRightMove().getStore();
	   var  leftStore = this.getLeftMove().getStore();
	   leftStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   rightStore.add(moveRecord);
	   });
	   leftStore.removeAll();
   },
   //右移
   rightMove:function(){
	   var me = this; 
	   var  rightStore = this.getRightMove().getStore();
	   var  selections = this.getLeftMove().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   rightStore.add(moveRecord);
	   this.getLeftMove().getStore().remove(selections);
   },
    //左移全部
   leftMoveAll:function(){
	   var me = this; 
	   var  rightStore = this.getRightMove().getStore();
	   var  leftStore = this.getLeftMove().getStore();
	   rightStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   leftStore.add(moveRecord);
	   });
	   rightStore.removeAll();
   },
   //左移
   leftMove:function(){
	   var me = this; 
	   var  selections = this.getRightMove().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var  leftStore = this.getLeftMove().getStore();
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   leftStore.add(moveRecord);
	   this.getRightMove().getStore().remove(selections);
   },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.defaults ={
				xtype:'button',
				width:60,
				disabled:false,
				height:20,
				margin:'8 0 0 10'
		};
		me.items = [{
			 text: '-->>',
			 margin:'20 0 0 10',
		     handler: function() {
		    	 me.rightMoveAll();
		     }
		},{
			text: '-->',
		     handler: function() {
		        me.rightMove();
		     }
		},{
			text: '<--',
		     handler: function() {
		    	 me.leftMove();
		     }
		},{
			text: '<<--',
		     handler: function() {
		    	 me.leftMoveAll();
		     }
		}]
		me.callParent([cfg]);
	}
});

////////////////////////////////////////////////////////////////////
// 全局变量（模块名userOrgRole） end
////////////////////////////////////////////////////////////////////

// 用户部门角色查询-主面板：
Ext.define('Foss.baseinfo.userOrgRole.SelectForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_userOrgRole_SelectForm_Id',
	itemId: 'Foss_baseinfo_userOrgRole_SelectForm_ItemId',
	//height: 80,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType : 'textfield',
	layout:'column',
	frame: true,
	columnWidth: 0.99,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 180
	},
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
			Ext.create('Foss.baseinfo.userOrgRole.SelectConditionForm'),
			Ext.create('Foss.baseinfo.userOrgRole.SelectResultForm')
		];
		me.callParent([cfg]);
	}
});


//==============================================================================================
// 用户部门角色详情的窗口
//==============================================================================================
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleDetailWindow',{
	extend: 'Ext.window.Window',
	id : 'Foss_baseinfo_userOrgRole_UserOrgRoleDetailWindow_Id',
	title: baseinfo.userOrgRole.i18n('foss.baseinfo.updateUserOrgRole'),
    width: 800,
    height: 720,
	closeAction:'hide',
	layout : 'column',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		    Ext.create('Foss.baseinfo.userOrgRole.UserOrgRoleDetailForm'),
		    Ext.create('Foss.baseinfo.userOrgRole.SelectRolePanel'),
		    Ext.create('Foss.baseinfo.userOrgRole.RoleListGrid'),
		    Ext.create('Foss.baseinfo.userOrgRole.UserOrgRoleUpdateButtonPanel')
		];
		me.callParent([cfg]);
	}
})


//==============================================================================================
//用户部门角色-修改窗口
//==============================================================================================


//定义baseinfo.userOrgRole.i18n('foss.baseinfo.userDeptRoleDetailShowView')
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleDetailForm',{
	extend: 'Ext.form.Panel',
	id: 'Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id',
	title: baseinfo.userOrgRole.i18n('foss.baseinfo.selectUserOrgRole'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	align: 'stretch',
	buttonAlign: 'center',//按钮居中
	columnWidth: 0.98,
	defaults: {
		margin:'5 5 5 5',
		anchor: '90%',
		columnWidth: 0.99,
		labelWidth: 130
	},
	
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [{
				hidden: true,
				name: 'empCode',
			    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1166'),
				margin:'5 20 5 10',
			    columnWidth:.4
			},{
				readOnly: true,
				name: 'userName',
			    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1167'),
				margin:'5 20 5 10',
			    columnWidth:.4
			},
			/*{
				hidden: true,
				name: 'operateOrgUnifiedCode',
			    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.operateDeptCode'),
				margin:'5 20 5 10',
			    columnWidth:.4
			},{
				name: 'operateOrgName',
			    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1168'),
				margin:'5 20 5 10',
			    columnWidth:.4
			},Ext.create('Foss.baseinfo.userOrgRole.UserOrgRoleDetailButtonPanel')
			*/
			{
				allowBlank: false,
				xtype : 'dynamicorgcombselector',
				type: 'ORG',
				labelWidth:160,
				name:'operateOrgUnifiedCode',
				valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
			//	valueField : 'operateOrgUnifiedCode',// 这个参数，只需与实体中的某个字段对应即可
				forceSelection : true,
				fieldLabel : baseinfo.userOrgRole.i18n('foss.baseinfo.baseinfo-userDeptAuthority.1168'),
				margin:'5 20 5 10',
				labelWidth:100,
			    columnWidth:.4,
			    listeners : {
			    	select : function(combo, records, eOpts) {
			    		// 当combo选择值以后
						// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
						baseinfo.userOrgRole.enterUpdatePanel();
					}
			    }
			}
		];
	  	me.callParent([cfg]);
	}
});


// 角色选择面板
Ext.define('Foss.baseinfo.userOrgRole.SelectRolePanel',{
	extend:'Ext.panel.Panel',
	id: 'Foss_baseinfo_userOrgRole_SelectRolePanel_Id',
	itemId: 'Foss_baseinfo_userOrgRole_SelectRolePanel_ItemId',
	layout:{
		type:'table',
		columns: 3
	},
	columnWidth: 0.99,
	defaults: {
		readOnly : false
	},
	frame:true,
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
	        baseinfo.userOrgRole.selectableRoleFrame, 
			baseinfo.userOrgRole.selectedButton, 
			baseinfo.userOrgRole.selectedRoleFrame
		];
	    me.callParent([cfg]);

	}
});

// 角色列表面板
Ext.define('Foss.baseinfo.userOrgRole.RoleListGrid',{
	extend: 'Ext.grid.Panel',
	id : 'Foss_baseinfo_userOrgRole_RoleListGrid_Id',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:.99, 
	height: 220,
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
    enableColumnHide:false,
    // 设置“如果查询的结果为空，则提示”
    emptyText: baseinfo.userOrgRole.i18n('foss.baseinfo.queryResultIsNull'),

    //增加滚动条
    autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
    frame: true,
    title:baseinfo.userOrgRole.i18n('foss.baseinfo.roleResourceList'),
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	selModel : Ext.create('Ext.selection.RowModel'),
	//selModel: Ext.create('Ext.selection.CheckboxModel'),
	//表格行可展开的插件
	plugins: [{
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Ext.panel.Panel'
	}],
	columns : [{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.role'),
			align: 'left',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'name'
		},{ 
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.resource'),
			align: 'center',
			flex: 5,
			xtype: 'linebreakcolumn',
			dataIndex: 'resources',
			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
				var aimStr=value;
				if(record && record.raw && record.raw.roleResourceEntityList){
					var a_resourceList = record.raw.roleResourceEntityList;
					if(a_resourceList.length>0){
						aimStr =  a_resourceList[0].resourceName;
						for(var i=0,l=a_resourceList.length; i<l; i++){
							aimStr+=","+a_resourceList[i].resourceName;
						}
						
					}
				}
				return aimStr;
			},sortable:false
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.userOrgRole.RoleListOfUserOrgRoleModel',
			pageSize: 3,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				// '../baseinfo/queryRoleByUserOrg.action'
				url : baseinfo.realPath("queryRoleByUserOrg.action"),
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'userOrgRoleVo.roleList',
					totalProperty : 'totalCount'
				}
			}, 
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	},
				beforeload : function(store, operation, eOpts) {
					var a_form = Ext.getCmp("Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id")
					var queryForm = a_form;
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'userOrgRoleVo.userOrgRoleEntity.empCode' : queryParams.empCode,
								'userOrgRoleVo.userOrgRoleEntity.orgUnifiedCode' : queryParams.operateOrgUnifiedCode

							}
						});	
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.userOrgRole.pagingBarRoleResource = me.bbar;
		me.callParent([cfg]);
	}
});

// 取消重置保存按钮面板
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleUpdateButtonPanel',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_userOrgRole_UserOrgRoleUpdateButtonPanel_Id',
	itemId: 'Foss_baseinfo_userOrgRole_UserOrgRoleUpdateButtonPanel_ItemId',
	frame: true,
	defaultType : 'textfield',
	layout:'column',
	columnWidth: 0.98,
	defaults: {
		readOnly : true,
		margin:'5 5 5 5'
	},
	
	// 定义获取按钮的一些方法：
	cancelButton : null,
	resetButton : null,
	saveButton : null,
	
	getCancelButton:function(me){
        if(this.cancelButton==null){
            this.cancelButton = Ext.create('Ext.button.Button',{
                xtype : 'button',
                text: baseinfo.userOrgRole.i18n('foss.baseinfo.cancel'),
                margin:'5 0 5 0',
                columnWidth:.12, 
    			width: 55,
                handler: function(){
                	var a_win=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailWindow_Id');
					a_win.setVisible(false);
                }
            });
          }
          return this.cancelButton;
    },
    getResetButton : function(me){
        if(this.resetButton==null){
        	this.resetButton = Ext.create('Ext.button.Button',{
				xtype : 'button',
				hidden : false,
				name: 'reset',
				text: baseinfo.userOrgRole.i18n('foss.baseinfo.reset'),
				columnWidth:.72, 
				width: 55,
				margin:'5 5 5 440',
				handler: function() {
					if(baseinfo.userOrgRole.currInfo.updateFormRowInfo == null){
						return;
					}
					var a_updateForm=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id');				

					a_updateForm.loadRecord(baseinfo.userOrgRole.currInfo.updateFormRowInfo);
					var rowInfo = baseinfo.userOrgRole.currInfo.updateFormRowInfo;
					var userName = rowInfo.data.empCode+"("+rowInfo.data.empName+")";
					a_updateForm.getForm().findField('userName').setValue(userName);
					a_updateForm.getForm().findField('operateOrgUnifiedCode').setCombValue(rowInfo.data.operateOrgName, rowInfo.data.operateOrgUnifiedCode);

					// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
					baseinfo.userOrgRole.enterUpdatePanel();

				}
			});
        }
        return this.resetButton;
    
    },
    getSaveButton : function(){
        if(this.saveButton==null){
            this.saveButton = Ext.create('Ext.button.Button',{
				cls:'yellow_button',
				text: baseinfo.userOrgRole.i18n('foss.baseinfo.save'),
				columnWidth:.12,  
				width: 55,   
				margin:'5 5 5 5',          
				handler: function() { 
					// baseinfo.userOrgRole.saveUserOrgRole();
					baseinfo.userOrgRole.updateUserOrgRole();
					
					// 保存成功后，表格到第一页
					if(baseinfo.userOrgRole.pagingBar){
						baseinfo.userOrgRole.pagingBar.moveFirst();
					}
				}
            });
        }
        return this.saveButton;
	},
      
  	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
	    			// 取消，重置，查询按钮：
	    			me.getCancelButton(),
	    			me.getResetButton(),
	    			me.getSaveButton()
		];
	    me.callParent([cfg]);

  	}
});




//==============================================================================================
// 下面是查询界面详细实现
//==============================================================================================



/**
* 用户部门角色界面数据模型定义
*/
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleModel', {
    extend: 'Ext.data.Model',
    fields: [
      // 用户部门角色工号/编码
      {name: 'empCode', type: 'string'},
      // 职员姓名
      {name: 'empName', type: 'string'},
      // 职员职位
      {name: 'title', type: 'string'},
      // 所属部门标杆编码
      {name: 'unifiedCode', type: 'string'},
      // 部门名称
      {name: 'orgName', type: 'string'},
      // 用户部门角色手机号
      {name: 'phone', type: 'string'},
      // 操作部门标杆编码
      {name: 'operateOrgUnifiedCode', type: 'string'},
      // 操作部门名称
      {name: 'operateOrgName', type: 'string'},
      // 操作部门是否是用户所属部门，是YES=Y,否NO=N
      {name: 'isDefaultOrg', type: 'string'},
      // 角色名称，查询时作为参数传入
      {name: 'roleName', type: 'date'},
      // 角色列表
      {name: 'roleList', type: 'string'}
    ]
});


/**
* 用户部门角色-角色列表数据模型定义
*/
Ext.define('Foss.baseinfo.userOrgRole.RoleListOfUserOrgRoleModel', {
    extend: 'Ext.data.Model',
    fields: [
      // 角色名称
      {name: 'name', type: 'string'},
      // 权限列表
      {name: 'resources', type: 'string'}
    ]
});

//查询条件面板
Ext.define('Foss.baseinfo.userOrgRole.SelectConditionForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_userOrgRole_SelectConditionForm_Id',
	itemId: 'Foss_baseinfo_userOrgRole_SelectConditionForm_ItemId',	
	layout:'column',
	frame : true,
	columnWidth: 0.98,
	width:1100,
	title: baseinfo.userOrgRole.i18n('foss.baseinfo.queryCondition'),
	defaults: {
		xtype : 'textfield',
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  initComponent: function(config){
    var me = this,
			cfg = Ext.apply({}, config);
    me.items = [
		Ext.create('Ext.container.Container',{
			columnWidth:.99,
			layout : 'column',	
			defaultType : 'textfield',
			items:[{
					name: 'empCode',
				    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.jobNumber'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},{
					name: 'empName',
				    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.employeeName'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},
				/*
				{
					hidden: true,
					name: 'operateOrgUnifiedCode',
				    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.operateDeptUnifiedCode'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},{
					name: 'operateOrgName',
				    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.operateDept'),
					margin:'5 20 5 10',
				    columnWidth:.4
				}, */
				{
					xtype : 'dynamicorgcombselector',
					type: 'ORG',
					labelWidth:160,
					name:'operateOrgUnifiedCode',
					valueField : 'unifiedCode',// 这个参数，只需与实体中的某个字段对应即可
					forceSelection : true,
					fieldLabel : baseinfo.userOrgRole.i18n('foss.baseinfo.operateDept'),
					margin:'5 20 5 10',
					labelWidth:100,
				    columnWidth:.4
				},{
					name: 'roleName',
				    fieldLabel: baseinfo.userOrgRole.i18n('foss.baseinfo.role'),
					margin:'5 20 5 10',
				    columnWidth:.4
				},Ext.create('Ext.container.Container',{
					columnWidth:.99,
					layout : 'column',
					items:[
						{
							columnWidth : 0.75,
							height : 0,
							xtype : 'container',
							style : 'padding-top:20px;border:none',
							hide:true
						},Ext.create('Ext.button.Button',{
							xtype : 'button',
							hidden : false,
							name: 'buttonReset',
							text: baseinfo.userOrgRole.i18n('foss.baseinfo.reset'),
							disabled:!baseinfo.userOrgRole.isPermission('userOrgRoleindex/userOrgRoleindexQueryButton'),
							hidden:!baseinfo.userOrgRole.isPermission('userOrgRoleindex/userOrgRoleindexQueryButton'),
							columnWidth:.12, 
							margin:'0,10,0,0',
							width:20,
							handler: function() {
								var userOrgRoleForm=Ext.getCmp("Foss_baseinfo_userOrgRole_SelectConditionForm_Id");
								// 将表单中的数据清空：
								userOrgRoleForm.getForm().reset();
							}
						}),{
							columnWidth : 0.010,
							height : 0,
							xtype : 'container',
							style : 'padding-top:20px;border:none',
							hide:true
						},Ext.create('Ext.button.Button',{
							xtype : 'button',
							hidden : false,
							cls:'yellow_button',
							name: 'buttonSelect',
							text: baseinfo.userOrgRole.i18n('foss.baseinfo.query'),
							disabled:!baseinfo.userOrgRole.isPermission('userOrgRoleindex/userOrgRoleindexQueryButton'),
							hidden:!baseinfo.userOrgRole.isPermission('userOrgRoleindex/userOrgRoleindexQueryButton'),
							columnWidth:.12, 
							margin:'0,10,0,30',
							width:20,
							
							//查询按钮的响应事件：
							handler: function() {
								var form=this.up('form').getForm();
								var empCode=form.findField('empCode').getValue();
								var empName=form.findField('empName').getValue();
								
								if(Ext.isEmpty(empCode)&&Ext.isEmpty(empName)){
									var msgTip = "请输入工号或职员姓名！";
									Ext.Msg.alert(baseinfo.userOrgRole.i18n('foss.baseinfo.tips'), msgTip);
									return;
								}
								var selectResultPanel=Ext.getCmp("Foss_baseinfo_userOrgRole_SelectResultForm_Id");
								selectResultPanel.setVisible(true);
								
								baseinfo.userOrgRole.pagingBar.moveFirst();
							}
						})
					]
				})					
			]
		})	
	];
    me.callParent([cfg]);
  }
});

// 用户部门角色的类定义：
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleComboboxStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//查询结果面板
Ext.define('Foss.baseinfo.userOrgRole.SelectResultForm',{
	extend:'Ext.form.Panel',
	id: 'Foss_baseinfo_userOrgRole_SelectResultForm_Id',
	itemId: 'Foss_baseinfo_userOrgRole_SelectResultForm_ItemId',
	layout:'column',
	frame: true,
	hidden:true,
	columnWidth: 0.98,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: baseinfo.userOrgRole.i18n('foss.baseinfo.userOrgRoleList'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},

  	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:.99,
				layout : 'column',
				items:[{
						xtype:'label',
						text:baseinfo.userOrgRole.i18n('foss.baseinfo.userOrgRoleList'),
						hidden:true,
						margin:'5 10 5 10',
						columnWidth:.99
					},
					Ext.create('Foss.baseinfo.userOrgRole.UserOrgRoleGrid')

				]
			})	
		];
	    me.callParent([cfg]);
  	}
});

//查询的显示表格：
Ext.define('Foss.baseinfo.userOrgRole.UserOrgRoleGrid',{
	extend: 'Ext.grid.Panel',
	id : 'Foss_baseinfo_userOrgRole_UserOrgRoleGrid_Id',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:.99, 
	
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
    enableColumnHide:false,
    // 设置“如果查询的结果为空，则提示”
    emptyText: baseinfo.userOrgRole.i18n('foss.baseinfo.queryResultIsNull'),
    
    //增加滚动条
    autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	selModel : Ext.create('Ext.selection.RowModel'),
	columns : [{
	        xtype:'actioncolumn',
	        flex: 0.3,
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.operate'),
			align: 'left',
	        items: [{
	        	iconCls:'deppon_icons_edit',
	            tooltip: baseinfo.userOrgRole.i18n('foss.baseinfo.update'),
	            disabled:!baseinfo.userOrgRole.isPermission('userOrgRoleindex/userOrgRoleindexUpdateButton'),
	            handler: function(grid, rowIndex, colIndex) {		
					var a_updateForm=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id');				
					var rowInfo = grid.getStore().getAt(rowIndex);
					a_updateForm.loadRecord(rowInfo);
					
					var userName = rowInfo.data.empCode+"("+rowInfo.data.empName+")";
					a_updateForm.getForm().findField('userName').setValue(userName);
					//a_updateForm.getForm().findField('operateOrgName').setValue(rowInfo.data.operateOrgName);
					// 设置编码不可以修改
					a_updateForm.getForm().findField('userName').setReadOnly(true);
					// a_updateForm.getForm().findField('operateOrgName').setReadOnly(false);
					// 设置combobox的值
					a_updateForm.getForm().findField('operateOrgUnifiedCode').setCombValue(rowInfo.data.operateOrgName, rowInfo.data.operateOrgUnifiedCode);

					// 将当前修改的用户，部门保存下来，在重置时使用
					baseinfo.userOrgRole.currInfo.updateFormRowInfo = rowInfo;
					
					// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
					baseinfo.userOrgRole.enterUpdatePanel();
					
					var a_win=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailWindow_Id');
					a_win.show()
	            }
	        }]
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.jobNumber'),
			align: 'left',
//			xtype: 'ellipsiscolumn',
			flex: 1,
			dataIndex: 'empCode'
			/*
			renderer: function(value) {				
				
				return value;
			}*/
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.xingming'),
			align: 'left',
			flex: 1,
			dataIndex: 'empName'
		},{
			hidden:true,
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.unifiedCode'),
			align: 'center',
			flex: 1,
			dataIndex: 'unifiedCode'
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.department'),
			align: 'left',
			flex: 2,
			dataIndex: 'orgName'
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.post'),//职位
			align: 'left',
			flex: 2,
			dataIndex: 'title'
//				renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
//					//var a_value =value;
//					// a_value = a_value=='1'?baseinfo.userDeptAuthority.i18n('foss.baseinfo.holdPost'):a_value=='2'?'离职':a_value
//					var a_value = FossDataDictionary.rendererSubmitToDisplay(value,'UUMS_POSITION');
//					a_value =a_value==null?value:a_value;
//					
//					return a_value;
//				}
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.mobileNumber'),
			align: 'left',
			flex: 2,
			dataIndex: 'phone'
		},{
			hidden: true,
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.operateDept'),
			align: 'left',
//			xtype: 'ellipsiscolumn',
			flex: 1,
			dataIndex: 'operateOrgUnifiedCode'
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.operateDeptName'),
			align: 'left',
			flex: 2,
			dataIndex: 'operateOrgName'
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.isDefault'),
			align: 'center',
			flex: 0.8,
			dataIndex: 'isDefaultOrg',
			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
				var aimStr="";
				if(record.data.unifiedCode==record.data.operateOrgUnifiedCode){
					aimStr=aimStr=baseinfo.userOrgRole.i18n('foss.baseinfo.yes');
				}
				else{
					aimStr=baseinfo.userOrgRole.i18n('foss.baseinfo.no')
				}
				//var aimStr=value=="Y"?baseinfo.userOrgRole.i18n('foss.baseinfo.yes'):value=='N'?baseinfo.userOrgRole.i18n('foss.baseinfo.no'):value;
				return aimStr;
			}
		},{
			text: baseinfo.userOrgRole.i18n('foss.baseinfo.role'),
			align: 'left',
			flex: 4,
			dataIndex: 'status',
			renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
				var aimStr="";
				if(record && record.raw && record.raw.roleList 
						&&record.raw.roleList.length>0){
					var a_roles= record.raw.roleList;
					aimStr= a_roles[0].name;
					for(var i = 1, l=a_roles.length; i<l; i++){
						if(a_roles[i].name){
							aimStr+=","+a_roles[i].name;
						}
						
					}
					if(!Ext.isEmpty(aimStr)){
						value= aimStr;
					}else{
						value = "";
					}
				}
				return value;
			}
		}
	],
	
	listeners:{
		itemdblclick: function(me, record, item, index, e, eOpts) {
			/**
			 * 功能：增加双击事件数据 加载
			 * 修改者：FOSS-073586-LIXUEXING
			 * 修改时间：2013-03-26 17:33
			 */
			// 获得当前选择的行的的数据
			var a_updateForm=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailForm_Id');				
			var rowInfo = record;
			a_updateForm.loadRecord(rowInfo);
			
			var userName = rowInfo.data.empCode+"("+rowInfo.data.empName+")";
			a_updateForm.getForm().findField('userName').setValue(userName);
			// 设置编码不可以修改
			a_updateForm.getForm().findField('userName').setReadOnly(true);
			// 设置combobox的值
			a_updateForm.getForm().findField('operateOrgUnifiedCode').setCombValue(rowInfo.data.operateOrgName, rowInfo.data.operateOrgUnifiedCode);

			// 将当前修改的用户，部门保存下来，在重置时使用
			baseinfo.userOrgRole.currInfo.updateFormRowInfo = rowInfo;
			
			// 初始化可选角色，已选角色框，用户所在操作部门的角色的角色权限列表
			baseinfo.userOrgRole.enterUpdatePanel();
			
			var a_window=Ext.getCmp('Foss_baseinfo_userOrgRole_UserOrgRoleDetailWindow_Id');
			a_window.show();
			var btnArr = a_window.query('button');
			for (i in btnArr){
				btnArr[i].setDisabled(true);
			}
			a_window.down('combo').setReadOnly(true);
			btnArr[(btnArr.length-3)].setDisabled(false);//取消按钮 可点
	    }
	},

	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.userOrgRole.UserOrgRoleModel',
			pageSize:10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				// '../baseinfo/queryUserOrgRoleDto.action'
				url : baseinfo.realPath("queryUserOrgRoleDto.action"),
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'userOrgRoleVo.userOrgRoleDtoList',
					totalProperty : 'totalCount'
				}
			},
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var queryForm = baseinfo.userOrgRole.queryForm;
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								// 设置请求参数
								'userOrgRoleVo.userOrgRoleDto.empCode' : queryParams.empCode,
								'userOrgRoleVo.userOrgRoleDto.empName' : queryParams.empName,
								'userOrgRoleVo.userOrgRoleDto.operateOrgUnifiedCode' : queryParams.operateOrgUnifiedCode,
								'userOrgRoleVo.userOrgRoleDto.roleName' : queryParams.roleName
							}
						});	
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.userOrgRole.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});



/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	
	baseinfo.userOrgRole.selectableRoleFrame = Ext.create('Foss.baseinfo.userOrgRole.SelectFrame');
	var a_columns = baseinfo.userOrgRole.selectableRoleFrame.columns;
	a_columns[0].text = baseinfo.userOrgRole.i18n('foss.baseinfo.canAllocateRole');
	baseinfo.userOrgRole.selectedRoleFrame = Ext.create('Foss.baseinfo.userOrgRole.SelectFrame',{
		columns: [
		     { header:baseinfo.userOrgRole.i18n('foss.baseinfo.allottedRole'),dataIndex: 'name' ,titlehidden:true,flex:1},
		     { hidden:true, dataIndex: 'code'}
	    ]
	});
	baseinfo.userOrgRole.selectedButton = Ext.create('Foss.baseinfo.userOrgRole.LeftRightButtonPanel');
	// 将左移右移框设置到左右移按钮面板的变量上
	baseinfo.userOrgRole.selectedButton.setLeftMove(baseinfo.userOrgRole.selectableRoleFrame);
	baseinfo.userOrgRole.selectedButton.setRightMove(baseinfo.userOrgRole.selectedRoleFrame);
	
	baseinfo.userOrgRole.windowz = Ext.create('Foss.baseinfo.userOrgRole.UserOrgRoleDetailWindow');
	if (Ext.getCmp('T_baseinfo-userOrgRoleindex_content')) {
		return;
	}
	
	Ext.getCmp('T_baseinfo-userOrgRoleindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-userOrgRoleindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		items: [
			Ext.create('Foss.baseinfo.userOrgRole.SelectForm')
		] 
	}));
	baseinfo.userOrgRole.queryForm=Ext.getCmp('Foss_baseinfo_userOrgRole_SelectConditionForm_Id');

});



