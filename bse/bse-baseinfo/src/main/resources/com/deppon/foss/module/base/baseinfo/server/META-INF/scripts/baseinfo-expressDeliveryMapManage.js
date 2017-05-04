/**
 * @author dujunhui-187862
 * 快递派送区域电子地图管理界面
 */
//常量定义
baseinfo.expressDeliveryMapManage.notEdited='NOT_EDITED';//'未编辑'
baseinfo.expressDeliveryMapManage.notVerified='NOT_VERIFIED';//未审核
baseinfo.expressDeliveryMapManage.isVerified='IS_VERIFIED';//已审核
baseinfo.expressDeliveryMapManage.isTurnBack='IS_TURNBACK';//已退回
baseinfo.expressDeliveryMapManage.isActived='IS_ACTIVED';//生效
//转换long类型为日期(在model中会用到)
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//营业部公共选择器初始化
baseinfo.getSalesDepartmant = function(){
	var me=Ext.getCmp('Foss_baseinfo_expressDeliveryMapManage_QueryForm_Id').getForm();
	if(Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'KD_PKP_SALESDEPT_MANAGER')//包含快递经营部门经理角色
			&& !Ext.Array.contains(FossUserContext.getCurrentUser().roleids,'EXPRESS_NETWORK_COMMISSIONER')){//无快递网点规划专员角色
		me.findField('salesDepartmentCode').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDeptCode());
		me.findField('salesDepartmentCode').setReadOnly(true);
	}
};
//提示框
baseinfo.expressDeliveryMapManage.expressDeliveryMapManageConfirmAlert = function(message,yesFn,noFn){
	Ext.Msg.confirm('温馨提示',message,function(o){
		if(o=='yes'){
			yesFn();
		}else{
			noFn();
		}
	});
};

/**
 * @param {} date--比较日期   day--比较日期之前或之后多少天的日期 day>0表示比较日期之后，day<0表示比较日期之前
 * @return {} 返回目标日期
 */
baseinfo.getStartDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(0);
	t2.setMinutes(0);
	t2.setSeconds(0);
	t2.setMilliseconds(0);	
	return t2;
};
baseinfo.getEndDate = function(date, day) {
	var d, s, t, t2;
	var MinMilli = 1000 * 60;
	var HrMilli = MinMilli * 60;
	var DyMilli = HrMilli * 24;
	t = Date.parse(date);
	t2 =  new Date(t+day*DyMilli);
	t2.setHours(23);
	t2.setMinutes(59);
	t2.setSeconds(59);
	t2.setMilliseconds(0);	
	return t2;
};

//消息提醒框
baseinfo.expressDeliveryMapManage.showWarningMsg = function(title,message,fun){
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
	//5秒后提醒框隐藏
	setTimeout(function(){
        Ext.Msg.hide();
    }, 5000);
};

