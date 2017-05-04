sign.expSign.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';//签收情况词条
sign.expSign.PKP_SIGN_STATUS = 'PKP_SIGN_STATUS';//签收状态词条
sign.expSign.SIGN_STATUS_PARTIAL = 'SIGN_STATUS_PARTIAL';//签收状态为部分签收
sign.expSign.PKP_SIGN_PERSON_TYPE='PKP_SIGN_PERSON_TYPE';//签收人类型
sign.expSign.GLOBAL_RECEIVE_CUSTOMER_CONTACT=null;
sign.expSign.SIGN_SERIAL_NO_ARR =null;
sign.expSign.SIGN_RECORD_SERINALNO_INDEX=null;
sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX=null;
sign.expSign.COUNT=null;
sign.expSign.IS_DELIVERY_YES = 'Y'; //是快递员派送 
sign.expSign.IS_DELIVERY_NO = 'N';  //不是快递员派送
sign.expSign.CZM_SIGN_STATUS = 'CZM_SIGN_STATUS';//子母件签收状态 231438
sign.expSign.CZM_SIGN_STATUS_PARTIAL = 'CZM_SIGN_STATUS_PARTIAL';//子母件部分签收
sign.expSign.CZM_SIGN_LIMIT_CODE = 'CZM_SIGN_PARAM';//子母件签收限制票数 配置参数code
sign.expSign.CZM_SIGN_LIMIT = null;  //子母件签收限制票数值
(function(){
	sign.expSign.situationStore =FossDataDictionary.getDataDictionaryStore(sign.expSign.PKP_SIGN_SITUATION, null);
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','GOODS_BACK'));
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','UNNORMAL_SIGN'));
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.expSign.situationStore.removeAt(sign.expSign.situationStore.find('valueCode','PARTIAL_SIGN'));//移除部分签收
	
	sign.expSign.signStatusStore =FossDataDictionary.getDataDictionaryStore(sign.expSign.PKP_SIGN_STATUS, null);
	sign.expSign.signStatusStore.removeAt(sign.expSign.signStatusStore.find('valueCode','SIGN_STATUS_NO'));//移除未签收
	
	sign.expSign.signTypeStore =FossDataDictionary.getDataDictionaryStore(sign.expSign.PKP_SIGN_PERSON_TYPE, null);
	sign.expSign.exsituationStore=FossDataDictionary.getDataDictionaryStore(sign.expSign.PKP_SIGN_SITUATION,
			null,null,['UNNORMAL_BREAK', 'UNNORMAL_DAMP', 'UNNORMAL_POLLUTION','UNNORMAL_GOODSHORT','UNNORMAL_ELSE']);
	
	sign.expSign.czmSignStatusStore =FossDataDictionary.getDataDictionaryStore(sign.expSign.CZM_SIGN_STATUS,null);  //拿数据字典的子母件签收状态 231438
	sign.expSign.czmSituationStore =FossDataDictionary.getDataDictionaryStore(sign.expSign.PKP_SIGN_SITUATION, null);  //子母件签收情况
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','GOODS_BACK'));
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','UNNORMAL_SIGN'));
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','PARTIAL_SIGN'));//移除部分签收
	sign.expSign.czmSituationStore.removeAt(sign.expSign.czmSituationStore.find('valueCode','UNNORMAL_SAMEVOTEODD'));//移除同票多类异常
	
	sign.expSign.queryCzmSignLimit = function(){
		Ext.Ajax.request({
			url:sign.realPath('queryCzmSignLimit.action'),
			method:'POST',
            timeout:100000,
            params:{
            	'signVo.czmSignDto.czmSignDataCode':sign.expSign.CZM_SIGN_LIMIT_CODE
            },
            success: function(response) {
            	var result = Ext.decode(response.responseText);
            	var limit = result.signVo.czmSignDto.czmSignLimit;
            	if(null != limit){
            		sign.expSign.CZM_SIGN_LIMIT = limit;
            	}
            }
		});
	};
	sign.expSign.queryCzmSignLimit();
	
})();

//让form里的控件可见　或者不可见
sign.expSign.setPartFormVisible = function(form,visible){
		if(form.findField('packingResult').isVisible()==!visible){
			form.findField('packingResult').setVisible(visible);
		}
		if(form.findField('alittleShort').isVisible()==!visible){
			form.findField('alittleShort').setVisible(visible);
			form.findField('alittleShort').setValue('');
		}
}

//待处理  ---查询Model
Ext.define('Foss.sign.expSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
	    {name: 'id',type: 'string'},// 运单id
		{name: 'waybillNo',type: 'string'},// 运单编号
		{name: 'arrivesheetNo', type: 'string'},// 到达联编号
		{name: 'arriveSheetGoodsQty', type: 'int'},// 到达联件数
		{name: 'deliverymanName', type: 'string',
			convert: function(value) {
				if (value != null && value != 'N/A') {//如果后台传的值不为空,且不为'N/A'
					return value;
				} else{
					return null;
				}
			}
		},// 提货人名称
		{name: 'identifyCode', type: 'string'},// 证件号码
		{name: 'identifyType', type: 'string'},// 证件类型
		{name: 'situation', type: 'string'},// 签收情况
		{name: 'signTime', type: 'date'},// 签收时间
		{name: 'signGoodsQty', type: 'int'},// 件数
		{name: 'stockGoodsQty'},// 库存件数
		{name: 'productCode'},// 运输性质
		{name: 'isWholeVehicle'},// 是否整车运单
		{name: 'signNote', type: 'string'},// 备注
		{name: 'isCurrentOrgCodeReceiveOrgCodeWVH', type: 'string'},// Y表示当前部门是收货部门 
		{name: 'orderNo', type: 'string'},// 订单号
		{name: 'signStatus'},// 签收状态
		{name: 'serviceTime'},//后台服务器当前时间
		{name: 'packingResult'},//外包装是否完好
		{name: 'alittleShort'},//短少量
		{name: 'deliverymanType'},//签收人类型
		{name: 'receiveCustomerContact'}//收货客户联系人名称
	]
});
 
// 签收信息--签收件 流水号Model
Ext.define('Foss.sign.expSerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'goodShorts'}// 是否内货短少
	]
});
//异常流水号选择的model 
Ext.define('Foss.sign.expSignSerialNoWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'waybillNo',type: 'string'},// 运单号
		{name:'isSelect',type:'boolean'}
	]
});
//签收出库同票多类grid对应model
Ext.define('Foss.sign.expSignOutWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'signState'},
		{name: 'choose'},
		{name: 'serialNo'}
	]
});


