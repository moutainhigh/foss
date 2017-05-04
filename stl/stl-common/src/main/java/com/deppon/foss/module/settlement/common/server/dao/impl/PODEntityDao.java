package com.deppon.foss.module.settlement.common.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.settlement.common.api.server.dao.IPODEntityDao;
import com.deppon.foss.module.settlement.common.api.shared.domain.PODEntity;

/**
 * 财务签收记录表 Dao
 * 
 * @author 099995-foss-wujiangtao
 * @date 2012-12-10 下午2:12:31
 * @since
 * @version
 */
public class PODEntityDao extends iBatis3DaoImpl implements IPODEntityDao{

	private static final String NAMESPACE = "foss.stl.PODEntityDao.";// 命名空间路径
	
	/**
	 * 新增财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午1:48:01
	 * @param entity 财务签收记录
	 * @return
	 */
	@Override
	public int add(PODEntity entity) {
		return this.getSqlSession().insert(NAMESPACE+"insert",entity);
	}

	/**
	 * 根据运单号查询运单的财务签收记录
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2012-12-10 下午1:57:02
	 * @param waybillNo 运单号
	 * @param podType   签收/反签收类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PODEntity> queryByWaybillNo(String waybillNo, String podType) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("waybillNo", waybillNo);
		map.put("podType", podType);
		return this.getSqlSession().selectList(NAMESPACE+"selectByWaybillNo",map);
	}
	
	/**
	 * 根据运单号查询运单的最新财务签收记录
	 * @author 092036-foss-bochenlong
	 * @date 2013-07-24 下午6:43:00
	 * @param waybillNo  运单号
	 * @return PODEntity
	 */
	@Override
	public PODEntity queryNewestPOD(String waybillNo) {
		return (PODEntity) this.getSqlSession().selectOne(NAMESPACE+"selectNewestPOD",waybillNo);
	}

}
