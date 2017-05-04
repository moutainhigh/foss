pricing.changeLongToDate = function(value){
	if(value!=null){
		var date = new Date(value);
		return date;
	}else{
		return null;
	}
};
pricing.inempDiscount.tomorrowTime ;
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.inempDiscount.tomorrowTime = today.setDate(today.getDate()+1);
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
			failFn(result);
		}
	});
};
//JSON格式的Ajax请求服务
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
//内部员工折扣方案MODEL
Ext.define('Foss.pricing.inempDiscount.inempDiscountPlanEntity', {
    extend: 'Ext.data.Model',
    fields : [{
		name: 'id',//方案id
		type: 'string'
	},{
        name : 'code',  //内部员工折扣方案编码
        type : 'string'
    },{
        name : 'chargeRebate', //运费折扣
        defaultValue : null
    },{
        name : 'highstPreferentialLimit',//最高优惠限额
        defaultValue : null
    },{
        name : 'beginTime',//优惠开始时间
        type : 'date',
        defaultValue : null,
        convert:pricing.changeLongToDate
    },{
        name : 'endTime',//优惠结束时间
        type : 'date',
        defaultValue : null,
        convert:pricing.changeLongToDate
    },{
        name : 'active',//数据状态
        type : 'string'
    },{
        name : 'remark',//备注
        type : 'string'
    },{
    	name : 'createTime',//创建方案时间
    	type : 'date',
        defaultValue : null,
        convert:pricing.changeLongToDate
    },{
    	name : 'modifyTime',//修改方案时间
    	type : 'date',
        defaultValue : null,
        convert:pricing.changeLongToDate
    },{
        name : 'createUserCode',//创建人编码
        type : 'string'
    },{
        name : 'modifyUserCode',//更改人编码
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名 
        type : 'string'
    },{
        name : 'createOrgCode',//创建部门
        type : 'string'
    },{
        name : 'modifyOrgCode',//更新部门
        type : 'string'
    },{
    	name : 'billTime', //业务时间
    	type : 'date',
        defaultValue : null,
        convert:pricing.changeLongToDate
    },{
    	name : 'currentUsedVersion', //是否当前版本
    	type : 'string'
    }]
});
//------------------------------------model---------------------------------------------------
/**
 * 内部员工折扣方案Store
 */
