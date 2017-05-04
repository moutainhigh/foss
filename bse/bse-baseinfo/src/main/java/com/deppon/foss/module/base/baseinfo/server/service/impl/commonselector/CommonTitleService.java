package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonTitleDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTitleService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity;
/**
 * 公共查询组件--“岗位信息”的数据库对应数据访问Service接口
 * @author 187862-dujunhui
 * @date 2014-08-08 上午8:23:13
 */
public class CommonTitleService implements ICommonTitleService {
	
	
	/**
	 * 职位信息DAO
	 */
	private ICommonTitleDao commonTitleDao;
	
	/**
	 * @param commonTitleDao the commonTitleDao to set
	 */
	public void setCommonTitleDao(ICommonTitleDao commonTitleDao) {
		this.commonTitleDao = commonTitleDao;
	}

	/** 
	 * <p>TODO(根据传入对象查询符合条件所有职位信息)</p> 
	 * @author 187862
	 * @date 2014-8-8 上午8:22:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTitleService#searchTitleByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity)
	 */
	@Override
	public List<CommonTitleEntity> searchTitleByCondition(
			CommonTitleEntity entity, int limit, int start) {
		// TODO Auto-generated method stub
		return commonTitleDao.queryTitleListByCondition(entity,limit, start);
	}

	/** 
	 * <p>TODO(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-8-8 上午8:22:03
	 * @param orgSearchCondtion
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonTitleService#countTitleByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonTitleEntity)
	 */
	@Override
	public Long countTitleByCondition(CommonTitleEntity entity) {
		// TODO Auto-generated method stub
		return commonTitleDao.countTitleListByCondition(entity);
	}
 

}
