//定义一个数据常量
baseinfo.freightRoute.deptCodes ={};
/**
 * 获取当前用户权限（初始化用户数据权限）
 */
baseinfo.freightRoute.init =function(){
	Ext.Ajax.request({
		async:false,
		url:baseinfo.realPath('queryCurrentUserOrg.action'),
		success:function(response){
			var json =Ext.decode(response.responseText);
			baseinfo.freightRoute.deptCodes =json.freightRouteVo.userOrgList;
		},
		exception:function(response){
			var json =Ext.decode(response.responseText);
		}
	});
};
/**
 * 判断到达部门和始发部门中是否有一个属于用户数据权限部门
 */
baseinfo.freightRoute.isValidTheDeptCodes =function(sourceCode,targetCode){
	//默认好为false
	var flag =false,
	deptCodes =baseinfo.freightRoute.deptCodes;
	if(deptCodes.length !=0){
		for(var i =0;i<deptCodes.length;i++){
			if(sourceCode == deptCodes[i]){
				flag =true;
			}
			if(targetCode ==deptCodes[i]){
				flag =true;
			}
		}
	}
	return flag;
};
/**
 * 判断 产品是否是快递 还是零担transType
 */
baseinfo.freightRoute.checkProductIsExp =function(transType){
	if(transType ==='RCP'||transType ==='PACKAGE'||transType=='EXPRESS_EPEP'||transType=='EPEP'||transType=='EXPRESS_DEAP'||transType=='DEAP'||transType=='RECISION_EC_PRODUCTS'||transType=='PCP'){
		return 'Y';
	}else{
		return 'N';
	}
	
}
/**
 * 走货路径
 * 
 * @author:谢艳涛
 * Build date: 2012-12-05
 * 
 */
//定义一个model
Ext.define('Foss.baseinfo.freightRoute.FreightRouteModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'virtualCode',  //虚拟编码
		type : 'string'
	}, {          
		name : 'orginalOrganizationCode',       //出发部门
		type : 'string'
	}, {          
		name : 'orginalOrganizationName',       //出发部门名称
		type : 'string'
	}, {          
		name : 'destinationOrganizationName',       //到达部门名称
		type : 'string'
	}, {
		name : 'destinationOrganizationCode', //到达部门
		type : 'string'
	},{
		name : 'packingOrganizationName', //打木架外场部门名称
		type : 'string'
	}, {
		name : 'transType',       //运输性质
		type : 'string'
	},{
		name : 'aging', //时效 (冗余)
		type : 'number'
	},{
		name : 'defaultRoute',  //是否默认走货路径
		type : 'string'
	},{
		name : 'doPacking', //是否可以打木架
		type : 'string'
	},{
		name : 'active', //是否有效
		type : 'string'
	},{
		name : 'notes', //备注
		type : 'string'
	},{
		name : 'createUser',     //创建人
		type : 'string'
	}, {
		name : 'createDate',     //创建时间
		type : 'date'
	}, {
		name : 'modifyUser',     //修改人
		type : 'string'
	}, {
		name : 'modifyDate',     //修改时间
		type : 'date'
	},{
        name : 'valid', //是否生效
        type : 'string'
    },{
        name : 'lineVirtualCode', //所使用的线路虚拟编码，查询用(扩展)
        type : 'string'
    } ]
});
//网点组实体
Ext.define('Foss.baseinfo.freightRoute.NetworkGroupModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'netGroupName',//网点组ＩＤ
        type : 'string'
    },{
        name : 'sourceOrgs',// 出发网点组
        type : 'string'
    },{
        name : 'targetOrgs',// 到达网点组
        type : 'string'
    },{
    	name : 'sourceOrganizationCodeList',//出发营业部列表
    	type : 'auto'
    },{
    	name : 'sourceOrganizationNameList',//出发营业部名称列表
    	type : 'auto'
    },{
    	name : 'targetOrganizationNameList',//到达营业部名称列表
    	type : 'auto'
    },{
    	name : 'targetOrganizationCodeList',//到达营业部code列表
    	type : 'auto'
    },{
    	name : 'freightRouteVirtualCode',//走货路径虚拟编码
    	type : 'string'
    },{
    	name : 'sourceOrgName',//出发网点组名称
    	type : 'string'
    },{
    	name : 'targetOrgName',//到达网点组名称
    	type : 'string'
    },{
        name : 'expNetworkGroup',//是否快递网点组
        type : 'string'
        }
    ]
});

//网点组对应营业部实体
Ext.define('Foss.baseinfo.freightRoute.DepartmentModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'code',//网点组对应部门编码
        type : 'string'
    },{
        name : 'name',// 网点组对应部门名称
        type : 'string'
    },{
        name : 'isDefault',// 是否默认配载部门
        type : 'auto'
    }]
});

//营业部实体
Ext.define('Foss.baseinfo.freightRoute.DeptModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'code',//部门编码
        type : 'string'
    },{
        name : 'name',// 部门名称
        type : 'string'
    }]
});


//走货路径线路实体
Ext.define('Foss.baseinfo.freightRoute.FreightRouteLineModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'sequence',// 序号
        type : 'int'
    },{
        name : 'virtualCode',// 走货路径线路虚拟编码
        type : 'string'
    },{
        name : 'freightRouteVirtualCode',// 走货路径虚拟编码
        type : 'string'
    },{
        name : 'lineVirtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'orginalOrganizationCode',// 出发部门编码
        type : 'string'
    },{
        name : 'destinationOrganizationCode', // 到达部门编码
        type : 'string'
    },{
        name : 'aging', // 时效（冗余）
        type : 'number'
    },{
        name : 'passbyAging', // 经停时间
        type : 'number'
    },{
        name : 'active',// 是否有效
        type : 'string'
    },{
        name : 'lineName', // 线路名称
        type : 'string'
    },{
        name : 'simpleCode',// 线路简码
        type : 'string'
    },{
        name : 'orginalOrganizationName', // 出发部门名称
        type : 'string'
    },{
        name : 'destinationOrganizationName',// 到达部门名称
        type : 'string'
    },{
    	name : 'classes',//考核班次
    	type : 'string',
    	convert: function(v){
    		if(v==0){
    			return "";
    		}
    		return v;
    	}
    }]
});


//创建一个走货路径的store
Ext.define('Foss.baseinfo.freightRoute.FreightRouteStore',{
	extend:'Ext.data.Store',
//	autoLoad: true,
	//页面条数定义
    pageSize: 10,
	//绑定model
    model: 'Foss.baseinfo.freightRoute.FreightRouteModel',
    proxy: {
        //以JSON的方式加载数据
        type : 'ajax',
        actionMethods:'POST',
        url: baseinfo.realPath('queryFreightRoute.action'),
		reader : {
			type : 'json',
			root : 'freightRouteVo.freightRouteEntities',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
    },
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	},
	//监听器
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp('Foss_baseinfo_freightRoute_QueryForm_Id').getForm();
			if (queryForm != null) {
				var queryParams = queryForm.getValues();
				Ext.apply(operation, {
					params : {
						'freightRouteVo.entity.orginalOrganizationCode':queryParams.orginalOrganizationCode,
						'freightRouteVo.entity.destinationOrganizationCode':queryParams.destinationOrganizationCode,
						'freightRouteVo.entity.transType':queryParams.transType,
						'freightRouteVo.entity.valid':queryParams.valid,
						'freightRouteVo.entity.lineVirtualCode':queryParams.lineVirtualCode
					}
				});	
			}
		}
	}
});


//创建一个网点组的store
Ext.define('Foss.baseinfo.freightRoute.NetworkGroupStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.freightRoute.NetworkGroupModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//创建部门的store
Ext.define('Foss.baseinfo.freightRoute.DeptStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.freightRoute.DeptModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//创建一个走货路径线路的store
Ext.define('Foss.baseinfo.freightRoute.FreightRouteLineStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.freightRoute.FreightRouteLineModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//创建一个出发站对应营业部的store
Ext.define('Foss.baseinfo.freightRoute.SourceOrgStore',{
	extend:'Ext.data.Store',
	//绑定model
	model: 'Foss.baseinfo.freightRoute.DepartmentModel',
    //构造函数
    constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//创建一个本地的管理部门（车队）名称store
Ext.define('Foss.baseinfo.freightRoute.TransTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//创建一个运输性质store
Ext.define('Foss.baseinfo.freightRoute.TransportNatureStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data:{'items':[
		            {'code':'TRANS_VEHICLE','name':baseinfo.freightRoute.i18n('foss.baseinfo.transVehicle')},
		            {'code':'TRANS_AIRCRAFT','name':baseinfo.freightRoute.i18n('foss.baseinfo.transAircraft')},
		            {'code':'TRANS_EXPRESS','name':baseinfo.freightRoute.i18n('foss.baseinfo.transExpress')},
		            {'code':'WHOLE_VEHICLE','name':baseinfo.freightRoute.i18n('foss.baseinfo.wholeVehicle')},
					{'code':'AIR_FREIGHT','name':baseinfo.freightRoute.i18n('foss.baseinfo.airFreight')},
					{'code':'COMMON_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.commonProducts')},
					{'code':'RECISION_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.recisionProducts')},
					{'code':'EXPRESS','name':baseinfo.freightRoute.i18n('foss.baseinfo.express')},
					{'code':'RECISION_BG_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.recisionBgProducts')},
					{'code':'COMMON_BG_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.commonBgProducts')},
		       		{'code':'FLF','name':baseinfo.freightRoute.i18n('foss.baseinfo.flflabel')},
					{'code':'FSF','name':baseinfo.freightRoute.i18n('foss.baseinfo.fsflabel')},
					{'code':'LRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.lrflabel')},//'精准汽运(长途)'
					{'code':'SRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.srflabel')},//'精准汽运(短途)'
					{'code':'PLF','name':baseinfo.freightRoute.i18n('foss.baseinfo.plflabel')},
					{'code':'AF','name':baseinfo.freightRoute.i18n('foss.baseinfo.aflabel')},
					{'code':'PACKAGE','name':baseinfo.freightRoute.i18n('foss.baseinfo.packagelabel')},//标准快递
					{'code':'RCP','name':baseinfo.freightRoute.i18n('foss.baseinfo.rcplabel')}, //3.60特惠件
					{'code':'BGFLF','name':baseinfo.freightRoute.i18n('foss.baseinfo.bgflf')},
					{'code':'BGLRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.bglrf')},
					{'code':'BGFSF','name':baseinfo.freightRoute.i18n('foss.baseinfo.bgfsf')},
					{'code':'BGSRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.bgsrf')},
					{'code':'DTD','name':baseinfo.freightRoute.i18n('foss.baseinfo.doorAndDoor')},//门到门
					{'code':'YTY','name':baseinfo.freightRoute.i18n('foss.baseinfo.fieldAndField')},  //场到场
					{'code':'EXPRESS_EPEP','name':baseinfo.freightRoute.i18n('foss.baseinfo.expressepeplabel')},  //二级电商尊享
					{'code':'EPEP','name':baseinfo.freightRoute.i18n('foss.baseinfo.epeplabel')},  //三级电商尊享
					{'code':'EXPRESS_DEAP','name':baseinfo.freightRoute.i18n('foss.baseinfo.expressdeaplabel')},  //二级商务专递
					{'code':'DEAP','name':baseinfo.freightRoute.i18n('foss.baseinfo.deaplabel')}  //三级商务专递
	   	]},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


//创建一个运输性质store
Ext.define('Foss.baseinfo.freightRoute.TransportLevelStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data:{'items':[
				{'code':'LEVEL1','name':baseinfo.freightRoute.i18n('foss.baseinfo.transLevel1')},//'精准汽运(长途)'
				{'code':'LEVEL2','name':baseinfo.freightRoute.i18n('foss.baseinfo.transLevel2')},//'精准汽运(短途)'
				{'code':'LEVEL3','name':baseinfo.freightRoute.i18n('foss.baseinfo.transLevel3')}
 	]},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//自定义确认密码校验
Ext.apply(Ext.form.VTypes, {
    confirmPwd : function(val, field) {
        if (field.confirmPwd) {
            var firstPwdId = field.confirmPwd.first;
            var secondPwdId = field.confirmPwd.second;
            this.firstField = Ext.getCmp(firstPwdId);
            this.secondField = Ext.getCmp(secondPwdId);
            var firstPwd = this.firstField.getValue();
            var secondPwd = this.secondField.getValue();
            if (firstPwd == secondPwd) {
                return true;
            } else {
                return false;
            }
        }
    }
});

/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.freightRoute.QueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.freightRoute.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_freightRoute_QueryForm_Id',
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelSeparator:'',
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	items :[ {
		name : 'orginalOrganizationCode',
		fieldLabel : baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
		columnWidth : .28,
		xtype : 'dynamicorgcombselector',
        type : 'ORG',
        transferCenter:'Y',//--或者查询外场
        airDispatch  : 'Y'//--空运配载
	}, {
		name : 'destinationOrganizationCode',
		fieldLabel : baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
		columnWidth : .28,
		xtype : 'dynamicorgcombselector',
        types : 'ORG,CPPX,CPKY',
        airDispatch:'Y',//查询空运总调
        transferCenter:'Y'//--或者查询外场
	},{
		xtype:'combo',
		name: 'transType',
		fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.transType'),
		columnWidth: .28,
		store:FossDataDictionary.getDataDictionaryStore('BSE_FREIGHT_TRANS_TYPE'),
		displayField: 'valueName',
		valueField: 'valueCode',
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		value:''
	},{
	    xtype:'combobox',
		name: 'valid',
		fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.status'),
		columnWidth: .28,
		displayField: 'name',
		//value值字段
		valueField:'code', 
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		value:'',
		store: Ext.create('Foss.baseinfo.freightRoute.TransTypeStore',{
			data:{'items':[
			    {'code':'','name':baseinfo.freightRoute.i18n('foss.baseinfo.alllabel')},
	       		{'code':'Y','name':baseinfo.freightRoute.i18n('foss.baseinfo.valid')},
				{'code':'N','name':baseinfo.freightRoute.i18n('foss.baseinfo.invalid')}
   			]}
		})
	},{
		name : 'lineVirtualCode',
		fieldLabel : '使用线路',
		columnWidth : .28,
		xtype : 'commonlineselector',
		lineSort:'BSE_LINE_SORT_TRANSFER',
		//value值字段
		valueField:'virtualCode'
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.freightRoute.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteQueryButton'),
			hidden:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.freightRoute.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteQueryButton'),
			hidden:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteQueryButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					//获取
					var orginalOrganizationCode =me.getForm().findField('orginalOrganizationCode').getValue();
					var destinationOrganizationCode =me.getForm().findField('destinationOrganizationCode').getValue();
					if(Ext.isEmpty(orginalOrganizationCode)&&Ext.isEmpty(destinationOrganizationCode)){
						Ext.Msg.alert(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),'始发、到达站请选择一个！');
						return;
					}
					//判断用户数据权限
					var flag =baseinfo.freightRoute.isValidTheDeptCodes(orginalOrganizationCode,destinationOrganizationCode);
					if(!flag){
						Ext.Msg.alert(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.authorityLimited'));
						return;
					}
					Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid().getPagingToolbar().moveFirst()
				}
			}
		}]
		me.callParent([cfg]);
	}
});


