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
baseinfo.platform.yes = 'Y';//是
baseinfo.platform.no = 'N';//否
baseinfo.platform.ALL = 'ALL';//全部
baseinfo.platform.carType = [{'valueName':baseinfo.platform.i18n('foss.baseinfo.4mi2'),'valueCode':'fourPointTwo'}
,{'valueName':baseinfo.platform.i18n('foss.baseinfo.6mi5'),'valueCode':'sixPointFive'}
,{'valueName':baseinfo.platform.i18n('foss.baseinfo.7mi6'),'valueCode':'sevenPointSix'}
,{'valueName':baseinfo.platform.i18n('foss.baseinfo.9mi6'),'valueCode':'ninePointSix'}
,{'valueName':baseinfo.platform.i18n('foss.baseinfo.17mi5'),'valueCode':'seventeenPointFive'}];//停靠车类型
//--------------------------------------baseinfo----------------------------------------
//月台信息
Ext.define('Foss.baseinfo.platform.PlatformEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyDate',
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'organizationCode',// 部门编码
        type : 'string'
    },{
        name : 'organizationName',// 部门名称
        type : 'string'
    },{
        name : 'platformCode',// 月台编码
        type : 'string'
    },{
        name : 'virtualCode',// 虚拟编码
        type : 'string'
    },{
        name : 'hasLift',// 是否有升降台
        type : 'string'
    },{
        name : 'height',// 高度  毫米
        defaultValue : null
    },{
        name : 'width',// 宽度  毫米
        defaultValue : null
    },{
        name : 'position',// 位置
        type : 'string'
    },{
        name : 'fourPointTwo',// 是否可停4.2
        type : 'string'
    },{
        name : 'sixPointFive',// 是否可停6.5
        type : 'string'
    },{
        name : 'sevenPointSix',// 是否可停7.6
        type : 'string'
    },{
        name : 'ninePointSix',// 是否可停9.6
        type : 'string'
    },{
        name : 'seventeenPointFive',// 是否可停17.5
        type : 'string'
    },{
        name : 'active',// 是否启用
        type : 'string'
    },{
        name : 'notes',//备注
        type : 'string'
    },{
        name : 'transferCode',//外场编码
        type : 'string'
    },{
        name : 'vehicleCode',//最大可停靠车型编码
        type : 'string'
    },{
        name : 'vehicleName',//最大可停靠车型名称
        type : 'string'
    },{
        name : 'abscissa',//横坐标
        type : 'string'
    },{
        name : 'ordinate',//纵坐标
        type : 'string'
    },{
        name : 'longDistance',//月台类型:长途
        type : 'string'
    },{
        name : 'shortDistance',//月台类型:长途
        type : 'string'
    },{
        name : 'pkp',//月台类型:长途
        type : 'string'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 月台Store（Foss.baseinfo.platform.PlatformEntity）
 */
Ext.define('Foss.baseinfo.platform.PlatformStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.platform.PlatformEntity',//月台的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryPlatformListByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'platformVo.platformEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 月台表单
 */
Ext.define('Foss.baseinfo.platform.QueryPlatformForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.platform.i18n('foss.baseinfo.platformInformationInquiry'),//月台信息查询
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
			name: 'organizationCode',//外场名称
			organizationCode:null,//组织编码
			userCode:Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'FOSS_SYSTEM_ADMIN')?null:FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode:Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'FOSS_SYSTEM_ADMIN')?null:FossUserContext.getCurrentDeptCode(),
			fieldLabel : baseinfo.platform.i18n('foss.baseinfo.outfield'),
			listeners:{
				select:function(comb,records,empo){
					comb.organizationCode = records[0].get('orgCode');
				}
			}
		},{
			name:'platformCode',
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.platformNo'),//月台编号
	        xtype : 'textfield'
		},{
			name: 'vehicleCode',
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.dockableModels'),//可停靠车型
	        xtype : 'commonvehicletypeselector'
		},{
			name:'position',
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.location'),//位置
	        xtype : 'textfield'
		},
			new Foss.baseinfo.platform.PlatformTypeCheckboxGroup({
				colspan:2,
				width: 320, 
				labelWidth:100
			})
		];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : baseinfo.platform.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.platform.isPermission('platform/platformQueryButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformQueryButton'),
			margin:'0 800 0 0',
			handler : function() {
				me.getForm().reset();
				me.getForm().findField('organizationCode').organizationCode = null;
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : baseinfo.platform.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.platform.isPermission('platform/platformQueryButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					if(Ext.isEmpty(me.getForm().findField('organizationCode').rawValue)){
						me.getForm().findField('organizationCode').organizationCode = null;
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
Ext.define('Foss.baseinfo.platform.PlatformGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.platform.i18n('foss.baseinfo.platformInformation'),//月台信息
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: baseinfo.platform.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
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
			this.platformAddWindow = Ext.create('Foss.baseinfo.platform.PlatformAddWindow');
			this.platformAddWindow.parent = this;//父元素
		}
		return this.platformAddWindow;
	},
	//修改月台WINDOW
	platformUpdateWindow:null,
	getPlatformUpdateWindow:function(){
		if (Ext.isEmpty(this.platformUpdateWindow)) {
			this.platformUpdateWindow = Ext.create('Foss.baseinfo.platform.PlatformUpdateWindow');
			this.platformUpdateWindow.parent = this;//父元素
		}
		return this.platformUpdateWindow;
	},
	//导入月台
	uploadPlatform:null,
	getUploadPlatform:function(){
		if (Ext.isEmpty(this.uploadPlatform)) {
			this.uploadPlatform = Ext.create('Foss.baseinfo.platform.UploadPlatform');
			this.uploadPlatform.parent = this;//父元素
		}
		return this.uploadPlatform;
	},
	//作废月台
	toVoidPlatform: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			baseinfo.showWoringMessage('请选择一条进行作废操作！');//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		baseinfo.showQuestionMes(baseinfo.platform.i18n('foss.baseinfo.wantSetAsideThesePlatforms'),function(e){//是否要作废这些月台？
			if(e=='yes'){//询问是否删除，是则发送请求
				var platformVirtualCodes = new Array();//月台
				for(var i = 0 ; i<selections.length ; i++){
					platformVirtualCodes.push(selections[i].get('virtualCode'));
				}
				var params = {'platformVo':{'platformVirtualCodes':platformVirtualCodes}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.requestTimeout'));//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deletePlatform.action');
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
			text : baseinfo.platform.i18n('foss.baseinfo.operate'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: baseinfo.platform.i18n('foss.baseinfo.update'),//修改
                disabled:!baseinfo.platform.isPermission('platform/platformUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var virtualCode= record.get('virtualCode');//站点组虚拟编码
    				var params = {'platformVo':{'platformEntity':{'virtualCode':virtualCode}}};
    				var successFun = function(json){
    					var updateWindow = me.getPlatformUpdateWindow();//获得修改窗口
    					if(Ext.isEmpty(json.platformVo.platformEntity)){
    						baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.returnDataEmpty'));//返回数据为空！
    						return ;
    					}
    					json.platformVo.platformEntity.width = json.platformVo.platformEntity.width/1000;
    					json.platformVo.platformEntity.height = json.platformVo.platformEntity.height/1000;
    					updateWindow.platformEntity = json.platformVo.platformEntity;//站点组
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.requestTimeout'));//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('searchPlatform.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: baseinfo.platform.i18n('foss.baseinfo.void'),//作废
                disabled:!baseinfo.platform.isPermission('platform/platformVoidButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		baseinfo.showQuestionMes(baseinfo.platform.i18n('foss.baseinfo.wantSetAsideThisPlatform'),function(e){//是否要作废这个月台？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var platformVirtualCodes = new Array();//月台
            				platformVirtualCodes.push(record.get('virtualCode'));
            				var params = {'platformVo':{'platformVirtualCodes':platformVirtualCodes}};
            				var successFun = function(json){
            					baseinfo.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.requestTimeout'));//请求超时
            					}else{
            						baseinfo.showErrorMes(json.message);
            					}
            				};
            				var url = baseinfo.realPath('deletePlatform.action');
            				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}]
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.fieldName'),//外场名称
			dataIndex : 'organizationName'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.fieldID'),//外场编号
			dataIndex : 'transferCode'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.platformNo'),//月台编号
			dataIndex : 'platformCode'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.liftingPlatform'),//升降台
			dataIndex : 'hasLift',
			renderer:function(value){
				if(value ==baseinfo.platform.yes){
					return '有';//有
				}else if(value ==baseinfo.platform.no){
					return '无';//无
				}else{
					return '';
				}
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.heightM'),//高度（米）
			dataIndex : 'height',
			renderer:function(value){
				return value/1000;
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.widthMeters'),//高度（米）
			dataIndex : 'width',
			renderer:function(value){
				return value/1000;
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.dockableModels'),//可停靠车型
			dataIndex : 'vehicleName'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.platformType'),//月台类型
			dataIndex : 'longDistance',
			renderer:function(value, meta, record, rowIndex, colIndex, store){
				var longDistance = (record.data.longDistance=='Y'?baseinfo.platform.i18n('foss.baseinfo.longDistance'):null);
				var shortDistance = (record.data.shortDistance=='Y'?baseinfo.platform.i18n('foss.baseinfo.shortDistance'):null);
				var pkp = (record.data.pkp=='Y'?baseinfo.platform.i18n('foss.baseinfo.pkp'):null);
				var array = [longDistance,shortDistance,pkp];
				for(var i = 0 ;i<array.length;i++){
					if(array[i] == null )
					{
						array.splice(i,1);
						i= i-1;
					}							  
				 }
				return array.toString();
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.abscissa'),//横坐标
			dataIndex : 'abscissa'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.ordinate'),//纵坐标 
			dataIndex : 'ordinate'
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.notes'),//备注
			dataIndex : 'notes'
		}];
		me.store = Ext.create('Foss.baseinfo.platform.PlatformStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPlatformForm();
					if(queryForm!=null){
						var vehicleCode = queryForm.getForm().findField('vehicleCode').getValue();
						var organizationCode = queryForm.getForm().findField('organizationCode').organizationCode;
						var platformCode = queryForm.getForm().findField('platformCode').getValue();
						var position = queryForm.getForm().findField('position').getValue();
						var longDistance = queryForm.getForm().findField('longDistance').getValue();
						var shortDistance = queryForm.getForm().findField('shortDistance').getValue();
						var pkp = queryForm.getForm().findField('pkp').getValue();
						
						Ext.apply(operation,{
							params : {//月台大查询，查询条件组织
								'platformVo.platformEntity.organizationCode':organizationCode,//外场CODE
								'platformVo.platformEntity.platformCode':platformCode,//月台编号
								'platformVo.platformEntity.position':position,//月台位置
								'platformVo.platformEntity.vehicleCode':vehicleCode,
								'platformVo.platformEntity.longDistance':(longDistance?'Y':null),
								'platformVo.platformEntity.shortDistance':(shortDistance?'Y':null),
								'platformVo.platformEntity.pkp':(pkp?'Y':null)
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
			text : baseinfo.platform.i18n('foss.baseinfo.add'),//新增
			disabled:!baseinfo.platform.isPermission('platform/platformAddButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformAddButton'),
			handler :function(){
				me.getPlatformAddWindow().show();
			} 
		},'-',{
			text : baseinfo.platform.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.platform.isPermission('platform/platformVoidButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformVoidButton'),
			handler :function(){
				me.toVoidPlatform();
			} 
		},'-',{
			text : baseinfo.platform.i18n('foss.baseinfo.import'),//导入
			disabled:!baseinfo.platform.isPermission('platform/platformImportButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformImportButton'),
			handler :function(){
				me.getUploadPlatform().show();
			} 
		},'-',{
			text : baseinfo.platform.i18n('foss.baseinfo.export'),//导出
			disabled:!baseinfo.platform.isPermission('platform/platformExportButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformExportButton'),
			handler :function(){
				//获取导出限制
				var store =me.getStore();
				baseinfo.showQuestionMes('是否要导出月台数据',function(e){
					if(e=='yes'){
						if(store.getCount()<1){
							baseinfo.showErrorMes('没有数据，无法导出！');
							return;
						}
						var queryForm = me.up().getQueryPlatformForm();
						var platformExport = '';
						var vehicleCode = queryForm.getForm().findField('vehicleCode').getValue();
						var organizationCode = queryForm.getForm().findField('organizationCode').organizationCode;
						var platformCode = queryForm.getForm().findField('platformCode').getValue();
						var position = queryForm.getForm().findField('position').getValue();
						var longDistance = queryForm.getForm().findField('longDistance').getValue();
						var shortDistance = queryForm.getForm().findField('shortDistance').getValue();
						var pkp = queryForm.getForm().findField('pkp').getValue();
						if(!Ext.isEmpty(organizationCode)){
							platformExport ='platformVo.platformEntity.organizationCode='+organizationCode;
						}
						if(!Ext.isEmpty(platformCode)){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.platformCode='+platformCode;
							}else{
								platformExport = 'platformVo.platformEntity.platformCode='+platformCode;
							}
						}
						if(!Ext.isEmpty(position)){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.position='+position;
							}else{
								platformExport = 'platformVo.platformEntity.position='+position;
							}
						}
						if(!Ext.isEmpty(vehicleCode)){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.vehicleCode='+vehicleCode;
							}else{
								platformExport = 'platformVo.platformEntity.vehicleCode='+vehicleCode;
							}
						}
						if((!Ext.isEmpty(longDistance))&&longDistance==true){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.longDistance=' + 'Y';
							}else{
								platformExport = 'platformVo.platformEntity.longDistance='+ 'Y';
							}
						}
						if((!Ext.isEmpty(shortDistance))&&shortDistance==true){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.shortDistance='+ 'Y';
							}else{
								platformExport = 'platformVo.platformEntity.shortDistance='+ 'Y';
							}
						}
						if((!Ext.isEmpty(pkp))&&pkp==true){
							if(!Ext.isEmpty(platformExport)){
								platformExport = platformExport+'&'+'platformVo.platformEntity.pkp='+ 'Y';
							}else{
								platformExport = 'platformVo.platformEntity.pkp='+ 'Y';
							}
						}
						var url = baseinfo.realPath('exportPlatform.action');
						if(!Ext.isEmpty(platformExport)){
							url = url+'?'+platformExport;
						}
						window.location=url;
						platformExport = '';
					}
				});
			} 
		},'-',{
			text : baseinfo.platform.i18n('foss.baseinfo.importTemplateDownload'),//导入模版下载
			disabled:!baseinfo.platform.isPermission('platform/platformImportTemplateDownloadButton'),
			hidden:!baseinfo.platform.isPermission('platform/platformImportTemplateDownloadButton'),
			handler :function(){
				var queryForm = me.up().getQueryPlatformForm();
				var platformExport = '';
					platformExport = 'platformVo.platformEntity.longDistance=' + 'D';
					platformExport = platformExport+'&'+'platformVo.platformEntity.shortDistance='+ 'D';
					platformExport = platformExport+'&'+'platformVo.platformEntity.pkp='+ 'D';
				var url = baseinfo.realPath('exportPlatform.action');
				if(!Ext.isEmpty(platformExport)){
					url = url+'?'+platformExport;
				}
				window.location=url;
				platformExport = '';
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});



//月台类型
Ext.define('Foss.baseinfo.platform.PlatformTypeCheckboxGroup',{  
	extend: 'Ext.form.CheckboxGroup',  
	name: 'platformType', 
	
	//allowBlank:false,
	align:'bottom',
	padding:'6 0',
	columns: 3,  //在上面定义的宽度上展示3列  
	fieldLabel: baseinfo.platform.i18n('foss.baseinfo.platformType'),
	items: [  
		{boxLabel: baseinfo.platform.i18n('foss.baseinfo.longDistance'), name: 'longDistance'},  //月台类型:长途
		{boxLabel: baseinfo.platform.i18n('foss.baseinfo.shortDistance'), name: 'shortDistance'},  //月台类型:短途
		{boxLabel: baseinfo.platform.i18n('foss.baseinfo.pkp'), name: 'pkp'},  //月台类型:接送货
	]  
});
//坐标
Ext.define('Foss.baseinfo.platform.PlatformCoordinateFieldSet',{
    extend : 'Ext.form.FieldSet',
	border: 0,
	defaults: {
		anchor: '100%',
		labelAlign :'left',
		labelWidth : 50,
		width:115,
		step:.01,
		allowBlank:false,
		maxValue: 999999,
		minValue: -999999,
		decimalPrecision:2,
		value: '0.00',
		allowDecimals : true,
		xtype : 'numberfield' ,
		hideEmptyLabel: true
	},
	layout: {
		type: 'hbox',
		align: 'top'
	},
	items: [{
		name: 'abscissa',//横坐标
		fieldLabel: baseinfo.platform.i18n('foss.baseinfo.abscissa')
		},{
		name: 'ordinate',//纵坐标
		fieldLabel: baseinfo.platform.i18n('foss.baseinfo.ordinate')
	}]
});



/**
 * @description 月台主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-platform_content')) {
		return;
	};
	var queryPlatformForm = Ext.create('Foss.baseinfo.platform.QueryPlatformForm');//查询FORM
	var platformGrid = Ext.create('Foss.baseinfo.platform.PlatformGrid');//查询结果GRID
	Ext.getCmp('T_baseinfo-platform').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-platform_content',
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
Ext.define('Foss.baseinfo.platform.PlatformAddWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.platform.i18n('foss.baseinfo.newPlatforms'),//新增月台
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.platform.PlatformGrid）
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :590,
	height :450,
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
    		this.platformForm = Ext.create('Foss.baseinfo.platform.PlatformForm',{
    			'isUpdate':false
    		});
    	}
    	return this.platformForm;
    },
    //提交月台数据
    commitPlatform:function(button){
    	var me = this;
    	if(me.getPlatformForm().getForm().isValid()){//校验form是否通过校验
    		var platformModel = new Foss.baseinfo.platform.PlatformEntity();
    		me.getPlatformForm().getForm().updateRecord(platformModel);//将FORM中数据设置到MODEL里面
    		platformModel.set('height',platformModel.get('height')*1000);
    		platformModel.set('width',platformModel.get('width')*1000);//后台一毫米计算
    		
    		/**
    		 * 读取checkbox中的月台类型	长途、短途、接送货
    		 * 	
    		 * 勾选为'Y'，不勾选为'N'
    		 */
    		
    		platformModel.set('longDistance',(platformModel.get('longDistance')=='true')?'Y':'N');
    		platformModel.set('shortDistance',(platformModel.get('shortDistance')=='true')?'Y':'N');
    		platformModel.set('pkp',(platformModel.get('pkp')=='true')?'Y':'N');
    
    		var params = {'platformVo':{'platformEntity':platformModel.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.requestTimeout'));//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('addPlatform.action');//请求月台新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :baseinfo.platform.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
					me.getPlatformForm().getForm().reset();
			} 
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.save'),//保存
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
Ext.define('Foss.baseinfo.platform.PlatformUpdateWindow',{
	extend : 'Ext.window.Window',
	title : baseinfo.platform.i18n('foss.baseinfo.modifyPlatform'),//修改月台
	closable : true,
	resizable:false,
	platformEntity:null,//修改月台数据
	parent:null,//父元素（弹出这个window的gird——Foss.baseinfo.platform.SiteGroupGrid）
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
			me.getPlatformForm().getForm().loadRecord(new Foss.baseinfo.platform.PlatformEntity(me.platformEntity));
			me.getPlatformForm().getForm().findField('organizationName').setCombValue(me.platformEntity.organizationName,me.platformEntity.transferCode);
			me.getPlatformForm().getForm().findField('organizationCode').setValue(me.platformEntity.organizationCode);
			if(!Ext.isEmpty(me.platformEntity.vehicleCode)){
				me.getPlatformForm().getForm().findField('vehicleCode').setCombValue(me.platformEntity.vehicleName,me.platformEntity.vehicleCode);
			}
			me.getPlatformForm().getForm().findField('platformType').setValue({
				'longDistance':me.platformEntity.longDistance=='Y'?true:false,
				'shortDistance':me.platformEntity.shortDistance=='Y'?true:false,
				'pkp':me.platformEntity.pkp=='Y'?true:false						
			});
		}
	},
	//修改月台FORM
	platformForm:null,
	getPlatformForm : function(){
    	if(Ext.isEmpty(this.platformForm)){
    		this.platformForm = Ext.create('Foss.baseinfo.platform.PlatformForm',{
    			'isUpdate':true
    		});
    		this.platformForm.getForm().findField('organizationName').setReadOnly(true);
    	}
    	return this.platformForm;
    },
    //修改月台
    commitPlatform:function(button){
    	var me = this;
    	if(me.getPlatformForm().getForm().isValid()){//校验form是否通过校验
    		var platformModel = new Foss.baseinfo.platform.PlatformEntity(me.platformEntity);
    		me.getPlatformForm().getForm().updateRecord(platformModel);//将FORM中数据设置到MODEL里面
    		platformModel.set('height',platformModel.get('height')*1000);
    		platformModel.set('width',platformModel.get('width')*1000);//后台一毫米计算

    		/**
    		 * 读取checkbox中的月台类型	长途、短途、接送货
    		 * 	
    		 * 勾选为'Y'，不勾选为'N'
    		 */
    		
    		platformModel.set('longDistance',(platformModel.get('longDistance')=='true')?'Y':'N');
    		platformModel.set('shortDistance',(platformModel.get('shortDistance')=='true')?'Y':'N');
    		platformModel.set('pkp',(platformModel.get('pkp')=='true')?'Y':'N');
    		var params = {'platformVo':{'platformEntity':platformModel.data}};//组织新增数据
    		var successFun = function(json){
    			button.setDisabled(false);
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				button.setDisabled(false);
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.requestTimeout'));//请求超时
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('updatePlatform.action');//请求月台新增
			button.setDisabled(true);
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :baseinfo.platform.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.reset'),//重置
			handler :function(){
					me.getPlatformForm().getForm().loadRecord(new Foss.baseinfo.platform.PlatformEntity(me.platformEntity));
					me.getPlatformForm().getForm().findField('organizationName').setCombValue(me.platformEntity.organizationName,me.platformEntity.transferCode);
			} 
		},{
			text : baseinfo.platform.i18n('foss.baseinfo.save'),//保存
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
Ext.define('Foss.baseinfo.platform.PlatformForm', {
	extend : 'Ext.form.Panel',
	title : baseinfo.platform.i18n('foss.baseinfo.platformInformation'),//月台信息
	frame: true,
	isUpdate:false,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	//width:200,
//    	colspan : 3
    },
    layout:{
		type:'table',
		columns: 2
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype : 'commontransfercenterselector',
			forceSelection : true,
			allowBlank:false,
			userCode:FossUserContext.getCurrentUserEmp().empCode,
			currentOrgCode:FossUserContext.getCurrentDeptCode(),
			name: 'organizationName',//外场名称
			fieldLabel : baseinfo.platform.i18n('foss.baseinfo.fieldName'),
	        listeners:{
	        	select:function(text,records,eops){
	        		me.getForm().findField('organizationCode').setValue(records[0].get('orgCode'));
	        		me.getForm().findField('transferCode').setValue(records[0].get('code'));
	        	}
	        }
		},{
			name: 'transferCode',//外场编号
			readOnly:true,
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.fieldID'),
	        xtype : 'textfield'
		},{
			name: 'organizationCode',//外场编号
			readOnly:true,
			hidden:true,
	        xtype : 'textfield'
		},{
			name: 'platformCode',//月台编号
			allowBlank:false,
			maxLength:4,
			regex:new RegExp('^\\d{0,5}$'),
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.platformNo'),
	        xtype : 'textfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'hasLift',
			 fieldLabel:baseinfo.platform.i18n('foss.baseinfo.liftingPlatform'),//升降台
			 items:[{
			 	 xtype:'radio',
			     boxLabel:'有',
			     name:'hasLift',
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:'无',
			     name:'hasLift',
			     inputValue:'N'
			     }]
		},{
			name: 'height',//高度（米）
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.heightM'),
	        allowBlank:false,
	        decimalPrecision:3,
	        maxValue: 999999.999,
	        minValue: 0.001,
	        value:1.3,
	        step:0.001,
	        xtype : 'numberfield'
		},{
			name: 'width',//宽度（米）
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.widthMeters'),
	        decimalPrecision:3,
	        step:0.001,
	        value:4.5,
	        allowBlank:false,
	        maxValue: 999999.999,
	        minValue: 0.001,
	        xtype : 'numberfield'
		},{
			name: 'vehicleCode',
			allowBlank:false,
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.dockableModels'),//可停靠车型
	        xtype : 'commonvehicletypeselector'
		},{
			name: 'position',//位置
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.location'),
	        xtype : 'textfield'
		},
			new Foss.baseinfo.platform.PlatformTypeCheckboxGroup({
				labelWidth : 80,
				width: 260,   
				allowBlank:false
			}),
			new Foss.baseinfo.platform.PlatformCoordinateFieldSet(),
		{
			name: 'notes',//备注
	        fieldLabel: baseinfo.platform.i18n('foss.baseinfo.airagencycompany.remark'),
	        colspan : 5,
	        maxLength:200,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
//上传附件弹出框
Ext.define('Foss.baseinfo.platform.UploadPlatform',{
	extend:'Ext.window.Window',
	title:baseinfo.platform.i18n('foss.baseinfo.importPlatform'),
	layout:{
		type:'vbox',
		align:'stretch'
	},
	width:400,
	height:150,
	closeAction:'hide',
	listeners:{
		beforehide:function(me){
			var form = me.down('form');
			form.getForm().findField('uploadFile').reset();
		}
	},
	parent:null,//（Foss.baseinfo.platform.PlatformGrid）
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [
		{
			xtype:'form',
			flex:1,
			layout:{
				type : 'hbox'
			},
			defaults : {
				margins : '0 5 0 0'
			},
			items:[{
				xtype:'filefield',
				name:'uploadFile',
				fieldLabel:baseinfo.platform.i18n('foss.baseinfo.pleaseSelectAttachments'),
				labelWidth:100,
				buttonText:baseinfo.platform.i18n('foss.baseinfo.browse'),
				flex:3
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:baseinfo.platform.i18n('foss.baseinfo.import'),
			xtype:'button',
			scope:me,
			handler:me.uploadFile
		},{
			text:baseinfo.platform.i18n('foss.baseinfo.cancel'),
			xtype:'button',
			handler:function(){
				me.close();
			}
		}];
	},
	//文件上传
	uploadFile:function(){
		var me = this;
		var successFn = function(json){
			if(Ext.isEmpty(json.platformVo.numList)){
				baseinfo.showInfoMes(baseinfo.platform.i18n('foss.baseinfo.allDataImportSuccess'));//全部数据导入成功！
				me.close();
				Ext.getCmp('T_baseinfo-platform_content').getPlatformGrid().getPagingToolbar().moveFirst();
			}else{
				var message = baseinfo.platform.i18n('foss.baseinfo.di');//第
				for(var i = 0;i<json.platformVo.numList.length;i++){
					message = message+json.platformVo.numList[i]+','
				}
				message = message+baseinfo.platform.i18n('foss.baseinfo.lineNoImport');
				baseinfo.showWoringMessage(message);
			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				baseinfo.showErrorMes(baseinfo.platform.i18n('foss.baseinfo.postAttachmentsIsNull'));//baseinfo.platform.i18n('foss.baseinfo.requestOvertime')
			}else{
				baseinfo.showErrorMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = baseinfo.realPath('importPlatform.action');
		form.submit({
            url: url,
            waitMsg: baseinfo.platform.i18n('foss.baseinfo.uploadYourAttachments'),
            success:function(form, action){
    			var result = action.result;
    			if(result.success){
    				successFn(result);
    			}else{
    				failureFn(result);
    			}
    		},
    		failure:function(form, action){
    			var result = action.result;
    			failureFn(result);
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);
    			failureFn(result);
    		}
        });
	}
});







