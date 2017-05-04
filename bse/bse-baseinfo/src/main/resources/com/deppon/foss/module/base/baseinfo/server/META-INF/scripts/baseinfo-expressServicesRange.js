/**.
 * <p>
 * 消息提示框 ，无国际化<br/>
 * <p>
 * @author WeiXing
 * @param message,fun
 * @时间 2014-08-20
 */
baseinfo.showInfoMsg = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',
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
//	setTimeout(function(){
//      Ext.Msg.hide();
//  }, 3000);
};

//Ajax请求--非json字符串
baseinfo.requestAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		params:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};

baseinfo.hasSelectedCityCode=''; //选中的地级市的代码
baseinfo.expressServicesRange.ployfeature=null;//地图服务
baseinfo.checkedCount=3; //checkGroup被选中的个数
baseinfo.checkedNodes=null; //被选中的节点
baseinfo.checkedCityName=null; //被选中的节点的城市名称
//左侧面板-定义checkBox Group
Ext.define('Foss.baseinfo.expressServicesRange.expressServicesRangeOptions',{
	extend:'Ext.form.CheckboxGroup',
//	fieldLabel: '派送区域',
//	labelAlign:'top',
//	labelSeparator:'',
	width:350,
	margin:false,
    vertical: true,
    height: 70,
    columns: 2,
    columnWidth: 1,
    defaults: {
		margin:'5 5 5 5'
	},
	items: [
            { boxLabel: '显示网点位置', name: 'rb1',checked: true,listeners:{
            	change:function(ts,newValue,oldValue,eOptions){
            		if(!newValue){
						if(baseinfo.checkedCount==1){
							alert("至少选中一项");
							ts.setValue(true);
							return;
						}
					}
            	}
            } },
            { boxLabel: '显示派送范围', name: 'rb2',checked: true,listeners:{
            	change:function(ts,newValue,oldValue,eOptions){
            		if(!newValue){
						if(baseinfo.checkedCount==1){
							alert("至少选中一项");
							ts.setValue(true);
							return;
						}
					}
            	}
            } },
            { boxLabel: '显示快递代理位置', name: 'rb3',checked: true,listeners:{
            	change:function(ts,newValue,oldValue,eOptions){
            		if(!newValue){
						if(baseinfo.checkedCount==1){
							alert("至少选中一项");
							ts.setValue(true);
							return;
						}
					}
            	}
            } },
            { boxLabel: '显示无范围网点', name: 'rb4',listeners:{
            	change:function(ts,newValue,oldValue,eOptions){
            		if(!newValue){
						if(baseinfo.checkedCount==1){
							alert("至少选中一项");
							ts.setValue(true);
							return;
						}
					}
            	}
            } }
    ],
    listeners:{
    	change:function(ts,newValue,oldValue,eOptions){
    		baseinfo.checkedCount=ts.getChecked().length;
    		var flagRb1=ts.items.items[0].checked;
			var flagRb2=ts.items.items[1].checked;
			var flagRb3=ts.items.items[2].checked;
			var flagRb4=ts.items.items[3].checked;
			var expressDeliveryCoordInateList=[]; //服务范围坐标数组
			var depCoordInateList=[];//点坐标数组
			if(baseinfo.checkedNodes!=null){
				var checkedes=baseinfo.checkedNodes;  //获取tree被选中的复选框
				for(var c=0;c<checkedes.length;c++){
					if(checkedes[c].data.leaf){//获取被选中的叶子节点的ID(Leaf=true)
						var tmpId=checkedes[c].data.id;
						var tmpArray=tmpId.split("_");
						if(tmpArray[2]=='Pilot'){//快递试点网点
							if(flagRb1){//点坐标
								depCoordInateList.push(tmpArray[1]);
							}
							if(flagRb2){//服务范围
								expressDeliveryCoordInateList.push(tmpArray[0]);
							}
						}

						if(tmpArray[2]=='OutBranch'){//落地配
							if(flagRb3){
								expressDeliveryCoordInateList.push(tmpArray[0]);
								depCoordInateList.push(tmpArray[1]);
							}
						}

						if(tmpArray[2]=='NonPilot'){//非试点快递网点
							if(flagRb4){
								expressDeliveryCoordInateList.push(tmpArray[0]);
								depCoordInateList.push(tmpArray[1]);
							}
						}
					}
				}
				var cityName=baseinfo.checkedCityName;
				baseinfo.expressServicesRange.ployfeature.clearOverlays();
				baseinfo.expressServicesRange.ployfeature.showCover(cityName,expressDeliveryCoordInateList,depCoordInateList,'EXPRESS');
			}
    	}
    }
})


