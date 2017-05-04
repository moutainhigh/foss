/**
 * @author 杨在强—273296
 * @date 2015-7-6 14:39:27
*/

/**
 * 全局变量及函数
 */
//出发行政区域
baseinfo.lateCoupon.startCodes=new Array();
baseinfo.lateCoupon.deleteStartCodes=new Array();
baseinfo.lateCoupon.updateId=null;
//到大 行政区域
baseinfo.lateCoupon.arriveCodes=new Array();
baseinfo.lateCoupon.deleteArriveCodes=new Array();
//转换long类型为日期(在model中会用到)
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};

/**
 * 字符串数组操作
 */
baseinfo.lateCoupon.removeStr = function(p_array, str){
	if(p_array==null || p_array.length==0){
		return p_array;
	}
	
	for(var i=0,l=p_array.length;i<l;i++){
		if(p_array[i]==str){
			p_array.splice(i,1);
			return p_array;
		}
	}
	return p_array;
};

//checked所有父结点
baseinfo.lateCoupon.checkParent = function(node,checked,flag){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			if(flag=='start'){
				baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.startCodes,parentNode.data.id);
				baseinfo.lateCoupon.startCodes.push(parentNode.data.id);
			}else if(flag=='arrive'){
				baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.arriveCodes,parentNode.data.id);
				baseinfo.lateCoupon.arriveCodes.push(parentNode.data.id);
			}
			
			parentNode.set('checked',true);
		}
		baseinfo.lateCoupon.checkParent(parentNode,checked,flag);
	}
};

//重新加载树结点
baseinfo.lateCoupon.reloadTreeNode = function(id){
	// 重新加载树结点：
	// 重新加载树结点：
	var resourceTree=Ext.getCmp(id),
		treeStore = resourceTree.getStore(),
		rootNode = treeStore.getRootNode();
	treeStore.load({ 
	    node: rootNode,
	    callback: function(records, operation, success){
	    	rootNode.expand();	    	
	    }
	});
	 //加载树结点时，初始化已选树结点，作废的树结点
	baseinfo.lateCoupon.startCodes=new Array();
	baseinfo.lateCoupon.deleteStartCodes=new Array();
};
//Ajax请求--json
baseinfo.requestJsonAjax = function(url,params,successFn,failFn){
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
//通过storeId和model创建STORE
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

//晚到补差价方案Model
Ext.define('Foss.baseinfo.lateCoupon.LateCouponEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'createUser',//创建人工号
        type : 'string'
    },{
        name : 'createUserName',//创建人名称
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'modifyUser',//修改人工号
        type : 'string'
    },{
        name : 'code',//code
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'startBigzone',//名称
        type : 'string'
    },{
        name : 'arriveBigzone',//名称
        type : 'string'
    },{
        name : 'smsSent',//名称
        type : 'string'
    },{
        name : 'productItem',//名称
        type : 'string'
    },{
        name : 'deliveryMethod',//名称
        type : 'string'
    },{
        name : 'customerGroup',//名称
        type : 'string'
    },{
        name : 'maxValue',//名称
        type : 'string'
    },{
        name : 'validDays',//名称
        type : 'string'
    },{
        name : 'isCodeMatching',//名称
        type : 'string'
    },{
        name : 'smsInfo',//名称
        type : 'string'
    },{
        name : 'isActivation',//名称
        type : 'string'
    },{
        name : 'active',//名称
        type : 'string'
    },{
        name : 'beginTime',//开始日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'endTime',//截止日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'startBigzoneEntitys'//名称
        
    },{
        name : 'startBigzoneCodes'//名称
        
    },{
        name : 'arriveBigzoneEntitys'//名称
        
    },{
        name : 'arriveBigzoneCodes'//名称
        
    },{
        name : 'productEntitys'//名称
        
    },{
        name : 'productCodes'//名称
        
    }]
});

//------------------------------------model---------------------------------------------------
/**
 * 降价发券方案Store
 */
Ext.define('Foss.baseinfo.lateCoupon.lateCouponStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.lateCoupon.LateCouponEntity',//降价发券方案MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryLateCouponByCodition.action'),
		reader : {
			type : 'json',
			root : 'lateCouponVo.lateCouponEntitys',
			totalProperty : 'totalCount'
		}
	}
});
/**
 *  查询条件
 */
