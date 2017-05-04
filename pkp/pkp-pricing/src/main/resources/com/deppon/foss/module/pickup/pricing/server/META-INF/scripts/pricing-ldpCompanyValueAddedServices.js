 pricing.ldpCompanyValueAddedServices.tomorrowTime = null;//获取服务器当前时间+1天的时间
 pricing.ldpCompanyValueAddedServices.ValueAdded = {number:/(^[0-9]+.?[0-9]*$)/};

//获取服务当前时间
pricing.haveServerNowTime = function(){
	
	Ext.Ajax.request({
		url:pricing.realPath('getAppCurrentTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.objectVo.nowTime);//获取服务当前时间
			 pricing.ldpCompanyValueAddedServices.tomorrowTime = today.setDate(today.getDate()+1);//设置明天的时间
			
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes('请求超时！');
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes('请求超时！');
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
	 return pricing.ldpCompanyValueAddedServices.tomorrowTime;
};
//转换long类型为日期(在model中会用到)
pricing.changeLongToDate = function(value) {
	if (!Ext.isEmpty(value)) {
		return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
	} else {
		return null;
	}
};

//Ajax请求--json
pricing.requestJsonAjax = function(url,params,successFn,failFn)
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

//快递代理增值服务弹出界面上 数据渲染
pricing.initLdpCompanyAddValueServicesWin = function(win,formRecord,viewState){
	//加载数据
	win.editForm.loadRecord(formRecord);
	win.editForm.getForm().findField('loadOrgCode').setCombValue(formRecord.get('loadOrgName'),formRecord.get('loadOrgCode'));//配载部门
	win.editForm.getForm().findField('expressPartbussCode').setCombValue(formRecord.get('expressPartbussName'),formRecord.get('expressPartbussCode'));//快递代理公司
	win.editForm.getForm().findField('proCode').setCombValue(formRecord.get('proName'),formRecord.get('proCode'));//省份
	//新增的时候时间默认为2999-01-01
	if(viewState == 'ADD'){
	    win.editForm.getForm().findField('endTime').setValue('2999-01-01');
	}
	
	if(viewState == 'VIEW'){
		var btnArr = win.query('button');
		for(var i = 0; i < btnArr.length;i++){
			btnArr[i].setDisabled(i != 0);
		}
		win.editForm.getForm().getFields().each(function(item){
					//设置为只读的
				item.setReadOnly(true);
				});
	}
	return win;

};

//提交快递代理公司增值服务
pricing.submitLdpCompanyAddValueServices = function(win,viewState,partbussValueAddEntity,grid){
		var url = '',
		m_success = '保存成功！',
		m_failure = '保存失败！',
		m_dateError = '数据异常！',
		objectVo = {};
	objectVo.partbussValueAddEntity = partbussValueAddEntity;
	if(viewState === 'ADD'){
		//新增URL
		url = pricing.realPath('addNewPartbussValueAddEntity.action')
	}else if(viewState === 'UPDATE'){
		//修改URL
		url = pricing.realPath('updatePartbussValueAddEntity.action');
	}

	Ext.Ajax.request({
		url:url,
		jsonData:{'objectVo':objectVo},
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(m_dateError);
			}else{//操作返回值 1：成功；-1：失败
				if(result.objectVo.returnInt === 1){
					grid.store.loadPage(1);
					pricing.showWoringMessage(m_success);
					win.hide();
				}else if(result.objectVo.returnInt === -1){
					pricing.showErrorMes(Ext.isEmpty(result.message)? m_failure:result.message);
				}
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(m_dateError);
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

//是否启用状态转换
pricing.changeBealoonToZhongWen = function(value) {
	if (!Ext.isEmpty(value)) {
		if(value === 'Y'){
			return '激活';
		}
		if(value === 'N'){
			return '未激活';
		}
	} else {
		return '未激活';
	}
};
/**
 * 快递代理公司增值服务MODEL
 */
Ext.define('Foss.pricing.addedValueServices.PartbussValueAddModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'expressPartbussCode',
        type : 'string'
    },{
        name : 'expressPartbussName',//快递代理公司
        type : 'string'
    },{
        name : 'loadOrgCode',//配载部门编码
        type : 'string'
    },{
        name : 'loadOrgName',//配载部门名称
        type : 'string'
    },{
        name : 'proCode',//省份编码
        type : 'string'
    },{
        name : 'proName',//省份名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string',
        convert : pricing.changeBealoonToZhongWen
    },{
        name : 'beginTime',//开始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',//结束时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'lastCreatorCode',//最后创建人编码
        type : 'string'
    },{
        name : 'lastCreatorName',//最后创建人名称
        type : 'string'
    },{
        name : 'lastCreateTime',//最后创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'codRate',//代收货款手续费费率
        type : 'string'
    },{
        name : 'minCodFee',//代收货款手续费最低一票
        type : 'number'
    },{
        name : 'insuranceFeeRate',//保价费率
        type : 'string'
    },{
        name : 'minInsuranceFee',//保价费率最低一票
        type : 'number'
    },{
        name : 'freightPayFeeRate',//到付费率
        type : 'string'
    },{
        name : 'minFreightPayFee',//到付费率最低一票
        type : 'number'
    },{
        name : 'descption',//备注描述
        type : 'string'
    }]
});

//快递代理增值服务STORE
Ext.define('Foss.pricing.addedValueServicesStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.addedValueServices.PartbussValueAddModel',
	pageSize:10,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('queryEntityByParams.action'),
		reader : {
			type : 'json',
			root : 'objectVo.partbussValueAddEntityList',
			totalProperty : 'totalCount'
		}
	}
});

//快递代理公司增值服务 查询条件
Ext.define('Foss.pricing.QueryLdpCompanyAddValueServicesForm', {
	extend : 'Ext.form.Panel',
	title : pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyAddServiceView'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	labelWidth:70
    },
    height :180,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,												//绑定的model Foss.pricing.commonSelector.AgencyDeptModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
//		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.pricing.addedValueServices.PartbussValueAddModel'):me.record);
	},
	getItems:function(){
		var me = this;
		return [{
			xtype:'commonLdpAgencyCompanySelector',
			fieldLabel:pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
			labelWidth:100,
			name:'expressPartbussCode'
		},{
			xtype:'dynamicorgcombselector',
			fieldLabel:'配载部门',
			name:'loadOrgCode',
			type:'ORG'
		},{
			xtype:'datefield',
			fieldLabel:'开始时间',
			format:'Y-m-d H:i:s',
			name:'beginTime'/*,
			editable:false*/
		},{
			xtype:'datefield',
			fieldLabel:'结束时间',
			format:'Y-m-d H:i:s',
			name:'endTime'/*,
			editable:false*/
		},{
			xtype : 'container',
			colspan:4,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text : '重置',
				handler : function() {
					this.up('form').getForm().reset();

				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
				text : '查询',
				cls:'yellow_button',
				handler : function() {
					if(!this.up('form').getForm().isValid()){
			    		pricing.showErrorMes("请检测数据必填项【*】是否填写完整且符合规范，有叉号的地方" +
			    				"表示输入有问题，将鼠标移动至有叉号的地方，会有详细提示！");
			    		return;
			    	}
				    //查询条件是否全部可为空
					var grid = Ext.getCmp('T_pricing-addValueServicesIndex_content').getQueryGrid();
					grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				}
			}]
		}];
	}
});
//------------------------------------GRID----------------------------------

