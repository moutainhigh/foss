﻿/**
 * @author foss-meiying
 * page:快递代理理签收偏线空运货物
 */
//运单开单件数
sign.expAirAgencySign.goodsQtyTotal = null;// 签收件数
sign.expAirAgencySign.deliverymanName = null;// 签收人
sign.expAirAgencySign.signSituation = null;// 签收情况
sign.expAirAgencySign.signNote = null;// 签收备注
sign.expAirAgencySign.PKP_CREDENTIAL_TYPE = 'PKP_CREDENTIAL_TYPE'; // 证件类型词条
sign.expAirAgencySign.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';//签收情况词条
sign.expAirAgencySign.PAYMENTMODE = 'SETTLEMENT__PAYMENT_TYPE'; // 付款方式词条
sign.expAirAgencySign.PICKUPGOODSAIR = 'PICKUPGOODSAIR'; // 派送方式词条
sign.expAirAgencySign.transportType = 'TRANS_TYPE';//运输方式词条
sign.expAirAgencySign.receiveCustomerName = null; //收货联系人
sign.expAirAgencySign.isOneInMoreSignQty = 1;//一票多件，按流水签收的签收件数
(function(){
	sign.expAirAgencySign.situationStore =FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PKP_SIGN_SITUATION, null);
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','GOODS_BACK'));//移除货物拉回
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_SIGN'));//移除异常签收
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_SAMEVOTEODD'));//移除同票多类异常
	
	sign.expAirAgencySign.situationStore1 =FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PKP_SIGN_SITUATION, null);
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','GOODS_BACK'));//移除货物拉回
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','UNNORMAL_SIGN'));//移除异常签收
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.expAirAgencySign.situationStore1.removeAt(sign.expAirAgencySign.situationStore1.find('valueCode','UNNORMAL_SAMEVOTEODD'));//移除同票多类异常
//	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_BREAK'));//移除异常-破损
//	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_DAMP'));//移除异常-潮湿
//	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_POLLUTION'));//移除异常-污染
//	sign.expAirAgencySign.situationStore.removeAt(sign.expAirAgencySign.situationStore.find('valueCode','UNNORMAL_ELSE'));//移除异常-其他
	// 还有一个拒收的等待添加基础资料
})();
//运输性质集合
Ext.define('Foss.sign.expAirAgencySign.TransportListStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
		    {'code':'ALL','name':sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.productCode.all')},//全部
			{'code':'PACKAGE','name':sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.productCode.package')},//标准快递
			{'code':'RCP','name':sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.productCode.rcp')},//3.60特惠件
			{'code':'EPEP','name':sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.productCode.epep')},//3.60特惠件
			{'code':'DEAP','name':sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.productCode.deap')}//快递商务件（航空）
		]
	},
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'memory',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'items'
		}
	}
});
//运单基本信息
Ext.define('Foss.sign.expAirAgencySign.model.WayBillInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
             //运单号
        {name: 'waybillNo'},
		{name: 'receiveCustomerName'},// 收货人(收货客户名称)
		{name: 'receiveCustomerAddress'},// 收货客户具体地址
		{name: 'receiveCustomerPhone'},// 收货客户电话
		{name: 'receiveCustomerMobilephone'},//收货人手机
		{name: 'arriveTime',type:'date',
			convert:dateConvert},// 到达时间
		{name: 'notificationResult',
			 convert:function(value){
				 if(value!=null && value == 'SUCCESS'){
					 return value;
				 }else {
					 return 'FAILURE';
				 }
			 }
		},// 是否通知成功
		{name: 'notificationTime',type:'date',
			convert:dateConvert},// 上次通知时间
		{name: 'toPayAmount', type : 'float'},// 到付总额
		{name: 'codAmount', type : 'float'},// 代收货款（开单时）
		{name: 'transportFee', type : 'float'},// 运费
		{name: 'deliveryGoodsFee', type : 'float'},// 送货费
		{name: 'insuranceFee', type : 'float'},// 保价费
		{name: 'paidMethod'},// 付款方式（出发部门）
		{name: 'otherFee', type : 'float'},// 其他费用
		{name: 'insuranceAmount', type : 'float'},// 货物价值
		{name: 'stockStatus'},//库存状态
		{name: 'receiveMethod',convert:function(value,record) {
			if(record.get('transportType')=='TRANS_VEHICLE' || record.get('transportType')=='TRANS_EXPRESS'){//汽运
				return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');			
			}else{//空运
				return FossDataDictionary.rendererSubmitToDisplay(value, sign.expAirAgencySign.PICKUPGOODSAIR);
			}
		}},// 派送方式
		{name: 'createTime',type:'date',
			convert:dateConvert},// 出发时间
		{name: 'deliveryCustomerCityName'},// 始发站
		{name: 'receiveOrgName'},// 始发部门
		{name: 'goodsName'},// 货物名称
		{name: 'isWholeVehicle'},// 是否整车运单
		{name: 'goodsQtyTotal' , type : 'int'},// 件数
		{name: 'goodsWeightTotal', type : 'float'},// 重量
		{name: 'goodsVolumeTotal', type : 'float'},// 体积
		{name: 'oldArriveNotoutGoodsQty', type : 'int'},// 签收前的到达未出库件数
		{name: 'goodsPackage'},// 包装
		{name: 'deliveryCustomerName'},// 发货人
		{name: 'transportType'},// 运输方式
		{name: 'isOneInMore'},//是否是一票多件
		{name: 'productCode'}// 运输性质
    ]
});
//待处理表格Model
Ext.define('Foss.sign.expAirAgencySign.model.WateDetailModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'waybillNo'},// 运单号
		{name: 'reasonCode'},//未执行系统签收原因编码
		{name: 'serialNo'},//流水号
        {name: 'externalBillNo'},//外发单号
        {name: 'expAirAgencySignOrgName'},//快递代理公司名称
        {name: 'expAirAgencySignOrgCode'},//快递代理公司编码
        {name: 'signGoodsQty'},//签收件数
        {name: 'deliverymanName'},//提货人名称
        {name: 'signTime', type: 'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},/* 签收时间*/
        {name: 'signSituation'},//签收情况
        {name: 'signNote'},//签收备注
		{name:'goodsQtyTotal'},//运单总件数
		{name : 'identifyType'},//证件类型
		{name : 'arriveNotoutGoodsQty'},//到达未出库件数
		{name : 'identifyCode'},//证件号码
		{name : 'isOneInMore'},//是否是一票多件
		{name: 'receiveCustomerName'} //签收信息提货人名称如果为空，则默认获取运单综合信息里收获联系人
    ]
});
//待处理表格Store
Ext.define('Foss.sign.expAirAgencySign.WateDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.sign.expAirAgencySign.model.WateDetailModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		timeout: 300000,
		actionMethods : 'POST',
		url:sign.realPath('queryExpressAgentSignByParams.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'airAgencySignVo.agencyQueryDtos'
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-expAirAgencySignIndex_content').getQueryForm().getValues();
			Ext.apply(operation,{
				params:{
					'airAgencySignVo.airAgencyQueryDto.waybillNo':queryParams.waybillNo,// 运单号
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerName':queryParams.receiveCustomerName,// 收货人(收货客户名称)
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerPhone':queryParams.receiveCustomerPhone,//  收货客户电话
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,// 收货人手机
//					'airAgencySignVo.airAgencyQueryDto.arriveTimeBegin':queryParams.arriveTimeBegin,//  到达时间起
//					'airAgencySignVo.airAgencyQueryDto.arriveTimeEnd':queryParams.arriveTimeEnd,// 到达时间止
					'airAgencySignVo.airAgencyQueryDto.successionTimingBegin':queryParams.arriveTimeBegin,//外发与外场交接时间起   268377
					'airAgencySignVo.airAgencyQueryDto.successionTimingEnd':queryParams.arriveTimeEnd,// 外发与外场交接时间止	268377
					'airAgencySignVo.airAgencyQueryDto.productCode':queryParams.productCode,// 运输性质
					'airAgencySignVo.airAgencyQueryDto.isExpress':'Y'// 是否快递
				}
			});
			
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), data.message, 'error', 3000);				
			}
		}
	}
});

