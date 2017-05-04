//常量
flag=true;
//用户所选择的节点对象
common.resourceObject=new Ext.util.HashMap();
//所有可能选择部门编码列表
common.resourceKeyValue=new Ext.util.HashMap();
//选中的部门编码列表
common.resourceKeyValueChoosed=new Ext.util.HashMap();
//常规业务指标
common.indicators=new Ext.util.HashMap();
//新业务指标
common.newindicators=new Ext.util.HashMap();
//应用数据指标
common.applicationIndicators=new Ext.util.HashMap();
//查询响应超时
common.timeout=600000;

//定义"监控树"
Ext.define('Foss.baseinfo.monitorOrgTree.LeftPanle',{
	extend: 'Ext.panel.Panel',
	frame: true,
	hidden : false,
	width : 300,
	autoScroll :false,
	defaultType : 'textfield',
	layout:'column',
	defaults: {
		readOnly : true,
		anchor: '90%',
		columnWidth: 0.99
	},
	bindData : function(record){
		//绑定表格数据到表单上
		//this.loadRecord(record);
	},

	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			// 权限树：
			Ext.create("Foss.baseinfo.monitorOrgTree.ResourceTree")
	    ];
	    me.callParent([cfg]);
	}
});

/**
 * ==============================================================================
 * 左边模块：
 */
Ext.define('Foss.baseinfo.monitorOrgTree.ResourceModel', {
    extend: 'Ext.data.Model',
    fields: [
      // 权限编码
      {name: 'code', type: 'string'},
      // 权限名称
      {name: 'name', type: 'string'},
      // 权限入口URI
      {name: 'entryUri', type: 'string'},
      // 功能层次
      {name: 'resLevel', type: 'string'},
      // 上级权限-编码
      {name: 'parentResCode', type: 'string'},
      // 上级权限-名称
      {name: 'parentResName', type: 'string'},
      // 显示顺序
      {name: 'displayOrder', type: 'string'},
      // 是否权限检查
      {name: 'checked', type: 'string'},
      // 权限类型
      {name: 'resType', type: 'string'},
      // 是否叶子结点
      {name: 'leafFlag', type: 'string'},
      // 图标的CSS样式
      {name: 'iconCls', type: 'string'},
      // 节点的CSS样式
      {name: 'cls', type: 'string'},
      // 权限描述
      {name: 'notes', type: 'string'}
    ]
});

//定义treeStore
Ext.define('Foss.baseinfo.monitorOrgTree.TreeStore',{
    extend: 'Ext.data.TreeStore',
    storeId: 'Foss_baseinfo_monitorOrgTree_TreeStore_Id',
    root: {
        text:'全国',
        id : '',
        checked: false,
        expanded: true
    },
    
    //设置一个代理，通过读取内存数据
    proxy: {
        type : 'ajax',
        actionMethods : 'POST',
        url : common.realPath("loadMonitorOrgTree.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
    },
    nodeParam: 'vo.parentOrgCode'
});


//checked所有父结点
common.checkParent = function(node,checked){
	var parentNode = node.parentNode;
	if(parentNode){
		if(parentNode.data.checked==false){
			common.resourceKeyValue.add(parentNode.data.id,parentNode.data.text);
			common.resourceKeyValueChoosed.add(parentNode.data.id,parentNode.data.text);			
			parentNode.set('checked',true);
		}
		common.checkParent(parentNode,checked);
	}
};

//定义"权限树型结构"
Ext.define('Foss.baseinfo.monitorOrgTree.ResourceTree',{
   id: 'Foss_baseinfo_monitorOrgTree_ResourceTree_Id',
   extend:'Ext.tree.Panel',
   margin: false,
   autoScroll: true,
   animate: false,
   useArrows: true,
   frame: false,
   rootVisible: true,
   columnWidth: 1,
   height: 720,
   store: Ext.create('Foss.baseinfo.monitorOrgTree.TreeStore'),
   defaults: {
       margin:'5 5 5 5'
   },
   bindData : function(me){
		
   },
   /**
	 * 父节点选中时，选择所有子节点
	 */
	selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(Ext.isEmpty(a_code)&&node.data.text=='全国'){
			a_code='DIP';
		}
		if(checked){			
			common.resourceKeyValue.add(a_code,node.data.text);
			common.resourceKeyValueChoosed.add(a_code,node.data.text);
		}else{
			common.resourceKeyValueChoosed.removeAtKey(a_code);
		}
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if(Ext.isEmpty(a_code)&&node.data.text=='全国'){
			a_code='DIP';
		}
		var id=Ext.getCmp('allTab').getActiveTab().itemId;
		if (checked == true) {
			//common.checkParent(node, true);
			this.selectChildFunction(node, true);			
			common.resourceKeyValue.add(a_code,node.data.text);
			common.resourceKeyValueChoosed.add(a_code,node.data.text);
			common.resourceObject.add(node.internalId,node);
		} else {
			this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			common.resourceKeyValueChoosed.removeAtKey(a_code);
			common.resourceObject.removeAtKey(node.internalId);
		}
		if(common.resourceKeyValueChoosed.getCount( )>5&&id=='p1'){
		 Ext.MessageBox.alert("提示",'最多只能选择5个部门!');
		 node.set("checked", false);
		 common.resourceKeyValue.removeAtKey(a_code);
		 common.resourceKeyValueChoosed.removeAtKey(a_code);
		 common.resourceObject.removeAtKey(node.internalId);
		 return ;
		 }
	},
	itemExpand : function(node) {
	},
   constructor: function(config){
       var me = this,
           cfg = Ext.apply({}, config);

       //监听鼠标事件
       me.listeners = {
           //左键单击
           itemclick:function(node,record,item,index,e){
               //阻止浏览器默认行为处理事件
               e.preventDefault();
               // 处理点击树结点事件：
               me.onclickTreeNode(me,record);
           },
           //右键单击
           itemcontextmenu:function(node,record,item,index,e){
               e.preventDefault();
           },
           //结点选择事件
           selectionchange:function( record, e ){
           }
       }
       // 监听鼠标事件
		me.listeners = {
			itemexpand : function(node){
				me.itemExpand(node);
			},
			// 当树结点被选择（checked)
			checkchange : me.checkChange
		};
       me.callParent([cfg]);
   }
});

