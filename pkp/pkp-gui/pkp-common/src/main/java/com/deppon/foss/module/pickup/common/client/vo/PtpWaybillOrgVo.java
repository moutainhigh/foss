package com.deppon.foss.module.pickup.common.client.vo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.common.client.utils.CalculateFeeTotalUtils;

/**
 * 
 * 运单属性VO 合伙人项目-运单信息修改前信息
 * 
 * @author 272311-FOSS-sangwenhao
 * @date 2016-01-11
 */
public class PtpWaybillOrgVo {
	
	private static Logger logger = LoggerFactory.getLogger(PtpWaybillOrgVo.class);
	
	// 公布价运费
	private  BigDecimal transportFee;

	/**
	 * 保价费 
	 */
	private  BigDecimal insuranceFee;
	
	/**
	 * 保价费 （修改前的值）
	 */
	private  BigDecimal insuranceFeeOrg;

	// 代收货款手续费
	private  BigDecimal codFee;
	
	private  BigDecimal codFeeOrg;
	
	// 接货费
	private  String pickUpFee;

	// 送货费+送货上楼费
	private  String deliveryGoodsFee;
	
	//基础送货费
	private  String baseDeliveryGoodsFee;
	
	//上楼费
	private  String upFloorFee;
	
	// 打木架货物费用
	private  BigDecimal standCharge;
	
	// 打木箱货物费用
	private  BigDecimal boxCharge;

	// 打木托货物费用
	private  BigDecimal salverGoodsCharge;
	
	// 包装费
	private  String packageFee;
	
	/**
	 * 装卸费
	 */
	private  BigDecimal serviceFee;

	// 其他费用
	private  BigDecimal otherFee;

	// 总费用
	private  BigDecimal totalFee;
	
	//送货进仓费
	private  BigDecimal deliveryWareHouseFee;
		
	//大件上楼费
	private  BigDecimal bigGoodsUpFloorFee ;
	
	//送货安装费
	private  BigDecimal pickupToDoorJZFee;

	// 异常操作服务费 ZZ
	private  BigDecimal exceptionOpreationFee;

	// 超远派送费 CY
	private  BigDecimal overDistanceFee;
	
	//签收单
	private  BigDecimal signBillFee ;

	private  boolean isChangeLabel;
	
	//转运返货是否重新计算公布价运费
	private  String isCalTraFee;
	//重新计算公布价运费出发部门
	private  String startOrgCodeCal;
	//重新计算公布价运费到达部门
	private  String destinationOrgCodeCal;
	
	//合伙人到达加收
	private  BigDecimal overTransportFee;
	//合伙人到达加收费率
	private  BigDecimal overTransportRate;
	
	//优惠劵金额--合伙人第二批需求--zoushengli
	//优惠券金额
	private BigDecimal couponFee;

	public  BigDecimal getTransportFee() {
		return transportFee;
	}

	public  BigDecimal getCodFee() {
		return codFee;
	}

