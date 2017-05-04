sign.applicationSignRfc.goodShortsCheckBox_Value =null; //记录goodShortsCheckBox的原始值
sign.applicationSignRfc.goodShortsCheckBox_KeyValue =new Ext.util.HashMap(); //记录goodShortsCheckBox的原始值 流水号和checkBox值对应
sign.applicationSignRfc.serialNoSituation_KeyValue =new Ext.util.HashMap(); //记录TagNumberGrid中被选中行的流水号和签收情况的对于关系
sign.applicationSignRfc.SIGN_SERIAL_NO_ARR =null;  //记录到达联Grid被选中行的全部流水号
sign.applicationSignRfc.SIGN_SERIAL_NO_ARR_ODD =null;  //记录到达联Grid被选中行的剩余的流水号
sign.applicationSignRfc.SIGN_RECORD_SERINALNO_INDEX=null;
sign.applicationSignRfc.SIGN_RECORD_WAYBILLNO=null;//记录到达联Grid被选中行的运单号
sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo=null;  //TagNumberGrid变更前的信息
sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo=null;  //TagNumberGrid变更后的信息
sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=null; //SignOutWithTicketStorageGridPanel变更前的信息
sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=null; //SignOutWithTicketStorageGridPanel变更后的信息
//记录到达联信息Form中签收情况的历史值
sign.applicationSignRfc.arriveSheetForm_SignNoteCheckValue=false;
//初始化TagNumberGrid数据--签收情况为异常时(除正常签收和同票多类异常之外)
sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL=null;
//初始化TagNumberGrid数据--签收情况为正常签收
sign.applicationSignRfc.TagNumberGrid_Data_NORMAL=null;
//初始化同票多类异常Grid数据--签收情况为同票多类异常
sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD=null;
//初始化同票多类异常TextArea数据--签收情况为同票多类异常
sign.applicationSignRfc.MultiExceptionForm_TextAreaData_SAMEVOTEODD=null;
//记录TagNumberGrid第二列中checkBox中被选中的所有行的信息用于放到变更Grid中,只有在TagNumberGrid中数据没有被用户修改时才会插入到变更Grid中
sign.applicationSignRfc.serialNoSituation_SelectedKeyValue=null;
//用于记录到达联Grid中的签收情况是否是'select'事件之后触发了Blur事件
sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent=null;
sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent_ChangeInfoArr=null;
//记录到达联Grid中被选中行的签收情况
sign.applicationSignRfc.arriveSheetGrid_SituationValue=null;


/*
function checkboxClick(flag){
	var tagNumberGridStore= Ext.getCmp('tagNumberGridId').getStore();
	var tagNumberGridModel= tagNumberGridStore.getRange();
	for(var i=0;i<tagNumberGridModel.length;i++){
		tagNumberGridModel[i].data.goodShorts=flag;
	}
	tagNumberGridStore.loadData(tagNumberGridModel,false);
	//更新变更Grid数据
	//1.获取到达联Form中签收情况
	var arriveSheetFormSituation= Ext.getCmp('arriveSheetFormId').getForm().findField('situation').getValue();
	//全选情况
	if(flag){
		var beforeArrInfo = new Array();
		var afterArrInfo =  new Array();
		sign.applicationSignRfc.serialNoSituation_KeyValue.each(function(key, value, length){
			if(value != arriveSheetFormSituation){
				beforeArrInfo.push(key+"."+value);
				afterArrInfo.push(key+"."+arriveSheetFormSituation);
			}
		});
		sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo = beforeArrInfo.join(",");
		sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo = afterArrInfo.join(",");
	}else{//全不选
		var beforeArrInfo = new Array();
		var afterArrInfo =  new Array();
		sign.applicationSignRfc.serialNoSituation_KeyValue.each(function(key, value, length){
			if(value != "NORMAL_SIGN"){
				beforeArrInfo.push(key+"."+value);
				afterArrInfo.push(key+"."+"NORMAL_SIGN");
			}
		});
		sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo = beforeArrInfo.join(",");
		sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo = afterArrInfo.join(",");
	}
	Ext.getCmp('arriveSheetFormId').getForm().findField('situation').fireEvent('blur');
}*/

// 定义付款model
Ext.define('Foss.sign.applicationSignRfc.RepaymentModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'repaymentNo', // 付款编号
						type : 'string'
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'consigneeCode', // 客户编码
						type : 'string'
					}, {
						name : 'consigneeName', // 客户名称
						type : 'string'
					}, {
						name : 'beforeConsigneeName', // 客户名称
						type : 'string'
					}, {
						name : 'paymentType', // 付款方式
						type : 'string'
					}, {
						name : 'claimNo', // 款项认领编号
						type : 'string'
					}, {
						name : 'actualFreight', // 实付运费
						type : 'Number'
					}, {
						name : 'codAmount', // 代收货款
						type : 'Number'
					}, {
						name : 'totMoney', // 货款总额
						type : 'Number'
					}, {
						name : 'paymentTime', // 付款时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'storageFee', // 仓储费
						type : 'Number'
					}, {
						name : 'operator', // 操作人
						type : 'string'
					}, {
						name : 'operatorCode', // 操作人编码
						type : 'string'
					}, {
						name : 'operateOrgName', // 操作部门
						type : 'string'
					}, {
						name : 'operateOrgCode', // 操作部门编码
						type : 'string'
					}, {
						name : 'currencyCode', // 币种 RMB
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}, {
						name : 'stlbillGeneratedStatus', // 是否已有财务单据
						type : 'string'
					}]

		});
// 定义到达联model
Ext.define('Foss.sign.applicationSignRfc.ArrivesheetModel', {
			extend : 'Ext.data.Model',
			idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : ' extid ',
						type : 'string'
					},// 额外的用于生成的EXT使用的列
					{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'arrivesheetNo', // 到达联编号
						type : 'string'
					}, {
						name : 'deliverymanName', // 提货人名称
						convert : function(value) {//
							if (value == 'N/A') {
								return "";
							} else {
								return value;
							}
						}
					}, {
						name : 'identifyType', // 证件类型
						type : 'string'
					}, {
						name : 'identifyCode', // 证件号码
						type : 'string'
					}, {
						name : 'situation', // 签收情况
						type : 'string'
					}, {
						name : 'arriveSheetGoodsQty', // 到达联件数
						type : 'Number'
					}, {
						name : 'signGoodsQty', // 签收件数
						type : 'Number'
					}, {
						name : 'signNote', // 签收备注
						type : 'string'
					}, {
						name : 'signTime', // 签收时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'isPrinted', // 是否打印
						type : 'string'
					}, {
						name : 'printtimes', // 打印次数
						type : 'Number'
					}, {
						name : 'createUserName', // 创建人
						type : 'string'
					}, {
						name : 'createUserCode', // 创建人编码
						type : 'string'
					}, {
						name : 'createOrgName', // 创建部门
						type : 'string'
					}, {
						name : 'createOrgCode', // 创建部门编码
						type : 'string'
					}, {
						name : 'createTime', // 创建时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'status', // 到达联状态
						type : 'string'
					}, {
						name : 'isPdaSign', // 是否PDA签到
						type : 'string'
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'isSentRequired', // 是否必送货
						type : 'string'
					}, {
						name : 'isNeedInvoice', // 是否需要发票
						type : 'string'
					}, {
						name : 'preNoticeContent', // 提前通知内容
						type : 'string'
					}, {
						name : 'deliverRequire', // 送货要求
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}, {
						name : 'tagNumber', // 标签编号
						type : 'string'
					}]

		});

// 定义标签编号model
Ext.define('Foss.sign.applicationSignRfc.TagNumberModel', {
			extend : 'Ext.data.Model',
			//idgen : 'uuid',// EXT在前台为每个模型额外以UUID方式生成主键
			//idProperty : 'extid',// 以上生成的主键用在名叫“extid”的列
			fields : [{
						name : 'tagNumber', // 运单号
						type : 'string'
					}, {
						name : 'goodShorts', //是否内物短少
						type : 'boolean'
					}]

		});
		
// 定义签收结果model
Ext.define('Foss.sign.applicationSignRfc.WaybillSignResultModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id', // ID
						type : 'string'
					}, {
						name : 'waybillNo', // 运单号
						type : 'string'
					}, {
						name : 'signSituation', // 签收情况
						type : 'string'
					}, {
						name : 'deliverymanName', // 提货人名称
						type : 'string'
					}, {
						name : 'identifyType', // 证件类型
						type : 'string'
					}, {
						name : 'identifyCode', // 证件号码
						type : 'string'
					}, {
						name : 'settleStatus', // 结清状态
						type : 'string'
					}, {
						name : 'signGoodsQty', // 签收件数
						type : 'Number'
					}, {
						name : 'signNote', // 签收备注
						type : 'string'
					}, {
						name : 'signTime', // 签收时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'createTime', // 生效时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'modifyTime', // 时效时间
						type : 'date',
						convert : dateConvert
					}, {
						name : 'active', // 是否有效
						type : 'string'
					}, {
						name : 'isPdaSign', // 是否PDA签到
						type : 'string'
					}, {
						name : 'signStatus', // 签收状态
						type : 'string'
					}, {
						name : 'agentCode',
						type : 'string'
					}, {
						name : 'agentName',
						type : 'string'
					}, {
						name : 'isRfcing', // 是否审批中
						type : 'string'
					}, {
						name : 'signCount', // 运单件数
						type : 'Number'
					}]

		});
// 定义变更明细MODEL
Ext.define('Foss.sign.applicationSignRfc.ChangeSignDetailModel', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'rfcType',
						type : 'string'
					}, {
						name : 'rfcItems',
						type : 'string'
					},// 变更项
					{
						name : 'rfcItemsCode',
						type : 'string'
					},// 变更CODE
					{
						name : 'beforeRfcinfo',
						type : 'string'
					},// 变更前
					{
						name : 'afterRfcinfo',
						type : 'string'
					},// 变更后
					{
						name : 'beforeRfcinfoValue',
						type : 'string'
					},// 变更前
					{
						name : 'afterRfcinfoValue',
						type : 'string'
					}// 变更后
			]
		});
// 定义付款store
Ext.define('Foss.sign.applicationSignRfc.RepaymentStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.applicationSignRfc.RepaymentModel'
		});
// 定义到达联store
Ext.define('Foss.sign.applicationSignRfc.ArriveSheetStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.applicationSignRfc.ArrivesheetModel'
		});
// 定义标签编号store
Ext.define('Foss.sign.applicationSignRfc.TagNumberStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.applicationSignRfc.TagNumberModel',
			listeners:{
				'datachanged': function(store,eOpts){
						
				},
				'update' : function(store,record,operation,modifiedFieldNames,eOpts){
					sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent = false;
					
					//初始化变更前后信息全局变量
					sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
					sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
						
					/**
					*当TagNumberGrid中的数据有变化时,记录变化前后数据信息
					*/
					//1.获取Form中签收情况的值
					var arriveSheetForm= Ext.getCmp('arriveSheetFormId').getForm();
					var situationStr= arriveSheetForm.findField('situation').getValue();
					//2.如果签收情况是"正常签收",变更信息不记录流水号
					if(situationStr=='NORMAL_SIGN'||situationStr==''){
						sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
						sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
					}else{
						//3.1 获取TagNumberGrid的store
						var tagNumberGridStore= Ext.getCmp('tagNumberGridId').getStore();
						//3.2 获取TagNumberGrid中的被修改的信息
						var tagNumberGridModifiedRecords= tagNumberGridStore.getModifiedRecords();
						//5.TagNumberGrid中第二行的checkBox中的原始值
						//sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo= sign.applicationSignRfc.goodShortsCheckBox_Value;
						//6.找到被修改的checkBox的原始值和对应的流水号
						var modifiedInfo=new Array();//记录Grid中被修改后的信息：流水号_签收情况
						var oldInfo=new Array(); //记录被修改的信息原始值：流水号_签收情况
						if(tagNumberGridModifiedRecords){
							for(var i=0;i<tagNumberGridModifiedRecords.length;i++){
								var modelTmp=tagNumberGridModifiedRecords[i];
								if(modelTmp.modified.hasOwnProperty('goodShorts')){//如果modelTmp.modified中含有goodShorts属性,正常控件值被修改了
									if(!modelTmp.data.goodShorts){//如果签收情况为"异常"时,当取消选中checkBox的时候,就表示签收情况被修改"正常签收"
										if(sign.applicationSignRfc.serialNoSituation_KeyValue.get(modelTmp.data.tagNumber) != "NORMAL_SIGN"){
											modifiedInfo.push(modelTmp.data.tagNumber+".NORMAL_SIGN");
											oldInfo.push(modelTmp.data.tagNumber+"."+sign.applicationSignRfc.serialNoSituation_KeyValue.get(modelTmp.data.tagNumber));
										}
									}else{
										if(sign.applicationSignRfc.serialNoSituation_KeyValue.get(modelTmp.data.tagNumber) != situationStr){
											modifiedInfo.push(modelTmp.data.tagNumber+"."+situationStr);
											oldInfo.push(modelTmp.data.tagNumber+"."+sign.applicationSignRfc.serialNoSituation_KeyValue.get(modelTmp.data.tagNumber));
										}
									}
								}
							}
						}	
						/*
						 *当到达联Form中的签收情况被改变的时候,要记录tagNumberGrid中第二列默认被选中的信息sign.applicationSignRfc.serialNoSituation_SelectedKeyValue
						 *同时要排除掉默认被选中中的由于update事件而被修改掉的信息
						 */
						 //1.获取到达联Form中的签收情况
						 var arriveSheetForm_situation = situationStr;
						 //2.获取到达联Grid中被选中行的签收情况
						 var arriveSheetGrid_situation = sign.applicationSignRfc.arriveSheetGrid_SituationValue;
						 //3.去除因update而导致的修改了默认的数据
						 if(arriveSheetForm_situation != arriveSheetGrid_situation){
							var tmpSelectedKeyValue = new Ext.util.HashMap();
							sign.applicationSignRfc.serialNoSituation_SelectedKeyValue.each(function(key, value, length){
								tmpSelectedKeyValue.add(key,value);
							});
							
							//遍历modifiedInfo数组
							for(var y=0;y<modifiedInfo.length;y++){
								var serialNoTmp = modifiedInfo[y].substring(0,modifiedInfo[y].indexOf(".")); //获取流水号
								if(tmpSelectedKeyValue.containsKey(serialNoTmp)){
									tmpSelectedKeyValue.removeAtKey(serialNoTmp);
								}
							}
							//4.将筛除后数据存到modifiedInfo和oldInfo数据中
							tmpSelectedKeyValue.each(function(key, value, length){
								if(value != arriveSheetForm_situation){
									oldInfo.push(key+"."+value);
									modifiedInfo.push(key+"."+arriveSheetForm_situation);
								}
							});
						 }
						
						
						//变更后信息
						sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo= modifiedInfo.join(",");
						//变更前信息
						sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo= oldInfo.join(",");
					}
					//触发到达联Form的Blur事件,更新变更信息到变更Grid中
					arriveSheetForm.findField('situation').fireEvent('blur');  
				}
			}
		});
