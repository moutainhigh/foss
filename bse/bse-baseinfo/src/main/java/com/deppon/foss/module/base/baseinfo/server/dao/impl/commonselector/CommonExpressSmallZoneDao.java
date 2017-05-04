
package com.deppon.foss.module.base.baseinfo.server.dao.impl.commonselector;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.commonselector.ICommonExpressSmallZoneDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonExpressSmallZoneEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 *<p>Title: CommonExpressSmallZoneDao</p>
 * <p>Description: 快递收派小区公共选择器的Dao</p>
 * <p>Company: Deppon</p>
 * @author    130566-ZengJunfan
 * @date       2014-5-4
 */
public class CommonExpressSmallZoneDao extends SqlSessionDaoSupport implements
		ICommonExpressSmallZoneDao {
	private final static String NAMSPACE ="foss.bse.bse-baseinfo.commonExpressSmallZone.";
	/**
	 *<p>Title: queryCommonExpressSmallZoneList</p>
	 *<p>根据条件查询快递收派小区</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:28:35
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonExpressSmallZoneEntity> queryCommonExpressSmallZoneList(
			CommonExpressSmallZoneEntity entity, int start, int limit) {
		RowBounds bounds =new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMSPACE+"queryCommonExpressSmallZoneList", entity,bounds);
	}
	/**
	 *<p>Title: queryCommonExpressSmallZoneCount</p>
	 *<p>统计总记录数</p>
	 *@author 130566-ZengJunfan
	 *@date 2014-5-4上午11:28:35
	 * @param entity
	 * @return
	 */
	@Override
	public long queryCommonExpressSmallZoneCount(
			CommonExpressSmallZoneEntity entity) {
		entity.setActive(FossConstants.ACTIVE);
		return (Long) this.getSqlSession().selectOne(NAMSPACE+"queryCommonExpressSmallZoneCount", entity);
	}

}
