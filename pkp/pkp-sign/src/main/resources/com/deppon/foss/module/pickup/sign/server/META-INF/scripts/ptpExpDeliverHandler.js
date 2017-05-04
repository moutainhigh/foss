sign.ptpExpDeliverHandler.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';//签收情况词条
sign.ptpExpDeliverHandler.PKP_PULLBACK_REASON = 'PKP_PULLBACK_REASON';//拉回原因词条
sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_TYPE='PKP_SIGN_PERSON_TYPE';//签收人类型
sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_NAME='PKP_SIGN_PERSON_NAME';//签收人
sign.ptpExpDeliverHandler.PKP_SIGN_STATUS='PKP_SIGN_STATUS';//签收状态
//付款方式词条
(function(){
	sign.ptpExpDeliverHandler.PaymentStore =FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE', null);
	sign.ptpExpDeliverHandler.PaymentStore.removeAt(sign.ptpExpDeliverHandler.PaymentStore.find('valueCode','OL'));
	sign.ptpExpDeliverHandler.PaymentStore.removeAt(sign.ptpExpDeliverHandler.PaymentStore.find('valueCode','FC'));
	sign.ptpExpDeliverHandler.PaymentStore.removeAt(sign.ptpExpDeliverHandler.PaymentStore.find('valueCode','BE'));//移除余额 353654
	sign.ptpExpDeliverHandler.signTypeStore =FossDataDictionary.getDataDictionaryStore(sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_TYPE, null);
})();
//签收情况
(function(){
	sign.ptpExpDeliverHandler.situationStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION', null);
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','UNNORMAL_SIGN'));
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','PARTIAL_SIGN'));//移除部分签收
	sign.ptpExpDeliverHandler.situationStore.removeAt(sign.ptpExpDeliverHandler.situationStore.find('valueCode','GOODS_BACK'));//移除货物拉回
	sign.ptpExpDeliverHandler.situationGoodBackStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION', null,null,'GOODS_BACK');
	sign.ptpExpDeliverHandler.exsituationStore=FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION',
		null,null,['UNNORMAL_BREAK', 'UNNORMAL_DAMP', 'UNNORMAL_POLLUTION','UNNORMAL_GOODSHORT','UNNORMAL_ELSE']);
})();
//签收状态
(function(){
	sign.ptpExpDeliverHandler.signStatusStore = FossDataDictionary.getDataDictionaryStore('PKP_SIGN_STATUS', null);
	//移除部分签收
	sign.ptpExpDeliverHandler.signStatusStore.removeAt(sign.ptpExpDeliverHandler.signStatusStore.find('valueCode','SIGN_STATUS_PARTIAL'));
})();

sign.ptpExpDeliverHandler.SIGN_STATUS_PARTIAL= 'SIGN_STATUS_PARTIAL';//签收状态为部分签收
sign.ptpExpDeliverHandler.SIGN_STATUS_ALL= 'SIGN_STATUS_ALL';//签收状态为全部签收
sign.ptpExpDeliverHandler.SIGN_STATUS_NO= 'SIGN_STATUS_NO';//签收状态为未签收
sign.ptpExpDeliverHandler.SIGN_SERIAL_NO_ARR =null;
sign.ptpExpDeliverHandler.SIGN_RECORD_SERINALNO_INDEX=null;
sign.ptpExpDeliverHandler.SIGN_RECORD_WAYBILLNO_INDEX=null;
sign.ptpExpDeliverHandler.COUNT=null;
sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT_CODE = 'CZM_SIGN_PARAM';//子母件签收限制票数 配置参数code
sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT = null;  //子母件签收限制票数值


(function(){
	//获取子母件限制的签收件数  数据来源于数据字典
	sign.ptpExpDeliverHandler.queryCzmSignLimit = function(){
		Ext.Ajax.request({
			url:sign.realPath('queryCzmSignLimit.action'),
			method:'POST',
			timeout:100000,
			params:{
				'signVo.czmSignDto.czmSignDataCode':sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT_CODE
			},
			success: function(response) {
				var result = Ext.decode(response.responseText);
				var limit = result.signVo.czmSignDto.czmSignLimit;
				if(null != limit){
					sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT = limit;
				}
			}

		});
	};
	sign.ptpExpDeliverHandler.queryCzmSignLimit(); 
})();


//让form里的控件可见　或者不可见
sign.ptpExpDeliverHandler.setPartFormVisible = function(form,visible){
	if(form.findField('packingResult').isVisible()==!visible){
		form.findField('packingResult').setVisible(visible);
	}
	if(form.findField('alittleShort').isVisible()==!visible){
		form.findField('alittleShort').setVisible(visible);
		form.findField('alittleShort').setValue('');
	}
	//并且数据可编辑
	form.findField('packingResult').setDisabled(!visible);
	//radiogroup 这种如果要操作items时候 需要把单个控件拿出来操作
	form.findField('packingResult').items.items[0].setDisabled(!visible);
	form.findField('packingResult').items.items[1].setDisabled(!visible);
	form.findField('alittleShort').setDisabled(!visible);
}
//让form里的所有控件都不可用
sign.ptpExpDeliverHandler.setFormDisabled = function(form,editable){
	var fields = form.getForm().getFields();
	fields.each(function(field){
		field.setDisabled(editable);
	});
}
sign.ptpExpDeliverHandler.paymentTypeCH='CH';//付款方式为现金
sign.ptpExpDeliverHandler.deliverStatus='SIGNINFO_CONFIRMED';//派送单状态为‘已签收确认’
sign.ptpExpDeliverHandler.formFieldReset = function(form,isTrue){
	var claimNo = form.findField('claimNo'), paymentType = form.findField('paymentType'),
		consigneeCode = form.findField('consigneeCode');
	if(isTrue){
		claimNo.reset();
		consigneeCode.reset();
		paymentType.reset();
	}
};
//根据传入的true,false 让财务信息里的付款方式，客户，款项确认编码是否只读
sign.ptpExpDeliverHandler.isReadOnly = function(form,isTrue){
	var claimNo = form.findField('claimNo'), paymentType = form.findField('paymentType'),
		consigneeCode = form.findField('consigneeCode');
	//款项确认编号
	claimNo.setReadOnly(isTrue);
	//付款方式
	paymentType.allowBlank = isTrue;
	paymentType.setReadOnly(isTrue);
	//客户编码可编辑
	consigneeCode.setReadOnly(isTrue);
}
//派送单号
sign.ptpExpDeliverHandler.deliverbillNo=null;
//付款方式
sign.ptpExpDeliverHandler.paymentType;
//财务信息
Ext.define('Foss.sign.ptpExpDeliverHandler.model.Financial', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'pocketShipping',type:'float',defaultValue: 0},/*实付运费*/
		{name : 'codAmount',type:'float',defaultValue: 0},/*代收货款*/
		{name : 'toPayAmount',type:'float',defaultValue: 0},/*收款总额*/
		{name : 'receiveableAmount',type:'float',defaultValue: 0},/*应收代收款*/
		{name : 'receivedAmount',type:'float',defaultValue: 0},/*已收代收款*/
		{name : 'receiveablePayAmoout',type:'float',defaultValue: 0},/*应收到付款*/
		{name : 'receivedPayAmount',type:'float',defaultValue: 0},/*已收到付款*/
		{name : 'paymentType'},/*付款方式*/
		{name : 'receiveCustomerName'},/*收货人(收货客户名称)*/
		{name : 'receiveCustomerContact'},/*收货客户联系人*/
		{name : 'isWholeVehicle'},/*是否整车运单*/
		{name : 'productCode'},/*运输性质*/
		{name: 'claimNo'},/*款项确认编号*/
		{name: 'consigneeName'},/*客户名称*/
		{name: 'consigneeCode'},/*客户编码*/
		{name:'receiveBigCustomer'},/*收货人是否大客户表示*/
		{name: 'orderNo'},/*订单号*/
		{name: 'receiveCustomerContact'},//收货客户联系人名称
		{name: 'existOldWaybillNo'},//是否存在关联单号
		{name: 'oldReceiveablePayAmoout',type:'float',defaultValue: 0},//关联单号应收到付款
		{name: 'totalMoney'}//总金额
	]
});
//签收信息--签收件 流水号Model
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
//		{name: 'noSelect',type: 'string'},// 是否选中
		{name: 'waybillNo',type: 'string'},// 运单编号 268377  yanling
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'goodShorts'}// 是否内货短少
	]
});
//异常流水号选择的model 
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'waybillNo',type: 'string'},// 运单号
		{name:'isSelect',type:'boolean'}
	]
});
//签收出库同票多类grid对应model
Ext.define('Foss.sign.ptpExpDeliverHandler.SignOutWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'signState'},
		{name: 'choose'},
		{name: 'serialNo'}
	]
});
//签收信息
Ext.define('Foss.sign.ptpExpDeliverHandler.model.SignInfoModel', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'id'},/* id*/
		{name : 'situation'},/* 签收情况*/
		{name : 'signNote'},/*签收备注*/
		{name : 'pullbackReason'},/*拉回原因*/
		{name: 'identifyCode', type: 'string'},// 证件号码
		{name: 'identifyType', type: 'string'},// 证件类型
		{name : 'isPdaSign'},/*是否PDA签到*/
		{name : 'arrivesheetNo'},/*到达联编号*/
		{name: 'waybillNo',  type: 'string'},//运单编号
		{name : 'arriveSheetGoodsQty',type:'int'},/*到达联总件数*/
		{name : 'signGoodsQty',type:'int'},/*签收件数*/
		{name : 'signTime', type: 'date',
			convert:function(value){
				if(value!=null){
					var date = new Date(value);
					return date;
				}else{
					return null;
				}
			}
		},/* 签收时间*/
		{name : 'deliverymanName',
			convert: function(value) {
				if (value != null && value != 'N/A') {
					return value;
				} else{
					return null;
				}
			}
		},/*签收人*/
		{name : 'status'},/*到达联状态*/
		{name : 'signStatus'},/*到达联签收状态*/
		{name: 'packingResult'},//外包装是否完好
		{name: 'alittleShort'},//短少量
		{name: 'deliverymanType'},//签收人类型
		{name: 'czmStatus'},	//子母件签收状态 268377 yanling
		{name : 'czmGoodsQty',type:'int'}//子母件总件数 268377 yanling
	]
});
//签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.ptpExpDeliverHandler.SerialNoSignOutStorageModel',
	//是否自动查询
	autoLoad: false,
	proxy: {
		type: 'memory',
		reader: {
			type: 'json',
			root: 'deliverbillDetailVo.signDetailEntitys'
		}
	}

});
//待处理表格Store
Ext.define('Foss.sign.ptpExpDeliverHandler.WateDetailStore',{
	extend: 'Ext.data.Store',
	storeId : 'Foss_sign_ptpExpDeliverHandler_WateDetailStoreID',
	fields: [
		{name: 'extid', type: 'string'},//额外的用于生成的EXT使用的列
		{name: 'waybillNo',  type: 'string'},//运单号
		{name: 'arrivesheetNo',  type: 'string'},//到达联编号
		{name: 'deliverStatus',  type: 'string'},//派送单状态
		{name: 'situation',  type: 'string'},//到达联签收情况
		{name: 'id',  type: 'string'}//派送单id
	],
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		actionMethods : 'POST',
		url:sign.realPath('queryPtpExpWaybillNoByParams.action'),
		//定义一个读取器
		reader: {
			//以JSON的方式读取
			type: 'json',
			idgen: 'uuid',//EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',//以上生成的主键用在名叫“extid”的列//定义字段
			//定义读取JSON数据的根对象
			root: 'deliverbillDetailVo.deliverbillDetailDtos'
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-ptpExpDeliverHandlerIndex_content').getQueryPanel().getValues();
			Ext.apply(operation,{
				params:{
					'deliverbillDetailVo.deliverbillDetailDto.deliverbillNo':queryParams.deliverbillNo
				}
			});
			//update 点击查询的时候   是否外包转完好 短少量  是否内货短少 都隐藏掉
			var form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
			form.findField('packingResult').setVisible(false);
			form.findField('alittleShort').setVisible(false);
			Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[1].hide();  //268377 子母件 yanling
			Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].show();  //268377 子母件 yanling
			Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide();  //268377 子母件 yanling
			form.findField('signTime').setVisible(true);
			//update
		},
		// load事件
		load: function(store, records, successful, eOpts){
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.tip'), data.message, 'error', 2000);	//提示			
				return;
			}
			
			var button = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_wateDetailGrid_goodsCheck_Id');
			if(records.length<=0){//如果待处理模块没有记录,让送货确认按钮不可用
				button.setDisabled(true);
			}else{
				if(records[0].data.deliverStatus ==sign.ptpExpDeliverHandler.deliverStatus){
					button.setDisabled(true);
				}else {
					button.setDisabled(false);
				}
			}
		}
	}
});

