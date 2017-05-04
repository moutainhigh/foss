/**
 *author : 043258-foss-zhaobin
 *page : 结清货款 
*/

(function(){
	sign.settlePayment.PaymentStore =FossDataDictionary.getDataDictionaryStore('REPAYMENT_PAYMENT_TYPE', null);
	
	//统一结算零担付款方式
	sign.settlePayment.CentralizedPaymentStore =
		Ext.create('Ext.data.Store',{
			fields:['valueCode','valueName'],
		    data : [{
				'valueCode' : 'DT',
				'valueName' : '临时欠款'
			},{
				'valueCode' : 'CT',
				'valueName' : '月结'
			},{
				'valueCode' : 'TT',
				'valueName' : '电汇'
			},{
				'valueCode' : 'BE',
				'valueName' : '余额'//add by 353654
			}]
		});
})();

//add by chenjunying DMANA-10629 保存扫描获取的收货人身份证号
sign.settlePayment.IDENTITY_CODE='';
//add by chenjunying DMANA-10629 保存扫描获取的代收人身份证号
sign.settlePayment.COD_IDENTITY_CODE='';

//时间默认值
sign.settlePayment.getTargetDate = function(date, day) {
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
sign.settlePayment.checkIdcard = function checkIdcard(idcard){ 
	var Errors=new Array( 
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeThrough'), //身份验证通过!
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeDigitsWrong'), //身份证号码位数不对!
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeIllegal'), //身份证号码出生日期超出范围或含有非法字符!
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeError'), //身份证号码校验错误!
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeAreaIllegal') ,//身份证地区非法!
			sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCodeIsNotNull')//身份证不能为空!
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
Ext.define('Foss.sign.settlePayment.SettlePaymentTypeStore',{
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
Ext.define('Foss.sign.settlePayment.SettlePaymentModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'waybillNo'},//运单号
		{name : 'storageQty'},//件数
		{name : 'receiveCustomerName'}//收货人(收货客户名称)
			]
});

//财务信息
Ext.define('Foss.sign.settlePayment.model.FinancialModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'receiveableAmount',type:'float',defaultValue: 0},//应收代收款
		{name : 'receivedAmount',type:'float',defaultValue: 0},//已收代收款
		{name : 'receiveablePayAmoout',type:'float',defaultValue: 0},//应收到付款
		{name : 'receivedPayAmount',type:'float',defaultValue: 0}//已收到付款
		]
});

//运单基本信息
Ext.define('Foss.sign.settlePayment.model.WayBillInfoModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'waybillNo'},// 运单号
		{name: 'receiveCustomerName'},// 收货人(收货客户名称)
		{name: 'receiveCustomerCode'},//收货客户编码
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
			return FossDataDictionary.rendererSubmitToDisplay(value, 'REPAYMENT_PAYMENT_TYPE');
		}},// 付款方式（出发部门）
		{name: 'otherFee',defaultValue: 0},// 其他费用
		{name: 'insuranceAmount'},// 货物价值
		{name: 'stockStatus'},//库存状态
		{name: 'receiveMethod',convert:function(value) {
			var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
			if(Ext.isEmpty(v) || value == v){
				v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
			}
			return v;
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
		{name: 'storageCharge',type:'float',defaultValue: 0},// 仓储费
		{name:'invoice'}//发票标记
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
Ext.define('Foss.sign.settlePayment.SettlePaymentStore',{
	extend: 'Ext.data.Store',
	//绑定一个模型
	model: 'Foss.sign.settlePayment.SettlePaymentModel',
	storeId:'Foss_sign_settlePayment_SettlePayment_Store',
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
			var queryParams=sign.settlePayment.settlePaymentForm.getValues();
						
			Ext.apply(operation,{
				params:{
					'vo.repaymentDto.airAgencyQueryDto.waybillNo':queryParams.waybillNo,// 运单号
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerName':queryParams.receiveCustomerName,// 收货人(收货客户名称)
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerPhone':queryParams.receiveCustomerPhone,//  收货客户电话
					'vo.repaymentDto.airAgencyQueryDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,// 收货人手机
					'vo.repaymentDto.airAgencyQueryDto.productCode':queryParams.productCode,// 运输性质
					'vo.repaymentDto.airAgencyQueryDto.storageTimeBegin':queryParams.storageTimeBegin,// 入库时间
					'vo.repaymentDto.airAgencyQueryDto.storageTimeEnd':queryParams.storageTimeEnd,// 入库时间
					'vo.repaymentDto.airAgencyQueryDto.isExpress':'N'// 是否快递字段
				}
			});
		},
		load : function(store, records, successful, oOpts) {
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), data.message, 'error', 2000);	//提示			
				return;
			}
			var grid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
			if(store.data.length>0)
			{
				grid.getSelectionModel().select(0);
				var record = Ext.create('Foss.sign.settlePayment.SettlePaymentModel',grid.getStore().getAt(0).data);
				grid.fireEvent('itemclick',grid,record);
			}	
		}
	}
});

//定义付款记录模型
Ext.define('Foss.sign.settlePayment.PaymentModel', {
	extend: 'Ext.data.Model',
	fields: [
				{ name: 'paymentType',convert:function(value) {
					return FossDataDictionary.rendererSubmitToDisplay(value, 'REPAYMENT_PAYMENT_TYPE');
				}},//付款方式
			    { name: 'actualFreight'},//实付运费
			    { name: 'codAmount'},//代收货款
			    { name: 'stlbillGeneratedStatus'},//是否已有财务单据
			    { name: 'paymentTime',convert:dateConvert}//付款时间
			 ]
});

