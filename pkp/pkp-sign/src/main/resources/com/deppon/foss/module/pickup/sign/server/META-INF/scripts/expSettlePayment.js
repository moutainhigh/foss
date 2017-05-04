/**
 *author : 043258-foss-zhaobin
 *page : 结清货款 
*/
//定义全局变量标记是否有关联单号
sign.expSettlePayment.aos ='N';
//去除网上支付、到付 付款方式
(function(){
	sign.expSettlePayment.PaymentStore =FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE', null);
	// sign.expSettlePayment.PaymentStore.removeAt(sign.expSettlePayment.PaymentStore.find('valueCode','OL'));
	sign.expSettlePayment.PaymentStore.removeAt(sign.expSettlePayment.PaymentStore.find('valueCode','FC'));
	sign.expSettlePayment.PaymentStore.removeAt(sign.expSettlePayment.PaymentStore.find('valueCode','DT'));
	
	//统一结算零担付款方式
	sign.expSettlePayment.CentralizedPaymentStore =
		Ext.create('Ext.data.Store',{
			fields:['valueCode','valueName'],
		    data : [{
				'valueCode' : 'CT',
				'valueName' : '月结'
			},{
				'valueCode' : 'TT',
				'valueName' : '电汇'
			}]
		});
})();
//时间默认值
sign.expSettlePayment.getTargetDate = function(date, day) {
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
	
//身份证号码校验
sign.expSettlePayment.checkIdcard = function checkIdcard(idcard){ 
	var Errors=new Array( 
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeThrough'), //身份验证通过!
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeDigitsWrong'), //身份证号码位数不对!
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeIllegal'), //身份证号码出生日期超出范围或含有非法字符!
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeError'), //身份证号码校验错误!
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeAreaIllegal') ,//身份证地区非法!
			sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCodeIsNotNull')//身份证不能为空!
	); 
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",
				    21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",
				    33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",
				    41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",
				    46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",
				    54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",
				    65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
	var idcard,Y,JYM; 
	var S,M; 
	var idcard_array = new Array(); 
	if(idcard==null) 
	{
		return Errors[5];
	}
	idcard_array = idcard.split(""); 
	//地区检验 
	
	//地区检验 
	if(area[parseInt(idcard.substr(0,2))]==null) 
	{
		return Errors[4];
	}
	//身份号码位数及格式检验 
	switch(idcard.length){ 
		case 15: 
			if ((parseInt(idcard.substr(6,2))+1900) % 4 == 0 || 
			   ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && 
				(parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){ 
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性 
			} else { 
				ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性 
			} 
			if(ereg.test(idcard)) 
			{
				return Errors[0]; 
			}
			else
			{
				return Errors[2]; 
			}
			break; 
		case 18: 
			//18位身份号码检测 
			//出生日期的合法性检查 
			//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9])) 
			//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8])) 
			if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){ 
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式 
			} else { 
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式 
			} 
			if(ereg.test(idcard)){//测试出生日期的合法性 
				//计算校验位 
				S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7 
				+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9 
				+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10 
				+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5 
				+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8 
				+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4 
				+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2 
				+ parseInt(idcard_array[7]) * 1 
				+ parseInt(idcard_array[8]) * 6 
				+ parseInt(idcard_array[9]) * 3 ; 
				Y = S % 11; 
				M = "F"; 
				JYM = "10X98765432"; 
				M = JYM.substr(Y,1);//判断校验位 
				if(M == idcard_array[17]){
					return Errors[0]; //检测ID的校验位 
				} 
				else {
					return Errors[3];
				} 
			} 
			else {
				return Errors[2]; 
			}
			break; 
		default: 
			return Errors[1]; 
			break; 
	} 
};

//创建处理异常枚举store
Ext.define('Foss.sign.expSettlePayment.SettlePaymentTypeStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
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

//待处理信息
Ext.define('Foss.sign.expSettlePayment.SettlePaymentModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'},//运单号
		{name : 'storageQty'},//件数
		{name : 'receiveCustomerName'}//收货人(收货客户名称)
			]
});

//财务信息
Ext.define('Foss.sign.expSettlePayment.model.FinancialModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'receiveableAmount',type:'float',defaultValue: 0},//应收代收款
		{name : 'receivedAmount',type:'float',defaultValue: 0},//已收代收款
		{name : 'receiveablePayAmoout',type:'float',defaultValue: 0},//应收到付款
		{name : 'receivedPayAmount',type:'float',defaultValue: 0},//已收到付款
		{name : 'connetnumCost',type:'float',defaultValue: 0},//关联单号费用
		//liuxiangcheng add 2016-5-27
		{name : 'totalMoney',type:'float',defaultValue: 0},//母件费用
		{name : 'totalPayment',type:'float',defaultValue: 0}//总收款金额
		]
});

//运单基本信息
Ext.define('Foss.sign.expSettlePayment.model.WayBillInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'waybillNo'},// 运单号
        {name: 'receiveCustomerCode'},
		{name: 'receiveCustomerName'},// 收货人(收货客户名称)
		{name: 'receiveCustomerAddress'},// 收货客户具体地址
		{name: 'receiveCustomerPhone'},// 收货客户电话
		{name: 'receiveCustomerMobilephone'},//收货人手机
		{name: 'arriveTime',type:'date',convert:dateConvert},// 到达时间
		{name: 'notificationResult',
				 convert:function(value){
					 if(value!=null && value == 'SUCCESS'){
						 return value;
					 }else {
						 return 'FAILURE';
					 }
				 }},// 是否通知成功
		{name: 'notificationTime',type:'date',convert:dateConvert},// 上次通知时间
		{name: 'toPayAmount',type:'float',defaultValue: 0},// 到付总额（含代收货款）
		{name: 'codAmount',defaultValue: 0},// 代收货款（开单时）
		{name: 'transportFee',defaultValue: 0},// 运费
		{name: 'deliveryGoodsFee',defaultValue: 0},// 送货费
		{name: 'insuranceFee',defaultValue: 0},// 保价费
		{name: 'paidMethod',convert:function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
		}},// 付款方式（出发部门）
		{name: 'otherFee',defaultValue: 0},// 其他费用
		{name: 'insuranceAmount'},// 货物价值
		{name: 'stockStatus'},//库存状态
		{name: 'receiveMethod',convert:function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
		}},// 派送方式
		{name: 'createTime',type:'date',convert:dateConvert},// 出发时间
		{name: 'deliveryCustomerCityName'},// 始发站
		{name: 'receiveOrgName'},// 始发部门
		{name: 'goodsName'},// 货物名称
		{name: 'isWholeVehicle'},// 是否整车运单
		{name: 'goodsQtyTotal'},// 件数
		{name: 'goodsWeightTotal'},// 重量
		{name: 'goodsVolumeTotal'},// 体积
		{name: 'goodsPackage'},// 包装
		{name: 'deliveryCustomerName'},// 发货人   
		{name: 'transportType',convert:function(value) {
			return FossDataDictionary.rendererSubmitToDisplay(value, 'TRANS_TYPE');
		}},// 运输方式
		{name: 'productCode',convert:function(value) {
			return Foss.pkp.ProductData.rendererSubmitToDisplay(value);
		}},// 运输性质
		{name: 'settleStatus'},//结清状态
		{name: 'storageCharge',type:'float',defaultValue: 0}// 仓储费
		,
		{name: 'startCentralizedSettlement'},//出发是否统一结算
		{name: 'arriveCentralizedSettlement'},//到达是否统一结算
		{name: 'startContractOrgCode'},//出发合同部门
		{name: 'arriveContractOrgCode'},//到达合同部门
		{name: 'startContractOrgName'},//出发合同部门
		{name: 'arriveContractOrgName'},//到达合同部门
		{name: 'startReminderOrgCode'},//出发催款部门标识
		{name: 'arriveReminderOrgCode'}//出发催款部门标识
    ]
});

