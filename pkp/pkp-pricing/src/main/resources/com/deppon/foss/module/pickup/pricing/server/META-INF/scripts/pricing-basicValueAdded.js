pricing.basicValueAdded.VALUATION_TYPE_BASICVALUEADDED = 'BASICVALUEADDED'//计费规则——规则类型——基础增值服务
pricing.basicValueAdded.PRICING_CODE_VALUEADDED = 'VAS';//增值服务是BF，HK,SH等的父节点
pricing.basicValueAdded.insurance = 'BF';//保费
pricing.basicValueAdded.paymentCollection = 'HK';//代收货款
pricing.basicValueAdded.deliveryCharges = 'SH';//送货费
pricing.basicValueAdded.receivingCharges = 'JH';//接货费
pricing.basicValueAdded.sign = 'QS';//签收回单
pricing.basicValueAdded.storageCharges = 'CCF';//仓储费
pricing.basicValueAdded.otherCharges = 'QT';//其他费用
pricing.basicValueAdded.superDelivery = 'CY';//超远派送
pricing.basicValueAdded.deliveryUpstairs = 'SHSL';//送货上楼
pricing.basicValueAdded.paperFiberPacking = 'ZQBZ';//纸纤包装
pricing.basicValueAdded.largeDeliveryUpstairs = 'DJSL';//大件上楼
pricing.basicValueAdded.packing = 'BZ';//包装
pricing.basicValueAdded.ALL = 'ALL';//全部
pricing.basicValueAdded.LONG = 'L';//长途
pricing.basicValueAdded.SHORT = 'S';//短途
pricing.basicValueAdded.Weight = 'WEIGHT';//计价方式明细-计费类别-按重量
pricing.basicValueAdded.Volume = 'VOLUME';//计价方式明细-计费类别-按体积
pricing.basicValueAdded.originalCost = 'ORIGINALCOST';//计价方式明细-计费类别-原始费用计费
pricing.basicValueAdded.kilometer = 'KILOM';//计价方式明细-计费类别-按公里
pricing.basicValueAdded.returnSubType = 'COD__COD_TYPE';//即日退，三日退，审核退
//签单回收类型
pricing.basicValueAdded.VALUEADDED_RETURN_TYPE = 'VALUEADDED_RETURN_TYPE';
pricing.basicValueAdded.YJFD = 'ORIGINAL';//计价方式明细--服务子类型-签收回单-原件反单
pricing.basicValueAdded.CZFD = 'FAX';//计价方式明细--服务子类型-签收回单-传真反单
//包装类型subType
pricing.basicValueAdded.VALUEADDED_PACKAGE_TYPE = 'VALUEADDED_PACKAGE_TYPE';
pricing.basicValueAdded.PACKAGE_TYPE__BOX='BOX';//木箱
pricing.basicValueAdded.PACKAGE_TYPE__FRAME='FRAME';//木架
pricing.basicValueAdded.PACKAGE_TYPE_SALVER='SALVER';//木托
//费率或者费用类型
pricing.basicValueAdded.FEE = 1;//费用
pricing.basicValueAdded.RATE = 0;//费率
//KG 或者m³ 或者 公里
pricing.basicValueAdded.KG = 'KG';
pricing.basicValueAdded.M3 = 'm³';
pricing.basicValueAdded.KM = '公里';
pricing.basicValueAdded.YUAN = '元';
//元/KG 或者 元/m³ 或者 元/个 或者 元/票
pricing.basicValueAdded.YUANBYWEIGHT = '元/KG';
pricing.basicValueAdded.YUANBYVOLUME = '元/m³';
pricing.basicValueAdded.YUANBYGE = '元/个';
pricing.basicValueAdded.YUANBYTICKET = '元/票';
pricing.basicValueAdded.YUANBYWEIGHTBYTICKET = '元/kg/票';
pricing.basicValueAdded.YUANBYVOLUMEBYTICKET = '元/m³/票';
//计价方式明细—-按重量最大值
pricing.basicValueAdded.CRITERIA_DETAIL_WEIGHT_MAX = 9999999;
//计价方式明细——按体积最大值
pricing.basicValueAdded.CRITERIA_DETAIL_VOLUME_MAX = 9999999;
//计价方式明细——按原始费用计费最大值
pricing.basicValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX = 9999999;
pricing.basicValueAdded.valueAddedType = [];//增值服务类型
pricing.basicValueAdded.otherChargesSubType = [];//其他费用（QT）的子类型
pricing.basicValueAdded.tomorrowTime = null;//服务器明天时间
//根据MANA-1320修改
pricing.basicValueAdded.defaultEndDate = null;//默认截止时间
pricing.basicValueAdded.limitedWarrantyItems = [];//限保物品
//查看明细隐藏删除按钮
pricing.basicValueAdded.viewOrUpdateOrDelete = null;
/**
 * 纸纤包装种类为基础增值
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-15上午10:31
 */
pricing.basicValueAdded.priceFibelPaperPackingEntity_Basic = 'basic';//基础增值
//查询增值服务类型
pricing.searchValueAddedType = function(){
	Ext.Ajax.request({
		url:pricing.realPath('searchValueAddedType.action'),
		async:false,
		jsonData:{'pricingValuationVo':{'priceEntity':{'refCode':pricing.basicValueAdded.PRICING_CODE_VALUEADDED}}},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.basicValueAdded.valueAddedType = result.pricingValuationVo.priceEntityList;//查询增值服务类型
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询限保物品
pricing.queryLimitedWarrantyItemsByEntity = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryLimitedWarrantyItemsByEntity.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.basicValueAdded.limitedWarrantyItems = result.pricingValuationVo.limitedWarrantyItemsEntityList;//查询限保物品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询增值服务类型,其他费用（QT）的子类型
pricing.searchOtherChargesSubType = function(){
	Ext.Ajax.request({
		url:pricing.realPath('searchValueAddedType.action'),
		async:false,
		jsonData:{'pricingValuationVo':{'priceEntity':{'refCode':pricing.basicValueAdded.otherCharges}}},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.basicValueAdded.otherChargesSubType = result.pricingValuationVo.priceEntityList;//查询增值服务类型,其他费用（QT）的子类型
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.basicValueAdded.tomorrowTime = today.setDate(today.getDate()+1);
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//根据MANA-1320修改
//获取默认截止时间
pricing.haveDefaultEndTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveDefaultEndTime.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var endDate = new Date(result.pricingValuationVo.defaultEndTime);
			pricing.basicValueAdded.defaultEndDate = endDate;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//转换long类型为日期(在model中会用到)
pricing.changeLongToDate = function(value) {
	if (value != null) {
		var date = new Date(value);
		return date;
	} else {
		return null;
	}
};
//Ajax请求--json
pricing.requestJsonAjax = function(url,params,successFn,failFn)
{
	Ext.Ajax.request({
		url:url,
		jsonData:params,
		success:function(response){
			var result = Ext.decode(response.responseText);
			if(result.success){
				successFn(result);
			}else{
				failFn(result);
			}
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			failFn(result);
		}
	});
};
/**.
 * <p>
 * 公共方法，通过storeId和model创建STORE<br/>
 * <p>
 * @param  storeId  
 * @param  model   store所用到的model名
 * @param  fields   store所用到的fields
 * @returns store  返回创建的store
 * @author 张斌
 * @时间 2012-8-31
 */
pricing.getStore = function(storeId,model,fields,data) {
	var store = null;
	if(!Ext.isEmpty(storeId)){
		store = Ext.data.StoreManager.lookup(storeId);
	}
	if(Ext.isEmpty(data)){
		data = [];
	}
	if(!Ext.isEmpty(model)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    model:model,
			    data:data
		     });
		}
	}
	if(!Ext.isEmpty(fields)){
		if(Ext.isEmpty(store)){
			store = Ext.create('Ext.data.Store', {
				storeId:storeId,
			    fields:fields,
			    data:data
		     });
		}
	}
	return store;
};

//发送请求获取计价方式明细信息，并显示在window中
//参数valuationRecord计费规则RECORD
//参数windowShow计费规则显示的弹窗
//参数isUpdate标记是否是修改
pricing.requestAndSetInfo = function(valuationRecord,windowShow,isUpdate){
	 var params = {'pricingValuationVo':{'valuationId':valuationRecord.get('id'),'priceEntity':{'code':valuationRecord.get('pricingEntryCode')}}};
	    var successFun = function(json){
	    	var priceCriteriaDetailEntityList = json.pricingValuationVo.priceCriteriaDetailEntityList;
	    	if(priceCriteriaDetailEntityList.length<1){
	    		pricing.showErrorMes(pricing.basicValueAdded.i18n('foss.pricing.articleBasisValueAddedServiceErrorLackDetailedInformation'));
	    		return;
	    	}
	    	windowShow.getEditForm().loadRecord(valuationRecord);
	    	var businessBeginTimeField = windowShow.down('fieldcontainer[name=businessBeginTime]'),
	    		businessEndTimeField = windowShow.down('fieldcontainer[name=businessEndTime]');
	    	businessBeginTimeField.setSubmitValue(businessBeginTimeField,valuationRecord.get('businessBeginTime'));
	    	businessEndTimeField.setSubmitValue(businessEndTimeField,valuationRecord.get('businessEndTime'));
	    	if(isUpdate){//如果是修改将生效日期修改为今天
	    		windowShow.valuationRecord = valuationRecord;//将计费规则的RECORD设置到window中
	    		windowShow.priceCriteriaDetailEntityList = priceCriteriaDetailEntityList;//将计价方式明细的LIST设置到window中
	    		//windowShow.getEditForm().getForm().findField('beginTime').setValue(new Date(pricing.basicValueAdded.tomorrowTime));
//	    		if(valuationRecord.get('active')=='Y'){//如果未激活,则长短途不可以修改
//		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[0].setReadOnly(true);
//		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[1].setReadOnly(true);
//		    	}else if(valuationRecord.get('active')=='N'){//如果未激活,则长短途可以修改
//		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[0].setReadOnly(false);
//		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[1].setReadOnly(false);
//		    	}
	    	}
	    	/**
	    	 * SHSL,SH,JH,BZ,CCF,QT,DJSL
	    	 * 默认费率,最低费率,最高费率全都要除以100
	    	 */
	    	if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.deliveryUpstairs
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.deliveryCharges
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.receivingCharges
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.packing
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.storageCharges
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.otherCharges
	    		||valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.largeDeliveryUpstairs){
	    		Ext.Array.each(priceCriteriaDetailEntityList,function(rr){
	    			if(!Ext.isEmpty(rr.feeRate)){
	    				rr.feeRate = Number((rr.feeRate/100).toFixed(3));
	    			}
	    			if(!Ext.isEmpty(rr.maxFeeRate)){
	    				rr.maxFeeRate = Number((rr.maxFeeRate/100).toFixed(3));
	    			}
	    			if(!Ext.isEmpty(rr.minFeeRate)){
	    				rr.minFeeRate = Number((rr.minFeeRate/100).toFixed(3));
	    			}
	    		});
	    	}
	    	if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.paymentCollection){
	    		Ext.Array.each(priceCriteriaDetailEntityList,function(rr){
	    			if(!Ext.isEmpty(rr.leftrange)&&!Ext.isEmpty(rr.rightrange)){
	    				rr.leftrange = rr.leftrange/100;
	    				rr.rightrange = rr.rightrange/100;
	    			}
	    		});
	    	}
	    	if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.insurance){//保费
	    		windowShow.getDownPanel().getInsuranceFormPanel().getSubTypeCombo().setValue(priceCriteriaDetailEntityList[0].subType);
	    		//windowShow.getDownPanel().getInsuranceFormPanel().loadRecord(new Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity(priceCriteriaDetailEntityList[0]));
	    		windowShow.getDownPanel().getInsuranceFormPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.storageCharges){//仓储
//	    		priceCriteriaDetailEntityList[0].feeRate = priceCriteriaDetailEntityList[0].feeRate/100;
	    		windowShow.getDownPanel().getStorageChargesFormPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.otherCharges){//其它
	    		windowShow.getDownPanel().getOtherChargesGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.deliveryCharges){//送货
	    		windowShow.getDownPanel().getDeliveryChargesGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.paymentCollection){//代收货款
	    		windowShow.getDownPanel().getPaymentCollectionGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.superDelivery){//超远派送
	    		windowShow.getDownPanel().getSuperDeliveryGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.deliveryUpstairs){//送货上楼费
	    		windowShow.getDownPanel().getDeliveryUpstairsGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.packing){//包装费
	    		windowShow.getDownPanel().getPackingFormPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.sign){//签收回单
	    		windowShow.getDownPanel().getSignFormPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.receivingCharges){//接货费
	    		windowShow.getDownPanel().getReceivingChargesFormPanel().getStore().loadData(priceCriteriaDetailEntityList);
		    }else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.paperFiberPacking){//纸纤包装
	    		var elements = windowShow.getDownPanel().getPaperFiberPackingFormPanel().getForm().getFields( ).items;//获得form页面元素
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			var priceFibelPaperPackingEntity=priceCriteriaDetailEntityList[i].priceFibelPaperPackingEntity;
	    			if(!Ext.isEmpty(priceFibelPaperPackingEntity)){	    			
		    			elements[0].setValue(priceFibelPaperPackingEntity.paperBoxOne)//纸箱1号客户
		    			elements[1].setValue(priceFibelPaperPackingEntity.fibelBagOneBlue)//纤袋1号蓝
		    			elements[2].setValue(priceFibelPaperPackingEntity.fibelBagOneWhite)//纤袋1号白
		    			elements[3].setValue(priceFibelPaperPackingEntity.paperBoxTwo)//纸箱2号客户
		    			elements[4].setValue(priceFibelPaperPackingEntity.fibelBagTwoBlue)//纤袋2号蓝
		    			elements[5].setValue(priceFibelPaperPackingEntity.fibelBagTwoWhite)//纤袋2号白
		    			elements[6].setValue(priceFibelPaperPackingEntity.paperBoxThree)//纸箱3号客户
		    			elements[7].setValue(priceFibelPaperPackingEntity.fibelBagThreeBlue)//纤袋3号蓝
		    			elements[8].setValue(priceFibelPaperPackingEntity.fibelBagThreeWhite)//纤袋3号白
		    			elements[9].setValue(priceFibelPaperPackingEntity.paperBoxFour)//纸箱4号客户
		    			elements[10].setValue(priceFibelPaperPackingEntity.fibelBagFourBlue)//纤袋4号蓝
		    			elements[11].setValue(priceFibelPaperPackingEntity.fibelBagFourWhite)//纤袋4号白
		    			elements[12].setValue(priceFibelPaperPackingEntity.fibelBagFiveWhite)//纤袋5号白
		    			elements[13].setValue(priceFibelPaperPackingEntity.fibelBagSixWhite)//纤袋6号白
	    			}
	    		}
	    	/**
	    	 * @需求：大件上楼优化需求
	    	 * @功能：配合定价优化项目修改代码
	    	 * @author:218371-foss-zhaoyanjun
	    	 * @date:2015-05-20上午10:31
	    	 */
		    }else if(valuationRecord.get('pricingEntryCode')==pricing.basicValueAdded.largeDeliveryUpstairs){//大件上楼
	    		windowShow.getDownPanel().getLargeDeliveryUpstairsGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
		    }
	    	windowShow.show();
	    };
	    var failureFun = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var url = pricing.realPath('searchCriteriaDetailById.action');
		pricing.requestJsonAjax(url,params,successFun,failureFun);
};
/**
 * 校验左右区间是否重叠,返回true或者false,并提示
 * 不能传入空数据
 */
pricing.checkRange = function(records){
	for(var i in records){
		var record = records[i];
		for(var j=0;j<i;j++){
			var tempRecord = records[j];
			if(tempRecord.get('valueaddCaculateType')==record.get('valueaddCaculateType')
				&&record.get('subType')==tempRecord.get('subType')){
				if(!(record.get('valueaddLeftrange')>=tempRecord.get('valueaddRightrange')
					||record.get('valueaddRightrange')<=tempRecord.get('valueaddLeftrange'))){
					var msg = '区间【'+record.get('valueaddLeftrange')+'-'+record.get('valueaddRightrange')
							+'】和区间【'+tempRecord.get('valueaddLeftrange')+'-'+tempRecord.get('valueaddRightrange')+'】重叠'; 
					pricing.showWoringMessage(msg);
					return false;
				}
			}
		}
	}
	return true;
}
/**
 * 校验数据,包括了leftrange和rightrange
 */
