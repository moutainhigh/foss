/**
 * 定人定区model									Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityModel
 * 定人定区store									Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityStore
 * 定人定区form									Foss.baseinfo.regionalVehicleIndex.QueryConditionForm
 * 定人定区grid									Foss.baseinfo.regionalVehicleIndex.QueryResultGrid
 * 定人定区winForm								Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWinForm
 * 定人定区winGrid								Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWinGrid
 * 定人定区win									Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWin
 * 大小区菜单panel								Foss.baseinfo.regionalVehicleIndex.RegionalVehicleBigZoneDetail
 */

//------------------------------------常量和公用方法----------------------------------
baseinfo.regionalVehicleIndex.rootParentId = '0'; //对应后台常量ComnConst.rootParentId
baseinfo.regionalVehicleIndex.ployfeature = null; //地图服务，可以通过进行 地图 展示所画区域
baseinfo.regionalVehicleIndex.mapWin = null; //查看地图的窗体
baseinfo.regionalVehicleIndex.regionNature = {bq:'BQ',sq:'SQ',bsq:'BSQ'};//区域性质：对应后台常量日ictionaryValueConstants.REGION_NATURE_BQ
baseinfo.regionalVehicleIndex.regionType = {b:'B',s:'S',bs:'BS'}; //接送货类型 
//baseinfo.regionalVehicleIndex.vehicleType = {dq:'CONSTANT_AREA_CAR',jd:'MOTOR_VEHICLE',djc:'',all:'ALL'}; //车辆类型 数据字典
baseinfo.regionalVehicleIndex.vehicleType = {fdq:'FIRST_CONSTANT_AREA_CAR',sdq:'SECOND_CONSTANT_AREA_CAR',jd:'MOTOR_VEHICLE',djc:'',all:'ALL'}; //车辆类型 数据字典
baseinfo.regionalVehicleIndex.regionTypeCommonSlect = {bq:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZone'),sq:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallZone'),djc:''}; //车辆类型 
baseinfo.regionalVehicleIndex.clickMenuEntityModel = null; //大小区实体 
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = 1;
	if(!Ext.isEmpty(message)){
		len = message.length;
	}
	Ext.Msg.show({
	    title:baseinfo.regionalVehicleIndex.i18n('i18n.baseinfo-util.fossAlert'),
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
//定车定区 修改和查看界面时初始化窗体
baseinfo.regionalVehicleIndex.initRegionalWin = function(win,record,regionNature){
	var form = win.editForm,form = form.getForm();
	form.findField('regionCode').setCombValue(record.get('regionName'),record.get('regionCode'));//区域
	form.findField('code').setValue(record.get('code'));//区域编码
	form.findField('regionType').setValue(record.get('regionType'));//接送货类型
//	form.findField('vehicleNo').setCombValue(record.get('vehicleNo'),record.get('vehicleNo'));//车牌号
	form.findField('fristRegionVehicleNo').setCombValue(record.get('fristRegionVehicleNo'),record.get('fristRegionVehicleNo'));//车牌号
	form.findField('secondRegionVehicleNo').setCombValue(record.get('secondRegionVehicleNo'),record.get('secondRegionVehicleNo'));//车牌号
	form.findField('motorVehicleNo').setCombValue(record.get('motorVehicleNo'),record.get('motorVehicleNo'));//车牌号
	form.findField('vehicleDept').setCombValue(record.get('vehicleDeptName'),record.get('vehicleDept'));//车辆所属车队组
//	form.findField('vehicleType').setValue(record.get('vehicleType'));//车辆职责类型
	form.findField('regionName').setValue(record.get('regionName'));//区域名称
	form.findField('regionNature').setValue(baseinfo.regionalVehicleIndex.regionNature.bsq === regionNature ?
			record.get('regionNature'): regionNature);//区域类型
	if(baseinfo.regionalVehicleIndex.regionNature.bsq === regionNature&&//TODO
			baseinfo.regionalVehicleIndex.regionNature.sq === record.get('regionNature')){
		form.findField('regionCode').setCombValue(record.get('smallZoneName'),record.get('regionCode'));//区域
		form.findField('code').setValue(record.get('smallZoneCode'));//区域编码
	}
	form.findField('motorVirtualCode').setValue(record.get('motorVirtualCode'));//机动车虚拟编码
	form.findField('fristRegionVehicleCode').setValue(record.get('fristRegionVehicleCode'));//机动车虚拟编码
	form.findField('secondRegionVehicleCode').setValue(record.get('secondRegionVehicleCode'));//机动车虚拟编码
	return win;
};
//点击大小区树后 新增 定车定区
baseinfo.regionalVehicleIndex.initTreeAddRegionalWin = function(win,record,regionNature){
	// 单字段 赋值 区域编码code和区域名称regionName和区域虚拟编码regionCode和区域类型(大小区域)regionNature
	//type接送货类型
	var editForm = win.editForm,form = editForm.getForm();
	//大区
	if(baseinfo.regionalVehicleIndex.regionNature.bq === regionNature){
		form.findField('fristRegionVehicleNo').hide();
		form.findField('fristRegionVehicleNo').setDisabled(false);
		form.findField('secondRegionVehicleNo').hide();
		form.findField('secondRegionVehicleNo').setDisabled(false);
		form.findField('regionCode').setCombValue(record.get('regionName'),record.get('virtualCode'));//区域
		form.findField('code').setValue(record.get('regionCode'));//区域编码
		form.findField('regionType').setValue(record.get('type'));//接送货类型
//		form.findField('vehicleType').setValue(baseinfo.regionalVehicleIndex.vehicleType.jd);//车辆职责类型
		form.findField('regionName').setValue(record.get('regionName'));//区域名称
		form.findField('regionNature').setValue(regionNature);//区域类型
	}
	//小区
	if(baseinfo.regionalVehicleIndex.regionNature.sq === regionNature){
		
		form.findField('motorVehicleNo').hide();
		form.findField('motorVehicleNo').setDisabled(true);
		if(record.data.regionType ==='PK'){
			form.findField('secondRegionVehicleNo').show();
			form.findField('secondRegionVehicleNo').setDisabled(false);
			form.findField('secondRegionVehicleNo').allowBlank =true;
		}else if(record.data.regionType=="DE"){ 
			form.findField('secondRegionVehicleNo').hide();
			form.findField('secondRegionVehicleNo').setValue('');
			form.findField('secondRegionVehicleNo').setDisabled(false);
		}
		form.findField('regionCode').setCombValue(record.get('regionName'),record.get('virtualCode'));//区域
		form.findField('code').setValue(record.get('regionCode'));//区域编码
		form.findField('regionType').setValue(record.get('regionType'));//接送货类型
//		form.findField('vehicleType').setValue(baseinfo.regionalVehicleIndex.vehicleType.dq);//车辆职责类型
		form.findField('regionName').setValue(record.get('regionName'));//区域名称
		form.findField('regionNature').setValue(regionNature);//区域类型
	}
	return win;
};
//分页查询 定去订车 时 动态加载 车辆汇总 数量
baseinfo.regionalVehicleIndex.setCarCount = function(grid,store){
	store.on('load',function(s,records){
		var jdCount = 0,fdqCount = 0,sdqCount=0,gridCount = 0;
        s.each(function(record){
        	if(!Ext.isEmpty(record.get('motorVirtualCode'))){
        		jdCount++;
        		gridCount++;
        	}
        	if(!Ext.isEmpty(record.get('fristRegionVehicleCode'))){
        		fdqCount++;
        		gridCount++;
        	}
        	if(!Ext.isEmpty(record.get('secondRegionVehicleCode'))){
        		sdqCount++;
        		gridCount++;
        	}

        })
        if(0 === store.getCount()){
    	    var toolbarArray = grid.dockedItems.items[2].items.items;
    	    toolbarArray[1].setText(0);
    	    toolbarArray[3].setText(0);
    	    toolbarArray[5].setText(0);
    	    toolbarArray[7].setText(0);
        }else{
    		if(!Ext.isEmpty(grid.dockedItems)){
    		    var toolbarArray = grid.dockedItems.items[2].items.items;
    		    toolbarArray[1].setText(gridCount);
    		    toolbarArray[3].setText(jdCount);
    		    toolbarArray[5].setText(fdqCount);
    		    toolbarArray[7].setText(sdqCount);
    		}
        }
	})
};
//大区 地图展示
baseinfo.regionalVehicleIndex.showMapWinBig = function(formId){
	var form = Ext.getCmp(formId).getForm(),virtualCode = form.findField('virtualCode').getValue()
		url = baseinfo.realPath('querySmallZonesByDeptCode.action'),
		objectVo = {},smallZoneEntity = {},gisIds = [],
		zoneModel = Ext.create('Foss.baseinfo.regionalVehicleIndex.SmallZoneEntityModel');
	zoneModel.set('management',form.findField('management').getValue());//管理部门
	zoneModel.set('regionType',baseinfo.regionalVehicleIndex.regionNature.sq);//区域类型
	zoneModel.set('bigzonecode',virtualCode);//接送货大区regionCode
	objectVo.smallZoneEntity = zoneModel.data;
	//通过大区虚拟code 得到 已选小区的gisId集合aJax同步请求
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.smallZoneEntityList)){
			for(var i in result.objectVo.smallZoneEntityList){
				gisIds.push(result.objectVo.smallZoneEntityList[i].gisid);
			}
		}else{
			if(Ext.isEmpty(result.message)){
				baseinfo.showInfoMsg('还没有圈定地图！');
			}else{
				baseinfo.showInfoMsg(result.message);
			}
			
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	},false);
	baseinfo.regionalVehicleIndex.getMapWin().show();
	baseinfo.regionalVehicleIndex.ployfeature.showModifiyAblePolygons(gisIds);
};
//小区 地图展示
baseinfo.regionalVehicleIndex.showMapWinSmall = function(formId){
	var form = Ext.getCmp(formId).getForm();
	baseinfo.regionalVehicleIndex.getMapWin().show();
	baseinfo.regionalVehicleIndex.ployfeature.showModifiyAblePolygons([form.findField('gisid').getValue()]);
};
//带有地图的 window
baseinfo.regionalVehicleIndex.getMapWin = function(){
	baseinfo.regionalVehicleIndex.mapWin = Ext.create('Ext.window.Window',{
		layout:'fit',
		items:[{
			xtype: 'container',
			height:400,
			width:700,
			listeners: {
				afterrender: function(field) {
					var aa = new DPMap.MyMap('VIEW', field.getId(),{center : "上海市", zoom : "STREET" },function(map) {
						baseinfo.regionalVehicleIndex.ployfeature = DMap.PolygonFeature(map,{isAddable:false,callBackFun:function(data) {}});
					})
				}
			}
		}]
	});
	return baseinfo.regionalVehicleIndex.mapWin;
};
//保存事件 
/**
 * 130566
 */
