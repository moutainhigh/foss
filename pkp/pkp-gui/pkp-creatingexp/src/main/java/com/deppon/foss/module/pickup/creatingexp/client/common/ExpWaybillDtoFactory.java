/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWaybillDtoFactory {

	/**
	 * 
	 * 获取费用明细
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:25:47
	 */
	public static List<WaybillChargeDtlEntity> getWaybillChargeDtlEntity(WaybillOtherCharge model, WaybillPanelVo vo) {
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		if (vo.getRefundType() != null && vo.getRefundType().getValueCode() != null) {
			chargeDtlEntityList.add(getCod(vo));// 添加代收货款费用明细
		}

		getDeliveryCharge(vo, chargeDtlEntityList);// 添加送货费

		if (vo.getPickupFee() != null && vo.getPickupFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPickUpCharge(vo));// 添加接货费
		}

		if (vo.getInsuranceCode() != null && !"".equals(vo.getInsuranceCode())) {
			chargeDtlEntityList.add(getInsurance(vo));// 添加保险费
		}

		if (vo.getPackageFee() != null && vo.getPackageFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPackageCharge(vo));// 添加包装费
		}

		if (vo.getStandCharge() != null && vo.getStandCharge().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getStandardPackCharge(vo));// 添加打木架费用
		}

		if (vo.getBoxCharge() != null && vo.getBoxCharge().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getBoxPackCharge(vo));// 添加打木箱费用
		}

		if (vo.getTransportFee() != null && vo.getTransportFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getTransportFee(vo));// 添加公布价运费
		}
		getOtherCharge(vo, chargeDtlEntityList, model);// 添加其他费用
		return chargeDtlEntityList;
	}

	/**
	 * 
	 * 获取公布价运费
	 * @author foss-sunrui
	 * @date 2013-3-14 下午1:56:59
	 * @param vo
	 * @return
	 * @see
	 */
	public static WaybillChargeDtlEntity getTransportFee(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		if(vo.getIsWholeVehicle()){
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
    		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
		}else{
    		chargeEntity.setPricingEntryCode(vo.getTransportFeeCode());
    		chargeEntity.setPricingCriDetailId(vo.getTransportFeeId());
		}
		chargeEntity.setAmount(vo.getTransportFee());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
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
	public static WaybillChargeDtlEntity getPackageCharge(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
		chargeEntity.setAmount(vo.getPackageFee());
		chargeEntity.setPricingCriDetailId(WaybillConstants.HAND_INPUT);
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}
	
	/**
	 * 
	 * 获取打木架费用
	 * @author 025000-FOSS-helong
	 * @date 2013-2-1 下午05:13:52
	 * @param vo
	 * @return
	 */
	public static WaybillChargeDtlEntity getStandardPackCharge(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(vo.getStandChargeCode());
		chargeEntity.setAmount(vo.getStandCharge());
		chargeEntity.setPricingCriDetailId(vo.getStandChargeId());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		return chargeEntity;
	}
	
	/**
	 * 
	 * 获取打木箱费用
	 * @author 025000-FOSS-helong
	 * @date 2013-2-1 下午05:13:52
	 * @param vo
	 * @return
	 */
	public static WaybillChargeDtlEntity getBoxPackCharge(WaybillPanelVo vo) {
		WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
		chargeEntity.setPricingEntryCode(vo.getBoxChargeCode());
		chargeEntity.setAmount(vo.getBoxCharge());
		chargeEntity.setPricingCriDetailId(vo.getBoxChargeId());
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
