baseinfo.administrativeRegions.OP_TYPE_UPDATE ="U";
baseinfo.administrativeRegions.OP_TYPE_ADD ="A";
baseinfo.administrativeRegions.OP_TYPE_INIT ="INIT";
/**
 * 重置
 */
baseinfo.administrativeRegions.reset = function(me){
	var form =me.getForm();
	form.reset();
}

/**
 * 行政区域修改操作
*/
baseinfo.administrativeRegions.updateDetailOperator = function(){
	
	var detailForm = Ext.getCmp('T_baseinfo-administrativeRegionsindex_content').getRegionsRightPannel().getAdministrativeRegionsDetailForm();
	
	var dictModifyForm = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
	dictModifyForm.operatorType = baseinfo.administrativeRegions.OP_TYPE_UPDATE;
	dictModifyForm.initFormFlag = baseinfo.administrativeRegions.OP_TYPE_INIT;
 	
 	if(Ext.isEmpty( detailForm.getForm().findField('parentDistrictCode').getValue())){
 		Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), baseinfo.administrativeRegions.i18n('foss.baseinfo.choseDistrict'));
 	}else{
 	baseinfo.administrativeRegions.dictModifyWindow.show();
	var districtModel = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', detailForm.getRecord().data);
	var parentDistrictCodeObj = dictModifyForm.getForm().findField('parentDistrictCode');
	// 设置编码不可以修改
	dictModifyForm.getForm().findField('code').setReadOnly(true);
	var parentDistrictName = detailForm.getForm().findField('parentDistrictName').getValue()
	var parentDistrictCode = detailForm.getForm().findField('parentDistrictCode').getValue()
	parentDistrictCodeObj.disName = parentDistrictName;
	parentDistrictCodeObj.disValue = parentDistrictCode;
	dictModifyForm.getForm().loadRecord(districtModel);
	dictModifyForm.getForm().findField('parentDistrictCode').setCombValue(parentDistrictName,parentDistrictCode);
 	}
}
/**
 * 行政区域修改操作
*/
baseinfo.administrativeRegions.updateOperator = function(grid, rowIndex, colIndex){
	var dictModifyForm = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
	dictModifyForm.operatorType = baseinfo.administrativeRegions.OP_TYPE_UPDATE;
	dictModifyForm.initFormFlag = baseinfo.administrativeRegions.OP_TYPE_INIT;
 	var rowInfo = grid.getStore().getAt(rowIndex);
 	baseinfo.administrativeRegions.dictModifyWindow.show();
	var districtModel = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', rowInfo.data);
	var parentDistrictCodeObj = dictModifyForm.getForm().findField('parentDistrictCode');
	// 设置编码不可以修改
	dictModifyForm.getForm().findField('code').setReadOnly(true);
	parentDistrictCodeObj.disName = rowInfo.data.parentDistrictName;
	parentDistrictCodeObj.disValue = rowInfo.data.parentDistrictCode;
	dictModifyForm.getForm().loadRecord(districtModel);
	dictModifyForm.getForm().findField('parentDistrictCode').setCombValue(rowInfo.data.parentDistrictName,rowInfo.data.parentDistrictCode);
}
/**
  * 行政区域新增操作
*/
baseinfo.administrativeRegions.addOperator = function(){
	var dictModifyForm = baseinfo.administrativeRegions.addDictModifyWindow.getDictMidifyUpdateForm();
	dictModifyForm.getForm().reset();
	//baseinfo.administrativeRegions.dictModifyWindow.show();
	dictModifyForm.setTitle(baseinfo.administrativeRegions.i18n('foss.baseinfo.addUpdateDistrict'));
//	dictModifyForm.operatorType = baseinfo.administrativeRegions.OP_TYPE_ADD; 
//	dictModifyForm.initFormFlag = baseinfo.administrativeRegions.OP_TYPE_INIT;
	dictModifyForm.getForm().findField('code').setReadOnly(false); 
	//点击“新增按钮”后，将选择的结点的id设置进去：
	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
	var selectRecord=administrativeRegionsTree.getSelectionModel().getSelection();
	if(selectRecord&&selectRecord.length>0){
		var record = selectRecord[0].data;
		var code=record.id;
		var name = record.text;
		parentDistrictCodeObj = dictModifyForm.getForm().findField('parentDistrictCode');
		parentDistrictCodeObj.disName = name;
		parentDistrictCodeObj.disValue = code;
		parentDistrictCodeObj.setCombValue(name,code);
		selectRecord[0].expand();
	} 
	baseinfo.administrativeRegions.addDictModifyWindow.show();
}
/**
 * 行政区域作废操作
*/
baseinfo.administrativeRegions.cancelOperator = function(grid, rowIndex, colIndex){
  	Ext.MessageBox.buttonText.yes = baseinfo.administrativeRegions.i18n('foss.baseinfo.sure');
		Ext.MessageBox.buttonText.no = baseinfo.administrativeRegions.i18n('foss.baseinfo.cancel');
		Ext.Msg.confirm(baseinfo.administrativeRegions.i18n('foss.baseinfo.tipInfo'),baseinfo.administrativeRegions.i18n('foss.baseinfo.sureDelete'),function(btn,text) {
			if (btn == 'yes') {
				var msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.deleteSuccess');
				// 获得当前选择的数据：
				var rowInfo = grid.getStore().getAt(rowIndex).data;
				//发送作废结点的Ajax请求.
				var administrativeRegionsVo = new Object();
				administrativeRegionsVo.administrativeRegionsDetail = new Object();
				administrativeRegionsVo.administrativeRegionsDetail.code = rowInfo.code;
				var params = {'administrativeRegionsVo':administrativeRegionsVo};
				var r_url = "deleteAdministrativeRegions.action";
				r_url=	baseinfo.realPath(r_url) ;
				Ext.Ajax.request({
		            url : r_url,
		            jsonData: params,
		            success : function(response) {
		                var json = Ext.decode(response.responseText);		
						//如果 树型结构中当前有这个结点，移除树结点，更新树型结构：
		            	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
		            	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
		            	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
		                var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
		                
						var currNode= administrativeRegionsTreeStore.getNodeById(rowInfo.code);
						if(currNode){
							currNode.remove();
						}        
		                grid.getStore().removeAt(rowIndex);
		            },
		            exception : function(response) {
		                var json = Ext.decode(response.responseText);
						msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.deleteFailure');
		            }
		        });		
				Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), msgTip);
			}
		});
}
/**
 * 保存行政区域修改方法
 */