//待处理数据源
Ext.define('Foss.sign.expSettlePayment.SettlePaymentStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.sign.expSettlePayment.SettlePaymentModel',
	storeId:'Foss_sign_expSettlePayment_SettlePayment_Store',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		url:sign.realPath('queryAirAgencyQueryDtoList.action'),
		actionMethods : 'POST',
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			//定义读取JSON数据的根对象
			root: 'vo.repaymentDto.airAgencyQueryDtoList'
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			// 执行查询
			var queryParams=sign.expSettlePayment.settlePaymentForm.getValues();
						
			Ext.apply(operation,{
				params:{
					'vo.repaymentDto.airAgencyQueryDto.waybillNo':queryParams.waybillNo,// 运单号
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerName':queryParams.receiveCustomerName,// 收货人(收货客户名称)
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerPhone':queryParams.receiveCustomerPhone,//  收货客户电话
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,// 收货人手机
					'vo.repaymentDto.airAgencyQueryDto.productCode':queryParams.productCode,// 运输性质
					'vo.repaymentDto.airAgencyQueryDto.storageTimeBegin':queryParams.storageTimeBegin,// 入库时间
					'vo.repaymentDto.airAgencyQueryDto.storageTimeEnd':queryParams.storageTimeEnd,// 入库时间
					'vo.repaymentDto.airAgencyQueryDto.isExpress':'Y'// 是否快递字段
				}
			});
		},
		load : function(store, records, successful, oOpts) {
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), data.message, 'error', 2000);	//提示			
				return;
			}
			var grid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
			if(store.data.length>0)
			{
				grid.getSelectionModel().select(0);
				var record = Ext.create('Foss.sign.expSettlePayment.SettlePaymentModel',grid.getStore().getAt(0).data);
				grid.fireEvent('itemclick',grid,record);
			}	
		}
	}
});

//定义付款记录模型
Ext.define('Foss.sign.expSettlePayment.PaymentModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'paymentType',convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'SETTLEMENT__PAYMENT_TYPE');
				}},//付款方式
			    { name: 'actualFreight'},//实付运费
			    { name: 'codAmount'},//代收货款
			    { name: 'stlbillGeneratedStatus'},//是否已有财务单据
			    { name: 'paymentTime',convert:dateConvert}//付款时间
			 ]
});

//创建一个变更内容store
Ext.define('Foss.sign.expSettlePayment.PaymentStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.sign.expSettlePayment.PaymentModel',
	//是否自动查询
	autoLoad: false,
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'vo.repaymentDto.repaymentEntityList'
        }
	}
});

//查询表单
Ext.define('Foss.sign.expSettlePayment.SettlePaymentForm',{
		extend:'Ext.form.Panel',
		id:'Foss_sign_expSettlePayment_SettlePaymentForm_Id',
		layout:'column',		
		cls:'autoHeight',
		bodyCls:'autoHeight',	
		collapsible: true,
		animCollapse: true,
		title:sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.queryCondition'),//查询条件
		frame:true,
		defaultType: 'textfield',	
		defaults: {
			margin:'5 10 5 10',
			//anchor: '90%',
			labelWidth:100
		},
		items: [{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillNo'),//单号
			name: 'waybillNo',
			allowBlank:true,
			vtype: 'waybill',
			columnWidth:.25,
			listeners:{
				change:function(field,new_v,old_v){
					if(!Ext.isEmpty(new_v)&& new_v.length >=11){
						//先去两端的空格
						var new_value = new_v.trim();
						//再把值设置给文本框
						field.setValue(new_value.substring(0,new_value.length>=10?10:new_value.length));
					}
				},
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                    	var grid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
                    	grid.getStore().load();
                    }
                }
			}
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerName'),//收货人
			name: 'receiveCustomerName',
			allowBlank:true,
			columnWidth:.25			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerPhone'),//收货客户电话
			name: 'receiveCustomerPhone',
			allowBlank:true,
			columnWidth:.25			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerMobilephone'),//收货人手机
			name: 'receiveCustomerMobilephone',
			allowBlank:true,
			columnWidth:.25			
		},{
		    xtype:'combobox',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.productCode'),//运输性质
			name:'productCode',
			columnWidth: 0.25,
			value:'ALL',
			store:Ext.create('Foss.sign.expSettlePayment.SettlePaymentTypeStore',
			{
			  data:{
			       'items':[
			            {'code':'','name':'全部'},
						{'code':'PACKAGE','name':'标准快递'},
						{'code':'RCP','name':'3.60特惠件'},
						{'code':'EPEP','name':'电商尊享'},
						{'code':'DEAP','name':'商务专递'}
				   ]
			  }
			}),
			value:''
		},{
			xtype: 'rangeDateField',
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.storeTime'),//入库时间
			fieldId: 'FOSS_ExpExceptionTime_Id',
			dateType: 'datetimefield_date97',
			fromName: 'storageTimeBegin',
			toName: 'storageTimeEnd',
			disallowBlank:true,
			editable:false,
			fromValue: Ext.Date.format(sign.expSettlePayment.getTargetDate(new Date(),-3),'Y-m-d H:i:s'),
			toValue: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			columnWidth: .5
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.reset'),//重置
				columnWidth:.08,
				handler:function()
				{
					var form = this.up('form').getForm();
					form.findField('waybillNo').setValue("");
					form.findField('receiveCustomerName').setValue("");
					form.findField('receiveCustomerPhone').setValue("");
					form.findField('receiveCustomerMobilephone').setValue("");
					form.findField('productCode').setValue("");
					form.findField('storageTimeBegin').setValue(Ext.Date.format(sign.expSettlePayment.getTargetDate(new Date(),-3),'Y-m-d H:i:s'));
					form.findField('storageTimeEnd').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.search'),//查询
				disabled:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquerybutton'),
				hidden:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquerybutton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function()
				{
					var form = this.up('form').getForm();
					var storageTimeBegin = form.getValues().storageTimeBegin;
					var storageTimeEnd = form.getValues().storageTimeEnd;
					var result = Ext.Date.parse(storageTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(storageTimeBegin,'Y-m-d H:i:s');
					if(result / (24 * 60 * 60 * 1000) >= 30){
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'),sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.dateintervalError'),'error', 3000);
						return;
					}
					if(form.isValid())
					{
						signInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm(),
						waybillInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm();
						paymentGrid = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id').getStore();
						detaiGrid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id').getStore();
						signInfoForm.reset();//签收信息重置
						paymentGrid.removeAll();//付款信息重置
						waybillInfoForm.reset();//运单基本信息重置
						detaiGrid.removeAll();//清空待处理列表
	//					signInfoForm.findField('claimNo').setReadOnly(true);
						Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id').getStore().load();
					}
					else
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillNoError'), 'error', 3000);
					}	
				}
			}]
		}]
	});

