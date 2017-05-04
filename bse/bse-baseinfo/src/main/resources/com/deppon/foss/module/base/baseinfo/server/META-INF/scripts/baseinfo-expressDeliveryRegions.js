baseinfo.expressDeliveryRegions.OP_TYPE_UPDATE ="U"; //修改常量
baseinfo.expressDeliveryRegions.OP_TYPE_ADD ="A"; //新增常量
baseinfo.expressDeliveryRegions.OP_TYPE_INIT ="INIT";
baseinfo.expressDeliveryRegions.NUM_INIT ="001";
/**
 * 将“是否是虚拟城市”编码 转成 显示的字符：
 */
baseinfo.expressDeliveryRegions.expressDeliveryRegionsToDis = function(regionss) {
	for ( var i = 0, leng = regionss.length; i < leng; i++) {
		regionss[i].virtualDistrictId = regionss[i].virtualDistrictId == "0" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no')
				: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes');
		regionss[i].degree = baseinfo.expressDeliveryRegions.expressDeliveryRegionsLevelToDis(regionss[i].degree);
	}
	return regionss;
};
/**
 * 将“行政级别”编码 转成 显示的字符：
 */
baseinfo.expressDeliveryRegions.expressDeliveryRegionsLevelToDis = function(p_code) {
	p_code = p_code == "NATION" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.country') 
			: p_code == "DISTRICT_PROVINCE" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.province')
					: p_code == "CITY" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.city')
							: p_code == "DISTRICT_COUNTY" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.county')
									: p_code == "TOWN_STREET_AGENCY" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.townStreetAgency')
											: p_code =="VILLAGE_ROAD" ? baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.villageRoad')
													: p_code;
	return p_code;
};
/**
 * 将“派送属性”编码转换成显示的字符：
 */
baseinfo.expressDeliveryRegions.deliveryNatureToDis =function(a_code){
	a_code = a_code =="DELIVERY_NATURE_QJPS"?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.qjps')
			: a_code =="DELIVERY_NATURE_BFPS"?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.bfps')
				 :	a_code =="DELIVERY_NATURE_FBFPS"?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.fbfps')
					    :	a_code =="DELIVERY_NATURE_ZTBPS"?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.ztbps')
									: a_code =="DELIVERY_NATURE_FQJPS"?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.fqjps')
											: a_code;
		return a_code;
};
/**
 * 设置行政级别‘省市区和镇街道’字段-AC-187862
 */
baseinfo.expressDeliveryRegions.setDegreeField=function(updateForm){
	var degree_field_container =updateForm.query('fieldcontainer')[0];
	//先清空容器
	if(!Ext.isEmpty(degree_field_container)){
		degree_field_container.removeAll();
	}
	//添加新的行政级别字段
	degree_field_container.add([
	            {boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.country'), name: 'degree', inputValue: 'NATION'},
				{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.province'), name: 'degree', inputValue: 'DISTRICT_PROVINCE' },
				{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.city'), name: 'degree', inputValue: 'CITY' },
				{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.county'), name: 'degree', inputValue: 'DISTRICT_COUNTY'},
				{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.townStreetAgency'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY' ,checked: true}] //镇街道办事处
		);
	//设置表单的一些不可编辑
	updateForm.getForm().findField('name').setReadOnly(true);
	updateForm.getForm().findField('virtualDistrictId').setReadOnly(true);
	updateForm.getForm().findField('parentDistrictCode').setDisabled(true);
	updateForm.getForm().findField('deliveryNature').setReadOnly(true);//187862-dujunhui
	degree_field_container.items.items[0].setReadOnly(true);
	degree_field_container.items.items[1].setReadOnly(true);
	degree_field_container.items.items[2].setReadOnly(true);
	degree_field_container.items.items[3].setReadOnly(true);
	degree_field_container.items.items[4].setReadOnly(true);
	updateForm.doComponentLayout();
};
/**
 * 设置行政级别‘村镇’字段
 */
baseinfo.expressDeliveryRegions.setDegreeField_CZ =function(updateForm){
	var degree_field_container =updateForm.query('fieldcontainer')[0];
	//先清空容器
	if(!Ext.isEmpty(degree_field_container)){
		degree_field_container.removeAll();
	}
	//添加新的行政级别字段
	degree_field_container.add([
//	            {boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.townStreetAgency'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY'}, //镇街道办事处
				{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.villageRoad'), name: 'degree', inputValue: 'VILLAGE_ROAD' ,checked: true} //村路
		]);
	//设置表单一些可编辑
	updateForm.getForm().findField('name').setReadOnly(false);
	updateForm.getForm().findField('virtualDistrictId').setReadOnly(false);
	updateForm.getForm().findField('parentDistrictCode').setDisabled(false);
	updateForm.getForm().findField('deliveryNature').setReadOnly(false);//187862-dujunhui
	degree_field_container.items.items[0].setReadOnly(false);
//	degree_field_container.items.items[1].setReadOnly(false);
	updateForm.doComponentLayout();
};
/**
 * 新增时，设置自动code编码
 */
baseinfo.expressDeliveryRegions.setAutoCode =function(parentDistrictCode,dictModifyForm){
	Ext.Ajax.request({
		params:{'vo.expressDeliveryRegionsEntity.parentDistrictCode':parentDistrictCode},
		url:baseinfo.realPath('queryMaxChildRegionsCode.action'),
		success:function(response){
			json =Ext.decode(response.responseText);
			var maxCode =json.vo.expressDeliveryRegionsEntity.code;
			var m_code =null;
			//若code不为空
			if(!Ext.isEmpty(maxCode)){
				//转换为数字
				m_code=new Number(maxCode);
				m_code =m_code+1;
			}else{
				//否则 说明是首次添加子节点  在后面加"001"
				if(parentDistrictCode.length ==8){
					parentDistrictCode =parentDistrictCode.substring(0,parentDistrictCode.length-2);
				}
				m_code =new Number(parentDistrictCode+baseinfo.expressDeliveryRegions.NUM_INIT);
			}
			dictModifyForm.getForm().findField('code').setValue(m_code.toString());
			dictModifyForm.getForm().findField('code').setReadOnly(true);
			//设置自动编码，用于重置
			dictModifyForm.autoCode =m_code.toString();
		}
	});
};
/**
 * 快递派送区域新增操作
*/
baseinfo.expressDeliveryRegions.addOperator = function(){
	var dictModifyForm = baseinfo.expressDeliveryRegions.addDictModifyWindow.getDictMidifyUpdateForm();
	dictModifyForm.getForm().reset();
	dictModifyForm.autoCode =null;
	dictModifyForm.parentDegree =null;
	dictModifyForm.parentDistrictObj =new Object();
	dictModifyForm.setTitle('新增快递派送范围区域');
	dictModifyForm.getForm().findField('code').setReadOnly(false); 
	//点击“新增按钮”后，将选择的结点的id设置进去：
	var index = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
	var expressDeliveryRegionsTree = index.getRegionsLeftPannel().getExpressDeliveryRegionsTree();
	var detailForm =index.getRegionsRightPannel().getExpressDeliveryRegionsDetailForm();
	var selectRecord=expressDeliveryRegionsTree.getSelectionModel().getSelection();
	//若用户选中某一个区域进行新增
	if(selectRecord&&selectRecord.length>0){
		var record = selectRecord[0].data;
		var degree =detailForm.regionsModel.get('degree');
		//判断是否用户只选中村镇后，进行新增
		if(degree !='TOWN_STREET_AGENCY'){//AC-187862-dujunhui选镇街道级别新增
			Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), 
					baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.degreeBeTheVillage'));//只能新增村路
			return ;
		}
		//设置自动编码
		baseinfo.expressDeliveryRegions.setAutoCode(record.id,dictModifyForm);
		//获取选择的节点的编码和名称，作为新增的上级编码
		var code=record.id; 
		var name = record.text;
		parentDistrictCodeObj = dictModifyForm.getForm().findField('parentDistrictCode');
		parentDistrictCodeObj.setCombValue(name,code);
		selectRecord[0].expand();
		//设置上级区域级别属性
		dictModifyForm.parentDegree =degree;
		//设置上级区域编码名称
		dictModifyForm.parentDistrictObj.parentDistrictCode =code;
		dictModifyForm.parentDistrictObj.parentDistrictName =name;
	} else{
		dictModifyForm.getForm().findField('code').setReadOnly(false);
	}
	baseinfo.expressDeliveryRegions.addDictModifyWindow.show();
};
/**
 * 快递派送区域的修改操作
 */
baseinfo.expressDeliveryRegions.updateOperator =function(){
	//详情界面
	var detailForm=Ext.getCmp("T_baseinfo-expressDeliveryRegions_content").getRegionsRightPannel().getExpressDeliveryRegionsDetailForm();
	//获取修改窗口表单
	var updateForm =baseinfo.expressDeliveryRegions.dictModifyWindow.getDictMidifyUpdateForm();
	updateForm.operatorType =baseinfo.expressDeliveryRegions.OP_TYPE_UPDATE;
	updateForm.initFormFlag=baseinfo.expressDeliveryRegions.OP_TYPE_INIT;
	//判断用户是否选择一个选择区域
	if(Ext.isEmpty(detailForm.getForm().findField('parentDistrictCode').getValue())){
		Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), 
				baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.choseDistrict'));
		return;
	}
	var params = {
			"vo.expressDeliveryRegionsEntity.code":detailForm.getRecord().data.code 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryExpressDeliveryRegionsByCode.action'),
		params:params,
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var a_entityDetail = json.vo.expressDeliveryRegionsEntity;
			var model = new Foss.baseinfo.expressDeliveryRegions.DistrictModel(a_entityDetail);
			var degree =model.get('degree');
			// 若要修改的行政级别为‘国家’,‘省’、‘市’、‘区县’、‘乡镇街道’-AC-187862
			if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
				//则设置当前行政级别的选项
				baseinfo.expressDeliveryRegions.setDegreeField(updateForm);
				if(degree=='DISTRICT_COUNTY'){//区县级别的派件区域只读-完善需求20151126-187862
					updateForm.getForm().findField('remark').setReadOnly(true);
					updateForm.getForm().findField('specialArea').setReadOnly(true);
				}else{
					updateForm.getForm().findField('remark').setReadOnly(false);
					updateForm.getForm().findField('specialArea').setReadOnly(false);
				}
			}else{
				baseinfo.expressDeliveryRegions.setDegreeField_CZ(updateForm);
			}
			var isBigCustomerOwnRegion = updateForm.getForm().findField('isBigCustomerOwnRegion');
			if(model.data.isBigCustomerOwnRegion==null||''==model.data.isBigCustomerOwnRegion){
				model.data.isBigCustomerOwnRegion = 'N';
			}
			if(degree!='DISTRICT_COUNTY'){
				isBigCustomerOwnRegion.setVisible(false);
			}else{
				isBigCustomerOwnRegion.setVisible(true);
			}
			//加载值
			updateForm.getForm().findField('code').setReadOnly(true);
			updateForm.getForm().loadRecord(model);
			updateForm.getForm().findField('parentDistrictCode').setCombValue(model.get('parentDistrictName'),model.get('parentDistrictCode'));//设置上级行政区域
			updateForm.getForm().findField('expressSalesDeptCode').setCombValue(model.get('expressSalesDeptName'),model.get('expressSalesDeptCode')); //设置所属快递营业部
			
			//解决从行政区域同步独立省市区县过来产生派送属性不可编辑问题--187862-dujunhui
			if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
				var isLeaf=model.data.isLeaf;
				if(isLeaf==="Y"){//叶子节点派送属性(派件区域)可编辑
					updateForm.getForm().findField('deliveryNature').setReadOnly(false);
					updateForm.getForm().findField('remark').setReadOnly(false);
					updateForm.getForm().findField('specialArea').setReadOnly(false);
				}else if(isLeaf==="N"){//非叶子节点属性只读
					updateForm.getForm().findField('deliveryNature').setReadOnly(true);
					updateForm.getForm().findField('remark').setReadOnly(true);
					updateForm.getForm().findField('specialArea').setReadOnly(true);
				}
				//解决国家、省份修改界面派送属性为空导致不可保存问题
				if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')){
					updateForm.getForm().findField('deliveryNature').allowBlank=true;
				}
				//国家、省、市的派件区域和特殊区域可编辑-20160105版本
                if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY') ||(degree =='DISTRICT_COUNTY')){
                	updateForm.getForm().findField('remark').setReadOnly(false);
					updateForm.getForm().findField('specialArea').setReadOnly(false);
	            }
			}
			
			//设置model 给DictModifyForm
			updateForm.dictModifyModel =model;
			updateForm.clickFromDetail =true;
			baseinfo.expressDeliveryRegions.dictModifyWindow.show();
		}
	});
};
/**
 * 快递派送区域的删除操作
 */
