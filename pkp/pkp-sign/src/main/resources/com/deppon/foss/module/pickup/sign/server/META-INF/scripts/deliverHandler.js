/**
 * @author foss-meiying page:派送处理
 */
// 付款方式词条
(function(){
	sign.deliverHandler.PaymentStore =FossDataDictionary.getDataDictionaryStore('REPAYMENT_PAYMENT_TYPE', null);
	sign.deliverHandler.PaymentStore.removeAt(sign.deliverHandler.PaymentStore.find('valueCode','OL'));
})();
// 签收情况
(function(){
	sign.deliverHandler.situationStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION', null);
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','UNNORMAL_SIGN'));
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));// 移除异常-丢货
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));// 移除异常-违禁品
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));// 移除异常-弃货
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','PARTIAL_SIGN'));// 移除部分签收
	sign.deliverHandler.situationStore.removeAt(sign.deliverHandler.situationStore.find('valueCode','GOODS_BACK'));// 移除货物拉回
	sign.deliverHandler.situationGoodBackStore =FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION', null,null,'GOODS_BACK');
	sign.deliverHandler.exsituationStore=FossDataDictionary.getDataDictionaryStore('PKP_SIGN_SITUATION',
			null,null,['UNNORMAL_BREAK', 'UNNORMAL_DAMP', 'UNNORMAL_POLLUTION','UNNORMAL_GOODSHORT','UNNORMAL_ELSE']);
})();

//过滤拉回原因词条 根据valuecade过滤拉回原因词条306566  DN201601290015
(function(){
	sign.deliverHandler.pullbackReasons =FossDataDictionary.getDataDictionaryStore('PKP_PULLBACK_REASON', null);
    sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','UNLOAD'));//20
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CUSTOM_NOTIN'));//45       
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','LINE_WRONG'));//50
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CAR_FORBID'));//25
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','DRIVER_LATE'));//10
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','GOODS_BREAK'));//60
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','BILL_QUESTION'));//30
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CAR_BREAK'));//35
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','PHONE_WRONG'));//75
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','HOLIDAY_DELAY'));//66
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','NOT_IN_UNIT_DELIVER'));//68
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CUSTOMER_NOT_CONTACT'));//100
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','BRANCH_ALLOT_WRONG'));//81
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','SUM_NOT_FULL'));//82
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','ARRIVE_LATE'));//83
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','POSTAGE_PAY_REFUSE'));//84
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','LACK_REJECT'));//86
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','WEIGHT_PRICE_WRONG_REFUSE_PAY'));  //87
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','BEYOND DELIVER'));  //88
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CUSTOME _QUIT'));  //89
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','REBACK_ORIGINAL'));  //85
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CUSTOM_VACATION'));  //90
	sign.deliverHandler.pullbackReasons.removeAt(sign.deliverHandler.pullbackReasons.find('valueCode','CUSTOM_CHANGE_DATE'));  //91

})();
sign.deliverHandler.SIGN_STATUS_PARTIAL= 'SIGN_STATUS_PARTIAL';// 签收状态为部分签收
sign.deliverHandler.SIGN_STATUS_ALL= 'SIGN_STATUS_ALL';// 签收状态为全部签收
sign.deliverHandler.SIGN_STATUS_NO= 'SIGN_STATUS_NO';// 签收状态为未签收
sign.deliverHandler.SIGN_SERIAL_NO_ARR =null;
sign.deliverHandler.SIGN_RECORD_SERINALNO_INDEX=null;
sign.deliverHandler.SIGN_RECORD_WAYBILLNO_INDEX=null;
sign.deliverHandler.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';// 签收情况词条
sign.deliverHandler.PKP_PULLBACK_REASON = 'PKP_PULLBACK_REASON';// 拉回原因词条
sign.deliverHandler.PKP_PULLBACK_REASON_DRIVER_LATE = 'OTHER_TIME_DELIVERY';// 拉回原因-客户要求其他时间送
sign.deliverHandler.OTHER = 'OTHER';// 拉回原因-客户要求其他时间送
sign.deliverHandler.COUNT=null;
// 让form里的所有控件都不可用
sign.deliverHandler.setFormDisabled = function(form,editable){
	var fields = form.getForm().getFields();
	fields.each(function(field){
		field.setDisabled(editable);
	});
}
// 让form里的控件可见 或者不可见
sign.deliverHandler.setPartFormVisible = function(form,visible){
		if(form.findField('packingResult').isVisible()==!visible){
			form.findField('packingResult').setVisible(visible);
		}
		if(form.findField('alittleShort').isVisible()==!visible){
			form.findField('alittleShort').setVisible(visible);
			form.findField('alittleShort').setValue('');
		}
}
sign.deliverHandler.editor = function(th,e){
	var newValue = e.value;// 获取本次付款金额修改后值
	var oldValue = e.originalValue;
	if(newValue == oldValue){
		return false;
	}
	e.record.set('signState',oldValue);
	var arr = th.grid.getStore().collect('signState'), 
	flag = arr.indexOf(newValue),
	form = Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
	if (flag >=0) {
		// 提交记录
 		e.record.commit();
		Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),
				'签收情况已被选择', 'error', 3000);
		return false;
	}
	e.record.set('signState',newValue);
	e.record.commit();
	var newArr =th.grid.getStore().collect('signState'),
	 isre= newArr.indexOf('UNNORMAL_GOODSHORT');
	if (isre>=0) {
		sign.deliverHandler.setPartFormVisible(form, true);
	}else{
		sign.deliverHandler.setPartFormVisible(form, false);
	}
	sign.deliverHandler.resetSignNote(newValue,oldValue);
}

sign.deliverHandler.resetSignNote = function() {
	var SignOutStorageFormPanel = Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm(),

	situation = SignOutStorageFormPanel.findField('situation').getValue(), 
	situationName = SignOutStorageFormPanel
			.findField('situation').getRawValue(), 
	alittleShort = SignOutStorageFormPanel
			.findField('alittleShort').getValue(), rstValue;
	if (situation == 'UNNORMAL_GOODSHORT') {
		if (!Ext.isEmpty(alittleShort)) {
			rstValue = situationName
					+ ','
					+ sign.deliverHandler.i18n('pkp.sign.sign.littleShort')
					+ ':'
					+ SignOutStorageFormPanel.findField('alittleShort')
							.getValue();
		} else {
			rstValue = situationName;
		}
	} else {
		var SignOutSerialNoWithTicket = Ext
				.getCmp('Foss_sign_deliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID'),
				signStates = SignOutSerialNoWithTicket.getStore().collect('signState'), 
				partSituation = '';
		if (signStates.length > 0) {
			for(var i = 0 ;i<signStates.length;i++){
				var situationN =FossDataDictionary.rendererSubmitToDisplay(signStates[i], sign.deliverHandler.PKP_SIGN_SITUATION)+',';  
				partSituation+=situationN;
			}	
				if(!Ext.isEmpty(alittleShort)){
					rstValue=situationName+','+partSituation+sign.deliverHandler.i18n('pkp.sign.sign.littleShort')+':'+SignOutStorageFormPanel.findField('alittleShort').getValue();
				}else{
					rstValue=situationName+','+partSituation.substring(0,partSituation.length-1);
				}
		} else {
			if (!Ext.isEmpty(alittleShort)) {
				rstValue = situationName
						+ ','
						+ sign.deliverHandler.i18n('pkp.sign.sign.littleShort')
						+ ':'
						+ SignOutStorageFormPanel.findField('alittleShort')
								.getValue();
			} else {
				rstValue = situationName;
			}
		}
	}
	SignOutStorageFormPanel.findField('signNote').setValue(rstValue);

}
sign.deliverHandler.paymentTypeCH='CH';// 付款方式为现金
sign.deliverHandler.deliverStatus='SIGNINFO_CONFIRMED';// 派送单状态为‘已签收确认’
sign.deliverHandler.formFieldReset = function(form,isTrue){
	var claimNo = form.findField('claimNo'), paymentType = form.findField('paymentType'),
	consigneeCode = form.findField('consigneeCode');
	if(isTrue){
		claimNo.reset();
		consigneeCode.reset();
		paymentType.reset();
	}
};
// 根据传入的true,false 让财务信息里的付款方式，客户，款项确认编码是否只读
sign.deliverHandler.isReadOnly = function(form,isTrue){
	var claimNo = form.findField('claimNo'), paymentType = form.findField('paymentType'),
		consigneeCode = form.findField('consigneeCode');
	// 款项确认编号
	claimNo.setReadOnly(isTrue);
	// 付款方式
	paymentType.allowBlank = isTrue;
	paymentType.setReadOnly(isTrue);
	// 客户编码可编辑
	consigneeCode.setReadOnly(isTrue);
}
// 派送单号
sign.deliverHandler.deliverbillNo=null;
// 付款方式
sign.deliverHandler.paymentType;
// 财务信息
Ext.define('Foss.sign.deliverHandler.model.Financial', {
	extend : 'Ext.data.Model',
	fields : [
		{name : 'pocketShipping',type:'float',defaultValue: 0},/* 实付运费 */
		{name : 'codAmount',type:'float',defaultValue: 0},/* 代收货款 */
		{name : 'toPayAmount',type:'float',defaultValue: 0},/* 收款总额 */
		{name : 'receiveableAmount',type:'float',defaultValue: 0},/* 应收代收款 */
		{name : 'receivedAmount',type:'float',defaultValue: 0},/* 已收代收款 */
		{name : 'receiveablePayAmoout',type:'float',defaultValue: 0},/* 应收到付款 */
		{name : 'receivedPayAmount',type:'float',defaultValue: 0},/* 已收到付款 */
		{name : 'paymentType'},/* 付款方式 */
		{name : 'receiveCustomerName'},/* 收货人(收货客户名称) */
		{name : 'receiveCustomerContact'},/* 收货客户联系人 */
		{name : 'isWholeVehicle'},/* 是否整车运单 */
		{name : 'productCode'},/* 运输性质 */
		{name: 'claimNo'},/* 款项确认编号 */
		{name: 'consigneeName'},/* 客户名称 */
		{name: 'consigneeCode'},/* 客户编码 */
		{name:'receiveBigCustomer'},/* 收货人是否大客户表示 */
		{name: 'orderNo'}/* 订单号 */
	]
});
// 签收信息--签收件 流水号Model
Ext.define('Foss.sign.deliverHandler.SerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'goodShorts'}// 是否内货短少
	]
});
// 异常流水号选择的model
Ext.define('Foss.sign.deliverHandler.SerialNoWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'waybillNo',type: 'string'},// 运单号
		{name:'isSelect',type:'boolean'}
	]
});
// 签收出库同票多类grid对应model
Ext.define('Foss.sign.deliverHandler.SignOutWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'signState'},
		{name: 'choose'},
		{name: 'serialNo'}
	]
});