//待处理表格
Ext.define('Foss.sign.expSettlePayment.WaitingProcessGrid', {
	extend: 'Ext.grid.Panel',	
	id:'Foss_sign_expSettlePayment_WaitingProcessGrid_Id',
	emptyText: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.emptyText'),//查询结果为空
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	titleCollapse:true,
	height:860,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	// 定义选择框
	//selModel : Ext.create('Ext.selection.CheckboxModel'),
    viewConfig: {
        enableTextSelection: true
     },
	columns: [{
		text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillNo'),//单号
		sortable: true, 
		dataIndex: 'waybillNo',
		width:75
	},{
		text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.storageQty'),//件数
		sortable: true, 
		dataIndex: 'storageQty',
		width:45
	},{
		text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerName'),//收货人
		sortable: true, 
		xtype: 'ellipsiscolumn',
		dataIndex: 'receiveCustomerName',
		width:80
	}],
	 listeners: {
        itemclick:function(dv, record, item, index, e){
			Ext.Ajax.request({
			    url:sign.realPath('queryPaymentByWaybillNo.action'),
			    params: {
			    	'vo.repaymentDto.airAgencyQueryDto.waybillNo': record.get('waybillNo')
			    },
				    success: function(response){
				    	var result = Ext.decode(response.responseText);
				    	var signInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm();
						var waybillInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm();
						//得到运单基本信息
						var wayBillInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.waybillDto,
								 'Foss.sign.expSettlePayment.model.WayBillInfoModel');
						var signInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.financialDto,
						 		 'Foss.sign.expSettlePayment.model.FinancialModel');
						//点击运单号有返回类型弹窗
						if(result.vo.repaymentDto.waybillDto.returnbillType !='NONE'){
							Ext.Msg.show({
								msg: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillsign'),
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.WARNING,
								componentCls: 'yellow_button'
							});
						};
						//加载签收信息
						signInfoForm.loadRecord(signInfoModel);
						//张新 2015-2-3  判断是否为返单 返单显示关联单号费用和总收款金额
						if(result.vo.repaymentDto.isback==1){
							signInfoForm.findField('connetnumCost').setVisible(true);	
							signInfoForm.findField('totalPayment').setVisible(true);
							signInfoForm.findField('occupy').setVisible(false);
							sign.expSettlePayment.aos='Y';
						}else{
							signInfoForm.findField('connetnumCost').setVisible(false);	
							signInfoForm.findField('totalPayment').setVisible(false);
							signInfoForm.findField('occupy').setVisible(true);
							sign.expSettlePayment.aos='N';
						}
						//加载运单基本信息
						waybillInfoForm.loadRecord(wayBillInfoModel);
						//加载付款信息
						sign.expSettlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
						//是否有仓储费
						if(result.vo.repaymentDto.waybillDto.storageCharge)
						{
							signInfoForm.findField('storageCharge').setValue(result.vo.repaymentDto.waybillDto.storageCharge);
						}
						else
						{
							signInfoForm.findField('storageCharge').setValue('0');
						}
						//运单是否有代收货款 若无代收货款 则签收信息中代收货款不可编辑
						if(result.vo.repaymentDto.waybillDto.codAmount==0 || result.vo.repaymentDto.waybillDto.codAmount==null)
						{
							signInfoForm.findField('codAmount').setValue('0');
							signInfoForm.findField('codAmount').setReadOnly(true);
						}
						else
						{
							signInfoForm.findField('codAmount').setReadOnly(false);
							Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.noCodAmount'), 'error', 3000);
						}
						var paymentGrid = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id');
						settleRow = paymentGrid.store.data.length;
						var store = sign.expSettlePayment.PaymentGrid.store;
						Ext.getCmp('Foss_sign_expSettlePayment_addException_Id').setDisabled(true);
						Ext.getCmp('Foss_sign_expSettlePayment_quickPayment_Id').setDisabled(true);

						store.each(function(record, index, allRecords){
							if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
								Ext.getCmp('Foss_sign_expSettlePayment_addException_Id').setDisabled(false);
								Ext.getCmp('Foss_sign_expSettlePayment_quickPayment_Id').setDisabled(false);
							}
						});
						if(index == undefined || index == null)
						{
							index = 0;
						}
						var grid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
						var selection = grid.getStore().getAt(index);
						var	receiveCustomerName = selection.get('receiveCustomerName');

						//将运单库存状态置为库存中
						waybillInfoForm.findField('stockStatus').setValue('库存中');
						signInfoForm.findField('deliverymanName').setValue(receiveCustomerName);
						signInfoForm.findField('consigneeCode').setValue("");
						signInfoForm.findField('identifyType').setValue("ID_CARD");
						signInfoForm.findField('identifyCode').setValue("");
						signInfoForm.findField('paymentType').setValue("CD");
						signInfoForm.findField('claimNo').setValue("");
						signInfoForm.findField('claimNo').setReadOnly(false);
						signInfoForm.findField('claimNo').enable();
						signInfoForm.findField('actualFreight').setValue('0');
						signInfoForm.findField('codAmount').setValue('0');
						signInfoForm.findField('totalCollection').setValue('0');
						signInfoForm.findField('actualFreight').setReadOnly(false);
						
						// 统一结算限制
						// 到达金额>0 到达统计结算
						if(signInfoModel.data.receiveablePayAmoout > 0
								  && wayBillInfoModel.data.arriveCentralizedSettlement == 'Y'){
//							signInfoForm.findField('consigneeCode').setValue(wayBillInfoModel.data.receiveCustomerCode);//到达客户
//							signInfoForm.findField('consigneeCode').setRawValue(wayBillInfoModel.data.receiveCustomerName);//到达客户
							signInfoForm.findField('consigneeCode').setCombValue(wayBillInfoModel.data.receiveCustomerName,wayBillInfoModel.data.receiveCustomerCode);
							signInfoForm.findField('consigneeCode').setReadOnly(true);
							
							var newData = Ext.clone(sign.expSettlePayment.CentralizedPaymentStore.data);
							if(signInfoForm.findField('paymentType').store == null){
								var newStore = Ext.create('Ext.data.Store',{
									fields:['valueCode','valueName'],
								    data : newData
								});
								signInfoForm.findField('paymentType').store = newStore;
							}else{
								signInfoForm.findField('paymentType').store.removeAll();
								signInfoForm.findField('paymentType').store.loadData(newData.items);
							}
							
							signInfoForm.findField('paymentType').value = "";//置空
							signInfoForm.findField('paymentType').setRawValue("");
						}else{
							signInfoForm.findField('consigneeCode').setValue('');//到达客户
							signInfoForm.findField('consigneeCode').setRawValue("");
							signInfoForm.findField('consigneeCode').setReadOnly(false);
							
							var newData = Ext.clone(sign.expSettlePayment.PaymentStore.data);
							if(signInfoForm.findField('paymentType').store == null){
								var newStore = Ext.create('Ext.data.Store',{
									fields:['valueCode','valueName'],
								    data : newData
								});
								signInfoForm.findField('paymentType').store = newStore;
							}else{
								signInfoForm.findField('paymentType').store.removeAll();
								signInfoForm.findField('paymentType').store.loadData(newData.items);
							}
							
							signInfoForm.findField('paymentType').setValue("CD");//置空
							signInfoForm.findField('paymentType').setRawValue("银行卡");
							
						}
				    }
			});
		}
	},
	constructor: function(config){
	var me = this,
		cfg = Ext.apply({}, config);
	me.store = Ext.create('Foss.sign.expSettlePayment.SettlePaymentStore');
	me.callParent([cfg]);	 
	}	
});

