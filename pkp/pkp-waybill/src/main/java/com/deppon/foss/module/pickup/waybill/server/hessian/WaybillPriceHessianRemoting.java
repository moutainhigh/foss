/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillPriceHessianRemoting.java
 * 
 * FILE NAME        	: WaybillPriceHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPaperFiberPackingTableService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceEntryService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IProductService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PriceFibelPaperPackingEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectiveExpressPlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.EffectivePlanDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ProductPriceDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPriceHessianRemoting;

/**
 * 
 * 价格远程服务
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:foss-sunrui,date:2012-11-5 下午8:26:44
 * </p>
 * 
 * @author foss-sunrui
 * @date 2012-11-5 下午8:26:44
 * @since
 * @version
 */
@Remote()
public class WaybillPriceHessianRemoting implements IWaybillPriceHessianRemoting {

	/**
	 * 价格计算服务
	 */
	@Resource
	private IBillCaculateService billCaculateService;
	
	/**
	 * 计价条目服务
	 */
	@Resource
	private IPriceEntryService priceEntryService;
	
	/**
	 *@param priceEntryService the priceEntryService to set.
	 */
	public void setPriceEntryService(IPriceEntryService priceEntryService) {
		this.priceEntryService = priceEntryService;
	}
	
	/**
	 * 纸纤包装单价信息服务
	 * @author：218371-foss-zhaoyanjun
	 * @date:2014-11-28下午17:05
	 */
	@Resource
	IPaperFiberPackingTableService paperFiberPackingTableService;
	
	public IPaperFiberPackingTableService getPaperFiberPackingTableService() {
		return paperFiberPackingTableService;
	}

	public void setPaperFiberPackingTableService(
			IPaperFiberPackingTableService paperFiberPackingTableService) {
		this.paperFiberPackingTableService = paperFiberPackingTableService;
	}
	
	@Resource
	IProductService productService;
	
	public IProductService getProductService() {
		return productService;
	}
	
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	

	/**
	 * 
	 * <p>
	 * 计算运费
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-6 上午11:34:38
	 * @param originalOrgCode
	 *            出发部门
	 * @param destinationOrgCode
	 *            到达部门
	 * @param productCode
	 *            产品
	 * @param goodsCode
	 *            货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
	 * @param receiveDate
	 *            营业部收货日期（可选，无则表示当前日期）,即开单日期
	 * @param isReceiveGoods
	 *            是否接货（可选，无则表示非接货）
	 * @param weight
	 *            重量
	 * @param volume
	 *            体积
	 * @param flightShift
	 *            航班班次
	 * @parm currencyCdoe 币种
	 * @return
	 * @see
	 */
	@Override
	public List<ProductPriceDto> queryProductPriceList(QueryBillCacilateDto queryDto) {
		if(queryDto==null){
			return null;
		}
		
		if(queryDto != null && productService.onlineDetermineIsExpressByProductCode(queryDto.getProductCode(), queryDto.getBillTime())){
			return billCaculateService.searchExpressProductPriceList(queryDto);
		}else{
			return billCaculateService.searchProductPriceList(queryDto);
		}
	
	}

	/**
	 * 
	 * <p>
	 * 计算增值服务费
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-6 上午11:35:10
	 * @param originalOrgCode
	 *            出发部门
	 * @param destinationOrgCode
	 *            到达部门
	 * @param productCode
	 *            产品
	 * @param goodsCode
	 *            货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
	 * @param receiveDate
	 *            营业部收货日期（可选，无则表示当前日期）
	 * @param weight
	 *            重量
	 * @param volume
	 *            体积
	 * @param originnalCost
	 *            费用（分）
	 * @param flightShift
	 *            航班
	 * @param longOrShort
	 *            长途 还是短途 参考 PricingConstants 常量 LONG_OR_SHORT_L LONG_OR_SHORT_S
	 * @param priceEntityCode
	 *            增值服务费用代码 请参考 PricingConstants 常量定义
	 * @param priceEntityName
	 *            增值服务费用 名称 支持模糊查询
	 * @param subType
	 *            子类型 对于代收货款：为退款类型(即日退，三日退，审核退) 对于签收回单：为返单类型（传真返单，原件返单）
	 *            对于其它费用：为费用类型名称（综合信息费，燃油附加费，中转费等） *
	 * @param currencyCdoe
	 *            货币类型
	 * @return
	 * @see
	 */
	@Override
	public List<ValueAddDto> queryValueAddPriceList(QueryBillCacilateValueAddDto queryDto) {
		if(queryDto != null && !productService.onlineDetermineIsExpressByProductCode(
				queryDto.getProductCode(), queryDto.getBillTime())){
			return billCaculateService.searchValueAddPriceList(queryDto);
		}else{
			return billCaculateService.searchExpressValueAddPriceList(queryDto);
		}
	}
	
	/**	
	 * 根据条目entryCodes批量查询条目信息,entryCode设置请参照常量类PricingConstants
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-12 下午5:14:07
	 */
	@Override
	public Map<String,String> queryPriceEntryNameByEntryCodes(List<String> entryCodes){
		return priceEntryService.queryPriceEntryNameByEntryCodes(entryCodes);
	}

	/**
	 * 
	 * <p>
	 * 查询产品时效
	 * </p>
	 * 
	 * @author foss-sunrui
	 * @date 2012-11-6 上午11:33:40
	 * @param originalOrgCode
	 *            出发部门
	 * @param destinationOrgCode
	 *            到达部门
	 * @param productCode
	 *            产品code
	 * @parm billDate 开单日期 可空 ，默认为当前时间
	 * @return
	 * @see
	 */
	@Override
	public List<EffectivePlanDto> queryEffectivePlanDetailList(String originalOrgCode, String destinationOrgCode, String productCode, Date billDate) {
		if(!productService.onlineDetermineIsExpressByProductCode(productCode, billDate)){
			return billCaculateService.searchEffectivePlanDetailList(originalOrgCode, destinationOrgCode, productCode, billDate);

		}else{
			List<EffectiveExpressPlanDto> expressList =  billCaculateService.searchExpressEffectivePlanDetailList(originalOrgCode, destinationOrgCode, productCode, billDate);
			List<EffectivePlanDto> list = new ArrayList<EffectivePlanDto>();
			if(expressList!=null && expressList.size()>0){
				for(int i = 0 ;i<expressList.size(); i++){
					EffectiveExpressPlanDto d = expressList.get(i);
					EffectivePlanDto d2 =new EffectivePlanDto();
					try {
						PropertyUtils.copyProperties(d2, d);
					} catch (Exception e) {
						e.printStackTrace();
					} 
					list.add(d2);
				}
			}
			return list;

		}
	}
	
	/**
	 * 通过id查询纸纤包装单价信息
	 * @author:218371-foss-zhaoyanjun
	 * @date:2014-11-26下午16:39
	 */
	@Override
	public PriceFibelPaperPackingEntity queryPriceFibelPaperPackingEntity(
			String id) {
			return paperFiberPackingTableService.selectByValuationIdAndCode(id);
	}
	
	/**
	 * 根据code查询对应费用name
	 */
	public String queryNameByCode(String entryCode) {
		String name = priceEntryService.queryPriceEntryNameByCode(entryCode);
		if (com.deppon.foss.framework.shared.util.string.StringUtil.isNotBlank(name)) {
			return name;
		}
		return "";
	}
}