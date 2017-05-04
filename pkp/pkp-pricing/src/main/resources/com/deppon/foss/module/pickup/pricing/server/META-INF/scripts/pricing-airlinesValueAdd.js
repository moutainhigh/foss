/**
 * 
 * 航空增值服务信息，如燃油费, 地面运输费,保费的基础价格信息维护
 */
pricing.airlinesValueAdd.yes = 'Y';//是
pricing.airlinesValueAdd.no = 'N';//否
pricing.yes = 'Y';//是
pricing.no = 'N';//否
pricing.airlinesValueAdd.tomorrowTime = null;

//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.airlinesValueAdd.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.airlinesValueAdd.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.airlinesValueAdd.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//--------------------------------------pricing----------------------------------------
//代理航空公司增值服务费用，包括燃油附加费，地面运输费，保费，最低总金额
Ext.define('Foss.pricing.AirlinesValueAddEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    },{
        name : 'loadOrgCode', //配载部门
        type : 'string'
    },{
        name : 'loadOrgName', //配载部门名称
        type : 'string'
    },{
        name : 'airlinesCode',//航空公司
        type : 'string'
    },{
        name : 'airlinesName',//航空公司名称
        type : 'string'
    },{
        name : 'airport', //出发机场
        type : 'string'
    },{
        name : 'airportName', //出发机场名称
        type : 'string'
    },{
        name : 'beginTime',//生效日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'active',//数据状态
        type : 'string'
    },{
        name : 'createOrgCode',//创建机构
        type : 'string'
    },{
        name : 'modifyOrgCode',//修改机构
        type : 'string'
    },{
        name : 'currencyCode',// 币种
        type : 'string'
    },{
    	defaultValue : null,
        name : 'oilAddFee'//燃油附加费	
    },{
    	defaultValue : null,
        name : 'minOilAddFee'//最低燃油附加费
    },{
    	defaultValue : null,
        name : 'groundTrsFee'//地面运输费
    },{
    	defaultValue : null,
        name : 'minGroundTrsFee'//最低地面运输费
    },{
    	defaultValue : null,
        name : 'insuranceFee'//保费
    },{
    	defaultValue : null,
        name : 'minInsuranceFee'//最低保费
    },{
    	defaultValue : null,//最低总金额
        name : 'minTotalFee'
    },{
        name : 'description',//备注
        type : 'string'
    },{
        name : 'priceNo',//运价号
        type : 'string'
    },{
    	name : 'currentUsedVersion',//是否最新版本
    	type : 'string'
    },{
    	name : 'showTime',
    	type : 'string'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 航空公司增值服务Store（Foss.pricing.AirlinesValueAddEntity）
 */
Ext.define('Foss.pricing.AirlinesValueAddStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.AirlinesValueAddEntity',//航空公司增值服务的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchAirlinesValueByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'airlinesValueAddVo.airlinesValueAddEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 航空公司增值服务表单
 */
Ext.define('Foss.pricing.QueryAirlinesValueAddForm', {
	extend : 'Ext.form.Panel',
	title: pricing.airlinesValueAdd.i18n('foss.pricing.theAirlineValueAddedServiceInformationInquiry'),//航空公司增值服务信息查询
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	colspan: 1,
    	margin : '8 10 5 10'
    },
    height :180,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			xtype : 'commonairlinesselector',
			forceSelection : true,
			name: 'airlinesCode',//航空公司
			fieldLabel : pricing.airlinesValueAdd.i18n('foss.pricing.airline')
		},{
			name: 'airport',//出发机场
			forceSelection : true,
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.separtureAirport'),
	        xtype : 'commonairportselector'
		},{
			name:'beginTime',
			editabled:false,
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.startTime'),//开始时间
	        format:'20y-m-d',
	        xtype : 'datefield'
		},{
			name:'endTime',
			editabled:false,
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.endTime'),//结束时间
	        format:'20y-m-d',
	        xtype : 'datefield'
		},
		{//是否当前版本 是，否，全部
			
			name: 'currentUsedVersion',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':pricing.airlinesValueAdd.i18n('i18n.pricing.currentVersion_yes'),'valueCode':pricing.yes}//是当前版本
		    , {'valueName':pricing.airlinesValueAdd.i18n('i18n.pricing.currentVersion_no'),'valueCode':pricing.no}]),//不是当前
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.currentUsedVersion'),//是否当前版本
	        xtype : 'combo'
		}
		],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.airlinesValueAdd.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.search'),//查询
			disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddQueryButton'),
			hidden: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getAirlinesValueAddGrid().getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司增值服务列表
 */