// 签收信息
Ext.define('Foss.sign.deliverHandler.model.SignInfoModel', {  
	extend : 'Ext.data.Model',
	fields : [
	    {name : 'id'},/* id */
		{name : 'situation'},/* 签收情况 */
		{name : 'signNote'},/* 签收备注 */
		{name : 'pullbackReason'},/* 拉回原因 */
		{name: 'identifyCode', type: 'string'},// 证件号码
		{name: 'identifyType', type: 'string'},// 证件类型
		{name : 'isPdaSign'},/* 是否PDA签到 */
		{name : 'arrivesheetNo'},/* 到达联编号 */
		{name: 'waybillNo',  type: 'string'},// 运单编号
		{name : 'arriveSheetGoodsQty',type:'int'},/* 到达联总件数 */
		{name : 'signGoodsQty',type:'int'},/* 签收件数 */
		{name : 'signTime', type: 'date',
			convert:function(value){
				 if(value!=null){
					 var date = new Date(value);
					 return date;
				 }else{
					 return null;
				 }
			}
		},/* 签收时间 */
		{name : 'deliverymanName',
			convert: function(value) {
				if (value != null && value != 'N/A') {
					return value;
				} else{
					return null;
				}
			}
		},/* 签收人 */
		{name : 'status'},/* 到达联状态 */
		{name : 'signStatus'},/* 到达联签收状态 */
		{name: 'packingResult'},// 外包装是否完好
		{name: 'alittleShort'},// 短少量
		{name: 'createType'},// 派送单状态
		{name : 'nextDeliverTime', type: 'date'},//再次送货时间
		{name : 'nextDeliverTime',
			convert: function(value) {
				if (value != null && value != 'N/A') {
					return value;
				} else{
					return null;
				}
			}
		}, // 送货时间 
		{name: 'pullbackNote',  type: 'string'}// 拉回原因备注  306566  DN201601290015
	]
});
// 签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.deliverHandler.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.deliverHandler.SerialNoSignOutStorageModel',
	// 是否自动查询
	autoLoad: false,
	proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'deliverbillDetailVo.signDetailEntitys'
        }
	}
	
});
// 待处理表格Store
Ext.define('Foss.sign.deliverHandler.WateDetailStore',{
	extend: 'Ext.data.Store',
	storeId : 'Foss_sign_deliverHandler_WateDetailStoreID',
	fields: [
	    {name: 'extid', type: 'string'},// 额外的用于生成的EXT使用的列
		{name: 'waybillNo',  type: 'string'},// 运单号
		{name: 'arrivesheetNo',  type: 'string'},// 到达联编号
		{name: 'deliverStatus',  type: 'string'},// 派送单状态
		{name: 'createType',  type: 'string'},// 派送单状态
		{name: 'situation',  type: 'string'},// 到达联签收情况
		{name: 'id',  type: 'string'}// 派送单id
	],
	// 定义一个代理对象
	proxy: {
		// 代理的类型为内存代理
		type: 'ajax',
		actionMethods : 'POST',
		url:sign.realPath('queryWaybillNoByParams.action'),
		// 定义一个读取器
		reader: {
			// 以JSON的方式读取
			type: 'json',
			idgen: 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列//定义字段
			// 定义读取JSON数据的根对象
			root: 'deliverbillDetailVo.deliverbillDetailDtos'
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-deliverHandlerIndex_content').getQueryPanel().getValues();
			Ext.apply(operation,{
				params:{
					'deliverbillDetailVo.deliverbillDetailDto.deliverbillNo':queryParams.deliverbillNo
				}
			});
			// update 点击查询的时候 是否外包转完好 短少量 是否内货短少 都隐藏掉
			var form=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
			form.findField('packingResult').setVisible(false);
		    form.findField('alittleShort').setVisible(false);
		    Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].hide();
			// update
		},
		// load事件
		load: function(store, records, successful, eOpts){
			var button = Ext.getCmp('Foss_sign_deliverHandler_wateDetailGrid_goodsCheck_Id');
			Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_noNormalSelectAll_ID').setVisible(false);
			if(records.length<=0){// 如果待处理模块没有记录,让送货确认按钮不可用
				button.setDisabled(true);
			}else{
				if(records[0].data.deliverStatus ==sign.deliverHandler.deliverStatus){
					button.setDisabled(true);
				}else {
					button.setDisabled(false);
				}
			}
		}
	}
}); 

// 待处理表格
Ext.define('Foss.sign.deliverHandler.WateDetailGrid',{
	extend:'Ext.grid.Panel',
	title:sign.deliverHandler.i18n('pkp.sign.deliverHandler.pending'),// 待处理
	// 收缩
	collapsible: true,
	// 是否有框
	frame:true,
	// 动画收缩
	animCollapse: true,
	// 高
	height: 790,
	emptyText:sign.deliverHandler.i18n('pkp.sign.deliverHandler.emptyText'),// 查询结果为空
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
					text : sign.deliverHandler.i18n('pkp.sign.deliverHandler.waybillNoSign')// 正常签收
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
					text :sign.deliverHandler.i18n('pkp.sign.deliverHandler.waybillNoRefuse')// 异常/货物拉回
				}]
	}],
	columns: [
        { header: sign.deliverHandler.i18n('pkp.sign.deliverHandler.waybillNo'),  align: 'center', dataIndex: 'waybillNo',width : 100},// 运单号
        { header: sign.deliverHandler.i18n('pkp.sign.deliverHandler.arrivesheetNo'),  align: 'center', dataIndex: 'arrivesheetNo',  flex: 1  }// 到达联编号
	],
	enableColumnHide: false,      // 取消列头菜单
  	// sortableColumns: false, //取消列头排序功能
	viewConfig: {
         enableTextSelection: true,// 设置行可以选择，进而可以复制
        forceFit : true,
        stripeRows: false,// 显示重复样式，不用隔行显示
         getRowClass : function(record, rowIndex, rp, ds) {
 			var situation = record.get('situation');
 			if (situation == 'NORMAL_SIGN') {
 				return 'passProcess_mark_color';// 变成绿色
 			}else{
 				if(Ext.isEmpty(situation)){
 					return '';
 				}else {
 					return 'failProcess_mark_color';// 变成红色
 				}
 				 
 			}
 		}
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.deliverHandler.WateDetailStore');
		me.bbar = ['->',{
			text: sign.deliverHandler.i18n('pkp.sign.deliverHandler.deliveryConfirmation'),// 送货确认
			disabled:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexconfirmbutton'),
			hidden:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexconfirmbutton'),
			tabIndex: 1,
			disabled:true,
			id:'Foss_sign_deliverHandler_wateDetailGrid_goodsCheck_Id',
			textAlign : 'center',
			handler: function() {
				Ext.getCmp('Foss_sign_deliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(true);
				var store = Ext.data.StoreManager.lookup('Foss_sign_deliverHandler_WateDetailStoreID');
				var deliverbillNos = new Array();
				deliverbillNos.push(sign.deliverHandler.deliverbillNo);
				var deliverIds = new Array();
				deliverIds.push(store.data.items[0].data.id);
				Ext.Ajax.request({
					url:sign.realPath('queryArrivesheetIsSign.action'),
					method: 'POST',
					jsonData: {
						'deliverbillDetailVo':{
							'deliverbillDetailDto':{
								'deliverbillNos':deliverbillNos,// 派送单号
								'ids':deliverIds // 派送单id
							}
						}
					},
					success: function(response){
						var json = Ext.decode(response.responseText);
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),json.message,'ok',3000);
							// 加载待处理模块下的信息
							// Ext.getCmp('Foss_sign_deliverHandler_WateDetailGrid_Id').getStore().load();
							// Ext.getCmp('Foss_sign_deliverHandler_WateDetailGrid_Id').getStore().removeAll();
							// 派送单号为空
							sign.deliverHandler.deliverbillNo = null;
							// 送货确认按钮不可用
							Ext.getCmp('Foss_sign_deliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(true);
					},exception: function(response){// 异常处理
						var json = Ext.decode(response.responseText);
						Ext.getCmp('Foss_sign_deliverHandler_wateDetailGrid_goodsCheck_Id').setDisabled(false);
              			Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), json.message, 'error', 3000);// 提示信息
					}
				});
			}
		}];
		me.callParent([cfg]);
	}
});

