/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.dao;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;

/**
 * @author 026123-foss-lifengteng
 *
 */
public interface INetGroupMixDao {

	/**
	 * @param netGroup
	 */
	boolean addNetGroupMix(NetGroupMixEntity netGroup);

	/**
	 * @param netGroup
	 */
	void updateNetGroupMix(NetGroupMixEntity netGroup);

	/**
	 * @param netGroupEntity
	 */
	void delete(NetGroupMixEntity netGroupEntity);

}