// 走货路径详细信息表单
Ext.define('Foss.baseinfo.freightRoute.DetailForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120,
		readOnly : true
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    items : [ {
		name: 'transType',
		fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.transType'),
		columnWidth: .25,
		allowBlank:false,
		queryMode:'local',
		editable:false,
		xtype:'combo',
		store:FossDataDictionary.getDataDictionaryStore('BSE_FREIGHT_TRANS_TYPE'),
		displayField: 'valueName',
		valueField: 'valueCode'
	},{
	        xtype : 'label'//占空间
		},{
			name: 'orginalOrganizationName',
			allowBlank:false,
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'destinationOrganizationName',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
	        allowBlank:false,
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'aging',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.aging'),
	        allowBlank:false,
	        maxLength:200,
	        xtype : 'textfield',
	        readOnly:true
		},{
			//指定类型为字段容器类型
			xtype      : 'fieldcontainer', 
			defaultType: 'checkboxfield',  //指导定容器中的项类型为checkboxfield
			items: [{ 
					boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.defaultRoute'), 
					name      : 'defaultRoute',  //表单的参数名
					inputValue: '1' //表单的参数值
				}]
		},{
			name: 'doPackings',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.doPackings'),
	        xtype : 'radiogroup',
	        layout:'column',
			defaults:{
 			width:100
 			},
			items: [{ 
					boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.yes'), 
					columnWidth:.5,
					name      : 'doPacking',
					inputValue: 'Y',
					readOnly:true
				}, { 
					boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.no'), 
					columnWidth:.5,
					name      : 'doPacking',
					inputValue: 'N',
					checked   : true,
					readOnly:true
			}]
		},{
			name: 'packingOrganizationName',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.packingOrganizationName'),
	        allowBlank:false,
	        maxLength:200,
	        xtype : 'textfield',
	        hidden : true
		},{
	        xtype : 'label'//占空间
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.airagencycompany.remark'),
	        colspan : 2,
	        maxLength:1000,
	        width:600,
	        xtype : 'textareafield'
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
});

// 新增/修改走货路径表单
Ext.define('Foss.baseinfo.freightRoute.AddUpdateForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height : 300,
//	collapsible: true,
	defaults : {
		margin : '5 15 5 25',
		labelWidth : 120
	},
	isUpdate:false,//是否为修改，默认false
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    //提交信息
    updateState : function(flag){
    	var me = this;
    	//获取表单
    	var basicForm = this.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	
    		//获取model实例
//			var record = basicForm.getRecord();
			var infoModel = me.up('window').infoModel;
			var model1 = new Foss.baseinfo.freightRoute.FreightRouteModel(infoModel);
			//将FORM中数据设置到MODEL里面
//			if(!me.isUpdate){
//				record=Ext.create('Foss.baseinfo.freightRoute.FreightRouteModel');
//			}
//			basicForm.updateRecord(record);
			basicForm.updateRecord(model1);
			
    		var jsonData = {'freightRouteVo':{'freightRouteVirtualCode':model1.get('virtualCode')}};
    		
    		var url = null;
    		if(flag){//生效
    			url = baseinfo.realPath('updateValid.action');//生效操作
    		}else{
    			url = baseinfo.realPath('updateInvalid.action');//失效操作
    		}
			
			var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  var infoModel = json.freightRouteVo.entity;
					  if(Ext.isEmpty(infoModel)){
					  	if(flag){//生效
					  		infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.validFailed'));
					  	}else{
					  		infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.invalidFailed'));
					  	}
					  	
					  }
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					  
					  var model = new Foss.baseinfo.freightRoute.FreightRouteModel(infoModel);
					  basicForm.loadRecord(model);
					  if(infoModel.defaultRoute == 'Y'){//判断是否默认走货路径
			    	  	basicForm.findField('defaultRoute').setValue(true);
				      }else{
				    	basicForm.findField('defaultRoute').setValue(false);
				      }
					  basicForm.findField('aging').setValue(infoModel.aging/1000);
					  if(flag){//生效
					  	me.up('window').parent.getPagingToolbar().moveFirst();
					  	//form表单元素不能修改
						me.getForm().getFields().each(function(item){
							item.setReadOnly(true);
						});//表格重置
						
					  	//走货路径线路添加按钮不可用
						me.up('window').getFreightRouteLineGrid().down('button').setDisabled(true);
						//添加网点组按钮不可用
						me.up('window').getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(true);
						//走货路径线路列表操作列隐藏
						me.up('window').getFreightRouteLineGrid().columns[0].hide();
						//网点组列表操作列隐藏
						me.up('window').getNetworkGroupGrid().columns[0].hide();
						
					  	//生效按钮不可用
						me.getDockedItems()[0].items.items[3].setDisabled(true);
						//保存按钮不可用
						me.getDockedItems()[0].items.items[2].setDisabled(true);
						//重置按钮不可用
						me.getDockedItems()[0].items.items[1].setDisabled(true);
						//失效按钮可用
						me.getDockedItems()[0].items.items[4].setDisabled(false);
					  }else{//失效
					  	me.up('window').parent.getPagingToolbar().moveFirst();
					  	//form表单元素不能修改
						me.getForm().getFields().each(function(item){
							if(item.getName( )=='aging'){
								item.setReadOnly(true);
							}else{
								item.setReadOnly(false);
							}
						});//表格重置
					  	//走货路径线路添加按钮可用
						me.up('window').getFreightRouteLineGrid().down('button').setDisabled(false);
						//添加网点组按钮可用
						me.up('window').getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(false);
						//走货路径线路列表操作列显示
						me.up('window').getFreightRouteLineGrid().columns[0].show();
						//网点组列表操作列显示
						me.up('window').getNetworkGroupGrid().columns[0].show();
						
					  	//生效按钮可用
						me.getDockedItems()[0].items.items[3].setDisabled(false);
						//保存按钮可用
						me.getDockedItems()[0].items.items[2].setDisabled(false);
						//重置按钮可用
						me.getDockedItems()[0].items.items[1].setDisabled(false);
						//失效按钮不可用
						me.getDockedItems()[0].items.items[4].setDisabled(true);
					  
					  }
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
    	
    },
    //提交表单
    commitInfo : function(){
    	var me = this;
    	//获取表单
    	var basicForm = this.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	
    		//获取model实例
			var record = basicForm.getRecord();
			//将FORM中数据设置到MODEL里面
			if(!me.isUpdate){
				record=Ext.create('Foss.baseinfo.freightRoute.FreightRouteModel');
			}
			basicForm.updateRecord(record);
			//时效保存到数据库时需要乘以1000
			record.set('aging',record.get('aging')*1000);
    		var jsonData = {'freightRouteVo':{entity:record.data}};
    		
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateFreightRoute.action');//请求走货路径修改
			}else{
				url = baseinfo.realPath('addFreightRoute.action');
			}
			var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid(); 
            Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //保存成功列表数据重新加载
					  infoGrid.getPagingToolbar().moveFirst();
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					  
					  if(Ext.isEmpty(json.freightRouteVo.entity)){
						infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.serverException'));//服务端有异常！
						return;
					  }
					  //将返回的值设置到window中
					  me.up('window').infoModel = json.freightRouteVo.entity;
					  if(me.isUpdate){//修改
					  	if(me.up('window').infoModel.valid == 'Y'){//走货路径生效
							//form表单元素不能修改
							me.getForm().getFields().each(function(item){
								item.setReadOnly(true);
							});
							//走货路径线路添加按钮不可用
							me.up('window').getFreightRouteLineGrid().down('button').setDisabled(true);
							//添加网点组按钮不可用
							me.up('window').getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(true);
							//走货路径线路列表操作列隐藏
							me.up('window').getFreightRouteLineGrid().columns[0].hide();
							//网点组列表操作列隐藏
							me.up('window').getNetworkGroupGrid().columns[0].hide();
							
							//生效按钮不可用
							me.getDockedItems()[0].items.items[3].setDisabled(true);
							//生效状态保存按钮不可用
							me.getDockedItems()[0].items.items[2].setDisabled(true);
							//失效按钮可用
							me.getDockedItems()[0].items.items[4].setDisabled(false);
						}else{
							//form表单元素不能修改
							me.getForm().getFields().each(function(item){
								if(item.getName( )=='aging'|| item.getName() == 'doPacking'){
									//只读的
									item.setReadOnly(true);
								}else{
									item.setReadOnly(false);
								}
							});
							//走货路径线路添加按钮不可用
							me.up('window').getFreightRouteLineGrid().down('button').setDisabled(false);
							//走货路径线路列表操作列显示
							me.up('window').getFreightRouteLineGrid().columns[0].show();
							//网点组列表操作列隐藏
							me.up('window').getNetworkGroupGrid().columns[0].show();
							//添加网点组按钮不可用
							me.up('window').getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(false);
							//生效按钮可用
							me.getDockedItems()[0].items.items[3].setDisabled(false);
							//失效状态保存按钮可用
							me.getDockedItems()[0].items.items[2].setDisabled(false);
							//失效按钮可用
							//失效按钮不可用
							me.getDockedItems()[0].items.items[4].setDisabled(true);
						}
//						return;//返回，不做以下操作
					  }else{//新增
						me.getForm().getFields( ).each(function(item,index,length){
							item.setReadOnly(true);//(对于numberfield的样式社会有问题)
						});//将FORM设置为不可用
						//me.doLayout( );
						me.getDockedItems()[0].items.items[1].setDisabled(true);//重置按钮不可用
						me.getDockedItems()[0].items.items[2].setDisabled(true);//保存按钮不可用
						me.getDockedItems()[0].items.items[3].setDisabled(false);//保存按钮不可用
						me.getDockedItems()[0].items.items[4].setDisabled(true);//保存按钮不可用
						//走货路径线路添加按钮可用
						me.up('window').getFreightRouteLineGrid().getDockedItems()[1].items.items[0].setDisabled(false);
						//添加网点组按钮可用
						me.up('window').getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(false);
					  }
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //失败消息
		              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
		        }
			});
		}
	},
	items : [
//	         {
//		    xtype:'combobox',
//			name: 'transLevel',
//			fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.transLevel'),
//			columnWidth: .25,
//			displayField: 'name',
//			allowBlank:false,
//			//value值字段
//			valueField:'code', 
//			queryMode:'local',
//			triggerAction:'all',
//			editable:false,
//			value:'LEVEL3',
//			store: Ext.create('Foss.baseinfo.freightRoute.TransportLevelStore'),
//			listeners:{
//		    	select: {
//		            fn: function(combo, records, eOpts ) {
//		            	var transType =this.up('form').getForm().findField('transType'); 
//		            	transType.reset();
//		            	var transTypeArray = new Array();
//		            	var record = records[0];
//		            	var transLevel =  this.up('form').getForm().findField('transLevel').getValue();
//		            	
//	            		
//		            	if(transLevel == 'LEVEL1') {
//		            		transTypeArray.push({'code':'TRANS_VEHICLE','name':baseinfo.freightRoute.i18n('foss.baseinfo.transVehicle')}); 
//		            		transTypeArray.push({'code':'TRANS_AIRCRAFT','name':baseinfo.freightRoute.i18n('foss.baseinfo.transAircraft')});
//		            	}
//		            	if(transLevel == 'LEVEL2') {
//		            		transTypeArray.push({'code':'WHOLE_VEHICLE','name':baseinfo.freightRoute.i18n('foss.baseinfo.wholeVehicle')}); 
//		            		transTypeArray.push({'code':'AIR_FREIGHT','name':baseinfo.freightRoute.i18n('foss.baseinfo.airFreight')}); 
//		               		transTypeArray.push({'code':'COMMON_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.commonProducts')}); 
//		               		transTypeArray.push({'code':'RECISION_PRODUCTS','name':baseinfo.freightRoute.i18n('foss.baseinfo.recisionProducts')}); 
//		            	}
//		            	if(transLevel == 'LEVEL3') {
//		            		transTypeArray.push({'code':'PLF','name':baseinfo.freightRoute.i18n('foss.baseinfo.plflabel')}); 
//		            		transTypeArray.push({'code':'AF','name':baseinfo.freightRoute.i18n('foss.baseinfo.aflabel')}); 		            		
//		            		transTypeArray.push({'code':'SRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.srflabel')}); 
//		            		transTypeArray.push({'code':'FLF','name':baseinfo.freightRoute.i18n('foss.baseinfo.flflabel')}); 
//		            		transTypeArray.push({'code':'FSF','name':baseinfo.freightRoute.i18n('foss.baseinfo.fsflabel')});
//		            		transTypeArray.push({'code':'LRF','name':baseinfo.freightRoute.i18n('foss.baseinfo.lrflabel')});
//		            	}
//		            	transType.store.loadData(transTypeArray);
//		            }
//		    	}
//			
//			}
//		},
	          {
	    xtype:'combo',
		name: 'transType',
		fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.transType'),
		columnWidth: .25,
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		editable:false,
		value:'AF',
		store:FossDataDictionary.getDataDictionaryStore('BSE_FREIGHT_TRANS_TYPE'),
		displayField: 'valueName',
		valueField: 'valueCode',
		listeners:{
//				change:function(item,newValue,oldValue){
//	        		var me = this.up('form');
//	        		if(me.isUpdate){
//	        			if(!Ext.isEmpty(me.getDockedItems()[0])){
//	        				//生效按钮不可用
//							me.getDockedItems()[0].items.items[3].setDisabled(true);
//							//保存按钮可用
//							me.getDockedItems()[0].items.items[2].setDisabled(false);
//							//失效按钮可用
//							me.getDockedItems()[0].items.items[4].setDisabled(true);
//	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
//	        				//生效按钮不可用
////							me.getDockedItems()[0].items.items[3].setDisabled(true);
//	        			}
//	        		}
//	        	},
	        	change:function(combo,records,eopts){
	        		//运输性质名称
	        		var typeName = combo.getRawValue();
	        		//运输性质Code
	        		var typeCode = combo.getValue();
	        		//获取走货路径信息
	        		var infoModel = this.up('form').up('window').infoModel;
	        		var orginalOrganizationCode = this.up('form').getForm().findField('orginalOrganizationCode');
	        		var destinationOrganizationCode = this.up('form').getForm().findField('destinationOrganizationCode');
	        		if(typeCode == 'FLF' || typeCode == 'FSF' ||typeCode == 'LRF' ||typeCode == 'SRF'||typeCode == 'PACKAGE'||typeCode == 'RCP'||typeCode == 'EXPRESS_EPEP'||typeCode == 'EPEP' || typeCode == 'BGFLF' || typeCode == 'BGLRF' || typeCode == 'BGFSF' || typeCode == 'BGSRF'
	        			|| typeCode == 'DTD' || typeCode == 'YTY'||typeCode == 'EXPRESS_DEAP'||typeCode == 'DEAP'||typeCode=='RECISION_EC_PRODUCTS'||typeCode=='PCP'){
	        			orginalOrganizationCode.store.removeAll();
						orginalOrganizationCode.store.removeListener('beforeload');
	        			orginalOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.airDispatch'] = null;
	        			});
//						orginalOrganizationCode.store.loadPage(1);
						//清空到达站数据
						destinationOrganizationCode.store.removeAll();
						destinationOrganizationCode.store.removeListener('beforeload');
	        			destinationOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null;
						var types1 = 'ORG';
						types = types1.split(',');
						searchParams['commonOrgVo.types'] = types;
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.airDispatch'] = null;
						//searchParams['commonOrgVo.type'] = 'ORG';
						});
//						destinationOrganizationCode.store.loadPage(1);
						
						if(!Ext.isEmpty(infoModel)){
							this.up('form').getForm().findField('orginalOrganizationCode').setCombValue(infoModel.orginalOrganizationName,infoModel.orginalOrganizationCode);//出发站
							this.up('form').getForm().findField('destinationOrganizationCode').setCombValue(infoModel.destinationOrganizationName,infoModel.destinationOrganizationCode);//到达站
						}else{
							orginalOrganizationCode.store.loadPage(1);
							destinationOrganizationCode.store.loadPage(1);
						}
	        		}
	        		
	        		//精准空运
	        		if(typeCode == 'AF'){
	        			orginalOrganizationCode.store.removeAll();
						orginalOrganizationCode.store.removeListener('beforeload');
	        			orginalOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.airDispatch'] = 'Y';
					});
