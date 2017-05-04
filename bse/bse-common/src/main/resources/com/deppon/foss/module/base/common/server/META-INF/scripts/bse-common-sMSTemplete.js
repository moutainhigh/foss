/**
 * 短信模板model									Foss.common.sMSTemplete.SMSTemplateEntityModel
 * 短信模板store									Foss.common.sMSTemplete.SMSTemplateEntityStore
 * 短信模板form									Foss.common.sMSTemplete.QueryConditionForm
 * 短信模板grid									Foss.common.sMSTemplete.QueryResultGrid
 * 短信模板winForm								Foss.common.sMSTemplete.SMSTemplateEntityWinForm
 * 短信模板winGrid								Foss.common.sMSTemplete.SMSTemplateEntityWinGrid
 * 短信模板win									Foss.common.sMSTemplete.SMSTemplateEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
//初始化界面数据 
common.sMSTemplete.initWinData = function(win,viewState,formRecord,gridData,advertiseType){
	//短信模板和部门模板 form 加载数据
	win.down('form').loadRecord(formRecord);
	//短信模板界面
	if(common.levelType.p === advertiseType){
		//添加广告语按钮控制
		common.operateWinBtn(win,viewState,'');
		win.down('button').setDisabled(common.viewState.update != viewState);
		if(common.viewState.add != viewState){
			win.down('grid').store.loadPage(1);
		}else{
			win.down('grid').store.loadData([]);
		}
	}else if(common.levelType.c === advertiseType){
		win.down('form').down('dynamicorgcombselector').setCombValue(formRecord.get('orgName'),formRecord.get('orgId'));
	}
	return win;
};
//保存事件 
common.sMSTemplete.submitEntity = function(win,viewState,operatEntity,advertiseType){
	var aType = common.levelType.p === advertiseType		//标识 是短信模板DG还是部门短信模板DDG
		,grid = aType?Ext.getCmp('T_common-sMSTempleteIndex_content').getQueryGrid():Ext.getCmp('Foss_common_SMSTemplateEntityWinGrid_ID')
		,url = aType?common.realPath('addSMSTemplateEntity.action'):common.realPath('addTemplateAppOrgEntity.action')
		,m_success = common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MsaveSuccess'),m_failure = common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MsaveFaliur'),m_dateError = common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MdataError')	
		,objectVo = {};
	if(aType){
		objectVo.smsTemplateEntity = operatEntity;
	}else{
		objectVo.templateAppOrgEntity = operatEntity;
	}
	//如果是修改 则调用修改 action
	if(common.viewState.update === viewState){
		url = aType?common.realPath('updateSMSTemplateEntity.action'):common.realPath('updateTemplateAppOrgEntity.action');
	}
	common.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(common.operatorCount.successV === result.objectVo.returnInt){
			//预操作grid加载数据
			grid.store.loadPage(1);
			if(aType){
				if(common.viewState.add === viewState){
					//只有保存后，才能点击"添加部门模板语"；
					win.down('button').setDisabled(false);
					// 把虚拟编码 写到 win表单上
					win.formRecord.set('virtualCode',result.objectVo.smsTemplateEntity.virtualCode);
				}
				//查看状态下 只有 取消按钮可用 [添加部门模板语,取消]按钮分别占 0和1
				common.operateWinBtn(win,viewState,common.operateType.save);
				//form表单元素都设置成只读
				common.formFieldSetReadOnly(true,win.down('form'));
			}else{
				win.hide();
			}
			//提示保存成功
			common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),m_success);
		}else{
			common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),result.message);
		}
	},function(result){
		grid.store.loadPage(1);
		common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),result.message);
	});
};
//作废事件
common.sMSTemplete.deleteEntityByCode = function(delType,operatRecord,grid,advertiseType){
	var aType = common.levelType.p === advertiseType		//标识 是短信模板DG还是部门短信模板DDG
		,grid = aType?Ext.getCmp('T_common-sMSTempleteIndex_content').getQueryGrid():Ext.getCmp('Foss_common_SMSTemplateEntityWinGrid_ID')
		,url = aType?common.realPath('deleteSMSTemplateEntity.action'):common.realPath('deleteTemplateAppOrgEntity.action')
		,objectVo = {},keyId = aType?'virtualCode':'id';
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert('提醒',common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MmustChooseOne'));
	}else{	
		for(var i=0;i<selection.length;i++){
			if(selection[i].get('active')=='N'){
				Ext.MessageBox.alert('提醒',common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.SelectOnlyVoidEnabled'));
				return ;
			}
		}
		if(!Ext.isEmpty(operatRecord)&&operatRecord.get('active')=='N'){
			Ext.MessageBox.alert('提醒',common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.SelectOnlyVoidEnabled'));
			return;
		}
		if(!Ext.isEmpty(delType)&&common.delType===delType){
			var codeStr = '';
			//批量作废
			url = aType?common.realPath('deleteSMSTemplateEntity.action'):common.realPath('deleteTemplateAppOrgEntity.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get(keyId+'');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get(keyId+'');
		}
		Ext.MessageBox.buttonText.yes = common.sMSTemplete.i18n('foss.common.share.Ok');
		Ext.MessageBox.buttonText.no = common.sMSTemplete.i18n('foss.common.share.Cancel');
		Ext.Msg.confirm(common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Malert'),common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MisDeletRecord'),function(btn,text) {
			if (btn == 'yes') {
				common.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MdeletSuccess'));
				},function(result){
					common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),result.message);
				});
			}
		});
	}
};
common.sMSTemplete.entityIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	//templateAppOrgEntityIsExist
	var url = common.realPath('sMSTempleteEntityIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	common.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MrepeatAlter')+fieldLabel+common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Mend'));
		}
	},function(result){
		field.markInvalid(common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.MrepeatAlter')+fieldLabel+common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Mend'));
	});
};

//------------------------------------MODEL----------------------------------
//短信模板Model
Ext.define('Foss.common.sMSTemplete.SMSTemplateEntityModel', {
extend: 'Ext.data.Model',
fields : [{name:'smsCode',type:'string'}, //模板代码
      {name:'smsName',type:'string'}, //模板名称
      {name:'subSystem',type:'string'},//所属子系统
      {name:'subSystemModule',type:'string'},//子系统功能模块
      {name:'content',type:'string'},//模板内容
      {name:'active',type:'string'},//是否启用
      {name:'virtualCode',type:'string'}, //虚拟编码
      {name:'createUser',type:'string'}, //创建人
      {name:'createDate',type:'date',convert: dateConvert,defaultValue:null}, //创建时间
      {name:'modifyUser',type:'string'}, //修改人
      {name:'modifyDate',type:'date',convert: dateConvert,defaultValue:null}, //修改时间
      ]
});
//部门短信模板Model
Ext.define('Foss.common.sMSTemplete.TemplateAppOrgEntityModel', {
	extend: 'Ext.data.Model',
	fields : [//所属部门Code
        {name:'orgId',type:'string'},
        {name:'orgName',type:'string'},
        //部门短信模板内容
        {name:'smsContent',type:'string'},
        //是否启用
        {name:'active',type:'string'},
        //短信模板虚拟Code
        {name:'smsVirtualCode',type:'string'}]
	});
//------------------------------------STORE----------------------------------
//短信模板STORE
Ext.define('Foss.common.sMSTemplete.SMSTemplateEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.common.sMSTemplete.SMSTemplateEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : common.realPath('querySMSTemplateEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smsTemplateEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//部门短信模板STORE
Ext.define('Foss.common.sMSTemplete.TemplateAppOrgEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.common.sMSTemplete.TemplateAppOrgEntityModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : common.realPath('queryTemplateAppOrgEntityByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.templateAppOrgEntityList'
		}
	}
});
//------------------------------------FORM----------------------------------
//短信模板 查询条件
Ext.define('Foss.common.sMSTemplete.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: common.sMSTemplete.i18n('foss.common.share.queryCondition'),
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
    record:null,												//绑定的model Foss.common.sMSTemplete.SMSTemplateEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this,store = FossDataDictionary.getDataDictionaryStore('SMS_TEMPLETE');
		return [{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempCode'),								//模板代码
			name: 'smsCode'
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempName'),							//模块名称
			name:'smsName',
			queryMode: 'local',
			xtype:'combo',colspan:3,
			store:store,
			displayField: 'valueName',
			valueField: 'valueName'
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subSystem'),							//所属子系统
			hidden:true,name:'subSystem'
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'),						//子系统功能模块
			hidden:true,name:'subSystemModule'
		},{
			xtype:'container',
			colspan:4,
			defaultType:'button',
			layout:'column',
			items:[{
				width: 75,
				columnWidth:.17,
				text : common.sMSTemplete.i18n('foss.common.share.reset'),
				disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteQueryButton'),
				hidden:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteQueryButton'),
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.66
			},{
				xtype:'button',
				columnWidth:.17,
				width: 75,
				text : common.sMSTemplete.i18n('foss.common.share.query'),
				disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteQueryButton'),
				hidden:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteQueryButton'),
				cls:'yellow_button',
				handler : function() {
					var grid  = Ext.getCmp('T_common-sMSTempleteIndex_content').getQueryGrid();//得到grid
					grid.store.loadPage(1);//用分页的moveFirst()方法
				}
			}]
		}];
	}
});
//短信模板 界面form
Ext.define('Foss.common.sMSTemplete.SMSTemplateEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
	frame:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.common.sMSTemplete.SMSTemplateEntityModel
    formStore:null,													//绑定的formStore Foss.common.sMSTemplete.PickupAndDeliverySMSTemplateEntityStore
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
			readOnly:(common.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this,store = FossDataDictionary.getDataDictionaryStore('SMS_TEMPLETE');
		return [{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subSystem'),							//短信模板编码
			hidden:true,name:'subSystem',allowBlank:true
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'),							//短信模板名称
			hidden:true,name:'subSystemModule',allowBlank:true
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempCode'),								//模块代码
			name: 'smsCode',
			readOnly:true
		},{
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempName'),							//上机部门名称
			name:'smsName',
			xtype:'combo',
			store:store,
			displayField: 'valueName',
			valueField: 'valueName',
			forceSelection:true,
	    	listeners:{
	    		select:function(field,recods){
	    			field.up('form').getForm().findField('smsCode').setValue(recods[0].get('valueCode'));
	    		}
	    	}
		},{
			colspan:2,
			xtype:'textareafield',
			fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempContent'),	
			name:'content',
			allowBlank:false,
			width:518,
			height:80,
	    	maxLength:500
		},{
			name:'virtualCode',
			allowBlank:true,
			hidden:true
		}];
	}
});
//------------------------------------GRID----------------------------------
//短信模板 查询结果grid
Ext.define('Foss.common.sMSTemplete.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.TresultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    bodyCls:'autoHeight',
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: common.sMSTemplete.i18n('foss.common.share.queryResultIsEmpty'),							//查询结果为空
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
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Badd'),								//新增
			disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteAddButton'),
			hidden:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteAddButton'),
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				me.addSMSTemplateEntity({}).show();
			} 
		},'-', {
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),								//作废
			disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteCancelButton'),
			hidden:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteCancelButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				common.sMSTemplete.deleteEntityByCode(common.delType,null,Ext.getCmp('T_common-sMSTempleteIndex_content').getQueryGrid(),common.levelType.p);
			} 
		}];
	},
	getPagingToolbar : function(config) {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//得到短信模板编辑窗体 得到短信模板编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getSMSTemplateEntityWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
		return common.sMSTemplete.initWinData(win,viewState,formRecord,[],common.levelType.p);
	},
	addSMSTemplateEntityWin:null,						//新增基短信模板
	addSMSTemplateEntity:function(param){
		return this.getSMSTemplateEntityWin(this.addSMSTemplateEntityWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TaddTemp'),common.viewState.add,param);
	},
	updateSMSTemplateEntityWin:null,						//修改基短信模板
	updateSMSTemplateEntity:function(param){
		return this.getSMSTemplateEntityWin(this.updateSMSTemplateEntityWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TupdateTemp'),common.viewState.update,param);
	},
	viewSMSTemplateEntityWin:null,						//查看基短信模板
	viewSMSTemplateEntity:function(param){
		return this.getSMSTemplateEntityWin(this.viewSMSTemplateEntityWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TviewTemp'),common.viewState.view,param);
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
	    	//查看 短信模板
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewSMSTemplateEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_common-sMSTempleteIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//模板代码
								'objectVo.smsTemplateEntity.smsCode':entity.smsCode,
								//模板名称
								'objectVo.smsTemplateEntity.smsName':entity.smsName,
								//所属子系统
								'objectVo.smsTemplateEntity.subSystem':entity.subSystem,
								//子系统功能模块
								'objectVo.smsTemplateEntity.subSystemModule':entity.subSystemModule
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
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Boperator'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bupdate'),
                disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteUpdateButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateSMSTemplateEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),
                disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteCancelButton'),
//                disabled:common.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				common.sMSTemplete.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,common.levelType.p);
                }
            }]
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSTemplete.tempCode'),									//短信模板编码
			flex:1,
			dataIndex : 'smsCode'
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSTemplete.tempName'),									//短信模板名称
			flex:1,
			dataIndex : 'smsName'
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subSystem'),									//上机部门名称
			flex:1,
			hidden:true,dataIndex : 'subSystem'
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.subFnSystem'),								//子系统功能模块
			flex:1,
			hidden:true,dataIndex : 'subSystemModule'
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSTemplete.tempState'),									//短信模板状态
			flex:0.5,
			dataIndex : 'active',
			renderer:function(value){
				if('Y' == value){
				return '已启用';
				}else{
				return '已禁止';
				}
			}
		},{
			text : common.sMSTemplete.i18n('foss.common.toDoMsg.createUser'),									//创建人
			flex:0.5,
			dataIndex : 'createUser'
		},{
			text : common.sMSTemplete.i18n('foss.common.toDoMsg.createTime'),									//创建时间
			flex:1,
			dataIndex : 'createDate',
			renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
				}
				return v;
			}
		},{
			text : common.sMSTemplete.i18n('foss.common.toDoMsg.modifyUser'),									//修改人
			flex:0.5,
			dataIndex : 'modifyUser'
		},{
			text : common.sMSTemplete.i18n('foss.common.toDoMsg.modifyDate'),									//修改时间
			flex:1,
			dataIndex : 'modifyDate',
			renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
				}
				return v;
			}
		}];
	}
});
// 部门短信模板  grid
Ext.define('Foss.common.sMSTemplete.SMSTemplateEntityWinGrid', {
	extend: 'Ext.grid.Panel',
	id:'Foss_common_SMSTemplateEntityWinGrid_ID',
	title : common.sMSTemplete.i18n('foss.common.sMSTemplete.TdeptTemp'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: common.sMSTemplete.i18n('foss.common.share.queryResultIsEmpty'),							//查询结果为空
	viewState:null,										//查看状态
	frame: true,
	//得到部门短信模板编辑窗体 得到部门短信模板编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getBillSloganAppOrgWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.common.sMSTemplete.TemplateAppOrgEntityModel'):param.formRecord;
		var gridData = Ext.isEmpty(param.gridDate)?[]:param.gridDate;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.common.sMSTemplete.TemplateAppOrgEntityWin',{
				'title':title,
				'sourceGrid':this,
				'viewState':viewState,
				'formRecord':formRecord,
				'gridDate':gridData
			});
		}
		//加载数据
		return common.sMSTemplete.initWinData(win,viewState,formRecord,gridData,common.levelType.c);
	},
	addBillSloganAppOrgWin:null,						//新增基部门短信模板
	addBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.addBillSloganAppOrgWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TaddDeptTemp'),common.viewState.add,param);
	},
	updateBillSloganAppOrgWin:null,						//修改基部门短信模板
	updateBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.updateBillSloganAppOrgWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TalterDeptTemp'),common.viewState.update,param);
	},
	viewBillSloganAppOrgWin:null,						//查看基部门短信模板
	viewBillSloganAppOrg:function(param){
		return this.getBillSloganAppOrgWin(this.viewBillSloganAppOrgWin,common.sMSTemplete.i18n('foss.common.sMSTemplete.TviewDeptTemp'),common.viewState.view,param);
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
	    	//查看 部门短信模板
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
				text:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),								//作废
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler :function(){
					common.sMSTemplete.deleteEntityByCode(common.delType, null, me, common.levelType.c);
				}
			}]
        }];
	},
	getTbar:function(config){
		var me = this;
		return [{
			text : common.sMSTemplete.i18n('foss.common.sMSTemplete.BaddDeptTemp'),							//添加网点
			name:'grid_addDeptBtn_name',
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			handler :function(){
				var param = {},formRecord = Ext.create('Foss.common.sMSTemplete.TemplateAppOrgEntityModel');
				param.formRecord = formRecord;
				formRecord.set('smsVirtualCode',me.up('window').formRecord.get('virtualCode'));
				me.addBillSloganAppOrg(param).show();
			} 
		}];
	},
	//表格数据源
	getGridStore:function(config){
		var me = this;
		return Ext.create('Foss.common.sMSTemplete.TemplateAppOrgEntityStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					Ext.apply(operation,{
						params : {
							//代理公司 虚拟code
							'objectVo.templateAppOrgEntity.smsVirtualCode':me.up('window').formRecord.get('virtualCode')
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
			hidden:(common.viewState.view === config.viewState),
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Boperator'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bupdate'),
                disabled:(common.viewState.view === config.viewState),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	var record = grid.getStore().getAt(rowIndex);				//选中的计费规则数据
                	param.formRecord = record;
    				me.updateBillSloganAppOrg(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),
                disabled:(common.viewState.view === config.viewState),
                handler: function(grid, rowIndex, colIndex) {
                	common.sMSTemplete.deleteEntityByCode(null,grid.getStore().getAt(rowIndex),grid,common.levelType.c);
                }
            }]
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.deptName'),						//适用部门
			flex:1,
			dataIndex : 'orgName'
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSTemplete.tempContent'),						//广告语内容
			flex:1,
			dataIndex : 'smsContent'
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_common-sMSTempleteIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.common.sMSTemplete.QueryConditionForm',{'record':Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.common.sMSTemplete.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_common-sMSTempleteIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_common-sMSTempleteIndex_content',
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
			text:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bcancel'),								//作废
			disabled:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteCancelButton'),
			hidden:!common.sMSTemplete.isPermission('sMSTempleteIndex/sMSTempleteCancelButton'),
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			handler :function(){
				common.sMSTemplete.deleteEntityByCode(common.delType,null,queryGrid,common.levelType.p);
			}
		}]
	}));
});
//------------------------------------WINDOW--------------------------------
//短信模板界面win
Ext.define('Foss.common.sMSTemplete.SMSTemplateEntityWin',{
	extend : 'Ext.window.Window',
	title : common.sMSTemplete.i18n('foss.common.sMSTemplete.TaddTemp'),								//新增短信模板   默认新增
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
			var winGrid = Ext.getCmp('Foss_common_SMSTemplateEntityWinGrid_ID');
			if(!Ext.isEmpty(winGrid)){
				winGrid.destroy();
			}
		}
	},
	viewState:common.viewState.add,				//查看状态,默认为新增
	editForm:null,											//短信模板表单Form
	editGrid:null,											//短信模板表格Grid
	formRecord:null,										//短信模板实体 Foss.common.BusinessPartnerModel
	gridDate:null,											//短信模板 网点信息数组  [Foss.common.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityWinForm',{'height':150,'viewState':config.viewState,'formRecord':config.formRecord});
		me.editGrid = Ext.create('Foss.common.sMSTemplete.SMSTemplateEntityWinGrid',{'height':250,'viewState':config.viewState});
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
			text : common.sMSTemplete.i18n('foss.common.share.Cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : common.sMSTemplete.i18n('foss.common.share.reset'),
			disabled:(common.viewState.view === config.viewState),
			handler :function(){
				// 重置
				common.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bsave'),
			disabled:(common.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.McheckIsRight'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		common.sMSTemplete.submitEntity(me,me.viewState,me.formRecord.data,common.levelType.p);
			}
		}];
	}
});
//部门短信模板界面win
Ext.define('Foss.common.sMSTemplete.TemplateAppOrgEntityWin',{
	extend : 'Ext.window.Window',
	title : common.sMSTemplete.i18n('foss.common.sMSTemplete.BaddDeptlanguage'),								//新增部门模板语   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :300,
	height :200,	
	viewState:common.viewState.add,				//查看状态,默认为新增
	editForm:null,											//短信模板表单Form
	editGrid:null,											//短信模板表格Grid
	formRecord:null,										//短信模板实体 Foss.common.BusinessPartnerModel
	gridDate:null,											//短信模板 网点信息数组  [Foss.common.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.items = [{	
			xtype : 'form',
			layout:'column',
			defaults:{
				allowBlank:false,
				readOnly:common.viewState.view === config.viewState
			},
		    items:[{
		    	xtype:'dynamicorgcombselector',
		    	name:'orgId',
		    	fieldLabel:common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.deptName')
		    },{
		    	xtype:'textareafield',
		    	name:'smsContent',
		    	fieldLabel:common.sMSTemplete.i18n('foss.common.sMSTemplete.tempContent'),
		    	height:60,
		    	maxLength:500
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
			text : common.sMSTemplete.i18n('foss.common.share.Cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : common.sMSTemplete.i18n('foss.common.share.reset'),
			disabled:(common.viewState.view === config.viewState),
			handler :function(){
				// 重置
				common.formReset([me.down('form').getForm()],[me.formRecord]);
			} 
		},{
			text : common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.Bsave'),
			disabled:(common.viewState.view === config.viewState),
			handler :function(){
		    	var editForm = me.down('form').getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		common.showInfoMsg(common.sMSTemplete.i18n('foss.common.util.TfossAlertU'),common.sMSTemplete.i18n('foss.common.sMSAdvertisingSlogan.McheckIsRight'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		common.sMSTemplete.submitEntity(me,me.viewState,me.formRecord.data,common.levelType.c);
			}
		}];
	}
});