// 财务信息
Ext.define('Foss.sign.deliverHandler.FinancialInfoForm',{
	extend: 'Ext.form.Panel',
	title :sign.deliverHandler.i18n('pkp.sign.deliverHandler.financialInformation'),// 财务信息
	id:'Foss_sign_deliverHandler_FinancialInfoForm_Id',
	// 收缩
	collapsible: true,
	// 是否有框
	frame:true,
	// 动画收缩
	animCollapse: true,
	defaults: {
		padding:'5 15 7 5',
		labelWidth:85,
		readOnly: true
	},
	height:195,
	defaultType : 'textfield',
	layout:'column',
	items:[{
			name: 'pocketShipping',
			fieldLabel: sign.deliverHandler.i18n('pkp.sign.deliverHandler.pocketShipping'),// 实付运费
			value:0,
			columnWidth: .33
		},{
			name: 'codAmount',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.codAmount') ,// 代收货款
			value:0,
			columnWidth: .33
		},{
			name: 'toPayAmount',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.toPayAmount'),// 收款总额
			value:0,
			columnWidth: .33
		},{
			name: 'receiveableAmount',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveableAmount'),// 应收代收款
			value:0,
			columnWidth: .33
		},{
			name: 'receivedAmount',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receivedAmount'),// 已收代收款
			value:0,
			columnWidth: .33
		},{
			name:'receiveablePayAmoout',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveablePayAmoout'),// 应收到付款
			readOnly: true,
			value:0,
			columnWidth: .33
		},{
			name:'receivedPayAmount',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receivedPayAmount'),// 已收到付款
			value:0,
			columnWidth: .33
		},{
			name:'paymentType',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.paymentType'),// 付款方式
			columnWidth: .33,
			// allowBlank: false,//不允许为空
			xtype: 'combobox',
			forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
			valueField:'valueCode', 
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			store:sign.deliverHandler.PaymentStore,
			listeners : {
		    	'select' : function(combo, records, eOpts){
		    		var form = this.up('form').getForm();
		    		if(records[0].get('valueCode') =='NT' || records[0].get('valueCode')=='TT'
		    				|| records[0].get('valueCode') =='CD'){// 支票,电汇,银行卡
		    			// 款项确认编号可编辑
						form.findField('claimNo').setReadOnly(false);
		    		}else{
		    			form.findField('claimNo').setValue('');
		    			// 款项确认编号不可编辑
						form.findField('claimNo').setReadOnly(true);
		    		}
		    		//add by 353654
		    		if((records[0].get('valueCode')=='BE') && !Ext.isEmpty(form.findField('consigneeCode').getValue())){
		    			//发送ajax调用CUBC的接口,将查询的余额回显到页面
		    			var consigneeCode = form.findField('consigneeCode').getValue();
		    			Ext.Ajax.request({
		                    url : sign.realPath('queryBalanceAmount.action'),//请求路径
		                        params : {
		                        	'vo.repaymentEntity.consigneeCode' : consigneeCode//请求参数
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
		                            Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), json.message, 'error',3000);
		                    }
		    			});
		    		}
		    	}
		    }
		},{
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.claimNo'),// 款项确认编号
			name: 'claimNo',
			labelWidth:95,
			columnWidth:.33
		},{
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.consigneeCode'),// 客户
			// editable:false,
			// forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			xtype : 'commoncustomerselector',
			name: 'consigneeCode',
			columnWidth:.22,
			listeners : {
				'select' : function(){
					var form=Ext.getCmp('Foss_sign_deliverHandler_FinancialInfoForm_Id').getForm();
		    		//add by 353654
		    		if((form.findField('paymentType')=='BE') && !Ext.isEmpty(form.findField('consigneeCode').getValue())){
		    			//发送ajax调用CUBC的接口,将查询的余额回显到页面
		    			var consigneeCode = form.findField('consigneeCode').getValue();
		    			Ext.Ajax.request({
		                    url : sign.realPath('queryBalanceAmount.action'),//请求路径
		                        params : {
		                        	'vo.repaymentEntity.consigneeCode' : consigneeCode//请求参数
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
		                            Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), json.message, 'error',3000);
		                    }
		    			});
		    		}
				}
		    }
		},{
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveCustomerContact'),// 收货客户联系人
			name: 'receiveCustomerContact',
			columnWidth:.22
		},{//add by 353654
			fieldLabel: sign.deliverHandler.i18n('pkp.sign.deliverHandler.balanceAmount'),//余额回显
			name: 'balanceAmount',
			allowBlank:true,
			readOnly:true,
			columnWidth:.22
		},{
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveCustomerName'),// 收货客户
			name: 'receiveCustomerName',
			labelWidth:95,
			columnWidth:.33
		}]
});
// 签收流水号GridPanel
Ext.define('Foss.sign.deliverHandler.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.deliverHandler.i18n('pkp.sign.deliverHandler.emptyText'),// 查询结果为空
	columnLines: true,
	height: 300,// 高度
	id: 'Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID',
	frame: true,
	enableColumnHide: false,      // 取消列头菜单
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {// 取消选中时产生的事件
				var form=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
				var situation=form.findField('situation').getValue();
				// 签收情况不为正常签收，同票多类异常
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD' ){
					Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.items[index].set('goodShorts',false);
				}
		},
			select: function(model,record,index) {
				var form=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
				var situation=form.findField('situation').getValue();
				var count=Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.length;
				// 签收情况不为正常签收，同票多类异常
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD' ){
					if(count=='1'){
						Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').getStore().data.items[0].set('goodShorts',true);
					}
				}
			}
		}
	}),
    title:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signPieces'),// 签收件
	columns: [
        {header: sign.deliverHandler.i18n('pkp.sign.deliverHandler.serialNo'), dataIndex: 'serialNo', flex: 1,align: 'center' },// 流水号
		 // update begin
        {header: sign.deliverHandler.i18n('pkp.sign.sign.goodShorts'), dataIndex: 'goodShorts', xtype:'checkcolumn',flex: 1.6,align: 'center', stopSelection:true,
			listeners:{
				'checkchange': function(model,record,index){
						var SerialNoGridPanel =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID'),
						 state=SerialNoGridPanel.getSelectionModel().isSelected(record),
						 form=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm(),
						 situation=form.findField('situation').getValue(),
						 count=SerialNoGridPanel.getStore().data.length;
						// 签收情况不为正常签收，同票多类异常
						if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
						   if(count=='1'){
								SerialNoGridPanel.getSelectionModel().selectAll(true);
								SerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
							}else if(!state){
								SerialNoGridPanel.getStore().data.items[record].set('goodShorts',false);
							}
						}
					}
				}
		}// 是否内货短少
    ],
    dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
				{
					name:'startSerialNo',
					fieldLabel:sign.deliverHandler.i18n('pkp.sign.sign.start'),// 从
					xtype:'numberfield',
					labelWidth:28,
					maxValue:9999,
					minValue:1,
					hideTrigger: true,
					columnWidth:.15,
					// allowBlank: false,//不允许为空
					allowNegative : false,// 不允许输入负数
					allowDecimals : false// 不允许有小数点
				},
				{
					name:'endSerialNo',
					fieldLabel:sign.deliverHandler.i18n('pkp.sign.sign.end'),// 到
					xtype:'numberfield',
					labelWidth:28,
					maxValue:9999,
					allowNegative : false,// 不允许输入负数
					minValue:1,
					hideTrigger: true,
					columnWidth:.15,
					// allowBlank: false,//不允许为空
					allowDecimals : false// 不允许有小数点
				},
				{
					xtype : 'button',
					columnWidth:.1,
					margin : '0 0 0 5',
					text :sign.deliverHandler.i18n('pkp.sign.sign.choose'),// 选中
					handler:function(grid, rowIndex, colIndex){
						var startSerialNo =this.up('toolbar').items.items[0].value,
							endSerialNo =this.up('toolbar').items.items[1].value,
							me = this.up('toolbar').up('grid'),
							store = me.getStore(),
							records = store.getRange(),
							choose = [];
						if(Ext.isEmpty(startSerialNo) || Ext.isEmpty(endSerialNo)){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), sign.deliverHandler.i18n('pkp.sign.sign.rangeMustEnter'), 'error', 4000);// 区间必须输入
							return;
						}
						if(startSerialNo>9999 || endSerialNo >9999){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), sign.deliverHandler.i18n('pkp.sign.sign.largeNumber'), 'error', 4000);// 数字最大范围是9999
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
					xtype: 'container',
					border : false,
					columnWidth:.2,
					html: '&nbsp;'     
				} , {
					xtype: 'checkbox',
				   // hideLabel: true,
					columnWidth:.06,
					heigth:20,
					id:'Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_noNormalSelectAll_ID',
					name:'noNormalSelectAll',
					allowBlank:true,
					labelWidth:140,
					hidden:true,
					fieldLabel:'全选',
					listeners : {
						'change' : function(field, newValue, oldValue,object){
					         	var SerialNoGridPanel =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID'),
						        selectRows=SerialNoGridPanel.getSelectionModel().getSelection();
							if(newValue ==true){
								if (selectRows != null && selectRows.length > 0){
									Ext.each(selectRows, function(item, index, allItems){
										item.set('goodShorts',true);
									});
								}else {
									field.setValue(false);
								}
								
							}else{
								if(selectRows != null && selectRows.length > 0){
									// selectRows
									Ext.each(selectRows , function(selectRow , index , allSelectRow){
										// 这里‘goodShorts’代表流水号面板的第二列
										selectRow.set('goodShorts' , false);
									});
								}
							}
						}
					}
            }, {
					xtype: 'container',
					border : false,
					columnWidth:.16,
					html: '&nbsp;'     
				}]
	}],
    viewConfig: {
        enableTextSelection: true
    },listeners:{
			beforeselect: function(rowModel, record, index, eOpts) {
		   var signOutStorageForm=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
		   var signStatus=signOutStorageForm.findField('signStatus').getValue();
		   if(signStatus==sign.deliverHandler.SIGN_STATUS_NO){
				return false;
		   }
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.deliverHandler.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});


/** --------------------------------------------------------------- */
// gird————
sign.deliverHandler.CellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});