Ext.define('Foss.baseinfo.lateCoupon.QuerySchemeForm', {
	extend : 'Ext.form.Panel',
	title:'查询条件',//baseinfo.lateCoupon.i18n('foss.baseinfo.queryCondition'),//查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : 0.3,
    	margin : '8 10 5 10',
    	anchor : '100%'
    },
    height :140,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items  = [{
			name: 'name',
		    fieldLabel:'方案名称',//baseinfo.lateCoupon.i18n('foss.lateCoupon.lateCouponScheme'),//降价发券方案
	        xtype : 'textfield'
		},{
			name: 'isActivation',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:baseinfo.getStore(null,null,['valueCode','valueName']
		    ,[
		      {'valueCode':'Y','valueName':'已激活'},//已激活baseinfo.lateCoupon.i18n('foss.lateCoupon.active')
		      {'valueCode':'N','valueName':'未激活'},//未激活baseinfo.lateCoupon.i18n('foss.lateCoupon.inActive')
		      {'valueCode':'','valueName':'全部'}//全部baseinfo.lateCoupon.i18n('foss.baseinfo.all')
		      ]),
	        fieldLabel: '方案状态',//baseinfo.lateCoupon.i18n('foss.lateCoupon.lateCouponSchemeState'),//降价发券方案状态
	        xtype : 'combo'
		},{
			name: 'businessDate',
			format:'Y-m-d H:i:s',
		    fieldLabel:'业务日期',//baseinfo.lateCoupon.i18n('foss.lateCoupon.businessDate'),//业务日期
	        xtype : 'datefield'
		}];
		me.fbar = [{
			xtype : 'button', 
			width:70,
			text : '重置',//baseinfo.lateCoupon.i18n('foss.baseinfo.reset'),//重置
			disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponQuerybutton'),
			hidden: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponQuerybutton'),
			handler : function() {
				me.getForm().reset();
			}
		},{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			margin:'0 0 0 825',
			text : '查询',//baseinfo.lateCoupon.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponQuerybutton'),
			hidden:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponQuerybutton'),
			handler : function() {
				if(me.getForm().isValid()){
					me.up().getSchemeGrid().getStore().load();
				}
			}
		}]
		me.callParent([cfg]);
	}
});
/**
 *  查询结果
 */
Ext.define('Foss.baseinfo.lateCoupon.SchemeGrid', {
	extend: 'Ext.grid.Panel',
	title :'查询结果',//baseinfo.lateCoupon.i18n('foss.baseinfo.queryResults'),//查询结果
	frame: true,
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText:'查询结果为空',//baseinfo.lateCoupon.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
	//得到bbar（分页）
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
	//作废方案
	deleteScheme:function(){
		var me = this;
		var lateCouponIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			baseinfo.showErrorMes('请选择要作废的未激活降价发券方案！');//请选择要删除的未激活降价发券方案！baseinfo.lateCoupon.i18n('foss.lateCoupon.selectInActiveDeleteWantedScheme')
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('isActivation')=='Y'){//只有未激活的降价发券方案才可以删除
				baseinfo.showErrorMes('请选择要作废的未激活降价发券方案！');//请选择要删除的未激活降价发券方案！baseinfo.lateCoupon.i18n('foss.lateCoupon.selectInActiveDeleteWantedScheme')
				return;
			}else if(selections[i].get('isActivation')=='N'){
				lateCouponIds.push(selections[i].get('id'));
			}else{
				lateCouponIds.push(selections[i].get('id'));
			}
		}
		baseinfo.showQuestionMes('是否要作废这些未激活的降价发券方案？',function(e){//是否要删除这些未激活的降价发券方案？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'lateCouponVo':{'lateCouponIds':lateCouponIds}};
				var successFun = function(json){
					baseinfo.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
					}else{
						baseinfo.showErrorMes(json.message);
					}
				};
				var url = baseinfo.realPath('deleteLateCoupon.action');
				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
//立即中止
    stop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length != 1){
	 		baseinfo.showWoringMessage('请只选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.lateCoupon.i18n('foss.lateCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections[0].get('isActivation')=='N'){
	 		baseinfo.showWoringMessage('请选择已激活的方案进行操作！');//baseinfo.lateCoupon.i18n('foss.lateCoupon.selectInActiveToOperate')
	 		return;
	 	}else{
	 		var params = {'lateCouponVo':{'lateCouponEntity':{'id':selections[0].data.id}}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url = baseinfo.realPath('stopLateCoupon.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	 	}
	},
//立即激活
    activate:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length != 1){
	 		baseinfo.showWoringMessage('请只选择一条记录进行操作！');//请选择一条记录进行操作！baseinfo.lateCoupon.i18n('foss.lateCoupon.selectOneToOperate')
			return;
	 	}
	 	if(selections[0].get('isActivation')=='Y'){
	 		baseinfo.showWoringMessage('请选择未激活的方案进行操作！');//请选择未激活数据进行操作！baseinfo.lateCoupon.i18n('foss.lateCoupon.selectInActiveToOperate')
	 		return;
	 	}else{
	 		var params = {'lateCouponVo':{'lateCouponEntity':{'id':selections[0].data.id}}};
			var successFun = function(json){
				baseinfo.showInfoMes(json.message);
				me.getPagingToolbar().moveFirst();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);
				}
			};
			var url = baseinfo.realPath('activateLateCoupon.action');
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	 	}
	},
//新增Window
	lateCouponAddWindow : null,
	getAddWindow : function() {
		if (Ext.isEmpty(this.lateCouponAddWindow)) {
			this.lateCouponAddWindow = Ext.create('Foss.baseinfo.lateCoupon.AddWindow');
			this.lateCouponAddWindow.parent = this;
		}
		return this.lateCouponAddWindow;
	},
