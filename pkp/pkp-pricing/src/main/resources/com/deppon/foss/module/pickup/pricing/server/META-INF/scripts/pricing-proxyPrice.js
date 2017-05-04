pricing.proxyPrice.Weight = 'WEIGHT';//计价方式明细-计费类别-按重量
pricing.proxyPrice.Volume = 'VOLUME';//计价方式明细-计费类别-按体积

//转换long类型为日期 
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

pricing.requestAndSetInfo = function(proxyRecord,windowShow){
	   var params = {'agentDeliveryFeeSchemeVo':{'id':proxyRecord.get('id')}};
	      var successFun = function(json){
	        var proxyPriceDetailList = json.agentDeliveryFeeSchemeVo.schemeEntity.feeEntityList;
	        if(proxyPriceDetailList!=null){
	               windowShow.getDownPanel().getStore().loadData(proxyPriceDetailList);
	        }else{
	        	pricing.showErrorMes(pricing.proxyPrice.i18n('foss.pricing.sectionAreaValueAddedServiceTheLackDetailedInformation'));
		          return;
	        }	       
	        windowShow.getEditForm().loadRecord(proxyRecord);	
	        windowShow.getEditForm().getForm().findField('agentDeptCode').setCombValue(proxyRecord.data.agentDeptName, proxyRecord.data.agentDeptCode);
	        windowShow.show();
	      };
	      var failureFun = function(json){
	      if(Ext.isEmpty(json)){
	        pricing.showErrorMes(pricing.proxyPrice.i18n('i18n.pricingRegion.requestTimeOut'));
	      }else{
	        pricing.showErrorMes(json.message);
	      }
	    };
	    var url = pricing.realPath('queryAgentDeliveryFeeSchemeById.action');
	    pricing.requestJsonAjax(url,params,successFun,failureFun);
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


//--------------------------------------pricing----------------------------------------
Ext.define('Foss.pricing.proxyPrice.proxyPriceAreaModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'schemeName',// 方案名称
		type : 'string'
	},{
		name : 'transportType',// 运输类型
		type : 'string'
	},  {
		name : 'agentDeptName',// 目的站
		type : 'string'
	}, {
		name : 'agentDeptCode',// 目的站编码
		type : 'string'
	}, {
		name : 'remark',// 方案描述
		type : 'string'
	}, {
		name : 'active',// 偏线代理送货费状态
		type : 'string'
	}]
});


Ext.define('Foss.pricing.proxyPrice.proxyPriceModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'startPoint',// 范围起点
		type : 'string'
	},{
		name : 'terminalPoint',// 范围终点
		type : 'string'
	},  {
		name : 'lowestPrice',// 最低一票
		type : 'string'
	}, {
		name : 'chargeStandard',// 收费标准
		type : 'string'
	}, {
		name : 'remark',// 方案描述
		type : 'string'
	}, {
		name : 'active',// 偏线代理送货费状态
		type : 'string'
	},{
        name : 'pricingManner',//计费类别
        type : 'string'
    }]
});

//代理送货费实体
Ext.define('Foss.pricing.proxyPrice.ProxyPriceDetailEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
    },{
        name : 'pricingManner',//计费类别
        type : 'string'
    },{
        name : 'chargeStandard',
        defaultValue : null//费率或者单价 或默认费率
    },{
        name : 'startPoint',
        defaultValue : null//范围起点
    },{
        name : 'terminalPoint',
        defaultValue : null//范围终点
    },{
        name : 'lowestPrice',//最低费用 或最低保险费
           defaultValue : null
    },{
        name : 'active',//数据状态
        type : 'string'
    }]
});


/**
 * 偏线代理送货费方案MODEL
 */
