pricing.expressRegionValueAdded.VALUATION_TYPE_expressRegionValueAdded = 'REGIONVALUEADDED'//计费规则——规则类型——区域增值服务
pricing.expressRegionValueAdded.PRICING_CODE_VALUEADDED = "VAS";//增值服务是BF，HK,SH等的父节点
pricing.expressRegionValueAdded.insurance = 'BF';//保费
pricing.expressRegionValueAdded.paymentCollection = 'HK';//代收货款
pricing.expressRegionValueAdded.deliveryCharges = 'SH';//送货费
pricing.expressRegionValueAdded.receivingCharges = 'JH';//接货费
pricing.expressRegionValueAdded.sign = 'QS';//签收回单
pricing.expressRegionValueAdded.storageCharges = 'CCF';//仓储费
pricing.expressRegionValueAdded.otherCharges = 'QT';//其他费用
pricing.expressRegionValueAdded.superDelivery = 'CY';//超远派送
pricing.expressRegionValueAdded.deliveryUpstairs = 'SHSL';//送货上楼
pricing.expressRegionValueAdded.packing = 'BZ';//包装
pricing.expressRegionValueAdded.ALL = 'ALL';
pricing.expressRegionValueAdded.LONG = 'L';//长途
pricing.expressRegionValueAdded.SHORT = 'S';//短途
pricing.expressRegionValueAdded.Weight = 'WEIGHT';//计价方式明细-计费类别-按重量
pricing.expressRegionValueAdded.Volume = 'VOLUME';//计价方式明细-计费类别-按体积
pricing.expressRegionValueAdded.originalCost = 'ORIGINALCOST';//计价方式明细-计费类别-原始费用计费
pricing.expressRegionValueAdded.kilometer = 'KILOM';//计价方式明细-计费类别-按公里
pricing.expressRegionValueAdded.returnSubType = 'COD__COD_TYPE';//即日退，三日退，审核退
pricing.expressRegionValueAdded.YJFD = 'ORIGINAL';//计价方式明细--服务子类型-签收回单-原件反单
pricing.expressRegionValueAdded.CZFD = 'FAX';//计价方式明细--服务子类型-签收回单-传真反单
//包装类型subType
pricing.expressRegionValueAdded.PACKAGE_TYPE__BOX='BOX';//木箱
pricing.expressRegionValueAdded.PACKAGE_TYPE__FRAME='FRAME';//木架
//计价方式明细—-按重量最大值
pricing.expressRegionValueAdded.CRITERIA_DETAIL_WEIGHT_MAX = 9999999999;
//计价方式明细——按体积最大值
pricing.expressRegionValueAdded.CRITERIA_DETAIL_VOLUME_MAX = 9999999999;
//计价方式明细——按原始费用计费最大值
pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX = 9999999999;
pricing.expressRegionValueAdded.valueAddedType = [];//增值服务类型
pricing.expressRegionValueAdded.otherChargesSubType = [];//其他费用（QT）的子类型
pricing.expressRegionValueAdded.tomorrowTime = null;//服务器明天时间
pricing.expressRegionValueAdded.productEntityList = [];//基础产品列表
pricing.expressRegionValueAdded.goodTypeList = [];//货物类型列表
pricing.expressRegionValueAdded.limitedWarrantyItems = [];//限保物品
//价格区域
pricing.expressRegionValueAdded.PRICING_REGION = 'PRICING_REGION';
//时效区域
pricing.expressRegionValueAdded.PRESCRIPTION_REGION = 'EFFECTIVE_REGION';
pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE = 'PACKAGE';//基础产品类型：经济快递
pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE_NO = 'PACKAGE_NO';//非经济快递：零担
pricing.expressRegionValueAdded.PRODUCTCODE_RCP ='RCP';//基础产品类型：3.60特惠件