// 定义签收结果store
Ext.define('Foss.sign.applicationSignRfc.WaybillSignResultStore', {
			extend : 'Ext.data.Store',
			model : 'Foss.sign.applicationSignRfc.WaybillSignResultModel'
		});
sign.applicationSignRfc.PKP_SIGN_SITUATION = 'PKP_SIGN_SITUATION';// 签收情况词条
(function() {
	sign.applicationSignRfc.situationStore2 = FossDataDictionary
			.getDataDictionaryStore(sign.applicationSignRfc.PKP_SIGN_SITUATION,
					null,null,['NORMAL_SIGN','UNNORMAL_ELSE','UNNORMAL_POLLUTION','UNNORMAL_DAMP','UNNORMAL_BREAK','UNNORMAL_GOODSHORT','UNNORMAL_SAMEVOTEODD']);
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','GOODS_BACK'));
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','UNNORMAL_SIGN'));
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','UNNORMAL_LOSTCARGO'));//移除异常-丢货
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','UNNORMAL_CONTRABAND'));//移除异常-违禁品
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','UNNORMAL_ABANDONGOODS'));//移除异常-弃货
//	sign.applicationSignRfc.situationStore2.removeAt(sign.applicationSignRfc.situationStore2.find('valueCode','PARTIAL_SIGN'));//部分签收
	//付款方式词条
	sign.applicationSignRfc.PaymentStore =FossDataDictionary.getDataDictionaryStore('SETTLEMENT__PAYMENT_TYPE', null,null,['CH','CD','TT','NT','CT','DT','OL']);
	//sign.applicationSignRfc.PaymentStore.removeAt(sign.applicationSignRfc.PaymentStore.find('valueCode','OL'));
	//sign.applicationSignRfc.PaymentStore.removeAt(sign.applicationSignRfc.PaymentStore.find('valueCode','FC'));
})();



/**
*同票多类异常Grid--弹出窗口--异常流水号选择对应的Grid--Store--Model
*/
 Ext.define('Foss.sign.SerialNoWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
	{name: 'serialNo',type: 'string'},// 流水号
	{name: 'waybillNo',type: 'string'},// 运单号
	{name:'isSelect',type:'boolean'}
	]
});
/**
*同票多类异常Grid--弹出窗口--异常流水号选择对应的Grid--Store
*/
Ext.define('Foss.sign.SerialNoWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SerialNoWithTicketStorageModel'
	});
