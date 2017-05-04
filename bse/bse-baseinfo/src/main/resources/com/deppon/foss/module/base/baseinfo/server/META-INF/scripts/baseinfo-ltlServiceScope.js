baseinfo.ltlServiceScope.coverFeature = null;

/**
 * 判断点击或查询的行政区域是否符合条件和是否有营业网点
 */
baseinfo.ltlServiceScope.verificationServiceScope=function(currentDistrictName,scopeList,seqName,_degree){
	//查询省级别以上的区域，直接返回null，不进入判断
	if (_degree=='DISTRICT_COUNTY'||_degree=='CITY') {
		
		var depCoordinateIds = [];					//点坐标IDS
		var deliveryCoordinateIds = [];				//范围IDS
		
		//查询市、县区无营业网点弹出提示框,并转到该市、县区
		if (scopeList.length<=0) {
			baseinfo.ltlServiceScope.coverFeature.showCover(currentDistrictName,deliveryCoordinateIds,depCoordinateIds,'LTL');
			Ext.ux.Toast.msg(baseinfo.ltlServiceScope.i18n('foss.baseinfo.tips'), baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.noSaleDept'), 'error', 3000);//该区域暂无营业网点
			return;
		}

		Ext.each(scopeList, function (scopeid, index){
			if (scopeid!=null) {
				if (scopeid.depCoordinate!=null) {
					//点坐标IDS
					depCoordinateIds.push(scopeid.depCoordinate);
				}
				if (scopeid.deliveryCoordinate!=null) {
					//范围IDS
					deliveryCoordinateIds.push(scopeid.deliveryCoordinate);
				}
			}
		});
		
		if(!Ext.isEmpty(depCoordinateIds)||!Ext.isEmpty(deliveryCoordinateIds)){
			// 清除地图所有的覆盖物
			baseinfo.ltlServiceScope.coverFeature.clearOverlays();
			
			//如果查询市，不显示周围派送范围
			if (_degree=='CITY') {
				// 显示派送范围和网点坐标
				baseinfo.ltlServiceScope.coverFeature.showCover(currentDistrictName,deliveryCoordinateIds,depCoordinateIds,'LTL');
			}
			
			//如果查询县区，显示派送范围
			if (_degree=='DISTRICT_COUNTY') {
				// 显示周边派送范围
				baseinfo.ltlServiceScope.coverFeature.showAmbitusCover(deliveryCoordinateIds,seqName,'SALES_DEPT');
				// 显示派送范围和网点坐标
				baseinfo.ltlServiceScope.coverFeature.showCover(currentDistrictName,deliveryCoordinateIds,depCoordinateIds,'LTL');
			}
		}
	}else{
		Ext.ux.Toast.msg(baseinfo.ltlServiceScope.i18n('foss.baseinfo.tips'), baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.region'), 'error', 3000);//只可查询市、县(区)级别的营业网点和派送范围
		return;
	}
}

/**
 * 树节点操作
 */
baseinfo.ltlServiceScope.treeNodeOperator=function(me,record){
	var a_code=record.data.id;
	// 如果是根节点，点击时不响应
	if(a_code =='100000'){
		return ;
	}
	var params = {
			"ltlServiceScopeVo.districtDto.code":a_code
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryDistrictByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			//得到行政区域名称全路径，从顶级区域到查询区域
			var seqName = json.ltlServiceScopeVo.districtDto.fullNamePath;
			//得到行政区域级别
			var _degree = json.ltlServiceScopeVo.districtDto.degree;
			// 得到该节点的坐标和派送范围
			var scopeList = json.ltlServiceScopeVo.serviceScopeList;
			//	当前点击行政区域名称
			var currentDistrictName = json.ltlServiceScopeVo.districtDto.name;
			// 验证该节点是否符合条件和是否有营业网点
			baseinfo.ltlServiceScope.verificationServiceScope(currentDistrictName,scopeList,seqName,_degree);
		}
	});
}

/**
 * =============================================Model=============================================
 */

