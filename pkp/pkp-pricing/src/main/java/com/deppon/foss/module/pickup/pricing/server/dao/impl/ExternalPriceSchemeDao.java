package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IExternalPriceSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ExternalPriceSchemeEntity;
/**
 * @author 092020-lipengfei
 *	偏线外发价格方案Dao
 */
public class ExternalPriceSchemeDao extends iBatis3DaoImpl implements
		IExternalPriceSchemeDao {
	private static final String MYBATIS_NAME_SPASE="foss.pkp.pkp-pricing.externalPriceScheme.";
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalPriceSchemeEntity> queryExternalPriceSchemeByParam(
			ExternalPriceSchemeEntity entity,int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		Map<String,String> map=new HashMap<String, String>();
		map.put("agentDeptCode", entity.getAgentDeptCode());
		map.put("externalOrgCode", entity.getExternalOrgCode());
		map.put("transportType", entity.getTransportType());
		map.put("active", entity.getActive());
		return getSqlSession().selectList(MYBATIS_NAME_SPASE + "queryExternalPriceSchemeByParam", map,rowBounds);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线外发价格方案总量
	 * @param entity
	 * @return Long
	 */
	@Override
	public Long queryRecordCount(ExternalPriceSchemeEntity entity) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("agentDeptCode", entity.getAgentDeptCode());
		map.put("externalOrgCode", entity.getExternalOrgCode());
		map.put("transportType", entity.getTransportType());
		map.put("active", entity.getActive());
		return (Long) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryRecordCount", map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	@Override
	public ExternalPriceSchemeEntity queryExternalePriceSchemeById(
			String schemeId) {
		return (ExternalPriceSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryExternalePriceSchemeById", schemeId);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int addExternalPriceScheme(ExternalPriceSchemeEntity entity) {
		return getSqlSession().insert(MYBATIS_NAME_SPASE + "addExternalPriceScheme", entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案名称查询偏线外发价格方案
	 * @param entity
	 * @return List<ExternalPriceSchemeEntity>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExternalPriceSchemeEntity> queryExternalePriceSchemeByName(
			String schemeName) {
		return (List<ExternalPriceSchemeEntity>) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryExternalePriceSchemeByName", schemeName);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 修改偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int updateExternalPriceScheme(ExternalPriceSchemeEntity entity) {
		return getSqlSession().update(MYBATIS_NAME_SPASE+"updateExternalPriceScheme",entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 删除偏线外发价格方案
	 * @param idList
	 * @return Integer
	 */
	@Override
	public int deleteExternalPriceSchemeById(List<String> idList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", idList);
		return getSqlSession().delete(MYBATIS_NAME_SPASE+"deleteExternalPriceSchemeById", map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线外发价格方案状态（激活、中止）
	 * @param idList
	 * @param state
	 * @return Integer
	 */
	@Override
	public int updateSchemeStateById(ExternalPriceSchemeEntity entity) {
		return getSqlSession().update(MYBATIS_NAME_SPASE+"updateSchemeStateById",entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID和状态查询偏线外发价格方案
	 * @param idList
	 * @param state
	 * @return Integer
	 */
	@Override
	public Long queryExternalPriceSchemeByIdAndState(
			List<String> idList, String active) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("idList", idList);
		map.put("active", active);
		return (Long) getSqlSession().selectOne(MYBATIS_NAME_SPASE+"queryExternalPriceSchemeByIdAndState",map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 复制偏线外发价格方案
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int copyExternalPriceScheme(ExternalPriceSchemeEntity entity,String copyId) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("entity", entity);
		map.put("copyId", copyId);
		return getSqlSession().insert(MYBATIS_NAME_SPASE+"copyExternalPriceScheme",map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线外发价格方案截止日期
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int updateExternalPriceSchemeEndTime(ExternalPriceSchemeEntity entity) {
		return getSqlSession().update(MYBATIS_NAME_SPASE+"updateExternalPriceSchemeEndTime",entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 批量新增偏线外发价格方案
	 * @param entityList
	 * @return Integer
	 */
	@Override
	public int batchAddExternalPriceScheme(
			List<ExternalPriceSchemeEntity> entityList) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("entityList", entityList);
		return getSqlSession().insert(MYBATIS_NAME_SPASE+"batchAddExternalPriceScheme", map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站，外发外场，运输类型，外发单生成时间 查询偏线外发价格方案
	 * @param targetOrgCode
	 * @param outOrgCode
	 * @param transportType
	 * @param externalBillCreateTime
	 * @return ExternalPriceSchemeEntity
	 */
	@Override
	public ExternalPriceSchemeEntity queryAgentOutPriceInfo(
			String targetOrgCode, String outOrgCode, String transportType,
			Date externalBillCreateTime) {
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("targetOrgCode", targetOrgCode);
		map.put("outOrgCode", outOrgCode);
		map.put("transportType", transportType);
		map.put("externalBillCreateTime", externalBillCreateTime);
		return (ExternalPriceSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE+"queryAgentOutPriceInfo",map);
	}

}
