airfreight.airQueryWaybill.choseMulitRecord=new Array();
//航班号
Ext.define('Foss.commonSelector.FlightSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonflightselector',
//	fieldLabel : '航班号',
	displayField : 'flightNo',// 显示名称
	valueField : 'flightNo',// 值
	active : 'Y',
	flightSort : null,//航班类别
	airLines : null,//航空公司
	origCode : null,//始发站代码
	targetCode:null,//目的站代码 
	queryParam : 'flightVo.flightDto.flightNo',// 查询参数
	showContent : '{flightNo}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.baseinfo.commonSelector.FlightStore'); 
		me.active =config.active;
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = operation.params;
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
			if (!Ext.isEmpty(me.flightSort)) {
				searchParams['flightVo.flightDto.flightSort'] = me.flightSort;
			}
			if (!Ext.isEmpty(me.airLines)) {
				searchParams['flightVo.flightDto.airlines'] = me.airLines;
			}
			if (!Ext.isEmpty(me.origCode)) {
				searchParams['flightVo.flightDto.origCode'] = me.origCode;
			}
//			if (!Ext.isEmpty(me.targetCode)) {
//				searchParams['flightVo.flightDto.targetCode'] = me.targetCode;
//			} 
			//始发站代码列表
			if (!Ext.isEmpty(airfreight.airQueryWaybill.origCodes)) {
				var origCodes = airfreight.airQueryWaybill.origCodes.split(',');
				searchParams['flightVo.flightDto.origCodes'] = origCodes;
			}
			searchParams['flightVo.flightDto.active'] = me.active;
		})
		me.callParent([cfg]);
	}
});

//显示机场市县名称机场		
Ext.define('Foss.commonSelector.AirportWithCityNameSelector', {
			extend : 'Foss.commonSelector.CommonCombSelector',
			alias : 'widget.commonairporwithcitynametselector',
			displayField : 'airportName',// 显示名称
			valueField : 'cityName',// 值
			queryParam : 'airportEntity.queryDistrictParam',// 查询参数
			showContent : '{cityName}&nbsp;&nbsp;&nbsp;{countyName}',// 显示表格列
			constructor : function(config) {
				var me = this, cfg = Ext.apply({}, config);
				me.store = Ext
						.create('Foss.baseinfo.commonSelector.AirportStore');
				me.callParent([cfg]);
			}
		});	

//绑定运单明细
airfreight.airQueryWaybill.bindingWaybillDetail = function(record){
		//待提交运单明细列表
	   var waybillNostr = '';
	   var gridStore = airfreight.airQueryWaybill.waybillInfo.store.data.items;
	   var existsRecord = new Array();
	   if(gridStore.length>0){
		   for(var i=0;i<record.length;i++){
			   var waybillNo = record[i].data.waybillNo;
			   for(var j=0;j<gridStore.length;j++){
				   if(waybillNo == gridStore[j].data.waybillNo){
				   	   existsRecord.push(record[i]);
					   waybillNostr = waybillNostr + '[' +waybillNo+ ']';
				   } 
			   }
	       }
	   }
	   //当选择合票的运单已经存在正单明细中时，给予提示是否确认合票
	   if(waybillNostr.length>0){
	        Ext.Msg.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo')+ waybillNostr
								+ airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.existInDetail'), function(btn,text){
				if(btn == 'yes'){
					airfreight.airQueryWaybill.waybillInfo.store.remove(existsRecord);
					airfreight.airQueryWaybill.executeBindingWaybillDetail(record);
				}else{
					return false;	
				}
			})
	   }else{
	       airfreight.airQueryWaybill.executeBindingWaybillDetail(record);
	   }
}

//绑定运单明细
airfreight.airQueryWaybill.executeBindingWaybillDetail = function(record){
    airfreight.airQueryWaybill.hpWindow.close();
	airfreight.airQueryWaybill.waybillInfo.store.add(record);
    var recordLength = airfreight.airQueryWaybill.waybillInfo.store.data.items;
    var volumeTotal = 0; //总体积
    var goodsQtyTotal = 0; //总件数
    var billingWeightTotal = 0; //总计费重量
    var grossWeightTotal = 0; //总毛重
    var goodsNameStr = '';
    var goodsName = '';
    var goodsNameMap = new Ext.util.HashMap();
    for(var j = 0 ;j<recordLength.length; j++){
	    volumeTotal = volumeTotal + Ext.Number.from(recordLength[j].data.volume,0);
	    goodsQtyTotal = goodsQtyTotal + Ext.Number.from(recordLength[j].data.goodsQty,0);
	    billingWeightTotal = billingWeightTotal + Ext.Number.from(recordLength[j].data.billingWeight,0);
	    grossWeightTotal = grossWeightTotal + Ext.Number.from(recordLength[j].data.grossWeight,0);
	    goodsName =  recordLength[j].data.goodsName;
	   	    //如果第一行
		   if(j==0){
			   goodsNameStr = goodsNameStr + goodsName; //拼接货物名称
			   goodsNameMap.add(goodsName,goodsName);
		   }else{
		   	   if(!goodsNameMap.get(goodsName)){
		   	   	   goodsNameStr = goodsNameStr + ' ' + goodsName;  //拼接' '+货物名称
		   	   	   goodsNameMap.add(goodsName,goodsName);
		   	   }
		   }
    }
    //设置总件数
    airfreight.airQueryWaybill.airwayBillRecord.data.goodsQty = airfreight.airQueryWaybill.fomatFloat(goodsQtyTotal,0);
    //设置总体积
    airfreight.airQueryWaybill.airwayBillRecord.data.volume = airfreight.airQueryWaybill.fomatFloat(volumeTotal,2); 
    //设置毛重
    airfreight.airQueryWaybill.airwayBillRecord.data.grossWeight = airfreight.airQueryWaybill.fomatFloat(grossWeightTotal,1);
    //设置计费重量
    if((volumeTotal*1000)/6>grossWeightTotal){
	    airfreight.airQueryWaybill.airwayBillRecord.data.billingWeight = airfreight.airQueryWaybill.fomatFloat((volumeTotal*1000)/6,1);
    }else{
	    airfreight.airQueryWaybill.airwayBillRecord.data.billingWeight = airfreight.airQueryWaybill.fomatFloat(grossWeightTotal, 1); 
    }
    airfreight.airQueryWaybill.airwayBillRecord.data.goodsName = goodsNameStr;
    airfreight.airQueryWaybill.updateCurrentAirwayBillForm.loadRecord(airfreight.airQueryWaybill.airwayBillRecord);
    airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('agencyName_wf').setValue(airfreight.airQueryWaybill.airwayBillRecord.data.agencyName);
    airfreight.airQueryWaybill.waybillJointTicketInfo.store.remove(record);
    airfreight.airQueryWaybill.airwaybillCalculate(airfreight.airQueryWaybill.airwayBillRecord);
}

//处理新增删除流水号
airfreight.airQueryWaybill.addAirwayBillSerialNoDetail = function(src,id,flag){
	var waybillNo = airfreight.airQueryWaybill.currentWaybillNo;
	var currentWaybillId = airfreight.airQueryWaybill.currentWaybillId;
	var keybillNoOrId = waybillNo + currentWaybillId;
	var modifyWeightVolumnMap = airfreight.airQueryWaybill.modifyAirwayBillWeightVolumnMap;
	var addSerailNoArray = new Array();
	var delSerailNoArray = new Array();
	var viewArray = new Array();
	var viewMap = airfreight.airQueryWaybill.viewMap;
	if(!src && !id){
		return false;
	}
	//当链接显示为蓝色时可以点击
	var operateFlag = src.substring(0,3);
	var serNo = src.substring(3,src.length)
	var srcDoc = document.getElementById(operateFlag+id+serNo);
	var srcStyle = srcDoc.style.color;
	if(srcStyle != 'blue'){
		return false;
	}
	 if(!src.indexOf('add')) {
		serialNo = src.substring(src.indexOf('add')+3,src.length);
		srcb = 'del' + id + serialNo;
		srcg = 'add' + id + serialNo;
		var addSerialNoMap = airfreight.airQueryWaybill.addSerialNoMap;
		var delSerialNoMap = airfreight.airQueryWaybill.delSerialNoMap;
		addSerialNoMap.add(keybillNoOrId, airfreight.airQueryWaybill.addAirSerialNoList);
		delSerialNoMap.add(keybillNoOrId, airfreight.airQueryWaybill.delAirSerialNoList);
		var airTotalCountMap = airfreight.airQueryWaybill.airTotalCountMap;
		if(airTotalCountMap.get(keybillNoOrId)){
			var count = airTotalCountMap.get(keybillNoOrId);
			airTotalCountMap.add(keybillNoOrId,count+1);
			airfreight.airQueryWaybill.airTotalCountMap = airTotalCountMap;
		}
		if(flag == 'add'){//需要添加的流水号
			if(addSerialNoMap.get(keybillNoOrId)){
				var addArray = addSerialNoMap.get(keybillNoOrId);
				for(var a = 0; a<addArray.length; a++){
					if (addArray[a].serialNo == serialNo){
						if(addArray[a].operateFlag = 'N' && addArray[a].viewFlag=='Y'){
							addArray[a].operateFlag = 'Y';
						}else if(addArray[a].operateFlag = 'Y' && addArray[a].viewFlag=='N'){
							addArray[a].operateFlag = 'N';
						}
						addSerailNoArray.push(addArray[a]);
					}else{
						addSerailNoArray.push(addArray[a]);
					}
				}
				addSerialNoMap.removeAtKey(keybillNoOrId);
				addSerialNoMap.add(keybillNoOrId,addSerailNoArray);
				airfreight.airQueryWaybill.addSerialNoMap = addSerialNoMap;
			}
		}else{
			if(delSerialNoMap.get(keybillNoOrId)){
				var delArray = delSerialNoMap.get(keybillNoOrId);
				for(var b = 0; b<delArray.length; b++){
					if (delArray[b].serialNo == serialNo){
						if(delArray[b].operateFlag = 'Y' && delArray[b].viewFlag=='N'){
							delArray[b].operateFlag = 'N';
						}else if(delArray[b].operateFlag = 'N' && delArray[b].viewFlag=='Y'){
							delArray[b].operateFlag = 'Y';
						}
						delSerailNoArray.push(delArray[b]);
					}else{
						delSerailNoArray.push(delArray[b]);
					}
				}	
				delSerialNoMap.add(keybillNoOrId,delSerailNoArray);
				airfreight.airQueryWaybill.delSerialNoMap = delSerialNoMap;
			}
		}
		var list = viewMap.get(keybillNoOrId);
		for(var c =0 ; c<list.length; c++){
			if(list[c].flag=='add'){
				if(list[c].serialNo == serialNo){
					list[c].viewFlag == 'N'?list[c].viewFlag='Y':list[c].viewFlag='N';
					viewArray.push(list[c]);
				}else{
					viewArray.push(list[c]);
				}
			}else{
				if(list[c].serialNo == serialNo){
					list[c].viewFlag == 'Y'?list[c].viewFlag='N':list[c].viewFlag='Y';
					viewArray.push(list[c]);
				}else{
					viewArray.push(list[c]);
				}
			}
		}
		viewMap.add(keybillNoOrId,viewArray);
		airfreight.airQueryWaybill.viewMap = viewMap; 
		//待配载件数
//		var serialNoCounts = airfreight.airQueryWaybill.currentOperateWeightTotal;
//		//总件数
//		var serialNoTotals = airfreight.airQueryWaybill.currentOperateVolumeTotal;
	} else {
		var serialNo = src.substring(src.indexOf('del')+3,src.length);
		var total = airfreight.airQueryWaybill.serialNoTotals;
		//统计页面删除流水个数，如果还剩下最后一条，不允许删除，提示最低保存一件流水
		var viewList = viewMap.get(keybillNoOrId);
		var remainItemCount = 0;
		for(var c =0 ; c<viewList.length; c++){
			if(viewList[c].viewFlag == 'Y'){
				remainItemCount++;
			}
		}
		if(remainItemCount == total-1){
			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
					airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.leastOneSerial'));
			return false;
		}
		srcb = 'add' + id + serialNo;
		srcg = 'del' + id + serialNo;
		var addSerialNoMap = airfreight.airQueryWaybill.addSerialNoMap;
		var delSerialNoMap = airfreight.airQueryWaybill.delSerialNoMap;
		addSerialNoMap.add(keybillNoOrId, airfreight.airQueryWaybill.addAirSerialNoList);
		delSerialNoMap.add(keybillNoOrId, airfreight.airQueryWaybill.delAirSerialNoList);
		airfreight.airQueryWaybill.modifyAirwayBillWeightVolumnMap = modifyWeightVolumnMap;
		if(flag == 'add'){//需要添加的流水号
			if(addSerialNoMap.get(keybillNoOrId)){
				var addArray = addSerialNoMap.get(keybillNoOrId);
				for(var a = 0; a<addArray.length; a++){
					if (addArray[a].serialNo == serialNo){
						addArray[a].operateFlag = 'N';
						addSerailNoArray.push(addArray[a]);
					}else{
						addSerailNoArray.push(addArray[a]);
					}
				}
				addSerialNoMap.add(keybillNoOrId,addSerailNoArray);
				airfreight.airQueryWaybill.addSerialNoMap = addSerialNoMap;
			}
		}else{
			if(delSerialNoMap.get(keybillNoOrId)){
				var delArray = delSerialNoMap.get(keybillNoOrId);
				for(var b = 0; b<delArray.length; b++){
					if (delArray[b].serialNo == serialNo){
						delArray[b].operateFlag = 'N';
						delSerailNoArray.push(delArray[b]);
					}else{
						delSerailNoArray.push(delArray[b]);
					}
				}
				delSerialNoMap.add(keybillNoOrId,delSerailNoArray);
				airfreight.airQueryWaybill.delSerialNoMap = delSerialNoMap;
			}	
		}
		var list = viewMap.get(keybillNoOrId);
		for(var c =0 ; c<list.length; c++){
			if(list[c].flag=='add'){
				if(list[c].serialNo == serialNo){
					list[c].viewFlag == 'N'?list[c].viewFlag ='Y':list[c].viewFlag='N';
					viewArray.push(list[c]);
				}else{
					viewArray.push(list[c]);
				}
			}else{
				if(list[c].serialNo == serialNo){
					list[c].viewFlag == 'Y'?list[c].viewFlag ='N':list[c].viewFlag='Y';
					viewArray.push(list[c]);
				}else{
					viewArray.push(list[c]);
				}
			}
		}
		var airTotalCountMap = airfreight.airQueryWaybill.airTotalCountMap;
		if(airTotalCountMap.get(keybillNoOrId)){
			var count = airTotalCountMap.get(keybillNoOrId);
			airTotalCountMap.add(keybillNoOrId,count-1);
			airfreight.airQueryWaybill.airTotalCountMap = airTotalCountMap;
		}
		viewMap.add(keybillNoOrId,viewArray);
		airfreight.airQueryWaybill.viewMap = viewMap; 
	}
	    var docb = document.getElementById(srcb);
		var docg = document.getElementById(srcg);
		var docmentAdd = document.getElementsByName(srcb);
		var docmentDel = document.getElementsByName(srcg);
		for(var i = 0; i < docmentAdd.length; i++) {
			var add = docmentAdd[i];
			var operatorType = src.substring(0,3);
			for(var j = 0; j < docmentDel.length; j++) {
				var del = docmentDel[j];
				del.style.color="gray";
			}
			add.style.color="blue";
		}
		//保存当前所操作的id对象的流水号样式
		airfreight.airQueryWaybill.modifyAirwayBillWeightVolume(airfreight.airQueryWaybill.delSerialNoMap,keybillNoOrId);
}