var message = null;
var passWordWin = null;
//签收信息表单
Ext.define('Foss.sign.expSettlePayment.SignInfoForm',{
		extend:'Ext.form.Panel',	
		id:'Foss_sign_expSettlePayment_SignInfoForm_Id',
		layout:'column',		
		bodyStyle:'padding:5 0 0 0',	
		cls:'autoHeight',		
		bodyCls:'autoHeight',
		defaultType: 'textfield',
		defaults: {
			margin:'5 0 5 10',
			anchor: '100%',
			labelWidth:90			
		},
		items: [{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.consigneeCode'),//客户
			xtype : 'commoncustomerselector',
			all:'true',
			singlePeopleFlag : 'Y',
			isPaging:true, //分页
			listWidth:300,//设置下拉框宽度
			name: 'consigneeCode',
			columnWidth:.33			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.deliverymanName'),//提货人姓名
			name: 'deliverymanName',
			maxLength:50,
			allowBlank:false,
			columnWidth:.35			
		},{
			name: 'blank',
			readOnly:true,
			columnWidth:.32	
		},{
			columnWidth:.33,
			xtype: 'combobox',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			mode:'local',
			queryMode: 'local',		
			triggerAction:'all',
			forceSelection:true,
			editable:true,
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyType'),//证件类型
			name: 'identifyType',
			displayField:'valueName',
			valueField:'valueCode',
			editable:false,
			value:'ID_CARD',
			store:FossDataDictionary.getDataDictionaryStore('PKP_CREDENTIAL_TYPE'),
			 listeners : 
			 	{
			    	'select' : function(combo, records, eOpts)
			    	{
			    		var form = this.up('form').getForm();
			    		form.findField('identifyCode').focus();
			    	}
				 }	
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.identifyCode'),//证件号码
			id: 'identify_ExpCode_ID',
			name: 'identifyCode',
			allowBlank:false,
			maxLength:50,
			columnWidth:.35
		},{
			border : false,
			xtype : 'container',
			columnWidth:0.2,
			layout:'column',			
			defaults: {
				margin:'0 0 0 0'
			},
			items : [{
						border : false,
						columnWidth:.26,
						html: '&nbsp;'
					},{
					height:24,	
					xtype : 'button',
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin'),
					text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.scanning'),//扫描获取
					columnWidth:.7,				
					handler: function() {
				    	  Ext.data.JsonP.request({
				    		  url: 'http://localhost:8077/idcard',
								 callbackKey: 'callback',		    
							     success: function(result, request) {
							    	 if(result.idNo == null)
						    		 {
							    		 alert('获取身份证信息失败');
						    		 }
							    	 Ext.getCmp('identify_ExpCode_ID').setValue(result.idNo);
							    	 
							     },
							     failure : function (result, request) {
							    	 alert('获取身份证信息失败');
							     }
					    	})
					}
					}]
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentType'),//付款方式
			name:'paymentType',
			columnWidth: 0.33,
			editable:false,
			//value:'CD',
			//store:sign.expSettlePayment.PaymentStore,
			 listeners : 
			 	{
			    	'select' : function(combo, records, eOpts)
			    	{
			    		var form = this.up('form').getForm();
			    		//付款方式选择月结/临欠时，“实付运费”不可输入且归0
			    		if(form.findField('paymentType').getValue()=='CT'||form.findField('paymentType').getValue()=='DT')
			    		{
			    			form.findField('actualFreight').setValue('0');
			    			form.findField('actualFreight').setReadOnly(true);
			    		}
			    		else 
		    			{
			    			form.findField('actualFreight').setReadOnly(false);
		    			}
			    		if(form.findField('paymentType').getValue()=='TT'||form.findField('paymentType').getValue()=='NT'
								|| form.findField('paymentType').getValue()=='OL' || form.findField('paymentType').getValue()=='CD')
			    		{
			    			form.findField('claimNo').setReadOnly(false);
			    			form.findField('claimNo').enable();
			    		}
			    		else
			    		{
			    			form.findField('claimNo').setValue("");
			    			form.findField('claimNo').disable();
			    		}	
			    	}
					,
			   		'focus':function( _this, _the, _eOpts ){
			   			var form = _this.up('form').getForm();		
			   			var wayForm = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm();
			   			if(wayForm.findField('arriveCentralizedSettlement').getValue() == 'Y'
			   				&&form.findField('receiveablePayAmoout').getValue() > 0
			   				&&form.findField('actualFreight').getValue() == 0){
			   				var newData = Ext.clone(sign.expSettlePayment.PaymentStore.data);
							if(_this.store == null){
								var newStore = Ext.create('Ext.data.Store',{
									fields:['valueCode','valueName'],
								    data : newData
								});
								_this.store = newStore;
							}else{
								_this.store.removeAll();
								_this.store.loadData(newData.items);
							}
			   				
							_this.value = "CD";//置空
							_//this.setRawValue("");
						}else if(wayForm.findField('arriveCentralizedSettlement').getValue() == 'Y'
			   				&&form.findField('receiveablePayAmoout').getValue() > 0
			   				&&form.findField('actualFreight').getValue() > 0){
							var newData = Ext.clone(sign.expSettlePayment.CentralizedPaymentStore.data);
							if(_this.store == null){
								var newStore = Ext.create('Ext.data.Store',{
									fields:['valueCode','valueName'],
								    data : newData
								});
								_this.store = newStore;
							}else{
								_this.store.removeAll();
								_this.store.loadData(newData.items);
							}
			   				
							_this.value = "";//置空
							_this.setRawValue("");
						}
			   		}
		
				 }	
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.claimNo'),//款项确认编号
			name: 'claimNo',
			allowBlank:true,
			maxLength:50,
			readOnly:true,
			columnWidth:.35,
			listeners : {
				blur : function(field, event, eOpts) {
					var form = this.up('form').getForm();
//					var form = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
					var waybillNo = form.findField('waybillNo').getValue();
					var paymentType = form.findField("paymentType").getValue();
					if (field.value != '') {//modify by 353654
						Ext.Ajax.request({
						    url:sign.realPath('queryTransfer.action'),
						    params : {					
								'vo.repaymentEntity.claimNo': field.value,
								'vo.repaymentEntity.waybillNo': waybillNo,
								'vo.repaymentEntity.paymentType': form.findField('paymentType').getValue()
						    },
					    	success:function(response){
					    		var json = Ext.decode(response.responseText);
					    		if(json.noCancelAmount<form.findField('receiveablePayAmoout').getValue())
					    		{
					    			form.findField('actualFreight').setValue(json.noCancelAmount);
					    		}else{
					    			form.findField('actualFreight').setValue(form.findField('receiveablePayAmoout').getValue());
					    		}
					    		if(json.noCancelAmount>0 && json.noCancelAmount != null)
					    		{
					    			form.findField('actualFreight').setReadOnly(true);
					    		}else{
					    			form.findField('actualFreight').setReadOnly(false);
					    		}	
					    	},
				    		exception: function(response){
								var json = Ext.decode(response.responseText);
		              			Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentFailed'), json.message, 'error', 3000);
							}
						});
					}
				}
			}
		},{
			xtype:'numberfield',
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.actualFreight'),//实付运费
			name: 'actualFreight',
			hideTrigger: true,
			maxLength:50,
			//allowDecimals : false,// 不允许有小数点
			allowBlank:false,
			readOnly:false,
			columnWidth:.33,
			listeners :{
				'blur':function(me,theEvent,eOpts)
				{
					var form = this.up('form').getForm();
					form.findField('totalCollection').setValue(form.findField('actualFreight').getValue()+form.findField('codAmount').getValue());
				}	
			}
		},{
			xtype:'numberfield',
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.codAmount'),//代收货款
			name: 'codAmount',
			hideTrigger: true,
			maxLength:50,
			//allowDecimals : false,// 不允许有小数点
			allowBlank:false,
			readOnly : true,
			columnWidth:.328,
			listeners :{
				'blur':function(me,theEvent,eOpts)
				{
					var form = this.up('form').getForm();
					form.findField('totalCollection').setValue(form.findField('actualFreight').getValue()+form.findField('codAmount').getValue());
				}	
			}
		},{
			xtype:'numberfield',
			fieldLabel:  '母件费用',
			name: 'totalMoney',
			hideTrigger: true,
			readOnly : true,
			maxLength:50,
			//allowDecimals : false,// 不允许有小数点
			allowBlank:false,
			columnWidth:.34
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.totalCollection'),//收款总额
			name: 'totalCollection',
			readOnly:true,
			columnWidth:.33
//			//张新  2015-2-3 增加监听，当关联单号值发生变化  就修改总收款金额
//			listeners : 
//		 	{
//		    	change : function(a,newValue,oldValue,eOpts)
//		    	{
//		    		var  connetnumCost=Ext.getCmp('connetnumCostid').value;
//		    		//String转换为int
//		    		var  newValue=parseInt(newValue)
//		    		Ext.getCmp('totalPaymentid').setValue(newValue+connetnumCost);
//		    	}
//			 }					
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveableAmount'),//应收代收款
			name: 'receiveableAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveablePayAmoout'),//应收到付款
			name: 'receiveablePayAmoout',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receivedAmount'),//已收代收款
			name: 'receivedAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receivedPayAmount'),//已收到付款
			name: 'receivedPayAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.storageCharge'),//仓储费
			name: 'storageCharge',
			readOnly:true,
			columnWidth:.33		
		},
		//张新修改 ——2014-2-2
		{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.associateWaybillNoFee'),//关联单号费用
			name: 'connetnumCost',
			//id: 'connetnumCostid',
			readOnly:true,
			columnWidth:.33,
			hidden:true
		},{
			fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.totalReceivesMoney'),//总收款金额
			name: 'totalPayment',
			//id: 'totalPaymentid',
			readOnly:true,
			columnWidth:.33,
			hidden:true
		},{
			name: 'occupy',
			//id: 'occupyid',
			readOnly:true,
			columnWidth:.66,
			hidden:false
		},{
			border : false,
			xtype : 'container',
			columnWidth:0.3,
			layout:'column',
			defaults: {
				margin:'0 0 0 0'
			},
			items : [{
				border : false,
				columnWidth:.2,
				html: '&nbsp;'
			},{
				columnWidth:.33,
				xtype : 'button',
				text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.settle'),//结清货款
				disabled:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexsettlebutton'),
				hidden:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexsettlebutton'),
				id:'Foss_sign_expSettlePayment_Payment_Id',
				height:24,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
				}),
				handler: function() {

					var form = this.up('form').getForm();
					var formparams = this.up('form').getForm().getValues();
					var wayformparams =Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().getValues();
					var waybillNo = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
					var toPayAmount = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().findField('toPayAmount').getValue();
					var consigneeCode =form.findField('consigneeCode').getValue();
					var consigneeName =form.findField('consigneeCode').getRawValue();
					var waitingProcessGrid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
					var paymentGrid = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id');
					
					if (waybillNo == null || waybillNo == "")
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), "请选择一条运单", 'error', 3000);
						return;
					}
					var resultReturnbillType = '';
					var resultReturnWaybillNo = '';
					//选中运单是否有签收单原件返回
					Ext.Ajax.request({
					    url:sign.realPath('queryWaybillByBack.action'),
					    async:false,
					    params: {
					    	'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
					    },
						    success: function(response){
						    	var result = Ext.decode(response.responseText);
						    	resultReturnbillType = result.vo.waybillDto.returnbillType;
						    	resultReturnWaybillNo = result.vo.waybillDto.returnWaybillNo;
								//点击运单号有返回类型弹窗
								if(!Ext.isEmpty(resultReturnbillType) 
										&& resultReturnbillType !='NONE'){
									if(resultReturnbillType =='ORIGINAL' 
										&& (resultReturnWaybillNo ==null || Ext.isEmpty(resultReturnWaybillNo))){
										Ext.Msg.show({
											msg: '此单有签收单原件返回，请先开返单后再结清货款',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.WARNING,
											componentCls: 'yellow_button'
										});
									}else if(resultReturnbillType =='FAX'){
										Ext.Msg.show({
											msg: '此单有签收单传真返回',
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.WARNING,
											componentCls: 'yellow_button'
										});
									}
								};
						    }
					});
					if (resultReturnbillType == 'ORIGINAL' 
						&& (resultReturnWaybillNo ==null || Ext.isEmpty(resultReturnWaybillNo))) {
						return;
					}
					Ext.Ajax.request({
						url:sign.realPath('queryPaymentByWaybillNo.action'),
						params: {
							'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
						},
						async:false,
						success: function(response){
				    	var result = Ext.decode(response.responseText);
						//加载付款信息
						sign.expSettlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
						}
					});
					formparams.waybillNo = waybillNo;
					formparams.consigneeCode = consigneeCode;
					formparams.consigneeName = consigneeName;
					formparams.storageFee = form.findField('storageCharge').getRawValue();
					selectRow = waitingProcessGrid.getSelectionModel().getSelection();
					var store = paymentGrid.getStore();
					var actualFreightFee = 0;
					var codAmountFee = 0
					store.each(function(record, index, allRecords){
						if(record.get('stlbillGeneratedStatus')=='STLBILL_NOGENERATE'||record.get('stlbillGeneratedStatus')=='STLBILL_GENERATEING'){
							actualFreightFee = actualFreightFee + record.get('actualFreight');
							codAmountFee = codAmountFee + record.get('codAmount');
						}
					});
					//若没有选择待处理运单
					if (selectRow.length == 0)
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.choseWaybillBefore'), 'error', 3000);
					}
					else if (!form.isValid())
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.checkRequiredInformation'), 'error', 3000);
					}
					//付款方式为“支票”或“电汇”，则款项认领编号不可为空
					else if((form.findField('paymentType').getValue()=='NT'||form.findField('paymentType').getValue()=='TT'||form.findField('paymentType').getValue()=='CD')
							&& form.findField('claimNo').getValue().trim()=='')
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.claimNoNotNull'), 'error', 3000);
					}
					//款项认领编号不为空时，必须为数字
					else if(form.findField('paymentType').getValue()=='CD'
						&& !/^\s*\d+\s*$/.test(form.findField('claimNo').getValue())){
							Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), '付款方式为银行卡时，款项确认编号必须输入数字。','error',3000);//付款方式为银行卡时，款项确认编号必须输入数字。
					}
					//付款方式为“月结”或“临欠”，则客户不可为空
					else if((form.findField('paymentType').getValue()=='CT'||form.findField('paymentType').getValue()=='DT')
							&& form.findField('consigneeCode').getValue()=='')
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.consigneeCodeNotNull'), 'error', 3000);
					}
					//付款方式为“月结”或“临欠”，不可以存在付款信息
					else if((form.findField('paymentType').getValue()=='CT'||form.findField('paymentType').getValue()=='DT')
							&& actualFreightFee > 0)
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.cannotCTDT'), 'error', 3000);
					}
					//实付运费不能大于应收到付款
					else if(form.findField('actualFreight').getValue() + actualFreightFee>form.findField('receiveablePayAmoout').getValue() && sign.expSettlePayment.aos !='Y')
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.actualFreightNotGTReceiveablePayAmoout'), 'error', 3000);
					}
					//代收货款不能大于应收代收款
					else if(form.findField('codAmount').getValue() + codAmountFee >form.findField('receiveableAmount').getValue())
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.codAmountNotGTReceiveableAmount'), 'error', 3000);
					}
					//实付运费和代收货款必须大于等于0
					else if(form.findField('actualFreight').getValue()< 0 || form.findField('codAmount').getValue()<0)
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.actualFreightGTZero'), 'error', 3000);
					}
					else if ((form.findField('actualFreight').getValue() == 0 && form.findField('codAmount').getValue() == 0) &&
							    (form.findField('receiveableAmount').getValue() > 0 || form.findField('receiveablePayAmoout').getValue() > 0) &&
							    form.findField('paymentType').getValue() !='CT' && form.findField('paymentType').getValue() !='DT')
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.actualFreightcodAmountGTZero'), 'error', 3000);
					}
					else if((form.findField('paymentType').getValue() =='CT' || form.findField('paymentType').getValue() =='DT')
							   && form.findField('receiveableAmount').getValue() > 0)
					{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.notCTDT'), 'error', 3000);
					}	
					// 统一结算
					else if(form.findField('paymentType').getValue()==null && form.findField('paymentType').getValue() == ""){
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), '请选择付款方式', 'error', 3000);
					}
