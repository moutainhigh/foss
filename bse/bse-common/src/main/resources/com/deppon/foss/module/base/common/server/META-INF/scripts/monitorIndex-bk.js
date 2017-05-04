//常量

//所有可能选择部门编码列表
common.resourceKeyValue=new Ext.util.HashMap();
//选中的部门编码列表
common.resourceKeyValueChoosed=new Ext.util.HashMap();
common.indicators=new Ext.util.HashMap();


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
    nodeParam: 'vo.parentOrgCode',
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
   height: 650,
   store: Ext.create('Foss.baseinfo.monitorOrgTree.TreeStore'),
   defaults: {
       margin:'5 5 5 5',
   },
   bindData : function(me){
		
   },
   /**
	 * 父节点选中时，选择所有子节点
	 */
	selectChildFunction : function(node, checked) {
		var me = this;
		var a_code=node.data.id;
		if(checked){			
			common.resourceKeyValue.add(a_code,node.data.text);
			common.resourceKeyValueChoosed.add(a_code,node.data.text);
		}else{
			common.resourceKeyValueChoosed.removeAtKey(a_code);
		}
		Ext.Array.each(node.childNodes, function(childNode) {
			childNode.set("checked", checked);
			me.selectChildFunction(childNode, checked);
			if(checked){
				common.resourceKeyValueChoosed.add(childNode.data.id,childNode.data.text);
			}else{
				common.resourceKeyValueChoosed.removeAtKey(childNode.data.id,childNode.data.text);
			}
				
		});
	},
	checkChange : function(node, checked) {
		var a_code=node.data.id;
		if (checked == true) {
			common.checkParent(node, true);
			this.selectChildFunction(node, true);			
			common.resourceKeyValue.add(a_code,node.data.text);
			common.resourceKeyValueChoosed.add(a_code,node.data.text);			
		} else {
			this.selectChildFunction(node, false);
			// 判断父节点下是否还有子节点是选中状态
			this.deSelectParentFunction(node);
			common.resourceKeyValueChoosed.removeAtKey(a_code);
		}
	},
	/**
	 * 当所有子节点没有选中时候，取消选择父节点
	 */
	deSelectParentFunction : function(node) {
		if (node.data.id == this.store.root.id)
			return;
		var parentNode = node.parentNode;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				if (childNode.data.checked == true) {
					return;
				}else{
					common.resourceKeyValueChoosed.removeAtKey(childNode.data.id);
				}
			}
		}
		if(parentNode.data.id != this.store.root.id){
			var a_code=parentNode.data.id;
			parentNode.set("checked", false);
			common.resourceKeyValueChoosed.removeAtKey(a_code);
		}
		this.deSelectParentFunction(parentNode);
	},
	itemExpand : function(node) {
		if(node.data.checked==null||node.data.checked==node.raw.checked){
			return;
		}
		//得到更改后的选中判断值
		var checked = node.data.checked;

		/* ***** 选中时将子节点全部选中****** */
		var parentNode = node;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				childNode.set("checked", checked);
				this.selectChildFunction(childNode, checked);
				if(!checked){
					common.resourceKeyValueChoosed.removeAtKey(childNode.data.id);
				}
			}
		}
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
	frame: true,
	collapsible: true,
	animCollapse: false,	
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	title:'按日统计结果',
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
Ext.define('Foss.baseinfo.searchNewBussiness.FormPanel',{
	extend: 'Ext.form.Panel',
	id:'Foss_baseinfo_searchNewBussiness_FormPanel_Id',
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
	extend: 'Ext.form.Panel',
	id:'Foss_baseinfo_newBussiness_Panel_ID',
	layout:'auto',	
	bodyStyle:'padding:5px 5px 0 5px',
	items:[
	       
	       ]
})

//监控TAB
Ext.define('Foss.base.monitor.TabPanel',{
	extend:'Ext.tab.Panel',
	cls:"innerTabPanel",
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
				items: Ext.create('Foss.baseinfo.newBussiness.Panel')
			 },{
				title: '新功能业务',
				tabConfig:{width:100},
				items: null
			},{
				title: '应用数据',
				tabConfig:{width:100},				
				items:null
			}
		];
		me.callParent([cfg]);
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

//全选
common.monitorIndex.checkAllSearchForm=function(val){
	var tempForm = common.monitorIndex.searchForm.getForm(); 
	common.indicators.each(function(key, value, length){
		if(key=='dayTotal'||key=='monthTotal'){
			
		}else{			
			tempForm.findField(key).setValue(val);
		}
	});
}