//					orginalOrganizationCode.store.loadPage(1);
	        		//清空数据
					destinationOrganizationCode.store.removeAll();
					destinationOrganizationCode.store.removeListener('beforeload');
        			destinationOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					var types = null;
					var types1 = 'ORG,CPKY';
					types = types1.split(',');
					searchParams['commonOrgVo.types'] = types;
					//若为精准空运，到达站为空运
					searchParams['commonOrgVo.transferCenter'] = null;
					searchParams['commonOrgVo.doAirDispatch'] = null;
					searchParams['commonOrgVo.airDispatch'] = 'Y';
					
					});
//					destinationOrganizationCode.store.loadPage(1);
        			if(!Ext.isEmpty(infoModel)){
						this.up('form').getForm().findField('orginalOrganizationCode').setCombValue(infoModel.orginalOrganizationName,infoModel.orginalOrganizationCode);//出发站
						this.up('form').getForm().findField('destinationOrganizationCode').setCombValue(infoModel.destinationOrganizationName,infoModel.destinationOrganizationCode);//到达站
					}else{
						orginalOrganizationCode.store.loadPage(1);
						destinationOrganizationCode.store.loadPage(1);
					}
	        		}
	        		
	        		if(typeCode == 'PLF'){//若为汽运偏线
	        			orginalOrganizationCode.store.removeAll();
						orginalOrganizationCode.store.removeListener('beforeload');
	        			orginalOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.airDispatch'] = null;
					});
//					orginalOrganizationCode.store.loadPage(1);
	        		//清空数据
					destinationOrganizationCode.store.removeAll();
					destinationOrganizationCode.store.removeListener('beforeload');
        			destinationOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					// 传递的部门类型是多种
					// 传递的部门类型是多种
					var types = null;
					var types1 = 'CPPX';
					types = types1.split(',');
					searchParams['commonOrgVo.types'] = types;
					searchParams['commonOrgVo.transferCenter'] = 'Y';
					searchParams['commonOrgVo.doAirDispatch'] = null;
//					searchParams['commonOrgVo.type'] = 'PX';
					});
//					destinationOrganizationCode.store.loadPage(1);
        			if(!Ext.isEmpty(infoModel)){
						this.up('form').getForm().findField('orginalOrganizationCode').setCombValue(infoModel.orginalOrganizationName,infoModel.orginalOrganizationCode);//出发站
						this.up('form').getForm().findField('destinationOrganizationCode').setCombValue(infoModel.destinationOrganizationName,infoModel.destinationOrganizationCode);//到达站
					}else{
						orginalOrganizationCode.store.loadPage(1);
						destinationOrganizationCode.store.loadPage(1);
					}
	        		}
	        		if(typeCode == 'TRANS_AIRCRAFT'){//若为汽运偏线
	        			orginalOrganizationCode.store.removeAll();
						orginalOrganizationCode.store.removeListener('beforeload');
	        			orginalOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
						searchParams['commonOrgVo.airDispatch'] = 'Y';
					});
//					orginalOrganizationCode.store.loadPage(1);
	        		//清空数据
					destinationOrganizationCode.store.removeAll();
					destinationOrganizationCode.store.removeListener('beforeload');
        			destinationOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					// 传递的部门类型是多种
					// 传递的部门类型是多种
					var types = null;
					var types1 = 'CPKY,ORG';
					types = types1.split(',');
					searchParams['commonOrgVo.types'] = types;
					searchParams['commonOrgVo.doAirDispatch'] = 'Y';
					searchParams['commonOrgVo.airDispatch'] = 'Y';
//					searchParams['commonOrgVo.type'] = 'PX';
					});
//					destinationOrganizationCode.store.loadPage(1);
        			if(!Ext.isEmpty(infoModel)){
						this.up('form').getForm().findField('orginalOrganizationCode').setCombValue(infoModel.orginalOrganizationName,infoModel.orginalOrganizationCode);//出发站
						this.up('form').getForm().findField('destinationOrganizationCode').setCombValue(infoModel.destinationOrganizationName,infoModel.destinationOrganizationCode);//到达站
					}else{
						orginalOrganizationCode.store.loadPage(1);
						destinationOrganizationCode.store.loadPage(1);
					}
	        		}
	        		
	        		
	        		if(typeCode == 'TRANS_VEHICLE'){//若为汽运偏线
	        			orginalOrganizationCode.store.removeAll();
						orginalOrganizationCode.store.removeListener('beforeload');
	        			orginalOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
						var searchParams = operation.params;
						if (Ext.isEmpty(searchParams)) {
							searchParams = {};
							Ext.apply(operation, {
										params : searchParams
									});
						}
						// 传递的部门类型是多种
						var types = null,config={};
						if (!Ext.isEmpty(config.types)) {
							types = config.types.split(',');
							searchParams['commonOrgVo.types'] = types;
						}
						searchParams['commonOrgVo.transferCenter'] = 'Y';
					//searchParams['commonOrgVo.airDispatch'] = 'Y';
					});
//					orginalOrganizationCode.store.loadPage(1);
	        		//清空数据
					destinationOrganizationCode.store.removeAll();
					destinationOrganizationCode.store.removeListener('beforeload');
        			destinationOrganizationCode.store.addListener('beforeload', function(store, operation, eOpts) {
					var searchParams = operation.params;
					if (Ext.isEmpty(searchParams)) {
						searchParams = {};
						Ext.apply(operation, {
									params : searchParams
								});
					}
					// 传递的部门类型是多种
					var types = null,config={};
					if (!Ext.isEmpty(config.types)) {
						types = config.types.split(',');
						searchParams['commonOrgVo.types'] = types;
					}
					searchParams['commonOrgVo.transferCenter'] = 'Y';
//					// 传递的部门类型是多种
//					var types = null;
//					var types1 = 'CPKY';
//					types = types1.split(',');
//					searchParams['commonOrgVo.types'] = types;
//					searchParams['commonOrgVo.doAirDispatch'] = 'Y';
////					searchParams['commonOrgVo.type'] = 'PX';
					});
//					destinationOrganizationCode.store.loadPage(1);
        			if(!Ext.isEmpty(infoModel)){
						this.up('form').getForm().findField('orginalOrganizationCode').setCombValue(infoModel.orginalOrganizationName,infoModel.orginalOrganizationCode);//出发站
						this.up('form').getForm().findField('destinationOrganizationCode').setCombValue(infoModel.destinationOrganizationName,infoModel.destinationOrganizationCode);//到达站
					}else{
						orginalOrganizationCode.store.loadPage(1);
						destinationOrganizationCode.store.loadPage(1);
					}
	        		}
	        		
	        		
	        		
	        	}
	        }
		
	},{
	        xtype : 'label'//占空间
		},{
			name: 'orginalOrganizationCode',
			allowBlank:false,
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
//	        forceSelection:true,
	        maxLength:200,
	        xtype : 'dynamicorgcombselector',
	        type : 'ORG',
	        transferCenter:'Y',//--或者查询外场
	        doAirDispatch:'Y',
	        airDispatch : null,//--空运总调
	        listeners:{
	        	change:function(item,newValue,oldValue,eopts){
	        		//出发站名称
	        		var orgName = item.getRawValue();
	        		//出发站Code
	        		var orgCode = item.getValue();
	        		var form = item.up('form');
	        		var window =form.up('window'),
	        		transType =form.getForm().findField('transType');
	        		var productCode=null;
	        		//若为修改，
	        		if(form.isUpdate){
	        			productCode =window.infoModel.transType;
	        		}else{
	        			productCode =transType.getValue();
	        		}
	        		var jsonData = {'freightRouteVo':{'orgCode':orgCode,'productCode':productCode}};
	        		
	        		var url = baseinfo.realPath('querySourceOrg.action');
	        		var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //查询出发营业部门
							  var groupSiteList = json.freightRouteVo.groupSiteList;
							  form.up('window').sourceList =groupSiteList;
							  form.up('window').getNetworkGroupPage().getSourceOrgGrid().getStore().loadData(groupSiteList);
			            },
				        //保存失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			name: 'destinationOrganizationCode',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
	        allowBlank:false,
//	        forceSelection:true,
	        maxLength:200,
	        xtype : 'dynamicorgcombselector',
	        type : null,
//	        types : 'ORG',
	        types:'ORG,CPKY',
	        transferCenter:'Y',//--或者查询外场
	        doAirDispatch : null,//--空运配载
	        airDispatch : 'Y',// 查询空运总调 配置此值
	        listeners:{
	        	change:function(item,newValue){
	        		//出发站名称
	        		var orgName = item.getRawValue();
	        		//出发站Code
	        		var orgCode = item.getValue();
	        		var form = item.up('form');
	        		var window =form.up('window'),
	        		transType =form.getForm().findField('transType');
	        		var productCode=null;
	        		//若为修改，
	        		if(form.isUpdate){
	        			productCode =window.infoModel.transType;
	        		}else{
	        			productCode =transType.getValue();
	        		}
	        		var jsonData = {'freightRouteVo':{'orgCode':orgCode,'productCode':productCode}};
	        		var url = baseinfo.realPath('queryTargetOrg.action') ;
	        		var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //查询到达营业部门
							  var groupSiteList = json.freightRouteVo.groupSiteList;
							  form.up('window').targetList =groupSiteList;
							  form.up('window').getNetworkGroupPage().getTargetOrgGrid().getStore().loadData(groupSiteList);
							  
			            },
				        //保存失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			name: 'aging',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.aging'),
//	        allowBlank:false,
	        maxLength:200,
	        xtype : 'textfield',
	        readOnly:true
		},{ 
			xtype: 'checkboxfield',  //指导定容器中的项类型为checkboxfield
			boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.defaultRoute'), 
			name      : 'defaultRoute',  //表单的参数名
			inputValue: '1', //表单的参数值
			checked   : true,
			listeners:{
	        	change:function(item,newValue,oldValue){
	        		var me = this.up('form');
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				//生效按钮不可用
							me.getDockedItems()[0].items.items[3].setDisabled(true);
							//保存按钮可用
							me.getDockedItems()[0].items.items[2].setDisabled(false);
							//失效按钮可用
							me.getDockedItems()[0].items.items[4].setDisabled(true);
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				//生效按钮不可用
//							me.getDockedItems()[0].items.items[3].setDisabled(true);
	        			}
	        		}
	        	}
	        }
			},{
				name: 'notes',//描述
		        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.airagencycompany.remark'),
		        colspan : 2,
		        maxLength:1000,
		        width:600,
		        xtype : 'textareafield'
			},{
			name: 'doPackings',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.doPackings'),
	        allowBlank:false,
	        hidden:true,
	        xtype : 'radiogroup',
	        layout:'column',
			defaults:{
 			width:100
 			},
 			listeners:{
            change:function(item,newValue,oldValue){
                if(newValue.doPacking == 'Y'){
                	//站位符隐藏
                	item.up('form').query('label')[1].hide();
                	//如果选择否，单选按钮可选
                	item.items.items[0].setReadOnly(false); 
                	item.items.items[1].setReadOnly(false);
                	item.up('form').doLayout();//让其重新调整，没有这句话，时效显示不出来
                }else{
                	//站位符隐藏
                	item.up('form').query('label')[1].show();
                	//如果选择否，单选按钮不可选
                	item.items.items[0].setReadOnly(true); 
                	item.items.items[1].setReadOnly(true);
                	item.up('form').doLayout();//让其重新调整，没有这句话，时效显示不出来
                }
            }
       		},
			items: [{ 
					boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.yes'), 
					columnWidth:.5,
					name      : 'doPacking',
					inputValue: 'Y',
					readOnly:true,
					hidden:true
				}, { 
					boxLabel  : baseinfo.freightRoute.i18n('foss.baseinfo.no'), 
					columnWidth:.5,
					name      : 'doPacking',
					inputValue: 'N',
					checked   : true,
					readOnly:false,
					hidden:true
			}]
		},{
	        xtype : 'label'//占空间
		} ],
		//构造函数
		constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.fbar = [{
				text :baseinfo.freightRoute.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						var infoModel = new Foss.baseinfo.freightRoute.FreightRouteModel(me.up('window').infoModel);
						infoModel.set('aging',infoModel.get('aging')/1000);
						me.loadRecord(infoModel);
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					me.commitInfo();
				} 
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.valid'),//保存
				disabled:true,
				handler :function(){
					me.updateState(true);
				} 
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.invalid'),//保存
				disabled:true,
				handler :function(){
					me.updateState(false);
				} 
			}];
			me.callParent([cfg]);
		}
});