//模型
Ext.define('Foss.monitor.model.DayTotal', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id',type:'string'},
		{name: 'allCountry',type:'string'},
		{name: 'dedivery',type:'string'},
		{name: 'selfFetch',type:'string'},
		{name: 'elsePro',type:'string'}
	]
});

//发车计划明细数据源
Ext.define('Foss.monitor.store.DayTotal',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.monitor.model.DayTotal',
	pageSize:15,
	proxy: {
		type: 'ajax',
		url:'',
		actionMethods:'post',
		reader: {
			type: 'json',
			root: 'vo',
			totalProperty : 'totalCount'
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//按日查询结果
Ext.define('Foss.base.dayGrid.GridPanel',{
	extend: 'Ext.grid.Panel',
	title:'动态表格',
	collapsible: true,
	collapsed :true, 
	animCollapse: true,
	columnLines:true,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	columns: [	],
	orgCode:null,
	getOrgCode:function(){
		return this.orgCode;
	},
	setOrgCode:function(orgCode){
		this.orgCode=orgCode;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}	
});

//常规业务
Ext.define('Foss.baseinfo.searchBussiness.FormPanel',{
	extend: 'Ext.form.Panel',	
	layout:'column',	
	bodyStyle:'padding:5px 5px 0 5px',
	fieldDefaults: {
		msgTarget: 'side',
		labelWidth: 90,
		margin:'5 5 5 0'
	},
	defaults: {
		anchor: '99%'
	}	   
});

//常规业务
Ext.define('Foss.baseinfo.newBussiness.Panel',{
	extend: 'Ext.panel.Panel',	
	layout:'auto',	
	//collapsible :true,
	bodyStyle:'padding:5px 5px 0 5px',
	items:[]
})

//标题面板
Ext.define('Foss.baseinfo.title.Panel',{
	extend: 'Ext.form.Panel',
	layout:'auto',	
	bodyStyle:'padding:5px 5px 0 5px'
	})

//监控TAB
Ext.define('Foss.base.monitor.TabPanel',{
	extend:'Ext.tab.Panel',
	cls:"innerTabPanel",
	id:'allTab',
	bodyCls:"overrideChildLeft",
	activeTab:0,
	autoScroll:false,
	frame: false,
	constructor: function(config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [{
				title: '常规业务',
				tabConfig:{width:100},
				frame:false,
				itemId: 'p1',
				items: Ext.create('Foss.baseinfo.newBussiness.Panel',{id:'Foss_baseinfo_normalBussiness_Panel_ID'})
			 },{
				title: '新功能业务',
				itemId: 'p2',
				frame:false,
				tabConfig:{width:100},
				items:  Ext.create('Foss.baseinfo.newBussiness.Panel',{id:'Foss_baseinfo_newBussiness_Panel_ID'})				
			},{
				title: '应用数据',
				itemId: 'p3',
				frame:false,
				tabConfig:{width:100},				
				items:Ext.create('Foss.baseinfo.newBussiness.Panel',{id:'Foss_baseinfo_applicationBussiness_Panel_ID'})		
			}
		];
		me.callParent([cfg]);
	},
	listeners:{
		tabchange:function( tabPanel, newCard, oldCard,eOpts ){
			var nodes=common.resourceObject
			if(newCard.itemId=='p2'){
				//if(Ext.isEmpty(Ext.getCmp('Foss_baseinfo_newBussiness_Panel_ID')))
				
			}else if(newCard.itemId=='p3'){
				
			}else if(newCard.itemId=='p1'){
				
				nodes.each(function(key, value, length){
					 value.set('checked',false);
				});
				common.resourceKeyValueChoosed.clear();
				common.resourceKeyValue.clear();
				common.resourceObject.clear();
			}
		}
		
	}
});

//右面板
Ext.define('Foss.baseinfo.monitor.PanleRight',{
	extend: 'Ext.panel.Panel',
	frame: true,
	width : 700,	
	autoScroll :true,
	hidden: false,
	defaultType : 'textfield',
	layout:'column',
	defaults: {
		readOnly : true,
		anchor: '90%',
		columnWidth: 0.99
	},
	bindData : function(record){
		//绑定表格数据到表单上
		//this.loadRecord(record);
	},

	initComponent: function(config){
	  var me = this,
				cfg = Ext.apply({}, config);
	  var tabPanel = Ext.create('Foss.base.monitor.TabPanel'); 
	  me.items = [tabPanel];
	  me.callParent([cfg]);
	}
});

//常规业务全选
common.monitorIndex.checkAllNormalSearchForm=function(val){
	var tempForm = common.monitorIndex.searchNoramlForm.getForm(); 
	common.indicators.each(function(key, value, length){
		if(key=='dayTotal'||key=='monthTotal'){
			
		}else{			
			tempForm.findField(key).setValue(val);
		}
	});
}

//新业务全选
common.monitorIndex.checkAllNewSearchForm=function(val){
	var tempForm = common.monitorIndex.searchNewForm.getForm(); 
	common.newindicators.each(function(key, value, length){
		if(key=='dayTotal'||key=='monthTotal'){
			
		}else{			
			tempForm.findField(key).setValue(val);
		}
	});
}

//应用数据全选543
common.monitorIndex.checkAllApplicationSearchForm=function(val){
	var tempForm = common.monitorIndex.searchApplicationForm.getForm(); 
	common.applicationIndicators.each(function(key, value, length){
		if(key=='dayTotal'||key=='monthTotal'){
			
		}else{			
			tempForm.findField(key).setValue(val);
		}
	});
}

//自动查询指标组合----根据某种类型 操作tab-------------------------------------------------------------------------------------
common.monitorIndex.autoQuerySearchCondition=function(bussinessType){
	var queryUrl=common.realPath('queryIndicatorGroupByCategory.action');
	var params={'vo.bussinessType':bussinessType};
	Ext.Ajax.request({
		url : queryUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//指标列表
			var useableIndicators=json.vo.useableIndicators;
			//常规
			if('COMMON'==bussinessType||Ext.isEmpty(bussinessType)){
				common.monitorIndex.makeNormalCondition(useableIndicators);
			}
			//新业务
			else if('NEW'==bussinessType){
					common.monitorIndex.makeNewBussinessCondition(useableIndicators);
			}
			//应用数据
			else if('APPLICATION'==bussinessType){
				common.monitorIndex.makeApplicationCondition(useableIndicators);
			}
		},exception : function(response) {
			var json = Ext.decode(response.responseText);			
		}
	});
}

//常规业务查询面板
common.monitorIndex.makeNormalCondition=function(useableIndicators){
	//构建列表checkbox
	if(!Ext.isEmpty(useableIndicators)){
		var indicatorStr="{boxLabel:'全选',name:'checkAll',xtype:'checkbox',inputValue: 'Y',columnWidth:.9," +
				"listeners:{" +
					"change :function(field,newValue, oldValue,eOpts ){" +								
							"common.monitorIndex.checkAllNormalSearchForm(newValue);" +							
					"}" +
				"}" +
				"},";
		for(var i=0;i<useableIndicators.length;i++){					
			var indicator= useableIndicators[i];
			//指标列表
			common.indicators.add(indicator,indicator);
			indicatorStr=indicatorStr+"{boxLabel:'"+indicator+"',name:'"+indicator+"',xtype:'checkbox',inputValue: 'Y',columnWidth:.3}";
			if(i==useableIndicators.length-1){
				//什么都不做
			}else{
				indicatorStr=indicatorStr+",";
			}
		}
	}
	//查询按钮---常规业务
	var tail=",{border : false,xtype : 'container',columnWidth:0.9,layout:'column',defaults: {margin:'5 0 5 10'},"
		+"items : [{border : false,columnWidth:.6,html: '&nbsp;'},{boxLabel  : '按日统计',name: 'total',xtype:'radiofield',inputValue: 'dayTotal',columnWidth:.15,checked:true},"
		+"{boxLabel:'按月统计',name:'total',xtype:'radiofield',inputValue: 'monthTotal',columnWidth:.15},"
		+"{columnWidth:.1,xtype : 'button',text: '查询',cls:'yellow_button',handler: function() {"
		+"if(this.up('form').getForm().isValid( )){"										
		+"common.monitorIndex.queryMonitorData(this);}}}]}";
		//完整的查询面板内容
		indicatorStr=indicatorStr+tail;				
		//拼接表单
		common.monitorIndex.searchNoramlForm=Ext.create('Foss.baseinfo.searchBussiness.FormPanel',{
					id:'Foss_baseinfo_searchNormalBussiness_FormPanel_Id',
					items: [{				
							xtype:'fieldset',
							title: '常规业务',
							defaultType: 'textfield',
							layout: 'column',
							defaults: {
								anchor: '100%'
							},
							items: eval('['+indicatorStr+']')
						  }
						]
				});
		//将查询面板进行填充
		var tempForm = Ext.getCmp('Foss_baseinfo_normalBussiness_Panel_ID')
		var items=tempForm.items;	
		 //插入动态表格
		tempForm.insert(items.length,common.monitorIndex.searchNoramlForm);
}

//新业务查询面板
common.monitorIndex.makeNewBussinessCondition=function(useableIndicators){
	//构建列表checkbox
	if(!Ext.isEmpty(useableIndicators)){
		var indicatorStr="{boxLabel:'全选',name:'checkAll',xtype:'checkbox',inputValue: 'Y',columnWidth:.9," +
		"listeners:{" +
		"change :function(field,newValue, oldValue,eOpts ){" +								
		"common.monitorIndex.checkAllNewSearchForm(newValue);" +							
		"}" +
		"}" +
		"},";
		for(var i=0;i<useableIndicators.length;i++){					
			var indicator= useableIndicators[i];
			//指标列表
			common.newindicators.add(indicator,indicator);
			indicatorStr=indicatorStr+"{boxLabel:'"+indicator+"',name:'"+indicator+"',xtype:'checkbox',inputValue: 'Y',columnWidth:.3}";
			if(i==useableIndicators.length-1){
				//什么都不做
			}else{
				indicatorStr=indicatorStr+",";
			}
		}
	}
	//查询按钮
	var tail=",{border : false,xtype : 'container',columnWidth:0.9,layout:'column',defaults: {margin:'5 0 5 10'},"
		+"items : [" +
		"{border : false,columnWidth:.9,html: '&nbsp;'},"		
		+"{columnWidth:.1,xtype : 'button',text: '查询',cls:'yellow_button',handler: function(){ "
		 +"if(this.up('form').getForm().isValid( )){"										
						+" common.monitorIndex.queryNewBussinessMonitorData(this);" 
						+"}" 
				+"}" 
				+"}" 
		   +"]" 
		+"}";
	//完整的查询面板内容
	indicatorStr=indicatorStr+tail;	
	console.log(indicatorStr);
	//拼接表单
	common.monitorIndex.searchNewForm=Ext.create('Foss.baseinfo.searchBussiness.FormPanel',{
		id:'Foss_baseinfo_searchNewBussiness_FormPanel_Id',
		items: [{				
			xtype:'fieldset',
			title: '新功能业务',
			defaultType: 'textfield',
			layout: 'column',
			defaults: {
				anchor: '100%'
			},
			items: eval('['+indicatorStr+']')
		}
		]
	});
	//将查询面板进行填充
	var tempForm = Ext.getCmp('Foss_baseinfo_newBussiness_Panel_ID')
	var items=tempForm.items;	
	//插入动态表格
	tempForm.insert(items.length,common.monitorIndex.searchNewForm);
}

//业务数据查询面板
common.monitorIndex.makeApplicationCondition=function(useableIndicators){
	//构建列表checkbox
	if(!Ext.isEmpty(useableIndicators)){
		var indicatorStr="{boxLabel:'全选',name:'checkAll',xtype:'checkbox',inputValue: 'Y',columnWidth:.9," +
		"listeners:{" +
		"change :function(field,newValue, oldValue,eOpts ){" +								
		"common.monitorIndex.checkAllApplicationSearchForm(newValue);" +							
		"}" +
		"}" +
		"},";
		for(var i=0;i<useableIndicators.length;i++){					
			var indicator= useableIndicators[i];
			//指标列表
			common.applicationIndicators.add(indicator,indicator);
			indicatorStr=indicatorStr+"{boxLabel:'"+indicator+"',name:'"+indicator+"',xtype:'checkbox',inputValue: 'Y',columnWidth:.3}";
			if(i==useableIndicators.length-1){
				//什么都不做
			}else{
				indicatorStr=indicatorStr+",";
			}
		}
	}
	//查询按钮,
	var tail=",{border : false,xtype : 'container',columnWidth:0.9,layout:'column',defaults: {margin:'5 0 5 10'},"
		+"items : [" +
		"{border : false,columnWidth:.5,html: '&nbsp;'},{boxLabel  : '按日统计',columnWidth:.2,name: 'total',xtype:'radiofield',inputValue: 'dayTotal',checked:true},{boxLabel:'按月统计',columnWidth:.2,name:'total',xtype:'radiofield',inputValue: 'monthTotal'},"		
		+"{columnWidth:.1,xtype : 'button',text: '查询',hidden:!common.monitorIndex.isPermission('monitorIndex/monitorQueryButton'),cls:'yellow_button',handler: function(){ "
		+"if(this.up('form').getForm().isValid( )){"										
		+" common.monitorIndex.queryApplicationBussinessMonitorData(this);" 
		+"}" 
		+"}" 
		+"}" 
		+"]" 
		+"}";
	//完整的查询面板内容
	indicatorStr=indicatorStr+tail;	
	console.log(indicatorStr);
	//拼接表单
	common.monitorIndex.searchApplicationForm=Ext.create('Foss.baseinfo.searchBussiness.FormPanel',{
		id:'Foss_baseinfo_searchApplicationBussiness_FormPanel_Id',
		
		items: [{				
			xtype:'fieldset',
			title: '应用数据',
			defaultType: 'textfield',
			layout: 'column',
			width:621,
			defaults: {
				anchor: '100%'
			},
			items: eval('['+indicatorStr+']')
		}
		]
	});
	//将查询面板进行填充
	var tempForm = Ext.getCmp('Foss_baseinfo_applicationBussiness_Panel_ID')
	var items=tempForm.items;	
	//插入动态表格
	tempForm.insert(items.length,common.monitorIndex.searchApplicationForm);
}




//查询常规监控指标
common.monitorIndex.queryMonitorData = function(btn){
	 if(Ext.isEmpty(common.resourceKeyValueChoosed)||common.resourceKeyValueChoosed.getCount( )<1){
		 Ext.MessageBox.alert("提示",'请选择需查询的部门!');
		 return ;
	 }
	 //限制所查询的部门最多为5个
	 if(common.resourceKeyValueChoosed.getCount( )>5){
		 Ext.MessageBox.alert("提示",'最多只能选择5个部门!');
		 return ;
	 }
	 
	//清空查询结果表格
	var tempForm = Ext.getCmp('Foss_baseinfo_normalBussiness_Panel_ID');
 	 var len = tempForm.items.length;
 	 for(var i=0 ;i<len;i++){
 	 	if(tempForm.items.length>1){
 	 		tempForm.remove(tempForm.items.items[1],true); 	 		 		
 	 	} 	 		
      }		 
	//查询表头
	var headerUrl=common.realPath('queryCommonIndicatorHeader.action');
	var indicatorGroups=new Array();
	//获取指标	 
	 var choosedFields=common.monitorIndex.searchNoramlForm.getValues();
	 //将选择指标加入队列
	 for(var field in choosedFields){
		 //if('dayTotal'==field||'monthTotal'==field||'checkAll'==field)
	 	if('total'==field||'checkAll'==field)
			 continue;
		 indicatorGroups.push(Ext.String.trim(field));
	 }
	 //校验是否选择指标
	 if(Ext.isEmpty(indicatorGroups)){
		 Ext.MessageBox.alert("提示",'请选择需查询的指标!');
		 return ;
	 }
	 btn.disable(true);
	 var task = new Ext.util.DelayedTask(function(){
		 btn.enable(true);
		});
	 task.delay(5000);
	 //查询指标列表
	var params={'vo.indicatorGroups':indicatorGroups};
	Ext.Ajax.request({
		url : headerUrl,
		params:params,
		sync : true, //同步
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//表头列表
			var headers=json.vo.headers;
			//表头js
			var headerJs="";
			//动态model fields
			var fields="{name: 'monitorDate',type:'string'},{name: 'monitorTimeRange',type:'string'},";
			if(!Ext.isEmpty(indicatorGroups)){
				
				for(var i=0;i<indicatorGroups.length;i++){
					//表头1
					var header_1=indicatorGroups[i];
					//开始构建表头
					headerJs =headerJs + "{text:'"+header_1+"' ,menuDisabled:true,"
					
					var header_2=new Array();
					//表头2
					header_2=headers[header_1];
					if(!Ext.isEmpty(header_2)){
						var headerSecJs='columns: [';
						var headerSecColumnJs='';						
						for(var j=0;j<header_2.length;j++){
							headerSecColumnJs=headerSecColumnJs+"{text : '"+header_2[j].indicatorName+"',dataIndex: '"+header_2[j].indicatorCode+"',menuDisabled:true,width : 100,sortable : false}";
							//非最后一行，则加上逗号
							if(j==header_2.length-1){
								//什么都不做
							}else{
								headerSecColumnJs=headerSecColumnJs+',';
							}
							//动态列表
							fields=fields+"{name: '"+header_2[j].indicatorCode+"',type:'string'},"
						}
						headerSecJs=headerSecJs+headerSecColumnJs+']'
					}
					//加入结束符
					headerJs=headerJs+headerSecJs+"}"
					//非最后一行，则加上逗号
					if(i==indicatorGroups.length-1){
						//什么都不做
					}else{
						headerJs=headerJs+',';
					}
				}
			}			
			//动态model
			fields=fields.substring(0,fields.length-1);
			//动态模型
			Ext.define('Foss.monitorIndex.model.MonitorData', {
					extend: 'Ext.data.Model',
					// 定义字段
					fields:eval('['+fields+']')
				});
			//查询数据源
			Ext.define('Foss.monitorIndex.store.MonitorData',{
				extend: 'Ext.data.Store',
				model: 'Foss.monitorIndex.model.MonitorData',
				orgCode:null,
				autoLoad:false,
				analysisType:null,				
				proxy: {
					type: 'ajax',
					url:common.realPath('queryDailyCommonIndicatorData.action'),
					actionMethods:'post',
					reader: {
						type: 'json',
						root: 'vo.datas'
					},
					timeout: common.timeout
				},
				constructor: function(config){
					var me = this,
						cfg = Ext.apply({}, config);
					me.listeners = {
						beforeload : function(store, operation, eOpts) {
							var queryParams={'vo.indicatorGroups':indicatorGroups,'vo.orgCode':this.orgCode,'vo.analysisType':this.analysisType,'vo.bussinessType':'COMMON'};
							Ext.apply(operation, {
								params : queryParams
							});
						}
					};
					me.callParent([cfg]);
				}
			});
			//根据选择的区域,			
			//根据选择的“按日统计”,"按月统计"进行查询		
//			common.monitorIndex.searchNoramlForm.getForm().findField('dayTotal').getValue();
//			if(!common.monitorIndex.searchNoramlForm.getForm().findField('dayTotal').getValue()
//					&&!common.monitorIndex.searchNoramlForm.getForm().findField('monthTotal').getValue()){
//				 Ext.MessageBox.alert("提示",'请选择统计方式[按日统计|按月统计]!');
//				return;
//			}
			 var i=0;
			//按日统计-不同情况生成表格
			 if(common.monitorIndex.searchNoramlForm.getForm().findField('total').getGroupValue()=='dayTotal'){
				 var titleHeader='按日统计'
					 common.monitorIndex.dynamicTitlePanel(titleHeader,'Foss_baseinfo_normalBussiness_Panel_ID');
				//组织编码
				var map = new Ext.util.HashMap();
				map=common.resourceKeyValueChoosed;
				map.each(function(key, value, length){
					var tempStore=Ext.create('Foss.monitorIndex.store.MonitorData');
					//动态创建表格
					common.monitorIndex.dynamicMonitorTable(headerJs,key,tempStore,'monitorTimeRange','Foss_baseinfo_normalBussiness_Panel_ID',null,'COMMON',null,i);
					i++;
				}); 
			 }
			//按日统计-不同情况生成表格
			 if(common.monitorIndex.searchNoramlForm.getForm().findField('total').getGroupValue()=='monthTotal'){
				 var titleHeader='按月统计'
					 common.monitorIndex.dynamicTitlePanel(titleHeader,'Foss_baseinfo_normalBussiness_Panel_ID');
					//组织编码
					var map = new Ext.util.HashMap();
					map=common.resourceKeyValueChoosed;
					map.each(function(key, value, length){
						var tempStore=Ext.create('Foss.monitorIndex.store.MonitorData');
						//动态创建表格
						common.monitorIndex.dynamicMonitorTable(headerJs,key,tempStore,'monitorDate','Foss_baseinfo_normalBussiness_Panel_ID',null,'COMMON',null,i);
					    i++;
					}); 						
			 }	
			 var totlapanel=Ext.getCmp('Foss_baseinfo_normalBussiness_Panel_ID');
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
		}
	});
}

//查询新业务监控指标
common.monitorIndex.queryNewBussinessMonitorData = function(btn){
	//检验是否选择部门
	if(Ext.isEmpty(common.resourceKeyValueChoosed)||common.resourceKeyValueChoosed.getCount( )<1){
		Ext.MessageBox.alert("提示",'请选择需查询的部门!');
		return ;
	}	
	//清空查询结果表格
	var tempForm = Ext.getCmp('Foss_baseinfo_newBussiness_Panel_ID');
	var len = tempForm.items.length;
	for(var i=0 ;i<len;i++){
		if(tempForm.items.length>1){
			tempForm.remove(tempForm.items.items[1],true); 	 		 		
		} 	 		
	}		 
	//查询表头
	var headerUrl=common.realPath('queryNewIndicatorHeader.action');
	var indicatorGroups=new Array();
	//获取指标	 
	var choosedFields=common.monitorIndex.searchNewForm.getValues();
	//将选择指标加入队列
	for(var field in choosedFields){
		if('dayTotal'==field||'monthTotal'==field||'checkAll'==field)
			continue;
		indicatorGroups.push(Ext.String.trim(field));
	}
	//校验是否选择指标
	if(Ext.isEmpty(indicatorGroups)){
		Ext.MessageBox.alert("提示",'请选择需查询的指标!');
		return ;
	}		 
	btn.disable(true);
	 var task = new Ext.util.DelayedTask(function(){
		 btn.enable(true);
		});
	 task.delay(3000);
	//查询指标列表
	var params={'vo.indicatorGroups':indicatorGroups};
	Ext.Ajax.request({
		url : headerUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//表头列表
			var headers=json.vo.headers;
			//表头js
			var headerJs="";
			//动态model fields
			var fields="{name: 'monitorDate',type:'string'},{name: 'monitorTimeRange',type:'string'},";
			if(!Ext.isEmpty(indicatorGroups)){
				
				for(var i=0;i<indicatorGroups.length;i++){
					//表头1
					var header_1=indicatorGroups[i];
					//开始构建表头
					headerJs =headerJs + "{text:'"+header_1+"' ,menuDisabled:true,"
					
					var header_2=new Array();
					//表头2
					header_2=headers[header_1];
					if(!Ext.isEmpty(header_2)){
						var headerSecJs='columns: [';
						var headerSecColumnJs='';						
						for(var j=0;j<header_2.length;j++){
							headerSecColumnJs=headerSecColumnJs+"{text : '"+header_2[j].indicatorName+"',dataIndex: '"+header_2[j].indicatorCode+"',menuDisabled:true,width : 100,sortable : false}";
							//非最后一行，则加上逗号
							if(j==header_2.length-1){
								//什么都不做
							}else{
								headerSecColumnJs=headerSecColumnJs+',';
							}
							//动态列表
							fields=fields+"{name: '"+header_2[j].indicatorCode+"',type:'string'},"
						}
						headerSecJs=headerSecJs+headerSecColumnJs+']'
					}
					//加入结束符
					headerJs=headerJs+headerSecJs+"}"
					//非最后一行，则加上逗号
					if(i==indicatorGroups.length-1){
						//什么都不做
					}else{
						headerJs=headerJs+',';
					}
				}
			}			
			//动态model
			fields=fields.substring(0,fields.length-1);
			//动态模型
			Ext.define('Foss.monitorIndex.model.MonitorData', {
				extend: 'Ext.data.Model',
				// 定义字段
				fields:eval('['+fields+']')
			});
			//查询数据源
			Ext.define('Foss.monitorIndex.store.MonitorData',{
				extend: 'Ext.data.Store',
				model: 'Foss.monitorIndex.model.MonitorData',
				orgCode:null,
				pageSize:10,
				analysisType:null,
				bussinessType:null,
				orgCodes:null,
				proxy: {
					type: 'ajax',
					url:common.realPath('queryDailyNewIndicatorData.action'),
					actionMethods:'post',
					reader: {
						type: 'json',
						root: 'vo.datas',
						totalProperty : 'totalCount'
					},
					timeout: common.timeout
				},
				constructor: function(config){
					var me = this,
					cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},listeners: {
					beforeload : function(store, operation, eOpts) {
						var queryParams={'vo.indicatorGroups':indicatorGroups,'vo.orgCode':this.orgCode,'vo.bussinessType':'NEW','vo.orgCodes':this.orgCodes, 'totalCount':this.orgCodes.length};
						Ext.apply(operation, {
							params : queryParams
						});
					}
				}
			});
				//根据选择的区域
				//组织编码
				var map = new Ext.util.HashMap();
				map=common.resourceKeyValueChoosed;
				var tempStore=Ext.create('Foss.monitorIndex.store.MonitorData');
				tempStore.orgCodes=common.resourceKeyValueChoosed.getKeys( );				
				//动态创建表格
				common.monitorIndex.dynamicMonitorTable(headerJs,'',tempStore,'monitorTimeRange','Foss_baseinfo_newBussiness_Panel_ID',null,'NEW','new',null);					
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			
		}
	});
}