//自动查询指标组合
common.monitorIndex.autoQuerySearchCondition=function(bussinessType){
	var queryUrl=common.realPath('queryIndicatorGroupByCategory.action');
	var params={'vo.bussinessType':bussinessType};
	Ext.Ajax.request({
		url : queryUrl,
		params:params,
		success : function(response) {
			var json = Ext.decode(response.responseText);
			var useableIndicators=json.vo.useableIndicators;
			if(!Ext.isEmpty(useableIndicators)){
				var indicatorStr="{boxLabel:'全选',name:'checkAll',xtype:'checkbox',inputValue: 'Y',columnWidth:.9," +
						"listeners:{" +
							"change :function(field,newValue, oldValue,eOpts ){" +								
									"common.monitorIndex.checkAllSearchForm(newValue);" +							
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
			
			var tail=",{border : false,xtype : 'container',columnWidth:0.9,layout:'column',defaults: {margin:'5 0 5 10'},"
				+"items : [{border : false,columnWidth:.6,html: '&nbsp;'},{boxLabel  : '按日统计',name: 'dayTotal',xtype:'checkbox',inputValue: 'Y',columnWidth:.15,checked:true},"
				+"{boxLabel:'按月统计',name:'monthTotal',xtype:'checkbox',inputValue: 'Y',columnWidth:.15},"
				+"{columnWidth:.1,xtype : 'button',text: '查询',cls:'yellow_button',handler: function() {"
				+"if(this.up('form').getForm().isValid( )){"										
				+"common.monitorIndex.queryMonitorData();}}}]}";
				indicatorStr=indicatorStr+tail;
				
			//拼接表单
			common.monitorIndex.searchForm=Ext.create('Foss.baseinfo.searchNewBussiness.FormPanel',{
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
			
			var tempForm = Ext.getCmp('Foss_baseinfo_newBussiness_Panel_ID')
			var items=tempForm.items;	
			 //插入动态表格
			tempForm.insert(items.length,common.monitorIndex.searchForm);
		},exception : function(response) {
			var json = Ext.decode(response.responseText);			
		}
	});
}

//查询监控指标
common.monitorIndex.queryMonitorData = function(){
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
	var headerUrl=common.realPath('queryCommonIndicatorHeader.action');
	var indicatorGroups=new Array();
	//获取指标	 
	 var choosedFields=common.monitorIndex.searchForm.getValues();
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
				proxy: {
					type: 'ajax',
					url:common.realPath('queryDailyCommonIndicatorData.action'),
					actionMethods:'post',
					reader: {
						type: 'json',
						root: 'vo.datas'
					}
				},
				constructor: function(config){
					var me = this,
						cfg = Ext.apply({}, config);
					me.callParent([cfg]);
				},listeners: {
					beforeload : function(store, operation, eOpts) {
							var queryParams={'vo.indicatorGroups':indicatorGroups,'vo.orgCode':this.orgCode};
							Ext.apply(operation, {
								params : queryParams
							});
					}
				}
			});
			//根据选择的区域,			
			//根据选择的“按日统计”,"按月统计"进行查询		
			common.monitorIndex.searchForm.getForm().findField('dayTotal').getValue();
			//按日统计-不同情况生成表格
			 if(common.monitorIndex.searchForm.getForm().findField('dayTotal').getValue()){
				 var titleHeader='按日统计-'
				//组织编码
				var map = new Ext.util.HashMap();
				map=common.resourceKeyValueChoosed;
				map.each(function(key, value, length){
					var tempStore=Ext.create('Foss.monitorIndex.store.MonitorData');
					//动态创建表格
					common.monitorIndex.dynamicMonitorTable(headerJs,key,tempStore,titleHeader,'monitorTimeRange');
				}); 					
			 }
			//按日统计-不同情况生成表格
			 if(common.monitorIndex.searchForm.getForm().findField('monthTotal').getValue()){
				 var titleHeader='按月统计-'
					//组织编码
					var map = new Ext.util.HashMap();
					map=common.resourceKeyValueChoosed;
					map.each(function(key, value, length){
						var tempStore=Ext.create('Foss.monitorIndex.store.MonitorData');
						//动态创建表格
						common.monitorIndex.dynamicMonitorTable(headerJs,key,tempStore,titleHeader,'monitorDate');
					}); 						
			 }				 
			
		},
		exception : function(response) {
			var json = Ext.decode(response.responseText);
			
		}
	});
}

//动态创建表格
common.monitorIndex.dynamicMonitorTable=function(headerJs,orgCode,tempStore,titleHeader,dataIndex){	
	tempStore.orgCode=orgCode;
	tempStore.load();
	var text='';
	if(dataIndex=='monitorDate'){
		text='日期';
	}else{
		text='时间';
	}
	var tempHeader="{text:'"+text+"',menuDisabled:true,align:'center',dataIndex:'"+dataIndex+"'},"+headerJs;
	//console.log(tempHeader);
	//动态列
	var dynamicGridColumn=eval("["+tempHeader+"]");
	var tempTitle=titleHeader+common.resourceKeyValue.get(orgCode);
	//动态加入表格
	var dynamicTable = Ext.create('Foss.base.dayGrid.GridPanel',
			{
				columns:dynamicGridColumn,
				title:tempTitle,
				conponentType:'table',
				orgCode:orgCode,
				store:tempStore
			});
	//dynamicTable.store=tempStore.load();
	//查询表单
	var tempForm = Ext.getCmp('Foss_baseinfo_newBussiness_Panel_ID');
	var items=tempForm.items;	
	 //插入动态表格
	tempForm.insert(items.length,dynamicTable);
}

//页面入口
Ext.onReady(function() {
	Ext.getCmp('T_common-monitorIndex').add(Ext.create('Ext.panel.Panel',{
		id:'T_common-monitorIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		layout:'column',
		margin:'0 0 0 0',
		items : [ Ext.create('Foss.baseinfo.monitorOrgTree.LeftPanle'),
			      Ext.create('Foss.baseinfo.monitor.PanleRight')] 
	}));	
		common.monitorIndex.autoQuerySearchCondition('');
});