pricing.checkBusinessRange = function(records){
	for(var i in records){
		var record = records[i];
		for(var j=0;j<i;j++){
			var tempRecord = records[j];
			if(tempRecord.get('valueaddCaculateType')==record.get('valueaddCaculateType')
				&&record.get('subType')==tempRecord.get('subType')){
				//如果起点终点没有交集
				if(record.get('valueaddLeftrange')>=tempRecord.get('valueaddRightrange')
					||record.get('valueaddRightrange')<=tempRecord.get('valueaddLeftrange')){
					continue;
				//如果起点跟终点有交集,就看业务范围的起点跟终点是否有交集
				}else{
					//如果有交集
					if(!(record.get('leftrange')>=tempRecord.get('rightrange')
					||record.get('rightrange')<=tempRecord.get('leftrange'))){
						pricing.showWoringMessage("基础数据:第"+(Number(i)+1)+"条,跟第"+(j+1)+"条冲突");
						return false;
					}else{
						continue;
					}
				}
			}
		}
	}
	return true;
}
pricing.addValueAdded = function(editForm,me){
	var priceValuationEntity = new Foss.pricing.basicValueAdded.PriceValuationEntityModel();//创建计费规则的MODEL
	editForm.updateRecord(priceValuationEntity);//将上半部分数据设置到创建计费规则的MODEL中
	var pricingEntryId = editForm.findField('pricingEntryCode').pricingEntryId;//增值服务类型处获取计价条目ID
	priceValuationEntity.set('pricingEntryId',pricingEntryId);//将几家条目ID设置到MODEL中
	priceValuationEntity.set('type',pricing.basicValueAdded.VALUATION_TYPE_BASICVALUEADDED);//设置增值服务类型为基础增值服务
	var priceCriteriaDetailEntityList = new Array();
	var pricingEntryCode = priceValuationEntity.get('pricingEntryCode');//得到计价条目CODE
	if(pricingEntryCode==pricing.basicValueAdded.insurance){//保费 BF
		var grid = me.getDownPanel().getInsuranceFormPanel();
		var isNotValid = false;
		if(grid.getStore().getCount()==0){
			pricing.showWoringMessage('请维护基础数据');
			return;
		}
		grid.getStore().each(function(record){
			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
				pricing.showWoringMessage('有一条基础数据起点大于终点');
				isNotValid = true;
				return ;
			}
			if(record.get('leftrange')>record.get('rightrange')){
				pricing.showWoringMessage('有一条基础数据声明价值起点大于声明价值终点');
				isNotValid = true;
				return ;
			}
			if(record.get('minFeeRate')>record.get('maxFeeRate')){
				pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
				isNotValid = true;
				return ;
			}
			record.set('subType',grid.getSubTypeCombo().getValue());
			priceCriteriaDetailEntityList.push(record.data);
		});
		if(isNotValid){
			return;
		}
		if(!pricing.checkBusinessRange(grid.getStore().getRange())){
			return;
		}
		priceValuationEntity.data['subType'] = grid.getSubTypeCombo().getValue();
		/*
    	var insuranceForm = me.getDownPanel().getInsuranceFormPanel().getForm();
    	var priceCriteriaDetailEntity= new Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity();//计价方式明细
    	if(insuranceForm.isValid()){
    		insuranceForm.updateRecord(priceCriteriaDetailEntity);//设置计价方式明细的值
    		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
				pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.minimumPremiumGreaterThanMaximumCoverageForMinimumInsurance'));//代收货款中有一条数据，最低一票大于最高一票！
				return ;
			}
    		priceCriteriaDetailEntity.set('caculateType',pricing.basicValueAdded.originalCost);
    		priceCriteriaDetailEntity.set('valueaddRightrange',pricing.basicValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);
    		priceCriteriaDetailEntity.set('valueaddLeftrange',0);
    	}else{
    		return;
    	}
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);
    	*/
	}else if(pricingEntryCode==pricing.basicValueAdded.paymentCollection){//代收货款 HK
		var paymentCollectionGrid = me.getDownPanel().getPaymentCollectionGridPanel();
		if(paymentCollectionGrid.getStore().getCount()>0){
			var isNotValid = false;
			paymentCollectionGrid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage('有一条基础数据代收金额起点大于代收金额终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFeeRate')>record.get('maxFeeRate')){
					pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
					isNotValid = true;
					return;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkBusinessRange(paymentCollectionGrid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据！');
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.superDelivery){//超远派送 CY
		var superDeliveryGrid = me.getDownPanel().getSuperDeliveryGridPanel();
		if(superDeliveryGrid.getStore().getCount()>0){
			var isNotValid = false;
			superDeliveryGrid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage('有一条基础数据距离起点大于距离终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkBusinessRange(superDeliveryGrid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.deliveryUpstairs){//送货上楼 SHSL
		var deliveryUpstairsGrid = me.getDownPanel().getDeliveryUpstairsGridPanel();
		if(deliveryUpstairsGrid.getStore().getCount()>0){
			var isNotValid = false;
			deliveryUpstairsGrid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFeeRate')>record.get('maxFeeRate')){
					pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
					isNotValid = true;
					return;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkRange(deliveryUpstairsGrid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.deliveryCharges){//送货费 SH
		var deliveryChargesGrid = me.getDownPanel().getDeliveryChargesGridPanel();
		if(deliveryChargesGrid.getStore().getCount()>0){
			var isNotValid = false;
			deliveryChargesGrid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFeeRate')>record.get('maxFeeRate')){
					pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
					isNotValid = true;
					return;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkRange(deliveryChargesGrid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.receivingCharges){//接货费 JH
		var grid = me.getDownPanel().getReceivingChargesFormPanel();
		if(grid.getStore().getCount()>0){
			var isNotValid = false;
			grid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkRange(grid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.sign){//签收回单 QS
		var grid = me.getDownPanel().getSignFormPanel();//得到FORM表单
		if(grid.getStore().getCount()>0){
			var isNotValid = false;
			grid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkRange(grid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
			return;
		}
	}else if(pricingEntryCode==pricing.basicValueAdded.storageCharges){//仓储费 CCF
		var grid = me.getDownPanel().getStorageChargesFormPanel();
    	if(grid.getStore().getCount()==0){
    		pricing.showWoringMessage('请维护基础数据');
    		return;
    	}else{
    		var isNotValid = false;
    		grid.getStore().each(function(record){
    			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
    				pricing.showWoringMessage('仓储费中有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
    			}
    			if(record.get('minFeeRate')>record.get('maxFeeRate')){
    				pricing.showWoringMessage('仓储费中有一条基础数据最低费率大于最高费率');
					isNotValid = true;
					return ;
    			}
    			if(record.get('minFee')>record.get('maxFee')){
    				pricing.showWoringMessage('仓储费中有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
    			}
    			priceCriteriaDetailEntityList.push(record.data);
    		});
    		if(isNotValid){
    			return;
    		}
    		if(!pricing.checkRange(grid.getStore().getRange())){
    			return;
    		}
    	}
	}else if(pricingEntryCode==pricing.basicValueAdded.otherCharges){//其它费用 QT
		var grid = me.getDownPanel().getOtherChargesGridPanel();
		if(grid.getStore().getCount()>0){
			var isNotValid = false;
			grid.getStore().each(function(record){
    			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
    				pricing.showWoringMessage('其他费用中有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
    			}
    			if(record.get('minFee')>record.get('maxFee')){
    				pricing.showWoringMessage('其他费用中有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
    			}
    			priceCriteriaDetailEntityList.push(record.data);
    		});
			if(isNotValid){
				return ;
			}
			if(!pricing.checkRange(grid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');
			return;
		}
	}else if(pricingEntryCode == pricing.basicValueAdded.packing){
		var grid = me.getDownPanel().getPackingFormPanel();
		var isNotValid = false;
		if(grid.getStore().getCount()==0){
    		pricing.showWoringMessage('请维护基础数据');
    		return;
    	}
		grid.getStore().each(function(record){
			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
				pricing.showWoringMessage('包装费中有一条基础数据起点大于终点');
				isNotValid = true;
				return ;
			}
			if(record.get('minFeeRate')>record.get('maxFeeRate')){
				pricing.showWoringMessage('包装费中有一条基础数据最低费率大于最高费率');
				isNotValid = true;
				return ;
			}
			if(record.get('minFee')>record.get('maxFee')){
				pricing.showWoringMessage('包装费中有一条基础数据最低一票大于最高一票');
				isNotValid = true;
				return ;
			}
			priceCriteriaDetailEntityList.push(record.data);
		});
		if(isNotValid){
			return ;
		}
		if(!pricing.checkRange(grid.getStore().getRange())){
			return;
		}
	}
	/**
	 * 纸纤包装数据组装
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-11-13下午17:29
	 */
	else if(pricingEntryCode==pricing.basicValueAdded.paperFiberPacking){
		var fibelPaperPackingForm=me.getDownPanel().getPaperFiberPackingFormPanel().getForm();
		var priceCriteriaDetailEntityFibelPaperPacking= new Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity();
		var priceFibelPaperPackingEntity=new Foss.pricing.basicValueAdded.PriceFibelPaperPackingEntity();
		if(fibelPaperPackingForm.isValid()){
			var elements = fibelPaperPackingForm.getFields().items;//获得FORM页面元素，一个原件返单，一个传真返单
			priceFibelPaperPackingEntity.set('paperBoxOne',elements[0].getValue());
			priceFibelPaperPackingEntity.set('fibelBagOneBlue',elements[1].getValue());
			priceFibelPaperPackingEntity.set('fibelBagOneWhite',elements[2].getValue());
			priceFibelPaperPackingEntity.set('paperBoxTwo',elements[3].getValue());
			priceFibelPaperPackingEntity.set('fibelBagTwoBlue',elements[4].getValue());
			priceFibelPaperPackingEntity.set('fibelBagTwoWhite',elements[5].getValue());
			priceFibelPaperPackingEntity.set('paperBoxThree',elements[6].getValue());
			priceFibelPaperPackingEntity.set('fibelBagThreeBlue',elements[7].getValue());
			priceFibelPaperPackingEntity.set('fibelBagThreeWhite',elements[8].getValue());
			priceFibelPaperPackingEntity.set('paperBoxFour',elements[9].getValue());
			priceFibelPaperPackingEntity.set('fibelBagFourBlue',elements[10].getValue());
			priceFibelPaperPackingEntity.set('fibelBagFourWhite',elements[11].getValue());
			priceFibelPaperPackingEntity.set('fibelBagFiveWhite',elements[12].getValue());
			priceFibelPaperPackingEntity.set('fibelBagSixWhite',elements[13].getValue());
			priceFibelPaperPackingEntity.set('kind',pricing.basicValueAdded.priceFibelPaperPackingEntity_Basic);
		}else{
			return;
		}
		priceCriteriaDetailEntityFibelPaperPacking.set('priceFibelPaperPackingEntity',priceFibelPaperPackingEntity.data);
		priceCriteriaDetailEntityFibelPaperPacking.set('caculateType',pricing.basicValueAdded.originalCost);
		priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityFibelPaperPacking.data);
	}
	/**
	 * 大件上楼费数据组装
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-12-11上午10:50
	 */
	else if(pricingEntryCode==pricing.basicValueAdded.largeDeliveryUpstairs){
		var largeDeliveryUpstairsGrid = me.getDownPanel().getLargeDeliveryUpstairsGridPanel();
		if(largeDeliveryUpstairsGrid.getStore().getCount()>0){
			var isNotValid = false;
			largeDeliveryUpstairsGrid.getStore().each(function(record){
				if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
					pricing.showWoringMessage('有一条基础数据起点大于终点');
					isNotValid = true;
					return ;
				}
				if(record.get('minFeeRate')>record.get('maxFeeRate')){
					pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
					isNotValid = true;
					return;
				}
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
					isNotValid = true;
					return ;
				}
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
			if(!pricing.checkRange(largeDeliveryUpstairsGrid.getStore().getRange())){
				return;
			}
		}else{
			pricing.showWoringMessage('请维护基础数据');
			return;
		}
	}
	// 需要记录是新增操作还是升级版本操作
	var operateTypeForAddAndUpdateVersion = pricing.basicValueAdded.viewOrUpdateOrDelete;
	priceValuationEntity.set('operateTypeForAddAndUpdateVersion', operateTypeForAddAndUpdateVersion);
	//货物类型已选择数据,基础产品已选择数据
	var arr1=[],arr2=[];
	arr1 = pricing.getIndustryData(me.down('treepanel[name=industryTree]'));
	var params = {'pricingValuationVo':{'priceCriteriaDetailEntityList':priceCriteriaDetailEntityList
		,'priceValuationEntity':priceValuationEntity.data}};//组装数据
	var businessBeginTimeField = me.down('fieldcontainer[name=businessBeginTime]'),
		businessEndTimeField = me.down('fieldcontainer[name=businessEndTime]');
	params.pricingValuationVo.priceValuationEntity['businessBeginTime'] = businessBeginTimeField.getSubmitValue(businessBeginTimeField);
	params.pricingValuationVo.priceValuationEntity['businessEndTime'] = businessEndTimeField.getSubmitValue(businessEndTimeField);
	params.pricingValuationVo.priceValuationEntity['customerIndustryEntityList'] = arr1;
	params.pricingValuationVo.priceValuationEntity['productList'] = arr2;
	var successFun = function(json){
		pricing.showInfoMes(json.message);//提示信息
		var grid  = Ext.getCmp('T_pricing-indexValuation_content').getPricingValuationGrid();
		grid.getPagingToolbar().moveFirst();
		me.hide();
	};//成功之后提示成功，隐藏现在界面
	var failureFun = function(json){
	    if (Ext.getCmp('pricing_basicValueAdded_commitButton') != undefined) {
			Ext.getCmp('pricing_basicValueAdded_commitButton').enable();
		}
	    if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
			Ext.getCmp('pricing_basicValueAdded_updateCommitButton').enable();
		}
	    
		if(Ext.isEmpty(json)){
			pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
		} else {
			pricing.showErrorMes(json.message);
		}
	};
	var url = pricing.realPath('addValueAdded.action');//新增基础增值服务
	//新增Window提交按钮
    if (Ext.getCmp('pricing_basicValueAdded_commitButton') != undefined) {
		Ext.getCmp('pricing_basicValueAdded_commitButton').disable(); // 发送请求之前禁用提交按钮
	}
    //修改Window提交按钮
    if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
		Ext.getCmp('pricing_basicValueAdded_updateCommitButton').disable();
	}
	pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
}

//------------------------------------pricing----------------------------------
//货物类型里的grid的model
Ext.define('Foss.pricing.basicValueAdded.GoodTypeModel',{
	extend: 'Ext.data.Model',
	fields:[{
		name:'parentProfessionCode'
	},{
		name:'parentProfessionName'
	},{
		name:'professionCode'
	},{
		name:'professionName'
	}]
});
//计价条目MODEL
Ext.define('Foss.pricing.basicValueAdded.PriceEntityModel', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert :pricing.changeLongToDate
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'name',//name
        type : 'string'
    },{
        name : 'code',//编码
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'refId',//父节点ID
        type : 'string'
    },{
        name : 'refCode',//父节点CODE
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    },{
        name : 'beginTime',//有效起始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime',//有效截止时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createOrgCode',//创建人部门CODE
        type : 'string'
    },{
        name : 'modifyOrgCode',//修改人部门CODE
        type : 'string'
    },{
        name : 'blongPricingId',//归集类型ID
        type : 'string'
    },{
        name : 'blongPricingCode',//归集类型CODE
        type : 'string'
    },{
        name : 'blongPricingName',//归集类型名称
        type : 'string'
    }]
});
//计费规则MODEL
Ext.define('Foss.pricing.basicValueAdded.PriceValuationEntityModel', {
    extend: 'Ext.data.Model',
    fields : [{
    	name:'businessBeginTime'//业务发生时间
    },{
    	name:'businessEndTime'//业务结束时间
    },{
    	name:'subType'
    },{
        name : 'id',//id
        type : 'string'
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'createUser',//创建人工号
        type : 'string'
    },{
        name : 'createUserName',//创建人姓名
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',//修改人工号
        type : 'string'
    },{
        name : 'modifyUserName',//修改人姓名
        type : 'string'
    },{
        name : 'goodsTypeId',//货物类型ID
        type : 'string'
    },{
        name : 'goodsTypeCode',//货物类型CODE
        type : 'string'
    },{
        name : 'productId',//产品ID
        type : 'string'
    },{
        name : 'deptRegionId', //始发区域ID
        type : 'string'
    },{
        name : 'arrvRegionId',//目的区域ID
        type : 'string'
    },{
        name : 'salesChannelCode',//渠道CODE
        type : 'string'
    },{
        name : 'salesChannelId', // 渠道ID
        type : 'string'
    },{
        name : 'pricingEntryId',//计价条目ID
        type : 'string'
    },{
        name : 'pricingEntryCode', //计价条目CODE
        type : 'string'
    },{
        name : 'pricingEntryName',//计价名称
        type : 'string'
    },{
        name : 'pricePlanId', //价格方案主信息ID
        type : 'string'
    },{
        name : 'pricePlanCode',//价格方案主信息CODE
        type : 'string'
    },{
        name : 'marketingEventId', //市场活动ID
        type : 'string'
    },{
        name : 'marketingEventCode', //市场活动CODE
        type : 'string'
    },{
        name : 'description',   //描述
        type : 'string'
    },{
    	name:'remark'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'beginTime', //有效起始时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'endTime', //有效截止时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'longOrShort',//长途还是短途
        type : 'string'
    },{
        name : 'type',//规则类型
        type : 'string'
    },{
        name : 'currencyCode',  //币种
        type : 'string'
    },{
        name : 'code',//编码
        type : 'string'
    },{
        name : 'name',// 名称
        type : 'string'
    },{
        name : 'criteriaId',//计价方式ID
        type : 'string'
    },{
    	name : 'currentUsedVersion',//是否当前版本
        type : 'string'
    },{
        name : 'operateTypeForAddAndUpdateVersion',//新增or升级版本操作
        type : 'string'
    }]
});
/**
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-13下午18:58
 */
Ext.define('Foss.pricing.basicValueAdded.PriceFibelPaperPackingEntity', {
    extend: 'Ext.data.Model',
    fields : [{
    	name:'valuationId',//计价规格表ID
    	type:'string'
    },{
    	name:'critcriaDetailId',//计价方式明细表ID
    	type:'string'
    },{
    	name:'createDate',//创建时间
        type:'date',
        defaultValue:null
    },{
    	name:'modifyDate',//修改时间
        type:'date',
        defaultValue:null
    },{
    	 name:'paperBoxOne',//纸箱一号
    	 defaultValue:null
    },{
    	name:'paperBoxTwo',//纸箱二号
    	defaultValue:null
    },{
    	name:'paperBoxThree',//纸箱三号
    	defaultValue:null
    },{
    	name:'paperBoxFour',//纸箱四号
    	defaultValue:null
    },{
    	name:'fibelBagOneBlue',//纤袋1号 蓝
    	defaultValue:null
    },{
    	name:'fibelBagTwoBlue',//纤袋2号 蓝
    	defaultValue:null
    },{
    	name:'fibelBagThreeBlue',//纤袋3号 蓝
    	defaultValue:null
    },{
    	name:'fibelBagFourBlue',//纤袋4号 蓝
    	defaultValue:null
    },{
    	name:'fibelBagOneWhite',//纤袋1号 白
    	defaultValue:null
    },{
    	name:'fibelBagTwoWhite',//纤袋2号 白
    	defaultValue:null
    },{
    	name:'fibelBagThreeWhite',//纤袋3号 白
    	defaultValue:null
    },{
    	name:'fibelBagFourWhite',//纤袋4号 白
    	defaultValue:null
    },{
    	name:'fibelBagFiveWhite',//纤袋5号 布匹白
    	defaultValue:null
    },{
    	name:'fibelBagSixWhite',//纤袋6号 布匹白
    	defaultValue:null
    },{
    	name:'kind',//区域种类
    	type:'string',
    	defaultValue:null
    }]
});
//计价方式明细MODEL
Ext.define('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
    },{
    	name:'returnbillType'//返单类型
    },{
    	name:'refundType'//退款类型
    },{
    	name:'canNotCharge'//是否可不收取
    },{
    	name:'valueaddSubType'//费用名称
    },{
    	name:'togeterCategory'//归集列别
    },{
    	name:'minInsuranceFee',//最低保险费
    	defaultValue : null
    },{
    	name:'maxFee',//最高一票
    	defaultValue : null
    },{
    	defaultValue : null,
    	name:'minFee'//最低一票
    },{
    	name:'packageType',//包装类型
    	type:'string'
    },{
    	defaultValue : null,
    	name:'rightrange'//声明价值起点
    },{
    	defaultValue : null,
    	name:'leftrange'//声明价值起点
    },{
    	defaultValue : null,
    	name:'feeRate'//默认费用
    },{
        name : 'createDate',//创建时间
        type : 'date',
        defaultValue : null,
        convert : null
    },{
        name : 'createUser',//创建人
        type : 'string'
    },{
        name : 'modifyDate',//修改时间
        type : 'date',
        defaultValue : null,
        convert : pricing.changeLongToDate
    },{
        name : 'modifyUser',//修改人
        type : 'string'
    },{
        name : 'name',//name//归集类别(现在还不清楚“归类级别是哪一个字段”暂存name中)
        type : 'string'
    },{
        name : 'caculateType',//计费类别
        type : 'string'
    },{
    	name : 'valueaddCaculateType'
    },{
        name : 'fee',//固定费用
        	 defaultValue : null
    },{
        name : 'feeRate',
        defaultValue : null//费率或者单价 或默认费率
    },{
        name : 'valueaddLeftrange',
        defaultValue : null//计价左区间
    },{
        name : 'valueaddRightrange',
        defaultValue : null//计价右区间
    },{
        name : 'minFeeRate',//最低费用 或最低保险费
        	 defaultValue : null
    },{
        name : 'maxFeeRate',//最高费用
        	 defaultValue : null
    },{
        name : 'subType', //服务子类型
        type : 'string'
    },{
        name : 'canmodify',//是否可以修改
        type : 'string'
    },{
        name : 'candelete',//是否可以删除
        type : 'string'
    },{
        name : 'description',//备注
        type : 'string'
    },{
        name : 'processProgram',//特殊处理程序
        type : 'string'
    },{
        name : 'processParmVal',//特殊处理程序参数
        type : 'string'
    },{
        name : 'pricingCriteriaId',//计价方式ID
        type : 'string'
    },{
        name : 'pricingValuationId',//计费规则ID
        type : 'string'
    },{
        name : 'parm2',
        defaultValue : null//计价参数2
    },{
        name : 'parm1',
        defaultValue : null//计价参数1
    },{
        name : 'tSrvPriceRuleId',//价格计算表达式
        type : 'string'
    },{
        name : 'parm3',
        defaultValue : null//计价参数3
    },{
        name : 'parm4',
        defaultValue : null//计价参数4
    },{
        name : 'parm5',
        defaultValue : null//计价参数5
    },{
        name : 'discountRate',
        defaultValue : null//折扣率
    },{
        name : 'active',//数据状态
        type : 'string'
    },{
        name : 'blongPricingName',//归集类型名称
        type : 'string'
    },{
        name : 'minFeeRate',//最低费率
        defaultValue : null
    },{
        name : 'maxFeeRate',//最高费率
        defaultValue : null
    },{
    	/**
    	 * 将Foss.pricing.basicValueAdded.PriceFibelPaperPackingEntity封装到此类中
    	 * @author:218371-foss-zhaoyanjun
    	 * @date:2014-11-13下午19:27
    	 */
    	name:'priceFibelPaperPackingEntity',
    	type:'Foss.pricing.basicValueAdded.PriceFibelPaperPackingEntity',
    	defaultValue:null
    }]
});
//基础产品里的grid的model
Ext.define('Foss.pricing.basicValueAdded.BasicProductModel',{
	extend: 'Ext.data.Model',
	fields:[{
		name:'code'
	},{
		name:'name'
	},{
		name:'active'
	},{
		name:'description'
	},{
		name:'mark'
	},{
		name:'versionNo'
	},{
		name:'beginTime'
	},{
		name:'endTime'
	},{
		name:'transportType'
	},{
		name:'levels'
	},{
		name:'parentCode'
	},{
		name:'parentName'
	},{
		name:'refId'
	},{
		name:'id'
	}]
});
//------------------------------------model----------------------------------
//基础产品里的grid的store
Ext.define('Foss.pricing.basicValueAdded.BasicProductStore',{
	extend : 'Ext.data.Store',
	model:'Foss.pricing.basicValueAdded.BasicProductModel',
	pageSize:1000,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('queryProductList.action'),
		reader : {
			type : 'json',
			root : 'pricingValuationVo.productEntityList',
			totalProperty : 'totalCount'
		}
	}
});
//货物类型里的grid的store
Ext.define('Foss.pricing.basicValueAdded.GoodTypeStore',{
	extend : 'Ext.data.Store',
	model:'Foss.pricing.basicValueAdded.GoodTypeModel',
	pageSize:1000,
	autoLoad:false,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('queryAllSecProfession.action'),
		reader : {
			type : 'json',
			root : 'pricingValuationVo.customerIndustryEntityList',
			totalProperty : 'totalCount'
		}
	}
});

/**
 * 区域Store
 */
Ext.define('Foss.pricing.basicValueAdded.PricingValuationStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.basicValueAdded.PriceValuationEntityModel',//引用MODEL
	pageSize:20,//每页显示20条
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchPricingValuation.action'),//请求增值服务URL
		reader : {
			type : 'json',
			root : 'pricingValuationVo.priceValuationEntityList',//得到的结果
			totalProperty : 'totalCount'
		}
	}
});