//待处理表格
Ext.define('Foss.sign.expAirAgencySign.WateDetailGrid',{
	extend:'Ext.grid.Panel',
	title:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.pending'),//待处理
	//收缩
	collapsible: true,
	emptyText:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.emptyText'),//查询结果为空
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	//自动增加滚动条
	autoScroll:true,
	//高
	height: 622,//622
	store: null, 
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	viewConfig: {
        enableTextSelection: true
    },
	selModel : Ext.create('Ext.selection.CheckboxModel',{
		//checkOnly:true
	}),
	batchSignWindow : null,
	getBatchSignWindow : function(){
		if(this.batchSignWindow == null){
			this.batchSignWindow = Ext.create('Foss_sign_expAirAgencySign_BatchSignWindow');
		}
		return this.batchSignWindow;
	},
	columns: [
        { 	header: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'), //单号
        	align: 'center',
        	dataIndex: 'waybillNo',
        	flex: 1,
			renderer: function(value,cellmeta, record){
				//因敏感字符未推送的运单号标红
				var reasonCode =  record.data['reasonCode'];
				if (value && reasonCode ) {
					return '<span style="color:red;">'+value+ '</span>';
				}else if(value){
					return value;
				}
				return null;
			}
	    },
        { 
        	header:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.serialNo'), //流水号
        	align: 'center', 
        	dataIndex: 'serialNo', 
        	width : 80,
        	renderer: function(value){
	            if (value) {
	                return Ext.String.format('<a href="javascript:void(0)">{0}</a>',value);
	            }
	            return null;
	        } 
        },
        { 
        	header:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.externalBillNo'), //外发单号
        	align: 'center', 
        	dataIndex: 'externalBillNo', 
        	width : 120,
        	renderer: function(value,cellmeta, record){
				//因敏感字符未推送的外发单号标红
				var reasonCode =  record.data['reasonCode'];
	            if (value && reasonCode ) {
	                return Ext.String.format('<a href="javascript:void(0)">{0}</a>','<span style="color:red;">'+value+ '</span>');
	            }else if(value){
					return Ext.String.format('<a href="javascript:void(0)">{0}</a>',value);
				}
	            return null;
	        } 
        }
    ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.expAirAgencySign.WateDetailStore');
		me.bbar = ['->',{
			text: sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.selectBefore20Sign'),//选择前20条
			tabIndex: 2,
			id:'Foss_sign_expAirAgencySign_WateDetailGrid_selectBefore20SignButton_Id',
			textAlign : 'center',
			plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						// 设定间隔秒数,如果不设置，默认为2秒
						seconds : 3
			}),
			handler: function() {
				var  wateBatchSignGrid = Ext.getCmp('Foss_sign_expAirAgencySign_WateDetailGrid_ID');
				var  wateBatchSignGridStore = wateBatchSignGrid.getStore();
				var  wateBatchSignGridCount = wateBatchSignGridStore.getCount();
				var   wateBatchSignGridSelectionModel = wateBatchSignGrid.getSelectionModel( );
				if(wateBatchSignGridCount > 0 && wateBatchSignGridCount <= 20){
					wateBatchSignGridSelectionModel.selectAll();
				}else{
					wateBatchSignGridSelectionModel.selectRange(0,19);
				}
								
			}
		},{
			text: sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.batchSign'),//批量签收
			tabIndex: 1,
			id:'Foss_sign_expAirAgencySign_WateDetailGrid_batchSignButton_Id',
			textAlign : 'center',
			plugins : Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						// 设定间隔秒数,如果不设置，默认为2秒
						seconds : 3
			}),
			handler: function() {
				var  wateBatchSignGrid = Ext.getCmp('Foss_sign_expAirAgencySign_WateDetailGrid_ID'),
				batchSignRecodes=wateBatchSignGrid.getSelectionModel().getSelection();
				if(batchSignRecodes.length==0){
					Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.atlessChooseOne'), 'error', 3000);// "请至少选择一条记录！"
					return;
				}else if(batchSignRecodes.length>20){
					Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.maxLengthTwenty'), 'error', 3000);//批量签收最多只能选择20条！
					return;					
				}else {
					var  batchSignArrayList = new Array();
					for(var i = 0; i < batchSignRecodes.length; i ++){
						var AirAgencyQueryDto='';
						if(batchSignRecodes[i].get('signGoodsQty') ==null || batchSignRecodes[i].get('signGoodsQty') <=0){
							batchSignRecodes[i].data.signGoodsQty = batchSignRecodes[i].get('goodsQtyTotal');
						}
						//判断是否是一票多件，签收件数重新赋值
						if(!Ext.isEmpty(batchSignRecodes[i].get('isOneInMore'))){
							batchSignRecodes[i].data.signGoodsQty = sign.expAirAgencySign.isOneInMoreSignQty;
						}
						sign.expAirAgencySign.deliverymanName = batchSignRecodes[i].get('deliverymanName');
						if (!Ext.isEmpty(sign.expAirAgencySign.deliverymanName)) {//签收信息提货人名称如果为空，则默认获取运单综合信息里收获联系人
							if(batchSignRecodes[i].get('signSituation') == null || batchSignRecodes[i].get('signSituation') == ""){//如果签收情况为空,则默认为"正常签收"
								AirAgencyQueryDto ={'waybillNo':batchSignRecodes[i].get('waybillNo'),
																'serialNo':batchSignRecodes[i].get('serialNo'),
																'externalBillNo':batchSignRecodes[i].get('externalBillNo'),
																'signGoodsQty':batchSignRecodes[i].get('signGoodsQty'),
																'deliverymanName':batchSignRecodes[i].get('deliverymanName'),
																'signSituation':'NORMAL_SIGN',
																'signNote':'正常签收',
																'goodsQtyTotal':batchSignRecodes[i].get('goodsQtyTotal'),
																'arriveNotoutGoodsQty':batchSignRecodes[i].get('arriveNotoutGoodsQty'),
																'isOneInMore' :batchSignRecodes[i].get('isOneInMore'),//是否是一票多件
																'identifyType':'ID_CARD'};
							}else{
								AirAgencyQueryDto ={'waybillNo':batchSignRecodes[i].get('waybillNo'),
																'serialNo':batchSignRecodes[i].get('serialNo'),
																'externalBillNo':batchSignRecodes[i].get('externalBillNo'),
																'signGoodsQty':batchSignRecodes[i].get('signGoodsQty'),
																'deliverymanName':batchSignRecodes[i].get('deliverymanName'),
																'signSituation':batchSignRecodes[i].get('signSituation'),
																'goodsQtyTotal':batchSignRecodes[i].get('goodsQtyTotal'),
																'signNote':batchSignRecodes[i].get('signNote'),
																'arriveNotoutGoodsQty':batchSignRecodes[i].get('arriveNotoutGoodsQty'),
																'isOneInMore' :batchSignRecodes[i].get('isOneInMore'),//是否是一票多件
																'identifyType':'ID_CARD'};
							}
								
						}else{
							if(batchSignRecodes[i].get('signSituation') == null || batchSignRecodes[i].get('signSituation') == ""){//如果签收情况为空,则默认为"正常签收"
								AirAgencyQueryDto ={'waybillNo':batchSignRecodes[i].get('waybillNo'),
																'serialNo':batchSignRecodes[i].get('serialNo'),
																'externalBillNo':batchSignRecodes[i].get('externalBillNo'),						
																'signGoodsQty':batchSignRecodes[i].get('signGoodsQty'),
																'deliverymanName':batchSignRecodes[i].get('receiveCustomerName'),
																'signSituation':'NORMAL_SIGN',
																'signNote':'正常签收',
																'goodsQtyTotal':batchSignRecodes[i].get('goodsQtyTotal'),
																'arriveNotoutGoodsQty':batchSignRecodes[i].get('arriveNotoutGoodsQty'),
																'isOneInMore' :batchSignRecodes[i].get('isOneInMore'),//是否是一票多件
																'identifyType':'ID_CARD'};
							
							}else{
								AirAgencyQueryDto ={'waybillNo':batchSignRecodes[i].get('waybillNo'),
																'serialNo':batchSignRecodes[i].get('serialNo'),
																'externalBillNo':batchSignRecodes[i].get('externalBillNo'),						
																'signGoodsQty':batchSignRecodes[i].get('signGoodsQty'),
																'deliverymanName':batchSignRecodes[i].get('receiveCustomerName'),
																'signSituation':batchSignRecodes[i].get('signSituation'),
																'goodsQtyTotal':batchSignRecodes[i].get('goodsQtyTotal'),
																'signNote':batchSignRecodes[i].get('signNote'),
																'arriveNotoutGoodsQty':batchSignRecodes[i].get('arriveNotoutGoodsQty'),
																'isOneInMore' :batchSignRecodes[i].get('isOneInMore'),//是否是一票多件
																'identifyType':'ID_CARD'};
							}
						}
						batchSignArrayList.push(AirAgencyQueryDto);
					}
					var batchSignwindow =wateBatchSignGrid.getBatchSignWindow();
					batchSignwindow.show();
					var batchSignGrid =batchSignwindow.getBatchSignGridPanel();
					batchSignGrid.getStore().loadData(batchSignArrayList);
					batchSignGrid.getSelectionModel().selectAll(true);
				}				
			}
		}];
		me.callParent([cfg]);
	}
});
//签收信息
Ext.define('Foss.sign.expAirAgencySign.SignInfoForm',{
	extend: 'Ext.form.Panel',
	title : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signForInformation'), //签收信息
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'5 10 5 10',
		labelWidth:80
	},
	defaultType : 'textfield',
	layout:'column',
	items:[{
			name: 'deliverymanName',
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.deliverymanName'),//提货人名称
			allowBlank: false,
			maxLength:50,
			columnWidth: .33
		},{
			xtype: 'combobox',
			name:'identifyType',
			fieldLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.identifyType'),//证件
			columnWidth: .33,
			value:'',
			valueField:'valueCode', 
			displayField: 'valueName',
			editable:false,//不可编辑
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			queryMode:'local',
			allowBlank: true,//非必填项
			triggerAction:'all',
			store : FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PKP_CREDENTIAL_TYPE, null),
		    listeners : 
			{
			    'select' : function(combo, records, eOpts)
			    {
			    	var form = this.up('form').getForm();
			    	form.findField('identifyCode').focus();
			    }
			}
			
		},{
			name: 'identifyCode',
			fieldLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.identifyCode'),//证件号码
			allowBlank: true,//非必填项
			regex:/^[A-Za-z0-9,]{0,50}$/,
			columnWidth: .33
		},{
			xtype: 'combobox',
			name:'signSituation',
			fieldLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signSituation'),//签收情况
			columnWidth: .33,
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			allowBlank: false,
			editable:false,//不可编辑
			valueField:'valueCode', 
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			store : sign.expAirAgencySign.situationStore,
			listeners : {
		    	'select' : function(combo, records, eOpts){
		    		var form = this.up('form').getForm(),
		    			record = form.getRecord();
		    		form.findField('signNote').setValue(null);
		    		// 签收情况选择为部分签收时，签收件数显示为运单开单件数且可编辑
		    		if(records[0].get('valueCode') == 'PARTIAL_SIGN'){
		    			form.findField('signGoodsQty').setReadOnly(false);
		    		}else{
		    			if(Ext.isEmpty(sign.expAirAgencySign.isOneInMore)){
		    				form.findField('signGoodsQty').setValue(sign.expAirAgencySign.goodsQtyTotal);
		    			}else{
		    				form.findField('signGoodsQty').setValue(sign.expAirAgencySign.isOneInMoreSignQty);
		    			}
		    			form.findField('signGoodsQty').setReadOnly(true);
		    				
	    				if(records[0].get('valueCode') == 'NORMAL_SIGN'){//签收情况为正常签收，签收备注自动填写“正常签收”
	    					form.findField('signNote').setValue(records[0].get('valueName'));
	    				}
		    		}
		    	}
		    }
		},{
			xtype: 'numberfield',
	        anchor: '100%',
	        maxValue: 1000000,
			name: 'signGoodsQty',
			hideTrigger: true,
	        keyNavEnabled: false,
	        allowBlank: false,
			fieldLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signGoodsQty'),//签收件数
			columnWidth: .33,
			allowDecimals : false,//不允许有小数点
		    minValue: 1
		},{
			name:'signNote',
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signNote'),//签收备注
			allowBlank: false,
			maxLength:200,
			columnWidth: .66
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				xtype: 'container',
				border : false,
				columnWidth:.89	,
				html: '&nbsp;'
			},{
				text:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.sign'),//签收
				cls:'yellow_button',
				disabled:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexsignbutton'),
				hidden:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexsignbutton'),
				columnWidth:.11,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
				}),
				id:'Foss_sign_expAirAgencySign_SignInfoForm_submit_id',
				align : 'right',
				handler:function(){
					var form = this.up('form').getForm(),
					    WateDetailGrid = Ext.getCmp('T_sign-expAirAgencySignIndex_content').getBigDownPanel().getWateDetailGrid(),
					    selectRow = WateDetailGrid.getSelectionModel().getSelection();
					var  signForm = Ext.getCmp('Foss_sign_expAirAgencySign_SignInfoForm_Id').getForm().getValues();
					var  wayBillValue = Ext.getCmp('Foss_sign_expAirAgencySign_wayBillInfoForm_id').getForm(),
					    wayBillForm = wayBillValue.getRecord(),
					    lineSignDto = '';
					if (selectRow.length == 0||selectRow.length>1){
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.selectOne'),'error',1000);//请选择一行！
					}else if (selectRow[0].get('waybillNo')!=wayBillForm.data.waybillNo){
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.signRecordAgreement')+wayBillForm.data.waybillNo,'error',4000);
						return;
					}else if (!form.isValid()){
						
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.notcompleteInfo'),'error',2000);//部分信息未填写完整,请确认！
						return;
					}else{
						// 如果签收情况为部分签收，并且签收件数大于运单开单件数，系统弹窗提示错误
						if(form.findField('signSituation').getValue() == 'PARTIAL_SIGN'&&form.findField('signGoodsQty').
								getValue() >= sign.expAirAgencySign.goodsQtyTotal){
							Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signGoodsQtyNotGrade'),'error',2000);
							return;
						}
						if(!Ext.isEmpty(sign.expAirAgencySign.isOneInMore) && form.findField('signSituation').getValue() == 'PARTIAL_SIGN'){
							Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.noPartalSign'),'error',5000);
							return;
						}
						
						Ext.Msg.confirm(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.okTosubmit'), function(btn,text){
							if(btn == 'yes'){
								lineSignDto = {'waybillNo' : wayBillForm.data.waybillNo,//运单号
										'serialNo' : selectRow[0].get('serialNo'),//流水号
										'externalWaybillNo' : selectRow[0].get('externalBillNo'),//外发单号
										'productType' : wayBillForm.data.productCode,//运输性质
										'isWholeVehicle' : wayBillForm.data.isWholeVehicle//是否整车运单 
										};
								Ext.Ajax.request({
								    url:sign.realPath('addExpressWaybillSignResult.action'),
								    timeout: 60000,
								    jsonData: {
								    	'airAgencySignVo':{
											'waybillSignResultEntity':signForm,
											'lineSignDto':lineSignDto,
											'oldArriveNotoutGoodsQty':wayBillForm.data.oldArriveNotoutGoodsQty
										}
								    },
								    success: function(response){
								    	var json = Ext.decode(response.responseText);
								    	WateDetailGrid.store.remove(selectRow);
								    	Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),json.message,'ok',4000);
								    	form.reset(); 
								    	wayBillValue.reset();
								    },exception: function(response){
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.commitFailed'), json.message, 'error', 3000);
									}
							});
						}
					});
				}
			}
		}]
	}],
	initData: function(){
		var form = this.getForm(),
			signSituation = form.findField('signSituation'),
			signGoodsQty = form.findField('signGoodsQty'),
			signNote = form.findField('signNote');
		var value = signSituation.store.getAt(sign.expAirAgencySign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueCode');//签收情况 code
		var name = signSituation.store.getAt(sign.expAirAgencySign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueName');//签收情况的name
//		signSituation.setValue(value);//签收情况默认选中“全部签收”
		if(!Ext.isEmpty(sign.expAirAgencySign.isOneInMore)){//是一票多件时，签收件数是1
			signGoodsQty.setValue(sign.expAirAgencySign.isOneInMoreSignQty);
		}else{
			signGoodsQty.setValue(sign.expAirAgencySign.goodsQtyTotal);//签收件数默认为运单开单件数
		}
		signGoodsQty.setReadOnly(true);
		if (!Ext.isEmpty(sign.expAirAgencySign.deliverymanName)) {
			form.findField('deliverymanName').setValue(sign.expAirAgencySign.deliverymanName);// 签收人
		} else{ //如果签收信息中的提货人名称为空,则默认获取运单综合信息里收货联系人
			form.findField('deliverymanName').setValue(sign.expAirAgencySign.receiveCustomerName);
		}
		if (!Ext.isEmpty(sign.expAirAgencySign.signSituation)) {
			signSituation.setValue(sign.expAirAgencySign.signSituation);// 签收情况
		} else {
			signSituation.setValue(value);//签收情况默认选中“全部签收”
		}
		if (!Ext.isEmpty(sign.expAirAgencySign.signNote)) {
			signNote.setValue(sign.expAirAgencySign.signNote);// 签收备注
		} else {
			signNote.setValue(name);//备注
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.sign.expAirAgencySign.tipPanel', {
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

//运单基本信息
Ext.define('Foss.sign.expAirAgencySign.wayBillInfoForm',{
	extend: 'Ext.form.Panel',
	title:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillBasicInformation'),//运单基本信息
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'5 10 5 10',
		labelWidth:100,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout:'column',
	items:[{
		name:'receiveCustomerName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerName'),//收货人
		columnWidth: .33
	},{
		name:'receiveCustomerAddress',
		tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//当值为空的时候，不显示tip
			isShowByData: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			tipBodyElement: 'Foss.sign.expAirAgencySign.tipPanel'
		},
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerAddress'),//提货人地址
		columnWidth: .33
	},{
		name:'receiveCustomerPhone',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerPhone'),//收货人电话
		columnWidth: .33
	},{
		name:'waybillNo',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),//单号
		columnWidth: .33
	},{
		name:'arriveTime',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.arriveTime'),//到达时间
		xtype:'datefield',
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	},{
		name:'stockStatus',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.stockStatus'),//库存状态
		columnWidth: .33
	},{
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.notificationResultName'),//通知成功
		xtype: 'radiogroup',
        vertical: true,
		columnWidth:.33,
		anchorSize: 50,
		layout:'column',
		columns: 1,
		name: 'notificationResultName',
		items: [
            { boxLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.yes'), name: 'notificationResult', inputValue: 'SUCCESS', readOnly : true,  checked: true},//是
			{ xtype: 'container',border : false,columnWidth:.5	,html: '&nbsp;'},
            { boxLabel: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.no'), name: 'notificationResult', inputValue: 'FAILURE', readOnly : true}//否
        ]
		
	},{
		name:'notificationTime',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.notificationTime'),//上次通知时间
		xtype:'datefield',
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	},{
		name:'paidMethod',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.paidMethod'),//付款方式
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PAYMENTMODE, null)
	
	},{
		name:'toPayAmount',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.toPayAmount'),//到付总额
		columnWidth: .33
	},{
		name:'codAmount',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.codAmount'),//代收货款
		columnWidth: .33
	},{
		name:'transportFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.transportFee'),//运费
		columnWidth: .33
	},{
		name:'deliveryGoodsFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.deliveryGoodsFee'),//送货费
		columnWidth: .33
	},{
		name:'insuranceAmount',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.insuranceAmount'),//货物价值
		columnWidth: .33
	},{
		name:'insuranceFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.insuranceFee'),//保价费
		columnWidth: .33
	},{
		name:'otherFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.otherFee'),//其他费用
		columnWidth: .33
	},{
		name:'receiveMethod',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveMethod'),//派送方式
		columnWidth: .33
	},{
		name:'goodsName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.goodsName'),//货物名称
		columnWidth: .33
	},{
		name:'goodsQtyTotal',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.goodsQtyTotal'),//件数
		columnWidth: .33
	},{
		name:'goodsWeightTotal',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.goodsWeightTotal'),//重量
		columnWidth: .33
	},{
		name:'goodsVolumeTotal',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.goodsVolumeTotal'),//体积
		columnWidth: .33
	},{
		name:'goodsPackage',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.goodsPackage'),//包装
		columnWidth: .33
	},{
		name:'deliveryCustomerName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.deliveryCustomerName'),//发货人
		columnWidth: .33
	},{
		name:'deliveryCustomerCityName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.deliveryCustomerCityName'),//始发站
		columnWidth: .33
	},{
		name:'receiveOrgName',
		tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//当值为空的时候，不显示tip
			isShowByData: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			tipBodyElement: 'Foss.sign.expAirAgencySign.tipPanel'
		},
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveOrgName'),//始发部门
		columnWidth: .33
	},{
		name:'transportType',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.transportType'),//运输类型
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.transportType, null)
	},{
		columnWidth: .33,
		xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:true,
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.productCode'),//运输性质
		name:'productCode',
		store: Ext.create('Foss.sign.expAirAgencySign.TransportListStore')
	},{
		xtype:'datefield',
		name:'createTime',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.createTime'),//出发时间
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	}]
});
//偏线外发单表单信息Model
Ext.define('Foss.sign.expAirAgencySign.model.PartiallineModel', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo',type:'string'},//运单号
		{name: 'externalBillNo',type:'string'},//外发单号			
		{name: 'externalUserName', type: 'string'},//外发员
		{name: 'externalAgencyFee', type: 'float',defaultValue: 0},//外发代理费（代收货款）
		{name: 'deliveryFee', type: 'float',defaultValue: 0},//付送货费（外发运费）
		{name: 'costAmount', type: 'float',defaultValue: 0},//外发成本总额（到付金额）
		{name: 'receiveAgencyFee', type: 'float',defaultValue: 0},//实收代理费(应用费用)
		{name: 'payAgencyFee', type: 'float',defaultValue: 0},//实付代理费（应付代理）
//		{name: 'isWriteOff', type: 'string',
//			convert: function(value) {
//				if (value == 'Y') {
//					return sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.yes');//是
//				} else if(value == 'N'){
//					return sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.no');//否
//				}else {
//					return null;
//				}
//			}
//		},//自动核销申请	
		{name: 'notes', type: 'string'},//备注
		{name: 'transferExternal', type: 'string',
			convert: function(value) {
				if (value == 'Y') {
					return sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.yes');//是
				} else if(value == 'N'){
					return sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.no');//否
				}else {
					return null;
				}
			}
		},//中转外发
		{name: 'currencyCode', type: 'string'},//币种
		{name: 'paidMethod', type: 'string'},//付款方式
		{name: 'toPayAmount', type: 'float',defaultValue: 0},//到付金额
		{name: 'agentCompanyName', type: 'string'},//外发代理
		{name: 'agentDeptName', type: 'string'},//到达网点
		{name: 'contactPhone', type: 'string'},//到达网点电话
		{name: 'address', type: 'string'},//到达网点地址
		{name: 'externalOrgName', type: 'string'},//外发部门	
		{name: 'registerUser', type: 'string'},//录入人（修改人）
		{name: 'registerTime', type: 'date',
			convert: function(value) {
				if (value != null) {
					var date = new Date(value);
					return Ext.Date.format(date,'Y-m-d H:i:s');
				} else {
					return null;
				}
			}
		}//录入日期
	]
});
//偏线外发单表单信息
Ext.define('Foss.sign.expAirAgencySign.ViewpartiallineFormPanel',{
	extend: 'Ext.form.Panel',
	title:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.theViewPartialLineBilled'),//查看快递代理外发单信息
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		margin:'5 5 5 5',
		labelWidth:90,
		readOnly : true
	},
	defaultType : 'textfield',
	//自动收缩高度
	cls:'autoHeight',
	bodyCls:'autoHeight',
	layout:'column',
	items:[{
		name:'waybillNo',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),//运单号
		columnWidth: .33
	},{
		name:'externalBillNo',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.externalBillNo'),//外发单号
		columnWidth: .33
	},{
		name:'externalUserName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.externalUserName'),//外发员
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'externalAgencyFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.externalAgencyFee'),//外发代理运费（代收货款）
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'deliveryFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.deliveryFee'),//付送货费（外发运费）
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'costAmount',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.costAmount'),//外发成本总额（到付金额）
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'receiveAgencyFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.receiveAgencyFee'),//实收代理费(应用费用)
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'payAgencyFee',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.payAgencyFee'),//实付代理费（应付代理）
		columnWidth: .33
	},/*{
		name:'isWriteOff',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.isWriteOff'),//自动核销申请
		columnWidth: .33
	}*/,{
		name:'notes',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.notes'),//备注
		height:60,
		columnWidth: .99
	},{
		name:'transferExternal',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.transferExternal'),//中转外发
		columnWidth: .33
	},{
		name:'currencyCode',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.currencyCode'),//币种
		columnWidth: .33
	},{
		name:'paidMethod',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.paidMethod'),//付款方式
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PAYMENTMODE, null)
	
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'toPayAmount',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.toPayAmount'),//到付金额
		columnWidth: .33
	},{
		name:'agentCompanyName',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.agentCompanyName'),//外发代理
		columnWidth: .33
	},{
		name:'agentDeptName',
		tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'Foss.sign.expAirAgencySign.tipPanel'
		},
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.agentDeptName'),//到达网点
		columnWidth: .33
	},{
		name:'contactPhone',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.contactPhone'),//到达网点电话
		columnWidth: .33
	},{
		name:'address',
		tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'Foss.sign.expAirAgencySign.tipPanel'
		},
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.address'),//到达网点地址
		columnWidth: .33
	},{
		name:'externalOrgName',
		tipConfig: {
			cls: 'autoHeight',
			bodyCls: 'autoHeight',
			width: 150,
			//是否随鼠标滑动
			trackMouse: true,
			//普通Form上必须配置tipType(区分普通Form和行展开的Form)
			tipType: 'normal',
			//当值为空的时候，不显示tip
			isShowByData: true,
			tipBodyElement: 'Foss.sign.expAirAgencySign.tipPanel'
		},
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.externalOrgName'),//外发部门
		columnWidth: .33
	},{
		name:'registerUser',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.registerUser'),//录入人（修改人）
		columnWidth: .33
	},{
		xtype: 'datefield',
		fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.registerTime'),//录入日期
		name: 'registerTime',
		format:'Y-m-d H:i:s',
		readOnly:true,
		columnWidth:.33	
	}]
});
//偏线外发单表单panel
sign.expAirAgencySign.ViewpartiallineFormPanel = Ext.create('Foss.sign.expAirAgencySign.ViewpartiallineFormPanel');
//查看偏线外发单表单
Ext.define('Foss.sign.expAirAgencySign.Viewpartialline', {
	extend : 'Ext.window.Window',
	//id:'Foss_sign_airAgencySign_Viewpartialline_ID',
	title: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.theViewPartialLineBilled'),//查看偏线外发单
	width: 840,	//宽度
	//bodyCls: 'autoHeight',//高度自适应
	layout: 'auto',	 
	closeAction : 'hide',
	resizable : false,
	modal : 'true',
	items : [sign.expAirAgencySign.ViewpartiallineFormPanel],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});