baseinfo.administrativeRegions.saveUpdateOperator = function(me){
	var administrativeRegionsDetailForm = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
	var administrativeRegionsObj = administrativeRegionsDetailForm.getForm().getValues(); 
	
	if(!administrativeRegionsDetailForm.getForm().isValid()){
		return;
	}
	var msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.addSuccessful');
	// 当前表单中的数据： 
	
	//树结点信息
	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
    var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
    // 被操作的结点的上级
	var parentNode= administrativeRegionsTreeStore.getNodeById(administrativeRegionsObj.parentDistrictCode);
	if(administrativeRegionsObj.parentDistrictCode==null||administrativeRegionsObj.parentDistrictCode==''){
		parentNode=administrativeRegionsTreeStore.getRootNode();
	}	
	// 展开父结点
	if(parentNode){
		parentNode.expand();
	}
	//发送保存新结点的Ajax请求
	var administrativeRegionsVo = new Object();
	var a_model = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', administrativeRegionsObj);
	administrativeRegionsVo.administrativeRegionsDetail = a_model.data
  	
  	administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId = baseinfo.administrativeRegions.isVirtaulToCode(administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId);
    
    var params = {'administrativeRegionsVo': administrativeRegionsVo};
	Ext.Ajax.request({
        url : baseinfo.realPath('updateAdministrativeRegions.action'),
        jsonData:params,
        success : function(response) {
        	var record = administrativeRegionsDetailForm.getForm().getRecord();
			//下面一句让表格里面的数据也更新了：
			administrativeRegionsDetailForm.getForm().updateRecord(record);	
            var json = Ext.decode(response.responseText);	
			//如果 树型结构中当前有这个结点，更更新树型结构：
			var currNode= administrativeRegionsTreeStore.getNodeById(record.get('code'));
			if(currNode){
				currNode.data.id = record.get('tregionCodse');
				currNode.data.parentId = record.get('parentDistrictCode');
				currNode.data.text = record.get('name');
								        
				//刷新树结点：
				var a=currNode.data.expanded?currNode.collapse():currNode.expand();
				a=currNode.data.expanded?currNode.collapse():currNode.expand();
				//只有set loaded为ture才可以更新页面
				currNode.set('loaded', true);	
			}
			var administrativeRegionsForm=baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
			administrativeRegionsForm.getForm().reset();	

			//刷新上级树结点：
			if(parentNode && parentNode.data){
				baseinfo.administrativeRegions.flushNodeById(parentNode.data.parentId);
			}
			msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.updateSuccessful');
			Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), msgTip);
			//点击保存后，清空表单数据，并隐藏表单：
			baseinfo.administrativeRegions.dictModifyWindow.hide();
			baseinfo.administrativeRegions.pagingBar.moveFirst();
        },
        exception : function(response) {
            var json = Ext.decode(response.responseText);
			msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.updateFail');
			Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), msgTip);
        }
       
	});
}
 
 /**
  * 保存行政区域新增方法
 */
baseinfo.administrativeRegions.saveAddOperator = function(me){
	var administrativeRegionsDetailForm = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
	if(!administrativeRegionsDetailForm.getForm().isValid()){
		return;
	}
	var msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.addSuccessful');
	// 当前表单中的数据：
	var administrativeRegionsObj = administrativeRegionsDetailForm.getForm().getValues(); 
	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
    var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
	// 被操作的结点的上级
	var parentNode= administrativeRegionsTreeStore.getNodeById(administrativeRegionsObj.parentDistrictCode);
	if(administrativeRegionsObj.parentDistrictCode==null||administrativeRegionsObj.parentDistrictCode==''){
		parentNode=administrativeRegionsTreeStore.getRootNode();
	}	  
	
	// 展开父结点
	if(parentNode){
		parentNode.expand();
	}
	// 修改树结点
	if(parentNode&&parentNode.data.leaf){
	    parentNode.data.leaf = false;
	}
	var addedNode = {
	    'id':administrativeRegionsObj.code,
	    'parentId':administrativeRegionsObj.parentDistrictCode,
	    "text": administrativeRegionsObj.name,
	    'expanded': true,
	    "leaf": true
	};
	//发送保存新结点的Ajax请求.
	var administrativeRegionsVo = new Object();
	var a_model = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', administrativeRegionsObj);
	administrativeRegionsVo.administrativeRegionsDetail = a_model.data
	administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId = baseinfo.administrativeRegions.isVirtaulToCode(administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId);
	var params = {'administrativeRegionsVo':administrativeRegionsVo};
    Ext.Ajax.request({
        url : baseinfo.realPath('addAdministrativeRegions.action'),
        jsonData:params,
        success : function(response) {
          	var json = Ext.decode(response.responseText);
			//将被添加的节点插入到树型结构中：	
			if(parentNode!=null){
				var length = parentNode.childNodes.length;
				parentNode.insertChild(length, addedNode);
				parentNode.lastChild.raw = addedNode; 		
				parentNode.set('loaded', true);	
			}
			var administrativeRegionsForm=baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
			administrativeRegionsForm.getForm().reset();
			//刷新树结点：
			baseinfo.administrativeRegions.flushNodeById(administrativeRegionsObj.code);
			Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), msgTip);
			//点击保存后，清空表单数据，并隐藏表单：
			baseinfo.administrativeRegions.dictModifyWindow.hide();
			baseinfo.administrativeRegions.pagingBar.moveFirst();
        },
        exception : function(response) {
          var json = Ext.decode(response.responseText);
        }
    });
}
   
 
/**
 * 作废行政区域的方法：
 */
baseinfo.administrativeRegions.deletesDistrict = function(){
	
	var administrativeResgionsIndex = Ext.getCmp('T_baseinfo-administrativeRegionsindex_content');
	var administrativeResgionsQueryResultPannel= administrativeResgionsIndex.getRegionsRightPannel().getAdministrativeRegionsQueryResultPannel();
	var a_grid = administrativeResgionsQueryResultPannel.getAdministrativeRegionsQueryResultGrid();
	// 获取选中的记录
	var rowObjs = a_grid.getSelectionModel().getSelection();
	if(rowObjs.length <= 0){
		Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tip'),baseinfo.administrativeRegions.i18n('foss.baseinfo.pleaseSelectDeletedRecord'));
		return;
	}
	Ext.MessageBox.buttonText.yes = baseinfo.administrativeRegions.i18n('foss.baseinfo.sure');
	Ext.MessageBox.buttonText.no = baseinfo.administrativeRegions.i18n('foss.baseinfo.cancel');
	
	// 获得国际化字符串：
	var tip1=baseinfo.administrativeRegions.i18n('foss.baseinfo.tipInfo');
	var tip2=baseinfo.administrativeRegions.i18n('foss.baseinfo.areYouSureToVoid');
	Ext.Msg.confirm(tip1,tip2,
		function(btn,text) {
			if (btn == 'yes') {
				var ids = '';
				// 将id通过,分隔：
				for ( var i = 0; i < rowObjs.length; i++) {
					ids = ids  +rowObjs[i].data.code+ ",";
				}
				ids=ids.substring(0,ids.length-1);
				//发送作废结点的Ajax请求.
				var administrativeRegionsVo = new Object();
				administrativeRegionsVo.administrativeRegionsDetail = new Object();
				administrativeRegionsVo.administrativeRegionsDetail.code = ids;
				var params = {'administrativeRegionsVo':administrativeRegionsVo};
				Ext.Ajax.request({
					url: baseinfo.realPath('deleteAdministrativeRegionsMore.action'),
					jsonData:params,
					//作废成功
					success : function(response) {
		                var json = Ext.decode(response.responseText);
		                //保存成功之后，移除表格中被作废的记录：
		                /**/
		                //如果树型结构中存在这个结点，则 移除：
		                var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
		            	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
		            	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
		                var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
		                for ( var i = 0; i < rowObjs.length; i++) {
							var currNode= administrativeRegionsTreeStore.getNodeById(rowObjs[i].data.code);
							if(currNode){
								currNode.remove();
							}
						}
						//删除完成后，将“从表格删除”改为刷新表格：
						baseinfo.administrativeRegions.pagingBar.moveFirst();
						top.Ext.MessageBox.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), baseinfo.administrativeRegions.i18n('foss.baseinfo.voidSuccessNo'));
		            },
		            //保存失败
	                exception : function(response) {
	                    var json = Ext.decode(response.responseText);
	                    top.Ext.MessageBox.alert("save fail",json.message);
	                }
				});
			}
		}
	);

	
}

/**
 * 将“是否是虚拟城市”编码 转成 显示的字符：
 */
