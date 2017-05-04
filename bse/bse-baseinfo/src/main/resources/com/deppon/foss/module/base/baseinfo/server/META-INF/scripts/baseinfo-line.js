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



baseinfo.line.lineType = 'LINE_TYPE';//线路类型
baseinfo.line.productType = 'PRODUCT_TYPE';//时效类型

//定义一个数据常量
baseinfo.line.deptCodes ={};
/**
 * 获取当前用户权限（初始化用户数据权限）
 */
baseinfo.line.init =function(){
	Ext.Ajax.request({
		async:false,
		url:baseinfo.realPath('queryCurrentUserOrgForLine.action'),
		success:function(response){
			var json =Ext.decode(response.responseText);
			baseinfo.line.deptCodes =json.lineVo.userOrgList;
		},
		exception:function(response){
			var json =Ext.decode(response.responseText);
		}
	});
};
/**
 * 判断到达部门和始发部门中是否有一个属于用户数据权限部门
 */
baseinfo.line.isValidTheDeptCodes =function(sourceCode,targetCode){
	//默认好为false
	var flag =false,
	deptCodes =baseinfo.line.deptCodes;
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


//--------------------------------------baseinfo----------------------------------------
//线路实体
Ext.define('Foss.baseinfo.line.LineEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',// 虚拟编码
        type : 'string'
    },{
        name : 'simpleCode',// 线路简码
        type : 'string'
    },{
        name : 'lineName',// 线路名称(冗余)
        type : 'string'
    },{
        name : 'organizationCode',// 管理部门（车队）编码
        type : 'string'
    },{
        name : 'organizationName',// 管理部门（车队）名称
        type : 'string'
    },{
        name : 'orginalOrganizationCode',// 出发部门编码
        type : 'string'
    },{
        name : 'destinationOrganizationCode',// 到达部门编码
        type : 'string'
    },{
        name : 'orginalOrganizationName', // 出发部门名称（扩展）
        type : 'string'
    },{
        name : 'destinationOrganizationName', // 到达部门名称（扩展）
        type : 'string'
    },{
        name : 'orginalCityCode',// 出发城市(冗余)
        type : 'string'
    },{
        name : 'destinationCityCode',// 到达城市(冗余)
        type : 'string'
    },{
        name : 'orginalCityName', // 出发城市名称
        type : 'string'
    },{
        name : 'destinationCityName',// 到达城市名称
        type : 'string'
    },{
        name : 'transType',// 运输类型（汽运，空运）-始发到达
        type : 'string'
    },{
        name : 'lineType',// 线路类型（专线，偏线，空运）-中转
        type : 'string'
    },{
        name : 'lineSort',// 线路类别 （始发，到达，中转到中转）
        type : 'string'
    },{
        name : 'isDefault',// 是否默认线路 - 始发到达
        type : 'string'
    },{
        name : 'commonAging',// 普车时效（千分之小时）
        defaultValue : null
    },{
        name : 'fastAging', // 卡车时效（千分之小时）
        defaultValue : null
    },{
        name : 'otherAging',// 偏线时效（千分之小时）
        defaultValue : 0
    },{
        name : 'distance',// 线路距离(公里)
        defaultValue : null
    },{
        name : 'active', // 是否有效
        type : 'string'
    },{
        name : 'notes', // 备注
        type : 'string'
    },{
        name : 'valid', //是否生效
        type : 'string'
    },{
        name : 'isNorewardPunish', //是否不奖线路
        type : 'string'
    }]
});
//线段实体
Ext.define('Foss.baseinfo.line.LineItemEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'sequence', // 顺序
        defaultValue : null
    },{
        name : 'virtualCode', // 虚拟编码
        type : 'string'
    },{
        name : 'lineVirtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'orginalOrganizationCode', // 出发部门编码
        type : 'string'
    },{
        name : 'orginalOrganizationName', // 出发部门名称
        type : 'string'
    },{
        name : 'destinationOrganizationCode',// 到达部门编码
        type : 'string'
    },{
        name : 'destinationOrganizationName',// 到达部门名称
        type : 'string'
    },{
        name : 'orginalCityCode',   // 出发城市(冗余)
        type : 'string'
    },{
        name : 'orginalCityName',   // 出发城市名称
        type : 'string'
    },{
        name : 'destinationCityCode',  // 到达城市(冗余)
        type : 'string'
    },{
        name : 'destinationCityName',  // 到达城市名称
        type : 'string'
    },{
        name : 'commonAging',  // 普车时效(千分之小时)
        defaultValue : null
    },{
        name : 'fastAging', // 卡车时效(千分之小时)
        defaultValue : null
    },{
        name : 'passbyAging',  // 经停时间 (千分之小时)
        defaultValue : null
    },{
        name : 'distance', // 线段距离(公里)
        defaultValue : null
    },{
        name : 'active',  // 是否有效
        type : 'string'
    },{
        name : 'notes',   // 备注
        type : 'string'
    }]
});
//发车标准实体
Ext.define('Foss.baseinfo.line.DepartureStandardEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'order',// 班次
        defaultValue : null
    },{
        name : 'virtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'lineVirtualCode',// 线路虚拟编码
        type : 'string'
    },{
        name : 'leaveTime',// 准点发车时间(eg: 0200)
        type : 'string'
    },{
        name : 'arriveTime',// 准点到达时间(eg: 1645)
        type : 'string'
    },{
        name : 'arriveDay', // 准点到达时间的天数,默认是0
        defaultValue : null
    },{
        name : 'deadTime', // 中转到达货最晚到达时间(eg: 0120)
        type : 'string'
    },{
        name : 'deadDay',// 中转到达货最晚到达的天数,默认是0
        defaultValue : null
    },{
        name : 'active',// 是否有效
        type : 'string'
    },{
        name : 'productType',// 时效类型
        type : 'string'
    },{
        name : 'notes', // 备注
        type : 'string'
    }]
});






//------------------------------------model---------------------------------------------------
/**
 * 线路Store
 */
