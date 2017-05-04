/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface INetGroupMixService {
	
	/**
     * 插条记录
     */
	void addNetGroupMix(NetGroupMixEntity netGroup);
	/**
	 * 更新条记录
	 */
	void updateNetGroupMix(NetGroupMixEntity netGroup);
	/**
	 * 新增或更新记录
	 */
	void saveOrUpdate(NetGroupMixEntity netGroup);
	/**
	 * @param netGroupEntity
	 */
	void delete(NetGroupMixEntity netGroupEntity) ;
}