// 待处理---Store
Ext.define('Foss.sign.expSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.expSignOutStorageModel',
	pageSize: 10,
	proxy : {
		type : 'ajax',
		url:sign.realPath('queryArriveSheetInfoExp.action'),
		actionMethods : 'post',
		timeout: 100000,
		reader : {
			type : 'json', 
			root : 'arriveSheetVo.signArriveSheetDtoList',
			totalProperty : 'totalCount' //总数
		}
	},// 事件监听
	listeners:{
		// 查询事件
		beforeload:function(s,operation,eOpts){
			var form = Ext.getCmp('T_sign-expressSignOutStorageIndex_content').getQueryForm().getForm();
			// 结清时间验证
			var settleTimeStart = form.getValues().settleTimeStart, settleTimeEnd = form.getValues().settleTimeEnd;
			if (!Ext.isEmpty(settleTimeStart) && !Ext.isEmpty(settleTimeEnd)) {	
				var result = Ext.Date.parse(settleTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(settleTimeStart,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 30){	
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), sign.expSign.i18n('pkp.sign.expSign.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}	
			}else {//如果结清时间为空
				Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), sign.expSign.i18n('pkp.sign.expSign.settleTimeNotNull'), 'error', 3000);
					return false;
			}
			if (!form.isValid()){
				return false;
			}
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-expressSignOutStorageIndex_content').getQueryForm().getValues();
			Ext.apply(operation,{
				params:{
					'signVo.signDto.waybillNo':queryParams.waybillNo,// 运单号
					'signVo.signDto.arrivesheetNo':queryParams.arrivesheetNo,// 到达联编号
					'signVo.signDto.receiveCustomerName':queryParams.receiveCustomerName,// 收货人
					'signVo.signDto.receiveCustomerPhone':queryParams.receiveCustomerPhone,// 收货人电话
					'signVo.signDto.receiveCustomerMobilephone':queryParams.receiveCustomerMobilephone,// 收货人手机
					'signVo.signDto.settleTimeStart':queryParams.settleTimeStart,// 结清时间起
					'signVo.signDto.settleTimeEnd':queryParams.settleTimeEnd// 结清时间止
				}
			});
		},
		load: function( store, records, successful, eOpts ){
			var data = store.getProxy().getReader().rawData;
			if(!data.success &&(!data.isException)){
				Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), data.message, 'error', 2000);	//提示			
			}
		}
	}
});
// 签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.ExpSerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.expSerialNoSignOutStorageModel',
	proxy : {
		type : 'ajax',
		url:sign.realPath('querySerialNoExp.action'),
		actionMethods : 'post',//方式
		timeout: 100000,
		reader : {
			type : 'json',
			root : 'stockVo.stockDtoList'
		}
	},
	listeners:{
		load: function(store,records){
			sign.expSign.GlobalCount=store.getCount();
			sign.expSign.SIGN_SERIAL_NO_ARR = store.collect('serialNo');
		}
	}
});
// 签收信息
Ext.define('Foss.sign.expSignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title: sign.expSign.i18n('pkp.sign.expSign.signInformation'), //标题-签收信息
	layout: 'column',//布局
	frame:true,
	id: 'Foss.sign.expSignOutStorageFormPanel_ID',
	defaults: { //默认值
		margin:'5 0 5 10',
		anchor: '98%',
		labelWidth:65
	},
	defaultType : 'textfield',
	items: [{
		name:'isCurrentOrgCodeReceiveOrgCodeWVH',//Y表示当前部门是收货部门 
		xtype:'hiddenfield'
	},{
		xtype:'combo',
		name: 'signStatus',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signStatus'),//到达联签收状态
		labelWidth: 100, 
		forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
		editable:false, //不可编辑
		forceSelection:true,// 必须选择一个。
		valueField:'valueCode',  
		displayField: 'valueName', 
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .45,
		store : sign.expSign.signStatusStore,
		listeners : {
	    	'select' : function(combo, records, eOpts){
	    		var form = this.up('form').getForm(),
	    			record = form.getRecord();
	    		// 签收状态选择为部分签收时，签收件数显示为到达联件数且可编辑
	    		if(records[0].get('valueCode') ==sign.expSign.SIGN_STATUS_PARTIAL){
	    			form.findField('signGoodsQty').setReadOnly(false);
	    		}else{
	    			form.findField('signGoodsQty').setValue(record.get('arriveSheetGoodsQty'));
	    			form.findField('signGoodsQty').setReadOnly(true);
	    		}
	    	}
		}
	},{
		xtype:'combo',
		name: 'situation',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.situation'),//签收情况
		labelWidth: 60, 
		forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
		editable:false, //不可编辑
		forceSelection:true,// 必须选择一个。
		valueField:'valueCode',  
		displayField: 'valueName', 
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .31,
		store : sign.expSign.situationStore,
		listeners : {
	    	'select' : function(combo, records, eOpts){
	    		var form = this.up('form').getForm(),
	    			record = form.getRecord();
	    		var ExpSerialNoGridPanel=Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID');
	    		var UnNormalSelectAll = Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
	    		UnNormalSelectAll.setVisible(false);
	    		ExpSerialNoGridPanel.show();
	    		var ExpWithTicketFormPanel = Ext.getCmp('Foss.sign.expSignwithTicketFormPanel_ID');
	    		ExpWithTicketFormPanel.hide();
	    		//内物短少信息初始为隐藏
	    		sign.expSign.setPartFormVisible(form,false);
	    		//若为正常签收
	    		if(records[0].get('valueCode')=='NORMAL_SIGN'){
	    			ExpSerialNoGridPanel.columns[2].hide();
	    		}//若为同票多类异常签收
	    		else if(records[0].get('valueCode')=='UNNORMAL_SAMEVOTEODD'){
	    			ExpSerialNoGridPanel.hide();
					ExpWithTicketFormPanel.show();
					var SignOutSerialNoWithTicket=Ext.getCmp('Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID');
					var  multiNormalSerNos=Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID'),
						 multiNormalSerNosStore=multiNormalSerNos.getStore();
					multiNormalSerNos.getSelectionModel().deselectAll();
					multiNormalSerNosStore.removeAll();
					if(ExpSerialNoGridPanel.getStore().data.length>0){
						multiNormalSerNosStore.loadData(ExpSerialNoGridPanel.getStore().data.items);
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
					var model=Ext.create('Foss.sign.expSignOutWithTicketStorageModel',{
								signState:'UNNORMAL_GOODSHORT',
								choose:'选择',
								serialNo:''
							});
					var model1=Ext.create('Foss.sign.expSignOutWithTicketStorageModel',{
								signState:'UNNORMAL_BREAK',
								choose:'选择',
								serialNo:''
							});
					SignOutSerialNoWithTicketStore.insert(0,model);
					SignOutSerialNoWithTicketStore.insert(1,model1);
					//内物短少信息初始为显示
					sign.expSign.setPartFormVisible(form,true);
	    		}else{
	    			ExpSerialNoGridPanel.columns[2].show();
	    			ExpSerialNoGridPanel.columns[2].setText('是否'+records[0].get('valueName'));
	    			UnNormalSelectAll.setVisible(true);
	    			UnNormalSelectAll.setFieldLabel('是否'+records[0].get('valueName')+'全选');
	    			UnNormalSelectAll.setValue(false);
	    			//当只有一个流水号时，默认勾选上
	    			var count=ExpSerialNoGridPanel.getStore().data.length;
	    			if(count==1){
	    				ExpSerialNoGridPanel.getSelectionModel().selectAll(true);
	    				ExpSerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
	    				UnNormalSelectAll.setValue(true);
	    			}
	    			var rstValue=records[0].get('valueName');
	    			if(records[0].get('valueCode')=='UNNORMAL_GOODSHORT'){
	    				sign.expSign.setPartFormVisible(form,true);
	    				//并且数据可编辑
	    				form.findField('packingResult').setDisabled(false);
	    				//这种如果要操作items时候 需要把单个控件拿出来操作
	    				form.findField('packingResult').items.items[0].setDisabled(false);
	    				form.findField('packingResult').items.items[1].setDisabled(false);
	    				form.findField('alittleShort').setDisabled(false);
	    			}else{
	    				sign.expSign.COUNT += 1;
	    				if(sign.expSign.COUNT==1){
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
	    		form.findField('signNote').setValue(records[0].get('valueName'));
	    		form.findField('isReturn').setVisible(false);
				form.findField('waybill').setVisible(false);
    			 if(form.findField('situation').getValue() == 'UNNORMAL_ELSE'){//如果签收情况为异常-其他   运单运输性质为快递
					if(form.getRecord().data.productCode!=null &&form.getRecord().data.productCode=='PACKAGE' ){
						form.findField('isReturn').reset();
						form.findField('isReturn').setVisible(true);
					}else{
						
					}
				}
	    	}
		}
	},{
		name: 'signGoodsQty',
		xtype:'numberfield',
		labelWidth: 50, 
		hideTrigger: true,
		allowBlank:false,
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signGoodsQty'),//签收件数
		allowDecimals : false,// 不允许有小数点
	    minValue: 1, //最小值为1
		listeners :{
			'blur':function(me,theEvent,eOpts){
				var form = this.up('form').getForm();
				if(form.findField('signStatus').getValue() ==sign.expSign.SIGN_STATUS_PARTIAL){// 如果签收情况为部分签收
					if(me.value >= form.getRecord().get('arriveSheetGoodsQty')){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.partSignSignGoodsQtyUnqualified'),'error',1000);//部分签收时签收件数必须小于到达联签收件数，请确认！
						this.selectText(); 
					}
				}
				
			}
		},
		columnWidth:.22
	},{ //add by 231438  是否快递员派送：Y；在快递派送处理界面默认为：是
		name: 'isCourierDelivery',
		fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.isCourierSend'),//是否快递员派送:
		xtype:'radiogroup',
		vertical: true,
        allowBlank:false,
		columnWidth:.45,
		anchorSize: 50,
		labelWidth:95,
		layout:'column',
		columns: 1,
		items:[
		       { style : 'margin-top:5px',itemId: 'yes', boxLabel: '是', name: 'isCourierDelivery', inputValue: 'Y',checked: true,id: 'isCourierDeliveryYES'},
		       { style : 'margin-top:5px;margin-left:5px',itemId: 'no', boxLabel: '否', name: 'isCourierDelivery', inputValue: 'N',id :'isCourierDeliveryNO' }
		      ],
		listeners: { 
			change:function(me,theEvent,eOpts){
				var checkValue = me.getChecked()[0].inputValue;
				if( checkValue == sign.expSign.IS_DELIVERY_YES){
					this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(false);
					this.up('form').getForm().findField('sendExpressEmpCode').allowBlank = false;
					this.up('form').getForm().findField('partnerName').setDisabled(true); 	//快递员派送(是) 合作伙伴姓名不可填写(置灰)(yl 268377)
					this.up('form').getForm().findField('partnerName').setValue(''); 	
					this.up('form').getForm().findField('partnerPhone').setDisabled(true);	//快递员派送(是) 合作伙伴手机不可填写(置灰)(yl 268377)
					this.up('form').getForm().findField('partnerPhone').setValue('');	
					this.up('form').getForm().findField('isPartner').setValue(false);		//快递员派送(是) 合作伙伴取消选择(yl 268377)

				}else{
					this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(true);
					//this.up('form').getForm().findField('partnerName').setDisabled(false);	//快递员派送(否) 合作伙伴姓名可以填写(yl 268377)
					//this.up('form').getForm().findField('partnerPhone').setDisabled(false);	//快递员派送(否) 合作伙伴手机可以填写(yl 268377)
					//this.up('form').getForm().findField('isPartner').setValue(true);		//快递员派送(否) 合作伙伴选中(yl 268377)
				}
			}
		}		
	},{//add by 231438  快递员(选择器)
		name:'sendExpressEmpCode',
		fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.courier'),//快递员:
		xtype:'commonExpressemployeeselector',
		labelWidth:60,
		columnWidth:.4,
		maxLength:50
	},{
		name: 'isReturn',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.isReturn'),//因客户要求，重新开单转货/返货
		allowBlank: true,
		labelWidth:200,
		xtype: 'checkbox' ,
		columnWidth:.5,
		handler:function(){
			var form = this.up('form').getForm(),checked = false;
    		if(form.findField('isReturn').checked==true){
    			checked = true;
    			
    		}else {
    		}
    		form.findField('signNote').setValue('');
    		form.findField('waybill').reset();
    		form.findField('signNote').setVisible(!checked);
    		form.findField('waybill').setVisible(checked);
		}
	},{//add 268377 yl 合作伙伴选择
		name: 'isPartner',
		fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.isPartner'),//是否合作伙伴(yl 268377)
		allowBlank: true,
		xtype: 'checkbox' ,
		columnWidth:.25,
		//allowBlank: false,//不允许为空
		handler:function(){
			var form = this.up('form').getForm(),checked = false;
			if(form.findField('isPartner').checked==true){
    			checked = true;
    			this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(true);	//选中伙伴派送快递员不可选(置灰)(yl 268377)
    			this.up('form').getForm().findField('partnerName').setDisabled(false);			//选中伙伴派送伙伴名称显示可填状态(yl 268377)
				this.up('form').getForm().findField('partnerPhone').setDisabled(false);			//选中伙伴派送伙伴手机显示可填状态(yl 268377)
    			Ext.getCmp('isCourierDeliveryNO').setValue(true);								//选中伙伴派送,快递选项为否(yl 268377)
    		}else {
//    			this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(false);	//取消伙伴派送快递员可选(yl 268377)
    			this.up('form').getForm().findField('partnerName').setDisabled(true);			//选中伙伴派送伙伴名称显示不可填状态(yl 268377)
    			this.up('form').getForm().findField('partnerName').setValue('');			
    			this.up('form').getForm().findField('partnerPhone').setDisabled(true);			//选中伙伴派送伙伴手机显示不可填状态(yl 268377)
    			this.up('form').getForm().findField('partnerPhone').setValue('');			
    			//Ext.getCmp('isCourierDeliveryYES').setValue(true);								//取消伙伴派送,快递选项为是(yl 268377)
    		}
		}
		
	},{
		name:'partnerName',
		fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.partnerName'),//伙伴名称(yl 268377)
		xtype:'textfield',
		labelWidth:60,
		hideTrigger: true,
		columnWidth:.35,
		disabled:true,
		maxLenght:5,
		allowBlank: false	//不允许为空
	},{
		name:'partnerPhone',
		fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.partnerPhone'),//伙伴手机(yl 268377)
		xtype:'numberfield',
		labelWidth:60,
		hideTrigger: true,
		columnWidth:.35,
		disabled:true,
		allowBlank: false,	//不允许为空
		allowDecimals:false,
		allowNegative:false,
		minLength:11,
		maxLength:11
	},{
		xtype: 'datetimefield_date97',
		editable : true,
		format : 'Y-m-d H:i:s',
		id: 'Foss_sign_ExpSignOutStorageFormPanel_SignTime_ID',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signTime'),//签收时间
		allowBlank:false,
//		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		columnWidth:.45,
		name: 'signTime',
		time : true,
		dateConfig: { 
			el : 'Foss_sign_ExpSignOutStorageFormPanel_SignTime_ID-inputEl',
			minDate: '%y-%M-%d 00:00:00',
			maxDate:'%y-%M-%d 23:59:59'
		}
	},{
		xtype:'combo',
		name: 'deliverymanType',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.deliverymanType'),//签收人类型
		labelWidth: 80, 
		forceSelection: true, 
		editable:false, 
		forceSelection:true,
		valueField:'valueCode',  
		displayField: 'valueName', 
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .31,
		store :sign.expSign.signTypeStore
	},{
		fieldLabel: sign.expSign.i18n('pkp.sign.settlePayment.packageResult'),//外包装是否完好
		xtype: 'radiogroup',
        vertical: true,
        allowBlank:false,
		columnWidth:.45,
		anchorSize: 50,
		labelWidth:95,
		layout:'column',
		columns: 1,
		name: 'packingResult',
		items: [
            { style : 'margin-top:5px',itemId: 'yes', boxLabel: '是', name: 'packingResult', inputValue: 'Y',checked: true},
            { style : 'margin-top:5px;margin-left:5px',itemId: 'no', boxLabel: '否', name: 'packingResult', inputValue: 'N' }
        ]
	},{
		name:'alittleShort',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.alittleShort'),//短少量
		xtype:'textfield',
		labelWidth:50,
		hideTrigger: true,
		columnWidth:.31,
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
				return sign.expSign.i18n('pkp.sign.expSign.alittleShortMaxLength');
			}else{
				return true;
			}
	},
		listeners: {  
			blur: function(thisCom, eventObject,eOpts) {
				var form = this.up('form').getForm();
				form.findField('signNote').setValue('');
				var alittleShort=form.findField('alittleShort').getValue();
				var rstValue;
				if(!Ext.isEmpty(alittleShort)){
					rstValue=sign.expSign.i18n('pkp.sign.expSign.unnormalGoodshort')+','+sign.expSign.i18n('pkp.sign.expSign.alittleShort')+':'+form.findField('alittleShort').getValue();
				}else{
					rstValue=sign.expSign.i18n('pkp.sign.expSign.unnormalGoodshort');
				}
				form.findField('signNote').setValue(rstValue);
	        }  
	    }
	},{
		labelWidth: 60,
		xtype: 'textarea',
		name:'signNote',
		hight: 25,
		allowBlank:false,
		maxLength:200,
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signNote'),//备注
		columnWidth: 1
	},{
		name: 'waybill',
		xtype:'numberfield',
		labelWidth: 100,
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.newWaybillNo'),//转货\返货新单号
		hideTrigger: true,
	    allowBlank:false,
		allowDecimals : false,// 不允许有小数点
	    minValue: 10000000, //最小值为
	    maxValue: 999999999999999,//最小值为
		columnWidth:.40
	}],
	initData: function(arriveSheetGoodsQty,serviceTime,signName,receiveCustomerContact){
		var form = this.getForm(),
			situation = form.findField('situation'),
			signGoodsQty = form.findField('signGoodsQty'),
			signTime = form.findField('signTime'),
			signNote = form.findField('signNote'),
			isReturn = form.findField('isReturn'),
			unnormalElseNote =form.findField('unnormalElseNote'),
			waybillNo=form.findField('waybill'),
			signStatus =form.findField('signStatus');
		    packingResult = form.findField('packingResult'),
		    alittleShort = form.findField('alittleShort');
			deliverymanType = form.findField('deliverymanType');
		form.reset();
		var value = situation.store.getAt(sign.expSign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueCode');// 签收情况 code
		var name = situation.store.getAt(sign.expSign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueName');// 签收情况的name
		situation.setValue(value);// 签收情况默认选中“部分签收”
	    signStatus.setValue("SIGN_STATUS_ALL");// 签收状态默认选中“全部签收”
		signGoodsQty.setValue(arriveSheetGoodsQty);// 签收件数默认为到达联件数
		signGoodsQty.setReadOnly(true);// 签收件数为不可编辑
		signTime.setValue(serviceTime);
		signNote.setVisible(true);//设置签收备注可见
		signNote.setValue(name);// 备注
		isReturn.setVisible(false);//因客户要求，重新开单转货/返货 复选框不可见
		waybillNo.setVisible(false);//设置输入的运单号不可见
		packingResult.setVisible(false);//设置外包装是否完好可见
		alittleShort.setVisible(false);//设置短少量可见
		Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').columns[2].hide();//是否内物短少隐藏
		sign.expSign.GLOBAL_RECEIVE_CUSTOMER_CONTACT=receiveCustomerContact;
		if(signName==receiveCustomerContact){
			deliverymanType.setValue("SIGN_PERSON_ME");
		}else{
			deliverymanType.setValue("");
		}
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


// 签收流水号GridPanel
Ext.define('Foss.sign.SerialNoOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
	columnLines: true,
	height: 300,//高度
	id: 'Foss_sign_ExpSerialNoOutStorageGridPanel_ID',
	frame: true,
	enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {//取消选中时产生的事件
				var form=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
				var situation=form.findField('situation').getValue();
				var UnNormalSelectAll = Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
				//var count=form.findField('signGoodsQty').getValue();
				//签收情况不为'正常签收'和'同票多类异常'时
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
					Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').getStore().data.items[index].set('goodShorts',false);
					UnNormalSelectAll.setValue(false);
				}
		},
			select: function(model,record,index) {
				var form=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
				var situation=form.findField('situation').getValue();
				var count=Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').getStore().data.length;
				//签收情况不为'正常签收'和'同票多类异常'时
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
					if(count=='1'){
						Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').getStore().data.items[0].set('goodShorts',true);
					}
				}
			}
		}
	}),
    title:sign.expSign.i18n('pkp.sign.expSign.signPieces'),//签收件
	columns: [
        {header: sign.expSign.i18n('pkp.sign.expSign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'},//流水号
        //update begin
        {header: sign.expSign.i18n('pkp.sign.expSign.goodShorts'), dataIndex: 'goodShorts', xtype:'checkcolumn',flex: 1,align: 'center', stopSelection:true,
		listeners:{
						'checkchange': function(model,record,index){
						var ExpSerialNoGridPanel=Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID');
						var state=ExpSerialNoGridPanel.getSelectionModel().isSelected(record);
						var form=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
						var situation=form.findField('situation').getValue();
						var count=ExpSerialNoGridPanel.getStore().data.length;
							//是否内货短少 checkbox 可以操作
							//var count=form.findField('signGoodsQty').getValue();
							if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
							if(count=='1'){
									ExpSerialNoGridPanel.getSelectionModel().selectAll();
									ExpSerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
								}else if(!state){
									ExpSerialNoGridPanel.getStore().data.items[record].set('goodShorts',false);
								}
							}
						}
					}
				}//是否内货短少
        //update end
    ],
    dockedItems : [ {
		xtype : 'toolbar',
		dock : 'top',
		layout : 'column',
		items : [
				{
					name:'startSerialNo',
					fieldLabel:sign.expSign.i18n('pkp.sign.expSign.start'),//从
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
					fieldLabel:sign.expSign.i18n('pkp.sign.expSign.end'),//到
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
					text :sign.expSign.i18n('pkp.sign.expSign.choose'),// 选中
					handler:function(grid, rowIndex, colIndex){
						var startSerialNo =this.up('toolbar').items.items[0].value,
							endSerialNo =this.up('toolbar').items.items[1].value,
							me = this.up('toolbar').up('grid'),
							store = me.getStore(),
							records = store.getRange(),
							choose = [];
						if(Ext.isEmpty(startSerialNo) || Ext.isEmpty(endSerialNo)){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), sign.expSign.i18n('pkp.sign.expSign.rangeMustEnter'), 'error', 4000);//区间必须输入
							return;
						}
						if(startSerialNo>9999 || endSerialNo >9999){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), sign.expSign.i18n('pkp.sign.expSign.largeNumber'), 'error', 4000);//数字最大范围是9999
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
					id : 'Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID',
					hidden :true,
					allowBlank : true,
					listeners : {
						change : function(field , newValue , oldValue , object){
							var StorageGridPanel = Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID');
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
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ExpSerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});


sign.expSign.CellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});
// 同票多类异常里的无异常签收流水号GridPanel
Ext.define('Foss.sign.expSignMultiExceptionNormalSerialNoGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
	columnLines: true,
	height: 230,//高度
	id: 'Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID',
	frame: true,
    enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    title:'无差异流水号',//签收件
	columns: [
        {header: sign.expSign.i18n('pkp.sign.expSign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'}
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.ExpSerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

//同票多类
Ext.define('Foss.sign.expSignMultiExceptionSignPiecesForm',{
	extend: 'Ext.form.Panel',
	title:'签收件',
    defaultType : 'textfield',
	layout: 'column',
	frame:true,
	id: 'Foss.sign.expSignwithTicketFormPanel_ID',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	signOutWithTicketStorageGridPanel :　null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			if(Ext.getCmp('Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID') );
			}
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.expSignOutWithTicketStorageGridPanel',{
				width:528
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},
	multiExceptionNormalSerialNoGridPanel :　null,
	getMultiExceptionNormalSerialNoGridPanel: function(){
		if(this.multiExceptionNormalSerialNoGridPanel == null){
		
			if(Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID') );
			}
			this.multiExceptionNormalSerialNoGridPanel = Ext.create('Foss.sign.expSignMultiExceptionNormalSerialNoGridPanel',{
				width:528
			});
		}
		return this.multiExceptionNormalSerialNoGridPanel;
	},
	listeners : {
		beforerender:function(){
				Ext.getCmp('Foss.sign.expSignwithTicketFormPanel_ID').hide();
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

//gird————

Ext.define('Foss.sign.expSignOutWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    emptyText:sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
	id: 'Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID',
	frame: false,
	selModel: {selType:'cellmodel'},
	//收缩
	//collapsible: true,
   // title:'流水号明细',//流水号明细
	plugins:[sign.expSign.CellEditing],
	viewConfig: {
        forceFit : true, enableTextSelection: true
	},
	columns: [
		{
            xtype:'actioncolumn',
			align: 'center',
			menuDisabled:true,
            text: '删除',//删除
			minWidth:50,maxWidth:50,
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
					multiNormalSerNos=Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore();
					if(!Ext.isEmpty(signState) && signState=='UNNORMAL_GOODSHORT'){
							var form=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
							sign.expSign.setPartFormVisible(form,false);		
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
		
        {header: '签收情况', dataIndex: 'signState',minWidth:110,maxWidth:110,menuDisabled:true,align: 'center',
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
					store : sign.expSign.exsituationStore,
					listeners : {
						'change' : function(field,newValue, oldValue, eOpts){
							var arr = this.up('panel').getStore().collect('signState'),
							    flag = arr.indexOf(newValue),
							    form=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
							if(oldValue=='UNNORMAL_GOODSHORT'&& !Ext.isEmpty(newValue)){
								sign.expSign.setPartFormVisible(form,false);
							}
							if(flag>=0){
								this.setValue('');
								Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), '签收情况已被选择', 'error', 3000);
								return false;
							}
							if(newValue=='UNNORMAL_GOODSHORT'){
								sign.expSign.setPartFormVisible(form,true);
							}
						}
					}
			},
			renderer:function(value){  
                return FossDataDictionary.rendererSubmitToDisplay(value, sign.expSign.PKP_SIGN_SITUATION);  
              }
		},
		{header: '流水号选择', dataIndex: 'choose',/*id:'choose_id'+new Date().getTime(),*/
		renderer:function(value,cellmeta){
			var returnStr = "<input type='button' value='选择'  style='width: 60px'/>";            
			return returnStr;
		},minWidth:80,maxWidth:80,menuDisabled:true,
		listeners:{
			'click': function(model,record,index){
						var value = this.up('panel').getStore().getAt(index).data.signState;
						if(!Ext.isEmpty(value)){
							//当前行的流水号数据
							var serialNo = this.up('panel').getStore().getAt(index).data.serialNo,
							 multiNormalSerNos=Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID'),
							 multiNormalSerNosStore=multiNormalSerNos.getStore(),
							 showSerials=new Array(),
							 chooseModel=new Array();
							var win ;
							var seriaNoGrid ;
							var serialNoOutStorage;
							if(!Ext.getCmp('Foss_sign_expSignWithTicketStoraWindow_ID')){
								win = Ext.create('Foss.sign.expSignWithTicketStoraWindow');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}else{
							    win = Ext.getCmp('Foss_sign_expSignWithTicketStoraWindow_ID');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}
							if(!Ext.isEmpty(serialNo)) {
								var serialNoArray=serialNo.split(',');
								for(var i=0;i<serialNoArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':serialNoArray[i],'isSelect':true});
									chooseModel.push(showSerials);
								}
								
							}
							if(multiNormalSerNosStore.data.length >0){
								for(var i=0;i<multiNormalSerNosStore.data.length;i++){
									showSerials.push(
									{'waybillNo':sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':multiNormalSerNosStore.data.items[i].data.serialNo,'isSelect':false});
								}
							}
							serialNoOutStorage.loadData(showSerials,true);
							sign.expSign.SIGN_RECORD_SERINALNO_INDEX=index;
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
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), '签收情况没有被选择', 'error', 3000);
								return false;
						}
				}		
			},align: 'center'
		},
		{header: '异常流水号', dataIndex: 'serialNo',
		xtype : 'ellipsiscolumn',
		menuDisabled:true,align: 'center',name:'serialNo',minWidth:288,maxWidth:288
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
						var count = sign.expSign.exsituationStore.getCount();
						var panelGridCount = this.up('panel').getStore().getCount();
						if(panelGridCount>=count){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'签收情况已全部选择,不可再添加!','error',1000);
							return false;
						}
						var model=Ext.create('Foss.sign.expSignOutWithTicketStorageModel',{
							signState:'',
							choose:'选择',
							serialNo:''
						});
						var store=this.up('panel').getStore();
						store.insert(0,model);
						sign.expSign.CellEditing.startEditByPosition({row: 0, column: 0});
					}
				}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.expSignOutStateWithTicketStorageStore');
		me.callParent([cfg]);
	}
});
//store
Ext.define('Foss.sign.expSignOutStateWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.expSignOutWithTicketStorageModel',
	listeners:{
	
	}
});
//gird
Ext.define('Foss.sign.expSignSerialNoWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
	height: 300,//高度
	id: 'Foss_sign_expSignSerialNoWithTicketMulStorageGridPanel',
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
		me.store = Ext.create('Foss.sign.expSignSerialNoWithTicketStorageStore');
		me.callParent([cfg]);
	},
	listeners:{
	}
});
//store
Ext.define('Foss.sign.expSignSerialNoWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.expSignSerialNoWithTicketStorageModel'
	});