Ext.define('Foss.pricing.proxyPrice.ProxyModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'schemeName',// 方案名称
		type : 'string'
	},{
		name : 'transportType',// 基础产品
		type : 'string'
	},{
		name : 'agentDeptName',// 目的站
		type : 'string'
	},{
		name : 'agentDeptCode',// 目的站编号
		type : 'string'
	},{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    }, {
		name : 'modifyUser',// 修改人
		type : 'string'
	},{
		name : 'modifyUserName',// 修改人姓名
		type : 'string'
	},{
		name : 'remark',// 方案描述
		type : 'string'
	}]
});
//------------------------------------model---------------------------------------------------
/**
 * 偏线代理送货费方案Store
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.proxyPrice.ProxyModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url: pricing.realPath('queryAgentDeliveryFeeScheme.action'),
		reader : {
			type : 'json',
			root : 'agentDeliveryFeeSchemeVo.schemeEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//----------------------------------------store---------------------------------
/**
 * 偏线代理送货费方案查询form表单
 * 
 * 2014-07-17修改bug:重置不好用
 */
Ext.define('Foss.pricing.proxyPrice.QueryProxyPriceForm', {
	extend : 'Ext.form.Panel',
	  title: pricing.proxyPrice.i18n('i18n.pricingRegion.searchCondition'),
	  frame: true,
	  collapsible: true,
	    defaults : {
	      margin : '8 10 8 10',
	      labelWidth:80
	    },
	     layout: 'auto',
	  defaultType : 'textfield',
	  layout:'column',
	  items :[{
	        xtype : 'textfield',
			fieldLabel:pricing.proxyPrice.i18n('foss.pricing.scenarioName'),//方案名称
			name: 'schemeName',
	        maxLength : 60,
	        maxLengthText:pricing.proxyPrice.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
		    allowBlank:true,
		    columnWidth:.3
		    },{
	        xtype : 'commonvehagencydeptselector',
	        name:'agentDeptCode',
	        fieldLabel:pricing.proxyPrice.i18n('foss.pricing.destinationLine'),//目的站
	        columnWidth: 0.3
	        },{		
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				xtype : 'button', 
				colspan : 1,
		        width:80,
				text : pricing.proxyPrice.i18n('foss.pricing.reset'),//重置
				handler : function() {
					pricing.proxyPrice.queryform.getForm().reset();
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.95,
				html: '&nbsp;'
			},{
		        xtype : 'button',
		        width:80,
		        cls:'yellow_button',
		        text : pricing.proxyPrice.i18n('i18n.pricingRegion.search'),
		        disabled: !pricing.proxyPrice.isPermission('proxyPrice/proxyPriceQueryButton'),
		        hidden: !pricing.proxyPrice.isPermission('proxyPrice/proxyPriceQueryButton'),
		        handler : function() {
		        	var grid = Ext.getCmp('T_pricing-proxyPriceIndex_content').getProxyGrid();
		        	grid.getPagingToolbar().moveFirst();
		      }
		    }]
	  }]
});
/**
 * 偏线代理送货费方案列表GRID
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.proxyPrice.i18n('i18n.pricingRegion.searchResults'),//查询结果
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.proxyPrice.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	proxyWindow : null,
	//得到弹出的显示窗口(新增偏线代理送货费方案)
	getProxyWindow : function(){
		if(Ext.isEmpty(this.proxyWindow)){
			this.proxyWindow = Ext.create('Foss.pricing.proxyPrice.proxyPriceWindow');
		}
		return this.proxyWindow;
	},
	
	proxyUpdateWindow : null,
	//得到弹出的显示窗口(修改偏线代理送货费方案)
	getProxyUpdateWindow : function(){
		if(Ext.isEmpty(this.proxyUpdateWindow)){
			this.proxyUpdateWindow = Ext.create('Foss.pricing.proxyPrice.proxyPriceUpdateWindow');
		}
		return this.proxyUpdateWindow;
	},
	
	//查看偏线代理送货费方案详情WINDOW
	proxyBasicInfoWindow:null,
	getProxyBasicInfoWindow:function(){
		if (Ext.isEmpty(this.proxyBasicInfoWindow)) {
			this.proxyBasicInfoWindow = Ext.create('Foss.pricing.proxyPrice.proxyPriceShowWindow');
		}
		return this.proxyBasicInfoWindow;
	},
	
    //新增偏线代理送货费	
	addProxyPrice : function(btn) {
		var me = this;
		me.getProxyWindow().show();
	},
	//删除偏线代理送货费	
	deleteProxyPrice: function() {
		var me = this;
		var idList = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.proxyPrice.i18n('foss.pricing.pleaseSelectUnActiveRegionDelete'));
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){					 
			idList.push(selections[i].get('id'));			
		}
		pricing.showQuestionMes(pricing.proxyPrice.i18n('foss.pricing.isDeleteTheseProxyPriceScheme'),function(e){//是否要删除这些偏线代理送货费方案？
			if(e=='yes'){
				var params = {'agentDeliveryFeeSchemeVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(pricing.proxyPrice.i18n('foss.pricing.deleteSuccess'));
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.proxyPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteAgentDeliveryFeeScheme.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
	    });
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.num')//序号
		},{
			
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.proxyPrice.i18n('foss.pricing.update'),//修改
                disabled: !pricing.proxyPrice.isPermission('indexExpress/indexUpdateButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var id= record.get('id');//偏线代理送货费编码
    				var params = {'agentDeliveryFeeSchemeVo':{'id':id}};
    				var successFun = function(json){
    					var updateWindow = me.getProxyUpdateWindow();//获得修改窗口
    					updateWindow.selection = new Foss.pricing.proxyPrice.ProxyModel(json.agentDeliveryFeeSchemeVo.schemeEntity);//偏线代理送货费
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.proxyPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryAgentDeliveryFeeSchemeById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.proxyPrice.i18n('foss.pricing.delete'),//删除
                disabled: !pricing.proxyPrice.isPermission('regionValueAdd/indexDeleteQueryButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var idList = new Array();
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
    				idList.push(record.get('id'));//偏线代理送货费方案ID
    				pricing.showQuestionMes(pricing.proxyPrice.i18n('foss.pricing.isDeleteThisProxyPriceScheme'),function(e){//是否要删除这个偏线代理送货费方案？
            			if(e=='yes'){//询问是否激活，是则发送请求
            				var params = {'agentDeliveryFeeSchemeVo':{'idList':idList}};
            				var successFun = function(json){
            					pricing.showInfoMes(pricing.proxyPrice.i18n('foss.pricing.deleteSuccess'));
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.proxyPrice.i18n('foss.pricing.requestTimedOut'));//请求超时
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteAgentDeliveryFeeScheme.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		});
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.proxyPrice.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.proxyPrice.isPermission('indexValuation/indexValuationQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {                	
                	var grid = grid.up();
                	var proxyRecord = grid.getStore().getAt(rowIndex);;//选中送货费方案数据
                	var windowShow =  grid.getProxyBasicInfoWindow();
                	pricing.requestAndSetInfo(proxyRecord,windowShow);
                	
                }
			}]
		},{
			hide:true,
			text: 'ID',
			hidden: true,
	        dataIndex: 'ID'
		},{
			width: 160,
			text: pricing.proxyPrice.i18n('foss.pricing.scenarioName'),//方案名称
	        dataIndex: 'schemeName'
		},{
			width: 160,
			text: pricing.proxyPrice.i18n('foss.pricing.basicProducts'),
	        dataIndex: 'transportType'
		},{
			width: 160,
			hidden: true,
			text: pricing.proxyPrice.i18n('foss.pricing.destinationNumber'),
	        dataIndex: 'agentDeptCode'
		},{
			width: 160,
			text: pricing.proxyPrice.i18n('foss.pricing.destinationStation'),
	        dataIndex: 'agentDeptName'
		},{
			text: pricing.proxyPrice.i18n('foss.pricing.modificationTime'),
			width: 180,
	        dataIndex: 'modifyDate',
	        renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:m:s');
			}
		},{
			text: pricing.proxyPrice.i18n('foss.pricing.modifyUser'),
			width: 180,
	        dataIndex: 'modifyUserName'
		}];
		me.store = Ext.create('Foss.pricing.proxyPrice.proxyPriceStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-proxyPriceIndex_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'agentDeliveryFeeSchemeVo.schemeEntity.schemeName' : queryForm.getForm().findField('schemeName').getValue(),//方案名称
								'agentDeliveryFeeSchemeVo.schemeEntity.agentDeptCode' : queryForm.getForm().findField('agentDeptCode').getValue()//目的站					
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
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.proxyPrice.isPermission('indexExpress/indexAddButton'),
			hidden: !pricing.proxyPrice.isPermission('indexExpress/indexAddButton'),
			handler :function(){
				me.addProxyPrice(this);
			} 
		},'-', {
			text : pricing.proxyPrice.i18n('foss.pricing.delete'),//删除
			disabled: !pricing.proxyPrice.isPermission('indexExpress/indexDeleteQueryButton'),
			hidden: !pricing.proxyPrice.isPermission('indexExpress/indexDeleteQueryButton'),
			handler :function(){
				me.deleteProxyPrice();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});


/**
 * 偏线代理送货费管理-新增弹窗
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceWindow',{
	extend : 'Ext.window.Window',
	title : pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.add'),//新增偏线代理送货费方案
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :600,
	height :700,
	proxyEditGrid : null,//显示偏线代理送货费关联部门明细表
	editForm:null,//偏线代理送货费新增修改的表单
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
		me.getProxyEditGrid().getStore().removeAll();	
			me.getEditForm().getForm().reset();
		},
		beforeshow:function(me){
		}
	},
	//得到偏线代理送货费新增修改的表单
	getEditForm:function(){
		if(this.editForm==null){
    		this.editForm = Ext.create('Foss.pricing.proxyPrice.proxyPriceEditFormPanel');
    	}
    	return this.editForm;
	},
	//得到偏线代理送货费实体Grid
    getProxyEditGrid : function(){
    	if(Ext.isEmpty(this.proxyEditGrid)){
    		this.proxyEditGrid = Ext.create('Foss.pricing.ProxyPrice.ProxyPricePlanDetailGridPanel');
    		this.proxyEditGrid.type = 'ADD';
    	}
    	return this.proxyEditGrid;
    },
    //提交数据
    commitProxy:function(){
    	var me = this;
    	var form = me.getEditForm().getForm();
    	if(form.isValid()){//表单校验
    		var proxyModel = new Foss.pricing.proxyPrice.proxyPriceAreaModel()
        	form.updateRecord(proxyModel);
        	var feeEntityList = pricing.changeModelListToDataList(me.getProxyEditGrid().getStore().getNewRecords( ));      	
           	var schemeEntity = proxyModel.data;//得到model中的数据
//           	for(var i=0;i<feeEntityList.length;i++){
//           		//起点
//           		var startPoint=feeEntityList[i].startPoint;
//           		//终点
//           		var terminalPoint =feeEntityList[i].terminalPoint;
//           		if(startPoint>=terminalPoint){
//           		  pricing.showErrorMes('起点不能大于等于终点');
//  		          return;
//           		}
//           		
//           	}
           	schemeEntity.feeEntityList=feeEntityList;
           	params = {
					'agentDeliveryFeeSchemeVo' :{'schemeEntity': schemeEntity
					}
				};
        	var successFun = function(json){
        		pricing.showInfoMes(pricing.proxyPrice.i18n('foss.pricing.saveSuccess'));//保存成功
        		me.close();//隐藏窗口
        		Ext.getCmp('T_pricing-proxyPriceIndex_content').getProxyGrid().getPagingToolbar().moveFirst();//重新加载数据
        		me.getEl().unmask();
        	};
        	var failureFun = function(json){
        		me.getEl().unmask();
        		if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.proxyPrice.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
        	};
        	var url = pricing.realPath('addAgentDeliveryFeeScheme.action');
        	me.getEl().mask();
        	pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    	
    },

    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getProxyEditGrid()];//设置window的元素
		me.fbar = [{
			text :pricing.proxyPrice.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			cls:'yellow_button',		
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.commit'),//提交
			cls:'yellow_button',
			margin:'0 0 0 380',
			handler :function(){
				me.commitProxy();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 偏线代理送货费管理-修改弹窗
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.update'),//修改偏线代理送货费方案
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :750,
	height :700,
	proxyEditGrid : null,//显示偏线代理送货费实体Grid
	editForm:null,//偏线代理送货费新增修改的表单
	selection:null,//修改的一行的数据MODEL
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getProxyEditGrid().getStore().removeAll();//清除数据
			me.getEditForm().getForm().reset();
		},
		beforeshow:function(me){
			if(Ext.isEmpty(me.selection)){//如果没有选择所要修改的数据，则提示选择
				pricing.showErrorMes(pricing.proxyPrice.i18n('i18n.pricingRegion.notSelectOneToUpdate'));
				return;
			}
			var selection = me.selection;//得到要修改的选中的数据
			var id= selection.data.id;//偏线代理送货费编码
			me.getEditForm().getForm().loadRecord(selection);//设置数据  
			me.getEditForm().getForm().findField('agentDeptCode').setCombValue(selection.data.agentDeptName, selection.data.agentDeptCode);
				var params = {'agentDeliveryFeeSchemeVo':{'id':id}};
				var successFun = function(json){
					me.getProxyEditGrid().getStore().add(json.agentDeliveryFeeSchemeVo.schemeEntity.feeEntityList);
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.proxyPrice.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('queryAgentDeliveryFeeSchemeById.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
				//设置按钮可用
			
		}
	},
	//得到偏线代理送货费新增修改的表单
	getEditForm:function(){
		if(this.editForm==null){
    		this.editForm = Ext.create('Foss.pricing.proxyPrice.proxyPriceEditFormPanel');
    	}
    	return this.editForm;
	},
	//得到偏线代理送货费实体Grid
    getProxyEditGrid : function(){
    	if(Ext.isEmpty(this.proxyEditGrid)){
    		this.proxyEditGrid = Ext.create('Foss.pricing.ProxyPrice.ProxyPricePlanDetailGridPanel');
    		this.proxyEditGrid.type = 'UPDATE';//设置表示是修改
    	}
    	return this.proxyEditGrid;
    },
  //提交数据
    commitProxy:function(){
    	var me = this;
    	var form = me.getEditForm().getForm();    	
    	if(form.isValid()){//表单校验
    		var proxyModel = new Foss.pricing.proxyPrice.proxyPriceAreaModel()
        	form.updateRecord(proxyModel);
    		var id=me.selection.data.id;
        	var feeEntityList = pricing.changeModelListToDataList(me.getProxyEditGrid().getStore().getNewRecords( ));      	
           	var schemeEntity = proxyModel.data;//得到model中的数据
//           	for(var i=0;i<feeEntityList.length;i++){
//           		//起点
//           		var startPoint=feeEntityList[i].startPoint;
//           		//终点
//           		var terminalPoint =feeEntityList[i].terminalPoint;
//           		if(startPoint>=terminalPoint){
//           		  pricing.showErrorMes('起点不能大于等于终点');
//  		          return;
//           		}
//           		
//           	}
           	schemeEntity.feeEntityList=feeEntityList;
           	schemeEntity.id=id;
           	params = {
					'agentDeliveryFeeSchemeVo' :{'schemeEntity': schemeEntity
					}
				};
        	var successFun = function(json){
        		pricing.showInfoMes(pricing.proxyPrice.i18n('foss.pricing.saveSuccess'));//保存成功
        		me.close();//隐藏窗口
        		Ext.getCmp('T_pricing-proxyPriceIndex_content').getProxyGrid().getPagingToolbar().moveFirst();//重新加载数据
        		me.getEl().unmask();
        	};
        	var failureFun = function(json){
        		me.getEl().unmask();
        		if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.proxyPrice.i18n('i18n.pricingRegion.requestTimeOut'));
				}else{
					pricing.showErrorMes(json.message);
				}
        	};
        	var url = pricing.realPath('updateAgentDeliveryFeeScheme.action');
        	me.getEl().mask();
        	pricing.requestJsonAjax(url,params,successFun,failureFun);
    	}
    	
    },

    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getProxyEditGrid()];
		me.fbar = [{
			text :pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.returnGrid'),//返回列表	
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.commit'),//提交偏线代理送货费信息
			cls:'yellow_button',
			margin:'0 0 0 560',
			handler :function(){
				me.commitProxy();
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 偏线代理送货费-查看详情
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.details'),//偏线代理送货费详情
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :680,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){			
			me.getDownPanel().getStore().removeAll();
			me.getEditForm().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	editForm:null,//偏线代理送货费新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.proxyPrice.proxyPriceShowFormPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
    	return this.editForm;
	},
	downPanel:null,//偏线代理送货费新增修改查看表单（下半部分）
	//下面PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.ProxyPrice.ProxyPricePlanDetailGridPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
    	return this.downPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.proxyPrice.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 偏线代理送货费实体Grid
 */

