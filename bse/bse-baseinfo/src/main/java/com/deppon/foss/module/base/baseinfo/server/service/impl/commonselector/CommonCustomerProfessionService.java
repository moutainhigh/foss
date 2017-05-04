package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonCustomerProfessionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonCustomerProfessionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonCustomerProfessionEntity;
/**
 * 公共查询组件--“客户行业信息”的数据库对应数据访问Service接口
 * @author 187862-dujunhui
 * @date 2014-9-23 上午10:46:27
 */
public class CommonCustomerProfessionService implements ICommonCustomerProfessionService {
	
	/**
	 *客户行业信息DAO
	 */
	private ICommonCustomerProfessionDao commonCustomerProfessionDao;

	public void setCommonCustomerProfessionDao(
			ICommonCustomerProfessionDao commonCustomerProfessionDao) {
		this.commonCustomerProfessionDao = commonCustomerProfessionDao;
	}

	/** 
	 * <p>(根据传入对象查询符合条件所有客户行业)</p> 
	 * @author 187862
	 * @date 2014-9-23 上午10:47:12
	 * @param entity
	 * @return 
	 */
	@Override
	public List<CommonCustomerProfessionEntity> searchCustomerProfessionByCondition(
			CommonCustomerProfessionEntity entity, int limit, int start) {
		return commonCustomerProfessionDao.queryCustomerProfessionListByCondition(entity,limit, start);
	}

	/** 
	 * <p>(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-9-23 上午10:47:12
	 * @param entity
	 * @return 
	 */
	@Override
	public Long countCustomerProfessionByCondition(CommonCustomerProfessionEntity entity) {
		return commonCustomerProfessionDao.countCustomerProfessionListByCondition(entity);
	}
 
}
