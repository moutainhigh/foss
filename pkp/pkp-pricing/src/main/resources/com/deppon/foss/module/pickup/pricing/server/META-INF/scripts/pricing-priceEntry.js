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
  
/**查询归集类别数据源**/
pricing.priceEntry.priceEntryEntitys = [];
pricing.priceEntry.queryPriceEntryEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findParentPriceEntry.action'),//查询归集类别
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.priceEntry.priceEntryEntitys = result.priceEntryVo.priceEntryList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.priceEntry.showErrorMes('请求超时');
			}else{
				pricing.priceEntry.showErrorMes(result.message);
			}
		}
	});
};


//--------------------------------------pricing----------------------------------------
//计价条目
Ext.define('Foss.pricing.priceEntry.PriceEntryModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'name', //计价条目名称
        type : 'string'
    },{
    	name : 'code', //计价条目编码
        type : 'string'
    },{
    	name : 'refId',  //上级ID
    	type : 'string'
    },{
    	name : 'refCode',  //上级code
    	type : 'string'
    },{
    	name : 'refName',  //上级名称
    	type : 'string'
    },{
    	name : 'remarks', //备注
    	type : 'string'
     },{
     	name : 'modifyUserName',//最后修改人
        type : 'string'
     },{ 
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createOrgCode',//创建部门code
        type : 'string'
    },{
        name : 'modifyOrgCode',//修改部门code
        type : 'string'
    },{
        name : 'blongPricingId',//归集类别ID
        type : 'string'
    },{
        name : 'blongPricingCode',//归集类别code
        type : 'string'
    },{
        name : 'blongPricingName',//归集类别名称
        type : 'string'
    },{
    	name : 'receiveDate',//营业日期
    	type : 'date'
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 增值服务其他费用 store
 */
Ext.define('Foss.pricing.priceEntry.PriceEntryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.priceEntry.PriceEntryModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchPriceEntryByCondition.action'),//请求地址
		reader : {
			type : 'json',
			root : 'priceEntryVo.priceEntryList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//----------------------------------------store---------------------------------

Ext.define('Foss.pricing.priceEntry.QueryPriceEntryForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceEntry.i18n('foss.pricing.priceEntryQtQueryForm'), 
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
	        fieldLabel: pricing.priceEntry.i18n('foss.pricing.priceEntryQtCostName'),//计价条目条目方案名称
	        labelWidth:120,
	        xtype : 'textfield'
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : pricing.priceEntry.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.priceEntry.i18n('i18n.pricingRegion.search'),//查询
			disabled: !pricing.priceEntry.isPermission('priceEntry/priceEntryQueryButton'),
			hidden: !pricing.priceEntry.isPermission('priceEntry/priceEntryQueryButton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getPriceEntryGrid().getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 货物类型列表
 */
Ext.define('Foss.pricing.priceEntry.PriceEntryGird', {
	extend: 'Ext.grid.Panel',
	title : pricing.priceEntry.i18n('foss.pricing.priceEntryQtQueryGird'),//货物类型信息
	frame: true,
	flex:1,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.priceEntry.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//其他费用条目新增WINDOW
	priceEntryAddWindow:null,
	getPriceEntryAddWindow:function(){
		if (Ext.isEmpty(this.priceEntryAddWindow)) {
			this.priceEntryAddWindow = Ext.create('Foss.pricing.priceEntry.PriceEntryAddWindow');
			this.priceEntryAddWindow.parent = this;//父元素
		}
		return this.priceEntryAddWindow;
	},
	//其他费用修改WINDOW
	priceEntryUpdateWindow:null,
	getPriceEntryUpdateWindow:function(){
		if (Ext.isEmpty(this.priceEntryUpdateWindow)) {
			this.priceEntryUpdateWindow = Ext.create('Foss.pricing.priceEntry.PriceEntryUpdateWindow');
			this.priceEntryUpdateWindow.parent = this;//父元素
		}
		return this.priceEntryUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.priceEntry.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.priceEntry.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.priceEntry.i18n('foss.pricing.update'),//修改
                disabled: !pricing.priceEntry.isPermission('priceEntry/priceEntryUpdateButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					    return 'deppon_icons_edit';
				},
                handler: function(grid,rowIndex,colIndex) {
    				var record = grid.getStore().getAt(rowIndex);
                	var id= record.get('id'); 
    				var params = {'priceEntryVo':{'priceEntity':{'id':id}}};
    				var successFun = function(json){
    					var priceEntryUpdateWindow = me.getPriceEntryUpdateWindow(); 
    					priceEntryUpdateWindow.priceEntry = json.priceEntryVo.priceEntity; 
    					priceEntryUpdateWindow.show(); 
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceEntry.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('findPriceEntryById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : '编码',
			dataIndex : 'code'
		},{
			text : pricing.priceEntry.i18n('foss.pricing.priceEntryQtCostName'),//货物类型名称
			dataIndex : 'name',
			width:80
		},{
			text : pricing.priceEntry.i18n('foss.pricing.lastModifiedUser'),//最后修改人
			dataIndex : 'modifyUserName',
			width:100
		},{
			text : pricing.priceEntry.i18n('foss.pricing.lastModifiedTime'),//最后修改时间
			dataIndex : 'modifyDate',
			width:100,
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.priceEntry.i18n('foss.pricing.priceEntryQtParentName'),//所属父类名称
			dataIndex : 'refName',
			width:100
		},{
			text : '归集类别',//所属父类名称
			dataIndex : 'blongPricingName',
			width:100
		},{
			text : pricing.priceEntry.i18n('foss.pricing.remark'),//类型描述
			dataIndex : 'remarks',
			width:120
		}];
		me.store = Ext.create('Foss.pricing.priceEntry.PriceEntryStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryPriceEntryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : { 
								'priceEntryVo.priceEntity.name':queryForm.getForm().findField('name').getValue()//名称
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
			text : pricing.priceEntry.i18n('i18n.pricingRegion.add'),//新增
			disabled: !pricing.priceEntry.isPermission('priceEntry/priceEntryAddButton'),
			hidden: !pricing.priceEntry.isPermission('priceEntry/priceEntryAddButton'),
			handler :function(){
				me.getPriceEntryAddWindow().show();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

/**
 * @description 货物类型主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-priceEntry_content')) {
		return;
	};
	pricing.priceEntry.queryPriceEntryEntityList();
	var queryPriceEntryForm = Ext.create('Foss.pricing.priceEntry.QueryPriceEntryForm');//查询FORM
	var priceEntryGird = Ext.create('Foss.pricing.priceEntry.PriceEntryGird');//查询结果GRID
	Ext.getCmp('T_pricing-priceEntry').add(Ext.create('Ext.panel.Panel',{
		id : 'T_pricing-priceEntry_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryPriceEntryForm : function() {
			return queryPriceEntryForm;
		},
		//获得查询结果GRID
		getPriceEntryGrid : function() {
			return priceEntryGird;
		},
		items : [queryPriceEntryForm, priceEntryGird]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * INSERT 
 */
Ext.define('Foss.pricing.priceEntry.PriceEntryAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceEntry.i18n('foss.pricing.priceEntryQtWindowAdd'),//新增货物类型
	closable : true,
    parent:null, 
	modal : true,
	resizable:true, 
	closeAction : 'hide', 
	width :570,
	height :420,
	listeners:{
		beforehide:function(me){ 
			me.getPriceEntryForm().getForm().reset(); 
		},
		beforeshow:function(me){ 
			
		}
	},
	//新增货物类型FORM
	priceEntryForm:null,
	getPriceEntryForm : function(){
    	if(Ext.isEmpty(this.priceEntryForm)){
    		this.priceEntryForm = Ext.create('Foss.pricing.priceEntry.PriceEntryForm');
    	}
    	return this.priceEntryForm;
    },
    //提交货物类型数据
    commitPriceEntry:function(){
    	var me = this;
    	if(me.getPriceEntryForm().getForm().isValid()){//校验form是否通过校验
    		var priceEntryModel = new Foss.pricing.priceEntry.PriceEntryModel();
    		me.getPriceEntryForm().getForm().updateRecord(priceEntryModel); 
    		var params = {'priceEntryVo':{'priceEntity':priceEntryModel.data}}; 
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceEntry.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('addPriceEntry.action');//请求货物类型新增
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.priceEntry.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.priceEntry.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getPriceEntryForm().getForm().loadRecord(new Foss.pricing.priceEntry.PriceEntryModel());
			} 
		},{
			text : pricing.priceEntry.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitPriceEntry();
			} 
		}];
		me.items = [me.getPriceEntryForm()];
		me.callParent([cfg]);
	}
});
/**
 * 修改其他费用
 */
Ext.define('Foss.pricing.priceEntry.PriceEntryUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.priceEntry.i18n('foss.pricing.priceEntryQtWindowEdit'),//修改其他费用信息
	closable : true,
	modal : true,
	resizable:false,
	priceEntry:null, 
	parent:null, 
	closeAction : 'hide',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :570,
	height :420,
	listeners:{
		beforehide:function(me){
			me.getPriceEntryForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){
			me.getPriceEntryForm().getForm().loadRecord(new Foss.pricing.priceEntry.PriceEntryModel(me.priceEntry));
			me.getPriceEntryForm().getForm().findField('code').setReadOnly(true);
		}
	},
	//修改计价条目FORM
	priceEntryForm:null,
	getPriceEntryForm : function(){
    	if(Ext.isEmpty(this.priceEntryForm)){
    		this.priceEntryForm = Ext.create('Foss.pricing.priceEntry.PriceEntryForm');
    	}
    	return this.priceEntryForm;
    },
    //修改计价条目
    commitPriceEntry:function(){
    	var me = this;
    	if(me.getPriceEntryForm().getForm().isValid()){
    		var priceEntryModel = new Foss.pricing.priceEntry.PriceEntryModel(me.priceEntry);
    		me.getPriceEntryForm().getForm().updateRecord(priceEntryModel);
    		var params = {'priceEntryVo':{'priceEntity':priceEntryModel.data}};
    		var successFun = function(json){
				pricing.showInfoMes(json.message);
				me.close();
				me.parent.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceEntry.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = pricing.realPath('updatePriceEntry.action');//请求货物类型修改
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			text :pricing.priceEntry.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			}
		},{
			text : pricing.priceEntry.i18n('foss.pricing.reset'),//重置
			handler :function(){
					me.getPriceEntryForm().getForm().loadRecord(new Foss.pricing.priceEntry.PriceEntryModel(me.priceEntry));
			} 
		},{
			text : pricing.priceEntry.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 305',
			handler :function(){
				me.commitPriceEntry();
			} 
		}];
		me.items = [me.getPriceEntryForm()];
		me.callParent([cfg]);
	}
});
/**
 * 计价条目-FORM
 */
Ext.define('Foss.pricing.priceEntry.PriceEntryForm', {
	extend : 'Ext.form.Panel',
	title : pricing.priceEntry.i18n('foss.pricing.priceEntryQtInfo'),
	frame: true,
	isUpdate:false,
	flex:1,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:80,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 3
	},
    constructor : function(config) {
			var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'name',//名称
			allowBlank:false,
	        fieldLabel: pricing.priceEntry.i18n('foss.pricing.priceEntryQtCostName'),
	        colspan : 3,
	        maxLength:50,
	        xtype : 'textfield'
		},{
	        name: 'code',//编码
			allowBlank:false,
			maxLength:50,
			colspan : 2,
	        fieldLabel: pricing.priceEntry.i18n('foss.pricing.coding'),
	        xtype : 'textfield'	     
		},{
			xtype: 'displayfield',
			value:"<font color='red'>"+pricing.priceEntry.i18n('foss.pricing.remarking')+"</font>"//备注：家装类货物费用编码需特殊设置
		},{
			name: 'blongPricingCode',//父级编码
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    colspan : 3,
		    editable:false,
		    allowBlank:false,
		    store:pricing.getStore(null,null,['code','name'],pricing.priceEntry.priceEntryEntitys),
	        fieldLabel:'归集类别',//所属归集类别
	        xtype : 'combo'
		
		},{
			name: 'remarks',//备注
	        fieldLabel: pricing.priceEntry.i18n('foss.pricing.remark'),
	        colspan : 3,
	        maxLength:200,
	        width:400,
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});