baseinfo.expressDeliveryRegions.deleteOperator =function(){
	//详情界面 、树
	var index =Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
	var detailForm=index.getRegionsRightPannel().getExpressDeliveryRegionsDetailForm();
	var tree =index.getRegionsLeftPannel().getExpressDeliveryRegionsTree();
	var treeStore =tree.getStore();
	//判断是否选中某个区域
	if(Ext.isEmpty(detailForm.getForm().findField('parentDistrictCode').getValue())){
		Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'),baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.choseDistrict'));
		return;
	}
	baseinfo.showQuestionMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.sureDelete'),function(e){
		if(e=='yes'){
			var degree =detailForm.getRecord().data.degree;
			//若行政级别为省市区县镇,表示不能删除
			if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
				Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), 
						baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.CanNotDeleteForCtiy'));
				return ;
			}
			var expressDeliveryRegionsObj =detailForm.getRecord().data;
			var model =new Foss.baseinfo.expressDeliveryRegions.DistrictModel(expressDeliveryRegionsObj);
			var params ={'vo':{'expressDeliveryRegionsEntity':model.data}};
			Ext.Ajax.request({
				url:baseinfo.realPath('deleteExpressDeliveryRegions.action'),
				jsonData:params,
				success:function(response){
					var json =Ext.decode(response.responseText);
					var currNode =treeStore.getNodeById(model.data.code);
					//清空树上的节点 和
					if(currNode){
						currNode.remove();
					}
					//清空详情
					detailForm.getForm().reset();
				},
				exception:function(response){
					json =Ext.decode(response.responseText);
					Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), json.message);
				}
			});
		}
	});
};
/**
 * 保存修改表单信息操作方法
 */
baseinfo.expressDeliveryRegions.save =function(updateForm){
	if(!updateForm.getForm().isValid()){
		return;
	}
	var msgTip=baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.updateSuccessful');
	// 当前表单中的数据：
	var expressDeliveryRegionsObj = updateForm.getForm().getValues(); 
	var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
	var expressDeliveryRegionsTree = expressDeliveryRegionsindex.getRegionsLeftPannel().getExpressDeliveryRegionsTree();
    var expressDeliveryRegionsTreeStore = expressDeliveryRegionsTree.getStore();
    //把值封装给model
	var model =new Foss.baseinfo.expressDeliveryRegions.DistrictModel(expressDeliveryRegionsObj);
	if(model.data.degree!="DISTRICT_COUNTY"){
    	model.data.isBigCustomerOwnRegion=null;
    }
	//判断obj中parentDistrictCode是否存在
    if(Ext.isEmpty(model.get('parentDistrictCode'))){
    	model.set('parentDistrictCode',updateForm.dictModifyModel.get('parentDistrictCode'));
    }
	// 被操作的结点的上级节点
	var parentNode= expressDeliveryRegionsTreeStore.getNodeById(model.data.parentDistrictCode);
	//若获取的上级节点为空，获取根节点
	if(!parentNode){
		parentNode=expressDeliveryRegionsTreeStore.getRootNode();
	}	  
	// 展开父结点
	parentNode.expand();
	var params={'vo':{'expressDeliveryRegionsEntity':model.data}};
	Ext.Ajax.request({
		jsonData:params,
		url:baseinfo.realPath('updateExpressDeliveryRegions.action'),
		success:function(response){
			var json =Ext.decode(response.responseText);
			//获取当前节点
			var currentNode =expressDeliveryRegionsTreeStore.getNodeById(model.get('code'));
			//存在当前节点，
			if(currentNode){
				currentNode.data.id =model.get('code');
				currentNode.data.parentId =model.get('parentDistrictCode');
				currentNode.data.text =model.get('name');
				currentNode.data.expanded?currentNode.collapse():currentNode.expand();
				//更新树结构
				currentNode.set('load',true);
			}
			//刷新上级节点
			baseinfo.expressDeliveryRegions.flushNodeById(parentNode.data.id);
			//设置form 情况
			updateForm.getForm().reset();
			Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), msgTip);
			baseinfo.expressDeliveryRegions.dictModifyWindow.hide();
			baseinfo.expressDeliveryRegions.pagingBar.moveFirst();
			//若是点击查询详细修改的话，要更新详细信息
			if(updateForm.clickFromDetail){
				var detailForm =expressDeliveryRegionsindex.getRegionsRightPannel().getExpressDeliveryRegionsDetailForm(); 
				model.set('parentDistrictName',updateForm.dictModifyModel.get('parentDistrictName'));//设置上级行政区域
				detailForm.getForm().loadRecord(model);
				detailForm.regionsModel = model;
			}
		},
		exception:function(response){
			var json =Ext.decode(response.responseText);
			Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), json.message);
		}
	});
};