Ext.define('Foss.baseinfo.line.LineStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.line.LineEntity',//线路的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('searchTransferToTransferLineByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'lineVo.lineEntityList',//获取的数据
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
 * 线路表单
 */
Ext.define('Foss.baseinfo.line.QueryLineForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.line.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	labelWidth:100,
    	width:255,
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :265,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var dataStore = FossDataDictionary.getDataDictionaryStore(baseinfo.line.lineType);
		if(!Ext.isEmpty(dataStore)){
			var all = {valueCode:'',valueName:'全部'};
			dataStore.add(all);
		}
		me.items  = [{
			name: 'lineName',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.lineName'),//线路名称
	        xtype : 'textfield'
		},{
			name: 'orginalOrganizationCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),//出发站
        	xtype : 'dynamicorgcombselector',
            types : 'ORG',
            transferCenter:'Y',//--或者查询外场
            airDispatch  : 'Y'//--空运总调
		},{
			name: 'destinationOrganizationCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),//到达站
        	xtype : 'dynamicorgcombselector',
            types : 'ORG,CPPX,CPKY',
            transferCenter:'Y',//--或者查询外场
            airDispatch  : 'Y'//--空运总调
		},{
			name: 'orginalCityCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.departureCity'),//出发城市
	        xtype : 'commoncityselector'
		},{
			name: 'destinationCityCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.arrivalCity'),//到达城市
	        xtype : 'commoncityselector'
		},{
			name: 'lineType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:dataStore,
		    value:'',
		    fieldLabel: '线路类型',//线路类型
	        xtype : 'combo'
		},{
			name: 'simpleCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.simpleCode'),//线路简码
	        xtype : 'textfield'
		},{
			xtype : 'dynamicorgcombselector',
			//forceSelection : true,
			types:'ORG',
			transDepartment:'Y',
			name: 'organizationCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.organizationCode')//管理车队
		},{
			name: 'valid',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:baseinfo.getStore(null,null,['valueCode','valueName'],[{'valueCode':'Y','valueName':baseinfo.line.i18n('foss.baseinfo.valid')}//生效
		    ,{'valueCode':'N','valueName':baseinfo.line.i18n('foss.baseinfo.invalid')}//失效
		    ,{'valueCode':'','valueName':'全部'}]),
		    value:'',
		    fieldLabel: baseinfo.line.i18n('foss.baseinfo.lineStatus'),//线路类型
	        xtype : 'combo'
		},{
			name: 'isNorewardPunish',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:baseinfo.getStore(null,null,['valueCode','valueName'],[{'valueCode':'Y','valueName':baseinfo.line.i18n('foss.baseinfo.yes')}//不奖线路
		    ,{'valueCode':'N','valueName':baseinfo.line.i18n('foss.baseinfo.no')}//有奖线路
		    ,{'valueCode':'','valueName':'全部'}]),
		    value:'',
		    fieldLabel: '是否不奖线路',//是否不奖线路	
	        xtype : 'combo'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.line.isPermission('indexLine/operationQueryButton'),
			hidden:!baseinfo.line.isPermission('indexLine/operationQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			text : baseinfo.line.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.line.isPermission('indexLine/operationQueryButton'),
			hidden:!baseinfo.line.isPermission('indexLine/operationQueryButton'),
			cls:'yellow_button',
			handler : function() {
				if(me.getForm().isValid()){
					//获取
					var orginalOrganizationCode =me.getForm().findField('orginalOrganizationCode').getValue();
					var destinationOrganizationCode =me.getForm().findField('destinationOrganizationCode').getValue();
					if(Ext.isEmpty(orginalOrganizationCode)&&Ext.isEmpty(destinationOrganizationCode)){
						Ext.Msg.alert(baseinfo.line.i18n('foss.baseinfo.notice'),'始发、到达站请选择一个！');
						return;
					}
					//判断用户数据权限
					var flag =baseinfo.line.isValidTheDeptCodes(orginalOrganizationCode,destinationOrganizationCode);
					if(!flag){
						Ext.Msg.alert(baseinfo.line.i18n('foss.baseinfo.notice'),baseinfo.line.i18n('foss.baseinfo.authorityLimited'));
						return;
					}
					me.up().getLineGrid().getPagingToolbar().moveFirst();
				}
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 线路列表
 */
Ext.define('Foss.baseinfo.line.LineGridPanel', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.line.i18n('foss.baseinfo.lineInfo'),//线路信息
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.line.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
	//线段新增WINDOW
	lineAddWindow:null,
	getLineAddWindow:function(){
		if (this.lineAddWindow == null) {
			this.lineAddWindow = Ext.create('Foss.baseinfo.line.LineAddWindow');
			this.lineAddWindow.parent = this;//父元素
		}
		return this.lineAddWindow;
	},
	//线段修改WINDOW
	lineUpdateWindow:null,
	getLineUpdateWindow:function(){
		if (this.lineUpdateWindow == null) {
			this.lineUpdateWindow = Ext.create('Foss.baseinfo.line.LineUpdateWindow');
			this.lineUpdateWindow.parent = this;//父元素
		}
		return this.lineUpdateWindow;
	},
	//线路详细查询
	lineShowWindow:null,
	getLineShowWindow:function(){
		if(this.lineShowWindow==null){
			this.lineShowWindow =Ext.create('Foss.baseinfo.line.LineShowWindow');
			this.lineShowWindow.parent =this;
		}
		return this.lineShowWindow;
	},
	//作废线路
	toVoidLine: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		var lineVirtualCodes = new Array();//线路ID数组
		for(var i = 0 ; i<selections.length ; i++){
			lineVirtualCodes.push(selections[i].get('virtualCode'));
		}
		baseinfo.showQuestionMes(baseinfo.line.i18n('foss.baseinfo.wantSetAsideTheseLines'),function(e){//是否要作废这些线路？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'lineVo':{'lineVirtualCodes':lineVirtualCodes}};
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
				var url = baseinfo.realPath('deleteTransferToTransferLine.action');
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
			text : baseinfo.line.i18n('foss.baseinfo.operate'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.line.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.line.isPermission('indexLine/operationUpdateButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var lineVirtualCode = record.get('virtualCode');//线路虚拟编码
    				var params = {'lineVo':{'lineVirtualCode':lineVirtualCode}};
    				var successFun = function(json){
    					var updateWindow = me.getLineUpdateWindow();
    					updateWindow.lineEntity = json.lineVo.lineEntity;//线路
    					updateWindow.lineItemEntityList = json.lineVo.lineItemEntityList;//线段
    					updateWindow.departureStandardEntityList = json.lineVo.departureStandardEntityList;//发车标准
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时');//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('saerchTransferToTransferLineInfo.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				//查询信息详细
				iconCls: 'deppon_icons_showdetail',
                tooltip: baseinfo.line.i18n('foss.baseinfo.details'),//查询
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var lineVirtualCode = record.get('virtualCode');//线路虚拟编码
    				var params = {'lineVo':{'lineVirtualCode':lineVirtualCode}};
    				var successFun = function(json){
    					var showWindow = me.getLineShowWindow();
    					showWindow.lineEntity = json.lineVo.lineEntity;//线路
    					showWindow.lineItemEntityList = json.lineVo.lineItemEntityList;//线段
    					showWindow.departureStandardEntityList = json.lineVo.departureStandardEntityList;//发车标准
    					showWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时');//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('saerchTransferToTransferLineInfo.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }	
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.line.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.line.isPermission('indexLine/operationVoidButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	baseinfo.showQuestionMes(baseinfo.line.i18n('foss.baseinfo.wantVoidLines'),function(e){//是否要作废这条线路？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var lineVirtualCodes = new Array();
            				//获取选中的数据
            				var record=grid.getStore().getAt(rowIndex);
            				lineVirtualCodes.push(record.get('virtualCode'));
            				var params = {'lineVo':{'lineVirtualCodes':lineVirtualCodes}};
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
            				var url = baseinfo.realPath('deleteTransferToTransferLine.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : baseinfo.line.i18n('foss.baseinfo.lineName'),//线路名称
			dataIndex : 'lineName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.simpleCode'),//线路简码
			dataIndex : 'simpleCode',
			width:80
		},{
			text : '线路类型',//线路类型
			width:65,
			dataIndex : 'lineType',
			renderer:function(value){
				var store = FossDataDictionary.getDataDictionaryStore(baseinfo.line.lineType);
				var returnValue = '';
				if(!Ext.isEmpty(store)){
					store.each(function(record){
						if(record.get('valueCode')==value){
							returnValue = record.get('valueName');
						}
					});
				}
				return returnValue;
			}
		},{
			text : baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),//出发站
			dataIndex : 'orginalOrganizationName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.departureCity'),//出发城市
			dataIndex : 'orginalCityName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),//到达站
			dataIndex : 'destinationOrganizationName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.arrivalCity'),//到达城市
			dataIndex : 'destinationCityName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.lineDistanceKm'),//线路距离（公里）
			dataIndex : 'distance'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.organizationCode'),//管理车队
			dataIndex : 'organizationName'
		},{
			text : baseinfo.line.i18n('foss.baseinfo.effectiveOrFailure'),//生效/失效
			dataIndex : 'valid',
			renderer:function(value){
				if(value=='Y'){//'Y'表示生效
					return baseinfo.line.i18n('foss.baseinfo.valid');//
				}else{//'N'表示失效
					return  baseinfo.line.i18n('foss.baseinfo.invalid');//
				}
			}
		},{
			text : '是否不奖线路',//是否不奖线路（Y/N）
			dataIndex : 'isNorewardPunish',
			renderer:function(value){
				if(value=='Y'){//'Y'可奖罚线路
					return baseinfo.line.i18n('foss.baseinfo.yes');//
				}else{//'N'或空表示不奖可以罚线路
					return  baseinfo.line.i18n('foss.baseinfo.no');//
				}
			}
		}];
		me.store = Ext.create('Foss.baseinfo.line.LineStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//查询线路大查询，查询条件组织
								'lineVo.lineEntity.valid':queryForm.getForm().findField('valid').getValue(),
								'lineVo.lineEntity.lineName':queryForm.getForm().findField('lineName').getValue(), // 线路名称
								'lineVo.lineEntity.orginalOrganizationCode':
									queryForm.getForm().findField('orginalOrganizationCode').getValue(),//出发站
								'lineVo.lineEntity.destinationOrganizationCode':
									queryForm.getForm().findField('destinationOrganizationCode').getValue(),//到达站
								'lineVo.lineEntity.orginalCityCode':queryForm.getForm().findField('orginalCityCode').getValue(),//出发城市
								'lineVo.lineEntity.destinationCityCode':queryForm.getForm().findField('destinationCityCode').getValue(),//到达城市
								'lineVo.lineEntity.lineType':queryForm.getForm().findField('lineType').getValue(),//线路类型
								'lineVo.lineEntity.isNorewardPunish':queryForm.getForm().findField('isNorewardPunish').getValue(),//是否不奖线路
								'lineVo.lineEntity.simpleCode':queryForm.getForm().findField('simpleCode').getValue(),//线路简码
								'lineVo.lineEntity.organizationCode':queryForm.getForm().findField('organizationCode').getValue()//管理车队
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
			text : baseinfo.line.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.line.isPermission('indexLine/operationAddButton'),
			hidden:!baseinfo.line.isPermission('indexLine/operationAddButton'),
			handler : function() {
				me.getLineAddWindow().show();
			}
        },'-',{
			text : baseinfo.line.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.line.isPermission('indexLine/operationVoidButton'),
			hidden:!baseinfo.line.isPermission('indexLine/operationVoidButton'),
			handler :function(){
				me.toVoidLine();
			} 
		},'-',{
			text : baseinfo.line.i18n('foss.baseinfo.export'),//导出
			disabled:!baseinfo.line.isPermission('indexLine/operationExportButton'),
			hidden:!baseinfo.line.isPermission('indexLine/operationExportButton'),
			handler :function(){
				var queryForm = me.up().getQueryForm();
				
				   if (queryForm != null) {
						var queryParams = queryForm.getValues();
						
						Ext.MessageBox.buttonText.yes = baseinfo.line.i18n('foss.baseinfo.confirm'); //确定 
						Ext.MessageBox.buttonText.no = baseinfo.line.i18n('foss.baseinfo.cancel'); //取消
						if(!Ext.fly('downloadOriginatingForm')){
							    var frm = document.createElement('form');
							    frm.id = 'downloadOriginatingForm';
							    frm.style.display = 'none';
							    document.body.appendChild(frm);
						}
						
						Ext.Msg.confirm( baseinfo.line.i18n('foss.baseinfo.tipInfo'), baseinfo.line.i18n('foss.baseinfo.exportMsg'), function(btn,text){
							if(btn == 'yes'){
								var params = {
										'lineVo.lineEntity.lineName':queryParams.lineName,
										'lineVo.lineEntity.orginalOrganizationCode':queryParams.orginalOrganizationCode,
										'lineVo.lineEntity.destinationOrganizationCode':queryParams.destinationOrganizationCode,
										'lineVo.lineEntity.orginalCityCode':queryParams.orginalCityCode,
										'lineVo.lineEntity.destinationCityCode':queryParams.destinationCityCode,
										'lineVo.lineEntity.lineType':queryParams.lineType,
										'lineVo.lineEntity.simpleCode':queryParams.simpleCode,
										'lineVo.lineEntity.organizationCode':queryParams.organizationCode,
										'lineVo.lineEntity.valid':queryParams.valid
									};
								Ext.Ajax.request({
									url:baseinfo.realPath('exportTransferToTransferLine.action'),
									form: Ext.fly('downloadOriginatingForm'),
									params:params,
									method:'post',
									isUpload: true,
									success:function(response){
										var result = Ext.decode(response.responseText);
									},
									failure:function(response){
										var result = Ext.decode(response.responseText);
										top.Ext.MessageBox.alert(baseinfo.line.i18n('foss.baseinfo.exportFailed'),result.message);
									}
								});
							}
						});
					}
				
				
//				
//				
//				var lineExport = '';
//				var lineName = queryForm.getForm().findField('lineName').getValue(); // 线路名称
//				var orginalOrganizationCode = queryForm.getForm().findField('orginalOrganizationCode').getValue(); //出发站
//				var destinationOrganizationCode = queryForm.getForm().findField('destinationOrganizationCode').getValue(); //到达站
//				var orginalCityCode = queryForm.getForm().findField('orginalCityCode').getValue(); //出发城市
//				var destinationCityCode = queryForm.getForm().findField('destinationCityCode').getValue(); //到达城市
//				var lineType = queryForm.getForm().findField('lineType').getValue(); //线路类型
//				var simpleCode = queryForm.getForm().findField('simpleCode').getValue(); //线路简码
//				var organizationCode = queryForm.getForm().findField('organizationCode').getValue(); //管理车队
//				var valid = queryForm.getForm().findField('valid').getValue(); //管理车
//				
//				if(!Ext.isEmpty(lineName)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.lineName='+lineName;
//					}else{
//						lineExport = 'lineVo.lineEntity.lineName='+lineName;
//					}
//				}
//				if(!Ext.isEmpty(orginalOrganizationCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.orginalOrganizationCode='+orginalOrganizationCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.orginalOrganizationCode='+orginalOrganizationCode;
//					}
//				}
//				if(!Ext.isEmpty(destinationOrganizationCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.destinationOrganizationCode='+destinationOrganizationCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.destinationOrganizationCode='+destinationOrganizationCode;
//					}
//				}
//				if(!Ext.isEmpty(orginalCityCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.orginalCityCode='+orginalCityCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.orginalCityCode='+orginalCityCode;
//					}
//				}
//				if(!Ext.isEmpty(destinationCityCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.destinationCityCode='+destinationCityCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.destinationCityCode='+destinationCityCode;
//					}
//				}
//				if(!Ext.isEmpty(lineType)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.lineType='+lineType;
//					}else{
//						lineExport = 'lineVo.lineEntity.lineType='+lineType;
//					}
//				}
//				if(!Ext.isEmpty(simpleCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.simpleCode='+simpleCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.simpleCode='+simpleCode;
//					}
//				}
//				if(!Ext.isEmpty(organizationCode)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.organizationCode='+organizationCode;
//					}else{
//						lineExport = 'lineVo.lineEntity.organizationCode='+organizationCode;
//					}
//				}
//				if(!Ext.isEmpty(valid)){
//					if(!Ext.isEmpty(lineExport)){
//						lineExport = lineExport+'&'+'lineVo.lineEntity.valid='+valid;
//					}else{
//						lineExport = 'lineVo.lineEntity.valid='+valid;
//					}
//				}
//				var url = baseinfo.realPath('exportTransferToTransferLine.action');
//				if(!Ext.isEmpty(lineExport)){
//					url = url+'?'+lineExport;
//				}
//				window.location=url;
//				lineExport = '';
			} 
		} ];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * @description 线路主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	//初始化
	baseinfo.line.init();
	if (Ext.getCmp('T_baseinfo-indexLine_content')) {
		return;
	};
	var queryForm = Ext.create('Foss.baseinfo.line.QueryLineForm');//查询FORM
	var lineGrid = Ext.create('Foss.baseinfo.line.LineGridPanel');//查询结果GRID
	Ext.getCmp('T_baseinfo-indexLine').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-indexLine_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getLineGrid : function() {
			return lineGrid;
		},
		items : [queryForm, lineGrid]
//		renderTo : 'T_baseinfo-indexLine-body'
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增线路信息
 */
Ext.define('Foss.baseinfo.line.LineAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.newLineInformation'),//新增线路信息
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineGridPanel）
	modal : true,
	lineEntity:null,//保存的线路实体
	resizable:false,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :1000,
	height :800,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getLineForm().getForm().reset();//表格重置
			me.getLineTab().getLineItemInfoGrid().getStore().removeAll();//清除线段表
			me.getLineTab().getDepartureStandardEntityGrid().getStore().removeAll();//清楚发车标准表
			//me.parent.getPagingToolbar().moveFirst();
			//把设置为不可编辑的该为可编辑
			me.getLineForm().getForm().getFields( ).each(function(item,index,length){
				if(item.getName( )=='destinationCityCode'
					||item.getName( )=='orginalCityCode'
						||item.getName( )=='commonAging'||item.getName( )=='fastAging'||item.getName( )=='lineName'){
					//出发城市到大城市，普车时效，卡车时效,线路名称是只读的
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});//将FORM设置为可用
			//设置为不可用的按钮改为可用
			me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
			me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
			me.getLineForm().getDockedItems()[1].items.items[3].show();//失效和生效显示
			me.getLineForm().getDockedItems()[1].items.items[4].show();
			//设置为可用的按钮改为不可用
			me.getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(true);//新增线段按钮不可用
			me.getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(true);//新增发车标准按钮不可用
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			if(!Ext.isEmpty(me.getLineForm().getDockedItems()[1])){
				me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(true);//失效和生效不可用
				me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(true);
			}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
				me.getLineForm().getDockedItems()[0].items.items[3].setDisabled(true);//失效和生效不可用
				me.getLineForm().getDockedItems()[0].items.items[4].setDisabled(true);
			}
			
		}
	},
	//新增线路FORM
	lineForm:null,
    getLineForm : function(){
    	if(Ext.isEmpty(this.lineForm)){
    		this.lineForm = Ext.create('Foss.baseinfo.line.LineForm');
    	}
    	return this.lineForm;
    },
    lineTab:null,//tab里面放了线段信息和发车标准
    getLineTab:function(){
    	if(Ext.isEmpty(this.lineTab)){
    		this.lineTab = Ext.create('Foss.baseinfo.line.LineTab');
    	}
    	return this.lineTab;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getLineForm(),me.getLineTab()];
		me.callParent([cfg]);
	}
});
/**
 * 修改线路信息
 */