Ext.define('Foss.pricing.AirlinesValueAddGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.airlinesValueAdd.i18n('foss.pricing.theAirlineValueAddedServiceInformation'),//航空公司增值服务信息
	frame: true,
	flex:1,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.airlinesValueAdd.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//航空公司增值服务新增WINDOW
	airlinesValueAddAddWindow:null,
	getAirlinesValueAddAddWindow:function(){
		if (this.airlinesValueAddAddWindow == null) {
			this.airlinesValueAddAddWindow = Ext.create('Foss.pricing.AirlinesValueAddAddWindow');
			this.airlinesValueAddAddWindow.parent = this;//父元素
		}
		return this.airlinesValueAddAddWindow;
	},
	//修改航空公司增值服务WINDOW
	airlinesValueAddUpdateWindow:null,
	getAirlinesValueAddUpdateWindow:function(){
		if (this.airlinesValueAddUpdateWindow == null) {
			this.airlinesValueAddUpdateWindow = Ext.create('Foss.pricing.AirlinesValueAddUpdateWindow');
			this.airlinesValueAddUpdateWindow.parent = this;//父元素
		}
		return this.airlinesValueAddUpdateWindow;
	},
	//删除航空公司增值服务
	deleteAirlinesValueAdd: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行删除操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.airlinesValueAdd.yes){
				pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.airlinesValueAdd.i18n('foss.pricing.deleteTheseTheAirlineValueAddedServices'),function(e){//是否要作废这些航空公司增值服务？
			if(e=='yes'){//询问是否删除，是则发送请求
				var airlinesValueAddIds = new Array();//航空公司增值服务ID
				for(var i = 0 ; i<selections.length ; i++){
					airlinesValueAddIds.push(selections[i].get('id'));
				}
				var params = {'airlinesValueAddVo':{'airlinesValueAddIds':airlinesValueAddIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteAirlinesValue.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
	},
	
	//激活航空公司增值服务
	actioveAirlinesValueAdd:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.pleaseSelectAnActivateOperation'));//请选择一条进行激活操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.airlinesValueAdd.yes){
				pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.pleaseSelectTheActivationDataForActivation'));//请选择未激活数据进行激活！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.airlinesValueAdd.i18n('foss.pricing.doYouWantToActivateTheseAirlinesValueAddedServices'),function(e){//是否要作废这些航空公司增值服务？
			if(e=='yes'){//询问是否删除，是则发送请求				
				var airlinesValueAddIds = new Array();//航空公司增值服务ID
				var currentDate = new Date(new Date() - 5000);
				for(var i = 0 ; i<selections.length ; i++){					
					var beginTime = new Date(selections[i].get('beginTime'));
		    		if(beginTime < currentDate){
		    			pricing.showInfoMes('开始时间不能早于当前时间！');
		    			return;
		    		}
		    		var endTime = new Date(selections[i].get('endTime'));
		    		if(beginTime > endTime){
		    			pricing.showInfoMes('开始时间不能晚于结束时间！');
		    			return;
		    		}
					airlinesValueAddIds.push(selections[i].get('id'));
				}
				var params = {'airlinesValueAddVo':{'airlinesValueAddIds':airlinesValueAddIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeAirlinesValue.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})	
	},
	
	
	
	
	
	
	  /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveAirlinesValueAddWindow:null,
	getImmediatelyActiveAirlinesValueAddWindow: function(airlinesValueAddEntity){
		if(Ext.isEmpty(this.immediatelyActiveAirlinesValueAddWindow)){
			this.immediatelyActiveAirlinesValueAddWindow = Ext.create('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyActiveTimeWindow');
			this.immediatelyActiveAirlinesValueAddWindow.parent = this;
		}
		this.immediatelyActiveAirlinesValueAddWindow.airlinesValueAddEntity = airlinesValueAddEntity;
		return this.immediatelyActiveAirlinesValueAddWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopAirlinesValueAddWindow:null,
	getImmediatelyStopAirlinesValueAddWindow: function(airlinesValueAddEntity){
		if(Ext.isEmpty(this.immediatelyStopAirlinesValueAddWindow)){
			this.immediatelyStopAirlinesValueAddWindow = Ext.create('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyStopEndTimeWindow');
			this.immediatelyStopAirlinesValueAddWindow.parent = this;
		}
		this.immediatelyStopAirlinesValueAddWindow.airlinesValueAddEntity = airlinesValueAddEntity;
		return this.immediatelyStopAirlinesValueAddWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStopAirlinesAdd:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')!=pricing.airlinesValueAdd.yes){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var airlinesValueAddEntity = selections[0].data;
	 		me.getImmediatelyStopAirlinesValueAddWindow(airlinesValueAddEntity).show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActiveAirlinesAdd:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')==pricing.airlinesValueAdd.yes){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var airlinesValueAddEntity = selections[0].data;
	 		me.getImmediatelyActiveAirlinesValueAddWindow(airlinesValueAddEntity).show();
	 	}
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.airlinesValueAdd.i18n('foss.pricing.update'),//修改
                disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddUpdateButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);;//选中数据
                	var updateWindow =  grid.up().getAirlinesValueAddUpdateWindow();
    				var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					updateWindow.airlinesValueAddEntity = json.airlinesValueAddVo.airlinesValueAddEntity;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryAirlinesValueAddById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.airlinesValueAdd.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddCopyButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var me = this;
                	var record = grid.up().getStore().getAt(rowIndex);;//选中数据
                	var updateWindow =  grid.up().getAirlinesValueAddUpdateWindow();
    				var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					grid.up().getPagingToolbar().moveFirst();
    					updateWindow.airlinesValueAddEntity = json.airlinesValueAddVo.airlinesValueAddEntity;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('upgradeAirlinesValueAdd.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.separtureAirport'),//出发机场
			dataIndex : 'airportName'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.airline'),//航空公司
			dataIndex : 'airlinesName'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value==pricing.airlinesValueAdd.yes){//'Y'表示激活
					return pricing.airlinesValueAdd.i18n('i18n.pricingRegion.active');
				}else if(value==pricing.airlinesValueAdd.no){//'N'表示未激活
					return  pricing.airlinesValueAdd.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
			
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.startTime'),//开始时间
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');;
			}
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.endTime'),//结束时间
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');;
			}
		},{
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.lastCreateUser'),//最后创建人
			dataIndex : 'modifyUserName'
		},{
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.lastCreateTime'),//最后创建时间
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');;
			}
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.fuelSurcharge'),//燃油附加费
			dataIndex : 'oilAddFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.minimumFuelSurcharges'),//最低燃油附加费
			dataIndex : 'minOilAddFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.groundTransportationCosts'),//地面运输费
			dataIndex : 'groundTrsFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.theLowestGroundTransportationFee'),//最低地面运输费
			dataIndex : 'minGroundTrsFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.premiums'),//保费
			dataIndex : 'insuranceFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.minimumPremiumFee'),//最低保险费
			dataIndex : 'minInsuranceFee'
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.minimumTotalAmount'),//最低总金额
			dataIndex : 'minTotalFee'
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.store = Ext.create('Foss.pricing.AirlinesValueAddStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryAirlinesValueAddForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//航空公司增值服务大查询，查询条件组织
								'airlinesValueAddVo.airlinesValueAddDto.airlinesCode':queryForm.getForm().findField('airlinesCode').getValue(),//航空公司CODE
								'airlinesValueAddVo.airlinesValueAddDto.beginTime':queryForm.getForm().findField('beginTime').getValue(),//开始时间
								'airlinesValueAddVo.airlinesValueAddDto.endTime':queryForm.getForm().findField('endTime').getValue(),//截止时间
								'airlinesValueAddVo.airlinesValueAddDto.airport':queryForm.getForm().findField('airport').getValue(),//出发机场
								'airlinesValueAddVo.airlinesValueAddDto.currentUsedVersion':queryForm.getForm().findField('currentUsedVersion').getValue()//是否当前版本
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
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddAddButton'),
			hidden: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddAddButton'),
			handler :function(){
				me.getAirlinesValueAddAddWindow().show();
			} 
		},'-',{
			text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddActiveButton'),
			hidden: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddActiveButton'),
			handler :function(){
				me.actioveAirlinesValueAdd();
			} 
		},'-',{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.delete'),//删除
			disabled: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddDeleteButton'),
			hidden: !pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddDeleteButton'),
			handler :function(){
				me.deleteAirlinesValueAdd();
			} 
		},'-',{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddImmediatelyActiveButton'),
			hidden:!pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActiveAirlinesAdd();
			} 
		},'-', {
			text : pricing.airlinesValueAdd.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddImmediatelyStopButton'),
			hidden:!pricing.airlinesValueAdd.isPermission('airlinesValueAdd/airlinesValueAddImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStopAirlinesAdd();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});


/**
 * @description 航空公司增值服务主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-airlinesValueAdd_content')) {
		return;
	};
	var queryAirlinesValueAddForm = Ext.create('Foss.pricing.QueryAirlinesValueAddForm');//查询FORM
	var airlinesValueAddGrid = Ext.create('Foss.pricing.AirlinesValueAddGrid');//查询结果GRID
	Ext.getCmp('T_pricing-airlinesValueAdd').add(Ext.create('Ext.panel.Panel', {
	 	id : 'T_pricing-airlinesValueAdd_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryAirlinesValueAddForm : function() {
			return queryAirlinesValueAddForm;
		},
		//获得查询结果GRID
		getAirlinesValueAddGrid : function() {
			return airlinesValueAddGrid;
		},
		items : [queryAirlinesValueAddForm, airlinesValueAddGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增航空公司增值服务信息
 */
Ext.define('Foss.pricing.AirlinesValueAddAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.airlinesValueAdd.i18n('foss.pricing.theNewAirlinesValueAddedServices'),//新增航空公司增值服务
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.AirlinesValueAddGrid）
	modal : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :590,
	height :490,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getAirlinesValueAddForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//新增航空公司增值服务FORM
	airlinesValueAddForm:null,
	getAirlinesValueAddForm : function(){
    	if(Ext.isEmpty(this.airlinesValueAddForm)){
    		this.airlinesValueAddForm = Ext.create('Foss.pricing.AirlinesValueAddForm');
    	}
    	return this.airlinesValueAddForm;
    },
    //提交航空公司增值服务数据
    commitAirlinesValueAdd:function(){
    	var me = this;
    	if(me.getAirlinesValueAddForm().getForm().isValid()){//校验form是否通过校验
    		var airlinesValueAddModel = new Foss.pricing.AirlinesValueAddEntity();
    		me.getAirlinesValueAddForm().getForm().updateRecord(airlinesValueAddModel);//将FORM中数据设置到MODEL里面
    		if(airlinesValueAddModel.get('endTime').getTime()<=airlinesValueAddModel.get('beginTime').getTime()){
        		pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.theClosingDateIsGreaterThanStartDate'));//截止日期要大于起始日期！
    			return;
        	}
        	var oilAddFee = airlinesValueAddModel.get('oilAddFee')*100//燃油附加费
        	var groundTrsFee = airlinesValueAddModel.get('groundTrsFee')*100//地面运输费
        	var insuranceFee = airlinesValueAddModel.get('insuranceFee')*100;//保费
    		var minGroundTrsFee = airlinesValueAddModel.get('minGroundTrsFee')*100;//最低地面运输费
    		var minInsuranceFee = airlinesValueAddModel.get('minInsuranceFee')*100;//最低保费
    		var minOilAddFee = airlinesValueAddModel.get('minOilAddFee')*100;//最低燃油附加费
    		var minTotalFee = airlinesValueAddModel.get('minTotalFee')*100;//总金额最低一票
    		var total = minGroundTrsFee+minInsuranceFee+minOilAddFee;
    		if(total>minTotalFee){
    			pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.youSetLowestVoteNotLessThanSumTotalAmountLowestVote'));//您设置的各项最低一票总和不能小于总金额最低一票！
    			return;
    		}
    		airlinesValueAddModel.set('oilAddFee',oilAddFee);//燃油附加费
    		airlinesValueAddModel.set('groundTrsFee',groundTrsFee);//地面运输费
    		airlinesValueAddModel.set('insuranceFee',insuranceFee);//保险费
    		airlinesValueAddModel.set('minGroundTrsFee',minGroundTrsFee);//最低地面运输费
    		airlinesValueAddModel.set('minInsuranceFee',minInsuranceFee);//最低保险费
    		airlinesValueAddModel.set('minOilAddFee',minOilAddFee);//最低燃油费
    		airlinesValueAddModel.set('minTotalFee',minTotalFee);//最低总费
    		var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':airlinesValueAddModel.data}};//组织新增数据
    		var paramscheck = 
    			{'airlinesValueAddVo':{
    				'airlinesValueAddDto':{
    					'loadOrgCode':airlinesValueAddModel.data.loadOrgCode,
    					'airlinesCode':airlinesValueAddModel.data.airlinesCode,
    					'airport':airlinesValueAddModel.data.airport
    				}
    			}
    		};
    		var successFunCheck = function(json){
    			var successFun = function(json){
    				pricing.showInfoMes(json.message);//提示新增成功
    				me.close();
    				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
    			};
    			var failureFun = function(json){
    				if(Ext.isEmpty(json)){
    					pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
    				}else{
    					pricing.showErrorMes(json.message);//提示失败原因
    				}
    			};
    			var url = pricing.realPath('addAirlinesValue.action');//请求航空公司增值服务新增
    			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
			};
			var failureFunCheck = function(json){
				Ext.Msg.confirm(pricing.airlinesValueAdd.i18n('foss.pricing.promptMessage'),pricing.airlinesValueAdd.i18n('foss.pricing.hasExists'),
						function(btn) {
							if (btn == 'yes') {
								
							}else{
								me.close();
								me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
							}
					});
			};
			var checkUrl = pricing.realPath('checkAirlinesValueExistsByCondition.action');//校验航空公司增值服务
			pricing.requestJsonAjax(checkUrl,paramscheck,successFunCheck,failureFunCheck);//发送AJAX请求
    		
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.airlinesValueAdd.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getAirlinesValueAddForm().getForm().loadRecord(new Foss.pricing.AirlinesValueAddEntity());
			} 
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitAirlinesValueAdd();
			} 
		}];
		me.items = [me.getAirlinesValueAddForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改航空公司增值服务
 */
Ext.define('Foss.pricing.AirlinesValueAddUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.airlinesValueAdd.i18n('foss.pricing.modifyTheAirlinesValueAddedServices'),//修改航空公司增值服务
	closable : true,
	modal : true,
	resizable:false,
	airlinesValueAddEntity:null,//修改航空公司增值服务数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :590,
	height :490,
	listeners:{
		beforehide:function(me){
			me.getAirlinesValueAddForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getAirlinesValueAddForm().getForm().loadRecord(new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity));
			//航空公司
			me.getAirlinesValueAddForm().getForm().findField('airlinesCode').setCombValue(me.airlinesValueAddEntity.airlinesName,me.airlinesValueAddEntity.airlinesCode);
			//出发机场
			me.getAirlinesValueAddForm().getForm().findField('airport').setCombValue(me.airlinesValueAddEntity.airportName,me.airlinesValueAddEntity.airport);
			//出发机场
			me.getAirlinesValueAddForm().getForm().findField('loadOrgCode').setCombValue(me.airlinesValueAddEntity.loadOrgName,me.airlinesValueAddEntity.loadOrgCode);
			
		}
	},
	//修改航空公司增值服务FORM
	airlinesValueAddForm:null,
	getAirlinesValueAddForm : function(){
    	if(Ext.isEmpty(this.airlinesValueAddForm)){
    		this.airlinesValueAddForm = Ext.create('Foss.pricing.AirlinesValueAddForm');
    	}
    	return this.airlinesValueAddForm;
    },
    //修改航空公司增值服务
    commitAirlinesValueAdd:function(){
    	var me = this;
    	if(me.getAirlinesValueAddForm().getForm().isValid()){//校验form是否通过校验
    		var airlinesValueAddModel = new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity);
    		me.getAirlinesValueAddForm().getForm().updateRecord(airlinesValueAddModel);//将FORM中数据设置到MODEL里面
    		if(airlinesValueAddModel.get('endTime').getTime()<=airlinesValueAddModel.get('beginTime').getTime()){
        		pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.theClosingDateIsGreaterThanStartDate'));//截止日期要大于起始日期！
    			return;
        	}
    		var oilAddFee = airlinesValueAddModel.get('oilAddFee')*100//燃油附加费
        	var groundTrsFee = airlinesValueAddModel.get('groundTrsFee')*100//地面运输费
        	var insuranceFee = airlinesValueAddModel.get('insuranceFee')*100;//保费
    		var minGroundTrsFee = airlinesValueAddModel.get('minGroundTrsFee')*100;//最低地面运输费
    		var minInsuranceFee = airlinesValueAddModel.get('minInsuranceFee')*100;//最低保费
    		var minOilAddFee = airlinesValueAddModel.get('minOilAddFee')*100;//最低燃油附加费
    		var minTotalFee = airlinesValueAddModel.get('minTotalFee')*100;//总金额最低一票
    		var total = minGroundTrsFee+minInsuranceFee+minOilAddFee;
    		if(total>minTotalFee){
    			pricing.showWoringMessage(pricing.airlinesValueAdd.i18n('foss.pricing.youSetLowestVoteNotLessThanSumTotalAmountLowestVote'));//您设置的各项最低一票总和不能小于总金额最低一票！
    			return;
    		}
    		airlinesValueAddModel.set('oilAddFee',oilAddFee);//燃油附加费
    		airlinesValueAddModel.set('groundTrsFee',groundTrsFee);//地面运输费
    		airlinesValueAddModel.set('insuranceFee',insuranceFee);//保险费
    		airlinesValueAddModel.set('minGroundTrsFee',minGroundTrsFee);//最低地面运输费
    		airlinesValueAddModel.set('minInsuranceFee',minInsuranceFee);//最低保险费
    		airlinesValueAddModel.set('minOilAddFee',minOilAddFee);//最低燃油费
    		airlinesValueAddModel.set('minTotalFee',minTotalFee);//最低总费
    		var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':airlinesValueAddModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.airlinesValueAdd.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateAirlinesValue.action');//请求航空公司增值服务修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.airlinesValueAdd.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getAirlinesValueAddForm().getForm().loadRecord(new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity));
			} 
		},{
			text : pricing.airlinesValueAdd.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 325',
			handler :function(){
				me.commitAirlinesValueAdd();
			} 
		}];
		me.items = [ me.getAirlinesValueAddForm()];
		me.callParent([cfg]);
	}
});
/**
 * 航空公司增值服务组-FORM
 */
