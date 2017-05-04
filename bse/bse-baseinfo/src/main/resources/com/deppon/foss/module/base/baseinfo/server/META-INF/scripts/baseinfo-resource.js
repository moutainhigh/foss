baseinfo.resource.MENU_TYPE_WEB ="WEB";
baseinfo.resource.MENU_TYPE_GUI ="GUI";
baseinfo.resource.ROOT_TYPE_WEB ="0";
baseinfo.resource.ROOT_TYPE_GUI ="GUI";
baseinfo.resource.EN_YES ="Y";
baseinfo.resource.EN_NO ="N";
baseinfo.resource.CN_YES =baseinfo.resource.i18n('foss.baseinfo.vehicle.selectYes');
baseinfo.resource.CN_NO =baseinfo.resource.i18n('foss.baseinfo.vehicle.selectNo');
baseinfo.resource.OP_TYPE_UPDATE ="U";
baseinfo.resource.OP_TYPE_ADD ="A";
/**
 * 重置查询条件
 */
baseinfo.resource.reset=function(me){
	var form =me.getForm();
	form.reset();
}
/**
 * 获取选中的tree
 */
baseinfo.resource.getSelectTree = function(){
	var pageIndex= Ext.getCmp('T_baseinfo-resourceindex_content');
	var leftPannel = pageIndex.getResourceLeft();
	var resourceMenuTab = leftPannel.getResourceMenuTab(); 
	var itemId=resourceMenuTab.getActiveTab().getItemId();
	if(itemId == baseinfo.resource.MENU_TYPE_WEB){
		return resourceMenuTab.getWebMenuTreeItem();
	}else if(itemId == baseinfo.resource.MENU_TYPE_GUI){
		return resourceMenuTab.getGuiMenuTreeItem();
	}
	return null;
}

/**
 * 根据权限编码或者权限信息
 */
baseinfo.resource.queryResourceByCode = function(a_code){
	var a_resource;
	// 向后台请求，通过权限code获得权限详情
	var resourceVo = new Object();
	resourceVo.resourceEntityDetail = new Object();
	resourceVo.resourceEntityDetail.code = a_code;
	var params = {'resourceVo':resourceVo};
	
	var r_url = "queryResourceByCode.action";
	r_url=	baseinfo.realPath(r_url) ;
	Ext.Ajax.request({
		url : r_url,
		jsonData:params,
		async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);

			a_resource = json.resourceVo.resourceEntityDetail;
			
		}
	});
	return a_resource;
}

/**
 * 菜单查询
 */
baseinfo.resource.search=function(searchName){
	var me;
	var pageIndex= Ext.getCmp('T_baseinfo-resourceindex_content');
	var leftPannel = pageIndex.getResourceLeft();
	var form=null;
	
	if(Ext.isEmpty(searchName)){
		form = leftPannel.getSearchMenuForm().getForm(); 
		searchName = form.findField("name").getValue();
	}
	if(!Ext.isEmpty(searchName)){
		// 去除前后空格
		searchName = searchName.replace(/(^\s*)|(\s*$)/g,"");			
	}else{
		return false; 
	}
	var resourceMenuTab = leftPannel.getResourceMenuTab(); 
	var itemId=resourceMenuTab.getActiveTab().getItemId();
	if(itemId === 'GUI'){
		me = Ext.getCmp('GUI_RESOURCE_TREE');
	}
	if(itemId === 'WEB'){
		me = Ext.getCmp('WEB_RESOURCE_TREE')
	}
	var resultTree=baseinfo.resource.getSelectTree();
	var params =  {
			"resourceVo.resourceEntityDetail.name":searchName,
			"resourceVo.resourceEntityDetail.belongSystemType":itemId
			
	};
	var url = baseinfo.realPath("queryResourceFullPathByName.action");
	Ext.Ajax.request({
		//请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
		url : url,
		params:params,
		async: false,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    		
			view = me.getView(),
			json = Ext.decode(response.responseText);
			var pathList = json.resourceVo.allFullPath;//查询到的第一个部门的全路径
			me.expandNodes = [];
			me.collapseAll();
			if(Ext.isEmpty(pathList)){
					Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.queryNoResult')); 
					return;
				}
				for(var i=0;i<pathList.length;i++){
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

/**
 * 树节点操作
 */
baseinfo.resource.treeNodeOperator=function(me,record){
	var params = {
			"resourceVo.resourceEntityDetail.code":record.data.id 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryResourceByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var entityDetail = json.resourceVo.resourceEntityDetail;
			if(entityDetail && entityDetail.parentRes){
				entityDetail.parentResName=entityDetail.parentRes.name;
			}
			var rightPannel = Ext.getCmp('T_baseinfo-resourceindex_content').getResourceRight();
			var form =rightPannel.getRightMiddleFormPannel();
			var model = Ext.create('Foss.baseinfo.resource.ResourceModel', entityDetail);
			form.getForm().loadRecord(model); 
			if(rightPannel.isHidden()){
				rightPannel.show();
			}
		}
	});
}
/**
 * 作废操作
 */
baseinfo.resource.cancelOperator = function(){
	var selectTree = baseinfo.resource.getSelectTree();
	var selection = selectTree.getSelectionModel().getSelection();
	var record = selection[0].data;
	if(record.id==baseinfo.resource.ROOT_TYPE_WEB || record.id==baseinfo.resource.ROOT_TYPE_GUI){
    	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.virtualRootNodeNotCancel'));
        return;
	}
	Ext.MessageBox.buttonText.yes = baseinfo.resource.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.resource.i18n('foss.baseinfo.cancel');
	Ext.Msg.confirm(baseinfo.resource.i18n('foss.baseinfo.tips'),baseinfo.resource.i18n('foss.baseinfo.areYouSureToVoid'), function(btn,text){
		if(btn == 'yes'){
			// 获得当前选择的数据：
			if(selection ==null || selection.length<=0){
				Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.plsChoiceCancelMenu'));
		        return;
		    }
			//发送作废结点的Ajax请求.
			var params = {
					"resourceVo.resourceEntityDetail.code":record.id 
			};
		    Ext.Ajax.request({
		        url : baseinfo.realPath("deleteResourceRecursion.action"),
		        params: params,
		        success : function(response) {
		            var json = Ext.decode(response.responseText);		
					//如果 树型结构中当前有这个结点，移除树结点，更新树型结构：
					var currNode= selectTree.getTreeStore().getNodeById(record.id);
					if(currNode){
						currNode.remove();
					}        
					var pageIndex= Ext.getCmp('T_baseinfo-resourceindex_content');
					//隐藏权限详情信息面板
					var rightPannel = pageIndex.getResourceRight();
					rightPannel.hide();
					Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.deleteSuccess'));
		        },
		        exception : function(response) {
		            var json = Ext.decode(response.responseText);
		            Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),json.message);
		        }
		    });		
		}
	});
}

