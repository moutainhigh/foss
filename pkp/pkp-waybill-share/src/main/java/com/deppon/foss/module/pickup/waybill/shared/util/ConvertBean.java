package com.deppon.foss.module.pickup.waybill.shared.util;

import java.math.BigDecimal;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 不同bean之间的转换
 * @author 272311-sangwenhao
 * @date 2016-1-30
 */
public class ConvertBean {
	
	/**
	 * 100
	 */
	private static final int NUM100 = 100;
	
	public static PtpWaybillDto getPtpWaybillDto(WaybillDto waybillDto,CurrentInfo currentInfo){
		if(waybillDto !=null){
			PtpWaybillDto ptpWaybillDto = waybillDto.getPtpWaybillDto() ;
			if(ptpWaybillDto == null){
				ptpWaybillDto = new PtpWaybillDto();
			}
			ptpWaybillDto.setCouponCode(waybillDto.getWaybillEntity().getPromotionsCode());//优惠券code
			ptpWaybillDto.setWaybillNo(waybillDto.getWaybillEntity().getWaybillNo());
			ptpWaybillDto.setWaybillNOId(waybillDto.getWaybillEntity().getId()) ;
			ptpWaybillDto.setReceiveCustomerCode(waybillDto.getWaybillEntity().getReceiveCustomerCode());
			ptpWaybillDto.setReceiveCustomerName(waybillDto.getWaybillEntity().getReceiveCustomerName());
			ptpWaybillDto.setDeliveryCustomerName(waybillDto.getWaybillEntity().getDeliveryCustomerName());
			ptpWaybillDto.setDeliveryCustomerCode(waybillDto.getWaybillEntity().getDeliveryCustomerCode());
			ptpWaybillDto.setGoodsWeightTotal(waybillDto.getWaybillEntity().getGoodsWeightTotal());
			ptpWaybillDto.setGoodsVolumeTotal(waybillDto.getWaybillEntity().getGoodsVolumeTotal());
			ptpWaybillDto.setGoodsQtyTotal(waybillDto.getWaybillEntity().getGoodsQtyTotal());
			ptpWaybillDto.setCountBills(waybillDto.getWaybillEntity().getGoodsQtyTotal());
			
			ptpWaybillDto.setTargetOrgCode(waybillDto.getWaybillEntity().getTargetOrgCode());
			
			ptpWaybillDto.setCreateUserCode(waybillDto.getWaybillEntity().getCreateUserCode());
			ptpWaybillDto.setCreateOrgCode(waybillDto.getWaybillEntity().getCreateOrgCode());
			ptpWaybillDto.setDeliveryCustomerContact(waybillDto.getWaybillEntity().getDeliveryCustomerContact());
			ptpWaybillDto.setReceiveCustomerContact(waybillDto.getWaybillEntity().getReceiveCustomerContact());
			ptpWaybillDto.setInvoice(waybillDto.getActualFreightEntity().getInvoice());
			ptpWaybillDto.setArriveCentralizedSettlement(waybillDto.getActualFreightEntity().getArriveCentralizedSettlement());
			ptpWaybillDto.setReceiveOrgCode(waybillDto.getWaybillEntity().getReceiveOrgCode());
			ptpWaybillDto.setProductId(waybillDto.getWaybillEntity().getProductId());
			ptpWaybillDto.setProductCode(waybillDto.getWaybillEntity().getProductCode());
			ptpWaybillDto.setReceiveMethod(waybillDto.getWaybillEntity().getReceiveMethod());
			ptpWaybillDto.setCreateTime(waybillDto.getWaybillEntity().getCreateTime());
			ptpWaybillDto.setGoodsName(waybillDto.getWaybillEntity().getGoodsName());
			
			ptpWaybillDto.setCustomerPickupOrgCode(waybillDto.getWaybillEntity().getCustomerPickupOrgCode());
			//到达部门 
			ptpWaybillDto.setArriveOrgCode(waybillDto.getWaybillEntity().getLastLoadOrgCode());
					
			ptpWaybillDto.setInsuranceRate(waybillDto.getWaybillEntity().getInsuranceRate());
			ptpWaybillDto.setInsuranceFee(waybillDto.getWaybillEntity().getInsuranceFee());
			ptpWaybillDto.setCodAmount(waybillDto.getWaybillEntity().getCodAmount());
			ptpWaybillDto.setCodRate(waybillDto.getWaybillEntity().getCodRate());
			ptpWaybillDto.setCodFee(waybillDto.getWaybillEntity().getCodFee());
			ptpWaybillDto.setRefundType(waybillDto.getWaybillEntity().getRefundType());
			ptpWaybillDto.setReturnBillType(waybillDto.getWaybillEntity().getReturnBillType());
			ptpWaybillDto.setSecretPrepaid(waybillDto.getWaybillEntity().getSecretPrepaid());
			ptpWaybillDto.setToPayAmount(waybillDto.getWaybillEntity().getToPayAmount());
			ptpWaybillDto.setPrePayAmount(waybillDto.getWaybillEntity().getPrePayAmount());
			ptpWaybillDto.setDeliveryGoodsFee(waybillDto.getWaybillEntity().getDeliveryGoodsFee());
			ptpWaybillDto.setOtherFee(waybillDto.getWaybillEntity().getOtherFee());
			ptpWaybillDto.setPackageFee(waybillDto.getWaybillEntity().getPackageFee());
			ptpWaybillDto.setPromotionsFee(waybillDto.getWaybillEntity().getPromotionsFee());
			ptpWaybillDto.setBillingType(waybillDto.getWaybillEntity().getBillingType());
			ptpWaybillDto.setUnitPrice(waybillDto.getWaybillEntity().getUnitPrice());
			ptpWaybillDto.setTransportFee(waybillDto.getWaybillEntity().getTransportFee());
			ptpWaybillDto.setValueAddFee(waybillDto.getWaybillEntity().getValueAddFee());
			ptpWaybillDto.setPaidMethod(waybillDto.getWaybillEntity().getPaidMethod());
			ptpWaybillDto.setTotalFee(waybillDto.getWaybillEntity().getTotalFee());
			ptpWaybillDto.setBillTime(waybillDto.getWaybillEntity().getBillTime());
			ptpWaybillDto.setRefCode(waybillDto.getWaybillEntity().getRefCode());
			ptpWaybillDto.setRefId(waybillDto.getWaybillEntity().getRefId());
			ptpWaybillDto.setBillWeight(waybillDto.getWaybillEntity().getBillWeight());
			ptpWaybillDto.setPickupFee(waybillDto.getWaybillEntity().getPickupFee());
			ptpWaybillDto.setServiceFee(waybillDto.getWaybillEntity().getServiceFee());
			ptpWaybillDto.setTransportType(waybillDto.getWaybillEntity().getTransportType());
			ptpWaybillDto.setActive(waybillDto.getWaybillEntity().getActive());
			ptpWaybillDto.setOtherPackage(waybillDto.getWaybillEntity().getOtherPackage());
			ptpWaybillDto.setPaperNum(waybillDto.getWaybillEntity().getPaperNum());
			ptpWaybillDto.setWoodNum(waybillDto.getWaybillEntity().getWoodNum());
			ptpWaybillDto.setFibreNum(waybillDto.getWaybillEntity().getFibreNum());
			ptpWaybillDto.setSalverNum(waybillDto.getWaybillEntity().getSalverNum());
			ptpWaybillDto.setMembraneNum(waybillDto.getWaybillEntity().getMembraneNum());
			ptpWaybillDto.setReceiveOrgName(waybillDto.getWaybillEntity().getReceiveOrgName());
			ptpWaybillDto.setCustomerPickupOrgName(waybillDto.getWaybillEntity().getCustomerPickupOrgName());
			ptpWaybillDto.setKilometer(waybillDto.getWaybillEntity().getKilometer());
			ptpWaybillDto.setIsEconomyGoods(waybillDto.getWaybillEntity().getIsEconomyGoods());
			ptpWaybillDto.setIsExpress(waybillDto.getWaybillEntity().getIsExpress());
			ptpWaybillDto.setCurrencyCode(waybillDto.getWaybillEntity().getCurrencyCode());
			if(FossConstants.YES.equals(waybillDto.getWaybillEntity().getPickupToDoor())){
				ptpWaybillDto.setPickupToDoor(true);
			}else{
				ptpWaybillDto.setPickupToDoor(false);
			}
			if(currentInfo != null){
				ptpWaybillDto.setCurrentDeptCode(currentInfo.getCurrentDeptCode());
				ptpWaybillDto.setCurrentDeptName(currentInfo.getCurrentDeptName());
				ptpWaybillDto.setUserName(currentInfo.getEmpName());
				ptpWaybillDto.setUserCode(currentInfo.getEmpCode());
			}else{
				ptpWaybillDto.setCurrentDeptCode(waybillDto.getWaybillEntity().getCreateOrgCode());
				ptpWaybillDto.setCurrentDeptName(waybillDto.getWaybillEntity().getCreateUserDeptName());
				ptpWaybillDto.setUserName(waybillDto.getWaybillEntity().getCreateUserName());
				ptpWaybillDto.setUserCode(waybillDto.getWaybillEntity().getCreateUserCode());
			}
			//2016年3月8日 10:03:15 葛亮亮
			if(null != waybillDto.getOpenBank()){
				ptpWaybillDto.setBankHQCode(waybillDto.getOpenBank().getBankCode()); //开户行编码
				ptpWaybillDto.setBankHQName(waybillDto.getOpenBank().getOpeningBankName()); //开户行名称
				ptpWaybillDto.setBankBranchCode(waybillDto.getOpenBank().getBranchBankCode());//支行编码（行号）
				ptpWaybillDto.setBankBranchName(waybillDto.getOpenBank().getBranchBankName());//支行名称
				ptpWaybillDto.setProvinceCode(waybillDto.getOpenBank().getProvCode());//省份编码
				ptpWaybillDto.setProvinceName(waybillDto.getOpenBank().getProvinceName());//省份名称
				ptpWaybillDto.setCityCode(waybillDto.getOpenBank().getCityCode());//城市编码
				ptpWaybillDto.setCityName(waybillDto.getOpenBank().getCityName());//城市名称
				ptpWaybillDto.setPublicPrivateFlag(waybillDto.getOpenBank().getAccountNature());//对公对私标志
				ptpWaybillDto.setPayeeRelationship(waybillDto.getOpenBank().getCustomer());//收款人与发货人关系
				ptpWaybillDto.setPayeePhone(waybillDto.getOpenBank().getMobilePhone());//收款人手机号码
				ptpWaybillDto.setPayeeName(waybillDto.getOpenBank().getAccountName());//收款人姓名
				ptpWaybillDto.setAccountNo(waybillDto.getOpenBank().getAccountNo());//收款人账号
			}
			//合伙人运单信息补充合伙人
			if(null != waybillDto.getPartnersWaybillEntity()){
				setPartnersWabill(waybillDto, ptpWaybillDto);
				ptpWaybillDto.setOverTransportFeeOrg(waybillDto.getPartnersWaybillEntity().getOverTransportFee() != null ? waybillDto.getPartnersWaybillEntity().getOverTransportFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //到达加收金额
				ptpWaybillDto.setOverTransportRateOrg(waybillDto.getPartnersWaybillEntity().getOverTransportRate() != null ? waybillDto.getPartnersWaybillEntity().getOverTransportRate().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //到达加收费率
				ptpWaybillDto.setCouponFeeOrg(waybillDto.getPartnersWaybillEntity().getCouponFee() != null ? waybillDto.getPartnersWaybillEntity().getCouponFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO );//优惠券金额
				ptpWaybillDto.setPartialTransportFeeOrg(waybillDto.getPartnersWaybillEntity().getPartialTransportFee() != null ? waybillDto.getPartnersWaybillEntity().getPartialTransportFee().divide(new BigDecimal(100)) : BigDecimal.ZERO); //合伙人偏线费
			}

			return ptpWaybillDto ;
		
		}else{
			return null ;
		}
	}

	private static void setPartnersWabill(WaybillDto waybillDto,
			PtpWaybillDto ptpWaybillDto) {
		setPartnersWabyill1(waybillDto, ptpWaybillDto);
		setPartnersWabyill2(waybillDto, ptpWaybillDto);
	}

	private static void setPartnersWabyill2(WaybillDto waybillDto,
			PtpWaybillDto ptpWaybillDto) {
		ptpWaybillDto.setBoxChargeOrg(waybillDto.getPartnersWaybillEntity().getBoxCharge() != null ? waybillDto.getPartnersWaybillEntity().getBoxCharge().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //打木箱货物费用
		ptpWaybillDto.setSalverGoodsChargeOrg(waybillDto.getPartnersWaybillEntity().getSalverGoodsCharge() != null ? waybillDto.getPartnersWaybillEntity().getSalverGoodsCharge().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //打木托货物费用
		ptpWaybillDto.setOtherFeeOrg(waybillDto.getPartnersWaybillEntity().getOtherFee() != null ? waybillDto.getPartnersWaybillEntity().getOtherFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); // 其他费
		ptpWaybillDto.setOverDistanceFeeOrg(waybillDto.getPartnersWaybillEntity().getOverDistanceFeeOrg() != null ? waybillDto.getPartnersWaybillEntity().getOverDistanceFeeOrg().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //超远派送费
		ptpWaybillDto.setSignBillFeeOrg(waybillDto.getPartnersWaybillEntity().getSignBillFeeOrg()!= null ? waybillDto.getPartnersWaybillEntity().getSignBillFeeOrg().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO);	//签单费
		ptpWaybillDto.setBaseDeliveryGoodsFeeOrg(waybillDto.getPartnersWaybillEntity().getBaseDeliveryGoodsFeeOrg() != null ? waybillDto.getPartnersWaybillEntity().getBaseDeliveryGoodsFeeOrg().divide(new BigDecimal(NUM100)).toString() : "0"); //基础送货费
		ptpWaybillDto.setExceptionOpreationFeeOrg(waybillDto.getPartnersWaybillEntity().getExceptionOpreationFee() != null ? waybillDto.getPartnersWaybillEntity().getExceptionOpreationFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //异常操作费
		ptpWaybillDto.setBigGoodsUpFloorFeeOrg(waybillDto.getPartnersWaybillEntity().getBigGoodsUpFloorFee() != null ? waybillDto.getPartnersWaybillEntity().getBigGoodsUpFloorFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //大件上楼费
		ptpWaybillDto.setDeliveryWareHouseFeeOrg(waybillDto.getPartnersWaybillEntity().getDeliveryWareHouseFee() != null ? waybillDto.getPartnersWaybillEntity().getDeliveryWareHouseFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //送货进仓费
		ptpWaybillDto.setPickupToDoorJZFeeOrg(waybillDto.getPartnersWaybillEntity().getPickupToDoorJZFee() != null ? waybillDto.getPartnersWaybillEntity().getPickupToDoorJZFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //送货安装费
		ptpWaybillDto.setIsCalTraFee(waybillDto.getPartnersWaybillEntity().getIsCalTraFee()); //转运返货是否重新计算公布价运费
		ptpWaybillDto.setStartOrgCodeCal(waybillDto.getPartnersWaybillEntity().getStartOrgCodeCal()); //重新计算公布价运费出发部门
		ptpWaybillDto.setDestinationOrgCodeCal(waybillDto.getPartnersWaybillEntity().getDestinationOrgCodeCal()); //重新计算公布价运费到达部门
	}

	private static void setPartnersWabyill1(WaybillDto waybillDto,
			PtpWaybillDto ptpWaybillDto) {
		ptpWaybillDto.setCodFeeOrg(waybillDto.getPartnersWaybillEntity().getCodFee() != null ? waybillDto.getPartnersWaybillEntity().getCodFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //代收货款手续费
		ptpWaybillDto.setDeliveryGoodsFeeOrg(waybillDto.getPartnersWaybillEntity().getDeliveryGoodsFee() != null ? waybillDto.getPartnersWaybillEntity().getDeliveryGoodsFee().divide(new BigDecimal(NUM100)).toString() : "0"); //送货费
		ptpWaybillDto.setPackageFeeOrg(waybillDto.getPartnersWaybillEntity().getPackageFee() != null ? waybillDto.getPartnersWaybillEntity().getPackageFee().divide(new BigDecimal(NUM100)).toString() : "0"); //包装手续费
		ptpWaybillDto.setPromotionsFeeOrg(waybillDto.getPartnersWaybillEntity().getPromotionsFee() != null ? waybillDto.getPartnersWaybillEntity().getPromotionsFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //优惠费用
		ptpWaybillDto.setTransportFeeOrg(waybillDto.getPartnersWaybillEntity().getTransportFee() != null ? waybillDto.getPartnersWaybillEntity().getTransportFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //公布价运费
		ptpWaybillDto.setValueAddFeeOrg(waybillDto.getPartnersWaybillEntity().getValueAddFee() != null ? waybillDto.getPartnersWaybillEntity().getValueAddFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //增值费用
		ptpWaybillDto.setPickUpFeeOrg(waybillDto.getPartnersWaybillEntity().getPickupFee() != null ? waybillDto.getPartnersWaybillEntity().getPickupFee().divide(new BigDecimal(NUM100)).toString() : "0"); //接货费
		ptpWaybillDto.setServiceFeeOrg(waybillDto.getPartnersWaybillEntity().getServiceFee() != null ? waybillDto.getPartnersWaybillEntity().getServiceFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //劳务费
		ptpWaybillDto.setInsuranceFeeOrg(waybillDto.getPartnersWaybillEntity().getInsuranceFee() != null ? waybillDto.getPartnersWaybillEntity().getInsuranceFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //保价费 
		ptpWaybillDto.setTotalFeeOrg(waybillDto.getPartnersWaybillEntity().getTotalFee() != null ? waybillDto.getPartnersWaybillEntity().getTotalFee().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //总费用
		ptpWaybillDto.setUpFloorFeeOrg(waybillDto.getPartnersWaybillEntity().getUpFloorFee() != null ? new BigDecimal(waybillDto.getPartnersWaybillEntity().getUpFloorFee()).divide(new BigDecimal(NUM100)).toString() : "0"); //上楼费
		ptpWaybillDto.setStandChargeOrg(waybillDto.getPartnersWaybillEntity().getStandCharge() != null ? waybillDto.getPartnersWaybillEntity().getStandCharge().divide(new BigDecimal(NUM100)) : BigDecimal.ZERO); //打木架货物费用
	}

}
