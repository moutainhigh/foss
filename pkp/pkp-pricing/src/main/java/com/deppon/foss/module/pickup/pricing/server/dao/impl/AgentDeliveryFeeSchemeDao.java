package com.deppon.foss.module.pickup.pricing.server.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IAgentDeliveryFeeSchemeDao;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.AgentDeliveryFeeSchemeEntity;

public class AgentDeliveryFeeSchemeDao extends iBatis3DaoImpl implements
		IAgentDeliveryFeeSchemeDao {
	private static final String MYBATIS_NAME_SPASE ="foss.pkp.pkp-pricing.agentDeliveryFeeScheme.";
	/**
	 * @author 092020-lipengfei
	 * @description 根据参数查询偏线代理送货费方案
	 * @param entity
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentDeliveryFeeSchemeEntity> queryAgentDeliveryFeeSchemeByParams(
			AgentDeliveryFeeSchemeEntity entity,int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		Map<String,String> map=new HashMap<String, String>();
		map.put("schemeName", entity.getSchemeName());
		map.put("agentDeptCode", entity.getAgentDeptCode());
		return getSqlSession().selectList(MYBATIS_NAME_SPASE + "queryAgentDeliveryFeeSchemeByParams", map,rowBounds);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 查询偏线代理送货费方案总量
	 * @param entity
	 * @return Long
	 */
	@Override
	public Long queryRecordCount(AgentDeliveryFeeSchemeEntity entity) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("schemeName", entity.getSchemeName());
		map.put("agentDeptCode", entity.getAgentDeptCode());
		return (Long) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryRecordCount",  map); 
	}
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int addAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity) {
		return getSqlSession().insert(MYBATIS_NAME_SPASE + "addAgentDeliveryFeeScheme", entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 新增偏线代理送货费
	 * @param entityList
	 * @return Integer
	 */
	@Override
	public int addAgentDeliveryFee(List<AgentDeliveryFeeEntity> entityList) {
		Map<String,List<AgentDeliveryFeeEntity>> map=new HashMap<String, List<AgentDeliveryFeeEntity>>();
		map.put("entityList", entityList);
		return getSqlSession().insert(MYBATIS_NAME_SPASE + "addAgentDeliveryFee", map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID删除偏线代理送货费方案
	 * @param idList
	 * @return Integer
	 */
	@Override
	public int deleteAgentDeliveryFeeSchemeById(List<String> idList) {
		List<String> list=new ArrayList<String>();
		int result=0;
		//分批处理，每200条处理一次
		for(int i=0;i<idList.size();i++){
			list.add(idList.get(i));
			if(i%NumberConstants.NUMBER_200==0||i==idList.size()-1){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idList", list);
				result+=getSqlSession().delete(MYBATIS_NAME_SPASE+"deleteAgentDeliveryFeeSchemeById", map);
				list.clear();
			}
		}
		return result;
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案Id删除偏线代理送货费
	 * @param schemeIdList
	 * @return Integer
	 */
	@Override
	public int deleteAgentDeliveryFeeByschemeId(List<String> schemeIdList) {
		Map<String,List<String>> map=new HashMap<String, List<String>>();
		map.put("schemeIdList", schemeIdList);
		return getSqlSession().delete(MYBATIS_NAME_SPASE+"deleteAgentDeliveryFeeByschemeId", map);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 更新偏线代理送货费方案
	 * @param entity
	 * @return Integer
	 */
	@Override
	public int updateAgentDeliveryFeeScheme(AgentDeliveryFeeSchemeEntity entity) {
		return getSqlSession().update(MYBATIS_NAME_SPASE+"updateAgentDeliveryFeeScheme",entity);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据ID查询偏线代理送货费方案
	 * @param schemeId
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeById(
			String schemeId) {
		return (AgentDeliveryFeeSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryAgentDeliveryFeeSchemeById",  schemeId); 
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据名称查询偏线代理送货费方案
	 * @param schemeName
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeByName(
			String schemeName) {
		return (AgentDeliveryFeeSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "queryAgentDeliveryFeeSchemeByName",  schemeName); 
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据方案ID查询偏线代理送货费
	 * @param schemeId
	 * @return AgentDeliveryFeeEntity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<AgentDeliveryFeeEntity> queryAgentDeliveryFeeBySchemeId(
			String schemeId) {
		return getSqlSession().selectList(MYBATIS_NAME_SPASE + "queryAgentDeliveryFeeBySchemeId", schemeId);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站统计送货费方案
	 * @param agentDeptCode
	 * @return Integer
	 */
	@Override
	public int countSchemeByagentDeptCode(String agentDeptCode){
		return (Integer) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "countSchemeByagentDeptCode", agentDeptCode);
	}
	/**
	 * @author 092020-lipengfei
	 * @description 根据目的站查询送货费方案
	 * @param agentDeptCode
	 * @return AgentDeliveryFeeSchemeEntity
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity querySchemeByagentDeptCode(
			String agentDeptCode) {
		return (AgentDeliveryFeeSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE + "querySchemeByagentDeptCode", agentDeptCode);
	}
	/**
	 * DEFECT-4086	
	 * @author 200945-wutao
	 * @description 验证目的站是否重复
	 * @return AgentDeliveryFeeSchemeEntity
	 * @param deptCode
	 */
	@Override
	public AgentDeliveryFeeSchemeEntity queryAgentDeliveryFeeSchemeByDeptCode(
			String deptCode) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("agentDeptCode",deptCode);
		return (AgentDeliveryFeeSchemeEntity) getSqlSession().selectOne(MYBATIS_NAME_SPASE+"queryAgentDeliveryFeeSchemeByDeptCode", map);
	}
}
