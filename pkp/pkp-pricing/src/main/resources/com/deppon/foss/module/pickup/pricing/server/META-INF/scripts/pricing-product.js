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
pricing.oneLevelProduct = [];
pricing.twoLevelProduct = [];
//一级产品
pricing.searchOneLevelProduct = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findOneLevelProduct.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.oneLevelProduct = result.pricingValuationVo.productEntityList;//查询一级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productIndex.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productIndex.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//二级产品
pricing.searchTwoLeveProduct = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findTwoLevelProduct.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.twoLevelProduct = result.pricingValuationVo.productEntityList;//查询二级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productIndex.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productIndex.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
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
pricing.yes = 'Y';//是
pricing.no = 'N';//否
pricing.ALL = 'ALL';//否
pricing.priority = 'PKP_PRODUCT_SPEED'; //产品优先级
//基础产品明细MODEL
Ext.define('Foss.pricing.ProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//产品CODE
        type : 'string'
    },{
        name : 'name',//产品名称
        type : 'string'
    },{
        name : 'active',//产品激活状态
        type : 'string'
    },{
        name : 'mark',//产品备注
        type : 'string'
    },{
        name : 'transportType', //产品性质(空运/汽运)
        type : 'string'
    },{
        name : 'levels', //产品层级
        type : 'int'
    },{
        name : 'parentCode', //父级产品编号
        type : 'string'
    },{
        name : 'refId',//父级产品ID
        type : 'string'
    },{
    	name : 'parentName',//父级产品名称
    	type : 'string'
    },{
        name : 'shortName',//简称
        type : 'string'
    },{
        name : 'priority',//优先级  	快-慢
        type : 'string'
    },{
        name : 'seq',//显示排序用
        type : 'int'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    }]
});
/**
 * 产品Store
 */
Ext.define('Foss.pricing.ProductStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.ProductEntity',
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('queryProductInfo.action'),
		reader : {
			type : 'json',
			root : 'productVo.productEntityList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 产品条件查询
 */ 
Ext.define('Foss.pricing.ProductFormPanel', {
	extend : 'Ext.form.Panel',
	title: pricing.productIndex.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	labelWidth:80
    },
   	layout: 'auto',
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
        xtype : 'textfield',
		fieldLabel:pricing.productIndex.i18n('foss.pricing.productName'),//方案名称
		name: 'name',
        maxLength : 200,
        maxLengthText:pricing.productIndex.i18n('foss.pricing.theProgramNameLengthExceedsTheMaximumLimit'),
	    allowBlank:true,
	    columnWidth:.3
	},{
		name: 'active',
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    value:pricing.ALL,
	    store:pricing.getStore(null,null,['valueName','valueCode']
	    ,[{'valueName':pricing.productIndex.i18n('i18n.pricingRegion.active'),'valueCode':pricing.yes}//激活
	    ,{'valueName':pricing.productIndex.i18n('i18n.pricingRegion.unActive'),'valueCode':pricing.no}//未激活
	    ,{'valueName':pricing.productIndex.i18n('i18n.pricingRegion.all'),'valueCode':pricing.ALL}]),//全部
        fieldLabel: pricing.productIndex.i18n('foss.pricing.status'),//状态
        xtype : 'combo'
	},{
		xtype : 'container',
		margin : '0 0 0 0',
		items : [{
			xtype : 'button', 
			width:70,
			text : "查询",
			disabled: !pricing.productIndex.isPermission('indexProduct/indexProductQueryButton'),
			hidden: !pricing.productIndex.isPermission('indexProduct/indexProductQueryButton'),
			handler : function() {
				me.up().getProductGrid().getPagingToolbar().moveFirst();
 			}
		}]
	}];
		me.callParent([cfg]);
	}
});


/**
 * 产品列表
 */
