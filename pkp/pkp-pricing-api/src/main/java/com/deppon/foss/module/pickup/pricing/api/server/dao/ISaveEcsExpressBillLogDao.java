package com.deppon.foss.module.pickup.pricing.api.server.dao;

import java.util.Map;

/**
 * FOSS提供该悟空计费接口记录计算日志
 * @author Foss-308595-GELL
 *
 */
public interface ISaveEcsExpressBillLogDao {
	
	void saveEcsBillLog(Map ecsBillLog);

}