/**
 * 走货路径线路FORM
 */
Ext.define('Foss.baseinfo.freightRoute.RouteLineForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:320,
	collapsible: true,
	isSearchComb:true,
	isUpdate:false,//是否为修改，默认false
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	labelWidth:130,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
     //提交新增信息
    commitInfo:function(){
    	var me = this;
    	//获取表单
    	var basicForm = me.getForm();
    	if(basicForm.isValid()){//校验form是否通过校验
    	    //创建MODEL
    		var infoModel = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineModel');
    		
    		//将FORM中数据设置到MODEL里面
			basicForm.updateRecord(infoModel);
    		//获得走货路径的虚拟编码
			var virtualCode = me.up('window').parent.up('window').infoModel.virtualCode;
			//获取走货路径FORM
			var addUpdateForm = me.up('window').parent.up('window').getAddUpdateForm().getForm();
			//获取走货路径数据对象
			var freightRouteModel = me.up('window').parent.up('window').infoModel;
			
    		infoModel.set('freightRouteVirtualCode',virtualCode);
    		
    		var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateFreightRouteLine.action');
				//设置修改信息的ID
				infoModel.set('id',me.up('window').infoModel.get('id'));
				//设置修改信息的虚拟编码
				infoModel.set('virtualCode',me.up('window').infoModel.get('virtualCode'));
			}else{
				url = baseinfo.realPath('addFreightRouteLine.action');
			}
			//时效在保存到数据库时需要乘以1000
			infoModel.set('aging',infoModel.get('aging')*1000);
			//经停时间在保存到数据库时需要乘以1000
			infoModel.set('passbyAging',infoModel.get('passbyAging')*1000);
			
    		//组织新增数据
    		var jsonData = {'freightRouteLineVo':{'entity':infoModel.data}};
    		var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
    		//Ajax请求
    		Ext.Ajax.request({
				url:url,
				jsonData:jsonData,
				//成功
				success : function(response) {
					  var json = Ext.decode(response.responseText);
					  //打印成功消息
					  infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					  //成功后的返回记录
					  var entity = json.freightRouteLineVo.entity;
					  //获取可以打木架的外场
					  var deptList = json.freightRouteLineVo.lineDeptList;
					  //获取走货路径线路时效
					  var lineAging = infoModel.get('aging');
					  //获取走货路径线路经停时间
					  var linePassbyAging = infoModel.get('passbyAging');
					  //获取走货路径时效
//    				  var aging = addUpdateForm.findField('aging').getValue();
    				  var aging = freightRouteModel.aging;
    				  //转换成Float类型
    				  /*var agingInt = parseFloat(aging);
    				  if(isNaN(agingInt)){//如果时效为非数字
    				  	agingInt = 0;
    				  }*/
					  if(me.isUpdate){
					  	//如果是更新 先删除数据
					  	me.up('window').parent.getStore().remove(me.up('window').infoModel);
					  	//如果是更新查询历史记录的时效+ 经停时间
					  	var countTime = (me.up('window').infoModel.get('aging')+me.up('window').infoModel.get('passbyAging'));
					  	//走货路径时效- 历史记录时效
					  	aging = aging-countTime;
					  }
					  //加到父元素grid的store中				  
					  me.up('window').parent.getStore().add(new Foss.baseinfo.freightRoute.FreightRouteLineModel(entity));
    				  me.up('window').close();//关闭该window
    				  
//    				  agingInt = agingInt+ parseFloat(lineAging) + parseFloat(linePassbyAging);
    				  //计算走货路径时效 = 以前走货路径时效+走货路径线路时效+走货路径线路经停时间
    				  aging = aging+ lineAging + linePassbyAging;
    				     			
    				  //为走货路径时效重新设置值
    				  freightRouteModel.aging = aging;
					  //为走货路径时效设值
					  addUpdateForm.findField('aging').setValue(aging/1000);
					  //设置保存按钮可用
					  me.getDockedItems()[1].items.items[2].setDisabled(false);
	            },
		        //保存失败
		        exception : function(response) {
		              var json = Ext.decode(response.responseText);
		              //设置保存按钮可用
					  me.getDockedItems()[1].items.items[2].setDisabled(false);
		              if(Ext.isEmpty(json)){
		              	infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.requestOvertime'));
		              }else{
		              	//失败消息
		              	infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
		              }
		        }
			});
    	}
    },
    items : [{
			name: 'sequence',//班次
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.sequence'),//'序号'
	        step:1,
	        allowBlank:false,
	        maxValue:999999,
	        minValue:1,
	        xtype : 'numberfield'
		},{
	        name: 'lineVirtualCode',
	        columnWidth: 1,
//			readOnly:true,
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.lineName'),//只读不可编辑，数据从线路中获取
	        valueField : 'virtualCode',// 值
	        xtype : 'commonlineselector',
	        lineSort:'BSE_LINE_SORT_TRANSFER',//线路类型
	        listeners:{
	        	change:function(item,records,objs){
	        		//线路名称
	        		var lineName = item.getRawValue();
	        		//线路Code
	        		var lineCode = item.getValue();
	        		var form = item.up('form');
	        		var infoModel = form.up('window').infoModel;
	        		var jsonData = {'freightRouteVo':{'lineVirtualCode':lineCode}};
	        		var url = baseinfo.realPath('queryLineStations.action');
	        		var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //查询出发站列表
							  var sourceOrgList = json.freightRouteVo.lineDeptList;
							  //获取线路简码
							  var lineSimpleCode = json.freightRouteVo.lineSimpleCode;
							  //设置线路简码
							  form.getForm().findField('simpleCode').setValue(lineSimpleCode);
							  sourceOrgList=Ext.isEmpty(sourceOrgList)?[]:sourceOrgList;
							  //获取出发站Sore
							  form.getForm().findField('orginalOrganizationCode').getStore().loadData(sourceOrgList);
							//获取到达站Sore
							  form.getForm().findField('destinationOrganizationCode').getStore().loadData(sourceOrgList);
							  if(form.isUpdate){//修改时赋值
								  form.getForm().findField('orginalOrganizationCode').setValue(infoModel.get('orginalOrganizationCode'));
								  form.getForm().findField('destinationOrganizationCode').setValue(infoModel.get('destinationOrganizationCode'));
							  }
			            },
				        //保存失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
		    xtype:'combobox',
			name: 'orginalOrganizationCode',
			fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
			columnWidth: .25,
			allowBlank:false,
			displayField: 'name',
			//value值字段
			valueField:'code', 
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			forceSelection:true,
			store: Ext.create('Foss.baseinfo.freightRoute.DeptStore'),
			listeners:{
	        	select:function(item,records,objs){
	        	}
	        }
		},{
			name: 'simpleCode',
			columnWidth: .25,
			readOnly:true,
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.simpleCode'),//只读不可编辑，数据从线路中获取
	        xtype : 'textfield'
		},{
		    xtype:'combobox',
			name: 'destinationOrganizationCode',
			fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
			columnWidth: .25,
			allowBlank:false,
			displayField: 'name',
			//value值字段
			valueField:'code', 
			queryMode:'local',
			triggerAction:'all',
			forceSelection:true,
			editable:false,
			store: Ext.create('Foss.baseinfo.freightRoute.DeptStore'),
			listeners:{
	        	select:function(item,records,objs){
	        		var form = item.up('form');
	        		//到达站名称
	        		var targetName = item.getRawValue();
	        		//到达站编码
	        		var targetCode = item.getValue();
	        		//获取出发站编码
	        		var sourceCode = form.getForm().findField('orginalOrganizationCode').getValue();
	        		//获取走货路径FORM对象
	        		var addUpdateForm = form.up('window').parent.up('window').getAddUpdateForm().getForm();
	        		//线路虚拟编码
	        		var lineVirtualCode = form.getForm().findField('lineVirtualCode').getValue();
	        		//获取三级产品编码
	        		var productCode = addUpdateForm.findField('transType').getValue();
	        		var jsonData = {'freightRouteVo':{'sourceOrgCode':sourceCode,'orgCode':targetCode,'lineVirtualCode':lineVirtualCode,'productCode':productCode}};
	        		var url = baseinfo.realPath('queryLineAging.action');
	        		var infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //获取产品时效
							  var aging = json.freightRouteVo.aging;
							  //产品时效在界面显示时需要除以1000
							  form.getForm().findField('aging').setValue(aging/1000);
			            },
				        //保存失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              infoGrid.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			name: 'aging',
			readOnly:true,
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.aging'),//只读不可编辑，数据从线路中获取 foss.baseinfo.aging '时效(小时)'
	        xtype : 'textfield'
		},{
			name: 'passbyAging',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.passbyAging'),// '经停时间(小时)'
	        step:0.001,
	        allowBlank:false,
	        maxValue:9999.999,
	        decimalPrecision:3,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'classes',
	        fieldLabel: baseinfo.freightRoute.i18n('foss.baseinfo.classes'),// '考核班次',
	        allowBlank:true,
	        maxValue:20,
	        allowDecimals: false,
	        allowNegative:false,
	        minValue:1,
	        xtype : 'numberfield'
		}],
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
				text :baseinfo.freightRoute.i18n('foss.baseinfo.cancel'),//取消
				handler :function(){
					me.up().close();
				}
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.reset'),//重置
				handler :function(){
					if(me.isUpdate){//如果是修改，加载上一次修改的
						me.getForm().updateRecord(new Foss.baseinfo.freightRoute.FreightRouteModel(me.up('window').infoModel));
					}else{//如果是新增，直接reset
						me.getForm().reset();//表格重置
					}
				} 
			},{
				text : baseinfo.freightRoute.i18n('foss.baseinfo.save'),//保存
				cls:'yellow_button',
				handler :function(){
					//防止数据重复提交，按钮灰掉
					this.disable();
					me.commitInfo();
				} 
		}];
		
		me.callParent([cfg]);
	}
});

/**
 * 新增走货路径窗口
 */
Ext.define('Foss.baseinfo.freightRoute.AddWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_freightRoute_AddWindow_Id',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.addFreightRoute'),
	width : 1000,
	height : 1000,
	isUpdate:false,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	standardList : null,//发车标准信息列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	sourceList:null,
	targetList:null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAddUpdateForm().getForm().reset();//表格重置
//			me.getStartStandardGrid().getStore().removeAll();//清除发车标准列表
			me.parent.getPagingToolbar().moveFirst();
			
			//把设置为不可编辑的该为可编辑
			me.getAddUpdateForm().getForm().getFields( ).each(function(item,index,length){
				if(item.getName( )=='aging'){
					//只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});//将FORM设置为可用
			
			//设置为不可用的按钮改为可用
			me.getAddUpdateForm().getDockedItems()[0].items.items[1].setDisabled(false);//重置按钮可用
			me.getAddUpdateForm().getDockedItems()[0].items.items[2].setDisabled(false);//保存按钮可用
			//走货路径线路添加按钮不可用
			me.getFreightRouteLineGrid().getDockedItems()[1].items.items[0].setDisabled(true);
			var containerPage = me.getNetworkGroupPage();
			//添加网点组按钮不可用
			containerPage.getCommitContainer().items.items[1].setDisabled(true);
			//清空出发/到达网点组列表数据
			containerPage.getTargetNetGroupGrid().getStore().removeAll();
			containerPage.getSourceNetGroupGrid().getStore().removeAll();
			me.getNetworkGroupGrid().getStore().removeAll();
			me.getFreightRouteLineGrid().getStore().removeAll();
			
		},
		beforeshow:function(me){//窗口显示之前事件
			//把设置为不可编辑的该为可编辑
//			me.getAddUpdateForm().getForm().getFields('doPacking').setReadOnly(true);
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.freightRoute.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	//网点组界面
	networkGroupPage :null,
	getNetworkGroupPage : function(){
		if(Ext.isEmpty(this.networkGroupPage)){
			this.networkGroupPage = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupContainer');
		}
		return this.networkGroupPage;
	},
	
	networkGroupGrid : null,
	//获取网点组列表
	getNetworkGroupGrid : function(){
		if(Ext.isEmpty(this.networkGroupGrid)){
			this.networkGroupGrid = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupGrid');
			this.networkGroupGrid.parent = this;
		}
		return this.networkGroupGrid;
	},
	freightRouteLineGrid:null,
	//获取走货路径线路列表
	getFreightRouteLineGrid : function(){
		if(Ext.isEmpty(this.freightRouteLineGrid)){
			this.freightRouteLineGrid = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineGrid');
			this.freightRouteLineGrid.parent = this;
		}
		return this.freightRouteLineGrid;
	},
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
		this.getAddUpdateForm().loadRecord(record);
		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm(),me.getNetworkGroupPage(),me.getNetworkGroupGrid(),me.getFreightRouteLineGrid()];
		me.callParent([ cfg ]);
	}
});