Ext.define('Foss.pricing.inempDiscount.inempDiscountPlanStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.inempDiscount.inempDiscountPlanEntity',//内部员工折扣方案MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('queryInempDiscountPlanList.action'),
		reader : {
			type : 'json',
			root : 'inempDiscountPlanVo.inempDiscountPlanEntityList',
			totalProperty:'totalCount'
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
 * 内部员工折扣方案查询表单
 */
Ext.define('Foss.pricing.inempDiscount.QueryInempDiscountForm', {
	extend : 'Ext.form.Panel',
	title: pricing.inempDiscount.i18n('foss.pricing.inempDiscount.inempDiscountQueryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :100,
	layout:'column',
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.fbar=[{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 825',
			text:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.query'),//'查询',
			//hidden: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountQuerybutton'),
			handler:function(){
				
					var grid = Ext.getCmp('T_pricing-inempDiscount_content').getAreaGrid();
					grid.getStore().load();
				}
			}];
		me.callParent([cfg]);
	}
});
/**
 * 内部员工折扣方案列表
 */
Ext.define('Foss.pricing.inempDiscount.InempDiscountGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.inempDiscount.i18n('i18n.pricingRegion.searchResults'),
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.inempDiscount.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//删除快递折扣主方案信息
	deleteInempDiscount:function(){
		var me = this;
		var inempDiscountPlanIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.pleaseDeleteInActiveInempDiscountPlan'));//请选择要删除的未激活折扣方案！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的折扣方案才可以删除
				pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.pleaseDeleteInActiveInempDiscountPlan'));//请选择要删除的未激活折扣方案！
				return;
			}else if(selections[i].get('active')=='N'){
				inempDiscountPlanIds.push(selections[i].get('id'));
			}else{
				inempDiscountPlanIds.push(selections[i].get('id'));
			}
		}
		if(inempDiscountPlanIds.length<1){
			pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.pleaseChooseLeastOneDiscountPlan'));//请至少选择一条未激活的折扣方案！
			return;
		}
		pricing.showQuestionMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.ifDeleteTheseInAcitveDiscountPlan'),function(e){//是否要删除这些未激活的折扣方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'inempDiscountPlanVo':{'inempDiscountPlanIds':inempDiscountPlanIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.inempDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteInempDsicountPlan.action'); //删除记录ACTION
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//内部员工折扣方案新增Window
	inempDiscountAddWindow:null,
	getInempDiscountAddWindow:function(){
		if(Ext.isEmpty(this.inempDiscountAddWindow)){
			this.inempDiscountAddWindow = Ext.create('Foss.pricing.inempDiscount.InempDiscountAddWindow');//定义的新增内部员工折扣方案的面板
			this.inempDiscountAddWindow.parent = this;
		}
		return this.inempDiscountAddWindow;
	},
	//内部员工折扣方案修改Window
	inempDiscountUpdateWindow : null,
	getInempDiscountUpdateWindow : function() {
		if (Ext.isEmpty(this.inempDiscountUpdateWindow)) {
			this.inempDiscountUpdateWindow = Ext.create('Foss.pricing.inempDiscount.InempDiscountEditorWindow');//内部员工折扣方案修改面板定义
			this.inempDiscountUpdateWindow.parent = this;
		}
		return this.inempDiscountUpdateWindow;
	},
	
	//查看详情
	inempDiscountShowWindow:null,
	getInempDiscountShowWindow : function() {
		if (Ext.isEmpty(this.inempDiscountShowWindow)) {
			this.inempDiscountShowWindow = Ext.create('Foss.pricing.inempDiscount.InempDiscountShowWindow');
			this.inempDiscountShowWindow.parent = this;
		}
		return this.inempDiscountShowWindow;
	},
	
	 /**
     * 立即激活
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
	 immediatelyInempDiscountActiveWindow:null,
	 getImmediatelyInempDiscountActiveWindow: function(){
			if(Ext.isEmpty(this.immediatelyInempDiscountActiveWindow)){
				this.immediatelyInempDiscountActiveWindow = Ext.create('Foss.pricing.inempDiscount.ImmediatelyActiveTimeWindow');
				this.immediatelyInempDiscountActiveWindow.parent = this;
			}
			return this.immediatelyInempDiscountActiveWindow;
		},

	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopWindow:null,
	getImmediatelyStopWindow : function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.inempDiscount.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	
	/**
	 * 立即激活
	 */
	immediatelyInempDiscountActive:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneDiscountPlan'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneDiscountPlan'));
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.expressDiscount.pleaseChooseOneInActiveDiscountPlan'));
	 		return;
	 	}else{
	 		var inempDiscountEntity = selections[0].data;
	 		me.getImmediatelyInempDiscountActiveWindow().inempDiscountPlanEntity = inempDiscountEntity;
	 		me.getImmediatelyInempDiscountActiveWindow().show();
	 	}
	},
	/**
	 * 立即中止
	 */
    immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.selectOneRecordToOp'));
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.selectOneActiveRecordToOp'));
	 		return;
	 	}else{
	 		var inempDiscountEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().inempDiscountPlanEntity = inempDiscountEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	
	//GIRD的构造函数
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{
			xtype: 'rownumberer',
			width:40,
			text : pricing.inempDiscount.i18n('i18n.pricing.rowNumber'),//'序号'
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.inempDiscount.i18n('i18n.pricingRegion.opra'),//'操作',
			items:[{
				//修改操作
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.inempDiscount.i18n('foss.pricing.update'),//'修改',
                disabled: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountUpdatebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
				handler:function(grid,rowIndex,colIndex){
					//获取选中的操作的行的数据信息
					var record = grid.getStore().getAt(rowIndex);
					var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':{'id':record.get('id')}}};
					var successFun = function(json){
						//获取要修改的行的数据信息
						var inempDiscountEntity = json.inempDiscountPlanVo.inempDiscountPlanEntity;
						//操作
						grid.up().getInempDiscountUpdateWindow().inempDiscountPlanEntity = inempDiscountEntity;//设置折扣方案主信息                        
                        grid.up().getInempDiscountUpdateWindow().show();//显示window
    	
					};
					var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.inempDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryInempDiscountPlanById.action'); //根据选中的ID查询
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                } 
			},{ //升级版本的操作 
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.inempDiscount.i18n('foss.pricing.upgradedVersion'),//'升级版本',
                disabled: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountUpgradebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
				 handler: function(grid, rowIndex, colIndex) {
	                	var record = grid.getStore().getAt(rowIndex);
	                	var inempDiscountEntity = record.data;
	                	var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':inempDiscountEntity}};
	    				var successFun = function(json){
	    					pricing.showInfoMes(json.message);
	    					grid.up().getPagingToolbar().moveFirst();
	    					};
	    				var failureFun = function(json){
	    					if(Ext.isEmpty(json)){
	    						pricing.showErrorMes(pricing.inempDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
	    					}else{
	    						pricing.showErrorMes(json.message);
	    					}
	    				};
	    				var url = pricing.realPath('upgradeInempDiscountPlan.action'); //升级方案操作
	    				pricing.requestJsonAjax(url,params,successFun,failureFun);
	                }
			},{
				//查询详情的操作
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.inempDiscount.i18n('foss.pricing.details'),//'查看详情',
                disabled: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	//封装JSON格式
                	var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					//如果操作成功；获取返回的数据
    					var inempDiscountEntity = json.inempDiscountPlanVo.inempDiscountPlanEntity;
                        grid.up().getInempDiscountShowWindow().inempDiscountPlanEntity = inempDiscountEntity;//设置折扣方案主信息                      
                        grid.up().getInempDiscountShowWindow().show();//显示window
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						pricing.showErrorMes(pricing.inempDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    					}else{
    						pricing.showErrorMes(json.message);
    					}
    				};
    				var url = pricing.realPath('queryInempDiscountPlanById.action'); //选中数据后的查询语句
    				pricing.requestJsonAjax(url,params,successFun,failureFun);
                }
			}]
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.code'),//'客户编码',
			dataIndex:'code'
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.state'),//'数据状态',
			dataIndex:'active',
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.inempDiscount.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.inempDiscount.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.modifyUser'),//'创建人',
			dataIndex:'modifyUserName'
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.modifyTime'),//'修改时间',
			dataIndex:'modifyTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//'开始时间',
			dataIndex:'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
			dataIndex:'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.isCurrentUsedVersion'),
			dataIndex:'currentUsedVersion',
			renderer:function(value){
				if(value=='Y'){//'Y'表示是
					return "是";
				}else if(value=='N'){//'N'表示否
					return  "否";
				}else{
					return '';
				}
			}
		},{
			text:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.remark'),//备注
			dataIndex:'remark'
		}];
		//通过定义的Store查询数据
		me.store = Ext.create('Foss.pricing.inempDiscount.inempDiscountPlanStore',{
			autoLoad : false,
			pageSize:20,
			listeners:{
				beforeload:function(store,operation,eOpts){
					//在主界面中定义的查询的表单，在OnReady的时候加载，并赋值
					
						Ext.apply(operation,{
							params:{
								'inempDiscountPlanVo.inempDiscountPlanEntity.code':null//方案编码 
							}
						});
					
				}
			}
			
		});
		me.listeners = {//消除出现滚动条之后，却不能用的BUG
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		    },
		
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//带选择框
						mode:'MULTI',
						checkOnly:true
			});
		
		//添加头部按钮
		me.tbar = [{
			//新增
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.addDiscountPlan'),//'新增',
			disabled: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountAddbutton'),
			hidden: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountAddbutton'),
			handler :function(){
				me.getInempDiscountAddWindow().show();
			} 
		},'-', {
			//删除
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.deleteDiscountPlan'),//'删除',
			disabled: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountDeletebutton'),
			hidden: !pricing.inempDiscount.isPermission('inempDiscount/inempDiscountDeletebutton'),
			handler :function(){
				me.deleteInempDiscount();
			} 
		},'-', {
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.immediatelyActiveDiscountPlan'),//'立即激活',
			//hidden:!pricing.inempDiscount.isPermission('expressDiscount/expressDiscountImmediatelyActivebutton'),
			handler :function(){
				me.immediatelyInempDiscountActive();
			} 
		},'-', {
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.immediatelyStopDiscountPlan'),//'立即中止',
			disabled:!pricing.inempDiscount.isPermission('inempDiscount/inempDiscountImmediatelyStopbutton'),
			hidden:!pricing.inempDiscount.isPermission('inempDiscount/inempDiscountImmediatelyStopbutton'),
			handler :function(){
				me.immediatelyStop();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});
/**
 * 新增内部员工折扣方案界面
 */
Ext.define('Foss.pricing.inempDiscount.InempDiscountAddWindow',{
	extend : 'Ext.window.Window',
	title : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.addInempDiscountPlan'),//'新增快递折扣方案',
	closable : true,
	modal : true,
	resizable:false,
	parent:null,
	closeAction : 'hide',
	width :590,
	height :480,
	//布局方式
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//获取到快递折扣方案的FORM表单
			me.getInempDiscountEditFormPanel().getForm().reset();
		},
		beforeshow:function(me){
			
		}
	},
	//定义主快递折扣价格方案的Form表单
	inempDiscountEditFormPanel:null,
	getInempDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.inempDiscountEditFormPanel)){
			this.inempDiscountEditFormPanel =Ext.create('Foss.pricing.inempDiscount.InempDiscountEditFormShowPanel');
		}
		return this.inempDiscountEditFormPanel;
	},
	//提交快递折扣方案的主信息
	commintInempDiscount:function(){
		var me = this;
		//获取表单的信息
		var form = me.getInempDiscountEditFormPanel();
		//校验form是否通过校验
		if(form.getForm().isValid()){
			var inempDiscountEntityModel = new Foss.pricing.inempDiscount.inempDiscountPlanEntity();// 折扣方案主信息
			//将FORM中数据设置到MODEL里面
    		form.getForm().updateRecord(inempDiscountEntityModel);
    		//验证起始时间必须大于开始时间
    		if(inempDiscountEntityModel.get('beginTime').getTime()>=inempDiscountEntityModel.get('endTime')){
    			pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.expressDiscount.endTimeGTbeginTime'));//终止日期需大于起始日期！
    			return;
    		}
    		//createTime":NaN,"modifyTime":NaN,"billTime":NaN
    		inempDiscountEntityModel.set('createTime',null);
    		inempDiscountEntityModel.set('modifyTime',null);
    		inempDiscountEntityModel.set('billTime',null);
    		//将需要添加的参数信息存在一个JSON格式的对象中
    		var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':inempDiscountEntityModel.data}};//折扣新增数据
    		var successFun = function(json){
				pricing.showInfoMes(json.message);//提示新增成功
				me.close();
				//成功之后重新查询刷新结果集
				me.parent.getPagingToolbar().moveFirst();
				
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					//提示失败原因
					pricing.showErrorMes(json.message);
				}
			};
			//请求员工内部折扣新增
			var url = pricing.realPath('addInempDiscountPlan.action');
			//发送AJAX请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
	},
	constructor : function(config){
		var me = this, 
		cfg = Ext.apply({}, config);
	me.items = [me.getInempDiscountEditFormPanel()];
	me.fbar = [{
		text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.closeWindow'),//关闭
		handler :function(){
			me.close();
		} 
	},{
		text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.commitExpressDiscountPlan'),//'提交',
		cls:'yellow_button',
		margin:'0 0 0 360',
		handler :function(){
			me.commintInempDiscount();
		} 
	}];
	me.callParent([cfg]);
 }
});

