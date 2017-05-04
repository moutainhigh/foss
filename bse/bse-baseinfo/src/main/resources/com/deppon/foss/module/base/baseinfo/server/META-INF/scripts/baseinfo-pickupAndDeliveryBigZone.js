/**
 * 接送货大区model									Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel
 * 接送货大区store									Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityStore
 * 接送货大区form									Foss.baseinfo.pickupAndDeliveryBigZone.QueryConditionForm
 * 接送货大区grid									Foss.baseinfo.pickupAndDeliveryBigZone.QueryResultGrid
 * 接送货大区winForm									Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWinForm
 * 接送货大区winGrid									Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWinGrid
 * 接送货大区win										Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.pickupAndDeliveryBigZone.ployfeature = null;					//地图服务
baseinfo.pickupAndDeliveryBigZone.regionNature = {bq:'DE',sq:'PK'};//区域性质：对应后台常量日DictionaryValueConstants.REGION_NATURE_BQ
baseinfo.pickupAndDeliveryBigZone.initSmallZoneData = [];					//地图服务
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.pickupAndDeliveryBigZone.i18n('i18n.baseinfo-util.fossAlert'),
	    width:110+len*15,
	    msg:'<div id="message">'+message+'</div>',
	    buttons: Ext.Msg.OK,
	    icon: Ext.MessageBox.INFO,
	    callback:function(e){
	    	if(!Ext.isEmpty(fun)){
	    		if(e=='ok'){
		    		fun();
		    	}
	    	}
	    }
	});
	setTimeout(function(){
      Ext.Msg.hide();
  }, 3000);
};
//弹出界面上 数据渲染
baseinfo.pickupAndDeliveryBigZone.initParentWin = function(win,viewState,formRecord,gridData){
	//加载数据
	win.editForm.loadRecord(formRecord);
	// 公共组件 
	win.editForm.getForm().findField('management').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//市区
	win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//区县
//	win.editForm.down('commonmotorcadeselector').setCombValue(formRecord.get('transDeptName'),formRecord.get('transDepartmentCode'));//省份
	win.editForm.getForm().findField('transDepartmentCode').setCombValue(formRecord.get('transDeptName'),formRecord.get('transDepartmentCode'));//部门
	 
	//grid加载数据
	if(baseinfo.viewState.add != viewState){
		win.editForm.sourceGrid.store.load({params : {
			//管理部门
			'objectVo.smallZoneEntity.management':formRecord.get('management'),
			//区域类型
			'objectVo.smallZoneEntity.regionType':formRecord.get('type'),
			//接送货大区编码
			'objectVo.smallZoneEntity.bigzonecode':null
		}});
		win.editForm.targetGrid.store.load({params : {
			//管理部门
			'objectVo.smallZoneEntity.management':formRecord.get('management'),
			//区域类型
			'objectVo.smallZoneEntity.regionType':formRecord.get('type'),
			//接送货大区
			'objectVo.smallZoneEntity.bigzonecode':formRecord.get('virtualCode')
		}});
		win.editForm.targetGrid.store.on('load',function(s,rs){
			s.each(function(record){
				baseinfo.pickupAndDeliveryBigZone.initSmallZoneData.push(record);
			});
		});
	}else{
		win.editForm.sourceGrid.store.loadData([]);
		win.editForm.targetGrid.store.loadData([]);
	}
	//非新增 状态 所有按钮不可用
	var btnArr = win.query('button');
	for(var i=0;i<btnArr.length;i++){
		btnArr[i].setDisabled(baseinfo.viewState.view === viewState);
	}
	if(baseinfo.viewState.view === viewState){
		baseinfo.formFieldSetReadOnly(true,win.editForm);
	}
	return win;
};
//保存事件 
baseinfo.pickupAndDeliveryBigZone.submitBigZoneEntity = function(win,viewState,operatEntity){
	var grid = Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addPickupAndDeliveryBigZone.action')
		,m_success = baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {},targetStore = win.editForm.getTargetGrid().store,sourceStore = win.editForm.getSourceGrid().store
		addList = [],delList = [];
	objectVo.bigZoneEntity = operatEntity;
	//初始为空，最终不为空
	if(Ext.isEmpty(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData)&&targetStore.getCount()>0){
		for(var k =0;k<targetStore.getCount();k++){
			addList.push(targetStore.getAt(k).get('virtualCode'));
		}
	}
	//最终为空，初始不为空
	else if(!Ext.isEmpty(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData)&&targetStore.getCount()<=0){
		for(var i = 0; i < baseinfo.pickupAndDeliveryBigZone.initSmallZoneData.length; i++){
			delList.push(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData[i].get('virtualCode'));
		}
	}
	//最终不为空，初始不为空
	else if(!Ext.isEmpty(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData)&&targetStore.getCount()>0){
		for(var k =0;k<targetStore.getCount();k++){
			//初始不包含 则为 新增
			if(!Ext.Array.contains(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData,targetStore.getAt(k))){
				addList.push(targetStore.getAt(k).get('virtualCode'));
			}
		}
		for(var i = 0; i < baseinfo.pickupAndDeliveryBigZone.initSmallZoneData.length; i++){
			//最终不包含 则为 删除
			if(!Ext.Array.contains(targetStore.data.items,baseinfo.pickupAndDeliveryBigZone.initSmallZoneData[i])){
				delList.push(baseinfo.pickupAndDeliveryBigZone.initSmallZoneData[i].get('virtualCode'));
			}
		}
	}
//	for(var j = 0; !Ext.isEmpty(targetStore.getRemovedRecords()) && j < targetStore.getRemovedRecords().length; j++){
//		delList.push(targetStore.getRemovedRecords()[j].get('virtualCode'));
//	}
	objectVo.addSmallZoneList = addList;
	objectVo.delSmallZoneList = delList;
	if(Ext.isEmpty(operatEntity.porters)){
		operatEntity.porters = [];
	}
	if(baseinfo.viewState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updatePickupAndDeliveryBigZone.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.bigZoneEntity)){
			grid.store.loadPage(1);
			//TODO 保存按钮 变为可用
			baseinfo.showInfoMsg(m_success);
			win.hide();
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//自动生成大区编码 type:区域类型，management管理部门
baseinfo.pickupAndDeliveryBigZone.autoCreateBigZoneNum = function(type,management,form){
	var objectVo = {},bigZoneEntity = {};
	bigZoneEntity.type = type;
	bigZoneEntity.management = management;
	objectVo.bigZoneEntity = bigZoneEntity;
	if(!Ext.isEmpty(type)&&!Ext.isEmpty(management)){
		//自动生成 区域编码
		baseinfo.requestAjaxJson(baseinfo.realPath('autoCreateBigZoneNum.action'), {
			'objectVo' : objectVo
		}, function(result) {
			//自动生成编码
			form.getForm().findField('regionCode').setValue(result.objectVo.codeStr);
		}, function(result) {});
		//通过管理部门 和接送货类型 加载 所属小区 内容
		form.sourceGrid.store.load({params : {
			//管理部门
			'objectVo.smallZoneEntity.management':management,
			//区域类型
			'objectVo.smallZoneEntity.regionType':type,
			//接送货大区编码
			'objectVo.smallZoneEntity.bigzonecode':null
		}});
		form.targetGrid.store.loadData([]);
		//TODO 去掉 GIS地图，整个换张题图
	}
};
//作废事件
baseinfo.pickupAndDeliveryBigZone.deleteBigZoneEntityByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex_content').getQueryGrid(),
		url = baseinfo.realPath('deletePickupAndDeliveryBigZone.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = [];
			//批量作废
			url = baseinfo.realPath('deletePickupAndDeliveryBigZone.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get('virtualCode');
		}
		Ext.MessageBox.buttonText.yes = baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.showInfoMsg(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
baseinfo.pickupAndDeliveryBigZone.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('limitedwarrantygoodsIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.bigZoneEntity = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.bigZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//------------------------------------MODEL----------------------------------
//接送货大区Model
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel', {
extend: 'Ext.data.Model',
fields : [{name:'regionCode',type:'string'}, //大区编码
	  {name:'regionName',type:'string'},//大区名称
	  {name:'management',type:'string'},//管理部门
	  {name:'managementName',type:'string'},//管理部门 名称
	  {name:'type',type:'string'},//区域类型
	  {name:'transDepartmentCode',type:'string'},//所属车队
	  {name:'transDeptName',type:'string'},//所属车队 名称
	  {name:'active',type:'string'},//是否启用
	  {name:'notes',type:'string'},//备注
	  {name:'virtualCode',type:'string'}, //虚拟编码,
	  // 所在省编码
	  {name:'provCode',type:'string'},
	  // 所在省名称（扩展）
	  {name:'provName',type:'string'},
	  // 所在市编码
	  {name:'cityCode',type:'string'},
	  // 所在市名称（扩展）
	  {name:'cityName',type:'string'},
	  // 所在区县
	  {name:'countyCode',type:'string'},
	  // 所在区县名称（扩展）
	  {name:'countyName',type:'string'}
	  ]
});
//接送货小区Model
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.SmallZoneEntityModel', {
extend: 'Ext.data.Model',
fields : [{name:'regionCode',type:'string'},//小区编码
	  {name:'regionName',type:'string'},//小区名称
	  {name:'management',type:'string'}, //管理部门
	  {name:'managementName',type:'string'},//管理部门Name
	  {name:'active',type:'string'},//是否启用
	  {name:'notes',type:'string'},//备注
	  {name:'virtualCode',type:'string'},//虚拟编码
	  //区域类型：接货区：DictionaryValueConstants.REGION_TYPE_PK
	  //       送货区：DictionaryValueConstants.REGION_TYPE_DE
	  {name:'regionType',type:'string'},
	  //GIS系统小区范围ＩＤ
	  {name:'gisid',type:'string'},
	  //所属大区虚拟编码
	  {name:'bigzonecode',type:'string'},
	  //所属大区虚name
	  {name:'bigzoneName',type:'string'},
	  //所属大区名称(扩展)
	  {name:'bigzoneName',type:'string'},
	  // 所在省编码
	  {name:'provCode',type:'string'},
	  // 所在省名称（扩展）
	  {name:'provName',type:'string'},
	  // 所在市编码
	  {name:'cityCode',type:'string'},
	  // 所在市名称（扩展）
	  {name:'cityName',type:'string'},
	  // 所在区县
	  {name:'countyCode',type:'string'},
	  // 所在区县名称（扩展）
	  {name:'countyName',type:'string'}]
});
//------------------------------------STORE----------------------------------
//接送货大区STORE
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryPickupAndDeliveryBigZoneByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.bigZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//接送货大区 所辖未选的小区 
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.UnselectedSmallZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.pickupAndDeliveryBigZone.SmallZoneEntityModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('querySmallZonesByDeptCode.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList'
		}
	}
});
//接送货大区 所辖已选的小区 
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.SelectedSmallZoneStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.pickupAndDeliveryBigZone.SmallZoneEntityModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('querySmallZonesByDeptCode.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList'
		}
	}
});
//------------------------------------FORM----------------------------------
//接送货大区 查询条件
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :185,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.bigZoneCode'),							//接送货大区编码
			name:'regionCode'
		},{
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.bigZoneName'),							//接送货大区名称
			name:'regionName'
		},FossDataDictionary.getDataDictionaryCombo('BSE_REGION_TYPE',{
			colspan:2,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),								//区域类型
			name: 'type'
		}),
		/*{
			labelWidth : 130,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.manageDept'),								//管理部门
			name: 'management',
			dispatchTeam:'Y',
			type:'ORG'
		},*/
		{
			labelWidth : 130,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.manageDept'),	 				 
			xtype:'commonmotorcadeselector',
			active : 'Y',
			isFullFleetOrgFlag:'Y',
			name:'management'			
		},
		{
			labelWidth : 130,colspan:2,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.belongTruckTeam'),							//所属车队
			//xtype:'commonmotorcadeselector',
			//active : 'Y',
			name:'transDepartmentCode',
			xtype : 'commonDynamicTransTeamSelector',//'commonmotorcadeselector',
			allowBlank : true,
			listeners :{
			  expand:function(operation, eOpts){
						 var transDepartmentCode=me.getForm().findField('transDepartmentCode');
						 var orgCode= me.getForm().findField('management').getValue();
			             transDepartmentCode.getStore().addListener('beforeload', function(store, operation, eOpts) {
								var searchParams = operation.params;
								if (Ext.isEmpty(searchParams)) {
									searchParams = {};
									Ext.apply(operation, {
										params : searchParams
									});
								}
								if(!Ext.isEmpty(orgCode)){
									searchParams['objectVo.entity.orgCode'] = orgCode;
								}	
				          });
			             if (!Ext.isEmpty(orgCode)){
								 transDepartmentCode.getStore().load({
									params : {
												"objectVo.entity.orgCode" : orgCode									}
								});	
							}
						 } 
//						expand : function(field, eOpts) {
//							var orgCode = me.getForm().findField('management').getValue();
//							if (!Ext.isEmpty(orgCode)){
//								 field.getStore().load({
//									params : {
//												"driverVo.driverEntity.orgId" : orgCode									}
//								});	
//							}
//						}
		  }
				
		},{
			xtype : 'container',colspan:3,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneQueryButton'),
				hidden:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneQueryButton'),
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
				disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneQueryButton'),
				hidden:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneQueryButton'),
				text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				handler : function() {
					var grid  = Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex_content').getQueryGrid();//得到grid
					grid.store.loadPage(1);//用分页的moveFirst()方法
				}
			}]
		}];
	}
});
//接送货大区 界面form
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	autoScroll:true,
	layout:{
        type: 'table',
        columns: 6
    },
    formRecord:null,												//绑定的model Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityStore
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
	    	//labelSeparator:'',
			allowBlank:true,
