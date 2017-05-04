/**
 * 汽运价格表表头管理
 * @author 094463-foss-xieyantao
 * @date 2014-1-14 下午3:08:57
 */

//转换long类型为日期
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

//消息提醒框
pricing.priceReportTitle.showWarningMsg = function(title,message,fun){
		Ext.Msg.show({
		    title:title,
		    msg:message,
		    width:120,
		    buttons: Ext.Msg.OK,
		    icon: Ext.MessageBox.WARNING,
		    callback:function(e){
		    	if(!Ext.isEmpty(fun)){
		    		if(e=='ok'){
			    		fun();
			    	}
		    	}
		    }
		});
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
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
pricing.tomorrowTime = null;
pricing.goodsTypeFlightPlan = [];//货物类型
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTimeA.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceReportTitle.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.priceReportTitle.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};

//--------------------------------------pricing----------------------------------------
//汽运价格表表头信息
Ext.define('Foss.pricing.PartbussPlanEntity', {
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
        name : 'createUserName',//创建人姓名
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
        name : 'serialNo',/* 序号 */
        type : 'string'
    },{
        name : 'header',/* 标题 */
        type : 'string'
    },{
        name : 'details', /* 内容 */
        type : 'string'
    },{
        name : 'isShow',/* 是否显示（Y/N） */
        type : 'string'
    }
  //===============新增'是否合伙人营业部'/20160918/lianhe/开始==================
    ,{
        name : 'isPartner',/* 是否合伙人营业部（Y/N） */
        type : 'string'
    }
  //===============新增'是否合伙人营业部'/20160918/lianhe/截止==================
    ,{
        name : 'active',/* 是否启用(Y/N) */
        type : 'string'
    },{
    	name : 'versionNo',/*是否最新版本*/
    	type : 'number'
    }]
});

//------------------------------------model---------------------------------------------------



/**
 * 汽运价格表表头Store（Foss.pricing.PartbussPlanEntity）
 */
Ext.define('Foss.pricing.PartbussPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.PartbussPlanEntity',//汽运价格表表头的MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : '../pricing/queryAllReportTitles.action',//请求地址
		reader : {
			type : 'json',
			root : 'vo.priceReportTitleList',//获取的数据
			totalProperty : 'totalCount'//总个数
		}
	}
});

//创建一个本地的类型store
Ext.define('Foss.pricing.priceReportTitle.TypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//----------------------------------------store---------------------------------

/**
 * 汽运价格表表头查询条件FORM
 */
Ext.define('Foss.pricing.QueryPartbussPlanForm', {
	extend : 'Ext.form.Panel',
	title: pricing.priceReportTitle.i18n('i18n.pricingRegion.searchCondition'),//查询条件
	frame: true,
	collapsible: true,
	layout:{
		type:'table',
		columns: 3
	},
    defaults : {
    	colspan: 1,
		margin : '8 10 8 10',
		anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			xtype : 'textfield',
			name: 'serialNo',//序号
			maxLength:20,
			fieldLabel : '序号'
		},{
			xtype : 'textfield',
			maxLength:50,
			name: 'header',//重要提示
			fieldLabel : '重要提示'
		}],
		me.fbar = [{
			xtype : 'button', 
			width:70,
			left : 1,
			text : pricing.priceReportTitle.i18n('foss.pricing.reset'),//重置
			margin:'0 825 0 0',
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : pricing.priceReportTitle.i18n('i18n.pricingRegion.search'),//查询
//			hidden: !pricing.flightPrice.isPermission('flightPrice/flightPriceQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					var grid = Ext.getCmp('T_pricing-priceReportTitleIndex_content').getFlightPriceGrid();
					grid.getPagingToolbar().moveFirst();
				}
				
			}
		}];
		me.callParent([cfg]);
	}
});


/**
 * 汽运价格表表头列表
 */