//查询货物类型列表
pricing.searchGoodTypeList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findGoodsTypeByCondictionExpress.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressRegionValueAdded.goodTypeList = result.pricingValuationVo.goodsTypeEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询基础产品列表
pricing.searchProductEntityList = function(){
	Ext.Ajax.request({
		url:pricing.realPath('findProductByConditionExpress.action'),//查询基础产品
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressRegionValueAdded.productEntityList = result.pricingValuationVo.productEntityList;
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询限保物品
pricing.queryLimitedWarrantyItemsByEntity = function(){
	Ext.Ajax.request({
		url:pricing.realPath('queryLimitedWarrantyItemsByEntityExpress.action'),
		async:false,
		jsonData:{},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressRegionValueAdded.limitedWarrantyItems = result.pricingValuationVo.limitedWarrantyItemsEntityList;//查询限保物品
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询增值服务类型
pricing.searchValueAddedType = function(){
	Ext.Ajax.request({
		url:pricing.realPath('searchValueAddedTypeExpress.action'),
		async:false,
		jsonData:{'pricingValuationVo':{'priceEntity':{'refCode':pricing.expressRegionValueAdded.PRICING_CODE_VALUEADDED}}},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressRegionValueAdded.valueAddedType = result.pricingValuationVo.priceEntityList;//将获取的增值服务类型放到全局变量中
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//查询增值服务类型,其他费用（QT）的子类型
pricing.searchOtherChargesSubType = function(){
	Ext.Ajax.request({
		url:pricing.realPath('searchValueAddedTypeExpress.action'),
		async:false,
		jsonData:{'pricingValuationVo':{'priceEntity':{'refCode':pricing.expressRegionValueAdded.otherCharges}}},
		success:function(response){
			var result = Ext.decode(response.responseText);
			pricing.expressRegionValueAdded.otherChargesSubType = result.pricingValuationVo.priceEntityList;//将获取的国家信息放到全局变量中
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		}
	});
};
//获取服务当前时间
pricing.haveServerNowTime = function(){
	Ext.Ajax.request({
		url:pricing.realPath('haveServerNowTimeExpress.action'),
		async:false,
		success:function(response){
			var result = Ext.decode(response.responseText);
			var today = new Date(result.pricingValuationVo.nowTime);//获取服务当前时间
			pricing.expressRegionValueAdded.tomorrowTime = today.setDate(today.getDate()+1);//设置明天的时间
		},
		failure:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(result.message);
			}
		},
		exception:function(response){
			var result = Ext.decode(response.responseText);
			if(Ext.isEmpty(result)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
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
pricing.requestAndSetInfoRegion = function(valuationRecord,windowShow,isUpdate){
	 var params = {'pricingValuationVo':{'valuationId':valuationRecord.get('id')}};
	    var successFun = function(json){
	    	var priceCriteriaDetailEntityList = json.pricingValuationVo.priceCriteriaDetailEntityList;
	    	if(priceCriteriaDetailEntityList.length<1){
	    		pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('foss.pricing.sectionAreaValueAddedServiceTheLackDetailedInformation'));
	    		return;
	    	}
	    	windowShow.getEditForm().loadRecord(valuationRecord);
	    	if(isUpdate){//如果是修改将生效日期修改为今天
	    		windowShow.valuationRecord = valuationRecord;//将计费规则的RECORD设置到window中
	    		windowShow.priceCriteriaDetailEntityList = priceCriteriaDetailEntityList;//将计价方式明细的LIST设置到window中
	    		//windowShow.getEditForm().getForm().findField('beginTime').setValue(new Date(pricing.expressRegionValueAdded.tomorrowTime));
	    		//windowShow.getEditForm().getForm().findField('endTime').setValue(new Date(pricing.expressRegionValueAdded.tomorrowTime));
	    		windowShow.getEditForm().getForm().findField('arrvRegionName').regionId = valuationRecord.get('arrvRegionId');//将ID设置到regionId属性中
	    		windowShow.getEditForm().getForm().findField('deptRegionName').regionId = valuationRecord.get('deptRegionId');
	    		windowShow.getEditForm().getForm().findField('productId').setValue(valuationRecord.get('productId'));
	    		if(valuationRecord.get('active')=='Y'){//不可修改内容
		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[0].setReadOnly(true);//如果激活,则长短途不可以修改
		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[1].setReadOnly(true);
		    		//windowShow.getEditForm().getForm().findField('description').setReadOnly(true);//如果激活,则方案描述不可以修改
		    		windowShow.getEditForm().getForm().findField('goodsTypeId').setReadOnly(true);//如果激活,则货物类型不可以修改
		    		windowShow.getEditForm().getForm().findField('productId').setReadOnly(true);//如果激活,则基础产品仍为经济快递
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').setReadOnly(true);//如果激活,则目的地（区域）不可以修改
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').up('container').down('button').setDisabled(true);//如果激活,则目的地（区域）的选择按钮不可用
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').up('container').items.items[2].setDisabled(true);//如果激活,则目的地（区域）的取消按钮不可用
		    		windowShow.getEditForm().getForm().findField('deptRegionName').setReadOnly(true);//如果激活,则出发地（区域）不可以修改
		    		windowShow.getEditForm().getForm().findField('deptRegionName').up('container').down('button').setDisabled(true);//如果激活,则出发地（区域）的选择按钮不可用
		    		windowShow.getEditForm().getForm().findField('deptRegionName').up('container').items.items[2].setDisabled(true);//如果激活,则出发地（区域）的取消按钮不可用
		    		//windowShow.getEditForm().getForm().findField('code').setDisabled(true);//如果激活,则增值服务方案编号的选择按钮不可用
		    		//windowShow.getEditForm().getForm().findField('name').setDisabled(true);//如果激活,则增值服务方案名称的选择按钮不可用
		    		
		    	}else if(valuationRecord.get('active')=='N'){//可修改内容
		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[0].setReadOnly(false);//如果未激活,则长短途可以修改
		    		windowShow.getEditForm().getForm().findField('longOrShort').items.items[1].setReadOnly(false);
		    		windowShow.getEditForm().getForm().findField('remarks').setReadOnly(false);//如果未激活,则方案描述可以修改
		    		windowShow.getEditForm().getForm().findField('goodsTypeId').setReadOnly(false);//如果未激活,则货物类型可以修改
		    		windowShow.getEditForm().getForm().findField('productId').setReadOnly(false);//如果未激活,基础产品可以进行修改
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').setReadOnly(false);//如果未激活,则目的地（区域）可以修改
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').up('container').down('button').setDisabled(false);//如果未激活,则目的地（区域）的选择按钮可用
		    		windowShow.getEditForm().getForm().findField('arrvRegionName').up('container').items.items[2].setDisabled(false);//如果激活,则目的地（区域）的取消按钮可用
		    		windowShow.getEditForm().getForm().findField('deptRegionName').setReadOnly(false);//如果未激活,则出发地（区域）可以修改
		    		windowShow.getEditForm().getForm().findField('deptRegionName').up('container').down('button').setDisabled(false);//如果未激活,则出发地（区域）的选择按钮可用
		    		windowShow.getEditForm().getForm().findField('deptRegionName').up('container').items.items[2].setDisabled(false);//如果激活,则出发地（区域）的取消按钮可用
		    		//windowShow.getEditForm().getForm().findField('code').setDisabled(false);//如果未激活,则增值服务方案编号的选择按钮可用
		    		//windowShow.getEditForm().getForm().findField('name').setDisabled(false);//如果未激活,则增值服务方案名称的选择按钮可用
		    	}
	    	}else{
	    		windowShow.getEditForm().getForm().findField('productId').setReadOnly(true);//查看详情  基础产品不可以进行修改
	    	}
	    	if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.insurance){//保费
	    		//windowShow.getDownPanel().getInsuranceGridPanel().loadRecord(new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(priceCriteriaDetailEntityList[0]));
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].leftrange = priceCriteriaDetailEntityList[i].leftrange/100;
	    			priceCriteriaDetailEntityList[i].rightrange = priceCriteriaDetailEntityList[i].rightrange/100;
	    		}
	    		windowShow.getDownPanel().getInsuranceGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.storageCharges){//仓储
	    		priceCriteriaDetailEntityList[0].feeRate = priceCriteriaDetailEntityList[0].feeRate/100;
	    		windowShow.getDownPanel().getStorageChargesFormPanel().loadRecord(new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(priceCriteriaDetailEntityList[0]));
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.otherCharges){//其它
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].feeRate = priceCriteriaDetailEntityList[i].feeRate/100;
	    		}
	    		windowShow.getDownPanel().getOtherChargesGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.deliveryCharges){//送货
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].feeRate = priceCriteriaDetailEntityList[i].feeRate/100;
	    		}
	    		windowShow.getDownPanel().getDeliveryChargesGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.paymentCollection){//代收货款
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].leftrange = priceCriteriaDetailEntityList[i].leftrange/100;
	    			priceCriteriaDetailEntityList[i].rightrange = priceCriteriaDetailEntityList[i].rightrange/100;
	    		}
	    		windowShow.getDownPanel().getPaymentCollectionGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.superDelivery){//超远派送
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].leftrange = priceCriteriaDetailEntityList[i].leftrange;
	    			priceCriteriaDetailEntityList[i].rightrange = priceCriteriaDetailEntityList[i].rightrange;
	    		}
	    		windowShow.getDownPanel().getSuperDeliveryGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.deliveryUpstairs){//送货上楼费
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			priceCriteriaDetailEntityList[i].leftrange = priceCriteriaDetailEntityList[i].leftrange;
	    			priceCriteriaDetailEntityList[i].rightrange = priceCriteriaDetailEntityList[i].rightrange;
	    			priceCriteriaDetailEntityList[i].feeRate = priceCriteriaDetailEntityList[i].feeRate/100;
	    		}
	    		windowShow.getDownPanel().getDeliveryUpstairsGridPanel().getStore().loadData(priceCriteriaDetailEntityList);
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.packing){//包装费
	    		var elements = windowShow.getDownPanel().getPackingFormPanel().getForm().getFields( ).items;//获得form页面元素
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			if(pricing.expressRegionValueAdded.PACKAGE_TYPE__FRAME==priceCriteriaDetailEntityList[i].subType){
	    				elements[0].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
	    				elements[1].setValue(priceCriteriaDetailEntityList[i].minFee)//最低一票费率
	    			}else if(pricing.expressRegionValueAdded.PACKAGE_TYPE__BOX==priceCriteriaDetailEntityList[i].subType){
	    				elements[2].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
	    				elements[3].setValue(priceCriteriaDetailEntityList[i].minFee)//最低一票费率
	    			}
	    		}
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.sign){//签收回单
	    		var elements = windowShow.getDownPanel().getSignFormPanel().getForm().getFields( ).items;//获得form页面元素，一个原件返单，一个传真返单
	    		for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
	    			if(elements[0].subType==priceCriteriaDetailEntityList[i].subType){
	    				elements[0].setValue(priceCriteriaDetailEntityList[i].fee)//设置费率
	    			}else if(elements[1].subType==priceCriteriaDetailEntityList[i].subType){
	    				elements[1].setValue(priceCriteriaDetailEntityList[i].fee)//设置费率
	    			}
	    		}
	    		
	    	}else if(valuationRecord.get('pricingEntryCode')==pricing.expressRegionValueAdded.receivingCharges){//接货费
	    		var receivingChargesForm = windowShow.getDownPanel().getReceivingChargesFormPanel().getForm();
	    		var elements = receivingChargesForm.getFields( ).items;//获得form页面元素，一个体积，一个重量
	    		receivingChargesForm.findField('minFee').setValue(priceCriteriaDetailEntityList[0].minFee);//设置最低收费
	    		receivingChargesForm.findField('maxFee').setValue(priceCriteriaDetailEntityList[0].maxFee);//设置最高收费
				for(var i=0;i<priceCriteriaDetailEntityList.length;i++){
				    if(elements[0].CACULATE_TYPE==priceCriteriaDetailEntityList[i].caculateType){
						elements[0].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
					}else if(elements[1].CACULATE_TYPE==priceCriteriaDetailEntityList[i].caculateType){
						elements[1].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
					}else if(elements[2].CACULATE_TYPE==priceCriteriaDetailEntityList[i].caculateType){
						elements[2].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
					}else if(elements[3].CACULATE_TYPE==priceCriteriaDetailEntityList[i].caculateType){
						elements[3].setValue(priceCriteriaDetailEntityList[i].feeRate/100)//设置费率
					}
				}
		    }
	    	windowShow.show();
	    };
	    var failureFun = function(json){
			if(Ext.isEmpty(json)){
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var url = pricing.realPath('searchCriteriaDetailByIdExpress.action');
		pricing.requestJsonAjax(url,params,successFun,failureFun);
};
//editForm已经getForm()之后的结果
//me就是当前台处的window
//新增区域增值服务的方法（组织数据，发送请求，成功方法，失败方法和校验）
pricing.addValueAddedRegionExpress = function(editForm,me){
	var priceValuationEntity = new Foss.pricing.expressRegionValueAdded.PriceValuationEntityModel();//创建计费规则的MODEL
	editForm.updateRecord(priceValuationEntity);//将上半部分数据设置到创建计费规则的MODEL中
	var pricingEntryId = editForm.findField('pricingEntryCode').pricingEntryId;//增值服务类型处获取计价条目ID
	priceValuationEntity.set('pricingEntryId',pricingEntryId);//将几家条目ID设置到MODEL中
	priceValuationEntity.set('type',pricing.expressRegionValueAdded.VALUATION_TYPE_expressRegionValueAdded);//设置增值服务类型为区域增值服务
	var arrvRegionId = editForm.findField('arrvRegionName').regionId;//目的地（区域）ID
	var deptRegionId = editForm.findField('deptRegionName').regionId;//始发地（区域）ID
	
	//如果基础产品选择快件包裹的话,始发、目的区域可以不填写
//	var productRecord = editForm.findField('productId').productRecord;
//	if(productRecord!=null&&productRecord!=''){
//		var productCode = productRecord.get('code');
//		if(productCode!=null&&productCode!=''){
//			if(productCode!=pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE&&productCode!=pricing.expressRegionValueAdded.PRODUCTCODE_RCP){
//				if(Ext.isEmpty(arrvRegionId)&&Ext.isEmpty(deptRegionId)){//目的地（区域）和 始发地（区域）必填其一
//					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.destinationAndOriginSelectOne'));//目的地（区域）和 始发地（区域）必填其一
//					return ;
//				}
//			}
//		}
//	}
	
	var productCode = editForm.findField('productId').getValue();
	
	if(Ext.isEmpty(deptRegionId)){
		deptRegionId = pricing.expressRegionValueAdded.ALL;
	}
	if(Ext.isEmpty(arrvRegionId)){
		arrvRegionId = pricing.expressRegionValueAdded.ALL;
	}
	priceValuationEntity.set('arrvRegionId',arrvRegionId);//设置目的地（区域）ID
	priceValuationEntity.set('deptRegionId',deptRegionId);//设置始发地（区域）ID
	priceValuationEntity.set('productCode',productCode);//产品code
	priceValuationEntity.set('productId',productCode);//产品code
//	if(!Ext.isEmpty(editForm.findField('productId').productRecord)){
//		priceValuationEntity.set('productCode',editForm.findField('productId').productRecord.get('code'));//产品code
//	}
	if(!Ext.isEmpty(editForm.findField('goodsTypeId').goodsTypeRecord)){
		priceValuationEntity.set('goodsTypeCode',editForm.findField('goodsTypeId').goodsTypeRecord.get('code'));//货物类型
	}
	var priceCriteriaDetailEntityList = new Array();
	var pricingEntryCode = priceValuationEntity.get('pricingEntryCode');//得到计价条目CODE
    if(pricingEntryCode==pricing.expressRegionValueAdded.insurance){//保费
//    	var insuranceForm = me.getDownPanel().getInsuranceGridPanel().getForm();
//    	var priceCriteriaDetailEntity= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细
//    	if(insuranceForm.isValid()){
//    		insuranceForm.updateRecord(priceCriteriaDetailEntity);//设置计价方式明细的值
//    		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
//				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.minimumPremiumGreaterThanMaximumCoverageForMinimumInsurance'));//代收货款中有一条数据，最低一票大于最高一票！
//				return ;
//			}
//    		priceCriteriaDetailEntity.set('caculateType',pricing.expressRegionValueAdded.originalCost);
//    		priceCriteriaDetailEntity.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);
//    		priceCriteriaDetailEntity.set('leftrange',0);
//    	}else{
//    		return;
//    	}
//    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);
    	
    	var insuranceGrid = me.getDownPanel().getInsuranceGridPanel();
    	if(insuranceGrid.getStore().getCount()>0){
			var isNotValid = false;
			
			//获取全部数据列表
			var datas1 = insuranceGrid.getStore().data.items;
			var datas2 = insuranceGrid.getStore().data.items;
			
			//验证所有的限保物品必须是同一种类
			var subTypeTemp = datas1[0].data.subType;
			for(var i=0;i<datas1.length;i++){
				if(subTypeTemp!=null&&subTypeTemp!=''){
					if(subTypeTemp!=datas1[i].data.subType){
						pricing.showWoringMessage('限保物品不一致');//限保物品不一致
						isNotValid = true;
						return ;
					}
				}else{
					if(datas1[i].data.subType!=null&&datas1[i].data.subType!=''){
						pricing.showWoringMessage('限保物品不一致');//限保物品不一致
						isNotValid = true;
						return ;
					}
				}
			}
			
			
			//循环，验证时候有重复区域数据
			for(var i=0;i<datas1.length;i++){
				for(var j=0;j<datas2.length;j++){
					
					//不能存在重复区域
					if(datas2[j].data.id!=datas1[i].data.id){
						if(datas2[j].data.leftrange>=datas1[i].data.leftrange
								&&datas2[j].data.leftrange<datas1[i].data.rightrange){
							pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
							isNotValid = true;
							return ;
						}
						if(datas2[j].data.rightrange>datas1[i].data.leftrange
								&&datas2[j].data.rightrange<datas1[i].data.rightrange){
							pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
							isNotValid = true;
							return ;
						}
					}
					
					
//					//如果两者都有限保物品类型
//					if((datas2[j].data.subType!=null&&datas2[j].data.subType!='')
//						&&(datas1[i].data.subType!=null&&datas1[i].data.subType!='')){
//						if(datas2[j].data.id!=datas1[i].data.id
//								&&datas2[j].data.subType==datas1[i].data.subType){
//							if(datas2[j].data.leftrange>=datas1[i].data.leftrange
//									&&datas2[j].data.leftrange<datas1[i].data.rightrange){
//								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//								isNotValid = true;
//								return ;
//							}
//							if(datas2[j].data.rightrange>datas1[i].data.leftrange
//									&&datas2[j].data.rightrange<datas1[i].data.rightrange){
//								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//								isNotValid = true;
//								return ;
//							}
//						}
//					}
//					//如果两者都没有限保物品类型
//					if((datas2[j].data.subType==null||datas2[j].data.subType=='')
//						&&(datas1[i].data.subType==null||datas1[i].data.subType=='')){
//						if(datas2[j].data.id!=datas1[i].data.id){
//							if(datas2[j].data.leftrange>=datas1[i].data.leftrange
//									&&datas2[j].data.leftrange<datas1[i].data.rightrange){
//								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//								isNotValid = true;
//								return ;
//							}
//							if(datas2[j].data.rightrange>datas1[i].data.leftrange
//									&&datas2[j].data.rightrange<datas1[i].data.rightrange){
//								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//								isNotValid = true;
//								return ;
//							}
//						}
//					}
				}
			};
		
			insuranceGrid.getStore().each(function(record){
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.minInsuranceFeeIsNotMoreMaxInsuranceFee'));//最低保额不能大于最高保额
					isNotValid = true;
					return ;
				}
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueBeginNotHighEnd'));//最低保险价值不能高于最高保险价值
					isNotValid = true;
					return ;
				}
				if(record.get('rightrange')<1000&&record.get('maxFee')!=1){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.MaxValuelessThousandAlert'));//保险价值低于1000元时,最高保额为1元
					isNotValid = true;
					return ;
				}
				record.set('id',null);
				record.set('description',null);
				record.set('leftrange',record.get('leftrange')*100);
				record.set('rightrange',record.get('rightrange')*100);
				record.set('caculateType',pricing.expressRegionValueAdded.originalCost);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));
			return;
		}
    	
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.paymentCollection){//代收货款
		var paymentCollectionGrid = me.getDownPanel().getPaymentCollectionGridPanel();
		if(paymentCollectionGrid.getStore().getCount()>0){
			var isNotValid = false;
			paymentCollectionGrid.getStore().each(function(record){
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.collectionMoneyDataLowestVotesGreaterThanMaximumVote'));//代收货款中有一条数据，最低一票大于最高一票！
					isNotValid = true;
					return ;
				}
				record.set('leftrange',record.get('leftrange')*100);
				record.set('rightrange',record.get('rightrange')*100);
				record.set('caculateType',pricing.expressRegionValueAdded.originalCost);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));
			return;
		}
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.superDelivery){//超远派送
		var superDeliveryGrid = me.getDownPanel().getSuperDeliveryGridPanel();
		if(superDeliveryGrid.getStore().getCount()>0){
			var isNotValid = false;
			superDeliveryGrid.getStore().each(function(record){
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.superDataDeliveryStartingPointGreaterEndOf'));//超远派送中有一条数据，起点大于终点！
					isNotValid = true;
					return ;
				}
				record.set('leftrange',record.get('leftrange'));
				record.set('rightrange',record.get('rightrange'));
				record.set('caculateType',pricing.expressRegionValueAdded.kilometer);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseMaintainUltraFarDeliveryValueAddedServicesBasicData'));
			return;
		}
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.deliveryUpstairs){//送货上楼
		var deliveryUpstairsGrid = me.getDownPanel().getDeliveryUpstairsGridPanel();
		if(deliveryUpstairsGrid.getStore().getCount()>0){
			var isNotValid = false;
			deliveryUpstairsGrid.getStore().each(function(record){
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.dataDeliveryUpstairsStartingPointGreaterThanTheEndOf'));//送货上楼中有一条数据，起点大于终点！
					isNotValid = true;
					return ;
				}
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage('最低保险价值不能高于最高保险价值');//送货上楼中有一条数据，起点大于终点！
					isNotValid = true;
					return ;
				}
				record.set('leftrange',record.get('leftrange'));
				record.set('rightrange',record.get('rightrange'));
				record.set('feeRate',record.get('feeRate')*100);
				record.set('caculateType',pricing.expressRegionValueAdded.Weight);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.maintenanceDeliveryTheUpstairsValueAddedServicesUnderlyingData'));
			return;
		}
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.deliveryCharges){//送货费
		var deliveryChargesGrid = me.getDownPanel().getDeliveryChargesGridPanel();
		if(deliveryChargesGrid.getStore().getCount()>0){
			var isNotValid = false;
			deliveryChargesGrid.getStore().each(function(record){
				if(record.get('leftrange')>record.get('rightrange')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.dataDeliveryChargeStartingPointGreaterThanEndOf'));//送货费中有一条数据，起点大于终点！
					isNotValid = true;
					return ;
				}
				record.set('feeRate',record.get('feeRate')*100);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.maintenanceDeliveryChargeValue-addedServiceBasicData'));
			return;
		}
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.receivingCharges){//接货费
		var receivingChargesForm = me.getDownPanel().getReceivingChargesFormPanel().getForm();
		var priceCriteriaDetailEntityWeight= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-按重量
		var priceCriteriaDetailEntityVolume= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-按体积
    	if(receivingChargesForm.isValid()){
    		var minFee = receivingChargesForm.findField('minFee').getValue();//得到最低收费
    		var maxFee = receivingChargesForm.findField('maxFee').getValue();//得到最高收费
    		var elements = receivingChargesForm.getFields( ).items;//获得费率的两个页面元素，一个按体积，一个按重量
    		var weightFeeRate = null;//按重量的费率
    		var volumeFeeRate = null;//按体积的费率
    		for(var i = 0;i<elements.length;i++){
    			if(elements[i].CACULATE_TYPE==pricing.expressRegionValueAdded.Weight){//表明是按重量
    				weightFeeRate = elements[i].getValue()*100;
    				priceCriteriaDetailEntityWeight.set('feeRate',weightFeeRate);//设置费率
                	priceCriteriaDetailEntityWeight.set('caculateType',pricing.expressRegionValueAdded.Weight);//设置计费类型
                	priceCriteriaDetailEntityWeight.set('minFee',minFee);//设置最低费
                	priceCriteriaDetailEntityWeight.set('maxFee',maxFee);//设置最高费
    			}else if(elements[i].CACULATE_TYPE==pricing.expressRegionValueAdded.Volume){//表明是按体积
    				volumeFeeRate = elements[i].getValue()*100;
    				priceCriteriaDetailEntityVolume.set('feeRate',volumeFeeRate);//设置费率
    				priceCriteriaDetailEntityVolume.set('caculateType',pricing.expressRegionValueAdded.Volume);//设置计费类型
    				priceCriteriaDetailEntityVolume.set('minFee',minFee);//设置最低费
    				priceCriteriaDetailEntityVolume.set('maxFee',maxFee);//设置最高费
    			}
    		}
    	}else{
    		return;
    	}
    	priceCriteriaDetailEntityWeight.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_WEIGHT_MAX);//设置按重量的右区间
    	priceCriteriaDetailEntityWeight.set('leftrange',0);//设置按重量的左区间
    	priceCriteriaDetailEntityVolume.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_VOLUME_MAX);//设置按体积的右区间
    	priceCriteriaDetailEntityVolume.set('leftrange',0);//设置按体积的左区间
    	//生成两条数据
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityWeight.data);//生成两条数据
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityVolume.data);
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.sign){//签收回单
		var signForm = me.getDownPanel().getSignFormPanel().getForm();//得到FORM表单
		var priceCriteriaDetailEntityYJ= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-原件返单
		var priceCriteriaDetailEntityCZ= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-传真返单
    	if(signForm.isValid()){
    		var feeRateYJ = null;//原件费率
    		var feeRateCZ = null;//传真费率
    		var elements = signForm.getFields( ).items;//获得FORM页面元素，一个原件返单，一个传真返单
    		for(var i = 0;i<elements.length;i++){
    			if(elements[i].subType==pricing.expressRegionValueAdded.YJFD){//表明是原件返单
    				feeRateYJ = elements[i].getValue();
    				priceCriteriaDetailEntityYJ.set('fee',feeRateYJ);//设置费率
    				priceCriteriaDetailEntityYJ.set('subType',pricing.expressRegionValueAdded.YJFD);//子服务类型是原件返单
    			}else if(elements[i].subType==pricing.expressRegionValueAdded.CZFD){//表明是传真返单
    				feeRateCZ = elements[i].getValue();
    				priceCriteriaDetailEntityCZ.set('fee',feeRateCZ);//设置费率
    				priceCriteriaDetailEntityCZ.set('subType',pricing.expressRegionValueAdded.CZFD);//子服务类型是传真返单
    			}
    		}
    	}else{
    		return;
    	}
    	priceCriteriaDetailEntityYJ.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);//设置按原始费用计费的左区间
    	priceCriteriaDetailEntityYJ.set('leftrange',0);//设置按原始费用计费的左区间
    	priceCriteriaDetailEntityYJ.set('caculateType',pricing.expressRegionValueAdded.originalCost);
    	priceCriteriaDetailEntityCZ.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);
    	priceCriteriaDetailEntityCZ.set('leftrange',0);
    	priceCriteriaDetailEntityCZ.set('caculateType',pricing.expressRegionValueAdded.originalCost);
    	//生成两条数据
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityYJ.data);//生成两条数据
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityCZ.data);
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.packing){//包装费
		var packingForm = me.getDownPanel().getPackingFormPanel().getForm();//获得form页面元素
		var priceCriteriaDetailEntityBOX= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-木箱
		var priceCriteriaDetailEntityFRAME= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细-木架
		if(packingForm.isValid()){
			var feeRateBOX = null;//木箱费率
			var minFeeBOX = null;//木箱最低一票
			var feeRateFRAME = null;//木架费率
			var minFeeFRAME = null;//木架最低一票
			var elements = packingForm.getFields( ).items;//获得FORM页面元素，一个原件返单，一个传真返单
			feeRateFRAME = elements[0].getValue();
			minFeeFRAME =  elements[1].getValue();
			feeRateBOX = elements[2].getValue();
			minFeeBOX = elements[3].getValue();
		}else{
			return;
		}
		priceCriteriaDetailEntityBOX.set('feeRate',feeRateBOX*100);//乘100//@TODO
		priceCriteriaDetailEntityBOX.set('minFee',minFeeBOX);
		priceCriteriaDetailEntityFRAME.set('feeRate',feeRateFRAME*100);//乘100
		priceCriteriaDetailEntityFRAME.set('minFee',minFeeFRAME);
		priceCriteriaDetailEntityFRAME.set('subType',pricing.expressRegionValueAdded.PACKAGE_TYPE__FRAME);//子服务类型是木架
		priceCriteriaDetailEntityBOX.set('subType',pricing.expressRegionValueAdded.PACKAGE_TYPE__BOX);//子服务类型是木箱
		priceCriteriaDetailEntityBOX.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_VOLUME_MAX);//设置按原始费用计费的左区间
		priceCriteriaDetailEntityBOX.set('leftrange',0);//设置按原始费用计费的左区间
		priceCriteriaDetailEntityBOX.set('caculateType',pricing.expressRegionValueAdded.Volume);
		priceCriteriaDetailEntityFRAME.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_VOLUME_MAX);//设置按原始费用计费的左区间
		priceCriteriaDetailEntityFRAME.set('leftrange',0);//设置按原始费用计费的左区间
		priceCriteriaDetailEntityFRAME.set('caculateType',pricing.expressRegionValueAdded.Volume);
		
		priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityBOX.data);
		priceCriteriaDetailEntityList.push(priceCriteriaDetailEntityFRAME.data);
	}else if(pricingEntryCode==pricing.expressRegionValueAdded.storageCharges){//仓储费
		var storageChargesForm = me.getDownPanel().getStorageChargesFormPanel().getForm();
		var priceCriteriaDetailEntity= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity();//计价方式明细
    	if(storageChargesForm.isValid()){
    		storageChargesForm.updateRecord(priceCriteriaDetailEntity);
    		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.storageChargesMinimumChargeGreaterThanMaximumCharge'));//仓储费中，最低收取大于最高收取！
				return ;
			}
    	}else{
    		return;
    	}
    	priceCriteriaDetailEntity.set('feeRate',priceCriteriaDetailEntity.get('feeRate')*100);//费率的单位是元/m³/天，要换算成分，所以乘100
    	priceCriteriaDetailEntity.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);//设置按原始费用计费的左区间
    	priceCriteriaDetailEntity.set('leftrange',0);//设置按原始费用计费的左区间
    	priceCriteriaDetailEntity.set('caculateType',pricing.expressRegionValueAdded.originalCost);
    	priceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);

	}else if(pricingEntryCode==pricing.expressRegionValueAdded.otherCharges){//其它费用（）！！！subType不是随便填写的
		var otherChargesGrid = me.getDownPanel().getOtherChargesGridPanel();
		if(otherChargesGrid.getStore().getCount()>0){
			var isNotValid = false;
			otherChargesGrid.getStore().each(function(record){
				if(record.get('minFee')>record.get('maxFee')){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.otherCostsDataAmountLowerLimitGreaterMaximumAmount'));//其它费用中有一条数据，金额下限大于金额上限！
					isNotValid = true;
					return ;
				}
				record.set('feeRate',record.get('feeRate')*100);
				record.set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);//设置按原始费用计费的左区间
				record.set('leftrange',0);//设置按原始费用计费的左区间
				record.set('caculateType',pricing.expressRegionValueAdded.originalCost);
				priceCriteriaDetailEntityList.push(record.data);
			});
			if(isNotValid){
				return;
			}
		}else{
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseMaintainBasicDataOtherValueAddedServices'));
			return;
		}
	}
    var params = {'pricingValuationVo':{'priceCriteriaDetailEntityList':priceCriteriaDetailEntityList
    	,'priceValuationEntity':priceValuationEntity.data}};//组装数据
    var successFun = function(json){
    	pricing.showInfoMes(json.message);//提示信息
    	var grid  = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getPricingValuationGrid();
		grid.getPagingToolbar().moveFirst();
    	me.hide();
    };//成功之后提示成功，隐藏现在界面
    var failureFun = function(json){
    	if(Ext.isEmpty(json)){
			pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
		}else{
			pricing.showErrorMes(json.message);
		}
    };
    var url = pricing.realPath('addValueAddedExpress.action');//新增区域增值服务
    pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
}
//------------------------------------pricing----------------------------------
//计价条目MODEL
Ext.define('Foss.pricing.expressRegionValueAdded.PriceEntityModel', {
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
        name : 'remarks',//描述
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
Ext.define('Foss.pricing.expressRegionValueAdded.PriceValuationEntityModel', {
    extend: 'Ext.data.Model',
    fields : [{
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
        name : 'goodsTypeName',//货物类型名称
        type : 'string'
    },{
        name : 'goodsTypeCode',//货物类型CODE
        type : 'string'
    },{
        name : 'productId',//产品ID
        type : 'string'
    },{
        name : 'productCode',//产品CODE
        type : 'string'
    },{
        name : 'deptRegionId', //始发区域ID
        type : 'string'
    },{
        name : 'deptRegionName', //始发区域NAME
        type : 'string'
    },{
        name : 'arrvRegionId',//目的区域ID
        type : 'string'
    },{
        name : 'arrvRegionName',//目的区域NAME
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
        name : 'remarks',   //描述
        type : 'string'
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
    	name : 'currentUsedVersion',//是否最新版本
    	type : 'string'
    }]
});
//计价方式明细MODEL
Ext.define('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
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
        name : 'fee',//固定费用
        	 defaultValue : null
    },{
        name : 'feeRate',
        defaultValue : null//费率或者单价
    },{
        name : 'leftrange',
        defaultValue : null//计价左区间
    },{
        name : 'rightrange',
        defaultValue : null//计价右区间
    },{
        name : 'minFee',//最低费用
        	 defaultValue : null
    },{
        name : 'maxFee',//最高费用
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
    }]
});
//基础产品明细MODEL
Ext.define('Foss.pricing.expressRegionValueAdded.ProductEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',//id
        type : 'string'
    },{
        name : 'code',//code
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    },{
        name : 'transportType',//运输类型
        type : 'string'
    },{
        name : 'levels'//产品等级
    },{
        name : 'parentCode',//父产品CODE
        type : 'string'
    },{
        name : 'refId',
        type : 'string'
    },{
        name : 'shortName',//简称
        type : 'string'
    },{
        name : 'priority',
        type : 'string'
    }]
});
//货物类型MODEL
Ext.define('Foss.pricing.expressRegionValueAdded.GoodsTypeEntity', {
    extend: 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'//id
    },{
        name : 'code',//编号
        type : 'string'
    },{
        name : 'name',//名称
        type : 'string'
    },{
        name : 'active',//是否激活
        type : 'string'
    },{
        name : 'description',//描述
        type : 'string'
    }]
});
/**
 * 区域MODEL
 */
