// 增加HashMap 将前台查询过的数据放入map里 避免发送二次请求查询 
(function(){
	if(Ext.isEmpty(querying.integrativeQuery.map)){
		querying.integrativeQuery.map = new Ext.util.HashMap();
	}
})();
ContextPath.BSE_BASEINFO//当前用户操作部门
//登陆用户当前部门
querying.integrativeQuery.currentDeptCode=FossUserContext.getCurrentDeptCode();
//登录用户当前部门是否调度组
querying.integrativeQuery.dispatchTeam=FossUserContext.getCurrentDept().dispatchTeam;
//登录用户当前部门是否车队
querying.integrativeQuery.transDepartment=FossUserContext.getCurrentDept().transDepartment;
//当前运单的到达部门
querying.integrativeQuery.arriveDeptCode=null;
//登陆用户员工号
querying.integrativeQuery.currentEmpCode=FossUserContext.getCurrentUserEmp().empCode;
//根据运单查询出来的始发部门
querying.integrativeQuery.leaveDeptCode = null;
//根据运单查询出来的到达部门
querying.integrativeQuery.arriveDeptCode = null;
//开单时间
querying.integrativeQuery.billTime = null;
//已作废单号
querying.integrativeQuery.waybillStatus=null;
querying.integrativeQuery.baseinfoRealPath = ContextPath.BSE_BASEINFO+"/baseinfo/";

/**
 * 查询条件form				Foss.integrativeQueryIndex.QueryForm
 * 查看运单详情				Foss_integrativeQueryIndex_QueryResult_Id
 */
//自定义查询 实体中 active默认值
querying.integrativeQuery.waybillNo = null;
querying.integrativeQuery.mark = {jj:'JJ',fcjj:'FCJJ'};//JJ：紧急，FCJJ：非常紧急
querying.integrativeQuery.regLimit = {
		m:/^.{0,40}$/,mobil:/^1\d{10}$/,w:/^\d{8,9}$/,expressW:/^\d{8,10}$/
	};
/**
 * 获得 查询条件 数据类型
 * 目的站 			TARGET_ORG_CODE；
 * 运输性质 			PRODUCT_CODE
 * 提货方式 			RECEIVE_METHOD
 * 货物类型 			GOODS_TYPE_CODE
 * 件数 				GOODS_QTY_TOTAL
 * 重量  			GOODS_WEIGHT_TOTAL
 * 体积  			GOODS_VOLUME_TOTAL
 * 付款方式  		PAID_METHOD
 **/
//标识是 查询条件 类型 对应后端 常量 QueryingConstant【IS_DICT,C_NUMBER,C_STRING】
querying.integrativeQuery.compareConditionType = {
	isNum:'C_NUMBER',
	isStr:'C_STRING',
	isDict:'IS_DICT'
};

// 联系人类型 转换
querying.integrativeQuery.convertInfo = function(conArray) {
	var linkManArray = [querying.integrativeQuery.i18n('foss.querying.financeLinkman'),querying.integrativeQuery.i18n('foss.querying.businessContact'),querying.integrativeQuery.i18n('foss.querying.shippingContact'),querying.integrativeQuery.i18n('foss.querying.receivePersonContact'),querying.integrativeQuery.i18n('foss.querying.agreementName')]
	//'财务联系人','业务联系人','发货联系人','收货联系人','协议联系人'
		retuen_v = '';
	for(var i = 0;i<conArray.length;i++){
		if(1 == conArray[i] || '1' == conArray[i]){
			if(retuen_v == ''){
				retuen_v = linkManArray[i];
			}else{
				retuen_v += ','+linkManArray[i];
			}
		}
	}
	return retuen_v;
};
//获得 查询条件 数据类型
querying.integrativeQuery.compareCondition ={
	PRODUCT_CODE:'IS_DICT',//运输性质虽然不是数据字典 不过以数据字典形式 处理
	TARGET_ORG_CODE:'C_STRING',
	RECEIVE_METHOD:'IS_DICT',
	GOODS_TYPE_CODE:'IS_DICT',
	GOODS_QTY_TOTAL:'C_NUMBER',
	GOODS_WEIGHT_TOTAL:'C_NUMBER',
	GOODS_VOLUME_TOTAL:'C_NUMBER',
	PAID_METHOD:'IS_DICT'
};
//获得 查询条件 数据类型
querying.integrativeQuery.compareConditionXtype ={
	PRODUCT_CODE:'combobox',
	TARGET_ORG_CODE:'textfield',
	RECEIVE_METHOD:'combobox',
	GOODS_TYPE_CODE:'combobox',
	GOODS_QTY_TOTAL:'numberfield',
	GOODS_WEIGHT_TOTAL:'numberfield',
	GOODS_VOLUME_TOTAL:'numberfield',
	PAID_METHOD:'combobox'
};
//获得 查询条件中 是数据字典的 数据字典 key
querying.integrativeQuery.compareConditionDict ={
	PRODUCT_CODE:'COMPARE_CONDITION',
	TARGET_ORG_CODE:'C_STRING',
	RECEIVE_METHOD:'PICKUPGOODSHIGHWAYS',//提货方式  【汽运】：PICKUPGOODSHIGHWAYS 空运：PICKUPGOODSAIR
	GOODS_TYPE_CODE:'AIRGOODSTYPE',//此为 空运数据字典 缺失 汽运 货物类型  【A:A货,B:B货】
	GOODS_QTY_TOTAL:'C_NUMBER',
	GOODS_WEIGHT_TOTAL:'C_NUMBER',
	GOODS_VOLUME_TOTAL:'C_NUMBER',
	PAID_METHOD:'SETTLEMENT__PAYMENT_TYPE'
};
//提货方式  汽运：PICKUPGOODSHIGHWAYS 【空运】：PICKUPGOODSAIR
querying.integrativeQuery.receiveMethod ={RECEIVE_METHOD:'PICKUPGOODSAIR'};
//自定义查询 实体中 active默认值
querying.integrativeQuery.active = {y:'Y',n:'N'};
//运输性质 数组
querying.integrativeQuery.productCodeArray = [];
//汽运 货物性质 后台编写常量 ，如若之后发现 更改或者不照   具体联系接送货
querying.integrativeQuery.qyGoodType = {a:'A',ah:'A货',b:'B',bh:'B货'};


//清空 运单详情信息
function cleanWaybillDetall (waybillVo){
	var record = new Foss.integrativeQueryIndex.Model.WaybillInfoModel({});
	Ext.getCmp('Foss_integrativeQueryIndex_Form_WaybillNOFullForm_Id')
	.getForm().findField('waybillNo').setValue(null);
	// 简单信息加载数据
	var waybillForm = Ext
			.getCmp('Foss_integrativeQueryIndex_SimpleRightBodyPanel_id');
	waybillForm.clearNatureForm();
	waybillForm.getForm().loadRecord(record);
	
	
	
	// 完整信息--运输信息加载数据
	var transportInfoForm = Ext
			.getCmp('Foss_integrativeQueryIndex_Form_TransportInfoForm_Id');
	transportInfoForm.clearNatureForm();
	transportInfoForm.getForm().loadRecord(record);
	// 完整信息--发货客户加载数据
	Ext
			.getCmp('Foss_integrativeQueryIndex_Form_DliverGoodsCustomerForm_Id')
			.getForm().loadRecord(record);
	// 完整信息--收货客户信息加载数据
	Ext
			.getCmp('Foss_integrativeQueryIndex_Form_ReceiveGoodsCustomerForm_Id')
			.getForm().loadRecord(record);
	// 完整信息--货物信息加载数据
	var cargoInfoForm = Ext
			.getCmp('Foss_integrativeQueryIndex_Form_CargoInfoForm_Id');
	cargoInfoForm.clearNatureForm();
	cargoInfoForm.getForm().loadRecord(record);
	// 完整信息--费用信息加载数据
	Ext.getCmp('Foss_integrativeQueryIndex_Form_OutlayInfoForm_Id').getForm().loadRecord(record);

	// 财务信息
	var financialInfoModel = new Foss.integrativeQueryIndex.Model.FinancialInfoModel({});
	var forms = Ext
			.getCmp('Foss_integrativeQueryIndex_FinancialTabPanel_id')
			.query('form');
	Ext.Array.each(forms, function(item, index, allForms) {
				item.getForm().loadRecord(financialInfoModel);
			})

	financialInfoModel = new Foss.integrativeQueryIndex.Model.FinancialInfoModel({});
	forms[0].getForm().loadRecord(financialInfoModel);

	// 打印标签记录
	Ext.getCmp('Foss_integrativeQueryIndex_LabelPrintingTabPanel_id').getStore().loadData([]);

	// 签收单
	var sinWaybilModel = new Foss.integrativeQueryIndex.Model.SignWaybillModel({});
	Ext.getCmp('Foss_integrativeQueryIndex_SignWaybillTabPanel_id').getForm().loadRecord(sinWaybilModel);
	// 跟踪记录
	Ext.getCmp('Foss_integrativeQueryIndex_TrackRecordGrid_id').store.loadData([]);
	// 货物轨迹（内部轨迹）
	Ext.getCmp('Foss_integrativeQueryIndex_Grid_InternalTrackingGrid_Id').store.loadData([]);
	// 运单更改
	Ext.getCmp('Foss_integrativeQueryIndex_StartChangeGrid_id').getStore().loadData([]);
	Ext.getCmp('Foss_integrativeQueryIndex_ArriveChangeGrid_id').getStore().loadData([]);
	// 状态信息
	var billOtherModel = new Foss.integrativeQueryIndex.Model.CargoTrackingInfoModel({});
	Ext.getCmp('Foss_integrativeQueryIndex_Form_StatusInfoForm_id').getForm().loadRecord(billOtherModel);
	Ext.getCmp('Foss_integrativeQueryIndex_Form_StatusInfoForm_id').down('grid').getStore().loadData([]);
	// 货物轨迹（按件查询）
	Ext.getCmp('Foss_integrativeQueryIndex_Grid_ExternalGrid_Id').store.loadData([]);
}

//跟踪记录
Ext.define('Foss.querying.integrativeQuery.TrackRecordEntity', {
	extend : 'Ext.data.Model',
	fields : [{name:'waybillNo',type:'string'},
      {name:'contacts',type:'string'},
      {name:'trackContent',type:'string'},
      {name:'createTime',
        type:'date',convert: dateConvert,defaultValue:null
      },
      {name:'createUserCode',type:'string'},
      {name:'createUserName',type:'string'},
      {name:'createOrgCode',type:'string'},
      {name:'traceCategories',type:'string'},//跟踪类别 
      {name:'postDate',type:'date',convert: dateConvert,defaultValue:null},//起草时间
      {name:'sendOrgName',type:'string'},//起草部门
      {name:'sendUserName',type:'string'},//起草人
      {name:'acceptedTime',type:'date',convert: dateConvert,defaultValue:null},//受理时间  
      {name:'acceptedOrgName',type:'string'},//受理部门
      {name:'acceptedMan',type:'string'},//受理人   
      {name:'acceptedRemark',type:'string'},//受理备注
      {name:'createOrgName',type:'string'}]
});

//打印标签记录  
Ext.define('Foss.integrativeQueryIndex.GoodsLabelPrintDtoModel', {
	extend : 'Ext.data.Model',
	fields : [/** 流水号*/
		{name:'serialNo',type:'string'},
		/** 关联原流水号*/
		{name:'originalSerialNo',type:'string'},
		/** 打印时间*/
		{name:'printTime',type:'date',convert: dateConvert,defaultValue:null},
		/** 打印人姓名*/
		{name:'printUserName',type:'string'},
		/** 打印人所在部门名称*/
		{name:'printUserOrgName',type:'string'},
		/** 打印次序*/
		{name:'printOrder',type:'string'},
		/** 打印标签类型*/
		{name:'lableType',type:'string'}
		]
});

//代理信息
Ext.define('Foss.integrativeQueryIndex.AgentInformationDtoModel', {
	extend : 'Ext.data.Model',
	fields : [
	     /** 流水号 */
	     {name:'serialNo',type:'string'},
	    /** 代理单号-由跟踪内容改的名字-(跟踪内容)*/
		{name:'traceContent',type:'string'},
		/** 跟踪时间*/
		{name:'operatTime',type:'string' ,convert: dateConvert},
		/** 跟踪人*/
		{name:'operatorName',type:'string'},
		/** 状态 */
		{name:'status',type:'string'},
		{name:'depTelephone',type:'string'},
		{name:'orgName',type:'string'}]
});


//按件查询model
Ext.define('Foss.querying.integrativeQuery.LabeledGoodEntityModel', {
	extend : 'Ext.data.Model',
	fields : [
		/**
		 * 打印流水号
		 */
		{name:'serialNo',type:'string'},
		
		/**
		 * 状态
		 */
		{name:'active',type:'string'},
		
		/**
		 * 运单号
		 */
		{name:'waybillNo',type:'string'},
		
		/**
		 * 创建时间
		 */
		{name:'createTime',type:'date',convert: dateConvert,defaultValue:null},
		
		/**
		 * 修改时间
		 */
		{name:'modifyTime',type:'date',convert: dateConvert,defaultValue:null},
		
		/**
		 * 开单时间
		 */
		{name:'billTime',type:'date',convert: dateConvert,defaultValue:null},
		
		/**
		 * 关联原单号流水号
		 */
		{name:'oldSerialNo',type:'string'},
		
		/**
		 * 件数变动事项
		 */
		{name:'numberChangItems',type:'string'},
		
		/**
		 * 是否开单初始值
		 */
		{name:'initalVale',type:'string'},
		
		/**
		 * 是否需要打木架
		 */
		{name:'isNeedWooden',type:'string'}]
});

//子母件轨迹model
Ext.define('Foss.querying.integrativeQuery.ZMJLocusModel', {
	extend : 'Ext.data.Model',
	fields : [
		/**
		 * 子母件关联单
		 */
		{name:'ZMJMark',type:'string'},

		/**
		 * 运单号
		 */
		{name:'waybillNo',type:'string'}
		
	]
});

// 定义运单信息（简单）模型
Ext.define('Foss.integrativeQueryIndex.Model.WaybillInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 运单号
						name : 'waybillNo',
						type : 'string'
					}, {
						// 收货部门 --code到名称的转换 TODO
						name : 'receiveOrgName',
						type : 'string'
					},{
						// 发货人客户分群
						name : 'flabelleavemonth',
						type : 'string'
					}, {
						// 收货客户名称
						name : 'receiveCustomerName',
						type : 'string'
					}, {
						// 收货电话
						name : 'receiveCustomerPhone',
						type : 'string'
					}, {
						// 收货人地址
						name : 'receiveCustomerAddress',
						type : 'string'
					}, {
						// 提货网点
						name : 'customerPickupOrgName',
						type : 'string'
					}, {
						//提货网点电话
						name : 'customerPickupOrgPhone',
						type : 'string'
					},{
						// 提货方式
						name : 'receiveMethod',
						type : 'string'
					}, {
						// 发货客户名称
						name : 'deliveryCustomerName',
						type : 'string'
					}, {
						// 运输性质
						name : 'productCode',
						type : 'string'
					},'productName', {
						// 目的站
						name : 'targetOrgCode',
						type : 'string'
					}, {
						// 货物品名
						name : 'goodsName',
						type : 'string'
					},{
						// 货物品名
						name : 'category',
						type : 'string'
					}, {
						// 付款方式
						name : 'paidMethod',
						type : 'string'
					}, {
						// 包装
						name : 'goodsPackage',
						type : 'string'
					}, {
						// 到付金额
						name : 'toPayAmount',
						type : 'string'
					}, {
						// 总件数
						name : 'goodsQtyTotal',
						type : 'string'
					}, {
						// 返单类别
						name : 'returnBillType',
						type : 'string'
					}, {
						// 总重量
						name : 'goodsWeightTotal',
						type : 'string'
					}, {
						// 总重量
						name : 'goodsVolumeTotal',
						type : 'string'
					},{
						// 子母件总重量
						name : 'czmGoodsWeightTotal',
						type : 'string'
					},{
						// 子母件总重量
						name : 'czmGoodsVolumeTotal',
						type : 'string'
					},{
						// 集中接送货
						name : 'pickupCentralized',
						type : 'string'
					}, {
						// 是否展会货
						name : 'isExhibitCargo',
						type : 'string'
					},{
						// 贵重物品
						name : 'preciousGoods',
						type : 'string'
					}, {
						// 卡车直送
						name : 'carDirectDelivery',
						type : 'string'
					}, {
						// 特殊物品
						name : 'specialShapedGoods',
						type : 'string'
					}, {
						// 整车
						name : 'isWholeVehicle',
						type : 'string'
					}, {
						// 是否经过营业部
						name : 'isPassOwnDepartment',
						type : 'string'
					}, {
						name : 'receiveOrgPhone',
						type : 'string'
					}, // 收货部门电话
					{
						name : 'loadMethod',
						type : 'string'
					}, // 配载类型
					{
						name : 'loadOrgCode',
						type : 'string'
					}, // 配载部门
					'loadOrgName',{
						name : 'loadMethod',
						type : 'string'
					}, // 预配线路/航班
					{
						name : 'flightShift',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 航班日期
					{
						name : 'flightNumberType',
						type : 'string'
					},//订单来源
					{
						name:'orderChannel',
						type:'string'
					},{
						//开单快递员
						name:'billingcourier',
						type:'string'
					},{
						//签收快递员
						name:'signcourier',
						type:'string'
					},
					//航班类型
					{
						name : 'createUserName',
						type : 'string'
					},  
					{ //提货网点地址
						name : 'customerPickupOrgAddress',
						type : 'string'
					}, {
						name : 'deliveryCustomerCode',
						type : 'string'
					}, // 发货客户编码
					{
						name : 'deliveryCustomerMobilephone',
						type : 'string'
					}, // 发货客户手机
					{
						name : 'deliveryCustomerPhone',
						type : 'string'
					}, // 发货客户电话
					{
						name : 'deliveryCustomerContact',
						type : 'string'
					},// 发货联系人
					{
						name : 'deliveryCustomerAddress',
						type : 'string'
					}, {
						name : 'receiveCustomerCode',
						type : 'string'
					}, // 收货客户编码
					{
						name : 'receiveCustomerMobilephone',
						type : 'string'
					}, // 收货客户手机
					{
						name : 'receiveCustomerContact',
						type : 'string'
					},// 收货联系人
					{
						name : 'receiverAddress',
						type : 'string'
					}, // 运单号
					{
						name : 'goodsTypeCode',
						type : 'string'
					}, // 货物类型（A、B）
					{
						name : 'goodsSize',
						type : 'string'
					}, // 尺寸
					{
						name : 'innerNotes',
						type : 'string'
					}, // 对内备注
					{
						name : 'outerNotes',
						type : 'string'
					}, // 对外备注
					{
						name : 'transportationRemark',
						type : 'string'
					}, // 储运事项
					{
						name : 'billingType',
						type : 'string'
					}, // 计费类型
					{
						name : 'transportFee',
						type : 'string'
					}, // 运价
					{
						name : 'totalFee',
						type : 'string'
					}, // 运费
					{
						name : 'prePayAmount',
						type : 'string'
					}, // 预付金额
					{
						name : 'insuranceAmount',
						type : 'string'
					}, // 保险价值
					{
						name : 'insuranceFee',
						type : 'string'
					}, // 保价费
					{
						name : 'codAmount',
						type : 'string'
					}, // 代收货款
					{
						name : 'codFee',
						type : 'string'
					},'codRate', // 货款费率
					{
						name : 'refundType',
						type : 'string'
					}, // 退款类型
					{
						name : 'pickUpFee',
						type : 'string'
					}, // 接货费
					{
						name : 'deliveryGoodsFee',
						type : 'string'
					}, // 送货费
					{
						name : 'packageFee',
						type : 'string'
					}, // 包装费
					{
						name : 'serviceFee',
						type : 'string'
					}, // 装卸费
					{
						name : 'otherFee',
						type : 'string'
					},// 其他费用
					{
						name : 'waybillStatus',
						type : 'string'
					}, // 运单状态
					{
						name : 'secretPrepaid',
						type : 'string'
					},//是否保密
					{
						name:'freightMethod',
						type:'string'
					},//配载类型（合票）
					//wp_078816_20140522添加约车编码
					{
						name:'orderVehicleNum',
						type:'string'
					}//添加约车编码
					,
					{
					 name:'receiveBigCustomer',
					 type:'string'
					},//收货客户是否大客户
					{
					 name:'deliveryBigCustomer',
					 type:'string'
					},//发货客户是否大客户
					{
					 name:'waybillType',
					 type:'string'
					},// 运单类型
					{
						 name:'returnWaybillNo',
						 type:'string'
					},// 返货单号
					{
						 name:'originalWaybillNo',
						 type:'string'
					},// 原单单号
					{
						 name:'returnSignWaybillNo',
						 type:'string'
					},// 签收单返单号
					{
						 name:'originalSignWaybillNo',
						 type:'string'
					},// 签收单原单号
					{
						 name:'toPayAmountDiscount',
						 type:'string'
					},// 到付折扣金额
					{
						 name:'prePayAmountDiscount',
						 type:'string'
					}, // 预付折扣金额
					{
						name:'originalWaybillNoTotalFee',
						type:'string'
					}, //原单总金额
					{
						name:'oriAndReturntotalFee',
						type:'string'
					}  //原单和返单总金额
			]
		});

// 定义状态信息模型
Ext.define('Foss.integrativeQueryIndex.Model.StatusInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'billTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 开单时间
					{
						name : 'preCustomerPickupTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 承诺到达时间
					{
						name : 'loadLineCode',
						type:'string'
					}, // 计划走货路径
					{
						name : 'actualPath',
						type:'string'
					} // 实际走货路径
			]
		});

// 定义货物轨迹-内部轨迹信息模型
Ext.define('Foss.integrativeQueryIndex.Model.InternalTrackingInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'department',
						type : 'string'
					}, // 部门
					{
						name : 'trackTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 时间
					{
						name : 'taskCode',
						type : 'string'
					}, // 任务编号
					{
						name : 'operate',
						type : 'string'
					},// 操作
					{
						name : 'time01',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 轨迹时间01
					{
						name : 'time02',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 轨迹时间02
					{
						name : 'time03',
						type:'date',convert: dateConvert,defaultValue:null
					} // 轨迹时间03
			]
		});

// 定义货物轨迹-内部轨迹信息模型
Ext.define('Foss.integrativeQueryIndex.Model.InternalTrackingInfoModel', {
	extend : 'Ext.data.Model',
	fields : [/************* 运单号****************/
      {name:'waybillNo',type:'string'},
      /************* 操作部门名称 ****************/
      {name:'operateOrgName',type:'string'},
      /************* 操作部门标杆编码 ****************/
      {name:'unifiedCode',type:'string'},
      /************* 操作部门编码 ****************/
      {name:'operateOrgCode',type:'string'},
      /************* 操作部门所在城市编码 ****************/
      {name:'operateCityCode',type:'string'},
      /************* 操作部门所在城市名称 ****************/
      {name:'operateCityName',type:'string'},
      /************* 操作类型****************/
      {name:'operateType',type:'string'},
      /************* 操作类型 名称****************/
      {name:'operateTypeName',type:'string'},
      /************* 操作内容 ****************/
      {name:'operateContent',type:'string'},
      /************* 操作时间 ****************/
      {name:'operateTime',type:'date',convert: dateConvert,defaultValue:null},
      /************* 操作人姓名 ****************/
      {name:'operateName',type:'string'},
      /************* 操作件数 ****************/
      {name:'operateNumber',type:'int'},
      /************* 单据编号 ****************/
      {name:'billNo',type:'string'},
      /************* 车牌号 ****************/
      {name:'vehicleNo',type:'string'},
      /************* 标杆编码 ****************/
      {name:'stockPositionNumber',type:'string'},
      /************* 备注 ****************/
      {name:'notes',type:'string'},
      /************* 流水号 ****************/
      {name:'serialNo',type:'string'},
      /************* 下一站部门编码 ****************/
      {name:'nextOrgCode',type:'string'},
      /************* 下一站部门名称 ****************/
      {name:'nextOrgName',type:'string'},
      /************* 下一站所在城市编码 ****************/
      {name:'nextCityCode',type:'string'},
      /************* 下一站所在城市名称 ****************/
      {name:'nextCityName',type:'string'},
      /************* 目的站部门编码 ****************/
      {name:'destinationStationOrgCode',type:'string'},
      /************* 目的站部门名称 ****************/
      {name:'destinationStationOrgName',type:'string'},
      /************* 目的站部门所在城市编码****************/
      {name:'destinationStationCityCode',type:'string'},
      /************* 目的站部门所在城市名称 ****************/
      {name:'destinationStationCityName',type:'string'},
      /************* 离开后预计到达下一操作部门时间 ****************/
      {name:'planArriveTime',type:'date',convert: dateConvert,defaultValue:null},
      /************* 派送人员姓名****************/
      {name:'deliveryName',type:'string'},
      /************* 货物状态****************/
      {name:'currentStatus',type:'string'},
      /************* 派送人员电话 ****************/
      {name:'deliveryPhone',type:'string'},
      /************* 签收人姓名 ****************/
      {name:'signManName',type:'string'}]
});

// 定义货物轨迹-按件查询(外部轨迹)列表信息模型
Ext.define('Foss.integrativeQueryIndex.Model.ExternalTrackingInfoModel', {
	extend : 'Ext.data.Model',
	fields : [{
				name : 'operatType',
				type : 'string'
			}, // 操作类型
			{
				name : 'operatContent',
				type : 'string'
			}, // 操作内容
			{
				name : 'operatTime',
				type:'date',convert: dateConvert,defaultValue:null
			}, // 操作时间
			{
				name : 'operater',
				type : 'string'
			},// 操作人
			'operateName',{
				name : 'goodsQty',
				type : 'int'
			}, // 操作件数
			{
				name : 'billsNum',
				type : 'string'
			}, // 单据编号
			{
				name : 'plateNum',
				type : 'string'
			}, // 车牌号
			{
				name : 'note',
				type : 'string'
			} // 备注
	],
	belongsTo : 'Foss.integrativeQueryIndex.Model.ExternalTrackingBasicInfoModel'
});

// 定义货物轨迹-按件查询(外部轨迹)基本信息模型
Ext.define('Foss.integrativeQueryIndex.Model.ExternalTrackingBasicInfoModel',
		{
			extend : 'Ext.data.Model',
			fields : [{
						name : 'signInNum',
						type : 'string'
					}, // 生成序号
					{
						name : 'signInTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 签收时间
					{
						name : 'signInDept',
						type : 'string'
					}, // 签收部门
					{
						name : 'signInType',
						type : 'string'
					} // 签收类型
			],

			hasMany : {
				model : 'Foss.integrativeQueryIndex.Model.ExternalTrackingInfoModel',
				name : 'externalTrackingInfoModels'
			}

			/*
			 * proxy:{ type:'rest', url:'externalTrackingInfoModels.json',
			 * reader:{ type:'json', root:'externalTrackingInfoModels' } }
			 */
		});
// 定义货物轨迹信息模型
Ext.define('Foss.integrativeQueryIndex.Model.CargoTrackingInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 货物状态
						name : 'action',
						type : 'string'
					}, {
						// 件数
						name : 'goodsCount',
						type : 'string'
					}, {
						// 当前位置
						name : 'nowPosition',
						type : 'string'
					}, {
						// 预计到达下一部门
						name : 'planArriveTime',
						type:'date',convert: dateConvert,defaultValue:null
					},{
						// 预计到下部门名称
						name : 'nextOrgCode',
						type : 'string'
					}, {
						// 预计派送/提货时间
						name : 'planPickupTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 是否可提货
						name : 'ifAvailedPickup',
						type : 'string'
					}, {
						// 开单时间
						name : 'billTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 承诺到达时间/提货时间
						name : 'preCustomerPickupTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 开单组织
						name : 'createOrgCode',
						type : 'string'
					}, {
						// 干线走货路径 -- 配载线路virtual code
						name : 'loadLineCode',
						type : 'string'
					}, {
						name : 'actualPath',
						type:'string'
					}, // 实际走货路径
					{
						// 到达部门 -- 提货网点
						name : 'customerPickupOrgCode',
						type : 'string'
					}, {
						//【保管费/仓储费】
						name : 'storageCharge',
						type : 'string'
					}, {
						//流水号
						name : 'serials',
						type : 'string'
					}]
		});