//------------------------------------store----------------------------------
/**
 * 基础增值服务查询表单
 */
Ext.define('Foss.pricing.basicValueAdded.QueryBasicValueAddedForm', {
	extend : 'Ext.form.Panel',
	title: pricing.basicValueAdded.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .3,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	anchor : '100%'
    },
    height :100,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {//构造器
		var me = this, 
			cfg = Ext.apply({}, config);
		var valueAddedType = new Array();
		for(var i = 0;i<pricing.basicValueAdded.valueAddedType.length;i++){
			valueAddedType.push(pricing.basicValueAdded.valueAddedType[i]);
		}
		valueAddedType.push({'code':'ALL','name':pricing.basicValueAdded.i18n('i18n.pricingRegion.all')});
		me.items = [{
			name: 'pricingEntryName',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    value:'ALL',
		    store:pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceEntityModel',null
		    ,valueAddedType),
	        fieldLabel: pricing.basicValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//所属增值服务
	        xtype : 'combo'
		},{
			xtype:'datefield',
			fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.businessDate'),//业务日期
			format:'Y-m-d',
			name:'beginTime'
		},{
			xtype: 'displayfield',
			columnWidth : .2,
			value:pricing.basicValueAdded.i18n('foss.pricing.PAccordingValueAddedServicesBusinessDatesRangeClosingDate')//根据业务日期查询介于生效日期和截止日期之间的增值服务
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.search'),//查询按钮
				disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationQueryButton'),
				hidden: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationQueryButton'),
				cls:'yellow_button',
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = Ext.getCmp('T_pricing-indexValuation_content').getPricingValuationGrid();//得大grid
						grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});

/**
 * 基础增值服务列表
 */
Ext.define('Foss.pricing.basicValueAdded.PricingBasicValuationGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.basicValueAdded.i18n('i18n.pricingRegion.searchResults'),
	cls: 'autoHeight',
	bodyCls: 'autoHeight', 
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.basicValueAdded.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
	frame: true,
	//得到bbar（分页）
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (Ext.isEmpty(this.pagingToolbar)) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 20
			});
		}
		return this.pagingToolbar;
	},
	//新增基础增值服务
    basicValueAddedWindow:null,
	getBasicValueAddedWindow:function(){
		if (Ext.isEmpty(this.basicValueAddedWindow)) {
			this.basicValueAddedWindow = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedWindow');
		}
		return this.basicValueAddedWindow;
	},
	//查看基础增值服务详情WINDOW
	basicValueAddedShowWindow:null,
	getBasicValueAddedShowWindow:function(){
		if (Ext.isEmpty(this.basicValueAddedShowWindow)) {
			this.basicValueAddedShowWindow = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedShowWindow');
		}
		return this.basicValueAddedShowWindow;
	},
	//用于修改弹出的WINDOW
	basicValueAddedUpdateWindow:null,
	getBasicValueAddedUpdateWindow:function(){
		if (Ext.isEmpty(this.basicValueAddedUpdateWindow)) {
			this.basicValueAddedUpdateWindow = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedUpdateWindow');
		}
		return this.basicValueAddedUpdateWindow;
	},
	//终止弹窗
	basicValueAddedEndTimeWindow:null,
	getBasicValueAddedEndTimeWindow:function(){
		if (Ext.isEmpty(this.basicValueAddedEndTimeWindow)) {
			this.basicValueAddedEndTimeWindow = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedEndTimeWindow');
			this.basicValueAddedEndTimeWindow.parent = this;
		}
		return this.basicValueAddedEndTimeWindow;
	},
	//新增基础增值服务
	addBasicValueAdded:function(){
		pricing.basicValueAdded.viewOrUpdateOrDelete = 'add';
		var me = this;
		me.getBasicValueAddedWindow().show();//显示新增弹窗
		Ext.getCmp('pricing_basicValueAdded_commitButton').setDisabled(false);
	},
	//激活增值服务
	activeValueAdded:function(){
		var me = this;
		var valuationIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseSelectValueAddedServicesActivated'));//请选择要激活的增值服务！
			return;
		}
		var today = new Date(pricing.basicValueAdded.tomorrowTime);
		today = today.setDate(today.getDate()-1);//得到当天的0点00分
		for(var i = 0 ; i<selections.length ; i++){
			//只有未被激活的区域的ID才会传到后台
			if(selections[i].get('active')=='N'){
				if(selections[i].get('beginTime').getTime()<=today){//过期的数据不能激活
					pricing.showWoringMessage('名称：'+selections[i].get('name')//'名称：
							+'编码：'+selections[i].get('code')//编码：
							+pricing.basicValueAdded.i18n('foss.pricing.basisValueAddedServicesExpiredNotActivate'));//的基础增值服务已经过期，不可激活！
					return;
				}else{
					valuationIds.push(selections[i].get('id'));
				}
			}
		}
		if(valuationIds.length<1){
			pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseSelectAtLeastOneInactiveValueAddedServices'));//请至少选择一条未激活的增值服务！
			return;
		}
		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantActivateTheseValueAddedServicesNotActivated'),function(e){//是否要激活这些未激活的增值服务？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'pricingValuationVo':{'valuationIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeValueAdded.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
	},
	//删除增值服务
	deleteValueAdded:function(){
		var me = this;
		var valuationIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseChooseCeleteNotActivatedValueAddedServices'));//请选择要激活的增值服务！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的增值服务才可以删除
				pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseChooseCeleteNotActivatedValueAddedServices'));//请选择要删除的未激活增值服务！
				return;
			}else if(selections[i].get('active')=='N'){
				valuationIds.push(selections[i].get('id'));
			}
		}
		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteTheseValueAddedServicesNotActivated'),function(e){//是否要删除这些未激活的增值服务？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'pricingValuationVo':{'valuationIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteValueAdded.action');
				pricing.requestJsonAjax(url,params,successFun,failureFun);
			}
		});
		
	},
	   /**
     * 立即生效
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyActiveWindow:null,
	getImmediatelyActiveWindow: function(){
		if(Ext.isEmpty(this.immediatelyActiveWindow)){
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.basicValueAdded.ImmediatelyActiveTimeWindow');
			this.immediatelyActiveWindow.parent = this;
		}
		return this.immediatelyActiveWindow;
	},
	
	
	/**
     * 立即中止
     * 对于实际业务可能在当天发生两次以上的调整，故给予立即激中止功能用于支持该业务，
     * 1、立即中止功能给予特定角色来操作。根据所登陆的用户所属某某角色来决定是否给予立即中止功能。防止一般用户进行当天频繁调价操作
     * 2、立即中止功能中止日期可以等于今天但是不能小于今天。
     * 
     */
    immediatelyStopWindow:null,
	getImmediatelyStopWindow: function(){
		if(Ext.isEmpty(this.immediatelyStopWindow)){
			this.immediatelyStopWindow = Ext.create('Foss.pricing.basicValueAdded.ImmediatelyStopEndTimeWindow');
			this.immediatelyStopWindow.parent = this;
		}
		return this.immediatelyStopWindow;
	},
	
	
	/**
	 * 立即中止
	 */
    immediatelyStop:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')!='Y'){
	 		pricing.showWoringMessage('请选择已激活数据进行操作！');
	 		return;
	 	}else{
	 		var priceValuationEntity = selections[0].data;
	 		me.getImmediatelyStopWindow().priceValuationEntity = priceValuationEntity;
	 		me.getImmediatelyStopWindow().show();
	 	}
	},
	
	/**
	 * 立即激活
	 */
    immediatelyActive:function(){
    	var me = this;
	 	var selections = me.getSelectionModel().getSelection();
	 	if(selections.length < 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections.length > 1){
	 		pricing.showWoringMessage('请选择一条记录进行操作！');
			return;
	 	}
	 	if(selections[0].get('active')=='Y'){
	 		pricing.showWoringMessage('请选择未激活数据进行操作！');
	 		return;
	 	}else{
	 		var priceValuationEntity = selections[0].data;
	 		me.getImmediatelyActiveWindow().priceValuationEntity = priceValuationEntity;
	 		me.getImmediatelyActiveWindow().show();
	 	}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,//序号
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.num')
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.basicValueAdded.i18n('foss.pricing.update'),//修改
                disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationUpdateButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N') {
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	pricing.basicValueAdded.viewOrUpdateOrDelete = 'update';
                	var grid = Ext.getCmp('T_pricing-indexValuation_content').getPricingValuationGrid();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getBasicValueAddedUpdateWindow();
					windowShow.valuationRecord = valuationRecord;
            	    if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
            	    	Ext.getCmp('pricing_basicValueAdded_updateCommitButton').setDisabled(false);
            		}
                	pricing.requestAndSetInfo(valuationRecord,windowShow,true);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.basicValueAdded.i18n('foss.pricing.upgradedVersion'),//升级版本
                disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationCopyButton'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y') {
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	pricing.basicValueAdded.viewOrUpdateOrDelete = 'upgradedVersion';
                	var grid = Ext.getCmp('T_pricing-indexValuation_content').getPricingValuationGrid();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getBasicValueAddedUpdateWindow();
					windowShow.valuationRecord = valuationRecord;
            	    if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
            	    	Ext.getCmp('pricing_basicValueAdded_updateCommitButton').setDisabled(false);
            		}
                	pricing.requestAndSetInfo(valuationRecord,windowShow,true);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.basicValueAdded.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	pricing.basicValueAdded.viewOrUpdateOrDelete = 'delete';
                	var grid = grid.up();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getBasicValueAddedShowWindow();
                	windowShow.valuationRecord = valuationRecord;
                	pricing.requestAndSetInfo(valuationRecord,windowShow,false);
                	
                }
			}]
		},{
			text : pricing.basicValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//所属增值服务
			dataIndex : 'pricingEntryCode',
			renderer:function(value){
				for(var i = 0;i<pricing.basicValueAdded.valueAddedType.length;i++){
					if(pricing.basicValueAdded.valueAddedType[i].code == value){
						return pricing.basicValueAdded.valueAddedType[i].name;
					}
				}
			}
		},{
			text : pricing.basicValueAdded.i18n('foss.pricing.theBasisOfValueAddedServicesName'),
			dataIndex : 'name'
		},{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.lastCreateUser'),//最后修改人
			dataIndex : 'modifyUserName'
		},{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.lastCreateTime'),//最后修改时间
			dataIndex : 'modifyDate',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.basicValueAdded.i18n('foss.pricing.availabilityDate'),//生效日期
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.basicValueAdded.i18n('foss.pricing.deadline'),//截止日期
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text :pricing.basicValueAdded.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.basicValueAdded.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.basicValueAdded.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.store = Ext.create('Foss.pricing.basicValueAdded.PricingValuationStore',{
			autoLoad : false,//自动加载
			pageSize : 20,//每页20条
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-indexValuation_content').getQueryForm();//得到查询的FORM表单
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								//得到查询条件“服务类型”
								'pricingValuationVo.priceValuationEntity.pricingEntryCode' : 
									queryForm.getForm().findField('pricingEntryName').getValue(),
								//得到查询条件“业务日期”
								'pricingValuationVo.priceValuationEntity.beginTime' : 
									queryForm.getForm().findField('beginTime').getValue(),
								//默认查询条件，计费规则类型(基础增值服务)
								'pricingValuationVo.priceValuationEntity.type' : 
									pricing.basicValueAdded.VALUATION_TYPE_BASICVALUEADDED
									
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
		    //增加滚动条事件，防止出现滚动条后却不能用
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
	    //添加多选框
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{
					mode:'MULTI',
					checkOnly:true
				});
		//添加头部按钮
		me.tbar = [{
			//新增
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.add'),
			disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationAddButton'),
			hidden: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationAddButton'),
			handler :function(){
				me.addBasicValueAdded();
			} 
		},'-', {
			//激活
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.active'),
			disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationActiveButton'),
			hidden: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationActiveButton'),
			handler :function(){
				me.activeValueAdded();
			} 
		},'-', {
			//删除
			text : pricing.basicValueAdded.i18n('foss.pricing.delete'),
			disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationDeleteButton'),
			hidden: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationDeleteButton'),
			handler :function(){
				me.deleteValueAdded();
			} 
		},'-', {
			//终止
			text : pricing.basicValueAdded.i18n('foss.pricing.termination'),
			disabled: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationStopButton'),
			hidden: !pricing.basicValueAdded.isPermission('indexValuation/indexValuationStopButton'),
			handler :function(){
				var selections = me.getSelectionModel().getSelection();
				if(selections.length!=1){
					pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseSelectActivationEffectiveDataTermination'));
					return;
				}else{
					var active = selections[0].get('active');
					var endTime = selections[0].get('endTime');
					var beginTime = selections[0].get('beginTime');
					if(active!='Y'||beginTime>=endTime){
						pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.pleaseSelectActivationEffectiveDataTermination'));
						return;
					}
				}
				me.getBasicValueAddedEndTimeWindow().show();
				me.getBasicValueAddedEndTimeWindow().selection = selections[0];
			} 
		},'-', {
			text : pricing.basicValueAdded.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.basicValueAdded.isPermission('indexValuation/indexValuationImmediatelyActiveButton'),
			hidden:!pricing.basicValueAdded.isPermission('indexValuation/indexValuationImmediatelyActiveButton'),
			handler :function(){
				me.immediatelyActive();
			} 
		},'-', {
			text : pricing.basicValueAdded.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.basicValueAdded.isPermission('indexValuation/indexValuationImmediatelyStopButton'),
			hidden:!pricing.basicValueAdded.isPermission('indexValuation/indexValuationImmediatelyStopButton'),
			handler :function(){
				me.immediatelyStop();
			} 
		}];
		//添加分页控件
		me.bbar = me.getPagingToolbar();
		//设置分页控件的store属性
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});
/**
 * @description 基础增值服务主页面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-indexValuation_content')) {
		return;
	}
	pricing.queryLimitedWarrantyItemsByEntity();//查询限保物品
	pricing.searchValueAddedType();//查询增值服务类型
	pricing.searchOtherChargesSubType();//查询增值服务类型,其他费用（QT）的子类型
	pricing.haveServerNowTime();//获取服务当前时间
	//根据MANA-1320修改
	pricing.haveDefaultEndTime();//获取默认截止时间
	var queryForm  = Ext.create('Foss.pricing.basicValueAdded.QueryBasicValueAddedForm');//查询FORM
	var pricingValuationGrid  = Ext.create('Foss.pricing.basicValueAdded.PricingBasicValuationGridPanel');//查询结果显示列表
	var setDate = Ext.create('Ext.form.field.Date');
	setDate.setValue(new Date(pricing.basicValueAdded.tomorrowTime));//得到明天时间是明天的当前时间不是明天的零点零零分
	pricing.basicValueAdded.tomorrowTime = setDate.getValue().getTime();//过一次datefield就变成零点零零分了
	Ext.getCmp('T_pricing-indexValuation').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-indexValuation_content',
		cls : 'panelContentNToolbar',
		bodyCls : 'panelContentNToolbar-body',
		//获得查询FORM
		getQueryForm : function() {
			return queryForm;
		},
		//获得查询结果GRID
		getPricingValuationGrid : function() {
			return pricingValuationGrid;
		},
		items : [ queryForm, pricingValuationGrid]
	}));
});
//----------------------------------------------上面是整体布局，下面是弹出窗口----------------------------------
/**
 * 基础增值服务-查看详情
 */
