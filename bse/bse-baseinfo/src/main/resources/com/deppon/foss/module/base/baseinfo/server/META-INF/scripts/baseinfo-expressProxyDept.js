/**
 * 快递代理网点编辑
 */
baseinfo.expressProxyDept.ployfeature==null;//GIS地图对象初始化
//地图保存方法
baseinfo.expressProxyDept.operateMethod = function(code,gisid,deliveryNature,type){
	var url;
	var vo={};
	var expressDeliveryRegionsEntity=new Object();
	expressDeliveryRegionsEntity.code=code;
	expressDeliveryRegionsEntity.expressProxyCoordinate=gisid;
	expressDeliveryRegionsEntity.expressProxyDeliveryNature=deliveryNature;
	vo.expressDeliveryRegionsEntity=expressDeliveryRegionsEntity;
	if(type==='ADD' || type==='UPDATE' ||type==='DELETE' ){
		url=baseinfo.realPath('updateExpressDeliveryRegionByGisMap.action');
	}
	/**
	 * AJAX请求
	 */
	baseinfo.requestAjaxJson(url,{'vo':vo},function(result){
		baseinfo.showInfoMsg(result.message);
	},function(result){
		baseinfo.showInfoMsg(result.message);
	});
};
//快递派送区域树Store
Ext.define('Foss.baseinfo.expressProxyDept.ExpressDeliveryTreeStore',{
	extend:'Ext.data.TreeStore',
	root:{
		text:baseinfo.expressProxyDept.i18n('foss.baseinfo.expressDeliveryRegions.regions'),//快递派送行政区域
		id:'01',//根节点ID
		expand:true//根节点是否展开
	},
	proxy:{
		type:'ajax',
		actionMethods:'POST',
		url:baseinfo.realPath('queryExpressDeliveryRegionsByParentDistrictCode.action'),
		reader:{
			type:'json',
			root:'nodes',
		}
	},
	nodeParam:'vo.expressDeliveryRegionsEntity.parentDistrictCode',
	
});
//树节点操作
baseinfo.expressProxyDept.treeNodeOperator=function(me,a_code){
	// 如果是根结点，点击时不响应
	if(a_code =='01'){
		return ;
	}
	var params = {
			"vo.expressDeliveryRegionsEntity.code":a_code 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('querySelfAndChildNodesByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var a_entityDetail = json.vo.expressDeliveryRegionsEntity;//自身节点
			var entityList=json.vo.expressDeliveryRegionsList;//下级节点
			if(a_entityDetail.degree!='TOWN_STREET_AGENCY'){//只有点击镇街道级别才可以对地图进行编辑
				baseinfo.expressProxyDept.ployfeature.closeEditTool();//不是镇级别则无工具栏
			}
			var detailPanel=Ext.getCmp('T_baseinfo-expressProxyDept_mapShowTopFrom');
			var mapShowPanel=Ext.getCmp('T_baseinfo-expressProxyDept_mapShowContent').getMapShowBottomPanel();
//			detailPanel.expressDeliveryRegionEntity=a_entityDetail;
			detailPanel.getForm().findField('expressSalesDeptName').setValue(a_entityDetail.expressSalesDeptName);
			detailPanel.getForm().findField('deliveryNatureName').setValue(a_entityDetail.deliveryNatureName);
			mapShowPanel.expressDeliveryRegionEntity=a_entityDetail;
			mapShowPanel.expressDeliveryRegionsList=entityList;
			if(a_entityDetail!=null){
				if(a_entityDetail.degree=='TOWN_STREET_AGENCY'){
					var query =new Object();
					query.id =a_entityDetail.expressProxyCoordinate;
					query.nature =a_entityDetail.expressProxyDeliveryNature;
					query.county =a_entityDetail.parentDistrictName;
					query.town =a_entityDetail.name;
					query.address =a_entityDetail.districtAddress;
					query.level=13;
					baseinfo.expressProxyDept.ployfeature.clearMarkers();
					baseinfo.expressProxyDept.ployfeature.showModifiablePointByQueryData(query);//查看单个可编辑
					baseinfo.expressProxyDept.ployfeature.openEditTool();//添加工具栏
				}
				
			    if(a_entityDetail.degree=='DISTRICT_COUNTY'
			    	||a_entityDetail.degree=='CITY'){									      
			    	//查看多个不可编辑网点（适用于查看一个市、区县下的所有快递代理网点）
			    	//自定义一个市、区县的快递代理 	   
			        var idList = [];
			        for(var i=0;i<entityList.length;i++){
			        	var query = new Object(); 
			        	query.id=entityList[i].expressProxyCoordinate;
			        	query.nature=entityList[i].expressProxyDeliveryNature;
			        	if(query.id!=null && query.id!=''){//非空判断
			        		idList.push(query);
			        	}
			        }
			        
			        if(a_entityDetail.degree=='DISTRICT_COUNTY'){//区县
			        	var orgnize = new Object(); 
			        	orgnize.city=a_entityDetail.parentDistrictName;
			        	orgnize.county =a_entityDetail.name;
			        	orgnize.town = '';
			        	orgnize.addrss=a_entityDetail.parentDistrictName+a_entityDetail.name;
			        	orgnize.level =11;
			        	baseinfo.expressProxyDept.ployfeature.clearMarkers();
				        //对快递代理网点做判空处理
						if(idList.length == 0){
							baseinfo.expressProxyDept.ployfeature.showCenter(orgnize);//只有区县没有快递代理网点，才需要调整视野
						}
				    	baseinfo.expressProxyDept.ployfeature.showUnModifiablePointsByQueryData(idList);//查看多个不可编辑网点
			        }else{//市
			        	var orgnize = new Object(); 
			        	orgnize.city=a_entityDetail.name;
			        	orgnize.county ='';
			        	orgnize.town = '';
			        	orgnize.addrss=a_entityDetail.name;
			        	orgnize.level=9;
			        	//点击市只需调整视野，不需要显示快递代理网点，所以只调一个方法showCenter
			        	baseinfo.expressProxyDept.ployfeature.showCenter(orgnize);
			        }
			    }
			}
//			var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
//			var expressDeliveryRegionsRightPanel = expressDeliveryRegionsindex.getRegionsRightPannel();
//			var expressDeliveryRegionsRightQueryResultPannel = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsQueryResultPanel();
//			
//			var detailForm = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsDetailForm(); 
//			detailForm.setVisible(true);
//			expressDeliveryRegionsRightQueryResultPannel.setVisible(false);
//			var districtModel = new Foss.baseinfo.expressDeliveryRegions.DistrictModel(a_entityDetail);
//			//把值设置给详情表单中的regionsModel
//			detailForm.regionsModel = districtModel;
//			detailForm.getForm().loadRecord(districtModel);
		}
	});
};