/**
*行政区域界面数据模型定义
*/
Ext.define('Foss.baseinfo.ltlServiceScope.DistrictModel',{
	extend:'Ext.data.Model',
	fields:[
		//ID
		{name:'id',type:'string'},
		//行政区域编码
		{name:'code',type:'string'},
		//区域全称
		{name:'name',type:'string'},
		//简称
		{name:'simpleName',type:'string'},
		//可用名称
		{name:'availableName',type:'string'},
		//上级行政区域编码
		{name:'parentDistrictCode',type:'string'},
		//上级行政区域名称
		{name:'parentDistrictName',type:'string'},
		//虚拟行政区域
		{name:'virtualDistrictId',type:'string'},
		//行政区域级别
		{name:'degree',type:'string'},
		//创建时间
		{name:'createTime',type:'date'},
		//最后修改时间
		{name:'modifyTime',type:'date'},
		//是否启用
		{name:'active',type:'string'},
		//创建人
		{name:'createUserCode',type:'string'},
		//更新人
		{name:'modifyUserCode',type:'string'},
		//更新人
		{name:'isHotCity',type:'string'},
		//后缀
		{name:'regionsuffix',type:'string'},
		//部门服务区坐标编号
		{name:'depCoordinate',type:'string'},
		//派送区坐标编号
		{name:'deliveryCoordinate',type:'string'}

	]
});

/**
 * =============================================Store=============================================
 */
//左侧面板-定义行政区域TreeStore
Ext.define('Foss.baseinfo.ltlServiceScope.DistrictTreeStore',{
  	extend: 'Ext.data.TreeStore',
  	async:true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
  	root: {
		//根的文本信息
		text:baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.china'),//中国
		//设置根节点的ID
		id : '100000',
		//根节点是否展开
		expanded: true
	},
	//设置一个代理，通过读取内存数据
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryByParentDistrictCode.action"),//公共查询行政区域方法
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	},
  	sorters : [ {
		property : 'code',
		direction : 'ASC'
	} ],
	
	//节点参数
  	nodeParam: 'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode'
	
});    

/**
 * =============================================左侧面板-定义行政区域查询PANEL=============================================
 */
Ext.define('Foss.baseinfo.ltlServiceScope.DistrictSearchForm',{
	extend:'Ext.form.Panel',  
    height:80,
    layout:{
		type:'table',
		columns: 2
	},
	searchDistrictByName : function(){
		var me = this;
		var text = me.getForm().findField('cityName').getValue();
		if(Ext.isEmpty(text.trim())){
			baseinfo.showWoringMessage(baseinfo.ltlServiceScope.i18n('foss.baseinfo.pleaseEnterQuery'));//请输入查询信息！
			return;
		}
		var params = {
			"ltlServiceScopeVo.districtDto.name":text//传参
		};
		Ext.Ajax.request({
			url :baseinfo.realPath('queryDistrictByName.action'),
			params:params,
			//async:false,
			success : function(response) {   
				json = Ext.decode(response.responseText);
		    	//得到行政区域全路径，从顶级区域到查询区域
				var seq = json.ltlServiceScopeVo.districtDto.fullCodePath;
				//得到行政区域名称全路径，从顶级区域到查询区域
				var seqName = json.ltlServiceScopeVo.districtDto.fullNamePath;
				//得到行政区域级别
				var _degree = json.ltlServiceScopeVo.districtDto.degree;
				if(Ext.isEmpty(seq)){//如果没有查到，则展开根结点(提示用户没有这个信息)，
					baseinfo.showWoringMessage(baseinfo.ltlServiceScope.i18n('foss.baseinfo.queryNoResult'));//查询无结果
					return;
				}else{
					me.up('panel').getDistrictTree().selectPath(seq,'id','.');//再按第一个路径展开
				}
				
				// 得到该节点的坐标和派送范围
				var scopeList = json.ltlServiceScopeVo.serviceScopeList;
				// 当前查询行政区域名称
				var currentDistrictName = json.ltlServiceScopeVo.districtDto.name;
				// 验证该节点是否符合条件和是否有营业网点
				baseinfo.ltlServiceScope.verificationServiceScope(currentDistrictName,scopeList,seqName,_degree);
			}
		});
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			cls:'yellow_button',
			text : baseinfo.ltlServiceScope.i18n('foss.baseinfo.query'),//查询
			handler : function() {
				me.searchDistrictByName();
			}
		}];
		me.items = [{
			xtype : 'textfield',
			fieldLabel : baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.cityName'),//城市名称
			labelPad : 4,
			labelWidth : 70,
			width:240,
			name : 'cityName',
		    blankText : baseinfo.ltlServiceScope.i18n('foss.baseinfo.pleaseEnterQuery')//请输入查询信息！
		}];
		me.callParent([cfg]);
	}
});

