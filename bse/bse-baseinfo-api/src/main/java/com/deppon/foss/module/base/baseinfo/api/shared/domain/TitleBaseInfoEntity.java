package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(Entity实体)
 * @author 187862-dujunhui
 * @date 2014-8-8 下午1:42:41
 * @since
 * @version
 */
public class TitleBaseInfoEntity extends BaseEntity {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 外场编码
	 */
	private String transferCenterCode;
	/**
	 * 外场名称
	 */
	private String transferCenterName;
	/**
	 * 接收部门编码
	 */
	private String receiveDeptCode;
	/**
	 * 接收部门名称
	 */
	private String receiveDeptName;
	/**
	 * 接收岗位编码
	 */
	private String receiveTitleCode;
	/**
	 * 接收岗位编码
	 */
	private String receiveTitleName;
	/**
	 * 冗余字段
	 */
	private String remark;
	/**
	 * 版本号
	 */
	private Long versionNo;
	/**
	 * 是否有效
	 */
	private String active;
	/**
	 * @return  the transferCenterCode
	 */
	public String getTransferCenterCode() {
		return transferCenterCode;
	}
	/**
	 * @param transferCenterCode the transferCenterCode to set
	 */
	public void setTransferCenterCode(String transferCenterCode) {
		this.transferCenterCode = transferCenterCode;
	}
	/**
	 * @return  the transferCenterName
	 */
	public String getTransferCenterName() {
		return transferCenterName;
	}
	/**
	 * @param transferCenterName the transferCenterName to set
	 */
	public void setTransferCenterName(String transferCenterName) {
		this.transferCenterName = transferCenterName;
	}
	/**
	 * @return  the receiveDeptCode
	 */
	public String getReceiveDeptCode() {
		return receiveDeptCode;
	}
	/**
	 * @param receiveDeptCode the receiveDeptCode to set
	 */
	public void setReceiveDeptCode(String receiveDeptCode) {
		this.receiveDeptCode = receiveDeptCode;
	}
	/**
	 * @return  the receiveDeptName
	 */
	public String getReceiveDeptName() {
		return receiveDeptName;
	}
	/**
	 * @param receiveDeptName the receiveDeptName to set
	 */
	public void setReceiveDeptName(String receiveDeptName) {
		this.receiveDeptName = receiveDeptName;
	}
	/**
	 * @return  the receiveTitleCode
	 */
	public String getReceiveTitleCode() {
		return receiveTitleCode;
	}
	/**
	 * @param receiveTitleCode the receiveTitleCode to set
	 */
	public void setReceiveTitleCode(String receiveTitleCode) {
		this.receiveTitleCode = receiveTitleCode;
	}
	/**
	 * @return  the receiveTitleName
	 */
	public String getReceiveTitleName() {
		return receiveTitleName;
	}
	/**
	 * @param receiveTitleName the receiveTitleName to set
	 */
	public void setReceiveTitleName(String receiveTitleName) {
		this.receiveTitleName = receiveTitleName;
	}
	
	/**
	 * @return  the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * @return  the versionNo
	 */
	public Long getVersionNo() {
		return versionNo;
	}
	/**
	 * @param versionNo the versionNo to set
	 */
	public void setVersionNo(Long versionNo) {
		this.versionNo = versionNo;
	}
	/**
	 * @return  the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	

}