//window
// 弹出窗口
Ext.define('Foss.sign.expSignWithTicketStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_expSignWithTicketStoraWindow_ID',
	closeAction: 'hide',
	layout: 'auto',	
	width:300,
	resizable : false,
	title:'流水号选择',//流水号选择
	modal: true,
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.expSignSerialNoWithTicketStorageGridPanel',{
				width:270,
				text : sign.expSign.i18n('pkp.sign.expSign.signPieces')//签收件
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
				var SerialNoWithTicket =Ext.getCmp('Foss_sign_expSignSerialNoWithTicketMulStorageGridPanel'),
					serialNos = SerialNoWithTicket.getSelectionModel().getSelection(),
					SerialNoWithTicketStore = SerialNoWithTicket.getStore().data,
					frontGrid=Ext.getCmp('Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID'),
					frontGrid_Store=frontGrid.getStore(),
					multiNormalSerNos=Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore(),
					selectSerialNos="",
					deselectSerialNos =new Array();
				if(serialNos.length<=0){
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), '请选择各类型异常流水号!', 'error', 1000);
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
					frontGrid_Store.data.items[sign.expSign.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',selectSerialNos.substring(0,selectSerialNos.length-1));
				}else{
					frontGrid_Store.data.items[sign.expSign.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',"");
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




// 弹出窗口
//弹出窗口
Ext.define('Foss.sign.ExpSiginOutStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_expSiginOutStoraWindow_ID',
	closeAction: 'close',
	layout: 'auto',	
	width:615,
	resizable : false,
	//align: 'center',
	title:sign.expSign.i18n('pkp.sign.expSign.signInformation'),//签收信息
	modal: true,
	signOutStorageFormPanel :　null,
	getSignOutStorageFormPanel: function(){
		if(this.signOutStorageFormPanel == null){
			if(Ext.getCmp('Foss_sign_ExpSignOutStorageFormPanel_SignTime_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_ExpSignOutStorageFormPanel_SignTime_ID') );
			}
			if(Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID') );
			}
			
			this.signOutStorageFormPanel = Ext.create('Foss.sign.expSignOutStorageFormPanel',{
				width:580
			});
		}
		return this.signOutStorageFormPanel;
	},
	signWithTicketFormPanel:null,
	getSignWithTicketFormPanel:function(){
		if(this.signWithTicketFormPanel == null){
		
		if(Ext.getCmp('Foss.sign.expSignwithTicketFormPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss.sign.expSignwithTicketFormPanel_ID') );
			}
			this.signWithTicketFormPanel = Ext.create('Foss.sign.expSignMultiExceptionSignPiecesForm',{
				width:580
			});
		}
		return this.signWithTicketFormPanel;
	},
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
		
			if(Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID') );
			}
			if(Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID') );
			}
			
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.SerialNoOutStorageGridPanel',{
				width:580,
				text : sign.expSign.i18n('pkp.sign.expSign.signPieces')//签收件
			});
		}
		return this.SerialNoOutStorageGridPanel;
	},
	resetPanel :  function(arrivesheet){
		sign.expSign.COUNT=0;
		var form = this.getSignOutStorageFormPanel(),
		serialNoOutStorage = this.getSerialNoOutStorageGridPanel().getStore(),
		serialNoQuery =this.getSerialNoOutStorageGridPanel().down('toolbar');
		this.getSerialNoOutStorageGridPanel().getSelectionModel().deselectAll();
		form.getForm().loadRecord(arrivesheet);
		form.initData(arrivesheet.get('arriveSheetGoodsQty'),arrivesheet.get('serviceTime'),arrivesheet.get('deliverymanName'),arrivesheet.get('receiveCustomerContact')); 
		//清除运单流水号的记录
		serialNoOutStorage.removeAll();	
		serialNoQuery.items.items[0].setValue('');
		serialNoQuery.items.items[1].setValue('');
		Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_UnNormalSelectAll_ID').setVisible(false);
		if(Ext.isEmpty(arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH'))||'Y'!=arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH')){
			// 根据运单编号查询运单流水号
			serialNoOutStorage.load({
				params:{
					'signVo.signDto.waybillNo':arrivesheet.get('waybillNo')
				}
			});
		}
		//记录一下全局的运单号
		sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX = arrivesheet.get('waybillNo');
		//隐藏同票多类对应的form
		Ext.getCmp('Foss.sign.expSignwithTicketFormPanel_ID').hide();
		Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').show();
	},	
	serialButtons  : null,
	getSerialButtons: function(){
		if(this.serialButtons  == null){
			this.serialButtons = Ext.create('Ext.button.Button',{
			text:sign.expSign.i18n('pkp.sign.expSign.signIn'),//签收 
			disabled:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerydetailbutton'),
			hidden:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerydetailbutton'),
			width:'100',
			style : {
				float : 'center'
			},
			cls:'yellow_button',
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
			handler:function(){
				//update
				var signOutStorageForm=Ext.getCmp('Foss.sign.expSignOutStorageFormPanel_ID').getForm();
				var situation=signOutStorageForm.findField('situation').getValue();
				var form=this.up('window').signOutStorageFormPanel.getForm();
				var signNote=null;
				var samevoteoddIsGoodsShort = false;
				var serialNos = new Array();
				var arriveSheet = form.getRecord();
					// 验证数字不能小于0时的提示信息
				if(form.findField('signGoodsQty').getValue()==0||form.findField('signGoodsQty').getValue()==null){
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signGoodsQtyGreateThanZero'),'error',1000);
					return;
				}
				
				var checkCount = Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection().length;
				if(situation!='UNNORMAL_SAMEVOTEODD'){
					//如果选择的流水号数量与签收件数不一致
					if(form.findField('signGoodsQty').getValue()!=checkCount){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.serialNoSignGoodsQtySame'),'error',1000);//选择流水号数量必须与签收件数一致，请确认！
						return;
					}
				}
				
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
					var flag=true;
					var arr=Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection(); 
					for(var i=0;i<arr.length;i++){
						flag=arr[i].get('goodShorts');
						if(flag==true){
							break;
						}
					}
					if(flag==false){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'是否'+signOutStorageForm.findField('situation').getRawValue()+'至少勾选一个！','error',2000);
						return ;
				    }
				}else if(situation=='UNNORMAL_SAMEVOTEODD'){
					var SignOutSerialNoWithTicketStore=Ext.getCmp('Foss_sign_expSignOutSerialNoWithTicketMulStorageGridPanel_ID').getStore(),
					//signSeriaNoNoteVal =Ext.getCmp('signSeriaNoNote_id').getValue(),
					multiNormalSelectSerNos=Ext.getCmp('Foss_sign_expSignMultiExceptionNormalSerialNoGridPanel_ID').getSelectionModel().getSelection(); 				
					if(SignOutSerialNoWithTicketStore.data.length<2){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'请至少选择两种异常签收情况！','error',3000);
						return ;
					}
					for(var i=0;i<SignOutSerialNoWithTicketStore.data.length;i++){
						var serialSituation =SignOutSerialNoWithTicketStore.getAt(i).data.signState,
						serialNo=SignOutSerialNoWithTicketStore.getAt(i).data.serialNo
						if(Ext.isEmpty(serialSituation)){//如果签收情况为空
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'签收件里的签收情况不能为空！','error',3000);
							return ;
						}
						if(Ext.isEmpty(serialSituation)){//如果签收情况为空
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'签收件里的签收情况不能为空！','error',3000);
							return ;
						}
						if(serialSituation =='UNNORMAL_GOODSHORT'){
							samevoteoddIsGoodsShort=true;//同票多类异常里有选择异常－内物短少
						}
						if(Ext.isEmpty(serialNo)||serialNo.indexOf(',')==0){//如果签收流水号为空
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),'签收件里的异常流水号不能为空！','error',3000);
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
					if(form.findField('signGoodsQty').getValue()!=serialNos.length){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.sign.tip'),sign.expSign.i18n('pkp.sign.sign.serialNoSignGoodsQtySame'),'error',1000);//选择流水号数量必须与签收件数一致，请确认！
						return;
					}
				}
				//update
				// 如果签收情况为部分签收，并且签收件数大于到达联件数，系统弹窗提示错误
				if(form.findField('signStatus').getValue()== sign.expSign.SIGN_STATUS_PARTIAL &&form.findField('signGoodsQty').
						getValue()>= form.getRecord().get('arriveSheetGoodsQty')){
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.partSignSignGoodsQtyUnqualified'),'error',2000);//部分签收时签收出库件数必须小于到达联签收件数，请确认！
					return;
				}
				//是快递员派送时，快递员信息不能为空，系统弹窗提示！ add by 231438
				if(form.findField('isCourierDelivery').getValue()['isCourierDelivery'] == sign.expSign.IS_DELIVERY_YES){//是快递员派送
					if(!form.findField('sendExpressEmpCode').getValue()){ //
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.expressEmpCodeNotNull'),'error',2000);//快递员不能为空，请录入快递员！
						return;
					}else{
						arriveSheet.set('isCourierDelivery',sign.expSign.IS_DELIVERY_YES);
						arriveSheet.set('sendExpressEmpCode',form.findField('sendExpressEmpCode').getValue());
					}
				}else{//不是快递派送
					arriveSheet.set('isCourierDelivery',sign.expSign.IS_DELIVERY_NO);
					arriveSheet.set('sendExpressEmpCode',null);
				}
				
				//是伙伴派送时,伙伴名称与伙伴手机不能为空
				if(form.findField('isPartner').checked==true){
	    			if(!form.findField('partnerName').getValue() || form.findField('partnerName').getValue().length>5){
	    				Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.expressPartnerNameNotNull'),'error',2000);//伙伴名称输入错误，不能为空且不能超过5位汉字，请正确输入！
	    				return;
	    			}else if(!form.findField('partnerPhone').getValue() || (form.findField('partnerPhone').getValue()+"").length != 11){
	    				Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.expressPartnerPhoneNotNull'),'error',2000);//伙伴手机输入错误，不能为空且手机号码只能为11位，请正确输入！
	    				return;
	    			}else{
	    				arriveSheet.set('isPartner',sign.expSign.IS_DELIVERY_YES);
	    				arriveSheet.set('partnerName',form.findField('partnerName').getValue());
	    				arriveSheet.set('partnerPhone',form.findField('partnerPhone').getValue());
	    			}
				}else{//不是合作伙伴
					arriveSheet.set('isPartner',sign.expSign.IS_DELIVERY_NO);
    				arriveSheet.set('partnerName',null);
    				arriveSheet.set('partnerPhone',null);
				}
				
				
				// 签收时间为空，系统弹窗提示！
				if(!form.findField('signTime').getValue()){
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signTimeNotNull'),'error',2000);//签收时间不能为空，请录入签收时间！
					return;
				}
				//签收人类型为空,系统弹窗提示！
				if(!form.findField('deliverymanType').isValid()){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.deliverymanTypeNotNull'),'error',3000);
						return;
				}
				if(form.findField('situation').getValue()=='UNNORMAL_GOODSHORT' || samevoteoddIsGoodsShort==true){
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
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.alittleShortMaxLength'),'error',3000);
							return;
						}
					}else{
						if(!form.findField('alittleShort').isValid()){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.alittleShortNotNull'),'error',3000);
							return;
						}
					}
					//校验外包装是否完好是否为空 如果为空 不让确认
					if(!form.findField('packingResult').isValid()){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.packingResultNotNull'),'error',3000);
						return;
					}
				}
				// 如果签收情况为异常其他 且复选框备注内容为“因客户要求，重新开单转货/返货”选中，转货/返货新单号为空  系统弹窗提示错误
				if(form.findField('situation').getValue()=='UNNORMAL_ELSE' && form.findField('isReturn').checked==true){
					if(Ext.isEmpty(form.findField('waybill').getValue())){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.newWaybillNoNotNull'),'error',1000);//转货/返货新单号不能为空
						return;
					}else if(!form.findField('waybill').isValid()){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.validWaybillNo'),'error',1000);//转货/返货新单号必须为有效的单号
						return;
					}
                    signNote='转货/返货中，新单号：'+form.findField('waybill').getValue();
					
					
				}else if(!form.findField('signNote').getValue() || (!form.findField('signNote').isValid())){// 签收备注为空，系统弹窗提示！
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signNoteNotNull'),'error',1000);//签收备注不能为空，请确认！
					return;
				}
			
					Ext.MessageBox.confirm(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.sureSerialNoInfo'),function(btn){//是否确定签收所选择的流水信息?
						if(btn == 'yes'){
							var serialNorowObjs = Ext.getCmp('Foss_sign_ExpSerialNoOutStorageGridPanel_ID').
								getSelectionModel().getSelection();//得到选中的流水号信息
								form.updateRecord(arriveSheet);//得到更新后的签收信息
							arriveSheet.set('signTime',Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true));
							if(signNote != null){
								arriveSheet.set('signNote',signNote);
							}
							var situation=signOutStorageForm.findField('situation').getValue();
							if(situation!='UNNORMAL_GOODSHORT'&&samevoteoddIsGoodsShort==false){
								arriveSheet.set('packingResult',null);
								arriveSheet.set('alittleShort',null);
							}
							if(situation!='UNNORMAL_SAMEVOTEODD'){
								if(serialNorowObjs!=null){
									for (var i = 0; i < serialNorowObjs.length; i++) {//把前台选中的流水号放入数组里
										var goodShorts;
										if(situation=='NORMAL_SIGN'){
											serialNos.push({
												'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
												'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
												'situation':situation//流水号签收情况
											});
											//update
										}else{
											var situ = 'NORMAL_SIGN';
											if(serialNorowObjs[i].get("goodShorts")==true){
												situ=situation;
											}
											serialNos.push({
												'serialNo':serialNorowObjs[i].get("serialNo"),//流水号
												'arrivesheetNo':arriveSheet.get("arrivesheetNo"),//到达联编号
												'situation':situ//流水号签收情况
											});
										}
									}
								}
							}
							
							var lineSignDto ={
										'productType' : arriveSheet.data.productCode,// 运输性质
										'isWholeVehicle' : arriveSheet.data.isWholeVehicle// 是否整车运单
										};
							Ext.Ajax.request({
								url:sign.realPath('addSignExp.action'),
								method: 'POST',
								timeout: 300000,
								jsonData: {
									'signDetailVo':{
										'signDetailList':serialNos//流水号信息集合
									},
									'arriveSheetVo': {
										'arriveSheet': arriveSheet.data //签收信息
									},'signVo':{
										'lineSignDto':lineSignDto, //给结算传的参数
										'orderNo':arriveSheet.data.orderNo//订单号
									}
								},
								success: function(response){
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),json.message,'ok',4000);
									Ext.getCmp('Foss_sign_expSiginOutStoraWindow_ID').close();
									// 更新查询结果里表格的记录
									Ext.getCmp('T_sign-expressSignOutStorageIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
								},exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), json.message, 'error', 4000);
								}
							});
						}else{
							//已取消签收
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signCanceled'),'ok',1000);
							}
					},this);
				}
			});
		}
		return  this.serialButtons ;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getSignOutStorageFormPanel(),
			me.getSerialNoOutStorageGridPanel(),
			me.getSignWithTicketFormPanel()
		];
		me.buttons = [me.getSerialButtons()];
		me.callParent([cfg]);
	}
});