//------------------------------查看收货人--------------------------------------------
		
		
//用来存储交互“客户”的数据库对应实体MODEL
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerModel', {
extend: 'Ext.data.Model',
fields : [//客户地址
    {name:'address',type:'string'},
    //客户属性
    {name:'property',type:'string'},
    //客户类型
    {name:'type',type:'string'},
    //信用额度
    {name:'creditAmount',type:'number'},
    //客户名称
    {name:'name',type:'string'},
    //营业执照号
    {name:'license',type:'string'},
    //客户所属部门标杆编码
    {name:'unifiedCode',type:'string'},
    //客户编码
    {name:'cusCode',type:'string'},
    //客户是否有效
    {name:'activeCus',type:'string'},
    //月结客户总欠款
    {name:'totalArrears',type:'number'},
    //结算方式
    {name:'chargeMode',type:'string'},
    //客户等级
    {name:'degree',type:'string'},
    //CRM客户ID
    {name:'crmCusId',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //是否异地调货
    {name:'fistrangoods',type:'string'},
    
    //部门名称
    {name:'deptname',type:'string'},
    {name:'deptName',type:'string'},
    {name:'contactList',defaultValue:[]},
    {name:'bankAccountList',defaultValue:[]},
    {name:'bargainList',defaultValue:[]},
    
    
    //所在省份名称
    {name:'provName',type:'string'},
    //所在城市名称
    {name:'cityName',type:'string'},
    //客户简称 simpleName
    {name:'simpleName',type:'string'},
    //是否重要客户
    {name:'isImport',type:'string'},
    //是否特殊客户
    {name:'isSpecial',type:'string'},
    //发票抬头
    {name:'billTitle',type:'string'},
    
    //客户合同-优惠信息集合（封装）
    {name:'bargainPreferList',defaultValue:[]},
    //客户联系人-偏好地址-客户接送货地址
    {name:'contactAddressList',defaultValue:[]}]
});
//用来存储交互“联系人”的数据库对应实体MODEL
Ext.define('Foss.integrativeQueryIndex.customerIndex.LinkmanModel', {
extend: 'Ext.data.Model',
fields : [//性别
    {name:'gender',type:'string'},
    //办公电话
    {name:'contactPhone',type:'string'},
    //移动电话
    {name:'mobilePhone',type:'string'},
    //传真
    {name:'faxNo',type:'string'},
    //联系人地址
    {name:'address',type:'string'},
    //电子邮箱
    {name:'email',type:'string'},
    //邮编
    {name:'zipCode',type:'string'},
    //生日
    {name:'birthday',type:'date'},
    //身份证号
    {name:'idCard',type:'string'},
    //个人爱好
    {name:'hobby',type:'string'},
    //是否接收邮件
    {name:'receiveEmail',type:'string'},
    //是否接收短信
    {name:'receiveMessage',type:'string'},
    //是否接收信件
    {name:'receiveLetter',type:'string'},
    //获知公司途径
    {name:'way',type:'string'},
    //民族
    {name:'nation',type:'string'},
    //籍贯
    {name:'hometown',type:'string'},
    //职务
    {name:'title',type:'string'},
    //任职部门
    {name:'workingDept',type:'string'},
    //联系人姓名
    {name:'contactName',type:'string'},
    //备注
    {name:'notes',type:'string'},
    //联系人类型
    {name:'contactType',type:'string'},
    //是否主联系人
    {name:'mainContract',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //客户编码
    {name:'customerCode',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //在CRM中FID
    {name:'crmId',defaultValue:null}, 
    //一个客户联系人对应多个联系人—地址（偏好地址）
    {name:'contactAddrList',defaultValue:[]},
    
    //接送货地址
    {name:'contactAddress',type:'string'},
    //详细地址（接送街道）
    {name:'address',type:'string'},
    //邮编
    {name:'zipCode',type:'string'},
    //省份
    {name:'provinceName',type:'string'},
    //城市
    {name:'cityCode',type:'string'},
    //区县
    {name:'countyCode',type:'string'},
    //地址类型
    {name:'addressType',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null}]
});
//用来存储交互“客户合同”的数据库对应实体MODEL
Ext.define('Foss.integrativeQueryIndex.customerIndex.CusBargainModel', {
extend: 'Ext.data.Model',
fields : [//付款方式
    {name:'chargeType',type:'string'},
    //申请欠款额度
    {name:'arrearsAmount',type:'number'},
    //协议联系人姓名
    {name:'name',type:'string'},
    //联系人固定电话
    {name:'contactPhone',type:'string'},
    //联系人手机
    {name:'mobilePhone',type:'string'},
    //联系人详细地址
    {name:'address',type:'string'},
    //对账日期
    {name:'statementDate',defaultValue:null},
    //开发票日期
    {name:'invoicingDate',defaultValue:null},
    //结款日期
    {name:'checkoutDate',defaultValue:null},
    //申请事由
    {name:'applyReason',type:'string'},
    //所属部门标杆编码
    {name:'unifiedCode',type:'string'},
    //合同适用部门
    {name:'applicateOrgId',type:'string'},
    //是否折扣
    {name:'discount',type:'string'},
    //合同状态
    {name:'status',type:'string'},
    //合同主体
    {name:'bargainSubject',type:'string'},
    //注册资金
    {name:'registerFunds',type:'number'},
    //原合同编号
    {name:'lastBargain',type:'string'},
    //合同编号
    {name:'bargainCode',type:'string'},
    //走货名称
    {name:'transName',type:'string'},
    //客户全称
    {name:'customerName',type:'string'},
    //协议联系人
    {name:'linkmanId',type:'string'},
    //结算方式
    {name:'chargeMode',type:'string'},
    //优惠类型
    {name:'preferentialType',type:'string'},
    //已用额度
    {name:'usedAmount',type:'number'},
    //是否超期
    {name:'overdue',type:'string'},
    //业务日期
    {name:'bizDate',type:'date'},
    //合同起始日期
    {name:'beginTime',type:'date',convert: dateConvert,defaultValue:null},
    //合同到期日期
    {name:'endTime',type:'date',convert: dateConvert,defaultValue:null},
    //是否启用
    {name:'active',type:'string'},
    //客户
    {name:'customerCode',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //在CRM中fid
    {name:'crmId',defaultValue:null}, 
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //一个客户合同对应多个合同适用部门
    {name:'appOrgList',defaultValue:[]},
    {name:'applyDepts',type:'string'},
    
    //运费折扣费率
    {name:'chargeRebate',defaultValue:null},
    //代收货款费率
    {name:'agentGathRate',defaultValue:null},
    //保价费率
    {name:'insureDpriceRate',defaultValue:null},
    //接货费率
    {name:'receivePriceRate',defaultValue:null},
    //送货费率
    {name:'deliveryFeeRate',defaultValue:null}]
});
//用来存储交互“银行账户信息”的数据库对应实体MODEL
Ext.define('Foss.integrativeQueryIndex.customerIndex.CusAccountModel', {
extend: 'Ext.data.Model',
fields : [//其他支行名称
    {name:'otherBranchBankName',type:'string'},
    //开户账号
    {name:'accountNo',type:'string'},
    //开户人姓名
    {name:'accountName',type:'string'},
    //开户行城市编码
    {name:'cityCode',type:'string'},
    //开户行省份编码
    {name:'provCode',type:'string'},
    //开户行编码
    {name:'bankCode',type:'string'},
    //手机号码
    {name:'mobilePhone',type:'string'},
    //账号与客户关系
    {name:'customer',type:'string'},
    //是否默认账号
    {name:'defaultAccount',type:'string'},
    //支行编码
    {name:'branchBankCode',type:'string'},
    //备注
    {name:'notes',type:'string'},
    //是否启用
    {name:'active',type:'string'},
    //虚拟编码
    {name:'virtualCode',type:'string'},
    //账户性质 对公；对私两种
    {name:'accountNature',type:'string'},
    //与客户信息是多对一关系
    {name:'customerDto',defaultValue:null},
    //在CRM中FID
    {name:'crmId',defaultValue:null}, 
    //开户行所在城市名称
    {name:'cityName',type:'string'},
    //开户行省份名称
    {name:'provinceName',type:'string'},
    //开户行名称
    {name:'openingBankName',type:'string'},
    //支行名称
    {name:'branchBankName',type:'string'},
    //所属客户ID
    {name:'belongCustom',defaultValue:null},
    //财务联系人名称
    {name:'financeLinkman',type:'string'},
    //账户用途
    {name:'accountUse',type:'string'}]
});

//客户 窗体 form
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerWinForm', {
	extend : 'Ext.form.Panel',
	frame: true,
	defaultType : 'textfield',
	layout:{
        type: 'table',
        columns: 2
    },
    record:null,												//绑定的model Foss.integrativeQueryIndex.customerIndex.CustomerModel
	constructor : function(config) {							//构造器
		var me = this,cfg = Ext.apply({}, config);
		me.defaults = me.getDefaults();
		me.items = me.getItems();
		me.callParent([cfg]);
		me.loadRecord(Ext.isEmpty(me.record)?Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerModel'):me.record);
	},
	getDefaults:function(){
		return {
	    	margin : '8 10 5 10',
	    	//labelSeparator:'',
	    	labelWidth:110,
	    	width:375,
	    	allowBlank:true,
	    	readOnly:true
	    };
	},
	getItems:function(){
		var me = this;
		return [{
	        fieldLabel: querying.integrativeQuery.i18n('foss.querying.department'),		//所属部门
			name:'deptname'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.customerCode'),		//客户编码
			name:'cusCode'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.customerSimpleName'),		//客户简称
			name:'simpleName'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.customerName'),		//客户名称
			name:'name'
		},FossDataDictionary.getDataDictionaryCombo('CRM_CUSTOMER_GRADE',{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.customerRating'),    //客户等级
			name: 'degree',
	    	labelWidth:110,
	    	width:375,
	    	readOnly:true
		})
		//,{
			//fieldLabel:baseinfo.customerIndex.i18n('foss.baseinfo.customer.settlementStyle'),									//省份
			//name:'chargeMode'
		//}
		,FossDataDictionary.getDataDictionaryCombo('CRM_CUSTOMER_ATTRIBUTE',{
			fieldLabel:'客户属性',  //客户属性
			name: 'property',
	    	labelWidth:110,
	    	width:375,
	    	readOnly:true
		})
		,{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.provinceName'),			//所在省份
			name:'provName'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.cityName'),			//所在城市
			name:'cityName'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.companyAddress'),			//公司地址
			name:'address'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.customerLicense'),			//客户税务登记号
			name:'license',
			renderer:function(value){
				if(null == value){
					return '';
				}else{
					return value;
				}
			}
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.whetherImportantCustomer'),	//是否重要客户
			xtype:'combobox',
			store: Ext.create('Ext.data.Store', {
			    fields: ['code', 'name'],
			    data : [{'code':'Y','name':'是'},{'code':'N','name':'否'}]}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
			name:'isImport'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.whetherSpecialCustomer'),	//是否特殊客户
			xtype:'combobox',
			store: Ext.create('Ext.data.Store', {
			    fields: ['code', 'name'],
			    data : [{'code':'Y','name':'是'},{'code':'N','name':'否'}]}),
		    queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',							
			name:'isSpecial'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.creditAmount'),	//信用额度(元)
			name:'creditAmount'
		},{
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.billTitle'),		//发票抬头
			name:'billTitle'
		}
		//,{
		//	fieldLabel:'应收金额(元)',									
		//	name:' '
		//}
		];
	}
});

/**
 * 客户查询 联系人信息
 */
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerWinLinkmanGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: querying.integrativeQuery.i18n('foss.querying.emptyText'),	//查询结果为空
	viewConfig:{
	    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	frame: true,
	columns:[{text : querying.integrativeQuery.i18n('foss.querying.contactName'),dataIndex : 'contactName'}  //联系人姓名
		,{text : querying.integrativeQuery.i18n('foss.querying.mobilePhone'),dataIndex : 'mobilePhone'}  //手机号码
		,{text : querying.integrativeQuery.i18n('foss.querying.contactType'),dataIndex : 'contactType'  //联系人类型
			,renderer:function(v){
				v = Ext.isEmpty(v)?[]:v.split(',');
				return querying.integrativeQuery.convertInfo(v);
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.mainContract'),dataIndex : 'mainContract'  //是否主联系人
			,renderer:function(v){
				return 'Y' == v?'是':'否';
			}
		}
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.contactAddress'),dataIndex : 'contactAddress'}  //接送货地址
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.zipCode'),dataIndex : 'zipCode'}  //接送货地址邮编
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.proName'),dataIndex : 'provinceName'}  //接送货省份
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.cityCode'),dataIndex : 'cityCode'}  //接送货城市
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.countyCode'),dataIndex : 'countyCode'}  //接送货区县
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.street'),dataIndex : 'address'}  //接送货街道
		,{sortable: false,text : querying.integrativeQuery.i18n('foss.querying.addressType'),dataIndex : 'addressType'  //接送货地址类型
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ADDRESS_TYPE');
			}}],
	//选择模式
	selType: 'cellmodel',
    //增加表格列的分割线
	columnLines: true,
	viewConfig: {
		stripeRows: false,
		listeners: {
			viewready: {
				fn: function(view) {
					var grid = view.up('grid');
					mergeCells(grid, [1,2,3,4]);
				}
			}
		}
	}
});
/**
 * 客户查询 合同信息
 */
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerWinContractGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: querying.integrativeQuery.i18n('foss.querying.emptyText'),		//查询结果为空
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	frame: true,
	columns:[{text : querying.integrativeQuery.i18n('foss.querying.bargainCode'),dataIndex : 'bargainCode'}  //合同编码
		,{text : querying.integrativeQuery.i18n('foss.querying.chargeType'),dataIndex : 'chargeType'  //结算方式
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CLEARING_TYPE');
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.preferentialType'),dataIndex : 'preferentialType'  // 优惠类型
			,renderer:function(v){
				
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_PREFERENTIAL_TYPE');
				return v;
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.beginTime'),dataIndex : 'beginTime'  //生效日期
			,renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				return v;
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.endTime'),dataIndex : 'endTime'  //失效日期
			,renderer:function(v){
				if(!Ext.isEmpty(v)){
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				return v;
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.arrearsAmount'),dataIndex : 'arrearsAmount'}   //申请欠款额度(元)
		,{text : querying.integrativeQuery.i18n('foss.querying.agreementName'),dataIndex : 'name'}  //协议联系人
		,{text : querying.integrativeQuery.i18n('foss.querying.chargeRebate'),dataIndex : 'chargeRebate'}  //运费折扣费率
		,{text : querying.integrativeQuery.i18n('foss.querying.insureDpriceRate'),dataIndex : 'insureDpriceRate'}  //保价费率
		,{text : querying.integrativeQuery.i18n('foss.querying.agentGathRate'),dataIndex : 'agentGathRate'}  //代收费率
		,{text : querying.integrativeQuery.i18n('foss.querying.receivePriceRate'),dataIndex : 'receivePriceRate'}  //接货费率
		,{text : querying.integrativeQuery.i18n('foss.querying.deliveryFeeRate'),dataIndex : 'deliveryFeeRate'}  //送货费率
		,{text : querying.integrativeQuery.i18n('foss.querying.applyDepts'),	dataIndex : 'applyDepts'}],  //绑定部门
	//选择模式
	selType: 'cellmodel',	
	viewConfig: {
		stripeRows: false,
		listeners: {
			viewready: {
				fn: function(view) {
//					var grid = view.up('grid');
//					mergeCells(grid, [1,2,3,4,5,6,7]);
				}
			}
		}
	}
});
/**
 * 客户查询 财务信息
 */
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerWinAccountGrid', {
	extend: 'Ext.grid.Panel',
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	autoScroll:true,									//滚动轴
	stripeRows : true, 									// 交替行效果
	selType : "rowmodel", 								// 选择类型设置为：行选择
	emptyText: querying.integrativeQuery.i18n('foss.querying.emptyText'),							//查询结果为空
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	frame:true,
	columns:[{text : querying.integrativeQuery.i18n('foss.querying.openingBankName'),dataIndex : 'openingBankName'}  //开户行
		,{text : querying.integrativeQuery.i18n('foss.querying.accountName'),dataIndex : 'accountName'}  //开户姓名
		,{text : querying.integrativeQuery.i18n('foss.querying.accountNo'),dataIndex : 'accountNo'}  //银行账号
		,{text : querying.integrativeQuery.i18n('foss.querying.branchBankName'),dataIndex : 'branchBankName'}  //支行名称
		,{text : querying.integrativeQuery.i18n('foss.querying.accountNature'),dataIndex : 'accountNature'  //账户性质
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ACCOUNT_NATURE');
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.accountProvinceName'),dataIndex : 'provinceName'}  //开户省
		,{text : querying.integrativeQuery.i18n('foss.querying.accountCityName'),	dataIndex : 'cityName'}  //开户城市
		,{text : querying.integrativeQuery.i18n('foss.querying.accountUse'),dataIndex : 'accountUse'  //账户用途
			,renderer:function(v){
				return FossDataDictionary. rendererSubmitToDisplay (v,'CRM_ACCOUNT_USE');
			}}
		,{text : querying.integrativeQuery.i18n('foss.querying.financeLinkman'),	dataIndex : 'financeLinkman'}  //财务联系人
		,{text : querying.integrativeQuery.i18n('foss.querying.defaultAccount'),	dataIndex : 'defaultAccount'  //是否默认账号
			,renderer:function(v){
				return 'Y'==v?'是':'否';
			}}]
});

/**
 * 客户window
 */
Ext.define('Foss.integrativeQueryIndex.customerIndex.CustomerWin',{
	extend : 'Ext.window.Window',
	title : querying.integrativeQuery.i18n('foss.querying.customerDetails'),										//客户详情   
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :850,
	height :600,		
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	autoScroll:true,										//滚动轴
	editForm:null,											//客户表单Form   Foss.integrativeQueryIndex.customerIndex.CustomerWinForm
	linkmanGrid:null,										//客户联系人Grid Foss.integrativeQueryIndex.customerIndex.CustomerWinLinkmanGrid
	contractGrid:null,										//客户合同Grid   Foss.integrativeQueryIndex.customerIndex.CustomerWinContractGrid
	accountGrid:null,										//客户财务Grid   Foss.integrativeQueryIndex.customerIndex.CustomerWinAccountGrid
	sourceGrid:null,										//来源表格 Grid
	viewState:null,											//查看状态 baseinfo.customerIndex.viewCustomerState
	formRecord:null,										//客户实体 Foss.baseinfo.BusinessPartnerModel
	//得到客户编辑窗体
	resetValues:function(custDto){
		var me = this;
		var formRecord = Ext.isEmpty(custDto)?Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerModel'):Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerModel',custDto);
		
		//加载数据
		this.editForm.loadRecord(formRecord);
		//wp_078816_20140506增加大客户标记
		//判断发货客户如果是大客户，就在后面加VIP标记
		if(formRecord.get('deliveryBigCustomer')=== 'Y'){
			this.editForm.findField('cusCode').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.customerCode')
					+'<span  class="big_Customer_pic_common">');
		}else{
			this.editForm.findField('cusCode').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.customerCode'));
		}
		//加载 联系人，合同，账号 信息		
		this.linkmanGrid.store.loadData(Ext.isEmpty(formRecord.get('contactAddressList'))?[]:formRecord.get('contactAddressList'));
		this.contractGrid.store.loadData(Ext.isEmpty(formRecord.get('bargainPreferList'))?[]:formRecord.get('bargainPreferList'));
		this.accountGrid.store.loadData(Ext.isEmpty(formRecord.get('bankAccountList'))?[]:formRecord.get('bankAccountList'));
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.editForm = Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerWinForm',{'height':360});
		//查询结果 联系人 显示列表
		me.linkmanGrid  = Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerWinLinkmanGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.integrativeQueryIndex.customerIndex.LinkmanModel',
				data:[]
			})
		});
		//查询结果 合同 显示列表
		me.contractGrid  = Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerWinContractGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.integrativeQueryIndex.customerIndex.CusBargainModel',
				data:[]
			})
		});
		//查询结果 账户，财务 显示列表
		me.accountGrid  = Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerWinAccountGrid',{
			'height':150,
			store:Ext.create('Ext.data.Store',{
				model : 'Foss.integrativeQueryIndex.customerIndex.CusAccountModel',
				data:[]
			})
		});
		me.items = [{html : querying.integrativeQuery.i18n('foss.querying.customerInformation')},me.editForm,  //客户基本信息：
		            {html : querying.integrativeQuery.i18n('foss.querying.linkmanInformation')},me.linkmanGrid,  //联系人信息：
		            {html : querying.integrativeQuery.i18n('foss.querying.contractInformation')},me.contractGrid,  //合同信息：
		            {html : querying.integrativeQuery.i18n('foss.querying.accountInformation')},me.accountGrid];  //财务信息：
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	//操作界面上的按钮
	getFbar:function(){
		var me = this;
		return [{
			text : querying.integrativeQuery.i18n('foss.querying.return'),  //返回
			margin : '0 720 0 0',
			handler :function(){
				me.hide();
			} 
		}];
	}
});

//-------------------------------------------------------------------------------
// 定义货物轨迹信息Store
Ext.define('Foss.integrativeQueryIndex.Store.CargoTrackingInfoStore', {
	extend : 'Ext.data.Store',
	// 绑定一个模型
	model : 'Foss.integrativeQueryIndex.Model.CargoTrackingInfoModel',
	data :[]
	});

// 定义货物轨迹-内部轨迹信息Store
Ext.define('Foss.integrativeQueryIndex.Store.InternalTrackingInfoStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.integrativeQueryIndex.Model.InternalTrackingInfoModel',
			sorters: [{     
			     property: 'operateTime',
			     direction: 'DESC'
			 }],
			data :[]
		});

// 定义一个空的store
Ext.define('Foss.integrativeQueryIndex.Store.EmptyStore', {
			extend : 'Ext.data.Store',
			// 绑定一个模型
			model : 'Foss.integrativeQueryIndex.Model.ExternalTrackingInfoModel'
		});
//按件查询Store
Ext.define('Foss.integrativeQueryIndex.Store.ExternalTrackingInfoStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.querying.integrativeQuery.LabeledGoodEntityModel'
});
//子母件轨迹Store
Ext.define('Foss.integrativeQueryIndex.ZMJLocusStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.querying.integrativeQuery.ZMJLocusModel'
});
//定义货物轨迹-内部轨迹信息Store
Ext.define('Foss.integrativeQueryIndex.Store.WaybillNOModel', {
	extend : 'Ext.data.Model',
	fields : ['waybillNo','mark','status','createUserCode']
});
// 创建一个本地的运单号store
Ext.define('Foss.integrativeQueryIndex.Store.WaybillNOStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.integrativeQueryIndex.Store.WaybillNOModel'
});

// 创建一个本地的运输性质store
Ext.define('Foss.integrativeQueryIndex.Store.TrasportProStore', {
			extend : 'Ext.data.Store',
			fields : [{
						name : 'code',
						type : 'string'
					}, {
						name : 'name',
						type : 'string'
					}],
			// 定义一个代理对象
			proxy : {
				// 代理的类型为内存代理
				type : 'memory',
				// 定义一个读取器
				reader : {
					// 以JSON的方式读取
					type : 'json',
					// 定义读取JSON数据的根对象
					root : 'items'
				}
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.callParent([cfg]);
			}
		});

// ----------------------------------------------------表单区域-------------------------------------------------------------------

//  运输信息
Ext.define('Foss.integrativeQueryIndex.Form.TransportInfoForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_TransportInfoForm_Id',
	title : querying.integrativeQuery.i18n('foss.querying.transportInformation'),  //运输信息
	frame : true,
	defaultType : 'textfield',
	//collapsed :true ,
	collapsible : true,
	animCollapse : true,
	layout : {
		type : 'table',
		columns : 3
	},
	flex : 1,
	defaults : {
		readOnly : true
	},
	bindData:function(record){
		var me = this;
		me.clearNatureForm();
		var orderChannel=record.get('orderChannel');
		//判断是否淘宝运单
		if(orderChannel=='TAOBAO'){
		  record.set('orderChannel','是');
		}else{
		 record.set('orderChannel','否');
		}
		var flightType = null;
		//航班/日期  设值
		var flightNumberType = record.get('flightNumberType');
		var flightShift = Ext.Date.format(new Date(record.get('flightShift')), 'Y-m-d');
		if(flightShift=='1970-01-01'){
			flightShift='';
		}
		if(!Ext.isEmpty(flightNumberType)){
			if(!Ext.isEmpty(flightShift)){
				flightType=flightNumberType+'/'+flightShift;
			    record.set('flightNumberType',flightType);
			}else{
				flightType=flightNumberType+'/'+'-';
			    record.set('flightNumberType',flightType);
			}
		    
		} else {
			if(!Ext.isEmpty(flightShift)){
		    var flightTime='-'+'/'+flightShift;
		    record.set('flightNumberType',flightTime);
			}else{
			var flightTime='-'+'/'+'-';
			record.set('flightNumberType',flightTime);
			}
		}
		me.getForm().loadRecord(record);
		me.addNature(record);
	},
	getComp : function(comName, labelField) {
		return {
			xtype : 'label',
			name : comName,
			margin : '0 5 0 8',
			html : '<span style="color:red;font-size:12px;font-weight:bold;background-color:yellow">'
					+ labelField + '</span>'
		}
	},
	addNature : function(record, resultForm) {
		var me = this;
		var natureForm = me.down('form');
		if ('Y' === record.get('preciousGoods')||'是' === record.get('preciousGoods')) {
			natureForm.add(me.getComp('preciousGoods', querying.integrativeQuery.i18n('foss.querying.valuables'))); //贵重物品
		}
		if ('Y' === record.get('specialShapedGoods')||'是' === record.get('specialShapedGoods')) {
			natureForm.add(me.getComp('specialShapedGoods', querying.integrativeQuery.i18n('foss.querying.specialItems')));  //特殊物品
		}
	},
	clearNatureForm : function() {
		var me = this;
		var natureForm = me.down('form');
		var labels = natureForm.query('label');
		Ext.Array.each(labels, function(item, index, allLabels) {
			natureForm.remove(item);
		});
	},
	items : [{
				name : 'receiveOrgName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingDepartment')  //收货部门
			}, {
				name : 'receiveOrgPhone',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingDepartmentPhone')  //收货部门电话
			}, {
				name : 'productName',//productName  productCode
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.transportPropertiesOf')  //运输性质
			}, {
				/*name : 'loadMethod',//取消
				fieldLabel : '配载类型'
			}, {*/
				name : 'loadOrgName',//loadOrgName loadOrgCode
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.stowageDepartment')  //配载部门
			}, {
				name : 'flightNumberType',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.flightType') //航班/日期
			}, {
				name : 'receiveMethod',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.collectMode') //提货方式
			}, {
				name : 'createUserName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.btianKaiRen')  //填开人
			}, {
				name : 'targetOrgCode',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.destinationStation')  //目的站
			}, {
				name : 'customerPickupOrgName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.deliveryOutlets')  //提货网点
			}, {
				name : 'customerPickupOrgPhone',
				colspan : 2,
				width:480,
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.deliveryOutletsPhone')  //提货网点电话
			}, {
				xtype : 'combobox',
				name:'freightMethod',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.freightMethod'),  //配载类型（合票）
				readOnly : true,  //设置为可读
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				store : FossDataDictionary.getDataDictionaryStore('MAKE_WAYBILL_WAY', null, {
					'valueCode' : '',
					'valueName' : '--'
				})	
			},{
				name : 'customerPickupOrgAddress',
				colspan : 2,
				width:480,
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.deliveryNetworkAddress')  //提货网点地址
			},{
				xtype : 'combobox',
				name : 'flabelleavemonth',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.consignorcustomersegmentation'),  //发货人客户分群
				readOnly : true,  //设置为可读
				displayField : 'valueName',
				valueField : 'valueCode',
				queryMode : 'local',
				triggerAction : 'all',
				store : FossDataDictionary.getDataDictionaryStore('CRM_CUSTOMER_GROUP', null, {
				})	
				
			}, {
				xtype : 'form',
				height : 25,
				colspan : 3,
				layout : 'column',
				id : 'Foss_integrativeQueryIndex_Form_TransportInfoForm_nature_id'
			}]
});

//  发货客户信息
Ext.define('Foss.integrativeQueryIndex.Form.DliverGoodsCustomerForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_DliverGoodsCustomerForm_Id',
	title : querying.integrativeQuery.i18n('foss.querying.shippingCustomerInformation'),  //发货客户信息
	frame : true,
	defaultType : 'textfield',
	margin:'-10 5 5 5',
	collapsible : true,
	animCollapse : true,
	flex : 1,
	layout : 'column',
	defaults : {
		readOnly : true
	},
	bindData:function(leaveDeptCode,arriveDeptCode,wayBillRecord){
		var me = this;
		currentDeptCode = FossUserContext.getCurrentDeptCode(),
		isExpressPart = FossUserContext.getCurrentDept().expressPart;
		if(currentDeptCode === leaveDeptCode){
			this.show();
			// 完整信息--发货客户加载数据
			this.getForm().loadRecord(wayBillRecord);
			//wp_078816_20140506增加大客户标记
			//判断收货客户如果是大客户，就在后面加VIP标记
			if(wayBillRecord.get('deliveryBigCustomer')=== 'Y'){
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode')
						+'<span  class="big_Customer_pic_common">');

			}else{
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode'));
			}
		}else if(currentDeptCode === arriveDeptCode&&querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryFindShipperButton')){
			this.show();
			// 完整信息--发货客户加载数据
			this.getForm().loadRecord(wayBillRecord);
			//wp_078816_20140506增加大客户标记
			//判断收货客户如果是大客户，就在后面加VIP标记
			if(wayBillRecord.get('deliveryBigCustomer')=== 'Y'){
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode')
						+'<span  class="big_Customer_pic_common">');

			}else{
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode'));
			}
			// 快递点部
		}else if(isExpressPart==='Y' & wayBillRecord.get('productCode')=='PACKAGE'){
			// 完整信息--发货客户加载数据
			this.getForm().loadRecord(wayBillRecord);
			//wp_078816_20140506增加大客户标记
			//判断收货客户如果是大客户，就在后面加VIP标记
			if(wayBillRecord.get('deliveryBigCustomer')=== 'Y'){
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode')
						+'<span  class="big_Customer_pic_common">');
			}else{
				this.getForm().findField('deliveryCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
						querying.integrativeQuery.i18n('foss.querying.customerCode'));
			}
		}else{
			this.hide();
		}
	},
	items : [{
				name : 'deliveryCustomerName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.customerName'),  //客户名称
				columnWidth : 0.333
			}, {
				name : 'deliveryCustomerCode',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.customerCode'),  //客户编码
				columnWidth : 0.333
			}, {
				name : 'deliveryCustomerMobilephone',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.phone'),  //手机
				columnWidth : 0.333
			}, {
				name : 'deliveryCustomerPhone',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.tellphone'),  //电话
				columnWidth : 0.333
			}, {
				name : 'deliveryCustomerContact',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.shippingContact'),  //发货联系人
				columnWidth : 0.333
			}, {
				name : 'deliveryCustomerAddress',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.theAddressConsignor'),  //发货人地址
				columnWidth : 1
			}]
});

//    收货客户信息
Ext.define('Foss.integrativeQueryIndex.Form.ReceiveGoodsCustomerForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_ReceiveGoodsCustomerForm_Id',
	title : querying.integrativeQuery.i18n('foss.querying.receivingCustomerInformation'),  //收货客户信息
	frame : true,
	defaultType : 'textfield',
	collapsible : true,
	animCollapse : true,
	flex : 1,
	layout : 'column',
	defaults : {
		readOnly : true
	},
	bindData:function(wayBillRecord){
		var me = this;
		// 完整信息--收货客户信息加载数据
				me.getForm().loadRecord(wayBillRecord);
				//wp_078816_增加大客户标记
				//判断收货客户如果是大客户，就在后面加VIP标记
				if(wayBillRecord.get('receiveBigCustomer')=== 'Y'){
					me.getForm().findField('receiveCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
							querying.integrativeQuery.i18n('foss.querying.customerCode')
							+'<span  class="big_Customer_pic_common">');
				}else{
					me.getForm().findField('receiveCustomerCode').setFieldLabel('&nbsp;&nbsp;'+
							querying.integrativeQuery.i18n('foss.querying.customerCode'));
				}
	},
	items : [{
				name : 'receiveCustomerName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.customerName'),  //客户名称
				columnWidth : 0.333
			}, {
				name : 'receiveCustomerCode',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.customerCode'),  //客户编码
				columnWidth : 0.333
			}, {
				name : 'receiveCustomerMobilephone',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.phone'),  //收货人手机
				columnWidth : 0.333
			}, {
				name : 'receiveCustomerPhone',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.tellphone'),  //收货人电话
				columnWidth : 0.333
			}, {
				name : 'receiveCustomerContact',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivePersonContact'),  //收货联系人
				columnWidth : 0.333
			}, {
				name : 'receiveCustomerAddress',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.consigneeAddress'),  //收货人地址
				columnWidth : 1
			}]
});

//  货物信息
Ext.define('Foss.integrativeQueryIndex.Form.CargoInfoForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_CargoInfoForm_Id',
	title : querying.integrativeQuery.i18n('foss.querying.cargoInformation'),  //货物信息
	frame : true,
	margin:'-10 5 5 5',
	defaultType : 'textfield',
	collapsible : true,
	animCollapse : true,
	flex : 1,
	layout : {
		type : 'table',
		columns : 4
	},
	defaults : {
		readOnly : true
	},
	getComp : function(comName, labelField) {
		return {
			xtype : 'label',
			name : comName,
			margin : '0 5 0 8',
			html : '<span style="color:red;font-size:12px;font-weight:bold;background-color:yellow">'
					+ labelField + '</span>'
		}
	},
	getComp1 : function(comName, labelField) {
		return {
			xtype : 'label',
			name : comName,
			margin : '5 5 0 -60',
			html : '<br/><span style="color:red;font-size:12px;font-weight:bold;background-color:yellow">'
					+ labelField + '</span>'
		}
	},
	addNature : function(record) {
		var me = this;
		var natureForm = me.down('form');
		if ('Y' === record.get('pickupCentralized')||'是' === record.get('pickupCentralized')) {
			// natureForm.findField('pickupCentralized.hidden=false;
			natureForm.add(me.getComp('pickupCentralized', querying.integrativeQuery.i18n('foss.querying.centralizedAccessDelivery')));  //集中接送货
		}
		/**
		 * @author 218392 张永雪
		 * 接种接送货是Y并且展会货是Y,让展会货换行，在集中接送货下方
		 */
		if (('Y' === record.get('isExhibitCargo')||'是' === record.get('isExhibitCargo'))
			&&('Y' === record.get('pickupCentralized')||'是' === record.get('pickupCentralized'))) {
			// natureForm.findField('isExhibitCargo').hidden=false;
			natureForm.add(me.getComp1('isExhibitCargo', querying.integrativeQuery.i18n('foss.querying.isExhibitCargo')));  //展会货
		}
		/**
		 * @author 218392 张永雪
		 * 接种接送货是N并且展会货是Y,展会货不换行
		 */
		if (('Y' === record.get('isExhibitCargo')||'是' === record.get('isExhibitCargo'))
			&&('N' === record.get('pickupCentralized')||'否' === record.get('pickupCentralized'))) {
			// natureForm.findField('isExhibitCargo').hidden=false;
			natureForm.add(me.getComp('isExhibitCargo', querying.integrativeQuery.i18n('foss.querying.isExhibitCargo')));  //展会货
		}
		if ('Y' === record.get('carDirectDelivery')||'是' === record.get('carDirectDelivery')) {
			natureForm.add(me.getComp('carDirectDelivery', querying.integrativeQuery.i18n('foss.querying.cartsDirect')));  //大车直送
		}
		if ('Y' === record.get('isWholeVehicle')||'是' === record.get('isWholeVehicle')) {
			if('Y' === record.get('isPassOwnDepartment')||'是' === record.get('isPassOwnDepartment')){
				natureForm.add(me.getComp('isWholeVehicle',
						querying.integrativeQuery.i18n('foss.querying.vehicleUpSalesDepartment')+'约车编码:'
						 + record.get('orderVehicleNum')));  //整车(到达营业部)
			}else{//TODO_WP
				natureForm.add(me.getComp('isWholeVehicle',
						querying.integrativeQuery.i18n('foss.querying.vehicleUpCustomerReaching')+'约车编码:'
						 +/*me.getForm().findField('orderVehicleNum')*/ record.get('orderVehicleNum')));  //整车(到达客户处)
			}
		}
		//【2016-08-05-308861】完整信息的货物信息中显示零担电子运单标识
		if('LTLEWAYBILL' == record.get('waybillType')){
			me.clearNatureForm();
			natureForm.add(me.getComp('waybillType', '零担电子运单'));
		}
	},
	clearNatureForm : function() {
		var me = this;
		var natureForm = me.down('form');
		var labels = natureForm.query('label');
		Ext.Array.each(labels, function(item, index, allLabels) {
					natureForm.remove(item);
				})
	},
	bindData:function(wayBillRecord){
		// 完整信息--货物信息加载数据
		var me = this;
		me.clearNatureForm();
		me.getForm().loadRecord(wayBillRecord);
		me.addNature(wayBillRecord);
	},
	items : [{
				name : 'goodsName',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.nameOfGoods')  //货物品名
			},{
				name : 'category',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.categoryOfGoods')  //货物品名
			}, {
				name : 'goodsTypeCode',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.cargoType')  //货物类型
			}, {
				name : 'goodsPackage',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.package')  //包装
			}, {
				name : 'goodsQtyTotal',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalNumberOf')  //总件数
			}, {
				name : 'goodsWeightTotal',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalWeight')  //总重量
			}, {
				name : 'goodsVolumeTotal',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalVolume')  //总体积
			},{
				name:'czmGoodsWeightTotal',
				fieldLabel:querying.integrativeQuery.i18n('foss.querying.czmGoodsWeightTotal') //子母件总重量
			},{      
				name:'czmGoodsVolumeTotal',
				fieldLabel:querying.integrativeQuery.i18n('foss.querying.czmGoodsVolumeTotal') //子母件总体积
			},{
				name : 'goodsSize',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.size')  //尺寸
			}, {
				name : 'innerNotes',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.internalMemo')  //对内备注
			}, {
				name : 'outerNotes',
				fieldLabel :querying.integrativeQuery.i18n('foss.querying.foreignRemarks')  //对外备注
			}, {
				name : 'transportationRemark',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.storageTransportationMatters'),  //储运事项
				colspan : 3
			}, {
				xtype : 'form',
				height : 50,
				colspan : 4,
				layout : 'column',
				id : 'Foss_integrativeQueryIndex_Form_CargoInfoForm_nature_Id'
			}]
});

//  费用信息
Ext.define('Foss.integrativeQueryIndex.Form.OutlayInfoForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_OutlayInfoForm_Id',
	title : querying.integrativeQuery.i18n('foss.querying.costInformation'),  //费用信息
	frame : true,
	defaultType : 'textfield',
	collapsible : true,
	animCollapse : true,
	layout : {
		type : 'table',
		columns : 5
	},
	flex : 1,
	defaults : {
		readOnly : true,
		labelWidth : 80
	},
	bindData:function(wayBillRecord){      // 完整信息--费用信息加载数据
		var me = this;
		var outLayInfoObj=wayBillRecord.data;
		if(wayBillRecord.get('secretPrepaid')=='Y'){
			prePayAmountObj=me.getForm().findField('prePayAmount');
			if(outLayInfoObj.receiveOrgName == FossUserContext.getCurrentDept().name){
				prePayAmountObj.setVisible(true);
			}else{
				prePayAmountObj.setVisible(false);
			}
		}
		me.loadRecord(wayBillRecord);
		if(outLayInfoObj.receiveOrgName != FossUserContext.getCurrentDept().name){
			me.getForm().findField('serviceFee').setValue(0);
		}
	},
	items : [{
		name : 'billingType',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.billingType')  //计费类型
	}, {
		name : 'transportFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.tariffs')  //运价
	}, {
		name : 'totalFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.freight')  //运费
	}, {
		name : 'paidMethod',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.termsPayment')  //付款方式
	}, {
		name : 'prePayAmount', 
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.prepaidAmount')  //预付金额
	}, {
		name : 'toPayAmount',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.ayTheAmount')  //到付金额
	}, {
		name : 'returnBillType',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.backSingleCategory')  //返单类别
	}, {
		name : 'insuranceAmount',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.insuredValue')  //保险价值
	}, {
		// TODO
		name : 'insuranceFee',//insuranceFee insureFreight
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.insuranceFee')  //保价费
	}, {
		name : 'codAmount',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentCollection')  //代收货款
	}, {
		name : 'codRate',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentCollectionRates'),//codRate codFee  //代收货款费率
		labelWidth : 90
	}, {
		name : 'refundType',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.refundType')  //退款类型
	}, { 
		name : 'pickUpFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingGoodsCharges')  //接货费
	}, {
		name : 'deliveryGoodsFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.deliveryCharges')  //送货费
	}, {
		name : 'packageFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.packingCharges')  //包装费
	}, {
		name : 'serviceFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.laborCosts')  //装卸费
	}, {
		name : 'otherFee',
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.otherExpenses')  //其他费用
	}, {
		name : 'toPayAmountDiscount',
		labelWidth : 100,
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.toPayAmountDiscount')  //到付折扣金额
	}, {
		name : 'prePayAmountDiscount',
		labelWidth : 100,
		fieldLabel : querying.integrativeQuery.i18n('foss.querying.prePayAmountDiscount')  //预付折扣金额
	}] 
});

//定义一个流水号的信息列表  3510  （在状态信息列表里展示）
Ext.define('Foss.integrativeQueryIndex.SerialsInfoGrid',{
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_Grid_SerialsInfoGrid_Id',
	columnLines: true,
	frame: true,
	baseCls : 'handOverBill_addNew_serialNoGap',
	autoScroll : true,
	width : 121,
	height:300,
	store : Ext.create('Foss.integrativeQueryIndex.Store.CargoTrackingInfoStore'),
	columns : [{
		// 字段标题
		header : querying.integrativeQuery.i18n('foss.querying.serialNumber'),  //流水号
		// 关联model中的字段名
		dataIndex : 'serials',
		align : 'center',
		width : 90
	}],
	bindData : function(record,grid,rowBodyElement) {
		var serials = record.get('serials'),
			serialsStrs = serials.split(','),
			serialsArray = [];
		for(var i=0;i<serialsStrs.length;i++){
			serialsArray.push({
				serials: serialsStrs[i]
			});
		}
		rowBodyElement.getStore().loadData(serialsArray);
	}
});


// 定义一个货物轨迹信息列表
Ext.define('Foss.integrativeQueryIndex.CargoTrackingInfoGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_Grid_CargoTrackingInfoGrid_Id',
	// 增加表格列的分割线
	columnLines : true,
	columnWidth : 1,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	margin : '0 10 20 10',
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	// title:'查询结果',
	// collapsible: true,
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	animCollapse : true,
	store : null,
	plugins : [ {    //ISSUE-3510
		header : true,
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.integrativeQueryIndex.SerialsInfoGrid'
	}],
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.cargoStatus'),  //货物状态
				// 关联model中的字段名
				dataIndex : 'action',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.number'), //件数
				// 关联model中的字段名
				dataIndex : 'goodsCount',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.currentLocation'), //当前位置
				// 关联model中的字段名
				dataIndex : 'nowPosition',
				width : 80
			}, {
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.estimatedTimeArrivalNextDepartment'), //预计到达下一部门时间
				// 关联model中的字段名
				dataIndex : 'planArriveTime',
				width : 150,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.nextDepartment'),  //下一部门
				// 关联model中的字段名
				dataIndex : 'nextOrgCode',
				width : 100
			}, 