//快递派送区域树形面板Panel
Ext.define('Foss.baseinfo.expressProxyDept.ExpressDeliveryTree',{
	extend:'Ext.tree.Panel',
	title:baseinfo.expressProxyDept.i18n('foss.baseinfo.expressDeliveryRegions.regions'),
	height:650,
	margin:false,
	autoScroll:true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	animate:false,
	useArrows:true,
	frame:false,
	rootVisible:true,
	columnWidth:1,
	store:Ext.create('Foss.baseinfo.expressProxyDept.ExpressDeliveryTreeStore'),//加载Store
	defaults:{
		margin:'5 5 0 5'
	},
	constructor:function(config){
		var me=this;
		cfg=Ext.apply({},config);
		//监听鼠标事件
		me.listeners={
			//左键点击时间
			itemclick:function(node,record,item,index,e){
				//阻止浏览器默认行为
				e.preventDefault();
				var a_code=record.data.id;
				baseinfo.expressProxyDept.treeNodeOperator(me,a_code);
			},
			//右键单击
		      itemcontextmenu:function(node,record,item,index,e){
		        e.preventDefault();
		      }
		};
		me.callParent([cfg]);
	}
});

//左侧快递派送区域树形封装面板Panel
Ext.define('Foss.baseinfo.expressProxyDept.ExpressDeliveryPanel',{
	extend:'Ext.panel.Panel',
	height:680,
	frame:true,
	layout:'column',
	columnWidth:0.25,
	defaultType:'textfield',
	//快递派送区域树组织
	expressDeliveryRegionTree:null,
	getExpressDeliveryRegionTree:function(){
		var me=this;
		if(Ext.isEmpty(me.expressDeliveryRegionTree)){
			me.expressDeliveryRegionTree=Ext.create('Foss.baseinfo.expressProxyDept.ExpressDeliveryTree');
		}
		return me.expressDeliveryRegionTree;
	},
	constructor:function(config){
		var me =this;
		cfg=Ext.apply({},config);
		me.items=[me.getExpressDeliveryRegionTree()];
		me.callParent([cfg]);
	}
	
});
//详情Window(作废)
//Ext.define('Foss.baseinfo.expressProxyDept.MapShowTopPanel',{
//	extend:'Ext.panel.Panel',
//	height:80,
//	columnWidth:0.72,
//	frame:true,
//	//详情Form
//	mapShowTopForm:null,
//	getMapShowTopForm:function(){
//		if(Ext.isEmpty(this.mapShowTopForm)){
//			this.mapShowTopForm=Ext.create('Foss.baseinfo.expressProxyDept.MapShowTopFrom');
//		}
//		return this.mapShowTopForm;
//	},
//	constructor:function(config){
//		var me=this;
//		cfg=Ext.apply({},config);
//		me.items=[me.getMapShowTopForm()];
//		me.callParent([cfg]);
//	}
//});
//详情Form
Ext.define('Foss.baseinfo.expressProxyDept.MapShowTopFrom',{
	extend:'Ext.form.Panel',
	id:'T_baseinfo-expressProxyDept_mapShowTopFrom',
	height:70,
	width:700,
	frame:false,
	layout:'column',
	expressDeliveryRegionEntity:null,
	defaults: {
		readOnly : true,
		margin:'5 5 5 5',
		anchor: '90%',
//		columnWidth: 1,
		labelWidth: 120
	},
//	listeners:{
//		beforeshow:function(me){//Form加载值
//			me.getForm().findField('expressSalesDeptName').setValue(me.expressDeliveryRegionEntity.expressSalesDeptName);
//			me.getForm().findField('deliveryNature').setValue(me.expressDeliveryRegionEntity.deliveryNature);
//		}
//	},
	constructor:function(config){
		var me=this;
		cfg=Ext.apply({},config);
		me.items=[{
			xtype:'textfield',
			fieldLabel:baseinfo.expressProxyDept.i18n('foss.baseinfo.expressDeliveryRegions.expressSalesDept'),
			columnWidth:0.5,
			name:'expressSalesDeptName'//所属快递虚拟营业部
		},{
			xtype:'textfield',
			fieldLabel:baseinfo.expressProxyDept.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'),
			columnWidth:0.4,
			name:'deliveryNatureName'//派送属性
		}];
		me.callParent([cfg]);
	}
});
//GIS电子地图窗口Window
Ext.define('Foss.baseinfo.expressProxyDept.MainGisPanel',{
	extend:'Ext.panel.Panel',
	closeAction:'hide',
	width:700,
	height:610,
//	collBackFun:function(){},//回调函数
	expressDeliveryRegionEntity : null, //查看实体数据
	expressDeliveryRegionsList:null,//查看List数据
//	parent:null,//父窗体
	constructor : function(config) {
		var me = this; 
		cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			height:600,
			width:700,
			listeners:{
				  //显示之前的事件
				  beforerender:function(panel){
				  Ext.defer(function(){
					   var mapLocation=baseinfo.expressProxyDept.i18n('foss.baseinfo.shanghaishi');
					   if(me.expressDeliveryRegionEntity!=null){
						   if(!Ext.isEmpty(me.expressDeliveryRegionEntity.name)){
							   mapLocation=me.expressDeliveryRegionEntity.name;
							  }
					   }
					   var expressProxyDeptMap = new DpMap(panel.getId(), {center :mapLocation,zoom : 13}, function (map) { 
						   var fun = function(data){
//								   console.log(data);
							   var code=me.expressDeliveryRegionEntity.code;
							   if('ADD' === data.flag){
								   var gisid=data.code;
								   var expressProxyDeliveryNature=data.nature;
								   baseinfo.expressProxyDept.operateMethod(code,gisid,expressProxyDeliveryNature,data.flag);
								   baseinfo.expressProxyDept.ployfeature.closeEditTool(); 
							   }else if('DELETE' === data.flag){
								   baseinfo.expressProxyDept.operateMethod(code,null,null,data.flag);
								   baseinfo.expressProxyDept.ployfeature.openEditTool();
								 }else if('UPDATE' ===data.flag){
								   var gisid=data.code;
								   var expressProxyDeliveryNature=data.nature;
								   baseinfo.expressProxyDept.operateMethod(code,gisid,expressProxyDeliveryNature,data.flag);
								 }
						   }
						   var callFun =function(data){
    							  if(data.flag ='QUERY'){
    								  baseinfo.expressProxyDept.ployfeature.closeEditTool();
    							  }
						   }

	    		//实例化一个新类
				baseinfo.expressProxyDept.ployfeature = new DpMap.service.DpMap_marker(map, 
						panel.getId(),{isAddable:true, 
							saveType:'EXPRESS_ADAPTE3',closeToolCallback:callFun,callBackFun: fun});
				baseinfo.expressProxyDept.ployfeature.closeEditTool();
						 });
					   },1000,this);
				   }
			}
		}];
		me.callParent([cfg]);
	}
});
//右侧快递派送区域详情和地图窗体的Window
Ext.define('Foss.baseinfo.expressProxyDept.MapShowPanel',{
	extend:'Ext.panel.Panel',
	id:'T_baseinfo-expressProxyDept_mapShowContent',
	height:680,
	frame:true,
	layout:'column',
	columnWidth:0.75,
	defaults: {
		readOnly : true,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
	//详情面板
	mapShowTopPanel:null,
	getMapShowTopPanel:function(){
		if(Ext.isEmpty(this.mapShowTopPanel)){
			this.mapShowTopPanel=Ext.create('Foss.baseinfo.expressProxyDept.MapShowTopFrom');
		}
		return this.mapShowTopPanel;
	},
	//地图面板
	mapShowBottomPanel:null,
	getMapShowBottomPanel:function(){
		if(Ext.isEmpty(this.mapShowBottomPanel)){
			this.mapShowBottomPanel=Ext.create('Foss.baseinfo.expressProxyDept.MainGisPanel');
		}
		return this.mapShowBottomPanel;
	},
	constructor:function(config){
		var me=this;
		cfg=Ext.apply({},config);
		me.items=[me.getMapShowTopPanel(me),me.getMapShowBottomPanel(me)];
		me.callParent([cfg]);
	}
});
//初始加载
Ext.onReady(function(){
	//初始化
	Ext.QuickTips.init();
	if(Ext.getCmp('T_baseinfo-expressProxyDept_content')){
		return;
	}
	 var expressDeliveryPanel=Ext.create('Foss.baseinfo.expressProxyDept.ExpressDeliveryPanel');
	 var mapShowPanel=Ext.create('Foss.baseinfo.expressProxyDept.MapShowPanel');
	 
	 Ext.getCmp('T_baseinfo-expressProxyDept').add(Ext.create('Ext.panel.Panel'),{
		 id:'T_baseinfo-expressProxyDept_content',
		 layout:'column',
		 bodyCls:'panelContentNToolbar-body',
		 cls:'panelContentNToolbar',
		 getExpressDeliveryPanel:function(){
			 return expressDeliveryPanel;
		 },
		 getMapShowPanel:function(){
			 return mapShowPanel;
		 },
		 items:[expressDeliveryPanel,,mapShowPanel]
	 });
});