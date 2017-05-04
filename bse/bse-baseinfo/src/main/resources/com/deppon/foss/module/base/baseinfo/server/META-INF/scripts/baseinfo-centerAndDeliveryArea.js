baseinfo.centerAndDeliveryArea.ployfeature==null;
var full=true;

//地图保存方法
baseinfo.centerAndDeliveryArea.operateMethod = function(ManageMent,manageMentName,GISID,GISAREA,TYPE){
	var url;
	var centerAndDeliveryAreaVo={};
	var centerAndDeliveryAreaEntity=new Object();
	centerAndDeliveryAreaEntity.manageMentCode=ManageMent;
	centerAndDeliveryAreaEntity.manageMentName=manageMentName;
	centerAndDeliveryAreaEntity.gisId=GISID;
	centerAndDeliveryAreaEntity.gisArea=GISAREA;
	centerAndDeliveryAreaVo.centerAndDeliveryAreaEntity=centerAndDeliveryAreaEntity;
	if(TYPE==='ADD'){
		url=baseinfo.realPath('addVehicleInfo.action');
	}else if(TYPE==='UPDATE'){
		url=baseinfo.realPath('updateVehicleInfo.action');
	}else{
		url=baseinfo.realPath('deleteVehicleInfo.action');
	}
	/**
	 * 
	 */
	baseinfo.requestAjaxJson(url,{'centerAndDeliveryAreaVo':centerAndDeliveryAreaVo},function(result){
		
		baseinfo.showInfoMsg(result.message);
		
	},function(result){
		var obj=result.centerAndDeliveryAreaVo;
		if(!Ext.isEmpty(obj.centerAndDeliveryAreaEntity.gisId)){
			if('delete'===obj.status||'add'===obj.status){
//				DpMap.base.deletePolygonByCode(obj.centerAndDeliveryAreaEntity.gisId);
				baseinfo.centerAndDeliveryArea.ployfeature.deletePolygonByCode(obj.centerAndDeliveryAreaEntity.gisId);
				baseinfo.showInfoMsg("请对你所要圈画的车队信息进行查询！");
				return;
			}
		}
		baseinfo.showInfoMsg(result.message);
	});
}





