package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 快递代理理网点运价方案实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 上午9:50:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 上午9:50:19
 * @since
 * @version
 */
public class OutbranchPlanEntity extends BaseEntity{
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     *快递代理运价方案ID.
     */
    private String expressPartbussPlanId;

    /**
     快递代理递代理公司网点CODE.
     */
    private String outerBranchCode;
    
    /**
    快递代理快递代理公司网点名称（扩展）.
     */
    private String outerBranchName;

    /**
     * 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则.
     */
    private String billType;

    /**
     * 数据状态.
     */
    private String active;

    /**
     * 数据版本.
     */
    private Long versionNo;

    /**
     * 创建组织.
     */
    private String createOrgCode;

    /**
     * 更新组织.
     */
    private String modifyOrgCode;

    /**
     * 币种.
     */
    private String currencyCode;
    
    /**
     * 省份名称（扩展）.
     */
    private String provName;
    
    /**
     * 城市名称（扩展）.
     */
    private String cityName;
    /**
     * 城市编码（扩展）.
     */
    private String cityCode;
    
    /**
     * 区县名称（扩展）.
     */
    private String districtName;
    
    /**
     * 区县编码（扩展）.
     */
    private String districtCode;
    
    /**
     * 价格明细.
     */
    private List<OubrPlanDetailEntity> priceDetail;
    
    /**
     * 开单  日期.
     */
    private Date billDate;
    
    
    /**
     *快递代理 快递代理公司网点名称（扩展）.
     *
     * @return  the outerBranchName
     */
    public String getOuterBranchName() {
        return outerBranchName;
    }
    
    /**
     * 获取 城市编码（扩展）.
     *
     * @return  the cityCode
     */
    public String getCityCode() {
        return cityCode;
    }


    
    /**
     * 设置 城市编码（扩展）.
     *
     * @param cityCode the cityCode to set
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }


    
    /**
     * 获取 区县编码（扩展）.
     *
     * @return  the districtCode
     */
    public String getDistrictCode() {
        return districtCode;
    }


    
    /**
     * 设置 区县编码（扩展）.
     *
     * @param districtCode the districtCode to set
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }


    /**
     快递代理置 快递代理公司网点名称（扩展）.
     *
     * @param outerBranchName the outerBranchName to set
     */
    public void setOuterBranchName(String outerBranchName) {
        this.outerBranchName = outerBranchName;
    }

    
    /**
     * 获取 省份名称（扩展）.
     *
     * @return  the provName
     */
    public String getProvName() {
        return provName;
    }

    
    /**
     * 设置 省份名称（扩展）.
     *
     * @param provName the provName to set
     */
    public void setProvName(String provName) {
        this.provName = provName;
    }

    
    /**
     * 获取 城市名称（扩展）.
     *
     * @return  the cityName
     */
    public String getCityName() {
        return cityName;
    }

    
    /**
     * 设置 城市名称（扩展）.
     *
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
    /**
     * 获取 区县名称（扩展）.
     *
     * @return  the districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    
    /**
     * 设置 区县名称（扩展）.
     *
     * @param districtName the districtName to set
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
    快递代理获取 快递代理运价方案ID.
     *
     * @retu快递代理the 快递代理运价方案ID
     */
    public String getExpressPartbussPlanId() {
        return expressPartbussPlanId;
    }

    /**
  快递代理* 设置 快递代理运价方案ID.
     *
     * @param expressPartbussPlanId快递代理e new 快递代理运价方案ID
     */
    public void setExpressPartbussPlanId(String expressPartbussPlanId) {
        this.expressPartbussPlanId = expressPartbussPlanId;
    }

    /**
快递代理  * 获取 快递代理公司网点CODE.
     *
     * @快递代理urn the 快递代理公司网点CODE
     */
    public String getOuterBranchCode() {
        return outerBranchCode;
    }

    /**快递代理    * 设置 快递代理公司网点CODE.
     *
     * @param outerBranch快递代理e the new 快递代理公司网点CODE
     */
    public void setOuterBranchCode(String outerBranchCode) {
        this.outerBranchCode = outerBranchCode;
    }

    /**
     * 获取 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则.
     *
     * @return the 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则
     */
    public String getBillType() {
        return billType;
    }

    /**
     * 设置 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则.
     *
     * @param billType the new 计费方式分为：FX：按照区间划分，固定收费 AW：步进价格规则
     */
    public void setBillType(String billType) {
        this.billType = billType;
    }

    /**
     * 获取 数据状态.
     *
     * @return the 数据状态
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置 数据状态.
     *
     * @param active the new 数据状态
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
     * 获取 创建组织.
     *
     * @return the 创建组织
     */
    public String getCreateOrgCode() {
        return createOrgCode;
    }

    /**
     * 设置 创建组织.
     *
     * @param createOrgCode the new 创建组织
     */
    public void setCreateOrgCode(String createOrgCode) {
        this.createOrgCode = createOrgCode;
    }

    /**
     * 获取 更新组织.
     *
     * @return the 更新组织
     */
    public String getModifyOrgCode() {
        return modifyOrgCode;
    }

    /**
     * 设置 更新组织.
     *
     * @param modifyOrgCode the new 更新组织
     */
    public void setModifyOrgCode(String modifyOrgCode) {
        this.modifyOrgCode = modifyOrgCode;
    }

    /**
     * 获取 币种.
     *
     * @return the 币种
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 设置 币种.
     *
     * @param currencyCode the new 币种
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    
    /**
     * 获取 价格明细.
     *
     * @return the 价格明细
     */
    public List<OubrPlanDetailEntity> getPriceDetail() {
        return priceDetail;
    }

    
    /**
     * 设置 价格明细.
     *
     * @param priceDetail the new 价格明细
     */
    public void setPriceDetail(List<OubrPlanDetailEntity> priceDetail) {
        this.priceDetail = priceDetail;
    }


    
    /**
     * 获取 开单  日期.
     *
     * @return the 开单  日期
     */
    public Date getBillDate() {
        return billDate;
    }


    
    /**
     * 设置 开单  日期.
     *
     * @param billDate the new 开单  日期
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }
    
    
}