baseinfo.regionalVehicleIndex.submitRegionVehicleEntity = function(win,viewState,operatEntity,sourceGrid,operateType){
	var url = baseinfo.realPath('addRegionalVehicle.action')
		,m_success = baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.saveSuccess')								//保存成功！
		,m_failure = baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.saveFail')								//保存失败！
		,m_dateError = baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.dataError')								//数据异常！
		,objectVo = {}
		,operateNature = (baseinfo.regionalVehicleIndex.regionNature.bq === operateType) || (baseinfo.regionalVehicleIndex.regionNature.sq === operateType);
	objectVo.regionVehicleDto = operatEntity;
	if(baseinfo.viewState.add === viewState){
		//新增URL(已经有)
	}else if(baseinfo.viewState.update === viewState){
		//修改URL
		url = baseinfo.realPath('updateRegionalVehicle.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.regionVehicleDto)){
			sourceGrid.store.loadPage(1);
			if(!operateNature){
				baseinfo.regionalVehicleIndex.setCarCount(sourceGrid,sourceGrid.store);
			}
			//TODO 保存按钮 变为可用
			baseinfo.showInfoMsg(m_success);
			win.hide();
		}else{
			baseinfo.showErrorMes(result.message);
		}
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//作废事件
baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode = function(delAgencyCompanyType,operatRecord,operatGrid){
	var grid = Ext.isEmpty(operatGrid)?Ext.getCmp('T_baseinfo-regionalVehicleIndex_content').getQueryGrid():operatGrid,
		url = baseinfo.realPath('deleteRegionalVehicle.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = '';
			//批量作废
			url = baseinfo.realPath('deleteRegionalVehicle.action');
			/**
			 * 130566- 修复作废
			 */
			for (var j = 0; j < selection.length; j++) {
				if(!Ext.isEmpty(selection[j].get('motorVirtualCode'))){
					codeStr = codeStr + ',' + selection[j].get('motorVirtualCode');
				}
				if(!Ext.isEmpty(selection[j].get('fristRegionVehicleCode'))){
					codeStr = codeStr + ',' + selection[j].get('fristRegionVehicleCode');
				}
				if(!Ext.isEmpty(selection[j].get('secondRegionVehicleCode'))){
					codeStr = codeStr + ',' + selection[j].get('secondRegionVehicleCode');
				}
				//codeStr = codeStr + ',' + selection[j].get('motorVirtualCode')+','+selection[j].get('fristRegionVehicleCode')+','+selection[j].get('secondRegionVehicleCode');
			}
			objectVo.codeStr = codeStr;
		}else{
			/**
			 * 130566- 修复作废
			 */
			var codeStr = '';
			if(!Ext.isEmpty(operatRecord.get('motorVirtualCode'))){
				codeStr = codeStr + ',' + operatRecord.get('motorVirtualCode');
			}
			if(!Ext.isEmpty(operatRecord.get('fristRegionVehicleCode'))){
				codeStr = codeStr + ',' + operatRecord.get('fristRegionVehicleCode');
			}
			if(!Ext.isEmpty(operatRecord.get('secondRegionVehicleCode'))){
				codeStr = codeStr + ',' + operatRecord.get('secondRegionVehicleCode');
			}
			objectVo.codeStr = codeStr;
			//objectVo.codeStr = operatRecord.get('motorVirtualCode')+','+operatRecord.get('fristRegionVehicleCode')+','+operatRecord.get('secondRegionVehicleCode');
		}
		Ext.MessageBox.buttonText.yes = baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					baseinfo.regionalVehicleIndex.setCarCount(grid,grid.store);
					baseinfo.showInfoMsg(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.deleteSuccess'));
				},function(result){
					baseinfo.showErrorMes(result.message);
				});
			}
		});
	}
};
baseinfo.regionalVehicleIndex.prohibitedArticlesIsExist = function(field,fieldValue,fieldLabel,fieldNmae){
	var url = baseinfo.realPath('regionalVehicleIsExist.action'),objectVo ={}
	,entytyRecord = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel');
	entytyRecord.set(fieldNmae+'',fieldValue);
	objectVo.regionVehicleDto = entytyRecord.data;
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(Ext.isEmpty(result.objectVo.regionVehicleDto)){
			field.clearInvalid();
		}else{
			field.markInvalid(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
		}
	},function(result){
		field.markInvalid(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatBegin')+fieldLabel+baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.dataRepeatEnd'));
	});
};
//------------------------------------MODEL----------------------------------
//定人定区DtoModel
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel', {
extend: 'Ext.data.Model',
fields : [//区域名称
    {name:'regionName',type:'string'},
    //区域虚拟编码
    {name:'regionCode',type:'string'},
    //区域编码
    {name:'code',type:'string'},
    //接送货类型
    {name:'regionType',type:'string'},
    //车牌号
    {name:'vehicleNo',type:'string'},
    //一级定区车车牌号
    {name:'fristRegionVehicleNo',type:'string'},
    //二级定区车车牌号
    {name:'secondRegionVehicleNo',type:'string'},
    //机动车车牌号
    {name:'motorVehicleNo',type:'string'},
    //车辆所属车队组
    {name:'vehicleDept',type:'string'},
    //车辆所属车队组（扩展）
    {name:'vehicleDeptName',type:'string'},
    //车辆职责类型
    {name:'vehicleType',type:'string'},
    //小区域名称
    {name:'smallZoneName',type:'string'},
    //小区域虚拟编码
    {name:'smallZoneCode',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //一级定区车所在数据的虚拟编码
    {name:'fristRegionVehicleCode',type:'string'},
    //二级定区车所在数据的虚拟编码
    {name:'secondRegionVehicleCode',type:'string'},
    //机动车所在数据的虚拟编码
    {name:'motorVirtualCode',type:'string'},
    //区域性质：大区：BQ 小区:SQ
    {name:'regionNature',type:'string'}, 
	{name:'createUser',type:'string'},
	{name:'createDate',type:'date',convert: dateConvert,defaultValue:null}, 
	{name:'modifyDate',type:'date',convert: dateConvert,defaultValue:null},
	{name:'modifyUser',type:'string'}]
});
//接送货大区Model
Ext.define('Foss.baseinfo.regionalVehicleIndex.BigZoneEntityModel', {
extend: 'Ext.data.Model',
fields : [{name:'regionCode',type:'string'}, //大区编码
	  {name:'regionName',type:'string'},//大区名称
	  {name:'management',type:'string'},//管理部门
	  {name:'managementName',type:'string'},//管理部门
	  {name:'type',type:'string'},//区域类型
	  {name:'transDepartmentCode',type:'string'},//所属车队
	  {name:'active',type:'string'},//是否启用
	  {name:'notes',type:'string'},//备注
	  {name:'virtualCode',type:'string'} //虚拟编码
	  ]
});
//接送货小区Model
Ext.define('Foss.baseinfo.regionalVehicleIndex.SmallZoneEntityModel', {
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
//定人定区 STORE
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryRegionalVehicleByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.regionVehicleDtoList',
			totalProperty : 'totalCount'
		}
	}
});
//查看大小区详情 定人定区 STORE
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionVehicleStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel',
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryBigZoneDetailByCode.action'),
		reader : {
			type : 'json',
			root : 'objectVo.regionVehicleDtoList'
		}
	}
});
//大小区 treeStore
Ext.define('Foss.baseinfo.regionalVehicleIndex.ZoneTreeStore',{
  	extend: 'Ext.data.TreeStore',
  	root: {
		text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.district'),												//根的文本信息
		id : baseinfo.regionalVehicleIndex.rootParentId,			//设置根节点的ID
		expanded: true												//根节点是否展开
	},
  	nodeParam: 'objectVo.codeStr',
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath('queryPickupAndDeliveryZone.action'),
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	}
});
//创建一个本地的管理（车辆职责类型）名称store
Ext.define('Foss.baseinfo.regionalVehicleIndex.vehicleTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'valueCode',  type: 'string'},
		{name: 'valueName',  type: 'string'}
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


//------------------------------------PANEL----------------------------------
//定义"行政区域"树结构，将树形结构跟结点的鼠标右键功能结合起来：
Ext.define('Foss.baseinfo.regionalVehicleIndex.ZoneTreePanel',{
    extend:'Ext.tree.Panel',
    margin: false,
    autoScroll: true,
    height:870,
    animate: false,
    useArrows: true,
    frame: false,
    rootVisible: false,
    store: null,
    defaults: {margin:'5 5 5 5'},
  	constructor: function(config){
		var me = this,cfg = Ext.apply({}, config);
	    me.listeners = me.getMyListeners(config);
	    me.store = me.getStore(config);
		me.callParent([cfg]);
	},
	getMyListeners:function(config){
		var me = this;
		return {
	      //左键单击
			itemclick:function(node,record,item,index,e){
				e.preventDefault();
				var record = me.getSelectionModel().getSelection()[0]
					,pojo = record.raw;
				me.onclickTreeNode(me,record,pojo);
			},
			select:function(rowModel,record,index,opt){
				var pojo = record.raw;
				me.onclickTreeNode(me,record,pojo);
			    
			},
	      //右键单击
	      itemcontextmenu:function(node,record,item,index,e){
			//阻止浏览器默认行为处理事件
	        e.preventDefault();
	      }
	    };
	},
	//当树型结构中的结点被点击时的响应函数。显示这个结点的详情：
	onclickTreeNode : function(tree, record,pojo){
		var mainPanel  = Ext.getCmp('T_baseinfo-regionalVehicleIndex_content');//得到grid
		// 设置查询结果为展示项
		if(record.data.leaf){//小区
			mainPanel.items.items[1].items.items[1].getLayout().setActiveItem(2);
			var smallForm = mainPanel.items.items[1].items.items[1].items.items[2],store = smallForm.getDetailGrid().store
				,zoneModel = Ext.create('Foss.baseinfo.regionalVehicleIndex.SmallZoneEntityModel',pojo.entity);
			baseinfo.regionalVehicleIndex.clickMenuEntityModel = zoneModel;
			smallForm.loadRecord(zoneModel);
			store.loadPage(1);													// 加载定人定区  
		}else{//大区
			mainPanel.items.items[1].items.items[1].getLayout().setActiveItem(1);
			var bigForm = mainPanel.items.items[1].items.items[1].items.items[1],store = bigForm.getDetailGrid().store
				,zoneModel = Ext.create('Foss.baseinfo.regionalVehicleIndex.BigZoneEntityModel',pojo.entity);
			baseinfo.regionalVehicleIndex.clickMenuEntityModel = zoneModel;
			bigForm.loadRecord(zoneModel);
			store.loadPage(1);													// 加载定人定区  
		}
	},
	getStore:function(config){
		var me = this;
		if(me.store === null){
			me.store = Ext.create('Foss.baseinfo.regionalVehicleIndex.ZoneTreeStore');
		}
		return me.store;
	}
});
//------------------------------------FORM----------------------------------
//定人定区 查询条件
Ext.define('Foss.baseinfo.regionalVehicleIndex.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	//labelSeparator:''
    },
    height :150,
	layout:{
        type: 'table',
        columns: 2
    },
    record:null,												//绑定的model Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
		me.getForm().findField('vehicleDept').setCombValue(FossUserContext. getCurrentDept().name,FossUserContext.getCurrentDeptCode());
		me.down('commonowntruckselector').store.on('beforeload',function(store, operation, eOpts){
			var params = operation.params;
			params['truckVo.truck.orgId'] = FossUserContext.getCurrentDeptCode();
			Ext.applyIf(operation, {
				params : params
			});
		});
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
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
	},
	getItems:function(){
		var me = this;
		var store = FossDataDictionary.getDataDictionaryStore('VEHICLE_RESPONSIBILITY_CATEGORY',
				null, {
					'valueCode' : '',
					'valueName' : '全部'
				});
		return [{
			xtype:'commonmotorcadeselector',
//			xtype:'commonsaledepartmentselector',				//营业部
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.trunckSquad'),									//车队
			name:'vehicleDept',
			readOnly:true,
			listeners:{
	        	change:function(item,newValue){
	        		var me = this.up('form');
	        		var url = baseinfo.realPath('queryDeptCodes.action') ;
	        		 Ext.Ajax.request({
						url:url,
						jsonData:null,
						//成功
						success : function(response) {
						  	var json = Ext.decode(response.responseText);
						  	//查询部门编码集合.
						  	var deptCodeList = json.objectVo.deptCodeList;
						  	//获取车牌号
						  	var vehicleNo = me.getForm().findField('vehicleNo');
						  	vehicleNo.store.removeAll();
						  	vehicleNo.store.addListener('beforeload', function(store, operation,eOpts) {
								var searchParams = operation.params;
								if (Ext.isEmpty(searchParams)) {
									searchParams = {};
									Ext.apply(operation, {
										params : searchParams
									});
								}
								// 传递多个部门
								var deptCodes = deptCodeList;
								if (!Ext.isEmpty(deptCodes)) {
//									var deptCodeLst = deptCodes.split(',');
									searchParams['truckVo.truck.orgIds'] = deptCodes;
									searchParams['truckVo.truck.orgId'] = null;
								}
								
							});
						  	
						  	vehicleNo.store.loadPage(1);
			            },
				        //保存失败
				        exception : function(response) {
				              var json = Ext.decode(response.responseText);
				              //失败消息
				              me.showWarningMsg(baseinfo.freightRoute.i18n('foss.baseinfo.notice'),json.message);
				        }
					});
	        	}
	        }
		},{
			xtype:'commonowntruckselector',
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.carNumber'),									//车牌号
			labelWidth:110,
			deptCodes:null,
			name:'vehicleNo'
		},{
			xtype:'combobox',
			colspan:2,
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.vehicleDutiesType'),								//车辆职责类型
			name: 'vehicleType',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:false,
			store:Ext.create('Foss.baseinfo.regionalVehicleIndex.vehicleTypeStore',{
				data:{'items':[
								{'valueCode':'','valueName':baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.alllabel')},//'全部'
								{'valueCode':'FIRST_CONSTANT_AREA_CAR','valueName':'一级定区车'},
							    {'valueCode':'SECOND_CONSTANT_AREA_CAR','valueName':'二级定区车'},
							    {'valueCode':'MOTOR_VEHICLE','valueName':'机动车'},
						   	]}
			}),
			value:''
		},{
			xtype : 'container',colspan:2,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleQueryButton'),
				hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleQueryButton'),
				handler : function() {
					var form = this.up('form').getForm();
					form.findField('vehicleNo').setValue(null);
					form.findField('vehicleType').setValue('');
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				columnWidth:.85
			},{
				xtype:'button',
				width: 75,
				text :  baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.query'),
				disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleQueryButton'),
				hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleQueryButton'),
				cls:'yellow_button',
				handler : function() {
					var mainPanel  = Ext.getCmp('T_baseinfo-regionalVehicleIndex_content')
					grid = mainPanel.getQueryGrid(); store = grid.store;//得到grid
				// 设置查询结果为展示项
				mainPanel.items.items[1].items.items[1].getLayout().setActiveItem(0);
//				grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
				store.loadPage(1);//用分页的moveFirst()方法
				baseinfo.regionalVehicleIndex.setCarCount(grid,store);
			}
			}]
		}];
	}
});
//定人定区 界面form
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 1
    },
    formRecord:null,												//绑定的model Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityModel
    regionNature:null,												//标识是否大小区baseinfo.regionalVehicleIndex.regionNature
    viewState:null,
    constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);

		me.down('commonowntruckselector').store.on('beforeload',function(store, operation, eOpts){
			var params = operation.params;
//			params['truckVo.truck.orgId'] = FossUserContext.getCurrentDeptCode();
			Ext.applyIf(operation, {
				params : params
			});
		});
	
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
			width:300,
			readOnly:true
	    };
	},
	//消息提醒框
	showWarningMsg : function(title,message,fun){
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
	},
	getItems:function(config){
		var me = this,regionName = 'textfield',vehicleType = '';
		if(baseinfo.regionalVehicleIndex.regionNature.bq === config.regionNature){
			regionName = 'commonbigzoneselector';
			vehicleType = baseinfo.regionalVehicleIndex.vehicleType.jd;
		}else if(baseinfo.regionalVehicleIndex.regionNature.sq === config.regionNature){
			regionName = 'commonsmallzoneselector';
			vehicleType = baseinfo.regionalVehicleIndex.vehicleType.dq;
		}else if(baseinfo.regionalVehicleIndex.regionNature.bsq === config.regionNature){
			regionName = 'commonservicezoneselector';
			vehicleType = baseinfo.regionalVehicleIndex.vehicleType.djc;
		}
		return [{
			xtype:regionName,
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.area'),								//区域
			name:'regionCode',
			valueField : 'virtualCode',// 值
			allowBlank:false,
			readOnly:(baseinfo.viewState.view === config.viewState||baseinfo.regionalVehicleIndex.regionNature.bq === config.regionNature||baseinfo.regionalVehicleIndex.regionNature.sq === config.regionNature),
			listeners:{
				select:function(field,records){
					//机动车大区 当为大区时，显示机动车，且输入框为必填，一二级定区车输入框隐藏，为非必填
					baseinfo.regionalVehicleIndex.regionTypeCommonSlect
					if(!Ext.isEmpty(records)&&baseinfo.regionalVehicleIndex.regionTypeCommonSlect.bq === records[0].get('zoneType')){
//						me.getForm().findField('vehicleType').setValue(baseinfo.regionalVehicleIndex.vehicleType.jd);
						me.getForm().findField('motorVehicleNo').show();
						me.getForm().findField('motorVehicleNo').setDisabled(false);
						me.getForm().findField('fristRegionVehicleNo').hide();
						me.getForm().findField('fristRegionVehicleNo').setDisabled(true);
						me.getForm().findField('fristRegionVehicleNo').allowBlank =true;
						me.getForm().findField('secondRegionVehicleNo').hide();
						me.getForm().findField('secondRegionVehicleNo').setDisabled(true);
						me.getForm().findField('secondRegionVehicleNo').allowBlank =true;
						me.getForm().findField('regionNature').setValue(baseinfo.regionalVehicleIndex.regionNature.bq);
					}
					//定区车小区
					else if(!Ext.isEmpty(records)&&baseinfo.regionalVehicleIndex.regionTypeCommonSlect.sq === records[0].get('zoneType')){
//						me.getForm().findField('vehicleType').setValue(baseinfo.regionalVehicleIndex.vehicleType.dq);
						me.getForm().findField('motorVehicleNo').hide();
						me.getForm().findField('motorVehicleNo').setDisabled(true);
						me.getForm().findField('fristRegionVehicleNo').show();
						me.getForm().findField('fristRegionVehicleNo').setDisabled(false);
						me.getForm().findField('fristRegionVehicleNo').allowBlank =false;
						/**
						 * 130566    若为接货区  可以有二级车，若是送货去 不允许有二级车
						 */
						if(records[0].get('regionType') ==='PK'){
							me.getForm().findField('secondRegionVehicleNo').show();
							me.getForm().findField('secondRegionVehicleNo').setDisabled(false);
							me.getForm().findField('secondRegionVehicleNo').allowBlank =true;
						}else if(records[0].get('regionType') ==='DE'){ 
							me.getForm().findField('secondRegionVehicleNo').hide();
							me.getForm().findField('secondRegionVehicleNo').setValue('');
							me.getForm().findField('secondRegionVehicleNo').setDisabled(false);
						}
						me.getForm().findField('regionNature').setValue(baseinfo.regionalVehicleIndex.regionNature.sq);
					}
					if(!Ext.isEmpty(records)){
						me.getForm().findField('code').setValue(records[0].get('regionCode'));
						me.getForm().findField('motorVirtualCode').setValue(records[0].get('motorVirtualCode'));
						me.getForm().findField('fristRegionVehicleCode').setValue(records[0].get('fristRegionVehicleCode'));
						me.getForm().findField('secondRegionVehicleCode').setValue(records[0].get('secondRegionVehicleCode'));
						me.getForm().findField('regionType').setValue(records[0].get('regionType'));
						me.getForm().findField('regionName').setValue(records[0].get('regionName'));
					}
				}
			}
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.areaCode'),							//区域编码
			name:'code'
		},FossDataDictionary.getDataDictionaryCombo('BSE_REGION_TYPE',{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.shipperType'),							//接送货类型
			name: 'regionType',
			readOnly:true,
			listeners:{
				change:function(item,newValue){					
					}
			}
		}),{
			xtype:'commonowntruckselector',
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.motorCarNumber'),								//机动车
			emptyText:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.enterQuery'),  //请先输入查询条件
			name:'motorVehicleNo',
			allowBlank:false,
			readOnly:(baseinfo.viewState.view === config.viewState),
			listeners:{
				select:function(field,records){
					/**
					 * ASP-项目优化：leo-zeng
					 */
					var regionCode=me.getForm().findField('code').getValue();
					var regionNature =me.getForm().findField('regionNature').getValue();
					//定区车小区
					if(!Ext.isEmpty(records)){
						Ext.Ajax.request({
							url : baseinfo.realPath('validSelectOrgIsRegionOrg.action'),
							jsonData : {'objectVo':{'regionVehicleDto':{'code':regionCode,'regionNature':regionNature},'motorOrg':records[0].get('orgId')}},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var isSuccess =result.objectVo.returnInt;
								if(isSuccess ==1){
									me.getForm().findField('vehicleDept').setCombValue(records[0].get('orgName'),records[0].get('orgId'));//车辆所属车队组
								}else if (isSuccess ==0){
									baseinfo.showErrorMes('所选车辆必须为本车队车辆！');
									field.setCombValue('','');
								}
							},
							failure : function(response) {
								var result = Ext.decode(response.responseText);
								baseinfo.showErrorMes(result.message);
							}
						});
						
					}
				}
			}
		},{
			xtype:'commonowntruckselector',
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.fristRegionVehicleCarNumber'),								//一级定区车
			emptyText:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.enterQuery'),  //请先输入查询条件
			name:'fristRegionVehicleNo',
			allowBlank:false,
			readOnly:(baseinfo.viewState.view === config.viewState),
			listeners:{
				select:function(field,records){
					/**
					 * ASP项目优化：leo-zeng
					 */
					var regionCode=me.getForm().findField('code').getValue();
					var regionNature =me.getForm().findField('regionNature').getValue();
					//定区车小区
					if(!Ext.isEmpty(records)){
						Ext.Ajax.request({
							url : baseinfo.realPath('validSelectOrgIsRegionOrg.action'),
							jsonData : {'objectVo':{'regionVehicleDto':{'code':regionCode,'regionNature':regionNature},'motorOrg':records[0].get('orgId')}},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								var isSuccess =result.objectVo.returnInt;
								if(isSuccess ==1){
									me.getForm().findField('vehicleDept').setCombValue(records[0].get('orgName'),records[0].get('orgId'));//车辆所属车队组
								}else if (isSuccess ==0){
									baseinfo.showErrorMes('所选车辆必须为本车队车辆！');
									field.setCombValue('','');
								}
							},
							failure : function(response) {
								var result = Ext.decode(response.responseText);
								baseinfo.showErrorMes(result.message);
							}
						});
					}
				}
			}
		},{
			xtype:'commonowntruckselector',
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.secondRegionVehicleCarNumber'),								//二级定区车
			emptyText:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.enterQuery'),  //请先输入查询条件
			name:'secondRegionVehicleNo',
			allowBlank:true,
			readOnly:(baseinfo.viewState.view === config.viewState),
			listeners:{
				select:function(field,records){
					//定区车小区
					if(!Ext.isEmpty(records)){
						//做非空校验
						if(!Ext.isEmpty(me.getForm().findField('vehicleDept').getValue())){
							//若一级车对应的部门 ，和二级车对应的部门不一致
							if(me.getForm().findField('vehicleDept').getValue()!=records[0].get('orgId')){
								baseinfo.showInfoMsg('该二级车不是属于'+me.getForm().findField('vehicleDept').getRawValue()+'的车！');
								me.getForm().findField('secondRegionVehicleNo').setCombValue('','');
								return ;
							}
						}
						//me.getForm().findField('vehicleDept').setCombValue(records[0].get('orgName'),records[0].get('orgId'));//车辆所属车队组
					}
				}
			}
		},{
			xtype:'commonmotorcadeselector',					//营业部
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.truckBelongSquad'),									//车队
			name:'vehicleDept',
			readOnly:true
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.motorVirtualCode'),
			hidden:true,
			name:'motorVirtualCode'
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.fristRegionVehicleCode'),
			hidden:true,
			name:'fristRegionVehicleCode'
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.secondRegionVehicleCode'),
			hidden:true,
			name:'secondRegionVehicleCode'
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.areaName'),
			hidden:true,
			name:'regionName'
		},{
			fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.pickupAndDeliveryBigZone.areaType'),
			hidden:true,
			name:'regionNature'
		}
		];
	}
});

