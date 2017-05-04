/**
 * @author foss-meiying
 * page:签收偏线空运货物
 */
//运单开单件数
sign.airAgencySign.goodsQtyTotal = null;
sign.airAgencySign.PKP_CREDENTIAL_TYPE = 'PKP_CREDENTIAL_TYPE'; // 证件类型词条
sign.airAgencySign.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';//签收情况词条
sign.airAgencySign.PAYMENTMODE = 'SETTLEMENT__PAYMENT_TYPE'; // 付款方式词条
sign.airAgencySign.PICKUPGOODSAIR = 'PICKUPGOODSAIR'; // 派送方式词条
sign.airAgencySign.transportType = 'TRANS_TYPE';//运输方式词条
(function(){
	sign.airAgencySign.situationStore =FossDataDictionary.getDataDictionaryStore(sign.airAgencySign.PKP_SIGN_SITUATION, null);
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','GOODS_BACK'));//移除货物拉回
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','UNNORMAL_SIGN'));//移除异常签收
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.airAgencySign.situationStore.removeAt(sign.airAgencySign.situationStore.find('valueCode','UNNORMAL_SAMEVOTEODD'));//移除同票多类异常
	
})();
//运输性质集合
Ext.define('Foss.sign.airAgencySign.TransportListStore',{
	extend: 'Ext.data.Store',
	fields: [
		{name: 'code',  type: 'string'},
		{name: 'name',  type: 'string'}
	],
	data: {
		'items':[
			{'code':'PLF','name':sign.airAgencySign.i18n('pkp.sign.airAgencySign.productCode.PLF')},//汽运偏线
			{'code':'AF','name':sign.airAgencySign.i18n('pkp.sign.airAgencySign.productCode.AF')}//精准空运
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
Ext.define('Foss.sign.airAgencySign.model.WayBillInfoModel', {
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
			if(record.get('transportType')=='TRANS_VEHICLE'){//汽运
				var v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSHIGHWAYS');
				if(Ext.isEmpty(v) || value == v){
					v = FossDataDictionary.rendererSubmitToDisplay(value, 'PICKUPGOODSSPECIALDELIVERYTYPE');
				}
				return v;			
			}else{//空运
				return FossDataDictionary.rendererSubmitToDisplay(value, sign.airAgencySign.PICKUPGOODSAIR);
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
		{name: 'productCode'}// 运输性质
    ]
});
//待处理表格Model
Ext.define('Foss.sign.airAgencySign.model.WateDetailModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'waybillNo'},// 运单号
        {name: 'externalBillNo'}//外发单号
    ]
});
//待处理表格Store
Ext.define('Foss.sign.airAgencySign.WateDetailStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.sign.airAgencySign.model.WateDetailModel',
	//定义一个代理对象
	proxy: {
		//代理的类型为内存代理
		type: 'ajax',
		timeout: 300000,
		actionMethods : 'POST',
		url:sign.realPath('queryAirAgencySignByParams.action'),
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
			var queryParams=Ext.getCmp('T_sign-airAgencySignIndex_content').getQueryForm().getValues();
			Ext.apply(operation,{
				params:{
					'airAgencySignVo.airAgencyQueryDto.waybillNo':queryParams.waybillNo,// 运单号
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerName':queryParams.receiveCustomerName,// 收货人(收货客户名称)
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerPhone':queryParams.receiveCustomerPhone,//  收货客户电话
					'airAgencySignVo.airAgencyQueryDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,// 收货人手机
					'airAgencySignVo.airAgencyQueryDto.arriveTimeBegin':queryParams.arriveTimeBegin,//  到达时间起
					'airAgencySignVo.airAgencyQueryDto.arriveTimeEnd':queryParams.arriveTimeEnd,// 到达时间止
					'airAgencySignVo.airAgencyQueryDto.productCode':queryParams.productCode// 运输性质
				}
			});
			
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), data.message, 'error', 3000);				
			}
		}
	}
});