Ext.define('Foss.pricing.ProxyPrice.ProxyPricePlanDetailGridPanel',{
	extend: 'Ext.grid.Panel',
	frame: true,
	flex:1,
	autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	type:'ADD',//UPDATE表示是修改的弹出框创建的这个GRID  ADD表示是新增的弹出框创建的这个GRID
    
    //新增重量一条默认值
    addWeightOneModel:function(){
      var me = this;
        var record = Ext.create('Foss.pricing.proxyPrice.ProxyPriceDetailEntity', {
        	startPoint:0,
        	terminalPoint:0,
        	lowestPrice:1,
            chargeStandard:0.01,
            pricingManner:pricing.proxyPrice.Weight
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //新增体积一条默认值
    addVolumeOneModel:function(){
      var me = this;
        var record = Ext.create('Foss.pricing.proxyPrice.ProxyPriceDetailEntity', {
        	startPoint:0,
        	terminalPoint:0,
        	lowestPrice:1,
            chargeStandard:0.01,
          pricingManner:pricing.proxyPrice.Volume
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
      if(Ext.isEmpty(this.plugins)){
        if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
          var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
                clicksToEdit: 1
            });
          this.plugins = [cellEditing];
        }else{
          this.plugins = [];
        }
      }
      return this.plugins;
    },
    
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
      var me = this;
      if(Ext.isEmpty(this.tbar)){
        if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
          this.tbar = [{
                text: pricing.proxyPrice.i18n('foss.pricing.inAccordanceWithTheWeightOfTheNew'),//按照重量新增
                handler : function(){
                  me.addWeightOneModel();
                }
            },{
                text: pricing.proxyPrice.i18n('foss.pricing.inAccordanceWithTheVolemeOfTheNew'),//按照体积新增
                handler : function(){
                  me.addVolumeOneModel();
                }
            }];//设置新增按按钮
        }
      }
      return this.tbar;
    },
	
	constructor : function(config) {
	    var me = this, 
	      cfg = Ext.apply({}, config);  
	    var isReadOnly = false;//默认非只读
	    if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
	      isReadOnly = config.isReadOnly;
	    }
	    
	    if(me.type=='ADD'){//新增和修改获取两个完全不同的STORE
			me.store = pricing.getStore('Foss.pricing.priceRegionExpress.DepartmentEditGridStoreForAdd','Foss.pricing.proxyPrice.proxyPriceModel',null,[]);
		}else if(me.type=='UPDATE'){
			me.store = pricing.getStore('Foss.pricing.priceRegionExpress.DepartmentEditGridStoreForUpdate','Foss.pricing.proxyPrice.proxyPriceModel',null,[]);
		}
	    
	    me.columns = [{
	        text : pricing.proxyPrice.i18n('i18n.pricingRegion.opra'),//操作
	        xtype:'actioncolumn',
	        align: 'center',
	        width:80,
	        items: [{
	            iconCls: 'deppon_icons_delete',
	                    tooltip: pricing.proxyPrice.i18n('foss.pricing.delete'),//删除
	            width:42,
				getClass : function (v, m, r, rowIndex) {
					if (!isReadOnly) {
						return 'deppon_icons_delete';
					} else {
						return 'statementBill_hide';
					}
				},
	            handler: function(grid, rowIndex, colIndex) {
	                    //获取选中的数据
	                var record=grid.getStore().getAt(rowIndex);
	                    pricing.showQuestionMes(pricing.proxyPrice.i18n('foss.pricing.wantDeleteThisDeliveryCharge'),function(e){//是否要删除这条送货费信息？
	                      if(e=='yes'){//询问是否删除，是则发送请求
	                        grid.getStore().remove(record);
	                      }
	                    });
	                    }
	          }]
	        },{
	            header: pricing.proxyPrice.i18n('foss.pricing.rangeStart'),//范围（起点）
	            dataIndex: 'startPoint',
	            flex: 1,
	            renderer:function(value,obj,record){
	                if(record.get('pricingManner')==pricing.proxyPrice.Weight){
	                return value+'kg';
	              }else if(record.get('pricingManner')==pricing.proxyPrice.Volume){
	                return value+'m³';
	              }else{
	                return value;
	              }
	            },
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                step:0.001,
	                decimalPrecision:3,
	                minValue: 0,
	                maxValue: 9999999999.999
	            }
	            
	        }, {
	            header: pricing.proxyPrice.i18n('foss.pricing.rangeEnd'),//范围（终点）
	            dataIndex: 'terminalPoint',
	            flex: 1,
	            renderer:function(value,obj,record){
	              if(record.get('pricingManner')==pricing.proxyPrice.Weight){
	                if(value==pricing.proxyPrice.CRITERIA_DETAIL_WEIGHT_MAX){
	                    return pricing.proxyPrice.i18n('foss.pricing.noUpperLimit');//无上限
	                  }
	              }else if(record.get('pricingManner')==pricing.proxyPrice.Volume){
	                if(value==pricing.proxyPrice.CRITERIA_DETAIL_VOLUME_MAX){
	                    return pricing.proxyPrice.i18n('foss.pricing.noUpperLimit');//无上限
	                  }
	              }
	              if(record.get('pricingManner')==pricing.proxyPrice.Weight){
	                return value+'kg';
	              }else if(record.get('pricingManner')==pricing.proxyPrice.Volume){
	                return value+'m³';
	              }else{
	                return value;
	              }
	            },
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                step:0.001,
	                decimalPrecision:3,
	                minValue: 0,
	                maxValue: 9999999999.999
	            }
	        }, {
	            header: pricing.proxyPrice.i18n('foss.pricing.theLowestVotes'),//最低一票
	            dataIndex: 'lowestPrice',
	            width: 70,
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                decimalPrecision:0,
	                minValue: 0,
	                maxValue: 99999999
	            }
	        }, {
	            header: pricing.proxyPrice.i18n('foss.pricing.charges'),//收费标准pricingManner
	            dataIndex: 'chargeStandard',
	            width: 70,
	            renderer:function(value,obj,record){
	              if(record.get('pricingManner')==pricing.proxyPrice.Weight){
	                return value+pricing.proxyPrice.i18n('foss.pricing.yuankg');
	              }else if(record.get('pricingManner')==pricing.proxyPrice.Volume){
	                return value+pricing.proxyPrice.i18n('foss.pricing.yuamm');
	              }else{
	                return value;
	              }
	            },
	            editor: {
	                xtype: 'numberfield',
	                allowBlank: false,
	                decimalPrecision:3,
	                minValue: 0,
	                maxValue: 99999999.999
	            }
	        }];
	    me.plugins=me.getPlugins(isReadOnly);
	    me.getTbar(isReadOnly);//获取新增按钮
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
 * 偏线代理送货费管理FORM
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:280,
	collapsible: true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	labelWidth:60,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
	items :[{
		name: 'schemeName',//方案名称
		allowBlank:false,
		editable:false,
        fieldLabel: pricing.proxyPrice.i18n('foss.pricing.scenarioName'),
        maxLength : 20,
        xtype : 'textfield'
	},{
		name: 'transportType',//偏线代理送货费性质
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value:pricing.proxyPrice.i18n('foss.pricing.carOuterWay'),
	    editable:false,
	    readOnly:true,
        fieldLabel: pricing.proxyPrice.i18n('i18n.pricingRegion.regionNature'),
        xtype : 'combo'
	},{
	    xtype : 'commonvehagencydeptselector',
	    name:'agentDeptCode',
	    editable:true,
	    fieldLabel:pricing.proxyPrice.i18n('foss.pricing.destinationLine'),//目的站
	    columnWidth: 0.3
	},{					
		name: 'remark',//方案描述
		colspan : 2,	
		editable:false,
		allowBlank:false,
        fieldLabel: pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.desc'),
        maxLength:200,
        xtype : 'textareafield'
	}]
});