//定人定区 点击菜单查看详情form 大区
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleBigZoneDetail', {
	extend : 'Ext.form.Panel',
//	layout:'fit',
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	formRecord:null,											//绑定的model Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config,me.getDefaults(config));
		/*me.items = [me.getInfoForm(),me.getDetailGrid(config),{
			xtype:'button',
			text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void')
		}];*/
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
			readOnly:true
	    };
	},
	getItems:function(config,defaults){
		var me = this,id = me.getId();
		return [{
			xtype:'container',
			defaultType : 'textfield',
			defaults:defaults,
//			height:600,
		    layout: {
		        type: 'table',
		        columns: 3
		    },
			items:[{
				colspan:3,
				xtype:'label',								//区域
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneBasicInfo')
			},{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneName'),								//区域
				name:'regionName'
			},{
				colspan:2,
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneCode'),							//区域编码
				name:'regionCode'
			},FossDataDictionary.getDataDictionaryCombo('BSE_REGION_TYPE',{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneType'),							//接送货类型
				name: 'type',
				readOnly:true
			}),{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.manageDept'),							//接送货类型
				name:'managementName'
			},{
		    	margin : '0 0 0 20',
				xtype:'label',								//区域
				html:'<a href="javascript:baseinfo.regionalVehicleIndex.showMapWinBig('+"'"+id+"'"+')">查看电子地图</a>'
			}
			/*****隐藏域***********/
			,{
				name:'virtualCode',
				hidden:true
			},{
				name:'management',
				hidden:true
			}]
		},me.getDetailGrid(config)/*,{
			xtype:'button',
			text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void')
		}*/];
	},
	detailGrid:null,
	getDetailGrid:function(config){
		var me = this;
		if(me.detailGrid === null){
			me.detailGrid = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleBigZoneDetailGrid',{
				height:350,
				title:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.motorVehicleInfo')
			});
		}
		return me.detailGrid;
	}
});
//定人定区 点击菜单查看详情form 小区
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleSmallZoneDetail', {
	extend : 'Ext.form.Panel',
	layout:'fit',
	formRecord:null,											//绑定的model Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults(config);
		me.items = me.getItems(config,me.getDefaults(config));
		me.callParent([cfg]);
	},
	getDefaults:function(config){
		var me = this;
		return {
	    	margin : '8 0 5 10',
	    	//labelSeparator:'',
			readOnly:true
	    };
	},
	getItems:function(config,defaults){
		var me = this,id = me.getId();
		return [{
			xtype:'container',
			defaultType : 'textfield',
			defaults:defaults,
		    layout: {
		        type: 'table',
		        columns: 4
		    },
			items:[{
				colspan:4,
				xtype:'label',								//区域
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallBasicInfo')
			},{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallZoneName'),								//区域
				name:'regionName'
			},{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallZoneCode'),							//区域编码
				name:'regionCode'
			},{
				fieldLabel:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.manageDept'),							//接送货类型
				name:'managementName'
			},{
		    	margin : '0 0 0 20',
				xtype:'label',								//区域
				html:'<a href="javascript:baseinfo.regionalVehicleIndex.showMapWinSmall('+"'"+id+"'"+')">查看电子地图</a>'
			}
			/*****隐藏域***********/
			,{
				name:'virtualCode',
				hidden:true
			},{
				name:'gisid',
				hidden:true
			},{
				name:'management',
				hidden:true
			}]
		},me.getDetailGrid(config),{
			xtype:'button',
			text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void')
		}];
	},
	detailGrid:null,
	getDetailGrid:function(config){
		var me = this;
		if(me.detailGrid === null){
			me.detailGrid = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleSmallZoneDetailGrid',{
				height:350,
				title:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.areaCarInfo')
			});
		}
		return me.detailGrid;
	}
});
//------------------------------------GRID----------------------------------
//定人定区 查询结果grid
Ext.define('Foss.baseinfo.regionalVehicleIndex.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	id:'Deppon_regionalVehicleIndex_QueryResultGrid_id',
	title : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
 								// 选择类型设置为：行选择
	emptyText: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
	
	
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
		me.dockedItems = me.getDockedItems(config);
		//添加分页控件
		me.bbar = me.getPagingToolbar(config);
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	},
	getDockedItems:function(config){
		var me = this;
		return[{
            xtype: 'toolbar',
            dock: 'bottom',
            items: [{
				xtype:'label',
		    	margin : '0 0 0 30',
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.carCount')
			},{
				xtype:'label',
				text:'0'
			},{
				xtype:'label',
		    	margin : '0 0 0 30',
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.motorVehicleCount')
			},{
				xtype:'label',
				text:'0'
			},{
				xtype:'label',
		    	margin : '0 0 0 30',
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.firstareaCarCount')
			},{
				xtype:'label',
				text:'0'
			},{
				xtype:'label',
		    	margin : '0 0 0 30',
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.secondareaCarCount')
			},{
				xtype:'label',
				text:'0'
			}]
        },{
        xtype: 'toolbar',
            items: [{
    			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.add'),								//新增
    			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
    			disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleAddButton'),
    			hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleAddButton'),
    			handler :function(){
    				me.addRegionVehicleEntity({}).show();
    			} 
    		},'-', {
    			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),								//作废
    			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
    			disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'),   			
    			hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'),   			
    			handler :function(){
    				baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(baseinfo.delAgencyType,null,Ext.getCmp('T_baseinfo-regionalVehicleIndex_content').getQueryGrid());
    			} 
    		},'-',{
    			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.export'),								//导出
    			//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
    			disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'),   			
    			hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'),   			
    			handler :function(){
    				if(!Ext.fly('downloadAttachFileForm')){
					    var frm = document.createElement('form');
					    frm.id = 'downloadAttachFileForm';
					    frm.style.display = 'none';
					    document.body.appendChild(frm);
					}
					var result = Ext.getCmp('Deppon_regionalVehicleIndex_QueryResultGrid_id').store;
					var queryForm = Ext.getCmp('T_baseinfo-regionalVehicleIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryParams = queryForm.getValues();
					//若异常信息不为空
					if(result.getCount()!=0){
//						Ext.Ajax.timeout =240000;
						Ext.Ajax.request({
							url:baseinfo.realPath('exportRegionalVehicleList.action'),
							form: Ext.fly('downloadAttachFileForm'),
							method : 'POST',
							params :{
								//车辆所属车队组
								'objectVo.regionVehicleDto.vehicleDept':queryParams.vehicleDept,
								//车牌号
								'objectVo.regionVehicleDto.vehicleNo':queryParams.vehicleNo,
								//车辆职责类型
								'objectVo.regionVehicleDto.vehicleType':queryParams.vehicleType
							},
							isUpload: true
						});
					}else{
						Ext.Msg.alert(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.exportFailed'));  //导出失败
					}
    			} 
    		}]
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
	//得到定人定区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'regionNature':Ext.isEmpty(formRecord.get('regionNature'))?baseinfo.regionalVehicleIndex.regionNature.bsq:formRecord.get('regionNature'),
				'formRecord':formRecord
			});
			if(baseinfo.regionalVehicleIndex.regionNature.bq == formRecord.get('regionNature')){
				win.editForm.getForm().findField('motorVehicleNo').show();
				win.editForm.getForm().findField('motorVehicleNo').setDisabled(false);
				win.editForm.getForm().findField('fristRegionVehicleNo').hide();
				win.editForm.getForm().findField('fristRegionVehicleNo').setDisabled(true);
				win.editForm.getForm().findField('fristRegionVehicleNo').allowBlank =true;
				win.editForm.getForm().findField('secondRegionVehicleNo').hide();
				win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(true);
				win.editForm.getForm().findField('secondRegionVehicleNo').allowBlank =true;
			}else if(baseinfo.regionalVehicleIndex.regionNature.sq == formRecord.get('regionNature')){
				win.editForm.getForm().findField('motorVehicleNo').hide();
				win.editForm.getForm().findField('motorVehicleNo').setDisabled(true);
				win.editForm.getForm().findField('fristRegionVehicleNo').show();
				win.editForm.getForm().findField('fristRegionVehicleNo').setDisabled(false);
				win.editForm.getForm().findField('fristRegionVehicleNo').allowBlank =false;
				/**
				 * 130566 -zjf 
				 */
				if(formRecord.get('regionType')==='PK'){
					win.editForm.getForm().findField('secondRegionVehicleNo').show();
					win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(false);
					win.editForm.getForm().findField('secondRegionVehicleNo').allowBlank =true;
				}else if(formRecord.get('regionType')==='DE'){
					win.editForm.getForm().findField('secondRegionVehicleNo').hide();
					win.editForm.getForm().findField('secondRegionVehicleNo').setValue('');
					win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(true);
				}
				
			}
		}
		//初始化window
		return baseinfo.regionalVehicleIndex.initRegionalWin(win,formRecord,baseinfo.regionalVehicleIndex.regionNature.bsq);
	},
	//得到定人定区编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getRegionVehicleEntityWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.alterRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.viewRegionalVehicle'),viewState,param);
		}
	},
	addRegionVehicleEntityWin:null,						//新增基定人定区公司
	addRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.add,param);
	},
	updateRegionVehicleEntityWin:null,						//修改基定人定区
	updateRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.update,param);
	},
	viewRegionVehicleEntityWin:null,						//查看基定人定区
	viewRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.view,param);
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
	    	//查看 定人定区
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewRegionVehicleEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-regionalVehicleIndex_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//车辆所属车队组
								'objectVo.regionVehicleDto.vehicleDept':entity.vehicleDept,
								//车牌号
								'objectVo.regionVehicleDto.vehicleNo':entity.vehicleNo,
								//车辆职责类型
								'objectVo.regionVehicleDto.vehicleType':entity.vehicleType
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
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.update'),
                disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleUpdateButton'), 
                hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleUpdateButton'), 
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				me.updateRegionVehicleEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),
                disabled:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'), 
                hidden:!baseinfo.regionalVehicleIndex.isPermission('queryRegionalVehicleByEntity/regionalVehicleDisableButton'), 
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }],
            width:70
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneCode'),									//大区CODE
			dataIndex : 'code',
			 width:80
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneName'),									//大区名称
			dataIndex : 'regionName',
			 width:120
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.bigZoneType'),									//大区类型
			dataIndex : 'regionType',
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'BSE_REGION_TYPE');
			}, 
			 width:60
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.motorCarNumber'),									//机动车车牌号
			dataIndex : 'motorVehicleNo',
			 width:80
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallZoneCode'),									//小区CODE
			dataIndex : 'smallZoneCode',
			 width:90
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.smallZoneName'),									//小区名称
			dataIndex : 'smallZoneName',
			 width:100
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.fristRegionVehicleCarNumber'),								//定人定区名称
			dataIndex : 'fristRegionVehicleNo',
			 width:80
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.secondRegionVehicleCarNumber'),								//定人定区名称
			dataIndex : 'secondRegionVehicleNo', 
			 width:80
		}];
	}
});
//定人定区 点击菜单大区查询结果grid
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleBigZoneDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
//    enableColumnHide:false,
//    enableColumnMove:false,
//	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
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
		me.dockedItems = me.getDockedItems(config);
		me.callParent([cfg]);
	},
	getDockedItems:function(config){
		var me = this;
		return[{
            xtype: 'toolbar',
            items: [{
    			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addMotorVehicle'),								//新增
    			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
    			handler :function(){
    				me.addRegionVehicleEntity({formRecord:baseinfo.regionalVehicleIndex.clickMenuEntityModel}).show();
    			} 
    		}]
        },{
            xtype: 'toolbar',
            dock: 'bottom',
            items: [{
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),								//作废
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler :function(){
					baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(baseinfo.delAgencyType,null,me);
				}
			}]
        }];
	},
	//得到菜单大区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':Ext.isEmpty(param.grid)?this:param.grid,
				'regionNature':baseinfo.regionalVehicleIndex.regionNature.bq,
				'formRecord':(baseinfo.viewState.add === viewState)?Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel'):formRecord
			});
				//由于是大区的
				win.editForm.getForm().findField('motorVehicleNo').show();
				win.editForm.getForm().findField('motorVehicleNo').setDisabled(false);
				win.editForm.getForm().findField('fristRegionVehicleNo').allowBlank =true;
				win.editForm.getForm().findField('secondRegionVehicleNo').allowBlank =true;
				win.editForm.getForm().findField('fristRegionVehicleNo').hide();
				win.editForm.getForm().findField('fristRegionVehicleNo').setDisabled(true);
				win.editForm.getForm().findField('secondRegionVehicleNo').hide();
				win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(true);
		}
		//初始化window
		return (baseinfo.viewState.add === viewState)?baseinfo.regionalVehicleIndex.initTreeAddRegionalWin(win,baseinfo.regionalVehicleIndex.clickMenuEntityModel,baseinfo.regionalVehicleIndex.regionNature.bq):baseinfo.regionalVehicleIndex.initRegionalWin(win,formRecord,baseinfo.regionalVehicleIndex.regionNature.bq);
	},
	//得到定人定区编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getRegionVehicleEntityWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.alterRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.viewRegionalVehicle'),viewState,param);
		}
	},
	addRegionVehicleEntityWin:null,						//新增基定人定区公司
	addRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.add,param);
	},
	updateRegionVehicleEntityWin:null,						//修改基定人定区
	updateRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.update,param);
	},
	viewRegionVehicleEntityWin:null,						//查看基定人定区
	viewRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.view,param);
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
	    	//查看 定人定区
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewRegionVehicleEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		var me = this;
		return Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('form').getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.formRecord);
					var entity = queryForm.formRecord.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//车辆所属车队组
								'objectVo.regionVehicleDto.regionCode':entity.virtualCode,
								//区域性质
								'objectVo.regionVehicleDto.regionNature':baseinfo.regionalVehicleIndex.regionNature.bq
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
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.update'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
                	param.grid = grid;
    				me.updateRegionVehicleEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : '机动车牌号',									//定人定区名称
			dataIndex : 'motorVehicleNo',
			flex:1
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.truckBelongSquad'),									//定人定区类型
			dataIndex : 'vehicleDeptName',flex:1
		}];
	}
});
//定人定区 点击菜单小区查询结果grid
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleSmallZoneDetailGrid', {
	extend: 'Ext.grid.Panel',
	title : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.resultList'),
    sortableColumns:false,