/**
 * 新增下级权限操作
 */
baseinfo.resource.addOperator = function(){
	var updateForm=baseinfo.resource.updateResourceWindow.getResourceUpdateForm();
	var selectTree=baseinfo.resource.getSelectTree();
	var selectionNodes = selectTree.getSelectionModel().getSelection();
    if(selectionNodes ==null || selectionNodes.length <= 0){
    	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.plsChoiceParentMenu'));
        return;
    }
    var record = selectionNodes[0];
    updateForm.getForm().reset();
    updateForm.getForm().findField("code").setReadOnly(false); 
    
    var pageIndexContent = Ext.getCmp('T_baseinfo-resourceindex_content');
	var leftPannelContent = pageIndexContent.getResourceLeft();
	var resourceMenuTab = leftPannelContent.getResourceMenuTab(); 
	var itemId=resourceMenuTab.getActiveTab().getItemId();
    
    updateForm.getForm().findField('belongSystemType').setReadOnly(true);
    updateForm.getForm().findField('belongSystemType').setValue(itemId);
    
	var nodeData = record.data;
	updateForm.getForm().findField('parentResCode').setCombValue(nodeData.text, nodeData.id);

	baseinfo.resource.updateResourceWindow.getResourceUpdateForm().operatorType=baseinfo.resource.OP_TYPE_ADD;
	baseinfo.resource.updateResourceWindow.show();
}
/**
 * 修改操作
 */
baseinfo.resource.updateOperator = function(){
	var updateForm=baseinfo.resource.updateResourceWindow.getResourceUpdateForm();
	var selectTree=baseinfo.resource.getSelectTree();
	var selectionNodes = selectTree.getSelectionModel().getSelection();
    if(selectionNodes ==null || selectionNodes.length <= 0){
    	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.plsChoiceParentMenu'));
        return;
    }
    var record = selectionNodes[0];
    if(record.data.id==baseinfo.resource.ROOT_TYPE_WEB || record.data.id== baseinfo.resource.ROOT_TYPE_GUI){
    	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.virtualRootNodeNotUpdate'));
        return;
    }
    //如果是根节点
    if(typeof(record.raw) == 'undefined'){
      var entity =new Object();
      updateForm.getForm().setValues(entity);
    }else{
      updateForm.getForm().setValues(record.raw.entity);  
    }
    updateForm.getForm().findField("code").setReadOnly(true);
    var nodeData = record.data;
    var code = nodeData.id;
	var params = {
			'resourceVo.resourceEntityDetail.code':code
	};
	Ext.Ajax.request({
		url : baseinfo.realPath("queryResourceByCode.action"),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var entityDetail = json.resourceVo.resourceEntityDetail;
			var model = Ext.create('Foss.baseinfo.resource.ResourceModel', entityDetail);
			updateForm.getForm().loadRecord(model);
			updateForm.getForm().findField('belongSystemType').setReadOnly(true);
			if(entityDetail.parentRes){
				updateForm.getForm().findField('parentResCode').setCombValue(entityDetail.parentRes.name, entityDetail.parentRes.code);
			}else{
				updateForm.getForm().findField('parentResCode').setCombValue(entityDetail.parentResName, entityDetail.parentResCode);
			}
		}
	});
	baseinfo.resource.updateResourceWindow.getResourceUpdateForm().operatorType=baseinfo.resource.OP_TYPE_UPDATE;
	baseinfo.resource.updateResourceWindow.show();
}


