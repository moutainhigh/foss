package com.deppon.pda.bdm.module.foss.load.shared.domain.tallyload;

import java.util.List;
/**
 * 接驳点实体类
 * @ClassName GetPotEntity.java 
 * @Description 
 * @author 245955
 * @date 2015-4-28
 */
public class GetPotEntity {
	/**
	 * 员工工号
	 */
	private String userCode;
	/**
	 * 大部门编号
	 **/
	private String departMent;

	private List<PotlModel> potCode;
	
    
	public String getDepartMent() {
		return departMent;
	}

	public void setDepartMent(String departMent) {
		this.departMent = departMent;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<PotlModel> getPotCode() {
		return potCode;
	}

	public void setPotCode(List<PotlModel> potCode) {
		this.potCode = potCode;
	}
    
}