//偏线外发单表单window
sign.expAirAgencySign.ViewpartiallineWindow = Ext.create('Foss.sign.expAirAgencySign.Viewpartialline');
Ext.define('Foss.sign.expAirAgencySign.model.batchSignModel', {
		extend : 'Ext.data.Model',
		fields : [
		{name:'goodsQtyTotal'},//运单总件数
		{name : 'arriveNotoutGoodsQty'},//到达未出库件数
		{name: 'waybillNo'},// 运单号
		{name: 'serialNo'},// 流水号
		{name: 'externalBillNo'},// 外发单号
        {name: 'signGoodsQty'},//签收件数
        {name: 'deliverymanName'},//提货人名称
        {name: 'signSituation',convert:function(value,record) {
			if(value){
				if(value=='UNNORMAL_ABANDONGOODS' ||value=='GOODS_BACK'||value=='UNNORMAL_SIGN'
				||value=='UNNORMAL_LOSTCARGO'||value=='UNNORMAL_CONTRABAND'){
					return null;
				}else{
					var rendervalue=FossDataDictionary.rendererSubmitToDisplay(value, sign.expAirAgencySign.PKP_SIGN_SITUATION);
					if(rendervalue==value){
						return null;
					}else{
						return value;
					}
				} 		
			}else{
				return null;
			}
		}},//签收情况
		{name : 'identifyType'},//证件类型
		{name : 'identifyCode'},//证件号码
		{name : 'isOneInMore'},//是否是一票多件
        {name: 'signNote'}//签收备注
		]
		});
 Ext.define('Foss_sign_expAirAgencySign_model_BatchSignStore', {
		extend:'Ext.data.Store',
		model : 'Foss.sign.expAirAgencySign.model.batchSignModel',
		storeId : 'Foss_sign_expAirAgencySign_model_BatchSignStoreID',
		data:[]
	});