//调用方法修改毛重,体积
airfreight.airQueryWaybill.modifyAirwayBillWeightVolume = function(map,keybillNoOrId){
	 var waybillNo = airfreight.airQueryWaybill.currentWaybillNo;
	 var goodsWeightTotal = airfreight.airQueryWaybill.modifyOriginalWeightMap.get(waybillNo);
	 var goodsVolumeTotal = airfreight.airQueryWaybill.modifyOriginalVolumeMap.get(waybillNo);
	 var total = airfreight.airQueryWaybill.serialNoTotals;
	 var countDelete = airfreight.airQueryWaybill.airTotalCountMap.get(keybillNoOrId);
	 //总重量
	 var weight = (countDelete/total)*goodsWeightTotal;
	 //总体积
	 var volume = (countDelete/total)*goodsVolumeTotal;
	 var recordForm = airfreight.airQueryWaybill.currentOperatewaybillNo.createForm().getForm();
 	 recordForm.findField('goodsWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(weight,1));
 	 recordForm.findField('goodsVolumeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(volume,2));
 	 if((volume*1000)/6>weight){
       var billingWeight = airfreight.airQueryWaybill.fomatFloat((volume*1000)/6,1);
       recordForm.findField('goodsBillingWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(billingWeight,1));
     }else{
       var billingWeight = airfreight.airQueryWaybill.fomatFloat(weight,1); 
       recordForm.findField('goodsBillingWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(billingWeight,1));
	 }
 	 recordForm.findField('goodsBillingWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(billingWeight,1));
	 airfreight.airQueryWaybill.currentOperatewaybillNo.createForm().loadRecord(recordForm);
	 return recordForm;
}
//数字运算后保留小数点后一位
airfreight.airQueryWaybill.fomatFloat = function(src,pos){      
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);      
}   
//遍历待选信息是否已在合票信息、运单明细列表中
airfreight.airQueryWaybill.vtype =  function (record){
	var recordMap = new Ext.util.HashMap();
	for(var i=0;i<record.length;i++){
		recordMap.add(record[i].data.id,record)
	}
}
/**
 *  //处理所有费用相关
 *  1、  航空运费：计费重量*运价，当航空运费小于航空公司费率基础资料的最低收费时，航空运费等于基础资料最低收费；当航空运费大于且等于航空公司费率基础资料的最低收费时，航空运费保持不变；小数点后保留2位；
 *  2、	地面运费：计费重量*地面运输费率，当地面运费小于基础资料里最低搬运费时，地面运费等于基础资料最低搬运费；当地面运费大于等于基础资料里最低搬运费时，地面运费保持不变；小数点后保留2位；
 *  3、	燃油附加税：计费重量*燃油附加费率，当燃油附加税小于基础资料最低燃油费时，燃油附加税等于基础资料最低燃油费；当燃油附加税大于且等于基础资料最低燃油费时，燃油附加税保持不变；
 *  4、	保险费：保险费计算方式为：保险费=运输保险*保险费率
 *  5、	附加费：手动输入；
 *  6、	运输保险：手动输入；
 *  7、	总金额：航空运费+地面运费+保险费+燃油附加税+制单费，总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位；
 *  8、	费用说明来源基础资料，打开录入航空正单界面时，根据当前总调部门，得到相应的费用说明信息；
 *  9、	总金额和航空运费、地面运费、燃油附加税、保险费不可以编辑，附加费可以手动修改；
 *  10、	当配载类型为单独开单外发和合大票外发时，制单费变为可编辑状态，否则不可编辑且为空；
 */
airfreight.airQueryWaybill.airwaybillCalculate = function (record){
	var recordCalcuLate = record.data;
	var priceRate = airfreight.airQueryWaybill.queryMinRate(record);//运价
	var updateCurrentAirwayBillForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	var airLineTwoletter = updateCurrentAirwayBillForm.findField('airLineTwoletter').getValue();
	var curFlightNo = updateCurrentAirwayBillForm.findField('flightNo').getValue();
	if(airLineTwoletter!='' && curFlightNo!=''){
		var airFee = 0;		//航空运费
		var inseranceFee = 0;//保险费
		var groundFee = 0;  //地面运费
		var fuelSurcharge = 0;//燃油附加税
		var feeTotal = 0;
		//运输保险
		var transportInsurance = Ext.Number.from(updateCurrentAirwayBillForm.findField('transportInsurance').getValue(),0);
		//设置制单费
		var billingFee = Ext.Number.from(updateCurrentAirwayBillForm.findField('billingFee').getValue(),0);
		//设置附加费
		var extraFee = Ext.Number.from(updateCurrentAirwayBillForm.findField('extraFee').getValue(),0);
		//计费重量
		var billingWeight = Ext.Number.from(updateCurrentAirwayBillForm.findField('billingWeight').getValue(),0);
		
		
		//附加费、地面运费、燃油附加税、保险费   的费率信息
		var airlinesValueAddEntity = airfreight.airQueryWaybill.airlinesValueAddEntity;
		//判断配载类型是否外发，如果外发则总金额等于航空运费+制单费用
		//wqh
		var airAssembleType = updateCurrentAirwayBillForm.findField('airAssembleType').getValue();
		if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF')
		{
			feeTotal = airFee+billingFee;
			updateCurrentAirwayBillForm.findField('extraFee').setValue(0);
			updateCurrentAirwayBillForm.findField('inseranceFee').setValue(0);
			updateCurrentAirwayBillForm.findField('transportInsurance').setValue(0);
			
		}else{
			
			if(airlinesValueAddEntity!=null && airlinesValueAddEntity.id!=null){
				var dmysfl = airlinesValueAddEntity.groundTrsFee;//地面运输费率
				var ryfjfl = airlinesValueAddEntity.oilAddFee;//燃油附加费率
				var bxfl = airlinesValueAddEntity.insuranceFee;//保险费率
				
				//计算地面运费2表示地面费率
				groundFee = billingWeight*dmysfl;
				//当地面运输费小于基础资料中提供的最低地面运输费时默认为基础资料中的最低地面运输费
				if(groundFee < airlinesValueAddEntity.minGroundTrsFee){
					groundFee = airlinesValueAddEntity.minGroundTrsFee;
				}
				//计算燃油附加税3表示燃油附加费率
				fuelSurcharge = billingWeight*ryfjfl;
				//当燃油附加费小于基础资料中的最低燃油费时默认为基础资料中的最低燃油附加费
				if(fuelSurcharge < airlinesValueAddEntity.minOilAddFee){
					fuelSurcharge = airlinesValueAddEntity.minOilAddFee;
				}
				//计算保险费用 300 保险费为航空公司费率中的基础资料的最低保险；小数点后保留2位；
				if(transportInsurance*bxfl < airlinesValueAddEntity.minInsuranceFee){
					inseranceFee = airlinesValueAddEntity.minInsuranceFee;
				}else{
					inseranceFee = transportInsurance*bxfl;
				}
				//设置总金额
				feeTotal = airFee + groundFee + inseranceFee + fuelSurcharge + billingFee;
				
			}
		}
		//总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
		//大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位
		  if(airlinesValueAddEntity!=null && airlinesValueAddEntity.id!=null){
			  if(feeTotal<airlinesValueAddEntity.minTotalFee){
					feeTotal = airlinesValueAddEntity.minTotalFee;
				}
		  }
		
		//给record赋值
		recordCalcuLate.airFee= airFee;
		recordCalcuLate.groundFee= groundFee;
		recordCalcuLate.fuelSurcharge= fuelSurcharge;
		recordCalcuLate.inseranceFee= inseranceFee;
		recordCalcuLate.extraFee= extraFee;
		recordCalcuLate.billingFee= billingFee;
		recordCalcuLate.feeTotal= feeTotal;
		updateCurrentAirwayBillForm.findField('fee').setValue(Ext.Number.from(priceRate,0));
		updateCurrentAirwayBillForm.findField('airFee').setValue(airFee);
		updateCurrentAirwayBillForm.findField('groundFee').setValue(groundFee.toFixed(2));
		updateCurrentAirwayBillForm.findField('fuelSurcharge').setValue(fuelSurcharge.toFixed(2));
		updateCurrentAirwayBillForm.findField('inseranceFee').setValue(inseranceFee.toFixed(2));
		updateCurrentAirwayBillForm.findField('feeTotal').setValue(Ext.Number.from(feeTotal,0).toFixed(2));
	}else{
			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
							airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.pleaseSelectFlight'));
		}
	//运价
	if(!Ext.isEmpty(priceRate) && priceRate!=0){
		airFee = airfreight.airQueryWaybill.fomatFloat(billingWeight*priceRate,0);
		if(airfreight.airQueryWaybill.minPriceRate!=null || airfreight.airQueryWaybill.minPriceRate!=''){
			if(billingWeight*priceRate<airfreight.airQueryWaybill.minPriceRate){
				airFee = airfreight.airQueryWaybill.fomatFloat(airfreight.airQueryWaybill.minPriceRate,0);
			}
		}
		feeTotal = airFee;
	}else{
		/*Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
						airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.flightNoFeeInfo'));*/
		alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.flightNoFeeInfo'));
	}
	return record;
}

//空运代理网点
Ext.define('Foss.commonSelector.AirAgencyDeptSelector', {
	extend : 'Foss.commonSelector.CommonCombSelector',
	alias : 'widget.commonairagencydeptselector',
	displayField : 'agentDeptName',// 显示名称
	valueField : 'agentDeptCode',// 值
	active : 'Y',//有效状态
	destCityCode:null,//目的站城市代码
	queryParam : 'outerBranchEntity.agentDeptName',// 查询参数
	showContent : '{agentDeptName}&nbsp;&nbsp;&nbsp;{agentDeptCode}',// 显示表格列
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext
				.create('Foss.baseinfo.commonSelector.AirAgencyDeptStore');
		//加载前事件
	    me.store.addListener('beforeload', function(store, operation, eOpts) {
	    	var searchParams = operation.params;
			//如果查询参数为空
			if (Ext.isEmpty(searchParams)) {
				searchParams = {};
				Ext.apply(operation, {
					params : searchParams
				});
			}
	      //目的站城市代码
	      if (!Ext.isEmpty(me.destCityCode)) {
	    	  searchParams['outerBranchEntity.cityCode'] = me.destCityCode;
	      }
	      searchParams['outerBranchEntity.active'] = me.active;
		})
		me.callParent([cfg]);
	}
});

Ext.define('Foss.airfreight.rateQueryModel',{
	extend: 'Ext.data.Model',
	fields: [{name: 'targetCode',type:'string'}]
});
Ext.define('Foss.airfreight.airwayBillModel',{
	extend: 'Ext.data.Model',
	fields: [
		{name: 'id',type:'string'},
		{name: 'airLineTwoletter',type:'string'},
		{name: 'airWaybillNo',type:'string'},
		{name: 'deptRegionCode',type:'string'},
		{name: 'deptRegionName',type:'string'},
		{name: 'arrvRegionCode',type:'string'},
		{name: 'arrvRegionName',type:'string'},
		{name: 'destOrgCode',type:'string'},
		{name: 'dedtOrgName',type:'string'},
		{name: 'receiverCode',type:'string'},
		{name: 'receiverName',type:'string'},
		{name: 'receiverContactPhone',type:'string'},
		{name: 'accountItem',type:'string'},
		{name: 'billingAgency',type:'string'},
		{name: 'receiverAddress',type:'string'},
		{name: 'storageItem',type:'string'},
		{name: 'flightNo',type:'string'},
		{name: 'flightDate',type:'date',
			convert: function(value) {
			if(!value) return '';
			var date = new Date(value);						
			var formatStr = 'Y-m-d';
			return Ext.Date.format(date, formatStr);
		}},
		{name: 'takeOffTime',type:'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}},
		{name: 'arriveTime',type:'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}
		},
		{name: 'rateClass',type:'string'},
		{name: 'paymentType',type:'string'},
		{name: 'grossWeight',type:'number'},
		{name: 'billingWeight',type:'number'},
		{name: 'fee',type:'number'},
		{name: 'agenctCode',type:'string'},
		{name: 'agencyName',type:'string'},
		{name: 'agencyName_wf',type:'string'},
		{name: 'declareValue',type:'string'},
		{name: 'itemCode',type:'string'},
		{name: 'goodsQty',type:'number'},
		{name: 'volume',type:'number'},
		{name: 'airFee',type:'number'},
		{name: 'extraFee',type:'number'},
		{name: 'goodsName',type:'string'},
		{name: 'packageStruction',type:'string'},
		{name: 'groundFee',type:'number'},
		{name: 'fuelSurcharge',type:'number'},
		{name: 'transportInsurance',type:'number'},
		{name: 'inseranceFee',type:'number'},
		{name: 'feeTotal',type:'number'},
		{name: 'feePlain',type:'string'},
		{name: 'billingFee',type:'number'},
		{name: 'shipperCode',type:'string'},
		{name: 'shipperName',type:'string'},
		{name: 'shipperAddress',type:'string'},
		{name: 'shipperContactPhone',type:'string'},
		{name: 'pickupType',type:'string'},
		{name: 'createOrgCode',type:'string'},
		{name: 'createUserCode',type:'string'},
		{name: 'createUserName',type:'string'},
		{name: 'createTime',type:'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);	
				if(date == 'Invalid Date'){
					 return value;
				}
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}
		},
		{name: 'modifyUserCode',type:'string'},
		{name: 'modifyUserName',type:'string'},
		{name: 'modifyTime',type:'date',
			convert: function(value) {
				if(!value) return '';
				var date = new Date(value);						
				var formatStr = 'Y-m-d H:i:s';
				return Ext.Date.format(date, formatStr);
			}
		},
		{name: 'currencyCode',type:'string'},
		{name: 'airAssembleType',type:'string'},
		{name: 'airPickState',type:'string'},//是否已做合大票
		{name: 'handoverState',type:'string'},
		{name: 'isNotPayment',type:'string'},
		{name:'jointTicketNo',type:'string'},
		{name:'createOrgName',type:'string'},
		{name: 'airportCode',type:'string'},
		{name: 'transportType',type:'string'}
	]
});
//加载store
airfreight.airQueryWaybill.airLineTwoletter = Ext.create('Ext.data.Store',{
	autoLoad: true,
	fields:['name','code'],
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url:airfreight.realPath('queryAirLineTwoletterList.action'),
		reader : {
			type : 'json',
			root : 'pointsSingleJointTicketVO.queryAllAirlines',
			successProperty: 'success'
		}
	}
});

//航空正单基本信息
Ext.define('Foss.airfreight.updateCurrentAirwayBillForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: false,
	queryAirlineRate : function (airLineTwoletter,origCode){
    	Ext.Ajax.request({
    		async: false,
    		url:airfreight.realPath('queryRate.action'),
			params :{
				'pointsSingleJointTicketVO.airLineTwoletter' : airLineTwoletter,
				'pointsSingleJointTicketVO.airportCode' : origCode
			},
			success:function(response){
				var result = Ext.decode(response.responseText);
				airfreight.airQueryWaybill.airlinesValueAddEntity = result.pointsSingleJointTicketVO.airlinesValueAddEntity;
				airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('accountItem').setValue(airfreight.airQueryWaybill.airlinesValueAddEntity.priceNo);
			},
			exception:function(response){
				var result = Ext.decode(response.responseText);
				Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'),result.message);
				return false;
			}
    	});
    	return airfreight.airQueryWaybill.airlinesValueAddEntity;
	},
	queryWaybillNo : function(airWaybillNo){
		var record = null;
    	Ext.Ajax.request({
    		async: false,
			url:airfreight.realPath('queryWidthPrintAirWaybill.action'),
	   		params : {
	   				'pointsSingleJointTicketVO.airWaybillNo':airWaybillNo
	   		},
			success:function(response){
				var result = Ext.decode(response.responseText);
				resultEntity = result.pointsSingleJointTicketVO.billEntity;
				airfreight.airQueryWaybill.resultEntity = resultEntity;
				record = Ext.create('Foss.airfreight.airwayBillModel',resultEntity);
			},
			failure:function(response){
				var result = Ext.decode(response.responseText);
			}
		});
		return record;
	},
	items:[
			Ext.create('Ext.toolbar.Toolbar', {
				   xtype:'toolbar',
				   dock:'right',
				   layout:'column',
				   defaultType:'button',
				   width:1024,
				   items:[{
					   xtype:'container',
					   html:'&nbsp;',
					   columnWidth:.94
				   },{
					   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.print'),
					   columnWidth:.06,
					   handler: function(){
					   	   airfreight.airQueryWaybill.printMulitRecord='';
						   Ext.create('Foss.airfreight.choiceAirwayBillPrintWindow',{printType:'INNER'}).show();
						   airfreight.airQueryWaybill.checkstatus.getForm().getFields().each(function(item,index,length){
		            			if(item.boxLabel==airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.agenBook')){
									item.show();    								
									return;
								}
							});
					   }
				   }]
			}),
	       airfreight.airQueryWaybill.airwayBillBasicInfo = Ext.create('Ext.form.FieldSet',{
	    	   title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.basicInfo'),
	    	   height:105,
	    	   width:1025,
	    	   layout:'column',
	    		queryAirlinesAgentCode : function (airLineTwoletter){
	    			
	    			/**
					 * @desc 获得配载类型，将配载类型传入后台
					 * @author wqh
					 * date 2013-08-08
					 * */
					var airQueryWaybillForm2 = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
					var airAssembleType = airQueryWaybillForm2.findField('airAssembleType').getValue();
					if(airAssembleType==null||airAssembleType==""){
						Ext.Msg.alert(airfreight.airEnteringFlightBill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'),'请选择配载类型');
						return ;
					}
				    			
	    			
	    	    	Ext.Ajax.request({
	    	    		async: false,
	    	    		url:airfreight.realPath('queryAirlinesAgentCode.action'),
	    				params :{
	    					'pointsSingleJointTicketVO.airLineTwoletter' : airLineTwoletter,
	    					'pointsSingleJointTicketVO.airAssembleType' : airAssembleType
	    				},
	    				success:function(response){
	    					var result = Ext.decode(response.responseText);
	    					var airlinesAgentEntity = result.pointsSingleJointTicketVO.airlinesAgent;
	    					if(airlinesAgentEntity!=null){
	    						airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('billingAgency').setValue(airlinesAgentEntity.agentCode);
	    					}else{
	    						airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('billingAgency').setValue('');
	    					}
	    				},
	    				exception:function(response){
	    					var result = Ext.decode(response.responseText);
	    					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.errorMessage'),result.message);
	    					return false;
	    				}
	    	    	});
	    	    	return airfreight.airQueryWaybill.airlinesValueAddEntity;
	    		},
	    	   defaults:{
	    			allowBlank: false,
	    			margin:'5 5 5 5',
	    			xtype: 'textfield'
	    		},
	    	   items:[{
	    			xtype:'commonairlinesselector',
	    			displayField : 'code',// 显示名称
	    			valueField : 'code',// 值 
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airLineTwoletter'),
	    			name: 'airLineTwoletter',
	    			columnWidth:.25,
	    			listeners: {
	    				select: function(combo, record, index) {
	    					var airLineTwoletterCode = record[0].data.code;
    						var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					var deptRegionCode = recordUpdateForm.findField('deptRegionName').getValue();
	    					var arrvRegionCode = recordUpdateForm.findField('arrvRegionName').getValue();
	    					var airAssembleType = recordUpdateForm.findField('airAssembleType').getValue();
	    					if(airAssembleType!=null){
	    						if(airAssembleType=='DDKD' || airAssembleType =='HDP'){
	    							recordUpdateForm.findField('agenctCode').setValue(airLineTwoletterCode);
	    							airfreight.airQueryWaybill.airwayBillRecord.data.agencyName = record[0].data.name;
	    						}
	    					}
	    					var findField = function(name) {
	    						return form.findField(name);
	    					};
	    					recordUpdateForm.findField('flightNo').airLines = airLineTwoletterCode;
	    					recordUpdateForm.findField('flightNo').store.load();
	    					//过滤航空公司代理人信息
    						recordUpdateForm.findField('agencyName_wf').assemblyDeptId = airfreight.airQueryWaybill.createDeptCode;
    						recordUpdateForm.findField('agencyName_wf').airlinesCode = airLineTwoletterCode;
    						recordUpdateForm.findField('agencyName_wf').store.load();
	    					recordUpdateForm.findField('storageItem').setValue(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.only')+record[0].data.name+airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.accept'));
	    					airfreight.airQueryWaybill.airwayBillBasicInfo.queryAirlinesAgentCode(airLineTwoletterCode);
	    					recordUpdateForm.findField('billingAgency').setReadOnly(true);
	    				},
	    				change : function(ths, newValue, oldValue, eOpts) {
	    					var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    						if (Ext.isEmpty(ths.getRawValue())) {
	    							var findField = function(name) {
	    								return recordUpdateForm.findField(name);
	    							};
	    							ths.setValue(null);
	    							findField('flightNo').airLines = null;
	    							findField('flightNo').store.load();
	    						}
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillNo'),
	    			xtype:'textfield',
	    			name:'airWaybillNo',
	    			columnWidth:.25,
	    			maxLength : 25,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.zdhcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.deptRegionName'),
	    			readOnly:true,
	    			xtype:'textfield',
	    			name:'deptRegionName',
	    			columnWidth:.25
	    		},{
	    			xtype : 'commonairporwithcitynametselector',
					fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.public.arrvRegionName'),
					name:'arrvRegionName',
					displayField : 'cityName',
					columnWidth:.25,
					listeners: {
						select : function(me, records, eOpts){
							if(Ext.isEmpty(me.getValue())){
								return;
							}else{
								var tempForm = me.up('form').getForm();
							}
							var record = records[0].data;
							var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
							//2013-05-31去除根据目的站过滤航班（有中转情况）
//							var destAirPortCode = record.airportCode;
//							recordUpdateForm.findField('flightNo').targetCode = destAirPortCode;
//	    					recordUpdateForm.findField('flightNo').store.load();
							var destCityCode = record.cityCode;
							recordUpdateForm.findField('dedtOrgName').destCityCode = destCityCode;
	    					recordUpdateForm.findField('dedtOrgName').store.load();
						}
					}
	    		},{
	    			xtype: 'combobox',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airAssembleType'),
	    			name: 'airAssembleType',
	    			hiddenName: 'airAssembleType',
	    			store: FossDataDictionary.getDataDictionaryStore('AIR_ASSEMBLE_TYPE','Foss_login_language_store_Id'),
	    			queryMode: 'local',
	    			displayField: 'valueName',
	    			valueField: 'valueCode',
	    			columnWidth:.25,
	    			listeners: {
	    				select: function(combo, record, index) {
	    					var code = record[0].data.valueCode;
	    					var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					if(code=='DDWFD' || code=='HDPWF'){
	    						//获取合票号
	    						recordUpdateForm.findField('billingFee').setReadOnly(false);
	    						recordUpdateForm.findField('airWaybillNo').setValue(recordUpdateForm.findField('jointTicketNo').getValue());
	    				
	    						document.getElementById(recordUpdateForm.findField('agencyName_wf').getId()).style.display = 'inline';
	    						document.getElementById(recordUpdateForm.findField('agenctCode').getId()).style.display = 'none';
	    						//重新计算费用
	    						//wqh
	    						var airFee=recordUpdateForm.findField('airFee').getValue();
	    						var billingFee=recordUpdateForm.findField('billingFee').getValue()
	    						recordUpdateForm.findField('groundFee').setValue(0);
	    						recordUpdateForm.findField('groundFee').setReadOnly(true);
	    						recordUpdateForm.findField('fuelSurcharge').setValue(0);
	    						recordUpdateForm.findField('fuelSurcharge').setReadOnly(true);
	    						recordUpdateForm.findField('transportInsurance').setValue(0);
	    						recordUpdateForm.findField('transportInsurance').setReadOnly(true);
	    						recordUpdateForm.findField('inseranceFee').setValue(0);
	    						recordUpdateForm.findField('inseranceFee').setReadOnly(true);
	    						recordUpdateForm.findField('extraFee').setValue(0);
	    						recordUpdateForm.findField('extraFee').setReadOnly(true);
	    						recordUpdateForm.findField('feeTotal').setValue(Ext.Number.from(airFee,0)+Ext.Number.from(billingFee,0));
	    					}
	    					//如果配载方式是合大票，重新计算总金额
	    					if(code=='HDP'||code=='DDKD')
    						{
    							var airFee=Ext.Number.from(recordUpdateForm.findField('airFee').getValue(),0);
    							recordUpdateForm.findField('billingFee').setValue(0);
    							recordUpdateForm.findField('groundFee').setValue(0);
    							recordUpdateForm.findField('fuelSurcharge').setValue(0);
    							recordUpdateForm.findField('inseranceFee').setValue(0);
    							//ISSUE-3324  航空正单明细录入界面，保险费支持编辑，可为0
    							recordUpdateForm.findField('inseranceFee').setReadOnly(false);
    							recordUpdateForm.findField('extraFee').setValue(0);
    							recordUpdateForm.findField('feeTotal').setValue(airFee);
    							recordUpdateForm.findField('transportInsurance').setReadOnly(false);
    							recordUpdateForm.findField('extraFee').setReadOnly(false);
    						}
	    					if(code=='HDP' || code=='HDPWF'){
	    						var dedtOrgName = recordUpdateForm.findField('dedtOrgName').getValue();
	    						recordUpdateForm.findField('receiverName').setValue(dedtOrgName);
	    					
	    						
	    					}
	    					//设置承运人
	    					if(code=='HDP' || code=='DDKD'){
	    						var airLineTwoletter = recordUpdateForm.findField('airLineTwoletter').getValue();
	    						recordUpdateForm.findField('billingFee').setValue(0);
	    						recordUpdateForm.findField('billingFee').setReadOnly(true);
	    						recordUpdateForm.findField('agenctCode').setValue(airLineTwoletter);
	    						recordUpdateForm.findField('agenctCode').setReadOnly(true);
	    						document.getElementById(recordUpdateForm.findField('agencyName_wf').getId()).style.display = 'none';
	    						document.getElementById(recordUpdateForm.findField('agenctCode').getId()).style.display = 'inline';

	    					}
	    					if(code=='DDWFD' || code=='DDKD'){
	    						 var showRecord = airfreight.airQueryWaybill.waybillInfo.store.data.items;
	    						 if(showRecord.length>1){
	    							 Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	    									 ,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.ddkdhddkwfdznbcytyd'));
	    							 return false;
	    						 }
	    						 if(showRecord.length!=0){
	    							 recordUpdateForm.findField('receiverName').setValue(showRecord[0].data.receiverName);
	    						 }
	    						 
	    					}
	    					if(code=='DDKD' || code =='HDP'){
	    						recordUpdateForm.findField('airWaybillNo').setValue(null);
	    					}
	    					var waybillDatas = airfreight.airQueryWaybill.waybillInfo.store.data.items;
	    					if(waybillDatas.length>0){
	    						for(var i = 0 ; i < waybillDatas.length; i++){
	    							var makeWaybillWay = waybillDatas[i].data.makeWaybillWay;
		    						if(code=='DDKD' || code=='DDWFD'){
						                if(makeWaybillWay=='HDP' || makeWaybillWay=='HDPWF'){
						                  Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
						                      ,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.hdpOnddkdIfContinue')); 
										  return false;
						                }
			    					}else if(code=='HDP' || code=='HDPWF'){
			    						if(makeWaybillWay=='DDKD' || makeWaybillWay=='DDWFD'){
						                  Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
						                      ,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ddkdOnhdpIfContinue')); 
										  return false;
						                }
			    					}
	    						}
	    					}
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.jointTicketNo'),
	    			xtype:'textfield',
	    			name:'jointTicketNo',
	    			disabled:true,
	    			columnWidth:.25,
	    			regex: /[^\d|chun]/g,
	    			regexText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.znsrzmhsz'),
	    			maxLength : 9,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.hpcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.dedtOrgName'),
	    			xtype:'commonAiragentAndDeptselector',
	    			name:'dedtOrgName',
	    			columnWidth:.25,
	    			listeners: {
	    				select: function(combo, record, index) {
	    					var recordAgent = record[0].data;
	    					var records = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord();
	    					var updateCurrentAirwayBillForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					updateCurrentAirwayBillForm.findField('arrvRegionName').orgCode =recordAgent.agentDeptCode;
	    					var airAssembleType = updateCurrentAirwayBillForm.findField('airAssembleType').getValue();
	    					if(airAssembleType=='HDP' || airAssembleType=='HDPWF'){
	    						updateCurrentAirwayBillForm.findField('receiverName').setValue(recordAgent.agentDeptName);
	    					}
    	            			records.data.destOrgCode = recordAgent.agentDeptCode;
    	            			updateCurrentAirwayBillForm.findField('arrvRegionName').setValue(recordAgent.cityName);
    	            			records.data.arrvRegionCode = recordAgent.cityCode;	    				
    	            		}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.public.arrvRegionName'),
	    			xtype:'textfield',
	    			name:'airportLocationName',
	    			allowBlank: true,
	    			columnWidth:.25,
	    			maxLength : 20,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
	    		}]
	       }),
	       airfreight.airQueryWaybill.airwayBillConsigneeInfo = Ext.create('Ext.form.FieldSet',{
	    	   title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.consigneeMessage'),
	    	   height:105,
	    	   width:1025,
	    	   layout:'column',
	    	   defaults:{
	    			margin:'5 5 5 5',
	    			xtype: 'textfield',
	    			allowBlank: false
	    		},
	    	   items:[{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiverName'),
	    			name:'receiverName',
	    			columnWidth:.25,
	    			maxLength : 100,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.shrcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiverContactPhone'),
	    			name:'receiverContactPhone',
	    			columnWidth:.25,
	    			maxLength : 30,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.accountItem'),
	    			allowBlank: true,
	    			name:'accountItem',
	    			columnWidth:.25,
	    			maxLength : 100,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.jssxcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingAgency'),
	    			name:'billingAgency',
	    			disabled:true,
	    			allowBlank: true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiverAddress'),
	    			allowBlank: true,
	    			name:'receiverAddress',
	    			columnWidth:.25,
	    			maxLength : 500,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storageItem'),
	    			name:'storageItem',
	    			allowBlank: true,
	    			columnWidth:.25
	    		}]
	       }),
	       airfreight.airQueryWaybill.filghtFareInfo = Ext.create('Ext.form.FieldSet',{
	    	   title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.flightpricingMessage'),
	    	   height:140,
	    	   width:1025,
	    	   layout:'column',
	    	   defaults:{
	    			margin:'5 5 5 5',
	    			xtype: 'textfield',
	    			allowBlank: false
	    	   },
	    	   items:[{
	    			xtype: 'commonflightselector',
	    			fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.flightNo'),
	    			name: 'flightNo',
	    			columnWidth:.25,
	    			listeners: {
	    				select: function(combo, record, index) {
	    					var record = record[0].data;
	    					airfreight.airQueryWaybill.filghtInfo = record; 
	    					airfreight.airQueryWaybill.airwayBillRecord.data.flightNo = record.flightNo;
				    		// 日期格式化
				    		var formatDate = function(value, formatStr) {
				    			if(!formatStr) formatStr = 'Y-m-d';
				    			return Ext.Date.format(new Date(value), formatStr); 
				    		};
	    				    var startTime = Ext.Date.format(new Date(record.planLeaveTime),'Y-m-d H:i:s');
	    				    var endTime = Ext.Date.format(new Date(record.planArriveTime),'Y-m-d H:i:s');
	    				    var planLeaveTime = formatDate(startTime,'H:i');
	    				    var planArriveTime = formatDate(endTime,'H:i');
	    					var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					recordUpdateForm.findField('takeOffTime').setValue(planLeaveTime);
	    					recordUpdateForm.findField('arriveTime').setValue(planArriveTime);
	    					var airLineTwoletter = recordUpdateForm.findField('airLineTwoletter').getValue();
	    					if(airLineTwoletter==''){
	    						return false;
	    					}
	    					recordUpdateForm.findField('flightNo').setValue(record.flightNo);
	    					var origCode = record.origCode;
	    					//查询费率
	    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.queryAirlineRate(airLineTwoletter,origCode);
	    					//计算相关费用
	    					airfreight.airQueryWaybill.airwaybillCalculate(airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord(),'query',record.flightNo);
	    					
	    				}
	    			}
	    		},{
	    			xtype: 'datefield',
	        		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.flightDate'),
	        		name: 'flightDate',
	        		format: 'Y-m-d',
	        		columnWidth:.25
	    		},{				
					xtype: 'timefield',
		            fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.takeOffTime'),
		            name:'takeOffTime',
		            altFormats: 'H:i|g:i A',
		            format: 'H:i',
		            increment: 1,
			    	columnWidth: .25
	    		},{
					xtype: 'timefield',
		            fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arriveTime'),
		            altFormats: 'H:i|g:i A',
		            format: 'H:i',
		            increment: 1,
		            name:'arriveTime',
			    	columnWidth: .25
	    		}
	       ,{
	    			xtype:'combobox',
	    			editable:false,
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.rateClass'),
	    			name: 'rateClass',
	    			hiddenName: 'rateClassr',
	    			store: FossDataDictionary. getDataDictionaryStore('RATE_CLASS','Foss_login_language_store_Id'),
	    			queryMode: 'local',
	    			displayField: 'valueName',
	    			valueField: 'valueCode',
	    			columnWidth:.25
	    		},{
	    			xtype:'combobox',
	    			editable:false,
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.paymentType'),
	    			name: 'paymentType',
	    			hiddenName: 'paymentType',
	    			store: FossDataDictionary. getDataDictionaryStore('PAYMENT_TYPE','Foss_login_language_store_Id'),
	    			queryMode: 'local',
	    			displayField: 'valueName',
	    			valueField: 'valueCode',
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
	    			name:'grossWeight',
	    			columnWidth:.25,
	        		maxLength : 9,
	        		maxValue:999999999,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.zlcgzdxz'),
	        		vtype:'grossWeight'
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
	    			name:'billingWeight',
	    			columnWidth:.25,
	        		maxLength : 7,
	        		maxValue:9999999,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.jfzlcgzdxz'),
	    			vtype:'billingWeight',
	    			listeners : {
	    				blur : function(v,event,eOpts){
	    					var value = Ext.Number.from(v.value,0);
	    					if(value >= 0){
	    						var record = airfreight.airQueryWaybill.airwayBillRecord;
	    						airfreight.airQueryWaybill.airwaybillCalculate(record);
	    					}
	    				}
	    			}
	    		},{
	    			xtype: 'numberfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.fee'),
	        		name:'fee',
	    			columnWidth:.25,
	    			minValue: 0,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	        		maxLength : 8,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.yjcgzdxz'),
	    			listeners : {
	    				blur : function(v,event,eOpts){
	    					var fee = Ext.Number.from(v.value,0);
	    					if(fee >= 0){
		    					var airFee = 0;
		    					var currentForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
		    					var billingWeight = Ext.Number.from(currentForm.findField('billingWeight').getValue(),0);
								//计算航空运费
								if(airfreight.airQueryWaybill.minPriceRate!=null ||airfreight.airQueryWaybill.minPriceRate!=''){
									if(billingWeight*fee<airfreight.airQueryWaybill.minPriceRate){
										airFee = airfreight.airQueryWaybill.fomatFloat(airfreight.airQueryWaybill.minPriceRate,0);
									}else{
										airFee = airfreight.airQueryWaybill.fomatFloat(billingWeight*fee,0);
									}
								}
								airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.airFee= airFee;
		    				 	currentForm.findField('airFee').setValue(airFee);
			
		    					var billingFee = currentForm.findField('billingFee').getValue();
		    				 	var groundFee = currentForm.findField('groundFee').getValue();
		    				 	var inseranceFee = currentForm.findField('inseranceFee').getValue();
		    				 	var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
		    				 	var extraFee = currentForm.findField('extraFee').getValue();
		    					var feeTotal=0.00;
		    				 	
		    					//判断是否外发，如果是外发则总金额等于航空运费+制单费用
		    					//2013-07-22 
		    					//wqh
		    					var airAssembleType = currentForm.findField('airAssembleType').getValue();
		    					
		    					if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF')
		    					{
		    						feeTotal = airFee+Ext.Number.from(billingFee,0);
		    						currentForm.findField('groundFee').setValue(0);
		    						currentForm.findField('transportInsurance').setValue(0);
		    						currentForm.findField('fuelSurcharge').setValue(0);
		    						currentForm.findField('extraFee').setValue(0);
		    						//flightinformationForm.findField('billingFee').setValue('');
		    					}else{
		    						
		    						//总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
		    						
		    						feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(inseranceFee,0)
		    						+ Ext.Number.from(fuelSurcharge,0) + Ext.Number.from(billingFee,0) + Ext.Number.from(extraFee,0);
		    					}
		    				 	
		    				 	//总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
								//大于并且等于航空公司费率基础资料的最低总金额时，总金额保持不变；小数点后保留2位
	    				 		if(airfreight.airQueryWaybill.airlinesValueAddEntity != null
	    				 				&& airfreight.airQueryWaybill.airlinesValueAddEntity.id != null){
									if(feeTotal < airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee){
										feeTotal = airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee;
									}
	    				 		}
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.feeTotal= feeTotal;
		    				 	currentForm.findField('feeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(feeTotal,2));
	    					}
	    					
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.agenctCode'),
	    			name:'agenctCode',
	    			allowBlank: true,
	    			columnWidth:.25,
	    			hidden:false
	    		},{
	    			xtype:'commonagentselector',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.agenctCode'),
	    			name:'agencyName_wf',
	    			columnWidth:.25,
	    			hidden:true,
	    			listeners: {
	    				select: function(combo, record, index) {
	    					var record = record[0].data;
	    					var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					var airwayBillRecord = airfreight.airQueryWaybill.airwayBillRecord;
	    					recordUpdateForm.findField('agenctCode').setValue(record.agentCode);
	    					airwayBillRecord.data.agencyName = record.agentName;
	    				}
	    			}
	    		}]
	       }),
	       airfreight.airQueryWaybill.airwayBillGoodsInformation =  Ext.create('Ext.form.FieldSet',{
	    	   title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.goodsMessage'),
	    	   height:210,
	    	   width:1025,
	    	   layout:'column',
	    	   defaults:{
	    			margin:'5 5 5 5',
	    			xtype: 'textfield',
	    			allowBlank: false
	    		},
	    		items:[{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.declareValue'),
	    			name:'declareValue',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.itemCode'),
	    			name:'itemCode',
	    			disabled:false,
	    			allowBlank: true,
	    			columnWidth:.25,
	    			maxLength : 25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.goodsQty'),
	    			name:'goodsQty',
	    			columnWidth:.25,
	    			maxLength : 9,
	    			maxValue:999999999,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.jscgzdxz'),
	    			vtype:'goodsQty'
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
	    			name:'volume',
	    			columnWidth:.25,
	    			maxLength : 8,
	    			maxValue:99999999,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.tjcgzdxz'),
	    			vtype:'volume'
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airFee'),
	    			name:'airFee',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			xtype: 'numberfield',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.extraFee'),
	    			name:'extraFee',
	    			columnWidth:.25,
	    			minValue: 0,
	    			maxValue: 999999999,
	    			maxLength:10,
	    			allowBlank: true,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	        	    listeners : {
	    				blur : function(v,event,eOpts){
	    					var currentForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					var value = Ext.Number.from(v.value,0);
	    					if(v.value == null){
	    						currentForm.findField('extraFee').setValue(0);
	    					}
	    					if(value >= 0){
		    					var airFee = currentForm.findField('airFee').getValue();
		    				 	var groundFee = currentForm.findField('groundFee').getValue();
		    				 	var inseranceFee = currentForm.findField('inseranceFee').getValue();
		    				 	var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
		    				 	var billingFee = currentForm.findField('billingFee').getValue();
		    					var feeTotal = 0.00;
		    					var extraFee = currentForm.findField('extraFee').getValue();
		    				 	//判断是否外发 ,如果属于外发 总金额等于航空运费+制单费用
		    				 	//2013-07-22 wqh
		    					var airAssembleType = currentForm.findField('airAssembleType').getValue();
		    	            	if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF'){
		    	            		
		    	            		 return false;
		    		            }else{
		    		            	
		    		            	
		    		            	//总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
		    		            	feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(inseranceFee,0)
		    		            	+ Ext.Number.from(fuelSurcharge,0)+ Ext.Number.from(billingFee,0)+value;
		    		            }
		    					
		    					
	    				 		//总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
	    				 		if(airfreight.airQueryWaybill.airlinesValueAddEntity != null
	    				 					&& airfreight.airQueryWaybill.airlinesValueAddEntity.id!=null){
			    					if(feeTotal < airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee){
										feeTotal = airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee;
									}
	    				 		}
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.feeTotal= feeTotal;
		    				 	currentForm.findField('feeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(feeTotal,2));
	    					}
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.groundFee'),
	    			name:'groundFee',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.fuelSurcharge'),
	    			name:'fuelSurcharge',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			xtype: 'numberfield',
	        		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.transportInsurance'),
	        		name:'transportInsurance',
	    			columnWidth:.25,
	    			minValue: 0,
	    			maxValue:9999999999,
	    			allowBlank: true,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	    			maxLength : 10,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.ysbxcgzdxz'),
	    			listeners : {
	    				blur : function(v,event,eOpts){
	    					var currentForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    					if(v.value == null){
	    						currentForm.findField('transportInsurance').setValue(0);
	    					}
	    					var value = Ext.Number.from(v.value,0);
	    					if(value >= 0){
	    						

		    					//判断是否外发，返回false
		    					//2013-07-22 
		    					//wqh
		    					var airAssembleType = currentForm.findField('airAssembleType').getValue();
		    					
		    					if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF')
		    					{
		    					
		    						return false;
		    					}
	    						
		    					var airFee = currentForm.findField('airFee').getValue();
		    				 	var groundFee = currentForm.findField('groundFee').getValue();
		    				 	var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
		    				 	var extraFee = currentForm.findField('extraFee').getValue();
		    				 	var billingFee = currentForm.findField('billingFee').getValue();
		    					var inseranceFee = 0;
		    					//总金额=航空运费+地面运费+保险费+燃油附加税+制单费+附加费
			    				var feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(inseranceFee,0)
			    						+ Ext.Number.from(fuelSurcharge,0)+Ext.Number.from(billingFee,0)+Ext.Number.from(extraFee,0);
		    					//地面运费、燃油附加税、保险费   的费率信息
		    					var airlinesValueAddEntity = airfreight.airQueryWaybill.airlinesValueAddEntity;
		    					if(airlinesValueAddEntity != null && airlinesValueAddEntity.id!=null){
		    						var transportInsurance = currentForm.findField('transportInsurance').getValue();
		    						var bxfl = airlinesValueAddEntity.insuranceFee;//保险费率 
		    						//计算保险费
									if(transportInsurance*bxfl < airlinesValueAddEntity.minInsuranceFee){
										inseranceFee = airlinesValueAddEntity.minInsuranceFee;
									}else{
										inseranceFee = transportInsurance*bxfl;
									}
									//总金额=航空运费+地面运费+保险费+燃油附加税+制单费
			    					feeTotal = feeTotal + Ext.Number.from(inseranceFee,0);
		    				 		//总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
		    				 		if(feeTotal < airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee){
										feeTotal = airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee;
									}
		    					}
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.inseranceFee= inseranceFee;
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.feeTotal= feeTotal;
		    				 	currentForm.findField('inseranceFee').setValue(airfreight.airQueryWaybill.fomatFloat(inseranceFee,2));
		    				 	currentForm.findField('feeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(feeTotal,2));
	    					}
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.inseranceFee'),
	    			xtype: 'numberfield',
	    			disabled:false,
	    			minValue: 0,
	    			maxValue:9999999999,
	    			maxLength :10,
	    			name:'inseranceFee',
	    			columnWidth:.25,
	    			allowBlank: true,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	     //保险费改变时重新计算
	        	    //BUG-54800 新增和修改航空正单，录入保险费后，保存，总运费没有加上保险费
	        	    //wqh 2013-09-13
	        	    listeners : {
	    				blur : function(v,event,eOpts){
	    				var currentForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();

	    					if(v.value == null){
	    						currentForm.findField('inseranceFee').setValue(0);
	    					}
	    					var value = Ext.Number.from(v.value,0);
	    					if(value >= 0){
	    						
	    						//判断是否外发，如果外发则总金额等于航空运费+制单费用
		    				 	//wqh
		    				 	//2013-07-22
		    					var airAssembleType = currentForm.findField('airAssembleType').getValue();
		    					if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF'){
		    						
		    						return false;
		    					}
		    					var airFee = currentForm.findField('airFee').getValue();
		    				 	var groundFee = currentForm.findField('groundFee').getValue();
		    				 	var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
		    				 	var billingFee = currentForm.findField('billingFee').getValue();
		    				 	var extraFee = currentForm.findField('extraFee').getValue();
		    				 	var inseranceFee= Ext.Number.from(currentForm.findField('inseranceFee').getValue(),0);
		    					//总金额=航空运费+地面运费+保险费+燃油附加税+制单费
			    				var feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(fuelSurcharge,0)
			    						+Ext.Number.from(billingFee,0) + Ext.Number.from(extraFee,0)+inseranceFee;
									
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.inseranceFee= inseranceFee;
		    					airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord().data.feeTotal= feeTotal;
		    				 	currentForm.findField('inseranceFee').setValue(airfreight.airQueryWaybill.fomatFloat(inseranceFee,2));
		    				 	currentForm.findField('feeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(feeTotal,2));
	    					}
	    				}
	    			}
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.feeTotal'),
	    			name:'feeTotal',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.feePlain'),
	    			name:'feePlain',
	    			columnWidth:.25,
	    			maxLength : 100,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.fysmcgzdxz')
	    		},{
	    			xtype: 'numberfield',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingFee'),
	    			name:'billingFee',
	    			allowBlank: true,
	    			columnWidth:.25,
	    			minValue: 0,
	    			maxValue: 9999999999,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	    			listeners : {
	    				blur : function(v,event,eOpts){
	    					var value = Ext.Number.from(v.value,0);
	    					if(value >= 0){
	    						var currentForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	    						var airFee = currentForm.findField('airFee').getValue();
		    				 	var groundFee = currentForm.findField('groundFee').getValue();
		    				 	var inseranceFee = currentForm.findField('inseranceFee').getValue();
		    				 	var fuelSurcharge = currentForm.findField('fuelSurcharge').getValue();
		    				 	var extraFee = currentForm.findField('extraFee').getValue();
		    				 	//设置制单费
		    					var billingFee = Ext.Number.from(currentForm.findField('billingFee').getValue(),0);
		    					var feeTotal = 0.00;
		    					//判断配载类型是否外发，如果外发则总金额等于航空运费
		    					//wqh
                                var airAssembleType = currentForm.findField('airAssembleType').getValue();
		    					
		    					if(airAssembleType=='DDWFD' || airAssembleType=='HDPWF')
		    					{
		    						feeTotal =Ext.Number.from(airFee,0)+billingFee;
		    						currentForm.findField('groundFee').setValue(0);
		    						currentForm.findField('transportInsurance').setValue(0);
		    						currentForm.findField('fuelSurcharge').setValue(0);
		    						currentForm.findField('extraFee').setValue(0);
		    					}else{
		    						
		    						feeTotal = Ext.Number.from(airFee,0) + Ext.Number.from(groundFee,0) + Ext.Number.from(inseranceFee,0)
		    						+ Ext.Number.from(fuelSurcharge,0)+value+Ext.Number.from(extraFee,0)+billingFee;
		    						
		    					}
		    					
		    					
		    					//总金额小于航空公司费率基础资料的最低总金额时，总金额等于基础资料最低总金额，
	    				 		if(airfreight.airQueryWaybill.airlinesValueAddEntity != null
	    				 					&& airfreight.airQueryWaybill.airlinesValueAddEntity.id!=null){
			    					if(feeTotal < airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee){
										feeTotal = airfreight.airQueryWaybill.airlinesValueAddEntity.minTotalFee;
									}
	    				 		}
		    					
		    					airfreight.airQueryWaybill.airwayBillRecord.data.feeTotal=feeTotal;
		    					currentForm.findField('feeTotal').setValue(feeTotal);
	    					}
	    				}
	    			}
	    		},{
	    			xtype: 'textareafield',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.goodsName'),
	    			allowBlank: true,
	    			name:'goodsName',
	    			columnWidth:.25,
	    			maxLength : 100,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.hwmccdycgzdxz')
	    		},{
	    			xtype: 'textareafield',
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageStruction'),
	    			name:'packageStruction',
	    			allowBlank: true,
	    			columnWidth:.25,
	    			maxLength : 100,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.bzsmcdycgzdxz')
	    		}]
	       }),
	       airfreight.airQueryWaybill.airwayBillOtherInfo = Ext.create('Ext.form.FieldSet',{
	    	   title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.OthersMessage'),
	    	   height:105,
	    	   width:1025,
	    	   layout:'column',
	    	   defaults:{
	    			margin:'5 5 5 5',
	    			xtype: 'textfield',
	    			allowBlank: false
	    		},
	    		items:[{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.shipperName'),
	    			name:'shipperName',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.shipperAddress'),
	    			name:'shipperAddress',
	    			allowBlank: true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.shipperContactPhone'),
	    			name:'shipperContactPhone',
	    			allowBlank: true,
	    			columnWidth:.25,
	    			maxLength : 50,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.dhcdycgzdxz')
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.pickupType'),
	    			name:'pickupType',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createOrgName'),
	    			name:'createOrgName',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createUserName'),
	    			name:'createUserName',
	    			disabled:true,
	    			columnWidth:.25
	    		},{
	    			fieldLabel :airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createTime'),
			        name : 'createTime',
			        xtype : 'datetimefield_date97',
			        time : true,
			        editable:false,
			        disabled:true,
			        allowBlank:false,
					columnWidth:.25
	    		}]
	       })
	       ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
		airfreight.airQueryWaybill.modifyVtypes = Ext.apply(Ext.form.field.VTypes, {
			phone: function(val,field){
				if(!isNaN(val)){
					return true;
				}else {
					return false;
				}
			},
		    grossWeight : function(val,field){
		    	var str = val+'';
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val,0));
		    	
		    },
		    billingWeight : function(val,field){
		    	var str = val+'';
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val,0));
		    },
		    volume : function(val,field){
		    	var str = val+'';
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val,0));
		    },
		    extraFee : function(val,field){
		    	var str = val+'';
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val,0));
		    },
		    goodsQty : function (val,field){
		    	var str = val+'';
		    	if(str.indexOf(".")==1)
		    		return false;
		    	if(str.substring(0,2)=='00')
		    		return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^\d+$/.test(Ext.Number.from(val,0));
		    },
		    goodsQtyText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.number'),
		    billingWeightText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.billingWeight'),
		    grossWeightText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.weight'),
		    volumeText:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tsgsyw'),
		    phoneText:airfreight.airQueryWaybill.i18n('foss.airfreight.phone')
		});
		me.airwayBillBasicInfo = airfreight.airQueryWaybill.airwayBillBasicInfo;
		me.airwayBillConsigneeInfo = airfreight.airQueryWaybill.airwayBillConsigneeInfo;
		me.filghtFareInfo = airfreight.airQueryWaybill.filghtFareInfo;
		me.airwayBillGoodsInformation = airfreight.airQueryWaybill.airwayBillGoodsInformation;
		me.airwayBillOtherInfo = airfreight.airQueryWaybill.airwayBillOtherInfo;
	}
});

//设置运单明细收缩展开样式
Ext.define('Foss.airfreight.airwayBillInfo',{
	extend:'Ext.form.Panel',
	frame:false,
	defaults:{
		margin:'0 5 5 5'
	},
	items:[{
	    dockedItems: [{
        xtype: 'toolbar',
        dock: 'bottom',
        items: [{
        	xtype:'button',
        	text:'+',
        	collapsible: true,
        	animCollapse: false,
        	handler: function(){
        		if(this.text=='+'){
        			this.setText('-'),
        			airfreight.airQueryWaybill.waybillInfo.setVisible(true);
        		}else{
        			this.setText('+'),
        			airfreight.airQueryWaybill.waybillInfo.setVisible(false);
        		}
        	}
        },{
        	text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetail')
        },{
        	xtype: 'container',
        	html:'&nbsp;',
			width:710
        },{
        	
        	text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tanyizhidan'),
        	disabled: !airfreight.airQueryWaybill.isPermission('airfreight/modifyAndSaveTangYiAwbButton'),
        	hidden: !airfreight.airQueryWaybill.isPermission('airfreight/modifyAndSaveTangYiAwbButton'),
        	handler:function(){
            	var	airLineTwoletter = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('airLineTwoletter').getValue();
            	if(airLineTwoletter!='CZ'){
            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.onlyNHuse'));
            		return false;
            	}
        		airfreight.airQueryWaybill.airLineTwoletterFreeCode = Ext.create('Foss.airfreight.airLineTwoletterFreeCode').show();
        	}
        },{
        	xtype: 'container',
        	html:'&nbsp;',
			width:35
        },{
            text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.save'),
            disabled: !airfreight.airQueryWaybill.isPermission('airfreight/updateAirwaybillButton'),
            hidden: !airfreight.airQueryWaybill.isPermission('airfreight/updateAirwaybillButton'),
            plugins: Ext.create('Deppon.ux.ButtonLimitingPlugin', {
  			  //设定间隔秒数,如果不设置，默认为2秒
  			  seconds: 5
  			}),
            handler:function(){
            	var updateCurrentWaybill = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
            	var airAssembleType = updateCurrentWaybill.findField('airAssembleType').getValue();
	            //判断配载类型如为单独开单或者合大票时承运人外发代理和制单费不是必输项
            	if(airAssembleType=='DDKD' || airAssembleType=='HDP'){
	            	updateCurrentWaybill.findField('agencyName_wf').allowBlank=true;
	            }
	            //20130319修改在保存时提示必输项没有输入的具体名称。
            	var fieldElements = updateCurrentWaybill._fields;
            	for(var i = 0 ;i < fieldElements.length; i++){
            		if(fieldElements.items[i].allowBlank == false && (fieldElements.items[i].value == null || fieldElements.items[i].value == '')){
            			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
                				,fieldElements.items[i].fieldLabel + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.notInput'));
                		return false;
            		}  
            	}
            	if(!airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().isValid()){
            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
            				airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.validError'));
            		return false;
            	}
            	var record = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord();
            	var saveRecord = airfreight.airQueryWaybill.waybillInfo.store.data.items;
            	record.data.modifyTime = null;
            	airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().updateRecord(record);
            	//2013-07-10 ISSUE-3269 允许不包含运单的航空正单保存，并且不包含运单的航空正单中重量体积和件数允许为0
//            	if(saveRecord.length==0 || saveRecord.length==''){
//            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
//            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.pleaseOptionSubmitWaybillDetail'));
//            		return false;
//            	}
            	var formatDate = function(value, formatStr) {
	    			if(!formatStr) formatStr = 'Y-m-d';
	    			return Ext.Date.format(new Date(value), formatStr); 
	    		};
	    		var convertFlightDate = updateCurrentWaybill.findField('flightDate').getValue();
	    		var flightDate = formatDate(convertFlightDate);
	    		var takeOffTime = updateCurrentWaybill.findField('takeOffTime').getValue();
            	var arriveTime = updateCurrentWaybill.findField('arriveTime').getValue();
            	var arriveDate = flightDate +' '+ formatDate(arriveTime,'H:i')+':00';
            	var takeOffDate = flightDate +' '+ formatDate(takeOffTime,'H:i')+':00';
            	record.data.arriveTime = arriveDate;
            	record.data.takeOffTime = takeOffDate;
            	var jsonData = new Array();
             	var addSerialNoMap = airfreight.airQueryWaybill.addSerialNoMap;
        		var delSerialNoMap = airfreight.airQueryWaybill.delSerialNoMap;
        		var addSerialMap = new Ext.util.HashMap();
        		var delSerialMap = new Ext.util.HashMap();
            	//遍历航空正单list集合
            	for(var i=0;i<saveRecord.length;i++){
            		var addSerialArray = new Array();
            		var delSerialArray = new Array();
            		var sRecord = saveRecord[i].data;
            		var addSerialNoList = addSerialNoMap.get(sRecord.waybillNo+sRecord.id);
            		var delSerialNoList = delSerialNoMap.get(sRecord.waybillNo+sRecord.id);
            		if(addSerialNoList!=null){
	            		for(var j=0;j<addSerialNoList.length;j++){
	            			var addList = addSerialNoList[j];
	            			if(addList.flag=='add' && addList.operateFlag=='Y'){
	            				//需要提交新增的流水号
	            				addSerialArray.push(addList.serialNo + ',' +addList.stockStatus)
	            			}
	            		}
	            		addSerialMap.add(sRecord.waybillNo,addSerialArray);
            		}else{
            			addSerialMap.add(null,null);
            		}
            		if(delSerialNoList!=null){
	            		for(var k=0;k<delSerialNoList.length;k++){
	            			var delList = delSerialNoList[k];
	            			if(delList.flag=='del' && delList.operateFlag=='N'){
	            				//需要提交新增的流水号
	            				delSerialArray.push(delList.serialNo)
	            			}
	            		}
	            		delSerialMap.add(sRecord.waybillNo,delSerialArray);
            		}else{
            			delSerialMap.add(null,null);            			
            		}
            		jsonData.push(sRecord);
            	}
            	
            	//判断配载类型如为单独开单或者单独开单外发时不允许提交多个运单明细
            	if(airAssembleType=='DDKD' || airAssembleType=='DDWFD'){
        			if(saveRecord.length>1){
	            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.CanAlsoChooseDifferentType'));
	            		return false;
        			}
	            }
	            //add  2013-06-08如果手工输入目的站文本框 不为空，则保存手工输入的目的站
	            var airportLocationName = updateCurrentWaybill.findField('airportLocationName').getValue();
	            if(!Ext.isEmpty(airportLocationName)){
	            	record.data.arrvRegionName = airportLocationName;
	            }
                //设置运输性质
                if(!Ext.isEmpty(airfreight.airQueryWaybill.transportType)){
                    record.data.transportType = airfreight.airQueryWaybill.transportType;
                }
                if(record.data.transportType=='PACKAGE_AIR'){
                	record.data.productCode = 'DEAP';
                }
	            record.data.airWaybillNo= Ext.String.trim(record.data.airWaybillNo);
                //设置到达部门名称
                var dedtOrgName = updateCurrentWaybill.findField('dedtOrgName').rawValue;
                var dedtOrgCode = updateCurrentWaybill.findField('dedtOrgName').getValue();
                record.data.dedtOrgName = dedtOrgName;
                record.data.destOrgCode = dedtOrgCode;
            	//收集航空正单、明细、流水
            	var airWayBilEntityList = {pointsSingleJointTicketVO:{billDetailList:jsonData,billEntity:record.data,addParameterMap:addSerialMap.map,delParameterMap:delSerialMap.map}};
            	Ext.Ajax.request({
            		url:airfreight.realPath('modifyAirWaybill.action'),
            		jsonData:airWayBilEntityList,
    				success:function(response){
    					var result = Ext.decode(response.responseText);
    					//保存航空正单id
    					var aiyWayBillId = result.pointsSingleJointTicketVO.ticketDto.aiyWayBillId;
    					//保存运单明细id数组
    					var waybillIds = result.pointsSingleJointTicketVO.ticketDto.waybillIds;
    					var printMap = new Ext.util.HashMap();
    					printMap.add('aiyWayBillId', aiyWayBillId);
    					printMap.add('waybillIds', waybillIds);
    					airfreight.airQueryWaybill.operateStatus = "0";
						airfreight.airQueryWaybill.airwayBillIdMaps = printMap;
						Ext.ux.Toast.msg(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.saveSuccess'));
						airfreight.airQueryWaybill.updateWaybillWeightVolumeMap.clear();
						airfreight.airQueryWaybill.isNotSaveSerialNoMap.clear();
						airfreight.airQueryWaybill.addSerialNoMap.clear();
						airfreight.airQueryWaybill.delSerialNoMap.clear();
						airfreight.airQueryWaybill.viewMap.clear();
						airfreight.airQueryWaybill.updateWaybillWeightVolumeMap.clear();
						airfreight.airQueryWaybill.isNotSaveSerialNoMap.clear();
						airfreight.airQueryWaybill.addSerialNoMap.clear();
						airfreight.airQueryWaybill.delSerialNoMap.clear();
                        airfreight.airQueryWaybill.transportType = '';
    				},
    				exception:function(response){
    					var result = Ext.decode(response.responseText);
    					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
    					return false;
    				}
            	});
            }
        }]
    }]
}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
});

//运单明细列表
Ext.define('Foss.airfreight.waybillInfo',{
	extend:'Ext.grid.Panel',
	frame:true,
	border:false,
	autoScroll:true,
	width:1025,
	height:250,
	//layout:'column',
	emptyText:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
	defaults:{
		 sortable: true,
		 flex: 1
	},
	columns:[{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
		dataIndex: 'waybillNo',
		flex: 0.9
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
		dataIndex: 'planFlightNo',
		flex: 0.8,
		renderer:function(value){
			if(value.trim() == ''){
				return null;
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
			}
		}
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
		dataIndex: 'makeWaybillWay',
		flex: 0.8,
	    renderer:function(v){
	      	if(v=='HDP'){
	      		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
	      	}else if(v == ' '){
            	return null;
            }else{
	      		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
	      	}
	    }
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
		dataIndex: 'arrvRegionName',
		flex: 0.7
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.measurement'),
		flex: 1,
		dataIndex: 'measurement'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
		flex: 0.8,
		dataIndex: 'grossWeight'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
		flex: 0.8,
		dataIndex: 'billingWeight'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
		flex: 0.7,
		dataIndex: 'volume'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
		flex: 0.7,
		dataIndex: 'goodsQty'
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
		flex: 1.2,
		dataIndex: 'goodsName'
	},{
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
        dataIndex: 'stockStatus',
        flex: 0.8,
        renderer:function(value){
			if(value=='NOTSTORE'){//未入库
				 return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage');
			}else if(value=='INSTORE'){ //库存中
				return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.instore');
			}
		}
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storageItem'),
        dataIndex: 'transportRemark',
        flex: 1.3
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
        dataIndex: 'receiveOrgName',
        flex: 1.5
	}],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaultType:'button',
		   items:[{
			   xtype:'container',
			   html:'&nbsp;',
			   columnWidth:.86
		   },{
			   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.remove'),
			   columnWidth:.06,
			   handler: function(){
				   record = airfreight.airQueryWaybill.waybillInfo.getSelectionModel().getSelection();
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
							   ,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.inexistence'));
					   return false;
				   }
				   airfreight.airQueryWaybill.waybillInfo.store.remove(record);
				   var updateRecord = airfreight.airQueryWaybill.waybillInfo.store.data.items;
				   var showGridRecordLength = updateRecord.length;
				   var grossWeight = 0;
				   var billingWeight = 0;
				   var goodsQty = 0;
				   var volume = 0;
				   var goodsNameStr = '';
				   var goodsName = '';
				   var goodsNameMap = new Ext.util.HashMap();
				   var getRecordValues =  airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
				   if(showGridRecordLength!=0){
					   for(var i = 0 ; i < showGridRecordLength; i++){
						   grossWeight = grossWeight + updateRecord[i].data.grossWeight;
						   billingWeight = billingWeight + updateRecord[i].data.billingWeight;
						   goodsQty = goodsQty + updateRecord[i].data.goodsQty;
						   volume = volume + updateRecord[i].data.volume;
						   goodsName =  updateRecord[i].data.goodsName;
					   	    //如果第一行
						   if(i==0){
							   goodsNameStr = goodsNameStr + goodsName; //拼接货物名称
							   goodsNameMap.add(goodsName,goodsName);
						   }else{
						   	   if(!goodsNameMap.get(goodsName)){
						   	   	   goodsNameStr = goodsNameStr + ' ' + goodsName;  //拼接' '+货物名称
						   	   	   goodsNameMap.add(goodsName,goodsName);
						   	   }
						   }
					   }
					   getRecordValues.findField('goodsName').setValue(goodsNameStr);
					   getRecordValues.findField('grossWeight').setValue(airfreight.airQueryWaybill.fomatFloat(grossWeight,1));
					   getRecordValues.findField('billingWeight').setValue(airfreight.airQueryWaybill.fomatFloat(billingWeight,1));
					   airfreight.airQueryWaybill.airwayBillRecord.data.billingWeight=airfreight.airQueryWaybill.fomatFloat(billingWeight,1);
					   getRecordValues.findField('goodsQty').setValue(goodsQty);
					   getRecordValues.findField('volume').setValue(airfreight.airQueryWaybill.fomatFloat(volume,2));
					   airfreight.airQueryWaybill.airwaybillCalculate(airfreight.airQueryWaybill.airwayBillRecord);
				   }else{
					   getRecordValues.findField('grossWeight').reset();
					   getRecordValues.findField('billingWeight').reset();
					   getRecordValues.findField('goodsQty').reset();
					   getRecordValues.findField('volume').reset();
				   }
			   }
		   },{
			   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket'),
			   columnWidth:.08,
			   handler : function(){
				   airfreight.airQueryWaybill.hpWindow = Ext.create('Foss.airfreight.hpWindow').show();
                   //TODO
				   var records = airfreight.airQueryWaybill.waybillInfo.store.data.items;
                   //子页面Form
                   if(!Ext.isEmpty(airfreight.airQueryWaybill.transportType)){
                       airfreight.airQueryWaybill.waybillFindForm.getForm().findField('transportType').setValue(airfreight.airQueryWaybill.transportType);
                   }else{
                       airfreight.airQueryWaybill.waybillFindForm.getForm().findField('transportType').setValue('PRECISION_AIR');
                   }
                   //子页面store
				   airfreight.airQueryWaybill.waybillJointTicketInfo.store.loadData(records);
			   }
		   }]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.store = Ext.create('Foss.airfreight.waybillInfoStore');
		me.callParent([cfg]);
	}
});



//待选信息、合票信息model
Ext.define('Foss.airfreight.singlejointticketModel',{
	extend: 'Ext.data.Model',
	fields: [
	        {name: 'id',type:'string'},
	     	{name: 'waybillNo',type:'string'},
			{name: 'makeWaybillWay',type:'string'},
			{name: 'goodsQty',type:'number'},
			{name: 'arrvRegionName',type:'string'},
			{name: 'measurement',type:'number'},
			{name: 'grossWeight',type:'number'},
			{name: 'billingWeight',type:'number'},
			{name: 'volume',type:'number'},
			{name: 'goodsName',type:'string'},
			{name: 'planFlightNo',type:'string'},
			{name: 'collectionFee',type:'number'},
			{name: 'arrivalFee',type:'number'},
			{name: 'pickupType',type:'string'},
			{name: 'deliverFee', type:'number'},
			{name: 'receiverAddress', type:'string'},
			{name: 'receiverContactPhone', type:'string'},
			{name: 'receiverName', type:'string'},
			{name: 'stockStatus',type:'string'},
			{name: 'receiveOrgName', type:'string'},
			{name: 'transportRemark', type:'string'},
			{name: 'departTime', type:'date',convert:dateConvert},
		    {name: 'unitPrice', type:'string'},
		    {name:'lockRemark',type:'string'},//锁票备注
			{name:'lockStatus',type:'string'},//锁票状态
			{name:'goodsQtyTotal',type:'string'},//开单总件数
			{name:'stockQty',type:'string'},//开单总件数
            {name:'transportType',type:'string'},//运输性质
            {name:'productCode',type:'string'}//产品性质

	]
});

Ext.define('Foss.airfreight.singlejointticketStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.singlejointticketModel',
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url:airfreight.realPath('wayBillFind.action'),
		reader : {
			type : 'json',
			root : 'pointsSingleJointTicketVO.volumeResult',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = airfreight.airQueryWaybill.waybillFindForm.getValues();
			Ext.apply(operation,{
				params : {
					'pointsSingleJointTicketVO.ticketDto' : n.arrvRegionName
				}
			});			
		},
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//总件数
			var goodsQtyTotal = 0;
			//毛重合
			var weightTotal = 0;
			//计费重量
			var billingWeightTotal = 0;
			//体积
			var volumeTotal = 0;
			//总票数
			var billNoTotal = totalArray.length;
			
			for(var i=0;i<totalArray.length;i++){
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				weightTotal = weightTotal + Ext.Number.from(totalArray[i].data.grossWeight,0);
				billingWeightTotal = billingWeightTotal + Ext.Number.from(totalArray[i].data.billingWeight,0);
				volumeTotal = volumeTotal + Ext.Number.from(totalArray[i].data.volume,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(weightTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
				}else if(count==3){
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(billingWeightTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
				}else{
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(volumeTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.cube'));
				}
				count ++;
			}
		}
	}
});


//运单明细store
Ext.define('Foss.airfreight.waybillInfoStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.singlejointticketModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});


//合票信息stroe
Ext.define('Foss.airfreight.waybillJointticketStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.singlejointticketModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	},
	listeners: {
		datachanged: function(store, operation, eOpts){
			var totalArray = store.data.items;
			//总件数
			var goodsQtyTotal = 0;
			//毛重合
			var weightTotal = 0;
			//计费重量
			var billingWeightTotal = 0;
			//体积
			var volumeTotal = 0;
			//总票数
			var billNoTotal = totalArray.length;
			for(var i=0;i<totalArray.length;i++){
				goodsQtyTotal = goodsQtyTotal + Ext.Number.from(totalArray[i].data.goodsQty,0);
				weightTotal = weightTotal + Ext.Number.from(totalArray[i].data.grossWeight,0);
				billingWeightTotal = billingWeightTotal + Ext.Number.from(totalArray[i].data.billingWeight,0);
				volumeTotal = volumeTotal + Ext.Number.from(totalArray[i].data.volume,0);
			}
			var count = 0;
			var toolbarArray = airfreight.airQueryWaybill.waybillJointTicketInfo.down('toolbar').query('textfield');
			for(var j=0;j<toolbarArray.length;j++){
				if(count==0){
					toolbarArray[j].setValue(billNoTotal);
				}else if(count==1){
					toolbarArray[j].setValue(goodsQtyTotal);
				}else if(count==2){
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(weightTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
					toolbarArray[j].hideValue = airfreight.airQueryWaybill.fomatFloat(weightTotal,1);
				}else if(count==3){
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(billingWeightTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.kilo'));
					toolbarArray[j].hideValue = airfreight.airQueryWaybill.fomatFloat(billingWeightTotal,1);
				}else{
					toolbarArray[j].setValue(airfreight.airQueryWaybill.fomatFloat(volumeTotal,1) + '  ' + airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.cube'));
					toolbarArray[j].hideValue = airfreight.airQueryWaybill.fomatFloat(volumeTotal,1);
				}
				count ++;
			}
		}
	}
});

//分单待选信息列表
Ext.define('Foss.airfreight.cesareanDeliveryWaybillInfo',{
	extend:'Ext.grid.Panel',
	title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.waitoption'),
	//layout:'column',
	frame:true,
	border:true,
	autoScroll:true,
	height:300,
	emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
	defaults:{
		//autoScroll: true
		sortable: true,
		//width : 50
		flex: 1
	},
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var makeWaybillWay = record.get('makeWaybillWay');
			if(makeWaybillWay=='DDKD'){
    			return 'x-grid-record-yellow';
			}
			var stockStatus = record.get('stockStatus');
			if(stockStatus=='NOTSTORE'){
    			return 'x-grid-record-gray';
			}
		}
	},
	columns:[{
		xtype : 'actioncolumn',
		//width : 50,
		flex: 1.2,
		text : '操作',
		align : 'center',
		items : [{
		    iconCls : 'foss_icons_stl_noFreeze',
		    handler:function(grid,rowIndex,colIndex,item,e){
		    	
		    	var waybillNo = grid.store.getAt(rowIndex).get('waybillNo');
		    	var lockStatus= grid.store.getAt(rowIndex).get('lockStatus');
		    	airfreight.airQueryWaybill.lockWaybillNo = waybillNo;
				airfreight.airQueryWaybill.rowIndex=rowIndex;
				airfreight.airQueryWaybill.grid=grid;
		    	if(lockStatus=='Y'){ 
		    		Ext.MessageBox.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
		    		'确认要解锁当前运单吗？',
		    		function(btn){
		    		  if(btn=='yes'){
		    			Ext.Ajax.request({
							url : airfreight.realPath('unlockAirWaybill.action'),
							params : {'pointsSingleJointTicketVO.airLockWaybillDetailEntity.waybillNo' : airfreight.airQueryWaybill.lockWaybillNo
			    					  },
							success:function(response){
					            grid.store.getAt(rowIndex).set('lockStatus','N');
					            grid.store.getAt(rowIndex).set('lockRemark','');
					            airfreight.airQueryWaybill.lockWaybillWindow.hide();
							},
							exception : function(response) {
			    				var result = Ext.decode(response.responseText);
			    				top.Ext.MessageBox.alert('提示',
			    				result.message);
			    			}
			            });
		    	      }
		    	   }
		    	 );
		    		return ;
		    	}
				var x = e.getX();
				var y = e.getY();
				var transportType = grid.store.getAt(rowIndex).get('transportType');
				if(transportType != 'PACKAGE_AIR'){
					if(airfreight.airQueryWaybill.lockWaybillWindow == null){
						airfreight.airQueryWaybill.lockWaybillWindow = Ext.create('Foss.airfreight.airQueryWaybill.lockWaybillWindow');
					}
					airfreight.airQueryWaybill.lockWaybillWindow.showAt(x + 10,y + 10);
				}
			}
		}],
		renderer:function(value, metadata, record){
			lockStatus=record.get('lockStatus');
		      if(lockStatus=='Y'){
		        this.items[0].iconCls = 'foss_icons_stl_freeze';
		      }else{
		      	this.items[0].iconCls = 'foss_icons_stl_noFreeze';
		      }
		}
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
        dataIndex: 'waybillNo',
        //width : 70
        flex: 1.2
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
        dataIndex: 'makeWaybillWay',
        //width : 90,
        flex: 0.8,
        renderer:function(v){
        	if(v=='HDP'){
        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
        	}else if(v == ' '){
            	return null;
            }else{
        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
        	}
        }
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
		dataIndex: 'planFlightNo',
		//width : 90,
		flex: 0.8,
		renderer:function(value){
			if(value.trim() == ''){
				return null;
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
			}
		}
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
        dataIndex: 'arrvRegionName',
        //width : 50
        flex: 0.7
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.measurement'),
        dataIndex: 'measurement',
        //width : 50
        flex: 1
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
        dataIndex: 'grossWeight',
        //width : 100
        flex: 0.8
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
        dataIndex: 'billingWeight',
        //width : 100
        flex: 0.8
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
        dataIndex: 'volume',
        //width : 100
        flex: 0.7
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
        dataIndex: 'goodsQty',
        //width : 50
        flex: 0.7
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
        dataIndex: 'goodsName',
        //width : 50
        flex: 1.2
	},{
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
        dataIndex: 'stockStatus',
        //width : 90,
        flex: 0.8,
        renderer:function(value){
			if(value=='NOTSTORE'){//未入库
				 return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage');
			}else if(value=='INSTORE'){ //库存中
				return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.instore');
			}
		}
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.departTime'),
		//width : 120,
		flex: 2.0,
		dataIndex: 'departTime',//外场交单出发时间
		format: 'Y-m-d H:i:s',
		xtype:'datecolumn'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.unit_price'),
		//width : 50,
		flex: 0.7,
		dataIndex: 'unitPrice'//运单费率
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storageItem'),
        dataIndex: 'transportRemark',
        //width : 90
        flex: 1.3
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
        dataIndex: 'receiveOrgName',
        //width : 90
        flex: 1.5
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.lockRemark'),//锁票备注
        dataIndex: 'lockRemark',
        //width : 90
        flex: 1.5
	},{xtype:'ellipsiscolumn',
        text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
        dataIndex:'transportType',
        //width : 90,
        flex:1.5,
        renderer:function(value){
            if(value=='PACKAGE_AIR'){
                return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageAir');
            }else if(value =='PRECISION_AIR'){
                return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir');
            }
        }
    }],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 xtype:'textfield',
			 value:'0',
			 readOnly:true,
			 labelWidth:50,
			 width:30
		   },
		   items:[{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.poll'),
			   columnWidth:.10,
			   dataIndex: 'billNoTotal'
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
			   columnWidth:.10,
			   dataIndex: 'goodsQtyTotal'
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
			   columnWidth:.14,
			   dataIndex: 'weightTotal'
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
			   labelWidth:70,
			   columnWidth:.15,
			   dataIndex: 'billingWeightTotal'
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
			   columnWidth:.15,
			   dataIndex: 'volumeTotal'
		   }]
	}],
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.store = Ext.create('Foss.airfreight.singlejointticketStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		airfreight.airQueryWaybill.pagingBar = me.store;
		me.callParent([cfg]);
		me.addListener({ 'itemdblclick': me.itemdblclick, scope: this});
	},
	itemdblclick : function(view,record,item,index,e,eOpts){
		var lockStatus=record.get('lockStatus');
	    var waybillNo=record.get('waybillNo');
	    var transportType=record.get('transportType');
	    //判断是否已锁定
		if(lockStatus=='Y'){
				Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
							'运单'+waybillNo+'未解锁，不允许下移，请先解锁');
			return;
		}
		//判断运输类型
		var items=airfreight.airQueryWaybill.waybillJointTicketInfo.store.data.items;
    	for(var i=0;i<items.length;i++){
    		var productCode=items[i].data.transportType;
    		if(productCode!=transportType){
    			
              Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                 '运单【'+waybillNo+'】与【'+items[i].data.waybillNo+"】 运输类型不同！");
                 return;
    		}
    	}
    	if(transportType!=airfreight.airQueryWaybill.transportType && airfreight.airQueryWaybill.transportType!=""){
    		 Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),'运输类型不同！');
    		 return;
    	}
		var goodsQty=record.get('stockQty');
        var goodsQtyTotal=record.get('goodsQtyTotal');
        var isDown=true;
		//判断当前配载件数是否等于开单件数
		if(goodsQty!=goodsQtyTotal && transportType != 'PACKAGE_AIR'){
		 	Ext.MessageBox.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
		    	'运单'+waybillNo+'开单件数与空运总调库存件数不一致，是否确定快速下移?',
		    		function(btn){
		    		  if(btn == 'yes'){
		    		  	
						  //绑定合票信息
						  airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(record);
						  //移除待选信息
						  airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(record);
	                     return;
		    	      }else{
		    	        return;
		    	      }
		        })
			
		}else{
		  //绑定合票信息
		  airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(record);
		  //移除待选信息
		  airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(record);
		}
		
		
	}
});
//定义空运锁票备注窗口
Ext.define('Foss.airfreight.airQueryWaybill.lockWaybillWindow',{
	extend : 'Ext.window.Window',
	closeAction : 'hide',
	resizable : false,
	width : 400,
	height:200,
	draggable : false,//拖动
	modal : true,
	title : '空运锁票',
	items : [{
		fieldLabel : '锁票备注',
		id:'airfreight.airQueryWaybill.lockRemark',
		name:'airfreight.airQueryWaybill.lockRemark',
		xtype : 'textarea',
		maxLength:1000,
		width:300,
		allowBlank:false,
		readOnly : false
	}],
	buttons:[
		{ text: '确认',
          handler:function(cmp,obj){
          	var grid=airfreight.airQueryWaybill.grid;
		    var rowIndex=airfreight.airQueryWaybill.rowIndex;
		    var lockRemark=Ext.query('[name=airfreight.airQueryWaybill.lockRemark]')[0].value
		    if(lockRemark==null || lockRemark==''){
		       Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
							'请输入锁票备注');
				return;
		    }
          	Ext.Ajax.request({
				url : airfreight.realPath('addAirLockWaybillDetail.action'),
				params : {'pointsSingleJointTicketVO.airLockWaybillDetailEntity.waybillNo' : airfreight.airQueryWaybill.lockWaybillNo,
    					   'pointsSingleJointTicketVO.airLockWaybillDetailEntity.lockRemark': lockRemark
    					  },
				success:function(response){
		            grid.store.getAt(rowIndex).set('lockStatus','Y');
		            grid.store.getAt(rowIndex).set('lockRemark',lockRemark);
		            airfreight.airQueryWaybill.lockWaybillWindow.hide();
		            Ext.query('[name=airfreight.airQueryWaybill.lockRemark]')[0].value='';
				},
				exception : function(response) {
    				var result = Ext.decode(response.responseText);
    				top.Ext.MessageBox.alert('提示',
    				result.message);
    			}
			});
          
          }
        }
	]

 });