//			{
//				// 字段标题
//				header : '预计到达最终外场时间',//TODO 实体中缺失 该字段
//				// 关联model中的字段名
//				dataIndex : 'planArriveTime',
//				width : 150,
//				renderer:function(v){
//					if(!Ext.isEmpty(v)){
//						return Ext.Date.format(new Date(v), 'Y-m-d');
//					}
//					return v;
//				}
//			},
			{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.expectedDeliveryPickUpTime'),  //预计派送/提货时间
				// 关联model中的字段名
				dataIndex : 'planPickupTime',
				width : 150,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.whetherTheDelivery'),  //是否可提货
				// 关联model中的字段名
				dataIndex : 'ifAvailedPickup',
				width : 100,
				renderer:function(v){
					if('N' === v){
						return '否';
					}else if('Y' === v){
						return '是';
					}
					return v;
				
				}
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.integrativeQueryIndex.Store.CargoTrackingInfoStore');
		me.callParent([cfg]);
	}
});
// （按件查询）或（子母件轨迹）双击一行展开的grid（某一个流水号的轨迹或某一个子母件的轨迹）
Ext.define('Foss.integrativeQueryIndex.InternalTrackingGrid_plugin', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : false,
	height : 300,
	stripeRows : true,
	// margin:'5 10 5 10',
	cls : "panelContentNToolbar",
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	bodyCls : 'panelContentNToolbar-body',
	store :Ext.create('Foss.integrativeQueryIndex.Store.InternalTrackingInfoStore'),
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.cargoStatus'),  //货物状态
				// 关联model中的字段名
				dataIndex : 'currentStatus',
				xtype : 'ellipsiscolumn',
				flex : 1
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operationsDepartment'),  //操作部门
				// 关联model中的字段名
				dataIndex : 'operateOrgName',
				xtype : 'ellipsiscolumn',
				width : 90
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operator'),  //操作人
				// 关联model中的字段名
				dataIndex : 'operateName',
				xtype : 'ellipsiscolumn',
				flex : 1
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.theTypeOperation'),  //操作类型
				// 关联model中的字段名
				dataIndex : 'operateTypeName',//operateType operateTypeName
				xtype : 'ellipsiscolumn',
				flex : 1
			}, 
//			{
//				// 字段标题
//				header : querying.integrativeQuery.i18n('foss.querying.operationContent'),  //操作内容
//				// 关联model中的字段名
//				dataIndex : 'operateContent',
//				width : 100
//			},
			{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operationTime'),  //操作时间
				// 关联model中的字段名
				dataIndex : 'operateTime',
				width : 130,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.number'),  //操作件数
				// 关联model中的字段名
				dataIndex : 'operateNumber',
				width : 45,
				renderer:function(value) {
					if(value===0){
						value=null;
					}
			    return value;
			}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.documentNumber'),  //单据编号
				// 关联model中的字段名
				dataIndex : 'billNo',
				xtype : 'ellipsiscolumn',
				flex : 1,
				sortable:false
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.licensePlateNumber'),  //车牌号
				// 关联model中的字段名
				dataIndex : 'vehicleNo',
				xtype : 'ellipsiscolumn',
				flex : 1
			}, {
				//字段标题
				header : querying.integrativeQuery.i18n('foss.querying.barCoding'),//定位编号
				dataIndex : 'stockPositionNumber',//关联model中的字段名      
				xtype : 'ellipsiscolumn',
				flex : 1
			},{
				// 字段标题
				text : querying.integrativeQuery.i18n('foss.querying.remark'),  //备注        
				// 关联model中的字段名
				dataIndex : 'notes',
				//xtype : 'ellipsiscolumn',
				width : 75,
				renderer:function(value, metadata) {
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qwidth="100" data-qtip="' + value + '"';
					}
			    return value;
			}
				//flex : 1
			}],
			bindData : function(record) {
				var me = this;
				me.store.loadData([]);
				var waybillVo = {},condition = {};
				condition.serialNo = record.get('serialNo');
				condition.waybillNo = record.get('waybillNo');
				waybillVo.condition = condition;
				//根据 流水号和单号查询 轨迹
				Ext.Ajax.request({
		    		url:querying.realPath('queryWayBillNoLocus.action'),
		    		jsonData:{'waybillVo':waybillVo},
		    		timeout:600000,
		    		success:function(response){
		    			var result = Ext.decode(response.responseText);
		    			var list = result.waybillVo.wayBIllNoLocusList;
		    			// 货物轨迹（每一个流水号的轨迹或每一个子母件的轨迹）
		    			me.store.loadData(Ext.isEmpty(list)?[]:list);
		    			//判断异常信息是否存在，存在提示用户
		    			if(!Ext.isEmpty(result.waybillVo.exceptionInfo)){
		    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),result.waybillVo.exceptionInfo);  //foss提醒您
		    			}
		    		},
		    		exception:function(response){
		    			var result = Ext.decode(response.responseText);
		    			if(Ext.isEmpty(result)){
		    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
		    			}else{
		    				Ext.Msg.alert(result.message);
		    			}
		    		}
		    	});
			}
});
// 内部轨迹（运单相关信息——货物轨迹——内部轨迹）
Ext.define('Foss.integrativeQueryIndex.InternalTrackingGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_Grid_InternalTrackingGrid_Id',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : false,
	height : 315,
	stripeRows : true,
	cls : "panelContentNToolbar",
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	bodyCls : 'panelContentNToolbar-body',
	store : null,
	tbar : [{
				xtype : 'textfield',
				name : 'name',
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.serialNumber')  //流水号
			}, {
				xtype : 'button',
				text : querying.integrativeQuery.i18n('foss.querying.search'),  //搜索
				handler : function() {
					var waybillVo = {},condition = {};
					condition.serialNo = this.up('panel').down('textfield').getValue();
					condition.waybillNo = querying.integrativeQuery.waybillNo;
					waybillVo.condition = condition;
					if(Ext.isEmpty(querying.integrativeQuery.waybillNo)){
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseSelectWaybill'));  //FOSS提醒您','请选择一个运单！
						return;
					}
					//根据 流水号和单号查询 轨迹
					Ext.Ajax.request({
			    		url:querying.realPath('queryWayBillNoLocus.action'),
			    		jsonData:{'waybillVo':waybillVo},
			    		timeout:600000,
			    		success:function(response){
			    			var result = Ext.decode(response.responseText);
			    			var list = result.waybillVo.wayBIllNoLocusList;
			    			// 货物轨迹（内部轨迹）
							Ext.getCmp('Foss_integrativeQueryIndex_Grid_InternalTrackingGrid_Id').store.loadData(Ext.isEmpty(list)?[]:list);
							Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.afterSearching'));  //FOSS提醒您','搜索完毕！
			    		},
			    		exception:function(response){
			    			var result = Ext.decode(response.responseText);
			    			if(Ext.isEmpty(result)){
			    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
			    			}else{
			    				Ext.Msg.alert(result.message);
			    			}
			    		}
			    	});
				}
			}],
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.cargoStatus'),  //货物状态
				// 关联model中的字段名
				dataIndex : 'currentStatus',
				xtype : 'ellipsiscolumn',
				width : 80
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operationsDepartment'),  //操作部门
				// 关联model中的字段名
				dataIndex : 'operateOrgName',
				xtype : 'ellipsiscolumn',
				width : 90
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operator'),  //操作人
				// 关联model中的字段名
				dataIndex : 'operateName',
				xtype : 'ellipsiscolumn',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.theTypeOperation'),  //操作类型
				// 关联model中的字段名
				dataIndex : 'operateTypeName',//operateType operateTypeName
				xtype : 'ellipsiscolumn',
				flex : 1
			},
//			{
//				// 字段标题
//				header : querying.integrativeQuery.i18n('foss.querying.operationContent'),  //操作内容
//				// 关联model中的字段名
//				dataIndex : 'operateContent',
//				width : 100,hidden:true
//			},
			{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.operationTime'),  //操作时间
				// 关联model中的字段名
				dataIndex : 'operateTime',
				width : 130,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.number'),  //操作件数
				// 关联model中的字段名
				dataIndex : 'operateNumber',
				width : 45,
				renderer:function(value) {
					if(value===0){
						value=null;
					}
			    return value;
			}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.documentNumber'), //单据编号
				// 关联model中的字段名
				dataIndex : 'billNo',
				xtype : 'ellipsiscolumn',
				flex : 1
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.licensePlateNumber'),  //车牌号
				// 关联model中的字段名
				dataIndex : 'vehicleNo',
				xtype : 'ellipsiscolumn',
				flex : 1
			}, {
				// 字段标题
				text : querying.integrativeQuery.i18n('foss.querying.remark'), //备注
				// 关联model中的字段名
				dataIndex : 'notes',
				//xtype : 'ellipsiscolumn',
				width : 110,
				renderer:function(value, metadata) {
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qwidth="100" data-qtip="' + value + '"';
					}
			    return value;
			}
				//flex : 1
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.integrativeQueryIndex.Store.InternalTrackingInfoStore');
		// me.selModel =
		// Ext.create('Ext.selection.CheckboxModel');
		me.callParent([cfg]);
	}
});


// 状态信息
Ext.define('Foss.integrativeQueryIndex.Form.StatusInfoForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_StatusInfoForm_id',
	title : querying.integrativeQuery.i18n('foss.querying.statusInformation'),  //状态信息
	collapsible : true,
	animCollapse : true,
	frame : true,
	/*
	 * cls:"panelContentNToolbar", bodyCls: 'panelContentNToolbar-body',
	 */
	defaultType : 'textfield',
	layout : 'column',
	flex : 1,
	margin:'-10 5 5 5',
	// height:550,
	defaults : {
		readOnly : true,
		margin : '2 5 5 5'
		//margin : '0 0 0 0'	
		// anchor: '90%',
	},
	cargoTrackingInfoGrid : null,
	getCargoTrackingInfoGrid : function() {
		if (this.cargoTrackingInfoGrid == null) {
			this.cargoTrackingInfoGrid = Ext.create('Foss.integrativeQueryIndex.CargoTrackingInfoGrid');
		}
		return this.cargoTrackingInfoGrid;
	},
	bindData: function(billOtherRecord, generalQueryDtos){
		this.getForm().loadRecord(billOtherRecord);
		this.getCargoTrackingInfoGrid().getStore().loadData(generalQueryDtos);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name : 'billTime',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.billingTime'),  //开单时间
			xtype:'datefield',
			format: 'Y/m/d',
			columnWidth : 0.33
			}, {
			name : 'preCustomerPickupTime',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.commitmentArrivalTime'),  //承诺到达时间
			xtype:'datefield',
			format: 'Y/m/d',
			columnWidth : 0.34
			}, {
			name : 'storageCharge',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.storageCharges'),//保管费
			columnWidth : 0.33
			}, {
			name : 'loadLineCode',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.plansTakeGoodsPath'),  //计划走货路径
			columnWidth : 1
			}, { 
			name : 'actualPath',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.actualPathGoodsPath'),  //实际走货路径
			columnWidth : 1
				}, me.getCargoTrackingInfoGrid()]
		me.callParent([cfg]);
	}
});
// 按件查询
Ext.define('Foss.integrativeQueryIndex.Grid.ExternalGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_Grid_ExternalGrid_Id',
	// 增加表格列的分割线
	columnLines : true,
	// columnWidth: 1,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	margin : '0 5 0 5',
	scroll : true,
	viewConfig:{
		enableTextSelection : true//设置行可以选择，进而可以复制
	}, 
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.serialNumber'),  //流水号
				// 关联model中的字段名
				dataIndex : 'serialNo',
				flex:1
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.waybillNumber'),  //运单号
				// 关联model中的字段名
				dataIndex : 'waybillNo',
				flex:1
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
		.create('Foss.integrativeQueryIndex.Store.ExternalTrackingInfoStore');
		me.callParent([cfg]);
	},
	// 表格行可展开的插件
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.integrativeQueryIndex.InternalTrackingGrid_plugin'
	}]
});
// 子母件轨迹
Ext.define('Foss.integrativeQueryIndex.ZMJLocusGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_ZMJLocusGrid_Id',
	// 增加表格列的分割线
	columnLines : true,
	// columnWidth: 1,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	margin : '0 5 0 5',
	scroll : true,
	viewConfig:{
		enableTextSelection : true//设置行可以选择，进而可以复制
	}, 
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.ZMJRelateWaybill'),  //子母件关联单
				// 关联model中的字段名
				dataIndex : 'ZMJMark',
				flex:1
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.waybillNumber'),  //运单号
				// 关联model中的字段名
				dataIndex : 'waybillNo',
				flex:1
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
		.create('Foss.integrativeQueryIndex.ZMJLocusStore');
		me.callParent([cfg]);
	},
	// 表格行可展开的插件
	plugins : [{
		ptype : 'rowexpander',
		rowsExpander : false,
		// 行体内容
		rowBodyElement : 'Foss.integrativeQueryIndex.InternalTrackingGrid_plugin'
	}]
});
// 定义货物轨迹-外部轨迹信息表单
Ext.define('Foss.integrativeQueryIndex.QueryFormModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 运单号
						name : 'waybillNo',
						type : 'string'
					}, {
						name : 'deptCode',
						type : 'string'
					}, {
						name : 'destStation',
						type : 'string'
					}, {
						name : 'freigthType',
						type : 'string'
					}, {
						name : 'freightNo',
						type : 'string'
					}, {
						name : 'contactWay',
						type : 'string'
					}, {
						name : 'contactNo',
						type : 'string'
					}, {
						name : 'contactManType',
						type : 'string'
					}, {
						name : 'contactManName',
						type : 'string'
					}, {
						name : 'transMethod',
						type : 'string'
					}, {
						name : 'startWaybillNo',
						type : 'string'
					}, {
						name : 'endWaybillNo',
						type : 'string'
					}, {
						name : 'startDate',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						name : 'endDate',
						type:'date',convert: dateConvert,defaultValue:null
					},{
						name:'callRecordNo', //电话记录单号
						type:'string'
					}]
		})

// 单号********   简单信息   完整信息
Ext.define('Foss.integrativeQueryIndex.Form.WaybillNOFullForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_Form_WaybillNOFullForm_Id',
	frame : false,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		readOnly : true
	},
	bindData: function(wayBillNumber,waybillStatus,waybillInfoByWaybillNoReultDto){
		var form = this.getForm();
		if(waybillStatus == 'OBSOLETE'){
			form.findField('waybillNo').setValue(wayBillNumber+querying.integrativeQuery.i18n('foss.querying.obsolete'));//已作废
		}else if(waybillStatus == 'ABORTED'){
			form.findField('waybillNo').setValue(wayBillNumber+querying.integrativeQuery.i18n('foss.querying.aborted'));//已中止
		}else{
			form.findField('waybillNo').setValue(wayBillNumber);
		}
		
		if(waybillInfoByWaybillNoReultDto.deliveryBigCustomer=='Y' || waybillInfoByWaybillNoReultDto.receiveBigCustomer=='Y'){
			form.findField('waybillNo').setFieldLabel(querying.integrativeQuery.i18n('foss.querying.spanWaybillNumber')+"&nbsp;&nbsp;"
					+'<span  class="big_Customer_pic_common">');
		}else{
			form.findField('waybillNo').setFieldLabel(querying.integrativeQuery.i18n('foss.querying.spanWaybillNumber'));
		}
		
	},
	radioChange: function(isSimple){
		var me = this,
			wayBillFullInfoPanel = me.up('panel'),
			completeRightBodyPanel = wayBillFullInfoPanel.getSimpleWayBillForm(),//Ext.getCmp('Foss_integrativeQueryIndex_CompleteRightBodyPanel_id');
			simpleRigthBodyPanel = wayBillFullInfoPanel.getCompleteWayBillInfoPanel();//Ext.getCmp('Foss_integrativeQueryIndex_SimpleRightBodyPanel_id');
		completeRightBodyPanel.setVisible(!isSimple);
		simpleRigthBodyPanel.setVisible(isSimple);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name : 'waybillNo',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.spanWaybillNumber'),  //单号
			columnWidth : .5,
			margin : '0 0 0 50',
			labelWidth : 75,
			cls : 'specilfield',
			labelSeparator : '',
			listeners:{
				change:function(field,new_v){
					querying.integrativeQuery.invalidWaybillNo = new_v;					
				}
			}
		}, {
			xtype : 'radiogroup', // 单选框组
			// fieldLabel : '爱好', // 复选框组的字段标签
			// columns : 3, // 3列来存放选择框项
			vertical : true, // 按照columns中指定的列数来分配单选框组
			// 验证表单输入不能为空，属性值：true表示可以为空，false表示不可以为空
			// allowBlank : false,
			defaults : {
				labelWidth : 80,
				margin : '8 0 0 0'
			},
			items : [{
				boxLabel : querying.integrativeQuery.i18n('foss.querying.simpleInformation'),  //简单信息
				name : 'infomation', // 表单的参数名
				inputValue : '1', // 表单的参数值
				checked : true,
				id : 'radiogroup_simple',
				// margin:'8 0 0 0',
				listeners : {
					change : function(ths, newValue, oldValue, eOpts) {
						me.radioChange(true);
					}
				}
			}, {
				boxLabel : querying.integrativeQuery.i18n('foss.querying.completeInformation'),  //完整信息
				name : 'infomation', // 表单的参数名
				inputValue : '2', // 表单的参数值
				id : 'radiogroup_complete',
				// margin:'8 0 0 0',
				listeners : {
					change : function(ths, newValue, oldValue, eOpts) {
						me.radioChange(false);
					}
				}
			}]
		}]
		me.callParent([cfg]);
	}
});

// 查询条件
Ext.define('Foss.integrativeQueryIndex.QueryForm', {
	extend : 'Ext.tab.Panel',
	title : querying.integrativeQuery.i18n('foss.querying.searchCondiction'),  //查询条件
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	animCollapse : true,
	activeTab : 0,// 默认激活第一个Tab页
	frame : true,
	//height:160,
	constructor : function(config) {
		var me = this;
		me.items = me.getItems();
		me.callParent([config])
		var dept = FossUserContext.getCurrentDept();
		me.down('form').getForm().findField('deptCode').setCombValue(dept.name,
				dept.code);
	},
	getButtonPanel : function() {
		var me = this;
		return {
			border : 1,
			xtype : 'container',
			columnWidth : 1,
			defaultType : 'button',
			layout : 'column',
			items : [{
						text : querying.integrativeQuery.i18n('foss.querying.reset'),  //重置
						columnWidth : .08,
						disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySearchButton'),
						hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySearchButton'),
						handler : function() {
							var ths = this;
							var form = ths.up('form').getForm();
							form.reset();
							form.findField('deptCode').setCombValue(FossUserContext.getCurrentDept().name,FossUserContext.getCurrentDept().code);//部门
							form.findField('startDate').setValue();
							form.findField('startDate').setValue(Ext.Date.format(new Date(new Date()	 .getFullYear(), new Date()	 .getMonth(), new Date()	 .getDate(), 0, 0, 0),	 'Y-m-d H:i:s'));
							form.findField('endDate').setValue(Ext.Date.format(new Date(new Date()	 .getFullYear(), new Date()	 .getMonth(), new Date()	 .getDate(), 23, 59, 59),	 'Y-m-d H:i:s'));
						}
					}, {
						xtype : 'container',
						border : false,
						columnWidth : .84,
						html : '&nbsp;'
					}, {
						text : querying.integrativeQuery.i18n('foss.querying.query'),  //查询
						disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySearchButton'),
						hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySearchButton'),
						cls : 'yellow_button',
						columnWidth : .08,
						handler : me.searchBtnHandler
					}]
		}
	},
	searchBtnHandler:function(btn){
		var ths = btn;
		 var form = ths.up('form').getForm();
       if (!form.isValid()) {
       	Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.makeSureDataFillCorrect'));  //FOSS提醒您','请确定数据都填写正确！
       	return;
       }
		Ext.getCmp('Foss_integrativeQueryIndex_SimpleRightBodyPanel_id')
				.clearNatureForm();
		Ext.getCmp('Foss_integrativeQueryIndex_Form_CargoInfoForm_Id')
				.clearNatureForm();
		Ext.getCmp('Foss_integrativeQueryIndex_Form_TransportInfoForm_Id')
				.clearNatureForm();
		var searchForm = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
		var searchModel = new Foss.integrativeQueryIndex.QueryFormModel();
		var formC = ths.up('form').getForm();
		formC.updateRecord(searchModel);
		//【2013-01-17-073586-LIXUEXING】单号允许输入单边查询，同时补充另一边值为相同值，否则接送货接口会 抛异常
		if(Ext.isEmpty(searchModel.get('startWaybillNo'))&&!Ext.isEmpty(searchModel.get('endWaybillNo'))){
			searchModel.set('startWaybillNo',searchModel.get('endWaybillNo'))
		}else if(!Ext.isEmpty(searchModel.get('startWaybillNo'))&&Ext.isEmpty(searchModel.get('endWaybillNo'))){
			searchModel.set('endWaybillNo',searchModel.get('startWaybillNo'))
		}
		//【2013-01-17-073586-LIXUEXING】查询时间 段 不能 为空
		if(Ext.isEmpty(searchModel.get('freigthType'))){
			var startDate = formC.findField('startDate').getValue(),endDate = formC.findField('endDate').getValue();
			if(Ext.isEmpty(startDate)||Ext.isEmpty(endDate)){
				Ext.MessageBox.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.queryTimeNotEmpty'));  //FOSS提醒您','查询时间不能为空！
				return;
			}else{
				//定义两个用来判断
				 var getStartTime = Ext.Date.parse(startDate, 'Y-m-d H:i:s'); 
			     var getEndTime = Ext.Date.parse(endDate, 'Y-m-d H:i:s');
			   //获取开始时间到结束的时间的天數差
					var diff =Math.round((getEndTime.getTime()-getStartTime.getTime())/(86400*1000));
					var contactWay = searchModel.get('contactNo');//收发货人联系方式
					var contactManType = searchModel.get('contactManName');//收发货联系人
					if(!Ext.isEmpty(searchModel.get('startWaybillNo'))||!Ext.isEmpty(searchModel.get('endWaybillNo'))){
						searchModel.set('startDate',Ext.Date.parse(startDate, 'Y-m-d H:i:s'));
						searchModel.set('endDate',Ext.Date.parse(endDate, 'Y-m-d H:i:s'));
					}else if(!Ext.isEmpty(contactWay)||!Ext.isEmpty(contactManType)){
						if(diff>31){
							Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.fossAlert'),querying.integrativeQuery.i18n('foss.querying.mustSearchMouthDay'));  //FOSS提醒      时间！
							return;
						}else{
							searchModel.set('startDate',Ext.Date.parse(startDate, 'Y-m-d H:i:s'));
							searchModel.set('endDate',Ext.Date.parse(endDate, 'Y-m-d H:i:s'));
						}
					}else if(diff>3){
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.fossAlert'),querying.integrativeQuery.i18n('foss.querying.mustSearchThreeDay'));  //FOSS提醒      时间！
						return;
					}else{
						searchModel.set('startDate',Ext.Date.parse(startDate, 'Y-m-d H:i:s'));
						searchModel.set('endDate',Ext.Date.parse(endDate, 'Y-m-d H:i:s'));
					}
			}
		}
		//【2013-01-17-073586-LIXUEXING】车次/正单号查询时 需要输入查询条件
		if(!Ext.isEmpty(searchModel.get('freigthType'))&&Ext.isEmpty(searchModel.get('freightNo'))){
			Ext.MessageBox.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseEnterTripsSingleNumber'));  //FOSS提醒您','请输入【车次/正单号】！
			return;
		}
		//【2014-08-06-130566-zengjunfan】单点登录查询时的记录单号准入
		if(!Ext.isEmpty(serviceId)){
			//searchModel.set('callRecordNo','000'); 【接口查询，代码注释去掉 优化】
			searchModel.set('callRecordNo',serviceId)
		}
		var successFn = function(json) {
			if(Ext.isEmpty(json.waybillVo.waybills)){
				Ext.MessageBox.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.notSearchData'));  //FOSS提醒您','没有检索到数据！
				return;
			}
			var store = searchForm.getStore(),storeData = [];
			store.on('load',function(s,records){   
				var girdcount=0; 
					s.each(function(r){
						if(querying.integrativeQuery.mark.jj == r.get('mark')&&querying.integrativeQuery.currentEmpCode==r.get('createUserCode')){
						   //给单元格涂色
							var cells = searchForm.getView().getNodes()[girdcount].children;
							for(var i= 0;i<cells.length;i++){
								cells[i].style.backgroundColor='#ffe600';
							}
						}else if(querying.integrativeQuery.mark.fcjj == r.get('mark')&&querying.integrativeQuery.currentEmpCode==r.get('createUserCode')){
							//给单元格涂色
							var cells = searchForm.getView().getNodes()[girdcount].children;
							for(var i= 0;i<cells.length;i++){
								cells[i].style.backgroundColor='#e23910';//#FF34B3
							}
						}
						girdcount++;
					}); 
			}); 
			function containItems(arr,item){
				if(Ext.isEmpty(arr)){
					return false;
				}
				for(i in arr){
					if(arr[i].waybillNo == item.waybillNo){
						return true;	
					}
				}
				return false;
			}
			store.each(function(r){
				if(!containItems(json.waybillVo.waybills,r.data)){
					storeData.push(r.data);
				}
			});
			var finalArr = Ext.Array.merge(json.waybillVo.waybills,storeData);
			if(finalArr.length > 3000 ){
				var extendLengh = finalArr.length - 3000 ;
				Ext.MessageBox.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.backResultList'));  //FOSS提醒您','返回结果列表不能大于3000条
				return;
			}
			//store.loadRawData(finalArr.reverse());
			store.loadRawData(finalArr);
			if(!Ext.isEmpty(json.waybillVo.waybills)){
				var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
				//如果新的查询结果为1 则定位到此返回结果，否则定位到第一个
				if(json.waybillVo.waybills.length == 1){
					var record = Ext.create('Foss.integrativeQueryIndex.Store.WaybillNOModel',json.waybillVo.waybills[0]);
					grid.getSelectionModel().select(0);
					grid.fireEvent('itemclick',grid,record);
				}else{
					grid.getSelectionModel().select(0);
					grid.fireEvent('itemclick',grid,grid.getSelectionModel().getSelection()[0]);
				}
			}else{
				cleanWaybillDetall({});
			}
		}
		var failFn = function(json) {
			if (Ext.isEmpty(json)) {
				Ext.MessageBox.show({
							title : querying.integrativeQuery.i18n('foss.querying.informationFailureTip'),  //信息（失败）提示
							msg : querying.integrativeQuery.i18n('foss.querying.requestTimeOut'),  //请求超时
							width : 450,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			} else {
				Ext.MessageBox.show({
							title : querying.integrativeQuery.i18n('foss.querying.informationFailureTip'),  //信息（失败）提示
							msg : json.message,
							width : 450,
							buttons : Ext.Msg.OK,
							icon : Ext.MessageBox.ERROR
						});
			}
		}
		var params = {
			'waybillVo' : {
				'condition' : searchModel.data
			}
		};
		var url = querying
				.realPath('searchWaybillNo.action');
		querying.requestJsonAjax(url, params, successFn,
				failFn);
	},
	getItems : function() {
		var me = this;
		// var queryForm1 = me.query('form')[0];
		// var queryForm2 = me.query('form')[1];
		var buttonPanel = me.getButtonPanel();
		// var buttonPanel2 = me.getButtonPanel(queryForm2);
		return [{
			xtype : 'form',
			title : querying.integrativeQuery.i18n('foss.querying.conditionSearch'),  //条件查询
			tabConfig : {
				width : 100
			},
			layout : 'column',
			defaults : {
				// margin: '5 10 5 10',
				xtype : 'textfield',
				labelWidth : 60,
				margin : '5 0 0 0',
				columnWidth : .25
			},
			items : [{
						name : 'deptCode',
						xtype : 'dynamicorgcombselector',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingDepartment')  //收货部门
					}, {
						xtype:'dynamicorgcombselector',
						types:'ORG,PX,KY',
						name : 'destStation',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.destinationStation')  //目的站
					}, {
						name : 'contactWay',
						xtype : 'combobox',
						queryMode : 'local',
						displayField : 'name',
						valueField : 'code',
						margin : '5 0 0 10',
						columnWidth : .1,
						value : 'shipperMobile',
						store : Ext.create('Ext.data.Store', {
									// 定义字段
									fields : [{
												name : 'code',
												type : 'string'
											}, {
												name : 'name',
												type : 'string'
											}],
									data : {
										'items' : [{
													"code" : "shipperMobile",
													"name" : querying.integrativeQuery.i18n('foss.querying.consignorShipperMobile')  //发货人手机
												}, {
													"code" : "shipperTel",
													"name" : querying.integrativeQuery.i18n('foss.querying.consignorShipperTel')  //发货人电话
												}, {
													"code" : "receiverMobile",
													"name" : querying.integrativeQuery.i18n('foss.querying.consigneeMobile')  //收货人手机
												}, {
													"code" : "receiverTel",
													"name" : querying.integrativeQuery.i18n('foss.querying.consigneePhone')  //收货人电话
												}]
									},
									proxy : {
										type : 'memory',
										reader : {
											type : 'json',
											root : 'items'
										}
									}
								})
					}, {
						columnWidth : .15,
						name : 'contactNo',
						regex :querying.integrativeQuery.regLimit.m
					}, {
						fieldLabel:querying.integrativeQuery.i18n('foss.querying.transportPropertiesOf'),							//运输性质
						xtype:'commonproductselector',
						showContent : '{name}',// 显示表格列
						name:'transMethod',
						levelses : '3'
					}, {
						name : 'contactManType',
						xtype : 'combobox',
						queryMode : 'local',
						displayField : 'name',
						valueField : 'code',
						margin : '5 0 0 10',
						columnWidth : .1,
						value : 'shipper',
						store : Ext.create('Ext.data.Store', {
									// 定义字段
									fields : [{
												name : 'code',
												type : 'string'
											}, {
												name : 'name',
												type : 'string'
											}],
									data : {
										'items' : [{
													"code" : "shipper",
													"name" : querying.integrativeQuery.i18n('foss.querying.consignor')  //发货人
												}, {
													"code" : "reveiver",
													"name" : querying.integrativeQuery.i18n('foss.querying.consignee')  //收货人
												}]
									},
									proxy : {
										type : 'memory',
										reader : {
											type : 'json',
											root : 'items'
										}
									}
								})
					}, {
						columnWidth : .15,
						name : 'contactManName',
							listeners:{
								change:function(field,new_v,old_v){
									if(!Ext.isEmpty(new_v)&& new_v.length >=50){
										//先去两端的空格
										var new_value = new_v.trim();
										//再把值设置给文本框(值是截取十位字符)
										field.setValue(new_value.substring(0,new_value.length>=50?50:new_value.length));
									}
								},
								specialkey: function(field, e){
				                    if (e.getKey() == e.ENTER) {
				                        me.searchBtnHandler(this.up('form').query('button')[1]);
				                    }
				                }
							}
							
					},  {
						xtype : 'rangeDateField',
						dateType : 'datetimefield_date97',
						fieldId : 'Foss_integrativeQueryIndex_StartEndDate-date97',
						dateType : 'datetimefield_date97',
						columnWidth : .46,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.startAndEndTime'),  //起止时间
//						dateRange : 31,
						fromName : 'startDate',
						toName : 'endDate',
						dateFat : 'yyyy-MM-dd HH:mm:ss',
						fromValue : Ext.Date.format(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate(), 0, 0, 0),
								'Y-m-d H:i:s'),
						toValue : Ext.Date.format(new Date(new Date()
												.getFullYear(), new Date()
												.getMonth(), new Date()
												.getDate(), 23, 59, 59),
								'Y-m-d H:i:s')
					},{
						name : 'startWaybillNo',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.waybillNumberDan'),  //单号
						regex :querying.integrativeQuery.regLimit.expressW,
						columnWidth : .16,
						listeners:{
							change:function(field,new_v,old_v){
								if(!Ext.isEmpty(new_v)&& new_v.length >=10){
									//先去两端的空格
									var new_value = new_v.trim();
									//再把值设置给文本框
									field.setValue(new_value.substring(0,new_value.length>=10?10:new_value.length));
								}
							},
							specialkey: function(field, e){
			                    if (e.getKey() == e.ENTER) {
			                        me.searchBtnHandler(this.up('form').query('button')[1]);
			                    }
			                }
						}
					}, {
						name : 'endWaybillNo',
						fieldLabel : '—',
						labelWidth : 20,
						regex :querying.integrativeQuery.regLimit.expressW,
						labelSeparator : '',
						columnWidth : .13,
						listeners:{
							change:function(field,new_v,old_v){
								if(!Ext.isEmpty(new_v)&& new_v.length >=10){
									//先去两端的空格
									var new_value = new_v.trim();
									//再把值设置给文本框(值是截取十位字符)
									field.setValue(new_value.substring(0,new_value.length>=10?10:new_value.length));
								}
							},
							specialkey: function(field, e){
			                    if (e.getKey() == e.ENTER) {
			                        me.searchBtnHandler(this.up('form').query('button')[1]);
			                    }
			                }
						}
					}, buttonPanel]
		}, {
			xtype : 'form',
			title : querying.integrativeQuery.i18n('foss.querying.tripsPositiveSingleNumberQuery'),   //车次/正单号查询
			tabConfig : {
				width : 150
			},
			layout : 'column',
			defaults : {
				// margin: '5 10 5 10',
				xtype : 'textfield',
				labelWidth : 100,
				margin : '5 0 0 0',
				columnWidth : .25
			},
			items : [{
						name : 'freigthType',
						xtype : 'combobox',
						queryMode : 'local',
						displayField : 'name',
						valueField : 'code',
						forceSelection:true,
						value : 'vehicleAssembleNo',
						store : Ext.create('Ext.data.Store', {
									// 定义字段
									fields : [{
												name : 'code',
												type : 'string'
											}, {
												name : 'name',
												type : 'string'
											}],
									data : {
										'items' : [{
													"code" : "vehicleAssembleNo",
													"name" : querying.integrativeQuery.i18n('foss.querying.tripsPositive')  //车次
												}, {
													"code" : "airWaybillNo",
													"name" : querying.integrativeQuery.i18n('foss.querying.tripsSingleNumber')  //正单号
												}]
									},
									proxy : {
										type : 'memory',
										reader : {
											type : 'json',
											root : 'items'
										}
									}
								})
					}, {
						name : 'freightNo',
						listeners:{
							change:function(field,new_v,old_v){
								if(!Ext.isEmpty(new_v)&& new_v.length >=80){
									//先去两端的空格
									var new_value = new_v.trim();
									//再把值设置给文本框(值是截取十位字符)
									field.setValue(new_value.substring(0,new_value.length>=80?80:new_value.length));
								}
							},
							specialkey: function(field, e){
				                   if (e.getKey() == e.ENTER) {
				                       me.searchBtnHandler(this.up('form').query('button')[1]);
				                   }
				               }
						}
					}, buttonPanel]
		}]
	}
});

// ------------------------------------------------列表区域-------------------------------------------------------------
// 左侧——运单列表
Ext.define('Foss.integrativeQueryIndex.WayBillResultGrid', {
	extend : 'Ext.grid.Panel',
	// 增加表格列的分割线
	columnLines : true,
	// region:'west',
	id : 'Foss_integrativeQueryIndex_QueryResult_Id',
	width : 200,
	height : 1040,
	// columnWidth : 0.2,
	// 表格对象增加一个边框
	frame : true,
	stripeRows : true,
	// 增加表格列的分割线
	columnLines : true,
	// 定义表格的标题
	title : querying.integrativeQuery.i18n('foss.querying.AWBList'),  //运单列表
	collapsible : true,
	animCollapse : true,
	// 定义选择框
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	store : null,
	//消息提醒框
	showWarningMsg : function(title,message,fun){
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
		//3秒后提醒框隐藏
		setTimeout(function(){
	        Ext.Msg.hide();
	    }, 3000);
	},
	custWindow : null,
	//获取客户显示窗口
	getCustWindow : function(){
		var me = this;
		if(Ext.isEmpty(this.custWindow)){
			this.custWindow = Ext.create('Foss.integrativeQueryIndex.customerIndex.CustomerWin');
		}
		return this.custWindow;
	},
	// 定义表格列信息
	columns : [Ext.create('Ext.grid.RowNumberer', {
			width : 40
	}), {
		// 字段标题
		header : querying.integrativeQuery.i18n('foss.querying.billNumber'),  //单号
		// 关联model中的字段名
		dataIndex : 'waybillNo',
		flex : 1
	}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.integrativeQueryIndex.Store.WaybillNOStore');
		me.listeners = {
			itemclick : function( rowModel, record, index, eOpts) {
				var waybillBodyPanel = me.up('panel'),
					waybillFullInfoPanel = waybillBodyPanel.getWayBillFullInfoPanel(),
					wayBillInfoCache = waybillBodyPanel.getWayBillInfoCache();
					url = querying.realPath('searchWaybillInfoByWaybillNo.action'),
						params = {
						'waybillVo' : {
							'condition' : {
								'waybillNo' : record.get('waybillNo')
							}
						}
					},
					wayBillVo = wayBillInfoCache.get(record.get('waybillNo'));
				if(!Ext.isEmpty(wayBillVo)){
					querying.integrativeQuery.waybillNo = record.get('waybillNo');
					querying.integrativeQuery.arriveDeptCode = wayBillVo.arriveDeptCode;
					//绑定运单详细信息
					waybillFullInfoPanel.bindData(wayBillVo);
					return;
				}
				querying.requestJsonAjax(url, params, 
						function(result){
					//绑定返货处理信息
				     	Ext.getCmp('Foss_integrativeQueryIndex_ReturnHandleTabPanel_id').store.loadData(Ext.isEmpty(result.waybillVo.returnGoodsRequestEntitys)?[]:result.waybillVo.returnGoodsRequestEntitys);
							//缓存运单详细信息
							wayBillInfoCache.add(record.get('waybillNo'), result.waybillVo);
							querying.integrativeQuery.waybillNo = record.get('waybillNo');
							querying.integrativeQuery.arriveDeptCode = result.waybillVo.arriveDeptCode;
							//绑定运单详细信息
							waybillFullInfoPanel.bindData(result.waybillVo);
							//加载异常信息,提示用户
							if(!Ext.isEmpty(result.waybillVo.exceptionInfo)){
								Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),result.waybillVo.exceptionInfo);  //foss提醒您
							}
						}, 
						function(result) {
							Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),result.message);  //foss提醒您
						}
				);
			}
		}
		me.callParent([cfg]);
	}
});

