//转换long类型为日期
baseinfo.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
baseinfo.orgAdministrativeInfo.saleDepartmentProduct = [];//三级产品
baseinfo.orgAdministrativeInfo.ORG_DEPARTURE = 'ORG_DEPARTURE';//营业部是出发
baseinfo.orgAdministrativeInfo.ORG_ARRIVE = 'ORG_ARRIVE';//营业部是到达
baseinfo.orgAdministrativeInfo.ORG_ROOT_ID = '103';//行政组织根节点ID
baseinfo.orgAdministrativeInfo.arrangeBizType = 'PKP_ARRANGRDOODS_SPEED';//理货类型
baseinfo.orgAdministrativeInfo.motorcadeType = 'BSE_FLEET_TYPE';//车队类型
baseinfo.orgAdministrativeInfo.orgLevel= 'ORG_LEVEL';//部门级别
//BUG-34950
baseinfo.orgAdministrativeInfo.standardTime =new Date('2013-07-01 00:00:00');//7月1号时间
//参数配置常量
baseinfo.orgAdministrativeInfo.amount1=0;//2013年七月1号后的临欠金额
baseinfo.orgAdministrativeInfo.amount2 =0;//七月1号前的金额
//BUG-34950
//查询配置参数的配置值
baseinfo.searchConfigParams = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('queryConfingParam.action'),
		async:false,
		jsonData:null,
		success:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.orgAdministrativeInfo.amount1 = result.orgAdministrativeInfoVo.minOwedLimit;//最小默认临欠金额
			baseinfo.orgAdministrativeInfo.amount2 = result.orgAdministrativeInfoVo.defaultOwedLimit;//7月1号前默认临欠金额
			
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.requestOvertime'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询所有三级产品
baseinfo.searchThreeLevelProduct = function(){
	Ext.Ajax.request({
		url:baseinfo.realPath('searchThreeLevelProduct.action'),
		async:false,
		jsonData:null,
		success:function(response){
			var result = Ext.decode(response.responseText);
			baseinfo.orgAdministrativeInfo.saleDepartmentProduct = result.productEntityList;//查询三级产品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.requestOvertime'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
/**
 * 設置电子地图按钮编辑/显示的方法
 */
baseinfo.setMapButtonCanEditFun = function(orgMainInfoForm,buttonId,isHide){
	//若是零担的 说明是派送电子地图，否则是快递派送区域电子地图
	if(buttonId ==='LD'){
		if(isHide=='hide'){
			if(Ext.getCmp('editDelivery_id').disabled ==false){
				Ext.getCmp('editDelivery_id').setDisabled(true);
			}
			Ext.getCmp('editDelivery_id').setVisible(false);
			Ext.getCmp('showDelivery_id').setVisible(false);
		}else if(isHide ='show'){
			Ext.getCmp('editDelivery_id').setVisible(true);
			Ext.getCmp('showDelivery_id').setVisible(true);
			if(Ext.getCmp('editDelivery_id').disabled ==true){
				Ext.getCmp('editDelivery_id').setDisabled(false);
			}
		}
	}else if(buttonId ==='KD'){
		if(isHide=='hide'){
			/*if(Ext.getCmp('editExpressDelivery_id').disabled ==false){
				Ext.getCmp('editExpressDelivery_id').setDisabled(true);
			}*/
			Ext.getCmp('showExpressDelivery_id').setVisible(false);
//			Ext.getCmp('editExpressDelivery_id').setVisible(false);
		}else if(isHide ='show'){
//			Ext.getCmp('editExpressDelivery_id').setVisible(true);
			Ext.getCmp('showExpressDelivery_id').setVisible(true);
			/*if(Ext.getCmp('editExpressDelivery_id').disabled ==true){
				Ext.getCmp('editExpressDelivery_id').setDisabled(false);
			}*/
		}
	}
};
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
baseinfo.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};
//组织信息
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgAdministrativeInfoEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//组织编码
        type : 'string'
    },{
        name : 'name',//组织名称
        type : 'string'
    },{
        name : 'orgLevel',//组织级别
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
        name : 'unifiedCode',//组织标杆编码
        type : 'string'
    },{
        name : 'leader',//组织负责人
        type : 'string'
    },{
        name : 'principalNo',//组织负责人工号
        type : 'string'
    },{
        name : 'parentOrgName',//上级组织名称
        type : 'string'
    },{
        name : 'parentOrgUnifiedCode',//上级组织标杆编码
        type : 'string'
    },{
        name : 'subsidiaryCode',//所属子公司编码
        type : 'string'
    },{
        name : 'subsidiaryName',//所属子公司名称
        type : 'string'
    },{
        name : 'costCenterCode',//所属成本中心编码
        type : 'string'
    },{
        name : 'costCenterName',//所属成本中心名称
        type : 'string'
    },{
        name : 'status',//组织状态
        type : 'string'
    },{
        name : 'endTime',//作废日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'beginTime',//启用日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'division',//是否事业部
        type : 'string'
    },{
        name : 'divisionCode',//事业部编码
        type : 'string'
    },{
        name : 'bigRegion',//是否大区
        type : 'string'
    },{
        name : 'smallRegion',//是否小区
        type : 'string'
    },{
        name : 'address',//部门地址
        type : 'string'
    },{
        name : 'deptArea',//部门面积
        type : 'string'
    },{
        name : 'countryRegion',//国家CODE
        type : 'string'
    },{
        name : 'countryRegionName',//国家名称
        type : 'string'
    },{
        name : 'provCode',//省份
        type : 'string'
    },{
        name : 'cityCode',//城市
        type : 'string'
    },{
        name : 'countyCode',//区县
        type : 'string'
    },{
        name : 'cityName',//城市名称
        type : 'string'
    },{
        name : 'countyName',//区县名称
        type : 'string'
    },{
        name : 'provName',//省份名称
        type : 'string'
    },{
        name : 'natureName',//城市名称
        type : 'string'
    },{
        name : 'salesDepartment',//是否营业部派送部
        type : 'string'
    },{
        name : 'transferCenter',//是否外场
        type : 'string'
    },{
        name : 'doAirDispatch',//是否可空运配载
        type : 'string'
    },{
        name : 'transDepartment',//是否车队
        type : 'string'
    },{
        name : 'dispatchTeam',//是否车队调度组
        type : 'string'
    },{
        name : 'billingGroup',//是否集中开单组
        type : 'string'
    },{
        name : 'transTeam',//是否车队组
        type : 'string'
    },{
        name : 'isDeliverSchedule',//是否派送排单
        type : 'string'
    },{
        name : 'isArrangeGoods',//是否理货
        type : 'string'
    },{
        name : 'isSecurity',//是否保安组
        type : 'string'
    },{
        name : 'tallySectorServiceOutfield',//保安组服务外场
        type : 'string'
    },{
        name : 'tallySectorServicemotorCade',//保安组服务车队
        type : 'string'
    },{
        name : 'deliverOutfield',//派送排单服务外场
        type : 'string'
    },{
        name : 'deliverOutfieldName',//派送排单服务外场名称
        type : 'string'
    },{
        name : 'arrangeOutfield',//理货部门服务外场
        type : 'string'
    },{
        name : 'arrangeOutfieldName',//理货部门服务外场名称
        type : 'string'
    },{
        name : 'arrangeBizType',//理货业务类型
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'airDispatch',//是否空运总调
        type : 'string'
    },{
        name : 'isEntityFinance',//是否实体财务部*********
        type : 'string'
    },{
        name : 'entityFinance',//所属实体财务部
        type : 'string'
    },{
        name : 'entityFinanceName',//所属实体财务部名称
        type : 'string'
    },{
        name : 'depCoordinate',//部门服务区坐标编号
        type : 'string'
    },{
        name : 'depTelephone',//部门电话
        type : 'string'
    },{
        name : 'depFax',//部门传真
        type : 'string'
    },{
        name : 'orgSimpleName',//部门简称
        type : 'string'

    },{
    	name : 'deptLevel',//部门层级
        type : 'string'

    },{
        name : 'fullPath',//全路径
        type : 'string'
    },{
        name : 'uumsId',//UUMS主键ID
        type : 'string'
    },{
        name : 'uumsParentId',//UUMS上级主键ID
        type : 'string'
    },{
        name : 'uumsIds',//UUMS主键ID序列
        type : 'string'
    },{
        name : 'isLeaf',//是否为叶子节点
        type : 'string'
    },{
        name : 'parentOrgCode',//上级组织CODE
        type : 'string'
    },{
        name : 'orgEnSimple',//英文简称
        type : 'string'
    },{
    	//快递添加的字段--新增的快递属性14
    	name : 'expressBigRegion',//是否快递大区
    	type : 'string'
    },{
    	name : 'expressSalesDepartment', //是否虚拟快递部门
    	type : 'string'
    },{
    	name : 'expressPart', //是否快递点部
    	type : 'string'
    },{
		name : 'expressBranch', //是否快递分部    
		type : 'string'
	},{
    	name : 'complementSimpleName',//补码简称
    	type :'string'
    },{
    	name:'expressSorting',//是否快递分拣
    	type:'String'
    },{
    	name:'isManageDepartment',//是否经营本部
    	type:'String'
    }]
});
//营业部
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//部门编码
        type : 'string'
    },{
        name : 'name',//部门名称
        type : 'string'
    },{
        name : 'stationNumber',//提货网点编码
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
        name : 'leave',//可出发
        type : 'string'
    },{
        name : 'arrive',//可到达
        type : 'string'
    },{
        name : 'station',//是否驻地部门
        type : 'string'
    },{
        name : 'slogans',//广告语
        type : 'string'
    },{
        name : 'openingDate',//开业日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'cancelArrivalDate',//取消到达日期
        type : 'date',
        defaultValue : null,
        convert : baseinfo.changeLongToDate
    },{
        name : 'maxTempArrears',//最大临欠额度
        type : 'int'
    },{
        name : 'transferGoodDept',//转货部门
        type : 'string'
    },{
        name : 'transferGoodDeptName',//转货部门名称
        type : 'string'
    },{
        name : 'billingGroup',//所属集中开单组
        type : 'string'
    },{
        name : 'billingGroupName',//所属集中开单组名称
        type : 'string'
    },{
        name : 'transferCenter',//驻地营业部所属外场
        type : 'string'
    },{
        name : 'transferCenterName',//驻地营业部所属外场名称
        type : 'string'
    },{
        name : 'pickupSelf',//可自提
        type : 'string'
    },{
        name : 'delivery',//可派送
        type : 'string'
    },{
        name : 'airArrive',//可空运到达
        type : 'string'
    },{
        name : 'truckArrive',//可汽运到达
        type : 'string'
    },{
        name : 'singlePieceLimitkg',//单件重量上限
        type : 'number'
    },{
        name : 'singleBillLimitkg',//单票重量上限
        type : 'number'
    },{
        name : 'singlePieceLimitvol',//单件体积上限
        type : 'number'
    },{
        name : 'singleBillLimitvol',//单票体积上限
        type : 'number'
    },{
        name : 'pickupAreaDesc',//自提区域描述
        type : 'string'
    },{
        name : 'deliveryAreaDesc',//派送区域描述
        type : 'string'
    },{
        name : 'deliveryCoordinate',//派送区坐标编号
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'inCentralizedShuttle',//是否在集中接送货范围内
        type : 'string'
    },{
        name : 'canPayServiceFee',//是否可开装卸费
        type : 'string'
    },{
        name : 'canExpressOneMany',//是否可开快递一票多件
        type : 'string'
    },{
        name : 'canReturnSignBill',//是否可返回签单
        type : 'string'
    },{
        name : 'canCashOnDelivery',//是否可货到付款
        type : 'string'
    },{
        name : 'canReturnSignBill',//是否可返回签单
        type : 'string'
    },{
        name : 'canAgentCollected',//是否代收貨款
        type : 'string'
    },{
    	//快递新增字段 (新增的快递属性14)
    	name:'canExpressReturnSignBill', //可快递返回签单
    	type:'string'
    },{
    	name:'canExpressPickupToDoor', //可快递接货
    	type:'string'
    },{
    	name:'canUpdateDestination',//补录不可修改快递目的站
    	type:'string'
    },{
    	name:'canExpressDoorToDoor',//是否可快递上门发货
    	type:'string'
    },{
    	name:'canExpressDelivery',//可快递派送
    	type:'string'
    },{
    	name:'canExpressPickupSelf',//可快递自提
    	type:'string'
    },{
    	name:'expressDeliveryAreaDesc',//快递派送区域描述
    	type:'string'
    },{
    	name:'expressPickupAreaDesc',//快递自提区域描述
    	type:'string'
    },{
    	name:'expressDeliveryCoordinate',//快递派送区域坐标编码
    	type:'string'
    },{
    	name:'satelliteDept',//是否卫星垫布
    	type:'string'
    },{
    	name:'expressManNum',// 
    	type:'string'
    },{
    	name:'departServiceArea',// 
    	type:'string'
    },{
        name : 'canArriveExpressOneMany',//是否可到快递一票多件-187862-dujunhui
        type : 'string'
    },{
        name : 'canCashOnDeliveryMany',//是否可货到付款(多件)-187862
        type : 'string'
    },{
        name : 'canAgentCollectedMany',//是否可代收货款(多件)-187862
        type : 'string'
    },{
    	name : 'agentCollectedUpperLimit',//代收貨款上限-232608-wusuhua
        type : 'string'
    },{
    	name:'canHomeImproSend',//是否可家装送装
    	type:'string'
    },{
        name : 'isLeagueSaleDept',//是否加盟网点
        type : 'string'
    },{
        name : 'isTwoLevelNetwork',//是否二级网点-308861
        type : 'string'
    },{
        name : 'networkModel',//网点模式-308861
        type : 'string'
    }]
});
//外场
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'orgCode',//部门编码
        type : 'string'
    },{
        name : 'code',//外场编码
        type : 'string'
    },{
        name : 'name',//外场名称
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
        name : 'simpleCode',//外场简码
        type : 'string'
    },{
        name : 'vehicleAssemble',//可汽运配载
        type : 'string'
    },{
        name : 'outAssemble',//可外发配载
        type : 'string'
    },{
        name : 'packingWood',//可打木架
        type : 'string'
    },{
        name : 'transfer',//可中转
        type : 'string'
    },{
        name : 'sortingMachine',//是否有自动分拣机
        type : 'string'
    },{
        name : 'goodsArea',//货区面积
        type : 'string'
    },{
        name : 'platArea',//货台面积
        type : 'string'
    },{
        name : 'platType',//库型
        type : 'string'
    },{
        name : 'parentOrgId',//所属外场
        type : 'string'
    },{
        name : 'motorcadeCode',//所属顶级车队Code
        type : 'string'
    },{
        name : 'motorcadeName',//所属顶级车队Name
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'airDispatchCode',//空运总调编码
        type : 'string'
    },{
        name : 'airDispatchName',//空运总调名称
        type : 'string'
    },{
    	//快递添加的区域--新增的快递属性15
    	name : 'expressOutAssemble',//可落地外发配载
    	type : 'string'
    },{
    	name : 'isHaveWaitForkArea',//是否有待叉区
    	type : 'string'
    },{
    	name : 'manSpeed',//人工速度
    	type : 'string'
    },{
    	name : 'forkSpeed',//电叉速度
    	type : 'string'
    },{
    	name : 'transferServiceChannel',//外场业务渠道dujunhui-187862
    	type : 'string'
    }]
});
//车队
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//部门编码
        type : 'string'
    },{
        name : 'name',//部门名称
        type : 'string'
    },{
        name : 'pinyin',//拼音
        type : 'string'
    },{
        name : 'service',//是否集中接送货
        type : 'string'
    },{
        name : 'serviceCode',//集中接送货车队编码
        type : 'string'
    },{
        name : 'serviceTeam',//是否集中接送货车队组
        type : 'string'
    },{
        name : 'parentOrgCode',//所属车队
        type : 'string'
    },{
        name : 'parentOrgCodeName',//所属车队名称
        type : 'string'
    },{
        name : 'transferCenter',//所服务外场
        type : 'string'
    },{
        name : 'transferCenterName',//所服务外场名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    },{
        name : 'dispatchTeam',//是否车队调度组
        type : 'string'
    },{
        name : 'fleetType',//车队类型
        type : 'string'
    },{
        name : 'isTopFleet',//是否顶级车队
        type : 'string'
    },{
        name : 'isManageVehicle',//是否直接管车
        type : 'string'
    },{
        name : 'serveBillTerm',//所服务的集中开单组的部门编码
        type : 'string'
    },{
        name : 'serveBillTermName',//所服务的集中开单组的部门名称
        type : 'string'
    }]
});
/**
 * 车队负责行政区
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrictEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'motorcadeCode',//车队部门编码
        type : 'string'
    },{
        name : 'motorcadeName',//车队部门名称
        type : 'string'
    },{
        name : 'districtCode',//行政区域部门编码
        type : 'string'
    },{
        name : 'districtName',//行政区域部门名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 车队负责营业区
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesAreaEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'motorcadeCode',//车队部门编码
        type : 'string'
    },{
        name : 'motorcadeName',//车队部门名称
        type : 'string'
    },{
        name : 'salesareaCode',//营业区域部门编码
        type : 'string'
    },{
        name : 'salesareaName',//营业区域部门名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 行政区域区域MODEL
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.AdministrativeRegionsEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'code',//行政区域编号
        type : 'string'
    },{
        name : 'name',//行政区域名称
        type : 'string'
    },{
        name : 'simpleName',//简称
        type : 'string'
    }]
});
/**
 * 营业部车队
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SalesMotorcadeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'salesdeptCode',//营业部编码
        type : 'string'
    },{
    	name:'salesdeptName',//营业部名称
    	type:'string'
    },{
        name : 'motorcadeCode',//车队编码
        type : 'string'
    },{
    	name:'motorcadeName',//车队名称
    	type:'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 营业部产品
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SalesProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'productCode',//产品编码
        type : 'string'
    },{
        name : 'productName',//产品名称
        type : 'string'
    },{
        name : 'salesDeptCode',//营业部编码
        type : 'string'
    },{
        name : 'salesName',//营业部名称
        type : 'string'
    },{
        name : 'salesType',//营业部类型
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 对公银行账号
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.PublicBankAccountEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'bankAcc',//银行账号
        type : 'string'
    },{
        name : 'bankAccName',//银行开户名
        type : 'string'
    },{
        name : 'deptCd',//部门标杆编码
        type : 'string'
    },{
        name : 'bankCd',//银行编码
        type : 'string'
    },{
        name : 'bankName',//银行名称
        type : 'string'
    },{
        name : 'subbranchCd',//支行编码
        type : 'string'
    },{
        name : 'subbranchName',//支行名称
        type : 'string'
    },{
        name : 'provCd',//省份编码
        type : 'string'
    },{
        name : 'provName',//省份名称
        type : 'string'
    },{
        name : 'cityCd',//城市编码
        type : 'string'
    },{
        name : 'cityName',//城市名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 集中开单组对应的外场
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'transFerCode',//外场编码
        type : 'string'
    },{
        name : 'transFerName',//外场名称
        type : 'string'
    },{
        name : 'billingGroupCode',//集中开单组编码
        type : 'string'
    },{
        name : 'billingGroupName',//集中开单组名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});

/**
 * 保安组
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'transcenterCode',//外场编码
        type : 'string'
    },{
        name : 'transcenterName',//外场名称
        type : 'string'
    },{
        name : 'securityCode',//保安组code
        type : 'string'
    },{
        name : 'securityName',//保安组名称
        type : 'string'
    },{
        name : 'motorcadeCode',//车队code
        type : 'string'
    },{
        name : 'motorcadeNmae',//车队name
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 集中开单组服务营业部
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SalesBillingGroupEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'virtualCode',//虚拟编码
        type : 'string'
    },{
        name : 'salesDeptCode',//营业部编码
        type : 'string'
    },{
        name : 'salesDeptName',//营业部名称
        type : 'string'
    },{
        name : 'billingGroupCode',//集中开单组编码
        type : 'string'
    },{
        name : 'billingGroupName',//集中开单组名称
        type : 'string'
    },{
        name : 'active',//是否启用
        type : 'string'
    }]
});
/**
 * 定义功能树的store
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.DepartmentTreeStore',{
	extend: 'Ext.data.TreeStore',
	autoSync:true,
	proxy : {
		type : 'ajax',
		url : baseinfo.realPath('loadDepartmentTree.action')
	},
	root : {
		id : baseinfo.orgAdministrativeInfo.ORG_ROOT_ID,//实际是根部门的CODE
		text : '集团',//集团
		expanded : true
	},
	sorters : [ {
		property : 'leaf',
		direction : 'ASC'
	} ]
});
//------------------------------------model---------------------------------------------------
/**
 * @description 行政组织树形结构
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgTreeSearchPanel', {
	extend:'Ext.panel.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.orgBizProperty'),//行政组织业务属性
    width:275,
    height:955,
    frame:true,
     //查询FORM
    searchDepartmentForm:null,
	getSearchDepartmentForm:function(){
		if(Ext.isEmpty(this.searchDepartmentForm)){
    		this.searchDepartmentForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SearchDepartmentForm');
    	}
    	return this.searchDepartmentForm;
	},
    //部门树PANEL
	departmentTreePanle:null,
	getDepartmentTreePanle:function(){
		if(Ext.isEmpty(this.departmentTreePanle)){
    		this.departmentTreePanle = Ext.create('Foss.baseinfo.orgAdministrativeInfo.DepartmentTreePanle');
    	}
    	return this.departmentTreePanle;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.	items  = [me.getSearchDepartmentForm(),me.getDepartmentTreePanle()];
		me.callParent([cfg]);
    }
});
/**
 * @description 查询部门界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SearchDepartmentForm', {
	extend:'Ext.form.Panel',  
    height:80,
    layout:{
		type:'table',
		columns: 2
	},
	//树形结构查询部门
    searchDept:function(){
    	var me = this;
    	if(!Ext.isEmpty(me)){
    		if(!Ext.isEmpty(me.dockedItems)){
    			if(!Ext.isEmpty(me.dockedItems.items[0])){
    				if(!Ext.isEmpty(me.dockedItems.items[0].items)){
    					if(!Ext.isEmpty(me.dockedItems.items[0].items.items[0])){
    				    	me.dockedItems.items[0].items.items[0].setDisabled(true);
                		}
            		}
        		}
    		}
    	}
    	//baseinfo.seqAll=null;
		var text = me.getForm().findField('deptName').getValue();
		if(Ext.isEmpty(text.trim())){
			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseEnterQuery'));//请输入查询信息！
	    	if(!Ext.isEmpty(me)){
	    		if(!Ext.isEmpty(me.dockedItems)){
	    			if(!Ext.isEmpty(me.dockedItems.items[0])){
	    				if(!Ext.isEmpty(me.dockedItems.items[0].items)){
	    					if(!Ext.isEmpty(me.dockedItems.items[0].items.items[0])){
	    				    	me.dockedItems.items[0].items.items[0].setDisabled(false);
	                		}
	            		}
	        		}
	    		}
	    	}
			return;
		}
		var array = {'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':{'name':text}}};//传参
		Ext.Ajax.request({
			url :baseinfo.realPath('queryAllFullPath.action'),//请求全路径的标杆编码的集合“.”分隔和查询到的第一个部门的全路径
			jsonData:array,
			//async:false,
			success : function(response) {   
				json = Ext.decode(response.responseText);
		    	if(!Ext.isEmpty(me)){
		    		if(!Ext.isEmpty(me.dockedItems)){
		    			if(!Ext.isEmpty(me.dockedItems.items[0])){
		    				if(!Ext.isEmpty(me.dockedItems.items[0].items)){
		    					if(!Ext.isEmpty(me.dockedItems.items[0].items.items[0])){
		    				    	me.dockedItems.items[0].items.items[0].setDisabled(false);
		                		}
		            		}
		        		}
		    		}
		    	}
				//baseinfo.seqAll=json.orgAdministrativeInfoVo.allFullPath;//全路径的标杆编码的集合“.”分隔
				var seq = json.orgAdministrativeInfoVo.fullPath;//查询到的第一个部门的全路径
				if(Ext.isEmpty(seq)){//如果没有查到，则展开根结点(提示用户没有这个信息)，
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.queryNoResult'));//查询无信息！
				}else{
					me.up('panel').getDepartmentTreePanle().selectPath(seq,'id','.');//再按第一个路径展开	
				}            								 
			},
			failure : function(response) {
				var result = Ext.decode(response.responseText);
		    	if(!Ext.isEmpty(me)){
		    		if(!Ext.isEmpty(me.dockedItems)){
		    			if(!Ext.isEmpty(me.dockedItems.items[0])){
		    				if(!Ext.isEmpty(me.dockedItems.items[0].items)){
		    					if(!Ext.isEmpty(me.dockedItems.items[0].items.items[0])){
		    				    	me.dockedItems.items[0].items.items[0].setDisabled(false);
		                		}
		            		}
		        		}
		    		}
		    	}
			},
			exception : function(response) {
				var result = Ext.decode(response.responseText);
		    	if(!Ext.isEmpty(me)){
		    		if(!Ext.isEmpty(me.dockedItems)){
		    			if(!Ext.isEmpty(me.dockedItems.items[0])){
		    				if(!Ext.isEmpty(me.dockedItems.items[0].items)){
		    					if(!Ext.isEmpty(me.dockedItems.items[0].items.items[0])){
		    				    	me.dockedItems.items[0].items.items[0].setDisabled(false);
		                		}
		            		}
		        		}
		    		}
		    	}
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
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizQueryButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizQueryButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.query'),//查询
			handler : function() {
				me.searchDept();
			}
		}];
		me.items = [{
			xtype : 'textfield',
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.orgName'),//部门名称
			labelPad : 4,
			labelWidth : 70,
			width:240,
			listeners : {  
			   specialkey : function(field, e) {  
				 if (e.getKey() == Ext.EventObject.ENTER) {
					 me.searchDept();
				   }  
				}
			}  ,
			name : 'deptName',
		    blankText : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseEnterNameDepartmentInquiries')//请输入部门名称查询
		}];
		me.callParent([cfg]);
	}
});
/**
 * 左侧树结构面板
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.DepartmentTreePanle', {
    extend:'Ext.tree.Panel',
	autoScroll:true,
	margin:false,  
	border : false,
	height:700,
	oldFullPath:null,//刷新之前展开的路径
	useArrows: true,
	
	rootVisible: true,  
	viewConfig: {plugins: { ptype: 'treeviewdragdrop', appendOnly: true } },
	layoutConfig : {
		// 展开折叠是否有动画效果
		animate : true
	},
	oldId:null,
	//清空数据+关闭form操作
	removeData:function(){
		var me =this;
		//获取主面板
		var orgInfoPanel=me.up('panel').up('panel').getOrgInfoPanel();
		orgInfoPanel.getOrgMainInfoForm().getForm().reset();//清空主信息面板的数据
		orgInfoPanel.getOrgBusinessInfoForm().getForm().reset();//清除行政业务属性的原有数据;
		orgInfoPanel.getOrgAuxiliaryInfoForm().getForm().reset();//清除行政组织基础原有数据
		orgInfoPanel.getSaleDeptMainPanel().getForm().findField('salesDepartment').setValue('false'); 		//清空‘是否营业部’
		orgInfoPanel.getOutfieldMainForm().getForm().findField('transferCenter').setValue('false'); 		//清空‘是否外场’
		orgInfoPanel.getMotorcadeMainForm().getForm().findField('transDepartment').setValue('false');		//清空‘是否车队’
		orgInfoPanel.getControlGroupMainForm().getForm().findField('dispatchTeam').setValue('false');		//清空‘是否调度组’
		orgInfoPanel.getMotorcadeGroupMainForm().getForm().findField('transTeam').setValue('false');		//清空‘是否车队组’
		orgInfoPanel.getDeliverScheduleMainForm().getForm().findField('isDeliverSchedule').setValue('false');//	清空’是否派送排单’
		orgInfoPanel.getBillingGroupMainForm().getForm().findField('billingGroup').setValue('false');		//清空‘是否集中开单组’
		orgInfoPanel.getArrangeGoodsMainForm().getForm().findField('isArrangeGoods').setValue('false');	//清空‘是否理货部门’
		orgInfoPanel.getSecurityTfrMotorcadeMainForm().getForm().findField('isSecurity').setValue('false');	//清空‘是否保安组’
	},
	//加载数据+和展开页面
	loadDataAndExpand:function(orgAdministrativeInfoModel){
		var me =this;
		//获取主面板
		var orgInfoPanel=me.up('panel').up('panel').getOrgInfoPanel();
		orgInfoPanel.getOrgBusinessInfoForm().getForm().loadRecord(orgAdministrativeInfoModel);//加载行政组织业务属性数据
		//若是事业部
		if(orgAdministrativeInfoModel.get('division')=='Y'){
			//事业部编码为空
			if(Ext.isEmpty(orgAdministrativeInfoModel.get('divisionCode'))){
				orgInfoPanel.getOrgBusinessInfoForm().getForm().findField('divisionCode').setValue(orgAdministrativeInfoModel.get('code'));
			}
		}
		orgInfoPanel.getOrgAuxiliaryInfoForm().getForm().loadRecord(orgAdministrativeInfoModel); //加载基本行政业务信息
		orgInfoPanel.getSaleDeptMainPanel().getForm().findField('salesDepartment').setValue(orgAdministrativeInfoModel.get('salesDepartment'));//加载‘是否是营业部’
		orgInfoPanel.getOutfieldMainForm().getForm().findField('transferCenter').setValue(orgAdministrativeInfoModel.get('transferCenter'));//加载‘是否是外场’
		orgInfoPanel.getControlGroupMainForm().getForm().findField('dispatchTeam').setValue(orgAdministrativeInfoModel.get('dispatchTeam'));//加载’是否调度组‘
		orgInfoPanel.getMotorcadeGroupMainForm().getForm().findField('transTeam').setValue(orgAdministrativeInfoModel.get('transTeam'));//加载‘是否车队组’
		orgInfoPanel.getMotorcadeMainForm().getForm().findField('transDepartment').setValue(orgAdministrativeInfoModel.get('transDepartment')); //加载‘是否车队’
		orgInfoPanel.getDeliverScheduleMainForm().getForm().findField('isDeliverSchedule').setValue(orgAdministrativeInfoModel.get('isDeliverSchedule'));//加载’是否派送排单‘
		orgInfoPanel.getArrangeGoodsMainForm().getForm().findField('isArrangeGoods').setValue(orgAdministrativeInfoModel.get('isArrangeGoods'));//加载’是否理货部门‘
		orgInfoPanel.getBillingGroupMainForm().getForm().findField('billingGroup').setValue(orgAdministrativeInfoModel.get('billingGroup'));//加载’是否集中开单组‘
		orgInfoPanel.getSecurityTfrMotorcadeMainForm().getForm().findField('isSecurity').setValue(orgAdministrativeInfoModel.get('isSecurity'));//加载’是否保安组‘
		//若是行政业务属性中的信息,展开该页面
		if((orgAdministrativeInfoModel.get('orgSimpleName')=='Y')||(orgAdministrativeInfoModel.get('airDispatch')=='Y')
				||(orgAdministrativeInfoModel.get('smallRegion')=='Y')||(orgAdministrativeInfoModel.get('bigRegion')=='Y')
				||(orgAdministrativeInfoModel.get('isEntityFinance')=='Y')||(orgAdministrativeInfoModel.get('division')=='Y')){
			orgInfoPanel.getOrgBusinessInfoForm().expand();
		}else{
			orgInfoPanel.getOrgBusinessInfoForm().collapse();
		}
		//若是营业部，展开营业部form
		if(orgAdministrativeInfoModel.get('salesDepartment')=='Y'){
			orgInfoPanel.getSaleDeptMainPanel().expand();
		}else {
			orgInfoPanel.getSaleDeptMainPanel().collapse();
		}
		//若是外场，展开外场的form
		if(orgAdministrativeInfoModel.get('transferCenter')=='Y'){
			orgInfoPanel.getOutfieldMainForm().expand();
		}else {
			orgInfoPanel.getOutfieldMainForm().collapse();
		}
		//若是车队调度组，展开车队调度form
		if(orgAdministrativeInfoModel.get('dispatchTeam')=='Y'){
			orgInfoPanel.getControlGroupMainForm().expand();
		}else {
			orgInfoPanel.getControlGroupMainForm().collapse();
		}
		//若是车队组，展开车队组
		if(orgAdministrativeInfoModel.get('transTeam')=='Y'){
			orgInfoPanel.getMotorcadeGroupMainForm().expand();
		}else {
			orgInfoPanel.getMotorcadeGroupMainForm().collapse();
		}
		//若是车队，展开车队form
		if(orgAdministrativeInfoModel.get('transDepartment')=='Y'){
			orgInfoPanel.getMotorcadeMainForm().expand();
		}else {
			orgInfoPanel.getMotorcadeMainForm().collapse();
		}
		//若是排单部门，展开排单部门form
		if(orgAdministrativeInfoModel.get('isDeliverSchedule')=='Y'){
			orgInfoPanel.getDeliverScheduleMainForm().expand();
		}else {
			orgInfoPanel.getDeliverScheduleMainForm().collapse();
		}
		//若是理货部门，展开理货部门form
		if(orgAdministrativeInfoModel.get('isArrangeGoods')=='Y'){
			orgInfoPanel.getArrangeGoodsMainForm().expand();
		}else {
			orgInfoPanel.getArrangeGoodsMainForm().collapse()
		}
		//若是集中开单组，展开集中开单组form
		if(orgAdministrativeInfoModel.get('billingGroup')=='Y'){
			orgInfoPanel.getBillingGroupMainForm().expand();
		}else {
			orgInfoPanel.getBillingGroupMainForm().collapse();
		}
		//若是保安组，展开保安组form
		if(orgAdministrativeInfoModel.get('isSecurity')=='Y'){
			orgInfoPanel.getSecurityTfrMotorcadeMainForm().expand();
		}else {
			orgInfoPanel.getSecurityTfrMotorcadeMainForm().collapse();
		}
	},
	//根据用户所在部门操作权限设置按钮
	buttonIsSetDisabled:function(buttonStatus){
		var me =this;
		//获取主面板
		var orgInfoPanel=me.up('panel').up('panel').getOrgInfoPanel();
		//根据用户所在部门操作权限设置保存的button属性
		Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id').setDisabled(buttonStatus);
		//根据用户所在部门操作权限设置修改的button属性
		Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(buttonStatus);
		//
		Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateDeptButton_Id').setDisabled(buttonStatus);
		//行政组织业务属性的保存的属性
		orgInfoPanel.getOrgBusinessInfoForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//营业部保存的button属性
		orgInfoPanel.getSaleDeptMainPanel().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//营业部修改的button属性
		orgInfoPanel.getSaleDeptMainPanel().getDockedItems()[1].items.items[2].setDisabled(buttonStatus);
		//外场保存的button属性
		orgInfoPanel.getOutfieldMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//集中开单组保存的button属性
		orgInfoPanel.getBillingGroupMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//理货部门保存的button属性
		orgInfoPanel.getArrangeGoodsMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//排单部门保存的button属性
		orgInfoPanel.getDeliverScheduleMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//车队保存的button属性
		orgInfoPanel.getMotorcadeMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//车队调度组保存的button属性
		orgInfoPanel.getControlGroupMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//车队组保存的button属性
		orgInfoPanel.getMotorcadeGroupMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
		//保安组保存的button属性
		orgInfoPanel.getSecurityTfrMotorcadeMainForm().getDockedItems()[1].items.items[1].setDisabled(buttonStatus);
	},
	//左键单击事件
	treeLeftKeyAction:function(node,record,item,index,e){
		var me = this,
			orgInfoPanel = me.up('panel').up('panel').getOrgInfoPanel(),
			orgMainInfoForm = orgInfoPanel.getOrgMainInfoForm(),//主信息form
			orgBusinessInfoForm	= orgInfoPanel.getOrgBusinessInfoForm(),//行政业务属性的Form
			saleDeptMainPanel =orgInfoPanel.getSaleDeptMainPanel();
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id').setDisabled(true);
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(true);
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateDeptButton_Id').setDisabled(true);
		if(Ext.isEmpty(record.raw)||record.raw.entity.id==me.oldId){
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id').setDisabled(false);
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(false);
			Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateDeptButton_Id').setDisabled(false);
			return;
		}else{
			me.oldId = record.raw.entity.id;
			orgMainInfoForm.outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();//外场相关信息为空
			orgMainInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();//车队相关信息为空
			orgMainInfoForm.saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity();//营业部相关信息为空
			orgMainInfoForm.billingGroupTransFerModel = new Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity();//集中开单组外场相关信息为空
			orgMainInfoForm.securityTfrMotorcadeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity();//保安組
			orgMainInfoForm.salesProductEntityList = [];
			orgMainInfoForm.salesMotorcadeEntityList = [];
			orgMainInfoForm.centralizedBillingGroupList = [];
			orgMainInfoForm.selectedCentralizedBillingGroupList = [];
			orgMainInfoForm.motorcadeServeDistrictEntityList=[];
			orgMainInfoForm.motorcadeServeSalesAreaEntityList=[];
			orgMainInfoForm.motorcadeServeSalesDeptEntityList=[];
			orgMainInfoForm.billingGroupServeSalesDeptEntityList =[];//在发送请求前，设置所有数据为空，然后再reset
			orgMainInfoForm.securityTfrMotorcadeEntityList = [];
			
			me.removeData();//发送请求之前先清空数据
			if(!Ext.isEmpty(record.raw)){
				var orgAdministrativeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.OrgAdministrativeInfoEntity(record.raw.entity);//得到部门的model
				me.oldFullPath = orgAdministrativeInfoModel.get('fullPath');
				orgMainInfoForm.orgAdministrativeInfoModel = orgAdministrativeInfoModel;//设置加载的数据，重置用
				var params = {'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':{'code':orgAdministrativeInfoModel.get('code')}}};
		    	var successFun = function(json){
		    		//获得行政组织信息
		    		orgAdministrativeInfoModel =new Foss.baseinfo.orgAdministrativeInfo.OrgAdministrativeInfoEntity(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity);
		    		//对公银行账号信息
		    		var publicBankAccountForm = orgInfoPanel.getPublicBankAccountForm();
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.publicBankAccountEntity)){
		    			publicBankAccountModel = new Foss.baseinfo.orgAdministrativeInfo.PublicBankAccountEntity();//设置加载的银行账号数据(空)
		    		}else{
		    			publicBankAccountModel = new Foss.baseinfo.orgAdministrativeInfo.PublicBankAccountEntity(json.orgAdministrativeInfoVo.publicBankAccountEntity);//设置加载的对公银行账号数据
		    		}
		    		publicBankAccountForm.getForm().loadRecord(publicBankAccountModel);
		    		//营业部form 
		    		var saleDepartmentModel = null;
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.saleDepartmentEntity)){
		    			saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity();//设置加载的营业部数据(空)
		    		}else{
		    			if(json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitkg>0){
		    				json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitkg=json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitkg/100;
		    			}
		    			if(json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitvol>0){
		    				json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitvol=json.orgAdministrativeInfoVo.saleDepartmentEntity.singleBillLimitvol/100;
		    			}
		    			if(json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitkg>0){
		    				json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitkg=json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitkg/100;
		    			}
		    			if(json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitvol>0){
		    				json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitvol=json.orgAdministrativeInfoVo.saleDepartmentEntity.singlePieceLimitvol/100;
		    			}
		    			saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity(json.orgAdministrativeInfoVo.saleDepartmentEntity);//设置加载的营业部数据
		    		}
		    		var salesMotorcadeEntityList = [];//营业部车队信息
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.salesMotorcadeEntityList)){
		    			salesMotorcadeEntityList = [];
		    		}else{
		    			salesMotorcadeEntityList = json.orgAdministrativeInfoVo.salesMotorcadeEntityList;
		    		}
		    		var salesProductEntityList = [];//营业产品信息
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.salesProductEntityList)){
		    			salesProductEntityList = [];
		    		}else{
		    			salesProductEntityList = json.orgAdministrativeInfoVo.salesProductEntityList;
		    		}
		    		var selectedCentralizedBillingGroupList = [];//营业部集中开单组信息
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.selectedCentralizedBillingGroupList)){
		    			selectedCentralizedBillingGroupList = [];
		    		}else{
		    			selectedCentralizedBillingGroupList = json.orgAdministrativeInfoVo.selectedCentralizedBillingGroupList;
		    		}
		    		var saleDepartmentInfoPanel = saleDeptMainPanel.getSaleDepartmentInfoPanel();
		    		saleDepartmentInfoPanel.salesMotorcadeEntityList = salesMotorcadeEntityList;//设置营业车队信息
		    		saleDepartmentInfoPanel.salesProductEntityList = salesProductEntityList;//设置营产品信息
		    		saleDepartmentInfoPanel.selectedCentralizedBillingGroupList = selectedCentralizedBillingGroupList;//设置已选集中开单组信息
		    		//外场主要panel
		    		var outfieldMainForm = orgInfoPanel.getOutfieldMainForm();
		    		//外场form 
		    		var outfieldInfoForm=outfieldMainForm.getOutfieldInfoForm();
		    		var outfieldModel = null;
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.outfieldEntity)){
		    			outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();//设置加载的外场数据(空)
		    		}else{
		    			outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity(json.orgAdministrativeInfoVo.outfieldEntity);//设置加载的外场数据
		    		}
		    		outfieldInfoForm.outfieldModel = outfieldModel;//设置外场属性
		    		//设置所属顶级车队的Name(解决Bug-7913)
		    		outfieldInfoForm.getForm().getFields().items[9].setCombValue(outfieldModel.get('motorcadeName'),outfieldModel.get('motorcadeCode'));
		    		//车队
		    		var motorcadeModel = null;
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.motorcadeEntity)){
		    			motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();//设置加载的车队数据(空)
		    		}else{
		    			motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity(json.orgAdministrativeInfoVo.motorcadeEntity);//设置加载的车队数据
		    		}
		    		var motorcadeServeSalesAreaEntityList = json.orgAdministrativeInfoVo.motorcadeServeSalesAreaEntityList;//车队负责营业区数据
		    		var motorcadeServeDistrictEntityList = json.orgAdministrativeInfoVo.motorcadeServeDistrictEntityList;//车队负责行政区域数据
		    		var motorcadeServeSalesDeptEntityList =json.orgAdministrativeInfoVo.motorcadeServeSalesDeptEntityList;//车队所服务营业部数据
		    		if(Ext.isEmpty(motorcadeServeSalesAreaEntityList)){//防止null的存在
		    			motorcadeServeSalesAreaEntityList = [];
		    		}
		    		if(Ext.isEmpty(motorcadeServeDistrictEntityList)){
		    			motorcadeServeDistrictEntityList = [];
		    		}
		    		if(Ext.isEmpty(motorcadeServeSalesDeptEntityList)){
		    			motorcadeServeSalesDeptEntityList =[];
		    		}
		    		if(orgMainInfoForm.orgAdministrativeInfoModel.get('transDepartment')=='Y'){
		    			//车队form
			    		var motorcadeInfoForm = orgInfoPanel.getMotorcadeMainForm().getMotorcadeInfoForm();
			    			motorcadeInfoForm.motorcadeModel = motorcadeModel;
			    			motorcadeInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
			    			motorcadeInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
			    			motorcadeInfoForm.motorcadeServeSalesDeptEntityList = motorcadeServeSalesDeptEntityList;
		    		}else if(orgMainInfoForm.orgAdministrativeInfoModel.get('dispatchTeam')=='Y'){
		    			//车队调度组form
			    		var controlGroupInfoForm = orgInfoPanel.getControlGroupMainForm().getControlGroupInfoForm();
			    			controlGroupInfoForm.motorcadeModel = motorcadeModel;
			    			controlGroupInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
			    			controlGroupInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
		    		}else if(orgMainInfoForm.orgAdministrativeInfoModel.get('transTeam')=='Y'){
		    			//车队组form
			    		var motorcadeGroupInfoForm = orgInfoPanel.getMotorcadeGroupMainForm().getMotorcadeGroupInfoForm();
			    			motorcadeGroupInfoForm.motorcadeModel = motorcadeModel;
			    			motorcadeGroupInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
			    			motorcadeGroupInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
		    		}
		    		//集中开单组form
		    		var billingGroupNameInfoForm = orgInfoPanel.getBillingGroupMainForm().getBillingGroupNameInfoForm();
		    		var billingGroupTransFerModel = null;
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.billingGroupTransFerEntity)){
		    			billingGroupTransFerModel = new Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity();//设置加载的开单组外场数据(空)
		    		}else{
		    			billingGroupTransFerModel = new Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity(json.orgAdministrativeInfoVo.billingGroupTransFerEntity);//设置加载的开单组外场数据
		    		}
		    		var billingGroupServeSalesDeptEntityList = json.orgAdministrativeInfoVo.salesBillingGroupEntityList;
		    		if(Ext.isEmpty(billingGroupServeSalesDeptEntityList)){//防止null的存在
		    			billingGroupServeSalesDeptEntityList =[];
		    		}
		    		billingGroupNameInfoForm.billingGroupTransFerModel =billingGroupTransFerModel;//设置开单组外场属性
		    		billingGroupNameInfoForm.billingGroupServeSalesDeptEntityList =billingGroupServeSalesDeptEntityList;//设置集中开单组营业部
		    		
		    		//保安组form
		    		var securityTfrMotorcadeInfoForm = orgInfoPanel.getSecurityTfrMotorcadeMainForm().getSecurityTfrMotorcadeInfoForm();
		    		var securityTfrMotorcadeInfoModel = null;
		    		if(Ext.isEmpty(json.orgAdministrativeInfoVo.securityTfrMotorcadeEntity)){
		    			securityTfrMotorcadeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity();//设置加载的开单组外场数据(空)
		    		}else{
		    			securityTfrMotorcadeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity(json.orgAdministrativeInfoVo.securityTfrMotorcadeEntity);//设置加载的开单组外场数据
		    		}
		    		var securityTfrMotorcadeEntityList = json.orgAdministrativeInfoVo.securityTfrMotorcadeEntityList;
		    		if(Ext.isEmpty(securityTfrMotorcadeEntityList)){//防止null的存在
		    			securityTfrMotorcadeEntityList =[];
		    		}
		    		securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel =securityTfrMotorcadeInfoModel;//设置保安组
		    		securityTfrMotorcadeInfoForm.securityTfrMotorcadeEntityList =securityTfrMotorcadeEntityList;//设置保安组
		    		
		    		
		    		
		    		orgMainInfoForm.motorcadeModel = motorcadeModel;//车队，车队组，车队调度组
		    		orgMainInfoForm.outfieldModel = outfieldModel;//外场
		    		orgMainInfoForm.saleDepartmentModel = saleDepartmentModel;//营业部
		    		orgMainInfoForm.salesProductEntityList = salesProductEntityList;//营业部产品信息
		    		orgMainInfoForm.salesMotorcadeEntityList = salesMotorcadeEntityList;//营业部车队信息
		    		orgMainInfoForm.selectedCentralizedBillingGroupList = selectedCentralizedBillingGroupList;//营业部集中开单组信息
		    		orgMainInfoForm.billingGroupTransFerModel =billingGroupTransFerModel;//开单组外场信息
		    		orgMainInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
		    		orgMainInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
		    		orgMainInfoForm.motorcadeServeSalesDeptEntityList =motorcadeServeSalesDeptEntityList;
		    		orgMainInfoForm.billingGroupServeSalesDeptEntityList =billingGroupServeSalesDeptEntityList;
		    		orgMainInfoForm.billingGroupServeSalesDeptEntityList =billingGroupServeSalesDeptEntityList;
		    		orgMainInfoForm.securityTfrMotorcadeInfoModel = securityTfrMotorcadeInfoModel;//保安组
		    		
		    		orgMainInfoForm.getForm().loadRecord(orgAdministrativeInfoModel);//加载数据
		    		orgMainInfoForm.getForm().findField('entityFinance').setCombValue(orgAdministrativeInfoModel.get('entityFinanceName'),orgAdministrativeInfoModel.get('entityFinance'));//所属实体财务部
		    		orgMainInfoForm.items.items[4].setReginValue(orgAdministrativeInfoModel.get('countryRegionName'),orgAdministrativeInfoModel.get('countryRegion'),0);
		    		orgMainInfoForm.items.items[4].setReginValue(orgAdministrativeInfoModel.get('provName'),orgAdministrativeInfoModel.get('provCode'),1);
		    		orgMainInfoForm.items.items[4].setReginValue(orgAdministrativeInfoModel.get('cityName'),orgAdministrativeInfoModel.get('cityCode'),2);
		    		orgMainInfoForm.items.items[4].setReginValue(orgAdministrativeInfoModel.get('countyName'),orgAdministrativeInfoModel.get('countyCode'),3);
		    		//加载数据并展开form
		    		me.loadDataAndExpand(orgAdministrativeInfoModel);
		    		
					//根据用户部门操作权限设置编辑部门电子地图的Button属性
					if(!Ext.isEmpty(orgInfoPanel)){
						if(!Ext.isEmpty(orgInfoPanel.getOrgMainInfoForm())){
							//部门编辑电子按钮
							if(!Ext.isEmpty(orgMainInfoForm.items)){
//								orgMainInfoForm.items.items[13].setDisabled(json.orgAdministrativeInfoVo.buttonStatus);
								Ext.getCmp('editDepartmentMapButton_id').setDisabled(json.orgAdministrativeInfoVo.buttonStatus);//按钮权限优化：dujunhui-187862
							}
						}
						//营业部主界面存在
						if(!Ext.isEmpty(orgInfoPanel.getSaleDeptMainPanel())){
							var salesDeptMainPanel =orgInfoPanel.getSaleDeptMainPanel();
							//部门是营业部
							if(salesDeptMainPanel.getForm().findField('salesDepartment').checked){
								if(!Ext.isEmpty(salesDeptMainPanel.getSaleDepartmentInfoPanel())){
									//若是可到达时
									if(orgMainInfoForm.saleDepartmentModel.get('arrive')=='Y'){
										baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'LD','show');
										Ext.getCmp('editDelivery_id').setDisabled(json.orgAdministrativeInfoVo.buttonStatus);
									}else{
										baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'LD','hide');
									}
									//若是可快递接货,或者可快递派送
									if((orgMainInfoForm.saleDepartmentModel.get('canExpressPickupToDoor')=='Y')
											||orgMainInfoForm.saleDepartmentModel.get('canExpressDelivery')=='Y'){
										baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','show');
									}else{
										baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','hide');
									}
									
								}
							}else{
								baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'LD','hide');
								baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','hide');
							}
						}
					}
					//先获取数据   再设置为不可编辑
					orgInfoPanel.colseFun();
					//根据用户的权限设置按钮
					me.buttonIsSetDisabled(json.orgAdministrativeInfoVo.buttonStatus);
		    	};
		    	var failureFun = function(json){
		    		if(Ext.isEmpty(json)){
						baseinfo.showErrorMes('请求超时');//请求超时
					}else{
						baseinfo.showErrorMes(json.message);
					}
		    	};
		    	var url = baseinfo.realPath('searchOrgAdministrativeBusinessInfo.action');
		    	//Ajax请求--json
	    		Ext.Ajax.request({
	    			url:url,
	    			jsonData:params, 
					async: false,		//设置为发起同步请求
	    			success:function(response){
	    				var result = Ext.decode(response.responseText);
	    				if(result.success){
	    					successFun(result);
	    					//根据用户所在部门操作权限设置保存的button属性
	    					Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id').setDisabled(false);
	    					//根据用户所在部门操作权限设置修改的button属性
	    					Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(false);
	    					//根据用户所在部门操作权限设置修改的button属性
	    					Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateDeptButton_Id').setDisabled(false);
	    					//根据用户所在部门操作权限设置修改的button属性
	    					Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id').setDisabled(result.orgAdministrativeInfoVo.buttonStatus);
	    					//根据用户所在部门操作权限设置保存的button属性
	    					Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id').setDisabled(result.orgAdministrativeInfoVo.buttonStatus);
	    				}else{
	    					failureFun(result);
	    				}
	    			},
	    			failure:function(response){
	    				var result = Ext.decode(response.responseText);
	    				failureFun(result);
	    			},
	    			exception:function(response){
	    				var result = Ext.decode(response.responseText);
	    				failureFun(result);
	    			}
	    		});  
			}
		}
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.orgAdministrativeInfo.DepartmentTreeStore');
		// 监听事件
		me.listeners={
		  	scrollershow: function(scroller) {
		  		if (scroller && scroller.scrollEl) {
		  				scroller.clearManagedListeners(); 
		  				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		  		}
		  	},
	    	itemclick : me.treeLeftKeyAction//单击事件
	    },
		me.callParent([cfg]);
     }
});
/**
 * =============================================@description 行政组织查主界面=============================================
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgInfoPanel', {
	extend:'Ext.panel.Panel', 
	frame:true,
	id:'Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id',
	width:735,
	height:'auto',
	layout:'auto',
	//定义mask 罩
	myMask:null,
	getMyMask:function(){
		if(Ext.isEmpty(this.myMask)){
			this.myMask = new Ext.LoadMask(this, {msg:"Please wait..."});
		}
		return this.myMask;
	},
	//组织架构基本信息FORM
    orgAuxiliaryInfoForm:null,
	getOrgAuxiliaryInfoForm:function(){
		if(Ext.isEmpty(this.orgAuxiliaryInfoForm)){
    		this.orgAuxiliaryInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OrgAuxiliaryInfoForm');
    	}
    	return this.orgAuxiliaryInfoForm;
	},
	//对公账户信息
	publicBankAccountForm:null,
	getPublicBankAccountForm:function(){
		if(Ext.isEmpty(this.publicBankAccountForm)){
    		this.publicBankAccountForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.PublicBankAccountForm');
    	}
    	return this.publicBankAccountForm;
	},
	//行政组织主信息FORM
	orgMainInfoForm:null,
	getOrgMainInfoForm:function(){
		if(Ext.isEmpty(this.orgMainInfoForm)){
    		this.orgMainInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OrgMainInfoForm');
    		this.orgMainInfoForm.getForm().findField('orgEnSimple').allowBlank = false;//不可为空
    	}
    	return this.orgMainInfoForm;
	},
	//行政组织业务属性Form
	orgBusinessInfoForm:null,
	getOrgBusinessInfoForm:function(){
		if(Ext.isEmpty(this.orgBusinessInfoForm)){
			this.orgBusinessInfoForm =Ext.create('Foss.baseinfo.orgAdministrativeInfo.OrgBusinessInfoForm');
		}
		return this.orgBusinessInfoForm;
	},
	//营业部信息主页面(营业部信息+checkBox)
	saleDeptMainPanel:null,
	getSaleDeptMainPanel:function(){
		if(Ext.isEmpty(this.saleDeptMainPanel)){
    		this.saleDeptMainPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDeptMainPanel');
    	}
    	return this.saleDeptMainPanel;
	},
	//外场信息主界面(外场信息+checkBox)
	outfieldMainForm:null,
	getOutfieldMainForm:function(){
		if(Ext.isEmpty(this.outfieldMainForm)){
    		this.outfieldMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OutfieldMainForm');
    	}
    	return this.outfieldMainForm;
	},
	//派送排单信息主界面(派送派单信息+checkBox)
	deliverScheduleMainForm:null,
	getDeliverScheduleMainForm:function(){
		if(Ext.isEmpty(this.deliverScheduleMainForm)){
    		this.deliverScheduleMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.DeliverScheduleMainForm');
    	}
    	return this.deliverScheduleMainForm;
	},
	//理货部门信息主界面(理货部门信息+checkBox)
	arrangeGoodsMainForm:null,
	getArrangeGoodsMainForm:function(){
		if(Ext.isEmpty(this.arrangeGoodsMainForm)){
    		this.arrangeGoodsMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ArrangeGoodsMainForm');
    	}
    	return this.arrangeGoodsMainForm;
	},
	//车队信息主界面
	motorcadeMainForm:null,
	getMotorcadeMainForm:function(){
		if(Ext.isEmpty(this.motorcadeMainForm)){
    		this.motorcadeMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeMainForm');
    	}
    	return this.motorcadeMainForm;
	},
	//车队调度组信息主界面(调度组信息+checkBox)
	controlGroupMainForm:null,
	getControlGroupMainForm:function(){
		if(Ext.isEmpty(this.controlGroupMainForm)){
    		this.controlGroupMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ControlGroupMainForm');
    	}
    	return this.controlGroupMainForm;
	},
	//车队组信息主界面(调度组信息+checkBox)
	motorcadeGroupMainForm:null,
	getMotorcadeGroupMainForm:function(){
		if(Ext.isEmpty(this.motorcadeGroupMainForm)){
    		this.motorcadeGroupMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeGroupMainForm');
    	}
    	return this.motorcadeGroupMainForm;
	},
	//集中开单组信息主界面(调度组信息+checkBox)
	billingGroupMainForm:null,
	getBillingGroupMainForm:function(){
		if(Ext.isEmpty(this.billingGroupMainForm)){
    		this.billingGroupMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.BillingGroupMainForm');
    	}
    	return this.billingGroupMainForm;
	},
	//保安组信息界面(调度组信息+checkBox)
	securityTfrMotorcadeMainForm:null,
	getSecurityTfrMotorcadeMainForm:function(){
		if(Ext.isEmpty(this.securityTfrMotorcadeMainForm)){
    		this.securityTfrMotorcadeMainForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeMainForm');
    	}
    	return this.securityTfrMotorcadeMainForm;
	},
	
	 //保存行政组织数据
    saveAllOrgInfo:function(button){
    	button.setDisabled(true);
    	var me = this;
    	var orgAdministrativeInfoEntity = me.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		button.setDisabled(false);
    		return;
    	}
    	var params = {'orgAdministrativeInfoVo':
			    		{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity}};
    	/**
    	 * 功能：营业部，外场，空运总调，，车队，事业部，营业小区，营业大区  集中开单组外场都要判断简称是否为空；为空则提示
    	 * 修改者：LIXUEXING
    	 * 修改时间：2013-03-16
    	 * */
		if (( orgAdministrativeInfoEntity.airDispatch == 'Y'|| orgAdministrativeInfoEntity.division == 'Y'
			|| orgAdministrativeInfoEntity.smallRegion == 'Y'|| orgAdministrativeInfoEntity.bigRegion == 'Y')
			&& Ext.isEmpty(orgAdministrativeInfoEntity.orgSimpleName)){
			Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.alertOrgSimpleName'));
    		button.setDisabled(false);
			return;
		}
    	var successFun = function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		var treeStore = me.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.colseFun();//所有的设置为不可编辑
    	};
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByOrg.action');
    	Ext.Ajax.request({
    		url:url,
    		async: false, //设置为同步
    		jsonData:params,
    		success:function(response){
    			var json = Ext.decode(response.responseText);
    			successFun(json);
    		},
    		failure:function(response){
    			var json = Ext.decode(response.responseText);
    			failureFun(json);
    		}
    	});
    },
    //获取行政组织主信息
    getOrgInfo:function(){
    	var me = this;
    	//主信息的form
    	var orgInfoForm = me.getOrgMainInfoForm();
    	//行政业务属性的form
    	var orgBusinessInfoForm=me.getOrgBusinessInfoForm();
    	if(orgInfoForm.getForm().isValid()&&orgBusinessInfoForm.getForm().isValid()){
    		orgInfoForm.getForm().updateRecord(orgInfoForm.orgAdministrativeInfoModel);//加载数据
    		var countryRegion = orgInfoForm.items.items[4].items.items[0].getValue();//国家
    		var provCode =orgInfoForm.items.items[4].items.items[1].getValue();//省
    		var cityCode = orgInfoForm.items.items[4].items.items[2].getValue();//市
    		var countyCode = orgInfoForm.items.items[4].items.items[3].getValue();//区县
    		var countryRegionName = orgInfoForm.items.items[4].items.items[0].getRawValue();//国家
    		var provName =orgInfoForm.items.items[4].items.items[1].getRawValue();//省
    		var cityName = orgInfoForm.items.items[4].items.items[2].getRawValue();//市
    		var countyName = orgInfoForm.items.items[4].items.items[3].getRawValue();//区县
    		orgInfoForm.orgAdministrativeInfoModel.set('countryRegionName',countryRegionName);
    		orgInfoForm.orgAdministrativeInfoModel.set('provName',provName);
    		orgInfoForm.orgAdministrativeInfoModel.set('cityName',cityName);
    		orgInfoForm.orgAdministrativeInfoModel.set('countyName',countyName);
    		orgInfoForm.orgAdministrativeInfoModel.set('countryRegion',countryRegion);
    		orgInfoForm.orgAdministrativeInfoModel.set('provCode',provCode);
    		orgInfoForm.orgAdministrativeInfoModel.set('cityCode',cityCode);
    		orgInfoForm.orgAdministrativeInfoModel.set('countyCode',countyCode);
    		orgBusinessInfoForm.getForm().updateRecord(orgInfoForm.orgAdministrativeInfoModel);//加载行政组织业务属性的数据
    		if(orgInfoForm.orgAdministrativeInfoModel.get('doAirDispatch')=='true'){//空运配载
    			orgInfoForm.orgAdministrativeInfoModel.set('doAirDispatch','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('doAirDispatch')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('doAirDispatch','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('smallRegion')=='true'){//营业小区
    			orgInfoForm.orgAdministrativeInfoModel.set('smallRegion','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('smallRegion')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('smallRegion','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('bigRegion')=='true'){//营业大区
    			orgInfoForm.orgAdministrativeInfoModel.set('bigRegion','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('bigRegion')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('bigRegion','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('division')=='true'){//事业部
    			orgInfoForm.orgAdministrativeInfoModel.set('division','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('division')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('division','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('isEntityFinance')=='true'){//是否实体财务部
    			orgInfoForm.orgAdministrativeInfoModel.set('isEntityFinance','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('isEntityFinance')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('isEntityFinance','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('airDispatch')=='true'){//空运总调
    			orgInfoForm.orgAdministrativeInfoModel.set('airDispatch','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('airDispatch')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('airDispatch','N');
    		}	
    		//新增的快递属性11
    		if(orgInfoForm.orgAdministrativeInfoModel.get('expressBigRegion')=='true'){ //是否快递大区
    			orgInfoForm.orgAdministrativeInfoModel.set('expressBigRegion','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('expressBigRegion')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('expressBigRegion','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('expressSalesDepartment')=='true'){ //是否快递虚拟营业部
    			orgInfoForm.orgAdministrativeInfoModel.set('expressSalesDepartment','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('expressSalesDepartment')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('expressSalesDepartment','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('expressPart')=='true'){ //是否快递点部
    			orgInfoForm.orgAdministrativeInfoModel.set('expressPart','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('expressPart')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('expressPart','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('expressBranch')=='true'){ //是否快递分部  
    			orgInfoForm.orgAdministrativeInfoModel.set('expressBranch','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('expressBranch')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('expressBranch','N');
    	    }
    		if(orgInfoForm.orgAdministrativeInfoModel.get('expressSorting')=='true'){ //是否快递分拣
    			orgInfoForm.orgAdministrativeInfoModel.set('expressSorting','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('expressSorting')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('expressSorting','N');
    		}
    		if(orgInfoForm.orgAdministrativeInfoModel.get('isManageDepartment')=='true'){ //是否经营本部
    			orgInfoForm.orgAdministrativeInfoModel.set('isManageDepartment','Y');
    		}else if(orgInfoForm.orgAdministrativeInfoModel.get('isManageDepartment')=='false'){
    			orgInfoForm.orgAdministrativeInfoModel.set('isManageDepartment','N');
    		}
    		if(me.getOutfieldMainForm().getForm().findField('transferCenter').checked){//外场被选中
    			orgInfoForm.orgAdministrativeInfoModel.set('transferCenter','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('transferCenter','N');
    		}
    		if(me.getSaleDeptMainPanel().getForm().findField('salesDepartment').checked){//营业部
    			orgInfoForm.orgAdministrativeInfoModel.set('salesDepartment','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('salesDepartment','N');
    		}
    		if(me.getMotorcadeMainForm().getForm().findField('transDepartment').checked){//车队
    			orgInfoForm.orgAdministrativeInfoModel.set('transDepartment','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('transDepartment','N');
    		}
    		if(me.getControlGroupMainForm().getForm().findField('dispatchTeam').checked){//车队调度组
    			orgInfoForm.orgAdministrativeInfoModel.set('dispatchTeam','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('dispatchTeam','N');
    		}
    		if(me.getBillingGroupMainForm().getForm().findField('billingGroup').checked){//集中开单组
    			orgInfoForm.orgAdministrativeInfoModel.set('billingGroup','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('billingGroup','N');
    		}
    		if(me.getDeliverScheduleMainForm().getForm().findField('isDeliverSchedule').checked){//排单部门
    			orgInfoForm.orgAdministrativeInfoModel.set('isDeliverSchedule','Y');
    			var deliverScheduleInfoForm = me.getDeliverScheduleMainForm().getDeliverScheduleInfoForm();
    			if(deliverScheduleInfoForm.getForm().isValid()){
    				deliverScheduleInfoForm.getForm().updateRecord(orgInfoForm.orgAdministrativeInfoModel);
    				orgInfoForm.orgAdministrativeInfoModel.set('deliverOutfieldName',deliverScheduleInfoForm.getForm().findField('deliverOutfield').getRawValue());
    			}else{
    				baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillRowSingleDepartments'));//请填写排单部门信息！
    			    return null;
    			}
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('isDeliverSchedule','N');
    		}
    		if(me.getArrangeGoodsMainForm().getForm().findField('isArrangeGoods').checked){//理货部门
    			orgInfoForm.orgAdministrativeInfoModel.set('isArrangeGoods','Y');
    			var arrangeGoodsInfoForm = me.getArrangeGoodsMainForm().getArrangeGoodsInfoForm();
    			if(arrangeGoodsInfoForm.getForm().isValid()){
    				arrangeGoodsInfoForm.getForm().updateRecord(orgInfoForm.orgAdministrativeInfoModel);
    				orgInfoForm.orgAdministrativeInfoModel.set('arrangeOutfieldName',arrangeGoodsInfoForm.getForm().findField('arrangeOutfield').getRawValue());
    			}else{
    				baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillOutTheTallyDepartment'));//请填写理货部门信息！
    			    return null;
    			}
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('isArrangeGoods','N');
    		}
				
			if(me.getSecurityTfrMotorcadeMainForm().getForm().findField('isSecurity').checked){//保安组
    			orgInfoForm.orgAdministrativeInfoModel.set('isSecurity','Y');
    			var securityTfrMotorcadeMainForm = me.getSecurityTfrMotorcadeMainForm().getSecurityTfrMotorcadeInfoForm();
    			if(securityTfrMotorcadeMainForm.getForm().isValid()){
    				securityTfrMotorcadeMainForm.getForm().updateRecord(orgInfoForm.orgAdministrativeInfoModel);
    				orgInfoForm.orgAdministrativeInfoModel.set('tallySectorServicemotorCade',securityTfrMotorcadeMainForm.getForm().findField('tallySectorServicemotorCade').getRawValue());
					orgInfoForm.orgAdministrativeInfoModel.set('tallySectorServiceOutfield',securityTfrMotorcadeMainForm.getForm().findField('tallySectorServiceOutfield').getRawValue());
    			}else{
    				baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillOutTheSecurityTfrMotorcade'));//请填写保安组信息！
    			    return null;
    			}
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('isSecurity','N');
    		}
			
    		if(me.getMotorcadeGroupMainForm().getForm().findField('transTeam').checked){//车队组
    			orgInfoForm.orgAdministrativeInfoModel.set('transTeam','Y');
    		}else{
    			orgInfoForm.orgAdministrativeInfoModel.set('transTeam','N');
    		}
    		return orgInfoForm.orgAdministrativeInfoModel.data;//把自己本身的model修改了，并返回其data，以供传到后台
    	}else{
    		return null;
    	}
    },
    //获取营业部信息
    getSaleDepartmentInfo:function(){
    	var me = this;
    	//营业部
    	var saleDepartmentInfoPanel = me.getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	var saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();//上部form
    	var deliveryInformationForm = saleDepartmentInfoPanel.getDeliveryInformationForm();//提货form
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//出发信息
    	var saleDepartmentBottomPanel =saleDepartmentInfoPanel.getSaleDepartmentBottomPanel(); //到达信息
    	if(saleDepartmentPartForm.getForm().isValid()&&deliveryInformationForm.getForm().isValid()
    			&& saleDepartmentCerterPanel.getForm().isValid() && saleDepartmentBottomPanel.getForm().isValid()){
    		if(Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel)){
    			saleDepartmentInfoPanel.saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity();
    		}
    		saleDepartmentPartForm.getForm().updateRecord(saleDepartmentInfoPanel.saleDepartmentModel);//上部加载数据
    		saleDepartmentInfoPanel.saleDepartmentModel.set('code',me.getOrgMainInfoForm().orgAdministrativeInfoModel.get('code'));//设置行政组织code
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('satelliteDept')=='true'){//是否卫星点部
    			saleDepartmentInfoPanel.saleDepartmentModel.set('satelliteDept','Y');
    		}else{
    			saleDepartmentInfoPanel.saleDepartmentModel.set('satelliteDept','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('isLeagueSaleDept')=='true'){//是否加盟网点
    			saleDepartmentInfoPanel.saleDepartmentModel.set('isLeagueSaleDept','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('isLeagueSaleDept')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('isLeagueSaleDept','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('isTwoLevelNetwork')=='true'){//是否二级网点-308861
    	    	saleDepartmentInfoPanel.saleDepartmentModel.set('isTwoLevelNetwork','Y');
    	    }else if(saleDepartmentInfoPanel.saleDepartmentModel.get('isTwoLevelNetwork')=='false'){
    	    	saleDepartmentInfoPanel.saleDepartmentModel.set('isTwoLevelNetwork','N');
    	    }
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('station')=='true'){//是否驻地部门
    			saleDepartmentInfoPanel.saleDepartmentModel.set('station','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('station')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('station','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('inCentralizedShuttle')=='true'){// 是否在集中接送货范围内
    			saleDepartmentInfoPanel.saleDepartmentModel.set('inCentralizedShuttle','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('inCentralizedShuttle')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('inCentralizedShuttle','N');
    		}
    		saleDepartmentCerterPanel.getForm().updateRecord(saleDepartmentInfoPanel.saleDepartmentModel);//出发加载数据
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('leave')=='true'){//可出发
    			saleDepartmentInfoPanel.saleDepartmentModel.set('leave','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('leave')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('leave','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canPayServiceFee')=='true'){//是否可开装卸费
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canPayServiceFee','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canPayServiceFee')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canPayServiceFee','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressOneMany')=='true'){//是否可开快递一票多件
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressOneMany','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressOneMany')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressOneMany','N');
    		}
    		//快递新增属性-17
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressPickupToDoor')=='true'){ //可快递接货
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressPickupToDoor','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressPickupToDoor')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressPickupToDoor','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canUpdateDestination')=='true'){ //补录不可修改快递目的站
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canUpdateDestination','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canUpdateDestination')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canUpdateDestination','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressDoorToDoor')=='true'){ //是否快递可上门发货
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressDoorToDoor','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressDoorToDoor')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressDoorToDoor','N');
    		}
    		saleDepartmentBottomPanel.getForm().updateRecord(saleDepartmentInfoPanel.saleDepartmentModel);//到达数据加载
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('arrive')=='true'){//可到达
    			saleDepartmentInfoPanel.saleDepartmentModel.set('arrive','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('arrive')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('arrive','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canReturnSignBill')=='true'){//是否可返回签单
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canReturnSignBill','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canReturnSignBill')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canReturnSignBill','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canCashOnDelivery')=='true'){//是否可货到付款
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canCashOnDelivery','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canCashOnDelivery')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canCashOnDelivery','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canAgentCollected')=='true'){//是否可代收货款
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canAgentCollected','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canAgentCollected')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canAgentCollected','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canArriveExpressOneMany')=='true'){//是否可到快递一票多件
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canArriveExpressOneMany','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canArriveExpressOneMany')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canArriveExpressOneMany','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canCashOnDeliveryMany')=='true'){//是否可货到付款（多件）
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canCashOnDeliveryMany','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canCashOnDeliveryMany')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canCashOnDeliveryMany','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canAgentCollectedMany')=='true'){//是否可到代收货款（多件）
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canAgentCollectedMany','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canAgentCollectedMany')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canAgentCollectedMany','N');
    		}
    		//新增的快递属性-9
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressReturnSignBill')=='true'){ //可快递返回签单
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressReturnSignBill','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressReturnSignBill')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressReturnSignBill','N');
    		}
    		deliveryInformationForm.getForm().updateRecord(saleDepartmentInfoPanel.saleDepartmentModel);//自提数据加载
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('pickupSelf')=='true'){//可自提
    			saleDepartmentInfoPanel.saleDepartmentModel.set('pickupSelf','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('pickupSelf')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('pickupSelf','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('delivery')=='true'){//可派送
    			saleDepartmentInfoPanel.saleDepartmentModel.set('delivery','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('delivery')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('delivery','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('airArrive')=='true'){//可空运到达
    			saleDepartmentInfoPanel.saleDepartmentModel.set('airArrive','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('airArrive')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('airArrive','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('truckArrive')=='true'){//可汽运到达
    			saleDepartmentInfoPanel.saleDepartmentModel.set('truckArrive','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('truckArrive')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('truckArrive','N');
    		}
    		//新增的快递属性-10
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressDelivery')=='true'){//可快递派送
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressDelivery','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressDelivery')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressDelivery','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressPickupSelf')=='true'){//可快递自提
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressPickupSelf','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canExpressPickupSelf')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canExpressPickupSelf','N');
    		}
    		if(saleDepartmentInfoPanel.saleDepartmentModel.get('canHomeImproSend')=='true'){//可家装送装
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canHomeImproSend','Y');
    		}else if(saleDepartmentInfoPanel.saleDepartmentModel.get('canHomeImproSend')=='false'){
    			saleDepartmentInfoPanel.saleDepartmentModel.set('canHomeImproSend','N');
    		}
    		return saleDepartmentInfoPanel.saleDepartmentModel.data;//把自己本身的model修改了，并返回其data，以供传到后台
    	}else{
    		return null;
    	}
    },
    //获取营业部产品信息
    getSalesProductEntityList:function(){
    	var me = this;
    	//营业部
    	var saleDepartmentInfoPanel = me.getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//出发信息
    	var saleDepartmentBottomPanel =saleDepartmentInfoPanel.getSaleDepartmentBottomPanel(); //到达信息
    	var salesProductEntityList = new Array();
    	var salesDeptCode = me.getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
    	saleDepartmentCerterPanel.getStartingProductsSelected().getStore().each(function(record){//出发已分配
    		var salesType = baseinfo.orgAdministrativeInfo.ORG_DEPARTURE;
    		var productCode = record.get('code');
    		var data = {'salesDeptCode':salesDeptCode,'productCode':productCode,'salesType':salesType};
    		salesProductEntityList.push(data);
    	});
    	saleDepartmentBottomPanel.getArriveProductsSelected().getStore().each(function(record){//到达已分配
    		var salesType = baseinfo.orgAdministrativeInfo.ORG_ARRIVE;
    		var productCode = record.get('code');
    		var data = {'salesDeptCode':salesDeptCode,'productCode':productCode,'salesType':salesType};
    		salesProductEntityList.push(data);
    	});
    	return salesProductEntityList;
    },
    //关闭所有的可编辑的，使其不可编辑
    colseFun:function(){
    	var me = this;
    	//行政组织主信息
    	var orgInfoForm = me.getOrgMainInfoForm();
    	orgInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
			item.setReadOnly(true);
    	});
    	orgInfoForm.items.items[4].getNation().setReadOnly(true);
    	orgInfoForm.items.items[4].getProvince().setReadOnly(true);
    	orgInfoForm.items.items[4].getCity().setReadOnly(true);
    	orgInfoForm.items.items[4].getCounty().setReadOnly(true);
    	orgInfoForm.items.items[4].getNation().setDisabled(true);
    	orgInfoForm.items.items[4].getProvince().setDisabled(true);
    	orgInfoForm.items.items[4].getCity().setDisabled(true);
    	orgInfoForm.items.items[4].getCounty().setDisabled(true);
    	//行政业务属性
    	me.getOrgBusinessInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		item.setReadOnly(true);
    	});
    	/**
    	 * 外场主界面信息（outfieldInfoForm+checkbox）
    	 */
    	var outFieldMainForm = me.getOutfieldMainForm();
    	outFieldMainForm.getForm().findField('transferCenter').setReadOnly(true);//设置‘是否外场’不可编辑
    	//判断是否外场被勾选上之后，就是form被show 之后 才给其设置
    	if(outFieldMainForm.getForm().findField('transferCenter').getValue()){
    		outFieldMainForm.getOutfieldInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    				if(item.readOnly==false){
    					item.setReadOnly(true);
    				}
        	});
    	}
       	/**
       	 * 车队信息主界面
       	 */
    	var motorcadeMainForm = me.getMotorcadeMainForm();
    	motorcadeMainForm.getForm().findField('transDepartment').setReadOnly(true); //’是否车队‘ 不可编辑
    	var  motorcadeInfoForm =motorcadeMainForm.getMotorcadeInfoForm();
    	//判断是否车队被勾选上之后，就是form被show 之后 才给其设置
    	if(motorcadeMainForm.getForm().findField('transDepartment').getValue()){
    		motorcadeInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
					item.setReadOnly(true);
				}
        	});
    		motorcadeInfoForm.getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(true);
        	motorcadeInfoForm.getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(true);//按钮设置不可用
        	motorcadeInfoForm.getMotorcadeServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(true);//所属营业部按钮不可用
    	}
    	/**
    	 * 车队组信息主界面
    	 */
    	var motorcadeGroupMainForm = me.getMotorcadeGroupMainForm();
    	motorcadeGroupMainForm.getForm().findField('transTeam').setReadOnly(true);//’是否车队组‘不可编辑
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupMainForm().getMotorcadeGroupInfoForm();
    	//判断是否车队组被勾选上之后，就是form被show 之后 才给其设置不可编辑
    	if(motorcadeGroupMainForm.getForm().findField('transTeam').getValue()){
    		motorcadeGroupInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly == false){
					item.setReadOnly(true);
				}
        	});
        	motorcadeGroupInfoForm.getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(true);
        	motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(true);//按钮设置不可用
    	}
    	/**
    	 * 派送排单部门主界面
    	 */
    	var deliverScheduleMainForm = me.getDeliverScheduleMainForm();
    	deliverScheduleMainForm.getForm().findField('isDeliverSchedule').setReadOnly(true);//设置‘是否派送排单’不可编辑
    	//判断是否派送派单被勾选上之后，就是form被show 之后 才给其设置不可编辑
    	if(deliverScheduleMainForm.getForm().findField('isDeliverSchedule').getValue()){
    		deliverScheduleMainForm.getDeliverScheduleInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
					item.setReadOnly(true);
				}
        	});
    	}
    	/**
    	 * 理货部门
    	 */
    	var arrangeGoodsMainForm = me.getArrangeGoodsMainForm();
    	arrangeGoodsMainForm.getForm().findField('isArrangeGoods').setReadOnly(true);//设置‘是否理货’不可编辑
    	var arrangeGoodsInfoForm =arrangeGoodsMainForm.getArrangeGoodsInfoForm();//理货部门信息
    	//判断是否派送派单被勾选上之后，就是form被show 之后 才给其设置不可编辑
    	if(arrangeGoodsMainForm.getForm().findField('isArrangeGoods').getValue()){
    		arrangeGoodsInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
    				item.setReadOnly(true);
    			}
        	});
    	}
		/**
    	 * 保安部部门me.getSecurityTfrMotorcadeMainForm().getForm().findField('isSecurity').checked
    	 */
    	var securityTfrMotorcadeMainForm = me.getSecurityTfrMotorcadeMainForm();
    	securityTfrMotorcadeMainForm.getForm().findField('isSecurity').setReadOnly(true);//设置‘是否理货’不可编辑
    	var securityTfrMotorcadeInfoForm =securityTfrMotorcadeMainForm.getSecurityTfrMotorcadeInfoForm();//理货部门信息
    	//判断是否派送派单被勾选上之后，就是form被show 之后 才给其设置不可编辑
    	if(securityTfrMotorcadeMainForm.getForm().findField('isSecurity').getValue()){
    		securityTfrMotorcadeInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
    				item.setReadOnly(true);
    			}
        	});
    	}
    	/**
    	 * 车队调度组主界面
    	 */
    	var controlGroupMainForm = me.getControlGroupMainForm();
    	controlGroupMainForm.getForm().findField('dispatchTeam').setReadOnly(true); //’是否调度组‘可编辑
    	//车队调度组
    	var controlGroupInfoForm =controlGroupMainForm.getControlGroupInfoForm();
    	//只有form  存在 才给其设置为不可编辑的
    	if(controlGroupMainForm.getForm().findField('dispatchTeam').getValue()){
    		controlGroupInfoForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
    				item.setReadOnly(true);
    			}
        	});
    	}
    	/**
    	 * 集中开单组外场
    	 */
    	var billingGroupMainForm =me.getBillingGroupMainForm();
    	billingGroupMainForm.getForm().findField('billingGroup').setReadOnly(true);
    	var billingGroupTransFerForm =billingGroupMainForm.getBillingGroupNameInfoForm();
    	if(billingGroupMainForm.getForm().findField('billingGroup').getValue()){
    		billingGroupTransFerForm.getForm().getFields( ).each(function(item){//设置不可编辑
    			if(item.readOnly==false){
    				item.setReadOnly(true);
    			}
        	});
        	billingGroupTransFerForm.getBillingGroupServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(true);//所属营业部按钮不可用
    	}
    	/**
    	 * 营业部
    	 */
    	me.getSaleDeptMainPanel().getForm().findField('salesDepartment').setReadOnly(true); //‘是否营业部’设为不可编辑
    	var saleDepartmentInfoPanel = me.getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	var saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();//上部form
    	var deliveryInformationForm = saleDepartmentInfoPanel.getDeliveryInformationForm();//提货form
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//中部的grid组(出发)
    	var saleDepartmentBottomPanel =saleDepartmentInfoPanel.getSaleDepartmentBottomPanel();//下部的grid组(到达)
    	//防止dom为空
    	if(me.getSaleDeptMainPanel().getForm().findField('salesDepartment').getValue()){
        	saleDepartmentPartForm.getForm().getFields( ).each(function(item){//设置不可编辑
        		if(item.readOnly == false){
        			item.setReadOnly(true);
        		}
        	});
        	saleDepartmentCerterPanel.getForm().getFields( ).each(function(item){//设置不可编辑
        		if(item.readOnly == false){
        			item.setReadOnly(true);
        		}
        	});
        	saleDepartmentBottomPanel.getForm().getFields( ).each(function(item){//设置不可编辑
        		if(item.readOnly == false){
        			item.setReadOnly(true);
        		}
        	});
        	//当可达到是选择上的时候，才可以设置提货不可编辑
        	if(saleDepartmentBottomPanel.getForm().findField('arrive').getValue()){
        		if(!Ext.isEmpty(deliveryInformationForm)){
        			deliveryInformationForm.getForm().getFields( ).each(function(item){//提货的设置不可编辑
            			if(item.readOnly==false){
            				item.setReadOnly(true);
            			}
            		});
        		}
        	}
        	var startingButtonPanel = saleDepartmentCerterPanel.getStartingButtonPanel();//出发使用产品按钮Panel
        	var arriveButtonPanel = saleDepartmentBottomPanel.getArriveButtonPanel();//到达适用产品按钮Panel
        	//出发使用产品按钮设置不可用
        	for(var i=0;i<startingButtonPanel.items.items.length;i++){
        		startingButtonPanel.items.items[i].setDisabled(true);
        	}
        	//到达适用产品按钮设置不可用
        	for(var i=0;i<arriveButtonPanel.items.items.length;i++){
        		arriveButtonPanel.items.items[i].setDisabled(true);
        	}
    	}
    },
	constructor : function(config) {
			var me = this, 
				cfg = Ext.apply({}, config);
			me.items = [me.getOrgAuxiliaryInfoForm(),
			            me.getPublicBankAccountForm(),
			            me.getOrgMainInfoForm(),
			            me.getOrgBusinessInfoForm(),	//行政组织业务属性
			            me.getSaleDeptMainPanel(),		//营业部主界面
			            me.getOutfieldMainForm(),		//外场主界面
			            me.getMotorcadeMainForm(),		//车队主界面
			            me.getControlGroupMainForm(),	//车队调度组主界面
			            me.getMotorcadeGroupMainForm(), //车队组主界面
			            me.getDeliverScheduleMainForm(),//派送派单主界面
			            me.getArrangeGoodsMainForm(),	//理货部门主界面
			            me.getBillingGroupMainForm(),	//集中开单组
			            me.getSecurityTfrMotorcadeMainForm() //保安组
			            ];
			me.callParent([cfg]);
	}
});
/**
 * @description 电子地图窗口
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
	extend:'Ext.window.Window',
	closeAction:'hide',
	width:750,
	collBackFun:function(){},//回调函数
	parent:null,//Foss.baseinfo.orgAdministrativeInfo.OrgMainInfoForm
	 constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype: 'container',
			height:750
		}];
		me.callParent([cfg]);
	},
	height:750
});
/**
 * @description 行政组织主信息查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgMainInfoForm', {
	extend:'Ext.form.Panel',  
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.viewModifyDetails'),//查看/修改详情
    height:475,
    frame:true,
    margin : '5 5 5 5',
    collapsible :true,
    animCollapse : true,
    orgAdministrativeInfoModel:null,//加载的数据Model
    outfieldModel:null,//外场相关信息
    motorcadeModel:null,//车队相关信息
    billingGroupTransFerModel:null,//集中开单组外场相关信息
    saleDepartmentModel:null,//营业部
    securityTfrMotorcadeInfoModel:null,//保安组
    salesMotorcadeEntityList:[],//营业部车队信息
    salesProductEntityList:[],//营业部产品信息
    centralizedBillingGroupList:[],//营业部全部集中开单组信息
    selectedCentralizedBillingGroupList:[],//营业部全部集中开单组信息
    motorcadeServeSalesAreaEntityList:[],//车队/车队组负责营业区
    motorcadeServeDistrictEntityList:[],//车队/车队组负责行政区
    securityTfrMotorcadeEntityList:[],//保安组
    gisMapType:{EXP:'EXP',LD:'LD'},//gis地图类型，
    layout:{
		type:'table',
		columns: 6
	},
    defaults : {
    	colspan : 1,
    	readOnly:true
    },
    //创建一个设置部门简称字段的公共方法
    setOrgSimpleNameField:function(orgMainInfoForm){
    	var orgSimpleName_field_Container = orgMainInfoForm.query('container')[2] ;
		var orgSimpleName_field = orgMainInfoForm.getForm().findField('orgSimpleName');
		var orgSimpleName_v = orgSimpleName_field.getValue();
		if(!Ext.isEmpty(orgSimpleName_field)){
			orgSimpleName_field_Container.remove(orgSimpleName_field);
		}
		orgSimpleName_field_Container.add(Ext.create('Ext.form.field.Text',{
			name: 'orgSimpleName',
			allowBlank:false,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentReferred'),//部门简称9
			labelWidth:100,
			readOnly:true,
			maxLength:16,value:orgSimpleName_v
		}));
    },
    //创建一个获取mapLocation的方法
    mapLoca:null,
    getMapLoca:function(){
    	var me =this;
    	if(!Ext.isEmpty(me.items.items[4])){
			var cityLocation = me.items.items[4].city.rawValue;
			var countryLocation = me.items.items[4].county.rawValue;
			if(!Ext.isEmpty(cityLocation)&&!Ext.isEmpty(cityLocation)){
				mapLoca = cityLocation + countryLocation;
			}else if(Ext.isEmpty(cityLocation)){
				mapLoca = baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.shanghaishi');
			}
    	}
    	return mapLoca;
    },
    //电子地图弹窗编辑
    mainGisWindow:null,
    getMainGisWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.mainGisWindow)){
    		this.mainGisWindow = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			'collBackFun':function(data){
    				if(data.flag=='DELETE'){
    					me.orgAdministrativeInfoModel.set('depCoordinate','');
        				me.getForm().findField('depCoordinate').setValue('');
    				}else if(data.flag=='ADD'||data.flag =='UPDATE'){
    					me.orgAdministrativeInfoModel.set('depCoordinate',data.code);
        				me.getForm().findField('depCoordinate').setValue(data.code);
        				me.mainGisWindow.close();
    				}
    				me.saveOrgAndSalesInfo(Ext.getCmp('Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id'));//优化：dujunhui-187862
    			},
    			listeners:{
    				beforeshow:function(window){	
    					Ext.defer(function(){
    					//获取省市区地址
    					var mapLocation =me.getMapLoca();
    					new DpMap(window.items.items[0].getId(),{center : mapLocation, zoom : 13 },function(map) { 
    			   		     var fun = function(data){
    			   		    	window.collBackFun(data);
    			   		    	if(data.flag=='DELETE'){
    			   		    		pointfeature.openEditTool();
    			   		    	}
    			   			 }
    			   		     var callFun =function(data){
    			   		    	 if(data.flag =='QUERY'){
    			   		    		pointfeature.closeEditTool();
    			   		    	 }
    			   		     }
    			   		 var pointfeature = new DpMap.service.DpMap_marker(map,window.items.items[0].getId(),{isAddable:true,callBackFun:fun,closeToolCallback:callFun}); 
    			   		 pointfeature.showModifiablePointById([Ext.getCmp('depCoordinate_id').getValue()]);
    			   		 /*//隐藏描点按钮
    			   		 if(!Ext.isEmpty(Ext.getCmp('depCoordinate_id').getValue())){
    			   			pointfeature.closeEditTool();
    			   		 }*/
    		           });
    					}, 700, this);
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
    	return this.mainGisWindow;
    },
    //电子地图弹窗查看
    mainGisShowWindow:null,
    getMainGisShowWindow:function(){
    	var me = this;
    	if(Ext.isEmpty(this.mainGisShowWindow)){
    		this.mainGisShowWindow = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			listeners:{
    				beforeshow:function(window){	
    					Ext.defer(function(){
    					var mapLocation =me.getMapLoca();
    					new DPMap.MyMap('VIEW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
    						var locateFeature =  DMap.LocateFeature(map);
						 var pointF = DMap.PointFeature(map,{isAddable:true,manipulable:1});
						 var id = Ext.getCmp('depCoordinate_id').getValue();
						 if(!Ext.isEmpty(id)){
						 	pointF.showNon_modifiablePointById(id);}
    					  });
    				}, 700, this);},
    			   beforehide:function(window){
    				   window.removeAll();
    				   window.add(Ext.create('Ext.container.Container',{
    					   height:500
    				   }));
    			   }
    			}
    		});
    	}
    	return this.mainGisShowWindow;
    },
  //派送自提区域电子地图弹窗编辑
    deliveryMainGisWindow:null,
    getDeliveryMainGisWindow:function(){
    	var me = this;
    	//营业部信息panel
    	var saleDepartmentInfoPanel	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	if(Ext.isEmpty(this.deliveryMainGisWindow)){
    		this.deliveryMainGisWindow = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			'collBackFun':function(data){
    				if(data.type=='DELETE'){
    					saleDepartmentInfoPanel.saleDepartmentModel.set('deliveryCoordinate','');
    					saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().findField('deliveryCoordinate').setValue('');
    				}else if(data.type=='ADD'||data.type=='UPDATE'){
    					saleDepartmentInfoPanel.saleDepartmentModel.set('deliveryCoordinate',data.code);
    					saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().findField('deliveryCoordinate').setValue(data.code);
        				me.deliveryMainGisWindow.close();
    				}
    			},
    			listeners:{
    				beforeshow:function(window){	
    					Ext.defer(function(){
    					var mapLocation =me.getMapLoca();
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
    						  }
    						 //实例化一个新类			
    						 var ployfeature = new DpMap.service.DpMap_polygon(map, window.items.items[0].getId(),{isAddable:true, callBackFun:fun, foregroundType:'SALES_DEPT', backgroundType:'CITY',closeToolCallback:callFun});
    						if(!Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel)){
    							//调用根据id展示范围方法
    							ployfeature.showModifiablePolygons([saleDepartmentInfoPanel.saleDepartmentModel.get('deliveryCoordinate')]);
    							//隐藏描点按钮
    							/*if(!Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel.get('deliveryCoordinate'))){
    								ployfeature.closeEditTool();
    							}*/
    						}
    						
    					});	
    			   }, 700, this);},
    			   beforehide:function(window){
    				   window.removeAll();
    				   window.add(Ext.create('Ext.container.Container',{
    					   height:500
    				   }));
    			   }
    			}
    		});
    	}
    	return this.deliveryMainGisWindow;
    },
  //派送自提电子地图弹窗查看
    deliveryMainGisShowWindow:null,
    getDeliveryMainGisShowWindow:function(){
    	var me = this;
    	var saleDepartmentInfoPanel	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	if(Ext.isEmpty(this.deliveryMainGisShowWindow)){
    		this.deliveryMainGisShowWindow = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			listeners:{
    				beforeshow:function(window){	
    					Ext.defer(function(){
    					var mapLocation =me.getMapLoca();
    					new DPMap.MyMap('VIEW', window.items.items[0].getId(),{center : mapLocation, zoom : "STREET" },function(map) { 
    						var locateFeature =  DMap.LocateFeature(map);
    			   		     var ployfeature = DMap.PolygonFeature(map,{isAddable:true, callBackFun:function(){}, foregroundType:'SALES_DEPT', backgroundType:'CITY'}); 
    			   			 if(!Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel)){
    			   				 //调用根据id展示范围方法 
    			   				ployfeature.showModifiyAblePolygons([saleDepartmentInfoPanel.saleDepartmentModel.get('deliveryCoordinate')]);
    			   			 }
    		           });
    			   }, 700, this);
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
    	return this.deliveryMainGisShowWindow;
    },
    //快递虚拟营业部的派送自提电子地图编辑
    expressDeliveryGisEditwindow:null,
    getExpressDeliveryGisEditWidow:function(){
    	var me=this,
    	saleDepartmentInfoPanel	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	if(Ext.isEmpty(this.expressDeliveryGisEditwindow)){
    		this.expressDeliveryGisEditwindow =Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			'collBackFun':function(data){
    				if(data.type=='DELETE'){
    					saleDepartmentInfoPanel.saleDepartmentModel.set('expressDeliveryCoordinate','');
    					saleDepartmentInfoPanel.saleDepartmentModel.set('departServiceArea',null);
    					saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().findField('expressDeliveryCoordinate').setValue('');
                        //保存再关闭
    					me.saveSalesInfoByGisMap(me.gisMapType.EXP);
    				}else if(data.type=='ADD'||data.type=='UPDATE'){
    					saleDepartmentInfoPanel.saleDepartmentModel.set('expressDeliveryCoordinate',data.code);
    					saleDepartmentInfoPanel.saleDepartmentModel.set('departServiceArea',data.area);
    					saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().findField('expressDeliveryCoordinate').setValue(data.code);
    					//新增或修改完一个地图，去数据库中保存一条数据
    					me.saveSalesInfoByGisMap(me.gisMapType.EXP);
    					me.expressDeliveryGisEditwindow.close();
    				}
    			},
    			listeners:{
    				beforeshow:function(window){
    					Ext.defer(function(){
        					var mapLocation =me.getMapLoca();
        					new DpMap(window.items.items[0].getId(), {center :mapLocation,zoom : 13}, function (map) { 
      						  var fun = function(data){
      							window.collBackFun(data);
      							if(data.type =='DELETE'){
    								ployfeature.openEditTool();
    							}
      						  }
      						  var callFun =function(data){
      							  if(data.type=='QUERY'){
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
   							 			saveType:'2',showLableType:true ,closeToolCallback:callFun,callBackFun: fun});
      						if(!Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel)){
      							//封装快递员人数和面积
      							function  message(name,content){
      								this.name = name ;
      								this.content = content ;
      							   }
      						    var m1 = new message("快递员人数",saleDepartmentInfoPanel.saleDepartmentModel.get('expressManNum'));
      							var m2 = new message("范围面积",saleDepartmentInfoPanel.saleDepartmentModel.get('departServiceArea'));
      							var oterMessage = [] ;
      							oterMessage.push(m1);
      						    oterMessage.push(m2);
      							//调用根据id展示范围方法
      							ployfeature.showModifiablePolygonAndOtherMessage(saleDepartmentInfoPanel.saleDepartmentModel.get('expressDeliveryCoordinate'),oterMessage);
      							//隐藏描点按钮
    							/*if(!Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel.get('expressDeliveryCoordinate'))){
    								ployfeature.closeEditTool();
    							}*/
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
    	return this.expressDeliveryGisEditwindow;
    },
    //查看快递派送区域电子地图
    expressDeliveryGisShowWindow:null,
    getExpressDeliveryGisShowWindow:function(){
    	var me = this ,
    	saleDepartmentInfoPanel	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
    	if(Ext.isEmpty(this.expressDeliveryGisShowWindow)){
    		this.expressDeliveryGisShowWindow =Ext.create('Foss.baseinfo.orgAdministrativeInfo.MainGisWindow',{
    			'parent':this,
    			listeners:{
    				beforeshow:function(window){
    					Ext.defer(function(){
	    					var mapLocation =me.getMapLoca();
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
			        	 saveType:'2',showLableType:true ,closeToolCallback: callFun,callBackFun: backFun});
			         
			         	//封装快递员人数和面积
						function  message(name,content){
							this.name = name ;
							this.content = content ;
						}
					    var m1 = new message("快递员人数",saleDepartmentInfoPanel.saleDepartmentModel.get('expressManNum'));
						var m2 = new message("范围面积",saleDepartmentInfoPanel.saleDepartmentModel.get('departServiceArea'));
						var oterMessage = [] ;
						oterMessage.push(m1);
					    oterMessage.push(m2);
					    ployfeature.showUnModifiablePolygonAndOtherMessage(saleDepartmentInfoPanel.saleDepartmentModel.get('expressDeliveryCoordinate'),oterMessage);
				  });	
    					}, 700, this);
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
    	return this.expressDeliveryGisShowWindow;
    },
    //通过修改GIS地图保存营业部信息   
    saveSalesInfoByGisMap:function(gisType){
    	var me =this;
    	var gisMapWin =null;
    	//若地图类型是快递的,获取快递地图窗口
    	if(gisType ==me.gisMapType.EXP){
    		gisMapWin =me.expressDeliveryGisEditwindow;
    	}else if(gisType ==me.gisMapType.LD){
    		gisMapWin =me.deliveryMainGisWindow;
    	}
    	//获取营业部信息
    	var saleDepartmentEntity =me.up('panel').getSaleDepartmentInfo();
    	if(Ext.isEmpty(saleDepartmentEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteBusinessDepartment'));//请填写完整营业部信息
			return;
    	}
    	//--------------------只做校验---------------------
    	if(saleDepartmentEntity.singleBillLimitkg>0){
    		saleDepartmentEntity.singleBillLimitkg=saleDepartmentEntity.singleBillLimitkg*100;
    	}
    	if(saleDepartmentEntity.singleBillLimitvol>0){
    		saleDepartmentEntity.singleBillLimitvol=saleDepartmentEntity.singleBillLimitvol*100;
    	}
		if(saleDepartmentEntity.singlePieceLimitkg>0){
    		saleDepartmentEntity.singlePieceLimitkg=saleDepartmentEntity.singlePieceLimitkg*100;
    	}
    	if(saleDepartmentEntity.singlePieceLimitvol>0){
    		saleDepartmentEntity.singlePieceLimitvol=saleDepartmentEntity.singlePieceLimitvol*100;
    	}
    	var params ={'orgAdministrativeInfoVo':{'saleDepartmentEntity':saleDepartmentEntity}};
    	//加上罩
    	var myMask = new Ext.LoadMask(gisMapWin,{msg:'正在修改保存地图，请稍等...'});
    	myMask.show();
    	Ext.Ajax.request({
    		url:baseinfo.realPath('updateSalesOrgByGisMap.action'),
    		async: false, //设置为同步
    		jsonData:params,
    		success:function(response){
    			var json = Ext.decode(response.responseText);
    			if(!Ext.isEmpty(json.orgAdministrativeInfoVo.saleDepartmentEntity)){//首先先确保返回值了(营业部)
        			//若返回值不为空
        			var saleDepartmentModel =  new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity(json.orgAdministrativeInfoVo.saleDepartmentEntity);//创建营业部的model
    		    	me.saleDepartmentModel = saleDepartmentModel;
    			}
    			//关闭罩
    			myMask.hide();
    			baseinfo.showInfoMes(json.message);
    			//关闭窗口
    			gisMapWin.close();
    		},
    		failure:function(response){
    			var json = Ext.decode(response.responseText);
    			//关闭罩
    			myMask.hide();
    			baseinfo.showInfoMes(json.message);
    		}
    	});
    },
	//保存行政组织数据
    saveOrgAndSalesInfo:function(button){
    	button.setDisabled(true);
    	var me = this;
    	var orgAdministrativeInfoEntity = me.up('panel').getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		button.setDisabled(false);
    		return;
    	}
    	var saleDepartmentEntity = null;
    	if(orgAdministrativeInfoEntity.salesDepartment=='Y'){
    		saleDepartmentEntity = me.up('panel').getSaleDepartmentInfo();
    		if(Ext.isEmpty(saleDepartmentEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteBusinessDepartment'));//请填写完整营业部信息
    			button.setDisabled(false);
    			return;
    		}
    		//---------------只做校验-----------------------------------------------------------------------------------------------------------
        	if(saleDepartmentEntity.singleBillLimitkg>0){
        		saleDepartmentEntity.singleBillLimitkg=saleDepartmentEntity.singleBillLimitkg*100;
        	}
        	if(saleDepartmentEntity.singleBillLimitvol>0){
        		saleDepartmentEntity.singleBillLimitvol=saleDepartmentEntity.singleBillLimitvol*100;
        	}
    		if(saleDepartmentEntity.singlePieceLimitkg>0){
        		saleDepartmentEntity.singlePieceLimitkg=saleDepartmentEntity.singlePieceLimitkg*100;
        	}
        	if(saleDepartmentEntity.singlePieceLimitvol>0){
        		saleDepartmentEntity.singlePieceLimitvol=saleDepartmentEntity.singlePieceLimitvol*100;
        	}
    	}
    	var params = {'orgAdministrativeInfoVo':
			    		{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity,'saleDepartmentEntity':saleDepartmentEntity}};
		//选择可派送的时候，必须编辑电子地图以给坐标编号赋值		
		if(!Ext.isEmpty(saleDepartmentEntity)&&(saleDepartmentEntity.delivery == 'Y') && Ext.isEmpty(saleDepartmentEntity.deliveryCoordinate)){
			Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canBeDeliveredAndDeliveryCoordinate'));
			button.setDisabled(false);
			return;
		}
    	var successFun = function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.saleDepartmentEntity)){//首先先确保返回值了(营业部)
    			//若返回值不为空
    			var saleDepartmentModel =  new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity(json.orgAdministrativeInfoVo.saleDepartmentEntity);//创建营业部的model
		    	me.saleDepartmentModel = saleDepartmentModel;
			}
    		var treeStore = me.up('panel').up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.up('panel').colseFun();//所有的设置为不可编辑
    	};
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByOrgAndSales.action');
    	Ext.Ajax.request({
    		url:url,
    		async: false, //设置为同步
    		jsonData:params,
    		success:function(response){
    			var json = Ext.decode(response.responseText);
    			successFun(json);
    		},
    		failure:function(response){
    			var json = Ext.decode(response.responseText);
    			failureFun(json);
    		}
    	});
    },
    //设置为不可编辑的
    closeFun:function(){
    	var me =this;
    	/*if(Ext.isEmpty(me.getDockedItems()[1])){
    		me.getDockedItems()[1].items.items[0].setDisabled(true);//重置不可用
    	}else{
    		me.getDockedItems()[1].items.items[0].setDisabled(true);//重置不可用
    	};*/
    	me.getForm().getFields( ).each(function(item){//设置不可编辑
			item.setReadOnly(true);
    	});
    	me.items.items[4].getNation().setReadOnly(true);
    	me.items.items[4].getProvince().setReadOnly(true);
    	me.items.items[4].getCity().setReadOnly(true);
    	me.items.items[4].getCounty().setReadOnly(true);
    	me.items.items[4].getNation().setDisabled(true);
    	me.items.items[4].getProvince().setDisabled(true);
    	me.items.items[4].getCity().setDisabled(true);
    	me.items.items[4].getCounty().setDisabled(true);
    
    },
  //点击行政组织信息修改，
    updateFun:function(){
    	var me = this;
    	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
    		return;
    	};
    	me.getForm().getFields( ).each(function(item){//设置可编辑
    		var fieldName = item.getName();
    		if(fieldName=='code'||fieldName=='name'||fieldName=='unifiedCode'||fieldName=='depCoordinate'||fieldName=='address'||fieldName=='depTelephone'||fieldName=='depFax'){
    			//除了部门名称，编码，标杆编码,坐标编码其它都可以编辑
    		}else{
    			item.setReadOnly(false);
    		}
    	});
        me.items.items[4].getNation().setDisabled(false);//国家
        me.items.items[4].getNation().setReadOnly(false);//国家
    	me.items.items[4].getProvince().setReadOnly(false);
    	me.items.items[4].getProvince().setDisabled(false);
    	me.items.items[4].getCity().setReadOnly(false);
    	me.items.items[4].getCity().setDisabled(false);
    	me.items.items[4].getCounty().setReadOnly(false);
    	me.items.items[4].getCounty().setDisabled(false);
    },
    //点击部门基本信息修改
    updateDeptInfoFun:function(){
    	var me =this;
    	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
    		return;
    	};
    	me.getForm().findField('address').setReadOnly(false);
    	me.getForm().findField('depTelephone').setReadOnly(false);
    	me.getForm().findField('depFax').setReadOnly(false);
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:119,
			id:'Foss_BaseInfo_OrgAdministrativeInfo_UpdateDeptButton_Id',
			cls:'yellow_button',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBaseInfoUpdateButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBaseInfoUpdateButton'),
			text : '修改部门基本信息',//修改部门信息
			handler : function() {
				me.updateDeptInfoFun();
			}
		},{
			xtype : 'button', 
			id:'Foss_BaseInfo_OrgAdministrativeInfo_SaveButton_Id',
			width:80,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizSaveButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizSaveButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler : function() {
				me.saveOrgAndSalesInfo(this);
			}
		},{
			xtype : 'button', 
			id:'Foss_BaseInfo_OrgAdministrativeInfo_UpdateButton_Id',
			width:119,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizUpdateButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/queryOrgBizUpdateButton'),
			text : '修改行政组织信息',//baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler : function() {
				me.updateFun();
			}
		}];
		me.items = [{
			name: 'code',
			colspan : 3,
			width:250,
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentNumber'),//部门编号
	        xtype : 'textfield'
		},{
			name: 'unifiedCode',
			colspan : 3,
			width:250,
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.benchmarkingCoding'),//标杆编码
	        xtype : 'textfield'
		},{
			name: 'name',
			colspan : 3,
			width:300,
			labelWidth:100,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.orgName'),//部门名称
	        xtype : 'textfield'
		},{
			name: 'deptArea',
			colspan : 3,
			decimalPrecision:3,
			maxValue: 99999.999,
			maxLength:9,
			minValue:0,
	        step:0.001,
			labelWidth:100,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theDepartmentsAreaSquareMeters'),//部门面积（平方米）
	        xtype : 'numberfield'
		},{
			xtype:'linkregincombselector',
			type:'N-P-C-C',
			labelWidth:80,
			nationIsBlank : true,
			cityIsBlank : true,
			provinceIsBlank : true,
			width:720,
			cityLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.city'),//城市
			provinceLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.province'),//省份
			areaLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.county'),//区县
			fieldLabel: '<span style="color:red">*</span>国家地区',//国家地区
			colspan : 6,
			name:'blongAddress'
		},{
			name: 'depCoordinate',
			id:'depCoordinate_id',
			colspan : 3,
			labelWidth:120,
			width:300,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentServiceAreaCoordinateCoding'),//部门服务区坐标编码
	        xtype : 'textfield'
		},{
			name:'complementSimpleName',
			colspan:3,
			labelWidth:100,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.complementSimpleName'),//补码简称
			xtype:'textfield'
		},{
			name: 'orgEnSimple',
			colspan : 3,
			labelWidth:100,
			width:300,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentalAbbreviation'),//部门英文简称
	        xtype : 'textfield'
		},{
			xtype : 'dynamicorgcombselector',
			name: 'entityFinance',
			colspan : 3,
			allowBlank:true,
			types:'ORG',
			labelWidth:100,
			width:300,
			isEntityFinance:'Y',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.owningEntityFinanceDepartment')//所属实体财务部
		},{
			name : 'orgLevel',
			queryMode : 'local',
			colspan : 3,
			displayField : 'valueName',
			valueField : 'valueCode',
			store : FossDataDictionary.getDataDictionaryStore(baseinfo.orgAdministrativeInfo.orgLevel),
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentLevel'), //部门级别
			xtype : 'combo'
		},{
			xtype:'container',
			colspan : 3,
			items:[{
				name: 'orgSimpleName',
				id:'baseinfo_orgSimpleName_Id',
		        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentReferred'),//部门简称9
				labelWidth:100,
				width:300,
				maxLength:16,
				readOnly:true,
		        xtype : 'textfield'
			}]
		},{
			name: 'address',
			colspan : 6,
			labelWidth:100,
			maxLength:333,
			maxLengthText:'字数不能超过333字',
			//allowBlank:false,
			xtype :'textfield',
			width:620,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentAddress')//部门地址
		},{
			name: 'depTelephone',
			colspan : 3,
			allowBlank:false,
			maxLength:100,
			labelWidth:100,
			width:300,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.tel'),//联系电话1
	        xtype : 'textfield'
		},{
			name: 'depFax',
			colspan : 3,
			allowBlank:false,
			maxLength:100,
			labelWidth:100,
			width:300,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentFax'),//部门传真2
	        xtype : 'textfield'
		},{
			text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.electronicMapOfTheEditorialDepartment'),//编辑部门电子地图
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editDepartmentMapButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editDepartmentMapButton'),
			colspan : 3,
			id:'editDepartmentMapButton_id',
			width:170,
			handler: function() {
				if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
		      me.getMainGisWindow().show();
		    },
            xtype:'button'
		},{
			text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.viewDepartmentElectronicMap'),//查看部门电子地图
			colspan : 3,
			width:170,
		    handler: function(){
		    	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
		      me.getMainGisShowWindow().show();
		    },
            xtype:'button'
		},{
	        text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.editDeliveryRegionalElectronicMap'),//编辑派送区域电子地图
	        disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editDeliveryRegionalElectronicMapButton'),
	        hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editDeliveryRegionalElectronicMapButton'),
	        colspan : 3,
	        width:170,
	        readOnly:false,
	        id:'editDelivery_id',
		    xtype:'button',
	        handler: function() {
	        	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
	        	me.getDeliveryMainGisWindow().show();
	        }
		},{
        	text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.viewDeliveryAreaElectronicMap'),//查看派送区域电子地图
	        xtype:'button',
	        width:170,
	        readOnly:false,
	        id:'showDelivery_id',
	        colspan : 3,
            handler: function() {
            	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
                me.getDeliveryMainGisShowWindow().show();
            }
        },
        /*{
        	text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.editExpressDeliveryAreaElectronicMap'),//编辑快递派送区域电子地图
	        xtype:'button',
	        colspan : 3,
	        readOnly:false,
	        id:'editExpressDelivery_id',
	        width:170,
            handler: function() {
            	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
                me.getExpressDeliveryGisEditWidow().show();
            }
        },*/
        {
        	text: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.viewExpressDeliveryAreaElectronicMap'),//查看快递派送区域电子地图
	        xtype:'button',
	        colspan : 3,
	        readOnly:false,
	        id:'showExpressDelivery_id',
	        width:170,
            handler: function() {
            	if(Ext.isEmpty(me.orgAdministrativeInfoModel)){
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartmentBeforeOperation'));//请选择具体部门进行操作
    				return;
				}
                me.getExpressDeliveryGisShowWindow().show();
            }
        }];
		me.callParent([cfg]);
	    me.down('container').getNation().setReadOnly(true);
		me.down('container').getNation().setDisabled(true);
    	me.down('container').getProvince().setReadOnly(true);
    	me.down('container').getProvince().setDisabled(true);
    	me.down('container').getCity().setReadOnly(true);
    	me.down('container').getCity().setDisabled(true);
    	me.down('container').getCounty().setReadOnly(true);
    	me.down('container').getCounty().setDisabled(true);
	}
});
/**
 * 行政组织业务属性Form
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgBusinessInfoForm', {
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.orgBizProperty'),//查看/修改详情
	frame:true,
	orgAdministrativeInfoModel:null,//加载的数据Model
    height:200,
    margin : '0 5 5 5',
    collapsible :true,
    animCollapse : true,
    collapsed : true,
    layout:{
		type:'column'
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 0 5 0'
    },
    constructor : function(config){
    	var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getForm().loadRecord(me.orgAdministrativeInfoModel);
			}
		},{
			xtype : 'button', 
			width:80,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBusinessInfoForm'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBusinessInfoForm'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler : function() {
				me.up('panel').saveAllOrgInfo(this);
			}
		},{
			xtype : 'button', 
			width:80,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBusinessInfoForm'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBusinessInfoForm'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler : function() {
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().getFields( ).each(function(item){//设置可编辑
		    		item.setReadOnly(false);
		    	});
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airTransportableStowage'),//可空运配载
            name  : 'doAirDispatch',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		//获取主面板
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		if(newvalue){
            			orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
            			me.getForm().findField('airDispatch').show();
            			if(me.up('panel').getOutfieldMainForm().getForm().findField('transferCenter').getValue()){ //如果是外場，就不能是配載部門 和空運總調
            				checkbox.setValue(false);
                        	baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentHasFieldCanNoLongerCheckAirDispatch'));//该部门已经是外场！
                        	return;
            			}
            			//选择了可空运配载，必须选择可空运总调
            			me.getForm().findField('airDispatch').setValue('Y');
            			me.doLayout();
            		}else{
            			orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
            			me.getForm().findField('airDispatch').reset();
            			me.getForm().findField('airDispatch').hide();
            			me.doLayout();
            		}
            	}
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.totalTransferByAir'),//空运总调
            name      : 'airDispatch',
            columnWidth:0.25,
            xtype:'checkbox',
            hidden:true,
            inputValue: 'Y',
            listeners:{
            	change : function(checkbox, newvalue, oldvalue){
            		if(newvalue){
            			
            		}else{
            			//若可空运配载勾上，不可以去掉空空运总调
            			if(me.getForm().findField('doAirDispatch').getValue()){
            				checkbox.setValue(true);
                        	baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airDispatchAndDoAirDispatchMustAllCheck'));
                        	return;
            			}
            		}
            	}
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheBusinessDistrict'),//是否营业小区
            name      : 'smallRegion',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change : function(checkbox, newvalue, oldvalue) {
            		//获取主面板
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
					if (newvalue) {
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					} else {
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}
				}
			}
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherOperatingLargeArea'),//是否营业大区
            name      : 'bigRegion',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change : function(checkbox, newvalue, oldvalue) {
            		//获取主面板
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
					if (newvalue) {
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					} else {
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}
				}
			}
		},{//新增的快递属性1
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherExpressSalesDepartment'),//是否快递虚拟营业部
			name :'expressSalesDepartment',
			xtype :'checkbox',
			inputValue :'Y',
			columnWidth:0.25,
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					var saleDepartmentInfoPanel= this.up('form').up('panel').getSaleDeptMainPanel().getSaleDepartmentInfoPanel();
					var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
					if(newvalue){
						var  saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();//上部form
						//判断是否驻地营业部勾选没
        				if(saleDepartmentPartForm.getForm().findField('station').getValue()){
        					checkbox.setValue(false);
        					//若是驻地营业部 ，提示
        					baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentHasStationCanNoLongerCheckTheSalesDepartment'));//
                			return;
        				}
        				//设置简称字段
        				orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
        				//出发，到达产品可选、已选的grid 
        				var startConSelectPanel =saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect(),
        				 startingProductsSelect =  saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected(),
        				 arriveConSelectPanel = saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect(),
        				 arriveProductsSelect = saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected();
        				//先判断，已选中是否有包裹
        				startingProductsSelect.getStore().data.each(function(item){
        					if(item.data.code!='PACKAGE'){
        						startingProductsSelect.getStore().remove(item);
        					}
        				});
        				arriveProductsSelect.getStore().data.each(function(item){
        					if(item.data.code!='PACKAGE'){
        						arriveProductsSelect.getStore().remove(item);
        					}
        				});
        				//再判断可选中 是否有包裹产品
        				startConSelectPanel.getStore().data.each(function(item){
        					if(item.data.code!='PACKAGE'){
        						startConSelectPanel.getStore().remove(item);
        					}
        				});
        				arriveConSelectPanel.getStore().data.each(function(item){
        					if(item.data.code!='PACKAGE'){
        						arriveConSelectPanel.getStore().remove(item);
        					}
        				});
					}else{
						//设置简称字段
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
        				//先清空全部的产品信息
        				saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().removeAll();//先清掉表格中数据
                		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().removeAll();
                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().removeAll();
                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().removeAll();
                		//重新加载数据
                		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().loadData(saleDepartmentInfoPanel.departureConSelectData);
                		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().loadData(saleDepartmentInfoPanel.departureSelectedData);
                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().loadData(saleDepartmentInfoPanel.arriveConSelectData);
                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().loadData(saleDepartmentInfoPanel.arriveSelectedData);
					}
				}
			}
		},{//新增的快递属性2
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherExpressBigRegion'),//是否快递大区
			name :'expressBigRegion',
			xtype :'checkbox',
			inputValue :'Y',
			columnWidth:0.25,
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					//获取主面板
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
					if(newvalue){
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}else{
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}
				}
			}
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheEntitysFinancialUnit'),//是否实体财务部
            name      : 'isEntityFinance',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheDivision'),//是否事业部
			//margin : '5 5 5 15',
			columnWidth:0.25,
            name      : 'division',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =this.up('form').up('panel').getOrgMainInfoForm();
            		if(newvalue){
            			//设置部门简称字段
            			orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
        				me.getForm().findField('divisionCode').show();//显示事业部编码
        				if(Ext.isEmpty(orgMainInfoForm.orgAdministrativeInfoModel.get('divisionCode'))){
        					me.getForm().findField('divisionCode').setValue(orgMainInfoForm.orgAdministrativeInfoModel.get('code'));
        				}
                	}else{
                		orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
        				me.getForm().findField('divisionCode').hide();
        				me.getForm().findField('divisionCode').setValue('');
                	}
                }
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherExpressPart'),//是否快递点部
			name :'expressPart',
			xtype :'checkbox',
			inputValue :'Y',
			columnWidth:0.25,
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					var orgMainInfoForm =this.up('form').up('panel').getOrgMainInfoForm();
					if(newvalue){
						//设置部门简称
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}else{
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}
				}
			}
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherBranch'),//是否快递分部
			name :'expressBranch',
			xtype :'checkbox',
			inputValue :'Y',
			columnWidth:0.25
		},{
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.divisionEncodingCodingForCentralizedAccessDelivery'),//事业部编码（用于集中接送货的编码）
			name :'divisionCode', 
			xtype : 'textfield',
			labelWidth:220,
			columnWidth:0.5,	
			hidden:true	
		},{
			boxLabel:'是否快递分拣',
			name:'expressSorting',
			xtype:'checkbox',
			inputValue:'Y',
			columnWidth:0.25,
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					var orgMainInfoForm =this.up('form').up('panel').getOrgMainInfoForm();
					if(newvalue){
						//设置部门简称
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}else{
						orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
					}
				}
			}
		},{
			boxLabel:'是否经营本部',
			name:'isManageDepartment',
			xtype:'checkbox',
			inputValue:'Y',
			columnWidth:0.25
		}];
		me.callParent([cfg]);
    }
});
/**
 *------------------------营业部信息主界面(checkbox+saleDepartmentInfoPanel)-------------------------
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDeptMainPanel',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.businessDepartment'),//营业部信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:500,
	autoScroll:true,
	margin : '0 5 5 5',
    layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 5 5'
    },
    //保存营业部信息的方法
    saveSaleDeptInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
    	//若是否营业部没有勾选的话，提示没有勾选营业部
    	if(!me.getForm().findField('salesDepartment').getValue()){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillIsSalesDept'));//请勾选是否营业部
    		button.setDisabled(false);
    		return;
    	}
    	var saleDepartmentEntity = null;
    	if(orgAdministrativeInfoEntity.salesDepartment=='Y'){
    		saleDepartmentEntity = orgInfoPanel.getSaleDepartmentInfo();
    		if(Ext.isEmpty(saleDepartmentEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteBusinessDepartment'));//请填写完整营业部信息
    			button.setDisabled(false);
    			return;
    		}
    		//---------------只做校验-----------------------------------------------------------------------------------------------------------
        	if(saleDepartmentEntity.singleBillLimitkg>0){
        		saleDepartmentEntity.singleBillLimitkg=saleDepartmentEntity.singleBillLimitkg*100;
        	}
        	if(saleDepartmentEntity.singleBillLimitvol>0){
        		saleDepartmentEntity.singleBillLimitvol=saleDepartmentEntity.singleBillLimitvol*100;
        	}
    		if(saleDepartmentEntity.singlePieceLimitkg>0){
        		saleDepartmentEntity.singlePieceLimitkg=saleDepartmentEntity.singlePieceLimitkg*100;
        	}
        	if(saleDepartmentEntity.singlePieceLimitvol>0){
        		saleDepartmentEntity.singlePieceLimitvol=saleDepartmentEntity.singlePieceLimitvol*100;
        	}
    	}
    	var saleDepartmentInfoPanel=me.getSaleDepartmentInfoPanel(); //获取营业部信息panel
    	var saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();   //获取头部表单信息
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//中部的grid组（出发）
    	var saleDepartmentBottomPanel = saleDepartmentInfoPanel.getSaleDepartmentBottomPanel(); //下部的信息（到达）
    	//判断可出发是否已经勾选上了，若勾选上了
    	if(saleDepartmentCerterPanel.getForm().findField('leave').getValue()){
    		//若出发产品已选为空的话，提示出发产品中必须有可选产品
    		if(saleDepartmentCerterPanel.getStartingProductsSelected().getStore().getCount()==0){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillStartingProducts'));
    			button.setDisabled(false);
    			return;
    		}
    	}
    	var salesProductEntityList = orgInfoPanel.getSalesProductEntityList();
    	var params = {'orgAdministrativeInfoVo':
			{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity
			,'saleDepartmentEntity':saleDepartmentEntity
			,'salesProductEntityList':salesProductEntityList
			}};
		/**
		* 功能：营业部，营业小区，营业大区  都要判断简称是否为空；为空则提示
		* */
		if (orgAdministrativeInfoEntity.salesDepartment == 'Y'&& Ext.isEmpty(orgAdministrativeInfoEntity.orgSimpleName)){
			Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.alertOrgSimpleName'));
			button.setDisabled(false);
			return;
		}
		var successFun = function(json){
    		button.setDisabled(false);
    		//bug-20556
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.saleDepartmentEntity)){//首先先确保返回值了(营业部)
				var saleDepartmentModel =  new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity(json.orgAdministrativeInfoVo.saleDepartmentEntity);//创建营业部的model
				saleDepartmentInfoPanel.saleDepartmentModel = saleDepartmentModel;
		    	orgInfoPanel.getOrgMainInfoForm().saleDepartmentModel = saleDepartmentModel;
			}
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.salesProductEntityList)){//部门产品
    			saleDepartmentInfoPanel.salesProductEntityList = salesProductEntityList;
		    	orgInfoPanel.getOrgMainInfoForm().salesProductEntityList = salesProductEntityList;
    		}
    		var salesMotorcadeEntityList =new Array();
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.salesMotorcadeEntityList)){//部门车队
    			saleDepartmentInfoPanel.salesMotorcadeEntityList = salesMotorcadeEntityList;
		    	orgInfoPanel.getOrgMainInfoForm().salesMotorcadeEntityList = salesMotorcadeEntityList;
    		}
    		var selectedCentralizedBillingGroupList =new Array();
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.selectedCentralizedBillingGroupList)){//营业部已选集中开单组
    			saleDepartmentInfoPanel.selectedCentralizedBillingGroupList = selectedCentralizedBillingGroupList;
		    	orgInfoPanel.getOrgMainInfoForm().selectedCentralizedBillingGroupList = selectedCentralizedBillingGroupList;
    		}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		//若修改成功后，该营业部可派送营业部，但是还没有描点，所以要给与用户描点提示
    		if((saleDepartmentInfoPanel.saleDepartmentModel.get('delivery')=='Y')
    				&&(Ext.isEmpty(saleDepartmentInfoPanel.saleDepartmentModel.get('deliveryCoordinate')))){
    			baseinfo.showInfoMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.plasePointTheDeliveryMap'));
    		}
    		me.setAllReadOnly();//设置营业部信息为不可编辑
    	};
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoBySaleDepartment.action');
    	//保存营业部信息
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
    //设置营业部信息不可编辑
    setAllReadOnly:function(){
    	var me =this;
    	var saleDepartmentInfoPanel = me.getSaleDepartmentInfoPanel();
    	var saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();//上部form
    	var deliveryInformationForm = saleDepartmentInfoPanel.getDeliveryInformationForm();//提货form
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//中部的grid组(出发)
    	var saleDepartmentBottomPanel =saleDepartmentInfoPanel.getSaleDepartmentBottomPanel();//下部的grid组(到达)
    	
    	me.getForm().findField('salesDepartment').setReadOnly(true); //‘是否营业部’设为不可编辑
    	saleDepartmentPartForm.getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
    			item.setReadOnly(true);
    		}
    	});
    	saleDepartmentCerterPanel.getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
    			item.setReadOnly(true);
    		}
    	});
    	saleDepartmentBottomPanel.getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
    			item.setReadOnly(true);
    		}
    	});
    	//当可达到是选择上的时候，才可以设置提货不可编辑
    	if(saleDepartmentBottomPanel.getForm().findField('arrive').getValue()){
    		if(!Ext.isEmpty(deliveryInformationForm)){
    			deliveryInformationForm.getForm().getFields( ).each(function(item){//提货的设置不可编辑
        			if(item.readOnly==false){
        				item.setReadOnly(true);
        			}
        		});
    		}
    	}
    	var startingButtonPanel = saleDepartmentCerterPanel.getStartingButtonPanel();//出发使用产品按钮Panel
    	var arriveButtonPanel = saleDepartmentBottomPanel.getArriveButtonPanel();//到达适用产品按钮Panel
    	//出发使用产品按钮设置不可用
    	for(var i=0;i<startingButtonPanel.items.items.length;i++){
    		startingButtonPanel.items.items[i].setDisabled(true);
    	}
    	//到达适用产品按钮设置不可用
    	for(var i=0;i<arriveButtonPanel.items.items.length;i++){
    		arriveButtonPanel.items.items[i].setDisabled(true);
    	}
    },
    //设置营业部为可编辑
    setAllCanEdit:function(){
    	var me =this;
    	var saleDepartmentInfoPanel=me.getSaleDepartmentInfoPanel(); //获取营业部信息panel
		var saleDepartmentPartForm = saleDepartmentInfoPanel.getSaleDepartmentPartForm();//上部form
    	var deliveryInformationForm = saleDepartmentInfoPanel.getDeliveryInformationForm();//提货form
    	var saleDepartmentCerterPanel = saleDepartmentInfoPanel.getSaleDepartmentCerterPanel();//中部的grid组（出发信息）
    	var saleDepartmentBottomPanel =	saleDepartmentInfoPanel.getSaleDepartmentBottomPanel();//下部的form（到达信息）
	    	saleDepartmentPartForm.getForm().getFields( ).each(function(item){//设置可编辑
	    		if(item.readOnly == true){
    				item.setReadOnly(false);
    			}
	    	});
	    	saleDepartmentPartForm.getForm().findField('maxTempArrears').setReadOnly(true);
	    	saleDepartmentCerterPanel.getForm().getFields( ).each(function(item){//设置可编辑
	    		if(item.readOnly == true){
    				item.setReadOnly(false);
    			}
	    	});
	    	//未勾选是否加盟网点时触发change事件-308861
	        saleDepartmentPartForm.getForm().findField('isLeagueSaleDept').fireEvent('change');
	    	//让代收货款上限不收修改按钮影响
	    	var agentCollectedUpperLimit=saleDepartmentBottomPanel.getForm().findField('agentCollectedUpperLimit');
	    	var  canAgentCollectedMany= saleDepartmentBottomPanel.getForm().findField('canAgentCollectedMany');
	    	var canAgentCollected=saleDepartmentBottomPanel.getForm().findField('canAgentCollected');
	    	
	    	saleDepartmentBottomPanel.getForm().getFields( ).each(function(item){//设置可编辑
	    		if(item.readOnly == true){
    				item.setReadOnly(false);
    			}
	    	});
	    	if(canAgentCollected.getValue()==true||canAgentCollectedMany.getValue()==true){
	    		agentCollectedUpperLimit.setReadOnly(false);
	    	}else{
	    		agentCollectedUpperLimit.setReadOnly(true);
	    	}
	    	
	    	//先判断可到达的是否勾选上的，若已经勾选上的，再设置提货form中的为可编辑
	    	if(saleDepartmentBottomPanel.getForm().findField('arrive').getValue()){
	    		deliveryInformationForm.getForm().getFields( ).each(function(item){//提货的设置可编辑
	    			if(item.readOnly == true){
	    				item.setReadOnly(false);
	    			}
	    		});
	    	}
	    	var startingButtonPanel = saleDepartmentCerterPanel.getStartingButtonPanel();//出发使用产品按钮Panel
	    	var arriveButtonPanel = saleDepartmentBottomPanel.getArriveButtonPanel();//到达适用产品按钮Panel
	    	//若可出发已经选中
	    	if(saleDepartmentCerterPanel.getForm().findField('leave').getValue()){
	    		//出发使用产品按钮设置可用
    	    	for(var i=0;i<startingButtonPanel.items.items.length;i++){
    	    		startingButtonPanel.items.items[i].setDisabled(false);
    	    	}
	    	}
	    	//若可达到已经选中
	    	if(saleDepartmentBottomPanel.getForm().findField('arrive').getValue()){
	    		//到达适用产品按钮设置可用
    	    	for(var i=0;i<arriveButtonPanel.items.items.length;i++){
    	    		arriveButtonPanel.items.items[i].setDisabled(false);
    	    	}
	    	}
    },
    //营业部信息
	saleDepartmentInfoPanel:null,
	getSaleDepartmentInfoPanel:function(){
		if(Ext.isEmpty(this.saleDepartmentInfoPanel)){
    		this.saleDepartmentInfoPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentInfoPanel');
    	}
    	return this.saleDepartmentInfoPanel;
	},
    constructor : function(config){
    	var me = this, 
		cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
			}
		},{
			xtype : 'button', 
			width:80,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveSaleDeptInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveSaleDeptInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler : function() {
				me.saveSaleDeptInfo(this);
			}
		},{
			xtype : 'button', 
			width:80,
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editSaleDeptInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/editSaleDeptInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler : function() {
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
		    	me.getForm().findField('salesDepartment').setReadOnly(false); //‘是否营业部’可编辑
		    	//设置营业部信息可以编辑
				me.setAllCanEdit();
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheBusinessDepartment'),//是否营业部
			columnWidth:0.25,
            name      : 'salesDepartment',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
        			var saleDepartmentInfoPanel= me.getSaleDepartmentInfoPanel();
        			//先获取主信息Form
        			var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		if(newvalue){
            			//设置部门简称
            			orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
            			if(me.up('panel').getMotorcadeMainForm().getForm().findField('transDepartment').getValue()){//选择了车队不能选择营业部
            				checkbox.setValue(false);
            				baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.transDepartmentCanNotSelectSalesdepartment'));//该部门已经是车队，不能再勾选营业部！
                        	return;
            			}
            			if(me.up('panel').getOutfieldMainForm().getForm().findField('transferCenter').getValue()){//选择了外场不能选择营业部
                        	checkbox.setValue(false);
                        	baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentHasFieldCanNoLongerCheckTheSalesDepartment'));//该部门已经是车队调度组，不能再勾选车队！
                        	return;
                		}else{
                			saleDepartmentInfoPanel.show();//显示营业部设置FORM
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().findField('transferCenter').hide();
                    		saleDepartmentInfoPanel.saleDepartmentModel = orgMainInfoForm.saleDepartmentModel;//设置数据
                    		//若可到达没有勾选
                    		if(orgMainInfoForm.saleDepartmentModel.get('arrive')=='N'){
                    			saleDepartmentInfoPanel.getDeliveryInformationForm().hide();
                    		}else{
                    			saleDepartmentInfoPanel.getDeliveryInformationForm().show();
                    		}
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载数据
                    		saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载自提数据
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载出发部分信息
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载到达部分信息
                    		//BUG-34950
                    		//获取最大临欠金额的字段
                    		var maxTempArrears = saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().findField('maxTempArrears');
                    		//判断临欠金额是否为0,说明是新增
                    		if(orgMainInfoForm.saleDepartmentModel.get('maxTempArrears')== 0){
                        		//新增时，最大临欠金额设置为可编辑
                        		maxTempArrears.setReadOnly(false);	
                    		}
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().findField('transferCenter').setCombValue(orgMainInfoForm.saleDepartmentModel.get('transferCenterName')
                    				,orgMainInfoForm.saleDepartmentModel.get('transferCenter'));//所属驻地外场
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().findField('transferGoodDept').setCombValue(orgMainInfoForm.saleDepartmentModel.get('transferGoodDeptName')
                    				,orgMainInfoForm.saleDepartmentModel.get('transferGoodDept'));//所属集中开单组
                    		//-------------------------------------------营业部产品----------------------------------------------------
                    		var arriveConSelectData = new Array();//到达可选产品
                    		var arriveSelectedData = new Array();//到达已选产品
                    		var departureSelectedData = new Array();//出发已选产品
                    		var departureConSelectData = new Array();//出发可选产品
                    		if(baseinfo.orgAdministrativeInfo.saleDepartmentProduct.length==0){
                    			return;
                    		}else{
            					for(var j=0;j<orgMainInfoForm.salesProductEntityList.length;j++){
            						if(orgMainInfoForm.salesProductEntityList[j].salesType=='ORG_DEPARTURE'){//出发
            							for(var i=0;i<baseinfo.orgAdministrativeInfo.saleDepartmentProduct.length;i++){
            								if(baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code == orgMainInfoForm.salesProductEntityList[j].productCode){//code相同
            									var data = {'name':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].name,'code':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code};
            									departureSelectedData.push(data);//出发已选
            								}
            							}
            						}else if(orgMainInfoForm.salesProductEntityList[j].salesType=='ORG_ARRIVE'){
            							for(var i=0;i<baseinfo.orgAdministrativeInfo.saleDepartmentProduct.length;i++){
            								if(baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code == orgMainInfoForm.salesProductEntityList[j].productCode){
            									var data = {'name':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].name,'code':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code};
            									arriveSelectedData.push(data);//到达已选
            								}
            							}
            						}
            					}
            					for(var i=0;i<baseinfo.orgAdministrativeInfo.saleDepartmentProduct.length;i++){
            						var isArriveSelectedDataItem = false;//标记是否在到达已选择中
    								for(var j=0;j<arriveSelectedData.length;j++){
    									if(arriveSelectedData[j].code==baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code){
    										isArriveSelectedDataItem = true;
    									}
    								}
    								var isDepartureSelectedDataItem = false;//标记是否在出发已选择中
    								for(var j=0;j<departureSelectedData.length;j++){
    									if(departureSelectedData[j].code==baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code){
    										isDepartureSelectedDataItem = true;
    									}
    								}
    								if(!isArriveSelectedDataItem){
    									var data = {'code':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code,'name':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].name};
    									arriveConSelectData.push(data);
    								}
    								if(!isDepartureSelectedDataItem){
    									var data = {'code':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].code,'name':baseinfo.orgAdministrativeInfo.saleDepartmentProduct[i].name};
    									departureConSelectData.push(data);
    								}
    							}
                    		}
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().removeAll();//先清掉表格中数据
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().loadData(departureConSelectData);
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().loadData(departureSelectedData);
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().loadData(arriveConSelectData);
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().loadData(arriveSelectedData);
                    		
                    		saleDepartmentInfoPanel.departureConSelectData = departureConSelectData;//将产品值付给panel属性，充值时候用
                    		saleDepartmentInfoPanel.departureSelectedData = departureSelectedData;
                    		saleDepartmentInfoPanel.arriveConSelectData = arriveConSelectData;
                    		saleDepartmentInfoPanel.arriveSelectedData = arriveSelectedData;
                    		//----------------------------------营业部集中开单组-------------------------------------------------
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedBillingGroup().getStore().removeAll();//先清空列表中之前的数据
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedBillingGroup().getStore().add(saleDepartmentInfoPanel.selectedCentralizedBillingGroupList);
                    		//----------------------------------营业部车队-------------------------------------------------
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedMotercade().getStore().removeAll();//先清空列表中的数据
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedMotercade().getStore().add(saleDepartmentInfoPanel.salesMotorcadeEntityList);
                		}
                	}else{
                		orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
                		if(!Ext.isEmpty(orgMainInfoForm.saleDepartmentModel)&&!Ext.isEmpty(orgMainInfoForm.saleDepartmentModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantDeleteTheAdministrativeOrganizationSalesDepartment'),function(e){//是否要删除行政组织的营业部信息？
                				if(e=='yes'){//询问是否删除，是则发送请求
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.salesDepartment = 'N';
                					var params = {'orgAdministrativeInfoVo'
                							:{'saleDepartmentEntity':orgMainInfoForm.saleDepartmentModel.data 
                					,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废营业部相关信息,顺带修改行政组织
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						orgMainInfoForm.saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity();//将空的数据设置到营业部model
                                		saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);
                            			saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载到达部分信息
                            			saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载数据
                            			saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载出发部分信息
                                		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().removeAll();//清掉表格中数据
                                		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().removeAll();
                                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().removeAll();
                                		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().removeAll();
                                		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedBillingGroup().getStore().removeAll();
                                		var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                    					var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity;  
                                		me.setAllReadOnly();
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteSaleDepartment.action');
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			orgMainInfoForm.salesProductEntityList = [];
                			saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsConSelect().getStore().removeAll();//先清掉表格中数据
                    		saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getStartingProductsSelected().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsConSelect().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getArriveProductsSelected().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedBillingGroup().getStore().removeAll();
                    		saleDepartmentInfoPanel.getSaleDepartmentPartForm().getSalesBeServedMotercade().getStore().removeAll();
                			var saleDepartmentModel = new Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentEntity();//重新设置以此控制
                			orgMainInfoForm.saleDepartmentModel = saleDepartmentModel;
                			saleDepartmentInfoPanel.getDeliveryInformationForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);
                			saleDepartmentInfoPanel.getSaleDepartmentCerterPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载到达部分信息
                			saleDepartmentInfoPanel.getSaleDepartmentPartForm().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载数据
                			saleDepartmentInfoPanel.getSaleDepartmentBottomPanel().getForm().loadRecord(orgMainInfoForm.saleDepartmentModel);//加载出发部分信息
                		};
                	}
                }
            }
		},me.getSaleDepartmentInfoPanel()];
		me.callParent([cfg]);
    }
});
/**
 *------------------------外场信息主界面(checkbox+outfieldInfoForm)------------------------- 
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OutfieldMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.outfieldInfo'),//外场信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:320,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//外场信息FORM
	outfieldInfoForm:null,
	getOutfieldInfoForm:function(){
		if(Ext.isEmpty(this.outfieldInfoForm)){
    		this.outfieldInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OutfieldInfoForm');
    	}
    	return this.outfieldInfoForm;
	},
	//获取外场信息
    getOutFieldInfo:function(){
    	var me = this;
    	var outFieldForm = me.getOutfieldInfoForm();
    	//行政组织主信息Form
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	if(outFieldForm.getForm().isValid()){//如果是空则创建一个
    		if(Ext.isEmpty(outFieldForm.outfieldModel)){
    			outFieldForm.outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();
    		}
    		outFieldForm.getForm().updateRecord(outFieldForm.outfieldModel);//加载数据
    		outFieldForm.outfieldModel.set('orgCode',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));//设置行政组织code
    		if(outFieldForm.outfieldModel.get('vehicleAssemble')=='true'){//可否汽运配载
    			outFieldForm.outfieldModel.set('vehicleAssemble','Y');
    		}else if(outFieldForm.outfieldModel.get('vehicleAssemble')=='false'){
    			outFieldForm.outfieldModel.set('vehicleAssemble','N');
    		}
    		if(outFieldForm.outfieldModel.get('outAssemble')=='true'){//可外发配载
    			outFieldForm.outfieldModel.set('outAssemble','Y');
    		}else if(outFieldForm.outfieldModel.get('outAssemble')=='false'){
    			outFieldForm.outfieldModel.set('outAssemble','N');
    		}
    		if(outFieldForm.outfieldModel.get('packingWood')=='true'){//可打木架
    			outFieldForm.outfieldModel.set('packingWood','Y');
    		}else if(outFieldForm.outfieldModel.get('packingWood')=='false'){
    			outFieldForm.outfieldModel.set('packingWood','N');
    		}
    		if(outFieldForm.outfieldModel.get('transfer')=='true'){//车可中转
    			outFieldForm.outfieldModel.set('transfer','Y');
    		}else if(outFieldForm.outfieldModel.get('transfer')=='false'){
    			outFieldForm.outfieldModel.set('transfer','N');
    		}
    		if(outFieldForm.outfieldModel.get('sortingMachine')=='true'){//是否自动分拣机
    			outFieldForm.outfieldModel.set('sortingMachine','Y');
    		}else if(outFieldForm.outfieldModel.get('sortingMachine')=='false'){
    			outFieldForm.outfieldModel.set('sortingMachine','N');
    		}
      		//新增的快递属性12
    		if(outFieldForm.outfieldModel.get('expressOutAssemble')=='true'){ //是否落地外发配载
    			outFieldForm.outfieldModel.set('expressOutAssemble','Y');
    		}else if(outFieldForm.outfieldModel.get('expressOutAssemble')=='false'){
    			outFieldForm.outfieldModel.set('expressOutAssemble','N');
    		}
    		if(outFieldForm.outfieldModel.get('isHaveWaitForkArea')=='true'){ //是否有待叉区
    			outFieldForm.outfieldModel.set('isHaveWaitForkArea','Y');
    		}else if(outFieldForm.outfieldModel.get('isHaveWaitForkArea')=='false'){
    			outFieldForm.outfieldModel.set('isHaveWaitForkArea','N');
    		}
    		return outFieldForm.outfieldModel.data;//把自己本身的model修改了，并返回其data，以供传到后台
    	}else{
    		return null;
    	}
    },
	//保存外场的数据
    saveOutfieldInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	//外场信息Form
    	var outfieldInfoForm =me.getOutfieldInfoForm();
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
    	//外场
    	var outfieldEntity = null;
    	if(orgAdministrativeInfoEntity.transferCenter=='Y'){
    		outfieldEntity = me.getOutFieldInfo();
    		if(Ext.isEmpty(outfieldEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteFieldInformation'));//请填写完整外场信息！
    			button.setDisabled(false);
    			return;
    		}else{
    			var deptArea = orgAdministrativeInfoEntity.deptArea;
    			var platArea = outfieldEntity.platArea;//货台面积
    			var goodsArea = outfieldEntity.goodsArea;//货区面积
    			if(!Ext.isEmpty(deptArea)){
    				if(!Ext.isEmpty(platArea)){
    					if(deptArea<platArea){
    						baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theDockAreaNotLargerThanTheSectorArea'));//货台面积不能大于部门面积！
    						button.setDisabled(false);
    						return;
    					}
    				}
    				if(!Ext.isEmpty(goodsArea)){
    					if(deptArea<goodsArea){
    						baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.cargoAreaNotLargerThanSectorArea'));//货区面积不能大于部门面积！
    						button.setDisabled(false);
    						return;
    					}
    				}
    			}
    		}
    	}else{
    		//若是否外场没有勾选上，提示勾选是否外场
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsOutfield'));//请勾选是否外场信息！
    		button.setDisabled(false);
    		return;
    	}
    	var params ={'orgAdministrativeInfoVo':{
    		'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity,'outfieldEntity':outfieldEntity}};
    	/**
    	 * 若保存外场,或者时，判断部门简称为空时，则提示
    	 */
    	if((orgAdministrativeInfoEntity.transferCenter == 'Y'|| orgAdministrativeInfoEntity.airDispatch == 'Y')&&Ext.isEmpty(orgAdministrativeInfoEntity.orgSimpleName)){
    		Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.alertOrgSimpleName'));
    		button.setDisabled(false);
			return;
    	}
    	var successFun = function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.outfieldEntity)){
    			var outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity(json.orgAdministrativeInfoVo.outfieldEntity);//返回的值设置到form的model中
    			outfieldInfoForm.outfieldModel = outfieldModel;
    			orgInfoPanel.getOrgMainInfoForm().outfieldModel = outfieldModel;
    		}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.closeFun();//设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByOutfield.action');
    	//保存外场
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
    //设置外场不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('transferCenter').setReadOnly(true);//设置'是否外场'可编辑
		me.getOutfieldInfoForm().getForm().getFields( ).each(function(item){//设置每个子节点为可编辑
			if(item.readOnly == false){
				item.setReadOnly(true);
			}
		});
	},
	//设置外出可编辑
	updateOutFieldFun:function(){
		var me =this;
		me.getOutfieldInfoForm().getForm().getFields( ).each(function(item){//设置每个子节点为可编辑				
			if(item.readOnly == true){
				item.setReadOnly(false);
			}
		});
	},
    constructor:function(config){
		var me =this, 	cfg = Ext.apply({}, config);
		me.fbar = [{
			xtype : 'button', 
			width:50,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			cls:'yellow_button',
			handler : function() {
				me.getOutfieldInfoForm().getForm().loadRecord(me.outfieldModel);
				me.getOutfieldInfoForm().getForm().findField('airDispatchCode').setCombValue(me.outfieldModel.get('airDispatchName'),me.outfieldModel.get('airDispatchCode'));
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveOutfieldInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveOutfieldInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveOutfieldInfo(this); 
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveOutfieldInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveOutfieldInfoButton'),
			cls:'yellow_button',
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
		    	me.getForm().findField('transferCenter').setReadOnly(false);//设置'是否外场'可编辑
		    	//设置外场信息form为可编辑
				me.updateOutFieldFun();
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherOutfield'),//是否外场
			width:90,
			colspan : 1,
			columnWidth:0.25,
            name : 'transferCenter',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		//先获取主信息Form
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var outfieldInfoForm =me.getOutfieldInfoForm();
                	if(newvalue){
                		//设置部门简称字段
                		orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
                		if(me.up('panel').getSaleDeptMainPanel().getForm().findField('salesDepartment').getValue()){//选择了营业部不能选择外场
                        	checkbox.setValue(false);
                        	baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentHasSalesDepartmentCanNoLongerCheckTheOutfield'));//该部门已经是营业部，不能再勾选外场！
                        	return;
                		}else if(me.up('panel').getOrgBusinessInfoForm().getForm().findField('airDispatch').getValue()){ //若选择了空运总调  就不能选择外场
                				checkbox.setValue(false);
                            	baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentHasAirDispatchCanNoLongerCheckTheOutfield'));//该部门已经是营业部，不能再勾选外场！
                            	return;
                		}else{
                			outfieldInfoForm.show();//显示外场设置FORM
                			outfieldInfoForm.outfieldModel = orgMainInfoForm.outfieldModel;//设置数据
                			outfieldInfoForm.loadRecord(orgMainInfoForm.outfieldModel);//加载数据
                			outfieldInfoForm.getForm().findField('airDispatchCode').setCombValue(orgMainInfoForm.outfieldModel.get('airDispatchName'),orgMainInfoForm.outfieldModel.get('airDispatchCode'));
                		}
                	}else{
                		orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
                		if(!Ext.isEmpty(orgMainInfoForm.outfieldModel)&&!Ext.isEmpty(orgMainInfoForm.outfieldModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherDeleteAdministrativeOrganizationOutfieldInformation'),function(e){//是否要删除行政组织的外场信息？
                				if(e=='yes'){//询问是否删除，是则发送请求
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.transferCenter = 'N';
                					var params = {'orgAdministrativeInfoVo'
                							:{'outfieldEntity':{'orgCode':orgMainInfoForm.outfieldModel.get('orgCode')}
                					,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废外场相关信息,顺带修改
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						orgMainInfoForm.outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();//将空的数据设置到外场model
                						outfieldInfoForm.outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();
                						outfieldInfoForm.getForm().loadRecord(new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity());
                						var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                						var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity; 
                                		me.closeFun();
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteOutfield.action');
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			var outfieldModel = new Foss.baseinfo.orgAdministrativeInfo.OutfieldEntity();//重新设置以此控制
                			orgMainInfoForm.outfieldModel = outfieldModel;
                			outfieldInfoForm.outfieldModel = outfieldModel;
                			outfieldInfoForm.getForm().loadRecord(outfieldModel);
                		};
                	}
                }
            }
		},me.getOutfieldInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 *------------------------车队信息主界面(checkbox+outfieldInfoForm)-------------------------
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetInformation'),//车队信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:400,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//车队信息Form
	motorcadeInfoForm:null,
	getMotorcadeInfoForm:function(){
		if(Ext.isEmpty(this.motorcadeInfoForm)){
    		this.motorcadeInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeInfoForm');
    	}
    	return this.motorcadeInfoForm;
	},
	//获取车队信息
    getMotorcadeInfo:function(){
    	var me = this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	//行政组织主信息Form
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	if(motorcadeInfoForm.getForm().isValid()){
    		if(Ext.isEmpty(motorcadeInfoForm.motorcadeModel)){
    			motorcadeInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
    		}
    		motorcadeInfoForm.getForm().updateRecord(motorcadeInfoForm.motorcadeModel);//加载数据
    		motorcadeInfoForm.motorcadeModel.set('code',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));//设置行政组织code
    		if(motorcadeInfoForm.motorcadeModel.get('service')=='true'){//是否集中接送货
    			motorcadeInfoForm.motorcadeModel.set('service','Y')
    		}else if(motorcadeInfoForm.motorcadeModel.get('service')=='false'){
    			motorcadeInfoForm.motorcadeModel.set('service','N')
    		}
    		if(motorcadeInfoForm.motorcadeModel.get('isTopFleet')=='true'){//是否顶级车队
    			motorcadeInfoForm.motorcadeModel.set('isTopFleet','Y')
    		}else if(motorcadeInfoForm.motorcadeModel.get('isTopFleet')=='false'){
    			motorcadeInfoForm.motorcadeModel.set('isTopFleet','N')
    		}
    		if(motorcadeInfoForm.motorcadeModel.get('isManageVehicle')=='true'){//是否直接管车
    			motorcadeInfoForm.motorcadeModel.set('isManageVehicle','Y');
    		}else if(motorcadeInfoForm.motorcadeModel.get('isManageVehicle')=='false'){
    			motorcadeInfoForm.motorcadeModel.set('isManageVehicle','N');
    		}
    		return motorcadeInfoForm.motorcadeModel.data;
    	}else{
    		return null;
    	}
    },
    //获取车队负责行政区域新增信息
    getMotorcadeServeDistrictEntityAddList:function(){
    	var me = this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeDistrictEntityAddModelList = motorcadeInfoForm.getMotorcadeServeDistrict().getStore().getNewRecords( );
    	var motorcadeServeDistrictEntityAddList = new Array();
    	for(var i = 0;i<motorcadeServeDistrictEntityAddModelList.length;i++){
    		motorcadeServeDistrictEntityAddList.push(motorcadeServeDistrictEntityAddModelList[i].data);
    	}
    	return motorcadeServeDistrictEntityAddList;
    },
    //获取车队负责营业区新增信息
    getMotorcadeServeSalesAreaEntityAddList:function(){
    	var me = this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeSalesAreaEntityAddModelList = motorcadeInfoForm.getMotorcadeServeSalesArea().getStore().getNewRecords( );
    	var motorcadeServeSalesAreaEntityAddList = new Array();
    	for(var i = 0;i<motorcadeServeSalesAreaEntityAddModelList.length;i++){
    		motorcadeServeSalesAreaEntityAddList.push(motorcadeServeSalesAreaEntityAddModelList[i].data);
    	}
    	return motorcadeServeSalesAreaEntityAddList;
    },
    //获取车队负责行政区域删除信息
    getMotorcadeServeDistrictEntityDeleteList:function(){
    	var me = this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeDistrictEntityDeleteModelList = motorcadeInfoForm.getMotorcadeServeDistrict().getStore().getRemovedRecords( );
    	var motorcadeServeDistrictEntityDeleteList = new Array();
    	for(var i = 0;i<motorcadeServeDistrictEntityDeleteModelList.length;i++){
    		motorcadeServeDistrictEntityDeleteList.push(motorcadeServeDistrictEntityDeleteModelList[i].data);
    	}
    	return motorcadeServeDistrictEntityDeleteList;
    },
    //获取车队负责营业区删除信息
    getMotorcadeServeSalesAreaEntityDeleteList:function(){
    	var me = this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeSalesAreaEntityDeleteModelList = motorcadeInfoForm.getMotorcadeServeSalesArea().getStore().getRemovedRecords( );
    	var motorcadeServeSalesAreaEntityDeleteList = new Array();
    	for(var i = 0;i<motorcadeServeSalesAreaEntityDeleteModelList.length;i++){
    		motorcadeServeSalesAreaEntityDeleteList.push(motorcadeServeSalesAreaEntityDeleteModelList[i].data);
    	}
    	return motorcadeServeSalesAreaEntityDeleteList;
    },
    //获取车队服务营业部的新增信息
    getMotorcadeServeSalesDeptEntityAddList:function(){
    	var me =this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeSalesDeptEntityAddModelList = motorcadeInfoForm.getMotorcadeServeSalesDept().getStore().getNewRecords( );
    	var motorcadeServeSalesDeptEntityAddList = new Array();
    	for(var i = 0;i<motorcadeServeSalesDeptEntityAddModelList.length;i++){
    		motorcadeServeSalesDeptEntityAddList.push(motorcadeServeSalesDeptEntityAddModelList[i].data);
    	}
    	return motorcadeServeSalesDeptEntityAddList;
    },
    //获取车队服务营业部的删除信息
    getMotorcadeServeSalesDeptEntityDeleteList:function(){
    	var me =this;
    	var motorcadeInfoForm = me.getMotorcadeInfoForm();
    	var motorcadeServeSalesDeptEntityDeleteModelList = motorcadeInfoForm.getMotorcadeServeSalesDept().getStore().getRemovedRecords( );
    	var motorcadeServeSalesDeptEntityDeleteList = new Array();
    	for(var i = 0;i<motorcadeServeSalesDeptEntityDeleteModelList.length;i++){
    		motorcadeServeSalesDeptEntityDeleteList.push(motorcadeServeSalesDeptEntityDeleteModelList[i].data);
    	}
    	return motorcadeServeSalesDeptEntityDeleteList;
    },
	//保存车队信息
	saveMotorcadeinfo:function(button){
		button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var motorcadeInfoForm =me.getMotorcadeInfoForm();
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
		//车队
    	var motorcadeEntity = null;
    	if(orgAdministrativeInfoEntity.transDepartment=='Y'){
    		motorcadeEntity = me.getMotorcadeInfo();
    		if(Ext.isEmpty(motorcadeEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteFleetInformation'));//请填写完整车队信息！
    			button.setDisabled(false);
    			return;
    		}
    	}else{
    		//若是否车队没有勾选，提示勾选车队
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsFleet'));//请勾选是否车队！
			button.setDisabled(false);
			return;
    	}
    	var params = {'orgAdministrativeInfoVo':
		{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity
		,'motorcadeEntity':motorcadeEntity
		,'motorcadeServeSalesAreaEntityAddList':me.getMotorcadeServeSalesAreaEntityAddList()  		//新增车队、车队组营业区
		,'motorcadeServeSalesAreaEntityDeleteList':me.getMotorcadeServeSalesAreaEntityDeleteList() 	//删除车队、车队组营业区
		,'motorcadeServeDistrictEntityAddList':me.getMotorcadeServeDistrictEntityAddList()  		//新增车队、车队组行政区域
		,'motorcadeServeDistrictEntityDeleteList':me.getMotorcadeServeDistrictEntityDeleteList() 	//删除车队、车队组行政区域
		,'motorcadeServeSalesDeptEntityAddList':me.getMotorcadeServeSalesDeptEntityAddList()		//新增车队营业部
		,'motorcadeServeSalesDeptEntityDeleteList':me.getMotorcadeServeSalesDeptEntityDeleteList()	//删除车队营业部
		}};
    	/**
    	 * 若保存车队时，判断部门简称为空时，则提示
    	 */
    	if(orgAdministrativeInfoEntity.transDepartment == 'Y'&&Ext.isEmpty(orgAdministrativeInfoEntity.orgSimpleName)){
    		Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.alertOrgSimpleName'));
    		button.setDisabled(false);
			return;
    	}
    	orgInfoPanel.getMyMask().show();//显示罩
    	var successFun = function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		//车队服务营业部、营业区、行政区域的store
    		var motorcadeServeSalesDeptStore = me.getMotorcadeInfoForm().getMotorcadeServeSalesDept().getStore(),
    			motorcadeServeSalesAreaStore= me.getMotorcadeInfoForm().getMotorcadeServeSalesArea().getStore(),
    			motorcadeServeDistrictStore=me.getMotorcadeInfoForm().getMotorcadeServeDistrict().getStore();
    		//车队
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.motorcadeEntity)){//首先先确保返回值了<!--下次修改注意下-->
				var motorcadeModel =  new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity(json.orgAdministrativeInfoVo.motorcadeEntity);//创建车队的model
				var motorcadeServeSalesAreaEntityList = json.orgAdministrativeInfoVo.motorcadeServeSalesAreaEntityList;	 //车队负责营业区
				var motorcadeServeDistrictEntityList = json.orgAdministrativeInfoVo.motorcadeServeDistrictEntityList;	//车队负责行政区域
				var motorcadeServeSalesDeptEntityList =json.orgAdministrativeInfoVo.motorcadeServeSalesDeptEntityList; //车队负责营业部
				if(Ext.isEmpty(motorcadeServeSalesAreaEntityList)){//防止NULL
					motorcadeServeSalesAreaEntityList = [];
				}
				if(Ext.isEmpty(motorcadeServeDistrictEntityList)){
					motorcadeServeDistrictEntityList = [];
				}
				if(Ext.isEmpty(motorcadeServeSalesDeptEntityList)){
					motorcadeServeSalesDeptEntityList =[];
				}
				motorcadeInfoForm.motorcadeModel = motorcadeModel;
				motorcadeInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
				motorcadeInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
				motorcadeInfoForm.motorcadeServeSalesDeptEntityList =motorcadeServeSalesDeptEntityList;
               	orgInfoPanel.getOrgMainInfoForm().motorcadeModel = motorcadeModel;
               	//先清空store ，再加载最新的store
               	motorcadeServeDistrictStore.removeAll();
               	motorcadeServeSalesAreaStore.removeAll();
               	motorcadeServeSalesDeptStore.removeAll();
               	motorcadeServeDistrictStore.loadData(motorcadeServeDistrictEntityList);
               	motorcadeServeSalesAreaStore.loadData(motorcadeServeSalesAreaEntityList);
               	motorcadeServeSalesDeptStore.loadData(motorcadeServeSalesDeptEntityList);
               }
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		orgInfoPanel.getMyMask().hide();//关闭罩
    		me.closeFun();//设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByMotorcade.action');
    	//保存车队
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    	
	},
	//设置车队form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('transDepartment').setReadOnly(true);//是否车队设置为可编辑
    	me.getMotorcadeInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
    	me.getMotorcadeInfoForm().getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(true);
    	me.getMotorcadeInfoForm().getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(true);
    	me.getMotorcadeInfoForm().getMotorcadeServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(true);
	},
	//设置车队为可编辑的
	updateMotorCadeFun:function(){
		var me =this;
		me.getMotorcadeInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
    		if(item.readOnly == true){
    			item.setReadOnly(false);
    		}
    	});
    	me.getMotorcadeInfoForm().getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(false);
    	me.getMotorcadeInfoForm().getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(false);
    	me.getMotorcadeInfoForm().getMotorcadeServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(false);
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar =[{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getMotorcadeInfoForm().getForm().loadRecord(me.motorcadeModel);
				me.getMotorcadeInfoForm().getForm().findField('serveBillTerm').setCombValue(me.motorcadeModel.get('serveBillTermName'),me.motorcadeModel.get('serveBillTerm'));
				me.getMotorcadeInfoForm().getForm().findField('transferCenter').setCombValue(me.motorcadeModel.get('transferCenterName'),me.motorcadeModel.get('transferCenter'));//所属外场
				me.getMotorcadeInfoForm().getMotorcadeServeDistrict().getStore().removeAll();//先清除数据
				me.getMotorcadeInfoForm().getMotorcadeServeDistrict().getStore().add(me.motorcadeServeDistrictEntityList);//先清除数据
				me.getMotorcadeInfoForm().getMotorcadeServeSalesArea().getStore().removeAll();//再加载数据
				me.getMotorcadeInfoForm().getMotorcadeServeSalesArea().getStore().add(me.motorcadeServeSalesAreaEntityList);//再加载原始数据
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeinfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeinfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveMotorcadeinfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeinfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeinfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
		    		me.getForm().findField('transDepartment').setReadOnly(false);//是否车队设置为可编辑
		    		//设置车队信息为可编辑
			    	me.updateMotorCadeFun();
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheFleet'),//是否车队
            name      : 'transDepartment',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            			if(me.up('panel').getSaleDeptMainPanel().getForm().findField('salesDepartment').getValue()){//选择了营业部不能选择车队
            				checkbox.setValue(false);
            				baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.salesdepartmentCanNotSelectTransDepartment'));//该部门已经是车队，不能再勾选营业部！
                        	return;
            			}
            		//先获取主信息Form
                	var orgMainInfoForm	=me.up('panel').getOrgMainInfoForm();
            		var motorcadeInfoForm = me.getMotorcadeInfoForm();
            		if(newvalue){
            			//设置部门简称字段
            			orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm); 			
            			motorcadeInfoForm.show();//显示车队设置FORM
                		motorcadeInfoForm.motorcadeModel = orgMainInfoForm.motorcadeModel;//设置数据
                		motorcadeInfoForm.getForm().loadRecord(orgMainInfoForm.motorcadeModel);//加载数据
                		motorcadeInfoForm.getForm().findField('serveBillTerm').setCombValue(orgMainInfoForm.motorcadeModel.get('serveBillTermName'),orgMainInfoForm.motorcadeModel.get('serveBillTerm'));//所服务集中开单组
                		motorcadeInfoForm.getForm().findField('transferCenter').setCombValue(orgMainInfoForm.motorcadeModel.get('transferCenterName'),orgMainInfoForm.motorcadeModel.get('transferCenter'));//所属外场
                		motorcadeInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();
                		motorcadeInfoForm.getMotorcadeServeDistrict().getStore().removeAll();
                		motorcadeInfoForm.getMotorcadeServeSalesDept().getStore().removeAll();
                		motorcadeInfoForm.getMotorcadeServeSalesArea().getStore().add(motorcadeInfoForm.motorcadeServeSalesAreaEntityList);//营业区
                		motorcadeInfoForm.getMotorcadeServeDistrict().getStore().add(motorcadeInfoForm.motorcadeServeDistrictEntityList);//行政区域         
                		motorcadeInfoForm.getMotorcadeServeSalesDept().getStore().add(motorcadeInfoForm.motorcadeServeSalesDeptEntityList);//营业部
                	}else{
                		orgMainInfoForm.setOrgSimpleNameField(orgMainInfoForm);
                		
                		if(!Ext.isEmpty(orgMainInfoForm.motorcadeModel)&&!Ext.isEmpty(orgMainInfoForm.motorcadeModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveAdministrativeOrganizationFleet'),function(e){//是否要删除行政组织的外场信息？
                				if(e=='yes'){//询问是否删除，是则发送请求
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.transDepartment = 'N';
                					var params = {'orgAdministrativeInfoVo':{'motorcadeEntity':{'code':orgMainInfoForm.motorcadeModel.get('code')}
                					,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废外场相关信息
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						orgMainInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();//将空的数据设置到外场model
                						//motorcadeInfoForm.hide();
                                		motorcadeInfoForm.motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                                		motorcadeInfoForm.getForm().loadRecord(new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity());
                                		var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                                		var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity;
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteMotorcade.action');
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			var motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                			orgMainInfoForm.motorcadeModel = motorcadeModel;
                    		motorcadeInfoForm.motorcadeModel = motorcadeModel;
                    		motorcadeInfoForm.getForm().loadRecord(motorcadeModel);
                    		motorcadeInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();
                    		motorcadeInfoForm.getMotorcadeServeDistrict().getStore().removeAll();
                    		motorcadeInfoForm.getMotorcadeServeSalesDept().getStore().removeAll();
                		};
                	}
                }
            }
		},me.getMotorcadeInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 *------------------------车队组信息主界面(checkbox+MotorcadeGroupInfoForm)---------------------- 
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeGroupMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetGroupInformation'),//车队组信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:320,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//车队组信息Form
	motorcadeGroupInfoForm:null,
	getMotorcadeGroupInfoForm:function(){
		if(Ext.isEmpty(this.motorcadeGroupInfoForm)){
    		this.motorcadeGroupInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeGroupInfoForm');
    	}
    	return this.motorcadeGroupInfoForm;
	},
	 //获取车队组信息
    getMotorcadeGroupInfo:function(){
    	var me = this;
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
    	if(motorcadeGroupInfoForm.getForm().isValid()){
    		if(Ext.isEmpty(motorcadeGroupInfoForm.motorcadeModel)){
    			motorcadeGroupInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
    		}
    		motorcadeGroupInfoForm.getForm().updateRecord(motorcadeGroupInfoForm.motorcadeModel);//加载数据
    		motorcadeGroupInfoForm.motorcadeModel.set('code',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));//设置行政组织code
    		if(motorcadeGroupInfoForm.motorcadeModel.get('serviceTeam')=='true'){//是否集中接送货
    			motorcadeGroupInfoForm.motorcadeModel.set('serviceTeam','Y');
    		}else if(motorcadeGroupInfoForm.motorcadeModel.get('serviceTeam')=='false'){
    			motorcadeGroupInfoForm.motorcadeModel.set('serviceTeam','N');
    		}
    		return motorcadeGroupInfoForm.motorcadeModel.data;
    	}else{
    		return null;
    	}
    },
    //获取车队组负责行政区域新增信息
    getMotorcadeGroupServeDistrictEntityAddList:function(){
    	var me = this;
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
    	var motorcadeServeDistrictEntityAddModelList = motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().getNewRecords( );
    	var motorcadeServeDistrictEntityAddList = new Array();
    	for(var i = 0;i<motorcadeServeDistrictEntityAddModelList.length;i++){
    		motorcadeServeDistrictEntityAddList.push(motorcadeServeDistrictEntityAddModelList[i].data);
    	}
    	return motorcadeServeDistrictEntityAddList;
    },
    //获取车队组负责营业区新增信息
    getMotorcadeGroupServeSalesAreaEntityAddList:function(){
    	var me = this;
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
    	var motorcadeServeSalesAreaEntityAddModelList = motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().getNewRecords( );
    	var motorcadeServeSalesAreaEntityAddList = new Array();
    	for(var i = 0;i<motorcadeServeSalesAreaEntityAddModelList.length;i++){
    		motorcadeServeSalesAreaEntityAddList.push(motorcadeServeSalesAreaEntityAddModelList[i].data);
    	}
    	return motorcadeServeSalesAreaEntityAddList;
    },
    //获取车队组负责行政区域删除信息
    getMotorcadeGroupServeDistrictEntityDeleteList:function(){
    	var me = this;
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
    	var motorcadeServeDistrictEntityDeleteModelList = motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().getRemovedRecords( );
    	var motorcadeServeDistrictEntityDeleteList = new Array();
    	for(var i = 0;i<motorcadeServeDistrictEntityDeleteModelList.length;i++){
    		motorcadeServeDistrictEntityDeleteList.push(motorcadeServeDistrictEntityDeleteModelList[i].data);
    	}
    	return motorcadeServeDistrictEntityDeleteList;
    },
    //获取车队组负责营业区删除信息
    getMotorcadeGroupServeSalesAreaEntityDeleteList:function(){
    	var me = this;
    	var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
    	var motorcadeServeSalesAreaEntityDeleteModelList = motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().getRemovedRecords( );
    	var motorcadeServeSalesAreaEntityDeleteList = new Array();
    	for(var i = 0;i<motorcadeServeSalesAreaEntityDeleteModelList.length;i++){
    		motorcadeServeSalesAreaEntityDeleteList.push(motorcadeServeSalesAreaEntityDeleteModelList[i].data);
    	}
    	return motorcadeServeSalesAreaEntityDeleteList;
    },
	//保存数据
	saveMotorcadeGroupInfo:function(button){
		button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var motorcadeGroupInfoForm =me.getMotorcadeGroupInfoForm();//获取车队组信息Form
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
		//车队组
    	if(orgAdministrativeInfoEntity.transTeam=='Y'){
    		motorcadeEntity = me.getMotorcadeGroupInfo();
    		if(Ext.isEmpty(motorcadeEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteFleetInformation'));//请填写完整车队信息！
    			button.setDisabled(false);
    			return;
    		}
    	}else{
    		//若是否车队组没有勾选上， 提示用户勾选该车队组 ，在进行保存
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsFleetGroup'));//请勾选是否车队组！
    		button.setDisabled(false);
    		return;
    	}
    	var params = {'orgAdministrativeInfoVo':
		{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity
		,'motorcadeEntity':motorcadeEntity
		,'motorcadeServeSalesAreaEntityAddList':me.getMotorcadeGroupServeSalesAreaEntityAddList()  		//新增车队、车队组营业区
		,'motorcadeServeSalesAreaEntityDeleteList':me.getMotorcadeGroupServeSalesAreaEntityDeleteList() 	//删除车队、车队组营业区
		,'motorcadeServeDistrictEntityAddList':me.getMotorcadeGroupServeDistrictEntityAddList()  			//新增车队、车队组行政区域
		,'motorcadeServeDistrictEntityDeleteList':me.getMotorcadeGroupServeDistrictEntityDeleteList()}}; 	//删除车队、车队组行政区域
    	/**
    	 * 若保存车队时，判断部门简称为空时，则提示
    	 */
    	if(orgAdministrativeInfoEntity.transTeam == 'Y'&&Ext.isEmpty(orgAdministrativeInfoEntity.orgSimpleName)){
    		Ext.Msg.alert(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tips'),baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.alertOrgSimpleName'));
    		button.setDisabled(false);
			return;
    	}
    	orgInfoPanel.getMyMask().show();//打开罩
    	var successFun = function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		//车队组服务营业区、行政区域的store
    		var motorcadeServeDistrictStore = me.getMotorcadeGroupInfoForm().getMotorcadeServeDistrict().getStore(),
    			motorcadeServeSalesAreaStore= me.getMotorcadeGroupInfoForm().getMotorcadeServeSalesArea().getStore();
    		//车队组
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.motorcadeEntity)){//首先先确保返回值了<!--下次修改注意下-->
				var motorcadeModel =  new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity(json.orgAdministrativeInfoVo.motorcadeEntity);//创建车队的model
				var motorcadeServeSalesAreaEntityList = json.orgAdministrativeInfoVo.motorcadeServeSalesAreaEntityList;
				var motorcadeServeDistrictEntityList = json.orgAdministrativeInfoVo.motorcadeServeDistrictEntityList;
				if(Ext.isEmpty(motorcadeServeSalesAreaEntityList)){//防止NULL
					motorcadeServeSalesAreaEntityList = [];
				}
				if(Ext.isEmpty(motorcadeServeDistrictEntityList)){
					motorcadeServeDistrictEntityList = [];
				} 
				motorcadeGroupInfoForm.motorcadeModel = motorcadeModel;
				motorcadeGroupInfoForm.motorcadeServeSalesAreaEntityList = motorcadeServeSalesAreaEntityList;
				motorcadeGroupInfoForm.motorcadeServeDistrictEntityList = motorcadeServeDistrictEntityList;
               	orgInfoPanel.getOrgMainInfoForm().motorcadeModel = motorcadeModel;
            	//先清空store ，再加载最新的store
               	motorcadeServeDistrictStore.removeAll();
               	motorcadeServeSalesAreaStore.removeAll();
               	motorcadeServeDistrictStore.loadData(motorcadeServeDistrictEntityList);
               	motorcadeServeSalesAreaStore.loadData(motorcadeServeSalesAreaEntityList);
               }
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		orgInfoPanel.getMyMask().hide();//关闭罩
    		me.closeFun();//所有的设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByMotorcadeGroup.action');
    	//保存车队组
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	},
	//设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('transTeam').setReadOnly(true); //‘是否车队组’不可编辑
    	me.getMotorcadeGroupInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
    	me.getMotorcadeGroupInfoForm().getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(true);
    	me.getMotorcadeGroupInfoForm().getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(true);//按钮设置不可用
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar=[{
			xtype : 'button', 
			width:50,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				var motorcadeGroupInfoForm =me.getMotorcadeGroupInfoForm();
				motorcadeGroupInfoForm.getForm().loadRecord(me.motorcadeModel);
				motorcadeGroupInfoForm.getForm().findField('parentOrgCode').setCombValue(me.motorcadeModel.get('parentOrgCodeName'),me.motorcadeModel.get('parentOrgCode'));
				motorcadeGroupInfoForm.getForm().findField('transferCenter').setCombValue(me.motorcadeModel.get('transferCenterName'),me.motorcadeModel.get('transferCenter'));
				motorcadeGroupInfoForm.getForm().findField('serveBillTerm').setCombValue(me.motorcadeModel.get('serveBillTermName'),me.motorcadeModel.get('serveBillTerm'));
				motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().removeAll();//先清除数据
				motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().add(me.motorcadeServeDistrictEntityList);//先清除数据
				motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();//再加载数据
				motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().add(me.motorcadeServeSalesAreaEntityList);//再加载原始数据
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeGroupInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveMotorcadeGroupInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveMotorcadeGroupInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('transTeam').setReadOnly(false); //‘是否车队组’可编辑
		    	me.getMotorcadeGroupInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		if(item.readOnly ==true){
		    			item.setReadOnly(false);
		    		}
		    	});
		    	me.getMotorcadeGroupInfoForm().getMotorcadeServeDistrict().getDockedItems()[0].items.items[0].setDisabled(false);
		    	me.getMotorcadeGroupInfoForm().getMotorcadeServeSalesArea().getDockedItems()[0].items.items[0].setDisabled(false);//按钮设置可用
			}
		}];
		me.items=[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherFleetGroup'),//是否车队组
            name      : 'transTeam',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var motorcadeGroupInfoForm = me.getMotorcadeGroupInfoForm();
            		if(newvalue){
            			//me.expand(); //展开form
            			motorcadeGroupInfoForm.show();//显示车队组设置FORM
                		motorcadeGroupInfoForm.motorcadeModel = orgMainInfoForm.motorcadeModel;//设置数据
                		motorcadeGroupInfoForm.getForm().loadRecord(orgMainInfoForm.motorcadeModel);//加载数据
                		motorcadeGroupInfoForm.getForm().findField('parentOrgCode').setCombValue(orgMainInfoForm.motorcadeModel.get('parentOrgCodeName'),orgMainInfoForm.motorcadeModel.get('parentOrgCode'));
                		motorcadeGroupInfoForm.getForm().findField('transferCenter').setCombValue(orgMainInfoForm.motorcadeModel.get('transferCenterName'),orgMainInfoForm.motorcadeModel.get('transferCenter'));
                		motorcadeGroupInfoForm.getForm().findField('serveBillTerm').setCombValue(orgMainInfoForm.motorcadeModel.get('serveBillTermName'),orgMainInfoForm.motorcadeModel.get('serveBillTerm'));
                		motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();
                		motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().removeAll();
                		motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().add(motorcadeGroupInfoForm.motorcadeServeSalesAreaEntityList);
                		motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().add(motorcadeGroupInfoForm.motorcadeServeDistrictEntityList);
                	}else{
                		if(!Ext.isEmpty(orgMainInfoForm.motorcadeModel)&&!Ext.isEmpty(orgMainInfoForm.motorcadeModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheAdministrativeOrganizationOfFleetGroup'),function(e){//是否要删除行政组织的车队组信息？
                				if(e=='yes'){//询问是否删除，是则发送请求foss.baseinfo.wantRemoveTheAdministrativeOrganizationOfFleetGroup
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.transTeam = 'N';
                					var params = {'orgAdministrativeInfoVo':{'motorcadeEntity':{'code':orgMainInfoForm.motorcadeModel.get('code')}
                					,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废车队组相关信息
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						orgMainInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();//将空的数据设置到外场model
                                		motorcadeGroupInfoForm.motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                                		motorcadeGroupInfoForm.getForm().loadRecord(new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity());//加载空数据
                                		var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                                		var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity;
                                		me.closeFun();//所有的设置为不可编辑
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteMotorcade.action');//作废请求
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			var motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                			orgMainInfoForm.motorcadeModel = motorcadeModel;
                   			motorcadeGroupInfoForm.motorcadeModel = motorcadeModel;
                   			motorcadeGroupInfoForm.getForm().loadRecord(motorcadeModel);
                   			motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();
                   			motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().removeAll();
                		};
                	}
                }
            }
		},me.getMotorcadeGroupInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 *------------------------车队调度组信息主界面(checkbox+controlGroupInfoForm)---------------------- 
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ControlGroupMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.schedulingGroup'),//调度组信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:200,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//调度组信息
	controlGroupInfoForm:null,
	getControlGroupInfoForm:function(){
		if(Ext.isEmpty(this.controlGroupInfoForm)){
    		this.controlGroupInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ControlGroupInfoForm');
    	}
    	return this.controlGroupInfoForm;
	},
	 //获取车队调度组信息
    getControlGroupInfo:function(){
    	var me = this;
    	//行政组织主信息Form
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	var controlGroupInfoForm = me.getControlGroupInfoForm();
    	if(controlGroupInfoForm.getForm().isValid()){
    		if(Ext.isEmpty(controlGroupInfoForm.motorcadeModel)){
    			controlGroupInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
    		}
    		controlGroupInfoForm.getForm().updateRecord(controlGroupInfoForm.motorcadeModel);//加载数据
    		controlGroupInfoForm.motorcadeModel.set('code',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));//设置行政组织code}
    		controlGroupInfoForm.motorcadeModel.set('dispatchTeam','Y');//将是否车队调度组设置为是
    		return controlGroupInfoForm.motorcadeModel.data;
    	}else{
    		return null;
    	}
    },
	//保存调度组信息
    saveControlGroupInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var controlGroupInfoForm =me.getControlGroupInfoForm();
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
    	//车队调度组
    	if(orgAdministrativeInfoEntity.dispatchTeam=='Y'){
    		motorcadeEntity = me.getControlGroupInfo();
    		if(Ext.isEmpty(motorcadeEntity)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillCompleteFleetSchedulingGroupGroupInformation'));//请填写完整车队调度组组信息！
    			button.setDisabled(false);  			
    			return;
    		}
    	}else{
    		//若是是否车队组没有勾选，提示用户勾选，然后return
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsFleetSchedulingGroup'));//请勾選是否車隊調度組！		
    		button.setDisabled(false);
    		return;
    	}
    	orgInfoPanel.getMyMask().show();//打开罩
    	var params ={'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity,'motorcadeEntity':motorcadeEntity}};
    	var successFun =function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.motorcadeEntity)){//首先先确保返回值了
				var motorcadeModel =  new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity(json.orgAdministrativeInfoVo.motorcadeEntity);//创建车队的model
				 if(orgAdministrativeInfoEntity.dispatchTeam=='Y'){//如果是车队调度组组
					 controlGroupInfoForm.motorcadeModel = motorcadeModel;
                }
				orgInfoPanel.getOrgMainInfoForm().motorcadeModel = motorcadeModel;
			}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		orgInfoPanel.getMyMask().hide();//关闭罩
    		me.closeFun();//设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByControlGroup.action');
    	//保存车队调度组
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
    //设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('dispatchTeam').setReadOnly(true);//设置‘是否调度组’不可编辑
    	me.getControlGroupInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar=[{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getControlGroupInfoForm().getForm().findField('parentOrgCode').setCombValue(me.motorcadeModel.get('parentOrgCodeName'),me.motorcadeModel.get('parentOrgCode'));
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveControlGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveControlGroupInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveControlGroupInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveControlGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveControlGroupInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('dispatchTeam').setReadOnly(false);//设置‘是否调度组’可编辑
		    	me.getControlGroupInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		if(item.readOnly == true){
		    			item.setReadOnly(false);
		    		}
		    	});
			}
		}];
		me.items=[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherFleetSchedulingGroup'),//是否车队调度组
            name      : 'dispatchTeam',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
            		var controlGroupInfoForm = me.getControlGroupInfoForm();
            		if(newvalue){
            			controlGroupInfoForm.show();//显示车队调度组设置FORM
            			controlGroupInfoForm.motorcadeModel = orgMainInfoForm.motorcadeModel;//设置数据
            			controlGroupInfoForm.getForm().findField('parentOrgCode').setCombValue(orgMainInfoForm.motorcadeModel.get('parentOrgCodeName'),orgMainInfoForm.motorcadeModel.get('parentOrgCode'));                		
                	}else{
                		if(!Ext.isEmpty(orgMainInfoForm.motorcadeModel)&&!Ext.isEmpty(orgMainInfoForm.motorcadeModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantDeleteAdministrativeOrganizationOfTheFleetSchedulingInformation'),function(e){//是否要删除行政组织的车队组信息？
                				if(e=='yes'){//询问是否删除，是则发送请求
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.dispatchTeam = 'N';
                					var params = {'orgAdministrativeInfoVo':{'motorcadeEntity':{'code':orgMainInfoForm.motorcadeModel.get('code')}
                						,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废车队组相关信息
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						orgMainInfoForm.motorcadeModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();//将空的数据设置到外场model
                                		controlGroupInfoForm.motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                                		controlGroupInfoForm.getForm().loadRecord(new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity());
                                		var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                                		var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity; 
                                		me.closeFun();//所有的设置为不可编辑
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteMotorcade.action');
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			var motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                			orgMainInfoForm.motorcadeModel = motorcadeModel;
                   			controlGroupInfoForm.motorcadeModel = motorcadeModel;
                   			controlGroupInfoForm.getForm().loadRecord(motorcadeModel);
                		};
                	}
                }
            }
		},me.getControlGroupInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 *------------------------派送排单信息主界面(checkbox+DeliverScheduleInfoForm)----------------------
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.DeliverScheduleMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.rowSingleSectorInformation'),//排单部门信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:200,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//保存排单部门信息
    saveDeliverScheduleInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		button.setDisabled(false);
    		return;
    	}
    	//若是否派送派单没有勾选，提示用户
    	if(!me.getForm().findField('isDeliverSchedule').getValue()){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsDeliverSchedule'));//请勾选是否派送排单！
    		button.setDisabled(false);
    		return;
    	}
    	var params ={'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity}};
    	var successFun =function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.closeFun();//所有的设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByOrg.action');
    	//保存排单部门信息
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
	//排单部门信息
	deliverScheduleInfoForm:null,
	getDeliverScheduleInfoForm:function(){
		if(Ext.isEmpty(this.deliverScheduleInfoForm)){
    		this.deliverScheduleInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.DeliverScheduleInfoForm');
    	}
    	return this.deliverScheduleInfoForm;
	},
	 //设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('isDeliverSchedule').setReadOnly(true);//设置‘是否派送排单’为不可编辑的
    	me.getDeliverScheduleInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
	},
	constructor :function(config){
		var me =this,cfg =Ext.apply({},config);
		me.fbar = [{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveDeliverScheduleInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveDeliverScheduleInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveDeliverScheduleInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveDeliverScheduleInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveDeliverScheduleInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('isDeliverSchedule').setReadOnly(false);//设置‘是否派送排单’为可编辑的
		    	me.getDeliverScheduleInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		if(item.readOnly == true){
		    			item.setReadOnly(false);
		    		}
		    	});
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherDeliverySingleRowOf'),//是否派送排单
            name      : 'isDeliverSchedule',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var deliverScheduleInfoForm = me.getDeliverScheduleInfoForm();
            		if(newvalue){
            			deliverScheduleInfoForm.show();//显示派送排单设置FORM
            			deliverScheduleInfoForm.orgAdministrativeInfoModel = orgMainInfoForm.orgAdministrativeInfoModel;//设置数据
                		deliverScheduleInfoForm.getForm().findField('deliverOutfield').setCombValue(orgMainInfoForm.orgAdministrativeInfoModel.get('deliverOutfieldName'),orgMainInfoForm.orgAdministrativeInfoModel.get('deliverOutfield'));//加载数据
                	}else{
                		deliverScheduleInfoForm.getForm().reset();
                		deliverScheduleInfoForm.getForm().findField('deliverOutfield').setValue(null);
                		deliverScheduleInfoForm.getForm().findField('deliverOutfield').setRawValue(null);
                		deliverScheduleInfoForm.getForm().updateRecord(orgMainInfoForm.orgAdministrativeInfoModel);//加载数据
                		deliverScheduleInfoForm.orgAdministrativeInfoModel = orgMainInfoForm.orgAdministrativeInfoModel;//设置数据
                	} 
                }
            }
		},me.getDeliverScheduleInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 *------------------------理货部门信息主界面(checkbox+ArrangeGoodsInfoForm)----------------------
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ArrangeGoodsMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theTallySectorInformation'),//理货部门信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:200,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//理货部门信息
	arrangeGoodsInfoForm:null,
	getArrangeGoodsInfoForm:function(){
		if(Ext.isEmpty(this.arrangeGoodsInfoForm)){
    		this.arrangeGoodsInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ArrangeGoodsInfoForm');
    	}
    	return this.arrangeGoodsInfoForm;
	},
	 //保存理货部门信息
    saveArrangeGoodsInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		button.setDisabled(false);
    		return;
    	}
    	//若是否理貨没有勾选，提示用户
    	if(!me.getForm().findField('isArrangeGoods').getValue()){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsArrangeGoods'));//请勾选是否派送排单！
    		button.setDisabled(false);
    		return;
    	}
    	var params ={'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity}};
    	var successFun =function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.closeFun();//所有的设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByOrg.action');
    	//保存理货部门信息
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
    //设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('isArrangeGoods').setReadOnly(true); //设置是否理货不可编辑
    	me.getArrangeGoodsInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar =[{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getArrangeGoodsInfoForm().getForm().loadRecord(me.orgAdministrativeInfoModel);
				me.getArrangeGoodsInfoForm().getForm().findField('arrangeOutfield').setCombValue(me.orgAdministrativeInfoModel.get('arrangeOutfieldName'),me.orgAdministrativeInfoModel.get('arrangeOutfield'));
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveArrangeGoodsInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('isArrangeGoods').setReadOnly(false); //设置是否理货可编辑
		    	me.getArrangeGoodsInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		if(item.readOnly == true){
		    			item.setReadOnly(false);
		    		}
		    	});
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheTally'),//是否理货
            name      : 'isArrangeGoods',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var arrangeGoodsInfoForm = me.getArrangeGoodsInfoForm();
            		if(newvalue){
            			arrangeGoodsInfoForm.show();//显示派送排单设置FORM
            			arrangeGoodsInfoForm.orgAdministrativeInfoModel = orgMainInfoForm.orgAdministrativeInfoModel;//设置数据
                		arrangeGoodsInfoForm.getForm().loadRecord(orgMainInfoForm.orgAdministrativeInfoModel);//加载数据
                		arrangeGoodsInfoForm.getForm().findField('arrangeOutfield').setCombValue(orgMainInfoForm.orgAdministrativeInfoModel.get('arrangeOutfieldName'),orgMainInfoForm.orgAdministrativeInfoModel.get('arrangeOutfield'));
                	}else{
                		arrangeGoodsInfoForm.getForm().reset();
                		arrangeGoodsInfoForm.getForm().findField('arrangeOutfield').setValue(null);
                		arrangeGoodsInfoForm.getForm().findField('arrangeOutfield').setRawValue(null);
                		arrangeGoodsInfoForm.getForm().updateRecord(orgMainInfoForm.orgAdministrativeInfoModel);//加载数据
                		arrangeGoodsInfoForm.orgAdministrativeInfoModel = orgMainInfoForm.orgAdministrativeInfoModel;//设置数据
                	}
                }
            }
		},me.getArrangeGoodsInfoForm()];
		me.callParent([cfg]);
	}
});