/**
 * 保存新增操作
 */
baseinfo.resource.saveAddOperator = function(me){
	var updateForm = baseinfo.resource.updateResourceWindow.getResourceUpdateForm();
	if(!updateForm.getForm().isValid()){
		Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),baseinfo.resource.i18n('foss.baseinfo.plsCheckCondition'));
		return false;
	}
	// 当前表单中的数据：
	var formValue = updateForm.getForm().getValues(); 
	var selectTree=baseinfo.resource.getSelectTree();
	//树结点数据
	var treeStore = selectTree.getTreeStore();
	//被操作的结点的上级
	var parentNode= treeStore.getNodeById(formValue.parentResCode);
	if(formValue.parentResCode==null || formValue.parentResCode==''){
		parentNode=treeStore.getRootNode();
	}	  
	var currNode = treeStore.getNodeById(formValue.code);
	// 判断请求的合法性
	var resType= formValue.resType;
	var parent = baseinfo.resource.queryResourceByCode(parentNode.data.id);
	if(parent){
		// 应用子系统下级只能有 子系统模块
		if(parent.resType=='1'){
			if(resType != '2'){
				Ext.Msg.alert("温馨提示",parent.name+"是应用系统 下级只能有 系统模块");
				return;
			}
		}
		// 子系统模块 下级只能有 子系统模块或者模块功能
		if(parent.resType=='2'){
			if(resType!='2' && resType != '3'){
				Ext.Msg.alert("温馨提示", parent.name+"是系统模块 下级只能有系统模块或者功能模块");
				return;
			}
		}
		//  模块功能 下级只能有 页面元素
		if(parent.resType=='3'){
			if(resType != '4'){
				Ext.Msg.alert("温馨提示", parent.name+"是功能 下级只能有 页面元素");
				return;
			}
		}
		//  模块功能 下级只能有 页面元素
		if(parent.resType=='4'){
			Ext.Msg.alert("温馨提示", parent.name + "是页面元素,不能作为权限的上级");
			return;
		}
	}
	//发送保存新结点的Ajax请求.
	var resourceVo = new Object();
	var model = Ext.create('Foss.baseinfo.resource.ResourceModel', formValue);
	resourceVo.isAdd = true;
	resourceVo.resourceEntityDetail = model.data;
	resourceVo.resourceEntityDetail.parentRes = new Object();
	resourceVo.resourceEntityDetail.parentRes.code = parent.code;
	resourceVo.resourceEntityDetail.parentRes.name = parent.name; 
	
	var params = {'resourceVo':resourceVo};
    Ext.Ajax.request({
        url : baseinfo.realPath("saveResource.action"),
        jsonData:params,
        success : function(response) {
          	var json = Ext.decode(response.responseText);
			//将被添加的节点插入到树型结构中：
			var addedNode = {
			    'id':formValue.code,
			    'parentId':formValue.parentResCode,
			    "text": formValue.name,
			    'expanded': true,
			    "leaf": formValue.leafFlag == 'Y' ? true : false
			};
			if(parentNode!=null){
				var length = parentNode.childNodes.length;
				parentNode.insertChild(length, addedNode);
				parentNode.lastChild.raw = addedNode; 		
				parentNode.set('loaded', true);	
			}
			//刷新树结点：
			if(!currNode){
				currNode = treeStore.getNodeById(formValue.code);
			}
			currNode.data.expanded ? currNode.collapse() : currNode.expand();
			currNode.set('loaded', true);	 
			updateForm.getForm().reset();		
			Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),baseinfo.resource.i18n('foss.baseinfo.saveSuccess'));
			baseinfo.resource.updateResourceWindow.hide();
        },
        exception : function(response) {
        	var json = Ext.decode(response.responseText); 
        	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),json.message);
        }
    }); 
}

/**
 * 保存修改操作
 */