//查询应用数据监控指标
common.monitorIndex.queryApplicationBussinessMonitorData = function(btn){
	btn.disable(true);
	 var task = new Ext.util.DelayedTask(function(){
		 btn.enable(true);
		});
	 task.delay(3000);
	//检验是否选择部门
	if(Ext.isEmpty(common.resourceKeyValueChoosed)||common.resourceKeyValueChoosed.getCount( )<1){
		Ext.MessageBox.alert("提示",'请选择需查询的部门!');
		 btn.enable(true);
		return ;
	}	
	//清空查询结果表格
	var tempForm = Ext.getCmp('Foss_baseinfo_applicationBussiness_Panel_ID');
	var len = tempForm.items.length;
	for(var i=0 ;i<len;i++){
		if(tempForm.items.length>1){
			tempForm.remove(tempForm.items.items[1],true); 	 		 		
		} 	 		
	}		 
	//查询表头
	var headerUrl=common.realPath('queryApplicationIndicatorHeader.action');
	var indicatorGroups=new Array();
	//获取指标	 
	var choosedFields=common.monitorIndex.searchApplicationForm.getValues();
	//将选择指标加入队列
	for(var field in choosedFields){
		if('total'==field||'checkAll'==field)
			continue;
		indicatorGroups.push(Ext.String.trim(field));
	}
	//校验是否选择指标
	if(Ext.isEmpty(indicatorGroups)){
		Ext.MessageBox.alert("提示",'请选择需查询的指标!');
		 btn.enable(true);
		return ;
	}
	//排序
	  //Ext.Array.sort(indicatorGroups);	 
	//查询指标列表
	for(var k=0;k<indicatorGroups.length;k++){
		//指标
		var indicatorGroup =indicatorGroups[k];		
		var params={'vo.indicatorGroup':indicatorGroup};
		common.monitorIndex.queryApplicationAjax(headerUrl,params,choosedFields.total);
		common.monitorIndex.pause(100);
	}
	
	
}