Ext.define('Foss.pricing.AirlinesValueAddForm', {
	extend : 'Ext.form.Panel',
	title : pricing.airlinesValueAdd.i18n('foss.pricing.theAirlineValueAddedServiceInformation'),//航空公司增值服务信息
	frame: true,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name:'loadOrgCode',
			fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.stowageDepartment'),//车队负责行政区域
			forceSelection : true,
			types:'ORG',
			doAirDispatch:'Y',//空运配载
			xtype:'dynamicorgcombselector'
		},{
			xtype : 'commonairlinesselector',
			forceSelection : true,
			name: 'airlinesCode',//航空公司
			fieldLabel : pricing.airlinesValueAdd.i18n('foss.pricing.airline')
		},{
			name: 'airport',//出发机场
			forceSelection : true,
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.separtureAirport'),
	        xtype : 'commonairportselector'
		},{
			name: 'beginTime',//生效时间
			editable:false,
			minValue:new Date(pricing.airlinesValueAdd.tomorrowTime),
			format:'Y-m-d',
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.effectiveTime'),
	        xtype : 'datefield'
		},{
			name: 'oilAddFee',//燃油附加费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.fuelSurcharge'),
	        xtype : 'numberfield',
	        minValue:0,
	        decimalPrecision:2,//精确到小数点后2位(执行4舍5入)  
	        step:0.01
		},{
			name: 'endTime',//截止时间
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.theCutOffTime'),
	        editable:false,
	        format:'Y-m-d',
	        minValue:new Date(pricing.airlinesValueAdd.tomorrowTime),
	        xtype : 'datefield'
		},{
			name: 'minOilAddFee',//最低燃油附加费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.minimumFuelSurcharges'),
	        xtype : 'numberfield',
	        decimalPrecision:2,//精确到小数点后2位(执行4舍5入)
	        minValue:0,
	        step:0.01
		},{
			name: 'groundTrsFee',//地面运输费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.groundTransportationCosts'),
	        xtype : 'numberfield',
	        minValue:0,
	        decimalPrecision:2,//精确到小数点后2位(执行4舍5入)  
	        step:0.01
		},{
			name: 'insuranceFee',//保费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.premiums'),
	        xtype : 'numberfield',
	        minValue:0,
	        decimalPrecision:2,//精确到小数点后2位(执行4舍5入)  
	        step:0.01
		},{
			name: 'minGroundTrsFee',//最低地面运输费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.theLowestGroundTransportationFee'),
	        minValue:0,
	        xtype : 'numberfield'
	        
		},{
			name: 'minInsuranceFee',//最低保费
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.minimumPremium'),
	        minValue:0,
	        xtype : 'numberfield'
	        
		},{
			name: 'minTotalFee',//最低总金额
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.minimumTotalAmount'),
	        allowNegative: false,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'description',//备注描述
	        fieldLabel: pricing.airlinesValueAdd.i18n('foss.pricing.remarksDescription'),
	        allowBlank:true,
	        colspan : 2,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});







