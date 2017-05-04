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
pricing.yes = 'Y';//是
pricing.no = 'N';//否
pricing.ALL = 'ALL';//全部
//--------------------------------------pricing----------------------------------------
//货物类型信息
Ext.define('Foss.pricing.GoodEntity', {
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
        name : 'createDate',
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'code',// 编码
        type : 'string'
    },{
        name : 'name',// 名称
        type : 'string'
    },{
        name : 'active',// 是否启用
        type : 'string'
    },{
        name : 'mark',//描述
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 货物类型Store（Foss.pricing.GoodEntity）
 */
Ext.define('Foss.pricing.GoodStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.GoodEntity',//货物类型的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchGoodsTypeByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'goodVo.goodsTypeEntityList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

/**
 * 货物类型表单
 */
Ext.define('Foss.pricing.QueryGoodForm', {
	extend : 'Ext.form.Panel',
	title: pricing.good.i18n('foss.pricing.theCargoTypeInformationQuery'),//货物类型信息查询
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
	        fieldLabel: pricing.good.i18n('foss.pricing.theNameOfTheTypeOfGoods'),//产品条目方案名称
	        labelWidth:120,
	        xtype : 'textfield'
		},{
			name: 'active',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:pricing.ALL,
		    store:pricing.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':pricing.good.i18n('i18n.pricingRegion.active'),'valueCode':pricing.yes}//激活
		    ,{'valueName':pricing.good.i18n('i18n.pricingRegion.unActive'),'valueCode':pricing.no}//未激活
		    ,{'valueName':pricing.good.i18n('i18n.pricingRegion.all'),'valueCode':pricing.ALL}]),//全部
	        fieldLabel: pricing.good.i18n('foss.pricing.status'),//状态
	        xtype : 'combo'
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.good.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.good.i18n('i18n.pricingRegion.search'),//查询
			disabled: !pricing.good.isPermission('good/goodQueryButton'),
			hidden: !pricing.good.isPermission('good/goodQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getGoodGrid().getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 货物类型列表
 */
Ext.define('Foss.pricing.GoodGrid', {
	extend: 'Ext.grid.Panel',
	title : pricing.good.i18n('foss.pricing.cargoTypeInformation'),//货物类型信息
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	flex:1,
    sortableColumns:false,
   // enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.good.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//货物类型新增WINDOW
	goodAddWindow:null,
	getGoodAddWindow:function(){
		if (Ext.isEmpty(this.goodAddWindow)) {
			this.goodAddWindow = Ext.create('Foss.pricing.GoodAddWindow');
			this.goodAddWindow.parent = this;//父元素
		}
		return this.goodAddWindow;
	},
	//修改货物类型WINDOW
	goodUpdateWindow:null,
	getGoodUpdateWindow:function(){
		if (Ext.isEmpty(this.goodUpdateWindow)) {
			this.goodUpdateWindow = Ext.create('Foss.pricing.GoodUpdateWindow');
			this.goodUpdateWindow.parent = this;//父元素
		}
		return this.goodUpdateWindow;
	},
	//删除货物类型
	deleteGood: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.good.i18n('foss.pricing.pleaseSelectVoidOperation'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		var goodIds = new Array();//货物类型
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')==pricing.no){
				goodIds.push(selections[i].get('id'));
			}else{
				
			}
		}
		if(Ext.isEmpty(goodIds)){
			pricing.showWoringMessage(pricing.good.i18n('foss.pricing.pleaseSelectLeastOneNotActivateTheCargoType'));//请至少选择一条未激活货物类型
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.good.i18n('foss.pricing.wantDeleteTheseTypeGoods'),function(e){//是否要删除这些货物类型？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'goodVo':{'goodIds':goodIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteGoodsType.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	//激活货物类型
	activeGood:function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.good.i18n('foss.pricing.pleaseSelectAnActivateOperation'));//请选择一条进行激活操作！
			return;//没有则提示并返回
		}
		var goodIds = new Array();//货物类型
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')==pricing.no){
				goodIds.push(selections[i].get('id'));
			}else{
				
			}
		}
		if(Ext.isEmpty(goodIds)){
			pricing.showWoringMessage(pricing.good.i18n('foss.pricing.pleaseSelectLeastOneNotActivateTheCargoType'));//请至少选择一条未激活货物类型
			return;//没有则提示并返回
		}
		pricing.showQuestionMes(pricing.good.i18n('foss.pricing.toActivateTheseTypeOfGoods'),function(e){//是否要激活这些货物类型？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'goodVo':{'goodIds':goodIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activationGoodsType.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.good.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.good.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.good.i18n('foss.pricing.update'),//修改
                disabled: !pricing.good.isPermission('good/goodUpdateQueryButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid,rowIndex,colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	var id= record.get('id');//货物类型ID
    				var params = {'goodVo':{'goodsTypeEntity':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getGoodUpdateWindow();//获得修改窗口
    					updateWindow.goodsTypeEntity = json.goodVo.goodsTypeEntity;//货物类型
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('findGoodsTypeById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}/*,{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.good.i18n('foss.pricing.delete'),//删除
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
            		pricing.showQuestionMes(pricing.good.i18n('foss.pricing.wantDeleteThisTypeGoods'),function(e){//是否要删除这个货物类型？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var goodIds = new Array();//货物类型
            				goodIds.push(record.get('id'));
            				var params = {'goodVo':{'goodIds':goodIds}};
            				var successFun = function(json){
            					pricing.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
            					}else{
            						pricing.showErrorMes(json.message);
            					}
            				};
            				var url = pricing.realPath('deleteGoodsType.action');
            				pricing.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                }
			}*/]
		},{
			text : pricing.good.i18n('foss.pricing.typeDefinitionNumber'),//类型定义编号
			flex: 1,
			dataIndex : 'code'
		},{
			text : pricing.good.i18n('foss.pricing.theNameOfTheTypeOfGoods'),//货物类型名称
			flex: 1,
			dataIndex : 'name'
		},{
			text : pricing.good.i18n('foss.pricing.createUser'),//创建人
			flex: 1,
			dataIndex : 'createUserName'
		},{
			text : pricing.good.i18n('foss.pricing.createTime'),//创建时间
			flex: 1,
			dataIndex : 'createDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.good.i18n('foss.pricing.lastModifiedUser'),//最后修改人
			flex: 1,
			dataIndex : 'modifyUserName'
		},{
			text : pricing.good.i18n('foss.pricing.lastModifiedTime'),//最后修改时间
			flex: 1,
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.good.i18n('foss.pricing.cargoStatus'),//货物状态
			flex: 1,
			dataIndex : 'active',
			renderer:function(value){
				if(value ==pricing.yes){
					return pricing.good.i18n('i18n.pricingRegion.active');//激活
				}else if(value ==pricing.no){
					return pricing.good.i18n('i18n.pricingRegion.unActive');//未激活
				}else{
					return '';
				}
			}
		},{
			text : pricing.good.i18n('foss.pricing.typeDescription'),//类型描述
			width:150,
			dataIndex : 'mark'
		}];
		me.store = Ext.create('Foss.pricing.GoodStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryGoodForm();
					var active = queryForm.getForm().findField('active').getValue();
					if(active == pricing.ALL){
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {//货物类型大查询，查询条件组织
									'goodVo.goodsTypeEntity.name':queryForm.getForm().findField('name').getValue()//货物类型名称
								}
							});
					    }
					}else{
						if(queryForm!=null){
							Ext.apply(operation,{
								params : {//货物类型大查询，查询条件组织
									'goodVo.goodsTypeEntity.name':queryForm.getForm().findField('name').getValue(),//货物类型名称
									'goodVo.goodsTypeEntity.active':active//货物状态
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
			text : pricing.good.i18n('i18n.pricingRegion.active'),//激活
			disabled: !pricing.good.isPermission('good/goodActiveButton'),
			hidden: !pricing.good.isPermission('good/goodActiveButton'),
			handler :function(){
				me.activeGood();
			} 
		},'-',{
			text : pricing.good.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.good.isPermission('good/goodAddQueryButton'),
			hidden: !pricing.good.isPermission('good/goodAddQueryButton'),
			handler :function(){
				me.getGoodAddWindow().show();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @mark 货物类型主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-good_content')) {
		return;
	};
	var queryGoodForm = Ext.create('Foss.pricing.QueryGoodForm');//查询FORM
	var goodGrid = Ext.create('Foss.pricing.GoodGrid');//查询结果GRID
	Ext.getCmp('T_pricing-good').add(Ext.create('Ext.panel.Panel',{
		id : 'T_pricing-good_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryGoodForm : function() {
			return queryGoodForm;
		},
		//获得查询结果GRID
		getGoodGrid : function() {
			return goodGrid;
		},
		items : [queryGoodForm, goodGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 新增货物类型信息
 */
Ext.define('Foss.pricing.GoodAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.good.i18n('foss.pricing.newTheTypeOfCargo'),//新增货物类型
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.GoodGrid）
	modal : true,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :570,
	height :300,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getGoodForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			
		}
	},
	//新增货物类型FORM
	goodForm:null,
	getGoodForm : function(){
    	if(Ext.isEmpty(this.goodForm)){
    		this.goodForm = Ext.create('Foss.pricing.GoodForm');
    	}
    	return this.goodForm;
    },
    //提交货物类型数据
    commitGood:function(){
    	var me = this;
    	if(me.getGoodForm().getForm().isValid()){//校验form是否通过校验
    		var goodModel = new Foss.pricing.GoodEntity();
    		me.getGoodForm().getForm().updateRecord(goodModel);//将FORM中数据设置到MODEL里面
    		goodModel.set('active',pricing.no);
    		var params = {'goodVo':{'goodsTypeEntity':goodModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addGoodsType.action');//请求货物类型新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.good.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.good.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getGoodForm().getForm().loadRecord(new Foss.pricing.GoodEntity());
			} 
		},{
			text : pricing.good.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitGood();
			} 
		}];
		me.items = [me.getGoodForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改货物类型
 */
Ext.define('Foss.pricing.GoodUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.good.i18n('foss.pricing.modifyTheTypeOfCargo'),//修改货物类型
	closable : true,
	modal : true,
	resizable:false,
	goodsTypeEntity:null,//修改货物类型数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :570,
	height :300,
	listeners:{
		beforehide:function(me){
			me.getGoodForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getGoodForm().getForm().loadRecord(new Foss.pricing.GoodEntity(me.goodsTypeEntity));
		}
	},
	//修改货物类型FORM
	goodForm:null,
	getGoodForm : function(){
    	if(Ext.isEmpty(this.goodForm)){
    		this.goodForm = Ext.create('Foss.pricing.GoodForm');
    	}
    	return this.goodForm;
    },
    //修改货物类型
    commitGood:function(){
    	var me = this;
    	if(me.getGoodForm().getForm().isValid()){//校验form是否通过校验
    		var goodModel = new Foss.pricing.GoodEntity(me.goodsTypeEntity);
    		me.getGoodForm().getForm().updateRecord(goodModel);//将FORM中数据设置到MODEL里面
    		goodModel.set('active',pricing.no);
    		var params = {'goodVo':{'goodsTypeEntity':goodModel.data}};//组织新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.good.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updateGoodsType.action');//请求货物类型修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.good.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.good.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getGoodForm().getForm().loadRecord(new Foss.pricing.GoodEntity(me.goodsTypeEntity));
			} 
		},{
			text : pricing.good.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitGood();
			} 
		}];
		me.items = [ me.getGoodForm()];
		me.callParent([cfg]);
	}
});
/**
 * 货物类型组-FORM
 */
Ext.define('Foss.pricing.GoodForm', {
	extend : 'Ext.form.Panel',
	title : pricing.good.i18n('foss.pricing.cargoTypeInformation'),//货物类型信息
	frame: true,
	isUpdate:false,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	//width:200,
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
			name: 'name',//类型名称
			allowBlank:false,
	        fieldLabel: pricing.good.i18n('foss.pricing.typeTheNameOf'),
	        colspan : 2,
	        maxLength:50,
	        xtype : 'textfield'
		},{
			name: 'mark',//备注
	        fieldLabel: pricing.good.i18n('foss.pricing.remark'),
	        colspan : 2,
	        maxLength:200,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});