// 同票多类异常里的无异常签收流水号GridPanel
Ext.define('Foss.sign.deliverHandler.MultiExceptionNormalSerialNoGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.deliverHandler.i18n('pkp.sign.deliverHandler.emptyText'),// 查询结果为空
	columnLines: true,
	height: 230,// 高度
	id: 'Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID',
	frame: true,
    enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    title:'无差异流水号',// 签收件
	columns: [
        {header: sign.deliverHandler.i18n('pkp.sign.expSign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'}
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.deliverHandler.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

// 同票多类
Ext.define('Foss.sign.deliverHandler.MultiExceptionSignPiecesForm',{
	extend: 'Ext.form.Panel',
	title:'签收件',
    defaultType : 'textfield',
	layout: 'column',
	frame:true,
	id: 'Foss.sign.deliverHandler.withTicketFormPanel_ID',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	signOutWithTicketStorageGridPanel :　null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.deliverHandler.OutWithTicketStorageGridPanel',{
				columnWidth : 1
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},
	multiExceptionNormalSerialNoGridPanel :　null,
	getMultiExceptionNormalSerialNoGridPanel: function(){
		if(this.multiExceptionNormalSerialNoGridPanel == null){
			this.multiExceptionNormalSerialNoGridPanel = Ext.create('Foss.sign.deliverHandler.MultiExceptionNormalSerialNoGridPanel',{
				columnWidth : 1
			});
		}
		return this.multiExceptionNormalSerialNoGridPanel;
	},
	listeners : {
		beforerender:function(){
				Ext.getCmp('Foss.sign.deliverHandler.withTicketFormPanel_ID').hide();
			}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		me.getSignOutWithTicketStorageGridPanel(),me.getMultiExceptionNormalSerialNoGridPanel()
	];
		me.callParent([cfg]);
	}
});

Ext.define('Foss.sign.deliverHandler.OutWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    emptyText:sign.deliverHandler.i18n('pkp.sign.deliverHandler.emptyText'),// 查询结果为空
	id: 'Foss_sign_deliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID',
	frame: false,
	selModel: {selType:'cellmodel'},
	// 收缩
	// collapsible: true,
   // title:'流水号明细',//流水号明细
	plugins:[sign.deliverHandler.CellEditing],
	viewConfig: {
        forceFit : true
	},
	listeners:{
	 		'edit':function(th,e){
	 			sign.deliverHandler.editor(th,e);
	 			
	 		}
	 },
	columns: [
		{
            xtype:'actioncolumn',
			align: 'center',
			menuDisabled:true,
            text: '删除',// 删除
			minWidth:65,maxWidth:65,
			align: 'center',
            items: [{
              // iconCls : 'deppon_icons_remove',
            	iconCls : 'deppon_icons_delete',
                tooltip: '删除',// 删除
                disabled:'',
                handler: function(grid, rowIndex, colIndex) {
                	var store=this.up('panel').getStore(),
					signState =store.getAt(rowIndex).data.signState,
					serialNo=store.getAt(rowIndex).data.serialNo,
					multiNormalSerNos=Ext.getCmp('Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore();
					if(!Ext.isEmpty(signState) && signState=='UNNORMAL_GOODSHORT'){
						var form=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
						sign.deliverHandler.setPartFormVisible(form,false);		
					}
					if(!Ext.isEmpty(serialNo)){
						var serialNoArray=serialNo.split(',');
						for(var i=0;i<serialNoArray.length;i++){
							var newRecord={serialNo:serialNoArray[i]}; 
							multiNormalSerNosStore.insert(multiNormalSerNosStore.getCount(),newRecord); 
						}
					}
					store.removeAt(rowIndex);
					if(!Ext.isEmpty(signState)){
						sign.deliverHandler.resetSignNote();
					}
				}
				
			}]
		},
		
        {header: '签收情况', dataIndex: 'signState',minWidth:135,maxWidth:135,menuDisabled:true,align: 'center',
			editor:{ 
					xtype:'combobox',
					labelWidth: 60, 
					forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
					editable:false, // 不可编辑
					valueField:'valueCode',  
					displayField: 'valueName', 
					queryMode:'local',// 如果不加这句话，就查的是签收情况里所有的项
					triggerAction:'all',
					store : sign.deliverHandler.exsituationStore,
					listeners : {
						'change' : function(field,newValue, oldValue, eOpts){
							if(newValue == oldValue){
								return false;
							}
						}
					}
			},
			renderer:function(value){  
                return FossDataDictionary.rendererSubmitToDisplay(value, sign.deliverHandler.PKP_SIGN_SITUATION);  
              }
		},
		{header: '流水号选择', dataIndex: 'choose',/*
												 * id:'choose_id'+new
												 * Date().getTime(),
												 */
		renderer:function(value,cellmeta){
			var returnStr = "<input type='button' value='选择'  style='width: 60px'/>";            
			return returnStr;
		},minWidth:98,maxWidth:98,menuDisabled:true,
		listeners:{
			'click': function(model,record,index){
						var value = this.up('panel').getStore().getAt(index).data.signState;
						if(!Ext.isEmpty(value)){
							// 当前行的流水号数据
							var serialNo = this.up('panel').getStore().getAt(index).data.serialNo,
							 multiNormalSerNos=Ext.getCmp('Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
							 multiNormalSerNosStore=multiNormalSerNos.getStore(),
							 showSerials=new Array(),
							 chooseModel=new Array();
							var win ;
							var seriaNoGrid ;
							var serialNoOutStorage;
							if(!Ext.getCmp('Foss_sign_deliverHandler_WithTicketStoraWindow_ID')){
								win = Ext.create('Foss.sign.deliverHandler.WithTicketStoraWindow');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}else{
							    win = Ext.getCmp('Foss_sign_deliverHandler_WithTicketStoraWindow_ID');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}
							if(!Ext.isEmpty(serialNo)) {
								var serialNoArray=serialNo.split(',');
								for(var i=0;i<serialNoArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.deliverHandler.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':serialNoArray[i],'isSelect':true});
									chooseModel.push(showSerials);
								}
								
							}
							if(multiNormalSerNosStore.data.length >0){
								for(var i=0;i<multiNormalSerNosStore.data.length;i++){
									showSerials.push(
									{'waybillNo':sign.deliverHandler.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':multiNormalSerNosStore.data.items[i].data.serialNo,'isSelect':false});
								}
							}
							serialNoOutStorage.loadData(showSerials,true);
							sign.deliverHandler.SIGN_RECORD_SERINALNO_INDEX=index;
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
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), '签收情况没有被选择', 'error', 3000);
								return false;
						}
				}		
			},align: 'center'
		},
		{header: '异常流水号', dataIndex: 'serialNo',
		xtype : 'ellipsiscolumn',
		menuDisabled:true,align: 'center',name:'serialNo',minWidth:387,maxWidth:387
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
						var count = sign.deliverHandler.exsituationStore.getCount();
						var panelGridCount = this.up('panel').getStore().getCount();
						if(panelGridCount>=count){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'签收情况已全部选择,不可再添加!','error',1000);
							return false;
						}
						var model=Ext.create('Foss.sign.deliverHandler.SignOutWithTicketStorageModel',{
							signState:'',
							choose:'选择',
							serialNo:''
						});
						var store=this.up('panel').getStore();
						store.insert(0,model);
						sign.deliverHandler.CellEditing.startEditByPosition({row: 0, column: 0});
					}
				}]
	}],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.deliverHandler.OutStateWithTicketStorageStore');
		me.callParent([cfg]);
	}
});
// store
Ext.define('Foss.sign.deliverHandler.OutStateWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.deliverHandler.SignOutWithTicketStorageModel',
	listeners:{
	
	}
});
// gird
Ext.define('Foss.sign.deliverHandler.SerialNoWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.deliverHandler.i18n('pkp.sign.deliverHandler.emptyText'),// 查询结果为空
	height: 300,// 高度
	id: 'Foss_sign_deliverHandler_SerialNoWithTicketMulStorageGridPanel',
	frame: true,
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {// 取消选中时产生的事件
				record.data.isSelect=false;
		},
			select: function(model,record,index) {
				if(Ext.isEmpty(record.data.isSelect) || record.data.isSelect==false){
					record.data.isSelect=true;
				}
			}
		}
	}),
    title:'流水号明细',// 流水号明细
	columns: [
		{header: '运单号', dataIndex: 'waybillNo'},
        {header: '流水号', dataIndex: 'serialNo'},
		{dataIndex: 'isSelect',hidden:true}
    
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.deliverHandler.SerialNoWithTicketStorageStore');
		me.callParent([cfg]);
	},
	listeners:{
	}
});
// store
Ext.define('Foss.sign.deliverHandler.SerialNoWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.deliverHandler.SerialNoWithTicketStorageModel'
	});
