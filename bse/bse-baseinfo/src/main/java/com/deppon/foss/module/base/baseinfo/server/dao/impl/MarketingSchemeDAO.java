package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IMarketingSchemeDAO;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarketingSchemeEntity;

public class MarketingSchemeDAO extends SqlSessionDaoSupport implements IMarketingSchemeDAO {
	/**
	 * ibatis Mapper
	 */
	private static final String MAKETING_EVENT_NAME_SPASE="foss.bse.bse-baseinfo.marketingScheme.";
	
	/**
	 * <p>根据主键Id删除记录<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-2 下午3:40:23
	 * @param id
	 * @return
	 */
	@Override
	public int deleteByPrimaryKey(String id) {
		return getSqlSession().delete(MAKETING_EVENT_NAME_SPASE + "deleteByPrimaryKey",id);
	}

	 /**
     * <p>根据主键查询记录<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-2 下午3:43:25
     * @param id
     * @return
     */
	@Override
	public MarketingSchemeEntity selectByPrimaryKey(String id) {
		String sql = MAKETING_EVENT_NAME_SPASE + "selectByPrimaryKey";		
		return (MarketingSchemeEntity)getSqlSession().selectOne(sql, id);				
	}

	/**
     * <p>Description:根据code查询记录<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-25 下午4:04:36
     * @param 
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<MarketingSchemeEntity> selectByMarketCode(String code, Date billDate) {
		Map map = new HashMap();
		map.put("code", code);
		map.put("endTime", billDate);
		String sql = MAKETING_EVENT_NAME_SPASE + "selectByMarketCode";		
		return getSqlSession().selectList(sql, map);	
    }
	
	/**
     * <p>根据主键更新内容不为空的字段<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-20 下午4:42:37
     * @param record
     * @return
     */
	@Override
	public int updateByPrimaryKeySelective(MarketingSchemeEntity record) {
		String sqlAddress = MAKETING_EVENT_NAME_SPASE + "updateByPrimaryKeySelective";		
		return getSqlSession().update(sqlAddress, record);				
	}

	/**
   	 * <p>查询降价发券方案总数<br/></p>
   	 * @author dujunhui-187862
   	 * @date 2014-10-1  上午11:17:28
   	 * @param marketintEvent
   	 * @return
   	 * @see
   	 */
	@Override
	public Long countMarketingScheme(MarketingSchemeEntity marketintEvent) {
		String sql = MAKETING_EVENT_NAME_SPASE + "countMarketingSchemeByCondition";	
		return (Long)getSqlSession().selectOne(sql, marketintEvent);
	}
	
	/**
   	 * 
   	 * <p>查询降价发券方案列表（分页）</p> 
   	 * @author dujunhui-187862
   	 * @date 2014-10-1  上午11:15:40
   	 * @param marketintEvent
   	 * @param start
   	 * @param limit
   	 * @return
   	 * @see
   	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingSchemeEntity> queryMarketingSchemeList(MarketingSchemeEntity marketingEvent, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		String sql = MAKETING_EVENT_NAME_SPASE + "queryMarketingSchemeByCondition";	
		return getSqlSession().selectList(sql, marketingEvent, rowBounds);
	}
	
	/**
	 * <p>根据NAME查询折扣方案<br/></p>
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:20:36
	 * @param marketintEventName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MarketingSchemeEntity> queryMarketingEventByName(String marketintEventName) {
		String sql = MAKETING_EVENT_NAME_SPASE + "queryMarketingEventByName";	
		return getSqlSession().selectList(sql, marketintEventName);
	}

	/**
	 * @Description: 获取折扣方案最大的编码
	 * @author dujunhui-187862
	 * @date 2014-10-1 下午6:32:36
	 * @return
	 */
	@Override
	public String getMarketingEventMaxCode(String type) {
		String sql = MAKETING_EVENT_NAME_SPASE + "getMarketEventMaxCode";	
		return (String)getSqlSession().selectOne(sql, type);
	}
	
	 /**
     * <p>新增记录（内容不为空的字段）<br/></p>
     * @author dujunhui-187862
     * @date 2014-10-2 上午9:30:47
     * @param record
     * @return
     */
	@Override
	public int insertSelective(MarketingSchemeEntity record) {
		String sql = MAKETING_EVENT_NAME_SPASE + "insertSelective";		
		return getSqlSession().insert(sql, record);				
	}

}