package com.deppon.foss.module.transfer.scheduling.api.shared.dto;
/**
 * 信息部名称和编码用于给结算传值
 * @author 310248
 *
 */
public class OrgEntity {
     //信息部名称
	private String applyPath;
	
	//信息部编码
	private String ministryinformationCode;

	public String getApplyPath() {
		return applyPath;
	}

	public void setApplyPath(String applyPath) {
		this.applyPath = applyPath;
	}

	public String getMinistryinformationCode() {
		return ministryinformationCode;
	}

	public void setMinistryinformationCode(String ministryinformationCode) {
		this.ministryinformationCode = ministryinformationCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ministryinformationCode == null) ? 0
						: ministryinformationCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrgEntity other = (OrgEntity) obj;
		if (ministryinformationCode == null) {
			if (other.ministryinformationCode != null)
				return false;
		} else if (!ministryinformationCode
				.equals(other.ministryinformationCode))
			return false;
		return true;
	}

}