Ext.define('Foss.baseinfo.line.LineUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.modifyLineInformation'),//新增线路信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineGridPanel）
	lineEntity:null,//修改的线路信息
	lineItemEntityList:null,//修改的线段数据集
	departureStandardEntityList:null,//修改的发车标准数据集
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :1000,
	height :800,
	listeners:{
		beforehide:function(me){
			me.getLineForm().getForm().getFields().each(function(item){
				var name = item.getName();
                if(name=='lineType'||name=='lineName'||name=='commonAging'||name=='fastAging'||name=='destinationCityName'||name=='orginalCityName'){
					
				}else{
					item.setReadOnly(false);
				}
			});//表格重置
			me.getLineForm().getForm().reset();//表格重置
			me.getLineTab().getLineItemInfoGrid().getStore().removeAll();//清除线段表
			me.getLineTab().getDepartureStandardEntityGrid().getStore().removeAll();//清楚发车标准表
			//me.parent.getPagingToolbar().moveFirst();
			me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
			me.getLineForm().getDockedItems()[1].items.items[0].setDisabled(false);//取消按钮可用
			me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
			me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
			me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(true);//失效按钮不可用
			me.getLineForm().getDockedItems()[1].items.items[3].show();//失效和生效显示
			me.getLineForm().getDockedItems()[1].items.items[4].show();
			me.getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增线段按钮不可用
			me.getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增发车标准按钮不可用
		},
		beforeshow:function(me){
			me.getLineTab();
			
			if(me.lineEntity.valid=='Y'&&me.lineEntity.lineType=='ZX'){//如果是生效则不可修改
				me.getLineForm().getForm().getFields().each(function(item){
					//生效状态下出发站、到达站、普车时效、卡车时效、线路名称、出发城市、到达城市、线路类型不允许修改
					if(item.getName( )=='orginalOrganizationCode' 
						|| item.getName( )=='destinationOrganizationCode'
							||item.getName( )=='destinationCityName'||item.getName( )=='orginalCityName'||item.getName( )=='commonAging'
								||item.getName( )=='fastAging'||item.getName( )=='lineName'||item.getName()== 'lineType'){
						
						item.setReadOnly(true);
					}else{
						item.setReadOnly(false);
					}
//					item.setReadOnly(true);
				});
//				me.getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(true);//新增线段按钮不可用
				//新增发车标准按钮不可用
				me.getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(true);
				
				if(!Ext.isEmpty(me.getLineForm().getDockedItems()[1])){
//					me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
//					me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(false);//失效按钮可用
				}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
//					me.getLineForm().getDockedItems()[0].items.items[1].setDisabled(true);//重置按钮不可用
//					me.getLineForm().getDockedItems()[0].items.items[2].setDisabled(true);//保存按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[4].setDisabled(false);//失效按钮可用
				}
			}else{
				me.getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增线段按钮可用
				me.getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增发车标准按钮可用
				if(!Ext.isEmpty(me.getLineForm().getDockedItems()[1])){
					me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(false);//生效按钮可用
					me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(true);//失效按钮不可用
				}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
					me.getLineForm().getDockedItems()[0].items.items[3].setDisabled(false);//生效按钮可用
					me.getLineForm().getDockedItems()[0].items.items[4].setDisabled(true);//失效按钮不可用
				}
				
			}
			
			me.lineEntity.commonAging = me.lineEntity.commonAging/1000;//从后台查出的值（普车时效、卡车时效、其它时效都是乘1000的）要进行处理
			me.lineEntity.fastAging = me.lineEntity.fastAging/1000;
			if(Ext.isEmpty(me.lineEntity.otherAging)){
				me.lineEntity.otherAging = me.lineEntity.otherAging/1000;
			};
			//获取出发站
	        var orginalOrganizationCode = me.getLineForm().getForm().findField('orginalOrganizationCode');
	        //获取到达站
	        var destinationOrganizationCode = me.getLineForm().getForm().findField('destinationOrganizationCode');
	        if(me.lineEntity.isNorewardPunish==null || me.lineEntity.isNorewardPunish==''){
	        	me.lineEntity.isNorewardPunish='N';
	        }
	        /*orginalOrganizationCode.getStore().on('remove',function(){
	             me.getLineForm().getForm().findField('orginalOrganizationCode').setCombValue(me.lineEntity.orginalOrganizationName,me.lineEntity.orginalOrganizationCode);//出发站
	        });
	        destinationOrganizationCode.getStore().on('remove',function(){
	             me.getLineForm().getForm().findField('destinationOrganizationCode').setCombValue(me.lineEntity.destinationOrganizationName,me.lineEntity.destinationOrganizationCode);//到达站
	        });*/
			me.getLineForm().getForm().loadRecord(new Foss.baseinfo.line.LineEntity(me.lineEntity));//加载线路数据
			
			/*orginalOrganizationCode.getStore().fireEvent('remove');
			destinationOrganizationCode.getStore().fireEvent('remove');*/
			if(me.lineEntity.lineType=='ZX'){
				me.getLineForm().getForm().findField('orginalOrganizationCode').setCombValue(me.lineEntity.orginalOrganizationName,me.lineEntity.orginalOrganizationCode);//出发站
			 	me.getLineForm().getForm().findField('destinationOrganizationCode').setCombValue(me.lineEntity.destinationOrganizationName,me.lineEntity.destinationOrganizationCode);//到达站
			}
			 if(me.lineEntity.organizationName==null||me.lineEntity.organizationCode==null){
				 me.getLineForm().getForm().findField('organizationCode').setCombValue(null,null);//管理车队
			    }else{
			    me.getLineForm().getForm().findField('organizationCode').setCombValue(me.lineEntity.organizationName,me.lineEntity.organizationCode);//管理车队
			    }
			//me.getLineForm().getForm().findField('organizationCode').setCombValue(me.lineEntity.organizationName,me.lineEntity.organizationCode);//管理车队
			if(!Ext.isEmpty(me.lineItemEntityList)){
				for(var i= 0;i<me.lineItemEntityList.length;i++){
					me.lineItemEntityList[i].commonAging = me.lineItemEntityList[i].commonAging/1000;//普车时效
					me.lineItemEntityList[i].fastAging = me.lineItemEntityList[i].fastAging/1000;//卡车时效
					me.lineItemEntityList[i].passbyAging = me.lineItemEntityList[i].passbyAging/1000;//经停时间 
				}
				me.getLineTab().getLineItemInfoGrid().getStore().loadData(me.lineItemEntityList);//加载线段信息
			}
			if(!Ext.isEmpty(me.departureStandardEntityList)){
				me.getLineTab().getDepartureStandardEntityGrid().getStore().loadData(me.departureStandardEntityList);//加载发车标准信息
			}
		}
	},
	//线路FORM
	lineForm:null,
    getLineForm : function(){
    	if(Ext.isEmpty(this.lineForm)){
    		this.lineForm = Ext.create('Foss.baseinfo.line.LineForm');
    		this.lineForm.getForm().findField('lineType').setReadOnly(true);//修改时，线路类型不可更改
    		this.lineForm.isUpdate = true;//表示其为修改
    	}
    	return this.lineForm;
    },
    lineTab:null,//tab里面放了线段信息和发车标准
    getLineTab:function(){
    	if(Ext.isEmpty(this.lineTab)){
    		this.lineTab = Ext.create('Foss.baseinfo.line.LineTab');
			//默认为不可用的按钮设置可用，在修改中按钮可用（新增发车标准/新增线段按钮）
    		this.lineTab.getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增线段按钮可用
    		this.lineTab.getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增发车标准按钮可用
    	}
    	return this.lineTab;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getLineForm(),me.getLineTab()];
		me.callParent([cfg]);
	}
});
/**
 * 线路-FORM
 */
