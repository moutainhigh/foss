
package com.deppon.foss.module.base.baseinfo.api.shared.domain;
import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 特殊地址实体类.
 *
 * @author 何波
 * @date 2013-2-20 下午2:24:59
 * @since
 * @version
 */
public class ExhibitionKeywordEntity extends BaseEntity {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = -6216784102822021979L;

    /**
     * 货物类型.
     */
    private String type;


    /**
     * 省份代码.
     */
    private String provinceCode;

    /**
     * 城市代码.
     */
    private String cityCode;

    /**
     * 区县代码.
     */
    private String countyCode;

    /**
     * 展馆关键字
     */
    private String exhibitionKeyword;
    /**
     * 展馆详细地址
     */
    private String exhibitionAddress;
    
	/**
     * 是否作废标志.
     */
    private String active;


    /**
     * 获取 货物类型.
     *
     * @return type
     */
    public String getType() {
	return type;
    }

    /**
     * 设置货物类型.
     *
     * @param type the new 货物类型
     */
    public void setType(String type) {
	this.type = type;
    }

    /**
     * 获取 省份代码.
     *
     * @return provinceCode
     */
    public String getProvinceCode() {
	return provinceCode;
    }

    /**
     * 设置 省份代码.
     *
     * @param provinceCode the new 省份代码
     */
    public void setProvinceCode(String provinceCode) {
	this.provinceCode = provinceCode;
    }

    /**
     * 获取 城市代码.
     *
     * @return cityCode
     */
    public String getCityCode() {
	return cityCode;
    }

    /**
     * 设置 城市代码.
     *
     * @param cityCode the new 城市代码
     */
    public void setCityCode(String cityCode) {
	this.cityCode = cityCode;
    }

    /**
     * 获取 区县代码.
     *
     * @return countyCode
     */
    public String getCountyCode() {
	return countyCode;
    }

    /**
     * 设置 区县代码.
     *
     * @param countyCode the new 区县代码
     */
    public void setCountyCode(String countyCode) {
	this.countyCode = countyCode;
    }

    /**
     * 获取 展馆关键字.
     *
     * @return exhibitionKeyword
     */
    public String getExhibitionKeyword() {
	return exhibitionKeyword;
    }

    /**
     * 设置 展馆关键字.
     *
     * @param exhibitionKeyword the new 展馆关键字
     */
    public void setExhibitionKeyword(String exhibitionKeyword) {
	this.exhibitionKeyword = exhibitionKeyword;
    }
    /**
     * 获取展馆详细地址
     * 
     * @return exihibitionAddress
     */
    public String getExhibitionAddress() {
		return exhibitionAddress;
	}
    /**
     * 设置展馆详细地址
     *
     * @param exihibitionAddress
     */
    public void setExhibitionAddress(String exhibitionAddress) {
		this.exhibitionAddress = exhibitionAddress;
	}
    /**
     * 获取 是否作废标志.
     *
     * @return active
     */
    public String getActive() {
	return active;
    }

    /**
     * 设置 是否作废标志.
     *
     * @param active the new 是否作废标志
     */
    public void setActive(String active) {
	this.active = active;
    }

}