/**
*同票多类异常Grid--弹出窗口--异常流水号选择对应的Grid
*/
Ext.define('Foss.sign.applicationSignRfc.SerialNoWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    columnLines: true,
    emptyText:sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.emptyText'),//查询结果为空
	height: 300,//高度
	id: 'Foss_sign_applicationSignRfc_SerialNoWithTicketMulStorageGridPanel',
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
/**
*同票多类异常Grid--弹出窗口
*/
Ext.define('Foss.sign.applicationSignRfc.applicationSignRfc.WithTicketStoraWindow', {
	extend: 'Ext.window.Window',
	id:'Foss_sign_applicationSignRfc_WithTicketStoraWindow_ID',
	closeAction: 'hide',
	layout: 'auto',	
	width:300,
	resizable : false,
	title:'流水号选择',//流水号选择
	modal: true,
	SerialNoOutStorageGridPanel : null,
	getSerialNoOutStorageGridPanel : function(){
		if(this.SerialNoOutStorageGridPanel==null){
			this.SerialNoOutStorageGridPanel = Ext.create('Foss.sign.applicationSignRfc.SerialNoWithTicketStorageGridPanel',{
				width:270,
				text : sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.signPieces')//签收件
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
				var SerialNoWithTicket =Ext.getCmp('Foss_sign_applicationSignRfc_SerialNoWithTicketMulStorageGridPanel'),
					serialNos = SerialNoWithTicket.getSelectionModel().getSelection(),
					SerialNoWithTicketStore = SerialNoWithTicket.getStore().data,
					frontGrid=Ext.getCmp('Foss_sign_applicationSignRfc_SignOutSerialNoWithTicketMulStorageGridPanel_ID'),
					frontGrid_Store=frontGrid.getStore(),
					signSeriaNoNote = Ext.getCmp('applicationSignRfc_signSeriaNoNote_id');
					var selectSerialNos="";
					var deselectSerialNos="";
				if(serialNos.length<=0){
					Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'), '请选择各类型异常流水号!', 'error', 1000);
						return false;
				}
				for(var i=0;i<SerialNoWithTicketStore.length;i++){
					if(SerialNoWithTicketStore.items[i].data.isSelect){
						selectSerialNos+=SerialNoWithTicketStore.items[i].data.serialNo;
						selectSerialNos+=',';
					}else{
						deselectSerialNos+=SerialNoWithTicketStore.items[i].data.serialNo;
						deselectSerialNos+=',';
					}
				}
				if(!Ext.isEmpty(deselectSerialNos)){
					signSeriaNoNote.setValue(deselectSerialNos.substring(0,deselectSerialNos.length-1));
				}else{
					signSeriaNoNote.setValue("");
				}
				if(!Ext.isEmpty(selectSerialNos)){
					frontGrid_Store.data.items[sign.applicationSignRfc.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',selectSerialNos.substring(0,selectSerialNos.length-1));
				}else{
					frontGrid_Store.data.items[sign.applicationSignRfc.SIGN_RECORD_SERINALNO_INDEX].set('serialNo',"");
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

/**
*同票多类异常Grid--Store--Model
*/
  Ext.define('Foss.sign.SignOutWithTicketStorageModel',{
	extend: 'Ext.data.Model',
	fields:	[
		{name: 'signState'},
		{name: 'choose'},
		{name: 'serialNo'}/*,
		{name: 'serialNoId'}*/
	]
});
/**
*同票多类异常Grid--Store
*/
Ext.define('Foss.sign.SignOutStateWithTicketStorageStore',{
	extend: 'Ext.data.Store',
	model:'Foss.sign.SignOutWithTicketStorageModel',
	listeners:{
		'update' : function(store,record,operation,modifiedFieldNames,eOpts){
			/***
			 *变更信息包括两部分：1.Grid中如果有添加新行时,要判断所添加的信息是否要添加到变更信息中
			 *                    2.无差异流水号好TextArea中,如果有值的话,要判断是否要添加到变更信息中
			 */
			 //1.获取Grid中的变更信息
			 
			//获取同票多类异常Grid
			var SignOutSerialNoWithTicketMulStorageGridPanel= Ext.getCmp('Foss_sign_applicationSignRfc_SignOutSerialNoWithTicketMulStorageGridPanel_ID');
			//获取同票多类异常Grid-Store
			var SignOutSerialNoWithTicketMulStorageGridPanel_Store=SignOutSerialNoWithTicketMulStorageGridPanel.getStore();
			//获取同票多类异常Grid-Store-Model
			var SignOutSerialNoWithTicketMulStorageGridPanel_Model=SignOutSerialNoWithTicketMulStorageGridPanel_Store.getRange();
			 sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array(); //变更前信息
			 sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array();  //变更后信息
			//如果整个Grid中有的格子没有填值,则此行信息不会被记录
			if(SignOutSerialNoWithTicketMulStorageGridPanel_Model){
				for(var i=0;i<SignOutSerialNoWithTicketMulStorageGridPanel_Model.length;i++){
					var tmp=SignOutSerialNoWithTicketMulStorageGridPanel_Model[i];
					if(tmp.data.serialNo!=''&&(tmp.data.signState!=null&&tmp.data.signState!='')){
						/**
						 *如果变更后的签收情况和原始签收情况不同,则记录变更信息
						 *1.首先判断Grid中的异常流水号列是否包含多个流水号拼接在一起
						 *2.如果有多个流水号拼接在一起,则拆解,然后遍历判断是否和原始签收情况不同
						 */
						 if(tmp.data.serialNo.indexOf(',')>=0){//有流水号拼接在一起
							var tmpSeriaNoArr= tmp.data.serialNo.split(',');
							for(var y=0;y<tmpSeriaNoArr.length;y++){
								//获取原始签收情况
								var sitatutionOld=sign.applicationSignRfc.serialNoSituation_KeyValue.get(tmpSeriaNoArr[y]);
								if(sitatutionOld!=tmp.data.signState){
									var beforeInfo=tmpSeriaNoArr[y]+"."+sitatutionOld;
									var afterInfo=tmpSeriaNoArr[y]+"."+tmp.data.signState;
									//记录Grid中的变更信息
									sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.push(beforeInfo);
									sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo.push(afterInfo);
								 }
							}
						 }else{//异常流水号列只有一个流水号
							//获取原始签收情况
							 var sitatutionOld=sign.applicationSignRfc.serialNoSituation_KeyValue.get(tmp.data.serialNo);
							 if(sitatutionOld!=tmp.data.signState){
								var beforeInfo=tmp.data.serialNo+"."+sitatutionOld;
								var afterInfo=tmp.data.serialNo+"."+tmp.data.signState;
								//记录Grid中的变更信息
								sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.push(beforeInfo);
								sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo.push(afterInfo);
							 }
						 }
					}
				}
			}
			//2. 获取无差异流水号TextArea中的变更信息(无差异流水号的签收情况都是正常签收)
			//如果Grid中只有一行,并且该行的信息还没有填写完整,则不添加变更信息
			if(SignOutSerialNoWithTicketMulStorageGridPanel_Model.length==1){
				var tmpModel=SignOutSerialNoWithTicketMulStorageGridPanel_Model[0];
				if(tmpModel.data.serialNo==''||tmp.data.signState==null||tmp.data.signState==''){
					return ;
				}
			}
			
			var signSeriaNoNoteValue= Ext.getCmp('applicationSignRfc_signSeriaNoNote_id').getValue();
			if(signSeriaNoNoteValue!=null&&signSeriaNoNoteValue!=''){
				var signSeriaNoNoteArr=signSeriaNoNoteValue.split(',');
				//遍历数组,判断每个流水号所对应的签收情况是否有变化
				for(var i=0;i<signSeriaNoNoteArr.length;i++){
					//获取原始的签收情况
					var sitatutionOld=sign.applicationSignRfc.serialNoSituation_KeyValue.get(signSeriaNoNoteArr[i]);
					//如果原始签收情况不为''或者'NORMAL_SIGN',则该流水号的签收情况被改变
					if(sitatutionOld!=''&&sitatutionOld!='NORMAL_SIGN'){
						var beforeInfo=signSeriaNoNoteArr[i]+"."+sitatutionOld;
						var afterInfo=signSeriaNoNoteArr[i]+"."+'NORMAL_SIGN';
						//记录TextArea中的变更信息
						sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.push(beforeInfo);
						sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo.push(afterInfo);
					}
				}
			}
			
			//获取Form中签收情况的值
					var arriveSheetForm= Ext.getCmp('arriveSheetFormId').getForm();
			//触发到达联Form的Blur事件,更新变更信息到变更Grid中
					arriveSheetForm.findField('situation').fireEvent('blur'); 
			
			
		}
	}
});
/**
*同票多类异常Grid
*/
var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {clicksToEdit: 1});
Ext.define('Foss.sign.applicationSignRfc.SignOutWithTicketStorageGridPanel',{
	extend:'Ext.grid.Panel',
    emptyText:sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.emptyText'),//查询结果为空
	id: 'Foss_sign_applicationSignRfc_SignOutSerialNoWithTicketMulStorageGridPanel_ID',
	frame: false,
	selModel: {selType:'cellmodel'},
	//收缩
	collapsible: true,
   // title:'流水号明细',//流水号明细
	plugins:[cellEditing],
	viewConfig: {
        forceFit : true
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
					signSeriaNoNote = Ext.getCmp('applicationSignRfc_signSeriaNoNote_id');
		
					if(!Ext.isEmpty(serialNo)){
						if(!Ext.isEmpty(signSeriaNoNote.getValue())){
							signSeriaNoNote.setValue(signSeriaNoNote.getValue().concat(',').concat(serialNo));
						}else{
							signSeriaNoNote.setValue(serialNo);
						}
					}
					store.removeAt(rowIndex);
					if(serialNo == "" || signState ==""){
						return;
					}
					/**
					 *移除的时候同时移除变更Grid中的对应的信息
					 */
					 //获取删除行的异常流水号,如果有逗号则有多个流水号,要拆解,反之只有一个流水号
					 var serialNoArr=new Array();
					 if(serialNo.indexOf(',')>=0){
						var tmpArr=serialNo.split(',');
						for(var x=0;x<tmpArr.length;x++){
							serialNoArr.push(tmpArr[x]);
						}
					 }else{
						serialNoArr.push(serialNo);
					 }
					 
					 //获取变更Grid
					 var dedicatedChangeDetailGrid= Ext.getCmp('dedicatedChangeDetailGridId');
					 //获取变更Grid-Store
					 var dedicatedChangeDetailGrid_Store=dedicatedChangeDetailGrid.getStore();
					 //获取变更Grid中的变更前信息列的数据
					 var beforeRfcinfoArr=dedicatedChangeDetailGrid_Store.collect('beforeRfcinfoValue'); //数组中的数据格式：流水号.签收情况
					 //遍历每一行的数据,拆解每一行的数据,然后判断删除行的流水号是否在变更Grid中某一行,如果是,则删除变更Grid中该行
					 var changeNormalSerialNO = new Array();
					 if(beforeRfcinfoArr!=null){
						for(var i=0;i<beforeRfcinfoArr.length;i++){
							var tmpInfo=beforeRfcinfoArr[i];
							
							var tmpInfoArr=tmpInfo.split('.'); //获取流水号
							if(serialNoArr.indexOf(tmpInfoArr[0])>=0){
								dedicatedChangeDetailGrid_Store.removeAt(i);
								dedicatedChangeDetailGrid_Store=dedicatedChangeDetailGrid.getStore();
								beforeRfcinfoArr=dedicatedChangeDetailGrid_Store.collect('beforeRfcinfoValue');
								i=i-1;
							}
						}
						//变正常签收的流水号
						changeNormalSerialNO = serialNoArr;
						//将变正常的流水号变更信息放到变更Grid中,同时更新同票多类变更信息前后的全局变量
						//1.获取变更Grid-Store
						var dedicatedChangeDetailGridStoreTmp = Ext.getCmp('dedicatedChangeDetailGridId').getStore();
						var tmplog = new Array();
						for(var k=0; k<changeNormalSerialNO.length;k++){
							if(sign.applicationSignRfc.serialNoSituation_KeyValue.get(changeNormalSerialNO[k]) != 'NORMAL_SIGN'){
								var tmpBefore = changeNormalSerialNO[k]+"."+sign.applicationSignRfc.serialNoSituation_KeyValue.get(changeNormalSerialNO[k]);
								var tmpAfter = changeNormalSerialNO[k]+"."+"NORMAL_SIGN";
								log = {
									'rfcType' : 'CHANGEDETAIL_TYPE_LABELTABLEFLG',
									'rfcItems' : '签收情况',
									'rfcItemsCode' : 'situation',
									'beforeRfcinfo' : tmpBefore,
									'afterRfcinfo' : tmpAfter,
									'beforeRfcinfoValue' : tmpBefore,
									'afterRfcinfoValue' : tmpAfter
								};
								sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.push(tmpBefore);
								sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo.push(tmpAfter);
								tmplog.push(log);
							}
						}
						if(tmplog.length>0){
							dedicatedChangeDetailGridStoreTmp.loadData(tmplog,true);
						}
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
					store : FossDataDictionary.getDataDictionaryStore(sign.applicationSignRfc.PKP_SIGN_SITUATION,
										null,null,['UNNORMAL_BREAK', 'UNNORMAL_DAMP', 'UNNORMAL_POLLUTION','UNNORMAL_GOODSHORT','UNNORMAL_ELSE']),
					listeners : {
						'change' : function(field,newValue, oldValue, eOpts){
								var arr = this.up('panel').getStore().collect('signState');
								var flag = arr.indexOf(newValue);
								if(flag>=0){
										this.setValue('');
										Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'), '签收情况已被选择', 'error', 3000);
										return false;
								}
						},
						'select' : function( combo, records,eOpts){
							combo.fireEvent('blur');
						}
					}
			},
			renderer:function(value){  
                return FossDataDictionary.rendererSubmitToDisplay(value, sign.applicationSignRfc.PKP_SIGN_SITUATION);  
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
							 signSeriaNoNote = Ext.getCmp('applicationSignRfc_signSeriaNoNote_id'),
							 signSeriaNoNoteVal = signSeriaNoNote.getValue(),
							 showSerials=new Array(),
							 chooseModel=new Array();
							var win ;
							var seriaNoGrid ;
							var serialNoOutStorage;
							if(!Ext.getCmp('Foss_sign_applicationSignRfc_WithTicketStoraWindow_ID')){
								win = Ext.create('Foss.sign.applicationSignRfc.applicationSignRfc.WithTicketStoraWindow');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}else{
							    win = Ext.getCmp('Foss_sign_applicationSignRfc_WithTicketStoraWindow_ID');
								seriaNoGrid=win.getSerialNoOutStorageGridPanel();
								serialNoOutStorage = seriaNoGrid.getStore();
								seriaNoGrid.getSelectionModel().deselectAll();
								serialNoOutStorage.removeAll();
							}
							if(!Ext.isEmpty(serialNo)) {
								var serialNoArray=serialNo.split(',');
								for(var i=0;i<serialNoArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.applicationSignRfc.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':serialNoArray[i],'isSelect':true});
									chooseModel.push(showSerials);
								}
								
							}
							if(!Ext.isEmpty(signSeriaNoNoteVal)) {
								var signSeriaNoNoteArray=signSeriaNoNoteVal.split(',');
								for(var i=0;i<signSeriaNoNoteArray.length;i++){
									showSerials.push(
									{'waybillNo':sign.applicationSignRfc.SIGN_RECORD_WAYBILLNO_INDEX,
									'serialNo':signSeriaNoNoteArray[i],'isSelect':false});
								}
							}
							serialNoOutStorage.loadData(showSerials,true);
							sign.applicationSignRfc.SIGN_RECORD_SERINALNO_INDEX=index;
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
							Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'), '签收情况没有被选择', 'error', 3000);
								return false;
						}
				}		
			},align: 'center'
		},
		{header: '异常流水号', dataIndex: 'serialNo',xtype : 'ellipsiscolumn',menuDisabled:true,align: 'center',name:'serialNo',minWidth:680,maxWidth:680}
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
						//如果没有无异常流水号了,不可再添加新的行
						var noteTextArea=Ext.getCmp('applicationSignRfc_signSeriaNoNote_id').getValue();
						if(noteTextArea==null||noteTextArea==''){
							Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'),'已不存在无异常流水号了,不可再添加新的行!','error',1000);
							return false;
						}
						
						var count = sign.applicationSignRfc.situationStore.getCount();
						var panelGridCount = this.up('panel').getStore().getCount();
						if(panelGridCount>=count){
							Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'),'签收情况已全部选择,不可再添加!','error',1000);
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
	},
	listeners:{
	}
});

/**
*同票多类Form
*/

Ext.define('Foss.sign.applicationSignRfc.MultiExceptionSignRfcForm',{
	extend: 'Ext.form.Panel',
	title:'签收件',
    defaultType : 'textfield',
	layout: 'column',
	frame:true,
	id: 'Foss.sign.applicationSignRfc.withTicketFormPanel_ID',
	defaults: {
		margin:'5 10 5 10',
		anchor: '90%',
		labelWidth:60
	},
	signOutWithTicketStorageGridPanel :　null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.applicationSignRfc.SignOutWithTicketStorageGridPanel',{
				width:980,
				viewConfig: {
					　　markDirty: false
				}
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},
	listeners : {
		beforerender:function(){
				Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID').hide();
			}
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({}, config);
		me.items = [
		me.getSignOutWithTicketStorageGridPanel(),
		{
			labelWidth: 100,
			id:'applicationSignRfc_signSeriaNoNote_id',
			xtype: 'textarea',
			name:'signSeriaNoNote',
			readOnly:true,
			height: 120,
			width:930,
			cls:'readonlygraybackground',
			maxLength:200,
			fieldLabel:'无差异流水号'
		}
	];
		me.callParent([cfg]);
	}
});


// 专线Panel
Ext.define('Foss.sign.applicationSignRfc.DedicatedPanel', {
	extend : 'Ext.panel.Panel',
	defaults : {
		margin : '5 10 5 10'
	},
	width : 1024,
	layout : 'column',

	fileUploadRepaymentGrid : null,
	getFileUploadRepaymentGrid : function() {
		if (this.fileUploadRepaymentGrid == null) {
			this.fileUploadRepaymentGrid = Ext.create(
					'Foss.applicationSignRfc.Grid.FileUploadRepaymentGrid', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadRepaymentGrid;
	},

	fileUploadArriveSheetGrid : null,
	getFileUploadArriveSheetGrid : function() {
		if (this.fileUploadArriveSheetGrid == null) {
			this.fileUploadArriveSheetGrid = Ext.create(
					'Foss.applicationSignRfc.Grid.FileUploadArriveSheetGrid', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadArriveSheetGrid;
	},
	// 付款GRID
	repaymentGrid : null,
	getRepaymentGrid : function() {
		if (this.repaymentGrid == null) {
			this.repaymentGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 1 / 2,
				emptyText : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.empty'), // 没有数据,
				height : 150,
				autoScroll : true,
				store : Ext
						.create('Foss.sign.applicationSignRfc.RepaymentStore'),
				selModel : Ext.create('Ext.selection.RadioModel', {
							mode : 'SINGLE',
							showHeaderCheckbox : false,
							listeners : {
								'beforeselect' : function(SelectionModel,
										record, rowIndex, eOpts) {
									if (record.data.isRfcing == 'Y') {
										return false;
									}
									return true;
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'repaymentNo',
					width : 190,
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.repaymentNo')
						// 结清编号
				}, {
					xtype : 'gridcolumn',
					width : 70,
					dataIndex : 'actualFreight',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.actualFreight')
						// 实付运费
				}, {
					xtype : 'gridcolumn',
					width : 70,
					dataIndex : 'codAmount',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.codAmount')
						// 代收货款
				}, {
					xtype : 'datecolumn',
					width : 135,
					format : 'Y-m-d H:i:s',
					dataIndex : 'paymentTime',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.paymentTime') // 结清时间,
				}],
				listeners : {
					'select' : function(rowModel, record, index, eOpts) {
						var repaymentForm = Ext.getCmp('repaymentFormId');
						repaymentForm.loadRecord(record);
						repaymentForm.setDisabled(false);
						if (record.get('stlbillGeneratedStatus') == 'STLBILL_NOTREQUIRE') {
							repaymentForm.getForm().findField('actualFreight')
									.setDisabled(true);
							repaymentForm.getForm().findField('codAmount')
									.setDisabled(true);
							repaymentForm.getForm().findField('paymentType')
									.setDisabled(true);
							repaymentForm.getForm().findField('claimNo')
									.setDisabled(true);
							repaymentForm.getForm().findField('consigneeName')
									.setDisabled(true);
							repaymentForm.getForm().findField('changeReason')
									.setDisabled(true);
						} else {
							repaymentForm.getForm().findField('paymentType')
									.setDisabled(false);
							repaymentForm.getForm().findField('consigneeName')
									.setDisabled(false);
							repaymentForm.getForm().findField('changeReason')
									.setDisabled(false);
							var a = 0;
							var b = 0;
							if (repaymentForm.getForm().findField('actualFreight').getValue() != '') {
								a = repaymentForm.getForm().findField('actualFreight').getValue();
							}
							if (repaymentForm.getForm().findField('codAmount').getValue() != '') {
								b = repaymentForm.getForm().findField('codAmount').getValue();
							}
							repaymentForm.getForm().findField('totMoney').setValue(a + b);
							var arriveSheetGrid = Ext
									.getCmp('arriveSheetGridId');

							if (repaymentForm.getForm()
									.findField('paymentType').getValue() == 'TT'
									|| repaymentForm.getForm()
											.findField('paymentType')
											.getValue() == 'NT'
											|| repaymentForm.getForm()
												.findField('paymentType')
												.getValue() == 'CD') {
								repaymentForm.getForm().findField('claimNo')
										.setDisabled(false);
							} else {
								repaymentForm.getForm().findField('claimNo')
										.setDisabled(true);
							}
						}
					}
				}
			});
		}
		return this.repaymentGrid;
	},
	// 到达联GRID
	arriveSheetGrid : null,
	getArriveSheetGrid : function() {
		if (this.arriveSheetGrid == null) {
			this.arriveSheetGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 1 / 2,
				emptyText : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.empty'), // 没有数据,
				id : 'arriveSheetGridId',
				height : 150,
				autoScroll : true,
				store : Ext
						.create('Foss.sign.applicationSignRfc.ArriveSheetStore'),
				multiSelect : false,
				selModel : Ext.create('Ext.selection.RadioModel', {
							mode : 'SINGLE',
							showHeaderCheckbox : false,
							listeners : {
								'beforeselect' : function(SelectionModel,
										record, rowIndex, eOpts) {
									if (record.data.isRfcing == 'Y') {
										return false;
									}
									return true;
								}
							}
						}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'arrivesheetNo',
					width : 92,
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.arrivesheetNo')
						// 到达联编号
				}, {
					xtype : 'gridcolumn',
					width : 62,
					dataIndex : 'deliverymanName',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.deliverymanName')
						// 提货人
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'signNote',
					width : 157,
					xtype : 'ellipsiscolumn',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.signNote')
						// 签收备注
				}, {
					xtype : 'datecolumn',
					dataIndex : 'signTime',
					width : 131,
					format : 'Y-m-d H:i:s',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.signTime')// 签收时间
				}],
				listeners : {
					'select' : function(rowModel, record, index, eOpts) {
						var arriveSheetForm = Ext.getCmp('arriveSheetFormId');
						arriveSheetForm.loadRecord(record);
						arriveSheetForm.getForm().findField('signTime').setValue(Ext.Date.format(record.data.signTime,'Y-m-d H:i:s'));
						arriveSheetForm.setDisabled(false);
						// 签收情况选择为部分签收时，签收情况不可编辑
						if (record.get('situation') == 'PARTIAL_SIGN') {
							arriveSheetForm.getForm().findField('situation').setVisible(false);
							arriveSheetForm.getForm().findField('situationPartial').setVisible(true);
							arriveSheetForm.getForm().findField('situationPartial').setValue('部分签收');
							arriveSheetForm.getForm().findField('situationPartial').setDisabled(true);
						} else {
							arriveSheetForm.getForm().findField('situation').setVisible(true);
							arriveSheetForm.getForm().findField('situationPartial').setVisible(false);
						}
		
						sign.applicationSignRfc.SIGN_RECORD_WAYBILLNO=record.get('waybillNo');//记录Grid被选行中的运单号
						
						/**
						 *初始化全局变量数据
						 */
						sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
						sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
						sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array(); //同票多类异常Grid变更前信息
						sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array();  //同票多类异常Grid变更后信息
						sign.applicationSignRfc.SIGN_SERIAL_NO_ARR=new Array(); //记录选中行中的流水号
						sign.applicationSignRfc.goodShortsCheckBox_KeyValue =new Ext.util.HashMap(); //记录goodShortsCheckBox的原始值 流水号和checkBox值对应
						sign.applicationSignRfc.serialNoSituation_KeyValue =new Ext.util.HashMap(); //记录TagNumberGrid中被选中行的流水号和签收情况的对于关系
						//备份异常签收Grid中的数据,正常签收Grid中的数据,同票多类异常Grid中的数据,无差异流水号TextArea中的流水号
						sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL=new Array();
						sign.applicationSignRfc.TagNumberGrid_Data_NORMAL=new Array();
						sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD=new Array();
						sign.applicationSignRfc.MultiExceptionForm_TextAreaData_SAMEVOTEODD=new Array();
						sign.applicationSignRfc.serialNoSituation_SelectedKeyValue=new Ext.util.HashMap();
						/**
						 *初始化TagNumberGrid,同票多类异常Grid,无差异流水号TextArea
						 */
						Ext.getCmp('tagNumberGridId').getStore().removeAll();
						Ext.getCmp('Foss_sign_applicationSignRfc_SignOutSerialNoWithTicketMulStorageGridPanel_ID').getStore().removeAll();
						Ext.getCmp('applicationSignRfc_signSeriaNoNote_id').setValue('');
						
						//获取被选中行的签收情况
						sign.applicationSignRfc.arriveSheetGrid_SituationValue=record.data.situation;
						
						
						//根据到达联Grid中被选中行的流水号,到数据库中查询出与流水号对应的签收情况
						var _tagNumber=record.data.tagNumber;
						var _tagNumberArr=_tagNumber.split(",");
						sign.applicationSignRfc.SIGN_SERIAL_NO_ARR= _tagNumberArr; //流水号
						var serialNos = new Array();
						for(var i=0;i<_tagNumberArr.length;i++){
							serialNos.push({
								'serialNo':_tagNumberArr[i],//流水号
								'arrivesheetNo':record.get("arrivesheetNo")//到达联编号
							});
						}
						var situationList=new Array(); //签收情况
						Ext.Ajax.request({
									url:sign.realPath('signSituationByArrivesheetNoSerialNo.action'),
									method: 'POST',
									async: false,
									timeout: 300000,
									jsonData: {
										'signDetailVo':{
											'signDetailList':serialNos//流水号信息集合
										}
									},
									success: function(response){
										var json = Ext.decode(response.responseText);
										situationList= json.signDetailVo.situationList;
									},exception: function(response){
										var json = Ext.decode(response.responseText);
										Ext.ux.Toast.msg(sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.tip'), json.message, 'error', 4000);
									}
								});
								
						/*
						 *获取异常签收时TagNumberGrid中的数据 和同票多类异常Grid中的数据
						 *同票多类异常From中有Grid和TextArea组成，TextArea中存放的是正常签收的流水号,Grid中存放的是异常签收
						 */
						var serialNo_Break = new Array();
						var serialNo_Damp = new Array();
						var serialNo_Pollution = new Array();
						var serialNo_GoodShort = new Array();
						var serialNo_Else = new Array();
						var serialNo_Normal = new Array();
						var goodShortsCheckBox=new Array();
						for(var i=0;i<situationList.length;i++){
							if(situationList[i]=='NORMAL_SIGN'||situationList[i]==''){
								goodShortsCheckBox.push(false);
								sign.applicationSignRfc.serialNoSituation_KeyValue.add(_tagNumberArr[i],'NORMAL_SIGN');
								serialNo_Normal.push(_tagNumberArr[i]);
							}else{
								goodShortsCheckBox.push(true);
								sign.applicationSignRfc.serialNoSituation_KeyValue.add(_tagNumberArr[i],situationList[i]);
								sign.applicationSignRfc.serialNoSituation_SelectedKeyValue.add(_tagNumberArr[i],situationList[i]);
								if(situationList[i] == 'UNNORMAL_BREAK'){ //破损
									serialNo_Break.push(_tagNumberArr[i]);
								}else if(situationList[i] == 'UNNORMAL_DAMP'){ //潮湿
									serialNo_Damp.push(_tagNumberArr[i]);
								}else if(situationList[i] == 'UNNORMAL_POLLUTION'){ //污染
									serialNo_Pollution.push(_tagNumberArr[i]);
								}else if(situationList[i] == 'UNNORMAL_GOODSHORT'){ //内物短少
									serialNo_GoodShort.push(_tagNumberArr[i]);
								}else if(situationList[i] == 'UNNORMAL_ELSE'){ //其他
									serialNo_Else.push(_tagNumberArr[i]);
								}
							}
						}
						//同票多类异常--无异常流水号
						if(serialNo_Normal.length>0){
							sign.applicationSignRfc.MultiExceptionForm_TextAreaData_SAMEVOTEODD.push(serialNo_Normal.join(','));  
						}
						//同票多类异常异常Grid
						if(serialNo_Break.length>0){
							sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD.push({  
									signState:'UNNORMAL_BREAK',
									choose:'选择',
									serialNo:serialNo_Break.join(',')
								});
						}
						if(serialNo_Damp.length>0){
							sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD.push({  
									signState:'UNNORMAL_DAMP',
									choose:'选择',
									serialNo:serialNo_Damp.join(',')
								});
						}
						if(serialNo_Pollution.length>0){
							sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD.push({  
									signState:'UNNORMAL_POLLUTION',
									choose:'选择',
									serialNo:serialNo_Pollution.join(',')
								});
						}
						if(serialNo_GoodShort.length>0){
							sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD.push({  
									signState:'UNNORMAL_GOODSHORT',
									choose:'选择',
									serialNo:serialNo_GoodShort.join(',')
								});
						}
						if(serialNo_Else.length>0){
							sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD.push({  
									signState:'UNNORMAL_ELSE',
									choose:'选择',
									serialNo:serialNo_Else.join(',')
								});
						}
								
						for(var i=0;i<_tagNumberArr.length;i++){
							sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL.push({tagNumber:_tagNumberArr[i],goodShorts:goodShortsCheckBox[i]});
						}
						//获取正常签收时TagNumberGrid中的数据
						for(var i=0;i<_tagNumberArr.length;i++){
							sign.applicationSignRfc.TagNumberGrid_Data_NORMAL.push({tagNumber:_tagNumberArr[i],goodShorts:false});
						}
						
						sign.applicationSignRfc.arriveSheetForm_SignNoteCheckValue=record.get('situation');
						
						/**
						*如果是异常签收(除同票多类异常外的异常签收)
						*/
						if(record.get('situation') != ''&&record.get('situation') != 'NORMAL_SIGN'&&record.get('situation') != 'UNNORMAL_SAMEVOTEODD'){
							var _tagNumberGrid= Ext.getCmp('tagNumberGridId');
							var _tagNumberGridStore=_tagNumberGrid.getStore();
							_tagNumberGridStore.loadData(sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL,true);
							//修改Grid的第二列名称
							/*if(goodShortsCheckBox.indexOf(true)>=0){
								var headerStr=record.data.signNote+'<input type="checkbox" checked="true" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
							}else{
								var headerStr=record.data.signNote+'<input type="checkbox" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
							}*/
//							var headerStr=record.data.signNote;
							var headerStr=arriveSheetForm.getForm().findField('situation').rawValue;
							_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
							_tagNumberGrid.columns[1].show();
							
							//对于整车签收,没有流水号的情况,TagNumberGrid不显示
							if(sign.applicationSignRfc.SIGN_SERIAL_NO_ARR.length>=1 && sign.applicationSignRfc.SIGN_SERIAL_NO_ARR!=""){
								_tagNumberGrid.show();
							}

							//隐藏同票多类异常Form
							var _multiExceptionSignPiecesForm= Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
							_multiExceptionSignPiecesForm.hide(); 
							
						}else if(record.get('situation') == '' || record.get('situation') == 'NORMAL_SIGN'){//如果是正常签收
							var _tagNumberGrid= Ext.getCmp('tagNumberGridId');
							var _tagNumberGridStore=_tagNumberGrid.getStore();
							_tagNumberGridStore.loadData(sign.applicationSignRfc.TagNumberGrid_Data_NORMAL,true);
	
//							var headerStr=record.data.signNote+'<input type="checkbox" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
//							var headerStr=record.data.signNote;
							var headerStr=arriveSheetForm.getForm().findField('situation').rawValue;
							_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
							_tagNumberGrid.columns[1].hide();
							
							//对于整车签收,没有流水号的情况,TagNumberGrid不显示
							if(sign.applicationSignRfc.SIGN_SERIAL_NO_ARR.length>=1 && sign.applicationSignRfc.SIGN_SERIAL_NO_ARR!=""){
								_tagNumberGrid.show();
							}
							
							//隐藏同票多类异常From
							var _multiExceptionSignPiecesForm= Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
							_multiExceptionSignPiecesForm.hide(); 							
						}else{//如果是同类多票异常
							//赋值签收流水号数据
							var signSeriaNoNote = Ext.getCmp('applicationSignRfc_signSeriaNoNote_id');
							signSeriaNoNote.setValue(sign.applicationSignRfc.MultiExceptionForm_TextAreaData_SAMEVOTEODD.join(','));
							
							var _multiExceptionSignPiecesForm = Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
							var _multiExceptionGrid = _multiExceptionSignPiecesForm.getSignOutWithTicketStorageGridPanel();
							var _multiExceptionGridStore = _multiExceptionGrid.getStore();
							_multiExceptionGridStore.loadData(sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD,true)
							//显示同票多类异常Form
							_multiExceptionSignPiecesForm.show();
							
							//隐藏TagNumberGrid
							var _tagNumberGrid= Ext.getCmp('tagNumberGridId');
							_tagNumberGrid.hide();
						}	
					}
				}
			});
		}
		return this.arriveSheetGrid;
	},
	//标签编号Grid
	tagNumberGrid : null,
	getTagNumberGrid : function() {
		if (this.tagNumberGrid == null) {
			this.tagNumberGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 4 / 10,
				emptyText : sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.empty'), // 没有数据,
				id : 'tagNumberGridId',
				hidden:true,
				height : 150,
				frame: false,
				margin:'5 5 5 35',
				autoScroll : true,
				store : Ext.create('Foss.sign.applicationSignRfc.TagNumberStore'),
				columns : [
					{header: '流水号', dataIndex: 'tagNumber',draggable:false,menuDisabled:true,align: 'center',minWidth:200,maxWidth:200,sortable:false},
					{header: '是否内物短少', dataIndex: 'goodShorts',draggable:false,xtype: 'checkcolumn',menuDisabled:true,align: 'center',minWidth:150,maxWidth:150,sortable:false,
						listeners:{
							'checkchange': function(model,record,index){
								var _tagNumberGrid=Ext.getCmp('tagNumberGridId');
								var _tagNumberGridStore=Ext.getCmp('tagNumberGridId').getStore();
								var _goodShortsCheckBoxValue=_tagNumberGridStore.collect('goodShorts');
								if(_goodShortsCheckBoxValue){
									if(_goodShortsCheckBoxValue.indexOf(true)>=0){
										var headerStr=_tagNumberGrid.columns[1].text;
										//var exceptionStr=headerStr.substr(0,headerStr.indexOf('<input'));
//										headerStr=exceptionStr+'<input type="checkbox" name="全选" checked="true" onchange="javascript:checkboxClick(this.checked)"/>全选';
										//headerStr=exceptionStr;
										_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
									}else{
										var headerStr=_tagNumberGrid.columns[1].text;
										//var exceptionStr=headerStr.substr(0,headerStr.indexOf('<input'));
//										headerStr=exceptionStr+'<input type="checkbox" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
										//headerStr=exceptionStr;
										_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
									}
								}
								
							}
						}
					}
				],
				listeners : {
					'select' : function(rowModel, record, index, eOpts) {
						
					}
				}
			});
		}
		return this.tagNumberGrid;
	},/*
	//同票多类异常
	signOutWithTicketStorageGridPanel :　null,
	getSignOutWithTicketStorageGridPanel: function(){
		if(this.signOutWithTicketStorageGridPanel == null){
			this.signOutWithTicketStorageGridPanel = Ext.create('Foss.sign.applicationSignRfc.SignOutWithTicketStorageGridPanel',{
				width:528,
				//hidden:true,
				columnWidth : 1
			});
		}
		return this.signOutWithTicketStorageGridPanel;
	},*/
	multiExceptionSignRfcForm: null,
	getMultiExceptionSignRfcForm: function(){
		if(this.multiExceptionSignRfcForm == null){
			this.multiExceptionSignRfcForm = Ext.create('Foss.sign.applicationSignRfc.MultiExceptionSignRfcForm',{
				width:528,
				columnWidth : 1
			});
		}
		return this.multiExceptionSignRfcForm;
	},
	// 变更项GRID
	dedicatedChangeDetailGrid : null,
	getDedicatedChangeDetailGrid : function() {
		if (this.dedicatedChangeDetailGrid == null) {
			this.dedicatedChangeDetailGrid = Ext.create('Ext.grid.Panel', {
				columnWidth : 1,
				autoScroll : true,
				height : 120,
				id : 'dedicatedChangeDetailGridId',
				store : Ext.create('Ext.data.Store', {
					model : 'Foss.sign.applicationSignRfc.ChangeSignDetailModel',
					proxy : {
						type : 'memory',
						reader : {
							type : 'json'
						}
					}
				}),
				columns : [{
					xtype : 'gridcolumn',
					dataIndex : 'rfcType',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.rfcType'), // 变更类型,
					renderer : function(value) {
						return FossDataDictionary.rendererSubmitToDisplay(
								value, 'PKP_SIGN_RFC_DETAIL_TYPE');
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'rfcItems',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.rfcItems')
						// 变更项
				}, {
					xtype : 'gridcolumn',
					hidden : true,
					dataIndex : 'rfcItemsCode',
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.rfcItemsCode')
						// 变更项CODE
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'beforeRfcinfo',
					hidden : true,
					text : '', // 变更前信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'afterRfcinfo',
					hidden : true,
					text : '',
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'beforeRfcinfoValue',
					flex : 1,
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.beforeRfcinfo'), // 变更前信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}, {
					xtype : 'gridcolumn',
					dataIndex : 'afterRfcinfoValue',
					flex : 1,
					text : sign.applicationSignRfc
							.i18n('pkp.sign.applicationSignRfc.afterRfcinfo'), // 变更后信息,
					renderer : function(value, metadata, record, rowIndex,
							colIndex, store) {
						if (record.get("rfcItemsCode") == 'paymentType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'SETTLEMENT__PAYMENT_TYPE');
						} else if (record.get("rfcItemsCode") == 'identifyType') {
							return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_CREDENTIAL_TYPE');
						} else if (record.get("rfcItemsCode") == 'situation') {
							if(value.indexOf('.')>=0){
								var tmpValueArr=value.split('.');
								return tmpValueArr[0]+"  "+FossDataDictionary.rendererSubmitToDisplay(
									tmpValueArr[1], 'PKP_SIGN_SITUATION');
							}else{
								return FossDataDictionary.rendererSubmitToDisplay(
									value, 'PKP_SIGN_SITUATION');
							}
						} else {
							return value;
						}
					}
				}]
			});
		}
		return this.dedicatedChangeDetailGrid;
	},

	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				xtype : 'form',
				frame : true,
				width : 1024,
				collapsible : true,
				animCollapse : true,
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.searchWaybill'), // 查询运单,
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							fieldLabel : sign.applicationSignRfc.i18n('pkp.sign.applicationSignRfc.waybillNo'), // 运单号,
							xtype : 'textfield',
							vtype: 'waybill',
							name : 'searchWaybillNo',
							allowBlank : false,
							columnWidth : .30
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'button',
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.search'), // 查询,
							disabled:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexqueryzbutton'),
							hidden:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexqueryzbutton'),
							cls : 'yellow_button',
							columnWidth : .08,
							handler : function() {
								var myform = this.up('form').getForm();
								var serachParms = myform.getValues();
								
								var arriveSheetForm = Ext
										.getCmp('arriveSheetFormId');
								var repaymentForm = Ext
										.getCmp('repaymentFormId');
								arriveSheetForm.getForm().reset();
								repaymentForm.getForm().reset();
								arriveSheetForm.setDisabled(true);
								repaymentForm.setDisabled(true);
								me.getRepaymentGrid().getStore().removeAll();
								me.getArriveSheetGrid().getStore().removeAll();
								me.getDedicatedChangeDetailGrid().getStore()
										.removeAll();
								if (serachParms.searchWaybillNo == '') {
									Ext.ux.Toast.msg('提示', '请输入运单号', 'error',
											1000);
									return;
								}
								var tagNumberGrid= Ext.getCmp('tagNumberGridId');
							
								var multiExceptionSignPiecesForm= Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
								if(tagNumberGrid){
									tagNumberGrid.hide();
								}
								var tagNumberGridStore= tagNumberGrid.getStore();
								if(tagNumberGridStore){
									tagNumberGridStore.removeAll();
								}
								
								if(multiExceptionSignPiecesForm){
									multiExceptionSignPiecesForm.hide(); 
								}
								var signOutSerialNoWithTicketMulStorageGridPanelStore= multiExceptionSignPiecesForm.getSignOutWithTicketStorageGridPanel().getStore();
								if(signOutSerialNoWithTicketMulStorageGridPanelStore){
									signOutSerialNoWithTicketMulStorageGridPanelStore.removeAll();
								}
								sign.applicationSignRfc.SIGN_SERIAL_NO_ARR =null;  //记录到达联Grid被选中行的全部流水号
								sign.applicationSignRfc.SIGN_SERIAL_NO_ARR_ODD =null;  //记录到达联Grid被选中行的剩余的流水号
								sign.applicationSignRfc.SIGN_RECORD_SERINALNO_INDEX=null;
								sign.applicationSignRfc.SIGN_RECORD_WAYBILLNO=null;//记录到达联Grid被选中行的运单号
								
								if (myform.isValid()) {
									Ext.Ajax.request({
										url : sign
												.realPath('querySignResultDedicated.action'),
										params : {
											'vo.signResultDto.signRfcEntity.waybillNo' : serachParms.searchWaybillNo
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											var repaymentArrivesheetDto = result.vo.signResultDto.repaymentArrivesheetDto;
											var repaymentList = repaymentArrivesheetDto.repaymentEntityList;
											var arriveSheetList = repaymentArrivesheetDto.arriveSheetEntityList;
											if (repaymentList != null && repaymentList.length > 0 ) {
												Ext.getCmp('repaymentFormId')
														.bindEvent();
												if (repaymentArrivesheetDto.isAuditingArrivesheetFlg == 'N') {
													repaymentForm
															.getForm()
															.findField('codAmount')
															.setDisabled(true);
													repaymentForm
															.getForm()
															.findField('actualFreight')
															.setDisabled(true);
												} else {
													repaymentForm
															.getForm()
															.findField('codAmount')
															.setDisabled(false);
													repaymentForm
															.getForm()
															.findField('actualFreight')
															.setDisabled(false);
												}
												me
														.getRepaymentGrid()
														.getStore()
														.loadData(repaymentList);
											}
											if (arriveSheetList != null && arriveSheetList.length > 0) {
												Ext.getCmp('arriveSheetFormId')
														.bindEvent();
												me
														.getArriveSheetGrid()
														.getStore()
														.loadData(arriveSheetList);
											}
										},
										exception : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.ux.Toast.msg('提示',
													result.message, 'error',
													1000);
										}
									});
								}
							}
						}]
			}, {
				// 收缩
				collapsible : true,
				// 是否有框
				frame : true,
				width : 1024,
				// 动画收缩
				animCollapse : true,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.settlePaymentInfo'), // 结清货款信息,
				items : [me.getRepaymentGrid(), {
					xtype : 'form',
					id : 'repaymentFormId',
					disabled : true,
					defaults : {
						margin : '5 10 5 10',
						labelWidth : 80
					},
					// 判断是否绑定
					eventFlag : false,
					bindEvent : function() {
						var repaymentMe = this, form = repaymentMe.getForm();
						var grid = me.getDedicatedChangeDetailGrid();
						var store = grid.getStore();
						if (repaymentMe.eventFlag) {
							return;
						}
						repaymentMe.getForm().getFields().each(
								function(item, index, length) {
									if (!(item.name == "changeReason")) {
										item.on('blur', function(component,
												eventObject, eOpts) {
											// 清除Grid数据
											var ary = new Array();
											store.each(function(record) {
												if (record.get('rfcType') == 'CHANGEDETAIL_TYPE_REPAYMENT') {
													ary.push(record);
												}
											});
											store.remove(ary);
											var record = form.getRecord();
											form.updateRecord(record);
											var changes = record.getChanges(), changeLogs = new Array();
											Ext.Object.each(changes, function(
													key, value, changes) {
												var field = form.findField(key);
												if (field.name != "totMoney") {
													if (field.name == "consigneeName") {
														// 提货客户
														var log = {
														'rfcType' : 'CHANGEDETAIL_TYPE_REPAYMENT',
														'rfcItems' : field.fieldLabel,
														'rfcItemsCode' : 'consigneeCode',
														'beforeRfcinfo' : record.data.consigneeCode,
														'afterRfcinfo' : value,
														'beforeRfcinfoValue' : eval('record.modified.' + key),
														'afterRfcinfoValue' : field.rawValue
														}
														changeLogs.push(log);
													} else {
														var log = {
														'rfcType' : 'CHANGEDETAIL_TYPE_REPAYMENT',
														'rfcItems' : field.fieldLabel,
														'rfcItemsCode' : field.name,
														'beforeRfcinfo' : eval('record.modified.' + key),
														'afterRfcinfo' : value,
														'beforeRfcinfoValue' : eval('record.modified.' + key),
														'afterRfcinfoValue' : value
														}
														changeLogs.push(log);
													}
													
												}
											});
											store.add(changeLogs);
										});
									}
								});
						repaymentMe.eventFlag = true;
					},
					layout : 'column',
					columnWidth : 1 / 2,
					items : [{
								xtype : 'hiddenfield',
								name : 'waybillNo' // 运单号
							}, {
								xtype : 'hiddenfield',
								name : 'id' // 付款ID
							}, {
								xtype : 'commoncustomerselector',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.consigneeCode'), // 提货客户,
								labelWidth : 60,
								all:'true',
								singlePeopleFlag : 'Y',
								isPaging:true, //分页
								columnWidth : 1 / 2,
								name : 'consigneeName',
								readOnly:true
							}, {
								xtype : 'numberfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.realCodAmount'), // 实收代收货款,
								labelWidth : 90,
								columnWidth : 1 / 2,
								name : 'codAmount',
								minValue : 0,
								hideTrigger : true,
								keyNavEnabled : false,
								mouseWheelEnabled : false,
								readOnly:true,
								listeners : {
									'change' : function(field, newValue,
											oldValue, eOpts) {
										var form = this.up('form').getForm();
										var totMoney = parseInt(newValue)
												+ parseInt(form
														.findField('actualFreight')
														.getValue());
										form.findField('totMoney')
												.setValue(totMoney);
									}
								}
							}, {
								xtype : 'numberfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.realActualFreight'), // 实收到付运费,
								labelWidth : 90,
								columnWidth : 1 / 2,
								name : 'actualFreight',
								minValue : 0,
								hideTrigger : true,
								keyNavEnabled : false,
								mouseWheelEnabled : false,
								readOnly:true,
								listeners : {
									'change' : function(field, newValue,
											oldValue, eOpts) {
										var form = this.up('form').getForm();
										var totMoney = parseInt(newValue)
												+ parseInt(form
														.findField('codAmount')
														.getValue());
										form.findField('totMoney')
												.setValue(totMoney);
									}
								}
							}, {
								xtype : 'combobox',
								name : 'paymentType',
								labelWidth : 90,
								columnWidth : 1 / 2,
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.paymentType'), // 到达付款方式,
								queryModel : 'local',
								displayField : 'valueName',
								valueField : 'valueCode',
								editable : false,
								store : sign.applicationSignRfc.PaymentStore,
								readOnly:true,
								listeners : {
									'change' : function(field, newValue,
											oldValue, eOpts) {
										var form = this.up('form').getForm();
										// TT==电汇,NT==支票
										// 到达付款方式由[电汇/支票]更改为非[电汇/支票]时，款项认领编号为空且不可编辑；
										// 到达付款方式由非[电汇/支票]更改为
										// [电汇/支票]时，款项认领编号为空且可编辑
										if ((oldValue == 'TT'|| oldValue == 'OL' || oldValue == 'NT' || oldValue == 'CD')
												&& (newValue != 'TT'|| newValue != 'OL' || newValue != 'NT' || newValue != 'CD')) {
											form.findField('claimNo')
													.setValue('');
											form.findField('claimNo')
													.setDisabled(true);
										}
										if ((oldValue != 'TT' || oldValue != 'NT'|| oldValue != 'OL' || oldValue != 'CD')
												&& (newValue == 'TT'|| newValue == 'OL' || newValue == 'NT' || newValue == 'CD')) {
											form.findField('claimNo')
													.setValue('');
											form.findField('claimNo')
													.setDisabled(false);
										}
									}
								}
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.totMoney'), // 实收到付总额,
								labelWidth : 90,
								columnWidth : 1 / 2,
								readOnly : true,
								name : 'totMoney'
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.claimNo'), // 款项认领编号,
								labelWidth : 90,
								columnWidth : 1 / 2,
								readOnly:true,
								name : 'claimNo'
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.changeReason'), // 变更原因,
								labelWidth : 60,
								columnWidth : 1,
								readOnly:true,
								name : 'changeReason'
							}]
				}, me.getFileUploadRepaymentGrid()]
			}, {
				// 收缩
				collapsible : true,
				width : 1024,
				// 是否有框
				frame : true,
				// 动画收缩
				animCollapse : true,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.arriveSheetInfo'), // 到达联信息,
				items : [me.getArriveSheetGrid(), {
					xtype : 'form',
					id : 'arriveSheetFormId',
					disabled : true,
					defaults : {
						margin : '5 10 5 10',
						labelWidth : 80
					},
					// 判断是否绑定
					eventFlag : false,
					bindEvent : function() {
						var arriveSheetMe = this, form = arriveSheetMe
								.getForm();
						var grid = me.getDedicatedChangeDetailGrid();
						var store = grid.getStore();
						if (arriveSheetMe.eventFlag) {
							return;
						}
	
						arriveSheetMe.getForm().getFields().each(
								function(item, index, length) {
									if (!(item.name == "changeReason")) {
										item.on('blur', function(component,
												eventObject, eOpts) {
											// 清除Grid数据
											var ary = new Array();
											store.each(function(rec) {
												if (rec.get('rfcType') == 'CHANGEDETAIL_TYPE_ARRIVESHEET'||rec.get('rfcType') == 'CHANGEDETAIL_TYPE_LABELTABLEFLG') {
													ary.push(rec);
												}
											});
											store.remove(ary);
											var record = form.getRecord();
											form.updateRecord(record);
											
											/*
											TagNumberGridAfterInfo= sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo;
											TagNumberGridBeforeInfo= sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo;
											SignOutGridBeforeInfo= sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo;
											SignOutGridAfterInfo= sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo;
											
											//如果签收情况是同票多类异常时,就把异常的变更前后信息全局变量清空,反之就把同票多类异常的变更前后信息全局变量清空
											//如果是正常签收,则同票多类异常和异常的变更前后信息全局变量都清空
											if(record.get('situation')=='UNNORMAL_SAMEVOTEODD'){//同票多类异常
												TagNumberGridBeforeInfo='';  //TagNumberGrid变更前的信息
												TagNumberGridAfterInfo='';  //TagNumberGrid变更后的信息
											}else if(record.get('situation')=='NORMAL_SIGN'){//正常签收
												TagNumberGridBeforeInfo='';  //TagNumberGrid变更前的信息
												TagNumberGridAfterInfo='';  //TagNumberGrid变更后的信息
												SignOutGridBeforeInfo=new Array(); //SignOutWithTicketStorageGridPanel变更前的信息
												SignOutGridAfterInfo=new Array(); //SignOutWithTicketStorageGridPanel变更后的信息
											}else{
												SignOutGridBeforeInfo=new Array(); //SignOutWithTicketStorageGridPanel变更前的信息
												SignOutGridAfterInfo=new Array(); //SignOutWithTicketStorageGridPanel变更后的信息
											}*/
											
											var changes = record.getChanges(), changeLogs = new Array();
											Ext.Object.each(changes, function(
													key, value, changes) {
												var field = form.findField(key);
												var log;
												if(Ext.isDate(value)){
													value = field.rawValue;
													record.set(field.name,Ext.Date.parse(value, "Y-m-d H:i:s", true));
													var modified =eval('Ext.Date.format(record.modified.' + key + ',"Y-m-d H:i:s",true)');
													if(Ext.isEmpty(modified)){
														return;
													}
													//eval('record.modified.' + key + '= Ext.Date.format(record.modified.' + key + ',"Y-m-d H:i:s",true)');
													log = {
													'rfcType' : 'CHANGEDETAIL_TYPE_ARRIVESHEET',
													'rfcItems' : field.fieldLabel,
													'rfcItemsCode' : field.name,
													'beforeRfcinfo' : modified,
													'afterRfcinfo' : value,
													'beforeRfcinfoValue' : modified,
													'afterRfcinfoValue' : value
													}
													changeLogs.push(log);
												}else {
													// 签收情况为部分签收的不记录
													if (eval('record.modified.' + key) != 'PARTIAL_SIGN') {
														log = {
														'rfcType' : 'CHANGEDETAIL_TYPE_ARRIVESHEET',
														'rfcItems' : field.fieldLabel,
														'rfcItemsCode' : field.name,
														'beforeRfcinfo' : eval('record.modified.' + key),
														'afterRfcinfo' : value,
														'beforeRfcinfoValue' : eval('record.modified.' + key),
														'afterRfcinfoValue' : value
														}
														changeLogs.push(log);
													}
												}
											});
											store.add(changeLogs);
											
											//如果签收情况选择前的值和选择后的值一样,则直接return 
											if(component.name=='situation'){
												if(sign.applicationSignRfc.arriveSheetForm_SignNoteCheckValue==component.value){
													sign.applicationSignRfc.arriveSheetForm_SignNoteCheckValue=component.value;
													return;
												}
											}
											
											//将TagNumberGrid中的变化信息添加到变更Grid中
											if(sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent
												&& Ext.getCmp('arriveSheetFormId').getForm().findField('situation').getValue( )!="NORMAL_SIGN"){//产生的变更信息是由签收情况Combox的select事件而产生的Blur事件
												if(Ext.getCmp('arriveSheetFormId').getForm().findField('situation').getValue( )!="UNNORMAL_SAMEVOTEODD"){//变更后的签收情况为"同票多类异常"时,不需要添加
													store.add(sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent_ChangeInfoArr);
												}
												//sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent=false;
											}else{//产生的变更信息是由tagNumberGrid的update事件而产生的Blur事件
												if(sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo!=''&&sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo!=''){
													var tmpTagNumberBefore= sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo;
													var tmpTagNumberAfter= sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo;
													if(tmpTagNumberBefore.indexOf(',')>=0&&tmpTagNumberAfter.indexOf(',')>=0){//含有多条变更信息的组合
														var tmpTagNumberBeforeArr=tmpTagNumberBefore.substring(0,(tmpTagNumberBefore.length)).split(',');//去除字符串最后一位逗号,然后再split
														var tmpTagNumberAfterArr=tmpTagNumberAfter.substring(0,tmpTagNumberAfter.length).split(',');
														var tmplog1 =new Array();
														for(var i=0;i<tmpTagNumberBeforeArr.length;i++){
															log = {
																'rfcType' : 'CHANGEDETAIL_TYPE_LABELTABLEFLG',
																'rfcItems' : '签收情况',
																'rfcItemsCode' : 'situation',
																'beforeRfcinfo' : tmpTagNumberBeforeArr[i],
																'afterRfcinfo' : tmpTagNumberAfterArr[i],
																'beforeRfcinfoValue' : tmpTagNumberBeforeArr[i],
																'afterRfcinfoValue' : tmpTagNumberAfterArr[i]
															};
															tmplog1.push(log);
														}
														store.add(tmplog1);
													}else{//只有一条变更信息
														log = {
																'rfcType' : 'CHANGEDETAIL_TYPE_LABELTABLEFLG',
																'rfcItems' : '签收情况',
																'rfcItemsCode' : 'situation',
																'beforeRfcinfo' : tmpTagNumberBefore,
																'afterRfcinfo' : tmpTagNumberAfter,
																'beforeRfcinfoValue' : tmpTagNumberBefore,
																'afterRfcinfoValue' : tmpTagNumberAfter
															};
														store.add(log);
													}
												}
											}
											
											
											//将同票多类异常的变更信息添加到变更Grid中
											if(sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo!=null
												&&sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.length>0
												&&sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo!=null
												&&sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo.length>0){
												//遍历同票多类异常的变更信息
												var tmpLog2= new Array();
												for(var i=0;i<sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo.length;i++){
													log = {
														'rfcType' : 'CHANGEDETAIL_TYPE_LABELTABLEFLG',
														'rfcItems' : '签收情况',
														'rfcItemsCode' : 'situation',
														'beforeRfcinfo' : sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo[i],
														'afterRfcinfo' : sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo[i],
														'beforeRfcinfoValue' : sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo[i],
														'afterRfcinfoValue' : sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo[i]
													};
													tmpLog2.push(log);
												}
												store.add(tmpLog2);
//												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array();
//												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array();
											}
											
										});
									}
								});
						
						
						
						arriveSheetMe.eventFlag = true;
					},
					layout : 'column',
					columnWidth : 1 / 2,
					items : [{
								xtype : 'hiddenfield',
								name : 'waybillNo' // 运单号
							}, {
								xtype : 'hiddenfield',
								name : 'id' // 到达联ID
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.reachDeliverymanName'), // 到达提货人,
								labelWidth : 80,
								columnWidth : 1 / 2,
								name : 'deliverymanName'
							}, {
								xtype : 'combobox',
								name : 'identifyType',
								labelWidth : 60,
								columnWidth : 1 / 2,
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.identifyType'), // 证件类型,
								queryModel : 'local',
								displayField : 'valueName',
								valueField : 'valueCode',
								editable : false,
								store : FossDataDictionary
										.getDataDictionaryStore(
												'PKP_CREDENTIAL_TYPE', null/*
																			 * , {
																			 * 'valueCode' :
																			 * '',
																			 * 'valueName' : '' }
																			 */)

							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.signNote'), // 签收备注,
								labelWidth : 60,
								columnWidth : 1 / 2,
								name : 'signNote'
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.identifyCode'), // 证件号码,
								labelWidth : 60,
								columnWidth : 1 / 2,
								name : 'identifyCode'
							}, {
								xtype : 'datetimefield_date97',// 核定时间
								format : 'Y-m-d H:i:s',
								id : 'sign-QueryPanelDedicated-signTimeEnd',// id
								// //
								// 必须填写
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.signTime'), // 签收时间,
								// //
								// 字段标题
								columnWidth : 1 / 2,
								name : 'signTime',
								time : true,
								readOnly:true,
								dateConfig : {
									el : 'sign-QueryPanelDedicated-signTimeEnd-inputEl',
									dateFmt : 'yyyy-MM-dd HH:ii:ss'
								}
							}, {
								xtype : 'combobox',
								name : 'situation',
								labelWidth : 60,
								columnWidth : 1 / 2,
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.signSituation'), // 签收情况,
								forceSelection : true, // 只允许从下拉列表中进行选择，不能输入文本
								editable : false, // 不可编辑
								forceSelection : true,// 必须选择一个。
								valueField : 'valueCode',
								displayField : 'valueName',
								queryMode : 'local',
								triggerAction : 'all',
								store : sign.applicationSignRfc.situationStore2,
								listeners : {
									'change':function(field,newValue,oldValue,eOpts ){
										if(oldValue!=null){//如果签收情况修改为"同票多类异常"时,如果流水号个数小于2,则不给修改。
											if(sign.applicationSignRfc.SIGN_SERIAL_NO_ARR.length<=1 && newValue == 'UNNORMAL_SAMEVOTEODD'){
												field.setValue(oldValue);
											}
										}
									},
									'select' : function(combo, records, eOpts){
										if(records[0].get('valueCode')=='UNNORMAL_SAMEVOTEODD'){//同票多类异常
											//如果流水号的数量小于等于1的时候,则提示不可以将签收情况修改为'同票多类异常'
											var serialNoCount= sign.applicationSignRfc.SIGN_SERIAL_NO_ARR.length;
											if(serialNoCount<=1){
												Ext.ux.Toast.msg('提示', '签收情况不可改变为同票多类异常','error', 1000);
												return false;
											}
											var _tagNumberGrid= Ext.getCmp('tagNumberGridId');
											_tagNumberGrid.hide();
											/**
											 *显示同票多类异常Form
											*/
											//初始化变更信息
											sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array(); //同票多类异常的变更前的信息
											sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array(); //同票多类异常变更后的信息
											sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
											sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
											//初始化同票多类异常Grid数据
											var _multiExceptionSignPiecesForm = Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
											var _multiExceptionGridStore = _multiExceptionSignPiecesForm.getSignOutWithTicketStorageGridPanel().getStore();
											_multiExceptionGridStore.removeAll();
											_multiExceptionGridStore.loadData(sign.applicationSignRfc.MultiExceptionForm_GridData_SAMEVOTEODD,true);
											//初始化无差异流水号TextArea数据
											var signSeriaNoNote = Ext.getCmp('applicationSignRfc_signSeriaNoNote_id');
											signSeriaNoNote.setValue(sign.applicationSignRfc.MultiExceptionForm_TextAreaData_SAMEVOTEODD.join(','));
											//显示Form
											_multiExceptionSignPiecesForm.show();
											
										}else{
											var _tagNumberGrid= Ext.getCmp('tagNumberGridId');
											/**
											*如果是异常签收
											*/
											if(records[0].get('valueCode') != ''&&records[0].get('valueCode') != 'NORMAL_SIGN'){
												var headerFlag = false;
												for(var x=0;x<sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL.length;x++){
													if(sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL[x].goodShorts){
														headerFlag = true;
														break;
													}
												}
												/*if(headerFlag){
													var headerStr=records[0].get('valueName')+'<input type="checkbox" checked="true" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
												}else{
													var headerStr=records[0].get('valueName')+'<input type="checkbox"; name="全选"; onchange="javascript:checkboxClick(this.checked)"/>全选';
												}*/
												var headerStr=records[0].get('valueName');
												_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
												//初始化TagNumberGrid的数据
												_tagNumberGrid.getStore().removeAll();
												_tagNumberGrid.getStore().loadData(sign.applicationSignRfc.TagNumberGrid_Data_UNNORMAL,true);
												
												/*
												 *如果Form中签收情况和到达联Grid中的签收情况不同时,要将TagNumberGrid中选中的信息添加到变更Grid中
												 */
												 //1.获取到达联Grid
												 var arriveSheetGrid = Ext.getCmp('arriveSheetGridId');
												 //2.获取到达联Grid中所选中行的签收情况
												 var arriveSheetGrid_Situation = arriveSheetGrid.getSelectionModel( ).getSelection( )[0].get('situation');
												if(arriveSheetGrid_Situation != records[0].get('valueCode')){
													//3.组装数据用于插入到变更Grid中
													var tmpLog =  new Array();
													sign.applicationSignRfc.serialNoSituation_SelectedKeyValue.each(function(key, value, length){//key为流水号,value为签收情况
														if(value != records[0].get('valueCode')){
															log = {
																'rfcType' : 'CHANGEDETAIL_TYPE_LABELTABLEFLG',
																'rfcItems' : '签收情况',
																'rfcItemsCode' : 'situation',
																'beforeRfcinfo' : key+"."+value,
																'afterRfcinfo' : key+"."+records[0].get('valueCode'),
																'beforeRfcinfoValue' : key+"."+value,
																'afterRfcinfoValue' : key+"."+records[0].get('valueCode')
															};
															tmpLog.push(log);
														}
													})
													if(tmpLog.length >0){
														sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent=true;
														sign.applicationSignRfc.SituationComboxBlurEventBySelectEvent_ChangeInfoArr = tmpLog;
													}
												}
												
												//初始化变更信息
												sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
												sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array(); //同票多类异常的变更前的信息
												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array(); //同票多类异常变更后的信息
												
												_tagNumberGrid.columns[1].show();
											}else{//正常签收
//												var headerStr=records[0].get('valueName')+'<input type="checkbox" name="全选" onchange="javascript:checkboxClick(this.checked)"/>全选';
												var headerStr=records[0].get('valueName');
												_tagNumberGrid.getView().getHeaderCt().getHeaderAtIndex(1).setText(headerStr);
												//将_tagNumberGrid的第二列隐藏掉
												_tagNumberGrid.columns[1].hide();
												//初始化TagNumberGrid的数据
												_tagNumberGrid.getStore().removeAll();
												_tagNumberGrid.getStore().loadData(sign.applicationSignRfc.TagNumberGrid_Data_NORMAL,true);
												//初始化变更信息
												sign.applicationSignRfc.TagNumberGrid_BeforeChangeInfo='';  //TagNumberGrid变更前的信息
												sign.applicationSignRfc.TagNumberGrid_AfterChangeInfo='';  //TagNumberGrid变更后的信息
												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_BeforeChangeInfo=new Array(); //同票多类异常的变更前的信息
												sign.applicationSignRfc.SignOutWithTicketStorageGridPanel_AfterChangeInfo=new Array(); //同票多类异常变更后的信息
											}
											//对于整车签收,没有流水号的情况,TagNumberGrid不显示
											if(sign.applicationSignRfc.SIGN_SERIAL_NO_ARR.length>=1 && sign.applicationSignRfc.SIGN_SERIAL_NO_ARR!=""){
												_tagNumberGrid.show();  
											}
												
											//隐藏同票多类异常Form
											var _multiExceptionSignPiecesForm= Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
											_multiExceptionSignPiecesForm.hide(); 
										}
									}
								}
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.signSituation'), // 签收情况,
								labelWidth : 60,
								columnWidth : 1 / 2,
								name : 'situationPartial',
								id : 'Foss_sign_applicationSignRfc_situationPartial_Id'
							}, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.changeReason'), // 变更原因,
								labelWidth : 60,
								columnWidth : 1,
								name : 'changeReason'
							}
							/*, {
								xtype : 'textfield',
								fieldLabel : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.signNo'), // 标签编号,
								labelWidth : 60,
								columnWidth : 1,
								name : 'tagNumber',
								readOnly : true
							}*/
							]
				},me.getTagNumberGrid(),me.getMultiExceptionSignRfcForm(),me.getFileUploadArriveSheetGrid()]
			}, {
				// 收缩
				collapsible : true,
				width : 1024,
				// 是否有框
				frame : true,
				// 动画收缩
				animCollapse : true,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.changeInfo'), // 变更信息,
				items : [me.getDedicatedChangeDetailGrid(), {
					border : 1,
					xtype : 'container',
					columnWidth : 1,
					defaultType : 'button',
					layout : 'column',
					items : [{
								xtype : 'container',
								border : false,
								columnWidth : .42,
								html : '&nbsp;'
							}, {
								xtype : 'button',
								text : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.submit'), // 提交,
								disabled:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexsavezbutton'),
								hidden:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexsavezbutton'),
								plugins : Ext.create(
										'Deppon.ux.ButtonLimitingPlugin', {
											// 设定间隔秒数,如果不设置，默认为2秒
											seconds : 3
										}),
								columnWidth : 1 / 10,
								handler : function() {
									var grid = me
											.getDedicatedChangeDetailGrid();
									var item = grid.getStore().getNewRecords();
									if (item.length < 1) {
										Ext.ux.Toast.msg('提示', '请输入需要变更的数据',
												'error', 1000);
										return;
									}
									var repaymentParms = Ext
											.getCmp('repaymentFormId')
											.getValues();
									var arriveSheetParms = Ext
											.getCmp('arriveSheetFormId')
											.getValues();
									var waybillNo;
									// 签收时间
									var signTime;
									if (!(typeof repaymentParms.waybillNo == 'undefined')
											&& repaymentParms.waybillNo != '') {
										waybillNo = repaymentParms.waybillNo;
									} else {
										waybillNo = arriveSheetParms.waybillNo;
									}

									// 付款凭证
									var fileRepaymentGrid = me
											.getFileUploadRepaymentGrid();
									var fileRepaymentArray = new Array();
									fileRepaymentGrid.getStore().each(
											function(record) {
												fileRepaymentArray.push({
													'id' : record.get('id')
//													,
//													'name' : record.get('name'),
//													'type' : record.get('type'),
//													'size' : record.get('size'),
//													'status' : record
//															.get('status'),
//													'relativePath' : record
//															.get('relativePath')
												});
											});
									// 到达联凭证
									var fileArriveSheetGrid = me
											.getFileUploadArriveSheetGrid();
									var fileArriveSheetArray = new Array();
									fileArriveSheetGrid.getStore().each(
											function(record) {
												fileArriveSheetArray.push({
													'id' : record.get('id')
//													,
//													'name' : record.get('name'),
//													'type' : record.get('type'),
//													'size' : record.get('size'),
//													'status' : record
//															.get('status'),
//													'relativePath' : record
//															.get('relativePath')
												});
											});
									var changeDetailentitys = new Array();
									var reFlg, arFlg, zeroFlg = false;
									for (var i = 0; i < item.length; i++) {
										changeDetailentitys.push({
													'rfcType' : item[i]
															.get('rfcType'),
													'rfcItems' : item[i]
															.get('rfcItems'),
													'rfcItemsCode' : item[i]
															.get('rfcItemsCode'),
													'beforeRfcinfo' : item[i]
															.get('beforeRfcinfo'),
													'afterRfcinfo' : item[i]
															.get('afterRfcinfo')
												});
										if (item[i].get('rfcType') == 'CHANGEDETAIL_TYPE_REPAYMENT') {
											reFlg = true;
										} else {
											arFlg = true;
											if (item[i].get('rfcItemsCode') == 'signTime') {
												signTime = item[i]
														.get('afterRfcinfo');
											}
										}
										if (item[i].get('rfcItemsCode') == 'actualFreight'
												|| item[i].get('codAmount')) {
											zeroFlg = true;
										}
									}
									// 判断改为0，不可以提交
									if (repaymentParms.totMoney == 0 && zeroFlg) {
										Ext.ux.Toast.msg('提示', '实收到付总额不能改为0',
												'error', 1000);
										return;
									}
									if (reFlg) {
										if (repaymentParms.paymentType == 'CD' && repaymentParms.claimNo.trim() == ''){
											Ext.ux.Toast
													.msg('提示', '请输入款项认领编号',
															'error', 1000);
											return;
										}
										if(repaymentParms.paymentType == 'CD' && !/^\s*\d+\s*$/.test(repaymentParms.claimNo)){
											Ext.ux.Toast
													.msg('提示', '付款方式为银行卡时，款项认领编号必须输入数字。',
															'error', 1000);
											return;
										}
										if (repaymentParms.changeReason == '') {
											Ext.ux.Toast
													.msg('提示', '请输入付款的变更原因',
															'error', 1000);
											return;
										}
									}
									if (arFlg) {
										if (arriveSheetParms.signTime == '') {
											Ext.ux.Toast.msg('提示', '请输入签收时间',
													'error', 1000);
											return;
										}
										if (arriveSheetParms.changeReason == '') {
											Ext.ux.Toast.msg('提示',
													'请输入到达联的变更原因', 'error',
													1000);
											return;
										}
									}
									
									//如果同票多类异常Form是显示的时候,当里面的数据小于两行的时候,不予以提交
									//并且这两行数据都要被填充
									var ticketFormPanel= Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID');
									if(ticketFormPanel !=null){
										if(ticketFormPanel.isVisible()){
											var mulStorageGridPanel = ticketFormPanel.getSignOutWithTicketStorageGridPanel();
											var mulStorageGridPanel_Store = mulStorageGridPanel.getStore();
											var mulStorageGridPanel_StoreCount=mulStorageGridPanel_Store.getCount();
											//获取异常流水号列的数据,如果该列有的行没有数据，则这一行数据没有完全填充
											var serialNoArr = mulStorageGridPanel_Store.collect('serialNo');
											if(mulStorageGridPanel_StoreCount<=1){
												Ext.ux.Toast.msg('提示',
														'同票多类异常新增数据不可少于两行', 'error',
														1000);
												return;
											}else{
												if(mulStorageGridPanel_StoreCount!=serialNoArr.length){
													Ext.ux.Toast.msg('提示',
														'同票多类异常新增数据请填写完整', 'error',
														1000);
														return;
												}
											}
										}
									}
									//当到达联Form中的签收情况是异常(不是同票多类异常并且也不是正常签收)时,
									//TagNumberGrid中的checkBox列中至少要有一个被选中
									var _tmpTagNumberGrid = Ext.getCmp('tagNumberGridId');
									if(_tmpTagNumberGrid != null){
										if(_tmpTagNumberGrid.isVisible()){
											//1.获取到达联Form
											var arriveSheetForm = Ext.getCmp('arriveSheetFormId').getForm();
											//2.获取Form中的签收情况
											var situationValue= arriveSheetForm.findField('situation').getValue( );
											if(situationValue!='' && situationValue!='NORMAL_SIGN' && situationValue!='UNNORMAL_SAMEVOTEODD'){
												//3.获取TagNumberGrid的Store
												var tagNUmberGrid_StoreTmp = Ext.getCmp('tagNumberGridId').getStore();
												//4.获取Store中的记录数量
												var storeCountTmp = tagNUmberGrid_StoreTmp.getCount();
												if(storeCountTmp<0){
													Ext.ux.Toast.msg('提示',
															'签收情况为异常时,流水号不可以为空', 'error',
															1000);
															return;
												}
												//5.获取TagNumberGrid中checkBox列
												var checkBoxValuesArr = tagNUmberGrid_StoreTmp.collect('goodShorts');
												if(checkBoxValuesArr.indexOf(true)<0){
													Ext.ux.Toast.msg('提示',
															'签收情况为异常时,流水号至少要有一个被选中', 'error',
															1000);
															return;
												}
											}
										}
									}
									
									Ext.MessageBox.confirm('确认框', '你确认保存吗?',
											function(btn) {
												if (btn == 'yes') {
													Ext.Ajax.request({
														url : sign
																.realPath('saveChangeDedicated.action'),
														method : 'POST',
														jsonData : {
															'vo' : {
																'signResultDto' : {
																	'repaymentFiles' : fileRepaymentArray,
																	'arrivesheetFiles' : fileArriveSheetArray,
																	'signTime' : signTime,
																	'changeDetailentity' : changeDetailentitys,
																	'repaymentChangeReason' : repaymentParms.changeReason,
																	'arrivesheetChangeReason' : arriveSheetParms.changeReason,
																	'tSrvRepaymentId' : repaymentParms.id,
																	'tSrvArrivesheetId' : arriveSheetParms.id,
																	'signRfcEntity' : {
																		'waybillNo' : waybillNo
																	}
																}
															}
														},
														success : function(
																response) {
															var arriveSheetForm = Ext
																	.getCmp('arriveSheetFormId');
															var repaymentForm = Ext
																	.getCmp('repaymentFormId');
															arriveSheetForm
																	.getForm()
																	.reset();
															repaymentForm
																	.getForm()
																	.reset();
															arriveSheetForm
																	.setDisabled(true);
															repaymentForm
																	.setDisabled(true);
															me
																	.getRepaymentGrid()
																	.getStore()
																	.removeAll();
															me
																	.getArriveSheetGrid()
																	.getStore()
																	.removeAll();
															me
																	.getDedicatedChangeDetailGrid()
																	.getStore()
																	.removeAll();
															me
																	.getFileUploadRepaymentGrid()
																	.getStore()
																	.removeAll();
															me
																	.getFileUploadArriveSheetGrid()
																	.getStore()
																	.removeAll();
															Ext.getCmp('tagNumberGridId').hide();
															Ext.getCmp('Foss.sign.applicationSignRfc.withTicketFormPanel_ID').hide();
															Ext.ux.Toast.msg(
																	'提示',
																	'保存成功!',
																	'ok', 1000);
														},
														exception : function(
																response) {
															var result = Ext
																	.decode(response.responseText);
															Ext.ux.Toast
																	.msg(
																			'提示',
																			result.message,
																			'error',
																			3000);
														}
													});
												}
											});

								}
							}, {
								text : sign.applicationSignRfc
										.i18n('pkp.sign.applicationSignRfc.cancel'), // 取消,
								columnWidth : .08,
								handler : function() {
									var myform = this.up('form');
									myform.getForm().reset();
								}
							}, {
								xtype : 'container',
								border : false,
								columnWidth : .42,
								html : '&nbsp;'
							}]

				}]
			}]
		});
		me.callParent(arguments);
	},
	listeners : {
		beforerender : function(_this, eOpts){
			Ext.getCmp('Foss_sign_applicationSignRfc_situationPartial_Id').setVisible(false);
		}
	} 
});