//			width:300,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			colspan:2,
			labelWidth:120,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.bigZoneCode'),							//接送货大区编码
			name:'regionCode',
			readOnly:true,
			allowBlank:false
		},{
			colspan:2,
			labelWidth:120,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.bigZoneName'),							//接送货大区名称
			name:'regionName',
			allowBlank:false
		},FossDataDictionary.getDataDictionaryCombo('BSE_REGION_TYPE',{
			colspan:2,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),								//区域类型
			name: 'type',
	    	labelWidth:120,
			listeners:{
				select:function(field,rs){
					if(!Ext.isEmpty(rs)){
        				baseinfo.pickupAndDeliveryBigZone.autoCreateBigZoneNum(field.getValue(),me.getForm().findField('management').getValue(),me);
            		}
        		}
        	}
		}),{
			colspan:6,
//			labelWidth : 120,
			fieldLabel : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.provenceCityArea'),
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			allowBlank:true,
			nationIsBlank:true,
			provinceIsBlank:false,
			cityIsBlank:false,
			areaIsBlank:false,
			provinceLabel : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.province'),
			cityLabel : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.city'),
			areaLabel : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.area'),
			width:841,
			hideLabel:true,
			provinceWidth : 280,
			cityWidth : 280,
			areaWidth : 280,
			labelWid : 120,
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},
		/*{
			colspan:2,
			labelWidth : 120,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.manageDept'),		//管理部门						//所属空运代理
			name: 'management',
			dispatchTeam:'Y',
			type:'ORG',
			listeners:{
				select:function(field,rs){
					if(!Ext.isEmpty(rs)){
        				baseinfo.pickupAndDeliveryBigZone.autoCreateBigZoneNum(me.getForm().findField('type').getValue(),field.getValue(),me);
            		}
        		}
        	}
		},*/
		{
			colspan:2,
			labelWidth : 120,
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.manageDept'),	 				 
			xtype:'commonmotorcadeselector',
			active : 'Y',
			allowBlank:true,
			isFullFleetOrgFlag:'Y',
			name:'management',
			listeners:{
				select:function(field,rs){
					if(!Ext.isEmpty(rs)){
        				baseinfo.pickupAndDeliveryBigZone.autoCreateBigZoneNum(me.getForm().findField('type').getValue(),field.getValue(),me);
            		}
        		},
        		change : function(field,rs) {
        		   var transDepartmentCode=me.getForm().findField('transDepartmentCode');
        		   transDepartmentCode.setValue(null);
        		}		
        	}
		},{
					colspan : 4,
					labelWidth : 120,
					fieldLabel : baseinfo.pickupAndDeliveryBigZone
							.i18n('foss.baseinfo.pickupAndDeliveryBigZone.belongTruckTeam'), // 所属车队组
																								// //所属车队
					xtype : 'commonDynamicTransTeamSelector',//'commonmotorcadeselector',
					allowBlank : true,
					listeners : {
						expand:function(operation, eOpts){
						 var transDepartmentCode=me.getForm().findField('transDepartmentCode');
						 var orgCode= me.getForm().findField('management').getValue();
			             transDepartmentCode.getStore().addListener('beforeload', function(store, operation, eOpts) {
								var searchParams = operation.params;
								if (Ext.isEmpty(searchParams)) {
									searchParams = {};
									Ext.apply(operation, {
										params : searchParams
									});
								}
								if(!Ext.isEmpty(orgCode)){
									searchParams['objectVo.entity.orgCode'] = orgCode;
								}	
				          });
			             if (!Ext.isEmpty(orgCode)){
								 transDepartmentCode.getStore().load({
									params : {
												"objectVo.entity.orgCode" : orgCode									}
								});	
							}
						 } 
//						expand : function(field, eOpts) {
//							var orgCode = me.getForm().findField('management').getValue();
//							if (!Ext.isEmpty(orgCode)){
//								 field.getStore().load({
//									params : {
//												"driverVo.driverEntity.orgId" : orgCode									}
//								});	
//							}
//						}
				  },
					name : 'transDepartmentCode'
				},{
			colspan:3,
			xtype: 'container',
			layout : 'hbox',
			height:300,
			items:[me.getSourceGrid(config),
			       Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.moveBtnContainer',{
					sourceGrid:me.getSourceGrid(config),
					targetGrid:me.getTargetGrid(config),
					viewState:config.viewState,
					flex:0.3
			}),me.getTargetGrid(config)]
		},{
			colspan:3,
			xtype: 'container',
			height:300,
			width:426,
			listeners: {
				afterrender: function(field) {
					var aa = new DPMap.MyMap('VIEW', field.getId(),{center : "上海市", zoom : "STREET" },function(map) {
						baseinfo.pickupAndDeliveryBigZone.ployfeature = DMap.PolygonFeature(map,{isAddable:false,callBackFun:function(data) {}});
					})
				}
			}
		},{
			colspan:6,
			xtype: 'textareafield',
			fieldLabel:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.remark'),
			width: 840,
			height:40
		}];
	},
	sourceGrid:null,										//未选小区grid
	getSourceGrid:function(config){
		var me = this;
		if(me.sourceGrid === null){
			me.sourceGrid = Ext.create('Ext.grid.Panel',{
				frame:true,
				height:280,
				flex:1.2,
//				title:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.souceGrid'),
				store:me.getUnselectedSmallZoneStore(config),
				columns:[{flex:1,text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.smallZone'),dataIndex : 'regionName'}]
			});
		}
		return me.sourceGrid;
	},
	targetGrid:null,										//已选小区grid 
	getTargetGrid:function(config){
		var me = this;
		if(me.targetGrid === null){
			me.targetGrid = Ext.create('Ext.grid.Panel',{
				frame:true,
				height:280,
				flex:1.2,
//				title:baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.targetGrid'),
				store:me.getSelectedSmallZoneStore(config),
				listeners:{
					itemclick : function(view, record) {
						//获取选中小区地图范围ＩＤ
						var gisid = record.get('gisid');
						var gisIds=[];
						gisIds.push(gisid);
						if(!Ext.isEmpty(gisIds)){
							//先清除范围
							//根据id清楚地图上的范围
							baseinfo.pickupAndDeliveryBigZone.ployfeature.clearPolygonOnMap(gisid);
							// 增加地图上区域
							baseinfo.pickupAndDeliveryBigZone.ployfeature.showModifiyAblePolygons(gisIds);
						}
					}
				},
				columns:[{flex:1,text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.smallZone'),dataIndex : 'regionName'}]
			});
		}
		return me.targetGrid;
	},
	unselectedSmallZoneStore:null,										//未选小区store
	getUnselectedSmallZoneStore:function(config){
		var me = this,form = me.getForm();
		if(me.unselectedSmallZoneStore === null){
			me.unselectedSmallZoneStore = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.UnselectedSmallZoneStore',{
				autoLoad : false
			});
		}
		return me.unselectedSmallZoneStore;
	},
	selectedSmallZoneStore:null,										//已选小区store 
	getSelectedSmallZoneStore:function(config){
		var me = this,form = me.getForm();
		if(me.selectedSmallZoneStore === null){
			me.selectedSmallZoneStore = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.SelectedSmallZoneStore',{
				autoLoad : false
			});
		}
		return me.selectedSmallZoneStore;
	}
});
//移动按钮 容器
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.moveBtnContainer', {
	extend : 'Ext.container.Container',
	buttonAlign : 'center',
	layout : 'column',
	sourceGrid:null,
	targetGrid:null,
	viewState:null,
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
		return [{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:15px;border:none',
			hide:true
		},{
			columnWidth : 1,
		    xtype : 'button',
		    disabled:baseinfo.viewState.view === config.viewState,
			text:'-->',
			handler : function(){
				//把选中的记录添加到目标列表中
				var models = config.sourceGrid.getSelectionModel().getSelection(),gisIds=[];
				if(!Ext.isEmpty(models) && models.length>0){
					for(var j = 0;j<models.length;j++){
						config.sourceGrid.store.remove(models[j]);
					}
					config.targetGrid.store.add(models);
					for(var i = 0;i<models.length;i++){
						gisIds.push(models[i].get('gisid'));
					}
					if(!Ext.isEmpty(gisIds)){
						// 增加地图上区域
						baseinfo.pickupAndDeliveryBigZone.ployfeature.showModifiyAblePolygons(gisIds);
					}
				}
			}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
		    disabled:baseinfo.viewState.view === config.viewState,
			text:'->>',
			handler : function(){
				//把选中的记录添加到目标列表中
				var models = config.sourceGrid.store.data.items,gisIds =[];
				if(!Ext.isEmpty(models) && models.length>0){
					var tempArray = Ext.Array.clone(models);
					for(var j = 0;j<tempArray.length;j++){
						config.sourceGrid.store.remove(tempArray[j]);
					}
					config.targetGrid.store.add(tempArray);
					var targetModel = config.targetGrid.store.data.items;
					for(var i = 0;i<targetModel.length;i++){
						if(!Ext.isEmpty(targetModel[i].get('gisid'))){
							gisIds.push(targetModel[i].get('gisid'));
						}
					}
					if(!Ext.isEmpty(gisIds)){
						// 增加地图上区域
						baseinfo.pickupAndDeliveryBigZone.ployfeature.showModifiyAblePolygons(gisIds);
					}
				}
			}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
		    disabled:baseinfo.viewState.view === config.viewState,
			text:'<--',
			handler : function(){
				//把选中的记录添加到源列表中
				var models = config.targetGrid.getSelectionModel().getSelection(),modelArray = [];
				if(!Ext.isEmpty(models) && models.length>0){
					for(var j = 0;j<models.length;j++){
						config.targetGrid.store.remove(models[j]);
						modelArray.push(models[j]);
					}
					config.sourceGrid.store.add(models);
					for(var i = 0;i<modelArray.length;i++){
						// 移除地图上区域
						baseinfo.pickupAndDeliveryBigZone.ployfeature.clearPolygonOnMap(modelArray[i].get('gisid'));
					}
				}}
		},{
			columnWidth : 1,
			height : 0,
			xtype : 'container',
			style : 'padding-top:10px;border:none',
			hide:true
		},{
			columnWidth : 1,
			xtype : 'button',
		    disabled:baseinfo.viewState.view === config.viewState,
			text:'<<-',
			handler : function(){
				//把选中的记录添加到源列表中
				var models = config.targetGrid.store.data.items;
				if(!Ext.isEmpty(models) && models.length>0){
					var tempArray = Ext.Array.clone(models);
					var modelArray = [];
					for(var j = 0;j<tempArray.length;j++){
						config.targetGrid.store.remove(tempArray[j]);
						modelArray.push(tempArray[j]);
					}
					config.sourceGrid.store.add(tempArray);
					for(var i = 0;i<modelArray.length;i++){
						// 移除地图上区域
						baseinfo.pickupAndDeliveryBigZone.ployfeature.clearPolygonOnMap(modelArray[i].get('gisid'));
					}
				}}
		}];
	}
});
//------------------------------------GRID----------------------------------
//接送货大区 查询结果grid
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
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
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.add'),								//新增
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneAddButton'),
			hidden:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneAddButton'),
			handler :function(){
				me.addBigZoneEntity({}).show();
			} 
		},'-', {
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.void'),								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneDisableButton'),
			hidden:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneDisableButton'),
			handler :function(){
				baseinfo.pickupAndDeliveryBigZone.deleteBigZoneEntityByCode(baseinfo.delAgencyType);
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
	//得到接送货大区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel'):param.formRecord
				,gridData = [];
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		//加载数据
//		win.editForm.loadRecord(formRecord);
		return baseinfo.pickupAndDeliveryBigZone.initParentWin(win,viewState,formRecord,gridData);
	},
	addBigZoneEntityWin:null,						//新增基接送货大区
	addBigZoneEntity:function(param){
		return this.getAgencyDeptWin(this.addBigZoneEntityWin,baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.addBigZone'),baseinfo.viewState.add,param);
	},
	updateBigZoneEntityWin:null,						//修改基接送货大区
	updateBigZoneEntity:function(param){
		return this.getAgencyDeptWin(this.updateBigZoneEntityWin,baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.alterBigZone'),baseinfo.viewState.update,param);
	},
	viewBigZoneEntityWin:null,						//查看基接送货大区
	viewBigZoneEntity:function(param){
		return this.getAgencyDeptWin(this.viewBigZoneEntityWin,baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.viewBigZone'),baseinfo.viewState.view,param);
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
	    	//查看 接送货大区
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				var win = me.viewBigZoneEntity(param);
				win.show();
				var store = win.down('form').getSelectedSmallZoneStore(),gisIds=[];
				for(var i = 0;i<store.getCount();i++){
					gisIds.push(store.getAt(i).get('gisid'));
				}
				if(!Ext.isEmpty(gisIds)){
					//TODO 批量增加地图上区域 
					baseinfo.pickupAndDeliveryBigZone.ployfeature.showModifiyAblePolygons(gisIds);
				}
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//接送货大区编码
								'objectVo.bigZoneEntity.regionCode':entity.regionCode,
								//接送货大区名称
								'objectVo.bigZoneEntity.regionName':entity.regionName,
								//区域类型
								'objectVo.bigZoneEntity.type':entity.type,
								//管理部门
								'objectVo.bigZoneEntity.management':entity.management,
								//所属车队
								'objectVo.bigZoneEntity.transDepartmentCode':entity.transDepartmentCode
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
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneEditButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				var win = me.updateBigZoneEntity(param);
					win.show();
					setTimeout(function(){
			            var store =  win.editForm.targetGrid.store,gisIds=[];
						for(var i = 0;i<store.getCount();i++){
							gisIds.push(store.getAt(i).get('gisid'));
						}
						if(!Ext.isEmpty(gisIds)){
							// 批量增加地图上区域
							baseinfo.pickupAndDeliveryBigZone.ployfeature.showModifiyAblePolygons(gisIds);
						}
			         },2000);
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneDisableButton'),
                hidden:!baseinfo.pickupAndDeliveryBigZone.isPermission('queryPickupAndDeliveryBigZoneByEntity/pickupAndDeliveryBigZoneDisableButton'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.pickupAndDeliveryBigZone.deleteBigZoneEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.bigZoneNum'),									//接送货大区编码
			dataIndex : 'regionCode',flex:1
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.pickupAndDeliveryBigZoneName'),									//接送货大区名称
			dataIndex : 'regionName',flex:1
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.manageDept'),									//上机部门名称
			dataIndex : 'managementName',flex:1
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),											//队员
			dataIndex : 'type',flex:1,
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'BSE_REGION_TYPE');
			}
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.ownedVehicleFleet'),											//队员
			dataIndex : 'transDeptName',flex:1
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex_content')){
		return;
	}
	var queryForm  = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.QueryResultGrid');//查询结果显示列表
	Ext.getCmp('T_baseinfo-pickupAndDeliveryBigZoneIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-pickupAndDeliveryBigZoneIndex_content',
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
		items : [ queryForm, queryGrid] 
	}));
});
//------------------------------------WINDOW--------------------------------
//接送货大区界面win
Ext.define('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.addBigZone'),								//新增接送货大区   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :890,
	height :610,	
	layout : 'fit',
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
//			Ext.getCmp('deppon_queryDestination_mapPane').destroy();
			baseinfo.pickupAndDeliveryBigZone.initSmallZoneData=[];
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//接送货大区表单Form
	editGrid:null,											//接送货大区表格Grid
	formRecord:null,										//接送货大区实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//接送货大区 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.pickupAndDeliveryBigZone.BigZoneEntityWinForm',{'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.editForm];
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
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.reset'),
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.save'),
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.pickupAndDeliveryBigZone.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.pickupAndDeliveryBigZone.submitBigZoneEntity(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});