//待处理表格
Ext.define('Foss.sign.ptpExpDeliverHandler.WateDetailGrid',{
	extend:'Ext.grid.Panel',
	title:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.pending'),//待处理
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	//高  
	height: 820,
	emptyText:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.emptyText'),//查询结果为空
	store: null,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
			{
				xtype : 'label',
				margin : '0 0 0 0',
				html :'&nbsp;&nbsp;',
				height:20,
				width:20,
				columnWidth:.12,
				style:'background-color:#99FF00;'
			},
			{
				xtype : 'label',
				margin : '0 0 0 5',
				columnWidth:.30,
				text : sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.waybillNoSign')//正常签收
			},
			{
				xtype : 'label',
				margin : '0 0 0 0',
				height:20,
				columnWidth:.12,
				html :'&nbsp;&nbsp;',
				width:20,
				style:'background-color:red;'
			},
			{
				xtype : 'label',
				columnWidth:.46,
				margin : '0 0 0 5',
				text :sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.waybillNoRefuse')// 异常/货物拉回
			}]
	}],
	columns: [
		{ header: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.waybillNo'),  align: 'center', dataIndex: 'waybillNo', width : 100 },//运单号
		{ header: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.arrivesheetNo'),  align: 'center', dataIndex: 'arrivesheetNo', flex : 1 }//到达联编号
	],
	enableColumnHide: false,      //取消列头菜单
	//sortableColumns: false,          //取消列头排序功能
	viewConfig: {
		enableTextSelection: true,//设置行可以选择，进而可以复制
		forceFit : true,
		stripeRows: false,//显示重复样式，不用隔行显示
		getRowClass : function(record, rowIndex, rp, ds) {
			var situation = record.get('situation');
			if (situation == 'NORMAL_SIGN') {
				return 'passProcess_mark_color';//变成绿色
			}else{
				if(Ext.isEmpty(situation)){
					return '';
				}else {
					return 'failProcess_mark_color';//变成红色
				}

			}
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ptpExpDeliverHandler.WateDetailStore');
		me.bbar = ['->',{
			text: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.deliveryConfirmation'),//送货确认
			disabled:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexconfirmbutton'),
			hidden:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexconfirmbutton'),
			tabIndex: 1,
			disabled:true,
			id:'Foss_sign_ptpExpDeliverHandler_wateDetailGrid_goodsCheck_Id',
			textAlign : 'center',
			handler: function() {
				Ext.getCmp('Foss_sign_ptpExpDeliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(true);
				var store = Ext.data.StoreManager.lookup('Foss_sign_ptpExpDeliverHandler_WateDetailStoreID');
				var deliverbillNos = new Array();
				deliverbillNos.push(sign.ptpExpDeliverHandler.deliverbillNo);
				var deliverIds = new Array();
				deliverIds.push(store.data.items[0].data.id);
				Ext.Ajax.request({
					url:sign.realPath('queryArrivesheetIsSignForPtpExpDeliver.action'),
					method: 'POST',
					jsonData: {
						'deliverbillDetailVo':{
							'deliverbillDetailDto':{
								'deliverbillNos':deliverbillNos,//派送单号
								'ids':deliverIds // 派送单id
							}
						}
					},
					success: function(response){
						var json = Ext.decode(response.responseText);
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),json.message,'ok',3000);
						//派送单号为空
						sign.ptpExpDeliverHandler.deliverbillNo = null;
						//送货确认按钮不可用
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(true);
					},exception: function(response){//异常处理
						var json = Ext.decode(response.responseText);
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(false);
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), json.message, 'error', 3000);//提示信息
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});

//财务信息
Ext.define('Foss.sign.ptpExpDeliverHandler.FinancialInfoForm',{
	extend: 'Ext.form.Panel',
	title :sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.financialInformation'),//财务信息
	id:'Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id',
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
	defaults: {
		padding:'5 20 10 5',
		labelWidth:85,
		readOnly: true
	},
	height:240,
	defaultType : 'textfield',
	layout:'column',
	items:[{
		name: 'pocketShipping',
		fieldLabel: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.pocketShipping'),//实付运费
		value:0,
		columnWidth: .33
	},{
		name: 'codAmount',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.codAmount') ,//代收货款
		value:0,
		columnWidth: .33
	},{
		name: 'toPayAmount',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.toPayAmount'),//收款总额
		value:0,
		columnWidth: .33
	},{
		name: 'receiveableAmount',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveableAmount'),//应收代收款
		value:0,
		columnWidth: .33
	},{
		name: 'receivedAmount',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receivedAmount'),//已收代收款
		value:0,
		columnWidth: .33
	},{
		name:'receiveablePayAmoout',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveablePayAmoout'),//应收到付款
		readOnly: true,
		value:0,
		columnWidth: .33
	},{
		name:'receivedPayAmount',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receivedPayAmount'),//已收到付款
		value:0,
		columnWidth: .33
	},{
		name:'paymentType',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.paymentType'),//付款方式
		columnWidth: .33,
		//allowBlank: false,//不允许为空
		xtype: 'combobox',
		forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
		valueField:'valueCode',
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store:sign.ptpExpDeliverHandler.PaymentStore,
		listeners : {
			'select' : function(combo, records, eOpts){
				var form = this.up('form').getForm();
				if(records[0].get('valueCode') =='NT' || records[0].get('valueCode')=='TT'
					|| records[0].get('valueCode') =='CD'){//支票,电汇,银行卡
					//款项确认编号可编辑
					form.findField('claimNo').setReadOnly(false);
				}else{
					form.findField('claimNo').setValue('');
					//款项确认编号不可编辑
					form.findField('claimNo').setReadOnly(true);
				}
			}
		}
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.claimNo'),//款项确认编号
		name: 'claimNo',
		labelWidth:95,
		columnWidth:.33
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.consigneeCode'),//客户
		//editable:false,
		//forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
		xtype : 'commoncustomerselector',
		name: 'consigneeCode',
		columnWidth:.33
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveCustomerContact'),//收货客户联系人
		name: 'receiveCustomerContact',
		columnWidth:.33
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveCustomerName'),//收货客户
		name: 'receiveCustomerName',
		labelWidth:95,
		columnWidth:.33
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.associateWaybillNoFee'),//关联单号费用
		name: 'oldReceiveablePayAmoout',
		value:0,
		columnWidth:.33
	},{
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.totalReceivesMoney'),//总收款金额
		name: 'totalMoney',
		value:0,
		columnWidth:.33
	}]
});
//签收流水号GridPanel
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
	columnLines: true,
	emptyText:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.emptyText'),//查询结果为空
	columnLines: true,
	height: 300,//高度
	id: 'Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID',
	frame: true,
	enableColumnHide: false,      //取消列头菜单
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {//取消选中时产生的事件
				var form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
				var UnNormalSelectAll = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
				var situation=form.findField('situation').getValue();
				//签收情况不为正常签收，同票多类异常
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD' ){
					Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.items[index].set('goodShorts',false);
					UnNormalSelectAll.setValue(false);
				}
			},
			select: function(model,record,index) {
				var form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
				var situation=form.findField('situation').getValue();
				var count=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.length;
				//签收情况不为正常签收，同票多类异常
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD' ){
					if(count=='1'){
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.items[0].set('goodShorts',true);
					}
				}
			}
		}
	}),
	title:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signPieces'),//签收件
	columns: [
		{header:sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.waybillNo'), dataIndex: 'waybillNo', flex: 1,align: 'center' },//268377 子母件 yanling 运单编号
		{header: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.serialNo'), dataIndex: 'serialNo', flex: 1,align: 'center' },//流水号
		//update begin
		{header: sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.goodShorts'), dataIndex: 'goodShorts', xtype:'checkcolumn',flex: 1.6,align: 'center', stopSelection:true,
			listeners:{
				'checkchange': function(model,record,index){
					var  SerialNoGridPanel =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID'),
						state=SerialNoGridPanel.getSelectionModel().isSelected(record),
						form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm(),
						situation=form.findField('situation').getValue(),
						count=SerialNoGridPanel.getStore().data.length;
					//签收情况不为正常签收，同票多类异常
					if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
						if(count=='1'){
							SerialNoGridPanel.getSelectionModel().selectAll(true);
							SerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
						}else if(!state){
							SerialNoGridPanel.getStore().data.items[record].set('goodShorts',false);
						}
					}
				}
			}}//是否内货短少
		//update end
	],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
			{
				name:'startSerialNo',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.start'),//从
				xtype:'numberfield',
				labelWidth:28,
				maxValue:9999,
				minValue:1,
				hideTrigger: true,
				columnWidth:.2,
				//allowBlank: false,//不允许为空
				allowNegative : false,//不允许输入负数
				allowDecimals : false//不允许有小数点
			},
			{
				name:'endSerialNo',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.end'),//到
				xtype:'numberfield',
				labelWidth:28,
				maxValue:9999,
				allowNegative : false,//不允许输入负数
				minValue:1,
				hideTrigger: true,
				columnWidth:.2,
				//allowBlank: false,//不允许为空
				allowDecimals : false//不允许有小数点
			},
			{
				xtype : 'button',
				columnWidth:.1,
				margin : '0 0 0 5',
				text :sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.choose'),// 选中
				handler:function(grid, rowIndex, colIndex){
					var startSerialNo =this.up('toolbar').items.items[0].value,
						endSerialNo =this.up('toolbar').items.items[1].value,
						me = this.up('toolbar').up('grid'),
						store = me.getStore(),
						records = store.getRange(),
						choose = [];
					if(Ext.isEmpty(startSerialNo) || Ext.isEmpty(endSerialNo)){
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.rangeMustEnter'), 'error', 4000);//区间必须输入
						return;
					}
					if(startSerialNo>9999 || endSerialNo >9999){
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.largeNumber'), 'error', 4000);//数字最大范围是9999
						return;
					}
					var selectRow = me.getSelectionModel().getSelection();
					if (selectRow != null && selectRow.length >= 0){
						for(var i = 0; i < selectRow.length; i ++){
							choose.push(selectRow[i]);
						}
					}
					if(startSerialNo >endSerialNo){
						for(var j = endSerialNo; j <= startSerialNo; j ++){
							for(var i = 0; i < records.length; i ++){
								var serialNo='000'+j,serialNo1='00'+j,serialNo2='0'+j;
								if(serialNo== records[i].get('serialNo')||
									serialNo1== records[i].get('serialNo')||
									serialNo2== records[i].get('serialNo')||
									j== records[i].get('serialNo')){
									choose.push(records[i]);
								}
							}
						}
						me.getSelectionModel().select(choose);
					}else {
						for(var j = startSerialNo; j <= endSerialNo; j ++){
							for(var i = 0; i < records.length; i ++){
								var serialNo='000'+j,serialNo1='00'+j,serialNo2='0'+j;
								if(serialNo== records[i].get('serialNo')||
									serialNo1== records[i].get('serialNo')||
									serialNo2== records[i].get('serialNo')||
									j== records[i].get('serialNo')){
									choose.push(records[i]);
									break;
								}
							}
						}
						me.getSelectionModel().select(choose);
					}

				}
			}, {
				xtype : 'container',
				border : false,
				columnWidth : .1,
				html : '&nbsp;'
			}, {
				xtype : 'checkbox',
				fieldLabel : '全选',
				columnWidth : .06,
				labelWidth : 140,
//					height : 20,
				name : 'unNormalSelectAll',
				id : 'Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID',
				hidden :true,
				allowBlank : true,
				listeners : {
					change : function(field , newValue , oldValue , object){
						var StorageGridPanel = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
						var selectRows = StorageGridPanel.getSelectionModel().getSelection();
						if(newValue == true){
							if(selectRows != null && selectRows.length > 0){
								//each用于迭代selectRows
								Ext.each(selectRows , function(selectRow , index , allSelectRow){
									//这里‘goodShorts’代表流水号面板的第二列
									selectRow.set('goodShorts' , true);
								});
							}else{
								//如果当前没有被选中的项，则该多选框不让选
								field.setValue(false);
							}
						}else{
							if(selectRows != null && selectRows.length > 0){
								//each用于迭代selectRows
								Ext.each(selectRows , function(selectRow , index , allSelectRow){
									//这里‘goodShorts’代表流水号面板的第二列
									selectRow.set('goodShorts' , false);
								});
							}
						}
					}
				}
			}]
	}],
	viewConfig: {
		enableTextSelection: true
	},listeners:{
		beforeselect: function(rowModel, record, index, eOpts) {
			var signOutStorageForm=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
			var signStatus=signOutStorageForm.findField('signStatus').getValue();
			//若签收状态为"未签收" 签收件不可选
			if(signStatus==sign.ptpExpDeliverHandler.SIGN_STATUS_NO){
				return false;
			}
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ptpExpDeliverHandler.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

/**---------------------------------------------------------------*/
//gird————
sign.ptpExpDeliverHandler.CellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});

