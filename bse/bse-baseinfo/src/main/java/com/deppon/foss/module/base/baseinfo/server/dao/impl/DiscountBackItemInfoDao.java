package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IDiscountBackItemInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CostCenterDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DiscountBackItemEntity;
/**
 * 事后折详细信息DAO实现类
 * @author 132599
 * ShenWeiHua 2015-02-06
 */
public class DiscountBackItemInfoDao extends SqlSessionDaoSupport implements IDiscountBackItemInfoDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.discountBackItemInfo.";
	
	/**
	 * 把从CRM系统同步过来的事后折数据插入到FOSS表内
	 * @author ShenWeiHua
	 * @param entity
	 * @return
	 * @date 2015-02-06 上午9:28:02
	 */
	@Override
	public int insertDiscountBackItemInfo(DiscountBackItemEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE+"insert",entity);
	}
	
	/**
	 * 把从CRM系统同步过来的事后折数据更新到FOSS表内
	 * @param entity
	 * @author ShenWeiHua
	 * @return
	 * @date 2015-02-06 上午9:29:02
	 */
	@Override
	public int updateDiscountBackItemInfo(DiscountBackItemEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE+"update",entity);
	}
	/**
	 * 根据crmId验证该信息是否已存在于FOSS数据库
	 * @author 132599-FOSS-ShenWeiHua
	 * @param crmId
	 * @date 2015-02-06 下午5:09:02
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean queryDiscountBackItemInfoByCrmId(BigDecimal crmId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("crmId", crmId);
		List<CostCenterDeptEntity> list = this.getSqlSession().selectList(
				NAMESPACE + "queryDiscountBackItemInfoByCrmId", map);
		return CollectionUtils.isNotEmpty(list);
	}

}
