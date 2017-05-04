
package com.deppon.foss.module.transfer.pda.api.shared.dto;

import java.io.Serializable;

/**
 * FOSS根据PDA传过来的运单号和当前所在转运场，查找出该运单走货路径中的下一转运场，并把结果返回给PDA。
 * @author dp-zwd 200968
 * @date 2015-7-24 
 */
public class PackagePathLoaderDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 17636689475688464L;
	/**
	 * 目的部门code
	 */
	private String objectiveOrgCode ;
	/**
	 * 目的部门name
	 */
	private String objectiveOrgName ;
	
	public String getObjectiveOrgCode() {
		return objectiveOrgCode;
	}
	public void setObjectiveOrgCode(String objectiveOrgCode) {
		this.objectiveOrgCode = objectiveOrgCode;
	}
	public String getObjectiveOrgName() {
		return objectiveOrgName;
	}
	public void setObjectiveOrgName(String objectiveOrgName) {
		this.objectiveOrgName = objectiveOrgName;
	}
	
	
	
}