// 同票多类异常里的无异常签收流水号GridPanel
Ext.define('Foss.sign.ptpExpDeliverHandler.MultiExceptionNormalSerialNoGridPanel',{
	extend:'Ext.grid.Panel',
	columnLines: true,
	emptyText:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.emptyText'),//查询结果为空
	columnLines: true,
	height: 230,//高度
	id: 'Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID',
	frame: true,
	enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	title:'无差异流水号',//签收件
	columns: [
		{header: sign.ptpExpDeliverHandler.i18n('pkp.sign.expSign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'}
	],
	viewConfig: {
		enableTextSelection: true
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ptpExpDeliverHandler.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

//同票多类
Ext.define('Foss.sign.ptpExpDeliverHandler.MultiExceptionSignPiecesForm',{
	extend: 'Ext.form.Panel',
	title:'签收件',
	defaultType : 'textfield',
	layout: 'column',
	frame:true,
	id: 'Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	signOutWithTicketStorageGridPanel :　null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.ptpExpDeliverHandler.OutWithTicketStorageGridPanel',{
				columnWidth : 1
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},
	multiExceptionNormalSerialNoGridPanel :　null,
	getMultiExceptionNormalSerialNoGridPanel: function(){
		if(this.multiExceptionNormalSerialNoGridPanel == null){
			this.multiExceptionNormalSerialNoGridPanel = Ext.create('Foss.sign.ptpExpDeliverHandler.MultiExceptionNormalSerialNoGridPanel',{
				columnWidth : 1
			});
		}
		return this.multiExceptionNormalSerialNoGridPanel;
	},
	listeners : {
		beforerender:function(){
			Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID').hide();
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getSignOutWithTicketStorageGridPanel(),me.getMultiExceptionNormalSerialNoGridPanel()/*,
			 {
			 labelWidth: 100,
			 id:'signSeriaNoNote_id',
			 xtype: 'textarea',
			 name:'signSeriaNoNote',
			 //editable:false,
			 readOnly:true,
			 height: 120,
			 width:530,
			 cls:'readonlygraybackground',
			 maxLength:200,
			 //border:1,
			 fieldLabel:'无差异流水号'
			 }*/
		];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.sign.ptpExpDeliverHandler.OutWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
	emptyText:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.emptyText'),//查询结果为空
	id: 'Foss_sign_ptpExpDeliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID',
	frame: false,
	selModel: {selType:'cellmodel'},
	//收缩
	//collapsible: true,
	// title:'流水号明细',//流水号明细
	plugins:[sign.ptpExpDeliverHandler.CellEditing],
	viewConfig: {
		forceFit : true
	},
	columns: [
		{
			xtype:'actioncolumn',
			align: 'center',
			menuDisabled:true,
			text: '删除',//删除
			minWidth:65,maxWidth:65,
			align: 'center',
			items: [{
				//  iconCls : 'deppon_icons_remove',
				iconCls : 'deppon_icons_delete',
				tooltip: '删除',//删除
				disabled:'',
				handler: function(grid, rowIndex, colIndex) {
					var store=this.up('panel').getStore(),
						signState =store.getAt(rowIndex).data.signState,
						serialNo=store.getAt(rowIndex).data.serialNo,
						multiNormalSerNos=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
						multiNormalSerNosStore=multiNormalSerNos.getStore();
					if(!Ext.isEmpty(signState) && signState=='UNNORMAL_GOODSHORT'){
						var form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
						sign.ptpExpDeliverHandler.setPartFormVisible(form,false);
					}
					if(!Ext.isEmpty(serialNo)){
						var serialNoArray=serialNo.split(',');
						for(var i=0;i<serialNoArray.length;i++){
							var newRecord={serialNo:serialNoArray[i]};
							multiNormalSerNosStore.insert(multiNormalSerNosStore.getCount(),newRecord);
						}
					}
					store.removeAt(rowIndex);
				}

			}]
		},

		{header: '签收情况', dataIndex: 'signState',minWidth:135,maxWidth:135,menuDisabled:true,align: 'center',
			editor:{
				xtype:'combobox',
				//name: 'situation',
				labelWidth: 60,
				forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
				editable:false, //不可编辑
				valueField:'valueCode',
				displayField: 'valueName',
				queryMode:'local',//如果不加这句话，就查的是签收情况里所有的项
				triggerAction:'all',
				store : sign.ptpExpDeliverHandler.exsituationStore,
				listeners : {
					'change' : function(field,newValue, oldValue, eOpts){
						var arr = this.up('panel').getStore().collect('signState'),
							flag = arr.indexOf(newValue),
							form=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
						if(oldValue=='UNNORMAL_GOODSHORT'&& !Ext.isEmpty(newValue)){
							sign.ptpExpDeliverHandler.setPartFormVisible(form,false);
						}
						if(flag>=0){
							this.setValue('');
							Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), '签收情况已被选择', 'error', 3000);
							return false;
						}
						if(newValue=='UNNORMAL_GOODSHORT'){
							sign.ptpExpDeliverHandler.setPartFormVisible(form,true);
						}
					}
				}
			},
			renderer:function(value){
				return FossDataDictionary.rendererSubmitToDisplay(value, sign.ptpExpDeliverHandler.PKP_SIGN_SITUATION);
			}
		},
		{header: '流水号选择', dataIndex: 'choose',/*id:'choose_id'+new Date().getTime(),*/
			renderer:function(value,cellmeta){
				var returnStr = "<input type='button' value='选择'  style='width: 60px'/>";
				return returnStr;
			},minWidth:98,maxWidth:98,menuDisabled:true,
			listeners:{
				'click': function(model,record,index){
					var value = this.up('panel').getStore().getAt(index).data.signState;
					if(!Ext.isEmpty(value)){
						//当前行的流水号数据
						var serialNo = this.up('panel').getStore().getAt(index).data.serialNo,
							multiNormalSerNos=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
							multiNormalSerNosStore=multiNormalSerNos.getStore(),
							showSerials=new Array(),
							chooseModel=new Array();
						var win ;
						var seriaNoGrid ;
						var serialNoOutStorage;
						if(!Ext.getCmp('Foss_sign_ptpExpDeliverHandler_WithTicketStoraWindow_ID')){
							win = Ext.create('Foss.sign.ptpExpDeliverHandler.WithTicketStoraWindow');
							seriaNoGrid=win.getSerialNoOutStorageGridPanel();
							serialNoOutStorage = seriaNoGrid.getStore();
							seriaNoGrid.getSelectionModel().deselectAll();
							serialNoOutStorage.removeAll();
						}else{
							win = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_WithTicketStoraWindow_ID');
							seriaNoGrid=win.getSerialNoOutStorageGridPanel();
							serialNoOutStorage = seriaNoGrid.getStore();
							seriaNoGrid.getSelectionModel().deselectAll();
							serialNoOutStorage.removeAll();
						}
						if(!Ext.isEmpty(serialNo)) {
							var serialNoArray=serialNo.split(',');
							for(var i=0;i<serialNoArray.length;i++){
								showSerials.push(
									{'waybillNo':sign.ptpExpDeliverHandler.SIGN_RECORD_WAYBILLNO_INDEX,
										'serialNo':serialNoArray[i],'isSelect':true});
								chooseModel.push(showSerials);
							}

						}
						if(multiNormalSerNosStore.data.length >0){
							for(var i=0;i<multiNormalSerNosStore.data.length;i++){
								showSerials.push(
									{'waybillNo':sign.ptpExpDeliverHandler.SIGN_RECORD_WAYBILLNO_INDEX,
										'serialNo':multiNormalSerNosStore.data.items[i].data.serialNo,'isSelect':false});
							}
						}
						serialNoOutStorage.loadData(showSerials,true);
						sign.ptpExpDeliverHandler.SIGN_RECORD_SERINALNO_INDEX=index;
						win.show();
						if(chooseModel.length>0){
							var selectSerials=[];
							var serialNoOutStorageRecords = serialNoOutStorage.getRange();
							for(var i=0;i<serialNoOutStorageRecords.length;i++){
								if(serialNoOutStorageRecords[i].get('isSelect')){
									selectSerials.push(serialNoOutStorageRecords[i]);
								}
							}
							seriaNoGrid.getSelectionModel().select(selectSerials);
						}
					}else{
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), '签收情况没有被选择', 'error', 3000);
						return false;
					}
				}
			},align: 'center'
		},
		{header: '异常流水号', dataIndex: 'serialNo',
			xtype : 'ellipsiscolumn',
			menuDisabled:true,align: 'center',name:'serialNo',minWidth:397,maxWidth:397
		}
	],
	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
			{
				xtype : 'button',
				width:50,
				margin : '0 0 0 5',
				text :'新增',
				hidden:'',
				handler:function(grid, rowIndex, colIndex){
					var count = sign.ptpExpDeliverHandler.situationStore.getCount();
					var panelGridCount = this.up('panel').getStore().getCount();
					if(panelGridCount>=count){
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收情况已全部选择,不可再添加!','error',1000);
						return false;
					}
					var model=Ext.create('Foss.sign.ptpExpDeliverHandler.SignOutWithTicketStorageModel',{
						signState:'',
						choose:'选择',
						serialNo:''
					});
					var store=this.up('panel').getStore();
					store.insert(0,model);
					sign.ptpExpDeliverHandler.CellEditing.startEditByPosition({row: 0, column: 0});
				}
			}]
	}],
	viewConfig: {
		enableTextSelection: true
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ptpExpDeliverHandler.OutStateWithTicketStorageStore');
		me.callParent([cfg]);
	},
	listeners:{
	}
});
//store
Ext.define('Foss.sign.ptpExpDeliverHandler.OutStateWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.ptpExpDeliverHandler.SignOutWithTicketStorageModel',
	listeners:{

	}
});
//gird
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
	columnLines: true,
	emptyText:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.emptyText'),//查询结果为空
	height: 300,//高度
	id: 'Foss_sign_ptpExpDeliverHandler_SerialNoWithTicketMulStorageGridPanel',
	frame: true,
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {//取消选中时产生的事件
				record.data.isSelect=false;
			},
			select: function(model,record,index) {
				if(Ext.isEmpty(record.data.isSelect) || record.data.isSelect==false){
					record.data.isSelect=true;
				}
			}
		}
	}),
	title:'流水号明细',//流水号明细
	columns: [
		{header: sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.waybillNo'), dataIndex: 'waybillNo',hidden:true}, //268377 子母件 yanling
		{header: '流水号', dataIndex: 'serialNo'},
		{dataIndex: 'isSelect',hidden:true}

	],
	viewConfig: {
		enableTextSelection: true
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageStore');
		me.callParent([cfg]);
	},
	listeners:{
	}
});
//store
Ext.define('Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageModel'
});
//window
// 弹出窗口
Ext.define('Foss.sign.ptpExpDeliverHandler.WithTicketStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_ptpExpDeliverHandler_WithTicketStoraWindow_ID',
	closeAction: 'hide',
	layout: 'auto',
	width:300,
	resizable : false,
	title:'流水号选择',//流水号选择
	modal: true,
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.ptpExpDeliverHandler.SerialNoWithTicketStorageGridPanel',{
				width:270,
				text : sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signPieces')//签收件
			});
		}
		return this.SerialNoOutStorageGridPanel;
	},
	serialButtons  : null,
	getSerialButtons: function(){
		if(this.serialButtons  == null){
			this.serialButtons = Ext.create('Ext.button.Button',{
				text:'确定',
				width:'100',
				style : {
					float : 'center'
				},
				cls:'yellow_button',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					seconds: 3
				}),
				handler:function(){
					var SerialNoWithTicket =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoWithTicketMulStorageGridPanel'),
						serialNos = SerialNoWithTicket.getSelectionModel().getSelection(),
						SerialNoWithTicketStore = SerialNoWithTicket.getStore().data,
						frontGrid=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID'),
						frontGrid_Store=frontGrid.getStore(),
						multiNormalSerNos=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
						multiNormalSerNosStore=multiNormalSerNos.getStore(),
						selectSerialNos="",
						deselectSerialNos =new Array();
					if(serialNos.length<=0){
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), '请选择各类型异常流水号!', 'error', 1000);
						return false;
					}
					for(var i=0;i<SerialNoWithTicketStore.length;i++){
						if(SerialNoWithTicketStore.items[i].data.isSelect){
							selectSerialNos+=SerialNoWithTicketStore.items[i].data.serialNo;
							selectSerialNos+=',';
						}else{
							deselectSerialNos.push(
								{'serialNo':SerialNoWithTicketStore.items[i].data.serialNo});
						}
					}
					multiNormalSerNos.getSelectionModel().deselectAll();
					multiNormalSerNosStore.removeAll();
					if(deselectSerialNos.length>0){
						multiNormalSerNosStore.loadData(deselectSerialNos,true);
						multiNormalSerNos.getSelectionModel().selectAll(true);
					}
					if(!Ext.isEmpty(selectSerialNos)){
						frontGrid_Store.data.items[sign.ptpExpDeliverHandler.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',selectSerialNos.substring(0,selectSerialNos.length-1));
					}else{
						frontGrid_Store.data.items[sign.ptpExpDeliverHandler.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',"");
					}
					this.up('panel').close();
				}
			});
		}
		return  this.serialButtons ;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getSerialNoOutStorageGridPanel()
		];
		me.buttons = [me.getSerialButtons()];
		me.callParent([cfg]);
	}
});


