scheduling.temprentalmark.QUERY_BY_BILL='QBB';//按单号查询
scheduling.temprentalmark.QUERY_BY_DATE='QBD';//按日期查询
var queryType;//查询类型
var orgCode;//部门编码
var departmentSignle;//部门标识
var dataList = new Array();//选中的记录
var dataListWaybill= new Array();//选中的运单数据
var dataListExpressWaybill=new Array();//选中的快递运单数据(269701-lln)
var dataListHandoverbill= new Array();//选中的交接单数据
var dataListDeliverbill= new Array();//选中的派送单数据
var dataListStowageBill= new Array();//选中的配载单
var dataListExpressHandoverbill= new Array(); //选中的快递交接单单号   313352 gouyangyang
var dataListAirPortbill= new Array(); // 选中快递机场扫单号
var smallTcketValidator;//标注小票单号的正确状态，提交校验时用
var isRepeateMark='N';//是否多次标记
var dateList = new Array();//存放单据时间
var smallTicketNumVisible;//小票单号是否可见
var exportType='QBB';//判断是在哪个页签上，是在按单号查询页签还是按日期查询页签。先默认为按单号
var exportMakeType='QBD';// 按日期导出EXCEL标示  gouyangyang  313352
var type=1;   //1为零担的    2为快递的。

/**
 *检查提交的表单合法性
 ***/
function checkForm(window){
	for(i=0;i<window.items.length;i++){
		if(!window.items.items[i].getForm().isValid()){
			return false;
		}
	}
	return true;
}
/**
 *去出字符串前后空格
 **/
trim=function() {

	return this.replace(/(^\s*)|(\s*$)/g,'');
}
/**
 *分类出选中数据中的单号
 **/
function billNoListForParmar(list){
	var arry = new Array();
	if(list.length>0){
		for(i=0;i<list.length;i++){
			if (list[i].get('billNo') == 'undefined') {
				arry[i]=list[i].get('handoverBillNo');
			} else {
				arry[i]=list[i].get('billNo');
			}
			
		}
	}
	return arry;
}
/**
 *判断选中的数据有哪些单据类型
 **/
function dataListSplit(arry){
	//每次分组前先清空数组
	dataListWaybill=[];//零担运单
	dataListHandoverbill=[];//交接单
	dataListDeliverbill=[];//派送单
	dataListStowageBill=[];//配载单
	dataListExpressWaybill=[]; //快递运单
	dataListExpressHandoverbill= [];// 快递交接单     313352 gouyangyang
	dataListAirPortbill= [];// 快递机场扫描单号
	for(i=0;i<arry.length;i++){
		if(arry[i].get('billType')=='waybill'){//零担运单
			dataListWaybill[i]=arry[i];
		}
		if(arry[i].get('billType')=='handoverbill'){//交接单
			dataListHandoverbill[i]=arry[i]
		}
		if(arry[i].get('billType')=='deliverbill'){//派送单
			dataListDeliverbill[i]=arry[i];
		}
		if(arry[i].get('billType')=='stowagebill'){//配载单
			dataListStowageBill[i]=arry[i];
		}
		//269701--lln--2015-09-01 begin
		if(arry[i].get('billType')=='expresswaybill'){//快递运单
			dataListExpressWaybill[i]=arry[i];
		}
		//269701--lln--2015-09-01 end
		if (arry[i].get('billType')=='expresshandoverbill') {  // 快递交接单
			dataListExpressHandoverbill[i] = arry[i];
		}
		if (arry[i].get('billType')=='airPortBill') {  // 快递机场扫描单
			dataListAirPortbill[i] = arry[i];
		}
	}
	dataListWaybill=arraySplice(dataListWaybill);
	dataListHandoverbill=arraySplice(dataListHandoverbill);
	dataListDeliverbill=arraySplice(dataListDeliverbill);
	dataListStowageBill=arraySplice(dataListStowageBill);
	dataListExpressWaybill=arraySplice(dataListExpressWaybill);  //快递运单--去除快递运单中的空元素  313352   gouyangyang
	dataListExpressHandoverbill=arraySplice(dataListExpressHandoverbill);  //快递交接单--去除快递交接单中的空元素  313352   gouyangyang
	dataListAirPortbill=arraySplice(dataListAirPortbill);  // 快递机场扫描单
}

/**
 * 去除数组中的空元素
 */
function arraySplice(arrayList){
	if(arrayList.length!=0){
		for(i=0;i<arrayList.length;i++){
			if(arrayList[i]==null||arrayList[i]==''){

				arrayList.splice(i,1);
				i--;
			}
		}
	}
	return arrayList;
}
/**
 *比较最晚时间
 **/
function maxDate(dateList){
	var temp ;
	for(i=0;i<dateList.length;i++){

		if(Date.parse(dateList[i])>Date.parse(dateList[i+1])){
			temp=dateList[i+1];
			dateList[i+1]=dateList[i];
			dateList[i]=temp;
		}
	}
	return dateList[dateList.length-1];

}

/**
 * 去掉相同的出发部门或到达部门
 */
function uniqueArray(data){
	data = data || [];
	var a = {};
	for (var i=0; i<data.length; i++) {
		var v = data[i];
		if (typeof(a[v]) == 'undefined'){
			a[v] = 1;
		}
	};
	data.length=0;
	for (var i in a){
		data[data.length] = i;
	}
	return data;
}


/**
 *  根据交接单获取单据生成日期并比较最晚时间
 **/
function maxCreateDate(dataList){
	var maxDate = dataList[0].data.billCreateTime;
	for(i=0;i<dataList.length;i++){
		if(Date.parse(dataList[i].data.billCreateTime)> maxDate){
			maxDate = Date.parse(dataList[i].data.billCreateTime);
		}
	}
	var date = new Date(maxDate);
	return Ext.Date.format(date,'Y-m-d H:i:s');
}

/**
 *  根据交接单获取单据生成日期并比较最晚时间
 **/
function maxCreateDateExpress(dataList){
	var maxDate = dataList[0].data.createTime;
	for(i=0;i<dataList.length;i++){
		if(Date.parse(dataList[i].data.createTime)> maxDate){
			maxDate = Date.parse(dataList[i].data.createTime);
		}
	}
	var date = new Date(maxDate);
	return Ext.Date.format(date,'Y-m-d H:i:s');
}

/**
 *日期格式化
 **/

function dateFormat(date){
	return Ext.Date.format(new Date(date.getFullYear(),date.getMonth(),date.getDate()),'Y-m-d H:i:s');
}
/**
 *检查输入的单号是否超过30个
 */
function validatorBillNo(str){
	var arry = new Array();
	arry = str.split(",");
	if(arry.length>30){
		return scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.maxNumber");//输入个数超过30个
	}
	return true;

};
/**
 * 解析字符串，并返回
 *
 **/
function billNoList(str){
	var arry = new Array();
	arry = str.split(",");
	if(arry.length>30){
		return false;
	}
	for(i=0;i<arry.length;i++){
		arry[i]=arry[i].trim();
	}
	return arry;

};

/**
 *判断选中的数据中的车牌号是否一样
 **/
function isVehicleNoSame(vehicleNoList){
	var len = vehicleNoList.length;
	if(len==1){
		return true
	}else{
		for(i=1;i<len;i++){
			if(vehicleNoList[0]!=vehicleNoList[i]){

				return false;
			}
		}
	}
	return true;

}
/**
 *判断小票单号是否要隐藏的action
 **/

smallTicketValidate=function(billList){
	var billNoList= new Array();
	smallTicketNumVisible=false
	for(i=0;i<billList.length;i++){
		billNoList[i]=billList[i].get('billNo');
	}

	Ext.Ajax.request({
		url : scheduling.realPath('smallTicketValidate.action'),
		params : {
			'rentalMarkVo.rentalMarkDto.handoverBillNoList':billNoList
		},
		success : function(response) {
			var result = Ext.decode(response.responseText);
			smallTicketNumVisible=result.rentalMarkVo.rentalMarkDto.smallTicketVisbile;
			scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
			scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
			Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
			if(!smallTicketNumVisible){
				scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').reset();
			}
		},
		exception:function (response){
			var result = Ext.decode(response.responseText);
			Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), result.message);//('提示','小票单号查询失败')

		}
	});
}


/**
 *判断小票单号是否隐藏 ,所需参数为“租车用途”
 */