//创建一个变更内容store
Ext.define('Foss.sign.settlePayment.PaymentStore', {
	extend: 'Ext.data.Store',
	//绑定一个模型
	model:'Foss.sign.settlePayment.PaymentModel',
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
Ext.define('Foss.sign.settlePayment.SettlePaymentForm',{
		extend:'Ext.form.Panel',
		id:'Foss_sign_settlePayment_SettlePaymentForm_Id',
		layout:'column',		
		cls:'autoHeight',
		bodyCls:'autoHeight',	
		collapsible: true,
		animCollapse: true,
		title:sign.settlePayment.i18n('pkp.sign.settlePayment.queryCondition'),//查询条件
		frame:true,
		defaultType: 'textfield',	
		defaults: {
			margin:'5 10 5 10',
			//anchor: '90%',
			labelWidth:100
		},
		items: [{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.waybillNo'),//单号
			name: 'waybillNo',
			allowBlank:true,
			vtype: 'waybill',
			columnWidth:.25,
			listeners:{
				change:function(field,new_v,old_v){
					if(!Ext.isEmpty(new_v)&& new_v.length >=9){
						//先去两端的空格
						var new_value = new_v.trim();
						//再把值设置给文本框
						field.setValue(new_value.substring(0,new_value.length>=9?9:new_value.length));
					}
				},
				specialkey: function(field, e){
                    if (e.getKey() == e.ENTER) {
                    	var grid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
                    	grid.getStore().load();
                    }
                }
			}
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerName'),//收货人
			name: 'receiveCustomerName',
			allowBlank:true,
			columnWidth:.25			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerPhone'),//收货客户电话
			name: 'receiveCustomerPhone',
			allowBlank:true,
			columnWidth:.25			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerMobilephone'),//收货人手机
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
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.productCode'),//运输性质
			name:'productCode',
			columnWidth: 0.25,
			value:'ALL',
			store:Ext.create('Foss.sign.settlePayment.SettlePaymentTypeStore',
			{
			  data:{
			       'items':[
						{'code':'ALL','name':'全部'},
						{'code':'FLF','name':'精准卡航'},
						{'code':'PCP','name':'精准包裹'},//331556 fanjingwei 德邦E家项目增加 2016-07-20
						{'code':'FSF','name':'精准城运'},
						{'code':'LRF','name':'精准汽运(长途)'},
						{'code':'SRF','name':'精准汽运(短途)'},
						{'code':'WVH','name':'整车'},
						{'code':'BGFLF','name':'精准大票卡航'},
						{'code':'BGFSF','name':'精准大票城运'},
						{'code':'BGLRF','name':'精准大票汽运(长)'},
						{'code':'BGSRF','name':'精准大票汽运(短)'},
						{'code':'DTD','name':'精准大票.经济件'},
						{'code':'YTY','name':'精准大票.标准件'}
						
				   ]
			  }
			})
		},{
			xtype: 'rangeDateField',
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.storeTime'),//入库时间
			fieldId: 'FOSS_ExceptionTime_Id',
			dateType: 'datetimefield_date97',
			fromName: 'storageTimeBegin',
			toName: 'storageTimeEnd',
			disallowBlank:true,
			editable:false,
			fromValue: Ext.Date.format(sign.settlePayment.getTargetDate(new Date(),-3),'Y-m-d H:i:s'),
			toValue: Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			columnWidth: .5
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text: sign.settlePayment.i18n('pkp.sign.settlePayment.reset'),//重置
				columnWidth:.08,
				handler:function()
				{
					var form = this.up('form').getForm();
					form.findField('waybillNo').setValue("");
					form.findField('receiveCustomerName').setValue("");
					form.findField('receiveCustomerPhone').setValue("");
					form.findField('receiveCustomerMobilephone').setValue("");
					form.findField('productCode').setValue("ALL");
					form.findField('storageTimeBegin').setValue(Ext.Date.format(sign.settlePayment.getTargetDate(new Date(),-3),'Y-m-d H:i:s'));
					form.findField('storageTimeEnd').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
				}
			},{
				xtype: 'container',
				border : false,
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text: sign.settlePayment.i18n('pkp.sign.settlePayment.search'),//查询
				hidden:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexquerybutton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function()
				{
					var form = this.up('form').getForm();
					var storageTimeBegin = form.getValues().storageTimeBegin;
					var storageTimeEnd = form.getValues().storageTimeEnd;
					var result = Ext.Date.parse(storageTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(storageTimeBegin,'Y-m-d H:i:s');
					if(result / (24 * 60 * 60 * 1000) >= 30){
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'),sign.settlePayment.i18n('pkp.sign.settlePayment.dateintervalError'),'error', 3000);
						return;
					}
					if(form.isValid())
					{
						signInfoForm = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm(),
						waybillInfoForm = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm();
						paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id').getStore();
						detaiGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id').getStore();
						signInfoForm.reset();//签收信息重置
						paymentGrid.removeAll();//付款信息重置
						waybillInfoForm.reset();//运单基本信息重置
						detaiGrid.removeAll();//清空待处理列表
			//			signInfoForm.findField('claimNo').setReadOnly(true);
						Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id').getStore().load();
					}
					else
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.waybillNoError'), 'error', 3000);
					}	
				}
			}]
		}]
	});

//待处理表格
Ext.define('Foss.sign.settlePayment.WaitingProcessGrid', {
	extend: 'Ext.grid.Panel',	
	id:'Foss_sign_settlePayment_WaitingProcessGrid_Id',
	emptyText: sign.settlePayment.i18n('pkp.sign.settlePayment.emptyText'),//查询结果为空
	collapsible: true,
	animCollapse: false,
	hideCollapseTool:true,
	titleCollapse:true,
	height:918,
	cls:'autoHeight',
	bodyCls:'autoHeight',
	store: null,
	// 定义选择框
	//selModel : Ext.create('Ext.selection.CheckboxModel'),
    viewConfig: {
        enableTextSelection: true
     },
	columns: [{
		text: sign.settlePayment.i18n('pkp.sign.settlePayment.waybillNo'),//单号
		sortable: true, 
		dataIndex: 'waybillNo',
		width:75
	},{
		text: sign.settlePayment.i18n('pkp.sign.settlePayment.storageQty'),//件数
		sortable: true, 
		dataIndex: 'storageQty',
		width:45
	},{
		text: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerName'),//收货人
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
				    	var signInfoForm = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
						var waybillInfoForm = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm();
						//得到运单基本信息
						var wayBillInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.waybillDto,
								 'Foss.sign.settlePayment.model.WayBillInfoModel');
						var signInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.financialDto,
						 		 'Foss.sign.settlePayment.model.FinancialModel');
						//得到返单类型，如果不为NONE。则提示
						if(result.vo.repaymentDto.waybillDto.returnbillType != 'NONE'){
							Ext.Msg.show({
								msg: sign.settlePayment.i18n('pkp.sign.settlePayment.waybillsign'),
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.WARNING,
								componentCls: 'yellow_button'
							});
						};
						var isPayByOL =result.vo.repaymentDto.isPayByOL;
						if((!Ext.isEmpty(isPayByOL))&& 'N'==isPayByOL){
							Ext.getCmp('Foss_sign_settlePayment_Payment_Id').setDisabled(true);
							//该运单网上支付未支付完成，需出发部门联系客户完成网上支付或更改付款方式  
							//{0}为网上支付运单，网上支付未完成，无法进行结清货款操作   提示信息
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.hasWaybillNoNotPayByOL', [result.vo.repaymentDto.waybillDto.waybillNo]) ,'error', 5000);
						}else {
							Ext.getCmp('Foss_sign_settlePayment_Payment_Id').setDisabled(false);
						}
						//加载签收信息
						signInfoForm.loadRecord(signInfoModel);
						//加载运单基本信息
						waybillInfoForm.loadRecord(wayBillInfoModel);
						//加载付款信息
						sign.settlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
						//add  by 353654
						//receivedAmount已收代收款
						//receivedPayAmount已收到付款
						var receivedAmount = signInfoForm.findField('receivedAmount').value;
						var receivedPayAmount = signInfoForm.findField('receivedPayAmount').value;
						var customerCode = result.vo.repaymentDto.financialDto.consigneeCode;
						var customerName = result.vo.repaymentDto.financialDto.consigneeName;
						if((receivedAmount > 0 || receivedPayAmount > 0) && (customerCode != null || customerName != null)){
							signInfoForm.findField('consigneeCode').getStore().loadData([
								{'cusCode':customerCode,'name':customerName}
							]);
							signInfoForm.findField('consigneeCode').setValue(customerCode);	
							signInfoForm.findField('consigneeNameCode').setValue(customerCode);
							signInfoForm.findField('consigneeCode').setReadOnly(true);
						}
						//是否有仓储费
						if(result.vo.repaymentDto.waybillDto.storageCharge)
						{
							signInfoForm.findField('storageCharge').setValue(result.vo.repaymentDto.waybillDto.storageCharge);
							//保存仓储费的值，方便判断的时候使用
							signInfoForm.storageCharge = result.vo.repaymentDto.waybillDto.storageCharge;							
							/*signInfoForm.findField('otherRevenueNo').setDisabled(false);
							signInfoForm.findField('otherRevenueNo').setValue('');*/		
						}else
						{   //如果没有仓储费，则清空前面设定的值
							signInfoForm.storageCharge = null;
							signInfoForm.findField('storageCharge').setValue('0');
							/*signInfoForm.findField('otherRevenueNo').setDisabled(true);
							signInfoForm.findField('otherRevenueNo').setValue('');*/
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
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.noCodAmount'), 'error', 3000);
						}
						var paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id');
						settleRow = paymentGrid.store.data.length;
						var store = sign.settlePayment.PaymentGrid.store;
						Ext.getCmp('Foss_sign_settlePayment_addException_Id').setDisabled(true);
						Ext.getCmp('Foss_sign_settlePayment_quickPayment_Id').setDisabled(true);

						store.each(function(record, index, allRecords){
							if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
								Ext.getCmp('Foss_sign_settlePayment_addException_Id').setDisabled(false);
								Ext.getCmp('Foss_sign_settlePayment_quickPayment_Id').setDisabled(false);
							}
						});
						if(index == undefined || index == null)
						{
							index = 0;
						}
						var grid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
						var selection = grid.getStore().getAt(index);
						var	receiveCustomerName = selection.get('receiveCustomerName');
						var	waybillNo = selection.get('waybillNo');
						signInfoForm.findField('waybillNo').setValue(waybillNo);
						//将运单库存状态置为库存中
						waybillInfoForm.findField('stockStatus').setValue('库存中');
						signInfoForm.findField('deliverymanName').setValue(receiveCustomerName);
						//modify by 378375(如果第一次结清查询，就走原来的逻辑！)
						if(receivedAmount == 0 && receivedPayAmount == 0){
						signInfoForm.findField('consigneeCode').setValue("");
						}
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
						signInfoForm.findField('revenuePaymentType').setValue('CH');
						signInfoForm.findField('revenueClaimNo').setReadOnly(true);
						signInfoForm.findField('revenueClaimNo').setValue('');
						signInfoForm.findField('revenueClaimNo').allowBlank=true;
//						signInfoForm.findField('otherRevenueNo').allowBlank=true;
						signInfoForm.findField('revenueClaimNo').allowBlank=true;
						
						// 统一结算限制
						// 到达金额>0 到达统计结算
						if(signInfoModel.data.receiveablePayAmoout > 0
								  && wayBillInfoModel.data.arriveCentralizedSettlement == 'Y'){
							//signInfoForm.findField('consigneeCode').setValue(wayBillInfoModel.data.receiveCustomerCode);//到达客户
							//signInfoForm.findField('consigneeCode').setRawValue(wayBillInfoModel.data.receiveCustomerName);//到达客户
							signInfoForm.findField('consigneeCode').setCombValue(wayBillInfoModel.data.receiveCustomerName,wayBillInfoModel.data.receiveCustomerCode);
							signInfoForm.findField('consigneeCode').setReadOnly(true);
							
							var newData = Ext.clone(sign.settlePayment.CentralizedPaymentStore.data);
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
						}else{  //modify by 378375(如果第一次结清查询，就走原来的逻辑！)
							if(receivedAmount == 0 && receivedPayAmount == 0){
							signInfoForm.findField('consigneeCode').setValue('');//到达客户
							signInfoForm.findField('consigneeCode').setRawValue('');//到达客户
							signInfoForm.findField('consigneeCode').setReadOnly(false);
							}
							
							var newData = Ext.clone(sign.settlePayment.PaymentStore.data);
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
	me.store = Ext.create('Foss.sign.settlePayment.SettlePaymentStore');
	me.callParent([cfg]);	 
	}	
});