Ext.define('Foss.pricing.PartbussPlanGrid', {
	extend: 'Ext.grid.Panel',
	title : '汽运价格表表头列表',//汽运价格表表头列表
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.priceReportTitle.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				prependButtons: true,
				defaults : {
					margin : '0 0 15 3'
				}
			});
		}
		return this.pagingToolbar;
	},
	//汽运价格表表头新增WINDOW
	flightPriceAddWindow:null,
	getFlightPriceAddWindow:function(){
		if (this.flightPriceAddWindow == null) {
			this.flightPriceAddWindow = Ext.create('Foss.pricing.PartbussPlanAddWindow');
			this.flightPriceAddWindow.parent = this;//父元素
		}
		return this.flightPriceAddWindow;
	},
	//修改汽运价格表表头WINDOW
	flightPriceUpdateWindow:null,
	getFlightPriceUpdateWindow:function(){
		if (this.flightPriceUpdateWindow == null) {
			this.flightPriceUpdateWindow = Ext.create('Foss.pricing.PartbussPlanUpdateWindow');
			this.flightPriceUpdateWindow.parent = this;//父元素
		}
		return this.flightPriceUpdateWindow;
	},
	//查看汽运价格表表头
	flightPriceShowWindow:null,
	getFlightPriceShowWindow:function(){
		if (this.flightPriceShowWindow == null) {
			this.flightPriceShowWindow = Ext.create('Foss.pricing.PartbussPlanShowWindow');
			this.flightPriceShowWindow.parent = this;//父元素
		}
		return this.flightPriceShowWindow;
	},
	//修改信息
	updateInfo: function(){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.priceReportTitle.i18n('请选择一条要修改的信息！'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		if(selections.length>1){
			pricing.showWoringMessage(pricing.priceReportTitle.i18n('只能选择一条信息进行修改，请重新选择！'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		for(var i = 0 ; i<selections.length ; i++){
			var id = selections[i].get('id');
			var params = {'vo':{'priceReportTitle':{'id':id}}};
			var successFun = function(json){
				var updateWindow = me.getFlightPriceUpdateWindow();
				updateWindow.partbussPlan = json.vo.priceReportTitle;
				updateWindow.show();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceReportTitle.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);
				}
			};
			var url = pricing.realPath('queryReportTitleById.action');
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	//作废汽运价格表表头
	deleteFlightPrice: function(btn){
		var me = this;
		var selections = me.getSelectionModel().getSelection();//获取选中的数据
		if(selections.length<1){//判断是否至少选中了一条
			pricing.showWoringMessage(pricing.priceReportTitle.i18n('foss.pricing.pleaseSelectVoidOperation'));//请选择一条进行作废操作！
			return;//没有则提示并返回
		}
		for(var i = 0;i<selections.length;i++){
			if(selections[i].get('active')==pricing.yes){
				pricing.showWoringMessage(pricing.priceReportTitle.i18n('foss.pricing.pleaseChooseToNotActivateTheDataToBeBeleted'));//请选择未激活数据进行删除！
				return;//没有则提示并返回
			}
		}
		pricing.showQuestionMes(pricing.priceReportSearch.i18n('i18n.pricingRegion.isDeleteTheseExpressDeliveryAgencyFreight'),function(e){//是否要删除这些快递代理运价？
			if(e=='yes'){//询问是否删除，是则发送请求
				var idList = new Array();//汽运价格表表头
				for(var i = 0 ; i<selections.length ; i++){
					idList.push(selections[i].get('id'));
				}
				var params = {'pricePlanVo':{'idList':idList}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.priceReportTitle.i18n('foss.pricing.requestTimedOut'));//请求超时
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = '../pricing/deleteFlightPriceA.action';
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [
			{dataIndex : 'serialNo',
			width:40,
			text : pricing.priceReportTitle.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.priceReportTitle.i18n('i18n.pricingRegion.opra'),//操作
			//dataIndex : 'id',
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.priceReportTitle.i18n('foss.pricing.update'),//修改
                disabled: !pricing.priceReportTitle.isPermission('priceReportTitle/updatebutton'),
				width:39,
                handler: function(grid, rowIndex, colIndex) {
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'vo':{'priceReportTitle':{'id':id}}};
    				var successFun = function(json){
    					var updateWindow = me.getFlightPriceUpdateWindow();
    					updateWindow.partbussPlan = json.vo.priceReportTitle;
    					updateWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceReportTitle.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryReportTitleById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.priceReportTitle.i18n('foss.pricing.details'),//查看详情
//                disabled: !pricing.flightPrice.isPermission('flightPrice/flightPriceDetailbutton'),
				width:39,
                handler: function(grid, rowIndex, colIndex) {
                	
                	var id = grid.getStore().getAt(rowIndex).get('id');
    				var params = {'vo':{'priceReportTitle':{'id':id}}};
    				var successFun = function(json){
    					var showWindow = me.getFlightPriceShowWindow();
    					showWindow.partbussPlan = json.vo.priceReportTitle;
    					showWindow.show();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.priceReportTitle.i18n('foss.pricing.requestTimedOut'));//请求超时
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryReportTitleById.action');
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text : '重要提示',//汽运价格表表头
			dataIndex : 'header',
			width:100
		},{
			text : '提示内容',//提示内容
			dataIndex : 'details',
			flex:1
		},{
			text : '修改时间',//修改时间
			dataIndex : 'modifyDate',
			width:150,
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.priceReportTitle.i18n('foss.pricing.modifyUser'),//修改人
			dataIndex : 'modifyUserName',
			width:100
		},{
			text : '是否显示',//是否显示
			dataIndex : 'isShow',
			width:100,
			renderer:function(value){
				if(value==pricing.yes){//'Y'表示激活
					return '是';
				}else if(value==pricing.no){//'N'表示未激活
					return  '否';
				}else{
					return '';
				}
			}
		}
		//===============新增'是否合伙人营业部'/20160918/lianhe/开始==================
		,{
			text : '是否合伙人营业部',//是否合伙人营业部Partner
			dataIndex : 'isPartner',
			width:120,
			renderer:function(value){
				if(value==pricing.yes){//'Y'表示合伙人
					return '是';
				}else if(value==pricing.no){//'N'表示非合伙人
					return  '否';
				}else{
					return '';
				}
			}
		}
		//===============新增'是否合伙人营业部'/20160918/lianhe/截止==================
		];
		me.store = Ext.create('Foss.pricing.PartbussPlanStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up().getQueryFlightPriceForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {//汽运价格表表头大查询，查询条件组织
								'vo.priceReportTitle.serialNo':queryForm.getForm().findField('serialNo').getValue(),//序号
								'vo.priceReportTitle.header':queryForm.getForm().findField('header').getValue()//重要提示
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
			text : pricing.priceReportTitle.i18n('foss.pricing.update'),//修改
			disabled: !pricing.priceReportTitle.isPermission('priceReportTitle/updatebutton'),
			hidden: !pricing.priceReportTitle.isPermission('priceReportTitle/updatebutton'),
			handler :function(){
				me.updateInfo();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		pricing.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------

/**
 * 新增汽运价格表表头信息
 */
Ext.define('Foss.pricing.PartbussPlanAddWindow',{
	extend : 'Ext.window.Window',
	title : '新增汽运价格表表头',//新增汽运价格表表头
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	partbussPlan:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :650,
	height :400,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
//			me.getFlightPriceForm().getForm().reset();//表格重置
//    		me.getFlightPriceForm().getDockedItems()[1].items.items[1].setDisabled(false);//form的重置按钮可用
//    		me.getFlightPriceForm().getDockedItems()[1].items.items[2].setDisabled(false);//form的保存按钮可用
//    		me.getFlightPriceForm().getForm().getFields().each(function(item){
//    			item.setReadOnly(false);
//    		});
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
//			var datet = new Date(2999, 11, 31, 23, 59, 59);
		}
	},
	//新增汽运价格表表头FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.PartbussPlanForm',{
    			'isUpdate':false
    		});
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getFlightPriceForm()];
		me.callParent([cfg]);
	}
});

/**
 * 汽运价格表表头详细信息
 */
Ext.define('Foss.pricing.PartbussPlanShowWindow',{
	extend : 'Ext.window.Window',
	title : '汽运价格表表头信息',//汽运价格表表头详细信息
	closable : true,
    parent:null,//父元素（弹出这个window的gird——Foss.pricing.PartbussPlanGrid）
	modal : true,
	partbussPlan:null,
	resizable:true,//可以调整窗口的大小
	closeAction : 'hide',//点击关闭是隐藏窗口
	width :650,
	height :400,
	listeners:{
		beforehide:function(me){//隐藏WINDOW的时候清除数据
			me.getFlightPriceForm().getForm().reset();//表格重置
		},
		beforeshow:function(me){//显示WINDOW的时候清除数据
			var record = new Foss.pricing.PartbussPlanEntity(me.partbussPlan);
			me.getFlightPriceForm().getForm().loadRecord(record);
		}
	},
	//新增汽运价格表表头FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.PartbussPlanAddForm');
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    //组件构造器
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getFlightPriceForm()];  
		me.callParent([cfg]);
	}
});

/**
 * 修改汽运价格表表头
 */
Ext.define('Foss.pricing.PartbussPlanUpdateWindow',{
	extend : 'Ext.window.Window',
	title : '修改汽运价格表表头',//修改汽运价格表表头
	closable : true,
	modal : true,
	resizable:false,
	partbussPlan:null,//修改汽运价格表表头数据
	parent:null,//父元素（弹出这个window的gird——Foss.pricing.SiteGroupGrid）
	closeAction : 'hide',
	width :650,
	height :400,
	listeners:{
		beforehide:function(me){
			me.getFlightPriceForm().getForm().reset();//表格重置
			/*me.getFlightPriceForm().getForm().getFields().each(function(item){
				item.setReadOnly(false);
			});*/
		},
		beforeshow:function(me){
			var record = new Foss.pricing.PartbussPlanEntity(me.partbussPlan);
			me.getFlightPriceForm().getForm().loadRecord(record);
		}
	},
	//修改汽运价格表表头FORM
	flightPriceForm:null,
	getFlightPriceForm : function(){
    	if(Ext.isEmpty(this.flightPriceForm)){
    		this.flightPriceForm = Ext.create('Foss.pricing.PartbussPlanForm',{
    			'isUpdate':true
    		});
    	}
    	return this.flightPriceForm;
    },
    dateFormat:function(value,format){
		if(!Ext.isEmpty(value)){
			return Ext.Date.format(new Date(value), format);
		}else{
			return null;
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getFlightPriceForm()];
		me.callParent([cfg]);
	}
});
/**
 * 汽运价格表表头组-FORM
 */
Ext.define('Foss.pricing.PartbussPlanForm', {
	extend : 'Ext.form.Panel',
//	title : '汽运价格表表头信息',//汽运价格表表头信息
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
    	allowBlank:false,
    	colspan : 1
    },
    layout:{
		type:'table',
		columns: 2
	},
    //提交数据
    commitFlightPrice:function(){
    	var me = this;
    	if(me.getForm().isValid()){//校验form是否通过校验
    		var flightPriceModel = null;
    		if(me.isUpdate){
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan);
    		}else{
    			flightPriceModel = new Foss.pricing.PartbussPlanEntity();
    		}
    		
    		me.getForm().updateRecord(flightPriceModel);//将FORM中数据设置到MODEL里面
    		var params = {'vo':{'priceReportTitle':flightPriceModel.data}};//新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.up('window').partbussPlan = json.vo.priceReportTitle;//返回数据
				me.up('window').parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				me.up('window').close();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.priceReportTitle.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					pricing.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = null;
			if(me.isUpdate){
				url = pricing.realPath('updateReportTitle.action');//请求汽运价格表表头修改
    		}
			
			pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			colspan : 2,
			name: 'serialNo',//序号
			maxLength:'20',
	        fieldLabel: '序号',
	        xtype : 'textfield',
	        readOnly:true
		},{
			colspan : 2,
			name: 'header',//序号
			maxLength:'50',
	        fieldLabel: '重要提示',
	        xtype : 'textfield'
		},{
			colspan : 2,
			name: 'details',//备注描述
	        fieldLabel: '提示内容',
	        maxLength:'200',
	        colspan : 2,
	        width:400,
	        xtype : 'textareafield'
		},{
			colspan : 2,
			name: 'isShows',
	        fieldLabel: '是否显示',
	        allowBlank:false,
	        xtype : 'radiogroup',
	        layout:'column',
			defaultType: 'radio',
			defaults:{
 			width:100
 			},
			items: [{ 
					boxLabel  : '是', 
					columnWidth:.5,
					name      : 'isShow',
					inputValue: 'Y',
					checked   : true
				}, { 
					boxLabel  : '否', 
					columnWidth:.5,
					name      : 'isShow',
					inputValue: 'N'
			}]
		}];
		me.fbar = [{
			text :pricing.priceReportTitle.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.up('window').close();
			}
		},{
			text : pricing.priceReportTitle.i18n('foss.pricing.reset'),//重置
			handler :function(){
				var partbussPlan =  me.up('window').partbussPlan;
				if(Ext.isEmpty(partbussPlan)){
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity());
				}else{
					me.getForm().loadRecord(new Foss.pricing.PartbussPlanEntity(me.up('window').partbussPlan));
				}
					
			} 
		},{
			text : pricing.priceReportTitle.i18n('foss.pricing.save'),//保存
			cls:'yellow_button',
			margin:'0 0 0 275',
			handler :function(){
				me.commitFlightPrice();
			} 
		}];
		me.callParent([cfg]);
	}
});


