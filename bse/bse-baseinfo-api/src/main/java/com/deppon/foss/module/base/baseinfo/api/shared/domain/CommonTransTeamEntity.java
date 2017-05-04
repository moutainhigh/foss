package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * TODO(Entity实体)
 * @author 187862-dujunhui
 * @date 2014-8-13 上午9:37:41
 * @since
 * @version
 */
public class CommonTransTeamEntity extends BaseEntity {

	/**
	 * TODO（序列化）
	 */
	private static final long serialVersionUID = 3294375553459355448L;
	
	/**
	 * 部门编码
	 */
	private String orgCode;
	/**
	 * 部门名称
	 */
	private String orgName;
	/**
	 * 车队组编码
	 */
	private String transTeamCode;
	/**
	 * 车队组名称
	 */
	private String transTeamName;
	/**
	 * 是否有效
	 */
	private String active;

	/**
	 * @return  the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return  the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return  the transTeamCode
	 */
	public String getTransTeamCode() {
		return transTeamCode;
	}
	/**
	 * @param transTeamCode the transTeamCode to set
	 */
	public void setTransTeamCode(String transTeamCode) {
		this.transTeamCode = transTeamCode;
	}
	/**
	 * @return  the transTeamName
	 */
	public String getTransTeamName() {
		return transTeamName;
	}
	/**
	 * @param transTeamName the transTeamName to set
	 */
	public void setTransTeamName(String transTeamName) {
		this.transTeamName = transTeamName;
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
