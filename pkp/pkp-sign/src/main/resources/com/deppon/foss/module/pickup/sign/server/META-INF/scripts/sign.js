/***
 * @author foss-meiying
 * page:签收出库自提
 */
sign.sign.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';//签收情况词条
sign.sign.PKP_SIGN_STATUS = 'PKP_SIGN_STATUS';//签收状态词条
sign.sign.SIGN_STATUS_PARTIAL = 'SIGN_STATUS_PARTIAL';//签收状态为部分签收
sign.sign.IS_CURRENT_ORG_CODE_RECEIVE_ORG_CODE_WVH =null;
sign.sign.SIGN_SERIAL_NO_ARR =null;
sign.sign.SIGN_RECORD_SERINALNO_INDEX=null;
sign.sign.SIGN_RECORD_WAYBILLNO_INDEX=null;
sign.sign.COUNT=null;
sign.sign.DOPSIGNQTY=null;//dop传来的家装签收件数
(function(){
	sign.sign.situationStore =FossDataDictionary.getDataDictionaryStore(sign.sign.PKP_SIGN_SITUATION, null);
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','GOODS_BACK'));
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','UNNORMAL_SIGN'));
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
	sign.sign.situationStore.removeAt(sign.sign.situationStore.find('valueCode','PARTIAL_SIGN'));//移除部分签收
	
	sign.sign.signStatusStore =FossDataDictionary.getDataDictionaryStore(sign.sign.PKP_SIGN_STATUS, null);
	sign.sign.signStatusStore.removeAt(sign.sign.signStatusStore.find('valueCode','SIGN_STATUS_NO'));//移除未签收
	
	sign.sign.exsituationStore=FossDataDictionary.getDataDictionaryStore(sign.sign.PKP_SIGN_SITUATION,
			null,null,['UNNORMAL_BREAK', 'UNNORMAL_DAMP', 'UNNORMAL_POLLUTION','UNNORMAL_GOODSHORT','UNNORMAL_ELSE']);
})();
//让form里的控件可见　或者不可见
sign.sign.setPartFormVisible = function(form,visible){
		if(form.findField('packingResult').isVisible()==!visible){
			form.findField('packingResult').setVisible(visible);
		}
		if(form.findField('alittleShort').isVisible()==!visible){
			form.findField('alittleShort').setVisible(visible);
			form.findField('alittleShort').setValue('');
		}
}
sign.sign.editor = function(th,e){
	var newValue = e.value;//获取本次付款金额修改后值
	var oldValue = e.originalValue;
	if(newValue == oldValue){
		return false;
	}
	e.record.set('signState',oldValue);
	var arr = th.grid.getStore().collect('signState'), 
	flag = arr.indexOf(newValue),
	form = Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();

	if (flag >=0) {
		//提交记录
 		e.record.commit();
		Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),
				'签收情况已被选择', 'error', 3000);
		return false;
	}
	e.record.set('signState',newValue);
	e.record.commit();
	var newArr =th.grid.getStore().collect('signState'),
	 isre= newArr.indexOf('UNNORMAL_GOODSHORT');
	if (isre>=0) {
		sign.sign.setPartFormVisible(form, true);
	}else{
		sign.sign.setPartFormVisible(form, false);
	}
	sign.sign.resetSignNote(newValue,oldValue);
}