common.monitorIndex.pause=function(millisecondi)
{
    var now = new Date();
    var exitTime = now.getTime() + millisecondi;
    while(true)
    {
        now = new Date();
        if(now.getTime() > exitTime) return;
    }
}

//AJAX请求
common.monitorIndex.queryApplicationAjax=function(headerUrl,params,total){
	Ext.Ajax.request({
		url : headerUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			//表头列表
			var headers=json.vo.appHeaders;
			//表头js
			var headerJs="";
			//动态model fields
			var fields="{name: 'monitorDate',type:'string'},{name: 'monitorTimeRange',type:'string'},";				
			if(!Ext.isEmpty(headers)){
				for(var key in headers){
					var keyValue = headers[key];
						//模型字段
						fields=fields+"{name: '"+key+"',type:'string'},";
						//表头JS
						headerJs =headerJs + "{text:'"+keyValue+"' ,menuDisabled:true,width : 80,sortable : false,dataIndex:'"+key+"'},";					
				}
				fields=fields.substring(0,fields.length-1);
				headerJs=headerJs.substring(0,headerJs.length-1);
			}						
			//动态模型
			Ext.define('Foss.monitorIndex.model.ApplicationMonitorData', {
				extend: 'Ext.data.Model',
				// 定义字段
				fields:eval('['+fields+']')
			});
			
			//查询数据源
			Ext.define('Foss.monitorIndex.store.ApplicationMonitorData',{
				extend: 'Ext.data.Store',
				model: 'Foss.monitorIndex.model.ApplicationMonitorData',
				analysisType:null,
				bussinessType:null,
				autoLoad:true,
				orgCodes:null,
				indicatorGroup:null,
				proxy: {
					type: 'ajax',
					url:common.realPath('queryDailyApplicationIndicatorData.action'),
					actionMethods:'post',
					reader: {
						type: 'json',
						root: 'vo.datas'
					},
					timeout: common.timeout
				},
				constructor: function(config){
					var me = this,
					cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},listeners: {
					beforeload : function(store, operation, eOpts) {
						//
						var queryParams={'total':total,'vo.indicatorGroup':this.indicatorGroup,'vo.bussinessType':'APPLICATION','vo.orgCodes':this.orgCodes};
						Ext.apply(operation, {
							params : queryParams
						});
					}
				}
			});
			//根据选择的区域
			//组织编码
			var map = new Ext.util.HashMap();
			map=common.resourceKeyValueChoosed;
			var tempStore=Ext.create('Foss.monitorIndex.store.ApplicationMonitorData');
			tempStore.orgCodes=common.resourceKeyValueChoosed.getKeys( );	
			//动态创建表格
			common.monitorIndex.dynamicMonitorTable(headerJs,'',tempStore,'monitorTimeRange','Foss_baseinfo_applicationBussiness_Panel_ID',json.vo.indicatorGroup,'APPLICATION',null,null);					
		},
		exception : function(response) { 
			var json = Ext.decode(response.responseText);
		}
	});
}