var message = null;
var passWordWin = null;
//签收信息表单
Ext.define('Foss.sign.settlePayment.SignInfoForm',{
		extend:'Ext.form.Panel',	
		id:'Foss_sign_settlePayment_SignInfoForm_Id',
		layout:'column',		
		bodyStyle:'padding:5 0 0 0',	
		cls:'autoHeight',		
		bodyCls:'autoHeight',	
		defaultType: 'textfield',
		storageCharge:null,//保管费
		defaults: {
			margin:'2 0 2 4',
			anchor: '100%',
			labelWidth:110			
		},
		items: [{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.consigneeCode'),//客户
			xtype : 'commoncustomerselector',
			all:'true',
			singlePeopleFlag : 'Y',
			isPaging:true, //分页
			listWidth:300,//设置下拉框宽度
			name: 'consigneeCode',
			columnWidth:.34			
		},{
			name: 'consigneeNameCode',
			columnWidth:.35,
			hidden:true
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.deliverymanName'),//提货人姓名
			name: 'deliverymanName',
			maxLength:50,
			allowBlank:false,
			columnWidth:.35			
		},{
			name: 'waybillNo',
			columnWidth:.35,
			hidden:true
		},{  //add by chenjunying 标识收货人身份证是否扫描录入
			name: 'identityIsScan',
			columnWidth:.35,
			hidden:true
		},{	 //add by chenjunying 标识代收人身份证是否扫描录入
			name: 'codIdentityIsScan',
			columnWidth:.35,
			hidden:true
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
					hidden:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentinadvancegoodsbutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin'),
					text: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsInadvance'),//提前找货
					columnWidth:.7,				
					handler: function() {
						var form = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
						var waybillNo = form.findField('waybillNo').getValue();
						if(!Ext.isEmpty(waybillNo)){
						    	Ext.Ajax.request({
							    url:sign.realPath('updateInadvanceGoodsByRepayment.action'),
							    timeout: 180000,
								params:{
							    	'waybillVo.dto.waybillNo':waybillNo// 运单号
							    },
						    	success:function(response){
						    		var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'),json.stateMsg,'ok',4000);
					    		},
					    		exception: function(response){
					    			var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'),json.message,'error',4000);
								}
							});
						}
					}
					}]
		},{
			columnWidth:.34,
			xtype: 'combobox',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			mode:'local',
			queryMode: 'local',		
			triggerAction:'all',
			forceSelection:true,
			editable:true,
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.identifyType'),//证件类型
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
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.identifyCode'),//证件号码
			id: 'identify_Code_ID',
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
					text: sign.settlePayment.i18n('pkp.sign.settlePayment.scanning'),//扫描获取
					columnWidth:.7,				
					handler: function() {
						//add by chenjunying 2015-01-09 DMANA-10629 添加验证，点击扫描获取时，证件类型必须身份证；且运单号有信息才发送请求
						var form = this.up('form').getForm();
						var idCardType=form.findField('identifyType').getValue();
						//获取运单信息Form中的运单号的值
						var waybill = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').
						getForm().findField('waybillNo').getValue();
					
						if('ID_CARD'!=idCardType){ //验证扫描时 证件类型是否是身份证
							alert('获取身份证信息失败，只有身份证能扫描');
						}else{ //验证是否是查出运单信息后，再点击扫描的
							if(waybill==null||waybill==''){
								alert('获取身份证信息失败，未查询出运单信息');
							}else{
								Ext.data.JsonP.request({
						    	url: 'http://localhost:8077/idcard',
								callbackKey: 'callback',		    
								success: function(result, request) {
									if(result.idNo == null){
										alert('获取身份证信息失败');
								    }else{
								    	Ext.getCmp('identify_Code_ID').setValue(result.idNo);
									    //add by chenjunying 扫描获取身份证成功，则将身份证扫描获取的值修改为'Y'
										sign.settlePayment.IDENTITY_CODE = form.findField('identifyCode').getValue();
										form.findField('identityIsScan').setValue('Y');//
								    	}
								},
							     failure : function (result, request) {
							    	 alert('获取身份证信息失败');
							     }
					    	})
						  }
						}
					}
				}]
		},{
			columnWidth:.34,
			xtype: 'combobox',                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       
			mode:'local',
			queryMode: 'local',		
			triggerAction:'all',
			forceSelection:true,
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.codIdentifyType'),//证件类型（代收人）
			name: 'codIdentifyType',
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
			    		form.findField('codIdentifyCode').focus();
			    	}
				 }	
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.codIdentifyCode'),//证件号码（代收人）
			id: 'cod_Identify_Code_ID',
			name: 'codIdentifyCode',
			//allowBlank:false,
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
					text: sign.settlePayment.i18n('pkp.sign.settlePayment.scanning'),//扫描获取
					columnWidth:.7,				
					handler: function() {
						//add by chenjunying 2015-01-10 DMANA-10629 添加验证，点击扫描获取时，证件类型必须身份证；且运单号有信息才发送请求
						var form = this.up('form').getForm();
						var codIdCardType=form.findField('codIdentifyType').getValue();
						//获取运单信息Form中的运单号的值
						var waybill = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').
						getForm().findField('waybillNo').getValue();
						if('ID_CARD'!=codIdCardType){ //验证扫描时 证件类型是否是身份证
							alert('获取代收人身份证信息失败，只有身份证能扫描');
						}else{ //验证是否是查出运单信息后，再点击扫描的
							if(waybill==null||waybill==''){
								alert('获取代收人身份证信息失败，未查询出运单信息');
							}else{
								Ext.data.JsonP.request({
						    	url: 'http://localhost:8077/idcard',
								callbackKey: 'callback',		    
								success: function(result, request) {
									if(result.idNo == null){
										alert('获取代收人身份证信息失败');
								    }else{
								    	Ext.getCmp('cod_Identify_Code_ID').setValue(result.idNo);
									    //add by chenjunying 扫描获取身份证成功，则将 代收人身份证扫描获取的值修改为'Y'
										sign.settlePayment.COD_IDENTITY_CODE = form.findField('codIdentifyCode').getValue();
										form.findField('codIdentityIsScan').setValue('Y');//
								    	}
								},
							     failure : function (result, request) {
							    	 alert('获取身份证信息失败');
							     }
					    	})
						}
					}
				}
			}]
		},{
		    xtype:'combobox',
			displayField:'valueName',
			valueField:'valueCode',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.paymentType'),//付款方式
			name:'paymentType',
			columnWidth: 0.34,
			editable:false,
		//	value:'CD',
			//store:sign.settlePayment.PaymentStore,
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
							form.findField('totalCollection').setValue(form.findField('actualFreight').getValue()+form.findField('codAmount').getValue());

			    		}
			    		else 
		    			{
			    			form.findField('actualFreight').setReadOnly(false);
		    			}
		    			if(form.findField('paymentType').getValue()=='TT'||form.findField('paymentType').getValue()=='OL'||form.findField('paymentType').getValue()=='NT'||form.findField('paymentType').getValue()=='CD')
			    		{
			    			form.findField('claimNo').setReadOnly(false);
			    			form.findField('claimNo').enable();
			    		}
			    		else
			    		{
			    			form.findField('claimNo').setValue("");
			    			form.findField('claimNo').disable();
			    		}
			    	 	//在产生了保管费的情况下 当付款方式选择为月结后，保管费自动归0,
		    			//delete by 353654 关于小票单号的校验
			    		if(form.storageCharge){
			    			if(form.findField('paymentType').getValue()=='CT'){
				    			form.findField('storageCharge').setValue('0');
				    			/*form.findField('otherRevenueNo').setValue('');
				    			form.findField('otherRevenueNo').setDisabled(true);*/
				    			
				    		}else{
				    			form.findField('storageCharge').setValue(form.storageCharge);
//							form.findField('otherRevenueNo').setDisabled(false);
				    		}
			    		}
			    		//TODO 选择余额结清时  add by 353654
			    		if(form.findField('paymentType').getValue()=='BE' && !Ext.isEmpty(form.findField('consigneeCode').getValue())){
			    			//发送ajax调用CUBC的接口,将查询的余额回显到页面
			    			var consigneeCode = form.findField('consigneeCode').getValue();
			    			Ext.Ajax.request({
			                    url : sign.realPath('queryBalanceAmount.action'),//请求路径
			                        params : {
			                            'vo.repaymentEntity.consigneeCode' : form.findField('consigneeCode').getValue()
			                        },
			                    success : function(response) {
			                            var json = Ext.JSON.decode(response.responseText);
			                            //将余额回显到页面
			                            if(json != null && json.balanceAmount != null){
			                            	form.findField('balanceAmount').setValue(json.balanceAmount);
			                            }else{
			                            	form.findField('balanceAmount').setValue('0');
			                            }
			                    },
			                    exception : function(response) {
			                            var json = Ext.decode(response.responseText);
			                            Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), json.message, 'error',3000);
			                    }
			    			});
			    		}
			    	}
					,
			   		'focus':function( _this, _the, _eOpts ){
			   			var form = _this.up('form').getForm();		
			   			var wayForm = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm();
			   			if(wayForm.findField('arriveCentralizedSettlement').getValue() == 'Y'
			   				&&form.findField('receiveablePayAmoout').getValue() > 0
			   				&&form.findField('actualFreight').getValue() == 0){
			   				var newData = Ext.clone(sign.settlePayment.PaymentStore.data);
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
							//_this.setRawValue("");
						}else if(wayForm.findField('arriveCentralizedSettlement').getValue() == 'Y'
			   				&&form.findField('receiveablePayAmoout').getValue() > 0
			   				&&form.findField('actualFreight').getValue() > 0){
							var newData = Ext.clone(sign.settlePayment.CentralizedPaymentStore.data);
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
			//add by 353654 
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.balanceAmount'),//余额回显
			name: 'balanceAmount',
			allowBlank:true,
			maxLength:50,
			readOnly:true,
			columnWidth:.31
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.claimNo'),//款项确认编号
			name: 'claimNo',
			allowBlank:true,
			maxLength:50,
			readOnly:true,
			columnWidth:.35,
			listeners : {
				blur : function(field, event, eOpts) {
					//modify by 353654
					var form = this.up('form').getForm();
//					var form = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
					var waybillNo = form.findField('waybillNo').getValue();
					var paymentType = form.findField("paymentType").getValue();
//					if (field.value != '' && paymentType != 'CD') {
					if (field.value != '') {
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
		              			Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.paymentFailed'), json.message, 'error', 3000);
							}
						});
					}
				}
			}
		},{
			xtype:'numberfield',
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.actualFreight'),//实付运费
			name: 'actualFreight',
			hideTrigger: true,
			maxLength:50,
			//allowDecimals : false,// 不允许有小数点
			allowBlank:false,
			readOnly:false,
			columnWidth:.34,
			listeners :{
				'blur':function(me,theEvent,eOpts)
				{
					var form = this.up('form').getForm();
					form.findField('totalCollection').setValue(form.findField('actualFreight').getValue()+form.findField('codAmount').getValue());
					if(form.findField('paymentType').getValue()!='CT'&&form.storageCharge!=null){
						var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
						var paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id');
						
						Ext.Ajax.request({
							url:sign.realPath('queryPaymentByWaybillNo.action'),
							params: {
								'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
							},
							async:false,
							success: function(response){
					    	var result = Ext.decode(response.responseText);
							//加载付款信息
							sign.settlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
							}
						});
						var store = paymentGrid.getStore();
						var actualFreightFee = 0;
						var codAmountFee = 0
						store.each(function(record, index, allRecords){
							if(record.get('stlbillGeneratedStatus')=='STLBILL_NOGENERATE'||record.get('stlbillGeneratedStatus')=='STLBILL_GENERATEING'){
								actualFreightFee = actualFreightFee + record.get('actualFreight');
								codAmountFee = codAmountFee + record.get('codAmount');
							}
						});
						//如果货款未全部结清，则不允许录入小票号
						/*if((form.findField('actualFreight').getValue() + actualFreightFee!=form.findField('receiveablePayAmoout').getValue())||(form.findField('codAmount').getValue() + codAmountFee !=form.findField('receiveableAmount').getValue())){
							form.findField('otherRevenueNo').setValue('');
							form.findField('otherRevenueNo').setDisabled(true);
						}else{
							form.findField('otherRevenueNo').setDisabled(false);
						}*/
					}
					
				}	
			}
		},{
			xtype:'numberfield',
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.codAmount'),//代收货款
			name: 'codAmount',
			hideTrigger: true,
			maxLength:50,
			//allowDecimals : false,// 不允许有小数点
			allowBlank:false,
			columnWidth:.35,
			listeners :{
				'blur':function(me,theEvent,eOpts)
				{
					var form = this.up('form').getForm();
					form.findField('totalCollection').setValue(form.findField('actualFreight').getValue()+form.findField('codAmount').getValue());
					
					if(form.findField('paymentType').getValue()!='CT'&&form.storageCharge!=null){
						var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
						var paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id');
						
						Ext.Ajax.request({
							url:sign.realPath('queryPaymentByWaybillNo.action'),
							params: {
								'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
							},
							async:false,
							success: function(response){
					    	var result = Ext.decode(response.responseText);
							//加载付款信息
							sign.settlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
							}
						});
						var store = paymentGrid.getStore();
						var actualFreightFee = 0;
						var codAmountFee = 0
						store.each(function(record, index, allRecords){
							if(record.get('stlbillGeneratedStatus')=='STLBILL_NOGENERATE'||record.get('stlbillGeneratedStatus')=='STLBILL_GENERATEING'){
								actualFreightFee = actualFreightFee + record.get('actualFreight');
								codAmountFee = codAmountFee + record.get('codAmount');
							}
						});
						//如果货款未全部结清，则不允许录入小票号
						/*if((form.findField('actualFreight').getValue() + actualFreightFee!=form.findField('receiveablePayAmoout').getValue())||(form.findField('codAmount').getValue() + codAmountFee !=form.findField('receiveableAmount').getValue())){
							form.findField('otherRevenueNo').setValue('');
							form.findField('otherRevenueNo').setDisabled(true);
						}else{
							form.findField('otherRevenueNo').setDisabled(false);
						}*/
					}
				}	
			}
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.totalCollection'),//收款总额
			name: 'totalCollection',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveableAmount'),//应收代收款
			name: 'receiveableAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveablePayAmoout'),//应收到付款
			name: 'receiveablePayAmoout',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receivedAmount'),//已收代收款
			name: 'receivedAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receivedPayAmount'),//已收到付款
			name: 'receivedPayAmount',
			readOnly:true,
			columnWidth:.33			
		},{
			fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.storageCharge'),//保管费
			name: 'storageCharge',
			readOnly:true,
			columnWidth:.33		
		},{
			border : false,
			xtype : 'container',
			columnWidth:0.99,
			layout:'column',
			defaults: {
				margin:'0 0 0 0'
			},
			items : [{
				fieldLabel : '小票付款方式',
				xtype : 'combobox',
				displayField:'name',
				valueField:'code',
				queryMode:'local',
				triggerAction:'all',
				editable:false,
				name:'revenuePaymentType',
				labelWidth : 120,
				columnWidth: 0.36,
				value : 'CH',
				store:Ext.create('Foss.sign.settlePayment.SettlePaymentTypeStore',
					{
			  			data:{
			       			'items':[
								{'code':'CH','name':'现金'},
								{'code':'CD','name':'银行卡'}
							]
			  			}
					}),
				listeners : {
					'select' : function(combo,records,eOpts){
						var form=this.up('form').getForm();
						//当小票付款方式选择现金时，小票款项确认编号不可填
						if(form.findField('revenuePaymentType').getValue()=='CH'){
							form.findField('revenueClaimNo').setValue('');
							form.findField('revenueClaimNo').setReadOnly(true);
							form.findField('revenueClaimNo').allowBlank=true;
						}else{
							form.findField('revenueClaimNo').setReadOnly(false);
							form.findField('revenueClaimNo').allowBlank=false;
						}
					}
				}
			},{
				border : false,
				columnWidth:.04,
				html: '&nbsp;'
			},{
				fieldLabel:'小票单号',
				name:'otherRevenueNo',
				xtype:'textfield',
				maxLength:50,
				columnWidth:.30,
				disabled:true
				/*listeners :{
					'blur':function(me,theEvent,eOpts){
						var form = this.up('form').getForm();
						if(form.findField('otherRevenueNo').disabled==false&&(form.findField('otherRevenueNo').getValue()==null || form.findField('otherRevenueNo').getValue()=='')){
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.otherRevenueNoIsNotNull'), 'error', 3000);
						}else if(form.findField('otherRevenueNo').isValid()){
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.otherRevenueNoIsValid'), 'error', 3000);
						}
					}
				}*/
			},
			{
				border : false,
				columnWidth:.05,
				html: '&nbsp;'
			},
			{
				columnWidth:.14,
				xtype : 'button',
				text: sign.settlePayment.i18n('pkp.sign.settlePayment.settle'),//结清货款
				disabled:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexsettlebutton'),
				hidden:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexsettlebutton'),
				id:'Foss_sign_settlePayment_Payment_Id',
				height:24,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
				}),
				listeners :{
					'mouseover':function(me,theEvent,eOpts){
						var form = this.up('form').getForm();
						var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						if(waybillNo==null||waybillNo==''){
							this.disabled=true;
						}
						/*if(form.findField('otherRevenueNo').disabled==false&&(form.findField('otherRevenueNo').getValue()==null || form.findField('otherRevenueNo').getValue()=='')){
							
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.otherRevenueNoIsNotNull'), 'error', 3000);
							}*/
					}
				},
				handler: function() {
					var form = this.up('form').getForm();
					var formparams = this.up('form').getForm().getValues();
					//add by chenjunying 2015-01-13 DMANA-10629
					//验证扫描获取的身份证号是否有人为修改
					var newCode = form.findField('identifyCode').getValue();//点击按钮时，身份证的值
					var newCodCode = form.findField('codIdentifyCode').getValue();//点击按钮时，代收人身份证的值
					if(newCode!=null || newCode!=''){//本人
						//newCode = newCode.replace(/' '/g,''); 去空格
						if(newCode != sign.settlePayment.IDENTITY_CODE){  //扫描后有人为修改，将记录扫描值改为N
							//form.findField('identityIsScan').setValue('N');
							formparams.identityIsScan = 'N';
						}
					}else{
						formparams.identityIsScan = 'N';
					}
					
					if(newCodCode!=null || newCodCode!=''){//代收人
						if(newCodCode != sign.settlePayment.COD_IDENTITY_CODE){//扫描后有人为修改，将记录扫描值改为N
							formparams.codIdentityIsScan = 'N';
						}
					}else{
						formparams.codIdentityIsScan = 'N';
					}
					
					
					var wayformparams =Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().getValues();
					var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
					var toPayAmount = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('toPayAmount').getValue();
					var consigneeName =form.findField('consigneeCode').getRawValue();
					var waitingProcessGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
					var paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id');
					
					Ext.Ajax.request({
						url:sign.realPath('queryPaymentByWaybillNo.action'),
						params: {
							'vo.repaymentDto.airAgencyQueryDto.waybillNo': waybillNo
						},
						async:false,
						success: function(response){
				    	var result = Ext.decode(response.responseText);
						//加载付款信息
						sign.settlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);				
						}
					});
					formparams.waybillNo = waybillNo;
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
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.choseWaybillBefore'), 'error', 3000);
					}
					else if (!form.isValid())
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.checkRequiredInformation'), 'error', 3000);
					}
					//付款方式为“支票”或“电汇”或“银行卡”或“网上支付”，则款项认领编号不可为空
					else if((form.findField('paymentType').getValue()=='NT'||form.findField('paymentType').getValue()=='TT'
							||form.findField('paymentType').getValue()=='CD')&& Ext.isEmpty((form.findField('claimNo').getValue())||' '.trim()))
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.claimNoNotNull'), 'error', 3000);
					}
					else if (form.findField('paymentType').getValue()=='OL'&&(form.findField('receiveablePayAmoout').getValue()+form.findField('receiveableAmount').getValue() > 0
							&&Ext.isEmpty((form.findField('claimNo').getValue())||' '.trim()))) {
						
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('付款方式为网上支付且应收到付款或应收到付款不同时为零，款项确认编号不能为空。'), 'error', 3000);
					//款项认领编号不为空时，必须为数字
					}else if(form.findField('paymentType').getValue()=='CD'
						&& !/^\s*\d+\s*$/.test(form.findField('claimNo').getValue())){
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '付款方式为银行卡时，款项确认编号必须输入数字。','error',3000);//付款方式为银行卡时，款项确认编号必须输入数字。
					}
					//付款方式为“月结”或“临欠”，则客户不可为空  add by 353654 付款方式为余额结清时
					else if((form.findField('paymentType').getValue()=='CT'||form.findField('paymentType').getValue()=='DT'|| (form.findField('paymentType').getValue()=='BE'))
							&&Ext.isEmpty((form.findField('consigneeCode').getValue())||' '.trim()))
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.consigneeCodeNotNull'), 'error', 3000);
					}
					//付款方式为“月结”或“临欠”，不可以存在付款信息
					else if((form.findField('paymentType').getValue()=='CT'||form.findField('paymentType').getValue()=='DT')
							&& actualFreightFee > 0)
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.cannotCTDT'), 'error', 3000);
					}
					//实付运费不能大于应收到付款
					else if(form.findField('actualFreight').getValue() + actualFreightFee>form.findField('receiveablePayAmoout').getValue())
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.actualFreightNotGTReceiveablePayAmoout'), 'error', 3000);
					}
					//代收货款不能大于应收代收款
					else if(form.findField('codAmount').getValue() + codAmountFee >form.findField('receiveableAmount').getValue())
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.codAmountNotGTReceiveableAmount'), 'error', 3000);
					}
					//实付运费和代收货款必须大于等于0
					else if(form.findField('actualFreight').getValue()< 0 || form.findField('codAmount').getValue()<0)
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.actualFreightGTZero'), 'error', 3000);
					}
					else if ((form.findField('actualFreight').getValue() == 0 && form.findField('codAmount').getValue() == 0) &&
							    (form.findField('receiveableAmount').getValue() > 0 || form.findField('receiveablePayAmoout').getValue() > 0) &&
							    form.findField('paymentType').getValue() !='CT' && form.findField('paymentType').getValue() !='DT')
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.actualFreightcodAmountGTZero'), 'error', 3000);
					}
					else if((form.findField('paymentType').getValue() =='CT' || form.findField('paymentType').getValue() =='DT')
							   && form.findField('receiveableAmount').getValue() > 0)
					{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.notCTDT'), 'error', 3000);
					}
					/*else if(form.findField('paymentType').getValue() !='CT' && form.storageCharge !=null&&
							(form.findField('actualFreight').getValue() + actualFreightFee == form.findField('receiveablePayAmoout').getValue())&&
							(form.findField('codAmount').getValue() + codAmountFee ==form.findField('receiveableAmount').getValue())&&
							(form.findField('otherRevenueNo').getValue()==null || form.findField('otherRevenueNo').getValue()==''))
					{		
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.otherRevenueNoIsNotNull'), 'error', 3000);
					}*/
					// 统一结算
					else if(form.findField('paymentType').getValue() == null || form.findField('paymentType').getValue() == ""){
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '请选择付款方式', 'error', 3000);
					}
					//添加用户余额校验 353654  当不是余额付款方式时缺有余额为0，则提示该用户没有余额actualFreight
					else if(form.findField('paymentType').getValue()=='BE' && form.findField('balanceAmount').getValue()-0 < form.findField('actualFreight').getValue()-0){
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '该客户余额不足,请输入正确金额或选择其他方式结清货款', 'error', 3000);
						this.disabled=true;
					}
					else if(wayformparams.arriveCentralizedSettlement == 'Y'
						&& form.findField('actualFreight').getValue() > 0 
						&& form.findField('codAmount').getValue() == 0
						&& form.findField('paymentType').getValue() !='DT'
							&&form.findField('paymentType').getValue() !='CT'
								&&form.findField('paymentType').getValue() !='TT'){
						
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '实付运费>0，代收=0时，以统一结算标准限制付款方式（需为临欠、月结、电汇）', 'error', 3000);	
					}
					else if(wayformparams.arriveCentralizedSettlement == 'Y'
						&& form.findField('actualFreight').getValue() == 0 
						&& form.findField('codAmount').getValue() > 0
						&& (form.findField('paymentType').getValue() =='CT'
							||form.findField('paymentType').getValue() =='TT')){
						
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '实付运费=0，代收>0时，以代收货款标准限制付款方式（不可为临欠、月结）', 'error', 3000);
					}
					else if(wayformparams.arriveCentralizedSettlement == 'Y'
						&& form.findField('actualFreight').getValue() > 0 
						&& form.findField('codAmount').getValue() > 0 
						&& form.findField('paymentType').getValue() !='TT'){
						
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '实付运费>0，代收>0时，以统一结算标准付款方式限制（只可为电汇）', 'error', 3000);
					}
					//当小票单号不为空时，小票付款方式必填
					/*else if(!Ext.isEmpty(form.findField('otherRevenueNo').getValue())
								&& Ext.isEmpty(form.findField('revenuePaymentType').getValue())){
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '小票单号不为空时，小票付款方式必填！', 'error', 3000);
					}*/
					//当保管费大于0，且小票付款方式为银行卡时，‘小票单号’和‘小票款项编号’必填
					else if(form.findField('revenuePaymentType').getValue() == 'CD' && form.findField('storageCharge').getValue() > 0
						&& (Ext.isEmpty((form.findField('revenueClaimNo').getValue())||' '.trim()))){
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '请填写完小票款项确认编号', 'error', 3000);
						
					}
					//当‘小票款项编号’不为空时，必须是数字
					else if(form.findField('revenuePaymentType').getValue() == 'CD' && form.findField('storageCharge').getValue() > 0
						&& !/^\s*\d+\s*$/.test(form.findField('revenueClaimNo').getValue())){
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), '小票付款方式为银行卡时，小票款项确认编号必须输入数字。', 'error', 3000);
						}
					else
					{
						//如果有代收货款
						if( (form.findField('codAmount').getValue()>0))
						{
							passWordWin = Ext.create('Foss.sign.settlePayment.PasswordWindow').show();
						}
						else
						{
							Ext.Ajax.request({
							    url:sign.realPath('addPaymentInfo.action'),
							    timeout: 180000,
							    jsonData:{
							    	'vo':{
							    	   'repaymentEntity':formparams,'waybillDto':wayformparams
							    	}
							    },
						    	success:function(response){
							    	form.findField('consigneeCode').setValue("");
							    	form.findField('deliverymanName').setValue("");
							    	form.findField('identifyType').setValue("ID_CARD");
							    	form.findField('identifyCode').setValue("");
							    	form.findField('codIdentifyType').setValue("ID_CARD");
							    	form.findField('codIdentifyCode').setValue("");
							    	//form.findField('paymentType').setValue("CH");
							    	form.findField('claimNo').setValue("");
							    	form.findField('claimNo').setReadOnly(false);
							    	form.findField('claimNo').allowBlank=false;
							    	form.findField('actualFreight').setValue("");
							    	form.findField('codAmount').setValue("");
							    	form.findField('totalCollection').setValue("");
							    	form.findField('actualFreight').setReadOnly(false);
							    	form.findField('revenueClaimNo').setValue("");
							    	waitingProcessGrid.store.removeAll()
							    	Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.paymentSuccessful'), 'ok', 3000);
							    	waitingProcessGrid.store.load();
							    	paymentGrid.store.removeAll();
					    		},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
									//waitingProcessGrid.store.removeAll()
									//waitingProcessGrid.store.load();
									//paymentGrid.store.removeAll();
			              			Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.paymentFailed'), json.message, 'error', 3000);
								}
							});
						}
					}
				}
			}]
		},{
			xtype : 'textfield',
			fieldLabel : '小票款项确认编号',
			name : 'revenueClaimNo',
			maxLength : 50,
			labelWidth : 120,
			columnWidth: 0.36,
			readOnly : true,
			allowBlank : true,
			regex:/^[0-9]{12}$/
		}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}
});

