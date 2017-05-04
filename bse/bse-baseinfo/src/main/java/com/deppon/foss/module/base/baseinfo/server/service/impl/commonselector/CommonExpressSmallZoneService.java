/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.server.service.impl.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressSmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonExpressSmallZoneService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;

/**
 *<p>Title: CommonExpressSmallZoneService</p>
 * <p>Description:快递收派小区公共选择器Service的实现类 </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public class CommonExpressSmallZoneService implements
		ICommonExpressSmallZoneService {
	/**
	 * 快递收派小区Dao
	 */
	private ICommonExpressSmallZoneDao commonExpressSmallZoneDao;
	/**
	 * @param commonExpressSmallZoneDao the commonExpressSmallZoneDao to set
	 */
	public void setCommonExpressSmallZoneDao(
			ICommonExpressSmallZoneDao commonExpressSmallZoneDao) {
		this.commonExpressSmallZoneDao = commonExpressSmallZoneDao;
	}
	/**
	 *<p>Title: queryCommonExpressSmallZoneList</p>
	 *<p>根据条件分页查询记录</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:19:39
	 * @param entity
	 * @param start
	 * @param limin
	 * @return
	 */
	@Override
	public List<CommonExpressSmallZoneEntity> queryCommonExpressSmallZoneList(
			CommonExpressSmallZoneEntity entity, int start, int limit) {
		if(null ==entity){
			entity =new CommonExpressSmallZoneEntity();
		}
		return commonExpressSmallZoneDao.queryCommonExpressSmallZoneList(entity, start, limit);
	}
	/**
	 *<p>Title: queryCommonExpressSmallZoneCount</p>
	 *<p>统计总记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:19:39
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCommonExpressSmallZoneCount(
			CommonExpressSmallZoneEntity entity) {
		if(null ==entity){
			entity =new CommonExpressSmallZoneEntity();
		}
		return commonExpressSmallZoneDao.queryCommonExpressSmallZoneCount(entity);
	}

}