/**
 * 快递代理公司增值服务列表grid
 */
Ext.define('Foss.pricing.QueryLdpCompanyAddValueServicesGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyAddService'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 10,
				prependButtons : true,
					defaults : {
						margin : '0 0 15 3'
					}
			});
		}
		return this.pagingToolbar;
	},
	
	//激活增值服务
	activeValueAdded:function(){
		var me = this;
		var valuationIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage('请选择要激活的增值服务！');//请选择要激活的增值服务！
			return;
		}
		if(selections.length>1){
			pricing.showWoringMessage('请选择一条记录进行激活操作！');//请选择要激活的增值服务！
			return;
		}
		var today = new Date();
		today.setHours(0); //获取当前小时数(0-23)
        today.setMinutes(0); //获取当前分钟数(0-59)
        today.setSeconds(0);
		var tomorrow = today.setDate(today.getDate()+1);
		var tomorrow = Ext.Date.format(new Date(tomorrow ),'Y-m-d H:i:s');
		for(var i = 0 ; i<selections.length ; i++){
			//只有未被激活的区域的ID才会传到后台
			if(selections[i].get('active')=='未激活'){
				if(selections[i].get('beginTime')< tomorrow){//过期的数据不能激活
					pricing.showWoringMessage('该快递代理增值服务信息已过期【执行激活操作时，开始时间不能早于当前时间下一天的开始时间】，不能激活，可以修改开始时间进行激活');
					return;
				}else{
					valuationIds.push(selections[i].get('id'));
				}
			}
		}
		if(valuationIds.length<1){
			pricing.showWoringMessage('请选择一条未激活的进行激活操作！');//请至少选择一条未激活的增值服务！
			return;
		}
		pricing.showQuestionMes('是否要激活这些未激活的区域增值服务？',function(e){//是否要激活这些未激活的区域增值服务？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'objectVo':{'ids':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes('请求超时！');
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeValueAddedServices.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//删除增值服务
	deleteValueAdded:function(){
		var me = this;
		var valuationIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage('请选择要删除的增值服务！');//请选择要删除的增值服务！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='激活'){//只有未激活的增值服务才可以删除
				pricing.showWoringMessage('只有未激活的增值服务才可以删除');//请选择要删除的未激活增值服务！
				return;
			}else if(selections[i].get('active')=='未激活'){
				valuationIds.push(selections[i].get('id'));
			}
		}
		pricing.showQuestionMes('是否要删除这些未激活的增值服务？',function(e){//是否要删除这些未激活的增值服务？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'objectVo':{'ids':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes('请求超时！');
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deletePartbussValueAddEntity.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
		
	},
	   /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopWindow:null,
	getImmediatelyStopWindow: function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStop:function(){
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
	 	if(selections[0].get('active')!='激活'){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var partbussValueAddEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().partbussValueAddEntity = partbussValueAddEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActive:function(){
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
	 	if(selections[0].get('active')=='激活'){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var partbussValueAddEntity = selections[0].data;
	 		me.getImmediatelyActiveWindow().partbussValueAddEntity = partbussValueAddEntity;
	 		me.getImmediatelyActiveWindow().show();
	 	}
	},
	
	//得到快递代理公司增值服务编辑窗体
	getLdpCompanyAddValueServicesWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.pricing.addedValueServices.PartbussValueAddModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.pricing.LdpCompanyAddedValueServicesWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return pricing.initLdpCompanyAddValueServicesWin(win,formRecord,viewState);
	},
	//得到快递代理公司增值服务编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改
	getAddValueServicesWin:function(viewState,param){
		if(viewState === 'ADD'){
			return this.getLdpCompanyAddValueServicesWin(this.addLdpCompanyAddedValueServicesWin,pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.addExpressDeliveryAgencyCompanyAddService'),viewState,param);
		}else if(viewState === 'UPDATE'){
			return this.getLdpCompanyAddValueServicesWin(this.updateLdpCompanyAddedValueServicesWin,pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.uppdateExpressDeliveryAgencyCompanyAddService'),viewState,param);
		}else if(viewState === 'VIEW')
			{
			return this.getLdpCompanyAddValueServicesWin(this.updateLdpCompanyAddedValueServicesWin,pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.deleteExpressDeliveryAgencyCompanyAddService'),viewState,param);

			}
	},
	addLdpCompanyAddedValueServicesWin:null,						//新增快递代理公司增值服务
	addLdpCompanyAddedValueServices:function(param){
		return this.getAddValueServicesWin('ADD',param);
	},
	updateLdpCompanyAddedValueServicesWin:null,						//修改快递代理公司增值服务
	updateLdpCompanyAddedValueServices:function(param){
		return this.getAddValueServicesWin('UPDATE',param);
	},
	viewLdpCompanyAddedValueServicesWin:null,						//查看快递代理公司增值服务
	viewLdpCompanyAddedValueServices:function(param){
		return this.getAddValueServicesWin('VIEW',param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns();
		me.store = me.getStore();
		me.listeners = me.getMyListeners();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar();
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
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
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.pricing.addedValueServicesStore',{
			autoLoad : false,
			pageSize : 10,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-addValueServicesIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var partbussValueAddEntity = queryForm.record.getData();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//快递代理公司编码
								'objectVo.partbussValueAddEntity.expressPartbussCode':partbussValueAddEntity.expressPartbussCode,
								//快递代理公司名称
								'objectVo.partbussValueAddEntity.expressPartbussName':partbussValueAddEntity.expressPartbussName,
								//配载部门
								'objectVo.partbussValueAddEntity.loadOrgCode':partbussValueAddEntity.loadOrgCode,
								//配载部门名称
								'objectVo.partbussValueAddEntity.loadOrgName':partbussValueAddEntity.loadOrgName,
								//开始时间
								'objectVo.partbussValueAddEntity.beginTime':partbussValueAddEntity.beginTime,
								//结束时间
								'objectVo.partbussValueAddEntity.endTime':partbussValueAddEntity.endTime
							}
						});	
					}
				}
		    }
		});
	},
	getTbar:function(){
		var me = this;
		return [{
			text : '新增',
			disabled: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesAddbutton'),
			hidden: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesAddbutton'),
			handler :function(){
				var win = me.addLdpCompanyAddedValueServices({});
				win.show();
			} 
		}/*,
		'-', 
		{
			text : '升级',
			handler :function(){
				var grid = Ext.getCmp('T_pricing-addValueServicesIndex_content').getQueryGrid();
				selection=grid.getSelectionModel().getSelection();
				if(selection.length<=0 ){
					Ext.MessageBox.alert('提醒','请选择一行数据进行升级！');
				}
				var param = {};			
            	param.formRecord = selection[0].data;
				me.updateLdpCompanyAddedValueServices(param).show();
			} 
		}*/,
		'-', 
		{
			text : '激活',
			disabled: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesActivebutton'),
			hidden: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesActivebutton'),
			handler :function(){
				me.activeValueAdded();
			} 
		},
		'-',
		{
			text : '删除',
			disabled: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesDeletebutton'),
			hidden: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesDeletebutton'),
			handler :function(){
				me.deleteValueAdded();
			} 
		},
		'-',
		{
			text : '立即激活',
			disabled: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesimmediatelyActivebutton'),
			hidden: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesimmediatelyActivebutton'),
			handler :function(){
				me.immediatelyActive();
			} 
		},
		'-',
		{
			text : '立即终止',
			disabled: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesimmediatelyStopbutton'),
			hidden: !pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesimmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		}];
	},
	getColumns:function(){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			text : '操作',
			items: [{
				iconCls: 'deppon_icons_showdetail',
                tooltip: '查看',//查看详情
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var param = {};
                	var record = grid.getStore().getAt(rowIndex);				
                	param.formRecord = record;
                	me.viewLdpCompanyAddedValueServices(param).show();
                }
			},{
				iconCls: 'deppon_icons_edit',
                tooltip: '修改',//修改
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === '未激活') {//未激活的显示
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';//隐藏图标
					}
				},
				disabled:!pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/ldpCompanyValueAddedServicesUpdatebutton'),
                handler: function(grid, rowIndex, colIndex) {
                	var param = {};
                	var record = grid.getStore().getAt(rowIndex);				
                	param.formRecord = record;
    				me.updateLdpCompanyAddedValueServices(param).show();
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: '升级版本',//升级版本
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === '激活') {//激活的显示
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';//隐藏图标
					}
				},
				disabled:!pricing.ldpCompanyValueAddedServices.isPermission('ldpCompanyValueAddedServices/	ldpCompanyValueAddedServicesPromotebutton'),
                handler: function(grid, rowIndex, colIndex) {
                	var param = {};
                	var record = grid.getStore().getAt(rowIndex);				
                	param.formRecord = record;
    				me.updateLdpCompanyAddedValueServices(param).show();
                }
			}]
		},{
			text:'配载部门',
			dataIndex : 'loadOrgName',
			flex : 1
		},{
			text: pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
			dataIndex : 'expressPartbussName',
			labelWidth:100,
			flex : 1.2
		},{
			text:'省份',
			dataIndex : 'proName',
			flex : 0.65
		},{
			text:'状态',
			dataIndex : 'active',
			flex : 0.8
		},{
			text:'开始时间',
			dataIndex : 'beginTime',
			flex : 0.75
		},{
			text:'结束时间',
			dataIndex : 'endTime',
			flex : 0.75
		},{
			text:'最后创建人',
			dataIndex : 'lastCreatorName',
			flex : 0.75
		},{
			text:'最后创建时间',
			dataIndex : 'lastCreateTime',
			flex : 0.8
		},{
			text:'代收货款手续费率',
			dataIndex : 'codRate',
			flex : 0.8
		},{
			text:'代收货款手续费最低一票',
			dataIndex : 'minCodFee',
			flex : 0.85
		},{
			text:'保价费率',
			dataIndex : 'insuranceFeeRate',
			flex : 0.75
		},{
			text:'保价费最低一票',
			dataIndex : 'minInsuranceFee',
			flex : 0.8
		},{
			text:'到付费率',
			dataIndex : 'freightPayFeeRate',
			flex : 0.75
		},{
			text:'到付费最低一票',
			dataIndex : 'minFreightPayFee',
			flex : 0.8
		},{
			text:'ID',
			dataIndex : 'id',
			hidden:true,
			flex : 0.75
		}];
	}
});