// 运单信息
Ext.define('Foss.integrativeQueryIndex.SimpleWayBillForm', {
	extend : 'Ext.form.Panel',
	id : 'Foss_integrativeQueryIndex_SimpleRightBodyPanel_id',
	layout : 'auto',
	title : querying.integrativeQuery.i18n('foss.querying.waybillInformation'),  //运单信息
	frame : true,
	defaultType : 'textfield',
	collapsible : true,
	animCollapse : true,
	layout : {
		type : 'table',
		columns : 3
	},
	 height:350,
	defaults : {
		readOnly : true,
		margin : '2 5 0 5',
		labelWidth : 100
	},
	//添加红字标记的方法，返回一个label
	getComp : function(comName, labelField) {
		return {
			xtype : 'label',
			name : comName,
			margin : '0 5 0 8',
			html : '<span style="color:red;font-size:12px;font-weight:bold;background-color:yellow">'
					+ labelField + '</span>'
		}
	},
	getComp1 : function(comName, labelField) {
		return {
			xtype : 'label',
			name : comName,
			margin : '5 5 0 -66',
			html : '<br/><span style="color:red;font-size:12px;font-weight:bold;background-color:yellow">'
					+ labelField + '</span>'
		}
	},
	//用于增加特殊标记
	addNature : function(record,isZMJ) {
		var me = this;
		var natureForm = me.down('form');
		if ('Y' === record.get('pickupCentralized')||'是' === record.get('pickupCentralized')) {
			// natureForm.findField('pickupCentralized').hidden=false;
			natureForm.add(me.getComp('pickupCentralized', querying.integrativeQuery.i18n('foss.querying.centralizedAccessDelivery')));  //集中接送货
		}
		/**
		 * @author 218392 张永雪
		 * 集中接送货是Y并且展会货是Y,让展会货换行，在集中接送货下方
		 */
		if (('Y' === record.get('isExhibitCargo')||'是' === record.get('isExhibitCargo'))
			&&('Y' === record.get('pickupCentralized')||'是' === record.get('pickupCentralized'))) {
			// natureForm.findField('isExhibitCargo').hidden=false;
			natureForm.add(me.getComp1('isExhibitCargo', querying.integrativeQuery.i18n('foss.querying.isExhibitCargo')));  //展会货
		}
		/**
		 * @author 218392 张永雪
		 * 集中接送货是N并且展会货是Y,展会货不换行
		 */
		if (('Y' === record.get('isExhibitCargo')||'是' === record.get('isExhibitCargo'))
			&&('N' === record.get('pickupCentralized')||'否' === record.get('pickupCentralized'))) {
			// natureForm.findField('isExhibitCargo').hidden=false;
			natureForm.add(me.getComp('isExhibitCargo', querying.integrativeQuery.i18n('foss.querying.isExhibitCargo')));  //展会货
		}
		if ('Y' === record.get('preciousGoods')||'是' === record.get('preciousGoods')) {
			natureForm.add(me.getComp('preciousGoods', querying.integrativeQuery.i18n('foss.querying.valuables')));  //贵重物品
		}
		if ('Y' === record.get('carDirectDelivery')||'是' === record.get('carDirectDelivery')) {
			natureForm.add(me.getComp('carDirectDelivery', querying.integrativeQuery.i18n('foss.querying.cartsDirect')));  //大车直送
		}
		if ('Y' === record.get('specialShapedGoods')||'是' === record.get('specialShapedGoods')) {
			natureForm.add(me.getComp('specialShapedGoods', querying.integrativeQuery.i18n('foss.querying.specialItems')));  //特殊物品
		}
		if ('Y' === record.get('isWholeVehicle')||'是' === record.get('isWholeVehicle')) {
			if('Y' === record.get('isPassOwnDepartment')||'是' === record.get('isPassOwnDepartment')){
				natureForm.add(me.getComp('isWholeVehicle',
						querying.integrativeQuery.i18n('foss.querying.vehicleUpSalesDepartment')+'约车编码:'
						 +/*me.getForm().findField('orderVehicleNum')*/record.get('orderVehicleNum')));  //整车(到达营业部)
			}else{//TODO_WP
				natureForm.add(me.getComp('isWholeVehicle',
						querying.integrativeQuery.i18n('foss.querying.vehicleUpCustomerReaching')+'约车编码:'
						 +/*me.getForm().findField('orderVehicleNum')*/record.get('orderVehicleNum')));  //整车(到达客户处)
			}
		}
		//子母件
		if ('Y' == isZMJ) {
			natureForm.add(me.getComp('isZMJ', '子母件'));  //子母件
		}
		
		//【2016-07-27-308861】是否电子运单
		if('LTLEWAYBILL' == record.get('waybillType')){
			me.clearNatureForm();
			natureForm.add(me.getComp('waybillType', '零担电子运单'));
		}
	},
	clearNatureForm : function() {
		var me = this;
		var natureForm = me.down('form');
		var labels = natureForm.query('label');
		Ext.Array.each(labels, function(item, index, allLabels) {
			natureForm.remove(item);
		});
	},
	bindData: function(leaveDeptCode,arriveDeptCode,wayBillRecord,wayBillNo,isZMJ){
		var me = this,
			//登陆用户当前部门
			currentDeptCode = FossUserContext.getCurrentDeptCode(),
			//快递点部
			isExpressPart = FossUserContext.getCurrentDept().expressPart,
			outLayInfoObj=wayBillRecord.data;
		me.clearNatureForm();
		var outLayInfoObj=wayBillRecord.data;
		var orderChannel=wayBillRecord.get('orderChannel');
		//判断是否淘宝运单
		if(orderChannel=='TAOBAO'){
		  wayBillRecord.set('orderChannel','是');
		}else{
		 wayBillRecord.set('orderChannel','否');
		}
		//预付费保密=Y时，收货部门=当前登录人部门则预付金额显示出来，否则隐藏
		if(wayBillRecord.get('secretPrepaid')=='Y'){
			prePayAmountObj=me.getForm().findField('prePayAmount');
			if(outLayInfoObj.receiveOrgName == FossUserContext.getCurrentDept().name){
				prePayAmountObj.setVisible(true);
			}else{
				prePayAmountObj.setVisible(false);
			}
		}
		//如果收货部门=当前登录人部门
		if(currentDeptCode === leaveDeptCode){
			// 简单信息--判断发货人手机和电话信息是否显示
			me.getForm().loadRecord(wayBillRecord);
			me.addNature(wayBillRecord,isZMJ);
		}else if(currentDeptCode === arriveDeptCode&&querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryFindShipperButton')){
			// 简单信息--判断发货人手机和电话信息是否显示
			me.getForm().loadRecord(wayBillRecord);
			me.addNature(wayBillRecord,isZMJ);
			//快递点部  登录人的部门必须是快递点部，查询的单必须是经济快递
		}else if(isExpressPart === 'Y' & wayBillRecord.get('productCode')=='PACKAGE'){
			// 简单信息--判断发货人手机和电话信息是否显示
			me.getForm().loadRecord(wayBillRecord);
			me.addNature(wayBillRecord,isZMJ);
		}else{
			wayBillRecord.set('deliveryCustomerMobilephone',null);
			wayBillRecord.set('deliveryCustomerPhone',null);
			me.getForm().loadRecord(wayBillRecord);
			me.addNature(wayBillRecord,isZMJ);
		}
		// 是否有权限显示查看发货人按钮   需求  黄帅  查看发货人规则制定
		if(currentDeptCode === leaveDeptCode){
			Ext.getCmp('T_querying-integrativeQueryIndex_toolbar').getComponent('Foss_integrativeQueryIndex_viewConsignor_Id').show();
		}else if(currentDeptCode === arriveDeptCode&&querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryFindShipperButton')){
			Ext.getCmp('T_querying-integrativeQueryIndex_toolbar').getComponent('Foss_integrativeQueryIndex_viewConsignor_Id').show();
		// 快递点部
		}else if(isExpressPart === 'Y' & wayBillRecord.get('productCode')=='PACKAGE'){
			Ext.getCmp('T_querying-integrativeQueryIndex_toolbar').getComponent('Foss_integrativeQueryIndex_viewConsignor_Id').show();
		}else{
			Ext.getCmp('T_querying-integrativeQueryIndex_toolbar').getComponent('Foss_integrativeQueryIndex_viewConsignor_Id').hide();
		}
		//wp_078816_增加大客户标记
		//判断收货客户如果是大客户，就在后面加VIP标记
		if(wayBillRecord.get('receiveBigCustomer')=== 'Y'){
			me.getForm().findField('receiveCustomerName').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.receivingCustomerName')
					+'<span  class="big_Customer_pic_common">');
		}else{
			me.getForm().findField('receiveCustomerName').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.receivingCustomerName'));
		}
		//判断发货客户如果是大客户，就在后面加VIP标记
		if(wayBillRecord.get('deliveryBigCustomer')=== 'Y'){
			me.getForm().findField('deliveryCustomerName').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.shippingCustomerName')
					+'<span  class="big_Customer_pic_common">');		
		}else{
			me.getForm().findField('deliveryCustomerName').setFieldLabel('&nbsp;&nbsp;'+
					querying.integrativeQuery.i18n('foss.querying.shippingCustomerName'));
		}
		//转换运单类型
		if(wayBillRecord.get('waybillType')!=null&&wayBillRecord.get('waybillType')!=''&&wayBillRecord.get('waybillType')=='EWAYBILL'){
			me.getForm().findField('waybillType').show();
			me.getForm().findField('waybillType').setValue(querying.integrativeQuery.i18n('foss.querying.waybillType0'));
		}else{
			if(wayBillRecord.get('productCode')!=null&&wayBillRecord.get('productCode')!=''&&(wayBillRecord.get('productCode')=='RCP'||wayBillRecord.get('productCode')=='PACKAGE')){
				me.getForm().findField('waybillType').show();
				me.getForm().findField('waybillType').setValue(querying.integrativeQuery.i18n('foss.querying.waybillType1'));
			}else{
				me.getForm().findField('waybillType').hide();
			}
		}
		//判断运单是零担还是快递，如果是零担则开单快递员和签收快递员字段隐藏,目前只显示 标准快递，360特惠件，电商尊享 3中类型的数据
		if(wayBillRecord.get('productCode')=='EPEP'||wayBillRecord.get('productCode')=='PACKAGE'||wayBillRecord.get('productCode')=='RCP'){
			me.getForm().findField('billingcourier').show();
			me.getForm().findField('signcourier').show();
		}else{
			me.getForm().findField('billingcourier').hide();
			me.getForm().findField('signcourier').hide();
		}
		//判断是否显示原单及返货单号
		//如果原单号和返单号都为空，那么两个值都不显示
		if(Ext.isEmpty(wayBillRecord.get('returnWaybillNo')) && Ext.isEmpty(wayBillRecord.get('originalWaybillNo'))){
			me.getForm().findField('returnWaybillNo').hide();
			me.getForm().findField('originalWaybillNo').hide();
			me.getForm().findField('originalWaybillNoTotalFee').hide();
			me.getForm().findField('oriAndReturntotalFee').hide();
		}else if(wayBillNo==wayBillRecord.get('returnWaybillNo')){ //如果查询的返单号则原单号显示返单号隐藏
			me.getForm().findField('returnWaybillNo').hide();
			me.getForm().findField('originalWaybillNoTotalFee').show();
			me.getForm().findField('oriAndReturntotalFee').show();
			me.getForm().findField('originalWaybillNo').show();
		}else if(wayBillNo==wayBillRecord.get('originalWaybillNo')){//如果查询的是原单号则返单号显示原单号隐藏
			me.getForm().findField('returnWaybillNo').show();
			me.getForm().findField('originalWaybillNo').hide();
			me.getForm().findField('originalWaybillNoTotalFee').hide();
			me.getForm().findField('oriAndReturntotalFee').hide();
		}
		

		if(Ext.isEmpty(wayBillRecord.get('returnSignWaybillNo')) && Ext.isEmpty(wayBillRecord.get('originalSignWaybillNo'))){
			me.getForm().findField('returnSignWaybillNo').hide();
			me.getForm().findField('originalSignWaybillNo').hide();
		}else if(wayBillNo==wayBillRecord.get('returnSignWaybillNo')){ //如果查询的返单号则原单号显示返单号隐藏
			me.getForm().findField('returnSignWaybillNo').hide();
			me.getForm().findField('originalSignWaybillNo').show();
		}else if(wayBillNo==wayBillRecord.get('originalSignWaybillNo')){//如果查询的是原单号则返单号显示原单号隐藏
			me.getForm().findField('returnSignWaybillNo').show();
			me.getForm().findField('originalSignWaybillNo').hide();
		}
		
		
	},
	constructor : function(config) {
		// TODO 配载类型 --没有
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
			name : 'receiveOrgName',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingDepartment')  //收货部门
		}, {
			name : 'receiveCustomerName',
			labelWidth : 120,
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingCustomerName') //收货客户名称
		}, {
			/*name : 'loadMethod',//取消
			fieldLabel : '配载类型'
		}, {*/
			name : 'receiveCustomerMobilephone',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consigneeMobile')  //手机
		}, {
			name : 'customerPickupOrgName',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.deliveryOutlets') //提货网点
		}, {
			name : 'receiveCustomerPhone',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consigneePhone')  //电话
		}, {
			name : 'receiveMethod',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.collectMode') //提货方式
		}, {
			name : 'receiveCustomerContact',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivePersonContact') //收货联系人
		}, {
			name : 'deliveryCustomerName',
			labelWidth : 120,
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.shippingCustomerName') //发货客户名称
		}, {
			name : 'deliveryCustomerContact',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.shippingContact')  //发货联系人
		}, {
			name : 'deliveryCustomerMobilephone',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consignorShipperMobile')  //发货人手机
		}, {
			name : 'deliveryCustomerPhone',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consignorShipperTel')  //发货人电话
		}, {
			// TODO CODE?
			name : 'targetOrgCode',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.destinationStation')  //目的站
		}, {
			name : 'productName',//productName  productCode
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.transportPropertiesOf')  //运输性质
		}, {
			name : 'goodsName',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.nameOfGoods')  //货物品名
		}, {
			name : 'paidMethod',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.termsPayment')  //付款方式
		}, {
			name : 'goodsPackage',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.package')  //包装
		}, {
			name : 'toPayAmount',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.ayTheAmount')  //到付金额
		}, {
			name : 'prePayAmount',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.prepaidAmount')  //预付金额
		}, {
			name : 'goodsQtyTotal',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalNumberOf')  //总件数
		}, {
			name : 'returnBillType',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.backSingleCategory')  //返单类别
		}, {
			name : 'goodsWeightTotal',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalWeight')  //总重量
		}, {
			name : 'goodsVolumeTotal',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.totalVolume')  //总体积
		}, {
			name : 'receiveCustomerAddress',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consigneeAddress'),  //收货人地址
			width:530,
			colspan : 2
		}, {
			name : 'waybillType',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.waybillType')  //运单类型
		}, {
			name : 'returnWaybillNo',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.returnWaybillNo')  //返货单号
		}, {
			name : 'returnSignWaybillNo',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.returnSignWaybillNo')  //签收单返单号
		}, {
			name : 'originalWaybillNo',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.originalWaybillNo'), //原单号
			tipConfig:{
				cls:'autoHeight',
				bodyCls:'autoHeight',
				width:300,
				trackMouse:true,
				tipType:'normal',
				isShowByData:true,
				tipBodyElement:'Foss.integrativeQueryIndex.tipPanel'
			}
		}, 
		 {
			name : 'originalSignWaybillNo',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.originalSignWaybillNo'), //原单号
		},{
			name : 'originalWaybillNoTotalFee',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.originalWaybillNoTotalFee')  //原单号费用
		}, {
			name : 'oriAndReturntotalFee',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.originalAndreturnWayWaybillNoTotalFee') //总金额
		},{
			name:'orderChannel',
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.isTaobaoWaybill')//是否淘宝运单
		},
		{
			xtype : 'combobox',
			name : 'flabelleavemonth',
			fieldLabel : querying.integrativeQuery.i18n('foss.querying.consignorcustomersegmentation'),  //发货人客户分群
			readOnly : true,  //设置为可读
			displayField : 'valueName',
			valueField : 'valueCode',
			queryMode : 'local',
			triggerAction : 'all',
			store : FossDataDictionary.getDataDictionaryStore('CRM_CUSTOMER_GROUP', null, {
			})	
		},{
			name:'billingcourier',
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.billingcourier')//开单快递员
		},{
			name:'signcourier',
			fieldLabel:querying.integrativeQuery.i18n('foss.querying.signcourier')//签收快递员
		},{
			xtype : 'form',
			height : 25,	
			colspan : 3,
			layout : 'column'
		}];
		me.callParent([config]);
	}
});
//tip黄框显示（原单号）
Ext.define('Foss.integrativeQueryIndex.tipPanel',{
	extend: 'Ext.panel.Panel',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	//回调方法
	bindData : function(value){
		this.update(value);
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
// 完整信息（包括运输信息、发货客户信息、收货客户信息、货物信息、费用信息）
Ext.define('Foss.integrativeQueryIndex.CompleteWayBillInfoPanel', {
	extend : 'Ext.panel.Panel',
	id : 'Foss_integrativeQueryIndex_CompleteRightBodyPanel_id',
	hidden : true,
	layout : 'auto',
	transportInfoForm: null,
	getTransportInfoForm: function(){
		if(Ext.isEmpty(this.transportInfoForm)){
			this.transportInfoForm = Ext.create('Foss.integrativeQueryIndex.Form.TransportInfoForm'); //运输信息
		}
		return this.transportInfoForm;
	},
	dliverGoodsCustomerForm: null,
	getDliverGoodsCustomerForm: function(){
		if(Ext.isEmpty(this.dliverGoodsCustomerForm)){
			this.dliverGoodsCustomerForm = Ext.create('Foss.integrativeQueryIndex.Form.DliverGoodsCustomerForm'); //发货客户信息
		}
		return this.dliverGoodsCustomerForm;
	},
	receiveGoodsCustomerForm: null,
	getReceiveGoodsCustomerForm: function(){
		if(Ext.isEmpty(this.receiveGoodsCustomerForm)){
			this.receiveGoodsCustomerForm = Ext.create('Foss.integrativeQueryIndex.Form.ReceiveGoodsCustomerForm'); //收货客户信息
		}
		return this.receiveGoodsCustomerForm;
	},
	cargoInfoForm: null,
	getCargoInfoForm: function(){
		if(Ext.isEmpty(this.cargoInfoForm)){
			this.cargoInfoForm = Ext.create('Foss.integrativeQueryIndex.Form.CargoInfoForm'); //货物信息
		}
		return this.cargoInfoForm;
	},
	outlayInfoForm: null,
	getOutlayInfoForm: function(){
		if(Ext.isEmpty(this.outlayInfoForm)){
			this.outlayInfoForm = Ext.create('Foss.integrativeQueryIndex.Form.OutlayInfoForm'); //费用信息
		}
		return this.outlayInfoForm;
	},
	bindData: function(leaveDeptCode,arriveDeptCode,wayBillRecord){
		this.getTransportInfoForm().bindData(wayBillRecord);
		this.getDliverGoodsCustomerForm().bindData(leaveDeptCode,arriveDeptCode,wayBillRecord);
		this.getReceiveGoodsCustomerForm().bindData(wayBillRecord);
		this.getCargoInfoForm().bindData(wayBillRecord);
		this.getOutlayInfoForm().bindData(wayBillRecord);
	},
	constructor : function(config) {
		var me = this;
		me.items = [
			me.getTransportInfoForm(),me.getDliverGoodsCustomerForm(),
			me.getReceiveGoodsCustomerForm(),me.getCargoInfoForm(),
			me.getOutlayInfoForm()
		];
		me.callParent([config]);
	}
});

//定义一个返回处理Return goods handling Tab面板
Ext.define('Foss.integrativeQueryIndex.ReturnHandlingTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,// 默认激活第一个Tab页
	frame : false,
	height : 380,
	internalTrackingGrid: null,
	getInternalTrackingGrid: function(){
		if(Ext.isEmpty(this.internalTrackingGrid)){
			this.internalTrackingGrid =  Ext.create('Foss.integrativeQueryIndex.InternalTrackingGrid');
		}
		return this.internalTrackingGrid;
	},
	externalGrid: null,
	getExternalGrid: function(){
		if(Ext.isEmpty(this.externalGrid)){
			this.externalGrid =  Ext.create('Foss.integrativeQueryIndex.Grid.ExternalGrid');
		}
		return this.externalGrid;
	},

	constructor : function(config) {
		Ext.apply(this, config);    
		var me = this;
		this.items = [{
			title : querying.integrativeQuery.i18n('foss.querying.internalLocus'),  //内部轨迹
			autoScroll:true,
			tabConfig : {
				width : 100
			},
			items : me.getInternalTrackingGrid()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.inquiriesByThePiece'),  //按件查询
			autoScroll:true,
			tabConfig : {
				width : 100
			},
			items : me.getExternalGrid()
		}];
		this.callParent(arguments);
	}
})

// 货物轨迹Tab
Ext.define('Foss.integrativeQueryIndex.CargoTrackTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,// 默认激活第一个Tab页
	frame : false,
	height : 380,
	internalTrackingGrid: null,
	getInternalTrackingGrid: function(){
		if(Ext.isEmpty(this.internalTrackingGrid)){
			this.internalTrackingGrid =  Ext.create('Foss.integrativeQueryIndex.InternalTrackingGrid');
		}
		return this.internalTrackingGrid;
	},
	externalGrid: null,
	getExternalGrid: function(){
		if(Ext.isEmpty(this.externalGrid)){
			this.externalGrid =  Ext.create('Foss.integrativeQueryIndex.Grid.ExternalGrid');
		}
		return this.externalGrid;
	},
	ZMJLocusGrid: null,
	getZMJLocusGrid: function(){
		if(Ext.isEmpty(this.ZMJLocusGrid)){
			this.ZMJLocusGrid =  Ext.create('Foss.integrativeQueryIndex.ZMJLocusGrid');
		}
		return this.ZMJLocusGrid;
	},
	constructor : function(config) {
		Ext.apply(this, config);    
		var me = this;
		this.items = [{
			title : querying.integrativeQuery.i18n('foss.querying.internalLocus'),  //内部轨迹
			autoScroll:true,
			tabConfig : {
				width : 100
			},
			items : me.getInternalTrackingGrid()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.inquiriesByThePiece'),  //按件查询
			autoScroll:true,
			tabConfig : {
				width : 100
			},
			items : me.getExternalGrid()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.ZMJLocus'),  //子母件轨迹
			autoScroll:true,
			tabConfig : {
				width : 100
			},
			items : me.getZMJLocusGrid()
		}];
		this.callParent(arguments);
	}
})

// 跟踪记录model
Ext.define('Foss.integrativeQueryIndex.Model.TrackRecordModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'trackContent',
						type : 'string'
					}, // 跟踪内容
					{
						name : 'contactMan',
						type : 'string'
					}, // 联系人
					{
						name : 'trackTpye',
						type : 'string'
					}, // 收货客户名称
					{
						name : 'trackTime',
						type : 'string'
					}, // 配载类型
					{
						name : 'trackDept',
						type : 'string'
					}, // 配载类型
					{
						name : 'trackMan',
						type : 'string'
					}]
		})

// 跟踪记录store
Ext.define('Foss.integrativeQueryIndex.TrackRecordStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.TrackRecordModel',
			data : []
		});
//提货信息Model
Ext.define('Foss.integrativeQueryIndex.specialAddedModel', {
	extend : 'Ext.data.Model',
	fields : [{name:'acceptTime',type:'date',convert: dateConvert,defaultValue:null},
      {name:'providerOrderNo',type:'string'},
      {name:'deliveryName',type:'string'},
      {name:'providerName',type:'string'},
      {name:'providerPhone',type:'string'},
      {name:'installTime',type:'date',convert: dateConvert,defaultValue:null},
      {name:'pickUpTime',type:'date',convert: dateConvert,defaultValue:null},
      {name:'valid',type:'int'},
      {name:'remark',type:'string'}
      ]
});		
//提货信息store
Ext.define('Foss.integrativeQueryIndex.SpecialValueAddedServiceStore',{
              extend:'Ext.data.Store',
             //model:'Foss.querying.integrativeQuery.SpecialValueAddedServiceModel'
              model:'Foss.integrativeQueryIndex.specialAddedModel'
             /* proxy : {
						type : 'ajax',
						actionMethods : 'post',
						url : querying.realPath('searchSpecialValueAddedServiceInfoByWaybillNo.action'),//请求地址
						reader : {
							type : 'json',
							root : 'waybillVo.deliveryInformationDto',//获取的数据
							totalProperty : 'totalCount'//总个数
						}
					},
					listeners: {
						beforeload: function(store, operation, eOpts){
								Ext.apply(operation,{
									params : {
										'waybillVo.condition.waybillNo':querying.integrativeQuery.waybillNo
									}
								});	
						}
				    }*/
}
);
//定义一个返货处理列表
Ext.define('Foss.integrativeQueryIndex.ReturnHandleTabPanel',{extend:'Ext.grid.Panel',
		id:'Foss_integrativeQueryIndex_ReturnHandleTabPanel_id',
		columnLines:true,
		border:true,
		height:360,
		stripeRows : true,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
		}, 
		store:null,
		// 定义表格列信息
		/*plugins : [{
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 绑定一个Template
		rowBodyTpl : [querying.integrativeQuery.i18n('foss.querying.pbTrackRecordInfo')]   //更改信息:
		}],*/
		columns : [{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.oriWaybill')  ,//工单编号
				// 关联model中的字段名
				dataIndex : 'dealnumber',
				width : 150
			
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.returnStatus'),  //处理状态
				// 关联model中的字段名
				dataIndex : 'returnStatus',
				xtype : 'ellipsiscolumn',
				width : 100,
				renderer:function(value){
				  var value_name = FossDataDictionary.rendererSubmitToDisplay(value,'RETURN_STATUS');
				  if(value==value_name){
					value_name ='未处理';
				  	if(value=='HANDLED'){
				  		value_name = '已处理';
				  	}
				  }
				  return value_name;
				}
			}, {
						// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.returnReason'), // 返货原因
						// 关联model中的字段名
				dataIndex : 'returnReason',
				xtype : 'ellipsiscolumn',
				renderer : function(value) {
				/*
			 	* if(value=='COMPANY_REASON'){ return '公司原因'; }else
				 * if(value=='CUSTOMER_REASON'){ return '客户原因';
				*/
					var value_name = FossDataDictionary.rendererSubmitToDisplay(
						value, 'RETURN_REASON');
						if(value==value_name){
						     value_name='客户原因';
						     if(value=='COMPANY_REASON'){
						     	value_name='公司原因';
						     }
						}
				
						return value_name;
				},
				width : 100
			},
			{
						// 字段标题
					header : querying.integrativeQuery.i18n('foss.querying.returnType'), // 返货类型
						// 关联model中的字段名
					dataIndex : 'returnType',
					xtype : 'ellipsiscolumn',
					width : 100,
					renderer:function(value){
					 var value_name =  FossDataDictionary.rendererSubmitToDisplay(
									value, 'RETURN_GOODS_TYPE');
							if(value_name=='CUSTORMER_REFUSE'){
									value_name ='客户拒收';							   
							}else if(value_name == 'SEVEN_DAYS_RETURN'){
							   value_name ='七天返货';
							}else if(value_name == 'OUTBOUND_THREE_DAYS_RETURN'){
							   value_name = '外发三天返货';
							}
					return value_name;
					}
				}, {
						// 字段标题
					header :querying.integrativeQuery.i18n('foss.querying.reportContent'), // 调查内容
					// 关联model中的字段名
					dataIndex : 'returnSurvey',
					xtype : 'ellipsiscolumn',
					width : 200
					},
					
				{// 上报时间
					width : 150,
					header : querying.integrativeQuery.i18n('foss.querying.timeReport'),
					dataIndex : 'timeReport',
					xtype : 'ellipsiscolumn',
					renderer : function(v) {
					if (!Ext.isEmpty(v)) {
						return Ext.Date.format(new Date(v * 1),
								'Y-m-d H:i:s');
							}
					return v;
						}
					}, // 处理时间
				{
						width : 150,
						header : querying.integrativeQuery.i18n('foss.querying.createTime'),
						dataIndex : 'updateTime',
						xtype : 'ellipsiscolumn',
						renderer : function(v) {
							if (!Ext.isEmpty(v)) {
								return Ext.Date.format(new Date(v * 1),
										'Y-m-d H:i:s');
							}
							return v;
						}
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.ReturnHandleStore');
				me.callParent([cfg]);
			}
		});
// 定义一个跟踪记录信息列表
Ext.define('Foss.integrativeQueryIndex.TrackRecordGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_TrackRecordGrid_id',
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	border : true,
	height : 360,
	stripeRows : true,
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	// 定义表格的标题
	// title:'查询结果',
	// collapsible: true,
	// animCollapse: true,
	store : null,
	// 定义表格列信息
	plugins : [{
		ptype : 'rowexpander',
		// 定义行展开模式（单行与多行），默认是多行展开(值true)
		rowsExpander : false,
		// 绑定一个Template
		rowBodyTpl : [querying.integrativeQuery.i18n('foss.querying.pbTrackRecordInfo')]   //更改信息:
	}],
	columns : [{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.trackingContent'),  //跟踪内容
				// 关联model中的字段名
				dataIndex : 'trackContent',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.contact'),  //联系人
				// 关联model中的字段名
				dataIndex : 'contacts',
				xtype : 'ellipsiscolumn',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.traceCategories'),  //跟踪类别
				// 关联model中的字段名
				dataIndex : 'traceCategories',
				xtype : 'ellipsiscolumn',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.trackTime'),  //跟踪/起草时间
				// 关联model中的字段名
				dataIndex : 'createTime',
				width : 132,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.trackingDepartment'),  //跟踪/起草部门
				// 关联model中的字段名
				dataIndex : 'createOrgName',
				xtype : 'ellipsiscolumn',
				width : 130
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.trackingPeople'),  //跟踪/起草人
				// 关联model中的字段名
				dataIndex : 'createUserName',
				xtype : 'ellipsiscolumn',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.processingTime'),  //受理时间
				// 关联model中的字段名
				dataIndex : 'acceptedTime',
				width : 132,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.AcceptOrg'),  //受理部门
				// 关联model中的字段名
				dataIndex : 'acceptedOrgName',
				xtype : 'ellipsiscolumn',
				width : 130
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.auditor'),  //受理人
				// 关联model中的字段名
				dataIndex : 'acceptedMan',
				xtype : 'ellipsiscolumn',
				width : 100
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.acceptingRemarks'),  //受理备注
				// 关联model中的字段名
				dataIndex : 'acceptedRemark',
				width : 100,
				renderer:function(value, metadata) {
					if(!Ext.isEmpty(value)){
						metadata.tdAttr = 'data-qwidth="100" data-qtip="' + value + '"';
					}
			    return value;
			}
			}],
			addWindow:null,
			getAddWindow:function(){
				if(this.addWindow==null){
					this.addWindow = Ext
					.create('Foss.integrativeQueryIndex.TrackRecordWindow');
				}
				return this.addWindow;
			},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.integrativeQueryIndex.TrackRecordStore');
		me.dockedItems =[{
			xtype :'toolbar',
			dock :'top',
			layout :'column',
			defaults :{
				margin :'0 10 0 0'
			},
			items :[
			{type : 'button',
				text : querying.integrativeQuery.i18n('foss.querying.add'),  //新增
				disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeAddButton'),
				hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeAddButton'),
				handler : function() {
					var wind = me.getAddWindow();
					wind.show();
				}},{type : 'button',
					text : querying.integrativeQuery.i18n('foss.querying.export'),  //导出
					disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeAddButton'),
					hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeAddButton'),
					handler : function() {
						if(!Ext.fly('downloadAttachFileForm')){
						    var frm = document.createElement('form');
						    frm.id = 'downloadAttachFileForm';
						    frm.style.display = 'none';
						    document.body.appendChild(frm);
						}
						var result = Ext.getCmp('Foss_integrativeQueryIndex_TrackRecordGrid_id').store;
						//若异常信息不为空
						if(result.getCount()!=0){
							var params = {'waybillVo.condition.waybillNo' : querying.integrativeQuery.waybillNo};
							Ext.Ajax.request({
								url:querying.realPath('queryExportTrackRecords.action'),
								form: Ext.fly('downloadAttachFileForm'),
								method : 'POST',
								params : params,
								isUpload: true
							});
						}else{
							Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.exportFail'));  //FOSS提醒您
						}
					}}
					]
			}];
		me.callParent([cfg]);
	}
});

//查询备注
Ext.define('Foss.integrativeQueryIndex.queryRemarks',{
	extend:'Ext.data.Store',
	fields:['text','value'],
	data:[{'text':querying.integrativeQuery.i18n('foss.querying.queryRemarks'),
		   'value':querying.integrativeQuery.i18n('foss.querying.queryRemarks')}]
});
//查询备注,未排单备注
Ext.define('Foss.integrativeQueryIndex.unSingleRowRemarks',{
	extend:'Ext.data.Store',
	fields:['text','value'],
	data:[{'text':querying.integrativeQuery.i18n('foss.querying.queryRemarks'),
		   'value':querying.integrativeQuery.i18n('foss.querying.queryRemarks')},
	      {'text':querying.integrativeQuery.i18n('foss.querying.unSingleRowRemarks'),
		   'value':querying.integrativeQuery.i18n('foss.querying.unSingleRowRemarks')}]
});