function isSmallTicketVisible(rentalUseRecord){

	smallTicketNumVisible=false//小票单号是隐藏标记
	scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);//每次先把小票单号设为隐藏
	scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);//每次小票单号隐藏时设为非必输
	Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
	if(rentalUseRecord=='ZH'){
		smallTicketNumVisible=false;
		scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
		scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
		Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);

		return;
	}
	if (dataListExpressWaybill.length!=0) {
		if(dataListExpressWaybill.length!=0){//如果是运单
			if(rentalUseRecord=='SH'){//租车用途为“送货”时,并且提货方式为自提时
				for(i=0;i<dataListExpressWaybill.length;i++){
					//自提方式'SELF_PICKUP','AIRPORT_PICKUP','SELF_PICKUP_AIR','INNER_PICKUP','SELF_PICKUP_FREE_AIR'
					if(dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListExpressWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
						||dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListExpressWaybill[i].get('pickUpWay')=='INNER_PICKUP'
						||dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}else if(rentalUseRecord=='JH'&&smallTicketNumVisible==false){//租车用途为“接货”时,并且为非上门接货时
				//租车用途为“接货”时
				for(i=0;i<dataListExpressWaybill.length;i++){
					if(dataListExpressWaybill[i].get('isDoorDeliver')=='N'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}else if(rentalUseRecord=='JSH'&&smallTicketNumVisible==false){
				for(i=0;i<dataListExpressWaybill.length;i++){
					if(dataListExpressWaybill[i].get('isDoorDeliver')=='N'||dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListExpressWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
						||dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListExpressWaybill[i].get('pickUpWay')=='INNER_PICKUP'
						||dataListExpressWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}

			if(!smallTicketNumVisible){
				scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').reset();
			}
		}
		Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible)
	} else {
		if(dataListWaybill.length!=0){//如果有运单
			if(rentalUseRecord=='SH'){//租车用途为“送货”时,并且提货方式为自提时
				for(i=0;i<dataListWaybill.length;i++){
					//自提方式'SELF_PICKUP','AIRPORT_PICKUP','SELF_PICKUP_AIR','INNER_PICKUP','SELF_PICKUP_FREE_AIR'
					if(dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
						||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListWaybill[i].get('pickUpWay')=='INNER_PICKUP'
						||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}else if(rentalUseRecord=='JH'&&smallTicketNumVisible==false){//租车用途为“接货”时,并且为非上门接货时
				//租车用途为“接货”时
				for(i=0;i<dataListWaybill.length;i++){
					if(dataListWaybill[i].get('isDoorDeliver')=='N'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}else if(rentalUseRecord=='JSH'&&smallTicketNumVisible==false){
				for(i=0;i<dataListWaybill.length;i++){
					if(dataListWaybill[i].get('isDoorDeliver')=='N'||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
						||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListWaybill[i].get('pickUpWay')=='INNER_PICKUP'
						||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){
						smallTicketNumVisible=true;
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(smallTicketNumVisible);
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').allowBlank=(!smallTicketNumVisible);
						Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible);
						return;
					}
				}
			}

			if(!smallTicketNumVisible){
				scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').reset();
			}
		}
		Ext.getCmp('ticketButton').setVisible(smallTicketNumVisible)
		if(smallTicketNumVisible==false&&dataListDeliverbill.length!=0){//如有派送单，并且“小票单号”是隐藏状态时
			smallTicketValidate(dataListDeliverbill);
		}
	}
}

/**放入需要小票的运单号**/
function wayBillForSmallTicketValidate(dataListWaybill,rentalUse){
	var wayBillNoForSmallTicket=new Array();//需要小票的运单
	if(dataListWaybill.length!=0){//如果有运单
		if(rentalUse=='SH'){//租车用途为“送货”时,并且提货方式为自提时
			for(i=0;i<dataListWaybill.length;i++){
				//自提方式'SELF_PICKUP','AIRPORT_PICKUP','SELF_PICKUP_AIR','INNER_PICKUP','SELF_PICKUP_FREE_AIR'
				if(dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
					||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListWaybill[i].get('pickUpWay')=='INNER_PICKUP'
					||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){
					wayBillNoForSmallTicket[i]=dataListWaybill[i];
				}
			}
		}else if(rentalUse=='JH'){//租车用途为“接货”时,并且为非上门接货时
			//租车用途为“接货”时
			for(i=0;i<dataListWaybill.length;i++){
				if(dataListWaybill[i].get('isDoorDeliver')=='N'){
					wayBillNoForSmallTicket[i]=dataListWaybill[i];
				}
			}
		}else if(rentalUse=='JSH'){
			for(i=0;i<dataListWaybill.length;i++){
				if(dataListWaybill[i].get('isDoorDeliver')=='N'||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP'|| dataListWaybill[i].get('pickUpWay')=='AIRPORT_PICKUP'
					||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_AIR'||dataListWaybill[i].get('pickUpWay')=='INNER_PICKUP'
					||dataListWaybill[i].get('pickUpWay')=='SELF_PICKUP_FREE_AIR'){

					wayBillNoForSmallTicket[i]=dataListWaybill[i];
				}
			}
		}
	}
	return wayBillNoForSmallTicket;
}

function noRepeatMark(dataListAll){

	if(dataListAll.length>0){//交接单、配载单和派送单不能多次标记
		for(i=0;i<dataListAll.length;i++){
			if(dataListAll[i].get('rentalNum')!=null&&dataListAll[i].get('rentalNum')!=''){
				return 'Y';
			}
		}
		var noList=new Array() ;//存放单号
		var origOrgCodeList = new Array();
		var destOrgCodeList = new Array();
		for(i=0;i<dataListAll.length;i++){
			noList[i] = dataListAll[i].get('billNo');
			origOrgCodeList.push(dataListAll[i].get('origOrgCode'));
			destOrgCodeList.push(dataListAll[i].get('destOrgCode'));
		}
		var isrepeatMark1;
		var kmsNum=0;
		Ext.Ajax.request({
			url : scheduling.realPath('handOverBillRepeatMark.action'),
			async :  false,
			params : {
				'rentalMarkVo.rentalMarkDto.handoverBillNoList':noList,
				'rentalMarkVo.origOrgCodeList':origOrgCodeList,
				'rentalMarkVo.destOrgCodeList':destOrgCodeList
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				isrepeatMark1=result.rentalMarkVo.isrepeatMark;

				kmsNum=result.rentalMarkVo.kmsNum;
				Ext.getCmp('temprentalmarkKmsNum').setValue(kmsNum);
			},
			exception:function (response){

				Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), "租车标记多次标记校验异常");//('提示','小票单号查询失败')
				return;
			}
		});
		return isrepeatMark1;
	}else{
		return 'N';
	}
}

//判断交接单不能被标记多次
function noRepeatMarkExpress(dataListAll ){

	if(dataListAll.length>0){//快递交接单不能多次标记
		//如果租车编号不等于空
		for(i=0;i<dataListAll.length;i++){
			if(dataListAll[i].get('rentalNum')!=null&&dataListAll[i].get('rentalNum')!=''){
				return 'Y';
			}
		}
		var noList=new Array() ;//存放单号
		var origOrgCodeList = new Array();
		var destOrgCodeList = new Array();
		for(i=0;i<dataListAll.length;i++){
			noList[i] = dataListAll[i].get('handoverBillNo');
			origOrgCodeList.push(dataListAll[i].get('departOrgCode'));
			destOrgCodeList.push(dataListAll[i].get('arriveOrgCode'));
		}
		var isrepeatMark1;
		var kmsNum=0;
		Ext.Ajax.request({
			url : scheduling.realPath('handOverBillRepeatMark.action'),
			async :  false,
			params : {
				'rentalMarkVo.rentalMarkDto.handoverBillNoList':noList,
				'rentalMarkVo.origOrgCodeList':origOrgCodeList,
				'rentalMarkVo.destOrgCodeList':destOrgCodeList
			},
			success : function(response) {
				var result = Ext.decode(response.responseText);
				isrepeatMark1=result.rentalMarkVo.isrepeatMark;

				kmsNum=result.rentalMarkVo.kmsNum;
				Ext.getCmp('temprentalmarkKmsNum').setValue(kmsNum);
			},
			exception:function (response){

				Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), "租车标记多次标记校验异常");//('提示','小票单号查询失败')
				return;
			}
		});
		return isrepeatMark1;
	}else{
		return 'N';
	}
}

/**
 *用于数据导出用  零担
 ***/
function exportData(form,exportType){

	var queryParams = form.getValues();
	var params;
	if(!Ext.fly('downloadAttachFileForm')){
		var frm = document.createElement('form');
		frm.id = 'downloadAttachFileForm';
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}
	if(scheduling.temprentalmark.departmentSignle!=null){
		orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
		departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
	}
	if(exportType == 'QBD'){
		params = {
			'rentalMarkVo.rentalMarkDto.billGenerationBeginTime' : Ext.Date.parse(queryParams.billGenerationBeginTime,'Y-m-d H:i:s'),//单据生成开始时间
			'rentalMarkVo.rentalMarkDto.billGenerationEndTime' : Ext.Date.parse(queryParams.billGenerationEndTime,'Y-m-d H:i:s'),//单据生成结束时间
			'rentalMarkVo.rentalMarkDto.vehicleNo' : queryParams.vehicleNo,//车牌号
			'rentalMarkVo.rentalMarkDto.borrowNo' : queryParams.borrowNo,//租车编号
			'rentalMarkVo.rentalMarkDto.isWayBill' : queryParams.isWayBill,//是否运单
			'rentalMarkVo.rentalMarkDto.isHandoverBill' : queryParams.isHandoverBill,//是否交接单
			'rentalMarkVo.rentalMarkDto.isDeliverBill' : queryParams.isDeliverBill,//是否派送单
			'rentalMarkVo.rentalMarkDto.isStowageBill' : queryParams.isStowageBill,//是否配载单
			'rentalMarkVo.rentalMarkDto.isExpressWayBill' : queryParams.isExpressWayBill,//是否快递运单  313352 gouyangyang
			'rentalMarkVo.rentalMarkDto.isHandoverEirBill' : queryParams.isHandoverEirBill,//是否快递交接单  313352 gouyangyang
			'rentalMarkVo.rentalMarkDto.isAirPortBillList' : queryParams.isAirPortBillList,//是否快递机场扫描单据  313352 gouyangyang
			'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
			'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
			'rentalMarkVo.rentalMarkDto.queryType' : exportType,//查询类型
			'rentalMarkVo.rentalMarkDto.createDept':queryParams.createDept,//单据创建部门
			'rentalMarkVo.rentalMarkDto.type': 1  //判断零担还是快递的     type=1为零担    2为快递
			
		}
	}else if(exportType=='QBB'){
		var queryParams = queryByBillNumberForm.getForm().getValues();
		//269701--lln--2015-08-31 begin
		var breakbulkWaybillNoList = billNoList(queryParams.breakbulkWaybillNoList);//零担运单号
		var expressWaybillNoList = billNoList(queryParams.expressWaybillNoList);//快递运单号
		//269701--lln--2015-08-31 end
		var handoverBillNoList = billNoList(queryParams.handoverBillNoList);
		var deliverbillNoList = billNoList(queryParams.deliverbillNoList);
		var stowageBillNoList = billNoList(queryParams.stowageBillNoList);
		var expressBillNoList = billNoList(queryParams.expressBillNoList);  // 快递交接单   313352
		var expressAirportNoList = billNoList(queryParams.expressAirportNoList);  // 快递机场扫描单据
		params = {
			'rentalMarkVo.rentalMarkDto.wayBillNoList' : breakbulkWaybillNoList,//零担运单号
			'rentalMarkVo.rentalMarkDto.expressWaybillNoList' : expressWaybillNoList,//快递运单号
			'rentalMarkVo.rentalMarkDto.handoverBillNoList' : handoverBillNoList,//交接单号
			'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList,//派送单号
			'rentalMarkVo.rentalMarkDto.stowageBillNoList':stowageBillNoList,//配载单号
			'rentalMarkVo.rentalMarkDto.expressBillNoList':expressBillNoList,//快递交接单  // gouyangyang  313352
			'rentalMarkVo.rentalMarkDto.expressAirportNoList':expressAirportNoList,//快递机场扫描单据 
			'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
			'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
			'rentalMarkVo.rentalMarkDto.queryType' : exportType,//查询类型
			'rentalMarkVo.rentalMarkDto.type': 1  //判断零担还是快递的     type=1为零担    2为快递
		}
	}
	Ext.Ajax.request({
		url:scheduling.realPath('exportTempRentalExcel.action'),
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : params,
		isUpload: true,
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('导出失败',result.message);
		}
	});


}




/**
 *用于数据导出用 快递313352
 ***/
function exportMakeData(form,exportMakeType){

	var queryParams = form.getValues();
	var params;
	if(!Ext.fly('downloadAttachFileForm')){
		var frm = document.createElement('form');
		frm.id = 'downloadAttachFileForm';
		frm.style.display = 'none';
		document.body.appendChild(frm);
	}
	if(scheduling.temprentalmark.departmentSignle!=null){
		orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
		departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
	}
	if(exportMakeType == 'QBD'){
		params = {
			'rentalMarkVo.rentalMarkDto.billGenerationBeginTime' : Ext.Date.parse(queryParams.billGenerationBeginTime,'Y-m-d H:i:s'),//单据生成开始时间
			'rentalMarkVo.rentalMarkDto.billGenerationEndTime' : Ext.Date.parse(queryParams.billGenerationEndTime,'Y-m-d H:i:s'),//单据生成结束时间
			'rentalMarkVo.rentalMarkDto.vehicleNo' : queryParams.vehicleNo,//车牌号
			'rentalMarkVo.rentalMarkDto.borrowNo' : queryParams.borrowNo,//租车编号
			'rentalMarkVo.rentalMarkDto.isWayBill' : queryParams.isWayBill,//是否运单
			'rentalMarkVo.rentalMarkDto.isHandoverBill' : queryParams.isHandoverBill,//是否交接单
			'rentalMarkVo.rentalMarkDto.isDeliverBill' : queryParams.isDeliverBill,//是否派送单
			'rentalMarkVo.rentalMarkDto.isStowageBill' : queryParams.isStowageBill,//是否配载单
			'rentalMarkVo.rentalMarkDto.isExpressWayBill' : queryParams.isExpressWayBill,//是否快递运单  313352 gouyangyang
			'rentalMarkVo.rentalMarkDto.isHandoverEirBill' : queryParams.isHandoverEirBill,//是否快递交接单  313352 gouyangyang
			'rentalMarkVo.rentalMarkDto.isAirPortBillList' : queryParams.isAirPortBillList,//是否快递机场扫描单据
			'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
			'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
			'rentalMarkVo.rentalMarkDto.queryType' : exportMakeType,//查询类型
			'rentalMarkVo.rentalMarkDto.createDept':queryParams.createDept,//单据创建部门
			'rentalMarkVo.rentalMarkDto.type': 2  //判断零担还是快递的     type=1为零担    2为快递
		
		}
	}else if(exportMakeType=='QBB'){
		var queryParams = queryByBillNumberForm.getForm().getValues();
		//269701--lln--2015-08-31 begin
		var breakbulkWaybillNoList = billNoList(queryParams.breakbulkWaybillNoList);//零担运单号
		var expressWaybillNoList = billNoList(queryParams.expressWaybillNoList);//快递运单号
		//269701--lln--2015-08-31 end
		var handoverBillNoList = billNoList(queryParams.handoverBillNoList);
		var deliverbillNoList = billNoList(queryParams.deliverbillNoList);
		var stowageBillNoList = billNoList(queryParams.stowageBillNoList);
		var expressBillNoList = billNoList(queryParams.expressBillNoList);  // 快递交接单   313352
		var expressAirportNoList = billNoList(queryParams.expressAirportNoList);  // 快递机场扫描单
		params = {
			'rentalMarkVo.rentalMarkDto.wayBillNoList' : breakbulkWaybillNoList,//零担运单号
			'rentalMarkVo.rentalMarkDto.expressWaybillNoList' : expressWaybillNoList,//快递运单号
			'rentalMarkVo.rentalMarkDto.handoverBillNoList' : handoverBillNoList,//交接单号
			'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList,//派送单号
			'rentalMarkVo.rentalMarkDto.stowageBillNoList':stowageBillNoList,//配载单号
			'rentalMarkVo.rentalMarkDto.expressBillNoList':expressBillNoList,//快递交接单  // gouyangyang  313352
			'rentalMarkVo.rentalMarkDto.expressAirportNoList':expressAirportNoList,//快递机场扫描单据
			'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
			'rentalMarkVo.rentalMarkDto.orgCode': orgCode,//部门编码
			'rentalMarkVo.rentalMarkDto.queryType': exportMakeType,//查询类型
			'rentalMarkVo.rentalMarkDto.type': 2  //判断零担还是快递的     type=1为零担    2为快递
		}
	}
	Ext.Ajax.request({
		url:scheduling.realPath('exportTempRentalMarkExcel.action'),
		form: Ext.fly('downloadAttachFileForm'),
		method : 'POST',
		params : params,
		isUpload: true,
		exception : function(response) {
			var result = Ext.decode(response.responseText);
			top.Ext.MessageBox.alert('导出失败',result.message);
		}
	});
}

//租车信息model
Ext.define('Foss.scheduling.tempmarkModel',{
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'vehicleNo',//车牌号
		type : 'string'
	},{
		name : 'rentalAmount',//租车金额
		type : 'number'
	},{
		name : 'kmsNum',//公里数
		type : 'String'
	},{
		name : 'departureName',//出发部门
		type : 'string'
	},{
		name : 'departureCode',//出发部门编码
		type : 'string'
	},{
		name : 'destinationCode',//出发部门编码
		type : 'string'
	},{
		name : 'destinationName',//到达部门
		type : 'string'
	}]
});
//租车用途store1
Ext.define('Foss.scheduling.RentalUseStore1', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '',
			name : ''
		}, {
			code : 'JH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.receive')//接货
		}, {
			code : 'SH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.send')//送货
		}, {
			code : 'ZH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.transfer')//转货
		},{
			code : 'JSH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.deliver')//接送货
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});
rentalUseStore1=Ext.create('Foss.scheduling.RentalUseStore1');
//租车用途store2
Ext.define('Foss.scheduling.RentalUseStore2', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '',
			name : ''
		}, {
			code : 'JH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.receive')//接货
		}, {
			code : 'SH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.send')//送货
		}, {
			code : 'JSH',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.deliver')//接送货
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});
rentalUseStore2=Ext.create('Foss.scheduling.RentalUseStore2');
//用车原因store
Ext.define('Foss.scheduling.CarReasonStore', {
	extend : 'Ext.data.Store',
	fields : [{
		name : 'code',
		type : 'string'
	}, {
		name : 'name',
		type : 'string'
	}],
	data : {
		'items' : [{
			code : '',
			name : ''
		}, {
			code : 'shortHanded',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.shortHanded')
		}, {
			code : 'specialGoods',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.specialGoods')
		}, {
			code : 'exhibition',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.exhibition')
		},{
			code : 'warehouseEntry',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.warehouseEntry')
		}, {
			code : 'lackVehiclesS',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.lackVehiclesS')
		}, {
			code : 'limitLine',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.limitLine')
		}, {
			code : 'customerReason',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.customerReason')
		},{
			code : 'longDelivery',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.longDelivery')
		}, {
			code : 'externalCauses',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.externalCauses')
		},{
			code : 'others',
			name : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.others')
		}]
	},
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

/**
 * 定义查询的列模型  零担
 */
Ext.define('Foss.scheduling.temprentalmark.QueryTemprentalMarkModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'rentalNum',//租车编号
		type : 'string'
	},{
		name : 'rentalAmount',//租车金额
		type : 'string'
	},{
		name : 'singleSum',//单票金额
		type : 'string'
	},{
		name : 'billNo',//单号
		type : 'string'
	},{
		name : 'billType',//单据类型
		type : 'string'
	},{
		name : 'vehicleNo',//车牌号
		type : 'string'
	},{
		name : 'consultPriceNo',//询价编号
		type : 'string'
	},{
		name : 'inviteVehicleNo',//约车编号
		type : 'string'
	},{
		name : 'driverName',//司机
		type : 'string'
	},{
		name : 'origOrgName',//出发部门
		type : 'string'
	},{
		name : 'destOrgName',//到达部门
		type : 'string'
	},{
		name : 'origOrgCode',//出发部门编码
		type : 'string'
	},{
		name : 'destOrgCode',//到达部门编码
		type : 'string'
	},{
		name : 'weight',//重量
		type : 'number'
	},{
		name : 'volume',//体积
		type : 'number'
	},{
		name : 'goodsName',//货物名称
		type : 'string'
	},{
		name : 'packing',//包装
		type : 'string'
	},{
		name : 'isDoorDeliver',//是否上门接货
		type : 'string'
	},{
		name : 'customerName',//发货客户名称
		type : 'string'
	},{
		name : 'sendAddress',//发货地址
		type : 'string'
	},{
		name : 'destination',//目的站
		type : 'string'
	},{
		name : 'pickUpWay',//提货方式
		type : 'string'
	},{
		name : 'pickUpWayName',//提货方式名称
		type : 'string'
	},{
		name : 'receiptContacts',//收货联系人
		type : 'string'
	},{
		name : 'receiptAddress',//收货地址
		type : 'string'
	},{
		name : 'billCreateTime',//单据生成时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'rentalUse',//租车用途
		type : 'string'
	},{
		name : 'createDate',//租车标记时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'markDeptName',//租车标记部门
		type : 'string'
	},{
		name : 'markDeptCode',//租车标记部门编码
		type : 'string'
	},{
		name : 'markOperator',//租车标记操作人
		type : 'string'
	},{//269701--lln--2015-09-01 begin
		name : 'isExpress',//是否快递
		type : 'string'
		//269701--lln--2015-09-01 end
	}]
});


/**
 * 查询store 零担
 * */
Ext.define('Foss.scheduling.QueryTemprentalMarkStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.scheduling.temprentalmark.QueryTemprentalMarkModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		timeout:300000,
		// 请求的url
		url :scheduling.realPath('temprentalmarkQuery.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'rentalMarkVo.rentalMarkEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){

			if(scheduling.temprentalmark.departmentSignle!=null){
				orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
				departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
			}
			if(queryType == scheduling.temprentalmark.QUERY_BY_DATE){
				var queryParams = queryByDateForm.getForm().getValues();
				Ext.apply(operation, {
					params : {
						'rentalMarkVo.rentalMarkDto.billGenerationBeginTime' : Ext.Date.parse(queryParams.billGenerationBeginTime,'Y-m-d H:i:s'),//单据生成开始时间
						'rentalMarkVo.rentalMarkDto.billGenerationEndTime' : Ext.Date.parse(queryParams.billGenerationEndTime,'Y-m-d H:i:s'),//单据生成结束时间
						'rentalMarkVo.rentalMarkDto.vehicleNo' : queryParams.vehicleNo,//车牌号
						'rentalMarkVo.rentalMarkDto.borrowNo' : queryParams.borrowNo,//租车编号
						'rentalMarkVo.rentalMarkDto.isWayBill' : queryParams.isWayBill,//是否零担运单
						'rentalMarkVo.rentalMarkDto.isHandoverBill' : queryParams.isHandoverBill,//是否交接单
						'rentalMarkVo.rentalMarkDto.isDeliverBill' : queryParams.isDeliverBill,//是否派送单
						'rentalMarkVo.rentalMarkDto.isStowageBill' : queryParams.isStowageBill,//是否配载单
						'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
						'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
						'rentalMarkVo.rentalMarkDto.queryType' : queryType,//查询类型
						'rentalMarkVo.rentalMarkDto.createDept':queryParams.createDept,//单据创建部门
						'rentalMarkVo.rentalMarkDto.isExpressWayBill' : queryParams.isExpressWayBill,//是否快递运单
						'rentalMarkVo.rentalMarkDto.isHandoverEirBill': queryParams.isHandoverEirBill,//是否快递交接单    313352   gouyangyang
						'rentalMarkVo.rentalMarkDto.isAirPortBillList': queryParams.isAirPortBillList,//是否快递航空单    313352   gouyangyang
						'rentalMarkVo.rentalMarkDto.type': 1  //判断零担还是快递的     type=1为零担    2为快递
					}
				});
			}else if(queryType==scheduling.temprentalmark.QUERY_BY_BILL){
				var queryParams = queryByBillNumberForm.getForm().getValues();
				//269701--lln--2015-08-31 begin
				//零担运单号
				var breakbulkWaybillNoList = billNoList(queryParams.breakbulkWaybillNoList);
				//快递运单号
				var expressWaybillNoList = billNoList(queryParams.expressWaybillNoList);
				//269701--lln--2015-08-31 end
				//交接单号
				var handoverBillNoList = billNoList(queryParams.handoverBillNoList);
				//配载单号
				var deliverbillNoList = billNoList(queryParams.deliverbillNoList);
				//派送单号
				var stowageBillNoList = billNoList(queryParams.stowageBillNoList);
				//快递交接单  313352
				var expressBillNoList = billNoList(queryParams.expressBillNoList);
				//快递机场扫描  313352
				var expressAirportNoList = billNoList(queryParams.expressAirportNoList);
				Ext.apply(operation, {
					params : {
						//269701--lln--2015-08-31 begin
						'rentalMarkVo.rentalMarkDto.wayBillNoList' : breakbulkWaybillNoList,//零担运单号
						'rentalMarkVo.rentalMarkDto.expressWaybillNoList' : expressWaybillNoList,//快递运单号
						//269701--lln--2015-08-31 end
						'rentalMarkVo.rentalMarkDto.handoverBillNoList' : handoverBillNoList,//交接单号
						'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList,//派送单号
						'rentalMarkVo.rentalMarkDto.stowageBillNoList':stowageBillNoList,//配载单号
						'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
						'rentalMarkVo.rentalMarkDto.expressBillNoList' :expressBillNoList,//快递交接单 313352 gyy
						'rentalMarkVo.rentalMarkDto.expressAirportNoList' :expressAirportNoList,//快递机场扫描
						'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
						'rentalMarkVo.rentalMarkDto.queryType' : queryType,//查询类型
						'rentalMarkVo.rentalMarkDto.type': 1  //判断零担还是快递的     type=1为零担    2为快递
					}
				});
			}
		},
		'datachanged' : function(store){
			Ext.getCmp('gridTotalCount').setValue(store.getTotalCount());
		}
	}
});

