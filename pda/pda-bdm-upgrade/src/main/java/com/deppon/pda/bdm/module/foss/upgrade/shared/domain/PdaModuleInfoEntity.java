package com.deppon.pda.bdm.module.foss.upgrade.shared.domain;

/**
 * 
  * @ClassName PdaModuleInfo 
  * @Description TODO PDA模块信息
  * @author mt 
  * @date 2013-8-22 下午4:16:07
 */
public class PdaModuleInfoEntity {
	private String id;
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 设备号
	 */
	private String dvccoode;
	/**
	 * PDA当前版本号
	 */
	private String currentVersion;
	/**
	 * PDA系统最新版本号
	 */
	private String newestVersion;
	/**
	 * BDM员工最后更新工号
	 */
	private String lastUpdateCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getDvccoode() {
		return dvccoode;
	}
	public void setDvccoode(String dvccoode) {
		this.dvccoode = dvccoode;
	}
	public String getCurrentVersion() {
		return currentVersion;
	}
	public void setCurrentVersion(String currentVersion) {
		this.currentVersion = currentVersion;
	}
	public String getNewestVersion() {
		return newestVersion;
	}
	public void setNewestVersion(String newestVersion) {
		this.newestVersion = newestVersion;
	}
	public String getLastUpdateCode() {
		return lastUpdateCode;
	}
	public void setLastUpdateCode(String lastUpdateCode) {
		this.lastUpdateCode = lastUpdateCode;
	}
}
