package com.deppon.pda.bdm.module.core.shared.domain;
/**
 * 
  * @ClassName ModuleVerEntity 
  * @Description TODO 模块版本实体
  * @author mt 
  * @date 2013-8-22 下午2:44:24
 */
public class ModuleVerEntity {
	/**
	 * 模块名称
	 */
	private String moduleName;
	/**
	 * 版本号
	 */
	private String pgmVer;
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getPgmVer() {
		return pgmVer;
	}
	public void setPgmVer(String pgmVer) {
		this.pgmVer = pgmVer;
	}
}