/**
 * 租车标记表单1
 */
Ext.define('Foss.temprentalmark.form.temprentalMark1',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:true,
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:80
	},
	items : [{
		xtype:'combobox',
		editable : false,
		autoWidth:true,
		name: 'rentalUse',
		//store: Ext.create('Foss.scheduling.RentalUseStore'),
		displayField : 'name',
		maxLength : 20,
		id:'rentalUse',
		columnWidth : .33,
		valueField : 'code',
		editable: false,
		allowBlank: false,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalUse'),//租车用途
		listeners:{
			'select':function(combo, records,eOpts){
				Ext.getCmp('ticketButton').setVisible(false);
				isSmallTicketVisible(this.getValue());
				var singleSum=scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('singleSum');
				if(this.getValue() == 'JSH'){
					singleSum.show();
				}
				if(this.getValue() == 'JH'){
					singleSum.show();
				}
				if(this.getValue() == 'SH'){
					singleSum.show();
				}
				if(this.getValue() == 'ZH'){
					singleSum.show();
				}
				if(smallTicketNumVisible){
					Ext.getCmp('ticketButton').setVisible(true);
				}
			}
		}
	},{
		xtype:'datefield',

		format: 'Y-m-d H:i:s',
		altFormats: 'Y,m,d H:i:s|Y.m.d H:i:s',
		columnWidth : .33,
		autoWidth:true,
		allowBlank: false,
		maxLength : 30,
		name: 'useCareDate',
		id:'useCareDate',
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.carDate'),   //用车日期
		listeners:{
			'blur':function(cm,evo,eOpts ){
				if(cm.getRawValue()!=null&&cm.getValue()!=''){
					if(cm.getRawValue()>dateFormat(new Date())||cm.getRawValue()<maxDate(dateList)){
						Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),"用车日期不能大于当前日期，不能小于单据生成日期");//("提示","用车日期不能早于当前时间")
						this.reset();
					}
				}
			}
		}

	},{

		xtype : 'combo',
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.carReason'),//用车原因
		value : '',
		allowBlank: false,
		maxLength : 20,
		name : 'carReason',
		displayField : 'name',
		valueField : 'code',
		columnWidth : .33,
		autoWidth:true,
		queryMode : 'local',
		triggerAction : 'all',
		editable : false,
		store : Ext.create('Foss.scheduling.CarReasonStore')
	},{
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.inviteCarNum'),//约车编号
		name: 'inviteVehicleNo',
		id:'inviteVehicleNo',
		maxLength : 20,
		allowBlank: true,
		autoWidth:true,
		columnWidth:.33,
		listeners:{
			'blur':function( cmp, the, eOpts){
				var inviteVehicleNo=this.getValue();
				var rentalMarkEntityList= new Array() ;
				for(i=0;i<dataList.length;i++){
					if (dataList[i].get('billType') != 'expresshandoverbill' || dataList[i].get('billType') != 'expresswaybill') {
						rentalMarkEntityList.push(dataList[i].data);
					} 
				}
				if(inviteVehicleNo!=null&&inviteVehicleNo!=''){ 
					Ext.Ajax.request({
						url : scheduling.realPath('queryInviteVehicleNo.action'),
						jsonData : {
							'rentalMarkVo':{
								'inviteVehicleNo':inviteVehicleNo,
								'rentalMarkEntityList':rentalMarkEntityList,
							}
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var inviteVehicleNoResult=result.rentalMarkVo.inviteVehicleNo;//约车编号
							var inviteState=result.rentalMarkVo.inviteState;//约车编号状态
							var acceptPerson=result.rentalMarkVo.acceptPerson;//约车受理人
							var rentalAmount=result.rentalMarkVo.rentalAmount;//租车金额310248
							var singleSum=result.rentalMarkVo.singleSum;//单票金额332219
							//310248 去掉&&inviteState!='DISMISS'&&inviteState!='REJECT'
							if(inviteState!='RETURN'){
								cmp.setFieldStyle('color:red;');
								cmp.validator=false;
								Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), '请输入已退回的同城外租车约车编码');//("提示","约车编号错误")
								cmp.reset();
								cmp.setFieldStyle('color:black;')
							}else{
								cmp.validator=true;
							}
							if(acceptPerson!=null||acceptPerson!==''){
								Ext.getCmp('acceptPerson').setValue(result.rentalMarkVo.acceptPerson);
							}
							if(acceptPerson!=null||acceptPerson!==''){
								Ext.getCmp('acceptPersonCode').setValue(result.rentalMarkVo.acceptPersonCode);
							}
							//310248
							if(rentalAmount!=null||rentalAmount!=''){
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setReadOnly(true);
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setValue(result.rentalMarkVo.rentalAmount);
							}
							if(null==rentalAmount||rentalAmount==''){
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').applyEmptyText();
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setReadOnly(false);
							}
							//332219
							if(singleSum!=null||singleSum!==''){
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('singleSum').setValue(result.rentalMarkVo.singleSum);
							}
							if(null==singleSum||singleSum==''){
								scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('singleSum').hide();
							}
							
						},
						failure:function (response){
							Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt.invititeNoFailed"));//("提示","约车编号验证失败")
						}
					});
				}
			}
		}
	},{
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.acceptPeople'),//约车受理人
		name: 'acceptPerson',
		id: 'acceptPerson',
		allowBlank: true,
		readOnly:true,
		autoWidth:true,
		columnWidth:.66
	},{
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.acceptPeople'),//约车受理人编号
		name: 'acceptPersonCode',
		id: 'acceptPersonCode',
		allowBlank: true,
		readOnly:true,
		autoWidth:true,
		hidden:true,
		columnWidth:.00
	},{//310248
		name : 'bearFeesDept',
		xtype : 'dynamicorgcombselector',
		fieldLabel : scheduling.temprentalmark
				.i18n('foss.scheduling.temprentalmark.label.bearFeesDept'), //费用承担部门
		type : 'ORG',
		allowBlank : false,
		hidden : true,
		columnWidth : 1
	},{
		xtype:'textfield',
		allowBlank:false,
		autoWidth:true,
		name:'consultPriceNo',
		maxLength:20,
		maxLengthText:'长度太大',
		fieldLabel:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.consultPriceNo'),//询价编号
		columnWidth:.33,
		listeners:{
			'blur':function( cmp, the, eOpts){
				var consultPriceNo=this.getValue();
				if(consultPriceNo!=null&&consultPriceNo!=''){
					Ext.Ajax.request({
						url:scheduling.realPath('queryByConsultPriceNo.action'),
						params:{'consultPriceVO.consultPriceNo':consultPriceNo},
						success:function(response){
							var result = Ext.decode(response.responseText);
							if(result.consultPriceVO.consultPriceEntity!=null){
								if(result.consultPriceVO.consultPriceEntity.needVehicleDept=='此询价编号已使用过'){
									Ext.Msg.alert("提示","该询价编号已标记使用，请重新填写");
									Ext.getCmp('commitBtn').setDisabled(true);
								}else{
									cmp.up("form").getForm().findField('quotedInfo').setValue(result.consultPriceVO.consultPriceEntity.quotedInfo);
									cmp.up("form").getForm().findField('needVehicleDept').setValue(result.consultPriceVO.consultPriceEntity.needVehicleDept);
									Ext.getCmp('commitBtn').setDisabled(false);
								}
							}else{
								cmp.up("form").getForm().findField('quotedInfo').setValue('');
								cmp.up("form").getForm().findField('needVehicleDept').setValue('');
								Ext.Msg.alert("提示","此询价编号非贵部门所有或无此询价编号")
								Ext.getCmp('commitBtn').setDisabled(true);
							}
						},
						failure:function(response){
							Ext.Msg.alert('提示','询价信息验证失败');
						}
					});
				}
			}
		}
	},{
		xtype:'textfield',
		allowBlank:false,
		autoWidth:true,
		name:'quotedInfo',
		maxLength:20,
		readOnly:true,
		maxLengthText:'长度太大',
		fieldLabel:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.quotedInfo'),//报价信息
		columnWidth:.33
	},{
		xtype:'textfield',
		allowBlank:false,
		autoWidth:true,
		readOnly:true,
		name:'needVehicleDept',
		maxLength:36,
		maxLengthText:'长度太大',
		fieldLabel:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.needVehicleDept'),//'请车部门'
		columnWidth:.33
	},{
		xtype:'radiogroup',
		vertical:true,
		allowBlank:false,
		columnWidth:.25,
		name:'useVehiclePlatform',
		id:'YesOrNo',
		fieldLabel:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.useCarPlatform'),//使用请车平台
		items:[{
			xtype:'radio',
			boxLabel:'是',
			name:'useVehiclePlatform',
			inputValue:'YES'
		},{
			xtype:'radio',
			boxLabel:'否',
			name:'useVehiclePlatform',
			inputValue:'NO'
		}],
		listeners:{
			'change':function(tabPanel,newValue,oldValue,eOpts){
				if(newValue.useVehiclePlatform== "YES"){
					Ext.getCmp("salesVehiclePlatformName").setDisabled(false);
					Ext.getCmp("salesVehiclePlatformName").setValue("");

				}else{
					Ext.getCmp("salesVehiclePlatformName").setDisabled(true);
					Ext.getCmp("salesVehiclePlatformName").setValue("请输入请车平台名称");
				}
			}
		}
	},{
		disabled:true,
		readonly:true,
		fieldLabel : '请车平台名称',
		name : 'salesVehiclePlatformName',
		id : 'salesVehiclePlatformName',
		hideLabel:true,
		xtype:'textfield',
		value :'请输入请车平台名称',
		allowBlank: false,
		columnWidth:.18,
		labelWidth : 30
	},{
		xtype:'button',
		id:'ticketButton',
		text:'小票单号填入',//小票号
		width:20,
		columnWidth: .15,
		hidden:true,
		handler:function(){

			var rentalUse = Ext.getCmp('rentalUse').getValue();
			var wayBillNoList = new Array();
			var deliverbillNoList = new Array();
			if (dataListExpressWaybill.lenght > 0) {
				 wayBillNoList = billNoListForParmar(arraySplice(wayBillForSmallTicketValidate(dataListExpressWaybill,rentalUse)));
			} else {
				 wayBillNoList = billNoListForParmar(arraySplice(wayBillForSmallTicketValidate(dataListWaybill,rentalUse)));
				 deliverbillNoList = billNoListForParmar(dataListDeliverbill);
			}
			
			if(rentalUse!=null&&rentalUse!=''){
				Ext.Ajax.request({
					url : scheduling.realPath('querySmallTicketForWayBill.action'),
					params : {
						'rentalMarkVo.rentalMarkDto.rentalUse':rentalUse,//租车用途
						'rentalMarkVo.rentalMarkDto.wayBillNoList' : wayBillNoList,//运单号
						'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList//派送单号
					},
					success : function(response) {
						var result = Ext.decode(response.responseText);
						var smallTickets=result.rentalMarkVo.rentalMarkDto.smallTickets;
						var waybillnos=result.rentalMarkVo.rentalMarkDto.waybillNos
						scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setValue(smallTickets);
						if(waybillnos!=null&&waybillnos!=''){
							smallTcketValidator=false;
							Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),"运单号:"+waybillnos+"未做对应小票");//("提示","小票单号错误")
						}else{
							smallTcketValidator=true;
						}
					},
					exception:function (response){
						Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.smallTicketNumValidFailed"));//('提示','小票单号验证失败')
					}
				});
			}

		}

	},{
		xtype: 'textarea',
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.smallTicketNum'),//小票单号
		name: 'smallTicketNum',
		emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
		id:'smallTicketNum',
		autoWidth:true,
		allowBlank: true,
		columnWidth:1,
		validator:function(){
			return validatorBillNo(this.getValue());
		},
		listeners:{
			'blur':function( cmp, the, eOpts){
				var smallTicketList=arraySplice(billNoList(this.getValue()));
				var rentalUse = Ext.getCmp('rentalUse').getValue();
				var wayBillNoList = new Array();
				var deliverbillNoList = new Array();
				if (dataListExpressWaybill.length > 0) {
					wayBillNoList = arraySplice(billNoListForParmar(wayBillForSmallTicketValidate(dataListExpressWaybill,rentalUse)));
				} else {
					wayBillNoList = arraySplice(billNoListForParmar(wayBillForSmallTicketValidate(dataListWaybill,rentalUse)));
					deliverbillNoList = billNoListForParmar(dataListDeliverbill);
				}

				if(smallTicketList.length!=0&&rentalUse!=null&&rentalUse!=''){
					Ext.Ajax.request({
						url : scheduling.realPath('querysmallTicketNum.action'),
						params : {'rentalMarkVo.rentalMarkDto.smallTicketList' : smallTicketList,//小票单号
							'rentalMarkVo.rentalMarkDto.rentalUse':rentalUse,//租车用途
							'rentalMarkVo.rentalMarkDto.wayBillNoList' : wayBillNoList,//零担运单号
							'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList//派送单号
						},
						success : function(response) {
							var result = Ext.decode(response.responseText);
							var smallTickets=result.rentalMarkVo.rentalMarkDto.smallTickets;
							var waybillnos=result.rentalMarkVo.rentalMarkDto.waybillNos
							if(smallTickets!=null&&smallTickets!=''){
								smallTcketValidator=false;
								if(rentalUse=='SH'){
									Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.smallTicketNumError")+smallTickets+";必须填入对应单据的和收入类别为“自提改派送”的小票单号");//("提示","小票单号错误")
								}
								if(rentalUse=='JH'){
									Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.smallTicketNumError")+smallTickets+";必须填入对应单据的和收入类别为“上门接货费”的小票单号");//("提示","小票单号错误")
								}
								if(rentalUse=='JSH'){
									Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.smallTicketNumError")+smallTickets+";必须填入对应单据的和收入类别为“自提改派送”或“上门接货费”的小票单号");//("提示","小票单号错误")
								}
							}
							else{
								smallTcketValidator=true; //标注小票单号的正确状态，提交校验时用

							}
							if(waybillnos!=null&&waybillnos!=''){
								smallTcketValidator=false;
								Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),"运单号:"+waybillnos+"未输入对应小票");//("提示","小票单号错误")
							}
						},
						exception:function (response){
							Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.smallTicketNumValidFailed"));//('提示','小票单号验证失败')
						}
					});
				}
			}
		}
	}],
	constructor: function(config){

		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 租车标记表单
 */
Ext.define('Foss.temprentalmark.form.temprentalMark2',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:true,
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:70
	},
	items : [{
		xtype:'commonleasedvehicleselector',
		name: 'vehicleNo',
		allowBlank: false,
		autoWidth:true,
		maxLength:100,
		forceSelection: true,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.vehicleNo'),   //车牌号
		columnWidth: .25
	},{
		xtype: 'numberfield',
		name: 'rentalAmount',
		allowBlank: false,
		autoWidth:true,
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalAmount'),//租车金额
		regex:/^-?\d{1,10}\.?\d{0,2}$/,
		validator : function(value) {
			if(value != '' && value <= 0) {
				return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.exception.enterEcOneNum');
			}
			return true;
		},
		columnWidth: .25,
		listeners:{
			'blur':function( cmp, the, eOpts){
				var rentalAmount=this.getValue();
				var count = dataListWaybill.length + dataListDeliverbill.length;
				if(count > 0){
					var singleSum=scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('singleSum');
					singleSum.show();
					var money =rentalAmount/count;//单票金额332219
					singleSum.setValue(money)
				}
			}
		}
	},{
		xtype: 'textfield',
		name: 'singleSum',
		readOnly:true,
		hidden:true,
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.singleMoney'),//单票金额
		columnWidth: .25
	},{
		xtype: 'textfield',
		//decimalPrecision: 1,
		autoWidth:true,
		allowBlank: false,
		allowNegative: false,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.kmsNum'),//公里数
		name: 'kmsNum',
		id:'temprentalmarkKmsNum',
		columnWidth: .25
	},{
		xtype : 'dynamicorgcombselector',
		allowBlank: false,
		autoWidth:true,
		name: 'departure',
		id:'origOrgCode',
		columnWidth: .80,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.departure'),//出发地
		listeners:{
			'blur':function(cmp ,the,ep){
				if(Ext.getCmp("rentalUse").getValue()=='ZH'){
				}else{
					cmp.setValue(cmp.getValue());
				}
			}
		}
	},{
		xtype : 'dynamicorgcombselector',
		allowBlank: false,
		autoWidth:true,
		name: 'destination',
		id:'destOrgCode',
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destination'),//目的地
		columnWidth: .80,
		listeners:{
			'blur':function(cmp ,the,ep){
				if(Ext.getCmp("rentalUse").getValue()=='ZH'){
				}else{
					cmp.setValue(cmp.getValue());
				}
			}
		}

	},{
		xtype:'button',
		id:'multiCarTakegoods',
		text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.MultiCarTakegoods'),//多车走货
		columnWidth: .15,
		handler:function(){
			var newForm = Ext.create('Foss.temprentalmark.form.temprentalMark3');
			scheduling.temprentalmark.markWin.add(2,newForm);
			scheduling.temprentalmark.markWin.doLayout();
		}

	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 租车标记表单3
 */
Ext.define('Foss.temprentalmark.form.temprentalMark3',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:true,
	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:70
	},
	items : [{
		xtype:'commonleasedvehicleselector',
		name: 'vehicleNo',
		allowBlank: false,
		forceSelection: true,
		autoWidth:true,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.vehicleNo'),   //车牌号
		columnWidth: .25
	},{
		xtype: 'numberfield',
		name: 'rentalAmount',
		allowBlank: false,
		autoWidth:true,
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalAmount'),//金额
		regex:/^-?\d+\.?\d{0,1}$/,
		validator : function(value) {
			if(value != '' && value <= 0) {
				return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.exception.enterEcOneNum');
			}
			return true;
		},
		columnWidth: .25
	},{
		xtype: 'textfield',
		name: 'singleSum',
		readOnly:true,
		hidden:true,
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.singleMoney'),//单票金额
		columnWidth: .25
	},{
		xtype: 'textfield',
		//decimalPrecision: 1,
		autoWidth:true,
		allowBlank: false,
		allowNegative: false,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.kmsNum'),//公里数
		name: 'kmsNum',
		regex:/^-?\d+\.?\d{0,1}$/,
		regexText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.regexText'),//格式输入有误
		validator : function(value) {
			if(value != '' && value <= 0) {
				return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.exception.enterEcOneNum');
			}
			return true;
		},
		columnWidth:.25
	},{
		xtype : 'dynamicorgcombselector',
		columnWidth: .33,
		allowBlank: false,
		name: 'departure',
		autoWidth:true,
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.departure'),//出发地
		listeners:{
			'blur':function(cmp ,the,ep){
				cmp.setValue(cmp.getValue());
			}
		}
	},{
		xtype : 'dynamicorgcombselector',
		allowBlank: false,
		autoWidth:true,
		name: 'destination',
		fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destination'),//目的地
		columnWidth: .33,
		listeners:{
			'blur':function(cmp ,the,ep){
				cmp.setValue(cmp.getValue());
			}
		}

	},{
		xtype:'button',
		text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.delete'),//删除
		columnWidth: .15,
		handler:function(){
			scheduling.temprentalmark.markWin.remove(this.up('form'));
			scheduling.temprentalmark.markWin.doLayout();
		}

	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.temprentalmark.form.temprentalMark4',{
	extend: 'Ext.form.Panel',
	cls:'autoHeight',
	defaultType: 'textfield',
	layout:'column',
	columnWidth: 1,
	frame:false,

	defaults: {
		margin:'5 5 5 5',
		anchor: '98%',
		labelWidth:70
	},
	items : [{
		name: 'remark',
		xtype: 'textarea',
		allowBlank: true,
		columnWidth: 1,
		maxLength:165,
		autoWidth:true,
		height:100,
		fieldLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.textfield.remark')//备注

	},{
		xtype: 'container',
		columnWidth:1,
		buttonAlign: 'center',
		layout:'column',
		defaults: {
			margin:'5 0 5 0'
		},
		items: [{
			xtype:'button',
			text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.cancel'),//取消
			columnWidth: .25,
			handler:function(){

				scheduling.temprentalmark.markWin.hide();
			}

		},{
			border : false,
			columnWidth:.5,
			html: '&nbsp;'
		},{
			xtype:'button',
			id:'commitBtn',
			text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.submit'),//提交
			columnWidth: .25,
			handler:function(){
				var length=scheduling.temprentalmark.markWin.items.length;
				var param1 = scheduling.temprentalmark.markWin.items.items[0].getForm().getValues();
				var carReason = param1.carReason;//用车原因
				var useCareDateRawValue = scheduling.temprentalmark.markWin.items.items[0].getForm().findField('useCareDate').getRawValue();
				/**
				 *如果多车走货，或者用车原因选择其他，则备注必填
				 **/
				if(length>3||carReason=='others'){
					scheduling.temprentalmark.temprentalMarkForm4.getForm().findField('remark').allowBlank=false;
				}else {
					scheduling.temprentalmark.temprentalMarkForm4.getForm().findField('remark').allowBlank=true;
				}

				if(!smallTcketValidator&&param1.smallTicketNum!=null&&param1.smallTicketNum.trim()!=''){
					Ext.Msg.alert(scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt'),scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.pleaseValidateSmallTicketNum'));//("提示","小票单号错误，请检查")
					return;
				}
				/**
				 *多车走货，车牌号不能一样
				 **/
				if(length>3){
					var array = new Array();
					for(i=0;i<length-2;i++){
						var values = scheduling.temprentalmark.markWin.items.items[i+1].getForm().getValues();
						array[i]=values.vehicleNo;
					}
					var nary = array.sort();
					for(i=0;i<array.length;i++){
						if(nary[i]==nary[i+1]){
							Ext.MessageBox.alert(scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt'),scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt.theSamevehicleNo'));
							return;
						}
					}
				}
				if(checkForm(scheduling.temprentalmark.markWin)){
					var myMask = new Ext.LoadMask(scheduling.temprentalmark.markWin, {
						msg:scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt.pleaseWait")
					});
					myMask.show();
					var rentalUse = param1.rentalUse;//租车用途
					var useCareDate = param1.useCareDate;//用车日期
					var inviteVehicleNo = param1.inviteVehicleNo;//约车编号
					var bearFeesDept = scheduling.temprentalmark.markWin.items.items[0].getForm().findField('bearFeesDept').getRawValue();
					var bearFeesDeptCode = scheduling.temprentalmark.markWin.items.items[0].getForm().findField('bearFeesDept').getValue();
					var acceptPerson =param1.acceptPerson;//约车受理人
					var acceptPersonCode = param1.acceptPersonCode;//约车受理人
					var consultPriceNo=param1.consultPriceNo;//询价编号
					var origOrgCode=param1.origOrgCode;//出发部门编码
					var destOrgCode=param1.destOrgCode;//到达部门编码
					//326027
					var salesVehiclePlatformName=param1.salesVehiclePlatformName;//请车平台名称
					var singleSum;//单票费用
					var useVehiclePlatform=param1.useVehiclePlatform;//使用请车平台
					//var nosalesVehiclePlatformName=param1.nosalesVehiclePlatformName;//非营业部请车平台名称
					var smallTicketNumList = new Array();
					var multiCarTakeGoodsDtoList = new Array();
					var rentalMarkEntityList= new Array() ;
					var expressMarkEntityList = new Array();

					smallTicketNumList = arraySplice(billNoList(param1.smallTicketNum))//小票单号
					for(i=1;i<length-1;i++){
						var form = scheduling.temprentalmark.markWin.items.items[i].getForm().getValues();
						singleSum=form.singleSum;
						var departureName = scheduling.temprentalmark.markWin.items.items[i].getForm().findField('departure').getRawValue();
						var departureCode = scheduling.temprentalmark.markWin.items.items[i].getForm().findField('departure').getValue();
						var destinationName = scheduling.temprentalmark.markWin.items.items[i].getForm().findField('destination').getRawValue();
						var destinationCode = scheduling.temprentalmark.markWin.items.items[i].getForm().findField('destination').getValue();
						//269701-lln-2015/10/06-begin
						// 1、出发地长度校验，由最长16位code变更为最长50位；2、字段输入和保存的规则不变
						if(scheduling.temprentalmark.markWin.items.items[i].getForm().findField('departure').getValue().length>50){
							myMask.hide();//解锁屏
							//提示信息变化   出发地长度不能超过16字--出发地长度超过限定值
							//当出发地的字段长度超过50时，点击【保存】，提示【出发地长度超过限定值】，并且无法保存
							Ext.Msg.alert("提示","出发地长度超过限定值");
							return;
						}
						// 1、 目的地长度校验，由最长16位code变更为最长50位；2、字段输入和保存的规则不变
						if(scheduling.temprentalmark.markWin.items.items[i].getForm().findField('destination').getValue().length>50){
							myMask.hide();//解锁屏
							//提示信息变化   目的地长度不能超过16字--目的地长度超过限定值
							//当目的地的字段长度超过50时，点击【保存】，提示【目的地长度超过限定值】，并且无法保存
							Ext.Msg.alert("提示","目的地长度超过限定值");
							return;
						}
						//269701-lln-2015/10/06-begin
						var multiCarTakeGoodsDto = new Object();//配置参数
						multiCarTakeGoodsDto.vehicleNo =form.vehicleNo;
						multiCarTakeGoodsDto.rentalAmount =form.rentalAmount;
						multiCarTakeGoodsDto.kmsNum =form.kmsNum;
						multiCarTakeGoodsDto.departureCode =departureCode;
						multiCarTakeGoodsDto.destinationCode = destinationCode;
						multiCarTakeGoodsDto.departureName=departureName;
						multiCarTakeGoodsDto.destinationName=destinationName;
						var record = new Foss.scheduling.tempmarkModel(multiCarTakeGoodsDto);
						multiCarTakeGoodsDtoList.push(record.data);
					}
					for(i=0;i<dataList.length;i++){
						if (dataList[i].get('billType') == 'expresshandoverbill' ||
							dataList[i].get('billType') == 'expresswaybill'||
							dataList[i].get('billType') == 'airPortBill') {
							expressMarkEntityList.push(dataList[i].data);
						} else {
							rentalMarkEntityList.push(dataList[i].data);
						}
					}
					var param2 = scheduling.temprentalmark.markWin.items.items[length-1].getForm().getValues()
					var remark = param2.remark;
					Ext.Ajax.request({
						url : scheduling.realPath('addTempRentalMark.action'),
						jsonData : {
							'tempRentalMatkVo':{
								'rentalUse':rentalUse,
								'consultPriceNo':consultPriceNo,
								'isRepeateMark':isRepeateMark,
								'useCareDate':useCareDate,
								'carReason':carReason,
								'inviteVehicleNo':inviteVehicleNo,
								'acceptPerson':acceptPerson,
								'acceptPersonCode':acceptPersonCode,
								'bearFeesDept':bearFeesDept,
								'bearFeesDeptCode':bearFeesDeptCode,
								'smallTicketNumList':smallTicketNumList,
								'multiCarTakeGoodsDtoList':multiCarTakeGoodsDtoList,
								'rentalMarkEntityList':rentalMarkEntityList,
								'remark':remark,
								'departmentSignle':departmentSignle,
								//326027
								'salesVehiclePlatformName':salesVehiclePlatformName,
								'useVehiclePlatform' : useVehiclePlatform,
								'wkTfrBillEntityList' : expressMarkEntityList,
								'singleSum' : singleSum
							}
						},
						success : function(response) {
							myMask.hide();//解锁屏
							Ext.Msg.alert(scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.RentalMarkWindow.title'),scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.opreatSuccess'));//("租车标记","操作成功")
							scheduling.temprentalmark.markWin.close();
						},
						exception:function (response){
							myMask.hide();//解锁屏
							var result = Ext.decode(response.responseText);
							Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),result.message);
						}
					});
				}

			}}]}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 租车标记窗口
 */
Ext.define('Foss.temprentalmark.window.temprentalMark', {
	extend:'Ext.window.Window',
	title: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.RentalMarkWindow.title'),//租车标记,
	modal:true,
	closeAction:'hide',
	width: 800,
	bodyCls: 'autoHeight',
	layout: 'auto',
	items:[scheduling.temprentalmark.temprentalMarkForm1=Ext.create('Foss.temprentalmark.form.temprentalMark1'),
		scheduling.temprentalmark.temprentalMarkForm2=Ext.create('Foss.temprentalmark.form.temprentalMark2'),
		scheduling.temprentalmark.temprentalMarkForm4=Ext.create('Foss.temprentalmark.form.temprentalMark4')],
	listeners:{
		'hide':function(window,eOpts){
			var len = window.items.length;
			for(var i=2 ;i<len-1;i++){
				if(window.items.length>3)
					window.remove(window.items.items[2],true);
			}
			len = window.items.length;
			for(var i=0;i<len;i++){
				window.items.items[i].getForm().reset();
			}
		}
	}
});

/**
 *租车标记函数
 ***/
function rentalMark(){
	//创建租车标记窗口
	if(scheduling.temprentalmark.markWin==null){
		scheduling.temprentalmark.markWin=Ext.create('Foss.temprentalmark.window.temprentalMark');
	}
	//每次点击租车标记时，先初始化多车走货按钮为可见
	Ext.getCmp('multiCarTakegoods').setVisible(true);
	var origOrgCode=dataList[0].get('origOrgCode');
	var destOrgCode=dataList[0].get('destOrgCode');
	for(i=0;i<dataList.length;i++){
		if(dataList[i].get('billType')!='waybill'){
			//如果选择的不全是运单，则多车走货按钮隐藏
			Ext.getCmp('multiCarTakegoods').setVisible(false);
		}
	}

	var typeList = new Array();//存放所选数据的单据类型
	var vehicleNoList = new Array();//存放车牌号
	var origOrgCodeList = new Array();
	var destOrgCodeList = new Array();
	var origOrgEntityList = new Array();
	var destOrgEntityList = new Array();
	rentalUse=Ext.getCmp('rentalUse');
	var kmsNumList = new Array();
	//combo.getStore().load();
	rentalUse.setReadOnly(false);
	var j=0;
	/**为初始化车牌号和用车日期做准备**/
	for(i=0;i<dataList.length;i++){
		typeList[i]=dataList[i].get('billType');
		dateList[i]=dateFormat(dataList[i].get('billCreateTime'));
		vehicleNoList[i]=dataList[i].get('vehicleNo');
		if(typeList[i]=='handoverbill'){
			kmsNumList[i]=dataList[i].get('kmsNum');
			dateList[i]=dateConvert(dataList[i].get('billCreateTime'));
			origOrgCodeList[j]=dataList[i].get('origOrgCode');
			destOrgCodeList[j]=dataList[i].get('destOrgCode');
			origOrgEntityList[j]={'code':dataList[i].get('origOrgCode'),'name':dataList[i].get('origOrgName')};
			destOrgEntityList[j]={'code':dataList[i].get('destOrgCode'),'name':dataList[i].get('destOrgName')};
			j++;
		}
		Ext.getCmp('origOrgCode').setReadOnly(typeList[i]=='handoverbill');
		Ext.getCmp('destOrgCode').setReadOnly(typeList[i]=='handoverbill');
		Ext.getCmp('temprentalmarkKmsNum').setReadOnly(typeList[i]=='handoverbill');
		Ext.getCmp('useCareDate').setReadOnly(typeList[i]=='handoverbill');
	}
	origOrgCodeList = uniqueArray(origOrgCodeList);
	destOrgCodeList = uniqueArray(destOrgCodeList);


	//269701--lln--2015-09-06 begin 
	//查询出的快递单号都可进行租车标记（不校验是否上门接货、送货，且无需填写小票单号）
	//如果选中的数据没有快递信息时，按照之前的校验进行
	if(dataListStowageBill.length==0){//当没有配载单时
		//如果选择的全是快递运单，则租车用途选项 默认为空，可修改，不允许选择“转货”--按照零担运单方式显示
		if(dataListExpressWaybill.length!=0 && dataListWaybill.length==0 && dataListHandoverbill.length==0 && dataListDeliverbill.length==0){
			rentalUse.setValue('');
			rentalUse.bindStore(rentalUseStore2);
			//269701--lln--2015-09-06 end
		}else if(dataListWaybill.length!=0 && dataListExpressWaybill.length==0 &&dataListHandoverbill.length==0&&dataListDeliverbill.length==0){//如果选择的全是零担运单，则租车用途选项 默认为空，可修改，不允许选择“转货”
			rentalUse.setValue('');
			rentalUse.bindStore(rentalUseStore2);
		}else if(dataListWaybill.length==0 && dataListExpressWaybill.length==0 &&dataListHandoverbill.length!=0&&dataListDeliverbill.length==0){//若选择的全是交接单，则默认为“转货”，不可修改
			rentalUse.bindStore(rentalUseStore1);
			rentalUse.setValue('ZH');
			rentalUse.setReadOnly(true);
		}else if(dataListWaybill.length==0 && dataListExpressWaybill.length==0 &&dataListHandoverbill.length==0&&dataListDeliverbill.length!=0){//若选择的全是派送单，则默认为“送货”，不可修改
			rentalUse.bindStore(rentalUseStore1);
			rentalUse.setValue('SH');
			rentalUse.setReadOnly(true);
		}else if(dataListWaybill.length!=0 && dataListExpressWaybill.length==0 &&dataListHandoverbill.length==0&&dataListDeliverbill.length!=0){//既有零担运单又有派送单
			rentalUse.bindStore(rentalUseStore1);
			rentalUse.setValue('JSH');
			rentalUse.setReadOnly(true);
		}else{
			rentalUse.setValue('JSH');
			rentalUse.setReadOnly(true);
		}
	}else {
		//配载单不为空时，快递运单必为空--租车用途显示一样
		if(dataListWaybill.length==0&&dataListDeliverbill.length==0){
			rentalUse.bindStore(rentalUseStore1);
			rentalUse.setValue('ZH');
			rentalUse.setReadOnly(true);
		}else{
			rentalUse.setValue('JSH');
			rentalUse.setReadOnly(true);
		}
	}
	for(i=0;i<dataList.length;i++){
		//给用车时间赋值
		if(dataList[i].get('billType')=='handoverbill'){
			//给用车时间赋值
			useCareDate=Ext.getCmp('useCareDate').setValue(maxCreateDate(dataList));//用车时间
		}else{
			useCareDate=Ext.getCmp('useCareDate').setValue(maxDate(dateList));//用车时间
		}
	}
	inviteVehicleNo=Ext.getCmp('inviteVehicleNo');//约车编号
	acceptPerson=Ext.getCmp('acceptPerson');//约车受理人
	var bearFeesDept=scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('bearFeesDept');//310248费用承担部门
	var temprentalMarkForm2=scheduling.temprentalmark.temprentalMarkForm2.getForm();
	var rentalAmount=temprentalMarkForm2.findField('rentalAmount');
	var singleSum=temprentalMarkForm2.findField('singleSum');
	
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('departure').getStore().loadData(origOrgEntityList);
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('departure').setValue(origOrgCodeList);

	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('destination').getStore().loadData(destOrgEntityList);
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('destination').setValue(destOrgCodeList);

	if(scheduling.temprentalmark.departmentSignle!=null){
		orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
		departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
	}
	if(departmentSignle=='SalesDepartment'){//为营业部时
		inviteVehicleNo.allowBlank=false;
		inviteVehicleNo.setVisible(true);
		acceptPerson.setVisible(true);
		rentalAmount.setReadOnly(true);
		bearFeesDept.setVisible(false);//310248
		bearFeesDept.allowBlank=true;//310248
	}else{
		inviteVehicleNo.setVisible(false);
		acceptPerson.setVisible(false);
		rentalAmount.setReadOnly(false);
		bearFeesDept.setVisible(true);
		bearFeesDept.allowBlank=false;
	}
	//先初始化小票单号为隐藏；
	scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(false);
	Ext.getCmp('ticketButton').setVisible(false)
	isSmallTicketVisible(rentalUse.getValue());//设置小票单号是否隐藏
	if(dataListWaybill.length==0&&isVehicleNoSame(vehicleNoList)){//如果有运单则车牌号为空，如果没有运单只有交接单或者派送单或交接单、或配载单，并且所选数据的车牌号相同则把车牌号自动设置为选择的数据
		scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('vehicleNo').setCombValue(vehicleNoList[0],vehicleNoList[0]);
		scheduling.temprentalmark.markWin.show();//执行点击租车标记后续操作
	}else if(dataListWaybill.length==0&&!isVehicleNoSame(vehicleNoList)){
		Ext.MessageBox.confirm("提示","所选单据车牌号不一样，是否确认标记",function(button){
			if(button=='yes'){
				scheduling.temprentalmark.markWin.show();//显示租车标记窗口
			}else{
				return ;
			}
		});
	}else {
		scheduling.temprentalmark.markWin.show();//显示租车标记窗口
	}

}

/**
 *租车标记函数
 ***/
function rentalMarkExpress(){
	//创建租车标记窗口
	if(scheduling.temprentalmark.markWin==null){
		scheduling.temprentalmark.markWin=Ext.create('Foss.temprentalmark.window.temprentalMark');
	}
	//每次点击租车标记时，先初始化多车走货按钮为可见
	Ext.getCmp('multiCarTakegoods').setVisible(true);
	var origOrgCode=dataList[0].get('departOrgCode');
	var destOrgCode=dataList[0].get('arriveOrgCode');
	
	for(i=0;i<dataList.length;i++){
		if(dataList[i].get('billType')!='expresswaybill'){
			//如果选择的不全是运单，则多车走货按钮隐藏
			Ext.getCmp('multiCarTakegoods').setVisible(false);
		}
	}

	var typeList = new Array();//存放所选数据的单据类型
	var vehicleNoList = new Array();//存放车牌号
	var origOrgCodeList = new Array();
	var destOrgCodeList = new Array();
	var origOrgEntityList = new Array();
	var destOrgEntityList = new Array();
	
	rentalUse=Ext.getCmp('rentalUse');
	var kmsNumList = new Array();
	rentalUse.setReadOnly(false);
	var j=0;
	
	/**为初始化车牌号和用车日期做准备**/
	for(i=0;i<dataList.length;i++){    
		typeList[i]=dataList[i].get('billType');
		dateList[i]=dateFormat(dataList[i].get('createTime'));
		vehicleNoList[i]=dataList[i].get('vehicleNo');
		if(typeList[i]=='expresshandoverbill'){  //快递交接单
			kmsNumList[i]=dataList[i].get('kmsNum');
			dateList[i]=dateConvert(dataList[i].get('createTime'));
			origOrgCodeList[j]=dataList[i].get('departOrgCode');
			destOrgCodeList[j]=dataList[i].get('arriveOrgCode');
			origOrgEntityList[j]={'code':dataList[i].get('departOrgCode'),'name':dataList[i].get('departOrgName')};
			destOrgEntityList[j]={'code':dataList[i].get('arriveOrgCode'),'name':dataList[i].get('arriveOrgName')};
			j++;
			Ext.getCmp('origOrgCode').setReadOnly(typeList[i]=='expresshandoverbill');
			Ext.getCmp('destOrgCode').setReadOnly(typeList[i]=='expresshandoverbill');
			Ext.getCmp('temprentalmarkKmsNum').setReadOnly(true);
			Ext.getCmp('useCareDate').setReadOnly(typeList[i]=='expresshandoverbill');
		} else if (typeList[i]=='airPortBill') {  //快递航空单
			kmsNumList[i]=dataList[i].get('kmsNum');
			dateList[i]=dateConvert(dataList[i].get('createTime'));
			origOrgCodeList[j]=dataList[i].get('departOrgCode');
			destOrgCodeList[j]=dataList[i].get('arriveOrgCode');
			origOrgEntityList[j]={'code':dataList[i].get('departOrgCode'),'name':dataList[i].get('departOrgName')};
			destOrgEntityList[j]={'code':dataList[i].get('arriveOrgCode'),'name':dataList[i].get('arriveOrgName')};
			j++;
			Ext.getCmp('temprentalmarkKmsNum').setValue('').setReadOnly(false);
			Ext.getCmp('origOrgCode').setReadOnly(false);
			Ext.getCmp('destOrgCode').setReadOnly(false);
			Ext.getCmp('useCareDate').setReadOnly(typeList[i]=='airPortBill');
		}else if (typeList[i]=='expresswaybill'){  //快递运单
			kmsNumList[i]=dataList[i].get('kmsNum');
			dateList[i]=dateConvert(dataList[i].get('createTime'));
			origOrgCodeList[j]=dataList[i].get('departOrgCode');
			destOrgCodeList[j]=dataList[i].get('arriveOrgCode');
			origOrgEntityList[j]={'code':dataList[i].get('departOrgCode'),'name':dataList[i].get('departOrgName')};
			destOrgEntityList[j]={'code':dataList[i].get('arriveOrgCode'),'name':dataList[i].get('arriveOrgName')};
			j++;
			Ext.getCmp('origOrgCode').setReadOnly(false);
			Ext.getCmp('destOrgCode').setReadOnly(false);
			Ext.getCmp('temprentalmarkKmsNum').setReadOnly(false);
			Ext.getCmp('useCareDate').setReadOnly(true);
		}
	}
		origOrgCodeList = uniqueArray(origOrgCodeList);
		destOrgCodeList = uniqueArray(destOrgCodeList);


	//269701--lln--2015-09-06 begin 
	//查询出的快递单号都可进行租车标记（不校验是否上门接货、送货，且无需填写小票单号）
	//如果选中的数据没有快递信息时，按照之前的校验进行
	if((dataListExpressWaybill.length !=0 || dataListExpressHandoverbill.length != 0) && dataListAirPortbill.length != 0){
		Ext.Msg.alert('标题','快递机场单据只能单独标记!');
		return;
	//如果选择的全是快递运单，则租车用途选项 默认为空，可修改，不允许选择“转货”--按照零担运单方式显示
	}else if(dataListExpressWaybill.length!=0 && dataListExpressHandoverbill.length==0){
		rentalUse.setValue('');
		rentalUse.bindStore(rentalUseStore2);
	}else if((dataListExpressWaybill.length==0 && dataListExpressHandoverbill.length != 0) || dataListAirPortbill.length != 0){//若选择的全是交接单，则默认为“转货”，不可修改
		rentalUse.bindStore(rentalUseStore1);
		rentalUse.setValue('ZH');
		rentalUse.setReadOnly(true);
//	}else if(dataListExpressWaybill.length !=0 && dataListExpressHandoverbill.length != 0 && dataListAirPortbill.length != 0){//若选择的单据有快递运单，快递交接单，快递机场扫描单据，则默认为“空”，可编辑。
//		rentalUse.bindStore(rentalUseStore1);
//		rentalUse.setValue('');
//		rentalUse.setReadOnly(true);
	}else{
		rentalUse.setValue('JSH');
		rentalUse.setReadOnly(true);
	}
	
	for(i=0;i<dataList.length;i++){
		//给用车时间赋值
		if(dataList[i].get('billType')=='expresshandoverbill'||dataList[i].get('billType')=='airPortBill'){
			//给用车时间赋值
			useCareDate=Ext.getCmp('useCareDate').setValue(maxCreateDateExpress(dataList));//用车时间
		}else{
			useCareDate=Ext.getCmp('useCareDate').setValue(maxDate(dateList));//用车时间
		}
	}
	inviteVehicleNo=Ext.getCmp('inviteVehicleNo');//约车编号
	acceptPerson=Ext.getCmp('acceptPerson');//约车受理人
	var bearFeesDept=scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('bearFeesDept');//310248费用承担部门
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('departure').getStore().loadData(origOrgEntityList);
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('departure').setValue(origOrgCodeList);

	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('destination').getStore().loadData(destOrgEntityList);
	scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('destination').setValue(destOrgCodeList);

	if(scheduling.temprentalmark.departmentSignle!=null){
		orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
		departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
	}
	if(departmentSignle=='SalesDepartment'){//为营业部时
		inviteVehicleNo.allowBlank=false;
		inviteVehicleNo.setVisible(true);
		acceptPerson.setVisible(true);
		scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setReadOnly(true);
		bearFeesDept.setVisible(false);//310248
		bearFeesDept.allowBlank=true;//310248
	}else{
		inviteVehicleNo.setVisible(false);
		acceptPerson.setVisible(false);
		scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setReadOnly(false);
		bearFeesDept.setVisible(true);
		bearFeesDept.allowBlank=false;
	}
	
	//scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('rentalAmount').setReadOnly(true);
	
	//先初始化小票单号为隐藏；
	scheduling.temprentalmark.temprentalMarkForm1.getForm().findField('smallTicketNum').setVisible(false);
	Ext.getCmp('ticketButton').setVisible(false);
	isSmallTicketVisible(rentalUse.getValue());//设置小票单号是否隐藏
	if(dataListWaybill.length==0&&isVehicleNoSame(vehicleNoList)){//如果有运单则车牌号为空，如果没有运单只有交接单或者派送单或交接单、或配载单，并且所选数据的车牌号相同则把车牌号自动设置为选择的数据
		scheduling.temprentalmark.temprentalMarkForm2.getForm().findField('vehicleNo').setCombValue(vehicleNoList[0],vehicleNoList[0]);
		scheduling.temprentalmark.markWin.show();//执行点击租车标记后续操作
	}else if(dataListWaybill.length==0&&!isVehicleNoSame(vehicleNoList)){
		Ext.MessageBox.confirm("提示","所选单据车牌号不一样，是否确认标记",function(button){
			if(button=='yes'){
				scheduling.temprentalmark.markWin.show();//显示租车标记窗口
			}else{
				return ;
			}
		});
	}else {
		scheduling.temprentalmark.markWin.show();//显示租车标记窗口
	}

}

/**
 *
 * 查询列表 零担grid
 *
 * */
var temprentalTabGridStore = null;
Ext.define('Foss.temprentalmark.TemprentalTabGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	//title : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.title.GoodsList'),//货物列表
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.textfield.empty'),
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.QueryTemprentalMarkStore');
		temprentalTabGridStore = me.store;
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.RentalMarkWindow.title'),//租车标记按钮
			disabled : !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkButton'),
			hidden : !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkButton'),
			handler : function(){
				var grid = this.ownerCt.ownerCt;
				dataList= grid.getSelectionModel().getSelection();
				if(dataList.length==0){
					Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.pleaseSelectDatas"));//("提示","请选择数据后操作")
					return;
				}

				dataListSplit(dataList);//把所选数据按单据类型分别存放
				//269701--lln--2015-09-01 begin
				//标记时：1、 货物列表中的快递信息可选择多条，同时标记；2、快递信息和零担信息不可同时标记
				if(dataListExpressWaybill.length!=0 && (dataListHandoverbill.length!=0
					|| dataListStowageBill.length!=0 || dataListDeliverbill.length!=0
					|| dataListWaybill.length != 0)){
					Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.expressBreakbulkMarked"));//("提示","快递信息和零担信息不可同时标记")
					return;
				}

				//269701--lln--2015-09-01 end

				var repatMarkSigln=noRepeatMark(dataListHandoverbill);
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.handoverBillAlreadyMarked"));//("提示","该交接单已被标记，不能再次标记")
					return;
				}
				//配载单多次标记校验	
				repatMarkSigln=noRepeatMark(dataListStowageBill);//配载单多次标记校验
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert('提示','该配载单已经被标记，不能再次标记');//("提示","该配载单已被标记，不能再次标记")
					return;
				}
				//派送单多次标记校验
				repatMarkSigln=noRepeatMark(dataListDeliverbill);

				if(repatMarkSigln=='Y'){
					Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.devliverBillAlreadyMarked"));//("提示","该交接单已被标记，不能再次标记")
					return;
				}

				isRepeateMark='N';//初始化多次标记为否
				if(dataListWaybill.length>0){//运单多次标记判断，根据运单号和租车标记部门两个字段判断当前操作部门在3个月内是否多次标记过该运单
					var reBillNo;
					for(i=0;i<dataListWaybill.length;i++){
						if(dataListWaybill[i].get('rentalNum')!=null&&dataListWaybill[i].get('rentalNum')!=''){
							if(dataListWaybill[i].get('markDeptCode')==orgCode){
								var createDate = dataListWaybill[i].get('createDate');
								var myDate = new Date(createDate.getFullYear(),createDate.getMonth()+3,createDate.getDate());
								if (dateFormat(myDate)>dateFormat(new Date())){
									isRepeateMark='Y';
									reBillNo=dataListWaybill[i].get('billNo');
									break;
								}
							}
						}
					}
					if(isRepeateMark=='N'){//向后台查询数据，看是否已标记
						var wayBillNoList=new Array() ;
						for(i=0;i<dataListWaybill.length;i++){
							wayBillNoList[i] = dataListWaybill[i ].get('billNo');
						}
						Ext.Ajax.request({
							url : scheduling.realPath('wayBillRepeatMark.action'),
							async :  false,//同步调用；
							params : {
								'rentalMarkVo.rentalMarkDto.wayBillNoList':wayBillNoList,
								'rentalMarkVo.rentalMarkDto.orgCode':orgCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								isRepeateMark=result.rentalMarkVo.isrepeatMark;

							},
							exception:function (response){
								Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt.validateFailed"));//('提示','小票单号查询失败')
							}
						});

					}
					if(isRepeateMark=='Y'){
						Ext.MessageBox.confirm(scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt'),scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt.ifReMarkAgain'),function(button){
							if(button=='yes'){
								rentalMark();//执行点击租车标记后续操作
							}else{
								return ;
							}
						});

					}else{
						rentalMark();//执行点击租车标记后续操作
					}
				}else{
					rentalMark();//执行点击租车标记后续操作
				}

			}
		},{
			xtype : 'button',
			text : '导出',
			hidden: !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkExportButton'),
			handler : function(){

				if(exportType=='QBB'){//如果按单号查询导出，导出单号对应条件的数据
					var form=queryByBillNumberForm.getForm();
				}else if(exportType=='QBD'){
					var form=queryByDateForm.getForm();
				}
				if(!form.isValid()){
					return;
				}
				exportData(form,exportType);
			}
		}],
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 50,
				maximumSize : 50,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['50', 50], ['100', 100], ['500', 500]]
				})
			});
		scheduling.temprentalmark.pagingBar = me.bbar;
		me.callParent([ cfg ]);
	},
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout : 'column',
		defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 60
		},
		items: [{
			fieldLabel : '总条数',
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 0.2,
			value: 0,
			id : 'gridTotalCount'
		}]
	}],
	columns:[{
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalNum'),//租车编号
		dataIndex: 'rentalNum',
		width: 100
	},{
		header:'租车金额',//租车金额
		dataIndex: 'rentalAmount',
		width: 100
	},{
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billNo'),//单号
		dataIndex: 'billNo'
	},{
		//269701--lln--2015-09-01 begin
		//区分零担与快递
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.isExpress'),//是否快递
		dataIndex:'isExpressWayBill',//是否快递
		hidden:true
		//269701--lln--2015-09-01 end
	},
		{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billType'),//单据类型
			dataIndex: 'billType',
			renderer:function(value){
				if(value=='handoverbill'){
					return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.handover');//交接单
				}
				else if(value=='waybill'){
					//按日期查询时：单据类型显示方式--快递运单和零担运单都是运单
					//按单号查询时:单据类型显示方式--快递运单和零担运单分开显示
					if(queryType==scheduling.temprentalmark.QUERY_BY_BILL){
						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.breakbulkWaybill');//零担运单
					}else{
						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.waybill');//运单
					}
				}
				else if(value=='deliverbill'){
					return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.deliverbill');//派送单
				}
				else if(value=='stowagebill'){
					return '配载单';
				}//269701--lln--2015-09-01 begin
				else if(value=='expressbill'){
					return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.expressWaybill');//快递运单
					//269701--lln--2015-09-01 end
				}else if(value=='airPortBill'){
					return '短途交接单';//短途交接单
				}
				else{
					return value;
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.vehicleNo'),//车牌号
			dataIndex: 'vehicleNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.consultPriceNo'),//询价编号///////////////
			dataIndex: 'consultPriceNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.inviteCarNum'),//约车编号
			dataIndex: 'inviteVehicleNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.driverName'),//司机
			dataIndex: 'driverName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.origOrgName'),//出发部门
			dataIndex: 'origOrgName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destOrgName'),//到达部门
			dataIndex: 'destOrgName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.origOrgCode'),//出发部门编码
			dataIndex: 'origOrgCode'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destOrgCode'),//到达部门编码
			dataIndex: 'destOrgCode'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.weight'),//重量
			dataIndex: 'weight'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.volume'),//体积
			dataIndex: 'volume'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.goodsName'),//货物品名
			dataIndex: 'goodsName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.packing'),//包装
			dataIndex: 'packing'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.isDoorDeliver'),//是否上门接货
			dataIndex: 'isDoorDeliver',
			renderer:function(value){
				if(value=='N'){
					return '否';
				}else if(value=='Y'){
					return '是';
				}else{
					return value;
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.customerName'),//发货客户名称
			dataIndex: 'customerName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.sendAddress'),//发货地址
			dataIndex: 'sendAddress'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destination'),//目的站
			dataIndex: 'destination'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.pickUpWayCode'),//提货方式
			dataIndex: 'pickUpWay',
			hidden:true
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.pickUpWayName'),//提货方式名称
			dataIndex: 'pickUpWayName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.receiptContacts'),//收货联系人
			dataIndex: 'receiptContacts'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.receiptAddress'),//收货地址
			dataIndex: 'receiptAddress'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billCreateTime'),//单据生成时间
			dataIndex: 'billCreateTime',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');

				}

			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalUse'),//租车用途
			dataIndex: 'rentalUse',
			renderer:function(value){
				if(value!=null){
					if(value=='SH'){
						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.send');//送货
					}else if(value=='JH'){
						return  scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.receive');//接货
					}else if(value=='ZH'){

						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.transfer');//转货
					}else if(value=='JSH'){
						
                             
						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.deliver');//接送货
					}else {

						return value;
					}
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.createDate'),//租车标记时间
			dataIndex: 'createDate',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),
						value.getDate()),'Y-m-d');
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markDeptName'),//租车标记部门
			dataIndex: 'markDeptName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markDeptCode'),//租车标记部门编码
			dataIndex: 'markDeptCode',
			hidden:true
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markOperator'),//租车标记操作人
			dataIndex: 'markOperator'
		}]
});