/**
 *------------------------保安组信息主界面(checkbox+ArrangeGoodsInfoForm)----------------------
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.securityTfrMotorcadeInfo'),//理货部门信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:200,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//保安組信息
	securityTfrMotorcadeInfoForm:null,
	getSecurityTfrMotorcadeInfoForm:function(){
		if(Ext.isEmpty(this.securityTfrMotorcadeInfoForm)){
    		this.securityTfrMotorcadeInfoForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeInfoForm');
    	}
    	return this.securityTfrMotorcadeInfoForm;
	},
	
		 //获取保安组信息
    getSecurityTfrMotorcadeInfo:function(){
    	var me = this;
    	//行政组织主信息Form
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	var securityTfrMotorcadeInfoForm = me.getSecurityTfrMotorcadeInfoForm();
    	if(securityTfrMotorcadeInfoForm.getForm().isValid()){
    		if(Ext.isEmpty(securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel)){
    			securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity();
    		}
    		securityTfrMotorcadeInfoForm.getForm().updateRecord(securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel);//加载数据
    		securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel.set('securityCode',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));//设置行政组织code}
			securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel.set('transcenterCode',securityTfrMotorcadeInfoForm.getForm().findField("tallySectorServiceOutfield").getValue());
			securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel.set('motorcadeCode',securityTfrMotorcadeInfoForm.getForm().findField("tallySectorServicemotorCade").getValue());
			securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel.set('isSecurity','Y');//将是否保安组设置为是
    		return securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel.data;
    	}else{
    		return null;
    	}
    },
	 //保存保安组信息
    saveSecurityTfrMotorcadeInfo:function(button){
    	button.setDisabled(true);
    	var me =this;
    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
    		button.setDisabled(false);
    		return;
    	}
				
		//车队调度组
    	if(orgAdministrativeInfoEntity.isSecurity=='Y'){
    		securityTfrMotorcadeInfo = me.getSecurityTfrMotorcadeInfo();
    		if(Ext.isEmpty(securityTfrMotorcadeInfo)){
    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillSecurityTfrMotorcadeInformation'));//请填写完整保安组信息！
    			button.setDisabled(false);  			
    			return;
    		}
    	}else{
    		//若是是否保安组没有勾选，提示用户勾选，然后return
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledIsSecurityTfrMotorcadeInfo'));//请勾選是保安組！		
    		button.setDisabled(false);
    		return;
    	}
  	//若是否保安组没有勾选，提示用户
    	if(!me.getForm().findField('isSecurity').getValue()){
    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFilledisSecurityTfrMotorcade'));//请勾选是否保安组！
    		button.setDisabled(false);
    		return;
    	}
		
		// var params ={'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity,'motorcadeEntity':motorcadeEntity}};
		
    	var params ={'orgAdministrativeInfoVo':{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity,'securityTfrMotorcadeEntity':securityTfrMotorcadeInfo}};
    	var successFun =function(json){
    		button.setDisabled(false);
    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
    		}
    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
    		node.raw.entity = orgAdministrativeInfoEntity;
    		baseinfo.showInfoMes(json.message);
    		me.closeFun();//所有的设置为不可编辑
    	}
    	var failureFun = function(json){
    		button.setDisabled(false);
    		if(Ext.isEmpty(json)){
				baseinfo.showErrorMes('请求超时');//请求超时
			}else{
				baseinfo.showErrorMes(json.message);
			}
    	};
    	var url = baseinfo.realPath('updateSecurityTfrMotorcadeInfoByOrg.action');
    	//保存理货部门信息
    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
    },
    //设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('isSecurity').setReadOnly(true); //设置是否理货不可编辑
    	me.getSecurityTfrMotorcadeInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar =[{
			xtype : 'button', 
			width:70,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getSecurityTfrMotorcadeInfoForm().getForm().loadRecord(me.orgAdministrativeInfoModel);
				me.getSecurityTfrMotorcadeInfoForm().getForm().findField('tallySectorServiceOutfield').setCombValue(me.orgAdministrativeInfoModel.get('transcenterName'),me.orgAdministrativeInfoModel.get('transcenterCode'));
				me.getSecurityTfrMotorcadeInfoForm().getForm().findField('tallySectorServicemotorCade').setCombValue(me.orgAdministrativeInfoModel.get('motorcadeNmae'),me.orgAdministrativeInfoModel.get('motorcadeCode'));
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler:function(){
				me.saveSecurityTfrMotorcadeInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveArrangeGoodsInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('isSecurity').setReadOnly(false); //设置是否理货可编辑
		    	me.getSecurityTfrMotorcadeInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		if(item.readOnly == true){
		    			item.setReadOnly(false);
		    		}
		    	});
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.securityTfrMotorcade'),//是否理货
            name      : 'isSecurity',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var orgMainInfoForm =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var securityTfrMotorcadeInfoForm = me.getSecurityTfrMotorcadeInfoForm();
            		if(newvalue){
            			securityTfrMotorcadeInfoForm.show();//显示派送排单设置FORM
            			securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel = orgMainInfoForm.securityTfrMotorcadeInfoModel;//设置数据
                		// securityTfrMotorcadeInfoForm.getForm().loadRecord(orgMainInfoForm.securityTfrMotorcadeModel);//加载数据
                		securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServiceOutfield').setCombValue(orgMainInfoForm.securityTfrMotorcadeInfoModel.get('transcenterName'),orgMainInfoForm.securityTfrMotorcadeInfoModel.get('transcenterCode'));
						securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServicemotorCade').setCombValue(orgMainInfoForm.securityTfrMotorcadeInfoModel.get('motorcadeNmae'),orgMainInfoForm.securityTfrMotorcadeInfoModel.get('motorcadeCode'));
                	}else{

                		if(!Ext.isEmpty(orgMainInfoForm.securityTfrMotorcadeInfoModel)&&!Ext.isEmpty(orgMainInfoForm.securityTfrMotorcadeInfoModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheSecurityTfrMotorcade'),function(e){//是否要删除行政组织的车队组信息？
                				if(e=='yes'){//询问是否删除，是则发送请求foss.baseinfo.wantRemoveTheAdministrativeOrganizationOfFleetGroup
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.isSecurity = 'N';
                					var params = {'orgAdministrativeInfoVo':{'securityTfrMotorcadeEntity':{'securityCode':orgMainInfoForm.securityTfrMotorcadeInfoModel.get('securityCode')}
                					,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废保安组相关信息
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServiceOutfield').setCombValue(null,null);
										securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServicemotorCade').setCombValue(null,null);
//                						orgMainInfoForm.securityTfrMotorcadeInfoModel = new Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity();//将空的数据设置到外场model
//                						securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel = new  Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity();
//                						securityTfrMotorcadeInfoForm.getForm().loadRecord(new  Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeEntity());//加载空数据
                                		var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                                		var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity;
                                		me.closeFun();//所有的设置为不可编辑
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteSecurityTfrMotorcade.action');//作废请求
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			

                			// var motorcadeModel = new  Foss.baseinfo.orgAdministrativeInfo.MotorcadeEntity();
                			// orgMainInfoForm.motorcadeModel = motorcadeModel;
                   			// motorcadeGroupInfoForm.motorcadeModel = motorcadeModel;
                   			// motorcadeGroupInfoForm.getForm().loadRecord(motorcadeModel);
                   			// motorcadeGroupInfoForm.getMotorcadeServeSalesArea().getStore().removeAll();
                   			// motorcadeGroupInfoForm.getMotorcadeServeDistrict().getStore().removeAll();
							
							securityTfrMotorcadeInfoForm.getForm().reset();
							securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServiceOutfield').setValue(null);
							securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServiceOutfield').setRawValue(null);
							securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServicemotorCade').setValue(null);
							securityTfrMotorcadeInfoForm.getForm().findField('tallySectorServicemotorCade').setRawValue(null);
							// securityTfrMotorcadeInfoForm.getForm().updateRecord(orgMainInfoForm.securityTfrMotorcadeModel);//加载数据
							securityTfrMotorcadeInfoForm.securityTfrMotorcadeInfoModel = orgMainInfoForm.securityTfrMotorcadeInfoModel;//设置数据
                		};
                	}
                }
            }
		},me.getSecurityTfrMotorcadeInfoForm()];
		me.callParent([cfg]);
	}
});



/**
 *------------------------集中开单组信息主界面(checkbox+ArrangeGoodsInfoForm)---------------------- 
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.BillingGroupMainForm',{
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.getBillingGroupInfo'),//集中开单组信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
	height:300,
	autoScroll:true,
	margin : '0 5 5 5',
	layout:{
			type:'table',
			columns: 1
	},
	defaults : {
	    	colspan : 1,
	    	margin : '5 5 5 5'
	},
	//获取集中开单组外场信息
    getbillingGroupTransFerInfo:function(){
    	var me =this;
    	var billingGroupNameInfoForm = me.getBillingGroupNameInfoForm();
    	//获取主信息form
    	var orgMainInfoForm =me.up('panel').getOrgMainInfoForm();
    	if(billingGroupNameInfoForm.getForm().isValid()){
    		if(Ext.isEmpty(billingGroupNameInfoForm.billingGroupTransFerModel)){
    			billingGroupNameInfoForm.billingGroupTransFerModel = new Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity();
    		}
    		//新增时 获取表单中开单组的编码和名称(若是修改的话，开单组的编码就不会为空)
    		var billingGroupCode =billingGroupNameInfoForm.billingGroupTransFerModel.get('billingGroupCode');
    		var billingGroupName =billingGroupNameInfoForm.billingGroupTransFerModel.get('billingGroupName');
    		//若两个值为空
    		if(Ext.isEmpty(billingGroupCode)||Ext.isEmpty(billingGroupName)){
    			//给其设置值
    			billingGroupNameInfoForm.billingGroupTransFerModel.set('billingGroupCode',orgMainInfoForm.orgAdministrativeInfoModel.get('code'));
    			billingGroupNameInfoForm.billingGroupTransFerModel.set('billingGroupName',orgMainInfoForm.orgAdministrativeInfoModel.get('name'));
    		}
    		billingGroupNameInfoForm.getForm().updateRecord(billingGroupNameInfoForm.billingGroupTransFerModel);//加载数据
    		//设置姓名
    		billingGroupNameInfoForm.billingGroupTransFerModel.set('transFerName',billingGroupNameInfoForm.getForm().findField('transFerCode').getRawValue());
    		billingGroupNameInfoForm.billingGroupTransFerModel.set('billingGroup','Y');//将是否集中开单组设置为是
    		return billingGroupNameInfoForm.billingGroupTransFerModel.data;
    	}else{
    		return null;
    	}	
    },
    //获取集中开单组营业部新增信息
    getBillingGroupServeSalesDeptEntityAddList:function(){
    	var me =this;
    	var billingGroupNameInfoForm=me.getBillingGroupNameInfoForm();
    	var billingGroupServeSalesDeptEntityAddModelList = billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore().getNewRecords( );
    	var billingGroupServeSalesDeptEntityAddList = new Array();
    	for(var i = 0;i<billingGroupServeSalesDeptEntityAddModelList.length;i++){
    		billingGroupServeSalesDeptEntityAddList.push(billingGroupServeSalesDeptEntityAddModelList[i].data);
    	}
    	return billingGroupServeSalesDeptEntityAddList;
    },
    ///获取集中开单组营业部删除信息
    getBillingGroupServeSalesDeptEntityDeleteList:function(){
    	var me =this;
    	var billingGroupNameInfoForm=me.getBillingGroupNameInfoForm();
    	var billingGroupServeSalesDeptEntityDeleteModelList = billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore().getRemovedRecords( );
    	var billingGroupServeSalesDeptEntityDeleteList = new Array();
    	for(var i = 0;i<billingGroupServeSalesDeptEntityDeleteModelList.length;i++){
    		billingGroupServeSalesDeptEntityDeleteList.push(billingGroupServeSalesDeptEntityDeleteModelList[i].data);
    	}
    	return billingGroupServeSalesDeptEntityDeleteList;
    },
	//保存信息
	saveBillingGroupInfo:function(button){
	    	button.setDisabled(true);
	    	var me =this;
	    	var orgInfoPanel =Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id'); //获取主面板
	    	var orgAdministrativeInfoEntity = orgInfoPanel.getOrgInfo();
	    	if(Ext.isEmpty(orgAdministrativeInfoEntity)){
	    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseCompleteOrgInformation'));
	    		button.setDisabled(false);
	    		return;
	    	}
	    	//集中开单组外场
	    	var billingGroupTransFerEntity =null;
	    	//集中开单组form
	    	var billingGroupNameInfoForm = me.getBillingGroupNameInfoForm();
	    	if(orgAdministrativeInfoEntity.billingGroup=='Y'){
	    		billingGroupTransFerEntity =me.getbillingGroupTransFerInfo();
	    		if(Ext.isEmpty(billingGroupTransFerEntity)){
	    			baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillCompleteBillingGroupTransFerInformation'));//请填写完整集中开单组外场信息！
	    			button.setDisabled(false);  			
	    			return;
	    		}
	    	}else{
	    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseFillIsBillingGroupInfo'));//请勾选是否集中开单组
	    		button.setDisabled(false);
	    		return;
	    	}
	    	var params ={'orgAdministrativeInfoVo':
	    		{'orgAdministrativeInfoEntity':orgAdministrativeInfoEntity
	    		,'billingGroupTransFerEntity':billingGroupTransFerEntity
	    		,'salesBillingGroupEntityAddList':me.getBillingGroupServeSalesDeptEntityAddList() //集中开单组营业部新增信息
	    		,'salesBillingGroupEntityDeleteList':me.getBillingGroupServeSalesDeptEntityDeleteList()}};//集中开单组删除信息
	    	var successFun=function(json){
	    		button.setDisabled(false);
	    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity)){//行政业务组织不为空
	    			orgAdministrativeInfoEntity =json.orgAdministrativeInfoVo.orgAdministrativeInfoEntity;
	    		}
	    		//集中开单组外场
	    		if(!Ext.isEmpty(json.orgAdministrativeInfoVo.billingGroupTransFerEntity)){
	    			var billingGroupTransFerModel = new Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity(json.orgAdministrativeInfoVo.billingGroupTransFerEntity);
	    			var billingGroupServeSalesDeptEntityList = json.orgAdministrativeInfoVo.salesBillingGroupEntityList;// 集中开单组服务营业部
	    			//防止billingGroupServeSalesDeptEntityList 为空
	    			if(Ext.isEmpty(billingGroupServeSalesDeptEntityList)){
	    				billingGroupServeSalesDeptEntityList =[];
	    			}
	    			billingGroupNameInfoForm.billingGroupTransFerModel =billingGroupTransFerModel;//设置开单组外场属性
	    			billingGroupNameInfoForm.billingGroupServeSalesDeptEntityList =billingGroupServeSalesDeptEntityList; //设置开单组服务营业部的数据
	    			//先清空grid 中的值 ，保存成功之后然后再重新加载数据到grid中
	    			var store =billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore();
	    			store.removeAll();
    				store.loadData(billingGroupNameInfoForm.billingGroupServeSalesDeptEntityList);
    				
	    			orgInfoPanel.getOrgMainInfoForm().billingGroupTransFerModel =billingGroupTransFerModel;
	    			orgInfoPanel.getOrgMainInfoForm().billingGroupServeSalesDeptEntityList =billingGroupServeSalesDeptEntityList;
	    		}
	    		var treeStore = orgInfoPanel.up().getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
	    		var node = treeStore.getNodeById(orgAdministrativeInfoEntity.uumsId);
	    		node.raw.entity = orgAdministrativeInfoEntity;
	    		baseinfo.showInfoMes(json.message);
	    		
	    		me.closeFun();//所有的设置为不可编辑
	    	}
	    	var failureFun = function(json){
	    		button.setDisabled(false);
	    		if(Ext.isEmpty(json)){
					baseinfo.showErrorMes('请求超时');//请求超时
				}else{
					baseinfo.showErrorMes(json.message);
				}
	    	};
	    	var url = baseinfo.realPath('updateOrgAdministrativeInfoByBillingGroupTransFer.action');
	    	//保存集中开单组外场
	    	baseinfo.requestJsonAjax(url,params,successFun,failureFun);
	    },
	//集中开单组信息
	billingGroupNameInfoForm:null,
	getBillingGroupNameInfoForm:function(){
		if(Ext.isEmpty(this.billingGroupNameInfoForm)){
			this.billingGroupNameInfoForm =Ext.create('Foss.baseinfo.orgAdministrativeInfo.BillingGroupNameInfoForm');
		}
		return this.billingGroupNameInfoForm;
	},
	//设置form为不可编辑
	closeFun:function(){
		var me =this;
		var orgMainInfoForm = me.up('panel').getOrgMainInfoForm();
		orgMainInfoForm.closeFun();
		me.getForm().findField('billingGroup').setReadOnly(true);
    	me.getBillingGroupNameInfoForm().getForm().getFields( ).each(function(item){//设置不可编辑
    		if(item.readOnly == false){
				item.setReadOnly(true);
			}
    	});
    	me.getBillingGroupNameInfoForm().getBillingGroupServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(true);
	},
	constructor:function(config){
		var me =this, cfg =Ext.apply({},config);
		me.fbar =[{
			xtype : 'button', 
			width:50,
			hidden:true,
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reset'),//重置
			handler : function() {
				me.getBillingGroupNameInfoForm().getForm().findField('transFerCode').setCombValue(me.billingGroupTransFerModel.get('transFerName'),me.billingGroupTransFerModel.get('transFerCode'));
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.save'),//保存
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBillingGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBillingGroupInfoButton'),
			cls:'yellow_button',
			handler:function(){
				me.saveBillingGroupInfo(this);
			}
		},{
			xtype:'button',
			width:80,
			margin : '5 5 0 5',
			disabled:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBillingGroupInfoButton'),
			hidden:!baseinfo.orgAdministrativeInfo.isPermission('queryOrgBiz/saveBillingGroupInfoButton'),
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.update'),//修改
			cls:'yellow_button',
			handler:function(){
				//判断没有选中一个部门时，是不让其点击修改
				if(Ext.isEmpty(me.up('panel').getOrgMainInfoForm().orgAdministrativeInfoModel)){
		    		baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectDepartment'));//请选择一个部门！
		    		return;
		    	};
				me.getForm().findField('billingGroup').setReadOnly(false);
		    	me.getBillingGroupNameInfoForm().getForm().getFields( ).each(function(item){//设置可编辑
		    		item.setReadOnly(false);
		    	});
		    	me.getBillingGroupNameInfoForm().getBillingGroupServeSalesDept().getDockedItems()[0].items.items[0].setDisabled(false);
			}
		}];
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.centralizedBillingGroup'),//是否集中开单组
            name      : 'billingGroup',
            columnWidth:0.25,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		//先获取主信息Form
                	var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var  billingGroupNameInfoForm= me.getBillingGroupNameInfoForm();
            		if(newvalue){
            			billingGroupNameInfoForm.show();//显示集中开单组设置FORM
            			billingGroupNameInfoForm.billingGroupTransFerModel = orgMainInfoForm.billingGroupTransFerModel;//设置数据
            			billingGroupNameInfoForm.getForm().findField('transFerCode').setCombValue(orgMainInfoForm.billingGroupTransFerModel.get('transFerName'),orgMainInfoForm.billingGroupTransFerModel.get('transFerCode'));                		
            			billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore().removeAll();
            			billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore().add(billingGroupNameInfoForm.billingGroupServeSalesDeptEntityList);
            		}else{
                		if(!Ext.isEmpty(orgMainInfoForm.billingGroupTransFerModel)&&!Ext.isEmpty(orgMainInfoForm.billingGroupTransFerModel.get('id'))){
                			baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantDeletecentralizedBillingGroupInformation'),function(e){//是否要删除行政组织的集中开单组信息？
                				if(e=='yes'){//询问是否删除，是则发送请求
                					var orgInfoEntity = orgMainInfoForm.orgAdministrativeInfoModel.data;
                					orgInfoEntity.billingGroup = 'N';
                					var params = {'orgAdministrativeInfoVo':{'billingGroupTransFerEntity':{'billingGroupCode':orgMainInfoForm.billingGroupTransFerModel.get('billingGroupCode')}
                						,'orgAdministrativeInfoEntity':orgInfoEntity}};//作废集中开单组外场相关信息
                					var successFun = function(json){
                						baseinfo.showInfoMes(json.message);
                						var billingGroupTransFerModel = new  Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity();
                						orgMainInfoForm.billingGroupTransFerModel = billingGroupTransFerModel;
                               			billingGroupNameInfoForm.billingGroupTransFerModel = billingGroupTransFerModel;
                               			billingGroupNameInfoForm.getForm().loadRecord(billingGroupTransFerModel);//加载数据
                               			var treeStore = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgTreeSearchPanel().getDepartmentTreePanle().getStore();
                                		var node = treeStore.getNodeById(orgInfoEntity.uumsId);
                                		node.raw.entity = orgInfoEntity;
                                		me.closeFun();//所有的设置为不可编辑
                					};
                					var failureFun = function(json){
                						if(Ext.isEmpty(json)){
                							baseinfo.showErrorMes('请求超时');//请求超时
                						}else{
                							baseinfo.showErrorMes(json.message);
                						}
                					};
                					var url = baseinfo.realPath('deleteBillingGroupTransFer.action');
                					baseinfo.requestJsonAjax(url,params,successFun,failureFun);
                				}else{
                					checkbox.setValue(true);
                				}
                			})
                		}else{
                			var billingGroupTransFerModel = new  Foss.baseinfo.orgAdministrativeInfo.BillingGroupTransFerEntity();
                			orgMainInfoForm.billingGroupTransFerModel = billingGroupTransFerModel;
                   			//billingGroupNameInfoForm.hide();
                   			billingGroupNameInfoForm.billingGroupTransFerModel = billingGroupTransFerModel;
                   			billingGroupNameInfoForm.getForm().loadRecord(billingGroupTransFerModel);//加载数据
                   			billingGroupNameInfoForm.getBillingGroupServeSalesDept().getStore().removeAll();
                		}	
                	}
                }
            }
		},me.getBillingGroupNameInfoForm()];
		me.callParent([cfg]);
	}
});
/**
 * @description 营业部信息查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentInfoPanel', {
	extend:'Ext.panel.Panel',  
	hideMode: 'visibility',
	saleDepartmentModel:null,//营业部model
	salesProductEntityList:[],//营业部产品信息
	salesMotorcadeEntityList:[],//营业部车队信息
	centralizedBillingGroupList:[],//全部集中开单组信息
	selectedCentralizedBillingGroupList:[],//已选集中开单组信息
	departureConSelectData:[],//出发可选产品
    arriveConSelectData:[],//到达可选产品
    motorcadeConSelectData:[],//可选车队
    departureSelectedData:[],//出发已选产品
    arriveSelectedData:[],//到达已选产品
    motorcadeSelectedData:[],//已选车队
    allCentralizedBillingGroupData:[],//可选集中开单组
    selectedCentralizedBillingGroupData:[],//已选集中开单组
    
	//title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.businessDepartment'),//营业部信息
    height:1800,
    layout:{
		type:'table',
		columns: 1
		
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 5 5'
    },
    
    //提货信息form
    deliveryInformationForm:null,
	getDeliveryInformationForm:function(){
		if(Ext.isEmpty(this.deliveryInformationForm)){
    		this.deliveryInformationForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.DeliveryInformationForm');
    	}
    	return this.deliveryInformationForm;
	},
    //营业部基本信息form
	saleDepartmentPartForm:null,
	getSaleDepartmentPartForm:function(){
		if(Ext.isEmpty(this.saleDepartmentPartForm)){
    		this.saleDepartmentPartForm = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentPartForm');
    		this.saleDepartmentPartForm.getForm().findField('openingDate').setReadOnly(true);
    	}
    	return this.saleDepartmentPartForm;
	},
	//营业部中部信息form（出发）
	saleDepartmentCerterPanel:null,
	getSaleDepartmentCerterPanel:function(){
		if(Ext.isEmpty(this.saleDepartmentCerterPanel)){
    		this.saleDepartmentCerterPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentCerterPanel');
    	}
    	return this.saleDepartmentCerterPanel;
	},
	//营业部下部信息form（到达）
	saleDepartmentBottomPanel:null,
	getSaleDepartmentBottomPanel:function(){
		if(Ext.isEmpty(this.saleDepartmentBottomPanel)){
			this.saleDepartmentBottomPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentBottomPanel');
		}
		return this.saleDepartmentBottomPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);

		me.items = [me.getSaleDepartmentPartForm(),me.getSaleDepartmentCerterPanel(),
		            me.getSaleDepartmentBottomPanel(),me.getDeliveryInformationForm()];
		me.callParent([cfg]);
	}
});
/**
 * @description 营业部部分信息
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentPartForm', {
	extend:'Ext.form.Panel', 
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.businessDepartmentBaseInfo'),
    height:459,
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 5 5'
    },
    //服务于营业部的车队
    salesBeServedMotercade:null,
    getSalesBeServedMotercade:function(){
    	if(Ext.isEmpty(this.salesBeServedMotercade)){
    		this.salesBeServedMotercade =Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDeptMotorcade');
    	}
    	return this.salesBeServedMotercade;
    },
    //服务于营业部的集中开单组
    salesBeServedBillingGroup:null,
    getSalesBeServedBillingGroup:function(){
    	if(Ext.isEmpty(this.salesBeServedBillingGroup)){
    		this.salesBeServedBillingGroup =Ext.create('Foss.baseinfo.orgAdministrativeInfo.SaleDeptBillingGroup');
    	}
    	return this.salesBeServedBillingGroup;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.satellitePartSalesDept.isSatellite'),//是否卫星点部
            name      : 'satelliteDept',
            xtype:'checkbox',
            colspan : 1,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.isLeagueSaleDept'),//是否加盟网点
            name      : 'isLeagueSaleDept',
            xtype:'checkbox',
            colspan : 1,
            inputValue: 'Y',
            listeners:{//配合合伙人二级网点需求-308861
            	change:function(checkbox,newvalue,oldvalue){
            		if(newvalue){//勾选时，是否是否二级网点复选框可见，网点模式可编辑
            			me.getForm().findField('isTwoLevelNetwork').setReadOnly(false);
            			me.getForm().findField('networkModel').setReadOnly(false);
						me.getForm().findField('isTwoLevelNetwork').reset();
						me.getForm().findField('networkModel').reset();
						me.getForm().findField('networkModel').setValue('STANDARD_MODEL');
            		}else{//未勾选时，是否二级网点复选框不可见,网点模式不可编辑
            			if(me.getForm().findField('isLeagueSaleDept').getValue()==true){
            				me.getForm().findField('isTwoLevelNetwork').setReadOnly(false);
            				me.getForm().findField('networkModel').setReadOnly(false);
            			}else{
            				me.getForm().findField('isTwoLevelNetwork').setReadOnly(true);
                			me.getForm().findField('isTwoLevelNetwork').reset();
                			me.getForm().findField('networkModel').setReadOnly(true);
                			me.getForm().findField('networkModel').reset();
            			}
            		}
            	}
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheResidentSector'),//是否驻地部门
            name      : 'station',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var  orgBusinessInfoForm = Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgBusinessInfoForm(),
            		 saleDepartmentPartForm = me.up('panel').getSaleDepartmentPartForm(),
            		 transferCenter_field0 = saleDepartmentPartForm.getForm().findField('transferCenter');
            		if(newvalue){
            			//当是快递虚拟营业部时,提示用戶不可編輯
                		if(orgBusinessInfoForm.getForm().findField('expressSalesDepartment').getValue()){
                			checkbox.setValue(false);
                			//若是驻地营业部 ，提示
        					baseinfo.showErrorMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theDeptIsExpressSalesDepartmentCanNotCheckStation'));
                			return;
                		}
            			Ext.isEmpty(transferCenter_field0)?'':saleDepartmentPartForm.remove(transferCenter_field0);
            			var transferCenter_field = Ext.create('Foss.commonOrgSelector.DynamicOrgSelector',{
            				types:'ORG',
            				allowBlank:false,
                			transferCenter:'Y',
                			fieldLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theResidentTurnoverMinistryOutfield'),//驻地营业部所属外场
                			name:'transferCenter',
                			labelWidth:130
            			});
            			saleDepartmentPartForm.add(transferCenter_field);
                	}else{
                		Ext.isEmpty(transferCenter_field0)?'':saleDepartmentPartForm.remove(transferCenter_field0);
            			var transferCenter_field = Ext.create('Foss.commonOrgSelector.DynamicOrgSelector',{
            				types:'ORG',
            				hidden:true,
                			transferCenter:'Y',
                			fieldLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theResidentTurnoverMinistryOutfield'),//驻地营业部所属外场
                			colspan : 2,
                			name:'transferCenter',
                			labelWidth:130
            			});
            			saleDepartmentPartForm.add(transferCenter_field);
                	}
                }
            }
		},{
			boxLabel  : '是否二级网点',//是否二级网点-308861
            name      : 'isTwoLevelNetwork',
            xtype:'checkbox',
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.andWhetherCentralizedAccessDelivery'),//是否集中接送货
            name      : 'inCentralizedShuttle',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{//网点模式下拉框-308861
			xtype : 'combobox',
			name : 'networkModel',
			margin : '5 5 5 5',
			width:200,
			labelWidth:120,
			fieldLabel : '网点模式',
			allowBlank : true,
			blankText : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.null'),//无
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('NETWORK_MODEL')
		},{
			name: 'slogans',
			width:320,
			labelWidth:60,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.slogans'),//广告语
	        xtype : 'textfield'
		},{
			name: 'openingDate',
			allowBlank:false,
			width:250,
			format:'Y-m-d',
			labelWidth:130,
			readOnly:false,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.openingDate'),//开业日期
	        xtype : 'datefield',
	        listeners:{
            	change:function(field){
            		var openingDate =field.getValue();
        			var maxTempArrears = me.getForm().findField('maxTempArrears');
            		var currentTime =new Date();//当前时间
            		if(!Ext.isEmpty(openingDate)){//若最大临欠额度有值的话，说明不是新增
            			if(maxTempArrears.getValue()==0){
            				//若开业时间小于7月1号
                    		if(openingDate.getTime()<baseinfo.orgAdministrativeInfo.standardTime.getTime()){
                    			maxTempArrears.setValue(baseinfo.orgAdministrativeInfo.amount2);
                    		}else{
                    			//开业时间大于七月以后，若当前时间-开业时间<3个月的话，讲金额设置为3w （该需求是经过需求人员程敏瑞询问后添加的）
                    			var diff =Math.round((currentTime.getTime()-openingDate.getTime())/(86400*1000));
                    			if(diff<90){
                    				//开业不足三个月的，
                    				maxTempArrears.setValue(baseinfo.orgAdministrativeInfo.amount1);
                    			}else{
                    				//开业日期超过三个月的 ，也就500w
                    				maxTempArrears.setValue(baseinfo.orgAdministrativeInfo.amount2);
                    			}
                    		}
            			}
            		}
            	}
            }
		},{
			name: 'maxTempArrears',
			width:250,
			decimalPrecision:2,
			minValue:0,
			step:0.01,
			maxValue:99999999.99,
			labelWidth:130,
			readOnly:true,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theLargestProOweCreditsYuan'),//最大临欠额度（元）
	        xtype : 'numberfield'
		},{
			fieldLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.transferGoodsSector'),//转货部门
			arrive :'Y',
			name:'transferGoodDept',
			xtype : 'commonsaledepartmentselector'
		},{
			xtype : 'dynamicorgcombselector',
			types:'ORG',
			transferCenter:'Y',
			fieldLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theResidentTurnoverMinistryOutfield'),//驻地营业部所属外场
			name:'transferCenter',
			labelWidth:130
		},{
			value  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.servedMotorcade'),//所服务的车队：
            xtype:'displayfield'
		},{
			value  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.servedCentralizedBillingGroup'),//所服务的集中开单组：
            xtype:'displayfield'
		},me.getSalesBeServedMotercade(),me. getSalesBeServedBillingGroup()];
		me.callParent([cfg]);
	}
});
// 营业部对应的集中开单组grid
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDeptBillingGroup',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:250,
	columns: [
	     { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameTheBusinessBillingGroup'),width:160,dataIndex: 'billingGroupName'}//集中开单组名称
	  ],
	height: 180,
	 constructor : function(config) {
		 var me = this, 
			cfg = Ext.apply({}, config);
		 me.listeners = {
			    scrollershow: function(scroller) {
			    	if (scroller && scroller.scrollEl) {
			    			scroller.clearManagedListeners(); 
			    			scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
			    	}
			    }
		 };
		 me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
			});
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.SalesBillingGroupEntity',null,[]),
		me.callParent([cfg]);
	}
});
// 营业部对应的车队grid
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDeptMotorcade',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:250,
	columns: [
	     { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameTheBusinessMotorcade'),width:160,dataIndex: 'motorcadeName'}//车队名称
	  ],
	 height: 180,
	 constructor : function(config) {
		 var me = this, 
			cfg = Ext.apply({}, config);
		 me.listeners = {
			    scrollershow: function(scroller) {
			    	if (scroller && scroller.scrollEl) {
			    			scroller.clearManagedListeners(); 
			    			scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
			    	}
			    }
		 };
		 me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
			});
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.SalesMotorcadeEntity',null,[]),
		me.callParent([cfg]);
	}
});
/**
 * @description 营业部中间信息（出发信息）
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentCerterPanel', {
	extend:'Ext.form.Panel', 
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.businessDepartmentLeaveInfo'),
    height:250,
    layout:{
		type:'table',
		columns: 4
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 10 5'
    },
    //出发可选产品grid
    startingProductsConSelect:null,
	getStartingProductsConSelect:function(){
		if(Ext.isEmpty(this.startingProductsConSelect)){
    		this.startingProductsConSelect = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ProductsConSelect');
    	}
    	return this.startingProductsConSelect;
	},
    //出发已选产品grid
    startingProductsSelected:null,
	getStartingProductsSelected:function(){
		if(Ext.isEmpty(this.startingProductsSelected)){
    		this.startingProductsSelected = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ProductsSelected');
    	}
    	return this.startingProductsSelected;
	},
   
	 //移动出发产品按钮panel
	startingButtonPanel:null,
	getStartingButtonPanel:function(){
		if(Ext.isEmpty(this.startingButtonPanel)){
    		this.startingButtonPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.StartingButtonPanel');
    	}
    	return this.startingButtonPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.cayBeStarting'),//可出发
            name      : 'leave',
            xtype:'checkbox',	
            inputValue: 'Y',
            //BUG-25289
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var startingButtonPanel = me.getStartingButtonPanel();//出发使用产品按钮Panel
            		if(newvalue){
            			//出发使用产品按钮设置可用
            	    	for(var i=0;i<startingButtonPanel.items.items.length;i++){
            	    		startingButtonPanel.items.items[i].setDisabled(false);
            	    	}
                	}else{
                		//出发使用产品按钮设置不可用
            	    	for(var i=0;i<startingButtonPanel.items.items.length;i++){
            	    		startingButtonPanel.items.items[i].setDisabled(true);
            	    	}
            	    	startingButtonPanel.leftMoveAll();//清空已经选择的
                	}
                }
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wetherCanBeOpenedForServicesRendered'),//是否可开装卸费
            name      : 'canPayServiceFee',
            xtype:'checkbox',	
            //colspan : 2,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressOneTicketManyPieces'),//是否可开快递一票多件
            name      : 'canExpressOneMany',
            xtype:'checkbox',	
            //colspan : 2,
            inputValue: 'Y'
		},{//新增快递属性4（营业部属性2）
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressPickupToDoor'),//是否可快递接货
			name : 'canExpressPickupToDoor',
			xtype:'checkbox',
			inputValue:'Y',
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					//行政组织主信息页面
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var canExpressDelivery=me.up().getDeliveryInformationForm().getForm().findField('canExpressDelivery');
					/*if(newvalue){
						//若地图按钮按钮不可见,再做显示
						if(!Ext.getCmp('editExpressDelivery_id').isVisible()){
							baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','show');
						}
					}else{
						//若没有选中可快递派送
						if(!canExpressDelivery.checked){
							//若地图按钮按钮可见,再做隐藏
							if(Ext.getCmp('editExpressDelivery_id').isVisible()){
								baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','hide');
							}
						}
						
					}*/
				}
			}	
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canUpdateBuluWaybillDestinationStation'),//补录不可修改快递目的站
            name      : 'canUpdateDestination',
            xtype:'checkbox',	
            colspan : 2,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressPickUpDoorToDoorDelivery'),//是否可快递上门发货
            name      : 'canExpressDoorToDoor',
            xtype:'checkbox',	
            colspan : 2,
            inputValue: 'Y'
		},{
			value  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departureApplicableProducts'),//出发适用产品：
            xtype:'displayfield'
		},me.getStartingProductsConSelect(),me.getStartingButtonPanel(),me.getStartingProductsSelected()
		];
		me.callParent([cfg]);
	}
});
/**
 * 营业部下部信息(到达信息)
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SaleDepartmentBottomPanel', {
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.businessDepartmentArriveInfo'),
    height:350,
    layout:{
		type:'table',
		columns: 4
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 10 5'
    },
    //到达可选产品grid
    arriveProductsConSelect:null,
	getArriveProductsConSelect:function(){
		if(Ext.isEmpty(this.arriveProductsConSelect)){
    		this.arriveProductsConSelect = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ProductsConSelect');
    	}
    	return this.arriveProductsConSelect;
	},
    //到达已选产品grid
    arriveProductsSelected:null,
	getArriveProductsSelected:function(){
		if(Ext.isEmpty(this.arriveProductsSelected)){
    		this.arriveProductsSelected = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ProductsSelected');
    	}
    	return this.arriveProductsSelected;
	},
	 //移动到达产品按钮panel
	arriveButtonPanel:null,
	getArriveButtonPanel:function(){
		if(Ext.isEmpty(this.arriveButtonPanel)){
    		this.arriveButtonPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.ArriveButtonPanel');
    	}
    	return this.arriveButtonPanel;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items =[{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canBeReached'),//可到达
            name      : 'arrive',
            xtype:'checkbox',	
            inputValue: 'Y',
            colspan : 1,
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		//行政组织主信息页面
            		var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
            		var deliveryInformationForm = me.up('panel').getDeliveryInformationForm();
					var arriveButtonPanel = me.getArriveButtonPanel();//到达适用产品按钮Panel
            		if(newvalue){
            			//到达产品按钮设置可用
            	    	for(var i=0;i<arriveButtonPanel.items.items.length;i++){
            	    		arriveButtonPanel.items.items[i].setDisabled(false);
            	    	}
            	    	//选中可到达，显示派送自提描点按钮
            	    	baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'LD','show');
            	    	//选择可到达，显示提货信息
            			deliveryInformationForm.show();
            			deliveryInformationForm.getForm().getFields( ).each(function(item){//提货信息, 设置可编辑
            				var fieldName = item.getName();
            	    		if(fieldName=='deliveryAreaDesc'||fieldName=='pickupAreaDesc'||
            	    				fieldName =='expressDeliveryAreaDesc' ||fieldName =='expressPickupAreaDesc'){
            	    			//除了自提区域描述和派送区域描述，快递自提和派送区域描述
            	    				//item.setReadOnly(true);
            	    		}else{
            	    			if(item.readOnly == true){
            	    				item.setReadOnly(false);
            	    			}
            	    		}
            	    	});
            			me.up('panel').doLayout();
            			me.getForm().findField('stationNumber').allowBlank = false;
                	}else{
                		//到达产品按钮设置不可用
            	    	for(var i=0;i<arriveButtonPanel.items.items.length;i++){
            	    		arriveButtonPanel.items.items[i].setDisabled(true);
            	    	}
            	    	arriveButtonPanel.leftMoveAll();//清空已经选择的到达产品
            	    	//不选可到达，隐藏派送自提描点按钮
            	    	baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'LD','hide');
                		deliveryInformationForm.getForm().reset();//提货信息清除数据
            	    	//不选择了可到达，不显示提货信息
                		deliveryInformationForm.hide();
            			me.getForm().findField('stationNumber').allowBlank = true;
            			var stationNumber = me.getForm().findField('stationNumber').getValue();
            			if(Ext.isEmpty(stationNumber)){
            				 me.getForm().findField('stationNumber').reset();
            			}
                	}
                }
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherItReturnSignBill'),//是否可返回签单
            name      : 'canReturnSignBill',
            xtype:'checkbox',
            colspan : 1,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherCashOnDelivery'),//是否可货到付款
            name      : 'canCashOnDelivery',
            xtype:'checkbox',
            colspan : 2,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTheCollectionOfMoney'),//是否可代收货款
            name      : 'canAgentCollected',
            xtype:'checkbox',
            colspan : 1,
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		if(newvalue){//勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
						//me.getForm().findField('agentCollectedUpperLimit').reset();
            		}else{//未勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			if(me.getForm().findField('canAgentCollectedMany').getValue()==true){
            				me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
            			}else{
            				me.getForm().findField('agentCollectedUpperLimit').setReadOnly(true);
                			me.getForm().findField('agentCollectedUpperLimit').reset();
            			}
            			
            		}
            	}
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canArriveExpressOneMany'),//是否可到快递一票多件
            name      : 'canArriveExpressOneMany',
            colspan : 2,
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		if(newvalue){//勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			//me.getForm().findField('agentCollectedUpperLimit').reset();
            			//me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
            			me.getForm().findField('canCashOnDeliveryMany').show();
            			me.getForm().findField('canAgentCollectedMany').show();
            			me.doLayout();
            		}else{//未勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			/*if(me.getForm().findField('canAgentCollected').getValue()==true){
            				me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
            			}else{
            				me.getForm().findField('agentCollectedUpperLimit').reset();
                			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(true);
            			}*/
            			me.getForm().findField('canCashOnDeliveryMany').reset();
            			me.getForm().findField('canAgentCollectedMany').reset();
            			me.getForm().findField('canCashOnDeliveryMany').hide();
            			me.getForm().findField('canAgentCollectedMany').hide();
            			me.doLayout();
            		}
            	}
            }
		},{
			name: 'agentCollectedUpperLimit',
			colspan : 1,
			width:190,
			decimalPrecision:3,
			minValue:1,
			step:1.00,
			maxValue:99999,
			labelWidth:110,
			//readOnly:true,
			allowDecimals:false,//是否允许小数
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.agentCollectedUpperLimit'),//代收貨款上限
	        xtype : 'numberfield'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canCashOnDeliveryMany'),//是否可货到付款（多件）
            name      : 'canCashOnDeliveryMany',
            xtype:'checkbox',
            hidden: true,
            colspan : 2,
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canAgentCollectedMany'),//是否可代收货款（多件）
            name      : 'canAgentCollectedMany',
            xtype:'checkbox',
            colspan : 2,
            hidden: true,
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		if(newvalue){//勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
            			me.doLayout();
            		}else{//未勾选时，是否可货到付款（多件）、是否可代收货款（多件）复选框可见
            			if(me.getForm().findField('canAgentCollected').getValue()==true){
            				me.getForm().findField('agentCollectedUpperLimit').setReadOnly(false);
            			}else{
            				me.getForm().findField('agentCollectedUpperLimit').reset();
                			me.getForm().findField('agentCollectedUpperLimit').setReadOnly(true);
            			}
            		}
            	}
            }
		},{//新增快递属性3（营业部属性1）
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressReturnSignBill'),//是否可快递返回签单
			name :'canExpressReturnSignBill',
			xtype:'checkbox',
			inputValue:'Y'
		},{
			name: 'cancelArrivalDate',
			colspan : 2,
			width:250,
			format:'Y-m-d',
			labelWidth:130,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.cancelTheDateArrival'),//取消到达日期
	        xtype : 'datefield'
		},{
			name: 'stationNumber',
			regex:new RegExp('^\\d{4,4}$'),
			colspan : 1,
			regexText:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.onlyFourDigits'),//只能是4位数字
			labelWidth:80,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.deptNum'),//提货网点编码
	        xtype : 'textfield'
		},{
			value  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.reachingTheApplicableProducts'),//到达适用产品：
            xtype:'displayfield'
		},me.getArriveProductsConSelect(),me.getArriveButtonPanel(),me.getArriveProductsSelected()
		];
		me.callParent([cfg])
    }
});
//出发适用产品，可选的
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ProductsConSelect', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   columns: [
       { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.optionalProducts'),dataIndex: 'name' ,titlehidden:true,flex:1},//可选产品：
       { hidden:true, dataIndex: 'code'}
   ],
   height: 150,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store = baseinfo.getStore(null,null,['name','code'],[]),
		me.callParent([cfg]);
	}
});
//出发适用产品，已选的
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ProductsSelected', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   columns: [
       { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.selectedProducts'),dataIndex: 'name' ,titlehidden:true,flex:1},//可选产品：
       { hidden:true, dataIndex: 'code'}
   ],
   height: 150,
   width: 200,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.store = baseinfo.getStore(null,null,['name','code'],[]),
		me.callParent([cfg]);
	}
});
//出发按钮panel
Ext.define('Foss.baseinfo.orgAdministrativeInfo.StartingButtonPanel', {
   extend:'Ext.panel.Panel',
   height: 150,
   width: 80,
   //右移全部
   rightMoveAll:function(){
	   var me = this;
	   var  rightStore = me.up().getStartingProductsSelected().getStore();
	   var  leftStore = me.up().getStartingProductsConSelect().getStore();
	   leftStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   rightStore.add(moveRecord);
	   });
	   leftStore.removeAll();
   },
   //右移
   rightMove:function(){
	   var me = this; 
	   var  rightStore = me.up().getStartingProductsSelected().getStore();
	   var  selections = me.up().getStartingProductsConSelect().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   rightStore.add(moveRecord);
	   me.up().getStartingProductsConSelect().getStore().remove(selections);
   },
    //左移全部
   leftMoveAll:function(){
	   var me = this; 
	   var  rightStore = me.up().getStartingProductsSelected().getStore();
	   var  leftStore = me.up().getStartingProductsConSelect().getStore();
	   rightStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   leftStore.add(moveRecord);
	   });
	   rightStore.removeAll();
   },
   //左移
   leftMove:function(){
	   var me = this; 
	   var  selections = me.up().getStartingProductsSelected().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var  leftStore = me.up().getStartingProductsConSelect().getStore();
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   leftStore.add(moveRecord);
	   me.up().getStartingProductsSelected().getStore().remove(selections);
   },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.defaults ={
				xtype:'button',
				width:60,
				disabled:true,
				height:20,
				margin:'8 0 0 10'
		};
		me.items = [{
			 text: '-->>',
			 margin:'20 0 0 10',
		     handler: function() {
		    	 me.rightMoveAll();
		     }
		},{
			text: '-->',
		     handler: function() {
		        me.rightMove();
		     }
		},{
			text: '<--',
		     handler: function() {
		    	 me.leftMove();
		     }
		},{
			text: '<<--',
		     handler: function() {
		    	 me.leftMoveAll();
		     }
		}]
		me.callParent([cfg]);
	}
});
//到达按钮panel
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ArriveButtonPanel', {
   extend:'Ext.panel.Panel',
   height: 150,
   width: 80,
   //右移全部
   rightMoveAll:function(){
	   var me = this;
	   var  rightStore = me.up().getArriveProductsSelected().getStore();
	   var  leftStore = me.up().getArriveProductsConSelect().getStore();
	   leftStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   rightStore.add(moveRecord);
	   });
	   leftStore.removeAll();
   },
   //右移
   rightMove:function(){
	   var me = this; 
	   var  rightStore = me.up().getArriveProductsSelected().getStore();
	   var  selections = me.up().getArriveProductsConSelect().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   rightStore.add(moveRecord);
	   me.up().getArriveProductsConSelect().getStore().remove(selections);
   },
    //左移全部
   leftMoveAll:function(){
	   var me = this; 
	   var  rightStore = me.up().getArriveProductsSelected().getStore();
	   var  leftStore = me.up().getArriveProductsConSelect().getStore();
	   rightStore.each(function(record){
		   var moveRecord = {'name':record.get('name'),'code':record.get('code')};
		   leftStore.add(moveRecord);
	   });
	   rightStore.removeAll();
   },
   //左移
   leftMove:function(){
	   var me = this; 
	   var  selections = me.up().getArriveProductsSelected().getSelectionModel().getSelection();
	   if(selections.length!=1){
		   return;
	   }
	   var  leftStore = me.up().getArriveProductsConSelect().getStore();
	   var moveRecord =  {'name':selections[0].get('name'),'code':selections[0].get('code')};
	   leftStore.add(moveRecord);
	   me.up().getArriveProductsSelected().getStore().remove(selections);
   },
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.defaults ={
				xtype:'button',
				width:60,
				disabled:true,
				height:20,
				margin:'8 0 0 10'
		};
		me.items = [{
			 text: '-->>',
			 margin:'20 0 0 10',
		     handler: function() {
		        me.rightMoveAll();
		     }
		},{
			text: '-->',
		     handler: function() {
		        me.rightMove();
		     }
		},{
			text: '<--',
		     handler: function() {
		       me.leftMove()
		     }
		},{
			text: '<<--',
		     handler: function() {
		        me.leftMoveAll();
		     }
		}]
		me.callParent([cfg]);
	}
});
/**
 * @description 提货信息
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.DeliveryInformationForm', {
	extend:'Ext.form.Panel',  
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.deliveryInformation'),//提货信息
    height:700,
    layout:{
		type:'table',
		columns: 6
	},
    defaults : {
    	colspan : 1,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.availableFromMentioning'),//可自提
            name      : 'pickupSelf',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var pickupAreaDesc = me.getForm().findField('pickupAreaDesc');
            		if(newvalue){
            			//判断dom是否存在，防止setReadOnly操作时dom为空 
            			if(!Ext.getDom(pickupAreaDesc.getId())){
            				return;
            			}
            			pickupAreaDesc.setReadOnly(false);//选择可自提，则自提区域描述可编辑
                	}else{
                		pickupAreaDesc.setReadOnly(true);//没有选择可自提，则自提区域描述不可编辑
                		pickupAreaDesc.reset();	
                	}
                }
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canBeDelivered'),//可派送
            name      : 'delivery',
            xtype:'checkbox',	
            inputValue: 'Y',
            listeners:{
            	change:function(checkbox,newvalue,oldvalue){
            		var deliveryAreaDesc = me.getForm().findField('deliveryAreaDesc');
            		if(newvalue){
            			//判断dom是否存在，防止setReadOnly操作时dom为空 
            			if(!Ext.getDom(deliveryAreaDesc.getId())){
            				return;
            			}
            			deliveryAreaDesc.setReadOnly(false);//选择可自提，则字体区域描述可编辑
                	}else{
                		deliveryAreaDesc.setReadOnly(true);//没有选择可自提，则字体区域描述不可编辑
                		deliveryAreaDesc.reset();
                	}
                }
            }
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.mayAutomotiveArrive'),//可空运到达
            name      : 'airArrive',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.mayCarAutomotiveArrive'),//可汽运到达
            name      : 'truckArrive',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{//新增的快递属性5（自提的属性1）
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressDelivery'),//可快递派送
			name : 'canExpressDelivery',
			xtype:'checkbox',
			inputValue : 'Y',
			listeners:{
				change:function(checkbox,newvalue,oldvalue){
					var orgMainInfoForm	=Ext.getCmp('Foss_baseinfo_orgAdministrativeInfo_OrgInfoPanel_Id').getOrgMainInfoForm();
					var expressDeliveryAreaDesc =me.getForm().findField('expressDeliveryAreaDesc');
					var canExpressPickupToDoor =me.up().getSaleDepartmentCerterPanel().getForm().findField('canExpressPickupToDoor');
					if(newvalue){
						if(!Ext.getDom(expressDeliveryAreaDesc.getId())){
							return;
						}
						//若地图按钮按钮不可见
						/*if(!Ext.getCmp('editExpressDelivery_id').isVisible()){
							baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','show');
						}*/
						expressDeliveryAreaDesc.setReadOnly(false); //选择了可快递派送，则派送描述可编辑
						expressDeliveryAreaDesc.allowBlank = false;
					}else{
						expressDeliveryAreaDesc.setReadOnly(true);
						expressDeliveryAreaDesc.allowBlank = true;
						/*//若可快递接货不勾选的话
						if(!canExpressPickupToDoor.checked){
							//若按钮可见  在做隐藏
							if(Ext.getCmp('editExpressDelivery_id').isVisible()){
								baseinfo.setMapButtonCanEditFun(orgMainInfoForm,'KD','hide');
							}
						}*/
						expressDeliveryAreaDesc.reset();
					}
				}
			}
		},{//新增的快递属性6（自提的属性2）
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canExpressPickupSelf'), //可快递自提
            name : 'canExpressPickupSelf',
			xtype:'checkbox',
			inputValue : 'Y',
			listeners :{
				change: function(checkbox,newvalue,oldvalue){
					var expressPickupAreaDesc =me.getForm().findField('expressPickupAreaDesc');
					if(newvalue){
						if(!Ext.getDom(expressPickupAreaDesc.getId())){
							return;
						}
						expressPickupAreaDesc.setReadOnly(false);//选择了可快递自提，则自提描述为可编辑
						expressPickupAreaDesc.allowBlank = false;
					}else{
						expressPickupAreaDesc.setReadOnly(true);
						expressPickupAreaDesc.allowBlank = true;
						expressPickupAreaDesc.reset();
					}
				}
			} 
		},{//是否可家装送装
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canHomeImproSend'), //可家装送装
            name : 'canHomeImproSend',
			xtype:'checkbox',
			inputValue : 'Y'
		},{
			name: 'singlePieceLimitkg',
			colspan : 3,
			width:250,
			decimalPrecision:3,
			minValue:0,
			step:0.01,
			maxValue:99999999.999,
			labelWidth:130,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.singlePieceWeightLimitKG'),//单件重量上限（KG）
	        xtype : 'numberfield'
		},{
			name: 'singlePieceLimitvol',
			colspan : 3,
			width:250,
			decimalPrecision:3,
			minValue:0,
			step:0.01,
			maxValue:99999999.999,
			labelWidth:130,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.singlePieceVolumeCapParty'),//单件体积上限（方）
	        xtype : 'numberfield'
		},{
			name: 'singleBillLimitkg',
			colspan : 3,
			width:250,
			decimalPrecision:3,
			minValue:0,
			step:0.01,
			maxValue:99999999.999,
			labelWidth:130,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.singleTicketMaximumWeightKG'),//单票重量上限（KG）
	        xtype : 'numberfield'
		},{
			name: 'singleBillLimitvol',
			colspan : 3,
			width:250,
			decimalPrecision:3,
			minValue:0,
			step:0.01,
			maxValue:99999999.999,
			labelWidth:130,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theMaximumSingleVoteVolumeParty'),//单票体积上限（方）
	        xtype : 'numberfield'
		},{
			name: 'pickupAreaDesc',
			colspan : 6,
			width:650,
			height:120,
			readOnly:true,
			//maxLength:1333,
			labelWidth:100,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.fromMentioningArea'),//自提区域描述
	        xtype : 'textareafield'
		},{
			name: 'deliveryAreaDesc',
			colspan : 6,
			width:650,
			height:120,
			readOnly:true,
			labelWidth:100,
			//maxLength:1333,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencycompany.deliveryArea'),//派送区域描述
	        xtype : 'textareafield'
		},{//新增的快递属性7（自提属性）
			name: 'expressDeliveryAreaDesc',
			colspan : 6,
			width:650,
			height:120,
			readOnly:true,
			labelWidth:100,
			//maxLength:1333,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.expressDeliveryAreaDesc'),//快递派送区域描述
	        xtype : 'textareafield'
		},{//新增的快递属性8（自提属性）
			name: 'expressPickupAreaDesc',
			colspan : 6,
			width:650,
			height:120,
			readOnly:true,
			labelWidth:100,
			//maxLength:1333,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.expressPickupAreaDesc'),//快递自提区域描述
	        xtype : 'textareafield'
		},{
			name: 'deliveryCoordinate', //派送区域坐标编码
			readOnly:true,
			hidden:true,
	        xtype : 'textfield'
		},{
			name: 'expressDeliveryCoordinate',//快递派送区域坐标编码
			readOnly:true,
			hidden:true,
	        xtype : 'textfield'
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 组织架构基本信息查看界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OrgAuxiliaryInfoForm', {
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameOfTheHeadOfTheOrganization'),//组织架构基本信息
	animCollapse : true,
	collapsible :true,
	collapsed : true,
	frame:true,
    height:430,
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	xtype:'displayfield',
    	labelWidth:110,
    	width:350,
    	margin : '3 5 3 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'leader',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.ameOfHeadOfOrganization')//组织负责人姓名
		},{
			name: 'principalNo',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.personInCharge')//组织负责人
		},{
			name: 'parentOrgUnifiedCode',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.parentOrganizationsBenchmarkEncoding')//上级组织标杆编码
		},{
			name: 'parentOrgCode',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.parentOrganizationCoding')//上级组织编码3
		},{
			name: 'parentOrgName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.superiorOrg')//组织负责人姓名
		},{
			name: 'zipCode',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.postCode')//邮编号码4
		},{
			name: 'subsidiaryName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.itsSubsidiaries')//所属子公司
		},{
			name: 'costCenterName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.financialCostCenter')//财务成本中心
		},{
			name: 'deptDesc',
			colspan : 2,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentRemarks')//部门备注信息5
		},{
			name: 'beginTime',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.openingStartDate'),
	        xtype:'datefield',
	        readOnly:true,
	        format:'Y-m-d'//启用日期
		},{
			name: 'endTime',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.obsoleteDate'),//作废日期
	        readOnly:true,
	        xtype:'datefield',
	        format:'Y-m-d'
		},{
			name: 'status',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.organizationOfTheState'),//组织状态
	        queryMode: 'local',
	        displayField: 'name',
	        readOnly:true,
	        valueField: 'value',
	        xtype:'combo',
	        store:Ext.create('Ext.data.Store', {
	            fields: ['value', 'name'],
	            data : [
	                {"value":"Y", "name":baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.enable')},
	                {"value":"N", "name":baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.notEnabled')}
	            ]
	        })
		},{
			name: 'displayOrder',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.theOrderWhichTheyAppear')//显示顺序6
		},{
			name: 'deptLevel',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.departmentLevelCJ')//部门层级7
		},{
			name: 'isLeaf',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.isLeafNodeW'),//是否为叶子节点8
	        queryMode: 'local',
	        displayField: 'name',
	        readOnly:true,
	        valueField: 'value',
	        xtype:'combo',
	        store:Ext.create('Ext.data.Store', {
	            fields: ['value', 'name'],
	            data : [
	                {"value":"Y", "name":baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.yes')},
	                {"value":"N", "name":baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.notIt')}
	            ]
	        })
		},{
			name: 'deptAttribute',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.organizationalNature')//组织性质10
		},{
			name: 'areaCode',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.regionEncodingDefaultAlphabet')//地区编码默认拼音11
		},{
			name: 'deptEmail',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.organization-mail')//组织邮箱12
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 对公银行账号信息
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.PublicBankAccountForm', {
	extend:'Ext.form.Panel',
	title:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.publicBankAccountInformation'),//对公银行账号信息
    height:230,
    frame:true,
    collapsible :true,
    animCollapse : true,
    collapsed : true,
    margin : '-15 5 5 5',
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	xtype:'displayfield',
    	labelWidth:110,
    	width:350,
    	margin : '3 5 3 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'bankAcc',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.customer.bankAccount')//银行账号
		},{
			name: 'bankAccName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.bankAccountName')//银行开户名
		},{
			name: 'deptCd',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.unifiedCode')//部门标杆编码
		},{
			name: 'bankCd',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.bank.bankCode')//银行编码
		},{
			name: 'bankName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.bank.bankName')//银行名称
		},{
			name: 'subbranchCd',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.sub-branchesEncoding')//支行编码
		},{
			name: 'subbranchName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.customer.subBankAccount')//支行名称
		},{
			name: 'provCd',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.provincesCoding')//省份编码
		},{
			name: 'provName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.zip')//省份名称
		},{
			name: 'cityCd',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.cityCodes')//城市编码
		},{
			name: 'cityName',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.airagencydept.cityName')//城市名称
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 外场信息查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.OutfieldInfoForm', {
	extend:'Ext.form.Panel',  
    height:250,
    outfieldModel:null,//外场信息model
    layout:{
		type:'table',
		columns: 6
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.mayAutomotiveStowage'),//可汽运配载
			margin : '5 5 5 15',
            name      : 'vehicleAssemble',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.outgoingStowage'),//可外发配载
            name      : 'outAssemble',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.toPlayTheGallows'),//可打木架
            name      : 'packingWood',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.canTransit'),//可中转
            name      : 'transfer',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.isThereAutomaticSorter'),//是否有自动分拣机
            name      : 'sortingMachine',
            xtype:'checkbox',	
            inputValue: 'Y'
		},{//新增的快递属性11
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.isExpressOutAssemble'),//是否可快递代理外发配载
			name :'expressOutAssemble',
            xtype:'checkbox',
            inputValue :'Y'
		},{//新增是否有待叉区
			boxLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.isWaitForkArea'),//是否可快递代理外发配载
			name :'isHaveWaitForkArea',
            xtype:'checkbox',
            colspan : 6,
            inputValue :'Y'
		},{
			name: 'code',
			colspan : 3,
			margin : '5 5 5 5',
			allowBlank:false,
			width:250,
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fieldCoding'),//外场编码
	        xtype : 'textfield'
		},{
			name: 'simpleCode',
			colspan : 3,
			margin : '5 5 5 5',
			allowBlank:false,
			width:250,
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.outfieldSimpleCode'),//外场简码
	        regex:/^[A-Za-z]{1,20}$/,
	        xtype : 'textfield'
		},{
			xtype : 'commonmotorcadeselector',
			name : 'motorcadeCode',
			colspan : 3,
			margin : '5 5 5 5',
			allowBlank:false,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.ownedTopLevelVehicleFleet'),//所属顶级车队
			allowBlank : false,
			isFullFleetOrgFlag:'Y',
			listWidth : 300,// 设置下拉框宽度
			isPaging : true // 分页
		},{
			name: 'goodsArea',
			colspan : 3,
			width:250,
			decimalPrecision:3,
			minValue:0,
			step:0.001,
			maxValue:99999999.999,
			margin : '5 15 5 15',
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.cargoAreaSquareMeters'),//货台面积（平方米）
	        xtype : 'numberfield'
		},{
			name: 'platArea',
			colspan : 3,
			margin : '5 5 5 5',
			width:250,
			labelWidth:120,
			decimalPrecision:3,
			minValue:0,
			step:0.001,
			maxValue:99999999.999,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.dockAreaSquareMeters'),//货台面积（平方米）
	        xtype : 'numberfield'
		},{
			name: 'platType',
			width:200,
			margin : '5 5 5 15',
			labelWidth:60,
			displayField: 'valueName',
			valueField: 'valueCode',
			colspan : 3,
			store:baseinfo.getStore(null,null,['valueName','valueCode']
		    ,[{'valueName':baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.unilateral'),'valueCode':'DB'},{'valueName':baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.bilateral'),'valueCode':'SB'}]),
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.libraryType'),//库型
	        xtype : 'combo',
	        editable : false
		},{
			xtype : 'dynamicorgcombselector',
			name:'airDispatchCode',
			colspan : 3,
			types:'ORG',
			airDispatch:'Y',//空运总调
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.totalTransferByAir')//空运总调
		},{
			name: 'manSpeed',
			colspan : 3,
			margin : '5 5 5 5',
			width:250,
			labelWidth:120,
			decimalPrecision:3,
			minValue:0,
			step:0.001,
			maxValue:99999999.999,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.manSpeed'),//人工速度（平方米）
	        xtype : 'numberfield'
		},{
			name: 'forkSpeed',
			colspan : 3,
			margin : '5 5 5 5',
			width:250,
			labelWidth:120,
			decimalPrecision:3,
			minValue:0,
			step:0.001,
			maxValue:99999999.999,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.forkSpeed'),//叉车速度（平方米）
	        xtype : 'numberfield'
		}, {//外场业务渠道下拉框dujunhui-187862
			xtype : 'combobox',
			name : 'transferServiceChannel',
			colspan : 3,
			margin : '5 5 5 5',
			width:250,
			labelWidth:120,
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.transferServiceChannel'),//外场业务渠道
			allowBlank : true,
			blankText : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.null'),//无
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			editable : false,
			store : FossDataDictionary.getDataDictionaryStore('TRANSFER_SERVICE_CHANNEL')
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 调度组查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ControlGroupInfoForm', {
	extend:'Ext.form.Panel',  
	motorcadeModel:null,//数据model
    height:150,
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'parentOrgCode',
			allowBlank:false,
			width:320,
			types:'ORG',
			transDepartment:'Y',
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.ownedVehicleFleet'),//所属车队
	        xtype : 'dynamicorgcombselector'
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 集中开单组外场查看界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.BillingGroupNameInfoForm', {
	extend:'Ext.form.Panel',  
	billingGroupTransFerModel:null,//数据model
	billingGroupServeSalesDeptEntityList:[],//集中开单组服务营业部
    height:300,
    layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    //集中开单组服务营业部
    billingGroupServeSalesDept:null,
    getBillingGroupServeSalesDept:function(){
    	if(Ext.isEmpty(this.billingGroupServeSalesDept)){
    		this.billingGroupServeSalesDept =Ext.create('Foss.baseinfo.orgAdministrativeInfo.BillingGroupServeSalesDept');
    	}
    	return this.billingGroupServeSalesDept;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'transFerCode',			
			allowBlank:false,
			width:320,
			types:'ORG',
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.billingGroupOutfield'),//对应外场
	        xtype : 'dynamicorgcombselector'
		},{	
			xtype : 'dynamicorgcombselector',
			types:'ORG',
			name:'billingGroupServeSalesDeptEntityList',  //重新定義一個list
			salesDepartment:'Y',//营业部
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.billingGroupResponsibleForTheBusinessDept'),
			listeners:{
				select:function(comb,records,obj){
					var billingGroupCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var billingGroupName =Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('name');
					var billingGroupServeSalesDeptModel = new Foss.baseinfo.orgAdministrativeInfo.SalesBillingGroupEntity();
						billingGroupServeSalesDeptModel.set('billingGroupCode',billingGroupCode);
						billingGroupServeSalesDeptModel.set('billingGroupName',billingGroupName);
					var boolean = false;
					me.getBillingGroupServeSalesDept().getStore().each(function(record){ //判断选择中的值是否存在纸槽中，
						if(record.get('salesDeptCode')==records[0].get('code')){
							boolean = true;
						}
					});
					if(boolean){
						return;
					}
					billingGroupServeSalesDeptModel.set('salesDeptCode',records[0].get('code'));
					billingGroupServeSalesDeptModel.set('salesDeptName',records[0].get('name'));
					me.getBillingGroupServeSalesDept().getStore().add(billingGroupServeSalesDeptModel);
				}
			}
		},me.getBillingGroupServeSalesDept()];
		me.callParent([cfg]);
	}
});
/**
 * 集中开单组服务营业部
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.BillingGroupServeSalesDept',{
	extend:'Ext.grid.Panel',  
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	frame:true,
	width:250,
	columns: [
	     { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameTheBusinessDepartment'),width:160,dataIndex: 'salesDeptName'}//营业部名称
	  ],
	 height: 180,
	 constructor : function(config) {
		 var me = this, 
			cfg = Ext.apply({}, config);
		 me.listeners = {
			    scrollershow: function(scroller) {
			    	if (scroller && scroller.scrollEl) {
			    			scroller.clearManagedListeners(); 
			    			scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
			    	}
			    }
		 };
		 me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
			});
		 me.tbar = [{									
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.removeSelectedBusinessDept'),//移除选中的营业部
			disabled:true,
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的数据
				if(selections.length<1){//判断是否至少选中了一条
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectRemoveOperation'));//请选择一条进行移除操作！
					return;//没有则提示并返回
				}															
				baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheseBusinessDepts'),function(e){//是否要移除这些营业部？
					if(e=='yes'){//询问是否删除
						me.getStore().remove(selections);
					}
				})
			} 
		 }];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.SalesBillingGroupEntity',null,[]),
		me.callParent([cfg]);
	}
});

/**
 * @description 车队查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeInfoForm', {
	extend:'Ext.form.Panel',  
	//hidden:true,
	motorcadeModel:null,//数据model
    motorcadeServeSalesAreaEntityList:[],//车队负责营业区
    motorcadeServeDistrictEntityList:[],//车队负责行政区
    motorcadeServeSalesDeptEntityList:[],//车队服务营业部
    height:520,
    layout:{
		type:'table',
		columns: 6
	},
    defaults : {
    	colspan : 3,
    	readOnly:true,
    	margin : '5 5 0 5'
    },
  //车队负责行政区域
    motorcadeServeDistrict:null,
	getMotorcadeServeDistrict:function(){
		if(Ext.isEmpty(this.motorcadeServeDistrict)){
    		this.motorcadeServeDistrict = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrict');
    	}
    	return this.motorcadeServeDistrict;
	},
	//车队负责营业区
	motorcadeServeSalesArea:null,
	getMotorcadeServeSalesArea:function(){
		if(Ext.isEmpty(this.motorcadeServeSalesArea)){
    		this.motorcadeServeSalesArea = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesArea');
    	}
    	return this.motorcadeServeSalesArea;
	},
	//车队服务营业部
	motorcadeServeSalesDept:null,
	getMotorcadeServeSalesDept:function(){
		if(Ext.isEmpty(this.motorcadeServeSalesDept)){
    		this.motorcadeServeSalesDept = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesDept');
    	}
    	return this.motorcadeServeSalesDept;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.andWhetherCentralizedAccessDelivery'),//是否集中接送货
            name      : 'service',
            width:250,
            colspan : 3,
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherTopTeams'),//是否顶级车队
            name      : 'isTopFleet',
            width:250,
            colspan : 2,
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherDirectPipeCar'),//是否直管车
            name      : 'isManageVehicle',
            width:250,
            colspan : 2,
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			xtype : 'dynamicorgcombselector',
			name:'serveBillTerm',
			types:'ORG',
			labelWidth:110,
			billingGroup:'Y',//集中开单组
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.servedCentralizedBillingGroup')//所服务集中开单组
		},{
			name: 'serviceCode',
			width:250,
			labelWidth:120,
			//allowBlank:true,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.centralizedAccessDeliveryFleetCoding'),//集中接送货车队编码
	        xtype : 'textfield',
	        listeners:{
            	blur:function(field,e,eOpts ){
            		var service =me.getForm().findField('service');
            		//被选中的话
            		if(service.getValue()){
            			field.allowBlank =false;
            		}else{
            			field.allowBlank =true;
            		}
            	}
            }
	        	
		},{
			xtype : 'dynamicorgcombselector',
			name:'transferCenter',
			types:'ORG',
			transferCenter:'Y',//查询外场
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetBelongsOutfield')//车队驻地外场
		},{
			xtype : 'commonareaselector',
			name: 'fleetType',//车队类型
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:FossDataDictionary.getDataDictionaryStore(baseinfo.orgAdministrativeInfo.motorcadeType),
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.loadandunloadsquad.truckType'),//车队类型
	        xtype : 'combo'
		},{
			labelWidth:90,
			xtype : 'commonreginbyconditionselector',
			degree:'DISTRICT_COUNTY',
			name:'motorcadeServeDistrictEntityList',
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetResponsibleForTheAdministrativeArea'),//车队负责行政区域
			showContent : '{name}&nbsp;&nbsp;&nbsp;{parentDistrictName}',
			listeners:{
				select:function(comb,records,obj){
					var motorcadeCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var motorcadeServeDistrictModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrictEntity();
					motorcadeServeDistrictModel.set('motorcadeCode',motorcadeCode);
					var boolean = false;
					me.getMotorcadeServeDistrict().getStore().each(function(record){
						if(record.get('districtCode')==records[0].get('code')){
							boolean = true;
						}
					});
					if(boolean){
						return;
					}
					motorcadeServeDistrictModel.set('districtCode',records[0].get('code'));
					motorcadeServeDistrictModel.set('districtName',records[0].get('name'));
					me.getMotorcadeServeDistrict().getStore().add(motorcadeServeDistrictModel);
				}
			}
		},{
			xtype : 'dynamicorgcombselector',
			types:'ORG',
			name:'motorcadeServeSalesAreaEntityList',
			smallRegion:'Y',//营业小区
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetResponsibleForTheBusinessArea'),
			listeners:{
				select:function(comb,records,obj){
					var motorcadeCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var motorcadeServeSalesAreaModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesAreaEntity();
					motorcadeServeSalesAreaModel.set('motorcadeCode',motorcadeCode);
					var boolean = false;
					//注：之前是 me.getMotorcadeServeDistrict()
					me.getMotorcadeServeSalesArea().getStore().each(function(record){ //判断选择中的值是否存在纸槽中，
						if(record.get('salesareaCode')==records[0].get('code')){
							boolean = true;
						}
					});
					if(boolean){
						return;
					}
					motorcadeServeSalesAreaModel.set('salesareaCode',records[0].get('code'));
					motorcadeServeSalesAreaModel.set('salesareaName',records[0].get('name'));
					me.getMotorcadeServeSalesArea().getStore().add(motorcadeServeSalesAreaModel);
				}
			}
		},me.getMotorcadeServeDistrict(),
		  me.getMotorcadeServeSalesArea(),
		{	
			xtype : 'dynamicorgcombselector',
			types:'ORG',
			name:'motorcadeServeSalesDeptEntityList',  //重新定義一個list
			salesDepartment:'Y',//营业部
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetResponsibleForTheBusinessDept'),
			listeners:{
				select:function(comb,records,obj){
					var motorcadeCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var motorcadeServeSalesDeptModel = new Foss.baseinfo.orgAdministrativeInfo.SalesMotorcadeEntity();
						motorcadeServeSalesDeptModel.set('motorcadeCode',motorcadeCode);
					var boolean = false;
					me.getMotorcadeServeSalesDept().getStore().each(function(record){ //判断选择中的值是否存在纸槽中，
						if(record.get('salesdeptCode')==records[0].get('code')){
							boolean = true;
						}
					});
					if(boolean){
						return;
					}
					motorcadeServeSalesDeptModel.set('salesdeptCode',records[0].get('code'));
					motorcadeServeSalesDeptModel.set('salesdeptName',records[0].get('name'));
					me.getMotorcadeServeSalesDept().getStore().add(motorcadeServeSalesDeptModel);
				}
			}
		},me.getMotorcadeServeSalesDept()];
		me.callParent([cfg]);
	}
});
/**
 * @description 车队组信息查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeGroupInfoForm', {
	extend:'Ext.form.Panel',  
	motorcadeModel:null,//数据model
    motorcadeServeSalesAreaEntityList:[],//车队组负责营业区
    motorcadeServeDistrictEntityList:[],//车队组负责行政区
    height:320,
    layout:{
		type:'table',
		columns: 2
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 0 5'
    },
	 //车队负责行政区域
    motorcadeServeDistrict:null,
	getMotorcadeServeDistrict:function(){
		if(Ext.isEmpty(this.motorcadeServeDistrict)){
    		this.motorcadeServeDistrict = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrict');
    	}
    	return this.motorcadeServeDistrict;
	},
	//车队负责营业区
	motorcadeServeSalesArea:null,
	getMotorcadeServeSalesArea:function(){
		if(Ext.isEmpty(this.motorcadeServeSalesArea)){
    		this.motorcadeServeSalesArea = Ext.create('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesArea');
    	}
    	return this.motorcadeServeSalesArea;
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			boxLabel  : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.whetherCentralizedAccessDeliveryFleetGroup'),//是否集中接送货车队组
            name      : 'serviceTeam',
            width:250,
            xtype:'checkbox',	
            inputValue: 'Y'
		},{
			name: 'parentOrgCode',
			allowBlank:false,
			width:250,
			types:'ORG',
			transDepartment:'Y',
			labelWidth:120,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.ownedVehicleFleet'),//所属车队
	        xtype : 'dynamicorgcombselector'
		},{
			xtype : 'dynamicorgcombselector',
			forceSelection : true,
			name:'transferCenter',
			types:'ORG',
			transferCenter:'Y',//查询外场
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetGroupBelongsOutfield')//车队所属外场
		},{
			name: 'fleetType',//车队组类型
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:FossDataDictionary.getDataDictionaryStore(baseinfo.orgAdministrativeInfo.motorcadeType),
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetGroupType'),//车队组类型
	        xtype : 'combo'
		},{
			xtype : 'dynamicorgcombselector',
			name:'serveBillTerm',
			types:'ORG',
			colspan : 2,
			labelWidth:110,
			billingGroup:'Y',//开单组
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.servedCentralizedBillingGroup')//所服务集中开单组
		},{
			forceSelection : true,
			labelWidth:110,
			fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetGroupResponsibleAdministrativeRegions'),//车队负责行政区域
			xtype : 'commonreginbyconditionselector',
			degree:'DISTRICT_COUNTY',//省市县类别
			listeners:{
				select:function(comb,records,obj){
					var motorcadeCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var motorcadeServeDistrictModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrictEntity();
					motorcadeServeDistrictModel.set('motorcadeCode',motorcadeCode);
					var boolean = false;
					me.getMotorcadeServeDistrict().getStore().each(function(record){
						if(record.get('districtCode')==records[0].get('code')){
							var boolean = true;
						}
					});
					if(boolean){
						return;
					}
					motorcadeServeDistrictModel.set('districtCode',records[0].get('code'));
					motorcadeServeDistrictModel.set('districtName',records[0].get('name'));
					me.getMotorcadeServeDistrict().getStore().add(motorcadeServeDistrictModel);
				}
			}
		},{
			xtype : 'dynamicorgcombselector',
			forceSelection : true,
			types:'ORG',
			smallRegion:'Y',
			labelWidth:110,
			fieldLabel : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.fleetGroupResponsibleBusinessArea'),
			listeners:{
				select:function(comb,records,obj){
					var motorcadeCode = Ext.getCmp('T_baseinfo-queryOrgBiz_content').getOrgInfoPanel().getOrgMainInfoForm().orgAdministrativeInfoModel.get('code');
					var motorcadeServeSalesAreaModel = new Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesAreaEntity();
					motorcadeServeSalesAreaModel.set('motorcadeCode',motorcadeCode);
					var boolean = false;
					me.getMotorcadeServeSalesArea().getStore().each(function(record){
						if(record.get('salesareaCode')==records[0].get('code')){
							var boolean = true;
						}
					});
					if(boolean){
						return;
					}
					motorcadeServeSalesAreaModel.set('salesareaCode',records[0].get('code'));
					motorcadeServeSalesAreaModel.set('salesareaName',records[0].get('name'));
					me.getMotorcadeServeSalesArea().getStore().add(motorcadeServeSalesAreaModel);
				}
			}
		},me.getMotorcadeServeDistrict(),me.getMotorcadeServeSalesArea()];
		me.callParent([cfg]);
	}
});
//车队负责行政区域GRID
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrict', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   frame:true,
   width:250,
   columns: [
       { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.administrativeAreaName'),width:160,dataIndex: 'districtName' }//行政区域名称
   ],
   height:180,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeDistrictEntity',null,[]),
		me.tbar = [{
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.removeSelectedAdministrativeRegions'),//移除选中的行政区域
			disabled:true,
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的数据
				if(selections.length<1){//判断是否至少选中了一条
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectRemoveOperation'));//请选择一条进行移除操作！
					return;//没有则提示并返回
				}
				baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheseAdministrativeRegions'),function(e){//是否要移除这些行政区域？
					if(e=='yes'){//询问是否删除
						me.getStore().remove(selections);
					}
				})
			} 
		}];
		me.callParent([cfg]);
	}
});
//车队负责营业区GRID
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesArea', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   frame:true,
   width:250,
   columns: [
       { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameTheBusinessDistrict'),width:160,dataIndex: 'salesareaName'}//营业区名称
   ],
   height: 180,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.tbar = [{
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.removeSelectedBusinessArea'),//移除选中的营业区
			disabled:true,
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的数据
				if(selections.length<1){//判断是否至少选中了一条
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectRemoveOperation'));//请选择一条进行移除操作！
					return;//没有则提示并返回
				}
				baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheseBusinessAreas'),function(e){//是否要移除这些营业区？
					if(e=='yes'){//询问是否删除
						me.getStore().remove(selections);
					}
				})
			} 
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesAreaEntity',null,[]),
		me.callParent([cfg]);
	}
});
//车队负责营业部GRID
Ext.define('Foss.baseinfo.orgAdministrativeInfo.MotorcadeServeSalesDept', {
   extend:'Ext.grid.Panel',  
   sortableColumns:false,
   enableColumnHide:false,
   enableColumnMove:false,
   frame:true,
   width:250,
   columns: [
       { header:baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.nameTheBusinessDepartment'),width:160,dataIndex: 'salesdeptName'}//营业部名称
   ],
   height: 180,
   constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.listeners = {
		    	scrollershow: function(scroller) {
		    		if (scroller && scroller.scrollEl) {
		    				scroller.clearManagedListeners(); 
		    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
		    		}
		    	}
		  };
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//多选框
			mode:'MULTI',
			checkOnly:true
		});
		me.tbar = [{									
			text : baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.removeSelectedBusinessDept'),//移除选中的营业区
			disabled:true,
			handler :function(){
				var selections = me.getSelectionModel().getSelection();//获取选中的数据
				if(selections.length<1){//判断是否至少选中了一条
					baseinfo.showWoringMessage(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.pleaseSelectRemoveOperation'));//请选择一条进行移除操作！
					return;//没有则提示并返回
				}															
				baseinfo.showQuestionMes(baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.wantRemoveTheseBusinessDepts'),function(e){//是否要移除这些营业区？
					if(e=='yes'){//询问是否删除
						me.getStore().remove(selections);
					}
				})
			} 
		}];
		me.store = baseinfo.getStore(null,'Foss.baseinfo.orgAdministrativeInfo.SalesMotorcadeEntity',null,[]),
		me.callParent([cfg]);
	}
});
/**
 * @description 排单部门查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.DeliverScheduleInfoForm', {
	extend:'Ext.form.Panel', 
	orgAdministrativeInfoModel:null,
    height:120,
    layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			name: 'deliverOutfield',
			allowBlank:false,
			width:400,
			types:'ORG',
			transferCenter:'Y',
			labelWidth:220,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.deliveryOfSingleRowServicesOutfield'),//派送派单服务外场
	        xtype : 'dynamicorgcombselector'
		}];
		me.callParent([cfg]);
	}
});
/**
 * @description 理货业务类型查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.ArrangeGoodsInfoForm', {
	extend:'Ext.form.Panel', 
	orgAdministrativeInfoModel:null,
    height:120,
    layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var arrangeBizTypeStore = FossDataDictionary.getDataDictionaryStore(baseinfo.orgAdministrativeInfo.arrangeBizType);//理货类型
		me.items = [{
			name: 'arrangeOutfield',
			allowBlank:false,
			width:400,
			labelWidth:220,
			type:'ORG',
			transferCenter:'Y',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tallySectorServiceOutfield'),//理货部门服务外场
	        xtype : 'dynamicorgcombselector'
		},{
			name: 'arrangeBizType',
			queryMode: 'local',
		    displayField: 'valueName',
		    valueField: 'valueCode',
		    editable:false,
		    store:arrangeBizTypeStore,
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tallyBusinessType'),//理货业务类型
	        xtype : 'combo'
		}];
		me.callParent([cfg]);
	}
});

/**
 * @description 保安组类型查看修改界面
 */
