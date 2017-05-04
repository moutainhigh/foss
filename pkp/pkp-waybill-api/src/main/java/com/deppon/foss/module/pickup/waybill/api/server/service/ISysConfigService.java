package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;

/**
 * 系统配置服务层接口
 * @author WangQianJin
 * @date 2013-4-26 上午10:43:16
 */
public interface ISysConfigService {

	/**
     * 
     * <p>通过主键查询系统配置</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param id
     * @return
     * @see
     */
    ConfigurationParamsEntity queryByPrimaryKey(String id);
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,根据confcode和orgcode一起查询</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param confCode
     * @param orgCode
     * @return
     * @see
     */
    ConfigurationParamsEntity queryByConfCode(String confCode, String orgCode);
    
    /**
     * 
     * <p>通过配置项标示查询系统配置,只根据confcode来查询</p> 
     * @author WangQianJin
     * @date 2013-4-26 上午10:43:16
     * @param confCode
     * @return 
     */
    ConfigurationParamsEntity queryByConfCode(String confCode);
    
    /**
     * 
     * <p>通过配置项集合查询系统配置</p> 
     * @author WangQianJin
     * @date 2012-10-24 下午1:45:48
     * @param confCodes
     * @param orgCode
     * @return
     * @see
     */
    List<ConfigurationParamsEntity> queryByConfCodeArray(String[] confCodes, String orgCode);
    
    /**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
    boolean addConfig(ConfigurationParamsEntity config);

	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	void updateConfig(ConfigurationParamsEntity config);

	/**
	 * 删除
	 * @param config
	 */
	void delete(ConfigurationParamsEntity config);
	
	
	/**
	 * 获取配置参数
	 * @author WangQianJin
	 * @param entity
	 * @return
	 */
	ConfigurationParamsEntity queryConfigurationParamsByEntity(String model,String config,String orgCode);
}
