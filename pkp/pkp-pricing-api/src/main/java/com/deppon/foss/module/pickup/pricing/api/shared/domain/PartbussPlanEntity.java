package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * 快递代理理公司运价方案实体类
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2013-7-18 上午10:22:56 </p>
 * @author 094463-foss-xieyantao
 * @date 2013-7-18 上午10:22:56
 * @since
 * @version
 */
public class PartbussPlanEntity extends BaseEntity{
	
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    private static final String YES="是";
    
    /**
     * 
     */
    private static final String NO="否";
    /**
     *快递代理公司.
     */
    private String expressPartbussCode;
    
    /**
     快递代理递代理公司名称.
     */
    private String expressPartbussName;

    /**
     * 运价编号.
     */
    private String priceNo;

    /**
     * 配载部门.
     */
    private String loadOrgCode;
    
    /**
     * 配载部门名称.
     */
    private String loadOrgName;
    /**
     * 生效日期.
     */
    private Date beginTime;

    /**
     * 结束日期.
     */
    private Date endTime;

    /**
     * 数据状态.
     */
    private String active;

    /**
     * 描述.
     */
    private String description;
    
    /**
     * 描述.
     */
    private String descNote;

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
     * 创建人名称.
     */
    private String createUserName;
    
    /**
     * 修改人.
     */
    private String modifyUserName;
    
    /**
     * 业务日期.
     */
    private Date billDate;
    
    /**
     * 是否当前版本.
     */
    private String currentUsedVersion;
    
    /**
    快递代理快递代理网点运价方案实体类集合.
     */
    private List<OutbranchPlanEntity> entityList;
    
    /**
     * 重量取数规则 add by hehaisu
     */
    private String weightRule;
    
    /**
     *快递代理 快递代理网点运价方案实体类集合.
     *
     * @return  the entityList
     */
    public List<OutbranchPlanEntity> getEntityList() {
        return entityList;
    }


    
    /**
     快递代理置 快递代理网点运价方案实体类集合.
     *
     * @param entityList the entityList to set
     */
    public void setEntityList(List<OutbranchPlanEntity> entityList) {
        this.entityList = entityList;
    }


    /**
     * 获取 描述.
     *
     * @return  the descNote
     */
    public String getDescNote() {
        return descNote;
    }

    
    /**
     * 设置 描述.
     *
     * @param descNote the descNote to set
     */
    public void setDescNote(String descNote) {
        this.descNote = descNote;
    }

    /**
     * 获得是否当前版本.
     *
     * @return the 是否当前版本
     */
    public String getCurrentUsedVersion() {
	Date now = new Date();
	if (StringUtils.isNotBlank(currentUsedVersion)) {// 查询条件时用到
	    return currentUsedVersion;// Y 或 N
	} else {
	    if(StringUtils.equals(FossConstants.NO, this.getActive())){
		currentUsedVersion = NO;
		
		return currentUsedVersion;
	    }else {
		if (this.getBeginTime() != null && this.getEndTime() != null) {
		    if (now.compareTo(this.getBeginTime()) >= 0&& now.compareTo(this.getEndTime()) < 0) {
			currentUsedVersion = YES;
			return currentUsedVersion;
		    } else {
			currentUsedVersion = NO;
			return currentUsedVersion;
		    }
		}else {
		    currentUsedVersion = "";
		    return currentUsedVersion;
		}
	    }
	}
    }

    /**
     * 设置是否当前版本.
     *
     * @param currentUsedVersion the new 是否当前版本
     */
    public void setCurrentUsedVersion(String currentUsedVersion) {
        this.currentUsedVersion = currentUsedVersion;
    }

    
    /**
     * 获取 业务日期.
     *
     * @return  the billDate
     */
    public Date getBillDate() {
        return billDate;
    }



    
    /**
     * 设置 业务日期.
     *
     * @param billDate the billDate to set
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }



    /**
    快递代理获取 快递代理公司名称.
     *
     * @return  the expressPartbussName
     */
    public String getExpressPartbussName() {
        return expressPartbussName;
    }


    
    /**
   快递代理 设置 快递代理公司名称.
     *
     * @param expressPartbussName the expressPartbussName to set
     */
    public void setExpressPartbussName(String expressPartbussName) {
        this.expressPartbussName = expressPartbussName;
    }


    /**
     * 获取 配载部门名称.
     *
     * @return  the loadOrgName
     */
    public String getLoadOrgName() {
        return loadOrgName;
    }

    
    /**
     * 设置 配载部门名称.
     *
     * @param loadOrgName the loadOrgName to set
     */
    public void setLoadOrgName(String loadOrgName) {
        this.loadOrgName = loadOrgName;
    }

    
    /**
     * 获取 创建人名称.
     *
     * @return  the createUserName
     */
    public String getCreateUserName() {
        return createUserName;
    }

    
    /**
     * 设置 创建人名称.
     *
     * @param createUserName the createUserName to set
     */
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    
    /**
     * 获取 修改人.
     *
     * @return  the modifyUserName
     */
    public String getModifyUserName() {
        return modifyUserName;
    }

    
    /**
     * 设置 修改人.
     *
     * @param modifyUserName the modifyUserName to set
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
    }

    /**
  快递代理* 获取 快递代理公司.
     *
     * @re快递代理n the 快递代理公司
     */
    public String getExpressPartbussCode() {
        return expressPartbussCode;
    }

    /**
快递代理  * 设置 快递代理公司.
     *
     * @param expressPartbussCo快递代理the new 快递代理公司
     */
    public void setExpressPartbussCode(String expressPartbussCode) {
        this.expressPartbussCode = expressPartbussCode;
    }

    /**
     * 获取 运价编号.
     *
     * @return the 运价编号
     */
    public String getPriceNo() {
        return priceNo;
    }

    /**
     * 设置 运价编号.
     *
     * @param priceNo the new 运价编号
     */
    public void setPriceNo(String priceNo) {
        this.priceNo = priceNo;
    }

    /**
     * 获取 配载部门.
     *
     * @return the 配载部门
     */
    public String getLoadOrgCode() {
        return loadOrgCode;
    }

    /**
     * 设置 配载部门.
     *
     * @param loadOrgCode the new 配载部门
     */
    public void setLoadOrgCode(String loadOrgCode) {
        this.loadOrgCode = loadOrgCode;
    }

    /**
     * 获取 生效日期.
     *
     * @return the 生效日期
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置 生效日期.
     *
     * @param beginTime the new 生效日期
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取 结束日期.
     *
     * @return the 结束日期
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置 结束日期.
     *
     * @param endTime the new 结束日期
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
     * 获取 描述.
     *
     * @return the 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置 描述.
     *
     * @param description the new 描述
     */
    public void setDescription(String description) {
        this.description = description;
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
     * 获取 重量取数规则
     *
     * return the 重量取数规则
     */
	public String getWeightRule() {
		return weightRule;
	}


	/**
     * 设置 重量取数规则.
     *
     * @param heightGetRule the new 重量取数规则
     */
	public void setWeightRule(String weightRule) {
		this.weightRule = weightRule;
	}
    
    
}