sign.sign.resetSignNote = function() {
	var SignOutStorageFormPanel = Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm(),

	situation = SignOutStorageFormPanel.findField('situation').getValue(), 
	situationName = SignOutStorageFormPanel
			.findField('situation').getRawValue(), 
	alittleShort = SignOutStorageFormPanel
			.findField('alittleShort').getValue(), rstValue;
	if (situation == 'UNNORMAL_GOODSHORT') {
		if (!Ext.isEmpty(alittleShort)) {
			rstValue = situationName
					+ ','
					+ sign.sign.i18n('pkp.sign.sign.littleShort')
					+ ':'
					+ SignOutStorageFormPanel.findField('alittleShort')
							.getValue();
		} else {
			rstValue = sign.sign.i18n('pkp.sign.sign.unnormalGoodshort');
		}
	} else {
		var SignOutSerialNoWithTicket = Ext
				.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID'),
				signStates = SignOutSerialNoWithTicket.getStore().collect('signState'), 
				partSituation = '';
		if (signStates.length > 0) {
			for(var i = 0 ;i<signStates.length;i++){
				var situationN =FossDataDictionary.rendererSubmitToDisplay(signStates[i], sign.sign.PKP_SIGN_SITUATION)+',';  
				partSituation+=situationN;
			}	
				if(!Ext.isEmpty(alittleShort)){
					rstValue=situationName+','+partSituation+sign.sign.i18n('pkp.sign.sign.littleShort')+':'+SignOutStorageFormPanel.findField('alittleShort').getValue();
				}else{
					rstValue=situationName+','+partSituation.substring(0,partSituation.length-1);
				}
		} else {
			if (!Ext.isEmpty(alittleShort)) {
				rstValue = situationName
						+ ','
						+ sign.sign.i18n('pkp.sign.sign.littleShort')
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
//待处理  ---查询Model
Ext.define('Foss.sign.SignOutStorageModel',{
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
		{name: 'signTime', type: 'date',convert:function(t){
			if(t){
				var date=new Date(t);
				return Ext.Date.format(date,'Y-m-d H:i:s');
			}
			return null;
		}},// 签收时间
		{name:'isPartner',type:'string'},//伙伴派送
		{name:'partnerName',type:'string'},//伙伴名称
		{name:'partnerPhone',type:'string'},//伙伴电话
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
		{name: 'specialValueAddedService'}//特殊增值服务
	]
});
//异常流水号选择的model 
 Ext.define('Foss.sign.SerialNoWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
	{name: 'serialNo',type: 'string'},// 流水号
	{name: 'waybillNo',type: 'string'},// 运单号
	{name:'isSelect',type:'boolean'}
	]
});


 //签收出库同票多类grid对应model
  Ext.define('Foss.sign.SignOutWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'signState'},
		{name: 'choose'},
		{name: 'serialNo'}
	]
});
// 签收信息--签收件 流水号Model
Ext.define('Foss.sign.SerialNoSignOutStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'serialNo',type: 'string'},// 流水号
		{name: 'goodShorts'}// 是否内货短少
	]
});
// 待处理---Store
Ext.define('Foss.sign.SignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SignOutStorageModel',
	pageSize: 10,
	proxy : {
		type : 'ajax',
		url:sign.realPath('queryArriveSheetInfo.action'),
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
			var form = Ext.getCmp('T_sign-signOutStorageIndex_content').getQueryForm().getForm();
			// 结清时间验证
			var settleTimeStart = form.getValues().settleTimeStart, settleTimeEnd = form.getValues().settleTimeEnd;
			if (!Ext.isEmpty(settleTimeStart) && !Ext.isEmpty(settleTimeEnd)) {	
				var result = Ext.Date.parse(settleTimeEnd,'Y-m-d H:i:s') - Ext.Date.parse(settleTimeStart,'Y-m-d H:i:s');	
				if(result / (24 * 60 * 60 * 1000) >= 30){	
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), sign.sign.i18n('pkp.sign.sign.the.date.range.cannot.be.more.than.30.days'), 'error', 3000); // '起止日期相隔不能超过30天！'
					return false;	
				}	
			}else {//如果结清时间为空
				Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), sign.sign.i18n('pkp.sign.sign.settleTimeNotNull'), 'error', 3000);
					return false;
			}
			if (!form.isValid()){
				return false;
			}
			// 执行查询
			var queryParams=Ext.getCmp('T_sign-signOutStorageIndex_content').getQueryForm().getValues();
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
				Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), data.message, 'error', 2000);	//提示			
			}
		}
	}
});
// 签收信息---查询签收件下的流水号Store
Ext.define('Foss.sign.SerialNoSignOutStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SerialNoSignOutStorageModel',
	proxy : {
		type : 'ajax',
		url:sign.realPath('querySerialNo.action'),
		actionMethods : 'post',//方式
		timeout: 100000,
		reader : {
			type : 'json',
			root : 'stockVo.stockDtoList'
		}
	},
	listeners:{
		load: function(store,records){
			sign.sign.GlobalCount=store.getCount();
			//记录一下流水号  后面使用不用再次查询数据库
			sign.sign.SIGN_SERIAL_NO_ARR = store.collect('serialNo');
		}
	}
});
//签收信息
Ext.define('Foss.sign.SignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title: sign.sign.i18n('pkp.sign.sign.signInformation'), //标题-签收信息
	layout: 'column',//布局
	frame:true,
	//id: 'Foss.sign.SignOutStorageFormPanel_ID',
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
		fieldLabel:sign.sign.i18n('pkp.sign.sign.signStatus'),//到达联签收状态
		labelWidth: 100, 
		forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
		editable:false, //不可编辑
		forceSelection:true,// 必须选择一个。
		valueField:'valueCode',  
		displayField: 'valueName', 
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .4,
		store : sign.sign.signStatusStore,
		listeners : {
			'select' : function(combo, records, eOpts){
				var form = this.up('form').getForm(),
				record = form.getRecord();
				//如果为家装运单，则默认签收件数设为家装传来的DOP签收数量；否则默认签收件数为到达联中的签收件数
				var specialAddValue=record.get('specialValueAddedService');
				// 签收状态选择为部分签收时，签收件数显示为到达联件数且可编辑
				if(records[0].get('valueCode') ==sign.sign.SIGN_STATUS_PARTIAL){
					form.findField('signGoodsQty').setReadOnly(false);
				}else{//全部签收
						if(!Ext.isEmpty(specialAddValue)){//TODO 特殊增值服务字段不为空
							form.findField('signGoodsQty').setValue(sign.sign.DOPSIGNQTY);
							form.findField('signGoodsQty').setReadOnly(true);
						}else{
							form.findField('signGoodsQty').setValue(record.get('arriveSheetGoodsQty'));
							form.findField('signGoodsQty').setReadOnly(true);
						}
					}
				}
			}
	},{
		xtype:'combo',
		name: 'situation',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.situation'),//签收情况
		labelWidth: 60, 
		forceSelection: true, // 只允许从下拉列表中进行选择，不能输入文本
		editable:false, //不可编辑
		forceSelection:true,// 必须选择一个。
		valueField:'valueCode',  
		displayField: 'valueName', 
		allowBlank:false,
		queryMode:'local',
		triggerAction:'all',
		columnWidth: .36,
		store : sign.sign.situationStore,
		listeners : {
	    	'select' : function(combo, records, eOpts){
	    		var form = this.up('form').getForm(),
	    			record = form.getRecord(),
					SerialNoGridPanel = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID'),
					UnNormalSelectAll = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
					
	    		UnNormalSelectAll.setVisible(false);
	    		//内物短少信息初始为隐藏
	    		sign.sign.setPartFormVisible(form,false);
				SerialNoGridPanel.show();
	    		//隐藏
	    		Ext.getCmp('Foss.sign.withTicketFormPanel_ID').hide();
	    		//若为正常签收
	    		if(records[0].get('valueCode')=='NORMAL_SIGN'){
	    			SerialNoGridPanel.columns[2].hide();
					form.findField('signNote').setValue(records[0].get('valueName'));
	    		}//若为同票多类异常签收
	    		else if(records[0].get('valueCode')=='UNNORMAL_SAMEVOTEODD'){
					SerialNoGridPanel.hide();
					var SignOutSerialNoWithTicket=Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID');
					Ext.getCmp('Foss.sign.withTicketFormPanel_ID').show();
					//赋值签收流水号数据
					//var signSeriaNoNote = Ext.getCmp('signSeriaNoNote_id');
					var  multiNormalSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID'),
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
					var model=Ext.create('Foss.sign.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_GOODSHORT',
								choose:'选择',
								serialNo:''
							});
					var model1=Ext.create('Foss.sign.SignOutWithTicketStorageModel',{
								signState:'UNNORMAL_BREAK',
								choose:'选择',
								serialNo:''
							});
					SignOutSerialNoWithTicketStore.insert(0,model);
					SignOutSerialNoWithTicketStore.insert(1,model1);
					//内物短少信息初始为显示
					sign.sign.setPartFormVisible(form,true);
					sign.sign.resetSignNote();
	    		}else{
	    			SerialNoGridPanel.columns[2].show();
	    			SerialNoGridPanel.columns[2].setText('是否'+records[0].get('valueName'));
					UnNormalSelectAll.setFieldLabel('是否'+records[0].get('valueName')+'全选');
	    			UnNormalSelectAll.setVisible(true);
	    			UnNormalSelectAll.setValue(false);
	    			var count = SerialNoGridPanel.getStore().data.length;
	    			//当只有一个流水号时，默认勾选上
	    			if(count==1){
	    				SerialNoGridPanel.getSelectionModel().selectAll(true);
	    				SerialNoGridPanel.getStore().data.items[0].set('goodShorts',true);
						UnNormalSelectAll.setValue(true);
	    			}
	    			var rstValue=records[0].get('valueName');
	    			if(records[0].get('valueCode')=='UNNORMAL_GOODSHORT'){
						sign.sign.setPartFormVisible(form,true);
	    				//并且数据可编辑
	    				form.findField('packingResult').setDisabled(false);
	    				//这种如果要操作items时候 需要把单个控件拿出来操作
	    				form.findField('packingResult').items.items[0].setDisabled(false);
	    				form.findField('packingResult').items.items[1].setDisabled(false);
	    				form.findField('alittleShort').setDisabled(false);
	    			}else{
	    				sign.sign.COUNT += 1;
	    				if(sign.sign.COUNT==1){
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
		name: 'signGoodsQty',
		xtype:'numberfield',
		labelWidth: 50, 
		hideTrigger: true,
		allowBlank:false,
		fieldLabel:sign.sign.i18n('pkp.sign.sign.signGoodsQty'),//签收件数
		allowDecimals : false,// 不允许有小数点
	    minValue: 1, //最小值为1
		listeners :{
			'blur':function(me,theEvent,eOpts){
				var form = this.up('form').getForm();
				if(form.findField('signStatus').getValue() ==sign.sign.SIGN_STATUS_PARTIAL){// 如果签收情况为部分签收
					if(me.value >= form.getRecord().get('arriveSheetGoodsQty')){
						Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.partSignSignGoodsQtyUnqualified'),'error',1000);//部分签收时签收件数必须小于到达联签收件数，请确认！
						this.selectText(); 
					}
				}
				
			}
		},
		columnWidth:.22
	},{
		xtype: 'datetimefield_date97',
		editable : true,
		format : 'Y-m-d H:i:s',
		id: 'Foss_sign_SignOutStorageFormPanel_SignTime_ID',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.signTime'),//签收时间
		allowBlank:false,
//		value:Ext.Date.format(new Date(),'Y-m-d H:i:s'),
		columnWidth:.38,
		name: 'signTime',
		time : true,
		dateConfig: { 
			el : 'Foss_sign_SignOutStorageFormPanel_SignTime_ID-inputEl',
			minDate: '%y-%M-%d 00:00:00',
			maxDate:'%y-%M-%d 23:59:59'
		}
	},{//隐藏的界面
		xtype:'component',
		columnWidth:.25,
		height:24
	},{//隐藏的界面
		xtype:'component',
		columnWidth:.25,
		height:24
	},{//add 231440  伙伴派送
		name: 'isPartner',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.isPartner'),//伙伴派送(231440)
		allowBlank: true,
		xtype: 'checkbox' ,
		columnWidth:.20,
		handler:function(){
			var form = this.up('form').getForm(),checked = false;
			if(form.findField('isPartner').checked==true){
    			checked = true;
    			this.up('form').getForm().findField('partnerName').setDisabled(false);			//选中伙伴派送伙伴名称显示可填状态(231440)
				this.up('form').getForm().findField('partnerPhone').setDisabled(false);			//选中伙伴派送伙伴手机显示可填状态(231440)
    		}else {
    			this.up('form').getForm().findField('partnerName').setDisabled(true);			//选中伙伴派送伙伴名称显示不可填状态(231440)
    			this.up('form').getForm().findField('partnerName').setValue('');			
    			this.up('form').getForm().findField('partnerPhone').setDisabled(true);			//选中伙伴派送伙伴手机显示不可填状态(231440)
    			this.up('form').getForm().findField('partnerPhone').setValue('');			
    		}
		}
		
	},{
		name:'partnerName',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.partnerName'),//伙伴名称(231440)
		xtype:'textfield',
		labelWidth:60,
		hideTrigger: true,
		columnWidth:.40,
		disabled:true,
		maxLenght:5,
		allowBlank: false	//不允许为空
	},{
		name:'partnerPhone',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.partnerPhone'),//伙伴手机(231440)
		xtype:'numberfield',
		labelWidth:60,
		hideTrigger: true,
		columnWidth:.40,
		disabled:true,
		allowBlank: false,	//不允许为空
		allowDecimals:false,
		allowNegative:false,
		minLength:11,
		maxLength:11
	},{
		fieldLabel: sign.sign.i18n('pkp.sign.settlePayment.packageResult'),//外包装是否完好
		xtype: 'radiogroup',
        vertical: true,
        allowBlank:false,
		columnWidth:.38,
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
		fieldLabel:sign.sign.i18n('pkp.sign.sign.littleShort'),//短少量
		xtype:'textfield',
		labelWidth:50,
		hideTrigger: true,
		columnWidth:.25,
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
					return sign.sign.i18n('pkp.sign.sign.alittleShortMaxLength');
				}else{
					return true;
				}
		},
		listeners: {  
				blur: function(thisCom, eventObject,eOpts) {
					sign.sign.resetSignNote();
	        } 
	    }
	},{
		labelWidth: 60,
		xtype: 'textarea',
		name:'signNote',
		hight: 18,
		allowBlank:false,
		maxLength:200,
		fieldLabel:sign.sign.i18n('pkp.sign.sign.signNote'),//备注
		columnWidth: 1
	}],
	initData: function(arriveSheetGoodsQty,serviceTime,arrivesheet,isDopSign){
		var form = this.getForm(),
		situation = form.findField('situation'),
		signGoodsQty = form.findField('signGoodsQty'),
		signTime = form.findField('signTime'),
		signNote = form.findField('signNote'),
		signStatus =form.findField('signStatus');
		//update
		packingResult = form.findField('packingResult'),
		alittleShort = form.findField('alittleShort');
		//update
		form.reset();
		var value = situation.store.getAt(sign.sign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueCode');// 签收情况 code
		var name = situation.store.getAt(sign.sign.situationStore.find('valueCode','NORMAL_SIGN')).get('valueName');// 签收情况的name
		if(isDopSign){//若是家装运单 ，则默认显示DOP传来的签收信息。 add zhuliangzhi 2015-12-08
			name=arrivesheet.get('signNote');
			value=arrivesheet.get('situation');
			if(Ext.isEmpty(value)){
				value="NORMAL_SIGN";
			}else{
				switch(value){
				case "01": value="NORMAL_SIGN"; break;
				case "02": value="UNNORMAL_BREAK"; break;
				case "03": value="UNNORMAL_DAMP"; break;
				case "04": value="UNNORMAL_POLLUTION"; break;
				case "05": value="UNNORMAL_GOODSHORT"; break;
				case "06": value="UNNORMAL_ELSE"; break;
				case "07": value="UNNORMAL_SAMEVOTEODD"; break;
				default: value="NORMAL_SIGN"; break;
				}
			}
			if(Ext.isEmpty(name)){
				name = situation.store.getAt(sign.sign.situationStore.find('valueCode',value)).get('valueName');
			}
			var valueName_Dop=situation.store.getAt(sign.sign.situationStore.find('valueCode',value)).get('valueName');
			var SerialNoGridPanel_Dop = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID');
			var UnNormalSelectAll_Dop = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
			
			if(value=='NORMAL_SIGN'){//若为正常签收
				SerialNoGridPanel_Dop.columns[2].hide();
				UnNormalSelectAll_Dop.setVisible(false);//正常签收时，内物短少面板隐藏不显示 add zhuliangzhi 2015-12-12
				packingResult.setVisible(false);//设置外包装是否完好不可见
				alittleShort.setVisible(false);//设置短少量不可见
			}else if(value=='UNNORMAL_SAMEVOTEODD'){//若为同票多类异常签收
				SerialNoGridPanel_Dop.hide();
				var SignOutSerialNoWithTicket=Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID');
				Ext.getCmp('Foss.sign.withTicketFormPanel_ID').show();
				//赋值签收流水号数据
				//var signSeriaNoNote = Ext.getCmp('signSeriaNoNote_id');
				var  multiNormalSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID'),
				multiNormalSerNosStore=multiNormalSerNos.getStore();
				multiNormalSerNos.getSelectionModel().deselectAll();
				multiNormalSerNosStore.removeAll();
				if(SerialNoGridPanel_Dop.getStore().data.length>0){
					multiNormalSerNosStore.loadData(SerialNoGridPanel_Dop.getStore().data.items);
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
				var model=Ext.create('Foss.sign.SignOutWithTicketStorageModel',{
					signState:'UNNORMAL_GOODSHORT',
					choose:'选择',
					serialNo:''
				});
				var model1=Ext.create('Foss.sign.SignOutWithTicketStorageModel',{
					signState:'UNNORMAL_BREAK',
					choose:'选择',
					serialNo:''
				});
				SignOutSerialNoWithTicketStore.insert(0,model);
				SignOutSerialNoWithTicketStore.insert(1,model1);
				//内物短少信息初始为显示
				sign.sign.setPartFormVisible(form,true);
				sign.sign.resetSignNote();
			}else{
				packingResult.setVisible(false);//设置外包装是否完好不可见
				alittleShort.setVisible(false);//设置短少量不可见
				Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').columns[2].hide();
				SerialNoGridPanel_Dop.columns[2].show();
				SerialNoGridPanel_Dop.columns[2].setText('是否'+valueName_Dop);
				UnNormalSelectAll_Dop.setFieldLabel('是否'+valueName_Dop+'全选');
				UnNormalSelectAll_Dop.setVisible(true);
				UnNormalSelectAll_Dop.setValue(false);
				var count = SerialNoGridPanel_Dop.getStore().data.length;
				//当只有一个流水号时，默认勾选上
				if(count==1){
					SerialNoGridPanel_Dop.getSelectionModel().selectAll(true);
					SerialNoGridPanel_Dop.getStore().data.items[0].set('goodShorts',true);
					UnNormalSelectAll_Dop.setValue(true);
				}
				var rstValue=valueName_Dop;
				if(value=='UNNORMAL_GOODSHORT'){
					sign.sign.setPartFormVisible(form,true);
					//并且数据可编辑
					form.findField('packingResult').setDisabled(false);
					//这种如果要操作items时候 需要把单个控件拿出来操作
					form.findField('packingResult').items.items[0].setDisabled(false);
					form.findField('packingResult').items.items[1].setDisabled(false);
					form.findField('alittleShort').setDisabled(false);
				}
			}

		}else{//不是家装运单 add zhuliangzhi 2015-12-08
			packingResult.setVisible(false);//设置外包装是否完好不可见
			alittleShort.setVisible(false);//设置短少量不可见
			Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').columns[2].hide();
		}
		situation.setValue(value);// 签收情况默认选中“正常签收”
		signStatus.setValue("SIGN_STATUS_ALL");// 签收状态默认选中“全部签收”
		signGoodsQty.setValue(arriveSheetGoodsQty);// 签收件数默认为到达联件数
		signGoodsQty.setReadOnly(true);// 签收件数为不可编辑
		signTime.setValue(serviceTime);
		signNote.setVisible(true);//设置签收备注可见
		signNote.setValue(name);// 备注
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
    emptyText:sign.sign.i18n('pkp.sign.sign.emptyText'),//查询结果为空
	columnLines: true,
	height: 300,//高度
	id: 'Foss_sign_SerialNoOutStorageGridPanel_ID',
	frame: true,
    enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel',{
		listeners: {
			deselect: function(model,record,index) {//取消选中时产生的事件
				var form=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();
				var situation=form.findField('situation').getValue();
				var UnNormalSelectAll = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID');
				//签收情况不为'正常签收'和'同票多类异常'时
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
					Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').getStore().data.items[index].set('goodShorts',false);	
					UnNormalSelectAll.setValue(false);
				}
		},
			select: function(model,record,index) {
				var form=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();
				var situation=form.findField('situation').getValue();
				var count=Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').getStore().data.length;
				//签收情况不为'正常签收'和'同票多类异常'时
				if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
					if(count=='1'){
						Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').getStore().data.items[0].set('goodShorts',true);
					}
				}
			}
		}
	}),
    title:sign.sign.i18n('pkp.sign.sign.signPieces'),//签收件
	columns: [
        {header: sign.sign.i18n('pkp.sign.sign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'},//流水号
        //update begin
        {header: sign.sign.i18n('pkp.sign.sign.goodShorts'), dataIndex: 'goodShorts', xtype:'checkcolumn',flex: 1,align: 'center', stopSelection:true,
		listeners:{
						'checkchange': function(model,record,index){
						var SerialNoGridPanel = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID');
						var state=SerialNoGridPanel.getSelectionModel().isSelected(record);
						var form=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();
						var situation=form.findField('situation').getValue();
						var count=SerialNoGridPanel.getStore().data.length;
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
					fieldLabel:sign.sign.i18n('pkp.sign.sign.start'),//从
					xtype:'numberfield',
					labelWidth:28,
					maxValue:9999,
					minValue:1,
					hideTrigger: true,
					columnWidth:.2,
					allowNegative : false,//不允许输入负数
					allowDecimals : false//不允许有小数点
				},
				{
					name:'endSerialNo',
					fieldLabel:sign.sign.i18n('pkp.sign.sign.end'),//到
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
					text :sign.sign.i18n('pkp.sign.sign.choose'),// 选中
					handler:function(grid, rowIndex, colIndex){
						var startSerialNo =this.up('toolbar').items.items[0].value,
							endSerialNo =this.up('toolbar').items.items[1].value,
							me = this.up('toolbar').up('grid'),
							store = me.getStore(),
							records = store.getRange(),
							choose = [];
						if(Ext.isEmpty(startSerialNo) || Ext.isEmpty(endSerialNo)){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), sign.sign.i18n('pkp.sign.sign.rangeMustEnter'), 'error', 4000);//区间必须输入
							return;
						}
						if(startSerialNo>9999 || endSerialNo >9999){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), sign.sign.i18n('pkp.sign.sign.largeNumber'), 'error', 4000);//数字最大范围是9999
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
					id : 'Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID',
					hidden :true,
					allowBlank : true,
					listeners : {
						change : function(field , newValue , oldValue , object){
							var StorageGridPanel = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID');
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
		me.store = Ext.create('Foss.sign.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

// 同票多类异常里的无异常签收流水号GridPanel
Ext.define('Foss.sign.MultiExceptionNormalSerialNoGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.sign.i18n('pkp.sign.sign.emptyText'),//查询结果为空
	columnLines: true,
	height: 230,//高度
	id: 'Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID',
	frame: true,
    enableColumnHide: false,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
    title:'无差异流水号',//签收件
	columns: [
        {header: sign.sign.i18n('pkp.sign.sign.serialNumber'), dataIndex: 'serialNo', flex: 1,align: 'center'}
    ],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.SerialNoSignOutStorageStore');
		me.callParent([cfg]);
	}
});

//同票多类
Ext.define('Foss.sign.MultiExceptionSignPiecesForm',{
	extend: 'Ext.form.Panel',
	title:'签收件',
    defaultType : 'textfield',
	layout: 'column',
	frame:true,
	id: 'Foss.sign.withTicketFormPanel_ID',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	signOutWithTicketStorageGridPanel :null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			if(Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID') );
			}
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.SignOutWithTicketStorageGridPanel',{
				width:528
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},
	multiExceptionNormalSerialNoGridPanel :null,
	getMultiExceptionNormalSerialNoGridPanel: function(){
		if(this.multiExceptionNormalSerialNoGridPanel == null){
		
		if(Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID') );
		}
		if(Ext.getCmp('Foss.sign.withTicketFormPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss.sign.withTicketFormPanel_ID') );
		}
			this.multiExceptionNormalSerialNoGridPanel = Ext.create('Foss.sign.MultiExceptionNormalSerialNoGridPanel',{
				width:528
			});
		}
		return this.multiExceptionNormalSerialNoGridPanel;
	},
	listeners : {
		beforerender:function(){
				Ext.getCmp('Foss.sign.withTicketFormPanel_ID').hide();
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

//gird————
var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});
Ext.define('Foss.sign.SignOutWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    emptyText:sign.sign.i18n('pkp.sign.sign.emptyText'),//查询结果为空
	id: 'Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID',
	frame: false,
	selModel: {selType:'cellmodel'},
	//收缩
	//collapsible: true,
   // title:'流水号明细',//流水号明细
	plugins:[cellEditing],
	viewConfig: {
        forceFit : true
	},
	listeners:{
	 		'edit':function(th,e){
	 			sign.sign.editor(th,e);
	 			
	 		}
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
					multiNormalSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore();
					//signSeriaNoNote = Ext.getCmp('signSeriaNoNote_id');
					if(!Ext.isEmpty(signState) && signState=='UNNORMAL_GOODSHORT'){
							var form=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();
							sign.sign.setPartFormVisible(form,false);		
					}
					if(!Ext.isEmpty(serialNo)){
						/*if(!Ext.isEmpty(signSeriaNoNote.getValue())){
							signSeriaNoNote.setValue(signSeriaNoNote.getValue().concat(',').concat(serialNo));
						}else{
							signSeriaNoNote.setValue(serialNo);
						}*/
						var serialNoArray=serialNo.split(',');
							for(var i=0;i<serialNoArray.length;i++){
								var newRecord={serialNo:serialNoArray[i]}; 
								multiNormalSerNosStore.insert(multiNormalSerNosStore.getCount(),newRecord); 
							}
					}
					store.removeAt(rowIndex);
					if (!Ext.isEmpty(signState)) {
						sign.sign.resetSignNote();
					}
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
					store :sign.sign.exsituationStore,
					listeners : {
						'change' : function(field,newValue, oldValue, eOpts){
							if(newValue == oldValue){
								return false;
							}
							/*var arr = this.up('panel').getStore().collect('signState'),
							    flag = arr.indexOf(newValue),
							    form=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm();
							if(oldValue=='UNNORMAL_GOODSHORT'&& !Ext.isEmpty(newValue)){
								sign.sign.setPartFormVisible(form,false);
							}
							if(flag>=0){
								this.setValue('');
								Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), '签收情况已被选择', 'error', 3000);
								return false;
							}
							if(newValue=='UNNORMAL_GOODSHORT'){
								sign.sign.setPartFormVisible(form,true);
							}*/
						}
					}
			},
			renderer:function(value){  
                return FossDataDictionary.rendererSubmitToDisplay(value, sign.sign.PKP_SIGN_SITUATION);  
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
							// signSeriaNoNote = Ext.getCmp('signSeriaNoNote_id'),
							 multiNormalSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID'),
							 multiNormalSerNosStore=multiNormalSerNos.getStore(),
							// signSeriaNoNoteVal = signSeriaNoNote.getValue(),
							 showSerials=new Array(),
							 chooseModel=new Array();
							var win ;
							var seriaNoGrid ;
							var serialNoOutStorage;
							if(!Ext.getCmp('Foss_sign_WithTicketStoraWindow_ID')){
								win = Ext.create('Foss.sign.WithTicketStoraWindow');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}else{
							    win = Ext.getCmp('Foss_sign_WithTicketStoraWindow_ID');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}
							if(!Ext.isEmpty(serialNo)) {
								var serialNoArray=serialNo.split(',');
								for(var i=0;i<serialNoArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.sign.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':serialNoArray[i],'isSelect':true});
									chooseModel.push(showSerials);
								}
								
							}
							/*if(!Ext.isEmpty(signSeriaNoNoteVal)) {
								var signSeriaNoNoteArray=signSeriaNoNoteVal.split(',');
								for(var i=0;i<signSeriaNoNoteArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.sign.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':signSeriaNoNoteArray[i],'isSelect':false});
								}
							}*/
							if(multiNormalSerNosStore.data.length >0){
								for(var i=0;i<multiNormalSerNosStore.data.length;i++){
									showSerials.push(
									{'waybillNo':sign.sign.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':multiNormalSerNosStore.data.items[i].data.serialNo,'isSelect':false});
								}
							}
							serialNoOutStorage.loadData(showSerials,true);
							sign.sign.SIGN_RECORD_SERINALNO_INDEX=index;
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
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), '签收情况没有被选择', 'error', 3000);
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
						var count = sign.sign.exsituationStore.getCount();
						var panelGridCount = this.up('panel').getStore().getCount();
						if(panelGridCount>=count){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'签收情况已全部选择,不可再添加!','error',1000);
							return false;
						}
						var model=Ext.create('Foss.sign.SignOutWithTicketStorageModel',{
							signState:'',
							choose:'选择',
							serialNo:''
						});
						var store=this.up('panel').getStore();
						store.insert(0,model);
						cellEditing.startEditByPosition({row: 0, column: 0});
					}
				}]
	}],
    viewConfig: {
        enableTextSelection: true
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.sign.SignOutStateWithTicketStorageStore');
		me.callParent([cfg]);
	}
});
//store
Ext.define('Foss.sign.SignOutStateWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SignOutWithTicketStorageModel',
	listeners:{
	
	}
});
//gird
Ext.define('Foss.sign.SerialNoWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.sign.i18n('pkp.sign.sign.emptyText'),//查询结果为空
	height: 300,//高度
	id: 'Foss_sign_SerialNoWithTicketMulStorageGridPanel',
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
		me.store = Ext.create('Foss.sign.SerialNoWithTicketStorageStore');
		me.callParent([cfg]);
	},
	listeners:{
	}
});
//store
Ext.define('Foss.sign.SerialNoWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SerialNoWithTicketStorageModel'
	});