Ext.define('Foss.pricing.basicValueAdded.BasicValueAddedShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.basicValueAdded.i18n('foss.pricing.detailsOfTheBasisForValueAddedServices'),//基础增值服务详情
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :700,
	height :550,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getEditForm().getForm().reset();
			//me.getDownPanel().getInsuranceFormPanel().getForm().reset();
			me.getDownPanel().getInsuranceFormPanel().getStore().removeAll();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();//超远派送
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();//送货上楼
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getPackingFormPanel().getStore().removeAll();//包装费
			me.getDownPanel().getSignFormPanel().getStore().removeAll();
			me.getDownPanel().getStorageChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
			/**
			 * 重置纸纤包装面板的内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-11-12上午10:23
			 */
			me.getDownPanel().getPaperFiberPackingFormPanel().getForm().reset();
			/**
			 * 重置大件上楼费的内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-12-12上午14:02
			 */
			me.getDownPanel().getLargeDeliveryUpstairsGridPanel().getStore().removeAll();
//			
		},
		beforeshow:function(me){
			
		},
		show:function(win){
			var valuationRecordId = win.valuationRecord.get('id');
			Ext.Ajax.request({
				url:'../pricing/queryAllSecProfession.action',
				params:{
					'pricingValuationVo.valuationId':valuationRecordId
				},
				success:function(response){
					var checkenIndustryList =  Ext.decode(response.responseText).pricingValuationVo.customerIndustryEntityList;
					pricing.showIndustryTreeData(win.down('treepanel[name=industryTree]'),checkenIndustryList);
				}
			});
		}
	},
	editForm:null,//基础增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedEditFormPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
    	return this.editForm;
	},
	downPanel:null,//基础增值服务新增修改查看表单（下半部分）
	//下颁布PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.basicValueAdded.DownPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
		this.downPanel.getInsuranceFormPanel().getSubTypeCombo().setReadOnly(true);//setDisabled(false);
    	return this.downPanel;
	},
	returnGrid:function(){
		var me = this;
		me.close();
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 基础增值服务-修改
 */
Ext.define('Foss.pricing.basicValueAdded.BasicValueAddedUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.basicValueAdded.i18n('foss.pricing.modifyBasisForValueAddedServices'),//基础增值服务详情
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :700,
	height :550,
	valuationRecord:null,//计费规则的record
	priceCriteriaDetailEntityList:null,//计价方式明细LIST
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getEditForm().getForm().reset();
			//me.getDownPanel().getInsuranceFormPanel().getForm().reset();
			me.getDownPanel().getInsuranceFormPanel().getStore().removeAll();
			me.getDownPanel().getInsuranceFormPanel().getSubTypeCombo().reset();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();//超远派送
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();//送货上楼
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getPackingFormPanel().getStore().removeAll();
			me.getDownPanel().getSignFormPanel().getStore().removeAll();
			me.getDownPanel().getStorageChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
			/**
			 * 重置纸纤包装面板的内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-11-12上午10:23
			 */
			me.getDownPanel().getPaperFiberPackingFormPanel().getForm().reset();
			/**
			 * 重置大件上楼费的内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-12-12上午14:02
			 */
			me.getDownPanel().getLargeDeliveryUpstairsGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			
		},
		show:function(win){
			var valuationRecordId = win.valuationRecord.get('id');
			Ext.Ajax.request({
				url:'../pricing/queryAllSecProfession.action',
				params:{
					'pricingValuationVo.valuationId':valuationRecordId
				},
				success:function(response){
					var checkenIndustryList =  Ext.decode(response.responseText).pricingValuationVo.customerIndustryEntityList;
					pricing.showIndustryTreeData(win.down('treepanel[name=industryTree]'),checkenIndustryList);
				}
			});
		}
	},
	editForm:null,//基础增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedEditFormPanel',{
    			'isReadOnly':false//设置是否只读
    		});
    		this.editForm.getForm().findField('pricingEntryCode').setReadOnly(true);