//待处理表格
Ext.define('Foss.sign.airAgencySign.WateDetailGrid',{
	extend:'Ext.grid.Panel',
	title:sign.airAgencySign.i18n('pkp.sign.airAgencySign.pending'),//待处理
	//收缩
	collapsible: true,
	emptyText:sign.airAgencySign.i18n('pkp.sign.airAgencySign.emptyText'),//查询结果为空
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
	columns: [
        { 	header: sign.airAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'), 
        	align: 'center',
        	dataIndex: 'waybillNo',
        	flex: 1,
        	renderer: function(value){
	            if (value) {
	                return value;
	            }
	            return null;
	        } 
	    },//单号
        { 
        	header:sign.airAgencySign.i18n('pkp.sign.airAgencySign.externalBillNo'), //外发单号
        	align: 'center', 
        	dataIndex: 'externalBillNo', 
        	width : 120,
        	renderer: function(value){
	            if (value) {
	                return Ext.String.format('<a href="javascript:void(0)">{0}</a>',value);
	            }
	            return null;
	        } 
        }
    ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.airAgencySign.WateDetailStore');
		me.callParent([cfg]);
	}
});
//签收信息
Ext.define('Foss.sign.airAgencySign.SignInfoForm',{
	extend: 'Ext.form.Panel',
	title : sign.airAgencySign.i18n('pkp.sign.airAgencySign.signForInformation'), //签收信息
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
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.deliverymanName'),//提货人名称
			allowBlank: false,
			maxLength:50,
			columnWidth: .33
		},{
			xtype: 'combobox',
			name:'identifyType',
			fieldLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.identifyType'),//证件
			columnWidth: .33,
			value:'',
			valueField:'valueCode', 
			displayField: 'valueName',
			editable:false,//不可编辑
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			queryMode:'local',
			allowBlank: false,
			triggerAction:'all',
			store : FossDataDictionary.getDataDictionaryStore(sign.airAgencySign.PKP_CREDENTIAL_TYPE, null),
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
			fieldLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.identifyCode'),//证件号码
			allowBlank: false,
			columnWidth: .33
		},{
			xtype: 'combobox',
			name:'signSituation',
			fieldLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.signSituation'),//签收情况
			columnWidth: .33,
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			allowBlank: false,
			editable:false,//不可编辑
			valueField:'valueCode', 
			displayField: 'valueName',
			queryMode:'local',
			triggerAction:'all',
			store : sign.airAgencySign.situationStore,
			listeners : {
		    	'select' : function(combo, records, eOpts){
		    		var form = this.up('form').getForm(),
		    			record = form.getRecord();
		    		form.findField('signNote').setValue(null);
		    		// 签收情况选择为部分签收时，签收件数显示为运单开单件数且可编辑
		    		if(records[0].get('valueCode') == 'PARTIAL_SIGN'){
		    			form.findField('signGoodsQty').setReadOnly(false);
		    		}else{
		    			form.findField('signGoodsQty').setValue(sign.airAgencySign.goodsQtyTotal);
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
			fieldLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.signGoodsQty'),//签收件数
			columnWidth: .33,
			allowDecimals : false,//不允许有小数点
		    minValue: 1
		},{
			xtype: 'datetimefield_date97',
			editable : true,
			format : 'Y-m-d H:i:s',
			id: 'Foss_sign_airAgencySign-QueryPanel-signTimeEnd',
			fieldLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.signTime'),//签收时间
			allowBlank:false,
//			value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),  
			name: 'signTime',
			columnWidth: .33,
			time : true,
			dateConfig: {
				el : 'Foss_sign_airAgencySign-QueryPanel-signTimeEnd-inputEl',
				minDate: '%y-%M-%d 00:00:00',
				maxDate:'%y-%M-%d 23:59:59'
			}
		},{
			name:'signNote',
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.signNote'),//签收备注
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
				text:sign.airAgencySign.i18n('pkp.sign.airAgencySign.submit'),//提交
				cls:'yellow_button',
				disabled:!sign.airAgencySign.isPermission('airagencysignindex/airagencysignindexsignbutton'),
				hidden:!sign.airAgencySign.isPermission('airagencysignindex/airagencysignindexsignbutton'),
				columnWidth:.11,
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
				}),
				id:'Foss_sign_airAgencySign_SignInfoForm_submit_id',
				align : 'right',
				handler:function(){
					var form = this.up('form').getForm(),
					    WateDetailGrid = Ext.getCmp('T_sign-airAgencySignIndex_content').getBigDownPanel().getWateDetailGrid(),
					    selectRow = WateDetailGrid.getSelectionModel().getSelection();
					if (selectRow.length == 0){
						Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.airAgencySign.i18n('pkp.sign.airAgencySign.selectOne'),'error',1000);//请选择一行！
					}else if (!form.isValid()){
						
						Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.airAgencySign.i18n('pkp.sign.airAgencySign.notcompleteInfo'),'error',2000);//部分信息未填写完整,请确认！
						return;
					}else{
						// 如果签收情况为部分签收，并且签收件数大于运单开单件数，系统弹窗提示错误
						if(form.findField('signSituation').getValue() == 'PARTIAL_SIGN'&&form.findField('signGoodsQty').
								getValue() >= sign.airAgencySign.goodsQtyTotal){
							Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'),sign.airAgencySign.i18n('pkp.sign.airAgencySign.signGoodsQtyNotGrade'),'error',2000);
							return;
						}	
						Ext.Msg.confirm(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.airAgencySign.i18n('pkp.sign.airAgencySign.okTosubmit'), function(btn,text){
							if(btn == 'yes'){
								var  signForm = Ext.getCmp('Foss_sign_airAgencySign_SignInfoForm_Id').getForm().getValues();
								var  wayBillValue = Ext.getCmp('Foss_sign_airAgencySign_wayBillInfoForm_id').getForm(),
								    wayBillForm = wayBillValue.getRecord(),
								    lineSignDto = '';
								lineSignDto = {'waybillNo' : wayBillForm.data.waybillNo,//运单号
										'productType' : wayBillForm.data.productCode,//运输性质
										'isWholeVehicle' : wayBillForm.data.isWholeVehicle,//是否整车运单 
										'signSituation' : form.findField('signSituation').getValue()//add by 353654 签收情况
										};
								signForm.signTime = Ext.Date.parse(signForm.signTime, "Y-m-d H:i:s", true);
								Ext.Ajax.request({
								    url:sign.realPath('addWaybillSignResult.action'),
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
								    	Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'),json.message,'ok',4000);
								    	form.reset(); 
								    	wayBillValue.reset();
								    },exception: function(response){
										var json = Ext.decode(response.responseText);
				              			Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.commitFailed'), json.message, 'error', 3000);
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
			serviceTime = form.findField('Foss_sign_airAgencySign-QueryPanel-signTimeEnd'),  //服务器当前时间
			signNote = form.findField('signNote');
		var value = signSituation.store.getAt(sign.airAgencySign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueCode');//签收情况 code
		var name = signSituation.store.getAt(sign.airAgencySign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueName');//签收情况的name
		signSituation.setValue(value);//签收情况默认选中“全部签收”
		signGoodsQty.setValue(sign.airAgencySign.goodsQtyTotal);//签收件数默认为运单开单件数
		signGoodsQty.setReadOnly(true);
		serviceTime.setValue(sign.airAgencySign.serviceTime);//服务器当前时间
		signNote.setValue(name);//备注
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.sign.airAgencySign.tipPanel', {
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
Ext.define('Foss.sign.airAgencySign.wayBillInfoForm',{
	extend: 'Ext.form.Panel',
	title:sign.airAgencySign.i18n('pkp.sign.airAgencySign.waybillBasicInformation'),//运单基本信息
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
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerName'),//收货人
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
			tipBodyElement: 'Foss.sign.airAgencySign.tipPanel'
		},
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerAddress'),//提货人地址
		columnWidth: .33
	},{
		name:'receiveCustomerPhone',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerPhone'),//收货人电话
		columnWidth: .33
	},{
		name:'waybillNo',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),//单号
		columnWidth: .33
	},{
		name:'arriveTime',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.arriveTime'),//到达时间
		xtype:'datefield',
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	},{
		name:'stockStatus',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.stockStatus'),//库存状态
		columnWidth: .33
	},{
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.notificationResultName'),//通知成功
		xtype: 'radiogroup',
        vertical: true,
		columnWidth:.33,
		anchorSize: 50,
		layout:'column',
		columns: 1,
		name: 'notificationResultName',
		items: [
            { boxLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.yes'), name: 'notificationResult', inputValue: 'SUCCESS', readOnly : true,  checked: true},//是
			{ xtype: 'container',border : false,columnWidth:.5	,html: '&nbsp;'},
            { boxLabel: sign.airAgencySign.i18n('pkp.sign.airAgencySign.no'), name: 'notificationResult', inputValue: 'FAILURE', readOnly : true}//否
        ]
		
	},{
		name:'notificationTime',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.notificationTime'),//上次通知时间
		xtype:'datefield',
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	},{
		name:'paidMethod',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.paidMethod'),//付款方式
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.airAgencySign.PAYMENTMODE, null)
	
	},{
		name:'toPayAmount',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.toPayAmount'),//到付总额
		columnWidth: .33
	},{
		name:'codAmount',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.codAmount'),//代收货款
		columnWidth: .33
	},{
		name:'transportFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.transportFee'),//运费
		columnWidth: .33
	},{
		name:'deliveryGoodsFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.deliveryGoodsFee'),//送货费
		columnWidth: .33
	},{
		name:'insuranceAmount',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.insuranceAmount'),//货物价值
		columnWidth: .33
	},{
		name:'insuranceFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.insuranceFee'),//保价费
		columnWidth: .33
	},{
		name:'otherFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.otherFee'),//其他费用
		columnWidth: .33
	},{
		name:'receiveMethod',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveMethod'),//派送方式
		columnWidth: .33
	},{
		name:'goodsName',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.goodsName'),//货物名称
		columnWidth: .33
	},{
		name:'goodsQtyTotal',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.goodsQtyTotal'),//件数
		columnWidth: .33
	},{
		name:'goodsWeightTotal',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.goodsWeightTotal'),//重量
		columnWidth: .33
	},{
		name:'goodsVolumeTotal',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.goodsVolumeTotal'),//体积
		columnWidth: .33
	},{
		name:'goodsPackage',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.goodsPackage'),//包装
		columnWidth: .33
	},{
		name:'deliveryCustomerName',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.deliveryCustomerName'),//发货人
		columnWidth: .33
	},{
		name:'deliveryCustomerCityName',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.deliveryCustomerCityName'),//始发站
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
			tipBodyElement: 'Foss.sign.airAgencySign.tipPanel'
		},
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveOrgName'),//始发部门
		columnWidth: .33
	},{
		name:'transportType',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.transportType'),//运输类型
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.airAgencySign.transportType, null)
	},{
		columnWidth: .33,
		xtype:'combobox',
		displayField:'name',
		valueField:'code',
		queryMode:'local',
		triggerAction:'all',
		editable:true,
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.productCode'),//运输性质
		name:'productCode',
		store: Ext.create('Foss.sign.airAgencySign.TransportListStore')
	},{
		xtype:'datefield',
		name:'createTime',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.createTime'),//出发时间
		format : 'Y-m-d H:i:s',
		columnWidth: .33
	}]
});
//偏线外发单表单信息Model
Ext.define('Foss.sign.airAgencySign.model.PartiallineModel', {
	extend: 'Ext.data.Model',
	fields: [
		{name: 'waybillNo',type:'string'},//运单号
		{name: 'externalBillNo',type:'string'},//外发单号			
		{name: 'externalUserName', type: 'string'},//外发员
		{name: 'externalAgencyFee', type: 'float',defaultValue: 0},//外发代理费
		{name: 'deliveryFee', type: 'float',defaultValue: 0},//付送货费
		{name: 'costAmount', type: 'float',defaultValue: 0},//外发成本总额
		{name: 'receiveAgencyFee', type: 'float',defaultValue: 0},//实收代理费
		{name: 'payAgencyFee', type: 'float',defaultValue: 0},//实付代理费
		{name: 'isWriteOff', type: 'string',
			convert: function(value) {
				if (value == 'Y') {
					return sign.airAgencySign.i18n('pkp.sign.airAgencySign.yes');//是
				} else if(value == 'N'){
					return sign.airAgencySign.i18n('pkp.sign.airAgencySign.no');//否
				}else {
					return null;
				}
			}
		},//自动核销申请	
		{name: 'notes', type: 'string'},//备注
		{name: 'transferExternal', type: 'string',
			convert: function(value) {
				if (value == 'Y') {
					return sign.airAgencySign.i18n('pkp.sign.airAgencySign.yes');//是
				} else if(value == 'N'){
					return sign.airAgencySign.i18n('pkp.sign.airAgencySign.no');//否
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
		{name: 'registerUser', type: 'string'},//录入人
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
Ext.define('Foss.sign.airAgencySign.ViewpartiallineFormPanel',{
	extend: 'Ext.form.Panel',
	title:sign.airAgencySign.i18n('pkp.sign.airAgencySign.theViewPartialLineBilled'),//偏线外发单信息
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
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),//运单号
		columnWidth: .33
	},{
		name:'externalBillNo',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.externalBillNo'),//外发单号
		columnWidth: .33
	},{
		name:'externalUserName',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.externalUserName'),//外发员
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'externalAgencyFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.externalAgencyFee'),//外发代理运费
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'deliveryFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.deliveryFee'),//付送货费
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'costAmount',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.costAmount'),//外发成本总额
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'receiveAgencyFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveAgencyFee'),//实收代理费
		columnWidth: .33
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'payAgencyFee',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.payAgencyFee'),//实付代理费
		columnWidth: .33
	},{
		name:'isWriteOff',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.isWriteOff'),//自动核销申请
		columnWidth: .33
	},{
		name:'notes',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.notes'),//备注
		height:60,
		columnWidth: .99
	},{
		name:'transferExternal',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.transferExternal'),//中转外发
		columnWidth: .33
	},{
		name:'currencyCode',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.currencyCode'),//币种
		columnWidth: .33
	},{
		name:'paidMethod',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.paidMethod'),//付款方式
		columnWidth: .33,
		xtype: 'combobox',
		valueField:'valueCode', 
		displayField: 'valueName',
		queryMode:'local',
		triggerAction:'all',
		store : FossDataDictionary.getDataDictionaryStore(sign.airAgencySign.PAYMENTMODE, null)
	
	},{
		xtype: 'numberfield',
		hideTrigger: true,
		name:'toPayAmount',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.toPayAmount'),//到付金额
		columnWidth: .33
	},{
		name:'agentCompanyName',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.agentCompanyName'),//外发代理
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
			tipBodyElement: 'Foss.sign.airAgencySign.tipPanel'
		},
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.agentDeptName'),//到达网点
		columnWidth: .33
	},{
		name:'contactPhone',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.contactPhone'),//到达网点电话
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
			tipBodyElement: 'Foss.sign.airAgencySign.tipPanel'
		},
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.address'),//到达网点地址
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
			tipBodyElement: 'Foss.sign.airAgencySign.tipPanel'
		},
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.externalOrgName'),//外发部门
		columnWidth: .33
	},{
		name:'registerUser',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.registerUser'),//录入人
		columnWidth: .33
	},{
		xtype: 'datefield',
		fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.registerTime'),//录入日期
		name: 'registerTime',
		format:'Y-m-d H:i:s',
		readOnly:true,
		columnWidth:.33	
	}]
});
//偏线外发单表单panel
sign.airAgencySign.ViewpartiallineFormPanel = Ext.create('Foss.sign.airAgencySign.ViewpartiallineFormPanel');

