baseinfo.expressDeliverySmallZone.ployfeature = null;					//地图服务
baseinfo.expressDeliverySmallZone.ployfeature1 = null;
baseinfo.expressDeliverySmallZone.region_type_map = {exs:'EXPRESSPKDE_REGIONS'}
var isFullScreen=false;
var windowObject=null;
//信息
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:baseinfo.expressDeliverySmallZone.i18n('i18n.baseinfo-util.fossAlert'),
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
//根据小区名称查询
baseinfo.expressDeliverySmallZone.querySmallByRegionName = function(obj){
	var status=obj.up('window').viewState;
	if(""==obj.getValue()||'UPDATE'==status){
		return;
	}
	var objectVo = {},expressDeliverySmallZoneEntity = {};
	expressDeliverySmallZoneEntity.regionName = obj.getValue();
	objectVo.expressDeliverySmallZoneEntity = expressDeliverySmallZoneEntity;
	if(!Ext.isEmpty(obj.getValue())){
		//自动生成 区域编码
		baseinfo.requestAjaxJson(baseinfo.realPath('querySmallZoneByName.action'), {
			'objectVo' : objectVo
		}, function(result) {
			
		}, function(result) {
			obj.setValue(null);
			baseinfo.showInfoMsg(result.message);
		});
	}
};

baseinfo.expressDeliverySmallZone.autoCreateBigZoneNum = function(management,form){
	form.getForm().findField('regionCode').reset();
	var objectVo = {},expressDeliverySmallZoneEntity = {};
//	expressDeliverySmallZoneEntity.type = type;
	expressDeliverySmallZoneEntity.management = management;
	objectVo.expressDeliverySmallZoneEntity = expressDeliverySmallZoneEntity;
	if(!Ext.isEmpty(management)){
		//自动生成 区域编码
		baseinfo.requestAjaxJson(baseinfo.realPath('queryRegionCodeByManagement.action'), {
			'objectVo' : objectVo
		}, function(result) {
			//自动生成编码
			form.getForm().findField('regionCode').setValue(result.objectVo.regionCode);
		}, function(result) {});
	}
};


