//转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
baseinfo.siteGroup.siteGroupType = 'SITE_GROUP_TYPE';//数据字典
//--------------------------------------baseinfo----------------------------------------
//站点model
Ext.define('Foss.baseinfo.SiteGroupSiteEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'seq',//序号
        type : 'int'
    },{
        name : 'site',//站点名称
        type : 'string'
    },{
        name : 'siteCode',//站点编码
        type : 'string'
    },{
        name : 'parentOrgCode',//所属站点组
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'orginalOrganizationName',//站点名称
        type : 'string'
    }]
});
//站点组model
Ext.define('Foss.baseinfo.SiteGroupEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'name',//站点组名称
        type : 'string'
    },{
        name : 'orgCode',//所属部门
        type : 'string'
    },{
        name : 'orgName',//所属部门名称
        type : 'string'
    },{
        name : 'type',//站点组类型
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'notes',//备注
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
    	name : 'siteGroupSiteEntityList',//所拥有的站点
    	defaultValue:[]
    }]
});
//外场model
Ext.define('Foss.baseinfo.OrgAdministrativeInfoEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'name',//部门名称
        type : 'string'
    },{
        name : 'code',//部门标杆编码
        type : 'string'
    }]
});

//选择站点组站点model
Ext.define('Foss.baseinfo.siteGroup.GroupSitesModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'name',//部门名称
        type : 'string'
    },{
        name : 'code',//部门编码
        type : 'string'
    }]
});


