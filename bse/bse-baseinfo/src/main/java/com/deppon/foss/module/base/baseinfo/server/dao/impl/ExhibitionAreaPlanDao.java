package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExhibitionAreaPlanDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExhibitionAreaPlanEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 展馆区域规划信息Dao
 * @author 187862
 * @date 2015-7-7 下午2:26:13
 */
public class ExhibitionAreaPlanDao extends SqlSessionDaoSupport implements
		IExhibitionAreaPlanDao{

	private static final String NAMESPACE = "foss.bse.bse-baseinfo.exhibitionAreaPlan.";

	/**
     * 新增展馆区域规划信息 ------
     * @author 187862-dujunhui
     * @date 2015-7-7 下午2:26:13
     * @param entity
     * @return 1：成功；-1：失败
     * @see
     */
	@Override
	public int addExhibitionAreaPlan(ExhibitionAreaPlanEntity entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setVirtualCode(entity.getId());
		entity.setActive(FossConstants.ACTIVE);
		entity.setCreateDate(new Date());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
		entity.setCreateUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
		entity.setModifyUser(entity.getCreateUser());
		entity.setVersionNo(new Date().getTime());
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
	}

	/**
     * 根据code作废展馆规划信息
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:21:25
     * @param codes  
     * @return 1：成功；-1：失败
     * @see
     */
	@Override
	public int deleteExhibitionAreaPlanByCode(String[] codes, String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exhibitionCodes", codes);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("activeN", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().update(NAMESPACE + "deleteByCode", map);
	}
	
	/**
     * 根据虚拟code作废展馆规划信息
     * @author 187862-dujunhui
     * @date 2015-9-6 下午4:08:16
     * @param codes  
     * @return 1：成功；-1：失败
     * @see
     */
	@Override
	public int deleteExhibitionAreaPlanByVirCode(String[] virtualCodes, String modifyUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("virtualCodes", virtualCodes);
		map.put("modifyUser", modifyUser);
		map.put("modifyDate", new Date());
		map.put("activeN", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		
		return this.getSqlSession().update(NAMESPACE + "deleteByVirtualCode", map);
	}

	/**
     * 根据传入对象查询符合条件的所有展馆规划信息
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:24:45
     * @param entity
     * @param limit
     * @param start
     * @return 符合条件的实体列表
     * @see
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<ExhibitionAreaPlanEntity> queryExhibitionAreaPlans(
			ExhibitionAreaPlanEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		entity.setActive(FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + "queryAreaPlanByCondition", entity, rowBounds);
	}

	/**
     * 统计总记录数 
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:27:16  
     * @param entity
     * @return
     * @see
     */
	@Override
	public Long queryRecordCount(ExhibitionAreaPlanEntity entity) {
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "getCountByCondition", entity);
	}

	/**
     * 根据展馆编码查询展馆区域规划详细信息，验证编码唯一性 
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:30:09 
     * @param exhibitionCode 展馆编码
     * @return 
     */
	@SuppressWarnings("unchecked")
	@Override
	public ExhibitionAreaPlanEntity queryExhibitionAreaPlanByCode(
			String exhibitionCode) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("exhibitionCode", exhibitionCode);
		map.put("active", FossConstants.ACTIVE);
		List<ExhibitionAreaPlanEntity> list=this.getSqlSession().selectList(NAMESPACE + "queryAreaPlanByCode", map);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	/**
     * 根据展馆名称查询展馆区域规划详细信息，验证名称唯一性 
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:33:21 
     * @param exhibitionName 展馆名称
     * @return 
     */
	@SuppressWarnings("unchecked")
	@Override
	public ExhibitionAreaPlanEntity queryExhibitionAreaPlanByName(
			String exhibitionName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("exhibitionName", exhibitionName);
		map.put("active", FossConstants.ACTIVE);
		List<ExhibitionAreaPlanEntity> list=this.getSqlSession().selectList(NAMESPACE + "queryAreaPlanByName", map);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	/**
     * 根据管理部门查询展馆编码最大值
     * @author 187862-dujunhui
     * @date 2015-7-7 下午4:35:34
     * @return 
     */
	@Override
	public String queryCodeByManagement(ExhibitionAreaPlanEntity entity) {
		return (String) this.getSqlSession().selectOne(NAMESPACE + "queryExhibitionCodeByManagement", entity);
	}
}