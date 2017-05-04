package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonRoleEntity;

public interface ICommonRoleService {

  
	
    /**
     * 根据entity模糊查询，
     * 
     * @author 087584-foss-lifanghong
     * @date 2012-11-2 下午4:11:7
     */
    List<CommonRoleEntity> queryRoleByEntity(
	    CommonRoleEntity commonentity,int start, int limit);
	
    /**
     * 查询queryRoleByEntity返回的记录总数,用于分页
     * 
     * @author 087584-foss-lifanghong
     * @date 2012-11-2 下午4:11:7
     */
    long queryRoleByEntityCount(CommonRoleEntity commonentity);
		
	


}