(function() {
	sign.applicationSignRfc.situationStore = FossDataDictionary
			.getDataDictionaryStore(sign.applicationSignRfc.PKP_SIGN_SITUATION,
					null);
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'GOODS_BACK'));
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'UNNORMAL_SIGN'));
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'UNNORMAL_LOSTCARGO'));
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'UNNORMAL_CONTRABAND'));
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'UNNORMAL_ABANDONGOODS'));
	sign.applicationSignRfc.situationStore.removeAt(sign.applicationSignRfc.situationStore.find('valueCode', 'UNNORMAL_SAMEVOTEODD'));
	
})();
// 空运/偏线/快递代理
Ext.define('Foss.sign.applicationSignRfc.AirliftPartialLineForm', {
	extend : 'Ext.form.Panel',
	//id: 'Foss_sign_applicationSignRfc_AirliftPartialLineForm_ID',
	defaults : {
		margin : '5 10 5 10'
	},
	width : 1000,

	fileUploadGrid : null,
	getFileUploadGrid : function() {
		if (this.fileUploadGrid == null) {
			this.fileUploadGrid = Ext.create(
					'Foss.applicationSignRfc.Grid.FileUploadGrid2', {
						columnWidth : 1,
						autoScroll : true
					});
		}
		return this.fileUploadGrid;
	},

	airliftPartialLineChangeDetailGrid : null,
	getAirliftPartialLineChangeDetailGrid : function() {
		if (this.airliftPartialLineChangeDetailGrid == null) {
			this.airliftPartialLineChangeDetailGrid = Ext.create(
					'Ext.grid.Panel', {
						columnWidth : 1,
						autoScroll : true,
						height : 120,
						store : Ext.create('Ext.data.Store', {
							model : 'Foss.sign.applicationSignRfc.ChangeSignDetailModel',
							proxy : {
								type : 'memory',
								reader : {
									type : 'json'
								}
							}
						}),
						columns : [{
							xtype : 'gridcolumn',
							dataIndex : 'rfcType',
							flex : 0.5,
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.rfcType'), // 变更类型,
							renderer : function(value) {
								return FossDataDictionary
										.rendererSubmitToDisplay(value,
												'PKP_SIGN_RFC_DETAIL_TYPE');
							}
						}, {
							xtype : 'gridcolumn',
							dataIndex : 'rfcItems',
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.rfcItems')
								// 变更项
						}, {
							xtype : 'gridcolumn',
							hidden : true,
							dataIndex : 'rfcItemsCode',
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.rfcItemsCode')
								// 变更项CODE
						}, {
							xtype : 'gridcolumn',
							dataIndex : 'beforeRfcinfo',
							flex : 1,
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.beforeRfcinfo'), // 变更前信息
							renderer : function(value, metadata, record,
									rowIndex, colIndex, store) {
								if (record.get("rfcItemsCode") == 'paymentType') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'SETTLEMENT__PAYMENT_TYPE');
								} else if (record.get("rfcItemsCode") == 'identifyType') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_CREDENTIAL_TYPE');
								} else if (record.get("rfcItemsCode") == 'signSituation') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_SIGN_SITUATION');
								} else {
									return value;
								}
							}
						}, {
							xtype : 'gridcolumn',
							dataIndex : 'afterRfcinfo',
							flex : 1,
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.afterRfcinfo'), // 变更后信息
							renderer : function(value, metadata, record,
									rowIndex, colIndex, store) {
								if (record.get("rfcItemsCode") == 'paymentType') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'SETTLEMENT__PAYMENT_TYPE');
								} else if (record.get("rfcItemsCode") == 'identifyType') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_CREDENTIAL_TYPE');
								} else if (record.get("rfcItemsCode") == 'signSituation') {
									return FossDataDictionary
											.rendererSubmitToDisplay(value,
													'PKP_SIGN_SITUATION');
								} else {
									return value;
								}
							}
						}]
					});
		}
		return this.airliftPartialLineChangeDetailGrid;
	},
	layout : 'column',
	// 判断是否绑定
	eventFlag : false,
	bindEvent : function() {
		var me = this, form = me.getForm();
		grid = me.getAirliftPartialLineChangeDetailGrid();
		if (me.eventFlag) {
			return;
		}
		me.getForm().getFields().each(function(item, index, length) {
			if (!(item.name == "changeReason" || item.name == "searchWaybillNo")) {
				item.on('blur', function(component, eventObject, eOpts) {
					var record = form.getRecord();
					if (record != null && record.data.id != '') {
						form.updateRecord(record);
						var changes = record.getChanges(), changeLogs = new Array();
						Ext.Object.each(changes, function(key, value, changes) {
							var field = form.findField(key);
							var log;
							if(Ext.isDate(value)){
								value = field.rawValue;
								if(value === eval('record.modified.' + key)){
									return;
								}
							}
							log = {
									'rfcType' : 'CHANGEDETAIL_TYPE_WAYBILLSIGNRESULT',
									'rfcItems' : field.fieldLabel,
									'rfcItemsCode' : field.name,
									'beforeRfcinfo' : eval('record.modified.' + key),
									'afterRfcinfo' : value
								}
							changeLogs.push(log);
						});
						grid.getStore().loadData(changeLogs);
					}
				});
			}
		});
		me.eventFlag = true;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [{
				width : 1000,
				frame : true,
				collapsible : true,
				animCollapse : true,
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.searchWaybill'), // 查询运单,
				items : [{
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'textfield',
							vtype: 'waybill',
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.waybillNo'), // 运单号,
							name : 'searchWaybillNo',
							allowBlank : false,
							columnWidth : .30
						}, {
							xtype : 'container',
							border : false,
							columnWidth : .31,
							html : '&nbsp;'
						}, {
							xtype : 'button',
							text : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.search'), // 查询,
									disabled:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexquerypbutton'),
							hidden:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexquerypbutton'),
							cls : 'yellow_button',
							columnWidth : .08,
							handler : function() {
								var myform = this.up('form').getForm();
								var serachParms = myform.getValues();
								// 条件运单号
								var searchWaybillNo = serachParms.searchWaybillNo;
								// 清空上次的数据
								myform.reset();
								myform.findField('signSituation')
										.setReadOnly(false);
								myform.findField('signGoodsQty')
										.setReadOnly(false);
								// 条件设置
								myform.findField('searchWaybillNo')
										.setValue(searchWaybillNo);
								if (searchWaybillNo == '') {
									Ext.ux.Toast.msg('提示', '请输入运单号', 'error',
											1000);
									return;
								}
								me.getAirliftPartialLineChangeDetailGrid()
										.getStore().removeAll();
								if (myform.isValid()) {
									Ext.Ajax.request({
										url : sign
												.realPath('querySignResultAirPartialLine.action'),
										params : {
											'vo.signResultDto.signRfcEntity.waybillNo' : serachParms.searchWaybillNo
										},
										success : function(response) {
											var result = Ext
													.decode(response.responseText);
											var formModel = new Foss.sign.applicationSignRfc.WaybillSignResultModel(result.vo.waybillSignResultEntity);
											if(Ext.isEmpty(formModel.data.id)){
												return;
											}
											formModel.data.signTime =Ext.Date.format(formModel.data.signTime,'Y-m-d H:i:s');
											sign.applicationSignRfc.AirliftPartialLineForm
													.loadRecord(formModel);
											if (myform
													.findField('signSituation')
													.getValue() != 'PARTIAL_SIGN') {
												myform
														.findField('signGoodsQty')
														.setReadOnly(true);
											} else {
												myform
														.findField('signGoodsQty')
														.setReadOnly(false);
											}
											me.bindEvent();
										},
										exception : function(response) {
											var result = Ext
													.decode(response.responseText);
											Ext.ux.Toast.msg('提示',
													result.message, 'error',
													1000);
											//sign.applicationSignRfc.AirliftPartialLineForm
											//		.loadRecord(null);
											//Ext.getCmp("Foss_sign_applicationSignRfc_AirliftPartialLineForm_ID").getForm().reset();
											sign.applicationSignRfc.AirliftPartialLineForm.getForm().reset();
											sign.applicationSignRfc.AirliftPartialLineForm.getForm().findField('searchWaybillNo').setValue(searchWaybillNo);
										}
									});
								}
							}
						}]
			}, {
				frame : true,
				width : 1000,
				height : 200,
				collapsible : true,
				animCollapse : true,
				defaults : {
					margin : '5 10 5 10',
					anchor : '100%'
				},
				layout : {
					type : 'column'
				},
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.signInfo'), // 签收信息,
				items : [{
							xtype : 'hiddenfield',
							name : 'id' // 运单签收结果ID
						}, {
							xtype : 'hiddenfield',
							name : 'waybillNo' // 运单号
						}, {
							xtype : 'hiddenfield',
							name : 'signCount' // 运单货物件数
						}, {
							xtype : 'textfield',
							name : 'deliverymanName',
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.reachDeliverymanName')
							// 到达提货人
						}, {
							xtype : 'combobox',
							name : 'identifyType',
							labelWidth : 60,
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.identifyType'), // 证件类型,
							queryModel : 'local',
							displayField : 'valueName',
							valueField : 'valueCode',
							editable : false,
							store : FossDataDictionary.getDataDictionaryStore(
									'PKP_CREDENTIAL_TYPE', null)
						}, {
							xtype : 'textfield',
							name : 'identifyCode',
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.identifyCode')
							// 证件号码
						}, {
							xtype : 'numberfield',
							name : 'signGoodsQty',
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.signGoodsQty'), // 签收件数,
							hideTrigger : true,
							keyNavEnabled : false,
							mouseWheelEnabled : false,
							minValue : 1,
							listeners : {
								'change' : function(field, newValue, oldValue,
										eOpts) {
									var form = this.up('form').getForm();
									var signCount = parseInt(form
											.findField('signCount').getValue());
									var signGoodsQty = parseInt(newValue);
									if (signCount > signGoodsQty) {
										form.findField('signSituation')
												.setValue('PARTIAL_SIGN');
									} else if (signCount == signGoodsQty) {
										form.findField('signSituation')
												.setValue('NORMAL_SIGN');
									} else {
										this.maxValue = signCount;
									}
									/*
									 * else if(signCount == signGoodsQty){
									 * 
									 * }else{
									 * Ext.ux.Toast.msg('提示','signCount'+signCount+'签收件数不可以大于运单上货物件数'+'newValue'+newValue,
									 * 'error',1000); }
									 */
								}
							}
						}, {
							xtype : 'combobox',
							name : 'signSituation',
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.signSituation'), // 签收情况,
							forceSelection : true, // 只允许从下拉列表中进行选择，不能输入文本
							editable : false, // 不可编辑
							forceSelection : true,// 必须选择一个。
							valueField : 'valueCode',
							displayField : 'valueName',
							queryMode : 'local',
							triggerAction : 'all',
							store : sign.applicationSignRfc.situationStore,
							listeners : {
								'select' : function(combo, records, eOpts) {
									var form = this.up('form').getForm(), record = form
											.getRecord();
									// 签收情况选择为部分签收时，签收件数显示为到达联件数且可编辑
									if (records[0].get('valueCode') == 'PARTIAL_SIGN') {
										form.findField('signGoodsQty').setReadOnly(false);
									} else {
										if (record != null) {
											form.findField('signGoodsQty').setValue(record.get('signCount'));
										}
										form.findField('signGoodsQty').setReadOnly(true);
									}
								}
							}
						}, {
							xtype : 'datetimefield_date97',// 核定时间
							format : 'Y-m-d H:i:s',
							id : 'sign-QueryPanelAirliftPartialLine-signTimeEnd',// id
							// 必须填写
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.signTime'), // 签收时间,
							// 字段标题
							columnWidth : 1 / 3,
							name : 'signTime',
							time : true,
							readOnly:true,
							dateConfig : {
								el : 'sign-QueryPanelAirliftPartialLine-signTimeEnd-inputEl',
								dateFmt : 'yyyy-MM-dd HH:ii:ss'
							}
						}, {
							xtype : 'textfield',
							name : 'signNote',
							columnWidth : 1 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.signNote')
							// 签收备注
						}, {
							xtype : 'textfield',
							name : 'signNo',
							columnWidth : 2 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.signNo'), // 标签编号,
							readOnly : true
						}, {
							xtype : 'textfield',
							name : 'changeReason',
							columnWidth : 2 / 3,
							fieldLabel : sign.applicationSignRfc
									.i18n('pkp.sign.applicationSignRfc.changeReason')
							// 变更原因
						}]
			}, {
				// 收缩
				collapsible : true,
				width : 1000,
				// 是否有框
				frame : true,
				// 动画收缩
				animCollapse : true,
				defaults : {
					margin : '5 10 5 10',
					labelWidth : 80
				},
				layout : 'column',
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.changeInfo'), // 变更信息,
				items : [me.getAirliftPartialLineChangeDetailGrid(),
						me.getFileUploadGrid(), {
							border : 1,
							xtype : 'container',
							columnWidth : 1,
							defaultType : 'button',
							layout : 'column',
							items : [{
										xtype : 'container',
										border : false,
										columnWidth : .42,
										html : '&nbsp;'
									}, {
										text : sign.applicationSignRfc
												.i18n('pkp.sign.applicationSignRfc.submit'), // 提交,
												disabled:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexsavepbutton'),
										hidden:!sign.applicationSignRfc.isPermission('applicationsignrfcindex/applicationsignrfcindexsavepbutton'),
										columnWidth : .08,
										plugins : Ext
												.create(
														'Deppon.ux.ButtonLimitingPlugin',
														{
															// 设定间隔秒数,如果不设置，默认为2秒
															seconds : 3
														}),
										handler : function() {
											var grid = me
													.getAirliftPartialLineChangeDetailGrid();

											var item = grid.getStore()
													.getNewRecords();
											var serachForm = this.up('form')
													.getForm();
											var serachParms = serachForm
													.getValues();
											// 保存的时候，对数据的校验
											var signCount = parseInt(serachParms.signCount);
											var signGoodsQty = parseInt(serachParms.signGoodsQty);
											if (signCount < signGoodsQty) {
												Ext.ux.Toast.msg('提示',
														'签收件数不可以大于运单上货物件数',
														'error', 1000);
												return;
											}
											var changeDetailentitys = new Array();

											var fileGrid = me
													.getFileUploadGrid();

											var filearray = new Array();
											fileGrid.getStore().each(
													function(record) {
														filearray.push({
															'id' : record
																	.get('id')
//																	,
//															'name' : record
//																	.get('name'),
//															'type' : record
//																	.get('type'),
//															'size' : record
//																	.get('size'),
//															'status' : record
//																	.get('status'),
//															'relativePath' : record
//																	.get('relativePath')
														});
													});

											var flg = false;
											if (item.length < 1) {
												Ext.ux.Toast.msg('提示',
														'请输入需要变更的数据', 'error',
														1000);
												return;
											}
											for (var i = 0; i < item.length; i++) {
												changeDetailentitys.push({
													'rfcItems' : item[i]
															.get('rfcItems'),
													'rfcItemsCode' : item[i]
															.get('rfcItemsCode'),
													'beforeRfcinfo' : item[i]
															.get('beforeRfcinfo'),
													'afterRfcinfo' : item[i]
															.get('afterRfcinfo')
												});
												flg = true;
											}
											if (flg) {
												if (serachParms.changeReason == '') {
													Ext.ux.Toast.msg('提示',
															'请输入变更原因', 'error',
															1000);
													return;
												}
											}
											if (Ext.isEmpty(serachParms.waybillNo)) {
												Ext.ux.Toast.msg('提示',
														'请输入运单号，点击查询后再提交！', 'error',
														1000);
												return;
											}
											Ext.MessageBox.confirm('确认框',
													'你确认保存吗?', function(btn) {
														if (btn == 'yes') {
															Ext.Ajax.request({
																url : sign
																		.realPath('saveChangeAirliftPartialLine.action'),
																method : 'POST',
																jsonData : {
																	'vo' : {
																		'signResultDto' : {
																			'waybillSignResultFiles' : filearray,
																			'changeDetailentity' : changeDetailentitys,
																			'signRfcEntity' : {
																				'waybillNo' : serachParms.waybillNo,
																				'reason' : serachParms.changeReason,
																				'tSrvWaybillSignResultId' : serachParms.id
																			}
																		}
																	}
																},
																success : function(
																		response) {
																	Ext.ux.Toast
																			.msg(
																					'提示',
																					'保存成功!',
																					'ok',
																					1000);
																	serachForm
																			.reset();
																	grid
																			.getStore()
																			.removeAll();
																	me
																			.getFileUploadGrid()
																			.getStore()
																			.removeAll();
																},
																exception : function(
																		response) {
																	var result = Ext
																			.decode(response.responseText);
																	Ext.ux.Toast
																			.msg(
																					'提示',
																					result.message,
																					'error',
																					1000);
																}
															});
														}
													});
										}
									}, {
										text : sign.applicationSignRfc
												.i18n('pkp.sign.applicationSignRfc.cancel'), // 取消,
										columnWidth : .08,
										handler : function() {
											var myform = this.up('form');
											var grid = me
													.getAirliftPartialLineChangeDetailGrid();
											myform.getForm().reset();
											grid.getStore().removeAll();
											me.getFileUploadGrid().getStore()
													.removeAll();
											sign.applicationSignRfc.AirliftPartialLineForm.getForm().reset();
										}
									}, {
										xtype : 'container',
										border : false,
										columnWidth : .42,
										html : '&nbsp;'
									}]
						}]
			}]
		});
		me.callParent(arguments);
	}
});

