package com.deppon.pda.bdm.module.foss.upgrade.shared.vo;

import java.io.File;

  
/**
 * TODO(查询本地数据Dto)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-6-13 上午9:51:56,content:TODO </p>
 * @author chengang
 * @date 2013-6-13 上午9:51:56
 * @since
 * @version
 */

public class QueryLocalListDto {
	private String flag;
	private String dateVer;
	private String currVer;
	private String deptCode;
	private File file;
	
	private String userCode;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getDateVer() {
		return dateVer;
	}
	public void setDateVer(String dateVer) {
		this.dateVer = dateVer;
	}
	public String getCurrVer() {
		return currVer;
	}
	public void setCurrVer(String currVer) {
		this.currVer = currVer;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	
	
}