baseinfo.resource.saveUpdateOperator = function(me){

	var updateForm = baseinfo.resource.updateResourceWindow.getResourceUpdateForm();
	// 当前表单中的数据：
	var formValue = updateForm.getForm().getValues(); 
	var selectTree=baseinfo.resource.getSelectTree();
	//树结点数据
	var treeStore = selectTree.getTreeStore();
	//被操作的结点的上级
	var parentNode= treeStore.getNodeById(formValue.parentResCode);
	if(formValue.parentResCode==null || formValue.parentResCode==''){
		parentNode=treeStore.getRootNode();
	}	  
	var currNode = treeStore.getNodeById(formValue.code);
	// 判断请求的合法性
	var resType= formValue.resType;
	var parent = baseinfo.resource.queryResourceByCode(parentNode.data.id);
	if(parent){
		// 应用子系统下级只能有 子系统模块
		if(parent.resType=='1'){
			if(resType != '2'){
				Ext.Msg.alert("温馨提示",parent.name+"是应用系统 下级只能有 系统模块");
				return;
			}
		}
		// 子系统模块 下级只能有 子系统模块或者模块功能
		if(parent.resType=='2'){
			if(resType!='2' && resType != '3'){
				Ext.Msg.alert("温馨提示", parent.name+"是系统模块 下级只能有系统模块或者功能模块");
				return;
			}
		}
		//  模块功能 下级只能有 页面元素
		if(parent.resType=='3'){
			if(resType != '4'){
				Ext.Msg.alert("温馨提示", parent.name+"是功能 下级只能有 页面元素");
				return;
			}
		}
		//  模块功能 下级只能有 页面元素
		if(parent.resType=='4'){
			Ext.Msg.alert("温馨提示", parent.name + "是页面元素,不能作为权限的上级");
			return;
		}
	}
	//发送保存新结点的Ajax请求.
	var resourceVo = new Object();
	var model = Ext.create('Foss.baseinfo.resource.ResourceModel', formValue);
	resourceVo.isAdd = false;
	resourceVo.resourceEntityDetail = model.data;
	resourceVo.resourceEntityDetail.parentRes = new Object();
	resourceVo.resourceEntityDetail.parentRes.code = parent.code;
	resourceVo.resourceEntityDetail.parentRes.name = parent.name; 
	
	var params = {'resourceVo':resourceVo};
    Ext.Ajax.request({
        url : baseinfo.realPath("saveResource.action"),
        jsonData:params,
        success : function(response) {
          	var json = Ext.decode(response.responseText);
			// 更新则先删除当前节点，再新增一个节点 
          	baseinfo.resource.treeNodeOperator(null,currNode);
			if(currNode){
				currNode.remove();
			}  
			//将被添加的节点插入到树型结构中：
			var addedNode = {
			    'id':formValue.code,
			    'parentId':formValue.parentResCode,
			    "text": formValue.name,
			    'expanded': true,
			    "leaf": formValue.leafFlag == 'Y' ? true : false
			};
			if(parentNode!=null){
				var length = parentNode.childNodes.length;
				parentNode.insertChild(length, addedNode);
				parentNode.lastChild.raw = addedNode; 		
				parentNode.set('loaded', true);	
			}
			baseinfo.resource.search(formValue.name);
			Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),baseinfo.resource.i18n('foss.baseinfo.saveSuccess'));
			updateForm.getForm().reset();				
			baseinfo.resource.updateResourceWindow.hide();
        },
        exception : function(response) {
        	var json = Ext.decode(response.responseText);
        	Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),json.message);
        }
    }); 
}

/**
 * 取消操作
 */
baseinfo.resource.cancel = function(){
	baseinfo.resource.updateResourceWindow.hide();
}
/**
 * ==============================================================================
 * 菜单树结构模块：
 */
Ext.define('Foss.baseinfo.resource.ResourceModel', {
    extend: 'Ext.data.Model',
    fields: [
      // 权限编码
      {name: 'code', type: 'string'},
      // 权限名称
      {name: 'name', type: 'string'},
      // 权限入口URI
      {name: 'entryUri', type: 'string'},
      // 功能层次
      {name: 'resLevel', type: 'string'},
      // 上级权限-编码
      {name: 'parentResCode', type: 'string'},
      // 上级权限-名称
      {name: 'parentResName', type: 'string'},
      // 显示顺序
      {name: 'displayOrder', type: 'string'},
      // 是否权限检查
      {name: 'checked', type: 'string'},
      // 权限类型
      {name: 'resType', type: 'string'},
      // 是否叶子结点
      {name: 'leafFlag', type: 'string'},
      // 图标的CSS样式
      {name: 'iconCls', type: 'string'},
      // 节点的CSS样式
      {name: 'cls', type: 'string'},
      // 权限描述
      {name: 'notes', type: 'string'},
      // 权限所属系统类型
      {name: 'belongSystemType', type: 'string'}
      
    ]
});