/**
 * 刷新节点
 */
baseinfo.expressDeliveryRegions.flushNodeById =function(code){
	var index = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
	var expressDeliveryRegionsTree = index.getRegionsLeftPannel().getExpressDeliveryRegionsTree();
	var store =expressDeliveryRegionsTree.getStore();
	//获取该节点
	var node =store.getNodeById(code);
	if(!node){
		//得到根节点
		node =store.getRootNode();
	}
	store.load({'node':node});
};
/**
 * 树节点操作
 */
baseinfo.expressDeliveryRegions.treeNodeOperator=function(me,a_code){
	//var a_code=record.data.id;
	// 如果是根结点，点击时不响应
	if(a_code =='01'){
		return ;
	}
	var params = {
			"vo.expressDeliveryRegionsEntity.code":a_code 
		};
	Ext.Ajax.request({
		url : baseinfo.realPath('queryExpressDeliveryRegionsByCode.action'),
		params:params,
		async: true,   //ASYNC 是否异步( TRUE 异步 , FALSE 同步)
		success : function(response) {    								 
			json = Ext.decode(response.responseText);
			var a_entityDetail = json.vo.expressDeliveryRegionsEntity;
			var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
			var expressDeliveryRegionsRightPanel = expressDeliveryRegionsindex.getRegionsRightPannel();
			var expressDeliveryRegionsRightQueryResultPannel = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsQueryResultPanel();
			
			var detailForm = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsDetailForm(); 
			detailForm.setVisible(true);
			expressDeliveryRegionsRightQueryResultPannel.setVisible(false);
			var districtModel = new Foss.baseinfo.expressDeliveryRegions.DistrictModel(a_entityDetail);
			//把值设置给详情表单中的regionsModel
			detailForm.regionsModel = districtModel;
			detailForm.getForm().loadRecord(districtModel);
		}
	});
};