//创建标题面板
common.monitorIndex.dynamicTitlePanel = function(title,id){	
	var dynamicTitle = Ext.create('Foss.baseinfo.title.Panel',{
		title:title,
		margin : '25 0 0 0'		
	});
	//查询表单
	var tempForm = Ext.getCmp(id);
	var items=tempForm.items;	
	 //插入动态表格
	tempForm.insert(items.length,dynamicTitle);
}

//导出
common.monitorIndex.exportCommon=function(bussinessType,analysisType,orgCode,indicatorGroup){
	var actionUrl=common.realPath('exportData.action');	
	//选择的常规指标
	var choosedFields;
	//常规业务
	if(bussinessType=='COMMON'){
		 choosedFields=common.monitorIndex.searchNoramlForm.getValues();
	}
	//新业务
	else if(bussinessType=='NEW'){
		choosedFields=common.monitorIndex.searchNewForm.getValues();
	}//应用数据
	else if(bussinessType=='APPLICATION'){
		choosedFields=common.monitorIndex.searchApplicationForm.getValues();
	}	
	//收集指标的容器
	var indicatorGroups=new Array();
	//将选择指标加入队列
	 if(!Ext.isEmpty(choosedFields)){
		 for(var field in choosedFields){
			 if('total'==field||'dayTotal'==field||'monthTotal'==field||'checkAll'==field)
				 continue;
			 indicatorGroups.push(Ext.String.trim(field));
		 }
	 }
	 //校验是否选择指标
	 if(Ext.isEmpty(indicatorGroups)){
		 Ext.MessageBox.alert("提示",'请选择需查询的指标!');
		 return ;
	 }		 
	 //查询指标列表
	var queryParams={
						'vo.indicatorGroups':indicatorGroups,
						'vo.analysisType':analysisType,
						'vo.orgCode':orgCode,
						'vo.bussinessType':bussinessType,
						'vo.orgCodes':common.resourceKeyValueChoosed.getKeys(),
						'vo.indicatorGroup':indicatorGroup,
						'vo.total':choosedFields.total
					};
	
	if(!Ext.fly('downloadAttachFileForm')){
			    var frm = document.createElement('form');
			    frm.id = 'downloadAttachFileForm';
			    frm.style.display = 'none';
			    document.body.appendChild(frm);
		}
		Ext.Ajax.request({
		url:actionUrl,
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : queryParams,
		isUpload: true,
		exception : function(response,opts) {
			var result = Ext.decode(response.responseText);
			Ext.MessageBox.alert('提示',result.message);			
		},success : function(response,opts) {
			Ext.MessageBox.alert('提示','导出成功');			
		}		
		});
}