// 跟踪记录from面板
Ext.define('Foss.integrativeQueryIndex.TrackRecordFormPanel', {
			extend : 'Ext.form.Panel', 
			height : '230',
			defaultType:'textfield',
			defaults:{
				margin : '5 5 5 0',
				labelWidth : 120
			},
			defaultType:'textfield',
			layout:{
				type : 'table',
				columns : 1
			},
			items : [{
						xtype: 'combobox',
						name:'traceCategories',
						displayField:'text',
						valueField:'value',
						margin:'5 0 0 0',
						value:querying.integrativeQuery.i18n('foss.querying.queryRemarks'),
						allowBlack:false,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.traceCategories'),  //跟踪类别
						maxLength:50,
						listeners:{
							change:function(combo,nv,ov){
								if(nv==querying.integrativeQuery.i18n('foss.querying.unSingleRowRemarks')){
									this.ownerCt.items.get(2).name='';
									this.ownerCt.items.get(3).name='trackContent';
									this.ownerCt.items.get(2).setVisible(false);
									this.ownerCt.items.get(2).setDisabled(true);
									this.ownerCt.items.get(3).setValue("");
									this.ownerCt.items.get(3).setVisible(true);
									this.ownerCt.items.get(3).setDisabled(false);
								}else{
									this.ownerCt.items.get(2).name='trackContent';
									this.ownerCt.items.get(3).name='';
									this.ownerCt.items.get(2).setValue("");
									this.ownerCt.items.get(2).setVisible(true);
									this.ownerCt.items.get(2).setDisabled(false);
									this.ownerCt.items.get(3).setVisible(false);
									this.ownerCt.items.get(3).setDisabled(true);
									this.ownerCt.items.get(4).setVisible(false);
									this.ownerCt.items.get(4).setDisabled(true);
								}
							}
						}
					},{
						xtype : 'textfield',
						name : 'contacts',
						margin : '5 0 0 0',
						allowBlank:false,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.contact'),  //联系人
						maxLength:50
					},{
						xtype : 'textarea',
						name : 'trackContent',
						margin : '5 0 0 0', 
						allowBlank:false,
						width:360,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.trackingContent'),  //跟踪内容
						maxLength:190
					},{
						xtype : 'combobox',
						margin : '5 0 0 0', 
						allowBlank:false,
						queryMode:'local',
						columnWidth: 0.275,
						width:300,
						anchor : '100%',
						maxLength : 50,
						editable:false,
						displayField:'valueName',
						valueField:'valueName',
						store:null,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.trackingContent'),  //跟踪内容
						listeners:{
							change:function(combo,nv,ov){
								if(nv=='客户要求指定日期送货(客户)'){
									this.ownerCt.items.get(4).setVisible(true);
									this.ownerCt.items.get(4).setDisabled(false);
									var trackRecordFormPanel = this.ownerCt;
									if(trackRecordFormPanel.getForm().findField('arriveDate')){
										trackRecordFormPanel.remove('Foss.querying.integrativeQuery_arriveDate1_ID');
									    var configItem = [{
									    	xtype : 'datetimefield_date97',
											name : 'arriveDate',
											id:'Foss.querying.integrativeQuery_arriveDate1_ID',
											margin : '5 0 0 0',
											time:true,
											format: 'Y-m-d H:i:s',
											allowBlank:false,
											fieldLabel : '指定日期',
											editable:'false',
											dateConfig: {
												el: 'Foss.querying.integrativeQuery_arriveDate1_ID-inputEl',
												dateFmt: 'yyyy-MM-dd HH:mi:ss'
											}
									    }];
									    trackRecordFormPanel.add(configItem[0]);
									}
								}else{
									this.ownerCt.items.get(4).setValue("");
									this.ownerCt.items.get(4).setVisible(false);
									this.ownerCt.items.get(4).setDisabled(true);
								}
							}
						}
					},{
						xtype : 'datetimefield_date97',
						name : 'arriveDate',
						id:'Foss.querying.integrativeQuery_arriveDate1_ID',
						margin : '5 0 0 0',
						time:true,
						format: 'Y-m-d H:i:s',
						allowBlank:false,
						fieldLabel : '指定日期',
						editable:'false',
						dateConfig: {
							el: 'Foss.querying.integrativeQuery_arriveDate1_ID-inputEl',
							dateFmt: 'yyyy-MM-dd HH:mi:ss'
						}
					}],
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items[3].store=FossDataDictionary.getDataDictionaryStore('BSE_UNSINGLE_ROWREMARKS');
		me.fbar = [{
			text :querying.integrativeQuery.i18n('foss.baseinfo.cancel'),//取消
			handler :function(){
				me.up().close();
			}
		},{
			text : querying.integrativeQuery.i18n('foss.baseinfo.save'),//保存
			cls:'yellow_button',
			handler :function(){
				var form = me.getForm();
				if(Ext.isEmpty(querying.integrativeQuery.waybillNo)){
					Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseSelectWaybill'));  //FOSS提醒您','请选择一条运单！
					return;
				}
	            if (form.isValid()) {
	            	var trackRecord = Ext.create('Foss.querying.integrativeQuery.TrackRecordEntity');
	            	trackRecord.set('waybillNo',querying.integrativeQuery.waybillNo);
	            	trackRecord.set('contacts',form.findField('contacts').getValue());
	            	trackRecord.set('trackContent',form.findField('trackContent').getValue()+form.findField('arriveDate').getValue());
	            	trackRecord.set('createTime',new Date());
	            	trackRecord.set('createUserCode',FossUserContext.getCurrentUser().employee.empCode);
	            	trackRecord.set('createUserName',FossUserContext.getCurrentUser().employee.empName);
	            	trackRecord.set('createOrgCode',FossUserContext.getCurrentDeptCode());
	            	trackRecord.set('createOrgName',FossUserContext.getCurrentDept().name);
	            	trackRecord.set('traceCategories',form.findField('traceCategories').getValue());  //查询备注
	            	trackRecord = trackRecord.data;
	            	//新增 跟踪记录
					Ext.Ajax.request({
			    		url:querying.integrativeQuery.baseinfoRealPath+'addTrackRecord.action',
			    		jsonData:{'objectVo':{'trackRecord':trackRecord}},
			    		timeout:600000,
			    		success:function(response){
			    			var result = Ext.decode(response.responseText),
			    			returnInt = result.objectVo.returnInt;
			    			if(!Ext.isEmpty(returnInt) && 1 ==returnInt){
			    				//grid中增加一行记录
			    				Ext.getCmp('Foss_integrativeQueryIndex_TrackRecordGrid_id').store.add(result.objectVo.trackRecord);
			    				me.up('window').close();
			    			}
			    		},
			    		exception:function(response){
			    			var result = Ext.decode(response.responseText);
			    			if(Ext.isEmpty(result)){
			    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
			    			}else{
			    				Ext.Msg.alert(result.message);
			    			}
			    		}
			    	});
	            }else{
	            	Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseConfirmWhetherNotFormComplete'));  //FOSS提醒您','请确认表单是否填写完整！
	            }
			} 
		}];
		me.callParent([cfg]);
	}
});
// 跟踪记录的新增功能window
Ext.define('Foss.integrativeQueryIndex.TrackRecordWindow', {
			extend : 'Ext.window.Window',
			closeAction : 'hide',
			title : querying.integrativeQuery.i18n('foss.querying.newTrackRecord'),  //新增跟踪记录
			layout : 'auto',
			modal:true,
			width : 400,
			height : 250,
			model:true,
			addForm:null,
			getAddForm:function(){
				if(this.addForm==null){
					this.addForm = Ext.create('Foss.integrativeQueryIndex.TrackRecordFormPanel');
				}
				return this.addForm;
			},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				var trackRecordFormPanel = me.getAddForm();
				me.items = [trackRecordFormPanel];
				me.listeners = {
					beforehide:function(me){
						me.getAddForm().getForm().reset();
					},
					show:function(){
						//判断是否调度组,车队,运单到达部门
						if(querying.integrativeQuery.dispatchTeam == 'Y'||
								querying.integrativeQuery.transDepartment == 'Y'||
								querying.integrativeQuery.currentDeptCode == querying.integrativeQuery.arriveDeptCode){
							trackRecordFormPanel.query('combobox')[0].store=Ext.create('Foss.integrativeQueryIndex.unSingleRowRemarks');
						}else{
							trackRecordFormPanel.query('combobox')[0].store=Ext.create('Foss.integrativeQueryIndex.queryRemarks');
						}
						if(trackRecordFormPanel.getForm().findField('traceCategories').getValue()
								==querying.integrativeQuery.i18n('foss.querying.queryRemarks')){
							trackRecordFormPanel.query('combobox')[1].setVisible(false);
							trackRecordFormPanel.query('combobox')[1].setDisabled(true);
							trackRecordFormPanel.getForm().findField('arriveDate').setVisible(false);
							trackRecordFormPanel.getForm().findField('arriveDate').setDisabled(true);
						};
					}
				};
				me.callParent([cfg]);
			}
		})

// 跟踪记录（运单相关信息——跟踪记录）
Ext.define('Foss.integrativeQueryIndex.TrackRecordTabPanel', {
			extend : 'Ext.panel.Panel',
			cls : "innerTabPanel",
			bodyCls : "overrideChildLeft",
			frame : true,
			height : 390,
			constructor : function(config) {
				Ext.apply(this, config);
				var trackRecordGrid = Ext.create('Foss.integrativeQueryIndex.TrackRecordGrid');
				this.items = [trackRecordGrid];
				this.callParent(arguments);
			}
		})

//返货信息Model
Ext.define('Foss.integrativeQueryIndex.Model.ReturnHandlerModel',{
	extend:'Ext.data.Model',
	fields:[{
			//工单编号
		   name:'dealnumber',
		   type:'string'
	
		},{
			//处理状态
			name:'returnStatus',
			type:'string'
		
		},{
		   //返货原因
			name:'returnReason',
			type:'string'
		
		},{
			//调查内容
			name:'returnSurvey',
			type:'string'
		
		
		},//上报时间
		{
			name : 'timeReport',
			type : 'string'
					}, 
		{// 处理时间
			name : 'updateTime',
			type : 'string'
		} ,{
		   name:'returnType',//返货类型
		   type:'string'
		}
	    
	
	
	
	]
	
});
// 出发运单更改Model
Ext.define('Foss.integrativeQueryIndex.Model.LeaveChangeWaybillModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 单据编号
				name : 'waybillNo',
				type : 'string'
			}, {
				// 变更信息
				name : 'changeItems',
				type : 'string'
			}, {
				// 起草人
				name : 'drafter',
				type : 'string'
			}, {
				// 起草部门
				name : 'draftOrgName',
				type : 'string'
			},{
				// 起草时间
				name : 'draftTime',
				type:'date',convert: dateConvert,defaultValue:null
			}, {
				// 需要受理部门
				name : 'needAcceptOrg',
				type : 'string'
			},{
				// 受理执行部门
				name : 'operateOrgName',
				type : 'string'
			}, {
				// 受理时间
				name : 'operateTime',
				type:'string'
			}, {
				// 受理备注
				name : 'notes',
				type : 'string'
			}, {
				// 变更原因
				name : 'rfcReason',
				type : 'string'
			}, {
				// 受理状态
				name : 'status',
				type : 'string'
			},{
				// 受理人
				name : 'auditor',
				type : 'string'
			},{
				// 审核状态
				name : 'acceptStatus',
				type : 'string'
			},{
				// 审核时间
				name : 'acceptTime',
				type:'string'
			},{
				// 审核备注
				name : 'acceptNotes',
				type : 'string'
			},{
				// 审核人
				name : 'acceptor',
				type : 'string'
			}]
});
// 到达运单更改Model
Ext.define('Foss.integrativeQueryIndex.Model.ChangeWaybillModel', {
	extend : 'Ext.data.Model',
	fields : [{
			// 单据编号
		name : 'rfcNo',
		type : 'string'
	}, {
		// 变更信息
		name : 'changeMsg',
		type : 'string'
	}, {
		// 起草人
		name : 'drafter',
		type : 'string'
	}, {
		// 起草时间
		name : 'draftTime',
		type:'date',convert: dateConvert,defaultValue:null
	}, {
		// 受理部门
		name : 'operateOrgName',
		type : 'string'
	}, {
		// 受理时间
		name : 'operateTime',
		type:'date',convert: dateConvert,defaultValue:null
	}, {
		// 受理备注
		name : 'notes',
		type : 'string'
	}, {
		// 变更原因
		name : 'reason',
		type : 'string'
	}, {
		// 受理状态
		name : 'status',
		type : 'string'
			}]
});
//返货信息store
Ext.define('Foss.integrativeQueryIndex.ReturnHandleStore',{
              extend:'Ext.data.Store',
              model:'Foss.integrativeQueryIndex.Model.ReturnHandlerModel'
}
);

// 出发更改store
Ext.define('Foss.integrativeQueryIndex.StartChangeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.LeaveChangeWaybillModel'
		})

// 到达更改store
Ext.define('Foss.integrativeQueryIndex.ArriveChangeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.ChangeWaybillModel'
		})

// 定义一个出发更改信息列表
Ext.define('Foss.integrativeQueryIndex.StartChangeGrid', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_StartChangeGrid_id',
			title : querying.integrativeQuery.i18n('foss.querying.startingChange'),  //出发更改
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : false,
			stripeRows : true,
			height : 170,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			plugins : [{
						ptype : 'rowexpander',
						// 定义行展开模式（单行与多行），默认是多行展开(值true)
						rowsExpander : false,
						// 绑定一个Template
						rowBodyTpl : [querying.integrativeQuery.i18n('foss.querying.pbModifyInfo')]   //更改信息:
					}],
			// 定义表格列信息
			columns : [{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.documentNumber'),  //单据编号
						// 关联model中的字段名
						dataIndex : 'waybillNo',
						width : 80
					}, {
						// 字段标题
						text : querying.integrativeQuery.i18n('foss.querying.changeHistory'),  //变更记录
						// 关联model中的字段名
						dataIndex : 'changeItems',
						xtype : 'ellipsiscolumn',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.drafters'),  //起草人
						// 关联model中的字段名
						dataIndex : 'drafter',
						width : 80
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.draftingOrg'),  //起草部门
						// 关联model中的字段名
						dataIndex : 'draftOrgName',
						width : 80
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.draftingTime'),  //起草时间
						// 关联model中的字段名
						dataIndex : 'draftTime',
						width : 120,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.callStatus'),  //受理状态
						// 关联model中的字段名
						dataIndex : 'status',
						width : 80,
						renderer:function(v){
							return FossDataDictionary. rendererSubmitToDisplay (v,'WAYBILL_RFC_STATUS');
						}
				}, {
					// 字段标题
					header : querying.integrativeQuery.i18n('foss.querying.needAcceptOrg'),  //需要受理部门
					// 关联model中的字段名
					dataIndex : 'needAcceptOrg',
					width : 100
				},{
					// 字段标题
					header : querying.integrativeQuery.i18n('foss.querying.theDepartmentHandling'),  //受理执行部门
					// 关联model中的字段名
					dataIndex : 'operateOrgName',
					width : 100
				}, {
					// 字段标题
					header : querying.integrativeQuery.i18n('foss.querying.auditor'),  //受理人
					// 关联model中的字段名
					dataIndex : 'auditor',
					align:'center',
					width : 80
				}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.processingTime'),  //受理时间
						// 关联model中的字段名
						dataIndex : 'operateTime',
						width : 120,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return v.substring(0,19);
							}
							return v;
						}
					},  {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.acceptingRemarks'),  //受理备注
						// 关联model中的字段名
						dataIndex : 'notes',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.reasonForChange'),  //变更原因
						// 关联model中的字段名
						dataIndex : 'rfcReason',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.acceptStatus'),  //审核状态
						// 关联model中的字段名
						dataIndex : 'acceptStatus',
						width : 100,
						renderer:function(v){
							if(v=='PRE_ACCECPT'){
								return querying.integrativeQuery.i18n('foss.querying.preAccecpt');  //已审核
							}else{
								return FossDataDictionary. rendererSubmitToDisplay (v,'WAYBILL_RFC_STATUS');
							}
						}
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.acceptTime'),  //审核时间
						// 关联model中的字段名
						dataIndex : 'acceptTime',
						width : 100
//						renderer:function(v){
//							if(!Ext.isEmpty(v)){
//								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
//							}
//							return v;
//						}
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.acceptor'),  //审核人
						// 关联model中的字段名
						dataIndex : 'acceptor',
						align:'center',
						width : 60
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.acceptNotes'),  //审核备注
						// 关联model中的字段名
						dataIndex : 'acceptNotes',
						width : 100
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.StartChangeStore');
				me.callParent([cfg]);
			}
		});

// 定义一个到达更改信息列表
Ext.define('Foss.integrativeQueryIndex.ArriveChangeGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_ArriveChangeGrid_id',
	title : querying.integrativeQuery.i18n('foss.querying.reachTheChange'),  //到达更改
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	height : 170,
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	plugins : [{
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : false,
				// 绑定一个Template
				rowBodyTpl : [querying.integrativeQuery.i18n('foss.querying.pbModifyInfoChangeMsg')]  //更改信息
			}],
	// 定义表格列信息
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.documentNumber'),  //单据编号
				// 关联model中的字段名
				dataIndex : 'rfcNo',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.changeHistory'),  //变更记录
				// 关联model中的字段名
				dataIndex : 'changeMsg',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.drafters'),   //起草人
				// 关联model中的字段名
				dataIndex : 'drafter',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.draftingTime'),  //起草时间
				// 关联model中的字段名
				dataIndex : 'draftTime',
				width : 100,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.processingTime'),  //受理时间
				// 关联model中的字段名
				dataIndex : 'operateTime',
				width : 120,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.theDepartmentHandling'),  //受理执行部门
				// 关联model中的字段名
				dataIndex : 'operateOrgName',
				width : 100
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.acceptingRemarks'),  //受理备注
				// 关联model中的字段名
				dataIndex : 'notes',
				width : 60
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.reasonForChange'),  //变更原因
				// 关联model中的字段名
				dataIndex : 'reason',
				width : 60
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.callStatus'),  //受理状态
				// 关联model中的字段名
				dataIndex : 'status',
				width : 60,
				renderer:function(v){
					return FossDataDictionary. rendererSubmitToDisplay (v,'PKP_SIGN_RFC_APPROVE_STATUS');
				}
				
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.integrativeQueryIndex.ArriveChangeStore');
		me.callParent([cfg]);
	}
});

// 定义一个运单更改panel
Ext.define('Foss.integrativeQueryIndex.ChangeWaybillTabPanel', {
	extend : 'Ext.panel.Panel',
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	height : 390,
	frame : true,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	items : [
			Ext.create('Foss.integrativeQueryIndex.StartChangeGrid'),
			Ext.create('Foss.integrativeQueryIndex.ArriveChangeGrid')],
	constructor : function(config) {
		var me = this;
		me.callParent([config]);
	}
});

//定义一个交单情况信息列表
Ext.define('Foss.integrativeQueryIndex.IssuingGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_IssuingGrid_id',
	title : '交单情况',  //通知情况
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	height : 160,
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	// 定义表格列信息
	/*交单时间 billTime、预计送货日期preDeliverDate、交单件数billQty交单人billOperateName、
	交单部门billOperateOrgName、交单接收部门、送货要求deliverRequire
	、实际收货地址receiveCustomerAddress、退/撤单时间、是否有效交单active*/
	columns : [{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.billTime'),  //交单时间
				// 关联model中的字段名
				dataIndex : 'billTime',
				width : 140,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				text : querying.integrativeQuery.i18n('foss.querying.preDeliverDate'),//'预计送货日期',  
				// 关联model中的字段名
				dataIndex : 'preDeliverDate',
				width : 90,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d');
					}
					return v;
				}
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.billQty'),  //交单件数
				// 关联model中的字段名
				dataIndex : 'billQty',
				width : 80
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.billOperateName'),  //交单人
				// 关联model中的字段名
				dataIndex : 'billOperateName',
				xtype : 'ellipsiscolumn',
				width : 80
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.billOperateOrgName'),  //交单部门
				// 关联model中的字段名
				dataIndex : 'billOperateOrgName',
				width : 120
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.deliverRequire'),  //送货要求
				// 关联model中的字段名
				dataIndex : 'deliverRequire',
				xtype : 'ellipsiscolumn',
				flex:1
		}, {
			// 字段标题
			header : querying.integrativeQuery.i18n('foss.querying.receiveCustomerAddress'),  //实际收货地址
			// 关联model中的字段名
			dataIndex : 'receiveCustomerAddress',
			xtype : 'ellipsiscolumn',
			flex:1
	}, {
		// 字段标题
		header : querying.integrativeQuery.i18n('foss.querying.modifyTime'),  //退/撤单时间
		// 关联model中的字段名
		dataIndex : 'modifyTime',
		xtype : 'ellipsiscolumn',
		width :140,
		renderer:function(v){
			if(!Ext.isEmpty(v)){
				return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
			}
			return v;
		}
}, {
	// 字段标题
	header : querying.integrativeQuery.i18n('foss.querying.active'),  //是否有效交单
	// 关联model中的字段名
	dataIndex : 'active',
	xtype : 'ellipsiscolumn',
	flex:1,
	renderer:function(v){
		if('Y'==v){
			return '是';
		}else{
			return '否';
		}
	}
}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.integrativeQueryIndex.IssuingStore');
		me.callParent([cfg]);
	}
});	
//定义一个退单情况信息列表
Ext.define('Foss.integrativeQueryIndex.ReturnIssuingGrid', {
	extend : 'Ext.grid.Panel',
	id : 'Foss_integrativeQueryIndex_ReturnIssuingGrid_id',
	title : '退单情况',  //通知情况
	// 增加表格列的分割线
	columnLines : true,
	// 表格对象增加一个边框
	frame : false,
	stripeRows : true,
	height : 160,
	viewConfig:{
    	enableTextSelection : true//设置行可以选择，进而可以复制    
	}, 
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	// 定义表格列信息
	/*“退单时间createDate”、“退单原因returnReason”、“退单原因备注returnReasonNotes”、
	 * “退单人createrName”、“退单部门createOrgName*/
	columns : [{
				// 字段标题foss.querying.createDate
				header : querying.integrativeQuery.i18n('foss.querying.createDate'), //退单时间
				// 关联model中的字段名
				dataIndex : 'createDate',
				width : 140,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			}, {
				// 字段标题
				text : querying.integrativeQuery.i18n('foss.querying.returnReasons'), //退单原因
				// 关联model中的字段名
				dataIndex : 'returnReason',
				width : 170,
				xtype : 'ellipsiscolumn'
				//var value_name = FossDataDictionary.rendererSubmitToDisplay(
					//	value, 'RETURN_REASON')
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.returnReasonNotes'),  //退单原因备注
				// 关联model中的字段名
				dataIndex : 'returnReasonNotes',
				width : 289,
				xtype : 'ellipsiscolumn'
				
			}, {
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.createrName'),  //退单人
				// 关联model中的字段名
				dataIndex : 'createrName',
				xtype : 'ellipsiscolumn',
				width : 89
			},{
				// 字段标题
				header : querying.integrativeQuery.i18n('foss.querying.returnOrgName'),  //退单部门
				// 关联model中的字段名
				dataIndex : 'createOrgName',
				width : 150
			}],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.integrativeQueryIndex.ReturnIssuingStore');
		me.callParent([cfg]);
	}
});	
//定义一个派送情况--通知情况的model
Ext.define('Foss.integrativeQueryIndex.Model.NoticeModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 通知时间
				name : 'operateTime',
				type : 'date',convert: dateConvert,defaultValue:null
			}, {
				// 通知方式
				name : 'noticeType',
				type : 'string'
			}, {
				// 通知对象
				name : 'consignee',
				type : 'string'
			}, {
				// 联系方式
				name : 'mobile',
				type:'string'
			}, {
				// 是否通知成功
				name: 'noticeResult',
					 convert:function(value){
					 if(value!=null && value == 'SUCCESS'){
					 return '是';
					 }else {
					 return '否';
					 }
					 }
			}, {
				// 送货要求
				name : 'deliverRequire',
				type:'string'
			},{
				// 通知内容
				name : 'noticeContent',
				type:'string'
			},{
				// 通知结果
				name : 'noticeResultContent',
				type:'string',
				convert:function(value,record){
					return  record.get('deliverRequire') + record.get('noticeContent'); 
				}
			}]
});
/*交单时间 billTime、预计送货日期preDeliverDate、交单件数billQty交单人billOperateName
、交单部门billOperateOrgName、交单接收部门、送货要求deliverRequire
、实际收货地址receiveCustomerAddress、退/撤单时间、是否有效交单active*/
//定义一个交单情况 model 
Ext.define('Foss.integrativeQueryIndex.Model.IssuingModel',{
			extend:'Ext.data.Model',
			fields:[{//交单时间
				     name:'billTime',
				     type:'date',convert: dateConvert,defaultValue:null
			},{//预计送货日期
			     name:'preDeliverDate',
			     type:'date',convert: dateConvert,defaultValue:null
			},{//交单件数
		     name:'billQty',
		     type:'int'
			},{ //交单人
			     name:'billOperateName',
			     type:'string'
			},{ //交单部门
				 name:'billOperateOrgName',
				 type:'string'
			},{//送货要求
			     name:'deliverRequire',
			     type:'string'
			},{//实际收货地址
			     name:'receiveCustomerAddress',
			     type:'string'
			},{//退单撤单实际
			     name:'modifyTime',
			     type:'date',convert: dateConvert,defaultValue:null
			},{//是否有效
			     name:'active',
			     type:'string'
			}
			 ]
});
//定义一个退单情况 model 
/*“退单时间createDate”、“退单原因returnReason”、“退单原因备注returnReasonNotes”、
“退单人createrName”、“退单部门createOrgName*/
Ext.define('Foss.integrativeQueryIndex.Model.ReturnIssuingModel',{
			extend:'Ext.data.Model',
			fields:[{//退单时间
				     name:'createDate',
				     type:'date',convert: dateConvert,defaultValue:null
			},{
			     name:'returnReason',
			     type:'string'
			},{//交单时间
		     name:'returnReasonNotes',
		     type:'string'
			},{
			     name:'createrName',
			     type:'string'
			},{
				 name:'createOrgName',
				 type:'string'
			}
			 ]
});
//定义一个派送情况--派送情况的model
Ext.define('Foss.integrativeQueryIndex.Model.DeliverySituationModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 送货时间
						name : 'operateTime',
						type : 'date',convert: dateConvert,defaultValue:null
					}, {
						// 送货司机
						name : 'driverName',
						type : 'string'
					}, {
						// 司机手机
						name : 'driverTel',
						type : 'string'
					}, {
						// 派送件数
						name : 'arrangeGoodsQty',
						type:'int'
					}, {
						// 派送车辆
						name : 'vehicleNo',
						type : 'string'
					}, {
						// 派送单号
						name : 'deliverbillNo',
						type:'string'
					},{
						// 预派送单状态
						name : 'status',
						type:'string'
					},{
						// 创建人
						name : 'createUserName',
						type:'string'
					},
					{
						// 差异报告类型/备注
						name : 'gapType',
						type:'string'
					},{
						// 创建时间
						name : 'submitTime',
						type : 'date',convert: dateConvert,defaultValue:null
					},{
						// 创建人所在部门
						name : 'createOrgName',
						type:'string'
					}]
		})
//定义一个派送情况--签收情况的model
Ext.define('Foss.integrativeQueryIndex.Model.SignCaseModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 签收时间
						name : 'signTime',
						type : 'date',convert: dateConvert,defaultValue:null
					}, {
						// 签收人
						name : 'deliverymanName',
						type : 'string'
					}, {
						//签收人类型
						name:'deliverymanType',
						type:'string'
					},{
						// 签收件数
						name : 'signGoodsQty',
						type : 'int'
					}, {
						// 签收情况
						name : 'signSituation',
						type:'string'
					},{
						// 签收類型
						name : 'receiveMethod',
						type:'string'
					},{
						//是否PDA签收
						name : 'isPdaSign',
						 convert:function(value){
							 if(value!=null && value === 'Y'){
							 return '是';
							 }else {
							 return '否';
							 }
							 }
					}, {
						// 签收备注
						name : 'signNote',
						type : 'string'
					},{
						// 是否有效签收
						name : 'active',
						 convert:function(value){
							 if(value!=null && value === 'Y'){
							 return '是';
							 }else {
							 return '否';
							 }
							 }
					},{
						//到达联签收 状态
						name:'signStatus',
						type:'string'
					},{
						//到达联签收 状态
						name:'goodShortAndSerialNo',
						type:'string'
					}]
		})
//交单情况store 
Ext.define('Foss.integrativeQueryIndex.IssuingStore',{
			extend:'Ext.data.Store',
			model:'Foss.integrativeQueryIndex.Model.IssuingModel'
});
//退单情况store ReturnIssuingStore
Ext.define('Foss.integrativeQueryIndex.ReturnIssuingStore',{
	extend:'Ext.data.Store',
	model:'Foss.integrativeQueryIndex.Model.ReturnIssuingModel'
});		
// 通知情况store
Ext.define('Foss.integrativeQueryIndex.NoticeStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.NoticeModel'
		})

// 派送情况store
Ext.define('Foss.integrativeQueryIndex.DeliverySituationStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.DeliverySituationModel'
		})		

// 签收情况store
Ext.define('Foss.integrativeQueryIndex.SignCaseStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.integrativeQueryIndex.Model.SignCaseModel'
		})

// 定义一个通知情况信息列表
Ext.define('Foss.integrativeQueryIndex.NoticeGrid', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_NoticeGrid_id',
			title : querying.integrativeQuery.i18n('foss.querying.notice'),  //通知情况
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : false,
			stripeRows : true,
			height : 160,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			// 定义表格列信息
			columns : [{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.noticeTime'),  //通知时间
						// 关联model中的字段名
						dataIndex : 'operateTime',
						width : 140,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					}, {
						// 字段标题
						text : querying.integrativeQuery.i18n('foss.querying.noticeType'),  //通知方式
						// 关联model中的字段名
						dataIndex : 'noticeType',
						width : 100,
						renderer:function(v){
							return FossDataDictionary. rendererSubmitToDisplay (v,'PKP_NOTIFY_CUSTOMER_TYPE');
						}
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.noticeConsignee'),  //通知对象
						// 关联model中的字段名
						dataIndex : 'consignee',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.mobile'),  //联系方式
						// 关联model中的字段名
						dataIndex : 'mobile',
						xtype : 'ellipsiscolumn',
						width : 130
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.yesOrNo'),  //是否成功
						// 关联model中的字段名
						dataIndex : 'noticeResult',
						width : 80
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.noticeContent'),  //通知结果
						// 关联model中的字段名
						dataIndex : 'noticeResultContent',
						flex:1,
						renderer:function(value, metadata) {
							if(!Ext.isEmpty(value)){
								metadata.tdAttr = 'data-qwidth="220" data-qtip="' + value + '"';
							}
					    return value;
					}
				}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.NoticeStore');
				me.callParent([cfg]);
			}
		});	

//定义一个派送情况信息列表
Ext.define('Foss.integrativeQueryIndex.DeliverySituationGrid', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_DeliverySituationGrid_id',
			title : querying.integrativeQuery.i18n('foss.querying.deliverySituation'),  //派送情况
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : false,
			stripeRows : true,
			height : 150,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			// 定义表格列信息
			columns : [{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.deliveryTime'),  //送货时间
						// 关联model中的字段名
						dataIndex : 'operateTime',
						width : 140,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					}, {
						// 字段标题
						text : querying.integrativeQuery.i18n('foss.querying.driverName'),  //送货司机
						// 关联model中的字段名
						dataIndex : 'driverName',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.driverTel'),  //司机手机
						// 关联model中的字段名
						dataIndex : 'driverTel',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.arrangeGoodsQty'),  //派送件数
						// 关联model中的字段名
						dataIndex : 'arrangeGoodsQty',
						width : 80
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.vehicleNo'),  //派送车辆
						// 关联model中的字段名
						dataIndex : 'vehicleNo',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.deliverbillNo'),  //派送单号
						// 关联model中的字段名
						dataIndex : 'deliverbillNo',
//						flex:1
						width : 80
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.status'),  //预派送单状态
						// 关联model中的字段名
						dataIndex : 'status',
						width : 120
					}
					,{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.gaptype'),  //差异报告类型/备注
						// 关联model中的字段名
						dataIndex : 'gapType',
						width : 120
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.createUserName'),  //创建人
						// 关联model中的字段名
						dataIndex : 'createUserName',
						width : 80
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.submitTime'),  //创建时间
						// 关联model中的字段名
						dataIndex : 'submitTime',
						width : 80,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.createOrgName'),  //创建人所在部门
						// 关联model中的字段名
						dataIndex : 'createOrgName',
						labelWidth:120,
						width : 120
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.DeliverySituationStore');
				me.callParent([cfg]);
			}
		});

//定义一个签收情况信息列表
Ext.define('Foss.integrativeQueryIndex.SignCaseGrid', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_SignCaseGrid_id',
			title : querying.integrativeQuery.i18n('foss.querying.signCase'),  //签收情况
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : false,
			stripeRows : true,
			height : 150,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			plugins : [{
				ptype : 'rowexpander',
				// 定义行展开模式（单行与多行），默认是多行展开(值true)
				rowsExpander : false,
				// 绑定一个Template
				rowBodyTpl : [querying.integrativeQuery.i18n('foss.querying.noteInfo')]   //备注信息:
			}],
			// 定义表格列信息
			columns : [{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.signTime'),  //签收时间
						// 关联model中的字段名
						dataIndex : 'signTime',
						width : 140,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					}, {
						// 字段标题
						text : querying.integrativeQuery.i18n('foss.querying.deliverymanName'),  //签收人
						// 关联model中的字段名
						dataIndex : 'deliverymanName',
						xtype : 'ellipsiscolumn',
						width : 100
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.deliverymanType'),  //签收人类型
						// 关联model中的字段名
						dataIndex : 'deliverymanType',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.signGoodsQty'),  //签收件数
						// 关联model中的字段名
						dataIndex : 'signGoodsQty',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.signType'),  //签收類型
						// 关联model中的字段名
						dataIndex : 'receiveMethod',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.signSituation'),  //签收情况
						// 关联model中的字段名
						dataIndex : 'signSituation',
						xtype : 'ellipsiscolumn',
						width : 105
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.isPdaSign'),  //是否PDA签收
						// 关联model中的字段名
						dataIndex : 'isPdaSign',
						width : 105
					}, {
						header :'到达联签收状态',  //到达联签收状态
						// 关联model中的字段名
						dataIndex : 'signStatus',
						width : 105
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.isActive'),  //是否有效签收
						// 关联model中的字段名
						dataIndex : 'active',
						width : 100
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.signNote'),  //签收备注
						// 关联model中的字段名
						dataIndex : 'signNote',
						//xtype : 'ellipsiscolumn',
						width : 100,
						renderer:function(value, metadata) {
							if(!Ext.isEmpty(value)){
								metadata.tdAttr = 'data-qwidth="80" data-qtip="' + value + '"';
							}
					        return value;
					    }
					},{
						// update
						header : querying.integrativeQuery.i18n('foss.querying.goodShortAndSerialNo'),  //异常类型件数/流水号
						// 关联model中的字段名
						dataIndex : 'goodShortAndSerialNo',
						width : 130,
						tipConfig: {
							maxWidth: 220,
				            width: 220,
				            height: 140
				        },
						tipBodyElement:'Foss.querying.QueryingSignForm',
				        windowClassName: 'Foss.querying.QueryingSignWindow',
						xtype : 'opentipwindowcolumn'
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.SignCaseStore');
				me.callParent([cfg]);
			}
		});