// 定义一个修改的窗口
Ext.define('Foss.baseinfo.freightRoute.UpdateWindow', {
	extend : 'Ext.window.Window',
//	id:'Foss_baseinfo_freightRoute_UpdateWindow_Id',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.updateFreightRoute'),
	width : 1000,
	height : 1000,
	isUpdate:true,//是否为修改，默认false
	parent : null, //父元素
	infoModel : null,//保存信息Model
	netGroupDtoList : null,//网点组信息列表
	lineDeptList : null,//可以打木架的外场
	lineList : null, //走货路径线路列表
	modal : true,
	closeAction : 'hidden',
	detailEntity : null,
	addUpdateForm : null,
	sourceList:null,
	targetList:null,
	//监听器
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			//form表单元素不能修改
			me.getAddUpdateForm().getForm().getFields().each(function(item){
				if(item.getName( )=='aging'|| item.getName() == 'doPacking'){
					
				}else{
					item.setReadOnly(false);
				}
			});//表格重置
			me.getAddUpdateForm().getForm().reset();//表格重置
			//清空可打木架外场缓存
			me.parent.getPagingToolbar().moveFirst();
			var containerPage = me.getNetworkGroupPage();
			//清空出发/到达网点组列表数据
			containerPage.getTargetNetGroupGrid().getStore().removeAll();
			containerPage.getSourceNetGroupGrid().getStore().removeAll();
			me.getNetworkGroupGrid().getStore().removeAll();
			me.getFreightRouteLineGrid().getStore().removeAll();
			//设置为不可用的按钮改为可用
//			me.getAddUpdateForm().getDockedItems()[0].items.items[1].setDisabled(false);//重置按钮可用
//			me.getAddUpdateForm().getDockedItems()[0].items.items[0].setDisabled(false);//保存按钮可用
			//设置为可用的按钮改为不可用
//			me.getStartStandardGrid().getDockedItems()[1].items.items[0].setDisabled(true);//新增按钮不可用
		},
		beforeshow:function(me){//窗口显示之前事件
			
		    me.getAddUpdateForm().getForm().loadRecord(new Foss.baseinfo.freightRoute.FreightRouteModel(me.infoModel));//加载线路信息
		    if(me.infoModel.defaultRoute == 'Y'){//判断是否默认走货路径
		    	me.getAddUpdateForm().getForm().findField('defaultRoute').setValue(true);
		    }else{
		    	me.getAddUpdateForm().getForm().findField('defaultRoute').setValue(false);
		    }
		    //加载前先清空列表数据
		    me.getNetworkGroupGrid().getStore().removeAll();
			if(!Ext.isEmpty(me.netGroupDtoList)){
				//加载网点组信息列表
				me.getNetworkGroupGrid().getStore().loadData(me.netGroupDtoList);
			}
			//加载前先清空列表数据
			me.getFreightRouteLineGrid().getStore().removeAll();
			if(!Ext.isEmpty(me.lineList)){
				//加载走货路径线路信息列表
				me.getFreightRouteLineGrid().getStore().loadData(me.lineList);
			}
			
			
			//选择器赋值
			//出发站
		    me.getAddUpdateForm().getForm().findField('orginalOrganizationCode').setCombValue(me.infoModel.orginalOrganizationName,me.infoModel.orginalOrganizationCode);
		    //到达站
		    me.getAddUpdateForm().getForm().findField('destinationOrganizationCode').setCombValue(me.infoModel.destinationOrganizationName,me.infoModel.destinationOrganizationCode);
			 //时效在显示的时候要除以1000
		    me.getAddUpdateForm().getForm().findField('aging').setValue(me.infoModel.aging/1000);
		    //走货路径线路添加按钮可用
			me.getFreightRouteLineGrid().down('button').setDisabled(false);
			//添加网点组按钮可用
			me.getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(false);
			if(me.infoModel.valid == 'Y'){//走货路径生效
				//form表单元素不能修改
				me.getAddUpdateForm().getForm().getFields().each(function(item){
					item.setReadOnly(true);
				});
				//走货路径线路添加按钮不可用
				me.getFreightRouteLineGrid().down('button').setDisabled(true);
				//添加网点组按钮不可用
				me.getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(true);
				//走货路径线路列表操作列隐藏
				me.getFreightRouteLineGrid().columns[0].hide();
				//网点组列表操作列隐藏
				me.getNetworkGroupGrid().columns[0].hide();
				
				//生效按钮不可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[3].setDisabled(true);
				//生效状态保存按钮不可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[2].setDisabled(true);
				//失效按钮可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[4].setDisabled(false);
			}else{
				//form表单元素不能修改
				me.getAddUpdateForm().getForm().getFields().each(function(item){
					if(item.getName( )=='aging'|| item.getName() == 'doPacking'){
						//只读的
						item.setReadOnly(true);
					}else{
						item.setReadOnly(false);
					}
				});
				//走货路径线路添加按钮不可用
				me.getFreightRouteLineGrid().down('button').setDisabled(false);
				//走货路径线路列表操作列显示
				me.getFreightRouteLineGrid().columns[0].show();
				//网点组列表操作列隐藏
				me.getNetworkGroupGrid().columns[0].show();
				//添加网点组按钮不可用
				me.getNetworkGroupPage().getCommitContainer().items.items[1].setDisabled(false);
				//生效按钮可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[3].setDisabled(false);
				//失效状态保存按钮可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[2].setDisabled(false);
				//失效按钮可用
				//失效按钮不可用
				me.getAddUpdateForm().getDockedItems()[0].items.items[4].setDisabled(true);
			}
		}
	},
	//获取FORM
	getAddUpdateForm : function() {
		if (this.addUpdateForm == null) {
			this.addUpdateForm = Ext.create('Foss.baseinfo.freightRoute.AddUpdateForm');
			this.addUpdateForm.isUpdate = this.isUpdate;
		}
		return this.addUpdateForm;
	},
	//网点组界面
	networkGroupPage :null,
	getNetworkGroupPage : function(){
		if(Ext.isEmpty(this.networkGroupPage)){
			this.networkGroupPage = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupContainer');
		}
		return this.networkGroupPage;
	},
	
	networkGroupGrid : null,
	//获取网点组列表
	getNetworkGroupGrid : function(){
		if(Ext.isEmpty(this.networkGroupGrid)){
			this.networkGroupGrid = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupGrid');
			this.networkGroupGrid.parent = this;
		}
		return this.networkGroupGrid;
	},
	freightRouteLineGrid:null,
	//获取走货路径线路列表
	getFreightRouteLineGrid : function(){
		if(Ext.isEmpty(this.freightRouteLineGrid)){
			this.freightRouteLineGrid = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineGrid');
			this.freightRouteLineGrid.parent = this;
		}
		return this.freightRouteLineGrid;
	},
	
	bindData : null,
	operationUrl : 'save',
	//信息列表
	infoGrid : null,
	getInfoGrid : function(){
		if(Ext.isEmpty(this.infoGrid)){
			this.infoGrid = Ext.getCmp('T_baseinfo-freightRoute_content').getFreightRouteGrid();
		}
	},
	resetWindow : function(record, operationUrl) {
//		this.getAddUpdateForm().loadRecord(record);
//		this.bindData = record;
		this.operationUrl = operationUrl;
	},
	//构造函数
	constructor : function(config) {
		var me = this, 
		   cfg = Ext.apply({}, config);
		me.items = [me.getAddUpdateForm(),me.getNetworkGroupPage(),me.getNetworkGroupGrid(),me.getFreightRouteLineGrid()];
		me.callParent([ cfg ]);
	}
});

/**
 * 修改走货路径线路窗口
 */
Ext.define('Foss.baseinfo.freightRoute.RouteLineUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.updateFreightRouteLine'),
	closable : true,
	modal : true,
	isUpdate:true,//是否为修改
	parent:null,//父元素
	resizable:false,
	closeAction : 'hide',
	infoModel : null,//绑定的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :400,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
			var basicForm = me.getInfoForm().getForm();
			basicForm.reset();//表格重置
			//清空出发站下拉框的数据
			basicForm.findField('orginalOrganizationCode').getStore().removeAll();
			//清空到达站下拉框的数据
			basicForm.findField('destinationOrganizationCode').getStore().removeAll();
		},
		beforeshow:function(me){//window显示所进行操作
			var basicForm = me.getInfoForm().getForm();
			//加载走货路径线路信息
			basicForm.loadRecord(me.infoModel);
			//选择器赋值
			//线路名称
			basicForm.findField('lineVirtualCode').setCombValue(me.infoModel.get('lineName'),me.infoModel.get('lineVirtualCode'));
			//时效显示时需要除以1000
			basicForm.findField('aging').setValue(me.infoModel.get('aging')/1000);
			//经停时间显示时需要除以1000
			basicForm.findField('passbyAging').setValue(me.infoModel.get('passbyAging')/1000);
		}
	},
    //获取FORM
	infoForm:null,
    getInfoForm : function(){
    	if(Ext.isEmpty(this.infoForm)){
    		this.infoForm = Ext.create('Foss.baseinfo.freightRoute.RouteLineForm');
    		this.infoForm.isUpdate = true;
    	}
    	return this.infoForm;
    },
    resetWindow : function(record) {
    	//加载数据
//		this.getInfoForm().loadRecord(record);
		this.infoModel = record;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getInfoForm()];
		me.callParent([cfg]);
	}
});


// 定义一个表格列表
Ext.define('Foss.baseinfo.freightRoute.FreightRouteGrid',{
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	id : 'Foss_baseinfo_freightRoute_FreightRouteGrid_Id',
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.queryGrid'),
	collapsible : true,
	animCollapse : true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 表格行可展开的插件
	plugins : [ {
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.baseinfo.freightRoute.InfoPanel'
	} ],
    
    addWindow : null,
	// 定义一个获取新增窗口的函数
	getAddWindow : function() {
		if (Ext.isEmpty(this.addWindow)) {
			this.addWindow = Ext.create('Foss.baseinfo.freightRoute.AddWindow');
			this.addWindow.parent = this;//父元素
		}
		return this.addWindow;
	},
	
	updateWindow : null,
	// 定义一个获取修改窗口的函数
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.updateWindow)) {
			this.updateWindow = Ext.create('Foss.baseinfo.freightRoute.UpdateWindow');
			this.updateWindow.parent = this;//父元素
		}
		return this.updateWindow;
	},
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.warnMsg'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		
		Ext.Msg.confirm(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.freightRoute.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.freightRoute.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放ID的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('virtualCode'));
				}
				
				var url = baseinfo.realPath('deleteFreightRoute.action');
				var jsonData = {'freightRouteVo':{ids:ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
	//Ajax请求
	requestAjax : function(url,jsonData,successFn,failFn)
	{
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
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
			}
		});
	},
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
	//分页组件
	pagingToolbar:null,
	getPagingToolbar:function(){
		var me = this;
		if(Ext.isEmpty(me.pagingToolbar)){
			me.pagingToolbar = Ext.create('Deppon.StandardPaging',{
					store:me.store,
					pageSize:10,
					prependButtons: true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
       return me.pagingToolbar;
	},
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.freightRoute.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
                            iconCls:'deppon_icons_edit',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.edit'),
							disabled:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteUpdateButton'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								//获取虚拟编码
								var virtualCode = record.get('virtualCode');
								var jsonData = {'freightRouteVo':{'freightRouteVirtualCode' : virtualCode}};
			    				//进行Ajax请求
                                Ext.Ajax.request({
									url:baseinfo.realPath('queryFreightRouteByVirtualCode.action'),
									jsonData:jsonData,
									//作废成功
									success : function(response) {
					                  	var json = Ext.decode(response.responseText);
						                  
					                  	//获得修改窗口
										var updateWindow = grid.up('grid').getUpdateWindow();
										//获取修改的Model信息
										updateWindow.infoModel = json.freightRouteVo.entity;
										//网点组列表
										updateWindow.netGroupDtoList = json.freightRouteVo.netGroupDtos;
										//获取可以打木架的外场
										updateWindow.lineDeptList = json.freightRouteVo.lineDeptList;
										//走货路径线路列表
										updateWindow.lineList = json.freightRouteVo.lineList;
//										updateWindow.resetWindow(record,'update');
										updateWindow.show();
					                  	//保存成功列表数据重新加载
					                  	grid.up('grid').getPagingToolbar().moveFirst();
					                  	
						                },
						            //保存失败
						            exception : function(response) {
					                  	var json = Ext.decode(response.responseText);
						            }
								});
							}
						},
						{
//							icon : '../images/baseinfo/delete.png',
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.void'),
							disabled:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteVoidButton'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.freightRoute.i18n('foss.baseinfo.notice'),
									msg:baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),//foss.baseinfo.deleteWarnMsg '作废后不可恢复，确认要作废么？'
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											var ids = new Array();//线路ID数组
											//通过走货路径虚拟编码作废线路
											ids.push(record.data.virtualCode);
											var url = baseinfo.realPath('deleteFreightRoute.action');
											var jsonData = {'freightRouteVo':{ids:ids}};
											//调用Ajax请求
											grid.up('grid').ajaxRequest(url,jsonData);
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'orginalOrganizationName',
				width : 150
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'destinationOrganizationName',
				flex : 1
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.transType'),
				// 关联model中的字段名
				dataIndex : 'transType',
				width : 150,
				renderer:function(v){
					return FossDataDictionary.rendererSubmitToDisplay (v,'BSE_FREIGHT_TRANS_TYPE');
				}
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.aging'),
				// 关联model中的字段名
				dataIndex : 'aging',
				width : 120,
				renderer:function(value){
					return value/1000;
				}
			} ,
