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
 * PROJECT NAME	: pkp-changingexp
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/changingexp/client/utils/WaybillDtoFactory.java
 * 
 * FILE NAME        	: WaybillDtoFactory.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.changingexp.client.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.changingexp.client.ui.internal.IncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.util.define.FossConstants;

public class WaybillDtoFactory {

	/**
	 * 
	 * 获取费用明细
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:25:47
	 */
	public static   List<WaybillChargeDtlEntity> getWaybillChargeDtlEntity(
			WaybillOtherCharge model, WaybillPanelVo vo) {
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		if (vo.getRefundType() != null && vo.getRefundType().getValueCode() != null) {
			chargeDtlEntityList.add(getCod(vo));// 添加代收货款费用明细
		}

		getDeliveryCharge(vo,chargeDtlEntityList);// 添加送货费
		
		if (vo.getPickupFee() != null
				&& vo.getPickupFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPickUpCharge(vo));// 添加接货费
		}

		if (vo.getInsuranceCode() != null && !"".equals(vo.getInsuranceCode())) {
			chargeDtlEntityList.add(getInsurance(vo));// 添加保险费
		}
		
		if (vo.getPackageFee() != null
				&& vo.getPackageFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPackageCharge(vo));// 添加包装费
		}
		
		

		getOtherCharge(vo, chargeDtlEntityList, model);// 添加其他费用
		
		if(vo.getTransportFee()!=null)
		{
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode("FRT");
			chargeEntity.setAmount(vo.getTransportFee());
			chargeEntity.setWaybillNo(vo.getWaybillNo());
			chargeEntity.setPricingCriDetailId(vo.getCodId());
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeEntity.setPricingCriDetailId("4545");
			chargeDtlEntityList.add(chargeEntity);
		}
		
		return chargeDtlEntityList;
	}

	/**
	 * 
	 * 获得代收货款
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	public static  WaybillChargeDtlEntity getCod(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(vo.getCodCode());
		chargeEntity.setAmount(vo.getCodFee());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setPricingCriDetailId(vo.getCodId());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}

	/**
	 * 
	 * 获取送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	public static  void getDeliveryCharge(WaybillPanelVo vo,List<WaybillChargeDtlEntity> chargeDtlEntityList) {

		//送货费集合
		List<DeliverChargeEntity> list = vo.getDeliverList();
		if(list != null && !list.isEmpty())
		{
			for(int i=0;i<list.size();i++)
			{
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				DeliverChargeEntity deliverCharge = list.get(i);
				//送货费编码
				chargeEntity.setPricingEntryCode(deliverCharge.getCode());
				chargeEntity.setAmount(deliverCharge.getAmount());
				chargeEntity.setWaybillNo(deliverCharge.getWaybillNo());
				chargeEntity.setPricingCriDetailId(deliverCharge.getId());
				chargeEntity.setActive(deliverCharge.getActive());
				chargeEntity.setCurrencyCode(deliverCharge.getCurrencyCode());
				chargeDtlEntityList.add(chargeEntity);
			}
		}
	}
	
	/**
	 * 
	 * 获取接货费
	 * @author 025000-FOSS-helong
	 * @date 2013-2-1 下午05:02:37
	 * @param vo
	 * @return
	 */
	public static  WaybillChargeDtlEntity getPickUpCharge(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_JH);
		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
		chargeEntity.setAmount(vo.getPickupFee());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}
	
	
	/**
	 * 
	 * 获取包装费
	 * @author 025000-FOSS-helong
	 * @date 2013-2-1 下午05:13:52
	 * @param vo
	 * @return
	 */
	public static  WaybillChargeDtlEntity getPackageCharge(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
		chargeEntity.setAmount(vo.getPackageFee());
		chargeEntity.setPricingCriDetailId("shoudongshuruid");
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}

	/**
	 * 
	 * 获取保价费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	public static  WaybillChargeDtlEntity getInsurance(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(vo.getInsuranceCode());
		chargeEntity.setAmount(vo.getInsuranceFee());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setPricingCriDetailId(vo.getInsuranceId());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}
	


	/**
	 * 
	 * 获取其它费用信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	public static  void getOtherCharge(WaybillPanelVo vo,
			List<WaybillChargeDtlEntity> list, WaybillOtherCharge model) {
		List<OtherChargeVo> data = model.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			for (OtherChargeVo otherVo : data) {
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setPricingEntryCode(otherVo.getCode());
				chargeEntity.setAmount(new BigDecimal(otherVo.getMoney()));
				chargeEntity.setWaybillNo(vo.getWaybillNo());
				if(otherVo.getId()!=null){
					chargeEntity.setPricingCriDetailId(otherVo.getId());
				}else{
					//如果数据库里面没有id 就使用一个其他id算了 参考FRT其他费用写法
					chargeEntity.setPricingCriDetailId("4567");
				}
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				list.add(chargeEntity);
			}
		} else {
			// 不做业务处理
		}
	}

}