//付款记录
Ext.define('Foss.sign.settlePayment.PaymentGrid', {
	extend:'Ext.grid.Panel',
	id:'Foss_sign_settlePayment_PaymentGrid_Id',
	//增加表格列的分割线
	columnLines: true,
	//表格对象增加一个边框
	stripeRows: true,
	emptyText: sign.settlePayment.i18n('pkp.sign.settlePayment.emptyText'),//查询结果为空
	height:180,
	store: Ext.create('Foss.sign.settlePayment.PaymentStore'),	 
    viewConfig: {
        enableTextSelection: true
     },
	//定义表格列信息
	columns: [
		{ 
			header: sign.settlePayment.i18n('pkp.sign.settlePayment.paymentType'),// 付款方式
			//关联model中的字段名
			dataIndex: 'paymentType',
			align: 'center',
			width:130
		},{ 
			header: sign.settlePayment.i18n('pkp.sign.settlePayment.actualFreight'),//实付运费 
			//关联model中的字段名
			dataIndex: 'actualFreight',
			align: 'center',
			width:130
		},{
			header: sign.settlePayment.i18n('pkp.sign.settlePayment.codAmount'),//代收货款 
			//关联model中的字段名
			dataIndex: 'codAmount', 
			align: 'center',
			width:130
		},{
			header: sign.settlePayment.i18n('pkp.sign.settlePayment.stlbillGeneratedStatus'),//是否已有财务单据 
			//关联model中的字段名
			dataIndex: 'stlbillGeneratedStatus', 
			align: 'center',
			renderer : function(value, metadata, record, rowIndex, columnIndex, store){
				if(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED'){
					return sign.settlePayment.i18n('pkp.sign.settlePayment.yes');//是
				}else{
					return sign.settlePayment.i18n('pkp.sign.settlePayment.no');//否
				}
			},
			width:130
		},{
			header: sign.settlePayment.i18n('pkp.sign.settlePayment.paymentTime'),//付款时间 
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
					id:'Foss_sign_settlePayment_quickPayment_Id',
					name:'addException',
					columnWidth:.15,
					text: sign.settlePayment.i18n('pkp.sign.settlePayment.quickPayment'),//快速结清
					disabled:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexquicksettlebutton'),
					hidden:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexquicksettlebutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						  //设定间隔秒数,如果不设置，默认为2秒
						  seconds: 3
					}),
					handler : function() {
						var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
						var paymentGridStore = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id').getStore();
						selectRow = waitingProcessGrid.getSelectionModel().getSelection();

						if (selectRow.length == 0)
						{
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.choseWaybillBefore'), 'error', 3000);
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
										    	var signInfoForm = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
												var waybillInfoForm = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm();
												//得到运单基本信息
												var wayBillInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.waybillDto,
														 'Foss.sign.settlePayment.model.WayBillInfoModel');
												var signInfoModel = Ext.ModelManager.create(result.vo.repaymentDto.financialDto,
												 		 'Foss.sign.settlePayment.model.FinancialModel');
												
												// 统一结算限制
												// 到达金额>0 到达统计结算
												if(signInfoModel.data.receiveablePayAmoout > 0
														  && wayBillInfoModel.data.arriveCentralizedSettlement == 'Y'){
													signInfoForm.findField('consigneeCode').setCombValue(wayBillInfoModel.data.receiveCustomerName,wayBillInfoModel.data.receiveCustomerCode);
													//signInfoForm.findField('consigneeCode').setValue(wayBillInfoModel.data.receiveCustomerCode);//到达客户
													//signInfoForm.findField('consigneeCode').setRawValue(wayBillInfoModel.data.receiveCustomerName);//到达客户
													signInfoForm.findField('consigneeCode').setReadOnly(true);
													
													var newData = Ext.clone(sign.settlePayment.CentralizedPaymentStore.data);
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
													signInfoForm.findField('consigneeCode').setRawValue('');//到达客户
													signInfoForm.findField('consigneeCode').setReadOnly(false);
													
													var newData = Ext.clone(sign.settlePayment.PaymentStore.data);
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
												sign.settlePayment.PaymentGrid.store.loadData(result.vo.repaymentDto.repaymentEntityList);
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
												var paymentGrid = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id');
												settleRow = paymentGrid.store.data.length;
												var store = sign.settlePayment.PaymentGrid.store;
												
												Ext.getCmp('Foss_sign_settlePayment_addException_Id').setDisabled(true);
												Ext.getCmp('Foss_sign_settlePayment_quickPayment_Id').setDisabled(true);

												store.each(function(record, index, allRecords){
													if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
														Ext.getCmp('Foss_sign_settlePayment_addException_Id').setDisabled(false);
														Ext.getCmp('Foss_sign_settlePayment_quickPayment_Id').setDisabled(false);
													}
												});
												
												Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.paymentSuccessful'), 'ok', 3000);
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
			              			Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.paymentFailed'), json.message, 'error', 3000);
								}
							});
						}
					}
				},{
					xtype:'button',
					allowBlank:true,
					id:'Foss_sign_settlePayment_addException_Id',
					name:'addException',
					columnWidth:.15,
					text: sign.settlePayment.i18n('pkp.sign.settlePayment.refuse'),//快速反结清
					disabled:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexquickreverseybutton'),
					hidden:!sign.settlePayment.isPermission('settlepaymentindex/settlepaymentindexquickreverseybutton'),
					plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
						  //设定间隔秒数,如果不设置，默认为2秒
						  seconds: 3
					}),
					handler : function() {
						var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
						var waitingProcessGrid = Ext.getCmp('Foss_sign_settlePayment_WaitingProcessGrid_Id');
						selectRow = waitingProcessGrid.getSelectionModel().getSelection();

						if (selectRow.length == 0)
						{
							Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.choseWaybillBeforeRefund'), 'error', 3000);
						}
						else
						{
							Ext.Ajax.request({
							    url:sign.realPath('refundPaymentInfo.action'),
								params:{'vo.repaymentEntity.waybillNo':waybillNo},
								success:function(response){
									Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.RefundSuccessful'), 'error', 3000);
									var store = sign.settlePayment.PaymentGrid.store,
										recordArray = new Array();
									Ext.getCmp('Foss_sign_settlePayment_addException_Id').setDisabled(true);
									Ext.getCmp('Foss_sign_settlePayment_quickPayment_Id').setDisabled(true);
									store.each(function(record, index, allRecords){
										if(!(record.get('stlbillGeneratedStatus')=='STLBILL_GENERATED')){
											recordArray.push(record);
										}
									});
									store.remove(recordArray);
								},
					    		exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), json.message, 'error', 3000);
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
Ext.define('Foss.sign.settlePayment.WaybillInfoForm',{
	extend: 'Ext.form.Panel',
	id:'Foss_sign_settlePayment_WaybillInfoForm_Id',
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
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerName'),//收货人
		columnWidth: .33
	},{
		name:'receiveCustomerAddress',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerAddress'),//收货客户地址
		columnWidth: .33
	},{
		name:'receiveCustomerPhone',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerPhone'),//收货人电话
		columnWidth: .33
	},{
		name:'waybillNo',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.waybillNo'),//单号
		columnWidth: .33
	},{
		xtype: 'datefield',
		name:'arriveTime',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.arriveTime'),//到达时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'stockStatus',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.stockStatus'),//库存状态
		columnWidth: .33
	},{
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.notificationResult'),//是否通知成功
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
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.notificationTime'),//上次通知时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'paidMethod',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.paidMethod'),//付款方式
		columnWidth: .33
	},{
		name:'toPayAmount',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.toPayAmount'),//到付总额
		columnWidth: .33
	},{
		name:'codAmount',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.codAmount'),//代收货款
		columnWidth: .33
	},{
		name:'transportFee',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.transportFee'),//运费
		columnWidth: .33
	},{
		name:'deliveryGoodsFee',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.deliveryGoodsFee'),//送货费
		columnWidth: .33
	},{
		name:'insuranceAmount',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.insuranceAmount'),//货物价值
		columnWidth: .33
	},{
		name:'insuranceFee',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.insuranceFee'),//保价费
		columnWidth: .33
	},{
		name:'otherFee',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.otherFee'),//其他费用
		columnWidth: .33
	},{
		name:'receiveMethod',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveMethod'),//派送方式
		columnWidth: .33
	},{
		name:'goodsName',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsName'),//货物名称
		columnWidth: .33
	},{
		name:'goodsQtyTotal',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsQtyTotal'),//件数
		columnWidth: .33
	},{
		name:'goodsWeightTotal',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsWeightTotal'),//重量
		columnWidth: .33
	},{
		name:'goodsVolumeTotal',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsVolumeTotal'),//体积
		columnWidth: .33
	},{
		name:'goodsPackage',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.goodsPackage'),//包装
		columnWidth: .33
	},{
		name:'deliveryCustomerName',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.deliveryCustomerName'),//发货人
		columnWidth: .33
	},{
		name:'deliveryCustomerCityName',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.deliveryCustomerCityName'),//始发站
		columnWidth: .33
	},{
		name:'receiveOrgName',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveOrgName'),//始发部门
		columnWidth: .33
	},{
		name:'transportType',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.transportType'),//运输类型
		columnWidth: .33
	},{
		name:'productCode',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.productCode'),//运输性质
		columnWidth: .33
	},{
		xtype: 'datefield',
		name:'createTime',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.createTime'),//出发时间
		columnWidth: .33,
		format : 'Y-m-d H:i:s'
	},{
		name:'settleStatus',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.settleStatus'),//结清状态
		hidden: true,
		columnWidth: .33
	},{
		name:'receiveCustomerCode',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerCode'),//客户编码
		columnWidth: .33,
		hidden: true
	},{
		name:'receiveCustomerMobilephone',
		fieldLabel: sign.settlePayment.i18n('pkp.sign.settlePayment.receiveCustomerMobilephone'),//收货人手机
		columnWidth: .33,
		hidden: true
	},{
		name:'invoice',
		fieldLabel:sign.settlePayment.i18n('pkp.sign.settlePayment.invoice'),//发票标记
		columnWidth: .33,
		hidden: true
	},{
		name:'arriveCentralizedSettlement',
		hidden: true,
		columnWidth: .1
	}]
});