/**
 * 遍历子节点
 */
var checkedC = function(node, checked) {
						if (node != null && node.data.id != '100000'
							&& node.data.id != '01'&&node.data.id.trim().substr(2,node.data.id.trim().length-2).trim()!='0000') {//行政区域级别为NATION,DISTRICT_PROVINCE还有根节点的没有复选框
						    node.eachChild(function(child) {
							child.set('checked', checked);
							child.fireEvent('checkchange', child, checked);
							if (node.childNodes.length > 0) {
								checkedC(child, checked);
							}
						});
				   }
			   };

/**
 * 递归实现找到所有父亲节点,并改为选中状态
 */				
var allParent = function(node, flag) {
					if (node != null && node.data.id != '100000'
						&& node.data.id != '01'&&node.data.id.trim().substr(2,node.data.id.trim().length-2).trim()!='0000') {//行政区域级别为NATION,DISTRICT_PROVINCE还有根节点的没有复选框
						//设置 点击的节点为选中状态
						node.set('checked', flag);
					}
					//本级节点的根节点不为空，且不是tree的rootNode的情况下
					if (node.parentNode != null && node.parentNode.data.id != '100000'
						&& node.parentNode.data.id != '01'
						&&node.parentNode.data.id.trim().substr(2,node.parentNode.data.id.trim().length-2).trim()!='0000') {//行政区域级别为NATION,DISTRICT_PROVINCE还有根节点的没有复选框
						if (!node.data.checked) {//如果此节点是未选中状态，则点击是选中操作
							var i = 0;
							//本级所有节点的选中数量
							node.parentNode.eachChild(function(child) {
								//循环判断本级下属所有节点是否有选中的
								if (child.data.checked) {
									i = i + 1;
								}
				
							});
							if (i <= 0) {//如果本级节点下属已经没有选中的，则更新其父节点为未选中。
								allParent(node.parentNode, flag);
							}
						} else {//如果是选中状态，则点击是取消选中操作
							allParent(node.parentNode, flag);
						}
				
					}
				};