//修改Window
	lateCouponUpdateWindow : null,
	getUpdateWindow : function() {
		if (Ext.isEmpty(this.lateCouponUpdateWindow)) {
			this.lateCouponUpdateWindow = Ext.create('Foss.baseinfo.lateCoupon.UpdateWindow');
			this.lateCouponUpdateWindow.parent = this;
		}
		return this.lateCouponUpdateWindow;
	},
//查看详情window
	lateCouponDeatilShowWindow:null,
	getDetailWindow : function() {
		if (Ext.isEmpty(this.lateCouponDeatilShowWindow)) {
			this.lateCouponDeatilShowWindow = Ext.create('Foss.baseinfo.lateCoupon.DetailWindow');
			this.lateCouponDeatilShowWindow.parent = this;
		}
		return this.lateCouponDeatilShowWindow;
	},
	
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [
		  {
			xtype: 'rownumberer',
			width:40,
			text : '序号'//baseinfo.lateCoupon.i18n('foss.baseinfo.sequence')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : '操作',//baseinfo.lateCoupon.i18n('foss.lateCoupon.operate'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: '修改',//baseinfo.lateCoupon.i18n('foss.baseinfo.update'),//修改
                disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponUpdatebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('isActivation') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var form=me.getUpdateWindow().getWindowForm().getForm();
                	//把记录中的值全部存入form中，但不包括3个多选选择器
					record.data.productItem=null;
					form.setValues(record.data);
					baseinfo.lateCoupon.startCodes=[];
					baseinfo.lateCoupon.arriveCodes=[];
					baseinfo.lateCoupon.updateId=record.data.id;
					if(record.data.startBigzone!=null){
						baseinfo.lateCoupon.startCodes=record.data.startBigzone.split(",");
						
						//mcombo1.setValue(record.data.startBigzone);
					}
					if(record.data.arriveBigzone!=null){
						baseinfo.lateCoupon.arriveCodes=record.data.arriveBigzone.split(",");
						//mcombo2.setValue(record.data.arriveBigzoneCodes);
					}
					if(record.data.productEntitys!=null){
						var mcombo3=form.findField('productItem');
						mcombo3.getStore().loadData(record.data.productEntitys);
						mcombo3.setValue(record.data.productCodes);
					}
					if(record.data.customerGroup!=null){
						var mcombo4=form.findField('customerGroup');
						var array1=record.data.customerGroup.split(',');
						mcombo4.setValue(array1);
					}
					if(record.data.deliveryMethod!=null){
						var mcombo5=form.findField('deliveryMethod');
						var array2=record.data.deliveryMethod.split(',');
						mcombo5.setValue(array2);
					}
					
					
                  //显示window
                    me.getUpdateWindow().show();
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: '升级版本',//baseinfo.lateCoupon.i18n('foss.lateCoupon.updateVersion'),//升级版本
                disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponUpgradebutton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('isActivation') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var params = {'lateCouponVo':{'lateCouponEntity':{'id':record.get('id')}}};
    				var successFun = function(json){
    					baseinfo.showInfoMes(json.message);
    					grid.up().getPagingToolbar().moveFirst();
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = baseinfo.realPath('copyLateCoupon.action');
    				baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    				
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: '查看详情',//baseinfo.lateCoupon.i18n('foss.baseinfo.details'),//查看详情
                disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponDetailbutton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var record = grid.getStore().getAt(rowIndex);
                	var form=me.getDetailWindow().getWindowForm().getForm();
                	record.data.productItem=null;
					form.setValues(record.data);
					baseinfo.lateCoupon.startCodes=[];
					baseinfo.lateCoupon.arriveCodes=[];
					if(record.data.startBigzone!=null){
						baseinfo.lateCoupon.startCodes=record.data.startBigzone.split(",");
						
						//mcombo1.setValue(record.data.startBigzone);
					}
					if(record.data.arriveBigzone!=null){
						baseinfo.lateCoupon.arriveCodes=record.data.arriveBigzone.split(",");
						//mcombo2.setValue(record.data.arriveBigzoneCodes);
					}
					if(record.data.productEntitys!=null){
						var mcombo3=form.findField('productItem');
						mcombo3.getStore().loadData(record.data.productEntitys);
						mcombo3.setValue(record.data.productCodes);
					}
					if(record.data.customerGroup!=null){
						var mcombo4=form.findField('customerGroup');
						var array1=record.data.customerGroup.split(',');
						mcombo4.setValue(array1);
					}
					if(record.data.deliveryMethod!=null){
						var mcombo5=form.findField('deliveryMethod');
						var array2=record.data.deliveryMethod.split(',');
						mcombo5.setValue(array2);
					}
					//显示window
					 if(baseinfo.lateCoupon.updateId!=record.data.id){
							baseinfo.lateCoupon.updateId=record.data.id;
						}
					 me.getDetailWindow().show();
                }
			}]
		},{
			text : '编码',//baseinfo.lateCoupon.i18n('foss.lateCoupon.code'),//编码
			width:140,
			dataIndex : 'code'
		},{
			text : '方案名称',//baseinfo.lateCoupon.i18n('foss.lateCoupon.schemeName'),//方案名称
			width:180,
			dataIndex : 'name'
		},{
			text :'状态',//baseinfo.lateCoupon.i18n('foss.baseinfo.status'),//状态
			dataIndex : 'isActivation',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return '已激活';//baseinfo.lateCoupon.i18n('foss.lateCoupon.active');
				}else if(value=='N'){//'N'表示未激活
					return '未激活';//baseinfo.lateCoupon.i18n('foss.lateCoupon.inActive');
				}else{
					return '';
				}
			}
		},{
			text : '创建人',//baseinfo.lateCoupon.i18n('foss.lateCoupon.creator'),//创建人
			width:80,
			dataIndex : 'createUserName'
		},{
			text :'修改时间',//baseinfo.lateCoupon.i18n('foss.lateCoupon.modifyDate'),//修改时间
			width:150,
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :'起始时间',//baseinfo.lateCoupon.i18n('foss.lateCoupon.beginDate'),//起始时间
			width:100,
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :'截止时间',//baseinfo.lateCoupon.i18n('foss.lateCoupon.endDate'),//截止时间
			width:100,
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		}];
		me.store = Ext.create('Foss.baseinfo.lateCoupon.lateCouponStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var querySchemeForm = me.up().getQuerySchemeForm();
					if(querySchemeForm!=null){
						Ext.apply(operation,{
							params : {
								'lateCouponVo.lateCouponEntity.name':querySchemeForm.getForm().findField('name').getValue(),//方案名称
								'lateCouponVo.lateCouponEntity.isActivation':querySchemeForm.getForm().findField('isActivation').getValue(),//状态
								'lateCouponVo.lateCouponEntity.businessDate':querySchemeForm.getForm().findField('businessDate').getValue()//业务日期
							}
						});	
					}
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
			text : '新增',//baseinfo.lateCoupon.i18n('foss.baseinfo.add'),//新增
			disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponAddbutton'),
			hidden: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponAddbutton'),
			handler :function(){
				// 重新加载树结点：
				me.getAddWindow().show();
				baseinfo.lateCoupon.updateId=null;
				baseinfo.lateCoupon.arriveCodes=[];
				baseinfo.lateCoupon.startCodes=[];
			} 
		},'-', {
			//删除
			text : '作废',//baseinfo.lateCoupon.i18n('foss.baseinfo.void'),//作废
			disabled: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponDeletebutton'),
			hidden: !baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponDeletebutton'),
			handler :function(){
				me.deleteScheme();
			}
		},
		'-', {
			text : '立即激活',//baseinfo.lateCoupon.i18n('foss.baseinfo.activatedImmediately'),//'立即激活',
			disabled:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponImmediatelyActivebutton'),
			hidden:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponImmediatelyActivebutton'),
			handler :function(){
				me.activate();
			} 
		},'-', {
			text : '立即中止',//baseinfo.lateCoupon.i18n('foss.baseinfo.terminationImmediately'),//'立即中止',
			disabled:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponImmediatelyStopbutton'),
			hidden:!baseinfo.lateCoupon.isPermission('lateCoupon/lateCouponImmediatelyStopbutton'),
			handler :function(){
				me.stop();
			} 
		}];
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});