//通过查询条件导出数据
baseinfo.expressDeliverySmallZone.exportExpressDeliverySmallZone = function(queryForm){
	var queryForm = queryForm.getForm();//得到查询的FORM表单
	queryForm.updateRecord(queryForm.record);
    if (queryForm != null) {
		Ext.MessageBox.buttonText.yes =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.leasedVan.cancel');
		if(!Ext.fly('expressDeliverySmallZone')){
			    var frm = document.createElement('form');
			    frm.id = 'expressDeliverySmallZone';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		
		Ext.Msg.confirm(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.tipInfo'),baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.exportMsg'), function(btn,text){
			if(btn == 'yes'){
				var params ={
					//接送货小区编码
					'objectVo.expressDeliverySmallZoneEntity.regionCode':queryForm.record.data.regionCode,
					//接送货小区名称
					'objectVo.expressDeliverySmallZoneEntity.regionName':queryForm.record.data.regionName,
					//上级部门名称
					'objectVo.expressDeliverySmallZoneEntity.management':queryForm.record.data.management
				}
		
				Ext.Ajax.request({
					url:baseinfo.realPath('exportExpressDeliverySmallZoneList.action'),
					form: Ext.fly('expressDeliverySmallZone'),
					params:params,
					method:'post',
					isUpload: true,
					success:function(response){
						var result = Ext.decode(response.responseText);
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						top.Ext.MessageBox.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.exportFailed'),result.message);
					}
				});
			}
		});
	}
};
//作废事件
baseinfo.expressDeliverySmallZone.deleteExpressDeliverySmallZoneEntityByCode = function(delAgencyCompanyType,operatRecord){
	var grid = Ext.getCmp('T_baseinfo-expressDeliverySmallZone_content').getQueryGrid(),
		url = baseinfo.realPath('deleteExpressDeliverySmallZone.action'),
		objectVo = {};
	selection=grid.getSelectionModel().getSelection();
	if(selection.length<=0 && Ext.isEmpty(operatRecord)){
		Ext.MessageBox.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.remind'),baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.selectData'));
	}else{	
		//定义地图范围ＩＤ数组
		var gisidArray = [];
		if(!Ext.isEmpty(delAgencyCompanyType)&&baseinfo.delAgencyType===delAgencyCompanyType){
			var codeStr = '';
			//批量作废
			url = baseinfo.realPath('deleteExpressDeliverySmallZone.action');
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
		Ext.MessageBox.buttonText.yes =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.confirmAlertRecord'),function(btn,text) {
			if (btn == 'yes') {
				baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
					grid.store.loadPage(1);
					var dMap = new DpMap('gisMap',{center : '上海市', zoom : 13 },function(map) {
						var polyFeature = new DpMap.service.DpMap_polygon(map,'gisMap',{isAddable:true,callBackFun:function(data){}});
						//删除小区成功后删除小区在GIS上面的范围
						for(var i= 0;i < gisidArray.length; i++){
							//根据小区gisid删除小区地图范围
							polyFeature.deletePolygonByCode(gisidArray[i]);
						}
						document.getElementById('mapDiv').parentNode.removeChild(document.getElementById('mapDiv'))
						document.getElementById('toolbar').parentNode.removeChild(document.getElementById('toolbar'));
					});
					baseinfo.showInfoMsg(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.deleteSuccess'));
					
				},function(result){
					baseinfo.showInfoMsg(result.message);
				});
			}
		});
	}
};
//保存事件 
baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity = function(win,viewState,operatEntity,obj){
	var grid = Ext.getCmp('T_baseinfo-expressDeliverySmallZone_content').getQueryGrid()
		,url = baseinfo.realPath('addExpressDeliverySmallZone.action')
		,m_success =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.saveSuccessful')							//保存成功！
		,m_failure =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.saveFail')								//保存失败！
		,m_dateError =baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.dataError')								//数据异常！
		,objectVo = {};
	objectVo.expressDeliverySmallZoneEntity = operatEntity;
	if(Ext.isEmpty(operatEntity.porters)){
		operatEntity.porters = [];
	}
	if('ADD' === viewState){
		//新增URL(已经有)
		//若地图还没有绘制，不允许新增
		/*if(Ext.isEmpty(objectVo.expressDeliverySmallZoneEntity.gisid)){
			Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'请绘制完地图，再保存');
			return;
		}*/
	}else if('UPDATE' === viewState){
		 
		//修改URL
		
		url = baseinfo.realPath('updateExpressDeliverySmallZone.action');
	}
	baseinfo.requestAjaxJson(url,{'objectVo':objectVo},function(result){
		if(!Ext.isEmpty(result.objectVo.expressDeliverySmallZoneEntity)){
			grid.store.loadPage(1);
			baseinfo.showInfoMsg(m_success);
			if(obj==null){
			   win.hide();
			}
		}else{
			baseinfo.showInfoMsg(result.message);
		}
	},function(result){
		/*var _objForm= win.items.items[0].getForm();
		var gidId=_objForm.findField('gisid').getValue();
		var bigReionCode = _objForm.findField('bigzonecode').getValue();
		if(_objForm.viewState=="UPDATE"&&!Ext.isEmpty(gidId)&&!Ext.isEmpty(bigReionCode)){
		 var dMap = new DpMap('gisMap',{center : '上海市', zoom : 13 },function(map) {
				 var polyFeature = new DpMap.service.DpMap_polygon(map,'gisMap',{isAddable:true,callBackFun:function(data){}});
					 polyFeature.deletePolygonByCode(gidId);
					document.getElementById('mapDiv').parentNode.removeChild(document.getElementById('mapDiv'))
					document.getElementById('toolbar').parentNode.removeChild(document.getElementById('toolbar'));
			 });
			  baseinfo.showInfoMsg(result.message);
		}*/
	
		 baseinfo.showInfoMsg(result.message); 
	});
};