//查看偏线外发单表单
Ext.define('Foss.sign.airAgencySign.Viewpartialline', {
	extend : 'Ext.window.Window',
	//id:'Foss_sign_airAgencySign_Viewpartialline_ID',
	title: sign.airAgencySign.i18n('pkp.sign.airAgencySign.theViewPartialLineBilled'),//查看偏线外发单
	width: 840,	//宽度
	//bodyCls: 'autoHeight',//高度自适应
	layout: 'auto',	 
	closeAction : 'hide',
	resizable : false,
	modal : 'true',
	items : [sign.airAgencySign.ViewpartiallineFormPanel],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);			
			me.callParent([cfg]);
	}	
});
//偏线外发单表单window
sign.airAgencySign.ViewpartiallineWindow = Ext.create('Foss.sign.airAgencySign.Viewpartialline');
//下面大panel
Ext.define('Foss.sign.airAgencySign.BigDownPanel',{
	extend:'Ext.panel.Panel',
	bodyCls: 'autoHeight',
	cls: 'autoHeight',
	layout: 'column',
	WateDetailGrid : null,
	getWateDetailGrid :function(){
		var me = this;
		if(this.WateDetailGrid==null){
			this.WateDetailGrid = Ext.create('Foss.sign.airAgencySign.WateDetailGrid',{
				columnWidth:.25,
				id:'Foss_sign_airAgencySign_WateDetailGrid_ID',
				listeners: {
					cellclick:  function(table, td, cellIndex, record, tr, rowIndex, e, eOpts){
						if(cellIndex==0){
							var wayBillInfoForm = me.getWayBillInfoForm();
							       signInfoForm = me.getSignInfoForm();
							       signInfoForm.getForm().reset();//重置签收信息
							       wayBillInfoForm.getForm().reset();//重置运单基本信息
							Ext.Ajax.request({
							    url:sign.realPath('queryByWaybillNo.action'),
							    params: {
							    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo')
							    },
							    success: function(response){
							    	signInfoForm.query('button')[0].setDisabled(false);//设置提交按钮可编辑
							    	var result = Ext.decode(response.responseText);
							    	//得到运单基本信息
									var wayBillInfoModel = Ext.ModelManager.create(result.airAgencySignVo.waybillDto,
											 'Foss.sign.airAgencySign.model.WayBillInfoModel');
									//加载运单基本信息
									wayBillInfoForm.loadRecord(wayBillInfoModel);
									//运单开单件数
									sign.airAgencySign.goodsQtyTotal=result.airAgencySignVo.waybillDto.goodsQtyTotal;
									//服务器当前时间
									sign.airAgencySign.serviceTime=result.airAgencySignVo.waybillDto.serviceTime;
									//库存状态为已出库
									wayBillInfoForm.getForm().findField('stockStatus').setValue(sign.airAgencySign.i18n('pkp.sign.airAgencySign.outOfStock'));//已出库
									//加载签收信息
									signInfoForm.initData();
							    },exception: function(response){
							    	signInfoForm.query('button')[0].setDisabled(true);//设置提交按钮不可编辑
						    		//运单开单件数
									sign.airAgencySign.goodsQtyTotal = 0;
									//服务器当前时间,如果异常,就用Browser所在电脑的系统时间
									sign.airAgencySign.serviceTime=Ext.Date.format(new Date(),'Y-m-d H:i:s');
									//库存状态为""
									wayBillInfoForm.getForm().findField('stockStatus').setValue("");
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), json.message, 'error', 3000);
								}
							});
						}else{
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
									    url:sign.realPath('queryExternalBillByWaybillNo.action'),
									    params: {
									    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo')
									    },
									    success: function(response){
									    	var result = Ext.decode(response.responseText);
									    	//得到偏线外发单表单基本信息
											var partiallineModel = Ext.ModelManager.create(result.airAgencySignVo.externalBillInfoDto,
													 'Foss.sign.airAgencySign.model.PartiallineModel');
											//加载运单基本信息
											sign.airAgencySign.ViewpartiallineFormPanel.loadRecord(partiallineModel);
											sign.airAgencySign.ViewpartiallineWindow.show();
											
									    }
									});
									Ext.Ajax.request({
										timeout: 60000,
									    url:sign.realPath('queryByWaybillNo.action'),
									    params: {
									    	'airAgencySignVo.airAgencyQueryDto.waybillNo': record.get('waybillNo')
									    },
									    success: function(response){
									    	signInfoForm.query('button')[0].setDisabled(false);//设置提交按钮可编辑
									    	var result = Ext.decode(response.responseText);
									    	//得到运单基本信息
											var wayBillInfoModel = Ext.ModelManager.create(result.airAgencySignVo.waybillDto,
													 'Foss.sign.airAgencySign.model.WayBillInfoModel');
											//加载运单基本信息
											wayBillInfoForm.loadRecord(wayBillInfoModel);
											//运单开单件数
											sign.airAgencySign.goodsQtyTotal=result.airAgencySignVo.waybillDto.goodsQtyTotal;
											//服务器当前时间
									        sign.airAgencySign.serviceTime=result.airAgencySignVo.waybillDto.serviceTime;
											//库存状态为已出库
											wayBillInfoForm.getForm().findField('stockStatus').setValue(sign.airAgencySign.i18n('pkp.sign.airAgencySign.outOfStock'));//已出库
											//加载签收信息
											signInfoForm.initData();
									    },exception: function(response){
									    	signInfoForm.query('button')[0].setDisabled(true);//设置提交按钮不可编辑
								    		//运单开单件数
											sign.airAgencySign.goodsQtyTotal = 0;
											//服务器当前时间,如果异常,就用Browser所在电脑的系统时间
											sign.airAgencySign.serviceTime=Ext.Date.format(new Date(),'Y-m-d H:i:s');
											//库存状态为""
											wayBillInfoForm.getForm().findField('stockStatus').setValue("");
											var json = Ext.decode(response.responseText);
					              			Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), json.message, 'error', 3000);
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
			this.signInfoForm = Ext.create('Foss.sign.airAgencySign.SignInfoForm',{
				id:'Foss_sign_airAgencySign_SignInfoForm_Id'
			});
		}
		return this.signInfoForm;
	},
	wayBillInfoForm : null,
	getWayBillInfoForm : function(){
		if(this.wayBillInfoForm==null){
			this.wayBillInfoForm = Ext.create('Foss.sign.airAgencySign.wayBillInfoForm',{
				id:'Foss_sign_airAgencySign_wayBillInfoForm_id'
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
Ext.define('Foss.sign.airAgencySign.QueryPanel', {
		extend:'Ext.form.Panel',
		// 指定容器的标题
		title: sign.airAgencySign.i18n('pkp.sign.airAgencySign.queryBuilder'),//查询条件
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
			name: 'waybillNo',//单号
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.waybillNo'),
			vtype: 'waybill',
			columnWidth: .25
		},{
			name: 'receiveCustomerName',//收货人
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerName'),
			columnWidth: .25
		},{
			name:'receiveCustomerPhone',//收货人电话
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerPhone'),
			columnWidth: .25
		},{
			name:'receiveCustomerMobilephone',//收货人手机
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.receiveCustomerMobilephone'),
			columnWidth: .25
		},{
			xtype:'combobox',
			name:'productCode',
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.productCode'),//运输性质
			columnWidth: .25,
			allowBlank: false,
			editable:false,//不可编辑
			forceSelection: true, //只允许从下拉列表中进行选择，不能输入文本
			valueField:'code', 
			displayField: 'name',
			queryMode:'local',
			triggerAction:'all',
			store: Ext.create('Foss.sign.airAgencySign.TransportListStore')
		},{
			xtype: 'rangeDateField',
			fieldLabel:sign.airAgencySign.i18n('pkp.sign.airAgencySign.arriveTime'),//到达时间
			fieldId:'Foss_sign_QueryPanel_rangeDateField_ID',
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
			columnWidth: .50
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			defaultType:'button',
			layout:'column',
			items:[{
				text:sign.airAgencySign.i18n('pkp.sign.airAgencySign.reset'),//重置
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
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:sign.airAgencySign.i18n('pkp.sign.airAgencySign.search'),//查询
			cls:'yellow_button',
			disabled:!sign.airAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			hidden:!sign.airAgencySign.isPermission('airagencysignindex/airagencysignindexquerybutton'),
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm(),
					signInfoForm = Ext.getCmp('Foss_sign_airAgencySign_SignInfoForm_Id').getForm(),
				    waybillInfoForm = Ext.getCmp('Foss_sign_airAgencySign_wayBillInfoForm_id').getForm();
				// 到达时间验证
				var arriveTimeBegin = form.getValues().arriveTimeBegin, arriveTimeEnd = form.getValues().arriveTimeEnd;
				if (!Ext.isEmpty(arriveTimeBegin) && !Ext.isEmpty(arriveTimeEnd)) {	
					var result = Ext.Date.parse(arriveTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(arriveTimeBegin,'Y-m-d H:i:s');	
					if(result / (24 * 60 * 60 * 1000) >= 2){	
						Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.airAgencySign.i18n('pkp.sign.airAgencySign.the.date.range.cannot.be.more.than.2.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
						return;	
					}	
				}else {//如果到达时间为空
					Ext.ux.Toast.msg(sign.airAgencySign.i18n('pkp.sign.airAgencySign.tip'), sign.airAgencySign.i18n('pkp.sign.airAgencySign.arriveTimeNotNull'), 'error', 3000);
						return;
				}
				 if (!form.isValid()){
					return;
				}
				signInfoForm.reset();//签收信息重置
			    waybillInfoForm.reset();//运单基本信息重置
				sign.airAgencySign.goodsQtyTotal = null;
				var vateDetailGrid = Ext.getCmp('Foss_sign_airAgencySign_WateDetailGrid_ID').getStore();
					vateDetailGrid.removeAll();
					vateDetailGrid.load();
			}
		}]
	}]
	});
Ext.onReady(function() {
	Ext.QuickTips.init();
	var queryPanel = Ext.create('Foss.sign.airAgencySign.QueryPanel'); 
	var bigDownPanel = Ext.create('Foss.sign.airAgencySign.BigDownPanel'); 

	Ext.create('Ext.panel.Panel',{
		id: 'T_sign-airAgencySignIndex_content',
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
		renderTo: 'T_sign-airAgencySignIndex-body'
	});
});