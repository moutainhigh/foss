package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;
/**
 * 快递派送区域Entity
 * @author 130566
 *
 */
public class ExpressDeliveryRegionsEntity extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5993410812113894717L;
	
	/**
	 * 区域编码
	 */
	private String code;
	/**
	 * 区域名称
	 */
	private String name;
	/**
     * 上级行政区域编码.
     */	
    private String parentDistrictCode;

    /**
     * 上级行政区域名称.
     */	
    private String parentDistrictName;

    /**
     * 虚拟行政区域.
     */	
    private String virtualDistrictId;

    /**
     * 行政区域级别.
     */	
    private String degree;
    
    /**
     * 行政区域级别名称“扩展字段”.
     */	
     private String degreeName;
     /**
      * 区号
      */
     private String areaCode;
     /**
      * 非标准派送时效
      */
     private String nonStandardDeliveryTime;
     /**
      * 派送属性
      */
     private String deliveryNature;
     /**
      * 是否大客户全境
      */
     private String isBigCustomerOwnRegion;
     /**
      * 所属营业部编码
      */
     private String expressSalesDeptCode;
     /**
      * 所属营业部名称
      */
     private String expressSalesDeptName;
     /**
      * 备注
      */
     private String remark;
     /**
      * 收件区域
      */
     private String remarkDe;
    /**
     * 特殊区域
     */
     private String specialArea;
    /**
     * 是否启用.
     */	
    private String active;
    /**
     * 版本号
     */
    private long versionNo;
    /**
     * 是否叶子节点
     */
    private String isLeaf; 
    /**
     * 城市等级
     */
    private String cityLevel;
    /**
     * 城市市辖区面积
     */
    private int cityArea;
    /**
     * 快递代理网点地图坐标编码
     */
    private String expressProxyCoordinate;
    /**
     * 快递代理网点派送属性
     */
    private String expressProxyDeliveryNature;
    /**
     * 派送属性中文名称
     */
    private String deliveryNatureName;
    /**
     * 配合快递代理网点编辑需求拼接行政级别上下级名称
     */
    private String districtAddress;
    
	public String getCityLevel() {
		return cityLevel;
	}
	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}
	public int getCityArea() {
		return cityArea;
	}
	public void setCityArea(int cityArea) {
		this.cityArea = cityArea;
	}
	public long getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(long versionNo) {
		this.versionNo = versionNo;
	}
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
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getNonStandardDeliveryTime() {
		return nonStandardDeliveryTime;
	}
	public void setNonStandardDeliveryTime(String nonStandardDeliveryTime) {
		this.nonStandardDeliveryTime = nonStandardDeliveryTime;
	}
	public String getDeliveryNature() {
		return deliveryNature;
	}
	public void setDeliveryNature(String deliveryNature) {
		this.deliveryNature = deliveryNature;
	}
	public String getIsBigCustomerOwnRegion() {
		return isBigCustomerOwnRegion;
	}
	public void setIsBigCustomerOwnRegion(String isBigCustomerOwnRegion) {
		this.isBigCustomerOwnRegion = isBigCustomerOwnRegion;
	}
	public String getExpressSalesDeptCode() {
		return expressSalesDeptCode;
	}
	public void setExpressSalesDeptCode(String expressSalesDeptCode) {
		this.expressSalesDeptCode = expressSalesDeptCode;
	}
	public String getExpressSalesDeptName() {
		return expressSalesDeptName;
	}
	public void setExpressSalesDeptName(String expressSalesDeptName) {
		this.expressSalesDeptName = expressSalesDeptName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getRemarkDe() {
		return remarkDe;
	}
	public void setRemarkDe(String remarkDe) {
		this.remarkDe = remarkDe;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	/**
	 * @return  the expressProxyCoordinate
	 */
	public String getExpressProxyCoordinate() {
		return expressProxyCoordinate;
	}
	/**
	 * @param expressProxyCoordinate the expressProxyCoordinate to set
	 */
	public void setExpressProxyCoordinate(String expressProxyCoordinate) {
		this.expressProxyCoordinate = expressProxyCoordinate;
	}
	/**
	 * @return  the expressProxyDeliveryNature
	 */
	public String getExpressProxyDeliveryNature() {
		return expressProxyDeliveryNature;
	}
	/**
	 * @param expressProxyDeliveryNature the expressProxyDeliveryNature to set
	 */
	public void setExpressProxyDeliveryNature(String expressProxyDeliveryNature) {
		this.expressProxyDeliveryNature = expressProxyDeliveryNature;
	}
	/**
	 * @return  the deliveryNatureName
	 */
	public String getDeliveryNatureName() {
		return deliveryNatureName;
	}
	/**
	 * @param deliveryNatureName the deliveryNatureName to set
	 */
	public void setDeliveryNatureName(String deliveryNatureName) {
		this.deliveryNatureName = deliveryNatureName;
	}
	public String getDistrictAddress() {
		return districtAddress;
	}
	public void setDistrictAddress(String districtAddress) {
		this.districtAddress = districtAddress;
	}
	public String getSpecialArea() {
		return specialArea;
	}
	public void setSpecialArea(String specialArea) {
		this.specialArea = specialArea;
	}
      
}