//					else if(wayformparams.arriveCentralizedSettlement == 'Y'
//						&& form.findField('actualFreight').getValue() > 0 
//						&& form.findField('codAmount').getValue() == 0
//							&&form.findField('paymentType').getValue() !='CT'
//							&&form.findField('paymentType').getValue() !='TT'){
//							Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), '实付运费>0，代收=0时，以统一结算标准限制付款方式（需为月结、电汇）', 'error', 3000);	
//					}
//					else if(wayformparams.arriveCentralizedSettlement == 'Y'
//						&& form.findField('actualFreight').getValue() == 0 
//						&& form.findField('codAmount').getValue() > 0
//						&& (form.findField('paymentType').getValue() =='CT'
//							||form.findField('paymentType').getValue() =='TT')){
//						 
//						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), '实付运费=0，代收>0时，以代收货款标准限制付款方式（不可为临欠、月结）', 'error', 3000);
//					}
//					else if(wayformparams.arriveCentralizedSettlement == 'Y'
//						&& form.findField('actualFreight').getValue() > 0 
//						&& form.findField('codAmount').getValue() > 0
//						&& form.findField('paymentType').getValue() !='TT'){
//						
//						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), '实付运费>0，代收>0时，以统一结算标准付款方式限制（只可为电汇）', 'error', 3000);
//					}
					
					else
					{
						//如果有代收货款
						if( (form.findField('codAmount').getValue()>0))
						{
							passWordWin = Ext.create('Foss.sign.expSettlePayment.PasswordWindow').show();
						}
						else
						{
							Ext.Ajax.request({
							    url:sign.realPath('addPaymentInfo.action'),
							    timeout: 180000,
							    jsonData:{
							    	'vo':{
							    	   'repaymentEntity':formparams
							    	}
							    },
						    	success:function(response){
							    	form.findField('consigneeCode').setValue("");
							    	form.findField('deliverymanName').setValue("");
							    	form.findField('identifyType').setValue("ID_CARD");
							    	form.findField('identifyCode').setValue("");
							    	//form.findField('paymentType').setValue("CH");
							    	form.findField('claimNo').setValue("");
							    	form.findField('claimNo').setReadOnly(false);
							    	form.findField('claimNo').allowBlank=false;
							    	form.findField('actualFreight').setValue("");
							    	form.findField('codAmount').setValue("");
							    	form.findField('totalCollection').setValue("");
							    	form.findField('actualFreight').setReadOnly(false);
							    	waitingProcessGrid.store.removeAll()
							    	Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentSuccessful'), 'ok', 3000);
							    	waitingProcessGrid.store.load();
							    	paymentGrid.store.removeAll();
							    	
					    		},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
									//waitingProcessGrid.store.removeAll()
									//waitingProcessGrid.store.load();
									//paymentGrid.store.removeAll();
			              			Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentFailed'), json.message, 'error', 3000);
								}
							});
						}
					}
				}
			}]
		}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
});