/**---------------------------------------------------------------*/
//签收信息
Ext.define('Foss.sign.ptpExpDeliverHandler.SignInfoForm',{
	extend: 'Ext.form.Panel',
	title:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signForInformation'),//签收信息
	id:'Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id',
	//收缩
	collapsible: true,
	//是否有框
	frame:true,
	//动画收缩
	animCollapse: true,
//	height:430,
//	layout:'column',
	margin:'-10 1 1 1',
	items:[{
		xtype: 'form',
		columnWidth:.65,
		defaults: {
			labelWidth:65,
			padding: '5 5 5 5'
		},
		cls : 'autoHeight',
		bodyCls : 'autoHeight',
		defaultType : 'textfield',
		frame:true,
		title:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signForInformation'),//签收信息
		layout:'column',
		items:[
			{
				name:'arrivesheetNo',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.arrivesheetNo'),//到达联编号
				columnWidth:.3,
				labelWidth:75,
				readOnly: true//只读
			},{
				name:'arriveSheetGoodsQty',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.arriveSheetGoodsQty'),//到达联总件数
				columnWidth:.22,
				labelWidth:60,
				readOnly: true//只读
			},{
				name: 'isPdaSign',
				xtype:'checkbox',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.isPdaSign'),//PDA
				columnWidth:.15,
				readOnly:true,
				labelWidth:50,
				height: 24,
				hidden:true,
				inputValue: 'Y'
			},{
				name:'czmGoodsQty',
				fieldLabel:'子母件总票数',//"子母件总票数"
				columnWidth:.22,
				labelWidth:100,
				readOnly: true//只读
			},{
				xtype:'combobox',
				name: 'signStatus',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.signStatus'),//到达联签收状态
				labelWidth: 100,
				forceSelection:true,// 必须选择一个。
				valueField:'valueCode',
				displayField: 'valueName',
				allowBlank:false,
				queryMode:'local',
				triggerAction:'all',
				columnWidth: .33,
				store :sign.ptpExpDeliverHandler.signStatusStore,
				listeners : {
					'select' : function(combo, records, eOpts){
						form = this.up('form').getForm();
						form.findField('packingResult').setVisible(false);
						form.findField('alittleShort').setVisible(false);
						var	financialInfo = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
						serialGrid =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
						form.findField('pullbackReason').setValue('');//拉回原因为空
						form.findField('signNote').setValue('');//签收备注为空
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID').setVisible(false);
						serialGrid.show();
						//serialGrid.columns[2].hide();  268377 到达联签收状态改变不隐藏流水号
						Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID').hide();
						var isVisiable=true;
						if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_NO){//到达联签收状态为‘未签收’
							serialGrid.getSelectionModel().deselectAll();//去掉流水号面板所有选中项
							form.findField('situationGoodsBack').setValue('GOODS_BACK');//为未签收的签收情况设置初始值‘货物拉回’
							form.findField('situationGoodsBack').setReadOnly(true);
							form.findField('situationGoodsBack').setDisabled(false);
							form.findField('signGoodsQty').setValue(0);//签收件数为0
							form.findField('signGoodsQty').setDisabled(true)//签收件数不可编辑
							form.findField('pullbackReason').setVisible(true);//拉回原因可见
							form.findField('pullbackReason').setDisabled(false);//拉回原因可编辑
							//form.findField('sendExpressEmpCode').setVisible(false);//快递员不可见 add by 231438
							//form.findField('sendExpressEmpCode').setValue('');//快递员值清空 add by 231438
							sign.ptpExpDeliverHandler.formFieldReset(financialInfo,true);
							sign.ptpExpDeliverHandler.isReadOnly(financialInfo,true);
							financialInfo.findField('paymentType').setValue(sign.ptpExpDeliverHandler.paymentType);
							Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[1].hide();  //268377 非子母件签收隐藏单号项
							Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide();  //268377

						}else{//到达联签收状态为‘全部签收’或‘部分签收’
							isVisiable=false;
							form.findField('pullbackReason').setVisible(false);//拉回原因不可见
							if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_ALL){
								serialGrid.getSelectionModel().selectAll()
							}
							//签收情况默认选中“正常签收”
							form.findField('situation').setValue('NORMAL_SIGN');
							//签收备注 默认为“正常签收”
							form.findField('signNote').setValue(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.normalSign'));//正常签收
							form.findField('signGoodsQty').setValue(form.findField('arriveSheetGoodsQty').getValue());//签收件数等于到达联件数
							//form.findField('sendExpressEmpCode').setVisible(true);//快递员可见  add by 231438
							sign.ptpExpDeliverHandler.isReadOnly(financialInfo,false);
							if(financialInfo.findField('paymentType').getValue() =='NT' || financialInfo.findField('paymentType').getValue()=='TT'
								|| financialInfo.findField('paymentType').getValue() =='CD'){//支票,电汇,银行卡
								//款项确认编号可编辑
								financialInfo.findField('claimNo').setReadOnly(false);
							}else{
								financialInfo.findField('claimNo').setValue('');
								//款项确认编号不可编辑
								financialInfo.findField('claimNo').setReadOnly(true);
							}
							if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_PARTIAL){// 签收状态选择为部分签收时，签收件数显示为到达联件数且可编辑
								form.findField('signGoodsQty').setDisabled(false);
							}else {
								form.findField('signGoodsQty').setDisabled(true)//签收件数不可编辑
							}
							var receiveCustomerContact=financialInfo.findField('receiveCustomerContact').getValue();
							if(!Ext.isEmpty(receiveCustomerContact)){
								//签收类型为收货人本人
								if(receiveCustomerContact==sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_NAME){
									form.findField('deliverymanType').setValue('SIGN_PERSON_ME');
								}else{
									form.findField('deliverymanType').setValue('');
								}
							}
						}
						form.findField('deliverymanType').reset();
						form.findField('deliverymanType').allowBlank = isVisiable;
						form.findField('deliverymanType').setVisible(!isVisiable);//签收人类型是否可见

						form.findField('situationGoodsBack').setVisible(isVisiable);//货物拉回是否可见
						form.findField('situation').setVisible(!isVisiable);//签收情况是否可见
						form.findField('signNote').setVisible(!isVisiable);//
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[1].hide();  //268377
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide();  //268377
					}
				}
			},{
				xtype:'component',
				height:15,
				columnWidth:.33
			},{
				xtype: 'datetimefield_date97',
				format : 'Y-m-d H:i:s',
				id: 'ptpExpDeliverHandler-QueryPanel-signTimeEnd',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signTime'),//签收时间
				allowBlank:false,
				columnWidth:.33,
				editable:false,
				value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
				name: 'signTime',
				time : true,
				dateConfig: {
					el : 'deliverHandler-QueryPanel-signTimeEnd-inputEl',
					minDate: '%y-%M-%d 00:00:00',
					maxDate:'%y-%M-%d 23:59:59'
				}
			},{//子母件签收状态。 268377 yanling
				xtype:'combobox',
				name: 'czmStatus',
				fieldLabel:'子母件签收状态', //'子母件签收状态'
				labelWidth: 100,
				forceSelection:true,// 必须选择一个。
				valueField:'valueCode',
				displayField: 'valueName',
				allowBlank:false,
				queryMode:'local',
				triggerAction:'all',
				columnWidth: .33,
				store :sign.ptpExpDeliverHandler.signStatusStore,
				listeners : {
					'select' : function(combo, records, eOpts){
						form = this.up('form').getForm();
						form.findField('packingResult').setVisible(false);
						form.findField('alittleShort').setVisible(false);
						var	financialInfo = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
						serialGrid =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
						form.findField('pullbackReason').setValue('');//拉回原因为空
						form.findField('signNote').setValue('');//签收备注为空
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID').setVisible(false);
						serialGrid.show();
						Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID').hide();
						var isVisiable=true;
						if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_NO){//子母件联签收状态为‘未签收’
							Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide();  //268377
							serialGrid.getSelectionModel().deselectAll();//去掉流水号面板所有选中项
							form.findField('situationGoodsBack').setValue('GOODS_BACK');//为未签收的签收情况设置初始值‘货物拉回’
							form.findField('situationGoodsBack').setReadOnly(true);
							form.findField('situationGoodsBack').setDisabled(false);
							form.findField('signGoodsQty').setValue(0);//签收件数为0
							form.findField('signGoodsQty').setDisabled(true)//签收件数不可编辑
							form.findField('pullbackReason').setVisible(true);//拉回原因可见
							form.findField('pullbackReason').setDisabled(false);//拉回原因可编辑
							//form.findField('sendExpressEmpCode').setVisible(false);//快递员不可见 add by 231438
							//form.findField('sendExpressEmpCode').setValue('');//快递员值清空 add by 231438
							sign.ptpExpDeliverHandler.formFieldReset(financialInfo,true);
							sign.ptpExpDeliverHandler.isReadOnly(financialInfo,true);
							financialInfo.findField('paymentType').setValue(sign.ptpExpDeliverHandler.paymentType);
						}else{//子母件签收状态为‘全部签收’或‘部分签收’
							isVisiable=false;
							form.findField('pullbackReason').setVisible(false);//拉回原因不可见
							if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_ALL){
								serialGrid.getSelectionModel().selectAll()
							}
							//签收情况默认选中“正常签收”
							form.findField('situation').setValue('NORMAL_SIGN');
							//签收备注 默认为“正常签收”
							form.findField('signNote').setValue(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.normalSign'));//正常签收
							form.findField('signGoodsQty').setValue(form.findField('arriveSheetGoodsQty').getValue());//签收件数等于到达联件数
							//form.findField('sendExpressEmpCode').setVisible(true);//快递员可见  add by 231438
							sign.ptpExpDeliverHandler.isReadOnly(financialInfo,false);
							if(financialInfo.findField('paymentType').getValue() =='NT' || financialInfo.findField('paymentType').getValue()=='TT'
								|| financialInfo.findField('paymentType').getValue() =='CD'){//支票,电汇,银行卡
								//款项确认编号可编辑
								financialInfo.findField('claimNo').setReadOnly(false);
							}else{
								financialInfo.findField('claimNo').setValue('');
								//款项确认编号不可编辑
								financialInfo.findField('claimNo').setReadOnly(true);
							}
							if(records[0].get('valueCode') ==sign.ptpExpDeliverHandler.SIGN_STATUS_PARTIAL){// 签收状态选择为部分签收时，签收件数显示为到达联件数且可编辑
								form.findField('signGoodsQty').setDisabled(false);
							}else {
								form.findField('signGoodsQty').setDisabled(true)//签收件数不可编辑
							}
							var receiveCustomerContact=financialInfo.findField('receiveCustomerContact').getValue();
							if(!Ext.isEmpty(receiveCustomerContact)){
								//签收类型为收货人本人
								if(receiveCustomerContact==sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_NAME){
									form.findField('deliverymanType').setValue('SIGN_PERSON_ME');
								}else{
									form.findField('deliverymanType').setValue('');
								}
							}
						}
						form.findField('deliverymanType').reset();
						form.findField('deliverymanType').allowBlank = isVisiable;
						form.findField('deliverymanType').setVisible(!isVisiable);//签收人类型是否可见

						form.findField('situationGoodsBack').setVisible(isVisiable);//货物拉回是否可见
						form.findField('situation').setVisible(!isVisiable);//签收情况是否可见
						form.findField('signNote').setVisible(!isVisiable);//签收备注是否可见
						Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide();  //268377
					}
				}
			},{
				name:'signGoodsQty',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signGoodsQty'),//签收件数
				xtype:'numberfield',
				labelWidth:60,
				hideTrigger: true,
				columnWidth:.33,
				allowBlank: false,//不允许为空
				allowDecimals : false,//不允许有小数点
				allowNegative:false,
				listeners :{
					'blur':function(me,theEvent,eOpts){
						var form = this.up('form').getForm();
						var signGoodsQty = form.findField('signGoodsQty').getValue();
//				form.findField('czmGoodsQty').setValue(55); 268377 测试
						var czmGoodsQty = form.findField('czmGoodsQty').getValue();
						var arriveSheetGoodsQty = form.findField('arriveSheetGoodsQty').getValue();


						//268377 修改  当到达联状态不可用，不做签收件数与总件数关系判断
						if(!Ext.isEmpty(signGoodsQty)&& parseInt(signGoodsQty) <= 0){// 如果签收情况为部分签收
							Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signGoodsQtyGreateThanZero'),'error',1000);//请选择一行！
						}
						//268377 修改  当到达联状态不可用，不做签收件数与总件数关系判断
						if(!Ext.isEmpty(signGoodsQty)&&parseInt(signGoodsQty)>arriveSheetGoodsQty && !form.findField('signStatus').disabled){
							Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.partSignSignGoodsQtyUnqualified'),'error',1000);//请选择一行！
						}
						//268377 添加子母件签收件数不能大于子母件总票数
						if(!Ext.isEmpty(signGoodsQty) && (parseInt(signGoodsQty) > czmGoodsQty || parseInt(signGoodsQty) > sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT) && !form.findField('czmStatus').disabled){
							Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收件数不能超过子母件总票数且快递子母件最多允许一次签收'+sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT+'件！','error',1000);//请选择一行！
						}
					}
				}
			},{
				xtype: 'combobox',
				name:'situation',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.situation'),//签收情况选择
				allowBlank: false,//不允许为空
				forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
				valueField:'valueCode',
				columnWidth:.33,
				displayField: 'valueName',
				queryMode:'local',
				triggerAction:'all',
				store : sign.ptpExpDeliverHandler.situationStore,
				listeners : {
					'select' : function(combo, records, eOpts){
						var form = this.up('form').getForm();
						var SerialNoGridPanel =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
						var UnNormalSelectAll = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
						UnNormalSelectAll.setVisible(false);
						var WithTicketFormPanel = Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID');
						sign.ptpExpDeliverHandler.setPartFormVisible(form,false);
						SerialNoGridPanel.show();
						WithTicketFormPanel.hide();
						//如果签收情况为正常签收
						if(records[0].get('valueCode') == 'NORMAL_SIGN'){
							SerialNoGridPanel.columns[3].hide();
							form.findField('signNote').setValue(records[0].get('valueName'));
						}else if(records[0].get('valueCode') == 'UNNORMAL_SAMEVOTEODD'){//同票多类异常
							//若子母件是同票多类异常操作 268377
							if(!form.findField('czmStatus').disabled){
								Ext.MessageBox.show({
									title : '提示',
									msg : '子母件不支持此操作',
									width : 250,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
								form.findField('situation').setValue('NORMAL_SIGN');
								form.findField('signNote').setValue('正常签收');
								Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[3].hide(); //268377   快递代理加载效果      隐藏 是否内货短少项
								return;
							}
							SerialNoGridPanel.hide();
							WithTicketFormPanel.show();
							var SignOutSerialNoWithTicket=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID');
							var  multiNormalSerNos=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
								multiNormalSerNosStore=multiNormalSerNos.getStore();
							multiNormalSerNos.getSelectionModel().deselectAll();
							multiNormalSerNosStore.removeAll();
							if(SerialNoGridPanel.getStore().data.length>0){
								multiNormalSerNosStore.loadData(SerialNoGridPanel.getStore().data.items);
								multiNormalSerNos.getSelectionModel().selectAll(true);
							}
							//signSeriaNoNote.setValue(sign.sign.SIGN_SERIAL_NO_ARR.join(','));
							//初始化一条记录
							var SignOutSerialNoWithTicketStore=SignOutSerialNoWithTicket.getStore();
							if(SignOutSerialNoWithTicketStore.data.length>0){
								//如果不加deselectAll()，多次加载时表格里的记录存在为空，报isDescendantOf' of undefined
								SignOutSerialNoWithTicket.getSelectionModel().deselectAll();
								SignOutSerialNoWithTicketStore.removeAll();
							}
							var model=Ext.create('Foss.sign.ptpExpDeliverHandler.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_GOODSHORT',
								choose:'选择',
								serialNo:''
							});
							var model1=Ext.create('Foss.sign.ptpExpDeliverHandler.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_BREAK',
								choose:'选择',
								serialNo:''
							});
							SignOutSerialNoWithTicketStore.insert(0,model);
							SignOutSerialNoWithTicketStore.insert(1,model1);
							//内物短少信息初始为显示
							sign.ptpExpDeliverHandler.setPartFormVisible(form,true);
						}else {
							SerialNoGridPanel.columns[3].show();
							SerialNoGridPanel.columns[3].setText('是否'+records[0].get('valueName'));
							UnNormalSelectAll.setVisible(true);
							UnNormalSelectAll.setValue(false);
							UnNormalSelectAll.setFieldLabel('是否'+records[0].get('valueName')+'全选');
							var count=SerialNoGridPanel.getStore().data.length;
							if(count=='1'){
								//把前面的流水号也默认勾选上
								SerialNoGridPanel.getSelectionModel().selectAll(true);
								SerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
								UnNormalSelectAll.setValue(true);
							}
							if(records[0].get('valueCode')=='UNNORMAL_GOODSHORT'){
								sign.ptpExpDeliverHandler.setPartFormVisible(form,true);
							}else{
								sign.ptpExpDeliverHandler.COUNT += 1;
								if(sign.ptpExpDeliverHandler.COUNT == 1){
									Ext.MessageBox.show({
										title : '提示',
										msg : '若内物短少与其他异常同时发生于同一件货物上，请优先选择“异常-内物短少”；否则一经查实，将按业绩粉饰处理。',
										width : 250,
										buttons : Ext.Msg.OK,
										icon : Ext.MessageBox.WARNING
									});
								}
							}
						}
					}
				}
			},{
				xtype: 'combobox',
				name:'situationGoodsBack',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.situation'),//签收情况选择
				allowBlank: false,//不允许为空
				forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
				valueField:'valueCode',
				columnWidth:.33,
				displayField: 'valueName',
				queryMode:'local',
				triggerAction:'all',
				store : sign.ptpExpDeliverHandler.situationGoodBackStore
			},{
				xtype: 'combobox',
				name:'pullbackReason',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.pullbackReason'),//拉回原因
				forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
				allowBlank: false,//不允许为空
				valueField:'valueCode',
				displayField: 'valueName',
				queryMode:'local',
				columnWidth:1,
				triggerAction:'all',
				store : FossDataDictionary.getDataDictionaryStore(sign.ptpExpDeliverHandler.PKP_PULLBACK_REASON, null)
			},{
				fieldLabel: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.packageResult'),//外包装是否完好
				xtype: 'radiogroup',
				vertical: true,
				allowBlank:false,
				columnWidth:.4,
				anchorSize: 50,
				labelWidth:105,
				style : 'margin-left:5px;margin-top:6px',
				layout:'column',
				columns: 1,
				name: 'packingResult',
				items: [
					{style:'margin-top:5px', itemId: 'yes', boxLabel: '是', name: 'packingResult', inputValue: 'Y', checked: true},
					{style:'margin-top:5px;margin-left:5px', itemId: 'no', boxLabel: '否', name: 'packingResult', inputValue: 'N' }
				]
			},{
				name:'alittleShort',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.alittleShort'),
				xtype:'textfield',
				labelWidth:50,
				hideTrigger: true,
				columnWidth:.6,
				allowBlank: false,//不允许为空
				validator: function(alittleShort) {
					var totalCount = 0;
					for (var i=0; i<alittleShort.trim().length; i++) {
						var c = alittleShort.charCodeAt(i);
						if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
							totalCount++;
						}else {
							totalCount+=3;
						}
					}
					if(totalCount>50){
						return sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.alittleShortMaxLength');
					}else{
						return true;
					}
				},
				listeners: {
					blur: function(field, value) {
						var form = this.up('form').getForm();
						var alittleShort = field.getValue();
//					var signNote = form.findField('signNote').getValue();
						form.findField('signNote').setValue('');
						var rstValue;
						if(!Ext.isEmpty(alittleShort)){
							rstValue=sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.unnormalGoodshort')+','+sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.littleShort')+':'+form.findField('alittleShort').getValue();
						}else{
							rstValue=sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.unnormalGoodshort');
						}
						form.findField('signNote').setValue(rstValue);
					}
				}
			},{
				xtype:'combo',
				name: 'deliverymanType',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.deliverymanType'),//签收人类型
				labelWidth: 80,
				forceSelection: true,
				editable:false,
				forceSelection:true,
				valueField:'valueCode',
				displayField: 'valueName',
				allowBlank:false,
				queryMode:'local',
				triggerAction:'all',
				columnWidth: .5,
				store : sign.ptpExpDeliverHandler.signTypeStore
			},{
				name:'signNote',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signNote'),//签收备注
				columnWidth:.5,
				maxLength:80,
				allowBlank: false//不允许为空
			},{ //add by 231438  是否快递员派送：Y；在快递派送处理界面默认为：是
				name: 'isCourierDelivery',
				columnWidth:.35,
				hidden:true
			},{//add by 231438  快递员(选择器)
				name:'sendExpressEmpCode',
				fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.courier'),//快递员:
				xtype:'commonExpressemployeeselector',
				labelWidth:60,
				columnWidth:.4,
				maxLength:50,
				hidden:true
			}]
	},Ext.create('Foss.sign.ptpExpDeliverHandler.SerialNoOutStorageGridPanel',{
//		columnWidth:.35
		height : 300,
		margin : '-10 1 1 1'
	}),Ext.create('Foss.sign.ptpExpDeliverHandler.MultiExceptionSignPiecesForm',{
//		height:300,
		margin:'-10 1 1 1'
	})],
	checkButtons  : null,
	getCheckButtons: function(){
		if(this.checkButtons  == null){
			this.checkButtons = Ext.create('Ext.button.Button',{
				text:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.confirm'),//确认 
				disabled:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexsignbutton'),
				hidden:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexsignbutton'),
				width:'100',
				disabled:true,
				id:'Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id',
				style : {
					float : 'center'
				},
				cls:'yellow_button',
				handler:function(){
					var baseForm  = this.up('form');
					var	financialForm = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm(),
						form = baseForm.getForm(),
						WateDetailGrid = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_WateDetailGrid_Id'),
						selectRow = WateDetailGrid.getSelectionModel().getSelection(),
						situation='GOODS_BACK',
						serialNos = new Array(),
						arriveSheet  = form.getRecord(),
						samevoteoddIsGoodsShort=false;
					if (selectRow.length == 0){
						Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.selectOne'),'error',1000);//请选择一行！
						return;
					}
					//如果是无PDA签收
					//if(form.findField('isPdaSign').getValue()==false || form.findField('isPdaSign').getValue() =='N'){
						if(!form.findField('czmStatus').disabled){
							//如果是子母件,必须选择要操作的运单至少一条
							var serialGirdPanel = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
							if(serialGirdPanel){
								if(!serialGirdPanel.getSelectionModel().hasSelection()){//如果签收无选中订单信息
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'请至少勾选一条运单信息!','error',3000);
									return ;
								}
								//获得选中的子母件运单记录集合
								var selectCZMWaybill = serialGirdPanel.getSelectionModel().getSelection();
								if(selectCZMWaybill != null ){
									for(var i = 0 ;i< selectCZMWaybill.length ; i++){
										//限制签收，无流水号不能正常签收, 但可拉回
										if(Ext.isEmpty(selectCZMWaybill[i].get('serialNo')) && form.findField('czmStatus').getValue() != 'SIGN_STATUS_NO'){
											Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收运单流水号不能为空','error',3000);
											return;
										}
									}
								}
							}
						}
						//如果快递员不为空，设置是快递员派送为：Y，不是则为：N ; add by 231438
						if(Ext.isEmpty(form.findField('sendExpressEmpCode').getValue())){
							//form.findField('isCourierDelivery').setValue('N');
							arriveSheet.set('isCourierDelivery','N');
							arriveSheet.set('sendExpressEmpCode',null);
						}else{
							arriveSheet.set('isCourierDelivery','Y');
							arriveSheet.set('sendExpressEmpCode',form.findField('sendExpressEmpCode').getValue());
						}
						var paymentType = financialForm.findField('paymentType').getValue(),
							checkCount  = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection().length;
						//268377 添加    当到达联状态不可选的情况下不做判断
						if((!form.findField('signStatus').getValue() && !form.findField('signStatus').disabled) ||(!form.findField('czmStatus').getValue()  && !form.findField('czmStatus').disabled)){
							Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signStatusNotNull'),'error',3000);//签收状态不能为空，请选择状态情况！
							return;
							//268377 签收状态为未签收  判断拉回原因是否为空
						}else if((form.findField('signStatus').getValue() == sign.ptpExpDeliverHandler.SIGN_STATUS_NO && !form.findField('signStatus').disabled  ) || (form.findField('czmStatus').getValue() == sign.ptpExpDeliverHandler.SIGN_STATUS_NO && !form.findField('czmStatus').disabled )) {
							// 如果拉回原因可见时为空，系统弹窗提示！
							if(Ext.isEmpty(form.findField('pullbackReason').getValue())){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.pullbackReasonNotNull'),'error',3000);//拉回原因不能为空,请选择拉回原因信息!
								return;
							}else if(!form.findField('signTime').getValue()){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tsip'),ign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.notcompleteInfo'),'error',3000);
								return;
							}
						}else {
//验证数字不能小于0时的提示信息   
							if(form.findField('signGoodsQty').getValue()<=0){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signGoodsQtyGreateThanZero'),'error',1000);//
								return;
							}
							if(form.findField('situation').getValue()!='UNNORMAL_SAMEVOTEODD'){
								//签收件数与选择的流水号进行比较  
								if(form.findField('signGoodsQty').getValue() != checkCount && form.findField('czmStatus').disabled){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.deliverHandler.serialNoSignGoodsQtySame'),'error',3000);//选择流水号数量必须与签收件数一致，请确认！
									return;
								}
								//子母件一次性签收件数限制， 268377
								if(checkCount > sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT && !form.findField('czmStatus').disabled){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'子母件一次性签收件数不能超过'+sign.ptpExpDeliverHandler.CZM_SIGN_LIMIT+'件','error',3000);
									return;
								}
							}
							if(form.findField('situation').getValue()!='NORMAL_SIGN' && form.findField('situation').getValue()!='UNNORMAL_SAMEVOTEODD'){
								var flag=true;
								var arr=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection();
								for(var i=0;i<arr.length;i++){
									flag=arr[i].get('goodShorts');
									if(flag==true){
										break;
									}
								}
								if(flag==false||flag==null){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),"是否"+form.findField('situation').getRawValue()+"至少勾选一个!",'error',2500);
									return ;
								}
							}else if(form.findField('situation').getValue()=='UNNORMAL_SAMEVOTEODD'){
								var SignOutSerialNoWithTicketStore=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID').getStore(),
								//signSeriaNoNoteVal =Ext.getCmp('signSeriaNoNote_id').getValue(),
									multiNormalSelectSerNos=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_MultiExceptionNormalSerialNoGridPanel_ID').getSelectionModel().getSelection();
								if(SignOutSerialNoWithTicketStore.data.length<2){
									//若子母件签收状态是同票多类异常操作 268377
									if(!form.findField('czmStatus').disabled){
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'子母件不支持同票多类异常操作,请重新选择签收情况!','error',3000);
										form.findField('situation').setValue('NORMAL_SIGN');
										return ;
									}
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'请至少选择两种异常签收情况！','error',3000);
									return ;
								}
								for(var i=0;i<SignOutSerialNoWithTicketStore.data.length;i++){
									var serialSituation =SignOutSerialNoWithTicketStore.getAt(i).data.signState,
										serialNo=SignOutSerialNoWithTicketStore.getAt(i).data.serialNo
									if(Ext.isEmpty(serialSituation)){//如果签收情况为空
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收件里的签收情况不能为空！','error',3000);
										return ;
									}
									if(Ext.isEmpty(serialSituation)){//如果签收情况为空
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收件里的签收情况不能为空！','error',3000);
										return ;
									}
									if(serialSituation =='UNNORMAL_GOODSHORT'){
										samevoteoddIsGoodsShort=true;//同票多类异常里有选择异常－内物短少
									}
									if(Ext.isEmpty(serialNo)||serialNo.indexOf(',')==0){//如果签收流水号为空
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'签收件里的异常流水号不能为空！','error',3000);
										return ;
									}
									var choose = serialNo.split(',');
									for(var j=0;j<choose.length;j++){
										serialNos.push({
											'serialNo':choose[j],//流水号
											'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
											'situation':serialSituation//流水号签收情况
										});
									}
								}
								if(multiNormalSelectSerNos!=null){
									for (var i = 0; i < multiNormalSelectSerNos.length; i++) {//把前台选中的流水号放入数组里
										serialNos.push({
											'serialNo':multiNormalSelectSerNos[i].get("serialNo"),//流水号
											'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
											'situation':'NORMAL_SIGN'//流水号签收情况
										});
									}
								}
								if(form.findField('signGoodsQty').getValue()!=serialNos.length && form.findField('czmStatus').disabled){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.sign.serialNoSignGoodsQtySame'),'error',1000);//选择流水号数量必须与签收件数一致，请确认！
									return;
								}
							}
							// 付款方式为空，系统弹窗提示！
							if(!paymentType){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.paymentTypeNotNull'),'error',3000);//付款方式不能为空，请选择付款方式！
								return;
							}
							// 客户为空，系统弹窗提示！
							else if(!financialForm.findField('consigneeCode').getValue() && (paymentType=='CT' || paymentType=='DT')){
								//(月结),DT(临时欠款)
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.ctOrdtConsigneeNotNull'),'error',3000);//付款方式为临时欠款,月结时客户不能为空，请选择客户信息
								return;
							}//如果付款方式为支票,电汇,银行卡时,款项确认编号不能为空
							else if((!financialForm.findField('claimNo').getValue().trim())
								&& (paymentType=='NT' || paymentType=='TT' || paymentType=='CD')){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.ntOrttConsigneeNotNull'),'error',3000);//付款方式为支票,电汇时,款项确认编号不能为空！
								return;
							}else if(paymentType=='CD' &&　!/^\s*\d+\s*$/.test(financialForm.findField('claimNo').getValue())){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),'付款方式为银行卡时，款项确认编号必须输入数字。','error',3000);//付款方式为银行卡时，款项确认编号必须输入数字。
								return;
							}
							// 如果签收状态为部分签收，并且签收件数大于到达联件数，系统弹窗提示错误
							else if(form.findField('signStatus').getValue()== sign.ptpExpDeliverHandler.SIGN_STATUS_PARTIAL && form.findField('signGoodsQty').
									getValue()>= form.getRecord().get('arriveSheetGoodsQty') && !form.findField('signStatus').disabled){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.partSignSignGoodsQtyUnqualified'),'error',3000);
								return;
							}
							//268377 添加子母件签收件判断  如果子母件状态为部分签收，并且子母件签收件数大于子母件总件数
							else if(form.findField('czmStatus').getValue()==sign.ptpExpDeliverHandler.SIGN_STATUS_PARTIAL && form.findField('signGoodsQty').
									getValue()>form.findField('czmGoodsQty').getValue() && !form.findField('czmStatus').disabled){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.partSignSignGoodsQtyUnqualified'),'error',3000);
								return;
							}
							// 签收情况为空，系统弹窗提示！
							else if(!form.findField('situation').getValue()){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.situationNotNull'),'error',3000);//签收情况不能为空，请选择签收情况！
								return;
							}
							// 如果签收备注可见时为空，系统弹窗提示！
							else if(!form.findField('signNote').isValid()){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signNoteNotNull'),'error',3000);
								return;
							}
							// 签收时间为空，系统弹窗提示！
							else if(!form.findField('signTime').getValue()){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.signTimeNotNull'),'error',3000);
								return;
							}
							//签收人类型为空,系统弹窗提示！
							else if(!form.findField('deliverymanType').isValid()){
								Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.deliverymanTypeNotNull'),'error',3000);
								return;
							}
							else if(form.findField('situation').getValue()=='UNNORMAL_GOODSHORT'||samevoteoddIsGoodsShort==true){
								//校验短少量是否为空 如果为空 不让确认
								var alittleShort = form.findField('alittleShort').getValue();
								if(!Ext.isEmpty(alittleShort)){
									var totalCount = 0;
									for (var i=0; i<alittleShort.trim().length; i++) {
										var c = alittleShort.charCodeAt(i);
										if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
											totalCount++;
										}else {
											totalCount+=3;
										}
									}
									if(totalCount>50){
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n(	'pkp.sign.ptpExpDeliverHandler.alittleShortMaxLength'),'error',3000);
										return;
									}
								}else{
									if(!form.findField('alittleShort').isValid()){
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.alittleShortNotNull'),'error',3000);
										return;
									}
								}
								//校验外包装是否完好是否为空 如果为空 不让确认
								if(!form.findField('packingResult').isValid()){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.packingResultNotNull'),'error',3000);
									return;
								}
							}
						}
						Ext.Msg.confirm( sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.okTosubmit'), function(btn,text){
							if(btn == 'yes'){
								//update
								var signOutStorageForm=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
								form.updateRecord(arriveSheet);
								//268377 获取签收状态
								var signStatus= 'SIGN_STATUS_ALL';
								if(signOutStorageForm.findField('czmStatus').disabled){
									signStatus = signOutStorageForm.findField('signStatus').getValue();
								}else{
									if(signOutStorageForm.findField('czmStatus').getValue() == 'SIGN_STATUS_PARTIAL'){
										signStatus = 'SIGN_STATUS_ALL';
									}else{
										signStatus = signOutStorageForm.findField('czmStatus').getValue();
									}
								}

								var situation=form.findField('situation').getValue();
								arriveSheet.data.signStatus = signStatus;
								if(situation!='UNNORMAL_GOODSHORT'&& samevoteoddIsGoodsShort==false){
									arriveSheet.set('packingResult',null);
									arriveSheet.set('alittleShort',null);
								}
								//temp
								if(form.findField('deliverymanType').isVisible()&&!Ext.isEmpty(form.findField('deliverymanType').getValue())){
									var deliverymanType=form.findField('deliverymanType').getValue();
									if(!Ext.isEmpty(deliverymanType)){
										arriveSheet.set('deliverymanType',deliverymanType.toString());
									}
								}else{
									arriveSheet.set('deliverymanType',null);
								}
								arriveSheet.set('isPdaSign','N');
								if(form.findField('alittleShort').isVisible()&&!Ext.isEmpty(form.findField('alittleShort').getValue())){
									arriveSheet.set('alittleShort',form.findField('alittleShort').getValue());
								}else{
									arriveSheet.set('alittleShort',null);
								}
								if(form.findField('packingResult').isVisible()&&!Ext.isEmpty(form.findField('packingResult').getValue())){
									var packingResult=form.findField('packingResult').getValue().packingResult;
									arriveSheet.set('packingResult',packingResult.toString());
								}else{
									arriveSheet.set('packingResult',null);
								}
								//update
								var	financialDto = '';
								arriveSheet.data.signGoodsQty = form.findField('signGoodsQty').getValue();
								arriveSheet.set('signTime',Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true));
								var serialNorowObjs = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').
									getSelectionModel().getSelection();//得到选中的流水号信息
								if(form.findField('pullbackReason').isVisible(true)){//如果拉回原因可见
									arriveSheet.data.signNote = arriveSheet.data.pullbackReason;
								}
								if(signStatus==sign.ptpExpDeliverHandler.SIGN_STATUS_NO){
									var situationGoodsBack=signOutStorageForm.findField('situationGoodsBack').getValue();
									arriveSheet.data.situation=situationGoodsBack;
								}else{
									arriveSheet.data.situation=situation;//得到签收情况
								}
								var	financialInfo = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
								var financial = financialInfo.getRecord();
								financialInfo.updateRecord(financial);
								var consigneeName =financialInfo.findField('consigneeCode').getRawValue();
								//子母件 流水号签收情况
								if(!signOutStorageForm.findField('czmStatus').disabled){
									if(serialNorowObjs!=null){
										for (var i = 0; i < serialNorowObjs.length; i++) {//把前台选中的流水号放入数组里
											if(situation=='NORMAL_SIGN'){
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//
													'waybillNo':serialNorowObjs[i].get('waybillNo'),//268377 运单编号
													'situation':situation//流水号签收情况
												});
											}else{
												var situ = 'NORMAL_SIGN';
												if(serialNorowObjs[i].get("goodShorts")==true){
													situ=situation;
												}else{
													situ = 'NORMAL_SIGN';
												}
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
													'waybillNo':serialNorowObjs[i].get('waybillNo'),//268377  运单编号
													'situation':situ//流水号签收情况
												});
											}
										}
									}
								}


								if(arriveSheet.data.situation!='UNNORMAL_SAMEVOTEODD' && arriveSheet.data.situation!='GOODS_BACK' && signOutStorageForm.findField('czmStatus').disabled){
									if(serialNorowObjs!=null){
										for (var i = 0; i < serialNorowObjs.length; i++) {//把前台选中的流水号放入数组里
											if(situation=='NORMAL_SIGN'){
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//
													'waybillNo':serialNorowObjs[i].get('waybillNo'),//268377 运单编号
													'situation':situation//流水号签收情况
												});
											}else{
												var situ = 'NORMAL_SIGN';
												if(serialNorowObjs[i].get("goodShorts")==true){
													situ=situation;
												}else{
													situ = 'NORMAL_SIGN';
												}
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
													'waybillNo':serialNorowObjs[i].get('waybillNo'),//268377  运单编号
													'situation':situ//流水号签收情况
												});
											}
										}
									}
								}
								financialDto = {
									'productType' : financial.data.productCode,//运输性质
									'isWholeVehicle' : financial.data.isWholeVehicle,//是否整车运单
									'paymentType' : financial.data.paymentType, //付款方式
									'receiveableAmount' :financial.data.receiveableAmount, //应收代收款
									'receiveablePayAmoout' : financial.data.receiveablePayAmoout, //应收到付款
									'pocketShipping' : financial.data.pocketShipping,//实付运费
									'consigneeCode':financial.data.consigneeCode,//客户编码
									'claimNo' :financial.data.claimNo,//款项确认编号
									'consigneeName':consigneeName,//客户名称
									'deliverbillNo':sign.ptpExpDeliverHandler.deliverbillNo,//派送单号
									'orderNo':financial.data.orderNo//订单号
								};
								Ext.Ajax.request({
									url:sign.realPath('addNoPdaSignForPtpExpDeliver.action'),
									method: 'POST',
									timeout: 300000,
									jsonData: {
										'deliverbillDetailVo':{
											'financialDto':financialDto, //财务信息
											'signDetailEntitys':serialNos //流水号信息集合
										},'arriveSheetVo': {
											'arriveSheet': arriveSheet.data //签收信息
										},'ptpExpDeliverHandlerVo':{
											'waybillNo': arriveSheet.data.waybillNo, //运单号
											'paymentType': financial.data.paymentType, //付款方式
											'consigneeCode': financial.data.consigneeCode, //客户代码
											'consigneeName': consigneeName,//客户名称
											'toPayFee': financial.data.pocketShipping, //实收到付运费
											'codAmount':  financial.data.codAmount  //代收货款 
										}
									},
									success: function(response){
										var json = Ext.decode(response.responseText);
										Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);//签收确认按钮不可编辑
										sign.ptpExpDeliverHandler.setFormDisabled(baseForm,true); //签收信息所有控件不可编辑
										sign.ptpExpDeliverHandler.isReadOnly(financialInfo,true);
										var serialGrid =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
										var selectIndexs = [];
										Ext.Array.each(serialGrid.getSelectionModel().getSelection(), function(record, index, countriesItSelf) {
											var index = WateDetailGrid.getStore().find("waybillNo", record.get('waybillNo'));
											if (index != -1) {
												selectIndexs.push(index);
											}

										});
										serialGrid.show();
										Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID').hide();
										serialGrid.getSelectionModel().deselectAll();
										serialGrid.getStore().removeAll();

										if (arriveSheet.data.situation == 'NORMAL_SIGN') {
											if (selectIndexs.length > 0) {
												Ext.Array.each(selectIndexs, function(selectIndex, index, countriesItSelf) {
													WateDetailGrid.getView().addRowCls(selectIndex, 'passProcess_mark_color');//变成绿色
												});
											} else {
												var selectIndex = WateDetailGrid.getSelectionModel().lastSelected.index;
												WateDetailGrid.getView().addRowCls(selectIndex, 'passProcess_mark_color');//变成绿色
											}
										}else{
											if (selectIndexs.length > 0) {
												Ext.Array.each(selectIndexs, function(selectIndex, index, countriesItSelf) {
													WateDetailGrid.getView().addRowCls(selectIndex, 'failProcess_mark_color');//变成红色
												});
											} else {
												var selectIndex = WateDetailGrid.getSelectionModel().lastSelected.index;
												WateDetailGrid.getView().addRowCls(selectIndex, 'failProcess_mark_color');//变成红色
											}
										}
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),json.message,'ok',2000);//操作成功！
									},exception: function(response){//异常处理
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), json.message, 'error', 3000);
									}
								});
							}
						});

					/*}//如果是有pda签收
					else if(form.findField('isPdaSign').getValue()==true || form.findField('isPdaSign').getValue() =='Y'){
						//如果快递员不为空，设置是快递员派送为：Y，不是则为：N ; add by 231438
						if(Ext.isEmpty(form.findField('sendExpressEmpCode').getValue())){
							//form.findField('isCourierDelivery').setValue('N');
							arriveSheet.set('isCourierDelivery','N');
							arriveSheet.set('sendExpressEmpCode',null);
						}else{
							arriveSheet.set('isCourierDelivery','Y');
							arriveSheet.set('sendExpressEmpCode',form.findField('sendExpressEmpCode').getValue());
						}
						
						//财务信息form
						var	financialInfo = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
						var financial = financialInfo.getRecord();
						financialInfo.updateRecord(financial);
						var consigneeName = financialInfo.findField('consigneeCode').getRawValue();
						financialDto = {
							'productType' : financial.data.productCode,//运输性质
							'isWholeVehicle' : financial.data.isWholeVehicle,//是否整车运单
							'paymentType' : financial.data.paymentType, //付款方式
							'receiveableAmount' :financial.data.receiveableAmount, //应收代收款
							'receiveablePayAmoout' : financial.data.receiveablePayAmoout, //应收到付款
							'pocketShipping' : financial.data.pocketShipping,//实付运费
							'consigneeCode':financial.data.consigneeCode,//客户编码
							'claimNo' :financial.data.claimNo,//款项确认编号
							'consigneeName':consigneeName,//客户名称
							'deliverbillNo':sign.ptpExpDeliverHandler.deliverbillNo,//派送单号
							'orderNo':financial.data.orderNo//订单号
						};
						
						Ext.Msg.confirm( sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.okTosubmit'), function(btn,text){
							if(btn == 'yes'){
								var arriveSheet1  = form.getRecord();
								form.updateRecord(arriveSheet1);
								Ext.Ajax.request({
									url:sign.realPath('addPdaSignForPtpExpDeliver.action'),
									method: 'POST',
									jsonData: {
										'arriveSheetVo': {
											'arriveSheet': arriveSheet1.data //签收信息
										},'deliverbillDetailVo':{
											'financialDto':financialDto //财务信息
										}
									},
									success: function(response){
										var json = Ext.decode(response.responseText);
										Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);//签收确认按钮不可编辑
										sign.ptpExpDeliverHandler.setFormDisabled(baseForm,true); //签收信息所有控件不可编辑
										sign.ptpExpDeliverHandler.isReadOnly(financialForm,true);
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.entrySignManOk'),'ok',2000);//录入签收人成功!
									},exception: function(response){//异常处理
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), json.message, 'error', 3000);
									}
								});
							}
						});
					}*/
				}
			});
		}
		return  this.checkButtons ;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.buttons = [me.getCheckButtons()];
		me.callParent([cfg]);
	}, listeners : {
		beforerender : function(_this, eOpts){
			var	form = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id').getForm();
			form.findField('pullbackReason').setVisible(false);//拉回原因不可见
			form.findField('situation').setVisible(false);//签收情况不可见
			form.findField('packingResult').setVisible(false);//外包装是否完好不可见
			form.findField('alittleShort').setVisible(false);//短少量不可见

			Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].hide(); //268377   快递代理加载效果      隐藏 是否内货短少项
			//动态改变样式
			//form.findField('signStatus').labelCls('margin-top:40px');
			form.findField('signStatus').addCls('margin-top:40px');
		}
	}
});