Ext.define('Foss_sign_expAirAgencySign_batchSignGrid', {
		extend:'Ext.grid.Panel',
		bodyCls : 'autoHeight',
		//收缩
		collapsible: true,
		//是否有框
		frame:true,
		//动画收缩
		animCollapse: true,
		//自动增加滚动条
		autoScroll:true,
		plugins:Ext.create('Ext.grid.plugin.CellEditing', {  // 对单元格级别进行编辑
			clicksToEdit : 1//// 设置鼠标点击多少次，触发编辑
			//isObservable : false
			
		}) ,
		selModel : Ext.create('Ext.selection.CheckboxModel',{
			checkOnly:true
		}),
		columns : [{
			hidden : true,
			//xtype:'hidden', 如果这样写　会导致window报错，且打不开。无该属性
			dataIndex : 'goodsQtyTotal'
		},{
			hidden : true,
			dataIndex : 'arriveNotoutGoodsQty'
		},{
			text : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),// 运单号
			width : 95,
			dataIndex : 'waybillNo'
		}, {
			text :sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signGoodsQty'),//签收件数
			dataIndex : 'signGoodsQty',
			width : 80,
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'numberfield',
				minValue: 1, //最小值为1
				allowDecimals : false,
				allowBlank: false//必填项	
			}
			
		}, {
			text : sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.deliverymanName'),//提货人姓名
			width : 90,
			dataIndex : 'deliverymanName',
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'textfield',
				allowBlank: false,//必填项
				maxLength:50
			}
		}, {
			text : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signSituation'),//签收情况
			width : 100,
			dataIndex : 'signSituation',
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'combobox',
				store:sign.expAirAgencySign.situationStore1,
				allowBlank: false,//必填项
				valueField:'valueCode', 
				forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
				editable:false,//不可编辑
				triggerAction:'all',
				displayField: 'valueName',
				queryMode:'local'//如果不加这句话，就查的是签收情况里所有的项
				
			},
			renderer:function(value){  
                return FossDataDictionary.rendererSubmitToDisplay(value, sign.expAirAgencySign.PKP_SIGN_SITUATION);  
              }
		}, {
			text : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.signNote'),//签收备注
			width : 120,
			dataIndex : 'signNote',
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'textfield',
				maxLength:200
			}
		}, {
			text : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.identifyType'),//证件类型
			width : 100,
			dataIndex : 'identifyType',
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'combobox',
				valueField:'valueCode', 
				displayField: 'valueName',
				queryMode:'local',
				store : FossDataDictionary.getDataDictionaryStore(sign.expAirAgencySign.PKP_CREDENTIAL_TYPE, null)
				
			},
			renderer:function(value){  
               return FossDataDictionary.rendererSubmitToDisplay(value, sign.expAirAgencySign.PKP_CREDENTIAL_TYPE);  
              }
		}, {
			text : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.identifyCode'),//证件号码
			flex : 0.5,
			dataIndex : 'identifyCode',
			editor : {
				// 定义编辑框的类型，编辑框为数字类型
				xtype : 'textfield',
				regex:/^[A-Za-z0-9,]{0,50}$/
				//maxLength:50	
			}
		}],constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss_sign_expAirAgencySign_model_BatchSignStore');
			me.callParent([cfg]);
		}
	});