Ext.define('Foss.pricing.expressRegionValueAdded.AreaModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'string'
	},{
		name : 'regionCode',// 区域编码
		type : 'string'
	},  {
		name : 'regionName',// 区域名称
		type : 'string'
	}, {
		name : 'regionType',// 区域类型
		type : 'string'
	}, {
		name : 'regionNature',// 区域性质
		type : 'string'
	}, {
		name : 'active',// 区域状态
		type : 'string'
	}, {
		name : 'beginTime',// 开始时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'endTime',// 结束时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'description',// 描述
		type : 'string'
	}, {
		name : 'nationCode',// 国家编号
		type : 'string'
	},{
		name : 'nationName',// 国家名称
		type : 'string'
	}, {
		name : 'proCode',// 省份编号
		type : 'string'
	},{
		name : 'proName',// 省份编号
		type : 'string'
	}, {
		name : 'cityCode',// 市编号
		type : 'string'
	},{
		name : 'cityName',// 市名称
		type : 'string'
	}, {
		name : 'countyCode',// 区县编号
		type : 'string'
	},{
		name : 'countyName',// 区县名称
		type : 'string'
	}, {
		name : 'modifyUserName',//修改人姓名
		type : 'string'
	}, {
		name : 'createUserName',//创建人姓名
		type : 'string'
	}, {
		name : 'createDate',//创建时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'modifyDate',//修改时间
		type: 'Date',
		defaultValue : null,
		 convert:pricing.changeLongToDate
	}, {
		name : 'createUser',//创建人
		type : 'string'
	}, {
		name : 'modifyUser',//修改人
		type : 'string'
	}]
});

//------------------------------------model----------------------------------


/**
 * 区域Store
 */
Ext.define('Foss.pricing.expressRegionValueAdded.PricingValuationStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.expressRegionValueAdded.PriceValuationEntityModel',//引用MODEL
	pageSize:20,//每页显示20条
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchPricingValuationExpress.action'),//请求增值服务URL
		reader : {
			type : 'json',
			root : 'pricingValuationVo.priceValuationEntityList',//得到的结果
			totalProperty : 'totalCount'
		}
	}
});