Ext.define('Foss.airfreight.operateWaybillInfo',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	height:50,
	frame: false,
	defaults:{
		xtype:'textfield'
	},
	   items:[{
		   fieldLabel:'',
		   readOnly:true,
		   columnWidth:.25
	   },{
		    xtype : 'button',
			iconCls : 'up-airfreight',
			tooltip:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.upmove'),
			width:50,
			handler: function(){
				records = airfreight.airQueryWaybill.waybillJointTicketInfo.getSelectionModel().getSelection();
				if(records.length!=0){
					var submenuGtasks = airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store;
					var submenuRecord = submenuGtasks.data.items.length;
					var jointTicketInformation = airfreight.airQueryWaybill.waybillJointTicketInfo.store;
					var jointTicketRecord = jointTicketInformation.data.items.length;
					airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.add(records);
					airfreight.airQueryWaybill.waybillJointTicketInfo.store.remove(records);
					
				}else {
					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
							,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionJointticket'));
				}
			}
	   },{
		   fieldLabel:'',
		   readOnly:true,
		   columnWidth:.10
	   },{
			xtype : 'button',
			iconCls : 'down-airfreight',
			tooltip:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.nextmove'),
			width:50,
			handler : function(){
				records = airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.getSelectionModel().getSelection();
				var waybillNoList='';
				var isQtyEqual=true;
				if(records.length!=0){
				  for(var i=0;i<records.length;i++){
					   var lockStatus=records[i].get('lockStatus');
	                    var waybillNo=records[i].get('waybillNo');
	                   //判断是否锁票
	       				if(lockStatus=='Y'){
	       					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	       					,'运单'+waybillNo+'未解锁，不允许下移，请先解锁');
	       				  return;
	       				} 
	       			  	var goodsQty=records[i].get('stockQty');
				        var goodsQtyTotal=records[i].get('goodsQtyTotal');
				        var productCode=records[i].get('transportType');
						//判断当前配载件数是否等于开单件数
						if(goodsQty!=goodsQtyTotal && productCode != 'PACKAGE_AIR'){
							waybillNoList+=waybillNo+','
							isQtyEqual=false;
						}
						
						//判断提交合票信息中是否运输性质全都一致
		                var items=airfreight.airQueryWaybill.waybillJointTicketInfo.store.data.items;
		                for(var j=0;j<items.length;j++){
		                
		                	if(items[j].data.transportType!=productCode ){
		                		 Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
		                                 '运单【'+waybillNo+'】与【'+items[j].data.waybillNo+"】 运输类型不同！");
		                                 return;
		                	}
		                }
		                if(productCode !=airfreight.airQueryWaybill.transportType && airfreight.airQueryWaybill.transportType!=""){
		           		 	Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),'运输类型不同！');
		           		 	return;
		                }
			
					}
					//处理waybillNoList显示格式
					var len=waybillNoList.length;
					waybillNoList=waybillNoList.substring(0,len-1);
					if(isQtyEqual){
					   //绑定合票信息
					   airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(records);
					   airfreight.airQueryWaybill.vtype(records);
					   //移除待选信息
					   airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(records);
					 
					}else{
					    Ext.Msg.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
						    	'运单{ '+waybillNoList+' }开单件数与空运总调库存件数不一致，是否确定下移?',
						    		function(btn){
						    		  if(btn=='yes'){
						    		  	//绑定合票信息
									   airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(records);
									   airfreight.airQueryWaybill.vtype(records);
									   //移除待选信息
									   airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(records);
										    	      }else{
						    	        return;
						    	      }
						})
					}
				
				}else{
					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
							,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionWaitJointticket'));
				}
			}
	   },{
		   fieldLabel:'',
		   readOnly:true,
		   columnWidth:.15
	   },{
		   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
		   name:'waybillNo',
		   columnWidth:.25
	   },{
		   xtype:'button',
		   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.fastpagedown'),
		   handler: function (){
				var waybillNo = this.up('form').getForm().findField('waybillNo').getValue();
        		var getSubmenuGtasks = airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.data.items;
        		var jointTicketInformation = airfreight.airQueryWaybill.waybillJointTicketInfo.store.data.items;
        		var inexistenced = false;
        		var inexistenceh = false;
        		var gtyIsEqual=true;//默认当前配载件数与开单总件数相等
       		    var index=0;//记住快速移动的下标
        		if(waybillNo.length==0){
        			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
        					,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ydhbnwk'));
        			return false;
        		}
        		if(getSubmenuGtasks.length<=0){
        			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
        					,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.dxlbbczsj'));
        			return false;
        		}
        		if(jointTicketInformation.length!=0){
        			for(var j=0;j<jointTicketInformation.length;j++){
        				if(waybillNo==jointTicketInformation[j].data.waybillNo){
        					inexistenceh = true;
        				}
        			}
        		}
        		if(inexistenceh==true){
        			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
        					,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.hdxxlbbcz')+waybillNo+airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ydhbnxy'));
        			return false;
        		}
        		for(var i=0;i<getSubmenuGtasks.length;i++){
        			//如果当前待选列表中存在此数据则遍历出相等的进行绑定
        			if(waybillNo==getSubmenuGtasks[i].data.waybillNo){
        			  inexistenced = true;
	       			  index=i;
	       				var lockStatus = getSubmenuGtasks[i].data.lockStatus;
	       				if(lockStatus=='Y'){
	       					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	       					,'该运单已经被锁定，无法快速下移，请先解锁');
	       					return;
	       				}
	       				var goodsQty=getSubmenuGtasks[i].data.stockQty;
					    var goodsQtyTotal=getSubmenuGtasks[i].data.goodsQtyTotal;
					    var transportType = getSubmenuGtasks[i].data.transportType;
						//判断当前配载件数是否等于开单件数
						if(goodsQty!=goodsQtyTotal && transportType != 'PACKAGE_AIR'){
							gtyIsEqual=false;
						}
        			}
        		}
        		//判断提交合票信息中是否运输性质全都一致
                var items=airfreight.airQueryWaybill.waybillJointTicketInfo.store.data.items;
                for(var i=0;i<items.length;i++){
                
                	if(items[i].data.transportType!=getSubmenuGtasks[index].data.transportType){
                	
                		 Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
                                 '运单【'+getSubmenuGtasks[index].data.waybillNo+'】与【'+items[i].data.waybillNo+"】 运输类型不同！");
                                 return;
                	}
                }
        		if(inexistenced==false){
    				Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
    						,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.dxlbbcz')+waybillNo+airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.bczdydh'));
    				return false;
    			}
    		  if(!gtyIsEqual){
   			  	 Ext.Msg.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),
			        '运单'+waybillNo+'开单件数与空运总调库存件数不一致，是否确定快速下移?',
					  function(btn){
						 if(btn=='yes'){
						   airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(getSubmenuGtasks[index]);
        				   airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(getSubmenuGtasks[index]);
						  }else{
						    return;
						  }
					})
   			  
	   			}else{
	   			    airfreight.airQueryWaybill.waybillJointTicketInfo.store.add(getSubmenuGtasks[index]);
        			airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.remove(getSubmenuGtasks[index]);	
	   			}
		   }
	   }],
	   constructor: function(config){
			var me = this,
				cfg = Ext.apply({},config);
			me.selModel = Ext.create('Ext.selection.CheckboxModel'),
			me.callParent([cfg]);
		}
});