//修改快递派送区域电子地图信息方法
baseinfo.expressDeliveryMapManage.updateExpressDeliveryMapManage = function(grid, rowIndex, colIndex){
	// 获取选中的数据
	var selection = grid.getStore().getAt(rowIndex);	
	var salesDepartmentCode = selection.data.salesDepartmentCode;
	Ext.Ajax.request({
		url:baseinfo.realPath('queryExpressDeliveryMapManageByCode.action'),
		params:{
			'expressDeliveryMapManageVo.entity.salesDepartmentCode':salesDepartmentCode
		},
		success:function(response){
			var result = Ext.decode(response.responseText);	
			// 获取弹出窗口GIS地图窗口
			var a_win = Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').
							getExpressDeliveryMapManageListGrid().getDeliveryGisWindow();
			var a_grid=Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').
							getExpressDeliveryMapManageListGrid();
			//获取要修改的信息实体
			a_grid.expressDeliveryMapManageEntity=result.expressDeliveryMapManageVo.entity;
			a_win.show();
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.show({
				title : '温馨提示',
				msg : result.message,
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
		}			
	});
};

//审核按钮对应的操作方法
baseinfo.expressDeliveryMapManage.verifyFunction=function(){
	var me=this;
	me.setDisabled(true);
	var selections=me.up('grid').getSelectionModel().getSelection();
	if(selections.length<1){//判断选中条数
		baseinfo.showErrorMes('请至少选中一条数据进行审核操作！');
		me.setDisabled(false);
		return;
	}
	
	var salesDepartmentCodes=new Array();//用于存放选中的营业部编码
	for(var i=0;i<selections.length;i++){
		if(selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isVerified){
			me.setDisabled(false);
			baseinfo.showInfoMes("你所选择的数据中已经包含了已审核状态!");//退回成功返回信息
			return;
		}
		if(selections[i].get('verifyState')!=baseinfo.expressDeliveryMapManage.notVerified){
			me.setDisabled(false);
			baseinfo.showInfoMes("请选择未审核的数据!");//退回成功返回信息
			return;
		}
		//存放选中的营业部编码
		salesDepartmentCodes.push(selections[i].get('salesDepartmentCode'));
	}
	//FOSS审核操作
	Ext.Ajax.request({
		url:baseinfo.realPath('verifyExpressDeliveryMapManage.action'),
		params:{
			"expressDeliveryMapManageVo.codeList":salesDepartmentCodes
		},
		success:function(response){
			var json=Ext.decode(response.responseText);
			baseinfo.showInfoMes(json.message);
			Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().store.loadPage(1);
			me.setDisabled(false);
			return;
		},
		exception:function(response){
			if(!Ext.isEmpty(response)){//界面抛出异常信息
				baseinfo.showErrorMes(Ext.decode(response.responseText).message);
			}else{
				baseinfo.showErrorMes('请求超时');
			}
			me.setDisabled(false);
		}
	})
};

//生效按钮对应的操作方法
baseinfo.expressDeliveryMapManage.activateFunction=function(){
	var me=this;
	me.setDisabled(true);
	var selections=me.up('grid').getSelectionModel().getSelection();//获取选中数据行
	if(selections.length<1){//判断选中的数据条数
		baseinfo.showErrorMes('请至少选中一条数据进行生效操作！');
		me.setDisabled(false);
		return;
	}
	
	var salesDepartmentCodes=new Array();//用于存放选中的营业部编码
	for(var i=0;i<selections.length;i++){
		if(selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isActived){
			me.setDisabled(false);
			baseinfo.showInfoMes("你所选择的数据中已经包含了已生效状态!");//退回成功返回信息
			return;
		}
		if(selections[i].get('verifyState')!=baseinfo.expressDeliveryMapManage.isVerified){
			me.setDisabled(false);
			baseinfo.showInfoMes("请选择已审批的数据!");//退回成功返回信息
			return;
		}
		salesDepartmentCodes.push(selections[i].get('salesDepartmentCode'));
	/*	DpMap.base.effectPloygon(selections[i].get('oldCoordinate'),selections[i].get('expressDeliveryCoordinate'),function(data){
			  if(data.type='effect'){
				  salesDepartmentCodes=null;
				  salesDepartmentCodes.push(selections[i].get('expressDeliveryCoordinate'));
				  Ext.Ajax.request({
						url:baseinfo.realPath('activateExpressDeliveryMapManage.action'),
						params:{
							"expressDeliveryMapManageVo.codeList":salesDepartmentCodes
						},
						success:function(response){
							var json=Ext.decode(response.responseText);
							return;
						},
						exception:function(response){
							 
						}
					})  
			  }
		});*/
	}
	
	/*baseinfo.showInfoMes(json.message);//生效成功返回提示
	Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().store.loadPage(1);*/
	
	if(salesDepartmentCodes.length<=0){
		me.setDisabled(false);
		baseinfo.showInfoMes("请刷新页面重新,重新选择所要生效的数据!");//GIS生效失败
		return;
	}
	Ext.Ajax.request({
		url:baseinfo.realPath('activateExpressDeliveryMapManage.action'),
		params:{
			"expressDeliveryMapManageVo.codeList":salesDepartmentCodes
		},
		success:function(response){
			var json=Ext.decode(response.responseText);
			baseinfo.showInfoMes(json.message);//生效成功返回提示
			Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().store.loadPage(1);
			//FOSS生效操作完成后，调用GIS API将生效状态传递至GIS
			for(var i=0;i<selections.length;i++){
				 var oldCoordinate=selections[i].get('oldCoordinate');
				 var expressDeliveryCoordinate=selections[i].get('expressDeliveryCoordinate');
				//调用GIS API将生效状态传递至GIS
//				DpMap.base.updateIsvalidByCode(selections[i].get('expressDeliveryCoordinate'),'2');
//				DpMap.base.deletePolygonByPolygonId(selections[i].get('oldCoordinate'),callFun);
				DpMap.base.effectPolygon(oldCoordinate,expressDeliveryCoordinate,function(data){
					if(data.type='effect'){
						console.log(data.type);
					}
				});
			}
			baseinfo.showInfoMes(json.message);//生效成功返回提示
			Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().store.loadPage(1);
			me.setDisabled(false);
			return;
		},
		exception:function(response){
			if(!Ext.isEmpty(response)){//界面异常抛出
				baseinfo.showErrorMes(Ext.decode(response.responseText).message);
			}else{
				baseinfo.showErrorMes('请求超时');
			}
			me.setDisabled(false);
		}
	})
};

//保存更新expressDeliveryCoordinate之后的ExpressDeliveryMapManageEntity至FOSS数据库
baseinfo.expressDeliveryMapManage.updateExpressDeliveryGISMap=function(expressDeliveryMapManageEntity,url){
	var me=this;
	Ext.Ajax.request({
		url:baseinfo.realPath(url),
		jsonData:{
			"expressDeliveryMapManageVo":{
				'entity':expressDeliveryMapManageEntity
			}
		},
		success:function(response){
			var json=Ext.decode(response.responseText);
			baseinfo.showInfoMes(json.message);//坐标保存成功返回提示
			Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().store.loadPage(1);
		},
		exception:function(response){
			if(!Ext.isEmpty(response)){
				baseinfo.showErrorMes(Ext.decode(response.responseText).message);
			}else{
				bseinfo.showErrorMes('请求超时');
			}
		}
	})
};



/**
 * 查询表单
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageQueryForm', {
	extend : 'Ext.form.Panel',
	title: baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.queryCondition'),//查询条件
	id : 'Foss_baseinfo_expressDeliveryMapManage_QueryForm_Id',
	frame: true,
	collapsible: true,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height :180,
	defaultType : 'textfield',
	layout:'column',//列布局
	items :[{
		name : 'salesDepartmentCode',
		fieldLabel : '营业部名称',
		columnWidth : 0.30,
        xtype : 'commonsaledepartmentselector',
        readOnly : false
	}, {
		name: 'verifyState',
		columnWidth:0.25,
		queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    value:'ALL',
	    store:baseinfo.getStore(null,null,['valueCode','valueName']
	    ,[{'valueCode':baseinfo.expressDeliveryMapManage.notEdited,'valueName':'未编辑'},{'valueCode':baseinfo.expressDeliveryMapManage.notVerified,'valueName':'未审核'},
	      {'valueCode':baseinfo.expressDeliveryMapManage.isVerified,'valueName':'已审核'},{'valueCode':baseinfo.expressDeliveryMapManage.isTurnBack,'valueName':'已退回'},
	      {'valueCode':baseinfo.expressDeliveryMapManage.isActived,'valueName':'生效'},
	      {'valueCode':'ALL','valueName':'全部'}//全部baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.all')
	      ]),
        fieldLabel: '审核状态',
        xtype : 'combo'
	},{
		name:'applyTimeBegin',
		xtype: 'datefield',
	    fieldLabel:'申请时间',
	    format:'Y-m-d H:i:s',
		editable:false,
	    columnWidth: 0.25,
	    labelWidth:80
	},{
		name:'applyTimeEnd',
		xtype: 'datefield',
	    fieldLabel:'至',
	    format:'Y-m-d H:i:s',
		editable:false,
	    columnWidth: 0.2,
	    labelWidth:20
	},{
		fieldLabel : '行政区域',
		labelWidth:100,
		columnWidth:1, 
		provinceWidth : 160,
		cityWidth : 160,
		cityLabel : '市',
		cityName : 'cityCode',//名称
		provinceLabel : '省',
		provinceName:'provCode',//省名称
		areaLabel : '县',
		areaName : 'countyCode',// 县名称
		areaWidth : 160,
		type : 'P-C-C',
		xtype : 'linkregincombselector',
		provinceIsBlank:true,
		cityIsBlank:true,
		areaIsBlank : true,
		areaLabelWidth:null
	},{
		xtype : 'container',
		columnWidth:1, 
		border: 1,
		defaultType:'button',
		layout:'column',
		items : [{
			xtype : 'button', 
			text:baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.reset'),//重置
			disabled:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageQueryButton'),
			hidden:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageQueryButton'),
			columnWidth:.1,
			handler: function(){
				var form=this.up('form').getForm();
				form.reset();
				baseinfo.getSalesDepartmant();//营业部公共选择器初始化不清空
//				form.findField('applyTimeBegin').setValue(Ext.Date.format(baseinfo.getStartDate(new Date(),-7),'Y-m-d H:i:s'));
//				form.findField('applyTimeEnd').setValue(Ext.Date.format(baseinfo.getEndDate(new Date(),0),'Y-m-d H:i:s'));
			}
		},{
			xtype:'container',
			html:'&nbsp;',
			columnWidth:.8
		},{
			xtype : 'button', 
			width:70,
			columnWidth:.1,
			text : baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.query'),//查询
			disabled:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageQueryButton'),
			hidden:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageQueryButton'),
			cls:'yellow_button',
			handler : function() {
				var form=this.up('form');
				baseinfo.expressDeliveryMapManage.expressDeliveryMapManageListQuery(form)
			}
		}]
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * Form查询方法: 
 */
baseinfo.expressDeliveryMapManage.expressDeliveryMapManageListQuery=function(me){
	//获取form及其参数值
	var form=me.getForm();
	var salesDepartmentCode = form.findField('salesDepartmentCode').getValue();
	var verifyState = form.findField('verifyState').getValue();
	var applyTimeBegin = form.findField('applyTimeBegin').getValue();
	var applyTimeEnd = form.findField('applyTimeEnd').getValue();
	var provCode = form.findField('provCode').getValue();
	var cityCode = form.findField('cityCode').getValue();
	var countyCode = form.findField('countyCode').getValue();
	//如果选择了省份，就一定要选择到市
	if(provCode!=null && provCode!=''){
		if(cityCode==null || cityCode==''){
			Ext.MessageBox.show({
				title : '温馨提示',
				msg : '通过行政区域查询必须至少选择到市',
				width : 300,
				buttons : Ext.Msg.OK,
				icon : Ext.MessageBox.WARNING
			});
			return false;
		}
	}
	// 设置参数
	params = form.getValues();
	Ext.apply(params, {
		'expressDeliveryMapManageVo.entity.verifyState' : verifyState,
		'expressDeliveryMapManageVo.entity.applyTimeBegin' : applyTimeBegin,
		'expressDeliveryMapManageVo.entity.applyTimeEnd' : applyTimeEnd,
		'expressDeliveryMapManageVo.entity.provCode' : provCode,
		'expressDeliveryMapManageVo.entity.cityCode' : cityCode,
		'expressDeliveryMapManageVo.entity.countyCode' : countyCode,
		'expressDeliveryMapManageVo.entity.salesDepartmentCode' : salesDepartmentCode
	});
	
	//获取grid及grid的store,并给store赋值
	var grid = Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid();
	grid.store.setSubmitParams(params);
	//查询
	grid.store.loadPage(1,{
	      callback: function(records, operation, success) {
	    	//抛出异常  
		    var rawData = grid.store.proxy.reader.rawData;
		    if(!success && ! rawData.isException){
		    	Ext.MessageBox.show({
					title : '温馨提示',
					msg : rawData.message,
					width : 300,
					buttons : Ext.Msg.OK,
					icon : Ext.MessageBox.WARNING
				});
				return false;
			}  
	    	//正常返回
	    	if(success){
	    		var result =   Ext.decode(operation.response.responseText);
				if(result.expressDeliveryMapManageVo.entityList!=null&&result.expressDeliveryMapManageVo.entityList.length>0){
					grid.show();
				}
	    	}
	       }
	    }); 
};

/**
 * 快递派送区域电子地图管理Model
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'id'
	},{
		name:'salesDepartmentCode', //营业部编码
		type : 'string'
	},{
		name:'salesDepartmentName', //营业部名称
		type : 'string'
	},{
		name:'districtCode',//行政区域编码
		type : 'string'
	},{
		name:'districtName',//行政区域名称
		type : 'string'
	},{
		name:'provCode',//省份编码
		type : 'string'
	},{
		name:'provName',//省份名称
		type : 'string'
	},{
		name:'cityCode',//城市编码
		type : 'string'
	},{
		name:'cityName',//城市名称
		type : 'string'
	},{
		name:'countyCode',//区县编码
		type : 'string'
	},{
		name:'countyName',//区县名称
		type : 'string'
	},{
		name:'verifyState',//审核状态
		type : 'string'
	},{
		name:'applyTimeBegin',//申请开始时间
		type : 'date',
        defaultValue : null,
        format:'Y-m-d H:i:s',
//        convert:dateConvert
        convert:baseinfo.changeLongToDate
	},{
		name:'applyTimeEnd',//申请结束时间
		type : 'date',
        defaultValue : null,
//        convert:dateConvert
        format:'Y-m-d H:i:s',
        convert:baseinfo.changeLongToDate
	},{
		name:'expressManNum',//快递员人数
        defaultValue : null
	},{
		name:'departServiceArea',//营业部服务面积
        defaultValue : null
	},{
		name:'applyManName',//申请人姓名
		type : 'string'
	},{
		name:'applyManCode',//申请人工号
		type : 'string'
	},{
		name:'applyTime',//申请时间
		type : 'date',
        defaultValue : null,
//        convert:dateConvert
        format:'Y-m-d H:i:s',
        convert:baseinfo.changeLongToDate
	},{
		name:'expressDeliveryCoordinate',//快递派送区域坐标
		type : 'string'
	},{
		name:'deptCoordinate',//部门区域坐标
		type : 'string'
	},{
		name:'verifyManName',//审核人姓名
		type : 'string'
	},{
		name:'verifyManCode',//审核人工号
		type : 'string'
	},{
		name:'verifyTime',//审核时间
		type : 'date',
        defaultValue : null,
//        convert:dateConvert
        format:'Y-m-d H:i:s',
        convert:baseinfo.changeLongToDate
	},{
		name:'active',//是否有效
		type : 'string'
	},{
		name:'createUser',//创建人
		type : 'string'
	},{
		name:'modifyUser',//修改人
		type : 'string'
	},{
		name:'versionNo',//版本号
		defaultValue : null
	},{
		name:'oldCoordinate',
		type : 'string'
	},{
		name:'note',//备注
		type : 'string'
	}]
});

/**
 * 快递派送区域电子地图管理Store
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageStore',{
	extend:'Ext.data.Store',
	model:'Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageModel',
	pageSize: 20,
	proxy:{
		type:'ajax',
		url:baseinfo.realPath('queryExpressDeliveryMapManage.action'),
		actionMethods:'post',
		reader:{
			type:'json',
			root:'expressDeliveryMapManageVo.entityList',
			totalProperty:'totalCount'
		}
	},
	submitParams: {},
	setSubmitParams: function(submitParams){
		this.submitParams = submitParams;
	},
	getSubmitParams: function(){
		return this.submitParams;
	},
	constructor:function(config){
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
	   		'beforeload': function(store, operation, eOpts){
	   			Ext.apply(me.submitParams, {
		          "limit":operation.limit,
		          "page":operation.page,
		          "start":operation.start
		          }); 
	   			Ext.apply(operation, {
	   				params : me.submitParams 
	   			});
	   		} 
		};
		me.callParent([ cfg ]);
	} 
});
//GIS电子地图窗口
Ext.define('Foss.baseinfo.expressDeliveryMapManage.MainGisWindow',{
	extend:'Ext.window.Window',
	closeAction:'hide',
	width:800,
	collBackFun:function(){},//回调函数
	expressDeliveryMapManageEntity : null, //查看实体数据
	expressDeliveryMapManageEntityList : null,//查看实体列表
	parent:null,//父窗体
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			height:600
		}];
		me.callParent([cfg]);
	},
	height:600
});
//查询结果列表
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageListGrid',{
	extend:'Ext.grid.Panel',
    title: '快递派送区域电子地图审核',
    frame:true,
    collapsible: true,
    hidden:false,
	height:500,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	expressDeliveryMapManageEntity : null, //查看实体数据
	expressDeliveryMapManageEntityList : null,//查看实体列表
    store: Ext.create('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageStore'),
    emptyText:'查询结果为空',//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.queryResultIsNull'),//查询结果为空
    //查看窗口
    expressDeliveryMapManageShowWindow : null,
    getExpressDeliveryMapManageShowWindow : function () {
    	if (this.expressDeliveryMapManageShowWindow == null) {
    		this.expressDeliveryMapManageShowWindow = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageShowWindow');
    		this.expressDeliveryMapManageShowWindow.parent = this; //父元素
    	}
    	this.expressDeliveryMapManageShowWindow.getExpressDeliveryMapManageForm().getForm().getFields().each(function (item) {
    		item.setReadOnly(true);
    	});
    	return this.expressDeliveryMapManageShowWindow;
    },
    //查看GIS快递派送区域电子地图
	deliveryGisShowWindow:null,
	getDeliveryGisShowWindow:function(){
	  var me =this;
	  if(Ext.isEmpty(this.deliveryGisShowWindow)){
		  this.deliveryGisShowWindow =Ext.create('Foss.baseinfo.expressDeliveryMapManage.MainGisWindow',{
			  'parent':this,
			  listeners:{
				 beforeshow:function(window){
					Ext.defer(function(){
						var mapLocation;
						  if((!Ext.isEmpty(me.expressDeliveryMapManageEntity.cityName))&&(!Ext.isEmpty(me.expressDeliveryMapManageEntity.countyName))){
							  //获取城市信息
							  var cityLocation = me.expressDeliveryMapManageEntity.cityName;
							  //获取区县信息
							  var countryLocation =me.expressDeliveryMapManageEntity.countyName;
							  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
	    							mapLocation = cityLocation + countryLocation;
	    						}else if(Ext.isEmpty(cityLocation)){
	    							mapLocation = baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.shanghaishi');
	    					}
						  }
						  //封装快递员人数和面积
						  function  message(name,content){
							  this.name = name;
							  this.content = content;
						  }
					      var m1 = new message("快递员人数",me.expressDeliveryMapManageEntity.expressManNum);
						  var m2 = new message("范围面积",me.expressDeliveryMapManageEntity.departServiceArea);
						  var oterMessage = [];
						  oterMessage.push(m1);
					      oterMessage.push(m2);
					      
						  var dmap =  new DpMap(window.items.items[0].getId(), {center:mapLocation,zoom: 13}, function(map) {
				               var fun = function(data) {
				            	   window.collBackFun(data);
				            	   if (data.type == 'DELETE') {
				            		   ployfeature.openEditTool();
				            	   }
				               }
				               
		                       var callFun = function(data) {
		                    	   if (data.type == 'QUERY') {
		                    		   ployfeature.closeEditTool();
		                    	   }
		                       }

		                       var backFun =function(data){
	                              alert(data);
								  if (data.type == 'DELETE') {
									  ployfeature.openEditTool();
								  }
								  if (data.type == 'ADD') {
									  ployfeature.closeEditTool();
								  }
								  if (data.type == 'UPDATE') {
									  ployfeature.closeEditTool();
								  }
		                       }
				 
		         var  ployfeature = new  DpMap.service.DpMap_polygon(map,window.items.items[0].getId(),{
		        	 isAddable:true, foregroundType:'EXPRESS_REGIONS',backgroundType:'CITY',
		        	 saveType:'2',showLableType:true,closeToolCallback: callFun,callBackFun: backFun});
		            ployfeature.closeEditTool();
		            if(!Ext.isEmpty(me.expressDeliveryMapManageEntity.expressDeliveryCoordinate)){
		            	ployfeature.showUnModifiablePolygonAndOtherMessage(me.expressDeliveryMapManageEntity.expressDeliveryCoordinate,oterMessage);
		            }
			  });
					},700,this);
				},
			    beforehide:function(window){
				    window.removeAll();
				    window.add(Ext.create('Ext.container.Container',{
	   					   height:500
	   				   })); 
				}
			 }
		 });
	  }
	  return this.deliveryGisShowWindow;
    },
    //编辑GIS快递派送区域电子地图
    deliveryGisWindow:null,
    getDeliveryGisWindow:function(){
	    var me =this;
	    if(Ext.isEmpty(this.deliveryGisWindow)){
		  this.deliveryGisWindow =Ext.create('Foss.baseinfo.expressDeliveryMapManage.MainGisWindow',{
			'parent':this,
			'collBackFun':function(data){
				var url=null;
				if(data.type =='DELETE'){
//					me.expressDeliveryMapManageEntity.expressDeliveryCoordinate='';
//					me.expressDeliveryMapManageEntity.departServiceArea=null;
					url="deleteExpressDeliveryGISMap.action";
				}else if(data.type =='ADD'||data.type=='UPDATE'){
					me.expressDeliveryMapManageEntity.expressDeliveryCoordinate=data.code;
					me.expressDeliveryMapManageEntity.departServiceArea=data.area;
					url="updateExpressDeliveryGISMap.action";
				}
				//保存更新expressDeliveryCoordinate之后的ExpressDeliveryMapManageEntity至FOSS数据库
				baseinfo.expressDeliveryMapManage.updateExpressDeliveryGISMap(me.expressDeliveryMapManageEntity,url);
				me.deliveryGisWindow.close();
			},
			listeners:{
				beforeshow:function(window){
					Ext.defer(function(){
						  var mapLocation;
						  if((!Ext.isEmpty(me.expressDeliveryMapManageEntity.cityName))&&(!Ext.isEmpty(me.expressDeliveryMapManageEntity.countyName))){
							  //获取城市信息
							  var cityLocation = me.expressDeliveryMapManageEntity.cityName;
							  //获取区县信息
							  var countryLocation =me.expressDeliveryMapManageEntity.countyName;
							  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
	    							mapLocation = cityLocation + countryLocation;
	    						}else if(Ext.isEmpty(cityLocation)){
	    							mapLocation = baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.shanghaishi');
	    					}
						  }
						//封装快递员人数和面积
						  function  message(name,content){
							  this.name = name;
							  this.content = content;
						  }
					      var m1 = new message("快递员人数",me.expressDeliveryMapManageEntity.expressManNum);
						  var m2 = new message("范围面积",me.expressDeliveryMapManageEntity.departServiceArea);
						  var oterMessage = [];
						  oterMessage.push(m1);
					      oterMessage.push(m2);
					      
						  new DpMap(window.items.items[0].getId(), {center :mapLocation,zoom : 13}, function (map) { 
    						  var fun = function(data){
    							window.collBackFun(data);
    							if(data.type =='DELETE'){
    								ployfeature.openEditTool();
    							}
    						  }
    						  var callFun =function(data){
    							if(data.type =='QUERY'){
    								ployfeature.closeEditTool();
    							}
    							if(data.type =='DELETE'||data.type =='ADD'||data.type=='UPDATE'){
    								ployfeature.openEditTool();
    							}
    						  }
    						  
    						 //实例化一个新类			
    						 var ployfeature = new DpMap.service.DpMap_polygon(map, 
    								 window.items.items[0].getId(),{isAddable:true, 
    							 		foregroundType:'EXPRESS_REGIONS', backgroundType:'CITY',
    							 			saveType:'2',showLableType:true,unShowPolygon:me.expressDeliveryMapManageEntity.oldCoordinate,closeToolCallback:callFun,callBackFun: fun});    						
    						 if(!Ext.isEmpty(me.expressDeliveryMapManageEntity.expressDeliveryCoordinate)){
    						    //调用根据id展示范围方法
    						     ployfeature.showModifiablePolygonAndOtherMessage(me.expressDeliveryMapManageEntity.expressDeliveryCoordinate,oterMessage);
    						  }
    						});	
					  },700,this);
				},
				beforehide:function(window){
					 window.removeAll();
	   				   window.add(Ext.create('Ext.container.Container',{
	   					   height:500
	   				   })); 
				}
			}
		  });
	    }
	    return this.deliveryGisWindow;
    },
    //批量查看GIS快递派送区域电子地图
	deliveryGisShowBatchWindow:null,
	getDeliveryGisShowBatchWindow:function(){
		  this.deliveryGisShowBatchWindow =Ext.create('Ext.window.Window',{
			  layout:'fit',
			  items:[{
					xtype: 'container',
					height:720,
					width:1150,
					listeners: {
						afterrender:function(field){
							  var me =Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid();
							  var mapLocation;
							  var ids=new Array();//用于存放选中的营业部派送坐标
							  me.expressDeliveryMapManageEntity=me.expressDeliveryMapManageEntityList[0];
							  for(var i=0;i<me.expressDeliveryMapManageEntityList.length;i++){ //循环将营业部派送坐标塞进ids
								  ids.push(me.expressDeliveryMapManageEntityList[i].expressDeliveryCoordinate);
							  }
							  if((!Ext.isEmpty(me.expressDeliveryMapManageEntity.cityName))&&(!Ext.isEmpty(me.expressDeliveryMapManageEntity.countyName))){
								  //获取城市信息
								  var cityLocation = me.expressDeliveryMapManageEntity.cityName;
								  //获取区县信息
								  var countryLocation =me.expressDeliveryMapManageEntity.countyName;
								  if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
									  mapLocation = cityLocation + countryLocation;
								  }else if(Ext.isEmpty(cityLocation)){
									  mapLocation = baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.shanghaishi');
								  }
							  }
							var aa = new DpMap(field.getId(),{center : mapLocation, zoom : "STREET" },function(map) {		  
								  var fun = function(data){
	                                  window.collBackFun(data);
	                                  if (data.type == 'DELETE') {
	                                      ployfeature.openEditTool();
	                                  }
	                              }
	                              var callFun = function(data) {
	                                  if (data.type == 'QUERY') {
	                                      ployfeature.closeEditTool();
	                                  }	
	                              }

								var backFun =function(data){                          
								  if (data.type == 'DELETE') {
	                                      ployfeature.openEditTool();
	                                  }
								  if (data.type == 'ADD') {
	                                      ployfeature.closeEditTool();
	                                  }
								  if (data.type == 'UPDATE') {
	                                      ployfeature.closeEditTool();
	                                  }
								}	 
	    
								var  ployfeature = new  DpMap.service.DpMap_polygon(map,field.getId(),{isAddable:true, 
										foregroundType:'EXPRESS_REGIONS',   backgroundType:'CITY',saveType:'2',
											showLableType:true,closeToolCallback: callFun,callBackFun: backFun});
								ployfeature.showUnModifiablePolygons(ids);					  
							  });	
						}
					}
				}]
			});
	  return this.deliveryGisShowBatchWindow;
    },
    //批量查看窗口
    expressDeliveryMapManageShowBatchWindow:null,
    getExpressDeliveryMapManageShowBatchWindow:function(){
    	if(this.expressDeliveryMapManageShowBatchWindow==null){
    		this.expressDeliveryMapManageShowBatchWindow=Ext.create('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageShowBatchWindow');
    		this.expressDeliveryMapManageShowBatchWindow.parent=this;//定义父元素
    	}
    	this.expressDeliveryMapManageShowBatchWindow.getExpressDeliveryMapManageBatchForm().getForm().getFields().each(function(item){
    		item.setReadOnly(true);
    	});
    	return this.expressDeliveryMapManageShowBatchWindow;
    },
    //导出方法
	exportToExcel: function () {
		var me = this,
		queryFormValue = me.up().getExpressDeliveryMapManageQueryForm().getForm().getValues();
		Ext.MessageBox.buttonText.yes = '确认';//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.confirm');
		Ext.MessageBox.buttonText.no = '取消';//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.cancel');
		if (!Ext.fly('downloadExpressDeliveryMapManageForm')) {
			var frm = document.createElement('form');
			frm.id = 'downloadExpressDeliveryMapManageForm';
			frm.style.display = 'none';
			document.body.appendChild(frm);
		}
		Ext.Msg.confirm('提示信息', '确定要导出查询结果吗?',
			function (btn, text) {//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.tipInfo');baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.exportMsg'),
				if (btn == 'yes') {
					var params = {			
							'expressDeliveryMapManageVo.entity.verifyState' : queryFormValue.verifyState,
							'expressDeliveryMapManageVo.entity.applyTimeBegin' : queryFormValue.applyTimeBegin,
							'expressDeliveryMapManageVo.entity.applyTimeEnd' : queryFormValue.applyTimeEnd,
							'expressDeliveryMapManageVo.entity.provCode' : queryFormValue.provCode,
							'expressDeliveryMapManageVo.entity.cityCode' : queryFormValue.cityCode,
							'expressDeliveryMapManageVo.entity.countyCode' : queryFormValue.countyCode,
							'expressDeliveryMapManageVo.entity.salesDepartmentCode' : queryFormValue.salesDepartmentCode
					};
					Ext.Ajax.request({
						url: baseinfo.realPath('queryExpressDeliveryMapManageForExprot.action'),
						form: Ext.fly('downloadExpressDeliveryMapManageForm'),
						params: params,
						method: 'post',
						isUpload: true,
						failure: function (response) {
							Ext.MessageBox.alert('提示信息',//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.tipInfo')
								'导出失败');//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.exportFailed')
						}
					});
				}
			}
		);
	},
	//批量查看方法
	batchView: function(){
		var me=this;
		var selections=me.up('grid').getSelectionModel().getSelection();//获取选中数据
		if(selections.length<2){//判断选中条数
			baseinfo.showErrorMes('请至少选中两条数据进行批量查看！单条数据请点击查看详情！');
			return;
		}
		var salesDepartmentCodes=new Array();//定义一个存放营业部编码的数组
		for(var i=0;i<selections.length;i++){//循环加入新建数组
			salesDepartmentCodes.push(selections[i].get('salesDepartmentCode'));
		}
		Ext.Ajax.request({
			url: baseinfo.realPath("queryExpressDeliveryMapManageBatchView.action"),
			params:{
				"expressDeliveryMapManageVo.codeList" : salesDepartmentCodes
			},
			success:function(response){
				var response=Ext.decode(response.responseText);
				//获取查看窗体
				var showWindow=me.up('grid').getDeliveryGisShowBatchWindow();
				Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid().
					expressDeliveryMapManageEntityList=response.expressDeliveryMapManageVo.entityList;
				showWindow.show();
			},
			exception:function(response){
				if(!Ext.isEmpty(response)){//返回异常信息
					baseinfo.showErrorMes(Ext.decode(response.responseText).message);
					return;
				}else{
					baseinfo.showErrorMes('请求超时');
				}
			}
		})
	},
	viewConfig: {
		enableTextSelection: true
	},
    returnBackWindow:null,
	getReturnBackWindow: function(){
		if(Ext.isEmpty(this.returnBackWindow)){
			this.returnBackWindow = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ReturnBackWindow');
			this.returnBackWindow.parent = this;
		}
		return this.returnBackWindow;
	},
	//退回按钮对应的操作方法
	returnBack:function(){
		var me=this;
		me.setDisabled(true);
		var selections=me.up('grid').getSelectionModel().getSelection();//获取选中的数据行
		if(selections.length<1){//判断选中的数据条数不小于1
			baseinfo.showErrorMes('请至少选中一条数据进行退回操作！');
			me.setDisabled(false);
			return;
		}
		var salesDepartmentCodes=new Array();//用于存放部门编码的数组
		for(var i=0;i<selections.length;i++){
			salesDepartmentCodes.push(selections[i].get('salesDepartmentCode'));
		}
		//提示框点击取消触发函数
		var noFn=function(){
		 	return false;
		};
		//定义标志位,确定是否弹窗提示
		var flag=false;
		for(var i=0;i<selections.length;i++){
			/*//数据中包含“已审核”、“生效”状态的数据时，点击“退回”按钮时系统弹窗提示
			if(selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isVerified 
				|| selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isActived
				|| selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isTurnBack){
				flag=true;
			}*/
			if(selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.isTurnBack){
				baseinfo.showInfoMes("你所选择的数据中已经包含了已退回状态!");//退回成功返回信息
				me.setDisabled(false);
				return;
			}
			
			if(selections[i].get('verifyState')==baseinfo.expressDeliveryMapManage.notEdited ){
				baseinfo.showInfoMes("你所选择的数据中不能包含为编辑状态数据!");//退回成功返回信息
				me.setDisabled(false);
				return;
			}
		}
		var gird=Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content').getExpressDeliveryMapManageListGrid();
		gird.getReturnBackWindow().codeList=salesDepartmentCodes;
		gird.getReturnBackWindow().show();
		me.setDisabled(false);
	},
	constructor:function(config){
		var me = this;
		me.columns = [{
			xtype: 'rownumberer',
			width: 40,
			text: '序号' //序号
		},{
	    	xtype:'actioncolumn',
	    	header:'操作',
	    	width:80,
	    	align: 'center',
	    	items:[{
	    		iconCls : 'deppon_icons_showdetail',
				tooltip : '查看详情',//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.details'), //查看详情
				disabled:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageViewButton'),
				width : 40,
				handler : function (grid, rowIndex, colIndex) {
					//获取选中的数据
					var record = grid.getStore().getAt(rowIndex);
					me.expressDeliveryMapManageEntity =record.data;
					var showWindow = me.getDeliveryGisShowWindow(); //获得GIS查看窗口
					showWindow.show(); //显示查看窗口
					
				/*	var salesDepartmentCode = record.get('salesDepartmentCode'); //详情id expressDeliveryCoordinate
					var params = {
						'expressDeliveryMapManageVo' : {
							'entity' : {
								'salesDepartmentCode' : salesDepartmentCode
							}
						}
					};
					var successFun = function (json) {
//						var json = Ext.decode(json.responseText);
						var showWindow = me.getDeliveryGisShowWindow(); //获得GIS查看窗口
//						showWindow.expressDeliveryMapManageEntity = json.expressDeliveryMapManageVo.entity; 
						me.expressDeliveryMapManageEntity = json.expressDeliveryMapManageVo.entity;
						showWindow.show(); //显示查看窗口
					};
					var failureFun = function (json) {
						if (Ext.isEmpty(json)) {
							baseinfo.showErrorMes('请求超时'); //请求超时baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.requestTimeout')
						} else {
							baseinfo.showErrorMes(json.message);
						}
					};
					var url = baseinfo.realPath('queryExpressDeliveryMapManageByCode.action');
					baseinfo.requestJsonAjax(url, params, successFun, failureFun);*/
				}
	    	},{
	    		iconCls : 'deppon_icons_edit',
				tooltip:'修改',
				getClass : function (v, m, r, rowIndex) {
					if (r.get('verifyState') === baseinfo.expressDeliveryMapManage.isVerified) {//当数据状态为“已审核”时，“编辑”按钮不可用，数据不能被重新编辑
					    return 'statementBill_hide';
					} else {
					    return 'deppon_icons_edit';
					}
				},
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageUpdateButton'),
				handler:function(grid, rowIndex, colIndex){
//					baseinfo.expressDeliveryMapManage.updateExpressDeliveryMapManage(grid, rowIndex, colIndex)
					//获取选中的数据
					var record = grid.getStore().getAt(rowIndex);
					me.expressDeliveryMapManageEntity =record.data;
					var showWindow = me.getDeliveryGisWindow(); //获得GIS查看窗口
					showWindow.show(); //显示查看窗口
				}
	    	}]
		},{
			header: '营业部名称',
			width:120,
			dataIndex: 'salesDepartmentName'
		},{
			header: '审核状态',
			width:80,
			dataIndex: 'verifyState',
			renderer: function (value) {
				if (value == baseinfo.expressDeliveryMapManage.notEdited) {
					return '未编辑'; 
				}else if(value==baseinfo.expressDeliveryMapManage.notVerified){
					return '未审核';
				}else if(value==baseinfo.expressDeliveryMapManage.isVerified){
					return '已审核';
				}else if(value==baseinfo.expressDeliveryMapManage.isTurnBack){
					return '已退回';
				}else if(value==baseinfo.expressDeliveryMapManage.isActived){
					return '生效';
				}
			}
		},{
			header: '行政区域',
			width:140,
			dataIndex: 'districtName'
		},{
			header: '省份名称',
			hidden:true,
			width:140,
			dataIndex: 'provName'
		},{
			header: '城市名称',
			hidden:true,
			width:140,
			dataIndex: 'cityName'
		},{
			header: '区县名称',
			hidden:true,
			width:140,
			dataIndex: 'countyName'
		},{
			header: '快递员人数',
			width:70,
			dataIndex: 'expressManNum'
		},{
			header: '营业部服务面积(km²)',
			width:70,
			dataIndex: 'departServiceArea'
		},{
			header: '申请人',
			width:70,
			dataIndex: 'applyManName'
		},{
			header: '申请时间',
			width:120,
			dataIndex: 'applyTime',
			format:'Y-m-d H:i:s',
			renderer: function (value) {
				return Ext.Date.format(value,'Y-m-d H:i:s');
			}
		},{
			header: '审核人',
			width:70,
			dataIndex: 'verifyManName'
		},{
			header: '审核时间',
			width:100,
			dataIndex: 'verifyTime',
			format:'Y-m-d H:i:s',
			renderer: function (value) {
				return Ext.Date.format(value,'Y-m-d H:i:s');
			}
		},{
			header: '备注',
			width:70,
			dataIndex: 'note',
			xtype : 'ellipsiscolumn'
		}];
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[{
				xtype :'button',
				text : '批量查看',
				columnWidth :.08,
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageBatchViewButton'),
				hidden: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageBatchViewButton'),
				handler : me.batchView //批量查看按钮对应操作
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.78
			},{
				xtype :'button',
				text : '导出',
				columnWidth :.08,
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageExportButton'),
				hidden: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageExportButton'),
				plugins: {
					ptype: 'buttondisabledplugin',
					seconds: 5
				},
				handler: me.exportToExcel,//导出按钮对应的操作方法
				scope: me
			}]
		},{
	   		xtype: 'toolbar',
		    dock: 'bottom',
		    layout:'column',		    	
		    defaults:{
				margin:'0 0 5 3'
			},		
		    items: [{
				height:5,
				columnWidth:1
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.3
			},{//分页组件
				xtype:'standardpaging',
				store:me.store,
				columnWidth:.7,
				plugins: Ext.create('Deppon.ux.PageSizePlugin',{
					pageSize:20,
					maximumSize:500
				})
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.69
			},{
				xtype :'button',
				text : '审核',
				columnWidth :.08,
				handler: baseinfo.expressDeliveryMapManage.verifyFunction,//审核按钮对应的操作方法
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageVerifyButton'),
				hidden: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageVerifyButton')
			},{
				xtype :'button',
				text : '生效',
				columnWidth :.08,
				handler: baseinfo.expressDeliveryMapManage.activateFunction,//生效按钮对应的操作方法
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageActivateButton'),
				hidden: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageActivateButton')
			},{
				xtype :'button',
				text : '退回',
				columnWidth :.08,
				handler: me.returnBack,//退回按钮对应的操作方法
				disabled: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageTurnBackButton'),
				hidden: !baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/expressDeliveryMapManageTurnBackButton')
			}]
		}];	
		me.callParent();
	}
});
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ReturnBackWindow',{
		extend: 'Ext.window.Window',
		title: '回退备注',//"立即激活方案"baseinfo.priceCoupon.i18n('foss.priceCoupon.activeSchemeQuick')
		width:380,
		height:200,
		codeList:null,
		closeAction: 'hide' ,
		returnBackForm:null,
		getReturnBackForm : function(){
	    	if(Ext.isEmpty(this.returnBackForm)){
	    		this.returnBackForm = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ReturnBackForm');
	    	}
	    	return this.returnBackForm;
	    },
	    listeners:{
	    	beforehide:function(me){
	    		me.getReturnBackForm().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getReturnBackForm()];
			me.callParent(cfg);
		}
});
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ReturnBackForm',{
	extend : 'Ext.form.Panel',
	layout:'column',
	returnBack:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var params = {
					'expressDeliveryMapManageVo.entity.note':form.findField('note').getValue(),
					'expressDeliveryMapManageVo.codeList':me.up().codeList
			};
    		var url = baseinfo.realPath('turnBackExpressDeliveryMapManage.action');
    		var successFun = function(json){
				baseinfo.showInfoMes(json.message);//退回成功返回信息
				me.up('window').close();
				me.up('window').parent.store.loadPage(1);
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				baseinfo.showErrorMes('请求超时！');//请求超时！baseinfo.priceCoupon.i18n('foss.baseinfo.requestOvertime')
    			}else{
    				baseinfo.showErrorMes(json.message);
    			}
    	    };
    		baseinfo.requestAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				fieldLabel:'备注',//'生效日期'baseinfo.priceCoupon.i18n('foss.priceCoupon.effectiveDate')
				name : 'note',
				xtype : 'textareafield',
				maxLength:50,
				columnWidth:.94
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.17,
				text : '确认',//,"确认"baseinfo.priceCoupon.i18n('foss.baseinfo.sure')
				handler : function() {
					me.returnBack();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.17,
				text : '取消',//"取消"baseinfo.priceCoupon.i18n('foss.baseinfo.cancel')
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});
/**
 * 查看快递派送区域电子地图信息window
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageShowWindow', {
	extend : 'Ext.window.Window',
	title : '查看快递派送区域电子地图信息',
	closable : true,
	resizable : false,
	expressDeliveryMapManageEntity : null, //查看实体数据
	expressDeliveryMapManageEntityList : null,//查看实体列表
	autoScroll: true,
	parent : null, //父元素（弹出这个window的gird——Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageListGrid）
	closeAction : 'hide',
	width : 800,
	height : 500,
	listeners : {
		beforehide : function (me) {
			me.resetData(); //清除掉这次的数据
		},
		beforeshow : function (me) {
			me.loadValue();
		}
	},
	//查看FORM
	expressDeliveryMapManageForm : null,
	getExpressDeliveryMapManageForm : function () {
		if (Ext.isEmpty(this.expressDeliveryMapManageForm)) {
			this.expressDeliveryMapManageForm = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ShowExpressDeliveryMapManageForm');
		}
		return this.expressDeliveryMapManageForm;
	},
	//清除数据
	resetData : function(){
		var me = this;
		var form = me.getExpressDeliveryMapManageForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems =form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				form.remove(oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
	},
	//加载原有数据
	loadValue : function () { 
		var me = this;
		var expressDeliveryMapManageModel = new Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageModel(me.expressDeliveryMapManageEntity);
		me.getExpressDeliveryMapManageForm().getForm().findField('salesDepartmentName').setValue(expressDeliveryMapManageModel.get('salesDepartmentName'));
		if(expressDeliveryMapManageModel.get('verifyState')==baseinfo.expressDeliveryMapManage.notEdited){
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue('未编辑');
		}else if(expressDeliveryMapManageModel.get('verifyState')==baseinfo.expressDeliveryMapManage.notVerified){
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue('未审核');
		}else if(expressDeliveryMapManageModel.get('verifyState')==baseinfo.expressDeliveryMapManage.isVerified){
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue('已审核');
		}else if(expressDeliveryMapManageModel.get('verifyState')==baseinfo.expressDeliveryMapManage.isTurnBack){
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue('已退回');
		}else if(expressDeliveryMapManageModel.get('verifyState')==baseinfo.expressDeliveryMapManage.isActived){
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue('生效');
		}else{
			me.getExpressDeliveryMapManageForm().getForm().findField('verifyState').setValue(expressDeliveryMapManageModel.get('verifyState'));
		}
		
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : '取消',//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}
		];
		me.items = [me.getExpressDeliveryMapManageForm()];//,me.getExpressDeliveryGISMapForm()
		me.callParent([cfg]);
	}
});

//查看快递派送区域电子地图信息form
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ShowExpressDeliveryMapManageForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '查看快递派送区域电子地图',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'营业部名称',
			flex:1,
			columnWidth:.6,
			readOnly : true,
			xtype : 'textfield',
			name:'salesDepartmentName'
		},{
			fieldLabel:'审核状态',
			flex:1,
			columnWidth:.4,
			readOnly : true,
			xtype : 'textfield',
			name:'verifyState'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 修改快递派送区域电子地图信息window
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.UpdateWindow', {
	extend : 'Ext.window.Window',
	id : 'Foss_baseinfo_expressDeliveryMapManage_UpdateWindow_Id',
	title : '修改快递派送区域电子地图信息',
	width : 950,
	height : 710,
	// resizable : false,
	columnWidth : 0.98,
	modal : true,
	closeAction : 'destroy',
	layout : 'column',
	getCancelButton : function(me) {
		var me = this;
		if (this.cancelButton == null) {
			this.cancelButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				text : '取消',
				// margin : '0 397 0 0',
				hidden : false,
				handler : function() {
					//取消按钮对应的操作
					me.close();
				}
			});
		}
		return this.cancelButton;
	},
	getResetButton : function(me) {
		var me = this;
		if (this.resetButton == null) {
			this.resetButton = Ext.create('Ext.button.Button', {
				xtype : 'button',
				hidden : false,
				cls : 'yellow_button',
				name : 'reset',
				text : '重置',
				margin : '0 10 0 0',
				handler : function() {
					//重置按钮对应的操作
					me.close();
				}
			});
		}
		return this.resetButton;
	},
	saveUserDepts : function() {
		var wind = Ext.getCmp('Foss_baseinfo_expressDeliveryMapManage_UpdateWindow_Id');
	},
	getSaveButton : function() {
		var me = this;
		if (this.saveButton == null) {
			this.saveButton = Ext.create('Ext.button.Button', {
				cls : 'yellow_button',
				text : '保存',
				//hidden:!baseinfo.expressDeliveryMapManage.isPermission('expressDeliveryMapManage/userDeptAuthoritySaveButton'),
				margin : '0 10 0 0',
				handler : function() {
					//保存按钮对应操作
					me.close();
				}
			});
		}
		return this.saveButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [Ext.create('Foss.baseinfo.expressDeliveryMapManage.UpdateForm')];
		me.fbar = [me.getResetButton(), me.getSaveButton(), me.getCancelButton()]
		me.callParent([cfg]);
	}
});
/**
 * 修改快递派送区域电子地图信息Form
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.UpdateForm',{
	extend:'Ext.form.Panel',
	id : 'Foss_baseinfo_expressDeliveryMapManage_UpdateForm_Id',
	frame:false,
	height:120,
	columnWidth : 0.98,
	defaults:{
		margin :'10 10 10 10',
		labelWidth :85,
		colspan : 1 
	},
//	defaultType:'textfield',
	layout:{
		type :'column',
		columns :2
	},
	items : [{
				name : 'salesDepartmentCode',
				fieldLabel : '营业部名称',
				columnWidth : .4,
		        xtype : 'commonsaledepartmentselector'
			},{
					xtype:'container',
					border:false,
					height:24,
					html:'&nbsp;',
					columnWidth:.59
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}			
});
/*
 * 批量查看地图Window
 */
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageShowBatchWindow',{
	extend:'Ext.window.Window',
	title:'批量查看快递派送区域电子地图信息',
	closable:true,
	resizable:false,
	autoScroll:true,
	parent:null,
	expressDeliveryMapManageEntity:null,
	expressDeliveryMapManageEntityList:null,
	width : 800,
	height : 500,
	closeAction:'hide',
	listeners : {
		beforehide : function (me) {
			me.resetData(); //清除掉这次的数据
		},
		beforeshow : function (me) {
			me.loadValue();
		}
	},
	//批量查看FORM
	expressDeliveryMapManageBatchForm : null,
	getExpressDeliveryMapManageBatchForm : function () {
		if (Ext.isEmpty(this.expressDeliveryMapManageBatchForm)) {
			this.expressDeliveryMapManageBatchForm = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ShowExpressDeliveryMapManageBatchForm');
		}
		return this.expressDeliveryMapManageBatchForm;
	},
	//清除数据
	resetData : function(){
		var me = this;
		var form = me.getExpressDeliveryMapManageBatchForm();
		if (!Ext.isEmpty(form.oldItems)) { //将多余的元素清掉
			var oldItems =form.oldItems;
			for (var i = 0; i < oldItems.length; i++) {
				form.remove(oldItems[i]);
			}
		}
		form.getForm().reset(); //表格重置
	},
	//加载原有数据
	loadValue : function () { 
		var me = this;
		var expressDeliveryMapManageModel = new Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageModel(me.expressDeliveryMapManageEntityList.pop(0));
		me.getExpressDeliveryMapManageBatchForm().getForm().findField('provName').setValue(expressDeliveryMapManageModel.get('provName'));
		me.getExpressDeliveryMapManageBatchForm().getForm().findField('cityName').setValue(expressDeliveryMapManageModel.get('cityName'));
		me.getExpressDeliveryMapManageBatchForm().getForm().findField('countyName').setValue(expressDeliveryMapManageModel.get('countyName'));
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.fbar = [{
				text : '取消',//baseinfo.expressDeliveryMapManage.i18n('foss.baseinfo.cancel'), //取消
				handler : function () {
					me.close();
				}
			}
		];
		me.items = [me.getExpressDeliveryMapManageBatchForm()];//,me.getExpressDeliveryGISMapBatchForm()
		me.callParent([cfg]);
	}
});
//批量查看快递派送区域电子地图信息Form
Ext.define('Foss.baseinfo.expressDeliveryMapManage.ShowExpressDeliveryMapManageBatchForm',{
	extend:'Ext.form.Panel',
	layout:'column',	
	frame:true,
	title : '批量查看快递派送区域电子地图',
	defaultType: 'textfield',	
	defaults: {
		margin:'0 5 5 5',
		anchor: '99%',
		labelWidth:100
	},
	standardSubmit:true,
	constructor:function(config){
		var me =this,cfg =Ext.apply({},config);
		me.items=[{
			fieldLabel:'省份名称',
			flex:1,
			columnWidth:.33,
			readOnly : true,
			xtype : 'textfield',
			name:'provName'
		},{
			fieldLabel:'城市名称',
			flex:1,
			columnWidth:.33,
			readOnly : true,
			xtype : 'textfield',
			name:'cityName'
		},{
			fieldLabel:'区县名称',
			flex:1,
			columnWidth:.33,
			readOnly : true,
			xtype : 'textfield',
			name:'countyName'
		}];
		me.callParent([cfg]);
	}
});

//初始化界面
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressDeliveryMapManage_content')) {
		return;
	}
	//查询FORM
	var expressDeliveryMapManageQueryForm = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageQueryForm');
	//显示grid
	var expressDeliveryMapManageListGrid = Ext.create('Foss.baseinfo.expressDeliveryMapManage.ExpressDeliveryMapManageListGrid');
	//初始化营业部公共选择器
	baseinfo.getSalesDepartmant();
	
	Ext.create('Ext.panel.Panel', {
		id: 'T_baseinfo-expressDeliveryMapManage_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getExpressDeliveryMapManageQueryForm:function(){
			return expressDeliveryMapManageQueryForm;
		},
		getExpressDeliveryMapManageListGrid:function(){
			return expressDeliveryMapManageListGrid;
		},
		items: [expressDeliveryMapManageQueryForm,expressDeliveryMapManageListGrid],
		renderTo : 'T_baseinfo-expressDeliveryMapManage-body'
	});
});