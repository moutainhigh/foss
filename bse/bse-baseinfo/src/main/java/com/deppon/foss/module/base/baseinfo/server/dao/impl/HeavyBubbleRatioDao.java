package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IHeavyBubbleRatioDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HeavyBubbleRatioEntity;
import com.deppon.foss.util.define.FossConstants;

/**
 * @author 218392 张永雪
 *
 * 2014-11-19下午2:25:39
 */
public class HeavyBubbleRatioDao extends SqlSessionDaoSupport implements IHeavyBubbleRatioDao{
	
	private static final String NAMESPACE = "foss.bse.bse-baseinfo.heavyBubbleRatio.";
	
	/**
	 * 验证重泡比外场是否存在
	 */
	@Override
	public boolean 	queryOutfieldNameByHeavyBubbleRatio(String outfield){
		Map<String,String> map = new HashMap<String,String>();
		map.put("outfield", outfield);
		map.put("active", FossConstants.ACTIVE);
		List list = this.getSqlSession().selectList(NAMESPACE + "queryOutfieldNameByHeavyBubbleRatio", map);
		return CollectionUtils.isNotEmpty(list);
	}
	/**
	 *  根据外场code 查询重泡比参数
	 */
	@Override
	public String queryHeavyBubbleParamByOutfield(String outfield) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("outfield", outfield);
		map.put("active", FossConstants.ACTIVE);
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryHeavyBubbleParamByOutfield", map);
	}
	
	/**
	 * 新增重泡比信息
	 */
	@Override
	public int addHeavyBubbleRatio(HeavyBubbleRatioEntity entity) {
		
		return this.getSqlSession().insert(NAMESPACE + "insert", entity);
		
	}
	
	/**
	 * 修改重泡比信息
	 */
	@Override
	public int updateHeavyBubbleRatio(HeavyBubbleRatioEntity entity) {
		
		return this.getSqlSession().update(NAMESPACE + "update", entity);
		
	}

	/**
	 * 作废重泡比信息
	 */
	@Override
	public int deleteHeavyBubbleRatio(List<String> idList) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("idList", idList);
		map.put("inactive", FossConstants.INACTIVE);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().update(NAMESPACE + "deleteById", map);
	}

	
	/**
	 * 根据输入的查询条件查询信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HeavyBubbleRatioEntity> queryAllHeavyBubbleRatio(
			HeavyBubbleRatioEntity entity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start,limit);
		return this.getSqlSession().selectList(NAMESPACE + "queryAllHeavyBubbleRatio", entity, rowBounds);
	}

	/**
	 * 统计总记录数
	 */
	@Override
	public Long queryRecordCount(HeavyBubbleRatioEntity entity) {
		// TODO Auto-generated method stub
		return (Long)this.getSqlSession().selectOne(NAMESPACE + "queryCount", entity);
	}

	

}
