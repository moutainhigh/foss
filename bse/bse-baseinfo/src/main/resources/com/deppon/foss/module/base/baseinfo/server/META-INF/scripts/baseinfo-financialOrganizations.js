baseinfo.financialOrganizations.FinancialOrganizationsRootCode = 'DBWL';//根节点CODE
baseinfo.financialOrganizations.FinancialOrganizationsRootName = baseinfo.financialOrganizations.i18n('foss.baseinfo.depponLogisticsIncorporatedCompany');//根节点名称
//转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
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
Ext.define('Foss.baseinfo.financialOrganizations.FinancialOrganizationsEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'name',//财务组织名称
        type : 'string'
    },{
        name : 'code',// 财务组织编码
        type : 'string'
    },{
        name : 'parentOrgCode',//上级组织编码
        type : 'string'
    },{
        name : 'parentOrgName',//上级组织名称
        type : 'string'
    },{
        name : 'costCenter',//是否成本中心
        type : 'string'
    },{
        name : 'subSidiary',//是否子公司
        type : 'string'
    },{
        name : 'fullName',//全称
        type : 'string'
    },{
        name : 'bankAccountName',//银行账号开户名
        type : 'string'
    },{
        name : 'bankAccount',// 银行账号
        type : 'string'
    },{
        name : 'bankAccountSubbranch',//银行账号开户支行
        type : 'string'
    },{
        name : 'active',// 是否启用
        type : 'string'
    },{
        name : 'notes',//备注
        type : 'string'
    },{
        name : 'fullPath',//全路径
        type : 'string'
    }]
});


/**
 * 定义功能树的store
 */
Ext.define('Foss.baseinfo.financialOrganizations.FinancialDepartmentTreeStore',{
	extend: 'Ext.data.TreeStore',
	autoSync:true,
	proxy : {
		type : 'ajax',
		url : baseinfo.realPath('loadFinancialDepartmentTree.action')
	},
	root : {
		id : baseinfo.financialOrganizations.FinancialOrganizationsRootCode,//实际是根部门的CODE
		text :baseinfo.financialOrganizations.FinancialOrganizationsRootName,
		expanded : true
	},
	sorters : [ {
		property : 'leaf',
		direction : 'ASC'
	} ]
});
//------------------------------------model---------------------------------------------------
/**
 * @description 行政组织树形结构
 */
Ext.define('Foss.baseinfo.financialOrganizations.FinancialOrgTreeSearchPanel', {
	extend:'Ext.panel.Panel',
	title:'财务组织',//baseinfo.financialOrganizations.i18n('foss.baseinfo.orgBizProperty'),//行政组织业务属性
    width:300,
    height:750,
    frame:true,
    //margin:'0 40 0 0',
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
     //查询FORM
	searchFinancialDepartmentForm:null,
	getSearchFinancialDepartmentForm:function(){
		if(Ext.isEmpty(this.searchFinancialDepartmentForm)){
    		this.searchFinancialDepartmentForm = Ext.create('Foss.baseinfo.financialOrganizations.SearchFinancialDepartmentForm');
    	}
    	return this.searchFinancialDepartmentForm;
	},
    //部门树PANEL
	financialDepartmentTreePanle:null,
	getFinancialDepartmentTreePanle:function(){
		if(Ext.isEmpty(this.financialDepartmentTreePanle)){
    		this.financialDepartmentTreePanle = Ext.create('Foss.baseinfo.financialOrganizations.FinancialDepartmentTreePanle');
    	}
    	return this.financialDepartmentTreePanle;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.	items  = [me.getSearchFinancialDepartmentForm(),me.getFinancialDepartmentTreePanle()];
		me.callParent([cfg]);
    }

});
/**
 * @description 查询部门界面
 */
