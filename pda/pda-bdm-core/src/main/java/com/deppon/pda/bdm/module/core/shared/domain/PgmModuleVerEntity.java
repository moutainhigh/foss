package com.deppon.pda.bdm.module.core.shared.domain;

import java.util.List;

/**
 * 
  * @ClassName PgmModuleVerEntity 
  * @Description TODO PDA模块版本升级记录 
  * @author mt 
  * @date 2013-8-22 下午2:42:10
 */
public class PgmModuleVerEntity extends DomainEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 42206254356030231L;
	private List<ModuleVerEntity> moduleVerEntity;
	
	public List<ModuleVerEntity> getModuleVerEntity() {
		return moduleVerEntity;
	}
	public void setModuleVerEntity(List<ModuleVerEntity> moduleVerEntity) {
		this.moduleVerEntity = moduleVerEntity;
	}
}