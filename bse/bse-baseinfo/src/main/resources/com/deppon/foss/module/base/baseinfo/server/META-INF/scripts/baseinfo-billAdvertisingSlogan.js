/**
 * 单据广告model									Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel
 * 单据广告store									Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityStore
 * 单据广告form									Foss.baseinfo.billAdvertisingSlogan.QueryConditionForm
 * 单据广告grid									Foss.baseinfo.billAdvertisingSlogan.QueryResultGrid
 * 单据广告winForm								Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWinForm
 * 单据广告winGrid								Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWinGrid
 * 单据广告win									Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.advertiseType = {dg:'DG',ddg:'DDG'};						//标识 是单据广告DG还是部门单据广告DDG
//初始化界面数据 
baseinfo.billAdvertisingSlogan.initWinData = function(win,viewState,formRecord,gridData,advertiseType){
	//单据广告和部门广告 form 加载数据
	win.down('form').loadRecord(formRecord);
	//单据广告界面
	if(baseinfo.advertiseType.dg === advertiseType){
		//添加广告语按钮控制
		baseinfo.operateWinBtn(win,viewState,'');
		win.down('button').setDisabled(baseinfo.viewState.update != viewState);
		if(baseinfo.viewState.add != viewState){
			win.down('grid').store.load();
		}else{
			win.down('grid').store.loadData([]);
		}
	}else if(baseinfo.advertiseType.ddg === advertiseType){
		win.down('form').down('dynamicorgcombselector').setCombValue(formRecord.get('orgName'),formRecord.get('orgCode'));
	}
	return win;
};
//保存事件 
baseinfo.billAdvertisingSlogan.submitEntity = function(win,viewState,operatEntity,advertiseType){
	var aType = baseinfo.advertiseType.dg === advertiseType		//标识 是单据广告DG还是部门单据广告DDG
		,grid = aType?Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content').getQueryGrid():Ext.getCmp('Foss_baseinfo_BillSloganEntityWinGrid_ID')
		,url = aType?baseinfo.realPath('addBillSloganEntity.action'):baseinfo.realPath('addBillSloganAppOrgEntity.action')
		,m_success = baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.saveSuccess'),m_failure = baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.saveFail'),m_dateError = baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.dataError')	
		,objectVo = {};
	if(aType){
		objectVo.billSloganEntity = operatEntity;
	}else{
		objectVo.billSloganAppOrgEntity = operatEntity;
	}
	//如果是修改 则调用修改 action
	if(baseinfo.viewState.update === viewState){
		url = aType?baseinfo.realPath('updateBillSloganEntity.action'):baseinfo.realPath('updateBillSloganAppOrgEntity.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(baseinfo.operatorCount.successV === result.objectVo.returnInt){
			//预操作grid加载数据
			grid.store.loadPage(1);
			if(aType){
				if(baseinfo.viewState.add === viewState){
					//只有保存后，才能点击"添加部门广告语"；
					win.down('button').setDisabled(false);
					// 把虚拟编码 写到 win表单上
					win.formRecord.set('virtualCode',result.objectVo.billSloganEntity.virtualCode);
				}
				//查看状态下 只有 取消按钮可用 [添加部门广告语,取消]按钮分别占 0和1
				baseinfo.operateWinBtn(win,viewState,baseinfo.operateType.save);
				//form表单元素都设置成只读
				baseinfo.formFieldSetReadOnly(true,win.down('form'));
			}else{
				win.hide();
			}
			//提示保存成功
			baseinfo.showInfoMsg(m_success);
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//作废事件
baseinfo.billAdvertisingSlogan.deleteEntityByCode = function(delType,operatRecord,grid,advertiseType){
//	var grid = Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content').getQueryGrid()
	var aType = Ext.isEmpty(advertiseType)||(baseinfo.advertiseType.dg === advertiseType)		//标识 是单据广告DG还是部门单据广告DDG
		,url = aType?baseinfo.realPath('deleteBillSloganEntity.action'):baseinfo.realPath('deleteBillSloganAppOrgEntity.action')
		,objectVo = {},keyId = aType?'virtualCode':'id';
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delType)&&baseinfo.delType===delType){
			var codeStr = '';
			//批量作废
			url = aType?baseinfo.realPath('deleteBillSloganEntity.action'):baseinfo.realPath('deleteBillSloganAppOrgEntity.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get(keyId+'');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get(keyId+'');
		}
		Ext.MessageBox.buttonText.yes = baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
baseinfo.billAdvertisingSlogan.entityIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('billSloganEntityExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//单据广告Model
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel', {
extend: 'Ext.data.Model',
fields : [{name:'sloganCode',type:'string'},//广告语代码
      {name:'sloganName',type:'string'},//广告语名称
      {name:'subSystem',type:'string'},//所属子系统
      {name:'subSystemModule',type:'string'},//子系统功能模块
      {name:'content',type:'string'},//广告语内容
      {name:'sloganSort',type:'string'},//广告语类别
      {name:'active',type:'string'},//是否启用
      {name:'virtualCode',type:'string'}//虚拟编码
      ]
});
//部门单据广告Model
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityModel', {
	extend: 'Ext.data.Model',
	fields : [//适用部门Code
        {name:'orgCode',type:'string'},
        //适用部门CodeName
        {name:'orgName',type:'string'},
        //部门广告语内容
        {name:'sloganContent',type:'string'},
        //广告语类型：DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS：短信广告语 DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL：单据广告语
        {name:'sloganSort',type:'string'},
        //是否启用
        {name:'active',type:'string'},
        //所属广告语code
        {name:'sloganCode',type:'string'}]
	});
//------------------------------------STORE----------------------------------
//单据广告STORE
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBillSloganEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.billSloganEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//部门单据广告STORE
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBillAdvertisingSloganForDepts.action'),
		reader : {
			type : 'json',
			root : 'objectVo.billSloganAppOrgEntityList'
//				,
//			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//单据广告 查询条件
Ext.define('Foss.baseinfo.billAdvertisingSlogan.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :140,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 4
    },
    record:null,												//绑定的model Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this,store = FossDataDictionary.getDataDictionaryStore('BILL_ADVERTISEMENT');
		return [{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adCode'),								//广告语代码
			name: 'sloganCode'
		},{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adName'),							//广告语名称
			name:'sloganName',
			colspan:3,
			xtype:'combo',
			store:store,
			forceSelection:true,
			displayField: 'valueName',
			valueField: 'valueName'
		},FossDataDictionary.getDataDictionaryCombo('SUB_SYSTEM',{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystem'),								//信息部性质
			name: 'subSystem',
			hidden:true
		}),{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystemFn'),						//子系统功能模块
			name:'subSystemModule',
			hidden:true
		},{
			xtype:'container',
			colspan:4,
			defaultType:'button',
			layout:'column',
			items:[{
				width: 75,
				text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganQueryButton'),
				hidden:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganQueryButton'),
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.99
			},{
				xtype:'button',
				width: 75,
				text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganQueryButton'),
				hidden:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganQueryButton'),
				cls:'yellow_button',
				handler : function() {
					var grid  = Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content').getQueryGrid();//得到grid
					grid.store.loadPage(1);//用分页的moveFirst()方法
				}
			}]
		}];
	}
//	buttons : [ {
//		text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.query'),
//		cls:'yellow_button',
//		handler : function() {
//			var grid  = Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content').getQueryGrid();//得到grid
//			grid.store.loadPage(1);//用分页的moveFirst()方法
//		}
//	}, {
//		text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.reset'),
//		cls:'yellow_button',
//		handler : function() {
//			this.up('form').getForm().reset();
//		}
//	}]
});
//单据广告 界面form
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
	frame:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.billAdvertisingSlogan.PickupAndDeliveryBillSloganEntityStore
    viewState:null,
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
			allowBlank:false,
//			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this,store = FossDataDictionary.getDataDictionaryStore('BILL_ADVERTISEMENT');
		return [FossDataDictionary.getDataDictionaryCombo('SUB_SYSTEM',{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystem'),								//所属子系统
			allowBlank:true,hidden:true,
			name: 'subSystem',
			readOnly:(baseinfo.viewState.view === config.viewState)
		}),{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystemFn'),							//单据广告名称
			allowBlank:true,hidden:true,
			name:'subSystemModule'
		},{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adCode'),								//违禁品类型
			name: 'sloganCode',
			readOnly:true
		},{
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adName'),							//上机部门名称
			name:'sloganName',
			xtype:'combo',
			store:store,
			forceSelection:true,
			displayField: 'valueName',
			valueField: 'valueName',
	    	listeners:{
	    		select:function(field,recods){
	    			field.up('form').getForm().findField('sloganCode').setValue(recods[0].get('valueCode'));
	    		}
	    	}
		},{
			colspan:2,
			xtype:'textareafield',
			fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adContent'),	
			name:'content',
			allowBlank:true,
			width:518,
			height:80,
	    	maxLength:250
		}];
	}
});
//TODO 部门单据广告  界面form
//------------------------------------GRID----------------------------------
//单据广告 查询结果grid
Ext.define('Foss.baseinfo.billAdvertisingSlogan.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	frame: true,
	//得到BBAR（分页）
	pagingToolbar : null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getStore();
		me.listeners = me.getMyListeners(config);
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加头部按钮
		me.tbar = me.getTbar(config);
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getTbar:function(config){
		var me = this;
		return[{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.add'),								//新增
			disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganAddButton'),
			hidden:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				me.addBillSloganEntity({}).show();
			} 
		},'-', {
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.void'),//作废
			disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganVoidButton'),
			hidden:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganVoidButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				baseinfo.billAdvertisingSlogan.deleteEntityByCode(baseinfo.delType,null,me,baseinfo.advertiseType.dg);
			} 
		}];
	},
	getPagingToolbar : function(config) {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20,
				plugins: Ext.create('Deppon.ux.PageSizePlugin', { 
				//设置的最大分页数，防止用户输入太大数量，影响服务器性能
                limitWarning: '最大显示记录不能超过'    })
				
			});
		}
		return this.pagingToolbar;
	},
	//得到单据广告编辑窗体 得到单据广告编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganEntityWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
		return baseinfo.billAdvertisingSlogan.initWinData(win,viewState,formRecord,[],baseinfo.advertiseType.dg);
	},
	addBillSloganEntityWin:null,						//新增基单据广告
	addBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.addBillSloganEntityWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.addAd'),baseinfo.viewState.add,param);
	},
	updateBillSloganEntityWin:null,						//修改基单据广告
	updateBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.updateBillSloganEntityWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.alterAd'),baseinfo.viewState.update,param);
	},
	viewBillSloganEntityWin:null,						//查看基单据广告
	viewBillSloganEntity:function(param){
		return this.getBillSloganEntityWin(this.viewBillSloganEntityWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.viewAd'),baseinfo.viewState.view,param);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 单据广告
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewBillSloganEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//广告语代码
								'objectVo.billSloganEntity.sloganCode':entity.sloganCode,
								//广告语名称
								'objectVo.billSloganEntity.sloganName':entity.sloganName,
								//所属子系统
								'objectVo.billSloganEntity.subSystem':entity.subSystem,
								//子系统功能模块
								'objectVo.billSloganEntity.subSystemModule':entity.subSystemModule
							}
						});	
					}
				}
		    }
		});
	},
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganUpdateButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateBillSloganEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganVoidButton'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.billAdvertisingSlogan.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,baseinfo.advertiseType.dg);
                }
            }]
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adCode'),									//单据广告编码
			flex:1,
			dataIndex : 'sloganCode'
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adName'),									//单据广告名称
			flex:1,
			dataIndex : 'sloganName'
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystem'),									//上机部门名称
			dataIndex : 'subSystem',hidden:true,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'SUB_SYSTEM');
			}
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.subSystemFn'),hidden:true,											//队员
			dataIndex : 'subSystemModule'
		}];
	}
});
// 部门单据广告  grid
Ext.define('Foss.baseinfo.BillSloganEntityWinGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_baseinfo_BillSloganEntityWinGrid_ID',
	title : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.deptAd'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	viewState:null,										//查看状态
	frame: true,
	//得到部门单据广告编辑窗体 得到部门单据广告编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganAppOrgWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return baseinfo.billAdvertisingSlogan.initWinData(win,viewState,formRecord,gridData,baseinfo.advertiseType.ddg);
	},
	addBillSloganAppOrgWin:null,						//新增基部门单据广告
	addBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.addBillSloganAppOrgWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.addDeptBillAd'),baseinfo.viewState.add,param);
	},
	updateBillSloganAppOrgWin:null,						//修改基部门单据广告
	updateBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.updateBillSloganAppOrgWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.alterDeptBillAd'),baseinfo.viewState.update,param);
	},
	viewBillSloganAppOrgWin:null,						//查看基部门单据广告
	viewBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.viewBillSloganAppOrgWin,baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.viewDeptBillAd'),baseinfo.viewState.view,param);
	},
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.columns = me.getColumns(config);
		me.store = me.getGridStore(config);
		me.listeners = me.getMyListeners(config);
		//添加头部按钮
		me.tbar = me.getTbar();
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'MULTI',checkOnly:true});
		//添加底部按钮
		me.dockedItems = me.getMyDockedItems(config);
		me.callParent([cfg]);
	},
	//监听事件
	getMyListeners:function(config){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	},
	    	//查看 偏线代理网点
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewBillSloganAppOrg(param).show();
	    	}
	    };
	},
	getMyDockedItems:function(config){
		var me = this;
		return [{
            xtype: 'toolbar',
            dock: 'bottom',
            items: [{
				text:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.void'),								//作废
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler :function(){
					baseinfo.billAdvertisingSlogan.deleteEntityByCode(baseinfo.delType, null, me, baseinfo.advertiseType.ddg);
				}
			}]
        }];
	},
	getTbar:function(config){
		var me = this;
		return [{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.BaddDeptBillAd'),							//添加网点
			name:'grid_addDeptBtn_name',
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				var param = {},formRecord = Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityModel');
				param.formRecord = formRecord;
				formRecord.set('sloganCode',me.up('window').formRecord.get('virtualCode'));
				me.addBillSloganAppOrg(param).show();
			} 
		}];
	},
	//表格数据源
	getGridStore:function(config){
		var me = this;
		return Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(operation,{
						params : {
							//代理公司 虚拟code
							'objectVo.billSloganAppOrgEntity.sloganCode':me.up('window').formRecord.get('virtualCode')
						}
					});	
				}
		    }
		});
	},
	//表格数据列
	getColumns:function(config){
		var me = this;
		return [{
			align : 'center',
			xtype : 'actioncolumn',
			hidden:(baseinfo.viewState.view === config.viewState),
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.update'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateBillSloganAppOrg(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
                	baseinfo.billAdvertisingSlogan.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,baseinfo.advertiseType.ddg);
                }
            }]
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.appDept'),						//适用部门
			flex:1,
			dataIndex : 'orgName'
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adContent'),						//广告语内容
			flex:1,
			dataIndex : 'sloganContent'
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.billAdvertisingSlogan.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.billAdvertisingSlogan.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-billAdvertisingSloganIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-billAdvertisingSloganIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		items : [ queryForm, queryGrid,{
			xtype:'button',
			text:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.void'),								//作废
			disabled:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganVoidButton'),
			hidden:!baseinfo.billAdvertisingSlogan.isPermission('billAdvertisingSloganIndex/billAdvertisingSloganVoidButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				baseinfo.billAdvertisingSlogan.deleteEntityByCode(baseinfo.delType,null,queryGrid,baseinfo.advertiseType.dg);
			}
		}] 
	}));
});
//------------------------------------WINDOW--------------------------------
//单据广告界面win
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.addAd'),								//新增单据广告   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :600,
	height :550,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
			var winGrid = Ext.getCmp('Foss_baseinfo_BillSloganEntityWinGrid_ID');
			if(!Ext.isEmpty(winGrid)){
				winGrid.destroy();
			}
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//单据广告表单Form
	editGrid:null,											//单据广告表格Grid
	formRecord:null,										//单据广告实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//单据广告 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.billAdvertisingSlogan.BillSloganEntityWinForm',{'height':150,'viewState':config.viewState,'formRecord':config.formRecord});
		me.editGrid = Ext.create('Foss.baseinfo.BillSloganEntityWinGrid',{'height':250,'viewState':config.viewState});
		me.items = [me.editForm,me.editGrid];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.billAdvertisingSlogan.submitEntity(me,me.viewState,me.formRecord.data,baseinfo.advertiseType.dg);
			}
		}];
	}
});
//部门单据广告界面win
Ext.define('Foss.baseinfo.billAdvertisingSlogan.BillSloganAppOrgEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.addDeptAd'),								//新增部门广告语   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :300,
	height :200,	
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//单据广告表单Form
	editGrid:null,											//单据广告表格Grid
	formRecord:null,										//单据广告实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//单据广告 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{	
			xtype : 'form',
			layout:'column',
			defaults:{
				allowBlank:baseinfo.viewState.view === config.viewState,
				readOnly:baseinfo.viewState.view === config.viewState
			},
		    items:[{
		    	xtype:'dynamicorgcombselector',
		    	name:'orgCode',
		    	fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.appDept')
		    },{
		    	xtype:'textareafield',
		    	name:'sloganContent',
		    	fieldLabel:baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.billAdvertisingSlogan.adContent'),
		    	height:60,
		    	maxLength:250
		    }]
		}];
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.reset'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.down('form').getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.save'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.down('form').getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.billAdvertisingSlogan.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.billAdvertisingSlogan.submitEntity(me,me.viewState,me.formRecord.data,baseinfo.advertiseType.ddg);
			}
		}];
	}
});