Ext.define('Foss.sign.settlePayment.PassWordFormPanel',{
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
		text : sign.settlePayment.i18n('pkp.sign.settlePayment.password'),//操作人密码:
		columnWidth:.33
	   },{
	    xtype : "textfield",
	    inputType : 'password',
	    height : 25,
		columnWidth:.63,
	    hideLabel : true,
	    allowBlank:false,
	    id:"cancelarea",
	    anchor : "90%",
	    msgTarget: 'side'
	   }],  
	   buttons : [{
			text : sign.settlePayment.i18n('pkp.sign.settlePayment.confirm'),//确定
			columnWidth:.5,
			margin:'0 25 0 0',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 2
			}),
			handler : function(){
				var ps = Ext.getCmp('cancelarea').value;
				var form=this.up('form').getForm();
				var signform = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm();
				var formparams = signform.getValues();
				var wayformparams = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().getValues();
				var consigneeName =Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm().findField('consigneeCode').getRawValue();
				var consigneeCode =Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm().findField('consigneeCode').getValue();
				var waybillNo = Ext.getCmp('Foss_sign_settlePayment_WaybillInfoForm_Id').getForm().findField('waybillNo').getValue();
				var storageFee = Ext.getCmp('Foss_sign_settlePayment_SignInfoForm_Id').getForm().findField('storageCharge').getValue();
				var paymentGridStore = Ext.getCmp('Foss_sign_settlePayment_PaymentGrid_Id').getStore();
				formparams.waybillNo = waybillNo;
				formparams.consigneeName = consigneeName;
				formparams.consigneeCode = consigneeCode;
				formparams.storageFee = storageFee;
				formparams.passWord = ps;
				if(form.isValid()){
					Ext.Ajax.request({
					    url:sign.realPath('validatePaymentInfo.action'),
					    jsonData:{
					    	'vo':{
					    	   'repaymentEntity':formparams,'waybillDto':wayformparams
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
				    		form.findField('cancelarea').setValue("");
				    		paymentGridStore.removeAll();
					    	Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'), sign.settlePayment.i18n('pkp.sign.settlePayment.paymentSuccessful'), 'success', 3000);
							passWordWin.close();
			    		},
			    		exception: function(response){
							var json = Ext.decode(response.responseText);
							form.findField('cancelarea').setValue("");
							paymentGridStore.removeAll();
	              			Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.paymentFailed'), json.message, 'error', 3000);
						}
					});
					}else{
						Ext.ux.Toast.msg(sign.settlePayment.i18n('pkp.sign.settlePayment.tip'),sign.settlePayment.i18n('pkp.sign.settlePayment.passwordNotNull'),'error',3000);
					}
				}
	   	},{
			columnWidth:.5,
			margin:'0 25 0 0',
			text : sign.settlePayment.i18n('pkp.sign.settlePayment.cancel'),//取消
			handler : function(){
				passWordWin.close();
			}
	   }]
	   
	});