// 上传付款文件的panel
Ext.define('Foss.applicationSignRfc.Grid.FileUploadRepaymentGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			modulePath : 'pkp-sign',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			title : sign.applicationSignRfc
					.i18n('pkp.sign.applicationSignRfc.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});
// 上传到达联文件的panel
Ext.define('Foss.applicationSignRfc.Grid.FileUploadArriveSheetGrid', {
			extend : 'Deppon.ux.FileUploadGrid',
			/*id : 'Foss_sign_applicationSignRfc_FileUploadArriveSheetGrid_Id',// id
			title : sign.applicationSignRfc
					.i18n('pkp.sign.applicationSignRfc.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			delNewUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'*/
			modulePath : 'pkp-sign',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			title : sign.applicationSignRfc
					.i18n('pkp.sign.applicationSignRfc.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
			
		});
// 上传运单签收结果文件的panel
Ext.define('Foss.applicationSignRfc.Grid.FileUploadGrid2', {
			extend : 'Deppon.ux.FileUploadGrid',
			fileTypes: ['jpg','jpeg','gif','bmp','png'],
			/*id : 'Foss_sign_applicationSignRfc_FileUploadGrid_Id2',// id
			title : sign.applicationSignRfc
					.i18n('pkp.sign.applicationSignRfc.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			delNewUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'*/
			modulePath : 'pkp-sign',
			title : sign.applicationSignRfc
					.i18n('pkp.sign.applicationSignRfc.fileUpload'), // 上传凭证附件,
			uploadUrl : ContextPath.PKP_DELIVER + '/sign/uploadFiles.action',
			// fileTypes: '*.*',
			downLoadUrl : ContextPath.PKP_DELIVER
					+ '/sign/downLoadFiles.action',
			deleteUrl : ContextPath.PKP_DELIVER + '/sign/deleteFile.action',
			imgReviewUrl : ContextPath.PKP_DELIVER + '/sign/reviewImg.action'
		});

Ext.define('Foss.sign.applicationSignRfc.applicationSignRfcPanel', {
	extend : 'Ext.tab.Panel',
	cls : 'innerTabPanel',
	activeTab : 0,
	dedicatedPanel: null,
	getDedicatedPanel: function(){
		if(Ext.isEmpty(this.dedicatedPanel)){
			this.dedicatedPanel = Ext.create(
				'Foss.sign.applicationSignRfc.DedicatedPanel', {
				tabConfig : {
					width : 120
				},
				width : 1024,
				title : sign.applicationSignRfc
				.i18n('pkp.sign.applicationSignRfc.dedicated')
				// 专线
			});
			sign.applicationSignRfc.DedicatedPanel = this.dedicatedPanel;
		}
		return this.dedicatedPanel;
	},
	airliftPartialLineForm: null,
	getAirliftPartialLineForm: function(){
		if(Ext.isEmpty(this.airliftPartialLineForm)){
			this.airliftPartialLineForm = Ext.create(
				'Foss.sign.applicationSignRfc.AirliftPartialLineForm', {
				tabConfig : {
					width : 120
				},
				width : 1024,
				title : sign.applicationSignRfc
						.i18n('pkp.sign.applicationSignRfc.airliftPartialLine')
				// 空运/偏线
			});
			sign.applicationSignRfc.AirliftPartialLineForm = this.airliftPartialLineForm;
		}
		return this.airliftPartialLineForm;
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			items : [
				me.getDedicatedPanel(),
				me.getAirliftPartialLineForm()
			]
		});
		me.callParent(arguments);
	}
});

Ext.onReady(function() {
			Ext.QuickTips.init();
			var panel = Ext.create("Foss.sign.applicationSignRfc.applicationSignRfcPanel");
			Ext.create('Ext.panel.Panel', {
				id : 'T_sign-applicationSignRfcIndex_content',
				cls : "panelContentNToolbar",
				bodyCls : 'panelContentNToolbar-body',
				getPanel : function() {
					return panel;
				},
				items : [panel],
				renderTo : 'T_sign-applicationSignRfcIndex-body'
			});
		});
