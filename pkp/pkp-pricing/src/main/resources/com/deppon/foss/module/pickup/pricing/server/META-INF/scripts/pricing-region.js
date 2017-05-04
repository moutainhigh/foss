//转换long类型为日期
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
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
//超链接点击部门时显示区域关联部门的WINDOW
pricing.showDept = function(regionId){
	var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid();
	var showDeptWindow = grid.getShowDeptWindow();
	showDeptWindow.regionId = regionId;
	showDeptWindow.show();
};

//超链接点击失效时显示失效时间设置WINDOW
//参数ID区域与部门关联的ID
pricing.showEndTimeWindow = function(id){
	var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid().getRegionUpdateWindow().getDeptEditGrid();
	var endTimeWindow = null;
	grid.getStore().each(function(record){
		if(id==record.get('id')){
			endTimeWindow = grid.getEndTimeWindow(record);
			endTimeWindow.selection = record;
		}
	});
	endTimeWindow.show();
};

//删除区域关联的部门（只在store中删除）新增是的方法
pricing.deleteDeptForAdd = function(includeOrgId){
	var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid();
	var editDeptGrid = grid.getRegionWindow().getDeptEditGrid();//得到新增弹出框的部门与区域关联表格ADD
	editDeptGrid.getStore().each(function(record){
		if(record.get('includeOrgId')==includeOrgId){//删除部门ID相同的部门数据
			editDeptGrid.getStore().remove(record);
		}
	});
};
//删除区域关联的部门（只在store中删除）修改式的方法
pricing.deleteDeptForUpdate = function(includeOrgId){
	var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid();
	var editDeptGrid = grid.getRegionUpdateWindow().getDeptEditGrid();//得到新增弹出框的部门与区域关联表格UPDATE
	editDeptGrid.getStore().each(function(record){
		if(record.get('includeOrgId')==includeOrgId){//删除部门ID相同的部门数据
			editDeptGrid.getStore().remove(record);
		}
	});
};
//时效区域
pricing.region.PRESCRIPTION_REGION = 'EFFECTIVE_REGION';
//价格区域
pricing.region.PRICING_REGION = 'PRICING_REGION';
//行政区域
pricing.region.ADMINISTRATIVE = 'ADMINISTRATIVE';
//网点
pricing.region.DEPT = 'DEPT';
//服务器明天时间
pricing.region.tomorrowTime = null;
//服务器今天天时间
pricing.region.currentDay = null;
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.region.currentDay = new Date(result.pricingValuationVo.nowTime);
			pricing.region.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
}
//--------------------------------------pricing----------------------------------------
/**
 * 区域与部门关联MODEL
 */
Ext.define('Foss.pricing.region.RegionDeptModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'priceRegionCode',// 区域编码
		type : 'string'
	},  {
		name : 'priceRegionName',//区域名称
		type : 'string'
	}, {
		name : 'priceRegionId',// 区域ID
		type : 'string'
	}, {
		name : 'includeOrgId',// 部门Id
		type : 'string'
	}, {
		name : 'regionNature',// 区域性质
		type : 'string'
	}, {
		name : 'beginTime',// 开始时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'includeOrgCode',// 部门标杆编码
		type : 'string'
	}, {
		name : 'includeOrgName',// 部门名称
		type : 'string'
	}, {
		name : 'cityCode',// 部门所属城市CODE
		type : 'string'
	}, {
		name : 'cityName',// 部门所属城市名称
		type : 'string'
	}, {
		name : 'nationCode',// 部门所属国家CODE
		type : 'string'
	}, {
		name : 'nationName',// 部门所属国家名称
		type : 'string'
	}, {
		name : 'proCode',// 部门所属省份CODE
		type : 'string'
	}, {
		name : 'proName',// 部门所属省份名称
		type : 'string'
	}, {
		name : 'countyCode',// 部门所属区县CODE
		type : 'string'
	}, {
		name : 'countyName',// 部门所属区县名称
		type : 'string'
	}, {
		name : 'status',// 状态
		type : 'string'
	}]
});
/**
 * 区域MODEL
 */
Ext.define('Foss.pricing.region.AreaModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
		name : 'modifyUserName',// 修改人姓名
		type : 'string'
	},{
		name : 'regionCode',// 区域编码
		type : 'string'
	},  {
		name : 'regionName',// 区域名称
		type : 'string'
	}, {
		name : 'regionType',// 区域类型
		type : 'string'
	}, {
		name : 'regionNature',// 区域性质
		type : 'string'
	}, {
		name : 'active',// 区域状态
		type : 'string'
	}, {
		name : 'beginTime',// 开始时间
		type: 'Date',
		defaultValue : null,
		convert:pricing.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		convert:pricing.changeLongToDate
	}, {
		name : 'description',// 描述
		type : 'string'
	}, {
		name : 'nationCode',// 国家编号
		type : 'string'
	},{
		name : 'nationName',// 国家名称
		type : 'string'
	}, {
		name : 'proCode',// 省份编号
		type : 'string'
	},{
		name : 'proName',// 省份编号
		type : 'string'
	}, {
		name : 'cityCode',// 市编号
		type : 'string'
	},{
		name : 'cityName',// 市名称
		type : 'string'
	}, {
		name : 'countyCode',// 区县编号
		type : 'string'
	},{
		name : 'countyName',// 区县名称
		type : 'string'
	}]
});
//------------------------------------model---------------------------------------------------
/**
 * 区域Store
 */