//弹出界面上 数据渲染
baseinfo.expressDeliverySmallZone.initParentWin = function(win,viewState,formRecord,gridData){
	if('ADD' === viewState){
		//新增时 必填项不用
		win.editForm.getForm().reset();
	}else{
		// 公共组件
		win.editForm.down('dynamicorgcombselector').setCombValue(formRecord.get('managementName'),formRecord.get('management'));//部门
		win.editForm.down('commonbigzoneselector').setCombValue(formRecord.get('bigzoneName'),formRecord.get('bigzonecode'));//县
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('provName'),formRecord.get('provCode'),'1');//省份
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('cityName'),formRecord.get('cityCode'),'2');//市
		win.editForm.down('linkregincombselector').setReginValue(formRecord.get('countyName'),formRecord.get('countyCode'),'3');//县
		win.editForm.getForm().findField('operatorCode').setCombValue(formRecord.get('operatorName'),formRecord.get('operatorCode'));//操作人
		win.editForm.down('commonExpressemployeeselectorYingYeName').setCombValue(formRecord.get('courierName'),formRecord.get('courierCode'));//
		//加载数据
		win.editForm.loadRecord(formRecord);
		//修改一个用户的时候，修改人也会改变：
		win.editForm.getForm().findField('operatorCode').setCombValue(FossUserContext.getCurrentUserEmp().empName,FossUserContext.getCurrentUserEmp().empCode);
	}
	if(baseinfo.viewState.view=== viewState){
		baseinfo.formFieldSetReadOnly(true,win.editForm);
		win.editForm.getForm().findField('p').setValue(formRecord.get('provName'));
		win.editForm.getForm().findField('c').setValue(formRecord.get('cityName'));
		win.editForm.getForm().findField('d').setValue(formRecord.get('countyName'));
	}
	return win;
};
//------------------------------------
baseinfo.expressDeliverySmallZone.DataPermissions=function(management){
	var params={'objectVo.expressDeliverySmallZoneEntity.regionCode':management};
	Ext.Ajax.request({
		url:baseinfo.realPath('queryDataPermissions.action'),
		params:params,
		method:'post',
		success:function(response){
			var result = Ext.decode(response.responseText);
			var grid  = Ext.getCmp('T_baseinfo-expressDeliverySmallZone_content').getQueryGrid();//得到grid
			grid.store.loadPage(1);//用分页的moveFirst()方法
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.showWoringMessage(result.message);
		}
	});
	
	
}
//------------------------------------MODEL----------------------------------
//接送货小区Model
Ext.define('Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel', {
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
	  //操作人编码
	  {name:'operatorCode',type:'string'},
	  //操作人名称
	  {name:'operatorName',type:'string'},
	  //
	  {name:'courierCode',type:'string'},
	  //
	  {name:'courierName',type:'string'},
	  //地图状态  add  update  delete
	  {name:'mapState',type:'string'},
	  //营业部与小区的距离
	  {name:'salesToSmallZone',type:'string'},
	  //主责快递员在快递车辆里面对应的营业部的地图信息
	  {name:'depCoordinate',type:'string'}
	  ]
});
//------------------------------------STORE----------------------------------
// 小区STORE
Ext.define('Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel',
	pageSize:20,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : baseinfo.realPath('queryExpressDeliverySmallZoneByEntity.action'),
		reader : {
			type : 'json',
			root : 'objectVo.smallZoneEntityList',
			totalProperty : 'totalCount'
		}
	}
});



