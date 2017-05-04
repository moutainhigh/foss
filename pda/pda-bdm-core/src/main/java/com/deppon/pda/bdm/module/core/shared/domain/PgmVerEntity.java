package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.Date;

/**
 * 
  * @ClassName PgmVerEntity 
  * @Description PDA版本升级记录 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class PgmVerEntity extends DomainEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 42206254356030231L;
	/**
	 * 版本号
	 */
	private String pgmVer;
	/**
	 * 设备型号
	 */
	private String pdaModel;
	/**
	 * 指定更新机构
	 */
	private String updateDept;
	/**
	 * 生效时间
	 */
	private String activeTime;
	/**
	 * 上传时间
	 */
	private Date updDate;
	/**
	 * 上传用户工号
	 */
	private String userCode;
	/**
	 * 上传用户姓名
	 */
	private String userNm;
	/**
	 * 强制更新标识
	 */
	private String forcFlag;
	/**
	 * 备注
	 */
	private String remark;
	
	
	public String getPgmVer() {
		return pgmVer;
	}
	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}
	public String getPdaModel() {
		return pdaModel;
	}
	public void setPdaModel(String pdaModel) {
		this.pdaModel = pdaModel;
	}
	public String getUpdateDept() {
		return updateDept;
	}
	public void setUpdateDept(String updateDept) {
		this.updateDept = updateDept;
	}
	
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public Date getUpdDate() {
		return updDate;
	}
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getForcFlag() {
		return forcFlag;
	}
	public void setForcFlag(String forcFlag) {
		this.forcFlag = forcFlag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}