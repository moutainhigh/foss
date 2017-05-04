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
pricing.productEntityList = [];//基础产品列表
pricing.goodTypeList = [];//货物类型列表
//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondiction.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productItem.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productItem.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询基础产品列表
pricing.searchProductEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryProductInfoByLevel2AndLevel3.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.productEntityList = result.productVo.productEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productItem.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.productItem.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
pricing.yes = 'Y';//是
pricing.no = 'N';//否
pricing.ALL = 'ALL';//全部
//--------------------------------------pricing----------------------------------------
//产品条目信息
Ext.define('Foss.pricing.ProductItemEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'modifyDate',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'goodstypeId',//货物类型ID
        type : 'string'
    },{
        name : 'goodstypeCode',//货物类型CODE
        type : 'string'
    },{
        name : 'productId',// 产品id
        type : 'string'
    },{
        name : 'productCode',// 产品CODE
        type : 'string'
    },{
        name : 'code',//条目编码
        type : 'string'
    },{
        name : 'name',// 条目名称
        type : 'string'
    },{
        name : 'mark',// 描述
        type : 'string'
    },{
        name : 'active',// 是否启用
        type : 'string'
    },{
        name : 'feeBottom',//最低一票
        defaultValue:null
    },{
        name : 'goodstypeName',//货物类型名称
        defaultValue:null
    },{
        name : 'createUserName',//创建人姓名
        defaultValue:null
    }]
});
//基础产品明细MODEL
Ext.define('Foss.pricing.ProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
    },{
        name : 'code',//code
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'mark',//描述
        type : 'string'
    },{
        name : 'transportType',//运输类型
        type : 'string'
    },{
        name : 'levels'//产品等级
    },{
        name : 'parentCode',//父产品CODE
        type : 'string'
    },{
        name : 'refId',
        type : 'string'
    },{
        name : 'shortName',//简称
        type : 'string'
    },{
        name : 'priority',
        type : 'string'
    }]
});
//货物类型MODEL
Ext.define('Foss.pricing.GoodsTypeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'//id
    },{
        name : 'code',//编号
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'mark',//描述
        type : 'string'
    }]
});
//------------------------------------model---------------------------------------------------
/**
 * 产品条目Store（Foss.pricing.ProductItemEntity）
 */