//快递收派 小区 查询条件
Ext.define('Foss.baseinfo.expressDeliverySmallZone.QueryConditionForm', {
	extend : 'Ext.form.Panel',
	title:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.expressQuery'),
	frame: true,
	html:"<div id='gisMap' style='position:absolute;top:10%; left:25%; z-index:1; visibility: hidden; '></div>",
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
    	//labelSeparator:'',
    	labelWidth:130
    },
    height :180,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    record:null,												//绑定的model Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.items = me.getItems();
		me.callParent([cfg]);
	},
	getItems:function(){
		var me = this;
		return [{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionCode'),							//快递收派小区编码
			name:'regionCode'
		},{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionName'),							//快递收派小区名称
			name:'regionName',
			
		},{ 
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.management'),									//管理部门
			name:'management',
//			expressPart:'Y',
//			type:'ORG'
		},FossDataDictionary.getDataDictionaryCombo('EXPRESS_SMALL_TYPE',{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.smallRegionType'),								//区域类型
			name: 'regionType',
			colspan:2,
			editable:false,
	    	labelWidth:130
		}),{
			xtype : 'container',colspan:3,
			defaultType:'button',
			layout:'column',
			items : [{
				width: 75,
				text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.reset'),
				disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),//按钮权限
				hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),//按钮权限
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
				disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),
				hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),
				text : baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.query'),
				cls:'yellow_button',
				handler : function() {
					baseinfo.expressDeliverySmallZone.DataPermissions(this.up('form').getForm().findField('management').getValue());
				}
			}]
		}];
	}
});
//---------------------------------------------------------------GRID-----------------------------------------------------------------------------
//快递收派小区 查询结果grid
Ext.define('Foss.baseinfo.expressDeliverySmallZone.QueryResultGrid', {
	extend: 'Ext.grid.Panel',
	title :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.expressList'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight', 
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.isEmpty'),							//查询结果为空
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
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.add'),								//新增
			disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneAddButton'),   //按钮权限
			hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneAddButton'),   //按钮权限
			handler :function(){
				me.addExpressDeliverySmallZoneEntity({}).show();
			} 
		},'-', {
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.delete'),								//作废
			disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneDisableButton'),
			hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneDisableButton'),
			handler :function(){
				baseinfo.expressDeliverySmallZone.deleteExpressDeliverySmallZoneEntityByCode(baseinfo.delAgencyType);
			} 
		},'-', {
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.expressExport'),								//导出
			disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneExportButton'),
			hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneExportButton'),
			handler :function(){
				baseinfo.expressDeliverySmallZone.exportExpressDeliverySmallZone(config.queryForm);
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
	//得到快递收派小区编辑窗体
	getAgencyDeptWin:function(win,title,viewState,param,id){
		var formRecord = Ext.isEmpty(param.formRecord)?Ext.create('Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel'):param.formRecord;
		if (Ext.isEmpty(win)) {
			win = Ext.create('Foss.baseinfo.expressDeliverySmallZone.ExpressDeliverySmallZoneEntityWin',{
				'title':title,
				'viewState':viewState,
				'sourceGrid':this,
				'formRecord':formRecord,
				'id':id
			});
		}
		return baseinfo.expressDeliverySmallZone.initParentWin(win,viewState,formRecord,null);
	},
	addExpressDeliverySmallZoneEntityWin:null,						//新增基快递收派小区
	addExpressDeliverySmallZoneEntity:function(param){
		var addForm=Ext.getCmp('T_ExpressDeliverySmallZone_ADD');
		if(null!=addForm){
			addForm.editForm.getForm().reset()
			return addForm;
		}
		return this.getAgencyDeptWin(this.addExpressDeliverySmallZoneEntityWin,baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.add'),'ADD',param,'T_ExpressDeliverySmallZone_ADD');
	},
	updateExpressDeliverySmallZoneEntityWin:null,						//修改基快递收派小区
	updateExpressDeliverySmallZoneEntity:function(param){
		return this.getAgencyDeptWin(this.updateExpressDeliverySmallZoneEntityWin,baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.update'),'UPDATE',param,null);
	},
	viewExpressDeliverySmallZoneEntityWin:null,						//查看基快递收派小区
	viewExpressDeliverySmallZoneEntity:function(param){
//		return this.getAgencyDeptWin(this.viewExpressDeliverySmallZoneEntityWin,baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.viewSmallZone'),baseinfo.viewState.view,param);
	},
	expressDeliverySmallZoneShowWindow:null,
	getExpressDeliverySmallZoneShowWindow:function(param){
		return this.getAgencyDeptWin(this.expressDeliverySmallZoneShowWindow,baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.query'),baseinfo.viewState.view,param,null);
	},
	getMyListeners:function(){
		var me = this;
		return {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		 
	    	},
	    	//查看 快递收派小区
	    	itemdblclick: function(view,record) {
				
	    	}
	    };
	},
	getStore:function(){
		return Ext.create('Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityStore',{
			autoLoad : false,
			pageSize : 20,
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_baseinfo-expressDeliverySmallZone_content').getQueryForm().getForm();//得到查询的FORM表单
					queryForm.updateRecord(queryForm.record);
					var entity = queryForm.record.data;
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
//								//快递收派小区编码
								'objectVo.expressDeliverySmallZoneEntity.regionCode':entity.regionCode,
//								//快递收派小区名称
								'objectVo.expressDeliverySmallZoneEntity.regionName':entity.regionName,
//								//上级部门名称
								'objectVo.expressDeliverySmallZoneEntity.management':entity.management,
								//小区类型
								'objectVo.expressDeliverySmallZoneEntity.regionType':entity.regionType
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
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.operate'),//操作
			items: [{
				iconCls : 'deppon_icons_edit',
				tooltip :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.modify'), //修改
                disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneEditButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
                	var win = me.updateExpressDeliverySmallZoneEntity(param);    		
                	win.show();
    				/*if(!Ext.isEmpty(baseinfo.expressDeliverySmallZone.ployfeature1) && !Ext.isEmpty(win.editForm.formRecord.get('gisid'))){
     					baseinfo.expressDeliverySmallZone.ployfeature1.showModifiyAblePolygons([win.editForm.formRecord.get('gisid')]);
    				}*/
				}
			}, {
				iconCls : 'deppon_icons_cancel',
				tooltip :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.delete'), //作废
				disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneDisableButton'),
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					baseinfo.expressDeliverySmallZone.deleteExpressDeliverySmallZoneEntityByCode(null,grid.getStore().getAt(rowIndex),grid);
				}
			}, {
				iconCls : 'deppon_icons_showdetail',
				tooltip :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.query'), //查看详情
				disabled:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),//按钮权限
				hidden:!baseinfo.expressDeliverySmallZone.isPermission('expressDeliverySmallZone/expressDeliverySmallZoneQueryButton'),//按钮权限
				width : 42,
				handler : function (grid, rowIndex, colIndex) {
					var param = {};
                	param.formRecord = grid.getStore().getAt(rowIndex);
    				var win = me.getExpressDeliverySmallZoneShowWindow(param);
    				win.show();
    				/*if(!Ext.isEmpty(baseinfo.expressDeliverySmallZone.ployfeature) && !Ext.isEmpty(win.editForm.formRecord.get('gisid'))){
     					baseinfo.expressDeliverySmallZone.ployfeature.showModifiyAblePolygons([win.editForm.formRecord.get('gisid')]);
    				}*/
				}
				 
			}]
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionCode'),									//快递收派小区编码
			dataIndex : 'regionCode',
			width:150
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionName'),									//快递收派小区名称
			dataIndex : 'regionName',
			width:150
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.management'),									//管理部门
			dataIndex : 'managementName'
		},{
			//TODO 所属区域 名称
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.belongBigZone'),											//所属大区
			width:125,
			dataIndex : 'bigzoneName'
		},{
			//TODO 小区面积
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.smallZoneArea'),											//所属大区
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
			//TODO 营业部到小区的距离
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.salesToSmallZone'),											//所属大区
			flex:1,
			dataIndex : 'salesToSmallZone',
			renderer:function(value){
					if(null === value || ''===value){
						return ' ';
					}else{
						return (new Number(value)).toFixed(2);
					}
			}	
		},{
			//TODO 所属区域 名称
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.smallRegionType'),											//所属大区
			width:125,
			dataIndex : 'regionType',
			renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'EXPRESS_SMALL_TYPE');
			}
		},{
			text:'主责快递员',
			width:125,
			dataIndex:'courierName'
		},{
			text:'操作人',
			width:125,
			dataIndex:'operatorName'
		}];
	}
});