//定义 web菜单treeStore
Ext.define('Foss.baseinfo.resource.WebResourceTreeStore',{
    extend: 'Ext.data.TreeStore', 
    root: {
        text:baseinfo.resource.i18n('foss.baseinfo.appSystem'),
        id : '0',
        expanded: true
    },
    //设置一个代理，通过读取内存数据
    proxy: {
        type : 'ajax',
        actionMethods : 'POST', 
        url : baseinfo.realPath("queryResourceByParentResShow.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
    },
    nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
});

//定义 gui菜单treeStore
Ext.define('Foss.baseinfo.resource.GuiResourceTreeStore',{
    extend: 'Ext.data.TreeStore', 
    root: {
        text:'GUI',
        id : 'GUI',
        expanded: true
    },
    
    //设置一个代理，通过读取内存数据
    proxy: {
        type : 'ajax',
        actionMethods : 'POST', 
        url : baseinfo.realPath("queryResourceByParentResShow.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
    },
    nodeParam: 'resourceVo.resourceEntityDetail.parentRes.code'
});
//定义web"权限树型结构"
Ext.define('Foss.baseinfo.resource.WebResourceTree',{ 
   extend:'Ext.tree.Panel',
   margin: false,
   id:'WEB_RESOURCE_TREE',
   autoScroll: true,
   animate: false,
   useArrows: true,
   frame: false,
   rootVisible: true,
   columnWidth: 1,
   height: 630,
   treeStore:null,
   getTreeStore:function(){
	 if(Ext.isEmpty(this.treeStore)){
		 this.treeStore = Ext.create('Foss.baseinfo.resource.WebResourceTreeStore');
	 }
	 return this.treeStore;
   }, 
   defaults: {
       margin:'5 5 5 5'
   },
   constructor: function(config){
       var me = this,
           cfg = Ext.apply({}, config);

       //监听鼠标事件
       me.listeners = {
           //左键单击
           itemclick:function(node,record,item,index,e){
               //阻止浏览器默认行为处理事件
               e.preventDefault();
               // 处理点击树结点事件：
               baseinfo.resource.treeNodeOperator(me,record);
           },
           //右键单击
           itemcontextmenu:function(node,record,item,index,e){
               e.preventDefault();
           } 
       };
       me.store = me.getTreeStore();
       me.callParent([cfg]);
   }
});    
 
//定义gui"权限树型结构"
Ext.define('Foss.baseinfo.resource.GuiResourceTree',{ 
 extend:'Ext.tree.Panel',
 margin: false,
 id:'GUI_RESOURCE_TREE',
 autoScroll: true,
 animate: false,
 useArrows: true,
 frame: false,
 rootVisible: true,
 columnWidth: 1,
 height: 630,
 treeStore:null,
 getTreeStore:function(){
	 if(Ext.isEmpty(this.treeStore)){
		 this.treeStore = Ext.create('Foss.baseinfo.resource.GuiResourceTreeStore');
	 }
	 return this.treeStore;
 },  
 defaults: {
     margin:'5 5 5 5'
 },
 constructor: function(config){
     var me = this,
         cfg = Ext.apply({}, config); 
     //监听鼠标事件
     me.listeners = {
         //左键单击
         itemclick:function(node,record,item,index,e){
             //阻止浏览器默认行为处理事件
             e.preventDefault();
             // 处理点击树结点事件：
             baseinfo.resource.treeNodeOperator(me,record);
         },
         //右键单击
         itemcontextmenu:function(node,record,item,index,e){
             e.preventDefault();
         }
     };
     me.store = me.getTreeStore();
     me.callParent([cfg]);
 }
});    
 
/**
 * ===============================================================================
 * 下面是大框架面板
 */
/*******左面板*****************************************/
//定义"权限修改界面-权限详情的展示"
Ext.define('Foss.baseinfo.resource.ResourceLeft',{
	extend: 'Ext.panel.Panel', 
	frame: true, 
	width : 300,
	height : 810,
	autoScroll :false,
	defaultType : 'textfield',
	layout:'column', 
	defaults: {
		readOnly : true, 
		anchor: '90%',
		columnWidth: 0.99
	},
	searchMeunForm:null,
	getSearchMenuForm:function(){
		if(Ext.isEmpty(this.searchMeunForm)){
			this.searchMeunForm=Ext.create('Foss.baseinfo.resource.QueryMenuForm');
		}
		return this.searchMeunForm;
	},
	resourceMenuTab:null,
	getResourceMenuTab:function(){
		var me=this;
		if(Ext.isEmpty(me.resourceMenuTab)){
			me.resourceMenuTab= Ext.create('Foss.baseinfo.resource.ResourceMenuTab');
		}
		return me.resourceMenuTab;
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [me.getSearchMenuForm(),me.getResourceMenuTab()];
	    me.callParent([cfg]);
	}
});

//菜单查询Form 
Ext.define('Foss.baseinfo.resource.QueryMenuForm',{
	extend:'Ext.form.Panel', 
	frame:true,
	collapsible:false,
	animcollapse:true,
	columnWidth:1,
	defaults:{
		margin : '5 5 0 0',
		labelWidth : 85,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	},	
  constructor: function(config){
  		var me = this,cfg = Ext.apply({}, config);
  		me.items=[{
					xtype : 'textfield', 
					name:'name', 
					fieldLabel:baseinfo.resource.i18n('foss.baseinfo.resourceName'),
					colspan:2
				  },
				  {
	  				border: 1,
	  				xtype:'container',
	  				height:40,
	  				colspan:2,
	  				defaultType:'button',
	  				layout:'column',
	  				items:[{
	  					  text:baseinfo.resource.i18n('foss.baseinfo.vehicle.resetBtn'),
	  					  disabled:!baseinfo.resource.isPermission('resourceindex/competenceQueryButton'),
	  					  hidden:!baseinfo.resource.isPermission('resourceindex/competenceQueryButton'),
	  					  columnWidth : .30,
	  					  handler:function(){  
	  						baseinfo.resource.reset(me);
	  		  		  		}
	  				  	},{
	  						xtype:'container',
	  						border:false,
	  						html:'&nbsp;',
	  						columnWidth:.4
	  					},
	  				  	{
	  						text:baseinfo.resource.i18n('foss.baseinfo.vehicle.queryBtn'),
	  						disabled:!baseinfo.resource.isPermission('resourceindex/competenceQueryButton'),
	  						hidden:!baseinfo.resource.isPermission('resourceindex/competenceQueryButton'),
	  						cls:'yellow_button', 
	  						columnWidth : .30,
	  						handler: function() {
	  							var form = this.up('form').getForm();
	  							var searchName=form.findField('name').getValue();
	  							if(Ext.isEmpty(searchName)){ 
	  								Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'), baseinfo.resource.i18n('foss.baseinfo.plsInputParam')); 
	  								return ;
	  							}
	  							baseinfo.resource.search(null);
	  						}
	  				  	}]
	  				}];
  		me.callParent([cfg]);
      }
});

//菜单TAB
Ext.define('Foss.baseinfo.resource.ResourceMenuTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	columnHeight: 'autoHeight', 
	webMenuTreeItem:null,
	getWebMenuTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.webMenuTreeItem)){
			me.webMenuTreeItem = Ext.create('Foss.baseinfo.resource.WebResourceTree');
		}
		return me.webMenuTreeItem;
	},
	guiMenuTreeItem:null,
	getGuiMenuTreeItem:function(){
		var me=this;
		if(Ext.isEmpty(me.guiMenuTreeItem)){
			me.guiMenuTreeItem= Ext.create('Foss.baseinfo.resource.GuiResourceTree')
		}
		return me.guiMenuTreeItem;
	},
	listeners: {
		tabchange : function(tabPanel,newCard,oldCard,eOpts ){
			if(baseinfo.resource.MENU_TYPE_GUI == newCard.getItemId()){
				baseinfo.resource.search(null);
			}
		 }
	}, 
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [ 
				    {
						title : baseinfo.resource.i18n('foss.baseinfo.webMenu'),
						itemId:'WEB',
						tabConfig : {
							width : 80
							},
						items : [
								 me.getWebMenuTreeItem()
						       ]
					},
					{
						title :  baseinfo.resource.i18n('foss.baseinfo.guiMenu'),
						itemId:'GUI',
						tabConfig : {
							width : 80
							},
						items : [
						         me.getGuiMenuTreeItem()
							   	]
					}
				 ]
	    me.callParent([cfg]);
	}
});
 