//密码输入panel
sign.settlePayment.passWordFormPanel = Ext.create('Foss.sign.settlePayment.PassWordFormPanel');

//定义密码输入window
Ext.define('Foss.sign.settlePayment.PasswordWindow', {
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	modal : 'true',
	width : 350,
	height : 180,
	items : [sign.settlePayment.passWordFormPanel],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});

sign.settlePayment.QueryPaymentGrid = Ext.create('Foss.sign.settlePayment.WaitingProcessGrid');
//左边面板
Ext.define('FOSS.settlePayment.Panel.LeftPanel',{
		extend:'Ext.panel.Panel',
		margin:'10 10 10 10',
		title:sign.settlePayment.i18n('pkp.sign.settlePayment.pending'),//待处理
		frame:true,
		items:[sign.settlePayment.QueryPaymentGrid]
});
	
//右边上面面板-签收信息
Ext.define('FOSS.settlePayment.Panel.RightTopPanel',{
		extend:'Ext.panel.Panel',
		title:sign.settlePayment.i18n('pkp.sign.settlePayment.signInformation'),//签收信息
		margin:'10 0 10 0',
		frame:true,
		items:[Ext.create('Foss.sign.settlePayment.SignInfoForm')]
		
});

sign.settlePayment.PaymentGrid = Ext.create('Foss.sign.settlePayment.PaymentGrid');
//右边中间面板-付款记录
Ext.define('FOSS.settlePayment.Panel.MiddlePanel',{
		extend:'Ext.panel.Panel',
		title:sign.settlePayment.i18n('pkp.sign.settlePayment.paymentRecords'),//付款记录
		margin:'10 0 10 0',
		frame:true,
		items:[sign.settlePayment.PaymentGrid]
		
});