Ext.define('Foss.pricing.expressRegionValueAdded.TransportTypeStore', {
	extend : 'Ext.data.Store',
	fields : [{
			name : 'valueCode',
			type : 'string'
		}, {
			name : 'valueName',
			type : 'string'
		}
	],
	proxy : {
		type : 'memory',
		reader : {
			type : 'json',
			root : 'items' //定义读取JSON数据的根对象
		}
	},
	constructor : function (config) {
		var me = this,
		cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//------------------------------------store----------------------------------
/**
 * 区域增值服务查询表单
 */
Ext.define('Foss.pricing.expressRegionValueAdded.QueryexpressRegionValueAddedForm', {
	extend : 'Ext.form.Panel',
	title: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	margin : '8 10 5 10',
     	labelWidth:90
    },
    layout: 'auto',
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {//构造器
		var me = this, 
			cfg = Ext.apply({}, config);
		var valueAddedType = new Array();
		var productEntityList = new Array();
		for(var i = 0;i<pricing.expressRegionValueAdded.valueAddedType.length;i++){
			valueAddedType.push(pricing.expressRegionValueAdded.valueAddedType[i]);
		}
		valueAddedType.push({'code':pricing.expressRegionValueAdded.ALL,'name':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.all')});

		for(var i = 0;i<pricing.expressRegionValueAdded.productEntityList.length;i++){
			productEntityList.push(pricing.expressRegionValueAdded.productEntityList[i]);
		}
		productEntityList.push({'code':pricing.expressRegionValueAdded.ALL,'name':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.all')});

		me.items = [{
			name: 'pricingEntryName',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    value:pricing.expressRegionValueAdded.ALL,
		    store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceEntityModel',null
		    ,valueAddedType),
	        fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//所属增值服务
	        xtype : 'combo',
	        columnWidth:.3
		},{
			xtype:'datefield',
			fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.businessDate'),//业务日期
			format:'Y-m-d',
			name:'beginTime',
			columnWidth:.3
		},{
			xtype: 'displayfield',
			columnWidth : .3,
			value:pricing.expressRegionValueAdded.i18n('foss.pricing.PAccordingValueAddedServicesBusinessDatesRangeClosingDate')//根据业务日期查询介于生效日期和截止日期之间的增值服务
		},{
			name: 'startExpressRegionId',
			valueField: 'id',
		    fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.originatingArea'),//始发区域
		    xtype : 'commonexpresspriceregionselector',
		    columnWidth:.3
		},{
			name: 'arrvExpressRegionId',
			valueField: 'id',
		    fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.destinationArea'),//始发区域
		    xtype : 'commonexpresspriceregionselector',
		    columnWidth:.3
		},{
		    xtype:'combobox',
			displayField:'name',
			valueField:'code',
			queryMode:'local',
			triggerAction:'all',
			editable:true,
			fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.basicProducts'),//基础产品
			name:'productCode',
			columnWidth:.3,
			store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.ProductEntity',null
					    ,productEntityList),
			value:pricing.expressRegionValueAdded.ALL
		},{
			xtype : 'container',
			columnWidth : 1,
			layout:{
				type :'column',
				columns :2
			},
			items : [{
				xtype:'container',
				border:false,
				html:'&nbsp;',
				columnWidth:.8
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.1,
				cls:'yellow_button',
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.search'),//查询按钮
				disabled: !pricing.expressRegionValueAdded.isPermission('indexRegionValuation/indexRegionValuationQueryButton'),
				hidden: !pricing.expressRegionValueAdded.isPermission('indexRegionValuation/indexRegionValuationQueryButton'),
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getPricingValuationGrid();//得大grid
						grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});

/**
 * 区域增值服务列表
 */
Ext.define('Foss.pricing.expressRegionValueAdded.PricingRegionValuationGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.searchResults'),
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
    autoScroll:true,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	emptyText: pricing.expressRegionValueAdded.i18n('foss.pricing.theQueryResultIsEmpty'),//查询结果为空
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
	//新增区域增值服务
    expressRegionValueAddedWindow:null,
	getexpressRegionValueAddedWindow:function(){
		if (Ext.isEmpty(this.expressRegionValueAddedWindow)) {
			this.expressRegionValueAddedWindow = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedWindow');
		}
		return this.expressRegionValueAddedWindow;
	},
	//查看区域增值服务详情WINDOW
	expressRegionValueAddedShowWindow:null,
	getexpressRegionValueAddedShowWindow:function(){
		if (Ext.isEmpty(this.expressRegionValueAddedShowWindow)) {
			this.expressRegionValueAddedShowWindow = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedShowWindow');
		}
		return this.expressRegionValueAddedShowWindow;
	},
	//用于修改弹出的WINDOW
	expressRegionValueAddedUpdateWindow:null,
	getexpressRegionValueAddedUpdateWindow:function(){
		if (Ext.isEmpty(this.expressRegionValueAddedUpdateWindow)) {
			this.expressRegionValueAddedUpdateWindow = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedUpdateWindow');
		}
		return this.expressRegionValueAddedUpdateWindow;
	},
	//新增区域增值服务
	addexpressRegionValueAdded:function(){
		var me = this;
		me.getexpressRegionValueAddedWindow().show();//显示新增弹窗
	},
	//终止增值服务WINDOW
	expressRegionValueAddedEndTimeWindow:null,
	getexpressRegionValueAddedEndTimeWindow:function(){
		if (Ext.isEmpty(this.expressRegionValueAddedEndTimeWindow)) {
			this.expressRegionValueAddedEndTimeWindow = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEndTimeWindow');
			this.expressRegionValueAddedEndTimeWindow.parent = this;
		}
		return this.expressRegionValueAddedEndTimeWindow;
	},
	//激活增值服务
	activeValueAdded:function(){
		var me = this;
		var valuationIds = new Array();
		//获取选中的数据
		var selections = me.getSelectionModel().getSelection();
		if(selections.length<1){
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectValueAddedServicesActivated'));//请选择要激活的增值服务！
			return;
		}
		var today = new Date(pricing.expressRegionValueAdded.tomorrowTime);
		today = today.setDate(today.getDate()-1);//得到当天的0点00分
		for(var i = 0 ; i<selections.length ; i++){
			//只有未被激活的区域的ID才会传到后台
			if(selections[i].get('active')=='N'){
				if(selections[i].get('beginTime').getTime()<=today){//过期的数据不能激活
					pricing.showWoringMessage('名称：'+selections[i].get('name')//'名称：
							+'编码：'+selections[i].get('code')//编码：
							+pricing.expressRegionValueAdded.i18n('foss.pricing.regionalValueAddedServicesExpiredNotActivate'));//的区域增值服务已经过期，不可激活！
					return;
				}else{
					valuationIds.push(selections[i].get('id'));
				}
			}
		}
		if(valuationIds.length<1){
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectAtLeastOneInactiveValueAddedServices'));//请至少选择一条未激活的增值服务！
			return;
		}
		pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.doYouWantActivateInactiveRegionalValueAddedServices'),function(e){//是否要激活这些未激活的区域增值服务？
			if(e=='yes'){//询问是否激活，是则发送请求
				var params = {'pricingValuationVo':{'valuationIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('activeValueAddedExpress.action');
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
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseChooseCeleteNotActivatedValueAddedServices'));//请选择要激活的增值服务！
			return;
		}
		for(var i = 0 ; i<selections.length ; i++){
			if(selections[i].get('active')=='Y'){//只有未激活的增值服务才可以删除
				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseChooseCeleteNotActivatedValueAddedServices'));//请选择要删除的未激活增值服务！
				return;
			}else if(selections[i].get('active')=='N'){
				valuationIds.push(selections[i].get('id'));
			}
		}
		pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteTheseValueAddedServicesNotActivated'),function(e){//是否要删除这些未激活的增值服务？
			if(e=='yes'){//询问是否删除，是则发送请求
				var params = {'pricingValuationVo':{'valuationIds':valuationIds}};
				var successFun = function(json){
					pricing.showInfoMes(json.message);
					me.getPagingToolbar().moveFirst();
				};
				var failureFun = function(json){
					if(Ext.isEmpty(json)){
						pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
					}else{
						pricing.showErrorMes(json.message);
					}
				};
				var url = pricing.realPath('deleteValueAddedExpress.action');
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
			this.immediatelyActiveWindow = Ext.create('Foss.pricing.expressRegionValueAdded.ImmediatelyActiveTimeWindow');
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
			this.immediatelyStopWindow = Ext.create('Foss.pricing.expressRegionValueAdded.ImmediatelyStopEndTimeWindow');
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
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.num')
		},{
			align : 'center',
			xtype : 'actioncolumn',
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			items: [{
				iconCls: 'deppon_icons_edit',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.update'),//修改
                //disabled:!pricing.expressRegionValueAdded.isPermission('pricing/updateValueAddedExpress'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'N'&&pricing.expressRegionValueAdded.isPermission('pricing/updateValueAddedExpress')) {//未激活的显示
					    return 'deppon_icons_edit';
					} else {
					    return 'statementBill_hide';//隐藏图标
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var grid = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getPricingValuationGrid();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getexpressRegionValueAddedUpdateWindow();
                	pricing.requestAndSetInfoRegion(valuationRecord,windowShow,true);
                }
			},{
				iconCls: 'deppon_icons_softwareUpgrade',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.upgradedVersion'),//升级版本
                //disabled:!pricing.expressRegionValueAdded.isPermission('pricing/upgradeValueAddedExpress'),
				width:42,
				getClass : function (v, m, r, rowIndex) {
					if (r.get('active') === 'Y'&&pricing.expressRegionValueAdded.isPermission('pricing/upgradeValueAddedExpress')) {//激活的显示
					    return 'deppon_icons_softwareUpgrade';
					} else {
					    return 'statementBill_hide';//隐藏图标
					}
				},
                handler: function(grid, rowIndex, colIndex) {
                	var grid = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getPricingValuationGrid();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getexpressRegionValueAddedUpdateWindow();
                	pricing.requestAndSetInfoRegion(valuationRecord,windowShow,true);
                }
			},{
				iconCls: 'deppon_icons_showdetail',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.details'),//查看详情
                disabled: !pricing.expressRegionValueAdded.isPermission('indexRegionValuation/indexRegionValuationQueryDetailButton'),
				width:42,
                handler: function(grid, rowIndex, colIndex) {
                	var grid = grid.up();
                	var valuationRecord = grid.getStore().getAt(rowIndex);;//选中的计费规则数据
                	var windowShow =  grid.getexpressRegionValueAddedShowWindow();
                	pricing.requestAndSetInfoRegion(valuationRecord,windowShow,false);
                }
			}]
		},{
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.scenarioName'),//方案名称
			dataIndex : 'name'
		},{
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//增值服务
			dataIndex : 'pricingEntryCode',
			renderer:function(value){
				for(var i = 0;i<pricing.expressRegionValueAdded.valueAddedType.length;i++){
					if(pricing.expressRegionValueAdded.valueAddedType[i].code == value){
						return pricing.expressRegionValueAdded.valueAddedType[i].name;
					}
				}
			}
		},{
			text :pricing.expressRegionValueAdded.i18n('foss.pricing.departureRegion'),//出发地区域
			dataIndex : 'deptRegionName'
		},{
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.availabilityDate'),//生效日期
			dataIndex : 'beginTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');
			}
		},{
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.deadline'),//截止日期
			dataIndex : 'endTime',
			renderer:function(value){
				return Ext.Date.format(new Date(value), 'Y-m-d H:i:s');;
			}
		},{
			text :pricing.expressRegionValueAdded.i18n('foss.pricing.destinationDiArea'),//出发地区域
			dataIndex : 'arrvRegionName'
		},{
			text :pricing.expressRegionValueAdded.i18n('foss.pricing.status'),//状态
			dataIndex : 'active',
			width:50,
			renderer:function(value){
				if(value=='Y'){//'Y'表示激活
					return pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.active');
				}else if(value=='N'){//'N'表示未激活
					return  pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.unActive');
				}else{
					return '';
				}
			}
		},{
			text : '是否当前版本',
			dataIndex : 'currentUsedVersion'
		}];
		me.store = Ext.create('Foss.pricing.expressRegionValueAdded.PricingValuationStore',{
			autoLoad : false,//自动加载
			pageSize : 20,//每页20条
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getQueryForm();//得到查询的FORM表单
					if(queryForm!=null){
						//var startRegionId = queryForm.getForm().findField('startRegionId').getValue();
						//var arrvRegionId = queryForm.getForm().findField('arrvRegionId').getValue();
						var startExpressRegionId = queryForm.getForm().findField('startExpressRegionId').getValue();
						var arrvExpressRegionId = queryForm.getForm().findField('arrvExpressRegionId').getValue();
						var productCode = queryForm.getForm().findField('productCode').getValue();
 //						var startRegion = null;
//						if(startRegionId!=null&&startRegionId!=''){
//							startRegion = startRegionId;
//						}else if(startExpressRegionId!=null&&startExpressRegionId!=''){
//							startRegion = startExpressRegionId;
//						}
//						
//						var arrvRegion = null;
//						if(arrvRegionId!=null&&arrvRegionId!=''){
//							arrvRegion = arrvRegionId;
//						}else if(arrvExpressRegionId!=null&&arrvExpressRegionId!=''){
//							arrvRegion = arrvExpressRegionId;
//						}
						
						Ext.apply(operation,{
							params : {
								//得到查询条件“服务类型”
								'pricingValuationVo.priceValuationEntity.pricingEntryCode' : 
									queryForm.getForm().findField('pricingEntryName').getValue(),
								//得到查询条件“业务日期”
								'pricingValuationVo.priceValuationEntity.beginTime' : 
									queryForm.getForm().findField('beginTime').getValue(),
									//默认查询条件，计费规则类型(区域增值服务)
								//默认查询条件，计费规则类型(区域增值服务)、快递区域增值服务
								'pricingValuationVo.priceValuationEntity.type' : 
									pricing.expressRegionValueAdded.VALUATION_TYPE_expressRegionValueAdded,
								'pricingValuationVo.priceValuationEntity.productCode' : 
									productCode,	
								'pricingValuationVo.priceValuationEntity.deptRegionId' : 
									startExpressRegionId,
								'pricingValuationVo.priceValuationEntity.arrvRegionId' : 
									arrvExpressRegionId
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
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/addValueAddedExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/addValueAddedExpress'),
			handler :function(){
				me.addexpressRegionValueAdded();
			} 
		},'-', {
			//激活
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.active'),
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/activeValueAddedExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/activeValueAddedExpress'),
			handler :function(){
				me.activeValueAdded();
			} 
		},'-', {
			//删除
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/deleteValueAddedExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/deleteValueAddedExpress'),
			handler :function(){
				me.deleteValueAdded();
			} 
		},'-',{
			//终止
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.termination'),
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/stopFastExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/stopFastExpress'),
			handler :function(){
				var selections = me.getSelectionModel().getSelection();
				if(selections.length!=1){
					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectActivationEffectiveDataTermination'));
					return;
				}else{
					var active = selections[0].get('active');
					var endTime = selections[0].get('endTime');
					var beginTime = selections[0].get('beginTime');
					if(active!='Y'||beginTime>=endTime){
						pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectActivationEffectiveDataTermination'));
						return;
					}
				}
				me.getexpressRegionValueAddedEndTimeWindow().show();
				me.getexpressRegionValueAddedEndTimeWindow().selection = selections[0];
			} 
		},'-', {
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.immediatelyActivationProgram'),//'立即激活',
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/immediatelyActiveValueAddedExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/immediatelyActiveValueAddedExpress'),
			handler :function(){
				me.immediatelyActive();
			} 
		},'-', {
			text : pricing.expressRegionValueAdded.i18n('foss.pricing.immediatelyStopProgram'),//'立即中止',
			disabled:!pricing.expressRegionValueAdded.isPermission('pricing/immediatelyStopFastExpress'),
			hidden:!pricing.expressRegionValueAdded.isPermission('pricing/immediatelyStopFastExpress'),
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
 * @description 区域增值服务主页面
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp('T_pricing-indexExpressRegionValuation_content')) {
		return;
	}
	pricing.searchValueAddedType();//查询增值服务类型
	pricing.searchOtherChargesSubType();//查询增值服务类型,其他费用（QT）的子类型
	pricing.haveServerNowTime();//获取服务当前时间
	pricing.searchProductEntityList();//获取基础产品
	pricing.queryLimitedWarrantyItemsByEntity();//查询限保物品
	pricing.searchGoodTypeList();//获取货物类型
	var queryForm  = Ext.create('Foss.pricing.expressRegionValueAdded.QueryexpressRegionValueAddedForm');//查询FORM
	var pricingValuationGrid  = Ext.create('Foss.pricing.expressRegionValueAdded.PricingRegionValuationGridPanel');//查询结果显示列表
	var setDate = Ext.create('Ext.form.field.Date');
	setDate.setValue(new Date(pricing.expressRegionValueAdded.tomorrowTime));//得到明天时间是明天的当前时间不是明天的零点零零分
	pricing.expressRegionValueAdded.tomorrowTime = setDate.getValue().getTime();//过一次datefield就变成零点零零分了
	Ext.getCmp('T_pricing-indexExpressRegionValuation').add(Ext.create('Ext.panel.Panel', {
	  	id : 'T_pricing-indexExpressRegionValuation_content',
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
 * 区域增值服务-查看详情
 */
Ext.define('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressRegionValueAdded.i18n('foss.pricing.regionalValueAddedServiceDetails'),//区域增值服务详情
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :850,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getEditForm().getForm().reset();
			me.getEditForm().getForm().findField('productId').productRecord = null;
			me.getEditForm().getForm().findField('goodsTypeId').goodsTypeRecord = null;
			me.getDownPanel().getInsuranceGridPanel().getStore().removeAll();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();//超远派送
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();//送货上楼
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getForm().reset();
			me.getDownPanel().getPackingFormPanel().getForm().reset();//包装费
			me.getDownPanel().getSignFormPanel().getForm().reset();
			me.getDownPanel().getStorageChargesFormPanel().getForm().reset();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			
		}
	},
	editForm:null,//区域增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEditFormPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
    	return this.editForm;
	},
	downPanel:null,//区域增值服务新增修改查看表单（下半部分）
	//下颁布PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.expressRegionValueAdded.RegionDownPanel',{
    			'isReadOnly':true//设置是否只读
    		});
    	}
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
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 区域增值服务-修改
 */
Ext.define('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedUpdateWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressRegionValueAdded.i18n('foss.pricing.modifyTheRegionalValueAddedServices'),//修改区域增值服务
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :850,
	valuationRecord:null,//计费规则的record
	priceCriteriaDetailEntityList:null,//计价方式明细LIST
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getEditForm().getForm().reset();
			me.getEditForm().getForm().findField('productId').productRecord = null;
			me.getEditForm().getForm().findField('goodsTypeId').goodsTypeRecord = null;
			me.getDownPanel().getInsuranceGridPanel().getStore().removeAll();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();//超远派送
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();//送货上楼
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getForm().reset();
			me.getDownPanel().getSignFormPanel().getForm().reset();
			me.getDownPanel().getStorageChargesFormPanel().getForm().reset();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
		},
		beforeshow:function(me){
			
		}
	},
	editForm:null,//区域增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEditFormPanel',{
    			'isReadOnly':false//设置是否只读
    		});
    		this.editForm.getForm().findField('pricingEntryCode').setReadOnly(true);
    		this.editForm.getForm().findField('productId').setReadOnly(true);
//    		this.editForm.getForm().findField('active').items.items[0].setReadOnly(true);
//    		this.editForm.getForm().findField('active').items.items[1].setReadOnly(true);
    	}
    	return this.editForm;
	},
	downPanel:null,//区域增值服务新增修改查看表单（下半部分）
	//下半部PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.expressRegionValueAdded.RegionDownPanel',{
    			'isReadOnly':false//设置是否只读
    		});
    	}
    	return this.downPanel;
	},
	regionWindow:null,//选择区域弹窗
	getRegionWindow:function(){
		if(Ext.isEmpty(this.regionWindow)){
    		this.regionWindow = Ext.create('Foss.pricing.expressRegionValueAdded.RegionGridShowWindow');
    	}
    	return this.regionWindow;
	},
	getExpressRegionWindow:function(){
		if(Ext.isEmpty(this.expressRegionWindow)){
    		this.expressRegionWindow = Ext.create('Foss.pricing.expressRegionValueAdded.ExpressRegionGridShowWindow');
    	}
    	return this.expressRegionWindow;
	},
	//返回列别按钮
	returnGrid:function(){
		var me = this;
		me.close();
	},
    //提交数据
    commintexpressRegionValueAdded:function(){
    	var me = this;
    	var editForm = me.getEditForm().getForm();
    	if(editForm.isValid()){//判断增值服务类型，长短途，是否激活，生效日期是否全部填写级及上半部分FORM是否按要求填写
    		if(me.valuationRecord.get('active')=='N'){
    			var priceValuationEntity = me.valuationRecord;
            	editForm.updateRecord(priceValuationEntity);//将上半部分数据设置到创建计费规则的MODEL中
            	var arrvRegionId = editForm.findField('arrvRegionName').regionId;//目的地（区域）ID
            	var deptRegionId = editForm.findField('deptRegionName').regionId;//始发地（区域）ID
            	
            	//如果基础产品选择快件包裹的话,始发、目的区域可以不填写
//            	var productRecord = editForm.findField('productId').productRecord;
//            	if(productRecord!=null&&productRecord!=''){
//            		var productCode = productRecord.get('code');
//            		if(productCode!=null&&productCode!=''){
//                		if(productCode!=pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE){
//                			if(Ext.isEmpty(arrvRegionId)&&Ext.isEmpty(deptRegionId)){//目的地（区域）和 始发地（区域）必填其一
//                				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.destinationAndOriginSelectOne'));//目的地（区域）和 始发地（区域）必填其一
//                				return ;
//                			}
//                		}
//                	}
//            	}
//            	
//            	if(Ext.isEmpty(arrvRegionId)&&Ext.isEmpty(deptRegionId)){//目的地（区域）和 始发地（区域）必填其一
//            		pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.destinationAndOriginSelectOne'));//目的地（区域）和 始发地（区域）必填其一
//            		return ;
//            	}
            	var productCode = editForm.findField('productId').getValue();
            	if(Ext.isEmpty(deptRegionId)){
            		deptRegionId = pricing.expressRegionValueAdded.ALL;
            	}
            	if(Ext.isEmpty(arrvRegionId)){
            		arrvRegionId = pricing.expressRegionValueAdded.ALL;
            	}
            	priceValuationEntity.set('arrvRegionId',arrvRegionId);//设置目的地（区域）ID
            	priceValuationEntity.set('deptRegionId',deptRegionId);//设置始发地（区域）ID
            	priceValuationEntity.set('productCode',productCode);//设置产品code
            	priceValuationEntity.set('productId',productCode);//设置产品code
            	var addPriceCriteriaDetailEntityList = new Array();//新增的计价方式明细
            	var updatePriceCriteriaDetailEntityList = new Array();//修改的计价方式明细
            	var pricingEntryCode = priceValuationEntity.get('pricingEntryCode');//得到计价条目CODE
                if(pricingEntryCode==pricing.expressRegionValueAdded.insurance){//保费
//                	var insuranceForm = me.getDownPanel().getInsuranceFormPanel().getForm();
//                	var priceCriteriaDetailEntity= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[0]);//计价方式明细
//                	if(insuranceForm.isValid()){
//                		insuranceForm.updateRecord(priceCriteriaDetailEntity);//设置计价方式明细的值
//                		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
//            				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.minimumPremiumGreaterThanMaximumCoverageForMinimumInsurance'));//代收货款中有一条数据，最低一票大于最高一票！
//            				return ;
//            			}
//                	}else{
//                		return;
//                	}
//                	updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);
                	
                	var insuranceGrid = me.getDownPanel().getInsuranceGridPanel();
                	if(insuranceGrid.getStore().getCount()>0){
            			var isNotValid = false;
            			
            			//获取全部数据列表
            			var datas1 = insuranceGrid.getStore().data.items;
            			var datas2 = insuranceGrid.getStore().data.items;
            			
            			//验证所有的限保物品必须是同一种类
            			var subTypeTemp = datas1[0].data.subType;
            			for(var i=0;i<datas1.length;i++){
            				if(subTypeTemp!=null&&subTypeTemp!=''){
            					if(subTypeTemp!=datas1[i].data.subType){
            						pricing.showWoringMessage('限保物品不一致');//限保物品不一致
            						isNotValid = true;
            						return ;
            					}
            				}else{
            					if(datas1[i].data.subType!=null&&datas1[i].data.subType!=''){
            						pricing.showWoringMessage('限保物品不一致');//限保物品不一致
            						isNotValid = true;
            						return ;
            					}
            				}
            			}
            			
            			
            			//循环，验证时候有重复区域数据
            			for(var i=0;i<datas1.length;i++){
            				for(var j=0;j<datas2.length;j++){
            					
            					//不能存在重复区域
            					if(datas2[j].data.id!=datas1[i].data.id){
            						if(datas2[j].data.leftrange>=datas1[i].data.leftrange
            								&&datas2[j].data.leftrange<datas1[i].data.rightrange){
            							pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
            							isNotValid = true;
            							return ;
            						}
            						if(datas2[j].data.rightrange>datas1[i].data.leftrange
            								&&datas2[j].data.rightrange<datas1[i].data.rightrange){
            							pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
            							isNotValid = true;
            							return ;
            						}
            					}
            					
            					
//            					//如果两者都有限保物品类型
//            					if((datas2[j].data.subType!=null&&datas2[j].data.subType!='')
//            						&&(datas1[i].data.subType!=null&&datas1[i].data.subType!='')){
//            						if(datas2[j].data.id!=datas1[i].data.id
//            								&&datas2[j].data.subType==datas1[i].data.subType){
//            							if(datas2[j].data.leftrange>=datas1[i].data.leftrange
//            									&&datas2[j].data.leftrange<datas1[i].data.rightrange){
//            								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//            								isNotValid = true;
//            								return ;
//            							}
//            							if(datas2[j].data.rightrange>datas1[i].data.leftrange
//            									&&datas2[j].data.rightrange<datas1[i].data.rightrange){
//            								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//            								isNotValid = true;
//            								return ;
//            							}
//            						}
//            					}
//            					//如果两者都没有限保物品类型
//            					if((datas2[j].data.subType==null||datas2[j].data.subType=='')
//            						&&(datas1[i].data.subType==null||datas1[i].data.subType=='')){
//            						if(datas2[j].data.id!=datas1[i].data.id){
//            							if(datas2[j].data.leftrange>=datas1[i].data.leftrange
//            									&&datas2[j].data.leftrange<datas1[i].data.rightrange){
//            								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//            								isNotValid = true;
//            								return ;
//            							}
//            							if(datas2[j].data.rightrange>datas1[i].data.leftrange
//            									&&datas2[j].data.rightrange<datas1[i].data.rightrange){
//            								pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueNotOveraly'));//保险价值不能存在重叠区域
//            								isNotValid = true;
//            								return ;
//            							}
//            						}
//            					}
            				}
            			};
            			
            			insuranceGrid.getStore().each(function(record){
            				if(record.get('minFee')>record.get('maxFee')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.minInsuranceFeeIsNotMoreMaxInsuranceFee'));//最低保额不能大于最高保额
            					isNotValid = true;
            					return ;
            				}
            				if(record.get('leftrange')>record.get('rightrange')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueBeginNotHighEnd'));//最低保险价值不能高于最高保险价值
            					isNotValid = true;
            					return ;
            				}
            				if(record.get('rightrange')<1000&&record.get('maxFee')!=1){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.MaxValuelessThousandAlert'));//保险价值低于1000元时,最高保额为1元
            					isNotValid = true;
            					return ;
            				}
//            				record.set('leftrange',record.get('leftrange')*100);
//            				record.set('rightrange',record.get('rightrange')*100);
//            				record.set('caculateType',pricing.expressRegionValueAdded.originalCost);
            			});
            			if(isNotValid){
            				return;
            			}
            			
            			var updatePriceCriteriaDetailEntityModel = insuranceGrid.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					if(updatePriceCriteriaDetailEntityModel[i].get('description')!=null
            							&&updatePriceCriteriaDetailEntityModel[i].get('description')!=''){
            						if(updatePriceCriteriaDetailEntityModel[i].get('description')=='ADD'){
            							updatePriceCriteriaDetailEntityModel[i].set('description',null);
            							updatePriceCriteriaDetailEntityModel[i].set('id',null);
            							updatePriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.originalCost);
            							updatePriceCriteriaDetailEntityModel[i].set('leftrange',updatePriceCriteriaDetailEntityModel[i].get('leftrange')*100);
                    					updatePriceCriteriaDetailEntityModel[i].set('rightrange',updatePriceCriteriaDetailEntityModel[i].get('rightrange')*100);
                    					addPriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台新增
                    					continue;
            						}
            					}
            					updatePriceCriteriaDetailEntityModel[i].set('leftrange',updatePriceCriteriaDetailEntityModel[i].get('leftrange')*100);
            					updatePriceCriteriaDetailEntityModel[i].set('rightrange',updatePriceCriteriaDetailEntityModel[i].get('rightrange')*100);
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            				
            			}
            			var addPriceCriteriaDetailEntityModel = insuranceGrid.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('leftrange'
            							,addPriceCriteriaDetailEntityModel[i].get('leftrange')*100);
            					addPriceCriteriaDetailEntityModel[i].set('rightrange'
            							,addPriceCriteriaDetailEntityModel[i].get('rightrange')*100);
            					addPriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.originalCost);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            			
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));
            			return;
            		}
                	
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.paymentCollection){//代收货款
            		var paymentCollectionGrid = me.getDownPanel().getPaymentCollectionGridPanel();
            		if(paymentCollectionGrid.getStore().getCount()>0){
            			var isNotValid = false;
            			paymentCollectionGrid.getStore().each(function(record){
            				if(record.get('minFee')>record.get('maxFee')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.collectionMoneyDataLowestVotesGreaterThanMaximumVote'));//代收货款中有一条数据，最低一票大于最高一票！
            					isNotValid = true;
            					return ;
            				}
            			});
            			if(isNotValid){
            				return;
            			}
            			var updatePriceCriteriaDetailEntityModel = paymentCollectionGrid.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					updatePriceCriteriaDetailEntityModel[i].set('leftrange',updatePriceCriteriaDetailEntityModel[i].get('leftrange')*100);
            					updatePriceCriteriaDetailEntityModel[i].set('rightrange',updatePriceCriteriaDetailEntityModel[i].get('rightrange')*100);
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            				
            			}
            			var addPriceCriteriaDetailEntityModel = paymentCollectionGrid.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('leftrange'
            							,addPriceCriteriaDetailEntityModel[i].get('leftrange')*100);
            					addPriceCriteriaDetailEntityModel[i].set('rightrange'
            							,addPriceCriteriaDetailEntityModel[i].get('rightrange')*100);
            					addPriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.originalCost);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));//请维护代收货款增值服务基础数据！
            			return;
            		}
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.superDelivery){//超远派送
            		var superDeliveryGrid = me.getDownPanel().getSuperDeliveryGridPanel();
            		if(superDeliveryGrid.getStore().getCount()>0){
            			var isNotValid = false;
            			superDeliveryGrid.getStore().each(function(record){
            				if(record.get('leftrange')>record.get('rightrange')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.superDataDeliveryStartingPointGreaterEndOf'));//超远派送中有一条数据，起点大于终点！
            					isNotValid = true;
            					return ;
            				}
            			});
            			if(isNotValid){
            				return;
            			}
            			var updatePriceCriteriaDetailEntityModel = superDeliveryGrid.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					updatePriceCriteriaDetailEntityModel[i].set('leftrange',updatePriceCriteriaDetailEntityModel[i].get('leftrange'));
            					updatePriceCriteriaDetailEntityModel[i].set('rightrange',updatePriceCriteriaDetailEntityModel[i].get('rightrange'));
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            				
            			}
            			var addPriceCriteriaDetailEntityModel = superDeliveryGrid.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('leftrange'
            							,addPriceCriteriaDetailEntityModel[i].get('leftrange'));
            					addPriceCriteriaDetailEntityModel[i].set('rightrange'
            							,addPriceCriteriaDetailEntityModel[i].get('rightrange'));
            					addPriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.kilometer);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));//请维护代收货款增值服务基础数据！
            			return;
            		}
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.deliveryUpstairs){//送货上楼
            		var deliveryUpstairsGridPanel = me.getDownPanel().getDeliveryUpstairsGridPanel();
            		if(deliveryUpstairsGridPanel.getStore().getCount()>0){
            			var isNotValid = false;
            			deliveryUpstairsGridPanel.getStore().each(function(record){
            				if(record.get('leftrange')>record.get('rightrange')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.dataDeliveryUpstairsStartingPointGreaterThanTheEndOf'));//送货上楼中有一条数据，起点大于终点！
            					isNotValid = true;
            					return ;
            				}
            			});
            			if(isNotValid){
            				return;
            			}
            			var updatePriceCriteriaDetailEntityModel = deliveryUpstairsGridPanel.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					updatePriceCriteriaDetailEntityModel[i].set('leftrange',updatePriceCriteriaDetailEntityModel[i].get('leftrange'));
            					updatePriceCriteriaDetailEntityModel[i].set('rightrange',updatePriceCriteriaDetailEntityModel[i].get('rightrange'));
            					updatePriceCriteriaDetailEntityModel[i].set('feeRate',updatePriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            				
            			}
            			var addPriceCriteriaDetailEntityModel = deliveryUpstairsGridPanel.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('leftrange'
            							,addPriceCriteriaDetailEntityModel[i].get('leftrange'));
            					addPriceCriteriaDetailEntityModel[i].set('rightrange'
            							,addPriceCriteriaDetailEntityModel[i].get('rightrange'));
            					addPriceCriteriaDetailEntityModel[i].set('feeRate',addPriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					addPriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.Weight);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.valueAddedServicesMaintenancePaymentCollectionBasicData'));//请维护代收货款增值服务基础数据！
            			return;
            		}
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.deliveryCharges){//送货费
            		var deliveryChargesGrid = me.getDownPanel().getDeliveryChargesGridPanel();
            		if(deliveryChargesGrid.getStore().getCount()>0){
            			var isNotValid = false;
            			deliveryChargesGrid.getStore().each(function(record){
            				if(record.get('leftrange')>record.get('rightrange')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.dataDeliveryChargeStartingPointGreaterThanEndOf'));//送货费中有一条数据，起点大于终点！
            					isNotValid = true;
            					return ;
            				}
            			});
            			if(isNotValid){
            				return;
            			}
            			var updatePriceCriteriaDetailEntityModel = deliveryChargesGrid.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					updatePriceCriteriaDetailEntityModel[i].set('feeRate',updatePriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            				
            			}
            			var addPriceCriteriaDetailEntityModel = deliveryChargesGrid.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('feeRate',addPriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.maintenanceDeliveryChargeValue-addedServiceBasicData'));//请维护送货费增值服务基础数据！
            			return;
            		}
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.receivingCharges){//接货费
            		var receivingChargesForm = me.getDownPanel().getReceivingChargesFormPanel().getForm();
                	if(receivingChargesForm.isValid()){
                		var minFee = receivingChargesForm.findField('minFee').getValue();//得到最低收费
                		var maxFee = receivingChargesForm.findField('maxFee').getValue();//得到最高收费
                		var elements = receivingChargesForm.getFields( ).items;//获得费率的两个页面元素，一个按体积，一个按重量
                		var weightModel = null;//按重量的model
                		var volumeModel = null;//按体积的model
                		for(var i= 0;i<me.priceCriteriaDetailEntityList.length;i++){
                			if(me.priceCriteriaDetailEntityList[i].caculateType==pricing.expressRegionValueAdded.Weight){
                				weightModel = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
                			}else if(me.priceCriteriaDetailEntityList[i].caculateType==pricing.expressRegionValueAdded.Volume){
                				volumeModel = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
                			}
                		}
                		var weightFeeRate = null;//按重量的费率
                		var volumeFeeRate = null;//按体积的费率
                		for(var i = 0;i<elements.length;i++){
                			if(elements[i].CACULATE_TYPE==pricing.expressRegionValueAdded.Weight){//表明是按重量
                				weightFeeRate = elements[i].getValue()*100;
                				weightModel.set('feeRate',weightFeeRate);//设置费率
                				weightModel.set('minFee',minFee);//设置最低费
                				weightModel.set('maxFee',maxFee);//设置最高费
                			}else if(elements[i].CACULATE_TYPE==pricing.expressRegionValueAdded.Volume){//表明是按体积
                				volumeFeeRate = elements[i].getValue()*100;
                				volumeModel.set('feeRate',volumeFeeRate);//设置费率
                				volumeModel.set('minFee',minFee);//设置最低费
                				volumeModel.set('maxFee',maxFee);//设置最高费
                			}
                		}
                	}else{
                		return;
                	}
                	//生成两条数据
                	updatePriceCriteriaDetailEntityList.push(weightModel.data);//生成两条数据
                	updatePriceCriteriaDetailEntityList.push(volumeModel.data);
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.packing){//包装费
            		var packingForm = me.getDownPanel().getPackingFormPanel().getForm();//获得form页面元素
            		var priceCriteriaDetailEntityBOX = null;
            		var priceCriteriaDetailEntityFRAME = null;
            		if(packingForm.isValid()){
            			for(var i= 0;i<me.priceCriteriaDetailEntityList.length;i++){
                			if(me.priceCriteriaDetailEntityList[i].subType==pricing.expressRegionValueAdded.PACKAGE_TYPE__FRAME){
                				priceCriteriaDetailEntityFRAME = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
                			}else if(me.priceCriteriaDetailEntityList[i].subType==pricing.expressRegionValueAdded.PACKAGE_TYPE__BOX){
                				priceCriteriaDetailEntityBOX = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
                			}
                		}
            			var feeRateBOX = null;//木箱费率
            			var minFeeBOX = null;//木箱最低一票
            			var feeRateFRAME = null;//木架费率
            			var minFeeFRAME = null;//木架最低一票
            			var elements = packingForm.getFields( ).items;//获得FORM页面元素，一个原件返单，一个传真返单
            			feeRateFRAME = elements[0].getValue();
            			minFeeFRAME =  elements[1].getValue();
            			feeRateBOX = elements[2].getValue();
            			minFeeBOX = elements[3].getValue();
            		}else{
            			return;
            		}
            		priceCriteriaDetailEntityBOX.set('feeRate',feeRateBOX*100);//乘100//@TODO
            		priceCriteriaDetailEntityBOX.set('minFee',minFeeBOX);
            		priceCriteriaDetailEntityFRAME.set('feeRate',feeRateFRAME*100);//乘100
            		priceCriteriaDetailEntityFRAME.set('minFee',minFeeFRAME);

            		
            		updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntityBOX.data);
            		updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntityFRAME.data);
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.sign){//签收回单
            		var signForm = me.getDownPanel().getSignFormPanel().getForm();//得到FORM表单
            		var priceCriteriaDetailEntityYJ= null;//计价方式明细-原件返单
            		var priceCriteriaDetailEntityCZ= null;//计价方式明细-传真返单
            		for(var i= 0;i<me.priceCriteriaDetailEntityList.length;i++){
            			if(me.priceCriteriaDetailEntityList[i].subType==pricing.expressRegionValueAdded.YJFD){//判断一条数据时原件返单还是传真返单
            				priceCriteriaDetailEntityYJ = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
            			}else if(me.priceCriteriaDetailEntityList[i].subType==pricing.expressRegionValueAdded.CZFD){
            				priceCriteriaDetailEntityCZ = new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[i]);
            			}
            		}
                	if(signForm.isValid()){
                		var feeRateYJ = null;//原件费率
                		var feeRateCZ = null;//传真费率
                		var elements = signForm.getFields( ).items;//获得FORM页面元素，一个原件返单，一个传真返单
                		for(var i = 0;i<elements.length;i++){
                			if(elements[i].subType==pricing.expressRegionValueAdded.YJFD){//表明是原件返单
                				feeRateYJ = elements[i].getValue();
                				priceCriteriaDetailEntityYJ.set('fee',feeRateYJ);//设置费率
                			}else if(elements[i].subType==pricing.expressRegionValueAdded.CZFD){//表明是传真返单
                				feeRateCZ = elements[i].getValue();
                				priceCriteriaDetailEntityCZ.set('fee',feeRateCZ);//设置费率
                			}
                		}
                	}else{
                		return;
                	}
                	//生成两条数据
                	updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntityYJ.data);//生成两条数据
                	updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntityCZ.data);
            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.storageCharges){//仓储费
            		var storageChargesForm = me.getDownPanel().getStorageChargesFormPanel().getForm();
            		var priceCriteriaDetailEntity= new Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity(me.priceCriteriaDetailEntityList[0]);//计价方式明细
                	if(storageChargesForm.isValid()){
                		storageChargesForm.updateRecord(priceCriteriaDetailEntity);
                		if(priceCriteriaDetailEntity.get('minFee')>priceCriteriaDetailEntity.get('maxFee')){
            				pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.storageChargesMinimumChargeGreaterThanMaximumCharge'));//仓储费中，最低收取大于最高收取！
            				return ;
            			}
                	}else{
                		return;
                	}
                	priceCriteriaDetailEntity.set('feeRate',priceCriteriaDetailEntity.get('feeRate')*100);//费率的单位是元/m³/天，要换算成分，所以乘100
                	updatePriceCriteriaDetailEntityList.push(priceCriteriaDetailEntity.data);

            	}else if(pricingEntryCode==pricing.expressRegionValueAdded.otherCharges){//其它费用（）！！！subType不是随便填写的
            		var otherChargesGrid = me.getDownPanel().getOtherChargesGridPanel();
            		if(otherChargesGrid.getStore().getCount()>0){
            			var isNotValid = false;
            			otherChargesGrid.getStore().each(function(record){
            				if(record.get('minFee')>record.get('maxFee')){
            					pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.otherCostsDataAmountLowerLimitGreaterMaximumAmount'));//其它费用中有一条数据，金额下限大于金额上限！
            					isNotValid = true;
            					return ;
            				}
            			});
            			if(isNotValid){
            				return;
            			}
            			var updatePriceCriteriaDetailEntityModel = otherChargesGrid.getStore().getUpdatedRecords( );//修改了的数据
            			if(!Ext.isEmpty(updatePriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<updatePriceCriteriaDetailEntityModel.length;i++){
            					updatePriceCriteriaDetailEntityModel[i].set('feeRate',updatePriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					updatePriceCriteriaDetailEntityList.push(updatePriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到updatePriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            			var addPriceCriteriaDetailEntityModel = otherChargesGrid.getStore().getNewRecords( );//新增了的数据
            			if(!Ext.isEmpty(addPriceCriteriaDetailEntityModel)){//如果不为空
            				for(var i =0;i<addPriceCriteriaDetailEntityModel.length;i++){
            					addPriceCriteriaDetailEntityModel[i].set('feeRate',addPriceCriteriaDetailEntityModel[i].get('feeRate')*100);
            					addPriceCriteriaDetailEntityModel[i].set('rightrange',pricing.expressRegionValueAdded.CRITERIA_DETAIL_ORIGINALCOST_MAX);
            					addPriceCriteriaDetailEntityModel[i].set('leftrange',0);
            					addPriceCriteriaDetailEntityModel[i].set('caculateType',pricing.expressRegionValueAdded.originalCost);
            					addPriceCriteriaDetailEntityList.push(addPriceCriteriaDetailEntityModel[i].data);//将MODEL的data只放到addPriceCriteriaDetailEntityList中，传给后台
            				}
            			}
            		}else{
            			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseMaintainBasicDataOtherValueAddedServices'));//请维护其他增值服务基础数据！
            			return;
            		}
            	}
                var params = {'pricingValuationVo':{'priceCriteriaDetailEntityList':addPriceCriteriaDetailEntityList
                	,'priceValuationEntity':priceValuationEntity.data,'updatePriceCriteriaDetailEntityList':updatePriceCriteriaDetailEntityList}};//组装数据
                var successFun = function(json){
                	pricing.showInfoMes(json.message);//提示信息
                	var grid  = Ext.getCmp('T_pricing-indexExpressRegionValuation_content').getPricingValuationGrid();
    				grid.getPagingToolbar().moveFirst();//重新查询计费规则
                	me.hide();
                };//成功之后提示成功，隐藏现在界面
                var failureFun = function(json){
                	if(Ext.isEmpty(json)){
    					pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));//提示请求超时
    				}else{
    					pricing.showErrorMes(json.message);
    				}
                };
                var url = pricing.realPath('updateValueAddedExpress.action');//修改区域增值服务
                pricing.requestJsonAjax(url,params,successFun,failureFun);//发送AJAX请求
    		}
    		else if(me.valuationRecord.get('active')=='Y'){
    			pricing.addValueAddedRegionExpress(editForm,me);
    		}
    		
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.commintexpressRegionValueAdded();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 区域增值服务-新增
 */
Ext.define('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressRegionValueAdded.i18n('foss.pricing.newRegionalValueAddedServices'),//新增区域增值服务
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :680,	
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){
			me.getEditForm().getForm().reset();
			me.getEditForm().getForm().findField('productId').productRecord = null;//基础产品属性设置为空
			me.getEditForm().getForm().findField('goodsTypeId').goodsTypeRecord = null;//货物类型属性设置为空
			me.getDownPanel().getInsuranceGridPanel().getStore().removeAll();
			me.getDownPanel().getPaymentCollectionGridPanel().getStore().removeAll();
			me.getDownPanel().getSuperDeliveryGridPanel().getStore().removeAll();
			me.getDownPanel().getDeliveryUpstairsGridPanel().getStore().removeAll();
			me.getDownPanel().getDeliveryChargesGridPanel().getStore().removeAll();
			me.getDownPanel().getReceivingChargesFormPanel().getForm().reset();
			me.getDownPanel().getPackingFormPanel().getForm().reset();//包装费
			me.getDownPanel().getSignFormPanel().getForm().reset();
			me.getDownPanel().getStorageChargesFormPanel().getForm().reset();
			me.getDownPanel().getOtherChargesGridPanel().getStore().removeAll();
			me.height = 680;
		},
		beforeshow:function(me){
			
		}
	},
	editForm:null,//区域增值服务新增修改查看表单（上半部分）
	//上面固定的FORM
	getEditForm:function(){
		if(Ext.isEmpty(this.editForm)){
    		this.editForm = Ext.create('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEditFormPanel',{
    			'isReadOnly':false
    		});
    	}
    	return this.editForm;
	},
	regionWindow:null,//选择区域弹窗
	getRegionWindow:function(){
		if(Ext.isEmpty(this.regionWindow)){
    		this.regionWindow = Ext.create('Foss.pricing.expressRegionValueAdded.RegionGridShowWindow');
    	}
    	return this.regionWindow;
	},
	expressRegionWindow:null,//快递区域弹出框
	getExpressRegionWindow:function(){
		if(Ext.isEmpty(this.expressRegionWindow)){
    		this.expressRegionWindow = Ext.create('Foss.pricing.expressRegionValueAdded.ExpressRegionGridShowWindow');
    	}
    	return this.expressRegionWindow;
	},
	downPanel:null,//区域增值服务新增修改查看表单（下半部分）
	//下颁布PANEL
	getDownPanel:function(){
		if(this.downPanel==null){
    		this.downPanel = Ext.create('Foss.pricing.expressRegionValueAdded.RegionDownPanel',{
    			'isReadOnly':false
    		});
    	}
    	return this.downPanel;
	},
    //提交数据
    commintexpressRegionValueAdded:function(){
    	var me = this;
    	var editForm = me.getEditForm().getForm();//传送getForm之后的form
    	if(editForm.isValid()){//判断增值服务类型，长短途，是否激活，生效日期是否全部填写级及上半部分FORM是否按要求填写
    		pricing.addValueAddedRegionExpress(editForm,me);
    	}
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEditForm(), me.getDownPanel()];
		me.fbar = [{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.returnGrid'),
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.commit'),
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.commintexpressRegionValueAdded();
			} 
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单（上部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEditFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : 1,
    	margin : '3 10 5 10',
		labelSeparator:'',
		labelWidth:130,
    	anchor : '100%'
    },
    height :490,
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
			xtype : 'textfield',
			fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.regionalValueAddedServiceNames'),//区域增值服务名称
			maxLength:15,
			allowBlank:false,
			name:'name'
		},{
			xtype : 'textfield',
			readOnly:true,
			beforeLabelTextTpl: '',
			emptyText:pricing.expressRegionValueAdded.i18n('foss.pricing.automaticallyGeneratedCoding'),//自动生成编码
			fieldLabel: '<span style="color:red">*</span>'+pricing.expressRegionValueAdded.i18n('foss.pricing.regionalValueAddedServicesCoding'),//区域增值服务编码
			maxLength:25,
			name:'code'
		},{
			name: 'pricingEntryCode',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'code',
		    editable:false,
		    readOnly:isReadOnly,
		    allowBlank:false,
		    store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceEntityModel',null
		    ,pricing.expressRegionValueAdded.valueAddedType),
	        fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.theirRespectiveValueAddedServices'),//所属增值服务
	        pricingEntryId:null,//服务ID
	        xtype : 'combo',
	        listeners:{
	        	change:function(com,newValue,oldValue){
	        		me.up('window').setHeight(850);
	        		for(var i=0;i<pricing.expressRegionValueAdded.valueAddedType.length;i++){//设置服务ID
	        			if(pricing.expressRegionValueAdded.valueAddedType[i].code==newValue){
	        				com.pricingEntryId = pricing.expressRegionValueAdded.valueAddedType[i].id;
	        			}
	        		}
	        		if(oldValue==pricing.expressRegionValueAdded.insurance){//保费
	        			com.up('form').up('window').getDownPanel().getInsuranceGridPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.receivingCharges){//收货费
	        			com.up('form').up('window').getDownPanel().getReceivingChargesFormPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.sign){
	        			com.up('form').up('window').getDownPanel().getSignFormPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.storageCharges){
	        			com.up('form').up('window').getDownPanel().getStorageChargesFormPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.otherCharges){
	        			com.up('form').up('window').getDownPanel().getOtherChargesGridPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.deliveryCharges){
	        			com.up('form').up('window').getDownPanel().getDeliveryChargesGridPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.paymentCollection){
	        			com.up('form').up('window').getDownPanel().getPaymentCollectionGridPanel().hide()
	        		}else if(oldValue==pricing.expressRegionValueAdded.superDelivery){//超远派送
	        			com.up('form').up('window').getDownPanel().getSuperDeliveryGridPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.deliveryUpstairs){//送货上楼
	        			com.up('form').up('window').getDownPanel().getDeliveryUpstairsGridPanel().hide();
	        		}else if(oldValue==pricing.expressRegionValueAdded.packing){//包装
	        			com.up('form').up('window').getDownPanel().getPackingFormPanel().hide();
	        		}
	        		if(newValue==pricing.expressRegionValueAdded.insurance){
	        			com.up('form').up('window').getDownPanel().getInsuranceGridPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.receivingCharges){
	        			com.up('form').up('window').getDownPanel().getReceivingChargesFormPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.sign){
	        			com.up('form').up('window').getDownPanel().getSignFormPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.storageCharges){
	        			com.up('form').up('window').getDownPanel().getStorageChargesFormPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.otherCharges){
	        			com.up('form').up('window').getDownPanel().getOtherChargesGridPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.deliveryCharges){
	        			com.up('form').up('window').getDownPanel().getDeliveryChargesGridPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.paymentCollection){
	        			com.up('form').up('window').getDownPanel().getPaymentCollectionGridPanel().show()
	        		}else if(newValue==pricing.expressRegionValueAdded.superDelivery){//超远派送
	        			com.up('form').up('window').getDownPanel().getSuperDeliveryGridPanel().show()
	        		}else if(newValue==pricing.expressRegionValueAdded.deliveryUpstairs){//送货上楼
	        			com.up('form').up('window').getDownPanel().getDeliveryUpstairsGridPanel().show();
	        		}else if(newValue==pricing.expressRegionValueAdded.packing){
	        			com.up('form').up('window').getDownPanel().getPackingFormPanel().show();
	        		}
	        	}
	        }
		},{
			xtype:'datefield',
			minValue: new Date(pricing.expressRegionValueAdded.tomorrowTime),
			value:new Date(pricing.expressRegionValueAdded.tomorrowTime),
			fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.availabilityDate'),//生效日期
			format:'Y-m-d',
			readOnly:isReadOnly,
			name:'beginTime',
			editable:false
		},{
			name: 'productId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    readOnly:false,
		    productRecord:null,//基础产品实体
		    store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.ProductEntity',null
		    ,pricing.expressRegionValueAdded.productEntityList),
	        fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.basicProducts'),//基础产品
	        xtype : 'combo',
	        value:pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE
//	        listeners:{
//	        	change:function(comb,newValue,oldvalue){
//	        		if(!Ext.isEmpty(newValue)){
//	        			comb.productRecord = comb.getStore().getById(newValue);
//	        			var form = this.up('form');
//	        			form.getForm().findField('deptRegionName').reset();
//						form.getForm().findField('arrvRegionName').reset();
//	        		}else{
//	        			comb.productRecord = null;
//	        			var form = this.up('form');
//						form.getForm().findField('deptRegionName').reset();
//						form.getForm().findField('arrvRegionName').reset();
//	        		}
//	        	}
//	        }
		},{
			name: 'goodsTypeId',
			queryMode: 'local',
		    displayField: 'name',
		    valueField: 'id',
		    editable:false,
		    readOnly:isReadOnly,
		    goodsTypeRecord:null,
		    store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.GoodsTypeEntity',null
		    ,pricing.expressRegionValueAdded.goodTypeList),
	        fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.cargoType'),//货物类型
	        xtype : 'combo',
	        listeners:{
	        	change:function(comb,newValue,oldvalue){
	        		if(!Ext.isEmpty(newValue)){
	        			comb.goodsTypeRecord = comb.getStore().getById(newValue);
	        		}else{
	        			comb.goodsTypeRecord = null;
	        		}
	        		
	        	}
	        }
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			defaults:{
				margin : '3 10 5 10',
				labelSeparator:'',
				labelWidth:130
			},
			layout: {
		        type: 'table',
		        columns: 6
		    },
			items : [{
				xtype : 'textfield',
				fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.departureRegion'),//出发地（区域）
				 colspan: 3,
				 regionId:null,//区域ID
				readOnly:true,
				name:'deptRegionName'
			},{
				xtype : 'button', 
				width:70,
				cls:'btnAfterTextfield',
				colspan: 1,
				disabled:isReadOnly,
				text :pricing.expressRegionValueAdded.i18n('foss.pricing.select'),//选择
				handler : function() {
					me.up('window').getExpressRegionWindow().show();
					me.up('window').getExpressRegionWindow().textField
					= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//					var form = this.up('form');
//					var productRecord = form.getForm().findField('productId').productRecord;
//					if(productRecord!=null&&productRecord!=''){
//						var productCode = productRecord.get('code');
//						if(productCode!=null
//								&&productCode!=''
//								&&productCode===pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE){
//							me.up('window').getExpressRegionWindow().show();
//							me.up('window').getExpressRegionWindow().textField
//							= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//							//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//						}else{
//							me.up('window').getRegionWindow().show();
//							me.up('window').getRegionWindow().textField
//							= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//							//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//						}
//					}else{
//						me.up('window').getRegionWindow().show();
//						me.up('window').getRegionWindow().textField
//						= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//						//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//					}
				}
			},{
				xtype : 'button', 
				width:70,
				cls:'btnAfterTextfield',
				colspan: 1,
				disabled:isReadOnly,
				text :pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.cancel'),//取消
				handler : function() {
					 this.up('container').items.items[0].regionId = null;
					 this.up('container').items.items[0].reset();
				}
			}]
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			defaults:{
				margin : '3 10 5 10',
				labelSeparator:'',
				labelWidth:130
			},
			layout: {
		        type: 'table',
		        columns: 6
		    },
			items : [{
				xtype : 'textfield',
				fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.destinationArea'),//目的地（区域）
				colspan: 3,
				 regionId:null,//区域ID
				 readOnly:true,
				name:'arrvRegionName'
			},{
				xtype : 'button', 
				width:70,
				colspan: 1,
				cls:'btnAfterTextfield',
				disabled:isReadOnly,
				text :pricing.expressRegionValueAdded.i18n('foss.pricing.select'),//选择
				handler : function() {
					me.up('window').getExpressRegionWindow().show();
					me.up('window').getExpressRegionWindow().textField
					= this.up('container').items.items[0];//this是指按钮，而me是
				}
//					var form = this.up('form');
//					var productRecord = form.getForm().findField('productId').productRecord;
//					if(productRecord!=null&&productRecord!=''){
//						var productCode = productRecord.get('code');
//						if(productCode!=null
//								&&productCode!=''
//								&&productCode===pricing.expressRegionValueAdded.PRODUCTCODE_PACKAGE){
//							me.up('window').getExpressRegionWindow().show();
//							me.up('window').getExpressRegionWindow().textField
//							= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//							//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//						}else{
//							me.up('window').getRegionWindow().show();
//							me.up('window').getRegionWindow().textField
//							= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//							//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//						}
//					}else{
//						me.up('window').getRegionWindow().show();
//						me.up('window').getRegionWindow().textField
//						= this.up('container').items.items[0];//this是指按钮，而me是指form表单。
//						//this的上一级是一个xtype : 'container',然后再找到items下的第一个元素就会获取出发地（区域）
//					}
//				}
			},{
				xtype : 'button', 
				width:70,
				cls:'btnAfterTextfield',
				colspan: 1,
				disabled:isReadOnly,
				text :pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.cancel'),//取消
				handler : function() {
					 this.up('container').items.items[0].regionId = null;
					 this.up('container').items.items[0].reset();
				}
			}]
		
		},{	
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'active',
			 width:160,
			 fieldLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.isActive'),//是否激活
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.ye'),
			     name:'active',
			     readOnly:isReadOnly,
			     inputValue:'Y'
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.no'),
			     name:'active',
			     readOnly:isReadOnly,
			     inputValue:'N'
			     }]
		},{
			 xtype:'radiogroup',
			 vertical:true,
			 allowBlank:false,
			 name:'longOrShort',
			 width:160,
			 fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.shortAndLongDistance'),
			 items:[{
			 	 xtype:'radio',
			     boxLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.longDistance'),//长途
			     readOnly:isReadOnly,
			     name:'longOrShort',
			     inputValue:pricing.expressRegionValueAdded.LONG
		     },{
		    	 xtype:'radio',
			     boxLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.shortDistance'),//短途
			     name:'longOrShort',
			     readOnly:isReadOnly,
			     inputValue:pricing.expressRegionValueAdded.SHORT
		    },{
		    	 xtype:'radio',
			     boxLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.all'),//全部
			     name:'longOrShort',
			     readOnly:isReadOnly,
			     inputValue:pricing.expressRegionValueAdded.ALL
		    }]
		},{
	        xtype : 'textareafield',
	        fieldLabel: pricing.expressRegionValueAdded.i18n('foss.pricing.programDescription'),
	        readOnly:isReadOnly,
	        name:'remarks'
	    }];
		me.callParent([cfg]);
	}
});
Ext.define('Foss.pricing.expressRegionValueAdded.RegionDownPanel', {
	extend:'Ext.panel.Panel',
    flex: 1,
    layout : {
		type : 'vbox',
		align : 'stretch'
	},
    //保费的填写数据formPANEL
    insuranceGridPanel:null,
    getInsuranceGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.insuranceGridPanel)){
    		this.insuranceGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.InsuranceGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.insuranceGridPanel;
    },
    //包装费
    packingFormPanel:null,
    getPackingFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.packingFormPanel)){
    		this.packingFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.PackingFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.packingFormPanel;
    },
    //接货费
    receivingChargesFormPanel:null,
    getReceivingChargesFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.receivingChargesFormPanel)){
    		this.receivingChargesFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.ReceivingChargesFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.receivingChargesFormPanel;
    },
    //签收回单PANEL
    signFormPanel:null,
    getSignFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.signFormPanel)){
    		this.signFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.SignFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.signFormPanel;
    },
    //仓储费PANEL
    storageChargesFormPanel:null,
    getStorageChargesFormPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.storageChargesFormPanel)){
    		this.storageChargesFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.StorageChargesFormPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.storageChargesFormPanel;
    },
    //其他费PANEL
    otherChargesGridPanel:null,
    getOtherChargesGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.otherChargesGridPanel)){
    		this.otherChargesGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.OtherChargesGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.otherChargesGridPanel;
    },
    //送货费
    deliveryChargesGridPanel:null,
    getDeliveryChargesGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.deliveryChargesGridPanel)){
    		this.deliveryChargesGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.DeliveryChargesGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.deliveryChargesGridPanel;
    },
    //代收货款
    paymentCollectionGridPanel:null,
    getPaymentCollectionGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.paymentCollectionGridPanel)){
    		this.paymentCollectionGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.PaymentCollectionGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.paymentCollectionGridPanel;
    },
  //超远派送
    superDeliveryGridPanel:null,
    getSuperDeliveryGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.superDeliveryGridPanel)){
    		this.superDeliveryGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.SuperDeliveryGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.superDeliveryGridPanel;
    },
    //送货上楼费
    deliveryUpstairsGridPanel:null,
    getDeliveryUpstairsGridPanel:function(isReadOnly){
    	if(Ext.isEmpty(this.deliveryUpstairsGridPanel)){
    		this.deliveryUpstairsGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.DeliveryUpstairsGridPanel',{
    			'isReadOnly':isReadOnly//设置是否只读
    		});
    	}
    	return this.deliveryUpstairsGridPanel;
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		var isReadOnly = false;//默认非只读
		if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
			isReadOnly = config.isReadOnly;
		}
		me.items = [me.getInsuranceGridPanel(isReadOnly),me.getReceivingChargesFormPanel(isReadOnly)
		            ,me.getSignFormPanel(isReadOnly),me.getStorageChargesFormPanel(isReadOnly)
		            ,me.getOtherChargesGridPanel(isReadOnly),me.getDeliveryChargesGridPanel(isReadOnly)
		            ,me.getPaymentCollectionGridPanel(isReadOnly),me.getPackingFormPanel(isReadOnly)
		            ,me.getSuperDeliveryGridPanel(isReadOnly),me.getDeliveryUpstairsGridPanel(isReadOnly)];
		me.callParent([cfg]);
	}
});