baseinfo.administrativeRegions.administrativeRegionsToDis=function(administrativeRegionss){
	for(var i=0,leng=administrativeRegionss.length;i<leng;i++){
		administrativeRegionss[i].virtualDistrictId=administrativeRegionss[i].virtualDistrictId=="0"?baseinfo.administrativeRegions.i18n('foss.baseinfo.no'):baseinfo.administrativeRegions.i18n('foss.baseinfo.yes');
		administrativeRegionss[i].degree=baseinfo.administrativeRegions.administrativeRegionsLevelToDis(administrativeRegionss[i].degree);
	}
	return administrativeRegionss;
}
/**
 * 将“是否是虚拟城市”显示的字符 转成 编码：
 */
baseinfo.administrativeRegions.administrativeRegionsToCode=function(administrativeRegionss){
	for(var i=0,leng=administrativeRegionss.length;i<leng;i++){
		administrativeRegionss[i].virtualDistrictId=(administrativeRegionss[i].virtualDistrictId==baseinfo.administrativeRegions.i18n('foss.baseinfo.no')?"0":"1");
		var level=administrativeRegionss[i].degree;
		administrativeRegionss[i].degree=baseinfo.administrativeRegions.administrativeRegionsLevelToCode(level);
	}
	return administrativeRegionss;
}
/**
 * 将“是否是虚拟城市”显示的字符 转成 编码(CHAR TO CODE)：
 */
baseinfo.administrativeRegions.isVirtaulToCode=function(a_char){
	return a_char==baseinfo.administrativeRegions.i18n('foss.baseinfo.yes')?'Y':a_char==baseinfo.administrativeRegions.i18n('foss.baseinfo.no')?'N':a_char;
}

/**
 * 将“行政级别”编码 转成 显示的字符：
 */
baseinfo.administrativeRegions.administrativeRegionsLevelToDis=function(p_code){
	p_code=p_code=="NATION"?baseinfo.administrativeRegions.i18n('foss.baseinfo.country'):
		p_code=="DISTRICT_PROVINCE"?baseinfo.administrativeRegions.i18n('foss.baseinfo.province'):
			p_code=="CITY"?baseinfo.administrativeRegions.i18n('foss.baseinfo.city'):
				p_code=="DISTRICT_COUNTY"?baseinfo.administrativeRegions.i18n('foss.baseinfo.county'):
					p_code=="TOWN_STREET_AGENCY"?baseinfo.administrativeRegions.i18n('foss.baseinfo.town_street'):
						p_code;
	return p_code;
}

/**
 * 将“行政级别”显示的字符 转成 编码：
 */
baseinfo.administrativeRegions.administrativeRegionsLevelToCode=function(p_dis){
	p_dis= p_dis==baseinfo.administrativeRegions.i18n('foss.baseinfo.country')?"NATION":
		p_dis==baseinfo.administrativeRegions.i18n('foss.baseinfo.province')?"DISTRICT_PROVINCE":
			p_dis==baseinfo.administrativeRegions.i18n('foss.baseinfo.city')?"CITY":
				p_dis==baseinfo.administrativeRegions.i18n('foss.baseinfo.county')?"DISTRICT_COUNTY":
					p_dis==baseinfo.administrativeRegions.i18n('foss.baseinfo.town_street')?"TOWN_STREET_AGENCY":
						p_dis;
	return p_dis;
}

/**
 * 将“行政级别”显示的字符 转成 编码：
 */
baseinfo.administrativeRegions.flushNodeById = function(p_id){ 
	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
	var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
	var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
    var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
	var a_node = administrativeRegionsTreeStore.getNodeById(p_id);
	if(!a_node){
		a_node = administrativeRegionsTreeStore.getRootNode();
	}
	if(a_node){
		administrativeRegionsTreeStore.load({'node': a_node});	
	}
}

/**
 * 树节点操作
 */
baseinfo.administrativeRegions.treeNodeOperator=function(me,record){
	var a_code=record.data.id;
	// 如果是根结点，点击时不响应
	if(a_code =='01'){
		return ;
	}
	var params = {
			"administrativeRegionsVo.administrativeRegionsDetail.code":a_code 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryAdministrativeRegionsByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var a_entityDetail = json.administrativeRegionsVo.administrativeRegionsDetail;
			var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
			var administrativeRegionsRightPanel = administrativeRegionsindex.getRegionsRightPannel();
			var administrativeRegionsRightQueryResultPannel = administrativeRegionsRightPanel.getAdministrativeRegionsQueryResultPannel();
			
			var detailForm = administrativeRegionsRightPanel.getAdministrativeRegionsDetailForm(); 
			detailForm.setVisible(true);
			administrativeRegionsRightQueryResultPannel.setVisible(false);
			var districtModel = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', a_entityDetail); 
			detailForm.getForm().loadRecord(districtModel);
		}
	});
}

/**
*行政区域界面数据模型定义
*/
Ext.define('Foss.baseinfo.administrativeRegions.DistrictModel',{
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
		{name:'regionsuffix',type:'string'}

	]
});
//左侧面板-定义行政区域TreeStore
Ext.define('Foss.baseinfo.administrativeRegions.AdministrativeRegionsTreeStore',{
  	extend: 'Ext.data.TreeStore', 
  	root: {
		//根的文本信息
		text:baseinfo.administrativeRegions.i18n('foss.baseinfo.district'),
		//设置根节点的ID
		id : '01',
		//根节点是否展开
		expanded: true
	},
	//设置一个代理，通过读取内存数据
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryByParentDistrictCode.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	},
  	nodeParam: 'administrativeRegionsVo.administrativeRegionsDetail.parentDistrictCode'
});    
//左侧面板-定义行政区域Tree
Ext.define('Foss.baseinfo.administrativeRegions.AdministrativeRegionsTree',{
    extend:'Ext.tree.Panel',
    title: baseinfo.administrativeRegions.i18n('foss.baseinfo.district'),
    margin: false,
    cls: 'autoHeight',
	bodyCls: 'autoHeight',
    autoScroll: true,
    animate: false,
    useArrows: true,
    frame: false,
    rootVisible: true,
    // 树要给高度才有滚动条
    height: 650,
    columnWidth: 1,
    store: Ext.create('Foss.baseinfo.administrativeRegions.AdministrativeRegionsTreeStore'),
    defaults: {
		margin:'5 5 5 5'
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
				baseinfo.administrativeRegions.treeNodeOperator(me,record); 
			},
	      //右键单击
	      itemcontextmenu:function(node,record,item,index,e){
	        e.preventDefault();
	      }
	    };
		me.callParent([cfg]);
	}
});  

//查询面板，包括查询输入框，查询按钮：
Ext.define('Foss.baseinfo.administrativeRegions.AdminnistrativeRegionsLeftPannel',{
	extend:'Ext.panel.Panel',
	height: 680,
	frame: true,
	layout:'column',
	columnWidth: 0.25,
	defaultType : 'textfield',
	//定义组织树方法
	administrativeRegionsTree: null,
	getAdministrativeRegionsTree: function(){
		if(this.administrativeRegionsTree == null){
			this.administrativeRegionsTree = Ext.create('Foss.baseinfo.administrativeRegions.AdministrativeRegionsTree');
		}
		return this.administrativeRegionsTree;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getAdministrativeRegionsTree()];  
		me.callParent([cfg]);
	}
});

//右边模块-新增按钮面板
Ext.define('Foss.baseinfo.administrativeRegions.AdministrativeRegionsAddButtonForm',{
	extend:'Ext.form.Panel',
	layout:'column',
	columnWidth: 0.98,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:.99,
				layout : 'column',
				items:[
					Ext.create('Ext.button.Button',{
						xtype : 'button',
						hidden : false,
						cls:'yellow_button',
						disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsAddButton'),
			  			hidden:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsAddButton'),
						name: 'administrativeRegions_add',
						text: baseinfo.administrativeRegions.i18n('foss.baseinfo.add'),
						columnWidth:.14, 
						margin:'0,10,0,0',
						width:50,
						handler: function() {
							baseinfo.administrativeRegions.addOperator();
						}
					})
				]
			})	
		];
	    me.callParent([cfg]);

	}
});