Ext.define('Foss.airfreight.serialDetailModel',{
	extend:'Ext.data.Model',
	fields:[{
		name:'serialNo',
		type:'string'
	},{
		name:'operatingStatus',
		type:'string'
	},{
		name:'goodsWeightTotal',
		type:'number'
	},{
		name:'goodsVolumeTotal',
		type:'number'
	},{
		name:'operateFlag',
		type:'string'
	},{
		name:'flag',
		type:'string'
	},{
		name:'viewFlag',
		type:'string'
	},{
		name:'id',
		type:'string'
	},{
		name:'stockStatus',
		type:'string'
	}]
})


Ext.define('Foss.airfreight.serialDetailStore',{
	extend: 'Ext.data.Store',
	model:'Foss.airfreight.serialDetailModel',
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items'
		}
	}
});

//航空货量流水明细列表
Ext.define('Foss.airfreight.waybillSerialNoWindow',{
	extend:'Ext.window.Window',
	width:600,
	height:380,
	title : airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetail'),
	showGrid : null,
	showForm : null,
	closeable: true,
	waybillNo: null,
	re:null,
	modal:true,
	closeAction:'hide',
	layout:'hbox',
	createGrid : function() {
		if(this.showGrid) {
			return this.showGrid; 
		}
		var serialStore = Ext.create('Foss.airfreight.serialDetailStore');
		this.showGrid = Ext.create('Ext.grid.Panel',{
			frame:true,
			border:true,
			autoScroll:true,
			width:300,
			height:300,
			store: serialStore,
			emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
			columns:[{
				text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.serialNumber'),
		        dataIndex: 'serialNo',
		        flex: 1.5
			},{
				text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
		        dataIndex: 'stockStatus',
		        flex: 1.5,
		        renderer : function(v, metadata, record, rowIndex, columnIndex, store){
		        	if(v=='NOTSTORE'){
		        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage');
		        	}else{
		        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.instore');
		        	}
		        }
			},{
				text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.operate'),
		        dataIndex: 'operatingStatus',
		        flex: 1.5,
		        renderer: function(v, metadata, record, rowIndex, columnIndex, store) {
		        	if(rowIndex==0){
		        		airfreight.airQueryWaybill.serialNoTotals = store.data.items.length;
		        	}
		        	var records = record.data;
		        	var initAddStyle = 'blue';  
		        	var initDelStyle = 'gray';
	        		if(records.viewFlag == 'N' && records.flag=='add'){
	        			initAddStyle = 'gray';  
			        	initDelStyle = 'blue';
	        		}else if(records.viewFlag == 'Y' && records.flag=='add'){
	        			initAddStyle = 'blue';  
			        	initDelStyle = 'gray';
	        		}else if(records.viewFlag == 'N' && records.flag=='del'){
	        			initAddStyle = 'gray';  
			        	initDelStyle = 'blue';
	        		}else if(records.viewFlag == 'Y' && records.flag=='del'){
	        			initAddStyle = 'blue';  
			        	initDelStyle = 'gray';
	        		}
		        	var formatStrAdd = '<a href="javascript:airfreight.airQueryWaybill.addAirwayBillSerialNoDetail(\''+'add'+record.data.serialNo+'\',\''+record.data.id+'\',\''+record.data.flag+'\');" style="color:'+initAddStyle+';" id=add'+record.data.id+record.data.serialNo+' name=add'+record.data.id+record.data.serialNo+'>'+airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.addTwo')+'</a>';
		        	var formatStrDelete = '<a href="javascript:airfreight.airQueryWaybill.addAirwayBillSerialNoDetail(\''+'del'+record.data.serialNo+'\',\''+record.data.id+'\',\''+record.data.flag+'\');" style="color:'+initDelStyle+';" id=del'+record.data.id+record.data.serialNo+' name=del'+record.data.id+record.data.serialNo+'>'+airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.delete')+'</a>';
		        	return formatStrAdd+formatStrDelete;
				}
			}]
		});
		return this.showGrid;
	},
	createForm : function(){
		if(this.showForm) {
			return this.showForm; 
		}
		this.showForm = Ext.create('Ext.form.Panel',{
			frame: false,
			border: false,
			width:300,
			layout: 'column',
			columns: 1,
			defaults:{
				margin:'10 5 5 50'
			},
			items: [{
				xtype: 'textfield',
				fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
				name: 'goodsWeightTotal',
				vtype:'goodsWeightTotal',
				labelWidth:60,
				width:200
			},{
				xtype: 'textfield',
				fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
				name:'goodsVolumeTotal',
				vtype:'goodsVolumeTotal',
				labelWidth:60,
				width:200
			},{
				xtype: 'textfield',
				fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
				name: 'goodsBillingWeightTotal',
				vtype:'goodsWeightTotal',
				hidden:true,
				labelWidth:60,
				width:200
			},{
				xtype:'container',
				margin:'10 5 5 60'
			},{
				xtype:'button',
				text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ensure'),
				labelWidth:20,
				width:50,
				handler:function(){
					var validateWeight = function(value){
						var str = value+'';
				    	if(str==0)
				    		return false;
				    	if(str.substring(0,1)=='-')
				    		return false;
			    		if(str.substring(0,2)=='00')
				    		return false;
			    		if(str.indexOf("..")==1)
			    			return false;
			    		var isNotNumber = str.substring(str.length-1,str.length);
			    		if(!/^[0-9]+$/.test(isNotNumber))
			    			return false;
			    		return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(value,0));
					}
					var validateVolume = function(value){
						var str = value+'';
				    	if(str==0)
				    		return false;
				    	if(str.substring(0,1)=='-')
				    		return false;
			    		if(str.substring(0,2)=='00')
				    		return false;
			    		if(str.indexOf("..")==1)
			    			return false;
			    		var isNotNumber = str.substring(str.length-1,str.length);
			    		if(!/^[0-9]+$/.test(isNotNumber))
			    			return false;
			    		return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(value,0));
					}
					var formValue = this.up('form').getForm();
					var count = airfreight.airQueryWaybill.airTotalCountMap.get(airfreight.airQueryWaybill.currentWaybillNo+airfreight.airQueryWaybill.currentWaybillId);
					var arry = airfreight.airQueryWaybill.updateWaybillWeightVolumeMap.get(formValue.findField('id').getValue());
					arry.data.grossWeight=formValue.findField('goodsWeightTotal').getValue();
					arry.data.volume=formValue.findField('goodsVolumeTotal').getValue();
					arry.data.billingWeight=formValue.findField('goodsBillingWeightTotal').getValue();
					arry.data.goodsQty = count;
					if(!validateWeight(arry.data.grossWeight)){
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.grossWeight'));
						return false;
					}
					if(!validateVolume(arry.data.volume)){
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.volum'));
						return false;
					}
					airfreight.airQueryWaybill.waybillJointTicketInfo.store.update(arry);
					Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
							,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.updateSuccess'));
					airfreight.airQueryWaybill.serialNoDetail.close();
				}
			},{
				xtype: 'textfield',
				hideLabel:true,
				name: 'id',
				hidden:true
			}]
		});
		return this.showForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		airfreight.airQueryWaybill.vtypesTotal = Ext.apply(Ext.form.field.VTypes, {
		    goodsWeightTotal : function(val,field){
		    	var str = val+'';
		    	if(str==0)
		    		return false;
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,1}$/.test(Ext.Number.from(val,0));
		    	
		    },
		    goodsVolumeTotal : function(val,field){
		    	var str = val+'';
		    	if(str==0)
		    		return false;
		    	if(str.substring(0,1)=='-')
		    		return false;
	    		if(str.substring(0,2)=='00')
		    		return false;
	    		if(str.indexOf("..")==1)
	    			return false;
	    		var isNotNumber = str.substring(str.length-1,str.length);
	    		if(!/^[0-9]+$/.test(isNotNumber))
	    			return false;
		    	return /^-?\d+\.?\d{0,2}$/.test(Ext.Number.from(val,0));
		    },
		    goodsWeightTotalText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.weight'),
		    goodsVolumeTotalText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.volum')
		});
		me.createGrid();
		me.createForm();
		me.add([me.showGrid,me.showForm]);
		
	}
});
//合票信息
Ext.define('Foss.airfreight.waybillJointTicketInfo',{
	extend:'Ext.grid.Panel',
	title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.houseMessage'),
	//layout:'column',
	frame:true,
	border:true,
	autoScroll:true,
	height:280,
	emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
	defaults:{
		sortable: true,
		flex: 1
	},
	viewConfig: {
        stripeRows: false,
		getRowClass: function(record, rowIndex, rp, ds) {
			var makeWaybillWay = record.get('makeWaybillWay');
			if(makeWaybillWay=='DDKD'){
    			return 'x-grid-record-yellow';
			}
			var stockStatus = record.get('stockStatus');
			if(stockStatus=='NOTSTORE'){
    			return 'x-grid-record-gray';
			}
		}
	},
	columns:[{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'deppon_icons_edit',
			handler: function(grid, rowIndex, colIndex){
				var record = grid.getStore().getAt(rowIndex);
				var currentTotalgoodsQty = record.data.goodsQty;
				var currentWaybillNo = record.data.waybillNo;
				var currentWaybillId = record.data.id;
				airfreight.airQueryWaybill.currentWaybillNo = currentWaybillNo;
				airfreight.airQueryWaybill.currentWaybillId = currentWaybillId;
				var params = {
						'pointsSingleJointTicketVO.ticketDto.waybillNo': record.data.waybillNo,
						'pointsSingleJointTicketVO.ticketDto.airWaybillDetailId': record.data.id
    			};
				Ext.Ajax.request({
					url:airfreight.realPath('queryWithModifySerailNo.action'),
	    			params:params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						//绑定运单明细数据
						if(!airfreight.airQueryWaybill.createForm){
							var serialWindow =Ext.create('Foss.airfreight.waybillSerialNoWindow',{'waybillNo':record.data.waybillNo});
							airfreight.airQueryWaybill.serialNoDetail = serialWindow;
						}
						airfreight.airQueryWaybill.serialNoDetail.show();
						var addType = result.pointsSingleJointTicketVO.ticketDto.addAirSerialNoDetailList;
						var delType = result.pointsSingleJointTicketVO.ticketDto.delAirSerialNoDetailList;
						var viewRecord = result.pointsSingleJointTicketVO.ticketDto.airSerialNoViewDetailList;
						airfreight.airQueryWaybill.addAirSerialNoList = result.pointsSingleJointTicketVO.ticketDto.addAirSerialNoDetailList;
						airfreight.airQueryWaybill.delAirSerialNoList = result.pointsSingleJointTicketVO.ticketDto.delAirSerialNoDetailList;
						airfreight.airQueryWaybill.currentOperatewaybillNo = airfreight.airQueryWaybill.serialNoDetail;
						var viewMap = airfreight.airQueryWaybill.viewMap;
						if(viewMap.get(currentWaybillNo+currentWaybillId)==null ||
								viewMap.get(currentWaybillNo+currentWaybillId).length==0){
							viewMap.add(currentWaybillNo+currentWaybillId,viewRecord);
						}
						var viewRecord = viewMap.get(currentWaybillNo+currentWaybillId);
						airfreight.airQueryWaybill.viewMap = viewMap;
						airfreight.airQueryWaybill.serialNoDetail.showGrid.store.loadData(viewRecord);
						var modifyForm = airfreight.airQueryWaybill.serialNoDetail.createForm().getForm();
						//运单总件数
						var airTotalCountMap = airfreight.airQueryWaybill.airTotalCountMap;
						if(!airTotalCountMap.get(currentWaybillNo+currentWaybillId)){
							airTotalCountMap.add(currentWaybillNo+currentWaybillId,currentTotalgoodsQty);
							airfreight.airQueryWaybill.airTotalCountMap = airTotalCountMap;
						}
						
						var total = airfreight.airQueryWaybill.serialNoTotals;
						var countDelete = airfreight.airQueryWaybill.airTotalCountMap.get(currentWaybillNo+currentWaybillId);
						var goodsQtyTotal = Ext.Number.from(record.data.goodsQtyTotal,0);
						var serialNoList = airfreight.airQueryWaybill.delSerialNoMap.get(record.data.waybillNo);
						var modifyOriginalWeightMap = airfreight.airQueryWaybill.modifyOriginalWeightMap;
						var modifyOriginalVolumeMap = airfreight.airQueryWaybill.modifyOriginalVolumeMap;
						if(!modifyOriginalWeightMap.get(currentWaybillNo) && !modifyOriginalVolumeMap.get(currentWaybillNo)){
							modifyOriginalWeightMap.add(currentWaybillNo,record.data.grossWeight);
							modifyOriginalVolumeMap.add(currentWaybillNo,record.data.volume);
							airfreight.airQueryWaybill.modifyOriginalWeightMap = modifyOriginalWeightMap;
							airfreight.airQueryWaybill.modifyOriginalVolumeMap = modifyOriginalVolumeMap;
						}
						
						var goodsWeightTotal = airfreight.airQueryWaybill.modifyOriginalWeightMap.get(currentWaybillNo);
						var goodsVolumeTotal = airfreight.airQueryWaybill.modifyOriginalVolumeMap.get(currentWaybillNo);
						
                        //判断是否快递空运
                        if(record.data.transportType != 'PACKAGE_AIR'){
                        	//重量
							var goodsWeight = (countDelete/total)*goodsWeightTotal;
							//体积
							var goodsVolume = (countDelete/total)*goodsVolumeTotal;
                        }else{
                        	//重量
                        	var goodsWeight=goodsWeightTotal;
                        	//体积
							var goodsVolume=goodsVolumeTotal;
                        }
						modifyForm.findField('goodsWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(goodsWeight,1));
						modifyForm.findField('goodsVolumeTotal').setValue(airfreight.airQueryWaybill.fomatFloat(goodsVolume,2));
						modifyForm.findField('goodsBillingWeightTotal').setValue(airfreight.airQueryWaybill.fomatFloat(record.data.billingWeight,1));
						airfreight.airQueryWaybill.serialNoDetail.createForm().loadRecord(modifyForm);
 						//将运单明细中的 毛重体积绑定到临时变量上方便动态计算
 						airfreight.airQueryWaybill.serialNoDetail.showGrid.goodsWeightTotal = goodsWeightTotal;
 						airfreight.airQueryWaybill.serialNoDetail.showGrid.goodsVolumeTotal = goodsVolumeTotal;
 						airfreight.airQueryWaybill.serialNoDetail.showGrid.goodsQtyTotal = goodsQtyTotal;
 						//全局变量(运单明细总毛重合体积件数)
// 						airfreight.airQueryWaybill.currentOperateWeightTotal = goodsWeightTotal;
// 						airfreight.airQueryWaybill.currentOperateVolumeTotal = goodsVolumeTotal;
// 						airfreight.airQueryWaybill.currentOperateBillingWeightTotal = goodsBillingWeightTotal;
 						airfreight.airQueryWaybill.serialNoDetail.createForm().getForm().findField('id').setValue(record.data.id);
						//保存当前record对象
						var updateWaybillWeightVolumeMap = airfreight.airQueryWaybill.updateWaybillWeightVolumeMap;
						updateWaybillWeightVolumeMap.add(record.data.id, record);
						airfreight.airQueryWaybill.updateWaybillWeightVolumeMap = updateWaybillWeightVolumeMap;
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
					}
				});
			}
		}],
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.operate'),
	    width: 40,
	    dataIndex: 'id'
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.waybillNo'),
        dataIndex: 'waybillNo',
        flex: 0.9
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.flightNumberType'),
		dataIndex: 'planFlightNo',
		flex: 0.8,
		renderer:function(value){
			if(value.trim() == ''){
				return null;
			}else{
				return FossDataDictionary.rendererSubmitToDisplay(value,'AIR_FLIGHT_TYPE');
			}
		}
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
        dataIndex: 'makeWaybillWay',
        flex: 0.8,
        renderer:function(v){
        	if(v=='HDP'){
        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.hdp');
        	}else if(v == ' '){
            	return null;
            }else{
        		return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ddkd');
        	}
        }
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
        dataIndex: 'arrvRegionName',
        flex: 0.7
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.measurement'),
        dataIndex: 'measurement'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.weight'),
        dataIndex: 'grossWeight',
        flex: 0.8
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.billingWeight'),
        dataIndex: 'billingWeight',
        flex: 0.8
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.volume'),
        dataIndex: 'volume',
        flex: 0.7
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsQty'),
        dataIndex: 'goodsQty',
        flex: 0.7
	},{
		xtype : 'ellipsiscolumn',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.goodsName'),
        dataIndex: 'goodsName',
        flex: 1.2
	},{
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
        dataIndex: 'stockStatus',
        flex: 0.8,
        renderer:function(value){
			if(value=='NOTSTORE'){//未入库
				 return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage');
			}else if(value=='INSTORE'){ //库存中
				return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.instore');
			}
		}
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.departTime'),
		flex: 1.5,
		dataIndex: 'departTime',//外场交单出发时间
		format: 'Y-m-d H:i:s',
		xtype:'datecolumn',
		dataIndex: 'departTime'//外场交单出发时间
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.unit_price'),
		flex: 0.7,
		dataIndex: 'unitPrice'//运单费率
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storageItem'),
        dataIndex: 'transportRemark',
        flex: 1.3
	},{
		xtype : 'ellipsiscolumn',
		text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.receiveOrgName'),
        dataIndex: 'receiveOrgName',
        flex: 1.5
	},{xtype:'ellipsiscolumn',
        text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
        dataIndex:'transportType',
        flex:1.5,
        renderer:function(value){
        if(value=='PACKAGE_AIR'){
            return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageAir');
        }else if(value =='PRECISION_AIR'){
            return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir');
        }
        }
    }],
	dockedItems:[{
		   xtype:'toolbar',
		   dock:'bottom',
		   layout:'column',
		   defaults:{
			 readOnly:true,
			 labelWidth:50,
			 xtype:'textfield',
			 width:30
		   },
		   items:[{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.poll'),
			   dataIndex: 'billNoTotal',
			   columnWidth:.10
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.goodsQty'),
			   dataIndex: 'goodsQtyTotal',
			   columnWidth:.10
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.grossWeight'),
			   dataIndex: 'weightTotal',
			   hideValue:'',
			   columnWidth:.12
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.billingWeight'),
			   labelWidth:70,
			   dataIndex: 'billingWeightTotal',
			   hideValue:'',
			   columnWidth:.15
		   },{
			   fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.volume'),
			   dataIndex: 'volumeTotal',
			   hideValue:'',
			   columnWidth:.15
		   },{
			   xtype:'container',
			   html:'&nbsp;',
			   columnWidth:.30
		   },{
			   xtype: 'button',
			   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.submitJointticket'),
			   columnWidth:.08,
			   handler:function() {
				   record = airfreight.airQueryWaybill.waybillJointTicketInfo.getSelectionModel().getSelection();
				   if(record.length==0){
					   Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
							   ,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.optionJointticket'));
					   return false;
				   }
				   var count = 1;
				   var targetOrgCode = '';
				   var isConsistent = false;
				   for(var i=0;i<record.length;i++){
					   if(count==1){
						   targetOrgCode = record[i].data.targetOrgCode;
					   }else{
						   if(targetOrgCode!=record[i].data.targetOrgCode){
							   isConsistent = true;
						   }
					   }
						   count++;
					}
				   	if(isConsistent){
						Ext.MessageBox.buttonText.yes = airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.yes');  
						Ext.MessageBox.buttonText.no = airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.no'); 
						Ext.Msg.confirm(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.gdhdmdzyhpzdmdzbfh'), function(btn,text){
							if(btn == 'yes'){
								airfreight.airQueryWaybill.bindingWaybillDetail(record);
							}else{
								return false;	
							}
						})
				   	}else{
				   		airfreight.airQueryWaybill.bindingWaybillDetail(record);
				   	}
			   }
		   }]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.store = Ext.create('Foss.airfreight.waybillJointticketStore'),
		me.callParent([cfg]);
		me.addListener({ 'itemdblclick': me.itemdblclick, scope: this});
	},
	itemdblclick : function(view,record,item,index,e,eOpts){
		//绑定合票信息
		airfreight.airQueryWaybill.waybillJointTicketInfo.store.remove(record);
		//移除待选信息
		airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.add(record);
	}
});

