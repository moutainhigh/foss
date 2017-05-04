/**
 * 接送货小区model									Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel
 * 接送货小区store									Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityStore
 * 接送货小区form									Foss.baseinfo.pickupAndDeliverySmallZone.QueryConditionForm
 * 接送货小区grid									Foss.baseinfo.pickupAndDeliverySmallZone.QueryResultGrid
 * 接送货小区winForm									Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWinForm
 * 接送货小区winGrid									Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWinGrid
 * 接送货小区win										Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWin
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.pickupAndDeliverySmallZone.ployfeature = null;					//地图服务
baseinfo.pickupAndDeliverySmallZone.ployfeature1 = null;
baseinfo.pickupAndDeliverySmallZone.deliverySalCoordinator = null;    //派送部坐标
baseinfo.pickupAndDeliverySmallZone.dpcon =null;			//导航距离
baseinfo.pickupAndDeliverySmallZone.region_type = {jhq:'PK',shq:'DE'};//接送货类型 BSE_REGION_TYPE,接货区 送货区
baseinfo.pickupAndDeliverySmallZone.region_type_map = {jhq:'PICKUP_REGIONS',shq:'DELIVERY_REGIONS'}//对接货小区(PICKUP_REGIONS)，送货小区(DELIVERY_REGIONS)，
isFullScreen = true;
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.pickupAndDeliverySmallZone.i18n('i18n.baseinfo-util.fossAlert'),
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
  }, 2000);
};
//弹出界面上 数据渲染
baseinfo.pickupAndDeliverySmallZone.initParentWin = function(win,viewState,formRecord,gridData){
	if(baseinfo.viewState.add === viewState){
		//新增时 必填项不用
		win.editForm.getForm().reset();
	}else{
		// 公共组件 
		win.editForm.down('commonmotorcadeselector').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
		win.editForm.down('commonbigzoneselector').setCombValue(formRecord.get('bigzoneName'),formRecord.get('bigzonecode'));//县
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//市
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//县
		//加载数据
		win.editForm.loadRecord(formRecord);
	}
	if(baseinfo.viewState.view === viewState){
		baseinfo.formFieldSetReadOnly(true,win.editForm);
	}
	return win;
};
//选择理货员 界面增加 panel事件 
baseinfo.pickupAndDeliverySmallZone.addPorter = function(parentContainer,childEntity,store,viewState){
	var childContainer = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.PortersContainer',{
		record:childEntity,store:store,viewState:viewState
	});
	//
	//TODO 新增一条数据展示
	parentContainer.add(childContainer);
	//TODO store add该数据
	
};


//根据名称验证小区名称是否重复
baseinfo.pickupAndDeliverySmallZone.queryPickSmallRegionByRegionName = function(obj){
	
	if(""==obj.getValue()){
		return;
	}
	
	var objectVo = {},smallZoneEntity = {};
	smallZoneEntity.regionName = obj.getValue();
	objectVo.smallZoneEntity = smallZoneEntity;
	baseinfo.requestAjaxJson(baseinfo.realPath('queryPickSmallRegionByRegionName.action'),{'objectVo':objectVo},function(result){
		 
	},function(result){
		obj.setValue(null);
		baseinfo.showInfoMsg(result.message);
	});
};



//保存事件 
baseinfo.pickupAndDeliverySmallZone.submitPickupAndDeliverySmallZoneEntity = function(win,viewState,operatEntity,obj){
	var grid = Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex_content').getQueryGrid()
		,url = baseinfo.realPath('addPickupAndDeliverySmallZone.action')
		,m_success = baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {};
	objectVo.smallZoneEntity = operatEntity;
	if(Ext.isEmpty(operatEntity.porters)){
		operatEntity.porters = [];
	}
	if(baseinfo.viewState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updatePickupAndDeliverySmallZone.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.smallZoneEntity)){
			grid.store.loadPage(1);
			baseinfo.showInfoMsg(m_success);
			if(obj==null){
			  win.hide();
			}
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//作废事件
baseinfo.pickupAndDeliverySmallZone.deletePickupAndDeliverySmallZoneEntityByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex_content').getQueryGrid(),
		url = baseinfo.realPath('deletePickupAndDeliverySmallZone.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		//定义地图范围ＩＤ数组
		var gisidArray = [];
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = '';
			//批量作废
			url = baseinfo.realPath('deletePickupAndDeliverySmallZone.action');
			for (var j = 0; j < selection.length; j++) {
				codeStr = codeStr + ',' + selection[j].get('virtualCode');
				//把选中的小区范围gisid存放到数组中
				gisidArray.push(selection[j].get('gisid'));
			}
			objectVo.codeStr = codeStr;
		}else{
			objectVo.codeStr = operatRecord.get('virtualCode');
			gisidArray.push(operatRecord.get('gisid'));
		}
		Ext.MessageBox.buttonText.yes = baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
				/*	var dMap = new DpMap('gisMap',{center : '上海市', zoom : 13 },function(map) {
						var polyFeature = new DpMap.service.DpMap_polygon(map,'gisMap',{isAddable:true,callBackFun:function(data){}});
						//删除小区成功后删除小区在GIS上面的范围
						for(var i= 0;i < gisidArray.length; i++){
							//根据小区gisid删除小区地图范围
							polyFeature.deletePolygonByCode(gisidArray[i]);
						}
					});*/
					for(var i= 0;i < gisidArray.length; i++){
						//根据小区gisid删除小区地图范围
						DpMap.base.deletePolygonByCode(gisidArray[i]);
					}
					baseinfo.showInfoMsg(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.deleteSuccess'));
					
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
//通过查询条件导出数据
baseinfo.pickupAndDeliverySmallZone.exportSmallZone = function(queryForm){
	var queryForm = queryForm.getForm();//得到查询的FORM表单
	queryForm.updateRecord(queryForm.record);
    if (queryForm != null) {
		Ext.MessageBox.buttonText.yes = "确定";  
		Ext.MessageBox.buttonText.no = "取消"; 
		if(!Ext.fly('downloadSmallZone')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadSmallZone';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm( baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.tipInfo'), '确定要导出查询结果吗?', function(btn,text){
			if(btn == 'yes'){
				var params ={
					//接送货小区编码
					'objectVo.smallZoneEntity.regionCode':queryForm.record.data.regionCode,
					//接送货小区名称
					'objectVo.smallZoneEntity.regionName':queryForm.record.data.regionName,
					//上级部门名称
					'objectVo.smallZoneEntity.management':queryForm.record.data.management,
					//区域类型
					'objectVo.smallZoneEntity.regionType':queryForm.regionType
				}
		
				Ext.Ajax.request({
					url:baseinfo.realPath('exportSmallZoneList.action'),
					form: Ext.fly('downloadSmallZone'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.exportFailed'),result.message);
					}
				});
			}
		});
	}
};
baseinfo.pickupAndDeliverySmallZone.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('pickupAndDeliverySmallZoneIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.pickupAndDeliverySmallZoneEntityList = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.pickupAndDeliverySmallZoneEntity)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};

//根据管理部门（顶级车队）获取所属外场，获取所属外场对应派送部坐标 
baseinfo.pickupAndDeliverySmallZone.getDeliverySal = function(managementCode){
	Ext.Ajax.request({
		url:baseinfo.realPath('findDeliverySaleDepartment.action'),
		params:{
			"objectVo.smallZoneEntity.management":managementCode
		},
		success:function(response){
			var json=Ext.decode(response.responseText);
//			baseinfo.showInfoMes(json.message);//查询成功返回提示
			baseinfo.pickupAndDeliverySmallZone.deliverySalCoordinator=json.objectVo.deliverySaleCoordinate;
		},
		exception:function(response){
			if(!Ext.isEmpty(response)){
				baseinfo.showErrorMes(Ext.decode(response.responseText).message);
			}else{
				bseinfo.showErrorMes('查询管理部门对应派送部请求超时');
			}
		}
	})
};
//------------------------------------MODEL----------------------------------
//接送货小区Model
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel', {
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
	  //GIS系统小区范 面积
	  {name:'gisArea',type:'string'}, //GIS 面积
	  //所属大区虚拟编码
	  {name:'bigzonecode',type:'string'},
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
	  {name:'countyName',type:'string'},
	  //GIS导航距离
	  {name:'navigationDistance',type:'string'},
	  //GIS中心点坐标(纬度)
	  {name:'centerPointLat',type:'string'},
	  //GIS中心点坐标(经度度)
	  {name:'centerPointLng',type:'string'}]
});
//------------------------------------STORE----------------------------------
//接送货小区STORE
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryPickupAndDeliverySmallZoneByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//理货员STORE
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryPickupAndDeliverySmallZoneExactByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.pickupAndDeliverySmallZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//------------------------------------FORM----------------------------------
//接送货小区 查询条件
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	html:"<div id='gisMap' style='position:absolute;top:10%; left:25%; z-index:1; visibility: hidden; '></div>",
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :190,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 3
    },
    record:null,												//绑定的model Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneCode'),							//接送货小区编码
			name:'regionCode'
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneName'),							//接送货小区名称
			name:'regionName'
		},
		/*{
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.manageDept'),									//管理部门
			name:'management',
			dispatchTeam:'Y',
			type:'ORG'
		},*/
		{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.manageDept'),	 				 
			xtype:'commonmotorcadeselector',
			active : 'Y',
			isFullFleetOrgFlag:'Y',
			name:'management'
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),	
			xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			name: 'regionType',
			columnWidth: 0.275,
			value:'',
			colspan:3,
			store:FossDataDictionary.getDataDictionaryStore('BSE_REGION_TYPE', null, {
				'valueCode': '',
	            'valueName': baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.announcement.all')
			})
			
		},{
			xtype : 'container',colspan:3,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneQueryButton'),
				hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneQueryButton'),
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
				disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneQueryButton'),
				hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneQueryButton'),
				text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				handler : function() {
					var grid  = Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex_content').getQueryGrid();//得到grid
					grid.store.loadPage(1);//用分页的moveFirst()方法
				}
			}]
		}];
	}
});
//接送货小区 界面form
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityStore
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
			labelWidth : 140,
			readOnly:(baseinfo.viewState.view === config.viewState)?true:false
	    };
	},
	getItems:function(config){
		var me = this;
		return [{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneCode'),							//接送货小区编码
			name:'regionCode',
			labelWidth:140,
			readOnly:true,
			allowBlank:true
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneName'),							//接送货小区名称
			name:'regionName',
			labelWidth:140,
			allowBlank:(baseinfo.viewState.view === config.viewState),
			listeners:{
				blur:function(The,eOpts ){
					baseinfo.pickupAndDeliverySmallZone.queryPickSmallRegionByRegionName(The);
        		}
			}
		},FossDataDictionary.getDataDictionaryCombo('BSE_REGION_TYPE',{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),								//区域类型
			name: 'regionType',
	    	labelWidth:140,
			allowBlank:(baseinfo.viewState.view === config.viewState)
		}),
		/*{
			labelWidth : 140,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.manageDept'),								//管理部门
			name: 'management',
			dispatchTeam:'Y',
			type:'ORG',
			allowBlank:(baseinfo.viewState.view === config.viewState)
		},*/
		{   labelWidth : 140,
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.manageDept'),	 				 
			xtype:'commonmotorcadeselector',
			active : 'Y',
			allowBlank:true,
			isFullFleetOrgFlag:'Y',
			allowBlank:false,
			name:'management'
		},
		{
			colspan:2,
			labelWidth : 140,
			width: 600,
			fieldLabel : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.provenceCityArea'),
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name
			areaName:'countyCode',// 县name
			allowBlank:true,
			nationIsBlank:true,
			provinceIsBlank:(baseinfo.viewState.view === config.viewState),
			cityIsBlank:(baseinfo.viewState.view === config.viewState),
			areaIsBlank:(baseinfo.viewState.view === config.viewState),
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
			colspan:2,
			xtype: 'textareafield',
			name:'notes',
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.remark'),
			width: 600,
			height:40
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.mapAddress'),
			name:'gisid',
			hidden:true,
			allowBlank:(baseinfo.viewState.view === config.viewState)
		},{
			fieldLabel:'地图地址',
			name:'gisNewId',
			hidden:true,
			allowBlank:true
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneArea'),
			name:'gisArea',
			hidden:true,
			allowBlank:(baseinfo.viewState.view === config.viewState)
		},{
			name:'virtualCode',
			hidden:true,
			allowBlank:true
		},{
			xtype:'commonbigzoneselector',
	    	valueField:'virtualCode',
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.belongBigZone'),							//所属大区
			name:'bigzonecode',hidden:true
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.navigationDistance'),
			name:'navigationDistance',
//			allowBlank:(baseinfo.viewState.view === config.viewState),
			hidden:true
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('GIS中心点坐标(纬度)'),
			name:'centerPointLat',
			hidden:true
		},{
			fieldLabel:baseinfo.pickupAndDeliverySmallZone.i18n('GIS中心点坐标(经度)'),
			name:'centerPointLng',
			hidden:true
		}];
	}
});
//理货员 查询条件
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.PortersContainer', {
	extend : 'Ext.form.Panel',
    record:null,												//绑定的model Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel
    store:null,													//绑定的Store Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityStore
	layout:'column',
	viewState:null,												//状态
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
	},
	getItems:function(config){
		var me = this;
		return [{xtype:'label',text:config.record.get('valueName')}
		,{xtype:'button',height:10,tooltip: baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.loadandunloadsquad.delete'),
            disabled:(baseinfo.viewState.view === config.viewState)?true:false,
		    handler: function(){
		    	me.hide();
		    	//TODO config.store remove该数据
		    }}];
	}
});
//------------------------------------GRID----------------------------------
//接送货小区 查询结果grid
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	queryForm:null,
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
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.add'),								//新增
			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
			disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneAddButton'),
			hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneAddButton'),
			handler :function(){
				me.addPickupAndDeliverySmallZoneEntity({}).show();
			} 
		},'-', {
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.void'),								//作废
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneDisableButton'),
			hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneDisableButton'),
			handler :function(){
				baseinfo.pickupAndDeliverySmallZone.deletePickupAndDeliverySmallZoneEntityByCode(baseinfo.delAgencyType);
			} 
		},'-', {
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.export'),								//导出
			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
			disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneExportButton'),
			hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneExportButton'),
			handler :function(){
				baseinfo.pickupAndDeliverySmallZone.exportSmallZone(config.queryForm);
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
	//得到接送货小区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord
			});
		}
		return baseinfo.pickupAndDeliverySmallZone.initParentWin(win,viewState,formRecord,null);
	},
	addPickupAndDeliverySmallZoneEntityWin:null,						//新增基接送货小区
	addPickupAndDeliverySmallZoneEntity:function(param){
		return this.getAgencyDeptWin(this.addPickupAndDeliverySmallZoneEntityWin,baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.addSmallZone'),baseinfo.viewState.add,param);
	},
	updatePickupAndDeliverySmallZoneEntityWin:null,						//修改基接送货小区
	updatePickupAndDeliverySmallZoneEntity:function(param){
		return this.getAgencyDeptWin(this.updatePickupAndDeliverySmallZoneEntityWin,baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.alterSmallZone'),baseinfo.viewState.update,param);
	},
	viewPickupAndDeliverySmallZoneEntityWin:null,						//查看基接送货小区
	viewPickupAndDeliverySmallZoneEntity:function(param){
		return this.getAgencyDeptWin(this.viewPickupAndDeliverySmallZoneEntityWin,baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.viewSmallZone'),baseinfo.viewState.view,param);
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
	    	//查看 接送货小区
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				var win = me.viewPickupAndDeliverySmallZoneEntity(param);
				win.show();
    			if(!Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature) && !Ext.isEmpty(win.editForm.formRecord.get('gisid'))){
     				baseinfo.pickupAndDeliverySmallZone.ployfeature.showModifiyAblePolygons([win.editForm.formRecord.get('gisid')]);
    			}
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//接送货小区编码
								'objectVo.smallZoneEntity.regionCode':entity.regionCode,
								//接送货小区名称
								'objectVo.smallZoneEntity.regionName':entity.regionName,
								//上级部门名称
								'objectVo.smallZoneEntity.management':entity.management,
								//区域类型
								'objectVo.smallZoneEntity.regionType':entity.regionType
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
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneEditButton'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				var win = me.updatePickupAndDeliverySmallZoneEntity(param);
    				win.show();
    				if(!Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature) && !Ext.isEmpty(win.editForm.formRecord.get('gisid'))){
     					baseinfo.pickupAndDeliverySmallZone.ployfeature.showModifiyAblePolygons([win.editForm.formRecord.get('gisid')]);
    				}
    			}
            },{
            	iconCls:'deppon_icons_cancel',
            	disabled:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneDisableButton'),
            	hidden:!baseinfo.pickupAndDeliverySmallZone.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneDisableButton'),
                tooltip: baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.pickupAndDeliverySmallZone.deletePickupAndDeliverySmallZoneEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneCode'),									//接送货小区编码
			dataIndex : 'regionCode',
			width:150
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneName'),									//接送货小区名称
			dataIndex : 'regionName',
			width:150
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.manageDept'),									//管理部门
			dataIndex : 'managementName'
		},{
			//TODO 所属区域 名称
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.belongBigZone'),											//所属大区
			width:125,
			dataIndex : 'bigzoneName'
		},{
			text:baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),	
			dataIndex:'regionType',
			width:125,
			renderer:function(val){
				if(null ==val||''==val){
					return '';
				}else if (val =='DE'){
					return '送货区';
				}else if(val =='PK'){
					return '接货区';
				}else{
					return val;
				}
			}
		},{
			//TODO 小区面积
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.smallZoneArea'),											//所属大区
			flex:1,
			dataIndex : 'gisArea',
			renderer:function(value){
					if(null === value || ''===value){
						return ' ';
					}else{
						return (new Number(value)).toFixed(3);
					}
			}	
		},{
			//导航距离
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.navigationDistance'),											//所属大区
			flex:1,
			dataIndex : 'navigationDistance',
			renderer:function(value){
					if(null === value || ''===value){
						return ' ';
					}else{
						return (new Number(value)).toFixed(3);
					}
			}	
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex_content')){
		return;
	}
	
	var queryForm  = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.SmallZoneEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.QueryResultGrid',{'queryForm':queryForm});//查询结果显示列表
		Ext.getCmp('T_baseinfo-pickupAndDeliverySmallZoneIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-pickupAndDeliverySmallZoneIndex_content',
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
//接送货小区界面win
Ext.define('Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.pickupAndDeliverySmallZone.addSmallZone'),								//新增接送货小区   默认新增
	closable : true,
//	modal : true,
	resizable:false,
	closeAction : 'destroy',
	width :640,
	height :270,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
		}
	},
	viewState:baseinfo.viewState.add,				//查看状态,默认为新增
	editForm:null,											//接送货小区表单Form
	editGrid:null,											//接送货小区表格Grid
	formRecord:null,										//接送货小区实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//接送货小区 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.pickupAndDeliverySmallZone.PickupAndDeliverySmallZoneEntityWinForm',{'height':180,'viewState':config.viewState,'formRecord':config.formRecord});
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
			text : '地图',
			handler :function(){
				//对接货小区(PICKUP_REGIONS)，送货小区(DELIVERY_REGIONS)，
				var form = me.down('form').getForm(),regionType = form.findField('regionType').getValue()
					,provName = form.findField('provCode').getRawValue(),cityName = form.findField('cityCode').getRawValue(),countyName = form.findField('countyCode').getRawValue();
//				var regionName=	form.findField('regionType').getValue();
				var regionName=	form.findField('regionName').getValue();
				var management=form.findField('management').getValue();
				if(Ext.isEmpty(regionName)){
					Ext.Msg.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'绘画地图时小区名称不能为空！');
				    return;
				}
				if(Ext.isEmpty(management)){
					Ext.Msg.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'管理部门不能为空！');
					return;
				}
				
				var mapLocation;
				
				if(!Ext.isEmpty(cityName) && Ext.isEmpty(countyName)){
					mapLocation = cityName;	
				}else if(!Ext.isEmpty(cityName) && !Ext.isEmpty(countyName)){
					mapLocation = cityName + countyName;
					}
				else{
					mapLocation = '上海市';
				}
				if(Ext.isEmpty(provName)||Ext.isEmpty(cityName)||Ext.isEmpty(countyName)){
					Ext.Msg.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'省市区不能为空！！');
					return;
				}
				
				if(Ext.isEmpty(regionType)){
					Ext.Msg.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'绘画地图时区域类型不能为空！');
					return;
				}
				if(!DpMap){
					Ext.Msg.alert(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'地图服务已停止，请稍后再试或联系IT服务中心！');
					return;
				}
				if(baseinfo.pickupAndDeliverySmallZone.region_type.jhq === regionType){regionType = baseinfo.pickupAndDeliverySmallZone.region_type_map.jhq}
				if(baseinfo.pickupAndDeliverySmallZone.region_type.shq === regionType){regionType = baseinfo.pickupAndDeliverySmallZone.region_type_map.shq}
				if(Ext.isEmpty(cityName)){
					// 城市为空则 默认显示为上海
					Ext.Msg.confirm(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'小区城市为空，地图打开后默认为上海市！是否打开地图？',function(btn,text) {
						if (btn == 'yes') {
							Ext.create('Ext.window.Window',{
								layout:'fit',
//								resizable : false,
								/*tbar:[{
									   text:'显示全屏',
									   handler:function(){
									    var _w=this.up().up();
									   if(isFullScreen){
									      isFullScreen = false;
										  this.setText('显示全屏');
										  _w.restore();
										 }else{
										  isFullScreen = true;	  
										  this.setText('退出全屏');
										  _w.maximize();
										 }
									   }
									}],*/
								items:[{
									xtype: 'container',
									height:720,
									width:1150,
									listeners: {
										afterrender: function(field) {
											/*var aa = new DPMap.MyMap('DRAW', field.getId(),{center : '上海市', zoom : "STREET" },function(map) {
												baseinfo.pickupAndDeliverySmallZone.ployfeature = DMap.PolygonFeature(map,{isAddable:(baseinfo.viewState.view != config.viewState),callBackFun:function(data) {
													if('ADD' === data.type){
														form.findField('gisid').setValue(data.code);
														form.findField('gisArea').setValue(data.area);
													}else if('DELETE' === data.type){
														form.findField('gisNewId').setValue(null);
														form.findField('gisArea').setValue(null);
													}else if('UPDATE' ===data.type){
														form.findField('gisArea').setValue(data.area);
													}
												},foregroundType:regionType,backgroundType:'COUNTY',manipulable:1});
											})*/
											var aa = new DpMap(field.getId(), {center :mapLocation,zoom : 13}, function (map) { 
												
												
												
					    						  var fun = function(data){
					    							  if('ADD' === data.type){
															form.findField('gisid').setValue(data.code);
															form.findField('gisArea').setValue(data.area);															
														}else if('DELETE' === data.type){
															form.findField('gisNewId').setValue(null);
															form.findField('gisArea').setValue(null);
															baseinfo.pickupAndDeliverySmallZone1.ployfeature1.openEditTool();
														}else if('UPDATE' ===data.type){
															form.findField('gisArea').setValue(data.area);
														}
					    						  }
					    						  var callFun =function(data){
					    							  if(data.type ='QUERY'){
					    								  baseinfo.pickupAndDeliverySmallZone1.ployfeature1.closeEditTool();
					    							  }
					    						  }
					    						 //实例化一个新类			
					    						  baseinfo.pickupAndDeliverySmallZone.ployfeature1 = new DpMap.service.DpMap_polygon(map,field.getId(),
					    								  {isAddable:(baseinfo.viewState.view != config.viewState),polygonName:regionName, callBackFun:fun, foregroundType:regionType, backgroundType:'COUNTY',closeToolCallback:callFun});
					    					});	
										}
									}
								}]
							}).show();
						}
					});
					return;
				}
				//城市不为空  查看时  聚焦到 对应城市  ，修改时  聚焦到 对应绘画 区域
				else if(baseinfo.viewState.view != config.viewState){
					Ext.create('Ext.window.Window',{
						layout:'fit',
//						resizable : false,
						items:[{
							xtype: 'container',
							height:720,
							width:1150,
							listeners: {
								afterrender: function(field) {
									/*new DPMap.MyMap('DRAW', field.getId(),{center : mapLocation, zoom : 'STREET' },function(map) {
										baseinfo.pickupAndDeliverySmallZone.ployfeature = DMap.PolygonFeature(map,{isAddable:(baseinfo.viewState.view != config.viewState),callBackFun:function(data) {
											if('ADD' === data.type){
												form.findField('gisid').setValue(data.code);
												form.findField('gisArea').setValue(data.area);
											}else if('DELETE' === data.type){
												form.findField('gisNewId').setValue(null);
												form.findField('gisArea').setValue(null);
											}else if('UPDATE' ===data.type){
												form.findField('gisArea').setValue(data.area);
											}
										},foregroundType:regionType,backgroundType:'COUNTY',manipulable:1});
									})*/
									var aa = new DpMap(field.getId(), {center :mapLocation,zoom : 13}, function (map) {
											 //调用导航距离的方法-187862-dujunhui
										     baseinfo.pickupAndDeliverySmallZone.dpcon = new DpMap.common(map, "");
										      
				    						  var fun = function(data){
				    							  var p=null;
											      var centerPoint=null;
//											      var vCode=null;
											      baseinfo.pickupAndDeliverySmallZone.getDeliverySal(form.findField('management').getValue());//根据管理部门获取派送部坐标
				    							  if('ADD' === data.type){
														form.findField('gisid').setValue(data.code);
														form.findField('gisArea').setValue(data.area);
														form.findField('centerPointLat').setValue(data.centerPoint.lat);
														form.findField('centerPointLng').setValue(data.centerPoint.lng);
//														vCode=data.code;
														centerPoint=data.centerPoint;
														if(config.viewState=='UPDATE'){
															baseinfo.pickupAndDeliverySmallZone.submitPickupAndDeliverySmallZoneEntity(me,me.viewState,me.down('form').getForm().getValues(),'DELETE');
														}else{
//															baseinfo.pickupAndDeliverySmallZone.submitPickupAndDeliverySmallZoneEntity(me,me.viewState,me.down('form').getForm().getValues(),'NEW_ADD');
															field.up().close();
														}
													}else if('DELETE' === data.type){
														form.findField('gisNewId').setValue(null);
														form.findField('gisArea').setValue(null);
														form.findField('centerPointLat').setValue(null);
														form.findField('centerPointLng').setValue(null);
														form.findField('navigationDistance').setValue(null);
														baseinfo.pickupAndDeliverySmallZone.ployfeature1.openEditTool();
														baseinfo.pickupAndDeliverySmallZone.submitPickupAndDeliverySmallZoneEntity(me,'UPDATE',me.down('form').getForm().getValues(),'DELETE');
													}else if('UPDATE' ===data.type){
														centerPoint=data.centerPoint;
														form.findField('gisArea').setValue(data.area);
														form.findField('centerPointLat').setValue(data.centerPoint.lat);
														form.findField('centerPointLng').setValue(data.centerPoint.lng);
													}
				    							  
				    							    //返回点
													baseinfo.pickupAndDeliverySmallZone.ployfeature1.queryPointByCode(baseinfo.pickupAndDeliverySmallZone.deliverySalCoordinator,function(point){
															if(!Ext.isEmpty(point)){
															    p=point;
															}
														});
													 
													//导航方法
													baseinfo.pickupAndDeliverySmallZone.dpcon.getDrivingDis(p, centerPoint, "", function(obj){
														form.findField('navigationDistance').setValue(obj.drivingDis);
																});
				    						  }
				    						  var callFun =function(data){
				    							  if(data.type ='QUERY'){
				    								  baseinfo.pickupAndDeliverySmallZone.ployfeature1.closeEditTool();
				    							  }
				    						  }
				    						 //实例化一个新类			
				    						  baseinfo.pickupAndDeliverySmallZone.ployfeature1 = new DpMap.service.DpMap_polygon(map,field.getId(),
				    								  {isAddable:(baseinfo.viewState.view != config.viewState),polygonName:regionName, callBackFun:fun, foregroundType:regionType, backgroundType:'COUNTY',closeToolCallback:callFun});
				    					});	
								}
							}
						}]
					}).show();
					setTimeout(function(){
					if(Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature1)){
							if(baseinfo.viewState.update == config.viewState){
								var ifWhile = true;
									while(ifWhile && Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature1)){
										if(!Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature1)){
											baseinfo.pickupAndDeliverySmallZone.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
											/*if(!Ext.isEmpty(form.findField('gisid').getValue())){
												baseinfo.pickupAndDeliverySmallZone.ployfeature.closeEditTool();
											}*/
											ifWhile = false;
											return;
										}
									}
							}
					}else{
						baseinfo.pickupAndDeliverySmallZone.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
						/*if(!Ext.isEmpty(form.findField('gisid').getValue())){
							baseinfo.pickupAndDeliverySmallZone.ployfeature.closeEditTool();
						}*/
					}
					}, 2000);
				}else if(baseinfo.viewState.view == config.viewState){
					Ext.create('Ext.window.Window',{
						layout:'fit',
						resizable : false,
						items:[{
							xtype: 'container',
							height:720,
							width:1150,
							listeners: {
								afterrender: function(field) {
									var aa = new DPMap.MyMap('VIEW', field.getId(),{center : mapLocation, zoom : 'STREET' },function(map) {
										baseinfo.pickupAndDeliverySmallZone.ployfeature = DMap.PolygonFeature(map,{isAddable:(baseinfo.viewState.view != config.viewState),callBackFun:function(data) {
											if('ADD' === data.type){
												form.findField('gisid').setValue(data.code);
												form.findField('gisArea').setValue(data.area);
											}else if('DELETE' === data.type){
												form.findField('gisNewId').setValue(null);
												form.findField('gisArea').setValue(null);
											}else if('UPDATE' ===data.type){
												form.findField('gisArea').setValue(data.area);
											}
										},foregroundType:regionType,backgroundType:'COUNTY',manipulable:1});
									})
								}
							}
						}]
					}).show();
					setTimeout(function(){
						if(Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature)){
							if(baseinfo.viewState.update == config.viewState){
								var ifWhile = true;
									while(ifWhile && Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature)){
										if(!Ext.isEmpty(baseinfo.pickupAndDeliverySmallZone.ployfeature)){
											baseinfo.pickupAndDeliverySmallZone.ployfeature.showModifiyAblePolygons([form.findField('gisid').getValue()]);
											ifWhile = false;
											return;
										}
									}
							}
					 
					}else{
						baseinfo.pickupAndDeliverySmallZone.ployfeature.showModifiyAblePolygons([form.findField('gisid').getValue()]);
					} }, 2000);
				}
			}
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.reset'),
			disabled:baseinfo.viewState.view === config.viewState,
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
			} 
		},{
			text : baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.save'),
			disabled:baseinfo.viewState.view === config.viewState,
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.pickupAndDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		//将中心点坐标回传给GIS保存
	    		var regionName=me.formRecord.data.regionName;
	    		var gisid=me.formRecord.data.gisid;
	    		var centerPointLat=me.formRecord.data.centerPointLat;
	    		var centerPointLng=me.formRecord.data.centerPointLng;
	    		var jsonstr={'regionName':regionName,'gisid':gisid,
	    				'centerPointLat':centerPointLat,'centerPointLng':centerPointLng};
	    		if(!Ext.isEmpty(centerPointLat) && !Ext.isEmpty(centerPointLng)){
	    			//中心点保存，返回gis库中
	    			baseinfo.pickupAndDeliverySmallZone.dpcon.saveAreaCenterPoint(jsonstr);
	    		}
	    		baseinfo.pickupAndDeliverySmallZone.submitPickupAndDeliverySmallZoneEntity(me,me.viewState,me.formRecord.data);
			}
		}];
	}
});