/**
 * 新增window
 */
Ext.define('Foss.baseinfo.lateCoupon.AddWindow',{
	extend : 'Ext.window.Window',
	title : '新增晚到补差价方案',//价格降价发券方案定义baseinfo.lateCoupon.i18n('foss.lateCoupon.lateCouponSchemeDefine')
	closable : true,
	modal : true,
	resizable:false,
	autoScroll:true,
	parent:null,//(Foss.baseinfo.lateCoupon.SchemeGrid)
	closeAction : 'hide',
	width :750,
	height :700,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getWindowForm().getForm().reset();
			me.windowForm=null;
		}
	},
	//降价发券方案主信息FORM
	windowForm:null,
	getWindowForm:function(){
		if(Ext.isEmpty(this.windowForm)){
    		this.windowForm = Ext.create('Foss.baseinfo.lateCoupon.WindowForm');
    	}
    	return this.windowForm;
	},
	//提交降价发券方案主信息
	commintLateCoupon:function(){
    	var me = this;
    	var form = me.getWindowForm();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var model = new Foss.baseinfo.lateCoupon.LateCouponEntity();// 降价发券方案主信息
    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
    		if(model.get('beginTime').getTime()>=model.get('endTime')){
    			baseinfo.showWoringMessage('终止日期需大于起始日期！');//终止日期需大于起始日期！baseinfo.lateCoupon.i18n('foss.lateCoupon.endDateBehindBeginDate')
    			return;
    		}
    		
    		if(model.get('productItem').length>100){
    			baseinfo.showWoringMessage('产品类型输入过多！');
    			return;
    		}
    		model.data.startBigzoneCodes=null;
    		model.data.startBigzoneEntitys=null;
    		model.data.arriveBigzoneCodes=null;
    		model.data.arriveBigzoneEntitys=null;
    		model.data.productCodes=null;
    		model.data.productEntitys=null;
    		model.data.startBigzone=baseinfo.lateCoupon.startCodes.join(",");
    		model.data.arriveBigzone=baseinfo.lateCoupon.arriveCodes.join(",");
    		var params = {'lateCouponVo':{'lateCouponEntity':model.data}};//降价发券新增数据
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//提示新增成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('addLateCoupon.action');//请求降价发券新增
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getWindowForm()];
		me.fbar = [{
			text : '返回列表',//baseinfo.lateCoupon.i18n('foss.lateCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '提交',//baseinfo.lateCoupon.i18n('foss.lateCoupon.commit')
			cls:'yellow_button',
			margin:'0 0 0 360',
			handler :function(){
				me.commintLateCoupon();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改window
 */
Ext.define('Foss.baseinfo.lateCoupon.UpdateWindow',{
	extend : 'Ext.window.Window',
	title : '修改晚到补差价方案',//降价发券方案定义baseinfo.lateCoupon.i18n('foss.lateCoupon.lateCouponSchemeDefine')
	closable : true,
	modal : true,
	resizable:false,
	autoScroll:true,
	lateCouponEntity:null,//降价发券方案主信息
	parent:null,//(Foss.baseinfo.lateCoupon.SchemeGrid)
	closeAction : 'hide',
	width :750,
	height :700,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getWindowForm().getForm().reset();
		}
	},
	//降价发券方案主信息FORM
	windowForm:null,
	getWindowForm:function(){
		if(Ext.isEmpty(this.windowForm)){
    		this.windowForm = Ext.create('Foss.baseinfo.lateCoupon.WindowForm');
    	}
    	return this.windowForm;
	},
	//提交降价发券方案主信息
	commintLateCoupon:function(){
    	var me = this;
    	var form = me.getWindowForm();
    	if(form.getForm().isValid()){//校验form是否通过校验
    		var model = new Foss.baseinfo.lateCoupon.LateCouponEntity();// 降价发券方案主信息
    		form.getForm().updateRecord(model);//将FORM中数据设置到MODEL里面
    		if(model.get('beginTime').getTime()>=model.get('endTime')){
    			baseinfo.showWoringMessage('终止日期需大于起始日期！');//终止日期需大于起始日期！baseinfo.lateCoupon.i18n('foss.lateCoupon.endDateBehindBeginDate')
    			return;
    		}
    		if(model.get('productItem').length>100){
    			baseinfo.showWoringMessage('产品类型输入过多！');
    			return;
    		}
    		model.data.startBigzoneCodes=null;
    		model.data.startBigzoneEntitys=null;
    		model.data.arriveBigzoneCodes=null;
    		model.data.arriveBigzoneEntitys=null;
    		model.data.productCodes=null;
    		model.data.productEntitys=null;
    		model.data.startBigzone=baseinfo.lateCoupon.startCodes.join(",");
    		model.data.arriveBigzone=baseinfo.lateCoupon.arriveCodes.join(",");
    		var params = {'lateCouponVo':{'lateCouponEntity':model.data}};//降价发券新增数据
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//提示修改成功
				me.close();
				me.parent.getPagingToolbar().moveFirst();//成功之后重新查询刷新结果集
				
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时！');//请求超时baseinfo.lateCoupon.i18n('foss.baseinfo.requestOvertime')
				}else{
					baseinfo.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = baseinfo.realPath('updateLateCoupon.action');//请求降价发券修改
			baseinfo.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
   	    }
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getWindowForm()];
		me.fbar = [{
			text : '返回列表',//baseinfo.lateCoupon.i18n('foss.lateCoupon.returnList')
			handler :function(){
				me.close();
			} 
		},{
			text : '提交',//baseinfo.lateCoupon.i18n('foss.lateCoupon.commit')
			cls:'yellow_button',
			margin:'0 0 0 285',
			handler :function(){
				me.commintLateCoupon();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 查看详情window
 */
Ext.define('Foss.baseinfo.lateCoupon.DetailWindow',{
	extend : 'Ext.window.Window',
	title : '查看晚到补差价方案',//查看降价发券方案baseinfo.lateCoupon.i18n('foss.lateCoupon.showSchemeDetail')
	resizable:false,
	lateCouponEntity:null,//降价发券方案主信息
	parent:null,//(Foss.baseinfo.lateCoupon.SchemeGrid)
	closeAction : 'hide',
	width :680,
	height :700,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getWindowForm().getForm().reset();
		}
	},
	windowForm:null,
	getWindowForm:function(){
		if(Ext.isEmpty(this.windowForm)){
			//创建内部表格
    		this.windowForm = Ext.create('Foss.baseinfo.lateCoupon.WindowForm');
			//全部设为只读
    		this.windowForm.getForm().getFields().each(function(item){
    			item.setReadOnly(true);
    		});
    		//将短信内容下面的提示模板去掉
    		this.windowForm.getForm().findField('smsInfo').afterSubTpl='';
    		//将方案期限的最小时间限制去掉，否则可能会有叉叉
    		this.windowForm.getForm().findField('beginTime').minValue='';
    		this.windowForm.getForm().findField('endTime').minValue='';
    	}
    	return this.windowForm;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getWindowForm()];
		me.fbar = [{
			text : '返回列表',//返回列表baseinfo.lateCoupon.i18n('foss.lateCoupon.returnList')
			handler :function(){
				me.close();
			}
		}];
		me.callParent([cfg]);
	}
});
/**
 * 共用窗口表格（方案主信息）
 */
Ext.define('Foss.baseinfo.lateCoupon.WindowForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	autoScroll: true,
	title:'晚到补差价方案',//降价发券方案定义baseinfo.lateCoupon.i18n('foss.lateCoupon.lateCouponSchemeDefine')
	flex:1,
	collapsible: true,
    defaults : {
    	colspan: 1,
    	labelWidth:110,
    	allowBlank:true,
    	margin : '5 5 5 5',
    	width:335
    },
	layout:{
		type:'table',
		columns: 2
	},
	startRegionsTree: null,
	getStartRegionsTree: function(){
		if(this.startRegionsTree == null){
			this.startRegionsTree = Ext.create('Foss.baseinfo.lateCoupon.StartRegionsTree');
		}
		return this.startRegionsTree;
	},
	arriveRegionsTree: null,
	getArriveRegionsTree: function(){
		if(this.arriveRegionsTree == null){
			this.arriveRegionsTree = Ext.create('Foss.baseinfo.lateCoupon.ArriveRegionsTree');
		}
		return this.arriveRegionsTree;
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'code',
		    fieldLabel:'编码',//编码baseinfo.lateCoupon.i18n('foss.lateCoupon.schemeCode')
		    readOnly:true,
		    emptyText:'自动生成编码',//自动生成编码baseinfo.lateCoupon.i18n('foss.lateCoupon.autoGenerateCode')
	        xtype : 'textfield'
		},{
			name: 'name',
			maxLength:20,
			allowBlank:false,
		    fieldLabel:'方案名称',//方案名称baseinfo.lateCoupon.i18n('foss.lateCoupon.schemeName')
	        xtype : 'textfield'
		},{
	        xtype: 'displayfield',
	        value:'<span style="font-size:10;font-weight:bold;color:blue">'
	        	+'优惠券使用条件'//优惠券使用条件baseinfo.lateCoupon.i18n('foss.lateCoupon.conditionToUseCoupon')
	        	+'</span>',//优惠券使用条件
	        margins: '0 0 0 10',
	        colspan: 2	
		},
		me.getStartRegionsTree(),me.getArriveRegionsTree()
		,{
			name:'smsSent',
	        xtype: 'combo',
	        fieldLabel:'短信发送对象',//产品类型baseinfo.lateCoupon.i18n('foss.lateCoupon.productPattern')
	        queryMode: 'local',
	        displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    value:'',
		    store:baseinfo.getStore(null,null,['valueCode','valueName']
		    ,[
		      {'valueCode':'','valueName':'全部'},//已激活baseinfo.lateCoupon.i18n('foss.lateCoupon.active')
		      {'valueCode':'start','valueName':'出发'},//未激活baseinfo.lateCoupon.i18n('foss.lateCoupon.inActive')
		      {'valueCode':'arrive','valueName':'到达'}//全部baseinfo.lateCoupon.i18n('foss.baseinfo.all')
		      ]),
	        colspan: 2
		},{
			name:'productItem',
	        xtype: 'commonproductmultiselector',
	        allowBlank:false,
	        levelses:'3',
	        fieldLabel:'产品类型',//产品类型baseinfo.lateCoupon.i18n('foss.lateCoupon.productPattern')
	        colspan: 1
		},{
			name:'deliveryMethod',
	        fieldLabel:'提货方式',//提货方式
	        xtype: 'combo',
	        queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    multiSelect:true,
		    store:FossDataDictionary.getDataDictionaryStore('PICKUPGOODSHIGHWAYS'),
		    value:'SELF_PICKUP',//自提
	        colspan: 1
		},{
			name:'customerGroup',
	        fieldLabel:'客户分群',//客户分群
	        xtype: 'combo',
	        queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    multiSelect:true,
		    store:FossDataDictionary.getDataDictionaryStore('CRM_CUSTOMER_GROUP'),
		    value:'NEWCUST',//新客户
	        colspan: 1
		},{
			name:'maxValue',
	        allowBlank:false,
	        fieldLabel:'最高面额',//最高面额
	        colspan: 1,
			minValue: 0,
			maxValue: 9999,
			allowDecimals: false,
			xtype:'numberfield'
	    },{
			name: 'beginTime',
			colspan: 1,
			width:285,
			format:'Y-m-d H:i:s',
			allowBlank:false,
			minValue:new Date(new Date().getFullYear(),
										   new Date().getMonth(), 
										   new Date().getDate(), 0, 0, 0),
		    fieldLabel:'方案期限',//降价发券期限baseinfo.lateCoupon.i18n('foss.lateCoupon.couponExpiration')
	        xtype:'datefield'
		},{
			name: 'endTime',
			colspan: 1,
			width:285,
			minValue:new Date(new Date().getFullYear(),
										   new Date().getMonth(), 
										   new Date().getDate(), 0, 0, 0),
			format:'Y-m-d H:i:s',
			allowBlank:false,
		    fieldLabel:'至',//至baseinfo.lateCoupon.i18n('foss.lateCoupon.to')
		    xtype : 'datefield'
		},{
			width:285,
			colspan: 1,
			xtype:'radiogroup',
			layout:'column',
			fieldLabel :'编码是否匹配',
			items: [{ boxLabel: '是', name: 'isCodeMatching',inputValue: 'Y'},
			         {	xtype:'container',
				        border:false,
				        html:'&nbsp;',
				        height:1,
				        columnWidth:.22 },
			        { boxLabel: '否', name: 'isCodeMatching',inputValue: 'N'}]
		},{
			name: 'validDays',
	        allowBlank:false,
	        colspan: 1,
			fieldLabel:'优惠券有效期(天)',
			minValue: 0,
			maxValue: 9999,
			allowDecimals: false,
			xtype:'numberfield'
		},{
			name: 'smsInfo',
			colspan: 2,
			width:570,
			maxLength:300,
			allowBlank:false,
		    fieldLabel:'优惠券短信内容',
		    afterSubTpl:'尊敬的客户。单号XX已到达，因比预计时间晚，发送X张优惠券共XX元作为补偿，详情可登录德邦官网输入单号查询！【德邦物流】',
	        xtype : 'textareafield'
		},{
			name:'id',
			xtype:'textfield',
			hidden:true
		}];
		me.callParent([cfg]);
	}
});