//右边模块-查询条件面板
Ext.define('Foss.baseinfo.administrativeRegions.AdministrativeRegionsQueryForm',{
	extend:'Ext.form.Panel',  
	layout: {
		type : 'table',
		columns : 3
	},
	frame : true,
	collapsible : true,
	title: baseinfo.administrativeRegions.i18n('foss.baseinfo.queryCondition'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '100%',
		labelWidth : 100,
		width : 100
	},
	height : 130,
	defaultType : 'textfield',
	layout : 'column',
	items : [{
			name:'name',
			fieldLabel:baseinfo.administrativeRegions.i18n('foss.baseinfo.districtFullName'),
			columnWidth : 0.35,
			labelWidth : 70
		},{
			name:'simpleName',
			columnWidth : 0.3,
			labelWidth : 70,
			fieldLabel:baseinfo.administrativeRegions.i18n('foss.baseinfo.simple')
		},{
			name:'virtualDistrictId',
			xtype:'combobox',
			fieldLabel:baseinfo.administrativeRegions.i18n('foss.baseinfo.virtualDistrict'),
			queryMode: 'local',
			displayField: 'name',
			value:'',
			valueField:'code', 
			columnWidth : 0.3,
			store: Ext.create('Ext.data.Store',{
				//定义字段
				fields: [
					{name: 'code',type:'string'},
					{name: 'name',type:'string'}
				],
				data: {
					'items':[
						{"code":"", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.all')},
						{"code":"Y", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.yes')},
						{"code":"N", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.no')}
					]
				},	
				proxy: {
					type: 'memory',
					reader: {
						type: 'json',
						root: 'items'
					}
				}
			})	
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text: baseinfo.administrativeRegions.i18n('foss.baseinfo.reset'),//重置   
				  columnWidth:.12,
				  handler: function() {
						this.up('form').getForm().reset();
					}
			  	},{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.76
				},
			  	{
				  text: baseinfo.administrativeRegions.i18n('foss.baseinfo.query'),//查询
				  columnWidth:.12,
				  cls:'yellow_button',
				  disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsQueryButton'),
				  hidden:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsQueryButton'),
				  text: baseinfo.administrativeRegions.i18n('foss.baseinfo.query'),
				  //查询按钮的响应事件：
				  handler: function() { 
					  	var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
						var administrativeRegionsRightPanel = administrativeRegionsindex.getRegionsRightPannel();
						var administrativeRegionsDetailFormPannel = administrativeRegionsRightPanel.getAdministrativeRegionsDetailForm();
						var administrativeRegionsRightQueryResultPannel = administrativeRegionsRightPanel.getAdministrativeRegionsQueryResultPannel();
						administrativeRegionsDetailFormPannel.setVisible(false);
						administrativeRegionsRightQueryResultPannel.setVisible(true);
						baseinfo.administrativeRegions.pagingBar.moveFirst();
					}
			  	}]
		}]
});

//右边模块-查询结果查询的显示表格Grid 
//：
Ext.define('Foss.baseinfo.administrativeRegions.administrativeRegionsQueryResultGrid',{
	extend: 'Ext.grid.Panel', 
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:.99, 
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
	enableColumnHide:false,
	// 设置“如果查询的结果为空，则提示”
	emptyText: baseinfo.administrativeRegions.i18n('foss.baseinfo.queryResultIsNull'),
	//增加滚动条
	autoScroll : false,
	stripeRows : true, // 交替行效果
	collapsible: true,
	animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	//selModel : Ext.create('Ext.selection.RowModel'),
	selModel: Ext.create('Ext.selection.CheckboxModel'),
	//表格行可展开的插件
	plugins: [{
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Foss.baseinfo.administrativeRegions.GridDetail'
	}],
	columns : [
		{
          xtype:'actioncolumn',
          flex: 1,
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.operate'),
			align: 'center',
          items: [{
          	iconCls:'deppon_icons_edit',
              tooltip: baseinfo.administrativeRegions.i18n('foss.baseinfo.update'),
              disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsUpdateButton'),
              handler: function(grid, rowIndex, colIndex) {
            	  baseinfo.administrativeRegions.updateOperator(grid, rowIndex, colIndex); 
              	}
              },{
          	iconCls:'deppon_icons_cancel',
              tooltip: baseinfo.administrativeRegions.i18n('foss.baseinfo.void'),
              disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsDisableButton'),
              handler: function(grid, rowIndex, colIndex) {
            	  baseinfo.administrativeRegions.cancelOperator(grid, rowIndex, colIndex);   
               }
              }]
      },{
			text : baseinfo.administrativeRegions.i18n('foss.baseinfo.code'),
			align: 'center',
			flex: 1.2,
			dataIndex :'code'
		},{
			text : baseinfo.administrativeRegions.i18n('foss.baseinfo.districtFullName'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex : 'name'
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.simple'),
			align: 'center',
			flex: 1,
			dataIndex: 'simpleName'
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.avalibleName'),
			align: 'center',
			flex: 1.2,
			xtype: 'ellipsiscolumn',
			dataIndex: 'availableName'
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.upRegionBr'),
			align: 'center',
			flex: 0.8,
			xtype: 'ellipsiscolumn',
			dataIndex: 'parentDistrictName'
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.virtualRegionBr'),
			align: 'center',
			flex: 0.8,
			xtype: 'ellipsiscolumn',
			dataIndex: 'virtualDistrictId',
			renderer: function(value) {
				value=value=='N'?baseinfo.administrativeRegions.i18n('foss.baseinfo.no'):value=='Y'?baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'):value;
				return value;
			}
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.regionBr'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'degree',
			// 由于renderer被覆盖，扩展一个rendererx方法来解决，用于数据显示前进行一些处理，比如转码：
			renderer: function(value) {				
				value=baseinfo.administrativeRegions.administrativeRegionsLevelToDis(value);
				return value;
			}
		},{
			text: baseinfo.administrativeRegions.i18n('foss.baseinfo.isHotCity'),
			align: 'center',
			flex: 0.8,
			xtype: 'ellipsiscolumn',
			dataIndex: 'isHotCity',
			renderer: function(value) {
				value=value=='N'?baseinfo.administrativeRegions.i18n('foss.baseinfo.no'):value=='Y'?baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'):value;
				return value;
			}
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		// "../baseinfo/queryAdministrativeRegionsByEntity.action"
		var r_url = "queryAdministrativeRegionsByEntity.action";
		r_url=	baseinfo.realPath(r_url) ;
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.administrativeRegions.DistrictModel',
			pageSize:10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				url : r_url,
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'administrativeRegionsVo.administrativeRegionsList',
					totalProperty : 'totalCount'
				}
			},
			constructor: function(config){
				var me = this,
					cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			},
			listeners: {
				beforeload : function(store, operation, eOpts) {
					var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
					var administrativeRegionsRightPanel = administrativeRegionsindex.getRegionsRightPannel();
					var queryForm = administrativeRegionsRightPanel.getAdministrativeRegionsQueryForm();
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'administrativeRegionsVo.administrativeRegionsDetail.name' : queryParams.name,
								'administrativeRegionsVo.administrativeRegionsDetail.simpleName' : queryParams.simpleName,
								'administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId' : queryParams.virtualDistrictId
							}
						});	
					}
				}
			}
		});
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.administrativeRegions.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
//弹出框 修改和保存FORM
Ext.define('Foss.baseinfo.administrativeRegions.DictModifyForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.administrativeRegions.i18n('foss.baseinfo.addUpdateDistrict'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	columnWidth: 0.99,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
	operatorType:null,
	initFormFlag:null,
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
			{
				name: 'code',
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.code'),
				allowBlank:false,
				columnWidth: 0.40,
				listeners :{'blur':function(me,theEvent,eOpts){
					if(baseinfo.administrativeRegions.flagAddModify=='add'&&me!=null&&me.value!=''){
						//判断是否有重复的：
						var record = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel');
						record.data.code = me.value;
						
						var administrativeRegionsVo = new Object();
						administrativeRegionsVo.administrativeRegionsDetail = record.data
						var params = {'administrativeRegionsVo':administrativeRegionsVo};
						var r_url = "queryAdministrativeRegionsByCode.action";
						r_url=	baseinfo.realPath(r_url) ;
						Ext.Ajax.request({
							url: r_url,
							jsonData: params,
							success : function(response) {
				                var json = Ext.decode(response.responseText);
				                if(json){
				                	var r_administrativeRegionsDetail=json.administrativeRegionsVo.administrativeRegionsDetail;
					                if(r_administrativeRegionsDetail&&r_administrativeRegionsDetail.code&&r_administrativeRegionsDetail.code!=''){
					                	var tipStr=baseinfo.administrativeRegions.i18n('foss.baseinfo.codeHasExist')+me.value+" 编码对应的行政区域的名称是："
					                			+r_administrativeRegionsDetail.name;
										top.Ext.MessageBox.alert(tipStr);
										me.focus();
					                }
				                }
				            },
			                exception : function(response) {
			                }
						});
					}
				}}
			},{
				name: 'name',
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtFullName'),
				allowBlank:false,
				columnWidth: 0.40
			},{
				// 后缀 
				xtype: 'combobox',
				name: 'regionsuffix',
				value: 'SUFFIX_CITY',
				labelWidth:0,
				fieldLabel:'',
				queryMode: 'local',
				displayField: 'name',
				valueField:'code', 
				margin:'5 10 5 10',
				columnWidth:.15,			
				store: Ext.create('Ext.data.Store',{
					//定义字段
					fields: [
						{name: 'code',type:'string'},
						{name: 'name',type:'string'}
					],
					data: {
						'items':[
							{"code":"SUFFIX_NATION", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.country')},
							{"code":"SUFFIX_PROVINCE", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.province')},
							{"code":"SUFFIX_ZIZHIZHOU", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.autonomousPrefecture')},
							{"code":"SUFFIX_CITY", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.city')},
							{"code":"SUFFIX_QU", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.qu')},
							{"code":"SUFFIX_XIAN", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.xian')},
							{"code":"SUFFIX_TOWN", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.town')},
							{"code":"SUFFIX_STREET", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.street')}
						]
					},	
					proxy: {
						type: 'memory',
						reader: {
							type: 'json',
							root: 'items'
						}
					}
				}),      
				/**
				 * 暂时无用
				 */
				listeners: {  
					beforerender: function(me, eOpts){  
				　　		var firstValue = me.store.data.items[0];
				　　		if(firstValue){
				　　			me.setValue(firstValue.data.code);
				　　		}
					}
			
				}
			},{
			  name: 'simpleName',
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.simple'),
			  allowBlank:false,
			  columnWidth: 0.40
			},{
			  name: 'availableName',
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.avalibleName'),
			  allowBlank:false,
			  columnWidth:.4
			},
			{
			  xtype: 'radiogroup', 
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.virtualDistrict'),
			  allowBlank:false,
			  height: 24,
			  columnWidth:.4,
			  items: [
				{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'), name: 'virtualDistrictId', inputValue: 'Y'},
				{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'), name: 'virtualDistrictId', inputValue: 'N', checked: true}
			  ]
			},{
				//复选框组：
				xtype: 'radiogroup',
				vertical:true,
				allowBlank:false,
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtLevel'),	
				height: 24,
				columnWidth:.6,	
				items: [
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.country'), name: 'degree', inputValue: 'NATION'},
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.province'), name: 'degree', inputValue: 'DISTRICT_PROVINCE' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.city'), name: 'degree', inputValue: 'CITY' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.county'), name: 'degree', inputValue: 'DISTRICT_COUNTY' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.town_street'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY' ,checked: true}
				],
				listeners : {
					change : function(_this, newValue, oldValue, eOpts ){
						var a_isChange=true;
						if(!a_isChange){
							return;
						}
						var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
						var a_form = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
						var a_parentDistrict = a_form.getForm().findField("parentDistrictCode");
						var a_upDegree = newValue.degree;
						var a_degree = newValue.degree;
						
						a_parentDistrict.setReadOnly(false);
						
						if(a_degree=='NATION'){
							a_parentDistrict.allowBlank=true;
							a_parentDistrict.setCombValue(baseinfo.administrativeRegions.i18n('foss.baseinfo.district'), "01");
							a_parentDistrict.setReadOnly(true);
							return;
						}else if(a_degree=='DISTRICT_PROVINCE'){
							a_upDegree = 'NATION';
						}else if(a_degree=='CITY'){
							a_upDegree = 'DISTRICT_PROVINCE';
						}else if(a_degree=='DISTRICT_COUNTY'){
							a_upDegree = 'CITY';
						}else if(a_degree=='TOWN_STREET_AGENCY'){
							a_upDegree = 'DISTRICT_COUNTY';
						}
						// 重写公共选择框的方法
						if(me.initFormFlag != baseinfo.administrativeRegions.OP_TYPE_INIT){
							a_parentDistrict.setCombValue(null, null);
							a_parentDistrict.store.removeAll();
						}
						a_parentDistrict.store.removeListener('beforeload');
						a_parentDistrict.store.addListener('beforeload', function(store, operation, eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							if(!Ext.isEmpty(a_upDegree)){
								searchParams['cityVo.degree'] = a_upDegree;
							} 
	        			});
						a_parentDistrict.store.loadPage(1,{
					      callback: function() {
					    	  if(me.initFormFlag == baseinfo.administrativeRegions.OP_TYPE_INIT){
					    		  parentDistrictCodeObj = a_form.getForm().findField('parentDistrictCode');
					    		  var disName = parentDistrictCodeObj.disName;
					    		  var disValue = parentDistrictCodeObj.disValue;
					    		  a_form.getForm().findField('parentDistrictCode').setCombValue(disName,disValue);	
					    	  }else{
					    		  a_parentDistrict.setValue(null); 
					    		  a_parentDistrict.setRawValue(null); 				    		  
					    	  }
					    	  me.initFormFlag=null;
					       } 
					    }); 
					}
				}
			},
			{
				xtype : 'commonreginselector',
				name:'parentDistrictCode',
				allowBlank : false, 
				disName:null,
				disValue:null,
				fieldLabel : baseinfo.administrativeRegions.i18n('foss.baseinfo.upDistrict'),
				degree:null,//省市县镇类别
				margin:'5 20 5 10',
			    columnWidth:.4
			},
			{
				  xtype: 'radiogroup', 
				  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.hotCity'),
				  allowBlank:false,
				  height: 24,
				  columnWidth:.4,
				  items: [
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'), name: 'isHotCity', inputValue: 'Y'},
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'), name: 'isHotCity', inputValue: 'N', checked: true}
				  ]
				},{
				border: 1,
				xtype:'container', 
				defaultType:'button',
				height : 60,
				columnWidth : 1, 
				layout:'column',
				items:[{
				      text: baseinfo.administrativeRegions.i18n('foss.baseinfo.cancel'),
					  columnWidth : .15,
					  handler: function(){ 
						  baseinfo.administrativeRegions.dictModifyWindow.hide();
					   }
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.administrativeRegions.i18n('foss.baseinfo.reset'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							baseinfo.administrativeRegions.reset(me);
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.administrativeRegions.i18n('foss.baseinfo.save'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							if(baseinfo.administrativeRegions.OP_TYPE_ADD==me.operatorType){
								baseinfo.administrativeRegions.saveAddOperator(me);
							}else if(baseinfo.administrativeRegions.OP_TYPE_UPDATE==me.operatorType){
								baseinfo.administrativeRegions.saveUpdateOperator(me);
							}
						}
				  	}]
				}  
		];
		me.callParent([cfg]);
	}
});

//弹出框 修改和保存FORM
Ext.define('Foss.baseinfo.administrativeRegions.AddDictModifyForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.administrativeRegions.i18n('foss.baseinfo.addUpdateDistrict'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	columnWidth: 0.99,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
//	operatorType:null,
//	initFormFlag:null,
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
			{
				name: 'code',
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.code'),
				allowBlank:false,
				columnWidth: 0.40,
				listeners :{'blur':function(me,theEvent,eOpts){
					if(baseinfo.administrativeRegions.flagAddModify=='add'&&me!=null&&me.value!=''){
						//判断是否有重复的：
						var record = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel');
						record.data.code = me.value;
						
						var administrativeRegionsVo = new Object();
						administrativeRegionsVo.administrativeRegionsDetail = record.data
						var params = {'administrativeRegionsVo':administrativeRegionsVo};
						var r_url = "queryAdministrativeRegionsByCode.action";
						r_url=	baseinfo.realPath(r_url) ;
						Ext.Ajax.request({
							url: r_url,
							jsonData: params,
							success : function(response) {
				                var json = Ext.decode(response.responseText);
				                if(json){
				                	var r_administrativeRegionsDetail=json.administrativeRegionsVo.administrativeRegionsDetail;
					                if(r_administrativeRegionsDetail&&r_administrativeRegionsDetail.code&&r_administrativeRegionsDetail.code!=''){
					                	var tipStr=baseinfo.administrativeRegions.i18n('foss.baseinfo.codeHasExist')+me.value+" 编码对应的行政区域的名称是："
					                			+r_administrativeRegionsDetail.name;
										top.Ext.MessageBox.alert(tipStr);
										me.focus();
					                }
				                }
				            },
			                exception : function(response) {
			                }
						});
					}
				}}
			},{
				name: 'name',
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtFullName'),
				allowBlank:false,
				columnWidth: 0.40
			},{
				// 后缀 
				xtype: 'combobox',
				name: 'regionsuffix',
				value: 'SUFFIX_CITY',
				labelWidth:0,
				fieldLabel:'',
				queryMode: 'local',
				displayField: 'name',
				valueField:'code', 
				margin:'5 10 5 10',
				columnWidth:.15,			
				store: Ext.create('Ext.data.Store',{
					//定义字段
					fields: [
						{name: 'code',type:'string'},
						{name: 'name',type:'string'}
					],
					data: {
						'items':[
							{"code":"SUFFIX_NATION", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.country')},
							{"code":"SUFFIX_PROVINCE", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.province')},
							{"code":"SUFFIX_ZIZHIZHOU", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.autonomousPrefecture')},
							{"code":"SUFFIX_CITY", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.city')},
							{"code":"SUFFIX_QU", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.qu')},
							{"code":"SUFFIX_XIAN", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.xian')},
							{"code":"SUFFIX_TOWN", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.town')},
							{"code":"SUFFIX_STREET", "name":baseinfo.administrativeRegions.i18n('foss.baseinfo.street')}
						]
					},	
					proxy: {
						type: 'memory',
						reader: {
							type: 'json',
							root: 'items'
						}
					}
				}),      
				/**
				 * 暂时无用
				 */
				listeners: {  
					beforerender: function(me, eOpts){  
				　　		var firstValue = me.store.data.items[0];
				　　		if(firstValue){
				　　			me.setValue(firstValue.data.code);
				　　		}
					}
			
				}
			},{
			  name: 'simpleName',
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.simple'),
			  allowBlank:false,
			  columnWidth: 0.40
			},{
			  name: 'availableName',
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.avalibleName'),
			  allowBlank:false,
			  columnWidth:.4
			},
			{
			  xtype: 'radiogroup', 
			  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.virtualDistrict'),
			  allowBlank:false,
			  height: 24,
			  columnWidth:.4,
			  items: [
				{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'), name: 'virtualDistrictId', inputValue: 'Y'},
				{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'), name: 'virtualDistrictId', inputValue: 'N', checked: true}
			  ]
			},{
				//复选框组：
				xtype: 'radiogroup',
				vertical:true,
				allowBlank:false,
				fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtLevel'),	
				height: 24,
				columnWidth:.6,	
				items: [
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.country'), name: 'degree', inputValue: 'NATION'},
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.province'), name: 'degree', inputValue: 'DISTRICT_PROVINCE' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.city'), name: 'degree', inputValue: 'CITY' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.county'), name: 'degree', inputValue: 'DISTRICT_COUNTY' },
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.town_street'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY' ,checked: true}
				],
				listeners : {
					change : function(_this, newValue, oldValue, eOpts ){
						var a_isChange=true;
						if(!a_isChange){
							return;
						}
						var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
						var a_form = baseinfo.administrativeRegions.dictModifyWindow.getDictMidifyUpdateForm();
						var a_parentDistrict = a_form.getForm().findField("parentDistrictCode");
						var a_upDegree = newValue.degree;
						var a_degree = newValue.degree;
						
						a_parentDistrict.setReadOnly(false);
						
						if(a_degree=='NATION'){
							a_parentDistrict.allowBlank=true;
							a_parentDistrict.setCombValue(baseinfo.administrativeRegions.i18n('foss.baseinfo.district'), "01");
							a_parentDistrict.setReadOnly(true);
							return;
						}else if(a_degree=='DISTRICT_PROVINCE'){
							a_upDegree = 'NATION';
						}else if(a_degree=='CITY'){
							a_upDegree = 'DISTRICT_PROVINCE';
						}else if(a_degree=='DISTRICT_COUNTY'){
							a_upDegree = 'CITY';
						}else if(a_degree=='TOWN_STREET_AGENCY'){
							a_upDegree = 'DISTRICT_COUNTY';
						}
						// 重写公共选择框的方法
						if(me.initFormFlag != baseinfo.administrativeRegions.OP_TYPE_INIT){
							a_parentDistrict.setCombValue(null, null);
							a_parentDistrict.store.removeAll();
						}
						a_parentDistrict.store.removeListener('beforeload');
						a_parentDistrict.store.addListener('beforeload', function(store, operation, eOpts) {
							var searchParams = operation.params;
							if (Ext.isEmpty(searchParams)) {
								searchParams = {};
								Ext.apply(operation, {
									params : searchParams
								});
							}
							if(!Ext.isEmpty(a_upDegree)){
								searchParams['cityVo.degree'] = a_upDegree;
							} 
	        			});
						a_parentDistrict.store.loadPage(1,{
					      callback: function() {
					    	  if(me.initFormFlag == baseinfo.administrativeRegions.OP_TYPE_INIT){
					    		  parentDistrictCodeObj = a_form.getForm().findField('parentDistrictCode');
					    		  var disName = parentDistrictCodeObj.disName;
					    		  var disValue = parentDistrictCodeObj.disValue;
					    		  a_form.getForm().findField('parentDistrictCode').setCombValue(disName,disValue);	
					    	  }else{
					    		  a_parentDistrict.setValue(null); 
					    		  a_parentDistrict.setRawValue(null); 				    		  
					    	  }
					    	  me.initFormFlag=null;
					       } 
					    }); 
					}
				}
			},
			{
				xtype : 'commonreginselector',
				name:'parentDistrictCode',
				allowBlank : false, 
				disName:null,
				disValue:null,
				fieldLabel : baseinfo.administrativeRegions.i18n('foss.baseinfo.upDistrict'),
				degree:null,//省市县镇类别
				margin:'5 20 5 10',
			    columnWidth:.4
			},
			{
				  xtype: 'radiogroup', 
				  fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.hotCity'),
				  allowBlank:false,
				  height: 24,
				  columnWidth:.4,
				  items: [
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'), name: 'isHotCity', inputValue: 'Y'},
					{boxLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'), name: 'isHotCity', inputValue: 'N', checked: true}
				  ]
				},{
				border: 1,
				xtype:'container', 
				defaultType:'button',
				height : 60,
				columnWidth : 1, 
				layout:'column',
				items:[{
				      text: baseinfo.administrativeRegions.i18n('foss.baseinfo.cancel'),
					  columnWidth : .15,
					  handler: function(){ 
						  baseinfo.administrativeRegions.dictModifyWindow.hide();
					   }
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.administrativeRegions.i18n('foss.baseinfo.reset'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							baseinfo.administrativeRegions.reset(me);
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.administrativeRegions.i18n('foss.baseinfo.save'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){

							var administrativeRegionsDetailForm = baseinfo.administrativeRegions.addDictModifyWindow.getDictMidifyUpdateForm();
							if(!administrativeRegionsDetailForm.getForm().isValid()){
								return;
							}
							var msgTip=baseinfo.administrativeRegions.i18n('foss.baseinfo.addSuccessful');
							// 当前表单中的数据：
							var administrativeRegionsObj = administrativeRegionsDetailForm.getForm().getValues(); 
							var administrativeRegionsindex = Ext.getCmp("T_baseinfo-administrativeRegionsindex_content");
							var administrativeRegionsLeftPanel=administrativeRegionsindex.getRegionsLeftPannel();
							var administrativeRegionsTree = administrativeRegionsLeftPanel.getAdministrativeRegionsTree();
						    var administrativeRegionsTreeStore = administrativeRegionsTree.getStore();
							// 被操作的结点的上级
							var parentNode= administrativeRegionsTreeStore.getNodeById(administrativeRegionsObj.parentDistrictCode);
							if(administrativeRegionsObj.parentDistrictCode==null||administrativeRegionsObj.parentDistrictCode==''){
								parentNode=administrativeRegionsTreeStore.getRootNode();
							}	  
							
							// 展开父结点
							if(parentNode){
								parentNode.expand();
							}
							// 修改树结点
							if(parentNode&&parentNode.data.leaf){
							    parentNode.data.leaf = false;
							}
							var addedNode = {
							    'id':administrativeRegionsObj.code,
							    'parentId':administrativeRegionsObj.parentDistrictCode,
							    "text": administrativeRegionsObj.name,
							    'expanded': true,
							    "leaf": true
							};
							//发送保存新结点的Ajax请求.
							var administrativeRegionsVo = new Object();
							var a_model = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModel', administrativeRegionsObj);
							administrativeRegionsVo.administrativeRegionsDetail = a_model.data
							administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId = baseinfo.administrativeRegions.isVirtaulToCode(administrativeRegionsVo.administrativeRegionsDetail.virtualDistrictId);
							var params = {'administrativeRegionsVo':administrativeRegionsVo};
						    Ext.Ajax.request({
						        url : baseinfo.realPath('addAdministrativeRegions.action'),
						        jsonData:params,
						        success : function(response) {
						          	var json = Ext.decode(response.responseText);
									//将被添加的节点插入到树型结构中：	
									if(parentNode!=null){
										var length = parentNode.childNodes.length;
										parentNode.insertChild(length, addedNode);
										parentNode.lastChild.raw = addedNode; 		
										parentNode.set('loaded', true);	
									}
									var administrativeRegionsForm=baseinfo.administrativeRegions.addDictModifyWindow.getDictMidifyUpdateForm();
									administrativeRegionsForm.getForm().reset();
									//刷新树结点：
									baseinfo.administrativeRegions.flushNodeById(administrativeRegionsObj.code);
									Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), msgTip);
									//点击保存后，清空表单数据，并隐藏表单：
									baseinfo.administrativeRegions.dictModifyWindow.hide();
									baseinfo.administrativeRegions.pagingBar.moveFirst();
						        },
						        exception : function(response) {
						          var json = Ext.decode(response.responseText);
						          Ext.Msg.alert(baseinfo.administrativeRegions.i18n('foss.baseinfo.tips'), json.message);
						        }
						    });

//							if(baseinfo.administrativeRegions.OP_TYPE_ADD==me.operatorType){
//								baseinfo.administrativeRegions.saveAddOperator(me);
//							}else if(baseinfo.administrativeRegions.OP_TYPE_UPDATE==me.operatorType){
//								baseinfo.administrativeRegions.saveUpdateOperator(me);
//							}
						}
				  	}]
				}  
		];
		me.callParent([cfg]);
	}
});


Ext.define('Foss.baseinfo.administrativeRegions.DistrictModifyWindow',{
	extend: 'Ext.window.Window', 
    width: 800,
	closeAction:'hide',
	layout : 'column', 
	dictMidifyForm:null,
	getDictMidifyUpdateForm:function(){
		if(Ext.isEmpty(this.dictMidifyForm)){
			this.dictMidifyForm=Ext.create('Foss.baseinfo.administrativeRegions.DictModifyForm');
		}
		return this.dictMidifyForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getDictMidifyUpdateForm()];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.baseinfo.administrativeRegions.AddDistrictModifyWindow',{
	extend: 'Ext.window.Window', 
    width: 800,
	closeAction:'hide',
	layout : 'column', 
	dictMidifyForm:null,
	getDictMidifyUpdateForm:function(){
		if(Ext.isEmpty(this.dictMidifyForm)){
			this.dictMidifyForm=Ext.create('Foss.baseinfo.administrativeRegions.AddDictModifyForm');
		}
		return this.dictMidifyForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getDictMidifyUpdateForm()];
		me.callParent([cfg]);
	}
});
baseinfo.administrativeRegions.dictModifyWindow = Ext.create('Foss.baseinfo.administrativeRegions.DistrictModifyWindow');
baseinfo.administrativeRegions.addDictModifyWindow = Ext.create('Foss.baseinfo.administrativeRegions.AddDistrictModifyWindow');
//右边模块-中间Grid区域Panel
Ext.define('Foss.baseinfo.administrativeRegions.AdministrativeRegionsQueryResultPannel',{
	extend:'Ext.panel.Panel', 
	layout:'column',
	frame: true,
	hidden:true,
	columnWidth: 0.98,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtList'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},
	administrativeRegionsCannelButton:null,
	getAdministrativeRegionsCannelButton:function(){
		if(Ext.isEmpty(this.administrativeRegionsCannelButton)){
			this.administrativeRegionsCannelButton = Ext.create('Ext.button.Button',{
				xtype : 'button',
				disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsDisableButton'),
				hidden:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsDisableButton'),
				hidden : false,
				name: 'administrativeRegions_deletes',
				text: baseinfo.administrativeRegions.i18n('foss.baseinfo.void'),
				columnWidth:.12, 
				margin:'0,10,0,0',
				width:30,
				handler: function() {
					baseinfo.administrativeRegions.deletesDistrict();
				}
			})
		}
		return this.administrativeRegionsCannelButton;
	},
	administrativeRegionsCannelButton1:null,
	getAdministrativeRegionsCannelButton1:function(){
		if(Ext.isEmpty(this.administrativeRegionsCannelButton1)){
			this.administrativeRegionsCannelButton1 = Ext.create('Ext.button.Button',{
				xtype : 'button',
				disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsDisableButton'),
				hidden:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsDisableButton'),
				hidden : false,
				name: 'administrativeRegions_deletes',
				text: baseinfo.administrativeRegions.i18n('foss.baseinfo.void'),
				columnWidth:.12, 
				margin:'0,10,0,0',
				width:30,
				handler: function() {
					baseinfo.administrativeRegions.deletesDistrict();
				}
			})
		}
		return this.administrativeRegionsCannelButton1;
	},
	administrativeRegionsQueryResultGrid:null,
	getAdministrativeRegionsQueryResultGrid:function(){
		if(Ext.isEmpty(this.administrativeRegionsQueryResultGrid)){ 
			this.administrativeRegionsQueryResultGrid = Ext.create('Foss.baseinfo.administrativeRegions.administrativeRegionsQueryResultGrid');
		}
		return this.administrativeRegionsQueryResultGrid;
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:.99,
				layout : 'column',
				items:[
						me.getAdministrativeRegionsCannelButton1(),
						me.getAdministrativeRegionsQueryResultGrid(),
						me.getAdministrativeRegionsCannelButton() 
					]
			})	
		];
	    me.callParent([cfg]);
	}
});

//右边模块-定义行政区域详情信息Form
Ext.define('Foss.baseinfo.administrativeRegions.DetailForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtInfo'),
	frame: true,
	defaultType : 'textfield',
	columnWidth: 1, 
	layout:{
		type : 'table',
		columns : 2
	},	
	defaults: {
		readOnly : true, 
		margin:'5 20 5 10',
		labelWidth: 120  
	}, 
	items:[{
	   		name: 'code',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.code') 
	     },{
	   		name: 'name',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtFullName') 
	     },{
	   		name: 'simpleName',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.simple') 
	     },{
	   		name: 'availableName',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.avalibleName') 
	     },{
	   		name: 'parentDistrictName',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.upDistrict') 
	     },{
	   		name: 'parentDistrictCode',
	   	    fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtLevel'),
	   	    hidden:true
		 },{
	   		name: 'virtualDistrictId',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.virtualDistrict'),
	   		value: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'),
	   	    listeners: {	
	   			change: function(me, newValue, oldValue, eOpts ){
	   				var a_value=newValue=='N'?baseinfo.administrativeRegions.i18n('foss.baseinfo.no'):newValue=='Y'?baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'):newValue;
	   				if(a_value!=newValue){
	   					me.setValue(a_value);
	   				}
	   			}
	   	    } 
	     },{
	   		name: 'degree',
	   	    fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.districtLevel'),
	   	    listeners: {	
				change: function(me, newValue, oldValue, eOpts ){
					var a_input=newValue;
					var a_value=baseinfo.administrativeRegions.administrativeRegionsLevelToDis(a_input);
					me.setValue(a_value);
				}
		    }
	   	},{
	   		name: 'isHotCity',
	   		fieldLabel: baseinfo.administrativeRegions.i18n('foss.baseinfo.hotCity'),
	   		value: baseinfo.administrativeRegions.i18n('foss.baseinfo.no'),
	   	    listeners: {	
	   			change: function(me, newValue, oldValue, eOpts ){
	   				var a_value=newValue=='N'?baseinfo.administrativeRegions.i18n('foss.baseinfo.no'):newValue=='Y'?baseinfo.administrativeRegions.i18n('foss.baseinfo.yes'):newValue;
	   				if(a_value!=newValue){
	   					me.setValue(a_value);
	   				}
	   			}
	   	    } 
	     },{
	   		xtype : 'container',
	   		border : false, 
	   		html : '&nbsp;'
	   	},{
			border: 1,
			xtype:'container',
			colspan:2,
			columnWidth:1, 
			defaultType:'button',
			layout:'column',
			items:[{
					xtype:'container',
					border:false,
					html:'&nbsp;',
					columnWidth:.7
				},
			  	{ 
				  text: baseinfo.administrativeRegions.i18n('foss.baseinfo.update'),
				  columnWidth:.3,
				  name: 'administrativeRegions_update',
				  cls:'yellow_button',
				  disabled:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsUpateButton'),
				  hidden:!baseinfo.administrativeRegions.isPermission('queryAdministrativeRegionsByEntity/administrativeRegionsUpateButton'),
				  //查询按钮的响应事件：
				  handler: function() {
					  baseinfo.administrativeRegions.updateDetailOperator();
					}
			  	}]
		}],
	   initComponent: function(config){
	   		var me = this,
			cfg = Ext.apply({}, config);
	   		me.callParent([cfg]);
	   }
});
//右边模块的实现的主面板：
Ext.define('Foss.baseinfo.administrativeRegions.AdminnistrativeRegionsRightPannel',{
	extend:'Ext.panel.Panel',  
	cls:'autoHeight',
	bodyCls:'autoHeight',
	defaultType : 'textfield',
	layout:'column',
	frame: true,
	columnWidth: 0.72,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 120
	},
	administrativeRegionsAddButton:null,
	getAdministrativeRegionsAddButton : function(){
		if(Ext.isEmpty(this.administrativeRegionsAddButton)){
			this.administrativeRegionsAddButton= Ext.create('Foss.baseinfo.administrativeRegions.AdministrativeRegionsAddButtonForm');
		}
		return this.administrativeRegionsAddButton;
	},
	administrativeRegionsQueryForm:null,
	getAdministrativeRegionsQueryForm : function(){
		if(Ext.isEmpty(this.administrativeRegionsQueryForm)){
			this.administrativeRegionsQueryForm= Ext.create('Foss.baseinfo.administrativeRegions.AdministrativeRegionsQueryForm');
		}
		return this.administrativeRegionsQueryForm;
	}, 
	administrativeRegionsQueryResultPannel:null,
	getAdministrativeRegionsQueryResultPannel : function(){
		if(Ext.isEmpty(this.administrativeRegionsQueryResultPannel)){
			this.administrativeRegionsQueryResultPannel= Ext.create('Foss.baseinfo.administrativeRegions.AdministrativeRegionsQueryResultPannel');
		}
		return this.administrativeRegionsQueryResultPannel;
	}, 
	administrativeRegionsDetailForm:null,
	getAdministrativeRegionsDetailForm : function(){
		if(Ext.isEmpty(this.administrativeRegionsDetailForm)){
			this.administrativeRegionsDetailForm = Ext.create('Foss.baseinfo.administrativeRegions.DetailForm');
		}
		return this.administrativeRegionsDetailForm;
	}, 
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
		            me.getAdministrativeRegionsAddButton(),
		            me.getAdministrativeRegionsQueryForm(),
		            me.getAdministrativeRegionsQueryResultPannel(),
		            me.getAdministrativeRegionsDetailForm()
		];
		me.callParent([cfg]);
	}
});
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-administrativeRegionsindex_content')) {
		return;
	}
	var adminnistrativeRegionsLeftPannel = Ext.create('Foss.baseinfo.administrativeRegions.AdminnistrativeRegionsLeftPannel');
	var adminnistrativeRegionsRightPannel = Ext.create('Foss.baseinfo.administrativeRegions.AdminnistrativeRegionsRightPannel');
	Ext.getCmp('T_baseinfo-administrativeRegionsindex').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-administrativeRegionsindex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		getRegionsLeftPannel : function() {
			return adminnistrativeRegionsLeftPannel;
		},
		getRegionsRightPannel : function() {
			return adminnistrativeRegionsRightPannel;
		},
		items: [adminnistrativeRegionsLeftPannel,adminnistrativeRegionsRightPannel]
	}));
});