Ext.define('Foss.baseinfo.line.LineForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	isUpdate:false,//是否为修改，默认false
	height:410,
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
    commitLine:function(button){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var lineEntity = null;
    		if(!Ext.isEmpty(me.up('window').lineEntity)){
    			lineEntity = new Foss.baseinfo.line.LineEntity(me.up('window').lineEntity);
    		}else{
    			lineEntity = Ext.create('Foss.baseinfo.line.LineEntity');//创建线路MODEL
    		}
    		me.getForm().updateRecord(lineEntity);//将FORM中数据设置到MODEL里面
    		lineEntity.set('commonAging',lineEntity.get('commonAging')*1000);//普车时效
    		lineEntity.set('fastAging',lineEntity.get('fastAging')*1000);//卡车时效
    		var otherAging = lineEntity.get('otherAging');//偏线空运时效
    		if(Ext.isEmpty(otherAging)){
    			lineEntity.set('otherAging',otherAging*1000);//偏线时效
    		}
    		var params = {'lineVo':{'lineEntity':lineEntity.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				if(Ext.isEmpty(json.lineVo.lineEntity)){
					baseinfo.showErrorMes(baseinfo.line.i18n('foss.baseinfo.abnormalServer'));//服务端有异常！
					return;
				}
				me.up('window').lineEntity = json.lineVo.lineEntity;//将返回的值设置到window中
				me.up('window').lineEntity.commonAging = me.up('window').lineEntity.commonAging/1000;//从后台查出的值（普车时效、卡车时效、其它时效都是乘1000的）要进行处理
				me.up('window').lineEntity.fastAging = me.up('window').lineEntity.fastAging/1000;
				if(Ext.isEmpty(me.up('window').lineEntity.otherAging)){
					me.up('window').lineEntity.otherAging = me.up('window').lineEntity.otherAging/1000;
				}
				if(me.isUpdate){//修改
					//生效状态下修改
					if(me.up('window').lineEntity.valid == 'Y'){
						me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
					}else{
						me.getDockedItems()[1].items.items[3].setDisabled(false);//生效按钮可用
					}
					
					return;//返回，不做以下操作
				}else{//新增
					me.getForm().getFields( ).each(function(item,index,length){
						item.setReadOnly(true);//(对于numberfield的样式社会有问题)
					});//将FORM设置为不可用
					//me.doLayout( );
					me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
					me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
					me.getDockedItems()[1].items.items[3].setDisabled(false);//生效按钮可用
					me.up('window').getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增线段按钮可用
					me.up('window').getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增发车标准按钮可用
				}
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = baseinfo.realPath('updateTransferToTransferLine.action');//请求路径修改
			}else{
				url = baseinfo.realPath('addTransferToTransferLine.action');//请求路径新增
			}
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
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
	//生效操作
	validLine:function(button){
		var me =  this;
		var virtualCode = me.up('window').lineEntity.virtualCode;
		var params = {'lineVo':{'lineEntity':{'virtualCode':virtualCode}}};//组织新增数据
		var successFun = function(json){
			button.setDisabled(false);
			if(Ext.isEmpty(json.lineVo.lineEntity)){
				baseinfo.showErrorMes(baseinfo.line.i18n('foss.baseinfo.theCommencementFailure'));//生效失败！
				return;
			}
			baseinfo.showInfoMes(json.message);//提示生效成功
			me.up('window').parent.getPagingToolbar().moveFirst();
			me.up('window').lineEntity = json.lineVo.lineEntity;
			me.up('window').lineEntity.commonAging = me.up('window').lineEntity.commonAging/1000;//从后台查出的值（普车时效、卡车时效、其它时效都是乘1000的）要进行处理
			me.up('window').lineEntity.fastAging = me.up('window').lineEntity.fastAging/1000;
			if(Ext.isEmpty(me.up('window').lineEntity.otherAging)){
				me.up('window').lineEntity.otherAging = me.up('window').lineEntity.otherAging/1000;
			}
			if(!Ext.isEmpty(me.up('window').lineItemEntityList)){
				me.up('window').getLineTab().getLineItemInfoGrid().getStore().loadData(me.up('window').lineItemEntityList);//加载线段信息
			}
			if(!Ext.isEmpty(me.up('window').departureStandardEntityList)){
				me.up('window').getLineTab().getDepartureStandardEntityGrid().getStore().loadData(me.up('window').departureStandardEntityList);//加载发车标准信息
			}
			me.getForm().getFields().each(function(item){
//				item.setReadOnly(true);
				//生效状态下出发站、到达站、普车时效、卡车时效、线路名称、出发城市、到达城市、线路类型不允许修改
				if(item.getName( )=='orginalOrganizationCode' 
					|| item.getName( )=='destinationOrganizationCode'
						||item.getName( )=='destinationCityName'||item.getName( )=='orginalCityName'||item.getName( )=='commonAging'
							||item.getName( )=='fastAging'||item.getName( )=='lineName'||item.getName()== 'lineType'){
					
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});
//			me.up('window').getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(true);//新增发车标准按钮不可用
			me.up('window').getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(true);//新增线段按钮不可用
//			me.getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
//			me.getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
//			me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
//			me.getDockedItems()[1].items.items[4].setDisabled(false);//失效按钮可用
			if(!Ext.isEmpty(me.getDockedItems()[1])){
//					me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
//					me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
				me.getDockedItems()[1].items.items[4].setDisabled(false);//失效按钮可用
			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
//					me.getLineForm().getDockedItems()[0].items.items[1].setDisabled(true);//重置按钮不可用
//					me.getLineForm().getDockedItems()[0].items.items[2].setDisabled(true);//保存按钮不可用
				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
				me.getDockedItems()[0].items.items[4].setDisabled(false);//失效按钮可用
			}
			
		};
		var failureFun = function(json){
			button.setDisabled(false);
			if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);//提示失败原因
			}
		};
		var url = baseinfo.realPath('validLine.action');//生效线路;
		button.setDisabled(true);
		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
	},
	//失效操作
	validNotLine:function(button){
		var me =  this;
		var virtualCode = me.up('window').lineEntity.virtualCode;
		var params = {'lineVo':{'lineEntity':{'virtualCode':virtualCode}}};//组织新增数据
		var successFun = function(json){
			button.setDisabled(false);
			if(Ext.isEmpty(json.lineVo.lineEntity)){
				baseinfo.showErrorMes(baseinfo.line.i18n('foss.baseinfo.expirationFails'));//失效失败！
				return;
			}
			baseinfo.showInfoMes(json.message);//提示失效成功
			me.up('window').parent.getPagingToolbar().moveFirst();
			me.up('window').lineEntity = json.lineVo.lineEntity;
			me.up('window').lineEntity.commonAging = me.up('window').lineEntity.commonAging/1000;//从后台查出的值（普车时效、卡车时效、其它时效都是乘1000的）要进行处理
			me.up('window').lineEntity.fastAging = me.up('window').lineEntity.fastAging/1000;
			if(Ext.isEmpty(me.up('window').lineEntity.otherAging)){
				me.up('window').lineEntity.otherAging = me.up('window').lineEntity.otherAging/1000;
			}
			if(!Ext.isEmpty(me.up('window').lineItemEntityList)){
				me.up('window').getLineTab().getLineItemInfoGrid().getStore().loadData(me.up('window').lineItemEntityList);//加载线段信息
			}
			if(!Ext.isEmpty(me.up('window').departureStandardEntityList)){
				me.up('window').getLineTab().getDepartureStandardEntityGrid().getStore().loadData(me.up('window').departureStandardEntityList);//加载发车标准信息
			}
			me.getForm().getFields().each(function(item){
				var name = item.getName();
				if(name=='lineType'||name=='lineName'||name=='commonAging'||name=='fastAging'||name=='destinationCityName'||name=='orginalCityName'){
					
				}else{
					item.setReadOnly(false);
				}
				
			});
			me.up('window').getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增线段按钮可用
			me.up('window').getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(false);//新增发车标准按钮可用
			me.getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮不可用
			me.getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮不可用
			me.getDockedItems()[1].items.items[3].setDisabled(false);//生效按钮不可用
			me.getDockedItems()[1].items.items[4].setDisabled(true);//失效按钮可用
		};
		var failureFun = function(json){
			button.setDisabled(false);
			if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);//提示失败原因
			}
		};
		var url = baseinfo.realPath('invalidLine.action');//请求路径修改;
		button.setDisabled(true);
		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var dataStore = FossDataDictionary.getDataDictionaryStore(baseinfo.line.lineType);
		me.fbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.up().close();
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				if(me.isUpdate){//如果是修改，加载上一次修改的
					me.loadRecord(new Foss.baseinfo.line.LineEntity(me.up('window').lineEntity));
				}else{//如果是新增，直接reset
					me.getForm().reset();//表格重置
					me.getForm().findField('orginalOrganizationCode').reset();
				}
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 330',
			handler :function(){
				me.commitLine(this);
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.valid'),//生效
			cls:'yellow_button',
			disabled:true,
			handler :function(){
				me.validLine(this);
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.invalid'),//失效
			cls:'yellow_button',
			disabled:true,
			handler :function(){
				me.validNotLine(this);
			} 
		}];
		me.items = [{
			name: 'lineName',//线路名称
			allowBlank:false,
			readOnly:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.lineName'),
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'simpleCode',//线路简码
			allowBlank:false,
			maxLength:50,
			regex:new RegExp('^[A-Za-z0-9-_]+$'),
			regexText:baseinfo.line.i18n('foss.baseinfo.regexTextMsg'),
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.simpleCode'),
	        xtype : 'textfield',
	        listeners:{
	        	change:function(item,newValue,oldValue){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        	}
	        }
		},{
			name: 'lineType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    value:'ZX',
		    editable:false,
		    allowBlank:false,
		    store:dataStore,
		    fieldLabel: '线路类型',//线路类型
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldValue){
	        		//获取出发站
	        		var orginalOrganizationCode = me.getForm().findField('orginalOrganizationCode');
	        		//获取到达站
	        		var destinationOrganizationCode = me.getForm().findField('destinationOrganizationCode');
	        		var lineEntity = me.up('window').lineEntity;
	        		if(newValue=='PX'){//偏线
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	    					me.getDockedItems()[1].items.items[3].hide();//生效按钮隐藏
	    					me.getDockedItems()[1].items.items[4].hide();//失效按钮隐藏
	    				}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	    					me.getDockedItems()[0].items.items[3].hide();//生效按钮隐藏
	    					me.getDockedItems()[0].items.items[4].hide();//失效按钮隐藏
	    				}
	        			me.down('label').show();//隐藏站位label
	        			me.getForm().findField('commonAging').hide();//隐藏普通时效（专线）
	        			me.getForm().findField('commonAging').setValue(null);//清空里面的数据
	        			me.getForm().findField('fastAging').hide();//隐藏卡车时效（专线）
	        			me.getForm().findField('fastAging').setValue(null);//清空里面的数据
	        			me.getForm().findField('otherAging').hide();//隐藏时效（偏线、空运）
	        			me.getForm().findField('distance').hide();//隐藏线路间距离
	        			me.getForm().findField('destinationCityName').hide();//隐藏到达城市
	        			
	        			me.up('window').getLineTab().hide();//隐藏TAB
	        			me.doLayout();//让其重新调整，没有这句话，时效显示不出来
	        			//-----------------------出发站只能为外场
	        			orginalOrganizationCode.store.removeAll();
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
						searchParams['commonOrgVo.type'] = 'ORG';
						
	        			});
//						orginalOrganizationCode.store.loadPage(1);
						
						//-----------------------到达站只能为偏线代理网点
						//清空数据
						destinationOrganizationCode.store.removeAll();
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
						var types1 = 'CPPX';
						types = types1.split(',');
						searchParams['commonOrgVo.transferCenter'] = null;
						searchParams['commonOrgVo.airDispatch'] = null;
						searchParams['commonOrgVo.types'] = types;
						});
//						destinationOrganizationCode.store.loadPage(1);
						if(!Ext.isEmpty(lineEntity)){
							me.getForm().findField('orginalOrganizationCode').setCombValue(lineEntity.orginalOrganizationName,lineEntity.orginalOrganizationCode);//出发站
							me.getForm().findField('destinationOrganizationCode').setCombValue(lineEntity.destinationOrganizationName,lineEntity.destinationOrganizationCode);//到达站
						}else{
							orginalOrganizationCode.store.loadPage(1);
							destinationOrganizationCode.store.loadPage(1);
						}
	        		}else if(newValue=='KY'){//空运
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	    					me.getDockedItems()[1].items.items[3].hide();//生效按钮隐藏
	    					me.getDockedItems()[1].items.items[4].hide();//失效按钮隐藏
	    				}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	    					me.getDockedItems()[0].items.items[3].hide();//生效按钮隐藏
	    					me.getDockedItems()[0].items.items[4].hide();//失效按钮隐藏
	    				}
	        			me.down('label').show();//隐藏站位label
	        			me.getForm().findField('commonAging').hide();//隐藏普通时效（专线）
	        			me.getForm().findField('commonAging').setValue(null);//清空里面的数据
	        			me.getForm().findField('fastAging').hide();//隐藏卡车时效（专线）
	        			me.getForm().findField('fastAging').setValue(null);//清空里面的数据
//	        			me.getForm().findField('otherAging').show();//显示时效（偏线、空运）
	        			me.getForm().findField('otherAging').hide();//隐藏时效（偏线、空运）
	        			me.getForm().findField('distance').hide();//隐藏线路间距离
	        			me.getForm().findField('destinationCityName').hide();//隐藏到达城市
	        			me.up('window').getLineTab().hide();//隐藏TAB
	        			me.doLayout();//让其重新调整，没有这句话，时效显示不出来
	        			
	        			//-----------------------出发站只能为外场和空运总调
	        			orginalOrganizationCode.store.removeAll();
//	        			orginalOrganizationCode.store.removeListener('beforeload');
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
						searchParams['commonOrgVo.type'] = 'ORG';
	        			});
//						orginalOrganizationCode.store.loadPage(1);
						
						//-----------------------到达站只能为空运总调和空运代理网点以及可以空运到达的营业部(@todo)
						//清空数据
						destinationOrganizationCode.store.removeAll();
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
						var types1 = 'ORG,CPKY';
						types = types1.split(',');
						searchParams['commonOrgVo.types'] = types;
						searchParams['commonOrgVo.transferCenter'] = null;
						searchParams['commonOrgVo.airDispatch'] = 'Y';
						});
//						destinationOrganizationCode.store.loadPage(1);
						
						if(!Ext.isEmpty(lineEntity)){
							me.getForm().findField('orginalOrganizationCode').setCombValue(lineEntity.orginalOrganizationName,lineEntity.orginalOrganizationCode);//出发站
							me.getForm().findField('destinationOrganizationCode').setCombValue(lineEntity.destinationOrganizationName,lineEntity.destinationOrganizationCode);//到达站
						}else{
							orginalOrganizationCode.store.loadPage(1);
							destinationOrganizationCode.store.loadPage(1);
						}
	        		}else{//专线
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	    					me.getDockedItems()[1].items.items[3].show();//生效按钮隐藏
	    					me.getDockedItems()[1].items.items[4].show();//失效按钮隐藏
	    				}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
	    					me.getDockedItems()[0].items.items[3].show();//生效按钮隐藏
	    					me.getDockedItems()[0].items.items[4].show();//失效按钮隐藏
	    				}
	        			me.down('label').show();//显示站位label
	        			me.getForm().findField('commonAging').show();//显示普通时效（专线）
	        			me.getForm().findField('fastAging').show();//显示卡车时效（专线）
	        			me.getForm().findField('otherAging').hide();//隐藏时效（偏线、空运）
	        			me.getForm().findField('otherAging').setValue(null);//清空里面的数据
	        			me.up('window').getLineTab().show();//隐藏TAB
	        			me.doLayout();//让其重新调整
	        			
	        			//-----------------------出发站只能为外场
	        			orginalOrganizationCode.store.removeAll();
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
						searchParams['commonOrgVo.type'] = 'ORG';
	        			});
						orginalOrganizationCode.store.loadPage(1);
						
						//-----------------------到达站只能为外场
						//清空数据
						destinationOrganizationCode.store.removeAll();
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
						});
						destinationOrganizationCode.store.loadPage(1);
	        		}
	        	}
	        }
		},{
			xtype : 'dynamicorgcombselector',
			forceSelection : true,
			types:'ORG',
			allowBlank:false,
			transDepartment:'Y',
			name: 'organizationCode',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.organizationCode'),//管理车队'
	        listeners:{
	        	select:function(item,records,eopts){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        	}
	        }
		},{
			name: 'orginalOrganizationCode',//出发站
			allowBlank:false,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),
	        forceSelection:true,
	        xtype : 'dynamicorgcombselector',
	        type : 'ORG',
	        doAirDispatch:'Y',
	        transferCenter:'Y',//--或者查询外场
	        airDispatch:null,
	        listeners:{
	        	select:function(combo,records,eopts){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        		var orginalCityCode = records[0].get('cityCode');//出发城CODE
	        		combo.up('form').getForm().findField('orginalCityCode').setValue(orginalCityCode);//设置城市编码
	        		var orginalOrganizationName = combo.getRawValue();
	        		var destinationOrganizationName = combo.up('form').getForm().findField('destinationOrganizationCode').getRawValue();
	        		var lineName = orginalOrganizationName+'-'+destinationOrganizationName;//线路名称是：出发站-到达站
        		 	combo.up('form').getForm().findField('lineName').setValue(lineName);
	        		
	        		var jsonData = {'lineVo':{'entity':{'orginalCityCode':orginalCityCode}}};
	        		var url = baseinfo.realPath('queryCityName.action');
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //获取城市名称
							  var orginalCityName = json.lineVo.entity.orginalCityName;
							  combo.up('form').getForm().findField('orginalCityName').setValue(orginalCityName);//由其带出出发城市的值@TODO以后还需要修改
			            },
				        //失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              combo.up('form').showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			name: 'destinationOrganizationCode',//到达站
			allowBlank:false,
			forceSelection:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),
	        xtype : 'dynamicorgcombselector',