//定义出发行政区域Tree
Ext.define('Foss.baseinfo.lateCoupon.StartRegionsTree',{
    title: '出发行政区域',
    //id: 'Foss_baseinfo_lateCoupon_StartRegionsTree_Id',
	extend:'Ext.tree.Panel',
	autoScroll: true,
	animate: false,
	useArrows: true,
	frame: true,
	rootVisible: true,
	border: true,
	height : 400,
	width:280,
	
	selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(checked){
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.startCodes, a_code);
			baseinfo.lateCoupon.startCodes.push(a_code);
		}else{
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.startCodes, a_code);
		}
		Ext.Array.each(node.childNodes, function(childNode) {
			me.selectChildFunction(childNode, checked);
			childNode.set("checked", checked);	   
		});
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
			baseinfo.lateCoupon.checkParent(node, true,'start');
			this.selectChildFunction(node, true);
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.startCodes, a_code);
			baseinfo.lateCoupon.startCodes.push(a_code);
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.deleteStartCodes, a_code);	
		} else {
		    //去除用户取消的编码
			this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			//this.deSelectParentFunction(node);
			baseinfo.lateCoupon.deleteStartCodes.push(a_code);
			//baseinfo.lateCoupon.startCodes.remove(a_code);
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.startCodes, a_code);
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store=Ext.create('Foss.baseinfo.lateCoupon.StartRegionsTreeStore');
		// 监听鼠标事件
		me.listeners = {
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
		me.callParent([cfg]);
	}
}); 	