//按单号查询from
Ext.define('Foss.temprentalmark.QueryByBillNumberForm',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
		margin :'20 5 5 0',
		labelWidth :85,
		colspan : 1
	},
	defaultType:'textfield',
	layout:'column',
	items:[
		{
			xtype: 'textarea',
			//autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.breakbulkWaybillNo'),//零担运单号
			name: 'breakbulkWaybillNoList',
			id:'breakbulkWaybillNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			autoWidth:true,
			colspan:3,
			columnWidth:.25,
			//零担运单号 位数8-9位，开头数字：0-9
			//regex : /^([0-9]{7,9}[,]){0,29}([0-9]{7,9}[,]?)$/i,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(false);
					form.findField('expressBillNoList').setDisabled(false);
					form.findField('airPortBillNoList').setDisabled(false);
				},
				focus : function(area){
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(true);
					form.findField('expressBillNoList').setDisabled(true);
					form.findField('airPortBillNoList').setDisabled(true);
				}
			}
//	    	   validator:function(){
//	    	   return validatorBillNo(this.getValue());
//	    	   }	 
		},{
			xtype: 'textarea',
			columnWidth:.25,
			autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.handoverNo'),//交接单号
			name: 'handoverBillNoList',
			id:'handoverBillNoList',
			regex :/^((k{0,1}[0-9]{8,10}[,])|([B][^,]+[,])|([0-9]{8,10}p{0,1}[,])){0,29}((k{0,1}[0-9]{8,10}[,]?)|([B][^,]+[,]?)|([0-9]{8,10}p{0,1}[,]?))$/i,
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			colspan:3,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(false);
					form.findField('expressBillNoList').setDisabled(false);
					form.findField('airPortBillNoList').setDisabled(false);
				},
				focus : function(area){
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(true);
					form.findField('expressBillNoList').setDisabled(true);
					form.findField('airPortBillNoList').setDisabled(true);
				}
			}
