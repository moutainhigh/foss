/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;

/**
 *<p>Title: ICommonExpressSmallZoneDao</p>
 * <p>Description: 快递收派小区公共选择器的Dao</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public interface ICommonExpressSmallZoneDao {
	/**
	 * <p>根据条件查询快递收派小区</p>
	 *<p>Title: queryCommonExpressSmallZoneList</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:25:26
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	List<CommonExpressSmallZoneEntity> queryCommonExpressSmallZoneList(CommonExpressSmallZoneEntity entity,int start,int limit);
	/**
	 * 统计总记录数
	 *<p>Title: queryCommonExpressSmallZoneCount</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:18:16
	 * @param entity
	 * @return
	 */
	long queryCommonExpressSmallZoneCount(CommonExpressSmallZoneEntity entity);
}