//定义出到达政区域Tree
Ext.define('Foss.baseinfo.lateCoupon.ArriveRegionsTree',{
    title: '到达行政区域',
    //id: 'Foss_baseinfo_lateCoupon_ArriveRegionsTree_Id',
	extend:'Ext.tree.Panel',
	autoScroll: true,
	animate: false,
	useArrows: true,
	frame: true,
	rootVisible: true,
	border: true,
	height : 400,
	width:280,
	selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(checked){
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.arriveCodes, a_code);
			baseinfo.lateCoupon.arriveCodes.push(a_code);
		}else{
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.arriveCodes, a_code);
		}
		Ext.Array.each(node.childNodes, function(childNode) {
			me.selectChildFunction(childNode, checked);
			childNode.set("checked", checked);	   
		});
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
			baseinfo.lateCoupon.checkParent(node, true,'arrive');
			this.selectChildFunction(node, true);
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.arriveCodes, a_code);
			baseinfo.lateCoupon.arriveCodes.push(a_code);
		} else {
		    //去除用户取消的编码
			this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			//this.deSelectParentFunction(node);
			baseinfo.lateCoupon.deleteArriveCodes.push(a_code);
			//baseinfo.lateCoupon.startCodes.remove(a_code);
			baseinfo.lateCoupon.removeStr(baseinfo.lateCoupon.arriveCodes, a_code);
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.lateCoupon.ArriveRegionsTreeStore');
		// 监听鼠标事件
		me.listeners = {
			/*itemexpand : function(node){
				me.itemExpand(node);
			},*/
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
		me.callParent([cfg]);
	}
}); 	
		
		
//定义出发行政区域 treeStore
Ext.define('Foss.baseinfo.lateCoupon.StartRegionsTreeStore',{
	extend: 'Ext.data.TreeStore',
	storeId: 'Foss_baseinfo_lateCoupon_StartRegionsTreeStore_Id',
	root: {
		// 根的文本信息
		text:'中国',
		// 设置根节点的ID
		id : '100000'
		
	},
	
		// 设置一个代理，通过读取内存数据
	proxy: {
		type : 'ajax',
		async: false,
		actionMethods : 'POST',
		params : {
			'lateCouponVo.lateCouponEntity.id':baseinfo.lateCoupon.updateId
		},
		url : baseinfo.realPath("queryStartDisctrict.action"),
		reader: {
           type: 'json',
           root: 'nodes'
           
		}
		
//		type:'ajax',
//		params : {
//			'lateCouponVo.lateCouponEntuty.id':baseinfo.lateCoupon.updateId
//		},
//		url: baseinfo.realPath("queryStartDisctrict.action"),
//		reader: {
//	           type: 'json'
//	           
//			},
//		extractResponseData: function(response) {
//              var json = Ext.JSON.decode(response.responseText);
//              Ext.each(json,function(record){
//            	  if(Ext.isEmpty(record.children)){
//            		  record.expanded = false;
//            		  record.leaf = true;
//            	  }else{
//            		  record.expanded = true;
//            	  }
//              });
//              response.responseText = Ext.JSON.encode(json);
//              return response  
//          }
	
	},
	// 设置action层接收父结点的标识：
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode':operation.params.node,
					'lateCouponVo.lateCouponEntity.id':baseinfo.lateCoupon.updateId
				}
			
			});	
			
		}
	}
});