//创建选择站点组站点的store
Ext.define('Foss.baseinfo.siteGroup.GroupSitesStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.siteGroup.GroupSitesModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//------------------------------------model---------------------------------------------------
/**
 * 站点组Store（Foss.baseinfo.SiteGroupEntity）
 */
Ext.define('Foss.baseinfo.SiteGroupStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.SiteGroupEntity',//线路的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../baseinfo/searchSiteGroupByCondition.action",//请求地址
		reader : {
			type : 'json',
			root : 'siteGroupVo.siteGroupEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});
/**
 * 外场tore（Foss.baseinfo.OrgAdministrativeInfoEntity）
 */
Ext.define('Foss.baseinfo.OrgAdministrativeStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.OrgAdministrativeInfoEntity',//部门的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../baseinfo/searchOrgAdministrativeByCondition.action",//请求地址
		reader : {
			type : 'json',
			root : 'siteGroupVo.orgAdministrativeInfoEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};

/**
 * 站点组表单
 */
Ext.define('Foss.baseinfo.QuerySiteGroupForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.siteGroup.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :150,
	defaultType : 'textfield',
	layout:'column',
	items :[{
		name:'name',
        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupName'),//站点组名称
        xtype : 'textfield'
	},{
		name: 'type',
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    value:'',
	    store:baseinfo.getStore(null,null,['valueName','valueCode']
	    ,[{'valueName':'全部','valueCode':''},{'valueName':baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.arrive'),'valueCode':'DD'},{'valueName':baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.leave'),'valueCode':'CF'}]),
        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupType'),//站点组类型
        xtype : 'combo'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.siteGroup.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupQueryButton'),
			hidden:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : baseinfo.siteGroup.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupQueryButton'),
			hidden:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getSiteGroupGrid().getPagingToolbar().moveFirst();
				}
				
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 站点组列表
 */
Ext.define('Foss.baseinfo.SiteGroupGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.lineInfo'),//线路信息
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.siteGroup.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//站点组新增WINDOW
	siteGroupAddWindow:null,
	getSiteGroupAddWindow:function(){
		if (this.siteGroupAddWindow == null) {
			this.siteGroupAddWindow = Ext.create('Foss.baseinfo.SiteGroupAddWindow');
			this.siteGroupAddWindow.parent = this;//父元素
		}
		return this.siteGroupAddWindow;
	},
	//修改站点组WINDOW
	siteGroupUpdateWindow:null,
	getSiteGroupUpdateWindow:function(){
		if (this.siteGroupUpdateWindow == null) {
			this.siteGroupUpdateWindow = Ext.create('Foss.baseinfo.SiteGroupUpdateWindow');
			this.siteGroupUpdateWindow.parent = this;//父元素
		}
		return this.siteGroupUpdateWindow;
	},
	//作废站点组
	toVoidSiteGroup: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.siteGroup.i18n('foss.baseinfo.isDeleteSiteGroups'),function(e){//是否要作废这些站点组？
			if(e=='yes'){//询问是否删除，是则发送请求
				var codeStr = '';//站点组虚拟CODE组成的字符串
				for(var i = 0 ; i<selections.length ; i++){
					codeStr = codeStr +','+ selections[i].get('virtualCode');
				}
				var params = {'siteGroupVo':{'codeStr':codeStr}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteSiteGroup.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
		
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : '序号'//序号
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.operate'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.siteGroup.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var siteGroupVirtualCode = record.get('virtualCode');//站点组虚拟编码
    				var params = {'siteGroupVo':{'siteGroupVirtualCode':siteGroupVirtualCode}};
    				var successFun = function(json){
    					var updateWindow = me.getSiteGroupUpdateWindow();//获得修改窗口
    					updateWindow.siteGroupEntity = json.siteGroupVo.siteGroupEntity;//站点组
    					updateWindow.siteGroupSiteEntityList = json.siteGroupVo.siteGroupSiteEntityList;//站点
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时');//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('saerchSiteGroupInfo.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.siteGroup.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupVoidButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.siteGroup.i18n('foss.baseinfo.isDeleteSiteGroup'),function(e){//是否要作废这个站点组？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var codeStr = record.get('virtualCode');//站点组虚拟CODE组成的字符串
            				var params = {'siteGroupVo':{'codeStr':codeStr}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes('请求超时');//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteSiteGroup.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupName'),//站点组名称
			dataIndex : 'name',
			width:150
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupType'),//站点组名称
			dataIndex : 'type',
			renderer:function(value){
				if(value == 'DD'){
					return baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.arrive');
				}else if(value == 'CF'){
					return baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.leave');
				}else{
					return;
				}
			},
			width:150
		},{
			text :'<span style="padding:0 50px 0 50px;">序号 </span><span style="padding:0 50px 0 50px;">站点 </span>',//序号--站点
			width:580,
			dataIndex : 'siteGroupSiteEntityList',
			renderer:function(value,obj,record){
				var returnValue = '<table border="1" width="570px">';
				for(var i=0;i<value.length;i++){
					returnValue = returnValue+'<tr>'+'<td width="150px">'+value[i].seq
					+'</td>'+'<td width="420px">'+value[i].site+'</td>'+'</tr>';
				}
				returnValue = returnValue+'</table>';
				return returnValue;
			}
		}];
		me.store = Ext.create('Foss.baseinfo.SiteGroupStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//查询站点组大查询，查询条件组织
								'siteGroupVo.siteGroupEntity.name':queryForm.getForm().findField('name').getValue(),
								'siteGroupVo.siteGroupEntity.type':queryForm.getForm().findField('type').getValue()
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [{
        	xtype : 'button', 
			text : baseinfo.siteGroup.i18n('foss.baseinfo.addCfSiteGroup'),//新增出发组
			disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupAddStartButton'),
			hidden:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupAddStartButton'),
			handler : function() {
				//获得当前用户所在部门
				var dept = FossUserContext.getCurrentUserDept();
	    		var params = {'siteGroupVo':{'deptCode':dept.code}};//组织新增数据
	    		var successFun = function(json){
					me.getSiteGroupAddWindow().show();
					
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);//提示失败原因
						me.getSiteGroupAddWindow().close();
					}
				};
				var url=null;
				url = baseinfo.realPath('searchUpOrgAdministrativeByCondition.action');//请求站点组新增
				//me.getSiteGroupAddWindow().show();
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				me.getSiteGroupAddWindow().getSiteGroupForm().getForm().findField('type').setValue('CF');
				me.getSiteGroupAddWindow().getSiteGroupForm().siteGroupType = 'CF';
			}
        },'-',{
        	xtype : 'button', 
			text : baseinfo.siteGroup.i18n('foss.baseinfo.addDdSiteGroup'),//新增到达组
			disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupAddReachButton'),
			hidden:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupAddReachButton'),
			handler : function() {
				//获得当前用户所在部门
				var dept = FossUserContext.getCurrentUserDept();
	    		var params = {'siteGroupVo':{'deptCode':dept.code}};//组织新增数据
	    		var successFun = function(json){
					
					me.getSiteGroupAddWindow().show();
					
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);//提示失败原因
						me.getSiteGroupAddWindow().close();
					}
				};
				var url=null;
				url = baseinfo.realPath('searchUpOrgAdministrativeByCondition.action');//请求站点组新增
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
				//me.getSiteGroupAddWindow().show();
				me.getSiteGroupAddWindow().getSiteGroupForm().getForm().findField('type').setValue('DD');
				me.getSiteGroupAddWindow().getSiteGroupForm().siteGroupType = 'DD';
			}
        },'-',{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupVoidButton'),
			hidden:!baseinfo.siteGroup.isPermission('siteGroup/siteGroupVoidButton'),
			handler :function(){
				me.toVoidSiteGroup();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * @description 站点组主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-siteGroup_content')) {
		return;
	};
	var queryForm = Ext.create('Foss.baseinfo.QuerySiteGroupForm');//查询FORM
	var siteGroupGrid = Ext.create('Foss.baseinfo.SiteGroupGrid');//查询结果GRID
	Ext.getCmp('T_baseinfo-siteGroup').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-siteGroup_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getSiteGroupGrid : function() {
			return siteGroupGrid;
		},
		items : [queryForm, siteGroupGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增站点组信息
 */
Ext.define('Foss.baseinfo.SiteGroupAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.addSiteGroup'),//新增站点组
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.SiteGroupGrid）
	modal : true,
	siteGroupEntity:null,//保存的线路实体
	resizable:false,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :800,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getSiteGroupForm().getForm().reset();//表格重置
			me.getSiteGroupStieGrid().getStore().removeAll();//清除站点表
			me.parent.getPagingToolbar().moveFirst();
			//把设置为不可编辑的该为可编辑
			me.getSiteGroupForm().getForm().getFields( ).each(function(item,index,length){
				if(item.getName( )=='orgName'||item.getName( )=='type'){
					//所属部门、站点类型是只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});//将FORM设置为可用
			//设置为不可用的按钮改为可用
			me.getSiteGroupForm().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
			me.getSiteGroupForm().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
			//设置为可用的按钮改为不可用
			me.getSiteGroupStieGrid().getDockedItems()[0].items.items[0].setDisabled(true);//移除站点不可用
			me.getSiteGroupStieGrid().getDockedItems()[0].items.items[1].setDisabled(true);//选择站点按钮不可用
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			//获得当前用户所在部门
			var dept = FossUserContext.getCurrentUserDept();
    		var params = {'siteGroupVo':{'deptCode':dept.code}};//组织新增数据
    		var successFun = function(json){
				if(Ext.isEmpty(json.siteGroupVo)){
					baseinfo.showErrorMes(baseinfo.siteGroup.i18n('foss.baseinfo.abnormalServer'));//服务端有异常！
					return;
				}
				var deptName = json.siteGroupVo.deptName;
				var deptCode = json.siteGroupVo.deptCode;
				//当前部门所在的外场名称
				me.getSiteGroupForm().getForm().findField('orgName').setValue(deptName);
				//当前部门所在的部门编码
				me.getSiteGroupForm().getForm().findField('orgCode').setValue(deptCode);
				
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
					me.close();
				}
			};
			var url=null;
			url = baseinfo.realPath('searchUpOrgAdministrativeByCondition.action');//请求站点组新增
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
			
			
			
		}
	},
	//新增站点组FORM
	siteGroupForm:null,
    getSiteGroupForm : function(){
    	if(Ext.isEmpty(this.siteGroupForm)){
    		this.siteGroupForm = Ext.create('Foss.baseinfo.SiteGroupForm');
    		this.siteGroupForm.isUpdate = false;
    	}
    	return this.siteGroupForm;
    },
    //站点GRID
    siteGroupStieGrid:null,
    getSiteGroupStieGrid:function(){
    	if(Ext.isEmpty(this.siteGroupStieGrid)){
    		this.siteGroupStieGrid = Ext.create('Foss.baseinfo.SiteGroupStieGrid');
    	}
    	return this.siteGroupStieGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getSiteGroupForm(),me.getSiteGroupStieGrid()];
		me.callParent([cfg]);
	}
});
/**
 * 修改站点组信息
 */
Ext.define('Foss.baseinfo.SiteGroupUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.updateSiteGroup'),//修改站点组信息
	closable : true,
	modal : true,
	resizable:false,
	 parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.SiteGroupGrid）
	 siteGroupEntity:null,//修改的站点组信息
	 siteGroupSiteEntityList:null,//修改的站点数据集
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :800,
	height :700,
	listeners:{
		beforehide:function(me){
			me.getSiteGroupForm().getForm().reset();//表格重置
			me.getSiteGroupStieGrid().getStore().removeAll();//清除站点表
			me.parent.getPagingToolbar().moveFirst();
		},
		beforeshow:function(me){
			me.getSiteGroupForm().getForm().loadRecord(new Foss.baseinfo.SiteGroupEntity(me.siteGroupEntity));//加载站点组数据
			if(!Ext.isEmpty(me.siteGroupSiteEntityList)){
				me.getSiteGroupStieGrid().getStore().loadData(me.siteGroupSiteEntityList);//加载站点信息
			}
		}
	},
	//新增站点组FORM
	siteGroupForm:null,
    getSiteGroupForm : function(){
    	if(Ext.isEmpty(this.siteGroupForm)){
    		this.siteGroupForm = Ext.create('Foss.baseinfo.SiteGroupForm');
    		this.siteGroupForm.isUpdate = true;//表示修改
    	}
    	return this.siteGroupForm;
    },
    //站点GRID
    siteGroupStieGrid:null,
    getSiteGroupStieGrid:function(){
    	if(Ext.isEmpty(this.siteGroupStieGrid)){
    		this.siteGroupStieGrid = Ext.create('Foss.baseinfo.SiteGroupStieGrid');
    		this.siteGroupStieGrid.getDockedItems()[0].items.items[0].setDisabled(false);//移除站点按钮可用
    		this.siteGroupStieGrid.getDockedItems()[0].items.items[1].setDisabled(false);//选择站点按钮可用
    	}
    	return this.siteGroupStieGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getSiteGroupForm(),me.getSiteGroupStieGrid()];
		me.callParent([cfg]);
	}
});
/**
 * 站点组-FORM
 */
Ext.define('Foss.baseinfo.SiteGroupForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.siteGroup'),//站点组信息
	frame: true,
	isUpdate:false,//是否为修改，默认false
	siteGroupType:null,//站点组类型
	height:275,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	
    	labelWidth:110,
    	width:330,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    //提交线路数据
    commitLine:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var siteGroupEntity = null;
    		if(!Ext.isEmpty(me.up('window').siteGroupEntity)){
    			siteGroupEntity = new Foss.baseinfo.SiteGroupEntity(me.up('window').siteGroupEntity);
    		}else{
    			siteGroupEntity = Ext.create('Foss.baseinfo.SiteGroupEntity');//创建站点组MODEL
    		}
    		me.getForm().updateRecord(siteGroupEntity);//将FORM中数据设置到MODEL里面
    		var params = {'siteGroupVo':{'siteGroupEntity':siteGroupEntity.data}};//组织新增数据
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				if(Ext.isEmpty(json.siteGroupVo.siteGroupEntity)){
					baseinfo.showErrorMes(baseinfo.siteGroup.i18n('foss.baseinfo.abnormalServer'));//服务端有异常！
					return;
				}
				me.up('window').siteGroupEntity = json.siteGroupVo.siteGroupEntity;//将返回的值设置到window中
				if(me.isUpdate){//修改
					return;//返回，不做以下操作
				}else{//新增
					me.getForm().getFields( ).each(function(item,index,length){
						item.setReadOnly(true);//(对于numberfield的样式社会有问题)
					});//将FORM设置为不可用
					//me.doLayout( );
					me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
					me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
					me.up('window').getSiteGroupStieGrid().getDockedItems()[0].items.items[0].setDisabled(false);//移除站点可用
					me.up('window').getSiteGroupStieGrid().getDockedItems()[0].items.items[1].setDisabled(false);//选择站点按钮可用
				}
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateSiteGroup.action');//请求站点组修改
			}else{
				url = baseinfo.realPath('addSiteGroup.action');//请求站点组新增
			}
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :baseinfo.siteGroup.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.up().close();
			}
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				if(me.isUpdate){//如果是修改，加载上一次修改的
					me.getForm().loadRecord(new Foss.baseinfo.SiteGroupEntity(me.up('window').siteGroupEntity));
				}else{//如果是新增，直接reset
					me.getForm().reset();//表格重置
					me.getForm().findField('type').setValue(me.siteGroupType);
				}
			} 
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 485',
			handler :function(){
				me.commitLine();
			} 
		}];
		me.items = [{
			name: 'name',//站点组名称
			allowBlank:false,
	        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupName'),
	        maxLength:200,
	        xtype : 'textfield'
//	        listeners:{
//	        	change:function(item,newValue,oldValue){
//	        		if(me.isUpdate){//修改
//	        			//移除站点可用
//		        		me.up('window').getSiteGroupStieGrid().getDockedItems()[0].items.items[0].setDisabled(true);
//		        		//选择站点按钮可用
//						me.up('window').getSiteGroupStieGrid().getDockedItems()[0].items.items[1].setDisabled(true);
//	        		}
//	        	}
//	        }
		},{
			name: 'orgName',
	        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.customer.belongDept'),  //所属部门
	        readOnly : true,
	        allowBlank:true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
	        xtype: 'hiddenfield',
	        name: 'orgCode',
	        value: '',
	        hidden:true
	    },{
			name: 'type',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    readOnly:true,
		    store:baseinfo.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':'全部','valueCode':''},{'valueName':baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.arrive'),'valueCode':'DD'},{'valueName':baseinfo.siteGroup.i18n('foss.baseinfo.airagencycompany.leave'),'valueCode':'CF'}]),
	        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.siteGroupType'),//站点组类型
	        xtype : 'combo'
		},{
	        xtype : 'label'//占空间
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxLength:200,
	        width:450,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
/**
* 站点GRID
*/
Ext.define('Foss.baseinfo.SiteGroupStieGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	 //可编辑表格
    plugins:null,
    getPlugins:function(){
    	if(Ext.isEmpty(this.plugins)){
			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		        clicksToEdit: 1
		    });
			this.plugins = [cellEditing];
    	}
    	return this.plugins;
    },
    //选择站点WINDOW
    orgAdministrativeSelectWindow:null,
    getOrgAdministrativeSelectWindow:function(){
    	if(Ext.isEmpty(this.orgAdministrativeSelectWindow)){
    		this.orgAdministrativeSelectWindow = Ext.create('Foss.baseinfo.OrgAdministrativeSelectWindow');
    		this.orgAdministrativeSelectWindow.parent = this;
    	}
    	return this.orgAdministrativeSelectWindow;
    },
	columns : [{
		text : baseinfo.siteGroup.i18n('foss.baseinfo.operate'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items:[{
	        tooltip: baseinfo.siteGroup.i18n('foss.baseinfo.void'),//作废
			iconCls:'deppon_icons_delete',
			width:42,
	        handler: function(grid, rowIndex, colIndex) {
	        	var me = this;
        		//获取选中的数据
				var record=grid.getStore().getAt(rowIndex);
        		baseinfo.showQuestionMes(baseinfo.siteGroup.i18n('foss.baseinfo.isDeleteSite'),function(e){//是否要作废这个站点？
        			if(e=='yes'){//询问是否删除，是则发送请求
        				var codeStr = record.get('virtualCode');//站点虚拟CODE组成的字符串
        				var params = {'siteGroupVo':{'codeStr':codeStr}};
        				var successFun = function(json){
        					baseinfo.showInfoMes(json.message);
        					grid.getStore().remove(record);
        				};
        				var failureFun = function(json){
        					if(Ext.isEmpty(json)){
        						baseinfo.showErrorMes('请求超时');//请求超时
        					}else{
        						baseinfo.showErrorMes(json.message);
        					}
        				};
        				var url = baseinfo.realPath('deleteSiteGroupSite.action');
        				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
        			}
        		})
           }
	     }]
    },{
		text : '序号',//序号
		dataIndex : 'seq',
		flex:1,
		editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 99999999
	    }
	},{
		text : baseinfo.siteGroup.i18n('foss.baseinfo.groupSite'),//站点
		dataIndex : 'site',
		flex:1
	},{
		text : baseinfo.siteGroup.i18n('foss.baseinfo.groupSiteCode'),//站点CODE
		dataIndex : 'siteCode',
		hidden:true,
		flex:1
	}],
	//作废站点
	toVoidSiteGroupSite: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.siteGroup.i18n('foss.baseinfo.isDeleteSites'),function(e){//是否要作废这些站点？
			if(e=='yes'){//询问是否删除，是则发送请求
				var codeStr = '';//站点组虚拟CODE组成的字符串
				for(var i = 0 ; i<selections.length ; i++){
					codeStr = codeStr +','+ selections[i].get('virtualCode');
				}
				var params = {'siteGroupVo':{'codeStr':codeStr}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getStore().remove(selections);
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteSiteGroupSite.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
		
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = baseinfo.getStore(null,'Foss.baseinfo.SiteGroupSiteEntity',null,[]);
		me.store.on('update',function(store,record){
				var siteGroupSiteEntity = record.data;
				var params = {'siteGroupVo':{'siteGroupSiteEntity':siteGroupSiteEntity}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					store.remove(record);
					var siteGroupSiteModel = new Foss.baseinfo.SiteGroupSiteEntity(json.siteGroupVo.siteGroupSiteEntity);
					store.insert(0,siteGroupSiteModel);
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('updateSiteGroupSite.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
		});
		me.plugins=me.getPlugins();
		me.tbar = [{
			text :baseinfo.siteGroup.i18n('foss.baseinfo.removeGroupSite'),//移除站点
			disabled:true,
			handler :function(){
				me.toVoidSiteGroupSite();
			}
		},{
			text :baseinfo.siteGroup.i18n('foss.baseinfo.selectGroupSite'),//选择站点
			disabled:true,
			handler :function(){
				me.getOrgAdministrativeSelectWindow().show();
			} 
		}];
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.callParent([cfg]);
	}
});
/**
 * 选择站点
 */
Ext.define('Foss.baseinfo.OrgAdministrativeSelectWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.selectGroupSite'),//选择站点
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.SiteGroupStieGrid）
	modal : true,
	siteGroupEntity:null,//保存的线路实体
	resizable:false,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :800,
	height :700,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getQueryOrgAdministrativeForm().getForm().reset();//表格重置
			me.getOrgAdministrativeGrid().getStore().removeAll();//清除外场表
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//查询条件
	queryOrgAdministrativeForm:null,
    getQueryOrgAdministrativeForm : function(){
    	if(Ext.isEmpty(this.queryOrgAdministrativeForm)){
    		this.queryOrgAdministrativeForm = Ext.create('Foss.baseinfo.QueryOrgAdministrativeForm');
    	}
    	return this.queryOrgAdministrativeForm;
    },
    //外场GRID
    qrgAdministrativeGrid:null,
    getOrgAdministrativeGrid:function(){
    	if(Ext.isEmpty(this.qrgAdministrativeGrid)){
    		this.qrgAdministrativeGrid = Ext.create('Foss.baseinfo.OrgAdministrativeGrid');
    	}
    	return this.qrgAdministrativeGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getQueryOrgAdministrativeForm(),me.getOrgAdministrativeGrid()];
		me.callParent([cfg]);
	}
});
/**
 * 外场查询表单（选择站点列表查询条件）
 */