/**
 * 
***************************派送区域界面数据模型*******************************************
*/
Ext.define('Foss.baseinfo.expressDeliveryRegions.DistrictModel',{
	extend:'Ext.data.Model',
	fields:[
		//ID
		{name:'id',type:'string'},
		//行政区域编码
		{name:'code',type:'string'},
		//区域名称
		{name:'name',type:'string'},
		//城市区号
		{name:'areaCode',type:'string'},
		//上级行政区域编码
		{name:'parentDistrictCode',type:'string'},
		//上级行政区域名称
		{name:'parentDistrictName',type:'string'},
		//虚拟行政区域
		{name:'virtualDistrictId',type:'string'},
		//行政区域级别
		{name:'degree',type:'string'},
		//非标准派送时效
		{name:'nonStandardDeliveryTime',type:'string'},
		//派送属性
		{name:'deliveryNature',type:'string'},
		//是否大客户全境
		{name:'isBigCustomerOwnRegion',type:'string'},
		//所属虚拟快递营业部编码
		{name:'expressSalesDeptCode',type:'string'},
		//所属快递营业部名称
		{name:'expressSalesDeptName',type:'string'},
		//派件区域
		{name:'remark',type:'string'},	
		//收件区域
		{name:'remarkDe',type:'string'},
		//特殊区域
		{name:'specialArea',type:'string'},
		//城市级别
		{name:'cityLevel',type:'string'},
		//市辖区面积
		{name:'cityArea',type:'number'},
		//创建时间
		{name:'createTime',type:'date'},
		//最后修改时间
		{name:'modifyTime',type:'date'},
		//创建人
		{name:'createUserCode',type:'string'},
		//更新人
		{name:'modifyUserCode',type:'string'},
		//是否叶子节点
		{name:'isLeaf',type:'string'},
		//是否启用
		{name:'active',type:'string'}
	]
});
/**
 * *******************************左侧面板-定义派送行政区域TreeStore*********************
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsTreeStore',{
  	extend: 'Ext.data.TreeStore', 
  	root: {
		//根的文本信息
		text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.regions'),
		//设置根节点的ID
		id : '01',
		//根节点是否展开
		expanded: true,
	},
	//设置一个代理，通过读取内存数据
  	proxy: {
		type : 'ajax',
		actionMethods : 'POST',
		url : baseinfo.realPath("queryExpressDeliveryRegionsByParentDistrictCode.action"),
        reader: {
            type: 'json',
            root: 'nodes'
        }
  	},
  	nodeParam: 'vo.expressDeliveryRegionsEntity.parentDistrictCode'
});    
/**
 * 左侧面板-定义行政区域Tree
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsTree',{
    extend:'Ext.tree.Panel',
    title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.regions'),
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
    store: Ext.create('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsTreeStore'),
    defaults: {
		margin:'5 5 0 5'
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
				var a_code =record.data.id;
				baseinfo.expressDeliveryRegions.treeNodeOperator(me,a_code); 
			},
	      //右键单击
	      itemcontextmenu:function(node,record,item,index,e){
	        e.preventDefault();
	      }
	    };
		me.callParent([cfg]);
	}
});  
/**
 * 左侧面板--封装树面板
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.expressDeliveryRegionsLeftPannel',{
	extend:'Ext.panel.Panel',
	height: 680,
	frame: true,
	layout:'column',
	columnWidth: 0.25,
	defaultType : 'textfield',
	//定义组织树方法
	expressDeliveryRegionsTree: null,
	getExpressDeliveryRegionsTree: function(){
		if(this.expressDeliveryRegionsTree == null){
			this.expressDeliveryRegionsTree = Ext.create('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsTree');
		}
		return this.expressDeliveryRegionsTree;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getExpressDeliveryRegionsTree()];  
		me.callParent([cfg]);
	}
});
/**
 ******************************* 右边面板-新增按钮面板*****************************
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsAddButtonForm',{
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
						cls:'yellow_button',
						name: 'administrativeRegions_add',
						text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.add'),
						disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/addButton'),
						hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/addButton'),
						columnWidth:.14, 
						margin:'0,10,0,0',
						width:50,
						handler: function() {
							baseinfo.expressDeliveryRegions.addOperator();
						}
					})
				]
			})	
		];
	    me.callParent([cfg]);
	}
});
/**
 * 右边模块-查询条件面板
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsQueryForm',{
	extend:'Ext.form.Panel',  
	layout: {
		type : 'table',
		columns : 3
	},
	frame : true,
	collapsible : true,
	title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.queryCondition'),
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
			fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaName'),//行政区域名称
			columnWidth : 0.35,
			labelWidth : 70
		},{
			name:'deliveryNature',
			columnWidth : 0.35,
			labelWidth : 70,
			fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'), //派送属性
			xtype:'combobox',
			valueField:'valueCode',
			displayField:'valueName',
			queryMode:'local',
			triggerAction:'all',
			value:'',
			store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE',null,{
				'valueCode':'',
				'valueName':baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.all')
			})
		},{
			name:'virtualDistrictId',
			xtype:'combobox',
			fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.virtualDistrict'),//是否虚拟行政区域
			queryMode: 'local',
			displayField: 'valueName',
			value:'',
			valueField:'valueCode', 
			columnWidth : 0.3,
			triggerAction:'all',
			store:FossDataDictionary.getDataDictionaryStore('FOSS_BOOLEAN',null,{
				'valueCode':'',
				'valueName':baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.all')
			})
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				  text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.reset'),//重置   
				  disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/queryButton'),
				  hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/queryButton'),
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
				  text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.query'),//查询
				  disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/queryButton'),
				  hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/queryButton'),
				  columnWidth:.12,
				  cls:'yellow_button',
				  text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.query'),
				  //查询按钮的响应事件：
				  handler: function() { 
					  	var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
						var expressDeliveryRegionsRightPanel = expressDeliveryRegionsindex.getRegionsRightPannel();
						var expressDeliveryRegionsDetailFormPannel = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsDetailForm();
						var queryResultPannel = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsQueryResultPanel();
						expressDeliveryRegionsDetailFormPannel.setVisible(false);
						queryResultPannel.setVisible(true);
						baseinfo.expressDeliveryRegions.pagingBar.moveFirst();
					}
			  	}]
		}]
});
/**
 * 右边模块-查询结果查询的显示表格Grid 
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.QueryResultGrid',{
	extend: 'Ext.grid.Panel', 
	cls:'autoHeight',
	bodyCls:'autoHeight',
	columnWidth:1, 
	// 设置表格不可排序
	sortableColumns:false,
	// 去掉排序的倒三角
	enableColumnHide:false,
	// 设置“如果查询的结果为空，则提示”
	emptyText: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.queryResultIsNull'),
	//增加滚动条
	autoScroll : true,
	stripeRows : true, // 交替行效果
	collapsible: true,
	animCollapse: true,
	selType : "rowmodel", // 选择类型设置为：行选择
	store : null,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	//表格行可展开的插件
	plugins: [{
		ptype: 'rowexpander',
		//定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander: false,
		//行体内容
		rowBodyElement : 'Foss.baseinfo.expressDeliveryRegions.GridDetail'
	}],
	columns : [{
          xtype:'actioncolumn',
          flex: 1,
			text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.operate'),
			align: 'center',
          items: [{
	          	  iconCls:'deppon_icons_edit',
	              tooltip: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.update'),
	              disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/updateButton'),
	              handler: function(grid, rowIndex, colIndex) {
	            	//获取得到Model
	            	var rowInfo=grid.getStore().getAt(rowIndex);
	            	var districtModel =new Foss.baseinfo.expressDeliveryRegions.DistrictModel(rowInfo.data);
	            	//获取修改窗口表单
	            	var updateForm =baseinfo.expressDeliveryRegions.dictModifyWindow.getDictMidifyUpdateForm();
	            	updateForm.operatorType =baseinfo.expressDeliveryRegions.OP_TYPE_UPDATE;
	            	updateForm.initFormFlag=baseinfo.expressDeliveryRegions.OP_TYPE_INIT;
	            	var degree =districtModel.get('degree');
	            	// 若要修改的行政级别为‘国家’，‘省’、‘市’、‘区县’、‘镇街道’
	            	if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
	            		//则设置当前行政级别的选项
	            		baseinfo.expressDeliveryRegions.setDegreeField(updateForm);
	            		if(degree=='DISTRICT_COUNTY'){//区县级别的派件区域只读-完善需求20151126-187862
	    					updateForm.getForm().findField('remark').setReadOnly(true);
	    					updateForm.getForm().findField('specialArea').setReadOnly(true);
	    				}else{
	    					updateForm.getForm().findField('remark').setReadOnly(false);
	    					updateForm.getForm().findField('specialArea').setReadOnly(false);
	    				}
	            	}else{
	            		baseinfo.expressDeliveryRegions.setDegreeField_CZ(updateForm);
	            	}
	            	var isBigCustomerOwnRegion = updateForm.getForm().findField('isBigCustomerOwnRegion');
	    			if(districtModel.data.isBigCustomerOwnRegion==null||''==districtModel.data.isBigCustomerOwnRegion){
	    				districtModel.data.isBigCustomerOwnRegion = 'N';
	    			}
	    			if(degree!='DISTRICT_COUNTY'){
	    				isBigCustomerOwnRegion.setVisible(false);
	    			}else{
	    				isBigCustomerOwnRegion.setVisible(true);
	    			}
	    			
	                //解决从行政区域同步独立省市区县过来产生派送属性不可编辑问题--187862-dujunhui
	    			if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')
	    					||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
	    				var isLeaf=districtModel.data.isLeaf;
		                if(isLeaf==="Y"){//叶子节点派送属性可编辑
		                  updateForm.getForm().findField('deliveryNature').setReadOnly(false);
		                  updateForm.getForm().findField('remark').setReadOnly(false);
						  updateForm.getForm().findField('specialArea').setReadOnly(false);
		                }else if(isLeaf==="N"){//非叶子节点属性只读
		                  updateForm.getForm().findField('deliveryNature').setReadOnly(true);
		                  updateForm.getForm().findField('remark').setReadOnly(true);
						  updateForm.getForm().findField('specialArea').setReadOnly(true);
		                }
		                //解决国家、省份修改界面派送属性为空导致不可保存问题
		                if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')){
		                  updateForm.getForm().findField('deliveryNature').allowBlank=true;
		                }
		                //国家、省、市的派件区域和特殊区域可编辑-20160105版本
		                if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY') ||(degree =='DISTRICT_COUNTY')){
		                	updateForm.getForm().findField('remark').setReadOnly(false);
							updateForm.getForm().findField('specialArea').setReadOnly(false);
			            }
	    			}
	    			
	            	//加载值
	            	updateForm.getForm().findField('code').setReadOnly(true);
	            	updateForm.getForm().loadRecord(districtModel);
	            	updateForm.getForm().findField('parentDistrictCode').setCombValue(districtModel.get('parentDistrictName'),districtModel.get('parentDistrictCode'));//设置上级行政区域
	            	updateForm.getForm().findField('expressSalesDeptCode').setCombValue(districtModel.get('expressSalesDeptName'),districtModel.get('expressSalesDeptCode')); //设置所属快递营业部
	            	//把model的值传给用户
	            	updateForm.dictModifyModel =districtModel;
	            	//显示窗口
	            	baseinfo.expressDeliveryRegions.dictModifyWindow.show();
	              }
              	},{
	          	  iconCls:'deppon_icons_cancel',
	              tooltip: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.void'),
	              disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/deleteButton'),
	              handler: function(grid, rowIndex, colIndex) {
	            	var tree =Ext.getCmp("T_baseinfo-expressDeliveryRegions_content").getRegionsLeftPannel().getExpressDeliveryRegionsTree();
	            	var treeStore =tree.getStore();
	            	//获取信息
	            	var rowInfo =grid.getStore().getAt(rowIndex);
	            	var model =new Foss.baseinfo.expressDeliveryRegions.DistrictModel(rowInfo.data);
	            	var degree =model.get('degree');
      				//若行政级别为省市区县,表示不能删除，镇街道也不可删除AC-187862-dujunhui
      				if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
      					Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), 
      							baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.CanNotDeleteForCtiy'));
      					return ;
      				}
	            	baseinfo.showQuestionMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.sureDelete'),function(e){
	            			if(e=='yes'){
	            				var params ={'vo':{'expressDeliveryRegionsEntity':model.data}};
	            				Ext.Ajax.request({
	            					url:baseinfo.realPath('deleteExpressDeliveryRegions.action'),
	            					jsonData:params,
	            					success:function(response){
	            						var json =Ext.decode(response.responseText);
	            						var currNode =treeStore.getNodeById(model.data.code);
	            						//清空树上的节点 和
	            						if(currNode){
	            							currNode.remove();
	            						}
	            						//清空列表中的这条数据
	            						grid.getStore().removeAt(rowIndex);
	            					},
	            					exception:function(response){
	            						json =Ext.decode(response.responseText);
	            						Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), json.message);
	            					}
	            				});
	            			}
	            	});
	             }
              }]
		},{
			text : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.code'),
			align: 'center',
			flex: 1,
			dataIndex :'code'  //行政区域编码
		},{
			text : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaName'),
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex : 'name'  //区域名称
		},{
			text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaCode'),//城市区号
			align:	'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'areaCode' //城市区号
		},{
			text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.upRegionBr'),
			align: 'center',
			flex: 0.8,
			xtype: 'ellipsiscolumn',
			dataIndex: 'parentDistrictName' //上级区域名称
		},{
			text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.virtualRegionBr'),
			align: 'center',
			flex: 0.8,
			xtype: 'ellipsiscolumn',
			dataIndex: 'virtualDistrictId', //是否虚拟区域
			renderer: function(value) {
				value=value=='N'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'):value=='Y'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'):value;
				return value;
			}
		},{
			text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.regionBr'), //行政区域级别
			align: 'center',
			flex: 1,
			xtype: 'ellipsiscolumn',
			dataIndex: 'degree',
			renderer: function(value) {				
				value=baseinfo.expressDeliveryRegions.expressDeliveryRegionsLevelToDis(value);
				return value;
			}
		},{
			text:'城市级别',
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'cityLevel',
			//渲染派送属性
			renderer:function(value){
				value = value =='FIRST_TIER_CITY'?'一线城市':
							value =='SECOND_TIER_CITY'?'二线城市':
									value =='THIRD_TIER_CITY'?'三线城市':
											value =='FOURTH_TIER_CITY'?'四线城市':
													value =='FIFTH_TIER_CITY'?'五线城市':
															value;
				return value;
			}
		},{
			text:'市辖区面积(KM²)',
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'cityArea',
		},{
			text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'),//派送属性
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'deliveryNature',
			//渲染派送属性
			renderer:function(value){
				value =baseinfo.expressDeliveryRegions.deliveryNatureToDis(value);
				return value;
			}
		},{
			text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.nonStandardDeliveryTime'),//非标准派送时效
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'nonStandardDeliveryTime',
			//渲染
			renderer:function(value){
				value = value == 'ZERO'?'0' : 
						value == 'HALF'?'+0.5' : 
							value == 'ONE'?'+1' :
								value =='ONE_HALF'?'+1.5' :
									value =='TWO'?'+2' :
										value =='TWO_HALF'?'+2.5' :
											value =='THREE'?'+3'  :
												value=='FOUR'?'+4':
													value=='FIVE'?'+5':
														value=='SIX'?'+6':
															value=='SEVEN'?'+7':
																value=='EIGHT'?'+8':
												                      value;
				return value ;
								
			}
		},{
			text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.expressSalesDept'),//所属虚拟快递营业部
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'expressSalesDeptName',
		},{
			text:'派件区域',//备注
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'remark',
		},{
			text:'收件区域',//备注
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'remarkDe',
		},{
			text:'特殊区域',//特殊区域
			align:'center',
			flex:1,
			xtype:'ellipsiscolumn',
			dataIndex:'specialArea',
		}
	],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		var r_url = "queryExpressDeliveryRegionsList.action";
		r_url=	baseinfo.realPath(r_url) ;
		me.store = Ext.create('Ext.data.Store',{
			model: 'Foss.baseinfo.expressDeliveryRegions.DistrictModel',
			pageSize:10,
			autoLoad: false,
			proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				url : r_url,
				//定义一个读取器
				reader: {
					type: 'json',
					root: 'vo.expressDeliveryRegionsList',
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
					var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
					var expressDeliveryRegionsRightPanel = expressDeliveryRegionsindex.getRegionsRightPannel();
					var queryForm = expressDeliveryRegionsRightPanel.getExpressDeliveryRegionsQueryForm();
					if (queryForm != null) {
						var queryParams = queryForm.getValues();
						Ext.apply(operation, {
							params : {
								'vo.expressDeliveryRegionsEntity.name':queryParams.name,
								'vo.expressDeliveryRegionsEntity.deliveryNature':queryParams.deliveryNature,
								'vo.expressDeliveryRegionsEntity.virtualDistrictId':queryParams.virtualDistrictId,
							}
						});	
					}
				}
			}
		});
		me.tbar =[ { 
			xtype: 'button', 
			text : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.void'),
			disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/deleteButton'),
			hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/deleteButton'),
			handler:function(){
					//获取已经选中的
					var selection =me.getSelectionModel().getSelection();
					var arr =new Array();
					if(selection.length<1){
						baseinfo.showErrorMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.deleteNoticeMsg'));// 沒有选中一条要删除的公告
			    		return;
					}
					for ( var i = 0; i < selection.length; i++) {
	      				arr.push(selection[i].data.code);
					}
					baseinfo.showQuestionMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.sureDelete'),function(e){
	        			if(e=='yes'){
	        				for ( var i = 0; i < selection.length; i++) {
	        					var degree =selection[i].data.degree;
								//判断是否删除的级别是否有省市区级别
								if((degree=='NATION')||(degree =='DISTRICT_PROVINCE')||(degree =='CITY')||(degree =='DISTRICT_COUNTY')||(degree =='TOWN_STREET_AGENCY')){
									baseinfo.showErrorMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.CanNotDeleteForCtiy'));
			      					return ;
			      				}
							}
	        				var params ={'vo':{'codes':arr}};
	        				Ext.Ajax.request({
	        					url:baseinfo.realPath('deleteMoreExpressDeliveryRegions.action'),
	        					jsonData:params,
	        					success:function(response){
	        						var json =Ext.decode(response.responseText);
	        						var tree =Ext.getCmp("T_baseinfo-expressDeliveryRegions_content").getRegionsLeftPannel().getExpressDeliveryRegionsTree(),
	        		            	treeStore =tree.getStore();
	        						for(var i =0;i<arr.length;i++){
	        							var currNode =treeStore.getNodeById(arr[i]);
		        						//清空树上的节点 
		        						if(currNode){
		        							currNode.remove();
		        						}
	        						}
	        						//删除成功
	        						baseinfo.showErrorMes(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.voidSuccessNo'));
	        						//清空列表中的这条数据
	        						baseinfo.expressDeliveryRegions.pagingBar.moveFirst();
	        					},
	        					exception:function(response){
	        						json =Ext.decode(response.responseText);
	        						Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), json.message);
	        					}
	        				});
	        			}
	        	});
			}
		}];
		me.bbar = Ext.create('Deppon.StandardPaging',{
				store:me.store
			});
		baseinfo.expressDeliveryRegions.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});
/**
 * 右边模块-定义派送区域详情信息Form
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.DetailForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtInfo'),
	frame: true,
	defaultType : 'textfield',
	columnWidth: 1, 
	regionsModel:null, //定义一个model
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
	   		fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.code') 
	     },{
	   		name: 'name', 
	   		fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaName') //区域名称
	     },{
	    	 name:'areaCode',
	    	 fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaCode')//城市区号
	     },{
	   		name: 'parentDistrictName',
	   		fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.upDistrict')  //上级区域名称
	     },{
	   		name: 'parentDistrictCode',
	   	    fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtLevel'),//上级区域编码
	   	    hidden:true
		 },{
	   		name: 'virtualDistrictId',
	   		fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.virtualDistrict'), //是否虚拟区域
	   		value: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'),
	   	    listeners: {	
	   			change: function(me, newValue, oldValue, eOpts ){
	   				var a_value=newValue=='N'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'):newValue=='Y'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'):newValue;
	   				if(a_value!=newValue){
	   					me.setValue(a_value);
	   				}
	   			}
	   	    } 
	     },{
	   		name: 'degree',
	   	    fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtLevel'), //行政区域级别
	   	    listeners: {	
				change: function(me, newValue, oldValue, eOpts ){
					var a_input=newValue;
					var a_value=baseinfo.expressDeliveryRegions.expressDeliveryRegionsLevelToDis(a_input);
					me.setValue(a_value);
				}
		    }
	   	},{
			xtype :'combobox',
			name:'nonStandardDeliveryTime',
			displayField: 'valueName',
			valueField: 'valueCode',	
			fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.nonStandardDeliveryTime'),//非标准时效
			queryMode:'local',
			triggerAction:'all',//触发器被触发时查询全部
			editable:false,
			readOnly:true,
			store:FossDataDictionary.getDataDictionaryStore('NO_STANDARD_DELIVERY_TIME'),
		},{
			xtype:'combobox',
			name:'deliveryNature',
			displayField: 'valueName',
			valueField: 'valueCode',	
			fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'),//派送属性
			queryMode:'local',
			triggerAction:'all',//触发器被触发时查询全部
			editable:false,
			readOnly:true,
			store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE'),
		},{
			xtype :'combobox',
			name:'cityLevel',
			displayField: 'valueName',
			valueField: 'valueCode',	
			fieldLabel:'城市级别',//城市级别
			queryMode:'local',
			triggerAction:'all',//触发器被触发时查询全部
			editable:false,
			readOnly:true,
			store:FossDataDictionary.getDataDictionaryStore('BSE_CITY_LEVEL'),
		},{
			xtype:'container',
			layout:'column',
			items:[{
					xtype: 'numberfield',
					name:'cityArea',
					maxValue: 99999,
					minValue: 0,
					fieldLabel:'市辖区面积',
					columnWidth:0.9,
					readOnly:true
				},{
					xtype:'label',
					text:'KM²',
					columnWidth:0.1
					}]
		},{
	   		name:'expressSalesDeptCode',
	   		xtype:'dynamicorgcombselector',
	   		fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.expressSalesDept'),//所属快递虚拟营业部
	   		colspan:1,
	   		readOnly:true,
	   		type : 'ORG',
	   		expressSalesDepartment:'Y',
//	   		salesDepartment:'Y',
	   		queryParam:'commonOrgVo.code',
	   		listeners:{
	   			change:function(_this, newValue, oldValue, eOpts){
	   				if(Ext.isEmpty(newValue)){
	   					return ;
	   				}
	   				_this.store.load({
	   					params:{
							start:0,
							limit:1,
							'commonOrgVo.code':newValue,
						}
	   				});
	   			}
	   		}
	   	},{
	   		name:'isBigCustomerOwnRegion',
	   		colspan:1,
	   		fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.isBigCustomerOwnRegion'),//大客户派送属性
	   		listeners: {	
	   			change: function(me, newValue, oldValue, eOpts ){
	   				if(newValue==''){
	   					me.setValue('');
	   					return;
	   				}
	   				var a_value=newValue=='N'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'):newValue=='Y'?baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'):newValue;
	   				if(a_value!=newValue){
	   					me.setValue(a_value);
	   				}
	   			}
	   	    }
	   	},{
	   		name:'remark',
	   		fieldLabel:'派件区域',
	   		xtype:'textarea',
	   		width:500,
	   		colspan:2,
	   	},{
	   		name:'remarkDe',
	   		fieldLabel:'收件区域',
	   		xtype:'textarea',
	   		width:500,
	   		colspan:2,
	   	},{
	   		name:'specialArea',
	   		fieldLabel:'特殊区域',
	   		xtype:'textarea',
	   		width:500,
	   		colspan:2,
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
					columnWidth:.5
				},{
					 text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.delete'), //删除按钮
					  columnWidth:.25,
					  disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/deleteButton'),
					  hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/deleteButton'),
					  cls:'yellow_button',
					  handler: function() {
						  baseinfo.expressDeliveryRegions.deleteOperator();
						}
				},{ 
					  text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.update'), //修改按钮
					  columnWidth:.25,
					  name: 'administrativeRegions_update',
					  disabled:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/updateButton'),
					  hidden:!baseinfo.expressDeliveryRegions.isPermission('expressDeliveryRegions/updateButton'),
					  cls:'yellow_button',
					  handler: function() {
						  baseinfo.expressDeliveryRegions.updateOperator();
						}
			  	}]
		}],
	   initComponent: function(config){
	   		var me = this,
			cfg = Ext.apply({}, config);
	   		me.callParent([cfg]);
	   }
});
/**
 * ******************************弹出框： 修改FORM*****************************
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.DictModifyForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.addUpdateDistrict'),
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
		labelWidth: 100
	},
	operatorType:null,
	initFormFlag:null,
	dictModifyModel:null,
	//若是直接点击详细信息上面的修改的话，为true
	clickFromDetail:false,
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [{	
			  name: 'code',
			  fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.code'),
			  allowBlank:false,
			  columnWidth: 0.5,	
			},{
			  name: 'name',
			  fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaName'), //派送区域名称
			  allowBlank:false,
			  columnWidth:.5,
			  listeners:{
				  blur:function(_this,theEvent,eOpts ){
					  if(!Ext.isEmpty(_this.value)){
						  var value = _this.value.trim();
						  if(value.length>=15){
							  _this.value =value.substring(0,14);
						  }
					  }
					  _this.setValue(_this.value);
				  }
			  }
			},{
				//复选框组：
				xtype: 'fieldcontainer',
				defaultType: 'radiofield',
				columnWidth:.5,
				allowBlank:false,
				layout: 'column',
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtLevel'),	
				items: [//AC-只有村路级别-187862-dujunhui
//					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.townStreetAgency'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY',checked: true}, //镇街道办事处
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.villageRoad'), name: 'degree', inputValue: 'VILLAGE_ROAD',checked: true } //村路
				],
				listeners : {
					change : function(_this, newValue, oldValue, eOpts ){
						var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
						var a_form = baseinfo.expressDeliveryRegions.dictModifyWindow.getDictMidifyUpdateForm();
						var a_parentDistrict = a_form.getForm().findField("parentDistrictCode");
						var a_upDegree = newValue.degree;
						var a_degree = newValue.degree;
						
						a_parentDistrict.setReadOnly(false);
						if(a_degree=='TOWN_STREET_AGENCY'){
							a_upDegree = 'DISTRICT_COUNTY';
						}else if(a_degree=='VILLAGE_ROAD'){
							a_upDegree = 'TOWN_STREET_AGENCY';
						}
						// 重写公共选择框的方法
						if(me.initFormFlag != baseinfo.expressDeliveryRegions.OP_TYPE_INIT){
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
								searchParams['expressDeliveryRegionsVo.expressDeliveryRegionsEntity.degree'] = a_upDegree;
							} 
	        			});
						a_parentDistrict.store.loadPage(1,{
					      callback: function() {
					    	  if(me.initFormFlag == baseinfo.expressDeliveryRegions.OP_TYPE_INIT){
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
			},{
				  xtype: 'radiogroup', 
				  fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.virtualDistrict'), //是否虚拟区域
				  allowBlank:false,
				  columnWidth:.5,
				  items: [
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'), name: 'virtualDistrictId', inputValue: 'Y'},
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'), name: 'virtualDistrictId', inputValue: 'N', checked: true}
				  ]
			},{
				xtype : 'commonrexpressdeliveryregionelector',
				name:'parentDistrictCode',
				allowBlank : false, 
				disName:null,
				disValue:null,
				fieldLabel : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.upDistrict'), //上级行政区域
				degree:null,//省市县类别
				margin:'5 20 5 10',
			    columnWidth:.5
			},{
				name:'areaCode',
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaCode'),//城市区号
				allowBlank: true,
				regex :/^\d{3,10}$/,
				columnWidth:.5
			},{
				xtype :'combobox',
				name:'nonStandardDeliveryTime',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.nonStandardDeliveryTime'),//非标准时效
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				allowBlank : false, 
				store:FossDataDictionary.getDataDictionaryStore('NO_STANDARD_DELIVERY_TIME'),
				columnWidth:.5
			},{
				xtype:'combobox',
				name:'deliveryNature',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'),//派送属性
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				allowBlank : false,
				store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE'),
				columnWidth:.48
			},{
				xtype :'combobox',
				name:'cityLevel',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:'城市级别',//城市级别
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('BSE_CITY_LEVEL',null,{}),
				columnWidth:.5
			},{
				xtype:'container',
				columnWidth:0.48,
				layout:'column',
				items:[{
						fieldLabel:'市辖区面积',
						xtype: 'numberfield',
						name:'cityArea',
						columnWidth:.85
					},{
						xtype:'label',
						text:'KM²',
						columnWidth:.15
						}]
			},{
				xtype:'dynamicorgcombselector',
				name:'expressSalesDeptCode',
				allowBlank:true,
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.expressSalesDept'),//所属虚拟快递营业部
				expressSalesDepartment:'Y',
//				salesDepartment:'Y',
				active:'Y',
				type:'ORG',
				columnWidth:.5
			},
			{
				xtype : 'radiogroup',
				name: 'isBigCustomerOwnRegion',
				allowBlank : false, 
				fieldLabel : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.isBigCustomerOwnRegion'),//是否大客户全境
			    columnWidth:.4,
			    height: 24,
			    items: [
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'), name: 'isBigCustomerOwnRegion', inputValue: 'Y'},
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'), name: 'isBigCustomerOwnRegion', inputValue: 'N', checked: true}
				]
			},{
				xtype:'textfield',
				name:'remark',
				xtype:'textarea',
				fieldLabel:'派件区域',//备注
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>250){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过250字，已自动帮你截取');
								_this.value =value.substring(0,250);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				xtype:'textfield',
				xtype:'textarea',
				name:'remarkDe',
				fieldLabel:'收件区域',//备注
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>500){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过500字，已自动帮你截取');
								_this.value =value.substring(0,500);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				xtype:'textfield',
				xtype:'textarea',
				name:'specialArea',
				fieldLabel:'特殊区域',//特殊区域
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>500){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过500字，已自动帮你截取');
								_this.value =value.substring(0,500);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				border: 1,
				xtype:'container', 
				defaultType:'button',
				height : 60,
				columnWidth : 1, 
				layout:'column',
				items:[{
				      text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.cancel'),
					  columnWidth : .15,
					  handler: function(){ 
						  baseinfo.expressDeliveryRegions.dictModifyWindow.hide();
					   }
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.reset'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							me.getForm().reset();
							var model =me.dictModifyModel;
							me.getForm().loadRecord(model);
							me.getForm().findField('parentDistrictCode').setCombValue(model.get('parentDistrictName'),model.get('parentDistrictCode'));//设置上级行政区域
							me.getForm().findField('expressSalesDeptCode').setCombValue(model.get('expressSalesDeptName'),model.get('expressSalesDeptCode')); //设置所属快递营业部
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.save'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							 if(baseinfo.expressDeliveryRegions.OP_TYPE_UPDATE==me.operatorType){
								baseinfo.expressDeliveryRegions.save(me);
							}
						}
				  	}]
				}  
		];
		me.callParent([cfg]);
	}
});
//弹出框 添加FORM
Ext.define('Foss.baseinfo.expressDeliveryRegions.AddDictModifyForm',{
	extend: 'Ext.form.Panel',
	title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.addUpdateDistrict'),
	frame: true,
	hidden : false,
	defaultType : 'textfield',
	layout:'column',
	columnWidth: 0.99,
	//上级编码
	parentDegree:null,
	autoCode:null,
	//上级区域对象
	parentDistrictObj:null,
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%',
		columnWidth: 1,
		labelWidth: 100
	},
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
			{  //行政区域编码
				name: 'code',
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.code'),
				allowBlank:false,
				regex :/^\d{9,12}$/,
				columnWidth: 0.5,
				listeners :{
					'blur':function(_this,theEvent,eOpts){
						if(_this!=null&&_this.value!=''){
							//判断是否有重复的：
							var params = {'vo.expressDeliveryRegionsEntity.code':_this.value};
						    var r_url=	baseinfo.realPath('queryExpressDeliveryRegionsByCode.action') ;
							Ext.Ajax.request({
								url: r_url,
								params: params,
								success : function(response) {
					                var json = Ext.decode(response.responseText);
					                var entity=json.vo.expressDeliveryRegionsEntity;
					                if(entity&&(!Ext.isEmpty(entity.code))){
					                	Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), 
					    						baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.codeAlreadyExists'));
										this.focus();
					                }
					            },
				                exception : function(response) {	
				                }
							});
						}
					}}
			},{
				name: 'name',  
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaName'),//行政区域名称
				allowBlank:false,
				columnWidth: 0.5,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						  if(!Ext.isEmpty(_this.value)){
							  var value = _this.value.trim();
							  if(value.length>=15){
								  _this.value =value.substring(0,14);
							  }
						  }
						  _this.setValue(_this.value);
					  }
				}
			},{
				//复选框组：
				xtype: 'radiogroup',
				vertical:true,
				allowBlank:false,
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtLevel'),//行政区域级别：街道、村路
				columnWidth:.5,	
				items: [//只有村路级别-AC-187862
//					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.townStreetAgency'), name: 'degree', inputValue: 'TOWN_STREET_AGENCY',checked: true}, //镇街道办事处
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.villageRoad'), name: 'degree', inputValue: 'VILLAGE_ROAD',checked: true } //村路
				],
				listeners : {
					change : function(_this, newValue, oldValue, eOpts ){

						var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
						var a_form = baseinfo.expressDeliveryRegions.dictModifyWindow.getDictMidifyUpdateForm();
						var a_parentDistrict = a_form.getForm().findField("parentDistrictCode");
						var a_upDegree = newValue.degree;
						var a_degree = newValue.degree;
						a_parentDistrict.setReadOnly(false);
						if(a_degree=='TOWN_STREET_AGENCY'){
							a_upDegree = 'DISTRICT_COUNTY';
						}else if(a_degree=='VILLAGE_ROAD'){
							a_upDegree = 'TOWN_STREET_AGENCY';
						}
						// 重写公共选择框的方法
						if(me.initFormFlag != baseinfo.expressDeliveryRegions.OP_TYPE_INIT){
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
								searchParams['expressDeliveryRegionsVo.expressDeliveryRegionsEntity.degree'] = a_upDegree;
							} 
	        			});
					}
				}
			},{
				  xtype: 'radiogroup', 
				  fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.virtualDistrict'), //是否虚拟行政区域
				  allowBlank:false,
				  columnWidth:.5,
				  items: [
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.yes'), name: 'virtualDistrictId', inputValue: 'Y'},
					{boxLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.no'), name: 'virtualDistrictId', inputValue: 'N', checked: true}
				  ]
			},{
				xtype : 'commonrexpressdeliveryregionelector',
				name:'parentDistrictCode',
				allowBlank : false, 
				disName:null,
				disValue:null,
				fieldLabel : baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.upDistrict'), //上级行政区域
				degree:null,//省市县类别
				margin:'5 20 5 10',
			    columnWidth:.5
			},{
				name:'areaCode',
				fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.areaCode'),//城市区号
				allowBlank:true,
				regex :/^\d{3,10}$/,
				columnWidth:0.5
			},{
				xtype :'combobox',
				name:'nonStandardDeliveryTime',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.nonStandardDeliveryTime'),//非标准时效
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				allowBlank : false, 
				store:FossDataDictionary.getDataDictionaryStore('NO_STANDARD_DELIVERY_TIME'),
				columnWidth:.5
			},{
				xtype:'combobox',
				name:'deliveryNature',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.deliveryNature'),//派送属性
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				allowBlank : false,
				store:FossDataDictionary.getDataDictionaryStore('DELIVERY_NATURE'),
				columnWidth:.5
			},{
				xtype :'combobox',
				name:'cityLevel',
				displayField: 'valueName',
				valueField: 'valueCode',	
				fieldLabel:'城市级别',//城市级别
				queryMode:'local',
				triggerAction:'all',//触发器被触发时查询全部
				editable:false,
				store:FossDataDictionary.getDataDictionaryStore('BSE_CITY_LEVEL',null,{}),
				columnWidth:.5
			},{
				xtype:'container',
				columnWidth:.5,
				layout:'column',
				items:[{
						fieldLabel:'市辖区面积',
						xtype: 'numberfield',
						name:'cityArea',
						maxValue: 99999,
				        minValue: 0,
						columnWidth:.9
					},{
						xtype:'label',
						text:'KM²',
						columnWidth:.1
						}]
			},{
				xtype:'dynamicorgcombselector',
				name:'expressSalesDeptCode',
				allowBlank:true,
				fieldLabel: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.expressDeliveryRegions.expressSalesDept'),//所属虚拟快递营业部
				expressSalesDepartment:'Y',
//				salesDepartment:'Y',
				active:'Y',
				type:'ORG',
				columnWidth:.5
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.5
			},{
				xtype:'textfield',
				name:'remark',
				xtype:'textarea',
				fieldLabel:'派件区域',//派件区域
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>250){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过250字，已自动帮你截取');
								_this.value =value.substring(0,250);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				xtype:'textfield',
				name:'remarkDe',
				xtype:'textarea',
				fieldLabel:'收件区域',//收件区域
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>500){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过500字，已自动帮你截取');
								_this.value =value.substring(0,500);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				xtype:'textfield',
				name:'specialArea',
				xtype:'textarea',
				fieldLabel:'特殊区域',//特殊区域
				colunmWidth:1,
				listeners:{
					blur:function(_this,theEvent,eOpts){
						if(!Ext.isEmpty(_this.value)){
							var value =_this.value.trim();
							if(value.length>500){
								Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '字数已超过500字，已自动帮你截取');
								_this.value =value.substring(0,500);
							}
						}
						_this.setValue(_this.value);
					}
				}
			},{
				border: 1,
				xtype:'container', 
				defaultType:'button',
				height : 60,
				columnWidth : 1, 
				layout:'column',
				items:[{
				      text: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.cancel'),
					  columnWidth : .15,
					  handler: function(){ 
						  baseinfo.expressDeliveryRegions.addDictModifyWindow.hide();
					   }
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.reset'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							me.getForm().reset();
							me.getForm().findField('code').setValue(me.autoCode);
							if(!Ext.isEmpty(me.parentDistrictObj)){
								me.getForm().findField('parentDistrictCode').setCombValue(me.parentDistrictObj.parentDistrictName,me.parentDistrictObj.parentDistrictCode);
							}
						}
				  	},{
						xtype:'container',
						border:false,
						html:'&nbsp;',
						columnWidth:.25
					},{
						text:baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.save'),
						cls:'yellow_button', 
						columnWidth : .15,
						handler: function(){
							if(!me.getForm().isValid()){
								return;
							}
							var msgTip=baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.addSuccessful');
							// 当前表单中的数据：
							var expressDeliveryRegionsObj = me.getForm().getValues(); 
							var expressDeliveryRegionsindex = Ext.getCmp("T_baseinfo-expressDeliveryRegions_content");
							var expressDeliveryRegionsTree = expressDeliveryRegionsindex.getRegionsLeftPannel().getExpressDeliveryRegionsTree();
						    var expressDeliveryRegionsTreeStore = expressDeliveryRegionsTree.getStore();
							// 被操作的结点的上级节点
							var parentNode= expressDeliveryRegionsTreeStore.getNodeById(expressDeliveryRegionsObj.parentDistrictCode);
							if(!parentNode){
								//上级节点为空的情况为两种，一种是本身是根节点，就返回根节点
								if(Ext.isEmpty(expressDeliveryRegionsObj.parentDistrictCode)){
									parentNode=expressDeliveryRegionsTreeStore.getRootNode();
								}
							}
							//判断上级级别和当前级别是否一致，
							if(!Ext.isEmpty(me.parentDegree)){
								//一致的话，不允许新增
								if(expressDeliveryRegionsObj.degree == me.parentDegree){
									Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), '不能在该级别下添加级别相同的下属界别！');
									return;
								}
							}
							//发送保存新结点的Ajax请求.
							var a_model = Ext.create('Foss.baseinfo.expressDeliveryRegions.DistrictModel',expressDeliveryRegionsObj);
							var params = {'vo':{'expressDeliveryRegionsEntity':a_model.data}};
						    Ext.Ajax.request({
						        url : baseinfo.realPath('addExpressDeliveryRegions.action'),
						        jsonData:params,
						        success : function(response) {
						          	var json = Ext.decode(response.responseText);
									//若父级节点不为空	
									if(parentNode!=null){
										// 展开父结点
										parentNode.expand();
										// 若原来节点为叶子节点，修改为非叶子节点
										if(parentNode&&parentNode.data.leaf){
										    parentNode.data.leaf = false;
										}
										var addedNode = {
										    'id':expressDeliveryRegionsObj.code,
										    'parentId':expressDeliveryRegionsObj.parentDistrictCode,
										    "text": expressDeliveryRegionsObj.name,
										    'expanded': true,
										    "leaf": true
										};
												
										var length = parentNode.childNodes.length;
										parentNode.insertChild(length, addedNode);
										parentNode.lastChild.raw = addedNode; 		
										parentNode.set('loaded', true);	
									}
									me.getForm().reset();
									//刷新树结点：
									baseinfo.expressDeliveryRegions.flushNodeById(expressDeliveryRegionsObj.code);
									Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), msgTip);
									//点击保存后，清空表单数据，并隐藏表单：
									baseinfo.expressDeliveryRegions.addDictModifyWindow.hide();
									baseinfo.expressDeliveryRegions.pagingBar.moveFirst();
						        },
						        exception : function(response) {
						          var json = Ext.decode(response.responseText);
						          Ext.Msg.alert(baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.tips'), json.message);
						        }
						    });
						}
				  	}]
				}  
		];
		me.callParent([cfg]);
	}
});
/**
 * 修改窗口
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.DistrictModifyWindow',{
	extend: 'Ext.window.Window', 
    width: 800,
	closeAction:'hide',
	layout : 'column', 
	dictMidifyForm:null,
	getDictMidifyUpdateForm:function(){
		if(Ext.isEmpty(this.dictMidifyForm)){
			this.dictMidifyForm=Ext.create('Foss.baseinfo.expressDeliveryRegions.DictModifyForm');
		}
		return this.dictMidifyForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getDictMidifyUpdateForm()];
		me.listeners={
			beforehide:function(me,eOpts){
				//清空数据
				me.getDictMidifyUpdateForm().getForm().reset();
			}
		};
		me.callParent([cfg]);
	}
});
/**
 * 新增窗口
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.AddDistrictModifyWindow',{
	extend: 'Ext.window.Window', 
    width: 800,
	closeAction:'hide',
	layout : 'column', 
	dictMidifyForm:null,
	getDictMidifyUpdateForm:function(){
		if(Ext.isEmpty(this.dictMidifyForm)){
			this.dictMidifyForm=Ext.create('Foss.baseinfo.expressDeliveryRegions.AddDictModifyForm');
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
baseinfo.expressDeliveryRegions.dictModifyWindow = Ext.create('Foss.baseinfo.expressDeliveryRegions.DistrictModifyWindow');
baseinfo.expressDeliveryRegions.addDictModifyWindow = Ext.create('Foss.baseinfo.expressDeliveryRegions.AddDistrictModifyWindow');
//右边模块-中间Grid区域Panel
Ext.define('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsQueryResultPanel',{
	extend:'Ext.panel.Panel', 
	layout:'column',
	frame: true,
	hidden:true,
	columnWidth: 1,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	title: baseinfo.expressDeliveryRegions.i18n('foss.baseinfo.districtList'),
	defaults: {
		readOnly : false,
		margin:'5 5 5 10',
		anchor: '90%'
	},
	QueryResultGrid:null,
	getQueryResultGrid:function(){
		if(Ext.isEmpty(this.QueryResultGrid)){ 
			this.QueryResultGrid = Ext.create('Foss.baseinfo.expressDeliveryRegions.QueryResultGrid');
		}
		return this.QueryResultGrid;
	},
	initComponent: function(config){
	    var me = this,
				cfg = Ext.apply({}, config);
	    me.items = [
			Ext.create('Ext.container.Container',{
				columnWidth:1,
				layout : 'column',
				items:[
						me.getQueryResultGrid()
					]
			})	
		];
	    me.callParent([cfg]);
	}
});
/**
 * 右边模块的实现的主面板：
 */