/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.airlinesValueAdd.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		airlinesValueAddEntity:null,
		closeAction: 'hide' ,
		
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.airlinesValueAddEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.airlinesValueAddEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.airlinesValueAdd.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.airlinesValueAdd.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.airlinesValueAdd.i18n('foss.pricing.showrightEndTimeInfo');
				me.airlinesValueAddEntity.showTime = value;
				me.airlinesValueAddEntity.endTime = null;
				me.getAirlinesValueAddStopFormPanel().getForm().loadRecord( new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity));
			}
		},
		
		pricePlanStopFormPanel:null,
		getAirlinesValueAddStopFormPanel : function(){
	    	if(Ext.isEmpty(this.pricePlanStopFormPanel)){
	    		this.pricePlanStopFormPanel = Ext.create('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyStopFormPanel');
	    	}
	    	return this.pricePlanStopFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getAirlinesValueAddStopFormPanel()];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	airlinesValueAddEntity:null,
	stopPricePlan:function(id){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var airlinesValueAddEntityModel = new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity);
			form.updateRecord(airlinesValueAddEntityModel);
    		airlinesValueAddEntityModel.set('id',id);
    		airlinesValueAddEntityModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':airlinesValueAddEntityModel.data}};
    		var url = pricing.realPath('immediatelyStopAirlinesValueAdd.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airlinesValueAdd.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				name:'showTime',
				width:280,
				xtype: 'displayfield',
				columnWidth:.9
				//原方案生效日期为【'+showbeginTime+'】截止日期为【'+showendTime+'】,您是否立即生效该方案?
				/*value:pricing.airlinesValueAdd.i18n('foss.pricing.showleftTimeInfo')
					  +showbeginTime+ pricing.airlinesValueAdd.i18n('foss.pricing.showmiddleTimeInfo')
					  +showendTime  + pricing.airlinesValueAdd.i18n('foss.pricing.showstopRightEndTimeInfo')*/
			},{ 
				fieldLabel :pricing.airlinesValueAdd.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_airlinesValueAdd_stopEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_airlinesValueAdd_stopEndTime_ID-inputEl'
				},
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var id = me.up('window').airlinesValueAddEntity.id;
					me.stopPricePlan(id);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});


