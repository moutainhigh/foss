//定义主营非主营常量
dict.dataDictionary.PRIMARY={CODE:"PRIMARY",NAME:'主营收入'};
dict.dataDictionary.SECONDARY={CODE:"SECONDARY",NAME:'非主营收入'};
dict.dataDictionary.WAYBILL={CODE:"WAYBILL",NAME:'运单相关'};
dict.dataDictionary.NOTWAYBILL={CODE:"NOTWAYBILL",NAME:'非运单相关'};
//转换long类型为日期
dict.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
dict.requestJsonAjax = function(url,params,successFn,failFn)
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
dict.getStore = function(storeId,model,fields,data) {
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

//查询所有的词
dict.dataDictionary.dataDictionary = [];
//自由车辆品牌
dict.dataDictionary.ownership_company = 'ownership_company';
//车辆品牌词代码
dict.dataDictionary.brandType = 'VEHICLE_BRAND_TYPE';
//查询所有的词
dict.searchdataDictionary = function(){
	Ext.Ajax.request({
		url:dict.realPath('getPropList.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			dict.dataDictionary.dataDictionary = result.dataDictionaryVo.dataDictionaryEntitys;//查询所有的词
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeoutW'));
			}else{
				dict.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeoutW'));
			}else{
				dict.showErrorMes(result.message);
			}
		}
	});
};
/**
 * 当termsCode为应收单收款类别时则显示单选框否则显示文本
 * DMANA-418增加termsCode类型是RENTCAR_COST_TYPE时，添加与修改显示两个文本框，其他类型显示一个文本
 * 
 */
dict.changeFormElement=function(formObj){
	var form = formObj.getForm();
	var termsCode=form.findField('termsCode').getValue();
	var extAttribute4_Obj = form.findField('extAttribute4');
	var extAttribute3_Obj = form.findField('extAttribute3');
	var extAttribute2_Obj = form.findField('extAttribute2');
	var extAttribute1_Obj = form.findField('extAttribute1');
	if(termsCode=='BILL_RECEIVABLE__COLLECTION_TYPE'){
		extAttribute4_Obj.hide();
		extAttribute3_Obj.show();
		extAttribute2_Obj.show();
		extAttribute1_Obj.hide();
	}else if(termsCode=='RENTCAR_COST_TYPE'){
		extAttribute4_Obj.show();
		extAttribute3_Obj.hide();
		extAttribute2_Obj.hide();
		extAttribute1_Obj.show();	
		extAttribute4_Obj.setValue(" ");
	}else{
		extAttribute4_Obj.hide();
		extAttribute3_Obj.hide();
		extAttribute2_Obj.hide();
		extAttribute1_Obj.show();
	}
}
//--------------------------------------dict----------------------------------------


//------------------------------------model---------------------------------------------------
//值MODEL
Ext.define('Foss.dict.dataDictionary.DataDictionaryValueEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'valueName',//值名称
        type : 'string'
    },{
        name : 'valueCode',//值代码
        type : 'string'
    },{
        name : 'language',//语言
        type : 'string'
    },{
        name : 'termsName',//上级词条名称-数据字典值不记录，用于显示到前台界面
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'termsCode',//上级词条
        type : 'string'
    },{
        name : 'valueSeq',//序号
        type : 'string'
    },{
        name : 'extAttribute1',//扩展属性
        type : 'string'
    },{
        name : 'extAttribute2',//扩展属性
        type : 'string'
    },{
        name : 'noteInfo',//备注
        type : 'string'
    }]
});
/**
 * 数据字典Store
 */
Ext.define('Foss.dict.dataDictionary.DataDictionaryStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.dict.dataDictionary.DataDictionaryValueEntity',//数据字典MODEL
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : "../dict/queryDataDictionaryValueByEntity.action",//查询的url
		reader : {
			type : 'json',
			root : 'dataDictionaryVo.dataDictionaryValueEntityList',//结果集
			totalProperty : 'totalCount'//个数
		}
	}
});

//----------------------------------------store---------------------------------
/**
 * 词表单
 */