//			{
//				// 字段标题
//				header : baseinfo.freightRoute.i18n('foss.baseinfo.doPackings'),
//				// 关联model中的字段名
//				dataIndex : 'doPacking',
//				width : 150,
//				renderer:function(value){
//				    if('N' == value){
//				    	return baseinfo.freightRoute.i18n('foss.baseinfo.no');
//				    }else if('Y' == value){
//				        return baseinfo.freightRoute.i18n('foss.baseinfo.yes');
//				    }
//				}
//			} ,
//			{
//				// 字段标题
//				header : baseinfo.freightRoute.i18n('foss.baseinfo.packingOrganizationName'),
//				// 关联model中的字段名
//				dataIndex : 'packingOrganizationName',
//				width : 150
//			} ,
			{
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.defaultRoute'),
				// 关联model中的字段名
				dataIndex : 'defaultRoute',
				width : 150,
				renderer:function(value){
				    if('N' == value){
				    	return baseinfo.freightRoute.i18n('foss.baseinfo.no');
				    }else if('Y' == value){
				        return baseinfo.freightRoute.i18n('foss.baseinfo.yes');
				    }
				}
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.status'),
				// 关联model中的字段名
				dataIndex : 'valid',
				width : 100,
				renderer:function(value){
				    if('Y' == value){
				    	return baseinfo.freightRoute.i18n('foss.baseinfo.valid');
				    }else{
				        return baseinfo.freightRoute.i18n('foss.baseinfo.invalid');
				    }
				}
			} ],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.FreightRouteStore');
				me.bbar = me.getPagingToolbar();
				me.getPagingToolbar().store = me.store;
				me.dockedItems = [{
					xtype : 'toolbar',
					dock : 'top',
					layout : 'column',
					defaults : {
						margin : '0 0 5 3'
					},
					items : [{
						xtype : 'button',
						text : baseinfo.freightRoute.i18n('foss.baseinfo.add'),
						hidden:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteAddButton'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用新增弹出窗口函数
							var model = Ext.create('Foss.baseinfo.freightRoute.FreightRouteModel');
                            this.addWindow = me.getAddWindow();
				           // this.addWindow.resetWindow(model,'save');
				            this.addWindow.show();
						}
					},{
						xtype : 'button',
						text : baseinfo.freightRoute.i18n('foss.baseinfo.void'),
						hidden:!baseinfo.freightRoute.isPermission('freightRoute/freightRouteVoidButton'),
						width : 80,
						handler : function() {// 作废多项选中的记录
						    //调用删除函数
							me.deleteInfos();
						}
					}
					]
		}], 
		me.callParent([ cfg ]);
	}
	});

/**
 * 新增走货路径线路窗口
 */
Ext.define('Foss.baseinfo.freightRoute.RouteLineAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.addFreightRouteLine'),
	closable : true,
	modal : true,
	isUpdate:false,//是否为修改，默认false
	parent:null,//父元素
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :400,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
			var basicForm = me.getInfoForm().getForm();
			basicForm.reset();//表格重置
			//清空出发站下拉框的数据
			basicForm.findField('orginalOrganizationCode').getStore().removeAll();
			//清空到达站下拉框的数据
			basicForm.findField('destinationOrganizationCode').getStore().removeAll();
		},
		beforeshow:function(me){//window显示所进行操作
			
		}
	},
	//获取FORM
	infoForm:null,
    getInfoForm : function(){
    	if(Ext.isEmpty(this.infoForm)){
    		this.infoForm = Ext.create('Foss.baseinfo.freightRoute.RouteLineForm');
    	}
    	return this.infoForm;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getInfoForm()];
		me.callParent([cfg]);
	}
});

/**
 * 修改网点组窗口
 */
Ext.define('Foss.baseinfo.freightRoute.NetGroupUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.updateNetGroup'),
	closable : true,
	modal : true,
	isUpdate:true,//是否为修改
	parent:null,//父元素
	resizable:false,
	closeAction : 'hide',
	infoModel : null,//绑定的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :800,
	height :400,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
		   me.getNetworkGroupPage().getSourceNetGroupGrid().getStore().removeAll();
		   me.getNetworkGroupPage().getTargetNetGroupGrid().getStore().removeAll();
		   //me.getNetworkGroupPage().getSourceOrgGrid().getStore().removeAll();
		  // me.getNetworkGroupPage().getTargetOrgGrid().getStore().removeAll();
		   
		},
		beforeshow:function(me){//window显示所进行操作
			//获取出发网点组列表
			var sourceGrid = me.getNetworkGroupPage().getSourceNetGroupGrid();
			//获取到达网点组列表
			var targetGrid = me.getNetworkGroupPage().getTargetNetGroupGrid();
			var sourceArray = me.infoModel.get('sourceOrganizationCodeList');
			//出发网点组名称列表
			var sourceNameArray = me.infoModel.get('sourceOrganizationNameList');
			//到达网点组名称列表
			var targetNameArray = me.infoModel.get('targetOrganizationNameList');
			var targetArray = me.infoModel.get('targetOrganizationCodeList');
			var arraySource = new Array();
			
			for(var i = 0; i < sourceArray.length;i++){
				//创建一个新的Model
				var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
				record.set('name',sourceNameArray[i]);
				record.set('code',sourceArray[i]);
				
				arraySource.push(record);
				//把选中的记录添加到出发网点组列表中
//				sourceGrid.getStore().insert(sourceGrid.getStore().count(),record);
			}
			sourceGrid.getStore().add(arraySource);
			var arrayTarget = new Array();
			for(var i = 0; i < targetArray.length;i++){
				//创建一个新的Model
				var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
				record.set('name',targetNameArray[i]);
				record.set('code',targetArray[i]);
				
				arrayTarget.push(record);
				//把选中的记录添加到出发网点组列表中
//				sourceGrid.getStore().insert(sourceGrid.getStore().count(),record);
			}
			targetGrid.getStore().add(arrayTarget);
			me.getNetworkGroupPage().getSourceOrgGrid().getStore().removeAll();
		    me.getNetworkGroupPage().getTargetOrgGrid().getStore().removeAll();
		    me.getNetworkGroupPage().getSourceOrgGrid().getStore().add(me.parent.up('window').sourceList);
		    me.getNetworkGroupPage().getTargetOrgGrid().getStore().add(me.parent.up('window').targetList);
			
		}
	},
    //网点组界面
	networkGroupPage :null,
	getNetworkGroupPage : function(config){
		if(Ext.isEmpty(this.networkGroupPage)){
			this.networkGroupPage = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupContainer');
			this.networkGroupPage.getCommitContainer().destroy();
			if(!Ext.isEmpty(config)){
				var sourceStore = config.parent.up('window').getNetworkGroupPage().getSourceOrgGrid().getStore();
				var targetStore = config.parent.up('window').getNetworkGroupPage().getTargetOrgGrid().getStore();
				var sourceArray = new Array();
				var targetArray = new Array();
				sourceStore.each(function(record){
					sourceArray.push(record);
				});
				targetStore.each(function(record){
					targetArray.push(record);
				});
				this.networkGroupPage.getSourceOrgGrid().getStore().add(sourceArray);
				this.networkGroupPage.getTargetOrgGrid().getStore().add(targetArray);
				
			}
		}
		return this.networkGroupPage;
	},
	
	//操作按钮
	operatContainer : null,
	getOperatContainer : function(){
		var me = this;
		if(Ext.isEmpty(this.operatContainer)){
			this.operatContainer = Ext.create('Ext.container.Container',{
//				flex:0.2,
//				buttonAlign : 'center',
				defaults: {
					margin:'60 0 40 0'
				},
				layout : 'column',
				items : [{
						border : false,
						columnWidth:.15,
						html: '&nbsp;'
					},{
						xtype : 'button',
						columnWidth:.13,
						text: baseinfo.freightRoute.i18n('foss.baseinfo.cancel'),
						handler: function() {
							me.hide();
						}
					},{
						border : false,
						columnWidth:.15,
						html: '&nbsp;'
					},{
						xtype : 'button',
						columnWidth:.13,
						text: baseinfo.freightRoute.i18n('foss.baseinfo.reset'),
						handler: function() {
							me.reload();
						}
					},{
						border : false,
						columnWidth:.15,
						html: '&nbsp;'
					},{
						columnWidth:.13,
						xtype : 'button',
						text: baseinfo.freightRoute.i18n('foss.baseinfo.save'),
						cls:'yellow_button',
						handler: function() {
							
							//获得出发网点组数据
							var sourcestore = me.getNetworkGroupPage().getSourceNetGroupGrid().getStore();
							//到达网点组数据
							var targetStore = me.getNetworkGroupPage().getTargetNetGroupGrid().getStore()
							//出发网点数组
							var sourceArray = new Array();
							//出发网点组名字数组
							var sourceNameArray = new Array();
							//到达网点组Code数组
							var targetArray = new Array();
							//出发网点组名字数组
							var targetNameArray = new Array();
							sourcestore.each(function(record){
								//保存部门编码
								sourceArray.push(record.get('code'));
								sourceNameArray.push(record.get('name'));
							});
							targetStore.each(function(record){
								//保存部门编码
								targetArray.push(record.get('code'));
								targetNameArray.push(record.get('name'));
							});
							//获得走货路径的虚拟编码
							var virtualCode = me.parent.up('window').infoModel.virtualCode;
							//获取走货路径的产品性质
							var transType =  me.parent.up('window').infoModel.transType;
							var isExpNetworkGroup =baseinfo.freightRoute.checkProductIsExp(transType);
							var record = new Foss.baseinfo.freightRoute.NetworkGroupModel(me.infoModel.data);
							record.set('sourceOrganizationCodeList',sourceArray);
							record.set('sourceOrganizationNameList',sourceNameArray);
							record.set('targetOrganizationCodeList',targetArray);
							record.set('targetOrganizationNameList',targetNameArray);
							record.set('freightRouteVirtualCode',virtualCode);
							record.set('expNetworkGroup',isExpNetworkGroup);
							var url = baseinfo.realPath('updateNetworkGroup.action');
							var jsonData = {'networkGroupVo':{'netGroupDto':record.data}};
							//进行Ajax请求
                            Ext.Ajax.request({
								url: url,
								jsonData: jsonData,
								//成功
								success : function(response) {
				                  	var json = Ext.decode(response.responseText);
					                
					                
					                //保存成功清空出发网点组、到达网点组所有数据
//									sourcestore.removeAll();
//									targetStore.removeAll();
                                    //先删除原始记录
//                                    me.parent.getStore().remove(me.infoModel);
				                  	//获得网点组信息列表
									var networkGroupGrid = me.parent.up('window').getNetworkGroupGrid();
									//网点组信息列表数据
									var record = json.networkGroupVo.netGroupDto;
									//先删除原始记录
									networkGroupGrid.getStore().remove(me.infoModel);
				                  	//保存成功列表数据重新加载
				                  	networkGroupGrid.getStore().add(record);
				                  	
				                  	me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				                  	me.hide();
					                },
					            //保存失败
					            exception : function(response) {
				                  	var json = Ext.decode(response.responseText);
				                  	me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					            }
							});
						
							
						}
					},{
						border : false,
						columnWidth:.15,
						html: '&nbsp;'
					}]
			});
		}
		return this.operatContainer;		
	},
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
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getNetworkGroupPage(config),me.getOperatContainer()];
		me.callParent([cfg]);
	}
});

/**
 * 查询网点组详细信息窗口
 */
Ext.define('Foss.baseinfo.freightRoute.NetGroupShowWindow',{
	extend : 'Ext.window.Window',
	title : '网点组详细信息',
	closable : true,
	modal : true,
	isUpdate:true,//是否为修改
	parent:null,//父元素
	resizable:false,
	closeAction : 'hide',
	infoModel : null,//绑定的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :600,
	height :350,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
		   me.getNetworkGroupPage().getSourceNetGroupGrid().getStore().removeAll();
		   me.getNetworkGroupPage().getTargetNetGroupGrid().getStore().removeAll();
		},
		beforeshow:function(me){//window显示所进行操作
			//获取出发网点组列表
			var sourceGrid = me.getNetworkGroupPage().getSourceNetGroupGrid();
			//获取到达网点组列表
			var targetGrid = me.getNetworkGroupPage().getTargetNetGroupGrid();
			var sourceArray = me.infoModel.get('sourceOrganizationCodeList');
			//出发网点组名称列表
			var sourceNameArray = me.infoModel.get('sourceOrganizationNameList');
			//到达网点组名称列表
			var targetNameArray = me.infoModel.get('targetOrganizationNameList');
			var targetArray = me.infoModel.get('targetOrganizationCodeList');
			var arraySource = new Array();
			
			for(var i = 0; i < sourceArray.length;i++){
				//创建一个新的Model
				var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
				record.set('name',sourceNameArray[i]);
				record.set('code',sourceArray[i]);
				
				arraySource.push(record);
				//把选中的记录添加到出发网点组列表中
//				sourceGrid.getStore().insert(sourceGrid.getStore().count(),record);
			}
			sourceGrid.getStore().add(arraySource);
			var arrayTarget = new Array();
			for(var i = 0; i < targetArray.length;i++){
				//创建一个新的Model
				var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
				record.set('name',targetNameArray[i]);
				record.set('code',targetArray[i]);
				
				arrayTarget.push(record);
				//把选中的记录添加到出发网点组列表中
//				sourceGrid.getStore().insert(sourceGrid.getStore().count(),record);
			}
			targetGrid.getStore().add(arrayTarget);
			
		}
	},
    //网点组界面
	networkGroupPage :null,
	getNetworkGroupPage : function(config){
		if(Ext.isEmpty(this.networkGroupPage)){
			this.networkGroupPage = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupContainer');
			this.networkGroupPage.getCommitContainer().destroy();//销毁添加网点组按钮
			this.networkGroupPage.getOperatSoureContainer().destroy();//销毁出发网点组操作按钮
			this.networkGroupPage.getOperatTargetContainer().destroy();//销毁到达网点组操作按钮
			this.networkGroupPage.getSourceOrgGrid().destroy();//销毁出发站对应营业部列表
			this.networkGroupPage.getTargetOrgGrid().destroy();//销毁到达站对应营业部列表
			/*if(!Ext.isEmpty(config)){
				var sourceStore = config.parent.up('window').getNetworkGroupPage().getSourceOrgGrid().getStore();
				var targetStore = config.parent.up('window').getNetworkGroupPage().getTargetOrgGrid().getStore();
				var sourceArray = new Array();
				var targetArray = new Array();
				sourceStore.each(function(record){
					sourceArray.push(record);
				});
				targetStore.each(function(record){
					targetArray.push(record);
				});
				this.networkGroupPage.getSourceOrgGrid().getStore().add(sourceArray);
				this.networkGroupPage.getTargetOrgGrid().getStore().add(targetArray);
				
			}*/
		}
		return this.networkGroupPage;
	},
	
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getNetworkGroupPage(config)];
		me.callParent([cfg]);
	}
});