//付款记录
Ext.define('Foss.sign.expSettlePayment.PaymentGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_sign_expSettlePayment_PaymentGrid_Id',
	//增加表格列的分割线
	columnLines: true,
	//表格对象增加一个边框
	stripeRows: true,
	emptyText: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.emptyText'),//查询结果为空
	height:180,
	store: Ext.create('Foss.sign.expSettlePayment.PaymentStore'),	 
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
		{ 
			header: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentType'),// 付款方式
			//关联model中的字段名
			dataIndex: 'paymentType',
			align: 'center',
			width:130
		},{ 
			header: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.actualFreight'),//实付运费 
			//关联model中的字段名
			dataIndex: 'actualFreight',
			align: 'center',
			width:130
		},{
			header: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.codAmount'),//代收货款 
			//关联model中的字段名
			dataIndex: 'codAmount', 
			align: 'center',
			width:130
		},{
			header: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.stlbillGeneratedStatus'),//是否已有财务单据 
			//关联model中的字段名
			dataIndex: 'stlbillGeneratedStatus', 
			align: 'center',
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED'){
					return sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.yes');//是
				}else{
					return sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.no');//否
				}
			},
			width:130
		},{
			header: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentTime'),//付款时间 
			//关联model中的字段名
			dataIndex: 'paymentTime', 
			align: 'center',
			width:180,
			renderer : function(value) {
				if (value != null) {
					var date = Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
					return date;
				} else {
					return null;
				}
			} 	
		}],
		constructor: function(config){
			var me = this,
			cfg = Ext.apply({}, config);
			me.dockedItems = [{
				xtype: 'toolbar',
				dock: 'bottom',
				layout:'column',
				defaults:{
					margin:'0 0 5 3',
					allowBlank:true
				},		
				items: [{
					xtype: 'container',
					border : false,
					columnWidth:.7,
					html: '&nbsp;'
				},{
					xtype:'button',
					allowBlank:true,
					id:'Foss_sign_expSettlePayment_quickPayment_Id',
					name:'addException',
					columnWidth:.15,
					text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.quickPayment'),//快速结清
					disabled:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquicksettlebutton'),
					hidden:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquicksettlebutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						  //设定间隔秒数,如果不设置，默认为2秒
						  seconds: 3
					}),
					handler : function() {
						var waybillNo = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
						var paymentGridStore = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id').getStore();
						selectRow = waitingProcessGrid.getSelectionModel().getSelection();

						if (selectRow.length == 0)
						{
							Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.choseWaybillBefore'), 'error', 3000);
						}else{
							Ext.Ajax.request({
							    url:sign.realPath('quickPayment.action'),
								params:{'vo.repaymentEntity.waybillNo':waybillNo},
								success:function(response){

									Ext.Ajax.request({
									    url:sign.realPath('queryPaymentByWaybillNo.action'),
									    params: {
									    	'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
									    },
										    success: function(response){
										    	var result = Ext.decode(response.responseText);
										    	var signInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm();
												var waybillInfoForm = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm();
												//得到运单基本信息
												var wayBillInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.waybillDto,
														 'Foss.sign.expSettlePayment.model.WayBillInfoModel');
												var signInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.financialDto,
												 		 'Foss.sign.expSettlePayment.model.FinancialModel');
												
												// 统一结算限制
												// 到达金额>0 到达统计结算
												if(signInfoModel.data.receiveablePayAmoout > 0
														  && wayBillInfoModel.data.arriveCentralizedSettlement == 'Y'){
													//signInfoForm.findField('consigneeCode').setValue(wayBillInfoModel.data.receiveCustomerCode);//到达客户
													//signInfoForm.findField('consigneeCode').setRawValue(wayBillInfoModel.data.receiveCustomerName);//到达客户
													signInfoForm.findField('consigneeCode').setCombValue(wayBillInfoModel.data.receiveCustomerName,wayBillInfoModel.data.receiveCustomerCode);
													signInfoForm.findField('consigneeCode').setReadOnly(true);
													
													var newData = Ext.clone(sign.expSettlePayment.CentralizedPaymentStore.data);
													if(signInfoForm.findField('paymentType').store == null){
														var newStore = Ext.create('Ext.data.Store',{
															fields:['valueCode','valueName'],
														    data : newData
														});
														signInfoForm.findField('paymentType').store = newStore;
													}else{
														signInfoForm.findField('paymentType').store.removeAll();
														signInfoForm.findField('paymentType').store.loadData(newData.items);
													}
													
													signInfoForm.findField('paymentType').value = "";//置空
													signInfoForm.findField('paymentType').setRawValue("");
												}else{
													signInfoForm.findField('consigneeCode').setValue('');//到达客户
													signInfoForm.findField('consigneeCode').setRawValue("");
													signInfoForm.findField('consigneeCode').setReadOnly(false);
													
													var newData = Ext.clone(sign.expSettlePayment.PaymentStore.data);
													if(signInfoForm.findField('paymentType').store == null){
														var newStore = Ext.create('Ext.data.Store',{
															fields:['valueCode','valueName'],
														    data : newData
														});
														signInfoForm.findField('paymentType').store = newStore;
													}else{
														signInfoForm.findField('paymentType').store.removeAll();
														signInfoForm.findField('paymentType').store.loadData(newData.items);
													}
													
													signInfoForm.findField('paymentType').setValue("CH");//置空
													signInfoForm.findField('paymentType').setRawValue("现金");
												}
												
												//加载签收信息
												signInfoForm.loadRecord(signInfoModel);
												//加载运单基本信息
												waybillInfoForm.loadRecord(wayBillInfoModel);
												//加载付款信息
												sign.expSettlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
												//是否有仓储费
												if(result.vo.repaymentDto.waybillDto.storageCharge)
												{
													signInfoForm.findField('storageCharge').setValue(result.vo.repaymentDto.waybillDto.storageCharge);
												}
												else
												{
													signInfoForm.findField('storageCharge').setValue('0');
												}
												//运单是否有代收货款 若无代收货款 则签收信息中代收货款不可编辑
												if(result.vo.repaymentDto.waybillDto.codAmount==0 || result.vo.repaymentDto.waybillDto.codAmount==null)
												{
													signInfoForm.findField('codAmount').setValue('0');
													signInfoForm.findField('codAmount').setReadOnly(true);
												}
												else
												{
													signInfoForm.findField('codAmount').setReadOnly(false);
												}
												var paymentGrid = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id');
												settleRow = paymentGrid.store.data.length;
												var store = sign.expSettlePayment.PaymentGrid.store;
												
												Ext.getCmp('Foss_sign_expSettlePayment_addException_Id').setDisabled(true);
												Ext.getCmp('Foss_sign_expSettlePayment_quickPayment_Id').setDisabled(true);

												store.each(function(record, index, allRecords){
													if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
														Ext.getCmp('Foss_sign_expSettlePayment_addException_Id').setDisabled(false);
														Ext.getCmp('Foss_sign_expSettlePayment_quickPayment_Id').setDisabled(false);
													}
												});
												
												Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentSuccessful'), 'ok', 3000);
												waitingProcessGrid.store.removeAll()
												waitingProcessGrid.store.load();
												paymentGridStore.removeAll();
										    }
									});
								
								},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
									waitingProcessGrid.store.removeAll()
									waitingProcessGrid.store.load();
									paymentGridStore.removeAll();
			              			Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentFailed'), json.message, 'error', 3000);
								}
							});
						}
					}
				},{
					xtype:'button',
					allowBlank:true,
					id:'Foss_sign_expSettlePayment_addException_Id',
					name:'addException',
					columnWidth:.15,
					text: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.refuse'),//快速反结清
					disabled:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquickreverseybutton'),
					hidden:!sign.expSettlePayment.isPermission('expSettlepaymentindex/expSettlepaymentindexquickreverseybutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						  //设定间隔秒数,如果不设置，默认为2秒
						  seconds: 3
					}),
					handler : function() {
						var waybillNo = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_expSettlePayment_WaitingProcessGrid_Id');
						selectRow = waitingProcessGrid.getSelectionModel().getSelection();

						if (selectRow.length == 0)
						{
							Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.choseWaybillBeforeRefund'), 'error', 3000);
						}
						else
						{
							Ext.Ajax.request({
							    url:sign.realPath('refundPaymentInfo.action'),
								params:{'vo.repaymentEntity.waybillNo':waybillNo},
								success:function(response){
									Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.RefundSuccessful'), 'error', 3000);
									var store = sign.expSettlePayment.PaymentGrid.store,
										recordArray = new Array();
									Ext.getCmp('Foss_sign_expSettlePayment_addException_Id').setDisabled(true);
									Ext.getCmp('Foss_sign_expSettlePayment_quickPayment_Id').setDisabled(true);
									store.each(function(record, index, allRecords){
										if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
											recordArray.push(record);
										}
									});
									store.remove(recordArray);
								},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), json.message, 'error', 3000);
								}
							});
						}
					}
				}]
		}],
		me.callParent([cfg]);
		}
	});	