//	    	   validator:function(){
//	    	   return validatorBillNo(this.getValue());
//	    	   }
		},{
			xtype: 'textarea',
			//autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: '配载单',
			name: 'stowageBillNoList',
			id:'stowageBillNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			autoWidth:true,
			colspan:3,
			columnWidth:.25,
			//regex : /^([0-9]{7,10}[,]){0,29}([0-9]{7,10}[,]?)$/i
			validator:function(){
				return validatorBillNo(this.getValue());
			},
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(false);
					form.findField('expressBillNoList').setDisabled(false);
					form.findField('airPortBillNoList').setDisabled(false);
				},
				focus : function(area){
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(true);
					form.findField('expressBillNoList').setDisabled(true);
					form.findField('airPortBillNoList').setDisabled(true);
				}
			}
		},{
			xtype: 'textarea',
			columnWidth:.25,
			autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.deliverbillNo'),//派送单
			name: 'deliverbillNoList',
			id:'deliverbillNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			regex:/^(P[0-9]{7,10}[,]){0,29}(P[0-9]{7,10}[,]?)$/i,
			height : 80,
			width : 300,
			allowBlank:true,
			colspan:3,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(false);
					form.findField('expressBillNoList').setDisabled(false);
					form.findField('airPortBillNoList').setDisabled(false);
				},
				focus : function(area){
					var form = this.up('form').getForm();
					form.findField('expressWaybillNoList').setDisabled(true);
					form.findField('expressBillNoList').setDisabled(true);
					form.findField('airPortBillNoList').setDisabled(true);
				}
			}