/**
 * 定义添加网点组界面
 */
Ext.define('Foss.baseinfo.freightRoute.NetworkGroupContainer',{
	extend: 'Ext.container.Container',
	layout : 'hbox',
	height:200,
	border : 1,
	items : null,
	sourceList:null,
	targetList:null,
	//出发站对应营业部列表
	sourceOrgGrid : null,
	getSourceOrgGrid : function(){
		if(Ext.isEmpty(this.sourceOrgGrid)){
			this.sourceOrgGrid = Ext.create('Foss.baseinfo.freightRoute.SourceOrgGrid');
		}
		return this.sourceOrgGrid;
	},
	//出发网点组操作按钮
	operatSoureContainer : null,
	getOperatSoureContainer : function(){
		var me = this;
		if(Ext.isEmpty(this.operatSoureContainer)){
			this.operatSoureContainer = Ext.create('Ext.container.Container',{
				flex:0.2,
				buttonAlign : 'center',
				layout : 'column',
				items : [{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:15px;border:none',
						hide:true
					},{
						columnWidth : 1,
					    xtype : 'button',
						text:'-->',
						handler : function(){
							//获得选择对象
							var sourceSelModel = me.getSourceOrgGrid().getSelectionModel();
							var modelLength = sourceSelModel.getSelection().length;
							if(modelLength > 0){
								for(var i = 0; i < modelLength;i++){
									var deptName = sourceSelModel.getSelection()[i].data.name;
									var deptCode = sourceSelModel.getSelection()[i].data.code;
									//创建一个新的Model
									var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
									record.set('name',deptName);
									record.set('code',deptCode);
									
									var record1 = me.getSourceNetGroupGrid().getStore().findRecord('code',deptCode);
									if(record1 == null){//判断出发网点组列表是否存在该记录
										//把选中的记录添加到出发网点组列表中
										me.getSourceNetGroupGrid().getStore().insert(me.getSourceNetGroupGrid().getStore().count(),record);
									}
								}
							}
						}
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'->>',
						handler : function(){
							me.getSourceNetGroupGrid().getStore().removeAll();
							//获得选择对象
							var store = me.getSourceOrgGrid().getStore();
							store.each(function(record){
								me.getSourceNetGroupGrid().getStore().add(record);
							});
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<--',
						handler : function(){
							//获得选择对象
							var sourceSelModel = me.getSourceNetGroupGrid().getSelectionModel();
							var modelLength = sourceSelModel.getSelection().length;
							if(modelLength > 0){
								var records = new Array();
								for(var i = 0; i < modelLength;i++){
									records[i] = sourceSelModel.getSelection()[i];
								}
								//删除选中的记录
								me.getSourceNetGroupGrid().getStore().remove(records);
							}
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<<-',
						handler : function(){
								//获得选择对象
								var store = me.getSourceNetGroupGrid().getStore();
								var modelArray = new Array();
								store.each(function(record){
									modelArray.push(record);
								});
								//清除所有数据
								me.getSourceNetGroupGrid().getStore().removeAll();
                        }
					}]
			});
		}
		return this.operatSoureContainer;		
	},
	//出发网点组列表
	sourceNetGroupGrid : null,
	getSourceNetGroupGrid : function(){
		if(Ext.isEmpty(this.sourceNetGroupGrid)){
			this.sourceNetGroupGrid = Ext.create('Foss.baseinfo.freightRoute.SourceNetGroupGrid');
		}
		return this.sourceNetGroupGrid;
	},
	//到达站对应营业部列表
	targetOrgGrid : null,
	getTargetOrgGrid : function(){
		if(Ext.isEmpty(this.targetOrgGrid)){
			this.targetOrgGrid = Ext.create('Foss.baseinfo.freightRoute.TargetOrgGrid');
		}
		return this.targetOrgGrid;
	},
	//到达网点组操作按钮
	operatTargetContainer : null,
	getOperatTargetContainer : function(){
		var me = this;
		if(Ext.isEmpty(this.operatTargetContainer)){
			this.operatTargetContainer = Ext.create('Ext.container.Container',{
				flex:0.2,
				buttonAlign : 'center',
				layout : 'column',
				items : [{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:15px;border:none',
						hide:true
					},{
						columnWidth : 1,
					    xtype : 'button',
						text:'-->',
						handler : function(){
							//获得选择对象
							var selModel = me.getTargetOrgGrid().getSelectionModel();
							var modelLength = selModel.getSelection().length;
							if(modelLength > 0){
								for(var i = 0; i < modelLength;i++){
									var deptName = selModel.getSelection()[i].data.name;
									var deptCode = selModel.getSelection()[i].data.code;
									//创建一个新的Model
									var record = Ext.create('Foss.baseinfo.freightRoute.DepartmentModel');
									record.set('name',deptName);
									record.set('code',deptCode);
									
									var record1 = me.getTargetNetGroupGrid().getStore().findRecord('code',deptCode);
									if(record1 == null){//判断出发网点组列表是否存在该记录
										//把选中的记录添加到出发网点组列表中
										me.getTargetNetGroupGrid().getStore().insert(me.getTargetNetGroupGrid().getStore().count(),record);
									}
								}
							}
						}
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'->>',
						handler : function(){
							me.getTargetNetGroupGrid().getStore().removeAll();
							//获得选择对象
							var store = me.getTargetOrgGrid().getStore();
							store.each(function(record){
								me.getTargetNetGroupGrid().getStore().add(record);
							});
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<--',
						handler : function(){
							//获得选择对象
							var selModel = me.getTargetNetGroupGrid().getSelectionModel();
							var modelLength = selModel.getSelection().length;
							if(modelLength > 0){
								var records = new Array();
								for(var i = 0; i < modelLength;i++){
									records[i] = selModel.getSelection()[i];
								}
								//删除选中的记录
								me.getTargetNetGroupGrid().getStore().remove(records);
							}
                        }
					},{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:10px;border:none',
						hide:true
					},{
						columnWidth : 1,
						xtype : 'button',
						text:'<<-',
						handler : function(){
								//获得选择对象
								var store = me.getTargetNetGroupGrid().getStore();
								var modelArray = new Array();
								store.each(function(record){
									modelArray.push(record);
								});
								//清除所有数据
								me.getTargetNetGroupGrid().getStore().removeAll();
                        }
					}]
			});
		}
		return this.operatTargetContainer;		
	},
	//到达网点组列表
	targetNetGroupGrid : null,
	getTargetNetGroupGrid : function(){
		if(Ext.isEmpty(this.targetNetGroupGrid)){
			this.targetNetGroupGrid = Ext.create('Foss.baseinfo.freightRoute.TargetNetGroupGrid');
		}
		return this.targetNetGroupGrid;
	},
	//确认提交按钮
	commitContainer : null,
	getCommitContainer : function(){
		var me = this;
		if(Ext.isEmpty(this.commitContainer)){
			this.commitContainer = Ext.create('Ext.container.Container',{
				flex:0.45,
				buttonAlign : 'center',
				layout : 'column',
				items : [{
						columnWidth : 1,
						height : 0,
						xtype : 'container',
						style : 'padding-top:60px;border:none',
						hide:true
					},{
						columnWidth : 1,
					    xtype : 'button',
						text:baseinfo.freightRoute.i18n('foss.baseinfo.addNetGroup'),
						disabled:true,
						handler : function(){
							//获得出发网点组数据
							var sourcestore = me.getSourceNetGroupGrid().getStore();
							//到达网点组数据
							var targetStore = me.getTargetNetGroupGrid().getStore()
							//出发网点数组
							var sourceArray = new Array();
							//到达网点数组
							var targetArray = new Array();
							sourcestore.each(function(record){
								//保存部门编码
								sourceArray.push(record.get('code'));
							});
							targetStore.each(function(record){
								//保存部门编码
								targetArray.push(record.get('code'));
							});
							if(sourceArray.length == 0){
							   me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.sourceNetGroupWarnMsg'));
							   return false;
							}
							if(targetArray.length == 0){
							   me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.targetNetGroupWarnMsg'));
							   return false;
							}
							//获得走货路径的虚拟编码
							var virtualCode = me.up('window').infoModel.virtualCode;
							//获取走货路径的产品性质
							var transType = me.up('window').infoModel.transType;
							var isExpNetworkGroup =baseinfo.freightRoute.checkProductIsExp(transType);
							var url = baseinfo.realPath('addNetworkGroup.action');
							var jsonData = {'networkGroupVo':{'netGroupDto':{'sourceOrganizationCodeList':sourceArray,'targetOrganizationCodeList':targetArray,'freightRouteVirtualCode':virtualCode,'expNetworkGroup':isExpNetworkGroup}}};
							//进行Ajax请求
                            Ext.Ajax.request({
								url: url,
								jsonData: jsonData,
								//成功
								success : function(response) {
				                  	var json = Ext.decode(response.responseText);
					                
					                
					                //保存成功清空出发网点组、到达网点组所有数据
									sourcestore.removeAll();
									targetStore.removeAll();
				                  	//获得网点组信息列表
									var networkGroupGrid = me.up('window').getNetworkGroupGrid();
									//网点组信息列表数据
									var record = json.networkGroupVo.netGroupDto;
				                  	//保存成功列表数据重新加载
				                  	networkGroupGrid.getStore().add(record);
				                  	me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					                },
					            //保存失败
					            exception : function(response) {
				                  	var json = Ext.decode(response.responseText);
				                  	me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
					            }
							});
						}
					}]
			});
		}
		return this.commitContainer;		
	},
	
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
//                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
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
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		this.items = [ 
		    me.getSourceOrgGrid(),
		    me.getOperatSoureContainer(),
			me.getSourceNetGroupGrid(), 
			me.getTargetOrgGrid(), 
			me.getOperatTargetContainer(),
			me.getTargetNetGroupGrid(),
			me.getCommitContainer()
		];
		me.callParent([cfg]);
	}
});

/**
 * 定义出发站对应营业部列表
 */
Ext.define('Foss.baseinfo.freightRoute.SourceOrgGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 180,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.sourceOrgs'),
	cls:'checkerHide',
	hideHeaders: true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sourceOrgs'),
				// 关联model中的字段名
				dataIndex : 'name',
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.SourceOrgStore');
				
		me.callParent([ cfg ]);
	}
	});


/**
 * 定义出发网点组对应营业部列表
 */
Ext.define('Foss.baseinfo.freightRoute.SourceNetGroupGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	height : 180,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.sourceNetGroup'),
	cls:'checkerHide',
	hideHeaders: true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sourceNetGroup'),
				// 关联model中的字段名
				dataIndex : 'name',
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.SourceOrgStore');
				
		me.callParent([ cfg ]);
	}
	});
	
/**
 * 定义到达站对应营业部列表
 */
Ext.define('Foss.baseinfo.freightRoute.TargetOrgGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	height : 180,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.targetOrgs'),
	cls:'checkerHide',
	hideHeaders: true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.targetOrgs'),
				// 关联model中的字段名
				dataIndex : 'name',
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.SourceOrgStore');
				
		me.callParent([ cfg ]);
	}
	});
	
/**
 * 定义到达网点组列表
 */
Ext.define('Foss.baseinfo.freightRoute.TargetNetGroupGrid',{
	extend : 'Ext.grid.Panel',
	frame : true,
	height : 180,
	parent : null,
	flex:1,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.targetNetGroup'),
	cls:'checkerHide',
	hideHeaders: true,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	store : null,
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.targetNetGroup'),
				// 关联model中的字段名
				dataIndex : 'name',
				width : 120
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.SourceOrgStore');
				
		me.callParent([ cfg ]);
	}
	});

/**
 * 定义网点组列表
 */
Ext.define('Foss.baseinfo.freightRoute.NetworkGroupGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 180,
	stripeRows : true,
	parent : null,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.netGroupGrid'),
	collapsible : true,
	animCollapse : true,
	store : null,
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.warnMsg'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		Ext.Msg.confirm(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.freightRoute.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.freightRoute.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放ID的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				
				var url = baseinfo.realPath('deleteFreightRoute.action');
				var jsonData = {'freightRouteVo':{ids:ids}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	addStandardWindow : null,
	//获取新增发车标准窗口
	getAddStandardWindow : function(){
		if(Ext.isEmpty(this.addStandardwindow)){
			this.addStandardwindow = Ext.create('Foss.baseinfo.freightRoute.StandardAddWindow');
			//设置器父元素
			this.addStandardwindow.parent = this;
		}
		return this.addStandardwindow;
	},
	netGroupUpdateWindow : null,
	//获取网点组修改窗口
	getNetGroupUpdateWindow : function(){
		var me = this;
		if(Ext.isEmpty(this.netGroupUpdateWindow)){
			this.netGroupUpdateWindow = Ext.create('Foss.baseinfo.freightRoute.NetGroupUpdateWindow',{
				parent:me
			});
			//设置器父元素
//			this.netGroupUpdateWindow.parent = this;
		}
		return this.netGroupUpdateWindow;
	},
	netGroupShowWindow : null,
	//获取网点组修改窗口
	getNetGroupShowWindow : function(){
		var me = this;
		if(Ext.isEmpty(this.netGroupShowWindow)){
			this.netGroupShowWindow = Ext.create('Foss.baseinfo.freightRoute.NetGroupShowWindow',{
				parent:me
			});
			//设置器父元素
//			this.netGroupUpdateWindow.parent = this;
		}
		return this.netGroupShowWindow;
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看详细信息
	    	itemdblclick: function(view,record) {
				me.getNetGroupShowWindow().infoModel = record;
				me.getNetGroupShowWindow().show();
	    	}
	    };
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
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
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.freightRoute.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
							iconCls:'deppon_icons_edit',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.edit'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								var updateWindow = grid.up('grid').getNetGroupUpdateWindow();
								updateWindow.infoModel = record;
								updateWindow.show();
							}
						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.freightRoute.i18n('foss.baseinfo.notice'),
									msg:baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											
											var url = baseinfo.realPath('deleteNetworkGroup.action');
											var jsonData = {'networkGroupVo':{'netGroupDto':record.data}};
											//调用Ajax请求
											Ext.Ajax.request({
												url:url,
												jsonData:jsonData,
												//作废成功
												success : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //作废成功从Store里面清楚该数据
									                  grid.getStore().remove(record);
									                  //打印成功消息
									                  grid.up('grid').showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
									                },
									            //保存失败
									            exception : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //打印作废失败消息
									                  grid.up('grid').showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
									            }
											});
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sourceNetGroup'),
				// 关联model中的字段名
				dataIndex : 'sourceOrgName',
				flex : 1
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.targetNetGroup'),
				// 关联model中的字段名
				dataIndex : 'targetOrgName',
				flex : 1