//右边下面面板-运单基本信息
Ext.define('FOSS.settlePayment.Panel.RightBottomPanel',{
		extend:'Ext.panel.Panel',
		title:sign.settlePayment.i18n('pkp.sign.settlePayment.waybillInformation'),//运单基本信息
		margin:'10 0 10 0',
		frame:true,
		items:[Ext.create('Foss.sign.settlePayment.WaybillInfoForm')]
		
});

//右边面板
Ext.define('FOSS.settlePayment.Panel.RightPanel',{
		extend:'Ext.panel.Panel',		
		items:[Ext.create('FOSS.settlePayment.Panel.RightTopPanel',{height:315}),
		       Ext.create('FOSS.settlePayment.Panel.MiddlePanel',{height:230}),
			   Ext.create('FOSS.settlePayment.Panel.RightBottomPanel',{height:410})
			  ]
});

//将约车信息、车辆信息、申请信息、货物信息、审核记录组合显示
Ext.define('FOSS.settlePayment.Container',{
	  extend: 'Ext.container.Container',
	  layout: 'column',
	  frame: false,
	  cls: 'autoHeight',
	  bodyCls: 'autoHeight',  
	  lefttPanel: null,
	  getLefttPanel: function(){
	    if(this.lefttPanel==null){
	      this.lefttPanel = Ext.create('FOSS.settlePayment.Panel.LeftPanel',{width:230,minHeight:950});
	    }
	    return this.lefttPanel;
	  },
	  rightPanel: null,
	  getRightPanel: function(){
	    if(this.rightPanel==null){
	      this.rightPanel = Ext.create('FOSS.settlePayment.Panel.RightPanel',{width:780});
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
	Ext.Ajax.timeout=130000;
	sign.settlePayment.settlePaymentForm = Ext.create('Foss.sign.settlePayment.SettlePaymentForm'); 
	sign.settlePayment.container=Ext.create('FOSS.settlePayment.Container');
	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-settlePaymentIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [sign.settlePayment.settlePaymentForm,sign.settlePayment.container],
		renderTo: 'T_sign-settlePaymentIndex-body'
	});
});
