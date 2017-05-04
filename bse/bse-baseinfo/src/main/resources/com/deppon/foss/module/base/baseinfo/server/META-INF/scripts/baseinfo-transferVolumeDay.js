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
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author lifanghong
 * @时间 2012-8-31
 */
//baseinfo.showInfoMes = function(message,fun) {
//	var len = message.length;
//	Ext.Msg.show({
//	    title:'FOSS提醒您:',
//	    width:110+len*15,
//	    msg:'<div id="message">'+message+'</div>',
//	    buttons: Ext.Msg.OK,
//	    icon: Ext.MessageBox.INFO,
//	    callback:function(e){
//	    	if(!Ext.isEmpty(fun)){
//	    		if(e=='ok'){
//		    		fun();
//		    	}
//	    	}
//	    }
//	});
//
////	setTimeout(function(){
////        Ext.Msg.hide();
////    }, 3000);
//};
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
//baseinfo.transferVolumeDay.yes = 'Y';//是
//baseinfo.transferVolumeDay.no = 'N';//否
//baseinfo.transferVolumeDay.ALL = 'ALL';//全部
baseinfo.transferVolumeDay.carType = [{'valueName':baseinfo.transferVolumeDay.i18n('foss.baseinfo.4mi2'),'valueCode':'fourPointTwo'}
,{'valueName':baseinfo.transferVolumeDay.i18n('foss.baseinfo.6mi5'),'valueCode':'sixPointFive'}
,{'valueName':baseinfo.transferVolumeDay.i18n('foss.baseinfo.7mi6'),'valueCode':'sevenPointSix'}
,{'valueName':baseinfo.transferVolumeDay.i18n('foss.baseinfo.9mi6'),'valueCode':'ninePointSix'}
,{'valueName':baseinfo.transferVolumeDay.i18n('foss.baseinfo.17mi5'),'valueCode':'seventeenPointFive'}];//停靠车类型
//--------------------------------------baseinfo----------------------------------------
//月台信息
Ext.define('Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyTime',
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'code',// 部门编码
        type : 'string'
    },{
        name : 'name',// 部门名称
        type : 'string'
    },{
        name : 'fullValue',// 仓库饱和度预警值
        type : 'string'
    },{
        name : 'volumeDay',//日承载货量
        type : 'string'
    },{
        name : 'lookDangerValue',// 仓库饱和度预警值(显示)
        type : 'string'
    },{
        name : 'lookFullValue',//日承载货量（显示）
        type : 'string'
    },{
        name : 'dangerValue',// 是否有升降台
        type : 'string'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 月台Store（Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity）
 */
Ext.define('Foss.baseinfo.transferVolumeDay.PlatformStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity',//月台的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryTransferVolumeDayList.action'),//请求地址
		reader : {
			type : 'json',
			root : 'transferVolumeDayVo.transferVolumeDayEntitys',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 月台表单
 */
Ext.define('Foss.baseinfo.transferVolumeDay.QueryPlatformForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.transferVolumeDay.i18n('Foss.baseinfo.transferVolumeDayInformationInquiry'),//场地日承载货量
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
			xtype : 'commontransfercenterselector',
			name: 'name',//外场名称
			organizationCode:null,//组织编码
			userCode:Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'FOSS_SYSTEM_ADMIN')?null:FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode:Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'FOSS_SYSTEM_ADMIN')?null:FossUserContext.getCurrentDeptCode(),
			fieldLabel : baseinfo.transferVolumeDay.i18n('foss.baseinfo.outfield'),
			listeners:{
				select:function(comb,records,empo){
					comb.organizationCode = records[0].get('orgCode');
				}
			}
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformQueryButton'),
			hidden:!baseinfo.transferVolumeDay.isPermission('platform/platformQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('organizationCode').organizationCode = null;
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformQueryButton'),
			hidden:!baseinfo.transferVolumeDay.isPermission('platform/platformQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					if(Ext.isEmpty(me.getForm().findField('name').rawValue)){
						me.getForm().findField('name').code = null;
						me.up().getPlatformGrid().getPagingToolbar().moveFirst();
					}else{
						me.up().getPlatformGrid().getPagingToolbar().moveFirst();
					}
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 月台列表
 */
Ext.define('Foss.baseinfo.transferVolumeDay.PlatformGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.transferVolumeDay.i18n('Foss.baseinfo.transferVolumeDayInformation'),//月台信息
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.transferVolumeDay.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
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
	//月台新增WINDOW
	platformAddWindow:null,
	getPlatformAddWindow:function(){
		if (Ext.isEmpty(this.platformAddWindow)) {
			this.platformAddWindow = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformAddWindow');
			this.platformAddWindow.parent = this;//父元素
		}
		return this.platformAddWindow;
	},
	//修改月台WINDOW
	platformUpdateWindow:null,
	getPlatformUpdateWindow:function(){
		if (Ext.isEmpty(this.platformUpdateWindow)) {
			this.platformUpdateWindow = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformUpdateWindow');
			this.platformUpdateWindow.parent = this;//父元素
		}
		return this.platformUpdateWindow;
	},
	//作废月台
	toVoidPlatform: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.wantSetAsideThisTransferVolumeDays'),function(e){//是否要作废这些月台？
			if(e=='yes'){//询问是否删除，是则发送请求
				var platformVirtualCodes = new Array();//月台
				for(var i = 0 ; i<selections.length ; i++){
					platformVirtualCodes.push(selections[i].get('code'));
				}
				var params = {'transferVolumeDayVo':{'codes':platformVirtualCodes}};
				var successFun = function(json){
					Ext.MessageBox.alert(baseinfo.transferVolumeDay.i18n('foss.transferVolumeDay.FOSSRemindYou'),json.message); ;
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.requestTimeout'));//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteTransferVolumeDayEntityByCode.action');
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
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.operate'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.transferVolumeDay.i18n('foss.baseinfo.update'),//修改
//                disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var code= record.get('code');//站点组虚拟编码
    				var params = {'transferVolumeDayVo':{'transferVolumeDayEntity':{'code':code}}}
    				var successFun = function(json){
    					var updateWindow = me.getPlatformUpdateWindow();//获得修改窗口
    					if(Ext.isEmpty(json.transferVolumeDayVo.transferVolumeDayEntity)){
    						baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.returnDataEmpty'));//返回数据为空！
    						return ;
    					}
//    					json.platformVo.transferVolumeDayEntity.width = json.platformVo.transferVolumeDayEntity.width/1000;
//    					json.platformVo.transferVolumeDayEntity.height = json.platformVo.transferVolumeDayEntity.height/1000;
    					updateWindow.transferVolumeDayEntity = json.transferVolumeDayVo.transferVolumeDayEntity;//站点组
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.requestTimeout'));//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('queryTransferVolumeDay.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.transferVolumeDay.i18n('foss.baseinfo.void'),//作废
//                disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformVoidButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.wantSetAsideThisTransferVolumeDay'),function(e){//是否要作废这个月台？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var platformVirtualCodes = new Array();//月台
            				platformVirtualCodes.push(record.get('code'));
            				var params ={'transferVolumeDayVo':{'codes':platformVirtualCodes}};
            				var successFun = function(json){
            					Ext.MessageBox.alert(baseinfo.transferVolumeDay.i18n('foss.transferVolumeDay.FOSSRemindYou'),json.message); ;
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deleteTransferVolumeDayEntityByCode.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.fieldID'),//外场编号
			dataIndex : 'code'
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.fieldName'),//外场名称
			dataIndex : 'name'
		},{
			text : '日承载货量（T）',//月台编号
			dataIndex : 'volumeDay'
		},{
			text : '仓库饱和度预警值',//可停靠车型
			flex:2,
			dataIndex : 'lookFullValue'
		},{
			text : '仓库饱和度危险值',//高度（米）
			flex:2,
			dataIndex : 'lookDangerValue'
		}];
		me.store = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPlatformForm();
					if(queryForm!=null){
//						var vehicleCode = queryForm.getForm().findField('vehicleCode').getValue();
//						var organizationCode = queryForm.getForm().findField('organizationCode').organizationCode;
						var code = queryForm.getForm().findField('name').rawValue;
//						var position = queryForm.getForm().findField('position').getValue();
						Ext.apply(operation,{
							params : {//月台大查询，查询条件组织
//								'platformVo.transferVolumeDayEntity.organizationCode':organizationCode,//外场CODE
//								'platformVo.transferVolumeDayEntity.platformCode':platformCode,//月台编号
//								'platformVo.transferVolumeDayEntity.position':position,//月台位置
								'transferVolumeDayVo.transferVolumeDayEntity.name':code
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
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformAddButton'),
			hidden:!baseinfo.transferVolumeDay.isPermission('platform/platformAddButton'),
			handler :function(){
				me.getPlatformAddWindow().show();
			} 
		},'-',{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.transferVolumeDay.isPermission('platform/platformVoidButton'),
			hidden:!baseinfo.transferVolumeDay.isPermission('platform/platformVoidButton'),
			handler :function(){
				me.toVoidPlatform();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store =me.store;
		me.callParent([cfg]);
	}
});

/**
 * @description 月台主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-transferVolumeDay_content')) {
		return;
	};
	var queryPlatformForm = Ext.create('Foss.baseinfo.transferVolumeDay.QueryPlatformForm');//查询FORM
	var platformGrid = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformGrid');//查询结果GRID
	Ext.getCmp('T_baseinfo-transferVolumeDay').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-transferVolumeDay_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryPlatformForm : function() {
			return queryPlatformForm;
		},
		//获得查询结果GRID
		getPlatformGrid : function() {
			return platformGrid;
		},
		items : [queryPlatformForm, platformGrid] 
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增月台信息
 */
Ext.define('Foss.baseinfo.transferVolumeDay.PlatformAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.transferVolumeDay.i18n('foss.baseinfo.addPlatformTransferVolumeDayInfor'),//新增场地
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.transferVolumeDay.PlatformGrid）
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :590,
	height :350,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getPlatformForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//新增月台FORM
	platformForm:null,
	getPlatformForm : function(){
    	if(Ext.isEmpty(this.platformForm)){
    		this.platformForm = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformForm',{
    			'isUpdate':false
    		});
    	}
    	return this.platformForm;
    },
    //提交月台数据
    commitPlatform:function(button){
    	var me = this;
    	if(me.getPlatformForm().getForm().isValid()){//校验form是否通过校验
    		var platformModel = new Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity();
    		me.getPlatformForm().getForm().updateRecord(platformModel);//将FORM中数据设置到MODEL里面
    		var name =  me.getPlatformForm().getForm().findField('name').rawValue
//    		platformModel.set('height',platformModel.get('height')*1000);
    		platformModel.set('name',name);//后台一毫米计算
    		var params = {'transferVolumeDayVo':{'transferVolumeDayEntity':platformModel.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
//				Ext.MessageBox.alert(baseinfo.transferVolumeDay.i18n('foss.querying.FOSSRemindYou'),json.message); ;//提示新增成功
				Ext.MessageBox.alert(baseinfo.transferVolumeDay.i18n('i18n.baseinfo-util.fossAlert'),json.message); 
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.requestTimeout'));//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('addTransferVolumeDay.action');//请求月台新增
//			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :baseinfo.transferVolumeDay.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
					me.getPlatformForm().getForm().reset();
			} 
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitPlatform(this);
			} 
		}];
		me.items = [me.getPlatformForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改月台
 */
Ext.define('Foss.baseinfo.transferVolumeDay.PlatformUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.transferVolumeDay.i18n('foss.baseinfo.modifyPlatformTransferVolumeDayInfor'),//场地修改
	closable : true,
	resizable:false,
	transferVolumeDayEntity:null,//修改月台数据
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.transferVolumeDay.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :590,
	height :450,
	listeners:{
		beforehide:function(me){
			me.getPlatformForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getPlatformForm().getForm().loadRecord(new Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity(me.transferVolumeDayEntity));
			me.getPlatformForm().getForm().findField('name').setCombValue(me.transferVolumeDayEntity.name,me.transferVolumeDayEntity.code);
			me.getPlatformForm().getForm().findField('code').setValue(me.transferVolumeDayEntity.code);
//			if(!Ext.isEmpty(me.transferVolumeDayEntity.vehicleCode)){
//				me.getPlatformForm().getForm().findField('vehicleCode').setCombValue(me.transferVolumeDayEntity.vehicleName,me.transferVolumeDayEntity.vehicleCode);
//			}
		}
	},
	//修改月台FORM
	platformForm:null,
	getPlatformForm : function(){
    	if(Ext.isEmpty(this.platformForm)){
    		this.platformForm = Ext.create('Foss.baseinfo.transferVolumeDay.PlatformForm',{
    			'isUpdate':true
    		});
    		this.platformForm.getForm().findField('name').setReadOnly(true);
    	}
    	return this.platformForm;
    },
    //修改月台
    commitPlatform:function(button){
    	var me = this;
    	if(me.getPlatformForm().getForm().isValid()){//校验form是否通过校验
    		var platformModel = new Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity(me.transferVolumeDayEntity);
    		me.getPlatformForm().getForm().updateRecord(platformModel);//将FORM中数据设置到MODEL里面
    		var name =  me.getPlatformForm().getForm().findField('name').rawValue
    		platformModel.set('name',name);//后台一毫米计算
//    		platformModel.set('height',platformModel.get('height')*1000);
//    		platformModel.set('width',platformModel.get('width')*1000);//后台一毫米计算
    		var params = {'transferVolumeDayVo':{'transferVolumeDayEntity':platformModel.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
				Ext.MessageBox.alert(baseinfo.transferVolumeDay.i18n('i18n.baseinfo-util.fossAlert'),json.message); ;//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
//				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.transferVolumeDay.i18n('foss.baseinfo.requestTimeout'));//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('updateTransferVolumeDay.action');//请求月台新增
//			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :baseinfo.transferVolumeDay.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
					me.getPlatformForm().getForm().loadRecord(new Foss.baseinfo.transferVolumeDay.TransferVolumeDayEntity(me.transferVolumeDayEntity));
					me.getPlatformForm().getForm().findField('name').setCombValue(me.transferVolumeDayEntity.name,me.transferVolumeDayEntity.code);
			} 
		},{
			text : baseinfo.transferVolumeDay.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitPlatform(this);
			} 
		}];
		me.items = [ me.getPlatformForm()];
		me.callParent([cfg]);
	}
});
/**
 * 月台组-FORM
 */
Ext.define('Foss.baseinfo.transferVolumeDay.PlatformForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.transferVolumeDay.i18n('Foss.baseinfo.transferVolumeDayInformation'),//月台信息
	frame: true,
	isUpdate:false,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	//width:200,
    	colspan : 3
    },
    layout:{
		type:'table',
		columns: 6
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'commontransfercenterselector',
			forceSelection : true,
			allowBlank:false,
//			userCode:FossUserContext.getCurrentUserEmp().empCode,
//			currentOrgCode:FossUserContext.getCurrentDeptCode(),
			name: 'name',//外场名称
			fieldLabel : baseinfo.transferVolumeDay.i18n('foss.baseinfo.fieldName'),
	        listeners:{
	        	select:function(text,records,eops){
	        		me.getForm().findField('code').setValue(records[0].get('orgCode'));
//	        		me.getForm().findField('code').setValue(records[0].get('code'));
	        	}
	        }
		},{
			name: 'code',//外场编号
			readOnly:true,
	        fieldLabel: baseinfo.transferVolumeDay.i18n('foss.baseinfo.fieldID'),
	        xtype : 'textfield'
		},
//		{
//			name: 'code',//外场编号
//			readOnly:true,
//			hidden:true,
//	        xtype : 'textfield'
//		}
		,{
			name: 'volumeDay',//日承载货量
			allowBlank:false,
			hideTrigger:true,
			minValue:0,
			maxValue:99999,
//			regex:new RegExp('^\\d{0,9}$'),
	        fieldLabel: '日承载货量(单位：T)',
	        xtype : 'numberfield'
		},{
			name: 'fullValue',//仓库饱和度预警值
			allowBlank:false,
			hideTrigger:true,
			minValue:0,
			maxValue:99999,
//			regex:new RegExp('^\\d{0,9}$'),
	        fieldLabel: '仓库饱和度预警值(%)',
	        xtype : 'numberfield'
		},{
			name: 'dangerValue',//仓库饱和度预警值
			allowBlank:false,
//			maxLength:4,
			hideTrigger:true,
			minValue:0,
			maxValue:99999,
//			regex:new RegExp('^\\d{0,9}$'),
	        fieldLabel: '仓库饱和度危险值(%)',
	        xtype : 'numberfield'
		}];
		me.callParent([cfg]);
	}
});
//上传附件弹出框

