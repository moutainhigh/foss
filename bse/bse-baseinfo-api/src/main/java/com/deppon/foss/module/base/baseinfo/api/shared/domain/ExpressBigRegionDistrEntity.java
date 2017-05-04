package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递大区与行政区域映射关系实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-25 上午10:29:28 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-25 上午10:29:28
 * @since
 * @version
 */
public class ExpressBigRegionDistrEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -7076218898797550553L;
    
    /**
     * 组织CODE.
     */
    private String orgCode;
    
    /**
     * 组织名称.
     */
    private String orgName;

    /**
     * 行政区域CODE.
     */
    private String districtCode;
    
    /**
     * 行政区域名称.
     */
    private String districtName;

    /**
     * 是否启用.
     */
    private String active;

    /**
     * 数据版本.
     */
    private Long versionNo;
    
    /**
     * 省份编码.
     */
    private String provCode;
    
    /**
     * 省份名称.
     */
    private String provName;
    
    /**
     * 城市编码.
     */
    private String cityCode;
    
    /**
     * 城市名称.
     */
    private String cityName;
    
    /**
     * 区县编码.
     */
    private String countyCode;
    
    /**
     * 区县名称.
     */
    private String countyName;
    
    /**
     * 行政区域编码列表
     */
    private List<String> districtCodeList;
    
    public List<String> getDistrictCodeList() {
		return districtCodeList;
	}



	public void setDistrictCodeList(List<String> districtCodeList) {
		this.districtCodeList = districtCodeList;
	}



	/**
     * 获取 组织名称.
     *
     * @return  the orgName
     */
    public String getOrgName() {
        return orgName;
    }


    
    /**
     * 设置 组织名称.
     *
     * @param orgName the orgName to set
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }


    /**
     * 获取 行政区域名称.
     *
     * @return  the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    
    /**
     * 设置 行政区域名称.
     *
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 获取 组织CODE.
     *
     * @return the 组织CODE
     */
    public String getOrgCode() {
	 
        return orgCode;
    }

    /**
     * 设置 组织CODE.
     *
     * @param orgCode the new 组织CODE
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取 行政区域CODE.
     *
     * @return the 行政区域CODE
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * 设置 行政区域CODE.
     *
     * @param districtCode the new 行政区域CODE
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * 获取 是否启用.
     *
     * @return the 是否启用
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 是否启用.
     *
     * @param active the new 是否启用
     */
    public void setActive(String active) {
        this.active = active;
    }

    /**
     * 获取 数据版本.
     *
     * @return the 数据版本
     */
    public Long getVersionNo() {
        return versionNo;
    }

    /**
     * 设置 数据版本.
     *
     * @param versionNo the new 数据版本
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }

    
    /**
     * 获取 省份编码.
     *
     * @return  the provCode
     */
    public String getProvCode() {
        return provCode;
    }

    
    /**
     * 设置 省份编码.
     *
     * @param provCode the provCode to set
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    
    /**
     * 获取 省份名称.
     *
     * @return  the provName
     */
    public String getProvName() {
        return provName;
    }

    
    /**
     * 设置 省份名称.
     *
     * @param provName the provName to set
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }

    
    /**
     * 获取 城市编码.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }

    
    /**
     * 设置 城市编码.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    
    /**
     * 获取 城市名称.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }

    
    /**
     * 设置 城市名称.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
    /**
     * 获取 区县编码.
     *
     * @return  the countyCode
     */
    public String getCountyCode() {
        return countyCode;
    }

    
    /**
     * 设置 区县编码.
     *
     * @param countyCode the countyCode to set
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    
    /**
     * 获取 区县名称.
     *
     * @return  the countyName
     */
    public String getCountyName() {
        return countyName;
    }

    
    /**
     * 设置 区县名称.
     *
     * @param countyName the countyName to set
     */
    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }
    
    
    
}