//	    	   validator:function(){
//	    	   return validatorBillNo(this.getValue());
//	    	   }
		},
		//269701--lln-2015-08-30 begin
		//新增--“快递运单号”输入框
		{
			xtype: 'textarea',
			//autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.expressWaybillNo'),//快递运单号
			name: 'expressWaybillNoList',
			id:'expressWaybillNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			autoWidth:true,
			colspan:3,
			//快递运单号 位数10位，开头数字：0-9
			regex : /^([0-9]{7,15}[,]){0,29}([0-9]{7,15}[,]?)$/i,
			//regex :/^\d{1,10}$/,
			//regex : /^([0-9]{7,15}[,]){0,29}([0-9]{7,15}[,]?)$/i,
			columnWidth:.25,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(false);
					form.findField('handoverBillNoList').setDisabled(false);
					form.findField('stowageBillNoList').setDisabled(false);
					form.findField('deliverbillNoList').setDisabled(false);
				},
				focus : function(area){
					type=2;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(true);
					form.findField('handoverBillNoList').setDisabled(true);
					form.findField('stowageBillNoList').setDisabled(true);
					form.findField('deliverbillNoList').setDisabled(true);
				}
			}

			//快递运单号 位数是10位，开头数字是5 6 7 8 9
//	    	   validator:function(){
//	    	   return validatorBillNo(this.getValue());
//	    	   }
		},{
			xtype: 'textarea',
			//autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: '快递交接单',
			name: 'expressBillNoList',
			id:'expressBillNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			autoWidth:true,
			colspan:3,
			columnWidth:.25,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(false);
					form.findField('handoverBillNoList').setDisabled(false);
					form.findField('stowageBillNoList').setDisabled(false);
					form.findField('deliverbillNoList').setDisabled(false);
				},
				focus : function(area){
					type=2;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(true);
					form.findField('handoverBillNoList').setDisabled(true);
					form.findField('stowageBillNoList').setDisabled(true);
					form.findField('deliverbillNoList').setDisabled(true);
				}
			}
			//regex : /^([0-9][a-z]{7,10}[,]){0,29}([0-9][a-z]{7,10}[,]?)$/i
//	    	   validator:function(){
//	    		   return validatorBillNo(this.getValue());        	
//	    	   }
		},{
			xtype: 'textarea',
			//autoScroll:true,
			emptyText: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.emptyText'),
			fieldLabel: '快递机场扫描单据号',
			name: 'expressAirportNoList',
			id:'expressAirportNoList',
			regexText:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rigthNumber'),
			height : 80,
			width : 300,
			allowBlank:true,
			autoWidth:true,
			colspan:3,
			columnWidth:.25,
			listeners : {
				blur : function(area){
					type=1;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(false);
					form.findField('handoverBillNoList').setDisabled(false);
					form.findField('stowageBillNoList').setDisabled(false);
					form.findField('deliverbillNoList').setDisabled(false);
				},
				focus : function(area){
					type=2;
					var form = this.up('form').getForm();
					form.findField('breakbulkWaybillNoList').setDisabled(true);
					form.findField('handoverBillNoList').setDisabled(true);
					form.findField('stowageBillNoList').setDisabled(true);
					form.findField('deliverbillNoList').setDisabled(true);
				}
			}
			//regex : /^([0-9][a-z]{7,10}[,]){0,29}([0-9][a-z]{7,10}[,]?)$/i
//	    	   validator:function(){
//	    		   return validatorBillNo(this.getValue());        	
//	    	   }
		},{
			xtype:'container',
			border:false,
			html:'&nbsp;',
			columnWidth:.15
		},
		//269701--lln-2015-08-30 end
		{
			border: 1,
			xtype:'container',
			columnWidth:.1,
			height : 80,
			style :'background:none',//去掉控件背景默认颜色
			colspan:3,
			defaultType:'button',
			layout:'border',//border布局 有东西南北中 5个位置
			items:[{
				text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.reset'),//重置
				width:10,//border布局 每次 columnWidth属性
				region:'south',
				handler:function(){
					this.up('form').getForm().reset();
				}
			},
				{
					text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.query'),//查询
					hidden : !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkQueryButton'),
					width:10,
					region:'north',
					cls:'yellow_button',
					handler:function(){
						var form = this.up('form').getForm();
						queryType=scheduling.temprentalmark.QUERY_BY_BILL;
						//269701--lln--2015-08-30 begin
						//所有单号均为空时 提示：请输入单号
						//追加快递运单号的判断
						if(form.getValues().breakbulkWaybillNoList==""	//零担运单号
							&&form.getValues().expressWaybillNoList==""	//快递运单号
							&&form.getValues().handoverBillNoList==""	//零担交接单号
							&&form.getValues().deliverbillNoList==""	//派送单号
							&&form.getValues().stowageBillNoList==""    //配载单号
							&&form.getValues().expressBillNoList==""    //快递交接单号
							&&form.getValues().expressAirportNoList==""    //快递机场扫描单号
						){ // 快递运单  313352 gouyangyang
							Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.pleasewriteTheBillNo"));//("提示","请输入单号")
							return;
						}
						
						if(scheduling.temprentalmark.mainTabPanel.getActiveTab().title == '零担列表'){
							scheduling.temprentalmark.pagingBar.moveFirst();
							expressGridStore.removeAll();
							//temprentalTabGridStore.getProxy().extraParams = { 'rentalMarkVo.rentalMarkDto.type': '1'}
							//temprentalTabGridStore.on('beforeload', function (store, options) {
								// ​var new_params = {'rentalMarkVo.rentalMarkDto.type': '1'};　　　 
								 //Ext.apply(store.proxy.extraParams, new_params);　
								 　//　 })};​
							Ext.getCmp('gridExpressTotalCount').setValue(0);
						}else{
							//expressGridStore.getProxy().extraParams = { 'rentalMarkVo.rentalMarkDto.type': '2'}
							scheduling.temprentalmark.pagingBar1.moveFirst();
							temprentalTabGridStore.removeAll();
							Ext.getCmp('gridTotalCount').setValue(0);
						}
						}
				}]
		}
	]
});
/**
 * 按日期查询的from
 */
