package com.deppon.pda.bdm.module.foss.delivery.shared.domain;

import java.io.Serializable;

public class RebackBillingEntity  implements Serializable{
	/**  
	 * @Fields serialVersionUID : TODO 
	 */
	private static final long serialVersionUID = 1L;
	//    新加的接口上传的字段，目的站：
     private String  destDeptCode;
    //  收入部门：
     private String deptCode;
     //  原单号：
     private String  oldWblCode;
     //　新单号:
     private String newWblCode;

	public String getDestDeptCode() {
		return destDeptCode;
	}
	public void setDestDeptCode(String destDeptCode) {
		this.destDeptCode = destDeptCode;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getOldWblCode() {
		return oldWblCode;
	}
	public void setOldWblCode(String oldWblCode) {
		this.oldWblCode = oldWblCode;
	}
	public String getNewWblCode() {
		return newWblCode;
	}
	public void setNewWblCode(String newWblCode) {
		this.newWblCode = newWblCode;
	}
     
     
     
     
}