Ext.define('Foss.pricing.ProductGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.productIndex.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
    sortableColumns:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.productIndex.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	productAddWindow : null,
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
	//得到弹出的显示窗口(新增)
	getProductAddWindow : function(){
		if(Ext.isEmpty(this.productAddWindow)){
			this.productAddWindow = Ext.create('Foss.pricing.ProductAddWindow');
			this.productAddWindow.parent = this;
		}
		return this.productAddWindow;
	},
	productUpdateWindow : null,
	//得到弹出的显示窗口(修改)
	getProductUpdateWindow : function(){
		if(Ext.isEmpty(this.productUpdateWindow)){
			this.productUpdateWindow = Ext.create('Foss.pricing.ProductUpdateWindow');
			this.productUpdateWindow.parent = this;
		}
		return this.productUpdateWindow;
	},
	
	//激活区域	
	activeProduct: function() {
		var me = this;
		var productIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage(pricing.productIndex.i18n('foss.pricing.pleaseSelectLeastOneData'));
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			//只有未被激活的区域的ID才会传到后台
			if(selections[i].get('active')=='N'){
				productIds.push(selections[i].get('id'));
			}else{
				pricing.showErrorMes(pricing.productIndex.i18n('foss.pricing.selectProductHaveUnactiveDataNotActive'));//请至少选择一条未激活的产品！
				return;
			}
		}
		if(productIds.length<1){
			pricing.showWoringMessage(pricing.productIndex.i18n('foss.pricing.selectProductHaveUnactiveDataNotActive'));//请至少选择一条未激活的产品！
			return;
		}
		var params = {'productVo':{'productIds':productIds}};
		var successFun = function(json){
			pricing.showInfoMes(json.message);
			me.getStore().load();
		};
		var failureFun = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.productIndex.i18n('i18n.pricingRegion.requestTimeOut'));//请求超时
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var url = pricing.realPath('activateProduct.action');
		pricing.requestJsonAjax(url,params,successFun,failureFun);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var priorityStore = FossDataDictionary.getDataDictionaryStore(pricing.priority);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.productIndex.i18n('i18n.pricingRegion.num')//序号
		},{
			
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.productIndex.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.productIndex.i18n('foss.pricing.update'),//修改
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
					var updateWindow = me.getProductUpdateWindow();//获得修改窗口
					updateWindow.productEntity = record.data;
					updateWindow.show();//显示修改窗口
    				
                }
			}]
		},{
			text : pricing.productIndex.i18n('foss.pricing.productNo'),//产品编号
			dataIndex : 'code'
		},{
			text : pricing.productIndex.i18n('foss.pricing.productName'),//产品名称
			dataIndex : 'name'
		},{
			text : pricing.productIndex.i18n('foss.pricing.createUser'),//创建人
			dataIndex : 'createUserName'
		},{
			text : pricing.productIndex.i18n('foss.pricing.createTime'),//创建时间
			dataIndex : 'createDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.productIndex.i18n('foss.pricing.lastOperator'),//最后操作人
			dataIndex : 'modifyUserName'
		},{
			text : pricing.productIndex.i18n('foss.pricing.finalOperationTime'),//最后操作时间
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.productIndex.i18n('foss.pricing.productLevel'),//产品级别
			dataIndex : 'levels'
		},{
			text : pricing.productIndex.i18n('foss.pricing.parentProductName'),//父级产品名称
			dataIndex : 'parentName'
		},{
			text : pricing.productIndex.i18n('foss.pricing.productStatus'),//产品状态
			dataIndex : 'active',
			idth:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.productIndex.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.productIndex.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : pricing.productIndex.i18n('foss.pricing.productCode'),//产品简称
			dataIndex : 'shortName'
		},{
			text : pricing.productIndex.i18n('foss.pricing.sequenceNumber'),//顺序号
			dataIndex : 'seq'
		},{
			text : pricing.productIndex.i18n('foss.pricing.priority'),//优先级
			dataIndex : 'priority',
			renderer:function(value){
				return pricing.changeCodeToNameStore(priorityStore,value);
			}
		}];
		me.store = Ext.create('Foss.pricing.ProductStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryProductForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : { 
								'productVo.productEntity.name' 		: queryForm.getForm().findField('name').getValue(),
								'productVo.productEntity.active'	: queryForm.getForm().findField('active').getValue()
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
			text : pricing.productIndex.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.productIndex.isPermission('indexProduct/indexProductAddButton'),
			hidden: !pricing.productIndex.isPermission('indexProduct/indexProductAddButton'),
			handler :function(){
				me.getProductAddWindow().show();
			} 
		},'-', {
			text : pricing.productIndex.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.productIndex.isPermission('indexProduct/indexProductActiveButton'),
			hidden: !pricing.productIndex.isPermission('indexProduct/indexProductActiveButton'),
			handler :function(){
				me.activeProduct();
			} 
		} ];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 产品定义主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	pricing.searchOneLevelProduct();
	pricing.searchTwoLeveProduct();
	var productGrid = Ext.create('Foss.pricing.ProductGridPanel');//查询结果GRID
	var queryform = Ext.create('Foss.pricing.ProductFormPanel');//查询结果GRID
	Ext.getCmp('T_pricing-indexProduct').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-indexProduct_content',
		cls : 'panelContentNToolbar',
		margin:'0 0 0 0',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询结果GRID
		getProductGrid : function() {
			return productGrid;
		},
		getQueryProductForm : function() {
			return queryform;
		},
		items : [queryform,productGrid]
	}));
});

/**
 * 新增产品信息
 */