/**
 * -----------------------------------------------------------------------------------------------------------------------------------------------
 * **/
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressDeliverySmallZone_content')){
		return;
	}
	
	var queryForm  = Ext.create('Foss.baseinfo.expressDeliverySmallZone.QueryConditionForm',{'record':Ext.create('Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel')});//查询FORM
	var queryGrid  = Ext.create('Foss.baseinfo.expressDeliverySmallZone.QueryResultGrid',{'queryForm':queryForm});//查询结果显示列表
		Ext.getCmp('T_baseinfo-expressDeliverySmallZone').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-expressDeliverySmallZone_content',
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
//-------------------------------------------------------------------------------------------------------------------------------------------------
//快递收派小区 界面form
Ext.define('Foss.baseinfo.expressDeliverySmallZone.ExpressDeliverySmallZoneEntityWinForm', {
	extend : 'Ext.form.Panel',
	defaultType : 'textfield',
//	autoScroll:true,
	layout:{
        type: 'table',
        columns: 2
    },
    formRecord:null,												//绑定的model Foss.baseinfo.expressDeliverySmallZone.SmallZoneEntityModel
    formStore:null,													//绑定的formStore Foss.baseinfo.expressDeliverySmallZone.expressDeliverySmallZoneEntityStore
    viewState:null,
    id:null,
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
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionCode'),							//快递收派编码
			name:'regionCode',
			labelWidth:140,
			readOnly:true,
			allowBlank:(baseinfo.viewState.view === config.viewState)
		},{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.regionName'),							//快递收派名称
			name:'regionName',
			maxLength : 50,
			maxLengthText:'不能超过50个字符！',
			labelWidth:140,
			allowBlank:(baseinfo.viewState.view === config.viewState),
			listeners:{
				blur:function(The,eOpts ){
					baseinfo.expressDeliverySmallZone.querySmallByRegionName(The);
        		}
			}
		},FossDataDictionary.getDataDictionaryCombo('EXPRESS_SMALL_TYPE',{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.smallRegionType'),								//区域类型
			name: 'regionType',
			editable:false,
	    	labelWidth:140,
			allowBlank:(baseinfo.viewState.view === config.viewState)
		}),{
			labelWidth : 140,
			xtype:'dynamicorgcombselector',
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.management'),								//管理部门
			name: 'management',
//			expressPart:'Y',
//			type:'ORG',
			allowBlank:(baseinfo.viewState.view === config.viewState),
			listeners:{
				select:function(field,rs){
					if(!Ext.isEmpty(rs)){
        				baseinfo.expressDeliverySmallZone.autoCreateBigZoneNum(me.getForm().findField('management').getValue(),me);
            		}
        		}
			}
		},{
			   xtype:'container',
			   defaultType : 'textfield',
			   colspan:2,
			   hidden:(baseinfo.viewState.view != config.viewState),
			   layout:{
					type: 'table',
					columns: 3
				},
			   items:[{
						fieldLabel:'省份',							//快递收派编码
						name:'p',
						//labelWidth:140,
						readOnly:true,
						hidden:(baseinfo.viewState.view != config.viewState)
					},{
						fieldLabel:'城市',							//快递收派编码
						name:'c',
						//labelWidth:140,
						readOnly:true,
						hidden:(baseinfo.viewState.view != config.viewState)
					},{
						fieldLabel:'区县',							//快递收派编码
						name:'d',
						//labelWidth:140,
						readOnly:true,
						hidden:(baseinfo.viewState.view != config.viewState)
					}]
			},{
			colspan:2,
			labelWidth : 140,
			width: 650,
			hidden :(baseinfo.viewState.view === config.viewState),
			fieldLabel :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.Provinces'),
			provinceName:'provCode',// 省份名称—对应name
			cityName:'cityCode',// 城市name	
			areaName:'countyCode',// 县name
			allowBlank:(baseinfo.viewState.view === config.viewState),
			nationIsBlank:true,
			provinceIsBlank:(baseinfo.viewState.view === config.viewState),
			cityIsBlank:(baseinfo.viewState.view === config.viewState),
			areaIsBlank:(baseinfo.viewState.view === config.viewState),
			type : 'P-C-C',
			xtype : 'linkregincombselector'
		},{
			xtype:'commonExpressemployeeselectorYingYeName',
			fieldLabel:'主责快递员',
			colspan:2,
			name:'courierCode',
			allowBlank:false,
			listeners:{
				change:function(field,newValue,oldValue){
					this.up('panel').getForm().findField('courierName').setValue(field.rawValue);
				}
			}
		},{
			colspan:2,
			xtype: 'textareafield',
			name:'notes',
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.Remark'),
			maxLength : 300,
			maxLengthText:'不能超过300个字符！',
			width: 595,
			height:60,
			allowBlank:true
		},{
			colspan:2,
			name:'operatorCode',
			xtype:'commonemployeeselector',
			fieldLabel:'操作人',
			width:300,
			readOnly:true
		},{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.mapAddress'),
			name:'gisid',
			hidden:true,
			allowBlank:true
		},{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.mapArea'),
			name:'gisNewId',
			hidden:true,
			allowBlank:true
		},{
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.expressDeliverySmallZone'),
			name:'gisArea',
			hidden:true,
			allowBlank:true
		},{
			name:'virtualCode',
			hidden:true,
			allowBlank:true
		},{
			xtype:'commonbigzoneselector',
	    	valueField:'virtualCode',
			fieldLabel:baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.expressDeliveryBigZone'),							//所属大区
			name:'bigzonecode',
			hidden:true,
			allowBlank:true
		},{
			name:'courierName',
			hidden:true,
			allowBlank:true
		},{//地图状态
			name:'mapState',
			hidden:true,
			allowBlank:true
		},{//营业部与小区的距离
			name:'salesToSmallZone',
			hidden:true,
			allowBlank:true
		},{
			name:'depCoordinate',
			hidden:true,
			allowBlank:true
		}];
	}
});