Ext.define('Foss.baseinfo.expressDeliveryRegions.expressDeliveryRegionsRightPannel',{
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
	//新增按钮
	expressDeliveryRegionsAddButton:null,
	getExpressDeliveryRegionsAddButton : function(){
		if(Ext.isEmpty(this.expressDeliveryRegionsAddButton)){
			this.expressDeliveryRegionsAddButton= Ext.create('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsAddButtonForm');
		}
		return this.expressDeliveryRegionsAddButton;
	},
	//查询条件面板
	expressDeliveryRegionsQueryForm:null,
	getExpressDeliveryRegionsQueryForm : function(){
		if(Ext.isEmpty(this.expressDeliveryRegionsQueryForm)){
			this.expressDeliveryRegionsQueryForm= Ext.create('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsQueryForm');
		}
		return this.expressDeliveryRegionsQueryForm;
	}, 
	//查询结果面板
	expressDeliveryRegionsQueryResultPanel:null,
	getExpressDeliveryRegionsQueryResultPanel : function(){
		if(Ext.isEmpty(this.expressDeliveryRegionsQueryResultPanel)){
			this.expressDeliveryRegionsQueryResultPanel= Ext.create('Foss.baseinfo.expressDeliveryRegions.ExpressDeliveryRegionsQueryResultPanel');
		}
		return this.expressDeliveryRegionsQueryResultPanel;
	}, 
	//左侧操作查询结果面板
	expressDeliveryRegionsDetailForm:null,
	getExpressDeliveryRegionsDetailForm : function(){
		if(Ext.isEmpty(this.expressDeliveryRegionsDetailForm)){
			this.expressDeliveryRegionsDetailForm = Ext.create('Foss.baseinfo.expressDeliveryRegions.DetailForm');
		}
		return this.expressDeliveryRegionsDetailForm;
	}, 
	initComponent: function(config){
		var me = this,
				cfg = Ext.apply({}, config);
		me.items = [
//		            me.getExpressDeliveryRegionsAddButton(),//AC优化需求取消新增按钮-187862-dujunhui
		            me.getExpressDeliveryRegionsQueryForm(),
		            me.getExpressDeliveryRegionsQueryResultPanel(),
		            me.getExpressDeliveryRegionsDetailForm()
		];
		me.callParent([cfg]);
	}
});
/**
 * 程序入口方法
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-expressDeliveryRegions_content')) {
		return;
	}
	var expressDeliveryRegionsLeftPannel = Ext.create('Foss.baseinfo.expressDeliveryRegions.expressDeliveryRegionsLeftPannel');
	var expressDeliveryRegionsRightPannel = Ext.create('Foss.baseinfo.expressDeliveryRegions.expressDeliveryRegionsRightPannel');
	Ext.getCmp('T_baseinfo-expressDeliveryRegions').add(Ext.create('Ext.panel.Panel',{
		id: 'T_baseinfo-expressDeliveryRegions_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'column',
		getRegionsLeftPannel : function() {
			return expressDeliveryRegionsLeftPannel;
		},
		getRegionsRightPannel : function() {
			return expressDeliveryRegionsRightPannel;
		},
		items: [expressDeliveryRegionsLeftPannel,expressDeliveryRegionsRightPannel]
	}));
});