//---------------------------------------新增或修改落地增值服务form

//快递代理增值服务 编辑窗体 form
Ext.define('Foss.pricing.LdpCompanyAddedValueServicesWinForm', {
	extend : 'Ext.form.Panel',
	title:pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyAddService'),
	frame: true,
	width :500,
	height :550,	
	defaultType : 'textfield',
	layout:{
      type: 'table',
      columns: 2
  },
  viewState:null,											//查看状态
  formRecord:null,										//加载的record
	constructor : function(config){							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
			margin : '8 10 5 10',
			allowBlank:false,
			labelWidth:155
	    };
	},
	//获得表单元素
	getItems:function(config){
		var me = this;
		return [{
			xtype:'dynamicorgcombselector',
			type:'ORG',
			fieldLabel:'配载部门',
			name:'loadOrgCode',
			allowBlank:true
		},{
			xtype:'commonLdpAgencyCompanySelector',
			fieldLabel:pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompany'),
			labelWidth:100,
			name:'expressPartbussCode'
		},{
			name:'proCode',// 省份名称—对应name
			fieldLabel :'省份',
			xtype : 'commonprovinceselector'
		},{
			xtype:'datefield',
			fieldLabel:'生效时间',
			labelWidth:80,
			format:'Y-m-d H:i:s',
//			editable:false,
			name:'beginTime'
		},{
			fieldLabel:'代收货款手续费率',
			name:'codRate',
			renderer: function(value,metaData ,record){
	        	if(value!=null||value!='')
	        	return value;
	        },
	        regex:pricing.ldpCompanyValueAddedServices.ValueAdded.number,
	        regexText:'非法输入，只能输入数字！',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:4,
	            step:0.001,
	            minValue: 0,
	            maxValue: 1
	        }
		},{
			xtype:'datefield',
			fieldLabel:'截止时间',
			labelWidth:80,
			format:'Y-m-d H:i:s',
//			editable:false,
			name:'endTime'
		},{
			fieldLabel:'代收货款手续费最低一票',
			name:'minCodFee',
			xtype: 'numberfield',
            decimalPrecision:3,
	        step:0.01,
            minValue: 0,
            maxValue: 99999999
		},{
			fieldLabel:'保价费率',
			name:'insuranceFeeRate',
			labelWidth:80,
			renderer: function(value,metaData ,record){
	        	if(value!=null||value!='')
	        	return value;
	        },
	        regex:pricing.ldpCompanyValueAddedServices.ValueAdded.number,
			regexText:'非法输入，只能输入数字！',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:4,
	            step:0.001,
	            minValue: 0,
	            maxValue: 1
	        }
	    },{
			fieldLabel:'保价费率最低一票',
			name:'minInsuranceFee',
			xtype: 'numberfield',
            decimalPrecision:3,
	        step:0.01,
            minValue: 0,
            maxValue: 99999999
		},{
			fieldLabel:'到付手续费率',
			name:'freightPayFeeRate',
			labelWidth:100,
			renderer: function(value,metaData ,record){
	        	if(value!=null||value!='')
	        	return value;
	        },
	        regex:pricing.ldpCompanyValueAddedServices.ValueAdded.number,
			regexText:'非法输入，只能输入数字！',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:4,
	            step:0.001,
	            minValue: 0,
	            maxValue: 1
	        }
	    },{
			fieldLabel:'到付手续费率最低一票',
			name:'minFreightPayFee',
			colspan:2,
			xtype: 'numberfield',
            decimalPrecision:3,
	        step:0.01,
            minValue: 0,
            maxValue: 99999999
		},{
			width:570,
			height:120,
			colspan:2,
			xtype:'textareafield',
			maxLength:500,
			maxLengthText:'请不要输入超过500个字符',
			allowBlank:true,
			fieldLabel:'备注描述',
			labelWidth:80,
			name:'descption'
		}];
	}
});
/**
* 新增或修改快递代理增值服务信息win
*/
Ext.define('Foss.pricing.LdpCompanyAddedValueServicesWin',{
	extend : 'Ext.window.Window',
	title:pricing.ldpCompanyValueAddedServices.i18n('i18n.pricingRegion.expressDeliveryAgencyCompanyAddService'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :700,
	height :520,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
		}
	},
	sourceGrid:null,										
	editForm:null,											//快递代理增值服务表单Form
//	editGrid:null,											//快递代理增值服务表格Grid
	formRecord:null,										//快递代理增值服务实体 
	gridDate:null,											//快递代理增值服务信息数组  
  constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.pricing.LdpCompanyAddedValueServicesWinForm',{'viewState':config.viewState,formRecord:config.formRecord});
		me.items = [me.editForm];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text:'取消',
			handler :function(){
				me.hide();
			}
		},{
			text:'重置',
			handler :function(){
				var gridData = [];
				pricing.initLdpCompanyAddValueServicesWin(me,me.formRecord);
			} 
		},{
			text:'保存',
			cls:'yellow_button',
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		pricing.showErrorMes("请检测数据必填项【*】是否填写完整且符合规范，有叉号的地方" +
		    				"表示输入有问题，将鼠标移动至有叉号的地方，会有详细提示！");
		    		return;
		    	}
		    	if(editForm.findField('beginTime').getValue() > editForm.findField('endTime').getValue()){
		    		pricing.showErrorMes('生效时间不能晚于截止时间！');
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		pricing.submitLdpCompanyAddValueServices(me,me.viewState,me.formRecord.data,me.sourceGrid);
			}
		}];
	}
});