/**
 * 偏线代理送货费管理FORM
 */
Ext.define('Foss.pricing.proxyPrice.proxyPriceShowFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:280,
	collapsible: true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	labelWidth:60,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: {
        type: 'table',
        columns: 1
    },
	items :[{
		name: 'schemeName',//方案名称
		allowBlank:false,
		readOnly:true,
        fieldLabel: pricing.proxyPrice.i18n('foss.pricing.scenarioName'),
        maxLength : 20,
        xtype : 'textfield'
	},{
		name: 'transportType',//偏线代理送货费性质
		queryMode: 'local',
	    displayField: 'value',
	    valueField: 'key',
	    value:pricing.proxyPrice.i18n('foss.pricing.carOuterWay'),
	    editable:false,
	    readOnly:true,
        fieldLabel: pricing.proxyPrice.i18n('i18n.pricingRegion.regionNature'),
        xtype : 'combo'
	},{
	    xtype : 'commonvehagencydeptselector',
	    name:'agentDeptCode',
	    editable:false,
	    readOnly:true,
	    fieldLabel:pricing.proxyPrice.i18n('foss.pricing.destinationLine'),//目的站
	    columnWidth: 0.3
	},{					
		name: 'remark',//方案描述
		colspan : 2,	
		editable:false,
		readOnly:true,
		allowBlank:false,
        fieldLabel: pricing.proxyPrice.i18n('i18n.pricing.proxyPrice.desc'),
        maxLength:200,
        xtype : 'textareafield'
	}]
});


/**
 * 开始加载界面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-proxyPriceIndex_content')) {
		return;
	}
	var queryForm = Ext.create('Foss.pricing.proxyPrice.QueryProxyPriceForm');//查询FORM
	var proxyGrid = Ext.create('Foss.pricing.proxyPrice.proxyPriceGridPanel');//查询结果GRID
	pricing.proxyPrice.queryform = queryForm;
	Ext.getCmp('T_pricing-proxyPriceIndex').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-proxyPriceIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getProxyGrid : function() {
			return proxyGrid;
		},
		items : [ queryForm, proxyGrid]
	}));
});