//快递收派小区界面win
Ext.define('Foss.baseinfo.expressDeliverySmallZone.ExpressDeliverySmallZoneEntityWin',{
	extend : 'Ext.window.Window',
	title : baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.addWindow'),								//新增快递收派  默认新增
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'destroy',
	width :670,
	height :300,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			//清空 有ID的组件
		}
	},
	viewState:'ADD',				//查看状态,默认为新增
	editForm:null,											//快递收派小区表单Form
	editGrid:null,											//快递收派货小区表格Grid
	formRecord:null,										//快递收派小区实体 Foss.baseinfo.BusinessPartnerModel
	gridDate:null,											//快递收派小区 网点信息数组  [Foss.baseinfo.OuterBranchModel]
    constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.baseinfo.expressDeliverySmallZone.ExpressDeliverySmallZoneEntityWinForm',{'height':180,'viewState':config.viewState,'formRecord':config.formRecord});
		me.items = [me.editForm];
		me.listeners = {
			'beforeshow':function(me){
				//为新增的时候
				if(config.viewState=='ADD'){
					me.editForm.getForm().findField('operatorCode').setCombValue(FossUserContext.getCurrentUserEmp().empName,FossUserContext.getCurrentUserEmp().empCode);
				}
			},
		};
		me.fbar = me.getFbar(config);
		me.callParent([cfg]);
	},
	initComponent:function(){
		var me = this;
		this.callParent();
	},
	/*mapWindow:null,
	getMapWindow:function(){
		if(this.mapWindow==null){
			this.mapWindow=Ext.create('Foss.baseinfo.expressDeliverySmallZone.MainGisWindow',{
				
				
				
			})
		}
		return this.mapWindow;
	},*/
	
	//操作界面上的按钮
	getFbar:function(config){
		var me = this;
		return [{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.map'),
			handler :function(){
				//CBD区域  CBD_REGION\专业市场  PROFESSIONAL_MARKET\商业区   BUSINESS_QUARTER\住宅区   RESIDENCES
				//商住混合区   MIXED_AREA\其他  OTHER
				var form = me.down('form').getForm(),
					regionType = form.findField('regionType').getValue(),
					cityName = form.findField('cityCode').getRawValue(),
					countyName = form.findField('countyCode').getRawValue(),
					regionName = form.findField('regionName').getValue();//区域名称
				var bigReionCode = form.findField('bigzonecode').getValue();
				var management = form.findField('management').getRawValue();
				var regionName = form.findField('regionName').getValue();
				var courierCode = form.findField('courierCode').getRawValue();
				
				if(!Ext.isEmpty(bigReionCode)&&me.viewState!="VIEW"){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'该小区已被大区绑定，请到快递收派大区界面解除绑定在修改！');
				return;
				}	
				if(Ext.isEmpty(regionType)||Ext.isEmpty(cityName)||Ext.isEmpty(countyName)||
						Ext.isEmpty(management)){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'请先完善小区信息在进行地图圈画！');
				 return;
				}
				if(Ext.isEmpty(courierCode)&&me.viewState!="VIEW"){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'主责快递员为必填项，请先填写再进行地图圈画！');
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
				if(Ext.isEmpty(regionName)){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'绘画地图时小区名称不能为空！');
					return;
				}
				if(!DpMap){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'地图服务已停止，请稍后再试或联系IT服务中心！');
					return;
				}