/**
 * 立即中止增值服务方案 Window
 */
Ext.define('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: '立即终止方案',
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = me.partbussValueAddEntity.beginTime;
	    		var showendTime = me.partbussValueAddEntity.endTime;
	    		var value = '原方案生效日期为【'
				  +showbeginTime+'】截止日期为【'
				  +showendTime+ '】，是否立即终止该方案？';
	    		me.getStopFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getStopFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getStopFormPanel()];
			me.callParent(cfg);
		}
});

/**
 * 立即中止增值服务功能FormPanel
 */
Ext.define('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var partbussValueAddModel = Ext.create('Foss.pricing.addedValueServices.PartbussValueAddModel');
			form.updateRecord(partbussValueAddModel);
			var endTime = me.up('window').partbussValueAddEntity.endTime;
			var beginTime = me.up('window').partbussValueAddEntity.beginTime;
			var endDate = form.findField('endTime').getValue();
			if(endDate < beginTime){
				pricing.showWoringMessage('立即终止的截止时间不能早于当前记录的开始时间!');
				return;
			}
			if(endDate > endTime){
				pricing.showWoringMessage('立即终止的截止时间不能晚于当前记录的结束时间!');
				return;
			}
			var id = me.up('window').partbussValueAddEntity.id;
			partbussValueAddModel.set('id',id);
			partbussValueAddModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
//			partbussValueAddModel.set('endTime',endDate);
    		var params = {'objectVo':{'partbussValueAddEntity':partbussValueAddModel.data}};
    		var url = pricing.realPath('inActiveImmediatelyValueAddedServices.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes('请求超时');
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
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{ 
				fieldLabel :'中止日期' ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_regionValueAdded_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_regionValueAdded_stopEndTime_ID-inputEl'
				},
				allowBlank:false,
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : '确认',
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : '取消',
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});