Ext.define('Foss.temprentalmark.QueryByDateForm',{
	extend:'Ext.form.Panel',
	frame:false,
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	columnWidth:1,
	height:400,
	defaults:{
		margin :'20 5 5 5',
		labelWidth :60,
		colspan :1
	},
	//defaultType:'textfield',
	layout:'column',
	items:[
		{
			xtype : 'rangeDateField',
			dateType : 'datetimefield_date97',
			fromName:'billGenerationBeginTime',
			fromValue:Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
				new Date().getDate()-4, 00, 00, 00), 'Y-m-d H:i:s'),
			toName: 'billGenerationEndTime',
			toValue:Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
				new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'),
			dateRange:5,
			editable: false,
			labelWidth :100,
			fieldLabel:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billCreateTime'),//单据生成时间
			editable:false,
			allowBlank:false,
			columnWidth: .45
		},{
			xtype:'commonleasedvehicleselector',
			name: 'vehicleNo',
			id:'vehicleNo',
			fieldLabel: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.vehicleNo'),   //车牌号
			columnWidth: .25
		},{
			xtype:'textfield',
			name: 'borrowNo',
			id:'borrowNo',
			fieldLabel: '租车编号',//租车单号
			columnWidth: .30
		},{
			name: 'createDept',
			id:'createDept',
			fieldLabel: '创建部门',//创建部门
			xtype : 'dynamicorgcombselector',
			allowBlank: true,
			autoWidth:true,
			columnWidth:.25
		},{
			html:'&nbsp;',
			columnWidth:.75
		},{
			xtype:'container',
			columnWidth:1,
			colspan:4,
			defaultType:'checkboxfield',
			layout:'column',
			items:[{
				xtype : 'checkboxfield',
				name : 'isWayBill',
				id: 'isWayBill',
				inputValue : 'Y',
				checked:true,
				uncheckedValue: 'N',
				columnWidth : 0.15,
				boxLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.waybill'),//运单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						var isWayBill = form.findField('isWayBill').getValue();
						var isHandoverBill = form.findField('isHandoverBill').getValue();
						var isDeliverBill = form.findField('isDeliverBill').getValue();
						var isStowageBill = form.findField('isStowageBill').getValue();
						if(isWayBill || isHandoverBill||
							isDeliverBill || isStowageBill){
							document.getElementById('isHandoverEirBill').checked = false;
							document.getElementById('isExpressWayBill').checked = false;
							document.getElementById('isAirPortBillList').checked = false;
							form.findField('isHandoverEirBill').setDisabled(true);
							form.findField('isExpressWayBill').setDisabled(true);
							form.findField('isAirPortBillList').setDisabled(true);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							document.getElementById('isHandoverEirBill').checked = true;
							document.getElementById('isExpressWayBill').checked = true;
							document.getElementById('isAirPortBillList').checked = true;
							form.findField('isHandoverEirBill').setDisabled(false);
							form.findField('isExpressWayBill').setDisabled(false);
							form.findField('isAirPortBillList').setDisabled(false);
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');

						}
					}
				}
			},{
				xtype : 'checkboxfield',
				name : 'isHandoverBill',
				id: 'isHandoverBill',
				checked:true,
				inputValue : 'Y',
				uncheckedValue: 'N',
				columnWidth : 0.15,
				boxLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.handover'),//交接单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						var isWayBill = form.findField('isWayBill').getValue();
						var isHandoverBill = form.findField('isHandoverBill').getValue();
						var isDeliverBill = form.findField('isDeliverBill').getValue();
						var isStowageBill = form.findField('isStowageBill').getValue();
						if(isWayBill || isHandoverBill||
							isDeliverBill || isStowageBill){
							document.getElementById('isHandoverEirBill').checked = false;
							document.getElementById('isExpressWayBill').checked = false;
							document.getElementById('isAirPortBillList').checked = false;
							form.findField('isHandoverEirBill').setDisabled(true);
							form.findField('isExpressWayBill').setDisabled(true);
							form.findField('isAirPortBillList').setDisabled(true);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
							document.getElementById('isHandoverEirBill').checked = true;
							document.getElementById('isExpressWayBill').checked = true;
							document.getElementById('isAirPortBillList').checked = true;
							form.findField('isHandoverEirBill').setDisabled(false);
							form.findField('isExpressWayBill').setDisabled(false);
							form.findField('isAirPortBillList').setDisabled(false);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');

						}
					}
				}
			},{
				xtype : 'checkboxfield',
				id:'isDeliverBill',
				name : 'isDeliverBill',
				inputValue : 'Y',
				uncheckedValue: 'N',
				checked:true,
				columnWidth : 0.15,
				boxLabel : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.deliverbill'),//派送单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						var isWayBill = form.findField('isWayBill').getValue();
						var isHandoverBill = form.findField('isHandoverBill').getValue();
						var isDeliverBill = form.findField('isDeliverBill').getValue();
						var isStowageBill = form.findField('isStowageBill').getValue();
						if(isWayBill || isHandoverBill||
							isDeliverBill || isStowageBill){
							document.getElementById('isHandoverEirBill').checked = false;
							document.getElementById('isExpressWayBill').checked = false;
							document.getElementById('isAirPortBillList').checked = false;
							form.findField('isHandoverEirBill').setDisabled(true);
							form.findField('isExpressWayBill').setDisabled(true);
							form.findField('isAirPortBillList').setDisabled(true);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
							document.getElementById('isHandoverEirBill').checked = true;
							document.getElementById('isExpressWayBill').checked = true;
							document.getElementById('isAirPortBillList').checked = true;
							form.findField('isHandoverEirBill').setDisabled(false);
							form.findField('isExpressWayBill').setDisabled(false);
							form.findField('isAirPortBillList').setDisabled(false);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');

						}
					}
				}
			},{
				xtype : 'checkboxfield',
				id:'isStowageBill',
				name : 'isStowageBill',
				inputValue : 'Y',
				uncheckedValue: 'N',
				checked:true,
				columnWidth : 0.15,
				boxLabel :'配载单',//配载单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						var isWayBill = form.findField('isWayBill').getValue();
						var isHandoverBill = form.findField('isHandoverBill').getValue();
						var isDeliverBill = form.findField('isDeliverBill').getValue();
						var isStowageBill = form.findField('isStowageBill').getValue();
						if(isWayBill || isHandoverBill||
							isDeliverBill || isStowageBill){
							document.getElementById('isHandoverEirBill').checked = false;
							document.getElementById('isExpressWayBill').checked = false;
							document.getElementById('isAirPortBillList').checked = false;
							form.findField('isHandoverEirBill').setDisabled(true);
							form.findField('isExpressWayBill').setDisabled(true);
							form.findField('isAirPortBillList').setDisabled(true);
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
							form.findField('isHandoverEirBill').setDisabled(false);
							form.findField('isExpressWayBill').setDisabled(false);
							form.findField('isAirPortBillList').setDisabled(false);
							form.findField('isHandoverEirBill').checked = true;
							form.findField('isExpressWayBill').checked = true;
							form.findField('isAirPortBillList').checked = true;
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');

						}
					}
				}
			},{
				xtype : 'checkboxfield',
				id:'isHandoverEirBill',
				name : 'isHandoverEirBill',
				inputValue: 'Y',
				uncheckedValue: 'N',
				checked:true,
				disabled : true,
				columnWidth : 0.15,
				boxLabel :'快递交接单',//快递交接单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						if(newValue){
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);

						}else{
							form.findField('isWayBill').setDisabled(false);
							form.findField('isHandoverBill').setDisabled(false);
							form.findField('isDeliverBill').setDisabled(false);
							form.findField('isStowageBill').setDisabled(false);
						}
						if(form.findField('isHandoverEirBill').getDisabled() == true){
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');
						}
					}
				}
			},{
				xtype : 'checkboxfield',
				id:'isExpressWayBill',
				name : 'isExpressWayBill',
				inputValue: 'Y',
				uncheckedValue: 'N',
				checked:true,
				disabled : true,
				columnWidth : 0.15,
				boxLabel :'快递运单',//快递运单
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						if(newValue){
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
						}else{
							form.findField('isWayBill').setDisabled(false);
							form.findField('isHandoverBill').setDisabled(false);
							form.findField('isDeliverBill').setDisabled(false);
							form.findField('isStowageBill').setDisabled(false);
						}
						if(form.findField('isWayEirBill').getDisabled() == true){
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');
						}
					}
				}
			},{
				xtype : 'checkboxfield',
				id:'isAirPortBillList',
				name : 'isAirPortBillList',
				inputValue: 'Y',
				uncheckedValue: 'N',
				checked:true,
				disabled : true,
				boxLabel :'快递机场扫面单据号',
				listeners : {
					'change' : function(th,newValue,oldValue){
						var form = this.up('form').getForm();
						if(newValue){
							form.findField('isWayBill').setDisabled(true);
							form.findField('isHandoverBill').setDisabled(true);
							form.findField('isDeliverBill').setDisabled(true);
							form.findField('isStowageBill').setDisabled(true);
						}else{
							form.findField('isWayBill').setDisabled(false);
							form.findField('isHandoverBill').setDisabled(false);
							form.findField('isDeliverBill').setDisabled(false);
							form.findField('isStowageBill').setDisabled(false);
						}
						if(form.findField('isWayEirBill').getDisabled() == true){
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabGridID');
						}else{
							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');
						}
					}
				}
			}]
		},{
			border: 1,
			xtype:'container',
			columnWidth:1,
			colspan:3,
			defaultType:'button',
			layout:'column',
			items:[{
				text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.reset'),//重置
				columnWidth:.1,
				handler:function(){
					var form= this.up('form').getForm();
					form.reset();
					form.findField('billGenerationBeginTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),
						new Date().getDate()-4, 00, 00, 00), 'Y-m-d H:i:s'));
					form.findField('billGenerationEndTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(), new Date().getMonth(),
						new Date().getDate(), 23, 59, 59),'Y-m-d H:i:s'));
					form.findField('isWayBill').setDisabled(false);
					form.findField('isHandoverBill').setDisabled(false);
					form.findField('isDeliverBill').setDisabled(false);
					form.findField('isStowageBill').setDisabled(false);
				}
			},{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.8
			},
				{
					text:scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.button.query'),//查询
					columnWidth:.1,
					cls:'yellow_button',
					handler:function(){
						if(scheduling.temprentalmark.mainTabPanel.getActiveTab().title == '零担列表'){
							var form = this.up('form').getForm();
							queryType=scheduling.temprentalmark.QUERY_BY_DATE;
							if(form.getValues().billGenerationBeginTime==''||form.getValues().billGenerationEndTime==''){
								Ext.Msg.alert("提示","请输入单据时间");
								return ;
							}
							if( form.getValues().isWayBill=='N'&&form.getValues().isHandoverBill=='N'
								&&form.getValues().isDeliverBill=='N'&&form.getValues().isStowageBill=='N'&&form.getValues().isHandoverEirBill=='N'&&form.getValues().isWayEirBill=='N'){
								Ext.Msg.alert("提示","请选择单据类型");
								return ;
							}

							scheduling.temprentalmark.pagingBar.moveFirst();
							expressGridStore.removeAll();
							Ext.getCmp('gridExpressTotalCount').setValue(0);
						}else{

							scheduling.temprentalmark.mainTabPanel.setActiveTab('temprentalTabExpressGridID');
							var form = this.up('form').getForm();
							queryType=scheduling.temprentalmark.QUERY_BY_DATE;
							if(form.getValues().billGenerationBeginTime==''||form.getValues().billGenerationEndTime==''){
								Ext.Msg.alert("提示","请输入单据时间");
								return ;
							}
							if( form.getValues().isWayBill=='N'&&form.getValues().isHandoverBill=='N'
								&&form.getValues().isDeliverBill=='N'&&form.getValues().isStowageBill=='N'&&form.getValues().isHandoverEirBill=='N'&&form.getValues().isWayEirBill=='N'){
								Ext.Msg.alert("提示","请选择单据类型");
								return ;
							}
							scheduling.temprentalmark.pagingBar1.moveFirst();
							temprentalTabGridStore.removeAll();
							Ext.getCmp('gridTotalCount').setValue(0);
					    }
					}
				}]
		}
	]
});

/**
 *定义查询的table
 */
Ext.define('Foss.temprentalmark.QueryTemprentalTab',{
	extend:'Ext.tab.Panel',
	frame : false,
	bodyCls : 'autoHeight',
	cls : 'innerTabPanel',
	activeTab : 0,
	columnWidth: 1,
	//columnHeight: 'autoHeight',
	height:280,
	items : [{
		title : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.tabTitle.queryByBillNo'),//按单号查询
		tabConfig : {
			width : 120
		},
		items : [queryByBillNumberForm = Ext.create('Foss.temprentalmark.QueryByBillNumberForm')]
	},{
		title : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.tabTitle.queryByDate'),//按日期查询
		tabConfig : {
			width : 120
		},
		items : [queryByDateForm = Ext.create('Foss.temprentalmark.QueryByDateForm')]
	}],
	listeners:{
		'tabchange':function(tabPanel,newCard,oldCard,eOpts ){
			//按单号
			if(exportType=='QBB'){
				exportType='QBD';
			//按日期
			}else{
				exportType='QBB';
			}
			var grid = scheduling.temprentalmark.mainTabPanel.getActiveTab().down('grid');
			grid.getStore().removeAll();
		}
	}
});


/**
 *
 * 查询列表 快递grid
 *
 * */