//自定义提示窗口  @author 张永雪  218392 2015-01-09 15:29:37
Ext.define('Foss.querying.QueryingSignWindow',{
	 extend: 'Ext.window.Window',
	 title: '异常类型件数/流水号详细信息',
	 width: 260,
	 height: 160,
	 closable: true,
	 closeAction: 'hide',
	 userForm: null,
	 getUserForm: function(){
	     if(this.userForm==null){
	         this.userForm = Ext.create('Foss.querying.QueryingSignForm');
	     }
	     return this.userForm;
	 },
	 bindData: function(record,cellIndex,rowIndex){
	     this.getUserForm().getForm().loadRecord(record);
	 },
	 constructor: function(config){
	     var me = this,
	         cfg = Ext.apply({}, config);
	     me.items=[me.getUserForm()];
	     me.callParent([cfg]);
	 }
});  
//自定义提示窗口中的form  @author 张永雪  218392 2015-01-09 15:15:28
Ext.define('Foss.querying.QueryingSignForm',{
	  extend: 'Ext.form.Panel',
	  frame:true,
	  height: 90,
	  width: 220,
	  defaultType : 'textfield',
	  items : [{
	      name: 'goodShortAndSerialNo',
	      fieldLabel: '异常类型件数/流水号',
	      margin:'6 3 3 3',
	      xtype : 'textarea',
	      autoScroll : true,
	      labelWidth:60,
	      width : 185
	  }],
	  bindData: function(record){
	      this.getForm().loadRecord(record);
	  },
	  constructor: function(config){
	      var me = this,
	          cfg = Ext.apply({}, config);
	      me.callParent([cfg]);
	  }
});
	
//定义一个派送情况的panel
Ext.define('Foss.integrativeQueryIndex.DeliverySituationTabPanel', {
			extend : 'Ext.panel.Panel',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			frame : false,
			layout : {
				type : 'vbox',
				align : 'stretch'
			},
			items : [
			        Ext.create('Foss.integrativeQueryIndex.NoticeGrid'),
			        Ext.create('Foss.integrativeQueryIndex.IssuingGrid'),
			        Ext.create('Foss.integrativeQueryIndex.ReturnIssuingGrid'),
					Ext.create('Foss.integrativeQueryIndex.DeliverySituationGrid'),
					Ext.create('Foss.integrativeQueryIndex.SignCaseGrid')],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});



//定义一个财务情况-其他费用的model
Ext.define('Foss.integrativeQueryIndex.Model.OtherExpensesModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 其他费用-应付单据类型（装卸费(SF)、服务补救(CP)、退运费(R)）
				name : 'payableBillType',
				type : 'string'
			}, {
				// 其他费用-金额
				name : 'amount',
				type : 'string'
			}, {
				// 其他费用-付款状态
				name : 'paymentStatus',
				type : 'string'
			}, {
				// 其他费用-付款方式
				name : 'paymentType',
				type : 'string'
			}, {
				// 其他费用-冲应收金额
				name : 'verifyRcvAmount',
				type : 'string'
			}, {
				// 其他费用-应退金额
				name : 'returnableAmount',
				type : 'string'
			}]
})

//定义一个财务情况-发票费用的model
Ext.define('Foss.integrativeQueryIndex.Model.InvoiceModel', {
	extend : 'Ext.data.Model',
	fields : [{
				// 发票费用-始开已开票金额
				name : 'leaveAmount',
				type : 'string'
			}, {
				// 发票费用-到达已开票金额
				name : 'arriveAmount',
				type : 'string'
			}]
})

//定义一个财务情况-代收貨款的model
Ext.define('Foss.integrativeQueryIndex.Model.paymentCollectionModel', {
	extend : 'Ext.data.Model',
	fields : [{
		// 代收贷款-实收代收货款
		name : 'codAmount',
		type : 'string'
	}, {
		// 代收贷款-收款方式
		name : 'paymentTypes',
		type : 'string'
	}, {
		// 代收贷款-冲应收金额
		name : 'verifyRcvAmount',
		type : 'string'
	}, {
		// 代收贷款-应退金额
		name : 'returnableAmount',
		type : 'string'
	}, {
		// 代收贷款-退款状态
		name : 'paymentStatus',
		type : 'string'
	}]
})

// 定义一个财务情况的model
Ext.define('Foss.integrativeQueryIndex.Model.FinancialInfoModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 始发运费-始发付款方式
						name : 'paymentType',
						type : 'string'
					}, {
						// 始发运费-实收始发运费
						name : 'receivedAmount',
						type : 'string'
					}, {
						// 始发运费-收款日期
						name : 'receivedDate',
						type : 'date',convert: dateConvert,defaultValue:null
					}, {
						// 运单号
						name : 'waybillNo',
						type : 'string'
					}, {
						// 到付费用-实收到付总额（到付运费+代收货款）
						name : 'totalAmount',
						type : 'string'
					}, {
						// 到付费用-实收到付运费
						name : 'transportAmount',
						type : 'string'
					}, {
						// 到付费用-付款方式
						name : 'paymentTypes', //String类型数组
						type : 'string'
					}, {
						name : 'originalWaybillNoTotalFee', //原单号费用
						type : 'string'
					}, {
						name : 'oriAndReturntotalFee',//原单和返单总金额
						type : 'string'
					}, {
						// 到付费用-付款时间
						name : 'paymentDate',
						type:'date',convert: dateConvert,defaultValue:null
					}]
		})
		
// 始发运费（运单相关信息——财务情况——始发运费）
Ext.define('Foss.integrativeQueryIndex.ReceivedPayPanel', {
			extend : 'Ext.form.Panel',
			title : querying.integrativeQuery.i18n('foss.querying.receivedPay'),   //始发运费
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			layout :{
				type:'table',
				columns:4
			} ,
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [FossDataDictionary.getDataDictionaryCombo('SETTLEMENT__PAYMENT_TYPE',{
						name : 'paymentType',
						readOnly:true,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivedPayType')  //始发付款方式
					}), {
						name : 'receivedAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paidReceivedPay')  //实收始发运费
					}, {
						name : 'receivedDate',
						xtype:'datefield',
						format: 'Y/m/d',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.collectionTime')  //收款日期
					},{}],// 撑一个位置
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});		

// 到付费用（运单相关信息——财务情况——到付费用）
Ext.define('Foss.integrativeQueryIndex.ArrivePayPanel', {
			extend : 'Ext.form.Panel',
			title : querying.integrativeQuery.i18n('foss.querying.toPayWith'),   //到付费用
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						name : 'totalAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.realReceivePaymentOfTheTotal')  //实收到付总额
					}, {
						name : 'transportAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.realReceiveCost')  //实收到付运费
					},{
						fieldLabel:querying.integrativeQuery.i18n('foss.querying.reachPaymentWay'),//到达付款方式
						readOnly:true,
						name: 'paymentTypes'
					}, {
						name : 'originalWaybillNoTotalFee',
						hidden:true,
						readOnly:true,
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.originalWaybillNoTotalFee')  //实收到付运费
					},{
						fieldLabel:querying.integrativeQuery.i18n('foss.querying.originalAndreturnWayWaybillNoTotalFee'),//到达付款方式
						readOnly:true,
						hidden:true,
						name: 'oriAndReturntotalFee'
					}, {
						name : 'paymentDate',
						xtype:'datefield',
						format: 'Y/m/d',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.reachAccountDate')  //到达付款日期
					}],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

// 定义一个其他費用-装卸费panel
Ext.define('Foss.integrativeQueryIndex.LaborCostsPanel', {
			extend : 'Ext.form.Panel',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			title : querying.integrativeQuery.i18n('foss.querying.laborCosts'),  //装卸费
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.handlingFeeAmount'),		  //装卸费金额			
						name: 'amount',
						readOnly:true
					}, FossDataDictionary.getDataDictionaryCombo('BILL_PAYMENT__REMIT_STATUS',{
						name : 'paymentStatus',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentStatus'),  //付款状态
						readOnly:true
					}),FossDataDictionary.getDataDictionaryCombo('SETTLEMENT__PAYMENT_TYPE',{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.termsPayment'),	 //付款方式			
						name: 'paymentType',
						readOnly:true
					})],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

//定义一个其他費用-服务补救panel
Ext.define('Foss.integrativeQueryIndex.ServiceRecoveryPanel', {
			extend : 'Ext.form.Panel',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			title : querying.integrativeQuery.i18n('foss.querying.serviceRecovery'),  //服务补救
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.taxCredit'),		  //减免金额		
						name: 'amount',
						readOnly:true
					}, FossDataDictionary.getDataDictionaryCombo('BILL_PAYMENT__REMIT_STATUS',{
						name : 'paymentStatus',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentStatus'),  //付款状态
						readOnly:true
					}),FossDataDictionary.getDataDictionaryCombo('SETTLEMENT__PAYMENT_TYPE',{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.termsPayment'),	 //	付款方式			
						name: 'paymentType',
						readOnly:true
					})],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

//定义一个其他費用-退运费panel
Ext.define('Foss.integrativeQueryIndex.RefundShippingPanel', {
			extend : 'Ext.form.Panel',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			title : querying.integrativeQuery.i18n('foss.querying.refundShipping'),  //退运费
			layout : {
				type:'table',
				columns:2
			},
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.refundShippingMoney'),		  //退运费金额	
						name: 'amount'
					}, {
						name : 'verifyRcvAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.amountsReceivable')  //冲应收金额
					},{
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.returnedAmount'),	 //	应退金额			
						name: 'returnableAmount'
					}, FossDataDictionary.getDataDictionaryCombo('BILL_PAYMENT__REMIT_STATUS',{
						name : 'paymentStatus',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentStatus'),  //付款状态
						readOnly:true
					}), FossDataDictionary.getDataDictionaryCombo('SETTLEMENT__PAYMENT_TYPE',{
						name : 'paymentType',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.termsPayment'),  //付款方式
						readOnly:true
					})],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

// 定义一个发票panel
Ext.define('Foss.integrativeQueryIndex.InvoicePanel', {
			extend : 'Ext.form.Panel',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			title : querying.integrativeQuery.i18n('foss.querying.invoice'),  //发票
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						name : 'leaveAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.invoicedAmount')  //始发已开票金额
					}, {
						name : 'arriveAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.reachInvoicedAmount')  //到达已开票金额
					}],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

// 定义一个代收货款panel
Ext.define('Foss.integrativeQueryIndex.PaymentCollectionPanel', {
			extend : 'Ext.form.Panel',
			title : querying.integrativeQuery.i18n('foss.querying.paymentCollection'),  //代收货款
			border : true,
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [{
						name : 'codAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paidPaymentCollection')  //实收代收货款
					}, {
						name : 'paymentTypes',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.receiptWay'),  //收款方式
						readOnly:true
					}, {
						name : 'verifyRcvAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.amountsReceivable')  //冲应收金额
					}, {
						name : 'returnableAmount',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.returnedAmount')  //应退金额
					},FossDataDictionary.getDataDictionaryCombo('COD__STATUS',{
						fieldLabel: querying.integrativeQuery.i18n('foss.querying.refundStatus'),		 //		退款状态	
						name: 'paymentStatus',
						readOnly:true
					})
//					{
//						name : 'codRefundNotes',
//						fieldLabel : querying.integrativeQuery.i18n('foss.querying.paymentCollectionDescription')  //代收货款说明
//					}
					],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

//定义一个财务情况-其他费用panel   ISSUE-3109
Ext.define('Foss.integrativeQueryIndex.OtherExpensesTabPanel', {
	extend : 'Ext.panel.Panel',
	id : 'Foss_integrativeQueryIndex_OtherExpensesTabPanel_id',
	title : querying.integrativeQuery.i18n('foss.querying.otherExpenses'),  //其他费用
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	// margin : '10 5 10 10',
	//frame : true,
	//height : 390,
	layout : 'column',
	constructor : function(config) {
		var me = this;
		me.items = [
				Ext.create('Foss.integrativeQueryIndex.LaborCostsPanel'),
				Ext.create('Foss.integrativeQueryIndex.ServiceRecoveryPanel'),
				Ext.create('Foss.integrativeQueryIndex.RefundShippingPanel')
				];
		me.callParent([config]);
	}
});

// 定义一个财务情况panel   ISSUE-3109
Ext.define('Foss.integrativeQueryIndex.FinancialTabPanel', {
	extend : 'Ext.panel.Panel',
	id : 'Foss_integrativeQueryIndex_FinancialTabPanel_id',
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	//frame : true,
	height :500,
	layout : 'column',
	constructor : function(config) {
		var me = this;
		me.items = [
		        Ext.create('Foss.integrativeQueryIndex.ReceivedPayPanel'),    //始发费用
				Ext.create('Foss.integrativeQueryIndex.ArrivePayPanel'),		//到付费用
				Ext.create('Foss.integrativeQueryIndex.PaymentCollectionPanel'), //代收货款
				Ext.create('Foss.integrativeQueryIndex.OtherExpensesTabPanel'), //其他费用
				Ext.create('Foss.integrativeQueryIndex.InvoicePanel')];  //发票费用
		me.callParent([config]);
	}
});

// 签收单model
Ext.define('Foss.integrativeQueryIndex.Model.SignWaybillModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 运单号
						name : 'waybillNo',
						type : 'string'
					}, {
						// 返单状态
						name : 'returnbillStatus',
						type : 'string'
					}, {
						// 返单type
						name : 'returnbillType',
						type : 'string'
					}, {
						// 返单时间
						name : 'returnbillTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 处理人
						name : 'handler',
						type : 'string'
					}, {
						// 确认时间
						name : 'verifyTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 反馈信息
						name : 'feedbackInfo',
						type : 'string'
					}, {
						// 快递号
						name : 'expressNo',
						type : 'string'
					}, {
						// 快递公司
						name : 'expressCompany',
						type : 'string'
					}, {
						// 创建人名字
						name : 'createUserName',
						type : 'string'
					}, {
						// 创建人编码
						name : 'createUserCode',
						type : 'string'
					}, {
						// 创建组织名字
						name : 'createOrgName',
						type : 'string'
					}, {
						// 创建组织编码
						name : 'createOrgCode',
						type : 'string'
					}, {
						// 创建时间
						name : 'createTime',
						type:'date',convert: dateConvert,defaultValue:null
					}]
		})

// 运单理赔信息Model
Ext.define('Foss.integrativeQueryIndex.Model.WaybillClaimbillModel', {
			extend : 'Ext.data.Model',
			fields : [{
						// 运单号
						name : 'waybillNum',
						type : 'string'
					}, {
						// 客户编码
						name : 'custNum',
						type : 'string'
					}, {
						// 客户名称
						name : 'custName',
						type : 'string'
					}, {
						// 出险类型
						name : 'riskType',
						type : 'string'
					}, {
						// 出险时间
						name : 'riskTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 报案时间
						name : 'reportCaseTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 索赔金额
						name : 'claimSum',
						type : 'string'
					}, {
						// 实际理赔金额
						name : 'actualPaySum',
						type : 'string'
					}, {
						// 收货部门名称
						name : 'receivingDeptName',
						type : 'string'
					}, {
						// 出险信息
						name : 'riskInfo',
						type : 'string'
					}, {
						// 处理状态
						name : 'status',
						type : 'string'
					}, {
						//处理人
						name : 'dealingPeople',
						type : 'string'
					}, {
						// 理赔专员处理时间
						name : 'dealingTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, {
						// 最终审批人（OA审批）
						name : 'finalApproval',
						type : 'string'
					}, {
						// 最终审批意见（OA中审批）
						name : 'finalApprovalOpinion',
						type:'string'
					}, {
						// 入部门费用
						name : 'indeptCharge',
						type : 'string'
					}, {
						// 入公司费用
						name : 'inCompanyCharge',
						type : 'string'
					}, {
						//其他费用
						name : 'otherCharge',
						type:'string'
					},{
						//出险原因
						name : 'dangerCause',
						type:'string'
					},{
						//经手是否有责任
						name : 'passIsDuty',
						type:'string'
					},{
						//责任部门名称
						name : 'responsibilityOrgName',
						type:'string'
					},{
						//责任部门编码
						name : 'responsibilityOrgCode',
						type:'string'
					}]
		})

//   签收单
Ext.define('Foss.integrativeQueryIndex.SignWaybillTabPanel', {
			extend : 'Ext.form.Panel',
			id : 'Foss_integrativeQueryIndex_SignWaybillTabPanel_id',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			// margin : '10 5 10 10',
			frame : true,
			heigth : 390,
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				readOnly : true
			},
			items : [FossDataDictionary.getDataDictionaryCombo('PKP_RETURNBILL_STATUS',{
						name : 'returnbillStatus',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.backSingleState'),  //返单状态
						readOnly:true
					}),FossDataDictionary.getDataDictionaryCombo('RETURNBILLTYPE',{
						name : 'returnbillType',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.backSingleType'),  //返单类型
						readOnly:true
					}), {
						name : 'verifyTime',
						xtype:'datefield',
						format: 'Y/m/d',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.verificationTime')  //核实时间
					}, {
						name : 'handler',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.handlePeople')  //处理人
					}, {
						name : 'expressCompany',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.outCourierCompanies')  //外发快递公司
					}, {
						name : 'expressNo',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.expressSingleNumber')  //快递单号
					}, {
						name : 'feedbackInfo',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.feedback')  //反馈信息
					}],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			}
		});

// 标签打印记录Model
Ext.define('Foss.integrativeQueryIndex.Model.TrackRecordModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'serialNo',
						type : 'string'
					}, // 流水号
					{
						name : 'originalSerialNo',
						type : 'string'
					}, // 关联流水号
					{
						name : 'printTime',
						type:'date',convert: dateConvert,defaultValue:null
					}, // 时间
					{
						name : 'printUserName',
						type : 'string'
					}, // 打印人
					{
						name : 'printUserOrgName',
						type : 'string'
					}, // 所属部门
					{ // 打印次序
						name : 'printOrder'
					}]
		})

// 跟踪记录store
Ext.define('Foss.integrativeQueryIndex.TrackRecordStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.querying.integrativeQuery.TrackRecordEntity'
		});
//定义一个特殊增值服务信息列表
Ext.define('Foss.integrativeQueryIndex.SpecialValueAddedServiceTabPanel',{
			extend:'Ext.grid.Panel',
			id:'Foss_integrativeQueryIndex_SpecialValueAddedServiceTabPanel_id',
			columnLines:true,
			border:true,
			height:360,
			stripeRows : true,
			cls : 'panelContentNToolbar',
			bodyCls :'panelContentNToolbar-body', 
			/*pagingToolbar : null,
			getPagingToolbar : function() {
				if (this.pagingToolbar == null) {
					this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20,
						hidden:true
					});
				}
				return this.pagingToolbar;
			},*/
			/**
			 * 显示传入时间（家装模块接收到供应商信息的时间）、
			 * 供应商订单号、提货人姓名、供应商名称、供应商联系电话、安装师傅提货时间、
			 * 供应商与客户预约的安装时间、是否有效、**/
			columns : [{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.acceptInTime'),//传入时间
				// 关联model中的字段名
				dataIndex : 'acceptTime',
				width : 100,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.providerOrderNo'),//供应商订单号
				// 关联model中的字段名
				dataIndex : 'providerOrderNo',
				width : 100
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.deliveryName'),//提货人姓名
				// 关联model中的字段名
				dataIndex : 'deliveryName',
				width : 100
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.providerName'),//供应商名称
				// 关联model中的字段名
				dataIndex : 'providerName',
				width : 100
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.providerPhone'),//供应商联系电话
				// 关联model中的字段名
				dataIndex : 'providerPhone',
				width : 150
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.pickUpTime'),//安装师傅提货时间
				// 关联model中的字段名
				dataIndex : 'pickUpTime',
				width : 150,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			
			},{
				//xtype: 'linebreakcolumn',
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.installTime'),//供应商与客户预约的安装时间
				// 关联model中的字段名
				dataIndex : 'installTime',
				width : 190,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
					}
					return v;
				}
			
			},{
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.valid'),//是否有效
				// 关联model中的字段名
				dataIndex : 'valid',
				width : 100,
				renderer:function(v){
					if(!Ext.isEmpty(v)){
						if(v==1)
							return '是';
					}
					return '否';
				}
			
			},{
				xtype : 'ellipsiscolumn',
				// 字段标题
				header :querying.integrativeQuery.i18n('foss.querying.remark'),//备注
				// 关联model中的字段名
				dataIndex : 'remark',
				width : 100
			
			}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.integrativeQueryIndex.SpecialValueAddedServiceStore');
				me.callParent([cfg]);
			}
});
// 定义一个标签打印记录信息列表
Ext.define('Foss.integrativeQueryIndex.LabelPrintingTabPanel', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_LabelPrintingTabPanel_id',
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : true,
			stripeRows : true,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			heigth : 370,
			// margin:'5 10 5 10',
			cls : "panelContentNToolbar",
			// bodyCls : 'panelContentNToolbar-body',
			// 定义表格列信息
			columns : [{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.serialNumber'),  //流水号
						// 关联model中的字段名
						dataIndex : 'serialNo',
						width : 65
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.associatedSerialNumber'),  //关联流水号
						// 关联model中的字段名
						dataIndex : 'originalSerialNo',
						width : 120
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.time'),  //时间
						// 关联model中的字段名
						dataIndex : 'printTime',
						width : 135,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.printPeople'),  //打印人
						// 关联model中的字段名
						dataIndex : 'printUserName',
						width : 100
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.department'),  //所属部门
						// 关联model中的字段名
						dataIndex : 'printUserOrgName',
						flex:1,
						width : 100
					}
					, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.lableType'),  //标签类型
						// 关联model中的字段名
						dataIndex : 'lableType',
						flex:1,
						renderer:function(lableType){
							if(!Ext.isEmpty(lableType)){
								if(lableType=="1"){
									return "货物标签";
								}else if (lableType=="2"){
									return "打木包装标签";
								}else{
									return "货物标签";
								}
							}else{
								return "货物标签";
							}
							
						}
					}
//					, {
//						// 字段标题
//						header : querying.integrativeQuery.i18n('foss.querying.printOrder'),  //打印次序
//						// 关联model中的字段名
//						dataIndex : 'printOrder',
//						width : 60
//					}
					],
					getFbar : function() {
						return [{
							type : 'button',
							text : querying.integrativeQuery.i18n('foss.querying.export'),  //导出
							handler : function() {
								if(!Ext.fly('downloadAttachFileForm')){
								    var frm = document.createElement('form');
								    frm.id = 'downloadAttachFileForm';
								    frm.style.display = 'none';
								    document.body.appendChild(frm);
								}
								var result = Ext.getCmp('Foss_integrativeQueryIndex_LabelPrintingTabPanel_id').store;
								//若异常信息不为空
								if(result.getCount()!=0){
									var params = {'waybillVo.condition.waybillNo' : querying.integrativeQuery.waybillNo};
									Ext.Ajax.request({
										url:querying.realPath('queryLablePrinting.action'),
										form: Ext.fly('downloadAttachFileForm'),
										method : 'POST',
										params : params,
										isUpload: true
									});
								}else{
									//top.Ext.MessageBox.alert(querying.integrativeQuery.i18n('foss.querying.exportFail'));  //导出失败
									Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.exportFail'));  //FOSS提醒您
								}
							
							}
						}]
					},
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Ext.data.Store',{
					model : 'Foss.integrativeQueryIndex.GoodsLabelPrintDtoModel'
				});
				me.tbar = me.getFbar();
				me.callParent([cfg]);
			}
		});

//定义一个代理信息列表
Ext.define('Foss.integrativeQueryIndex.AgentInformationTabPanel', {
			extend : 'Ext.grid.Panel',
			id : 'Foss_integrativeQueryIndex_AgentInformationTabPanel_id',
			// 增加表格列的分割线
			columnLines : true,
			// 表格对象增加一个边框
			frame : true,
			stripeRows : true,
			viewConfig:{
		    	enableTextSelection : true//设置行可以选择，进而可以复制    
			}, 
			heigth : 370,
			// margin:'5 10 5 10',
			cls : "panelContentNToolbar",
			// bodyCls : 'panelContentNToolbar-body',
			//得到bbar
			pagingToolbar : null,
			getPagingToolbar : function() {
				if (this.pagingToolbar == null) {
					this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
						store : this.store,
						pageSize : 20,
						hidden:true
					});
				}
				return this.pagingToolbar;
			},
			// 定义表格列信息
			columns : [{
							// 字段标题
							header : querying.integrativeQuery.i18n('foss.querying.serialNumber'),  //流水号
							// 关联model中的字段名
							dataIndex : 'serialNo',
							width : 100
						}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.AgentNumber'),  //代理单号
						// 关联model中的字段名
						dataIndex : 'traceContent',
						width : 150
					}, {
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.TraceTime'),  //跟踪时间
						// 关联model中的字段名
						dataIndex : 'operatTime',
						width : 150,
						renderer:function(v){
							if(!Ext.isEmpty(v)){
								return Ext.Date.format(new Date(v), 'Y-m-d H:i:s');
							}
							return v;
						}
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.TracePeople'),  //跟踪人
						// 关联model中的字段名
						dataIndex : 'operatorName',
						width : 120
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.orgName'),  //外发负责部门
						//关联model中的字段名
						dataIndex : 'orgName',
						width : 120             
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.depTelephone'),  //联系电话
						// 关联model中的字段名
						dataIndex : 'depTelephone',
						width : 120
					},{
						// 字段标题
						header : querying.integrativeQuery.i18n('foss.querying.Statuses'),  //状态
						// 关联model中的字段名
						dataIndex : 'status',
						width : 120
					}],
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext.create('Ext.data.Store',{
					model : 'Foss.integrativeQueryIndex.AgentInformationDtoModel',
					pageSize : 20,
					proxy : {
						type : 'ajax',
						actionMethods : 'post',
						url : querying.realPath('queryAgentInformation.action'),//请求地址
						reader : {
							type : 'json',
							root : 'waybillVo.queryAgentInformationResultDtos',//获取的数据
							totalProperty : 'totalCount'//总个数
						}
					},
					listeners: {
						beforeload: function(store, operation, eOpts){
								Ext.apply(operation,{
									params : {
										'waybillVo.condition.waybillNo':querying.integrativeQuery.waybillNo
									}
								});	
						}
				    }
				});
				me.bbar = me.getPagingToolbar();
				me.callParent([cfg]);
			}
		});


Ext.define('Foss.integrativeQueryIndex.RecompenseTabPanel', {        //保险理赔
			extend : 'Ext.form.Panel',
			id : 'Foss_integrativeQueryIndex_RecompenseTabPanel_id',
			cls : "panelContentNToolbar",
			bodyCls : 'panelContentNToolbar-body',
			// margin : '10 5 10 10',
			frame : true,
			heigth : 390,
			layout : 'column',
			defaults : {
				xtype : 'textfield',
				columnWidth: .3,
				readOnly : true
			},
			items : [{
						name : 'custName',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.customerName')  //客户名称
					},{
						name : 'waybillNum',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.waybillNumber')  //运单号
					},{
						name : 'riskType',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.chuXianType')  //出险类型
					},{
						name : 'riskTime',
						xtype:'datefield',
						format: 'Y/m/d',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.chuXianDate')  //出险日期
					},{
						name : 'reportCaseTime',
						xtype:'datefield',
						format: 'Y/m/d',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.reportDate')  //报案日期
					},{
						name : 'claimSum',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.claimAmount')  //索赔金额
					}, {
						name : 'actualPaySum',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.actualClaimAmount')  //实际理赔金额
					}, {
						name : 'receivingDeptName',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.receivingDepartmentName')  //收货部门名称
					}, {
						name : 'riskInfo',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.chuXianInformation'),  //出险信息
						columnWidth:1
					}, {
						name : 'indeptCharge',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.enterDepartmentCost'),  //入部门费用
						columnWidth:1
					} ,{
						name : 'status',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.handleType')  //处理状态
					},{
						name : 'otherCharge',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.otherExpenses')  //其他费用
					}, {
						name : 'inCompanyCharge',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.enterCompanyCost')  //入公司费用
					}, {
						name : 'dealingPeople',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.handlePeople')  //处理人
					}, {
						name : 'dealingTime',
						xtype:'datefield',
						format: 'Y/m/d H:i:s',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.handleTime')  //处理时间
					}, {
						name : 'finalApproval',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.approvalMan')  //审批人
					}, {
						name : 'finalApprovalOpinion',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.approvalOpinion'),  //审批意见
						columnWidth:1
					},{
						name:'responsibilityOrgName',
						fieldLabel :querying.integrativeQuery.i18n('foss.querying.responsibilityOrg')//责任部门  
					},{
						name:'passIsDuty',
						fieldLabel :querying.integrativeQuery.i18n('foss.querying.passIsDuty'),//经手是否有责任
						columnWidth:.6,
						listeners:{
							'change':function(_this,newV,oldV,eOpts){
								if(newV){
									if(newV=='Y'){
										_this.setValue('是');
									}else if(newV =='N'){
										_this.setValue('否');
									}
								}else{
									if(oldV=='Y'){
										_this.setValue('是');
									}else if(oldV =='N'){
										_this.setValue('否');
									}
								}
							}
						}
					},{
						name:'dangerCause',
						fieldLabel : querying.integrativeQuery.i18n('foss.querying.dangerCause'),//出险原因
						columnWidth:1
					}],
			constructor : function(config) {
				var me = this;
				me.callParent([config]);
			} 
		});

//  @author 132599-foss-shenweihua   @date 2013-04-15
/**
 * 他人收件人 form Model
 */
Ext.define('Foss.integrativeQueryIndex.Model.HandleOthersInformationModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'receiveCustomerContact',
						type : 'string'
					}, // 真实收货人
					{
						name : 'receiveCustomerPhone',
						type : 'string'
					} // 真实收货人电话
					]
		})


/**
 * 他人收件信息FORM
 */
Ext.define('Foss.integrativeQueryIndex.RealConsigneeForm', {
	extend : 'Ext.form.Panel',
	frame : true,
	collapsible : true,
	animCollapse : true,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	layout : {
		type : 'column'
	},
	bodyPadding : 10,
	title : querying.integrativeQuery.i18n('foss.querying.HandleOthersInformation'),
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 80
	},
	fileUploadResultGrid : null,
	getFileUploadResultGrid : function() {
		if (this.fileUploadResultGrid == null) {
			this.fileUploadResultGrid = Ext.create('Foss.integrativeQueryIndex.FileUploadResultGrid',
			{
				columnWidth : 1,
				autoScroll : true
			});
		}
		return this.fileUploadResultGrid;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'textfield',
				readOnly : true,
				columnWidth : .5,// 默认长度 一行可以显示4个
				fieldLabel :querying.integrativeQuery.i18n('foss.querying.receiveCustomerContact'),// 真是收获人
				name : 'receiveCustomerContact' // 关联的名称
			}, {
				xtype : 'textfield',
				readOnly : true,
				columnWidth : .5,// 默认长度 一行可以显示4个
				fieldLabel : querying.integrativeQuery.i18n('foss.querying.consigneePhone'),// 收获人电话
				name : 'receiveCustomerPhone'// 关联的名称
			}, {
				border : 1,
				xtype : 'container',
				columnWidth : 1,
				defaultType : 'button',
				layout : 'column',
				items : [
					me.getFileUploadResultGrid()]
			}]
		});
		me.callParent(arguments);
	}
});

/**
 * 附件信息  
 */
Ext.define('Foss.integrativeQueryIndex.FileUploadResultGrid', {
	extend : 'Deppon.ux.FileUploadGrid',
	reviewFlag : true,
	title : querying.integrativeQuery.i18n('foss.querying.downloadViewCertificateAttachments'),//下载查看凭证附件
	downLoadUrl : ContextPath.PKP_DELIVER + '/sign/downLoadFiles.action',
	imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
});

/**
 * @author 132599-foss-shenweihua   @date 2013-04-15
 * 处理他人收件人tabPanel
 */
Ext.define('Foss.integrativeQueryIndex.HandleOthersInformationTabPanel', {
	extend : 'Ext.panel.Panel',
	cls : "panelContentNToolbar",
	bodyCls : 'panelContentNToolbar-body',
	id:'Foss.integrativeQueryIndex.HandleOthersInformationTabPanel_ID',
	height : 390,
	frame : false,
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	//他人收件信息FORM
	realConsigneeForm:null,
    getRealConsigneeForm : function(){
    	if(Ext.isEmpty(this.realConsigneeForm)){
    		this.realConsigneeForm = Ext.create('Foss.integrativeQueryIndex.RealConsigneeForm');
    	}
    	return this.realConsigneeForm;
    },
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		me.items = [ me.getRealConsigneeForm()];
		me.callParent([cfg]);
	}
});