Ext.define('Foss.baseinfo.orgAdministrativeInfo.SecurityTfrMotorcadeInfoForm', {
	extend:'Ext.form.Panel', 
	securityTfrMotorcadeInfoModel:null,
	securityTfrMotorcadeEntityList:[],
    height:120,
    layout:{
		type:'table',
		columns: 1
	},
    defaults : {
    	colspan : 1,
    	readOnly:true,
    	margin : '5 5 5 5'
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var arrangeBizTypeStore = FossDataDictionary.getDataDictionaryStore(baseinfo.orgAdministrativeInfo.arrangeBizType);//理货类型
		me.items = [{
			name: 'tallySectorServiceOutfield',
			allowBlank:false,
			width:400,
			labelWidth:220,
			type:'ORG',
			transferCenter:'Y',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.securityTfrMotorcadeServiceOutfield'),//保安组服务外场
	        xtype : 'dynamicorgcombselector'
		},{
			name: 'tallySectorServicemotorCade',
			allowBlank:true,
			width:400,
			labelWidth:220,
//			type:'ORG',
//			transferCenter:'Y',
	        fieldLabel: baseinfo.orgAdministrativeInfo.i18n('foss.baseinfo.tallySectorServiceMotorCade'),//保安组服务车队
	        xtype : 'commonmotorcadeselector'
		}];
		me.callParent([cfg]);
	}
});


/**
 * @description 行政组织主页
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_baseinfo-queryOrgBiz_content')) {
		return;
	};
	baseinfo.searchConfigParams();//获取配置参数值
	baseinfo.searchThreeLevelProduct();//查询三级产品
	var orgTreeSearchPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OrgTreeSearchPanel');//组织树查询panel
	var orgInfoPanel = Ext.create('Foss.baseinfo.orgAdministrativeInfo.OrgInfoPanel');//组织详细信息PANEL
	Ext.getCmp('T_baseinfo-queryOrgBiz').add(Ext.create('Ext.panel.Panel', {
		id : 'T_baseinfo-queryOrgBiz_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		layout:'column',
		//组织树查询panel
		getOrgTreeSearchPanel : function() {
			return orgTreeSearchPanel;
		},
		//组织详细信息PANEL
		getOrgInfoPanel : function() {
			return orgInfoPanel;
		},
		items : [orgTreeSearchPanel,orgInfoPanel] 
	}));
});