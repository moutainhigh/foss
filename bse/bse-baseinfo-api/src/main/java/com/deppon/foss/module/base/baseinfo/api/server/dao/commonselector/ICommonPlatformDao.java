package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;

public interface ICommonPlatformDao {
	/**
     * 
     * <p>
     * 按条件查询
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:21 PM
     * @param platform
     * @param start
     * @param limit
     * @return
     * @see
     */

	List<PlatformEntity> queryPlatformListByCondition(PlatformEntity platform,
			int start, int limit);

    /**
     * 
     * <p>
     * 按条件查询的总数
     * </p>
     * 
     * @author foss-zhujunyong
     * @date Mar 7, 2013 1:57:19 PM
     * @param platform
     * @return
     * @see
     */
	long countPlatformListByCondition(PlatformEntity platform);

}