/**
 * 增值服务表单保费（下部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.InsuranceGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
	isreadOnlyFlag:false,
	setIsreadOnlyFlag:function(isreadOnlyFlag){
		this.isreadOnlyFlag = isreadOnlyFlag;
	},
    columns: [{
		text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items: [{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass:function(v,m,r,rowIndex){
					var grid = this.up('grid');
					if(grid.isreadOnlyFlag){
						return 'statementBill_hide';
					}else{
						return 'deppon_icons_delete';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
    				grid.getStore().remove(record);
//            		pricing.showQuestionMes('是否要删除这条保费规则信息?',function(e){//是否要删除这条保费规则信息？
//            			if(e=='yes'){//询问是否删除，是则发送请求
//            				grid.getStore().remove(record);
//            			}
//            		});
                }
			}]
	  },{
    	header: pricing.expressRegionValueAdded.i18n('foss.pricing.limitedWarrantyItems'),//现报物品
        dataIndex: 'subType',
        width: 95,
        renderer: function(value){
        	var store = pricing.getStore(null,null,['goodsName','virtualCode'],pricing.expressRegionValueAdded.limitedWarrantyItems);
        	var name = '';
        	if(!Ext.isEmpty(store)){
        		store.each(function(record){
        			if(record.get('virtualCode')==value){
        				name = record.get('goodsName');
        			}
        		});
        	}
        	return name;
        },
        editor: {
    		queryMode: 'local',
    	    displayField: 'goodsName',
    	    valueField: 'virtualCode',
    	    editable:false,
    	    store:pricing.getStore(null,null,['goodsName','virtualCode'],pricing.expressRegionValueAdded.limitedWarrantyItems),
    	    xtype : 'combo'
        }
    },{
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueBegin'),//保险价值（起）
        dataIndex: 'leftrange',
        flex: 1,
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
			decimalPrecision:3,
	        step:0.01,
            minValue: 0,
            maxValue: 99999999
        }
        
    }, {
        header:pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceGoodsValueEnd'),//保险价值（止）
        dataIndex: 'rightrange',
        flex: 1,
        renderer: function(value,metaData ,record){
        	if(value<1000){
        		record.set('minFee',1);
        		record.set('maxFee',1);
        		record.set('feeRate',value/1000);
        	}
        	return value;
        },
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:3,
	        step:0.01,
            minValue: 0,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceFeeMin'),//最低保额
        dataIndex: 'minFee',
        width: 65,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: 1
            //maxValue: 20000
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.insuranceFeeMax'),//最高保额
        dataIndex: 'maxFee',
        width: 65,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: 1
            //maxValue: 20000
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rates'),//费率‰
        dataIndex: 'feeRate',
        width: 65,
        //align: 'right',
        renderer: function(value,metaData ,record){
        	if(value!=null||value!='')
        	return value;
        },
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:4,
            step:0.001,
            minValue: 0,
            maxValue: 1
        }
    }],
    //新增代收货款一条默认值
    addOneModel:function(){
    	var me = this;
//    	var store = FossDataDictionary.getDataDictionaryStore(pricing.expressRegionValueAdded.returnSubType);
//    	var subType = '';
//    	if(!Ext.isEmpty(store)){
//    		subType = store.getAt(0).get('valueCode');
//    	}
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	'subType':'',
        	'leftrange':0,
        	'rightrange':1000,
        	'minFee':1,
        	'maxFee':1,
        	'feeRate':0.001,
        	'description':'ADD',
        	'id':me.store.data.length+1
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
    		        text: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),//新增
    		        handler : function(){
    		        	me.addOneModel();
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
		me.setIsreadOnlyFlag(isReadOnly);
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});

//Ext.define('Foss.pricing.expressRegionValueAdded.InsuranceFormPanel', {
//extend : 'Ext.form.Panel',
//frame: true,
//collapsible: true,
//hidden:true,
//defaults : {
//	labelWidth:120,
//	columnWidth : 1,
//	labelSeparator:'',
//	margin : '5 5 5 5',
//	anchor : '100%'
//},
//flex:1,
//defaultType : 'textfield',
//layout:'column',
//constructor : function(config) {
//	var me = this, 
//		cfg = Ext.apply({}, config);
//	var isReadOnly = false;//默认非只读
//	if(!Ext.isEmpty(config)&&!Ext.isEmpty(config.isReadOnly)){//判断参数不配共并且config.isReadOnly不为空则将参数中只读信息传给isReadOnly
//		isReadOnly = config.isReadOnly;
//	}
//	me.items = [{
//		name:'subType',
//		queryMode: 'local',
//	    displayField: 'goodsName',
//	    valueField: 'virtualCode',
//	    editable:false,
//	    readOnly:isReadOnly,
//	    fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.limitedWarrantyItems'),//限保物品
//	    store:pricing.getStore(null,null,['goodsName','virtualCode']
//	    ,pricing.expressRegionValueAdded.limitedWarrantyItems),
//        xtype : 'combo'
//	},{
//		xtype:'numberfield',
//        decimalPrecision:4,
//        name:'feeRate',
//        readOnly:isReadOnly,
//        allowBlank:false,
//        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.rates'),//费率
//        step:0.0001,
//        maxValue: 1,
//        minValue: 0
//	},{
//		xtype:'numberfield',
//        decimalPrecision:2,
//        name:'minFee',
//        readOnly:isReadOnly,
//        allowBlank:false,
//        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.minimumPremiumFee'),//最低保险费
//        maxValue: 99999999.99,
//        minValue: 0 
//	},{
//		xtype:'numberfield',
//        decimalPrecision:2,
//        name:'maxFee',
//        readOnly:isReadOnly,
//        allowBlank:false,
//        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.maximumCoverageForMinimumInsurance'),//最低保险最高保额
//        maxValue: 99999999.99,
//        minValue: 0
//	}];
//	me.callParent([cfg]);
//}
//});

/**
 * 增值服务表单包装费（下部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.PackingFormPanel', {
	extend : 'Ext.form.Panel',
	frame: true,
	flex:1,
	collapsible: true,
	hidden:true,
    defaults : {
    	colspan: 1,
    	margin : '5 5 5 5',
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
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.woodenFrameRates'),//木架费率
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.lowestTicketYuan'),//最低一票（元）
	        name:'minFee',
	        readOnly:isReadOnly,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.crateRates'),//木箱费率
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.lowestTicketYuan'),//最低一票（元）
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 99999999.99,
	        minValue: 0
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单接货费（下部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.ReceivingChargesFormPanel', {
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
	        CACULATE_TYPE:pricing.expressRegionValueAdded.Weight,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.byWeight'),//按重量
	        name:'feeRate',
	        readOnly:isReadOnly,
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanKG'),//元/KG
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        allowBlank:false,
	        readOnly:isReadOnly,
	        CACULATE_TYPE:pricing.expressRegionValueAdded.Volume,
	        name:'feeRate',
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.byVolume'),//按体积
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuamm'),//元/m³
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'minFee',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.minimumCharge'),//最低收费
	        maxValue: 99999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        name:'maxFee',
	        readOnly:isReadOnly,
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.maximumCharge'),//最高收费
	        maxValue: 99999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单签收回单（下部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.SignFormPanel', {
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
	        subType:pricing.expressRegionValueAdded.YJFD,//标示其为“原件返单”
	        name:'feeRate',
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.originalBackToSingle'),//原件返单
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        readOnly:isReadOnly,
	        name:'feeRate',
	        subType:pricing.expressRegionValueAdded.CZFD,//标示其为“传真返单”
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.faxBackToASingle'),//传真返单
	        maxValue: 99999999.99,//数据库中存的是分，数据库中长度为10，这里单位是原，所以长度职位8位
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});

/**
 * 增值服务表单仓储费（下部）
 */