//下面大panel 包括待处理,财物信息,签收信息
Ext.define('Foss.sign.ptpExpDeliverHandler.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	financialInfoForm : null,//财务信息
	getFinancialInfoForm : function(){
		if(this.financialInfoForm==null){
			this.financialInfoForm = Ext.create('Foss.sign.ptpExpDeliverHandler.FinancialInfoForm');
		}
		return this.financialInfoForm;
	},
	wateDetailGrid : null,//待处理模块
	getWateDetailGrid :function(){
		var me = this;
		if(this.wateDetailGrid==null){
			this.wateDetailGrid = Ext.create('Foss.sign.ptpExpDeliverHandler.WateDetailGrid',{
				id: 'Foss_sign_ptpExpDeliverHandler_WateDetailGrid_Id',
				columnWidth:.24,
				listeners: {
					//待处理----运单号的点击事件
					itemclick:  function(dv, record, item, index, e){
						var view = this.getView();
						Ext.Ajax.request({
							url:sign.realPath('queryFinanceSignExp.action'),
							method:'POST',
							params: {//运单号
								'deliverbillDetailVo.deliverbillDetailDto.waybillNo': record.get('waybillNo'),
								'deliverbillDetailVo.deliverbillDetailDto.deliverbillNo': sign.ptpExpDeliverHandler.deliverbillNo,
								'deliverbillDetailVo.deliverbillDetailDto.arrivesheetNo': record.get('arrivesheetNo')
							},
							success: function(response){
								var result = Ext.decode(response.responseText),
									form = me.getSignInfoForm(),
									baseForm = form.getForm(),
									financialInfoForm = me.getFinancialInfoForm(),
									baseFinancialInfoForm = financialInfoForm.getForm(),
									serialNosGrid = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
								sign.ptpExpDeliverHandler.formFieldReset(baseFinancialInfoForm,true);
								serialNosGrid.getSelectionModel().deselectAll();
								serialNosGrid.getStore().removeAll();//清除所有流水号信息
								var UnNormalSelectAll = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
								UnNormalSelectAll.setVisible(false);
								//如果到达联的信息为空
								if(result.arriveSheetVo.arriveSheet == null){
									Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.arriveSheetInvalid'), 'error', 2000);
									baseForm .reset();
									baseFinancialInfoForm.reset();
									Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);//签收确认按钮不可编辑
									return;
								}
								//得到财务信息
								var financialModel = Ext.ModelManager.create(result.deliverbillDetailVo.financialDto,
									'Foss.sign.ptpExpDeliverHandler.model.Financial');
								//得到签收信息
								var signlModel = Ext.ModelManager.create(result.arriveSheetVo.arriveSheet,
									'Foss.sign.ptpExpDeliverHandler.model.SignInfoModel');
								//加载财务信息
								financialInfoForm.loadRecord(financialModel);
								var _financialInfoForm = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
								if(financialModel.data.existOldWaybillNo==false){
									_financialInfoForm.findField('oldReceiveablePayAmoout').setVisible(false);
									_financialInfoForm.findField('totalMoney').setVisible(false);
								}
								//** 添加大客户图标
								if(financialModel.data.receiveBigCustomer == 'Y'){
									baseFinancialInfoForm.findField('receiveCustomerName').
										setFieldLabel('&nbsp;&nbsp;'+sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveCustomerName')+'<span  class="big_Customer_pic_common">');
								}else{
									baseFinancialInfoForm.findField('receiveCustomerName').setFieldLabel('&nbsp;&nbsp;'+sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.receiveCustomerName'));
								}
								//****
								//得到付款方式
								sign.ptpExpDeliverHandler.paymentType = result.deliverbillDetailVo.financialDto.paymentType;
								var	arriveSheet = result.arriveSheetVo.arriveSheet,//到达联信息
									isPdaSign = arriveSheet.isPdaSign,//得到是否PDA签收字段
									status = arriveSheet.status,//得到到达联状态
									situation = arriveSheet.situation;//签收情况
								//如果到达联状态为”签收“ 或”拒收“
								Ext.getCmp('Foss.sign.ptpExpDeliverHandler.withTicketFormPanel_ID').hide();
								Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').show();
								if(status=='SIGN' || status=='REFUSE'){
									if (situation == 'NORMAL_SIGN') {
										view.addRowCls(index, 'passProcess_mark_color');//变成绿色
									}else{
										view.addRowCls(index, 'failProcess_mark_color');//变成红色
									}
									form.loadRecord(signlModel);
									baseForm.findField('signTime').setValue(Ext.Date.format(signlModel.get('signTime'),'Y-m-d H:i:s'));
									sign.ptpExpDeliverHandler.isReadOnly(baseFinancialInfoForm,true);
									var isVisible =false;
									if((!Ext.isEmpty(situation)) && situation == 'GOODS_BACK'){//如果签收情况为货物拉回
										baseForm.findField('pullbackReason').setValue(arriveSheet.signNote);//得到货物拉回原因
										baseForm.findField('situationGoodsBack').setValue(situation);
									} else{
										isVisible=true;
										baseForm.findField('signNote').setValue(arriveSheet.signNote);//得到签收备注
										if((!Ext.isEmpty(situation)) && situation == 'PARTIAL_SIGN'){//如果签收情况为部分签收
											baseForm.findField('situation').setRawValue('部分签收');//签收情况
										}else{
											//签收情况默认选中“正常签收”
											baseForm.findField('situation').setValue(situation);
										}
									}
									baseForm.findField('situationGoodsBack').setVisible(!isVisible);//货物拉回
									baseForm.findField('pullbackReason').setVisible(!isVisible);//拉回原因
									baseForm.findField('situation').setVisible(isVisible);//签收情况
									baseForm.findField('signNote').setVisible(isVisible);//签收备注
									baseForm.findField('packingResult').setVisible(false);
									baseForm.findField('alittleShort').setVisible(false);
									//baseForm.findField('sendExpressEmpCode').setVisible(isVisible);//派件快递员

									sign.ptpExpDeliverHandler.setFormDisabled(form, true);//所有控件不可编辑
									Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);//签收确认按钮不可编辑
									if(situation=='UNNORMAL_GOODSHORT'||(situation=='UNNORMAL_SAMEVOTEODD' &&!Ext.isEmpty(baseForm.findField('alittleShort').getValue()))){
										baseForm.findField('packingResult').setVisible(true);
										baseForm.findField('alittleShort').setVisible(true);
										baseForm.findField('alittleShort').setValue(baseForm.findField('alittleShort').getValue());
										baseForm.findField('packingResult').setValue(baseForm.findField('packingResult').getValue());
										baseForm.findField('packingResult').items.items[0].setDisabled(false);
										baseForm.findField('packingResult').items.items[1].setDisabled(false);
										if(baseForm.findField('packingResult').getValue()=='Y'){
											baseForm.findField('packingResult').items.items[0].value=true;
											baseForm.findField('packingResult').setValue({
												packingResult:'Y'
											});
										}else{
											baseForm.findField('packingResult').setValue({
												packingResult:'N'
											});
										}
										baseForm.findField('packingResult').setDisabled(true);
										baseForm.findField('alittleShort').setDisabled(true);
									}
								}else {
									sign.ptpExpDeliverHandler.COUNT = 0;
									//记录一下全局的运单号
									sign.ptpExpDeliverHandler.SIGN_RECORD_WAYBILLNO_INDEX=arriveSheet.waybillNo;
									form.loadRecord(signlModel);
									sign.ptpExpDeliverHandler.setFormDisabled(form, true);//所有控件不可编辑
									Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);//签收确认按钮不可编辑
									if(isPdaSign == "Y"){
										Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'), sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.arriveSheetStateUnqualified'), 'error', 2000);//该运单是pda签收,但到达联状态却不为签收或拒收！
										return;
									}else if(isPdaSign == "N"){
										baseForm.findField('pullbackReason').setVisible(false);//拉回原因不可见
										baseForm.findField('situationGoodsBack').setVisible(false);//货物拉回不可见
										baseForm.findField('situation').setVisible(true);//签收情况可见
										baseForm.findField('packingResult').setVisible(false);
										baseForm.findField('alittleShort').setVisible(false);

										var waybillNoczm = record.get('waybillNo');
										//判断运单是否子母件 268377
										if(result.deliverbillDetailVo.twoInOneWaybillDto != null && result.deliverbillDetailVo.twoInOneWaybillDto.isTwoInOne == 'Y'){
											Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[1].show();  //268377 显示签收件模块中运单编号列
											//子母件签收状态可编辑
											baseForm.findField('czmStatus').setDisabled(false);
											//子母件签收状态默认选中"全部签收"
											baseForm.findField('czmStatus').setValue(sign.ptpExpDeliverHandler.SIGN_STATUS_ALL);
											//子母件总件数显示
											baseForm.findField('czmGoodsQty').setDisabled(false);
											//子母件到达总件数
											baseForm.findField('czmGoodsQty').setValue(result.deliverbillDetailVo.signDetailEntitys.length);

										}else{
											Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID').columns[1].hide(); //非子母件隐藏签收件模块运单编号
											//到达联签收状态
											baseForm.findField('signStatus').setDisabled(false);
											//到达联签收状态默认选中"全部签收"
											baseForm.findField('signStatus').setValue(sign.ptpExpDeliverHandler.SIGN_STATUS_ALL);
										}


										//加载签收件信息到GridPanel
										serialNosGrid.getStore().loadData(result.deliverbillDetailVo.signDetailEntitys);
										sign.ptpExpDeliverHandler.isReadOnly(baseFinancialInfoForm,false);
										sign.ptpExpDeliverHandler.SIGN_SERIAL_NO_ARR = serialNosGrid.getStore().collect('serialNo');
										if(!baseForm.findField('czmStatus').disabled){
											//子母件默认默认全选流水号
											var czmPanel =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID');
											czmPanel.getSelectionModel().selectAll();
										}
										//签收时间可编辑
										baseForm.findField('signTime').setDisabled(false);
										//签收情况可编辑
										baseForm.findField('situation').setDisabled(false);
										baseForm.findField('signNote').setVisible(true);//签收备注可见
										//签收备注可编辑
										baseForm.findField('signNote').setDisabled(false);
										//签收情况默认选中“正常签收”
										baseForm.findField('situation').setValue('NORMAL_SIGN');
										//正常签收
										//签收人类型
										baseForm.findField('deliverymanType').setValue('');
										baseForm.findField('deliverymanType').setDisabled(false);
										baseForm.findField('deliverymanType').setVisible(true);//签收人类型可见
										//baseForm.findField('sendExpressEmpCode').setDisabled(false);;//派件快递员可编辑
										var financialForm=Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm();
										var receiveCustomerContact=financialForm.findField('receiveCustomerContact').getValue();
										if(!Ext.isEmpty(receiveCustomerContact)){
											//签收人为收货人本人
											sign.ptpExpDeliverHandler.PKP_SIGN_PERSON_NAME=signlModel.data.deliverymanName;
											if(receiveCustomerContact==signlModel.data.deliverymanName){
												baseForm.findField('deliverymanType').setValue('SIGN_PERSON_ME');
											}
										}
										Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_confirmButton_Id').setDisabled(false);//签收确认按钮可编辑
										if(signlModel.get('signTime')!= null){
											//签收时间默认为当前系统时间
											baseForm.findField('signTime').setValue(Ext.Date.format(signlModel.get('signTime'),'Y-m-d H:i:s'));
											baseForm.findField('signTime').setVisible(true);
										}else {
											//签收时间默认为当前时间
											baseForm.findField('signTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
											baseForm.findField('signTime').setVisible(true);
										}
										//签收件数=到达联件数
										baseForm.findField('signGoodsQty').setValue(baseForm.findField('arriveSheetGoodsQty').getValue());

										if(baseFinancialInfoForm.findField('paymentType').getValue() == sign.ptpExpDeliverHandler.paymentTypeCH){//现金
											//款项确认编号不可编辑
											baseFinancialInfoForm.findField('claimNo').setReadOnly(true);
										}
										//签收件的条件“从”可编辑
										baseForm.findField('startSerialNo').setDisabled(false);
										baseForm.findField('startSerialNo').setValue('');
										baseForm.findField('endSerialNo').setValue('');
										//签收件的条件“到”可编辑可编辑
										baseForm.findField('endSerialNo').setDisabled(false);
										Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID').setDisabled(false);
										baseForm.findField('arrivesheetNo').setDisabled(false);
										baseForm.findField('arriveSheetGoodsQty').setDisabled(false);
										baseForm.findField('isPdaSign').setDisabled(false);
										baseForm.findField('deliverymanType').allowBlank = false;
									}
								}
							}
						});
					}
				}
			});
		}
		return this.wateDetailGrid;
	},
	signInfoForm : null,//签收信息
	getSignInfoForm : function(){
		if(this.signInfoForm==null){
			this.signInfoForm = Ext.create('Foss.sign.ptpExpDeliverHandler.SignInfoForm');
		}
		return this.signInfoForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getWateDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.76,
			items : [
				me.getFinancialInfoForm(),me.getSignInfoForm()
			]
		}];
		me.callParent([cfg]);
	}
});

