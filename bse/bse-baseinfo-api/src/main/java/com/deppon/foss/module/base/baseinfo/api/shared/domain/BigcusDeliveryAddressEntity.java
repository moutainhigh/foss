package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;


/**
 *  零担大客户派送地址库
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:232607,date:2016-3-25 下午4:19:15,content:TODO </p>
 * @author 232607 
 * @date 2016-3-25 下午4:19:15
 * @since
 * @version
 */
public class BigcusDeliveryAddressEntity extends BaseEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4431043981093093441L;
	
	/**
     * 编码
     */
	private String code;
	
	/**
     * 名称
     */
	private String name;
	
    /**
     * 上级区域编码
     */
	private String parentDistrictCode;

	/**
     * 上级区域名称
     */
	private String parentDistrictName;
	
	/**
     * 是否虚拟区域
     */
	private String virtualDistrictId;
	
	/**
     * 行政区域级别
     */
	private String degree;
	
	/**
     * 派送加时
     */
	private double deliveryAddTime;
	
	/**
     * 派送网点类型
     */
	private String deliveryType;
	
	/**
	 * 超派费用
	 */
	private Double muchHigherDelivery;
	
	/**
     * 派送营业部编码
     */
	private String deliverySalesDeptCode;
	/**
     * 派送营业部名称
     */
	private String deliverySalesDeptName;
	/**
     * 派送备注
     */
	private String deliveryRemark;
	
	/**
     * 是否有效
     */
	private String active;
	
	/**
     * 是否叶子节点（用于前台逻辑，是叶子节点的才能修改派送属性）
     */
	private String isLeaf;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentDistrictCode() {
		return parentDistrictCode;
	}

	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}

	public String getParentDistrictName() {
		return parentDistrictName;
	}

	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}

	public String getVirtualDistrictId() {
		return virtualDistrictId;
	}

	public void setVirtualDistrictId(String virtualDistrictId) {
		this.virtualDistrictId = virtualDistrictId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public double getDeliveryAddTime() {
		return deliveryAddTime;
	}

	public void setDeliveryAddTime(double deliveryAddTime) {
		this.deliveryAddTime = deliveryAddTime;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public Double getMuchHigherDelivery() {
		return muchHigherDelivery;
	}

	public void setMuchHigherDelivery(Double muchHigherDelivery) {
		this.muchHigherDelivery = muchHigherDelivery;
	}

	public String getDeliverySalesDeptCode() {
		return deliverySalesDeptCode;
	}

	public void setDeliverySalesDeptCode(String deliverySalesDeptCode) {
		this.deliverySalesDeptCode = deliverySalesDeptCode;
	}

	public String getDeliveryRemark() {
		return deliveryRemark;
	}

	public void setDeliveryRemark(String deliveryRemark) {
		this.deliveryRemark = deliveryRemark;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}


	public String getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getDeliverySalesDeptName() {
		return deliverySalesDeptName;
	}

	public void setDeliverySalesDeptName(String deliverySalesDeptName) {
		this.deliverySalesDeptName = deliverySalesDeptName;
	}


	

}