Ext.define('Foss.pricing.expressRegionValueAdded.StorageChargesFormPanel', {
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
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.charges'),//原件返单
	        maxValue: 99999999.99,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanmpiao'),//元/m³/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:2,
	        readOnly:isReadOnly,
	        name:'minFee',
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.minimumChargeQu'),//最低收取
	        maxValue: 99999999.99,
	        minValue: 0 
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		},{
			xtype:'numberfield',
	        decimalPrecision:0,
	        readOnly:isReadOnly,
	        name:'maxFee',
	        allowBlank:false,
	        fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.maximumCharge'),//最高收取
	        maxValue: 99999999,
	        minValue: 0
		},{
			xtype: 'label',
	        text: pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao'),//元/票
	        margins: '0 0 0 10'
		}];
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单其它费（下部）可编辑表格 pricing.expressRegionValueAdded.otherCharges = 'OTHERCHARGES';//其他费用
 */
Ext.define('Foss.pricing.expressRegionValueAdded.OtherChargesGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
	isreadOnlyFlag:false,
	setIsreadOnlyFlag:function(isreadOnlyFlag){
		this.isreadOnlyFlag = isreadOnlyFlag;
	},
    columns: [{
		text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items: [{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass:function(v,m,r,rowIndex){
					var grid = this.up('grid');
					if(grid.isreadOnlyFlag){
						return 'statementBill_hide';
					}else{
						return 'deppon_icons_delete';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
					grid.getStore().remove(record);
            		/*pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteOtherCostInformation'),function(e){//是否要删除这条其他费用信息？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				grid.getStore().remove(record);
            			}
            		});*/
                }
			}]
	  },{
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.name'),//名称
        dataIndex: 'subType',
        renderer:function(value,obj,record){
        	for(var i=0;i<pricing.expressRegionValueAdded.otherChargesSubType.length;i++){
        		if(pricing.expressRegionValueAdded.otherChargesSubType[i].code==value){
        			return pricing.expressRegionValueAdded.otherChargesSubType[i].name;
        		}
        	}
        },
        flex: 1,
        editor: {
        	queryMode: 'local',
    	    displayField: 'name',
    	    valueField: 'code',
    	    editable:false,
    	    allowBlank: false,
    	    store:pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceEntityModel',null
    	    ,pricing.expressRegionValueAdded.otherChargesSubType),
    	    xtype : 'combo'
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.imputationCategory'),//归集类别(现在还不清楚“归类级别是哪一个字段”暂存name中)
        dataIndex: 'subType',
        flex: 1,
        renderer:function(value,obj,record){
        	for(var i=0;i<pricing.expressRegionValueAdded.otherChargesSubType.length;i++){
        		if(pricing.expressRegionValueAdded.otherChargesSubType[i].code==value){
        			return pricing.expressRegionValueAdded.otherChargesSubType[i].blongPricingName;
        		}
        	}
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.sum'),//金额
        dataIndex: 'fee',
        width: 70,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: -99999999,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.amountLowerLimit'),//金额下限
        dataIndex: 'minFee',
        width: 70,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: -99999999,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.theMaximumAmount'),//金额上限
        dataIndex: 'maxFee',
        width: 70,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: -99999999,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.whetherItCanBeModified'),//是否可修改
        dataIndex: 'canmodify',
        width: 95,
        renderer: function(value){
        	if(value=='N'){
        		return pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.no');//否
        	}else if(value=='Y'){
        		return pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.ye');
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
    	    ,[{'key':'N','value':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.ye')}]),
            xtype : 'combo'
        }
    },{
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.whetherItCanBeDeleted'),//是否可删除
        dataIndex: 'candelete',
        width: 95,
        renderer: function(value){
        	if(value=='N'){
        		return pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.no');
        	}else if(value=='Y'){
        		return pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.ye');
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
    	    ,[{'key':'N','value':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.no')},{'key':'Y','value':pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.ye')}]),
            xtype : 'combo'
        }
    }],
    //新增一条其他费用默认值
    addOneModel:function(){
    	var me = this;
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
            subType:pricing.expressRegionValueAdded.otherChargesSubType[0].code,
            imputationCategory:pricing.expressRegionValueAdded.otherExpenses,
            feeRate:1,
            minFee:1,
            maxFee:1,
            canmodify:'Y',
            candelete:'Y' 
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
    		        text: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),
    		        handler : function(){
    		        	me.addOneModel();
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
		me.setIsreadOnlyFlag(isReadOnly);
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单送货费（下部）可编辑表格pricing.expressRegionValueAdded.deliveryCharges = 'SH';//送货费
 */
Ext.define('Foss.pricing.expressRegionValueAdded.DeliveryChargesGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
	isreadOnlyFlag:false,
	setIsreadOnlyFlag:function(isreadOnlyFlag){
		this.isreadOnlyFlag = isreadOnlyFlag;
	},
    columns: [{
		text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items: [{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass:function(v,m,r,rowIndex){
					var grid = this.up('grid');
					if(grid.isreadOnlyFlag){
						return 'statementBill_hide';
					}else{
						return 'deppon_icons_delete';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
					grid.getStore().remove(record);
            		/*pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteThisDeliveryCharge'),function(e){//是否要删除这条送货费信息？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				grid.getStore().remove(record);
            			}
            		});*/
                }
			}]
	  },{
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeStart'),//范围（起点）
        dataIndex: 'leftrange',
        flex: 1,
        renderer:function(value,obj,record){
          	if(record.get('caculateType')==pricing.expressRegionValueAdded.Weight){
        		return value+'kg';
        	}else if(record.get('caculateType')==pricing.expressRegionValueAdded.Volume){
        		return value+'m³';
        	}else{
        		return value;
        	}
        },
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
	        step:0.001,
            decimalPrecision:3,
            minValue: 0,
            maxValue: 9999999999.999
        }
        
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeEnd'),//范围（终点）
        dataIndex: 'rightrange',
        flex: 1,
        renderer:function(value,obj,record){
        	if(record.get('caculateType')==pricing.expressRegionValueAdded.Weight){
        		if(value==pricing.expressRegionValueAdded.CRITERIA_DETAIL_WEIGHT_MAX){
            		return pricing.expressRegionValueAdded.i18n('foss.pricing.noUpperLimit');//无上限
            	}
        	}else if(record.get('caculateType')==pricing.expressRegionValueAdded.Volume){
        		if(value==pricing.expressRegionValueAdded.CRITERIA_DETAIL_VOLUME_MAX){
            		return pricing.expressRegionValueAdded.i18n('foss.pricing.noUpperLimit');//无上限
            	}
        	}
        	if(record.get('caculateType')==pricing.expressRegionValueAdded.Weight){
        		return value+'kg';
        	}else if(record.get('caculateType')==pricing.expressRegionValueAdded.Volume){
        		return value+'m³';
        	}else{
        		return value;
        	}
        },
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            step:0.001,
            decimalPrecision:3,
            minValue: 0,
            maxValue: 9999999999.999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.theLowestVotes'),//最低一票
        dataIndex: 'minFee',
        width: 70,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:0,
            minValue: 0,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.charges'),//收费标准
        dataIndex: 'feeRate',
        width: 70,
        renderer:function(value,obj,record){
        	if(record.get('caculateType')==pricing.expressRegionValueAdded.Weight){
        		return value+pricing.expressRegionValueAdded.i18n('foss.pricing.yuankg');
        	}else if(record.get('caculateType')==pricing.expressRegionValueAdded.Volume){
        		return value+pricing.expressRegionValueAdded.i18n('foss.pricing.yuamm');
        	}else{
        		return value;
        	}
        },
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:3,
            minValue: 0,
            maxValue: 99999999.999
        }
    }],
    //新增重量一条默认值
    addWeightOneModel:function(){
    	var me = this;
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	leftrange:0,
        	rightrange:0,
        	minFee:1,
        	feeRate:0.01,
            caculateType:pricing.expressRegionValueAdded.Weight
        });
        me.store.insert(0,record);
        me.getPlugins()[0].startEditByPosition({row: 0, column: 0});
    },
    //新增体积一条默认值
    addVolumeOneModel:function(){
    	var me = this;
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	leftrange:0,
        	rightrange:0,
        	minFee:1,
        	feeRate:0.01,
            caculateType:pricing.expressRegionValueAdded.Volume
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
    		        text: pricing.expressRegionValueAdded.i18n('foss.pricing.inAccordanceWithTheWeightOfTheNew'),//按照重量新增
    		        handler : function(){
    		        	me.addWeightOneModel();
    		        }
    		    },{
    		        text: pricing.expressRegionValueAdded.i18n('foss.pricing.inAccordanceWithTheVolemeOfTheNew'),//按照体积新增
    		        handler : function(){
    		        	me.addVolumeOneModel();
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
		me.setIsreadOnlyFlag(isReadOnly);
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单代收货款（下部）可编辑表格pricing.expressRegionValueAdded.paymentCollection = 'HK';//代收货款
 */
Ext.define('Foss.pricing.expressRegionValueAdded.PaymentCollectionGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
	isreadOnlyFlag:false,
	setIsreadOnlyFlag:function(isreadOnlyFlag){
		this.isreadOnlyFlag = isreadOnlyFlag;
	},
    columns: [{
		text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
		xtype:'actioncolumn',
		align: 'center',
		width:80,
		items: [{
				iconCls: 'deppon_icons_delete',
                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
				width:42,
				getClass:function(v,m,r,rowIndex){
					var grid = this.up('grid');
					if(grid.isreadOnlyFlag){
						return 'statementBill_hide';
					}else{
						return 'deppon_icons_delete';
					}
				},
                handler: function(grid, rowIndex, colIndex) {
            		//获取选中的数据
    				var record=grid.getStore().getAt(rowIndex);
					grid.getStore().remove(record);
            		/*pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteThisCollectionMoney'),function(e){//是否要删除这条代收货款信息？
            			if(e=='yes'){//询问是否删除，是则发送请求
            				grid.getStore().remove(record);
            			}
            		});*/
                }
			}]
	  },{
    	header: pricing.expressRegionValueAdded.i18n('foss.pricing.refundType'),
        dataIndex: 'subType',
        width: 95,
        renderer: function(value){
        	var store = FossDataDictionary.getDataDictionaryStore(pricing.expressRegionValueAdded.returnSubType);
        	return pricing.changeCodeToNameStore(store,value);
        },
        editor: {
    		queryMode: 'local',
    	    displayField: 'valueName',
    	    valueField: 'valueCode',
    	    editable:false,
    	    store:FossDataDictionary.getDataDictionaryStore(pricing.expressRegionValueAdded.returnSubType),
    	    xtype : 'combo'
        }
    },{
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.purchasePriceRangeStartingPoint'),//货物重量范围（起点）
        dataIndex: 'leftrange',
        flex: 1,
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
			decimalPrecision:3,
	        step:0.001,
            minValue: 0,
            maxValue: 99999999
        }
        
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.paymentRangeEnd'),//货物重量范围（终点）
        dataIndex: 'rightrange',
        flex: 1,
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:3,
	        step:0.001,
            minValue: 0,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.theLowestVotes'),//最低一票
        dataIndex: 'minFee',
        width: 65,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: 0,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.upVotes'),//最高一票
        dataIndex: 'maxFee',
        width: 65,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            minValue: 0,
            maxValue: 99999999
        }
    }, {
        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rates'),//费率‰
        dataIndex: 'feeRate',
        width: 65,
        //align: 'right',
        editor: {
            xtype: 'numberfield',
            allowBlank: false,
            decimalPrecision:4,
            step:0.0001,
            minValue: 0,
            maxValue: 1
        }
    }],
    //新增代收货款一条默认值
    addOneModel:function(){
    	var me = this;
    	var store = FossDataDictionary.getDataDictionaryStore(pricing.expressRegionValueAdded.returnSubType);
    	var subType = '';
    	if(!Ext.isEmpty(store)){
    		subType = store.getAt(0).get('valueCode');
    	}
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	'subType':subType,
        	'leftrange':0,
        	'rightrange':0,
        	'minFee':1,
        	'maxFee':1,
        	'feeRate':0.0001
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
    		        text: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),//新增
    		        handler : function(){
    		        	me.addOneModel();
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
		me.setIsreadOnlyFlag(isReadOnly);
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});
/**
 * 增值服务表单超远派送（下部）可编辑表格pricing.expressRegionValueAdded.superDelivery = 'CY';//超远派送费
 */
Ext.define('Foss.pricing.expressRegionValueAdded.SuperDeliveryGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增超远派送一条默认值
    addOneModel:function(){
    	var me = this;
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	'leftrange':0,
        	'rightrange':0,
        	'fee':20
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
    		        text: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),//新增
    		        handler : function(){
    		        	me.addOneModel();
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
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
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
						grid.getStore().remove(record);
	            		/*pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteFarDeliveryInformation'),function(e){//是否要删除这条超远派送信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});*/
	                }
				}]
		  },{
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeStart'),//货物重量范围（起点）
	        dataIndex: 'leftrange',
	        flex: 1,
	        renderer:function(value){
	        	return value+pricing.expressRegionValueAdded.i18n('foss.pricing.km');//公里
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:3,
	            step:0.001,
	            minValue: 0,
	            maxValue: 99999999
	        }
	        
	    }, {
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeEnd'),//货物重量范围（终点）
	        dataIndex: 'rightrange',
	        flex: 1,
	        renderer:function(value){
	        	return value+pricing.expressRegionValueAdded.i18n('foss.pricing.km');//公里
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:3,
	            step:0.001,
	            minValue: 0,
	            maxValue: 99999999
	        }
	    },{
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.charges'),//收费标准
	        dataIndex: 'fee',
	        width: 65,
	        renderer:function(value){
	        	return value+pricing.expressRegionValueAdded.i18n('foss.pricing.yuan');//元
	        },
	        //align: 'right',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:3,
	            minValue: 0,
	            step:0.001,
	            maxValue: 99999999.999
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});

/**
 * 增值服务表单送货上楼（下部）可编辑表格pricing.expressRegionValueAdded.deliveryUpstairs = 'SHSL';//送货上楼
 */
Ext.define('Foss.pricing.expressRegionValueAdded.DeliveryUpstairsGridPanel', {
	extend : 'Ext.grid.Panel',
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	frame: true,
    hidden:true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	flex:1,
    //新增送货上楼一条默认值
    addOneModel:function(){
    	var me = this;
        var record = Ext.create('Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity', {
        	'leftrange':0,
        	'rightrange':0,
        	'minFee':1,
        	'feeRate':0.001
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
    		        text: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.add'),//新增
    		        handler : function(){
    		        	me.addOneModel();
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
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.opra'),//操作
			xtype:'actioncolumn',
			align: 'center',
			width:80,
			items: [{
					iconCls: 'deppon_icons_delete',
	                tooltip: pricing.expressRegionValueAdded.i18n('foss.pricing.delete'),//删除
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
						grid.getStore().remove(record);
	            		/*pricing.showQuestionMes(pricing.expressRegionValueAdded.i18n('foss.pricing.wantDeleteThisDeliveryUpstairs'),function(e){//是否要删除这条送货上楼信息？
	            			if(e=='yes'){//询问是否删除，是则发送请求
	            				grid.getStore().remove(record);
	            			}
	            		});*/
	                }
				}]
		  },{
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeStart'),//范围（起点）
	        dataIndex: 'leftrange',
	        flex: 1,
	        renderer:function(value){
	        	return value+'KG';
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
				decimalPrecision:3,
	            step:0.001,
	            minValue: 0,
	            maxValue: 99999999
	        }
	        
	    }, {
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rangeEnd'),//范围（终点）
	        dataIndex: 'rightrange',
	        flex: 1,
	        renderer:function(value){
	        	return value+'KG';
	        },
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:3,
	            step:0.001,
	            minValue: 0,
	            maxValue: 99999999
	        }
	    }, {
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.theLowestVotes'),//最低一票
	        dataIndex: 'minFee',
	        flex: 1,
	        renderer:function(value){
	        	return value+pricing.expressRegionValueAdded.i18n('foss.pricing.yuanpiao');//元/票
	        },
	        //align: 'right',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            minValue: 0,
	            maxValue: 99999999
	        }
	    }, {
	        header: pricing.expressRegionValueAdded.i18n('foss.pricing.rates'),//费率
	        dataIndex: 'feeRate',
	        flex: 1,
	        renderer:function(value){
	        	return value+pricing.expressRegionValueAdded.i18n('foss.pricing.yuanKG');//元/票
	        },
	        //align: 'right',
	        editor: {
	            xtype: 'numberfield',
	            allowBlank: false,
	            decimalPrecision:3,
	            step:0.001,
	            minValue: 0,
	            maxValue: 99999999.999
	        }
	    }];
		me.plugins=me.getPlugins(isReadOnly);
		me.getTbar(isReadOnly);//获取新增按钮
	    me.store = pricing.getStore(null,'Foss.pricing.expressRegionValueAdded.PriceCriteriaDetailEntity',null,[]);
		me.callParent([cfg]);
	}
});