//				width : 200
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupStore');
				me.listeners = me.getMyListeners(config);
				
		me.callParent([ cfg ]);
	}
	});
	
/**
 * 定义走货路径线路信息列表
 */
Ext.define('Foss.baseinfo.freightRoute.FreightRouteLineGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 180,
	stripeRows : true,
	parent : null,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.lineInfo'),
	collapsible : true,
	animCollapse : true,
	store : null,
	autoScroll:true,
	//作废函数
	deleteInfos : function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.warnMsg'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteNoticeMsg'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		Ext.Msg.confirm(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),function(e){
			Ext.MessageBox.buttonText.yes = baseinfo.freightRoute.i18n('foss.baseinfo.confirm');
			Ext.MessageBox.buttonText.no = baseinfo.freightRoute.i18n('foss.baseinfo.cancel');
			if(e == 'yes'){//询问是否删除
				var ids = new Array(); //定义一个存放ID的数组
				for(var i = 0 ; i<selections.length ; i++){
					ids.push(selections[i].get('id'));
				}
				
				var url = baseinfo.realPath('deleteFreightRouteLine.action');
				var jsonData = {'freightRouteLineVo':{'entity':{ids:ids}}};
				//调用Ajax请求
				me.ajaxRequest(url,jsonData);
			}
		});
	},
	addWindow : null,
	//获取新增走货路径线路窗口
	getAddWindow : function(){
		if(Ext.isEmpty(this.addWindow)){
			this.addWindow = Ext.create('Foss.baseinfo.freightRoute.RouteLineAddWindow');
			//设置器父元素
			this.addWindow.parent = this;
		}
		return this.addWindow;
	},
	updateWindow : null,
	//获取修改走货路径线路窗口
	getUpdateWindow : function(){
		if(Ext.isEmpty(this.updateWindow)){
			this.updateWindow = Ext.create('Foss.baseinfo.freightRoute.RouteLineUpdateWindow');
			//设置器父元素
			this.updateWindow.parent = this;
		}
		return this.updateWindow;
	},
	//Ajax请求
	ajaxRequest : function(url,jsonData){
		var me = this;
		Ext.Ajax.request({
			url:url,
			jsonData:jsonData,
			//作废成功
			success : function(response) {
                  var json = Ext.decode(response.responseText);
                  //保存成功列表数据重新加载
                  me.getPagingToolbar().moveFirst();
                  //打印成功消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
                },
            //保存失败
            exception : function(response) {
                  var json = Ext.decode(response.responseText);
                  //打印作废失败消息
                  me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
            }
		});
		
	},
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
	// 定义表格列信息
	columns : [{
				xtype : 'actioncolumn',
				width : 80,
				text : baseinfo.freightRoute.i18n('foss.baseinfo.operate'),
				align : 'center',
				items : [{
							iconCls:'deppon_icons_edit',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.edit'),
							// 编辑事件
							handler : function(grid, rowIndex,colIndex) {
								//获取选中的数据
								var record = grid.getStore().getAt(rowIndex);
								var updateWindow = grid.up('grid').getUpdateWindow();
								//绑定数据
								updateWindow.resetWindow(record);
								updateWindow.show();
							}
						},
						{
							iconCls:'deppon_icons_delete',
							tooltip : baseinfo.freightRoute.i18n('foss.baseinfo.void'),
							handler : function(grid, rowIndex,colIndex) {
								//作废操作提示窗口
								Ext.Msg.show({
									title:baseinfo.freightRoute.i18n('foss.baseinfo.notice'),
									msg:baseinfo.freightRoute.i18n('foss.baseinfo.deleteWarnMsg'),
									buttons:Ext.Msg.YESNO,
									icon: Ext.Msg.QUESTION, 
									fn:function(btn){
										if(btn == 'yes'){
											//获取选中的数据
											var record = grid.getStore().getAt(rowIndex)
											
											var url = baseinfo.realPath('deleteFreightRouteLine.action');
											var jsonData = {'freightRouteLineVo':{'entity':record.data}};
											//获取走货路径时效
//    				  						var aging = grid.up('window').getAddUpdateForm().getForm().findField('aging').getValue();
//    				  						var agingInt = parseInt(aging);
    				  						//获取走货路径数据对象
											var freightRouteModel = grid.up('window').infoModel;
											var aging = freightRouteModel.aging;
    				  						//获取当前记录的时效+经停时间
    				  						var countTime = record.get('aging')+record.get('passbyAging');
    				  						aging = aging-countTime;
											//调用Ajax请求
											Ext.Ajax.request({
												url:url,
												jsonData:jsonData,
												//作废成功
												success : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //作废成功从Store里面清楚该数据
									                  grid.getStore().remove(record);
									                  //打印成功消息
									                  grid.up('grid').showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
									                  grid.up('window').getAddUpdateForm().getForm().findField('aging').setValue(aging/1000);
									                  //为走货路径时效重新设置值
    				  									freightRouteModel.aging = aging;
									                },
									            //保存失败
									            exception : function(response) {
									                  var json = Ext.decode(response.responseText);
									                  //打印作废失败消息
									                  grid.up('grid').showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
									            }
											});
										}
									}
								});
							}
						} ]
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sequence'),
				// 关联model中的字段名
				dataIndex : 'sequence',
				width:60
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.lineName'),
				// 关联model中的字段名
				dataIndex : 'lineName',
				flex:1
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.simpleCode'),
				// 关联model中的字段名
				dataIndex : 'simpleCode',
				width : 120
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'orginalOrganizationName',
				width : 80
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'destinationOrganizationName',
				width : 80
			} , {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.aging'),
				// 关联model中的字段名
				dataIndex : 'aging',
				width : 80,
				renderer:function(value){
					return value/1000;
				}
			} , {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.passbyAging'),
				// 关联model中的字段名
				dataIndex : 'passbyAging',
				width : 120,
				renderer:function(value){
					return value/1000;
				}
			} ,{
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.classes'),// '考核班次',
				// 关联model中的字段名
				dataIndex : 'classes',
				width : 120
			} ],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineStore');
				me.tbar = [{
					text :baseinfo.freightRoute.i18n('foss.baseinfo.addLine'),
					disabled:true,
					handler :function(){
						me.getAddWindow().show();
					} 
				}];
		me.callParent([ cfg ]);
	}
	});

/**
 * 定义走货路径线路详细信息列表
 */
Ext.define('Foss.baseinfo.freightRoute.FreightRouteLineInfoGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 220,
	stripeRows : true,
	parent : null,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.lineInfo'),
	collapsible : true,
	animCollapse : true,
	store : null,
	
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sequence'),
				// 关联model中的字段名
				dataIndex : 'sequence',
				width:80
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.lineName'),
				// 关联model中的字段名
				dataIndex : 'lineName',
				flex:1
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.simpleCode'),
				// 关联model中的字段名
				dataIndex : 'simpleCode',
				width : 120
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.orginalOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'orginalOrganizationName',
				width : 80
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.destinationOrganizationCode'),
				// 关联model中的字段名
				dataIndex : 'destinationOrganizationName',
				width : 80
			} , {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.aging'),
				// 关联model中的字段名
				dataIndex : 'aging',
				width : 80
			} , {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.passbyAging'),
				// 关联model中的字段名
				dataIndex : 'passbyAging',
				width : 120
			} ,{
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.classes'),// '考核班次',
				// 关联model中的字段名
				dataIndex : 'classes',
				width : 120
			} ],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineStore');
				
		me.callParent([ cfg ]);
	}
	});
/**
 * 定义网点组详细信息列表
 */
Ext.define('Foss.baseinfo.freightRoute.NetworkGroupInfoGrid',{
	extend : 'Ext.grid.Panel',
	// 表格对象增加一个边框
	frame : true,
	height : 180,
	stripeRows : true,
	parent : null,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : baseinfo.freightRoute.i18n('foss.baseinfo.netGroupGrid'),
	collapsible : true,
	animCollapse : true,
	store : null,
	
	// 定义表格列信息
	columns : [ {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.sourceNetGroup'),
				// 关联model中的字段名
				dataIndex : 'sourceOrgName',
				flex : 1
			}, {
				// 字段标题
				header : baseinfo.freightRoute.i18n('foss.baseinfo.targetNetGroup'),
				// 关联model中的字段名
				dataIndex : 'targetOrgName',
				width : 250
			}],
			//构造函数
	 		constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupStore');
				
		me.callParent([ cfg ]);
	}
	});	

// 查看记录的详细信息
Ext.define('Foss.baseinfo.freightRoute.InfoPanel', {
	extend : 'Ext.panel.Panel',
	title : baseinfo.freightRoute.i18n('foss.baseinfo.detailInfo'),
	frame : true,
	infoForm : null,
	// 获取走货路径详细信息
	getInfoForm : function() {
		if (this.infoForm == null) {
			this.infoForm = Ext.create('Foss.baseinfo.freightRoute.DetailForm');
		}
		return this.infoForm;
	},
	networkGroupGrid : null,
	//获取网点组列表
	getNetworkGroupGrid : function(){
		if(Ext.isEmpty(this.networkGroupGrid)){
			this.networkGroupGrid = Ext.create('Foss.baseinfo.freightRoute.NetworkGroupInfoGrid');
			this.networkGroupGrid.parent = this;
		}
		return this.networkGroupGrid;
	},
	freightRouteLineGrid:null,
	//获取走货路径线路列表
	getFreightRouteLineGrid : function(){
		if(Ext.isEmpty(this.freightRouteLineGrid)){
			this.freightRouteLineGrid = Ext.create('Foss.baseinfo.freightRoute.FreightRouteLineInfoGrid');
			this.freightRouteLineGrid.parent = this;
		}
		return this.freightRouteLineGrid;
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [this.getInfoForm(),this.getNetworkGroupGrid(),this.getFreightRouteLineGrid()];
		this.callParent(arguments);
	},
	bindData : function(record) {
		var me = this;
		//获取线路虚拟编码
		//获取虚拟编码
		var virtualCode = record.get('virtualCode');
		var jsonData = {'freightRouteVo':{'freightRouteVirtualCode' : virtualCode}};
		//进行Ajax请求
        Ext.Ajax.request({
			url:baseinfo.realPath('queryFreightRouteByVirtualCode.action'),
			jsonData:jsonData,
			//成功
			success : function(response) {
              	var json = Ext.decode(response.responseText);
				//获取修改的Model信息
				var infoModel = new Foss.baseinfo.freightRoute.FreightRouteModel(json.freightRouteVo.entity);
				// 绑定表格数据到表单上
				me.getInfoForm().getForm().loadRecord(infoModel);
				var packingOrganizationName = me.getInfoForm().getForm().findField('packingOrganizationName');
				if(infoModel.get('doPacking')== 'N'){
					packingOrganizationName.hide();
					me.getInfoForm().query('label')[1].show();
					me.getInfoForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				}else if(infoModel.get('doPacking')== 'Y'){
					packingOrganizationName.show();
					me.getInfoForm().query('label')[1].hide();
					me.getInfoForm().doLayout();//让其重新调整，没有这句话，时效显示不出来
				}
				if(infoModel.get('defaultRoute') == 'Y'){//判断是否默认走货路径
			    	me.getInfoForm().getForm().findField('defaultRoute').setValue(true);
			    }else{
			    	me.getInfoForm().getForm().findField('defaultRoute').setValue(false);
			    }
			    //时效在显示时需要除以1000
			    me.getInfoForm().getForm().findField('aging').setValue(infoModel.get('aging')/1000)
				//网点组列表
				var netGroupDtoList = json.freightRouteVo.netGroupDtos;
				if(netGroupDtoList == null){
					netGroupDtoList = {};
				}
				//走货路径线路列表
				var lineList = json.freightRouteVo.lineList;
				if(lineList == null){
					lineList = {};
				}
				me.getNetworkGroupGrid().getStore().loadData(netGroupDtoList);
				me.getFreightRouteLineGrid().getStore().loadData(lineList);
              	
                },
            //查询数据失败
            exception : function(response) {
              	var json = Ext.decode(response.responseText);
            }
		});
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	baseinfo.freightRoute.init();
    //查询FORM
	var queryForm = Ext.create('Foss.baseinfo.freightRoute.QueryForm');
	//获取结果列表
	var queryResult = Ext.create('Foss.baseinfo.freightRoute.FreightRouteGrid');
	Ext.getCmp('T_baseinfo-freightRoute').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-freightRoute_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getFreightRouteGrid : function(){
			return queryResult;
		},
		items : [ queryForm, queryResult]
//		renderTo : 'T_baseinfo-freightRoute-body'
	}));
});