/**
 *************************************** 合票信息 *********************************
 * */
//分单合票查询条件
Ext.define('Foss.airfreight.waybillFindForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.searchCondition'),
	height:170,
	defaults:{
		xtype: 'textfield',
		margin:'5 5 5 5',
		allowBlank: false
	},
	items:[{
		xtype:'combobox',
		editable:false,
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.makeWaybillWay'),
		allowBlank: true,
		name: 'makeWaybillWay',
		hiddenName: 'makeWaybillWay',
		emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.all'),
		store: FossDataDictionary. getDataDictionaryStore('MAKE_WAYBILL_WAY','Foss_login_language_store_Id'),
		queryMode: 'local',
		displayField: 'valueName',
		valueField: 'valueCode',
		columnWidth:.25
		
	},{
		name: 'stockStatus',
		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.stockStatus'),
		allowBlank: true,
		xtype:'combobox',
		emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.all'),
	    store:Ext.create('Ext.data.Store', {
		    fields: ['valueName', 'valueCode'],
		    data : [
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.all'),'valueCode': ''},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.instore'), 'valueCode': 'INSTORE'},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage'), 'valueCode': 'NOTSTORE'}
		    ]
		}),
	    queryMode: 'local',
	    displayField: 'valueName',
	    valueField: 'valueCode',
	    editable:false,
	    forceSelection:true,
	    columnWidth:.25
	},{
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionName'),
		name:'arrvRegionName',
		allowBlank: true,
		columnWidth:.25
	},{
        xtype:'combobox',
        name:'transportType',
        allowBlank:false,
        //readOnly:true,
        store:Ext.create('Ext.data.Store',{
            fields:['valueName','valueCode'],
            data:[{'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir'),'valueCode':'PRECISION_AIR'},
                {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageAir'),'valueCode':'PACKAGE_AIR'}]
        }),
        displayField:'valueName',
        valueField:'valueCode',
        queryMode:'local',
        value:'PRECISION_AIR',
        fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
        listeners: {
	    	'change' : function(field,newValue,oldValue,eOpts){
	    		var form = this.up('form').getForm();
	    		if(newValue=='PRECISION_AIR'){
	    			for(var i = 0;i<airfreight.airQueryWaybill.waybillFindForm.items.length;i++){
	    				if("foss_airfreight_storageTime_panel" == airfreight.airQueryWaybill.waybillFindForm.items.items[i].name){
	    					airfreight.airQueryWaybill.waybillFindForm.items.items[i].hide();
	    				}
	    			}
	    			form.findField('arrvRegionName').setReadOnly(false);
	    			form.findField('nextTfrOrg').setValue(null);
	    			form.findField('nextTfrOrg').setReadOnly(true);
	    			form.findField('stockStatus').setReadOnly(false);
	    			form.findField('stockStatus').setValue('ALL');
	    		}else{
	    			for(var i = 0;i<airfreight.airQueryWaybill.waybillFindForm.items.length;i++){
	    				if("foss_airfreight_storageTime_panel" == airfreight.airQueryWaybill.waybillFindForm.items.items[i].name){
	    					airfreight.airQueryWaybill.waybillFindForm.items.items[i].show();
	    				}
	    			}
	    			form.findField('arrvRegionName').setReadOnly(true);
	    			form.findField('nextTfrOrg').setReadOnly(false);
	    			form.findField('stockStatus').setValue('INSTORE');
	    			form.findField('stockStatus').setReadOnly(true);
	    		}
	    	}
	     }
    },{
		xtype: 'rangeDateField',
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.createWaybillTime'),
		fieldId:'Foss_waybillFindForm_createBillTiem_ID'+(Ext.Date.format(new Date(),'Y-m-d H:i:s')),
		dateType: 'datetimefield_date97',
		dateRange:10, //时间跨度不能大于10天
		fromName: 'beginInTime',
		toName: 'endInTime',
		fromValue:Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',
		columnWidth: .5
	},{
    		xtype:'dynamicorgcombselector',
    		type : 'ORG',
    		transferCenter : 'Y',
    		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airhandovebill.label.nextTfrOrg'),  //下一外场
    		allowBlank:true,
    		name:'nextTfrOrg',
    		columnWidth:.25,
    		readOnly:true
    	},{
		xtype: 'button',
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.search'),
		disabled: !airfreight.airQueryWaybill.isPermission('/tfr-airfreight-web/airfreight/queryConvertWayBillDetail.action'),
		hidden: !airfreight.airQueryWaybill.isPermission('/tfr-airfreight-web/airfreight/queryConvertWayBillDetail.action'),
		columnWidth:.08,
		handler: function(){
			var n = airfreight.airQueryWaybill.waybillFindForm.getValues();
            //获取到达网点 airfreight.airQueryWaybill.updateCurrentAirwayBillForm
            //var dest_dept = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('dedtOrgName').getValue();
            var dest_dept=this.up('form').getForm().findField('nextTfrOrg').getValue();
            var transportType = this.up('form').getForm().findField('transportType').value;
			if(!airfreight.airQueryWaybill.waybillFindForm.getForm().isValid()){
				Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),'包含输入项未输入完整!');
				return false;
			}
			var params = {
					'pointsSingleJointTicketVO.ticketDto.makeWaybillWay': n.makeWaybillWay,
					'pointsSingleJointTicketVO.ticketDto.stockStatus': n.stockStatus,
					'pointsSingleJointTicketVO.ticketDto.arrvRegionName': n.arrvRegionName,
					'pointsSingleJointTicketVO.ticketDto.beginInTime': n.beginInTime,
					'pointsSingleJointTicketVO.ticketDto.endInTime': n.endInTime,
					'pointsSingleJointTicketVO.ticketDto.storageBeginInTime': n.storageBeginInTime,
                    'pointsSingleJointTicketVO.ticketDto.storageEndInTime': n.storageEndInTime,
                    'pointsSingleJointTicketVO.ticketDto.nextDestOrg': dest_dept,
                    'pointsSingleJointTicketVO.ticketDto.transportType':transportType
			};
			Ext.Ajax.request({
    			url:airfreight.realPath('queryConvertWayBillDetail.action'),
    			timeout: 600000,
    			params:params,
				success:function(response){
					var result = Ext.decode(response.responseText);
					var arryvolumeResult = result.pointsSingleJointTicketVO.billDetailList;
					var newArray = [];
					//清除gird所有记录
					airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.removeAll();
					//获取合票信息所有行数据
					var jointTicketInformationStore = airfreight.airQueryWaybill.waybillJointTicketInfo.store;
					//合票信息数组长度
					var gridLength = jointTicketInformationStore.data.items.length;
					var showGridDisable = [];
					var showGridview = [];
					for(var i=0;i<arryvolumeResult.length;i++){
						newArray.push(arryvolumeResult[i]);
						for(var j=0;j<gridLength;j++){
							if(arryvolumeResult[i].id ==jointTicketInformationStore.data.items[j].data.id){
								showGridDisable.push(arryvolumeResult[i]);
							    Ext.Array.remove(arryvolumeResult,arryvolumeResult[i]);
							}
						}
					}
					airfreight.airQueryWaybill.waybillJointTicketInfo.store.removeAll();
					if(showGridview.length>0){
						airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.add(showGridview);
					}else{
						airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo.store.add(arryvolumeResult);
					}
				},
				exception:function(response){
					var result = Ext.decode(response.responseText);
				}
    		});
		}
	},{
		 xtype:'container',
		 html:'&nbsp;',
		 columnWidth:.03
	},{
	    xtype : 'panel',
	    border : false,
	    height: 18,
		width: 25,
		columnWidth:.05,
	    cls: 'x-grid-record-yellow'
	  },{
	    html:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ddkd'),
	    xtype : 'panel',
	    columnWidth:.06,
	    border : false
	  },{
	    xtype : 'panel',
	    border : false,
	    height: 18,
		width: 25,
		columnWidth:.05,
	    cls: 'x-grid-record-gray'
	  },{
	    html:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.storage'),
	    xtype : 'panel',
	    columnWidth:.06,
	    border : false
	  },{ 
		  xtype: 'panel',
		  name:'foss_airfreight_storageTime_panel',
		  hidden:true,
		  height: 40,
		  columnWidth: .5 ,
		  items: [{
	        xtype: 'rangeDateField',
	        fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket.storageTime'),
	        fieldId:'Foss_airfreight_storageTime_Id'+(Ext.Date.format(new Date(),'Y-m-d H:i:s')),  
	        dateType: 'datetimefield_date97',
	        fromName: 'storageBeginInTime',
	        toName: 'storageEndInTime',
	        dateRange: 7, //时间跨度不能大于7天
	        editable: false,
	        fromValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '00:00:00',
	        toValue: Ext.Date.format(new Date(), 'Y-m-d') + ' ' + '23:59:59',
	        columnWidth: .9 
		 }]
	   }
	  ],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
});
Ext.define('Foss.airfreight.hpWindow',{
	   extend:'Ext.window.Window',
	   title: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.houseJointticket'),
	   modal:true,
	   closable:true,
	   closeAction:'hide',
	   width: 1200,
	   height: 1000,
	   items:[
        	  airfreight.airQueryWaybill.waybillFindForm = Ext.create('Foss.airfreight.waybillFindForm'),
	          airfreight.airQueryWaybill.cesareanDeliveryWaybillInfo = Ext.create('Foss.airfreight.cesareanDeliveryWaybillInfo'),
	          airfreight.airQueryWaybill.operateWaybillInfo = Ext.create('Foss.airfreight.operateWaybillInfo'),
	          airfreight.airQueryWaybill.waybillJointTicketInfo = Ext.create('Foss.airfreight.waybillJointTicketInfo')
		],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({},config);
			me.callParent([cfg]);
		}
		
});