Ext.define('Foss_sign_expAirAgencySign_BatchSignWindow',{
	extend:'Ext.window.Window',
	title: '查看签收信息',
	width: 940,	//宽度
	closeAction: 'close',
	layout: 'auto',	
	resizable : false,	
	//id:'Foss_sign_expAirAgencySign_BatchSignWindow_Id',
	modal : 'true',
	batchSignGridPanel : null,
	getBatchSignGridPanel : function(){
		if(this.batchSignGridPanel==null){
			this.batchSignGridPanel = Ext.create('Foss_sign_expAirAgencySign_batchSignGrid');
		}
		return this.batchSignGridPanel;
	},
	buttons:[{
		cls : 'yellow_button',
		text :sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.sign'),//签收
		//hidden:!predeliver.notifyCustomer.isPermission('notifycustomerindex/notifycustomerindexnotifybutton'),
		plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
			seconds: 3
		}),
		handler:function(){
			var batchSignGrid = this.up('window').getBatchSignGridPanel(),
			selectRow=batchSignGrid.getSelectionModel().getSelection();
			if (selectRow.length == 0){
				Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.selectOne'),'error',1000);//请选择一行！
			}else{
				var containArray = new Array(),//用于删除成功签收的数据
				 WateDetailGrid = Ext.getCmp('T_sign-expAirAgencySignIndex_content').getBigDownPanel().getWateDetailGrid(),
				 selectWateDetailRecords=WateDetailGrid.getSelectionModel().getSelection(),
				 selectWateDetailRecordsLength=selectWateDetailRecords.length;
				
				for ( var j = 0; j < selectRow.length; j++) {
					if(Ext.isEmpty(selectRow[j].get('signSituation'))){
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),'签收情况不能为空！请选择签收情况！','error',5000);
							return;
					}
				// 如果签收情况为部分签收，并且签收件数大于运单开单件数，系统弹窗提示错误
					if(selectRow[j].get('signSituation')=='PARTIAL_SIGN'){
						if(selectRow[j].get('signGoodsQty')>= selectRow[j].get('goodsQtyTotal')){
							Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.waybillNo')+selectRow[j].get('waybillNo')+sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.signGoodsQtyLessGoodsQty')+selectRow[j].get('goodsQtyTotal'),'error',5000);
							return;
						}
						if(!Ext.isEmpty(selectRow[j].get('isOneInMore'))){//一票多件，按流水签收没有部分签收
							Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),selectRow[j].get('waybillNo')+":"+selectRow[j].get('serialNo')+sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.noPartalSign'),'error',5000);
							return;
						}
					}else{
						if(Ext.isEmpty(selectRow[j].get('isOneInMore'))){
							if(selectRow[j].get('signGoodsQty')!= selectRow[j].get('goodsQtyTotal')){
								Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.waybillNo')+selectRow[j].get('waybillNo')+sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.signGoodsQtyEqualGoodsQty')+selectRow[j].get('goodsQtyTotal'),'error',5000);
								return;
							}
						}else{
							if(selectRow[j].get('signGoodsQty')!= sign.expAirAgencySign.isOneInMoreSignQty){
								Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.limitIsOneInMoreSignQty'),'error',5000);
								return;
							}
						}
					}
					for(k = 0;k < selectWateDetailRecordsLength; k ++){
						if(selectRow[j].get('waybillNo') == selectWateDetailRecords[k].data.waybillNo){
							containArray.push(selectWateDetailRecords[k]);
						}
					}	
					if(Ext.isEmpty(selectRow[j].get('deliverymanName'))){
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),'提货人姓名不能为空！请录入提货人信息','error',5000);
							return;
					}
					if(Ext.isEmpty(selectRow[j].get('signNote'))){
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),'签收备注信息不能为空！','error',5000);
							return;
					}
					
				}
				Ext.Msg.confirm(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.okTosubmit'), function(btn,text){
					if(btn == 'yes'){
						var  signForm = Ext.getCmp('Foss_sign_expAirAgencySign_SignInfoForm_Id').getForm().getValues();
						var batchSignDtos=new Array();
						for ( var i = 0; i < selectRow.length; i++) {
							var batchSignDto =new Object();
							var waybillSignResultEnitty  =
							{'waybillNo' : selectRow[i].get('waybillNo'),//运单号
									'serialNo' : selectRow[i].get('serialNo'),//流水号
									'externalWaybillNo' : selectRow[i].get('externalBillNo'),//外发单号
									'signGoodsQty' :selectRow[i].get('signGoodsQty'),//签收件数
									'deliverymanName' :selectRow[i].get('deliverymanName'),//提货人名称
									'signSituation' :selectRow[i].get('signSituation'),//签收情况
									'identifyType' :selectRow[i].get('identifyType'),//证件类型
									'identifyCode' :selectRow[i].get('identifyCode'),//证件号码
									'signNote' :selectRow[i].get('signNote')//签收备注
									};
							batchSignDto.waybillSignResultEntity=waybillSignResultEnitty;
							batchSignDto.oldArriveNotoutGoodsQty =selectRow[i].get('arriveNotoutGoodsQty');
							batchSignDtos.push(batchSignDto);
						
						}
						Ext.Ajax.request({
							url:sign.realPath('batchAddExpressSign.action'),
							timeout: 600000,
							jsonData: {
								'airAgencySignVo':{'batchSignDtos':batchSignDtos}
							},
							success: function(response){
								var json = Ext.decode(response.responseText);
								WateDetailGrid.store.remove(containArray);
								Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'),json.message,'ok',4000);
								WateDetailGrid.getBatchSignWindow().close();
							},exception: function(response){
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.commitFailed'), json.message, 'error', 3000);
							}
						});
					}
				});
			}
		}
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getBatchSignGridPanel()];				
			me.callParent([cfg]);
	}	
});
//下面大panel
Ext.define('Foss.sign.expAirAgencySign.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	WateDetailGrid : null,
	getWateDetailGrid :function(){
		var me = this;
		if(this.WateDetailGrid==null){
			this.WateDetailGrid = Ext.create('Foss.sign.expAirAgencySign.WateDetailGrid',{
				columnWidth:.25,
				id:'Foss_sign_expAirAgencySign_WateDetailGrid_ID',
				listeners: {
					cellclick:  function(table, td, cellIndex, record, tr, rowIndex, e, eOpts){
						if(cellIndex==1){
							var wayBillInfoForm = me.getWayBillInfoForm();
							       signInfoForm = me.getSignInfoForm();
							       signInfoForm.getForm().reset();//重置签收信息
							       wayBillInfoForm.getForm().reset();//重置运单基本信息
							Ext.Ajax.request({
							    url:sign.realPath('queryByWaybillNo_EXP.action'),
							    params: {
							    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo'),
							    	'airAgencySignVo.airAgencyQueryDto.serialNo': record.get('serialNo')
							    },
							    success: function(response){
							    	signInfoForm.query('button')[0].setDisabled(false);//设置提交按钮可编辑
							    	var result = Ext.decode(response.responseText);
							    	//得到运单基本信息
									var wayBillInfoModel = Ext.ModelManager.create(result.airAgencySignVo.waybillDto,
											 'Foss.sign.expAirAgencySign.model.WayBillInfoModel');
									//加载运单基本信息
									wayBillInfoForm.loadRecord(wayBillInfoModel);
									//运单开单件数
									sign.expAirAgencySign.goodsQtyTotal=result.airAgencySignVo.waybillDto.goodsQtyTotal;
								    sign.expAirAgencySign.deliverymanName = record.data.deliverymanName;// 签收人
								    sign.expAirAgencySign.signSituation = record.data.signSituation;// 签收情况
								    sign.expAirAgencySign.signNote = record.data.signNote;// 签收备注
								    sign.expAirAgencySign.receiveCustomerName = result.airAgencySignVo.waybillDto.receiveCustomerName //收货联系人
								    sign.expAirAgencySign.isOneInMore = result.airAgencySignVo.waybillDto.isOneInMore //是否是一票多件
									//库存状态为已出库
									wayBillInfoForm.getForm().findField('stockStatus').setValue(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.outOfStock'));//已出库
									//加载签收信息
									signInfoForm.initData();
							    },exception: function(response){
							    	signInfoForm.query('button')[0].setDisabled(true);//设置提交按钮不可编辑
						    		//运单开单件数
									sign.expAirAgencySign.goodsQtyTotal = 0;
									//库存状态为""
									wayBillInfoForm.getForm().findField('stockStatus').setValue("");
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), json.message, 'error', 3000);
								}
							});
						}else if(cellIndex==2){
							//得到外发单号
							var externalBillNo = record.get('externalBillNo');
							//得到运单号
							var waybillNo = record.get('waybillNo');
							//如果外发单号不为空
							if(!Ext.isEmpty(externalBillNo)){
								var wayBillInfoForm = me.getWayBillInfoForm();
							       signInfoForm = me.getSignInfoForm();
							       signInfoForm.getForm().reset();//重置签收信息
							       wayBillInfoForm.getForm().reset();//重置运单基本信息
								if(!Ext.isEmpty(waybillNo)){
									Ext.Ajax.request({
										timeout: 60000,
									    url:sign.realPath('queryExpressExternalBillByNo.action'),
									    params: {
									    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo'),
									    	'airAgencySignVo.airAgencyQueryDto.serialNo': record.get('serialNo')
									    },
									    success: function(response){
									    	var result = Ext.decode(response.responseText);
									    	//得到偏线外发单表单基本信息
											var partiallineModel = Ext.ModelManager.create(result.airAgencySignVo.externalBillInfoDto,
													 'Foss.sign.expAirAgencySign.model.PartiallineModel');
											//加载运单基本信息
											sign.expAirAgencySign.ViewpartiallineFormPanel.loadRecord(partiallineModel);
											sign.expAirAgencySign.ViewpartiallineWindow.show();
											
									    }
									});
									Ext.Ajax.request({
										timeout: 60000,
									    url:sign.realPath('queryByWaybillNo_EXP.action'),
									    params: {
									    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo')
									    },
									    success: function(response){
									    	signInfoForm.query('button')[0].setDisabled(false);//设置提交按钮可编辑
									    	var result = Ext.decode(response.responseText);
									    	//得到运单基本信息
											var wayBillInfoModel = Ext.ModelManager.create(result.airAgencySignVo.waybillDto,
													 'Foss.sign.expAirAgencySign.model.WayBillInfoModel');
											//加载运单基本信息
											wayBillInfoForm.loadRecord(wayBillInfoModel);
											//运单开单件数
											sign.expAirAgencySign.goodsQtyTotal=result.airAgencySignVo.waybillDto.goodsQtyTotal;
											sign.expAirAgencySign.deliverymanName = record.data.deliverymanName;// 签收人
										    sign.expAirAgencySign.signSituation = record.data.signSituation;// 签收情况
										    sign.expAirAgencySign.signNote = record.data.signNote;// 签收备注
										    sign.expAirAgencySign.receiveCustomerName = result.airAgencySignVo.waybillDto.receiveCustomerName //收货联系人
										    sign.expAirAgencySign.isOneInMore = result.airAgencySignVo.waybillDto.isOneInMore //是否是一票多件
											//库存状态为已出库
											wayBillInfoForm.getForm().findField('stockStatus').setValue(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.outOfStock'));//已出库
											//加载签收信息
											signInfoForm.initData();
									    },exception: function(response){
									    	signInfoForm.query('button')[0].setDisabled(true);//设置提交按钮不可编辑
								    		//运单开单件数
											sign.expAirAgencySign.goodsQtyTotal = 0;
											//库存状态为""
											wayBillInfoForm.getForm().findField('stockStatus').setValue("");
											var json = Ext.decode(response.responseText);
					              			Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), json.message, 'error', 3000);
										}
									});
								}
							}
						
						}
						
					}
				}
			});
		}
		return this.WateDetailGrid;
	},
	signInfoForm : null,
	getSignInfoForm : function(){
		if(this.signInfoForm==null){
			this.signInfoForm = Ext.create('Foss.sign.expAirAgencySign.SignInfoForm',{
				id:'Foss_sign_expAirAgencySign_SignInfoForm_Id'
			});
		}
		return this.signInfoForm;
	},
	wayBillInfoForm : null,
	getWayBillInfoForm : function(){
		if(this.wayBillInfoForm==null){
			this.wayBillInfoForm = Ext.create('Foss.sign.expAirAgencySign.wayBillInfoForm',{
				id:'Foss_sign_expAirAgencySign_wayBillInfoForm_id'
			});
		}
		return this.wayBillInfoForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.selModel = Ext.create('Ext.selection.CellModel',{
			mode: 'SINGLE'
		});
		me.items = [me.getWateDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.75,
			items : [
				me.getSignInfoForm(),me.getWayBillInfoForm()
			]
		}];
		me.callParent([cfg]);
	}
});
			