Ext.define('Foss.dict.dataDictionary.QueryDataDictionaryForm', {
	extend : 'Ext.form.Panel',
	title: dict.dataDictionary.i18n('foss.dict.searchCondiction'),//查询条件
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .35,
    	maxLength:50,
    	margin : '8 10 5 10',
       	anchor : '100%'
    },
    height :180,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		var dataDictionary = new Array();
		for(var i = 0;i<dict.dataDictionary.dataDictionary.length;i++){
			dataDictionary.push(dict.dataDictionary.dataDictionary[i]);
		}
		dataDictionary.push({'termsCode':'ALL','termsName':dict.dataDictionary.i18n('foss.dict.all')});
		me.items = [{
			name: 'termsCode',
			queryMode: 'local',
		    displayField: 'termsName',
		    valueField: 'termsCode',
		    store:dict.getStore(null,null,['termsCode','termsName']
		    ,dataDictionary),
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.theWordName'),//词名称
	        xtype : 'combo'
		},{
			name: 'valueCode',
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.valueCode'),//值代码
	        xtype : 'textfield'
		},{
			name: 'valueName', 
			fieldLabel: dict.dataDictionary.i18n('foss.dict.valueName'),//值名称
	        xtype : 'textfield'
		},{ 
			xtype:'container' 
		},{
			border: 1,
			xtype:'container',
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
				  text : dict.dataDictionary.i18n('foss.dict.reset'),//重置
				  columnWidth:.08,
				  disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryQueryButton'),
				  hidden:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryQueryButton'),
				  handler : function() {
						me.getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.84
				},{
				  text : dict.dataDictionary.i18n('foss.dict.search'),//查询
				  columnWidth:.08,
				  disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryQueryButton'),
				  hidden:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryQueryButton'),
				  cls:'yellow_button',  
				  handler:function() {
					if(me.getForm().isValid()){
						me.up().getDataDictionaryGrid().getPagingToolbar().moveFirst();
					}
				  }
			  	}]
			}]; 
		me.callParent([cfg]);
	}
});
/**
 * 词列表
 */