// window
// 弹出窗口
Ext.define('Foss.sign.deliverHandler.WithTicketStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_deliverHandler_WithTicketStoraWindow_ID',
	closeAction: 'hide',
	layout: 'auto',	
	width:300,
	resizable : false,
	title:'流水号选择',// 流水号选择
	modal: true,
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.deliverHandler.SerialNoWithTicketStorageGridPanel',{
				width:270,
				text : sign.deliverHandler.i18n('pkp.sign.deliverHandler.signPieces')// 签收件
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
				var SerialNoWithTicket =Ext.getCmp('Foss_sign_deliverHandler_SerialNoWithTicketMulStorageGridPanel'),
					serialNos = SerialNoWithTicket.getSelectionModel().getSelection(),
					SerialNoWithTicketStore = SerialNoWithTicket.getStore().data,
					frontGrid=Ext.getCmp('Foss_sign_deliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID'),
					frontGrid_Store=frontGrid.getStore(),
					multiNormalSerNos=Ext.getCmp('Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore(),
					selectSerialNos="",
					deselectSerialNos =new Array();
				if(serialNos.length<=0){
					Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), '请选择各类型异常流水号!', 'error', 1000);
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
					frontGrid_Store.data.items[sign.deliverHandler.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',selectSerialNos.substring(0,selectSerialNos.length-1));
				}else{
					frontGrid_Store.data.items[sign.deliverHandler.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',"");
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


/** --------------------------------------------------------------- */


// 签收信息
Ext.define('Foss.sign.deliverHandler.SignInfoForm',{
	extend: 'Ext.form.Panel',
	title:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signForInformation'),// 签收信息
	id:'Foss_sign_deliverHandler_SignInfoForm_Id',
	// 收缩
	collapsible: true,
	// 是否有框
	frame:true,
	// 动画收缩
	animCollapse: true,
	margin:'-10 1 1 1',
	// height:700,
	// layout:'hbox',
	items:[{
		xtype: 'form',
		defaults: {
			labelWidth:65,
			padding: '5 5 5 5'
		},
		defaultType : 'textfield',
		frame:true,
		title:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signForInformation'),// 签收信息
		layout:'column',
		items:[
		{
			name:'arrivesheetNo',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.arrivesheetNo'),// 到达联编号
			columnWidth:.3,
			labelWidth:75,
			readOnly: true// 只读
		},{
			name:'arriveSheetGoodsQty',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.arriveSheetGoodsQty'),// 到达联总件数
			columnWidth:.22,
			labelWidth:60,
			readOnly: true// 只读
		},{
			name: 'isPdaSign',
			xtype:'checkbox',
	        fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.isPdaSign'),// PDA
	        columnWidth:.15,
	        readOnly:true,
	        labelWidth:50,
	        height: 24,
			inputValue: 'Y'
		},{
			xtype: 'datetimefield_date97',
			format : 'Y-m-d H:i:s',
			id: 'deliverHandler-QueryPanel-signTimeEnd',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signTime'),// 签收时间
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
		},{
			xtype:'combobox',
			name: 'signStatus',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.sign.signStatus'),// 到达联签收状态
			labelWidth: 100, 
			forceSelection:true,// 必须选择一个。
			valueField:'valueCode',  
			displayField: 'valueName', 
			allowBlank:false,
			queryMode:'local',
			triggerAction:'all',
			columnWidth: .3,
			store :FossDataDictionary.getDataDictionaryStore('PKP_SIGN_STATUS', null),
			listeners : {
				'select' : function(combo, records, eOpts){
					form = this.up('form').getForm();
					form.findField('packingResult').setVisible(false);
					form.findField('alittleShort').setVisible(false);
					 Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].hide();
					// update
					var	financialInfo = Ext.getCmp('Foss_sign_deliverHandler_FinancialInfoForm_Id').getForm(),
					serialGrid =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID'),
					signGoodsQty = form.findField('signGoodsQty');
					serialGrid.show();
					Ext.getCmp('Foss.sign.deliverHandler.withTicketFormPanel_ID').hide();
					form.findField('pullbackReason').setValue('');// 拉回原因为空
		    		form.findField('signNote').setValue('');// 签收备注为空
					var isVisiable=true;
					Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_noNormalSelectAll_ID').setVisible(false);
					if(records[0].get('valueCode') ==sign.deliverHandler.SIGN_STATUS_NO){ // 未签收
						serialGrid.getSelectionModel().deselectAll();
						form.findField('situationGoodsBack').setValue('GOODS_BACK');
						form.findField('situationGoodsBack').setReadOnly(true);
						signGoodsQty.setValue(0);// 签收件数为0
						signGoodsQty.setDisabled(true)// 签收件数不可编辑
						form.findField('pullbackReason').setVisible(true);// 拉回原因可见
		    			form.findField('pullbackReason').setDisabled(false);// 拉回原因可编辑
		    			sign.deliverHandler.formFieldReset(financialInfo,true);
		    			sign.deliverHandler.isReadOnly(financialInfo,true);
		    			
		    			//306566   DN201601290015   选择未签收的时候还没选择拉回原因的时候 备注不显示哦
						form.findField('pullbackNote').setVisible(false);// 拉回备注不可见
		    			financialInfo.findField('paymentType').setValue(sign.deliverHandler.paymentType);
		    			if (form.findField('pullbackReason').getValue() == sign.deliverHandler.PKP_PULLBACK_REASON_DRIVER_LATE ) {
		    				form.query('textfield[name="nextDeliverTime"]')[0].setVisible(true);// 送货日期可见
						}
		    			//当选择其他原因的时候，新增一行备注，
		    		/*	if (form.findField('pullbackReason').getValue() == sign.deliverHandler.OTHER ) {
		    				form.query('textfield[name="pullbackNote"]')[0].setVisible(true);// 拉回 备注可见
						}*/
		    		}else{
						isVisiable=false;
						// 签收情况默认选中“正常签收”
						form.findField('situation').setValue('NORMAL_SIGN');
						// 签收备注 默认为“正常签收”
						form.findField('signNote').setValue(sign.deliverHandler.i18n('pkp.sign.deliverHandler.normalSign'));// 正常签收
						// 签收件数等于到达联件数
			    		sign.deliverHandler.isReadOnly(financialInfo,false);
						form.findField('pullbackReason').setVisible(false);// 拉回原因不可见
						form.findField('nextDeliverTime').setVisible(false);// 送货日期不可见
						
						//不选择其他原因  备注不可见306566  DN201601290015
						form.findField('pullbackNote').setVisible(false);// 拉回备注不可见
					 
			    		if(financialInfo.findField('paymentType').getValue() =='NT' || financialInfo.findField('paymentType').getValue()=='TT'
			    				|| financialInfo.findField('paymentType').getValue() =='CD'){// 支票,电汇,银行卡
			    			// 款项确认编号可编辑
			    			financialInfo.findField('claimNo').setReadOnly(false);
			    		}else{
			    			financialInfo.findField('claimNo').setValue('');
			    			// 款项确认编号不可编辑
			    			financialInfo.findField('claimNo').setReadOnly(true);
			    		}
			    		signGoodsQty.setValue(form.findField('arriveSheetGoodsQty').getValue());// 签收件数等于到达联件数
						if(records[0].get('valueCode') ==sign.deliverHandler.SIGN_STATUS_PARTIAL){// 签收状态选择为部分签收时，签收件数显示为到达联件数且可编辑
							signGoodsQty.setDisabled(false);
						}else {
							signGoodsQty.setDisabled(true)// 签收件数不可编辑
						}

					}
					form.findField('signNote').setVisible(!isVisiable);// 签收备注可见
		    		form.findField('signNote').setDisabled(isVisiable);// 签收备注可编辑
					form.findField('situationGoodsBack').setVisible(isVisiable);// 货物拉回
					form.findField('situation').setVisible(!isVisiable);// 签收情况不可见
					form.findField('deliverymanName').reset();
					form.findField('deliverymanName').allowBlank = isVisiable;
		    		form.findField('deliverymanName').setDisabled(isVisiable);
				}
			}
		},{
			name:'signGoodsQty',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signGoodsQty'),// 签收件数
			xtype:'numberfield',
			labelWidth:60,
			hideTrigger: true,
			columnWidth:.22,
			allowBlank: false,// 不允许为空
			allowDecimals : false// 不允许有小数点
		},{
			xtype: 'combobox',
			name:'situation',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.situation'),// 签收情况选择
			allowBlank: false,// 不允许为空
			forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
			valueField:'valueCode', 
			columnWidth:.3,
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			store : sign.deliverHandler.situationStore,
			listeners : {
		    	'select' : function(combo, records, eOpts){
		    		var form = this.up('form').getForm(),
					SerialNoOutStorageGridPanel =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID');
				SerialNoOutStorageGridPanel.show();
				form.findField('signNote').setValue('');// 签收备注为空
		    		// 内物短少信息初始为隐藏
	    			sign.deliverHandler.setPartFormVisible(form,false);
				var UnNormalSelectAll=Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_noNormalSelectAll_ID');
				UnNormalSelectAll.setVisible(false);
				var WithTicketFormPanel = Ext.getCmp('Foss.sign.deliverHandler.withTicketFormPanel_ID');
    				WithTicketFormPanel.hide();
				// 如果签收情况为正常签收
				if(records[0].get('valueCode') == 'NORMAL_SIGN'){
		    			SerialNoOutStorageGridPanel.columns[2].hide();
		    			form.findField('signNote').setValue(records[0].get('valueName'));
					}else if(records[0].get('valueCode') == 'UNNORMAL_SAMEVOTEODD'){// 同票多类异常
						SerialNoOutStorageGridPanel.hide();
						WithTicketFormPanel.show();
						var SignOutSerialNoWithTicket=Ext.getCmp('Foss_sign_deliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID');
						var  multiNormalSerNos=Ext.getCmp('Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID'),
						 multiNormalSerNosStore=multiNormalSerNos.getStore();
						multiNormalSerNos.getSelectionModel().deselectAll();
						multiNormalSerNosStore.removeAll();
						if(SerialNoOutStorageGridPanel.getStore().data.length>0){
							multiNormalSerNosStore.loadData(SerialNoOutStorageGridPanel.getStore().data.items);
							multiNormalSerNos.getSelectionModel().selectAll(true);
						}
						// 初始化一条记录
						var SignOutSerialNoWithTicketStore=SignOutSerialNoWithTicket.getStore();
						if(SignOutSerialNoWithTicketStore.data.length>0){
							// 如果不加deselectAll()，多次加载时表格里的记录存在为空，报isDescendantOf'
							// of undefined
							SignOutSerialNoWithTicket.getSelectionModel().deselectAll();
							SignOutSerialNoWithTicketStore.removeAll();
						}
						var model=Ext.create('Foss.sign.deliverHandler.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_GOODSHORT',
								choose:'选择',
								serialNo:''
						});
						var model1=Ext.create('Foss.sign.deliverHandler.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_BREAK',
								choose:'选择',
								serialNo:''
						});
						SignOutSerialNoWithTicketStore.insert(0,model);
						SignOutSerialNoWithTicketStore.insert(1,model1);
						// 内物短少信息初始为显示
						sign.deliverHandler.setPartFormVisible(form,true);
						sign.deliverHandler.resetSignNote();
					}else {
						SerialNoOutStorageGridPanel.columns[2].show();
						UnNormalSelectAll.setVisible(true);
						UnNormalSelectAll.setDisabled(false);
						UnNormalSelectAll.setFieldLabel('是否'+records[0].get('valueName')+'全选');
						UnNormalSelectAll.setValue(false);
						SerialNoOutStorageGridPanel.columns[2].setText('是否'+records[0].get('valueName'));
						var count=SerialNoOutStorageGridPanel.getStore().data.length;
						if(count=='1'){		
							// 把前面的流水号也默认勾选上
							SerialNoOutStorageGridPanel.getSelectionModel().selectAll(true);
							SerialNoOutStorageGridPanel.getStore().data.items[0].set('goodShorts',true);
						}
						if(records[0].get('valueCode')=='UNNORMAL_GOODSHORT'){
							sign.deliverHandler.setPartFormVisible(form,true);
		    			
							// 并且数据可编辑
							form.findField('packingResult').setDisabled(false);
							// radiogroup 这种如果要操作items时候 需要把单个控件拿出来操作
							form.findField('packingResult').items.items[0].setDisabled(false);
							form.findField('packingResult').items.items[1].setDisabled(false);
							form.findField('alittleShort').setDisabled(false);
						}else{
							sign.deliverHandler.COUNT += 1;
							if(sign.deliverHandler.COUNT == 1){
								Ext.MessageBox.show({
									title : '提示',
									msg : '若内物短少与其他异常同时发生于同一件货物上，请优先选择“异常-内物短少”；否则一经查实，将按业绩粉饰处理。',
									width : 250,
									buttons : Ext.Msg.OK,
									icon : Ext.MessageBox.WARNING
								});
							}
						}
						form.findField('signNote').setValue(records[0].get('valueName'));
					}
		    	}
		    }
		},{
			xtype: 'combobox',
			name:'situationGoodsBack',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.situation'),// 签收情况选择
			allowBlank: false,// 不允许为空
			forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
			valueField:'valueCode', 
			columnWidth:.25,
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			store : sign.deliverHandler.situationGoodBackStore
		},{
			xtype: 'combobox',
			name:'pullbackReason',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.pullbackReason'),// 拉回原因
			forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
			allowBlank: false,// 不允许为空
			valueField:'valueCode', 
			displayField: 'valueName',
			queryMode:'local',
			columnWidth:.5,
			triggerAction:'all',
			//过滤拉回原因词条 306566  DN201601290015
			store:sign.deliverHandler.pullbackReasons,
			//store:FossDataDictionary.getDataDictionaryStore('PKP_PULLBACK_REASON', null),
			listeners : {
				change : function(combo, newValue) {
					var form = combo.up('form');
					if (newValue == sign.deliverHandler.PKP_PULLBACK_REASON_DRIVER_LATE  ){
						form.query('textfield[name="nextDeliverTime"]')[0].setVisible(true);
						form.query('textfield[name="nextDeliverTime"]')[0].setValue(Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,+1),"Y-m-d"));
					}else {
						form.query('textfield[name="nextDeliverTime"]')[0].setVisible(false);
					}
					//拉回原因为306566  DN201601290015
				 	if (newValue == sign.deliverHandler.OTHER ) {
	    				form.query('textfield[name="pullbackNote"]')[0].setVisible(true);// 拉回备注可见
					}else {
						form.query('textfield[name="pullbackNote"]')[0].setVisible(false);
					} 
				}
			}
		},{
			fieldLabel: sign.deliverHandler.i18n('pkp.sign.deliverHandler.packageResult'),// 外包装是否完好
			xtype: 'radiogroup',
        	vertical: true,
        	allowBlank:false,
			columnWidth:.4,
			anchorSize: 50,
			heigth:20,
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
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.alittleShort'),// 短少量
			xtype:'textfield',
			labelWidth:50,
			hideTrigger: true,
			columnWidth:.6,
			allowBlank: false,// 不允许为空
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
					return sign.deliverHandler.i18n('pkp.sign.deliverHandler.alittleShortMaxLength');
				}else{
					return true;
				}
		},
			listeners: {  
	       	 blur: function(field, value) {  
	        		sign.deliverHandler.resetSignNote();
	       	 }  
	    	}
		},{
			name:'deliverymanName',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.deliverymanName'),// 签收人
			labelWidth:60,
			maxLength:50,
			columnWidth:.4,
			allowBlank:false
		},{
			name:'signNote',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.signNote'),// 签收备注
			columnWidth:.6,
			maxLength:200,
			allowBlank: false// 不允许为空
		},{
			name: 'nextDeliverTime', 
			xtype: 'datefield',
			fieldLabel: sign.deliverHandler.i18n('pkp.sign.deliverHandler.deliverDate'),// 送货日期
			labelWidth:60,
			format:'Y-m-d',
			columnWidth: .3,
			allowBlank: false,
			value : Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,+1),"Y-m-d"),
			minValue: new Date(),
			align: 'center'
		},{
			//拉回原因为空的时候添加拉回备注信息306566  DN201601290015
			name:'pullbackNote',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.pullbackNote'),// 拉回 备注(备注)
			labelWidth:60,
			maxLength:20,
			columnWidth:.4,
			allowBlank:true  //允许为空
			
		}]
	},Ext.create('Foss.sign.deliverHandler.SerialNoOutStorageGridPanel',{
		height:300,
		margin:'-10 1 1 1'
	}),Ext.create('Foss.sign.deliverHandler.MultiExceptionSignPiecesForm',{
// height:300,
		margin:'-10 1 1 1'
	})],
	checkButtons  : null,
	getCheckButtons: function(){
		if(this.checkButtons  == null){
			this.checkButtons = Ext.create('Ext.button.Button',{
				text:sign.deliverHandler.i18n('pkp.sign.deliverHandler.confirm'),// 确认
				disabled:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexsignbutton'),
				hidden:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexsignbutton'),
				width:'100',
				id:'Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id',
				disabled:true,
				style : {
					float : 'center'
				},
				cls:'yellow_button',
				handler:function(){
					var baseForm  = this.up('form');
					var	financialForm = Ext.getCmp('Foss_sign_deliverHandler_FinancialInfoForm_Id').getForm(),
						form = baseForm.getForm(),
					 WateDetailGrid = Ext.getCmp('Foss_sign_deliverHandler_WateDetailGrid_Id'),
					    selectRow = WateDetailGrid.getSelectionModel().getSelection(),
						situation='GOODS_BACK',
						arriveSheet  = form.getRecord(),
						serialNos = new Array(),
						samevoteoddIsGoodsShort=false;
					if (selectRow.length == 0){
						Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.selectOne'),'error',1000);// 请选择一行！
						return;
					}
					// 如果是无PDA签收
					if(form.findField('isPdaSign').getValue()==false || form.findField('isPdaSign').getValue() =='N'){
						var paymentType = financialForm.findField('paymentType').getValue(),
							checkCount  = Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection().length;
						if(!form.findField('signStatus').getValue()){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.signStatusNotNull'),'error',3000);// 签收状态不能为空，请选择状态情况！
							return;
						}else if(form.findField('signStatus').getValue() == sign.deliverHandler.SIGN_STATUS_NO) {
							// 如果拉回原因可见时为空，系统弹窗提示！
							 if(Ext.isEmpty(form.findField('pullbackReason').getValue())){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.pullbackReasonNotNull'),'error',3000);// 拉回原因不能为空,请选择拉回原因信息!
								return;
							// 如果拉回原因为司机晚送 ，送货日期为空，系统弹窗提示！
							}else if(form.findField('pullbackReason').getValue() == sign.deliverHandler.PKP_PULLBACK_REASON_DRIVER_LATE 
									&& form.findField('nextDeliverTime').getValue()==null ){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'送货日期不能为空！','error',3000);
								return;
							// 签收时间为空时提示
							}else if(!form.findField('signTime').getValue()){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.notcompleteInfo'),'error',3000);
								return;
							}
						}else {
							// 验证数字不能小于0时的提示信息
							 if(form.findField('signGoodsQty').getValue()==0 && form.findField('signStatus').getValue()==sign.deliverHandler.SIGN_STATUS_PARTIAL){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.ignGoodsQtyGreateThanZero'),'error',3000);
								return;
							}
							if(form.findField('situation').getValue()!='UNNORMAL_SAMEVOTEODD'){
								if(form.findField('signGoodsQty').getValue() != checkCount){
									Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.serialNoSignGoodsQtySame'),'error',3000);// 选择流水号数量必须与签收件数一致，请确认！
									return;
								}	
							} 
							if(form.findField('situation').getValue()!='NORMAL_SIGN' && form.findField('situation').getValue()!='UNNORMAL_SAMEVOTEODD'){
								var flag=true;
									var arr=Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection(); 
									for(var i=0;i<arr.length;i++){
									flag=arr[i].get('goodShorts');
									if(flag==true){
										break;
										}
									}
									if(flag==false||flag==null){
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),"是否"+form.findField('situation').getRawValue()+"至少勾选一个!",'error',2500);
										return ;
									}
							}else if(form.findField('situation').getValue()=='UNNORMAL_SAMEVOTEODD'){
								var SignOutSerialNoWithTicketStore=Ext.getCmp('Foss_sign_deliverHandler_OutSerialNoWithTicketMulStorageGridPanel_ID').getStore(),
								// signSeriaNoNoteVal
								// =Ext.getCmp('signSeriaNoNote_id').getValue(),
								multiNormalSelectSerNos=Ext.getCmp('Foss_sign_deliverHandler_MultiExceptionNormalSerialNoGridPanel_ID').getSelectionModel().getSelection(); 				
								if(SignOutSerialNoWithTicketStore.data.length<2){
									Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'请至少选择两种异常签收情况！','error',3000);
									return ;
								}
								for(var i=0;i<SignOutSerialNoWithTicketStore.data.length;i++){
									var serialSituation =SignOutSerialNoWithTicketStore.getAt(i).data.signState,
									serialNo=SignOutSerialNoWithTicketStore.getAt(i).data.serialNo
									if(Ext.isEmpty(serialSituation)){// 如果签收情况为空
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'签收件里的签收情况不能为空！','error',3000);
										return ;
									}
									if(Ext.isEmpty(serialSituation)){// 如果签收情况为空
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'签收件里的签收情况不能为空！','error',3000);
										return ;
									}
									if(serialSituation =='UNNORMAL_GOODSHORT'){
										samevoteoddIsGoodsShort=true;// 同票多类异常里有选择异常－内物短少
									}
									if(Ext.isEmpty(serialNo)||serialNo.indexOf(',')==0){// 如果签收流水号为空
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'签收件里的异常流水号不能为空！','error',3000);
										return ;
									}
									var choose = serialNo.split(',');
									for(var j=0;j<choose.length;j++){
										serialNos.push({
											'serialNo':choose[j],// 流水号
											'arrivesheetNo':arriveSheet.get("arrivesheetNo"),// 到达联编号
											'situation':serialSituation// 流水号签收情况
										});
									}
								}
								if(multiNormalSelectSerNos!=null){
									for (var i = 0; i < multiNormalSelectSerNos.length; i++) {// 把前台选中的流水号放入数组里
										serialNos.push({
											'serialNo':multiNormalSelectSerNos[i].get("serialNo"),// 流水号
											'arrivesheetNo':arriveSheet.get("arrivesheetNo"),// 到达联编号
											'situation':'NORMAL_SIGN'// 流水号签收情况
										});
									}
								}
								if(form.findField('signGoodsQty').getValue()!=serialNos.length){
									Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.sign.serialNoSignGoodsQtySame'),'error',1000);// 选择流水号数量必须与签收件数一致，请确认！
									return;
								}
							}
							// 付款方式为空，系统弹窗提示！
							if(!paymentType){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.paymentTypeNotNull'),'error',3000);// 付款方式不能为空，请选择付款方式！
								return;
							}
							// 客户为空，系统弹窗提示！add by 353654 BE——余额结清
							else if(!financialForm.findField('consigneeCode').getValue() && (paymentType=='CT' || paymentType=='DT' || paymentType=='BE')){
								// (月结),DT(临时欠款)
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.ctOrdtConsigneeNotNull'),'error',3000);// 付款方式为临时欠款,月结时客户不能为空，请选择客户信息
								return;
							}// 如果付款方式为支票,电汇,银行卡时,款项确认编号不能为空
							else if((!financialForm.findField('claimNo').getValue().trim()) 
									&& (paymentType=='NT' || paymentType=='TT' || paymentType=='CD')){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.ntOrttConsigneeNotNull'),'error',3000);// 付款方式为支票,电汇时,款项确认编号不能为空！
								return;
							}else if(paymentType=='CD' && !/^\s*\d+\s*$/.test(financialForm.findField('claimNo').getValue())){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),'付款方式为银行卡时，款项确认编号必须输入数字。','error',3000);// 付款方式为银行卡时,款项确认编号必须为数字！
								return;
							}//add by 353654
							else if(paymentType=='BE' && financialForm.findField('balanceAmount').getValue()-0 < financialForm.findField('pocketShipping').getValue()-0){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), '客户余额不足,请选择其他方式结清货款并签收', 'error', 3000);
								this.disabled=true;
								return;
							}
							// 如果签收状态为部分签收，并且签收件数大于到达联件数，系统弹窗提示错误
							else if(form.findField('signStatus').getValue()== sign.deliverHandler.SIGN_STATUS_PARTIAL &&form.findField('signGoodsQty').
									getValue()>= form.getRecord().get('arriveSheetGoodsQty')){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.partSignSignGoodsQtyUnqualified'),'error',3000);
								return;
							}
							// 签收情况为空，系统弹窗提示！
							else if(!form.findField('situation').getValue()){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.situationNotNull'),'error',3000);// 签收情况不能为空，请选择签收情况！
								return;
							}
							// 如果签收备注可见时为空，系统弹窗提示！
							else if(!form.findField('signNote').isValid()){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.signNoteNotNull'),'error',3000);
								return;
							}
							// 签收时间为空，系统弹窗提示！
							else if(!form.findField('signTime').getValue()){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.signTimeNotNull'),'error',3000);
								return;
							}
							// 签收人为空，系统弹窗提示！
							else if(!form.findField('deliverymanName').isValid()){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.signManNotNull'),'error',3000);
								return;
							}
							// 签收人为默认值N/A，系统弹窗提示！
							else if(form.findField('deliverymanName').getValue() == 'N/A'){
								Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.defaultNotInput'),'error',3000);
								return;
							}
							else if(form.findField('situation').getValue()=='UNNORMAL_GOODSHORT'||samevoteoddIsGoodsShort==true){
								// 校验短少量是否为空 如果为空 不让确认
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
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n(	'pkp.sign.deliverHandler.alittleShortMaxLength'),'error',3000);
										return;
									}
								}else{
										if(!form.findField('alittleShort').isValid()){
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.alittleShortNotNull'),'error',3000);
										return;
									}
								}
								// 校验外包装是否完好是否为空 如果为空 不让确认
								if(!form.findField('packingResult').isValid()){
									Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.packingResultNotNull'),'error',3000);
									return;
								}
							}
						}
						 Ext.Msg.confirm( sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), sign.deliverHandler.i18n('pkp.sign.deliverHandler.okTosubmit'), function(btn,text){
								if(btn == 'yes'){
									var signOutStorageForm=Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
									form.updateRecord(arriveSheet);
									var situation=form.findField('situation').getValue();						
									if(situation!='UNNORMAL_GOODSHORT'&& samevoteoddIsGoodsShort==false){
										arriveSheet.set('packingResult',null);
										arriveSheet.set('alittleShort',null);
									}
									arriveSheet.set('isPdaSign','N');
									var	financialDto = '';
										arriveSheet.data.signGoodsQty = form.findField('signGoodsQty').getValue();
										arriveSheet.data.nextDeliverTime = form.findField('nextDeliverTime').getValue(); // 赋值送货日期
										arriveSheet.set('signTime',Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true));
									var serialNorowObjs = Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').
										getSelectionModel().getSelection();// 得到选中的流水号信息
										if(form.findField('pullbackReason').isVisible(true)){// 如果拉回原因可见
											arriveSheet.data.signNote = arriveSheet.data.pullbackReason;
										}
										var signStatus=signOutStorageForm.findField('signStatus').getValue();
										if(signStatus==sign.deliverHandler.SIGN_STATUS_NO){
											var situationGoodsBack=signOutStorageForm.findField('situationGoodsBack').getValue();
											arriveSheet.data.situation=situationGoodsBack;
										}else{
											arriveSheet.data.situation=situation;// 得到签收情况
										}
									var	financialInfo = Ext.getCmp('Foss_sign_deliverHandler_FinancialInfoForm_Id').getForm();
									var financial = financialInfo.getRecord();
										financialInfo.updateRecord(financial);
									var consigneeName =financialInfo.findField('consigneeCode').getRawValue();
									
									if(arriveSheet.data.situation!='UNNORMAL_SAMEVOTEODD' && arriveSheet.data.situation!='GOODS_BACK'){
										if(serialNorowObjs!=null){
										for (var i = 0; i < serialNorowObjs.length; i++) {// 把前台选中的流水号放入数组里
											var goodShorts;
											if(situation=='NORMAL_SIGN'){
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),// 流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),// 到达联编号
													'goodShorts':goodShorts,// 是否内物短少
													'situation':situation// 流水号签收情况
												});
												// update
											}else{
												var situ = 'NORMAL_SIGN';
												if(serialNorowObjs[i].get("goodShorts")==true){
													situ=situation;
												}else{
													situ = 'NORMAL_SIGN';
												}
												serialNos.push({
													'serialNo':serialNorowObjs[i].get("serialNo"),// 流水号
													'arrivesheetNo':arriveSheet.get("arrivesheetNo"),// 到达联编号
													'situation':situ// 流水号签收情况
												});
											}
										}
									}
									}
									financialDto = {
												'productType' : financial.data.productCode,// 运输性质
												'isWholeVehicle' : financial.data.isWholeVehicle,// 是否整车运单
												'paymentType' : financial.data.paymentType, // 付款方式
												'receiveableAmount' :financial.data.receiveableAmount, // 应收代收款
												'receiveablePayAmoout' : financial.data.receiveablePayAmoout, // 应收到付款
												'consigneeCode':financial.data.consigneeCode,// 客户编码
												'claimNo' :financial.data.claimNo,// 款项确认编号
												'consigneeName':consigneeName,// 客户名称
												'deliverbillNo':sign.deliverHandler.deliverbillNo,// 派送单号
												'orderNo':financial.data.orderNo// 订单号
												};
									Ext.Ajax.request({
										url:sign.realPath('addNoPdaSignSign.action'),
										method: 'POST',
										timeout: 300000,
										jsonData: {
											'deliverbillDetailVo':{
												'financialDto':financialDto, // 财务信息
												'signDetailEntitys':serialNos // 流水号信息集合
											},'arriveSheetVo': {
												'arriveSheet': arriveSheet.data // 签收信息
											}
										},
										success: function(response){
											var json = Ext.decode(response.responseText);
											Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);
											sign.deliverHandler.setFormDisabled(baseForm,true); // 签收信息所有控件不可编辑
											sign.deliverHandler.isReadOnly(financialInfo,true);
											var serialGrid =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID');
											serialGrid.show();
											Ext.getCmp('Foss.sign.deliverHandler.withTicketFormPanel_ID').hide();
											serialGrid.getSelectionModel().deselectAll();
											serialGrid.getStore().removeAll();
											var selectIndex = WateDetailGrid.getSelectionModel().lastSelected.index;
											if (arriveSheet.data.situation == 'NORMAL_SIGN') {
												WateDetailGrid.getView().addRowCls(selectIndex, 'passProcess_mark_color');// 变成绿色
								 			}else{
								 				WateDetailGrid.getView().addRowCls(selectIndex, 'failProcess_mark_color');// 变成红色
								 				
								 			}
											Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),json.message,'ok',2000);// 操作成功！
										},exception: function(response){// 异常处理
											var json = Ext.decode(response.responseText);
					              			Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), json.message, 'error', 3000);
										}
									});
								}
							});
						 
					}// 如果是有pda签收
					else if(form.findField('isPdaSign').getValue()==true || form.findField('isPdaSign').getValue() =='Y'){
						// 签收人为空，系统弹窗提示！
						if(!form.findField('deliverymanName').isValid()){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.signManNotNull'),'error',3000);
							return;
						}
						// 签收人为默认值N/A，系统弹窗提示！
						if(form.findField('deliverymanName').getValue() == 'N/A'){
							Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.defaultNotInput'),'error',3000);
							return;
						}
						Ext.Msg.confirm( sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), sign.deliverHandler.i18n('pkp.sign.deliverHandler.okTosubmit'), function(btn,text){
							if(btn == 'yes'){
								var arriveSheet1  = form.getRecord();
									form.updateRecord(arriveSheet1);
									if(form.findField('pullbackReason').isVisible(true)){// 如果拉回原因可见
										arriveSheet1.data.signNote = arriveSheet.data.pullbackReason;
									}
									arriveSheet1.set('isPdaSign','Y');
								Ext.Ajax.request({
									url:sign.realPath('addPdaSign.action'),
									method: 'POST',
									jsonData: {
										'arriveSheetVo': {
											'arriveSheet': arriveSheet1.data // 签收信息
										}
									},
									success: function(response){
										var json = Ext.decode(response.responseText);
										Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);
										sign.deliverHandler.setFormDisabled(baseForm,true); // 签收信息所有控件不可编辑
										sign.deliverHandler.isReadOnly(financialForm,true);
										Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.entrySignManOk'),'ok',2000);// 录入签收人成功!
									},exception: function(response){// 异常处理
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'), json.message, 'error', 3000);
									}
								});
							}
						});
						
					}
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
			var	form = Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id').getForm();
				form.findField('pullbackReason').setVisible(false);// 拉回原因不可见
				form.findField('situationGoodsBack').setVisible(false);
				form.findField('alittleShort').setVisible(false);// 短少量不可见
				form.findField('nextDeliverTime').setVisible(false); // 送货时间不可见
			// form.findField('packingResult').setVisible(false);//外包装是否完好不可见
				
		}
	}
});



