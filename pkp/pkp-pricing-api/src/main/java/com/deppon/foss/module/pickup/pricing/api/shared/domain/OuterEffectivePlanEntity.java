package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.io.Serializable;
import java.util.Date;

public class OuterEffectivePlanEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private String id;
	/**
	 * 版本名称
	 */
    private String name;
    /**
     * 版本号
     */
    private Long versionNo;
    /**
     * 是否激活
     */
    private String active;
    /**
     * 偏线部门Code
     */
    private String partialLineCode;
    /**
     * 产品类型
     */
    private String productCode;
    /**
     * 外发外场
     */
    private String outFieldCode;
    /**
     * 外发外场
     */
    private String outFieldName;
    /**
     * 国家
     */
    private String nationCode;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 省份Code
     */
    private String provCode;
    /**
     * 省份Name
     */
    private String provName;
    /**
     * 城市Name
     */
    private String cityName;
    /**
     * 区县name
     */
    private String countyName;
    /**
     * 城市Code
     */
    private String cityCode;
    /**
     * 县Code
     */
    private String countyCode;
    /**
     * 承诺最大天数或时间
     */
    private Integer maxTime;
    /**
     * 承诺最大天数或时间单位
     */
    private String maxTimeUnit;
    /**
     * 承诺最小天数或时间
     */
    private Integer minTime;
    /**
     * 承诺最小天数或时间单位
     */
    private String minTimeUnit;
    /**
     * 到达代理网点承诺时点
     */
    private String arriveOuterBranchTime;
    /**
     * 派送承诺时点
     */
    private String deliveryTime;
    /**
     * 派送承诺需加天数
     */
    private Integer addDay;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 创建人Code
     */
    private String createUserCode;
    /**
     * 修改人Code
     */
    private String modifyUserCode;
    /**
     * 创建人OrgCode
     */
    private String createOrgCode;
    /**
     * 修改人Code
     */
    private String modifyOrgCode;

    public String getId() {
        return id;
    }

	public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }


    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    public String getPartialLineCode() {
        return partialLineCode;
    }

    public void setPartialLineCode(String partialLineCode) {
        this.partialLineCode = partialLineCode == null ? null : partialLineCode.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getNationCode() {
        return nationCode;
    }

    public Long getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}

	public String getOutFieldCode() {
		return outFieldCode;
	}

	public void setOutFieldCode(String outFieldCode) {
		this.outFieldCode = outFieldCode;
	}

	public String getOutFieldName() {
		return outFieldName;
	}

	public void setOutFieldName(String outFieldName) {
		this.outFieldName = outFieldName;
	}

	public void setNationCode(String nationCode) {
        this.nationCode = nationCode == null ? null : nationCode.trim();
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode == null ? null : provCode.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getCountyCode() {
        return countyCode;
    }

    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode == null ? null : countyCode.trim();
    }

    public Integer getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Integer maxTime) {
        this.maxTime = maxTime;
    }

    public String getMaxTimeUnit() {
        return maxTimeUnit;
    }

    public void setMaxTimeUnit(String maxTimeUnit) {
        this.maxTimeUnit = maxTimeUnit == null ? null : maxTimeUnit.trim();
    }

    public Integer getMinTime() {
        return minTime;
    }

    public void setMinTime(Integer minTime) {
        this.minTime = minTime;
    }

    public String getMinTimeUnit() {
        return minTimeUnit;
    }

    public void setMinTimeUnit(String minTimeUnit) {
        this.minTimeUnit = minTimeUnit == null ? null : minTimeUnit.trim();
    }

    public String getArriveOuterBranchTime() {
        return arriveOuterBranchTime;
    }

    public void setArriveOuterBranchTime(String arriveOuterBranchTime) {
        this.arriveOuterBranchTime = arriveOuterBranchTime == null ? null : arriveOuterBranchTime.trim();
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime == null ? null : deliveryTime.trim();
    }

    public Integer getAddDay() {
        return addDay;
    }

    public void setAddDay(Integer addDay) {
        this.addDay = addDay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getCreateUserCode() {
        return createUserCode;
    }

    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode == null ? null : createUserCode.trim();
    }

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode == null ? null : modifyUserCode.trim();
    }

    public String getCreateOrgCode() {
        return createOrgCode;
    }

    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode == null ? null : createOrgCode.trim();
    }

    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode == null ? null : modifyOrgCode.trim();
    }

	public String getProvName() {
		return provName;
	}

	public void setProvName(String provName) {
		this.provName = provName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}