/***
 * 
 * 快递折扣方案的FORM表单，用于显示和新增的表单
 * 
 ***/
Ext.define('Foss.pricing.inempDiscount.InempDiscountEditFormShowPanel',{
	extend : 'Ext.form.Panel',
	frame: true,
	title:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.discountPlanDefine'),//'折扣方案定义',//方案定义
	flex:1,
	collapsible: true,
	height:500,
    defaults : {
    	colspan: 2,
    	labelWidth:110,
    	allowBlank:false,
    	margin : '5 5 5 5'
    },
	layout:{
		type:'table',
		columns: 2
	},
	constructor:function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var minValueDate = new Date(pricing.inempDiscount.tomorrowTime);
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:3,
	        allowBlank:false,
	        name:'chargeRebate',
	        fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.chargeRebate'),//运费折扣
	        step:0.001,
	        maxValue: 1,
	        minValue: 0.001 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'highstPreferentialLimit',
	        fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.highstPreferentialLimit'),//最高优惠限额
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,  
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//开始时间
	        xtype : 'datefield'
		},{
			name: 'endTime',//截止时间,
			colspan: 1,
			labelWidth:60,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
	        xtype : 'datefield'
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.remark'),//'备注',
	        xtype : 'textareafield'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 定义修改的窗体信息
 */
Ext.define('Foss.pricing.inempDiscount.InempDiscountEditorWindow',{
	extend : 'Ext.window.Window',
	title : pricing.inempDiscount.i18n('foss.pricing.inempDiscount.editInempDiscountPlan'),//'折扣窗体',
	resizable:false,
	inempDiscountPlanEntity:null,//折扣方案信息
	parent:null,
	modal:true,
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//
			me.getInempDiscountEditFormPanel().getForm().reset();		
		},
		beforeshow:function(me){ 
			var form = me.getInempDiscountEditFormPanel();
			if(!Ext.isEmpty(me.inempDiscountPlanEntity)){
				form.getForm().loadRecord(new Foss.pricing.inempDiscount.inempDiscountPlanEntity(me.inempDiscountPlanEntity));//加载数据
				form.getForm().inempDiscountPlanEntity = me.inempDiscountPlanEntity;
			}else{
				pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.noInempDiscountPlanInfo'));//没有折扣方案信息！
				return;
			}
		}
	},
	inempDiscountEditFormPanel:null,
	getInempDiscountEditFormPanel:function(){
		if(Ext.isEmpty(this.inempDiscountEditFormPanel)){
    		this.inempDiscountEditFormPanel = Ext.create('Foss.pricing.inempDiscount.InempDiscountEditFormPanel');
    	}
    	return this.inempDiscountEditFormPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getInempDiscountEditFormPanel()];
		me.fbar = [{
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 定义修改的form表单
 */
Ext.define('Foss.pricing.inempDiscount.InempDiscountEditFormPanel',{
	extend : 'Ext.form.Panel',
	frame: true,
	title:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.discountPlanUpdate'),
	flex:1,
	inempDiscountPlanEntity:null,
	collapsible: true,
	height:500,
    defaults : {
    	colspan: 2,
    	labelWidth:110,
    	allowBlank:false,
    	margin : '5 5 5 5'
    },
	layout:{
		type:'table',
		columns: 2
	},
	
	//用于修改 fancy
	updateInempDiscount:function(){
		var me = this;
		//获取表单的信息
		var form = me;
		//校验form是否通过校验
		if(form.getForm().isValid()){
			var inempDiscountEntityModel = new Foss.pricing.inempDiscount.inempDiscountPlanEntity(me.inempDiscountPlanEntity);// 折扣方案主信息
			//将FORM中数据设置到MODEL里面
    		form.getForm().updateRecord(inempDiscountEntityModel);
    		
    		//验证起始时间必须大于开始时间
    		if(inempDiscountEntityModel.get('beginTime').getTime()>=inempDiscountEntityModel.get('endTime')){
    			pricing.showWoringMessage(pricing.inempDiscount.i18n('foss.pricing.expressDiscount.endTimeGTbeginTime'));//终止日期需大于起始日期！
    			return;
    		}
    		var inempDiscountEntityPlanId = me.getForm().inempDiscountPlanEntity.id;
    		var remark = form.getForm().findField('remark').getValue();
    		inempDiscountEntityModel.set('remark',remark);
    		inempDiscountEntityModel.set('createTime',null);
    		inempDiscountEntityModel.set('modifyTime',null);
    		inempDiscountEntityModel.set('billTime',null);
    		inempDiscountEntityModel.set('id',inempDiscountEntityPlanId);
    		//将需要添加的参数信息存在一个JSON格式的对象中
    		var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':inempDiscountEntityModel.data}};//折扣新增数据
    		var successFun = function(json){
    			//提示新增成功
				pricing.showInfoMes(json.message);
				//当修改成功了，需要更新下表单中的数据信息
				Ext.getCmp('T_pricing-inempDiscount_content').getAreaGrid().getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.requestTimedOut'));//请求超时
				}else{
					//提示失败原因
					pricing.showErrorMes(json.message);
				}
			};
			//更新方案折扣信息
			var url = pricing.realPath('updateInempDiscountPlan.action');
			//发送AJAX请求
			pricing.requestJsonAjax(url,params,successFun,failureFun);
		}
		
	},
	
	constructor:function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var minValueDate = new Date(pricing.inempDiscount.tomorrowTime);
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'chargeRebate',
	        fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.chargeRebate'),//运费折扣
	        step:0.001,
	        maxValue: 1,
	        minValue: 0.001 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        name:'highstPreferentialLimit',
	        fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.highstPreferentialLimit'),//开始范围
	        step:0.01,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			name: 'beginTime',
			colspan: 1,
			format:'Y-m-d',
			minValue:minValueDate,  
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.beginTime'),//开始时间
	        xtype : 'datefield'
		},{
			name: 'endTime',//截止时间,
			colspan: 1,
			labelWidth:60,
			minValue:minValueDate,
			format:'Y-m-d',
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.endTime'),//'截止时间',
	        xtype : 'datefield'
		},{
			name: 'remark',
			width:400,
			maxLength:200,
			allowBlank:true,
		    fieldLabel:pricing.inempDiscount.i18n('foss.pricing.inempDiscount.remark'),//'备注',
	        xtype : 'textareafield'
		}];
		me.fbar = [{
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.reset'),//'重置',
			margin:'0 0 0 360',
			handler :function(){
				me.getForm().reset();
			} 
		},{
			text :  pricing.inempDiscount.i18n('foss.pricing.expressDiscount.commitExpressDiscountPlan'),//'提交',
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.updateInempDiscount();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 定义查看详情的窗体信息
 */
Ext.define('Foss.pricing.inempDiscount.InempDiscountShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.inempDiscount.i18n('foss.pricing.inempDiscount.inempDiscountShowWindow'),//'查看折扣窗体',
	resizable:false,
	inempDiscountPlanEntity:null,//折扣方案主信息 expressDiscountEntity
	parent:null,
	modal:true,
	closeAction : 'hide',
	width :650,
	height :720,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//
			me.getInempDiscountShow().getInempDiscountShowPanel().getForm().reset();
		},
		beforeshow:function(me){
			var form = me.getInempDiscountShow().getInempDiscountShowPanel();
			if(!Ext.isEmpty(me.inempDiscountPlanEntity)){
				form.getForm().loadRecord( new Foss.pricing.inempDiscount.inempDiscountPlanEntity(me.inempDiscountPlanEntity));//加载数据
			}else{
				pricing.showErrorMes(pricing.inempDiscount.i18n('foss.pricing.inempDiscount.noInempDiscountPlanInfo'));//没有折扣方案信息！
				return;
			}
		}
	},
	//显示快递折扣主方案 和查询主方案对应的明细信息
	inempDiscountShow:null,
	getInempDiscountShow:function(){
		if(Ext.isEmpty(this.inempDiscountShow)){
    		this.inempDiscountShow = Ext.create('Foss.pricing.inempDiscount.InempDiscountShow');
    	}
    	return this.inempDiscountShow;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getInempDiscountShow()];
		me.fbar = [{
			text : pricing.inempDiscount.i18n('foss.pricing.expressDiscount.returnWindow'),//'返回',
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/****
 * 
 * 用于显示内部员工折扣方案信息
 * 
 ***/
Ext.define('Foss.pricing.inempDiscount.InempDiscountShow',{
	extend:'Ext.tab.Panel',
	clsL:'innerTabPanel',
	flex:1,
	layout:{
		type:'vbox',
		align:'stretch'
	},
	//快递折扣主方案信息FORM
	//定义主快递折扣价格方案的Form表单
	inempDiscountShowPanel:null,
	getInempDiscountShowPanel:function(){
		if(Ext.isEmpty(this.inempDiscountShowPanel)){
			this.inempDiscountShowPanel =Ext.create('Foss.pricing.inempDiscount.InempDiscountEditFormShowPanel');
			//设置为不可编辑
			this.inempDiscountShowPanel.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
			//fancy
			this.inempDiscountShowPanel.getForm().findField('beginTime').allowBlank=true;
			this.inempDiscountShowPanel.getForm().findField('endTime').allowBlank=true;
		}
		return this.inempDiscountShowPanel;
	},
	constructor:function(config){
		var me = this,cfg= Ext.apply({},config);
		me.items = [me.getInempDiscountShowPanel()];
		me.callParent([cfg]);
	}
});
/****
 * 
 * 定义立即激活的窗体信息
 * 
 ****/

Ext.define('Foss.pricing.inempDiscount.ImmediatelyActiveTimeWindow',{
	extend:'Ext.window.Window',
	title:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.immediatelyActiveDiscountPlanWindow'),//'立即激活方案',
	width:380,
	height:160,
	closeAction: 'hide' ,
	parent:null,
	inempDiscountPlanEntity:null,			//	实体
	
	//立即激活的FORM表单
	immediatelyActiveFormPanel:null,
	getImmediatelyActiveFormPanel:function(){
		if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
			this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.inempDiscount.ImmediatelyActiveFormPanel');
		}
		return this.immediatelyActiveFormPanel;
	},
	
	listeners:{
		beforeshow:function(me){
			//开始时间
			var showbeginTime = Ext.Date.format(new Date(me.inempDiscountPlanEntity.beginTime), 'Y-m-d');
			//结束时间
    		var showendTime = 	Ext.Date.format(new Date(me.inempDiscountPlanEntity.endTime), 'Y-m-d');
    		//显示
    		var value = pricing.inempDiscount.i18n('foss.pricing.showleftTimeInfo')
			  +showbeginTime+pricing.inempDiscount.i18n('foss.pricing.showmiddleTimeInfo')
			  +showendTime+pricing.inempDiscount.i18n('foss.pricing.showrightEndTimeInfo');
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

/***
 * 
 * 立即激活功能的Form表单
 * 
 ***/
Ext.define('Foss.pricing.inempDiscount.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	
	activeAction:function(){
		var me = this;
		var form = me.getForm();
		if(form.isValid()){
			//实体：
			var inempDiscountEntity = new Foss.pricing.inempDiscount.inempDiscountPlanEntity();
			//加载实体到对应的Form表单
			form.updateRecord(inempDiscountEntity);
			//设置当前时间
			inempDiscountEntity.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
			
			var id = me.up('window').inempDiscountPlanEntity.id;
			var createTime = me.up('window').inempDiscountPlanEntity.createTime;
			var endTime = me.up('window').inempDiscountPlanEntity.endTime;
			var modifyTime = me.up('window').inempDiscountPlanEntity.modifyTime;
			//fancy
			inempDiscountEntity.set('id',id);
			inempDiscountEntity.set('createTime',createTime);
			inempDiscountEntity.set('endTime',endTime);
			inempDiscountEntity.set('modifyTime',modifyTime);
			inempDiscountEntity.set('billTime',null);
			//封装对应的参数
    		var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':inempDiscountEntity.data}};
    		//立即激活的Action
    		var url = pricing.realPath('activeInempDiscountPlan.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.inempDiscount.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    	    //发送AJAX请求
    		pricing.requestJsonAjax(url,params,successFun,failureFun);
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
				fieldLabel:pricing.inempDiscount.i18n('foss.pricing.expressDiscount.effectiveDate'),//生效日期,
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_inempDiscount_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_inempDiscount_activeEndTime_ID-inputEl'
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
				text : pricing.inempDiscount.i18n('i18n.pricingRegion.determine'),//确认
				handler : function() {
					me.activeAction();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.inempDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
		this.callParent(cfg);
        }
});
/****
 * 
 * 定义立即终止的窗体信息
 * 
 ****/
Ext.define('Foss.pricing.inempDiscount.ImmediatelyStopEndTimeWindow',{
	extend: 'Ext.window.Window',
	title: pricing.inempDiscount.i18n('foss.pricing.expressDiscount.immediatelyStopDiscountPlanWindow'),//"立即中止方案",
	width:380,
	height:152,
	closeAction: 'hide' ,
	stopFormPanel:null,
	parent:null,
	
	getStopFormPanel : function(){
    	if(Ext.isEmpty(this.stopFormPanel)){
    		this.stopFormPanel = Ext.create('Foss.pricing.inempDiscount.ImmediatelyStopFormPanel');
    	}
    	return this.stopFormPanel;
    },
    listeners:{
    	beforeshow:function(me){
    		var showbeginTime = Ext.Date.format(new Date(me.inempDiscountPlanEntity.beginTime), 'Y-m-d');
    		var showendTime = 	Ext.Date.format(new Date(me.inempDiscountPlanEntity.endTime), 'Y-m-d');
    		var value = pricing.inempDiscount.i18n('foss.pricing.showleftTimeInfo')
			  +showbeginTime+pricing.inempDiscount.i18n('foss.pricing.showmiddleTimeInfo')
			  +showendTime+pricing.inempDiscount.i18n('foss.pricing.showstopRightEndTimeInfo');
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

/***
 * 
 *立即终止FORM表单
 *
 ***/
Ext.define('Foss.pricing.inempDiscount.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var inempDiscountEntity = new Foss.pricing.inempDiscount.inempDiscountPlanEntity(me.inempDiscountPlanEntity);
			form.updateRecord(inempDiscountEntity);
			
			inempDiscountEntity.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
			var id = me.up('window').inempDiscountPlanEntity.id;
			
			var id = me.up('window').inempDiscountPlanEntity.id;
			var createTime = me.up('window').inempDiscountPlanEntity.createTime;
			var beginTime = me.up('window').inempDiscountPlanEntity.beginTime;
			var modifyTime = me.up('window').inempDiscountPlanEntity.modifyTime;
			//fancy
			inempDiscountEntity.set('id',id);
			inempDiscountEntity.set('createTime',createTime);
			inempDiscountEntity.set('beginTime',beginTime);
			inempDiscountEntity.set('modifyTime',modifyTime);
			inempDiscountEntity.set('billTime',null);
			//时间限制
			if(inempDiscountEntity.get('endTime')<inempDiscountEntity.get('beginTime')) {
				pricing.showWoringMessage(pricing.inempDiscount.i18n('Foss.pricing.expressDiscount.compareTime'));
				return;
			}
			//封装参数
			var params = {'inempDiscountPlanVo':{'inempDiscountPlanEntity':inempDiscountEntity.data}};
    		
			//立即中止ACTION
    		var url = pricing.realPath('stopInempDiscountPlan.action');
    		
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel :pricing.inempDiscount.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_inempDiscount_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_inempDiscount_stopEndTime_ID-inputEl'
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
				text : pricing.inempDiscount.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.inempDiscount.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});

//加载员工内部折扣方案主界面
Ext.onReady(function(){
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-inempDiscount_content')) {
		return;
	}
	pricing.haveServerNowTime();//加载获取当前时间
	
	var queryInempDiscountForm = Ext.create('Foss.pricing.inempDiscount.QueryInempDiscountForm');//查询条件
	var inempDiscountGridPanel = Ext.create('Foss.pricing.inempDiscount.InempDiscountGridPanel');//查询结果

	Ext.getCmp('T_pricing-inempDiscount').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-inempDiscount_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryInempDiscountForm : function() {
			return queryInempDiscountForm;
		},
		//获得查询结果GRID
		getAreaGrid : function() {
			return inempDiscountGridPanel;
		},
		items : [queryInempDiscountForm,inempDiscountGridPanel]
	}));
});