// 下面大panel 包括待处理,财物信息,签收信息
Ext.define('Foss.sign.deliverHandler.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	financialInfoForm : null,// 财务信息
	getFinancialInfoForm : function(){
		if(this.financialInfoForm==null){
			this.financialInfoForm = Ext.create('Foss.sign.deliverHandler.FinancialInfoForm');
		}
		return this.financialInfoForm;
	},
	wateDetailGrid : null,// 待处理模块
	getWateDetailGrid :function(){
		var me = this;
		if(this.wateDetailGrid==null){
			this.wateDetailGrid = Ext.create('Foss.sign.deliverHandler.WateDetailGrid',{
				id: 'Foss_sign_deliverHandler_WateDetailGrid_Id',
				columnWidth:.25,
				listeners: {
					// 待处理----运单号的点击事件
					itemclick:  function(dv, record, item, index, e){
						var view = this.getView();
						Ext.Ajax.request({
						    url:sign.realPath('queryFinanceSign.action'),
						    method:'POST',
					    	params: {// 运单号
					    		'deliverbillDetailVo.deliverbillDetailDto.waybillNo': record.get('waybillNo'),
					    		'deliverbillDetailVo.deliverbillDetailDto.deliverbillNo': sign.deliverHandler.deliverbillNo,
					    		'deliverbillDetailVo.deliverbillDetailDto.arrivesheetNo': record.get('arrivesheetNo')
						    },
						    success: function(response){
								var result = Ext.decode(response.responseText),
									form = me.getSignInfoForm(),
									baseForm = form.getForm(),
									financialInfoForm = me.getFinancialInfoForm(),
									baseFinancialInfoForm = financialInfoForm.getForm(),
									serialNosGrid = Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID');
									//此运单有返回类型时，弹出提醒框
									if(result.deliverbillDetailVo.financialDto.returnbillType!='NONE' &&
										!Ext.isEmpty(result.deliverbillDetailVo.financialDto.returnbillType)){
										Ext.Msg.show({
											//title:'Save Changes?',
											msg: sign.deliverHandler.i18n('pkp.sign.queryReturnBill.returnbillTypeNotNone'),
											buttons: Ext.Msg.OK,
											icon: Ext.Msg.WARNING,
											componentCls: 'yellow_button'
										});
									}
									
									sign.deliverHandler.formFieldReset(baseFinancialInfoForm,true);
									serialNosGrid.getSelectionModel().deselectAll();
									serialNosGrid.getStore().removeAll();// 清除所有流水号信息
									Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_noNormalSelectAll_ID').setVisible(false);
								// 如果到达联的信息为空
								if(result.arriveSheetVo.arriveSheet == null){
									Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.arriveSheetInvalid'), 'error', 2000);
									baseForm .reset();
									baseFinancialInfoForm.reset();
									Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);
									return;
								}	
								// 得到财务信息
								var financialModel = Ext.ModelManager.create(result.deliverbillDetailVo.financialDto,
										'Foss.sign.deliverHandler.model.Financial');
								// 得到签收信息
								var signlModel = Ext.ModelManager.create(result.arriveSheetVo.arriveSheet,
										'Foss.sign.deliverHandler.model.SignInfoModel');
								// 加载财务信息
								financialInfoForm.loadRecord(financialModel);
							// ** 添加大客户图标
								if(financialModel.data.receiveBigCustomer == 'Y'){
									baseFinancialInfoForm.findField('receiveCustomerName').
										setFieldLabel('&nbsp;&nbsp;'+sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveCustomerName')+'<span  class="big_Customer_pic_common">');
								}else{
									baseFinancialInfoForm.findField('receiveCustomerName').setFieldLabel('&nbsp;&nbsp;'+sign.deliverHandler.i18n('pkp.sign.deliverHandler.receiveCustomerName'));
								}	 
								// ****
								// 得到付款方式
								sign.deliverHandler.paymentType = result.deliverbillDetailVo.financialDto.paymentType;
								var	arriveSheet = result.arriveSheetVo.arriveSheet,// 到达联信息
									isPdaSign = arriveSheet.isPdaSign,// 得到是否PDA签收字段
									status = arriveSheet.status,// 得到到达联状态
									situation = arriveSheet.situation,// 签收情况
									deliverymanName = arriveSheet.deliverymanName;// 签收人
								// 如果到达联状态为”签收“ 或”拒收“
								Ext.getCmp('Foss.sign.deliverHandler.withTicketFormPanel_ID').hide();
								Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').show();
								if(status=='SIGN' || status=='REFUSE'){
						 			if (situation == 'NORMAL_SIGN') {
						 				view.addRowCls(index, 'passProcess_mark_color');// 变成绿色
						 			}else{
						 				view.addRowCls(index, 'failProcess_mark_color');// 变成红色
						 			}
									form.loadRecord(signlModel);
									baseForm.findField('signTime').setValue(Ext.Date.format(signlModel.get('signTime'),'Y-m-d H:i:s'));
									sign.deliverHandler.isReadOnly(baseFinancialInfoForm,true);
									var isVisible =false;
									if((!Ext.isEmpty(situation)) && situation == 'GOODS_BACK'){// 如果签收情况为货物拉回
										baseForm.findField('pullbackReason').setValue(arriveSheet.signNote);// 得到货物拉回原因
										baseForm.findField('situationGoodsBack').setValue(situation);
									} else{
										isVisible=true;
										baseForm.findField('signNote').setValue(arriveSheet.signNote);// 得到签收备注
										if((!Ext.isEmpty(situation)) && situation == 'PARTIAL_SIGN'){// 如果签收情况为部分签收
											baseForm.findField('situation').setRawValue('部分签收');// 签收情况
										}
									}
									
									if (sign.deliverHandler.PKP_PULLBACK_REASON_DRIVER_LATE == arriveSheet.signNote 
											&& (!Ext.isEmpty(situation)) && situation == 'GOODS_BACK') {
											var date =new Date(arriveSheet.nextDeliverTime);
											baseForm.findField('nextDeliverTime').setValue(date);
											baseForm.findField('nextDeliverTime').setVisible(!isVisible);
									}
									baseForm.findField('situationGoodsBack').setVisible(!isVisible);// 货物拉回
									baseForm.findField('pullbackReason').setVisible(!isVisible);// 拉回原因
									baseForm.findField('nextDeliverTime').setVisible(!isVisible);//再次派送时间 
									baseForm.findField('situation').setVisible(isVisible);// 签收情况
									baseForm.findField('signNote').setVisible(isVisible);// 签收备注
									baseForm.findField('packingResult').setVisible(false);
									baseForm.findField('alittleShort').setVisible(false);
									Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].hide();
									sign.deliverHandler.setFormDisabled(form, true);// 所有控件不可编辑
									Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(true);
									
									// 如果是pda签收
									if(isPdaSign == "Y"){
										if((deliverymanName == null ||deliverymanName == 'N/A')&& status != 'REFUSE'){
										Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(false);
							    			baseForm.findField('deliverymanName').setDisabled(false);// 签收人可编辑
										}
									}
									
									if(situation=='UNNORMAL_GOODSHORT'||(situation=='UNNORMAL_SAMEVOTEODD' &&!Ext.isEmpty(baseForm.findField('alittleShort').getValue()))){
										baseForm.findField('packingResult').setVisible(true);
										baseForm.findField('alittleShort').setVisible(true);
										baseForm.findField('alittleShort').setValue(baseForm.findField('alittleShort').getValue());
										baseForm.findField('packingResult').setValue(baseForm.findField('packingResult').getValue());
										baseForm.findField('packingResult').setDisabled(true);
										baseForm.findField('alittleShort').setDisabled(true);
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
									sign.deliverHandler.COUNT = 0;
									// 记录一下全局的运单号
									sign.deliverHandler.SIGN_RECORD_WAYBILLNO_INDEX=arriveSheet.waybillNo;
									form.loadRecord(signlModel);
									sign.deliverHandler.setFormDisabled(form, false);// 所有控件可编辑
									Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_confirmButton_Id').setDisabled(false);
									// form.query('button')[0].setDisabled(false);//签收确认按钮可编辑
									baseForm.findField('pullbackReason').setVisible(false);// 拉回原因不可见
									baseForm.findField('nextDeliverTime').setVisible(false); //再次送货不可见
									baseForm.findField('situationGoodsBack').setVisible(false);// 货物拉回不可见
									baseForm.findField('packingResult').setVisible(false);
									baseForm.findField('alittleShort').setVisible(false);
									baseForm.findField('situation').setVisible(true);// 签收情况可见
									baseForm.findField('signNote').setVisible(true);// 签收备注可见
									Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID').columns[2].hide();
									// 加载签收件信息到GridPanel里 --添加了空判断
									if (result.deliverbillDetailVo.signDetailEntitys!=null) {
										serialNosGrid.getStore().loadData(result.deliverbillDetailVo.signDetailEntitys);
									}
									
									sign.deliverHandler.SIGN_SERIAL_NO_ARR = serialNosGrid.getStore().collect('serialNo');
									sign.deliverHandler.isReadOnly(baseFinancialInfoForm,false);
									// 签收情况默认选中“正常签收”
									baseForm.findField('situation').setValue('NORMAL_SIGN');
									// 签收状态默认选中“全部签收”
									baseForm.findField('signStatus').setValue(sign.deliverHandler.SIGN_STATUS_ALL);
									// 签收备注 默认为“正常签收”
									baseForm.findField('signNote').setValue(sign.deliverHandler.i18n('pkp.sign.deliverHandler.normalSign'));// 正常签收
									if(signlModel.get('signTime')!= null){
										// 签收时间默认为当前系统时间
										baseForm.findField('signTime').setValue(Ext.Date.format(signlModel.get('signTime'),'Y-m-d H:i:s'));
									}else {
										// 签收时间默认为当前时间
										baseForm.findField('signTime').setValue(Ext.Date.format(new Date(),'Y-m-d H:i:s'));
									}
									// 签收件数=到达联件数
									baseForm.findField('signGoodsQty').setValue(baseForm.findField('arriveSheetGoodsQty').getValue());
									baseForm.findField('signGoodsQty').setDisabled(true);// 签收件数不可编辑
									if(baseFinancialInfoForm.findField('paymentType').getValue() == sign.deliverHandler.paymentTypeCH){// 现金
										// 款项确认编号不可编辑
										baseFinancialInfoForm.findField('claimNo').setReadOnly(true);
									}
									baseForm.findField('startSerialNo').setValue('');
									baseForm.findField('endSerialNo').setValue('');
																}
						    }
						});
					}
				}
			});
		}
		return this.wateDetailGrid;
	},
	signInfoForm : null,// 签收信息
	getSignInfoForm : function(){
		if(this.signInfoForm==null){
			this.signInfoForm = Ext.create('Foss.sign.deliverHandler.SignInfoForm');
		}
		return this.signInfoForm;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [me.getWateDetailGrid(),{
			border: 1,
			xtype:'container',
			columnWidth:.75,
			items : [
				me.getFinancialInfoForm(),me.getSignInfoForm()
			]
		}];
		me.callParent([cfg]);
	}
});
			
