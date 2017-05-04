package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;

public interface ICommonRoleDao {

	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lifanghong
     * @date 2013-04-26
     */
    List<CommonRoleEntity> queryRoleByEntity(CommonRoleEntity commonentity,
	    int start, int limit);
	
    /**
     * 查询queryRoleByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lijun
     * @date 2013-04-26
     */
    long queryRoleByEntityCount(CommonRoleEntity commonentity);
	
	
    
	


}
