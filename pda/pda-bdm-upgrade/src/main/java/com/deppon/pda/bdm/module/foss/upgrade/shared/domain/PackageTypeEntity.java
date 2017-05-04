package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 
 * TODO(包装类型实体)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-15 上午10:54:41,content:TODO </p>
 * @author chengang
 * @date 2013-3-15 上午10:54:41
 * @since
 * @version
 */
public class PackageTypeEntity extends BaseEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 价格
	 */
	private Long price;
	
	/**
	 * 包装类型
	 */
	private String type;
	
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

	

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
