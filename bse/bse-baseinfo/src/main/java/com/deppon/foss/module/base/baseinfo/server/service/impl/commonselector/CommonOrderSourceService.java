package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonOrderSourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonOrderSourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonOrderSourceEntity;
/**
 * 公共查询组件--“订单来源信息”的数据库对应数据访问Service接口
 * @author 187862-dujunhui
 * @date 2014-9-22 下午3:57:13
 */
public class CommonOrderSourceService implements ICommonOrderSourceService {
	
	
	/**
	 * 订单来源信息DAO
	 */
	private ICommonOrderSourceDao commonOrderSourceDao;
	
	public void setCommonOrderSourceDao(ICommonOrderSourceDao commonOrderSourceDao) {
		this.commonOrderSourceDao = commonOrderSourceDao;
	}

	/** 
	 * <p>(根据传入对象查询符合条件所有订单来源)</p> 
	 * @author 187862
	 * @date 2014-9-22 下午3:58:46
	 * @param entity
	 * @return 
	 */
	@Override
	public List<CommonOrderSourceEntity> searchOrderSourceByCondition(
			CommonOrderSourceEntity entity, int limit, int start) {
		return commonOrderSourceDao.queryOrderSourceListByCondition(entity,limit, start);
	}

	/** 
	 * <p>(统计总记录数 )</p> 
	 * @author 187862
	 * @date 2014-9-22 下午3:58:46
	 * @param entity
	 * @return 
	 */
	@Override
	public Long countOrderSourceByCondition(CommonOrderSourceEntity entity) {
		return commonOrderSourceDao.countOrderSourceListByCondition(entity);
	}
 
}