//定义到达行政区域 treeStore
Ext.define('Foss.baseinfo.lateCoupon.ArriveRegionsTreeStore',{
	extend: 'Ext.data.TreeStore',
	storeId: 'Foss_baseinfo_lateCoupon_ArriveRegionsTreeStore_Id',
	root: {
		// 根的文本信息
		text:'中国',
		// 设置根节点的ID
		id : '100000'
	
	},
	autoLoad : false,
		// 设置一个代理，通过读取内存数据
	proxy: {
		type : 'ajax',
		async: false,
		actionMethods : 'POST',
		params : {
			'lateCouponVo.lateCouponEntity.id':baseinfo.lateCoupon.updateId
		},
		url : baseinfo.realPath("queryArriveDisctrict.action"),
		reader: {
           type: 'json',
           root: 'nodes'
		}
	},
	// 设置action层接收父结点的标识：
	listeners : {
		beforeload: function(store, operation, eOpts){
			Ext.apply(operation,{
				params : {
					// 上级权限编码
					'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode':operation.params.node,
					'lateCouponVo.lateCouponEntity.id':baseinfo.lateCoupon.updateId
				}
			});	
			
		}
	}
});


//Ext.define('Foss.baseinfo.lateCoupon.AdministrativeRegionsTreeStore',{
//	extend:'Ext.data.TreeStore',
//	proxy : {
//		type : 'ajax',
//		actionMethods : 'post',
//		url : baseinfo.realPath('queryAllDisctrict.action'),// 查询的url
//		reader : {
//			type : 'json',
//			root : 'nodes'// 结果集
//		}
//	}
//
//});