Ext.define('Foss.baseinfo.QueryOrgAdministrativeForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.siteGroup.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :150,
	defaultType : 'textfield',
	layout:'column',
	items :[{
		name:'name',
        fieldLabel: baseinfo.siteGroup.i18n('foss.baseinfo.groupSiteName'),//站点名称
        xtype : 'textfield'
	}],
	//消息提醒框
	showWarningMsg : function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.siteGroup.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 570',
			text : baseinfo.siteGroup.i18n('foss.baseinfo.query'),//查询
			handler : function() {
				var type = me.up('window').parent.up('window').siteGroupEntity.type;
				//获得站点组所在部门编码
				var orgCode = me.up('window').parent.up('window').siteGroupEntity.orgCode;
				//获取站点名称
				var siteName = me.getForm().findField('name').getValue();
				var url = null;
				if(type=='CF'){
					url = baseinfo.realPath('queryLeaveGroupSites.action');
				}else{
					url = baseinfo.realPath('queryArriveGroupSites.action');
				}
				
				var jsonData = {'siteGroupVo':{'deptCode':orgCode,'siteName':siteName}};
				//调用Ajax请求
				Ext.Ajax.request({
					url:url,
					jsonData:jsonData,
					//作废成功
					success : function(response) {
		                  var json = Ext.decode(response.responseText);
		                  //获取站点列表
		                  var infoList = json.siteGroupVo.mapDtoList;
		                  if(infoList == null){
		                	  infoList = {};
		                  }
		                  //加载数据
		                  me.up().getOrgAdministrativeGrid().getStore().loadData(infoList);
		                },
		            //保存失败
		            exception : function(response) {
		                  var json = Ext.decode(response.responseText);
		                  //打印作废失败消息
		                  me.showWarningMsg(baseinfo.siteGroup.i18n('foss.baseinfo.notice'),json.message);
		            }
				});
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 * 外场列表信息
 */
Ext.define('Foss.baseinfo.OrgAdministrativeGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.siteGroup.i18n('foss.baseinfo.groupSiteGrid'),//站点列表信息
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//选择外场
	selectDpeartment:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			baseinfo.showErrorMes(baseinfo.siteGroup.i18n('foss.baseinfo.selectOutField'));//请选择一个外场！
			return;
		}
		var stieGroupSiteArray = new Array();
		//
		var store = me.up('window').parent.getStore();
		var seq1 = 0;
		store.each(function(record){
			if(seq1<record.data.seq){
				seq1=record.data.seq;
			}
		});
		var seq = seq1+1
		var stieGroupVirtualCode = me.up('window').parent.up('window').siteGroupEntity.virtualCode;
		for(var i = 0;i<selections.length;i++){
			var stieGroupSite = {'parentOrgCode':stieGroupVirtualCode,'seq':seq+i,'siteCode':selections[i].get('code'),'site':selections[i].get('name')};
			stieGroupSiteArray.push(stieGroupSite);
		}
		var params = {'siteGroupVo':{'siteGroupSiteEntityList':stieGroupSiteArray}};
		var successFun = function(json){
			baseinfo.showInfoMes(json.message);
			var siteGroupSiteEntityList = new Array();
			for(var i=0;i<json.siteGroupVo.siteGroupSiteEntityList.length;i++){
				siteGroupSiteEntityList.push(new Foss.baseinfo.SiteGroupSiteEntity(json.siteGroupVo.siteGroupSiteEntityList[i]));
			}
			
			me.up('window').parent.getStore().add(siteGroupSiteEntityList);
			me.up('window').close();
		};
		var failureFun = function(json){
			if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
		};
		var url = baseinfo.realPath('addSiteGroupSiteList.action');
		baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : '序号'//序号
		},{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.groupSiteName'),//站点名称
			width:500,
			dataIndex : 'name'
		}];
		/*me.store = Ext.create('Foss.baseinfo.OrgAdministrativeStore',{
			autoLoad : false,//不自动加载
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryOrgAdministrativeForm();
					var codes = new Array();
					me.up('window').parent.getStore().each(function(record){
						if(!Ext.isEmpty(record.get('siteCode'))){
							codes.push(record.get('siteCode'));
						}
					});
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//查询外场组织数据
								'siteGroupVo.orgAdministrativeInfoEntity.name':queryForm.getForm().findField('name').getValue(),
								'siteGroupVo.orgAdministrativeInfoEntity.codes':codes
							}
						});	
					}
				}
		    }
		});*/
		me.store = Ext.create('Foss.baseinfo.siteGroup.GroupSitesStore',{
			listeners:{
				beforeload:function(store,operation,eOpts){
					//获取查询FORM
					var queryForm = me.up().getQueryOrgAdministrativeForm();
					var codes = new Array();
					me.up('window').parent.getStore().each(function(record){
						if(!Ext.isEmpty(record.get('siteCode'))){
							codes.push(record.get('siteCode'));
						}
					});
				}
			}
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
					mode:'MULTI',
					checkOnly:true
				});
		me.tbar = [{
			text : baseinfo.siteGroup.i18n('foss.baseinfo.confirm'),//确定
			handler :function(){
				me.selectDpeartment();
			} 
		},{
        	xtype : 'button', 
			text : baseinfo.siteGroup.i18n('foss.baseinfo.cancel'),//取消
			handler : function() {
				me.up('window').close();
			}
        }];
//		me.bbar = me.getPagingToolbar();
//		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