/*******右面板**********************************************/
//定义"权限修改界面-权限详情的展示"
Ext.define('Foss.baseinfo.resource.ResourceRight',{
	extend: 'Ext.panel.Panel', 
	frame: true,
	width : 700,
	height : 810,
	autoScroll :true,
	hidden: true,
	defaultType : 'textfield',
	layout:'column',
	rightTopButtonPannel:null,
	getRightTopButtonPannel:function(){
		var me=this;
		if(Ext.isEmpty(me.rightTopButtonPannel)){
			me.rightTopButtonPannel=Ext.create('Foss.baseinfo.resource.ResourceDetailButtonPanel');
		}
		return me.rightTopButtonPannel;
	},
	rightMiddleFormPannel:null,
	getRightMiddleFormPannel :function(){
		var me=this;
		if(Ext.isEmpty(me.rightMiddleFormPannel)){
			me.rightMiddleFormPannel=Ext.create('Foss.baseinfo.resource.ResourceDetailForm');
		}
		return me.rightMiddleFormPannel;
	},
	initComponent: function(config){
	  var me = this,
				cfg = Ext.apply({}, config);
	  me.items = [me.getRightTopButtonPannel(),me.getRightMiddleFormPannel()];
	  me.callParent([cfg]);
	}
}); 


//权限详情界面-按钮面板
Ext.define('Foss.baseinfo.resource.ResourceDetailButtonPanel',{
	extend:'Ext.form.Panel',  
	frame:false, 
	columnWidth: 1,   
	defaultType:'button', 
	layout:'column' ,
	defaults:{
		margin : '15 5 5 0', 
		colspan : 1 
	},  
	items:[{
		  text:baseinfo.resource.i18n('foss.baseinfo.void'), 
		  disabled:!baseinfo.resource.isPermission('resourceindex/competenceVoidButton'),
		  hidden:!baseinfo.resource.isPermission('resourceindex/competenceVoidButton'),
		  columnWidth:.2, 
		  handler: baseinfo.resource.cancelOperator
	  	},{
		  xtype:'container',  
		  columnWidth:.2
  		},{
		  text:baseinfo.resource.i18n('foss.baseinfo.add'),
		  disabled:!baseinfo.resource.isPermission('resourceindex/competenceAddButton'),
		  hidden:!baseinfo.resource.isPermission('resourceindex/competenceAddButton'),
		  columnWidth:.2, 
		  handler:baseinfo.resource.addOperator
	  	},{
		   xtype:'container',  
		   columnWidth:.2
	  	},{
		   text:baseinfo.resource.i18n('foss.baseinfo.update'), 
		   disabled:!baseinfo.resource.isPermission('resourceindex/competenceUpdateButton'),
		   hidden:!baseinfo.resource.isPermission('resourceindex/competenceUpdateButton'),
		   columnWidth:.2, 
		   handler:baseinfo.resource.updateOperator
	  	}] 
});