//Ext.define('Foss.baseinfo.lateCoupon.AdministrativeRegionsTreeStore',{
//  	extend: 'Ext.data.TreeStore', 
//  	root: {
//		//根的文本信息
//		text:'出发行政区域',
//		//设置根节点的ID
//		id : '01',
//		//根节点是否展开
//		expanded: true
//	},
//	//设置一个代理，通过读取内存数据
//  	proxy: {
//		type : 'ajax',
//		actionMethods : 'POST',
//		url : baseinfo.realPath("queryAllDisctrict.action"),
//        reader: {
//            type: 'json',
//            root: 'nodes'
//        }
//  	},
//  	nodeParam: 'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode'
//});    


//查询出发省市面板，
Ext.define('Foss.baseinfo.lateCoupon.RegionsPannel',{
	extend:'Ext.panel.Panel',
	height: 380,
	frame: true,
	layout:'column',
	columnWidth: 0.25,
	defaultType : 'textfield',
	//定义组织树方法
	startRegionsTree: null,
	getStartRegionsTree: function(){
		if(this.startRegionsTree == null){
			this.startRegionsTree = Ext.create('Foss.baseinfo.lateCoupon.StartRegionsTree');
		}
		return this.startRegionsTree;
	},
	arriveRegionsTree: null,
	getArriveRegionsTree: function(){
		if(this.arriveRegionsTree == null){
			this.arriveRegionsTree = Ext.create('Foss.baseinfo.lateCoupon.ArriveRegionsTree');
		}
		return this.arriveRegionsTree;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getStartRegionsTree(),me.getArriveRegionsTree()];  
		me.callParent([cfg]);
	}
});

/**
 *   初始化
 */

Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-lateCoupon_content')) {
		return;
	}
	var querySchemeForm = Ext.create('Foss.baseinfo.lateCoupon.QuerySchemeForm');//查询条件
	var schemeGrid = Ext.create('Foss.baseinfo.lateCoupon.SchemeGrid');//查询结果
	Ext.getCmp('T_baseinfo-lateCoupon').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_baseinfo-lateCoupon_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQuerySchemeForm : function() {
			return querySchemeForm;
		},
		//获得查询结果GRID
		getSchemeGrid : function() {
			return schemeGrid;
		},
		items : [querySchemeForm,schemeGrid]
	}));
});