//地图panel baseinfo-centerAndDeliveryArea.js
Ext.define('Foss.baseinfo.centerAndDeliveryArea.QueryResultMapContianer', {
	extend: 'Ext.panel.Panel',
	title :'地图显示列表',
	//cls: 'autoHeight',
	//bodyCls: 'autoHeight', 
	frame: true,
	height :670,
	tbar: [{ type: 'button', 
			 text: '显示全屏' ,
			 handler:function(){
			 if(full){
				  this.setText('退出全屏');
			      var _form=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryForm().setVisible(false);
				  var gridHeight=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryResultMapContianer().getHeight();
				  Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryResultMapContianer().setHeight(180+gridHeight);
				  full=false;
			   }else{
			      this.setText('显示全屏');
				  full=true;
			      var _form=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryForm().setVisible(true);
				  var gridHeight=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryResultMapContianer().getHeight();
				  Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryResultMapContianer().setHeight(gridHeight-180);
				  
			   }
			 }}
		],
	constructor : function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items=[{
							xtype: 'container',
							height:700,
							width:1000,
							listeners:{
							   //显示之前的事件
								  beforerender:function(panel){
								  Ext.defer(function(){
									   var mapLocation='上海';
									   var centerAndDeliveryAreaMap = new DpMap(panel.getId(), {center :mapLocation,zoom : 13}, function (map) { 
										   var fun = function(data){
											   console.log(data);
											   var manageMent=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryForm().getForm().findField('manageMent').getValue();
											   var manageMentName=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryForm().getForm().findField('manageMent').rawValue;
											   /*if(Ext.isEmpty(manageMent)){
												   Ext.Msg.alert(baseinfo.centerAndDeliveryArea.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'请查询之后在画地图！');
												   return;
											   }*/
											   if('ADD' === data.type){
												   var gisid=data.code;
												   var gisArea=data.area;
												   baseinfo.centerAndDeliveryArea.operateMethod(manageMent,manageMentName,gisid,gisArea,data.type);
												   baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool(); 
											   }else if('DELETE' === data.type){
												   baseinfo.centerAndDeliveryArea.operateMethod(manageMent,manageMentName,null,null,data.type);
												   baseinfo.centerAndDeliveryArea.ployfeature.openEditTool();
												 }else if('UPDATE' ===data.type){
												   var gisid=data.code;
												   var gisArea=data.area;
												   baseinfo.centerAndDeliveryArea.operateMethod(manageMent,manageMentName,gisid,gisArea,data.type);
												 }
										   }
										   var callFun =function(data){
				    							  if(data.type ='QUERY'){
				    								  baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool();
				    							  }
				    						  }
			
				    		baseinfo.centerAndDeliveryArea.ployfeature = new DpMap.service.DpMap_polygon(map,panel.getId(),
				    				{isAddable:true, callBackFun:fun, foregroundType:'FLEET_REGIONS', backgroundType:'COUNTY',closeToolCallback:callFun});
				    		baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool();
									 });
								   },1000,this);
							   }
						   }}]
						
		me.callParent([cfg]);
	}
});
//查询FORM
Ext.define('Foss.baseinfo.centerAndDeliveryArea.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.centerAndDeliveryArea.i18n('foss.baseinfo.queryCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130,
    	width:280
    },
    height :180,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
	constructor : function(config) {							 
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			xtype:'commonmotorcadeselector',
			fieldLabel:'顶级车队',									 
			name:'manageMent',
			isFullFleetOrgFlag:'Y',
			width:300
		},
		{
			xtype:'commontruckselector',
			fieldLabel:'车牌号',									 
			name:'vehicleNo',
			width:300
		},
		{
			labelWidth : 130,
			xtype:'commonbigzoneselector',
			fieldLabel:'集中接送货大区',								 
			name: 'bigRegionCode',
			width:300
		},{
			labelWidth : 130,
			xtype:'commonsmallzoneselector',
			fieldLabel:'集中接送货小区',								 
			name: 'smallRegionCode',
			width:300
		},{
			fieldLabel:'gisId',
			name:'gisid',
			hidden:true
		},{
			xtype : 'container',colspan:3,
			defaultType:'button',
			layout:{
				type: 'table',
				columns: 4
			},
			items : [{
				width: 75,
				text : baseinfo.centerAndDeliveryArea.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.centerAndDeliveryArea.isPermission('queryCenterAndDeliveryByEntity/centerAndDeliveryQueryButton'),
				hidden:!baseinfo.centerAndDeliveryArea.isPermission('queryCenterAndDeliveryByEntity/centerAndDeliveryQueryButton'),
				handler : function() {
					this.up('form').getForm().reset();
				}
			},{
				xtype:'container',
				html:'&nbsp;',
				width:460 
			},
			/*{
				xtype:'button',
				width: 75,
//				hidden:!baseinfo.centerAndDeliveryArea.isPermission('queryPickupAndDeliverySmallZoneExactByEntity/pickupAndDeliverySmallZoneQueryButton'),
				text : '全屏',
				cls:'yellow_button',
				handler : function() {
				  var _form=Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content').getQueryForm().setVisible(false); 
				}
			},*/
			{
				xtype:'button',
				width: 75,
				disabled:!baseinfo.centerAndDeliveryArea.isPermission('queryCenterAndDeliveryByEntity/centerAndDeliveryQueryButton'),
				hidden:!baseinfo.centerAndDeliveryArea.isPermission('queryCenterAndDeliveryByEntity/centerAndDeliveryQueryButton'),
				text : baseinfo.centerAndDeliveryArea.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				handler : function() {
					var queryForm=this.up().up().getForm();
					var vehicleDept=queryForm.findField('manageMent').getValue();
					if(vehicleDept==null){
						baseinfo.showInfoMsg("车队不能为空必须输入！");
						return;
					}
					 var centerAndDeliveryAreaVo={};
					 centerAndDeliveryAreaVo.centerAndDeliveryAreaDto=queryForm.getValues();
					 var url= baseinfo.realPath('queryVehicleInfo.action');
					
					baseinfo.requestAjaxJson(url,{'centerAndDeliveryAreaVo':centerAndDeliveryAreaVo},function(result){
//						console.log(result);rolePermissions  drawMap
						var obj=result.centerAndDeliveryAreaVo.centerAndDeliveryAreaEntity;
						if(obj.bigRegions.length==0){
							baseinfo.showInfoMsg("该车队下没有大区！");
							return;
						}
						
						if(obj.drawMap=='N'){
							baseinfo.centerAndDeliveryArea.ployfeature.clearPolygons();
							baseinfo.centerAndDeliveryArea.ployfeature.queryFleet(obj,false);
							baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool(); 
						}else{
							baseinfo.centerAndDeliveryArea.ployfeature.clearPolygons();
							baseinfo.centerAndDeliveryArea.ployfeature.queryFleet(obj,true);
							baseinfo.centerAndDeliveryArea.ployfeature.openEditTool();
						}
					},function(result){
						baseinfo.showInfoMsg(result.message);
					});
				}
			}]
		}];
	}
});
//------------------------------------ONREADY----------------------------------
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-centerAndDeliveryAreaIndex_content')){
		return;
	}
	
	var queryForm  = Ext.create('Foss.baseinfo.centerAndDeliveryArea.QueryConditionForm');//查询FORM
	var QueryResultMapContianer  = Ext.create('Foss.baseinfo.centerAndDeliveryArea.QueryResultMapContianer');// 
		Ext.getCmp('T_baseinfo-centerAndDeliveryArea').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-centerAndDeliveryAreaIndex_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getQueryResultMapContianer : function() {
			return QueryResultMapContianer;
		},
		items : [ queryForm, QueryResultMapContianer] 
	}));
		
		/*url=baseinfo.realPath('queryRolePermissions.action');
		baseinfo.requestAjaxJson(url,function(result){
//			baseinfo.showInfoMsg(result.message);
			if(null!=result.centerAndDeliveryAreaVo){
				var obj=result.centerAndDeliveryAreaVo.centerAndDeliveryAreaEntity;
				if(null!=obj){
					
					if(!Ext.isEmpty(obj.drawMap)&&obj.drawMap=='Y'&&!Ext.isEmpty(obj.rolePermissions)&&obj.rolePermissions==='Y'){
						baseinfo.centerAndDeliveryArea.ployfeature.clearPolygons();
						baseinfo.centerAndDeliveryArea.ployfeature.queryFleet(obj);
						return;
					}
					if(!Ext.isEmpty(obj.rolePermissions)&&obj.rolePermissions==='Y'){
//						baseinfo.centerAndDeliveryArea.ployfeature.showUnModifiablePolygons(obj);
						baseinfo.centerAndDeliveryArea.ployfeature.clearPolygons();
						baseinfo.centerAndDeliveryArea.ployfeature.queryFleet(obj);
						if(!Ext.isEmpty(obj.gisId)){
							baseinfo.centerAndDeliveryArea.ployfeature.showUnModifiablePolygons(obj.gisId);
						}
						if(Ext.isEmpty(obj.drawMap)){
							   baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool();
						}
					}
					if(!Ext.isEmpty(obj.drawMap)&&obj.drawMap==='N'){
						baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool();
					}
					
				}else{
					
					baseinfo.centerAndDeliveryArea.ployfeature.closeEditTool();
				}
			}
			
		},function(result){
			baseinfo.showInfoMsg(result.message);
		});*/
		
		
});
