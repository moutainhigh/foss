package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.Date;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(SecurityTfrMotorcade的实体类)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:16:35
 * @since
 * @version
 */
public class SecurityTfrMotorcadeEntity extends BaseEntity {
	
	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 6993366585989655618L;

	private String virtualCode;
	private String securityCode;
	private String transcenterCode;
	private String motorcadeCode;
	private String active;
	
	private Date createDate;// 创建时间
	private String createUser;// 创建人
	private Date modifyDate;// 修改时间
	private String modifyUser;// 修改人
	/**
	 * 保安组名称
	 */
	private String securityName;
	
	/**
	 *外场名称 
	 */
	private String transcenterName;
	
	/**
	 * 车队名称
	 */
	private String motorcadeNmae;
	
	
	
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
	public String getTranscenterName() {
		return transcenterName;
	}
	public void setTranscenterName(String transcenterName) {
		this.transcenterName = transcenterName;
	}
	public String getMotorcadeNmae() {
		return motorcadeNmae;
	}
	public void setMotorcadeNmae(String motorcadeNmae) {
		this.motorcadeNmae = motorcadeNmae;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	/**
	 * @return  the virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}
	/**
	 * @param virtualCode the virtualCode to set
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}
	/**
	 * @return  the securityCode
	 */
	public String getSecurityCode() {
		return securityCode;
	}
	/**
	 * @param securityCode the securityCode to set
	 */
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	/**
	 * @return  the transcenterCode
	 */
	public String getTranscenterCode() {
		return transcenterCode;
	}
	/**
	 * @param transcenterCode the transcenterCode to set
	 */
	public void setTranscenterCode(String transcenterCode) {
		this.transcenterCode = transcenterCode;
	}
	/**
	 * @return  the motorcadeCode
	 */
	public String getMotorcadeCode() {
		return motorcadeCode;
	}
	/**
	 * @param motorcadeCode the motorcadeCode to set
	 */
	public void setMotorcadeCode(String motorcadeCode) {
		this.motorcadeCode = motorcadeCode;
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