//    enableColumnHide:false,
//    enableColumnMove:false,
//	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.queryResultIsNull'),							//查询结果为空
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
		me.dockedItems = me.getDockedItems(config);
		me.callParent([cfg]);
	},
	getDockedItems:function(config){
		var me = this;
		return[{
            xtype: 'toolbar',
            items: [{
    			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addAreaCar'),								//新增
    			//hidden:!pricing.isPermission('../pricing/saveRole.action')),
    			handler :function(){
    				me.addRegionVehicleEntity({formRecord:baseinfo.regionalVehicleIndex.clickMenuEntityModel}).show();
    			} 
    		}]
        },{
            xtype: 'toolbar',
            dock: 'bottom',
            items: [{
				text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),								//作废
				//hidden:!pricing.isPermission('../pricing/deleteRole.action')),
				handler :function(){
					baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(baseinfo.delAgencyType,null,me);
				}
			}]
        }];
	},
	//得到 小区 编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':Ext.isEmpty(param.grid)?this:param.grid,
				'regionNature':baseinfo.regionalVehicleIndex.regionNature.sq,
				'formRecord':(baseinfo.viewState.add === viewState)?Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel'):formRecord
			});
			//由于是小区的
			win.editForm.getForm().findField('motorVehicleNo').hide();
			win.editForm.getForm().findField('motorVehicleNo').setDisabled(true);
			win.editForm.getForm().findField('fristRegionVehicleNo').show();
			win.editForm.getForm().findField('fristRegionVehicleNo').setDisabled(false);
			win.editForm.getForm().findField('fristRegionVehicleNo').allowBlank =false;
			/**
			 * 130566 -zjf 
			 */
			if(formRecord.get('regionType')==='PK'){
				win.editForm.getForm().findField('secondRegionVehicleNo').show();
				win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(false);
				win.editForm.getForm().findField('secondRegionVehicleNo').allowBlank =true;
			}else if(formRecord.get('regionType')==='DE'){
				win.editForm.getForm().findField('secondRegionVehicleNo').hide();
				win.editForm.getForm().findField('secondRegionVehicleNo').setValue('');
				win.editForm.getForm().findField('secondRegionVehicleNo').setDisabled(true);
			}
		}
		//初始化window
		return (baseinfo.viewState.add === viewState)?baseinfo.regionalVehicleIndex.initTreeAddRegionalWin(win,baseinfo.regionalVehicleIndex.clickMenuEntityModel,baseinfo.regionalVehicleIndex.regionNature.sq):baseinfo.regionalVehicleIndex.initRegionalWin(win,formRecord,baseinfo.regionalVehicleIndex.regionNature.sq);
	},
	//得到定人定区编辑窗体,查看状态viewState："ADD"新增,"UPDATE"修改,"VIEW"查看
	getRegionVehicleEntityWin:function(viewState,param){
		if(baseinfo.viewState.add === viewState){
			return this.getAgencyDeptWin(this.addRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.update === viewState){
			return this.getAgencyDeptWin(this.updateRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.alterRegionalVehicle'),viewState,param);
		}else if(baseinfo.viewState.view === viewState){
			return this.getAgencyDeptWin(this.viewRegionVehicleEntityWin,baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.viewRegionalVehicle'),viewState,param);
		}
	},
	addRegionVehicleEntityWin:null,						//新增基定人定区公司
	addRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.add,param);
	},
	updateRegionVehicleEntityWin:null,						//修改基定人定区
	updateRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.update,param);
	},
	viewRegionVehicleEntityWin:null,						//查看基定人定区
	viewRegionVehicleEntity:function(param){
		return this.getRegionVehicleEntityWin(baseinfo.viewState.view,param);
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
	    	//查看 定人定区
	    	itemdblclick: function(view,record) {
				var param = {};
            	param.formRecord = record;
				me.viewRegionVehicleEntity(param).show();
	    	}
	    };
	},
	getStore:function(){
		var me = this;
		return Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleStore',{
			autoLoad : false,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('form').getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.formRecord);
					var entity = queryForm.formRecord.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//车辆所属车队组
								'objectVo.regionVehicleDto.regionCode':entity.virtualCode,
								//车牌号
								'objectVo.regionVehicleDto.regionNature':baseinfo.regionalVehicleIndex.regionNature.sq
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
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.operate'),//操作
			items: [{
            	iconCls:'deppon_icons_edit',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.update'),
                handler: function(grid, rowIndex, colIndex) {
    				var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
                	param.grid = grid;
    				me.updateRegionVehicleEntity(param).show();
    			}
            },{
            	iconCls:'deppon_icons_cancel',
                tooltip: baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.void'),
                disabled:baseinfo.actioncolumnDisabled,
                handler: function(grid, rowIndex, colIndex) {
    				baseinfo.regionalVehicleIndex.deleteRegionVehicleEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
                }
            }]
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.fristRegionVehicleCarNumber'),		//一级车						//定人定区名称
			dataIndex : 'fristRegionVehicleNo',flex:1
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.secondRegionVehicleCarNumber'),//二级车								//定人定区名称
			dataIndex : 'secondRegionVehicleNo',flex:1
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.truckBelongSquad'),									//定人定区类型
			dataIndex : 'vehicleDeptName',flex:1
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-regionalVehicleIndex_content')){
		return;
	}
	var queryTree  = Ext.create('Foss.baseinfo.regionalVehicleIndex.ZoneTreePanel');//查询树PANEL
	var queryForm  = Ext.create('Foss.baseinfo.regionalVehicleIndex.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleDtoModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.regionalVehicleIndex.QueryResultGrid');//查询结果显示列表
	var queryDetailBigZone  = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleBigZoneDetail',{'formRecord':Ext.create('Foss.baseinfo.regionalVehicleIndex.BigZoneEntityModel')});//查询结果显示列表
	var queryDetailSmallZone  = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionalVehicleSmallZoneDetail',{'formRecord':Ext.create('Foss.baseinfo.regionalVehicleIndex.SmallZoneEntityModel')});//查询结果显示列表
	Ext.getCmp('T_baseinfo-regionalVehicleIndex').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-regionalVehicleIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout:'column',
		defaults:{
	        xtype: 'container',
	        margins: '5 0 0 5'
		},
	    items: [{
	        width: 250,
	        items : [{
	        	/**
	        	 * ASP项目：leo-zeng 做的优化
	        	 */
		        xtype: 'label',
		        columnWidth:0.3,
		        text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.areaNameNum')
	        },{
		        xtype: 'textfield',
		        columnWidth:0.5,
		        name:'name'
	        },{
		        xtype: 'container',
				layout:'column',
		        items : [{
			        xtype: 'combobox',
			        fieldLabel:'区域类型',
			        displayField:'valueName',
					valueField:'valueCode',
					queryMode:'local',
					triggerAction:'all',
					editable:false,
					name: 'regionType',					
			        width:190,
			        value:'',
					store:FossDataDictionary.getDataDictionaryStore('BSE_REGION_TYPE', null, {
						'valueCode': '',
			            'valueName': baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.announcement.all')
					})					
		        },{
			        xtype: 'button',
			        width:50,
			        height:25,
			        text:baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.query'),
			        disabled:!baseinfo.regionalVehicleIndex.isPermission('queryPickupAndDeliveryZone/pickupAndDeliveryZoneQueryButton'),
			        hidden:!baseinfo.regionalVehicleIndex.isPermission('queryPickupAndDeliveryZone/pickupAndDeliveryZoneQueryButton'),
					cls:'yellow_button',
					plugins: {
				        ptype: 'buttondisabledplugin',
				        seconds: 3
				    },
					handler : function(btn) {
						var name = btn.up('container').up().down('textfield').getValue();
						var regionType =btn.up('container').down('combobox').getValue()
						if(Ext.isEmpty(name)&&Ext.isEmpty(regionType)){
							queryTree.store.load({params:{
						        'objectVo.codeStr':baseinfo.regionalVehicleIndex.rootParentId
						    }});
						}else if(!Ext.isEmpty(name)){
							//若是区域名称有值的话，默认优先查询
							regionType ='';
							baseinfo.requestAjaxJson(baseinfo.realPath('queryZoneByNameOrNum.action'),{'objectVo':{'codeStr':name}},
									function(result){
										var nodes = result.nodes,path = result.objectVo.codeStr;
										if(Ext.isEmpty(path)){
											queryTree.store.load({params:{
										        'objectVo.codeStr':baseinfo.regionalVehicleIndex.rootParentId,
										        'objectVo.regionType':regionType
										    }});
										}else{
											queryTree.selectPath(path,'id','.');
										}
									},function(result){
								baseinfo.showInfoMsg(result.message);
							},false);
						}else{
							queryTree.store.load({params:{
						        'objectVo.codeStr':baseinfo.regionalVehicleIndex.rootParentId,
						        'objectVo.regionType':regionType
						    }})
						}
					}
		        }]
	        },queryTree]
	    },{
	        columnWidth: 0.99,
	        items : [ queryForm,{
		        xtype: 'container',
		        layout:'card',
		        activeItem: 0,
		        items:[queryGrid,queryDetailBigZone,queryDetailSmallZone]
	        }]
	    }], 
		//获得查询TREE
		getQueryTree : function() {
			return queryTree;
		},
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryGrid : function() {
			return queryGrid;
		},
		//获得查看详情form 大区
		getQueryDetailBigZone:function(){
			return queryDetailBigZone;
		},
		//获得查看详情form 小区
		getQueryDetailSmallZone:function(){
			return queryDetailSmallZone;
		}
	}));
});
//------------------------------------WINDOW--------------------------------
//定人定区界面win
Ext.define('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.regionalVehicle.addRegionalVehicle'),									//新增定人定区公司   默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :370,
	height :370,	
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
		}
	},
	viewState:baseinfo.viewState.add,						//查看状态,默认为新增
	editForm:null,											//定人定区公司表单Form
	sourceGrid:null,										//定人定区公司表格Grid
	formRecord:null,										//定人定区公司实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//定人定区公司 网点信息数组  [Foss.baseinfo.OuterBranchModel]
	regionNature:baseinfo.regionalVehicleIndex.regionNature.bsq,	//标识 弹出窗体界面是 大区小区还是大小区 定车定区(默认大小区兼共)
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.regionalVehicleIndex.RegionVehicleEntityWinForm',{'regionNature':config.regionNature,'viewState':config.viewState,'formRecord':config.formRecord});
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
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.cancel'),
			handler :function(){
				me.hide();
			}
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.reset'),
			disabled:baseinfo.viewState.view === config.viewState,
			handler :function(){
				if(baseinfo.viewState.add === config.viewState 
					&&baseinfo.regionalVehicleIndex.regionNature.bsq != config.regionNature){
					//大小区 点击新增后 重置 只是把 车牌号和车队所属车队清空
					me.down('form').getForm().findField('vehicleNo').setValue(null);
					me.down('form').getForm().findField('vehicleDept').setValue(null);
				}else{
					// 重置
					baseinfo.regionalVehicleIndex.initRegionalWin(me,me.formRecord,config.regionNature);
				}
			} 
		},{
			text : baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.save'),
			disabled:baseinfo.viewState.view === config.viewState,
			handler :function(){
		    	var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断定人定区必填项是否填写并全部填写合法
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.regionalVehicleIndex.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		baseinfo.regionalVehicleIndex.submitRegionVehicleEntity(me,me.viewState,me.formRecord.data,config.sourceGrid,config.regionNature);
			}
		}];
	}
});