//运单基本信息
Ext.define('Foss.sign.expSettlePayment.WaybillInfoForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_sign_expSettlePayment_WaybillInfoForm_Id',
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
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerName'),//收货人
		columnWidth: .33
	},{
		name:'receiveCustomerAddress',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerAddress'),//收货客户地址
		columnWidth: .33
	},{
		name:'receiveCustomerPhone',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveCustomerPhone'),//收货人电话
		columnWidth: .33
	},{
		name:'waybillNo',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillNo'),//单号
		columnWidth: .33
	},{
		xtype: 'datefield',
		name:'arriveTime',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.arriveTime'),//到达时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'stockStatus',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.stockStatus'),//库存状态
		columnWidth: .33
	},{
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.notificationResult'),//是否通知成功
		xtype: 'radiogroup',
        vertical: true,
		columnWidth:.33,
		anchorSize: 50,
		labelWidth:120,
		layout:'column',
		columns: 1,
		name: 'notification',
		items: [
            { itemId: 'yes', boxLabel: '是', name: 'notificationResult', inputValue: 'SUCCESS', readOnly : true},
			{ xtype: 'container',border : false,columnWidth:.5	,html: '&nbsp;'},
            { itemId: 'no', boxLabel: '否', name: 'notificationResult', inputValue: 'FAILURE', checked: true, readOnly : true}
        ]
	},{
		xtype: 'datefield',
		name:'notificationTime',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.notificationTime'),//上次通知时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'paidMethod',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paidMethod'),//付款方式
		columnWidth: .33
	},{
		name:'toPayAmount',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.toPayAmount'),//到付总额
		columnWidth: .33
	},{
		name:'codAmount',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.codAmount'),//代收货款
		columnWidth: .33
	},{
		name:'transportFee',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.transportFee'),//运费
		columnWidth: .33
	},{
		name:'deliveryGoodsFee',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.deliveryGoodsFee'),//送货费
		columnWidth: .33
	},{
		name:'insuranceAmount',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.insuranceAmount'),//货物价值
		columnWidth: .33
	},{
		name:'insuranceFee',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.insuranceFee'),//保价费
		columnWidth: .33
	},{
		name:'otherFee',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.otherFee'),//其他费用
		columnWidth: .33
	},{
		name:'receiveMethod',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveMethod'),//派送方式
		columnWidth: .33
	},{
		name:'goodsName',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.goodsName'),//货物名称
		columnWidth: .33
	},{
		name:'goodsQtyTotal',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.goodsQtyTotal'),//件数
		columnWidth: .33
	},{
		name:'goodsWeightTotal',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.goodsWeightTotal'),//重量
		columnWidth: .33
	},{
		name:'goodsVolumeTotal',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.goodsVolumeTotal'),//体积
		columnWidth: .33
	},{
		name:'goodsPackage',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.goodsPackage'),//包装
		columnWidth: .33
	},{
		name:'deliveryCustomerName',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.deliveryCustomerName'),//发货人
		columnWidth: .33
	},{
		name:'deliveryCustomerCityName',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.deliveryCustomerCityName'),//始发站
		columnWidth: .33
	},{
		name:'receiveOrgName',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.receiveOrgName'),//始发部门
		columnWidth: .33
	},{
		name:'transportType',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.transportType'),//运输类型
		columnWidth: .33
	},{
		name:'productCode',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.productCode'),//运输性质
		columnWidth: .33
	},{
		xtype: 'datefield',
		name:'createTime',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.createTime'),//出发时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'settleStatus',
		fieldLabel: sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.settleStatus'),//结清状态
		hidden: true,
		columnWidth: .33
	},{
		name:'arriveCentralizedSettlement',
		hidden: true,
		columnWidth: .1
	}]
});

