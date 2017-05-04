/**
 * 
 */
package com.deppon.foss.module.pickup.creatingexp.client.common;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.client.commons.i18n.I18nManager;
import com.deppon.foss.framework.client.commons.i18n.II18n;
import com.deppon.foss.framework.client.core.context.SessionContext;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.waybill.shared.dto.AddressFieldDto;
import com.deppon.foss.module.pickup.common.client.utils.BooleanConvertYesOrNo;
import com.deppon.foss.module.pickup.common.client.utils.CommonUtils;
import com.deppon.foss.module.pickup.common.client.vo.DeliverChargeEntity;
import com.deppon.foss.module.pickup.common.client.vo.OtherChargeVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.common.client.vo.WaybillPanelVo;
import com.deppon.foss.module.pickup.common.client.vo.exp.ExpWaybillPanelVo;
import com.deppon.foss.module.pickup.creatingexp.client.ui.ExpWaybillEditUI;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpConcessionsPanel.WaybillDiscountCanvas;
import com.deppon.foss.module.pickup.creatingexp.client.ui.editui.ExpIncrementPanel.WaybillOtherCharge;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillCHDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillDisDtlPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPaymentPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequirePendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class ExpWaybillTempSaveData {
	//运单UI
	private ExpWaybillEditUI waybillEditUI;
	
	/**
	 * 国际化对象
	 */
	private static final II18n i18n = I18nManager.getI18n(ExpWaybillEditUI.class); 
	
	public ExpWaybillTempSaveData(ExpWaybillEditUI waybillEditUI)
	{
		this.waybillEditUI = waybillEditUI;
	}
	
	
	/**
	 * 
	 * 组装后台DTO
	 * 
	 * @author 025000-FOSS-helong
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-3 下午04:06:31
	 */
	public WaybillPendingDto getWaybillDto(WaybillPanelVo bean){
		WaybillPendingDto dto = new WaybillPendingDto();
		
		if(bean.isPCPending()!=null&&bean.isPCPending()){
			dto.setIsPendingImport(true);
		}else{
			dto.setIsPendingImport(false);
		}
		dto.setWaybillPending(getWaybillPendingEntity(bean));
		
		// 非内部带货，内部带货不产生任何与费用相关的数据
		if (!WaybillConstants.INNER_PICKUP.equals(bean.getReceiveMethod().getValueCode())) {
    		dto.setWaybillChargeDtlPending(getWaybillCHDtlPendingEntity(bean));
    		dto.setWaybillDisDtlPending(getWaybillDisDtlEntity(bean));	
    		dto.setWaybillPaymentPending(getWaybillPaymentEntity(bean));
    		// 设置开户银行信息
    		dto.setOpenBank(bean.getOpenBank());
		}
		
		if (!StringUtils.isEmpty(bean.getPromotionsCode())) {
			CouponInfoDto couponInfoDto = bean.getCouponInfoDto();
			// 判断优惠券实体是否为空
			if (couponInfoDto != null) {
				dto.setCouponInfoDto(couponInfoDto);
			} else {
				throw new WaybillValidateException(i18n.get("foss.gui.creating.waybillSubmitConfirmAction.exception.nullCouponInfo"));
			}
			// 查看是否有优惠券编号
			if (StringUtils.isNotEmpty(bean.getPromotionsCode())) {
				couponInfoDto.setUsed(true);
				dto.setCouponInfoDto(couponInfoDto);
			}
		}

		dto.setWoodenRequirePending(getWoodenRequirementsEntity(bean));
		return dto;
	}

	/**
	 * 
	 * 获得运单数据
	 * 
	 * @author 025000-FOSS-helong
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @date 2012-11-3 下午04:15:27
	 */
	private WaybillPendingEntity getWaybillPendingEntity(WaybillPanelVo vo2) {
		ExpWaybillPanelVo vo = (ExpWaybillPanelVo)vo2;
		WaybillPendingEntity entity = new WaybillPendingEntity();

		entity.setWaybillNo(vo.getWaybillNo());// 运单号
		entity.setOrderNo(vo.getOrderNo());// 订单号
		entity.setOrderChannel(vo.getOrderChannel());// 订单来源
		entity.setOrderPaidMethod(vo.getOrderPayment());// 订单付款方式
		entity.setDeliveryCustomerId(vo.getDeliveryCustomerId());// 发货客户ID
		entity.setDeliveryCustomerContactId(vo.getDeliveryCustomerContactId());// 发货客户联系人ID
		entity.setDeliveryCustomerCode(vo.getDeliveryCustomerCode());// 发货客户编码
		entity.setDeliveryBigCustomer(vo.getDeliveryBigCustomer());// 发货客户编码
		entity.setDeliveryCustomerName(vo.getDeliveryCustomerName());// 发货客户名称
		entity.setDeliveryCustomerMobilephone(vo.getDeliveryCustomerMobilephone());// 发货客户手机
		entity.setDeliveryCustomerPhone(vo.getDeliveryCustomerPhone());// 发货客户电话
		entity.setDeliveryCustomerContact(vo.getDeliveryCustomerContact());// 发货客户联系人

		AddressFieldDto consignerAddress = waybillEditUI.consignerPanel.getTxtConsignerArea().getAddressFieldDto();
		if (null != consignerAddress) {
			entity.setDeliveryCustomerProvCode(consignerAddress.getProvinceId());// 发货省份
			entity.setDeliveryCustomerCityCode(consignerAddress.getCityId());// 发货市
			entity.setDeliveryCustomerDistCode(consignerAddress.getCountyId());// 发货区
		}
		entity.setDeliveryCustomerNationCode(vo.getDeliveryCustomerNationCode());// 发货国家
		entity.setDeliveryCustomerAddress(vo.getDeliveryCustomerAddress());// 发货具体地址

		entity.setReceiveCustomerId(vo.getReceiveCustomerId());// 收货客户ID
		entity.setReceiveCustomerContactId(vo.getReceiveCustomerContactId());// 收货联系人ID
		entity.setReceiveCustomerCode(vo.getReceiveCustomerCode());// 收货客户编码
		entity.setReceiveBigCustomer(vo.getReceiveBigCustomer());// 大客户标记
		entity.setReceiveCustomerName(vo.getReceiveCustomerName());// 收货客户名称
		entity.setReceiveCustomerMobilephone(vo.getReceiveCustomerMobilephone());// 收货客户手机
		entity.setReceiveCustomerPhone(vo.getReceiveCustomerPhone());// 收货客户电话
		entity.setReceiveCustomerContact(vo.getReceiveCustomerContact());// 收货客户联系人

		AddressFieldDto consigneeAddress = waybillEditUI.consigneePanel.getTxtConsigneeArea().getAddressFieldDto();
		if (null != consigneeAddress) {
			entity.setReceiveCustomerProvCode(consigneeAddress.getProvinceId());// 收货省份
			entity.setReceiveCustomerCityCode(consigneeAddress.getCityId());// 收货市
			entity.setReceiveCustomerDistCode(consigneeAddress.getCountyId());// 收货区
		}
		entity.setReceiveCustomerNationCode(vo.getReceiveCustomerNationCode());// 收货国家
		entity.setReceiveCustomerAddress(vo.getReceiveCustomerAddress());// 收货具体地址

		entity.setReceiveOrgCode(vo.getReceiveOrgCode());// 收货部门
		entity.setOrderVehicleNum(vo.getVehicleNumber());// 约车编号
		entity.setProductId(vo.getProductCode().getId());// 产品ID
		entity.setProductCode(vo.getProductCode().getCode());// 运输性质
		entity.setReceiveMethod(vo.getReceiveMethod().getValueCode());// 提货方式
		entity.setCustomerPickupOrgCode(vo.getCustomerPickupOrgCode().getCode());// 提货网点
		entity.setLoadMethod(vo.getLoadMethod());// 配载类型
		entity.setTargetOrgCode(vo.getTargetOrgCode());// 目的站
		entity.setPickupToDoor(BooleanConvertYesOrNo.booleanToString(vo.getPickupToDoor()));// 是否上门接货
		entity.setDriverCode(vo.getDriverCode());// 司机
		entity.setPickupCentralized(BooleanConvertYesOrNo.booleanToString(vo.getPickupCentralized()));// 是否集中接货
		entity.setLoadLineCode(vo.getLoadLineCode());// 配载线路
		entity.setLoadOrgCode(vo.getLoadOrgCode());// 配载部门
		entity.setLastLoadOrgCode(vo.getLastLoadOrgCode());// 最终配载部门
		entity.setPreDepartureTime(vo.getPreDepartureTime());// 预计出发时间
		entity.setPreCustomerPickupTime(vo.getPreCustomerPickupTime());// 预计派送/提货时间
		entity.setCarDirectDelivery(BooleanConvertYesOrNo.booleanToString(vo.getCarDirectDelivery()));// 是否大车直送
		entity.setGoodsName(vo.getGoodsName());// 货物名称

		entity.setGoodsQtyTotal(vo.getGoodsQtyTotal());// 货物总件数
		entity.setGoodsWeightTotal(vo.getGoodsWeightTotal());// 货物总重量
		entity.setGoodsVolumeTotal(vo.getGoodsVolumeTotal());// 货物总体积
		entity.setGoodsSize(vo.getGoodsSize());// 货物尺寸
		if (ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(vo.getProductCode().getCode())) {
			entity.setGoodsTypeCode(vo.getAirGoodsType().getValueCode());// 货物类型
		} else {
			entity.setGoodsTypeCode(vo.getGoodsType());// 货物类型
		}
		entity.setPreciousGoods(BooleanConvertYesOrNo.booleanToString(vo.getPreciousGoods()));// 是否贵重物品
		entity.setSpecialShapedGoods(BooleanConvertYesOrNo.booleanToString(vo.getSpecialShapedGoods()));// 是否异形物品
		// 对外备注
		if (vo.getOuterNotes() != null) {
		    entity.setOuterNotes(vo.getOuterNotes().getValueCode());// 对外备注
		}
		entity.setInnerNotes(vo.getInnerNotes());// 对内备注
		entity.setTransportationRemark(vo.getTransportationRemark());// 储运事项
		entity.setGoodsPackage(vo.getGoodsPackage());// 货物包装

		// 纸
		entity.setPaperNum(Integer.valueOf(vo.getPaper()));
		// 木
		entity.setWoodNum(Integer.valueOf(vo.getWood()));
		// 纤
		entity.setFibreNum(Integer.valueOf(vo.getFibre()));
		// 托
		entity.setSalverNum(Integer.valueOf(vo.getSalver()));
		// 膜
		entity.setMembraneNum(Integer.valueOf(vo.getMembrane()));
		// 其他
		entity.setOtherPackage(vo.getOtherPackage());

		entity.setInsuranceAmount(vo.getInsuranceAmount());// 保价声明价值
		// 保价费率
		BigDecimal insuranceRate = vo.getInsuranceRate();
		// 将费率转换成千分率的数据
		insuranceRate = insuranceRate.divide(new BigDecimal(WaybillConstants.PERMILLAGE));
		entity.setInsuranceRate(insuranceRate);
		entity.setInsuranceFee(vo.getInsuranceFee());// 保价费

		entity.setCodAmount(vo.getCodAmount());// 代收货款
		// 代收费率
		BigDecimal codReate = vo.getCodRate();
		// 将费率转换成千分率的数据
		codReate = codReate.divide(new BigDecimal(WaybillConstants.PERMILLAGE));
		entity.setCodRate(codReate);
		entity.setCodFee(vo.getCodFee());// 代收货款手续费
		if (vo.getRefundType() != null) {
			entity.setRefundType(vo.getRefundType().getValueCode());// 退款类型
		}

		entity.setReturnBillType(vo.getReturnBillType().getValueCode());// 返单类别
		entity.setSecretPrepaid(BooleanConvertYesOrNo.booleanToString(vo.getSecretPrepaid()));// 预付费保密
		entity.setToPayAmount(vo.getToPayAmount());// 到付金额
		entity.setPrePayAmount(vo.getPrePayAmount());// 预付金额
		entity.setDeliveryGoodsFee(vo.getDeliveryGoodsFee());// 送货费
		entity.setOtherFee(vo.getOtherFee());// 其他费用
		entity.setPackageFee(vo.getPackageFee());// 包装手续费
		entity.setPromotionsFee(vo.getPromotionsFee());// 优惠费用
		// 运费计费类型
		String billTypeCode = vo.getBillingType().getValueCode();
		// 判断计费类型是否为空
		if (StringUtil.isEmpty(billTypeCode)) {
			// 为空则默认为重量计费
			entity.setBillingType(WaybillConstants.BILLINGWAY_WEIGHT);
		} else {
			// 不为空则从VO中取值
			entity.setBillingType(vo.getBillingType().getValueCode());
		}
		entity.setBillingType(vo.getBillingType().getValueCode());// 运费计费类型
		entity.setUnitPrice(vo.getUnitPrice());// 运费计费费率
		entity.setTransportFee(vo.getTransportFee());// 公布价运费
		entity.setValueAddFee(vo.getValueAddFee());// 增值费用
		if (vo.getPaidMethod() != null) {
			entity.setPaidMethod(vo.getPaidMethod().getValueCode());// 开单付款方式
		}
		entity.setArriveType(vo.getArriveType());// 到达类型
		entity.setActive(FossConstants.YES);// 运单状态
		if (StringUtil.isEmpty(vo.getWaybillstatus())) {
			if (WaybillConstants.ONLINE_LOGIN.equals(SessionContext.get("user_loginType").toString())) {
				entity.setPendingType(WaybillConstants.WAYBILL_STATUS_PC_PENDING);// 运单暂存
			} else {
				entity.setPendingType(WaybillConstants.WAYBILL_STATUS_OFFLINE_PENDING);// 运单离线暂存
			}
		} else {
			entity.setPendingType(vo.getWaybillstatus());
		}
		entity.setForbiddenLine(FossConstants.YES);// 禁行
		if (vo.getFreightMethod() != null) {
			entity.setFreightMethod(vo.getFreightMethod().getValueCode());// 合票方式
		}
		entity.setFlightShift(vo.getFlightShift());// 航班时间
		if (vo.getFlightNumberType() != null) {
			entity.setFlightNumberType(vo.getFlightNumberType().getValueCode());// 航班类型
		}
		entity.setTotalFee(vo.getTotalFee());// 总费用
		entity.setPromotionsCode(vo.getPromotionsCode());// 优惠编码
		entity.setCreateTime(new Date());// 创建时间
		entity.setModifyTime(new Date());// 更新时间
		entity.setBillTime(new Date());// 开单时间

		entity.setCreateUserCode(vo.getCreateUserCode());// 开单人
		entity.setModifyUserCode(vo.getCreateUserCode());// 更新人
		entity.setCreateOrgCode(vo.getCreateOrgCode());// 开单组织
		entity.setModifyOrgCode(vo.getCreateOrgCode());// 更新组织
		entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// 币种
		entity.setIsWholeVehicle(BooleanConvertYesOrNo.booleanToString(vo.getIsWholeVehicle()));// 是否整车运单
		// 是否经过营业部
		entity.setIsPassOwnDepartment(BooleanConvertYesOrNo.booleanToString(vo.getIsPassDept()));
		// 整车约车编号
		entity.setOrderVehicleNum(vo.getVehicleNumber());
		entity.setWholeVehicleActualfee(CommonUtils.defaultIfNull(vo.getWholeVehicleActualfee()));// 整车开单报价
		entity.setWholeVehicleAppfee(CommonUtils.defaultIfNull(vo.getWholeVehicleAppfee()));// 整车约车报价
		entity.setAccountName(vo.getAccountName());// 返款帐户开户名称
		entity.setAccountCode(vo.getAccountCode());// 返款帐户开户账户
		entity.setAccountBank(vo.getAccountBank());// 返款帐户开户银行
		entity.setBillWeight(vo.getBillWeight());// 计费重量
		entity.setPickupFee(vo.getPickupFee());// 接货费

		entity.setServiceFee(vo.getServiceFee());// 装卸费
		entity.setPreArriveTime(vo.getPreCustomerPickupTime());// 预计到达时间
		entity.setTransportType(vo.getTransportType());// 运输类型
		entity.setAddTime(new Date());// 新增时间
		entity.setKilometer(vo.getKilometer());// 公里数
		
		//以下为小件代码
		entity.setIsAddCode(vo.getIsAddCode());//是否补码
		entity.setAddCodeTime(vo.getAddCodeTime());//补码时间
		if(vo.getReturnType()!=null 
				&& StringUtils.isNotEmpty(vo.getReturnType().getValueCode())){
			entity.setReturnType(vo.getReturnType().getValueCode());//返单/返货类型
			entity.setOriginalWaybillNo(vo.getOriginalWaybillNo());//返单/返货原运单号
		}
		entity.setExpressEmpCode(vo.getExpressEmpCode());//快递员code
		entity.setExpressEmpName(vo.getExpressEmpName());//快递员名称
		entity.setExpressOrgCode(vo.getExpressOrgCode());//快递点code
		entity.setExpressOrgName(vo.getExpressOrgName());//快递点名称
		entity.setPdaSerial(vo.getPdaSerial());// PDA串号
		entity.setBankTradeSerail(vo.getBankTradeSerail());//银行交易流水号
		//进行地址备注的数据的插入
		entity.setDeliveryCustomerAddressNote(vo.getDeliveryCustomerAddressNote());
		entity.setReceiveCustomerAddressNote(vo.getReceiveCustomerAddressNote());
		
		/**
		 * 将交易流水号导入entity中
		 * @author:218371-foss-zhaoyanjun
		 * @date:2015-01-23
		 */
		entity.setTransactionSerialNumber(vo.getTransactionSerialNumber());
		//将是否上门发货保存到entity中
		if(vo.isHomeDelivery()){			
			entity.setHomeDelivery(WaybillConstants.YES);
		}else{
			entity.setHomeDelivery(WaybillConstants.NO);
		}
		
		return entity;
	}

	/**
	 * 
	 * 获取费用明细
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:25:47
	 */
	private List<WaybillCHDtlPendingEntity> getWaybillCHDtlPendingEntity(
			WaybillPanelVo vo) {
		List<WaybillCHDtlPendingEntity> chargeDtlEntityList = new ArrayList<WaybillCHDtlPendingEntity>();
		if(vo.getRefundType() != null && vo.getRefundType().getValueCode() != null)
		{
			chargeDtlEntityList.add(getCod(vo));//添加代收货款费用明细
		}
		
		getDeliveryCharge(vo,chargeDtlEntityList);//添加送货费
		
		if (vo.getPickupFee() != null
				&& vo.getPickupFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPickUpCharge(vo));// 添加接货费
		}
		
		if(vo.getInsuranceCode() != null && !"".equals(vo.getInsuranceCode()))
		{
			chargeDtlEntityList.add(getInsurance(vo));//添加保险费
		}
		
		if (vo.getPackageFee() != null
				&& vo.getPackageFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getPackageCharge(vo));// 添加包装费
		}
		
		if (vo.getStandCharge() != null
				&& vo.getStandCharge().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getStandardPackCharge(vo));// 添加打木架费用
		}
		
		if (vo.getBoxCharge() != null
				&& vo.getBoxCharge().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getBoxPackCharge(vo));// 添加打木箱费用
		}
		
		if(vo.getTransportFee() !=null 
				&& vo.getTransportFee().compareTo(BigDecimal.ZERO) != 0) {
			chargeDtlEntityList.add(getTransportFee(vo));// 添加公布价运费
		}
		
		getOtherCharge(vo,chargeDtlEntityList);//添加其他费用	
		return chargeDtlEntityList;
	}
	
	/**
	 * 
	 * 获取接货费
	 * @author 025000-FOSS-helong
	 * @date 2013-2-1 下午05:02:37
	 * @param vo
	 * @return
	 */
	private WaybillCHDtlPendingEntity getPickUpCharge(WaybillPanelVo vo) {
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	private WaybillCHDtlPendingEntity getPackageCharge(WaybillPanelVo vo) {
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	private WaybillCHDtlPendingEntity getStandardPackCharge(WaybillPanelVo vo) {
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	private WaybillCHDtlPendingEntity getBoxPackCharge(WaybillPanelVo vo) {
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	 * 获取公布价运费
	 * @author foss-sunrui
	 * @date 2013-3-14 下午1:56:59
	 * @param vo
	 * @return
	 * @see
	 */
	private WaybillCHDtlPendingEntity getTransportFee(WaybillPanelVo vo) {
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	private WaybillCHDtlPendingEntity getCod(WaybillPanelVo vo)
	{
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
		String codCode = vo.getCodCode();
		if(StringUtil.isEmpty(codCode)){
			codCode = PriceEntityConstants.PRICING_CODE_HK;
		}
		chargeEntity.setPricingEntryCode(codCode);
		chargeEntity.setAmount(vo.getCodFee());
		chargeEntity.setPricingCriDetailId(vo.getCodId());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeEntity.setBillTime(new Date());
		return chargeEntity;
	}
	
	/**
	 * 
	 * 获取送货费
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	public static  void getDeliveryCharge(WaybillPanelVo vo,List<WaybillCHDtlPendingEntity> chargeDtlEntityList) {

		//送货费集合
		List<DeliverChargeEntity> list = vo.getDeliverList();
		if(list != null && !list.isEmpty())
		{
			for(int i=0;i<list.size();i++)
			{
				WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
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
	 * 获取保价费
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	private  WaybillCHDtlPendingEntity getInsurance(WaybillPanelVo vo){
		WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
		chargeEntity.setPricingEntryCode(vo.getInsuranceCode());
		chargeEntity.setAmount(vo.getInsuranceFee());
		chargeEntity.setWaybillNo(vo.getWaybillNo());
		chargeEntity.setPricingCriDetailId(vo.getInsuranceId());
		chargeEntity.setActive(FossConstants.ACTIVE);
		chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
		chargeEntity.setBillTime(new Date());
		return chargeEntity;
	}
	
	/**
	 * 
	 * 获取保价费
	 * @author 025000-FOSS-helong
	 * @date 2012-11-20 上午10:12:02
	 */
	private void getOtherCharge(WaybillPanelVo vo,List<WaybillCHDtlPendingEntity> list) {
		WaybillOtherCharge model = (WaybillOtherCharge) waybillEditUI.incrementPanel.getTblOther().getModel();
		List<OtherChargeVo> data = model.getData();
		if(CollectionUtils.isNotEmpty(data)){
			for(OtherChargeVo otherVo : data){
				WaybillCHDtlPendingEntity chargeEntity = new WaybillCHDtlPendingEntity();
				chargeEntity.setPricingEntryCode(otherVo.getCode());
				chargeEntity.setAmount(new BigDecimal(otherVo.getMoney()));
				chargeEntity.setWaybillNo(vo.getWaybillNo());
				chargeEntity.setPricingCriDetailId(otherVo.getId());
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeEntity.setBillTime(new Date());
				list.add(chargeEntity);
			}
		}
	}

	/**
	 * 
	 * 获取折扣明细
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:28:33
	 */
	private List<WaybillDisDtlPendingEntity> getWaybillDisDtlEntity(WaybillPanelVo vo) {
		WaybillDiscountCanvas discountTableModel = (WaybillDiscountCanvas) waybillEditUI.canvasContentPanel.getConcessionsPanel().getTblConcessions().getModel();
		List<WaybillDisDtlPendingEntity> disDtlEntityList = new ArrayList<WaybillDisDtlPendingEntity>();
		List<WaybillDiscountVo> data = discountTableModel.getData();
		if(data!=null&&!data.isEmpty()){
			for(WaybillDiscountVo entity:data){
				WaybillDisDtlPendingEntity disDtlEntity = new WaybillDisDtlPendingEntity();
        		//优惠项目名称
        		disDtlEntity.setPricingEntryName(entity.getFavorableItemName());
        		//优惠项目CODE
        		disDtlEntity.setPricingEntryCode(entity.getFavorableItemCode());
        		//优惠类型名称
        		disDtlEntity.setTypeName(entity.getFavorableTypeName());
        		//优惠类型CODE
    			disDtlEntity.setType(entity.getFavorableTypeCode());
        		//优惠子类型名称
        		disDtlEntity.setSubTypeName(entity.getFavorableSubTypeName());
        		//优惠子类型CODE
    			disDtlEntity.setSubType(entity.getFavorableSubTypeCode());
    			//折扣费率
        		disDtlEntity.setRate(new BigDecimal(entity.getFavorableDiscount()));
        		//设置快递续重折扣费率
        		if(entity.getContinueFavorableDiscount()!=null){
        			//设置快递续重折扣费率
            		disDtlEntity.setExpressContinueRate(new BigDecimal(entity.getFavorableDiscount()));
        		}
        		//折扣金额
        		disDtlEntity.setAmount(new BigDecimal(entity.getFavorableAmount()));
        		//运单号
        		disDtlEntity.setWaybillNo(vo.getWaybillNo());
        		if(entity.getDiscountId()!=null){
        			disDtlEntity.setDicountId(entity.getDiscountId());
        		}else{
        			disDtlEntity.setDicountId(UUIDUtils.getUUID());
        		}
        		if(entity.getChargeDetailId()!=null){
        			disDtlEntity.setWaybillChDePgId(entity.getChargeDetailId());
        		}else{
        			disDtlEntity.setWaybillChDePgId(UUIDUtils.getUUID());
        		}
        		disDtlEntity.setCreateTime(new Date());
        		disDtlEntity.setModifyTime(new Date());
        		disDtlEntity.setActive(FossConstants.ACTIVE);
        		disDtlEntity.setBillTime(new Date());
        		disDtlEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
        		disDtlEntityList.add(disDtlEntity);
			}
		}
		return disDtlEntityList;
	}

	/**
	 * 
	 * 获取打木架信息
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:31:07
	 */
	private WoodenRequirePendingEntity getWoodenRequirementsEntity(
			WaybillPanelVo vo) {
		
		if((vo.getStandGoodsNum() == null || vo.getStandGoodsNum().intValue() == 0)
				&& (vo.getBoxGoodsNum() == null || vo.getBoxGoodsNum() == 0))
		{
			return null;
		}else
		{
			WoodenRequirePendingEntity woodenRequirementsEntity = new WoodenRequirePendingEntity();
			woodenRequirementsEntity.setWaybillNo(vo.getWaybillNo());
			woodenRequirementsEntity.setPackageOrgCode(vo.getPackageOrgCode());
			woodenRequirementsEntity.setStandGoodsNum(vo.getStandGoodsNum());
			woodenRequirementsEntity.setStandRequirement(vo.getStandRequirement());
			woodenRequirementsEntity.setStandGoodsSize(vo.getStandGoodsSize());
			woodenRequirementsEntity.setStandGoodsVolume(vo.getStandGoodsVolume());
			woodenRequirementsEntity.setBoxGoodsNum(vo.getBoxGoodsNum());
			woodenRequirementsEntity.setBoxRequirement(vo.getBoxRequirement());
			woodenRequirementsEntity.setBoxGoodsSize(vo.getBoxGoodsSize());
			woodenRequirementsEntity.setBoxGoodsVolume(vo.getBoxGoodsVolume());
			woodenRequirementsEntity.setActive(FossConstants.ACTIVE);
			woodenRequirementsEntity.setCreateTime(new Date());
			woodenRequirementsEntity.setModifyTime(new Date());
			return woodenRequirementsEntity;
		}
	}

	/**
	 * 
	 * 获取支付
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-11-15 下午07:32:36
	 */
	private List<WaybillPaymentPendingEntity> getWaybillPaymentEntity(WaybillPanelVo vo) {
		List<WaybillPaymentPendingEntity> waybillPaymentEntityList = new ArrayList<WaybillPaymentPendingEntity>();
		
		//判断预付金额是否为空
		if(vo.getPrePayAmount()!= null&&vo.getPrePayAmount().compareTo(BigDecimal.ZERO)!= 0)
		{
			WaybillPaymentPendingEntity prePayAmount =getPrePayAmount(vo);
			waybillPaymentEntityList.add(prePayAmount);//预付金额
		}
		//判断到付金额是否为空
		if(vo.getToPayAmount()!=null&&vo.getToPayAmount().compareTo(BigDecimal.ZERO)!=0)
		{
			WaybillPaymentPendingEntity toPayAmount =getToPayAmount(vo);
			waybillPaymentEntityList.add(toPayAmount);//到付金额
		}
		//判断手写现付金额是否为空
		if (vo.getHandWriteMoney() != null
				&& vo.getHandWriteMoney().compareTo(BigDecimal.ZERO) != 0) {
			WaybillPaymentPendingEntity handWriteMoney = getHandWriteMoney(vo);
			waybillPaymentEntityList.add(handWriteMoney);//手写现付金额
		}
		return waybillPaymentEntityList;
	}
	
	/**
	 * 获取预付金额
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentPendingEntity  getPrePayAmount(WaybillPanelVo vo)
	{
		WaybillPaymentPendingEntity prePayAmount = new WaybillPaymentPendingEntity();
		prePayAmount.setWaybillNo(vo.getWaybillNo());
		prePayAmount.setType(WaybillConstants.PAYMENT_PRE_PAY);//预付金额类型
		prePayAmount.setAmount(vo.getPrePayAmount());//预付金额
		prePayAmount.setActive(FossConstants.ACTIVE);
		prePayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);//RMB
		prePayAmount.setPaymentTime(new Date());
		return prePayAmount;
		
	}
	/**
	 * 到付金额
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private WaybillPaymentPendingEntity getToPayAmount(WaybillPanelVo vo) {

		WaybillPaymentPendingEntity toPayAmount = new WaybillPaymentPendingEntity();
		toPayAmount.setWaybillNo(vo.getWaybillNo());
		toPayAmount.setType(WaybillConstants.PAYMENT_TO_PAY);// 到付金额类型
		toPayAmount.setAmount(vo.getToPayAmount());// 到付金额
		toPayAmount.setActive(FossConstants.ACTIVE);
		toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		toPayAmount.setPaymentTime(new Date());
		return toPayAmount;
	}

	/**
	 * 手写先付金额
	 * @author 026113-FOSS-linwensheng
	 * @return WaybillPaymentEntity
	 */
	private  WaybillPaymentPendingEntity getHandWriteMoney(WaybillPanelVo vo) {
		WaybillPaymentPendingEntity toPayAmount = new WaybillPaymentPendingEntity();
		toPayAmount.setWaybillNo(vo.getWaybillNo());
		toPayAmount.setType(WaybillConstants.PAYMENT_REAL_PAY);// 手写先付金额类型
		toPayAmount.setAmount(vo.getHandWriteMoney());// 手写先付金额
		toPayAmount.setActive(FossConstants.ACTIVE);
		toPayAmount.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);// RMB
		toPayAmount.setPaymentTime(new Date());
		return toPayAmount;
	}
}