/**
 * 汽运价格表表头组-FORM
 */
Ext.define('Foss.pricing.PartbussPlanAddForm', {
	extend : 'Ext.form.Panel',
//	title : '汽运价格表表头信息',//汽运价格表表头信息
	frame: true,
	height:290,
	collapsible: true,
    defaults : {
    	margin : '5 5 5 5',
    	labelWidth:120,
//    	allowBlank:false,
    	readOnly:true,
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
			colspan : 2,
			name: 'serialNo',//序号
			maxLength:'20',
	        fieldLabel: '序号',
	        xtype : 'textfield'
		},{
			colspan : 2,
			name: 'header',//序号
			maxLength:'50',
	        fieldLabel: '重要提示',
	        xtype : 'textfield'
		},{
			colspan : 2,
			name: 'details',//备注描述
			maxLength:'200',
	        fieldLabel: '提示内容',
	        colspan : 2,
	        width:400,
	        xtype : 'textareafield'
		},{
			text : '是否显示',//是否显示
			dataIndex : 'isShow',
			width:100,
			renderer:function(value){
				if(value==pricing.yes){//'Y'表示激活
					return '是';
				}else if(value==pricing.no){//'N'表示未激活
					return  '否';
				}else{
					return '';
				}
			}
		}];
		me.callParent([cfg]);
	}
});

/**
 * @description 汽运价格表表头管理主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-priceReportTitleIndex_content')) {
		return;
	};
	var queryFlightPriceForm = Ext.create('Foss.pricing.QueryPartbussPlanForm');//查询FORM
	var flightPriceGrid = Ext.create('Foss.pricing.PartbussPlanGrid');//查询结果GRID
	Ext.getCmp('T_pricing-priceReportTitleIndex').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-priceReportTitleIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryFlightPriceForm : function() {
			return queryFlightPriceForm;
		},
		//获得查询结果GRID
		getFlightPriceGrid : function() {
			return flightPriceGrid;
		},
		items : [queryFlightPriceForm, flightPriceGrid]
	}));
});