Ext.define('Foss.sign.expSettlePayment.PassWordFormPanel',{
	   extend: 'Ext.form.Panel',
	   autoWidth:true,
	   autoHigth: true,
	   layout:"column",
	   labelAlign:"right",
	   defaults: {
			margin:'10 10 5 10'
	   },
	   items:[{
	    xtype:"label",
	    height  : 10,
	    labelWidth:80,
		text : sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.password'),//操作人密码:
		columnWidth:.33
	   },{
	    xtype : "textfield",
	    inputType : 'password',
	    height : 25,
		columnWidth:.63,
	    hideLabel : true,
	    allowBlank:false,
	    id:"expCancelarea",
	    anchor : "90%",
	    msgTarget: 'side'
	   }],  
	   buttons : [{
			text : sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.confirm'),//确定
			columnWidth:.5,
			margin:'0 25 0 0',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 2
			}),
			handler : function(){
				var ps = Ext.getCmp('expCancelarea').value;
				var form=this.up('form').getForm();
				var signform = Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm();
				var formparams = signform.getValues();
				var consigneeName =Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm().findField('consigneeCode').getRawValue();
				var waybillNo = Ext.getCmp('Foss_sign_expSettlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
				var storageFee = Ext.getCmp('Foss_sign_expSettlePayment_SignInfoForm_Id').getForm().findField('storageCharge').getValue();
				var paymentGridStore = Ext.getCmp('Foss_sign_expSettlePayment_PaymentGrid_Id').getStore();
				formparams.waybillNo = waybillNo;
				formparams.consigneeName = consigneeName;
				formparams.storageFee = storageFee;
				formparams.passWord = ps;
				if(form.isValid()){
					Ext.Ajax.request({
					    url:sign.realPath('validatePaymentInfo.action'),
					    jsonData:{
					    	'vo':{
					    	   'repaymentEntity':formparams
					    	}
					    },
				    	success:function(response){
				    		signform.findField('consigneeCode').setValue("");
				    		signform.findField('deliverymanName').setValue("");
				    		signform.findField('identifyType').setValue("ID_CARD");
				    		signform.findField('identifyCode').setValue("");
				    		//signform.findField('paymentType').setValue("CH");
				    		signform.findField('claimNo').setValue("");
				    		signform.findField('claimNo').setReadOnly(false);
				    		signform.findField('claimNo').allowBlank=false;
				    		signform.findField('actualFreight').setValue("");
				    		signform.findField('codAmount').setValue(""); 
				    		signform.findField('totalCollection').setValue("");
				    		form.findField('expCancelarea').setValue("");
				    		paymentGridStore.removeAll();
					    	Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'), sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentSuccessful'), 'success', 3000);
							passWordWin.close();
			    		},
			    		exception: function(response){
							var json = Ext.decode(response.responseText);
							form.findField('expCancelarea').setValue("");
							paymentGridStore.removeAll();
	              			Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentFailed'), json.message, 'error', 3000);
						}
					});
					}else{
						Ext.ux.Toast.msg(sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.tip'),sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.passwordNotNull'),'error',3000);
					}
				}
	   	},{
			columnWidth:.5,
			margin:'0 25 0 0',
			text : sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.cancel'),//取消
			handler : function(){
				passWordWin.close();
			}
	   }]
	   
	});

//密码输入panel
sign.expSettlePayment.passWordFormPanel = Ext.create('Foss.sign.expSettlePayment.PassWordFormPanel');

//定义密码输入window
Ext.define('Foss.sign.expSettlePayment.PasswordWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	width : 350,
	height : 180,
	items : [sign.expSettlePayment.passWordFormPanel],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

sign.expSettlePayment.QueryPaymentGrid = Ext.create('Foss.sign.expSettlePayment.WaitingProcessGrid');
//左边面板
Ext.define('FOSS.expSettlePayment.Panel.LeftPanel',{
		extend:'Ext.panel.Panel',
		margin:'10 10 10 10',
		title:sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.pending'),//待处理
		frame:true,
		items:[sign.expSettlePayment.QueryPaymentGrid]
});
	
//右边上面面板-签收信息
Ext.define('FOSS.expSettlePayment.Panel.RightTopPanel',{
		extend:'Ext.panel.Panel',
		title:sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.signInformation'),//签收信息
		margin:'10 0 10 0',
		frame:true,
		items:[Ext.create('Foss.sign.expSettlePayment.SignInfoForm')]
		
});

sign.expSettlePayment.PaymentGrid = Ext.create('Foss.sign.expSettlePayment.PaymentGrid');
//右边中间面板-付款记录
Ext.define('FOSS.expSettlePayment.Panel.MiddlePanel',{
		extend:'Ext.panel.Panel',
		title:sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.paymentRecords'),//付款记录
		margin:'10 0 10 0',
		frame:true,
		items:[sign.expSettlePayment.PaymentGrid]
		
});

//右边下面面板-运单基本信息
Ext.define('FOSS.expSettlePayment.Panel.RightBottomPanel',{
		extend:'Ext.panel.Panel',
		title:sign.expSettlePayment.i18n('pkp.sign.expSettlePayment.waybillInformation'),//运单基本信息
		margin:'10 0 10 0',
		frame:true,
		items:[Ext.create('Foss.sign.expSettlePayment.WaybillInfoForm')]
		
});

//右边面板
Ext.define('FOSS.expSettlePayment.Panel.RightPanel',{
		extend:'Ext.panel.Panel',		
		items:[Ext.create('FOSS.expSettlePayment.Panel.RightTopPanel',{height:290}),
		       Ext.create('FOSS.expSettlePayment.Panel.MiddlePanel',{height:230}),
			   Ext.create('FOSS.expSettlePayment.Panel.RightBottomPanel',{height:410})
			  ]
});

//将约车信息、车辆信息、申请信息、货物信息、审核记录组合显示
Ext.define('FOSS.expSettlePayment.Container',{
	  extend: 'Ext.container.Container',
	  layout: 'column',
	  frame: false,
	  cls: 'autoHeight',
	  bodyCls: 'autoHeight',  
	  lefttPanel: null,
	  getLefttPanel: function(){
	    if(this.lefttPanel==null){
	      this.lefttPanel = Ext.create('FOSS.expSettlePayment.Panel.LeftPanel',{width:230,minHeight:950});
	    }
	    return this.lefttPanel;
	  },
	  rightPanel: null,
	  getRightPanel: function(){
	    if(this.rightPanel==null){
	      this.rightPanel = Ext.create('FOSS.expSettlePayment.Panel.RightPanel',{width:780});
	    }
	    return this.rightPanel;
	  }, 
	  
	  constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
	      me.items = [
	      me.getLefttPanel(),
	      me.getRightPanel()
	    ]; 
			me.callParent([cfg]);
		}
}); 


Ext.onReady(function() {
	Ext.QuickTips.init();
	sign.expSettlePayment.settlePaymentForm = Ext.create('Foss.sign.expSettlePayment.SettlePaymentForm'); 
	sign.expSettlePayment.container=Ext.create('FOSS.expSettlePayment.Container');
	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-expSettlePaymentIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [sign.expSettlePayment.settlePaymentForm,sign.expSettlePayment.container],
		renderTo: 'T_sign-expSettlePaymentIndex-body'
	});
});