/*********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * *********************************************************************************
 * */
//外发托运打印书窗口
	Ext.define('Foss.airfreight.modifyPrintoutgoingcheck',{
		extend: 'Ext.window.Window',
		title: airfreight.airQueryWaybill.i18n('foss.airfreight.agencyPrintBook'),
		modal:true,
		id:'modifyPrintoutgoingcheckId',
		closeAction:'hide',
		width: 650,
		height: 450,
		layout: 'auto',
		resultEntity : null,
		items:[{
	    	xtype:'form',
	    	id: 'modifyPrintFormId',
	    	layout: 'column',
			defaults: {
        		margin: '10 10 10 10',
        		columnWidth:.5,
        		labelWidth:101
        	},
	        	items: [{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airWaybillNo'),
	        		name: 'airWaybillNo'
	        	},{
	    		  dockedItems: [{
	    		        xtype: 'toolbar',
	    		        dock: 'bottom',
	    		        items: [{
	    		            text: airfreight.airQueryWaybill.i18n('foss.airfreight.find'),
	    		            handler:function(){
	    		            	var modifyPrintForm = this.up('form').getForm();
	    		            	var airWaybillNo = modifyPrintForm.findField('airWaybillNo').getValue();
	    		            	var recordData = airfreight.airQueryWaybill.checkstatus.queryWaybillNo(airWaybillNo);
	    		            	modifyPrintForm.loadRecord(recordData);
	    		            	airfreight.airQueryWaybill.isNotHomeQuery  = true;
	    		            }
	    		        },{
	    		        	text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.print'),
	      		            handler:function(){
	      		            	if(airfreight.airQueryWaybill.isNotHomeQuery){
		      		            	if(airfreight.airQueryWaybill.resultHomeEntity==null){
		      		            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
		      		            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airWaybillDetailMessageNotPrint'));
		      		            		return false;
		      		            	}
	      		            	}
	      		            	var fieldElements = this.up('form').getForm()._fields;
				            	for(var i = 0 ;i < fieldElements.length; i++){
				            		if(fieldElements.items[i].value == null || fieldElements.items[i].value == ''){
				            			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
				                				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.printFieldCantBlank'));
				                		return false;
				            		}  
				            	}
	      		            	if(!this.up('form').getForm().isValid()){
	      		            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
	      		            				,airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.validError'));
	      		            		return false;
		      		            }
	      		            	var newForm = this.up('form').getForm();
	      		            	var newRecord =newForm.getRecord();
	      		            	newForm.updateRecord(newRecord);
	      		            	var str = Ext.encode(newRecord.data);
	      		            	do_printpreview('agencyConsignBook',{'record':str}, ContextPath.TFR_PARTIALLINE);
	      		            }
	    		        }]
	    		    }]
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.deptRegionName'),
	        		name: 'deptRegionName',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.arrvRegionName'),
	        		name: 'arrvRegionName',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.shipperName'),
	        		name: 'shipperName',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.shipperContactPhone'),
	        		name: 'shipperContactPhone',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.receiverName'),
	        		name: 'receiverName',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.receiverContactPhone'),
	        		name: 'receiverContactPhone',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.receiverAddress'),
	        		name: 'receiverAddress',
	        		allowBlank:true,
	    			maxLength : 500,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.dzcdycgzdxz')
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.storageItem'),
	        		name: 'storageItem',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.pickupType'),
	        		name: 'pickupType',
	        		allowBlank:true
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.goodsName'),
	        		name: 'goodsName',
	        		allowBlank:true
	        	},{
	        		xtype: 'numberfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.fee'),
	        		name: 'fee',
	    			minValue: 0.001,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	        	    maxLength : 9,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
	        	},{
	        		xtype: 'numberfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.feeTotal'),
	        		name: 'feeTotal',
	    			minValue: 0.001,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    decimalPrecision : 2,
	        	    maxLength : 9,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
	        	},{
	        		xtype: 'numberfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.goodsQty'),
	        		name: 'goodsQty',
	    			minValue: 1,
	    			allowDecimals: false, 
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    maxLength : 9,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
	        	},{
	        		xtype: 'numberfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.grossWeight'),
	        		name: 'grossWeight',
	    			minValue: 0.001,
	    			hideTrigger: true,  
	        	    keyNavEnabled: true,  
	        	    mouseWheelEnabled: true,
	        	    maxLength : 9,
	        		maxLengthText:airfreight.airQueryWaybill.i18n('foss.airfreight.checkoutMessage.cdcgzdxz')
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.agencyName'),
	        		name: 'agencyName'
	        	},{
	        		xtype: 'datetimefield_date97',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.createTime'),
	        		name: 'createTime',
					time : true,
					editable:false,
					columnWidth:.5,
					id : 'Foss_airfreight_createTime_ID2',
					dateConfig: {
						el : 'Foss_airfreight_createTime_ID2-inputEl'
					}
	        	},{
	        		xtype: 'textfield',
	        		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.createUserName'),
	        		name: 'createUserName'
	        	}]
		}],
		constructor: function(config){
			var me = this,
				cfg = Ext.apply({}, config);			
				me.callParent([cfg]);
		}	
	});
