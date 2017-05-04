package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBargainAppOrgService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusBargainService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPreferentialService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IReturnGoodsRequestEntityService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BargainAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusBargainEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CustomerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ReturnGoodsRequestEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PreferentialInfoDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.CusBargainVo;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.pricing.api.server.dao.ISaveEcsExpressBillLogDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IGuiBillCaculateService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPriceCrmOrderService;
import com.deppon.foss.module.pickup.pricing.api.server.service.IWaybillExpressPriceServiceForECS;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.DiscountTypeConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.PriceEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.EscWayBillMiddleEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.CouponInfoResultDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiQueryBillCalculateSubDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultBillCalculateDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.GuiResultDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.PriceDiscountDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.QueryBillCacilateValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.dto.ValueAddDto;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.BillCaculateServiceException;
import com.deppon.foss.module.pickup.pricing.api.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.pricing.api.shared.vo.WaybillDiscountVo;
import com.deppon.foss.module.pickup.waybill.shared.domain.EscWayBillRequestEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillChargeDtlEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.AmountInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.CouponWaybillInfoDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscResultBillCalculateDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscResultDiscountDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EscValuationEntryDto;
import com.deppon.foss.module.pickup.waybill.shared.response.EscWayBillResponseEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * FOSS 提供快递总运费
 * @author Foss-308595-GELL
 *
 */
@Service
public class WaybillExpressPriceServiceForECS implements IWaybillExpressPriceServiceForECS {

	@Resource
	private IGuiBillCaculateService guiBillCaculateService;
	
	private static final double POINT_05 = 0.5;
	/**
	 * 价格计算服务
	 */
	@Resource
	private IBillCaculateService billCaculateService;
	private BigDecimal defaultFirstWeight = BigDecimal.valueOf(POINT_05);
	
	/**
	 * 客户合同优惠信息接口
	 */
	@Resource
	IPreferentialService preferentialService;
	
	public void setPreferentialService(IPreferentialService preferentialService) {
		this.preferentialService = preferentialService;
	}
	
	@Resource
	IReturnGoodsRequestEntityService returnGoodsRequestEntityService;
	public void setReturnGoodsRequestEntityService(
			IReturnGoodsRequestEntityService returnGoodsRequestEntityService) {
		this.returnGoodsRequestEntityService = returnGoodsRequestEntityService;
	}
		
	/**
	 * 订单服务(WebService)
	 */
	@Resource
	IPriceCrmOrderService priceCrmOrderService;
	
	@Resource
	ICusBargainService cusBargainService;
	
	/**
	 * 客户服务
	 */
	@Resource
	private ICustomerService customerService;
	
	@Resource
	IConfigurationParamsService configurationParamsService;
	
	@Resource
	private IBargainAppOrgService bargainAppOrgService;
	
	/**
	 * 组织服务
	 */
	@Resource
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	
	/**
     * 小数点保留位数
     */
	private static int newScale = 2;
	
	/**
	 * 保存计算请求日志
	 */
	@Resource
	private ISaveEcsExpressBillLogDao saveEcsExpressBillLogDao;
	
	/**
	 * 开单查询总运费     葛亮亮
	 */
	@Override
	public EscWayBillResponseEntity queryBillingPrice(EscWayBillRequestEntity requestEntity) {
		boolean   isAccurateCost=false;
		//返回实体
		EscWayBillResponseEntity escWayBillResponseEntity = new EscWayBillResponseEntity();
		//中间转化使用类
		EscWayBillMiddleEntity escWayBillMiddleEntity = new EscWayBillMiddleEntity();
		//有一些费用可以在一开始就从请求类中提取出来放到中间转化类
		initWayBillMiddleEntity(requestEntity, escWayBillMiddleEntity);
		
		try {
			if(null == requestEntity){
				escWayBillResponseEntity.setSuccess("0");
				escWayBillResponseEntity.setMessage("请求实体为空");
				return escWayBillResponseEntity;
			}
		
			//是否精准计价
			if (PricingConstants.MONTH_PAYMENT.equals(requestEntity.getPaidMethod())) {
				CustomerEntity customerEntity=customerService.queryCustomerInfoByCusCode(requestEntity.getCustomerCode());
				if(customerEntity!=null&&"Y".equalsIgnoreCase(customerEntity.getIsAccuratePrice())){
					isAccurateCost=true;
				}				
			}
			// 判断是否内部带货:如果内部带货，不能计算优惠券
			if (!PricingConstants.DEPPON_CUSTOMER.equals(requestEntity.getCustomerCode())
					&& !PricingConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL
							.equals(requestEntity.getReturnType())) {
				//计算各种运费
				calculateFee(requestEntity, escWayBillMiddleEntity, false,isAccurateCost);
				
				//如果有优惠券编号， 需要计算两次总运费：原因是， 优惠券要求把总运费传到CRM进行校验
				if (!StringUtils.isEmpty(requestEntity.getPromotionsCode())) {
					//快递没有整车
					
					//内部发货不使用优惠券 dp-foss-dongjialing 225131
					if (StringUtil.isBlank(requestEntity.getInternalDeliveryType())
							|| StringUtil.isBlank(requestEntity.getEmployeeNo())) {
						// 处理优惠券
						executeCoupon(requestEntity, escWayBillMiddleEntity);
						// 设置未冲减优惠券的运费
						escWayBillMiddleEntity.setBeforeProTranFee(escWayBillMiddleEntity.getTransportFee());
					}
				}
				//设置优惠总费用
				calcaulatePromotionsFee(escWayBillMiddleEntity, requestEntity);
				
				//在计算玩优惠费用后需要重新计算运费
				calculateTotalFee(requestEntity, escWayBillMiddleEntity, true);
			}else{
				// 计算内部带货公布价运费
				calculateInnerPickupTransFee(requestEntity, escWayBillMiddleEntity,isAccurateCost);
				// 内部带货金额清零（经过FOSS代码分析，内部带时公布价运费不会置为0，而上面就只计算了公布价运费所以不用清空值，）
				//resetZero();
			}
			
			// 处理增值优惠券费用
			offsetCouponFee(requestEntity, escWayBillMiddleEntity);
			
			//转化返回类
			setEscWayBillResponseEntity(escWayBillMiddleEntity, escWayBillResponseEntity, requestEntity, isAccurateCost);
			
			// 处理完优惠券清空优惠券费用，防止再次冲减
			escWayBillMiddleEntity.setCouponFree(BigDecimal.ZERO);
			
			//设置成功标识
			escWayBillResponseEntity.setSuccess("1");
			
			//返回实体
			return escWayBillResponseEntity;
		}catch (MarkActivitiesException e){
			escWayBillResponseEntity.setSuccess("0");
			escWayBillResponseEntity.setMessage("开单计算运费异常！"+ e.getErrorCode());
			return escWayBillResponseEntity;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			escWayBillResponseEntity.setSuccess("0");
			escWayBillResponseEntity.setMessage(e.getMessage());
			if(null == e.getMessage() || e.getMessage().isEmpty()){
				escWayBillResponseEntity.setMessage("开单计算运费异常！");
			}
			return escWayBillResponseEntity;
		}
	}
	
	/**
	 * 有一些费用可以在一开始就从请求类中提取出来放到中间转化类
	 */
	public void initWayBillMiddleEntity(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		
		//先使用请求类中传递过来的装卸费填充
		escWayBillMiddleEntity.setServicefee(requestEntity.getServicefee());
		//先使用请求类中传递过来的填充保存数据库
		escWayBillMiddleEntity.setDcServicefee(requestEntity.getServicefee());
		//是否在前台显示（先设置为可编辑）
		escWayBillMiddleEntity.setServiceChargeFlag(true);
		
		// 获取保价申明价值
		BigDecimal insuranceAmount = BigDecimal.ZERO;
		//将 代收货款 从提供的计价LIST中取出来
		BigDecimal codAmount = BigDecimal.ZERO;
		//包装费(快递没有包装费)，如果在页面填写包装费后需要取出来进行累加
		BigDecimal packageFee = BigDecimal.ZERO;
		//送货费(快递没有送货费)，如果页面填写了送货费需要取出来进行累加
		BigDecimal deliveryGoodsFee = BigDecimal.ZERO;
		//其他费用明细(经过沟通，悟空传过来多少其他费用，接口就计算多少其他费用项)
		List<EscValuationEntryDto> escValuationEntryDto = new ArrayList<EscValuationEntryDto>();	
		//返单类别
		String qsSubType = PricingConstants.NOT_RETURN_BILL;
		for(EscValuationEntryDto escEntry : requestEntity.getPriceEntities()){
			if(PriceEntityConstants.PRICING_CODE_BF.equals(escEntry.getPriceEntityCode())){
				insuranceAmount = escEntry.getOriginnalCost();
			}else if(PriceEntityConstants.PRICING_CODE_HK.equals(escEntry.getPriceEntityCode())){
				codAmount = escEntry.getOriginnalCost();
			}else if(PriceEntityConstants.PRICING_CODE_BZ.equals(escEntry.getPriceEntityCode())){
				packageFee = escEntry.getOriginnalCost();
			}else if(PriceEntityConstants.PRICING_CODE_SH.equals(escEntry.getPriceEntityCode())){
				deliveryGoodsFee = escEntry.getOriginnalCost();
			}else if(PriceEntityConstants.PRICING_CODE_QT.equals(escEntry.getPriceEntityCode())){
				escValuationEntryDto.add(escEntry);
			}else if(PriceEntityConstants.PRICING_CODE_QS.equals(escEntry.getPriceEntityCode())){
				//返单类别  原件签收单返回
				if(PricingConstants.RETURNBILLTYPE_ORIGINAL.equals(escEntry.getSubType())){
					qsSubType = PricingConstants.RETURNBILLTYPE_ORIGINAL;									
				}else if(PricingConstants.RETURNBILLTYPE_FAX.equals(escEntry.getSubType())){
					qsSubType = PricingConstants.RETURNBILLTYPE_FAX;					
				}
			}
		}
		//保价声明价值存入中间转化类
		escWayBillMiddleEntity.setInsuranceAmount(insuranceAmount);
		//代收货款
		escWayBillMiddleEntity.setCodAmount(codAmount);
		//包装费
		escWayBillMiddleEntity.setPackageFee(packageFee);
		//送货费
		escWayBillMiddleEntity.setDeliveryGoodsFee(deliveryGoodsFee);
		//其他费用
		escWayBillMiddleEntity.setOtherChargeVos(escValuationEntryDto);
		//返单类别
		escWayBillMiddleEntity.setQsSubType(qsSubType);
		
		//其他费用累计
		BigDecimal otherCharge = BigDecimal.ZERO;				
		for(EscValuationEntryDto escEntry : escWayBillMiddleEntity.getOtherChargeVos()){			
			//累计其他费用金额
			otherCharge = otherCharge.add(escEntry.getOriginnalCost());
		}
		//将其他费用金额存入返回实体
		escWayBillMiddleEntity.setOtherFee(otherCharge);		
		
	}
	
	/**
	 * 转化返回类
	 */
	public void setEscWayBillResponseEntity(EscWayBillMiddleEntity escWayBillMiddleEntity, 
			EscWayBillResponseEntity escWayBillResponseEntity, EscWayBillRequestEntity requestEntity,boolean accurateValuation){
		
		//运单号
		escWayBillResponseEntity.setWaybillNo(requestEntity.getWaybillNo());		
		//到付金额
		escWayBillResponseEntity.setToPayFee(escWayBillMiddleEntity.getToPayFee() != null ? escWayBillMiddleEntity.getToPayFee() : BigDecimal.ZERO);
		//预付金额 
		escWayBillResponseEntity.setPrePayFee(escWayBillMiddleEntity.getPrePayFee() != null ? escWayBillMiddleEntity.getPrePayFee() : BigDecimal.ZERO);		
		//公布价运费
		escWayBillResponseEntity.setTransportFee(escWayBillMiddleEntity.getTransportFee() != null ? escWayBillMiddleEntity.getTransportFee() : BigDecimal.ZERO);		
		//优惠总金额
		escWayBillResponseEntity.setPromotionsFee(escWayBillMiddleEntity.getPromotionsFee() != null ? escWayBillMiddleEntity.getPromotionsFee() : BigDecimal.ZERO);		
		//增值费用
		escWayBillResponseEntity.setValueAddFee(escWayBillMiddleEntity.getValueAddFee() != null ? escWayBillMiddleEntity.getValueAddFee() : BigDecimal.ZERO);		
		//其他费用
		escWayBillResponseEntity.setOtherFee(escWayBillMiddleEntity.getOtherFee() != null ? escWayBillMiddleEntity.getOtherFee() : BigDecimal.ZERO);		
		//总金额
		escWayBillResponseEntity.setTotalFee(escWayBillMiddleEntity.getTotalFee() != null ? escWayBillMiddleEntity.getTotalFee() : BigDecimal.ZERO);
		//加收方式(前台页面显示)
		escWayBillResponseEntity.setServicefee(escWayBillMiddleEntity.getServicefee() != null ? escWayBillMiddleEntity.getServicefee() : BigDecimal.ZERO);
		//折让方式-装卸费(数据库存储的费用)
		escWayBillResponseEntity.setDcServicefee(escWayBillMiddleEntity.getDcServicefee() != null ? escWayBillMiddleEntity.getDcServicefee() : BigDecimal.ZERO);
		//装卸费是否在前台页面展示（false：不在前台页面展示且不能编辑）
		escWayBillResponseEntity.setServiceChargeFlag(escWayBillMiddleEntity.isServiceChargeFlag());
		//代收手续费 
		escWayBillResponseEntity.setCodFee(escWayBillMiddleEntity.getCodFee() != null ? escWayBillMiddleEntity.getCodFee() : BigDecimal.ZERO);
		//保价费 
		escWayBillResponseEntity.setInsuranceFee(escWayBillMiddleEntity.getInsuranceFee() != null ? escWayBillMiddleEntity.getInsuranceFee() : BigDecimal.ZERO);
		//计费重量 
		escWayBillResponseEntity.setBillWeight(escWayBillMiddleEntity.getBillWeight());
		//优惠券，再传回去之前将优惠券最终金额和类型设置到返回类中
		if(null != escWayBillMiddleEntity.getCouponInfoDto()){
			escWayBillMiddleEntity.getCouponInfoDto().setCouponFree(escWayBillMiddleEntity.getCouponFree());
			escWayBillMiddleEntity.getCouponInfoDto().setCouponRankType(escWayBillMiddleEntity.getCouponRankType());
			//处理LIST
			AmountInfoDto amountInfoDto = new AmountInfoDto();
			amountInfoDto.setValuationCode("NONE");
			amountInfoDto.setValuationAmonut(BigDecimal.ZERO);
			escWayBillMiddleEntity.getCouponInfoDto().getWaybillInfo().getAmountInfoList().add(amountInfoDto);
		}
		escWayBillResponseEntity.setCouponInfoDto(escWayBillMiddleEntity.getCouponInfoDto());
		//费用信息
		setEscResultBill(escWayBillMiddleEntity.getGuiResultBillCalculateDtos(), escWayBillResponseEntity,accurateValuation);
	}
	