/**
 * 区域Store
 */
Ext.define('Foss.pricing.expressRegionValueAdded.AreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.expressRegionValueAdded.AreaModel',
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchRegionValueAddByConditionExpress.action'),
		reader : {
			type : 'json',
			root : 'regionValueAddVo.regionEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 区域列表
 */
Ext.define('Foss.pricing.expressRegionValueAdded.AreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : pricing.expressRegionValueAdded.i18n('foss.pricing.departureDestinationArea'),//出发地/目的地区域
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionNum'),//区域编号
			dataIndex : 'regionCode'
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionName'),//区域名称
			dataIndex : 'regionName'
		}];
		me.store = Ext.create('Foss.pricing.expressRegionValueAdded.AreaStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getSearchRegionForm();
					if(queryForm!=null){
						var regionName = queryForm.getForm().findField('regionName').getValue();
						if (!Ext.isEmpty(regionName)) {
							regionName = "%" + regionName + "%";
						}
						Ext.apply(operation,{
							params : {
								'regionValueAddVo.regionEntity.regionName' : regionName,//区域名称
								'regionValueAddVo.regionEntity.regionCode' : queryForm.getForm().findField('regionCode').getValue(),//区域编号
								'regionValueAddVo.regionEntity.active' : 'Y',//之查询激活的
								//查询的只是价格区域
								'regionValueAddVo.regionEntity.regionNature':pricing.expressRegionValueAdded.PRESCRIPTION_REGION
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
					mode:'SINGLE',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * 区域查询WINDOW
 */
Ext.define('Foss.pricing.expressRegionValueAdded.RegionGridShowWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressRegionValueAdded.i18n('foss.pricing.queryOriginDestination'),//查询出发地/目的地
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :515,
	textField:null,//对应的是哪个textField区点击选择
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getSearchRegionForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getRegionGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
		}
	},
	//返回列表
	returnGrid:function(){
		var me = this;
		me.close();
	},
	//选择区域
	selectRegion:function(){
		var me = this;
		var selections = me.getRegionGridPanel().getSelectionModel().getSelection();//获取选择的数据
		  //me.textField.regionId = '1';
		 //me.textField.setValue(pricing.expressRegionValueAdded.i18n('foss.pricing.shanghai'));设置数据成功，只用来做测试
		if(selections.length==0){
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectRegion'));//请选择一个区域！
			return;
		}
        me.textField.setValue(selections[0].get('regionName'));
        me.textField.regionId = selections[0].get('id');
        me.hide();
	},
	//查询的FORM
	searchRegionForm:null,
	getSearchRegionForm:function(){
		if(Ext.isEmpty(this.searchRegionForm)){
    		this.searchRegionForm = Ext.create('Foss.pricing.expressRegionValueAdded.SearchRegionForm');
    	}
		return this.searchRegionForm;
	},
	//区域的GRID
	regionGridPanel:null,
	getRegionGridPanel:function(){
		if(Ext.isEmpty(this.regionGridPanel)){
    		this.regionGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.AreaGridPanel');
    	}
		return this.regionGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getSearchRegionForm(), me.getRegionGridPanel()];
		me.fbar = [{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.determine'),//确定
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.selectRegion();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 快递区域Store
 */
Ext.define('Foss.pricing.expressRegionValueAdded.ExpressAreaStore', {
	extend : 'Ext.data.Store',
	model : 'Foss.pricing.expressRegionValueAdded.AreaModel',
	autoLoad:false,//不需要自动加载
	pageSize:5,
	proxy : {
		type : 'ajax',
		actionMethods : 'post',
		url : pricing.realPath('searchRegionExpressByCondition.action'),
		reader : {
			type : 'json',
			root : 'regionExpressVo.regionEntityList',
			totalProperty : 'totalCount'
		}
	}
});
/**
 * 快递区域列表
 */
Ext.define('Foss.pricing.expressRegionValueAdded.ExpressAreaGridPanel', {
	extend: 'Ext.grid.Panel',
	title : '快递出发地/目的地区域列表',//出发地/目的地区域
	frame: true,
	flex:1,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	//得到bbar
	pagingToolbar : null,
	getPagingToolbar : function() {
		if (this.pagingToolbar == null) {
			this.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : this.store,
				pageSize : 5
			});
		}
		return this.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.columns = [{xtype: 'rownumberer',
			width:40,
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.num')//序号
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionNum'),//区域编号
			dataIndex : 'regionCode'
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionName'),//区域名称
			dataIndex : 'regionName'
		}];
		me.store = Ext.create('Foss.pricing.expressRegionValueAdded.ExpressAreaStore',{
			autoLoad:false,//不需要自动加载
			listeners: {
				beforeload: function(store, operation, eOpts){
					var queryForm = me.up('window').getSearchRegionForm();
					if(queryForm!=null){
						Ext.apply(operation,{
							params : {
								'regionExpressVo.regionEntity.regionName' : "%"+queryForm.getForm().findField('regionName').getValue()+"%",//区域名称
								'regionExpressVo.regionEntity.regionCode' : queryForm.getForm().findField('regionCode').getValue(),//区域编号
								'regionExpressVo.regionEntity.regionNature' : pricing.expressRegionValueAdded.PRICING_REGION,//之查询激活的
								'regionExpressVo.regionEntity.active' : 'Y'//之查询激活的
							}
						});	
					}
				}
		    }
		});
		me.listeners = {
	    	scrollershow: function(scroller) {
	    		if (scroller && scroller.scrollEl) {
	    				scroller.clearManagedListeners(); 
	    				scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
	    		}
	    	}
	    },
		me.selModel = Ext.create('Ext.selection.CheckboxModel',{//单选框
					mode:'SINGLE',
					checkOnly:true
				});
		me.bbar = me.getPagingToolbar();
		me.getPagingToolbar().store = me.store;
		me.callParent([cfg]);
	}
});

/**
 * 快递区域查询WINDOW
 */
Ext.define('Foss.pricing.expressRegionValueAdded.ExpressRegionGridShowWindow',{
	extend : 'Ext.window.Window',
	title : '查询出发地/目的地',
	closable : true,
	modal : true,
	resizable:false,
	closeAction : 'hide',
	width :550,
	height :515,
	textField:null,//对应的是哪个textField区点击选择
	layout : {
		type : 'vbox',
		align : 'stretch'
	},
	listeners:{
		beforehide:function(me){//在隐藏之前清除掉所有加载的数据
			me.getSearchRegionForm().getForm().reset();
			me.textField = null;
			
		},
		beforeshow:function(me){
			me.getRegionGridPanel().getPagingToolbar().moveFirst();//确保查询第一页的数据
		}
	},
	//返回列表
	returnGrid:function(){
		var me = this;
		me.close();
	},
	//选择区域
	selectRegion:function(){
		var me = this;
		var selections = me.getRegionGridPanel().getSelectionModel().getSelection();//获取选择的数据
		  //me.textField.regionId = '1';
		 //me.textField.setValue(pricing.expressRegionValueAdded.i18n('foss.pricing.shanghai'));设置数据成功，只用来做测试
		if(selections.length==0){
			pricing.showWoringMessage(pricing.expressRegionValueAdded.i18n('foss.pricing.pleaseSelectRegion'));//请选择一个区域！
			return;
		}
        me.textField.setValue(selections[0].get('regionName'));
        me.textField.regionId = selections[0].get('id');
        me.hide();
	},
	//查询的FORM
	searchRegionForm:null,
	getSearchRegionForm:function(){
		if(Ext.isEmpty(this.searchRegionForm)){
    		this.searchRegionForm = Ext.create('Foss.pricing.expressRegionValueAdded.SearchRegionForm');
    	}
		return this.searchRegionForm;
	},
	//区域的GRID
	regionGridPanel:null,
	getRegionGridPanel:function(){
		if(Ext.isEmpty(this.regionGridPanel)){
    		this.regionGridPanel = Ext.create('Foss.pricing.expressRegionValueAdded.ExpressAreaGridPanel');
    	}
		return this.regionGridPanel;
	}, 
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getSearchRegionForm(), me.getRegionGridPanel()];
		me.fbar = [{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.returnGrid'),//返回列表
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.determine'),//确定
			cls:'yellow_button',
			margin:'0 0 0 365',
			handler :function(){
				me.selectRegion();
			} 
		}];
		me.callParent([cfg]);
	}
});

/**
 * 查询区域
 */
Ext.define('Foss.pricing.expressRegionValueAdded.SearchRegionForm', {
	extend : 'Ext.form.Panel',
	title: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.searchCondition'),
	frame: true,
	collapsible: true,
    defaults : {
    	columnWidth : .4,
    	margin : '8 10 5 10',
    	labelSeparator:'',
    	labelWidth:60,
    	anchor : '100%'
    },
    height :90,
	defaultType : 'textfield',
	layout:'column',
	constructor : function(config) {//构造器
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [{
			xtype:'textfield',
			fieldLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionName'),//区域名称
			name:'regionName'
		},{
			xtype:'textfield',
			fieldLabel:pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.regionCode'),//区域编码
			name:'regionCode'
		},{
			xtype : 'container',
			margin : '0 0 0 0',
			columnWidth : .2,
			items : [{
				xtype : 'button', 
				width:70,
				cls:'yellow_button',
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.search'),//查询按钮
				handler : function() {
					if(me.getForm().isValid()){
						var grid  = me.up('window').getRegionGridPanel();
						grid.getPagingToolbar().moveFirst();//用分页的moveFirst()方法
					}
				}
			}]
		}];
		me.callParent([cfg]);
	}
});
/**
 * 修改弹窗-设置终止时间
 */
Ext.define('Foss.pricing.expressRegionValueAdded.expressRegionValueAddedEndTimeWindow',{
	extend : 'Ext.window.Window',
	title : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.setEndTime'),
	closable : true,
	modal : true,
	resizable:false,
	parent:null,//(Foss.pricing.expressRegionValueAdded.PricingRegionValuationGridPanel)
	closeAction : 'hide',
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
    			minValue: new Date(pricing.expressRegionValueAdded.tomorrowTime),
    			//labelSeparator:'',
    	    	labelWidth:60,
    			fieldLabel: pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.endTime'),//失效日期
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
				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
			}else{
				pricing.showErrorMes(json.message);
			}
		};
		var url = pricing.realPath('updatePriceValuationExpress.action');
		pricing.requestJsonAjax(url,params,successFun,failureFun);
    },
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [me.getEndTimeField()];
		me.fbar = [{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.cancel'),//取消
			handler :function(){
				me.close();
			} 
		},{
			text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.determine'),//确认
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
Ext.define('Foss.pricing.expressRegionValueAdded.ImmediatelyStopEndTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.expressRegionValueAdded.i18n('foss.pricing.immediatelySupendPricePriceScheme'),//"立即中止方案",
		width:380,
		height:152,
		closeAction: 'hide' ,
		stopFormPanel:null,
		parent:null,
		getStopFormPanel : function(){
	    	if(Ext.isEmpty(this.stopFormPanel)){
	    		this.stopFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.ImmediatelyStopFormPanel');
	    	}
	    	return this.stopFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.priceValuationEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.priceValuationEntity.endTime), 'Y-m-d');
	    		var value = pricing.expressRegionValueAdded.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.expressRegionValueAdded.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.expressRegionValueAdded.i18n('foss.pricing.showrightEndTimeInfo');
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
Ext.define('Foss.pricing.expressRegionValueAdded.ImmediatelyStopFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	stop:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		var priceValuationEntityModel = new Foss.pricing.expressRegionValueAdded.PriceValuationEntityModel();
			form.updateRecord(priceValuationEntityModel);
			var id = me.up('window').priceValuationEntity.id;
			priceValuationEntityModel.set('id',id);
			priceValuationEntityModel.set('endTime',Ext.Date.parse(form.findField('endTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricingValuationVo':{'priceValuationEntity':priceValuationEntityModel.data}};
    		var url = pricing.realPath('stopFastExpress.action');
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
				fieldLabel :pricing.expressRegionValueAdded.i18n('foss.pricing.suspendTime') ,//'中止日期',
				name : 'endTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				id : 'Foss_expressRegionValueAdded_stopEndTime_ID',
				dateConfig: {
					el : 'Foss_expressRegionValueAdded_stopEndTime_ID-inputEl'
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
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.determine'),//"确认",
				handler : function() {
					me.stop();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.cancel'),//"取消",
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
Ext.define('Foss.pricing.expressRegionValueAdded.ImmediatelyActiveTimeWindow',{
		extend: 'Ext.window.Window',
		title: pricing.expressRegionValueAdded.i18n('foss.pricing.immediatelyActiveationPriceScheme'),//"立即激活方案",
		width:380,
		height:152,
		priceValuationEntity:null,
		closeAction: 'hide' ,
		immediatelyActiveFormPanel:null,
		getImmediatelyActiveFormPanel : function(){
	    	if(Ext.isEmpty(this.immediatelyActiveFormPanel)){
	    		this.immediatelyActiveFormPanel = Ext.create('Foss.pricing.expressRegionValueAdded.ImmediatelyActiveFormPanel');
	    	}
	    	return this.immediatelyActiveFormPanel;
	    },
	    listeners:{
	    	beforeshow:function(me){
	    		var showbeginTime = Ext.Date.format(new Date(me.priceValuationEntity.beginTime), 'Y-m-d');
	    		var showendTime = 	Ext.Date.format(new Date(me.priceValuationEntity.endTime), 'Y-m-d');
	    		var value = pricing.expressRegionValueAdded.i18n('foss.pricing.showleftTimeInfo')
				  +showbeginTime+pricing.expressRegionValueAdded.i18n('foss.pricing.showmiddleTimeInfo')
				  +showendTime+pricing.expressRegionValueAdded.i18n('foss.pricing.showrightEndTimeInfo');
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
Ext.define('Foss.pricing.expressRegionValueAdded.ImmediatelyActiveFormPanel',{
	extend : 'Ext.form.Panel',
	layout:'column',
	activetion:function(){
		var me = this;
    	var form = me.getForm();
    	if(form.isValid()){
    		
    		//判断选择的激活时间不能早于当前时间
    		var nowDate = new Date();
    		var selectedDate = Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s');
    		if(selectedDate<nowDate){
    			pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('foss.pricing.activeTimeIsMustBeforeStopTime'));
    			return false;
    		}
    		
			var priceValuationEntityModel = new Foss.pricing.expressRegionValueAdded.PriceValuationEntityModel();
			form.updateRecord(priceValuationEntityModel);
			var id = me.up('window').priceValuationEntity.id;
			priceValuationEntityModel.set('id',id);
			priceValuationEntityModel.set('beginTime',Ext.Date.parse(form.findField('beginTime').getValue(), 'Y-m-d H:i:s'));
    		var params = {'pricingValuationVo':{'priceValuationEntity':priceValuationEntityModel.data}};
    		var url = pricing.realPath('activeFastExpress.action');
    		var successFun = function(json){
    			pricing.showInfoMes(json.message);
    			me.up('window').hide();
				me.up('window').parent.getPagingToolbar().moveFirst();  			
    	    };
    	    var failureFun = function(json){
    	    	if(Ext.isEmpty(json)){
    				pricing.showErrorMes(pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.requestTimeOut'));
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
				fieldLabel:pricing.expressRegionValueAdded.i18n('foss.pricing.availabilityDate'),//'生效日期',
				name : 'beginTime',
				xtype : 'datetimefield_date97',
				editable:false,
				time : true,
				allowBlank:false,
				id : 'Foss_expressRegionValueAdded_activeEndTime_ID',
				dateConfig: {
					el : 'Foss_expressRegionValueAdded_activeEndTime_ID-inputEl'
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
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.determine'),//,"确认",
				handler : function() {
					me.activetion();
				}
			},{
				xtype : 'button', 
				width:70,
				columnWidth:.15,
				text : pricing.expressRegionValueAdded.i18n('i18n.pricingRegion.cancel'),//"取消",
				handler : function() {
					me.up('window').hide();
				}
			}];
        this.callParent(cfg);
    }
});