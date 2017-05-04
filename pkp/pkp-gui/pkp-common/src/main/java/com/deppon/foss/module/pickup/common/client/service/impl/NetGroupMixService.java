/**
 * 
 */
package com.deppon.foss.module.pickup.common.client.service.impl;

import org.mybatis.guice.transactional.Transactional;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.NetGroupMixEntity;
import com.deppon.foss.module.pickup.common.client.dao.INetGroupMixDao;
import com.deppon.foss.module.pickup.common.client.service.INetGroupMixService;
import com.google.inject.Inject;

/**
 * @author 026123-foss-lifengteng
 *
 */
public class NetGroupMixService  implements INetGroupMixService {
	
	@Inject
	INetGroupMixDao  netGroupMixDao;
	
	/**
	 * 
	 * 功能：插条记录
	 * @param: orgInfo
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void addNetGroupMix(NetGroupMixEntity netGroup) {
		netGroupMixDao.addNetGroupMix(netGroup);

	}
	
	/**
	 * 
	 * 功能：更新条记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Transactional
	@Override
	public void updateNetGroupMix(NetGroupMixEntity netGroup) {
		netGroupMixDao.updateNetGroupMix(netGroup);

	}
	
	/**
	 * 
	 * 功能：新增或更新记录
	 * @param:
	 * @return void
	 * @since:1.6
	 */
	@Override
	public void saveOrUpdate(NetGroupMixEntity netGroup) {
		if(!netGroupMixDao.addNetGroupMix(netGroup)){
			netGroupMixDao.updateNetGroupMix(netGroup);
		}
	}

	/**
	 * @param netGroupEntity
	 */
	public void delete(NetGroupMixEntity netGroupEntity) {
		netGroupMixDao.delete(netGroupEntity);
		
	}

}