/**
 * =============================================左侧面板-定义行政区域Tree=============================================
 */
Ext.define('Foss.baseinfo.ltlServiceScope.DistrictTree',{
    extend:'Ext.tree.Panel',
    margin: false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight',
    autoScroll: false,
    animate: false,
    useArrows: true,
    frame: false,
    rootVisible: true,
    // 树要给高度才有滚动条
    height: 750,
    columnWidth: 1,
    store: Ext.create('Foss.baseinfo.ltlServiceScope.DistrictTreeStore'),
    defaults: {
		margin:'5 5 5 5'
	},
  	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
	    //监听鼠标事件
	    me.listeners = {
			// 左键单击
			itemclick : function(node, record, item, index, e) {
				// 阻止浏览器默认行为处理事件
				e.preventDefault();
				baseinfo.ltlServiceScope.treeNodeOperator(me, record);
			},
			// 右键单击
			itemcontextmenu : function(node, record, item, index, e) {
				e.preventDefault();
			}
		};
		me.callParent([cfg]);
	}
});  

/**
 * =============================================查询面板，包括查询输入框，查询按钮PANEL=============================================
 */
Ext.define('Foss.baseinfo.ltlServiceScope.DistrictLeftPannel',{
	extend:'Ext.panel.Panel',
	title: baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.deliveryArea'),//派送区域
	width:275,
    height:930,
	frame: true,
	defaultType : 'textfield',
	//定义城市查询框
	districtSearchForm: null,
	getDistrictSearchForm: function(){
		if(this.districtSearchForm == null){
			this.districtSearchForm = Ext.create('Foss.baseinfo.ltlServiceScope.DistrictSearchForm');
		}
		return this.districtSearchForm;
	},
	//定义组织树方法
	districtTree: null,
	getDistrictTree: function(){
		if(this.districtTree == null){
			this.districtTree = Ext.create('Foss.baseinfo.ltlServiceScope.DistrictTree');
		}
		return this.districtTree;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getDistrictSearchForm(),me.getDistrictTree()];  
		me.callParent([cfg]);
	}
});


/**
 * =============================================地图信息PANEL=============================================
 */
Ext.define('Foss.baseinfo.ltlServiceScope.GisRightPanel', {
	extend:'Ext.panel.Panel', 
	title : baseinfo.ltlServiceScope.i18n('foss.baseinfo.ltlServiceScope.map'),//地图
	frame:true,
	id:'Foss_baseinfo_ltlServiceScope_GisRightPanel_Id',
	width:735,
	height:760,
	layout:'auto',
	//定义mask 罩
	myMask:null,
	getMyMask:function(){
		if(Ext.isEmpty(this.myMask)){
			this.myMask = new Ext.LoadMask(this, {msg:"Please wait..."});
		}
		return this.myMask;
	},
	 
	constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [{
				xtype: 'container',
				height:700,
				width:700,
				listeners: {
					//组件渲染之后
					afterrender: function(field) {
						// 延迟2秒加载地图，否则打开页面无法获取地图所需的js，报资源找不到异常
						Ext.Function.defer(function(){
							var aa = new DPMap.MyMap('VIEW', field.getId(),{center : "上海市", zoom : "STREET" },
								function(map) {
								baseinfo.ltlServiceScope.coverFeature = DMap.CoverFeature(map,{isAddable:false});
							})
							
						}, 2000);
					}
				}
			}];
			me.callParent([cfg]);
	}
});



/**
 * @description 零担服务范围主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-ltlServiceScope_content')) {
		return;
	};
	var districtLeftPanel = Ext.create('Foss.baseinfo.ltlServiceScope.DistrictLeftPannel');//派送区域树查询panel
	var gisRightPanel = Ext.create('Foss.baseinfo.ltlServiceScope.GisRightPanel');//电子地图信息PANEL
	Ext.getCmp('T_baseinfo-ltlServiceScope').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-ltlServiceScope_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout:'column',
		//派送区域树查询panel
		getDistrictLeftPanel : function() {
			return districtLeftPanel;
		},
		//电子地图信息PANEL
		getGisRightPanel : function() {
			return gisRightPanel;
		},
		items : [districtLeftPanel,gisRightPanel] 
	}));
	
});



