package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IPositionDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonPositionService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PositionEntity;
/**
 * 职位 service接口
 * @author 130346-foss-lifanghong
 * @date 2014-4-29 下午4:10:24
 */
public class CommonPositionService implements ICommonPositionService {
	private IPositionDao positionDao;
	
	
	public void setPositionDao(IPositionDao positionDao) {
		this.positionDao = positionDao;
	}
	/**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * @author 130346-foss-lifanghong
     * @date 2014-4-29 下午4:10:24
     */
	@Override
	public List<PositionEntity> queryPositionByEntitySelector(
			PositionEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		return positionDao.queryPositionByEntitySelector(entity, start, limit);
	}
	 /**
     * 模糊查询--查询符合条件的总数
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * @author 130346-foss-lifanghong
     * @date 2014-4-29 下午4:10:24
     */
	@Override
	public long countPositionByEntitySelector(PositionEntity entity) {
		// TODO Auto-generated method stub
		return positionDao.countPositionByEntitySelector(entity);
	}

}