// 运单相关信息tab
Ext.define('Foss.integrativeQueryIndex.DetailTabPanel', {
	extend : 'Ext.tab.Panel',
	flex : 1,
	cls : 'innerTabPanel',
	width:'auto',
	columnWidth : .99,
	id:'Foss.integrativeQueryIndex.DetailTabPanel_Id',
	title : querying.integrativeQuery.i18n('foss.querying.vaybillInfo'),  //运单相关信息
	activeTab : 0,// 默认激活第一个Tab页
	autoScroll : false,
	//下拉菜单插件
	/**plugins: [{
		ptype: 'tabscrollermenu',
		id:'OFB_menu',
		maxText  : 40,
		pageSize : 100
	}],**/
	//tab 切换事件
	listeners:{
	      tabchange:function(t,p){//其中参数p指的是当前活动的panel
	      	var waybillNo = Ext.getCmp('Foss_integrativeQueryIndex_Form_WaybillNOFullForm_Id').
	      		getForm().findField('waybillNo').value;
	      	if(!Ext.isEmpty(waybillNo)){
		        var params = {'waybillVo' : {'condition' : {
		        	'waybillNo' : querying.integrativeQuery.waybillNo
				}}};
	      		if(p.title == querying.integrativeQuery.i18n('foss.querying.trackRecord')){  //跟踪记录
	              	//加载数据
	      			var url = querying.realPath('queryWayBilllTrackRecords.action');
				    var successFn = function(json) {
							/*if (Ext.isEmpty(json.waybillVo.trackRecordList)) {
								return;
							}*/
							// 跟踪记录
							Ext.getCmp('Foss_integrativeQueryIndex_TrackRecordGrid_id').store.
							loadData(Ext.isEmpty(json.waybillVo.trackRecordList)?[]:json.waybillVo.trackRecordList);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.waybillChange')){  //运单更改
	            	//加载数据
	      			var url = querying.realPath('queryWayBilllChangeByWaybillNo.action');
				    var successFn = function(json) {
				    		//出发更改
							Ext.getCmp('Foss_integrativeQueryIndex_StartChangeGrid_id').getStore()
								.loadData(Ext.isEmpty(json.waybillVo.startChangeSignRfcDtos)?[]:json.waybillVo.startChangeSignRfcDtos);
							//到达更改
							Ext.getCmp('Foss_integrativeQueryIndex_ArriveChangeGrid_id').getStore()
								.loadData(Ext.isEmpty(json.waybillVo.arriveChangeSignRfcDtos)?[]:json.waybillVo.arriveChangeSignRfcDtos);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.deliverySituation')){  //派送情况
	            	//加载数据
	      			var url = querying.realPath('queryDeliverySituationByWaybillNo.action');
				    var successFn = function(json) {
				    		
				    		//通知情况
							Ext.getCmp('Foss_integrativeQueryIndex_NoticeGrid_id').getStore()
								.loadData(Ext.isEmpty(json.waybillVo.deliverySituationDto.notificationList)?[]:json.waybillVo.deliverySituationDto.notificationList);
							//派送情况
							Ext.getCmp('Foss_integrativeQueryIndex_DeliverySituationGrid_id').getStore()
								.loadData(Ext.isEmpty(json.waybillVo.deliverySituationDto.deliverbilldtoList)?[]:json.waybillVo.deliverySituationDto.deliverbilldtoList);
							//退单情况数据绑定
							Ext.getCmp('Foss_integrativeQueryIndex_ReturnIssuingGrid_id').getStore()
							.loadData(Ext.isEmpty(json.waybillVo.deliverySituationDto.visibleHandBillReturnList)?[]:json.waybillVo.deliverySituationDto.visibleHandBillReturnList);
							//交单情况
							Ext.getCmp('Foss_integrativeQueryIndex_IssuingGrid_id').getStore()
							.loadData(Ext.isEmpty(json.waybillVo.deliverySituationDto.deliverHandoverbillList)?[]:json.waybillVo.deliverySituationDto.deliverHandoverbillList);
							//只有查询单号运输性质为标准快递或3.60特惠件时才显示
							if(!Ext.isEmpty(json.waybillVo.deliverySituationDto.signSituationList)){
								var productCode=json.waybillVo.deliverySituationDto.signSituationList[0].productCode;
								if(!Ext.isEmpty(productCode)&&(productCode=='PACKAGE'||productCode=='RCP' ||productCode=='EPEP'||productCode=='DEAP')){
									//运输性质为快递时，显示签收情况中的签收人类型一栏 
									Ext.getCmp('Foss_integrativeQueryIndex_SignCaseGrid_id').columns[2].show();
									//运输性质为快递时，隐藏签收情况中的签收人一栏 DN201511090005
									Ext.getCmp('Foss_integrativeQueryIndex_SignCaseGrid_id').columns[1].hide();
								}else{
									//运输性质为零担时，隐藏签收情况中的签收人类型一栏 
									Ext.getCmp('Foss_integrativeQueryIndex_SignCaseGrid_id').columns[2].hide();
									//运输性质为零担时，显示签收情况中的签收人一栏 DN201511090005
									Ext.getCmp('Foss_integrativeQueryIndex_SignCaseGrid_id').columns[1].show();
								}
							}
							//签收情况
							Ext.getCmp('Foss_integrativeQueryIndex_SignCaseGrid_id').getStore()
								.loadData(Ext.isEmpty(json.waybillVo.deliverySituationDto.signSituationList)?[]:json.waybillVo.deliverySituationDto.signSituationList);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.financialSitation')){  //财务情况
	            	//加载数据
	      			var url = querying.realPath('queryFinanceConditionByWayBillNo.action');
	      			var  params_condtion = {'waybillVo':
						      					{   'wayBillNo':querying.integrativeQuery.waybillNo,
						      						'leaveDeptCode':querying.integrativeQuery.leaveDeptCode,
						      						'arriveDeptCode':querying.integrativeQuery.arriveDeptCode
						      					}
	      									};
				    var successFn = function(json) {
				    	// 财务情况  始发运费
						var financialInfoModel = new Foss.integrativeQueryIndex.Model.FinancialInfoModel(Ext.isEmpty(json.waybillVo.waybillFinanceInfoDto.origFeeInfo)?{}:json.waybillVo.waybillFinanceInfoDto.origFeeInfo);
						var forms = Ext.getCmp('Foss_integrativeQueryIndex_FinancialTabPanel_id').query('form');
						// 到付费用
						if(!Ext.isEmpty(json.waybillVo.waybillFinanceInfoDto.destFeeInfo)){
							var paymentTypes = json.waybillVo.waybillFinanceInfoDto.destFeeInfo.paymentTypes;
						}
						var paymentType=[];
						if(!Ext.isEmpty(paymentTypes)){
							for(var i=0;i<paymentTypes.length;i++){
							paymentType.push(FossDataDictionary.rendererSubmitToDisplay(paymentTypes[i],'SETTLEMENT__PAYMENT_TYPE'));
							}
						}
						var ReachPayInfoModel = new Foss.integrativeQueryIndex.Model.FinancialInfoModel(Ext.isEmpty(json.waybillVo.waybillFinanceInfoDto.destFeeInfo)?{}:json.waybillVo.waybillFinanceInfoDto.destFeeInfo);
						var wayBillVo = Ext.getCmp('T_querying-integrativeQueryIndex_content').getBodyPanel().getWayBillInfoCache().get(querying.integrativeQuery.waybillNo);
						if(!Ext.isEmpty(wayBillVo)){
							ReachPayInfoModel.set('originalWaybillNoTotalFee',wayBillVo.waybillInfoByWaybillNoReultDto.originalWaybillNoTotalFee);
							ReachPayInfoModel.set('oriAndReturntotalFee',wayBillVo.waybillInfoByWaybillNoReultDto.oriAndReturntotalFee);
							if(querying.integrativeQuery.waybillNo===wayBillVo.waybillInfoByWaybillNoReultDto.returnWaybillNo){
								forms[1].getForm().findField('originalWaybillNoTotalFee').show();
								forms[1].getForm().findField('oriAndReturntotalFee').show();
							}else{
								forms[1].getForm().findField('originalWaybillNoTotalFee').hide();
								forms[1].getForm().findField('oriAndReturntotalFee').hide();
							}
						}
						ReachPayInfoModel.set('paymentTypes',paymentType);
						// 代收货款
						if(!Ext.isEmpty(json.waybillVo.waybillFinanceInfoDto.codFeeInfo)){
							var paymentypes = json.waybillVo.waybillFinanceInfoDto.codFeeInfo.paymentTypes;
						}	
						var paymentype=[];
						if(!Ext.isEmpty(paymentypes)){
							for(var i=0;i<paymentypes.length;i++){
								paymentype.push(FossDataDictionary.rendererSubmitToDisplay(paymentypes[i],'SETTLEMENT__PAYMENT_TYPE'));
							}
						}
						var PaymentCollectionInfoModel = new Foss.integrativeQueryIndex.Model.paymentCollectionModel(Ext.isEmpty(json.waybillVo.waybillFinanceInfoDto.codFeeInfo)?{}:json.waybillVo.waybillFinanceInfoDto.codFeeInfo);
						PaymentCollectionInfoModel.set('paymentTypes',paymentype);
						
						//其他费用-装卸费
						var handlingChargesInfoModel = new Foss.integrativeQueryIndex.Model.OtherExpensesModel(Ext.isEmpty(json.waybillVo.handlingCharges)?{}:json.waybillVo.handlingCharges);
						//其他费用-服务补救
						var serviceRemedyInfoModel = new Foss.integrativeQueryIndex.Model.OtherExpensesModel(Ext.isEmpty(json.waybillVo.serviceRemedy)?{}:json.waybillVo.serviceRemedy);
						//其他费用-退运费
						var returnShippingInfoModel = new Foss.integrativeQueryIndex.Model.OtherExpensesModel(Ext.isEmpty(json.waybillVo.returnShipping)?{}:json.waybillVo.returnShipping);
						//发票
						var InvoiceInfoModel = new Foss.integrativeQueryIndex.Model.InvoiceModel(Ext.isEmpty(json.waybillVo.invoice)?{}:json.waybillVo.invoice);
						forms[0].getForm().loadRecord(financialInfoModel);
						forms[1].getForm().loadRecord(ReachPayInfoModel);
						forms[2].getForm().loadRecord(PaymentCollectionInfoModel);
						forms[3].getForm().loadRecord(handlingChargesInfoModel);
						forms[4].getForm().loadRecord(serviceRemedyInfoModel);
						forms[5].getForm().loadRecord(returnShippingInfoModel);
						forms[6].getForm().loadRecord(InvoiceInfoModel);
					}
					querying.requestJsonAjax(url, params_condtion, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.receiptOf')){  //签收单
	            	//加载数据
	      			var url = querying.realPath('querySignedBillByWaybillNo.action');
				    var successFn = function(json) {
				    	/*if (Ext.isEmpty(json.waybillVo.billProcessEntities)) {
							return;
						}*/
				    	var sinWaybilModel = new Foss.integrativeQueryIndex.Model.SignWaybillModel(Ext.isEmpty(json.waybillVo.billProcessEntities)?{}:json.waybillVo.billProcessEntities[0]);
						Ext.getCmp('Foss_integrativeQueryIndex_SignWaybillTabPanel_id').getForm().loadRecord(sinWaybilModel);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.insuranceClaims')){  //保险理赔
	            	//加载数据
	      			var url = querying.realPath('queryClaimbillByWaybillNo.action');
				    var successFn = function(json) {
				    	/*if (Ext.isEmpty(json.waybillVo.queryClaimbillResultDto)) {
							return;
						}*/
				    	var waybilClaimbillModel = new Foss.integrativeQueryIndex.Model.WaybillClaimbillModel(Ext.isEmpty(json.waybillVo.queryClaimbillResultDto)?{}:json.waybillVo.queryClaimbillResultDto);
				    	Ext.getCmp('Foss_integrativeQueryIndex_RecompenseTabPanel_id').getForm().loadRecord(waybilClaimbillModel);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.labelPrintRecord')){  //标签打印记录
	            	//加载数据
	      			var url = querying.realPath('queryLabelPrintByWaybillNo.action');
				    var successFn = function(json) {
				    	/*if (Ext.isEmpty(json.waybillVo.goodsLabelPrintDtos)) {
							return;
						}*/
				    	// 打印标签记录
				    	Ext.getCmp('Foss_integrativeQueryIndex_LabelPrintingTabPanel_id').getStore()
							.loadData(Ext.isEmpty(json.waybillVo.goodsLabelPrintDtos)?[]:json.waybillVo.goodsLabelPrintDtos);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.HandleOthersInformation')){  //处理他人收件人信息
	            	//加载数据
	      			var url = querying.realPath('querySignByOtherDto.action');
				    var successFn = function(json) {
				    	//创建他人收件人信息
						var formModel = new Foss.integrativeQueryIndex.Model.HandleOthersInformationModel(json.waybillVo.dto);
						//获取收件人FORM
						var otherForm = Ext.getCmp('Foss.integrativeQueryIndex.HandleOthersInformationTabPanel_ID').getRealConsigneeForm();
						//加载form数据
						otherForm.loadRecord(formModel);
						
						//加载grid数据
						var otherGrid = Ext.getCmp('Foss.integrativeQueryIndex.HandleOthersInformationTabPanel_ID').getRealConsigneeForm().fileUploadResultGrid;
						if (!Ext.isEmpty(json.waybillVo.dto)) {
							otherGrid.getStore().loadData(json.waybillVo.dto.files);
						}
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	            }else if(p.title == querying.integrativeQuery.i18n('foss.querying.AgentInformation')){  //代理信息
	            	//加载数据
	      			/*var url = querying.realPath('queryAgentInformation.action');
				    var successFn = function(json) {
				    	// 打印标签记录
				    	Ext.getCmp('Foss_integrativeQueryIndex_AgentInformationTabPanel_id').getStore()
							.loadData(Ext.isEmpty(json.waybillVo.queryAgentInformationResultDtos)?[]:json.waybillVo.queryAgentInformationResultDtos);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});*/
	            	//重新加载
					Ext.getCmp('Foss.integrativeQueryIndex.DetailTabPanel_Id').getAgentInformationTabPanel().getPagingToolbar().moveFirst();
	            }else if(p.title==querying.integrativeQuery.i18n('foss.querying.specialValueAddedService')){  //特殊增值服务信息
	            	//加载数据
	      			var url = querying.realPath('searchSpecialValueAddedServiceInfoByWaybillNo.action');
				    var successFn = function(json) {
				    	Ext.getCmp('Foss_integrativeQueryIndex_SpecialValueAddedServiceTabPanel_id').getStore()
							.loadData(Ext.isEmpty(json.waybillVo.deliveryInformationDtos)?[]:json.waybillVo.deliveryInformationDtos);
					}
					querying.requestJsonAjax(url, params, successFn, function(json) {
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),json.message);  //FOSS提醒您
					});
	          //	Ext.getCmp('Foss.integrativeQueryIndex.DetailTabPanel_Id').getSpecialValueAddedServiceTabPanel().getPagingToolbar().moveFirst();

	          }  
	      	}
	      }
	},
	//特殊增值服务
	specialValueAddedServiceTabPanel:null,
	getSpecialValueAddedServiceTabPanel:function(){
	    if(Ext.isEmpty(this.specialValueAddedServiceTabPanel)){
	        this.specialValueAddedServiceTabPanel = Ext.create('Foss.integrativeQueryIndex.SpecialValueAddedServiceTabPanel');
	    }
	return this.specialValueAddedServiceTabPanel;
	},
	cargoTrackTabPanel: null,
	getCargoTrackTabPanel : function() {
		if(Ext.isEmpty(this.cargoTrackTabPanel)){
			this.cargoTrackTabPanel = Ext.create('Foss.integrativeQueryIndex.CargoTrackTabPanel');
		}
		return this.cargoTrackTabPanel;
	},
	returnHandleTabPanel:null,
	getReturnHandleTabPanel:function(){
	  if(Ext.isEmpty(this.returnHandleTabPanel)){
	  
	     this.returnHandleTabPanel= Ext.create('Foss.integrativeQueryIndex.ReturnHandleTabPanel');
	  }
	   return this.returnHandleTabPanel
	},
	
	trackRecordTabPanel: null,
	getTrackRecordTabPanel : function() {
		if(Ext.isEmpty(this.trackRecordTabPanel)){
			this.trackRecordTabPanel = Ext.create('Foss.integrativeQueryIndex.TrackRecordTabPanel');
		}
		return this.trackRecordTabPanel;
	},
	changeWaybillTabPanel: null,
	getChangeWaybillTabPanel : function() {
		if(Ext.isEmpty(this.changeWaybillTabPanel)){
			this.changeWaybillTabPanel = Ext.create('Foss.integrativeQueryIndex.ChangeWaybillTabPanel');
		}
		return this.changeWaybillTabPanel;
	},
	financialTabPanel: null,
	getFinancialTabPanel : function() {
		if(Ext.isEmpty(this.financialTabPanel)){
			this.financialTabPanel = Ext.create('Foss.integrativeQueryIndex.FinancialTabPanel');
		}
		return this.financialTabPanel;
	},
	signWaybillTabPanel: null,
	getSignWaybillTabPanel : function() {
		if(Ext.isEmpty(this.signWaybillTabPanel)){
			this.signWaybillTabPanel = Ext.create('Foss.integrativeQueryIndex.SignWaybillTabPanel');
		}
		return this.signWaybillTabPanel;
	},
	recompenseTabPanel:null,
	getRecompenseTabPanel : function() {
		if(Ext.isEmpty(this.recompenseTabPanel)){
			this.recompenseTabPanel = Ext.create('Foss.integrativeQueryIndex.RecompenseTabPanel');
		}
		return this.recompenseTabPanel;
	},
	labelPrintingTabPanel:null,
	getLabelPrintingTabPanel : function() {
		if(Ext.isEmpty(this.labelPrintingTabPanel)){
			this.labelPrintingTabPanel = Ext.create('Foss.integrativeQueryIndex.LabelPrintingTabPanel');
		}
		return this.labelPrintingTabPanel;
	},
	handleOthersInformationTabPanel:null,
	getHandleOthersInformationTabPanel : function() {  //@author 132599-foss-shenweihua   @date 2013-04-13
		if(Ext.isEmpty(this.handleOthersInformationTabPanel)){
			this.handleOthersInformationTabPanel = Ext.create('Foss.integrativeQueryIndex.HandleOthersInformationTabPanel');
		}
		return this.handleOthersInformationTabPanel;
	},
	agentInformationTabPanel:null,
	getAgentInformationTabPanel:function() {  
		if(Ext.isEmpty(this.agentInformationTabPanel)){
			this.agentInformationTabPanel = Ext.create('Foss.integrativeQueryIndex.AgentInformationTabPanel');
		}
		return this.agentInformationTabPanel;
	},
	deliverySituationTabPanel: null,
	getDeliverySituationTabPanel : function() {  //@author 132599-foss-shenweihua   @date 2013-07-04
		if(Ext.isEmpty(this.deliverySituationTabPanel)){
			this.deliverySituationTabPanel = Ext.create('Foss.integrativeQueryIndex.DeliverySituationTabPanel');
		}
		return this.deliverySituationTabPanel;
	},
	bindData: function(ladeledGoodList,wayBIllNoLocusList,ZMJRelateWaybillList){
		//默认激活第一个选项卡页
		var detailTabPanel = Ext.getCmp('Foss.integrativeQueryIndex.DetailTabPanel_Id');
		detailTabPanel.setActiveTab(0);
		//内部轨迹赋值
		this.getCargoTrackTabPanel().getInternalTrackingGrid().getStore().loadData(wayBIllNoLocusList);
		//按件查询的流水号列表赋值
		this.getCargoTrackTabPanel().getExternalGrid().getStore().loadData(ladeledGoodList);
		//子母件关联单赋值
		this.getCargoTrackTabPanel().getZMJLocusGrid().getStore().loadData(ZMJRelateWaybillList);
	},
	constructor : function(config) {
		Ext.apply(this, config);
		this.items = [{
			title : querying.integrativeQuery.i18n('foss.querying.cargoTrack'),  //货物轨迹
			tabConfig : {
				width : 100
			},
			items : this.getCargoTrackTabPanel()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.trackRecord'),  //跟踪记录
			tabConfig : {
				width : 100
			},
			items : this.getTrackRecordTabPanel()
		},{
			title :querying.integrativeQuery.i18n('foss.querying.specialValueAddedService'),  //特殊增值服务信息
			tabConfig : {
				width : 100
			},
			items : this.getSpecialValueAddedServiceTabPanel()
		},{
			title : querying.integrativeQuery.i18n('foss.querying.waybillChange'),  //运单更改
			tabConfig : {
				width : 100
			},
			items : this.getChangeWaybillTabPanel()
		}, { //@author 132599-foss-shenweihua   @date 2013-07-04
			title : querying.integrativeQuery.i18n('foss.querying.deliverySituation'),  //派送情况
			tabConfig : {
				width : 100
			},
			items : this.getDeliverySituationTabPanel()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.financialSitation'),  //财务情况
			tabConfig : {
				width : 100
			},
			width:'auto',
			autoScroll : true,
			items : this.getFinancialTabPanel()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.receiptOf'),  //签收单
			tabConfig : {
				width : 100
			},
			items : this.getSignWaybillTabPanel()
		}, {
		        	
		      title:'返货处理',
		      tabConfig:{
		      width:100
		           },
		       items : this.getReturnHandleTabPanel()
		        },
		{
			title : querying.integrativeQuery.i18n('foss.querying.insuranceClaims'),  //保险理赔
			tabConfig : {
				width : 100
			},
			items : this.getRecompenseTabPanel()
		}, {
			title : querying.integrativeQuery.i18n('foss.querying.labelPrintRecord'),  //标签打印记录
			tabConfig : {
				width : 100
			},
			items : this.getLabelPrintingTabPanel()
		},{ 
			title : querying.integrativeQuery.i18n('foss.querying.AgentInformation'),  //代理信息
			tabConfig : {
				width : 100
			},
			items : this.getAgentInformationTabPanel()
		},{ //@author 132599-foss-shenweihua   @date 2013-04-13
			title : querying.integrativeQuery.i18n('foss.querying.HandleOthersInformation'),  //处理他人收件信息
			tabConfig : {
				width : 100
			},
			items : this.getHandleOthersInformationTabPanel()
		}];
		this.callParent(arguments);
	}
});

// 右侧——运单详细信息的大panel
Ext.define('Foss.integrativeQueryIndex.WayBillFullInfoPanel', {
	extend : 'Ext.panel.Panel',
	flex : 1,
	frame : true,
	//单号********       简单信息   完整信息
	waybillNOFullForm: null,
	getWaybillNOFullForm: function(){
		if(Ext.isEmpty(this.waybillNOFullForm)){
			this.waybillNOFullForm = Ext.create('Foss.integrativeQueryIndex.Form.WaybillNOFullForm');
		}
		return this.waybillNOFullForm;
	},
	//简单信息（运单信息）
	simpleWayBillForm: null,
	getSimpleWayBillForm: function(){
		if(Ext.isEmpty(this.simpleWayBillForm)){
			this.simpleWayBillForm = Ext.create('Foss.integrativeQueryIndex.SimpleWayBillForm');
		}
		return this.simpleWayBillForm;
	},
	//完整信息（运输信息、发货客户信息、收货客户信息、货物信息、费用信息）
	completeWayBillInfoPanel: null,
	getCompleteWayBillInfoPanel: function(){
		if(Ext.isEmpty(this.completeWayBillInfoPanel)){
			this.completeWayBillInfoPanel = Ext.create('Foss.integrativeQueryIndex.CompleteWayBillInfoPanel');
		}
		return this.completeWayBillInfoPanel;
	},
	//状态信息
	statusInfoForm: null,
	getStatusInfoForm: function(){
		if(Ext.isEmpty(this.statusInfoForm)){
			this.statusInfoForm = Ext.create('Foss.integrativeQueryIndex.Form.StatusInfoForm');
		}
		return this.statusInfoForm;
	},
	//运单相关信息（Tab）
	detailTabPanel: null,
	getDetailTabPanel: function(){
		if(Ext.isEmpty(this.detailTabPanel)){
			this.detailTabPanel = Ext.create('Foss.integrativeQueryIndex.DetailTabPanel');
		}
		return this.detailTabPanel;
	},
	//通过运单详细信息，绑定各个模块信息
	bindData: function(wayBillVo){
		if(null !=wayBillVo.waybillInfoByWaybillNoReultDto){
			//（单号*** 简单信息 完整信息）的绑定数据
			this.getWaybillNOFullForm().bindData(wayBillVo.wayBillNo,wayBillVo.waybillInfoByWaybillNoReultDto.waybillStatus,wayBillVo.waybillInfoByWaybillNoReultDto);
			// 将运单状态信息赋值给常量
			querying.integrativeQuery.waybillStatus = wayBillVo.waybillInfoByWaybillNoReultDto.waybillStatus;
			//运单详细信息model，用于传给简单信息、完整信息的绑定数据方法
			var wayBillRecord = Ext.create('Foss.integrativeQueryIndex.Model.WaybillInfoModel',wayBillVo.waybillInfoByWaybillNoReultDto);
			//简单信息的绑定数据
			this.getSimpleWayBillForm().bindData(wayBillVo.leaveDeptCode,wayBillVo.arriveDeptCode,wayBillRecord,wayBillVo.wayBillNo,wayBillVo.twoInOneWaybillDto.isTwoInOne);
			querying.integrativeQuery.leaveDeptCode = wayBillVo.leaveDeptCode;
			querying.integrativeQuery.arriveDeptCode = wayBillVo.arriveDeptCode;
			//完整信息的绑定数据
			this.getCompleteWayBillInfoPanel().bindData(wayBillVo.leaveDeptCode,wayBillVo.arriveDeptCode,wayBillRecord);
		}
		
		// 状态信息
		var billOtherRecord = Ext.create('Foss.integrativeQueryIndex.Model.CargoTrackingInfoModel',(Ext.isEmpty(wayBillVo.wayBillOtherForBseDto)?{}:wayBillVo.wayBillOtherForBseDto));
		var generalQueryDtos = Ext.isEmpty(wayBillVo.generalQueryDtos)?[]:wayBillVo.generalQueryDtos;
		this.getStatusInfoForm().bindData(billOtherRecord, generalQueryDtos);
		
		//货物轨迹（按件查询）
		var ladeledGoodList = Ext.isEmpty(wayBillVo.labeledGoodList)?[]:wayBillVo.labeledGoodList;
		//货物轨迹（内部轨迹）
		var wayBIllNoLocusList = Ext.isEmpty(wayBillVo.wayBIllNoLocusList)?[]:wayBillVo.wayBIllNoLocusList;
		//货物轨迹（子母件轨迹）
		var ZMJRelateWaybillList=[];
		if(!Ext.isEmpty(wayBillVo.twoInOneWaybillDto.mainWaybillNo)){
			ZMJRelateWaybillList.push({ZMJMark:'母件',waybillNo:wayBillVo.twoInOneWaybillDto.mainWaybillNo});
		}
		if(!Ext.isEmpty(wayBillVo.twoInOneWaybillDto.waybillNoList)){
			for(var i=0;i<wayBillVo.twoInOneWaybillDto.waybillNoList.length;i++){
				ZMJRelateWaybillList.push({ZMJMark:'子件',waybillNo:wayBillVo.twoInOneWaybillDto.waybillNoList[i]});
			}
		}
		this.getDetailTabPanel().bindData(ladeledGoodList,wayBIllNoLocusList,ZMJRelateWaybillList);
	},
	constructor : function(config) {
		var me = this;
		me.items = [
			me.getWaybillNOFullForm(),me.getCompleteWayBillInfoPanel(),
			me.getSimpleWayBillForm(),me.getStatusInfoForm(),
			me.getDetailTabPanel()
		];
		me.callParent([config])
	}
});

// 查询结果（左侧运单列表+右侧详细信息）
Ext.define('Foss.integrativeQueryIndex.BodyPanel', {
	extend: 'Ext.panel.Panel',
	layout: {
		type : 'hbox',
		align : 'stretch'
	},
	margin: '-21 0 0 0',
	cls: 'targetPanel.body.dom.scrollHeight-targetPanel.body.dom.offsetHeight',
	//运单缓存
	wayBillInfoCache: querying.integrativeQuery.map,
	getWayBillInfoCache: function(){
		return this.wayBillInfoCache;
	},
	//左侧——运单列表
	wayBillResultGrid: null,
	getWayBillResultGrid: function(){
		if(Ext.isEmpty(this.wayBillResultGrid)){
			this.wayBillResultGrid = Ext.create('Foss.integrativeQueryIndex.WayBillResultGrid');
		}
		return this.wayBillResultGrid;
	},
	//返货处理信息
	returnHandleTabPanel:null,
	getReturnHandleTabPanel:function(){
		if(Ext.isEmpty(this.returnHandleTabPanel)){
			this.returnHandleTabPanel = Ext.create('Foss.integrativeQueryIndex.ReturnHandleTabPanel');
		}
		return this.returnHandleTabPanel;

	},
	//右侧——运单详细信息的大panel
	wayBillFullInfoPanel: null,
	getWayBillFullInfoPanel: function(){
		if(Ext.isEmpty(this.wayBillFullInfoPanel)){
			this.wayBillFullInfoPanel = Ext.create('Foss.integrativeQueryIndex.WayBillFullInfoPanel');
		}
		return this.wayBillFullInfoPanel;
	},
	constructor : function(config) {
		var me = this;
		me.items = [me.getWayBillResultGrid(),me.getWayBillFullInfoPanel()];
		me.callParent([config])
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	//运输性质 查询 
	Ext.Ajax.request({
		url:querying.realPath('../pricing/findThreeLevelProduct.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			querying.integrativeQuery.productCodeArray = result.pricingValuationVo.productEntityList;//查询三级产品
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				querying.showErrorMes(querying.integrativeQuery.i18n('foss.querying.requestTimeout'));//请求超时！
			}else{
				querying.showErrorMes(result.message);
			}
		}
	});
	
	Ext.create('Ext.panel.Panel', {
		id : "T_querying-integrativeQueryIndex_toolbar",
		height : 42,
		margin:'-17 0 0 0',
		cls : 'floatToolbar',
		items : [{
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.cleanAll'),  //清空所有
			iconCls : 'deppon_Sicons_emptyAll',
			xtype : 'button',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearAllButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearAllButton'),
			handler : function() {
				Ext.MessageBox.buttonText.yes = querying.integrativeQuery.i18n('foss.querying.ensur');  //确定
				Ext.MessageBox.buttonText.no = querying.integrativeQuery.i18n('foss.querying.cancel');  //取消
				Ext.Msg.confirm(querying.integrativeQuery.i18n('foss.querying.message'), querying.integrativeQuery.i18n('foss.querying.ensurCleanAll'), function(btn, text) {  //提示信息', '确定要清空所有的运单么？
					if (btn == 'yes') {
						Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id')
								.getStore().removeAll();
						cleanWaybillDetall({});
					}
				});
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.cleanSelect'),  //清空选中
			xtype : 'button',
			iconCls : 'deppon_Sicons_empty',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearCheckedButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearCheckedButton'),
			handler : function() {
				var rowObjs = Ext
						.getCmp('Foss_integrativeQueryIndex_QueryResult_Id')
						.getSelectionModel().getSelection();
				if (rowObjs.length > 0) {
					Ext.MessageBox.buttonText.yes = querying.integrativeQuery.i18n('foss.querying.ensur');  //确定
					Ext.MessageBox.buttonText.no = querying.integrativeQuery.i18n('foss.querying.cancel');  //取消
					Ext.Msg.confirm(querying.integrativeQuery.i18n('foss.querying.message'), querying.integrativeQuery.i18n('foss.querying.ensurCleanSelect'),  //提示信息', '确定要清空选中的运单么？
							function(btn, text) {
								if (btn == 'yes') {
									var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
									grid.getStore().remove(rowObjs);
									cleanWaybillDetall({});
									grid.getView().refresh()
								}
							});
				} else {
					Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.tip'), querying.integrativeQuery.i18n('foss.querying.selectCleanWaybill'));  //提示', '请选择您要清空的运单！
				} 
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.clearCache'),  //清除缓存
			xtype : 'button',
			iconCls : 'deppon_Sicons_empty',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearCheckedButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryClearCheckedButton'),
			handler : function() {
				Ext.MessageBox.buttonText.yes = querying.integrativeQuery.i18n('foss.querying.ensur');  //确定
				Ext.MessageBox.buttonText.no = querying.integrativeQuery.i18n('foss.querying.cancel');  //取消
				Ext.Msg.confirm(querying.integrativeQuery.i18n('foss.querying.message'), querying.integrativeQuery.i18n('foss.querying.ensurCleanCache'), function(btn, text) {  //提示信息', '确定要清空所有的缓存么？
					if (btn == 'yes') {
						var cache = Ext.getCmp('T_querying-integrativeQueryIndex_content').getBodyPanel().getWayBillInfoCache();
						cache.clear();
					}
				});
			}
			
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.markTag'),  //标记(急)
			iconCls : 'deppon_Sicons_flag',
			xtype : 'button',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySignButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySignButton'),
			handler : function() {
				// 获取选中的行
				var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
				var rowObjs = grid.getSelectionModel().getSelection();
				if (rowObjs.length > 0) {
					Ext.MessageBox.buttonText.yes = querying.integrativeQuery.i18n('foss.querying.ensur');  //确定
					Ext.MessageBox.buttonText.no = querying.integrativeQuery.i18n('foss.querying.cancel');  //取消
					Ext.Msg.confirm(querying.integrativeQuery.i18n('foss.querying.message'), querying.integrativeQuery.i18n('foss.querying.ensurMarkTap'), function(btn, text) {  //提示信息', '确定要标记或取消（急）么？
						if (btn == 'yes') {
							var objectVo ={},codeStr = [];
							for (var i = 0; i < rowObjs.length; i++) {
								var index = grid.getStore().indexOf(rowObjs[i]);
								var cells = grid.getView().getNodes()[index].children;

								var rowMark = querying.integrativeQuery.mark.jj == rowObjs[i].get('mark');
								rowMark = cells[0].style.backgroundColor == 'rgb(255, 255, 36)';  //'rgb(162, 94, 162)'   '#A25EA2'  
								rowObjs[i].set('mark',rowMark?null:querying.integrativeQuery.mark.jj);
								rowObjs[i].set('createUserCode',querying.integrativeQuery.currentEmpCode);
								for (var j = 0; j < cells.length; j++) {
									cells[j].style.backgroundColor = (rowMark?'#fcfcfc':'#ffe600');
								}
								codeStr.push(rowObjs[i].get('waybillNo'));
							}
							objectVo.codeStr = codeStr;
							objectVo.markStatus = querying.integrativeQuery.mark.jj;
							Ext.Ajax.request({
					    		url:querying.integrativeQuery.baseinfoRealPath+'addWaybillMarkList.action',
					    		jsonData:{'objectVo':objectVo},
					    		timeout:600000,
					    		success:function(response){
					    			var result = Ext.decode(response.responseText);
					    		},
					    		exception:function(response){
					    			var result = Ext.decode(response.responseText);
					    			if(Ext.isEmpty(result)){
					    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
					    			}else{
					    				Ext.Msg.alert(result.message);
					    			}
					    		}
					    	});
						}
					});
				} else {
					Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.tip'), querying.integrativeQuery.i18n('foss.querying.selectMarkTapWaybill'));  //提示', '请选择要标记（急）的运单！
				}
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.markEmergency'),  //标记(紧急)
			iconCls : 'deppon_Sicons_flagEmer',
			xtype : 'button',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryInstancySignButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryInstancySignButton'),
			handler : function() {
				// 获取选中的行
				var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
				var rowObjs = grid.getSelectionModel().getSelection();
				if (rowObjs.length > 0) {
					Ext.MessageBox.buttonText.yes = querying.integrativeQuery.i18n('foss.querying.ensur');  //确定
					Ext.MessageBox.buttonText.no = querying.integrativeQuery.i18n('foss.querying.cancel');  //取消
					Ext.Msg.confirm(querying.integrativeQuery.i18n('foss.querying.message'), querying.integrativeQuery.i18n('foss.querying.ensurMarkEmergency'), function(btn, text) {  //提示信息', '确定要标记或取消（紧急）么？
						if (btn == 'yes') {
							var objectVo ={},codeStr = [];
							for (var i = 0; i < rowObjs.length; i++) {
								var index = grid.getStore().indexOf(rowObjs[i]);
								var cells = grid.getView().getNodes()[index].children;
								
								var rowMark = querying.integrativeQuery.mark.fcjj == rowObjs[i].get('mark');
								rowMark = cells[0].style.backgroundColor == 'rgb(226, 57, 16)';
								rowObjs[i].set('mark',rowMark?null:querying.integrativeQuery.mark.fcjj);
								rowObjs[i].set('createUserCode',querying.integrativeQuery.currentEmpCode);
								for (var j = 0; j < cells.length; j++) {
									cells[j].style.backgroundColor = (rowMark?'#fcfcfc':'#e23910');
								}
								codeStr.push(rowObjs[i].get('waybillNo'));
							}
							objectVo.codeStr = codeStr;
							objectVo.markStatus = querying.integrativeQuery.mark.fcjj;
							Ext.Ajax.request({
					    		url:querying.integrativeQuery.baseinfoRealPath+'addWaybillMarkList.action',
					    		jsonData:{'objectVo':objectVo},
					    		timeout:600000,
					    		success:function(response){
					    			var result = Ext.decode(response.responseText);
					    		},
					    		exception:function(response){
					    			var result = Ext.decode(response.responseText);
					    			if(Ext.isEmpty(result)){
					    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
					    			}else{
					    				Ext.Msg.alert(result.message);
					    			}
					    		}
					    	});
						}
					});
				} else {
					Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.tip'), querying.integrativeQuery.i18n('foss.querying.selectMarkEmergencyWaybill'));  //'提示', '请选择要标记（紧急）的运单！'
				}
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.custQuery'),  //自定义查询
			xtype : 'button',
			iconCls : 'deppon_Sicons_query',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySelfDefineSearchButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQuerySelfDefineSearchButton'),
			handler : function() {
				var win = Ext.create('Foss.integrativeQueryIndex.UserDefinedWin');
				// 获得自定义查询 方案和 方案查询条件
				win.show();
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.printWaybill'),  //打印运单
			iconCls : 'deppon_Sicons_print',
			xtype : 'button',
			disabled : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryPrintButton'),
			hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryPrintButton'),
			handler : function() {
					if(querying.integrativeQuery.waybillStatus=='OBSOLETE'){   //已作废
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.tip'),querying.integrativeQuery.i18n('foss.querying.waybillNumberVoid')); //该单号已作废！
					}else if(querying.integrativeQuery.waybillStatus=='ABORTED'){  //已中止
						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.tip'),querying.integrativeQuery.i18n('foss.querying.waybillNumberStop')); //该单号已中止！
					}else{
						querying.integrativeQuery.printGoodsWayBill(Ext
								.getCmp('Foss_integrativeQueryIndex_Form_WaybillNOFullForm_Id')
								.getForm().findField('waybillNo').getValue(), false
							).printWayBill();
					}
			}
		}, {
			width : 100,
			tabIndex : 1,
			textAlign : 'center',
			text : querying.integrativeQuery.i18n('foss.querying.viewConsignor'),  //查看发货人
			itemId : 'Foss_integrativeQueryIndex_viewConsignor_Id',
			xtype : 'button',
			iconCls : 'deppon_Sicons_findShipper',
			//hidden : !querying.integrativeQuery.isPermission('integrativeQueryIndex/integrativeQueryFindShipperButton'),
			handler : function() {
				var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
				var rowObjs = grid.getSelectionModel().getSelection();
				//获取客户展示窗口
				var custWindow = grid.getCustWindow();
				if (rowObjs.length > 0) {
					
					if(rowObjs.length > 1){
						grid.showWarningMsg(querying.integrativeQuery.i18n('foss.querying.reminder'),querying.integrativeQuery.i18n('foss.querying.pleaseSelectWayBill'));  //温馨提醒','请选择运单号！
					}else{
						//获取运单号
						var waybillNO = rowObjs[0].get('waybillNo');
						var url = querying.realPath('queryCustomerInfo.action');
						var jsonData = {'waybillVo':{'wayBillNo':waybillNO}};
						//调用Ajax请求
						Ext.Ajax.request({
							url:url,
							jsonData:jsonData,
							//作废成功
							success : function(response) {
				                  var json = Ext.decode(response.responseText);
				                  //为window 窗口设置值
				                  if(json.objectVo.custDto==null){
				                	  grid.showWarningMsg(querying.integrativeQuery.i18n('foss.querying.messageTip'),querying.integrativeQuery.i18n('foss.querying.informalCustomersInformationView'));  //消息提示
				                  }else{
				                	  custWindow.resetValues(json.objectVo.custDto);
					                  //显示窗口
					                  custWindow.show();
				                  }
				                },
				            //保存失败
				            exception : function(response) {
				                  var json = Ext.decode(response.responseText);
				                  grid.showWarningMsg(querying.integrativeQuery.i18n('foss.querying.messageTip'),querying.integrativeQuery.i18n('foss.querying.informalCustomersInformationView'));  //消息提示
				            }
						});
					}
				}else{
					grid.showWarningMsg(querying.integrativeQuery.i18n('foss.querying.reminder'),querying.integrativeQuery.i18n('foss.querying.pleaseSelectWayBill'));  //温馨提醒','请选择运单号！
					return ;
				}
				
			}
		}],
		renderTo : 'T_querying-integrativeQueryIndex-body'
	});
	//查询条件
	var queryForm = Ext.create('Foss.integrativeQueryIndex.QueryForm');
	//查询结果（左侧运单列表+右侧详细信息）
	var bodyPanel = Ext.create('Foss.integrativeQueryIndex.BodyPanel');
	
	Ext.getCmp('T_querying-integrativeQueryIndex').add(Ext.create('Ext.panel.Panel', {
				id : 'T_querying-integrativeQueryIndex_content',
				cls : "panelContent",
				margin:'30 0 0 0',
				bodyCls : 'panelContent-body',
				layout : 'auto',
				getBodyPanel: function(){
					return bodyPanel;
				},
				getQueryForm : function() {
					return queryForm;
				},
				items : [queryForm,bodyPanel],
				listeners: {
					'boxready': function( component, width, height, eOpts){
						var tab = Ext.getCmp('T_querying-integrativeQueryIndex');
						if(Ext.isEmpty(tab)){
							tab = Ext.getCmp('T_querying-integrativeQueryIndex_content');
						}
						var loader = tab.loader;
						if(Ext.isEmpty(loader)){
							return;	
						}
						var params = loader.params;
						if(Ext.isEmpty(params)){
							return;
						}
						var queryTabForm = queryForm.getActiveTab(),
							button = queryTabForm.items.items[10].items.items[2],
							waybillNoField = queryTabForm.getForm().findField('startWaybillNo');
						waybillNoField.setValue(params.waybillNumber);
						queryForm.searchBtnHandler(button);
					}
				} 
			}));
});
/**
 * 自定义查询  代码
 */
querying.integrativeQuery.rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {		//对行级别进行编辑
	clicksToEdit: 2								//设置鼠标点击多少次，触发编辑
});
querying.integrativeQuery.selectFirstRow = function(gridArray,parentData,childData,parentRecord) {
	gridArray[0].store.on('load',function(s,rs){
		var count =0;
		new Ext.util.DelayedTask(function(){
			gridArray[0].store.each(function(){
				count++;
				if(count === gridArray[0].store.getCount()){
					Ext.isEmpty(parentRecord)?gridArray[0].getSelectionModel().select(0):gridArray[0].getSelectionModel().select(parentRecord);

					gridArray[1].store.on('load',function(s,rs){
						var count =0;
						new Ext.util.DelayedTask(function(){
							gridArray[1].store.each(function(){
								count++;
								if(count === gridArray[1].store.getCount()){
									gridArray[1].getSelectionModel().select(0);
								}
							});
						
						}).delay(100);
					});
					gridArray[1].store.loadRawData(childData);
				
				}
			});
		
		}).delay(100);
	});
	gridArray[0].store.loadRawData(parentData);
	if(Ext.isEmpty(parentData)){
		var textfieldArr = gridArray[1].up('window').query('textfield');
		textfieldArr[1].setValue(null);
		textfieldArr[2].setValue(null);
		gridArray[1].store.loadData([]);
	}
};
//------------------------------------WINDOW--------------------------------
//自定义查询界面win
Ext.define('Foss.integrativeQueryIndex.UserDefinedWin',{
	extend : 'Ext.window.Window',
	title : querying.integrativeQuery.i18n('foss.querying.custQuery'),			//自定义查询
	closable : true,
	modal : true,
	resizable:false,
	autoScroll:true,
	width :750,
	height :525,	
	layout : 'column',
	listeners:{
		beforehide:function(me){}
	},
	constructor : function(config) {
		var me = this,cfg = Ext.apply({}, config);
		me.fbar = me.getMyFbar(config);
		me.tbar = me.getMyTbar(config);
		me.items = me.getItems(config);
		me.callParent([cfg]);
		var parentData = [],childData = [];
		//自定义查询 界面 打开时 加载当前登录用户 查询方案
		me.initWinSearch();
	},
    getItems : function(config){
     	var me = this
     	,compareConditionStore = FossDataDictionary.getDataDictionaryStore('COMPARE_CONDITION')
     	,compareSignStore = FossDataDictionary.getDataDictionaryStore('COMPARE_SYMBOL')
     	,logicSignStore = FossDataDictionary.getDataDictionaryStore('LOGIC_SYMBOL');
     	return [{
 			xtype:'grid',
 			frame : true,
 			deferRowRender:false,
 		    sortableColumns:true,
 		    enableColumnHide:false,
 		    enableColumnMove:false,
 			stripeRows : true, 									// 交替行效果
 			selType : "rowmodel", 								// 选择类型设置为：行选择
 			emptyText: querying.integrativeQuery.i18n('foss.querying.searchListNull'),							//查询结果为空
 			width:180,
 			height:320,
 			selModel:Ext.create('Ext.selection.CheckboxModel',{//mode:'SINGLE',checkOnly:true
 				listeners:{
 					select:function(rowModel,record,index,eOpts ){
 						me.selectSchemeEvent(rowModel,record,index,eOpts );
 					}
 				}}),
 			store:Ext.create('Ext.data.Store',{//Ext.data.ArrayStore
 				model:'Foss.querying.integrativeQuery.UserDefinedQueryDtoModel',
 				data:[]
 			}),
 			columns:[{flex:1,dataIndex:'name',text:querying.integrativeQuery.i18n('foss.querying.program')}]  //方案
 		},{
 			xtype:'container',
 			columnWidth:1,
 			items:[{
 				columnWidth:1,width:535,
 				xtype:'textfield',
 				fieldLabel:querying.integrativeQuery.i18n('foss.querying.programName'),  //方案名
 				maxLength:50
 			},{
 				xtype:'textfield',
 				name:'code',
 				hidden:true,
 				fieldLabel:querying.integrativeQuery.i18n('foss.querying.programCode')  //方案code
 			},{
 				xtype:'grid',
 				frame : true,
 			    sortableColumns:true,
 			    enableColumnHide:false,
 			    enableColumnMove:false,
 				stripeRows : true, 									// 交替行效果
 				selType : "rowmodel", 								// 选择类型设置为：行选择
 				emptyText: querying.integrativeQuery.i18n('foss.querying.searchListNull'),							//查询结果为空
 				selType: 'rowmodel',								//选择模式
 				plugins: [querying.integrativeQuery.rowEditing],
//	 				renderTo: 'rowediting',
 				columnWidth:1,
 				height:300,
 				columns:[{flex:1,dataIndex:'compareCondition',text:querying.integrativeQuery.i18n('foss.querying.compareCon'),  //比较条件
	 					renderer:function(v){
	 						return FossDataDictionary. rendererSubmitToDisplay (v,'COMPARE_CONDITION');
	 					},
	 					field: {xtype: 'combo',
							displayField: 'valueName',
							valueField: 'valueCode',
							store:compareConditionStore,
							listeners:{
								select:function(field,record,index,eOpts ){
									me.selectCompareEvent(field,record,index,eOpts );
								},
			        	 		focus:function( field,the,eOpts ){
			        	 			me.focusCompareEvent( field,the,eOpts );
			        	 		}
							}
					}},{flex:1,dataIndex:'compareSign',text:querying.integrativeQuery.i18n('foss.querying.compareSigne'),  //比较符
	 					renderer:function(v){
	 						return FossDataDictionary. rendererSubmitToDisplay (v,'COMPARE_SYMBOL');
	 					},
	 					field: {
							xtype: 'combo',
							displayField: 'valueName',
							valueField: 'valueName',
							store:compareSignStore,
			        	 	listeners:{
			        	 		focus:function( field,the,eOpts ){
			        	 			me.focusCompareEvent( field,the,eOpts );
			        	 		}
			        	 	}
 					}},{flex:1,dataIndex:'compareValue',text:querying.integrativeQuery.i18n('foss.querying.compareValue'),  //比较值
 	 					renderer:function(v,meta,record,rowIndex){
 	 						var v_code = record.get('compareCondition');
 	 						//数据字典
 	 						if(querying.integrativeQuery.compareConditionType.isDict === querying.integrativeQuery.compareCondition[v_code]){
 	 							var data = [],product = {};
 	 							// 如果是【运输性质】 则循环 对应运输性质 描述
 	 							if(querying.integrativeQuery.compareConditionDict.PRODUCT_CODE === querying.integrativeQuery.compareConditionDict[v_code]){
 	 								for(var i = 0;i< querying.integrativeQuery.productCodeArray.length;i++){
 	 									product = querying.integrativeQuery.productCodeArray[i];
 	 									if(product.code === v){
 	 										return product.name;
 	 									}
 	 								}
 	 							}
 	 							// 如果是【货物类型】 则循环 对应货物类型 描述
 	 							else if(querying.integrativeQuery.compareConditionDict.GOODS_TYPE_CODE === querying.integrativeQuery.compareConditionDict[v_code]){
 	 									if(querying.integrativeQuery.qyGoodType.a  === v){
 	 										return querying.integrativeQuery.qyGoodType.ah;
 	 									}else if(querying.integrativeQuery.qyGoodType.b  === v){
 	 										return querying.integrativeQuery.qyGoodType.bh;
 	 									}else{
 	 										return FossDataDictionary.rendererSubmitToDisplay (v,querying.integrativeQuery.compareConditionDict[v_code]);
 	 									}
 	 							}
 	 							// 如果是【提货方式】 则循环【汽运和空运】 数据字典 data数据
 	 							else if(querying.integrativeQuery.compareConditionDict.RECEIVE_METHOD === querying.integrativeQuery.compareConditionDict[v_code]){
 	 								var receiveMethod = querying.integrativeQuery.receiveMethod.RECEIVE_METHOD,airData = [],highWayData = [];
 	 								airData = FossDataDictionary.getDataByTermsCode(receiveMethod);//空运
 	 								airData = Ext.isEmpty(airData)?[]:airData;
 	 								highWayData = FossDataDictionary.getDataByTermsCode(querying.integrativeQuery.compareConditionDict[v_code]);//汽运
 	 								highWayData = Ext.isEmpty(highWayData)?[]:highWayData;
 	 								for(var i = 0;i< airData.length;i++){
 	 									data.push({'valueCode':airData[i].valueCode,'valueName':(querying.integrativeQuery.i18n('foss.querying.airlift') + airData[i].valueName)});  //空运
 	 								}
 	 								for(var j = 0;j< highWayData.length;j++){
 	 									data.push({'valueCode':highWayData[j].valueCode,'valueName':(querying.integrativeQuery.i18n('foss.querying.automotive') + highWayData[j].valueName)});  //汽运
 	 								}
 	 								for(var k = 0;k< data.length;k++){
 	 									if(data[k].valueCode === v){
 	 										return data[k].valueName;
 	 									}
 	 								}
 	 							}
 	 							//否则则 调用数据字典渲染方法
 	 							else{
 	 	 							return FossDataDictionary.rendererSubmitToDisplay (v,querying.integrativeQuery.compareConditionDict[v_code]);
 	 							}
 	 						}
 	 						return v;
 	 					},field: {
 				        	 	xtype:'fosscontainer',
 				        	 	layout: 'anchor',
 				        	 	items:[{
 				        	 		xtype: 'textfield',
 				        	 		name:'compareValue'
 				        	 	}]
 					}},{flex:1,dataIndex:'logicSign',text:querying.integrativeQuery.i18n('foss.querying.logicSigne'),  //逻辑符
	 					renderer:function(v){
	 						return FossDataDictionary. rendererSubmitToDisplay (v,'LOGIC_SYMBOL');
	 					},
	 					field: {
						xtype: 'combo',
						displayField: 'valueName',
						valueField: 'valueCode',
						store:logicSignStore,
		        	 	listeners:{
		        	 		focus:function( field,the,eOpts ){
		        	 			me.focusCompareEvent( field,the,eOpts );
		        	 		}
		        	 	}
 					}},{flex:1,dataIndex:'schemeCode',text:querying.integrativeQuery.i18n('foss.querying.schameId'),hidden:true,field: {  //方案id
 						xtype: 'textfield',
		        	 	listeners:{
		        	 		focus:function( field,the,eOpts ){
		        	 			me.focusCompareEvent( field,the,eOpts );
		        	 		}
		        	 	}
 					}}],
 				store:Ext.create('Ext.data.Store',{
 					model:'Foss.querying.integrativeQuery.UserDefinedQueryConditionModel',
 					data:[]
 				}),
 				tbar: [{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.add'),handler:function(btn){  //新增
 					if(!querying.integrativeQuery.rowEditing.editing){
 		                var r = Ext.create('Foss.querying.integrativeQuery.UserDefinedQueryConditionModel');
 		                btn.up('grid').store.insert(0, r);
 		                querying.integrativeQuery.rowEditing.startEdit(0, 0);
 					}else{
 						Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseWaiteToAdd'));  //Foss提醒您','请完成上一次编辑后再进行新增！
 					}
 				}},'-',{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.delete'),handler:function(btn){  //删除
 					 //关键属性id
 					 var grid = btn.up('grid'); store =  grid.store, sm = grid.getSelectionModel();
 					 querying.integrativeQuery.rowEditing.cancelEdit();
 					 store.remove(sm.getSelection());
 					 if (store.getCount() > 0) {
 						 sm.select(0);
 					 }
 				}},'-', {
 					xtype:'label',
 					html: querying.integrativeQuery.i18n('foss.querying.dbClickToEdit')  //双击进行编辑
 				}],
 			    fbar: [{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.ensur'),handler:function(btn){  //确定
 			    	me.ensureEvent(btn);
 			    }},
 	           { xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.cancel'),handler:function(btn){  //取消
 	        	   me.cancelEvent(btn);
 	           }}]
 			}]
 		}];
    },
	getMyFbar:function(config){
     	var me = this;
		return [{xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.query'),handler:function(btn){  //查询
			//校验是否选择查询方案，
			me.searchWaybill(btn);
		}}];
	},
	getMyTbar:function(config){
     	var me = this;
		return [{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.add'),handler:function(btn){  //新增
				// 新增查询方案，把对应查询方案 查询条件 清空
				me.addSchemeEvent(btn);
			}},
			{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.save'),hidden:true,handler:function(btn){  //保存
				//TODO 计划去掉此按钮，改用界面上 确定 实现该功能
			}},
			{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.void'),handler:function(btn){  //作废
				// 作废所选 查询方案 [支持批量作废]关键属性code
				me.delSchemeEvent(btn);
			}},
			{ xtype: 'button', text: querying.integrativeQuery.i18n('foss.querying.edit'),hidden:true },//已取消该功能  //编辑
			{ xtype: 'label', width:300},
			{ xtype: 'combo', fieldLabel: querying.integrativeQuery.i18n('foss.querying.maxReturnList'),  //最大返回结果数
				name:'',
				queryMode: 'local',
				displayField: 'value',
				valueField: 'code',
				store:Ext.create('Ext.data.Store',{//Ext.data.ArrayStore
					fields: ['code', 'value'],
				    data : [{'value':'1000', 'code':1000},
				        {'value':'3000', 'code':3000},
				        {'value':'10000', 'code':10000},
				        {'value':'20000', 'code':20000},
				        {'value':'50000', 'code':50000},
				        {'value':'100000', 'code':100000}
				    ]
			})}
	     ];
	 },
	//新增 查询方案
	 addSchemeEvent:function(btn){
		var me = this,gridArray = btn.up('window').query('grid')
			,textfieldArr = btn.up('window').query('textfield')
			,schameName = textfieldArr[1],schameCode = textfieldArr[2]
			,grid = gridArray[1],store = grid.store
			,userDefinedQuery =Ext.create('Foss.querying.integrativeQuery.UserDefinedQueryConditionModel');
		schameName.setValue(null);
		schameCode.setValue(null);
		store.loadData([]);
        store.insert(0, userDefinedQuery);
        querying.integrativeQuery.rowEditing.startEdit(0, 0);
	 },
	//作废 查询方案
	 delSchemeEvent:function(btn){
		var me = this,gridArray = btn.up('window').query('grid')
			,schameName = btn.up('window').query('textfield')[1].getValue()
			,re_count = btn.up('window').down('combo').getValue()
			,schemeGrid = gridArray[0],grid = gridArray[1]
			,store = schemeGrid.store,data = store.data.items
			,codeStr = [],selectData = schemeGrid.getSelectionModel().getSelection();
		// 检查是否选择至少一条数据
		if(selectData.length<1){
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.selectSearchSchame'));  //FOSS提醒您','请选择一条查询方案！
			return;
		}
		// 自定义查询 方案 预删除 的 code数组
		for ( var i = 0; i < selectData.length; i++){
			codeStr.push(selectData[i].get('code'));
		}
		//作废方案 后 重新加载 数据
		me.ajax_fn(querying.integrativeQuery.baseinfoRealPath+'deleteUserDefinedQueryDto.action'
				, {'codeStr':codeStr}, function(result){
			me.initWinSearch();
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.operatorSuccess'));  //Foss提醒您','操作成功！
		});
	 },
	//按照 界面上 当前展示 自定义查询条件 查询运单
	searchWaybill:function(btn){
     	var me = this;
		// 按照 界面上 当前展示 自定义查询条件 查询运单
		querying.integrativeQuery.rowEditing.cancelEdit();									//取消 grid编辑状态
		var me = this,gridArray = btn.up('window').query('grid')
			,schameName = btn.up('window').query('textfield')[1].getValue()
			,re_count = btn.up('window').down('combo').getValue()
			,schemeGrid = gridArray[0],grid = gridArray[1]
			,store = grid.store,data = store.data.items
			,userDefinedQuery =Ext.create('Foss.querying.integrativeQuery.UserDefinedQueryDtoModel').data
			,con_array = [];
		// 判断查询条数是否为空 ,为空则按照默认值为1000，否则按照选定值 查询
		re_count = Ext.isEmpty(re_count)?1000:re_count;
		// 判断查询条件 是否为空没有任何查询条件 无条件 则给予提醒
		if(0 === grid.store.getCount()){
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.searchConditionNotBeNull'));  //FOSS提醒您','自定义查询条件不能为空，请增减至少一个查询条件！
			return;
		}
		// 判断查询条件中是否有 查询条件中 存在空值 的条件 存在则给予提醒
		for ( var i = 0; i < data.length; i++){
			if(Ext.isEmpty(data[i].get('compareCondition'))||Ext.isEmpty(data[i].get('compareSign'))
				||Ext.isEmpty(data[i].get('compareValue'))||Ext.isEmpty(data[i].get('logicSign'))){
				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseCheckStart')+(i+1)+querying.integrativeQuery.i18n('foss.querying.pleaseCheckEnd'));  //FOSS提醒您
				return;  //请核实<span style = "color:red">第(i+1)行</span>数据是否填写完整且正确！
			}else{
				con_array.push(data[i].data);
			}
		}
		userDefinedQuery.name = schameName;
		userDefinedQuery.userQueryConditions = con_array;
		userDefinedQuery.returnCount = re_count;
		
		//查询运单 
		me.ajax_fn(querying.realPath('queryWayBillListByUserDefinedQueryDto.action')
				, {'userDefinedQuery':userDefinedQuery}, function(result){
			var grid = Ext.getCmp('Foss_integrativeQueryIndex_QueryResult_Id');
			var store = grid.getStore();
			store.on('load',function(s,records){   
				var girdcount=0;   
				s.each(function(r){   
					if(querying.integrativeQuery.mark.jj == r.get('mark')&&querying.integrativeQuery.currentEmpCode==r.get('createUserCode')){
					   //给单元格涂色
						var cells = grid.getView().getNodes()[girdcount].children;
						for(var i= 0;i<cells.length;i++){
							cells[i].style.backgroundColor='#ffe600'; //金黄色
						}
					}else if(querying.integrativeQuery.mark.fcjj == r.get('mark')&&querying.integrativeQuery.currentEmpCode==r.get('createUserCode')){
						//给单元格涂色
						var cells = grid.getView().getNodes()[girdcount].children;
						for(var i= 0;i<cells.length;i++){
							cells[i].style.backgroundColor='#e23910';//#FF34B3
						}
					}
					girdcount++;
				});   
			}); 
			store.loadRawData(result.waybillVo.waybills);
			if(!Ext.isEmpty(result.waybillVo.waybills)){
				grid.getSelectionModel().select(0);
				grid.fireEvent('itemclick',grid,grid.getSelectionModel().getSelection()[0]);
			}else{
				cleanWaybillDetall({});
			}
		});
	},
	initWinSearch:function(parentRecord){
     	var me = this;
		me.ajax_fn(querying.realPath('queryUserDefinedQueryDtos.action')
				, {'userDefinedQuery':{}}, function(result){
			parentData = Ext.isEmpty(result.objectVo.userDefinedQueryList)?[]:result.objectVo.userDefinedQueryList;
			childData = Ext.isEmpty(parentData)?[]:parentData[0].userQueryConditions;
			querying.integrativeQuery.selectFirstRow(me.query('grid'),parentData,childData,parentRecord);
		});
	},
    //可编辑表格 聚焦事件 修改 比较值的 值
	focusCompareEvent:function( field,the,eOpts ){
		var me = this,form = field.up('form')
			,v_compareValue = form.getRecord().data.compareValue
			,con = form.down('container'),form = form.getForm()
			,v_code = form.findField('compareCondition').getValue()
			,c_compareValue = form.findField('compareValue');
		// 判断值  比较值是否正确如果不正确 则进行修改
		if(!Ext.isEmpty(c_compareValue)&&querying.integrativeQuery.compareConditionXtype[v_code] != c_compareValue.xtype){
			con.removeAll();
			me.addCompareValueCom(con,v_code);
			if(!Ext.isEmpty(form.findField('compareValue'))){
				form.findField('compareValue').setValue(v_compareValue);
			}
		}
    },
	//可编辑 表格中 比较条件 select事件
	selectCompareEvent:function(field,record,index,eOpts ){
		var me = this,con = field.up('form').down('container')
			,v_code = record[0].get('valueCode'),component = null;
		con.removeAll();
		me.addCompareValueCom(con,v_code);
	},
	//判断 比较值 类型是否和 比较条件 类型 相对应 
	addCompareValueCom:function(con,v_code){
		var component = null;
		//数据字典
		if(querying.integrativeQuery.compareConditionType.isDict === querying.integrativeQuery.compareCondition[v_code]){
			var data = [],store = Ext.create('Ext.data.Store',{
				fields:['valueCode','valueName'],
	            data : data
			}),product = {};
			// 如果是【运输性质】 则 封装 数据字典 data数据
			if(querying.integrativeQuery.compareConditionDict.PRODUCT_CODE === querying.integrativeQuery.compareConditionDict[v_code]){
				for(var i = 0;i< querying.integrativeQuery.productCodeArray.length;i++){
					product = querying.integrativeQuery.productCodeArray[i];
					data.push({'valueCode':product.code,'valueName':product.name});
				}
				store = Ext.create('Ext.data.Store',{
					fields:['valueCode','valueName'],
		            data : data
				});
			}
			// 如果是【货物类型】 则循环 数据字典 data数据
			else if(querying.integrativeQuery.compareConditionDict.GOODS_TYPE_CODE === querying.integrativeQuery.compareConditionDict[v_code]){
				var a = querying.integrativeQuery.qyGoodType.a,b = querying.integrativeQuery.qyGoodType.b,
				ah = querying.integrativeQuery.qyGoodType.ah,bh = querying.integrativeQuery.qyGoodType.bh;
				store = FossDataDictionary.getDataDictionaryStore(querying.integrativeQuery.compareConditionDict[v_code],null,[
				      {'valueCode':a,'valueName':ah},{'valueCode':b,'valueName':bh}
				    ]);
			}
			// 如果是【提货方式】 则循环【汽运和空运】 数据字典 data数据
			else if(querying.integrativeQuery.compareConditionDict.RECEIVE_METHOD === querying.integrativeQuery.compareConditionDict[v_code]){
				var receiveMethod = querying.integrativeQuery.receiveMethod.RECEIVE_METHOD,airData = [],highWayData = [];
				airData = FossDataDictionary.getDataByTermsCode(receiveMethod);//空运
				airData = Ext.isEmpty(airData)?[]:airData;
				highWayData = FossDataDictionary.getDataByTermsCode(querying.integrativeQuery.compareConditionDict[v_code]);//汽运
				highWayData = Ext.isEmpty(highWayData)?[]:highWayData;
				for(var i = 0;i< airData.length;i++){
					data.push({'valueCode':airData[i].valueCode,'valueName':(querying.integrativeQuery.i18n('foss.querying.airlift') + airData[i].valueName)});  //空运
				}
				for(var j = 0;j< highWayData.length;j++){
					data.push({'valueCode':highWayData[j].valueCode,'valueName':(querying.integrativeQuery.i18n('foss.querying.automotive') + highWayData[j].valueName)});  //汽运
				}
				store = Ext.create('Ext.data.Store',{
					fields:['valueCode','valueName'],
		            data : data
				});
			}
			//否则则 调用数据字典 获取 data数据方法
			else{
				store = FossDataDictionary. getDataDictionaryStore(querying.integrativeQuery.compareConditionDict[v_code]);
			}
			component = Ext.create('Ext.form.ComboBox', {
				store: store,
				displayField: 'valueName',
	            valueField: 'valueCode',
	            forceSelection:true,
				hideLabel:true,
				anchor: '1 1',
				name:'compareValue'
			});
		}
		//数字
		else if(querying.integrativeQuery.compareConditionType.isNum === querying.integrativeQuery.compareCondition[v_code]){
			component = Ext.create('Ext.form.field.Number',{
					hideLabel:true,
					anchor: '1 1',
					name:'compareValue',
					hideTrigger: true,
			        keyNavEnabled: false,
			        mouseWheelEnabled: false
			});
		}
		//字符串
		else if(querying.integrativeQuery.compareConditionType.isStr === querying.integrativeQuery.compareCondition[v_code]){
			component = Ext.create('Ext.form.field.Text',{
					hideLabel:true,
					anchor: '1 1',
					name:'compareValue'
			});
		}
		// 加载 所选 方案的 具体查询条件 可输入值
		Ext.isEmpty(component)?'':con.add(component);
	},
    //选择 方案 事件
    selectSchemeEvent:function(rowModel,record,index,eOpts ){
		var conGrid = this.query('grid')[1]
			,textfieldArr = this.query('grid')[0].up('window').query('textfield')
			,schameName = textfieldArr[1],schameCode = textfieldArr[2];
		// 加载 所选 方案的 具体查询条件
		conGrid.store.on('load',function(s,rs){
			var count =0;
			new Ext.util.DelayedTask(function(){
				conGrid.store.each(function(){
					count++;
					if(count === conGrid.store.getCount()){
						conGrid.getSelectionModel().select(0);
					}
				});
			}).delay(100);
		});
		conGrid.store.loadRawData(record.get('userQueryConditions'));
		//赋值  方案名
		schameName.setValue(record.get('name'));
		schameCode.setValue(record.get('code'));
    },
    //取消 事件
    cancelEvent:function(btn){
		var schemeGrid = btn.up('window').down('grid')
			,schameName = btn.up('window').query('textfield')[1]
			,grid = btn.up('grid');
		//TODO ①选中方案中第一条，加载方案中条件
		// ②方案重新load，条件全部 置空
//		schameName.setValue(null);
//		schemeGrid.store.load();
//		grid.store.loadData([]);
		this.initWinSearch();
		Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.operatorSuccess'));  //Foss提醒您','操作成功！
    },
    //确定 事件 
    ensureEvent:function(btn){
		querying.integrativeQuery.rowEditing.cancelEdit();									//取消 grid编辑状态
		var me = this,schemeGrid = btn.up('window').down('grid')
			,textfieldArr = btn.up('window').query('textfield')
			,schameName = textfieldArr[1].getValue(),schameCode = textfieldArr[2].getValue()
			,grid = btn.up('grid'),store = grid.store,data = store.data.items
			,re_count = btn.up('window').down('combo').getValue()
			,userDefinedQuery =Ext.create('Foss.querying.integrativeQuery.UserDefinedQueryDtoModel').data
			,con_array = [];
		// 判断方案名称是否为空 为空,为空则按照默认值为1000，否则按照选定值 查询
		if(Ext.isEmpty(schameName)){
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.schameNameNotBeNull'));  //FOSS提醒您','方案名不能为空！
			return;
		}
		// 判断查询条数是否为空 为空,为空则按照默认值为1000，否则按照选定值 查询
		re_count = Ext.isEmpty(re_count)?1000:re_count;
		// 判断查询条件 是否为空没有任何查询条件 无条件 则给予提醒
		if(0 === grid.store.getCount()){
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.searchConditionNotBeNull'));  //FOSS提醒您','自定义查询条件不能为空，请增减至少一个查询条件！
			return;
		}
		// 判断查询条件中是否有 查询条件中 存在空值 的条件 存在则给予提醒
		for ( var i = 0; i < data.length; i++){
			if(Ext.isEmpty(data[i].get('compareCondition'))||Ext.isEmpty(data[i].get('compareSign'))
				||Ext.isEmpty(data[i].get('compareValue'))||Ext.isEmpty(data[i].get('logicSign'))){
				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.pleaseCheckStart')+(i+1)+querying.integrativeQuery.i18n('foss.querying.pleaseCheckEnd'));  //FOSS提醒您
				return;
			}else{
				con_array.push(data[i].data);
			}
		}
		userDefinedQuery.name = schameName;
		userDefinedQuery.code = schameCode;
		userDefinedQuery.userQueryConditions = con_array;
		//保存 新建方案 
		var url = Ext.isEmpty(schameCode)?querying.integrativeQuery.baseinfoRealPath+'addUserDefinedQueryDto':querying.integrativeQuery.baseinfoRealPath+'updateUserDefinedQueryDto';
		me.ajax_fn(url+'.action'
				, {'userDefinedQuery':userDefinedQuery}, function(result){
			if(!Ext.isEmpty(schameCode)){
				var userDefinedQuery = result.objectVo.userDefinedQuery;
				textfieldArr[1].setValue(userDefinedQuery.name);
				textfieldArr[2].setValue(userDefinedQuery.code);
				
				store.on('load',function(s,rs){
					var count =0;
					store.each(function(){
						count++;
						if(count === store.getCount()){
							grid.getSelectionModel().select(0);
						}
					});
				});
				store.loadRawData(userDefinedQuery.userQueryConditions);
				schemeGrid.store.each(function(r){
					if(userDefinedQuery.code == r.get('code')){
						r.set('userQueryConditions',userDefinedQuery.userQueryConditions);
						return;
					}
				});
			}else{
				me.initWinSearch();
			}
			Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.FOSSRemindYou'),querying.integrativeQuery.i18n('foss.querying.operatorSuccess'));  //Foss提醒您','操作成功！
		});
	},
    //ajax请求
    ajax_fn:function(url,objectVo,success_fn){
    	Ext.Ajax.request({
    		url:url,
    		jsonData:{'objectVo':objectVo},
    		timeout:600000,
    		success:function(response){
    			var result = Ext.decode(response.responseText);
    			success_fn(result);
    		},
    		exception:function(response){
    			var result = Ext.decode(response.responseText);
    			if(Ext.isEmpty(result)){
    				Ext.Msg.alert(querying.integrativeQuery.i18n('foss.querying.dataException'));  //数据异常!
    			}else{
    				Ext.Msg.alert(result.message);
    			}
    		}
    	});
    }
});
Ext.define('Foss.querying.integrativeQuery.FossContainer',{
	extend : 'Ext.container.Container',
	alias: 'widget.fosscontainer',
	getValue:function(){
        return null;
    }
});
//自定义查询条件 方案 dto实体
Ext.define('Foss.querying.integrativeQuery.UserDefinedQueryDtoModel', {
	extend : 'Ext.data.Model',
	fields : [//方案编码
	    {name:'code',type:'string'},
	    //方案名称
     	{name:'name',type:'string'},
     	 //比较符
     	{name:'userCode',type:'string'},
     	 //是否默认方案.
     	{name:'defaults',type:'string'},
     	 //是否启用.
     	{name:'active',type:'string',defaultValue:querying.integrativeQuery.active.y},
     	 //自定义查询 返回结果数.
     	{name:'returnCount',type:'int'},
     	 //自定义查询条件 集合.
     	{name:'userQueryConditions',defaultValue:null},

    	{name:'createUser',type:'string'},
    	{name:'createDate',type:'date',convert: dateConvert,defaultValue:null}, 
    	{name:'modifyDate',type:'date',convert: dateConvert,defaultValue:null},
    	{name:'modifyUser',type:'string'}]
});
//自定义查询条件 实体
Ext.define('Foss.querying.integrativeQuery.UserDefinedQueryConditionModel', {
	extend : 'Ext.data.Model',
	fields : [//比较条件
     	{name:'compareCondition',type:'string'},
     	 //比较符
     	{name:'compareSign',type:'string'},
     	 //比较值
     	{name:'compareValue',type:'string'},
     	 //逻辑符
     	{name:'logicSign',type:'string'},
     	 //查询方案code
     	{name:'schemeCode',type:'string'},
     	 //是否启用.
     	{name:'active',type:'string',defaultValue:querying.integrativeQuery.active.y},

    	{name:'createUser',type:'string'},
    	{name:'createDate',type:'date',convert: dateConvert,defaultValue:null}, 
    	{name:'modifyDate',type:'date',convert: dateConvert,defaultValue:null},
    	{name:'modifyUser',type:'string'}]
});
//货物轨迹 流水号
Ext.define('Foss.querying.integrativeQuery.FowlerDtoModel', {
	extend : 'Ext.data.Model',
	fields : [//打印流水号
      {name:'serialNo',type:'string'},
      //状态
      {name:'active',type:'string'},
      //运单号
      {name:'waybillNo',type:'string'},
      //创建时间
      {name:'createTime',type:'date',convert: dateConvert,defaultValue:null},
      //修改时间
      {name:'modifyTime',type:'date',convert: dateConvert,defaultValue:null},
      //开单时间
      {name:'billTime',type:'date',convert: dateConvert,defaultValue:null},
      //关联原单号流水号
      {name:'oldSerialNo',type:'string'},
      //件数变动事项
      {name:'numberChangItems',type:'string'},
      //是否开单初始值
      {name:'initalVale',type:'string'},
      //是否需要打木架
      {name:'isNeedWooden',type:'string'}]
});
