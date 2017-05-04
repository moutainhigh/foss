package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IPopValueAddedDetailDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.pop.PriceValueAddedDetailEntity;

/**
 * 
 * <p>
 * Description:增值服务明细dao<br />
 * </p>
 * 
 * @title PopValueAdddedDetailDao.java
 * @package com.deppon.foss.module.pickup.pricing.server.dao.impl
 * @author xx
 * @version 0.1 2014年10月16日
 */
public class PopValueAdddedDetailDao extends SqlSessionDaoSupport implements IPopValueAddedDetailDao {
	// 增值明细ibatis命名空间
	private static final String PRICING_ENTITY_VALUEADDEDDETAIL = "foss.pkp.pkp-pricing.priceValueAddedEntityMapper.";
	// 插入增值明细
	private static final String INSERTSELECTIVE = "insertSelective";
	// 根据主键更新增值服务明细
	private static final String UPDATEVALUEADDED = "updateValueAdded";
	// 根据增值服务id更新增值服务明细
	private static final String UPDATEVALUEADDEDDETAILBYVALUEADDEDID = "updateValueAddedDetailByValueAddedId";
	//删除增值服务明细
	private static final String DELETEVALUEADDEDDETAIL = "deleteValueAddedDetail";
	//激活增值服务明细
	private static final String ACTIVEVALUEADDEDDETAIL = "activeValueAddedDetail";
	//根据id删除增值服务明细
	private static final String DELETEBYPRIMARYKEY = "deleteByPrimaryKey";
	//根据id查询增值服务明细
	private static final String SELECTBYVALUEADDEDID = "selectByValueAdded";

	/**
	 * 
	 * <p>
	 * Description:根据增值服务id查询增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param valueAddId
	 * @return
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceValueAddedDetailEntity> selectByValueAddedId(String valueAddId) {
		return getSqlSession().selectList(
				PRICING_ENTITY_VALUEADDEDDETAIL + SELECTBYVALUEADDEDID,
				valueAddId);
	}
	/**
	 * 
	 * <p>
	 * Description:插入增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param priceValueAddedDetailEntity
	 *
	 */
	@Override
	public void insertSelective(PriceValueAddedDetailEntity priceValueAddedDetailEntity) {
		getSqlSession().insert(PRICING_ENTITY_VALUEADDEDDETAIL + INSERTSELECTIVE, priceValueAddedDetailEntity);
	}

	/**
	 * 
	 * <p>
	 * Description:根据主键更新增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param priceValueAddedDetailEntity
	 *
	 */
	@Override
	public void updateValueAddedDetailByPrimaryKey(PriceValueAddedDetailEntity priceValueAddedDetailEntity) {
		// 修改时间
		priceValueAddedDetailEntity.setModifyDate(new Date());
		getSqlSession().update(PRICING_ENTITY_VALUEADDEDDETAIL + UPDATEVALUEADDED, priceValueAddedDetailEntity);

	}

	/**
	 * 
	 * <p>
	 * Description:根据增值服务id更新增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param priceValueAddedDetailEntity
	 *
	 */
	@Override
	public void updateValueAddedDetailByValueAddedId(PriceValueAddedDetailEntity priceValueAddedDetailEntity) {
		// 修改时间
		priceValueAddedDetailEntity.setModifyDate(new Date());
		getSqlSession().update(PRICING_ENTITY_VALUEADDEDDETAIL + UPDATEVALUEADDEDDETAILBYVALUEADDEDID, priceValueAddedDetailEntity);
	}

	/**
	 * 
	 * <p>
	 * Description:根据主键删除增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param id
	 *
	 */
	@Override
	public void deleteByPrimaryKey(String id) {
		 getSqlSession().delete(PRICING_ENTITY_VALUEADDEDDETAIL + DELETEBYPRIMARYKEY, id);

	}

	/**
	 * 
	 * <p>
	 * Description:根据增值服务id激活增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param valueAddedIds
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void activeValueAddedDetailByValueAddedIds(List<String> valueAddedIds) {
		Map map  = new HashMap();
		map.put("valueAddedIds", valueAddedIds);
		map.put("versionNo", new Date().getTime());
		 getSqlSession().update(
				 PRICING_ENTITY_VALUEADDEDDETAIL + ACTIVEVALUEADDEDDETAIL, map);
	}

	/**
	 * 
	 * <p>
	 * Description:删除未激活的增值服务明细<br />
	 * </p>
	 * 
	 * @author xx
	 * @version 0.1 2014年10月16日
	 * @param valueAddedIds
	 *
	 */
	@Override
	public void deleteValueAddedDetailByValueAddedIds(List<String> valueAddedIds) {
		Map<String,List<String>> map  = new HashMap<String,List<String>>();
		map.put("valueAddedIds", valueAddedIds);
		getSqlSession().delete(
				PRICING_ENTITY_VALUEADDEDDETAIL + DELETEVALUEADDEDDETAIL, map);
	}

}