Ext.define('Foss.dict.dataDictionary.DataDictionaryGridPanel', {
	extend: 'Ext.grid.Panel',
	title : dict.dataDictionary.i18n('foss.dict.searchResult'),//查询结果列表
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    bodyCls:'autoHeight',
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: dict.dataDictionary.i18n('foss.dict.selectResultNull'),//查询结果为空
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
	//作废数据字典
	toVoid: function(btn){
		var me = this;
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.pleaseOneToOp'));
			return;
		}
		var dataDictionaryValueEntityList = new Array();
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('extAttribute1')==dict.dataDictionary.ownership_company
					&&selections[i].get('termsCode')==dict.dataDictionary.brandType){
				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.ownVehicleBrandCanNotBeCancelled'));
				return;
			}
			dataDictionaryValueEntityList.push({'virtualCode':selections[i].get('virtualCode'),'termsCode':selections[i].get('termsCode')});
		}
		dict.showQuestionMes(dict.dataDictionary.i18n('foss.dict.isVoidValues'),function(e){//是否要作废这些值？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'dataDictionaryVo':{'dataDictionaryValueEntityList':dataDictionaryValueEntityList}};
				var successFun = function(json){
					dict.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeout'));//请求超时
					}else{
						dict.showErrorMes(json.message);
					}
				};
				var url = dict.realPath('deleteValue.action');
				dict.requestJsonAjax(url,params,successFun,failureFun);
			}
		})
	},
	
	//值新增
	valueAddWindow:null,
	getValueAddWindow : function() {
		//if (this.valueAddWindow == null) {
			this.valueAddWindow = Ext.create('Foss.dict.dataDictionary.ValueAddWindow');
			this.valueAddWindow.parent = this;
		//}
		return this.valueAddWindow;
	},
	//值修改
	valueUpdateWindow:null,
	getValueUpdateWindow : function() {
		//if (this.valueUpdateWindow == null) {
			this.valueUpdateWindow = Ext.create('Foss.dict.dataDictionary.ValueUpdateWindow');
			this.valueUpdateWindow.parent = this;
		//}
		return this.valueUpdateWindow;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : dict.dataDictionary.i18n('foss.dict.num')//序号
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : dict.dataDictionary.i18n('foss.dict.op'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: dict.dataDictionary.i18n('foss.dict.update'),//修改
                disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryUpdateButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
    				//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	if(record.get('extAttribute1')==dict.dataDictionary.ownership_company
                			&&record.get('termsCode')==dict.dataDictionary.brandType){
        				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.ownVehicleBrandCanNotBeChanged'));
        				return;
        			}
                	var termsCode = record.get('termsCode');//词条代码
                	var valueCode = record.get('valueCode');//值代码
    				var params = {'dataDictionaryVo':{'dataDictionaryValueEntity':{'termsCode':termsCode,'valueCode':valueCode}}};
    				var successFun = function(json){
    					var updateWindow = me.getValueUpdateWindow();//获得修改窗口
    					updateWindow.dataDictionaryValueEntity = json.dataDictionaryVo.dataDictionaryValueEntity;//值
    					updateWindow.show();//显示修改窗口
    				};
    				var failureFun = function(json){
    					if(Ext.isEmpty(json)){
    						baseinfo.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeout'));//请求超时
    					}else{
    						baseinfo.showErrorMes(json.message);
    					}
    				};
    				var url = dict.realPath('queryDataDictionaryValueByTermsCodeValueCode.action');
    				dict.requestJsonAjax(url,params,successFun,failureFun);
                	
                }
			},{
				iconCls: 'deppon_icons_cancel',
                tooltip: dict.dataDictionary.i18n('foss.dict.void'),//作废
                disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryDisableButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
                	if(record.get('extAttribute1')==dict.dataDictionary.ownership_company
                			&&record.get('termsCode')==dict.dataDictionary.brandType){
        				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.ownVehicleBrandCanNotBeCancelled'));
        				return;
        			}
                	dict.showQuestionMes(dict.dataDictionary.i18n('foss.dict.isVoidThisValue'),function(e){//是否要作废这条值？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				var dataDictionaryValueEntityList = new Array();
            				dataDictionaryValueEntityList.push({'virtualCode':record.get('virtualCode'),'termsCode':record.get('termsCode')});
            				var params = {'dataDictionaryVo':{'dataDictionaryValueEntityList':dataDictionaryValueEntityList}};
            				var successFun = function(json){
            					dict.showInfoMes(json.message);
            					me.getPagingToolbar().moveFirst();
            				};
            				var failureFun = function(json){
            					if(Ext.isEmpty(json)){
            						dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeout'));//请求超时
            					}else{
            						dict.showErrorMes(json.message);
            					}
            				};
            				var url = dict.realPath('deleteValue.action');
            				dict.requestJsonAjax(url,params,successFun,failureFun);
            			}
            		})
                	
                }
			}]

		},{
			text : dict.dataDictionary.i18n('foss.dict.theWordName'),//词条名称
			dataIndex : 'termsName'
		},{
			text : dict.dataDictionary.i18n('foss.dict.valueCode'),//值代码
			dataIndex : 'valueCode'
		},{
			text : dict.dataDictionary.i18n('foss.dict.valueName'),//值名称
			dataIndex : 'valueName'
		},{
			text : dict.dataDictionary.i18n('foss.dict.num'),//序号
			dataIndex : 'valueSeq'
		},{
			text : dict.dataDictionary.i18n('foss.dict.extendedField'),//扩展字段
			dataIndex : 'extAttribute1',
			renderer:function(value){
				if(value == dict.dataDictionary.PRIMARY.CODE){
					return dict.dataDictionary.PRIMARY.NAME;
				}else if(value == dict.dataDictionary.SECONDARY.CODE){
					return dict.dataDictionary.SECONDARY.NAME;
				}
				return value;
			}
		},{
			text : dict.dataDictionary.i18n('foss.dict.extendedField2'),//扩展字段2
			dataIndex : 'extAttribute2',
			renderer:function(value){
				if(value == dict.dataDictionary.WAYBILL.CODE){
					return dict.dataDictionary.WAYBILL.NAME;
				}else if(value == dict.dataDictionary.NOTWAYBILL.CODE){
					return dict.dataDictionary.NOTWAYBILL.NAME;
				}
				return value;
			}
		},{
			text : dict.dataDictionary.i18n('foss.dict.remarks'),//备注
			dataIndex : 'noteInfo'
		}];
		me.store = Ext.create('Foss.dict.dataDictionary.DataDictionaryStore',{
			autoLoad : false,//不自动加载
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_dict-indexDataDicionary_content').getQueryForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'dataDictionaryVo.dataDictionaryValueEntity.termsCode':
									queryForm.getForm().findField('termsCode').getValue(),//词代码
								'dataDictionaryVo.dataDictionaryValueEntity.valueName':
									queryForm.getForm().findField('valueName').getValue(),//名称
								'dataDictionaryVo.dataDictionaryValueEntity.valueCode':
									queryForm.getForm().findField('valueCode').getValue()//值代码
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
			text : dict.dataDictionary.i18n('foss.dict.void'),//作废
			disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryDisableButton'),
			hidden:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryDisableButton'),
			handler :function(){
				me.toVoid();
			} 
		},{
			text : dict.dataDictionary.i18n('foss.dict.add'),//新增
			disabled:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryAddButton'),
			hidden:!dict.dataDictionary.isPermission('queryDataDictionaryValueByEntity/dataDictionaryAddButton'),
			handler :function(){
				me.getValueAddWindow().show();
			} 
		} ];
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});