Ext.define('Foss.pricing.ProductItemStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.ProductItemEntity',//产品条目的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchProductItemByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'productItemVo.productItemEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 产品条目表单
 */
Ext.define('Foss.pricing.QueryProductItemForm', {
	extend : 'Ext.form.Panel',
	title: pricing.productItem.i18n('foss.pricing.searchInfoProductEntry'),//产品条目信息查询
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
    height :150,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			name:'name',
			labelWidth:120,
	        fieldLabel: pricing.productItem.i18n('foss.pricing.productEntryProgramName'),//产品条目方案名称
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:pricing.ALL,
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':pricing.productItem.i18n('i18n.pricingRegion.active'),'valueCode':pricing.yes}//激活
		    ,{'valueName':pricing.productItem.i18n('i18n.pricingRegion.unActive'),'valueCode':pricing.no}//未激活
		    ,{'valueName':pricing.productItem.i18n('i18n.pricingRegion.all'),'valueCode':pricing.ALL}]),//全部
	        fieldLabel: pricing.productItem.i18n('foss.pricing.status'),//状态
	        xtype : 'combo'
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.productItem.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.productItem.i18n('i18n.pricingRegion.search'),//查询
			disabled: !pricing.productItem.isPermission('productItem/productItemQueryButton'),
			hidden: !pricing.productItem.isPermission('productItem/productItemQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getProductItemGrid().getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 产品条目列表
 */
Ext.define('Foss.pricing.ProductItemGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.productItem.i18n('foss.pricing.infoProductEntry'),//产品条目信息
	frame: true,
	flex:1,
	bodyCls: 'autoHeight',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.productItem.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//产品条目新增WINDOW
	productItemAddWindow:null,
	getProductItemAddWindow:function(){
		if (Ext.isEmpty(this.productItemAddWindow)) {
			this.productItemAddWindow = Ext.create('Foss.pricing.ProductItemAddWindow');
			this.productItemAddWindow.parent = this;//父元素
		}
		return this.productItemAddWindow;
	},
	//修改产品条目WINDOW
	productItemUpdateWindow:null,
	getProductItemUpdateWindow:function(){
		if (Ext.isEmpty(this.productItemUpdateWindow)) {
			this.productItemUpdateWindow = Ext.create('Foss.pricing.ProductItemUpdateWindow');
			this.productItemUpdateWindow.parent = this;//父元素
		}
		return this.productItemUpdateWindow;
	},
	//删除产品条目
	deleteProductItem: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.productItem.i18n('foss.pricing.pleaseSelectOneDeleteOperation'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		var productItemids = new Array();//产品条目
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')==pricing.no){
				productItemids.push(selections[i].get('id'));
			}else{
				
			}
		}
		if(Ext.isEmpty(productItemids)){
			pricing.showWoringMessage(pricing.productItem.i18n('foss.pricing.selectOneProductEntry'));//请至少选择一条未激活产品条目！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.productItem.i18n('foss.pricing.isDeleteTheseProductEntry'),function(e){//是否要删除这些产品条目？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'productItemVo':{'productItemids':productItemids}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteProductItem.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
		
	},
	//激活产品条目
	activeProductItem: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.productItem.i18n('foss.pricing.pleaseSelectAnActivateOperation'));//请选择一条进行激活操作！
			return;//没有则提示并返回
		}
		var productItemids = new Array();//产品条目
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')==pricing.no){
				productItemids.push(selections[i].get('id'));
			}else{
				pricing.showWoringMessage(pricing.productItem.i18n('foss.pricing.selectUnActiveProductEntry'));//请选择未激活产品条目进行激活！
				return;//没有则提示并返回
			}
		}
		if(Ext.isEmpty(productItemids)){
			pricing.showWoringMessage(pricing.productItem.i18n('foss.pricing.selectOneProductEntry'));//请至少选择一条未激活产品条目！
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.productItem.i18n('foss.pricing.isActiveProductEntry'),function(e){//是否要激活这些产品条目？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'productItemVo':{'productItemids':productItemids}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activationProductItem.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
		
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.productItem.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.productItem.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.productItem.i18n('foss.pricing.update'),//修改
                disabled: !pricing.productItem.isPermission('productItem/productItemUpdateButton'),
				width:42,
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var id= record.get('id');//产品条目ID
    				var params = {'productItemVo':{'productItemEntity':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getProductItemUpdateWindow();//获得修改窗口
    					updateWindow.productItemEntity = json.productItemVo.productItemEntity;//产品条目
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('findProductItemById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}/*,{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.productItem.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_delete';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		pricing.showQuestionMes(pricing.productItem.i18n('foss.pricing.isDeleteThisProductEntry'),function(e){//是否要删除这个产品条目？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var productItemids = new Array();//产品条目
            				productItemids.push(record.get('id'));
            				var params = {'productItemVo':{'productItemids':productItemids}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteProductItem.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}*/]
		},{
			text : pricing.productItem.i18n('foss.pricing.productEntryProgramName'),//产品条目方案名称
			dataIndex : 'name'
		},{
			text : pricing.productItem.i18n('foss.pricing.entryNumber'),//条目编号
			dataIndex : 'code'
		},{
			text : pricing.productItem.i18n('foss.pricing.cargoTypeNumber'),//货物类型编号
			dataIndex : 'goodstypeCode'
		},{
			text : pricing.productItem.i18n('foss.pricing.theNameOfTheTypeOfGoods'),//货物类型名称
			dataIndex : 'goodstypeName'
		},{
			text : pricing.productItem.i18n('foss.pricing.productDefinitionNumber'),//产品定义编号
			dataIndex : 'productCode'
		},{
			text : pricing.productItem.i18n('foss.pricing.newName'),//新增人名称
			dataIndex : 'createUserName'
		},{
			text : pricing.productItem.i18n('foss.pricing.theLowestVotes'),//最低一票
			dataIndex : 'feeBottom'
		},{
			text : pricing.productItem.i18n('foss.pricing.activeStatus'),//激活状态
			dataIndex : 'active',
			renderer:function(value){
				if(value == pricing.yes){
					return pricing.productItem.i18n('foss.pricing.hasBeenActivated');
				}else if(value == pricing.no){
					return pricing.productItem.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		}];
		me.store = Ext.create('Foss.pricing.ProductItemStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryProductItemForm();
					var active = queryForm.getForm().findField('active').getValue();
					if(active==pricing.ALL){
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {//产品条目大查询，查询条件组织
									'productItemVo.productItemEntity.name':queryForm.getForm().findField('name').getValue()//产品条目名称
								}
							});				
					    }
					}else{
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {//产品条目大查询，查询条件组织
									'productItemVo.productItemEntity.name':queryForm.getForm().findField('name').getValue(),//产品条目名称
									'productItemVo.productItemEntity.active':active//产品条目状态
								}
							});				
					    }
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
			text : pricing.productItem.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.productItem.isPermission('productItem/productItemActiveButton'),
			hidden: !pricing.productItem.isPermission('productItem/productItemActiveButton'),
			handler :function(){
				me.activeProductItem();
			} 
		},'-',{
			text : pricing.productItem.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.productItem.isPermission('productItem/productItemAddButton'),
			hidden: !pricing.productItem.isPermission('productItem/productItemAddButton'),
			handler :function(){
				me.getProductItemAddWindow().show();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @mark 产品条目主页
 */
Ext.onReady(function() {
	Ext.form.Field.prototype.msgTarget='side';
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-productItem_content')) {
		return;
	};
	pricing.searchGoodTypeList();//货物类型
	pricing.searchProductEntityList();//产品
	var queryProductItemForm = Ext.create('Foss.pricing.QueryProductItemForm');//查询FORM
	var productItemGrid = Ext.create('Foss.pricing.ProductItemGrid');//查询结果GRID
	Ext.getCmp('T_pricing-productItem').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-productItem_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryProductItemForm : function() {
			return queryProductItemForm;
		},
		//获得查询结果GRID
		getProductItemGrid : function() {
			return productItemGrid;
		},
		items : [queryProductItemForm, productItemGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增产品条目信息
 */
Ext.define('Foss.pricing.ProductItemAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.productItem.i18n('foss.pricing.newProductEntries'),//新增产品条目
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.ProductItemGrid）
	modal : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :350,
	height :430,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getProductItemForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//新增产品条目FORM
	productItemForm:null,
	getProductItemForm : function(){
    	if(Ext.isEmpty(this.productItemForm)){
    		this.productItemForm = Ext.create('Foss.pricing.ProductItemForm',{
    			'isUpdate':false
    		});
    	}
    	return this.productItemForm;
    },
    //提交产品条目数据
    commitProductItem:function(){
    	var me = this;
    	if(me.getProductItemForm().getForm().isValid()){//校验form是否通过校验
    		var productItemModel = new Foss.pricing.ProductItemEntity();
    		me.getProductItemForm().getForm().updateRecord(productItemModel);//将FORM中数据设置到MODEL里面
    		productItemModel.set('active',pricing.no);
    		var goodstypeCode = me.getProductItemForm().getForm().findField('goodstypeId').goodsTypeRecord.get('code');//货物类型CODE
    		productItemModel.set('goodstypeCode',goodstypeCode);
    		var productCode = me.getProductItemForm().getForm().findField('productId').productRecord.get('code');//货物类型CODE
    		productItemModel.set('productCode',productCode);   		
    		var params = {'productItemVo':{'productItemEntity':productItemModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addProductItem.action');//请求产品条目新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.productItem.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.productItem.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getProductItemForm().getForm().loadRecord(new Foss.pricing.ProductItemEntity());
			} 
		},{
			text : pricing.productItem.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 85',
			handler :function(){
				me.commitProductItem();
			} 
		}];
		me.items = [me.getProductItemForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改产品条目
 */
Ext.define('Foss.pricing.ProductItemUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.productItem.i18n('foss.pricing.modifyProductEntries'),//修改产品条目
	closable : true,
	modal : true,
	resizable:false,
	productItemEntity:null,//修改产品条目数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :350,
	height :430,
	listeners:{
		beforehide:function(me){
			me.getProductItemForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getProductItemForm().getForm().loadRecord(new Foss.pricing.ProductItemEntity(me.productItemEntity));
		}
	},
	//修改产品条目FORM
	productItemForm:null,
	getProductItemForm : function(){
    	if(Ext.isEmpty(this.productItemForm)){
    		this.productItemForm = Ext.create('Foss.pricing.ProductItemForm');
    	}
    	return this.productItemForm;
    },
    //修改产品条目
    commitProductItem:function(){
    	var me = this;
    	if(me.getProductItemForm().getForm().isValid()){//校验form是否通过校验
    		var productItemModel = new Foss.pricing.ProductItemEntity(me.productItemEntity);
    		me.getProductItemForm().getForm().updateRecord(productItemModel);//将FORM中数据设置到MODEL里面
    		productItemModel.set('active',pricing.no);
    		var goodstypeCode = me.getProductItemForm().getForm().findField('goodstypeId').goodsTypeRecord.get('code');//货物类型CODE
    		productItemModel.set('goodstypeCode',goodstypeCode);
    		var productCode = me.getProductItemForm().getForm().findField('productId').productRecord.get('code');//货物类型CODE
    		productItemModel.set('productCode',productCode);
    		var params = {'productItemVo':{'productItemEntity':productItemModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.productItem.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateProductItem.action');//请求产品条目新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.productItem.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.productItem.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getProductItemForm().getForm().loadRecord(new Foss.pricing.ProductItemEntity(me.productItemEntity));
			} 
		},{
			text : pricing.productItem.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 85',
			handler :function(){
				me.commitProductItem();
			} 
		}];
		me.items = [ me.getProductItemForm()];
		me.callParent([cfg]);
	}
});
/**
 * 产品条目-FORM
 */
Ext.define('Foss.pricing.ProductItemForm', {
	extend : 'Ext.form.Panel',
	title : pricing.productItem.i18n('foss.pricing.infoProductEntry'),//产品条目信息
	frame: true,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:110,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 1
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',//产品条目方案名称
			allowBlank:false,
	        fieldLabel: pricing.productItem.i18n('foss.pricing.productEntryProgramName'),
	        maxLength:50,
	        maxLengthText:pricing.productItem.i18n('foss.pricing.theProductEntriesSchemeNameLengthExceedsMaximumLimit'),
	        xtype : 'textfield'
		},{
			name: 'code',//方案编码
			allowBlank:false,
			maxLength:20,
			maxLengthText:pricing.productItem.i18n('foss.pricing.programCodingLengthMax'),
	        fieldLabel: pricing.productItem.i18n('foss.pricing.programCoding'),
	        xtype : 'textfield'
		},{
			name: 'goodstypeId',
			queryMode: 'local',
			allowBlank:false,
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    goodsTypeRecord:null,
		    store:pricing.getStore(null,'Foss.pricing.GoodsTypeEntity',null
		    ,pricing.goodTypeList),
	        fieldLabel: pricing.productItem.i18n('foss.pricing.theNameOfTheTypeOfGoods'),//货物类型名称
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldvalue){
	        		if(!Ext.isEmpty(newValue)){
	        			comb.goodsTypeRecord = comb.getStore().getById(newValue);
	        		}else{
	        			comb.goodsTypeRecord = null;
	        		}
	        		
	        	}
	        }
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    allowBlank:false,
		    valueField: 'id',
		    editable:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.ProductEntity',null
		    ,pricing.productEntityList),
	        fieldLabel: pricing.productItem.i18n('foss.pricing.productDefinition'),//产品定义
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldvalue){
	        		if(!Ext.isEmpty(newValue)){
	        			comb.productRecord = comb.getStore().getById(newValue);
	        		}else{
	        			comb.productRecord = null;
	        		}
	        		
	        	}
	        }
		},{
			xtype:'numberfield',
	        decimalPrecision:0,
	        allowBlank:false,
	        fieldLabel:pricing.productItem.i18n('foss.pricing.theLowestVotes'),//最低一票
	        name:'feeBottom',
	        maxValue: 99999999,
	        minValue: 0
		},{
			name: 'mark',//类型描述
	        fieldLabel: pricing.productItem.i18n('foss.pricing.typeDescription'),
	        maxLength:200,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});