Ext.define('Foss.baseinfo.financialOrganizations.SearchFinancialDepartmentForm', {
	extend:'Ext.form.Panel',  
    height:80,
    layout:{
		type:'table',
		columns: 2
	},
	//树形结构查询部门
    searchDept:function(){
    	var me = this;
		var text = me.getForm().findField('deptName').getValue();
		var array = {'financialOrganizationsInfoVo':{'financialOrganizationsEntity':{'name':text}}};//传参
		Ext.Ajax.request({
			url :"../baseinfo/queryFinancialOrganizationsFullPath.action",//请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
			jsonData:array,
			//async:false,
			success : function(response) {    								 
				json = Ext.decode(response.responseText);
				var seq = json.financialOrganizationsInfoVo.fullPath;//查询到的第一个部门的全路径
				if(Ext.isEmpty(seq)){//如果没有查到，则展开根结点
					me.up('panel').getFinancialDepartmentTreePanle().getStore().load({'node':me.up('panel').getFinancialDepartmentTreePanle().getRootNode()});	
				}else{
					seq = '!'+seq;
					me.up('panel').getFinancialDepartmentTreePanle().selectPath(seq,'id','!');//再按第一个路径展开
				}            								 
			}
		});
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			disabled:!baseinfo.financialOrganizations.isPermission('financialOrganizations/financialOrganizationsQueryButton'),
			hidden:!baseinfo.financialOrganizations.isPermission('financialOrganizations/financialOrganizationsQueryButton'),
			text : baseinfo.financialOrganizations.i18n('foss.baseinfo.query'),//查询
			cls:'yellow_button',
			handler : function() {
				me.searchDept();
			}
		}];
		me.items = [{
			xtype : 'textfield',
			fieldLabel : baseinfo.financialOrganizations.i18n('foss.baseinfo.name'),//部门名称
			labelPad : 4,
			labelWidth : 80,
			width:265,
			listeners : {  
			   specialkey : function(field, e) {  
				 if (e.getKey() == Ext.EventObject.ENTER) {
					 me.searchDept();
				   }  
				}
			}  ,
			name : 'deptName'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 左侧树结构面板
 */
Ext.define('Foss.baseinfo.financialOrganizations.FinancialDepartmentTreePanle', {
    extend:'Ext.tree.Panel',
	autoScroll:true,
	margin:false,  
	border : false,
	flex:1,
	oldFullPath:null,//刷新之前展开的路径
	useArrows: true,
	//collapsible : true,
	rootVisible: true,  
	viewConfig: {plugins: { ptype: 'treeviewdragdrop', appendOnly: true } },
	layoutConfig : {
		// 展开折叠是否有动画效果
		animate : true
	},
	oldId:null,
	//左键单击事件
	treeLeftKeyAction:function(node,record,item,index,e){
		var me = this;
		if(Ext.isEmpty(record.raw)||record.raw.entity.id==me.oldId){
			return;
		}else{
			var financialDepartmentModel = new Foss.baseinfo.financialOrganizations.FinancialOrganizationsEntity(record.raw.entity);
			if(financialDepartmentModel.get('costCenter')=='Y'){//是否是成本中心
				financialDepartmentModel.set('costCenter',baseinfo.financialOrganizations.i18n('foss.baseinfo.yes'));
			}else if(financialDepartmentModel.get('costCenter')=='N'){
				financialDepartmentModel.set('costCenter',baseinfo.financialOrganizations.i18n('foss.baseinfo.no'));
			}
			if(financialDepartmentModel.get('subSidiary')=='Y'){//是否是子公司
				financialDepartmentModel.set('subSidiary',baseinfo.financialOrganizations.i18n('foss.baseinfo.yes'));
			}else if(financialDepartmentModel.get('subSidiary')=='N'){
				financialDepartmentModel.set('subSidiary',baseinfo.financialOrganizations.i18n('foss.baseinfo.no'));
			}
			me.up('panel').up().getFinancialInfoForm().getForm().loadRecord(financialDepartmentModel);
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.financialOrganizations.FinancialDepartmentTreeStore');
		// 监听事件
		me.listeners={
		  	scrollershow: function(scroller) {
		  		if (scroller && scroller.scrollEl) {
		  				scroller.clearManagedListeners(); 
		  				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		  		}
		  	},
	    	itemclick : me.treeLeftKeyAction//单击事件
	    	//itemdblclick:treeDbLeftKeyAction//双击事件
	    },
		me.callParent([cfg]);
     }
});
/**
 * @description 行政组织主信息查看修改界面
 */
Ext.define('Foss.baseinfo.financialOrganizations.FinancialInfoForm', {
	extend:'Ext.form.Panel',  
	title:baseinfo.financialOrganizations.i18n('foss.baseinfo.finOrgDetail'),//财务组织-详情信息
	frame:true,
    flex:1,
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	xtype:'displayfield',
    	width:300,
    	margin : '15 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.name')//名称
		},{
			name: 'code',
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.orgCode')//组织编码
		},{
			name: 'parentOrgName',
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.superiorOrg')//上级组织名称
		},{
			name: 'costCenter',
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.costCenter')//是否是成本中心
		},{
			name: 'subSidiary',
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.isSubsidiary')//是否是子公司
		},{
			xtype:'label',
		},{
			name: 'fullName',
			colspan : 2,
			width:600,
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.fullName')//全称
		},{
			name: 'notes',
			colspan : 2,
			width:600,
	        fieldLabel: baseinfo.financialOrganizations.i18n('foss.baseinfo.notes')//描述
		}];
		me.callParent([cfg]);
	}
});

/**
 * @description 财务组织主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-financialOrganizations_content')) {
		return;
	};
	var financialOrgTreeSearchPanel = Ext.create('Foss.baseinfo.financialOrganizations.FinancialOrgTreeSearchPanel');//财务组织树查询panel
	var financialInfoForm = Ext.create('Foss.baseinfo.financialOrganizations.FinancialInfoForm');//组织详细信息PANEL
	Ext.getCmp('T_baseinfo-financialOrganizations').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-financialOrganizations_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : {
			type : 'hbox',
			align : 'stretch'
		},
		//layout: 'border',
		//财务组织树查询panel
		getFinancialOrgTreeSearchPanel : function() {
			return financialOrgTreeSearchPanel;
		},
		//财务组织详细信息PANEL
		getFinancialInfoForm : function() {
			return financialInfoForm;
		},
		items : [financialOrgTreeSearchPanel,financialInfoForm] 
	}));
});