	/**
	 * 初始化值/值清空
	 * @author 272311-sangwenhao
	 * @date 2016-1-13
	 */
	public static PtpWaybillOrgVo init(){
		try {
			Class<PtpWaybillOrgVo> clazz = PtpWaybillOrgVo.class;
			Constructor<PtpWaybillOrgVo> con = clazz.getDeclaredConstructor(new Class[] {});
			con.setAccessible(true); 
			PtpWaybillOrgVo obj = con.newInstance(new Object[] {});
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					Method setMethod = clazz.getMethod(methodName,method.getParameterTypes()[0]);
					if (BigDecimal.class.isAssignableFrom(method.getParameterTypes()[0])) {
						setMethod.invoke(obj, BigDecimal.ZERO);
					}else if (String.class.isAssignableFrom(method.getParameterTypes()[0])) {
						setMethod.invoke(obj, "0");
					}else if(Boolean.class.isAssignableFrom(method.getParameterTypes()[0])
							||(boolean.class.isAssignableFrom(method.getParameterTypes()[0])) ){
						setMethod.invoke(obj, false);
					}else{
						logger.error("解析 _field.getType() 类型出错了");
						
					}
				}
			}
			return obj ;
		} catch (Exception e) {
			logger.error(" PtpWaybillOrgVo初始化值异常："+e.getMessage()) ;
			throw new BusinessException(" PtpWaybillOrgVo初始化值异常："+e.getMessage());
		}
	}

	public BigDecimal getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(BigDecimal serviceFee) {
		this.serviceFee = serviceFee;
	}

	public BigDecimal getDeliveryWareHouseFee() {
		return deliveryWareHouseFee;
	}

	public void setDeliveryWareHouseFee(BigDecimal deliveryWareHouseFee) {
		this.deliveryWareHouseFee = deliveryWareHouseFee;
	}

	public BigDecimal getBigGoodsUpFloorFee() {
		return bigGoodsUpFloorFee;
	}

	public void setBigGoodsUpFloorFee(BigDecimal bigGoodsUpFloorFee) {
		this.bigGoodsUpFloorFee = bigGoodsUpFloorFee;
	}

	public  BigDecimal getPickupToDoorJZFee() {
		return pickupToDoorJZFee;
	}

	public  BigDecimal getCodFeeOrg() {
		return codFeeOrg;
	}

	public  String getStartOrgCodeCal() {
		return startOrgCodeCal;
	}

	public  String getDestinationOrgCodeCal() {
		return destinationOrgCodeCal;
	}

	public BigDecimal getInsuranceFee() {
		return insuranceFee;
	}

	public void setInsuranceFee(BigDecimal insuranceFee) {
		this.insuranceFee = insuranceFee;
	}

	public BigDecimal getInsuranceFeeOrg() {
		return insuranceFeeOrg;
	}

	public void setInsuranceFeeOrg(BigDecimal insuranceFeeOrg) {
		this.insuranceFeeOrg = insuranceFeeOrg;
	}

	public String getPickUpFee() {
		return pickUpFee;
	}

	public void setPickUpFee(String pickUpFee) {
		this.pickUpFee = pickUpFee;
	}

	public String getDeliveryGoodsFee() {
		return deliveryGoodsFee;
	}

	public void setDeliveryGoodsFee(String deliveryGoodsFee) {
		this.deliveryGoodsFee = deliveryGoodsFee;
	}

	public String getBaseDeliveryGoodsFee() {
		return baseDeliveryGoodsFee;
	}

	public void setBaseDeliveryGoodsFee(String baseDeliveryGoodsFee) {
		this.baseDeliveryGoodsFee = baseDeliveryGoodsFee;
	}

	public String getUpFloorFee() {
		return upFloorFee;
	}

	public void setUpFloorFee(String upFloorFee) {
		this.upFloorFee = upFloorFee;
	}

	public BigDecimal getStandCharge() {
		return standCharge;
	}

	public void setStandCharge(BigDecimal standCharge) {
		this.standCharge = standCharge;
	}

	public BigDecimal getBoxCharge() {
		return boxCharge;
	}

	public void setBoxCharge(BigDecimal boxCharge) {
		this.boxCharge = boxCharge;
	}

	public BigDecimal getSalverGoodsCharge() {
		return salverGoodsCharge;
	}

	public void setSalverGoodsCharge(BigDecimal salverGoodsCharge) {
		this.salverGoodsCharge = salverGoodsCharge;
	}

	public String getPackageFee() {
		return packageFee;
	}

	public void setPackageFee(String packageFee) {
		this.packageFee = packageFee;
	}

	public BigDecimal getOtherFee() {
		return otherFee;
	}

	public void setOtherFee(BigDecimal otherFee) {
		this.otherFee = otherFee;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getExceptionOpreationFee() {
		return exceptionOpreationFee;
	}

	public void setExceptionOpreationFee(BigDecimal exceptionOpreationFee) {
		this.exceptionOpreationFee = exceptionOpreationFee;
	}

	public BigDecimal getOverDistanceFee() {
		return overDistanceFee;
	}

	public void setOverDistanceFee(BigDecimal overDistanceFee) {
		this.overDistanceFee = overDistanceFee;
	}

	public BigDecimal getSignBillFee() {
		return signBillFee;
	}

	public void setSignBillFee(BigDecimal signBillFee) {
		this.signBillFee = signBillFee;
	}

	public boolean isChangeLabel() {
		return isChangeLabel;
	}

	public void setChangeLabel(boolean isChangeLabel) {
		this.isChangeLabel = isChangeLabel;
	}

	public String getIsCalTraFee() {
		return isCalTraFee;
	}

	public void setIsCalTraFee(String isCalTraFee) {
		this.isCalTraFee = isCalTraFee;
	}

	public void setCodFeeOrg(BigDecimal codFeeOrg) {
		this.codFeeOrg = codFeeOrg;
	}

	public void setPickupToDoorJZFee(BigDecimal pickupToDoorJZFee) {
		this.pickupToDoorJZFee = pickupToDoorJZFee;
	}

	public void setStartOrgCodeCal(String startOrgCodeCal) {
		this.startOrgCodeCal = startOrgCodeCal;
	}

	public void setDestinationOrgCodeCal(String destinationOrgCodeCal) {
		this.destinationOrgCodeCal = destinationOrgCodeCal;
	}

	public void setTransportFee(BigDecimal transportFee) {
		this.transportFee = CalculateFeeTotalUtils.formatNumberInteger(transportFee);
	}

	public void setCodFee(BigDecimal codFee) {
		this.codFee = CalculateFeeTotalUtils.formatNumberInteger(codFee);
	}

	public BigDecimal getOverTransportFee() {
		return overTransportFee;
	}

	public void setOverTransportFee(BigDecimal overTransportFee) {
		this.overTransportFee = overTransportFee;
	}

	public BigDecimal getOverTransportRate() {
		return overTransportRate;
	}

	public void setOverTransportRate(BigDecimal overTransportRate) {
		this.overTransportRate = overTransportRate;
	}

	public BigDecimal getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(BigDecimal couponFee) {
		this.couponFee = couponFee;
	}

	 /**
     * 偏线费  352676
     */
    private BigDecimal partialTransportFee;

	public BigDecimal getPartialTransportFee() {
		return partialTransportFee;
	}

	public void setPartialTransportFee(BigDecimal partialTransportFee) {
		this.partialTransportFee = partialTransportFee;
	}
}