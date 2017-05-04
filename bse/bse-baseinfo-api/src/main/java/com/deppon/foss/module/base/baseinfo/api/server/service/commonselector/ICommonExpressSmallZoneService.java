/**
 * 
 */
package com.deppon.foss.module.base.baseinfo.api.server.service.commonselector;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;

/**
 *<p>Title: ICommonExpressSmallZoneService</p>
 * <p>Description:快递收派小区公共选择器Service </p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public interface ICommonExpressSmallZoneService extends IService{
	/**
	 *<p>Title: queryCommonExpressSmallZoneList</p>
	 *<p>根据条件查询快递手牌小区</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:15:33
	 * @param entity
	 * @param start
	 * @param limin
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