//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 值新增
 */
Ext.define('Foss.dict.dataDictionary.ValueAddWindow',{
	extend : 'Ext.window.Window',
	title : dict.dataDictionary.i18n('foss.dict.valueOfInformation'),
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//父元素（Foss.dict.dataDictionary.DataDictionaryGridPanel）
	closeAction : 'destroy',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :560,
	height :380,
	listeners:{
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		beforeshow:function(me){
			me.getValueForm().getForm().reset();//清空数据
			dict.changeFormElement(me.getValueForm());
		
		}
	},
	//值FORM
	valueForm:null,
    getValueForm : function(){
    	if(Ext.isEmpty(this.valueForm)){
    		this.valueForm = Ext.create('Foss.dict.dataDictionary.ValueForm');
    	}
    	return this.valueForm;
    },
    //重置数据
    resetValue:function(){
    	var me = this; 
    	me.getValueForm().getForm().reset();
    },
    //提交数据
    commitValue:function(){
    	var me = this;
    	if(me.getValueForm().getForm().isValid()){//校验form是否通过校验
    		me.dockedItems.items[1].items.items[1].setDisabled(true);
    		me.dockedItems.items[1].items.items[2].setDisabled(true);
    		var valueModel = Ext.create('Foss.dict.dataDictionary.DataDictionaryValueEntity');//创建一个值MODEL
        	var form = me.getValueForm().getForm();
    		form.updateRecord(valueModel);
        	if(valueModel.get('extAttribute1')==dict.dataDictionary.ownership_company
        			&&valueModel.get('termsCode')==dict.dataDictionary.brandType){
				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.ownVehicleBrandCanNotBeAdded'));
				return;
			}
        	if(valueModel.get('termsCode')=='BILL_RECEIVABLE__COLLECTION_TYPE'){
            	var extAttribute2 = form.findField('extAttribute2').getValue();
            	if(!Ext.isEmpty(extAttribute2.extAttributes2)){
        			valueModel.data.extAttribute1 = extAttribute2.extAttributes2;
        		}else{
        			dict.showErrorMes('请选择是否主营选项!');
        			return;
        		}
            	var extAttribute3 = form.findField('extAttribute3').getValue();
            	if(!Ext.isEmpty(extAttribute3.extAttributes3)){
        			valueModel.data.extAttribute2 = extAttribute3.extAttributes3;
        		}else{
        			dict.showErrorMes('请选择是否运单相关选项!');
        			return;
        		}
        	}
    		var params = {'dataDictionaryVo':{'dataDictionaryValueEntity':valueModel.data}};//组织新增数据
    		var successFun = function(json){
    			me.dockedItems.items[1].items.items[1].setDisabled(false);
    			me.dockedItems.items[1].items.items[2].setDisabled(false);
				dict.showInfoMes(json.message);//提示新增成功
				me.parent.getPagingToolbar().moveFirst();
				me.destroy();
			};
			var failureFun = function(json){
				me.dockedItems.items[1].items.items[1].setDisabled(false);
				me.dockedItems.items[1].items.items[2].setDisabled(false);
				if(Ext.isEmpty(json)){
					dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeout'));//请求超时
				}else{
					dict.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = dict.realPath('addValue.action');//请求路径新增

			dict.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getValueForm()];
		me.fbar = [{
			text : dict.dataDictionary.i18n('foss.dict.cancel'),//取消
			handler :function(){
				me.destroy();
			} 
		},{
			text : dict.dataDictionary.i18n('foss.dict.reset'),//重置
			handler :function(){
				me.resetValue();
			} 
		},{
			text : dict.dataDictionary.i18n('foss.dict.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
				me.commitValue();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 值-FORM
 */
Ext.define('Foss.dict.dataDictionary.ValueForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	height:260,
	collapsible: true,
    defaults : {
    	colspan : 1,
    	margin : '8 10 5 10',
    	maxLength:50,
    //	allowBlank:false,
    	labelWidth:60,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout: 'column',
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items  = [{
			name: 'termsCode',
			queryMode: 'local',
		    displayField: 'termsName',
		    valueField: 'termsCode',
			columnWidth:.5,
		    forceSelection : true,
		    store:dict.getStore(null,null,['termsCode','termsName'],
		    dict.dataDictionary.dataDictionary),
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.theWordName'),//词名称
	        xtype : 'combo',
	        listeners:{
				'change':function(me,newValue,oldValue,eOpts){
					var form = me.up('form');
					dict.changeFormElement(form);
				}
			}
		},{
			name: 'valueSeq',//序号
			allowBlank:false,
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.num'),
	        decimalPrecision:0,
			columnWidth:.5,
	        minValue:0,
	        maxValue:999999,
	        xtype : 'numberfield'
		},{
			name: 'valueCode',//值代码
			allowBlank:false,
			columnWidth:.5,
			regex:new RegExp('^[A-Za-z0-9_]+$'),
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.valueCode'),
	        xtype : 'textfield'
		},{
			name: 'valueName',//值名称
			allowBlank:false,
			columnWidth:.5,
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.valueName'),
	        xtype : 'textfield'
		},{
			name: 'extAttribute1',// 扩展字段1
			columnWidth:.5,
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.extendedField'),
	        allowBlank:true,
	        xtype : 'textfield'
		},{
			name: 'extAttribute4',// 扩展字段2
			columnWidth:.5,
			labelWidth:70,
	        fieldLabel: dict.dataDictionary.i18n('foss.dict.extendedField2'),
	        allowBlank:true,
	        xtype : 'textfield'
		},{
	 		xtype:'radiogroup',
	 		fieldLabel:'是否主营收入',//是否主营收入
	 		name:'extAttribute2',
	 		columns: 1,
			columnWidth:.5,
	 		defaultType:'radio',
	 		layout:'table',
	 		labelWidth:100,
	 		isFormField: true,
	 		defaults:{ 
	 			margin : '5 5 5 5'
	 		},
	 		items:[{
	 			boxLabel:'是',//是
	 			name:'extAttributes2',
	 			inputValue:dict.dataDictionary.PRIMARY.CODE
	 		},{
	 			boxLabel:'否',//否
	 			name:'extAttributes2',
	 			inputValue:dict.dataDictionary.SECONDARY.CODE
	 		}]
	 	},{
	 		xtype:'radiogroup',
	 		fieldLabel:'是否运单相关',//是否运单相关
	 		name:'extAttribute3',
			columnWidth:.5,
	 		columns: 1,
	 		defaultType:'radio',
	 		layout:'table',
	 		labelWidth:100,
	 		isFormField: true,
	 		defaults:{ 
	 			margin : '5 5 5 5'
	 		},
	 		items:[{
	 			boxLabel:'是',//是
	 			name:'extAttributes3',
	 			inputValue:dict.dataDictionary.WAYBILL.CODE
	 		},{
	 			boxLabel:'否',//否
	 			name:'extAttributes3',
	 			inputValue:dict.dataDictionary.NOTWAYBILL.CODE
	 		}]
	 	},{
			fieldLabel: dict.dataDictionary.i18n('foss.dict.remarks'),//备注
			name:'noteInfo',
			columnWidth:1,
		    allowBlank:true,
		    maxLength:200,
		    width:400,
		    colspan : 2,
			xtype:'textareafield'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 值修改
 */
Ext.define('Foss.dict.dataDictionary.ValueUpdateWindow',{
	extend : 'Ext.window.Window',
	title : dict.dataDictionary.i18n('foss.dict.valueOfInformation'),
	closable : true,
	modal : true,
	resizable:false,
	dataDictionaryValueEntity:null,//修改的值LIST
	parent:null,//父元素（Foss.dict.dataDictionary.DataDictionaryGridPanel）
	closeAction : 'destroy',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	width :560,
	height :380,
	listeners:{
		beforehide:function(me){
			me.getValueForm().getForm().reset();//清空数据
		},
		beforeshow:function(me){
			me.getValueForm().getForm().reset();//清空数据
			if(!Ext.isEmpty(me.dataDictionaryValueEntity)){//不为空则赋值
				var form = me.getValueForm().getForm();
				form.loadRecord(new Foss.dict.dataDictionary.DataDictionaryValueEntity(me.dataDictionaryValueEntity));//赋值
				if(me.dataDictionaryValueEntity.termsCode == 'BILL_RECEIVABLE__COLLECTION_TYPE'){
					form.findField('extAttribute2').setValue(me.dataDictionaryValueEntity.extAttribute1);	
					form.findField('extAttribute3').setValue(me.dataDictionaryValueEntity.extAttribute2);
				}else{// 给扩展字段1，扩展字段2赋值回显
					form.findField('extAttribute1').setValue(me.dataDictionaryValueEntity.extAttribute1);
					form.findField('extAttribute4').setValue(me.dataDictionaryValueEntity.extAttribute2);
				}
			}
		}
	},
	//值FORM
	valueForm:null,
    getValueForm : function(){
    	if(Ext.isEmpty(this.valueForm)){
    		this.valueForm = Ext.create('Foss.dict.dataDictionary.ValueForm');
    	}
    	return this.valueForm;
    },
    //提交数据
    commitValue:function(){
    	var me = this;
    	if(me.getValueForm().getForm().isValid()){//校验form是否通过校验
    		var valueModel = new Foss.dict.dataDictionary.DataDictionaryValueEntity(me.dataDictionaryValueEntity)//创建一个值MODEL
        	var form = me.getValueForm().getForm();
    		form.updateRecord(valueModel);
    		if(valueModel.get('extAttribute1')==dict.dataDictionary.ownership_company
    				&&valueModel.get('termsCode')==dict.dataDictionary.brandType){
				dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.ownVehicleBrandCanNotBeChanged'));
				return;
			}
			
			var termsCode = form.findField('termsCode').getValue();
			if(termsCode == 'BILL_RECEIVABLE__COLLECTION_TYPE'){// 词类型是应收单收款类别的赋值
				var extAttribute2 = form.findField('extAttribute2').getValue();
				if(!Ext.isEmpty(extAttribute2)){
					valueModel.data.extAttribute1 = extAttribute2.extAttributes2;
				}
				var extAttribute3 = form.findField('extAttribute3').getValue();
				if(!Ext.isEmpty(extAttribute3)){
					valueModel.data.extAttribute2 = extAttribute3.extAttributes3;
				}
			}else{// 非应收单收款类别的赋值
				var extAttributeValue1 = form.findField('extAttribute1').getValue();
				if(!Ext.isEmpty(extAttributeValue1)){
					valueModel.data.extAttribute1 = extAttributeValue1;
				}

				var extAttributeValue2 = form.findField('extAttribute4').getValue();
				if(!Ext.isEmpty(extAttributeValue2)){
					valueModel.data.extAttribute2 = extAttributeValue2;
				}
			}
			
    		var params = {'dataDictionaryVo':{'dataDictionaryValueEntity':valueModel.data}};//组织新增数据
    		var successFun = function(json){
				dict.showInfoMes(json.message);//提示新增成功
				me.parent.getPagingToolbar().moveFirst();
				me.destroy();
			};
			var failureFun = function(json){
				if(Ext.isEmpty(json)){
					dict.showErrorMes(dict.dataDictionary.i18n('foss.dict.requestTimeout'));//请求超时
				}else{
					dict.showErrorMes(json.message);//提示失败原因
				}
			};
			var url = dict.realPath('updateValue.action');//请求路径新增

			dict.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getValueForm()];
		me.fbar = [{
			text : dict.dataDictionary.i18n('foss.dict.cancel'),//取消
			handler :function(){
				me.destroy();
			} 
		},{
			text : dict.dataDictionary.i18n('foss.dict.reset'),//重置
			handler :function(){
				me.getValueForm().getForm().loadRecord(new Foss.dict.dataDictionary.DataDictionaryValueEntity(me.dataDictionaryValueEntity));//赋值
			} 
		},{
			text : dict.dataDictionary.i18n('foss.dict.confirm'),//确定
			cls:'yellow_button',
			margin:'0 0 0 235',
			handler :function(){
				me.commitValue();
			} 
		}];
		me.callParent([cfg]);
	}
});

//------------------------------新增修改--------------------------------------------------------------------

/**
 * @description 数据字典主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_dict-indexDataDicionary_content')) {
		return;
	}
	dict.searchdataDictionary();
	var queryForm = Ext.create('Foss.dict.dataDictionary.QueryDataDictionaryForm');//查询FORM
	var dataDictionaryGrid = Ext.create('Foss.dict.dataDictionary.DataDictionaryGridPanel');//查询结果GRID
	Ext.create('Ext.panel.Panel', {
		id : 'T_dict-indexDataDicionary_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getDataDictionaryGrid : function() {
			return dataDictionaryGrid;
		},
		items : [queryForm, dataDictionaryGrid],
		renderTo : 'T_dict-indexDataDicionary-body'
	});
});