package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonCustomerDegreeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerDegreeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerDegreeEntity;

/**
 * 公共查询组件--“客户等级信息”的数据库对应数据访问Service接口
 * @author 187862-dujunhui
 * @date 2014-9-23 上午8:38:22
 */
public class CommonCustomerDegreeService implements ICommonCustomerDegreeService {
	
	
	/**
	 *客户等级信息DAO
	 */
	private ICommonCustomerDegreeDao commonCustomerDegreeDao;

	public void setCommonCustomerDegreeDao(
			ICommonCustomerDegreeDao commonCustomerDegreeDao) {
		this.commonCustomerDegreeDao = commonCustomerDegreeDao;
	}

	/** 
	 * <p>(根据传入对象查询符合条件所有客户等级)</p> 
	 * @author 187862
	 * @date 2014-9-23 上午8:39:08
	 * @param entity
	 * @return 
	 */
	@Override
	public List<CommonCustomerDegreeEntity> searchCustomerDegreeByCondition(
			CommonCustomerDegreeEntity entity, int limit, int start) {
		return commonCustomerDegreeDao.queryCustomerDegreeListByCondition(entity,limit, start);
	}

	/** 
	 * <p>(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-9-23 上午8:39:08
	 * @param entity
	 * @return 
	 */
	@Override
	public Long countCustomerDegreeByCondition(CommonCustomerDegreeEntity entity) {
		return commonCustomerDegreeDao.countCustomerDegreeListByCondition(entity);
	}
 
}