var expressGridStore = null;
Ext.define('Foss.temprentalmark.TemprentalTabExpressGrid', {
	extend : 'Ext.grid.Panel',
	frame : true,
	columnLines: true,
	//title : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.title.GoodsList'),//货物列表
	bodyCls : 'autoHeight',
	cls : 'autoHeight',
	height : 450,
	emptyText : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.textfield.empty'),
	constructor: function(config){
		var me = this;
		cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.scheduling.QueryTemprentalMarkExpressStore');
		expressGridStore = me.store;
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
			mode : 'SIMPLE',
			checkOnly : true//限制只有点击checkBox后才能选中行
		});
		me.tbar = [{
			xtype : 'button',
			text : scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.RentalMarkWindow.title'),//租车标记按钮
			disabled : !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkButton'),
			hidden : !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkButton'),
			handler : function(){
				var grid = this.ownerCt.ownerCt;
				dataList= grid.getSelectionModel().getSelection();
				if(dataList.length==0){
					Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"),scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.label.pleaseSelectDatas"));//("提示","请选择数据后操作")
					return;
				}

				dataListSplit(dataList);//把所选数据按单据类型分别存放

				//快递交接单
				repatMarkSigln=noRepeatMarkExpress(dataListExpressHandoverbill);//快递交接单多次标记校验
				
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert('提示','该交接单已经被标记，不能再次标记');//("提示","该交接单已经被标记，不能再次标记")
					return;
				}
				repatMarkSigln=noRepeatMarkExpress(dataListAirPortbill);//快递机场扫描单多次标记校验
				if(repatMarkSigln=='Y'){
					Ext.Msg.alert('提示','该快递机场扫描单已经被标记，不能再次标记');//("提示","该快递机场扫描单已经被标记，不能再次标记")
					return;
				}
				if (dataListExpressWaybill.length > 0 && dataListExpressHandoverbill.length > 0) {
					Ext.Msg.alert('提示','交接单和运单不能同时被标记');//("提示","交接单和运单不能同时被标记")
					return;
				}
				isRepeateMark='N';//初始化多次标记为否
				if(dataListExpressWaybill.length>0){//运单多次标记判断，根据运单号和租车标记部门两个字段判断当前操作部门在3个月内是否多次标记过该运单
					var reBillNo;
					for(i=0;i<dataListExpressWaybill.length;i++){
						if(dataListExpressWaybill[i].get('rentalNum')!=null&&dataListExpressWaybill[i].get('rentalNum')!=''){
							if(dataListExpressWaybill[i].get('markDeptCode')==orgCode){
								var createDate = dataListExpressWaybill[i].get('createDate');
								var myDate = new Date(createDate.getFullYear(),createDate.getMonth()+3,createDate.getDate());
								if (dateFormat(myDate)>dateFormat(new Date())){
									isRepeateMark='Y';
									reBillNo=dataListExpressWaybill[i].get('handoverBillNo');
									break;
								}
							}
						}
					}
					if(isRepeateMark=='N'){//向后台查询数据，看是否已标记
						var wayBillNoList=new Array() ;
						for(i=0;i<dataListExpressWaybill.length;i++){
							wayBillNoList[i] = dataListExpressWaybill[i].get('handoverBillNo');
						}
						Ext.Ajax.request({
							url : scheduling.realPath('wayBillRepeatMark.action'),
							async :  false,//同步调用；
							params : {
								'rentalMarkVo.rentalMarkDto.wayBillNoList':wayBillNoList,
								'rentalMarkVo.rentalMarkDto.orgCode':orgCode
							},
							success : function(response) {
								var result = Ext.decode(response.responseText);
								isRepeateMark=result.rentalMarkVo.isrepeatMark;

							},
							exception:function (response){
								Ext.Msg.alert(scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt"), scheduling.temprentalmark.i18n("foss.scheduling.temprentalmark.prompt.validateFailed"));//('提示','小票单号查询失败')
							}
						});

					}
					if(isRepeateMark=='Y'){
						Ext.MessageBox.confirm(scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt'),scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.prompt.ifReMarkAgain'),function(button){
							if(button=='yes'){
								rentalMarkExpress();//执行点击租车标记后续操作
							}else{
								return ;
							}
						});

					}else{
						rentalMarkExpress();//执行点击租车标记后续操作
					}
				}else{
					rentalMarkExpress();//执行点击租车标记后续操作
				}

			}
		},{
			xtype : 'button',
			text : '导出',
			hidden: !scheduling.temprentalmark.isPermission('scheduling/temprentalmarkExportButton'),
			handler : function(){

				if(exportType=='QBB'){//如果按单号查询导出，导出单号对应条件的数据
					var form=queryByBillNumberForm.getForm();
				}else if(exportType=='QBD'){
					var form=queryByDateForm.getForm();
				}
				if(!form.isValid()){
					return;
				}
				exportMakeData(form,exportType);
			}
		}],
			me.bbar = Ext.create('Deppon.StandardPaging',{
				store : me.store,
				pageSize : 50,
				maximumSize : 50,
				plugins : Ext.create('Deppon.ux.PageSizePlugin', {
					sizeList : [['50', 50], ['100', 100], ['500', 500]]
				})
			});
		scheduling.temprentalmark.pagingBar1 = me.bbar;
		me.callParent([ cfg ]);
	},
	dockedItems: [{
		xtype: 'toolbar',
		dock: 'bottom',
		layout : 'column',
		defaults: {
			xtype : 'textfield',
			readOnly : true,
			labelWidth : 60
		},
		items: [{
			fieldLabel : '总条数',
			xtype : 'textfield',
			readOnly : true,
			columnWidth : 0.2,
			value: 0,
			id : 'gridExpressTotalCount'
		}]
	}],
	columns:[{
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalNum'),//租车编号
		dataIndex: 'rentalNum',
		width: 100
	},{
		header:'租车金额',//租车金额
		dataIndex: 'rentalAmount',
		width: 100
	},{
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billNo'),//单号
		dataIndex: 'handoverBillNo'
	},{
		//269701--lln--2015-09-01 begin
		//区分零担与快递
		header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.isExpress'),//是否快递
		dataIndex:'isExpressWayBill',//是否快递
		hidden:true
		//269701--lln--2015-09-01 end
	},
		{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billType'),//单据类型
			dataIndex: 'billType',
			renderer:function(value){
				if(value=='expresshandoverbill'){
					return '快递交接单';
				} else if(value=='expresswaybill'){
					return '快递运单'
			    } else if(value=='airPortBill'){
					return '短途交接单'
				} else{
					return value;
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.vehicleNo'),//车牌号
			dataIndex: 'vehicleNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.consultPriceNo'),//询价编号///////////////
			dataIndex: 'consultPriceNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.inviteCarNum'),//约车编号
			dataIndex: 'inviteVehicleNo'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.driverName'),//司机
			dataIndex: 'driverName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.origOrgName'),//出发部门
			dataIndex: 'departOrgName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destOrgName'),//到达部门
			dataIndex: 'arriveOrgName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.origOrgCode'),//出发部门编码
			dataIndex: 'departOrgCode'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destOrgCode'),//到达部门编码
			dataIndex: 'arriveOrgCode'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.weight'),//重量
			dataIndex: 'totalWeight'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.volume'),//体积
			dataIndex: 'totalVolumn'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.goodsName'),//货物品名
			dataIndex: 'goodsName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.isDoorDeliver'),//是否上门接货
			dataIndex: 'isDoorDeliver',
			renderer:function(value){
				if(value=='N'){
					return '否';
				}else if(value=='Y'){
					return '是';
				}else{
					return value;
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.customerName'),//发货客户名称
			dataIndex: 'customerName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.sendAddress'),//发货地址
			dataIndex: 'sendAddress'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.destination'),//目的站
			dataIndex: 'destination'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.pickUpWayCode'),//提货方式
			dataIndex: 'pickUpWay',
			hidden:true
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.pickUpWayName'),//提货方式名称
			dataIndex: 'pickUpWayName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.receiptContacts'),//收货联系人
			dataIndex: 'receiptContacts'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.receiptAddress'),//收货地址
			dataIndex: 'receiptAddress'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.billCreateTime'),//单据生成时间
			dataIndex: 'createTime',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),value.getDate(),value.getHours(),value.getMinutes(),value.getSeconds()),'Y-m-d H:i:s');

				}

			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.rentalUse'),//租车用途
			dataIndex: 'rentalUse',
			renderer:function(value){
				if(value!=null){
					if(value=='SH'){
						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.send');//送货
					}else if(value=='JH'){
						return  scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.receive');//接货
					}else if(value=='ZH'){

						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.transfer');//转货
					}else if(value=='JSH'){

						return scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.options.deliver');//接送货
					}else {

						return value;
					}
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.createDate'),//租车标记时间
			dataIndex: 'createDate',
			renderer:function(value){
				if(value!=null){
					return Ext.Date.format(new Date(value.getFullYear(),value.getMonth(),
						value.getDate()),'Y-m-d');
				}
			}
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markDeptName'),//租车标记部门
			dataIndex: 'markDeptName'
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markDeptCode'),//租车标记部门编码
			dataIndex: 'markDeptCode',
			hidden:true
		},{
			header: scheduling.temprentalmark.i18n('foss.scheduling.temprentalmark.label.markOperator'),//租车标记操作人
			dataIndex: 'markOperator'
		}]
});

// TODO  快递Model
Ext.define('Foss.scheduling.temprentalmark.QueryTemprentalMarkExpressModel', {
	extend : 'Ext.data.Model',
	//定义字段
	fields : [{
		name : 'rentalNum',//租车编号
		type : 'string'
	},{
		name : 'rentalAmount',//租车金额
		type : 'string'
	},{
		name : 'handoverBillNo',//单号
		type : 'string'
	},{
		name : 'billType',//单据类型
		type : 'string'
	},{
		name : 'vehicleNo',//车牌号
		type : 'string'
	},{
		name : 'consultPriceNo',//询价编号
		type : 'string'
	},{
		name : 'inviteVehicleNo',//约车编号
		type : 'string'
	},{
		name : 'driverName',//司机
		type : 'string'
	},{
		name : 'departOrgName',//出发部门
		type : 'string'
	},{
		name : 'arriveOrgName',//到达部门
		type : 'string'
	},{
		name : 'departOrgCode',//出发部门编码
		type : 'string'
	},{
		name : 'arriveOrgCode',//到达部门编码
		type : 'string'
	},{
		name : 'totalWeight',//重量
		type : 'string'
	},{
		name : 'totalVolumn',//体积
		type : 'string'
	},{
		name : 'goodsName',//货物名称
		type : 'string'
	},{
		name : 'isDoorDeliver',//是否上门接货
		type : 'string'
	},{
		name : 'customerName',//发货客户名称
		type : 'string'
	},{
		name : 'sendAddress',//发货地址
		type : 'string'
	},{
		name : 'destination',//目的站
		type : 'string'
	},{
		name : 'pickUpWay',//提货方式
		type : 'string'
	},{
		name : 'pickUpWayName',//提货方式名称
		type : 'string'
	},{
		name : 'receiptContacts',//收货联系人
		type : 'string'
	},{
		name : 'receiptAddress',//收货地址
		type : 'string'
	},{
		name : 'createTime',//单据生成时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'rentalUse',//租车用途
		type : 'string'
	},{
		name : 'createDate',//租车标记时间
		type : 'date',
		convert: dateConvert
	},{
		name : 'markDeptName',//租车标记部门
		type : 'string'
	},{
		name : 'markDeptCode',//租车标记部门编码
		type : 'string'
	},{
		name : 'markOperator',//租车标记操作人
		type : 'string'
	},{//269701--lln--2015-09-01 begin
		name : 'isExpress',//是否快递
		type : 'string'
		//269701--lln--2015-09-01 end
	}]
});

/**
 *
 * 查询store 快递
 * */
Ext.define('Foss.scheduling.QueryTemprentalMarkExpressStore', {
	extend : 'Ext.data.Store',
	pageSize : 50,
	// 绑定一个模型
	model : 'Foss.scheduling.temprentalmark.QueryTemprentalMarkExpressModel',
	// 定义一个代理对象
	proxy : {
		type : 'ajax',
		actionMethods:'POST',
		timeout:300000,
		// 请求的url
		url :scheduling.realPath('temprentalmarkQuery.action'),
		// 定义一个读取器
		reader : {
			// 以JSON的方式读取
			type : 'json',
			// 定义读取JSON数据的根对象
			root : 'rentalMarkVo.expressMarkEntityList',
			successProperty: 'success',
			totalProperty : 'totalCount'
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	},
	listeners : {
		'beforeload' : function(store, operation, eOpts){

			if(scheduling.temprentalmark.departmentSignle!=null){
				orgCode = scheduling.temprentalmark.departmentSignle.split('_')[1];
				departmentSignle = scheduling.temprentalmark.departmentSignle.split('_')[0];
			}
			if(queryType == scheduling.temprentalmark.QUERY_BY_DATE){
				var queryParams = queryByDateForm.getForm().getValues();
				Ext.apply(operation, {
					params : {
						'rentalMarkVo.rentalMarkDto.billGenerationBeginTime' : Ext.Date.parse(queryParams.billGenerationBeginTime,'Y-m-d H:i:s'),//单据生成开始时间
						'rentalMarkVo.rentalMarkDto.billGenerationEndTime' : Ext.Date.parse(queryParams.billGenerationEndTime,'Y-m-d H:i:s'),//单据生成结束时间
						'rentalMarkVo.rentalMarkDto.vehicleNo' : queryParams.vehicleNo,//车牌号
						'rentalMarkVo.rentalMarkDto.borrowNo' : queryParams.borrowNo,//租车编号
						'rentalMarkVo.rentalMarkDto.isWayBill' : queryParams.isWayBill,//是否零担运单
						'rentalMarkVo.rentalMarkDto.isHandoverBill' : queryParams.isHandoverBill,//是否交接单
						'rentalMarkVo.rentalMarkDto.isDeliverBill' : queryParams.isDeliverBill,//是否派送单
						'rentalMarkVo.rentalMarkDto.isStowageBill' : queryParams.isStowageBill,//是否配载单
						'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
						'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
						'rentalMarkVo.rentalMarkDto.queryType' : queryType,//查询类型
						'rentalMarkVo.rentalMarkDto.createDept':queryParams.createDept,//单据创建部门
						'rentalMarkVo.rentalMarkDto.isHandoverEirBill': queryParams.isHandoverEirBill, //是否快递交接单
						'rentalMarkVo.rentalMarkDto.isExpressWayBill': queryParams.isExpressWayBill,//是否快递运单
						'rentalMarkVo.rentalMarkDto.isAirPortBillList': queryParams.isAirPortBillList,//是否快递航空
						'rentalMarkVo.rentalMarkDto.type': 2  //判断零担还是快递的     type=1为零担    2为快递
						
					}
				});
			}else if(queryType==scheduling.temprentalmark.QUERY_BY_BILL){
				var queryParams = queryByBillNumberForm.getForm().getValues();
				//269701--lln--2015-08-31 begin
				//零担运单号
				var breakbulkWaybillNoList = billNoList(queryParams.breakbulkWaybillNoList);
				//快递运单号
				var expressWaybillNoList = billNoList(queryParams.expressWaybillNoList);
				//269701--lln--2015-08-31 end
				//交接单号
				var handoverBillNoList = billNoList(queryParams.handoverBillNoList);
				//配载单号
				var deliverbillNoList = billNoList(queryParams.deliverbillNoList);
				//派送单号
				var stowageBillNoList = billNoList(queryParams.stowageBillNoList);
				// 快递交接单  313352 gouyangyang
				var expressBillNoList = billNoList(queryParams.expressBillNoList);
				// 快递航空  313352 gouyangyang
				var expressAirportNoList = billNoList(queryParams.expressAirportNoList);
				Ext.apply(operation, {
					params : {
						//269701--lln--2015-08-31 begin
						'rentalMarkVo.rentalMarkDto.wayBillNoList' : breakbulkWaybillNoList,//零担运单号
						'rentalMarkVo.rentalMarkDto.expressWaybillNoList' : expressWaybillNoList,//快递运单号
						//269701--lln--2015-08-31 end
						'rentalMarkVo.rentalMarkDto.handoverBillNoList' : handoverBillNoList,//交接单号
						'rentalMarkVo.rentalMarkDto.deliverbillNoList' : deliverbillNoList,//派送单号
						'rentalMarkVo.rentalMarkDto.stowageBillNoList':stowageBillNoList,//配载单号
						'rentalMarkVo.rentalMarkDto.departmentSignle' : departmentSignle,//部门标识
						'rentalMarkVo.rentalMarkDto.expressBillNoList':expressBillNoList,//快递交接单   313352 gouyangyang
						'rentalMarkVo.rentalMarkDto.orgCode' : orgCode,//部门编码
						'rentalMarkVo.rentalMarkDto.queryType' : queryType,//查询类型
						'rentalMarkVo.rentalMarkDto.expressAirportNoList':expressAirportNoList,//快递航空单
						'rentalMarkVo.rentalMarkDto.type': 2  //判断零担还是快递的     type=1为零担    2为快递
					}
				});

			}
		},
		'datachanged' : function(store){
			Ext.getCmp('gridExpressTotalCount').setValue(store.getTotalCount());
		}
	}
});
//TODO
Ext.define('Foss.temprentalmark.TemprentalTabPanel', {
	extend : 'Ext.tab.Panel',
	cls : "innerTabPanel",
	bodyCls : "overrideChildLeft",
	activeTab : 0,
	autoScroll : false,
	frame : false,
	items : [{
		tabConfig : {
			width : 100
		},
		itemId : 'temprentalTabGridID',
		title : '零担列表', //零担列表
		items : Ext.create('Foss.temprentalmark.TemprentalTabGrid')
	}, {
		tabConfig : {
			width : 100
		},
		itemId : 'temprentalTabExpressGridID',
		title : '快递列表', //快递列表
		items : Ext.create('Foss.temprentalmark.TemprentalTabExpressGrid')
	}],
	listeners : {
		'tabchange' : function(tabPanel,newCard,oldCard,eOpts ){
		}
	}
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	scheduling.temprentalmark.queryTabForm  = Ext.create('Foss.temprentalmark.QueryTemprentalTab');//查询TAB
	scheduling.temprentalmark.mainTabPanel = Ext.create("Foss.temprentalmark.TemprentalTabPanel");
	Ext.create('Ext.panel.Panel',{
		id: 'T_scheduling-temprentalmarkIndex_content',
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		layout: 'auto',
		items: [scheduling.temprentalmark.queryTabForm,scheduling.temprentalmark.mainTabPanel],
		renderTo: 'T_scheduling-temprentalmarkIndex-body'
	});
});