//    		this.editForm.getForm().findField('active').items.items[0].setReadOnly(true);
//    		this.editForm.getForm().findField('active').items.items[1].setReadOnly(true);
    	}
    	return this.editForm;
	},
	downPanel:null,//基础增值服务新增修改查看表单（下半部分）
	//下半部PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.basicValueAdded.DownPanel',{
    			'isReadOnly':false//设置是否只读
    		});
    	}
    	return this.downPanel;
	},
	//返回列别按钮
	returnGrid:function(){
		var me = this;
		me.close();
	},
    //提交数据
    commintBasicValueAdded:function(){
    	var me = this;
    	var editForm = me.getEditForm().getForm();
    	if(editForm.isValid()){//判断增值服务类型，长短途，是否激活，生效日期是否全部填写级及上半部分FORM是否按要求填写
    		//业务发生时间控件
    		var beginBusinessField = me.getEditForm().down('fieldcontainer[name=businessBeginTime]'),
    			endBusinessField = me.getEditForm().down('fieldcontainer[name=businessEndTime]');
    		if(beginBusinessField.getSubmitValue(beginBusinessField)>=endBusinessField.getSubmitValue(endBusinessField)){
    			pricing.showWoringMessage('业务结束时间必须大于业务发生时间');
    			return;
    		}
    		if(editForm.findField('beginTime').getValue().getTime()>editForm.findField('endTime').getValue().getTime()){
				pricing.showWoringMessage('截止时间必须大于生效时间');
				return;
			}
    		if(me.valuationRecord.get('active')=='N'){
    			var priceValuationEntity = new Foss.pricing.basicValueAdded.PriceValuationEntityModel(me.valuationRecord.data)
            	editForm.updateRecord(priceValuationEntity);//将上半部分数据设置到创建计费规则的MODEL中
            	
            	var addPriceCriteriaDetailEntityList = new Array();//新增的计价方式明细
            	var updatePriceCriteriaDetailEntityList = new Array();//修改的计价方式明细
            	var deletePriceCriteriaDetailEntityList = new Array();//删除的计价方式明细
            	var pricingEntryCode = priceValuationEntity.get('pricingEntryCode');//得到计价条目CODE
                var showGrid = null;
                var priceCriteriaDetailEntityFibelPaperPacking = null;
                if(pricingEntryCode==pricing.basicValueAdded.insurance){//保费 BF
					var grid = me.getDownPanel().getInsuranceFormPanel();
					var isNotValid = false;
					if(grid.getStore().getCount()==0){
						pricing.showWoringMessage('请维护基础数据');
						return;
					}
					grid.getStore().each(function(record){
						if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
							pricing.showWoringMessage('有一条基础数据起点大于终点');
							isNotValid = true;
							return ;
						}
						if(record.get('leftrange')>record.get('rightrange')){
							pricing.showWoringMessage('有一条基础数据声明价值起点大于声明价值终点');
							isNotValid = true;
							return ;
						}
						if(record.get('minFeeRate')>record.get('maxFeeRate')){
							pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
							isNotValid = true;
							return ;
						}
						record.set('subType',grid.getSubTypeCombo().getValue());
					});
					if(isNotValid){
						return;
					}
					if(!pricing.checkBusinessRange(grid.getStore().getRange())){
						return;
					}
					showGrid = grid;
					priceValuationEntity.data['subType'] = grid.getSubTypeCombo().getValue();
					/*
			    	var insuranceForm = me.getDownPanel().getInsuranceFormPanel().getForm();
			    	var priceCriteriaDetailEntity= new Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity();//计价方式明细
			    	if(insuranceForm.isValid()){
			    		insuranceForm.updateRecord(priceCriteriaDetailEntity);//设置计价方式明细的值
			    		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
							pricing.showWoringMessage(pricing.basicValueAdded.i18n('foss.pricing.minimumPremiumGreaterThanMaximumCoverageForMinimumInsurance'));//代收货款中有一条数据，最低一票大于最高一票！
							return ;
						}
			    		priceCriteriaDetailEntity.set('caculateType',pricing.basicValueAdded.originalCost);
			    		priceCriteriaDetailEntity.set('valueaddRightrange',pricing.basicValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);
			    		priceCriteriaDetailEntity.set('valueaddLeftrange',0);
			    	}else{
			    		return;
			    	}
			    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);
			    	*/
				}else if(pricingEntryCode==pricing.basicValueAdded.paymentCollection){//代收货款 HK
					var paymentCollectionGrid = me.getDownPanel().getPaymentCollectionGridPanel();
					if(paymentCollectionGrid.getStore().getCount()>0){
						var isNotValid = false;
						paymentCollectionGrid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('leftrange')>record.get('rightrange')){
								pricing.showWoringMessage('有一条基础数据代收金额起点大于代收金额终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFeeRate')>record.get('maxFeeRate')){
								pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
								isNotValid = true;
								return;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkBusinessRange(paymentCollectionGrid.getStore().getRange())){
							return;
						}
						showGrid = paymentCollectionGrid;
					}else{
						pricing.showWoringMessage('请维护基础数据！');
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.superDelivery){//超远派送 CY
					var superDeliveryGrid = me.getDownPanel().getSuperDeliveryGridPanel();
					if(superDeliveryGrid.getStore().getCount()>0){
						var isNotValid = false;
						superDeliveryGrid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('leftrange')>record.get('rightrange')){
								pricing.showWoringMessage('有一条基础数据距离起点大于距离终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkBusinessRange(superDeliveryGrid.getStore().getRange())){
							return;
						}
						showGrid = superDeliveryGrid;
					}else{
						pricing.showWoringMessage('请维护基础数据');
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.deliveryUpstairs){//送货上楼 SHSL
					var deliveryUpstairsGrid = me.getDownPanel().getDeliveryUpstairsGridPanel();
					if(deliveryUpstairsGrid.getStore().getCount()>0){
						var isNotValid = false;
						deliveryUpstairsGrid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFeeRate')>record.get('maxFeeRate')){
								pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
								isNotValid = true;
								return;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkRange(deliveryUpstairsGrid.getStore().getRange())){
							return;
						}
						showGrid = deliveryUpstairsGrid;
					}else{
						pricing.showWoringMessage('请维护基础数据');
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.deliveryCharges){//送货费 SH
					var deliveryChargesGrid = me.getDownPanel().getDeliveryChargesGridPanel();
					if(deliveryChargesGrid.getStore().getCount()>0){
						var isNotValid = false;
						deliveryChargesGrid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFeeRate')>record.get('maxFeeRate')){
								pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
								isNotValid = true;
								return;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkRange(deliveryChargesGrid.getStore().getRange())){
							return;
						}
						showGrid = deliveryChargesGrid;
					}else{
						pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.receivingCharges){//接货费 JH
					var grid = me.getDownPanel().getReceivingChargesFormPanel();
					if(grid.getStore().getCount()>0){
						var isNotValid = false;
						grid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkRange(grid.getStore().getRange())){
							return;
						}
						showGrid = grid;
					}else{
						pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.sign){//签收回单 QS
					var grid = me.getDownPanel().getSignFormPanel();//得到FORM表单
					if(grid.getStore().getCount()>0){
						var isNotValid = false;
						grid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkRange(grid.getStore().getRange())){
							return;
						}
						showGrid = grid;
					}else{
						pricing.showWoringMessage('请维护基础数据');//请维护送货费增值服务基础数据！
						return;
					}
				}else if(pricingEntryCode==pricing.basicValueAdded.storageCharges){//仓储费 CCF
					var grid = me.getDownPanel().getStorageChargesFormPanel();
			    	if(grid.getStore().getCount()==0){
			    		pricing.showWoringMessage('请维护基础数据');
			    		return;
			    	}else{
			    		var isNotValid = false;
			    		grid.getStore().each(function(record){
			    			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
			    				pricing.showWoringMessage('仓储费中有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
			    			}
			    			if(record.get('minFeeRate')>record.get('maxFeeRate')){
			    				pricing.showWoringMessage('仓储费中有一条基础数据最低费率大于最高费率');
								isNotValid = true;
								return ;
			    			}
			    			if(record.get('minFee')>record.get('maxFee')){
			    				pricing.showWoringMessage('仓储费中有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
			    			}
			    		});
			    		if(isNotValid){
			    			return;
			    		}
			    		if(!pricing.checkRange(grid.getStore().getRange())){
			    			return;
			    		}
			    		showGrid = grid;
			    	}
				}else if(pricingEntryCode==pricing.basicValueAdded.otherCharges){//其它费用 QT
					var grid = me.getDownPanel().getOtherChargesGridPanel();
					if(grid.getStore().getCount()>0){
						var isNotValid = false;
						grid.getStore().each(function(record){
			    			if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
			    				pricing.showWoringMessage('其他费用中有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
			    			}
			    			if(record.get('minFee')>record.get('maxFee')){
			    				pricing.showWoringMessage('其他费用中有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
			    			}
			    		});
						if(isNotValid){
							return ;
						}
						if(!pricing.checkRange(grid.getStore().getRange())){
							return;
						}
						showGrid = grid;
					}else{
						pricing.showWoringMessage('请维护基础数据');
						return;
					}
				}else if(pricingEntryCode == pricing.basicValueAdded.packing){
					var grid = me.getDownPanel().getPackingFormPanel();
					var isNotValid = false;
					if(grid.getStore().getCount()==0){
			    		pricing.showWoringMessage('请维护基础数据');
			    		return;
			    	}
					grid.getStore().each(function(record){
						if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
							pricing.showWoringMessage('包装费中有一条基础数据起点大于终点');
							isNotValid = true;
							return ;
						}
						if(record.get('minFeeRate')>record.get('maxFeeRate')){
							pricing.showWoringMessage('包装费中有一条基础数据最低费率大于最高费率');
							isNotValid = true;
							return ;
						}
						if(record.get('minFee')>record.get('maxFee')){
							pricing.showWoringMessage('包装费中有一条基础数据最低一票大于最高一票');
							isNotValid = true;
							return ;
						}
					});
					if(isNotValid){
						return ;
					}
					if(!pricing.checkRange(grid.getStore().getRange())){
						return;
					}
					showGrid = grid;
				}
				/**
                 * 将修改后的纸纤包装的费用加入到数据库中
                 * @author:218371-foss-zhaoyanjun
                 * @date:2014-11-24下午14:21
                 */
                else if(pricingEntryCode==pricing.basicValueAdded.paperFiberPacking){//纸纤包装费用修改
            		var priceFibelPaperPackingEntity=new Foss.pricing.basicValueAdded.PriceFibelPaperPackingEntity();
            		var paperFiberPackingForm = me.getDownPanel().getPaperFiberPackingFormPanel().getForm();//获得form页面元素
            		if(paperFiberPackingForm.isValid()){
            			var elements = paperFiberPackingForm.getFields( ).items;//获得FORM页面元素，一个原件返单，一个传真返单
            			priceFibelPaperPackingEntity.set('paperBoxOne',elements[0].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagOneBlue',elements[1].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagOneWhite',elements[2].getValue());
            			priceFibelPaperPackingEntity.set('paperBoxTwo',elements[3].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagTwoBlue',elements[4].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagTwoWhite',elements[5].getValue());
            			priceFibelPaperPackingEntity.set('paperBoxThree',elements[6].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagThreeBlue',elements[7].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagThreeWhite',elements[8].getValue());
            			priceFibelPaperPackingEntity.set('paperBoxFour',elements[9].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagFourBlue',elements[10].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagFourWhite',elements[11].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagFiveWhite',elements[12].getValue());
            			priceFibelPaperPackingEntity.set('fibelBagSixWhite',elements[13].getValue());
            			priceFibelPaperPackingEntity.set('kind',pricing.basicValueAdded.priceFibelPaperPackingEntity_Basic);
            			for(var i= 0;i<me.priceCriteriaDetailEntityList.length;i++){
            				priceCriteriaDetailEntityFibelPaperPacking = new Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
            				priceCriteriaDetailEntityFibelPaperPacking.set('priceFibelPaperPackingEntity',priceFibelPaperPackingEntity.data);
                		}
            		}else{
            			return;
            		}
                }
                /**
            	 * 大件上楼费数据组装
            	 * @author:218371-foss-zhaoyanjun
            	 * @date:2014-12-11上午10:50
            	 */
            	else if(pricingEntryCode==pricing.basicValueAdded.largeDeliveryUpstairs){
            		var largeDeliveryUpstairsGrid = me.getDownPanel().getLargeDeliveryUpstairsGridPanel();
            		if(largeDeliveryUpstairsGrid.getStore().getCount()>0){
						var isNotValid = false;
						largeDeliveryUpstairsGrid.getStore().each(function(record){
							if(record.get('valueaddLeftrange')>record.get('valueaddRightrange')){
								pricing.showWoringMessage('有一条基础数据起点大于终点');
								isNotValid = true;
								return ;
							}
							if(record.get('minFeeRate')>record.get('maxFeeRate')){
								pricing.showWoringMessage('有一条基础数据最低费率大于最高费率');
								isNotValid = true;
								return;
							}
							if(record.get('minFee')>record.get('maxFee')){
								pricing.showWoringMessage('有一条基础数据最低一票大于最高一票');
								isNotValid = true;
								return ;
							}
						});
						if(isNotValid){
							return;
						}
						if(!pricing.checkRange(largeDeliveryUpstairsGrid.getStore().getRange())){
							return;
						}
						showGrid = largeDeliveryUpstairsGrid;
					}else{
						pricing.showWoringMessage('请维护基础数据');
						return;
					}
            	}
//                var priceCriteriaDetailEntityFibelPaperPacking = null;
            	if(pricingEntryCode==pricing.basicValueAdded.paperFiberPacking){
            		updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntityFibelPaperPacking.data);
            	}else{
            		var updatePriceCriteriaDetailEntityModel = showGrid.getStore().getUpdatedRecords( );//修改了的数据
	    			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
	    				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
	    					updatePriceCriteriaDetailEntityModel[i].set('feeRate',updatePriceCriteriaDetailEntityModel[i].get('feeRate'));
	    					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
	    				}
	    				
	    			}
	    			var addPriceCriteriaDetailEntityModel = showGrid.getStore().getNewRecords( );//新增了的数据
	    			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
	    				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
	    					addPriceCriteriaDetailEntityModel[i].set('feeRate',addPriceCriteriaDetailEntityModel[i].get('feeRate'));
	    					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
	    				}
	    			}
	    			// DEFECT-1021 删除操作不起作用 by lufeifei start
	    			var deletePriceCriteriaDetailEntityModel = showGrid.getStore().getRemovedRecords();//删除的数据
	    			if(!Ext.isEmpty(deletePriceCriteriaDetailEntityModel)){//如果不为空
	    				for(var i =0;i<deletePriceCriteriaDetailEntityModel.length;i++){
	    					deletePriceCriteriaDetailEntityList.push(deletePriceCriteriaDetailEntityModel[i].data);
	    				}
	    			}
            	}
				var arr1=[],arr2=[];
				arr1 = arr1 = pricing.getIndustryData(me.down('treepanel[name=industryTree]'));
                var params = {'pricingValuationVo':{'priceCriteriaDetailEntityList':addPriceCriteriaDetailEntityList
                	,'priceValuationEntity':priceValuationEntity.data,'updatePriceCriteriaDetailEntityList':updatePriceCriteriaDetailEntityList
                	,'deletePriceCriteriaDetailEntityList':deletePriceCriteriaDetailEntityList}};//组装数据
                var businessBeginTimeField = me.down('fieldcontainer[name=businessBeginTime]'),
					businessEndTimeField = me.down('fieldcontainer[name=businessEndTime]');
				params.pricingValuationVo.priceValuationEntity['businessBeginTime'] = businessBeginTimeField.getSubmitValue(businessBeginTimeField);
				params.pricingValuationVo.priceValuationEntity['businessEndTime'] = businessEndTimeField.getSubmitValue(businessEndTimeField);
				params.pricingValuationVo.priceValuationEntity['customerIndustryEntityList'] = arr1;
				params.pricingValuationVo.priceValuationEntity['productList'] = arr2;
                var successFun = function(json){
                	pricing.showInfoMes(json.message);//提示信息
                	var grid  = Ext.getCmp('T_pricing-indexValuation_content').getPricingValuationGrid();
    				grid.getPagingToolbar().moveFirst();//重新查询计费规则
                	me.hide();
                };//成功之后提示成功，隐藏现在界面
                var failureFun = function(json){
                    if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
                        Ext.getCmp('pricing_basicValueAdded_updateCommitButton').enable();
                    }
                	if(Ext.isEmpty(json)){
    					pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));//提示请求超时
    				}else{
    					pricing.showErrorMes(json.message);
    				}
                };
                var url = pricing.realPath('updateValueAdded.action');//修改基础增值服务
                  //修改Window提交按钮
                if (Ext.getCmp('pricing_basicValueAdded_updateCommitButton') != undefined) {
                	Ext.getCmp('pricing_basicValueAdded_updateCommitButton').disable();
                }
                pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    		}else if(me.valuationRecord.get('active')=='Y'){
    			pricing.addValueAdded(editForm,me);
    		}
    		
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},'->',{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			id:'pricing_basicValueAdded_updateCommitButton',
			handler :function(){
				me.commintBasicValueAdded();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 基础增值服务-新增
 */
Ext.define('Foss.pricing.basicValueAdded.BasicValueAddedWindow',{
	extend : 'Ext.window.Window',
	title : pricing.basicValueAdded.i18n('foss.pricing.newBasisForValueAddedServices'),//新增基础增值服务
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :700,
	height :550,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getEditForm().getForm().reset();
			me.getDownPanel().getInsuranceFormPanel().getStore().removeAll();
			//me.getDownPanel().getInsuranceFormPanel().getForm().reset();
			me.getDownPanel().getInsuranceFormPanel().getSubTypeCombo().reset();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getPackingFormPanel().getStore().removeAll();//包装费
			me.getDownPanel().getSignFormPanel().getStore().removeAll();
			me.getDownPanel().getStorageChargesFormPanel().getStore().removeAll();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getPaperFiberPackingFormPanel().getForm().reset();
			/**
			 * 重置大件上楼费的内容
			 * @author:218371-foss-zhaoyanjun
			 * @date:2014-12-12上午14:02
			 */
			me.getDownPanel().getLargeDeliveryUpstairsGridPanel().getStore().removeAll();
			me.height = 550;
		},
		beforeshow:function(me){
			
		},
		show:function(win){
			pricing.showIndustryTreeData(win.down('treepanel'));
		}
	},
	editForm:null,//基础增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.basicValueAdded.BasicValueAddedEditFormPanel',{
    			'isReadOnly':false
    		});
    	}
    	return this.editForm;
	},
	downPanel:null,//基础增值服务新增修改查看表单（下半部分）
	//下颁布PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.basicValueAdded.DownPanel',{
    			'isReadOnly':false
    		});
    	}
    	return this.downPanel;
	},
    //提交数据
    commintBasicValueAdded:function(){
    	var me = this;
    	var editForm = me.getEditForm().getForm();
    	if(editForm.isValid()){//判断增值服务类型，长短途，是否激活，生效日期是否全部填写级及上半部分FORM是否按要求填写
    		//业务发生时间控件
    		var beginBusinessField = me.getEditForm().down('fieldcontainer[name=businessBeginTime]'),
    			endBusinessField = me.getEditForm().down('fieldcontainer[name=businessEndTime]');
    		if(beginBusinessField.getSubmitValue(beginBusinessField)>=endBusinessField.getSubmitValue(endBusinessField)){
    			pricing.showWoringMessage('业务结束时间必须大于业务发生时间');
    			return;
    		}
    		if(editForm.findField('beginTime').getValue().getTime()>editForm.findField('endTime').getValue().getTime()){
				pricing.showWoringMessage('截止时间必须大于生效时间');
				return;
			}
    		pricing.addValueAdded(editForm,me);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},'->',{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			id:'pricing_basicValueAdded_commitButton',
			handler :function(){
				//this.disable();
				me.commintBasicValueAdded();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单（上部）
 */
Ext.define('Foss.pricing.basicValueAdded.BasicValueAddedEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : 1,
    	margin : '8 10 5 10',
    	allowBlank:false,
		labelSeparator:'',
		labelWidth:120,
		width:300,
    	anchor : '100%' 
    },
    //根据MANA-1320修改
    height :450,
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns:2
	},
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype : 'textfield',
			fieldLabel: pricing.basicValueAdded.i18n('foss.pricing.theBasisOfValueAddedServicesName'),//基础增值服务名称
			 allowBlank:false,
			 maxLength:15,
			readOnly:isReadOnly,
			name:'name'
		},{
			xtype : 'textfield',
			readOnly:true,
			allowBlank:true,
			beforeLabelTextTpl: '',
			emptyText:pricing.basicValueAdded.i18n('foss.pricing.automaticallyGeneratedCoding'),//自动生成编码
			fieldLabel: '<span style="color:red">*</span>'+pricing.basicValueAdded.i18n('foss.pricing.theBasisOfValueAddedServicesCoding'),//基础增值服务编码
			maxLength:25,
			name:'code'
		},{
			xtype:'datefield',
			minValue:isReadOnly?null:new Date(pricing.basicValueAdded.tomorrowTime),
			value:new Date(pricing.basicValueAdded.tomorrowTime),
			fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.availabilityDate'),
			format:'Y-m-d',
			readOnly:isReadOnly,
			name:'beginTime',
			editable:false
		},{ //根据MANA-1320修改
			xtype:'datefield',
			minValue:isReadOnly?null:new Date(pricing.basicValueAdded.tomorrowTime),
			value:new Date(pricing.basicValueAdded.defaultEndDate),
			fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.endTimeTwo'),
			format:'Y-m-d',
			readOnly:isReadOnly,
			name:'endTime',			
			editable:false
		},{
			name: 'pricingEntryCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    readOnly:isReadOnly,
		    allowBlank:false,
		    store:pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceEntityModel',null
		    ,pricing.basicValueAdded.valueAddedType),
	        fieldLabel: pricing.basicValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//所属增值服务
	        pricingEntryId:null,//服务ID
	        xtype : 'combo',
	        listeners:{
	        	change:function(com,newValue,oldValue){
	        		//根据所选择的增值服务动态改变window高度
	        		me.up('window').setHeight(780);
	        		for(var i=0;i<pricing.basicValueAdded.valueAddedType.length;i++){//设置服务ID
	        			if(pricing.basicValueAdded.valueAddedType[i].code==newValue){
	        				com.pricingEntryId = pricing.basicValueAdded.valueAddedType[i].id;
	        			}
	        		}
	        		/*for(var i=0;i<pricing.basicValueAdded.limitedWarrantyItems.length;i++){//设置服务ID
	        			if(pricing.basicValueAdded.limitedWarrantyItems[i].code==newValue){
	        				com.id = pricing.basicValueAdded.limitedWarrantyItems[i].id;
	        			}
	        		}*/
	        		
	        		if(oldValue==pricing.basicValueAdded.insurance){//保费
	        			com.up('form').up('window').getDownPanel().getInsuranceFormPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.receivingCharges){//收货费
	        			com.up('form').up('window').getDownPanel().getReceivingChargesFormPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.sign){
	        			com.up('form').up('window').getDownPanel().getSignFormPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.storageCharges){
	        			com.up('form').up('window').getDownPanel().getStorageChargesFormPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.otherCharges){
	        			com.up('form').up('window').getDownPanel().getOtherChargesGridPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.deliveryCharges){
	        			com.up('form').up('window').getDownPanel().getDeliveryChargesGridPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.paymentCollection){
	        			com.up('form').up('window').getDownPanel().getPaymentCollectionGridPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.superDelivery){//超远派送
	        			com.up('form').up('window').getDownPanel().getSuperDeliveryGridPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.deliveryUpstairs){//送货上楼
	        			com.up('form').up('window').getDownPanel().getDeliveryUpstairsGridPanel().hide();
	        		}else if(oldValue==pricing.basicValueAdded.packing){//包装
	        			com.up('form').up('window').getDownPanel().getPackingFormPanel().hide();
	        		}
	        		/**
	        		 * 若改变的是纸纤包装
	        		 * @author:218371-foss-zhaoyanjun
	        		 * @date:2014-11-11下午16:34
	        		 */
	        		else if(oldValue==pricing.basicValueAdded.paperFiberPacking){
	        			com.up('form').up('window').getDownPanel().getPaperFiberPackingFormPanel().hide();
	        		}
	        		/**
	        		 * 若改变的是大件上楼费
	        		 * @author:218371-foss-zhaoyanjun
	        		 * @date:2014-12-11下午14:00
	        		 */
	        		else if(oldValue==pricing.basicValueAdded.largeDeliveryUpstairs){
	        			com.up('form').up('window').getDownPanel().getLargeDeliveryUpstairsGridPanel().hide();
	        		}
	        		if(newValue==pricing.basicValueAdded.insurance){
	        			com.up('form').up('window').getDownPanel().getInsuranceFormPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.receivingCharges){
	        			com.up('form').up('window').getDownPanel().getReceivingChargesFormPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.sign){
	        			com.up('form').up('window').getDownPanel().getSignFormPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.storageCharges){
	        			com.up('form').up('window').getDownPanel().getStorageChargesFormPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.otherCharges){
	        			com.up('form').up('window').getDownPanel().getOtherChargesGridPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.deliveryCharges){
	        			com.up('form').up('window').getDownPanel().getDeliveryChargesGridPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.paymentCollection){
	        			com.up('form').up('window').getDownPanel().getPaymentCollectionGridPanel().show()
	        		}else if(newValue==pricing.basicValueAdded.superDelivery){//超远派送
	        			com.up('form').up('window').getDownPanel().getSuperDeliveryGridPanel().show()
	        		}else if(newValue==pricing.basicValueAdded.deliveryUpstairs){//送货上楼
	        			com.up('form').up('window').getDownPanel().getDeliveryUpstairsGridPanel().show();
	        		}else if(newValue==pricing.basicValueAdded.packing){
	        			com.up('form').up('window').getDownPanel().getPackingFormPanel().show();
	        		}
	        		/**
	        		 * 若改变的是纸纤包装
	        		 * @author:218371-foss-zhaoyanjun
	        		 * @date:2014-11-11下午16:34
	        		 */
	        		else if(newValue==pricing.basicValueAdded.paperFiberPacking){
	        			com.up('form').up('window').getDownPanel().getPaperFiberPackingFormPanel().show();
	        		}
	        		/**
	        		 * 若改变的是大件上楼费
	        		 * @author:218371-foss-zhaoyanjun
	        		 * @date:2014-11-11下午16:34
	        		 */
	        		else if(newValue==pricing.basicValueAdded.largeDeliveryUpstairs){
	        			com.up('form').up('window').getDownPanel().getLargeDeliveryUpstairsGridPanel().show();
	        		}
	        	}
	        }
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'active',
			 fieldLabel:pricing.basicValueAdded.i18n('i18n.pricingRegion.isActive'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.basicValueAdded.i18n('i18n.pricingRegion.ye'),
			     name:'active',
			     readOnly:isReadOnly,
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.basicValueAdded.i18n('i18n.pricingRegion.no'),
			     name:'active',
			     readOnly:isReadOnly,
			     inputValue:'N'
			     }]
		},{
			xtype:'fieldcontainer',
			fieldLabel:'业务发生时间',
			name:'businessBeginTime',
			layout:'hbox',
			getSubmitValue:function(field){
				var h = field.down('[name=h]').getValue(),
					m = field.down('[name=m]').getValue(),
					s = field.down('[name=s]').getValue();
					return ''+(h<10?('0'+h):h)+(m<10?('0'+m):m)+(s<10?('0'+s):s);
			},
			setSubmitValue:function(field,val){
				if(Ext.isEmpty(val)){
					return;
				}
				field.down('[name=h]').setValue(val.substring(0,2));
				m = field.down('[name=m]').setValue(val.substring(2,4));
				s = field.down('[name=s]').setValue(val.substring(4,6));
			},
			items:[{
				xtype:'numberfield',
				allowDecimals:false,
				readOnly:isReadOnly,
				allowBlank:false,
				minValue:0,
				name:'h',
				maxValue:23,
				value:0,
				width:45,
				msgTarget:'quit',
				labelWidth:0
			},{
				xtype:'numberfield',
				name:'m',
				readOnly:isReadOnly,
				allowDecimals:false,
				allowBlank:false,
				minValue:0,
				maxValue:59,
				value:0,
				fieldLabel:':',
				msgTarget:'quit',
				labelSeparator:'',
				width:65,
				labelWidth:15
			},{
				allowDecimals:false,
				allowBlank:false,
				readOnly:isReadOnly,
				name:'s',
				xtype:'numberfield',
				minValue:0,
				maxValue:59,
				msgTarget:'quit',
				fieldLabel:':',
				labelSeparator:'',
				value:0,
				width:65,
				labelWidth:15
			}]
		},{
			xtype:'fieldcontainer',
			fieldLabel:'到',
			layout:'hbox',
			name:'businessEndTime',
			getSubmitValue:function(field){
				var h = field.down('[name=h]').getValue(),
					m = field.down('[name=m]').getValue(),
					s = field.down('[name=s]').getValue();
					return ''+(h<10?('0'+h):h)+(m<10?('0'+m):m)+(s<10?('0'+s):s);
			},
			setSubmitValue:function(field,val){
				if(Ext.isEmpty(val)){
					return;
				}
				field.down('[name=h]').setValue(val.substring(0,2));
				m = field.down('[name=m]').setValue(val.substring(2,4));
				s = field.down('[name=s]').setValue(val.substring(4,6));
			},
			items:[{
				xtype:'numberfield',
				name:'h',
				allowDecimals:false,
				readOnly:isReadOnly,
				allowBlank:false,
				minValue:0,
				maxValue:23,
				value:23,
				width:45,
				msgTarget:'quit',
				labelWidth:0
			},{
				xtype:'numberfield',
				name:'m',
				allowDecimals:false,
				readOnly:isReadOnly,
				allowBlank:false,
				minValue:0,
				maxValue:59,
				value:59,
				fieldLabel:':',
				msgTarget:'quit',
				labelSeparator:'',
				width:65,
				labelWidth:15
			},{
				allowDecimals:false,
				allowBlank:false,
				readOnly:isReadOnly,
				xtype:'numberfield',
				name:'s',
				minValue:0,
				maxValue:59,
				msgTarget:'quit',
				fieldLabel:':',
				labelSeparator:'',
				value:59,
				width:65,
				labelWidth:15
			}]
		},{
			xtype:'tabpanel',
			colspan:2,
			width:620,
			height:260,
			items:[{
				xtype:'foss_pricing_industrytree',
				name:'industryTree'
			}]
		}];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.pricing.basicValueAdded.DownPanel', {
	extend:'Ext.panel.Panel',
    flex: 1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //保费的填写数据formPANEL
    insuranceFormPanel:null,
    getInsuranceFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.insuranceFormPanel)){
    		this.insuranceFormPanel = Ext.create('Foss.pricing.basicValueAdded.InsuranceFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.insuranceFormPanel;
    },
    //收货费
    receivingChargesFormPanel:null,
    getReceivingChargesFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.receivingChargesFormPanel)){
    		this.receivingChargesFormPanel = Ext.create('Foss.pricing.basicValueAdded.ReceivingChargesFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.receivingChargesFormPanel;
    },
    //包装费
    packingFormPanel:null,
    getPackingFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.packingFormPanel)){
    		this.packingFormPanel = Ext.create('Foss.pricing.basicValueAdded.PackingFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.packingFormPanel;
    },
    //签收回单PANEL
    signFormPanel:null,
    getSignFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.signFormPanel)){
    		this.signFormPanel = Ext.create('Foss.pricing.basicValueAdded.SignFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.signFormPanel;
    },
    //仓储费PANEL
    storageChargesFormPanel:null,
    getStorageChargesFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.storageChargesFormPanel)){
    		this.storageChargesFormPanel = Ext.create('Foss.pricing.basicValueAdded.StorageChargesFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.storageChargesFormPanel;
    },
    //其他费PANEL
    otherChargesGridPanel:null,
    getOtherChargesGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.otherChargesGridPanel)){
    		this.otherChargesGridPanel = Ext.create('Foss.pricing.basicValueAdded.OtherChargesGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.otherChargesGridPanel;
    },
    //送货费
    deliveryChargesGridPanel:null,
    getDeliveryChargesGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.deliveryChargesGridPanel)){
    		this.deliveryChargesGridPanel = Ext.create('Foss.pricing.basicValueAdded.DeliveryChargesGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.deliveryChargesGridPanel;
    },
    //代收货款
    paymentCollectionGridPanel:null,
    getPaymentCollectionGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.paymentCollectionGridPanel)){
    		this.paymentCollectionGridPanel = Ext.create('Foss.pricing.basicValueAdded.PaymentCollectionGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.paymentCollectionGridPanel;
    },
    //超远派送
    superDeliveryGridPanel:null,
    getSuperDeliveryGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.superDeliveryGridPanel)){
    		this.superDeliveryGridPanel = Ext.create('Foss.pricing.basicValueAdded.SuperDeliveryGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.superDeliveryGridPanel;
    },
    //送货上楼费
    deliveryUpstairsGridPanel:null,
    getDeliveryUpstairsGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.deliveryUpstairsGridPanel)){
    		this.deliveryUpstairsGridPanel = Ext.create('Foss.pricing.basicValueAdded.DeliveryUpstairsGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.deliveryUpstairsGridPanel;
    },
    /**
     * 纸纤包装
     * @author:218371-foss-zhaoyanjun
     * @date:2014-11-11上午08:38
     */
    paperFiberPackingFormPanel:null,
    getPaperFiberPackingFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.paperFiberPackingFormPanel)){
    		this.paperFiberPackingFormPanel = Ext.create('Foss.pricing.basicValueAdded.paperFiberPackingFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.paperFiberPackingFormPanel;
    },
    /**
     * 大件上楼费
     * @author:218371-foss-zhaoyanjun
     * @date:2014-12-11上午09:36
     */
    largeDeliveryUpstairsGridPanel:null,
    getLargeDeliveryUpstairsGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.largeDeliveryUpstairsGridPanel)){
    		this.largeDeliveryUpstairsGridPanel = Ext.create('Foss.pricing.basicValueAdded.largeDeliveryUpstairsGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.largeDeliveryUpstairsGridPanel;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [me.getInsuranceFormPanel(isReadOnly),me.getReceivingChargesFormPanel(isReadOnly)
		            ,me.getSignFormPanel(isReadOnly),me.getStorageChargesFormPanel(isReadOnly)
		            ,me.getOtherChargesGridPanel(isReadOnly),me.getDeliveryChargesGridPanel(isReadOnly)
		            ,me.getPaymentCollectionGridPanel(isReadOnly),me.getPackingFormPanel(isReadOnly)
		            ,me.getSuperDeliveryGridPanel(isReadOnly),me.getDeliveryUpstairsGridPanel(isReadOnly)
		            /**
		             * 启动PaperFiberPackingFromPanel面板
		             * @author:218371-foss-zhaoyanjun
		             * @date:2014-11-12上午11:10
		             */
		            ,me.getPaperFiberPackingFormPanel(isReadOnly)
		            /**
					 * 启动largeDeliveryUpstairsGridPanel面板
					 * @author:218371-foss-zhaoyanjun
					 * @date:2014-12-11上午09:39
					 */
		            ,me.getLargeDeliveryUpstairsGridPanel(isReadOnly)];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单保费（下部）
 */
/*
Ext.define('Foss.pricing.basicValueAdded.InsuranceFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
	hidden:true,
    defaults : {
    	labelWidth:120,
    	columnWidth : 1,
    	labelSeparator:'',
    	margin : '5 5 5 5',
    	anchor : '100%'
    },
    flex:1,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			name:'subType',
			queryMode: 'local',
    	    displayField: 'goodsName',
    	    valueField: 'virtualCode',
    	    editable:false,
    	    readOnly:isReadOnly,
    	    fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.limitedWarrantyItems'),//限保物品
    	    store:pricing.getStore(null,null,['goodsName','virtualCode']
    	    ,pricing.basicValueAdded.limitedWarrantyItems),
            xtype : 'combo'
		},{
			xtype:'numberfield',
	        decimalPrecision:4,
	        name:'feeRate',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.rates'),
	        step:0.0001,
	        maxValue: 1,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'minFee',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.minimumPremiumFee'),
	        maxValue: 9999999.99,
	        minValue: 0 
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'maxFee',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.maximumCoverageForMinimumInsurance'),
	        maxValue: 9999999.99,
	        minValue: 0
		}];
		me.callParent([cfg]);
	}
});*/
/**
 * 增值服务表单保费（下部）可编辑表格pricing.basicValueAdded.Insurance = 'BF';//保费Panel
 */
Ext.define('Foss.pricing.basicValueAdded.InsuranceFormPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
			'caculateType': pricing.basicValueAdded.originalCost,
			'valueaddCaculateType': valueaddCaculateType,
//			'feeRate':pricing.basicValueAdded.RATE,
        	'valueaddLeftrange': 0,
        	'valueaddRightrange': 9999999,
			'leftrange':0,
			'rightrange':9999999,
        	'minFeeRate':0.001,
        	'maxFeeRate':0.006,
        	'feeRate':0.006,
        	'canmodify':'N',
        	'minFee':0
        	
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    subTypeCombo: null,
    getSubTypeCombo: function(){
    	if(Ext.isEmpty(this.subTypeCombo)){
    		this.subTypeCombo = Ext.widget({
				name:'subType',
				queryMode: 'local',
	    	    displayField: 'goodsName',
	    	    valueField: 'virtualCode',
	    	    editable:false,
	    	    fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.limitedWarrantyItems'),//限保物品
	    	    store:pricing.getStore(null,null,['goodsName','virtualCode'],
	    	    pricing.basicValueAdded.limitedWarrantyItems),
	            xtype : 'combo',
	            plugins: {
			        ptype: 'clearvalueplugin'
			    }
			})
    	}
    	return this.subTypeCombo;
    },
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    				//设置新增按按钮
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text:'按体积新增',//按体积新增
    		    	handler:function(){
    		    		me.addOneModel(pricing.basicValueAdded.Volume)
    		    	}
    		    }, '-' ,me.getSubTypeCombo()];
    		} else {
    			this.tbar = [me.getSubTypeCombo()];
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
		text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items: [{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (pricing.basicValueAdded.viewOrUpdateOrDelete == 'delete') {
					    return 'statementBill_hide';
					} else {
					    return 'deppon_icons_delete';//隐藏图标
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteThisInsuranceCharge'),function(e){//是否要删除这条送货费信息？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				grid.getStore().remove(record);
            			}
            		});
                }
			}]
	  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '声明价值起点',//声明价值起点
	        dataIndex: 'leftrange',
	        renderer:function(value){
	        	return value+pricing.basicValueAdded.YUAN;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    }, {
	        header: '声明价值终点',//声明价值终点
	        dataIndex: 'rightrange',
	        renderer:function(value){
	        	return value+pricing.basicValueAdded.YUAN;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    }, {
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        //align: 'right',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
		        step:0.001,
	            decimalPrecision:4,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        //align: 'right',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
		        step:0.001,
	            decimalPrecision:4,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
		        step:0.001,
	            decimalPrecision:4,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '最低保险费',//最低保险费
	        dataIndex: 'minFee',
	        width: 80,
	        //align: 'right',
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUAN;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    }, {
	        header: '费率是否可修改',//费率是否可修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return '否';//否
	        	}else if(value=='Y'){
	        		return '是';
	        	}else{
	        		return '';
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':'否'},{'key':'Y','value':'是'}]),
	            xtype : 'combo'
	        }
	    }]
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单接货费（下部）JH
 */