//				if(baseinfo.expressDeliverySmallZone.region_type.jhq === regionType){regionType = baseinfo.expressDeliverySmallZone.region_type_map.jhq}
//				if(baseinfo.expressDeliverySmallZone.region_type.shq === regionType){regionType = baseinfo.expressDeliverySmallZone.region_type_map.shq}
				regionType= baseinfo.expressDeliverySmallZone.region_type_map.exs;
				if(Ext.isEmpty(cityName)){
					// 城市为空则 默认显示为上海
					Ext.Msg.confirm(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'小区城市为空，地图打开后默认为上海市！是否打开地图？',function(btn,text) {
						if (btn == 'yes') {
							 
							Ext.create('Ext.window.Window',{
								layout:'fit',
								resizable : false,
								items:[{
									xtype: 'container',
									height:630,
									width:1000,
									listeners: {
										afterrender: function(field) {
											windowObject = new DpMap(field.getId(), {center :mapLocation,zoom : 13}, function (map) { 
					    						  var fun = function(data){
					    							  if('ADD' === data.type){
															form.findField('gisid').setValue(data.code);
															form.findField('gisArea').setValue(data.area);
															form.findField('mapState').setValue(data.type);
															baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
															baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,me.viewState,me.down('form').getForm().getValues());
															field.up().close();
					    							  }else if('DELETE' === data.type){
					    							  		form.findField('mapState').setValue(data.type);
															form.findField('gisid').setValue(null);
															form.findField('gisArea').setValue(null);
															form.findField('salesToSmallZone').setValue(null);
															baseinfo.expressDeliverySmallZone.ployfeature1.openEditTool();
															baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,'UPDATE',me.down('form').getForm().getValues(),'DELETE');

														}else if('UPDATE' ===data.type){
															form.findField('gisArea').setValue(data.area);
															form.findField('mapState').setValue(data.type);
															baseinfo.expressDeliverySmallZone.ployfeature1.openEditTool();
//															baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,'UPDATE',me.down('form').getForm().getValues());

														}
					    						  }
					    						  var callFun =function(data){
					    							  if(data.type ='QUERY'){
					    								  baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
					    							  }
					    						  }
					    						 //实例化一个新类			
					    						  if(baseinfo.expressDeliverySmallZone.ployfeature1==null){
					    							  baseinfo.expressDeliverySmallZone.ployfeature1= new DpMap.service.DpMap_polygon(map,field.getId(),{isAddable:(baseinfo.viewState.view != config.viewState),polygonName:regionName, callBackFun:fun, foregroundType:regionType, backgroundType:'COUNTY'});					    							  
					    						  }
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
					var w=Ext.create('Ext.window.Window',{
						layout:'fit',
						resizable : false,
						items:[{
							xtype: 'container',
							height:630,
							width:1000,
							listeners: {
								afterrender: function(field) {
									var aa = new DpMap(field.getId(), {center :mapLocation,zoom : 13}, function (map) { 
				    						  var fun = function(data){
				    							  if('ADD' === data.type){
														form.findField('gisid').setValue(data.code);
														form.findField('gisArea').setValue(data.area);
														form.findField('mapState').setValue(data.type);
														baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
														if(config.viewState=='UPDATE'){
															baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,me.viewState,me.down('form').getForm().getValues(),'ADD');
														}else{
														    baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,me.viewState,me.down('form').getForm().getValues());
														    field.up().close();
														}
														
				    							  }else if('DELETE' === data.type){
				    							  		form.findField('mapState').setValue(data.type);
														form.findField('gisid').setValue(null);
														form.findField('gisArea').setValue(null);
														form.findField('salesToSmallZone').setValue(null);
														baseinfo.expressDeliverySmallZone.ployfeature1.openEditTool();
														baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,'UPDATE',me.down('form').getForm().getValues(),'DELETE');
													}else if('UPDATE' ===data.type){
														form.findField('gisArea').setValue(data.area);
														form.findField('mapState').setValue(data.type);
//														baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,'UPDATE',me.down('form').getForm().getValues());
//														field.up().close();
													}
				    						  }
				    						  var callFun=function(data){
				    							  if(data.type ='QUERY'){
				    								  baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
				    							  }
				    						  }
				    						  
				    						 //实例化一个新类			
				    						  baseinfo.expressDeliverySmallZone.ployfeature1 =  new DpMap.service.DpMap_polygon(map,field.getId(),{
				    							  isAddable:(baseinfo.viewState.view != config.viewState),polygonName:regionName, callBackFun:fun, foregroundType:regionType, backgroundType:'COUNTY',closeToolCallback:callFun});
				    					});	
								}
							}
						}]
					}).show();
					setTimeout(function(){
					if(Ext.isEmpty(baseinfo.expressDeliverySmallZone.ployfeature1)){
							if(baseinfo.viewState.update == config.viewState){
								var ifWhile = true;
									while(ifWhile && Ext.isEmpty(baseinfo.expressDeliverySmallZone.ployfeature1)){
										if(!Ext.isEmpty(baseinfo.expressDeliverySmallZone.ployfeature1)){
											baseinfo.expressDeliverySmallZone.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
											if(!Ext.isEmpty(form.findField('gisid').getValue())){
												baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
											}
											ifWhile = false;
											return;
										}
									}
							}
					}else{
						var obj=baseinfo.expressDeliverySmallZone.ployfeature1.showModifiablePolygons([form.findField('gisid').getValue()]);
						if(!Ext.isEmpty(form.findField('gisid').getValue())){
							baseinfo.expressDeliverySmallZone.ployfeature1.closeEditTool();
						}
					}
					}, 2000);
				}else if(baseinfo.viewState.view == config.viewState){
					Ext.create('Ext.window.Window',{
						layout:'fit',
						resizable : false,
						items:[{
							xtype: 'container',
							height:630,
							width:1000,
							listeners: {
								afterrender: function(field) {
									
								 var aa = new DPMap.MyMap(baseinfo.viewState.view, field.getId(),{center:mapLocation,zoom:'STREET' },function(map) { 
									
									 var gisid=form.findField('gisid').getValue();
										var depCoordinate=form.findField('depCoordinate').getValue();
										if(!Ext.isEmpty(depCoordinate)){
											var pointFeature = new DMap.PointFeature(map,{isAddable:false});
											pointFeature.showNon_modifiablePointById(depCoordinate);
										}
										  
										if(!Ext.isEmpty(gisid)){
											var polygonFeature=  new DMap.PolygonFeature(map,{isAddable:false});
										     var idList =[];
										     idList.push(gisid);
										     polygonFeature.showModifiyAblePolygons(idList);
										}
				    					});	
								}
							}
						}]
					}).show();
				}
			}
				
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.canle'),
			handler :function(){
				me.hide();
			}
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.reset'),
			disabled:(baseinfo.viewState.view === config.viewState),
			handler :function(){
				// 重置
				baseinfo.formReset([me.editForm.getForm()],[me.formRecord]);
				// 公共组件 
//				me.editForm.down('dynamicorgcombselector').setCombValue(me.formRecord.get('managementName'),me.formRecord.get('management'));//部门
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('provName'),me.formRecord.get('provCode'),'1');//省份
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('cityName'),me.formRecord.get('cityCode'),'2');//市
				me.editForm.down('linkregincombselector').setReginValue(me.formRecord.get('countyName'),me.formRecord.get('countyCode'),'3');//县\
//				me.editForm.loadRecord(me.formRecord);
//				win.editForm.down('commonemployeeselector').setCombValue(me.formRecord.get('courierName'),me.formRecord.get('courierCode'));//县

			} 
		},{
			text :baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.expressDeliverySmallZone.save'),
			disabled:(baseinfo.viewState.view === config.viewState),
			plugins: {
		        ptype: 'buttondisabledplugin',
		        seconds: 3
		    },
			handler :function(){
				var editForm = me.editForm.getForm();
		    	//实时校验的 结果是否通过,判断偏线代理必填项是否填写并全部填写合法
				var gisid=editForm.findField('gisid').getValue();
				if(Ext.isEmpty(gisid)){
					Ext.Msg.alert(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.billAdvertisingSlogan.fossAlertU'),'您还未对该小区进行地图范围的圈画！');
					return;
				}
		    	if(!editForm.isValid()){
		    		baseinfo.showInfoMsg(baseinfo.expressDeliverySmallZone.i18n('foss.baseinfo.airagencycompany.checkData'));
		    		return;
		    	}
	    		editForm.updateRecord(me.formRecord);
	    		
	    		baseinfo.expressDeliverySmallZone.submitExpressDeliverySmallZoneEntity(me,me.viewState,me.formRecord.data);
		    	 
			}
		}];
	}
});

