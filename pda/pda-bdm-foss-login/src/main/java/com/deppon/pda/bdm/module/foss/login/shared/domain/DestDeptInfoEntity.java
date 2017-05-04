package com.deppon.pda.bdm.module.foss.login.shared.domain;



public class DestDeptInfoEntity {
	private String deptCode;
	private String deptName;
	private String deptType;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		System.out.println();
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	@Override
	public int hashCode() {
		return deptCode == null ? System.identityHashCode(this) : deptCode.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if(!(obj instanceof DestDeptInfoEntity)){
			return false;
		}
		final DestDeptInfoEntity o = (DestDeptInfoEntity)obj;
		if(this.getDeptCode().equals(o.getDeptCode())){
			return true;
		}
		return false;
	}

}
