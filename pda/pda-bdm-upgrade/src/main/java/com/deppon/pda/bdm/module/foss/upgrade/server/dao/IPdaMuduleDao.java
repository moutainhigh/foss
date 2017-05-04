package com.deppon.pda.bdm.module.foss.upgrade.server.dao;

import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.PdaModuleInfoEntity;

  
/**
 * 
  * @ClassName IPdaMuduleDao 
  * @Description TODO 
  * @author mt 
  * @date 2013-8-22 下午4:12:44
 */
public interface IPdaMuduleDao {
	/**
	 * 
	* @Description: TODO 更新设备模块信息
	* @param parameter
	* @return void    
	* @author mt
	* @date 2013-8-22 下午4:13:27
	 */
	public void updatePdaModuleInfo(PdaModuleInfoEntity parameter);
}