//签收偏线空运货物查询    查询条件
Ext.define('Foss.sign.expAirAgencySign.QueryPanel', {
		extend:'Ext.form.Panel',
		// 指定容器的标题
		title: sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.queryBuilder'),//查询条件
		frame:true,
		//收缩
		collapsible: true,
		//动画收缩
		animCollapse: true, 
		bodyCls: 'autoHeight',
		cls: 'autoHeight',
		//默认边距 间隔
		defaults: {
			margin: '5 10 5 10',   //四边距  上右下左
			labelWidth: 95
		},
		layout: 'column',
		//默认控件是'textfield'
		defaultType: 'textfield',
		
		// 定义容器中的项
		items: [{
			fieldLabel : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'), //运单号,
			name : 'waybillNo',
			labelWidth : 60,
			xtype : 'textarea',
			emptyText : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo.valitation'),
			regex : /^([0-9]{10}\n?)+$/i,
			regexText : sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo.valitation'),
			columnWidth: .25
		},{
			name: 'receiveCustomerName',//收货人
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerName'),
			columnWidth: .25
		},{
			name:'receiveCustomerPhone',//收货人电话
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerPhone'),
			columnWidth: .25
		},{
			name:'receiveCustomerMobilephone',//收货人手机
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerMobilephone'),
			columnWidth: .25
		},{
			xtype:'combobox',
			name:'productCode',
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.productCode'),//运输性质
			columnWidth: .25,
			allowBlank: false,
			editable:false,//不可编辑
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			valueField:'code', 
			displayField: 'name',
			queryMode:'local',
			triggerAction:'all',
			store: Ext.create('Foss.sign.expAirAgencySign.TransportListStore'),
			value:'ALL'
		},{
			xtype: 'rangeDateField',
			fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.successionTiming'),//到达时间 -->变更为外场与外发交接时间 2015-07-25 268377 yl
			fieldId:'Foss_sign_expQueryPanel_rangeDateField_ID',
			fromEditable:false,
			toEditable :false,
			disallowBlank:true,
			//dateRange: 30,
			dateType: 'datetimefield_date97',
			fromName: 'arriveTimeBegin',
			toName: 'arriveTimeEnd',
			fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,0,0,0),'Y-m-d H:i:s'),
			toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
					,23,59,59),'Y-m-d H:i:s'),
			labelWidth: 130,
			columnWidth: .49
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.reset'),//重置
				columnWidth:.08,
				handler:function(){
					var myform = this.up('form').getForm();
						myform.reset(); 
						//到达时间默认为当前系统时间0:00至23:59
						myform.findField('arriveTimeBegin').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
								,0,0,0),'Y-m-d H:i:s'));
						
						myform.findField('arriveTimeEnd').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
								,23,59,59),'Y-m-d H:i:s'));
				}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.47,
			html: '&nbsp;'
		},{
			text:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.downloadImport'),//下载导入模板
			cls:'yellow_button',
			disabled:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysigndownloadimportquerybutton'),
			hidden:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysigndownloadimportquerybutton'),
			columnWidth:.11,
			handler :function(){
				//window.open(sign.realPath('exportImportModel.action'));
    			filePath();//jsp页面写的javascript方法获取服务器上的文件路径，并下载
			} 
		},{
			xtype: 'container',
			border : false,
			columnWidth:.10,
			html: '&nbsp;'
		},{
			text:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.import'),//导入
			cls:'yellow_button',
			disabled:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			hidden:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			columnWidth:.08,
			handler :function(){
				Ext.create('Foss.sign.expAirAgencySign.ImportSingFile').show();
			} 
		},{
			xtype: 'container',
			border : false,
			columnWidth:.08,
			html: '&nbsp;'
		},{
			text:sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.search'),//查询
			cls:'yellow_button',
			disabled:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			hidden:!sign.expAirAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm(),
					signInfoForm = Ext.getCmp('Foss_sign_expAirAgencySign_SignInfoForm_Id').getForm(),
				    waybillInfoForm = Ext.getCmp('Foss_sign_expAirAgencySign_wayBillInfoForm_id').getForm();
				// 到达时间验证
				var arriveTimeBegin = form.getValues().arriveTimeBegin, arriveTimeEnd = form.getValues().arriveTimeEnd;
				if (!Ext.isEmpty(arriveTimeBegin) && !Ext.isEmpty(arriveTimeEnd)) {	
					var result = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');	
					if(result / (24 * 60 * 60 * 1000) >= 2){	
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
						return;	
					}	
				}else {//如果到达时间为空
					Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.arriveTimeNotNull'), 'error', 3000);
						return;
				}
				
				// 验证运单号输入的行数
				var waybillNoes = form.getValues().waybillNo;
				if (!Ext.isEmpty(waybillNoes)) {
					var arrayWaybillNo = waybillNoes.split('\n');
					if (arrayWaybillNo.length > 50) {
						Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.notifyCustomer.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo.valitation'), 'error', 3000); // 
						return;	
					}
					for (var i = 0; i < arrayWaybillNo.length; i++) {
						if (Ext.isEmpty(arrayWaybillNo[i])) {
							Ext.ux.Toast.msg(sign.expAirAgencySign.i18n('pkp.sign.notifyCustomer.tip'), sign.expAirAgencySign.i18n('pkp.sign.airAgencySign.waybillNo.valitation'), 'error', 3000); // 运单号不能录入空回车
							return;	
						}
					}
				}
				 if (!form.isValid()){
					return;
				}
				signInfoForm.reset();//签收信息重置
			    waybillInfoForm.reset();//运单基本信息重置
				sign.expAirAgencySign.goodsQtyTotal = null;
				var vateDetailGrid = Ext.getCmp('Foss_sign_expAirAgencySign_WateDetailGrid_ID'),
				vateDetailGridStore=vateDetailGrid.getStore();
				vateDetailGrid.getSelectionModel().deselectAll();
				vateDetailGridStore.removeAll();
				vateDetailGridStore.load();
			}
		}]
	}]
	});