Ext.define('Foss.pricing.region.AreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.region.AreaModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../pricing/searchRegionByCondition.action",
		reader : {
			type : 'json',
			root : 'regionVo.regionEntityList',
			totalProperty : 'totalCount'
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
pricing.getStore = function(storeId,model,fields,data) {
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
 * 区域查询表单
 */
Ext.define('Foss.pricing.region.QueryRegionForm', {
	extend : 'Ext.form.Panel',
	title: pricing.region.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name: 'regionName',
	        fieldLabel: pricing.region.i18n('i18n.pricingRegion.regionName'),//区域名称
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'value',
		    value:'ALL',
		    valueField: 'key',
		    editable:false,
		    store:pricing.getStore('Foss.pricing.region.AreaStatusStore',null,['key','value']
		    ,[{'key':'N','value':pricing.region.i18n('i18n.pricingRegion.unActive')},{'key':'Y','value':pricing.region.i18n('i18n.pricingRegion.active')}
		    ,{'key':'ALL','value':pricing.region.i18n('i18n.pricingRegion.all')}]),
	        fieldLabel: pricing.region.i18n('i18n.pricingRegion.regionStatus'),//区域状态
	        xtype : 'combo'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.region.i18n('i18n.pricingRegion.search'),
				disabled: !pricing.region.isPermission('index/indexQueryButton'),
				hidden: !pricing.region.isPermission('index/indexQueryButton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid();
						grid.getPagingToolbar().moveFirst();
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 区域列表
 */
Ext.define('Foss.pricing.region.AreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.region.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.region.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	regionWindow : null,
	//得到弹出的显示窗口(新增)
	getRegionWindow : function(){
		if(Ext.isEmpty(this.regionWindow)){
			this.regionWindow = Ext.create('Foss.pricing.region.RegionWindow');
		}
		return this.regionWindow;
	},
	endTimeWindow : null,
	//激活时设置起始日期
	getEndTimeWindow : function(){
		if(Ext.isEmpty(this.endTimeWindow)){
			this.endTimeWindow = Ext.create('Foss.pricing.region.EndTimeWindow',{
				type:'BEGIN'
			});
		}
		return this.endTimeWindow;
	},
	//立即激活
	immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.region.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	
	//立即中止时设置起始日期
	immediatelyStopWindow:null,
	getImmediatelyStopWindow: function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.region.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	regionUpdateWindow : null,
	//得到弹出的显示窗口(修改)
	getRegionUpdateWindow : function(){
		if(Ext.isEmpty(this.regionUpdateWindow)){
			this.regionUpdateWindow = Ext.create('Foss.pricing.region.RegionUpdateWindow');
		}
		return this.regionUpdateWindow;
	},
	showDeptWindow : null,
	//得到弹出的显示窗口（显示区域关联的部门）
	getShowDeptWindow : function(){
		if(Ext.isEmpty(this.showDeptWindow)){
			this.showDeptWindow = Ext.create('Foss.pricing.region.PricingDepartmentWindow');
		}
		return this.showDeptWindow;
	},
    //新增区域	
	addArea : function(btn) {
		var me = this;
		me.getRegionWindow().show();
	},
	//激活区域	
	immediatelyActive: function(btn) {
		var me = this;
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.region.i18n('foss.pricing.selectOneRecordToOp'));
			return;
		}
		if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.region.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
		if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage(pricing.region.i18n('foss.pricing.selectOneUnActiveRecordToOp'));
	 		return;
	 	}else{
	 		var priceRegionEntity = selections[0].data;
			me.getImmediatelyActiveWindow().priceRegionEntity = priceRegionEntity;
			me.getImmediatelyActiveWindow().show();
	 	}
	},
	// 立即中止
	immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.region.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.region.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage(pricing.region.i18n('foss.pricing.selectOneActiveRecordToOp'));
	 		return;
	 	}else{
	 		var priceRegionEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().priceRegionEntity = priceRegionEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	//删除区域	
	deleteArea: function() {
		var me = this;
		var regionIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.region.i18n('foss.pricing.pleaseSelectUnActiveRegionDelete'));
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			//只有未被激活的区域的ID才会传到后台
			if(selections[i].get('active')=='N'){
				regionIds.push(selections[i].get('id'));
			}else{
				pricing.showErrorMes(pricing.region.i18n('foss.pricing.pleaseSelectUnActiveRegionDelete'));
				return;
			}
		}
		pricing.showQuestionMes(pricing.region.i18n('foss.pricing.isDeleteTheseUnActiveRegion'),function(e){//是否要删除这些未激活的区域？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'regionVo':{'regionIds':regionIds,'regionNature':pricing.region.PRICING_REGION}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.region.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteRegions.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
	    });
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.region.i18n('i18n.pricingRegion.num')//序号
		},{
			
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.region.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.region.i18n('foss.pricing.update'),//修改
                disabled: !pricing.region.isPermission('index/indexUpdateButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var id= record.get('id');//区域编码
    				var params = {'regionVo':{'regionEntity':{'id':id,'regionNature':pricing.region.PRICING_REGION}}};
    				var successFun = function(json){
    					var updateWindow = me.getRegionUpdateWindow();//获得修改窗口
    					updateWindow.selection = new Foss.pricing.region.AreaModel(json.regionVo.regionEntity);//区域
    					//updateWindow.getEditForm().isSearchComb = false;
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.region.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('searchRegionByID.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.region.i18n('foss.pricing.delete'),//删除
                disabled: !pricing.region.isPermission('index/indexDeleteQueryButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_delete';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var regionIds = new Array();
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
    				regionIds.push(record.get('id'));//区域ID
    				pricing.showQuestionMes(pricing.region.i18n('foss.pricing.isDeleteThisUnActiveRegion'),function(e){//是否要删除这个未激活的区域？
            			if(e=='yes'){//询问是否激活，是则发送请求
            				var params = {'regionVo':{'regionIds':regionIds,'regionNature':pricing.region.PRICING_REGION}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.region.i18n('foss.pricing.requestTimedOut'));//请求超时
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteRegions.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		});
                }
			}]
		},{
			text : pricing.region.i18n('i18n.pricingRegion.regionNum'),//区域编号
			dataIndex : 'regionCode'
		},{
			text : pricing.region.i18n('i18n.pricingRegion.regionName'),//区域名称
			dataIndex : 'regionName'
		},{
			text : pricing.region.i18n('i18n.pricingRegion.regionType'),//区域类型
			dataIndex : 'regionType',
			renderer:function(value,obj,record){
				if(!Ext.isEmpty(value)&&value==pricing.region.DEPT){//部门组织
					var regionId = "'"+record.get('id')+"'";
					//点击部门时触发的方法
					return '<a href="javascript:pricing.showDept('+regionId+');">'+pricing.region.i18n('foss.pricing.sectorOrganizations')+'</a>';
				}else if(!Ext.isEmpty(value)&&value==pricing.region.ADMINISTRATIVE){//行政组织
					return pricing.region.i18n('foss.pricing.administrativeOrganization');
				}else{
					return '';
				}
			}
		},{
			text : pricing.region.i18n('i18n.pricingRegion.lastCreateTime'),//最后修改时间
			dataIndex : 'modifyDate',
			width:140,
			renderer:function(value,obj,record){
				return new Date(value).format('yyyy-MM-dd hh:mm:ss');
			}
		},{
			text : '开始时间',
			dataIndex : 'beginTime',
			width:140,
			renderer:function(value,obj,record){
				return new Date(value).format('yyyy-MM-dd hh:mm:ss');
			}
		},{
			text : '结束时间',
			dataIndex : 'endTime',
			width:140,
			renderer:function(value,obj,record){
				return new Date(value).format('yyyy-MM-dd hh:mm:ss');
			}
		},{
			text : pricing.region.i18n('i18n.pricingRegion.lastCreateUser'),//最后修改人
			dataIndex : 'modifyUserName'
		},{
			text : pricing.region.i18n('i18n.pricingRegion.regionStatus'),//是否激活（区域状态）
			dataIndex : 'active',
			renderer:function(value){
				if(value=='Y'){
					return pricing.region.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){
					return  pricing.region.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.region.i18n('i18n.pricingRegion.regionNature'),//区域性质
			dataIndex : 'regionNature',
			renderer:function(value){
				if(value==pricing.region.PRESCRIPTION_REGION){//时效区域
					return pricing.region.i18n('i18n.pricingRegion.prescriptionArea');
				}else if(value==pricing.region.PRICING_REGION){//价格区域
					return  pricing.region.i18n('i18n.pricingRegion.pricingArea');
				}else{
					return '';
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.region.AreaStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-index_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'regionVo.regionEntity.regionName' : "%"+queryForm.getForm().findField('regionName').getValue()+"%",//区域名称
								'regionVo.regionEntity.active' : queryForm.getForm().findField('active').getValue(),//是否激活
								'regionVo.regionEntity.regionNature':pricing.region.PRICING_REGION
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
			text : pricing.region.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.region.isPermission('index/indexAddButton'),
			hidden: !pricing.region.isPermission('index/indexAddButton'),
			//iconcls:'deppon_icons_newOnBtn',
			handler :function(){
				me.addArea(this);
			} 
		},'-', {
			text : pricing.region.i18n('foss.pricing.immediatelyActivationProgram'),//立即激活
			disabled: !pricing.region.isPermission('index/indexImmediatelyActiveButton'),
			hidden: !pricing.region.isPermission('index/indexImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActive(this);
			} 
		},'-', {
			text : pricing.region.i18n('foss.pricing.immediatelyStopProgram'),//立即中止
			disabled: !pricing.region.isPermission('index/indexImmediatelyStopButton'),
			hidden: !pricing.region.isPermission('index/indexImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStop(this);
			} 
		},'-', {
			text : pricing.region.i18n('foss.pricing.delete'),//删除
			disabled: !pricing.region.isPermission('index/indexDeleteQueryButton'),
			hidden: !pricing.region.isPermission('index/indexDeleteQueryButton'),
			handler :function(){
				me.deleteArea();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * @description 区域管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-index_content')) {
		return;
	}
	pricing.haveServerNowTime();
	var queryForm = Ext.create('Foss.pricing.region.QueryRegionForm');//查询FORM
	var areaGrid = Ext.create('Foss.pricing.region.AreaGridPanel');//查询结果GRID
	Ext.getCmp('T_pricing-index').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-index_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return areaGrid;
		},
		items : [ queryForm, areaGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 区域管理-区域部门关联显示弹窗
 */
Ext.define('Foss.pricing.region.PricingDepartmentWindow',{
	extend : 'Ext.window.Window',
	title : pricing.region.i18n('i18n.pricingRegion.deptDetailed'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :500,
	height :400,
	deptShowGrid : null,//显示区域关联部门明细表
	regionId:null,//关联的区域ID
	listeners:{
		beforehide:function(me){
			me.getDeptShowGrid().getStore().removeAll();
		},
		beforeshow:function(me){//根据区域ID和区域性质，查询区域关联部门
			var params = {'regionVo':{priceRegioOrgnEntity:{'priceRegionId':me.regionId,'regionNature':pricing.region.PRICING_REGION}}};
			var successFun = function(json){
				me.getDeptShowGrid().getStore().add(json.regionVo.priceRegioOrgnEntityList);
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('searchRegionDept.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//得到区域关联部门的表格
    getDeptShowGrid : function(){
    	if(Ext.isEmpty(this.deptShowGrid)){
    		this.deptShowGrid = Ext.create('Foss.pricing.region.PricingDepartmentShowGridPanel');
    	}
    	return this.deptShowGrid;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getDeptShowGrid()];
		me.fbar = [{
			text : pricing.region.i18n('i18n.pricingRegion.returnGrid'),//返回列表，其实就是隐藏窗口
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 区域管理-区域部门关系显示-部门表格
 */
Ext.define('Foss.pricing.region.PricingDepartmentShowGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.region.i18n('i18n.pricingRegion.deptDetailed'),
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	columns : [{xtype: 'rownumberer',
		width:40,
		text : pricing.region.i18n('i18n.pricingRegion.num')//序号
	},{
		text : pricing.region.i18n('i18n.pricingRegion.nationProCityCounty'),//所属国家/省份/市/区-县
		dataIndex : 'priceRegionName',
		flex:2,
		renderer:function(value,obj,record){
			return record.get('nationName')+'/'+record.get('proName')+'/'+record.get('cityName')+'/'+record.get('countyName');
		}
	},{
		text : pricing.region.i18n('i18n.pricingRegion.deptName'),
		dataIndex : 'includeOrgName',
		flex:2
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = pricing.getStore('Foss.pricing.region.DepartmentShowGridStore','Foss.pricing.region.RegionDeptModel',null,[]);
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.callParent([cfg]);
		me.getView().on('render', function(view) {
		    view.tip = Ext.create('Ext.tip.ToolTip', {
		        target: view.el,
		        delegate: view.itemSelector,
		        trackMouse: true,
		        listeners: {
		            beforeshow: function updateTipBody(tip) {
		                tip.update(pricing.region.i18n('i18n.pricingRegion.deptName')//部门名称
		                		+': '+ view.getRecord(tip.triggerElement).get('includeOrgName')
		                		+pricing.region.i18n('foss.pricing.semicolon')
		                		+pricing.region.i18n('i18n.pricingRegion.nationProCityCounty')//所属国家/省份/市/区-县
		                		+': '
		                		+view.getRecord(tip.triggerElement).get('nationName')+'/'
		                		+view.getRecord(tip.triggerElement).get('proName')+'/'
		                		+view.getRecord(tip.triggerElement).get('cityName')+'/'
		                		+view.getRecord(tip.triggerElement).get('countyName'));//国家+省份+城市+区县显示不全，就用扩展
		            }
		        }
		    });
		});
	}
});
//------------------------------新增修改--------------------------------------------------------------------

/**
 * 区域管理-新增弹窗
 */
Ext.define('Foss.pricing.region.RegionWindow',{
	extend : 'Ext.window.Window',
	title : pricing.region.i18n('i18n.pricingRegion.addRegion'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :750,
	height :700,
	deptEditGrid : null,//显示区域关联部门明细表
	editForm:null,//区域新增修改的表单
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清楚上次使用的数据
			//me.getEditForm().isSearchComb = false;
			me.getDeptEditGrid().getStore().removeAll();
			me.getDeptEditGrid().getAddButton().setDisabled(true);
			me.getEditForm().getForm().reset();
		},
		beforeshow:function(me){
			//me.getEditForm().isSearchComb = true;
		}
	},
	//得到区域新增修改的表单
	getEditForm:function(){
		if(this.editForm==null){
    		this.editForm = Ext.create('Foss.pricing.region.RegionEditFormPanel');
    		this.editForm.items.items[5].items.items[2].allowBlank = true;//城市和区县是可以不填写的
    		this.editForm.items.items[5].items.items[3].allowBlank = true;
    	}
    	return this.editForm;
	},
	//得到区域关联部门的表格
    getDeptEditGrid : function(){
    	if(Ext.isEmpty(this.deptEditGrid)){
    		this.deptEditGrid = Ext.create('Foss.pricing.region.DepartmentGridPanel');
    		this.deptEditGrid.type = 'ADD';
    	}
    	return this.deptEditGrid;
    },
    //提交数据
    commintRegion:function(){
    	var me = this;
    	var form = me.getEditForm().getForm();
    	if(form.isValid()){//表单校验
    		var regionModel = new Foss.pricing.region.AreaModel()
        	form.updateRecord(regionModel);
    		if(regionModel.get('regionType') == pricing.region.ADMINISTRATIVE){//行政区域
    			var nationCode = me.getEditForm().items.items[5].items.items[0].getValue();
    			var proCode = me.getEditForm().items.items[5].items.items[1].getValue();
    			var cityCode = me.getEditForm().items.items[5].items.items[2].getValue();
    			var countyCode = me.getEditForm().items.items[5].items.items[3].getValue();
    			if(Ext.isEmpty(proCode)||Ext.isEmpty(nationCode)){
    				pricing.showWoringMessage(pricing.region.i18n('foss.pricing.countriesAndProvincesRequired'));//国家和省份必填！
					return;    				
    			}
    			regionModel.set('nationCode',nationCode);
    			regionModel.set('proCode',proCode);
    			regionModel.set('cityCode',cityCode);
    			regionModel.set('countyCode',countyCode);
    		}
        	var addPriceRegioOrgnEntityList = pricing.changeModelListToDataList(me.getDeptEditGrid().getStore().getNewRecords( ));
           	var regionEntity = regionModel.data;//得到model中的数据
        	var params = {'regionVo':{'regionEntity':regionEntity,'addPriceRegioOrgnEntityList':addPriceRegioOrgnEntityList}};
        	var successFun = function(json){
        		pricing.showInfoMes(json.message);//提示信息
        		me.close();//隐藏窗口
        		Ext.getCmp('T_pricing-index_content').getAreaGrid().getPagingToolbar().moveFirst();//重新加载数据
        		me.getEl().unmask();
        	};
        	var failureFun = function(json){
        		me.getEl().unmask();
        		if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
        	};
        	var url = pricing.realPath('addRegion.action');
        	me.getEl().mask();
        	pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    	
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDeptEditGrid()];//设置window的元素
		me.fbar = [{
			text :pricing.region.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.region.i18n('i18n.pricingRegion.commit'),//提交
			margin:'0 0 0 560',
			cls:'yellow_button',
			handler :function(){
				me.commintRegion();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 区域管理-修改弹窗
 */
Ext.define('Foss.pricing.region.RegionUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.region.i18n('i18n.pricingRegion.updateRegion'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :750,
	height :700,
	deptEditGrid : null,//显示区域关联部门明细表
	editForm:null,//区域新增修改的表单
	selection:null,//修改的一行的数据MODEL
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getDeptEditGrid().getStore().removeAll();//清除数据
			me.getDeptEditGrid().getAddButton().setDisabled(true);//设施“新增按钮”不可用
			me.getEditForm().getForm().reset();
		},
		beforeshow:function(me){
			if(Ext.isEmpty(me.selection)){//如果没有选择所要修改的数据，则提示选择
				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.notSelectOneToUpdate'));
				return;
			}
			var selection = me.selection;//得到要修改的选中的数据
			me.getEditForm().getForm().loadRecord(selection);//设置数据
			me.getEditForm().items.items[5].items.items[0].setRawValue(selection.get('nationName'));//修改时，只将国家-省-市-区县名称设置，因为是只用来展示
			me.getEditForm().items.items[5].items.items[1].setRawValue(selection.get('proName'));
			me.getEditForm().items.items[5].items.items[2].setRawValue(selection.get('cityName'));
			me.getEditForm().items.items[5].items.items[3].setRawValue(selection.get('countyName'));
			//判断区域类型
			if(!Ext.isEmpty(selection.get('regionType'))&&selection.get('regionType')==pricing.region.DEPT){//如果是类型是部门
				me.getDeptEditGrid().getAddButton().setDisabled(false);//新增按钮打开，查询该区域关联的部门
				var params = {'regionVo':{priceRegioOrgnEntity:{'priceRegionId':selection.get('id'),'regionNature':pricing.region.PRICING_REGION}}};
				var successFun = function(json){
					me.getDeptEditGrid().getStore().add(json.regionVo.priceRegioOrgnEntityList);
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('searchRegionDept.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
				//设置按钮可用
			}
		}
	},
	//得到区域新增修改的表单
	getEditForm:function(){
		if(this.editForm==null){
    		this.editForm = Ext.create('Foss.pricing.region.RegionEditFormPanel');
    		this.editForm.items.items[5].items.items[2].allowBlank = true;//城市和区县是可以不填写的
    		this.editForm.items.items[5].items.items[3].allowBlank = true;
    		this.editForm.items.items[2].maxLength = 50;
    	}
    	return this.editForm;
	},
	//得到区域关联部门的表格
    getDeptEditGrid : function(){
    	if(Ext.isEmpty(this.deptEditGrid)){
    		this.deptEditGrid = Ext.create('Foss.pricing.region.DepartmentGridPanel');
    		this.deptEditGrid.type = 'UPDATE';//设置表示是修改
    	}
    	return this.deptEditGrid;
    },
    //提交数据
    commintRegion:function(){
    	var me = this;
    	var form = me.getEditForm().getForm();//得到FROM表单
    	if(form.isValid()){//符合填写条件
    		var regionModel = new Foss.pricing.region.AreaModel();
    		regionModel.set('regionName',form.findField('regionName').getValue());//区域名称
    		regionModel.set('regionNature',pricing.region.PRICING_REGION);//区域性质
    		regionModel.set('regionCode',me.selection.get('regionCode'));//区域编号
    		regionModel.set('id',me.selection.get('id'));//区域ID
    		regionModel.set('active',me.selection.get('active'));//状态
    		regionModel.set('regionType',me.selection.get('regionType'));//区域类型
    		if(regionModel.get('active') == 'N'){
    			if(regionModel.get('regionType') == pricing.region.ADMINISTRATIVE){//行政区域
    				var nationCode = me.getEditForm().items.items[5].items.items[0].getValue();
	    			var proCode = me.getEditForm().items.items[5].items.items[1].getValue();
	    			var cityCode = me.getEditForm().items.items[5].items.items[2].getValue();
	    			var countyCode = me.getEditForm().items.items[5].items.items[3].getValue();
	    			regionModel.set('nationCode',nationCode);
	    			regionModel.set('proCode',proCode);
	    			regionModel.set('cityCode',cityCode);
	    			regionModel.set('countyCode',countyCode);
    			} // 已激活记录修改的时候不需要向后台传省市区县，只要显示就够了
    		}
        	var regionEntity = regionModel.data;
        	var addPriceRegioOrgnEntityList = pricing.changeModelListToDataList(me.getDeptEditGrid().getStore().getNewRecords( ));//新增的部门
        	var updatePriceRegioOrgnEntityList = pricing.changeModelListToDataList(me.getDeptEditGrid().getStore().getUpdatedRecords( ));//修改的关联部门
        	var params = {'regionVo':{'regionEntity':regionEntity,'addPriceRegioOrgnEntityList':addPriceRegioOrgnEntityList
        			,'updatePriceRegioOrgnEntityList':updatePriceRegioOrgnEntityList}};//组织数据
        	var successFun = function(json){
        		pricing.showInfoMes(json.message);
        		me.close();
        		Ext.getCmp('T_pricing-index_content').getAreaGrid().getPagingToolbar().moveFirst();
        	};
        	var failureFun = function(json){
        		if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
        	};
        	var url = pricing.realPath('updateRegion.action');
        	pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDeptEditGrid()];
		me.getEditForm().getForm().findField('regionCode').setReadOnly(true);//不能修改区域编号
		me.getEditForm().getForm().findField('active').setDisabled(true);//不能修是否激活
		//me.getEditForm().getForm().findField('nationCode').setReadOnly(true);//不能修是否省市区县
		/*me.getEditForm().items.items[5].items.items[0].setReadOnly(true);
		me.getEditForm().items.items[5].items.items[1].setReadOnly(true);
		me.getEditForm().items.items[5].items.items[2].setReadOnly(true);
		me.getEditForm().items.items[5].items.items[3].setReadOnly(true);
		me.getEditForm().items.items[5].items.items[0].clearListeners( );
		me.getEditForm().items.items[5].items.items[1].clearListeners( );
		me.getEditForm().items.items[5].items.items[2].clearListeners( );
		me.getEditForm().items.items[5].items.items[3].clearListeners( );*///@UPDATE
		me.getEditForm().getForm().findField('regionType').setReadOnly(true);//区域类型
		me.fbar = [{
			text :pricing.region.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.region.i18n('i18n.pricingRegion.commit'),//提交区域信息
			cls:'yellow_button',
			margin:'0 0 0 560',
			handler :function(){
				me.commintRegion();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 区域管理-新增-部门表格
 */
Ext.define('Foss.pricing.region.DepartmentGridPanel',{
	extend: 'Ext.grid.Panel',
	title : pricing.region.i18n('i18n.pricingRegion.deptInfo'),
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	addButton :null,//新增按钮（要根据状态控制）
	selType : "rowmodel", // 选择类型设置为：行选择
	departmentAddWindow:null,//新增部门弹窗
	endTimeWindow:null,//设置失效时间窗口
	type:'ADD',//UPDATE表示是修改的弹出框创建的这个GRID  ADD表示是新增的弹出框创建的这个GRID
	//得到设置失效时间传出的
	getEndTimeWindow:function(record){
		if(Ext.isEmpty(this.endTimeWindow)){
    		this.endTimeWindow = Ext.create('Foss.pricing.region.EndTimeWindow',{
    			type:'END',
    			selection:record
    		});
    	}
    	return this.endTimeWindow;
	},
    //得到新增部门
	getDepartmentAddWindow:function(){
		if(Ext.isEmpty(this.departmentAddWindow)){
    		this.departmentAddWindow = Ext.create('Foss.pricing.region.DepartmentAddWindow');
    	}
    	return this.departmentAddWindow;
	},
    //新增区域关联的部门（让window弹出）
	 orgWindow:null,
     getOrgWindow:function(){
    	var me = this;
    	//if(Ext.isEmpty(me.orgWindow)){
    		me.orgWindow = Ext.create('Foss.baseinfo.commonSelector.orgSelectorWindow',{
    			type:'ORG',
    			active:'Y',
    			modal:true,
    			commitFun:function(){
    				var selections = this.getGridRecord();
    				if(selections.length<1){
    					pricing.showWoringMessage(pricing.region.i18n('foss.pricing.pleaseSelectLeastOneData'));//请至少选择一条数据！
    					return;
    				}
    				var depts = new Array();
    				var grid = Ext.getCmp('T_pricing-index_content').getAreaGrid().getRegionWindow().getDeptEditGrid();//获取区域关联部门的GRID
    				var realySelections = new Array();//真的要新加的
    				for(var i=0;i<selections.length;i++){//去重
    					var isSelected = false;//是否已经选择
    					grid.getStore().each(function(record){
    						if(selections[i].get('code')==record.get('includeOrgCode')){
    							isSelected = true;
    							return;
    						}
        				});
    					if(!isSelected){
    						realySelections.push(selections[i]);
    					}
					}
    				var selection = me.up().selection;
    				for(var i=0;i<realySelections.length;i++){
    					if(Ext.isEmpty(selection)){//me.selection为空，则表示是新增，不需要设置关联的区域id/code/name
    						var data = {'includeOrgId':realySelections[i].get('id')
    			    				,'includeOrgCode':realySelections[i].get('code')
    			    				,'includeOrgName':realySelections[i].get('name')
    			    				,'cityCode':realySelections[i].get('cityCode')
    			    				,'cityName':realySelections[i].get('cityName')};
    						depts.push(data);
    					}else{//me.selection不为空，则表示是修改
    						var data = {'includeOrgId':realySelections[i].get('id')
    			    				,'includeOrgCode':realySelections[i].get('code')
    			    				,'includeOrgName':realySelections[i].get('name')
    			    				,'cityCode':realySelections[i].get('cityCode')
    			    				,'cityName':realySelections[i].get('cityName')
    			    				,'priceRegionCode':selection.get('regionCode')
    			    				,'priceRegionName':selection.get('regionName')
    			    				,'priceRegionId':selection.get('id')
    			    				,'regionNature':pricing.region.PRICING_REGION
    			    				};
    						depts.push(data);
    					}
    					
    				}
    				grid.getStore().add(depts);
    				this.close();
    			}
    		});
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().removeAt(0);//去掉全部以后，机场就成1了
    		me.orgWindow.items.items[0].getForm().findField('type').getStore().removeAt(1);
    		me.orgWindow.parent = this;
    	//}
    	return me.orgWindow;
    },
	//得到新增按钮(在之后要控制该按钮是否可用)
	getAddButton:function(){
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
			    text: pricing.region.i18n('i18n.pricingRegion.add'),//新增
			    disabled:true,
			    handler: function() {
			    	me.getOrgWindow().show();
			    }
			});
		}
		return me.addButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		if(me.type=='ADD'){//新增和修改获取两个完全不同的STORE
			me.store = pricing.getStore('Foss.pricing.region.DepartmentEditGridStoreForAdd','Foss.pricing.region.RegionDeptModel',null,[]);
		}else if(me.type=='UPDATE'){
			me.store = pricing.getStore('Foss.pricing.region.DepartmentEditGridStoreForUpdate','Foss.pricing.region.RegionDeptModel',null,[]);
		}
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.region.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.region.i18n('i18n.pricingRegion.deptStandCode'),//部门编号
			dataIndex : 'includeOrgCode',
			flex:3
		},{
			text : pricing.region.i18n('i18n.pricingRegion.deptName'),//部门名称
			dataIndex : 'includeOrgName'
		},{
			text : pricing.region.i18n('i18n.pricingRegion.belongCity'),//所属城市
			dataIndex : 'cityName'
		},{
			text : pricing.region.i18n('i18n.pricingRegion.effectiveStartTime'),//有效期是日期
			dataIndex : 'beginTime',
			renderer:function(value){
				if(Ext.isEmpty(value)){
					return new Date(pricing.region.currentDay).format('yyyy-MM-dd');//服务器时间的明天
				}else{
					return new Date(value).format('yyyy-MM-dd');
				}
			}
		},{
			text : pricing.region.i18n('i18n.pricingRegion.effectiveEndTime'),//有效截止日期
			dataIndex : 'endTime',
			renderer:function(value){
				if(Ext.isEmpty(value)){
					return '2999-12-31';
				}else{
					return new Date(value).format('yyyy-MM-dd');
				}
				
			}
		},{
			text : pricing.region.i18n('i18n.pricingRegion.opra'),//操作
			dataIndex : 'deptName',
			renderer:function(value,obj,record){
				if(Ext.isEmpty(record.get('id'))){
					var includeOrgId = "'"+record.get('includeOrgId')+"'";
					if(me.type=='ADD'){//ADD调用一个方法deleteDeptForAdd
						return '<a href="javascript:pricing.deleteDeptForAdd('+includeOrgId+');">删除</a>';
					}else if(me.type=='UPDATE'){//ADD调用一个方法deleteDeptForUpdate
						return '<a href="javascript:pricing.deleteDeptForUpdate('+includeOrgId+');">删除</a>';
					}
				}else{
					var id = "'"+record.get('id')+"'";
					return '<a href="javascript:pricing.showEndTimeWindow('+id+');">立即中止</a>';
				}
				
			}
		}];
		me.tbar = [me.getAddButton()];
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
 * 区域管理-新增/修改-FORM
 */
Ext.define('Foss.pricing.region.RegionEditFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.region.i18n('i18n.pricingRegion.regionInfoSet'),
	frame: true,
	height:280,
	collapsible: true,
	//isSearchComb:true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:60,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
	items :[{
		name: 'regionNature',//区域性质
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value:pricing.region.PRICING_REGION,
	    editable:false,
	    readOnly:true,
	    store:pricing.getStore('Foss.pricing.region.RegionNatureStore',null,['key','value'],[{'key':pricing.region.PRESCRIPTION_REGION,'value':pricing.region.i18n('i18n.pricingRegion.prescriptionArea')},{'key':pricing.region.PRICING_REGION,'value':pricing.region.i18n('i18n.pricingRegion.pricingArea')}]),
        fieldLabel: pricing.region.i18n('i18n.pricingRegion.regionNature'),
        xtype : 'combo'
	},{
		name: 'regionName',//区域名称
		allowBlank:false,
		maxLength : 150,
		regex: new RegExp('^[^\\s][^\\s]{0,}$'),
		regexText:pricing.region.i18n('foss.pricing.areaNameNotContainSpaceCharacter'),//区域名称不能包含空格符！
        fieldLabel: pricing.region.i18n('i18n.pricingRegion.regionName'),
        xtype : 'textfield'
	},{
		name: 'regionCode',//区域编号
		allowBlank:false,
		maxLength : 50,
		// 区域的编号不能出现重复信息、区域编号由字母加数字组成不能超过50个长度。
		regex: new RegExp('^[A-Za-z0-9]+$'),
		regexText: pricing.region.i18n('foss.pricing.regionCodeOnlyNumberLetter'),
        fieldLabel: pricing.region.i18n('i18n.pricingRegion.regionCode'),
        xtype : 'textfield'
	},{
		 xtype:'radiogroup',//是否激活
		 vertical:true,
		 allowBlank:false,
		 name:'active',
		 width:160,
		 fieldLabel:pricing.region.i18n('i18n.pricingRegion.isActive'),
		 items:[{
		 	 xtype:'radio',
		     boxLabel:pricing.region.i18n('i18n.pricingRegion.ye'),
		     name:'active',
		     inputValue:'Y'
	     },{
	    	 xtype:'radio',
		     boxLabel:pricing.region.i18n('i18n.pricingRegion.no'),
		     name:'active',
		     inputValue:'N'
		     }]
   	   },{
	   		name: 'regionType',//区域类型
			queryMode: 'local',
		    displayField: 'value',
		    valueField: 'key',
		    editable:false,
		    allowBlank:false,
		    value:pricing.region.ADMINISTRATIVE,
		    store:pricing.getStore(null,null,['key','value'],[{'key':pricing.region.DEPT,'value':pricing.region.i18n('foss.pricing.netWork')}
		    ,{'key':pricing.region.ADMINISTRATIVE,'value':pricing.region.i18n('foss.pricing.administrativeRegions')}]),
	        fieldLabel:pricing.region.i18n('i18n.pricingRegion.regionType'),//区域类型
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldValue){
	        		//if(comb.up('form').isSearchComb){
	        			if(newValue==pricing.region.DEPT){//部门组织，新增加部门按钮可以操作，省市区县不可选
		        			comb.up('form').up('window').getDeptEditGrid().getAddButton().setDisabled(false);
		        			comb.up('form').items.items[5].items.items[0].hide();
		        			comb.up('form').items.items[5].items.items[1].hide();
		        			comb.up('form').items.items[5].items.items[2].hide();
		        			comb.up('form').items.items[5].items.items[3].hide();
		        			comb.up('form').items.items[5].items.items[0].allowBlank = true;
		        			comb.up('form').items.items[5].items.items[1].allowBlank = true;
		        		}else if(newValue==pricing.region.ADMINISTRATIVE){//行政组织
		        			comb.up('form').up('window').getDeptEditGrid().getAddButton().setDisabled(true);
		        			comb.up('form').items.items[5].items.items[0].show();
		        			comb.up('form').items.items[5].items.items[1].show();
		        			comb.up('form').items.items[5].items.items[2].show();
		        			comb.up('form').items.items[5].items.items[3].show();
		        			comb.up('form').items.items[5].items.items[0].allowBlank = false;
		        			comb.up('form').items.items[5].items.items[1].allowBlank = false;
		        		}else{//当执行reset的时候会执行
		        			comb.up('form').up('window').getDeptEditGrid().getAddButton().setDisabled(true);
		        			comb.up('form').items.items[5].items.items[0].show();
		        			comb.up('form').items.items[5].items.items[1].show();
		        			comb.up('form').items.items[5].items.items[2].show();
		        			comb.up('form').items.items[5].items.items[3].show();
		        			comb.up('form').items.items[5].items.items[0].allowBlank = false;
		        			comb.up('form').items.items[5].items.items[1].allowBlank = false;
		        		}
	        		//}
	        	}
	        }
   	   },{
   		   xtype:'linkregincombselector',
   		   width:800,
   		   //hidden:true,
   		   allowBlank:false,
   		   fieldLabel:pricing.region.i18n('i18n.pricingRegion.belongAddress')//所在地
   	   }]
});

/**********************立即激活、中止 **********************************************

/**
 * 区域管理-修改弹窗-设置失效时间（新增这只起始日期）
 */
Ext.define('Foss.pricing.region.EndTimeWindow',{
	extend : 'Ext.window.Window',
	title: pricing.region.i18n('foss.pricing.immediatelySupendPricePriceScheme'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :380,
	height :150,
	endTimeField:null,//设置失效时间的datefield
	selection:null,//选择的当行的数据
	regionIds:[],
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	stopFormPanel : null,
	getStopFormPanel : function() {
		if (Ext.isEmpty(this.stopFormPanel)) {
			this.stopFormPanel = Ext.create('Foss.pricing.region.ImmediatelySuspendFormPanel');
		}
		return this.stopFormPanel;
	},
	listeners : {
		beforeshow : function(me) {
			var showbeginTime = Ext.Date.format(new Date(me.selection
					.get('beginTime')), 'Y-m-d');
			var showendTime = Ext.Date.format(new Date(me.selection
					.get('endTime')), 'Y-m-d');
			var value = pricing.region.i18n('foss.pricing.showleftTimeInfo')
					+ showbeginTime
					+ pricing.region.i18n('foss.pricing.showmiddleTimeInfo')
					+ showendTime
					+ pricing.region
							.i18n('foss.pricing.showrightEndTimeInfo');
			me.getStopFormPanel().down('displayfield').setValue(value);
		},
		beforehide : function(me) {
			me.getStopFormPanel().getForm().reset();
		}
	},
    constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getStopFormPanel() ];
		me.callParent(cfg);
	}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.region.ImmediatelySuspendFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	 //设置失效日期
	commintEndTime:function(){
		var me = this;
    	var form = this.getForm();
    	var endTime = Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s')
    	me.up('window').selection.set('endTime',endTime);//将选中修改失效日期的数据修改
    	me.up('window').close();
    },
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{ 
				fieldLabel :pricing.region.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_region_suspendEndTime_ID',
				dateConfig: {
					el : 'Foss_region_suspendEndTime_ID-inputEl'
				},
				allowBlank:false,
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				id : 'Foss_pricingRegion_conButton_ID',
				width:70,
				columnWidth:.15,
				text : pricing.region.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var form = this.up('form').getForm();
					var activeEndTime1 = form.getValues().endTime;
					var result = Ext.Date.parse(activeEndTime1,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
					if(result<0)
					{
						Ext.ux.Toast.msg(pricing.region.i18n('foss.pricing.promptMessage'),'中止时间不能小于当前时间','error', 3000);
						Ext.getCmp('Foss_pricingRegion_conButton_ID').setDisabled(false);
						return;
					}
					me.commintEndTime();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.region.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.region.ImmediatelyActiveTimeWindow',{
	extend: 'Ext.window.Window',
	title: pricing.region.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
	width:380,
	height:152,
	closeAction: 'hide' ,
	priceRegionEntity : null,
	immediatelyActiveFormPanel:null,
	getImmediatelyActiveFormPanel : function(){
    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.region.ImmediatelyActiveFormPanel');
    	}
    	return this.immediatelyActiveFormPanel;
    },
    listeners:{
    	beforeshow:function(me){
    		var showbeginTime = Ext.Date.format(new Date(me.priceRegionEntity.beginTime), 'Y-m-d');
    		var showendTime = 	Ext.Date.format(new Date(me.priceRegionEntity.endTime), 'Y-m-d');
    		var value = pricing.region.i18n('foss.pricing.showleftTimeInfo')
			  +showbeginTime+pricing.region.i18n('foss.pricing.showmiddleTimeInfo')
			  +showendTime+pricing.region.i18n('foss.pricing.showrightEndTimeInfo');
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
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.region.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var priceRegion = me.up('window').priceRegionEntity;
			var beginTime = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
			var params = {'regionVo':{'regionEntity':priceRegion,'regionNature':pricing.region.PRICING_REGION,'beginTime':beginTime}};
			var url = pricing.realPath('activeRegions.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.region.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_region_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_region_activeEndTime_ID-inputEl'
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
				text : pricing.region.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.region.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});


/**
 * 立即中止价格区域方案 Window
 */
Ext.define('Foss.pricing.region.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.region.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		parent:null,
		priceRegionEntity:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)) {
	    		this.stopFormPanel = Ext.create('Foss.pricing.region.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.priceRegionEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.priceRegionEntity.endTime), 'Y-m-d');
	    		var value = pricing.region.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.region.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.region.i18n('foss.pricing.showstopRightEndTimeInfo');
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
 * 立即中止价格区域功能FormPanel
 */
Ext.define('Foss.pricing.region.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var priceRegion = me.up('window').priceRegionEntity;
    		var endTime = Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s');
    		var params = {'regionVo':{'regionEntity':priceRegion,'regionNature':pricing.region.PRICING_REGION,'endTime':endTime}};
    		var url = pricing.realPath('immedietelyStopRegion.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.region.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel :pricing.region.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_region_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_region_stopEndTime_ID-inputEl'
				},
				allowBlank:false,
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				id : 'Foss_pricingRegion1_conButton_ID',
				width:70,
				columnWidth:.15,
				text : pricing.region.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					var form = this.up('form').getForm();
					var activeEndTime4 = form.getValues().endTime;
					var result = Ext.Date.parse(activeEndTime4,'Y-m-d H:i:s') - Ext.Date.parse(Ext.Date.format(new Date(),'Y-m-d H:i:s'),'Y-m-d H:i:s');
					if(result<0)
					{
						Ext.ux.Toast.msg(pricing.region.i18n('foss.pricing.promptMessage'),'中止时间不能小于当前时间','error', 3000);
						Ext.getCmp('Foss_pricingRegion1_conButton_ID').setDisabled(false);
						return;
					}
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.region.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});