/**
 * 立即激活价格方案Window
 */
Ext.define('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.airlinesValueAdd.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		airlinesValueAddEntity:null,
		closeAction: 'hide' ,
		
		listeners:{
			beforehide:function(me){
				var form = me.down('form');
				form.getForm().reset();
			},
			beforeshow:function(me){
				var beginTime = Ext.Date.format(new Date(me.airlinesValueAddEntity.beginTime), 'Y-m-d H:i:s');
				var endTime = Ext.Date.format(new Date(me.airlinesValueAddEntity.endTime), 'Y-m-d H:i:s');
				var value = pricing.airlinesValueAdd.i18n('foss.pricing.showleftTimeInfo')
					  +beginTime+pricing.airlinesValueAdd.i18n('foss.pricing.showmiddleTimeInfo')
					  +endTime+pricing.airlinesValueAdd.i18n('foss.pricing.showrightEndTimeInfo');
				me.airlinesValueAddEntity.showTime = value;
				me.airlinesValueAddEntity.beginTime = null;
				me.getAirPricePlanImmediatelyActiveFormPanel().getForm().loadRecord( new Foss.pricing.AirlinesValueAddEntity(me.airlinesValueAddEntity));
			}
		},
		
		airPricePlanImmediatelyActiveFormPanel:null,
		getAirPricePlanImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.airPricePlanImmediatelyActiveFormPanel)){
	    		this.airPricePlanImmediatelyActiveFormPanel = Ext.create('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyActiveFormPanel');
	    	}
	    	return this.airPricePlanImmediatelyActiveFormPanel;
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getAirPricePlanImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.airlinesValueAdd.AirlinesValueAddImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetionPricePlan:function(airlinesValueAddEntity){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var airlinesValueAddEntityModel = new Foss.pricing.AirlinesValueAddEntity(airlinesValueAddEntity);
			form.updateRecord(airlinesValueAddEntityModel);
			var beginTime = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
			var currentDate = new Date(new Date() - 5000);
    		if(beginTime < currentDate){
    			pricing.showInfoMes('开始时间不能早于当前时间！');
    			return;
    		}
    		var endTime = airlinesValueAddEntity.endTime;
    		if(beginTime > endTime){
    			pricing.showInfoMes('开始时间不能晚于结束时间！');
    			return;
    		}
    		airlinesValueAddEntityModel.set('beginTime',beginTime);
    		airlinesValueAddEntityModel.set('id',airlinesValueAddEntity.id);
    		var params = {'airlinesValueAddVo':{'airlinesValueAddEntity':airlinesValueAddEntityModel.data}};
    		var url = pricing.realPath('immediatelyActiveAirlinesValueAdd.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				pricing.pagingBar.moveFirst();   			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.airlinesValueAdd.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				name:'showTime',
				width:280,
				xtype: 'displayfield',
				columnWidth:.9
				//原方案生效日期为【'+showbeginTime+'】截止日期为【'+showendTime+'】,您是否立即生效该方案?
//				value:pricing.airlinesValueAdd.i18n('foss.pricing.showleftTimeInfo')
//					  +showbeginTime+pricing.airlinesValueAdd.i18n('foss.pricing.showmiddleTimeInfo')
//					  +showendTime+pricing.airlinesValueAdd.i18n('foss.pricing.showrightEndTimeInfo')
			},{
				fieldLabel:pricing.airlinesValueAdd.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_airlinesValueAdd_activetionEndTime_ID',
				allowBlank:false,
				dateConfig: {
					el : 'Foss_airlinesValueAdd_activetionEndTime_ID-inputEl'
				},
				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					var airlinesValueAddEntity = me.up('window').airlinesValueAddEntity;
					me.activetionPricePlan(airlinesValueAddEntity);
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.airlinesValueAdd.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});