Ext.define('Foss.pricing.ProductAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.productIndex.i18n('foss.pricing.productInformationIntoThe'),//产品信息录入
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.ProductGridPanel）
	modal : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :350,
	height :500,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getProductForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//新增产品FORM
	productForm:null,
	getProductForm : function(){
    	if(Ext.isEmpty(this.platformForm)){
    		this.platformForm = Ext.create('Foss.pricing.ProductForm');
    	}
    	return this.platformForm;
    },
    //提交产品数据
    commitProduct:function(){
    	var me = this;
    	if(me.getProductForm().getForm().isValid()){//校验form是否通过校验
    		var productModel = new Foss.pricing.ProductEntity();
    		me.getProductForm().getForm().updateRecord(productModel);//将FORM中数据设置到MODEL里面
    		var parentCode = null;//父产品CODE
    		var refId = null;//父产品ID
    		if(productModel.get('levels')==2){
    			var field = me.getProductForm().getForm().findField('parentCodeOne');
    			refId = field.refId;
    			parentCode = field.getValue();
        		if(Ext.isEmpty(parentCode)){
        			pricing.showWoringMessage(pricing.productIndex.i18n('foss.pricing.pleaseSelectTheParentProduct'));//请选择父级产品！
        			return;
        		}
    		}
    		if(productModel.get('levels')==3){
    			var field = me.getProductForm().getForm().findField('parentCodeTwo');
    			refId = field.refId;
    			parentCode = field.getValue();
        		if(Ext.isEmpty(parentCode)){
        			pricing.showWoringMessage(pricing.productIndex.i18n('foss.pricing.pleaseSelectTheParentProduct'));//请选择父级产品！
        			return;
        		}
    		}
    		productModel.set('parentCode',parentCode);
    		productModel.set('refId',refId);
    		var params = {'productVo':{'productEntity':productModel.data}};//新增产品数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.parent.getStore().load();//成功之后重新查询刷新结果集
				me.close();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.productIndex.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addProduct.action');//请求产品新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.productIndex.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.productIndex.i18n('foss.pricing.reset'),//重置
			handler :function(){
				me.getProductForm().getForm().reset();
			} 
		},{
			text : pricing.productIndex.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 85',
			handler :function(){
				me.commitProduct();
			} 
		}];
		me.items = [me.getProductForm()];
		me.callParent([cfg]);
	}
});
/**
 * 产品-FORM
 */