// 查询条件
Ext.define('Foss.sign.deliverHandler.QueryPanel', {
		extend:'Ext.form.Panel',
		id:'Foss_sign_deliverHandler_QueryPanel_Id',
		// 指定容器的标题
		title: sign.deliverHandler.i18n('pkp.sign.deliverHandler.queryBuilder'),// 查询条件
		frame:true,
		// 收缩
		collapsible: true,
		// 动画收缩
		animCollapse: true, 
		bodyCls: 'autoHeight',
		cls: 'autoHeight',
		// 默认边距 间隔
		defaults: {
		margin: '5 10 5 10',  // 四边距 上右下左
		labelWidth: 95
		},	
		layout: 'column',
		defaultType: 'textfield',
		// 定义容器中的项
		items: [{
			name: 'deliverbillNo',
			fieldLabel:sign.deliverHandler.i18n('pkp.sign.deliverHandler.deliverbillNo'),// 派送单号
			align:'center',
			allowBlank:false,
			columnWidth:.33
		},{
			xtype:'button',
			margin: '5 10 5 30',  // 四边距 上右下左
			text:sign.deliverHandler.i18n('pkp.sign.deliverHandler.search'),// 查询
			disabled:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexquerybutton'),
			hidden:!sign.deliverHandler.isPermission('deliverhandlerindex/deliverhandlerindexquerybutton'),
			cls:'yellow_button',
			columnWidth:.11,
			handler:function(){
				var queryForm = this.up('form').getForm().getValues(),
					signForm = Ext.getCmp('Foss_sign_deliverHandler_SignInfoForm_Id'),
					FinancialInfoForm =Ext.getCmp('Foss_sign_deliverHandler_FinancialInfoForm_Id').getForm(),
					WateDetailGrid =Ext.getCmp('Foss_sign_deliverHandler_WateDetailGrid_Id'),
					SerialNoOutStorageGridPanel =Ext.getCmp('Foss_sign_deliverHandler_SerialNoOutStorageGridPanel_ID'),
					signBaseForm = signForm.getForm();
				// 签收信息重置
    			signBaseForm.reset();
    			sign.deliverHandler.setFormDisabled(signForm,true);
    			signBaseForm.findField('pullbackReason').setVisible(false);// 拉回原因不可见
    			signBaseForm.findField('signNote').setVisible(true);// 签收备注可见
    			// 财务信息重置
    			FinancialInfoForm.reset();
    			sign.deliverHandler.isReadOnly(FinancialInfoForm,true);
    			WateDetailGrid.getStore().removeAll();
    			SerialNoOutStorageGridPanel.getSelectionModel().deselectAll();
    			// 清除签收件模块下的信息
				SerialNoOutStorageGridPanel.getStore().removeAll();// 清除所有流水号信息
				if(!this.up('form').getForm().isValid()){
						Ext.ux.Toast.msg(sign.deliverHandler.i18n('pkp.sign.deliverHandler.tip'),sign.deliverHandler.i18n('pkp.sign.deliverHandler.queryNotNull'),'error',1000);// 查询条件不能为空！
    					return;
    			}
    			// 得到派送单编号
    			sign.deliverHandler.deliverbillNo = queryForm.deliverbillNo;
    			// 加载待处理模块下的信息
				WateDetailGrid.getStore().load();
			}
		}]
	});



Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Foss.sign.deliverHandler.QueryPanel'); 
	var bigDownPanel = Ext.create('Foss.sign.deliverHandler.BigDownPanel'); 
	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-deliverHandlerIndex_content',
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'auto', 
		getQueryPanel: function(){
			return queryPanel;
		},
		items: [queryPanel,bigDownPanel,{
		
		}],
		renderTo: 'T_sign-deliverHandlerIndex-body'
	});
});