sign.expAirAgencySign.showInfoMes = function(message,fun) {
	var len = message.length;
	Ext.Msg.show({
	    title:'FOSS提醒您:',//title:pricing.util.i18n('i18n.pricing-util.fossAlert'),
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
};


//导入文件弹出框
Ext.define('Foss.sign.expAirAgencySign.ImportSingFile',{
	extend:'Ext.window.Window',
	title:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.import.title'),//快递代理签收运单
	layout:{
		type:'vbox',
		align:'stretch'
	},
	width:400,
	height:150,
	closeAction:'hide',
	listeners:{
		beforehide:function(me){
			var form = me.down('form');
			form.getForm().findField('uploadFile').reset();
		}
	},
	parent:null,//（Foss.pricing.PlatformGrid）
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [
		{
			xtype:'form',
			flex:1,
			layout:{
				type : 'hbox'
			},
			defaults : {
				margins : '0 5 0 0'
			},
			items:[{
				xtype:'filefield',
				name:'uploadFile',
				fieldLabel:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.pleaseSelectAttachments'),//请选择附件
				labelWidth:100,
				buttonText:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.browse'),//浏览
				flex:3,
				listeners:{
					'change' : function(fb, v) {
						var pattern = /^.+?\.(xls)$/,
							pattern2 = /^.+?\.(xlsx)$/;
						if(!pattern.test(v) && !pattern2.test(v)) {
							Ext.Msg.alert('警告', "请导入[.xls]或[.xlsx]后缀的excel文件!");
						}	
					}
		    	}
			}]
		}];
		me.fbar = me.getFbar();
		me.callParent([cfg]);
	},
	getFbar:function(){
		var me = this;
		return [{
			text:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.import'),//导入
			xtype:'button',
			scope:me,
			handler:me.uploadQueryFile
		},{
			text:sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.cancel'),//取消
			xtype:'button',
			handler:function(){
				me.close();
			}
		}];
	},
	//文件上传
	uploadFile:function(){
		var me = this;
		var successFn = function(json){
			//sign.expAirAgencySign.showInfoMes('全部数据导入成功！');//全部数据导入成功！
//			if(Ext.isEmpty(json.effectivePlanVo.numList)){
				sign.expAirAgencySign.showInfoMes('全部数据导入成功！');//全部数据导入成功！
				me.close();
//			}else{
//				var message = sign.expAirAgencySign.i18n('foss.pricing.first');//第
//				for(var i = 0;i<json.effectivePlanVo.numList;i++){
//					message = message+json.effectivePlanVo.numList[i]+','
//				}
//				message = message+sign.expAirAgencySign.i18n('foss.pricing.lineImportSuccess');//行，没有导入成功！
//				sign.expAirAgencySign.showInfoMes(message);
//			}
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				sign.expAirAgencySign.showInfoMes(sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.requestTimeOut'));//请求超时
			}else{
				sign.expAirAgencySign.showInfoMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = sign.realPath('importExpressSignedBill.action');
		form.submit({
            url: url,
            waitMsg: sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.uploadYourAttachment'),//上传您的附件...
            timeout:600000,   
            success:function(form, action){
    			var result = action.result;
    			if(Ext.isEmpty(result)){
					failureFn(result);
					return;
				}
    			if(result.success){
    				successFn(result);
    			}else{
    				failureFn(result);
    			}
    		},
    		failure:function(form, action){
    			var result = action.result;
    			failureFn(result);
    		},
    		exception : function(form, action) {
				var result = action.result;
				failureFn(result);
			}
        });
	},
	uploadQueryFile:function(){
		var me = this;
		var successFn = function(json){
			me.close();
			signInfoForm = Ext.getCmp('Foss_sign_expAirAgencySign_SignInfoForm_Id').getForm(),
		    waybillInfoForm = Ext.getCmp('Foss_sign_expAirAgencySign_wayBillInfoForm_id').getForm();
			signInfoForm.reset();//签收信息重置
		    waybillInfoForm.reset();//运单基本信息重置
		    sign.expAirAgencySign.goodsQtyTotal = null;// 签收件数
		    sign.expAirAgencySign.deliverymanName = null;// 签收人
		    sign.expAirAgencySign.signSituation = null;// 签收情况
		    sign.expAirAgencySign.signNote = null;// 签收备注
		    sign.expAirAgencySign.receiveCustomerName = null; //收货联系人
			var vateDetailGrid = Ext.getCmp('Foss_sign_expAirAgencySign_WateDetailGrid_ID').getStore();
			vateDetailGrid.removeAll();
			vateDetailGrid.loadData(json.airAgencySignVo.agencyQueryDtos);	
		};
		var failureFn = function(json){
			if(Ext.isEmpty(json)){
				sign.expAirAgencySign.showInfoMes(sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.requestTimeOut'));//请求超时
			}else{
				sign.expAirAgencySign.showInfoMes(json.message);
			}
		};
		var form = me.down('form').getForm();
		var url = sign.realPath('importExpressSignedBill.action');
		form.submit({
			url: url,
			waitMsg: sign.expAirAgencySign.i18n('pkp.sign.expAirAgencySign.uploadYourAttachment'),//上传您的附件...
			timeout:600000,   
			success:function(form, action){
				var result = action.result;
				if(Ext.isEmpty(result)){
					sign.expAirAgencySign.showInfoMes("请求超时，请确认文件上传大小是否大于10M!导入的EXCEL表格大小不能超过10M");//请求超时
					return;
				}
				if(result.success){
					successFn(result);
				}else{
					failureFn(result);
				}
			},
			failure:function(form, action){
				var result = action.result;
				failureFn(result);
			},
			exception : function(form, action) {
				var result = action.result;
				failureFn(result);
			}
		});
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Foss.sign.expAirAgencySign.QueryPanel'); 
	var bigDownPanel = Ext.create('Foss.sign.expAirAgencySign.BigDownPanel'); 

	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-expAirAgencySignIndex_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContentNToolbar-body',
		layout:'auto',
		getQueryForm: function(){
			return queryPanel;
		},
		getBigDownPanel: function(){
			return bigDownPanel;
		},
		items: [queryPanel,bigDownPanel],
		renderTo: 'T_sign-expAirAgencySignIndex-body'
	});
});