//查询条件
Ext.define('Foss.sign.ptpExpDeliverHandler.QueryPanel', {
	extend:'Ext.form.Panel',
	id:'Foss_sign_ptpExpDeliverHandler_QueryPanel_Id',
	// 指定容器的标题
	title: sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.queryBuilder'),//查询条件
	frame:true,
	//收缩
	collapsible: true,
	//动画收缩
	animCollapse: true,
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	//默认边距 间隔
	defaults: {
		margin: '5 10 5 10',  //四边距  上右下左
		labelWidth: 95
	},
	layout: 'column',
	defaultType: 'textfield',
	// 定义容器中的项
	items: [{
		name: 'deliverbillNo',
		fieldLabel:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.deliverbillNo'),//派送单号
		align:'center',
		allowBlank:false,
		columnWidth:.33
	},{
		xtype:'button',
		margin: '5 10 5 30',  //四边距  上右下左
		text:sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.search'),//查询
		disabled:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexquerybutton'),
		hidden:!sign.ptpExpDeliverHandler.isPermission('deliverhandlerindex/ptpExpDeliverHandlerIndexquerybutton'),
		cls:'yellow_button',
		columnWidth:.11,
		handler:function(){
			var queryForm = this.up('form').getForm().getValues(),
				signForm = Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SignInfoForm_Id'),
				FinancialInfoForm =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_FinancialInfoForm_Id').getForm(),
				WateDetailGrid =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_WateDetailGrid_Id'),
				SerialNoOutStorageGridPanel =Ext.getCmp('Foss_sign_ptpExpDeliverHandler_SerialNoOutStorageGridPanel_ID'),
				signBaseForm = signForm.getForm();
			//签收信息重置
			signBaseForm.reset();
			sign.ptpExpDeliverHandler.setFormDisabled(signForm,true);
			signBaseForm.findField('pullbackReason').setVisible(false);//拉回原因不可见
			signBaseForm.findField('signNote').setVisible(true);//签收备注可见
			//财务信息重置
			FinancialInfoForm.reset();
			sign.ptpExpDeliverHandler.isReadOnly(FinancialInfoForm,true);
			WateDetailGrid.getStore().removeAll();
			SerialNoOutStorageGridPanel.getSelectionModel().deselectAll();
			//清除签收件模块下的信息
			SerialNoOutStorageGridPanel.getStore().removeAll();//清除所有流水号信息
			if(!this.up('form').getForm().isValid()){
				Ext.ux.Toast.msg(sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.tip'),sign.ptpExpDeliverHandler.i18n('pkp.sign.expDeliverHandler.queryNotNull'),'error',1000);//查询条件不能为空！
				return;
			}
			//得到派送单编号
			sign.ptpExpDeliverHandler.deliverbillNo = queryForm.deliverbillNo;
			//加载待处理模块下的信息
			WateDetailGrid.getStore().load();
		}
	}]
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Foss.sign.ptpExpDeliverHandler.QueryPanel');
	var bigDownPanel = Ext.create('Foss.sign.ptpExpDeliverHandler.BigDownPanel');
	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-ptpExpDeliverHandlerIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto',
		getQueryPanel: function(){
			return queryPanel;
		},
		items: [queryPanel,bigDownPanel,{

		}],
		renderTo: 'T_sign-ptpExpDeliverHandlerIndex-body'
	});
});