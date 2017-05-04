package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * TODO(违禁品实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-15 上午10:54:41,content:TODO </p>
 * @author chengang
 * @date 2013-3-15 上午10:54:41
 * @since
 * @version
 */
public class ProhibitedGoodsEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 违禁品类型
	 */
	private String danType;
	
	/**
	 * 违禁品名称
	 */
	private String danName;
	
	/**
	 * 违禁品等级
	 */
	private String danLevel;
	
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 状态
	 */
	private String operFlag;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 最后更新时间
	 */
	private Date updDate;
	
	/**
	 * 版本号
	 */
	private String version;

	public String getDanType() {
		return danType;
	}

	public void setDanType(String danType) {
		this.danType = danType;
	}

	public String getDanName() {
		return danName;
	}

	public void setDanName(String danName) {
		this.danName = danName;
	}

	public String getDanLevel() {
		return danLevel;
	}

	public void setDanLevel(String danLevel) {
		this.danLevel = danLevel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