//选择打印方式窗口
Ext.define('Foss.airfreight.choiceAirwayBillPrintWindow', {
		extend:'Ext.window.Window',
		title: airfreight.airQueryWaybill.i18n('foss.airfreight.optionPrint'),
		modal:true,
		width: 200,
		printType:null,
		height: 170,
		layout: 'auto',
		closeAction:'hide',
		items: [
        	airfreight.airQueryWaybill.checkstatus = Ext.create('Ext.form.Panel',{
        		queryWaybillNo : function(airWaybillNo){
	        		var record = null;
	            	Ext.Ajax.request({
	            		async: false,
	        			url:airfreight.realPath('queryWidthPrintAirWaybill.action'),
	        	   		params : {
	        	   				'pointsSingleJointTicketVO.airWaybillNo':airWaybillNo
	        	   		},
	        			success:function(response){
	        				var result = Ext.decode(response.responseText);
	        				resultEntity = result.pointsSingleJointTicketVO.billEntity;
	        				airfreight.airQueryWaybill.resultHomeEntity = resultEntity;
	        				record = Ext.create('Foss.airfreight.airwayBillModel',resultEntity);
	        			},
	        			failure:function(response){
	        				var result = Ext.decode(response.responseText);
	        			}
	        		});
	        		return record;
	        	},
        		items: [{
						xtype: 'radiogroup',
						columns: 1,
						margin : '10 10 10 10', 
        		        items: [{
        		        	boxLabel  : airfreight.airQueryWaybill.i18n('foss.airfreight.airlineBilling'),
        					name      : 'checkstatus',
        					checked : true,
        					inputValue: 'A'
        		        },{
        					boxLabel  : airfreight.airQueryWaybill.i18n('foss.airfreight.airwayAgencyInventory'),
        					name      : 'checkstatus',
        					inputValue: 'B'
        				},{
        					boxLabel  : airfreight.airQueryWaybill.i18n('foss.airfreight.agencyShipperBook'),
        					name      : 'checkstatus',
        					inputValue: 'C'
        				}]
        		},{
        			dockedItems: [{
        		        xtype: 'toolbar',
        		        dock: 'bottom',
        		        items: [{
        		        	xtype: 'container',
        		        	margin: '0 0 0 35'
        		        },{
        		            text: airfreight.airQueryWaybill.i18n('foss.airfreight.confirm'),
        		            handler:function(){
        		            	//do_printpreview('aryWaybill',{'ids':str,'isNotbatchPrint':1});MulitRecord=str;
        		            	//如果非多条打印
        						if(Ext.isEmpty(airfreight.airQueryWaybill.printMulitRecord)){
        							var radioValue = this.up('form').getForm().getValues()['checkstatus'];
            		            	var recordData = null;
            		            	if(airfreight.airQueryWaybill.checkstatus.ownerCt.printType=='INNER'){
            		            		var airWaybillNo = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm().findField('airWaybillNo').getValue();
            		            	}else{
            		            		var airWaybillNo = airfreight.airQueryWaybill.resultAirWaybill.getSelectionModel().getSelection()[0].data.airWaybillNo;
            		            	}
            		            	recordData = airfreight.airQueryWaybill.checkstatus.queryWaybillNo(airWaybillNo);
            		            	if(radioValue==null || radioValue==''){
            		            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
            		            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.qxzdybb'));        		            		
            		            	}else if (radioValue =='C'){
    	        	            		this.up('window').hide();
    	        	            		var modifyPrintWindow = Ext.getCmp('modifyPrintoutgoingcheckId');
    	        	            		if(modifyPrintWindow == null){
    	        	            			modifyPrintWindow = Ext.create('Foss.airfreight.modifyPrintoutgoingcheck');
    	        	            		}
    	        	            		modifyPrintWindow.show();
    	        	            		Ext.getCmp('modifyPrintFormId').getForm().loadRecord(recordData);
            		            	}else if(radioValue == 'B'){
            		            		Ext.Msg.confirm('', airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.sfdyxgshbm'), function(obj){  
            		            			var isnotRecDept = 'Y';
            		            			var str = Ext.encode(recordData.data);
            		            			if(obj == 'no'){
            		            				isnotRecDept = 'N';
            		            		    }
            		            			do_printpreview('agency',{'record':str,
		            		    				'id':recordData.data.id,'ids:':'',
		            		    				'isNotbatchPrint':'',
		            		    				'isnotRecDept':isnotRecDept,
		            		    				'createUserName':FossUserContext.getCurrentUser().employee.empName},
		            		    				ContextPath.TFR_PARTIALLINE);
            		            		});
            		            	}else{
            		            		Ext.Msg.confirm('', airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.sfdyxgje'), function(obj){  
            		            		    var isnotMoney = 'Y';
            		            			if(obj == 'no'){
            		            				isnotMoney = 'N';
            		            			}
            		            			do_printpreview('aryWaybill',
                		            				{
                		            					'record':recordData.data.id,
                		            					'isNotbatchPrint':0,
                		            					'isnotMoney':isnotMoney
                		            				}, 
                		            				ContextPath.TFR_PARTIALLINE);
            		            		}); 
            		            	}
        						}
        						//如果多条打印
        						else{
        							var radioValue = this.up('form').getForm().getValues()['checkstatus'];
            		            	if(radioValue==null || radioValue==''){
            		            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
            		            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.qxzdybb'));        		            		
            		            	}else if (radioValue =='C'){
            		            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
            		            				,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.selectOneToPrint'));     
            		            	}else if(radioValue == 'B'){
            		            		Ext.Msg.confirm('', airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.sfdyxgshbm'), function(obj){  
            		            			var isnotRecDept = 'Y';
            		            			if(obj == 'no'){
            		            				isnotRecDept = 'N';
            		            		    }
                		            		do_printpreview('agency',{'records':airfreight.airQueryWaybill.choseMulitRecord,
                		            				'ids':airfreight.airQueryWaybill.printMulitRecord,
                		            				'isNotbatchPrint':1,
                		            				'isnotRecDept':isnotRecDept,
                		            				'id':'',
                		            				'createUserName':FossUserContext.getCurrentUser().employee.empName}, 
                		            				ContextPath.TFR_PARTIALLINE);
            		            		});
            		            	}else{  
            		            		Ext.Msg.confirm('', airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.sfdyxgje'), function(obj){  
            		            		    var isnotMoney = 'Y';
            		            			if(obj == 'no'){
            		            				isnotMoney = 'N';
            		            			}
            		            			do_printpreview('aryWaybill',
            		            					{
            		            						'ids':airfreight.airQueryWaybill.printMulitRecord,
            		            						'isNotbatchPrint':1,
            		            						'isnotMoney':isnotMoney
            		            					}, 
            		            					ContextPath.TFR_PARTIALLINE);
            		            		}); 
            		            	}
            		            }
        		            }
        		        },{
        		        	xtype: 'container',
        		        	margin: '0 0 0 10'
        		        },{	
        		        	text: airfreight.airQueryWaybill.i18n('foss.airfreight.cancel'),
        		            handler:function(){
        		            	this.up('window').hide();
        		            }
        		        }]
        		    }]
        		}]
        	})
		]
});

//航空正单打印
Ext.define('Foss.airfreight.airwayBillPrintToolbar', {
	   extend:'Ext.toolbar.Toolbar',
	   xtype:'toolbar',
	   dock:'right',
	   layout:'column',
	   defaultType:'button',
	   width:1024,
	   items:[{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.85
	   },{
		   text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.print'),
		   disabled: !airfreight.airQueryWaybill.isPermission('airfreight/printWidthPrintAirWaybillButton'),
		   hidden: !airfreight.airQueryWaybill.isPermission('airfreight/printWidthPrintAirWaybillButton'),
		   columnWidth:.06,
		   handler: function(){
				record = airfreight.airQueryWaybill.resultAirWaybill.getSelectionModel().getSelection();
				airfreight.airQueryWaybill.isNotHomeQuery = false;				
            	if(record.length=='0'){
            		Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.messages'),airfreight.airQueryWaybill.i18n('foss.airfreight.optionPrintAirfreightAirWay'));
            	}else if(record.length=='1'){
            		airfreight.airQueryWaybill.printMulitRecord='';
            		airfreight.airQueryWaybill.choiceAirwayBillPrintWindow = Ext.create('Foss.airfreight.choiceAirwayBillPrintWindow',{printType:'HOME'}).show();
            		airfreight.airQueryWaybill.checkstatus.getForm().getFields().each(function(item,index,length){
            			if(item.boxLabel==airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.agenBook')){
							item.show();    								
							return;
						}
					});
            	}else{
            		//选择多条航空正单打印
    				Ext.MessageBox.buttonText.yes = airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ensure');  
    				Ext.MessageBox.buttonText.no = airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.cancel'); 
    				Ext.Msg.confirm( airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
    						,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.optionBigAirWaybillAffirmPrint'), function(btn,text){
    					if(btn == 'yes'){
    						var str = "";
    						for(var i=0;i<record.length;i++){
    							str = str +record[i].data.id;
    							airfreight.airQueryWaybill.choseMulitRecord.push(record[i].data);
    				 			if(record.length-1==i){
    				 				continue;
    				 			}
    				 			str = str + ',';
    						}
    						//批量打印
    						//do_printpreview('aryWaybill',{'ids':str,'isNotbatchPrint':1});
    						airfreight.airQueryWaybill.printMulitRecord=str;
    						airfreight.airQueryWaybill.choiceAirwayBillPrintWindow = Ext.create('Foss.airfreight.choiceAirwayBillPrintWindow',{printType:'HOME'}).show();
    						
    						airfreight.airQueryWaybill.checkstatus.getForm().getFields().each(function(item,index,length){
    							if(item.boxLabel==airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.agenBook')){
    								item.hide();    								
    								return;
    							}
    						});
    					}else{
    						return false;
    					}
    				})
            	}
		   }
	   },{
		   xtype:'container',
		   html:'&nbsp;',
		   columnWidth:.03
	   },{
		   text:airfreight.airQueryWaybill.i18n('foss.airfreight.public.export'),
		   columnWidth:.06,
		   handler: function(){
				if(!Ext.fly('downloadAttachFileForm')){
				    var frm = document.createElement('form');
				    frm.id = 'downloadAttachFileForm';
				    frm.style.display = 'none';
				    document.body.appendChild(frm);
		       	}
		   		var n = airfreight.airQueryWaybill.modifyQueryForm.getValues();
			    Ext.Ajax.request({
	    			url:airfreight.realPath('queryAirWaybillForExport.action'),
	    			form: Ext.fly('downloadAttachFileForm'),
					method : 'POST',
	    	   		params : {
	    	   			'airWayBillVo.airWayBillDto.airWaybillNo' : n.airWaybillNo,
						'airWayBillVo.airWayBillDto.beginInTime' : n.beginInTime,
						'airWayBillVo.airWayBillDto.endInTime' : n.endInTime,
						'airWayBillVo.airWayBillDto.arrvRegionName' : n.arrvRegionName,
						'airWayBillVo.airWayBillDto.airlineTwoletter' : n.airLineTwoletter,
						'airWayBillVo.airWayBillDto.airAssembleType' : n.airAssembleType,
						'airWayBillVo.airWayBillDto.createOrgCode' : airfreight.airQueryWaybill.createDeptCode,
						'airWayBillVo.airWayBillDto.airAssembl查看尺寸及储运事项eType' : n.airAssembleType   
	    	   		},
	    	   		isUpload: true,
	    			success:function(response){
	    				Ext.ux.Toast.msg(airfreight.airChangeInventory.i18n('foss.airfreight.public.promptMessage')
					    	,airfreight.airChangeInventory.i18n('foss.airfreight.public.exportSuccess'));
	    			}
				});
	
		   }
	   }]
}),
//航空正单查询条件
Ext.define('Foss.airfreight.modifyQueryForm',{
	extend: 'Ext.form.Panel',
	layout: 'column',
	frame: true,
	title: airfreight.airQueryWaybill.i18n('foss.airfreight.findairWayMessages'),
	defaults: {
		margin: '5 5 5 5'
	},
	items: [{
		xtype:'commonairlinesselector',
		displayField : 'code',// 显示名称
		valueField : 'code',// 值 
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airlineTwoletter'),
		name: 'airLineTwoletter',
		columnWidth:.25
	},{
		xtype: 'textfield',
		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airWaybillNo'),
		name: 'airWaybillNo',
		columnWidth:.25
	},{
		xtype : 'commonairporwithcitynametselector',
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.arrvRegionName'),
		name:'arrvRegionName',
		displayField : 'cityName',
		columnWidth:.25
	},{
		xtype:'combobox',
		editable:false,
		fieldLabel:airfreight.airQueryWaybill.i18n('foss.airfreight.airAssembleType'),
		emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.all'),
		name: 'airAssembleType',
		value:'ALL',
		hiddenName: 'airAssembleType',
	    store:Ext.create('Ext.data.Store', {
		    fields: ['valueName', 'valueCode'],
		    data : [
				{'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.all'),'valueCode': 'ALL'},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.independentBillingOutsource'), 'valueCode': 'DDWFD'},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.independentBilling'),'valueCode': 'DDKD'},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.totalBigBillNo'),'valueCode': 'HDP'},
		        {'valueName':airfreight.airQueryWaybill.i18n('foss.airfreight.totalBigBillNoOutsource'),'valueCode': 'HDPWF'}
		    ]
		}),
		queryMode: 'local',
		displayField: 'valueName',
		valueField: 'valueCode',
		columnWidth:.25	
	},{
		xtype: 'dynamicorgcombselector',
		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.airMasterCalibration'),
		type:'ORG',
		doAirDispatch:'Y',
		name: 'createOrgName',
		allowBlank: false,
		disabled:true,
		columnWidth:.25
	},{
		xtype: 'rangeDateField',
		fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.createAirWaybillTime'),
		fieldId: 'Foss_airfreight_editcreateTime_Id',
		dateType: 'datetimefield_date97',
		fromName: 'beginInTime',
		toName: 'endInTime',
		allowBlank: false,
		fromValue:Ext.Date.format(new Date(),'Y-m-d')+ ' '+'00:00:00',
		toValue:Ext.Date.format(new Date(),'Y-m-d')+' '+'23:59:59',
		columnWidth: .5
	},{
            xtype: 'combobox',
            allowBlank: false,
            name: 'transportType',
            fieldLabel: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),
            emptTxt: airfreight.airQueryWaybill.i18n('foss.airfreight.all'),
            store: Ext.create('Ext.data.Store', {
                fields: ['valueName', 'valueCode'],
                data: [{'valueName': '全部', 'valueCode': 'ALL'},
                    {'valueName': airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir'), 'valueCode': 'PRECISION_AIR'},
                    {'valueName': airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageAir'), 'valueCode': 'PACKAGE_AIR'}
                ]
            }),
            queryMode: 'local',
            value: 'ALL',
            displayField: 'valueName',
            valueField: 'valueCode',
            editable: false,
            columnWidth: .25
            
       },{
		xtype: 'container',
		columnWidth:.25
	   },{
		border: 1,
		xtype:'container',
		columnWidth:1,
		defaultType:'button',
		layout:'column',
		items:[{
			text: airfreight.airQueryWaybill.i18n('foss.airfreight.reset'),
			columnWidth:.08,
			handler:function(){
				var createDeptcode = airfreight.airQueryWaybill.createDeptCode;
				var createDeptName = airfreight.airQueryWaybill.createDeptName;
				this.up('form').getForm().findField('createOrgName').setCombValue(createDeptName,createDeptcode);
				this.up('form').getForm().findField('airLineTwoletter').reset();
				this.up('form').getForm().findField('airWaybillNo').reset();
				this.up('form').getForm().findField('arrvRegionName').reset();
				this.up('form').getForm().findField('airAssembleType').reset();
				this.up('form').getForm().findField('beginInTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'00','00','00'), 'Y-m-d H:i:s'));
				this.up('form').getForm().findField('endInTime').setValue(Ext.Date.format(new Date(new Date().getFullYear(),new Date().getMonth(),new Date().getDate()
						,'23','59','59'), 'Y-m-d H:i:s'));
				}
			},{
				xtype: 'container',
				columnWidth:.84,
				html: '&nbsp;'
			},{
				text:airfreight.airQueryWaybill.i18n('foss.airfreight.find'),
				disabled: !airfreight.airQueryWaybill.isPermission('airfreight/queryAirWayBillButton'),
				hidden: !airfreight.airQueryWaybill.isPermission('airfreight/queryAirWayBillButton'),
				cls:'yellow_button',
				columnWidth:.08,
				handler:function(){
					var form = this.up('form').getForm();
					var beginDate = this.up('form').getForm().findField('beginInTime').getValue();
					var endDate = this.up('form').getForm().findField('endInTime').getValue();
					var checkDateTimeSpan =  function(beginDate, endDate) {
						if(beginDate=='' || beginDate==null){
							Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
									,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createBillStartTime'));
							return false;
						}
						if(endDate=='' || endDate==null){
							Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
									,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createBillEndTime'));
							return false;
						}
						if(!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
							var begin = Ext.Date.parse(beginDate, "Y-m-d H:i:s", true);
							var end = Ext.Date.parse(endDate, "Y-m-d H:i:s", true);
							var pool = begin - end;
							var m = -86400000 * 31;
							if(pool < m) {
								Ext.MessageBox.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.prompt')
										,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.createBillTime'));				
								return false;
							} 
						}
						return true;
					}
					if(form.isValid()){
						if(!checkDateTimeSpan(beginDate,endDate))
						return false;
						airfreight.airQueryWaybill.pagingBar.moveFirst();
					}else{
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage')
								,airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.includeNotInputItem'));
						return false;
					}
				}
		}]
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({},config);
		me.callParent([cfg]);
	}
});