//动态创建常规表格-----创建grid
common.monitorIndex.dynamicMonitorTable=function(headerJs,orgCode,tempStore,dataIndex,tempFormId,indicator,bussinessType,newObj,i){	
	tempStore.orgCode=orgCode;
	tempStore.analysisType=dataIndex;
	tempStore.indicatorGroup=indicator;
	var dockedItems=null;
	if(null!=newObj){
		var dockedItems="[{xtype : 'standardpaging',store : tempStore,dock : 'bottom'}]";
	}
	//,plugins : {ptype : 'pagesizeplugin',alertOperation : true,
	//sizeArray : [['2', 2], ['5', 5], ['10', 10], ['20', 20]],maximumSize : 100
	//tempStore.load();
	var tempHeader;
	var collapsible=true;
	var	collapsed =true;
	var	animCollapse=true;
	var title=null;
	//新业务表头
	if(Ext.isEmpty(orgCode)&&Ext.isEmpty(indicator)){
		 tempHeader="{text:'部门',menuDisabled:true,align:'center',dataIndex:'monitorDate',width:150},"+headerJs;
		 var collapsible=false;
		 var collapsed =false;
		 var animCollapse=false;
		 title='部门';
		 tempStore.load();
	}
	else{
		//应用数据表头
		if(!Ext.isEmpty(indicator)){
			tempHeader="{text:'"+indicator+"',menuDisabled:true,align:'center',dataIndex:'monitorDate',width:120},"+headerJs;
			 var collapsible=false;
			 var collapsed =false;
			 var animCollapse=false;
			 title=indicator;
		}
		//常规表头
		else{
			if(i==0){
			 var collapsible=true;
			 var collapsed =false;
			 var animCollapse=false;
			 tempStore.load();
			}
			tempHeader="{text:'"+common.resourceKeyValue.get(orgCode)+"',menuDisabled:true,align:'center',dataIndex:'"+dataIndex+"'},"+headerJs;
			title=common.resourceKeyValue.get(orgCode);
		}
		 
	}	
	//console.log(tempHeader);
	//动态列
	var dynamicGridColumn=eval("["+tempHeader+"]");	
	//动态加入表格
	var dynamicTable = Ext.create('Foss.base.dayGrid.GridPanel',
			{	
				columnLines:true,
				columns:dynamicGridColumn,
				id:orgCode,
				title:title,
				hideCollapseTool:false,
				collapsible: collapsible,
				collapsed :collapsed, 
				animCollapse: animCollapse,
				height:300,
				//autoScroll:true,
				conponentType:'table',
				orgCode:orgCode,
				store:tempStore,
				listeners: {
				        expand: {
				            fn: function(p,obj){
				            	var count=p.getStore().getCount();
				            	if(count==0){
				            	 p.getStore().load();
				            	}
				            	//p.getStore().load();
				            }
				        }
				         
				    },
				tbar:[{
					xtype: 'button', 
					text: '导出',//'新增发车计划',
					hidden:false,
					handler: function() {	
						common.monitorIndex.exportCommon(bussinessType,dataIndex,orgCode,indicator);
						/*if('按日统计'==title){
							alert('按日统计导出');
							common.monitorIndex.exportCommon('COMMON','monitorTimeRange');
						}else if('按月统计'==title){
							alert('按月统计导出');
							common.monitorIndex.exportCommon('COMMON','monitorDate');
						}*/
					}
				}],
				dockedItems : eval(dockedItems),
				constructor : function (config) {
					var me = this,
					cfg = Ext.apply({}, config);
					//me.store = Ext.create('Network.bse.BusinessManageStore');
					me.callParent([cfg]);
				}
			});
	//查询表单
	var tempForm = Ext.getCmp(tempFormId);
	var items=tempForm.items;	
	 //插入动态表格
	tempForm.insert(items.length,dynamicTable);
}


//页面入口
Ext.onReady(function() {
		//创建入口
		Ext.create('Ext.panel.Panel',{
			id:'T_common-monitorIndex_content',
			cls:"panelContentNToolbar",
			bodyCls:'panelContent-body',
			layout:'column',
			margin:'0 0 0 0',
			items : [ Ext.create('Foss.baseinfo.monitorOrgTree.LeftPanle'),
				      Ext.create('Foss.baseinfo.monitor.PanleRight')],
			renderTo: 'T_common-monitorIndex-body'
		});	
		//查询指标，初始化常规面板
		common.monitorIndex.autoQuerySearchCondition('COMMON');
		//查询指标，初始化新业务面板
		common.monitorIndex.autoQuerySearchCondition('NEW');
		//查询指标，初始化应用数据面板
		common.monitorIndex.autoQuerySearchCondition('APPLICATION');
		
});