//左侧面板-定义行政区域TreeStore
Ext.define('Foss.baseinfo.expressServicesRange.ExpressServicesRangeTreeStore',{
  	extend: 'Ext.data.TreeStore', 
  	root: {
		//根的文本信息
		text:baseinfo.expressServicesRange.i18n('foss.baseinfo.district'),
		//设置根节点的ID
		id : '01',
		//根节点是否展开
		expanded: true
	},
	//设置一个代理，通过读取内存数据
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryByParentDistrictCode_ESR.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	},
  	nodeParam: 'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode'   //用于传递parentDistrictCode到action
});    
//左侧面板-定义行政区域Tree
Ext.define('Foss.baseinfo.expressServicesRange.ExpressServicesRangeTree',{
    extend:'Ext.tree.Panel',
//    title: baseinfo.expressServicesRange.i18n('foss.baseinfo.district'),
    margin: false,
    autoScroll: true,
    animate: false,
//    useArrows: true,  //在tree中使用Vista-style样式的箭头
    frame: true,
    rootVisible: true,
    // 树要给高度才有滚动条
    height: 650,
    columnWidth: 1,
    store: Ext.create('Foss.baseinfo.expressServicesRange.ExpressServicesRangeTreeStore'),
    defaults: {
		margin:'5 5 5 5'
	},
  	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
	    //监听鼠标事件
	    me.listeners = {
	    		beforeitemexpand:function(opt){
					var record=opt;
					if(record.parentNode==null){
						return;
					}
					//加载子节点
					if((record.parentNode.data.id.trim().substr(record.parentNode.data.id.trim().length-2,record.parentNode.data.id.trim().length).trim()=='00'||
							record.parentNode.data.id.trim().substr(record.parentNode.data.id.trim().length-2,record.parentNode.data.id.trim().length).trim()=='-1')&&//特例：直辖市 北京
							record.childNodes.length==0){
						
								var params ={
									'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode':record.data.id
								}
								//添加子节点
								Ext.Ajax.request({
									url:baseinfo.realPath("queryByParentDistrictCode_ESR.action"),
									params:params,
									async:false,
									success:function(response){
										var result = Ext.decode(response.responseText);
										if(result.success){
											var nodes=result.nodes;
											for(var n=0;n<nodes.length;n++){
												var childrenNodesList=[];
												//遍历节点的子节点
												var childrenNodes=nodes[n].children;
												for(var c=0;c<childrenNodes.length;c++){
													var childNode = {
														'id':childrenNodes[c].id,
														'parentId':childrenNodes[c].parentId,
														"text": childrenNodes[c].text,
														'checked': childrenNodes[c].checked,
														"leaf": childrenNodes[c].leaf
													}
													childrenNodesList.push(childNode);
												}
												var addedNode = {
														'id':nodes[n].id,
														'parentId':nodes[n].parentId,
														"text": nodes[n].text,
														'checked': nodes[n].checked,
														"leaf": nodes[n].leaf
//														"children":childrenNodesList
													}
													
												var parentNode=record;
												if(parentNode!=null){
													var length = parentNode.childNodes.length;
													parentNode.insertChild(length, addedNode);
													parentNode.lastChild.raw = addedNode; 		
													parentNode.set('loaded', true);	
													
													//为区县添加子节点
													for(var v=0;v<childrenNodesList.length;v++){
														parentNode.childNodes[n].insertChild(childrenNodesList.length,childrenNodesList[v]);
														parentNode.childNodes[n].lastChild.raw = childrenNodesList[v]; 		
														parentNode.childNodes[n].set('loaded', true);
													}
												}
											}
										}else{
											baseinfo.showInfoMsg("节点加载失败",function(){});
											return;
										}
									},
									failure:function(response){
										baseinfo.showInfoMsg("节点加载失败",function(){});
										return;
									},
									exception:function(response){
										baseinfo.showInfoMsg("节点加载失败",function(){});
										return;
									}
								});
								//record.expand();//展开节点
						}
				},
				//左键单击
			itemclick:function(node,record,item,index,e){
				//阻止浏览器默认行为处理事件
				e.preventDefault();
				if(record.parentNode==null){
					return;
				}
				//加载子节点
				if((record.parentNode.data.id.trim().substr(record.parentNode.data.id.trim().length-2,record.parentNode.data.id.trim().length).trim()=='00'||
						record.parentNode.data.id.trim().substr(record.parentNode.data.id.trim().length-2,record.parentNode.data.id.trim().length).trim()=='-1')&&//特例：直辖市 北京
						record.childNodes.length==0){
					
							var params ={
								'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode':record.data.id
							}
							//添加子节点
							Ext.Ajax.request({
								url:baseinfo.realPath("queryByParentDistrictCode_ESR.action"),
								params:params,
								async:false,
								success:function(response){
									var result = Ext.decode(response.responseText);
									if(result.success){
										var nodes=result.nodes;
										for(var n=0;n<nodes.length;n++){
											var childrenNodesList=[];
											//遍历节点的子节点
											var childrenNodes=nodes[n].children;
											for(var c=0;c<childrenNodes.length;c++){
												var childNode = {
													'id':childrenNodes[c].id,
													'parentId':childrenNodes[c].parentId,
													"text": childrenNodes[c].text,
													'checked': childrenNodes[c].checked,
													"leaf": childrenNodes[c].leaf
												}
												childrenNodesList.push(childNode);
											}
											var addedNode = {
													'id':nodes[n].id,
													'parentId':nodes[n].parentId,
													"text": nodes[n].text,
													'checked': nodes[n].checked,
													"leaf": nodes[n].leaf
//													"children":childrenNodesList
												}
												
											var parentNode=record;
											if(parentNode!=null){
												var length = parentNode.childNodes.length;
												parentNode.insertChild(length, addedNode);
												parentNode.lastChild.raw = addedNode; 		
												parentNode.set('loaded', true);	
												
												//为区县添加子节点
												for(var v=0;v<childrenNodesList.length;v++){
													parentNode.childNodes[n].insertChild(childrenNodesList.length,childrenNodesList[v]);
													parentNode.childNodes[n].lastChild.raw = childrenNodesList[v]; 		
													parentNode.childNodes[n].set('loaded', true);
												}
											}
										}
									}else{
										baseinfo.showInfoMsg("节点加载失败",function(){});
										return;
									}
								},
								failure:function(response){
									baseinfo.showInfoMsg("节点加载失败",function(){});
									return;
								},
								exception:function(response){
									baseinfo.showInfoMsg("节点加载失败",function(){});
									return;
								}
							});
							//record.expand();//展开节点
					}
				
				//操作复选框
				if (record != null && record.data.id != '100000'
					&& record.data.id != '01'&&record.data.id.trim().substr(2,record.data.id.trim().length-2).trim()!='0000') {//行政区域级别为NATION,DISTRICT_PROVINCE还有根节点的没有复选框
					
					checkedC(record,!record.get('checked'));
					allParent(record,!record.get('checked'));

					var sel=me.getSelectionModel().getSelection();
					var isLeaf=sel[0].data.leaf; //是否是叶子节点
					if(isLeaf){//叶子节点
						var parentNodeId=sel[0].parentNode.parentNode.data.id; //找到父节点的ID
						if(baseinfo.hasSelectedCityCode==''){
							baseinfo.hasSelectedCityCode=parentNodeId;
							if(sel[0].parentNode.parentNode.data.checked){
//								baseinfo.checkedCityName=sel[0].parentNode.parentNode.data.text;
								baseinfo.checkedCityName=sel[0].parentNode.data.text;
							}
						}else{
							if(baseinfo.hasSelectedCityCode!=parentNodeId){
								//取消选中的复选框
//								checkedC(record,!record.get('checked'));
//								allParent(record,!record.get('checked'));
								checkedC(record,false);
								allParent(record,false);
								baseinfo.showInfoMsg("不能选择其他市的县区",function(){});
								return false;
							}else{
								var parentChecked=sel[0].parentNode.data.checked;
								if(!parentChecked){//如果父节点(地级市)取消选中,则清空
									baseinfo.hasSelectedCityCode='';
									baseinfo.checkedCityName="";
								}
							}
						}
					}else{//非叶子节点
						if(sel[0].childNodes[0]==undefined){//如果未展开,当点击的时候复选框不被选中
//							checkedC(record,!record.get('checked'));
//							allParent(record,!record.get('checked'));
							checkedC(record,false);
							allParent(record,false);
//							baseinfo.checkedCityName=sel[0].parentNode.data.text;
							baseinfo.checkedCityName=sel[0].data.text;
							var cityName=baseinfo.checkedCityName;
							baseinfo.expressServicesRange.ployfeature.showCover(cityName,{},{},'EXPRESS');
							return false;
						}
						if(sel[0].childNodes[0]){//有子节点,如果子节点是非叶子节点,则判断子节点是否展开,如果未展开,则不能被选中。
							var childNodes=sel[0].childNodes;
							var collapsedChildNodes=0; //未展开的子节点的个数
							for(var x=0;x<childNodes.length;x++){
								var childNode=childNodes[x];
								if(!(childNode.data.leaf)){//判断是否为非叶子节点
									if (childNode.childNodes[0]==undefined){//未展开
										checkedC(childNode,false);
										allParent(childNode,false);
										collapsedChildNodes=parseInt(collapsedChildNodes)+1;  //计算未展开的子节点个数
										continue;
									}
								}
							}
						}
						if(collapsedChildNodes==childNodes.length){//如果所有的子节点都未展开,就return
							baseinfo.checkedCityName=sel[0].data.text;
							var cityName=baseinfo.checkedCityName;
							baseinfo.expressServicesRange.ployfeature.showCover(cityName,{},{},'EXPRESS');
							return false;
						}
						//判断节点是地级市节点,还是区县节点
						if(sel[0].childNodes[0].data.leaf){//区县节点(区县级的非叶子节点)
							var nodeId=sel[0].parentNode.data.id;//找到地级市的ID
							if(baseinfo.hasSelectedCityCode==''){
								baseinfo.hasSelectedCityCode=nodeId;
								if(sel[0].parentNode.data.checked){
//									baseinfo.checkedCityName=sel[0].parentNode.data.text;
									baseinfo.checkedCityName=sel[0].data.text;
								}
							}else{
								if(baseinfo.hasSelectedCityCode!=nodeId){
									//取消选中的复选框
//									checkedC(record,!record.get('checked'));
//									allParent(record,!record.get('checked'));
									checkedC(record,false);
									allParent(record,false);
									baseinfo.showInfoMsg("不能选择其他市的县区",function(){});
									return false;
								}else{
									var nodeChecked=sel[0].parentNode.data.checked;
									if(!nodeChecked){//如果取消选中区县,则清空
										baseinfo.hasSelectedCityCode='';
										baseinfo.checkedCityName="";
									}
								}
							}
						}else{//地级市节点(地级市级别非叶子节点)
							var nodeId=sel[0].data.id;//
							if(baseinfo.hasSelectedCityCode==''){
								baseinfo.hasSelectedCityCode=nodeId;
								if(sel[0].data.checked){
									baseinfo.checkedCityName=sel[0].data.text;
								}
							}else{
								if(baseinfo.hasSelectedCityCode!=nodeId){
									//取消选中的复选框
//									checkedC(record,!record.get('checked'));
//									allParent(record,!record.get('checked'));
									checkedC(record,false);
									allParent(record,false);
									baseinfo.showInfoMsg("不能选择其他市的县区",function(){});
									return false;
								}else{
									var nodeChecked=sel[0].data.checked;
									if(!nodeChecked){//如果取消选中地级市,则清空
										baseinfo.hasSelectedCityCode='';
										baseinfo.checkedCityName="";
									}
								}
							}
						}
						
					}
				}
				
				var options=me.up('panel').getExpressServicesRangeOptions();
				var flagRb1=options.items.items[0].checked;
				var flagRb2=options.items.items[1].checked;
				var flagRb3=options.items.items[2].checked;
				var flagRb4=options.items.items[3].checked;

				var expressDeliveryCoordInateList=[]; //服务范围坐标数组
				var depCoordInateList=[];//点坐标数组
				var checkedes=me.getChecked();  //获取tree被选中的复选框
				baseinfo.checkedNodes=checkedes;
				for(var c=0;c<checkedes.length;c++){
					if(checkedes[c].data.leaf){//获取被选中的叶子节点的ID(Leaf=true)
						var tmpId=checkedes[c].data.id;
						var tmpArray=tmpId.split("_");
						if(tmpArray[2]=='Pilot'){//快递试点网点
							if(flagRb1){//点坐标
								depCoordInateList.push(tmpArray[1]);
							}
							if(flagRb2){//服务范围
								expressDeliveryCoordInateList.push(tmpArray[0]);
							}
						}

						if(tmpArray[2]=='OutBranch'){//落地配
							if(flagRb3){
								expressDeliveryCoordInateList.push(tmpArray[0]);
								depCoordInateList.push(tmpArray[1]);
							}
						}

						if(tmpArray[2]=='NonPilot'){//非试点快递网点
							if(flagRb4){
								expressDeliveryCoordInateList.push(tmpArray[0]);
								depCoordInateList.push(tmpArray[1]);
							}
						}
					}
				}
				var cityName=baseinfo.checkedCityName;
				baseinfo.expressServicesRange.ployfeature.clearOverlays();
				baseinfo.expressServicesRange.ployfeature.showCover(cityName,expressDeliveryCoordInateList,depCoordInateList,'EXPRESS');
			},
	      //右键单击
	      itemcontextmenu:function(node,record,item,index,e){
	        e.preventDefault();
	      }
	    };
		me.callParent([cfg]);
	}
});  