//window
// 弹出窗口
Ext.define('Foss.sign.WithTicketStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_WithTicketStoraWindow_ID',
	closeAction: 'hide',
	layout: 'auto',	
	width:300,
	resizable : false,
	title:'流水号选择',//流水号选择
	modal: true,
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.SerialNoWithTicketStorageGridPanel',{
				width:270,
				text : sign.sign.i18n('pkp.sign.sign.signPieces')//签收件
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
				var SerialNoWithTicket =Ext.getCmp('Foss_sign_SerialNoWithTicketMulStorageGridPanel'),
					serialNos = SerialNoWithTicket.getSelectionModel().getSelection(),
					SerialNoWithTicketStore = SerialNoWithTicket.getStore().data,
					frontGrid=Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID'),
					frontGrid_Store=frontGrid.getStore(),
					multiNormalSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID'),
					multiNormalSerNosStore=multiNormalSerNos.getStore(),
					//signSeriaNoNote = Ext.getCmp('signSeriaNoNote_id');
					selectSerialNos="",
					deselectSerialNos =new Array();
					//var deselectSerialNos="";
				if(serialNos.length<=0){
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), '请选择各类型异常流水号!', 'error', 1000);
						return false;
				}
				for(var i=0;i<SerialNoWithTicketStore.length;i++){
					if(SerialNoWithTicketStore.items[i].data.isSelect){
						selectSerialNos+=SerialNoWithTicketStore.items[i].data.serialNo;
						selectSerialNos+=',';
					}else{
						//multiNormalSerNosStore.getAt();
						//deselectSerialNos+=SerialNoWithTicketStore.items[i].data.serialNo;
						//deselectSerialNos+=',';
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
				/*if(!Ext.isEmpty(deselectSerialNos)){
					signSeriaNoNote.setValue(deselectSerialNos.substring(0,deselectSerialNos.length-1));
				}else{
					signSeriaNoNote.setValue("");
				}*/
				if(!Ext.isEmpty(selectSerialNos)){
					frontGrid_Store.data.items[sign.sign.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',selectSerialNos.substring(0,selectSerialNos.length-1));
				}else{
					frontGrid_Store.data.items[sign.sign.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',"");
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
//弹出提示窗口
Ext.define('Foss.sign.SiginHintWindow',{
	extend:'Ext.window.Window',
	id:'Foss.sign.SiginHintWindow_ID',
	title:'提示信息',
	closeAction:'close',
	layout:'auto',
	width:300,
	resizable:false,
	modal:true,
	signOutHintPanel :null,
	getSignOutHintPanel:function(){
		if(this.signOutHintPanel==null){
			this.signOutHintPanel = Ext.create('Foss.sign.SignOutHintPanel',{
				width:260,id:'Foss.sign.SignOutHintPanel_ID'
			});
		}
		return this.signOutHintPanel;
	},
	resetPanel :  function(arrivesheet){
		var form = this.getSignOutHintPanel();
		form.getForm().loadRecord(arrivesheet);
//		form.initData(arrivesheet.get('waybillNo'),arrivesheet.get('signTime'));
		form.initData(arrivesheet.waybillNo,arrivesheet.signTime);
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		            me.getSignOutStorageFormPanel()
		          ];
		me.callParent([cfg]);
	}
})

//弹出窗口
Ext.define('Foss.sign.SiginOutStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_SiginOutStoraWindow_ID',
	closeAction: 'close',
	layout: 'auto',	
	width:615,
	resizable : false,
	//align: 'center',
	title:sign.sign.i18n('pkp.sign.sign.signInformation'),//签收信息
	modal: true,
	signOutStorageFormPanel :null,
	getSignOutStorageFormPanel: function(){
		if(this.signOutStorageFormPanel == null){
		if(Ext.getCmp('Foss_sign_SignOutStorageFormPanel_SignTime_ID')!=null){
			Ext.destroy(Ext.getCmp('Foss_sign_SignOutStorageFormPanel_SignTime_ID') );
		}
		if(Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID')!=null){
			Ext.destroy(Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID') );
		}
		if(Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID')!=null){
			Ext.destroy(Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID') );
		}
		
		this.signOutStorageFormPanel = Ext.create('Foss.sign.SignOutStorageFormPanel',{
			width:580,id:'Foss.sign.SignOutStorageFormPanel_ID'
		});
		}
		return this.signOutStorageFormPanel;
	},
	signWithTicketFormPanel:null,
	getSignWithTicketFormPanel:function(){
		if(this.signWithTicketFormPanel == null){
			this.signWithTicketFormPanel = Ext.create('Foss.sign.MultiExceptionSignPiecesForm',{
				width:580
			});
		}
		return this.signWithTicketFormPanel;
	},
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			if(Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID') );
			}
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.SerialNoOutStorageGridPanel',{
				width:580,
				text : sign.sign.i18n('pkp.sign.sign.signPieces')//签收件
			});
		}
		return this.SerialNoOutStorageGridPanel;
	},
	resetPanel :  function(arrivesheet,dop_signGoodsQty){
		sign.sign.COUNT=0;
		var form = this.getSignOutStorageFormPanel(),
		serialNoOutStorage = this.getSerialNoOutStorageGridPanel().getStore(),
		serialNoQuery =this.getSerialNoOutStorageGridPanel().down('toolbar');
		this.getSerialNoOutStorageGridPanel().getSelectionModel().deselectAll();
		form.getForm().loadRecord(arrivesheet);
		sign.sign.IS_CURRENT_ORG_CODE_RECEIVE_ORG_CODE_WVH=arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH');
		var isDopSign=false;
		/*if(!Ext.isEmpty(dop_signGoodsQty)){
		isDopSign=true;
		form.initData(dop_signGoodsQty,arrivesheet.get('serviceTime'),arrivesheet,isDopSign); 
	}else{
		form.initData(arrivesheet.get('arriveSheetGoodsQty'),arrivesheet.get('serviceTime'),arrivesheet,isDopSign); 
	} */
		if(!Ext.isEmpty(dop_signGoodsQty)){
			isDopSign=true;
		}
		form.initData(arrivesheet.get('arriveSheetGoodsQty'),arrivesheet.get('serviceTime'),arrivesheet,isDopSign); 

		//清除运单流水号的记录
		serialNoOutStorage.removeAll();	
		serialNoQuery.items.items[0].setValue('');
		serialNoQuery.items.items[1].setValue('');
		Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_UnNormalSelectAll_ID').setVisible(false);
		if(Ext.isEmpty(arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH'))||'Y'!=arrivesheet.get('isCurrentOrgCodeReceiveOrgCodeWVH')){
			// 根据运单编号查询运单流水号
			serialNoOutStorage.load({
				params:{
					'signVo.signDto.waybillNo':arrivesheet.get('waybillNo')
				}
			});
		}
		//记录一下全局的运单号
		sign.sign.SIGN_RECORD_WAYBILLNO_INDEX=arrivesheet.get('waybillNo');
		//隐藏同票多类对应的form
		Ext.getCmp('Foss.sign.withTicketFormPanel_ID').hide();
		Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').show();
	},	
	serialButtons  : null,
	getSerialButtons: function(){
		if(this.serialButtons  == null){
			this.serialButtons = Ext.create('Ext.button.Button',{
			text:sign.sign.i18n('pkp.sign.sign.signIn'),//签收 
			disabled:!sign.sign.isPermission('signoutstorageindex/signoutstorageindexsignbutton'),
			hidden:!sign.sign.isPermission('signoutstorageindex/signoutstorageindexsignbutton'),
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
				var signOutStorageForm=Ext.getCmp('Foss.sign.SignOutStorageFormPanel_ID').getForm(),
				    situation=signOutStorageForm.findField('situation').getValue(),
				    form=this.up('window').signOutStorageFormPanel.getForm(),
				    signNote=null,
				    samevoteoddIsGoodsShort=false,
				    serialNos = new Array(),
				    arriveSheet = form.getRecord();
					// 验证数字不能小于0时的提示信息
				if(form.findField('signGoodsQty').getValue()==0||form.findField('signGoodsQty').getValue()==null){
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.signGoodsQtyGreateThanZero'),'error',1000);
					return;
				}
				var checkCount = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection().length;
				if(sign.sign.IS_CURRENT_ORG_CODE_RECEIVE_ORG_CODE_WVH!=null &&sign.sign.IS_CURRENT_ORG_CODE_RECEIVE_ORG_CODE_WVH=='Y'){
						
				}else {
					if(situation!='UNNORMAL_SAMEVOTEODD'){
						//如果选择的流水号数量与签收件数不一致
						if(form.findField('signGoodsQty').getValue()!=checkCount){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.serialNoSignGoodsQtySame'),'error',1000);//选择流水号数量必须与签收件数一致，请确认！
							return;
						}
					}
					if(situation!='NORMAL_SIGN' && situation!='UNNORMAL_SAMEVOTEODD'){
						var flag=true;
						var arr=Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').getSelectionModel().getSelection(); 
						for(var i=0;i<arr.length;i++){
							flag=arr[i].get('goodShorts');
							if(flag==true){
								break;
							}
						}
						if(flag==false){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'是否'+signOutStorageForm.findField('situation').getRawValue()+'至少勾选一个！','error',2000);
							return ;
						}
					}
				}
				if(situation=='UNNORMAL_SAMEVOTEODD'){//如果签收情况为同票多类异常
					var SignOutSerialNoWithTicketStore=Ext.getCmp('Foss_sign_SignOutSerialNoWithTicketMulStorageGridPanel_ID').getStore(),
					//signSeriaNoNoteVal =Ext.getCmp('signSeriaNoNote_id').getValue(),
					multiNormalSelectSerNos=Ext.getCmp('Foss_sign_MultiExceptionNormalSerialNoGridPanel_ID').getSelectionModel().getSelection(); 				
					if(SignOutSerialNoWithTicketStore.data.length<2){
						Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'请至少选择两种异常签收情况！','error',3000);
						return ;
					}
					for(var i=0;i<SignOutSerialNoWithTicketStore.data.length;i++){
						var serialSituation =SignOutSerialNoWithTicketStore.getAt(i).data.signState,
						serialNo=SignOutSerialNoWithTicketStore.getAt(i).data.serialNo
						if(Ext.isEmpty(serialSituation)){//如果签收情况为空
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'签收件里的签收情况不能为空！','error',3000);
							return ;
						}
						if(Ext.isEmpty(serialSituation)){//如果签收情况为空
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'签收件里的签收情况不能为空！','error',3000);
							return ;
						}
						if(serialSituation =='UNNORMAL_GOODSHORT'){
							samevoteoddIsGoodsShort=true;//同票多类异常里有选择异常－内物短少
						}
						if(Ext.isEmpty(serialNo)||serialNo.indexOf(',')==0){//如果签收流水号为空
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'签收件里的异常流水号不能为空！','error',3000);
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
						Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.serialNoSignGoodsQtySame'),'error',1000);//选择流水号数量必须与签收件数一致，请确认！
						return;
					}
				}
				//如果签收情况为部分签收，并且该单为家装运单，系统弹窗提示错误
				if(form.findField('signStatus').getValue()== sign.sign.SIGN_STATUS_PARTIAL && 
						!Ext.isEmpty(form.getRecord().get('specialValueAddedService'))){
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),'家装运单不允许部分签收，请选择全部签收操作','error',2000);//家装运单不允许部分签收，请选择全部签收操作
					return;
				}
				// 如果签收情况为部分签收，并且签收件数大于到达联件数，系统弹窗提示错误
				if(form.findField('signStatus').getValue()== sign.sign.SIGN_STATUS_PARTIAL &&form.findField('signGoodsQty').
						getValue()>= form.getRecord().get('arriveSheetGoodsQty')){
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.partSignSignGoodsQtyUnqualified'),'error',2000);//部分签收时签收出库件数必须小于到达联签收件数，请确认！
					return;
				}
				
				//是伙伴派送时,伙伴名称与伙伴手机不能为空
				if(form.findField('isPartner').checked==true){
	    			if(!form.findField('partnerName').getValue() || form.findField('partnerName').getValue().length>5){
	    				Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.PartnerNameNotNull'),'error',2000);//伙伴名称输入错误，不能为空且不能超过5位汉字，请正确输入！
	    				return;
	    			}else if(!form.findField('partnerPhone').getValue() || (form.findField('partnerPhone').getValue()+"").length != 11){
	    				Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.PartnerPhoneNotNull'),'error',2000);//伙伴手机输入错误，不能为空且手机号码只能为11位，请正确输入！
	    				return;
	    			}else{
	    				arriveSheet.set('isPartner','Y');
	    				arriveSheet.set('partnerName',form.findField('partnerName').getValue());
	    				arriveSheet.set('partnerPhone',form.findField('partnerPhone').getValue());
	    			}
				}else{//不是合作伙伴
					arriveSheet.set('isPartner','N');
    				arriveSheet.set('partnerName',null);
    				arriveSheet.set('partnerPhone',null);
				}
				
				// 签收时间为空，系统弹窗提示！
				if(!form.findField('signTime').getValue()){
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.signTimeNotNull'),'error',2000);//签收时间不能为空，请录入签收时间！
					return;
				}
				if(form.findField('situation').getValue()=='UNNORMAL_GOODSHORT'||samevoteoddIsGoodsShort==true){
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
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.alittleShortMaxLength'),'error',3000);
							return;
						}
					}else{
						if(!form.findField('alittleShort').isValid()){
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.alittleShortNotNull'),'error',3000);
							return;
						}
					}
					//校验外包装是否完好是否为空 如果为空 不让确认
					if(!form.findField('packingResult').isValid()){
						Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.packingResultNotNull'),'error',3000);
						return;
					}
				}
				if(!form.findField('signNote').getValue() || (!form.findField('signNote').isValid())){// 签收备注为空，系统弹窗提示！
					Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.signNoteNotNull'),'error',1000);//签收备注不能为空，请确认！
					return;
				}	
					Ext.MessageBox.confirm(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.sureSerialNoInfo'),function(btn){//是否确定签收所选择的流水信息?
						if(btn == 'yes'){
							var serialNorowObjs = Ext.getCmp('Foss_sign_SerialNoOutStorageGridPanel_ID').
								getSelectionModel().getSelection();//得到选中的流水号信息
							
								form.updateRecord(arriveSheet);//得到更新后的签收信息
								
							if(form.findField('isPartner').checked==true){//到达联伙伴信息赋值  By:231440
								arriveSheet.set('isPartner',"Y");
								}else{
									arriveSheet.set('isPartner',"N");
								}
							
							arriveSheet.set('signTime',Ext.Date.parse(form.findField('signTime').getValue(), "Y-m-d H:i:s", true));
							if(signNote != null){
								arriveSheet.set('signNote',signNote);
							}
							var situation=signOutStorageForm.findField('situation').getValue();
							if(situation!='UNNORMAL_GOODSHORT'&& samevoteoddIsGoodsShort==false){
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
										}else {
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
										'isWholeVehicle' : arriveSheet.data.isWholeVehicle,// 是否整车运单
										'signSituation' : situation//add by 353654 签收情况
										};
							Ext.Ajax.request({
								url:sign.realPath('addSign.action'),
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
									Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),json.message,'ok',4000);
									Ext.getCmp('Foss_sign_SiginOutStoraWindow_ID').close();
									// 更新查询结果里表格的记录
									Ext.getCmp('T_sign-signOutStorageIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
								},exception: function(response){
									var json = Ext.decode(response.responseText);
			              			Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'), json.message, 'error', 4000);
								}
							});
						}else{
							//已取消签收
							Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),sign.sign.i18n('pkp.sign.sign.signCanceled'),'ok',1000);
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
Ext.define('Foss.sign.QuerysignOutStorageFormPanel',{
	extend: 'Ext.form.Panel',
	title:sign.sign.i18n('pkp.sign.sign.queryBuilder'),// 查询条件
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
		fieldLabel:sign.sign.i18n('pkp.sign.sign.waybillNo'),//运单号
		name: 'waybillNo',//运单号
		vtype: 'waybill',
		columnWidth:.25
	},{
		name: 'arrivesheetNo',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.arrivesheetNo'),//到达联编号
		columnWidth:.25
	},{
		name: 'receiveCustomerName',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.receiveCustomerName'),//收货人
		columnWidth:.25
	},{
		name: 'receiveCustomerPhone',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.receiveCustomerPhone'),//收货人电话
		columnWidth:.25
	},{
		name: 'receiveCustomerMobilephone',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.receiveCustomerMobilephone'),//收货人手机
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		fieldLabel:sign.sign.i18n('pkp.sign.sign.settleTime'),//结清时间
		fieldId:'Foss_sign_QuerysignOutStorageFormPanel_rangeDateField_ID',
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
		fieldLabel:sign.sign.i18n('pkp.sign.sign.noMentionGoods'),//所有未提货物
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
			text:sign.sign.i18n('pkp.sign.sign.reset'),//重置
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
			text:sign.sign.i18n('pkp.sign.sign.search'),//查询
			cls:'yellow_button',
			disabled:!sign.sign.isPermission('signoutstorageindex/signoutstorageindexquerybutton'),
			hidden:!sign.sign.isPermission('signoutstorageindex/signoutstorageindexquerybutton'),
			columnWidth:.08,
			plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
				  //设定间隔秒数,如果不设置，默认为2秒
				  seconds: 3
			}),
			handler:function(){
				Ext.getCmp('T_sign-signOutStorageIndex_content').getArriveGrid().getPagingToolbar().moveFirst();
			}
		}]
	}]
});

	
// 待处理-GridPanel---第一层
Ext.define('Foss.sign.QuerysignOutStorageGridPanel',{
	extend:'Ext.grid.Panel',
    bodyCls: 'autoHeight',
	cls: 'autoHeight',
    frame: true,
	id: 'QuerysignOutStorage_GridPanel_Id',
    stripeRows: true,
    title:sign.sign.i18n('pkp.sign.sign.pending'),//'待处理'
    emptyText:sign.sign.i18n('pkp.sign.sign.emptyText'),//查询结果为空'
	collapsible: true,
	animCollapse: true,
	store: null,
	viewConfig: {
        enableTextSelection: true
    },
	siginOutStoraWindow : null,
	getSiginOutStoraWindow : function(){
		if(this.siginOutStoraWindow == null){
			if(Ext.getCmp('Foss_sign_SiginOutStoraWindow_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_SiginOutStoraWindow_ID') );
			}
			this.siginOutStoraWindow = Ext.create('Foss.sign.SiginOutStoraWindow',{
			});
		}
		return this.siginOutStoraWindow;
	},
	signOutHintWindow:null,
	getSignOutHintWindow : function(){
		if(this.signOutHintWindow == null){
			if(Ext.getCmp('Foss_sign_SignOutHintWindow_ID')!=null){
				Ext.destroy(Ext.getCmp('Foss_sign_SignOutHintWindow_ID') );
			}
			this.siginOutStoraWindow = Ext.create('Foss.sign.SiginHintWindow',{
			});
		}
		return this.signOutHintWindow;
	},
	columns: [
			{
				xtype:'actioncolumn',
				text: sign.sign.i18n('pkp.sign.sign.operate'),//操作
				width: 100,
				align: 'center',
				items: [{
					iconCls: 'deppon_icons_signin',
					tooltip: sign.sign.i18n('pkp.sign.sign.signIn'),//签收
					disabled:!sign.sign.isPermission('signoutstorageindex/signoutstorageindexquerydetailbutton'),
					handler: function(grid, rowIndex, colIndex) {
						var arrivesheet = grid.getStore().getAt(rowIndex);
						//TODO 拿字段判断是否家装
						//发请求拿数据 
						//重写赋值方法，判断如果是简装 调新赋值方法；不是家装调原方法
						if(!Ext.isEmpty(arrivesheet.data.specialValueAddedService)){//判断是否特殊增值服务（增值服务字段:以‘_EQUIP’结尾为家装运单）
							Ext.Ajax.request({
								url:sign.realPath('queryDopCacheInfo.action'),
								timeout: 180000,
								params:{
									'signVo.signDto.waybillNo':arrivesheet.data.waybillNo// 运单号
								},
								success:function(response){
									var json = Ext.decode(response.responseText);
									if(!Ext.isEmpty(json.signVo.dopEntity)){//是dop传来的运单
										sign.sign.DOPSIGNQTY=json.signVo.dopEntity.signGoodsQty;
										arrivesheet.set('serviceTime',Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
												,new Date().getHours(),new Date().getMinutes(),new Date().getSeconds()),'Y-m-d H:i:s'));
										arrivesheet.data.signNote=json.signVo.dopEntity.signNote  ;
										arrivesheet.data.situation=json.signVo.dopEntity.signStatus  ;
										arrivesheet.set('signTime',json.signVo.dopEntity.signTime);
										var window = grid.up('gridpanel').getSiginOutStoraWindow();
										window.show();
										window.resetPanel(arrivesheet,sign.sign.DOPSIGNQTY);
									}else if(json.signVo.rfcFlag){//家装运单反签收过的运单
										var window = grid.up('gridpanel').getSiginOutStoraWindow();
										window.show();
										window.resetPanel(arrivesheet,null);
									}else {//不是dop传来的运单，弹出提示“此运单为家装运单，客户未签收”
										Ext.MessageBox.alert("提示 ","此运单为家装运单，客户未签收");
									}
								},
								exception: function(response){
									var json = Ext.decode(response.responseText);
									Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),json.message,'error',4000);
								}
							});
						}else{//不是特殊增值服务
							var window = grid.up('gridpanel').getSiginOutStoraWindow();
							window.show();
							window.resetPanel(arrivesheet,null);
						}
					}
				},{
					iconCls:'deppon_icons_pickUpInventory',
					tooltip: sign.sign.i18n('pkp.sign.sign.goodsInadvance'),//提前找货
					disabled:!sign.sign.isPermission('signoutstorageindex/inadvancegoodsindexquerydetailbutton'),
					handler: function(grid, rowIndex, colIndex) {
						var arrivesheet = grid.getStore().getAt(rowIndex);
						if(!Ext.isEmpty(arrivesheet.data.waybillNo)){
							Ext.Ajax.request({
								url:sign.realPath('updateInadvanceGoodsBySign.action'),
								timeout: 180000,
								params:{
									'signVo.signDto.waybillNo':arrivesheet.data.waybillNo// 运单号
							},
					    	success:function(response){
						    	var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),json.stateMsg,'ok',4000);
				    		},
				    		exception: function(response){
								var json = Ext.decode(response.responseText);
								Ext.ux.Toast.msg(sign.sign.i18n('pkp.sign.sign.tip'),json.message,'error',4000);
							}
						});
					}
                }
            }
			]
        },
		{ text : sign.sign.i18n('pkp.sign.sign.waybillNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'waybillNo' },//运单编号
		{ text : sign.sign.i18n('pkp.sign.sign.arrivesheetNo'),align: 'center',flex: 1,xtype: 'ellipsiscolumn',dataIndex : 'arrivesheetNo' }, //到达联编号
		{ text : sign.sign.i18n('pkp.sign.sign.arriveSheetGoodsQty'),align: 'center',flex: 1,dataIndex : 'arriveSheetGoodsQty' },//到达联件数
		{ text : sign.sign.i18n('pkp.sign.sign.stockGoodsQty'),align: 'center',flex: 1,dataIndex : 'stockGoodsQty' },//库存件数
		{ text : sign.sign.i18n('pkp.sign.sign.deliverymanName'),align: 'center',flex: 1,dataIndex : 'deliverymanName' },//提货人
		{ text : sign.sign.i18n('pkp.sign.sign.identifyCode'),align: 'center',width : 150,dataIndex : 'identifyCode' },//证件号码
		{ text : sign.sign.i18n('pkp.sign.sign.serviceTime'),align: 'center',width : 150,dataIndex : 'serviceTime',hidden:true,hideable:false} //后台服务器当前时间
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
		me.store = Ext.create('Foss.sign.SignOutStorageStore');
		me.bbar = me.getPagingToolbar();
		me.callParent([cfg]);
	}
});

Ext.onReady(function() {
	var queryForm =  Ext.create('Foss.sign.QuerysignOutStorageFormPanel');
	var queryResultGrid = Ext.create('Foss.sign.QuerysignOutStorageGridPanel');
	// // 定义到达联查询列表
	Ext.create('Ext.panel.Panel',{
		id:'T_sign-signOutStorageIndex_content',
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
		renderTo: 'T_sign-signOutStorageIndex-body'
	});
	
});

 