Ext.define('Foss.airfreight.airwayBillStore',{
	extend: 'Ext.data.Store',
	model: 'Foss.airfreight.airwayBillModel',
	pageSize:20,
	proxy: {
		type : 'ajax',
		actionMethods:'POST',
		url:airfreight.realPath('queryAirWayBill.action'),
		reader : {
			type : 'json',
			root : 'airWayBillVo.result',
			totalProperty : 'totalCount',
			successProperty: 'success'
		}
	},
	listeners: {
		beforeload: function(store, operation, eOpts){
			var n = airfreight.airQueryWaybill.modifyQueryForm.getValues();
			Ext.apply(operation,{
				params : {
					'airWayBillVo.airWayBillDto.airWaybillNo' : n.airWaybillNo,
					'airWayBillVo.airWayBillDto.beginInTime' : n.beginInTime,
					'airWayBillVo.airWayBillDto.endInTime' : n.endInTime,
					'airWayBillVo.airWayBillDto.arrvRegionName' : n.arrvRegionName,
					'airWayBillVo.airWayBillDto.airlineTwoletter' : n.airLineTwoletter,
					'airWayBillVo.airWayBillDto.airAssembleType' : n.airAssembleType,
					'airWayBillVo.airWayBillDto.createOrgCode' : airfreight.airQueryWaybill.createDeptCode,
					'airWayBillVo.airWayBillDto.airAssembleType' : n.airAssembleType,
					'airWayBillVo.airWayBillDto.transportType' : n.transportType
				}
			});			
		}
	}
});

Ext.define('Foss.airfreight.editAirwayBillWindow',{
	extend:'Ext.window.Window',
	title: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.modifyWaybill'),
	modal:true,
	closable:true,
	closeAction:'hide',
	width: 1100,
	layout: 'auto',
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		airfreight.airQueryWaybill.updateCurrentAirwayBillForm = Ext.create('Foss.airfreight.updateCurrentAirwayBillForm');
		var editsubmitForm =  Ext.create('Foss.airfreight.airwayBillInfo');
		airfreight.airQueryWaybill.waybillInfo = Ext.create('Foss.airfreight.waybillInfo');
		me.add([airfreight.airQueryWaybill.updateCurrentAirwayBillForm, editsubmitForm, airfreight.airQueryWaybill.waybillInfo]);
	}
});


//新增
Ext.define('Foss.airfreight.addWaybill',{
	extend:'Ext.container.Container',
	width : 1080,
	layout : 'column',
	items: [{
			xtype: 'container',
			margin:'5 5 5 10',
			html: '&nbsp;'
		},{
			width:65,
			xtype:'button',
			text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.add'),
			columnWidth:.08,
			handler:function(){
				addTab('T_airfreight-airEnteringFlightBill',//对应打开的目标页面js的onReady里定义的renderTo
						airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.enteringWaybillDetail'),//打开的Tab页的标题
						airfreight.realPath('airEnteringFlightBill.action'));
			}
	}]
});

/**
 * @returns airwaybill List
 * @param {0}
 * @author zhou de jun
 * @deprecated 查询航空正单结果集
 * */
Ext.define('Foss.airfreight.ResultAirWaybill',{
	extend: 'Ext.grid.Panel',
	id: 'airwaybill_id',
	frame: true,
	border: true,
	emptyText: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.searchResultInexistence'),
	columns: [{
		xtype: 'actioncolumn',
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.edit'),
				disabled: !airfreight.airQueryWaybill.isPermission('airfreight/modifyAirWaybillEntityButton'),
				hidden: !airfreight.airQueryWaybill.isPermission('airfreight/modifyAirWaybillEntityButton'),
				handler: function(grid, rowIndex, colIndex) {
					var record = grid.getStore().getAt(rowIndex);
					var airwayBillRecord = null;
					Ext.Ajax.request({
				   		url:airfreight.realPath('queryAirWaybillEntity.action'),
				   		params:params={
				   				'airWayBillVo.airWayBillDto.airwaybillId':record.data.id
				   		},
						success:function(response){
							var result = Ext.decode(response.responseText);
							var formatDate = function(value, formatStr) {
				    			if(!formatStr) formatStr = 'Y-m-d';
				    			return Ext.Date.format(new Date(value), formatStr); 
				    		};
							airfreight.airQueryWaybill.airlinesRate = result.airWayBillVo.airlinesValueAddEntity;
							var modifyWeightVolumnMap = airfreight.airQueryWaybill.modifyAirwayBillWeightVolumnMap;
							airwayBillRecord = result.airWayBillVo.optAirWaybillEntity;
							airwayBillDetailRecordList = result.airWayBillVo.airWaybillDetailEntityList;
                            airfreight.airQueryWaybill.transportType = result.airWayBillVo.optAirWaybillEntity.transportType;
                            //如果为空则设置为精准空运
                            if(Ext.isEmpty(airfreight.airQueryWaybill.transportType)){
                                airfreight.airQueryWaybill.transportType = 'PRECISION_AIR';
                            }
                            var f=false;
                            for(var i = 0;i<airwayBillDetailRecordList.length;i++){
                            	if(airwayBillDetailRecordList[i].waybillNo.substring(0,1)=='B'){
                            		f=true;
                            	}
                            }for(var i = 0;i<airwayBillDetailRecordList.length;i++){
                            	if(f){
                            		airwayBillDetailRecordList[i].transportType='PACKAGE_AIR';
                            		airwayBillDetailRecordList[i].productCode ='DEAP';
                            	}
                            }
							airfreight.airQueryWaybill.airwayBillRecord = Ext.create('Foss.airfreight.airwayBillModel',airwayBillRecord);
							airfreight.airQueryWaybill.modifyAirwayBillWeightVolumnMap = modifyWeightVolumnMap;
							Ext.create('Foss.airfreight.editAirwayBillWindow').show();
							airfreight.airQueryWaybill.waybillInfo.setVisible(true);
							airwayBillRecord.flightDate = Ext.Date.format(new Date(airwayBillRecord.flightDate),'Y-m-d');
		    				var startTime = formatDate(airwayBillRecord.takeOffTime,'H:i');
		    				var endDateTime = formatDate(airwayBillRecord.arriveTime,'H:i');
		    				airfreight.airQueryWaybill.airwayBillRecord.data.takeOffTime = startTime;
		    				airfreight.airQueryWaybill.airwayBillRecord.data.arriveTime = endDateTime;
		    				var airLineTwoletter = airfreight.airQueryWaybill.airwayBillRecord.data.airLineTwoletter;
		    				var airportCode = airfreight.airQueryWaybill.airwayBillRecord.data.airportCode;
		    				airfreight.airQueryWaybill.updateCurrentAirwayBillForm.queryAirlineRate(airLineTwoletter,airportCode);
		    				//var newFeeTotal = airfreight.airQueryWaybill.airwayBillRecord.data.feeTotal+airfreight.airQueryWaybill.airwayBillRecord.data.billingFee;
		    				//airfreight.airQueryWaybill.airwayBillRecord.data.feeTotal = airfreight.airQueryWaybill.fomatFloat(newFeeTotal,2);
							airfreight.airQueryWaybill.updateCurrentAirwayBillForm.loadRecord(airfreight.airQueryWaybill.airwayBillRecord);
							var recordUpdateForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();		    				
		    				var airAssembleTypeCode = recordUpdateForm.findField('airAssembleType').getValue();
		    				if(airAssembleTypeCode=='HDP' || airAssembleTypeCode =='DDKD'){
		    					recordUpdateForm.findField('agenctCode').setReadOnly(true);
	    						document.getElementById(recordUpdateForm.findField('agencyName_wf').getId()).style.display = 'none';
	    						document.getElementById(recordUpdateForm.findField('agenctCode').getId()).style.display = 'inline';
								recordUpdateForm.findField('billingFee').setReadOnly(true);
		    				}else if (airAssembleTypeCode =='DDWFD' || airAssembleTypeCode=='HDPWF'){
		    					recordUpdateForm.findField('agencyName_wf').setValue(airfreight.airQueryWaybill.airwayBillRecord.data.agencyName);
		    					document.getElementById(recordUpdateForm.findField('agencyName_wf').getId()).style.display = 'inline';
	    						document.getElementById(recordUpdateForm.findField('agenctCode').getId()).style.display = 'none';
	    						document.getElementById(recordUpdateForm.findField('agenctCode').getId())
	    						recordUpdateForm.findField('billingFee').setReadOnly(false);
		    				}
		    				recordUpdateForm.findField('airportLocationName').setValue(airwayBillRecord.arrvRegionName);
                            recordUpdateForm.findField('dedtOrgName').setCombValue(airwayBillRecord.dedtOrgName,airwayBillRecord.destOrgCode);
		    				recordUpdateForm.findField('airAssembleType').setValue(airAssembleTypeCode);
		    				recordUpdateForm.findField('flightNo').airLines = airfreight.airQueryWaybill.airwayBillRecord.data.airLineTwoletter;
		    				recordUpdateForm.findField('flightNo').store.load();
							airfreight.airQueryWaybill.waybillInfo.store.loadData(airwayBillDetailRecordList);
							airfreight.airQueryWaybill.airwayBillDetailRecord = airwayBillDetailRecordList;
						},
						exception:function(response){
							var result = Ext.decode(response.responseText);
						}
					})
				}
			}],
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.operator'),
        width: 50,
        sortable: true,
        dataIndex: 'id'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airWaybillNo'),
		width: 100,
        sortable: true,
        dataIndex: 'airWaybillNo'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airlineTwoletter'),
		width: 80,
        sortable: true,
        dataIndex: 'airLineTwoletter'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airAssembleType'),
        width: 90,
        sortable: true,
        dataIndex: 'airAssembleType',//配载类型
        renderer : function(value){
        	//根据value转换成对应的值
        	return FossDataDictionary.rendererSubmitToDisplay (value,'AIR_ASSEMBLE_TYPE');
        }
	},{//269701--2016/04/20--begin 新增是否已做合大票
		text: '是否已做合大票',
        width: 90,
        sortable: true,
        dataIndex: 'airPickState',
        renderer :function(value){
        	if(value == 'N'){
        		return '<span style="color:red;">' + '否' + '</span>';;
        	}else if(value == 'Y'){
        		return '<span style="color:green;">' + '是' + '</span>';;
        	}else{
			 return '<span style="color:black;">' + '--' + '</span>';
			}
        }
		//269701--2016/04/20--end
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.tranportType'),//运输类型
        width: 90,
        sortable: true,
        dataIndex: 'transportType',
        renderer : function(value){
        	if(value=='PRECISION_AIR'){
        	   return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.precisionAir');
        	}else{
        	   return airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.packageAir');

        	}
        	
        }
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.createDepartment'),
        sortable: true,
        dataIndex: 'createOrgName'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.deptRegionName'),
		width: 70,
        sortable: true,
        dataIndex: 'deptRegionName'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.dedtOrgName'),
        sortable: true,
        dataIndex: 'dedtOrgName'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.arrvRegionName'),
		width: 70,
        sortable: true,
        dataIndex: 'arrvRegionName'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.flightNo'),
        width: 70,
        sortable: true,
        dataIndex: 'flightNo'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.handoverState'),
        width: 90,
        sortable: true,
        dataIndex: 'handoverState',
        renderer :function(value){
        	if(value == 'N'){
        		return '<span style="color:red;">' + airfreight.airQueryWaybill.i18n('foss.airfreight.nothandover') + '</span>';;
        	}else if(value == 'Y'){
        		return '<span style="color:green;">' + airfreight.airQueryWaybill.i18n('foss.airfreight.alreadyhandover') + '</span>';;
        	}
        }
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.goodsQty'),
        width: 60,
        sortable: true,
        dataIndex: 'goodsQty'
	},{
		text: airfreight.airQueryWaybill.i18n('foss.airfreight.grossWeight'),
        width: 60,
        sortable: true,
        dataIndex: 'grossWeight'
	}],
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Foss.airfreight.airwayBillStore');
		me.selModel = Ext.create('Ext.selection.CheckboxModel'),
		me.bbar = Ext.create('Deppon.StandardPaging',{
			store:me.store
		});
		airfreight.airQueryWaybill.pagingBar = me.bbar;
		me.callParent([cfg]);
	}
});

Ext.onReady(function(){
	Ext.QuickTips.init();
	var toolbar= Ext.create('Foss.airfreight.airwayBillPrintToolbar');
	var queryform = Ext.create('Foss.airfreight.modifyQueryForm');
	var addWaybill = Ext.create('Foss.airfreight.addWaybill');
	var resultAirWaybill =	Ext.create('Foss.airfreight.ResultAirWaybill');
	var isNotSaveSerialNoMap = new Ext.util.HashMap();
	var addSerialNoMap = new Ext.util.HashMap();
	var delSerialNoMap = new Ext.util.HashMap();
	var modifyAirwayBillWeightVolumnMap = new Ext.util.HashMap();
	var updateWaybillWeightVolumeMap = new Ext.util.HashMap();
	airfreight.airQueryWaybill.modifyOriginalWeightMap = new Ext.util.HashMap();
	airfreight.airQueryWaybill.modifyOriginalVolumeMap = new Ext.util.HashMap();
	airfreight.airQueryWaybill.airTotalCountMap = new Ext.util.HashMap();
	airfreight.airQueryWaybill.updateWaybillWeightVolumeMap = updateWaybillWeightVolumeMap;
	airfreight.airQueryWaybill.isNotSaveSerialNoMap = isNotSaveSerialNoMap;
	airfreight.airQueryWaybill.addSerialNoMap = addSerialNoMap;
	airfreight.airQueryWaybill.delSerialNoMap = delSerialNoMap;
	airfreight.airQueryWaybill.viewMap = new Ext.util.HashMap();
	airfreight.airQueryWaybill.modifyAirwayBillWeightVolumnMap = modifyAirwayBillWeightVolumnMap;
	airfreight.airQueryWaybill.isNotHomeQuery = false;
	airfreight.airQueryWaybill.serialNoList ='';
	airfreight.airQueryWaybill.totalLength = 0;
	airfreight.airQueryWaybill.modifyQueryForm = queryform;
	airfreight.airQueryWaybill.resultAirWaybill = resultAirWaybill;
    airfreight.airQueryWaybill.transportType = '';
	Ext.create('Ext.panel.Panel',{
		id:'T_airfreight-airQueryWaybill_content',
		cls:"panelContentNToolbar",
		bodyCls:'panelContent-body',
		items : [toolbar,queryform,addWaybill,resultAirWaybill],
		renderTo: 'T_airfreight-airQueryWaybill-body'
	});
		Ext.Ajax.request({
		async: false,
		url:airfreight.realPath('queryBaseData.action'),
		success:function(response){
			var result = Ext.decode(response.responseText);
			var deptRegionAirportList = result.pointsSingleJointTicketVO.deptRegionAirportList;
			var airportListStr = '';
			for(var i=0;i<deptRegionAirportList.length;i++){
				if(i==0){
					airportListStr = airportListStr + deptRegionAirportList[i].airportCode;
				}else{
					airportListStr =  airportListStr + ',' +deptRegionAirportList[i].airportCode
				}
			}
			airfreight.airQueryWaybill.origCodes = airportListStr;
			var initData = result.pointsSingleJointTicketVO.ticketDto;
			var createDeptCode = initData.orgCode;
			var createDeptName = initData.orgName;
			airfreight.airQueryWaybill.createDeptCode = initData.orgCode;
			airfreight.airQueryWaybill.createDeptName = initData.orgName;
			airfreight.airQueryWaybill.modifyQueryForm.getForm().findField('createOrgName').setCombValue(createDeptName,createDeptCode);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//提示信息
			Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
		}
	});
});

//获取最低运价
airfreight.airQueryWaybill.queryMinRate = function(record){
	var entity = Ext.create('Foss.airfreight.rateQueryModel',airfreight.airQueryWaybill.airwayBillRecord.data);
	var updateCurrentAirwayBillForm = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getForm();
	entity.data.targetCode = updateCurrentAirwayBillForm.findField('dedtOrgName').destCityCode;  //目的地code
	var airLineTwoletter = updateCurrentAirwayBillForm.findField('airLineTwoletter').getValue();
	var billingWeight = Ext.Number.from(updateCurrentAirwayBillForm.findField('billingWeight').getValue(),0);//计费重量
	var queryPriceRate = 0;
	var queryRate = {pointsSingleJointTicketVO:{flightEntity:entity.data,
		airLineTwoletter:airLineTwoletter,weight:billingWeight,flightNo:record.data.flightNo}};
	Ext.Ajax.request({
		//设置后台返回的合票号
		async: false,
		url:airfreight.realPath('queryFlightMinPriceRate.action'),
		jsonData:queryRate,		
		success:function(response){
			var result = Ext.decode(response.responseText);
			queryPriceRate = result.pointsSingleJointTicketVO.priceRate;
			airfreight.airQueryWaybill.minPriceRate = result.pointsSingleJointTicketVO.minPriceRate;
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			//Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
			alert(result.message);
			return false;
		}
	});
	return queryPriceRate;
}

//选择航空公司三字码
Ext.define('Foss.airfreight.airLineTwoletterFreeCode',{
	extend:'Ext.window.Window',
	width:400,
	height:240,
	title : airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.optionAirportCode'),
	closeable: true,
	modal:true,
	closeAction:'hide',
	createForm : function(){
		if(airfreight.airQueryWaybill.showCodeForm) {
			return airfreight.airQueryWaybill.showCodeForm; 
		}
		airfreight.airQueryWaybill.showCodeForm = Ext.create('Ext.form.Panel',{
			frame: false,
			border: false,
			width:360,
			layout: 'column',
			defaults:{
				margin:'5 5 5 5'
			},
			//获取始发站三字码、目的站三字码
			queryAirportCode : function (url,cityCode,paramsTypes){
				var records = null;
				var params =null;
				if(paramsTypes=='deptRegion'){
					params = {
							'pointsSingleJointTicketVO.deptRegionCode': cityCode
					};
				}else{
					params = {
							'pointsSingleJointTicketVO.arrvRegionCode': cityCode
					};
				}
				Ext.Ajax.request({
					//设置后台返回的合票号
					async: false,
					url:airfreight.realPath(url),
					params : params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						if(paramsTypes=='deptRegion'){
							records = result.pointsSingleJointTicketVO.deptRegionAirportList;
							if(!records) {
								records = [];
							}
							var json= {
									fields:['airportCode','airportName'],
								    data : records
								};
							var store = Ext.create('Ext.data.Store', json);
							var deptRegionCode = airfreight.airQueryWaybill.showCodeForm.getForm().findField('deptRegionCode');
							deptRegionCode.store = store;
						}else{
							records = result.pointsSingleJointTicketVO.arrvRegionAirportList;
							if(!records) {
								records = [];
							}
							var json= {
									fields:['airportCode','airportName'],
								    data : records
								};
							var store = Ext.create('Ext.data.Store', json);
							var arrvRegionCode = airfreight.airQueryWaybill.showCodeForm.getForm().findField('arrvRegionCode');
							arrvRegionCode.store = store;
						}
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
						return false;
					}
				});
				return records; 
			},
			//制作唐翼制单
			saveTangYiAwb : function (url,deptRegionCode,arrvRegionCode,airWaybillNo){
				params = {
						'pointsSingleJointTicketVO.deptRegionCode': deptRegionCode,
						'pointsSingleJointTicketVO.arrvRegionCode': arrvRegionCode,
						'pointsSingleJointTicketVO.airWaybillNo': airWaybillNo
				};
				Ext.Ajax.request({
					//设置后台返回的合票号
					async: false,
					url:airfreight.realPath(url),
					params : params,
					success:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.makesuccess'));
					},
					exception:function(response){
						var result = Ext.decode(response.responseText);
						Ext.Msg.alert(airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.promptMessage'),result.message);
						return false;
					}
				});
			},
			items: [{
				xtype: 'combobox',
				mode:'local',
				queryMode: 'local',		
				triggerAction:'all',
				forceSelection:true,
				editable:false,
				fieldLabel:     airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.deptRegionCode'),
				name:           'deptRegionCode',
				displayField:   'airportCode',
				valueField:     'airportCode',		
				store: null,
				labelWidth:100,
				width:300,
    			listeners : {
    				focus : function(e,t,s) {
    					var record = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord();
    					var deptRegionCode = record.data.deptRegionCode;
    					var url = 'byDeptRegionCodeQueryAirportCode.action';
    					var cityCode = deptRegionCode;
    					var paramsTypes = 'deptRegion';
    					airfreight.airQueryWaybill.showCodeForm.queryAirportCode(url,cityCode,paramsTypes);
    				}
				//beforequery事件
    			}
			},{
				xtype: 'combobox',
				mode:'local',
				queryMode: 'local',		
				triggerAction:'all',
				forceSelection:true,
				editable:false,
				fieldLabel:     airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.arrvRegionCode'),
				name:           'arrvRegionCode',
				displayField:   'airportCode',
				valueField:     'airportCode',		
				store: null,
				labelWidth:100,
				width:300,
				listeners : {
					focus : function(e,t,s) {
						var record = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord();
    					var arrvRegionCode = record.data.arrvRegionCode;
    					var url = 'byArrvRegionCodeQueryAirportCode.action';
    					var cityCode = arrvRegionCode;
    					var paramsTypes = 'arrvRegion'; 
    					airfreight.airQueryWaybill.showCodeForm.queryAirportCode(url,cityCode,paramsTypes);
    				}
    			}
			},{
				xtype:'container',
				margin:'10 5 5 60'
			},{
				dockedItems:[{
					   xtype:'toolbar',
					   dock:'top',
					   layout:'column',
					   defaultType:'button',
					   width:300,
					   items:[{
						   xtype:'container',
						   html:'&nbsp;',
						   columnWidth:.65
					   },{
						   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.ensure'),
						   columnWidth:.15,
						   handler:function() {
							   var deptRegionCode = airfreight.airQueryWaybill.showCodeForm.getForm().findField('deptRegionCode').getValue();
							   var arrvRegionCode = airfreight.airQueryWaybill.showCodeForm.getForm().findField('arrvRegionCode').getValue();
							   var record = airfreight.airQueryWaybill.updateCurrentAirwayBillForm.getRecord();
							   var airWaybillNo = record.data.airWaybillNo;
							   var url = 'saveTangYiAwb.action';
							   airfreight.airQueryWaybill.showCodeForm.saveTangYiAwb(url,deptRegionCode,arrvRegionCode,airWaybillNo);
						   }
					   },{
						   xtype:'container',
						   html:'&nbsp;',
						   columnWidth:.05
					   },{
						   text:airfreight.airQueryWaybill.i18n('foss.airfreight.airEnteringFlightBill.cancel'),
						   columnWidth:.15,
						   handler:function() {
							   airfreight.airQueryWaybill.airLineTwoletterFreeCode.close();
						   }
					   }]
				}]
			}]
		});
		return airfreight.airQueryWaybill.showCodeForm;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({},config);
		me.callParent([cfg]);
		me.createForm();
		me.add([airfreight.airQueryWaybill.showCodeForm]);
	}
});