//
Ext.define('Foss.baseinfo.expressServicesRange.ExpressServicesRangeLeftPannel',{
	extend:'Ext.panel.Panel',
	//height: 680,
	frame: true,
	title:'派送区域',
//	layout:'column',
	layout:'auto',
	columnWidth: 0.35,
	defaultType : 'textfield',
	//定义组织树方法
	expressServicesRangeTree: null,
	getExpressServicesRangeTree: function(){
		if(this.expressServicesRangeTree == null){
			this.expressServicesRangeTree = Ext.create('Foss.baseinfo.expressServicesRange.ExpressServicesRangeTree');
		}
		return this.expressServicesRangeTree;
	},
	expressServicesRangeOptions:null,
	getExpressServicesRangeOptions:function(){
		if(this.expressServicesRangeOptions==null){
			this.expressServicesRangeOptions=Ext.create('Foss.baseinfo.expressServicesRange.expressServicesRangeOptions');
		}
		return this.expressServicesRangeOptions;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getExpressServicesRangeOptions(),me.getExpressServicesRangeTree()];  
		me.callParent([cfg]);
	}
});

Ext.define('Foss.baseinfo.expressServicesRange.ExpressServicesRangeRightPannel',{
	extend:'Ext.panel.Panel',
	height: 680,
	frame: true,
	title:'快递服务范围',
//	layout:'column',
	layout:'auto',
	columnWidth: 0.65,
	defaultType : 'textfield',
	constructor: function(config){
		var me = this, cfg = Ext.apply({}, config);
		me.items=[{
							xtype: 'container',
							height:630,
							width:630,
							listeners:{
							   //显示之前的事件
								  beforerender:function(panel){
								  Ext.defer(function(){
									  var dmap = new DPMap.MyMap('VIEW', panel.getId(),{center : "上海市",zoom:"TOWN"},function(map) { 
										  		baseinfo.expressServicesRange.ployfeature = DMap.CoverFeature(map,{isAddable:false});
										   });
								   },1000,this);
							   }
						   }}]
		me.callParent([cfg]);
	}
});

/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressServicesRange_content')) {
		return;
	}
	var expressServicesRangeLeftPannel = Ext.create('Foss.baseinfo.expressServicesRange.ExpressServicesRangeLeftPannel');
	var expressServicesRangeRightPannel = Ext.create('Foss.baseinfo.expressServicesRange.ExpressServicesRangeRightPannel');
	Ext.getCmp('T_baseinfo-expressServicesRange').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-expressServicesRange_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		getExpressLeftPannel : function() {
			return expressServicesRangeLeftPannel;
		},
		getExpressRightPannel : function() {
			return expressServicesRangeRightPannel;
		},
		items: [expressServicesRangeLeftPannel,expressServicesRangeRightPannel]
	}));
});