	/**
	 * 优惠券冲减费用
	 */
	public void offsetCouponFee(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		// 优惠费
		BigDecimal couponFee = defaultIfNull(escWayBillMiddleEntity.getCouponFree());
		// 优惠类型
		String type = defaultIfNull(escWayBillMiddleEntity.getCouponRankType());
	
		// 是否有优惠金额,是否已冲减
		if (couponFee.compareTo(BigDecimal.ZERO) > 0) {
			// 校验优惠类型是否符合条件 (通过FOSS代码对比，快递只冲减 PRICING_CODE_HK、PRICING_CODE_FRT)
			validateCouponTypeExpress(type);
			// 冲减费用类型
			if (PriceEntityConstants.PRICING_CODE_HK.equals(type)) {
				//校验费用是否符合要求
				validateFeeIsZero(escWayBillMiddleEntity.getCodFee(),type);
				// 冲减代收货款手续费
				BigDecimal codFee = defaultIfNull(escWayBillMiddleEntity.getCodFee());
				processPromotionsFee(requestEntity, escWayBillMiddleEntity, codFee,couponFee,
						PriceEntityConstants.PRICING_CODE_HK);
				codFee=codFee.subtract(couponFee);
				codFee = convertFeeToZero(codFee);
				//中间转化表重新设置代收货款手续费
				escWayBillMiddleEntity.setCodFee(codFee);
			}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(type)){
				//公布价运费优惠券的金额在在上面处理好优惠券后重新计算总运费时已经从公布价运费中减去
				processPromotionsFee(requestEntity,escWayBillMiddleEntity, 
						escWayBillMiddleEntity.getBeforeProTranFee(),couponFee,
						PriceEntityConstants.PRICING_CODE_FRT);
			}	
			
			//重新计算增值服务费
			resetCalculateFee(requestEntity, escWayBillMiddleEntity);
		}
	}
	
	/**
	 * 冲减玩增值优惠费后再次重新计算总运费
	 */
	public void resetCalculateFee(EscWayBillRequestEntity requestEntity,
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		//重新累加增值费用
		resetCalculateIncrement(escWayBillMiddleEntity);
		//重新计算总费用，注意不在冲减优惠费
		calculateTotalFee(requestEntity, escWayBillMiddleEntity, false);		
	}
	
	/**
	 * 
	 * 重新计算增值费用
	 * @author WangQianJin
	 * @date 2013-08-05
	 */
	private static void resetCalculateIncrement(EscWayBillMiddleEntity escWayBillMiddleEntity) {
		BigDecimal incrementFee = BigDecimal.ZERO;// 增值服务费
		BigDecimal deliveryGoodsFee = escWayBillMiddleEntity.getDeliveryGoodsFee();// 送货费
		if(deliveryGoodsFee==null){
			deliveryGoodsFee=BigDecimal.ZERO;
		}
		BigDecimal packageFee = escWayBillMiddleEntity.getPackageFee();// 包装费
		if(packageFee==null){
			packageFee=BigDecimal.ZERO;
		}
		BigDecimal insuranceFee = escWayBillMiddleEntity.getInsuranceFee();// 保价费
		if(insuranceFee==null){
			insuranceFee=BigDecimal.ZERO;
		}
		BigDecimal codFee = escWayBillMiddleEntity.getCodFee();// 代收手续费
		if(codFee==null){
			codFee=BigDecimal.ZERO;
		}
		
		// 接货费（快递没有接货费）

		BigDecimal otherFee = escWayBillMiddleEntity.getOtherFee();// 其他费用合计
		if(otherFee==null){
			otherFee=BigDecimal.ZERO;
		}
		// 增值服务费=送货费+包装费+保价费+代收手续费+其他费用合计
		incrementFee = deliveryGoodsFee.add(packageFee).add(insuranceFee)
				.add(codFee).add(otherFee);
		
		//中间转换表重新付值增值费用
		escWayBillMiddleEntity.setIncrementFee(incrementFee);
		escWayBillMiddleEntity.setValueAddFee(incrementFee);
	} 
	
	/**
	 * 检验优惠冲减后的费用是否正确
	 */
	private static BigDecimal convertFeeToZero(BigDecimal fee){
		BigDecimal value = defaultIfNull(fee);
		//检验优惠费用是否小于0
		if(BigDecimal.ZERO.compareTo(value) > 0 ){
			return BigDecimal.ZERO;
		}else{
			return value;
		}
	}
	
	//处理优惠券金额
	private void processPromotionsFee(EscWayBillRequestEntity requestEntity,
			EscWayBillMiddleEntity escWayBillMiddleEntity, BigDecimal subFee, BigDecimal proFee,
			String priceEntryCode) {
		if(subFee!=null && proFee!=null){
			//如果优惠券金额大于要冲减的金额，则重新设置优惠券金额为要冲减的金额
			if(proFee.compareTo(subFee) > 0 ){				
				//优惠信息
				List<WaybillDiscountVo> waybillDiscountVos = escWayBillMiddleEntity.getWaybillDiscountVo();				
				if(waybillDiscountVos!=null && waybillDiscountVos.size()>0){
					//优惠总费用
					BigDecimal totalPromotionsFee = BigDecimal.ZERO;
					//将所有的优惠金额累加，如果原先有这次的优惠项，使用这次的金额覆盖
					for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
						if(requestEntity.getPromotionsCode()!=null && requestEntity.getPromotionsCode().equals(waybillDiscountVo.getDiscountId())){
							waybillDiscountVo.setFavorableAmount(subFee.toString());
							//将优惠券信息存入返回计价明细
							setDZYHQDiscount(escWayBillMiddleEntity, waybillDiscountVo.getFavorableItemCode(), subFee);
						}
						totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(waybillDiscountVo.getFavorableAmount()));
					}
					
					//中间转换类新设置优惠费用
					escWayBillMiddleEntity.setPromotionsFee(totalPromotionsFee);					
					//中间转换类新设置优惠明细
					escWayBillMiddleEntity.setWaybillDiscountVo(waybillDiscountVos);
				}
			}else{//如果优惠券金额小于要冲减的金额，使用优惠券金额
				setDZYHQDiscount(escWayBillMiddleEntity, priceEntryCode, proFee);
			}
		}
	}
	
	/**
	 * 将优惠券实际冲减金额存放返回计价明细中的折扣信息中
	 */
	public void setDZYHQDiscount(EscWayBillMiddleEntity escWayBillMiddleEntity, String priceEntryCode,
			BigDecimal reduceFee){
		for(GuiResultBillCalculateDto billCalculateDto : escWayBillMiddleEntity.getGuiResultBillCalculateDtos()){
			if(priceEntryCode.equals(billCalculateDto.getPriceEntryCode())){
				List<GuiResultDiscountDto> discountDtoList = billCalculateDto.getDiscountPrograms();
				if(null == discountDtoList){
					discountDtoList = new ArrayList<GuiResultDiscountDto>();
				}
				
				GuiResultDiscountDto discountDto = new GuiResultDiscountDto();
				discountDto.setPriceEntryCode(priceEntryCode); //计价条目CODE
				discountDto.setPriceEntryName("电子优惠券"); //计价条目Name
				discountDto.setSubType("YHJ");
				discountDto.setSubTypeName("discountDto");
				discountDto.setDiscountRate(BigDecimal.ZERO); //优惠费率
				discountDto.setReduceFee(reduceFee); //优惠金额
				discountDtoList.add(discountDto);
				billCalculateDto.setDiscountPrograms(discountDtoList); //重新设置优惠明细
				//将优惠券实际冲减金额和类型放到中间转换类 2016年6月15日 14:58:23
				escWayBillMiddleEntity.setCouponFree(reduceFee);
				escWayBillMiddleEntity.setCouponRankType(priceEntryCode);
				break;
			}
		}
	}
	
	/**
	 * 判断要使用优惠券的费用是否符合要求
	 */
	public static void validateFeeIsZero(BigDecimal fee,String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(null == fee){			
			//feeName为空，无法使用优惠券！
			throw new WaybillValidateException(feeName+"为空，无法使用优惠券！");	
		}
		if(fee.compareTo(new BigDecimal(0))==0){			
			//feeName等于，无法使用优惠券！
			throw new WaybillValidateException(feeName+"等于0，无法使用优惠券！");	
		}
		if(fee.doubleValue()<0){			
			//feeName小于0，无法使用优惠券！
			throw new WaybillValidateException(feeName+"小于0，无法使用优惠券！");	
		}
	}
	
	/**
	 * 判断要使用优惠券的类型是否符合要求（快递）
	 * @创建时间 2014-5-7 上午8:44:26   
	 * @创建人： WangQianJin
	 */
	public static void validateCouponTypeExpress(String type){
		//费用名称
		String feeName=convertFeeToName(type);
		if(PriceEntityConstants.PRICING_CODE_BF.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)){			
			//您输入的优惠券类型-feeName无法冲减，请手动调整费用！
			throw new WaybillValidateException("您输入的优惠编码类型-"+ feeName +"无法冲减，请手动调整费用！");	
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)			
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){
			//您输入的优惠券类型-feeName无法冲减，请选择营销活动！
			throw new WaybillValidateException("您输入的优惠编码类型-"+ feeName +"无法冲减，请选择营销活动！");
		}		
	}
	
	/**
	 * 计算内部带货公布价运费
	 */
	public void calculateInnerPickupTransFee(EscWayBillRequestEntity requestEntity,
			EscWayBillMiddleEntity escWayBillMiddleEntity,boolean isAccurateCost){
		// 需要直接把费用写进bean里
		if (PricingConstants.DEPPON_CUSTOMER.equals(requestEntity.getCustomerCode())) {
			
			//将请求实体处理成FOSS中的使用类
			GuiQueryBillCalculateDto billCalculateDto = getGuiQueryBillCalculateDto(requestEntity, escWayBillMiddleEntity);
			
			//计算公布价和增值类费用
			List<GuiResultBillCalculateDto> guiResultBillCalculateDtos = guiBillCaculateService.queryGuiExpressBillPrice(billCalculateDto);

			// 如果返回的价格为空，抛出业务异常
			if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
				throw new WaybillValidateException("没有查询到对应的价格，请添加对应的价格");
			}
			
			//公布价运费
			GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(guiResultBillCalculateDtos,PriceEntityConstants.PRICING_CODE_FRT, null);
			
			// 设置公布价运费的费用
			setProductPriceDtoCollection(dto, requestEntity, escWayBillMiddleEntity,isAccurateCost);
						
			// 设置最低一票
			BigDecimal minTransportFee = BigDecimal.ZERO;
			minTransportFee = dto.getMinFee();
			escWayBillMiddleEntity.setMinTransportFee(minTransportFee);
			
			//将计价信息明细存入中间表
			escWayBillMiddleEntity.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
		}
	}
	
	/**
	 * 设置优惠总费用
	 */
	public void calcaulatePromotionsFee(EscWayBillMiddleEntity escWayBillMiddleEntity, 
			EscWayBillRequestEntity requestEntity){
		//折扣信息
		List<WaybillDiscountVo> waybillDiscountVos = escWayBillMiddleEntity.getWaybillDiscountVo();
		/**
		 * 如果优惠价格为空或者为0时
		 */
		if (escWayBillMiddleEntity.getCouponFree() != null
				&& BigDecimal.ZERO.compareTo(escWayBillMiddleEntity.getCouponFree()) != 0) {
			if (waybillDiscountVos == null) {
				waybillDiscountVos = new ArrayList<WaybillDiscountVo>();

			}

			//判断优惠券是否需要添加到优惠费用中
			if (isAddPromotionsFeeByTypeExpress(escWayBillMiddleEntity.getCouponRankType())) {
				//MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
				WaybillDiscountVo waybillDiscountVo = new WaybillDiscountVo();
				// 优惠折扣项目名称
				waybillDiscountVo.setFavorableItemName(convertFeeToName(escWayBillMiddleEntity.getCouponRankType()));
				// 优惠折扣项目CODE
				waybillDiscountVo.setFavorableItemCode(defaultIfNull(escWayBillMiddleEntity.getCouponRankType()));
				// 优惠类别名称
				waybillDiscountVo.setFavorableTypeName("优惠券");
				// 优惠类别CODE
				waybillDiscountVo.setFavorableTypeCode("YHJ");
				waybillDiscountVo.setFavorableDiscount(BigDecimal.ZERO.toString());
				waybillDiscountVo.setFavorableAmount(escWayBillMiddleEntity.getCouponFree().toString());
				// 折扣ID(优惠编码)
				waybillDiscountVo.setDiscountId(requestEntity.getPromotionsCode());
				// 类型 discount 为公布价折扣 promotion 为增值服务优惠
				waybillDiscountVo.setFavorableTypeCode(PricingConstants.VALUATION_TYPE_DISCOUNT);

				waybillDiscountVos.add(waybillDiscountVo);
			}
		}
		// 优惠总费用
		BigDecimal totalPromotionsFee = BigDecimal.ZERO;
		// 是否享有推广活动折扣(这个条件只用作下面的页面提醒)
//		boolean flagActive = false;
		if (waybillDiscountVos != null) {
			for (WaybillDiscountVo waybillDiscountVo : waybillDiscountVos) {
				totalPromotionsFee = totalPromotionsFee.add(new BigDecimal(
						waybillDiscountVo.getFavorableAmount()));
				// 是否活动折扣
//				if (DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE
//						.equals(waybillDiscountVo.getFavorableTypeCode())) {
//					flagActive = true;
//				}
			}
		}
		/**
		 * 设置优惠费用
		 */
		totalPromotionsFee = totalPromotionsFee.setScale(0, BigDecimal.ROUND_HALF_UP);

		//中间转换类
		escWayBillMiddleEntity.setPromotionsFee(totalPromotionsFee);
		escWayBillMiddleEntity.setWaybillDiscountVo(waybillDiscountVos);
		
		// 选择了营销活动但未享受，给予用户提示 (接口不提供这样的功能)		
//		if (bean.getActiveInfo() != null
//				&& StringUtils.isNotEmpty(bean.getActiveInfo().getValueCode())) {
//			if (!flagActive) {
//				MsgBox.showInfo(i18n
//						.get("foss.gui.creating.expcalculateAction.activeinfo.nohave.actdis"));
//			}
//		}
	}
	
	/**
	 * 根据优惠券类型判断是否要添加到优惠费用中(快递)
	 */
	public static boolean isAddPromotionsFeeByTypeExpress(String type){		
		boolean isAdd=true;				
		if(PriceEntityConstants.PRICING_CODE_JH.equals(type) 
				|| PriceEntityConstants.PRICING_CODE_SH.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BF.equals(type)
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(type)
				|| PriceEntityConstants.PRICING_CODE_ZHXX.equals(type)){			
			//快递只处理代收货款和公布价运费的优惠券
			isAdd=false;
		}		
		return isAdd;
	}	
	
	/**
	 * 根据费用类型编码获取对应中文名
	 */
	public static String convertFeeToName(String feeCode){
		//费用编码
		String code = defaultIfNull(feeCode);
		//费用名称
		String name = "";
		if(PriceEntityConstants.PRICING_CODE_TOTAL.equals(code)){
			name = "总费用";
		}else if(PriceEntityConstants.PRICING_CODE_FRT.equals(code)){
			name = "运费";
		}else if(PriceEntityConstants.PRICING_CODE_VALUEADDED.equals(code)){
			name = "增值服务";
		}else if(PriceEntityConstants.PRICING_CODE_BF.equals(code)){
			name = "保费";
		}else if(PriceEntityConstants.PRICING_CODE_HK.equals(code)){
			name = "代收货款";
		}else if(PriceEntityConstants.PRICING_CODE_SH.equals(code)){
			name = "送货费";
		}else if(PriceEntityConstants.PRICING_CODE_SHSL.equals(code)){
			name = "送货上楼费";
		}else if(PriceEntityConstants.PRICING_CODE_SHJC.equals(code)){
			name = "送货进仓费";
		}else if(PriceEntityConstants.PRICING_CODE_CY.equals(code)){
			name = "超远派送费";
		}else if(PriceEntityConstants.PRICING_CODE_JH.equals(code)){
			name = "接货费";
		}else if(PriceEntityConstants.PRICING_CODE_CCF.equals(code)){
			name = "仓储费";
		}else if(PriceEntityConstants.PRICING_CODE_QT.equals(code)){
			name = "其他费用";
		}else if(PriceEntityConstants.PRICING_CODE_BZ.equals(code)){
			name = "包装费用";
		}else if(PriceEntityConstants.PRICING_CODE_ZZ.equals(code)){
			name = "中转费";
		}else if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(code)){
			name = "综合信息服务费";
		}else if(PriceEntityConstants.PRICING_CODE_KDBZF.equals(code)){
			name = "快递包装费";
		}
		
		return name;
	}	
	
	/**
	 * 若为null，则设置默认值
	 */
	public static String defaultIfNull(String str){
		//若对象为空，则返回空字符串
		if(null == str){
			return "";
		}else{
			return str;
		}
	}
	
	/**
	 * 若为null，则设置默认值
	 */
	public static BigDecimal defaultIfNull(BigDecimal num){
		//若对象为空，则返回0值
		if(null == num){
			//返回0值 
			return BigDecimal.valueOf(0);
		}
		/**
		 * 返回原值
		 * 不能四舍五入，否则体积为0.01的，在导入或补录时就变成0了
		 */
		//return num.setScale(2, BigDecimal.ROUND_HALF_UP);
		return num;
	}
	
	/**
	 * 处理优惠券 2016年5月11日 14:35:49 葛亮亮
	 */
	public void executeCoupon(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity){
		//封装CRM查询优惠券需要类
		CouponInfoDto couponInfoDto = getCouponInfoDto(requestEntity, escWayBillMiddleEntity);
		
		if (couponInfoDto != null) {
			//转换CRM优惠券接口异常信息
			CouponInfoResultDto dto = new CouponInfoResultDto();
			try {
				dto = priceCrmOrderService.validateCoupon(couponInfoDto);
			} catch (Exception e1) {
				String exceptionType = e1.getMessage();
				if(PricingConstants.CONVERT_VALID_ATE_ERROR.equals(exceptionType)){
					throw new WaybillValidateException("调用优惠劵验证接口失败-数据对象转换错误");
				}else{
					throw new WaybillValidateException("调用优惠劵验证接口失败");
				}				
			}

			if (dto != null) {
				// 判断：该优惠券是否已被使用
				if (!dto.isCanUse()) {

					dto.getCanNotUseReason();
					// "您的优惠券已使用，不可重复使用！(waybill:9876543210;value:50)"
					String canNotUseReason = StringUtil.defaultIfNull(dto.getCanNotUseReason());
					String waybill = StringUtils.substringBetween(canNotUseReason, ":", ";");

					// 判断：该优惠券是否是被该运单使用的（从不可使用原因的字符串中截取使用该优惠券的运单号）
					if (!requestEntity.getWaybillNo().equals(StringUtil.defaultIfNull(waybill).trim())) {
						// 不能使用优惠券的原因
						throw new WaybillValidateException(canNotUseReason);
					} else {
						String lastAmount = StringUtils.substringAfterLast(canNotUseReason, "value:");
						lastAmount = StringUtils.substringBeforeLast(lastAmount, ")");
						lastAmount = StringUtils.substringBeforeLast(lastAmount, "}");

						/**
						 * 设置优惠券费用
						 */
						try {
							escWayBillMiddleEntity.setCouponFree(new BigDecimal(lastAmount));
						} catch (Exception e) {
							escWayBillMiddleEntity.setCouponFree(BigDecimal.ZERO);
						}
						/**
						 * 设置优惠券返回实体
						 */
						escWayBillMiddleEntity.setCouponInfoDto(couponInfoDto);

						/**
						 * 设置优惠券冲减类型 MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
						 */
						escWayBillMiddleEntity.setCouponRankType(dto.getDeductibleType());
					}
				} else {
					// 优惠金额
					if (dto.getCouponAmount() != null) {
						/**
						 * 设置优惠券费用
						 */
						escWayBillMiddleEntity.setCouponFree(dto.getCouponAmount());
						/**
						 * 设置优惠券返回实体
						 */
						escWayBillMiddleEntity.setCouponInfoDto(couponInfoDto);

						/**
						 * 设置优惠券冲减类型 MANA-1961 营销活动与优惠券编码关联 2014-04-10 026123
						 */
						escWayBillMiddleEntity.setCouponRankType(dto.getDeductibleType());
					} else {
						throw new WaybillValidateException("查询优惠券--优惠券优惠金额为0！");
					}
				}
			}
		}
	}
	
	/**
	 * 封装CRM查询优惠券需要类(仅仅封装查询CRM优惠接口需要的字段)
	 */
	public CouponInfoDto getCouponInfoDto(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity){
		// 优惠信息DTO
		CouponInfoDto couponInfo = new CouponInfoDto();
		// 运单信息
		CouponWaybillInfoDto waybillInfo = new CouponWaybillInfoDto();
		// 运单号
		waybillInfo.setWaybillNumber(requestEntity.getWaybillNo());
		// 产品号
		waybillInfo.setProduceType(requestEntity.getProductCode());
		// 订单号
		waybillInfo.setOrderNumber(requestEntity.getOrderNo());
		//订单来源
		waybillInfo.setOrderSource(requestEntity.getOrderChannel());
		
		// 判断总金额是否已有
		if (escWayBillMiddleEntity.getTotalFee() != null
				&& escWayBillMiddleEntity.getTotalFee().compareTo(BigDecimal.ZERO) == 0) {
			throw new WaybillValidateException("查询优惠券--请先计算总金额！");
		}
		
		// 总金额
		waybillInfo.setTotalAmount(escWayBillMiddleEntity.getTotalFee());
		// 发货人手机
//		waybillInfo.setLeaveMobile(bean.getDeliveryCustomerMobilephone());
		// 发货人电话
//		waybillInfo.setLeavePhone(bean.getDeliveryCustomerPhone());
		// 客户编码
		waybillInfo.setCustNumber(requestEntity.getCustomerCode());
		// 获取出发部门
		String receiveOrgCode = requestEntity.getOriginalOrgCode();		
		OrgAdministrativeInfoEntity receiveOrgAdministrative = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(receiveOrgCode);		
		if (receiveOrgAdministrative == null) {
			throw new WaybillValidateException("查询优惠券--未查询出有效的出发部门！");
		}
		
		// 最终配载部门-也就是最后一个自有网点
		String lastLoadOrgCode = requestEntity.getLastLoadOrgCode();
		if (lastLoadOrgCode == null) {
			throw new WaybillValidateException("查询优惠券--未获取到有效的最终配载部门编码！");
		}		
		OrgAdministrativeInfoEntity lastLoadOrgAdministrative = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(lastLoadOrgCode);		
		if (lastLoadOrgAdministrative == null) {
			throw new WaybillValidateException("查询优惠券--未查询出有效的最终配载部门！");
		}
		
		// 始发配载部门
		String loadOrgCode = requestEntity.getLoadOrgCode();
		if (loadOrgCode == null) {
			throw new WaybillValidateException("查询优惠券--未获取到有效的始发配载部门编码！");
		}

		// 始发配载部门标杆编码
		String firstLoadOutOrgInfoCode = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(loadOrgCode).getUnifiedCode();
				
		// 最终外场编码
		String lastLoadOutOrgInfoCode = null;
		if (!StringUtils.isEmpty(requestEntity.getLastOutLoadOrgCode())) {
			// 获取最终外场标杆编码
			lastLoadOutOrgInfoCode = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(requestEntity.getLastOutLoadOrgCode()).
					getUnifiedCode();
		} else {
			throw new WaybillValidateException("查询优惠券--最终配载部门编码为空！");
		}
				
		// 发货部门-标杆编码
		waybillInfo.setLeaveDept(receiveOrgAdministrative.getUnifiedCode());
		// 到达部门-标杆编码-由于偏线最后到达部门是代理，这里是最后一个只有部门
		waybillInfo.setArrivedDept(lastLoadOrgAdministrative.getUnifiedCode());
		// 发货部门所在外场
		waybillInfo.setLeaveOutDept(firstLoadOutOrgInfoCode);
		// 到达部门所在外场
		waybillInfo.setArrivedOutDept(lastLoadOutOrgInfoCode);
		
		// 获取费用明细
		List<WaybillChargeDtlEntity> waybillChargeDtlEntitys = getWaybillChargeDtlEntity(requestEntity, 
				escWayBillMiddleEntity);
		
		List<AmountInfoDto> amountInfoList = new ArrayList<AmountInfoDto>();
		for (WaybillChargeDtlEntity waybillChargeDtlEntity : waybillChargeDtlEntitys) { // 计价明细
			AmountInfoDto amountInfo = new AmountInfoDto();

			if (PriceEntityConstants.PRICING_CODE_SH.equals(waybillChargeDtlEntity.getPricingEntryCode())
				|| PriceEntityConstants.PRICING_CODE_SHSL.equals(waybillChargeDtlEntity.getPricingEntryCode())
				|| PriceEntityConstants.PRICING_CODE_SHJC.equals(waybillChargeDtlEntity.getPricingEntryCode())) {
				// 计价条目编码-送货费
				amountInfo.setValuationCode(PriceEntityConstants.PRICING_CODE_SH);
				// 获取通过计算得到的送货费 （快递不计算送货费所以默认为0。那为什么包装费不也用计算后的费用？包装费也不用计算）
				amountInfo.setValuationAmonut(BigDecimal.ZERO);
			} else {
				// 计价条目编码-保险费
				amountInfo.setValuationCode(waybillChargeDtlEntity.getPricingEntryCode());
				// 计价金额
				amountInfo.setValuationAmonut(waybillChargeDtlEntity.getAmount());
			}
			amountInfoList.add(amountInfo);
		}
		waybillInfo.setAmountInfoList(amountInfoList);
		couponInfo.setWaybillInfo(waybillInfo);
		couponInfo.setCouponNumber(requestEntity.getPromotionsCode());
		return couponInfo;
	}
	
	/**
	 * 获取费用明细
	 */
	public List<WaybillChargeDtlEntity> getWaybillChargeDtlEntity(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		List<WaybillChargeDtlEntity> chargeDtlEntityList = new ArrayList<WaybillChargeDtlEntity>();
		// 添加代收货款费用明细
		if(escWayBillMiddleEntity.getCodFee().compareTo(BigDecimal.ZERO) > 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_HK);
			chargeEntity.setAmount(escWayBillMiddleEntity.getCodFee());
			chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);       //暂时存null，认为CRM要这个ID没有用
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
		// 添加送货费,快递不计算送货费，只能在页面修改
		if(escWayBillMiddleEntity.getDeliveryGoodsFee().compareTo(BigDecimal.ZERO) > 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_SH);
			chargeEntity.setAmount(escWayBillMiddleEntity.getDeliveryGoodsFee());
			chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}

		// 添加接货费(快递没有接货费)
		
		// 添加保险费 
		if(escWayBillMiddleEntity.getInsuranceFee().compareTo(BigDecimal.ZERO) > 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BF);
			chargeEntity.setAmount(escWayBillMiddleEntity.getInsuranceFee());
			chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);       //暂时存null，认为CRM要这个ID没有用
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
		// 添加包装费，快递不计算包装费，只能在页面修改
		if(escWayBillMiddleEntity.getPackageFee().compareTo(BigDecimal.ZERO) > 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_BZ);
			chargeEntity.setAmount(escWayBillMiddleEntity.getPackageFee());
			chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
		// 添加公布价运费
		if(escWayBillMiddleEntity.getTransportFee().compareTo(BigDecimal.ZERO) > 0){
			WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
			chargeEntity.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
			chargeEntity.setAmount(escWayBillMiddleEntity.getTransportFee());
			chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
			chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);
			chargeEntity.setActive(FossConstants.ACTIVE);
			chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			chargeDtlEntityList.add(chargeEntity);
		}
		
		// 添加其他费用
		if(escWayBillMiddleEntity.getOtherFee().compareTo(BigDecimal.ZERO) > 0){
			for(EscValuationEntryDto escEntry : escWayBillMiddleEntity.getOtherChargeVos()){
				WaybillChargeDtlEntity chargeEntity = new WaybillChargeDtlEntity();
				chargeEntity.setPricingEntryCode(escEntry.getSubType());
				chargeEntity.setAmount(escEntry.getOriginnalCost());
				chargeEntity.setWaybillNo(requestEntity.getWaybillNo());
				chargeEntity.setPricingCriDetailId(PricingConstants.HAND_INPUT);
				chargeEntity.setActive(FossConstants.ACTIVE);
				chargeEntity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
				chargeDtlEntityList.add(chargeEntity);
			}
		}

		return chargeDtlEntityList;
	}
	
	/**
	 * 计算各种运费
	 * 葛亮亮 2016年5月9日 09:53:48
	 * 悟空 要求 签收回单的费用 不按照 FOSS 中累加到公布价运费中来，单独且需要返回价格明细
	 */
	public void calculateFee(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity,
			boolean needMinusCoupen,boolean accurateValuation){
		//FOSS计算公布价运费和增值服务LIST
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos;	
		//将请求实体处理成FOSS中的使用类		
		GuiQueryBillCalculateDto billCalculateDto = new GuiQueryBillCalculateDto();
		try {
			billCalculateDto = getGuiQueryBillCalculateDto(requestEntity, escWayBillMiddleEntity);
		} catch (Exception e1) {
			throw new WaybillValidateException("开单请求类转换成计价实体类异常");
		}
		
		//调用 FOSS 计价方法			
		try {
			guiResultBillCalculateDtos = guiBillCaculateService.queryGuiExpressBillPrice(billCalculateDto);
		} catch (BillCaculateServiceException e) {				
			throw new WaybillValidateException(e.getErrorCode());
		} catch (MarkActivitiesException e) {
			throw new WaybillValidateException(e.getErrorCode());
		} catch (Exception e){
			throw new WaybillValidateException("开单计价条目计算价格异常！");
		}
		
		// 如果返回的价格为空，抛出业务异常
		if (guiResultBillCalculateDtos == null || guiResultBillCalculateDtos.isEmpty()) {
			throw new WaybillValidateException("没有查询到对应的价格，请添加对应的价格");
		}
		/*费用明细特殊处理*/
		setSpecialHandlingBillCalculate(guiResultBillCalculateDtos, escWayBillMiddleEntity);
		
		//推广活动会对其他费用打折，重新在其他费用面板赋值(这边实际用处是用来重新计算下 其他总费，因为接口处理不了页面只能对数字进行加减，费用明细也在返回明细中)
		setterOtherFeeByMakActive(guiResultBillCalculateDtos, requestEntity, escWayBillMiddleEntity);
		
		List<GuiResultBillCalculateDto> guiResultBillCalculateDtos2 = null;
	    //小件 返单计算两次 第二次首重 把总费用加进去
		//签收单返单 需求 要把费用添加到增加费用中，不要添加到公布价运费中
		if (escWayBillMiddleEntity.getQsSubType() != null
			&& PricingConstants.RETURNBILLTYPE_ORIGINAL.equals(escWayBillMiddleEntity.getQsSubType())
			&& !PricingConstants.DEPPON_CUSTOMER.equals(requestEntity.getCustomerCode())) {

			GuiQueryBillCalculateDto dto2 = new GuiQueryBillCalculateDto();
			try {
				PropertyUtils.copyProperties(dto2, billCalculateDto);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			List<GuiQueryBillCalculateSubDto> priceEntities2 = new ArrayList<GuiQueryBillCalculateSubDto>();
			List<GuiQueryBillCalculateSubDto> priceEntities = billCalculateDto.getPriceEntities();
			
			for (GuiQueryBillCalculateSubDto d : priceEntities) {
				if (PriceEntityConstants.PRICING_CODE_FRT.equals(d.getPriceEntityCode())) {
					GuiQueryBillCalculateSubDto d2 = new GuiQueryBillCalculateSubDto();
					try {
						PropertyUtils.copyProperties(d2, d);
					} catch (Exception e) {
						e.printStackTrace();
					}
					priceEntities2.add(d2);
				}
			}
			dto2.setPriceEntities(priceEntities2);
			dto2.setWeight(defaultFirstWeight);
			dto2.setVolume(BigDecimal.ZERO);
			dto2.setCustomerCode("");
			dto2.setIsSelfPickUp(FossConstants.NO);
			// 根据DMANA-4938修改，标准快递和3.60特惠件以及商务专递的原件签单返回计费统一按照标准快递首重收费
			if (PricingConstants.ROUND_COUPON_PACKAGE.equals(dto2.getProductCode()) ||
					PricingConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT.equals(dto2.getProductCode())	) {
				dto2.setProductCode(PricingConstants.PACKAGE);
			}
			guiResultBillCalculateDtos2 = guiBillCaculateService.queryGuiExpressBillPrice(dto2);

			if (guiResultBillCalculateDtos2 != null && guiResultBillCalculateDtos2.size() > 0) {
				GuiResultBillCalculateDto qsDto = guiResultBillCalculateDtos2.get(0);
				if (qsDto != null && qsDto.getCaculateFee() != null) {
					
					List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(
							getQueryOtherChargeParam(requestEntity, escWayBillMiddleEntity));
					// 折扣优惠
					if (CollectionUtils.isNotEmpty(list)) {
						for (ValueAddDto addDto : list) {
							if (PriceEntityConstants.PRICING_CODE_QS.equals(addDto.getPriceEntityCode())) {
								//因为下面会将计算出来的运费作为签收费，所以这里先将明细中的费用设置成运费，如果有折扣下面会重新付值
								addDto.setCaculateFee(qsDto.getCaculateFee());
								// 优惠方案
								List<PriceDiscountDto> disDto = addDto.getDiscountPrograms();
								if (CollectionUtils.isNotEmpty(disDto)) {
									PriceDiscountDto dto = disDto.get(0);
									// z折扣费率
									BigDecimal discountRate = dto.getDiscountRate();
									if (discountRate != null) {
										// 折扣后费用
										BigDecimal fee = qsDto.getCaculateFee().multiply(discountRate);
										BigDecimal reduceFee = qsDto.getCaculateFee().subtract(fee);
										dto.setReduceFee(reduceFee);
										qsDto.setCaculateFee(fee.setScale(0,BigDecimal.ROUND_HALF_UP));
										//因为需要将签收回单明细存入返回的明细中所以需要将签收费用设置下
										addDto.setCaculateFee(fee.setScale(0,BigDecimal.ROUND_HALF_UP));
									}
								}
								//将签收回单的明细存入价格明细中
								setQsResultBillCalculate(addDto, guiResultBillCalculateDtos);
								//保存返回单折扣
								setReturnBillDiscount(addDto.getDiscountPrograms(), escWayBillMiddleEntity);
								break;
							}
						}
					}
				}
			}
		}
		
		//快递不能开整车，所以不用判定是否整车		
		// 获取公布价运费
		GuiResultBillCalculateDto dto = getGuiResultBillCalculateDto(
				guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_FRT, null);

		//原来的逻辑 把签收单返单的费用加入公布价中  ( 现在根据悟空要求签收费不再类加到公布价运费中 )
		if (guiResultBillCalculateDtos2 != null && guiResultBillCalculateDtos2.size() > 0) {
			GuiResultBillCalculateDto dto2 = guiResultBillCalculateDtos2.get(0);
			if (dto2 != null && dto2.getCaculateFee() != null) {
				//悟空后来要求放到运费中
				dto.setCaculateFee(dto.getCaculateFee().add(dto2.getCaculateFee()));
				//将签收费单独存入中间转化类（最终算到增值费用中其他费）
//				escWayBillMiddleEntity.setQsFee(dto2.getCaculateFee());
			}
		}

		// 返单开单 公布价为 0
		if (PricingConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(requestEntity.getReturnType())) {
			dto.setCaculateFee(BigDecimal.ZERO);
			//由于签收费单独出来，也需要置为0
//			escWayBillMiddleEntity.setQsFee(BigDecimal.ZERO);
		}
		
		/**
		 * PAD快递返货开单FOSS补录，如果上报原因为我司原因不计算公布价运费
		 * 这一块可能会有问题，不知道能不能取到 BSE.T_BAS_RETURN_GOODS 中的信息
		 */
		if(!PricingConstants.COMPANY_REASON.equals(requestEntity.getReturnBillReason()) 
				&& StringUtils.isNotEmpty(requestEntity.getOriWaybill()) 
				&& PricingConstants.WAYBILL_EXPRESSTYPE_RETURN_CARGO.equals(requestEntity.getReturnType())){
			ReturnGoodsRequestEntity e = returnGoodsRequestEntityService.queryReturnGoodsRequestEntityByWayBillNo(requestEntity.getOriWaybill());
			if(e != null && PricingConstants.RETURNREASON_COMPANY_REASON.equals(e.getReturnReason())){
				// 公布价运费设为0
				dto.setCaculateFee(BigDecimal.ZERO);
//				escWayBillMiddleEntity.setQsFee(BigDecimal.ZERO);
			}
		}
		
		//设置公布价运费
		// 如果返货开单 是我司原因 公布价为0
		if (PricingConstants.COMPANY_REASON.equals(requestEntity.getReturnBillReason())) {
			dto.setCaculateFee(BigDecimal.ZERO);
//			escWayBillMiddleEntity.setQsFee(BigDecimal.ZERO);
			dto.setDiscountPrograms(null);
		}
		
		//签收费独立出来算到其他费用(签收原件返回，因为签收金额在上面特殊情况下会置为0)。如果开单时先选择了返单类型，之后又取消了，那其他费用中的签收金额在悟空页面会删掉
		//原件返回放到运费中
//		if(escWayBillMiddleEntity.getQsSubType() != null
//				&& WaybillConstants.RETURNBILLTYPE_ORIGINAL.equals(escWayBillMiddleEntity.getQsSubType())){
//			//处理其他费用
//			BigDecimal qsFee = escWayBillMiddleEntity.getQsFee() != null ? escWayBillMiddleEntity.getQsFee() : BigDecimal.ZERO;
//			if(qsFee.compareTo(BigDecimal.ZERO) > 0){
//				setOtherFee(escWayBillMiddleEntity.getQsFee(), escWayBillMiddleEntity);
//			}else{
//				delOtherFee(escWayBillMiddleEntity, WaybillConstants.RETURNBILLTYPE_ORIGINAL);
//			}	
//			//重新累加其他费用
//			setTotalOtherFee(escWayBillMiddleEntity);
//			
//			//将返回明细中的签收费用重新置下
//			for(GuiResultBillCalculateDto calculateDto : guiResultBillCalculateDtos){
//				if(PriceEntityConstants.PRICING_CODE_QS.equals(calculateDto.getPriceEntryCode())){
//					calculateDto.setCaculateFee(escWayBillMiddleEntity.getQsFee());
//					break;
//				}
//			}
//		}else{
//			//这里只删除原单返回
//			delOtherFee(escWayBillMiddleEntity, WaybillConstants.RETURNBILLTYPE_ORIGINAL);
//
//			//重新累加其他费用
//			setTotalOtherFee(escWayBillMiddleEntity);
//		}
				
		//设置公布价集合
		setProductPriceDtoCollection(dto, requestEntity, escWayBillMiddleEntity,accurateValuation);
		
		/**
		 * 新添加 (gyk) 若单票包装费字段不为空，计算运费时从CRM中直接读取CRM中的单票包装费的值，且包装费字段置灰，不可修改。
		 */
		PreferentialInfoDto preferentialInfoDto = queryPreferentialInfo(requestEntity, escWayBillMiddleEntity);
		if (preferentialInfoDto != null) {
			if(preferentialInfoDto.getSinTicketPackCharge() != null) {
				    escWayBillMiddleEntity.setPackageFee(preferentialInfoDto.getSinTicketPackCharge());
//					ui.getBillingPayPanel().getTxtPackCharge().setEnabled(false);
				}
		}
		
		//如果是转寄退回需要判断是否有“新折扣”(要放在计算增值服务费之前处理，因为在处理增值服务费会在运费中添加装卸费)
		if(null != requestEntity.getReturnType() 
				&& ("RETURN_NORMAL".equals(requestEntity.getReturnType())
						|| "RETURN_CARGO".equals(requestEntity.getReturnType())
						|| "RETURN_PIECE".equals(requestEntity.getReturnType()))){
			setReturnBill(requestEntity, escWayBillMiddleEntity, dto);
		}
		
		// 计算增值服务费
		// 返单开单增值服务为0
		if (!(null != requestEntity.getReturnType()
			&& PricingConstants.WAYBILL_EXPRESSTYPE_RETURN_WAYBILL.equals(requestEntity.getReturnType()))) {
			//计算快递增值服务费
			try {
				calculateValueAdd(requestEntity, guiResultBillCalculateDtos, escWayBillMiddleEntity);
			} catch (BillCaculateServiceException e1) { 
				throw e1;
			} catch (Exception e) {
				throw new WaybillValidateException("开单增值服务费计算异常！");
			}
		}
		
		//计算总运费(这边计算总运费不需要冲减优惠券的运费)
		calculateTotalFee(requestEntity, escWayBillMiddleEntity, needMinusCoupen);
		
		//将包装费，送货费(等)加到返回计价明细中，因为包装费会"若单票包装费字段不为空，计算运费时从CRM中直接读取CRM中的单票包装费的值"，将送货费也一起传回去
		setGuiResultBillCalculateDtos(guiResultBillCalculateDtos, escWayBillMiddleEntity);
		
		//将计价信息明细存入中间表
		escWayBillMiddleEntity.setGuiResultBillCalculateDtos(guiResultBillCalculateDtos);
		
		//临时欠款（快递没有临时欠款）
	}
	
	/**
	 * 转寄退回处理
	 * 如果客户维护了快递返货运费折扣新折扣再在计算出来的公布价运费进行折扣
	 */
	public void setReturnBill(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity,
			GuiResultBillCalculateDto dto){
		//公布价运费
		BigDecimal transportFee = escWayBillMiddleEntity.getTransportFee();
		//打折后的金额
		BigDecimal tmpTransportFee = BigDecimal.ZERO;
		//优惠金额
		BigDecimal discountFee = BigDecimal.ZERO;
		
		//‘商务专递’,快递国际件不打折（快递报关通-标（GTSE）、国际快递-标（ICES）、快递报关通-快（GTEC）、国际快递-标（ICEC））返货不享受折扣
		if(!(PricingConstants.DEAP.equals(requestEntity.getProductCode())
				||"GTSE".equals(requestEntity.getProductCode())
				||"ICES".equals(requestEntity.getProductCode())
				||"GTEC".equals(requestEntity.getProductCode())
				||"ICEC".equals(requestEntity.getProductCode())
				)){
			//查询CRM 客户合同信息
			PreferentialInfoDto preferentialInfoDto = preferentialService.queryPreferentialInfo(
							requestEntity.getCustomerCode(), null, ProductEntityConstants.PRICING_PRODUCT_C2_C20005);
			
			if(preferentialInfoDto!=null){
				//原折扣
				if(PriceEntityConstants.OLD_PREFE.equals(preferentialInfoDto.getExpBackFreghtType())){
					//转寄退回 原折扣 统一按照首重折扣计算
					BigDecimal discount = preferentialInfoDto.getChargeRebate() ;//首重
					//折扣后的金额
					tmpTransportFee = transportFee.multiply(discount).setScale(0, BigDecimal.ROUND_HALF_UP);
					//折扣优惠的金额
					discountFee = transportFee.subtract(tmpTransportFee);
					//处理折扣信息
					setTransportFeeDiscount(tmpTransportFee, discount, discountFee, escWayBillMiddleEntity, dto);
				}else if(PriceEntityConstants.NEW_PREFE.equals(preferentialInfoDto.getExpBackFreghtType())){//新折扣
					//折扣率
					BigDecimal discount = preferentialInfoDto.getBackFreghtFixed();
					//折扣后的金额
					tmpTransportFee = transportFee.multiply(discount).setScale(0, BigDecimal.ROUND_HALF_UP);
					//折扣优惠的金额
					discountFee = transportFee.subtract(tmpTransportFee);
					//处理折扣信息
					setTransportFeeDiscount(tmpTransportFee, discount, discountFee, escWayBillMiddleEntity, dto);
				}
			}
		}
	}
	
	/**
	 * 设置运费折扣信息
	 */
	public void setTransportFeeDiscount(BigDecimal tmpTransportFee, BigDecimal discount, BigDecimal discountFee,
			EscWayBillMiddleEntity escWayBillMiddleEntity, GuiResultBillCalculateDto dto){
		//重新计算公布价运费费率（折扣后的金额）
		BigDecimal feeRate = tmpTransportFee.divide(escWayBillMiddleEntity.getBillWeight(), 2, BigDecimal.ROUND_HALF_DOWN);			
		//设置新费率
		escWayBillMiddleEntity.setFeeRate(feeRate);
		//设置最终运费
		escWayBillMiddleEntity.setTransportFee(tmpTransportFee);
		
		//添加公布价折扣优惠
		GuiResultDiscountDto discountDto = new GuiResultDiscountDto();
		//折扣率
		discountDto.setDiscountRate(discount);
		//渠道类型
		discountDto.setDiscountType("CUSTOMER_CONTRACT");
		discountDto.setDiscountTypeName("客户合同");
		discountDto.setMarketName("快递返货运费折扣新折扣");
		discountDto.setPriceEntryCode(PriceEntityConstants.PRICING_CODE_FRT);
		discountDto.setPriceEntryName("运费");
		//优惠金额
		discountDto.setReduceFee(discountFee);
		discountDto.setSaleChannelCode("CONTRACT_NORMAL");
		discountDto.setSaleChannelName("普通合同");
		
		//公布价原有折扣信息
		List<GuiResultDiscountDto> guiResultDiscountDtoList = dto.getDiscountPrograms();
		if(null == guiResultDiscountDtoList){
			guiResultDiscountDtoList = new ArrayList<GuiResultDiscountDto>();
		}
		
		//添加快递返货运费折扣新折扣
		guiResultDiscountDtoList.add(discountDto);
		
		//设置返回明细
		dto.setDiscountPrograms(guiResultDiscountDtoList);
		//设置运费
		dto.setCaculateFee(tmpTransportFee);
		//设置运费费率
		dto.setFeeRate(feeRate);
		
		// 设置公布价折扣优惠		
		List<WaybillDiscountVo> discountVo = new ArrayList<WaybillDiscountVo>();
		if(null != escWayBillMiddleEntity.getWaybillDiscountVo()){
			for(WaybillDiscountVo disVo : escWayBillMiddleEntity.getWaybillDiscountVo()){
				if(!disVo.getFavorableItemCode().equals(PriceEntityConstants.PRICING_CODE_FRT)){
					discountVo.add(disVo);
				}
			}
			escWayBillMiddleEntity.setWaybillDiscountVo(discountVo);
		}//因为公布价的折扣信息在这里要全部重新计算，所以清空中间转换表中运费折扣信息
		setDiscountPrograms(dto, escWayBillMiddleEntity);
	}
	
	
	/**
	 * 将包装费和送货费封装到返回计价明细
	 */
	public void setGuiResultBillCalculateDtos(List<GuiResultBillCalculateDto> guiResultBillCalculateDtos, 
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		//包装费
		GuiResultBillCalculateDto packCalculateDto = new GuiResultBillCalculateDto();
		packCalculateDto.setPriceEntryCode("BZ");
		packCalculateDto.setPriceEntryName("包装费");
		packCalculateDto.setCaculateFee(escWayBillMiddleEntity.getPackageFee());
		guiResultBillCalculateDtos.add(packCalculateDto);
		
		//送货费
		GuiResultBillCalculateDto deliveryCalculateDto = new GuiResultBillCalculateDto();
		deliveryCalculateDto.setPriceEntryCode("SH");
		deliveryCalculateDto.setPriceEntryName("送货费");
		deliveryCalculateDto.setCaculateFee(escWayBillMiddleEntity.getDeliveryGoodsFee());
		guiResultBillCalculateDtos.add(deliveryCalculateDto);
		
		//设置公布价运费
		for(GuiResultBillCalculateDto calculateDto : guiResultBillCalculateDtos){
			if(PriceEntityConstants.PRICING_CODE_FRT.equals(calculateDto.getPriceEntryCode())){
				//设置运费
				calculateDto.setCaculateFee(escWayBillMiddleEntity.getTransportFee());
				//设置运费费率
				calculateDto.setFeeRate(escWayBillMiddleEntity.getFeeRate());
				break;
			}
		}
	}
	
	/**
	 * gyk 调用crm接口查询客户合同信息 同时验证客户是否是部门绑定客户
	 * 
	 * @param bean
	 * @return
	 */
	private PreferentialInfoDto queryPreferentialInfo(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		String productCodeTemp = ProductEntityConstants.PRICING_PRODUCT_C2_C20005;
		PreferentialInfoDto preferentialInfoDto = preferentialService.queryPreferentialInfo(
						requestEntity.getCustomerCode(), null, productCodeTemp);
		boolean isDiscount = false;
		if (preferentialInfoDto != null) {
			String unicode = null;
			OrgAdministrativeInfoEntity orgInfo = orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(requestEntity.getOriginalOrgCode());
			if(null != orgInfo){
				unicode = orgInfo.getUnifiedCode();
			}			
			List<String> list = new ArrayList<String>();
			if (unicode != null) {
				list.add(unicode);
			}
			// 验证客户是否是部门绑定客户
			List<BargainAppOrgEntity> bargainAppOrgEntities = bargainAppOrgService.queryAppOrgByBargainCrmId(
							preferentialInfoDto.getCusBargainId(), list);
			if (CollectionUtils.isNotEmpty(bargainAppOrgEntities)) {
				for (int i = 0; i < bargainAppOrgEntities.size(); i++) {
					BargainAppOrgEntity bargainAppOrgEntity = bargainAppOrgEntities.get(i);
					String unifiedCode = bargainAppOrgEntity.getUnifiedCode();
					OrgAdministrativeInfoEntity orgAdministrativeInfoEntity = orgAdministrativeInfoService.
							queryOrgAdministrativeInfoByUnifiedCode(unifiedCode);
					if (orgAdministrativeInfoEntity == null) {
						continue;
					}
					String orgCode = orgAdministrativeInfoEntity.getCode();
					if (StringUtils.equals(requestEntity.getOriginalOrgCode(), orgCode)) {
						isDiscount = true;
					}
				}
			}
		}
		if (isDiscount) {
			return preferentialInfoDto;
		} else {
			return null;
		}
	}
	
	/**
	 * 费用明细特殊处理
	 */
	public void setSpecialHandlingBillCalculate(List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		//保价费置为0(对于一些费用的特殊处理（例如在开 3.60特惠件时，没有保价声明价值但是会算出保价费，FOSS是直接当有申明价值时再付值，但是悟空这边需要将计费明细传回去所以要将计费明细中的价格置为0）)
		if(null == escWayBillMiddleEntity.getInsuranceAmount() 
				|| escWayBillMiddleEntity.getInsuranceAmount().compareTo(BigDecimal.ZERO) <= 0){
			// 获取保价费
			GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos,
					PriceEntityConstants.PRICING_CODE_BF, null);
			if(null != insuranceCollection){
				insuranceCollection.setCaculateFee(BigDecimal.ZERO);
			}
		}
	}
	
	/**
	 * 签收回单价格明细
	 */
	public void setQsResultBillCalculate(ValueAddDto valueAddDto, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos){
		if(null != valueAddDto) {
			GuiResultBillCalculateDto resultBillCalculateDto =  new GuiResultBillCalculateDto();
			resultBillCalculateDto.setId(valueAddDto.getId());
			resultBillCalculateDto.setCaculateExpression(valueAddDto.getCaculateExpression());
			resultBillCalculateDto.setActualFeeRate(valueAddDto.getActualFeeRate());
			resultBillCalculateDto.setCaculateFee(valueAddDto.getCaculateFee());
			resultBillCalculateDto.setDiscountFee(valueAddDto.getDiscountFee());
			resultBillCalculateDto.setFee(valueAddDto.getFee());
			resultBillCalculateDto.setFeeRate(valueAddDto.getFeeRate());
			resultBillCalculateDto.setMinFee(valueAddDto.getMinFee());
			resultBillCalculateDto.setMaxFee(valueAddDto.getMaxFee());
			resultBillCalculateDto.setSubType(valueAddDto.getSubType());
			resultBillCalculateDto.setSubTypeName(valueAddDto.getSubTypeName());
			resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
			resultBillCalculateDto.setPriceEntryCode(valueAddDto.getPriceEntityCode());
			resultBillCalculateDto.setPriceEntryName(valueAddDto.getPriceEntityName());
			resultBillCalculateDto.setProductCode(valueAddDto.getProductCode());
			resultBillCalculateDto.setProductName(valueAddDto.getProductName());
			resultBillCalculateDto.setGoodsTypeCode(valueAddDto.getGoodsTypeCode());
			resultBillCalculateDto.setGoodsTypeName(valueAddDto.getGoodsTypeName());
			resultBillCalculateDto.setDefaultBF(valueAddDto.getDefaultBF());
			resultBillCalculateDto.setCandelete(valueAddDto.getCandelete());
			resultBillCalculateDto.setCanmodify(valueAddDto.getCanmodify());
			resultBillCalculateDto.setHeavyFeeRate(valueAddDto.getHeavyFeeRate());
			resultBillCalculateDto.setLightFeeRate(valueAddDto.getActualFeeRate());
			resultBillCalculateDto.setCaculateType(valueAddDto.getCaculateType());
			resultBillCalculateDto.setCentralizePickup(FossConstants.NO);
			if(CollectionUtils.isNotEmpty(valueAddDto.getDiscountPrograms())) {
				List<GuiResultDiscountDto> resultDiscountDtos = new ArrayList<GuiResultDiscountDto>();
				for (int k = 0; k < valueAddDto.getDiscountPrograms().size(); k++) {
					PriceDiscountDto discountDto =  valueAddDto.getDiscountPrograms().get(k);
					GuiResultDiscountDto guiResultDiscountDto = new GuiResultDiscountDto();
					guiResultDiscountDto.setDiscountRate(discountDto.getDiscountRate());
					guiResultDiscountDto.setMarketName(discountDto.getMarketName());
					guiResultDiscountDto.setReduceFee(discountDto.getReduceFee());
					guiResultDiscountDto.setPriceEntryCode(discountDto.getPriceEntryCode());
					guiResultDiscountDto.setPriceEntryName(discountDto.getPriceEntryName());
					guiResultDiscountDto.setSaleChannelCode(discountDto.getSaleChannelCode());
					guiResultDiscountDto.setSaleChannelName(discountDto.getSaleChannelName());
					guiResultDiscountDto.setDiscountId(discountDto.getDiscountId());
					guiResultDiscountDto.setDiscountType(discountDto.getType());
					guiResultDiscountDto.setDiscountTypeName(discountDto.getTypeName());
					guiResultDiscountDto.setChargeDetailId((discountDto.getChargeDetailId()));
					//添加推广活动折扣
					guiResultDiscountDto.setActiveCode(discountDto.getActiveCode());
					guiResultDiscountDto.setActiveName(discountDto.getActiveName());
					guiResultDiscountDto.setActiveStartTime(discountDto.getActiveStartTime());
					guiResultDiscountDto.setActiveEndTime(discountDto.getActiveEndTime());
					guiResultDiscountDto.setOptionsCrmId(discountDto.getOptionsCrmId());
					resultDiscountDtos.add(guiResultDiscountDto);
				}
				resultBillCalculateDto.setDiscountPrograms(resultDiscountDtos);
			}
			guiResultBillCalculateDtos.add(resultBillCalculateDto);
		}
	}
	
	/**
	 * 设置公布价集合
	 */
	public void setProductPriceDtoCollection(GuiResultBillCalculateDto dto, EscWayBillRequestEntity requestEntity,
			EscWayBillMiddleEntity escWayBillMiddleEntity,boolean accurateValuation){
		if (dto == null) {
			throw new WaybillValidateException("没有查询到对应的价格，请添加对应的价格");
		}
		if (dto.getCaculateFee() == null) {
			throw new WaybillValidateException("公布价运费为0，请检查价格是否存在！");
		}
		BigDecimal transportFee = dto.getCaculateFee();
		if(accurateValuation){
			transportFee = dto.getCaculateFee().setScale(2,BigDecimal.ROUND_HALF_UP);
		}else{
			transportFee = dto.getCaculateFee().setScale(0,BigDecimal.ROUND_HALF_UP); // 四舍五入
		}
		//公布价运费
		
		// 最低一票
		BigDecimal minTransportFee = dto.getMinFee();
				
		//给中间转化类 设置运费
		escWayBillMiddleEntity.setTransportFee(transportFee);
		escWayBillMiddleEntity.setMinTransportFee(minTransportFee);
		
		// 设置计费重量
		if (dto.getVolumeWeight() != null) {
			escWayBillMiddleEntity.setBillWeight(dto.getVolumeWeight()); // 设置计费重量
		} else {
			escWayBillMiddleEntity.setBillWeight(requestEntity.getWeight());
		}

		// 设置费率
		BigDecimal feeRate = BigDecimal.ZERO;
		if (dto.getActualFeeRate() != null) {
			feeRate = dto.getActualFeeRate().divide(new BigDecimal(NumberConstants.NUMBER_100), newScale, RoundingMode.HALF_EVEN);
		} else {
			//这边的费率和FOSS中比对过了，都是除以货物总重量
			if ((transportFee != null && escWayBillMiddleEntity.getBillWeight() != null && escWayBillMiddleEntity.getBillWeight().doubleValue() > 0)
					|| (transportFee != null && requestEntity.getWeight() != null && requestEntity.getWeight().doubleValue() > 0)) {
				feeRate = (transportFee.divide(requestEntity.getWeight(), newScale, RoundingMode.HALF_EVEN));
			}
		}
		//设置到中间转换表
		escWayBillMiddleEntity.setFeeRate(feeRate);
		
		// 设置公布价折扣优惠
		setDiscountPrograms(dto, escWayBillMiddleEntity);
	}
	
	/**
	 * 处理折扣明细
	 */
	public void setDiscountPrograms(GuiResultBillCalculateDto dto, EscWayBillMiddleEntity escWayBillMiddleEntity){
		//优惠信息
		List<GuiResultDiscountDto> discountPrograms = dto.getDiscountPrograms();
		
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			//折扣信息
			List<WaybillDiscountVo> data = escWayBillMiddleEntity.getWaybillDiscountVo();
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}
			
			// 获取保价申明价值
			BigDecimal insuranceAmount = escWayBillMiddleEntity.getInsuranceAmount() != null ? escWayBillMiddleEntity.getInsuranceAmount() : BigDecimal.ZERO;
			
			for (GuiResultDiscountDto discountDto : discountPrograms) {
				//报价费为0，优惠金额为0
				if (BigDecimal.ZERO.compareTo(insuranceAmount) == 0) {
					if(PriceEntityConstants.PRICING_CODE_BF.equals(discountDto.getPriceEntryCode())){
						discountDto.setReduceFee(BigDecimal.ZERO);
					}
				}
				
				resultDiscountadd(discountDto, data);
			}
			escWayBillMiddleEntity.setWaybillDiscountVo(data);
		}
	}
	
	/**
	 * 处理折扣优惠信息
	 */
	public static void resultDiscountadd(GuiResultDiscountDto dto, List<WaybillDiscountVo> data) {
		
		WaybillDiscountVo vo = new WaybillDiscountVo();
		// 折扣ID
		vo.setDiscountId(dto.getDiscountId());
		// 费用类型id
		vo.setChargeDetailId(dto.getChargeDetailId());
		//如果是其他费用，则显示子类型-----定价体系优化项目POP-423
		if(PriceEntityConstants.PRICING_CODE_QT.equals(dto.getPriceEntryCode())
				|| PriceEntityConstants.PRICING_CODE_BZ.equals(dto.getPriceEntryCode())){
			// 优惠折扣项目
			vo.setFavorableItemName(dto.getSubTypeName());
			// 优惠项目CODE
			vo.setFavorableItemCode(dto.getSubType());
		}else{
			// 优惠折扣项目
			vo.setFavorableItemName(dto.getPriceEntryName());
			// 优惠项目CODE
			vo.setFavorableItemCode(dto.getPriceEntryCode());
		}
		// 优惠折扣类型
		vo.setFavorableTypeName(dto.getDiscountTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getDiscountType());
		// 优惠折扣子类型
		vo.setFavorableSubTypeName(dto.getSaleChannelName());
		// 优惠折扣子类型
		vo.setFavorableSubTypeCode(dto.getSaleChannelCode());
		//营销活动编码
		vo.setActiveCode(dto.getActiveCode());
		//营销活动名称
		vo.setActiveName(dto.getActiveName());
		//营销活动开始时间
		vo.setActiveStartTime(dto.getActiveStartTime());
		//营销活动结束时间
		vo.setActiveEndTime(dto.getActiveEndTime());
		//营销活动折扣对应的CRM_ID
		vo.setOptionsCrmId(dto.getOptionsCrmId());
		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount("无折扣费率，可能是数据有异常，请联系管理员");
		}
		if (dto.getRenewalDiscountRate()!= null) {
			//设置快递续重折扣率
			vo.setContinueFavorableDiscount(dto.getRenewalDiscountRate().toString());
		} 
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			   vo.setFavorableAmount("无金额减免，可能是数据有异常，请联系管理员");
		}
		data.add(vo);
	}
	
	/**
	 * 保存返回单折扣
	 */
	public void setReturnBillDiscount(List<PriceDiscountDto> discountPrograms, EscWayBillMiddleEntity escWayBillMiddleEntity){
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			List<WaybillDiscountVo> data = escWayBillMiddleEntity.getWaybillDiscountVo(); 
			if(null == data){
				data = new ArrayList<WaybillDiscountVo>();
			}
			for (PriceDiscountDto dto : discountPrograms) {
				setPriceDiscount(dto, data);
			}
			//存入中间转化类
			escWayBillMiddleEntity.setWaybillDiscountVo(data);
		}
	}
	
	/**
	 * 设置返回单折扣信息
	 */
	public void setPriceDiscount(PriceDiscountDto dto, List<WaybillDiscountVo> data){
		WaybillDiscountVo vo = new WaybillDiscountVo();
		// 折扣ID
		vo.setDiscountId(dto.getDiscountId());
		// 费用类型id
		vo.setChargeDetailId(dto.getChargeDetailId());
		// 优惠折扣项目
		vo.setFavorableItemName(dto.getPriceEntryName());
		// 优惠项目CODE
		vo.setFavorableItemCode(dto.getPriceEntryCode());
		// 优惠折扣类型
		vo.setFavorableTypeName(dto.getTypeName());
		// 优惠折扣类型
		vo.setFavorableTypeCode(dto.getType());
		// 优惠折扣子类型
		vo.setFavorableSubTypeName(dto.getSaleChannelName());
		// 优惠折扣子类型
		vo.setFavorableSubTypeCode(dto.getSaleChannelCode());
		//营销活动编码
		vo.setActiveCode(dto.getActiveCode());
		//营销活动名称
		vo.setActiveName(dto.getActiveName());
		//营销活动开始时间
		vo.setActiveStartTime(dto.getActiveStartTime());
		//营销活动结束时间
		vo.setActiveEndTime(dto.getActiveEndTime());
		//营销活动折扣对应的CRM_ID
		vo.setOptionsCrmId(dto.getOptionsCrmId());
		if (dto.getDiscountRate() != null) {
			// 优惠折扣率
			vo.setFavorableDiscount(dto.getDiscountRate().toString());
		} else {
			// 优惠折扣率
			vo.setFavorableDiscount("无折扣费率，可能是数据有异常，请联系管理员");
		}
		// 优惠折扣金额
		if (dto.getReduceFee() != null) {
			//优惠金额
			vo.setFavorableAmount(dto.getReduceFee().toString());			
		} else {
			vo.setFavorableAmount("无金额减免，可能是数据有异常，请联系管理员");
		}
		data.add(vo);
	}
	
	/**
	 * 计算总运费
	 */
	public void calculateTotalFee(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity, 
			boolean needMinusCoupen){
		//公布价运费
		BigDecimal transportFee=escWayBillMiddleEntity.getTransportFee();
		//增值费
		BigDecimal incrementFee=escWayBillMiddleEntity.getValueAddFee();
		
		/**
		 * 判断公布价运费是否为空，如果为空，则设置为0
		 */
		if(transportFee==null){
			transportFee = BigDecimal.ZERO;
		}
		/**
		 * 判断增值费用是否为空，如果为空，则设置为0
		 */
		if(incrementFee==null){
			incrementFee = BigDecimal.ZERO;
		}
		
		/**
		 * 从请求实体中取出代收货款金额
		 */
		BigDecimal codAmout = escWayBillMiddleEntity.getCodAmount();
		if(codAmout==null){
			codAmout = BigDecimal.ZERO;
		}
		
		/**
		 * 最低一票(快递不计算最低一票)
		 */
		
		/**
		 * 优惠费用,在未统计优惠信息时  escWayBillMiddleEntity.getCouponFree() 是空的，不需要冲减优惠信息
		 */
		BigDecimal couponFree = escWayBillMiddleEntity.getCouponFree();
		
		/**
		 * 若冲减类型为运费时，才可以对运费进行冲减
		 * MANA-1961 营销活动与优惠券编码关联
		 * 2014-04-10 026123
		 */
		if(couponFree!=null && PriceEntityConstants.PRICING_CODE_FRT.equals(escWayBillMiddleEntity.getCouponRankType())){
			couponFree = couponFree.setScale(0, BigDecimal.ROUND_HALF_UP);// 四舍五入
		}else{
			couponFree=BigDecimal.ZERO;
		}
		
		/**
		 * 公布价运费需要减去优惠费用
		 */
		if(needMinusCoupen && !FossConstants.YES.equals(escWayBillMiddleEntity.getFlagTakeCoupon())){
			transportFee=transportFee.subtract(couponFree);		
		}
		
		if(transportFee!=null && transportFee.doubleValue()<0){
			//小件只能把运费冲减到零			
			//Online-定价体系优化项目DJTWO-175-DJTWO-251
			if(needMinusCoupen && !FossConstants.YES.equals(escWayBillMiddleEntity.getFlagTakeCoupon())){
				transportFee = BigDecimal.ZERO;
			}
		}

		/**
		 * 不是内部发货
		 */
		if(StringUtil.isBlank(requestEntity.getInternalDeliveryType())
				|| StringUtil.isBlank(requestEntity.getEmployeeNo()))
		{				
			if(transportFee != null){
				//RFOSS2015052801（DN201505260016）  取消优惠券最低一票校验  --206860
				if(needMinusCoupen && !FossConstants.YES.equals(escWayBillMiddleEntity.getFlagTakeCoupon())){
					//当运费小于0，将运费修改为0
					if(transportFee.compareTo(BigDecimal.ZERO) < 0){
						transportFee = BigDecimal.ZERO;
					}
				}
			 }
		}
				
		//公布运价费用重新赋值给中间转化类
		escWayBillMiddleEntity.setTransportFee(transportFee);
		
		/**
		 * 总运费=公布价运费+增值服务费
		 */
		if(transportFee == null){
			transportFee = BigDecimal.ZERO;
		}
		BigDecimal totalFee = transportFee.add(incrementFee);
		
		if(requestEntity.getPaidMethod() != null){
			if (PricingConstants.ARRIVE_PAYMENT.equals(requestEntity.getPaidMethod())) {
				totalFee = totalFee.add(codAmout);
				// 总费用
				escWayBillMiddleEntity.setTotalFee(totalFee);
				// 到付金额
				escWayBillMiddleEntity.setToPayFee(totalFee);
				// 预付金额
				escWayBillMiddleEntity.setPrePayFee(BigDecimal.ZERO);
			} else {
				// 预付金额
				escWayBillMiddleEntity.setPrePayFee(totalFee);
				//到付金额
				escWayBillMiddleEntity.setToPayFee(codAmout);
				// 总金额加上代收货款
				totalFee = totalFee.add(codAmout);
				// 总费用
				escWayBillMiddleEntity.setTotalFee(totalFee);
			}
		}else{
			// 预付金额
			escWayBillMiddleEntity.setToPayFee(totalFee);
			//到付金额
			escWayBillMiddleEntity.setToPayFee(codAmout);
			// 总金额加上代收货款
			totalFee = totalFee.add(codAmout);
			// 总费用
			escWayBillMiddleEntity.setTotalFee(totalFee);
		}
	}
	
	/**
	 * 计算快递增值服务
	 * 葛亮亮 2016年5月9日 17:05:27
	 */
	public void calculateValueAdd(EscWayBillRequestEntity requestEntity, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos, 
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		// 将代收货款费率转换成千分率的格式
		BigDecimal permillage = new BigDecimal(PricingConstants.PERMILLAGE);
		
		//包装费(快递没有包装费)，如果在页面填写包装费后需要取出来进行累加
		BigDecimal packageFee = escWayBillMiddleEntity.getPackageFee() != null ? escWayBillMiddleEntity.getPackageFee() : BigDecimal.ZERO;
		
		// 获取保价费
		GuiResultBillCalculateDto insuranceCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_BF, null);
		
		//保险声明价值
		BigDecimal insuranceAmount = escWayBillMiddleEntity.getInsuranceAmount() != null ? escWayBillMiddleEntity.getInsuranceAmount() : BigDecimal.ZERO;
		if(insuranceAmount.compareTo(BigDecimal.ZERO) > 0 
				&& null == insuranceCollection){
			throw new WaybillValidateException("未查到对应的保险手续费！");
		}
		
		BigDecimal insuranceFee = BigDecimal.ZERO;
		if(null != insuranceCollection && null != insuranceCollection.getCaculateFee()){
			insuranceFee = insuranceCollection.getCaculateFee().setScale(0,BigDecimal.ROUND_HALF_UP);
			//处理保价费优惠费
			setDiscountPrograms(insuranceCollection, escWayBillMiddleEntity);
		}
		escWayBillMiddleEntity.setInsuranceFee(insuranceFee);
		
		// 获取代收货款手续费
		GuiResultBillCalculateDto codCollection = getGuiResultBillCalculateDto(guiResultBillCalculateDtos,
				PriceEntityConstants.PRICING_CODE_HK, null);
		
		//代收货款金额 
		BigDecimal codAmount = escWayBillMiddleEntity.getCodAmount() != null ? escWayBillMiddleEntity.getCodAmount() : BigDecimal.ZERO;
		//防止页面填写的代收货款金额超过配置的代收货款上限，返回代收货款为空
		if(codAmount.compareTo(BigDecimal.ZERO) > 0 
				&& null == codCollection){
			throw new WaybillValidateException("未查到对应的代收货款手续费！");
		}
		
		BigDecimal codFee = BigDecimal.ZERO;
		if(null != codCollection && null != codCollection.getCaculateFee()){
			//处理代收货款优惠费
			setDiscountPrograms(codCollection, escWayBillMiddleEntity);
			
			//如果代收货款为0 则代收货款手续费为0
			if(codAmount.compareTo(BigDecimal.ZERO) > 0){
				//代收货款费率
				BigDecimal feeRate = BigDecimal.ZERO;
				if (codCollection.getCaculateFee() != null) {
					feeRate = codCollection.getCaculateFee().divide(codAmount, NumberConstants.NUMBER_5, BigDecimal.ROUND_HALF_UP);
					feeRate = feeRate.multiply(permillage);				
				} else {
					feeRate = codCollection.getActualFeeRate() != null ? codCollection.getActualFeeRate() : BigDecimal.ZERO;
					feeRate = feeRate.multiply(permillage);
				}
							
				//计算后的代收货款手续费
				codFee = codCollection.getCaculateFee().setScale(0,BigDecimal.ROUND_HALF_UP);// 四舍五入
				
				/**
				 * @author yangkang DMANA-3793 快递代收货款最低一票 与客户合同中读取代收货款手续费最低一票的值比较确定值
				 * 当且仅当当前代收货款手续费等于或者小于增值服务中的最低一票时
				 */
				if (codFee.compareTo(codCollection.getMinFee()) <= 0) {
					// 根据客户编码和产品类型查询客户优惠信息
					PreferentialInfoDto entityInfo = preferentialService.
							queryPreferentialInfo(requestEntity.getCustomerCode(), null,ProductEntityConstants.PRICING_PRODUCT_C2_C20005);				
					// 实际代收货款手续费,可能小于增值服务中配置的最低一票
					BigDecimal actualCodFee = codAmount.multiply(feeRate.divide(permillage)).setScale(0,BigDecimal.ROUND_HALF_UP);
	
					if (entityInfo != null
							&& entityInfo.getLowestCharge() != null
							&& entityInfo.getLowestCharge().compareTo(
									BigDecimal.ZERO) >= 0) {
						// 当客户合同中的代收货款最低手续费值小于增值服务中配置的最低一票时，取客户合同中的最低手续费的值
						if (entityInfo.getLowestCharge().compareTo(codCollection.getMinFee()) < 0) {
							// 设置代收货款手续费为客户合同中的值
							codFee = entityInfo.getLowestCharge();
							// 用客户合同中配置的代收汇款手续费与实际代收货款手续费作比较 取其中的大值
							if (codFee.compareTo(actualCodFee) < 0) {
								codFee = actualCodFee;
							}
						}
					}
				}
				//重新计算费率
				feeRate = codFee.divide(codAmount, NumberConstants.NUMBER_5, BigDecimal.ROUND_HALF_UP);
				//将最终的代收货款手续费重新放到明细中
				codCollection.setCaculateFee(codFee);
				//费率
				codCollection.setActualFeeRate(feeRate);
			}
		}		
		escWayBillMiddleEntity.setCodFee(codFee);
		
		//接货费(快递没有接货费)
		
		//送货费(快递没有送货费)，如果页面填写了送货费需要取出来进行累加
		BigDecimal deliveryGoodsFee = escWayBillMiddleEntity.getDeliveryGoodsFee() != null ? escWayBillMiddleEntity.getDeliveryGoodsFee() : BigDecimal.ZERO;
		
		//返单折扣（处理"客户签收单传真返回"费用添加到其他费）
		setReturnBillCollection(requestEntity, escWayBillMiddleEntity, guiResultBillCalculateDtos);

		//其他费用合计
		BigDecimal otherFee = escWayBillMiddleEntity.getOtherFee() != null ? escWayBillMiddleEntity.getOtherFee() : BigDecimal.ZERO;
		escWayBillMiddleEntity.setOtherFee(otherFee);
				
		// 增值服务费
		BigDecimal incrementFee = BigDecimal.ZERO;
		incrementFee = packageFee.add(insuranceFee).add(codFee).add(deliveryGoodsFee).add(otherFee);
		escWayBillMiddleEntity.setIncrementFee(incrementFee);
		
		//将增值服务费存入返回实体
		escWayBillMiddleEntity.setValueAddFee(incrementFee);
		
		// 计算装卸费
		calculateServiceFee(requestEntity, escWayBillMiddleEntity);
		
		// 添加其他费用 折扣
		setOtherChargeDataCollection(requestEntity, escWayBillMiddleEntity, guiResultBillCalculateDtos);
	}
	
	/**
	 * 返单折扣（处理"客户签收单传真返回"费用添加到其他费）
	 */
	public void setReturnBillCollection(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity, List<GuiResultBillCalculateDto> guiResultBillCalculateDtos){
		
		//传真返回(查询 "客户签收单传真返回" 签单费用需要用到“长短途”属性，但是悟空不能提供，后来看生产上面用这块配置的都是ALL，所以在生产上面是没有问题，在项目环境只需要属性改成ALL就行)
		if (!PricingConstants.NOT_RETURN_BILL.equals(escWayBillMiddleEntity.getQsSubType())
				&& PricingConstants.RETURNBILLTYPE_FAX.equals(escWayBillMiddleEntity.getQsSubType())) {
			//获取签收费
			List<ValueAddDto> list = billCaculateService.searchExpressValueAddPriceList(
					getQueryOtherChargeParam(requestEntity, escWayBillMiddleEntity));
			
			if(null == list){
				throw new WaybillValidateException("未查询到返单费用！");
			}else
			{
				ValueAddDto dto = list.get(0);
				//签收固定费用
				BigDecimal czQsFee = dto.getFee() != null ? dto.getFee().setScale(0, BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
				//处理其他费用中是否已经含有此子类
				setOtherFee(czQsFee, escWayBillMiddleEntity);

				//重新累加其他费用
				setTotalOtherFee(escWayBillMiddleEntity);
				
				// 折扣优惠
				if (CollectionUtils.isNotEmpty(list)) {
					for (ValueAddDto addDto : list) {
						if (PriceEntityConstants.PRICING_CODE_QS.equals(addDto.getPriceEntityCode())) {
							//将签收回单的明细存入价格明细中
							setQsResultBillCalculate(addDto, guiResultBillCalculateDtos);
							//防止折扣为空的情况
							if(CollectionUtils.isNotEmpty(addDto.getDiscountPrograms())){
								setReturnBillDiscount(addDto.getDiscountPrograms(), escWayBillMiddleEntity);
							}
						}
					}
				}
			}
		}else{//将 原件签收单返回从其他费用中移除
			//这里只删除 传真的， 原件返回的在前面处理
			delOtherFee(escWayBillMiddleEntity, PricingConstants.RETURNBILLTYPE_FAX);

			//重新累加其他费用
			setTotalOtherFee(escWayBillMiddleEntity);
		}
	}
	
	/**
	 * 从其他费用明细中删除制定的子类
	 */
	public void delOtherFee(EscWayBillMiddleEntity escWayBillMiddleEntity, String subStype){
		for(EscValuationEntryDto escValuationEntryDto : escWayBillMiddleEntity.getOtherChargeVos()){
			//如果有制定剔除的子类则直接剔除
			if(subStype.equals(escValuationEntryDto.getSubType())){
				escWayBillMiddleEntity.getOtherChargeVos().remove(escValuationEntryDto);
				break;
			}
		}
	}
	
	/**
	 * 其他费用中是否已经有需要累加的项，如果有在改变值后累加，没有在添加后累加
	 */
	public void setOtherFee(BigDecimal fee, EscWayBillMiddleEntity escWayBillMiddleEntity){
		//是否存在添加其他项
		Boolean isExist = false;
		for(EscValuationEntryDto escValuationEntryDto : escWayBillMiddleEntity.getOtherChargeVos()){
			if(escWayBillMiddleEntity.getQsSubType().equals(escValuationEntryDto.getSubType())){
				//存在重新赋值
				escValuationEntryDto.setOriginnalCost(fee);
				isExist = true;
			}
		}
		
		//不存在进行添加
		if(!isExist){
			EscValuationEntryDto valuationEntryDto = new EscValuationEntryDto();
			valuationEntryDto.setPriceEntityCode(PriceEntityConstants.PRICING_CODE_QT); //费用类型代码(其他)
			valuationEntryDto.setOriginnalCost(fee); //费用
			valuationEntryDto.setSubType(escWayBillMiddleEntity.getQsSubType()); //签收的类别
			//添加到LIST
			escWayBillMiddleEntity.getOtherChargeVos().add(valuationEntryDto);
		}
	}
	
	/**
	 * 重新累加其他费用
	 */
	public void setTotalOtherFee(EscWayBillMiddleEntity escWayBillMiddleEntity){
		//其他费用
		BigDecimal otherFee = BigDecimal.ZERO;
		for(EscValuationEntryDto escValuationEntryDto : escWayBillMiddleEntity.getOtherChargeVos()){
			//累加
			otherFee = otherFee.add(escValuationEntryDto.getOriginnalCost());
		}
		//重新设置其他费用
		escWayBillMiddleEntity.setOtherFee(otherFee);
	}
	
	/**
	 * 查询其他费用折扣
	 */
	private void setOtherChargeDataCollection(EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity,
			List<GuiResultBillCalculateDto> dtos) {
		/* 判断是否有综合信息费与快递包装费 */
		boolean flagZhxx = false;
		boolean flagKdbz = false;
		for(EscValuationEntryDto escEntry : requestEntity.getPriceEntities()){
			if(PriceEntityConstants.PRICING_CODE_QT.equals(escEntry.getPriceEntityCode())){
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(escEntry.getSubType())){
					flagZhxx = true;
				}
				
				if (PriceEntityConstants.PRICING_CODE_KDBZF.equals(escEntry.getSubType())){
					flagKdbz = true;
				}		
			}
		}

		if (dtos != null && !dtos.isEmpty()) {
			// 设置其他费用的折扣优惠
			for (GuiResultBillCalculateDto valueAddDto : dtos) {
				// 是否为其他费用
				if (PriceEntityConstants.PRICING_CODE_QT.equals(valueAddDto
						.getPriceEntryCode())) {
					if (PriceEntityConstants.PRICING_CODE_ZHXX
							.equals(valueAddDto.getSubType())) {
						if (flagZhxx) {
							setFavorableDiscount(valueAddDto.getDiscountPrograms(),
									requestEntity, escWayBillMiddleEntity);
						}
					} else if (PriceEntityConstants.PRICING_CODE_KDBZF
							.equals(valueAddDto.getSubType())) {
						if (flagKdbz) {
							setFavorableDiscount(valueAddDto.getDiscountPrograms(),
									requestEntity, escWayBillMiddleEntity);
						}
					} else {
						setFavorableDiscount(valueAddDto.getDiscountPrograms(), 
								requestEntity, escWayBillMiddleEntity);
					}
				}
			}
		}
	}

	/**
	 * 设置折扣优惠
	 */
	public void setFavorableDiscount(List<GuiResultDiscountDto> discountPrograms, 
			EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity) {
		if (discountPrograms != null && !discountPrograms.isEmpty()) {
			//中间转换表中存放的折扣信息
			List<WaybillDiscountVo> data = escWayBillMiddleEntity.getWaybillDiscountVo(); 
			
			if (data == null) {
				data = new ArrayList<WaybillDiscountVo>();
			}

			for (GuiResultDiscountDto dto : discountPrograms) {		
				//报价费为0，优惠金额为0（保价声明价值在前面设置 公布价格集合的时候已经添加）
				if ("0".equals(escWayBillMiddleEntity.getInsuranceAmount()) || (BigDecimal.ZERO.compareTo(escWayBillMiddleEntity.getInsuranceAmount()) == 0)) {
					if(PriceEntityConstants.PRICING_CODE_BF.equals(dto.getPriceEntryCode())){
						dto.setReduceFee(BigDecimal.ZERO);
					}
				}
				
				resultDiscountadd(dto, data);
			}
			escWayBillMiddleEntity.setWaybillDiscountVo(data);
		}
	}
	
	/**
	 * 计算装卸费
	 */
	private void calculateServiceFee(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		//根据页面传递是否需要计算装卸费
		if(!requestEntity.getIsCalculateServicefee()){
			//计算装卸费
			setServiceChargeState(requestEntity, escWayBillMiddleEntity);
		}
		//装卸费规则校验
		validataServiceFee(requestEntity, escWayBillMiddleEntity);
		
		// 获取装卸费
		BigDecimal serivceFee = escWayBillMiddleEntity.getServicefee();
		if (serivceFee != null && serivceFee.longValue() != 0) {
			// 获取运费
			BigDecimal transportFee = escWayBillMiddleEntity.getTransportFee();
			transportFee = transportFee.add(escWayBillMiddleEntity.getServicefee());
			// 设置新的运费
			escWayBillMiddleEntity.setTransportFee(transportFee);
			// 费率 = 最新运费（运费+装卸费）/计费重量
			BigDecimal feeRate = transportFee.divide(escWayBillMiddleEntity.getBillWeight(), 2, BigDecimal.ROUND_HALF_DOWN);			
			//设置新费率
			escWayBillMiddleEntity.setFeeRate(feeRate);
		}
	}
	
	/**
	 * 验证装卸费规则
	 */
	private void validataServiceFee(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		//获取用于前台显示装卸费
		BigDecimal serivceFee = escWayBillMiddleEntity.getServicefee();

		if (serivceFee == null || serivceFee.longValue() == 0) {
			return;
		}

		if (serivceFee.longValue() < 0) {
			throw new WaybillValidateException("输入的快递装卸费不能为负数，请重新输入！");
		}

		BigDecimal transportFee = escWayBillMiddleEntity.getTransportFee();

		// 输入费用不得高于公布价运费*快递装卸费费率的积截断小数点后的值
		BigDecimal serviceFee2 = null;
		CustomerEntity custinfo = customerService.queryCustomerInfoByCusCode(requestEntity.getCustomerCode());
		if(custinfo!=null&&StringUtils.isNotBlank(custinfo.getSetProportion())){
			BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
			serviceFee2 = (transportFee.multiply(setProportion)).setScale(0,BigDecimal.ROUND_DOWN);
		}else{
			serviceFee2 = (transportFee
				.multiply(getServiceFeeRate(requestEntity, escWayBillMiddleEntity))).setScale(0,
				BigDecimal.ROUND_DOWN);
		}
		if (serivceFee.compareTo(serviceFee2) > 0) {
			throw new WaybillValidateException("输入的快递装卸费超过限制，请重新输入！");
		}
	}
	
	/**
	 * 获取装卸费费率
	 */
	private BigDecimal getServiceFeeRate(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		BigDecimal serviceFeeRate = BigDecimal.ZERO;
		// 调用接口读取装卸费费率
		ConfigurationParamsEntity paramsEntity = configurationParamsService.queryConfigurationParamsByOrgCode(DictionaryConstants.SYSTEM_CONFIG_PARM__STL,
				"STL_SERVICE_EXPRESS_FEE_RATIO",requestEntity.getOriginalOrgCode());
		
		if (paramsEntity == null) {
			throw new WaybillValidateException("当前部门不存在装卸费比率，无法进行装卸费计算");
		}else
		{
			// 判断是否存在装卸费费率
			if (paramsEntity.getConfValue() == null) {
				throw new WaybillValidateException("当前部门不存在装卸费比率，无法进行装卸费计算");
			} else {
				serviceFeeRate = new BigDecimal(paramsEntity.getConfValue());
			}
		}
		return serviceFeeRate;
	}
	
	
	/**
	 * 计算装卸费
	 */
	private void setServiceChargeState(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity) {
		/**
		 * 9. 2012年12月1日 (以后)开业的部门不能开装卸费 10. 如果订单渠道为阿里巴巴或者是月发越送属性客户不可开装卸费
		 * 11.是否可以开装卸费的依据取决于部门的业务属性（ 即部门属性基础资料中增加是否可开装卸费的字段）。
		 * 12.装卸费上限由增值服务配置基础资料实现 （在产品API中体现）。
		 */
		boolean serviceChargeEnabled = true;
		
		// 快递只允许对应部门的月结客户开装卸费
		CusBargainVo vo = new CusBargainVo();
		vo.setExpayway(PricingConstants.MONTH_END);
		vo.setCustomerCode(requestEntity.getCustomerCode());
		vo.setBillDate(new Date());
		vo.setBillOrgCode(requestEntity.getOriginalOrgCode());
		boolean isOk = false;
		CusBargainEntity cusBargain  = cusBargainService.queryCusBargainByVoExp(vo);
		if(null != cusBargain){
			isOk = true;
		}
		//先使用请求类中传递过来的装卸费填充
		escWayBillMiddleEntity.setServicefee(requestEntity.getServicefee());
		//先使用请求类中传递过来的填充保存数据库
		escWayBillMiddleEntity.setDcServicefee(requestEntity.getServicefee());
		//是否在前台显示（先设置为可编辑）
		escWayBillMiddleEntity.setServiceChargeFlag(serviceChargeEnabled);
		if (!isOk) {
			CustomerEntity custinfo = customerService.queryCustomerInfoByCusCode(requestEntity.getCustomerCode());
			if(custinfo!=null&&StringUtils.isNotBlank(custinfo.getSetProportion())){
				BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
				BigDecimal servicefee = escWayBillMiddleEntity.getTransportFee().multiply(setProportion)
						.setScale(0, BigDecimal.ROUND_DOWN);
				if(escWayBillMiddleEntity.getServicefee() == null|| escWayBillMiddleEntity.getServicefee().compareTo(BigDecimal.ZERO) == 0 ){
					//加收方式
					if("CHARGETYPE".equals(custinfo.getExpHandChargeBusiType())){
						escWayBillMiddleEntity.setServicefee(servicefee);
						escWayBillMiddleEntity.setDcServicefee(servicefee);
						serviceChargeEnabled = true;
					//折让方式,数据不展示在前台界面 ，存在数据库另一个字段
					}else if("DISCOUNTTYPE".equals(custinfo.getExpHandChargeBusiType())){
						servicefee = escWayBillMiddleEntity.getTransportFee().multiply(setProportion)
								.setScale(1, BigDecimal.ROUND_DOWN);
						escWayBillMiddleEntity.setServicefee(BigDecimal.ZERO);
						escWayBillMiddleEntity.setDcServicefee(servicefee);
						//装卸费需要存到数据库,给结算用
						serviceChargeEnabled = false;
					}
				}
			}else{
				if(escWayBillMiddleEntity.getServicefee()==null||escWayBillMiddleEntity.getServicefee().compareTo(BigDecimal.ZERO) == 0 ){
					escWayBillMiddleEntity.setServicefee(BigDecimal.ZERO);
					escWayBillMiddleEntity.setDcServicefee(BigDecimal.ZERO);
				}
				serviceChargeEnabled = false;
			}
		} else {
			//根据客户编码查询到CRM中配置的客户装卸费业务方式和比例值
			CustomerEntity custinfo = customerService.queryCustomerInfoByCusCode(requestEntity.getCustomerCode());
			if(custinfo!=null){
				if(StringUtils.isNotBlank(custinfo.getSetProportion())){
					//如果为加收方式，则自动计算装卸费，并展示在前台页面。若为折让方式自动计算不在前台展示
				    //加收方式
					if(StringUtils.isNotEmpty(custinfo.getSetProportion())&&
							"CHARGETYPE".equals(custinfo.getExpHandChargeBusiType())){
						BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
						BigDecimal servicefee = escWayBillMiddleEntity.getTransportFee().multiply(setProportion).setScale(0, BigDecimal.ROUND_DOWN);
						if(escWayBillMiddleEntity.getServicefee() == null||escWayBillMiddleEntity.getServicefee().compareTo(BigDecimal.ZERO) == 0 ){
							escWayBillMiddleEntity.setServicefee(servicefee);
							escWayBillMiddleEntity.setDcServicefee(servicefee);
						}
						serviceChargeEnabled = true;
					//折让方式,数据不展示在前台界面 ，存在数据库另一个字段
					}else if(StringUtils.isNotBlank(custinfo.getSetProportion())&&
							"DISCOUNTTYPE".equals(custinfo.getExpHandChargeBusiType())){
						BigDecimal setProportion = new BigDecimal(custinfo.getSetProportion());
						BigDecimal servicefee = escWayBillMiddleEntity.getTransportFee().multiply(setProportion).
								setScale(1, BigDecimal.ROUND_DOWN);
						if(escWayBillMiddleEntity.getServicefee()==null||escWayBillMiddleEntity.getServicefee().compareTo(BigDecimal.ZERO) == 0 ){
							escWayBillMiddleEntity.setServicefee(BigDecimal.ZERO);
							escWayBillMiddleEntity.setDcServicefee(servicefee);
						}
						serviceChargeEnabled = false;
				   }
			  }else{
				  //如果crm中没有配置费率，若为月结客户，则读取foss中的费率手动计算，不做逻辑处理，设置为可编辑。
				  serviceChargeEnabled = true;
			  }
	      }
	   }
	   //是否在前台显示
	   escWayBillMiddleEntity.setServiceChargeFlag(serviceChargeEnabled);
	}
	
	/**
	 * 获取指定费用明细
	 * 葛亮亮 2016年5月9日 15:26:19	
	 */
	private GuiResultBillCalculateDto getGuiResultBillCalculateDto(
			List<GuiResultBillCalculateDto> dtos, String valueAddType,
			String subType) {

		for (GuiResultBillCalculateDto guiResultBillCalculateDto : dtos) {
			if (subType == null) {
				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())) {
					return guiResultBillCalculateDto;
				}
			} else {
				if (valueAddType.equals(guiResultBillCalculateDto
						.getPriceEntryCode())
						&& subType.equals(guiResultBillCalculateDto
								.getSubType())) {
					return guiResultBillCalculateDto;
				}
			}

		}

		return null;
	}
	
	/**
	 * 签收单返单,查询增值服務实体
	 */
	public QueryBillCacilateValueAddDto getQueryOtherChargeParam(EscWayBillRequestEntity requestEntity,
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		QueryBillCacilateValueAddDto queryDto = new QueryBillCacilateValueAddDto();
		queryDto.setOriginalOrgCode(requestEntity.getOriginalOrgCode());// 出发部门CODE
		queryDto.setDestinationOrgCode(requestEntity.getDestinationOrgCode());// 到达部门CODE
		queryDto.setProductCode(requestEntity.getProductCode());// 产品CODE
		queryDto.setGoodsTypeCode(null);// 货物类型CODE
		queryDto.setReceiveDate(requestEntity.getBillDate());// 营业部收货日期（可选，无则表示当前日期）
		queryDto.setCustomerCode(requestEntity.getCustomerCode());
		
		//设置 重量
		queryDto.setWeight(null != requestEntity.getWeight() ? requestEntity.getWeight() : BigDecimal.ZERO);
		//体积
		queryDto.setVolume(null != requestEntity.getVolume() ? requestEntity.getVolume() : BigDecimal.ZERO);
		queryDto.setOriginnalCost(BigDecimal.ZERO);// 原始费用
		queryDto.setFlightShift(null);// 航班号
		queryDto.setLongOrShort(null);// 长途 还是短途
		// 为费用类型名称（综合信息费，燃油附加费，中转费等）
		queryDto.setSubType(escWayBillMiddleEntity.getQsSubType()); 
		queryDto.setCurrencyCdoe(FossConstants.CURRENCY_CODE_RMB);// 币种
		if (PricingConstants.NOT_RETURN_BILL.equals(escWayBillMiddleEntity.getQsSubType())) {// 判断有无返单类型
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QT);// 计价条目CODE
																				// 其他费用
		} else {
			queryDto.setPricingEntryCode(PriceEntityConstants.PRICING_CODE_QS);// 计价条目CODE
																				// 签收回单
		}
		queryDto.setPricingEntryName(null);// 计价名称
		return queryDto;
	}
	
	/**
	 * 推广活动会对其他费用打折，重新在其他费用面板赋值
	 */
	public void setterOtherFeeByMakActive(List<GuiResultBillCalculateDto> guiResultBillCalculateDtos,
			EscWayBillRequestEntity requestEntity, EscWayBillMiddleEntity escWayBillMiddleEntity){
		// 判断是否参加了推广活动
		if (null != requestEntity.getActiveCode()) {
			
			// 推广活动折扣后的综合信息费
			BigDecimal zhxxDis = null;
			// 推广活动折扣后的快递包装费
			BigDecimal kdbzDis = null;
			// 综合信息费
			for (GuiResultBillCalculateDto guiResultBillCalculateDto : guiResultBillCalculateDtos) {
				if (PriceEntityConstants.PRICING_CODE_QT.equals(guiResultBillCalculateDto.getPriceEntryCode())
					&& (PriceEntityConstants.PRICING_CODE_ZHXX.equals(guiResultBillCalculateDto.getSubType()) 
						|| PriceEntityConstants.PRICING_CODE_KDBZF.equals(guiResultBillCalculateDto.getSubType()))) {
					// 获取折扣信息
					List<GuiResultDiscountDto> disList = guiResultBillCalculateDto.getDiscountPrograms();
					if (CollectionUtils.isNotEmpty(disList)) {
						for (GuiResultDiscountDto dto : disList) {
							if (dto != null
								&& DiscountTypeConstants.DISCOUNT_TYPE__ACTIVE.equals(dto.getDiscountType())) {
								if (PriceEntityConstants.PRICING_CODE_ZHXX.equals(guiResultBillCalculateDto.getSubType())) {
									// 获取折扣后的综合信息费四舍五入取整
									zhxxDis = guiResultBillCalculateDto.getCaculateFee().setScale(0,BigDecimal.ROUND_HALF_UP);
								}
								if (PriceEntityConstants.PRICING_CODE_KDBZF.equals(guiResultBillCalculateDto.getSubType())) {
									// 获取折扣后的快递包装费四舍五入取整
									kdbzDis = guiResultBillCalculateDto.getCaculateFee().setScale(0,BigDecimal.ROUND_HALF_UP);
								}
							}
						}
					}
				}
			}
			
			BigDecimal otherCharge = BigDecimal.ZERO;
			//打折其他费用总和计算
			for(EscValuationEntryDto escEntry : escWayBillMiddleEntity.getOtherChargeVos()){
				if(PriceEntityConstants.PRICING_CODE_ZHXX.equals(escEntry.getSubType())){
					escEntry.setOriginnalCost(zhxxDis);
				}
				
				if (PriceEntityConstants.PRICING_CODE_KDBZF.equals(escEntry.getSubType())){
					escEntry.setOriginnalCost(kdbzDis);
				}		
				//累计其他费用金额
				otherCharge = otherCharge.add(escEntry.getOriginnalCost());
			}
			
			//将其他费用金额存入返回实体
			escWayBillMiddleEntity.setOtherFee(otherCharge);
		}
	}
	
	/**
	 * 将计算好的费用明细封装到返回类
	 */
	public void setEscResultBill(List<GuiResultBillCalculateDto> guiResultBillCalculateDtos, EscWayBillResponseEntity escWayBillResponseEntity,boolean accurateValuation){
		// 将代收货款费率转换成千分率的格式
//		BigDecimal permillage = new BigDecimal(WaybillConstants.PERMILLAGE);
		List<EscResultBillCalculateDto> escResultBillList = new ArrayList<EscResultBillCalculateDto>();
		for(GuiResultBillCalculateDto guiResultBill : guiResultBillCalculateDtos){
			EscResultBillCalculateDto escResultBill = new EscResultBillCalculateDto();				 	
			//费用类型代码
			escResultBill.setPriceEntryCode(guiResultBill.getPriceEntryCode());
			//费用类型名称
			escResultBill.setPriceEntryName(guiResultBill.getPriceEntryName());
			//子类型
			escResultBill.setSubType(guiResultBill.getSubType());
			//经过计算后的费用，例如使用传入的 重量* feeRate 得出后的计算值
			BigDecimal caculateFee = guiResultBill.getCaculateFee() != null ? guiResultBill.getCaculateFee() : BigDecimal.ZERO;
			//打折后的费用
			BigDecimal discountFee = guiResultBill.getDiscountFee() != null ? guiResultBill.getDiscountFee() : BigDecimal.ZERO;
			//最终实际计算的费率
		    BigDecimal actualFeeRate = guiResultBill.getActualFeeRate() != null ? guiResultBill.getActualFeeRate() : BigDecimal.ZERO;
		    escResultBill.setActualFeeRate(actualFeeRate);
			if("FRT".equalsIgnoreCase(guiResultBill.getPriceEntryCode())&&accurateValuation){				
					caculateFee = caculateFee.setScale(2,BigDecimal.ROUND_HALF_UP);	
					discountFee = discountFee.setScale(2,BigDecimal.ROUND_HALF_UP);// 四舍五入
			}else{
				caculateFee = caculateFee.setScale(0,BigDecimal.ROUND_HALF_UP);	
				discountFee = discountFee.setScale(0,BigDecimal.ROUND_HALF_UP);// 四舍五入
			}
			
			escResultBill.setCaculateFee(caculateFee);
			escResultBill.setDiscountFee(discountFee);
			//单价/费率
			BigDecimal feeRate = guiResultBill.getFeeRate() != null ? guiResultBill.getFeeRate() : BigDecimal.ZERO;
			//保价和代收费率乘以1000
//			if(PriceEntityConstants.PRICING_CODE_BF.equals(guiResultBill.getPriceEntryCode())
//					|| PriceEntityConstants.PRICING_CODE_HK.equals(guiResultBill.getPriceEntryCode())){
//				feeRate = feeRate.multiply(permillage);
//			}
			escResultBill.setFeeRate(feeRate);
			//计费类别:WEIGHT，按重量计费，单位为公斤，VOLUME 按体积计费，单位为立方；
			escResultBill.setCaculateType(guiResultBill.getCaculateType());
			//折扣信息
			if(null != guiResultBill.getDiscountPrograms()){
				List<EscResultDiscountDto> escResultDiscountList = new ArrayList<EscResultDiscountDto>();
				for(GuiResultDiscountDto guiResultDiscount : guiResultBill.getDiscountPrograms()){
					EscResultDiscountDto escResultDiscount = new EscResultDiscountDto();
					//折扣率
					escResultDiscount.setDiscountRate(guiResultDiscount.getDiscountRate());
					//快递续重折扣率
					escResultDiscount.setRenewalDiscountRate(guiResultDiscount.getRenewalDiscountRate());
					//折扣费用
					BigDecimal reduceFee = guiResultDiscount.getReduceFee() != null ? guiResultDiscount.getReduceFee() : BigDecimal.ZERO;
					reduceFee = reduceFee.setScale(0,BigDecimal.ROUND_HALF_UP);// 四舍五入
					escResultDiscount.setReduceFee(reduceFee);
					//市场CODE
					escResultDiscount.setMarketCode(guiResultDiscount.getMarketCode());
					//市场NAME
					escResultDiscount.setMarketName(guiResultDiscount.getMarketName());
					//市场活动类型
					escResultDiscount.setMarketType(guiResultDiscount.getMarketType());
					//计价条目CODE
					escResultDiscount.setPriceEntryCode(guiResultDiscount.getPriceEntryCode());
					//计价条目Name
					escResultDiscount.setPriceEntryName(guiResultDiscount.getPriceEntryName());
					//子类型
					escResultDiscount.setSubType(guiResultDiscount.getSubType());
					//方案渠道CODE
					escResultDiscount.setSaleChannelCode(guiResultDiscount.getSaleChannelCode());
					//方案渠道NAME
					escResultDiscount.setSaleChannelName(guiResultDiscount.getSaleChannelName());
					//优先级类型、产品、渠道、客户
					escResultDiscount.setDiscountType(guiResultDiscount.getDiscountType());
					//优先级类型名称、产品、渠道、客户
					escResultDiscount.setDiscountTypeName(guiResultDiscount.getDiscountTypeName());
					
					//放入单个折扣LIST
					escResultDiscountList.add(escResultDiscount);
				}
				
				//为了解决LIST只有一条值时，JSON转化会转成MAP
				if(escResultDiscountList.size() <= 1){
					EscResultDiscountDto escResultDiscount = new EscResultDiscountDto();
					escResultDiscount.setPriceEntryCode("FOSS_FOR_ESC_NONE_DISCOUNT");
					escResultDiscountList.add(escResultDiscount);
				}
				
				//折扣信息放入返回类
				escResultBill.setResultDiscountDtos(escResultDiscountList);
			}
			//放入LIST
			escResultBillList.add(escResultBill);
		}
		
		//为了解决LIST只有一条值时，JSON转化会转成MAP
		if(escResultBillList.size() <= 1){
			EscResultBillCalculateDto escResultBill = new EscResultBillCalculateDto();	
			escResultBill.setPriceEntryCode("FOSS_FOR_ESC_NONE");
			escResultBillList.add(escResultBill);
		}
		
		//将转化好的费用存入返回类
		escWayBillResponseEntity.setResultBillCalculateDtos(escResultBillList);
	}
	
	/**
	 * 将请求实体处理成FOSS中的使用类
	 */
	public GuiQueryBillCalculateDto getGuiQueryBillCalculateDto(EscWayBillRequestEntity requestEntity, 
			EscWayBillMiddleEntity escWayBillMiddleEntity){
		//将请求实体处理成FOSS中的使用类
		GuiQueryBillCalculateDto billCalculateDto = new GuiQueryBillCalculateDto();
		
		//OWS 特有字段 (沟通后 悟空 暂不考虑)
		//该接口的使用者(官网：OWS CC:CC)
		billCalculateDto.setUsers(null);
		//出发区县code(目前官网使用)
		billCalculateDto.setStartCountyCode(null);
		//出发城市code(目前官网使用)
		billCalculateDto.setStartCityCode(null);
		//出发省份code(目前官网使用)
		billCalculateDto.setStartProvCode(null);
		//到达区县code(目前官网使用)
		billCalculateDto.setArriveCountyCode(null);
		//到达城市code(目前官网使用)
		billCalculateDto.setArriveCityCode(null);
		//到达省份code(目前官网使用)
		billCalculateDto.setArriveProvCode(null);
		
		//非OWS 字段
		//出发部门
		billCalculateDto.setOriginalOrgCode(requestEntity.getOriginalOrgCode());
		//到达部门
		billCalculateDto.setDestinationOrgCode(requestEntity.getDestinationOrgCode());
		//营业部收货日期（可选，无则表示当前日期）,即开单日期
		billCalculateDto.setReceiveDate(requestEntity.getBillDate());
		//计价条目列表
		if(null != requestEntity.getPriceEntities()){
			List<GuiQueryBillCalculateSubDto> guiSubDtoList = new ArrayList<GuiQueryBillCalculateSubDto>();
			//转化计价条目
			for(EscValuationEntryDto escEntry : requestEntity.getPriceEntities()){
				GuiQueryBillCalculateSubDto guiSubDto = new GuiQueryBillCalculateSubDto();
				//包装费和送货费不需要计价,签收费用需要分别判断计价
				if(!(PriceEntityConstants.PRICING_CODE_BZ.equals(escEntry.getPriceEntityCode())
						|| PriceEntityConstants.PRICING_CODE_SH.equals(escEntry.getPriceEntityCode())
						|| PriceEntityConstants.PRICING_CODE_QS.equals(escEntry.getPriceEntityCode()))){
					//费用类型代码
					guiSubDto.setPriceEntityCode(escEntry.getPriceEntityCode());
					//原始费用（增值服务具备）
					guiSubDto.setOriginnalCost(escEntry.getOriginnalCost());
					//增值服务子类型（增值服务具备）
					guiSubDto.setSubType(escEntry.getSubType());
					//放入 LIST
					guiSubDtoList.add(guiSubDto);
				}
			}
			//存入转化好的计价条目
			billCalculateDto.setPriceEntities(guiSubDtoList);
		}
		//大礼包code
//		billCalculateDto.setCityMarketCode(); 确认后 悟空这边没有大礼包
		//产品编号
		billCalculateDto.setProductCode(requestEntity.getProductCode());
		//币种
		billCalculateDto.setCurrencyCdoe(requestEntity.getCurrencyCdoe());
		//航班班次
		billCalculateDto.setFlightShift(null);  //快递没有空运不用考虑
		//货物类型（目前只有空运价格才和货物类型有关，汽运价格与货物类型没有关系的）
		billCalculateDto.setGoodsCode(null);	//快递没有空运不用考虑
		//是否接货
		billCalculateDto.setIsReceiveGoods(FossConstants.NO); //快递应该都是默认不接货（界面上门接货不可勾选）
		//渠道code
		billCalculateDto.setChannelCode(requestEntity.getChannelCode());
		//设置 重量
		billCalculateDto.setWeight(requestEntity.getWeight());
		//体积
		billCalculateDto.setVolume(requestEntity.getVolume());
		//客户编码
		billCalculateDto.setCustomerCode(requestEntity.getCustomerCode());
		//是否自提
		billCalculateDto.setIsSelfPickUp(FossConstants.NO); //送货上楼
		//营销活动DTO
		billCalculateDto.setActiveDto(requestEntity.getActiveDto());
		//是否要计算营销活动折扣（默认为否）
		if(null != requestEntity.getActiveCode()){
			billCalculateDto.setCalActiveDiscount(true);
		}else{
			billCalculateDto.setCalActiveDiscount(false);
		}
		//活动编码
		billCalculateDto.setActiveCode(requestEntity.getActiveCode());
		//开单品名
		billCalculateDto.setGoodsName(requestEntity.getGoodsName());
		//发货客户编码
		billCalculateDto.setDeliveryCustomerCode(requestEntity.getCustomerCode());
		//订单来源
		billCalculateDto.setOrderChannel(requestEntity.getOrderChannel());
		//收货部门
		billCalculateDto.setReceiveOrgCode(requestEntity.getOriginalOrgCode());
		//出发外场
		billCalculateDto.setLoadOrgCode(requestEntity.getLoadOrgCode());
		//到达外场
		billCalculateDto.setLastOutLoadOrgCode(requestEntity.getLastOutLoadOrgCode());
		//到达部门
		billCalculateDto.setCustomerPickupOrgCode(requestEntity.getDestinationOrgCode());
		//开单时间
		billCalculateDto.setBillTime(requestEntity.getBillDate());
		//纯运费
		billCalculateDto.setTransportFee(BigDecimal.ZERO); //默认为0
		//重量
		billCalculateDto.setGoodsWeightTotal(requestEntity.getWeight());
		//体积
		billCalculateDto.setGoodsVolumeTotal(requestEntity.getVolume());
		//菜鸟新需求返货折扣
		//是否满足条件
		billCalculateDto.setIsCainiao(requestEntity.getIsCainiao());
		//原始单号
		billCalculateDto.setReturnWaybillNo(requestEntity.getReturnWaybillNo());
		//原收货人的客户编码
		billCalculateDto.setOldreceiveCustomerCode(requestEntity.getOldreceiveCustomerCode());
		//原始开单时间
		billCalculateDto.setReturnbilltime(requestEntity.getReturnbilltime());
		//原单保价费
		billCalculateDto.setReturnInsuranceFee(requestEntity.getReturnInsuranceFee());
		//原单公布价费
		billCalculateDto.setReturnTransportFee(requestEntity.getReturnTransportFee());
		//原单号收货部门编码
		billCalculateDto.setOriginalReceiveOrgCode(requestEntity.getOriginalReceiveOrgCode());
		//内部发货折扣
		//内部发货类型
		billCalculateDto.setInternalDeliveryType(requestEntity.getInternalDeliveryType());
		//员工号
		billCalculateDto.setEmployeeNo(requestEntity.getEmployeeNo());
		//根据条件查询当前月份的优惠总额(由悟空直接提供)
		BigDecimal amount = requestEntity.getEmployeeAmount() != null ? requestEntity.getEmployeeAmount() : BigDecimal.ZERO;
		if (requestEntity.getInternalDeliveryType() != null) {
			billCalculateDto.setInternalDeliveryType(requestEntity.getInternalDeliveryType());
		}
		//当前月份的优惠总额
		billCalculateDto.setAmount(amount);
		//当前月份的发货总金额(FOSS没有对员工发货总金额赋值)
		billCalculateDto.setTotalAmount(null);			
		//是否伙伴开单
		//是否合伙人
		if(null!=requestEntity.getPartnerBilling()){
			if(requestEntity.getPartnerBilling()){
				billCalculateDto.setPartnerBillingLogo("Y");
			}else{
				billCalculateDto.setPartnerBillingLogo("N");
			}
		}
		//是否上门发货
		billCalculateDto.setHomeDelivery(false); //快递没有上门接货
		
		//返回封装好的查询实体
		return billCalculateDto;
	}
	
	//添加计费日志
	public void saveEcsBillLog(Map ecsBillLog){
		//保存日志
		try {
			saveEcsExpressBillLogDao.saveEcsBillLog(ecsBillLog);
		} catch (Exception e) {
			throw new WaybillValidateException("计费日志添加失败:" + e.getMessage().toString());
		}
	}
}