// 查询条件----第一层
Ext.define('Foss.sign.ExpQuerysignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title:sign.expSign.i18n('pkp.sign.expSign.queryBuilder'),// 查询条件
    defaultType : 'textfield',
	collapsible: true,
	layout: 'column',
	cls:'autoHeight',
	bodyCls:'autoHeight',
	frame:true,
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:90
	},
	items: [{
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.waybillNo'),//运单号
		name: 'waybillNo',//运单号
		vtype: 'waybill',
		columnWidth:.25
	},{
		name: 'arrivesheetNo',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.arrivesheetNo'),//到达联编号
		columnWidth:.25
	},{
		name: 'receiveCustomerName',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.receiveCustomerName'),//收货人
		columnWidth:.25
	},{
		name: 'receiveCustomerPhone',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.receiveCustomerPhone'),//收货人电话
		columnWidth:.25
	},{
		name: 'receiveCustomerMobilephone',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.receiveCustomerMobilephone'),//收货人手机
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.settleTime'),//结清时间
		fieldId:'Foss_sign_ExpQuerysignOutStorageFormPanel_rangeDateField_ID',
		fromEditable:false,
		toEditable :false,
		disallowBlank:true,
		//dateRange: 30,
		dateType: 'datetimefield_date97',
		fromName: 'settleTimeStart',
		toName: 'settleTimeEnd',
		fromValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,0,0,0),'Y-m-d H:i:s'),
		toValue: Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
				,23,59,59),'Y-m-d H:i:s'),
		columnWidth: .50
	},{
		name: 'noMentionGoods',
		fieldLabel:sign.expSign.i18n('pkp.sign.expSign.noMentionGoods'),//所有未提货物
		allowBlank: true,
		xtype: 'checkbox' ,
		columnWidth:.25,
		handler:function(){
			// 如果"所有未提货物"选中,运单号，到达联编号，收货人，收货人电话，收货人手机不可用
			var form = this.up('form').getForm(),checked = false;
    		if(form.findField('noMentionGoods').checked==true){
    			checked = true;
    		}
    		form.findField('waybillNo').setDisabled(checked);
			form.findField('arrivesheetNo').setDisabled(checked);
			form.findField('receiveCustomerName').setDisabled(checked);
			form.findField('receiveCustomerPhone').setDisabled(checked);
			form.findField('receiveCustomerMobilephone').setDisabled(checked);
		}
		
	},{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text:sign.expSign.i18n('pkp.sign.expSign.reset'),//重置
			columnWidth:.08,
			handler:function(){
				var form = this.up('form').getForm();
				form.reset();
				//结清时间默认为当前系统时间0:00至23:59
				form.findField('settleTimeStart').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,0,0,0),'Y-m-d H:i:s'));
				
				form.findField('settleTimeEnd').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,23,59,59),'Y-m-d H:i:s'));
				
			}
		},{
			xtype: 'container',
			border : false,
			columnWidth:.84,
			html: '&nbsp;'
		},{
			text:sign.expSign.i18n('pkp.sign.expSign.search'),//查询
			cls:'yellow_button',
			disabled:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerybutton'),
			hidden:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerybutton'),
			columnWidth:.08,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
			handler:function(){
				Ext.getCmp('T_sign-expressSignOutStorageIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
			}
		}]
	}]
});

	
// 待处理-GridPanel---第一层
Ext.define('Foss.sign.ExpQuerysignOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    frame: true,
	id: 'ExpQuerysignOutStorage_GridPanel_Id',
    stripeRows: true,
    title:sign.expSign.i18n('pkp.sign.expSign.pending'),//'待处理'
    emptyText:sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空'
	collapsible: true,
	animCollapse: true,
	store: null,
	viewConfig: {
        enableTextSelection: true
    },
	expSiginOutStoraWindow : null,
	getExpSiginOutStoraWindow : function(){
		if(this.expSiginOutStoraWindow == null){
		
			if(Ext.getCmp('Foss_sign_expSiginOutStoraWindow_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_expSiginOutStoraWindow_ID') );
			}
			this.expSiginOutStoraWindow = Ext.create('Foss.sign.ExpSiginOutStoraWindow',{
			});
		}
		return this.expSiginOutStoraWindow;
	},
	czmSiginOutStoraWindow :　null,
	getCzmSiginOutStoraWindow: function(){
		if(this.czmSiginOutStoraWindow == null){
			if(Ext.getCmp('Foss.sign.czmSiginOutStoraWindow_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss.sign.czmSiginOutStoraWindow_ID') );
			}
			this.czmSiginOutStoraWindow = Ext.create('Foss.sign.czmSiginOutStoraWindow',{
			});
		}
		return this.czmSiginOutStoraWindow;
	},
	columns: [
			{
            xtype:'actioncolumn',
            text: sign.expSign.i18n('pkp.sign.expSign.operate'),//操作
			width: 50,
			align: 'center',
            items: [{
               iconCls: 'deppon_icons_signin',
                tooltip: sign.expSign.i18n('pkp.sign.expSign.signIn'),//签收
                disabled:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexsignbutton'),
                handler: function(grid, rowIndex, colIndex) {
                	var arrivesheet = grid.getStore().getAt(rowIndex);
                		/*window = grid.up('gridpanel').getExpSiginOutStoraWindow();
                	var arrivesheet = grid.getStore().getAt(rowIndex),
                		window = grid.up('gridpanel').getExpSiginOutStoraWindow();
                		window.show();
                		window.resetPanel(arrivesheet);*/
                	
                	Ext.Ajax.request({
                		url:sign.realPath('queryCzmInfo.action'),
                		method:'POST',
                		timeout:300000,
                		/*jsonData:{
                			'arriveSheetVo': {
								'arriveSheet': arrivesheet.data //签收信息
							}
                		},*/
                		params:{
                			'signVo.signDto.waybillNo':arrivesheet.get('waybillNo')
                		},
                		success:function(response){
                			var czmResult = Ext.decode(response.responseText);
                			czmSignDto = czmResult.signVo.czmSignDto
                    		if(czmSignDto == null){
                    			var window = grid.up('gridpanel').getExpSiginOutStoraWindow();
                        		window.show();
                        		window.resetPanel(arrivesheet)
                    		}else{
                    			var window = grid.up('gridpanel').getCzmSiginOutStoraWindow();
                        		window.show();
                        		window.resetCzmPanel(arrivesheet,czmSignDto)
                    		}
                    	},exception:function(response){
                    		var json = Ext.decode(response.responseText);
                  			Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), json.message, 'error', 4000);
                    	}
                	});
                }
            }]
        },
		{ text : sign.expSign.i18n('pkp.sign.expSign.waybillNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'waybillNo' },//运单编号
		{ text : sign.expSign.i18n('pkp.sign.expSign.arrivesheetNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'arrivesheetNo' }, //到达联编号
		{ text : sign.expSign.i18n('pkp.sign.expSign.arriveSheetGoodsQty'),align: 'center',flex: 1,dataIndex : 'arriveSheetGoodsQty' },//到达联件数
		{ text : sign.expSign.i18n('pkp.sign.expSign.stockGoodsQty'),align: 'center',flex: 1,dataIndex : 'stockGoodsQty' },//库存件数
		{ text : sign.expSign.i18n('pkp.sign.expSign.deliverymanName'),align: 'center',flex: 1,dataIndex : 'deliverymanName' },//提货人
		{ text : sign.expSign.i18n('pkp.sign.expSign.identifyCode'),align: 'center',width : 150,dataIndex : 'identifyCode' },//证件号码
		//后台服务器当前时间
		{ text : sign.expSign.i18n('pkp.sign.expSign.serviceTime'),align: 'center',width : 150,dataIndex : 'serviceTime',hidden:true,hideable:false},
		//收货客户联系人名称
		{ text : sign.expSign.i18n('pkp.sign.expSign.receiveCustomerContact'),align: 'center',width : 150,dataIndex : 'receiveCustomerContact',hidden:true,hideable:false} 
    ],
    pagingToolbar : null,
  	getPagingToolbar : function() {
  		if (this.pagingToolbar == null) {
  			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
  				store : this.store,
  				plugins: 'pagesizeplugin',
				displayInfo: true
  			});
  		}
  		return this.pagingToolbar;
  	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.expSignOutStorageStore');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm =  Ext.create('Foss.sign.ExpQuerysignOutStorageFormPanel');
	var queryResultGrid = Ext.create('Foss.sign.ExpQuerysignOutStorageGridPanel');
	// // 定义到达联查询列表
	Ext.create('Ext.panel.Panel',{
		id:'T_sign-expressSignOutStorageIndex_content',
		cls:"panelContent",
		bodyCls:'panelContent-body',
		layout:'auto',
		getArriveGrid: function(){
			return queryResultGrid;
		},
		getQueryForm: function(){
			return queryForm;
		},
		margin:'0 0 0 0',
		items : [queryForm,queryResultGrid],
		renderTo: 'T_sign-expressSignOutStorageIndex-body'
	});
});
	
	/**
	 * 弹出窗口：子母件签收
	 * 231438-chenjunying
	 */
	Ext.define('Foss.sign.czmSiginOutStoraWindow',{
		extend:'Ext.window.Window',
		id:'Foss.sign.czmSiginOutStoraWindow_ID',
		closeAction: 'close',
		layout: 'auto',	
		width:635,
		resizable : false,
		title:sign.expSign.i18n('pkp.sign.expSign.signInformation'),//签收信息
		modal: true,
		czmSignOutStorageFormPanel :　null,
		getCzmSignOutStorageFormPanel: function(){
			if(this.czmSignOutStorageFormPanel == null){// 上部分签收信息pannel
				if(Ext.getCmp('Foss_sign_ExpCzmSignOutStorageFormPanel_SignTime_ID')!=null){
					Ext.destroy(Ext.getCmp('Foss_sign_ExpCzmSignOutStorageFormPanel_SignTime_ID') );
				}
				/*if(Ext.getCmp('Foss.sign.expCzmSignOutStorageFormPanel_ID')!=null){
					Ext.destroy(Ext.getCmp('Foss.sign.expCzmSignOutStorageFormPanel_ID') );
				}*/
				this.czmSignOutStorageFormPanel = Ext.create('Foss.sign.expCzmSignOutStorageFormPanel',{
					width:600
				});
			}
			return this.czmSignOutStorageFormPanel;
		},
		czmRelationSignOutStorageGridPannel :　null,
		getCzmRelationSignOutStorageGridPannel: function(){ //子母件签收件gridPannel
			if(this.czmRelationSignOutStorageGridPannel == null){
				this.czmRelationSignOutStorageGridPannel = Ext.create('Foss.sign.czmRelationSignOutStorageGridPannel',{
					width:600
				});
			}
			return this.czmRelationSignOutStorageGridPannel;
		},
		czmRelationSignNotInStorageGridPannel : null,
		getCzmRelationSignNotInStorageGridPannel: function(){ //子母件未在库票数gridPannel
			if(this.czmRelationSignNotInStorageGridPannel == null){
				this.czmRelationSignNotInStorageGridPannel = Ext.create('Foss.sign.czmRelationSignNotInStorageGridPannel',{
					width:600
				});
			}
			return this.czmRelationSignNotInStorageGridPannel;
		},
		
		resetCzmPanel:function(arrivesheet,czmSignDto){ //给window中的panel中设置初始值 
			//全局变量保存查询的运单号
			sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX = arrivesheet.get('waybillNo');
			
			var form = this.getCzmSignOutStorageFormPanel();  //获取window中的签收信息 在库子母件form
			var czmInStorage = this.getCzmRelationSignOutStorageGridPannel().getStore();
			form.getForm().loadRecord(arrivesheet);//载入数据
			var czmNotInStorage=this.getCzmRelationSignNotInStorageGridPannel().getStore();
			var dataStore = new Array(); //新定义一个数组，用于存拿到的StringList转换后的json
			
			if (czmSignDto.stockNotInWaybillNoList) {
				var data = czmSignDto.stockNotInWaybillNoList;
				for (var index = 0, len = data.length; index < len; index++ ) {
					dataStore.push({'waybillNo': data[index]}); //给未在库子母件store赋值
				}
			}
			czmNotInStorage.loadData(dataStore);  //未在库子母件store加载数据
			
			form.czmInitData(czmSignDto.czmGoodsQtyTotal,czmSignDto.czmGoodsQtyInStock,arrivesheet.get('serviceTime'),
				arrivesheet.get('deliverymanName'),arrivesheet.get('receiveCustomerContact'));
			//清除运单号的记录
			czmInStorage.removeAll();
			if(Ext.isEmpty(arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH')) ||
					'Y'!=arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH')){
				// 根据运单编号查询子母件在库运单号、流水号
				czmInStorage.load({
					params:{
						'signVo.signDto.czmWaybillNoList':czmSignDto.stockInWaybillNoList
					},
					callback: function(records, operation, success) {
					    //排序重新load在库子母件的store
						var inGrid = Ext.getCmp('Foss_sign_CzmRelationSignOutStorageGridPannel_ID'),
							inStore = inGrid.store;
						var record = null;
						inStore.each(function(item) {
							if (sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX == item.get('waybillNo')) {
								record = item; //复制符合条件的记录
							}
						});
						if (record) { //如果record不为空
							inStore.remove(record); //移动操作
							inStore.insert(0, record);
						}
						/*inStore.each(function(item) {
							console.info(item.get('serialNo'));
						});*/
						Ext.getCmp('Foss_sign_CzmRelationSignOutStorageGridPannel_ID').store = inStore;
				    }
				});
			}
			Ext.getCmp('Foss_sign_CzmRelationSignOutStorageGridPannel_ID').show();
		},
		serialButtons  : null,
		getSerialButtons: function(){  //签收按钮
			if(this.serialButtons  == null){
				this.serialButtons = Ext.create('Ext.button.Button',{
				text:sign.expSign.i18n('pkp.sign.expSign.signIn'),//签收 
				disabled:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerydetailbutton'),
				hidden:!sign.expSign.isPermission('signoutstorageindex/expresssignoutstorageindexquerydetailbutton'),
				width:'100',
				style : {
					float : 'center'
				},
				cls:'yellow_button',
				plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
					  //设定间隔秒数,如果不设置，默认为2秒
					  seconds: 3
					}),
				handler:function(){
					//获取签收信息pannel
					var czmSignOutStorageForm = Ext.getCmp('Foss.sign.expCzmSignOutStorageFormPanel_ID').getForm();
					var situation=czmSignOutStorageForm.findField('situation').getValue();
					var form=this.up('window').czmSignOutStorageFormPanel.getForm();
					var czmSignInfos = new Array(); //数组用来存选中的子母件
					var arriveSheet = form.getRecord();
					//选中的票数
					var checkCount = Ext.getCmp('Foss_sign_CzmRelationSignOutStorageGridPannel_ID').getSelectionModel().getSelection().length;
					// 签收时间为空，系统弹窗提示！
					if(!form.findField('signTime').getValue()){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signTimeNotNull'),'error',2000);//签收时间不能为空，请录入签收时间！
						return;
					}
					//签收人类型为空,系统弹窗提示！
					if(!form.findField('deliverymanType').isValid()){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.deliverymanTypeNotNull'),'error',3000);
							return;
					}
					if(form.findField('situation').getValue()=='UNNORMAL_GOODSHORT'){
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
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.alittleShortMaxLength'),'error',3000);
							return;
						}
					}else{
						if(!form.findField('alittleShort').isValid()){
							Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.alittleShortNotNull'),'error',3000);
							return;
						}
					}
					//校验外包装是否完好是否为空 如果为空 不让确认
					if(!form.findField('packingResult').isValid()){
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.packingResultNotNull'),'error',3000);
						return;
					}
				}
				//是快递员派送时，快递员信息不能为空，系统弹窗提示！ add by 231438
				if(form.findField('isCourierDelivery').getValue()['isCourierDelivery'] == sign.expSign.IS_DELIVERY_YES){//是快递员派送
					if(!form.findField('sendExpressEmpCode').getValue()){ //
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.expressEmpCodeNotNull'),'error',2000);//快递员不能为空，请录入快递员！
						return;
					}else{
						arriveSheet.set('isCourierDelivery',sign.expSign.IS_DELIVERY_YES);
						arriveSheet.set('sendExpressEmpCode',form.findField('sendExpressEmpCode').getValue());
					}
				}else{//不是快递派送
					arriveSheet.set('isCourierDelivery',sign.expSign.IS_DELIVERY_NO);
					arriveSheet.set('sendExpressEmpCode',null);
				}
				// 签收备注为空，系统弹窗提示！
				if(!form.findField('signNote').getValue() || (!form.findField('signNote').isValid())){// 签收备注为空，系统弹窗提示！
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signNoteNotNull'),'error',1000);//签收备注不能为空，请确认！
					return;
				}
				//签收件数超出限制，系统弹窗提示！
				if(checkCount > sign.expSign.CZM_SIGN_LIMIT){
					Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),"子母件签收一次不能超过"+sign.expSign.CZM_SIGN_LIMIT+"票！",'error',1000);
					return;
				}
				Ext.Msg.confirm(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.sureCzmWaybillChecked'),function(btn){
					if(btn == 'yes'){
						//获取选中的签收件--子母件
						var czmWaybillObjs = Ext.getCmp('Foss_sign_CzmRelationSignOutStorageGridPannel_ID').getSelectionModel().getSelection();
						form.updateRecord(arriveSheet);//得到更新后的签收信息
						//签收时间
						arriveSheet.set('signTime',Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true));
						//签收备注
						var signNote = form.findField('signNote').getValue();
						arriveSheet.set('signNote',signNote);
						//签收情况
						if(situation != 'UNNORMAL_GOODSHORT'){ //签收情况
								arriveSheet.set('packingResult',null);
								arriveSheet.set('alittleShort',null);
						}
						//给到达联签收件数赋值
						arriveSheet.set('signGoodsQty',arriveSheet.get('arriveSheetGoodsQty'));
						//签收件选中
						if(czmWaybillObjs !== null){
							for(var i=0;i<czmWaybillObjs.length;i++){
								czmSignInfos.push({
									'waybillNo':czmWaybillObjs[i].get("waybillNo"),
									'serialNo': czmWaybillObjs[i].get("serialNo")
								});
							}
						}
						var lineSignDto ={
										'productType' : arriveSheet.data.productCode,// 运输性质
										'isWholeVehicle' : arriveSheet.data.isWholeVehicle// 是否整车运单
										};
										
						Ext.Ajax.request({
								url:sign.realPath('addCzmBatchSign.action'),
								method: 'POST',
								timeout: 300000,
								jsonData: {
									'signDetailVo':{
										'czmSignList':czmSignInfos//子母件运单号、流水号信息集合
									},
									'arriveSheetVo':{
										'arriveSheet':arriveSheet.data //签收到达联信息
									},'signVo':{
										'lineSignDto':lineSignDto, //给结算传的参数
										'orderNo':arriveSheet.data.orderNo//订单号
									}
								},
								success:function(response){
									var result = Ext.decode(response.responseText);
									Ext.getCmp('Foss.sign.czmSiginOutStoraWindow_ID').close();//关闭子母件签收窗口
									Ext.getCmp('T_sign-expressSignOutStorageIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
								},
								exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'), json.message, 'error', 4000);
								}
						});
					}else{
						//已取消签收
						Ext.ux.Toast.msg(sign.expSign.i18n('pkp.sign.expSign.tip'),sign.expSign.i18n('pkp.sign.expSign.signCanceled'),'ok',1000);
					}
				},this);
				}
				});
			}
			return  this.serialButtons ;
		},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [me.getCzmSignOutStorageFormPanel(),
		            me.getCzmRelationSignOutStorageGridPannel(),
		            me.getCzmRelationSignNotInStorageGridPannel()];
		me.buttons = [me.getSerialButtons()];
		me.callParent([cfg]);
	}
	});
	
	//子母件签收信息 
	Ext.define('Foss.sign.expCzmSignOutStorageFormPanel',{
		extend: 'Ext.form.Panel',
		title: sign.expSign.i18n('pkp.sign.sign.signInformation'),//子母件签收信息
		layout: 'column',//布局
		frame: true,
		id: 'Foss.sign.expCzmSignOutStorageFormPanel_ID',
		defaults: { //默认值
			margin: '5 0 5 10',
			anchor: '98%',
			labelWidth:65
		},
		defaultType : 'textfield',
		items:[{
			name:'isCurrentOrgCodeReceiveOrgCodeWVH',//Y表示当前部门是收货部门 
			xtype:'hiddenfield'
		},{
			xtype:'combo',
			name: 'czmSignStatus',
			fieldLabel:sign.expSign.i18n('pkp.sign.expSign.czmSignStatus'),//子母件签收状态
			labelWidth:100,
			forceSelection: true, //只允许从下拉列表中选择，不能输入文本
			editable:false,
			valueField:'valueCode',
			displayField:'valueName',
			value:'CZM_SIGN_STATUS_ALL',
			allowBlank:false,
			queryMode:'local',
			triggerAction:'all',
			columnWidth:.40,
			store:sign.expSign.czmSignStatusStore
			,listeners:{
				'select' : function(combo, records, eOpts){
		    		var form = this.up('form').getForm();
		    			//record = form.getRecord();
					// 签收状态选择为：子母件部分签收时，签收件数显示为1且可编辑;选择子母件全部签收时，签收件数显示为在库子母件数，且不可编辑
					if(records[0].data.valueCode==sign.expSign.CZM_SIGN_STATUS_PARTIAL){
						form.findField('czmGoodsQtyInStock').setReadOnly(false)
					}else{
						form.findField('czmGoodsQtyInStock').setReadOnly(false);
					}
				}
			}
		},{
			xtype:'combo',
			name:'situation',
			fieldLabel:sign.expSign.i18n('pkp.sign.expSign.situation'),//签收情况
			labelWidth: 60, 
			forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
			editable:false, //不可编辑
			valueField:'valueCode',  
			displayField: 'valueName', 
			value:'NORMAL_SIGN',
			allowBlank:false,
			queryMode:'local',
			triggerAction:'all',
			columnWidth: .33,
			store : sign.expSign.czmSituationStore
			,listeners : {
				'select':function(combo,records,eOpts){
					var form = this.up('form').getForm(),
						czmSituation = form.findField('situation').getRawValue(),
						record = form.getRecord();
					
					form.findField('signNote').setValue(records[0].get('valueName'));
					if(records[0].get('valueCode') == 'UNNORMAL_GOODSHORT'){
						form.findField('packingResult').setVisible(true);
						form.findField('alittleShort').setVisible(true);
					}else if(records[0].get('valueCode') =='NORMAL_SIGN'){
						form.findField('packingResult').setVisible(false);
						form.findField('alittleShort').setVisible(false);
						form.findField('packingResult').setValue('');
						form.findField('alittleShort').setValue('');
					}else{
						form.findField('packingResult').setVisible(false);
						form.findField('alittleShort').setVisible(false);
						form.findField('packingResult').setValue('');
						form.findField('alittleShort').setValue('');
					}
				}
			}
		},{
			name: 'czmGoodsQty',
			xtype:'numberfield',
			labelWidth: 85, 
			hideTrigger: true,
			//allowBlank:false,
			//readOnly:true,
			fieldLabel:sign.expSign.i18n('pkp.sign.expSign.czmGoodsQty'),//子母件总票数
			allowDecimals : false,// 不允许有小数点
		    minValue: 2, //最小值为1
		    editable:false, //不可编辑
			columnWidth:.27
		},{
			    xtype: 'datetimefield_date97',
			    editable : true,
			    format : 'Y-m-d H:i:s',
			    labelWidth: 60, 
			    id: 'Foss_sign_ExpCzmSignOutStorageFormPanel_SignTime_ID',
			    fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signTime'),//签收时间
			    value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
			    allowBlank:false,
			    columnWidth:.40,
			    name: 'signTime',
			    time : true,
			    dateConfig: { 
			      el : 'Foss_sign_ExpCzmSignOutStorageFormPanel_SignTime_ID-inputEl',
			      minDate: '%y-%M-%d 00:00:00',
			      maxDate:'%y-%M-%d 23:59:59'
			    }
			  },{
			    xtype:'combo',
			    name: 'deliverymanType',
			    fieldLabel:sign.expSign.i18n('pkp.sign.expSign.deliverymanType'),//签收人类型
			    labelWidth: 80, 
			    forceSelection: true, 
			    editable:false, 
			    forceSelection:true,
			    valueField:'valueCode',  
			    displayField: 'valueName', 
			    allowBlank:false,
			    queryMode:'local',
			    triggerAction:'all',
			    columnWidth: .35,
			    store :sign.expSign.signTypeStore
			  },{
			    fieldLabel: sign.expSign.i18n('pkp.sign.settlePayment.packageResult'),//外包装是否完好
			    xtype: 'radiogroup',
			        vertical: true,
			        allowBlank:false,
			    columnWidth:.35,
			    anchorSize: 50,
			    labelWidth:95,
			    layout:'column',
			    columns: 1,
			    name: 'packingResult',
			    items: [
			            { style : 'margin-top:5px',itemId: 'yes', boxLabel: '是', name: 'packingResult', inputValue: 'Y',checked: true},
			            { style : 'margin-top:5px;margin-left:5px',itemId: 'no', boxLabel: '否', name: 'packingResult', inputValue: 'N' }
			        ]
			  },{
			    name:'alittleShort',
			    fieldLabel:sign.expSign.i18n('pkp.sign.expSign.alittleShort'),//短少量
			    xtype:'textfield',
			    labelWidth:50,
			    hideTrigger: true,
			    columnWidth:.30,
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
			        return sign.expSign.i18n('pkp.sign.expSign.alittleShortMaxLength');
			      }else{
			        return true;
			      }
			  },
			    listeners: {  
			      blur: function(thisCom, eventObject,eOpts) {
			        var form = this.up('form').getForm();
			        form.findField('signNote').setValue('');
			        var alittleShort=form.findField('alittleShort').getValue();
			        var rstValue;
			        if(!Ext.isEmpty(alittleShort)){
			          rstValue=sign.expSign.i18n('pkp.sign.expSign.unnormalGoodshort')+','+sign.expSign.i18n('pkp.sign.expSign.alittleShort')+':'+form.findField('alittleShort').getValue();
			        }else{
			          rstValue=sign.expSign.i18n('pkp.sign.expSign.unnormalGoodshort');
			        }
			        form.findField('signNote').setValue(rstValue);
			          }  
			      }
			  },{
					name: 'czmGoodsQtyInStock',
					xtype:'numberfield',
					labelWidth: 100, 
					hideTrigger: true,
					allowBlank:false,
					fieldLabel:sign.expSign.i18n('pkp.sign.expSign.czmGoodsQtyInStock'),//子母件在库票数
					allowDecimals : false,// 不允许有小数点
				    minValue: 1, //最小值为1
				    //editable:false, //不可编辑
					columnWidth:.3
				},{ //add by 231438  是否快递员派送：Y；在快递派送处理界面默认为：是
					name: 'isCourierDelivery',
					fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.isCourierSend'),//是否快递员派送:
					xtype:'radiogroup',
					vertical: true,
			        allowBlank:false,
					columnWidth:.35,
					anchorSize: 50,
					labelWidth:95,
					layout:'column',
					columns: 1,
					items:[
					       { style : 'margin-top:5px',itemId: 'yes', boxLabel: '是', name: 'isCourierDelivery', inputValue: 'Y',checked: true,id: 'isCourierDeliveryYES'},
					       { style : 'margin-top:5px;margin-left:5px',itemId: 'no', boxLabel: '否', name: 'isCourierDelivery', inputValue: 'N',id :'isCourierDeliveryNO' }
					      ],
					listeners: { 
						change:function(me,theEvent,eOpts){
							var checkValue = me.getChecked()[0].inputValue;
							if( checkValue == sign.expSign.IS_DELIVERY_YES){
								this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(false);
								this.up('form').getForm().findField('sendExpressEmpCode').allowBlank = false;
			
							}else{
								this.up('form').getForm().findField('sendExpressEmpCode').setDisabled(true);
							}
						}
					}		
				},{//add by 231438  快递员(选择器)
					name:'sendExpressEmpCode',
					fieldLabel:sign.expSign.i18n('pkp.sign.expDeliverHandler.courier'),//快递员:
					xtype:'commonExpressemployeeselector',
					labelWidth:60,
					columnWidth:.3,
					maxLength:50
				},{
			    labelWidth: 60,
			    xtype: 'textarea',
			    name:'signNote',
			    hight: 20,
			    allowBlank:false,
			    maxLength:200,
			    fieldLabel:sign.expSign.i18n('pkp.sign.expSign.signNote'),//备注
			    columnWidth: 1
			  }],
			czmInitData: function(czmGoodsQtyTotal,czmGoodsQtyInStock,serviceTime,signName,receiveCustomerContact){ //TODO
			var form = this.getForm(),
				czmSignStatus = form.findField('czmSignStatus'),  //子母件签收状态
				czmSituation = form.findField('situation'),    //子母件签收情况
				czmSignTime = form.findField('signTime'),    //签收时间
				czmGoodsQty = form.findField('czmGoodsQty'),  //子母件总票数
				czmInStorageQty = form.findField('czmGoodsQtyInStock'), //子母件在库票数
				czmPackingResult = form.findField('packingResult'), //外包装是否完好
				czmAlittleShort = form.findField('alittleShort'); //短少量
				czmDeliverymanType = form.findField('deliverymanType'), //签收人类型
				czmSignNot = form.findField('signNote');//签收备注
			form.reset();
			var value = czmSituation.store.getAt(sign.expSign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueCode');// 签收情况 code
			var name = czmSituation.store.getAt(sign.expSign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueName');// 签收情况的name
			czmSignTime.setValue(serviceTime);
			czmSignStatus.setValue('CZM_SIGN_STATUS_ALL');  //子母件签收状态
			czmSituation.setValue(value);  //子母件签收（具体签收情况）,默认正常签收
			czmSignNot.setValue(name);  //签收备注
			czmGoodsQty.setValue(czmGoodsQtyTotal);  //设置子母件总票数值
			czmInStorageQty.setValue(czmGoodsQtyInStock);  //在库票数赋值
			czmGoodsQty.setReadOnly(true);  //子母件总票数不可编辑
			czmInStorageQty.setReadOnly(true);  //子母件签收状态默认为全部签收，在库票数不可编辑
			czmPackingResult.setVisible(false);//设置外包装是否完好默认不可见
			czmAlittleShort.setVisible(false);//设置短少量默认不可见
			sign.expSign.GLOBAL_RECEIVE_CUSTOMER_CONTACT=receiveCustomerContact;
			if(signName==receiveCustomerContact){
				czmDeliverymanType.setValue("SIGN_PERSON_ME");
			}else{
				czmDeliverymanType.setValue("");
			}
		},
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);
			me.callParent([cfg]);
		}
		});
	
	/**
	 *弹出框中的签收件--在库子母件
	 */
	Ext.define('Foss.sign.czmRelationSignOutStorageGridPannel', { //字母件关联单号(流水号隐藏)信息
		extend: 'Ext.grid.Panel',
		columnLines: true,
		height: 270, //高度
		emptyText: sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
		id: 'Foss_sign_CzmRelationSignOutStorageGridPannel_ID',
		frame: true,
		enableColumnHide: false,

		title: sign.expSign.i18n('pkp.sign.expSign.signPieces'),//签收件
		selModel:Ext.create('Ext.selection.CheckboxModel',{}), //表头的单选框，每行头都会生成单选框
		columns: [
			{header: sign.expSign.i18n('pkp.sign.expSign.czmWaybillNo'), flex: 1, align: 'center', dataIndex:'waybillNo'}//运单编号 
		],
		viewConfig: {
			enableTextSelection: true,
			autoHeight: true,
			getRowClass:function(record, rowIndex, rp, ds){
				var waybill = record.get('waybillNo');
				if(sign.expSign.SIGN_RECORD_WAYBILLNO_INDEX == waybill){
					return 'sign_czm_query_color';
				}
			}
		},
		constructor: function (config) {
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.sign.expCzmSignOutStorageStore');
			me.callParent([cfg]);
		}
	});
	/**
	 *弹出框中的未在库子母件 
	 */
	Ext.define('Foss.sign.czmRelationSignNotInStorageGridPannel',{
		extend: 'Ext.grid.Panel',
		title:sign.expSign.i18n('pkp.sign.expSign.czmNotInStorage'),//未在库子母件
		height:210,
		emptyText: sign.expSign.i18n('pkp.sign.expSign.emptyText'),//查询结果为空
		id:'Foss_sign_CzmRelationSignNotInStorageGridPannel_ID',
		frame: true,
		enableColumnHide: false,
		columns: [
			{header: sign.expSign.i18n('pkp.sign.expSign.czmWaybillNo'), flex: 1, align: 'center', dataIndex:'waybillNo'}//运单编号 ,
				],
		viewConfig: {
			autoHeight: true,
			enableTextSelection: true
		},
		constructor: function (config) {
			var me = this,
				cfg = Ext.apply({}, config);
			me.store = Ext.create('Foss.sign.expCzmNotInStorageStore');
			me.callParent([cfg]);
		}
	});
	/**
	 *子母件model
	 */
	Ext.define('Foss.sign.expCzmSignOutStorageModel',{
		extend:'Ext.data.Model',
		fields:[
		        {name:'waybillNo',type:'string'}, //子母件签收：运单号
		        {name:'serialNo',type:'string'}//子母件签收：流水号
		]
	});
	/**
	 * 子母件Store
	 */
	Ext.define('Foss.sign.expCzmSignOutStorageStore',{ //在库子母件
		extend:'Ext.data.Store',
		model:'Foss.sign.expCzmSignOutStorageModel',
		pageSize:50,
		proxy:{
			type:'ajax',
			url:sign.realPath('queryCzmInSerialNo.action'),
			actionMethods : 'post',
			timeout: 100000,
			reader : {
				type:'json',
				root:'stockVo.stockDtoList'
			}
		}
	});	
	
	/**
	 * 子母件Store
	 */
	Ext.define('Foss.sign.expCzmNotInStorageStore',{ //未在库子母件
		extend:'Ext.data.Store',
		fields : ['waybillNo'],
		proxy : {
			type: 'memory',
			reader: {
				type: 'json'
			}
		},
		pageSize:50
	});
	