Ext.define('Foss.pricing.ProductForm', {
	extend : 'Ext.form.Panel',
	title : pricing.productIndex.i18n('foss.pricing.productInformationIntoThe'),//产品信息录入
	frame: true,
	flex:1,
	margin:'0 0 0 0',
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	width:250,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 1
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
	    var twoLevelProduct = [];
		me.items = [{
			name: 'name',//名称
			allowBlank:false,
			maxLength:200,
	        fieldLabel: pricing.productIndex.i18n('foss.pricing.name'),
	        xtype : 'textfield'
		},{
			name: 'code',//编码
			allowBlank:false,
			maxLength:200,
	        fieldLabel: pricing.productIndex.i18n('foss.pricing.coding'),
	        xtype : 'textfield'
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'levels',
			 fieldLabel:pricing.productIndex.i18n('foss.pricing.level'),//层级
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.productIndex.i18n('foss.pricing.oneLevel'),
			     name:'levels',
			     inputValue:1,
			     listeners:{
			    	 change:function(item,newValue){
			    		 if(newValue){
			    			 me.getForm().findField('text').show();
			    			 me.getForm().findField('parentCodeOne').hide();
			    			 me.getForm().findField('parentCodeTwo').hide();
			    		 }
			    	 }
			     }
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.productIndex.i18n('foss.pricing.twoLevel'),
			     name:'levels',
			     inputValue:2,
			     listeners:{
			    	 change:function(item,newValue){
			    		 if(newValue){
			    			 me.getForm().findField('parentCodeOne').show();
			    			 me.getForm().findField('parentCodeTwo').hide();
			    			 me.getForm().findField('text').hide();
			    		 }
			    	 }
			     }
			 },{
		    	 xtype:'radio',
			     boxLabel:pricing.productIndex.i18n('foss.pricing.threeLevel'),
			     name:'levels',
			     inputValue:3,
			     listeners:{
			    	 change:function(item,newValue){
			    		 if(newValue){
			    			 me.getForm().findField('parentCodeTwo').show();
			    			 me.getForm().findField('parentCodeOne').hide();
			    			 me.getForm().findField('text').hide();
			    		 }
			    	 }
			     }
			 }]
		},{
			name:'text',
			xtype:'displayfield',
			value:pricing.productIndex.i18n('foss.pricing.parentProducts')
		},{
			name:'parentCodeOne',
			queryMode: 'local',
    	    displayField: 'name',
    	    valueField: 'code',
    	    hidden:true,
    	    refId:null,//父级
    	    editable:false,
    	    fieldLabel:'<span style="color:red">*</span>'+pricing.productIndex.i18n('foss.pricing.parentProductsNormal'),//父级产品
    	    store:pricing.getStore(null,'Foss.pricing.ProductEntity',null
    	    ,pricing.oneLevelProduct),
            xtype : 'combo',
            listeners:{
            	change:function(com,newValue){
            		com.getStore().each(function(record){
            			if(record.get('code')==newValue){
            				com.refId = record.get('id');
            			}
            		});
            	}
            }
		},{
			name:'parentCodeTwo',
			queryMode: 'local',
			hidden:true,
    	    displayField: 'name',
    	    valueField: 'code',
    	    refId:null,//父级
    	    editable:false,
    	    fieldLabel:'<span style="color:red">*</span>'+pricing.productIndex.i18n('foss.pricing.parentProductsNormal'),//父级产品
    	    store:pricing.getStore(null,'Foss.pricing.ProductEntity',null
    	    ,pricing.twoLevelProduct),
            xtype : 'combo',
            listeners:{
            	change:function(com,newValue){
            		com.getStore().each(function(record){
            			if(record.get('code')==newValue){
            				com.refId = record.get('id');
            			}
            		});
            	}
            }
		},{
			name:'priority',
			queryMode: 'local',
    	    displayField: 'valueName',
    	    valueField: 'valueCode',
    	    editable:false,
    	    allowBlank:false,
    	    fieldLabel:pricing.productIndex.i18n('foss.pricing.priority'),//优先级
    	    store:FossDataDictionary.getDataDictionaryStore(pricing.priority),
            xtype : 'combo'
		},{
			name: 'seq',//顺序号
			allowBlank:false,
	        fieldLabel: pricing.productIndex.i18n('foss.pricing.sequenceNumber'),
	        step:1,
	        maxValue:50,
	        minValue:0,
	        xtype : 'numberfield'
		},{
			name: 'shortName',//简称
			maxLength:50,
	        fieldLabel: pricing.productIndex.i18n('foss.pricing.simplName'),
	        xtype : 'textfield'
		},{
			name: 'mark',//备注
	        fieldLabel: pricing.productIndex.i18n('foss.pricing.remark'),
	        maxLength:200,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改产品
 */
Ext.define('Foss.pricing.ProductUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.productIndex.i18n('foss.pricing.modifyProduct'),//修改产品
	closable : true,
	modal : true,
	resizable:false,
	productEntity:null,//修改产品数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.ProductGridPanel）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :350,
	height :500,
	listeners:{
		beforehide:function(me){
			me.getProductForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getProductForm().getForm().loadRecord(new Foss.pricing.ProductEntity(me.productEntity));
			if(me.productEntity.levels==2){
				 me.getProductForm().getForm().findField('parentCodeOne').setValue(me.productEntity.parentCode);
			}
			if(me.productEntity.levels==3){
				 me.getProductForm().getForm().findField('parentCodeTwo').setValue(me.productEntity.parentCode);
			}
		}
	},
	//修改产品FORM
	productForm:null,
	getProductForm : function(){
    	if(Ext.isEmpty(this.productForm)){
    		this.productForm = Ext.create('Foss.pricing.ProductForm');
    	}
    	return this.productForm;
    },
    //修改产品
    commitProduct:function(){
    	var me = this;
    	if(me.getProductForm().getForm().isValid()){//校验form是否通过校验
    		var productModel = new Foss.pricing.ProductEntity(me.productEntity);
    		me.getProductForm().getForm().updateRecord(productModel);//将FORM中数据设置到MODEL里面
    		var parentCode = null;//父产品CODE
    		var refId = null;//父产品ID
    		if(productModel.get('levels')==2){
    			var field = me.getProductForm().getForm().findField('parentCodeOne');
    			refId = field.refId;
    			parentCode = field.getValue();
    		}
    		if(productModel.get('levels')==3){
    			var field = me.getProductForm().getForm().findField('parentCodeTwo');
    			refId = field.refId;
    			parentCode = field.getValue();
    		}
    		productModel.set('parentCode',parentCode);
    		productModel.set('refId',refId);
            var params = {'productVo':{'productEntity':productModel.data}};//修改产品数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示修改成功
				me.parent.getStore().load();//成功之后重新查询刷新结果集
				me.close();
				
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.productIndex.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateProduct.action');//请求产品新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.productIndex.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.productIndex.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getProductForm().getForm().loadRecord(new Foss.pricing.ProductEntity(me.productEntity));
					if(me.productEntity.levels==2){
						 me.getProductForm().getForm().findField('parentCodeOne').setValue(me.productEntity.parentCodeOne);
					}
					if(me.productEntity.levels==3){
						 me.getProductForm().getForm().findField('parentCodeTwo').setValue(me.productEntity.parentCodeOne);
					}
			} 
		},{
			text : pricing.productIndex.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 85',
			handler :function(){
				me.commitProduct();
			} 
		}];
		me.items = [ me.getProductForm()];
		me.callParent([cfg]);
	}
});



