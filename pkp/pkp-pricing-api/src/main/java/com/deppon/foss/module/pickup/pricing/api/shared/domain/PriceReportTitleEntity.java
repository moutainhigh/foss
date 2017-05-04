package com.deppon.foss.module.pickup.pricing.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 汽运价格报表表头信息实体
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:094463-foss-xieyantao,date:2014-1-9 上午11:02:19 </p>
 * @author 094463-foss-xieyantao
 * @date 2014-1-9 上午11:02:19
 * @since
 * @version
 */
public class PriceReportTitleEntity extends BaseEntity{

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1194079894202349672L;

    /**
     * 序号.
     */
    private Long serialNo;

    /**
     * 标题.
     */
    private String header;

    /**
     * 内容.
     */
    private String details;

    /**
     * 是否显示（Y/N）.
     */
    private String isShow;

    /**
     * 是否启用(Y/N).
     */
    private String active;

    /**
     * 版本号.
     */
    private Long versionNo;
    
    /**
     * 修改人名称.
     */
    private String modifyUserName;
    
    /**
     * 创建人名称.
     */
    private String createUserName;

    //===================新增'是否合伙人营业部'属性/20160919/lianhe/开始====================
    /**
     * 是否合伙人营业部(Y/N).
     */
    private String isPartner;
    
    public String getIsPartner() {
		return isPartner;
	}

	public void setIsPartner(String isPartner) {
		this.isPartner = isPartner;
	}
	//===================新增'是否合伙人营业部'属性/20160919/lianhe/截止====================

	/**
     * 获取 修改人名称.
     *
     * @return  the modifyUserName
     */
    public String getModifyUserName() {
        return modifyUserName;
    }


    
    /**
     * 设置 修改人名称.
     *
     * @param modifyUserName the modifyUserName to set
     */
    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName;
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
     * 获取 序号.
     *
     * @return  the serialNo
     */
    public Long getSerialNo() {
        return serialNo;
    }

    
    /**
     * 设置 序号.
     *
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    
    /**
     * 获取 标题.
     *
     * @return  the header
     */
    public String getHeader() {
        return header;
    }

    
    /**
     * 设置 标题.
     *
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    
    /**
     * 获取 内容.
     *
     * @return  the details
     */
    public String getDetails() {
        return details;
    }

    
    /**
     * 设置 内容.
     *
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    
    /**
     * 获取 是否显示（Y/N）.
     *
     * @return  the isShow
     */
    public String getIsShow() {
        return isShow;
    }

    
    /**
     * 设置 是否显示（Y/N）.
     *
     * @param isShow the isShow to set
     */
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    
    /**
     * 获取 是否启用(Y/N).
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }

    
    /**
     * 设置 是否启用(Y/N).
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }

    
    /**
     * 获取 版本号.
     *
     * @return  the versionNo
     */
    public Long getVersionNo() {
        return versionNo;
    }

    
    /**
     * 设置 版本号.
     *
     * @param versionNo the versionNo to set
     */
    public void setVersionNo(Long versionNo) {
        this.versionNo = versionNo;
    }
    
}