Ext.define('Foss.pricing.basicValueAdded.ReceivingChargesFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
    defaults : {
    	colspan: 1,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 2
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:2,
	        CACULATE_TYPE:pricing.basicValueAdded.Weight,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.byWeight'),//按重量
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanKG'),//元/KG
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        readOnly:isReadOnly,
	        CACULATE_TYPE:pricing.basicValueAdded.Volume,
	        name:'feeRate',
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.byVolume'),//按体积
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuamm'),//元/m³
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'minFeeRate',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.minimumCharge'),//最低收费
	        maxValue: 9999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'maxFeeRate',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.maximumCharge'),//最高收费
	        maxValue: 9999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单接货费（下部）JH
 */
Ext.define('Foss.pricing.basicValueAdded.ReceivingChargesFormPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增一条其他费用默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType': valueaddCaculateType,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.FEE,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFee':0,
        	'maxFee':9999999,
        	'feeRate':0,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text:'按体积新增',//按体积新增
    		    	handler:function(){
    		    		me.addOneModel(pricing.basicValueAdded.Volume);
    		    	}
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
						width:42,
						getClass : function (v, m, r, rowIndex) {
							if (!isReadOnly) {
								return 'deppon_icons_delete';
							} else {
								return 'statementBill_hide';
							}
						},
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes('是否要删除这条接货费信息？',function(e){//是否要删除这条接货费信息？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },  {
		        header: '起点',//起点
		        dataIndex: 'valueaddLeftrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    }, {
		        header: '终点',//终点
		        dataIndex: 'valueaddRightrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    },{
		        header: '最低一票',//最低一票
		        dataIndex: 'minFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最高一票',//最高一票
		        dataIndex: 'maxFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    }, {
		        header: '默认收费金额',//默认收费金额
		        dataIndex: 'feeRate',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: pricing.basicValueAdded.i18n('foss.pricing.whetherItCanBeModified'),//是否可修改
		        dataIndex: 'canmodify',
		        width: 95,
		        renderer: function(value){
		        	if(value=='N'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.no');
		        	}else if(value=='Y'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.ye');
		        	}else{
		        		return '';
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'value',
		    	    valueField: 'key',
		    	    editable:false,
		    	    store:pricing.getStore(null,null,['key','value']
		    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.ye')}]),
		            xtype : 'combo'
		        }
		    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单包装费（下部）BZ
 */
Ext.define('Foss.pricing.basicValueAdded.PackingFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
    defaults : {
    	colspan: 1,
    	margin : '3 3 3 3',
    	labelSeparator:'',
    	labelWidth:150,
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 1
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.woodenFrameRates'),//木架费率
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.lowestTicketYuan'),//最低一票（元）
	        name:'minFeeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.crateRates'),//木箱费率
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.lowestTicketYuan'),//最低一票（元）
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
		},{
	        xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.woodenSalverRates'),//木托（元/个）
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
	  },{
	    xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.lowestSingleYuan'),//最低一个（元）
	        name:'minFeeRate',
	        readOnly:isReadOnly,
	        maxValue: 9999999.99,
	        minValue: 0
	  }];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单包装费（下部）BZ
 */
Ext.define('Foss.pricing.basicValueAdded.PackingFormPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增超远派送一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType': pricing.basicValueAdded.Volume,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
        	'subType':FossDataDictionary.getDataByTermsCode(pricing.basicValueAdded.VALUEADDED_PACKAGE_TYPE)[0].valueCode,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFeeRate':1,
        	'maxFeeRate':1,
        	'maxFee':1,
        	'minFee':1,
        	'canmodify':'N',
        	'feeRate':1
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		        text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume)
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
						    return 'deppon_icons_delete';
						} else {
						    return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes('是否要删除这条包装费信息？',function(e){//是否要删除这条包装费信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
		  	header:'包装类型',
		  	dataIndex: 'subType',
		  	width:70,
		  	renderer:function(val){
		  		return FossDataDictionary.rendererSubmitToDisplay(val,pricing.basicValueAdded.VALUEADDED_PACKAGE_TYPE);
		  	},
		  	editor: {
	            xtype: 'combo',
	            queryMode: 'local',
	    	    displayField: 'valueName',
	    	    valueField: 'valueCode',
	    	    editable:false,
	    	    allowBlank:false,
	            store:FossDataDictionary.getDataDictionaryStore(pricing.basicValueAdded.VALUEADDED_PACKAGE_TYPE)
	        }
		  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        renderer:function(value,metaData,record){
	        	if(record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__FRAME
	        		||record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__BOX){
	        		if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        			return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
	        	}
	        	if(record.get('subType')==pricing.basicValueAdded.PACKAGE_TYPE_SALVER){
	        		return value+pricing.basicValueAdded.YUANBYGE;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        renderer:function(value,metaData,record){
	        	if(record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__FRAME
	        		||record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__BOX){
	        		if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        			return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
	        	}
	        	if(record.get('subType')==pricing.basicValueAdded.PACKAGE_TYPE_SALVER){
	        		return value+pricing.basicValueAdded.YUANBYGE;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        renderer:function(value,metaData,record){
	        	if(record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__FRAME
	        		||record.get('subType') ==pricing.basicValueAdded.PACKAGE_TYPE__BOX){
	        		if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        			return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUME;
		        	}
	        	}
	        	if(record.get('subType')==pricing.basicValueAdded.PACKAGE_TYPE_SALVER){
	        		return value+pricing.basicValueAdded.YUANBYGE;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('subType')==pricing.basicValueAdded.PACKAGE_TYPE_SALVER){
	        		return val+pricing.basicValueAdded.YUANBYGE;
	        	}
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最高一票',//最高一票
	        dataIndex: 'maxFee',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('subType')==pricing.basicValueAdded.PACKAGE_TYPE_SALVER){
	        		return val+pricing.basicValueAdded.YUANBYGE;
	        	}
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: pricing.basicValueAdded.i18n('foss.pricing.ratesIsUpdate'),//费率是否可以修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return '';
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }
	    ];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单签收回单（下部）QS
 */
Ext.define('Foss.pricing.basicValueAdded.SignFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
    defaults : {
    	colspan: 1,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 2
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        readOnly:isReadOnly,
	        subType:pricing.basicValueAdded.YJFD,//标示其为“原件返单”
	        name:'fee',
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.originalBackToSingle'),//原件返单
	        maxValue: 9999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        readOnly:isReadOnly,
	        name:'fee',
	        subType:pricing.basicValueAdded.CZFD,//标示其为“传真返单”
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.faxBackToASingle'),//传真返单
	        maxValue: 9999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单签收回单（下部）QS
 */
Ext.define('Foss.pricing.basicValueAdded.SignFormPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增一条其他费用默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.originalCost,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.FEE,
        	'subType':FossDataDictionary.getDataByTermsCode(pricing.basicValueAdded.VALUEADDED_RETURN_TYPE)[0].valueCode,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFee':0,
        	'maxFee':0,
        	'fee':0,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text:'按体积新增',//按体积新增
    		    	handler:function(){
    		    		me.addOneModel(pricing.basicValueAdded.Volume); 
    		    	}
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
						width:42,
						getClass : function (v, m, r, rowIndex) {
							if (!isReadOnly) {
								return 'deppon_icons_delete';
							} else {
								return 'statementBill_hide';
							}
						},
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes('是否要删除这条签收回单信息？',function(e){//是否要删除这条签收回单信息？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },{
		        header: '返单类型',//返单类型
		        dataIndex: 'subType',
		        renderer:function(val){
		        	return FossDataDictionary.rendererSubmitToDisplay(val,pricing.basicValueAdded.VALUEADDED_RETURN_TYPE);
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'valueName',
	    	    	valueField: 'valueCode',
		    	    editable:false,
		    	    allowBlank: false,
		    	    store:FossDataDictionary.getDataDictionaryStore(pricing.basicValueAdded.VALUEADDED_RETURN_TYPE),
		            xtype : 'combo'
		        }
		    }, {
		        header: '起点',//起点
		        dataIndex: 'valueaddLeftrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    }, {
		        header: '终点',//终点
		        dataIndex: 'valueaddRightrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    },{
		        header: '最低一票',//最低一票
		        dataIndex: 'minFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最高一票',//最高一票
		        dataIndex: 'maxFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    }, {
		        header: '默认收费金额',//默认收费金额
		        dataIndex: 'fee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: pricing.basicValueAdded.i18n('foss.pricing.whetherItCanBeModified'),//是否可修改
		        dataIndex: 'canmodify',
		        width: 95,
		        renderer: function(value){
		        	if(value=='N'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.no');
		        	}else if(value=='Y'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.ye');
		        	}else{
		        		return '';
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'value',
		    	    valueField: 'key',
		    	    editable:false,
		    	    store:pricing.getStore(null,null,['key','value']
		    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.ye')}]),
		            xtype : 'combo'
		        }
		    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单仓储费（下部） CCF
 */
Ext.define('Foss.pricing.basicValueAdded.StorageChargesFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
    defaults : {
    	colspan: 1,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	anchor : '100%'
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 2
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        readOnly:isReadOnly,
	        name:'feeRate',
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.charges'),//原件返单
	        maxValue: 9999999.99,
	        minValue: 0 
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanmpiao'),//元/m³/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        readOnly:isReadOnly,
	        name:'minFeeRate',
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.minimumChargeQu'),
	        maxValue: 9999999.99,
	        minValue: 0 
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:0,
	        readOnly:isReadOnly,
	        name:'maxFeeRate',
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.maximumCharge'),
	        maxValue: 9999999,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.basicValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单仓储费（下部） CCF
 */
Ext.define('Foss.pricing.basicValueAdded.StorageChargesFormPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增一条其他费用默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.originalCost,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
            'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFeeRate':1,
        	'maxFeeRate':1,
        	'maxFee':0,
        	'minFee':0,
        	'feeRate':10,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text:'按体积新增',//按体积新增
    		    	handler:function(){
    		    		me.addOneModel(pricing.basicValueAdded.Volume);
    		    	}
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
				text : '操作',//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: '删除',//删除
						width:42,
						getClass : function (v, m, r, rowIndex) {
							if (!isReadOnly) {
								return 'deppon_icons_delete';
							} else {
								return 'statementBill_hide';
							}
						},
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes('是否要删除这条保管费信息？',function(e){//是否要删除这条保管费信息？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  }, {
		        header: '起点',//起点
		        dataIndex: 'valueaddLeftrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    }, {
		        header: '终点',//终点
		        dataIndex: 'valueaddRightrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    },{
		        header: '最低费率',//最低费率
		        dataIndex: 'minFeeRate',
		        width: 70,
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.YUANBYWEIGHTBYTICKET;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUMEBYTICKET;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最高费率',//最高费率
		        dataIndex: 'maxFeeRate',
		        width: 70,
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.YUANBYWEIGHTBYTICKET;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUMEBYTICKET;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    }, {
		        header: '默认费率',//默认费率
		        dataIndex: 'feeRate',
		        width: 70,
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.YUANBYWEIGHTBYTICKET;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.YUANBYVOLUMEBYTICKET;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最低一票',//最低一票
		        dataIndex: 'minFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最高一票',//最高一票
		        dataIndex: 'maxFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: 0,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header:'费率是否可修改',//费率是否可修改
		        dataIndex: 'canmodify',
		        width: 95,
		        renderer: function(value){
		        	if(value=='N'){
		        		return '否';
		        	}else if(value=='Y'){
		        		return '是';
		        	}else{
		        		return '';
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'value',
		    	    valueField: 'key',
		    	    editable:false,
		    	    store:pricing.getStore(null,null,['key','value']
		    	    ,[{'key':'N','value':'否'},{'key':'Y','value':'是'}]),
		            xtype : 'combo'
		        }
		    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单其它费（下部）可编辑表格 pricing.basicValueAdded.otherCharges = 'QT';//其他费用
 */
Ext.define('Foss.pricing.basicValueAdded.OtherChargesGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增一条其他费用默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.originalCost,
        	'valueaddCaculateType':valueaddCaculateType,
        	'feeRate':1,
            'subType':pricing.basicValueAdded.otherChargesSubType[0].code,
            'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFee':1,
        	'maxFee':1,
        	'fee':1,
        	'canmodify':'Y',
        	'candelete':'Y'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text:'按体积新增',//按体积新增
    		    	handler:function(){
    		    		me.addOneModel(pricing.basicValueAdded.Volume);
    		    	}
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
				xtype:'actioncolumn',
				align: 'center',
				width:80,
				items: [{
						iconCls: 'deppon_icons_delete',
		                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
						width:42,
						getClass : function (v, m, r, rowIndex) {
							if (!isReadOnly) {
								return 'deppon_icons_delete';
							} else {
								return 'statementBill_hide';
							}
						},
		                handler: function(grid, rowIndex, colIndex) {
		            		//获取选中的数据
		    				var record=grid.getStore().getAt(rowIndex);
		            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteOtherCostInformation'),function(e){//是否要删除这条其他费用信息？
		            			if(e=='yes'){//询问是否删除，是则发送请求
		            				grid.getStore().remove(record);
		            			}
		            		});
		                }
					}]
			  },{
		        header: '费用名称',//费用名称
		        dataIndex: 'subType', 
		        renderer:function(value){
		        	for(var i=0;i<pricing.basicValueAdded.otherChargesSubType.length;i++){
		        		if(pricing.basicValueAdded.otherChargesSubType[i].code==value){
		        			return pricing.basicValueAdded.otherChargesSubType[i].name;
		        		}
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'name',
		    	    valueField: 'code',
		    	    editable:false,
		    	    allowBlank: false,
		    	    store:pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceEntityModel',null
		    	    ,pricing.basicValueAdded.otherChargesSubType),
		            xtype : 'combo'
		        }
		    }, {
		        header: pricing.basicValueAdded.i18n('foss.pricing.imputationCategory'),
		        dataIndex: 'subType',
		        renderer:function(value,metaDatea,record){
		        	for(var i=0;i<pricing.basicValueAdded.otherChargesSubType.length;i++){
		        		if(pricing.basicValueAdded.otherChargesSubType[i].code==value){
		        			record.data.togeterCategory = pricing.basicValueAdded.otherChargesSubType[i].blongPricingCode;
		        			return pricing.basicValueAdded.otherChargesSubType[i].blongPricingName;
		        		}
		        	}
		        }
		    }, 
		    	{
		        header: '起点',//起点
		        dataIndex: 'valueaddLeftrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    }, {
		        header: '终点',//终点
		        dataIndex: 'valueaddRightrange',
		        renderer:function(value,metaData,record){
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
		        		return value+pricing.basicValueAdded.KG;
		        	}
		        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
		        		return value+pricing.basicValueAdded.M3;
		        	}
		        	return value;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
					decimalPrecision:2,
		            step:1,
		            minValue: 0,
		            maxValue: 9999999.99
		        }
		    },{
		        header: '最低一票',//最低一票
		        dataIndex: 'minFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: -9999999,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: '最高一票',//最高一票
		        dataIndex: 'maxFee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: -9999999,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    }, {
		        header: '默认收费金额',//默认收费金额
		        dataIndex: 'fee',
		        width: 70,
		        renderer:function(val){
		        	return val+pricing.basicValueAdded.YUANBYTICKET;
		        },
		        editor: {
		            xtype: 'numberfield',
		            allowBlank: false,
		            minValue: -9999999,
		            allowDecimals:false,
		            maxValue: 9999999
		        }
		    },{
		        header: pricing.basicValueAdded.i18n('foss.pricing.whetherItCanBeModified'),//是否可修改
		        dataIndex: 'canmodify',
		        width: 95,
		        renderer: function(value){
		        	if(value=='N'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.no');
		        	}else if(value=='Y'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.ye');
		        	}else{
		        		return '';
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'value',
		    	    valueField: 'key',
		    	    editable:false,
		    	    store:pricing.getStore(null,null,['key','value']
		    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.ye')}]),
		            xtype : 'combo'
		        }
		    },{
		        header: '是否可不收取',//是否可不收取
		        dataIndex: 'candelete',
		        width: 95,
		        renderer: function(value){
		        	if(value=='N'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.no');
		        	}else if(value=='Y'){
		        		return pricing.basicValueAdded.i18n('i18n.pricingRegion.ye');
		        	}else{
		        		return '';
		        	}
		        },
		        editor: {
		    		queryMode: 'local',
		    	    displayField: 'value',
		    	    valueField: 'key',
		    	    editable:false,
		    	    store:pricing.getStore(null,null,['key','value']
		    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.basicValueAdded.i18n('i18n.pricingRegion.ye')}]),
		            xtype : 'combo'
		        }
		    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单送货费（下部）可编辑表格pricing.basicValueAdded.deliveryCharges = 'SH';//送货费
 */
Ext.define('Foss.pricing.basicValueAdded.DeliveryChargesGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    autoScroll:true,
    enableColumnMove:false,
	flex:1,
    //新增重量一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':valueaddCaculateType,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFeeRate':0.01,
        	'maxFeeRate':0.01,
        	'feeRate':0.01,
        	'minFee':1,
//        	'maxFee':1,
        	'canmodify':'N'  
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		        text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume);
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
							return 'deppon_icons_delete';
						} else {
							return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteThisDeliveryCharge'),function(e){//是否要删除这条送货费信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    }
//	    ,{
//	        header: '最高一票',//最高一票
//	        dataIndex: 'maxFee',
//	        width: 70,
//	        renderer:function(val){
//	        	return val+pricing.basicValueAdded.YUANBYTICKET;
//	        },
//	        editor: {
//	            xtype: 'numberfield',
//	            allowBlank: false,
//	            minValue: 0,
//	            allowDecimals:false,
//	            maxValue: 9999999
//	        }
//	    }
	    ,{
	        header: pricing.basicValueAdded.i18n('foss.pricing.ratesIsUpdate'),//费率是否可以修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return '';
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
	    me.callParent([cfg]);
	}
});
/**
 * 增值服务表单代收货款（下部）可编辑表格pricing.basicValueAdded.paymentCollection = 'HK';//代收货款
 */
Ext.define('Foss.pricing.basicValueAdded.PaymentCollectionGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    autoScroll:true,
	flex:1,
	autoScroll:true,
    //新增代收货款一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
    	var store = FossDataDictionary.getDataDictionaryStore(pricing.basicValueAdded.returnSubType);
    	var subType = '';
    	if(!Ext.isEmpty(store)){
    		subType = store.getAt(0).get('valueCode');
    	}
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.originalCost,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
        	'subType':subType,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'leftrange':0,
        	'rightrange':9999999,
        	'minFeeRate':0.001,
        	'maxFeeRate':0.001,
        	'feeRate':0.001,
        	'minFee':1,
        	'maxFee':1,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		    	text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume);
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
							return 'deppon_icons_delete';
						} else {
							return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteThisCollectionMoney'),function(e){//是否要删除这条代收货款信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
	    	header: pricing.basicValueAdded.i18n('foss.pricing.refundType'),//退款类型
	        dataIndex: 'subType',
	        width: 95,
	        renderer: function(value){
	        	return FossDataDictionary.rendererSubmitToDisplay(value,pricing.basicValueAdded.returnSubType);
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'valueName',
	    	    valueField: 'valueCode',
	    	    editable:false,
	    	    store:FossDataDictionary.getDataDictionaryStore(pricing.basicValueAdded.returnSubType),
	    	    xtype : 'combo'
	        }
	    },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '代收金额起点',//代收金额起点
	        dataIndex: 'leftrange',
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUAN;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 9999999,
	             allowDecimals:false
	        }
	    }, {
	        header: '代收金额终点',//代收金额终点
	        dataIndex: 'rightrange',
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUAN;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 9999999,
	             allowDecimals:false
	        }
	    },{
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:3,
				step:0.001,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:3,
				step:0.001,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:3,
				step:0.001,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 65,
			renderer:function(val){
				return val+pricing.basicValueAdded.YUANBYTICKET;
			},
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 9999999,
	            allowDecimals:false
	        }
	    },{
	        header: ' 最高一票',// 最高一票
	        dataIndex: 'maxFee',
	        width: 65,
	        renderer:function(val){
				return val+pricing.basicValueAdded.YUANBYTICKET;
			},
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 9999999,
	            allowDecimals:false
	        }
	    }, {
	        header: '费率是否可修改',//费率是否可修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return '';
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单超远派送（下部）可编辑表格pricing.basicValueAdded.superDelivery = 'CY';//超远派送费
 */
Ext.define('Foss.pricing.basicValueAdded.SuperDeliveryGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增超远派送一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.kilometer,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.FEE,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'leftrange':0,
        	'rightrange':0,
        	'minFee':0,
        	'maxFee':9999999,
        	'canmodify':'N',
        	'fee':50
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		        text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume);
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
						    return 'deppon_icons_delete';
						} else {
						    return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteFarDeliveryInformation'),function(e){//是否要删除这条超远派送信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '距离起点',//距离起点
	        dataIndex: 'leftrange',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.KM;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999
	        }
	    },{
	        header: '距离终点',//距离终点
	        dataIndex: 'rightrange',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.KM;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最高一票',//最高一票
	        dataIndex: 'maxFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '默认收费金额',//默认收费金额
	        dataIndex: 'fee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '收费金额是否可修改',//收费金额是否可修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return '';
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }
	    ];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单送货上楼（下部）可编辑表格pricing.basicValueAdded.deliveryUpstairs = 'SHSL';//送货上楼
 */
Ext.define('Foss.pricing.basicValueAdded.DeliveryUpstairsGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增送货上楼一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.Weight,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFeeRate':0.01,
        	'maxFeeRate':0.01,
        	'maxFee':1,
        	'minFee':1,
        	'feeRate':0.01,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		        text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume);
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns =  [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
						    return 'deppon_icons_delete';
						} else {
						    return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteThisDeliveryUpstairs'),function(e){//是否要删除这条送货上楼信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最高一票',//最高一票
	        dataIndex: 'maxFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: pricing.basicValueAdded.i18n('foss.pricing.ratesIsUpdate'),//费率是否可以修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return value;
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单纸纤包装（下部）
 * @author:218371-foss-zhaoyanjun
 * @date:2014-11-11上午08:46
 */
Ext.define('Foss.pricing.basicValueAdded.paperFiberPackingFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
	//height:1000,
	autoScroll:true,
    defaults : {
    	colspan: 1,
    	margin : '3 3 3 3',
    	width:140,
    	labelWidth:60,
    	anchor : '100%',
    	labelSeparator:':',
    	labelAlign:'right'
    },
	defaultType : 'textfield',
	layout:{
		type:'table',
		columns: 3
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.paperBoxOne'),//纸箱1号 客户
	        name:'paperBoxOne',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelOneBlue'),//纤袋1号 蓝
	        name:'fibelOneBlue',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelOneWhite'),//纤袋1号 白
	        name:'fibelOneWhite',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.paperBoxTwo'),//纸箱2号 客户
	        name:'paperBoxTwo',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelTwoBlue'),//纤袋2号 蓝
	        name:'fibelTwoBlue',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelTwoWhite'),//纤袋2号 白
	        name:'fibelTwoWhite',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.paperBoxThree'),//纸箱3号 客户
	        name:'paperBoxThree',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelThreeBlue'),//纤袋3号 蓝
	        name:'fibelThreeBlue',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelThreeWhite'),//纤袋3号白
	        name:'fibelThreeWhite',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.paperBoxFour'),//纸箱4号 客户
	        name:'paperBoxFour',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelFourBlue'),//纤袋4号 蓝 
	        name:'fibelFourBlue',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelFourWhite'),//纤袋4号 白
	        name:'fibelFourWhite',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelFiveWhite'),//纤袋5号 布匹白
	        name:'fibelFiveWhite',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
			decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.fibelSixWhite'),//纤袋6号 布匹白
	        name:'fibelFiveSix',
	        readOnly:isReadOnly,
	        maxValue: 99999.99,
	        minValue: 0
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改弹窗-设置终止时间
 */
Ext.define('Foss.pricing.basicValueAdded.BasicValueAddedEndTimeWindow',{
	extend : 'Ext.window.Window',
	title : pricing.basicValueAdded.i18n('i18n.pricingRegion.setEndTime'),
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	parent:null,//(Foss.pricing.basicValueAdded.PricingBasicValuationGridPanel)
	width :250,
	height :150,
	endTimeField:null,//设置失效时间的datefield
	selection:null,//选择的当行的数据
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getEndTimeField().reset();//清除上次的数据
		}
	},
     //得到失效日期元素
	getEndTimeField:function(){
		if(Ext.isEmpty(this.endTimeField)){
    		this.endTimeField = Ext.create('Ext.form.field.Date',{
    			minValue: new Date(pricing.basicValueAdded.tomorrowTime),
    			//labelSeparator:'',
    	    	labelWidth:60,
    			fieldLabel: pricing.basicValueAdded.i18n('i18n.pricingRegion.endTime'),//失效日期
    			name:'endTime',
    			editable:false
    		});
    	}
    	return this.endTimeField;
	},
    //设置失效日期
	commintEndTime:function(){
    	var me = this;
    	var time = me.getEndTimeField().getValue().getTime();
    	time = time+(24*60*60*1000-1);//设置时间为当天的最后一毫秒
    	var params = {'pricingValuationVo':{'priceValuationEntity':{'id':me.selection.get('id'),'endTime':new Date(time)}}};
    	var successFun = function(json){
			pricing.showInfoMes(json.message);
			me.parent.getPagingToolbar().moveFirst();
			me.close();
		};
		var failureFun = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var url = pricing.realPath('updatePriceValuation.action');
		pricing.requestJsonAjax(url,params,successFun,failureFun);
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEndTimeField()];
		me.fbar = [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.determine'),//确认
			margin:'0 0 0 50',
			cls:'yellow_button',
			handler :function(){
				me.commintEndTime();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 立即中止价格方案 Window
 */
Ext.define('Foss.pricing.basicValueAdded.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.basicValueAdded.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.pricing.basicValueAdded.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.priceValuationEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.priceValuationEntity.endTime), 'Y-m-d');
	    		var value = pricing.basicValueAdded.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.basicValueAdded.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.basicValueAdded.i18n('foss.pricing.showrightEndTimeInfo');
	    		me.getStopFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getStopFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getStopFormPanel()];
			me.callParent(cfg);
		}
});

/**
 * 立即中止功能FormPanel
 */
Ext.define('Foss.pricing.basicValueAdded.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var priceValuationEntityModel = new Foss.pricing.basicValueAdded.PriceValuationEntityModel();
			form.updateRecord(priceValuationEntityModel);
			var id = me.up('window').priceValuationEntity.id;
			priceValuationEntityModel.set('id',id);
			priceValuationEntityModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricingValuationVo':{'priceValuationEntity':priceValuationEntityModel.data}};
    		var url = pricing.realPath('stopFast.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
    			me.up('window').parent.getPagingToolbar().moveFirst();
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.pricePlan.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{ 
				fieldLabel :pricing.basicValueAdded.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_basicValueAdded_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_basicValueAdded_stopEndTime_ID-inputEl'
				},
				allowBlank:false,
				columnWidth:.9
	    	},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
	        me.callParent(cfg);
    }
});


/**
 * 立即激活价格方案Window
 */
Ext.define('Foss.pricing.basicValueAdded.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.basicValueAdded.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		priceValuationEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.basicValueAdded.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.priceValuationEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.priceValuationEntity.endTime), 'Y-m-d');
	    		var value = pricing.basicValueAdded.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.basicValueAdded.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.basicValueAdded.i18n('foss.pricing.showrightEndTimeInfo');
	    		me.getImmediatelyActiveFormPanel().down('displayfield').setValue(value);
	    	},
	    	beforehide:function(me){
	    		me.getImmediatelyActiveFormPanel().getForm().reset();
	    	}
	    },
	   	constructor : function(config) {
			var me = this, cfg = Ext.apply({}, config);
			me.items = [me.getImmediatelyActiveFormPanel()];
			me.callParent(cfg);
		}
});


/**
 * 立即激活功能Form
 */
Ext.define('Foss.pricing.basicValueAdded.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
			var priceValuationEntityModel = new Foss.pricing.basicValueAdded.PriceValuationEntityModel();
			form.updateRecord(priceValuationEntityModel);
			var id = me.up('window').priceValuationEntity.id;
			priceValuationEntityModel.set('id',id);
			priceValuationEntityModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricingValuationVo':{'priceValuationEntity':priceValuationEntityModel.data}};
    		var url = pricing.realPath('activeFast.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.basicValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
    			}else{
    				pricing.showErrorMes(json.message);
    			}
    	    };
    		pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    	}
	},
	constructor: function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [{
				width:280,
				xtype: 'displayfield',
				columnWidth:.9,
				value:''
			},{
				fieldLabel:pricing.basicValueAdded.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_basicValueAdded_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_basicValueAdded_activeEndTime_ID-inputEl'
				},
				columnWidth:.9
			},{
				xtype: 'container',
				columnWidth:.6,
				html: '&nbsp;'
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.basicValueAdded.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});
/**
 * 增值服务表单大件上楼费（下部）可编辑表格pricing.basicValueAdded.largeDeliveryUpstairs = 'DJSL';//大件上楼费
 * @author:218371-foss-zhaoyanjun
 * @date:2014-12-11下午14:17
 */
Ext.define('Foss.pricing.basicValueAdded.largeDeliveryUpstairsGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    autoScroll:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增送货上楼一条默认值
    addOneModel:function(valueaddCaculateType){
    	var me = this;
        var record = Ext.create('Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity', {
        	'caculateType':pricing.basicValueAdded.Weight,
        	'valueaddCaculateType':valueaddCaculateType,
//        	'feeRate':pricing.basicValueAdded.RATE,
        	'valueaddLeftrange':0,
        	'valueaddRightrange':9999999,
        	'minFeeRate':0.01,
        	'maxFeeRate':0.01,
        	'maxFee':1,
        	'minFee':1,
        	'feeRate':0.01,
        	'canmodify':'N'
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //可编辑表格
    plugins:null,
    getPlugins:function(isReadOnly){
    	if(Ext.isEmpty(this.plugins)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
    		        clicksToEdit: 1
    		    });
    			this.plugins = [cellEditing];
    		}else{
    			this.plugins = [];
    		}
    	}
    	return this.plugins;
    },
    //tbar新增
    tbar:null,
    getTbar:function(isReadOnly){
    	var me = this;
    	if(Ext.isEmpty(this.tbar)){
    		if(!isReadOnly){//如果可编辑则给cellEditing赋值，如果不可编辑cellEditing为null
    			this.tbar = [{
    		        text: '按重量新增',//按重量新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Weight);
    		        }
    		    },{
    		        text: '按体积新增',//按体积新增
    		        handler : function(){
    		        	me.addOneModel(pricing.basicValueAdded.Volume);
    		        }
    		    }];//设置新增按按钮
    		}
    	}
    	return this.tbar;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);	
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.columns =  [{
			text : pricing.basicValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.basicValueAdded.i18n('foss.pricing.delete'),//删除
					width:42,
					getClass : function (v, m, r, rowIndex) {
						if (!isReadOnly) {
						    return 'deppon_icons_delete';
						} else {
						    return 'statementBill_hide';
						}
					},
	                handler: function(grid, rowIndex, colIndex) {
	            		//获取选中的数据
	    				var record=grid.getStore().getAt(rowIndex);
	            		pricing.showQuestionMes(pricing.basicValueAdded.i18n('foss.pricing.wantDeleteThisDeliveryUpstairs'),function(e){//是否要删除这条送货上楼信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});
	                }
				}]
		  },{
	        header: '起点',//起点
	        dataIndex: 'valueaddLeftrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '终点',//终点
	        dataIndex: 'valueaddRightrange',
	        renderer:function(value,metaData,record){
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Weight){
	        		return value+pricing.basicValueAdded.KG;
	        	}
	        	if(record.get('valueaddCaculateType')==pricing.basicValueAdded.Volume){
	        		return value+pricing.basicValueAdded.M3;
	        	}
	        	return value;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
	            step:1,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    }, {
	        header: '最低费率',//最低费率
	        dataIndex: 'minFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最高费率',//最高费率
	        dataIndex: 'maxFeeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '默认费率',//默认费率
	        dataIndex: 'feeRate',
	        width: 70,
	        renderer:function(val,metaData,record){
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Weight){
	        		return val+pricing.basicValueAdded.YUANBYWEIGHT;
	        	}
	        	if(record.get('valueaddCaculateType') == pricing.basicValueAdded.Volume){
	        		return val+pricing.basicValueAdded.YUANBYVOLUME;
	        	}
	        	return val;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:2,
				step:0.01,
	            minValue: 0,
	            maxValue: 9999999.99
	        }
	    },{
	        header: '最低一票',//最低一票
	        dataIndex: 'minFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: '最高一票',//最高一票
	        dataIndex: 'maxFee',
	        width: 70,
	        renderer:function(val){
	        	return val+pricing.basicValueAdded.YUANBYTICKET;
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            allowDecimals:false,
	            maxValue: 9999999
	        }
	    },{
	        header: pricing.basicValueAdded.i18n('foss.pricing.ratesIsUpdate'),//费率是否可以修改
	        dataIndex: 'canmodify',
	        width: 120,
	        renderer: function(value){
	        	if(value=='N'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isNo');//否
	        	}else if(value=='Y'){
	        		return pricing.basicValueAdded.i18n('foss.pricing.isYes');
	        	}else{
	        		return value;
	        	}
	        },
	        editor: {
	    		queryMode: 'local',
	    	    displayField: 'value',
	    	    valueField: 'key',
	    	    editable:false,
	    	    store:pricing.getStore(null,null,['key','value']
	    	    ,[{'key':'N','value':pricing.basicValueAdded.i18n('foss.pricing.isNo')},{'key':'Y','value':pricing.basicValueAdded.i18n('foss.pricing.isYes')}]),
	            xtype : 'combo'
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.basicValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});


/**
 * 货物类型树 基础,产品,区域增值服务三个界面一样的定义,改一处,必须三个界面都修改
 */
Ext.define('Foss.pricing.IndustryTree',{
	extend:'Ext.tree.Panel',
	alias: ['widget.foss_pricing_industrytree'],
	collapsible:true,
 　　　frame:true,
 	animate:true,
 	useArrows:true,//使用Vista样式箭头
 	lines:false,//不显示竖线
 　　　rootVisible:false,
 　　　autoScroll:true,
 	width:280,
 	height:190,
 	selModel:{mode:'MULTI'},
 	title:'货物类型',
 	initComponent:function(){
 		var me = this;
 		me.on('checkchange',me.checkchangeFn);
 		me.callParent();
 	},
 	//如果是选中或取消父节点,选中或取消所有子节点
 	checkchangeFn:function(node, checked){
 		var me = this;
 		if(node.hasChildNodes()){
 			node.eachChild(function(child){
 				child.set('checked',checked);
 				child.fireEvent('checkchange', [child, checked]);
 				me.checkchangeFn(child,checked);
 			});
 		}
 	}
});
/**
 * 查询货物类型数据 基础,产品,区域增值服务三个界面一样的定义,改一处,必须三个界面都修改
 */
Ext.Ajax.request({
	url:'../pricing/queryAllSecProfession.action',
	success:function(response){
		pricing.industryData = Ext.decode(response.responseText).pricingValuationVo.customerIndustryEntityList;
	}
});
/**
 * 货物类型树获取选中数据
 */
pricing.getIndustryData = function(treepanel){
	var submitArr = [];
	var records = treepanel.getChecked();
	//如果未选中,或者选中的没有叶子节点 返回全部数据
	if(!Ext.isEmpty(records)){
		Ext.Array.each(records,function(record){
			if(record.data.leaf){
				submitArr.push(record.data.entity)
			}
		});
	}
	if(Ext.isEmpty(submitArr)){
		return pricing.industryData;
	}
	return submitArr;
};
/**
 *校验二级行业编码是否是否包含传入的参数 ,返回true或者false
 */
pricing.checkContainer = function(array,record){
	var flag = false;
	Ext.Array.each(array,function(r){
		if(r.professionCode==record.professionCode){
			flag = true;
			//跳出循环
			return false;
		}
	});
	return flag;
}
/**
 * 货物类型树加载数据 基础,产品,区域增值服务三个界面一样的定义,改一处,必须三个界面都修改
 */
pricing.showIndustryTreeData = function(treepanel,checkedArr){
	var rootNode = Ext.create('Ext.data.Field',{
		text:'货物类型',
		checked : false
	});
	treepanel.setRootNode(rootNode);
	rootNode = treepanel.getRootNode();
	//用于遍历时校验一级行业是否已经加载
	var map ={};
	//遍历到的当前一级行业
	var currFirstNode;
	Ext.Array.each(pricing.industryData,function(e){
		//如果一级行业还没有
		if(!map[e.parentProfessionCode]){
			map[e.parentProfessionCode] = true;
			//添加一级行业
			currFirstNode = rootNode.appendChild(new Ext.data.Field({
				text:e.parentProfessionName,
				checked : false,
				leaf:false
			}));
			//这个数据必须单独放置,如果加载field对象中,转换成node时会丢失
			currFirstNode.data.entity = e;
		}
		//添加二级行业
		var secodeIndustyChecked = Ext.isEmpty(checkedArr)?false:pricing.checkContainer(checkedArr,e);
		var secodeIndusty = currFirstNode.appendChild(new Ext.data.Field({
			text:e.professionName,
			checked : secodeIndustyChecked,
			leaf:true
		}));
		if(secodeIndustyChecked){
			secodeIndusty.parentNode.set('checked',true);
		}
		secodeIndusty.data.entity = e;
	});
};