//	        types : 'ORG',
	        types:'ORG,CPKY',
	        transferCenter:'Y',//--或者查询外场
	        airDispatch:null,
	        doAirDispatch:'Y',
	        listeners:{
	        	select:function(combo,records,eopts){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        		var destinationCityCode = records[0].get('cityCode');//获取城市编码
	        		combo.up('form').getForm().findField('destinationCityCode').setValue(destinationCityCode);//设置城市编码
	        		var destinationOrganizationName = combo.getRawValue();
	        		var orginalOrganizationName = combo.up('form').getForm().findField('orginalOrganizationCode').getRawValue();
	        		var lineName = orginalOrganizationName+'-'+destinationOrganizationName;//线路名称是：出发站-到达站
	        		combo.up('form').getForm().findField('lineName').setValue(lineName);
	        		
	        		var jsonData = {'lineVo':{'entity':{'destinationCityCode':destinationCityCode}}};
	        		var url = baseinfo.realPath('queryCityName.action');
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //获取城市名称
							  var destinationCityName = json.lineVo.entity.destinationCityName;
							  combo.up('form').getForm().findField('destinationCityName').setValue(destinationCityName);//由其带出出发城市的值
			            },
				        //失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              combo.up('form').showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
	        xtype: 'hiddenfield',
	        name: 'orginalCityCode',
	        value: '',
	        hidden:true
	    },{
			name: 'orginalCityName',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.departureCity'),
	        readOnly : true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
	        xtype: 'hiddenfield',
	        name: 'destinationCityCode',
	        value: '',
	        hidden:true
	    },{
			name: 'destinationCityName',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.arrivalCity'),
	        readOnly : true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'commonAging',//普车时效（小时）
			readOnly:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.generalCarAgingHours'),
	        value:0,
	        minValue:0,
	        maxValue:9999999.999,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		},{
			name: 'fastAging',//卡车时效（小时）
			readOnly:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.truckAgingHours'),
	        value:0,
	        minValue:0,
	        maxValue:9999999.999,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		},{
			name: 'distance',//词条名称
			allowBlank:false,
			decimalPrecision:0,
			minValue:0,
			value:0,
	        maxValue:9999999999,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.lineDistanceKm'),
	        xtype : 'numberfield',
	        listeners:{
	        	change:function(item,newValue,oldValue){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        	}
	        }
		},{
			name: 'otherAging',//时效（小时）
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.limitationHours'),
	        hidden:true,
	        minValue:0,
	        maxValue:9999999.999,
	        step:0.001,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		}/*,{
	        xtype : 'label'//占空间
		}*/,{
			name: 'isNorewardPunishs',
	        fieldLabel: '是否不奖线路',
	        xtype : 'radiogroup',
	        layout:'column',
			defaults:{
 			width:100
 			},
			items: [{ 
					boxLabel  : baseinfo.line.i18n('foss.baseinfo.yes'), 
					columnWidth:.5,
					name      : 'isNorewardPunish',
					inputValue: 'Y',
					checked   : true
//					readOnly:true
				}, { 
					boxLabel  : baseinfo.line.i18n('foss.baseinfo.no'), 
					columnWidth:.5,
					name      : 'isNorewardPunish',
					inputValue: 'N'
			}]
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxLength:1000,
	        width:450,
	        xtype : 'textareafield',
	        listeners:{
	        	change:function(item,newValue,oldValue){
	        		if(me.isUpdate){
	        			if(!Ext.isEmpty(me.getDockedItems()[1])){
	        				me.getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
	        			}else if(!Ext.isEmpty(me.getDockedItems()[0])){
	        				me.getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
	        			}
	        		}
	        	}
	        }
		}];
		me.callParent([cfg]);
	}
});
/**
 * 线段信息和发车标准GRID-TAB
 */
Ext.define('Foss.baseinfo.line.LineTab', {
	extend : 'Ext.tab.Panel',
    flex:1,
    //发车标准GRID
    departureStandardEntityGrid:null,
    getDepartureStandardEntityGrid:function(){
    	if(Ext.isEmpty(this.departureStandardEntityGrid)){
    		this.departureStandardEntityGrid = Ext.create('Foss.baseinfo.line.DepartureStandardEntityGrid');
    	}
    	return this.departureStandardEntityGrid;
    },
    //线段信息GRID
    lineItemInfoGrid:null,
    getLineItemInfoGrid:function(){
    	if(Ext.isEmpty(this.lineItemInfoGrid)){
    		this.lineItemInfoGrid = Ext.create('Foss.baseinfo.line.LineItemInfoGrid');
    	}
    	return this.lineItemInfoGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
	        title: baseinfo.line.i18n('foss.baseinfo.segmentInformation'),//线段信息
	        items:[me.getLineItemInfoGrid()]
	    },{
	        title: baseinfo.line.i18n('foss.baseinfo.standard'),//发车标准
	        items:[me.getDepartureStandardEntityGrid()]
	    }];
		me.callParent([cfg]);
	}
});


/**
 * 线段GRID
 */
Ext.define('Foss.baseinfo.line.LineItemInfoGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//线段新增WINDOW
	lineItemAddWindow:null,
	getLineItemAddWindow:function(){
		if(Ext.isEmpty(this.lineItemAddWindow)){
    		this.lineItemAddWindow = Ext.create('Foss.baseinfo.line.LineItemAddWindow');
    		this.lineItemAddWindow.parent = this;//设置其父元素
    	}
    	return this.lineItemAddWindow;
	},
	//线段修改WINDOW
	lineItemUpdateWindow:null,
	getLineItemUpdateWindow:function(){
		if(Ext.isEmpty(this.lineItemUpdateWindow)){
    		this.lineItemUpdateWindow = Ext.create('Foss.baseinfo.line.LineItemUpdateWindow');
    		this.lineItemUpdateWindow.parent = this;//设置其父元素
    	}
    	return this.lineItemUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			text : baseinfo.line.i18n('foss.baseinfo.operate'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:70,
			items:[{
		        tooltip: baseinfo.line.i18n('foss.baseinfo.update'),//修改
				iconCls:'deppon_icons_edit',
				width:35,
				getClass : function (v, m, r, rowIndex) {
					var valid = null;
					if(!Ext.isEmpty(me.up('window').lineEntity)){
						valid = me.up('window').lineEntity.valid;
					}
					if (valid === 'Y') {
//					    return 'statementBill_hide';
						return 'deppon_icons_edit';
					} else {
					    return 'deppon_icons_edit';
					}
				},
		        handler: function(grid, rowIndex, colIndex) {
		        	//获取选中的数据
					var record=grid.getStore().getAt(rowIndex);
					grid.up().getLineItemUpdateWindow().lineItemModel = record;//将选中的数据设置到修改WINDOW的属性中
					grid.up().getLineItemUpdateWindow().show();
		        }
			},{
		        tooltip: baseinfo.line.i18n('foss.baseinfo.void'),//作废
				iconCls:'deppon_icons_cancel',
				width:35,
				getClass : function (v, m, r, rowIndex) {
					var valid = null;
					if(!Ext.isEmpty(me.up('window').lineEntity)){
						valid = me.up('window').lineEntity.valid;
					}
					if (valid === 'Y') {
					    return 'statementBill_hide';
					} else {
					    return 'deppon_icons_cancel';
					}
				},
		        handler: function(grid, rowIndex, colIndex) {
	            	baseinfo.showQuestionMes(baseinfo.line.i18n('foss.baseinfo.wantSetAsideThisSegment'),function(e){//是否要作废这条线段？
	        			if(e=='yes'){//询问是否删除，是则发送请求
	        				//获取选中的数据
	        				var record=grid.getStore().getAt(rowIndex);
	        				var lineVirtualCode = me.up('window').lineEntity.virtualCode;
	        				var params = {'lineVo':{'lineItemEntity':{'virtualCode':record.get('virtualCode'),'lineVirtualCode':lineVirtualCode}}};//组织传参
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
	        				var url = baseinfo.realPath('deleteRoadSection.action');
	        				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	        			}
	        		})
	            }
		     }]
	   },{
			text : baseinfo.line.i18n('foss.baseinfo.segmentOrder'),//线段顺序
			dataIndex : 'sequence',
			width : 65
		},{
			text : baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),//出发站
			dataIndex : 'orginalOrganizationName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.departureCity'),//出发城市
			dataIndex : 'orginalCityName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),//到达站
			dataIndex : 'destinationOrganizationName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.arrivalCity'),//到达城市
			dataIndex : 'destinationCityName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.lineItemDistanceKm'),//线段距离（公里）
			dataIndex : 'distance',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.generalCarAgingHours'),//普车时效（小时）
			dataIndex : 'commonAging',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.truckAgingHours'),//卡车时效（小时）
			dataIndex : 'fastAging',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.stopTimeHours'),//卡车时效（小时）
			dataIndex : 'passbyAging',
			flex:1
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.line.LineItemEntity',null,[]);
		me.store.on('datachanged',function(store,eOpts){//计算线路的普车时效和卡车时效
			var commonAging = 0;
			var fastAging = 0;
			store.each(function(record){
				commonAging = commonAging + record.get('commonAging') + record.get('passbyAging');
				fastAging = fastAging + record.get('fastAging') + record.get('passbyAging');
			});
		    me.up('window').getLineForm().getForm().findField('commonAging').setValue(commonAging);//设置线路的普车时效
		    me.up('window').getLineForm().getForm().findField('fastAging').setValue(fastAging);//设置线路的卡车时效
		});
		me.tbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.newSegment'),//新增线段
			disabled:true,
			handler :function(){
				me.getLineItemAddWindow().show();
			} 
		}];
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
 * 发车标准GRID
 */