/**
 * 立即激活增值服务方案Window
 */
Ext.define('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: '立即激活方案',//"立即激活方案",
		width:380,
		height:152,
		partbussValueAddEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = me.partbussValueAddEntity.beginTime;
	    		var showendTime = me.partbussValueAddEntity.endTime;
	    		var value = '原方案生效日期为【'
					  +showbeginTime+'】截止日期为【'
					  +showendTime+ '】，是否立即激活该方案？';
	    		me.getImmediatelyActiveFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getImmediatelyActiveFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活增值服务功能Form
 */
Ext.define('Foss.pricing.LdpCompanyAddedValueServices.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var partbussValueAddModel = Ext.create('Foss.pricing.addedValueServices.PartbussValueAddModel');
			form.updateRecord(partbussValueAddModel);
			var date = Ext.Date.format(new Date(new Date().getTime()), 'Y-m-d H:i:s');
			var endTime = me.up('window').partbussValueAddEntity.endTime;
			var beginTime = me.up('window').partbussValueAddEntity.beginTime;
			var beginDate = form.findField('beginTime').getValue();
			if(beginDate < date){
				pricing.showWoringMessage('立即激活的生效时间不能早于当前时间！');
				return;
			}
			if(beginDate < beginTime){
				pricing.showWoringMessage('立即激活的生效时间不能早于当前记录的开始时间!');
				return;
			}
			if(beginDate > endTime){
				pricing.showWoringMessage('立即激活的生效时间不能晚于当前记录的结束时间!');
				return;
			}
			var id = me.up('window').partbussValueAddEntity.id;
			partbussValueAddModel.set('id',id);
  		    partbussValueAddModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
//			partbussValueAddModel.set('beginTime',beginDate);
    		var params = {'objectVo':{'partbussValueAddEntity':partbussValueAddModel.data}};
    		var url = pricing.realPath('activeImmediatelyValueAddedServices.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes('请求超时');
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
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{
				fieldLabel:'生效日期',//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_regionValueAdded_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_regionValueAdded_activeEndTime_ID-inputEl'
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
				text : '确认',//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : '取消',//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});

//------------------------------------ONREADY----------------------------------
/**
* 程序入口方法
*/
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-addValueServicesIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.pricing.QueryLdpCompanyAddValueServicesForm',{'record':Ext.create('Foss.pricing.addedValueServices.PartbussValueAddModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.pricing.QueryLdpCompanyAddValueServicesGrid');//查询结果显示列表
	Ext.getCmp('T_pricing-addValueServicesIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_pricing-addValueServicesIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items : [ queryForm, queryGrid]
	}));
});