//"权限-详情界面"
Ext.define('Foss.baseinfo.resource.ResourceDetailForm',{
	extend: 'Ext.form.Panel', 
	title: baseinfo.resource.i18n('foss.baseinfo.resourceDetail'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	columnWidth: 1, 
	layout:{
		type : 'table',
		columns : 2
	},	
	defaults: {
		readOnly : true, 
		margin:'5 20 5 10',
		labelWidth: 120  
	}, 
	items:[{
		name: 'code',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceCode')
	},{
		name: 'name',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceName') 
	},{
		name: 'entryUri',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceEntryUri')
	},{
		name: 'parentResCode',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.superiorResource'),
	    hidden: true 
	},{
		name: 'parentResName',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.superiorResource') 
	},{
		name: 'displayOrder',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.theOrderWhichTheyAppear') 
	},{
		name: 'resType',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceType'),
		margin:'5 20 5 10', 
		listeners: {  
			change : function(me, newValue, oldValue, eOpts){
				var a_value = FossDataDictionary.rendererSubmitToDisplay(newValue,'RESOURCE_TYPE');
				if(a_value){
					me.setValue(a_value);
				} 
				return ;
			}	
		}
	},{
		name: 'checked',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.isResourceCheck') ,
	    listeners: {	
			change: function(me, newValue, oldValue, eOpts ){
				if(newValue==baseinfo.resource.EN_YES){
					me.setValue(baseinfo.resource.CN_YES);
				}else if(newValue==baseinfo.resource.EN_NO){
					me.setValue(baseinfo.resource.CN_NO);
				}
				return;
			}
	    }
	},{
		name: 'leafFlag',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.isLeafNode'),
	    listeners: {	
			change: function(me, newValue, oldValue, eOpts ){
				if(newValue==baseinfo.resource.EN_YES){
					me.setValue(baseinfo.resource.CN_YES);
				}else if(newValue==baseinfo.resource.EN_NO){
					me.setValue(baseinfo.resource.CN_NO);
				}
				return;
			}
	    }
	},{
		name: 'belongSystemType',
		colspan : 2 ,
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.belongSystemType')  
	},{
		name: 'notes',
	    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceDiscribe') 
	}],
	initComponent: function(config){
		var me = this,cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//定义"权限-新增和修改界面"
Ext.define('Foss.baseinfo.resource.ResourceUpdateForm',{
	extend: 'Ext.form.Panel', 
	// title: '权限-新增和修改界面',
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	operatorType:null,  
	defaults:{
		margin : '5 5 5 0',
		labelWidth : 130,
		colspan : 1 
	},
	defaultType:'textfield',
	layout:{
		type : 'table',
		columns : 2
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items=[{
			name: 'code',
		    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceCode'),
		    allowBlank: false,
		    validateOnBlur: true,
		    validateOnChange: false,
			margin:'5 20 5 10',
		    maxLength:50, 
		    validator:function(value){
		    	if(Ext.isEmpty(value) || me.operatorType==baseinfo.resource.OP_TYPE_UPDATE){
		    		return true;
		    	}
		    	var params = {
		    			'resourceVo.resourceEntityDetail.code' : value
		    	}; 
		    	var resultFlag=false;
		        Ext.Ajax.request({
		            url : baseinfo.realPath("queryResourceExactByEntity.action"),
		            params: params,
					async: false,
		            success : function(response) {
		                var json = Ext.decode(response.responseText);
		                if(json){
		                	var entity=json.resourceVo.resourceEntityDetail;
			                if(entity && !Ext.isEmpty(entity.code)){
			                	 //var tipStr ='权限已经存在！<br>'+value+" 对应的名称是："+entity.name;
			                	var tipStr =baseinfo.resource.i18n('foss.baseinfo.resourceHasExist')+value+baseinfo.resource.i18n('foss.baseinfo.withNameIs')+entity.name;
			                	 Ext.MessageBox.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),tipStr);
			                	 resultFlag = false;
			                }else{
			                	resultFlag = true;			                	
			                }
		                }
		            },
		            exception : function(response) {
		                var json = Ext.decode(response.responseText);
		                Ext.Msg.alert(baseinfo.resource.i18n('foss.baseinfo.tips'),json.message);
		    			resultFlag = false;
		            }
		        });		
		        return resultFlag;
		    }
		    
		},{
			name: 'name',
		    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceName') ,//'权限名称'
		    allowBlank:false,
			margin:'5 20 5 10',
		    maxLength:100 
		},{
			name: 'entryUri',
		    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceEntryUri'), // '权限入口URI',
		    allowBlank:	false,
		    validateOnBlur:	true,
		    validateOnChange: false,
			margin:'5 20 5 10',
		    maxLength:500 
		},{
			xtype : 'commonresourceselector', 
			name:'parentResCode',
			valueField : 'code',
			allowBlank : false,
			fieldLabel : baseinfo.resource.i18n('foss.baseinfo.superiorResource'),//'上级权限', 
			margin:'5 20 5 10' 
		},
		{   xtype: 'numberfield',
			name: 'displayOrder',
		    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.theOrderWhichTheyAppear'),//'显示顺序',
		    allowBlank:false,  
		    minValue: 0,
	        maxValue: 999999999,
			margin:'5 20 5 10'
		},
		FossDataDictionary.getDataDictionaryCombo('RESOURCE_TYPE',
			{
				//人员状态（状态, 1，应用子系统；2，子系统模块；3,模块功能；4，页面元素）
				fieldLabel:baseinfo.resource.i18n('foss.baseinfo.resourceType'),//'权限类型',								
				name: 'resType',
		    	//labelSeparator:'',
				labelWidth : 130,
				margin:'5 20 5 10'
			}
		),{
			xtype: 'checkboxfield',
			name: 'checked',
			boxLabel: baseinfo.resource.i18n('foss.baseinfo.isResourceCheck'),//'是否权限检查',
		    inputValue: 'Y',
		    checked:true,
			margin:'5 20 5 10' 
		},{
			xtype:'checkboxfield',
			name: 'leafFlag',
			boxLabel: baseinfo.resource.i18n('foss.baseinfo.isLeafNode'),//'是否叶子结点',
		    inputValue: 'Y',
		    checked:true,
			margin:'5 20 5 10' 
		},{
	 		xtype:'radiogroup',
	 		fieldLabel:baseinfo.resource.i18n('foss.baseinfo.belongSystemType'),//权限所属系统类型
	 		name:'belongSystemType',  
	 		allowBlank:false,
	 		margin:'5 20 5 10',
	 		defaultType:'radio',
	 		layout:'table',
	 		colspan:2,
	 		isFormField: true,
	 		items:[{
	 			boxLabel:'WEB',
	 			name:'belongSystemType',
	 			inputValue:baseinfo.resource.MENU_TYPE_WEB,
	 			margin:'5 10 5 2'
	 		},{
	 			boxLabel:'GUI',
	 			name:'belongSystemType',
	 			margin:'5 10 5 2',
	 			inputValue:baseinfo.resource.MENU_TYPE_GUI 
	 		}]
	  	 },{
			name: 'notes',
		    fieldLabel: baseinfo.resource.i18n('foss.baseinfo.resourceDiscribe'),//权限描述
			margin:'5 20 5 10',
			colspan:2,
		    maxLength:1000,
		    width : 600
		},{
			border: 1,
			xtype:'container',
			colspan:2,
			defaultType:'button',
			columnWidth:1,
			margin:'15 0 15 0',
			layout:'column',
			items:[{
				  text:baseinfo.resource.i18n('foss.baseinfo.cancel'),
				  columnWidth : .2,
				  handler: function(){ 
					  baseinfo.resource.updateResourceWindow.hide();
				   }
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.2
				},{
					text:baseinfo.resource.i18n('foss.baseinfo.reset'),
					cls:'yellow_button', 
					columnWidth : .2,
					handler: function(){
						baseinfo.resource.reset(me);
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.2
				},{
					text:baseinfo.resource.i18n('foss.baseinfo.save'),
					cls:'yellow_button', 
					columnWidth : .2,
					handler: function(){
						if(baseinfo.resource.OP_TYPE_ADD==me.operatorType){
							baseinfo.resource.saveAddOperator(me);
						}else if(baseinfo.resource.OP_TYPE_UPDATE==me.operatorType){
							baseinfo.resource.saveUpdateOperator(me);
						}
					}
			  	}]
			}
	];
	me.callParent([cfg]);
}
}); 

//定义"菜单修改窗口界面"
Ext.define('Foss.baseinfo.resource.UpdateResourceWindow',{
	extend: 'Ext.window.Window', 
	title: baseinfo.resource.i18n('foss.baseinfo.updateOrAddResource'),
	width: 700,
	height: 330,
	closeAction:'hide',
	layout : 'column',
	columnWidth:1,
	operatorType:null, 
	resourceUpdateForm:null,
	getResourceUpdateForm:function(){
		if(Ext.isEmpty(this.resourceUpdateForm)){
			this.resourceUpdateForm=Ext.create('Foss.baseinfo.resource.ResourceUpdateForm');
		}
		return this.resourceUpdateForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getResourceUpdateForm()];
		me.callParent([cfg]);
	}
});
baseinfo.resource.updateResourceWindow=Ext.create('Foss.baseinfo.resource.UpdateResourceWindow');  

/**
 * 程序启动的初始化方法：
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-resourceindex_content')) {
		return;
	};
	var resourceLeft = Ext.create('Foss.baseinfo.resource.ResourceLeft');
	var resourceRight = Ext.create('Foss.baseinfo.resource.ResourceRight');
	
	Ext.getCmp('T_baseinfo-resourceindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-resourceindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		getResourceLeft : function() {
			return resourceLeft;
		},
		getResourceRight : function() {
			return resourceRight;
		},
		items: [resourceLeft,resourceRight] 
	}));
});