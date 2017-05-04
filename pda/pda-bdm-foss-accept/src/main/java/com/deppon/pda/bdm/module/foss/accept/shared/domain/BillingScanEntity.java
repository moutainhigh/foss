package com.deppon.pda.bdm.module.foss.accept.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.shared.domain 
 * @file BillingScanEntity.java 
 * @description 开单信息实体类
 * @author ChenLiang
 * @created 2012-12-31 上午11:34:21    
 * @version 1.0
 */
public class BillingScanEntity extends ScanMsgEntity {

	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 订单号
	 */
	private String orderCode;

	/**
	 * 出发部门
	 */
	private String departDeptCode;

	/**
	 * 提货方式
	 */
	private String takeType;

	/**
	 * 目的地编号
	 */
	private String destinationCode;

	/**
	 * 运输性质
	 */
	private String transType;

	/**
	 * 重量
	 */
	private double weight;

	/**
	 * 体积
	 */
	private double volume;
	
	/**
	 * 体积尺寸  2013-08-09 新增体积尺寸 xujun
	 */
	private String volumeSize;

	/**
	 * 代打木架体积
	 */
	private double gallowsVolume;
	
	/**
	 * 打木架尺寸  2013-08-09 新增体积尺寸 xujun
	 */
	private String gallowsVolumeSize;

	/**
	 * 代打木箱体积
	 */
	private double boxVolume;
	
	/**
	 * 打木箱尺寸  2013-08-09 新增体积尺寸 xujun
	 */
	private String boxVolumeSize;

	/**
	 * 包装类型
	 */
	private String wrapType;

	/**
	 * 货物类型
	 */
	private String crgType;

	/**
	 * 车牌号
	 */
	private String truckCode;
	
	/**
	 * 运费
	 */
	private double freight;

	/**
	 * 实收运费
	 */
	private double paidFreight;

	/**
	 * 优惠券
	 */
	private String couponCode;
	
	/**
	 * 优惠金额
	 */
	private double couponMoney;

	/**
	 * 付款方式
	 */
	private String paidType;

	/**
	 * 是否打木架
	 */
	private String isGallows;

	/**
	 * 增值服务项
	 */
	private List<ValueAddServiceEntity> appreciationService;

	/**
	 * 返单类别
	 */
	private String returnBillType;
	
	/**
	 * 退款类型
	 */
	private String refundType;
	
	/**
	 * PDA前台开单保存数据库时间
	 */
	private Date billBeginTm;

	/**
     * 营销编码
     */
    private String marketingCode;
    /**       
     *营销名称
     */
    private String marketingName;
	
    /**
     * 收货人地址：省
     */
	private String  province;
	
    /**
     * 收货人地址：市
     */
    private String city;
    /**
     * 收货人地址：区
     */
    private String region;
    
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	
	public String getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(String volumeSize) {
		this.volumeSize = volumeSize;
	}

	public String getGallowsVolumeSize() {
		return gallowsVolumeSize;
	}

	public void setGallowsVolumeSize(String gallowsVolumeSize) {
		this.gallowsVolumeSize = gallowsVolumeSize;
	}

	public String getBoxVolumeSize() {
		return boxVolumeSize;
	}

	public void setBoxVolumeSize(String boxVolumeSize) {
		this.boxVolumeSize = boxVolumeSize;
	}

	public String getReturnBillType() {
		return returnBillType;
	}

	public void setReturnBillType(String returnBillType) {
		this.returnBillType = returnBillType;
	}

	public String getRefundType() {
		return refundType;
	}

	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getDepartDeptCode() {
		return departDeptCode;
	}

	public void setDepartDeptCode(String departDeptCode) {
		this.departDeptCode = departDeptCode;
	}

	public String getTakeType() {
		return takeType;
	}

	public void setTakeType(String takeType) {
		this.takeType = takeType;
	}

	public String getDestinationCode() {
		return destinationCode;
	}

	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getGallowsVolume() {
		return gallowsVolume;
	}

	public void setGallowsVolume(double gallowsVolume) {
		this.gallowsVolume = gallowsVolume;
	}

	public double getBoxVolume() {
		return boxVolume;
	}

	public void setBoxVolume(double boxVolume) {
		this.boxVolume = boxVolume;
	}

	public String getWrapType() {
		return wrapType;
	}

	public void setWrapType(String wrapType) {
		this.wrapType = wrapType;
	}

	public String getCrgType() {
		return crgType;
	}

	public void setCrgType(String crgType) {
		this.crgType = crgType;
	}

	public String getTruckCode() {
		return truckCode;
	}

	public void setTruckCode(String truckCode) {
		this.truckCode = truckCode;
	}

	public double getPaidFreight() {
		return paidFreight;
	}

	public void setPaidFreight(double paidFreight) {
		this.paidFreight = paidFreight;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getPaidType() {
		return paidType;
	}

	public void setPaidType(String paidType) {
		this.paidType = paidType;
	}

	public String getIsGallows() {
		return isGallows;
	}

	public void setIsGallows(String isGallows) {
		this.isGallows = isGallows;
	}

	public List<ValueAddServiceEntity> getAppreciationService() {
		return appreciationService;
	}

	public void setAppreciationService(
			List<ValueAddServiceEntity> appreciationService) {
		this.appreciationService = appreciationService;
	}

	public double getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(double couponMoney) {
		this.couponMoney = couponMoney;
	}

	public double getFreight() {
		return freight;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public String getMarketingCode() {
		return marketingCode;
	}

	public void setMarketingCode(String marketingCode) {
		this.marketingCode = marketingCode;
	}

	public String getMarketingName() {
		return marketingName;
	}

	public void setMarketingName(String marketingName) {
		this.marketingName = marketingName;
	}
	
    public Date getBillBeginTm() {
		return billBeginTm;
	}

	public void setBillBeginTm(Date billBeginTm) {
		this.billBeginTm = billBeginTm;
	}

}