Ext.define('Foss.baseinfo.line.DepartureStandardEntityGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
//	flex:1,
	height:250,
//	autoScroll:true,
	scrollershow: function(scroller) {
	    	if (scroller && scroller.scrollEl) {
				scroller.clearManagedListeners(); 
				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
			}
		},
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//发车标准WINDOW新增
	departureStandardAddWindow:null,
	getDepartureStandardAddWindow:function(){
		if(Ext.isEmpty(this.departureStandardAddWindow)){
    		this.departureStandardAddWindow = Ext.create('Foss.baseinfo.line.DepartureStandardAddWindow');
    		this.departureStandardAddWindow.parent = this;//设置器父元素
    	}
    	return this.departureStandardAddWindow;
	},
	//发车标准WINDOW修改
	departureStandardUpdateWindow:null,
	getDepartureStandardUpdateWindow:function(){
		if(Ext.isEmpty(this.departureStandardUpdateWindow)){
    		this.departureStandardUpdateWindow = Ext.create('Foss.baseinfo.line.DepartureStandardUpdateWindow');
    		this.departureStandardUpdateWindow.parent = this;//设置器父元素
    	}
    	return this.departureStandardUpdateWindow;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			text : baseinfo.line.i18n('foss.baseinfo.operate'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items:[{
		        tooltip: baseinfo.line.i18n('foss.baseinfo.update'),//修改
				iconCls:'deppon_icons_edit',
				width:42,
				getClass : function (v, m, r, rowIndex) {
					var valid = null;
					if(!Ext.isEmpty(me.up('window').lineEntity)){
						valid = me.up('window').lineEntity.valid;
					}
					if (valid === 'Y') {
//					    return 'statementBill_hide';
						return 'deppon_icons_edit';
					} else {
					    return 'deppon_icons_edit';
					}
				},
		        handler: function(grid, rowIndex, colIndex) {
		        	//获取选中的数据
					var record=grid.getStore().getAt(rowIndex);
					record.set('sequence',rowIndex+1);//设置班次
					grid.up().getDepartureStandardUpdateWindow().departureStandardModel = record;//将选中的数据设置到修改WINDOW的属性中
					grid.up().getDepartureStandardUpdateWindow().show();
		        }
			},{
		        tooltip: baseinfo.line.i18n('foss.baseinfo.void'),//作废
				iconCls:'deppon_icons_cancel',
				width:42,
				getClass : function (v, m, r, rowIndex) {
					var valid = null;
					if(!Ext.isEmpty(me.up('window').lineEntity)){
						valid = me.up('window').lineEntity.valid;
					}
					if (valid === 'Y') {
//					    return 'statementBill_hide';
						return 'deppon_icons_cancel';
					} else {
					    return 'deppon_icons_cancel';
					}
				},
		        handler: function(grid, rowIndex, colIndex) {
		        	baseinfo.showQuestionMes(baseinfo.line.i18n('foss.baseinfo.wantSetAsideThisGridStandards'),function(e){//是否要作废这条发车标准？
	        			if(e=='yes'){//询问是否删除，是则发送请求
	        				//获取选中的数据
	        				var record=grid.getStore().getAt(rowIndex);
	        				var lineVirtualCode = me.up('window').lineEntity.virtualCode;
	        				var params = {'lineVo':{'departureStandardEntity':{'virtualCode':record.get('virtualCode'),'lineVirtualCode':lineVirtualCode}}};//组织传参
	        				var successFun = function(json){
	        					baseinfo.showInfoMes(json.message);
	        					grid.getStore().remove(record);//在grid中删除这条数据
	        				};
	        				var failureFun = function(json){
	        					if(Ext.isEmpty(json)){
	        						baseinfo.showErrorMes('请求超时');//请求超时
	        					}else{
	        						baseinfo.showErrorMes(json.message);
	        					}
	        				};
	        				var url = baseinfo.realPath('deleteDepartureStandar.action');
	        				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	        			}
	        		})
		        }
			}]
	   },{
			dataIndex : 'order',
			width:40,
			text : baseinfo.line.i18n('foss.baseinfo.order')//班次
		},{
			text : baseinfo.line.i18n('foss.baseinfo.leaveTime'),//准点出发时间
			dataIndex : 'leaveTime',
			flex:1
		},{
			text : baseinfo.line.i18n('foss.baseinfo.latestArrivalTimeOfTransitReachesTheGoods'),//中转到达货最晚到达时间
			dataIndex : 'deadTime',
			flex:1,
			renderer:function(value,metaData ,record){
				return value +'<font color="blue">'+'-'+record.get('deadDay')+baseinfo.line.i18n('foss.baseinfo.day')+'</font>';
			}
		},{
			text : baseinfo.line.i18n('foss.baseinfo.productType'),//时效类型
			dataIndex : 'productType',
			renderer:function(value){
				var store = FossDataDictionary.getDataDictionaryStore(baseinfo.line.productType);
				var returnValue = '';
				if(!Ext.isEmpty(store)){
					store.each(function(record){
						if(record.get('valueCode')==value){
							returnValue = record.get('valueName');
						}
					});
				}
				return returnValue;
			},
			flex:1
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.line.DepartureStandardEntity',null,[]);
		me.tbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.addStandard'),//新增发车标准
			disabled:true,
			handler :function(){
				me.getDepartureStandardAddWindow().show();
			} 
		}];
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
 * 新增线段信息
 */
Ext.define('Foss.baseinfo.line.LineItemAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.newSegmentInformation'),//新增线段信息
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineItemInfoGrid）
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :500,
	listeners:{
		beforehide:function(me){
			me.getLineItemForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//根据区域ID和区域性质，查询区域关联部门
			me.getLineItemForm().getForm().findField('sequence').setValue(me.parent.getStore().getCount()+1);
		}
	},
	//线段FORM
	lineItemForm:null,
    getLineItemForm : function(){
    	if(Ext.isEmpty(this.lineItemForm)){
    		this.lineItemForm = Ext.create('Foss.baseinfo.line.LineItemForm');
    	}
    	return this.lineItemForm;
    },
    //提交线段信息
    commitLineItem:function(button){
    	var me = this;
    	if(me.getLineItemForm().getForm().isValid()){//校验form是否通过校验
    		var lineItemEntityAdd = Ext.create('Foss.baseinfo.line.LineItemEntity');//创建线段MODEL
    		me.getLineItemForm().getForm().updateRecord(lineItemEntityAdd);//将FORM中数据设置到MODEL里面
    		if(lineItemEntityAdd.get('distance')>=100000){
    			baseinfo.showInfoMes(baseinfo.line.i18n('foss.bse.baseinfo.line.distanceCanNotMoreThan'));
    		    me.getLineItemForm().getForm().findField('distance').setValue(0);
    		    button.setDisabled(false);
    		    return;
    		}
    		lineItemEntityAdd.set('commonAging',lineItemEntityAdd.get('commonAging')*1000);//普车时效
    		lineItemEntityAdd.set('fastAging',lineItemEntityAdd.get('fastAging')*1000);//卡车时效
    		lineItemEntityAdd.set('passbyAging',lineItemEntityAdd.get('passbyAging')*1000);//经停时间在后台都是*1000来存储的
    		lineItemEntityAdd.set('lineVirtualCode',me.parent.up('window').lineEntity.virtualCode);
    		lineItemEntityAdd.set('orginalOrganizationName',me.getLineItemForm().getForm().findField('orginalOrganizationCode').getRawValue());
    		lineItemEntityAdd.set('destinationOrganizationName',me.getLineItemForm().getForm().findField('destinationOrganizationCode').getRawValue());
    		//设置线路虚拟编码(数据来自线路window的属性（Foss.baseinfo.line.LineUpdateWindow/Foss.baseinfo.line.LineAddWindow）)
    		var params = {'lineVo':{'lineItemEntity':lineItemEntityAdd.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false)
				baseinfo.showInfoMes(json.message);//提示新增成功
				var lineItemEntity = json.lineVo.lineItemEntity;//返回新增数据
				lineItemEntity.commonAging = lineItemEntity.commonAging/1000;//普车时效
				lineItemEntity.fastAging = lineItemEntity.fastAging/1000;//卡车时效
				lineItemEntity.passbyAging = lineItemEntity.passbyAging/1000;//经停时间 
				me.parent.getStore().add(new Foss.baseinfo.line.LineItemEntity(lineItemEntity));//向父元素中加入刚新增好的值
				me.close();//关闭该window
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('addRoadSection.action');//请求路径
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getLineItemForm()];
		me.fbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getLineItemForm().getForm().reset();//表格重置
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 430',
			handler :function(){
				me.commitLineItem(this);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改线段信息
 */
Ext.define('Foss.baseinfo.line.LineItemUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.modifySegmentInformation'),//修改线段信息
	lineItemModel:null,//线段数据实体
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineItemInfoGrid）
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :500,
	listeners:{
		beforehide:function(me){
			me.getLineItemForm().getForm().reset();//表格重置
			//把不可编辑改为可编辑
			me.getLineItemForm().getForm().getFields().each(function(item){
				if(item.getName()=='orginalCityName'
						||item.getName()=='destinationCityName'){
					item.setReadOnly(true);
				}else{
					item.setReadOnly(false);
				}
			});
		},
		beforeshow:function(me){
			me.getLineItemForm().getForm().loadRecord(me.lineItemModel);//将数据设置到FORM表单中
			me.getLineItemForm().getForm().findField('orginalOrganizationCode').setCombValue(me.lineItemModel.get('orginalOrganizationName'),me.lineItemModel.get('orginalOrganizationCode'));
			me.getLineItemForm().getForm().findField('destinationOrganizationCode').setCombValue(me.lineItemModel.get('destinationOrganizationName'),me.lineItemModel.get('destinationOrganizationCode'));
//			me.getLineItemForm().getForm().findField('orginalCityCode').setCombValue(me.lineItemModel.get('orginalCityName'),me.lineItemModel.get('orginalCityCode'));
//			me.getLineItemForm().getForm().findField('destinationCityCode').setCombValue(me.lineItemModel.get('destinationCityName'),me.lineItemModel.get('destinationCityCode'));
			//获取线路信息
			var lineEntity = me.parent.up('window').lineEntity;
			if(lineEntity.valid == 'Y'){//线路状态生效情况下， 线段顺序、出发站、到达站、出发城市、到达城市不允许修改
				me.getLineItemForm().getForm().getFields().each(function(item){
					if(item.getName( )=='sequence'||item.getName()=='orginalOrganizationCode'
						||item.getName()=='destinationOrganizationCode'||item.getName()=='orginalCityName'
							||item.getName()=='destinationCityName'){
						item.setReadOnly(true);
					}else{
						item.setReadOnly(false);
					}
				});
			}
		}
	},
	//线段FORM
	lineItemForm:null,
    getLineItemForm : function(){
    	if(Ext.isEmpty(this.lineItemForm)){
    		this.lineItemForm = Ext.create('Foss.baseinfo.line.LineItemForm');
    	}
    	return this.lineItemForm;
    },
    //提交修改的数据
    commintLineItemUpdate:function(button){
    	var me = this;
    	if(me.getLineItemForm().getForm().isValid()){//校验form是否通过校验
    		var lineItemEntityUpdate = new Foss.baseinfo.line.LineItemEntity(me.lineItemModel.data);//创建线段MODEL
    		me.getLineItemForm().getForm().updateRecord(lineItemEntityUpdate);//将FORM中数据设置到MODEL里面
    		if(lineItemEntityUpdate .get('distance')>=100000){
    		    baseinfo.showInfoMes(baseinfo.line.i18n('foss.bse.baseinfo.line.distanceCanNotMoreThan'));
    		    me.getLineItemForm().getForm().findField('distance').setValue(0);
    		    button.setDisabled(false);
    		    return;
    		}
    		lineItemEntityUpdate.set('commonAging',lineItemEntityUpdate.get('commonAging')*1000);//普车时效
    		lineItemEntityUpdate.set('fastAging',lineItemEntityUpdate.get('fastAging')*1000);//卡车时效
    		lineItemEntityUpdate.set('passbyAging',lineItemEntityUpdate.get('passbyAging')*1000);//经停时间在后台都是*1000来存储的
    		lineItemEntityUpdate.set('orginalOrganizationName',me.getLineItemForm().getForm().findField('orginalOrganizationCode').getRawValue());
    		lineItemEntityUpdate.set('destinationOrganizationName',me.getLineItemForm().getForm().findField('destinationOrganizationCode').getRawValue());
    		var params = {'lineVo':{'lineItemEntity':lineItemEntityUpdate.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
				baseinfo.showInfoMes(json.message);//提示新增成功
				var lineItemEntity = json.lineVo.lineItemEntity;//返回新增数据
				lineItemEntity.commonAging = lineItemEntity.commonAging/1000;//普车时效
				lineItemEntity.fastAging = lineItemEntity.fastAging/1000;//卡车时效
				lineItemEntity.passbyAging = lineItemEntity.passbyAging/1000;//经停时间 
				me.parent.getStore().remove(me.lineItemModel);//删除原来旧的数据
				me.parent.getStore().add(new Foss.baseinfo.line.LineItemEntity(lineItemEntity));//向父元素中加入刚修改好的值
				me.close();//关闭该window
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('updateRoadSection.action');//请求路径
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getLineItemForm()];
		me.fbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getLineItemForm().getForm().loadRecord(new Foss.baseinfo.line.LineItemEntity(me.lineItemEntity));//将数据设置到FORM表单中
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 430',
			handler :function(){
				me.commintLineItemUpdate(this);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 线段-FORM
 */
Ext.define('Foss.baseinfo.line.LineItemForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:420,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	labelWidth:120,
    	anchor : '100%'
    },
	layout: {
        type: 'table',
        columns: 2
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'sequence',//线段顺序
			allowBlank:false,
			decimalPrecision:0,
			minValue:0,
			value:0,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.segmentOrder'),
	        xtype : 'numberfield'
		},{
			name: 'distance',//线段距离（公里）
			allowBlank:false,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.lineItemDistanceKm'),
	        decimalPrecision:0,
			minValue:0,
			value:0,
	        xtype : 'numberfield'
		},{
			name: 'orginalOrganizationCode',//出发站
			allowBlank:false,
			forceSelection:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),
	        xtype : 'dynamicorgcombselector',
        	type : 'ORG',
        	transferCenter:'Y',//--或者查询外场
	        listeners:{
	        	select:function(combo,records,eopts){
	        		var orginalCityCode = records[0].get('cityCode');//出发城CODE
	        		combo.up('form').getForm().findField('orginalCityCode').setValue(orginalCityCode);//设置城市编码
	        		
	        		var jsonData = {'lineVo':{'entity':{'orginalCityCode':orginalCityCode}}};
	        		var url = baseinfo.realPath('queryCityName.action');
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //获取城市名称
							  var orginalCityName = json.lineVo.entity.orginalCityName;
							  combo.up('form').getForm().findField('orginalCityName').setValue(orginalCityName);//由其带出出发城市的值@TODO以后还需要修改
			            },
				        //失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              combo.up('form').showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			name: 'destinationOrganizationCode',//到达站
			allowBlank:false,
			forceSelection:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),
	        xtype : 'dynamicorgcombselector',
        	type : 'ORG',
        	transferCenter:'Y',//--或者查询外场
	        listeners:{
	        	select:function(combo,records,eopts){
	        	    var destinationCityCode = records[0].get('cityCode');//获取城市编码
	        		combo.up('form').getForm().findField('destinationCityCode').setValue(destinationCityCode);//设置城市编码
	        		
	        		var jsonData = {'lineVo':{'entity':{'destinationCityCode':destinationCityCode}}};
	        		var url = baseinfo.realPath('queryCityName.action');
	        		 Ext.Ajax.request({
						url:url,
						jsonData:jsonData,
						//成功
						success : function(response) {
							  var json = Ext.decode(response.responseText);
							  //获取城市名称
							  var destinationCityName = json.lineVo.entity.destinationCityName;
							  combo.up('form').getForm().findField('destinationCityName').setValue(destinationCityName);//由其带出出发城市的值
			            },
				        //失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              combo.up('form').showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
	        xtype: 'hiddenfield',
	        name: 'orginalCityCode',
	        value: '',
	        hidden:true
	    },{
			name: 'orginalCityName',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.departureCity'),
	        readOnly : true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
	        xtype: 'hiddenfield',
	        name: 'destinationCityCode',
	        value: '',
	        hidden:true
	    },{
			name: 'destinationCityName',
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.arrivalCity'),
	        readOnly : true,
	        maxLength:200,
	        xtype : 'textfield'
		},{
			name: 'commonAging',//普车时效（小时）
			allowBlank:false,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.generalCarAgingHours'),
	        step:1,
	        minValue:1,
	        maxValue:1000,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		},{
			name: 'fastAging',//卡车时效（小时）
			allowBlank:false,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.truckAgingHours'),
	        step:1,
	        minValue:1,
	        maxValue:1000,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		},{
			name: 'passbyAging',//经停时间（小时）
			allowBlank:false,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.stopTimeHours'),
	        step:1,
	        minValue:0,
	        maxValue:1000,
	        decimalPrecision:3,
	        xtype : 'numberfield'
		},{
	        xtype : 'label'//占空间
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxLength:200,
	        width:450,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 新增发车标准信息
 */
Ext.define('Foss.baseinfo.line.DepartureStandardAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.addStandard'),//新增发车标准
	closable : true,
	modal : true,
	 parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.DepartureStandardEntityGrid）
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :500,
	listeners:{
		beforehide:function(me){//window隐藏所进行操作
			me.getDepartureStandardForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//window显示所进行操作
			var orginalOrganizationName = me.parent.up('window').lineEntity.orginalOrganizationName;//出发站名称
			var destinationOrganizationName = me.parent.up('window').lineEntity.destinationOrganizationName;//到达站名称
			me.getDepartureStandardForm().getForm().findField('orginalOrganizationName').setValue(orginalOrganizationName);//发车标准的出发站来自于线路
			me.getDepartureStandardForm().getForm().findField('destinationOrganizationName').setValue(destinationOrganizationName);//发车标准的到达站来自于线路
			me.getDepartureStandardForm().getForm().findField('order').setValue(me.parent.getStore().getCount()+1);
		}
	},
	//发车标准FORM
	departureStandardForm:null,
    getDepartureStandardForm : function(){
    	if(Ext.isEmpty(this.departureStandardForm)){
    		this.departureStandardForm = Ext.create('Foss.baseinfo.line.DepartureStandardForm');
    	}
    	return this.departureStandardForm;
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
    //提交新增的发车标准
    commitDepartureStandard:function(button){
    	var me = this;
    	if(me.getDepartureStandardForm().getForm().isValid()){//校验form是否通过校验
    		var departureStandardEntity = Ext.create('Foss.baseinfo.line.DepartureStandardEntity');//创建发车标准MODEL
    		me.getDepartureStandardForm().getForm().updateRecord(departureStandardEntity);//将FORM中数据设置到MODEL里面
    		var leaveTime = me.getDepartureStandardForm().getForm().findField('leaveTime').getRawValue( ).replace(':','') ;//准点出发时间
    		var deadTime = me.getDepartureStandardForm().getForm().findField('deadTime').getRawValue( ).replace(':','') ;//中转到达货最晚到达时间
    		departureStandardEntity.set('deadTime',deadTime);
    		departureStandardEntity.set('leaveTime',leaveTime);
    		
    		//获取天数
    		var deadDay = me.getDepartureStandardForm().getForm().findField('deadDay').getValue();
    		//准点出发时间
    		var leaveT = me.getDepartureStandardForm().getForm().findField('leaveTime').getValue() ;
    		//中转到达货最晚到达时间
    		var deadT = me.getDepartureStandardForm().getForm().findField('deadTime').getValue() ;
    		deadT = new Date(new Date(deadT).setDate(new Date(deadT).getDate()- parseInt(deadDay)));
    		if(deadT > leaveT){
    			me.showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),baseinfo.line.i18n('foss.baseinfo.departureTransitReachCargoLatestArrivalTimeNotPermitMoreQuasiPoint'));
    			return;
    		}
    		departureStandardEntity.set('lineVirtualCode',me.parent.up('window').lineEntity.virtualCode);
    		//设置线路虚拟编码(数据来自线路window的属性（Foss.baseinfo.line.LineUpdateWindow/Foss.baseinfo.line.LineAddWindow）)
    		var params = {'lineVo':{'departureStandardEntity':departureStandardEntity.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false)
    			baseinfo.showInfoMes(json.message);//提示新增成功
    			var departureStandardEntity = json.lineVo.departureStandardEntity;//新增的发车标准数据
    			me.parent.getStore().add(new Foss.baseinfo.line.DepartureStandardEntity(departureStandardEntity));//加到父元素grid的store中
    			me.parent.getStore().sort('leaveTime','ASC');//重新排序
    			me.close();//关闭该window
    		};
    		var failureFun = function(json){
    			button.setDisabled(false);
    			if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时');//请求超时
    			}else{
    				baseinfo.showErrorMes(json.message);//提示失败原因
    			}
    		};
    		var url = baseinfo.realPath('addDepartureStandar.action');//请求路径
    		button.setDisabled(true);
    		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getDepartureStandardForm()];
		me.fbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getLineItemForm().getForm().reset();//表格重置
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 430',
			handler :function(){
				me.commitDepartureStandard(this);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改发车标准
 */
Ext.define('Foss.baseinfo.line.DepartureStandardUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.updateStandard'),//新增发车标准
	closable : true,
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineItemInfoGrid）
	departureStandardModel:null,//修改时加载数据
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :700,
	height :500,
	listeners:{
		beforehide:function(me){
			me.getDepartureStandardForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			var model = new Foss.baseinfo.line.DepartureStandardEntity(me.departureStandardModel.data);
			var orginalOrganizationName = me.parent.up('window').lineEntity.orginalOrganizationName;//出发站名称
			var destinationOrganizationName = me.parent.up('window').lineEntity.destinationOrganizationName;//到达站名称
			me.getDepartureStandardForm().getForm().findField('orginalOrganizationName').setValue(orginalOrganizationName);//发车标准的出发站来自于线路
			me.getDepartureStandardForm().getForm().findField('destinationOrganizationName').setValue(destinationOrganizationName);//发车标准的到达站来自于线路
			me.getDepartureStandardForm().getForm().loadRecord(model);//加载相关数据
			
		}
	},
	//发车标准FORM
	departureStandardForm:null,
    getDepartureStandardForm : function(){
    	if(Ext.isEmpty(this.departureStandardForm)){
    		this.departureStandardForm = Ext.create('Foss.baseinfo.line.DepartureStandardForm');
    	}
    	return this.departureStandardForm;
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
    //提交修改的发车标准
    commitDepartureStandard:function(button){
    	var me = this;
    	if(me.getDepartureStandardForm().getForm().isValid()){//校验form是否通过校验
    		var departureStandardEntity = new Foss.baseinfo.line.DepartureStandardEntity(me.departureStandardModel.data);//创建发车标准MODEL
    		me.getDepartureStandardForm().getForm().updateRecord(departureStandardEntity);//将FORM中数据设置到MODEL里面
    		var leaveTime = me.getDepartureStandardForm().getForm().findField('leaveTime').getRawValue( ).replace(':','') ;//准点出发时间
    		var deadTime = me.getDepartureStandardForm().getForm().findField('deadTime').getRawValue( ).replace(':','') ;//中转到达货最晚到达时间
    		departureStandardEntity.set('deadTime',deadTime);
    		departureStandardEntity.set('leaveTime',leaveTime);
    		
    		//获取天数
    		var deadDay = me.getDepartureStandardForm().getForm().findField('deadDay').getValue();
    		//准点出发时间
    		var leaveT = me.getDepartureStandardForm().getForm().findField('leaveTime').getValue() ;
    		//中转到达货最晚到达时间
    		var deadT = me.getDepartureStandardForm().getForm().findField('deadTime').getValue() ;
    		deadT = new Date(new Date(deadT).setDate(new Date(deadT).getDate()- parseInt(deadDay)));
    		if(deadT > leaveT){
    			me.showWarningMsg(baseinfo.line.i18n('foss.baseinfo.notice'),baseinfo.line.i18n('foss.baseinfo.departureTransitReachCargoLatestArrivalTimeNotPermitMoreQuasiPoint'));
    			return;
    		}
    		var params = {'lineVo':{'departureStandardEntity':departureStandardEntity.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
    			baseinfo.showInfoMes(json.message);//提示新增成功
    			var departureStandardEntity = json.lineVo.departureStandardEntity;//新增的发车标准数据
    			me.parent.getStore().remove(me.departureStandardModel);//删除原有的没有修改的数据
    			me.parent.getStore().add(new Foss.baseinfo.line.DepartureStandardEntity(departureStandardEntity));//加到父元素grid的store中
    			me.parent.getStore().sort('leaveTime','ASC');//重新排序
    			me.close();//关闭该window
    		};
    		var failureFun = function(json){
    			button.setDisabled(false);
    			if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时');//请求超时
    			}else{
    				baseinfo.showErrorMes(json.message);//提示失败原因
    			}
    		};
    		var url = baseinfo.realPath('updateDepartureStandar.action');//请求路径
    		button.setDisabled(true);
    		baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getDepartureStandardForm()];
		me.fbar = [{
			text :baseinfo.line.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
				me.getLineItemForm().getForm().loadRecrod(new Foss.baseinfo.line.DepartureStandardEntity(me.departureStandardModel));//加载相关数据
			} 
		},{
			text : baseinfo.line.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 430',
			handler :function(){
				me.commitDepartureStandard(this);
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 发车标准-FORM
 */
Ext.define('Foss.baseinfo.line.DepartureStandardForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:370,
	collapsible: true,
	isSearchComb:true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	
    	labelWidth:100,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 2
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'order',//班次
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.order'),
	        step:1,
	        allowBlank:false,
	        maxValue:999999,
	        minValue:1,
	        xtype : 'numberfield'
		},{
			xtype: 'timefield',
		    fieldLabel: baseinfo.line.i18n('foss.baseinfo.leaveTime'),//准点出发时间
            name:'leaveTime',
            format:'H:i',//24小时制
            allowBlank:false
		},{
			name: 'orginalOrganizationName',//出发站
			readOnly:true,
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),//只读不可编辑，数据从线路中获取
	        xtype : 'textfield'
		},{
			name: 'destinationOrganizationName',//到达站
			readOnly:true,//只读不可编辑，数据从线路中获取
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),
	        xtype : 'textfield'
		},{
			xtype : 'container',//一个容器
			colspan :2,
			defaults : {
		    	colspan : 1,
		    	margin : '0 0 0 0',
		    	labelWidth:140,
		    	
		    	anchor : '100%'
		    },
		    layout: {
		        type: 'table',
		        columns: 3
		    },
		    items:[{
		    	name: 'deadTime',//中转到达货最晚到达时间
				allowBlank:false,
				colspan : 1,
				width:250,
		        fieldLabel: baseinfo.line.i18n('foss.baseinfo.latestArrivalTimeOfTransitReachesTheGoods'),
		        format:'H:i',//24小时制
		        xtype : 'timefield'
		    },{
		    	name: 'deadDay',
				allowBlank:false,
				colspan : 1,
				width:200,
				labelWidth:40,
				minValue:0,
				decimalPrecision:0,
		        fieldLabel: '——',
		        xtype : 'numberfield'
		    },{
		        xtype : 'label',
		        text:baseinfo.line.i18n('foss.baseinfo.day')//天
		    }]
		},{
			queryMode: 'local',
    	    displayField: 'valueName',
    	    valueField: 'valueCode',
    	    editable:false,
    	    allowBlank:false,
    	    fieldLabel: baseinfo.line.i18n('foss.baseinfo.productType'),
    	    name:'productType',
    	    store:FossDataDictionary.getDataDictionaryStore(baseinfo.line.productType),
            xtype : 'combo'
		},{
			xtype:'label'
		},{
			name: 'notes',//描述
	        fieldLabel: baseinfo.line.i18n('foss.baseinfo.notes'),
	        colspan : 2,
	        maxlength:1000,
	        width:450,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});

/**
 * ------------查询线路信息窗口-----------------
 */
Ext.define('Foss.baseinfo.line.LineShowWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.line.i18n('foss.baseinfo.showLineInformation'),//查看线路信息
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.line.LineGridPanel）
	lineEntity:null,//修改的线路信息
	lineItemEntityList:null,//修改的线段数据集
	departureStandardEntityList:null,//修改的发车标准数据集
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :1000,
	height :800,
	listeners:{
		beforehide:function(me){
			me.getLineForm().getForm().getFields().each(function(item){
				var name = item.getName();
                if(name=='lineType'||name=='lineName'||name=='commonAging'||name=='fastAging'||name=='destinationCityName'||name=='orginalCityName'){
					
				}else{
					item.setReadOnly(false);
				}
			});//表格重置
			me.getLineForm().getForm().reset();//表格重置
			me.getLineTab().getLineItemInfoGrid().getStore().removeAll();//清除线段表
			me.getLineTab().getDepartureStandardEntityGrid().getStore().removeAll();//清楚发车标准表
			//me.parent.getPagingToolbar().moveFirst();
			me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(false);//保存按钮可用
			me.getLineForm().getDockedItems()[1].items.items[0].setDisabled(false);//取消按钮可用
			me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(false);//重置按钮可用
			me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
			me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(true);//失效按钮不可用
			me.getLineForm().getDockedItems()[1].items.items[3].show();//失效和生效显示
			me.getLineForm().getDockedItems()[1].items.items[4].show();
		},
		beforeshow:function(me){
			me.getLineTab();
			
			//所有的文本都设置为不可编辑
			me.getLineForm().getForm().getFields().each(function(item){
				//不允许更改
				item.setReadOnly(true);
				});	
			if(me.lineEntity.valid=='Y'&&me.lineEntity.lineType=='ZX'){//如果是生效则不可修改
				//新增线段按钮不可用
				me.getLineTab().getDepartureStandardEntityGrid().getDockedItems()[0].items.items[0].setDisabled(true);
				//新增发车标准按钮不可用
				me.getLineTab().getLineItemInfoGrid().getDockedItems()[0].items.items[0].setDisabled(true);
				
				if(!Ext.isEmpty(me.getLineForm().getDockedItems()[1])){
					me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[3].setDisabled(true);//生效按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[4].setDisabled(true);//失效按钮不可用
				}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
					me.getLineForm().getDockedItems()[0].items.items[1].setDisabled(true);//重置按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[2].setDisabled(true);//保存按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[3].setDisabled(true);//生效按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[4].setDisabled(true);//失效按钮不可用
				}
			}else{
				if(!Ext.isEmpty(me.getLineForm().getDockedItems()[1])){
					me.getLineForm().getDockedItems()[1].items.items[1].setDisabled(true);//重置按钮不可用
					me.getLineForm().getDockedItems()[1].items.items[2].setDisabled(true);//保存按钮不可用
				}else if(!Ext.isEmpty(me.getLineForm().getDockedItems()[0])){
					me.getLineForm().getDockedItems()[0].items.items[1].setDisabled(true);//重置按钮不可用
					me.getLineForm().getDockedItems()[0].items.items[2].setDisabled(true);//保存按钮不可用
				}
			}
			
			me.lineEntity.commonAging = me.lineEntity.commonAging/1000;//从后台查出的值（普车时效、卡车时效、其它时效都是乘1000的）要进行处理
			me.lineEntity.fastAging = me.lineEntity.fastAging/1000;
			if(Ext.isEmpty(me.lineEntity.otherAging)){
				me.lineEntity.otherAging = me.lineEntity.otherAging/1000;
			};
			//获取出发站
	        var orginalOrganizationCode = me.getLineForm().getForm().findField('orginalOrganizationCode');
	        //获取到达站
	        var destinationOrganizationCode = me.getLineForm().getForm().findField('destinationOrganizationCode');
	  
			me.getLineForm().getForm().loadRecord(new Foss.baseinfo.line.LineEntity(me.lineEntity));//加载线路数据
			

			if(me.lineEntity.lineType=='ZX'){
				me.getLineForm().getForm().findField('orginalOrganizationCode').setCombValue(me.lineEntity.orginalOrganizationName,me.lineEntity.orginalOrganizationCode);//出发站
			 	me.getLineForm().getForm().findField('destinationOrganizationCode').setCombValue(me.lineEntity.destinationOrganizationName,me.lineEntity.destinationOrganizationCode);//到达站
			}
			me.getLineForm().getForm().findField('organizationCode').setCombValue(me.lineEntity.organizationName,me.lineEntity.organizationCode);//管理车队
			if(!Ext.isEmpty(me.lineItemEntityList)){
				for(var i= 0;i<me.lineItemEntityList.length;i++){
					me.lineItemEntityList[i].commonAging = me.lineItemEntityList[i].commonAging/1000;//普车时效
					me.lineItemEntityList[i].fastAging = me.lineItemEntityList[i].fastAging/1000;//卡车时效
					me.lineItemEntityList[i].passbyAging = me.lineItemEntityList[i].passbyAging/1000;//经停时间 
				}
				me.getLineTab().getLineItemInfoGrid().getStore().loadData(me.lineItemEntityList);//加载线段信息
			}
			if(!Ext.isEmpty(me.departureStandardEntityList)){
				me.getLineTab().getDepartureStandardEntityGrid().getStore().loadData(me.departureStandardEntityList);//加载发车标准信息
			}
		}
	},
	lineForm:null,
    getLineForm : function(){
    	if(Ext.isEmpty(this.lineForm)){
    		this.lineForm = Ext.create('Foss.baseinfo.line.LineForm');
    		this.lineForm.getForm().findField('lineType').setReadOnly(true);//修改时，线路类型不可更改
    		this.lineForm.isUpdate = true;//表示其为修改
    	}
    	return this.lineForm;
    },
    lineTab:null,//tab里面放了线段信息和发车标准
    getLineTab:function(){
    	if(Ext.isEmpty(this.lineTab)){
    		this.lineTab = Ext.create('Foss.baseinfo.line.ShowLineTab');
			
    	}
    	return this.lineTab;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getLineForm(),me.getLineTab()];
		me.callParent([cfg]);
	}
		
});

/**
 * ----------查看线段和发车标准的信息----------------
 */
Ext.define('Foss.baseinfo.line.ShowLineTab', {
	extend : 'Ext.tab.Panel',
    flex:1,
    //发车标准GRID
    departureStandardEntityGrid:null,
    getDepartureStandardEntityGrid:function(){
    	if(Ext.isEmpty(this.departureStandardEntityGrid)){
    		this.departureStandardEntityGrid = Ext.create('Foss.baseinfo.line.ShowDepartureStandardEntityGrid');
    	}
    	return this.departureStandardEntityGrid;
    },
    //线段信息GRID
    lineItemInfoGrid:null,
    getLineItemInfoGrid:function(){
    	if(Ext.isEmpty(this.lineItemInfoGrid)){
    		this.lineItemInfoGrid = Ext.create('Foss.baseinfo.line.ShowLineItemInfoGrid');
    	}
    	return this.lineItemInfoGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
	        title: baseinfo.line.i18n('foss.baseinfo.segmentInformation'),//线段信息
	        items:[me.getLineItemInfoGrid()]
	    },{
	        title: baseinfo.line.i18n('foss.baseinfo.standard'),//发车标准
	        items:[me.getDepartureStandardEntityGrid()]
	    }];
		me.callParent([cfg]);
	}
});
/**
 * 定义查看的发车标准GRID
 */
Ext.define('Foss.baseinfo.line.ShowDepartureStandardEntityGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
//	flex:1,
//	autoScroll:true,
	height:250,
	scrollershow: function(scroller) {
	    	if (scroller && scroller.scrollEl) {
				scroller.clearManagedListeners(); 
				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
			}
		},
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			dataIndex : 'order',
			width:40,
			text : baseinfo.line.i18n('foss.baseinfo.order')//班次
		},{
			text : baseinfo.line.i18n('foss.baseinfo.leaveTime'),//准点出发时间
			dataIndex : 'leaveTime',
			flex:1
		},{
			text : baseinfo.line.i18n('foss.baseinfo.latestArrivalTimeOfTransitReachesTheGoods'),//中转到达货最晚到达时间
			dataIndex : 'deadTime',
			flex:1,
			renderer:function(value,metaData ,record){
				return value +'<font color="blue">'+'-'+record.get('deadDay')+baseinfo.line.i18n('foss.baseinfo.day')+'</font>';
			}
		},{
			text : baseinfo.line.i18n('foss.baseinfo.productType'),//时效类型
			dataIndex : 'productType',
			renderer:function(value){
				var store = FossDataDictionary.getDataDictionaryStore(baseinfo.line.productType);
				var returnValue = '';
				if(!Ext.isEmpty(store)){
					store.each(function(record){
						if(record.get('valueCode')==value){
							returnValue = record.get('valueName');
						}
					});
				}
				return returnValue;
			},
			flex:1
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.line.DepartureStandardEntity',null,[]);
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
 * 查看 线路的线段GRID
 */
Ext.define('Foss.baseinfo.line.ShowLineItemInfoGrid', {
	extend: 'Ext.grid.Panel',
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			text : baseinfo.line.i18n('foss.baseinfo.segmentOrder'),//线段顺序
			dataIndex : 'sequence',
			width : 65
		},{
			text : baseinfo.line.i18n('foss.baseinfo.orginalOrganizationCode'),//出发站
			dataIndex : 'orginalOrganizationName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.departureCity'),//出发城市
			dataIndex : 'orginalCityName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.destinationOrganizationCode'),//到达站
			dataIndex : 'destinationOrganizationName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.arrivalCity'),//到达城市
			dataIndex : 'destinationCityName',
			width : 80
		},{
			text : baseinfo.line.i18n('foss.baseinfo.lineItemDistanceKm'),//线段距离（公里）
			dataIndex : 'distance',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.generalCarAgingHours'),//普车时效（小时）
			dataIndex : 'commonAging',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.truckAgingHours'),//卡车时效（小时）
			dataIndex : 'fastAging',
			width : 120
		},{
			text : baseinfo.line.i18n('foss.baseinfo.stopTimeHours'),//卡车时效（小时）
			dataIndex : 'passbyAging',
			flex:1
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.line.LineItemEntity',null,[]);
		me.store.on('datachanged',function(store,eOpts){//计算线路的普车时效和卡车时效
			var commonAging = 0;
			var fastAging = 0;
			store.each(function(record){
				commonAging = commonAging + record.get('commonAging') + record.get('passbyAging');
				fastAging = fastAging + record.get('fastAging') + record.get('passbyAging');
			});
		    me.up('window').getLineForm().getForm().findField('commonAging').setValue(commonAging);//设置线路的普车时效
		    me.up('window').getLineForm().getForm().findField('fastAging').setValue(fastAging);//设置线路的卡车时效
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
